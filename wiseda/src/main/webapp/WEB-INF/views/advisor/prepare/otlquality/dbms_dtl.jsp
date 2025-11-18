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
	
	$("#chart_sch_bar").css({"width":chart_size,"float":"left"});
	$("#lind_div").css({"width":line_size,"float":"left"});
	$("#chart_sch_pie").css({"width":pie_size,"float":"left"});
});

$(window).on('load',function() {
   //그리드 초기화
   //$(window).resize();
	
	
   $("#chart_sch_bar").css({"width":chart_size,"float":"left"});
   $("#lind_div").css({"width":line_size,"float":"left"});
   $("#chart_sch_pie").css({"width":pie_size,"float":"left"});
   
   createIBChart("chart_sch_bar", "chart_sch_bar", {
      width: "auto",
      height: "250px"
    });
   createIBChart("chart_sch_pie", "chart_sch_pie", {
      width: "auto",
      height: "250px"
    });
});

function initDbmsGrid() {
//진단대상 테이블 grid
      with(grid_dbms){
          
          var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);

           /* 기존소스_bak 181022  */
           /* var headers = [ {Text:"진단대상ID|진단대상명|스키마ID|스키마명|스키마한글명|전체테이블수|전체컬럼수|분석차수|진단테이블수|진단컬럼수|분석총건수|추정오류건수|오류율(%)|품질점수(%)", Align:"Center"} ]; */
           
           var headers = [ {Text:"<s:message code='BDQ.HEADER.DBMS.DTL' />", Align:"Center"} ];

           var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [
                       {Type:"Text",   Width:150,                  SaveName:"dbConnTrgId",   Align:"Left",    Edit:0, Hidden:1},
                       {Type:"Text",   Width:150,                  SaveName:"dbConnTgrPnm",  Align:"Left",    Edit:0},
                       {Type:"Text",   Width:150,                  SaveName:"dbSchId",         Align:"Left",    Edit:0, Hidden:1},
                       {Type:"Text",   Width:150,                  SaveName:"dbSchPnm",     Align:"Left",    Edit:0, Hidden:0},
                       {Type:"Text",   Width:150,                  SaveName:"dbSchLnm",     Align:"Left",    Edit:0, Hidden:0},
                       {Type:"Float",  Width:150, Format:"#,###",  SaveName:"tblCnt",         Align:"Right",   Edit:0, Hidden:0},
                       {Type:"Float",  Width:150, Format:"#,###",  SaveName:"colCnt",        Align:"Right",   Edit:0, Hidden:0},
                       {Type:"Text",   Width:150,                  SaveName:"anaDgr",         Align:"Center",  Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Float",  Width:150, Format:"#,###",  SaveName:"anaTblCnt",     Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Float",  Width:150, Format:"#,###",  SaveName:"anaColCnt",     Align:"Right",   Edit:0, ColMerge:0},
                       {Type:"Float",  Width:150, Format:"#,###",  SaveName:"anaCnt",         Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Float",  Width:150, Format:"#,###",  SaveName:"esnErCnt",     Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Float",  Width:150, Format:"###\\%", SaveName:"errRate",         Align:"Right",   Edit:0, Hidden:0, ColMerge:0},
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
      init_sheet(grid_dbms);    
       //===========================
}

function grid_dbms_OnSearchEnd() {
   if(grid_dbms.GetDataLastRow() == -1) return;
   $("#hiddenDbmsId").val(grid_dbms.GetCellValue(1, 'dbConnTrgId'));
   $("#hiddenSchId").val(grid_dbms.GetCellValue(1, 'dbSchId'));
   draw_bar_chart(1, 1);
}

function grid_dbms_OnClick(Row, Col) {
   $("#hiddenDbmsId").val(grid_dbms.GetCellValue(Row, 'dbConnTrgId'));
   $("#hiddenSchId").val(grid_dbms.GetCellValue(Row, 'dbSchId'));
   draw_bar_chart(Row, Col);
}

function draw_bar_chart(Row, Col) {
	
   chart_sch_bar.removeAll();
   sRow = 0;
   lRow = 0;
   var schId = '';
   var selSchId = grid_dbms.GetCellValue(Row, 'dbSchId');
   
   for(i=1; i<=grid_dbms.GetDataLastRow(); i++) {
      var searchSchId = grid_dbms.GetCellValue(i, 'dbSchId');
      
      if(searchSchId == selSchId) {
         if(sRow == 0) sRow = i;
         lRow = i;
      }
   }
   
   var dataX1 = new Array();
   
   var dataX2 = new Array();
   var dataX3 = new Array();
   
   for(i=sRow; i<=lRow; i++) {
      var a = new Object();
      a = grid_dbms.GetCellValue(i, 'nonRate');
      dataX1.push(a);
      var b = new Object();
      b = grid_dbms.GetCellValue(i, 'errRate');
      dataX2.push(b);
      var c = new Object();
      c = grid_dbms.GetCellValue(i, 'anaDgr') + "<s:message code='DGR' />"; 
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
                  "format": '{value}',
              },
              "title": {
                  "text": "<s:message code='QLTY.SCR' />",   /* '품질점수' */
              },
              "max":100
          }, {
              "title": {
                  "text": "<s:message code='EROR.RATE' />",   /* '오류율(%)' */
              },
              "labels": {
                  "format": '{value}%',
              },
              "opposite": true
          }],
          "series": [{
              "name": "<s:message code='EROR.RATE' />",
              "type": "column",
              "data": dataX2,
              "yAxis": 1,
              "Cursor" : "pointer",
              "tooltip": {
                  "valueSuffix": '%'
              }
          }, {
              "name": "<s:message code='QLTY.SCR' />",
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
      });
    
    chart_sch_bar.on('click', 'series', function (evt) {
      var seriesIndex = evt.seriesIndex;
      var dataIndex = evt.dataIndex;
      drawDbmsPie(seriesIndex, dataIndex);
    });
      
    chart_sch_bar.setOptions(initData);
   chart_sch_bar.draw();
   
   drawDbmsPie(0, 0);
}

function chart_sch_bar_OnPointClick(Index, X, Y) {
   //console.log($("#hiddenDbmsId").val());
   drawDbmsPie(Index, X);
}

function drawDbmsPie(Index, X) {
   if(Index != 0)  return;
//    var xAxis = chart_sch_bar.GetXAxis(Index).categories;

   chart_sch_pie.removeAll();
   var xAxis = getChartAxisOptions(chart_sch_bar, 'x', Index, 'categories') || [];
   
   var param = new Object();
   param.dbConnTrgId = $("#hiddenDbmsId").val();
   param.dbSchId = $("#hiddenSchId").val();
   param.anaDgr = xAxis[X].replace("<s:message code='DGR' />", '');
   
   chart_sch_pie.setOptions(toCamel({
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
   
   draw_pie_chart(param);
}

function draw_pie_chart(param) {
   $.getJSON('<c:url value="/advisor/prepare/otlquality/getDbmsPieData.do"/>', param, function(data){
      if(data ==  null) return;
      

      //시리즈 생성
//       var series    = chart_sch_pie.CreateSeries();      

      //각 시리즈별 이름과 타입 설정
      var series = toCamel({
         Name:"<s:message code='EROR.RATE' />",   /* 오류율(%) */
         Type:"pie"
      });      
      
      //시리즈별 데이터 생성
      var points = new Array();
      
      //console.log(data);

      var errRate = 0;
      for(i=0; i<data.length; i++) {
         points.push(toCamel({X:0, Y:data[i].errRate, Name:data[i].dqiLnm, Sliced:0}));
         //errRate = parseFloat(errRate) + parseFloat(data[i].nonRate);
      }
      //console.log(errRate); //01001.21.31.31.21
      //points.push({X:0, Y:((100*data.length)-parseFloat(errRate)), Name:'오류점수', Sliced:0});
      
//       series.AddPoints(points);
      series.data = points;

//       chart_sch_pie.AddSeries(series);
      chart_sch_pie.setOptions({
         series : [series]
      }, {
          append : true
         ,resetData : true
      });

      chart_sch_pie.draw();

   });
}
</script>

<div id="grid_01" class="grid_01">
   <script type="text/javascript">createIBSheet("grid_dbms", "99%", "250px");</script>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div>
   <div id="chart_sch_bar"></div>
   <div id="lind_div"><span></span></div>
   <div id="chart_sch_pie"></div>
</div>
<div style="clear:both; height:10px;"><span></span></div>
<input type="hidden" id="hiddenDbmsId" name="hiddenDbmsId" />
<input type="hidden" id="hiddenSchId" name="hiddenSchId" />