package com.ootd.ootdApp.product.model.dao;

import java.util.List;
import java.util.Map;

import com.ootd.ootdApp.product.model.vo.Product;

public interface ProductDAO {

	// =========Product List (select) 조회=========

	/**
	 * brandList 조회를 위한 메소드
	 * @param numPerPage 
	 * @param cPage 
	 * @param product 
	 * 
	 * @return List<Product>
	 */
	List<Map<String, String>> brandSelectList(int cPage, int numPerPage, Product product);

	/**
	 * secondHandList 조회를 위한 메소드
	 * @param numPerPage 
	 * @param cPage 
	 * 
	 * @return List<Product>
	 */
	List<Map<String, String>> secondHandSelectList(int cPage, int numPerPage , Product product);
	
	
	/**
	 * brnadList 전체갯수 조회를 위한 메소드
	 */
	int brandSelectTotalContents();

	/**
	 * secondHandList 전체갯수 조회를 위한 메소드
	 */
	int secondHandSelectTotalContents();
	
	/**
	 * brand_name 전체조회를 위한 메소드
	 */
	List<String> brandNameSelectList();
	
	
	
	// =========Product Input (insert)=========

	/**
	 * brand 상품 등록을 위한 메소드
	 * 
	 * @param product
	 * @return int
	 */
	int brandInsert(Product product);

	/**
	 * secondHand 상품 등록을 위한 메소드
	 * 
	 * @param product
	 * @return int
	 */
	int secondHandInsert(Product product);

	// =========Product Detail (selectOne)=========

	/**
	 * 상품 번호로 brand상품 한 개 조회를 위한 메소드
	 * 
	 * @param product_no
	 * @return Product
	 */
	Product brandSelectOne(int product_no);

	/**
	 * 상품 번호로 secondHand상품 한 개 조회를 위한 메소드
	 * 
	 * @param product_no
	 * @return Product
	 */
	Product secondHandSelectOne(int product_no);

	// =========Product Update (Update)=========

	/**
	 * brand상품 수정하는 메소드
	 * 
	 * @param Product
	 * @return int
	 */
	int brandUpdate(Product product);

	/**
	 * secondHand상품 수정하는 메소드
	 * 
	 * @param Product
	 * @return int
	 */
	int secondHandUpdate(Product product);

	// =========Product Delete( Delete )========

	/**
	 * 상품번호로 brand상품 삭제하는 메소드
	 * 
	 * @param product_no
	 * @return int
	 */
	int brandDelete(int product_no);

	/**
	 * 상품번호로 secondHand상품 삭제하는 메소드
	 * 
	 * @param product_no
	 * @return int
	 */
	int secondHandDelete(int product_no);

	

}