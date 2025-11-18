<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- <%@ taglib uri="http://www.springframework.org/tags" prefix="s" %> --%>
<%-- <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> --%>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="x-ua-compatible" content="IE=10">
<title>::WISE DA:: <tiles:insertAttribute name="title" ignore="true" /> ::</title>

<%@ include file="headinclude.jsp" %>

<script type="text/javascript">   
 
$(document).ready(function(){   
	
/* 	$('div#mainContent').css({
		'padding' : '5px 10px 40px 40px'
	});
	$('div#footer').css({
		'width' : '100%' ,
		'bottom' : '0' ,
		'position' : 'fixed'
	}); */

});
 
</script>

</head>
<%-- <body align="left" valign="top" width="100%" height="100%" scroll="no" onload="<decorator:getProperty property='body.onload' />"> --%>
<body>
<div class="wrap">
	<!-- 상단 메뉴 --> 
		<tiles:insertAttribute name="header" />

    <!-- 왼쪽에 메뉴가 있는 서브 컨테이너 시작 -->
    <div class="subcont_left">
	<!-- 사이드 메뉴(왼쪽) -->
    	<tiles:insertAttribute name="menu" />
	
	<!-- 메인컨텐츠 -->
		<tiles:insertAttribute name="body" />

        <div class="ht40"><span></span></div><!-- footer와 서브 컨텐츠 사이 하단 여백 -->
    </div>
    <!-- 왼쪽에 메뉴가 있는 서브 컨테이너 끝 -->
	<!-- 페이지 하단 -->
	<tiles:insertAttribute name="footer" />
</div>
</body>
</html>