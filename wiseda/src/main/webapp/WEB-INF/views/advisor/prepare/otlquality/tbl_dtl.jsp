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
	
	$("#chart_tbl_bar").css({"width":chart_size,"float":"left"});
	$("#lind_div").css({"width":line_size,"float":"left"});
	$("#chart_tbl_pie").css({"width":pie_size,"float":"left"});
});


$(window).on('load',function() {
	//그리드 초기화
	//$(window).resize();
	
   $("#chart_tbl_bar").css({"width":chart_size,"float":"left"});
   $("#lind_div").css({"width":line_size,"float":"left"});
   $("#chart_tbl_pie").css({"width":pie_size,"float":"left"});
   
	createIBChart("chart_tbl_bar", "chart_tbl_bar", {
		width: "auto",
		height: "250px"
	});
	createIBChart("chart_tbl_pie", "chart_tbl_pie", {
		width: "auto",
		height: "250px"
	});	
});

function initTblGrid() {
//진단대상 테이블 grid
		with(grid_tbl){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        /* 기존소스_bak 181022  */
	        /* var headers = [ {Text:"진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명|분석차수|진단컬럼수|분석총건수|추정오류건수|오류율(%)|품질점수", Align:"Center"} ]; */
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.TBL.DTL' />", Align:"Center"} ];
			//진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명|분석차수|진단컬럼수|분석총건수|추정오류건수|오류율(%)|품질점수
	        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
	                    {Type:"Text",   Width:150,                  SaveName:"dbConnTgrId",   Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"dbConnTgrPnm",  Align:"Left",    Edit:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbSchId",	      Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"dbSchPnm",	  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbcTblNm",	  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbcTblKorNm",	  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"anaDgr",	      Align:"Center",  Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"#,###",  SaveName:"anaColCnt",	  Align:"Right",   Edit:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"#,###",  SaveName:"anaCnt",	      Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"#,###",  SaveName:"esnErCnt",	  Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"###\\%", SaveName:"errRate",	      Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Float",  Width:150, Format:"###", SaveName:"nonRate",       Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
	                    
	                ];

	        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	        InitColumns(cols);
	        
	        FitColWidth();
	        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	        SetExtendLastCol(1);
	        SetMergeSheet(msPrevColumnMerge);
	    }
	    //==시트설정 후 아래에 와야함===
		init_sheet(grid_tbl);    
	    //===========================
}

function grid_tbl_OnSearchEnd() {
	if(grid_tbl.GetDataLastRow() == -1) return;
	$("#hiddenDbmsId2").val(grid_tbl.GetCellValue(1, 'dbConnTgrId'));
	$("#hiddenSchId2").val(grid_tbl.GetCellValue(1, 'dbSchId'));
	$("#hiddenTblNm2").val(grid_tbl.GetCellValue(1, 'dbcTblNm'));
	draw_bar_tbl(1, 1);
}

function grid_tbl_OnClick(Row, Col) {
	$("#hiddenDbmsId2").val(grid_tbl.GetCellValue(Row, 'dbConnTgrId'));
	$("#hiddenSchId2").val(grid_tbl.GetCellValue(Row, 'dbSchId'));
	$("#hiddenTblNm2").val(grid_tbl.GetCellValue(Row, 'dbcTblNm'));
	draw_bar_tbl(Row, Col);
}

function draw_bar_tbl(Row, Col) {
	chart_tbl_bar.removeAll();
	
	sRow = 0;
	lRow = 0;
	
	var tblNm = '';
	var selTblNm = grid_tbl.GetCellValue(Row, 'dbcTblNm');
	var selSchId = grid_tbl.GetCellValue(Row, 'dbSchId');
	
	for(i=1; i<=grid_tbl.GetDataLastRow(); i++) {
		var searchTblNm = grid_tbl.GetCellValue(i, 'dbcTblNm');
		var searchSchId = grid_tbl.GetCellValue(i, 'dbSchId');
		
		if(searchTblNm == selTblNm && searchSchId == selSchId) {
			if(sRow == 0) sRow = i;
			lRow = i;
		}
	}
	var dataX1 = new Array();
	
	var dataX2 = new Array();
	var dataX3 = new Array();
	
	for(i=sRow; i<=lRow; i++) {
		var a = new Object();
		a = grid_tbl.GetCellValue(i, 'nonRate');
		dataX1.push(a);
		var b = new Object();
		b = grid_tbl.GetCellValue(i, 'errRate');
		dataX2.push(b);
		var c = new Object();
		c = grid_tbl.GetCellValue(i, 'anaDgr') + "<s:message code='DGR' />";
		dataX3.push(c);
	}
    var initData = toCamel({
		    "chart": {
		        "type": ["spline", "column"]
		    },
		    "colors": ["#0000FF", "#FF0000"],
		    "xAxis": {
		        "categories": dataX3,
		        "crosshair": true
		    },
		    "yAxis": [{
		        "labels": {
		            "format": '{value}'
		        },
		        "title": {
		            "text": "<s:message code='QLTY.SCR' />"	/* '품질점수' */
		        },
		        "max":100
		    }, {
		        "title": {
		            "text": "<s:message code='EROR.RATE' />"	/* '오류율(%)' */
		        },
		        "labels": {
		            "format": '{value}%'
		        },
		        "opposite": true
		    }],
		    "series": [{
		        "name": "<s:message code='EROR.RATE' />",
		        "type": "column",
		        "data": dataX2,
		        "yAxis": 1,
		        "Cursor" : "pointer"
		    }, {
		        "name": "<s:message code='QLTY.SCR' />",
		        "type": "spline",
		        "data": dataX1
		    }],
		    "tooltip": {
						"shared": true,
						/** for IBChartLite - instead of series.tooltip.valueSuffix */
						formatter: function (aParams) {
							var aHtml = [];
							var name, seriesName, marker, data, params, tmpStr;
							for (var i = 0; i < aParams.length; i += 1) {
								params = aParams[i];
								name = params.name;
								if (!aHtml.length) aHtml.push(name);
								seriesName = params.seriesName;
								marker = params.marker;
								data = params.data;
								// 시리즈 데이터 포매터 분기
								if (seriesName === '오류율(%)') data += '%';
								tmpStr = marker + seriesName + ': ' + '<b>' + data + '</b>';
								aHtml.push(tmpStr);
							}
							// console.log(aHtml)
							return aHtml.join('<br/>');
						}
		    }
		});
		/** for IBChartLite -- instead of chart_tbl_bar_OnPointClick */
		chart_tbl_bar.on('click', 'series', function (evt) {
			// console.log(evt)
			var seriesIndex = evt.seriesIndex;
			var dataIndex = evt.dataIndex;
			drawTblPie(seriesIndex, dataIndex);
		})
		chart_tbl_bar.setOptions(initData);
		chart_tbl_bar.draw();
		
	drawTblPie(0, 0);

}

// function chart_tbl_bar_OnPointClick(Index, X, Y) {
// 	//console.log($("#hiddenDbmsId2").val());
// 	drawTblPie(Index, X);
// }

function drawTblPie(Index, X) {
	if(Index != 0)  return;
	// var xAxis = chart_tbl_bar.GetXAxis(Index).categories;
	
	chart_tbl_pie.removeAll();
	var xAxis = getChartAxisOptions(chart_tbl_bar, 'x', Index, 'categories') || [];
	
	var param = new Object();
	param.dbConnTgrId = $("#hiddenDbmsId2").val();
	param.dbSchId = $("#hiddenSchId2").val();
	param.dbcTblNm = $("#hiddenTblNm2").val();
	param.anaDgr = xAxis[X].replace('차', '');
	
	chart_tbl_pie.setOptions(toCamel({
		Chart : {
			BackgroundColor : "#ffffff", //차트 배경색 설정
			Type : "column"//, //차트 Type 설정
			//BorderColor : "#000000" //차트 테두리 색 설정
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
	
	draw_pie_tbl(param);
}

function draw_pie_tbl(param) {
	$.getJSON('<c:url value="/advisor/prepare/otlquality/getTblPieData.do"/>', param, function(data){
		if(data ==  null) return;
		
		
		var series = toCamel({
			Name:"<s:message code='EROR.RATE' />",	/* 오류율(%) */
			Type:"pie",
			data: points
		});	
		
		//시리즈별 데이터 생성
		var points = new Array();
		
		//console.log(data);
        var errRate = 0;
		for(i=0; i<data.length; i++) {
			points.push(toCamel({X:0, Y:data[i].errRate, Name:data[i].dqiLnm, Sliced:0}));
		}
		
		// series.AddPoints(points);
        series.data = points;
		// chart_tbl_pie.AddSeries(series);

		chart_tbl_pie.setOptions({
			series: [ series ]
		}, { append: true, resetData: true })

		chart_tbl_pie.draw();

	});
}
</script>

<div id="grid_02" class="grid_02">
	<script type="text/javascript">createIBSheet("grid_tbl", "99%", "250px");</script>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div>
	<div id="chart_tbl_bar"></div>
	<div id="line_div"><span></span></div>
	<div id="chart_tbl_pie"></div>
</div>
<div style="clear:both; height:10px;"><span></span></div>
<input type="hidden" id="hiddenDbmsId2" name="hiddenDbmsId2" />
<input type="hidden" id="hiddenSchId2" name="hiddenSchId2" />
<input type="hidden" id="hiddenTblNm2" name="hiddenTblNm2" />
