<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<html>
<head>
<title>Error Page</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/da-import.css"/>"  />

<script type="text/javascript">

$(document).ready(function() {
	
// 	$("#btnLogin").click( function(){ doLogin(); });
		
});


</script>

</head>

<body>
    <div class="error_page">
    	<div class="error_img"><img src="<c:url value="/images/error_img_01.png" />" alt=""></div>
        <div class="error_txt">
            <div class="error_txt01">
                <s:message code="REQ.PAGE" /><br />
                <span><s:message code="NOT.FND" /></span>  <!-- 요청하신 페이지를 <br> --> <!-- 찾을 수 없습니다. -->
            </div>
            <div class="error_txt02">
               <s:message code="VSIT.PAGE.ERR" /> <br>
                <s:message code="PAGE.ADRR.RD" /> <br>
                <s:message code="NOT.FND.PAGE" /> <br><br>
                <s:message code="INP.ADDR" /> <br>
                <s:message code="RE.CONF" /><br><br>
                <s:message code="ERR.CODE" /> : ${errCd}<br>
            </div>
		<!-- 방문하시려는 페이지의 주소가 잘못입력되었거나 <br>
                페이지의 주소가 변경 혹은 삭제되어 <br>
                요청하신 페이지를 찾을 수 없습니다. <br><br>
                입력하신 주소가 정확한지 <br>
                다시 한번 확인해 주시기 바랍니다.<br> -->
            <div class="error_bt"><a href="<c:url value='/'/>"><s:message code="ERR.PAGE.MSG.MAIN.GO" /></a></div><!-- 메인페이지 바로가기 -->
        </div>
    </div>
<div class="login_wrap" style="display: none;">
	<div class="login_container">
<%--     	<div class="login_logo"><img src='<c:url value="/images/gnb/m_top_wiseda.png" />' alt="WISEDA"></div> --%>
		<div class="stit">안내</div>
		<div class="txt_center">
            <img src="<c:url value="/img/page_ban.gif" />" alt="지금은 사용할 수 없습니다.">
        </div>
        
<!--         <div class="login_footer">Copyright(C) 2013 By National Health Insurance. All right reserved.</div> -->
    </div>
</div>

</body>
</html>