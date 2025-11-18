<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>
		
<script type="text/javascript">
var sRow = 0;
var lRow = 0;

var window_width = window.innerWidth;
var chart_size = (window_width*67/100);
var line_size = (window_width*1/100);
var pie_size = (window_width*27/100);

$(document).ready(function() {
});

$(window).resize(function(){
	window_width = window.innerWidth;
	chart_size = (window_width*67/100);
	line_size = (window_width*1/100);
	pie_size = (window_width*27/100);
	
	$("#chart_dqi_bar").css({"width":chart_size,"float":"left"});
	$("#lind_div").css({"width":line_size,"float":"left"});
	$("#chart_dqi_pie").css({"width":pie_size,"float":"left"});
});

$(window).on('load',function() {
	//그리드 초기화
	//$(window).resize();
   $("#chart_dqi_bar").css({"width":chart_size,"float":"left"});
   $("#lind_div").css({"width":line_size,"float":"left"});
   $("#chart_dqi_pie").css({"width":pie_size,"float":"left"});
	
	createIBChart("chart_dqi_bar", "chart_dqi_bar", {
      width: "auto",
      height: "250px"
    });
   createIBChart("chart_dqi_pie", "chart_dqi_pie", {
      width: "auto",
      height: "250px"
    });
});

function initDqiGrid() {
//진단대상 테이블 grid
		with(grid_dqi){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.DQI.DTL' />"}
	                      , {Text:"<s:message code='BDQ.HEADER.DQI.DTL2' />", Align:"Center"} ];

            //진단대상id|진단대상명|스키마id|스키마명|상위품질지표명|품질지표id|품질지표명|합계건수|품질점수|오류율(%)|업무규칙|업무규칙|업무규칙|업무규칙|업무규칙|업무규칙|업무규칙|프로파일
            //|프로파일|프로파일|프로파일|프로파일|프로파일|이상값|이상값|이상값|이상값|이상값|이상값
            
            
            //진단대상id|진단대상명|스키마id|스키마명|상위품질지표명|품질지표id|품질지표명|합계건수|품질점수|오류율(%)|분석차수|건수|시그마|분석건수|
            //오류건수|품질점수|오류율(%)|분석차수|건수|분석건수|오류건수|품질점수|오류율(%)|분석차수|건수|분석건수|추정오류건수|품질점수|오류율(%)
            
	        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
	                    {Type:"Text", Width:100, SaveName:"dbConnTrgId", Align:"Left", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Text", Width:100, SaveName:"dbConnTgrLnm", Align:"Left", Edit:0, Hidden:0, ColMerge:1},
	                    {Type:"Text", Width:100, SaveName:"dbSchId", Align:"Left", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Text", Width:100, SaveName:"dbSchPnm", Align:"Left", Edit:0, Hidden:0, ColMerge:1},
	                    
	                    {Type:"Text", Width:100, SaveName:"uppDqiLnm", Align:"Left", Edit:0, Hidden:0, ColMerge:0},
	                    
	                    {Type:"Text", Width:100, SaveName:"dqiId", Align:"Left", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Text", Width:200, SaveName:"dqiLnm", Align:"Left", Edit:0, Hidden:0, ColMerge:0},
	                    
	                    {Type:"Float", Width:120, Format:"#,###", SaveName:"totAnaCnt", Align:"Right", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:150, Format:"###", SaveName:"totNonRate", Align:"Center", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:150, Format:"###\\%", SaveName:"totErrRate", Align:"Center", Edit:0, Hidden:0, ColMerge:0},
	                    
	                    {Type:"Text", Width:100, SaveName:"brAnaDgr", Align:"Center", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Float", Width:80, Format:"#,###", SaveName:"brCnt", Align:"Right", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Text", Width:100, SaveName:"sigma", Align:"Center", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"#,###", SaveName:"brAnaCnt", Align:"Right", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"#,###", SaveName:"brErCnt", Align:"Right", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"###", SaveName:"brNonRate", Align:"Center", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"###\\%", SaveName:"brErrRate", Align:"Center", Edit:0, Hidden:0, ColMerge:0},
	                    
	                    {Type:"Text", Width:100, SaveName:"prfAnaDgr", Align:"Center", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Float", Width:80, Format:"#,###", SaveName:"prfCnt", Align:"Right", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"#,###", SaveName:"prfAnaCnt", Align:"Right", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"#,###", SaveName:"prfErCnt", Align:"Right", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"###", SaveName:"prfNonRate", Align:"Center", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"###\\%", SaveName:"prfErrRate", Align:"Center", Edit:0, Hidden:0, ColMerge:0},
	                    
	                    {Type:"Text", Width:100, SaveName:"mdAnaDgr", Align:"Center", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Float", Width:80, Format:"#,###", SaveName:"mdCnt", Align:"Right", Edit:0, Hidden:1, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"#,###", SaveName:"mdAnaCnt", Align:"Right", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"#,###", SaveName:"mdErCnt", Align:"Right", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"###", SaveName:"mdNonRate", Align:"Center", Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float", Width:100, Format:"###\\%", SaveName:"mdErrRate", Align:"Center", Edit:0, Hidden:0, ColMerge:0}
	                ];
	        
	        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	        InitColumns(cols);
	        
	        FitColWidth();
	        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	        SetExtendLastCol(1);
	        SetMergeSheet(msPrevColumnMerge + msHeaderOnly);
	    }
	    //==시트설정 후 아래에 와야함===
		init_sheet(grid_dqi);    
	    //===========================
	    
	   //그리드 건수 정보 표시 위치
	   //표시하지 않음 - 0
	   //좌측상단 - 1
	   //우측상단 - 2
	   //좌측하단 - 3
	   //우측하단 - 4
	   //grid_dqi.SetCountPosition(0);
}

function grid_dqi_OnSearchEnd() {
	//alert(grid_dqi.GetCountFormat());
	if(grid_dqi.GetDataLastRow() == -1) return;
	
	//draw_bar_chart(1, 1);
	$("#hiddenDbmsId").val(grid_dqi.GetCellValue(2, 'dbConnTrgId'));
	$("#hiddenSchId").val(grid_dqi.GetCellValue(2, 'dbSchId'));
	$("#hiddenDqiId").val(grid_dqi.GetCellValue(2, 'dqiId'));

	draw_bar_chart2(2);
}

function grid_dqi_OnClick(Row, Col) {
	$("#hiddenDbmsId").val(grid_dqi.GetCellValue(Row, 'dbConnTrgId'));
	$("#hiddenSchId").val(grid_dqi.GetCellValue(Row, 'dbSchId'));
	$("#hiddenDqiId").val(grid_dqi.GetCellValue(Row, 'dqiId'));
	
	draw_bar_chart2(Row);
}

function draw_bar_chart2(Row) {
	chart_dqi_bar.removeAll();
	
	
	//각 시리즈별 이름과 타입 설정
	var seriesDqi = toCamel({
		Type:"column",
		Name: "<s:message code='EROR.RATE' />", // 오류율(%)
		Cursor : "pointer",
		YAxis: 1,
	});

	var seriesDqiLine = toCamel({
		Type:"spline",
		Name:"<s:message code='QLTY.SCR' />",		/* 품질점수 */
	});

	var anaDgr = new Array();
	anaDgr.push("<s:message code='BR.STAT' />"    ); /* 업무규칙 */
	anaDgr.push("<s:message code='PROF.STAT' />"   ); /* 프로파일 */
	anaDgr.push("<s:message code='OTLI.NM' />"    ); /* 이상값 */
	
	var initData = toCamel({
		"xAxis": {
			"labels": {
	            "format": "{value}"  /* 차 */
	        },
	        "categories":anaDgr
	    },
	    "yAxis": [{
	        "labels": {
	            "format": '{value}'
	        },
	        "title": {
	            "text": "<s:message code='QLTY.SCR' />"		/* 품질점수 */
	        },
	        "max":100
	    }, {
	        "title": {
	            "text": "<s:message code='EROR.RATE' />",		/* 오류율(%) */
	        },
	        "labels": {
	            "format": '{value}%',
	        },
	        "opposite": true
	    }],
	    "tooltip": {
	        "headerFormat": "",
	        "shared": true
	    }
	});
	
    chart_dqi_bar.setOptions(initData);
    
    //시리즈별 데이터 생성
	var pointsDqi = new Array();
	var pointsDqiLine = new Array();
	
	var brErr  = grid_dqi.GetCellValue(Row, 'brErrRate');
	var brNon  = grid_dqi.GetCellValue(Row, 'brNonRate');
	var prfErr = grid_dqi.GetCellValue(Row, 'prfErrRate');
	var prfNon = grid_dqi.GetCellValue(Row, 'prfNonRate');
	var otlErr = grid_dqi.GetCellValue(Row, 'mdErrRate');
	var otlNon = grid_dqi.GetCellValue(Row, 'mdNonRate');
	
	pointsDqi.push(toCamel({X:0, Y:brErr==''?0:brErr}));
	pointsDqiLine.push(toCamel({X:0, Y:brNon==''?0:brNon}));
	pointsDqi.push(toCamel({X:1, Y:prfErr==''?0:prfErr}));
	pointsDqiLine.push(toCamel({X:1, Y:prfNon==''?0:prfNon}));
	pointsDqi.push(toCamel({X:2, Y:otlErr==''?0:otlErr}));
	pointsDqiLine.push(toCamel({X:2, Y:otlNon==''?0:otlNon}));

	seriesDqi.data = pointsDqi;
	seriesDqiLine.data = pointsDqiLine;

	chart_dqi_bar.addSeries(seriesDqi);
	chart_dqi_bar.addSeries(seriesDqiLine);

	chart_dqi_bar.draw();
	
	drawDqiPie2(Row);
}

function drawDqiPie2(Row) {
	
	chart_dqi_pie.removeAll();
	
	chart_dqi_pie.setOptions(toCamel({
		Chart : {
			BackgroundColor : "#ffffff", //차트 배경색 설정
			Type : "column" //차트 Type 설정
		},
		Legend : {
			Layout : "vertical", //Legend 모양 설정
			Align : "right", //Legend 가로 정렬 설정
			VerticalAlign : "center" //Legend 세로 정렬 설정
		},
		
		Colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
		
		PlotOptions : {
			Pie:{
				InnerSize:20,
				SlicedOffset:20,
				AllowPointSelect:true,
				StartAngle:60
			},
			Series : {
				DataLabels:{
					Enabled:true,
					Align:"center",
					Color : "#333333"
				}
			},
			Column : {
				PointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		XAxis : {
			TickInterval : 1, //X축 레이블 간격 설정
			Labels : { //X축 레이블 설정
				Enabled : false
			}
		},
		YAxis : {
			TickInterval : 30, //Y축 레이블 간격 설정
			Min : 0, //Y축 Min값 설정
			Title : { //Y축 제목 설정
				Text : ""
			}
		}
		
	}));
	

	
	//시리즈 생성 && 각 시리즈별 이름과 타입 설정
	var series 	= toCamel({
		Name:"<s:message code='EROR.RATE' />",		/* 오류율(%) */
		Type:"pie"
	});

	//시리즈별 데이터 생성
	var points = new Array();
	
	var br = parseInt(grid_dqi.GetCellValue(Row, 'brErCnt')==''?0:grid_dqi.GetCellValue(Row, 'brErCnt'));
	var prf = parseInt(grid_dqi.GetCellValue(Row, 'prfErCnt')==''?0:grid_dqi.GetCellValue(Row, 'prfErCnt'));
	var otl = parseInt(grid_dqi.GetCellValue(Row, 'mdErCnt')==''?0:grid_dqi.GetCellValue(Row, 'mdErCnt'));
	var tot = br+prf+otl;
	
	points.push(toCamel({X:0, Y:(br/tot * 100).toFixed(2),  Name:"<s:message code='BR.STAT' />"  , Sliced:0}));
	points.push(toCamel({X:0, Y:(prf/tot * 100).toFixed(2), Name:"<s:message code='PROF.STAT' />", Sliced:0}));
	points.push(toCamel({X:0, Y:(otl/tot * 100).toFixed(2), Name:"<s:message code='OTLI.NM' />"  , Sliced:0}));
	
	series.data = points;

	chart_dqi_pie.addSeries(series);

	chart_dqi_pie.draw();
}
</script>

<div id="grid_01" class="grid_01">
	<script type="text/javascript">createIBSheet("grid_dqi", "99%", "250px");</script>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div style="width:100%;">
	<div id="chart_dqi_bar"></div>
	<div style="width:1%;float:left;"><span></span></div>
	<div id="chart_dqi_pie"></div>
</div>
<div style="clear:both; height:10px;"><span></span></div>
<input type="hidden" id="hiddenDbmsId" name="hiddenDbmsId" />
<input type="hidden" id="hiddenSchId" name="hiddenSchId" />
<input type="hidden" id="hiddenDqiId" name="hiddenDqiId" />
