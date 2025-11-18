<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">

$(document).ready(function(){
	
	//프로파일 그리드 초기회
	initPrfGrid();
	//그리드 사이즈 조절 초기화...		
	//bindibsresize();
	
	  //진단대상 상세정보 READONLY SETTING
	$("#frmAnaTrg input[type=text]").css("border-color","transparent").css("width", "47%");
	  
	// 진단대상 테이블 상세
	// $("#searchAnaTrgDtl_div").attr("style","width:100%;float:right;");
	
	//조회버튼 숨기기...
	$("#btnSearch").hide();
	
	//저장버튼 
	$("#btnPrfSave").click(function(){ 
		saveProfile();
	}).show();
  	
	//분석실행
	$("#btnExec").click(function(){ 
		//스케줄 등록
		regScheduler();		
	});
	
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
	
	//삭제
	$("#btnPrfDelete").click(function(){
		//삭제할 프로파일 선택
		if($("form[name=frmAnaTrg] #prfId").val() == ""){
			var message = "<s:message code="PRF.KNDCHK1" />"; 
			showMsgBox("ERR", message); 
			return;
		}

		$("form[name=frmAnaTrg] #regTypCd").val("D");		
		saveProfile();  
	});
	
	//엑셀내리기
	$("#btnExcelDown").click(function(){ }).hide();
	
	
	//텝별 프로파일종류 코드 강제 셋팅
	$("#tab-pt01").click(function(){
 		$("#prfKndCd").val("PT01");
 		//해당 프로파일 찾기
//  		var row = grid_prf.FindText("prfKndCd", $("#prfKndCd").val());
 		var row = grid_prf.GetSelectRow();
 		//프로파일 상세조회
 		SearchPrfDtl(row);
 	});
 	$("#tab-pt02").click(function(){
 		$("#prfKndCd").val("PT02");
 		//해당 프로파일 찾기
 		var row = grid_prf.FindText("prfKndCd", $("#prfKndCd").val());
 		//프로파일 상세조회
 		SearchPrfDtl(row);
 	}); 
	$("#tab-pc01").click(function(){
 		$("#prfKndCd").val("PC01");
 		//해당 프로파일 찾기
 		var row = grid_prf.FindText("prfKndCd", $("#prfKndCd").val());
 		//프로파일 상세조회
 		SearchPrfDtl(row);
 	});
 	$("#tab-pc02").click(function(){
 		$("#prfKndCd").val("PC02");
 		//해당 프로파일 찾기
 		var row = grid_prf.FindText("prfKndCd", $("#prfKndCd").val());
 		//프로파일 상세조회
 		SearchPrfDtl(row);
 	});
 	$("#tab-pc03").click(function(){
 		$("#prfKndCd").val("PC03");
 		//해당 프로파일 찾기
 		var row = grid_prf.FindText("prfKndCd", $("#prfKndCd").val());
 		//프로파일 상세조회
 		SearchPrfDtl(row);
 	});
 	$("#tab-pc04").click(function(){
 		$("#prfKndCd").val("PC04");
 		//해당 프로파일 찾기
 		var row = grid_prf.FindText("prfKndCd", $("#prfKndCd").val());
 		//프로파일 상세조회
 		SearchPrfDtl(row);
 	});
 	$("#tab-pc05").click(function(){
 		$("#prfKndCd").val("PC05");
 		//해당 프로파일 찾기
 		var row = grid_prf.FindText("prfKndCd", $("#prfKndCd").val());
 		//프로파일 상세조회
 		SearchPrfDtl(row);
 	});
});


$(window).on('load',function(){
});


$(window).resize( function(){
// 	grid_prf.FitColWidth();
});

//그리드 셋팅
function initPrfGrid()
{
	
    //프로파일 조회 grid
	 with(grid_prf){
	    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.PRF_DTL'/>", Align:"Center"}
                ];
        //No.|프로파일종류|프로파일종류코드|객체명|품질지표명|품질지표ID|분석차수|분석일시|분석건수|추정오류건수|추정오류율(%)|분석자|PRF_ID|분석로그ID
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Combo",   Width:100,  SaveName:"prfKndCdNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"prfKndCd",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"objNm",    	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"dqiLnm",    	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"dqiId",    	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"anaDgr",    	Align:"Center", Edit:0},
                    {Type:"Date",   Width:100,  SaveName:"anaStrDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Int",   Width:100,  SaveName:"anaCnt",    	Align:"Right", Edit:0},
                    {Type:"Int",   Width:100,  SaveName:"esnErCnt",    	Align:"Right", Edit:0},
                    {Type:"Float",   Width:100,  SaveName:"esnErRate",    	Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"anaUserNm",    	Align:"Left", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"prfId",    	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"anaLogId",    	Align:"Left", Edit:0, Hidden:1}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
 	    SetColProperty("prfKndCdNm", 	${codeMap.prfKndCdibs});
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    //==시트설정 후 아래에 와야함===
   init_sheet(grid_prf);    
    //===========================	
}



function saveProfile() {
	//form 체크
	if(!$("#frmAnaTrg").valid()) return false;
	
	var vprfKndCd = $("#prfKndCd").val();
	
	//프로파일 선택
	if(vprfKndCd == ""){
		var message = "<s:message code="PRF.KNDCHK"  arguments="<s:message code='STRG' />" />"; /* 저장 */
		showMsgBox("ERR", message); 
		return;
	}
	
	//컬럼분석(PC01) 저장
	if(vprfKndCd == "PC01"){
		savePC01();
	}
	//코드분석(PC02) 
	else if(vprfKndCd == "PC02"){
		savePC02();
	}
	//날짜형식분석(PC03) 
	else if(vprfKndCd == "PC03"){
		savePC03();
	}
	//범위분석(PC04) 
	else if(vprfKndCd == "PC04"){
		savePC04();
	}
	//패턴분석(PC05) 
	else if(vprfKndCd == "PC05"){
		savePC05();
	}
	//테이블분석(PT01) 저장
	else if(vprfKndCd == "PT01"){
		savePT01();
	}
	//코드분석(PT02) 
	else if(vprfKndCd == "PT02"){
		savePT02();
	}
}

function resetPrfInfo(tblColGb){
	//프로파일 조회 초기화
	grid_prf.RemoveAll();
	
	if(tblColGb == "PC"){
		//진단대상 컬럼 리스트 초기화
		grid_col.RemoveAll();
	}

	//컬럼 상세정보 초기화
	$('form[name=frmAnaTrg]')[0].reset();
	//hidden 일경우 form reset() 초기화 안됨
	//각 hidden 요소벌 초기화
	$("form[name=frmAnaTrg] #tblColGb").val(tblColGb);
	$("form[name=frmAnaTrg] #prfId").val("");
	$("form[name=frmAnaTrg] #prfKndCd").val("");
	$("form[name=frmAnaTrg] #regTypCd").val("");
	$("form[name=frmAnaTrg] #dbConnTrgId").val("");
	$("form[name=frmAnaTrg] #dbSchId").val("");
	$("form[name=frmAnaTrg] #sheetRow").val("");
	
	//스케줄 작업 정보
	$("form[name=frmAnaTrg] #shdJobId").val("");
	$("form[name=frmAnaTrg] #etcJobNm").val("");
	
}

//진단대상 상세정보 화면 셋팅
function setAnaTgtDtl(data){
	json2formmapping($("form[name=frmAnaTrg]"), data);
	
	//프로파일 설정 리스트 조회
	getPrfLst();
}

//프로파일 설정 리스트 조회
function getPrfLst(){
	//분석일시 초기화
	$("form[name=frmAnaTrg] #schAnaStrDtm").val(""); 
	var param = "&"+$("form[name=frmAnaTrg]").serialize(); 
	
	grid_prf.DoSearch("<c:url value="/dq/profile/getPrfLst.do" />", param);
}

//프로파일별 상세 조회
function SearchPrfDtl(row){	
	if(row > 0 ){
		//그리드 선택 데이터 변수 setting
		var param = "&prfId="+grid_prf.GetCellValue(row, "prfId"); 
		     param += "&prfKndCd="+grid_prf.GetCellValue(row, "prfKndCd"); 
	 	     param += "&schAnaStrDtm="+grid_prf.GetCellValue(row, "anaStrDtm");  
	 	     
		var urls = '<c:url value="/dq/profile/getPrfDtl.do"/>';
		ajax2Json(urls, param, ajaxDtlCallback);
	}else{
		$("form[name=frmAnaTrg] #prfId").val("");
		$("form[name=frmAnaTrg] #regTypCd").val("");
		$("form[name=frmAnaTrg] #sheetRow").val("");
		
		//스케줄 작업 정보
		$("form[name=frmAnaTrg] #shdJobId").val("");
		$("form[name=frmAnaTrg] #etcJobNm").val("");
	}
}


//프로파일별 상세조회 후 처리 함수
function ajaxDtlCallback(data){
	var vPrfId = data.resultVO.prfId;
	var vPrfKndCd = data.prfKndCd;
	var vSchAnaStrDtm = data.schAnaStrDtm;
	
	 //프로파일 입력폼 reset
	 doAction("DtlReset");

	//컬럼분석
	if(vPrfKndCd == "PC01"){
		setDtlPC01(data);
	}
	//유효값 분석 상세 조회
	else if(vPrfKndCd == "PC02"){
		setDtlPC02(data);
	}
	//날짜형식분석
	if(vPrfKndCd == "PC03"){
		setDtlPC03(data);
	}
	//범위분석
	if(vPrfKndCd == "PC04"){
		setDtlPC04(data);
	}
	//패턴분석
	if(vPrfKndCd == "PC05"){
		setDtlPC05(data);
	}
	//관계분석
	if(vPrfKndCd == "PT01"){
		setDtlPT01(data);
	}
	//중복 분석
	if(vPrfKndCd == "PT02"){
		setDtlPT02(data);
	}
	
	//프로파일종류 셋팅
	$("form[name=frmAnaTrg] #prfKndCd").val(vPrfKndCd);
	//프로파일 ID 셋팅
	$("form[name=frmAnaTrg] #prfId").val(vPrfId);
	//분석일시 셋팅
	$("form[name=frmAnaTrg] #schAnaStrDtm").val(vSchAnaStrDtm);
	//REG_TYP_CD 셋팅
	if(vPrfId != ""){
		$("form[name=frmAnaTrg] #regTypCd").val("U");
	}
	
	//스케줄 작업 정보
	$("form[name=frmAnaTrg] #shdJobId").val(vPrfId);
	$("form[name=frmAnaTrg] #etcJobNm").val("["+data.resultVO.prfKndCdNm+"] " + data.resultVO.dbcTblNm+"."+data.resultVO.objNm);
	
}

function tabClick(vPrfKndCd){
	//컬럼분석
	if(vPrfKndCd == "PC01"){
		$("#tab-pc01 a").click();
	}
	//유효값 분석 
	else if(vPrfKndCd == "PC02"){
		$("#tab-pc02 a").click();
	}
	//날짜형식분석
	else if(vPrfKndCd == "PC03"){
		$("#tab-pc03 a").click();
	}
	//범위분석
	else if(vPrfKndCd == "PC04"){
		$("#tab-pc04 a").click();
	}
	//패턴분석
	else if(vPrfKndCd == "PC05"){
		$("#tab-pc05 a").click();
	}
	//관계분석
	else if(vPrfKndCd == "PT01"){
		$("#tab-pt01 a").click();
	}
	//중복 분석
	else if(vPrfKndCd == "PT02"){
		$("#tab-pt02 a").click();
	}
	
	else {
		if($("form[name=frmAnaTrg] #tblColGb").val() == "PT" ){
			$("#tab-pt01 a").click();
		}else if($("form[name=frmAnaTrg] #tblColGb").val() == "PC" ){
			$("#tab-pc01 a").click();
			
			
			if($("form[name=frmAnaTrg] #metaAsscAnly").val() == "C"){
				
				$("#tab-pc02 a").click();
			}else if($("form[name=frmAnaTrg] #metaAsscAnly").val() == "D"){
				
				$("#tab-pc03 a").click();
				
				var infotpLnm = $("form[name=frmAnaTrg] #infotpLnm").val();
				
				//alert(infotpLnm);
				
				switch(infotpLnm){
					
					case "날짜V4":
						$("#frmInputPC03 #dateFrmCd").val("04");
						break;
					case "날짜V6":
						$("#frmInputPC03 #dateFrmCd").val("03");
						break;
					case "날짜V8":
						$("#frmInputPC03 #dateFrmCd").val("02");
						break;
					case "날짜V14":
						$("#frmInputPC03 #dateFrmCd").val("01");
						break;
				}
				
				
			}else if($("form[name=frmAnaTrg] #metaAsscAnly").val() == "R"){
				
				$("#tab-pc04 a").click();
				
				var dmnMinVal = $("form[name=frmAnaTrg] #dmnMinVal").val();
				var dmnMaxVal = $("form[name=frmAnaTrg] #dmnMaxVal").val();
				
				$("#frmInputPC04 #rngEfva1").val(dmnMinVal);
				$("#frmInputPC04 #rngOpr1").val("04");
				
				if(dmnMaxVal != ""){
					
					$("#frmInputPC04 #rngCnc").val("01"); //01: AND 02:OR
					
					$("#frmInputPC04 #rngCnc").change();
					
					$("#frmInputPC04 #rngOpr2").val("03");
					
					$("#frmInputPC04 #rngEfva2").val(dmnMaxVal);
				}
				
			
			}else{
				
				$("#tab-pc01 a").click();
			}
		}
	}
}

function getDataPattern(){
 	var objId = $("form[name=frmAnaTrg] #prfId").val();
	var schAnaStrDtm = $("form[name=frmAnaTrg] #schAnaStrDtm").val().replace(/ /g, ''); 
	
	if(objId == ""){
		showMsgBox("ERR", "<s:message code="INQ.DATA.SEL" />"); /*조회할 데이터를 선택하십시오.*/

		return;
	}
	
 	var param = "?objId="+objId;
	     param += "&objDate="+schAnaStrDtm;
	     param += "&objIdCol=PRF_ID";		  
         param += "&objResTbl=WAM_PRF_RESULT";
	     param += "&objErrTbl=WAM_PRF_ERR_DATA";
	     param += "&erDataSnoCol=ESN_ER_DATA_SNO";
         param += "&objGb="+$("form[name=frmAnaTrg] #prfKndCd").val();        
         param += "&erDataSno="+0;
         
    var url = '<c:url value="/dq/report/popup/datapattern_pop.do" />';
 	var popup = OpenWindow(url+param, "DATA_PATTERN", "800", "700", "yes"); 
}

//로그조회
function getAnaLog(){
	var objId = $("form[name=frmAnaTrg] #prfId").val();
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
	var prfId = $("form[name=frmAnaTrg] #prfId").val();
	
	if(prfId == ""){
		var message = "<s:message code="INQ.PROF.CHC.1" />"; /* 조회할 프로파일을 선택하십시오. */
		showMsgBox("INF", message); 
		return;
	}
	
	var url   = "<c:url value="/dq/profile/popup/sql_pop.do"/>";
	var param = "?prfId=" + prfId ;
	     param += "&prfKndCd=" + $("form[name=frmAnaTrg] #prfKndCd").val();
	var popup = OpenWindow(url+param,"SQL","800","650","yes");
}

//프로파일 조회 오류
function grid_prf_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		var vPrfKndCd =  grid_prf.GetCellValue(1, "prfKndCd");
		tabClick(vPrfKndCd);
	}
}

//프로파일 상세 조회
function grid_prf_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	var vPrfKndCd =  grid_prf.GetCellValue(row, "prfKndCd");
	tabClick(vPrfKndCd);
}

//실시간실행 스케줄 등록
function regScheduler(){
	if($('form[name=frmAnaTrg] #prfId').val() == ""){
		showMsgBox("ERR", "<s:message code="ANLY.DATA.CHC" />"); /* 분석할 데이터를 선택하십시오. */
		return;
	}
	var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
	var param = "&shdKndCd=QP"; //프로파일
    
    ibsSaveJson = getform2IBSjson($("#frmAnaTrg"));
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



//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function postProcessIBS(res) {
	//요청번호 셋팅
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			//테이블 프로파일 리스트 조회
			getPrfLst("PT");
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			//프로파일 ID 초기화
			$("form[name=frmAnaTrg] #prfId").val("");
			$("form[name=frmAnaTrg] #regTypCd").val("");
		
			 //입력폼 form reset
			 doAction("DtlReset");
			//테이블 프로파일 리스트 조회
			getPrfLst("PT");
			
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;			
	}
}
</script>

<!-- </head> -->
<!-- <body>     -->
	<div id="searchAnaTrgDtl_div" >
		<div class="stit"><s:message code="DIAG.TRGT.DTL.INFO"/></div><!-- 진단대상 상세정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		
	 	<form id="frmAnaTrg" name="frmAnaTrg" method="post">
	 	<!-- 프로파일 ID, 프로파일 종류 셋팅 -->
			<input type="hidden" name="dbConnTrgId" id="dbConnTrgId"  /> 
			<input type="hidden" name="dbSchId" id="dbSchId"  />
			<input type="hidden" name="tblColGb" id="tblColGb" value="" />     
			<input type="hidden" name="prfKndCd" id="prfKndCd" />
			<input type="hidden" name="prfId" id="prfId" />   
			<input type="hidden" name="regTypCd" id="regTypCd" /> 
			<input type="hidden" name="schAnaStrDtm" id="schAnaStrDtm"  />
			<!--  분석실행 작업ID -->
			<input type="hidden" name="shdJobId" id="shdJobId"  />
			<input type="hidden" name="etcJobNm" id="etcJobNm"  />
			
			<input type="hidden" name="metaAsscAnly" id="metaAsscAnly"  />
			
			<input type="hidden" name="dmnMinVal" id="dmnMinVal"  />
			<input type="hidden" name="dmnMaxVal" id="dmnMaxVal"  />
			<input type="hidden" name="dmnLnm"    id="dmnLnm"  />
			<input type="hidden" name="infotpLnm" id="infotpLnm"  />
			
			<input type="hidden" name="anaDgr" id="anaDgr"  />
			<!-- 컬럼프로파일 : 컬럼명 테이블프로파일 : 관계키명, 중복분석명사용 -->
			<div id="div_objNm"></div>
			
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
		<div class="tb_basic" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
			   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
			   <colgroup>
			   <col style="width:12%;" />
			   <col style="width:38%;" />
			   <col style="width:12%;" />
			   <col style="width:38%;" />
			   </colgroup>
			       <tbody>   
			       		<tr>                               
			               <th scope="row"  class="th_require"><label for="dbConnTrgPnm"><s:message code="DB.MS" /></label></th><!-- 진단대상명 -->
			               <td>
			                   <input  type="text"  name="dbConnTrgPnm" id="dbConnTrgPnm"  class="b0wd98" readonly />
			               </td>
			               <th scope="row"  class="th_require"><label for="dbSchPnm"><s:message code="SCHEMA.NM" /></label></th><!-- 스키마명 -->
			               <td>
			                   <input type="text" name="dbSchPnm" id="dbSchPnm"  class="b0wd98" readonly />
			               </td>
			           </tr>                         
			           <tr>                               
			               <th scope="row"  class="th_require"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!-- 테이블명 -->
			               <td>
			                   <input type="text" name="dbcTblNm" id="dbcTblNm"  class="b0wd98" readonly/>
			               </td>
			               <th scope="row"><label for="dbcTblKorNm"><s:message code="TBL.KRN.NM" /></label></th><!-- 테이블한글명 -->
			               <td>
			                   <input type="text" name="dbcTblKorNm" id="dbcTblKorNm"  class="b0wd98" readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">
			           		<th scope="row"  class="th_require"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!-- 컬럼명 -->	
			           		<td>
			           			<input type="text" name="dbcColNm" id="dbcColNm"   readonly />
			           		</td>
			           		 <th scope="row"><label for="dbcColKorNm"><s:message code="CLMN.KRN.NM" /></label></th><!-- 컬럼한글명 -->
			               <td>
			                   <input type="text" name="dbcColKorNm" id="dbcColKorNm"   readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">                               
			               <th scope="row"><label for="pkYn">PK</label></th>
			               <td>
			                   <input type="text" name="pkYn" id="pkYn"   readonly/>
			               </td>
			               <th scope="row"><label for="nullYn"><s:message code="NULL.YN" /></label></th><!-- Null여부 -->
			                <td>
			                   <input type="text" name="nullYn" id="nullYn"   readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">                               
			                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th><!-- 데이터타입 -->
			               <td>
			                   <input type="text" name="dataType" id="dataType"  readonly />
			               </td>
			               <th scope="row"><label for="defltVal">DEFAULT</label></th>
			               <td>
			                   <input type="text" name="defltVal" id="defltVal"    readonly/>
			               </td>
			           </tr>
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			</form>
	</div>
	
		
	<!--  설정 프로파일 조회 -->
	<div style="clear:both; height:20px;"><span></span></div>
	<div id="grid_03" class="grid_01"  style="width:100%">
		<script type="text/javascript">createIBSheet("grid_prf", "100%", "150px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
	
	
<!-- </body> -->
<!-- </html> -->
