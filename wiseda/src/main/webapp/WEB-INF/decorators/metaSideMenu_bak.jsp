<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
 $(document).ready(function(){
	 	$("#menuLeft").css({
	 		'position' : 'fixed',
	 		'z-index' : '1000',
	 		'marginLeft': '-281px'
	 		
	 	});
		$('.nleft_bt_sub').click(function(){
			
// 		    if($(".nleft_bt_sub").hasClass("smenu_bt_bar_in") == true) 
// 			{
				$("#menuLeft").stop().animate({'marginLeft':'10px'},200).animate({'marginLeft': '0px'}, 100, function(){
					
					$("#mainContent").one('click', function(){
						$("#menuLeft").stop().animate({'marginLeft':'-284px'},200).animate({'marginLeft':'-281px'},100);
					});
					
				});
				
			
// 				$(".nleft_bt_sub").removeClass();
// 				$(".nleft_bt_sub").toggleClass("smenu_bt_bar_out");
										
				//var vLeftframe = document.all.leftframe;
				//vLeftframe.style.visibility = "hidden";
// 			} else {
// 				$("#menuLeft").stop().animate({'marginLeft':'-284px'},200).animate({'marginLeft':'-276px'},100);
			
// 				$(".nleft_bt_sub").removeClass();
// 				$(".nleft_bt_sub").toggleClass("smenu_bt_bar_in");
				//var vLeftframe = document.all.leftframe;
				//vLeftframe.style.visibility = "visible";
// 			}
		});
		//마우스 오버가 아닐 경우...
// 		$(".nleft_cont").mouseleave(function(){
// 			$("#menuLeft").stop().animate({'marginLeft':'-284px'},200).animate({'marginLeft':'-281px'},100);
// 		});
		
// 		$('.nleft_bt_sub').mouseenter(function(){
// 			$(this).click();
// 		});
	 
	 $('.side_menu_acco').accordion({
	      collapsible: true, 
// 	      active: false,
	      heightStyle: "content"
	    });
	 
	 //마우스 오버시 이미지 변환 
// 	 imgConvert($('div.smenu_favor_bt a img'));
	 
	 //메뉴 이동....
	 //표준 등록 요청
	 $("#menuStndRqst").click(function(){
// 		location.href="<c:url value="/meta/stnd/stnd_rqst.do" />"; 
		location.href="<c:url value="/meta/stnd/stndtot_rqst.do" />"; 
	 });
	 //사전통합 조회
	 $("#menuStdnLst").click(function(){
			location.href="<c:url value="/meta/stnd/stnd_lst.do" />"; 
		 });
	 //표준단어 조회
	 $("#menuStdWordLst").click(function(){
			location.href="<c:url value="/meta/stnd/stwd_lst.do" />"; 
		 });
	 //표준도메인 조회
	 $("#menuDomainLst").click(function(){
			location.href="<c:url value="/meta/stnd/dmn_lst.do" />"; 
		 });
	 //표준항목 조회
	 $("#menuStItemLst").click(function(){
			location.href="<c:url value="/meta/stnd/sditm_lst.do" />"; 
		 });
	 //변경이력 조회
	 $("#menuStdnHistLst").click(function(){
			location.href="<c:url value="/meta/stnd/althistory_lst.do" />"; 
		 });

	//표준단어 약어생성
	 $("#menuAbbreviated").click(function(){
			location.href="<c:url value="/meta/stnd/stwdabbreviated_lst.do" />"; 
		 });
	//유사어/금지어 관리
	 $("#menuAssonant").click(function(){
			location.href="<c:url value="/meta/stnd/symn_lst.do" />"; 
		 });
	//내 요청목록 조회
	 $("#menuRqstMyList").click(function(){
			location.href="<c:url value="/meta/stnd/rqstmy_lst.do" />"; 
		 });
	//내 결재목록 조회
	 $("#menuRqstToDoList").click(function(){
			location.href="<c:url value="/meta/stnd/rqsttodo_lst.do" />"; 
		 });
	 
	
	 
	 //주제영역 등록
	 $("#menuRegSubjRqst").click(function(){
			location.href="<c:url value="/meta/model/subj_lst.do" />"; 
		 });
	 //주제영역 조회
	 $("#menuSubjLst").click(function(){
			location.href="<c:url value="/meta/model/metasubj_lst.do" />"; 
		 });
	 //물리모델 등록요청
	 $("#menuModelPdmRqst").click(function(){
			location.href="<c:url value="/meta/model/pdmtbl_rqst.do" />"; 
		 });
	 //물리모델 조회
 	 $("#menuModelPdmLst").click(function(){
			location.href="<c:url value="/meta/model/pdmtbl_lst.do" />"; 
		 });
	 //물리모델 변경이력 조회
 	 $("#menuModelPdmHist").click(function(){
			location.href="<c:url value="/meta/model/pdmtblhist_lst.do" />"; 
		 });
	 
	 //모델 vs DB 갭분석
	 $("#menuQltModelGapLst").click(function(){
			location.href="<c:url value="/meta/qlt/qltmodelgap_lst.do" />"; 
		 });
	 
	//DDL 테이블조회
	 $("#menuDDLLst").click(function(){
			location.href="<c:url value="/meta/ddl/ddltbl_lst.do" />"; 
	});

	//DDL 테이블 등록요청
	 $("#menuDDLRqst").click(function(){
			location.href="<c:url value="/meta/ddl/ddltbl_rqst.do" />"; 
	});
	 
	//DBC 테이블조회
	 $("#menuDbcList").click(function(){
			location.href="<c:url value="/meta/dbc/dbctbl_lst.do" />"; 
	});

	

	 
	 
 });
 

</script>
    <!-- 왼쪽서브메뉴 시작 -->
    <div class="nleft" > <!-- left:-281px; -->
    	<div class="nleft_cont" style="z-index: 1100;">
    		<div class="nleft_tit">표준데이터</div>
            <div class="nleft_menu">
<!--             	제이쿼리메뉴 들어가면 됨. -->
				    <div class="m_navi_cont_02" >
				    	<div class="side_menu_acco">
						  <h3>표준 사전</h3>
						  <div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStdnLst" >사전통합 조회</a></div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStdWordLst" >표준단어 조회</a></div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDomainLst" >도메인 조회</a></div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStItemLst" >표준항목 조회</a></div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStdnHistLst" >변경이력 조회</a></div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuAbbreviated">표준단어 약어생성</a></div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuAutoDivision">사전 자동 분할</a></div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="stndSimilarity">사전 유사구성 분석</a></div>
						    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="codeSimilarity">코드 유사구성 분석</a></div>
						    
						  </div>
						  </div>
				    	<div class="side_menu_acco">
						  <h3>등록 요청</h3>
						  <div>
							 <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStndRqst">표준 등록요청</a></div> 
						     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuCodeRqst" >코드 등록요청</a></div> 
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuCodeSendLst">코드 이관요청</a></div> 
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuRqstMyList">내 요청목록 조회</a></div>
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuRqstToDoList">내 결재목록 조회</a></div>
						  </div>
						</div>
				    	<div class="side_menu_acco">
						  <h3>DA 업무</h3>
						  <div>
						     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuAssonant" >유사어/금지어 관리</a></div> 
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDomaingLst">도메인그룹관리</a></div> 
						  </div>
						</div>
				    	<div class="side_menu_acco">
						  <h3>데이터 모델</h3>
						  <div>
						     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuRegSubjRqst" >주제영역 등록</a></div> 
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuSubjLst">주제영역 조회</a></div> 
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuModelPdmRqst" >물리모델 등록요청</a></div>  
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuModelPdmLst" >물리모델 조회</a></div>
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuModelPdmHist" >물리모델 변경이력 조회</a></div>
						  </div>
						</div>
				    	<div class="side_menu_acco">
						  <h3>DDL</h3>
						  <div>
						     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDDLRqst" >DDL테이블 등록</a></div> 
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDDLLst">DDL테이블 조회</a></div> 
						  </div>
						</div>
						<div class="side_menu_acco">
						  <h3>DBC</h3>
						  <div>
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDbcList">DBC테이블 조회</a></div> 
						  </div>
						</div>
				    	<div class="side_menu_acco" style="display: none;">
						  <h3>데이터 현황</h3>
						  <div>
						     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuQltModelGapLst" >모델 vs DB 갭분석</a></div> 
				             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="">수집 DB 조회</a></div>
						  </div>
						</div>
				    </div>
            </div>
        </div>
        <div class="nleft_bt_sub"><img src="<c:url value="/images/nleft_bt_sub.gif"/>" alt="<s:message code='SB.MENU' />"></div> <!-- 서브메뉴 -->
    </div>
    <!-- 왼쪽서브메뉴 끝 -->
