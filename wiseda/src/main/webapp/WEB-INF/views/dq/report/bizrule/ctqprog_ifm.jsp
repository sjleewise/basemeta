<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>



<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script> --%>

<script type="text/javascript">

$(document).ready(function() {

	//그리드 초기화 
 	initGrid4();
 	

	//탭 초기화
 	//$( "#tabs" ).tabs();
	

	$("#btnTreeNew").hide();
	
	$("#btnDelete").hide();
	
	//조회
	$("#btnSearch").click(function(){
		$('#bizrule_sel_title').html(null);
		}).show();
	


	createIBChart("progChart", "progChart", {
      width: "auto",
      height: "350px"
    });
});



function initGrid4()
{
    with(grid_sheet4){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.CTQPROG_LST'/>"
                    	,Align:"Center"}
                ];
        //No.|진단대상ID|진단대상명(물리)|진단대상명|중요정보항목 ID|중요정보항목명(LNM)|중요정보항목명|업무규칙수|분석건수|오류건수|품질점수(%)|DPMO|SIGMA

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:200,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Text",   	Width:50,  SaveName:"ctqId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"ctqLnm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:240,  SaveName:"fullPath",   	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:240,  SaveName:"brCnt",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:240,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:240,   SaveName:"erCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:240,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,   SaveName:"dpmo", 	Align:"Right", Edit:0, ColMerge:0, Hidden:1},
                    {Type:"Text",   	Width:240,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        
        InitComboNoMatchText(1, "");
        
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet4);    
    //===========================
   
}




function grid_sheet4_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

	//클릭한 진단대상명의 시작과 끝위치를 구한다.
	var tmpDB = grid_sheet4.GetCellValue(row, "dbConnTrgLnm");
	var strVal = 0;
	var endVal = 0;
	for(var i=0; i<grid_sheet4.SearchRows(); i++) {
		if(grid_sheet4.GetCellValue(i+1, "dbConnTrgLnm") == tmpDB) {
			if(strVal == 0) strVal = (i+1);
		} else {
			if(strVal != 0 && endVal == 0) {
				endVal = i;
				break;
			}
		}
		
		if(i+1 == grid_sheet4.SearchRows()) endVal = (i+1);
	}
	
	//차트데이터 생성
	chartDraw(strVal, endVal);
	
}



function grid_sheet4_OnSearchEnd(code) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공....
	}
	//차트 클리어
// 	progChart.RemoveAll();
	
	if(grid_sheet4.SearchRows() == 0) return;
	
	//첫번째 행 클릭상태로 차트데이터를 생성...
	grid_sheet4_OnClick(1);
	
}

$(window).on('load',function(){
	
	
});

function chartDraw(strVal, endVal)	{
	
	var category = new Array();
	//차트 카테고리값 설정...
	for(var i=strVal; i<= endVal; i++) {
		category.push (grid_sheet4.GetCellValue(i, "ctqLnm"));
	}
	
	progChart.setOptions({
		chart : {
			backgroundColor : "#FFFFFF", //차트 배경색 설정
			type : "column", //차트 Type 설정
			borderColor : "#FFFFFF"
		},
		legend : {
			layout : "vertical", //Legend 모양 설정
			align : "right", //Legend 가로 정렬 설정
			verticalAlign : "center" //Legend 세로 정렬 설정
		},
		
		colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
		
		plotOptions : {
			series : {
				dataLabels : { //시리즈의 데이터 레이블 설정
					enabled : true,
				}
			},
			column : {
				pointPadding : 0.2 // 컬럼간의 간격 설정
			},
		},
		xAxis : {
			categories : category,
			tickInterval : 1, //X축 레이블 간격 설정
			labels : { //X축 레이블 설정
				enabled : true
			},
			title : { //X축 제목 설정
				text : "<s:message code='IMCE.INFO.ITEM.NM'/>" /*중요정보항목명*/

			}
		},
		yAxis : [{
			tickInterval : 20, //Y축 레이블 간격 설정
			min : 0, //Y축 Min값 설정
			max : 100, //Y축 Max값 설정
			title : { //Y축 제목 설정
				text : "<s:message code='QLTY.SCR.PER'/>"/*품질점수(%)*/

			},
			opposite : 0
		},{
			tickInterval : 1.2, //Y축 레이블 간격 설정
			min : 0, //Y축 Min값 설정
			max : 6, //Y축 Max값 설정
			title:{
				text:"<s:message code='SCOLLTMA.PER'/>" /*시그마(%)*/

			},
			opposite : 1
		}
		]});
	
	
	//검색결과가 없을경우 리턴...
	if(grid_sheet4.SearchRows() == 0) return;
	
	
	
	//시리즈 생성
// 	var seriesQuality = progChart.CreateSeries();
// 	var seriesSigma = progChart.CreateSeries();
		
	var seriesQuality = {
		name:"<s:message code='QLTY.SCR'/>",
		type:"spline"
	};/*품질점수*/

	var seriesSigma = {
		name:"<s:message code='SCOLLTMA'/>", /*시그마*/

		type:"column",
		yAxis:1
	};

		
	//시리즈별 데이터 생성
	var pointsQuality = new Array();
	var pointsSigma = new Array();		
	
	for(var i=strVal, j=0; i<=endVal; i++, j++) {
		pointsQuality.push({x:j, y:grid_sheet4.GetCellValue(i, "erRate"), name:grid_sheet4.GetCellText(i, "fullPath")});
		pointsSigma.push({x:j, y:grid_sheet4.GetCellValue(i, "sigma"), name:grid_sheet4.GetCellText(i, "fullPath")});
	}
	
// 	seriesQuality.AddPoints(pointsQuality);
// 	seriesSigma.AddPoints(pointsSigma);
	seriesQuality.data=pointsQuality;
	seriesSigma.data=pointsSigma;
	
// 	progChart.AddSeries(seriesQuality);
// 	progChart.AddSeries(seriesSigma);

	progChart.setOptions({
		series : [seriesQuality, seriesSigma]
	}, {
		 append : true
		,resetData : true
	});
	

	
	progChart.draw();
	
}


</script>



<div style="clear:both; height:5px;"><span></span></div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet4", "100%", "250px");</script>            
</div>

<div style="clear:both; height:20px;"><span></span></div>
<div class="main_chart_01" id="progChart"></div>



