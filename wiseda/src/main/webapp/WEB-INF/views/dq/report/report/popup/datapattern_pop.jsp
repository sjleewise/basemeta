<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>

<script type="text/javascript">

$(document).ready(function() {
	//탭 초기화....
	//$( "#tabs" ).tabs();
	
	//업무규칙, 테이블 프로파일 컬럼분석상세 display : none
	if("${search.objGb}".indexOf("PC") == -1 ){
		$("[id$='-03']").css("display", "none"); 
	}
	
	$("#tab-02 a").click(function(){
		loadDetail("ANA");
	});
	
	$("#tab-03 a").click(function(){
		loadDetail("COL");
	});
	
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
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});

function doAction(sAction)
{
    switch(sAction)
    {
    }       
}

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
	<div class="pop_tit" >
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
	    <div class="pop_tit_txt"><s:message code="DATA.PTRN.INQ"/></div><!--데이터패턴 조회-->
	    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
	    <!-- 팝업 타이틀 끝 -->
	
         
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
	
	    <!-- 팝업 내용 시작 -->
	   <div class="pop_content">
			<div id="tabs">
				<ul>
					<li id="tab-01"><a href="#tabs-01"><s:message code="DATA.PTRN.INQ"/></a></li><!--데이터패턴조회-->
					<li id="tab-02"><a href="#tabs-02"><s:message code="ANLY.RSLT.DTL.INQ"/></a></li><!--분석결과상세조회-->
					<li id="tab-03"><a href="#tabs-03"><s:message code="CLMN.ANLY.DTL.INQ"/></a></li><!--컬럼분석상세조회-->

				</ul>
				<div id="tabs-01">
					<div id="dataptrlst"><%@include file="dataptr_lst.jsp" %></div>
					
				</div>
				<div id="tabs-02">
					<div id="anaresdtl"></div>
<%-- 					<%@include file="anares_dtl.jsp" %> --%>
				</div>
				<div id="tabs-03">
					<div id="colanaresdtl"><%@include file="colanares_dtl.jsp" %></div>
				</div>
			</div>
			
		</div>
	</div>

</body>
</html>