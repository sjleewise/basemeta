<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title>프로파일 일괄등록</title>

<style type="text/css">
	#tabs2 {display: block; margin-top:300px;}
</style>

<script type="text/javascript">

//그리드명
var grid_name = new Object();
//FORM 명
var selectForm = new Object();
var connTrgSchJson = ${codeMap.connTrgSch} ;

$(document).ready(function() {
	
	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val("${waqMstr.bizDtlCd}");
	
	setFormByPrfKndCd($("#bizDtlCd").val());
    $("#prfKndCd").val($("#bizDtlCd").val());
	    
	$("#tabs").tabs();
	
	$("#tabs2").tabs();
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.SHORTREQUIRED" />";
	//폼검증
	$("#frmAnaTrg").validate({
		rules: {
			dbConnTrgPnm		: "required",
			dbSchPnm		: "required",
			prfKndCd	: "required",
			objNm : {
				required : false ,
				required: function(){
					if($("#prfKndCd").val().indexOf("PC") > -1){
						if($("#objNm").val() == ""){	return true;  }
					}
		            return false;
				}
			}
		},
		messages: {
			dbConnTrgPnm		: requiremessage,
			dbSchPnm		: requiremessage,
			prfKndCd 	: requiremessage,
			objNm	:  requiremessage
		}
	});
	
	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
    	//iframe 형태의 팝업일 경우
    	if ("${waqMstr.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    });
	
	//프로파일 유형에 따른 입력폼 변경
	$("form[name=frmAnaTrg] #prfKndCd").change(function(){
		setFormByPrfKndCd($("form[name=frmAnaTrg] #prfKndCd").val());
	});
	
	//프로파일 유형에 따른 입력폼 변경
	$("form[name=frmAnaTrgDmn] #prfKndCd").change(function(){
		setFormByPrfKndCd($("form[name=frmAnaTrgDmn] #prfKndCd option:selected").val());
	});
	
	$("#btnRqstNew").hide();
	
	// 폼저장이벤 Event Bind
	$("#btnGridSave").click( function(){ 
		if(!$("#frmAnaTrg").valid()) {
			return false;
		}
		grid_name.SetColProperty("dbcTblNm", 	{KeyField:0});
		doAction("Add"); 
		
	} ).find(".ui-button-text").text("등록대상추가");

	// 폼초기화 버튼 Event Bind
	$("#btnReset").click( function(){ 
		Reset();
	} );
	
	//조회
    $("#btnSearch").click( function(){
    	doAction("Search"); 
    } ).show();	
	
	
	// 저장 Event Bind
    $("#btnSave").click(function(){
    	
    	var rows = grid_name.IsDataModified();
    	if(!rows) {
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	if($("#mstFrm #rqstStepCd").val() == "N")  {
    		doAction("SearchSave");	
    	} else {
    		doAction("Save");
    	}
    	
    }).show();
	
    //삭제
    $("#btnDelete").click( function(){
    	doAction("Delete"); 
    } ).show();
	
    // 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		//등록가능한지 확인한다.vrfCd = 1
		var regchk = grid_name.FindText("vrfCd", "등록가능");
		
		if(regchk > 0) {
			if($("#mstFrm #aprLvl").val() > 0 ){
				showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Submit');
			}else{
				showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Approve');
			}
		} else {
			showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
			return false;
		}
		
	});
    
	 $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
    //화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/profile/popup/prfschrqst_pop.do" />';
    } );
	
	//======================================================
	 // 셀렉트 박스 초기화
	//======================================================
	double_select(connTrgSchJson, $("#frmAnaTrg #dbConnTrgPnm"));
 	$('select', $("#frmAnaTrg #dbConnTrgPnm").parent()).change(function(){
 		double_select(connTrgSchJson, $(this));
 	});
 	
 	double_select(connTrgSchJson, $("#frmAnaTrgDmn #dbConnTrgPnm"));
 	$('select', $("#frmAnaTrgDmn #dbConnTrgPnm").parent()).change(function(){
 		double_select(connTrgSchJson, $(this));
 	});
	
    //스키마 기준탭이 먼저 선택되어있게 한다.
 	$("#tabs #forSchema").click();
    
  	//요청서 bar css 높이 변경
	$(".ntit").height("0px");
  	
  	//모델마트연계조회
 	$("#btnModelMartSearch").click(function(){
 		if(!$("#frmAnaTrg").valid()) {
			return false;
		}
 		doAction("getModelMartRelLst");
 	});
		
});

$(window).on('load',function() {

	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
// 	if(rqststep !=  "A" ){
// 	if($("#mstFrm #aprLvl").val() > 0 ){
		setDispRqstMainButton(rqststep, grid_name);
// 	}
	
	//S, Q, A 상태일때 등록대상 dlsabled...
	if($("#mstFrm #rqstStepCd").val() != "N")  {
		doAction("Search");
// 		$('button.btn_rqst_new').button().hide();	
		$("#frmAnaTrg select").attr("disabled", "true");
		$("#frmAnaTrg input").attr("disabled", "true");
		//모델마트 연계 조회버튼
		$("[id='btnModelMartSearch']").css("display", "none");
		//항목추가, 초기화
		$("[id='divInputBtn']").css("display", "none");
	}

});

//엔터키 조회
EnterkeyProcess("getModelMartRelLst");

function setFormByPrfKndCd(prfKndCd){
	//유효값 입력 필드 리셋
	//clearPC02();
	$("[id^='tblDtl_']").css("display", "none");
	$("[id^='colDtl_']").css("display", "none");
	$("[id^='tblGrid_']").css("display", "none");
	$("[id^='colGrid_']").css("display", "none");
	$("[id^='selected_title_area']").css("display", "none");
	
	//프로파일별 상세 정보 SHOW
	$("[id$='_"+prfKndCd+"']").show();
	//요청서업무구분 SETTING
	$("#bizDtlCd").val(prfKndCd);
	
	//테이블프로파일
	if(prfKndCd == "PT01"){
		//컬럼명 필수입력 css remove
		$("#thdbcObjNm").removeClass("th_require");
		//모델마트 연계 조회버튼
		$("[id='btnModelMartSearch']").css("display", "");
		//항목추가, 초기화
		$("[id='divInputBtn']").css("display", "none");
	}else{
		//컬럼명 필수입력 css remove
		$("#thdbcObjNm").addClass("th_require");
		$("[id='btnModelMartSearch']").css("display", "none");
		$("[id='divInputBtn']").css("display", "");
	}
	
	//관계분석
	if(prfKndCd == "PT01"){
		$("[id$='tblDtl_PT01']").hide();
		$("[id$='tblGrid_PT01']").show();
		grid_name = grid_pt01;
		selectForm = $("#frmInputPT01");
		grid_rel_col_sheet.RemoveAll();
	}
	//중복분석
	else if(prfKndCd == "PT02"){
		grid_name = grid_pt02;
		selectForm = $("#frmInputPT02");
		grid_unq_col_sheet.RemoveAll();
	}
	//컬럼분석
	else if(prfKndCd == "PC01"){
		grid_name = grid_pc01;
		selectForm = $("#frmInputPC01");
	}
	//코드분석
	else if(prfKndCd == "PC02"){
		grid_name = grid_pc02;
		selectForm = $("#frmInputPC02");
		grid_efva_userdf_sheet.RemoveAll();
		//추가버튼 하위 ul 테그 버튼
		$("#addButtonMenu_PC02").hide();
	}
	//날짜형식분석
	else if(prfKndCd == "PC03"){
		grid_name = grid_pc03;
		selectForm = $("#frmInputPC03");
	}
	//범위분석
	else if(prfKndCd == "PC04"){
		grid_name = grid_pc04;
		selectForm = $("#frmInputPC04");
	}
	//문자열패턴분석
	else if(prfKndCd == "PC05"){
		grid_name = grid_pc05;
		selectForm = $("#frmInputPC05");
		grid_ptr_userdf_sheet.RemoveAll();
		
		//추가버튼 하위 ul 테그 버튼
		$("#addButtonMenu_PC05").hide();
	}
	Reset();
	//그리드 RemoveAll
	grid_name.RemoveAll();
}

function Reset(){
	//등록대상 form reset
// 	$("form[name=frmAnaTrg] #dbConnTrgPnm").val("");
// 	$("form[name=frmAnaTrg] #dbSchPnm").val("");
	$("form[name=frmAnaTrg] #dbcTblNm").val("");
	$("form[name=frmAnaTrg] #objNm").val("");
	//프로파일 상세 form reset
// 	selectForm[0].reset();
	
	var prfKndCd = $("#prfKndCd").val();
	if(prfKndCd == "PT01"){
	}else if(prfKndCd == "PT02"){
		resetPT02();
	}else if(prfKndCd == "PC01"){
		resetPC01();
	}else if(prfKndCd == "PC02"){
		resetPC02();
	}else if(prfKndCd == "PC03"){
		resetPC03();
	}else if(prfKndCd == "PC04"){
		resetPC04();
	}else if(prfKndCd == "PC05"){
		resetPC05();
	}
}

function doAction(sAction)
{
//     alert(sAction);
    switch(sAction)
    {
    	case "Add":
    		
    		//프로파일별 필수입력항목 확인
    		var prfKndCd = $("#prfKndCd").val();
    		if(prfKndCd == "PT01"){
    		}else if(prfKndCd == "PT02"){
    			//중복컬럼 입력 확인
    			if(grid_unq_col_sheet.GetTotalRows() == ""){ 
    				var message = "<s:message code="ERR.EMPTY" arguments="중복컬럼을 입력하십시오." />";
    				showMsgBox("INF", message); 
    				return;
    			}
    			
    			//동일 중복컬럼 확인
    			var DupRow = grid_unq_col_sheet.ColValueDupRows("dbcColNm");
    			if(DupRow != ""){
    				var message = "<s:message code="ERR.EMPTY"  arguments="동일한 중복컬럼이 존재 합니다." />";
    				showMsgBox("INF", message); 
    				return;
    			}
    		}
    		//컬럼분석 필수 입력 확인
    		else if(prfKndCd == "PC01"){
    			//입력필드 체크박스 확인
    			if( $("form[name=frmInputPC01] input:checkbox:checked").length == 0 ){
    				var message = "<s:message code="MSG.SELECT" arguments="상세정보" />";
    				showMsgBox("INF", message); 
    				return;
    			}
    		}
    		//코드분석 필수입력 확인
    		else if(prfKndCd == "PC02"){
				//필수입력항목 체크
				var EfvaKndCd = $("form[name=frmInputPC02] #efvaAnaKndCd option:selected").val();
				
				if(EfvaKndCd == "META"){
					//차후 진단대상 컬럼 도메인 정보 유무에 따라 리턴
				}
				//코드테이블
				else if(EfvaKndCd == "CTBL"){
					//form 체크
				 	if(!$("form[name=frmInputPC02_cdtbl]").valid()){
				 		var message = "<s:message code="MSG.INPUT"  arguments="코드테이블 정보" />";
			    		showMsgBox("ERR", message); 
				 		return false;
				 	}
				}
				//사용자정의
				else if(EfvaKndCd == "USER"){
					var ibsSaveJsonPc02UserDf = grid_efva_userdf_sheet.GetSaveJson(0);
					//사용자정의 유효값 입력 확인
					if(ibsSaveJsonPc02UserDf.data.length == 0){ 
						var message = "<s:message code="ERR.EMPTY" arguments="사용자정의유효값을 입력하십시오." />";
						showMsgBox("ERR", message); 
						return;
					}
					
					//동일 중복컬럼 확인
					var DupRow = grid_efva_userdf_sheet.ColValueDupRows("userDfEfva");
					if(DupRow != ""){
						var message = "<s:message code="ERR.EMPTY"  arguments="동일한 사용자정의유효값이 존재 합니다." />";
						showMsgBox("INF", message); 
						return;
					}
				}
    		}
			//날짜형식분석 필수입력확인
			else if(prfKndCd == "PC03"){
				//입력필드 체크박스 확인
				if( $("form[name=frmInputPC03] dateFrmCd").val() == "" ){
					var message = "<s:message code="MSG.SELECT" arguments="날짜형식코드" />";
					showMsgBox("INF", message); 
					return;
				}
			}
    		//범위분석 필수입력 확인
			else if(prfKndCd == "PC04"){
				//입력필드 체크박스 확인
				if(!$("form[name=frmInputPC04]").valid()){
					var message = "<s:message code="MSG.SELECT" arguments="범위분석 상세정보" />";
					showMsgBox("INF", message); 
					return;
				}
			}
    		//패턴분석 필수입력 확인
			else if(prfKndCd == "PC05"){
				//동일 중복컬럼 확인
				var DupRow = grid_ptr_userdf_sheet.ColValueDupRows("userDfPtr");
				if(DupRow != ""){
					var message = "<s:message code="ERR.EMPTY"  arguments="동일한 사용자정의패턴이 존재 합니다." />";
					showMsgBox("INF", message); 
					return;
				}
			}
    		
    		
    		//중복분석, 사용자정의코드분석 , 사용자정의문자열패턴분석 일경우 사용자정의값 만큼 for 문 돌려~
    		if(prfKndCd == "PT02"){
    			var ibsSaveJson=grid_unq_col_sheet.GetSaveJson(1);
    			var row = "";
    			for(var i=1; i<= ibsSaveJson.data.length; i++){
					row = grid_name.DataInsert(-1);
					form2ibsmapping(row, $("#frmAnaTrg"), grid_name);
					form2ibsmapping(row, selectForm, grid_name);
					//중복컬럼명
					grid_name.SetCellValue(row, "dbcColNm", grid_unq_col_sheet.GetCellValue(i, "dbcColNm") );
					
					grid_name.SetCellValue(row, "ibsStatus", "I");
					grid_name.SetCellValue(row, "rqstDcd", "CU");
    			}
    		}else if(prfKndCd == "PC02"){
    			var EfvaKndCd = $("form[name=frmInputPC02] #efvaAnaKndCd option:selected").val();
    			 if(EfvaKndCd == "USER"){
					var ibsSaveJsonPc02UserDf = grid_efva_userdf_sheet.GetSaveJson(0);
					var row = "";
					for(var i=1; i<= ibsSaveJsonPc02UserDf.data.length; i++){
						row = grid_name.DataInsert(-1);
						form2ibsmapping(row, $("#frmAnaTrg"), grid_name);
						form2ibsmapping(row, selectForm, grid_name);
						//사용자정의유효값
						grid_name.SetCellValue(row, "userDfEfva", grid_efva_userdf_sheet.GetCellValue(i, "userDfEfva") );
						grid_name.SetCellValue(row, "userDfEfvaNm", grid_efva_userdf_sheet.GetCellValue(i, "userDfEfvaNm") );
						
						grid_name.SetCellValue(row, "ibsStatus", "I");
						grid_name.SetCellValue(row, "rqstDcd", "CU");
					}
    			 }else{
					var row = grid_name.DataInsert(-1);
					form2ibsmapping(row, $("#frmAnaTrg"), grid_name);
					form2ibsmapping(row, selectForm, grid_name);
					grid_name.SetCellValue(row, "ibsStatus", "I");
					grid_name.SetCellValue(row, "rqstDcd", "CU");
    			 }
    		}else if(prfKndCd == "PC05"){
    			var ibsSaveJsonPc05UserDf =grid_ptr_userdf_sheet.GetSaveJson(0);
    			if(ibsSaveJsonPc05UserDf.data.length > 0){
					var row = "";
					for(var i=1; i<= ibsSaveJsonPc05UserDf.data.length; i++){
						row = grid_name.DataInsert(-1);
						form2ibsmapping(row, $("#frmAnaTrg"), grid_name);
						form2ibsmapping(row, selectForm, grid_name);
						//사용자정의유효값
						grid_name.SetCellValue(row, "userDfPtr", grid_ptr_userdf_sheet.GetCellValue(i, "userDfPtr") );
						grid_name.SetCellValue(row, "userDfPtrNm", grid_ptr_userdf_sheet.GetCellValue(i, "userDfPtrNm") );
						
						grid_name.SetCellValue(row, "ibsStatus", "I");
						grid_name.SetCellValue(row, "rqstDcd", "CU");
					}
    			}else{
					var row = grid_name.DataInsert(-1);
					form2ibsmapping(row, $("#frmAnaTrg"), grid_name);
					form2ibsmapping(row, selectForm, grid_name);
					grid_name.SetCellValue(row, "ibsStatus", "I");
					grid_name.SetCellValue(row, "rqstDcd", "CU");
				}
    		}else{
    			  var row = grid_name.DataInsert(-1);
    	   		  form2ibsmapping(row, $("#frmAnaTrg"), grid_name);
    	   		  form2ibsmapping(row, selectForm, grid_name);
    	   		  grid_name.SetCellValue(row, "ibsStatus", "I");
    	   		  grid_name.SetCellValue(row, "rqstDcd", "CU");
    		}
    		
    		Reset();
    		
		    //테이블정보 필수입력항목을 해제...
		    grid_name.SetCellProperty("dbcTblNm", {KeyField:0});
// 		    grid_name.SetCellProperty("rqstDcd", {KeyField:0});
		    
		    break;
	        
		case "Down2Excel":  //엑셀내려받기
			if(grid_name.GetTotalRows() == 0 ){
				grid_name.DataInsert(0);
			}
			grid_name.Down2Excel({HiddenColumn:1, Merge:1});
			break;

		case "Search" :
			//프로파일별 url 셋팅
			var url = "";
			var prfKndCd = $("#prfKndCd").val();
			if(prfKndCd == "PT01"){
				url = '<c:url value="/dq/profile/getPrfPT01ExlLst.do"/>';
			}else if(prfKndCd == "PT02"){
				url = '<c:url value="/dq/profile/getPrfPT02ExlLst.do"/>';
			}else if(prfKndCd == "PC01"){
				url = '<c:url value="/dq/profile/getPrfPC01ExlLst.do"/>';
			}else if(prfKndCd == "PC02"){
				url = '<c:url value="/dq/profile/getPrfPC02ExlLst.do"/>';
			}else if(prfKndCd == "PC03"){
				url = '<c:url value="/dq/profile/getPrfPC03ExlLst.do"/>';
			}else if(prfKndCd == "PC04"){
				url = '<c:url value="/dq/profile/getPrfPC04ExlLst.do"/>';
			}else if(prfKndCd == "PC05"){
				url = '<c:url value="/dq/profile/getPrfPC05ExlLst.do"/>';
			}
			
			var param = $("#mstFrm").serialize();
			grid_name.DoSearch(url, param);
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
			
			break;
		
		//모델마트 연계 관계정보 조회
		case "getModelMartRelLst":
	 		var url = '<c:url value="/dq/r7mart/searchModelMartRelLst.do"/>';
	 		var param = $("#frmAnaTrg").serialize();
			grid_name.DoSearch(url, param);
			break;
            
  		case "SearchSave" :
  			
			var ibsSaveJson = grid_name.GetSaveJson(1);
			
			//2. 필수입력 누락인 경우
			if (ibsSaveJson.Code == "IBS010") return;
			if(ibsSaveJson.data.length == 0) return;

			//프로파일별 url 셋팅
			var url = "";
			var prfKndCd = $("#prfKndCd").val();
			if(prfKndCd == "PT01"){
				url = '<c:url value="/dq/profile/regExlPT01Lst.do"/>';
			}else if(prfKndCd == "PT02"){
				url = '<c:url value=""/>';
			}else if(prfKndCd == "PC01"){
				url = '<c:url value="/dq/profile/getDbcColList.do"/>';
			}else if(prfKndCd == "PC02"){
				url = '<c:url value="/dq/profile/getDbcColList.do"/>';
			}else if(prfKndCd == "PC03"){
				url = '<c:url value="/dq/profile/getDbcColList.do"/>';
			}else if(prfKndCd == "PC04"){
				url = '<c:url value="/dq/profile/getDbcColList.do"/>';
			}else if(prfKndCd == "PC05"){
				url = '<c:url value="/dq/profile/getDbcColList.do"/>';
			}
			
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
        	//alert(param);
        	
			break;
			
  		case "Save" :
			var ibsSaveJson = grid_name.GetSaveJson(0);
			//2. 필수입력 누락인 경우
			if (ibsSaveJson.Code == "IBS010") return;
			if(ibsSaveJson.data.length == 0) return;

			//프로파일별 url 셋팅
			var url = "";
			var prfKndCd = $("#prfKndCd").val();
			if(prfKndCd == "PC01"){
				url = '<c:url value="/dq/profile/regExlPC01Lst.do"/>';
			}else if(prfKndCd == "PC02"){
				url = '<c:url value="/dq/profile/regExlPC02Lst.do"/>';
			}else if(prfKndCd == "PC03"){
				url = '<c:url value="/dq/profile/regExlPC03Lst.do"/>';
			}else if(prfKndCd == "PC04"){
				url = '<c:url value="/dq/profile/regExlPC04Lst.do"/>';
			}else if(prfKndCd == "PC05"){
				url = '<c:url value="/dq/profile/regExlPC05Lst.do"/>';
			}else if(prfKndCd == "PT01"){
				url = '<c:url value="/dq/profile/regExlPT01Lst.do"/>';
			}else if(prfKndCd == "PT02"){
				url = '<c:url value="/dq/profile/regExlPT02Lst.do"/>';
			}
			
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
			
  		case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
			
			break;
			
		case "Approve" : //결재처리
			
			//프로파일 검토상태코드 승인으로 변경
			//결재프로세스 태울경우 제거
			if($("form[name=mstFrm] #aprLvl").val() == 0){
				doAllApprove(grid_name, "1");
			}
		
			var saveJson = grid_name.GetSaveJson(1);
			
			//2. 필수입력 누락인 경우
			if (saveJson.Code == "IBS010") return;
			if(saveJson.data.length == 0) return;
			
			var url = "";
			var prfKndCd = $("#prfKndCd").val();
			if(prfKndCd == "PT01"){
				url = '<c:url value="/dq/profile/approvePrfPT01.do"/>';
			}else if(prfKndCd == "PT02"){
				url = '<c:url value="/dq/profile/approvePrfPT02.do"/>';
			}else if(prfKndCd == "PC01"){
				url = '<c:url value="/dq/profile/approvePrfPC01.do"/>';
			}else if(prfKndCd == "PC02"){
				url = '<c:url value="/dq/profile/approvePrfPC02.do"/>';
			}else if(prfKndCd == "PC03"){
				url = '<c:url value="/dq/profile/approvePrfPC03.do"/>';
			}else if(prfKndCd == "PC04"){
				url = '<c:url value="/dq/profile/approvePrfPC04.do"/>';
			}else if(prfKndCd == "PC05"){
				url = '<c:url value="/dq/profile/approvePrfPC05.do"/>';
			}
			
			var param = $('form[name=mstFrm]').serialize();
			
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
	   		
		case "Delete" :
			//테크박스가 입력상태인 경우 삭제...
			if(!grid_name.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			
			//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_name);
        	
        	var tmpkey = getibscheckjoin(grid_name, "rqstSno");
			
        	if(tmpkey < 0) return;
        	
			var url = '<c:url value="/dq/profile/delPrfExlLst.do"/>';
			var param = $("#mstFrm").serialize()+"&joinkey="+tmpkey;
			IBSpostJson2(url, null, param, ibscallback);

			break;
    }
}


/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
//	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
//	    		$("form#frmAnaTrg input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
// 	    			$('button.btn_rqst_new').button().hide();
// 	    			$("#frmAnaTrg select").attr("disabled", "true");
// 	    			$("#frmAnaTrg input").attr("disabled", "true");
	    		}
//	    		$("form#frmAnaTrg input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} 
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 마스터 정보 셋팅...
			
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
//	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
//	    		$("form#frmAnaTrg input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
// 	    			$('button.btn_rqst_new').button().hide();
// 	    			$("#frmAnaTrg select").attr("disabled", "true");
// 	    			$("#frmAnaTrg input").attr("disabled", "true");
	    		}
//	    		$("form#frmAnaTrg input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/dq/profile/popup/prfschrqst_pop.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
	}
}


</script>
</head>

<body>


<div class="pop_tit">
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
	<div class="pop_tit_txt">프로파일 일괄등록</div>
	<div class="pop_tit_close"><a>창닫기</a></div>
	<!-- 팝업 타이틀 끝 -->

	<!-- 팝업 내용 시작 -->
	<div class="pop_content">
	<!-- 메뉴 메인 제목 -->
		<div class="ntit">
			<div class="">
				<c:if test="${REQ_MENU != null }">
				  ${REQ_MENU.fullPath }
				</c:if>
		   </div>
		</div>

		<div id="tabs">
			<ul>
		<!-- 		<li><a href="#tabs-1">도메인기준</a></li> -->
				<li><a href="#tabs-2" id="forSchema">스키마기준</a></li>
			</ul>
			<div id="tabs-1">
				<!-- 도메인기준탭 시작 -->
				<div id="domainInfo">
		<%-- 		<%@include file="colbatch_dmn_dtl.jsp" %> --%>
				</div>
			<!-- 도메인기준탭 끝 -->
			</div>
	
			<!-- 스키마기준탭 시작 -->
			<div id="tabs-2">
				<div id="schemaInfo"><%@include file="prfschrqst_dtl.jsp" %></div>
		  	</div>
			<!-- 스키마기준탭 끝 -->
	
		</div>

	 	<div style="clear:both; height:5px;"><span></span></div>
	   
		<div id="tabs2">
		<!-- 비 순차적 목록테그 -->
			<ul>
				<li><a href="#tabs-1">프로파일 정보</a></li>
				<li id="tabs-rqstvrf"><a href="#tabs-2" id="vrfForColBatch">검증결과</a></li>
			</ul>
			<div id="tabs-1">
				<div><%@include file="prfschrqst_grid_dtl.jsp" %></div>
			</div>
			<div id="tabs-2">
				<div id="vrfInfo"><%@include file="/WEB-INF/views/commons/rqstmst/rqstvrf_lst.jsp" %></div>
			</div>
			
		</div>
		<form id="frmGridNm" name="frmGridNm">
			<input type="hidden" id="gridNm" name="gridNm"/>
		</form>
	</div>
</div>

<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>

</body>
</html>

