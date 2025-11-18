<!DOCTYPE html>
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
<%-- <title><s:message code="BZWR.RGR.QLTY.TRANSITION"/></title><!--업무규칙 품질추이--> --%>


<!-- ibchart.js  -->
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script> --%>


<script type="text/javascript">
$(document).ready(function() {

	//그리드 초기화 
 	initTrsGrid();
 	
	//탭 초기화
 	//$( "#tabs" ).tabs();
	
// 	$("#btnTreeNew").hide();
	
// 	$("#btnDelete").hide();
	
	
	
	
	
	//조회
// 	$("#btnSearch").click(function(){
		
// 		$('#bizrule_sel_title').html(null);
		
// 		doTrsAction("Search");
		
// 		}).show();

});

// EnterkeyProcess("Search");


/* ibchart 설정  */
$(window).on('load',function(){
	createIBChart("trsChart", "trsChart", {
      width: "600px",
      height: "500px"
    });
	
	trsChart.setOptions({
		chart : {
			backgroundColor : "#ffffff", //차트 배경색 설정
			type : "line" //차트 Type 설정
		},
		
		legend : {
			layout : "vertical", //Legend 모양 설정
			align : "right", //Legend 가로 정렬 설정
			verticalAlign : "top" //Legend 세로 정렬 설정
		},
		
		plotOptions : {
			series : {
				dataLabels : { //시리즈의 데이터 레이블 설정
					enabled : true,
				}
			},
			column : {
				pointPadding : 0.1 // 컬럼간의 간격 설정
			},
		}
// 		,XAxis : {
// 			tickInterval : 1, //X축 레이블 간격 설정
// 			labels : { //X축 레이블 설정
// 				enabled : true
// 			},
// 			title:{ //X축 제목 설정
// 				text : "<s:message code='ANLY.ODR' />"/*분석차수*/

// 			}
// 		},
// 		YAxis : {
// //				 TickInterval : 100,
// 			 Title : {
// 			 Text : "aaa"
// 			 }
// 		}
	});
	
	chartDraw();
	
});


function chartDraw()	{
		
		//차트 클리어
// 		trsChart.removeAll();
		
		//검색결과가 없을경우 리턴...
		if(trs_sheet.SearchRows() == 0) return;
		
		//시리즈 생성
		var series = new Array();

		for(var i=0; i<trs_sheet.SearchRows(); i++) {
// 			series[i] = trsChart.CreateSeries();
			//각 시리즈별 이름과 타입 설정
			series[i] = {
				Name:trs_sheet.GetCellText(i+1, "anaDgr")+"차",
				Type:"spline"
			};	
		}
		
		//시리즈별 데이터 생성
		var points = new Array();
		
		var category = new Array();
			
		for(var i=0; i<trs_sheet.SearchRows(); i++) {
			points[i] = new Array();
			
			for(var j=0;j<4;j++) {
				points[j] = new Array();
				
				points[j].push({ Y:trs_sheet.GetCellValue(i+1, "minVal1"),   Name:trs_sheet.GetCellValue(0, "minVal1")});
				points[j].push({ Y:trs_sheet.GetCellValue(i+1, "maxVal1"),   Name:trs_sheet.GetCellValue(0, "maxVal1")});
				points[j].push({ Y:trs_sheet.GetCellValue(i+1, "stddevVal"), Name:trs_sheet.GetCellValue(0, "stddevVal")});
				points[j].push({ Y:trs_sheet.GetCellValue(i+1, "avgVal"),    Name:trs_sheet.GetCellValue(0, "avgVal")});
				
			}
			series[i].data=points[i];
			
		}
		
		category.push(trs_sheet.GetCellValue(0, "minVal1"));
		category.push(trs_sheet.GetCellValue(0, "maxVal1"));
		category.push(trs_sheet.GetCellValue(0, "stddevVal"));
		category.push(trs_sheet.GetCellValue(0, "avgVal"));	
		
		category = Array.from(new Set(category));
		
		console.log("series2");
		console.log(series);
		
		
		trsChart.setOptions({
			series : series,
			xAxis : {
				categories : category,
				tickInterval : 1, //X축 레이블 간격 설정
				labels : { //X축 레이블 설정
					enabled : true
				},
				title:{ //X축 제목 설정
					text : "<s:message code='ANLY.ODR' />"/*분석차수*/

				}
			}
			,yAxis : {
				 tickInterval : 1,
				 title : {
					 },
			     labels : {
			    	 enabled : false
			     }
			}
	
		
		

		}, {
			 append : true
			,resetData : true
		});
		
// 		trsChart.SetYAxisOptions({
// 			Title:{
// 			 Text:"Y축 타이틀" //타이틀 설정
// 			 },
// // 			 GridLineWidth : 2, //라인 넓이설정
// // 			 TickInterval : 10 // Tick 간격을 10으로 설정
// 			}, 1);

		
		trsChart.draw();
		
// 		trsChart.addSeries(series);
};




function initTrsGrid()
{
    with(trs_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(1);
        
        var headerTxt = "No.|프로파일ID|분석차수|최소값|최대값|표준편차|평균";
        
        var headers = [
						{Text:headerTxt ,Align:"Center"}
//                     {Text:"<s:message code='DQ.HEADER.BIZRULEPROG_LST'/>" ,Align:"Center"}
                ];
        //No.|프로파일ID|분석차수|최소값|최대값|표준편차|평균

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0 ,ColMerge:0},
                    {Type:"Text",   	Width:50,  SaveName:"prfId",   	Align:"Left", Edit:0, Hidden:1,ColMerge:0},
                    {Type:"Text", 		Width:60,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Text", 		Width:60,   SaveName:"minVal1",  Align:"Center", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Text", 		Width:60,   SaveName:"maxVal1",  Align:"Center", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Text", 		Width:60,   SaveName:"stddevVal",  Align:"Center", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Text", 		Width:60,   SaveName:"avgVal",  Align:"Center", Edit:0, Hidden:0,ColMerge:0},
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        InitComboNoMatchText(1, "");
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(trs_sheet);    
    //===========================
   
    doTrsAction("Search");
}



function doTrsAction(sAction)
{
        
    switch(sAction)
    {
	    case "Search":
	    	var param =  $("#frmSearch").serialize();
	    	trs_sheet.DoSearch("<c:url value="/dq/report/ajaxgrid/getColAnaTrsLst.do" />", param);
	    	
	    	break;
	    	
  		case "Down2Excel":  //엑셀내려받기
            trs_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'업무규칙품질추이'});
//             progChart.Down2Image({FileName:"BizruleProg", Type:IBExportType.JPEG, Width:800, Url:"./../../../js/IBChart/jsp/Down2Image.jsp"});
            break;
    }
}


function trs_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

}


function trs_sheet_OnSearchEnd(Code) {
	if(Code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공....
		//차트데이터 생성, .DoSearch()로 조회가 되었을때 실행
		chartDraw();
	}
	
}

 
</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="BZWR.RGR.QLTY.TRANSITION"/></div><!--업무규칙 품질추이-->

	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<%-- <div style="clear:both; height:5px;"><span></span></div> --%>
<%-- 	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
<%-- <div style="clear:both; height:5px;"><span></span></div> --%>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("trs_sheet", "100%", "250px");</script>            
</div>

<div style="clear:both; height:5px;"><span></span></div>
	
	<div class="chart" id="trsChart" >
	</div>
</div>

</body>
</html>