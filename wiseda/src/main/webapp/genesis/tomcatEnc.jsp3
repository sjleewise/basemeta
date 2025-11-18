<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "org.apache.commons.dbcp.*" %>
<%
	String work = request.getParameter("work");

%>

<%
	if(!"Y".equals(work) && !"N".equals(work)){
%>

<form action="tomcatEnc.jsp?work=Y" method="post">
	<input type="text" name="word" size="20">
	<input type="submit" value="변환">
</form>

<%
	}else if("Y".equals(work)){
		String word = request.getParameter("word");
		TomcatEnc aes = new TomcatEnc();
		String decr = aes.encrypt(word);
		
%>
<form action="tomcatEnc.jsp?work=Y" method="post">
	<input type="text" name="word" size="20" value=<%=word %>>
	<input type="text" name="decr" size="120" value=<%=decr %>>
</form>
<%		
	} 
%>
