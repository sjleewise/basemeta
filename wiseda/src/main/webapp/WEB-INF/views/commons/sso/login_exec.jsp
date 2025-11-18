<%@ page language="java"%> 
<%@ page contentType="text/html;charset=EUC-KR"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.util.List"%>
<%@ page import="java.io.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="java.security.*"%>
<%@page import="com.initech.eam.nls.TicketV3"%>

<%@ include file="config.jsp" %>
<%!
	// 에러 페이지 URL
		public void throwError(int errorCode, HttpServletRequest request, HttpServletResponse response )
		throws IOException	{
			response.sendRedirect(NLS_ERROR_URL + "?errorCode=" + errorCode);
     	}
	// Provider Add . 2013.10.30. Initech Rocky.
	static {
		try {

			if(Security.getProvider("Initech") == null){
				Security.addProvider(new com.initech.provider.crypto.InitechProvider());
			}
		} catch (Throwable t) { 
			t.printStackTrace();
		}
	}

%>
<%
	List res = null;
	String userid = null;
	String toa = null;

	
	try {
		String ticket = (String) request.getParameter("ticket");
		System.out.println("*================== ticket = "+ticket);
		String savedNonce = (String) session.getAttribute("nexess.nls.resession.nonce");
				
		if (ticket != null) {
			ticket = URLDecoder.decode(ticket);
		}
		
		int firstIndex        = ticket.indexOf("&&");
		int secondeIndex      = ticket.lastIndexOf("&&");
		String encNonce       = ticket.substring(0, firstIndex);
		String encSKIPAndTime = ticket.substring(firstIndex + 2, secondeIndex);
		String encIDAndTOA    = ticket.substring(secondeIndex + 2);

		//System.out.println("### 1 [encNonce]="+encNonce);
		//System.out.println("### 2 [encSKIPAndTime]="+encSKIPAndTime);
		//System.out.println("### 3 [encIDAndTOA]="+encIDAndTOA);
		
		String decNonce = null;
		try {
			decNonce = cutCarriageReturn(TicketV3.decryptNonce(encSKIPAndTime, encNonce));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("티켓검증 실패! 에러 페이지로 이동 필요");
			throwError( 3102, request, response);
		}
	    
		if (decNonce.equals(savedNonce)) {
		    // 티켓에서 ID 와 TOA 를 추출
			res = TicketV3.readIDAndTOA(encSKIPAndTime, encIDAndTOA);
			userid = (String) res.get(0);
			toa = (String) res.get(1);
			System.out.println("*================== [login_exec.jsp]  userid = "+userid);
			session.setAttribute("SSO_ID", userid);	// AP Session에 SSO_ID 등록이 필요 시 사용. 
			//sso id 획득 완료. 업무 페이지로 이동.
			String repiUrl = request.getParameter("rediUrl");
			if(repiUrl == null) {
				response.sendRedirect(AP_MAIN_URL);
			} else {
				String[] rediUrl = repiUrl.split("§");
				response.sendRedirect(rediUrl[0]+"?"+rediUrl[1]);
			}
			
		}else{
			System.out.println("티켓검증 실패! 에러 페이지로 이동 필요");
			throwError( 3102, request, response);
		}
	} catch (Exception e) {
		System.out.println("티켓검증 실패! 에러 페이지로 이동 필요");
		throwError( 3102, request, response);
	}

%>

<%-- <html>
<body>
<b>Nexess Login Agent</b><br>
Login Success<br>
&nbsp&nbsp&nbsp&nbsp userid = [<%=userid%>]<br>
&nbsp&nbsp&nbsp&nbsp toa = [<%=toa%>]<br>
</body>
</html>  --%>
