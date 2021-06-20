package com.ootd.ootdApp.myPage.brand.model.service;

import java.util.List;
import java.util.Map;

import com.ootd.ootdApp.member.model.vo.Member;
import com.ootd.ootdApp.myPage.brand.model.vo.MypageOrderList;
import com.ootd.ootdApp.product.model.vo.Product;

public interface BrandService {

	List<Map<String, String>> selectBrandOrderList(int cPage, int numPerPage, String brand_name);
	
	int brandSelectTotalContents();

	List<Map<String, String>> selectBrandProductList(int cPage, int numPerPage, int member_no);

	int deleteBrandProductList(int productNo);
	
	// 주문 detail
	List<MypageOrderList> selectBrandOrderDetail(int orderNo);
	
	// Info update에 관련된 공통 함수
	int updateBrandInfo(Member tempMember);

	int emUpdateBrandInfo(Member tempMember);

	int phUpdateBrandInfo(Member tempMember);

	int acUpdateBrandInfo(Member tempMember);

	List<Product> selectBrandProductRankList(int member_no);

	int updateBrandStatus(int orderNo);

	int brandProductSelectTotalContents();

}