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
	
	$("#chart_otl_bar").css({"width":chart_size,"float":"left"});
	$("#lind_div").css({"width":line_size,"float":"left"});
	$("#chart_otl_pie").css({"width":pie_size,"float":"left"});
});

$(window).on('load',function() {
	//그리드 초기화
	//$(window).resize();
	
   $("#chart_otl_bar").css({"width":chart_size,"float":"left"});
   $("#lind_div").css({"width":line_size,"float":"left"});
   $("#chart_otl_pie").css({"width":pie_size,"float":"left"});
   
	createIBChart("chart_otl_bar", "chart_otl_bar", {
		width: "auto",
		height: "250px"
	});
	createIBChart("chart_otl_pie", "chart_otl_pie", {
		width: "auto",
		height: "250px"
	});	
});

function initOtlGrid() {
//진단대상 테이블 grid
		with(grid_otl){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        /* 기존소스_bak 181022  */
	        /* var headers = [ {Text:"진단대상ID|진단대상명|스키마ID|스키마명|테이블명|이상값ID|데이터셋ID|이상값알고리즘ID|알고리즘ID|알고리즘한글명|프로파일명|품질지표ID|품질지표논리명|최근분석차수|분석총건수|추정오류건수|오류율(%)|품질점수", Align:"Center"} ]; */
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.OTL.DTL' />", Align:"Center"} ];
			//진단대상ID|진단대상명|스키마ID|스키마명|테이블명|이상값ID|데이터셋ID|이상값알고리즘ID|알고리즘ID|알고리즘한글명|프로파일명|품질지표ID|품질지표논리명|최근분석차수|분석총건수|추정오류건수|오류율(%)|품질점수
	        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
	                    {Type:"Text",   Width:150,                  SaveName:"dbConnTrgId",   Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"dbConnTgrPnm",  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbSchId",	      Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"dbSchPnm",	  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbTblNm",	      Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"otlDtcId",	  Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"daseId",	  	  Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"otlAlgId",	  Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"algId",	  	  Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"algLnm",	  	  Align:"Left",    Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Text",   Width:150,                  SaveName:"otlNm",	  	  Align:"Left",    Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dqiId",	  	  Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"dqiLnm",	  	  Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"anaDgr",	      Align:"Center",  Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"#,###",  SaveName:"anaCnt",	      Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"#,###",  SaveName:"esnErCnt",	  Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"###\\%", SaveName:"errRate",	      Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"###",    SaveName:"nonRate",       Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
	                ];

	        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	        InitColumns(cols);
	        
	        FitColWidth();
	        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	        SetExtendLastCol(1);
	        SetMergeSheet(msPrevColumnMerge);
	    }
	    //==시트설정 후 아래에 와야함===
		init_sheet(grid_otl);    
	    //===========================
}

function grid_otl_OnSearchEnd() {
	if(grid_otl.GetDataLastRow() == -1) return;
	
	$("#hiddenDbmsId4").val(grid_otl.GetCellValue(1, 'dbConnTrgId'));
	$("#hiddenSchId4").val(grid_otl.GetCellValue(1, 'dbSchId'));
	$("#hiddenOtlDtcId").val(grid_otl.GetCellValue(1, 'otlDtcId'));
	$("#hiddenAnaDgr").val(grid_otl.GetCellValue(1, 'anaDgr'));
	
	var param = new Object();
	param.otlDtcId = $("#hiddenOtlDtcId").val();
	
	draw_bar_otl(param);
}

function grid_otl_OnClick(Row, Col) {
	if(Col < 9) return;
	$("#hiddenDbmsId4").val(grid_otl.GetCellValue(Row, 'dbConnTrgId'));
	$("#hiddenSchId4").val(grid_otl.GetCellValue(Row, 'dbSchId'));
	$("#hiddenOtlDtcId").val(grid_otl.GetCellValue(Row, 'otlDtcId'));
	$("#hiddenAnaDgr").val(grid_otl.GetCellValue(Row, 'anaDgr'));
	
	var param = new Object();
	param.otlDtcId = $("#hiddenOtlDtcId").val();
	
	draw_bar_otl(param);
}

function draw_bar_otl(param) {
	$.getJSON('<c:url value="/advisor/prepare/otlquality/getOtlBarData.do"/>', param, function(data){

		if(data ==  null) return;

		chart_otl_bar.removeAll();

		//시리즈 생성
		var seriesOtlCol   = toCamel({
			Type:"column",
			Name:	"<s:message code='EROR.RATE' />" ,/* '오류율(%)' */
			Cursor : "pointer",
			YAxis: 1,
		});

		var seriesOtlSpline   = toCamel({
			Type:"spline",
			Name:"<s:message code='QLTY.SCR' />"	/* '품질점수' */
		});
		//각 시리즈별 이름과 타입 설정

		
		//데이터 길이 확인
		var cnt = data.length;
		
		var anaDgr = new Array();
		for(i=0; i<cnt; i++) {
			anaDgr.push(data[i].anaDgr);
		}

		var initData = toCamel({
				"xAxis": {
					"labels": {
			            "format": "{value}<s:message code='DGR' />"
			        },
			        "title": {
			        	"text": "<s:message code='ANLY.ODR' />"/* '분석차수' */
			        },
			        "categories":anaDgr
			    },
			    "yAxis": [{
			        "labels": {
			            "format": '{value}',
			        },
			        "title": {
			            "text": "<s:message code='QLTY.SCR' />",/* '품질점수' */
			        },
			        "max":100
			    }, {
			        "title": {
			            "text": "<s:message code='EROR.RATE' />",/* '오류율(%)' */
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
	    
		
		//시리즈별 데이터 생성
		var pointsCol = new Array();
		var pointsLine = new Array();

		for(i=0;i<cnt;i++) {
			pointsCol.push(toCamel({X:i, Y:data[i].errRate}));
			pointsLine.push(toCamel({X:i, Y:data[i].nonRate}));
		}

		seriesOtlCol.data = pointsCol;
		seriesOtlSpline.data = pointsLine;

		chart_otl_bar.addSeries(seriesOtlCol);
		chart_otl_bar.addSeries(seriesOtlSpline);
		
		chart_otl_bar.setOptions(initData);
		chart_otl_bar.draw();
		
		drawOtlPie(0, 0);
	});
}

// function chart_otl_bar_OnPointClick(Index, X, Y) {
// 	//console.log($("#hiddenDbmsId4").val());
// 	drawOtlPie(Index, X);
// }

function drawOtlPie(Index, X) {
	if(Index != 0)  return;
	
	chart_otl_pie.removeAll();
	var xAxis = getChartAxisOptions(chart_otl_bar, 'x', Index, 'categories');
	
	var param = new Object();
	param.dbTblNm = $("#hiddenDbmsId4").val();
	param.dbSchId = $("#hiddenSchId4").val();
	param.otlDtcId = $("#hiddenOtlDtcId").val();
	param.anaDgr =  $("#hiddenAnaDgr").val();
	
	
	
	
	chart_otl_pie.setOptions(toCamel({
		Chart : {
			BackgroundColor : "#ffffff", //차트 배경색 설정
			Type : "column"//, //차트 Type 설정
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
		
	},{ append: true, resetData: true }));
	
	draw_pie_otl(param);
}

function draw_pie_otl(param) {
	$.getJSON('<c:url value="/advisor/prepare/otlquality/getOtlPieData.do"/>', param, function(data){
		if(data ==  null) return;
		
		//시리즈 생성
		var series = toCamel({
			Name:"<s:message code='EROR.RATE' />"	,		/* "오류율(%)" */
			Type:"pie"
		});		

		
		//시리즈별 데이터 생성
		var points = new Array();
		
		//console.log(data);
        var errRate = 0;
		for(i=0; i<data.length; i++) {
			points.push(toCamel({X:0, Y:data[i].errRate, Name:data[i].dqiLnm, Sliced:0}));
		}
		
		series.data = points;
		
		chart_otl_pie.addSeries(series);

		chart_otl_pie.draw();

	});
}
</script>

<div id="grid_04" class="grid_04">
	<script type="text/javascript">createIBSheet("grid_otl", "99%", "250px");</script>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div>
	<div id="chart_otl_bar"></div>
	<div id="line_div"><span></span></div>
	<div id="chart_otl_pie"></div>
</div>
<div style="clear:both; height:10px;"><span></span></div>
<input type="hidden" id="hiddenDbmsId4" name="hiddenDbmsId4" />
<input type="hidden" id="hiddenSchId4" name="hiddenSchId4" />
<input type="hidden" id="hiddenOtlDtcId" name="hiddenOtlDtcId" />
<input type="hidden" id="hiddenAnaDgr" name="hiddenAnaDgr" />
