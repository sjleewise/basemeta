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
<title><s:message code="DIAG.TRGT.QLTY.PRES"/></title><!--진단대상별 품질현황-->
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;
$(document).ready(function() {

	//그리드 초기화 
 	initGrid();
 	

	//탭 초기화
 	//$( "#tabs" ).tabs();
	

	$("#btnTreeNew").hide();
	
	$("#btnDelete").hide();
	
	//조회
	$("#btnSearch").click(function(){
		
		$('#bizrule_sel_title').html(null);
		
		doAction("Search");
		
		}).show();
	
	//엑셀다운로드
	$("#btnExcelDown").click(function(){	
		
		doAction("Down2Excel");	
		
	});
	
	
	
	//======================================================
	 // 셀렉트 박스 초기화
	//======================================================
	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
		double_select(connTrgSchJson, $(this));
	});
	
	
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
                    {Text:"<s:message code='DQ.HEADER.PROFILEQUALITY_LST'/>"
                    	,Align:"Center"}
                ];
                //No.|분석차수(Hidden)|분석차수|진단대상|진단대상|진단대상|진단대상|진단대상|진단대상|진단대상|진단대상|스키마|스키마|스키마|스키마|스키마|스키마|스키마|스키마|테이블|테이블|테이블|테이블|테이블|테이블|테이블|테이블|컬럼|컬럼|컬럼|컬럼|컬럼|컬럼|컬럼|컬럼|"+"관계분석|관계분석|관계분석|관계분석|관계분석|중복분석|중복분석|중복분석|중복분석|컬럼분석|컬럼분석|코드분석|코드분석|코드분석|코드분석|날짜형식분석|날짜형식분석|날짜형식분석|날짜형식분석|범위분석|범위분석|범위분석|범위분석|문자열패턴분석|문자열패턴분석|문자열패턴분석|문자열패턴분석

        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:90,  SaveName:"anaDgr",   	Align:"Left", Edit:0, Hidden:1, ColMerge:0},
                    {Type:"Text",   	Width:90,  SaveName:"anaDgrDisp",   	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Text",   	Width:90,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Int",   	Width:90,  SaveName:"prfTotCntDb",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaTblTotCntDb",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaColTotCntDb",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaCntDb",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntDb",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRateDb",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbSchId",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Text",   	Width:90,  SaveName:"dbSchLnm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Int",   	Width:90,  SaveName:"prfTotCntSch",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaTblTotCntSch",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaColTotCntSch",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaCntSch",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntSch",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRateSch",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbcTblNm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Text",   	Width:90,  SaveName:"dbcTblKorNm",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Int",   	Width:90,  SaveName:"prfTotCntTbl",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaTblTotCntTbl",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaColTotCntTbl",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaCntTbl",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntTbl",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRateTbl",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbcColNm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Text",   	Width:90,  SaveName:"dbcColKorNm",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Int",   	Width:90,  SaveName:"prfTotCntCol",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaTblTotCntCol",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaColTotCntCol",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"anaCntCol",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntCol",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRateCol",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    //여기부터 각 분석별 컬럼...
                    
                    {Type:"Int",   	Width:90,  SaveName:"prfCntPT01",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaTgtCntPT01",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaCntPT01",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntPT01",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRatePT01",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaTgtCntPT02",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaCntPT02",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntPT02",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRatePT02",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaTgtCntPC01",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaCntPC01",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaTgtCntPC02",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaCntPC02",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntPC02",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRatePC02",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaTgtCntPC03",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaCntPC03",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntPC03",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRatePC03",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaTgtCntPC04",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaCntPC04",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntPC04",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRatePC04",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaTgtCntPC05",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"prfAnaCntPC05",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:90,  SaveName:"esnErCntPC05",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:90,  SaveName:"esnErRatePC05",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        SetColProperty("prfKndCd", ${codeMap.prfKndCdibs});
        
        InitComboNoMatchText(1, "");
        
      
//         FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}



function doAction(sAction)
{
        
    switch(sAction)
    {
	    case "Search":
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch("<c:url value="/dq/report/profile/getProfileQuality.do" />", param);

			//차트데이터 생성
// 	    	getLineChart(param);
	    	
	    	break;
	    	
  		case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
//   			progChart.Down2Image({FileName:"ProfileProg", Type:IBExportType.JPEG, Width:800, Url:"./../../../js/IBChart/jsp/Down2Image.jsp"});
            break;
    }
}


function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

	if($("#sortTyp").val() == "dbConnTrgLnm") tmpTyp = "dbConnTrgLnm";
	else if($("#sortTyp").val() == "dbSchLnm") tmpTyp = "dbSchLnm";
	else if($("#sortTyp").val() == "dbcTblNm") tmpTyp = "dbcTblNm";
	else if($("#sortTyp").val() == "dbcColNm") tmpTyp = "dbcColNm";
	
	//클릭한 진단대상명의 시작과 끝위치를 구한다.
	var tmpDB = grid_sheet.GetCellValue(row, tmpTyp);
	var tmpDbcTbl = grid_sheet.GetCellValue(row, "dbcTblNm");
	var strVal = 0;
	var endVal = 0;
	
	if($("#sortTyp").val() == "dbcColNm") {
		for(var i=0; i<grid_sheet.SearchRows()+1; i++) {
			if(grid_sheet.GetCellValue(i+1, tmpTyp) == tmpDB && grid_sheet.GetCellValue(i+1, "dbcTblNm") == tmpDbcTbl) {

				if(strVal == 0) strVal = (i+1);
			} else {
				if(strVal != 0 && endVal == 0) {
					endVal = i;
					break;
				}
			}
			
			if(i+1 == grid_sheet.SearchRows()+1) endVal = (i+1);
		}

		
	} else { //dbc컬럼은 dbc테이블과 겹칠수 있으므로 별도로 시작/종료열을 계산한다.
		for(var i=0; i<grid_sheet.SearchRows()+1; i++) {
			if(grid_sheet.GetCellValue(i+1, tmpTyp) == tmpDB) {

				if(strVal == 0) strVal = (i+1);
			} else {
				if(strVal != 0 && endVal == 0) {
					endVal = i;
					break;
				}
			}
			
			if(i+1 == grid_sheet.SearchRows()+1) endVal = (i+1);
		}
		
	}

	
	//차트데이터 생성
	chartDraw(strVal, endVal);

}



function grid_sheet_OnSearchEnd() {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
	//차트 클리어
	progChart.RemoveAll();
	
	if(grid_sheet.SearchRows() == 0) return;
	
	//첫번째 행 클릭상태로 차트데이터를 생성...
	grid_sheet_OnClick(2);
}

$(window).on('load',function(){
	
	
// 	chartDraw();
	
	setSheetColumn($("#sortTyp").val());


});

function chartDraw(strVal, endVal)	{
	//차트 클리어
	progChart.RemoveAll();
	
	//검색결과가 없을경우 리턴...
	if(grid_sheet.SearchRows() == 0) return;
	
	//차트 클리어
	progChart.RemoveAll();
	
	var category = new Array(); //카테고리를 보여줄 변수...
	var titleText = ""; //x축 기준을 설정할 변수...
	
	//시트에서 가져와 push할 헤더값 설정...
	var cellText = "";
	
	//차트 카테고리값 설정... (컬럼명 검색시는 차트표시 안하므로 return)
	if($("#sortTyp").val() == "dbConnTrgLnm") {
		titleText = grid_sheet.GetCellText(1, "dbConnTrgLnm");
		cellText = "dbConnTrgLnm";
		for(var i=strVal, j=0; i<=endVal; i++, j++) {
			category.push (grid_sheet.GetCellValue(strVal+j, "dbConnTrgLnm") + "(" + grid_sheet.GetCellValue(strVal+j, "anaDgrDisp") + ")");
		}
	}else if($("#sortTyp").val() == "dbSchLnm") {
		titleText = grid_sheet.GetCellText(1, "dbSchLnm");
		cellText = "dbSchLnm";
		for(var i=strVal, j=0; i<=endVal; i++, j++) {
			category.push (grid_sheet.GetCellValue(strVal+j, "dbSchLnm") + "(" + grid_sheet.GetCellValue(strVal+j, "anaDgrDisp") + ")");
		}
	} else if($("#sortTyp").val() == "dbcTblNm") {
		titleText = grid_sheet.GetCellText(1, "dbcTblNm");
		cellText = "dbcTblNm";
		for(var i=strVal, j=0; i<=endVal; i++, j++) {
			category.push (grid_sheet.GetCellValue(strVal+j, "dbcTblNm") + "(" + grid_sheet.GetCellValue(strVal+j, "anaDgrDisp") + ")");

		}
	} else if($("#sortTyp").val() == "dbcColNm") {
		titleText = grid_sheet.GetCellText(1, "dbcColNm");
		cellText = "dbcColNm";
		for(var i=strVal, j=0; i<=endVal; i++, j++) {
			category.push (grid_sheet.GetCellValue(strVal+j, "dbcColNm") + "(" + grid_sheet.GetCellValue(strVal+j, "anaDgrDisp") + ")");

		}
	}

	progChart.SetOptions({
		Chart : {
			BackgroundColor : "#FFFFFF", //차트 배경색 설정
			Type : "column", //차트 Type 설정
			BorderColor : "#FFFFFF"
		},
		Legend : {
			Layout : "vertical", //Legend 모양 설정
			Align : "right", //Legend 가로 정렬 설정
			VerticalAlign : "center" //Legend 세로 정렬 설정
		},
		
		Colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
		
		PlotOptions : {
			Series : {
				Stacking:"normal",
				DataLabels : { //시리즈의 데이터 레이블 설정
					Enabled : true,
				}
			},
			Column : {
				PointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		XAxis : {
			Categories : category,
			TickInterval : 1, //X축 레이블 간격 설정
			Labels : { //X축 레이블 설정
				Enabled : true
			},
			Title : { //X축 제목 설정
				Text : titleText
			}
		},
		YAxis : {
// 			TickInterval : 20, //Y축 레이블 간격 설정
// 			Min : 0, //Y축 Min값 설정
// 			Max : 100, //Y축 Max값 설정
			Labels : { //X축 레이블 설정
				Enabled : false
			},
			Title : { //Y축 제목 설정
				Text : "<s:message code='QLTY.SCR.PER'/>"/*품질점수(%)*/
			}
		}
	});
	
	
	
	//시리즈 생성
	var seriesPT01 = progChart.CreateSeries();
	var seriesPT02 = progChart.CreateSeries();
	var seriesPC02 = progChart.CreateSeries();
	var seriesPC03 = progChart.CreateSeries();
	var seriesPC04 = progChart.CreateSeries();
	var seriesPC05 = progChart.CreateSeries();
	
	seriesPT01.SetOptions({
		Name:"<s:message code='RLT.ANLY'/>",/*관계분석*/
		Type:"bar"
	});
	seriesPT02.SetOptions({
		Name:"<s:message code='DUP.ANLY'/>", /*중복분석*/
		Type:"bar"
	});
	seriesPC02.SetOptions({
		Name:"<s:message code='CD.ANLY'/>",
		Type:"bar"
	});/*코드분석*/

	seriesPC03.SetOptions({
		Name:"<s:message code='DATE.FRMT.ANLY'/>",
		Type:"bar"
	});/*날짜형식분석*/
	seriesPC04.SetOptions({
		Name:"<s:message code='RNG.ANLY'/>", /*범위분석*/
		Type:"bar"
	});
	seriesPC05.SetOptions({
		Name:"<s:message code='STRING.PTRN.ANLY'/>", /*문자열패턴분석*/
		Type:"bar"
	});
	
	//시리즈별 데이터 생성
	var pointsPT01 = new Array();
	var pointsPT02 = new Array();
	var pointsPC02 = new Array();
	var pointsPC03 = new Array();
	var pointsPC04 = new Array();
	var pointsPC05 = new Array();
			
	for(var i=0; i<category.length; i++) {
		pointsPT01.push({X:i, Y:grid_sheet.GetCellValue(strVal+i, "esnErRatePT01"), Name:grid_sheet.GetCellText(strVal+i, "anaDgrDisp")});
		pointsPT02.push({X:i, Y:grid_sheet.GetCellValue(strVal+i, "esnErRatePT02"), Name:grid_sheet.GetCellText(strVal+i, "anaDgrDisp")});
		pointsPC02.push({X:i, Y:grid_sheet.GetCellValue(strVal+i, "esnErRatePC02"), Name:grid_sheet.GetCellText(strVal+i, "anaDgrDisp")});
		pointsPC03.push({X:i, Y:grid_sheet.GetCellValue(strVal+i, "esnErRatePC03"), Name:grid_sheet.GetCellText(strVal+i, "anaDgrDisp")});
		pointsPC04.push({X:i, Y:grid_sheet.GetCellValue(strVal+i, "esnErRatePC04"), Name:grid_sheet.GetCellText(strVal+i, "anaDgrDisp")});
		pointsPC05.push({X:i, Y:grid_sheet.GetCellValue(strVal+i, "esnErRatePC05"), Name:grid_sheet.GetCellText(strVal+i, "anaDgrDisp")});

	}

	seriesPT01.AddPoints(pointsPT01);
	seriesPT02.AddPoints(pointsPT02);
	seriesPC02.AddPoints(pointsPC02);
	seriesPC03.AddPoints(pointsPC03);
	seriesPC04.AddPoints(pointsPC04);
	seriesPC05.AddPoints(pointsPC05);
	
	
	progChart.AddSeries(seriesPC05);
	progChart.AddSeries(seriesPC04);
	progChart.AddSeries(seriesPC03);
	progChart.AddSeries(seriesPC02);
	progChart.AddSeries(seriesPT02);
	progChart.AddSeries(seriesPT01);

	
	progChart.Draw();

}

function setSheetColumn(sortTyp) {
	
	grid_sheet.SetColHidden("dbConnTrgId", 1);
	grid_sheet.SetColHidden("dbConnTrgLnm", 1);
	grid_sheet.SetColHidden("prfTotCntDb", 1);
	grid_sheet.SetColHidden("anaTblTotCntDb", 1);
	grid_sheet.SetColHidden("anaColTotCntDb", 1);
	grid_sheet.SetColHidden("anaCntDb", 1);
	grid_sheet.SetColHidden("esnErCntDb", 1);
	grid_sheet.SetColHidden("esnErRateDb", 1);
	grid_sheet.SetColHidden("dbSchId", 1);
	grid_sheet.SetColHidden("dbSchLnm", 1);
	grid_sheet.SetColHidden("prfTotCntSch", 1);
	grid_sheet.SetColHidden("anaTblTotCntSch", 1);
	grid_sheet.SetColHidden("anaColTotCntSch", 1);
	grid_sheet.SetColHidden("anaCntSch", 1);
	grid_sheet.SetColHidden("esnErCntSch", 1);
	grid_sheet.SetColHidden("esnErRateSch", 1);
	grid_sheet.SetColHidden("dbcTblNm", 1);
	grid_sheet.SetColHidden("dbcTblKorNm", 1);
	grid_sheet.SetColHidden("prfTotCntTbl", 1);
	grid_sheet.SetColHidden("anaTblTotCntTbl", 1);
	grid_sheet.SetColHidden("anaColTotCntTbl", 1);
	grid_sheet.SetColHidden("anaCntTbl", 1);
	grid_sheet.SetColHidden("esnErCntTbl", 1);
	grid_sheet.SetColHidden("esnErRateTbl", 1);
	grid_sheet.SetColHidden("dbcColNm", 1);
	grid_sheet.SetColHidden("dbcColKorNm", 1);
	grid_sheet.SetColHidden("prfTotCntCol", 1);
	grid_sheet.SetColHidden("anaTblTotCntCol", 1);
	grid_sheet.SetColHidden("anaColTotCntCol", 1);
	grid_sheet.SetColHidden("anaCntCol", 1);
	grid_sheet.SetColHidden("esnErCntCol", 1);
	grid_sheet.SetColHidden("esnErRateCol", 1);

	grid_sheet.RemoveAll();
	progChart.RemoveAll();
	
	if($("#sortTyp").val() == "dbConnTrgLnm") {
		grid_sheet.SetColHidden("dbConnTrgId", 1);
		grid_sheet.SetColHidden("dbConnTrgLnm", 0);
		grid_sheet.SetColHidden("prfTotCntDb", 0);
		grid_sheet.SetColHidden("anaTblTotCntDb", 0);
		grid_sheet.SetColHidden("anaColTotCntDb", 0);
		grid_sheet.SetColHidden("anaCntDb", 0);
		grid_sheet.SetColHidden("esnErCntDb", 0);
		grid_sheet.SetColHidden("esnErRateDb", 0);
	} else if($("#sortTyp").val() == "dbSchLnm") {
		grid_sheet.SetColHidden("dbConnTrgLnm", 0);
		grid_sheet.SetColHidden("dbSchId", 1);
		grid_sheet.SetColHidden("dbSchLnm", 0);
		grid_sheet.SetColHidden("prfTotCntSch", 0);
		grid_sheet.SetColHidden("anaTblTotCntSch", 0);
		grid_sheet.SetColHidden("anaColTotCntSch", 0);
		grid_sheet.SetColHidden("anaCntSch", 0);
		grid_sheet.SetColHidden("esnErCntSch", 0);
		grid_sheet.SetColHidden("esnErRateSch", 0);
	} else if($("#sortTyp").val() == "dbcTblNm") {
		grid_sheet.SetColHidden("dbConnTrgLnm", 0);
		grid_sheet.SetColHidden("dbSchLnm", 0);
		grid_sheet.SetColHidden("dbcTblNm", 0);
		grid_sheet.SetColHidden("dbcTblKorNm", 1);
		grid_sheet.SetColHidden("prfTotCntTbl", 0);
		grid_sheet.SetColHidden("anaTblTotCntTbl", 0);
		grid_sheet.SetColHidden("anaColTotCntTbl", 0);
		grid_sheet.SetColHidden("anaCntTbl", 0);
		grid_sheet.SetColHidden("esnErCntTbl", 0);
		grid_sheet.SetColHidden("esnErRateTbl", 0);
	} else if($("#sortTyp").val() == "dbcColNm") {
		grid_sheet.SetColHidden("dbConnTrgLnm", 0);
		grid_sheet.SetColHidden("dbSchLnm", 0);
		grid_sheet.SetColHidden("dbcTblNm", 0);
		grid_sheet.SetColHidden("dbcColNm", 0);
		grid_sheet.SetColHidden("dbcColKorNm", 1);
		grid_sheet.SetColHidden("prfTotCntCol", 0);
		grid_sheet.SetColHidden("anaTblTotCntCol", 0);
		grid_sheet.SetColHidden("anaColTotCntCol", 0);
		grid_sheet.SetColHidden("anaCntCol", 0);
		grid_sheet.SetColHidden("esnErCntCol", 0);
		grid_sheet.SetColHidden("esnErRateCol", 0);
	}
	doAction("Search");
}


</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PROF.QLTY.PRES.1"/></div><!--프로파일 품질현황-->

	</div>
	
	<div class="location"><img src="<c:url value="/images/location_home.gif"/>" alt="" style="padding-top:1px;" /> HOME &gt; <s:message code="QLTY.PRES" /> &gt; <s:message code="PROF.QLTY.PRES.1"/></div><!--품질현황--> <!--프로파일 품질현황-->
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div id="search_div">
	<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
	<form id="frmSearch" name="frmSearch" method="post">
		<input type="hidden" id="bizAreaId" name="bizAreaId"/>
		<input type="hidden" id="dqiId" name="dqiId"/>
		<input type="hidden" id="ctqId" name="ctqId"/>
		<input type="hidden" id="brId" name="brId"/>
		<input type="hidden" id="baseDttm" name="baseDttm"/>
		 <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BZWR.RULE.INQ' />"> <!--업무규칙 조회-->

                   <caption><s:message code="BZWR.RULE.INQ" /></caption><!--업무규칙 조회-->

                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                  </colgroup>
                   
                   <tbody>                            
                       <tr>     
                          <th scope="row"><label for="sortTyp"><s:message code="INQ.BASE"/></label></th><!--검색기준-->
		                    <td>
			                    <select id="sortTyp" name="sortTyp" onChange="setSheetColumn()">
			                    <option value="dbConnTrgLnm"><s:message code="DB.MS" /></option><!--진단대상명-->
			                    <option value="dbSchLnm"><s:message code="SCHEMA.NM" /></option><!--스키마명-->
			                    <option value="dbcTblNm"><s:message code="TBL.NM" /></option><!--테이블명-->
			                    <option value="dbcColNm"><s:message code="CLMN.NM" /></option><!--컬럼명-->
			                    </select>
		                    </td>                           
                           <th scope="row" class=""><label for="dbSchId"><s:message code="DBMS.SCHEMA.NM"/></label></th><!--진단대상명/스키마명-->

	                      <td>
				            <select id="dbConnTrgId" class="" name="dbConnTrgId">
				             <option value="">---<s:message code="CHC" />---</option><!--선택-->

				            </select>
				            <select id="dbSchId" class="" name="dbSchId">
				             <option value="">---<s:message code="CHC" />---</option><!--선택-->

				             </select>
				           </td>
                           
                           <!-- 
                           <th scope="row"><label for="prfKndCd">프로파일종류</label></th>
	                         <td>
                               <select id="prfKndCd"  name="prfKndCd">
								    <option value=""><s:message code="WHL" /></option>
								    <option value="PT01"><s:message code="RLT.ANLY"/></option>
								    <option value="PT02"><s:message code="DUP.ANLY"/></option>

								    <option value="PC02">코드분석</option>
								    <option value="PC03"><s:message code="DATE.FRMT.ANLY"/></option>
								    <option value="PC04"><s:message code="RNG.ANLY"/></option>
								    <option value="PC05"><s:message code="STRING.PTRN.ANLY"/></option><
								</select>
                           </td>
                            -->
	                        <th scope="row"><label for="anaDgr"><s:message code="ANLY.ODR" /></label></th><!--분석차수-->

                           <td>
                              <select id="anaDgr"  name="anaDgr">
								    <option value=""><s:message code="WHL" /></option><!--전체-->
								    <c:forEach var="code" items="${codeMap.anaDgrCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
                           </td>
                       </tr>
                       <tr>                               
                           <th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

                           <td>
                               <input type="text" name="dbcTblNm" id="dbcTblNm" />
                           </td>
                           <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

                           <td colspan="3">
                               <input type="text" name="dbcColNm" id="dbcColNm" />
                           </td>
                        </tr>    
                   </tbody>
                 </table>   
            </div>
            </fieldset>
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div><!--를 사용하시면 됩니다.--><!--클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고-->

</form>

<!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<div style="clear:both; height:5px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>

<div style="clear:both; height:20px;"><span></span></div>
<div class="main_chart_01" id="chart1">
        	
            <script type="text/javascript">createIBChart("progChart", "100%", "350px");</script>
        </div>


</div>
</body>

</html>