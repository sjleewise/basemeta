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
<title><s:message code="PROF.INQ"/></title><!--프로파일조회-->

<script type="text/javascript">

var interval = "";
var param = "";
var anaStrDtms  = "";
var objId = "";
var prfKndCds = "";
var esnErCnt = "";
var grid_nm = new Object();
var selectedSheetIndex=1;		//추가됨


var connTrgSchJson = ${codeMap.connTrgSch} ;

$(document).ready(function() {
	
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	//$( "#tabs" ).tabs();
	//그리드 초기화 
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	
	
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
	// 업무규칙전환 엑셀내리기 Event Bind
	$("#btnBrExcelDown").click( function(){
		doAction("SearchPrfBrTransfer"); 
	} ).show();
	
	
	$("#btnPdfDown").click(function() {
		doAction("Down2Pdf");
	}).hide();
	
	$("#btnHwpDown").click(function() {
		doAction("Down2Hml");
	}).hide();
	
	$("#poiDown").click( function() {
		doAction("poiDown");
	}).hide();
	
	//등록버튼 hidden
	$("#btnPrfSave").hide();

	//분석실행
	$("#btnExec").click(function(){ 
		//스케줄 등록
		regScheduler();		
	});
	
	//삭제버튼 hidden
	$("#btnPrfDelete").hide();
	
	//삭제버튼 hidden
	$("#btnPrfSchRqst").hide();
	
	 //프로파일 텝
// 	$("#tabs").attr("style","width:99%;float:right;");
	  
	//데이터패턴
	$("#btnPattenSearch").click(function(){ 
		getDataPattern();
	});
	
	//로그조회
	$("#btnLogSearch").click(function(){ 
		 getAnaLog();
	});
	
	//분석SQL 보기
	$("#btnSqlSearch").click(function(){ 
		SearchSql();
	});
	
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbSchLnm"), "DBSCH");
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	
	//======================================================
	 // 셀렉트 박스 초기화
	//======================================================
	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
		double_select(connTrgSchJson, $(this));
	});
	
	
	//텝 클릭
	grid_nm = grid_pt01;
	$( "#tab-PT01 a" ).click(function(){
		$('#prfKndCd').val('PT01');
		grid_nm = grid_pt01;
		objId = "";
		selectedSheetIndex=1;
	});
	$( "#tab-PT02 a" ).click(function(){
		$('#prfKndCd').val('PT02');
		grid_nm = grid_pt02;
		objId = "";
		selectedSheetIndex=2;
	});
	$( "#tab-PC01 a" ).click(function(){
		$('#prfKndCd').val('PC01');
		grid_nm = grid_pc01;
		objId = "";
		selectedSheetIndex=3;
	});
	$( "#tab-PC02 a" ).click(function(){
		$('#prfKndCd').val('PC02');
		grid_nm = grid_pc02;
		objId = "";
		selectedSheetIndex=4;
	});
	$( "#tab-PC03 a" ).click(function(){
		$('#prfKndCd').val('PC03');
		grid_nm = grid_pc03;
		objId = "";
		selectedSheetIndex=5;
	});
	$( "#tab-PC04 a" ).click(function(){
		$('#prfKndCd').val('PC04');
		grid_nm = grid_pc04;
		objId = "";
		selectedSheetIndex=6;
	});
	$( "#tab-PC05 a" ).click(function(){
		$('#prfKndCd').val('PC05');
		grid_nm = grid_pc05;
		objId = "";
		selectedSheetIndex=7;
	});

	// 탭클릭 이벤트 
	$( "#tabs" ).tabs({
	  activate: function( event, ui ) {
		  
		  var selectedTab =  $("#tabs").tabs('option', 'active'); 
		    
		  //alert('selectedTab : ' + selectedTab); 
		  
		  switch (selectedTab) {
		  	case 0:		  		  		
		  		if(!init_grid_pt01) initprfpt01grid();		  		
		  		break;
			case 1:									
				if(!init_grid_pt02) initprfpt02grid();
		  		break;
			case 2:		  					
				if(!init_grid_pc01) initprfpc01grid();
		  		break;
			case 3:		  				  					
				if(!init_grid_pc02) initprfpc02grid();
		  		break;
			case 4:		  
				if(!init_grid_pc03) initprfpc03grid();
		  		break;
			case 5:		  	
				if(!init_grid_pc04) initprfpc04grid();
		  		break;	
			case 6:		  	
				if(!init_grid_pc05) initprfpc05grid();
		  		break;		
		  }
	  }
	});

	
});

$(window).on('load',function() {
	//업무규칙전환 엑셀다운로드에 필요
	initGridBr();
	
	//PK값으로 검색
	var prfId ="";
	var prfKndCd="";
	prfId="${search.prfId}";
	prfKndCd="${search.prfKndCd}";
	$("#frmSearch #prfId").val(prfId);
	$("#frmSearch #prfKndCd").val(prfKndCd);
	if((prfId != null && prfId != "" && prfId !="undefined") || (prfKndCd != null && prfKndCd != "" && prfKndCd !="undefined")){
		doAction("Search");
	}

	detailPt01();
	
	var linkFlag = "";
	linkFlag = "${linkFlag}";
	if(linkFlag == "1"){
		doAction("Search");
	}
	
	
		
});


$(window).resize(function() {

});

EnterkeyProcess("Search");


function initGridBr()
{
    
    with(grid_br){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
   					{Text:"<s:message code='DQ.HEADER.PROFILE_LST'/>", Align:"Center"}
   				];
        //No.|상태|검토상태|검토내용|요청구분|등록유형|검증결과|업무영역ID|업무영역명|업무규칙ID|업무규칙명|업무규칙담당자ID|업무규칙담당자|진단대상ID|진단대상명|테이블명|컬럼명|품질지표ID|품질지표명|중요정보항목ID|중요정보항목명|사용여부|설명|건수SQL|분석SQL검증대상DBID|검증대상DB명|검증테이블명|검증컬럼명|검증비교KEY컬럼|검증비교값컬럼|검증JOIN방식|건수SQL(검증)|분석SQL(검증)|표준편차|분산|평균|유일값수|최소빈도값|최대빈도값

        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:0},
                    {Type:"Combo",      Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",       Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",      Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",      Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Combo",      Width:120,  SaveName:"vrfCd",		Align:"Center", Edit:0, Hidden:1},	
                    {Type:"Text",   	Width:100,  SaveName:"bizAreaId",    	Align:"Center", Edit:1, Hidden:1}, 
                    {Type:"Text",   	Width:100,  SaveName:"bizAreaLnm",    	Align:"Center", Edit:0, Hidden:0, KeyField:1}, 
                    {Type:"Text",   	Width:100,  SaveName:"brId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"brNm",   	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,  SaveName:"brCrgpUserId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"brCrgpUserNm",   	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgPnm",   	Align:"Center", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbSchPnm",   		Align:"Center", Edit:0, Hidden:0, KeyField:0},
                    {Type:"Text",   	Width:90,   SaveName:"dbcTblNm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:70,   SaveName:"dbcColNm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:70,   SaveName:"dqiId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"dqiLnm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:70,   SaveName:"ctqId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"ctqLnm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:30,   SaveName:"useYn", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:30,   SaveName:"objDescn", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"cntSql", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"anaSql", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"tgtDbConnTrgId", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"tgtDbcTblNm", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"tgtDbcColNm", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"tgtKeyDbcColNm", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"tgtKeyDbcColVal", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"tgtCntSql", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"tgtAnaSql", 	Align:"Center", Edit:0, Hidden:0}
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
        SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
		SetColProperty("vrfCd", ${codeMap.vrfCdibs});

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        SetColHidden("ibsStatus"	,1);
        SetColHidden("objVers"		,1);
      
        FitColWidth();  
        
//         SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_br);    
    //===========================
}

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	param = $('#frmSearch').serialize();
        	var prfKndCd =$('#prfKndCd').val();
        	
        	if(prfKndCd==""){
        		detailPt01();
        		grid_pt02.RemoveAll();
        		grid_pc01.RemoveAll();
        		grid_pc02.RemoveAll();
        		grid_pc03.RemoveAll();
        		grid_pc04.RemoveAll();
        		grid_pc05.RemoveAll();
//         		detailPt02();
//         		detailPc01();
// 	        	detailPc02();
// 	        	detailPc03();
// 	        	detailPc04();
// 	        	detailPc05();
        	}else if (prfKndCd=="PT01"){
        		$( "#tabs" ).tabs( "option", "active", 0);
        		detailPt01();
        		grid_pt02.RemoveAll();
        		grid_pc01.RemoveAll();
        		grid_pc02.RemoveAll();
        		grid_pc03.RemoveAll();
        		grid_pc04.RemoveAll();
        		grid_pc05.RemoveAll();
        	}else if (prfKndCd=="PT02"){
        		$( "#tabs" ).tabs( "option", "active", 1);
        		grid_pt01.RemoveAll();
        		detailPt02();
        		grid_pc01.RemoveAll();
        		grid_pc02.RemoveAll();
        		grid_pc03.RemoveAll();
        		grid_pc04.RemoveAll();
        		grid_pc05.RemoveAll();
        	}else if (prfKndCd=="PC01"){
        		$( "#tabs" ).tabs( "option", "active", 2);
        		grid_pt01.RemoveAll();
        		grid_pt02.RemoveAll();
        		detailPc01();
        		grid_pc02.RemoveAll();
        		grid_pc03.RemoveAll();
        		grid_pc04.RemoveAll();
        		grid_pc05.RemoveAll();
        	}else if (prfKndCd=="PC02"){
            	$( "#tabs" ).tabs( "option", "active", 3 );
            	grid_pt01.RemoveAll();
        		grid_pt02.RemoveAll();
        		grid_pc01.RemoveAll();
	     		detailPc02();
	     		grid_pc03.RemoveAll();
        		grid_pc04.RemoveAll();
        		grid_pc05.RemoveAll();
     		}else if (prfKndCd=="PC03"){
     			$( "#tabs" ).tabs( "option", "active", 4 );
     			grid_pt01.RemoveAll();
        		grid_pt02.RemoveAll();
        		grid_pc01.RemoveAll();
        		grid_pc02.RemoveAll();
	     		detailPc03();
	     		grid_pc04.RemoveAll();
        		grid_pc05.RemoveAll();
     		}else if (prfKndCd=="PC04"){
     			$( "#tabs" ).tabs( "option", "active", 5 );
     			grid_pt01.RemoveAll();
        		grid_pt02.RemoveAll();
        		grid_pc01.RemoveAll();
        		grid_pc02.RemoveAll();
        		grid_pc03.RemoveAll();
	     		detailPc04();
	     		grid_pc05.RemoveAll();
     		}else if (prfKndCd=="PC05"){
     			$( "#tabs" ).tabs( "option", "active", 6 );
     			grid_pt01.RemoveAll();
        		grid_pt02.RemoveAll();
        		grid_pc01.RemoveAll();
        		grid_pc02.RemoveAll();
        		grid_pc03.RemoveAll();
        		grid_pc04.RemoveAll();
	     		detailPc05();
     		}	
        	
        	break;
       
        	
        case "Down2Excel":  //엑셀내려받기
        	
        	grid_pt01.Down2ExcelBuffer(true);  
        
        	if(grid_pt01.SearchRows()>0){
        		grid_pt01.Down2Excel({FileName:'프로파일조회_관계분석',SheetName:'<s:message code="RLT.ANLY"/>', HiddenColumn:1, Merge:1}); /*관계분석*/
        	}
        	if(grid_pt02.SearchRows()>0){
        		grid_pt02.Down2Excel({FileName:'프로파일조회_중복분석',SheetName:'중복분석', HiddenColumn:1, Merge:1}); 
        	}
        	if(grid_pc01.SearchRows()>0){
        		grid_pc01.Down2Excel({FileName:'프로파일조회_컬럼분석',SheetName:'<s:message code="CLMN.ANLY"/>', HiddenColumn:1, Merge:1}); /*컬럼분석*/
        	}
        	if(grid_pc02.SearchRows()>0){
        		grid_pc02.Down2Excel({FileName:'프로파일조회_코드분석',SheetName:'<s:message code="CD.ANLY"/>', HiddenColumn:1, Merge:1}); /*코드분석*/
        	}
        	if(grid_pc03.SearchRows()>0){
        		grid_pc03.Down2Excel({FileName:'프로파일조회_날짜형식분석',SheetName:'날짜형식분석', HiddenColumn:1, Merge:1}); 
        	}
        	if(grid_pc04.SearchRows()>0){
        		grid_pc04.Down2Excel({FileName:'프로파일조회_범위분석',SheetName:'범위분석', HiddenColumn:1, Merge:1}); 
        	}
        	if(grid_pc05.SearchRows()>0){
        		grid_pc05.Down2Excel({FileName:'프로파일조회_문자열패턴분석',SheetName:'<s:message code="PTRN.ANLY"/>', HiddenColumn:1, Merge:1}); /*<!--패턴분석-->*/
        	}
        
        	if(grid_pt01.SearchRows()==0 && grid_pt02.SearchRows()==0 &&
       			grid_pc01.SearchRows()==0 && grid_pc02.SearchRows()==0 &&
       			grid_pc03.SearchRows()==0 && grid_pc04.SearchRows()==0 &&
       			grid_pc05.SearchRows()==0  ){
        		return;
        	}
        	
        	grid_pt01.Down2ExcelBuffer(false);        
        
            break;
            
        case "SearchPrfBrTransfer":
			var sRow = grid_nm.FindCheckedRow("ibsCheck");
			if(sRow == ""){
				showMsgBox("ERR", "<s:message code='DATA.CHC' />"); /*데이터를 선택하십시오.*/
				return;
			} 
			
	    	var arrRow = sRow.split("|");
	    	var tmpkey = "";
	    	for(var idx=0; idx<arrRow.length; idx++){ 
	    		tmpkey += grid_nm.GetCellValue(arrRow[idx], 'prfId') +"|";
			}

	    	grid_br.RemoveAll();
			var param = "prfId="+tmpkey;
			grid_br.DoSearch("<c:url value="/dq/profile/getPrfBrTransfer.do" />", param);
			
        	break;
        	
        case "goBizruleRqst":
			var sRow = grid_nm.FindCheckedRow("ibsCheck");
			if(sRow == ""){
				showMsgBox("ERR", "<s:message code='DATA.CHC' />"); /*데이터를 선택하십시오.*/
				return;
			} 
			
	    	var arrRow = sRow.split("|");
	    	var tmpkey = "";
	    	for(var idx=0; idx<arrRow.length; idx++){ 
	    		tmpkey += grid_nm.GetCellValue(arrRow[idx], 'prfId') +"|";
			}

	    	grid_br.RemoveAll();
			var param = "prfId="+tmpkey;
			grid_br.DoSearch("<c:url value="/dq/profile/getPrfBrTransfer.do" />", param);
			
        	break;
        case "Down2Pdf":
        	var pdfParam = {FileName:'profile_list', Dpi:5500, paaper:"landscape"}
            
        	if(grid_pt01.SearchRows()>0){
        		grid_pt01.Down2Pdf(pdfParam); /*관계분석*/
        	}
        	if(grid_pc01.SearchRows()>0){
        		grid_pt02.Down2Pdf(pdfParam); 
        	}
        	if(grid_pc01.SearchRows()>0){
        		grid_pc01.Down2Pdf(pdfParam); /*컬럼분석*/
        	}
        	if(grid_pc02.SearchRows()>0){
        		grid_pc02.Down2Pdf(pdfParam); /*코드분석*/
        	}
        	if(grid_pc03.SearchRows()>0){
        		grid_pc03.Down2Pdf(pdfParam); 
        	}
        	if(grid_pc04.SearchRows()>0){
        		grid_pc04.Down2Pdf(pdfParam); 
        	}
        	if(grid_pc05.SearchRows()>0){
        		grid_pc05.Down2Pdf(pdfParam); /*<!--패턴분석-->*/
        	}
        
        	if(grid_pt01.SearchRows()==0 && grid_pt02.SearchRows()==0 &&
       			grid_pc01.SearchRows()==0 && grid_pc02.SearchRows()==0 &&
       			grid_pc03.SearchRows()==0 && grid_pc04.SearchRows()==0 &&
       			grid_pc05.SearchRows()==0  ){
        		return;
        	}
        	
        	break;
        	
        case "Down2Hml":
        	var pdfParam = {FileName:'profile_list'}
            
        	if(grid_pt01.SearchRows()>0){
        		grid_pt01.Down2Hml(pdfParam); /*관계분석*/
        	}
        	if(grid_pc01.SearchRows()>0){
        		grid_pt02.Down2Hml(pdfParam); 
        	}
        	if(grid_pc01.SearchRows()>0){
        		grid_pc01.Down2Hml(pdfParam); /*컬럼분석*/
        	}
        	if(grid_pc02.SearchRows()>0){
        		grid_pc02.Down2Hml(pdfParam); /*코드분석*/
        	}
        	if(grid_pc03.SearchRows()>0){
        		grid_pc03.Down2Hml(pdfParam); 
        	}
        	if(grid_pc04.SearchRows()>0){
        		grid_pc04.Down2Hml(pdfParam); 
        	}
        	if(grid_pc05.SearchRows()>0){
        		grid_pc05.Down2Hml(pdfParam); /*<!--패턴분석-->*/
        	}
        
        	if(grid_pt01.SearchRows()==0 && grid_pt02.SearchRows()==0 &&
       			grid_pc01.SearchRows()==0 && grid_pc02.SearchRows()==0 &&
       			grid_pc03.SearchRows()==0 && grid_pc04.SearchRows()==0 &&
       			grid_pc05.SearchRows()==0  ){
        		return;
        	}
        	
        	break;
        
        case "poiDown":
        	//searchBox의 DBMS및 스키마명 선택된 것
        	var dbmsName=$('#dbConnTrgId').val();		//DBMS
        	var schName=$('#dbSchId').val();			//스키마명
        	
        	if(!dbmsName || !schName){			//DBMS와 스키마명이 --선택-- 으로 되어있는 경우 종료
        		showMsgBox("ERR", "DBMS와 스키마명을 지정해주십시오.");
        		return;
        	}
        	
        	$('#dbCodeValue').val(dbmsName);		//선택된 DBMS의 값
        	$('#schCodeValue').val(schName);		//선택된 스키마의 값
        	$('#sheetNum').val(selectedSheetIndex);	//선택된 분석의 종류, 1~7까지 존재
        	
        	//hidden input을 가지고있는 poiForm
        	var myForm = document.poiForm;
        	var url ='<c:url value="/dq/profile/poiDown.do" />';
        	
        	myForm.action=url;
        	myForm.method="post";
        	myForm.target="poiForm";
        	myForm.submit();	//dbCodeValue, schCodeValue, sheetNum 세가지의 값을 POST로 넘김
        	
        	break;
            
    }       
}

var init_grid_pt01 = false;
var init_grid_pt02 = false;
var init_grid_pc01 = false;
var init_grid_pc02 = false;
var init_grid_pc03 = false;
var init_grid_pc04 = false;
var init_grid_pc05 = false;
//상세정보호출
function detailPt01() {
	if (!init_grid_pt01) {
// 		alert("그리드초기화");
		initprfpt01grid();
		init_grid_pt01 = true;
	}
// 	$( "#tab-PT01 a" ).click();
	var param = $('#frmSearch').serialize();
	grid_pt01.DoSearch("<c:url value="/dq/profile/profileListRelAna.do" />", param);
}
function detailPt02() {
	if (!init_grid_pt02) {
// 		alert("그리드초기화");
		initprfpt02grid();
		init_grid_pt02 = true;
	}
// 	$( "#tab-PT02 a" ).click();
	var param = $('#frmSearch').serialize();
	grid_pt02.DoSearch("<c:url value="/dq/profile/profileListUnqAna.do" />", param);
}
function detailPc01() {
	if (!init_grid_pc01) {
// 		alert("그리드초기화");
		initprfpc01grid();
		init_grid_pc01 = true;
	}
// 	$( "#tab-PC01 a" ).click();
	var param = $('#frmSearch').serialize();
	grid_pc01.DoSearch("<c:url value="/dq/profile/profileListColAna.do" />", param);
}
function detailPc02() {
	if (!init_grid_pc02) {
// 		alert("그리드초기화");
		initprfpc02grid();
		init_grid_pc02 = true;
	}
// 	$( "#tab-PC02 a" ).click();
	var param = $('#frmSearch').serialize();
	grid_pc02.DoSearch("<c:url value="/dq/profile/profileListCodeAna.do" />", param);
}
function detailPc03() {
	if (!init_grid_pc03) {
// 		alert("그리드초기화");
		initprfpc03grid();
		init_grid_pc03 = true;
	}
// 	$( "#tab-PC03 a" ).click();
	var param = $('#frmSearch').serialize();
	grid_pc03.DoSearch("<c:url value="/dq/profile/profileListDateAna.do" />", param);
}
function detailPc04() {
	if (!init_grid_pc04) {
// 		alert("그리드초기화");
		initprfpc04grid();
		init_grid_pc04 = true;
	}
// 	$( "#tab-PC04 a" ).click();
	var param = $('#frmSearch').serialize();
	grid_pc04.DoSearch("<c:url value="/dq/profile/profileListRangeAna.do" />", param);
}
function detailPc05() {
	if (!init_grid_pc05) {
// 		alert("그리드초기화");
		initprfpc05grid();
		init_grid_pc05 = true;
	}
// 	$( "#tab-PC05 a" ).click();
	var param = $('#frmSearch').serialize();
	grid_pc05.DoSearch("<c:url value="/dq/profile/profileListPatternAna.do" />", param);
}

//프로파일 상세 팝업
function prfDtlPopup(param){
  	var url = '<c:url value="/dq/profile/popup/prfdtl_pop.do"/>';
	var popwin = OpenModal(url+"?"+param, "prfDtlPop",  1100, 750, "yes"); 
	popwin.focus();
} 

//데이터패턴 조회
function getDataPattern(){
//  	var objId = prfId;
// 	var anaStrDtm = $("form[name=frmSearch] #anaStrDtm").val().replace(/ /g, ''); 
	
	if(objId == ""){
		showMsgBox("ERR", "<s:message code="INQ.DATA.SEL" />"); /*조회할 데이터를 선택하십시오.*/
		
		return;
	}else if(prfKndCds == "PC02"){
		if(esnErCnt == ""){
			showMsgBox("ERR", "<s:message code="DATA.PTRN.DETL.EXIS.NO" />");/*데이터패턴 내역이 존재하지 않습니다*/
			
			return;
		}
	}
	
 	var param = "?objId="+objId;
	     param += "&objDate="+anaStrDtms.replace(/ /g, ''); 
	     param += "&objIdCol=PRF_ID";		  
         param += "&objResTbl=WAM_PRF_RESULT";
	     param += "&objErrTbl=WAM_PRF_ERR_DATA";
	     param += "&erDataSnoCol=ESN_ER_DATA_SNO";
	     param += "&erDataSno="+0;
         param += "&objGb="+prfKndCds;        
//          param += "&objGb="+$("form[name=frmSearch] #prfKndCd").val();        
         
	
    var url = '<c:url value="/dq/report/popup/datapattern_pop.do" />';
 	var popup = OpenWindow(url+param, "DATA_PATTERN", "800", "600", "yes"); 
}

//로그조회
function getAnaLog(){
// 	var objId = $("form[name=frmSearch] #prfId").val();
	if(objId == ""){
		showMsgBox("ERR", "<s:message code="INQ.DATA.SEL" />"); /*조회할 데이터를 선택하십시오.*/

		return;
	}
	var param = "?objId="+objId;
		param += "&objResTbl=WAM_PRF_RESULT";
    var url = '<c:url value="/dq/report/popup/analog_pop.do" />';
    var popup = OpenWindow(url+param, "LOG_SEARCH", "800", "600", "yes");
}

//분석SQL조회 
function SearchSql(){
// 	var prfId = $("form[name=frmSearch] #prfId").val();
	
	if(objId == ""){
		var message = "<s:message code="INQ.PROF.CHC.1" />"; /* 조회할 프로파일을 선택하십시오. */
		showMsgBox("INF", message); 
		return;
	}
	
	var url   = "<c:url value="/dq/profile/popup/sql_pop.do"/>";
	var param = "?prfId=" + objId ;
	     param += "&prfKndCd=" + prfKndCds;
	var popup = OpenWindow(url+param,"SQL","800","650","yes");
}

//실시간실행 스케줄 등록
function regScheduler(){
	if(grid_nm == ""){
		grid_nm = grid_pt01;
	}
	var ibsSaveJson = grid_nm.GetSaveJson(0);
	if(ibsSaveJson.data.length == 0){
		showMsgBox("ERR", "<s:message code="ANLY.DATA.CHC" />"); /* 분석할 데이터를 선택하십시오. */
		return;
	}
	
	var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
	var param = "&shdKndCd=QP"; //프로파일
    IBSpostJson2(urls, ibsSaveJson, param, schedulerCallBack);
}

function schedulerCallBack(data){
	//스케줄등록 오류
	if(data.RESULT.CODE < 0){
		showMsgBox("ERR", data.RESULT.MESSAGE); 
	}else{
		showMsgBox("INF", data.RESULT.MESSAGE); 
	}
}


function grid_br_OnSearchEnd (code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		var params = { FileName : "profileBrTransfer.xls",  SheetName : "Sheet", HiddenColumn:1, Merge:1} ;
		grid_br.Down2Excel(params);
		//조회 성공....
	}
}

// 검색조건 중 프로파일 종류 콤보값 변경시 grid_nm 업데이트
// 처음 화면 검색조건에 관계분석이 아닌 프로파일 종류 지정하고 조회 후 분석실행하면 "분석할 데이터를 선택하십시오." 문제 해결을 위해 추가
function prfKndCdChg(){
	var prfkndcd = $('#frmSearch #prfKndCd').val();
	
	switch (prfkndcd){
	 case "PT01":
		 grid_nm = grid_pt01;
		 break;
	 case "PT02":
		 grid_nm = grid_pt02;
		 break;
	 case "PC01":
		 grid_nm = grid_pc01;
		 break;
	 case "PC02":
		 grid_nm = grid_pc02;
		 break;
	 case "PC03":
		 grid_nm = grid_pc03;
		 break;
	 case "PC04":
		 grid_nm = grid_pc04;
		 break;
	 case "PC05":
		 grid_nm = grid_pc05;
		 break;
	 default:
		 grid_nm = grid_pt01;
	     break;
	}
	
}

// function poiDown(){

// 	//searchBox의 DBMS및 스키마명 선택된 것
// 	var dbmsName=$('#dbConnTrgId').val();		//DBMS
// 	var schName=$('#dbSchId').val();			//스키마명
	
// 	if(!dbmsName || !schName){			//DBMS와 스키마명이 --선택-- 으로 되어있는 경우 종료
// 		alert("DBMS와 스키마명을 지정해주십시오.");
// 		return;
// 	}
	
// 	$('#dbCodeValue').val(dbmsName);		//선택된 DBMS의 값
// 	$('#schCodeValue').val(schName);		//선택된 스키마의 값
// 	$('#sheetNum').val(selectedSheetIndex);	//선택된 분석의 종류, 1~7까지 존재
	
// 	//hidden input을 가지고있는 poiForm
// 	var myForm = document.poiForm;
// 	var url ='<c:url value="/dq/profile/poiDown.do" />';
	
// 	myForm.action=url;
// 	myForm.method="post";
// 	myForm.target="poiForm";
// 	myForm.submit();	//dbCodeValue, schCodeValue, sheetNum 세가지의 값을 POST로 넘김
	
// }
</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PROF.INQ" /></div><!--프로파일 조회-->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        <input type="hidden" name="sheetRow" id="sheetRow"  />
        <input type="hidden" name="prfId" id="prfId" />
        <input type="hidden" name="anaStrDtm" id="anaStrDtm" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.INQ'/>"><!--프로파일조회-->

                   <caption><s:message code="PROF.INQ"/></caption><!--프로파일조회-->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:20%;" />
                   <col style="width:15%;" />
                   <col style="width:20%;" />
                   <col style="width:15%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class=""><label for="dbSchId"><s:message code="DB.MS.SCHEMA.NM"/></label></th><!--진단대상명/스키마명-->

                      <td>
			            <select id="dbConnTrgId" class="" name="dbConnTrgId">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			            </select>
			            <select id="dbSchId" class="" name="dbSchId">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			             </select>
			           </td>
                           
                           
                           <th scope="row"><label for="prfKndCd"><s:message code="PROF.KIND"/>
							</label></th><!--프로파일종류-->
	                         <td>
                               <select id="prfKndCd"  name="prfKndCd" onchange="prfKndCdChg()">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.prfKndCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
                           </td>
	                        <th scope="row"><label for="schRegYn"><s:message code="EXCT.YN"/></label></th><!--실행여부-->

	                           <td>
	                           		<select id="schRegYn"  name="schRegYn">
	                           			<option value=""><s:message code="WHL" /></option><!--전체-->

	                           			<option value="Y">Y</option>
	                           			<option value="N">N</option>
	                           		</select>
	                           </td>
                       </tr>
                       <tr>                               
                           <th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

                           <td>
                               <input type="text" name="dbcTblNm" id="dbcTblNm" />
                           </td>
                           <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM"/></label></th><!--컬럼명(관계/중복분석명)-->

                           <td>
                               <input type="text" name="dbcColNm" id="dbcColNm" />
                           </td>
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
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:5px;"><span></span></div>
</div>

<!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonProfile.jsp" />
	
	<!-- 추가됨 -->
	<form name="poiForm">
		<input type="hidden" id="sheetNum" name="sheetNum" value="">
		<input type="hidden" id ="dbCodeValue" name = "dbCodeValue" value="">
		<input type="hidden" id ="schCodeValue" name = "schCodeValue" value="">
	</form>
	
	<div style="clear:both; height:5px;"><span></span></div>       
	
	<div id="tabs">
	  <ul>
	    <li id="tab-PT01"><a href="#tabs-pt01"><s:message code="RLT"/></a></li><!--관계분석-->
	    <li id="tab-PT02"><a href="#tabs-pt02"><s:message code="DUP"/></a></li><!--중복분석-->
	    <li id="tab-PC01"><a href="#tabs-pc01"><s:message code="ANA.CLMN"/></a></li><!--컬럼분석-->
	    <li id="tab-PC02"><a href="#tabs-pc02"><s:message code="ANA.CD"/></a></li><!--코드분석-->
	    <li id="tab-PC03"><a href="#tabs-pc03"><s:message code="DATE.FRMT"/></a></li><!--날짜형식분석-->
	    <li id="tab-PC04"><a href="#tabs-pc04"><s:message code="ANA.RNG"/></a></li><!--범위분석-->
	    <li id="tab-PC05"><a href="#tabs-pc05"><s:message code="STRING.PTRN"/></a></li><!--문자열패턴분석-->
	  </ul>
	  <div id="tabs-pt01">
			<div id="detailInfoPT01"><%@include file="list/relAna_lst.jsp" %></div>
	  </div>
	  <div id="tabs-pt02">
			<div id="detailInfoPT02"><%@include file="list/unqAna_lst.jsp" %></div>
	  </div>
	  <div id="tabs-pc01">
			<div id="detailInfoPC01"><%@include file="list/colAna_lst.jsp" %></div>
	  </div>
	  <div id="tabs-pc02">
			<div id="detailInfoPC02"><%@include file="list/codeAna_lst.jsp" %></div>
	  </div>
	  <div id="tabs-pc03">
			<div id="detailInfoPC03"><%@include file="list/dateAna_lst.jsp" %></div>
	  </div>
	  <div id="tabs-pc04">
			<div id="detailInfoPC04"><%@include file="list/rangeAna_lst.jsp" %></div>
	  </div>
	  <div id="tabs-pc05">
			<div id="detailInfoPC05"><%@include file="list/patternAna_lst.jsp" %></div>
	  </div>
	 </div>


	<!-- 업무규칙전환 조회 그리드 -->
	<div id="grid_br" class="grid_01" style="display:none;">
	     <script type="text/javascript">createIBSheet("grid_br", "100%", "100px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
	
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>