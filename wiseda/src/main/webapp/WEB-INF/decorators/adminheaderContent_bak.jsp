<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<c:set var="toplogo"><s:message code="wiseda.site.name" /></c:set>
<script type="text/javascript">
$(window).on('load',function(){
	
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
	
});

function logout(){
	location.href="<c:url value="/logout" />";
} 
</script>

    <!-- 상단메뉴 시작 -->
<div class="m_top">
	<div class="m_top_left">
<c:choose>
	<c:when test="${toplogo == 'SLC'}">
    	<a href="<c:url value="/" />"><img src="<c:url value="/images/logo/logo_slc.png" />" style="padding: 5px; width: 160px; height: 30px;" alt="DA 관리자" /></a>
	</c:when>
	<c:otherwise>
    	<a href="<c:url value="/" />"><img src="<c:url value="/images/gnb/m_top_wisedaadmin.png"/>" alt="WISE DA 관리자" /></a>
	</c:otherwise>
</c:choose>	
<%--     	<img src="<c:url value="/images/gnb/m_top_liteversion.png" />" alt="lite version" /> --%>
    </div>
    <div class="m_top_right" >
        	<div class="top_menu_01_01"><a>${sessionScope.loginVO.name}님<span class="ui-icon ui-icon-triangle-1-s smenu_link"></span></a></div>
        		  <ul class="login_button_menu" id="login_sub_menu">
				    <li id="btnLogout"><a><span class="ui-icon ui-icon-unlocked"></span><s:message code="LOG.OUT" /></a></li> <!-- 로그 아웃 -->
<%-- 				    <li id="btnChgUserInfo"><a><span class="ui-icon ui-icon-contact"></span>내 정보 변경</a></li> --%>
				  </ul>
<!--
    	<ul class="top_menu_01">
        	<li class="top_menu_01_01"><a>홍길동님</a></li>
             <li class="top_menu_01_02"><a href="#">내정보변경</a></li>
            <li class="top_menu_01_03"><a href="#"><s:message code="LOG.OUT" /></a></li> <!-- 로그아웃 -->
            <li class="top_menu_01_04"><a href="#"><s:message code="SITE.MAP" /></a></li> <!-- 사이트맵 -->
            <li class="top_menu_01_05"><a href="#"><s:message code="SYS.MNG" /></a></li> <!-- 시스템관리자 -->
        </ul>
            -->
    </div>
</div>
    <!-- 상단메뉴 끝 -->
<!-- 메뉴 메인 제목 -->
<div class="m_tit">
	<div class="m_tit_tit">
	<c:if test="${REQ_MENU != null }">
		  ${REQ_MENU.menuNm }
		</c:if>
	</div>
    <div class="m_tit_location">
    <c:if test="${REQ_MENU != null }">
		  ${REQ_MENU.fullPath }
		</c:if>
    </div>
</div>
<!-- 메뉴 메인 제목 -->