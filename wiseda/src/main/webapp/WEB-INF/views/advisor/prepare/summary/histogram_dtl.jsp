<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<style>

/* body {
  font: 10px sans-serif;
} */

.bar rect {
  fill: steelblue;
  shape-rendering: crispEdges;
}

.bar text {
  fill: #fff;
}

.axis path, .axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

</style>
<script src="http://d3js.org/d3.v3.min.js"></script>

<script type="text/javascript">
var varTyp;

$(document).ready(function() {
	
// 	$("#frmdmnfeature input[type=text]").css("border-color","transparent").css("width", "98%").attr("readonly", true);
    $("#frmhisto input[type=text]").attr("readonly", true);
});

$(window).on('load',function() {
	inithistoGrid();
});


$(window).resize( function(){
});


function inithistoGrid(){
	
    with(histo_grid){
    	
   		var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    
                    {Text:"<s:message code='BDQ.HEADER.HISTOGRAM.DTL' />", Align:"Center"}
                ];
					//No|상태|변수ID|구간시작값|구간종료값|구간데이터|구간데이터 건수
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
        		{Type:"Seq",    Width:50,   SaveName:"hstSno",      Align:"Center", Edit:0},
                {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"anlVarId",    	Align:"Left", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"sctStrVal",    	Align:"Center", Edit:0, Hidden:0}, 
                {Type:"Text",   Width:100,  SaveName:"sctEndVal",    	Align:"Center", Edit:0, Hidden:0,},
                {Type:"Text",   Width:150,  SaveName:"strendval",   CalcLogic:"|sctStrVal|+| ~ |+|sctEndVal|", Align:"Center", Edit:0, Hidden:1},
                {Type:"Float",   Width:100,  SaveName:"sctVal",    	Align:"Center", Edit:0, Hidden:0, Format:"###,###,##0"},
            ];
    
    //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
    InitColumns(cols);
    //콤보 목록 설정...
// 	   SetColProperty("dmngNm", {ComboText: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그', ComboCode: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'});
//	     'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'

    InitComboNoMatchText(1, "");
    
    //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
    
    FitColWidth();
    SetExtendLastCol(1); 
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(histo_grid);
    //===========================
    	
}

	// Math from
	// http://www.cse.wustl.edu/~jain/books/ftp/ch5f_slides.pdf
	// Page 32
	function pareto(alpha){
	  return function(){
	    return 1 / Math.pow(Math.random(), 1 / alpha);
	  };
	}
    
function createHistgram(data) {
	
	if (data == null || data.length < 1) return;
	
	// A formatter for counts.
	var formatCount = d3.format(",.0f");

	var margin = {top: 10, right: 30, bottom: 30, left: 30},
	    width = 400 - margin.left - margin.right,
	    height = 300 - margin.top - margin.bottom;

// 	var data = hdata.length;
	var datadx = data[0].sctEndVal - data[0].sctStrVal;
	
	var x = d3.scale.linear()
	    .domain([d3.min(data, function(d) { return d.sctStrVal; }), d3.max(data, function(d) { return d.sctEndVal; })])
	    .range([0, width]);

	var y = d3.scale.linear()
	    .domain([d3.min(data, function(d) { return d.sctVal; }), d3.max(data, function(d) { return d.sctVal; })])
	    .range([height, 0]);

	var xAxis = d3.svg.axis()
	    .scale(x)
	    .orient("bottom");

	var svg = d3.select("div#histo_dtl_div").append("svg")
	    .attr("width", width + margin.left + margin.right)
	    .attr("height", height + margin.top + margin.bottom)
	  .append("g")
	    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	var bar = svg.selectAll(".bar")
	    .data(data)
	  .enter().append("g")
	    .attr("class", "bar")
	    .attr("transform", function(d) { return "translate(" + x(d.sctStrVal) + "," + y(d.sctVal) + ")"; });

	bar.append("rect")
	    .attr("x", 1)
	    .attr("width", x(datadx) - 1)
	    .attr("height", function(d) { return height - y(d.sctVal); });

	bar.append("text")
	    .attr("dy", ".5em")
	    .attr("y", 6)
	    .attr("x", x(datadx) / 2)
	    .attr("text-anchor", "middle")
	    .text(function(d) { return formatCount(d.sctVal); });

	svg.append("g")
	    .attr("class", "x axis")
	    .attr("transform", "translate(0," + height + ")")
	    .call(xAxis);
}


//진단대상 테이블 조회 오류
function histo_grid_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		//차트를 그리자...
 		//createHistgram(); 
 		
 		var anlVarId = histo_grid.GetCellValue(1,"anlVarId");
 		
		var imgUrl = "<c:url value='/img/advisor/' />" + anlVarId + "_histo.png";

		var imgHtml = "<img id='imgHisto' src='" + imgUrl + "' style='width:99%;' />"; 
		
		var varTyp = grid_col.GetCellValue(grid_col.GetSelectRow(), 'varType');

		//alert(imgUrl);
		 		
 		$.ajax({
		    url: imgUrl,
		    type:'HEAD',
		    error: function() {
		        //alert("file not exists");

		    	$("#histo_dtl_div").html("");

		    },
		    success: function() {
		    	//alert("file exists");
		    	$("#histo_dtl_div").html(imgHtml);

				if(varTyp == 'numeric') {
		    		$("#imgHisto").css("height","250px");
				} else if(varTyp == 'categorical') {
					$("#imgHisto").css("height","400px");
				}
		    }
		});

	}
}

function histo_grid_OnLoadData(data) {
	//데이터 확인...
	if(data != null) {
		//차트를 그리자...
		$("div#histo_dtl_div svg").remove();
		
		var jsondata = jQuery.parseJSON(data)
		
		//D3차트로 구현시 
		//createHistgram(jsondata.DATA);

		
	}

}

</script>

<!-- </head> -->
<!-- <body> -->
	<div >
		<div class="stit"><s:message code='INTV.PER.DATA.CNT' /></div><!--구간별 데이터 건수-->

		<div style="clear:both; height:10px;"><span></span></div>
		
		<div id="histo_dtl_div"></div>
		
		<div style="clear:both; height:10px;"><span></span></div>
		<!-- 그리드 입력 입력 -->
		<div id="grid_histo" class="grid_01">
		     <script type="text/javascript">createIBSheet("histo_grid", "99%", "400px");</script>            
		</div>
		
	 	<%-- <form id="frmhisto" name="frmhisto" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic2" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.ANLY.DTL.INFO'/>">
			   <caption><s:message code="CLMN.ANLY.DTL.INFO"/></caption><!--컬럼분석 상세정보-->
			   <colgroup>
			   <col style="width:60%;" />
			   <col style="width:40%;" />
			   <col style="width:20%;" />
			   <col style="width:30%;" />
			   </colgroup>
			   	   <thead>
			   	   		<tr>
			   	   			<th>구간데이터</th>
			   	   			<th>구간데이터 건수</th>
			   	   		</tr>
			   	   </thead>
			       <tbody id="histo_tbl_body">   
			       		<tr>                               
			               <td colspan="2" style="text-align: center;">조회된 데이터가 없습니다.</td>
			           </tr>                         
			       </tbody>
			     </table>   
			</div>
			
			</fieldset>
			 
			</form> --%>
	</div>
	
