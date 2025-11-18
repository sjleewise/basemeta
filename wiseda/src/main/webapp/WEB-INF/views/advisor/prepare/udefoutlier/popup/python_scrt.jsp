<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.util.HashMap"%>
<%
	HashMap map = (HashMap)request.getAttribute("codeMap");
    String jspPath = (String)map.get("python_script_path") + "/";
    String fileName = (String)request.getParameter("udfOtlDtcId") + ".py";
    String txtFilePath = jspPath + fileName;
    
    BufferedReader reader = null;
    
    StringBuilder sb = null;
    String line = "";
    
    try{
	    reader = new BufferedReader(new FileReader(txtFilePath));
	    
	    sb = new StringBuilder();
	    
	    while((line = reader.readLine())!= null){
	        sb.append(line+"\n");
	    }
    } catch(Exception e) {
    	sb = new StringBuilder();
    	
    	sb.append("생성된 스크립트가 없습니다.");
    }
%>


<jsp:useBean id="currentTime" class="java.util.Date"/>
<fmt:formatDate value="${currentTime }" pattern="yyyyMMddHHmmss" var="currentTime"/> 

<html>
<title>이상값 검색</title> 

<head>
<script type="text/javascript">

$(document).ready(function() {
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
     	if ("${headerVO.popType}" == "I") {
     		parent.closeLayerPop();
     	} else {
     		window.close();
     	}
     });
});


$(window).on('load',function() {

});


$(window).resize(function(){
  
});


</script>
</head>

<body onsubmit="return;false;">

	
	<!-- 팝업 내용 시작 -->
	<div class="pop_content" id="popCont">
         
		<textarea style="width:100%; height:385px; resize:none;" id="test" name="test" readOnly><%= sb %></textarea>

   </div>
	<!-- 이상값탐지결과 탭 끝 -->
			
</div>

</body>
</html>