<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title></title>

<script type="text/javascript">

$(document).ready(function() {
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	//$( "#tabs" ).tabs();
	
	//팝업 닫기
    $("div.pop_tit_close").click(function(){
    	window.close();
    });
	
  //업무규칙, 테이블 프로파일 컬럼분석상세 display : none
	if("${search.objGb}".indexOf("PC") == -1 ){
		$("[id$='-03']").css("display", "none"); 
	}
	
	
  //상세화면 READONLY
//   	$("#frmDetail button").hide();
// 	$("#frmDbDetail button").hide();
// 	$("#frmVrtDetail button").hide();
	$("form").addClass("tb_read");
	$("button").hide();
	$("input").css("width", "99%");	
	$("textarea").attr('readOnly', true);
	$("select").attr('disabled', true);
	
// 	$("#frmDetail").addClass("tb_read");
// 	$("#frmDetail select").attr('disabled', true);
// 	$("#frmDetail textarea").attr('readOnly', true);
	
// 	$("#frmDbDetail").addClass("tb_read");
// 	$("#frmDbDetail select").attr('disabled', true);
// 	$("#frmDbDetail textarea").attr('readOnly', true);
	
// 	$("#frmVrtDetail").addClass("tb_read");
// 	$("#frmVrtDetail select").attr('disabled', true);
// 	$("#frmVrtDetail textarea").attr('readOnly', true);
	
	$("#otherinfo").show();
	
	$("#tab-02 a").click(function(){
		loadDetail("ANA");
	});
	
	$("#tab-03 a").click(function(){
		loadDetail("COL");
	});
    
});

//상세정보호출
function loadDetail( tab ) {
	var param =  $("#frmSearch").serialize();
	//분석결과상세조회	
	if(tab == "ANA"){
		$('div#anaresdtl').load('<c:url value="/dq/report/ajaxgrid/getAnaResDtl.do"/>', param, function(){});
	}
	//컬럼분석 결과 조회
	else if(tab == "COL"){
		$('div#colanaresdtl').load('<c:url value="/dq/report/ajaxgrid/getColAnaResDtl.do"/>', param, function(){});
	}
}

</script>
</head>

<body>

<div class="pop_tit">
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
		<div class="pop_tit_txt"><s:message code="BZWR.RGR.DTL.INQ"/></div><!--업무규칙 상세조회-->
		<div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
		<!-- 팝업 타이틀 끝 -->
		
<!-- 메뉴 메인 제목 -->
		<div style="clear:both; height:5px;"><span></span></div>

<!-- 팝업 내용 시작 -->
		<div class="pop_content">
<!-- 업무규칙 상세 조회 -->
			<div id="bizrule">
			
				<!-- 프로파일 종류별 텝 -->
				<!-- 선택 레코드의 내용을 탭처리... -->
				<div id="tabs">
				  <ul>
				    <li id="tab-1"><a href="#tabs-1"><s:message code="BSIC.INFO"/></a></li><!--기본정보-->
				    <li id="tab-2"><a href="#tabs-2"><s:message code="DIAG.INFO"/></a></li><!--진단정보-->
				    <li id="tab-3"><a href="#tabs-3"><s:message code="COMPARE.VRFC.INFO"/></a></li><!--비교검증정보-->
				    <li id="tab-01"><a href="#tabs-01"><s:message code="DATA.PTRN.INQ"/></a></li><!--데이터패턴조회-->
					<li id="tab-02"><a href="#tabs-02"><s:message code="ANLY.RSLT.DTL.INQ"/></a></li><!--분석결과상세조회-->
					<li id="tab-03"><a href="#tabs-03"><s:message code="CLMN.ANLY.DTL.INQ"/></a></li><!--컬럼분석상세조회-->

				  </ul>
				  <div id="tabs-1">
				 			<%@include file="../bizrule_detail.jsp" %>
				  	</div>
				  <div id="tabs-2">
				  			<%@include file="../bizrule_dbDetail.jsp" %>
				  	</div>
				  <div id="tabs-3">
				  			<%@include file="../bizrule_vrtDetail.jsp" %>
				  	</div>
				  	<div id="tabs-01">
					<div id="dataptrlst"><%@include file="../../report/popup/dataptr_lst.jsp" %></div>
					
					</div>
					<div id="tabs-02">
						<div id="anaresdtl"></div>
	<%-- 					<%@include file="anares_dtl.jsp" %> --%>
					</div>
					<div id="tabs-03">
						<div id="colanaresdtl"><%@include file="../../report/popup/colanares_dtl.jsp" %></div>
					</div>
				  </div>
				  
				 </div>
		</div>
</div>

<!--  데이터패턴용 히든Form... -->
<form id="frmSearch" name="frmSearch" method="post">
	     <input  type="hidden"  name="objId" id="objId" value="${search.objId}" />
	     <input  type="hidden"  name="objIdCol" id="objIdCol" value="${search.objIdCol}" />
	     <input  type="hidden"  name="objResTbl" id="objResTbl" value="${search.objResTbl}" />
	     <input  type="hidden"  name="objErrTbl" id="objErrTbl" value="${search.objErrTbl}" />
	     <input  type="hidden"  name="erDataSnoCol" id="erDataSnoCol" value="${search.erDataSnoCol}" />
	     <input  type="hidden"  name="objDate" id="objDate" value="${search.objDate}" />
	     <input  type="hidden"  name="objGb" id="objGb" value="${search.objGb}" />
	     <input  type="hidden"  name="colNm" id="colNm" value="${search.colNm}" />
		</form>

</body>
</html>