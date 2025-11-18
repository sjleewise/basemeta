<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="IE=10" >
<title>::WISE META:: <decorator:title default="<s:message code='META' />" /> ::</title> <!-- 메타 -->

<%@ include file="adminheadinclude.jsp" %>


<script type="text/javascript">   
 
$(document).ready(function(){ 
	
	/* $('div#mainContent').css({
		'padding' : '5px 40px 40px 40px'
	}); */
	$('div#footer').css({
		'width' : '100%' ,
		'bottom' : '0' ,
		'position' : 'fixed'
	});
	
});
 
</script>

<decorator:head/>

</head>
<body>
<div class="wrap">
	<!-- 상단 GNB --> 
	<div id="header" >
		<page:apply-decorator id="metagnb" name="metagnb" />
	</div>
	
	<!-- 	사이드 메뉴 -->
	<div id="menuLeft" >
		<page:apply-decorator id="metaSideMenu" name="metaSideMenu" />
	</div>
	
	<!-- 	컨텐츠 내용 -->
	<div id="mainContent" class="cont">
		<decorator:body />
	</div>

	<!-- 페이지 하단 -->
	<div id="footer">
	<page:apply-decorator id="adminfooter" name="adminfooter"/>
	</div>
</div>
</body>
</html>