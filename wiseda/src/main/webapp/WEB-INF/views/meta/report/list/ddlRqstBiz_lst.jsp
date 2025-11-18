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
		initGrid2();
});

//엔터키 처리한다.
/* EnterkeyProcess("Search"); */

$(window).on('load',function() {
	
});




 function initGrid2()
 {
     
     with(grid_sheet02){
     	
     	var cfg = {SearchMode:2,Page:100,SizeMode:0};
         SetConfig(cfg);
         
         SetMergeSheet(msHeaderOnly);
 		var headers = [
 						{Text:"<s:message code='META.HEADER.DDLRQSTBIZ.LST.1'/>"}
 					,{Text:"<s:message code='META.HEADER.DDLRQSTBIZ.LST.2'/>", Align:"Center"}
 						];
 						//구분|구분|총계|DDL요청서|DDL요청서|DDL요청서
 						//시스템명|주제영역명|총계|생성|변경|삭제
 			
 			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
 			
 			InitHeaders(headers, headerInfo); 

 			var cols = [						
 						{Type:"Text",   Width:200,  SaveName:"sysAreaLnm",		Align:"Left",  Edit:0, ColMerge:1},
 						{Type:"Text",   Width:200,  SaveName:"fullPath",	Align:"Left",  Edit:0},
 						{Type:"Text",   Width:150,  SaveName:"tot",				Align:"Right", Edit:0},
 						{Type:"Text",   Width:200,  SaveName:"insCnt",			Align:"Right", Edit:0},
 						{Type:"Text",   Width:200,  SaveName:"updCnt",			Align:"Right", Edit:0},
 						{Type:"Text",   Width:200,  SaveName:"delCnt",			Align:"Right", Edit:0}
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
     init_sheet(grid_sheet02);    
     //===========================
 }
 
 

 
 function chartDrow2(){
	 rqstChart2.SetOptions({
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
				TickInterval : 1, //X축 레이블 간격 설정
				Categories : ['', '', '', '', '', '', '', '', '', '', '', ''],
				Labels : { //X축 레이블 설정
					Enabled : true,
					Align : "center"
				},
				Title:{ //X축 제목 설정
					Text : "<s:message code='SUBJ.TRRT.NM' />" //주제영역명
				}

			},
			YAxis : {
				TickInterval : 50, //Y축 레이블 간격 설정
				Min : 0, //Y축 Min값 설정
				Title : { //Y축 제목 설정
					Text : "<s:message code='SUBJ.TRRT.AGGT'/>" //주제영역별 총계
				}
			}
		});


		//시리즈 생성
		var series = new Array();
		var datamodelNm = "";
		var primarySubjNm = "";
		var temp ="";
		for(var i=2; i<grid_sheet02.SearchRows()+2; i++){
			series[i-2] = rqstChart2.CreateSeries();
			//각 시리즈별 이름과 타입 설정
			datamodelNm = grid_sheet02.GetCellText(i, "sysAreaLnm");
			primarySubjNm = grid_sheet02.GetCellText(i, "fullPath");
			temp = datamodelNm +" > "+ primarySubjNm;
			series[i-2].SetOptions({
				Name:temp,
				Type:"column"
			});	
		}
		//시리즈별 데이터 생성
		var points = new Array();
		var name = "<s:message code='AGGT' />"; //총계
		for(var i=2, j=0; i<grid_sheet02.SearchRows()+2; i++, j++){
			 points.push({X:i-1, Y:grid_sheet02.GetCellValue(i,"tot"),Name:name });
			 //각각 시리즈의 데이터를 담아 시리즈를 등록
			 series[j].AddPoints(points);
			 rqstChart2.AddSeries(series[j]);
			 points.pop();
		} 
		 
		rqstChart2.Draw();
 }; 

 
 function grid_sheet02_OnSearchEnd(code, message, stCode, stMsg) {
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
		if($('#tabs-pt02').css("display") == 'block'){
			$('#div_chart_01').css("display", "none");
			$('#div_chart_02').css("display", "block");
			$('#div_chart_03').css("display", "none");
			$('#div_chart_04').css("display", "none");
			chartDrow2();
		}

	}
</script>

<!-- 그리드 입력 입력 -->
<div class="grid_01" id="grid_sheet02">
     <script type="text/javascript">createIBSheet("grid_sheet02", "100%", "250px");</script>            
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!--ibchart 생성  -->
<div class="main_chart_01" style="display: none;" id="div_chart_02" >
	    <script type="text/javascript">
	    	createIBChart("rqstChart2", "100%", "350px");
	    </script>
</div>
<!--ibchart END  -->