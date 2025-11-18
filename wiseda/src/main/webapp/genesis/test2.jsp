<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"
		 import="java.util.*"
%>

<script type="text/javascript">
	function deleteModelSvg(){
		var modelName = document.deleteModelSvg.modelName.value;
		document.location.href="/webviewer41/admin/batch/deleteModelSvg.do?modelName="+modelName;
		
	}
</script>
<%
	//String defaultPropertiesPath = "/spring/context-application.xml";

	if(this.getClass().getResource("/") != null) out.println("<br>/ : " + this.getClass().getResource("/").toString());
	if(this.getClass().getResource(".") != null) out.println("<br>. : " + this.getClass().getResource(".").toString());
	if(this.getClass().getResource("")  != null) out.println("<br>'': " + this.getClass().getResource("").toString());
	
%>

