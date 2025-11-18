<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>데이터 품질현황</title>


<script type="text/javascript" src='<c:url value="/js/FusionCharts/FusionCharts/FusionCharts.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/FusionCharts/FusionWidgets/FusionCharts.js"/>'></script>

<script type="text/javascript">

$(document).ready(function(){   
	
	/* 시스템별 데이터 오류율(%) */
	getWidgetsChartList();
	/* 데이터품질 개선활동 진행현황 */
	getPieChartList();

	
	/* 대상업무 selectBox 
	$("#dbmsID").change(function(){
		
		getPieChartList();
	});
    
    분석차수 selectBox 
   $("#exeNum").change(function(){
		
		getPieChartList();
	});
   */ 
 	
});


function getWidgetsChartList() {
	
	var chartUrl = '<c:url value="/swffile/fusioncharts/MSColumn2D.swf"/>';
	var chart = new FusionCharts(chartUrl, "ChartId", "100%", "100%", "0", "0");
	   chart.setDataURL('<c:url value="/portal/dashboard/widgetsChartList.do"/>');
	   chart.render("widgetsChart");
}

function getPieChartList(){
/* 	
    var param =document.getElementById("exeNum").value;
		param += "/"+document.getElementById("dbmsID").value;
     */
    var chartUrl = '<c:url value="/swffile/fusioncharts/Pie2D.swf"/>';
	var chart = new FusionCharts(chartUrl, "ChartId", "100%", "100%", "0", "0");
	   chart.setDataURL('<c:url value="/portal/dashboard/pieChart.do"/>');	
	   chart.render("chartPie");
}

/* 
//차트링크
function widgetsChartClick() {
	showMsgBox("ERR", "widgetsChartClick");
//	return false;
}

//차트링크
function pieChartClick() {
	showMsgBox("ERR", "pieChartClick");
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
        	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 품질관리현황 &gt; 데이터 품질현황</div>
            <div class="stit">데이터 품질현황</div>
            
            <div class="s05_02_01" style="">
            	<div class="stit_02">대상업무별 오류율(%)</div>
                <div class="s05_chart" id="widgetsChart"></div>
          	</div>
            <div class="s05_02_02">
            	<div class="stit_02">대상업무별 측정결과</div>
                <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="">
                <colgroup>
                    <col style="width:31%;">
                    <col style="width:13%;">
                    <col style="width:13%;">
                    <col style="width:15%;">
                    <col style="width:15%;">
                    <col style="width:13%;">
                </colgroup>
                <tr class="tr_th">
                  <th rowspan="2">대상업무</th>
                  <th colspan="2">품질관리대상</th>
                  <th colspan="3">측정결과</th>
                  </tr>
                <tr class="tr_th">
                  <th>테이블</th>
                  <th>컬럼</th>
                  <th>업무규칙</th>
                  <th>오류발생업무규칙</th>
                  <th>오류율</th>
                </tr>
                <c:forEach var="result" items="${result}" varStatus="status">
                <c:if test="${status.count % 2 == 0}">
                <tr style="cursor:default;">
                  <td>${result.bizareaNm}</td>  
                  <td>${result.tblCnt}</td>
                  <td>${result.colCnt}</td>
                  <td>${result.braCnt}</td>
                  <td>${result.errCnt}</td>
                  <td>${result.errRate}</td>
                </tr>
                </c:if>
                <c:if test="${status.count % 2 == 1}">
                <tr class="tr_line" style="cursor:default;">
                  <td>${result.bizareaNm}</td>  
                  <td>${result.tblCnt}</td>
                  <td>${result.colCnt}</td>
                  <td>${result.braCnt}</td>
                  <td>${result.errCnt}</td>
                  <td>${result.errRate}</td>
                </tr>
                </c:if>
                </c:forEach>
              </table>
          	</div>
            <div class="ht20"><span></span></div>
            
            <div class="s05_02_03" style="">
            	<div class="stit_02">대상업무별 진행현황 <!-- <span>I <a href="#">근거문서 바로가기</a></span> --></div>
                <div class="s05_chart" id="chartPie"></div>
          	</div>
            
            <div class="s05_02_04">
            	<div class="stit_02">대상업무별 개선활동 진행현황 
            	<!-- <p class="s05_02_04_radio"><input type="radio" name="s05_02_radio" title="업무팀" checked> 업무팀 <input type="radio" name="s05_02_radio" title="담당자"> 담당자</p> <span>I <a href="#">매뉴얼 다운로드</a></span> -->
            	</div>
                
                <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="">
                  <colgroup>
                      <col style="width:25%;">
                      <col style="width:15%;">
                      <col style="width:15%;">
                      <col style="width:15%;">
                      <col style="width:15%;">
                      <col style="width:15%;">
                  </colgroup>
                  <tr class="tr_th">
                    <th rowspan="2">대상업무</th>
                    <th rowspan="2">업무규칙</th>
                    <th rowspan="2">개선대상</th>
                    <th colspan="2">진행중</th>
                    <th rowspan="2">완료</th>
                  </tr>
                  <tr class="tr_th">
                    <th>원인등록</th>
                    <th>개선안등록</th>
                  </tr>
                  <c:forEach var="resultTeam" items="${resultTeam}" varStatus="status">
                  <c:if test="${status.count % 2 == 0}">
                  <tr style="cursor:default;">
                    <td>${resultTeam.bizareaNm}</td>
                    <td>${resultTeam.braCnt}</td>
                    <td>${resultTeam.nonPrc}</td>
                    <td>${resultTeam.errPrc}</td>
                    <td>${resultTeam.impPrc}</td>
                    <td>${resultTeam.impEnd}</td>
                  </tr>
                  </c:if>
                  <c:if test="${status.count % 2 == 1}">
                  <tr class="tr_line" style="cursor:default;">
                    <td>${resultTeam.bizareaNm}</td>
                    <td>${resultTeam.braCnt}</td>
                    <td>${resultTeam.nonPrc}</td>
                    <td>${resultTeam.errPrc}</td>
                    <td>${resultTeam.impPrc}</td>
                    <td>${resultTeam.impEnd}</td>
                  </tr>
                  </c:if>
                  </c:forEach>
                </table>
            </div>
        </div>
        <!-- 오른쪽 내용 끝 -->
   
</body>
</html>