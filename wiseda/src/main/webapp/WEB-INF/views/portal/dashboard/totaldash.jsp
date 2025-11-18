<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>데이터 종합현황</title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->
<script type="text/javascript" src='<c:url value="/js/FusionCharts/FusionCharts/FusionCharts.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/FusionCharts/FusionWidgets/FusionCharts.js"/>'></script>

<script type="text/javascript">

$(document).ready(function(){   
	
	getErrChartList();
	getSysdate();
	
});

function getErrChartList() {
	
	var chartUrl = '<c:url value="/swffile/fusionwidgets/AngularGauge.swf"/>';
	var chart = new FusionCharts(chartUrl, "ChartId", "350", "200", "0", "0");
	   chart.setDataURL('<c:url value="/portal/dashboard/ErrChartCtrl.do"/>');
	   chart.render("errChartList");
	
	var chartUrlSub = '<c:url value="/swffile/fusionwidgets/AngularGauge.swf"/>';
	var chartSub = new FusionCharts(chartUrlSub, "ChartId", "350", "200", "0", "0");
	    chartSub.setDataURL('<c:url value="/portal/dashboard/ErrChartSubCtrl.do"/>');
	    chartSub.render("dqErrChartList");   
}

function getSysdate(){
	var curr = new Date();
	var currDate = "기준일 : " + curr.getFullYear()+'년'+(curr.getMonth()+1)+'월'+curr.getDate()+'일';
	
	document.getElementById("currID").innerHTML = currDate;
	document.getElementById("currRightID").innerHTML = currDate;

}

/* 
//차트링크
function chartProcClick() {
	
	showMsgBox("ERR", "chartProcClick");
//	return false;
}
//차트링크
function chartProcSubClick() {
	showMsgBox("ERR", "chartProcSubClick");
//	return false;
}
*/

//천단위 콤마찍기
function commify(n) {

	var reg = /(^[+-]?\d+)(\d{3})/;   // 정규식

	while (reg.test(n)){
	  n = n.replace(reg, '$1' + ',' + '$2');
	}
	document.write(n);

}
	
</script> 
</head>
<body>
     <!-- 오른쪽 내용 시작 -->
     <div class="right">
     	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 품질관리현황 &gt; 데이터 종합현황</div>
         <div class="stit">데이터 종합현황</div>
         <div class="s05_01_01" style="width:250px;">
         	<div class="stit_02">표준데이터  <div id="currID" style="text-align:right;font-weight:normal;"></div> </div>
             <div id="divFom" class="s05_01_01_cont"  style="padding:3px 10px 1px 3px;">
                 <c:forEach var="result" items="${result}">
                    <div class="s05_01_01_item">
                    	<div class="s05_01_01_item_01">${result.cntName}</div>
                        <div class="s05_01_01_item_02"><span>${result.totCnt}</span> 건</div>
                 	</div>                                            
                 </c:forEach>
       		 </div>
       	 </div>	
         <div class="s05_01_03" >
         	<div class="stit_02">데이터모델 <div id="currRightID" style="text-align:right;font-weight:normal;"></div></div>
            	<table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="">
		             <colgroup>
		                 <col style="width:30%;">
		                 <col style="width:15%;">
		                 <col style="width:15%;">
		                 <col style="width:15%;">
		                 <col style="width:15%;">
		             </colgroup>
		           	 <tr class="tr_th">
		             	<th style="height:23px;">개체명</th>
		             	<th>전체총수</th>
		             	<th>신규</th>
		             	<th>변경</th>
		             	<th>삭제</th>
		             </tr>
		             <c:forEach var="upresult" items="${upresult}" varStatus="status">
		             	<c:if test="${status.count % 2 == 0}">
			             <tr class="tr_line" style="cursor:default;">
			                <td>${upresult.termTypeNm}</td>
			                <td>${upresult.curCnt}</td>  
			                <td>${upresult.insCnt}</td>
			                <td>${upresult.updCnt}</td>
			                <td>${upresult.delCnt}</td>
			             </tr>
		             	</c:if>
		             	<c:if test="${status.count % 2 == 1}">
			             <tr style="cursor:default;">
			                <td>${upresult.termTypeNm}</td>
			                <td>${upresult.curCnt}</td>  
			                <td>${upresult.insCnt}</td>
			                <td>${upresult.updCnt}</td>
			                <td>${upresult.delCnt}</td>
			           	 </tr>
		             	</c:if>
		             </c:forEach>
         		</table>
       	 </div>
         <div class="ht20"><span></span></div>
         <div class="s05_01_04">
         	<div class="stit_02">모델 vs DB 일치율</div>
            <div class="s05_chart" id="errChartList"  style="border:0px;"></div>
       	 </div>
         <div class="s05_01_05" style="">
         	<div class="stit_02" style="margin-left: 30px;">데이터 품질 오류율</div>
            <div class="s05_chart" id="dqErrChartList"  style="border:0px;text-align: right; padding-right: 10px;"> </div>
       	 </div>
     </div>
     <!-- 오른쪽 내용 끝 -->   
        
</body>
</html>