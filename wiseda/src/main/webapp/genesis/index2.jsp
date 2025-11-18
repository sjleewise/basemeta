<%@ page isELIgnored="false" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.genesis.mmart.test.*" %>

<%
	String w_db_name = "";
	if(System.getProperty("os.name").toLowerCase().indexOf("window") > -1){
		w_db_name = "w_db_win.xml";
	}else{
		w_db_name = "w_db_unix.xml";
	}
	
	//String runKill	= request.getParameter("runKill"	);
	//if("Y".equals(runKill)) ERwin.end	(new HashMap(), "jsp"	);
%>
<h5>
* 홈 : <%= System.getProperty("user.home") + System.getProperty("file.separator") %>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
** db.xml : <%= request.getSession().getServletContext().getRealPath("/") +"genesis" + System.getProperty("file.separator") + w_db_name %>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a target="blank" href="_category.jsp">라이브</a>
<a target="blank" href="_model.jsp">모델</a>
<a target="blank" href="_subject.jsp">주제영역</a>

</h5>

<iframe name="iframe_search" src = "r9_search2.jsp" width="1500" height="150"/>
</iframe>

<iframe id="iframe_result" name="iframe_result" src = "_comboEntity.jsp?work=1" width="1500" height="600"/>
</iframe>