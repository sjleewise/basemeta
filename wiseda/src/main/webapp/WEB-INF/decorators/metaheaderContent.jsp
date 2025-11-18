<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>


<script type="text/javascript">
$(document).ready(function(){
	
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
				"width"    : $('.top_menu_01_01').width(),
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
	
	
	//시스템 관리 페이지 (관리자만 보이도록)
	<c:if test="${sessionScope.loginVO.isAdminYn == 'Y' }">
	$("div.top_menu_01_05").click(function(){
		//관리자 페이지로 이동...
// 		alert("관리자 페이지로 이동합니다.");
		window.open('<c:url value="/commons/sys/connlog/selectConnStat.do" />', '_blank');
// 		opener.location.href = '<c:url value="/meta/stnd/stnd_lst.do" />';
		
	});
	</c:if>
	
});

function logout(){
	location.href="<c:url value="/logout" />";
} 
</script>

    <!-- 상단메뉴 시작 -->
<div class="m_top" style="height: 65px; background-size: contain;">
	<div class="m_top_left" style="margin-top: 15px;">
    	<a href="<c:url value="/" />"><img src="<c:url value="/images/gnb/m_top_wisemeta.png"/>" alt="<s:message code='WISE.META' />" /></a> <!-- 위세메타 -->
<%--     	<img src="<c:url value="/images/gnb/m_top_liteversion.png" />" alt="lite version" /> --%>
    </div>
    <div class="m_top_right" style="margin-top: 5px;">
    	<div style="float: right;">
        	<div class="top_menu_01_01" style="display: inline;"><a>${sessionScope.loginVO.name}<s:message code="SIR" /><span class="ui-icon ui-icon-triangle-1-s smenu_link"></span></a></div> <!-- 님 -->
        		  <ul class="login_button_menu" id="login_sub_menu">
				    <li id="btnLogout"><a><span class="ui-icon ui-icon-unlocked"></span><s:message code="LOG.OUT" /></a></li> <!-- 로그 아웃 -->
				    <li id="btnChgUserInfo"><a><span class="ui-icon ui-icon-contact"></span><s:message code="MY.INFO.CHG" /></a></li> <!-- 내 정보 변경 -->
				  </ul>
			<div class="top_menu_01_04" style="display: inline;"><a><s:message code="SITE.MAP" /></a></div> <!-- 사이트맵 -->
			<c:if test="${sessionScope.loginVO.isAdminYn == 'Y' }">
			<div class="top_menu_01_05" style="display: inline;"><a><s:message code="SYS.MNG" /></a></div> <!-- 시스템관리 -->
			</c:if>
    	</div>
<!--
    	<ul class="top_menu_01">
        	<li class="top_menu_01_01"><a>홍길동님</a></li>
             <li class="top_menu_01_02"><a href="#">내정보변경</a></li>
            <li class="top_menu_01_03"><a href="#">로그아웃</a></li>
            <li class="top_menu_01_04"><a href="#">사이트맵</a></li> 
            <li class="top_menu_01_05"><a href="#">시스템관리자</a></li> 
        </ul>
            -->
     <!-- 메인 메뉴 시작 -->
     <div style="clear: both; float: left;">
     <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.LAYOUT.TBL' />"> <!-- 레이아웃을 위한 테이블입니다. -->
       <tr>
         <td width="7" class="top_bg_01"></td>
         <td class="top_bg_02">
         <ul class="top_menu_02">
             <li class="top_menu_02_01"><a href="#"><s:message code="STRD.DATA" /></a></li> <!-- 표준데이터 -->
             <li class="top_menu_02_02"><a href="#"><s:message code="DATA.MDEL" /></a></li> <!-- 데이터모델 -->
             <li class="top_menu_02_03"><a href="#"><s:message code="DATA.BASE" /></a></li> <!-- 데이터베이스 -->
             <li class="top_menu_02_04"><a href="#"><s:message code="IMPACHART" /></a></li> <!-- 영향도 -->
             <li class="top_menu_02_05"><a href="#"><s:message code="DATA.QLTY" /></a></li> <!-- 데이터품질 -->
             <li class="top_menu_02_06"><a href="#"><s:message code="BZWR.MNG" /></a></li> <!-- 업무관리 -->
         </ul>
         </td>
         <td width="7" class="top_bg_03"></td>
         <td width="5"></td>
       </tr>
     </table>
     </div>
    <!-- 메인 메뉴 끝 -->
    </div>
</div>
    <!-- 상단메뉴 끝 -->
<!-- 메뉴 메인 제목 -->
<div class="m_tit">
	<div class="m_tit_tit"></div>
    <div class="m_tit_location"></div>
</div>
<!-- 메뉴 메인 제목 -->