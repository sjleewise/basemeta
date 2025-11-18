<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />
<div style="clear:both; height:5px;"><span></span></div>
	<div id="profileGrid">
	
		<div id="tblGrid_PT01"><%@include file="../exl/relana_exl.jsp" %></div>

		<div id="tblGrid_PT02"><%@include file="../exl/unqana_exl.jsp" %></div>
		
		<div id="colGrid_PC01"><%@include file="../exl/colana_exl.jsp" %></div>

		<div id="colGrid_PC02"><%@include file="../exl/codeana_exl.jsp" %></div>

		<div id="colGrid_PC03"><%@include file="../exl/dateana_exl.jsp" %></div>

		<div id="colGrid_PC04"><%@include file="../exl/rangeana_exl.jsp" %></div>

		<div id="colGrid_PC05"><%@include file="../exl/patternana_exl.jsp" %></div>

	</div>
<div style="clear:both; height:5px;"><span></span></div>