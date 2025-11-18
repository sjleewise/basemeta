<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.genesis.mmart.action.*" %>

<%
	String w_db_name = "";
	if(System.getProperty("os.name").toLowerCase().indexOf("window") > -1){
		w_db_name = "w_db_win.xml";
	}else{
		w_db_name = "w_db_unix.xml";
	}
	
//	String runKill	= request.getParameter("runKill"	);
//	if("Y".equals(runKill)) ERwin.end	(new HashMap(), "jsp"	);
%>
<h5>


<a target="blank" href="_category.jsp">library</a>
<a target="blank" href="_model.jsp">model</a>
<a target="blank" href="_subject.jsp">subject</a>

</h5>

<iframe name="iframe_search" src = "r9_search.jsp" width="1500" height="150"/>
</iframe>

<iframe id="iframe_result" name="iframe_result" src = "r9_result.jsp?work=1" width="1500" height="600"/>
</iframe>