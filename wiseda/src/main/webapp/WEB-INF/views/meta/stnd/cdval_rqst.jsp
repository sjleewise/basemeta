<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">

$(document).ready(function() {
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	//imgConvert($('div.tab_navi a img'));

	/* $('#btnCdvalSave').button({
		icons: { primary: "ui-icon-disk" }
	}); */
	
	// 조회 Event Bind
// 	$("#btnSearch").click(function(){ doCdvalAction("Search");  });
						
	// 추가 Event Bind
	$("#btnCdvalNew").click(function(){ 
		
// 		doCdvalAction("New");
		doAction("NewCdval");
	
	});
	
	// 삭제 Event Bind
	$("#btnCdvalDelete").click(function(){ 
		
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (cdval_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'CdvalDelete');
//         	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
    	}
// 		doCdvalAction("CdvalDelete");  
	});
	
	// 유효값 일괄삭제 Event Bind
	$("#btnCdvalAllDelete").click(function(){ 
		
		//요청단계가 임시저장이 아닌경우 리턴
		if($("#mstFrm #rqstStepCd").val() != "S") {
			showMsgBox("ERR", "<s:message code="REQ.CDVALDEL.ERR" />");
			return;
			
    	} else {
			//삭제요청 확인 메세지
			showMsgBox("CNF", "<s:message code="REQ.CDVALDEL" />", "CdvalAllDelete");
//         	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
    		
    	}
// 		doCdvalAction("CdvalDelete");  
	});
	
	//저장 이벤트
	$("#btnCdvalSave").click(function(){
		//저장할 대상이 있는지 체크 후 물어본다. 
		if(!chkSheetDataModified(cdval_sheet)) {
			//처리대상이 없는 경우
			showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
			return;
		}
		
		showMsgBox("CNF", "<s:message code="CNF.SAVE" />", 'CdvalSave');
		
		//doAction("CdvalSave");
		
	}).show();
				  
	// 엑셀내리기 Event Bind
	$("#btnCdvalExcelDown").click( function(){ doCdvalAction("Down2Excel"); } );
	
	// 엑셀업로 Event Bind
	$("#btnCdvalExcelLoad").click( function(){ doCdvalAction("LoadExcel"); } );
		  
	// 엑셀업로 Event Bind
	$("#btnCdvalGridSave").click( function(){ doCdvalAction("Save"); } );

	// 엑셀업로 Event Bind
	$("#btnDmnReset").click( function(){ doCdvalAction("Reset"); } );
});

$(window).on('load',function() {
// 	alert('window.load');
	initCdvalGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	setDispRqstMainButton(rqststep, cdval_sheet);
	$("#btnRegRqst").hide();
	cdval_sheet.SetColHidden("rvwStsCd"	,1);
	cdval_sheet.SetColHidden("rvwConts"	,1);
	
	if(rqststep=="Q" || rqststep =="A"){
		cdval_sheet.SetColEditable("stndAsrt",0);
		cdval_sheet.SetColEditable("dmnLnm",0);
		cdval_sheet.SetColEditable("cdVal",0);
		cdval_sheet.SetColEditable("cdValNm",0);
		cdval_sheet.SetColEditable("uppCdVal",0);
		cdval_sheet.SetColEditable("dispOrd",0);
		cdval_sheet.SetColEditable("useYn",0);
		cdval_sheet.SetColEditable("vvNote1",0);
		cdval_sheet.SetColEditable("vvNoteNm1",0);
		cdval_sheet.SetColEditable("vvNote2",0);
		cdval_sheet.SetColEditable("vvNoteNm2",0);
		cdval_sheet.SetColEditable("vvNote3",0);
		cdval_sheet.SetColEditable("vvNoteNm3",0);
		cdval_sheet.SetColEditable("vvNote4",0);
		cdval_sheet.SetColEditable("vvNoteNm4",0);
		cdval_sheet.SetColEditable("vvNote5",0);
		cdval_sheet.SetColEditable("vvNoteNm5",0);
		cdval_sheet.SetColEditable("outlCntn1",0);
		cdval_sheet.SetColEditable("outlCntn2",0);
		cdval_sheet.SetColEditable("objDescn",0);
	}
// 	if($("#mstFrm #rqstNo").val() != "" && $("#rqstSnoCdval").val() != "") {
// 		doCdvalAction("Search");
// 	}
});


$(window).resize(
	
	function(){
				
		// cdval_sheet.SetExtendLastCol(1);	
	}
);


function initCdvalGrid()
{
	
	with(cdval_sheet){
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headtext  = "<s:message code='META.HEADER.CDVAL.RQST.1'/>";
			headtext += "|표준분류|도메인논리명|코드값|코드값명|상위코드값|표시순서|사용여부|적용시작일자|적용종료일자";
			headtext += "|<s:message code='META.HEADER.CDVAL.RQST.3'/>";
			headtext += "|<s:message code='META.HEADER.CDVAL.RQST.4'/>";
			headtext += "|<s:message code='META.HEADER.CDVAL.RQST.5'/>";

			// headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
			// headtext += "|표준분류|도메인논리명|코드값|코드값명|상위코드값|표시순서|사용여부";
			// headtext += "|기타1|기타1명|기타2|기타2명|기타3|기타3명|기타4|기타4명|기타5|기타5명";
			// headtext += "|적요1|적요2";
			// headtext += "|비고|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호";

		
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
					{Type:"CheckBox", Width:40,   SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:1, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:1, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:1, KeyField:1},						
					{Type:"Combo",  Width:60,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:80,  SaveName:"vrfCd",		Align:"Center", Edit:0},
					{Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0, KeyField:1},
					{Type:"Text",   Width:200,   SaveName:"dmnLnm",	 	Align:"Left", Edit:0, KeyField:1, Hidden:0},
					{Type:"Text",   Width:200,  SaveName:"cdVal",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:200,  SaveName:"cdValNm",   	Align:"Left", Edit:1, KeyField:1}, 
					{Type:"Text",   Width:60,  SaveName:"uppCdVal",   	Align:"Left", Edit:1, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"dispOrd",   	Align:"Left", Edit:1},
					{Type:"Combo",  Width:80,  SaveName:"useYn",		Align:"Center", Edit:1},
					{Type:"Text",   Width:100, SaveName:"aplStrDt",   	Align:"Left", Edit:1},
					{Type:"Text",   Width:100, SaveName:"aplEndDt",   	Align:"Left", Edit:1},
					{Type:"Text",   Width:60,  SaveName:"vvNote1",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNoteNm1",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNote2",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNoteNm2",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNote3",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNoteNm3",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNote4",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNoteNm4",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNote5",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"vvNoteNm5",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"outlCntn1",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"outlCntn2",   	Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:1},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
					{Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Int",   Width:50,  SaveName:"rqstDtlSno",  Align:"Center", Edit:0, Hidden:1}
				];
					
		InitColumns(cols);
		
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		SetColProperty("useYn", 	{ComboCode:"Y|N", ComboText:"Y|N"});
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		
		InitComboNoMatchText(1, "");
		
		//SetColHidden("rqstUserId",1);
	    SetSheetHeight("300");
		// FitColWidth();  
		
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(cdval_sheet);	
	//===========================
   
}



function doCdvalAction(sAction)
{
		
	switch(sAction)
	{
		case "New": //추가
		
			//선택행 다음 행에 추가...
		 	//cdval_sheet.DataInsert();
			//첫행에 추가...
		 	//cdval_sheet.DataInsert(0);
			//마지막 행에 추가..
			var nrow = cdval_sheet.DataInsert(-1);
			
			break;

		case "Reset" :
			
			//입력창을 이전 상태로 복원...
			if($("#gridRowCdval").val() == "") return;
			
			ibs2formmapping($("#gridRowCdval").val(), $("form#cdvalInput"), cdval_sheet);
			$("#dmnLnmCdval").val($("#dmnLnm").val());
			$("#uppDmnLnmCdval").val($("#uppDmnLnm").val());

			break;

		case "Delete" :
			//테크박스가 입력상태인 경우 삭제...
			if(!cdval_sheet.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			ibsSaveJson = cdval_sheet.GetSaveJson(0, "ibsCheck");
			
			var url = "<c:url value="/meta/stnd/delcdvalrqstlist.do"/>";
			var param = $("#frmInput", parent.document).serialize();
			IBSpostJson(url, param, cdvalibscallback);
			
			break;
		case "Save" :
			// 공통으로 처리...
			if($("#gridRowCdval").val() == "") return;
			form2ibsmapping($("#gridRowCdval").val(), $("form#cdvalInput"), cdval_sheet);
			ibsSaveJson = cdval_sheet.GetSaveJson(0);
			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/regcdvalrqstlist.do"/>";
			var param = $("#frmInput", parent.document).serialize();
			IBSpostJson(url, param, cdvalibscallback);
			
			break;
			
		case "Search":
			var param = "rqstNo="+$("#rqstNo", parent.document).val();
			    param += "&rqstSno="+$("#rqstSnoCdval").val();
			cdval_sheet.DoSearch("<c:url value="/meta/stnd/getcdvalrqstlist.do" />", param);
			//cdval_sheet.DoSearchScript("testCdvalJsonlist");
						
			$('#cdvalInput')[0].reset();
			$("#rqstSnoCdval").val($("#rqstSno").val());
			$("#dmnLnmCdval").val($("#dmnLnm").val());
			$("#uppDmnLnmCdval").val($("#uppDmnLnm").val());
			
			break;
	   
		case "Down2Excel":  //엑셀내려받기
		
			cdval_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"유효값 등록요청"});
			
			break;
		case "LoadExcel":  //엑셀업로
		
			cdval_sheet.LoadExcel({Mode:'HeaderMatch'});
			
			break;
	}	   
}
 
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function cdvalibscallback(res){
	var result = res.RESULT.CODE;
	if(result == 0) {
		doCdvalAction("Search");
		showMsgBox("INF", res.RESULT.MESSAGE);
	} else {
//	 	alsert("저장실패");
		showMsgBox("ERR", "<s:message code="MSG.TEST" />");
	}
}



/*
	row : 행의 index
	col : 컬럼의 index
	value : 해당 셀의 value
	x : x좌표
	y : y좌표
*/
function cdval_sheet_OnDblClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	if(cdval_sheet.GetCellEditable(row,col) == '0')
	//$(document).parent().find("#tabs-5").click();
	{
		$("#tabs #cdvalinfo").click();
	}
}

function cdval_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

	$("#mstFrm #bizDtlCd").val("CDVAL");
	
	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(cdval_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  cdval_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="VLD.VAL" /> : ' + param.cdVal + ' [' + param.cdValNm +']'; //유효값
	$('#dmnvv_sel_title').html(tmphtml);
		
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno + "&rqstDtlSno=" + param.rqstDtlSno;

	//검증결과 조회
	getRqstVrfLst(param1);
	
	//grid_change.RemoveAll();
	//if(param.regTypCd == 'U') {
		getRqstChg(param1,"COL");
	//}
	

}


function cdval_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function cdval_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
}

function cdval_sheet_OnLoadExcel(){
// 	alert ("load");
	var crow  = grid_sheet.GetSelectRow();
	if(crow < 1) {
		alert("<s:message code="MSG.DMN.CHC" />"); //도메인을 선택해야한다.
		return ;
	}
	
	var DmnLnm  = grid_sheet.GetCellValue(crow, "dmnLnm");
	var rqstNo  = grid_sheet.GetCellValue(crow, "rqstNo");
	var rqstSno = grid_sheet.GetCellValue(crow, "rqstSno");
	
	for(var i = cdval_sheet.HeaderRows(); i <= cdval_sheet.RowCount(); i++) {
		cdval_sheet.SetCellValue(i, "DmnLnm", DmnLnm);
		cdval_sheet.SetCellValue(i, "rqstNo", rqstNo);
		cdval_sheet.SetCellValue(i, "rqstSno", rqstSno);
	}
	
}
</script>

<div style="clear:both; height:5px;"><span></span></div>

        <div class="divLstBtn" style="padding-right :10px">	 
            <div class="bt03">
                <button class="btn_rqst_new" id="btnCdvalRqstNew" name="btnCdvalRqstNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
				  <ul class="add_button_menu">
				    <li class="btn_new" id="btnCdvalNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li><!-- 신규 추가 -->
				    <li class="btn_excel_load" id="btnCdvalExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UP" /></a></li> <!-- 엑셀 올리기 -->
				  </ul>         
			    <button class="btn_save" id="btnCdvalSave" 	name="btnCdvalSave"><s:message code="STRG" /></button> <!-- 저장 -->
			    <button class="btn_delete" id="btnCdvalDelete" 	name="btnCdvalDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
			    <button class="btn_delete" id="btnCdvalAllDelete" 	name="btnCdvalAllDelete"><s:message code="VLD.VAL.BNDL.DEL.DEMD" /></button> <!-- 유효값 일괄삭제요청 -->
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnCdvalExcelDown" name="btnCdvalExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 --> 
	    	</div>
        </div>
         <!-- 조회버튼영역  -->
         <div style="clear:both; height:5px;"><span></span></div>
        <div class="tb_comment"> * <s:message code="MSG.VLD.VAL.DEL.DEMD.CHG" /></div> <!-- 유효값을 삭제하기 위해서는 반드시 해당 유효값을 삭제요청으로 등록해야 합니다.</br> * 유효값 일괄삭제 버튼을 클릭하시면 유효값들이 삭제요청으로 변경됩니다. -->
<div style="clear:both; height:5px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_02" class="grid_01">
	     <script type="text/javascript">createIBSheet("cdval_sheet", "100%", "150px");</script>            
	</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="dmnvv_sel_title"></div>
	</div>

<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
		<!-- 입력폼 시작 -->
		<div id="input_cdval_form_div" style="display: none;">
			<form id="cdvalInput" name="cdvalInput" method="post">
			<input type="hidden" id="gridRowCdval" name="gridRow" />
			<input type="hidden" id="rqstSnoCdval" name="rqstSno" />
			<input type="hidden" id="rqstDtlSno" name="rqstDtlSno" />
			<fieldset>
			<legend><s:message code="DEMD.STS" /></legend><!-- 요청상태 -->
			<div style="float:left; width:49%;">
				<div class="stit"><s:message code="DEMD.STS" /></div><!-- 요청상태 -->
				<div style="clear:both; height:5px;"><span></span></div>
				<div class="tb_basic">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DEMD.STS' />"><!-- 요청상태 -->
						<caption><s:message code="DMN.DEMD.STS" /></caption><!-- 도메인 요청상태 -->
						<colgroup>
						<col style="width:30%;" />
						<col style="width:70%;" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row" class="th_require"><label for="rqstDcd"><s:message code="DEMD.DSTC" /></label></th> <!-- 요청구분 -->
								<td>
									<select id="rqstDcdCdval"  name="rqstDcd">
										<option value="C"><s:message code="NEW.CHG"/></option> <!-- 신규/변경 -->
										<option value="D"><s:message code="DEL" /></option> <!-- 삭제 -->
									</select>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
								<td>
									<select id="regTypCdCdval"  name="regTypCd">
										<option value="C"><s:message code="NEW" /></option> <!-- 신규 -->
										<option value="U"><s:message code="CHG" /></option> <!-- 변경 -->
										<option value="D"><s:message code="DEL" /></option> <!-- 삭제 -->
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
			<div style="float:right; width:49%;">
				<div class="stit"><s:message code="VRFC.RSLT" /></div> <!-- 검증결과 -->
				<div style="clear:both; height:5px;"><span></span></div>
				<div class="tb_basic">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
						<caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
						<colgroup>
						<col style="width:30%;" />
						<col style="width:70%;" />
						</colgroup>
						<tbody>
						<tr>
							<th scope="row"><label for="vrfCd"><s:message code="VRFC.RSLT" /></label></th> <!-- 검증결과 -->
							<td>
								<select id="vrfCdCdval" name="vrfCd">
									<option value="1"><s:message code="REG.POSB" /></option> <!-- 등록가능 -->
									<option value="2"><s:message code="VRFC.EROR" /></option> <!-- 검증오류 -->
									<option value="3"><s:message code="CNFR" /></option> <!-- 확인 -->
									<option value="4"><s:message code="VRFC.BFM" /></option> <!-- 검증전 -->
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="vrfRmk"><s:message code="VRFC.RMRK" /></label></th> <!-- 검증비고 -->
							<td><input type="text" name="vrfRmk" id="vrfRmk" /></td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
 			<div style="clear:both; height:10px;"><span></span></div>
 			<div class="stit"><s:message code="CD.VAL.INFO" /></div><!-- 코드값 정보 -->
	 		<div style="clear:both; height:5px;"><span></span></div>
			<legend><s:message code="DMN.INFO" /></legend><!-- 도메인정보 -->
			<div class="tb_basic">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CD.VAL.INFO' />"><!-- 코드값정보 -->
					<caption><s:message code="CD.VAL.INFO.INPT" />	</caption> <!-- 코드값 정보입력 -->
					<colgroup>
					<col style="width:15%;" />
					<col style="width:35%;" />
					<col style="width:15%;" />
					<col style="width:35%;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="th_require"><label for="dmnLnm"><s:message code="DMN.LGC.NM" /></label></th> <!-- 도메인논리명 -->
							<td>
								<span class="input_inactive"><input type="text" id="dmnLnmCdval" name="dmnLnm" readonly/></span>
							</td>
							<th scope="row"><label for="uppDmnLnm"><s:message code="PRNT.DMN.LGC.NM" /></label></th> <!-- 부모도메인논리명 -->
							<td>
								<span class="input_inactive"><input type="text" id="uppDmnLnmCdval" name="uppDmnLnm" readonly/></span>
							</td>
						</tr>
						<tr>
							<th scope="row" class="th_require"><label for="cdVal"><s:message code="CD.VAL" /></label></th> <!-- 코드값 -->
							<td colspan="3"><input type="text" id="cdVal" name="cdVal" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="cdValNm"><s:message code="CD.VAL.NM" /></label></th> <!-- 코드값명 -->
							<td colspan="3"><input type="text" id="cdValNm" name="cdValNm" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="uppCdVal"><s:message code="UPRN.CD.VAL" /></label></th> <!-- 상위코드값 -->
							<td colspan="3">
								<input type="text" id="uppCdVal" name="uppCdVal" />
								<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
								<button class="btnSearchPop" id="cdvalPop"><s:message code="SRCH" /></button> <!-- 검색 -->
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="objDescnCdval"><s:message code="RMRK" /></label></th> <!-- 비고 -->
							<td colspan="3"><textarea id="objDescnCdval" name="objDescnCdval"></textarea></td>
						</tr>
					</tbody>
				</table>
				<div style="clear:both; height:10px;"><span></span></div>
				<div id="divCdvalInputBtn" style="text-align: center;">
		           <button class="btn_frm_save" type="button" id="btnCdvalGridSave" name="btnCdvalGridSave"><s:message code="STRG" /></button> <!-- 저장 -->
		           <button class="btn_frm_reset" type="button" id="btnDmnReset" name="btnDmnReset"><s:message code="INON" /></button> <!-- 초기화 -->
		        </div>
		        
			</div>
			</fieldset>
			</form>
		</div>