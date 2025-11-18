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
	 
	 //진단대상DBMS 조회 --곽효신
	 $("#menuAnaDbmsRqst").click(function(){
			location.href="<c:url value="/dq/criinfo/anatrg/anadbmsrqst.do" />"; 
	 });
	 //업무영역 조회
	 $("#menuBizAreaList").click(function(){
			location.href="<c:url value="/dq/criinfo/bizarea/bizarea_lst.do" />"; 
	 });
	 //데이터품질지표 조회
	 $("#menuDqiList").click(function(){
			location.href="<c:url value="/dq/criinfo/dqi/dqi_lst.do" />"; 
	 });
	 //중요정보항목 조회
	 $("#menuCtqList").click(function(){
			location.href="<c:url value="/dq/criinfo/ctq/ctq_lst.do" />"; 
	 });
	 
	//업무영역등록요청
	 $("#menuBizAreaRqst").click(function(){
			location.href="<c:url value="/dq/criinfo/bizarea/bizarea_rqst.do" />"; 
	 });
	 //데이터품질지표 등록요청
	 $("#menuDqiRqst").click(function(){
			location.href="<c:url value="/dq/criinfo/dqi/dqi_rqst.do" />"; 
	 });
	 //중요정보항목 등록요청
	 $("#menuCtqRqst").click(function(){
			location.href="<c:url value="/dq/criinfo/ctq/ctq_rqst.do" />"; 
	 });
	
	 //테이블프로파일관리
	 $("#menuTblPrfRqst").click(function(){
			location.href="<c:url value="/dq/profile/tblprf_rqst.do" />"; 
	 });
	 //컬럼프로파일관리
	 $("#menuColPrfRqst").click(function(){
			location.href="<c:url value="/dq/profile/colprf_rqst.do" />"; 
	 });
	 //프로파일조회 
	 $("#menuPrfList").click(function(){
			location.href="<c:url value="/dq/profile/profile_list.do" />"; 
	 });
	 //프로파일 엑셀등록
	 $("#menuPrfExlRqst").click(function(){
			location.href="<c:url value="/dq/profile/prfexl_rqst.do" />"; 
	 });
	 
	 //업무규칙 조회
	 $("#menuBraLst").click(function(){
		 location.href="<c:url value="/dq/bizrule/bizrule_lst.do" />";
	 });
	//업무규칙 등록
	 $("#menuBraRqst").click(function(){
		 location.href="<c:url value="/dq/bizrule/bizrule_rqst.do" />";
	 });
	 

		//스케줄 관리
	 $("#menuShdMng").click(function(){
			location.href="<c:url value="/dq/scheduler/schedule_lst.do" />"; 
	 });
		
	 //스케줄로그 조회
	 $("#menuShdLog").click(function(){
			location.href="<c:url value="/dq/scheduler/schedulelog_lst.do" />"; 
	 });
	 
	 
	//개선계획 조회
	 $("#menuImPlList").click(function(){
			location.href="<c:url value="/dq/impv/impl_lst.do" />"; 
	 });
	//개선결과 조회
	 $("#menuImRslList").click(function(){
			location.href="<c:url value="/dq/impv/imrsl_lst.do" />"; 
	 });
	//개선계획 등록요청
	 $("#menuImPlRqst").click(function(){
			location.href="<c:url value="/dq/impv/impl_rqst.do" />"; 
	 });
	//개선결과 등록요청
	 $("#menuImRslRqst").click(function(){
			location.href="<c:url value="/dq/impv/imrsl_rqst.do" />"; 
	 });
	

 });
 

</script>

<div id="menuLeft">

<!-- sub 메뉴 시작 -->
<div class="m_navi" >
	<div class="m_navi_tit">:::::: <span class="m_navi_tit_bold"><s:message code="BASE.INFO" /></span> ::::::</div> <!-- 기준정보 -->
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
		  <h3><s:message code="BASE.INFO" /></h3> <!-- 기준정보 -->
		  <div>
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuAnaDbmsRqst" ><s:message code="DIAG.TRGT.DB.MS.INQ" /></a></div> <!-- 진단대상DBMS 조회 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuBizAreaList" ><s:message code="BZWR.TRRT.INQ" /></a></div> <!-- 업무영역 조회 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDqiList" ><s:message code="DATA.QLTY.GIPO.INQ" /></a></div> <!-- 데이터품질지표 조회 -->
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuCtqList" ><s:message code="IMCE.INFO.ITEM.INQ" /></a></div> <!-- 중요정보항목 조회 -->
		  </div>
		  </div>
		  <div class="side_menu_acco">
		  <h3><s:message code="REG.DEMD" /></h3> <!-- 등록요청 -->
		  <div>
			 <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuBizAreaRqst"><s:message code="BZWR.TRRT.REG.DEMD" /></a></div> <!-- 업무영역 등록요청 --> 
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDqiRqst" ><s:message code="DATA.QLTY.GIPO.REG.DEMD" /></a></div> <!-- 데이터품질지표 등록요청 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuCtqRqst"><s:message code="IMCE.INFO.ITEM.REG.DEMD" /></a></div> <!-- 중요정보항목 등록요청 --> 
		  </div>
		</div>
    	<div class="side_menu_acco">
		  <h3><s:message code="PROF" /></h3> <!-- 프로파일 -->
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuTblPrfRqst" ><s:message code="TBL.PROF.MNG" /></a></div> <!-- 테이블프로파일 관리 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuColPrfRqst"><s:message code="CLMN.PROF.MNG" /></a></div> <!-- 컬럼프로파일 관리 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuPrfList"><s:message code="PROF.INQ" /></a></div> <!-- 프로파일 조회 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuPrfExlRqst"><s:message code="PROF.EXCL.REG" /></a></div> <!-- 프로파일 엑셀등록 --> 
		  </div>
		</div>
    	<div class="side_menu_acco" s>
		  <h3><s:message code="BZWR.RULE" /></h3> <!-- 업무규칙 -->
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuBraLst" ><s:message code="BZWR.RULE.INQ" /></a></div> <!-- 업무규칙 조회 --> 
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuBraRqst" ><s:message code="BZWR.RULE.REG.DEMD" /></a></div> <!-- 업무규칙 등록요청 -->
		  </div>
		</div>
    	<div class="side_menu_acco" >
		  <h3><s:message code="QLTY.PRES" /></h3> <!-- 품질현황 -->
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="" ><s:message code="PROF.PRES.INQ" /></a></div> <!-- 프로파일 현황조회 --> 
		  </div>
		</div>
    	<div class="side_menu_acco" >
		  <h3><s:message code="IMPV.ACVT" /></h3> <!-- 개선활동 -->
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuImPlList" ><s:message code="IMPV.PLAN.INQ" /></a></div> <!-- 개선계획 조회 --> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuImRslList"><s:message code="IMPV.RSLT.INQ" /></a></div> <!-- 개선결과 조회 -->
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuImPlRqst"><s:message code="IMPV.PLAN.REG.DEMD" /></a></div> <!-- 개선계획 등록요청 -->
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuImRslRqst"><s:message code="IMPV.RSLT.REG.DEMD" /></a></div> <!-- 개선결과 등록요청 -->
		  </div>
		</div>
		<div class="side_menu_acco" >
		  <h3><s:message code="SCDU" /></h3> <!-- 스케줄 --> 
		  <div>
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuShdMng"><s:message code="SCDU.MNG" /></a></div> <!-- 스케줄 관리 -->
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuShdLog" ><s:message code="SCDU.LOG.INQ" /></a></div> <!-- 스케줄로그 조회 --> 
		  </div>
		</div>
    </div>
</div>
    <div class="smenu_bt_bar" id="divSideBar">
        <div class="smenu_bt_bar_in" id="divSideBarCont"><a >&nbsp;</a></div>
    </div>
</div>