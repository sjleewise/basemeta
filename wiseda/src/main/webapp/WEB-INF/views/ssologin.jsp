<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java"%> 
<%@page contentType="text/html;charset=EUC-KR"%> 
<%@page import="com.initech.util.Base64Util"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.initech.eam.nls.Ticket"%>
<%@page import="com.initech.eam.nls.CookieManager"%>
<%@page import="java.security.SecureRandom"%>
<%@page import="com.initech.eam.nls.NLSHelper"%>

<%@ include file="commons/sso/config.jsp" %>
<%
	/* 
	  #noCacheNonce : 프록시에서의 캐싱을 방지하기 위한 Cookie와 RequestParameter로 이용됨
	  1) LoginFormPage와 resession.jsp에서 생성하고, 그 이후 페이지에 RequestParameter로 넘겨준다.
	     -> NLS에서 Page이동시 항상 넘기고 받도록 한다.
	  2) LoginFormPage와 resession.jsp에서 이 이름의 Cookie를 생성해주도록 한다.    
	 */
	 
    String noCacheNonce = Ticket.generateNonce();
	  
    // -- resession.nonce 생성
	SecureRandom random = new SecureRandom();
	byte[] nonce = new byte[8];
	random.nextBytes(nonce);
	
	String nonceStr = cutCarriageReturn (new String(Base64Util.encode(nonce))) ;
	
	session.setAttribute("nexess.nls.resession.nonce", nonceStr);
	
%>

<OBJECT ID="NEXESS_API" CLASSID="CLSID:D4F62B67-8BA3-4A8D-94F6-777A015DB612" width=0 height=0></OBJECT>

<script language=javascript>
// 	alert("sso script start");
	try{
		
	var resession = NEXESS_API.PrepareResession("<%=nonceStr%>");
	var ticket = NEXESS_API.GetTicket();
	
	if(ticket.length == 1 || ticket == ""){		
		self.location.replace("<%=NLS_LOGIN_URL%>");
		//로그인 되어있지 않음. NLS 로 replace.
	}else{
		while(ticket.indexOf("+") != -1) {
			ticket= ticket.replace("+", "%2b");
		}
		self.location.replace("<%=ASCP_URL%>?ticket=" + escape(ticket));
	}
	}catch (err ){
// 		alert("sso errror");
		self.location.replace("/nhisportal/loginform.do");
	}

</script>