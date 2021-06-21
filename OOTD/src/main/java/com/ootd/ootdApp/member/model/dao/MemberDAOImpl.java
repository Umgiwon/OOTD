package com.ootd.ootdApp.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ootd.ootdApp.member.model.vo.Member;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	SqlSessionTemplate sqlSession;
	
	@Override
	public Member selectOneMember(String member_id) {
		
		return sqlSession.selectOne("member-mapper.selectOneMember", member_id);
	}

	@Override
	public int insertMember(Member member) {
		
		return sqlSession.insert("member-mapper.insertMember", member);
	}
	
	@Override
	public int insertBrandMember(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.insert("member-mapper.insertBrandMember", member);
	}

	@Override
	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Member selectMemberID(Member member) {
		
		if ( member.getLogin_type() == 1 ) {
			return sqlSession.selectOne("member-mapper.selectBrandId", member);
		} else {
			return sqlSession.selectOne("member-mapper.selectMemberId", member);
		}
	}

	@Override
	public int updateNewPass(Member m) {
		
		return sqlSession.update("member-mapper.updatePasswordFind", m);
	}

	@Override
	public int selectKakaoCount(String member_id) {
		
		return sqlSession.selectOne("member-mapper.selectKakaoCount", member_id);
	}

	

	

	

}
