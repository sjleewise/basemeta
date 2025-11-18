<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>



<!-- ibchart.js  -->
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
		$('#bizrule_sel_title').html(null);		
		}).show();

	

});

/* ibchart 설정  */
$(window).on('load',function(){
	createIBChart("bizruleChart", "bizruleChart", {
      width: "auto",
      height: "350px"
    });
	
	bizruleChart.setOptions({
		chart : {
			backgroundColor : "#ffffff", //차트 배경색 설정
			yype : "line" //차트 Type 설정
		},
		
		legend : {
			layout : "vertical", //Legend 모양 설정
			align : "right", //Legend 가로 정렬 설정
			verticalAlign : "top" //Legend 세로 정렬 설정
		},
		
		plotOptions : {
			series : {
				DataLabels : { //시리즈의 데이터 레이블 설정
					Enabled : true,
				}
			},
			column : {
				PointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		xAxis : {
			tickInterval : 1, //X축 레이블 간격 설정
			labels : { //X축 레이블 설정
				Enabled : true
			},
			title:{ //X축 제목 설정
				Text : "<s:message code='ANLY.ODR' />"/*분석차수*/

			}
		},
		yAxis : {
			tickInterval : 20, //Y축 레이블 간격 설정
			min : 0, //Y축 Min값 설정
			max :100,
			title : { //Y축 제목 설정
				Text : "<s:message code='QLTY.SCR.PER'/>" /*품질점수(%)*/
			}
		}
	});
	
	chartDrow();
	
});


function chartDrow()	{
		
		//차트 클리어
// 		bizruleChart.RemoveAll();
		
		//검색결과가 없을경우 리턴...
		if(grid_sheet1.SearchRows() == 0) return;
		
		//진단대상논리명 종류
		var tmpDbConnTrgLnm = "";
		var dbConnTrgLnm;
		
		//최대차수를 배열로 구현 각각의 시리즈에 다른값 적용하기 위해.
		var maxAnaDgr = new Array();
		var countAnaDgr=0;
		var count=0;
		
		//분기되는 row의 번호 1부터 시작한다.
		var tmpCount=1;
		var strCount=1;
		
		//프로파일컬럼 시작위치
		var tmpStrDbConnTrgLnm = "";
		var strDbConnTrgLnm;
		
		var minVal = 999;
		
		for(var i=0; i<grid_sheet1.SearchRows(); i++) {
			 
			//최대차수 계산...
			 countAnaDgr++;
			 tmpCount++;
			//진단대상논리명 추출(차수에 따른 중복 제거)
			if(grid_sheet1.GetCellText(i+1, "dbConnTrgLnm") != grid_sheet1.GetCellText(i+2, "dbConnTrgLnm")) {
				
				//각 시리즈에서 최대차수값을 저장.
				maxAnaDgr[count]=countAnaDgr;
				countAnaDgr=0;
				count++;
				
				if(tmpDbConnTrgLnm != "") {
					tmpDbConnTrgLnm += "|";
					tmpStrDbConnTrgLnm += "|";
				}
				tmpDbConnTrgLnm += grid_sheet1.GetCellText(i+1, "dbConnTrgLnm");	
				//각 시리즈의 첫번째 start값 
				tmpStrDbConnTrgLnm += strCount;
				strCount=tmpCount;
				
			}else{
				
			}
			
		}
		dbConnTrgLnm = tmpDbConnTrgLnm.split("|");
		strDbConnTrgLnm = tmpStrDbConnTrgLnm.split("|");
		/* alert(tmpDbConnTrgLnm);
		alert(tmpStrDbConnTrgLnm); */
		
		//시리즈 생성
		var series = new Array();

		for(var i=0; i<dbConnTrgLnm.length; i++) {
// 			series[i] = bizruleChart.CreateSeries();
			//각 시리즈별 이름과 타입 설정
			series[i] = {
				Name:dbConnTrgLnm[i],
				Type:"spline"
			};	
		}
		
		//시리즈별 데이터 생성
		var points = new Array();
		
		var category = new Array();
			
		for(var i=0; i<dbConnTrgLnm.length; i++) {
			points[i] = new Array();
			for(var j=0;j<maxAnaDgr[i];j++) {	
				var row = parseInt(strDbConnTrgLnm[i]) + parseInt(j);
				points[i].push({X:j, Y:grid_sheet1.GetCellValue(row, "erRate"), Name:grid_sheet1.GetCellValue(row, "anaDgrDisp")});
				category.push(grid_sheet1.GetCellValue(row, "anaDgrDisp"));
				if(grid_sheet1.GetCellValue(row, "erRate") < minVal) {
					minVal = grid_sheet1.GetCellValue(row, "erRate");
				}
			}
		}
// 		for(var i=0; i<dbConnTrgLnm.length; i++) {
// 			series[i].AddPoints(points[i]);
			
// 		}
		category = Array.from(new Set(category));
		
		for(var i=0; i<dbConnTrgLnm.length; i++) {
// 			bizruleChart.AddSeries(series[i]);
			series[i].data=points[i];
		}
		
		bizruleChart.setOptions({
			series : series,
			xAxis : {
				categories : category
			},
			yAxis : {
				TickInterval : 10, //Y축 레이블 간격 설정
				Min : minVal-1, //Y축 Min값 설정
				Max : 100 //Y축 Max값 설정
			}
		}, {
			 append : true
			,resetData : true
		});
		
		bizruleChart.draw();
};




function initGrid()
{
    with(grid_sheet1){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(1);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.BIZRULEPROG_LST'/>" ,Align:"Center"}
                ];
        //No.|진단대상ID|진단대상명|진단대상논리명|분석차수(숨김)|분석차수|분석일자|분석건수|오류건수|<s:message code="DIAG.TRGT.NM" /><!--진단대상명-->|DPMO|SIGMA

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0 ,ColMerge:0},
                    {Type:"Text",   	Width:100,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1,ColMerge:0},
                    {Type:"Text",   	Width:100,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:0, Hidden:1 ,ColMerge:1},
                    {Type:"Text",   	Width:200,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0 ,ColMerge:1},
                    {Type:"Text", 		Width:120,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1,ColMerge:0},
                    {Type:"Text", 		Width:240,   SaveName:"anaDgrDisp",  Align:"Center", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Date", 		Width:240,   SaveName:"anaStrDtm",  Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd",ColMerge:0},
                    {Type:"Int",   	Width:240,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Int",   	Width:240,   SaveName:"erCnt", 	Align:"Right", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Text",   	Width:240,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Text",   	Width:240,   SaveName:"dpmo", 	Align:"Right", Edit:0, ColMerge:0, Hidden:1},
                    {Type:"Text",   	Width:240,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0,ColMerge:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        InitComboNoMatchText(1, "");
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet1);    
    //===========================
   
}



function grid_sheet11_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

}


function grid_sheet1_OnSearchEnd(Code) {
	if(Code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공....
		//차트데이터 생성, .DoSearch()로 조회가 되었을때 실행
		chartDrow();
	}
	
}

 
</script>

<div style="clear:both; height:5px;"><span></span></div>
<div style="clear:both; height:5px;"><span></span></div>


<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet1", "100%", "250px");</script>            
</div>

<div style="clear:both; height:5px;"><span></span></div>
<div class="main_chart_01" id="bizruleChart" ></div>
