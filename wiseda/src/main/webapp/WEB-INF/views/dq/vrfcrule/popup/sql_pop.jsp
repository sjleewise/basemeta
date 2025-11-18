<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="ANLY.SQL.INQ"/></title><!--분석SQL 조회-->


<script type="text/javascript">

$(document).ready(function() {
	var param = "&prfId="+"${prfId}";
	//SQL조회
	loadDetail(param);
});

$(window).on('load',function() {
});


$(window).resize(
);


function doAction(sAction)
{
    switch(sAction)
    {
    }       
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/profile/ajaxgrid/sql_dtl.do"/>', param, function(){
		setTabByPrfKndCd();
	});
}

</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="ANLY.SQL.INQ"/></div><!--분석SQL 조회-->

    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
		<div class="stit"><s:message code="ANLY.SQL" /></div><!--분석SQL-->

		<div style="clear:both; height:5px;"><span></span></div>
    	<div id="detailInfo"><%@include file="sql_dtl.jsp" %></div>
	</div>
</div>
</body>
</html>