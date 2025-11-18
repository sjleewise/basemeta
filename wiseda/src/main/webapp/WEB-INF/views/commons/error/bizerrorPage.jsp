<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Error Page</title>


<script type="text/javascript">

$(document).ready(function() {
	
// 	$("#btnLogin").click( function(){ doLogin(); });
		
});


</script>

</head>

<body>
<div class="login_wrap">
	<div class="login_container">
<%--     	<div class="login_logo"><img src='<c:url value="/images/gnb/m_top_wiseda.png" />' alt="WISEDA"></div> --%>
		<div class="stit"><s:message code="GUDE" /></div> <!-- 안내 -->
		<div class="txt_center">
            <img src="<c:url value="/img/page_ban.gif" />" alt="<s:message code='MSG.NOT.USE' />"> <!-- 지금은 사용할 수 없습니다. -->
        </div>
        <c:if test="${null ne exception }">
	        <c:if test="${not empty exception.errCode}">
				<div>errcode : ${exception.errCode}</div>
			</c:if>
		 
			<c:if test="${not empty exception.errMsg}">
				<div>errMessage : ${exception.errMsg}</div>
			</c:if>
        </c:if>
        
<!--         <div class="login_footer">Copyright(C) 2013 By National Health Insurance. All right reserved.</div> -->
    </div>
</div>

</body>
</html>