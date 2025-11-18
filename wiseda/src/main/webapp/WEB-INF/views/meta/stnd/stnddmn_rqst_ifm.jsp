<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title><s:message code="DMN.REG.DEMD" /></title> <!-- 도메인등록요청 -->
<script type="text/javascript">

// var dmngJson = ${codeMap.dmng} ;
// var dmnginfotpJson = ${codeMap.dmnginfotp} ;
<%-- var infotpinfolstJson ; ${codeMap.infotpinfolst} ; --%>

var selectedRow = null;
var interval = "";

$(document).keypress(function(e) {
	  if(e.which == 13) {
	    // enter pressed
//     alert("you pressed enter key");
		 // doLogin();
		 
	  }
	});
	
$(document).ready(function() {
	//탭 초기화....
// 	$("#tabs").tabs();
// 	$("li#btnNew").hide();
	//업무구분상세 초기화...
// 	$("#mstFrm #bizDtlCd").val('DMN');
	$("#mstFrm #rqstNo").val($("#mstFrm #rqstNo" , parent.document).val());
	$("#mstFrm #rqstNm").val($("#mstFrm #rqstNm" , parent.document).val());
	$("#mstFrm #bizDcd").val($("#mstFrm #bizDcd" , parent.document).val());
	$("#mstFrm #rqstStepCd").val($("#mstFrm #rqstStepCd" , parent.document).val());
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
//     initSearchPop();	//검색 팝업 초기화
// 	initSearchButton();	//검색 버튼 초기화
	
	//탭 초기화....
// 	$("#tabs").tabs().hide();
	
	//마우스 오버 이미지 초기화
// 	//imgConvert($('div.tab_navi a img'));
	
	// 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		
		//alert("등록");
		
		//등록가능한지 확인한다.vrfCd = 1
		var regchk = grid_sheet.FindText("vrfCd", "<s:message code='REG.POSB' />"); //등록가능
		//if(regchk) {alert (regchk);} 
		if(regchk > 0) {
			showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Submit');
		} else {
			showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
			return false;
		}
		
	});
	
	//전체승인 버튼 이벤트 처리
	$("#btnAllApprove").click(function(){
		doAllApprove(grid_sheet, "1");
// 		doAllApprove(cdval_sheet, "1");
		
	});
	//전체반려 버튼 이벤트 처리
	$("#btnAllReject").click(function(){
		doAllApprove(grid_sheet, "2");
// 		doAllApprove(cdval_sheet, "2");
	});
	
	//검토처리 Event Bind
	$("#btnReqApprove").click(function(){
		//alert("결재처리")
		//그리드 변경대상 체크한다.
		if (!chkSheetDataModified(grid_sheet)) {
			showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
			return false;
		}
		// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
		if (chkRvwStsCd(grid_sheet, "rvwStsCd") > -1) {
			//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
			showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
			return false;
		};
		
		//반려 선택시 반려사유를 입력하도록 한다.
		var tmprow = chkRvwCont(grid_sheet, "rvwStsCd", "rvwConts");
		if (tmprow > 0 ) {
			showMsgBox("INF", "<s:message code="ERR.REJECT" />");
			grid_sheet.SetSelectRow(tmprow);
			//선택한 상세정보를 가져온다...
			var param =  grid_sheet.GetRowJson(tmprow);
		
			//선택한 그리드의 row 내용을 보여준다.....
			var tmphtml = "<s:message code='STRD.WORD' /> : " + param.stwdLnm + " [" + param.stwdPnm +"]"; /*표준단어*/
			$('#stndword_sel_title').html(tmphtml);
			
			//var param = grid_sheet.GetRowJson(row);
			var param1 = $("#mstFrm").serialize();
			param1 += "&rqstSno=" + param.rqstSno;
			
			//param = 
			loadDetail(param1, tmprow);
			
			//검증결과 조회
// 			getRqstVrfLst(param1);
// 			$("#frmInput #rvwConts").focus();
			return false;
		}
		
		doAction("Approve");
		
	});
	
	

// 	$("#btnDmnSch").click(function(){ setDmn($("#dmnLnm").val());  });

	
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  }).show();
	
	//추가 Event Bind
	$("#btnRqstNew").show();
	
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  });
	
	// 추가(기존) Event Bind
	$("#btnChangAdd").click(function(){
//		$('div#popSearch iframe').attr('src', "<c:url value='/meta/stnd/dmnlst_pop.do' />");

		doAction("AddWam");
// 		var popup = OpenWindow("<c:url value="/meta/stnd/dmnlst_pop.do"/>","dmnSch","800","600","yes");
// 		popup.focus();
	}).show();
	
	// 삭제 Event Bind
	$("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//         	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
    	}
	}).show();
				  
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	
	// 엑셀업로 Event Bind
	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).show();
	
	//화면리로드
    $("#btnBlank").click( function(){
		parent.location.href = '<c:url value="/meta/stnd/stndtot_rqst.do" />';
    } );
		  
	// 폼저장 Event Bind
// 	$("#btnGridSave").click( function(){ doAction("SaveRow"); } );

	// 폼리셋 버튼로 Event Bind
// 	$("#btnReset").click( function(){ doAction("Reset"); } );

    $("#tabs-2").hide();
    $("#tabs-5").hide();
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	$("#btnRegRqst").hide();
	
	doAction("Search");
	
	//탭 초기화....
	$("#tabs").tabs();
	
	//$( "#layer_div" ).show();

});


$(window).resize(
	
	function(){
				
		// grid_sheet.SetExtendLastCol(1);	
	}
);


function initGrid()
{
	
	with(grid_sheet){
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headtext  = "<s:message code='META.HEADER.STNDDMN.RQST.IFM.1'/>";
			headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.2'/>";
			headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.3'/>";
			headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.IFM.4'/>";

			//headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
			//headtext += "|대분류코드|표준분류|도메인논리명|도메인물리명|논리명기준구분|물리명기준구분|도메인그룹ID|도메인그룹|인포타입ID|인포타입|데이터타입|길이|소수점|암호화여부";
			//headtext += "|용어자동생성여부|모델|상위주제영역|주제영역|주제영역ID|코드값유형|코드값부여방식|코드ID|데이터형식|목록엔티티논리명|목록엔티티물리명|목록어트리뷰트논리명|목록어트리뷰트물리명|부모도메인|최소값|최대값";
			//headtext += "|담당자ID|담당자명|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";

		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0},							
					
					{Type:"Text",   Width:100,   SaveName:"dmnDscd",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0, KeyField:1},
					{Type:"Text",   Width:100,   SaveName:"dmnLnm",   	Align:"Left", Edit:0, KeyField:1},
					{Type:"Text",   Width:100,   SaveName:"dmnPnm",   	Align:"Left", Edit:0, KeyField:1, Hidden:0}, 
					{Type:"Text",   Width:100,   SaveName:"lnmCriDs",   	Align:"Left", Edit:0, KeyField:1},
					{Type:"Text",   Width:100,   SaveName:"pnmCriDs",   	Align:"Left", Edit:0, KeyField:1},
					{Type:"Combo",  Width:60,    SaveName:"dmngId",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"dmngLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",  Width:60,    SaveName:"infotpId",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"infotpLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"dataType",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,    SaveName:"dataLen",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,    SaveName:"dataScal",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,   SaveName:"encYn",	 	Align:"Center", Edit:0, Hidden:0},
// 					{Type:"Combo",  Width:100,   SaveName:"dupYn",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,   SaveName:"sditmAutoCrtYn",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,    SaveName:"mdlLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,    SaveName:"uppSubjLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,    SaveName:"fullPath",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,    SaveName:"subjId",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,   SaveName:"cdValTypCd",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,   SaveName:"cdValIvwCd",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,    SaveName:"cdId",	 		Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,    SaveName:"dataFrm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"lstEntyLnm",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"lstEntyPnm",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"lstAttrLnm",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"lstAttrPnm",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"uppDmnLnm",	 	Align:"Left", Edit:0, Hidden:0},					
					{Type:"Text",   Width:80,    SaveName:"dmnMinVal",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:80,    SaveName:"dmnMaxVal",	 	Align:"Left", Edit:0, Hidden:0},
					
					{Type:"Text",   Width:60,    SaveName:"crgUserId",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,    SaveName:"crgUserNm",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:120,   SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:60,    SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:60,    SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,    SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:60,    SaveName:"rqstNo",  Align:"Center", Edit:0}, 
					{Type:"Int",    Width:60,    SaveName:"rqstSno",  Align:"Center", Edit:0}
				];
					
		InitColumns(cols);
		
		//콤보 목록 설정
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	 {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 		SetColProperty("dupYn", 	 {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		
// 		SetSendComboData(0,"dmngLnm", "Text");
		//SetSendComboData(0,"infotpLnm", "Text");
		
		InitComboNoMatchText(1, "");
		
		SetColHidden("dmngId"	,1);
		SetColHidden("infotpId"	,1);
		SetColHidden("mdlLnm"	,1);
		SetColHidden("uppSubjLnm",1);
		SetColHidden("subjId"	,1);
		SetColHidden("rqstNo"	,1);
		SetColHidden("rqstSno"	,1);
		SetColHidden("fullPath"	,1);
		
	  
// 		FitColWidth();  
	    //SetSheetHeight(200);
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_sheet);	

	//===========================
   
}


function doAction(sAction)
{
		
	switch(sAction)
	{
		case "New": //추가
// 			loadDetail();
			resetForm($("form#frmInput"));
	    	$("#frmInput #ibsStatus").val("I");
	    	$("#sditmAutoCrtYn").show();
			break;
			
		case "AddWam": //기존 표준도메인 추가
			var url   = "<c:url value="/meta/stnd/popup/stnddmn_pop.do"/>";
// 			var param = "?popRqst=Y";
// 			var popup = OpenWindow(url+param,"stnddmnSearch","800","600","yes");
			var param = "&popRqst=Y";
			openLayerPop(url,"1000","600", param) ;
		
		break;

		case "Reset" :
			
			//폼내용 초기화.....
			$("#frmInput")[0].reset();
			
			//입력창을 이전 상태로 복원...
// 			if($("#gridRow").val() == "") return;
			
// 			$("#dmngLnm").val(grid_sheet.GetCellValue($("#gridRow").val(),"dmngId"));
// 			$("#dmngLnm").change();
// 			ibs2formmapping($("#gridRow").val(), $("form#frmInput"), grid_sheet);
// 			$("#dmngLnm").val(grid_sheet.GetCellValue($("#gridRow").val(),"dmngId"));
// 			$("#infotpLnm").val(grid_sheet.GetCellValue($("#gridRow").val(),"infotpId"));

// 			$("#dmnStwdPop").hide();
			
			break;

		case "Delete" :
			//트리 시트의 경우 하위 레벨도 체크하도록 변경...
	    	//setTreeCheckIBS(grid_sheet);

	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/deldmnrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
			
			break;
		case "SaveRow" :
			// 공통으로 처리...
			$("#cdValTypCd").prop("disabled", false);
			$("#cdValIvwCd").prop("disabled", false);
			$("#dmnDscd").prop("disabled", false);
			var saveJson = getform2IBSjson($("#frmInput"));
// 			saveJson = $("#frmInput").serializeArray();
// 			saveJson = $("#frmInput").serializeObject();

			if (saveJson.Code == "IBS000") return;
// 			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/regdmnrqstlist.do"/>";
			
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
			
			break;
		case "Save" :
			// 공통으로 처리...
			if($("#gridRow").val() == "") return;

			form2ibsmapping($("#gridRow").val(), $("form#frmInput"), grid_sheet);
			grid_sheet.SetCellValue($("#gridRow").val(),"dmngLnm",$("#dmngLnm option:selected").text());
			grid_sheet.SetCellValue($("#gridRow").val(),"dmngId",$("#dmngLnm").val());
			grid_sheet.SetCellValue($("#gridRow").val(),"infotpLnm",$("#infotpLnm option:selected").text());
			grid_sheet.SetCellValue($("#gridRow").val(),"infotpId",$("#infotpLnm").val());
			ibsSaveJson = grid_sheet.GetSaveJson(0);
			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/regdmnrqstlist.do"/>";
			var param = $("#frmInput", parent.document).serialize();
			IBSpostJson(url, param, ibscallback);
			
			break;
			
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getdmnrqstlist.do" />", param);
			//코드도메인 조회...
// 			var param = "rqstNo="+$("#mstFrm #rqstNo").val();
			cdval_sheet.DoSearch("<c:url value="/meta/stnd/getcdvalrqstlist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
						
// 			$("#dmnStwdPop").hide();

// 			$('#frmInput')[0].reset();
//			parent.document.getElementById("sditmRqst").contentWindow.document.doAction("Search");
			if(selectedRow==null){
			   loadDetail(param, "");
			}else{
				loadDetail(param, selectedRow);
			}

			break;
			
		case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
			
			break;
	   
		case "Approve" : //결재처리
			
			var saveJson = grid_sheet.GetSaveJson(1);
			console.log(saveJson);
			//2. 필수입력 누락인 경우
// 			alert(saveJson.Code);
			if (saveJson.Code == "IBS000") return 0;
			if(saveJson.data.length == 0) return 0;
			
// 			var url = "<c:url value="/meta/stnd/approveStndDmn.do"/>";
			var url = "<c:url value="/meta/stnd/regapproveStndDmn.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJsonNoMsg(url, saveJson, param, ibscallbackNoMsg);
		
	   		break;
	   		
		case "Down2Excel":  //엑셀내려받기
		//보여지는 컬럼들만 엑셀 다운로드          
	      var downColNms = "";
	     	for(var i=0; i<=grid_sheet.LastCol();i++ ){
	     		if(grid_sheet.GetColHidden(i) != 1){
	     			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
	     		}
	     	}
			grid_sheet.Down2Excel({HiddenColumn:1, DownCols:downColNms,Merge:1, FileName:'도메인등록요청'});
			
			break;
		case "LoadExcel":  //엑셀업로
			var url = "<c:url value="/meta/stnd/popup/stnddmn_xls.do" />";
// 			var xlspopup = OpenWindow(url ,"stnddmnxls","800","600","yes");
			openLayerPop(url, 1200, 700);
			break;
			
			
		case "NewCdval":
			//도메인이 선택되어 있는지 확인한다.
			var srow = grid_sheet.GetSelectRow();
			if(srow < 1) {
				showMsgBox("ERR", "<s:message code="REQ.SELDMN.ERR" />");
				return;
			}
			
			//등록 코드 생성....
			var crow = cdval_sheet.DataInsert(-1);
			
			var seldmn = grid_sheet.GetRowJson(srow);
			
			cdval_sheet.SetCellValue(crow, "dmnLnm", seldmn.dmnLnm);
			cdval_sheet.SetCellValue(crow, "stndAsrt", seldmn.stndAsrt);
			cdval_sheet.SetCellValue(crow, "rqstNo", seldmn.rqstNo);
			cdval_sheet.SetCellValue(crow, "rqstSno", seldmn.rqstSno);

			break;
		//도메인 코드 삭제.....
		case "CdvalDelete":
			
			//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(cdval_sheet);
	    	
	    	var DelJson = cdval_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/delcdvalrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
			
			break;
			
			//모든 도메인 유효값 삭제.....
		case "CdvalAllDelete":
			
			//체크된 행 중에 입력상태인 경우 시트에서 제거...
// 	    	delCheckIBS(cdval_sheet);
	    	
// 	    	var DelJson = cdval_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
// 			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(DelJson.data.length == 0) return;
			var url = "<c:url value="/meta/stnd/delallcdvalrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, null, param, ibscallback);
			
			break;
			
		case "CdvalSearch" :
			var param = $("#mstFrm").serialize();
			
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
// 			getRqstVrfLst(param);
			
			break;
			
		case "CdvalSave" :
			
			var saveJson = cdval_sheet.GetSaveJson(0);
			
			if (saveJson.Code == "IBS000") return;
			if (saveJson.Code == "IBS010") return;
			
// 			alert("저장 가능...");
// 			return;
			
			var url = "<c:url value="/meta/stnd/regcdvalrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
			
			break;
	}
}
 
//상세정보호출
function loadDetail(param, row) {
// 	$('div#detailInfo').load('<c:url value="/meta/stnd/ajaxgrid/stnddmn_rqst_dtl.do"/>', param, function( response, status, xhr ) {
// 		  if ( status == "error" ) {
// 			    var msg = "상세정보 호출중 오류발생...";
// 			    alert( msg + xhr.status + " " + xhr.statusText );
// 			  }
// 	});
    //$("#frmInput #sditmAutoCrtYn").attr("checked",false);
	if(row != "") {
		$("#frmInput #sditmAutoCrtYn").attr("checked",false);
		ibs2formmapping(row, $("form#frmInput"), grid_sheet);
		$("#frmInput #rqstSno").val(param.rqstSno);
    	$("#frmInput #ibsStatus").val("U");
    
    	if(grid_sheet.GetCellValue(row,"regTypCd")=='U'){
    		$("#frmInput #dmnDscd").attr("readonly", true);
    	}else{
    		$("#frmInput #dmnDscd").attr("readonly", false);
    	}
	}else{
		$("#frmInput #ibsStatus").val("I");
	}
	
	if($("#frmInput #regTypCd").val()=='U'||$("#frmInput #regTypCd").val()=='D'){
	    $("#sditmAutoCrtYn").hide();	
	}else{
		$("#sditmAutoCrtYn").show();
	}
	//도메인그룹/인포타입 셋팅
// 		var param = "dmnLnm=" + $("#frmInput #dmnLnm").val();
// 		param += "&dmnPnm="+$("#frmInput #dmnPnm").val();
// 		param += "&rqstSno="+$("#frmInput #rqstSno").val();
// 		param += "&rqstNo="+$("#mstFrm #rqstNo").val();
// 		param += "&stndAsrt="+$("#frmInput #stndAsrt").val();
		var param = "dmnLnm=" + grid_sheet.GetCellValue(row,"dmnLnm");
		param += "&dmnPnm="+ grid_sheet.GetCellValue(row,"dmnPnm");
		param += "&rqstSno="+ grid_sheet.GetCellValue(row,"rqstSno");
		param += "&rqstNo="+ grid_sheet.GetCellValue(row,"rqstNo");
		param += "&stndAsrt="+ grid_sheet.GetCellValue(row,"stndAsrt");
// 		alert(param);
	setDomainInfoinit (param);
}

//================================================
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
//================================================
function ibscallback2(res){
  var result = res.RESULT.CODE;
  if(result == 0) {
//   	alert(res.RESULT.MESSAGE);
		//공통메세지 팝업 : 성공 메세지...
		
//   	showMsgBox("INF", res.RESULT.MESSAGE);
  	//alert(postProcessIBS);
  	
  	if (postProcessIBS != null) {
  		postProcessIBS(res);
  	}
  	
  } else {
//   	alsert("저장실패");
		//공통메시지 팝업 : 실패 메세지...
  	showMsgBox("ERR", res.RESULT.MESSAGE);
  }
}
/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
// 		showMsgBox("INF", res.RESULT.MESSAGE);
	}	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
//	    		alert(res.resultVO.rqstNo);
				parent.updateMst(res.resultVO);
// 	    		json2formmapping ($("#mstFrm"), res.resultVO);
// 	    		json2formmapping ($("#mstFrm", parent.document), res.resultVO);
	    		
// 	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
// // 	    		$("#mstFrm #bizDtlCd").val("DMN");
	    		
// //	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
// // 	    			$("#btnRegRqst").show();
// 	    			$("#btnRegRqst", parent.document).show();
// 	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");
				if(parent.inititemifm)
				$('#stnditem_ifm', parent.document)[0].contentWindow.doAction("Search");
	    	} 
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//요청서 저장 및 검증
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
			
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
// 	    		alert(res.resultVO.rqstNo);
				parent.updateMst(res.resultVO);
// 	    		json2formmapping ($("#mstFrm"), res.resultVO);
// 	    		json2formmapping ($("#mstFrm", parent.document), res.resultVO);
	    		
// 	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
// // 	    		$("#mstFrm #bizDtlCd").val("DMN");
	    		
// //	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
// // 	    			$("#btnRegRqst").show();
// 	    			$("#btnRegRqst", parent.document).show();
// 	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");
				if(parent.inititemifm)
				$('#stnditem_ifm', parent.document)[0].contentWindow.doAction("Search");
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
// 			var url = '<c:url value="/meta/stnd/stnddmn_rqst.do" />';
// 			var param = $('form[name=mstFrm]').serialize();
// 			location.href = url +"?"+param;
			parent.retapproveDmn = 1;
			parent.doAction("Approve");
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}


function grid_sheet_OnLoadExcel() {
	
// 	ibsSaveJson = grid_sheet.GetSaveJson(0);
	
// 	var url = "<c:url value="/meta/stnd/regdmnrqstlist.do"/>";
// 	var param = $("#frmInput", parent.document).serialize();
// 	IBSpostJson(url, param, ibscallback);
}

/*
	row : 행의 index
	col : 컬럼의 index
	value : 해당 셀의 value
	x : x좌표
	y : y좌표
*/
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(grid_sheet.GetColEditable(col)) return;

	$("#mstFrm #bizDtlCd").val("DMN");
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
    selectedRow = grid_sheet.GetSelectRow();
    //alert(selectedRow);
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = "<s:message code='DMN' /> : " + param.dmnLnm + " [" + param.dmnPnm +"]"; /*도메인*/
	//$('#stnddmn_sel_tit').html(tmphtml);
	
	loadDetail(param, row);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
	cdval_sheet.DoSearch("<c:url value="/meta/stnd/getcdvalrqstlist.do" />", param1);
	
	//검증결과 조회
	getRqstVrfLst(param1);
	
	//변경항목 조회
	grid_change.RemoveAll();
	
	if(param.regTypCd == 'U') {		
		
		getRqstChg(param1);
	}
	
	//$("#sditmAutoCrtYn").attr("checked",false);
// 	$("#gridRow").val(row);

// 	$("#dmngLnm").val(grid_sheet.GetCellValue($("#gridRow").val(),"dmngId"));
// 	$("#dmngLnm").change();
// 	ibs2formmapping(row, $("form#frmInput"), grid_sheet);
// 	$("#infotpLnm").val(grid_sheet.GetCellValue(row,"infotpId"));
// 	$("#dmngLnm").val(grid_sheet.GetCellValue(row,"dmngId"));
	
// 	$("#divTitle").text("도메인 : " + grid_sheet.GetCellValue(row,"dmnLnm"));

// 	$("#dmnStwdPop").hide();

// 	$("#dmnLnm").focus().click();
	
// 	$("#rqstSnoCdval").val($("#rqstSno").val());
	
// 	doCdvalAction("Search");
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
// 		alert("저장 성공했습니다.");
	} else {
// 		alert("저장 실패했습니다.");
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		parent.initdmnifm = true;
		$('#btnReset').click();
		//loadDetail('',"");
// 		if(grid_sheet.SearchRows() > 0  && parent.$("#clickBtnNm").val() != "ITM"  ){
// 			parent.$("#stnddmnradio").click();
// 		}	
	//	if(selectedRow!=null){
		
		//	grid_sheet.SetSelectRow(selectedRow);
		//}
	}
	//항목재조회
// 	$("#btnSearch", parent.document.getElementById("sditmRqst").contentWindow.document).click();

	/* grid_sheet.SetRowLevel(1,0);
	grid_sheet.SetRowLevel(2,1);
	grid_sheet.SetRowLevel(3,2);
	alert(grid_sheet.GetRowLevel(2));
	grid_sheet.ShowTreeLevel(0, 1);
	alert("펼치기");
	grid_sheet.ShowTreeLevel(-1); */
}
</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div id="layer_div" > 
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="STRD.DMN.REG" /></div> <!-- 표준도메인등록 -->
<!-- 	<div class="stit">시스템영역 관리</div> -->
	</div>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />
<!-- 검색조건 입력폼 -->
<div style="clear:both; height:5px;"><span></span></div>
		
<!-- 그리드 입력 입력 -->
<div id="grid_01" class="grid_01">
	<script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>			
</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- <div class="selected_title_area">
	<div class="selected_title" id="stnddmn_sel_tit"></div>
</div>
 -->
<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><s:message code="DMN.DTL.INFO" /></a></li>  <!-- 도메인 상세정보 --> 		
		<li><a href="#tabs-2"><s:message code="VLD.VAL.LST" /></a></li> <!-- 유효값 목록 -->
		
		<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
		<li id="tabs-rqstvrf"><a href="#tabs-3"><s:message code="VRFC.RSLT" /></a></li> <!-- 검증결과 -->
		</c:if>
		
		<c:if test="${waqMstr.rqstStepCd!= 'A'}">
			<li id="tabs-rqstchg"><a href="#tabs-4"><s:message code="CHG.ITEM" /></a></li> <!-- 변경항목 -->
			<li id="tabs-rqstCdchg"><a href="#tabs-5" id="cdvalinfo"><s:message code="VLD.VAL.CHG.ITEM" /></a></li> <!-- 유효값변경항목 -->
		</c:if>
	</ul>
	
	<div id="tabs-1">
		<!-- 	상세정보 ajax 로드시 이용 -->
<!-- 		<div id="detailInfo"></div> -->
		<%@include file="stnddmn_rqst_dtl.jsp" %>
	</div>
	<!-- 유효값 목록 탭 -->
	<div id="tabs-2">
		<%@include file="cdval_rqst.jsp" %>
	</div>
	<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
	<div id="tabs-3">
		<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	</div>

	</c:if>
	<c:if test="${waqMstr.rqstStepCd!='A' }">
	<div id="tabs-4">
		<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
	</div>
	<div id="tabs-5">
		<%@include file="../../commons/rqstmst/rqstChangeCol_lst.jsp" %>
	</div>
	</c:if>
</div>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<form name="mstFrm" id="mstFrm" style="display:none;">
	<input type="text" name="rqstNo" id="rqstNo" value="${waqMstr.rqstNo}" />
	<input type="text" name="rqstNm" id="rqstNm" value="${waqMstr.rqstNm}" />
	<input type="text" name="bizDcd" id="bizDcd" value="${waqMstr.bizDcd}" />
	<input type="text" name="bizDtlCd" id="bizDtlCd" value="DMN" />
	<input type="text" name="rqstStepCd" id="rqstStepCd" value="${waqMstr.rqstStepCd}" />
	<input type="text" name="rqstResn" id="rqstResn" value="${waqMstr.rqstResn}" />
</form>
</body>
</html>