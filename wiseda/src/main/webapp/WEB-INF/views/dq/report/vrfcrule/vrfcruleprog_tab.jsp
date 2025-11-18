<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="PROF.QLTY.TRANSITION"/></title><!--프로파일 품질추이-->

<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script> --%>
<script type="text/javascript">


$(document).ready(function() {

	//그리드 초기화 
 	initGrid();

	//탭 초기화
 	//$( "#tabs" ).tabs();
	

	$("#btnTreeNew").hide();
	
	$("#btnDelete").hide();
	
	//조회
	$("#btnSearch").click(function(){
		
		//$('#bizrule_sel_title').html(null);
		
// 		doAction("Search");
		
		}).show();
	
	

	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	
	
});

EnterkeyProcess("Search");

function initGrid()
{
    with(grid_sheet){
    
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(1);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.VRFCRULEPROG_LST'/>"
                    	,Align:"Center"}
                ];
        //No.|진단대상ID|진단대상명|진단대상논리명|검증분류|분석차수(숨김)|분석차수|분석일자|분석건수|오류건수|품질점수(%)|DPMO|SIGMA

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Text",   	Width:100,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Text",   	Width:180,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Combo",   	Width:180,  SaveName:"vrfcTyp",   	Align:"Center", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Text", 		Width:120,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1, ColMerge:0},
                    {Type:"Text", 		Width:120,   SaveName:"anaDgrDisp",  Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Date", 		Width:120,   SaveName:"anaStrDtm",  Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Int",   	Width:100,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:100,   SaveName:"esnErCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:100,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:100,   SaveName:"dpmo", 	Align:"Right", Edit:0, Hidden:1, ColMerge:0},
                    {Type:"Float",   	Width:100,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        //SetColProperty("prfKndCd", ${codeMap.prfKndCdibs});
        SetColProperty("vrfcTyp", ${codeMap.vrfcTypibs});
        
        InitComboNoMatchText(1, "");
        
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}




function grid_sheet_OnClick(row, col, value, cellx, celly) {

	if(row < 1) return;

	//클릭한 진단대상명의 시작과 끝위치를 구한다.
	var tmpDB = grid_sheet.GetCellValue(row, "dbConnTrgLnm");
	var strVal = 0;
	var endVal = 0;
	for(var i=0; i<grid_sheet.SearchRows(); i++) {
		if(grid_sheet.GetCellValue(i+1, "dbConnTrgLnm") == tmpDB) {
			if(strVal == 0) strVal = (i+1);
		} else {
			if(strVal != 0 && endVal == 0) {
				endVal = i;
				break;
			}
		}

		if(i+1 == grid_sheet.SearchRows()) endVal = (i+1);
	}

	//차트데이터 생성
	chartDraw(strVal, endVal);

}



function grid_sheet_OnSearchEnd(code) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	//차트 클리어
	// progChart.RemoveAll();

	if(grid_sheet.SearchRows() == 0) return;

	//첫번째 행 클릭상태로 차트데이터를 생성...
	grid_sheet_OnClick(1);
}

$(window).on('load',function(){
	createIBChart("progChart", "progChart", {
		width: "auto",
		height: "350px"
	});
	
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
				pointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		xAxis : {
			tickInterval : 1, //X축 레이블 간격 설정
			labels : { //X축 레이블 설정
				enabled : true
			},
			title : { //X축 제목 설정
				text : "<s:message code='ANLY.ODR' />"/*분석차수*/
			}
		},
		yAxis : {
			tickInterval : 10, //Y축 레이블 간격 설정
			min : 60, //Y축 Min값 설정
			max : 100, //Y축 Max값 설정
			title : { //Y축 제목 설정
				text : "<s:message code='QLTY.SCR.PER'/>"/*품질점수(%)*/

			}
		}
	});

 	chartDraw();


});

function chartDraw(strVal, endVal)	{
	//차트 클리어
	// progChart.RemoveAll();

	//검색결과가 없을경우 리턴...
	if(grid_sheet.SearchRows() == 0) return;

	//프로파일종류
	var tmpvrfcTyp = "";
	var vrfcTyp;

	//최대차수
	var maxAnaDgr = 0;

	//프로파일컬럼 시작위치
	var tmpStrvrfcTyp = "";
	var strvrfcTyp;

	//각 프로파일컬럼 갯수
	var tmpPrfCntVal = 1;
	var tmpPrfCnt = "";
	var prfCnt;
	var firstFlag = true;
	
	var minVal = 999;
	
	for(var i=strVal; i<=endVal; i++) {
	
		//최대차수 계산...
		if(grid_sheet.GetCellValue(i, "anaDgr") > maxAnaDgr) {
			maxAnaDgr = grid_sheet.GetCellValue(i, "anaDgr");
		}
		
		if(grid_sheet.GetCellValue(i, "erRate") < minVal) {
			minVal = grid_sheet.GetCellValue(i, "erRate");
		}
	
		//프로파일종류 추출(차수에 따른 중복 제거)
		if(grid_sheet.GetCellText(i-1, "vrfcTyp") != grid_sheet.GetCellText(i, "vrfcTyp")) {
			if(tmpvrfcTyp != "") {
				tmpvrfcTyp += "|";
				tmpStrvrfcTyp += "|";
				if(tmpPrfCnt != "")
					tmpPrfCnt += "|";
			}
			tmpvrfcTyp += grid_sheet.GetCellText(i, "vrfcTyp") + "(" + grid_sheet.GetCellText(i, "dbConnTrgLnm") + ")";
			tmpStrvrfcTyp += i;
			if(firstFlag) {
				firstFlag = false;
			} else {
				tmpPrfCnt += tmpPrfCntVal;
				tmpPrfCntVal = 1;
			}
		
		} else {
			tmpPrfCntVal++;
		}

		if(i == endVal){
			if(tmpPrfCnt != "") {
				tmpPrfCnt += "|";
			}
		
			tmpPrfCnt += tmpPrfCntVal;
		}
					
	}
	vrfcTyp = tmpvrfcTyp.split("|");
	strvrfcTyp = tmpStrvrfcTyp.split("|");
	prfCnt = tmpPrfCnt.split("|");


	//시리즈 생성
	var series = new Array();
	for(var i=0; i<vrfcTyp.length; i++) {
		// series[i] = progChart.CreateSeries();
		//각 시리즈별 이름과 타입 설정
		series[i] = {
			name:vrfcTyp[i],
			type:"spline"
		};
	}
	
	//시리즈별 데이터 생성
	var points = new Array();
	
	var category = new Array();
		
	for(var i=0; i<vrfcTyp.length; i++) {
		points[i] = new Array();
		for(var j=0;j<prfCnt[i];j++) {
			var row = parseInt(strvrfcTyp[i]) + parseInt(j);
			points[i].push({x:grid_sheet.GetCellText(row, "anaDgrDisp"), y:grid_sheet.GetCellValue(row, "erRate"), name:grid_sheet.GetCellText(row, "anaDgrDisp")});
// 			alert(i + "번째 배열값 : " + points[i]);
			category.push(grid_sheet.GetCellValue(row, "anaDgrDisp"));
		}
	}
	for(var i=0; i<vrfcTyp.length; i++) {
		series[i].data = points[i];
	}

	category = Array.from(new Set(category));
	
	// for(var i=0; i<vrfcTyp.length; i++) {
	// 	progChart.AddSeries(series[i]);
	// }

	progChart.setOptions({
		series: series,
		xAxis : {
			categories : category
		},
		yAxis : {
			tickInterval : 10, //Y축 레이블 간격 설정
			min : minVal, //Y축 Min값 설정
			max : 100 //Y축 Max값 설정
		}
	}, { append: true, resetData: true })

	progChart.draw();

}


</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PROF.QLTY.TRANSITION"/></div><!--프로파일 품질추이-->

	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div id="search_div">
	<div style="clear:both; height:5px;"><span></span></div>

<!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>

<div style="clear:both; height:20px;"><span></span></div>
<div class="main_chart_01" id="progChart">
        
<%--             <script type="text/javascript">createIBChart("progChart", "100%", "350px");</script> --%>
        </div>


</div>

</body>

</html>