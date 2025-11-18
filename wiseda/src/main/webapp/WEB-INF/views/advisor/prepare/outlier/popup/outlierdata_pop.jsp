<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<jsp:useBean id="currentTime" class="java.util.Date"/>
<fmt:formatDate value="${currentTime }" pattern="yyyyMMddHHmmss" var="currentTime"/> 

<html>
<title><s:message code='ANRM.VAL.SRCH' /></title><!-- 이상값 검색 코드값으로 수정 181017 --> 

<head>
<script type="text/javascript">

$(document).ready(function() {
	//탭 초기화....
	//$( "#tabs" ).tabs(); 
		
	<c:if test="${otlVO.algPnm == 'B'}">
	
		$("#frmSearch #anlVarId").change(function(){
	
			doAction("SearchOutDataOne");
			
			$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>'+$(this).val()+'.png?ver=${currentTime}');
		});
	</c:if>

	$("#tabs").tabs({  
	    select: function(event, ui) {
		    <c:choose>
		    	<c:when test="${otlVO.algPnm == 'B'}">
			    	$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>'+$("#anlVarId").val()+'.png?ver=${currentTime}');
			    	//$("#outlier_img_div img").css("height","500px");
		        </c:when>
		        <c:when test="${otlVO.algPnm == 'R'}">
		        	$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>${otlVO.otlDtcId }.png?ver=${currentTime }');
		        </c:when>
		        <c:otherwise> 
			        $("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>${otlVO.otlDtcId }.png?ver=${currentTime }');
					$("#cor_img_div img").attr("src", '<c:url value="/img/advisor/"/>${otlVO.otlDtcId }_cor.png?ver=${currentTime }');
		        </c:otherwise>
	        </c:choose>
	    }
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
	
	initoutdataGrid();

	if ($("#frmSearch #algPnm").val() == "B") {
		//컬럼을 선택하도록 한다.
		doAction("SearchOutDataOne"); 
		//$("#frmSearch #anlVarId").change();  
	} else if ($("#frmSearch #algPnm").val() == "R") {
		doAction("SearchOutDataMulti");
		$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>${otlVO.otlDtcId }.png?ver=${currentTime }');
	} else {
		doAction("SearchOutDataMulti");
		$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>${otlVO.otlDtcId }.png?ver=${currentTime }');
		$("#cor_img_div img").attr("src", '<c:url value="/img/advisor/"/>${otlVO.otlDtcId }_cor.png?ver=${currentTime }');
	}
	
	//$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	//setibsheight($("#grid_out01"));
	// grid_sheet.SetExtendLastCol(1);    
});


function doAction(sAction)
{
    switch(sAction)
    {
	    /*이상값탐지 단변량 결과 데이터 조회*/
	    case "SearchOutDataOne":
	    	var param = $("#frmSearch").serialize(); 
	    	grid_outdata.DoSearch("<c:url value="/advisor/prepare/outlier/getOutDataOne.do" />", param);
	    	break;
	   	/*이상값탐지 다변량 결과 데이터 조회*/
	    case "SearchOutDataMulti":
	    	var param = $("#frmSearch").serialize();
	    	grid_outdata.DoSearch("<c:url value="/advisor/prepare/outlier/getOutDataMulti.do" />", param);
	    	break;
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

<body onsubmit="return;false;">
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="OTL.DATA.PTRN.INQ"/></div><!--데이터패턴 조회-->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->
</div>
	    <!-- 팝업 타이틀 끝 -->
	
	
	    <!-- 팝업 내용 시작 -->
<div class="pop_content">
         
		<form id="frmSearch" name="frmSearch" method="post" onsubmit="return;false;">
	     <input  type="hidden"  name="daseId" id="daseId" value="${otlVO.daseId }" />
	     <input  type="hidden"  name="otlDtcId" id="otlDtcId" value="${otlVO.otlDtcId }" />
	     <input  type="hidden"  name="otlAlgId" id="otlAlgId" value="${otlVO.otlAlgId }" />
	     <input  type="hidden"  name="dbSchId" id="dbSchId" value="${otlVO.dbSchId }" />
	     <input  type="hidden"  name="dbSchPnm" id="dbSchPnm" value="${otlVO.dbSchPnm }" />
	     <input  type="hidden"  name="algPnm" id="algPnm" value="${otlVO.algPnm }" />
	     <input  type="hidden"  name="objId" id="objId" value="${search.objId}" />
	     <input  type="hidden"  name="objDate" id="objDate" value="${search.objDate}" /> 
	     <input  type="hidden"  name="colNm" id="colNm" value="${search.colNm}" />
		<c:if test="${otlVO.algPnm eq 'B'}">
		<div class="stit"><s:message code="CLMN.CHC3"/></div><!--컬럼분석 상세정보-->	<!-- 컬럼 선택 코드값으로 수정 181017 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<div>
		   <select id="anlVarId" name="anlVarId">
		   <c:forEach var="col" items="${colist}" varStatus="status">
	        	<option value="${col.anlVarId}">${col.colNm1} (${col.colKorNm })</option>
	       </c:forEach>
		   </select>
		</div>
		<div style="clear:both; height:10px;"><span></span></div>
		</c:if>   
		</form>
	
	<!-- 이상값탐지결과 탭처리... -->
	<div id="tabs">
	  <ul>
	  	<li><a href="#tabs-01"><s:message code="OTL.DATA.PTRN.INQ"/></a></li><!--데이터패턴조회-->
		<li><a href="#tabs-02">Outlier Chart</a></li><!--분석결과상세조회-->
		
		<c:if test="${otlVO.algPnm ne 'B' and otlVO.algPnm ne 'R'}">
			<li><a href="#tabs-03"><s:message code="CRLT"/></a></li><!--분석결과상세조회-->	<!-- 상관관계 코드값으로 수정 181017 -->
		</c:if>
	  </ul>  	  
	  <div id="tabs-01">
	  	<%@include file="outlierdata_dtl.jsp" %> 
	  </div>
	  <div id="tabs-02">
	  	<div id="outlier_img_div" > 
	  		<img alt="" src='' style="width: 100%; height: 100%; object-fit: contain;">
	  	</div>
	  </div>
	  <c:if test="${otlVO.algPnm ne 'B' and otlVO.algPnm ne 'R'}">
	  <div id="tabs-03">
	  	<div id="cor_img_div" >
	  		<img alt="" src='' style="width: 100%; height: 100%; object-fit: contain;">
	  	</div>
	  </div>
	  </c:if> 

   </div>
	<!-- 이상값탐지결과 탭 끝 -->
			
</div>

</body>
</html>