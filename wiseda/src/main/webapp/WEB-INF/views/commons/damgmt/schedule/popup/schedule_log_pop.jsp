<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">
$(document).ready(function() {
	
});

$(window).on('load',function(event){
	event.preventDefault();
	setTimeout(function(){
		grid_sheet.SetColHidden("ibsCheck"	,1);
		$("#search_div .stit, #frmSearch").css("display", "none");
		$(".divLstBtn").css("display", "none");
		
		$("#shdLnm").val("${shdLnm}");
		doAction("Search");
	});
});

</script>

<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="SCDU.LOG.INQ" /></div> <!-- 스케줄 로그 조회 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
	<div><%@include file="/WEB-INF/views/commons/damgmt/schedule/schedulelog_lst.jsp" %></div>
	</div>
</div>	

<script type="text/javascript">

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		$("#tab-2 a").click();
		grid_sheet_OnClick(1);
		grid_sheet.SetSelectRow(1);
	}
}

</script>