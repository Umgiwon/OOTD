package com.ootd.ootdApp.member.controller;

import java.nio.charset.Charset;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ootd.ootdApp.member.exception.MemberException;
import com.ootd.ootdApp.member.model.service.MemberService;
import com.ootd.ootdApp.member.model.vo.MailVO;
import com.ootd.ootdApp.member.model.vo.Member;



@SessionAttributes({"member"})
@Controller
public class MemberController {

	@Autowired 
	private JavaMailSenderImpl mailSender;

	
	@Autowired
	MemberService memberService;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	@RequestMapping("/member/goMyPage.do")
	public String goMyPage(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("member");
		
		if ( member.getLogin_type() == 1) return "myPage/myPage_Brand_Info";
		else return "myPage/myPage_Info";
	}
	
	@RequestMapping("/member/memberJoin.do")
	public String memberJoin() {
		
		return "member/memberJoin";
	}
	
	@RequestMapping("/member/brandJoin.do")
	public String brandJoin() {
		
		return "member/brandJoin";
	}
	
	
	@RequestMapping("/member/joinAccept.do")
	public String memberJoinAccept() {
		
		return "member/joinAccept";
	}
	
	@RequestMapping("member/memberJoinEnd.do")
	public String memberJoinEnd(
				SessionStatus status,
				Member member,
				Model model
			) {
		
		System.out.println("joinEnd 들어온 정보 : " + member);
		
		String pass1 = member.getMember_pw();
		String pass2 = bcrypt.encode(pass1);
		
		member.setMember_pw(pass2);
		
		int login_type = member.getLogin_type();
		
		
		try {
			
			int result = 0;
			
			if ( login_type == 1 ) result = memberService.insertBrandMember(member);
			else if ( login_type == 2 ) result = memberService.insertMember(member);
			
			String msg = "";
			String loc = "/";
			
			if ( result > 0 ) {
				msg = "회원가입 성공!";
			} else {
				msg = "회원가입 실패";
			}
			
			status.setComplete();
			
			model.addAttribute("loc", loc);
			model.addAttribute("msg", msg);
			
		} catch ( Exception e ) {
			
			System.out.println("회원가입 에러");
			
			throw new MemberException(e.getMessage());
		}
		
		return "common/msg";
	}
	
	
	@RequestMapping("member/memberLogin.do")
	public String memberLogin(
				@RequestParam String member_id,
				@RequestParam String member_pw,
				Model model
			) {
		
		System.out.println("login Access");
		
		Member result = memberService.selectOneMember(member_id);
		
		System.out.println("로그인 조회 결과 : " + result);
		
		String loc = "/";
		String msg = "";
		
		if ( result != null ) {
			
			if ( bcrypt.matches(member_pw, result.getMember_pw()) ) {
				
				msg = "로그인 성공";
				model.addAttribute("member", result);
				
			} else {
				msg = "비밀번호를 다시 확인해주세요.";	
			}
		} else {
			msg = "아이디를 다시 확인해주세요.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("loc", loc);
		
		return "common/msg";
	}
	
	@RequestMapping("member/logout.do")
	public String logout(SessionStatus status, Model model) {
		
		String msg = "";
		
		if ( ! status.isComplete() ) {
			status.setComplete();
			
			msg = "로그아웃 완료";
		}
		
		model.addAttribute("msg", msg);
		
		return "common/msg";
	}
	
	@RequestMapping("member/sendIdMail.do") 
	public String sendIdMail(
				final MailVO vo,
				Member member,
				Model model,
				SessionStatus status
			) { 
		
		System.out.println("sendMail Controller 접근 : " + member);
		
		Member m;
		String msg;
		
		if ( member.getLogin_type() == 1 ) {
			member.setBrand_name( member.getMember_name() );
			member.setMember_name(null);
		}
		
		m = memberService.selectMemberID(member);
			
		System.out.println("sendMail selectMemberId :: " + m);
			
		if ( m != null ) {
				
			vo.setFrom("alfus78910@gmail.com");
			vo.setTo(m.getEmail());
			vo.setSubject("[내일의 옷] 아이디 확인 관련 메일");
			
			if ( m.getLogin_type() == 2 ) {
				
				vo.setContents(
						"<html><body>"
						+ "<h2>내일의 옷에서 알려드립니다.</h2><br>"
						+ "<p>개인 회원이신 " + m.getMember_name() + "님의 아이디는 <p style='font-weight:bolder;'>" 
						+ m.getMember_id() + "</p> 입니다.<br>"
						+ "확인 후 내일의 옷 홈페이지에서 로그인해주세요."
						+ "</p>"
						+ "</body></html>"
					);
				
			} else {
				
				vo.setContents(
						"<html><body>"
						+ "<h2>내일의 옷에서 알려드립니다.</h2><br>"
						+ "<p>브랜드 회원이신 " + m.getBrand_name() + "님의 아이디는 <p style='font-weight:bolder;'>" 
						+ m.getMember_id() + "</p> 입니다.<br>"
						+ "확인 후 내일의 옷 홈페이지에서 로그인해주세요."
						+ "</p>"
						+ "</body></html>"
					);
			}
				
			final MimeMessagePreparator preparator = new MimeMessagePreparator() { 
				@Override public void prepare(MimeMessage mimeMessage) throws Exception { 
					final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); 
					helper.setFrom("OOTD <alfus78910@gmail.com>"); 
					helper.setTo(vo.getTo()); 
					helper.setSubject(vo.getSubject()); 
					helper.setText(vo.getContents(), true); 
				} 
			}; 
					
			mailSender.send(preparator); 
			
			if ( m.getLogin_type() == 2 ) {
				
				msg = m.getMember_name() + "님의 메일주소로 아이디를 발송했습니다. 확인 후 로그인해주세요";
				
			} else {
				
				msg = m.getBrand_name() + "님의 메일주소로 아이디를 발송했습니다. 확인 후 로그인해주세요";
			}
		
		} else {
				
			msg = "입력하신 이름과 메일주소로 가입된 정보가 없습니다.";
				
		}
		
		// 세션 등록 방지
		status.setComplete();
		
		model.addAttribute("msg", msg);
				
		return "common/msg"; 	
	}
	
	@RequestMapping("member/sendPwMail.do") 
	public String sendPwMail(
				final MailVO vo,
				Member member,
				Model model,
				SessionStatus status
			) { 
		
		System.out.println("sendMail Controller 접근 : " + member);
		
		Member m;
		String msg = "";
		
		if ( member.getLogin_type() == 1 ) {
			member.setBrand_name( member.getMember_name() );
			member.setMember_name(null);
		}
			
		m = memberService.selectMemberID(member);
			
		System.out.println("sendMail selectMemberId :: " + m);
			
		if ( m != null ) {
				
			String newPass = randomCode();
			String encryptpass = bcrypt.encode(newPass);
			
			m.setMember_pw(encryptpass);
			
			int updateResult = memberService.updateNewPass(m);
			
			if ( updateResult > 0 ) {
				
				vo.setFrom("alfus78910@gmail.com");
				vo.setTo(m.getEmail());
				vo.setSubject("[내일의 옷] 비밀번호 확인 관련 메일");
			
				if ( m.getLogin_type() == 2 ) {
				
					vo.setContents(
							"<html><body>"
							+ "<h2>내일의 옷에서 알려드립니다.</h2><br>"
							+ "<p>개인 회원이신 " + m.getMember_name() 
							+ "님의 임시 비밀번호는 <p style='font-weight:bolder;'>" 
							+ newPass + "</p> 입니다.<br>"
							+ "확인 후 내일의 옷 홈페이지에서 로그인해주세요.<br>"
							+ "로그인 후에는 마이페이지 > 회원정보 에서 반드시 비밀번호를 재설정하세요."
							+ "</p>"
							+ "</body></html>"
						);
				
				} else {
				
					vo.setContents(
						"<html><body>"
						+ "<h2>내일의 옷에서 알려드립니다.</h2><br>"
						+ "<p>브랜드 회원이신 " + m.getBrand_name() 
						+ "님의 임시 비밀번호는 <p style='font-weight:bolder;'>" 
						+ newPass + "</p> 입니다.<br>"
						+ "확인 후 내일의 옷 홈페이지에서 로그인해주세요."
						+ "로그인 후에는 마이페이지 > 회원정보 에서 반드시 비밀번호를 재설정하세요."
						+ "</p>"
						+ "</body></html>"
					);
				}
				
				final MimeMessagePreparator preparator = new MimeMessagePreparator() { 
					@Override public void prepare(MimeMessage mimeMessage) throws Exception { 
						final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); 
						helper.setFrom("OOTD <alfus78910@gmail.com>"); 
						helper.setTo(vo.getTo()); 
						helper.setSubject(vo.getSubject()); 
						helper.setText(vo.getContents(), true); 
					} 
				}; 
					
				mailSender.send(preparator); 
			
				if ( m.getLogin_type() == 2 ) {
				
					msg = m.getMember_name() + "님의 메일주소로 임시 비밀번호를 발송했습니다. 확인 후 로그인해주세요";
				
				} else {
				
					msg = m.getBrand_name() + "님의 메일주소로 임시 비밀번호를 발송했습니다. 확인 후 로그인해주세요";
				}
				
			} else {
				System.out.println("임시 비밀번호 업데이트 실패");
			}
			
			
		
		} else {
				
			msg = "입력하신 정보로 가입된 정보가 없습니다.";
				
		}
		
		// 세션 등록 방지
		status.setComplete();
		
		model.addAttribute("msg", msg);
				
		return "common/msg"; 	
	}
	
	// 랜덤한 임시 비밀번호 생성 메소드
	public String randomCode() {
		
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 8;
		Random random = new Random();

		String generatedString = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();

		System.out.println(generatedString);
		
		return generatedString;
	}


	
	
	
	
}
