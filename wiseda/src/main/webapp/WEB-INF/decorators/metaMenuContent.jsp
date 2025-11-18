<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
 $(document).ready(function(){
	 	$("#menuLeft").css({
	 		'position' : 'fixed',
	 		'z-index' : '1000',
	 		'marginLeft': '-276px'
	 		
	 	});
		$('#divSideBar').click(function(){
			
		    if($("#divSideBarCont").hasClass("smenu_bt_bar_in") == true) 
			{
				$("#menuLeft").stop().animate({'marginLeft':'10px'},200).animate({'marginLeft': '0px'}, 100);
			
				$("#divSideBarCont").removeClass();
				$("#divSideBarCont").toggleClass("smenu_bt_bar_out");
										
				//var vLeftframe = document.all.leftframe;
				//vLeftframe.style.visibility = "hidden";
			} else {
				$("#menuLeft").stop().animate({'marginLeft':'-284px'},200).animate({'marginLeft':'-276px'},100);
			
				$("#divSideBarCont").removeClass();
				$("#divSideBarCont").toggleClass("smenu_bt_bar_in");
				//var vLeftframe = document.all.leftframe;
				//vLeftframe.style.visibility = "visible";
			}
		});
		
		$('#divSideBar').mouseenter(function(){
			$(this).click();
		});
	 
	 $('.side_menu_acco').accordion({
	      collapsible: true, 
// 	      active: false,
	      heightStyle: "content"
	    });
	 
	 //마우스 오버시 이미지 변환 
	 imgConvert($('div.smenu_favor_bt a img'));
	 
	 //메뉴 이동....
	 //표준 등록 요청
	 $("#menuStndRqst").click(function(){
// 		location.href="<c:url value="/meta/stnd/stnd_rqst.do" />"; 
		location.href="<c:url value="/meta/stnd/stndnew_rqst.do" />"; 
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
			location.href="<c:url value="/meta/model/subj_lst.do" />"; 
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

<div id="menuLeft">

<!-- sub 메뉴 시작 -->
<div class="m_navi" >
	<div class="m_navi_tit">:::::: <span class="m_navi_tit_bold"><s:message code="STRD.DATA" /></span> ::::::</div> <!-- 표준 데이터 -->
    <div class="m_navi_cont_01" style="display: none;">
   		<div class="smenu_mywork"><s:message code="MY.BZWR" /></div> <!-- 나의 업무 -->
	       <div>· <s:message code="DEMD" /> <span style="font-weight:bold;"><a href="#">13</a></span><s:message code="CNT" /> <span style="color:#999999;">I</span> <s:message code="APRL" /> <span style="font-weight:bold;"><a href="#">30</a></span><s:message code="CNT" /> · </div> <!-- 요청 건 결재 건-->
	       <div style="clear:both; height:7px;"><span></span></div>
	       <div class="smenu_favor"><s:message code="FAVO" /></div> <!-- 즐겨찾기 -->
	       <div class="smenu_favor_bt"><a href="#"><img src='<c:url value="/images/smenu_bt_move.gif" />'  alt="<s:message code='MOVE' />" /></a></div> <!-- 이동 -->
	       <div class="smenu_favor_bt"><a href="#"><img src='<c:url value="/images/smenu_bt_delete.gif"/>'  alt="<s:message code='DEL' />" /></a></div> <!-- 삭제 -->
	       <div class="smenu_favor_bt"><a href="#"><img src='<c:url value="/images/smenu_bt_add.gif"/>'  alt="<s:message code='ADDT' />" /></a></div> <!-- 추가 -->
	       <div>
	           <select id="" class="" name="" style="width:100%;">
	               <option value=""><s:message code="MSG.ADDT.BTN.TAB.REG" /></option> <!-- 추가버튼을 누르면 현재 탭이 등록됩니다. -->
	               <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
	               <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
	           </select>
	       </div>
    </div>
    <div class="m_navi_cont_02" >
    	<div class="side_menu_acco">
		  <h3><s:message code="STRD.BFHD" /></h3> <!-- 표준 사전 -->
		  <div>
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStdnLst" ><s:message code="BFHD.INTG.INQ" /></a></div> <!-- 사전통합 조회 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStdWordLst" ><s:message code="STRD.WORD.INQ" /></a></div> <!-- 표준단어 조회 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDomainLst" ><s:message code="DMN.INQ" /></a></div> <!-- 도메인 조회 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStItemLst" ><s:message code="STRD.TERMS.INQ" /></a></div> <!-- 표준용어 조회 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStdnHistLst" ><s:message code="CHG.HSTR.INQ" /></a></div> <!-- 변경이력 조회 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuAbbreviated"><s:message code="STRD.WORD.ABRV.CRTN" /></a></div> <!-- 표준단어 약어생성 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuAutoDivision"><s:message code="BFHD.ATMD.DIV" /></a></div> <!-- 사전 자동 분할 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="stndSimilarity"><s:message code="BFHD.SIMI.CMPS.ANLY" /></a></div> <!-- 사전 유사구성 분석 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="codeSimilarity"><s:message code="CD.SIMI.CMPS.ANLY" /></a></div> <!-- 코드 유사구성 분석 -->
		    
		  </div>
		  </div>
    	<div class="side_menu_acco">
		  <h3><s:message code="REG.DEMD" /></h3> <!-- 등록 요청 -->
		  <div>
			 <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuStndRqst"><s:message code="STRD.REG.DEMD" /></a></div> <!-- 표준 등록요청 --> 
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuCodeRqst" ><s:message code="CD.REG.DEMD" /></a></div> <!-- 코드 등록요청 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuCodeSendLst"><s:message code="CD.TFCT.DEMD" /></a></div> <!-- 코드 이관요청 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuRqstMyList"><s:message code="MY.DEMD.LST.INQ" /></a></div> <!-- 내 요청목록 조회 -->
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuRqstToDoList"><s:message code="MY.APRL.LST.INQ" /></a></div> <!-- 내 결재목록 조회 -->
		  </div>
		</div>
    	<div class="side_menu_acco">
		  <h3><s:message code="DA.BZWR" /></h3> <!-- DA 업무 -->
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuAssonant" ><s:message code="SIMIWORD.PRHB.WORD.MNG" /></a></div> <!-- 유사어/금지어 관리 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDomaingLst"><s:message code="DMN.GRP.MNG" /></a></div> <!-- 도메인그룹관리 --> 
		  </div>
		</div>
    	<div class="side_menu_acco">
		  <h3><s:message code="DATA.MDEL" /></h3> <!-- 데이터 모델 -->
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuRegSubjRqst" ><s:message code="SUBJ.TRRT.REG" /></a></div> <!-- 주제영역 등록 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuSubjLst"><s:message code="SUBJ.TRRT.INQ" /></a></div> <!-- 주제영역 조회 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuModelPdmRqst" ><s:message code="PHYC.MDEL.REG.DEMD" /></a></div> <!-- 물리모델 등록요청 -->  
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuModelPdmLst" ><s:message code="PHYC.MDEL.INQ" /></a></div> <!-- 물리모델 조회 -->
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuModelPdmHist" ><s:message code="PHYC.MDEL.CHG.HSTR.INQ" /></a></div> <!-- 물리모델 변경이력 조회 -->
		  </div>
		</div>
    	<div class="side_menu_acco">
		  <h3>DDL</h3>
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDDLRqst" ><s:message code="DDL.TBL.REG" /></a></div> <!-- DDL테이블 등록 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDDLLst"><s:message code="DDL.TBL.INQ" /></a></div> <!-- DDL테이블 조회 --> 
		  </div>
		</div>
		<div class="side_menu_acco">
		  <h3>DBC</h3>
		  <div>
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDbcList"><s:message code="DB.C.TBL.INQ" /></a></div> <!-- DBC테이블 조회 --> 
		  </div>
		</div>
    	<div class="side_menu_acco" style="display: none;">
		  <h3><s:message code="DATA.PRES" /></h3> <!-- 데이터 현황 -->
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuQltModelGapLst" ><s:message code="MDEL.VS.DB.GAP.ANLY" /></a></div> <!-- 모델 vs DB 갭분석 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id=""><s:message code="COLLT.DB.INQ" /></a></div> <!-- 수집 DB 조회 -->
		  </div>
		</div>
    </div>
</div>
    <div class="smenu_bt_bar" id="divSideBar">
        <div class="smenu_bt_bar_in" id="divSideBarCont"><a >&nbsp;</a></div>
    </div>
</div>