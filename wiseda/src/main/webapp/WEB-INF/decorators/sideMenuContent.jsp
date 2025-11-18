<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">
<!--
	$(document).ready(function(){
		//url에 따라서 메뉴 번호를 가져와서 해당 서브메뉴만 보여준다.
		var menubbsid = $('input[name=bbsId]').eq(0).val();
		//alert(menubbsid);
		var result = chkbbsmenu(menubbsid);
		//chkbbsmenu(menubbsid)
		$('div.left').hide().eq(result[0]).show();
		
		$('ul.left_menu').eq(result[0]).find('li').removeClass();
		$('ul.left_menu').eq(result[0]).find('li').eq(result[1]).addClass('left_menu_select');
// 		alert(result[2]);
		$('div.location').html("<img src='<c:url value="/img/location_home.gif"/>' alt='home'> &gt; " + result[2]);
// 		$('ul.left_menu').eq(result[0]).find('li').eq(result[1]).find('a').text();
// 		$('#scmjob').addClass('left_menu_link');
		
		//프로그램 담당자 변경....
		$("#scmjob").click(function(){
// 			alert('scmjob');
// 	        window.open("http://10.1.60.58/login2.asp");
			programMove();
		});
		
	});

//형상관리이동
function programMove() {
	
	var url = "http://10.1.60.58/login2.asp";	
    OpenWindow(url,"w","600px","650px","true");
}

/* 	function findmenuseq(){
		var urlpath = $(location).attr('href');
		var menuseq = -1;
		
		if (urlpath.indexOf("/portal/dqms") > -1) {
			menuseq = 0;
		} else if (urlpath.indexOf("/portal/policy") > -1) {
			menuseq = 1;
		} else if (urlpath.indexOf("/portal/devguide") > -1) {
			menuseq = 2;
		} else if (urlpath.indexOf("/portal/myjob") > -1) {
			menuseq = 3;
		} else if (urlpath.indexOf("/portal/dashboard") > -1) {
			menuseq = 4;
		} else if (urlpath.indexOf("/portal/community") > -1) {
			menuseq = 5;
		}
		
		return menuseq
	} */
//-->
</script>

    	<!-- 왼쪽 메뉴 시작 -->
        <div class="left" style="display: none;">
        	<div class="left_tit"><img src='<c:url value="/img/left_tit_01.gif" />' alt="<s:message code='QLTY.MNG' />"></div> <!-- 품질관리 -->
            <ul class="left_menu">
                <li class="left_menu_select"><a href='<c:url value="/portal/dqms/IntroCtrl.do"/>'><s:message code="QLTY.MNG.SYST" /></a></li> <!-- 품질관리체계 -->
                <li><a href='<c:url value="/portal/dqms/SystemCtrl.do"/>'><s:message code="SYS.PRES" /></a></li> <!-- 시스템 현황 -->
                <li><a href='<c:url value="/portal/dqms/ProcessCtrl.do"/>'><s:message code="BZWR.PROC" /></a></li> <!-- 업무 프로세스 -->
            </ul>
        </div>
        <div class="left" style="display: none;">
        	<div class="left_tit"><img src='<c:url value="/img/left_tit_02.gif" />' alt="<s:message code='PLCY.GUIDE' />"></div> <!-- 정책/지침 -->
            <ul class="left_menu">
                <li class="left_menu_select"><a href='<c:url value="/portal/policy/RawCtrl.do"/>'><s:message code="LAW.NTIF" /></a></li> <!-- 법령/고시 -->
	            <li><a href='<c:url value="/portal/policy/GuideCtrl.do"/>'><s:message code="GUIDE.MANUL" /></a></li> <!-- 지침/매뉴얼 -->
	            <li><a href='<c:url value="/portal/policy/Iso9001Ctrl.do"/>'><s:message code="ISO9001.QLTY.DOCU" /></a></li> <!-- ISO9001 품질문서 -->
            </ul>
        </div>
        <div class="left" style="display: none;">
        	<div class="left_tit"><img src='<c:url value="/img/left_tit_03.gif" />' alt="<s:message code='DEV.GUIDE' />"></div> <!-- 개발가이드 -->
            <ul class="left_menu">
	            <li class="left_menu_select"><a href='<c:url value="/portal/devguide/devguide.do"/>'><s:message code="DEV.GUIDE" /></a></li> <!-- 개발가이드 -->
	            <li><a href='<c:url value="/portal/devguide/reportlist.do"/>'><s:message code="CALC.MNG" /></a></li> <!-- 산출물관리 -->
	            <li><a href='<c:url value="/portal/devguide/board.do"/>'><s:message code="DTA.WIT" /></a></li> <!-- 자료실 -->
            </ul>
        </div>
        <div class="left" style="display: none;">
        	<div class="left_tit"><img src='<c:url value="/img/left_tit_04.gif" />' alt="<s:message code='MY.BZWR' />"></div> <!-- 나의업무 -->
            <ul class="left_menu">
                <li class="left_menu_select"><a href='<c:url value="/portal/myjob/request.do"/>'><s:message code="REG.DEMD.INQ" /></a></li> <!-- 등록요청 조회 -->
                <li><a href='<c:url value="/portal/myjob/apprReq.do"/>'><s:message code="APRL.TRGT.INQ" /></a></li> <!-- 결재대상 조회 -->
                <li><a href='<c:url value="/portal/myjob/DqJobCtrl.do"/>'><s:message code="QLTY.ACVT.INQ" /></a></li> <!-- 품질활동 조회 -->
                <li><a href='<c:url value="/portal/myjob/OwnShipCtrl.do"/>'><s:message code="TBL.CHG.R.CHG" /></a></li> <!-- 테이블 담당자 변경 -->
                <li id="scmjob" ><a ><s:message code="PGM.CHG.R.CHG" /></a></li><!--  프로그램 담당자 변경 -->
<!--                 <li id="scmjob" class="left_menu_icon_link"><a >프로그램 담당자 변경</a></li> -->
<%--             	<li><a href='<c:url value="/portal/myjob/recentWork.do"/>'>최근작업내역 리스트</a></li> --%>
            </ul>
        </div>
        <div class="left" style="display: none;">
        	<div class="left_tit"><img src='<c:url value="/img/left_tit_05.gif" />' alt="<s:message code='QLTY.MNG.PRES' />"></div> <!-- 품질관리현황 -->
            <ul class="left_menu">
	            <li class="left_menu_select"><a href='<c:url value="/portal/dashboard/TotDashCtrl.do"/>'><s:message code="DATA.SYNE.PRES" /></a></li> <!-- 데이터 종합현황 -->
	            <li><a href='<c:url value="/portal/dashboard/DqDashCtrl.do"/>'><s:message code="DATA.QLTY.PRES" /></a></li> <!-- 데이터 품질현황 -->
	            <li><a href='<c:url value="/portal/dashboard/ProgramDash.do"/>'><s:message code="PGM.HOLD.PRES" /></a></li> <!-- 프로그램 보유현황 -->
            </ul>
        </div>
        <div class="left" style="display: none;">
        	<div class="left_tit"><img src='<c:url value="/img/left_tit_06.gif" />' alt="<s:message code='COMMUNITY' />"></div> <!-- 커뮤니티 -->
            <ul class="left_menu">
	            <li class="left_menu_select"><a href='<c:url value="/portal/community/NoticeCtrl.do"/>'><s:message code="ALAM.YARD" /></a></li> <!-- 알림마당 -->
	            <li><a href='<c:url value="/portal/community/KnowCtrl.do"/>'><s:message code="KNOW.SHARE" /></a></li> <!-- 지식나눔 -->
	            <li><a href='<c:url value="/portal/community/ToctocCtrl.do"/>'><s:message code="EVERYTHING" /><span style="font-weight: bold;color:#00b1d7; "><s:message code="TOKTOK" /></span></a></li> <!-- 아무거나 톡!톡! -->
	            <li><a href='<c:url value="/portal/community/QnaCtrl.do"/>'><s:message code="QNA" /></a></li> <!-- 묻고답하기 -->
            </ul>
        </div>
        <!-- 왼쪽 메뉴 끝 -->
