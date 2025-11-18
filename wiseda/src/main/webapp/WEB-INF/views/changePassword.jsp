<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>


<html>
<head>
<title></title>
<%@ include file="/WEB-INF/decorators/headinclude.jsp" %>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/jsbn.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/rsa.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/prng4.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/rng.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/base64.js" />'></script>

<script type="text/javascript">

$(document).ready(function() {
	
	//로그인 에러체크. 로그인상태가 error일 경우 메세지 출력
	//var loginError = "${loginError}";
	//if (loginError == "error") {
	//	showMsgBox("ERR", "로그인 정보가 존재하지 않습니다.<br>아이디와 비밀번호를 확인해 주세요.");
	//}
	
	//$("#btnLogin").click( function(){ doLogin(); });
	$('#loginId').focus();
			
});


$(document).keypress(function(e) {

});


</script>

</head>

<body>
<div class="login_wrap">
	<div class="login_container">
        <form name="loginFrm" id="loginFrm" method="post">
        <div class="login_cont">
            <div class="login_form">
            	<div class="login_id"><input id="loginId" name="id" type="text" title="<s:message code='ID' />\" value=""></div> <!-- 아이디 -->
                <div class="login_pass"><input id="loginPwd" name="password" type="password" title="<s:message code='PWD' />" value=""></div> <!-- 패스워드 -->
                <div class="login_pass2"><input id="loginPwd2" name="password" type="password" title="<s:message code='PWD' />" value=""></div> <!-- 패스워드 -->
                <input type="hidden" id="rsaPublicKeyModulus" value="${publicKeyModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
                <input type="hidden" id="rsaPublicKeyExponent" value="${publicKeyExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
                <div class="login_bt"><a id="btnLogin" href="#"><img src='<c:url value="/img/login_bt.gif" />' alt="<s:message code='LOIN' />"></a></div> <!-- 로그인 -->
            </div>
        </div>
        </form>
    </div>
</div>

</body>
</html>