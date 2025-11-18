<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title>이상값 검색</title> 

<head>
<script type="text/javascript">

$(document).ready(function() {
	//탭 초기화....
	//$( "#tabs" ).tabs(); 
	
	/* <c:if test="${otlVO.algPnm == 'B'}">
	$("#frmSearch #anlVarId").change(function(){

		doAction("SearchOutDataOne");
		
		$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>'+$(this).val()+'.png?ver=${currentTime}');
	});
	</c:if> */
	
	var files = new Image();
	files.src = '<c:url value="/img/advisor/"/>${search.udfOtlDtcId }_cor.png?ver=${currentTime }';
	
	files.onload=function(){
	 //파일 존재 시 내용
		$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>${search.udfOtlDtcId }.png?ver=${currentTime }');
    	$("#cor_img_div img").attr("src", '<c:url value="/img/advisor/"/>${search.udfOtlDtcId }_cor.png?ver=${currentTime }');
	} 
	files.onerror=function(){
	 //파일 없을 시 내용 
		$("#tab3").hide();
		$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>${search.udfOtlDtcId }.png?ver=${currentTime }');
	}

	/* $("#tabs").tabs({  
	    select: function(event, ui) {
		    <c:choose>
		    	<c:when test="${otlVO.algPnm == 'B'}">
			    	$("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>'+$("#anlVarId").val()+'.png?ver=${currentTime}');
		        </c:when>
		        <c:otherwise> 
			        $("#outlier_img_div img").attr("src", '<c:url value="/img/advisor/"/>${search.udfOtlDtcId }.png?ver=${currentTime }');
					$("#cor_img_div img").attr("src", '<c:url value="/img/advisor/"/>${search.udfOtlDtcId }_cor.png?ver=${currentTime }');
		        </c:otherwise>
	        </c:choose>
	        
	    	
	    }
	}); */
	
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
	
	doAction("SearchResultData");
});


$(window).resize(function(){ 
});


function doAction(sAction)
{
    switch(sAction)
    {
	    case "SearchResultData":
	    	var param = $("#frmSearch").serialize(); 
	    	grid_outdata.DoSearch("<c:url value="/advisor/prepare/udefoutlier/getResultData.do" />", param);
	    	break;

		/*이상값탐지 단변량 결과 데이터 조회*/
	    /* case "SearchOutDataOne":
	    	var param = $("#frmSearch").serialize(); 
	    	grid_outdata.DoSearch("<c:url value="/advisor/prepare/outlier/getOutDataOne.do" />", param);
	    	break; */
	   	/*이상값탐지 다변량 결과 데이터 조회*/
	    /* case "SearchOutDataMulti":
	    	var param = $("#frmSearch").serialize();
	    	grid_outdata.DoSearch("<c:url value="/advisor/prepare/outlier/getOutDataMulti.do" />", param);
	    	break; */
    }       
}

//상세정보호출
/* function loadDetail( tab ) {
	var param =  $("#frmSearch").serialize();
	//분석결과상세조회	
	if(tab == "ANA"){
		$('div#anaresdtl').load('<c:url value="/dq/report/ajaxgrid/getAnaResDtl.do"/>', param, function(){});
	}
	//컬럼분석 결과 조회
	else if(tab == "COL"){
		$('div#colanaresdtl').load('<c:url value="/dq/report/ajaxgrid/getColAnaResDtl.do"/>', param, function(){});
	}
} */
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
	     <input  type="hidden"  name="udfOtlDtcId" id="udfOtlDtcId" value="${search.udfOtlDtcId }" />
	     <input  type="hidden"  name="colNm" id="colNm" value="${search.colNm}" />
	     
	     <%-- <input  type="hidden"  name="daseId" id="daseId" value="${otlVO.daseId }" />
	     <input  type="hidden"  name="otlAlgId" id="otlAlgId" value="${otlVO.otlAlgId }" />
	     <input  type="hidden"  name="dbSchId" id="dbSchId" value="${otlVO.dbSchId }" />
	     <input  type="hidden"  name="dbSchPnm" id="dbSchPnm" value="${otlVO.dbSchPnm }" />
	     <input  type="hidden"  name="algPnm" id="algPnm" value="${otlVO.algPnm }" /> --%>
	     <input  type="hidden"  name="objId" id="objId" value="${search.objId}" />
	     <input  type="hidden"  name="objDate" id="objDate" value="${search.objDate}" />
		
		<c:if test="${otlVO.algPnm eq 'B'}">
		<div class="stit">컬럼 선택</div><!--컬럼분석 상세정보-->
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
	  	<li id='tab2'><a href="#tabs-02">Chart</a></li><!--분석결과상세조회-->
		<li id='tab3'><a href="#tabs-03">상관관계</a></li><!--분석결과상세조회-->
	  </ul>
	  
	  <div id="tabs-01">
	  	<%@include file="result_view_dtl.jsp" %> 
	  </div> 
	  <div id="tabs-02">
	  	<div id="outlier_img_div" style="overflow:auto;"> 
	  		<img id="outlier_img" alt="" src='' style="width: 100%; height:100%; object-fit: contain;">
	  	</div>
	  </div>
	  <div id="tabs-03">
	  	<div id="cor_img_div" style="overflow:auto;">
	  		<img id="cor_img" alt="" src='' style="width: 100%; height:100%; object-fit: contain;">
	  	</div>
	  </div>

   </div>
	<!-- 이상값탐지결과 탭 끝 -->
			
</div>

</body>
</html>