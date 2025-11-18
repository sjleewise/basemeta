<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="PEDC.LORQ.TRTT.PRES"/></title> <!-- 기간별 요청서 처리현황 -->

<!-- ibchart.js  -->


<script type="text/javascript">

$(document).ready(function() {
		initGrid();
});

//엔터키 처리한다.
/* EnterkeyProcess("Search"); */

$(window).on('load',function() {
	
});




 function initGrid()
 {
     
     with(grid_sheet01){
     	
      	var cfg = {SearchMode:2,Page:100,SizeMode:0};
        SetConfig(cfg);
        
        SetMergeSheet(msHeaderOnly);
 		var headers = [
 						{Text:"<s:message code='META.HEADER.DDLRQSTPERIOD.LST.1'/>"}
 					,{Text:"<s:message code='META.HEADER.DDLRQSTPERIOD.LST.2'/>", Align:"Center"}
 						];
 						//월|요청서현황|요청서현황|요청서현황|요청서현황|요청서현황|요청서현황|요청서현황|처리현황|처리현황|처리현황|처리현황
 						//월|DDL테이블|DDL테이블|DDL인덱스|DDL인덱스|기타오브젝트|등록완료건수|등록완료건수|DDL테이블|DDL인덱스|기타오브젝트|총처리건수
 			
 			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
 			
 			InitHeaders(headers, headerInfo); 

 			var cols = [						
 						{Type:"Text",   Width:100,  SaveName:"staDtm",		Align:"Left", Edit:0},
 						{Type:"Text",   Width:100,  SaveName:"ddlCnt",		Align:"Right", Edit:0, Hidden:1},
 						{Type:"Text",   Width:200,  SaveName:"ddlCntDisp",		Align:"Right", Edit:0},
 						{Type:"Text",   Width:100,  SaveName:"ddlIdxCnt",		Align:"Right", Edit:0, Hidden:1},
 						{Type:"Text",   Width:200,  SaveName:"ddlIdxCntDisp",		Align:"Right", Edit:0},
 						{Type:"Text",   Width:100,  SaveName:"dfcCnt",		Align:"Right", Edit:0, Hidden:1},
 						{Type:"Text",   Width:100,  SaveName:"tot",			Align:"Right", Edit:0, Hidden:1},
 						{Type:"Text",   Width:200,  SaveName:"totDisp",			Align:"Right", Edit:0},
 						{Type:"Text",   Width:150,  SaveName:"tDdlCnt",		Align:"Right", Edit:0},
 						{Type:"Text",   Width:150,  SaveName:"tDdlIdxCnt",		Align:"Right", Edit:0},
 						{Type:"Text",   Width:100,  SaveName:"tDfcCnt",		Align:"Right", Edit:0, Hidden:1},
 						{Type:"Text",   Width:150,  SaveName:"tTot",		Align:"Right", Edit:0}
 					];
                     
         InitColumns(cols);
         
      //   SetColProperty("bizDtlCd", 	${codeMap.bizdtlcdibs});
         
         //SetColHidden("rqstUserNm",1);
         
         //업무구분을 한글명칭으로
      //   SetColProperty("bizDcd", ${codeMap.bizDcdibs});

         FitColWidth();  
         
         SetExtendLastCol(1);    
     }
     
     //==시트설정 후 아래에 와야함=== 
     init_sheet(grid_sheet01);    
     //===========================
 }

 
 function chartDrow(){
	 var categoriesName =new Array();
	 for(var i=1; i<grid_sheet01.SearchRows()+2; i++){
		categoriesName.push(grid_sheet01.GetCellValue(i,"staDtm") );
	 }
	 rqstChart1.SetOptions({
			Chart : {
				BackgroundColor : "#ffffff", //차트 배경색 설정
				Type : "line" ,           //차트 Type 설정
				BorderColor : "#FFFFFF" 

			},
			
			Legend : {
				Layout : "vertical", //Legend 모양 설정
				Align : "right", //Legend 가로 정렬 설정
				VerticalAlign : "center" //Legend 세로 정렬 설정
			},
			
			PlotOptions : {
				Series : {
					DataLabels : { //시리즈의 데이터 레이블 설정
						Enabled : true,
					}
				},
				  Line:{
			             //선의 굵기
			             LineWidth:2
				}
			},
			XAxis : {
				Categories :categoriesName
				,
				TickInterval : 1, //X축 레이블 간격 설정
				Labels : { //X축 레이블 설정
					Enabled : true,
					Align : "center"
				},
				Title:{ //X축 제목 설정
					Text : "<s:message code='YR.MM'/>" //년/월
				},
				
				Offset : 10 //표시되는 X축의 간격표시
			},
			YAxis : {
				TickInterval : 50, //Y축 레이블 간격 설정
				Min : 0, //Y축 Min값 설정
				Title : { //Y축 제목 설정
					Text : "<s:message code='MTHY.TRTT.TCNT'/>" //월별 총처리건수
				}
			}
		});

		//시리즈 생성
		var name ="<s:message code='REG.CMPL.CCNT'/>"; //등록완료건수
		var	series= rqstChart1.CreateSeries();
			series.SetOptions({
				Name:name,
				Type:"line"
			});	
			
		//시리즈별 데이터 생성
		var points = new Array();
		var name = "<s:message code='TRTT.TCNT'/>"; //총처리건수
		for(var i=2, j=1; i<grid_sheet01.SearchRows()+2; i++, j++){
			 points.push({X:j, Y:grid_sheet01.GetCellValue(i,"tot"),Name:name });
			 //각각 시리즈의 데이터를 담아 시리즈를 등록
		} 
		series.AddPoints(points);
		rqstChart1.AddSeries(series);
		
		rqstChart1.Draw();
 };
 
 function grid_sheet01_OnSearchEnd(code, message, stCode, stMsg) {
	 if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
	 }
	 if(code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}
		//차트데이터 생성, .DoSearch()로 조회가 되었을때 실행
		 //tabs을 찍은상태에서 조회시
		if($('#tabs-pt01').css("display") == 'block'){
			$('#div_chart_01').css("display", "block");
			$('#div_chart_02').css("display", "none");
			$('#div_chart_03').css("display", "none");
			$('#div_chart_04').css("display", "none");
			chartDrow();
		}
 }

</script>

<!-- 그리드 입력 입력 -->
<div class="grid_01" id="grid_sheet01">
     <script type="text/javascript">createIBSheet("grid_sheet01", "100%", "250px");</script>            
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!--ibchart 생성  -->
<div class="main_chart_01" id="div_chart_01" style="display: none;" >
	    <script type="text/javascript">
	    	createIBChart("rqstChart1", "100%", "350px");
	    </script>
</div>
<!--ibchart END  -->