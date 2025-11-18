<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%-- <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> -->
<!-- <meta http-equiv="x-ua-compatible" content="IE=10" > -->
<%-- <title>::WISE DA:: <tiles:insertAttribute name="title" ignore="true" /> ::</title> --%>

<%@ include file="adminheadinclude.jsp" %>

<%-- <tiles:insertTemplate template="/WEB-INF/decorators/adminheadinclude.jsp" /> --%>

<!-- </head> -->
<!-- <body> -->
	<!-- 	컨텐츠 내용 -->
	<tiles:insertAttribute name="body" />
<!-- </body> -->
<!-- </html> -->