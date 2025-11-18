<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="CD.TBL.INQ.1"/></title><!--코드테이블 조회-->


<script type="text/javascript">
	$(document).ready(	function() {
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		//그리드 초기화 
		initGrid();
		
		//그리드 사이즈 조절 초기화...		
		bindibsresize();
		
		// 조회 Event Bind
		$("#btnSearch").click(function(event) {
			//event.preventDefault();	//브라우저 기본 이벤트 제거...
			doAction("SearchAnaTrgCol");
		});
		
		// 추가 Event Bind
		$("#btnNew").click(function(event) {
			//event.preventDefault();	//브라우저 기본 이벤트 제거...
			doAction("InsertRelCol");
		});
		
		//선택버턴
		$("#btnReturn").click(function(event) {
			//event.preventDefault();	//브라우저 기본 이벤트 제거...
			doAction("Return");
		}).show();
		
		// 부모테이블 조회 Event Bind
		$("#btnTblSearch").click(function(event){
	    	event.preventDefault();	//브라우저 기본 이벤트 제거...
	    	SearchTbl();
		}).parent().buttonset();

		//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
	    $("div.pop_tit_close").click(function(){
	    	//iframe 형태의 팝업일 경우
	    	if ("${anaTrgTblVO.popType}" == "I") {
	    		parent.closeLayerPop();
	    	} else {
	    		window.close();
	    	}
	    });
		 
		//부모테이블 진단대상 정보 셋팅
		$("form[name=frmSearch] #schPaDbConnTrgLdm").val(  "${wamRelTblVO.paTblDbConnTrgNm }" == "" ? "${anaTrgTblVO.dbConnTrgPnm }" : "${wamRelTblVO.paTblDbConnTrgNm }");
		$("form[name=frmSearch] #schPaDbConnTrgId").val(  "${wamRelTblVO.paTblDbConnTrgId }" == "" ? "${anaTrgTblVO.dbConnTrgId }" : "${wamRelTblVO.paTblDbConnTrgId }");
		$("form[name=frmSearch] #schPaDbSchNm").val(  "${wamRelTblVO.paTblDbcSchNm }" == "" ? "${anaTrgTblVO.dbSchPnm }" : "${wamRelTblVO.paTblDbcSchNm }");
		$("form[name=frmSearch] #schPaDbSchId").val(  "${wamRelTblVO.paTblDbcSchId }" == "" ? "${anaTrgTblVO.dbSchId }" : "${wamRelTblVO.paTblDbcSchId }");
				
// 		$("form[name=frmSearch] input[type=text]").css("border-color","transparent");
		//자식테이블 조회조건 readonly
		$("form[name=frmSearch] #schDbConnTrgLdm").css("border-color","transparent").attr("readonly",true);
		$("form[name=frmSearch] #schDbSchNm").css("border-color","transparent").attr("readonly",true);
		$("form[name=frmSearch] #schDbcTblNm").css("border-color","transparent").attr("readonly",true);
		$("form[name=frmSearch] #schDbcTblKorNm").css("border-color","transparent").attr("readonly",true);
		
		//부모테이블 조회조건  readonly
		$("form[name=frmSearch] #schPaDbConnTrgLdm").css("border-color","transparent").attr("readonly",true);
		$("form[name=frmSearch] #schPaDbSchNm").attr("readonly",true);
		$("form[name=frmSearch] #schPaDbcTblNm").attr("readonly",true);
		$("form[name=frmSearch] #schPaDbcTblKorNm").attr("readonly",true);
	});

	//엔터키 처리한다.
	EnterkeyProcess("SearchAnaTrgCol");
	
	$(window).on('load',function() {
		var prfId = $('form[name=frmSearch] #prfId').val() ;
		//prfid 존재 시 등록된 관계컬럼 조회
		if(!prfId == ""){
			//진단대상 컬럼 조회
			doAction("SearchAnaTrgCol"); 	
		}
	});

	$(window).resize(function() {
		//그리드 가로 스크롤 방지
		//자식테이블
		grid_catbl.FitColWidth();
		//부모테이블
		grid_patbl.FitColWidth();
		//관계컬럼
		grid_rel_col_sheet.FitColWidth();
	});

	function initGrid() {

		with (grid_catbl) {
			var cfg = {SearchMode : 2,Page : 100};
			SetConfig(cfg);

			var headers = [ {Text : "<s:message code='DQ.HEADER.ANATRGRELTBLCOL_POP'/>", Align : "Center"	} ];
			//선택|No|상태|Position|진단대상ID|진단대상명|스키마ID|스키마명|자식테이블명|자식테이블한글명|자식컬럼명|자식컬럼한글명|Pk여부|Data Type|Null여부|Default

			 var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	           
	           InitHeaders(headers, headerInfo); 

	           var cols = [
	                   {Type:"Radio", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
	   				   {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0, Hidden:1},
	   	               {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:70,  SaveName:"ord",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   
	                   {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:120,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
	                   {Type:"Text",   Width:120,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0},
	                   {Type:"Text",   Width:70,  SaveName:"pkYn",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"dataType",    	Align:"Left", Edit:0},
	                   {Type:"Text",   Width:80,  SaveName:"nullYn",    	Align:"Left", Edit:0, Hidden:0},
	                   {Type:"Text",   Width:80,  SaveName:"defltVal",    	Align:"Left", Edit:0, Hidden:0}
	               ];

			//각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			InitColumns(cols);

			//콤보 목록 설정...
			//SetColProperty("regTypCd", 	${codeMap.regTypCdibs});

			InitComboNoMatchText(1, "");

			FitColWidth();
			
			//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다    
			//SetExtendLastCol(1);
		}

		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_catbl);
		//===========================

		//진단대상 컬럼 grid
		with (grid_patbl) {

			var cfg = {SearchMode : 2,Page : 100	};
			SetConfig(cfg);

			var headers = [ {Text : "<s:message code='DQ.HEADER.ANATRGRELTBLCOL_POP2'/>", Align : "Center"	} ];
			//선택|No|상태|Position|진단대상ID|진단대상명|스키마ID|스키마명|부모테이블명|부모테이블한글명|부모컬럼명|부모컬럼한글명|Pk여부|Data Type|Null여부|Default

			 var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	           
	           InitHeaders(headers, headerInfo); 

	           var cols = [
	                   {Type:"Radio", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
	   				   {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0, Hidden:1},
	   	               {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:70,  SaveName:"ord",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   
	                   {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:120,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
	                   {Type:"Text",   Width:120,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0},
	                   {Type:"Text",   Width:70,  SaveName:"pkYn",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"dataType",    	Align:"Left", Edit:0},
	                   {Type:"Text",   Width:80,  SaveName:"nullYn",    	Align:"Left", Edit:0, Hidden:0},
	                   {Type:"Text",   Width:80,  SaveName:"defltVal",    	Align:"Left", Edit:0, Hidden:0}
	               ];

			//각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			InitColumns(cols);

			InitComboNoMatchText(1, "");

			FitColWidth();
			
			//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			//SetExtendLastCol(1); 
		}
		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_patbl);
		//===========================
			
		with(grid_rel_col_sheet){
			var cfg = {SearchMode:2,Page:100};
			SetConfig(cfg);
			
			var headers = [
						{Text:"<s:message code='DQ.HEADER.ANATRGRELTBLCOL_POP3'/>", Align:"Center"}
					];
			//삭제|No.|상태|요청구분|등록유형|검증결과|검토상태코드|+"자식테이블DB접속대상ID|자식테이블DBC스키마ID|자식테이블DBC테이블명|자식컬럼명|자식컬럼한글명|

			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
		                {Type:"CheckBox", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
						{Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
		                {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",  Width:100,  SaveName:"rqstDcd",	 Align:"Center", Edit:0, KeyField:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"rvwStsCd",		Align:"Center", Edit:0, Hidden:1},	
						
						{Type:"Text",   Width:200,  SaveName:"chTblDbConnTrgId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"chTblDbcSchId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"chTblDbcTblNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"chTblDbcColNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"chTblDbcColKorNm",   	Align:"Left", Edit:0, KeyField:0},
						
						{Type:"Text",   Width:200,  SaveName:"paTblDbConnTrgId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"paTblDbcSchId",   	Align:"Left", Edit:0, KeyField:0 ,Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"paTblDbcTblNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"paTblDbcColNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"paTblDbcColKorNm",   	Align:"Left", Edit:0, KeyField:0},

						{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:0,  Hidden:1},
						{Type:"Date",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
					];
			
			InitColumns(cols);
			
// 			SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
//	 		SetColProperty("regTypCd", ${codeMap.regTypCd});
//	 		SetColProperty("vrfCd", ${codeMap.vrfCd});
//	 		SetColProperty("rvwStsCd", ${codeMap.rvwStsCd});
			
			InitComboNoMatchText(1, "");
		  
			FitColWidth();  
			
			SetExtendLastCol(1);	
		}
		
		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_rel_col_sheet);
		//===========================
	}

	function doAction(sAction, param) {

		switch (sAction) {
		
			/*진단대상 테이블 조회*/
			case "SearchAnaTrgCol":
				
				if($('form[name=frmSearch] #schPaDbcTblNm').val() == ""){
					// 부모테이블명을 입력하십시오.
	    			showMsgBox("INF", "<s:message code='PRNT.TBL.NM.INPT'/>" ); 
					return;
				}
				
				//자식, 부모스키마, 테이블 동일할 경우
				if($('form[name=frmSearch] #schDbSchNm').val() == $('form[name=frmSearch] #schPaDbSchNm').val()){
					if($('form[name=frmSearch] #schDbcTblNm').val() == $('form[name=frmSearch] #schPaDbcTblNm').val() ){
						 
						/*자식테이블명과 부모테이블명이 동일합니다.*/
		    			showMsgBox("INF", "<s:message code='CHILD.TBL.NM.PRNT.TBL.NM.SAME'/>"); 
						return;
					}
				}
				
				param = "";
				param = $('form[name=frmSearch]').serialize();
				grid_catbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getAnaTrgColLst.do" />", param);
				break;
				
			//관계컬럼 조회
			case "SearchRelColLst": 
				var param = "&prfId="+$('form[name=frmSearch] #prfId').val() ; 
				grid_rel_col_sheet.DoSearch("<c:url value="/dq/profile/getPrfPT01RelColLst.do" />", param);
				break;
				
			case "InsertRelCol":
				var cRow = grid_catbl.FindCheckedRow("ibsCheck");
				if(cRow == 0){
					var message = "<s:message code='ERR.EMPTY'  arguments="<s:message code='CHILD.CLMN.NM.CHC'/>" />";/*자식컬럼명을 선택하십시오.*/

	    			showMsgBox("INF", message); 
	    			return;
				}
				//자식테이블 정보 셋팅
				$("form[name=frmInput] #chTblDbConnTrgId").val(grid_catbl.GetCellValue(cRow, "dbConnTrgId")) ;
				$("form[name=frmInput] #chTblDbcSchId").val(grid_catbl.GetCellValue(cRow, "dbSchId")) ;
				$("form[name=frmInput] #chTblDbcTblNm").val(grid_catbl.GetCellValue(cRow, "dbcTblNm")) ;
				$("form[name=frmInput] #chTblDbcColNm").val(grid_catbl.GetCellValue(cRow, "dbcColNm")) ;
				$("form[name=frmInput] #chTblDbcColKorNm").val(grid_catbl.GetCellValue(cRow, "dbcColKorNm")) ;
				//부모테이블 정모 셋팅
				var pRow = grid_patbl.FindCheckedRow("ibsCheck");
				if(pRow == 0){
					var message = "<s:message code='ERR.EMPTY'  arguments="<s:message code='PRNT.CLMN.NM.CHC'/>" />"; /*부모컬럼명을 선택하십시오.*/
	    			showMsgBox("INF", message); 
	    			return;
				}
				$("form[name=frmInput] #paTblDbConnTrgId").val(grid_patbl.GetCellValue(pRow, "dbConnTrgId")) ;
				$("form[name=frmInput] #paTblDbcSchId").val(grid_patbl.GetCellValue(pRow, "dbSchId")) ;
				$("form[name=frmInput] #paTblDbcTblNm").val(grid_patbl.GetCellValue(pRow, "dbcTblNm")) ;
				$("form[name=frmInput] #paTblDbcColNm").val(grid_patbl.GetCellValue(pRow, "dbcColNm")) ;
				$("form[name=frmInput] #paTblDbcColKorNm").val(grid_patbl.GetCellValue(pRow, "dbcColKorNm")) ;
				
				var iRow = grid_rel_col_sheet.DataInsert(-1);
				form2ibsmapping(iRow, $("form[name=frmInput]"), grid_rel_col_sheet);
				
				//선택된 row 삭제
// 				grid_catbl.RowDelete(cRow, 0);
// 				grid_patbl.RowDelete(pRow, 0);
				
				//선택된 row  radio 버튼 edit false
				grid_catbl.SetCellEditable(cRow, "ibsCheck", 0);
				grid_catbl.SetCellValue(cRow, "ibsCheck", "0");
				
				grid_patbl.SetCellEditable(pRow, "ibsCheck", 0); 
				grid_patbl.SetCellValue(pRow, "ibsCheck", "0");

				break;
				
			//parent 화면 셋팅
			case "Return":
				if(grid_rel_col_sheet.GetTotalRows() == 0){
					var message = "<s:message code="RLT.CLMN.CHC" />"; /*관계컬럼을 선택하십시오.*/ 
	    			showMsgBox("INF", message); 
					return;
				}
				
				//동일 관계컬럼 확인
				var DupRow = grid_rel_col_sheet.ColValueDupRows("chTblDbConnTrgId|chTblDbcSchId|chTblDbcTblNm|chTblDbcColNm|paTblDbConnTrgId|paTblDbcSchId|paTblDbcTblNm|paTblDbcColNm");
				if(DupRow != ""){
					var message = "<s:message code="SAME.RLT.CLMN.EXIS" />"; /*동일한 관계컬럼이 존재 합니다.*/
	    			showMsgBox("INF", message);  
					return;
				}
				
				var retjson = $("#frmSearch").serializeObject();
				//iframe 형태의 팝업일 경우
				if ("${anaTrgTblVO.popType}" == "I") {
					parent.returnRelColPop(JSON.stringify(retjson));
				} else {
					opener.returnRelColPop(JSON.stringify(retjson));
				}
				
				//관계컬럼 반영
				ibsSheet2SheetStatus(grid_rel_col_sheet ,   parent.grid_rel_col_sheet, "ibsStatus"); 
				
				$('div.pop_tit_close').click();
				break;

		}
	}
	
	
	//부모테이블 조회
	function SearchTbl(){
		var param = "";
		param += "&dbConnTrgPnm="+$('form[name=frmSearch] #schPaDbConnTrgLdm').val();
		param += "&dbSchPnm="+$('form[name=frmSearch] #schPaDbSchNm').val();
		param += "&dbcTblNm="+$('form[name=frmSearch] #schPaDbcTblNm').val();
		param += "&dbcTblKorNm="+$('form[name=frmSearch] #schPaDbcTblKorNm').val();
		
		var url = '<c:url value="/dq/criinfo/anatrg/popup/anatrgtbl_pop.do"/>';
		openLayerPop(url ,800 ,600,  param);
	}
	
	
	function returnAnatblPop (ret) {
		var retjson = jQuery.parseJSON(ret);
		
		//부모테이블 설정
		$("form[name=frmSearch] #schPaDbConnTrgId").val(retjson.dbConnTrgId);
		$("form[name=frmSearch] #schPaDbSchId").val(retjson.dbSchId);
		$("form[name=frmSearch] #schPaDbConnTrgLdm").val(retjson.dbConnTrgPnm);
		$("form[name=frmSearch] #schPaDbSchNm").val(retjson.dbSchPnm);
		$("form[name=frmSearch] #schPaDbcTblNm").val(retjson.dbcTblNm);
		$("form[name=frmSearch] #schPaDbcTblKorNm").val(retjson.dbcTblKorNm);
		
		//조회
		doAction("SearchAnaTrgCol");
	}
	

	function grid_catbl_OnDblClick(row, col, value, cellx, celly) {
		if (row < 1)	return;
	}

	function grid_catbl_OnClick(row, col, value, cellx, celly) {
		if (row < 1)	return;
	}


	function grid_patbl_OnClick(row, col, value, cellx, celly) {
		if (row < 1) return;
	}
	
	/*체크박스 변경시 발생 이벤트*/
	function grid_rel_col_sheet_OnBeforeCheck(Row, Col) {
		if(grid_rel_col_sheet.GetCellValue(Row,"ibsStatus") == "I") {
			//선택된 row  radio 버튼 edit true
			var cfRow = grid_catbl.FindText("dbcColNm", grid_rel_col_sheet.GetCellValue(Row,"chTblDbcColNm"));
			var pfRow = grid_patbl.FindText("dbcColNm", grid_rel_col_sheet.GetCellValue(Row,"paTblDbcColNm"));
			grid_catbl.SetCellEditable(cfRow, "ibsCheck", 1);
			grid_patbl.SetCellEditable(pfRow, "ibsCheck", 1);
			//선택된 row 삭제
			grid_rel_col_sheet.RowDelete(Row, 0);
		}
	 }
	
	//조회 에러
	function grid_catbl_OnSearchEnd(code, message, stCode, stMsg) {
		if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
		}
		if (code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}else{
			//부모테이블 컬럼 조회
			var param = "&schDbConnTrgLdm="+$('form[name=frmSearch] #schPaDbConnTrgLdm').val();
			     param +=  "&schDbSchNm="+$('form[name=frmSearch] #schPaDbSchNm').val();
			     param +=  "&schDbcTblNm="+$('form[name=frmSearch] #schPaDbcTblNm').val();
			     param +=  "&schDbcTblKorNm="+$('form[name=frmSearch] #schPaDbcTblKorNm').val();
			grid_patbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getAnaTrgColLst.do" />", param);
		}
	}
	
	function grid_patbl_OnSearchEnd(code, message, stCode, stMsg) {
		if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
		}
		if (code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}else{
			/*
			var prfId = $('form[name=frmSearch] #prfId').val() ;
			//prfid 존재 시 등록된 관계컬럼 조회
			if(!prfId == ""){
				//관계컬럼 조회
				doAction("SearchRelColLst"); 	
			}
			*/
		}
	}
	
	function grid_rel_col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
		if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
		}
		if (code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}else{
			/*
			//등록된 관계컬럼 재선택 못하게 radio click disable
			var cRow = "";
			var pRow = "";
			
			for(var i=1; i<=grid_rel_col_sheet.GetTotalRows(); i++){
				//자식컬럼
				cRow = grid_catbl.FindText("dbcColNm", grid_rel_col_sheet.GetCellValue(i, "chTblDbcColNm"));
				if(cRow > 0){
					grid_catbl.SetCellEditable(cRow, "ibsCheck", 0);
					grid_catbl.SetCellValue(cRow, "ibsCheck", "0");
				}
				//부모컬럼
				pRow = grid_patbl.FindText("dbcColNm", grid_rel_col_sheet.GetCellValue(i, "paTblDbcColNm"));
				alert(pRow);
				if(pRow > 0){
					grid_patbl.SetCellEditable(pRow, "ibsCheck", 0); 
					grid_patbl.SetCellValue(pRow, "ibsCheck", "0");
				}
			}
			*/
		}
	}
	
</script>
</head>

<body>
	<div class="pop_tit">
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
		<div class="pop_tit_txt"><s:message code="RLT.CLMN.INQ"/></div><!--관계컬럼 조회-->
		<div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
		<!-- 팝업 타이틀 끝 -->

		<!-- 팝업 내용 시작 -->
		<div class="pop_content">
			<!-- 검색조건 입력폼 -->
			<div id="search_div">
				<fieldset>
				<legend><s:message code="FOREWORD" /></legend><!--머리말-->
				
				<form id="frmInput" name="frmInput" method="post">
				<input type="hidden" name="chTblDbConnTrgId" id="chTblDbConnTrgId" />
				<input type="hidden" name="chTblDbcSchId" id="chTblDbcSchId" />
				<input type="hidden" name="chTblDbcTblNm" id="chTblDbcTblNm" />
				<input type="hidden" name="chTblDbcColNm" id="chTblDbcColNm" />
				<input type="hidden" name="chTblDbcColKorNm" id="chTblDbcColKorNm" />
				<input type="hidden" name="paTblDbConnTrgId" id="paTblDbConnTrgId" />
				<input type="hidden" name="paTblDbcSchId" id="paTblDbcSchId" />
				<input type="hidden" name="paTblDbcTblNm" id="paTblDbcTblNm" />
				<input type="hidden" name="paTblDbcColNm" id="paTblDbcColNm" />
				<input type="hidden" name="paTblDbcColKorNm" id="paTblDbcColKorNm" />
				</form>
			
				<form id="frmSearch" name="frmSearch" method="post">
				<input type="hidden" name="prfId" id="prfId" value="${wamRelTblVO.prfId}" />  
				<input type="hidden" name="schDbConnTrgId" id="schDbConnTrgId"	value="${anaTrgTblVO.dbConnTrgId }" />
				<input type="hidden" name="schDbSchId" id="schDbSchId"	value="${anaTrgTblVO.dbSchId }" />
				<!-- 부모테이블 진단대상ID -->
				<input type="hidden" name="schPaDbConnTrgId" id="schPaDbConnTrgId"	value="" />
				<input type="hidden" name="schPaDbSchId" id="schPaDbSchId"	value="" />
			
			
			
				<table width="100%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='RLT.CLMN.INQ'/>"> <!--관계컬럼 조회-->
					<caption></caption>
					<colgroup>
						<col style="width: 49%;" />
						<col style="width: 1%;" />
						<col style="width: 49%;" />
					</colgroup>
					<tbody>
						<tr>
							<td>
								<!-- 자식테이블정보 -->
								<div class="stit"><s:message code="CHILD.TBL.INQ.COND"/></div><!--자식테이블 검색조건-->

								<div style="clear: both; height: 5px;"><span></span></div>
								<div class="tb_basic2">
								<table width="99%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='CHILD.TBL.INQ'/>"><!-- //자식테이블 조회 -->

									<caption></caption>
									<colgroup>
										<col style="width: 30%;" />
										<col style="width: 70%;" />
									</colgroup>
	
									<tbody>
										<tr>
											<th scope="row" class="th_require"><label for="schDbConnTrgLdm"><s:message code="CHILD.TBL.DIAG.TRGT.NM"/></label></th><!--자식테이블진단대상명-->

											<td>
												<input type="text" class="wd95p" name="schDbConnTrgLdm"	id="schDbConnTrgLdm" value="${anaTrgTblVO.dbConnTrgPnm }" />
											</td>
										</tr>
										<tr>
											<th scope="row" class="th_require"><label for="schDbSchNm"><s:message code="CHILD.TBL.SCHEMA.NM"/></label></th><!--자식테이블스키마명-->

											<td><input type="text" class="wd95p" name="schDbSchNm" id="schDbSchNm"	value="${anaTrgTblVO.dbSchPnm }"  /></td>
										</tr>
										<tr>
											<th scope="row" class="th_require"><label for="schDbcTblNm"><s:message code="CHILD.TBL.NM"/></label></th><!--자식테이블명-->

											<td><input type="text" class="wd95p" name="schDbcTblNm" 	id="schDbcTblNm" value="${anaTrgTblVO.dbcTblNm }" /></td>
										</tr>
										<tr>
											<th scope="row"><label for="schDbcTblKorNm"><s:message code="CHILD.TBL.KRN.NM"/></label></th><!--자식테이블한글명-->

											<td><input type="text" class="wd95p" name="schDbcTblKorNm" 	id="schDbcTblKorNm" value="${anaTrgTblVO.dbcTblKorNm }"  /></td>
										</tr>
									</tbody>
								</table>
								</div>
							</td>
							
							<td>
								<div style="clear: both; width: 4px;"><span></span></div>
							</td>
							
							<td>
								<!-- 부모테이블정보 -->
								<div class="stit"><s:message code="PRNT.TBL.INQ.COND"/></div><!--부모테이블 검색조건-->
								<div style="clear: both; height: 5px;"><span></span></div>
								<div class="tb_read">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"	summary="부모테이블 조회">
									<caption></caption>
									<colgroup>
										<col style="width: 30%;" />
										<col style="width: 70%;" />
									</colgroup>
	
									<tbody>
										<tr>
											<th scope="row" class="th_require"><label for="schPaDbConnTrgLdm"><s:message code="PRNT.TBL.DIAG.TRGT.NM"/></label></th><!--부모테이블진단대상명-->
											<td><input type="text" class="wd60p" name="schPaDbConnTrgLdm"	id="schPaDbConnTrgLdm" /></td>
										</tr>
										<tr>
											<th scope="row" class="th_require"><label for="schPaDbSchNm"><s:message code="PRNT.TBL.SCHEMA.NM"/></label></th><!--부모테이블스키마명-->
											<td><input type="text" class="wd60p" name="schPaDbSchNm" id="schPaDbSchNm"	 /></td>
										</tr>
										
										<tr>
											<th scope="row" class="th_require"><label for="schPaDbcTblNm"><s:message code="PRNT.TBL.NM"/></label></th><!--부모테이블명-->
											<td>
												<input type="text" class="wd60p" name="schPaDbcTblNm" 	id="schPaDbcTblNm" value="${wamRelTblVO.paTblDbcTblNm }" />
												<button class="btnDelPop" ><s:message code="DEL" /></button><!--삭제-->

						 						<button class="btnSearchPop" id="btnTblSearch"><s:message code="INQ" /></button><!--검색-->
<!-- 												<button class="btn_search" id="btnTblSearch" name="btnTblSearch"  style="float:right" >테이블<s:message code="INQ"/>
 -->
											</td>
										</tr>
										<tr>
											<th scope="row"><label for="schPaDbcTblKorNm"><s:message code="PRNT.TBL.KRN.NM"/></label></th><!--부모테이블한글명-->
											<td><input type="text" class="wd60p" name="schPaDbcTblKorNm" 	id="schPaDbcTblKorNm" value="${wamRelTblVO.paTblDbcTblKorNm }" /></td>
										</tr>
									</tbody>
								</table>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
				</fieldset>
			</div>
			
				<!-- 조회버튼영역  -->
				<div style="clear: both; height: 10px;"><span></span></div>
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/><!--조회-->

			    <button class="btn_rqst_new" id="btnNew" name="btnNew"><s:message code="ADDT" /></button><!--추가-->
 
				<div style="clear: both; height: 5px;"><span></span></div>
			
				<div id="grid_div">
					<fieldset>
					<legend><s:message code="FOREWORD" /></legend><!--머리말-->			
					<table width="100%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='CHILD.TBL.INQ'/>"><!-- //자식테이블 조회 -->

						<caption></caption>
						<colgroup>
							<col style="width: 49%;" />
							<col style="width: 1%;" />
							<col style="width: 49%;" />
						</colgroup>
		
						<tbody>
							<tr>
								<td>
									<div id="grid_01" class="grid_01">
										<script type="text/javascript">createIBSheet("grid_catbl", "100%", "250px");</script>
									</div>
								</td>
								<td>
									<div style="clear: both; width: 4px;"><span></span></div>
								</td>
								<td>
									<div id="grid_02" class="grid_01">
										<script type="text/javascript"> createIBSheet("grid_patbl", "100%", "250px");	</script>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>
		
			<div style="clear: both; height: 5px;"><span></span></div>
			<div class="stit"><s:message code="RLT.CLMN"/></div><!--관계컬럼-->
			<div style="clear: both; height: 5px;"><span></span></div>
			
			<!-- 선택 버튼 추가 -->
			<button class="btn_save" id="btnReturn" name="btnReturn"><s:message code="RFLC"/></button><!--반영-->
			
			<div style="clear: both; height: 5px;"><span></span></div>
			<div id="grid_03" class="grid_01">
				<script type="text/javascript"> createIBSheet("grid_rel_col_sheet", "100%", "210px");	</script>
			</div>
			
			<div style="clear: both; height: 5px;"><span></span></div>
			
		</div>
	</div>
</body>
</html>