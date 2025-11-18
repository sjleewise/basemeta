<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<c:set var="toplogo"><s:message code="wiseda.site.name" /></c:set>

<html>
<head>
<title></title>

<%-- <script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/jsbn.js" />'></script> --%>
<%-- <script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/rsa.js" />'></script> --%>
<%-- <script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/prng4.js" />'></script> --%>
<%-- <script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/rng.js" />'></script> --%>
<%-- <script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/base64.js" />'></script> --%>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/aes.js" />'></script>
<script language="JavaScript" type="text/javascript" src='<c:url value="/js/security/sha256.js" />'></script>

<script type="text/javascript">
// alert("${toplogo}");
$(document).ready(function() {
	
	//로그인 에러체크. 로그인상태가 error일 경우 메세지 출력
	var loginError = "${loginError}";
	if (loginError == "error") {
		showMsgBox("ERR", "<s:message code='MSG.LOIN.INFO.EXIS' /><br><s:message code='MSG.ID.PWD.CNFR' />"); /* 로그인 정보가 존재하지 않습니다. */ /* 아이디와 비밀번호를 확인해 주세요. */
	} else if(loginError == "iderror") {
		showMsgBox("ERR", "ID가 존재하지 않습니다. <br> ID를 확인해주세요.");
	} else if(loginError == "pwerror") {
		showMsgBox("ERR", "비밀번호가 맞지 않습니다. <br> 5회 실패시 ID가 정지됩니다.");
	} else if(loginError == "lockerror") {
		showMsgBox("ERR", "정지된 아이디입니다. <br> 관리자에게 문의하세요.");
	}
	
	$("#btnLogin").click( function(){ doLogin(); });

	$("#btnchgPwd").click( function(){ doChangePassword(); });
	
	$('#loginId').focus();
			
});


$(document).keypress(function(e) {
	  if(e.which == 13) {
	    // enter pressed
//       alert("you pressed enter key");
		  doLogin();
	  }
	});


	function encode64(str){
	 return encode(escape(str));
	}
	 
	var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	 
 	function encode(input){
	 var output = "";
	 var chr1, chr2, chr3;
	 var enc1, enc2, enc3, enc4;
	 var i =0;
	 do{
	  chr1 = input.charCodeAt(i++);
	  chr2 = input.charCodeAt(i++);
	  chr3 = input.charCodeAt(i++);
	  enc1 = chr1 >> 2;
	  enc2 = ((chr1 & 3) << 4) | (chr2 >> 4 );
	  enc3 = ((chr2 & 15) << 2) | (chr3 >> 6 );
	  enc4 = chr3 & 63;
	  if(isNaN(chr2)){
	   enc3 = enc4 =64;
	  }else if(isNaN(chr3)){
	   enc4 = 64;
	  }
	  output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
	 }while(i<input.length);
	 
	 return output;
	} 
	 
	/* 참고하세요
	    function encode(input){   
	        var output = "";    
	        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;    
	        var i = 0;    
	   
	        input = utf8_encode(input);    
	   
	        while (i < input.length) {    
	   
	            chr1 = input.charCodeAt(i++);    
	            chr2 = input.charCodeAt(i++);    
	            chr3 = input.charCodeAt(i++);    
	   
	            enc1 = chr1 >> 2;    
	            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);    
	            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);    
	            enc4 = chr3 & 63;    
	   
	            if (isNaN(chr2)) {    
	                enc3 = enc4 = 64;    
	            } else if (isNaN(chr3)) {    
	                enc4 = 64;    
	            }    
// 	            output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);    
	            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);    
	           }    
	           return output;    
	    }
	   function utf8_encode(string) {    
	        string = string.replace(/\r\n/g,"\n");    
	        var utftext = "";    
	   
	        for (var n = 0; n < string.length; n++) 
	  {    
	            var c = string.charCodeAt(n);    
	   
	            if (c < 128) {    
	                utftext += String.fromCharCode(c);    
	            }else if((c > 127) && (c < 2048)) {    
	                utftext += String.fromCharCode((c >> 6) | 192);    
	                utftext += String.fromCharCode((c & 63) | 128);    
	            }else {    
	                utftext += String.fromCharCode((c >> 12) | 224);    
	                utftext += String.fromCharCode(((c >> 6) & 63) | 128);    
	                utftext += String.fromCharCode((c & 63) | 128);    
	            }    
	        }    
	        return utftext;    
	    }
// 	*/

function doLogin(){
	
    if($("#loginId").val() == '') {
        alert("<s:message code="MSG.ID.INPT" />"); /* 아이디를 입력하세요 */
        return false;
    }
    if($("#loginPwd").val() == '') {
        alert("<s:message code="MSG.PWD.INPT" />"); /* 비밀번호를 입력하세요 */
        return false;
    }
    
//     var rsaPublicKeyModulus = $("#rsaPublicKeyModulus").val();
//     var rsaPublicKeyExponent = $("#rsaPublicKeyExponent").val();
    
//     var rsa = new RSAKey();
//     rsa.setPublic(rsaPublicKeyModulus, rsaPublicKeyExponent);

    // 사용자ID와 비밀번호를 RSA로 암호화한다.
//     var securedUsername = rsa.encrypt($('#loginId').val());
//     var securedPassword = rsa.encrypt($('#loginPwd').val());
//     var securedUsername = encode64($('#loginId').val());
//     var securedPassword = encode64($('#loginPwd').val());

      var securedUsername = CryptoJS.AES.encrypt($('#loginId').val() , "${sessionScope.__rsaPrivateKey__}").toString();
      var securedPassword = CryptoJS.AES.encrypt($('#loginPwd').val(), "${sessionScope.__rsaPrivateKey__}").toString();
      
    // POST 로그인 폼에 값을 설정하고 발행(submit) 한다.
    /* var securedLoginForm = document.getElementById(“securedLoginForm”);
    securedLoginForm.securedUsername.value = securedUsername;
    securedLoginForm.securedPassword.value = securedPassword; */
//     linebrk(res, 64)
    $('#securedUsername').val(securedUsername);
    $('#securedPassword').val(securedPassword);
//     alert($('#securedUsername').val());
//     alert($('#securedPassword').val());
    
    $("#secureFrm").attr("action", "<c:url value='/login.do'/>").submit();
//     $("#loginFrm").attr("action", "<c:url value='/login.do'/>").submit();
    
    return true;
	
}


function doChangePassword(){
	
   // var url = "/changePassword.do";
    var param = $("#loginFrm").serialize();
    OpenWindow("<c:url value='/changePassword.do' />","changePassword", 400,300,param);
	
    return true;
	
}
</script>

</head>

<body>
<div class="lg_wrap">
<c:choose>
    <c:when test="${sessionScope.mainSite == 'DQ'}">
    	<div class="lg_logo"><img src='<c:url value="/images/logo_dq.png" />' alt="WISE DA LOGIN"></div>
	</c:when>
	<c:when test="${sessionScope.mainSite == 'META'}">
    	<div class="lg_logo"><img src='<c:url value="/images/logo_meta.png" />' alt="WISE DA LOGIN"></div>
	</c:when>
	<c:otherwise>
    	<div class="lg_logo"><img src='<c:url value="/images/logo_meta.png" />' alt="WISE DA LOGIN"></div>
	</c:otherwise>
</c:choose>    	
    	
<%--         <form name="loginFrm" id="loginFrm" method="post">
        <div class="login_cont">
        	<div class="login_tit"><img src='<c:url value="/img/login_tit.gif" />' alt="MemberLogin 사용자 아이디와 비밀번호가 타인에게 노출되지 않도록 유의하시기 바랍니다."></div>
            <div class="login_form">
            	<div class="login_id"><input id="loginId" name="id" type="text" title="아이디" value=""></div>
                <div class="login_pass"><input id="loginPwd" name="password" type="password" title="패스워드" value=""></div>
                <input type="hidden" id="rsaPublicKeyModulus" value="${publicKeyModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
                <input type="hidden" id="rsaPublicKeyExponent" value="${publicKeyExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
                <div class="login_bt"><a id="btnLogin" href="#"><img src='<c:url value="/img/login_bt.gif" />' alt="로그인"></a></div>
            
                <!-- <div class="login_bt2"><a id="btnchgPwd" href="#"><img src='<c:url value="/img/pwChg.gif" />' alt="패스워드변경"></a></div> -->
            </div>
        </div>
        </form> --%>

    <div class="lg_cont">
    	<div class="lg_cont_01"></div>
        <div class="lg_cont_02">
        	<div class="lg_tit">MEMBER LOGIN</div>
<!--         	<div class="lg_tit">메타데이터 관리시스템</div> -->
            <div class="lg_login">
    <form name="loginFrm" id="loginFrm" method="post">
                <ul class="lg_form">
                    <li>
                        <span><s:message code="ID" /></span> <!-- 아이디 -->
                        <input id="loginId" name="id" type="text" title="<s:message code='ID' />" value=""> <!-- 아이디 -->
                    </li>
                    <li>
                        <span><s:message code="PWD" /></span> <!-- 비밀번호 -->
                        <input id="loginPwd" name="password" type="password" title="<s:message code='PWD' />" value=""> <!-- 패스워드 -->
                    </li>
                </ul>
                <input type="hidden" id="rsaPublicKeyModulus" value="${publicKeyModulus}" /><!-- 서버에서 전달한값을 셋팅한다. -->
                <input type="hidden" id="rsaPublicKeyExponent" value="${publicKeyExponent}" /><!-- 서버에서 전달한값을 셋팅한다. -->
                
                                
    </form>    
                <div class="lg_bt_login"><button id="btnLogin"><s:message code="LOIN" /></button></div> <!-- 로그인 -->
            </div>
            <ul class="lg_bt_list" style="display: none;">
            	<li><a id="btnchgPwd" href="#"><s:message code="PWD.CHG" /></a></li> <!-- 비밀번호 변경 -->
                <li><a href="#"><s:message code="PWD" /> <s:message code="INON" /></a></li> <!-- 비밀번호 초기화 -->
            </ul>
        </div>
    </div>
        
        
	<div class="lg_foot">
		<c:choose>
		    <c:when test="${sessionScope.mainSite == 'DQ'}">
		    	<div class="lg_foot_tit"><s:message code="DQ.USE.NOTI"/></div> <!-- 데이터품질관리시스템  이용관련 안내 -->
			</c:when>
			<c:when test="${sessionScope.mainSite == 'META'}">
		    	<div class="lg_foot_tit"><s:message code="META.USE.NOTI"/></div> <!-- 메타관리시스템  이용관련 안내 -->
			</c:when>
			<c:otherwise>
		    	<div class="lg_foot_tit"><s:message code="META.USE.NOTI"/></div> <!-- 메타관리시스템  이용관련 안내 -->
			</c:otherwise>
		</c:choose>
	    <div class="lg_foot_cont">
	    	<ul>
	        	<li><s:message code="MSG.LOGIN.PWD.INIT"/></li> <!-- 최초로 로그인시 비밀번호를 초기화 및 변경해야 합니다. -->
<!-- 	            <li>평가단계 변경 시 관련 직원에게 시스템이 자동적으로 지식관리시스템 메일을 발송하여 평가단계를 안내합니다.</li> -->
<!-- 	            <li>직원평가시스템 이용 및 평가절차와 관련한 사항 등은 오진하 과장(0000), 정혜림 조사역(0000)에게 전화 또는 메일로 문의해주시기 바랍니다.</li> -->
	        </ul>
	    </div>
	</div>
</div>

<form id="secureFrm" name="secureFrm">
	<input type="hidden" name="securedUsername" id="securedUsername" /> 	
	<input type="hidden" name="securedPassword" id="securedPassword" /> 	
</form>

</body>
</html>