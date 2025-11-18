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

var connTrgSchJson = ${codeMap.connTrgSch} ;


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
			doAction("InsertMtcCol");
		});
		
		//택스트매칭 저장 
		$("#btnReturn").click(function(event) {
			//event.preventDefault();	//브라우저 기본 이벤트 제거...
			doAction("Return");
		}).show();
		
		// 타겟테이블 조회 Event Bind
		$("#btnTblSearch").click(function(event){
	    	event.preventDefault();	//브라우저 기본 이벤트 제거...
	    	SearchTbl();
		}).parent().buttonset();

		//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
	    $("div.pop_tit_close").click(function(){
	    	//iframe 형태의 팝업일 경우
	    	if ("${dataMtcTblVO.popType}" == "I") {
	    		parent.closeLayerPop();
	    	} else {
	    		window.close();
	    	}
	    });
	    
		
		//소스테이블 정보 세팅
		$("#frmSearch #srcDbConnTrgNm").val("${dataMtcTblVO.srcDbConnTrgNm }"); 
		$("#frmSearch #srcDbSchNm").val("${dataMtcTblVO.srcDbcSchNm }"); 

		
		$("#frmSearch #tgtDbConnTrgLdm").val("${dataMtcTblVO.srcDbConnTrgNm }"); 

		 
		//소스 진단대상 DB
		double_select(connTrgSchJson, $("#frmSearch #srcDbConnTrgId"));
	   	$('select', $("#frmSearch #srcDbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
	   	
	   	$("#frmSearch #srcDbcSchId").change(function() {
			tblChange();
	   	});

	   	$("#frmSearch #srcDbConnTrgId").val('${dataMtcTblVO.srcDbConnTrgId }').attr("selected", true);
	   	double_select(connTrgSchJson, $("#frmSearch #srcDbConnTrgId"));
	   	for(i=0; i<$("#srcDbcSchId option").size(); i++) {
	   		//alert($("#frmSearch #srcDbcSchId").text());
	   		if('${dataMtcTblVO.srcDbcSchNm }' == $("#frmSearch #srcDbcSchId option:eq(" + i + ")").text()) {
	   			$("#frmSearch #srcDbcSchId option:eq(" + i + ")").attr("selected", "selected");
	   		}
	   	}
	   	tblChange();
	   	for(i=0; i<$("#tgtDbcTblNm option").size(); i++) {
	   		//alert($("#frmSearch #srcDbcSchId").text());
	   		if('${dataMtcTblVO.srcDbcTblNm }' == $("#frmSearch #srcDbcTblNm option:eq(" + i + ")").text()) {
	   			$("#frmSearch #srcDbcTblNm option:eq(" + i + ")").attr("selected", "selected");
	   		}
	   		if('${dataMtcTblVO.tgtDbcTblNm }' == $("#frmSearch #tgtDbcTblNm option:eq(" + i + ")").text()) {
	   			$("#frmSearch #tgtDbcTblNm option:eq(" + i + ")").attr("selected", "selected");
	   		}
	   	}
	   	
	});
	
	function tblChange() {
		var url = "<c:url value='/advisor/prepare/textcluster/getTblList.do' />";
		
		var param = $("#frmSearch").serialize();
		console.log(param);
		
		$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: replacerXssParam(param),
			dataType: 'json',
			success: function (data) {
				$("#frmSearch #srcDbcTblNm").find("option").remove().end();
				$("#frmSearch #tgtDbcTblNm").find("option").remove().end();
				
				$("#frmSearch #srcDbcTblNm").append('<option value=""></option>');
				$("#frmSearch #tgtDbcTblNm").append('<option value=""></option>');
				
				for(i=0; i<data.length; i++) {
					$("#frmSearch #srcDbcTblNm").append('<option value="' + data[i].srcDbcTblNm + '">' + data[i].srcDbcTblNm + '</option>');
					$("#frmSearch #tgtDbcTblNm").append('<option value="' + data[i].srcDbcTblNm + '">' + data[i].srcDbcTblNm + '</option>');
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
							
			}
		});
	}

	//엔터키 처리한다.
	EnterkeyProcess("SearchAnaTrgCol");
	
	$(window).on('load',function() {
		var prfId = $('#frmSearch #prfId').val() ;
		//prfid 존재 시 등록된 관계컬럼 조회
		if(!prfId == ""){
			//진단대상 컬럼 조회
			doAction("SearchAnaTrgCol"); 	
		}
	});

	$(window).resize(function() {
		//그리드 가로 스크롤 방지
		//소스테이블
		grid_srctbl.FitColWidth();
		//타겟테이블
		grid_tgttbl.FitColWidth();
		//관계컬럼
		grid_mtc_col_sheet.FitColWidth();
	});

	function initGrid() {

		with (grid_srctbl) {
			var cfg = {SearchMode : 2,Page : 100};
			SetConfig(cfg);

			var headerText = "<s:message code='DQ.HEADER.ANATRGRELTBLCOL_POP4'/>";
			//선택|No|상태|Position|진단대상ID|진단대상명|스키마ID|스키마명|소스테이블명|소스테이블한글명|소스컬럼명|소스컬럼한글명|Pk여부|Data Type|Null여부|Default
			var headers = [ {Text : headerText, Align : "Center"	} ];
			
			var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	           
            InitHeaders(headers, headerInfo);  
           
            var cols = [
                    {Type:"Radio",  Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
   		 		    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0, Hidden:1},
   	                {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:70,   SaveName:"ord",          Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",  Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm", Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchId",    	 Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchPnm",     Align:"Left", Edit:0, Hidden:1}, 
                    
                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",     Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",  Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,  SaveName:"dbcColNm",     Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dbcColKorNm",  Align:"Left", Edit:0},
                    {Type:"Text",   Width:70,   SaveName:"pkYn",    	 Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"dataType",     Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"nullYn",    	 Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,   SaveName:"defltVal",     Align:"Left", Edit:0, Hidden:0}
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
		init_sheet(grid_srctbl);
		//===========================

		//진단대상 컬럼 grid
		with (grid_tgttbl) {

			var cfg = {SearchMode : 2,Page : 100	};
			SetConfig(cfg);

			var headerText = "<s:message code='DQ.HEADER.ANATRGRELTBLCOL_POP5'/>";
			//선택|No|상태|Position|진단대상ID|진단대상명|스키마ID|스키마명|타겟테이블명|타겟테이블한글명|타겟컬럼명|타겟컬럼한글명|Pk여부|Data Type|Null여부|Default
			var headers = [ {Text : headerText, Align : "Center"	} ];
			

			 var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	           
             InitHeaders(headers, headerInfo); 
             
             var cols = [
                     {Type:"Radio",  Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
   		 	 	     {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0, Hidden:1},
   	                 {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                     {Type:"Text",   Width:70,   SaveName:"ord",    	Align:"Center", Edit:0},
                     {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                     {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
                     {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                     {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1}, 
                     
                     {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
                     {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0, Hidden:1}, 
                     {Type:"Text",   Width:120,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
                     {Type:"Text",   Width:120,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0},
                     {Type:"Text",   Width:70,   SaveName:"pkYn",    	Align:"Center", Edit:0},
                     {Type:"Text",   Width:100,  SaveName:"dataType",    	Align:"Left", Edit:0},
                     {Type:"Text",   Width:80,   SaveName:"nullYn",    	Align:"Left", Edit:0, Hidden:0},
                     {Type:"Text",   Width:80,   SaveName:"defltVal",    	Align:"Left", Edit:0, Hidden:0}
                 ];

			//각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			InitColumns(cols);

			InitComboNoMatchText(1, "");

			FitColWidth();
			
			//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			//SetExtendLastCol(1); 
		}
		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_tgttbl);
		//===========================
			
		with(grid_mtc_col_sheet){
			var cfg = {SearchMode:2,Page:100};
			SetConfig(cfg);

			var headerText = "<s:message code='DQ.HEADER.ANATRGRELTBLCOL_POP7'/>"; 
			//삭제|No.|상태|요청구분|등록유형|검증결과|검토상태코드|소스접속대상ID|소스스키마ID|소스테이블명|
			//소스컬럼명|소스컬럼한글명|타겟접속대상ID|타겟스키마ID|타겟테이블명|타겟컬럼명|타겟컬럼한글명						
			var headers = [
						      {Text: headerText , Align:"Center"}
						  ];

			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
		                {Type:"CheckBox", Width:70,   SaveName:"ibsCheck",   Align:"Center", Edit:1, Hidden:0, Sort:0},
						{Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
		                {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",  Width:100,  SaveName:"rqstDcd",	     Align:"Center", Edit:0, KeyField:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"regTypCd",	 Align:"Center", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"vrfCd",		 Align:"Center", Edit:0, Hidden:1},						
						{Type:"Combo",  Width:100,  SaveName:"rvwStsCd",	 Align:"Center", Edit:0, Hidden:1},	
						
						{Type:"Text",   Width:200,  SaveName:"srcDbConnTrgId",   Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"srcDbcSchId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"srcDbcTblNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"srcDbcColNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"srcDbcColKorNm",   Align:"Left", Edit:0, KeyField:0},
						
						{Type:"Text",   Width:200,  SaveName:"tgtDbConnTrgId",   Align:"Left", Edit:0, KeyField:0, Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"tgtDbcSchId",   	Align:"Left", Edit:0, KeyField:0 ,Hidden:1},
						{Type:"Text",   Width:200,  SaveName:"tgtDbcTblNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"tgtDbcColNm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:200,  SaveName:"tgtDbcColKorNm",   Align:"Left", Edit:0, KeyField:0},

						{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	Edit:0,  Hidden:1},
						{Type:"Date",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"rqstNo",      Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"rqstSno",     Align:"Center", Edit:0, Hidden:1}
					];
			
			InitColumns(cols);
			
			InitComboNoMatchText(1, "");
		  
			FitColWidth();  
			
			SetExtendLastCol(1);	
		}
		
		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_mtc_col_sheet);
		//===========================
	}

	function doAction(sAction, param) {

		switch (sAction) {
		
			/*진단대상 테이블 조회*/
			case "SearchAnaTrgCol":
				
				if($("#frmSearch #srcDbcSchId").val() == ""){					
	    			showMsgBox("INF", "진단대상을 입력하세요." ); 
					return;
				}

				if($("#frmSearch #srcDbcTblNm").val() == ""){					
	    			showMsgBox("INF", "소스테이블명을 입력하세요." ); 
					return;
				}

				if($("#frmSearch #tgtDbcTblNm").val() == ""){					
	    			showMsgBox("INF", "타겟테이블명을 입력하세요." );  
					return;
				}
				
				
				var param = "";
				
				param +=  "&schDbConnTrgId=" + $("#frmSearch #srcDbConnTrgId").val();
				param +=  "&schDbSchId="     + $("#frmSearch #srcDbcSchId").val();
				param +=  "&schDbcTblNm="    + $("#frmSearch #srcDbcTblNm").val();

				//alert(param);
				
				grid_srctbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getAnaTrgColLst.do" />", param);
				
				break;
				
			//관계컬럼 조회
			case "SearchRelColLst": 
				var param = "&prfId="+$('#frmSearch #prfId').val() ; 
				grid_mtc_col_sheet.DoSearch("<c:url value="/dq/profile/getPrfPT01RelColLst.do" />", param);
				break;
				
			case "InsertMtcCol":
				var cRow = grid_srctbl.FindCheckedRow("ibsCheck"); 
				
				if(cRow == 0){
					var message = "<s:message code='ERR.EMPTY'  arguments="<s:message code='CHILD.CLMN.NM.CHC'/>" />";/*소스컬럼명을 선택하십시오.*/

					message = "소스컬럼명을 선택하십시오.";
 
	    			showMsgBox("INF", message); 
	    			return;
				}
				//소스테이블 정보 셋팅
				$("#frmInput #srcDbConnTrgId").val(grid_srctbl.GetCellValue(cRow, "dbConnTrgId")) ;
				$("#frmInput #srcDbcSchId").val(   grid_srctbl.GetCellValue(cRow, "dbSchId")) ;
				$("#frmInput #srcDbcTblNm").val(   grid_srctbl.GetCellValue(cRow, "dbcTblNm")) ;
				$("#frmInput #srcDbcColNm").val(   grid_srctbl.GetCellValue(cRow, "dbcColNm")) ;
				$("#frmInput #srcDbcColKorNm").val(grid_srctbl.GetCellValue(cRow, "dbcColKorNm")) ;
				
				//타겟테이블 정모 셋팅
				var pRow = grid_tgttbl.FindCheckedRow("ibsCheck");
				
				if(pRow == 0){
					var message = "<s:message code='ERR.EMPTY'  arguments="<s:message code='PRNT.CLMN.NM.CHC'/>" />"; /*타겟컬럼명을 선택하십시오.*/

					message = "타겟컬럼명을 선택하십시오.";
					
	    			showMsgBox("INF", message); 
	    			return;
				}
				
				$("#frmInput #tgtDbConnTrgId").val(grid_tgttbl.GetCellValue(pRow, "dbConnTrgId")) ;
				$("#frmInput #tgtDbcSchId").val(   grid_tgttbl.GetCellValue(pRow, "dbSchId")) ;
				$("#frmInput #tgtDbcTblNm").val(   grid_tgttbl.GetCellValue(pRow, "dbcTblNm")) ; 
				$("#frmInput #tgtDbcColNm").val(   grid_tgttbl.GetCellValue(pRow, "dbcColNm")) ;
				$("#frmInput #tgtDbcColKorNm").val(grid_tgttbl.GetCellValue(pRow, "dbcColKorNm")) ;

				$("#frmInput #ibsStatus").val("I");
				
				var iRow = grid_mtc_col_sheet.DataInsert(-1);
				form2ibsmapping(iRow, $("#frmInput"), grid_mtc_col_sheet);
				
				//선택된 row 삭제
// 				grid_srctbl.RowDelete(cRow, 0);
// 				grid_tgttbl.RowDelete(pRow, 0);
				
				//선택된 row  radio 버튼 edit false
				grid_srctbl.SetCellEditable(cRow, "ibsCheck", 0);
				grid_srctbl.SetCellValue(cRow, "ibsCheck", "0");
				
				grid_tgttbl.SetCellEditable(pRow, "ibsCheck", 0); 
				grid_tgttbl.SetCellValue(pRow, "ibsCheck", "0");

				break;
				
			//parent 화면 셋팅
			case "Return":
				if(grid_mtc_col_sheet.GetTotalRows() == 0){
					var message = "<s:message code="RLT.CLMN.CHC" />"; /*관계컬럼을 선택하십시오.*/ 

					message = "소스,타겟컬럼을 선택하십시오.";
	    			showMsgBox("INF", message); 
					return;
				}

				//동일 관계컬럼 확인
				var DupRow = grid_mtc_col_sheet.ColValueDupRows("srcTblDbConnTrgId|srcTblDbcSchId|chTblDbcTblNm|srcTblDbcColNm|tgtTblDbConnTrgId|tgtTblDbcSchId|tgtTblDbcTblNm|tgtTblDbcColNm");
				if(DupRow != ""){
					var message = "<s:message code="SAME.RLT.CLMN.EXIS" />"; /*동일한 관계컬럼이 존재 합니다.*/

					message = "동일한 컬럼이 존재 합니다."; 
	    			showMsgBox("INF", message);  
					return;
				}
				
				var url = "<c:url value="/advisor/prepare/textcluster/insertWadMtcInf.do"/>"; 

				var param = $("#frmInput").serialize();

				var saveJson = grid_mtc_col_sheet.GetSaveJson(0);
	        	
				IBSpostJson2(url, saveJson, param, ibscallback); 
				
				//관계컬럼 반영
				//ibsSheet2SheetStatus(grid_mtc_col_sheet, parent.grid_mtc_col_sheet, "ibsStatus"); 
				
				//$('div.pop_tit_close').click();
				break;

		}
	}
	
	
	//타겟테이블 조회
	function SearchTbl(){
		var param = "";
		param += "&dbConnTrgPnm="+$('#frmSearch #tgtDbConnTrgLdm').val();
		param += "&dbSchPnm="+$('#frmSearch #tgtDbSchNm').val();
		param += "&dbcTblNm="+$('#frmSearch #tgtDbcTblNm').val();
		param += "&dbcTblKorNm="+$('#frmSearch #tgtDbcTblKorNm').val();
		
		var url = '<c:url value="/dq/criinfo/anatrg/popup/anatrgtbl_pop.do"/>';
		openLayerPop(url ,800 ,600,  param);
	}
	
	
	function returnAnatblPop (ret) {
		var retjson = jQuery.parseJSON(ret);
		
		//타겟테이블 설정
		$("#frmSearch #tgtDbConnTrgId").val(retjson.dbConnTrgId);
		$("#frmSearch #tgtDbSchId").val(retjson.dbSchId);
		$("#frmSearch #tgtDbConnTrgLdm").val(retjson.dbConnTrgPnm);
		$("#frmSearch #tgtDbSchNm").val(retjson.dbSchPnm);
		$("#frmSearch #tgtDbcTblNm").val(retjson.dbcTblNm);
		$("#frmSearch #tgtDbcTblKorNm").val(retjson.dbcTblKorNm);
		
		//조회
		doAction("SearchAnaTrgCol");
	}
	

	function grid_srctbl_OnDblClick(row, col, value, cellx, celly) {
		if (row < 1)	return;
	}

	function grid_srctbl_OnClick(row, col, value, cellx, celly) {
		if (row < 1)	return;
	}


	function grid_tgttbl_OnClick(row, col, value, cellx, celly) {
		if (row < 1) return;
	}
	
	/*체크박스 변경시 발생 이벤트*/
	function grid_mtc_col_sheet_OnBeforeCheck(Row, Col) {
		if(grid_mtc_col_sheet.GetCellValue(Row,"ibsStatus") == "I") {
			//선택된 row  radio 버튼 edit true
			var cfRow = grid_srctbl.FindText("dbcColNm", grid_mtc_col_sheet.GetCellValue(Row,"chTblDbcColNm"));
			var pfRow = grid_tgttbl.FindText("dbcColNm", grid_mtc_col_sheet.GetCellValue(Row,"paTblDbcColNm"));
			grid_srctbl.SetCellEditable(cfRow, "ibsCheck", 1);
			grid_tgttbl.SetCellEditable(pfRow, "ibsCheck", 1);
			//선택된 row 삭제
			grid_mtc_col_sheet.RowDelete(Row, 0);
		}
	 }
	
	//조회 에러
	function grid_srctbl_OnSearchEnd(code, message, stCode, stMsg) {
		if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
		}
		
		if (code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}else{
			//타겟테이블 컬럼 조회
			var param = "";

			param += "&schDbConnTrgId=" + $('#frmSearch #srcDbConnTrgId').val();
		    param += "&schDbSchId="     + $('#frmSearch #srcDbcSchId').val();
		    param += "&schDbcTblNm="    + $('#frmSearch #tgtDbcTblNm').val(); 
		        
			grid_tgttbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getAnaTrgColLst.do" />", param);
		}
	}
	
	function grid_tgttbl_OnSearchEnd(code, message, stCode, stMsg) {
		if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
		}
		if (code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}
	}
	
	function grid_mtc_col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
		if (stCode == 401) {
			showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
			return;
		}
		if (code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}
	}
	
</script>
</head>

<body>
	<div class="pop_tit">
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
		<div class="pop_tit_txt">텍스트매칭컬럼 조회</div><!-- 텍스트매칭컬럼 조회 -->
		<div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
		<!-- 팝업 타이틀 끝 -->

		<!-- 팝업 내용 시작 -->
		<div class="pop_content">
			<!-- 검색조건 입력폼 -->
			<div id="search_div">
				<fieldset>
				<legend><s:message code="FOREWORD" /></legend><!--머리말-->
				
				<form id="frmInput" name="frmInput" method="post" >
				    <input type="hidden" name="ibsStatus"      id="ibsStatus"      />
					<input type="hidden" name="srcDbConnTrgId" id="srcDbConnTrgId" />
					<input type="hidden" name="srcDbcSchId"    id="srcDbcSchId"    />  
					<input type="hidden" name="srcDbcTblNm"    id="srcDbcTblNm"    />
					<input type="hidden" name="srcDbcColNm"    id="srcDbcColNm"    />
					<input type="hidden" name="srcDbcColKorNm" id="srcDbcColKorNm" />
					<input type="hidden" name="tgtDbConnTrgId" id="tgtDbConnTrgId" />
					<input type="hidden" name="tgtDbcSchId"    id="tgtDbcSchId"    />
					<input type="hidden" name="tgtDbcTblNm"    id="tgtDbcTblNm"    />
					<input type="hidden" name="tgtDbcColNm"    id="tgtDbcColNm"    /> 
					<input type="hidden" name="tgtDbcColKorNm" id="tgtDbcColKorNm" />
				</form>
			
				<form id="frmSearch" name="frmSearch" method="post">
				
			
				<table width="100%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='RLT.CLMN.INQ'/>"> <!--관계컬럼 조회-->
					<caption></caption>
					<colgroup>
						<col style="width:*;" />
						
					</colgroup>
					<tbody>
						<tr>
							<td>
								<!-- 소스테이블정보 -->
								<div class="stit">검색조건</div><!--소스테이블 검색조건-->

								<div style="clear: both; height: 5px;"><span></span></div>
								<div class="tb_basic2">
								<table width="99%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='CHILD.TBL.INQ'/>"><!-- //소스테이블 조회 -->

									<caption></caption>
									<colgroup>
										<col style="width: 15%;" />
										<col style="width: 35%;" />
										<col style="width: 15%;" />
										<col style="width: 35%;" /> 
									</colgroup>
	
									<tbody>
										<tr>
											<th scope="row" class="th_require"><label for="srcDbConnTrgId">진단대상</label></th><!--소스테이블진단대상--> 
											<td colspan="3">												
												<select class = "wd100" id="srcDbConnTrgId" name="srcDbConnTrgId">
									             	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
									            </select>
									            <select class = "wd100" id="srcDbcSchId" name="srcDbcSchId">
									             	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
									            </select> 
											</td> 
										</tr>										
										<tr>
											<th scope="row" class="th_require"><label for="srcDbcTblNm">소스테이블명</label></th><!--소스테이블명-->

											<td>
												<!-- <input type="text" class="wd95p" id="srcDbcTblNm" name="srcDbcTblNm" /> -->
												<select class = "wd100" id="srcDbcTblNm" name="srcDbcTblNm">
									             	<option value=""></option>
									            </select>
											</td>
																						
											<th scope="row" class="th_require"><label for="tgtDbcTblNm">타겟테이블명</label></th><!--타겟테이블명-->
											<td>
												<!-- <input type="text" class="wd60p" name="tgtDbcTblNm" id="tgtDbcTblNm" /> -->
												<select class = "wd100" id="tgtDbcTblNm" name="tgtDbcTblNm">
									             	<option value=""></option>
									            </select>
<%-- 												<button class="btnDelPop" ><s:message code="DEL" /></button><!--삭제-->  --%>
<%-- 						 						<button class="btnSearchPop" id="btnTblSearch"><s:message code="INQ" /></button><!--검색--> --%>
											</td>
										</tr> 		
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0"	summary="<s:message code='CHILD.TBL.INQ'/>"><!-- //소스테이블 조회 -->

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
										<script type="text/javascript">createIBSheet("grid_srctbl", "100%", "250px");</script>
									</div>
								</td>
								<td>
									<div style="clear: both; width: 4px;"><span></span></div>
								</td>
								<td>
									<div id="grid_02" class="grid_01">
										<script type="text/javascript"> createIBSheet("grid_tgttbl", "100%", "250px");	</script>
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
				<script type="text/javascript"> createIBSheet("grid_mtc_col_sheet", "100%", "210px");	</script> 
			</div>
			
			<div style="clear: both; height: 5px;"><span></span></div>
			
		</div>
	</div>
</body>
</html>