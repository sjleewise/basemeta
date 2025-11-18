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

$(document).ready(function() {
});

$(window).on('load',function() {
	//그리드 초기화
	//$(window).resize();
});

function initColGrid() {
//진단대상 테이블 grid
		with(grid_col){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.COL.DTL' />", Align:"Center"} ];
	        //진단대상ID|진단대상명|스키마ID|스키마명|테이블명|컬럼명|컬럼한글명|분석차수|분석총건수|추정오류건수|오류율(%)|품질점수
	        
	        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
	                    {Type:"Text",   Width:150,                  SaveName:"dbConnTrgId",   Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"dbConnTgrPnm",  Align:"Left",    Edit:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbSchId",	      Align:"Left",    Edit:0, Hidden:1},
	                    {Type:"Text",   Width:150,                  SaveName:"dbSchPnm",	  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbcTblNm",	  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbcColNm",	  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"dbcColKorNm",	  Align:"Left",    Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,                  SaveName:"anaDgr",	      Align:"Center",  Edit:0, Hidden:0, ColMerge:0},
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
		init_sheet(grid_col);    
	    //===========================
}

function grid_col_OnSearchEnd() {
	chart_col_bar.RemoveAll();
	chart_col_pie.RemoveAll();
	if(grid_col.GetDataLastRow() == -1) return;
	$("#hiddenDbmsId3").val(grid_col.GetCellValue(1, 'dbConnTrgId'));
	$("#hiddenSchId3").val(grid_col.GetCellValue(1, 'dbSchId'));
	draw_bar_col(1, 1);
}

function grid_col_OnClick(Row, Col) {
	$("#hiddenDbmsId3").val(grid_col.GetCellValue(Row, 'dbConnTrgId'));
	$("#hiddenSchId3").val(grid_col.GetCellValue(Row, 'dbSchId'));
	draw_bar_col(Row, Col);
}

function draw_bar_col(Row, Col) {
	sRow = 0;
	lRow = 0;
	var colNm = '';
	var selColNm = grid_col.GetCellValue(Row, 'dbcColNm');
	var selTblId = grid_col.GetCellValue(Row, 'dbcTblNm');
	
	for(i=1; i<=grid_col.GetDataLastRow(); i++) {
		var searchColNm = grid_col.GetCellValue(i, 'dbcColNm');
		var searchTblId = grid_col.GetCellValue(i, 'dbcTblNm');
		
		if(searchColNm == selColNm && searchTblId == selTblId) {
			if(sRow == 0) sRow = i;
			lRow = i;
		}
	}
	
	var dataX1 = new Array();
	
	var dataX2 = new Array();
	var dataX3 = new Array();
	
	for(i=sRow; i<=lRow; i++) {
		var a = new Object();
		a = grid_col.GetCellValue(i, 'nonRate');
		dataX1.push(a);
		var b = new Object();
		b = grid_col.GetCellValue(i, 'errRate');
		dataX2.push(b);
		var c = new Object();
		c = grid_col.GetCellValue(i, 'anaDgr') + "차";
		dataX3.push(c);
	}
	                               
    var initData = {
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
		            "format": '{value}',
		        },
		        "title": {
		            "text": '품질점수',
		        },
		        "max":100
		    }, {
		        "title": {
		            "text": '오류율(%)',
		        },
		        "labels": {
		            "format": '{value}%',
		        },
		        "opposite": true
		    }],
		    "series": [{
		        "name": "오류율(%)",
		        "type": "column",
		        "data": dataX2,
		        "yAxis": 1,
		        "Cursor" : "pointer",
		        "tooltip": {
		            "valueSuffix": '%'
		        }
		    }, {
		        "name": "품질점수",
		        "type": "spline",
		        "data": dataX1,
		        "tooltip": {
		            "valueSuffix": ''
		        }
		    }],
		    "tooltip": {
		        "headerFormat": "",
		        "shared": true
		    }
		};
    chart_col_bar.SetOptions(initData);
	chart_col_bar.Draw();
	
	drawColPie(0, 0);
}

function chart_col_bar_OnPointClick(Index, X, Y) {
	//console.log($("#hiddenDbmsId3").val());
	drawColPie(Index, X);
}

function drawColPie(Index, X) {
	if(Index != 0)  return;
	var xAxis = chart_col_bar.GetXAxis(Index).categories;
	
	var param = new Object();
	param.dbConnTrgId = $("#hiddenDbmsId3").val();
	param.dbSchId = $("#hiddenSchId3").val();
	param.anaDgr = xAxis[X].replace('차', '');
	
	chart_col_pie.SetOptions({
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
		
	});
	
	draw_pie_col(param);
}

function draw_pie_col(param) {
	$.getJSON('<c:url value="/advisor/prepare/otlquality/getColPieData.do"/>', param, function(data){
		if(data ==  null) return;
		
		chart_col_pie.RemoveAll();

		//시리즈 생성
		var series 	= chart_col_pie.CreateSeries();		

		//각 시리즈별 이름과 타입 설정
		series.SetOptions({
			Name:"품질점수",
			Type:"pie"
		});		
		
		//시리즈별 데이터 생성
		var points = new Array();
		
		//console.log(data);

		for(i=0; i<data.length; i++) {
			points.push({X:0, Y:data[i].nonRate, Name:data[i].dqiLnm, Sliced:0});
		}
		
		series.AddPoints(points);

		chart_col_pie.AddSeries(series);

		chart_col_pie.Draw();

	});
}
</script>

<div id="grid_03" class="grid_03">
	<script type="text/javascript">createIBSheet("grid_col", "99%", "250px");</script>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div style="width:100%;">
	<div style="width:70%;float:left;"><script type="text/javascript">createIBChart("chart_col_bar", "100%", "250px");</script></div>
	<div style="width:1%;float:left;"><span></span></div>
	<div style="width:29%;float:left;"><script type="text/javascript">createIBChart("chart_col_pie", "100%", "250px");</script></div>
</div>
<div style="clear:both; height:10px;"><span></span></div>
<input type="hidden" id="hiddenDbmsId3" name="hiddenDbmsId3" />
<input type="hidden" id="hiddenSchId3" name="hiddenSchId3" />
