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
 	initGrid5();
 	

	//탭 초기화
 	//$( "#tabs" ).tabs();
	

	$("#btnTreeNew").hide();
	
	$("#btnDelete").hide();
	
	//조회
	$("#btnSearch").click(function(){
		$('#bizrule_sel_title').html(null);
		}).show();
	
// 	//엑셀다운로드
// 	$("#btnExcelDown").click(function(){	
		
// 		doAction("Down2Excel");	
		
// 	});
	
	
	
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	
	createIBChart("progChart", "progChart", {
      width: "auto",
      height: "350px"
    });
});



function initGrid5()
{
    with(grid_sheet5){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(1);
        
        var headers = [
                       {Text:"<s:message code='DQ.HEADER.BIZRULEQUALITY_LST'/>"	,Align:"Center"}
                   ];
           //No.|분석차수(Hidden)|분석차수|진단대상ID|진단대상명|테이블명|컬럼명|업무규칙수|진단테이블수|진단컬럼수|분석건수|오류건수|품질점수(%)

           var headerInfo = {Sort:0, ColMove:1, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 
           
           var cols = [                        
                       {Type:"Seq",    	Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   	Width:10,  SaveName:"anaDgr",   	Align:"Left", Edit:0, Hidden:1, ColMerge:0},
                       {Type:"Text",   	Width:200,  SaveName:"anaDgrDisp",   	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Text",   	Width:10,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                       {Type:"Text",   	Width:200,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                       {Type:"Text",   	Width:130,  SaveName:"dbcTblNm",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                       {Type:"Text",   	Width:130,  SaveName:"dbcColNm",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                       {Type:"Int",   	Width:200,  SaveName:"totBrCnt",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Int",   	Width:200,  SaveName:"anaTblCnt",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Int",   	Width:200,  SaveName:"anaColCnt",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Int",   	Width:200,  SaveName:"anaCnt",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Int",   	Width:200,  SaveName:"erCnt",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Float",   	Width:200,  SaveName:"erRate",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0}
                 
                   ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
//         SetColProperty("prfKndCd", ${codeMap.prfKndCdibs});
        
        InitComboNoMatchText(1, "");
        
      
//         FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet5);    
    //===========================
   
}





function grid_sheet5_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

// 	if($("#sortTyp").val() == "dbConnTrgLnm") tmpTyp = "dbConnTrgLnm";
// 	else if($("#sortTyp").val() == "dbcTblNm") tmpTyp = "dbcTblNm";
// 	else if($("#sortTyp").val() == "dbcColNm") tmpTyp = "dbcColNm";
	
// 	//클릭한 진단대상명의 시작과 끝위치를 구한다.
// 	var tmpDB = grid_sheet5.GetCellValue(row, tmpTyp);
// 	var tmpDbcTbl = grid_sheet5.GetCellValue(row, "dbcTblNm");
	var strVal = 0;
	var endVal = 0;
	
// 	if($("#sortTyp").val() == "dbcColNm") {
// 		for(var i=0; i<grid_sheet5.SearchRows()+1; i++) {
// 			if(grid_sheet5.GetCellValue(i+1, tmpTyp) == tmpDB && grid_sheet5.GetCellValue(i+1, "dbcTblNm") == tmpDbcTbl) {

// 				if(strVal == 0) strVal = (i+1);
// 			} else {
// 				if(strVal != 0 && endVal == 0) {
// 					endVal = i;
// 					break;
// 				}
// 			}
			
// 			if(i+1 == grid_sheet5.SearchRows()+1) endVal = (i+1);
// 		}

		
// 	} else { //dbc컬럼은 dbc테이블과 겹칠수 있으므로 별도로 시작/종료열을 계산한다.
// 		for(var i=0; i<grid_sheet5.SearchRows()+1; i++) {
// 			if(grid_sheet5.GetCellValue(i+1, tmpTyp) == tmpDB) {

// 				if(strVal == 0) strVal = (i+1);
// 			} else {
// 				if(strVal != 0 && endVal == 0) {
// 					endVal = i;
// 					break;
// 				}
// 			}
			
// 			if(i+1 == grid_sheet5.SearchRows()+1) endVal = (i+1);
// 		}
		
// 	}

	srtVal = 1;
	endVal = grid_sheet5.SearchRows();

	//차트데이터 생성
	chartDraw(strVal, endVal);

}



function grid_sheet5_OnSearchEnd(code) {
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//차트 클리어
		progChart.removeAll();
		
		if(grid_sheet5.SearchRows() == 0) return;
		
		//첫번째 행 클릭상태로 차트데이터를 생성...
		grid_sheet5_OnClick(1);
	}
}

$(window).on('load',function(){
	
	
// 	chartDraw();
	
	setSheetColumn($("#sortTyp").val());


});

function chartDraw(strVal, endVal)	{
	//차트 클리어
// 	progChart.RemoveAll();
	
	//검색결과가 없을경우 리턴...
	if(grid_sheet5.SearchRows() == 0) return;
	
	//차트 클리어
// 	progChart.RemoveAll();
	
	var category = new Array(); //카테고리를 보여줄 변수...
	var titleText = ""; //x축 기준을 설정할 변수...
	
	//시트에서 가져와 push할 헤더값 설정...
	var cellText = "";
	
	//차트 카테고리값 설정... (컬럼명 검색시는 차트표시 안하므로 return)
	if($("#sortTyp").val() == "dbConnTrgLnm") {
		titleText = grid_sheet5.GetCellText(0, "dbConnTrgLnm");
		cellText = "dbConnTrgLnm";
		for(var i=strVal, j=0; i<=endVal; i++, j++) {
			category.push(grid_sheet5.GetCellValue(strVal+j, "dbConnTrgLnm") + "(" + grid_sheet5.GetCellValue(strVal+j, "anaDgrDisp") + ")");
		}
	} else if($("#sortTyp").val() == "dbcTblNm") {
		titleText = grid_sheet5.GetCellText(0, "dbcTblNm");
		cellText = "dbcTblNm";
		for(var i=strVal, j=0; i<=endVal; i++, j++) {
			category.push(grid_sheet5.GetCellValue(strVal+j, "dbcTblNm") + "(" + grid_sheet5.GetCellValue(strVal+j, "anaDgrDisp") + ")");

		}
	} else if($("#sortTyp").val() == "dbcColNm") {
		titleText = grid_sheet5.GetCellText(0, "dbcColNm");
		cellText = "dbcColNm";
		for(var i=strVal, j=0; i<=endVal; i++, j++) {
			category.push(grid_sheet5.GetCellValue(strVal+j, "dbcColNm") + "(" + grid_sheet5.GetCellValue(strVal+j, "anaDgrDisp") + ")");

		}
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
// 				stacking:"normal",
				dataLabels : { //시리즈의 데이터 레이블 설정
					enabled : true,
				}
			},
			column : {
				pointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		xAxis : {
			categories : category,
			tickInterval : 1, //X축 레이블 간격 설정
			labels : { //X축 레이블 설정
				enabled : true
			},
			title : { //X축 제목 설정
				text : titleText
			}
		},
		yAxis : {
// 			tickInterval : 20, //Y축 레이블 간격 설정
			min : 0, //Y축 Min값 설정
			max : 100, //Y축 Max값 설정
			labels : { //X축 레이블 설정
				enabled : false
			},
			title : { //Y축 제목 설정
				text : "<s:message code='QLTY.SCR.PER'/>"/*품질점수(%)*/
			}
		}
	});
	
	
	
	//시리즈 생성
// 	var series = progChart.CreateSeries();

	
	var series = {
		name:"<s:message code='QLTY.SCR'/>",
		type:"bar"
	};/*품질점수*/


	
	//시리즈별 데이터 생성
	var points = new Array();
			
	for(var i=0; i<category.length; i++) {
		points.push({x:i, y:grid_sheet5.GetCellValue(strVal+i, "erRate"), name:grid_sheet5.GetCellText(strVal+i, "anaDgrDisp")});

	}

// 	series.AddPoints(points);
    series.data = points;
	
	
// 	progChart.AddSeries(series);
	progChart.setOptions({
		series : [series]
	}, {
		 append : true
		,resetData : true
	});

	
	progChart.draw();

}

function setSheetColumn(sortTyp) {
	
	grid_sheet5.SetColHidden("dbConnTrgLnm", 1);
	grid_sheet5.SetColHidden("dbcTblNm", 1);
	grid_sheet5.SetColHidden("dbcColNm", 1);

	grid_sheet5.RemoveAll();
// 	progChart.RemoveAll();
	
	if($("#sortTyp").val() == "dbConnTrgLnm") {
		grid_sheet5.SetColHidden("dbConnTrgLnm", 0);
	} else if($("#sortTyp").val() == "dbcTblNm") {
		grid_sheet5.SetColHidden("dbConnTrgLnm", 0);
		grid_sheet5.SetColHidden("dbSchLnm", 0);
		grid_sheet5.SetColHidden("dbcTblNm", 0);
	} else if($("#sortTyp").val() == "dbcColNm") {
		grid_sheet5.SetColHidden("dbConnTrgLnm", 0);
		grid_sheet5.SetColHidden("dbSchLnm", 0);
		grid_sheet5.SetColHidden("dbcTblNm", 0);
		grid_sheet5.SetColHidden("dbcColNm", 0);
	}
}


</script>


<div style="clear:both; height:5px;"><span></span></div>
<div style="clear:both; height:5px;"><span></span></div>


<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet5", "100%", "250px");</script>            
</div>

<div style="clear:both; height:20px;"><span></span></div>
<div class="main_chart_01" id="progChart"> </div>

