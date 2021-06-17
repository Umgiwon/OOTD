<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.ootd.ootdApp.product.model.vo.*" %>
<% Product pr = (Product)request.getAttribute("product"); // product(카테고리, 브랜드네임) 받아오기 %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>브랜드리스트</title>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/productList.css">
</head>
<body>

	<c:import url="../common/header.jsp"/>

    <div class="container-List">
        <!-- 스토어 이름 -->
        <header class="list-title">
            <h2 align="center">BRAND STORE</h2>
        </header>

        <!-- 카테고리 사이드바 -->
        <nav class="side-bar">
            <!-- 카테고리 -->
            <div id="category">
                <ul class="category">
                    <!-- 상품별 -->
                    <li id="prod-li">
                        <div class="showArea">
                            <b class="accordionTitle">PRODUCT</b>
                            &nbsp;&nbsp;
                            <!-- 아이콘 -->
                            <span class="accIcon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                                    <path d="M8 0a1 1 0 0 1 1 1v6h6a1 1 0 1 1 0 2H9v6a1 1 0 1 1-2 0V9H1a1 1 0 0 1 0-2h6V1a1 1 0 0 1 1-1z"/>
                                </svg>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-dash-lg" viewBox="0 0 16 16">
                                    <path d="M0 8a1 1 0 0 1 1-1h14a1 1 0 1 1 0 2H1a1 1 0 0 1-1-1z"/>
                                </svg>
                            </span>
                        </div>
       
                        
                        <!-- 클릭 시 보여줄 영역 (Dropdown) -->
                        <div class="dropArea">
                            <ul class="hiddenArea" id="categoryList">
                                <li class="prod cate" id="0">All</li>
                                <li class="prod cate" id="1">Top</li>
                                <li class="prod cate" id="2">bottom</li>
                                <li class="prod cate" id="3">Shoes</li>
                                <li class="prod cate" id="4">Hat</li>
                                <li class="prod cate" id="5">Bag</li>
                            </ul>
                        </div> 
                    </li>
                    <br>
                    <!-- 브랜드별 -->
                    <li id="brand-li">
                        <div class="showArea">
                            <b class="accordionTitle">ALL BRANDS</b>
                            &nbsp;&nbsp;
                            <!-- 아이콘 -->
                            <span class="accIcon">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                                    <path d="M8 0a1 1 0 0 1 1 1v6h6a1 1 0 1 1 0 2H9v6a1 1 0 1 1-2 0V9H1a1 1 0 0 1 0-2h6V1a1 1 0 0 1 1-1z"/>
                                </svg>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-dash-lg" viewBox="0 0 16 16">
                                    <path d="M0 8a1 1 0 0 1 1-1h14a1 1 0 1 1 0 2H1a1 1 0 0 1-1-1z"/>
                                </svg>
                            </span>
                        </div>
                        
                        <!-- 클릭 시 보여줄 영역 (Dropdown) -->
                        <div class="dropArea">
                            <ul class="hiddenArea" id="brandList">
                            	<li class="brand cate">All</li>
                            	<c:forEach items="${ brandName }" var="b">
	                                <li class="brand cate">${ b }</li>
                                </c:forEach>
                            </ul>
                        </div> 
                    </li>
                </ul>
            </div>
        </nav>


        

        <section class="main-wrapper">
            <!-- 필터 정렬 영역 -->
            <div class="filter-div">
                <span class="filter-text">최신순</span>
                <span class="filter-text">낮은 가격순</span>
                <span class="filter-text">높은 가격순</span>
            </div>

            <div class="content-wrapper">
                <!-- 상품 list -->
                <div class="product-list">
                <ul>
                	<c:forEach items="${ list }" var="p">
	                    <li class="thumbnail-list">
	                        <div class="thumbnail-area" id="${ p.product_no }">
	                            <!-- thumbnail image -->
	                            <img src="${pageContext.request.contextPath }/resources/images/${ p.att_name }" alt="">
	                            <!-- 상품 정보 (hover) -->
	                            <span class="thumbnail-info">
	                                <b>${ p.brand_name }</b> <br />
	                                <span>${ p.product_name }</span>
	                                     <br><br>
	                                <span>${ p.product_price }</span>
	                            </span>
	                        </div>
	                    </li>
                    </c:forEach>
                </ul>
            </div>

            <!-- 페이지네이션 (임시) -->
            <!-- <div class="pagination">
                <a href="#">&laquo;</a>
                <a href="#">1</a>
                <a href="#">2</a>
                <a href="#">3</a>
                <a href="#">4</a>
                <a href="#">5</a>
                <a href="#">6</a>
                <a href="#">&raquo;</a>
              </div>
            </div> -->
		<c:out value="${pageBar}" escapeXml="false"/>
            

            
            
        </section>
	    <!-- TOP 버튼 -->
	    <aside>
	        <div id="topBtn" href="#">TOP</div>
	    </aside>        

        
    </div>

    <!-- FOOTER IMPORT -->
   	<%@ include file="../common/footer.jsp" %>
   	
    <script>
    
    
	 	// 카테고리 클릭시 id 값을 가지고 selectList 로 이동 
	 	$( document ).ready( function() {
	 		$('#categoryList').children('.prod').on("click",function(){
				var categoryNo = $(this).attr("id");
				var pType = 1;
				console.log("categoryNo="+categoryNo);
				var bName = "<%= pr.getBrand_name() %>" ;	
				console.log("bName="+bName);
				/* if( bName != null) { */
					location.href = "${pageContext.request.contextPath}/product/productList.do?categoryNo="+categoryNo+"&pType=1&bName="+bName;
				/* } else {
					location.href = "${pageContext.request.contextPath}/product/productList.do?categoryNo="+categoryNo+"&pType=1";	
				}   */
				
			});
	 	});
	 	
	 	
		// 브랜드 클릭시 id 값을 가지고 selectList 로 이동 
	 	$( document ).ready( function() {
	 		$('#brandList').children('.brand').on("click",function(){
				var bName = $(this).text();
				var pType = 1;
				console.log("bName="+bName);	
				var categoryNo = <%= pr.getProduct_category() %> ;	
				console.log("categoryNo="+categoryNo);
				/* if( categoryNo != null) { */
					location.href = "${pageContext.request.contextPath}/product/productList.do?bName="+bName+"&pType=1&categoryNo="+categoryNo;
				/* } else {
					location.href = "${pageContext.request.contextPath}/product/productList.do?bName="+bName+"&pType=1";	
				} */
				
			});
	 	});
    
		
		
		
        // Dropdown 
        $('.showArea').on('click', function() {
            // 숨긴 영역 slide down
            $(this).next('.dropArea').slideToggle(300);

            // +, - 아이콘 바꾸기
            var plus = $(this).children('span').children('.bi-plus-lg');
            var dash = $(this).children('span').children('.bi-dash-lg');

            if ( dash.css('display') == 'none' ) {
                dash.css('display', 'inline-block');
                plus.css('display', 'none');
            } else {
                plus.css('display', 'inline-block');
                dash.css('display', 'none');
            }
        });

        // hover 시 상품정보 보여주기
        $('.thumbnail-area').on({
                'mouseenter' : function() {
                    $(this).children('span').css('display', 'block');
                }, 
                'mouseleave' : function() {
                    $(this).children('span').css('display', 'none');
                }
        });
        
        // click 시 ProductDetail 이동
		$(function(){
			$('.thumbnail-area').on("click",function(){
				var product_no = $(this).attr("id");
				console.log("product_no="+product_no);
				location.href = "${pageContext.request.contextPath}/product/productDetail.do?product_no="+product_no+"&pType=1";
			});
		});
        
        
        
        // filter 클릭 시 밑줄 표기하기
        $('.filter-text').on('click', function() {
            $(this).css('border-bottom', 'solid 2px gray');
            $(this).siblings().css('border-bottom', '');
        });

        // top 버튼
        $(function() {
        $("#topBtn").click(function() {
            $('html, body').animate({
                scrollTop : 0
            }, 400);
            return false;
        });
    });

    // if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
        //document.documentElement.classList.add("dark");
        
    // }

    $('#toggleTheme').on('click', function() {
        $('html').toggleClass("dark");

        var mode = $('html').attr('class');
        localStorage.setItem('darkMode', mode)
        console.log(localStorage);
    });
    </script>
    

</body>

</html>