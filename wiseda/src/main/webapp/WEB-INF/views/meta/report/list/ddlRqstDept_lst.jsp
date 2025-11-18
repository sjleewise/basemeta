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
		initGrid3();
});

//엔터키 처리한다.
/* EnterkeyProcess("Search"); */

$(window).on('load',function() {
	
});




 function initGrid3()
 {
     
     with(grid_sheet03){
     	
     	var cfg = {SearchMode:2,Page:100,SizeMode:0};
         SetConfig(cfg);
         
         SetMergeSheet(msHeaderOnly);
 		var headers = [
 						{Text:"<s:message code='META.HEADER.DDLRQSTDEPT.LST.1'/>"}
 					,{Text:"<s:message code='META.HEADER.DDLRQSTDEPT.LST.2'/>", Align:"Center"}
 						];
 						//구분|구분|총계|DDL요청서|DDL요청서|DDL요청서|기타요청서
 						//부서|파트|총계|생성|변경|삭제|기타요청서
 			
 			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
 			
 			InitHeaders(headers, headerInfo); 

 			var cols = [						
 						{Type:"Text",   Width:200,  SaveName:"deptNm",		Align:"Left", Edit:0},
 						{Type:"Text",   Width:200,  SaveName:"partNm",		Align:"Left", Edit:0},
 						{Type:"Text",   Width:150,  SaveName:"tot",			Align:"Right", Edit:0},
 						{Type:"Text",   Width:150,  SaveName:"insCnt",		Align:"Right", Edit:0},
 						{Type:"Text",   Width:150,  SaveName:"updCnt",		Align:"Right", Edit:0},
 						{Type:"Text",   Width:150,  SaveName:"delCnt",		Align:"Right", Edit:0},
 						{Type:"Text",   Width:70,  SaveName:"etcCnt",		Align:"Right", Edit:0, Hidden:1}
 					];
                     
         InitColumns(cols);
         
      //   SetColProperty("bizDtlCd", 	${codeMap.bizdtlcdibs});
         
         //SetColHidden("rqstUserNm",1);
         
         //업무구분을 한글명칭으로
      //   SetColProperty("bizDcd", ${codeMap.bizDcdibs});

         FitColWidth();  
         
         //마지막 시트의 헤더 풀사이즈
         SetExtendLastCol(1);    
     }
     
     //==시트설정 후 아래에 와야함=== 
     init_sheet(grid_sheet03);    
     //===========================
 }

 
 
 function chartDrow3(){

	 var categories = new Array();
	//카테고리 설정
	for(var i=0; i<= grid_sheet03.SearchRows(); i++) {
		categories.push (grid_sheet03.GetCellValue(i+1, "deptNm") + " > " + grid_sheet03.GetCellValue(i+1, "partNm") );
	}
	 
	 rqstChart3.SetOptions({
			Chart : {
				BackgroundColor : "#ffffff", //차트 배경색 설정
				Type : "column" ,           //차트 Type 설정
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
				Column : {
					PointPadding : 1 // 컬럼간의 간격 설정
					,PointWidth : 20 //포인트의 넓이를 설정한다.

				},
			},
			XAxis : {
				Categories : categories,
				TickInterval : 1, //X축 레이블 간격 설정
				Labels : { //X축 레이블 설정
					Enabled : true,
					Align : "center"
				},
				Title:{ //X축 제목 설정
					Text : "<s:message code='DEPT.PART.DSTC'/>" //부서/파트 구분
				}

			},
			YAxis : {
				TickInterval : 50, //Y축 레이블 간격 설정
				Min : 0, //Y축 Min값 설정
				Title : { //Y축 제목 설정
					Text : "<s:message code='DEPT.PART.AGGT'/>" //
				}
			}
		});


		//시리즈 생성
		var series = new Array();
		var deptNm = "";
		var partNm = "";
		var temp ="";
		for(var i=2; i<grid_sheet03.SearchRows()+2; i++){
			series[i-2] = rqstChart3.CreateSeries();
			//각 시리즈별 이름과 타입 설정
			deptNm = grid_sheet03.GetCellText(i, "deptNm");
			partNm = grid_sheet03.GetCellText(i, "partNm");
			temp = deptNm +" > "+ partNm;
			series[i-2].SetOptions({
				Name:temp,
				Type:"column"
			});	
		}
		//시리즈별 데이터 생성
		var points = new Array();
		var name = "<s:message code='AGGT' />"; //총계
		for(var i=2, j=0; i<grid_sheet03.SearchRows()+2; i++, j++){
			 points.push({X:i-1, Y:grid_sheet03.GetCellValue(i,"tot"),Name:name });
			 //각각 시리즈의 데이터를 담아 시리즈를 등록
			 series[j].AddPoints(points);
			 rqstChart3.AddSeries(series[j]);
			 points.pop();
		} 
		 
		rqstChart3.Draw();
 } 
 
 function grid_sheet03_OnSearchEnd(code, message, stCode, stMsg) {
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
		if($('#tabs-pt03').css("display") == 'block'){
			$('#div_chart_01').css("display", "none");
			$('#div_chart_02').css("display", "none");
			$('#div_chart_03').css("display", "block");
			$('#div_chart_04').css("display", "none");
			chartDrow3();
		}
	}
 


</script>

<!-- 그리드 입력 입력 -->
<div class="grid_01" id="grid_sheet03">
     <script type="text/javascript">createIBSheet("grid_sheet03", "100%", "250px");</script>            
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!--ibchart 생성  -->
<div class="main_chart_01" style="display: none;" id="div_chart_03" >
	    <script type="text/javascript">
	    	createIBChart("rqstChart3", "100%", "350px");
	    </script>
</div>
<!--ibchart END  -->