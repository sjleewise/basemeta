<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>


<script type="text/javascript">
$(document).ready(function(){
	
	//전체보기 & 바로가기 링크 위치 조정...
	$.fn.positionallmenu = function( using ) {
	      return this.position({
	        my: "left top",
	        at: "left bottom",
	        of: "ul.gnb_bt",
	        collision: "none",
	        using: using
	      });
	};

	$.fn.positionlink = function( using ) {
	      return this.position({
	  		my: "left top",
			at: "right+10 bottom+10",
			of: "li#allMenu",
			collision: "none",
			using: using
	      });
	};
	
	//전체보기 클릭 이벤트 처리
	$('li#allMenu').click(function(){
		
		//전체보기를 메인메뉴 하단에 보여준다.
		$('div.gnb_all').show().positionallmenu();
	
	});
	
	$('div.gnb_all_close').click(function(){
		$('div.gnb_all').hide();
	});
	
	//바로가기 링크
	$('div.quick').show().positionlink();
	
	//윈도우 리사이즈시 전체메뉴 & 바로가기 링크 위치 재조정...
	$(window).resize(function(){
		$('div.gnb_all').positionallmenu();
		$('div.quick').positionlink();
		
	});
	
	
	selectmainmenu();
	
	
	//로그인 서브메뉴 초기화...
	$(".top_menu_01_01").click(function(){
		    var sub_menu = $(this).next().show().position({
			my: "left top",
            at: "left bottom+2",
            of: $(this).parent()
		});
		    $(document).one( "click", function() {
		    	sub_menu.hide();
	        });
	        return false;
	});
	
	//추가 버튼 메뉴...
	$("ul.login_button_menu").hide().menu({
		//position: { my: "left top", at: "left bottom"},
		create: function( event, ui ) {
			//alert($(".ui-menu").css("width"));
			$(this).css({
				"position" : "absolute",
				"width"    : $('.m_top_right').width(),
				"z-index"  : "1100"
			});
		}
	});
	
	//로그아웃 처리...
	$("#btnLogout").click(function(){
		//로그아웃 하시겠어요???? 확인창...
		var message = "<s:message code="CNF.LOGOUT" arguments="${sessionScope.loginVO.name}" />";
		showMsgBox("CNF", message, logout);
	});
	
	 $.getJSON('<c:url value="/rqstCount.do"/>', function(data){
		if(data ==  null) return;
		$('#rqstCnt').text(data[0]);
		$('#aprvCnt').text(data[1]);
 		
	}); 
	
	
	$("#pMove").click(function(){
		programMove();
	});
	
	$('ul.gnb_bt img').mouseout(function(){
		selectmainmenu();
		
	});
	
	//관리자 페이지 이동....
	$("li#btnAdmin").click(function(){
	    //새창열기 get 방식 
		var url = '<c:url value="/commons/sys/connlog/selectConnStat.do" />';	
		window.open(url, '_blank');
// 	    OpenWindow(url,"_blank","800px","780px","true");
	});
	
});


function selectmainmenu() {
	//최상위 선택메뉴 있을시 해당 메뉴를 마우스오버 효과로 표시한다.
	 var menuseq = chkbbsmenu()[0];
	if (menuseq > -1) {
		var imgsrc = $('ul.gnb_bt img').eq(menuseq).attr('src');
//		alert(imgsrc);
		if (imgsrc.indexOf('_.gif') == -1) {
			$('ul.gnb_bt img').eq(menuseq).attr('src', imgsrc.replace('.gif', '_.gif'));
		}
		
	}
}

function logout(){
	location.href="<c:url value="/logout" />";
} 

//형상관리이동
function programMove() {
 	
	/* 새창열기 post 방식
	var idx = $(".btn_subForm").index(this);
    var url = getRequestUrl($('form[name="subForm"] input[name="formId"]').eq(idx).val(),idx);	
	var popUp = OpenWindow(url,"w","1024px","768px","true");
 	$('form[name="subForm"]').eq(idx).attr('action', url);
	$('form[name="subForm"]').eq(idx).attr("target", popUp);
	$('form[name="subForm"]').eq(idx).submit();
    */
	
    //새창열기 get 방식 
	var url = "http://10.1.60.58/login2.asp";	
    OpenWindow(url,"w","600px","650px","true");
}








</script>

	<!-- top 시작 -->
	<div class="top">
    	<h1 class="top_logo"><a href='<c:url value="/"/>'><img src='<c:url value="/images/gnb/m_top_wiseda.png"/>' alt="WISEDA"></a></h1>
        <ul class="top_info">
        	<li><span class="top_info_user">${sessionScope.loginVO.name}</span>님 안녕하세요</li>
            <li><a href="<c:url value="/portal/myjob/request.do?requestStatus=2"/>">등록요청 <span class="top_info_num" id="rqstCnt"></span>건</a></li>
            <li><a href="<c:url value="/portal/myjob/apprReq.do"/>">결재대상 <span class="top_info_num" id="aprvCnt"></span>건</a></li>
            <li id="btnLogout"><a ><s:message code="LOG.OUT" /></a></li> <!-- 로그아웃 -->
            <c:if test="${sessionScope.loginVO.isAdminYn == 'Y' }">
            <li id="btnAdmin"><a>관리자메뉴</a></li>
			</c:if>
            <li>&nbsp;</li>
        </ul>
    </div>
    <!-- top 끝 -->
    
    <!-- gnb 시작 -->
    <div class="gnb">
        <ul class="gnb_bt">
            <li><a href='<c:url value="/portal/dqms/IntroCtrl.do"/>'><img src='<c:url value="/img/gnb_01.gif"/>' onMouseOver="this.src='<c:url value="/img/gnb_01_.gif"/>'" onMouseOut="this.src='<c:url value="/img/gnb_01.gif" />'" alt="품질관리"></a></li>
            <li><a href='<c:url value="/portal/policy/RawCtrl.do"/>'><img src='<c:url value="/img/gnb_02.gif"/>' onMouseOver="this.src='<c:url value="/img/gnb_02_.gif"/>'" onMouseOut="this.src='<c:url value="/img/gnb_02.gif" />'" alt="정책/지침"></a></li>
            <li><a href='<c:url value="/portal/devguide/devguide.do"/>'><img src='<c:url value="/img/gnb_03.gif"/>' onMouseOver="this.src='<c:url value="/img/gnb_03_.gif"/>'" onMouseOut="this.src='<c:url value="/img/gnb_03.gif"/>'" alt="개발가이드"></a></li>
            <li><a href='<c:url value="/portal/myjob/request.do"/>'><img src='<c:url value="/img/gnb_04.gif"/>' onMouseOver="this.src='<c:url value="/img/gnb_04_.gif"/>'" onMouseOut="this.src='<c:url value="/img/gnb_04.gif"/>'" alt="나의업무"></a></li>
            <li><a href='<c:url value="/portal/dashboard/TotDashCtrl.do"/>'><img src='<c:url value="/img/gnb_05.gif"/>' onMouseOver="this.src='<c:url value="/img/gnb_05_.gif"/>'" onMouseOut="this.src='<c:url value="/img/gnb_05.gif"/>'" alt="품질관리현황"></a></li>
            <li><a href='<c:url value="/portal/community/NoticeCtrl.do"/>'><img src='<c:url value="/img/gnb_06.gif"/>' onMouseOver="this.src='<c:url value="/img/gnb_06_.gif"/>'" onMouseOut="this.src='<c:url value="/img/gnb_06.gif"/>'" alt="커뮤니티"></a></li>
            <li id="allMenu"><a ><img src='<c:url value="/img/gnb_07_.gif" />' alt="전체보기"></a></li>
        </ul>
    </div>
    <!-- gnb 끝 -->

    <!-- 전체보기 시작 -->
<div class="gnb_all" style="position:absolute; top:110px; left:0px; z-index:10; display:none;">
    <div class="gnb_all_top">
        <div class="gnb_all_tit">전체보기</div>
        <div class="gnb_all_close"><a href="#"><img src='<c:url value="/img/gnb_all_close.gif" />' alt="닫기"></a></div>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit">품질관리</div>
        <ul class="gnb_all_menu_list">
            <li><a href='<c:url value="/portal/dqms/IntroCtrl.do"/>'>품질관리체계</a></li>
            <li><a href='<c:url value="/portal/dqms/SystemCtrl.do"/>'>시스템 현황</a></li>
            <li><a href='<c:url value="/portal/dqms/ProcessCtrl.do"/>'>업무 프로세스</a></li>
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit">정책/지침</div>
        <ul class="gnb_all_menu_list">
            <li><a href='<c:url value="/portal/policy/RawCtrl.do"/>'>법령/고시</a></li>
            <li><a href='<c:url value="/portal/policy/GuideCtrl.do"/>'>지침/매뉴얼</a></li>
            <li><a href='<c:url value="/portal/policy/Iso9001Ctrl.do"/>'>ISO9001 품질문서</a></li>
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit">개발가이드</div>
        <ul class="gnb_all_menu_list">
            <li><a href='<c:url value="/portal/devguide/devguide.do"/>'>개발가이드</a></li>
            <li><a href='<c:url value="/portal/devguide/reportlist.do"/>'>산출물관리</a></li>
            <li><a href='<c:url value="/portal/devguide/board.do"/>'>자료실</a></li>
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit">나의업무</div>
        <ul class="gnb_all_menu_list">
            <li><a href='<c:url value="/portal/myjob/request.do"/>'>등록요청 조회</a></li>
            <li><a href='<c:url value="/portal/myjob/apprReq.do"/>'>결재대상 조회</a></li>
            <li><a href='<c:url value="/portal/myjob/DqJobCtrl.do"/>'>품질활동 조회</a></li>
            <li><a href='<c:url value="/portal/myjob/OwnShipCtrl.do"/>'>테이블 담당자 변경</a></li>
            <li><a id="pMove">프로그램 담당자 변경</a></li>
<%--             <li><a href='<c:url value="/portal/myjob/ScmJobCtrl.do"/>'>프로그램 담당자 변경</a> <img src='<c:url value="/img/left_menu_icon_link.gif" />' alt=""></li> --%>
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit">품질관리현황</div>
        <ul class="gnb_all_menu_list">
            <li><a href='<c:url value="/portal/dashboard/TotDashCtrl.do"/>'>데이터 종합현황</a></li>
            <li><a href='<c:url value="/portal/dashboard/DqDashCtrl.do"/>'>데이터 품질현황</a></li>
            <li><a href='<c:url value="/portal/dashboard/ProgramDash.do"/>'>프로그램 보유현황</a></li>
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit">커뮤니티</div>
        <ul class="gnb_all_menu_list">
            <li><a href='<c:url value="/portal/community/NoticeCtrl.do"/>'>알림마당</a></li>
            <li><a href='<c:url value="/portal/community/KnowCtrl.do"/>'>지식나눔</a></li>
            <li><a href='<c:url value="/portal/community/ToctocCtrl.do"/>'>아무거나<span style="font-weight: bold;color:#00b1d7; ">톡!톡!</span></a></li>
            <li><a href='<c:url value="/portal/community/QnaCtrl.do"/>'>묻고답하기</a></li>
        </ul>
    </div>
</div>
<!-- 전체보기 끝 -->

<!-- Quick Link 시작 -->
<div class="quick" style="position:absolute; top:110px; left:1010px; display: none;">
    <div><img src='<c:url value="/img/quick_tit.gif"/>' alt="Quick Link"></div>
    <ul class="quick_list">
        <li><a href="<%=WiseConfig.URL_META %>" target="_blank"><img src='<c:url value="/img/quick_bt_01.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_01_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_01.gif" />'" alt="메타데이터"></a></li>
        <li><a href="<%=WiseConfig.URL_DQ %>" target="_blank"><img src='<c:url value="/img/quick_bt_02.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_02_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_02.gif" />'" alt="데이터품질"></a></li>
        <li><a href="<%=WiseConfig.URL_ENCAP %>" target="_blank"><img src='<c:url value="/img/quick_bt_03.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_03_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_03.gif" />'" alt="영향도분석"></a></li>
        <li><a href="<%=WiseConfig.URL_SVN %>" target="_blank"><img src='<c:url value="/img/quick_bt_04.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_04_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_04.gif" />'" alt="SW형상관리"></a></li>
<%--         <li><a href="" target="_blank"><img src='<c:url value="/img/quick_bt_05.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_05_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_05.gif" />'" alt="분배관리"></a></li> --%>
<%--         <li><a href="" target="_blank"><img src='<c:url value="/img/quick_bt_06.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_06_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_06.gif" />'" alt="IT인재관리"></a></li> --%>
    </ul>
</div>
<!-- Quick Link 끝 -->