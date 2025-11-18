<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <%@ taglib uri="http://www.springframework.org/tags" prefix="s" %> --%>
<%-- <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="IE=10" >
<title>::WISE DA:: <tiles:insertAttribute name="title" ignore="true" /> ::</title>

<%@ include file="adminheadinclude.jsp" %>


<script type="text/javascript">   
 
$(document).ready(function(){ 
	
// 	$('div#mainContent').css({
// 		'padding' : '5px 40px 40px 40px'
// 	});
	$('div#footer').css({
		'width' : '100%' ,
		'bottom' : '0' ,
		'position' : 'fixed'
	});
	
});
 
</script>

</head>
<body>
<div class="wrap">
	<!-- 상단 GNB --> 
	<div id="header" >
		<tiles:insertAttribute name="header" />
	</div>
	
	<!-- 	컨텐츠 내용 -->
	<div class="n4main">
		<tiles:insertAttribute name="body" />
	</div>
	
	<br><br>
	
	<!-- 페이지 하단 -->
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</div>
</body>
</html>

