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
	 
	 $("#loginConnStat").click(function(){
		location.href="<c:url value="/commons/sys/connlog/selectConnStat.do" />"; 
	 });
	 $("#menuConnStat").click(function(){
			location.href="<c:url value="/commons/sys/menu/selectStatMenu.do" />"; 
		 });
	 $("#menuConnLog").click(function(){
			location.href="<c:url value="/commons/sys/connlog/selectLoginLog.do" />"; 
		 });
	 $("#menuManage").click(function(){
			location.href="<c:url value="/commons/sys/menu/selectMenu.do" />"; 
		 });
	 //메뉴 권한관리...
	 $("#menuAuth").click(function(){
			location.href="<c:url value="/commons/sys/menu/usergmenumap_lst.do" />"; 
		 });
	 $("#menuComCode").click(function(){
		 location.href='<c:url value="/commons/code/codemanager.do" />';
	 });
	 $("#menuComDetailCode").click(function(){
		 location.href='<c:url value="/commons/code/selectComDetailCodeList.do" />';
	 });
	//==================================================================================== 
	 //게시판 관리
	 $("#menuRegSubjRqst").click(function(){
		 location.href='<c:url value="/commons/sys/bbs/BoardMstrList.do" />';
	 });
		//==================================================================================== 
	 
	 
	//==================================================================================== 
	 //DA 관리자 메뉴
	//==================================================================================== 
	//결재그룹 관리
	 $("#aprgCrud").click(function(){
			location.href="<c:url value="/commons/sys/aprg/aprg_lst.do" />"; 
	 });
	//결재자 관리
	 $("#menuApproveUser").click(function(){
			location.href="<c:url value="/commons/damgmt/approve/approveuser_lst.do" />"; 
	 });
	//결재라인 관리
	 $("#menuApproveLine").click(function(){
			location.href="<c:url value="/commons/damgmt/approve/approveline_lst.do" />"; 
	 });
	//요청서별 결재자 관리
	 $("#menuRqstApprove").click(function(){
			location.href="<c:url value="/commons/damgmt/approve/requestapprove_lst.do" />"; 
	 });
	 //시스템 관리
	 $("#menuSystemCrud").click(function(){
			location.href="<c:url value="/commons/damgmt/sysarea/sysarea_lst.do" />"; 
	 });
	 //주제영역 관리
	 $("#menuSubjArea").click(function(){
			location.href="<c:url value="/meta/subjarea/subj_lst.do" />"; 
	 });
	 
	//주제영역 스키마 매핑
	 $("#menuSubjSchMap").click(function(){
			location.href="<c:url value="/commons/damgmt/subjsch/subjschmap_lst.do" />"; 
	 });
	//부서 관리
	 $("#deptCrud").click(function(){
			location.href="<c:url value="/commons/sys/dept/dept_lst.do" />"; 
	 });
	 //도메인그룹 관리
	 $("#dmnGroupCrud").click(function(){
			location.href="<c:url value="/commons/damgmt/dmnginfo/dmng_lst.do" />"; 
		 });
	 //인포타입 관리
	 $("#infoTypeCrud").click(function(){
			location.href="<c:url value="/commons/damgmt/dmnginfo/infotp_lst.do" />"; 
		 });
	 //도메인그룹 인포타입 매핑 관리
	 $("#dmngInfotpMapCrud").click(function(){	
			location.href="<c:url value="/commons/damgmt/dmnginfo/dmngInfotpMap_lst.do" />"; 
		 });
	//DBMS 데이터타입 관리
	 $("#menuDbmsDataType").click(function(){
			location.href="<c:url value="/commons/damgmt/db/dbmsdatatype_lst.do" />"; 
	 });
	//DBMS 데이터타입 변환관리
	 $("#menuDbmsDataTypeMap").click(function(){
			location.href="<c:url value="/commons/damgmt/db/dbmsdatatypemap_lst.do" />"; 
	 });
	
	
		//스케줄 관리
	 $("#scheduleMng").click(function(){
			location.href="<c:url value="/commons/damgmt/schedule/schedule_lst.do" />"; 
	 });
		
	 //스케줄로그 조회
	 $("#menuShdLog").click(function(){
			location.href="<c:url value="/commons/damgmt/schedule/schedulelog_lst.do" />"; 
	 });
	 
	
	
	//==================================================================================== 
	//시스템 관리
	//==================================================================================== 
	 //사용자 그룹 관리
 	 $("#menuUserGroupRqst").click(function(){
		location.href="<c:url value="/commons/user/usergroup_lst.do" />"; 
	 });
	 //사용자 관리
 	 $("#menuUserRqst").click(function(){
		location.href="<c:url value="/commons/user/user_lst.do" />"; 
	 });
 	//프로그램 관리
	 $("#projectMang").click(function(){
		 location.href='<c:url value="/commons/sys/pms/selectProject.do" />';
	 });
	 $("#programMang").click(function(){
		 location.href='<c:url value="/commons/sys/program/selectProgramList.do" />';
	 });
	//DBMS 관리
	 $("#menuDbmsMng").click(function(){
			location.href="<c:url value="/commons/damgmt/db/dbconntrg_lst.do" />"; 
	 });

 });
 

</script>

<div id="menuLeft">

<!-- sub 메뉴 시작 -->
<div class="m_navi" >
	<div class="m_navi_tit">:::::: <span class="m_navi_tit_bold">Admin Menu</span> ::::::</div>
    <div class="m_navi_cont_01" style="display: none;">
   		<div class="smenu_mywork">나의 업무</div>
	       <div>· 요청 <span style="font-weight:bold;"><a href="#">13</a></span>건 <span style="color:#999999;">I</span> 결재 <span style="font-weight:bold;"><a href="#">30</a></span>건 · </div>
	       <div style="clear:both; height:7px;"><span></span></div>
	       <div class="smenu_favor">즐겨찾기</div>
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
		  <h3>접속통계</h3>
		  <div>
			<div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="loginConnStat">사용자접속통계</a></div>
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuConnStat" >메뉴접속통계</a></div>
		    <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuConnLog" >사용자접속이력</a></div>
		  </div>
		  </div>
    	<div class="side_menu_acco">
		  <h3>게시판관리</h3>
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuRegSubjRqst" >게시판생성관리</a></div> 
<!--              <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuSubjLst">게시판사용관리</a></div>  -->
		  </div>
		  </div>
    	<div class="side_menu_acco" style="display: none;">
		  <h3>데이터 현황</h3>
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuQltModelGapLst" >모델 vs DB 갭분석</a></div> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="">수집 DB 조회</a></div>
		  </div>
		  </div>
    	<div class="side_menu_acco">
		  <h3>DA 관리</h3>
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="aprgCrud" >결재그룹 관리</a></div> 
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuApproveUser" >결재자 관리</a></div> 
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuApproveLine" >결재라인 관리</a></div> 
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuRqstApprove" >요청서별 결재자 관리</a></div> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="">권한 관리</a></div> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuSystemCrud" >시스템 관리</a></div> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuSubjArea" >주제영역 관리</a></div>
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuSubjSchMap" >주제영역별 스키마 매핑</a></div>  
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDbmsMng" >DBMS 관리</a></div> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDbmsDataType" >DBMS별 데이터타입 관리</a></div>
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDbmsDataTypeMap" >DBMS별 데이터타입 변환관리</a></div>
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="dmnGroupCrud">도메인그룹 관리</a></div>
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="dmngInfotpMapCrud">도메인그룹 인포타입 매핑관리</a></div>
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="infoTypeCrud">인포타입 관리</a></div>
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuShdLog" >스케줄로그 조회</a></div> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="scheduleMng">스케줄 관리</a></div>
             
		  </div>
		</div>
    	<div class="side_menu_acco">
		  <h3>시스템관리</h3>
		  <div>
		     <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuUserGroupRqst" >사용자그룹 등록</a></div> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuUserRqst" >사용자 등록</a></div> 
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="deptCrud">부서 관리</a></div>
			 <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="projectMang" >프로젝트 관리</a></div> 
			 <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="programMang" >프로그램 관리</a></div> 
			 <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuManage" >메뉴 관리</a></div>
			 <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuAuth" >메뉴권한 관리</a></div> 
<!--              <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="">권한 관리</a></div>  -->
<!--              <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuSystemCrud" >시스템 관리</a></div>  -->
<!--              <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuDbmsCrud" >DBMS 관리</a></div>  -->
             <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuComCode">공통코드 관리</a></div>
<!--              <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="menuComDetailCode">공통상세코드 관리</a></div> -->
<!--              <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="dmnGroupCrud">도메인그룹 관리</a></div> -->
<!--              <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="dmngInfotpMapCrud"">도메인그룹 인포타입 매핑관리</a></div> -->
<!--              <div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span><a id="infoTypeCrud">인포타입 관리</a></div> -->
             
		  </div>
		</div>
    </div>
</div>
    <div class="smenu_bt_bar" id="divSideBar">
        <div class="smenu_bt_bar_in" id="divSideBarCont"><a >&nbsp;</a></div>
    </div>
</div>