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

var interval = "";

$(document).ready(function() {
	
	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('DMN');
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
//     initSearchPop();	//검색 팝업 초기화
// 	initSearchButton();	//검색 버튼 초기화
	
	//탭 초기화....
	$("#tabs").tabs();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
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
			var tmphtml = '<s:message code="STRD.WORD" /> : ' + param.stwdLnm + ' [' + param.stwdPnm +']'; /*표준단어*/
			$('#stndword_sel_title').html(tmphtml);
			
			//var param = grid_sheet.GetRowJson(row);
			var param1 = $("#mstFrm").serialize();
			param1 += "&rqstSno=" + param.rqstSno;
			
			//param = 
			//loadDetail(param1);
			
			//검증결과 조회
// 			getRqstVrfLst(param1);
// 			$("#frmInput #rvwConts").focus();
			return false;
		}
		
		doAction("Approve");
		
	});
	
	

// 	$("#btnDmnSch").click(function(){ setDmn($("#dmnLnm").val());  });

	
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
						
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  });
	
	// 추가(기존) Event Bind
	$("#btnChangAdd").click(function(){
//		$('div#popSearch iframe').attr('src', "<c:url value='/meta/stnd/dmnlst_pop.do' />");

		doAction("AddWam");
// 		var popup = OpenWindow("<c:url value="/meta/stnd/dmnlst_pop.do"/>","dmnSch","800","600","yes");
// 		popup.focus();
	});
	
	// 삭제 Event Bind
	$("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//         	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
    	}
	});
				  
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
	// 엑셀업로 Event Bind
	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
		  
	// 폼저장 Event Bind
// 	$("#btnGridSave").click( function(){ doAction("SaveRow"); } );

	// 폼리셋 버튼로 Event Bind
// 	$("#btnReset").click( function(){ doAction("Reset"); } );

    
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	
	
	//loadDetail();

	doAction("Search");

	//doAction("Search");
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
		
		var headtext  = "<s:message code='META.HEADER.STNDDMN.RQST.1'/>";
			headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.2'/>";
			headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.2'/>";
			headtext += "|<s:message code='META.HEADER.STNDDMN.RQST.4'/>";

			//headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
			//headtext += "|도메인논리명|도메인물리명|논리명기준구분|도메인그룹ID|도메인그룹논리명|인포타입ID|인포타입논리명|데이터타입|길이|소수점";
			//headtext += "|용어자동생성여부|모델|상위주제영역|주제영역|주제영역ID|코드값유형|코드값부여방식|데이터형식|목록엔티티논리명|목록엔티티물리명|상위도메인|출처구분";
			//headtext += "|담당자ID|담당자명|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";

		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",		Align:"Center", Edit:0},							
					
					{Type:"Text",   Width:100,  SaveName:"dmnLnm",   	Align:"Left", Edit:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"dmnPnm",   	Align:"Left", Edit:0, KeyField:1}, 
					{Type:"Text",   Width:100,  SaveName:"lnmCriDs",   	Align:"Left", Edit:0, KeyField:1},
					{Type:"Combo",   Width:60,   SaveName:"dmngId",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dmngLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",   Width:60,   SaveName:"infotpId",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"infotpLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dataType",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dataLen",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dataScal",	 	Align:"Left", Edit:0, Hidden:0},
					
					{Type:"Combo",   Width:60,   SaveName:"sditmAutoCrtYn",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"mdlLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"uppSubjLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"fullPath",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"subjId",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",   Width:60,   SaveName:"cdValTypCd",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",   Width:60,   SaveName:"cdValIvwCd",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dataFrm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"lstEntyLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"lstEntyPnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"uppDmnLnm",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dmnOrgDs",	 	Align:"Left", Edit:0, Hidden:0},

					{Type:"Text",   Width:60,   SaveName:"crgUserId",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"crgUserNm",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0}
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
		SetColHidden("crgUserId"	,1);
		SetColHidden("crgUserNm"	,1);
	  
// 		FitColWidth();  
		
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
			loadDetail();
			
			break;
			
		case "AddWam": //기존 표준도메인 추가
			var url   = "<c:url value="/meta/stnd/popup/stnddmn_pop.do"/>";
			var param = "?popRqst=Y";
			var popup = OpenWindow(url+param,"stnddmnSearch","800","600","yes");
		
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
			
			break;
			
		case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
			
			break;
	   
		case "Approve" : //결재처리
			
			var saveJson = grid_sheet.GetSaveJson(0);
			
			//2. 필수입력 누락인 경우
// 			alert(saveJson.Code);
			if (saveJson.Code == "IBS000") return;
// 			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/approveStndDmn.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
	   		
		case "Down2Excel":  //엑셀내려받기
		
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
			
			break;
		case "LoadExcel":  //엑셀업로
		
			var url = "<c:url value="/meta/stnd/popup/stnddmn_xls.do" />";
			
			var xlspopup = OpenWindow(url ,"stnddmnxls","800","600","yes");
		
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
		case "CdvalSearch" :
			var param = $("#mstFrm").serialize();
			
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
// 			getRqstVrfLst(param);
			
			break;
			
		case "CdvalSave" :
			
			var saveJson = cdval_sheet.GetSaveJson(0);
			
// 			if (saveJson.Code == "IBS010") return;
			
// 			alert("저장 가능...");
// 			return;
			
			var url = "<c:url value="/meta/stnd/regcdvalrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
			
			break;
	}
}
 
//상세정보호출
// function loadDetail(param) {
// 	$('div#detailInfo').load('<c:url value="/meta/stnd/ajaxgrid/stnddmn_rqst_dtl.do"/>', param, function( response, status, xhr ) {
// 		  if ( status == "error" ) {
// 			    var msg = "상세정보 호출중 오류발생...";
// 			    alert( msg + xhr.status + " " + xhr.statusText );
// 			  }
// 	});
// }


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
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd").val("DMN");
	    		
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
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
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd").val("DMN");
	    		
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/stnd/stnddmn_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
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
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="DMN" /> : ' + param.dmnLnm + ' [' + param.dmnPnm +']'; /*도메인*/
	$('#stnddmn_sel_tit').html(tmphtml);
	
	loadDetail(param);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
	cdval_sheet.DoSearch("<c:url value="/meta/stnd/getcdvalrqstlist.do" />", param1);
	
	getRqstVrfLst(param1);
	
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
	<script type="text/javascript">createIBSheet("grid_sheet", "100%", "150px");</script>			
</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area">
	<div class="selected_title" id="stnddmn_sel_tit"></div>
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><s:message code="DMN.INFO.DTL" /></a></li> <!-- 도메인정보 상세 -->
		<li><a href="#tabs-2"><s:message code="VLD.VAL.LST" /></a></li> <!-- 유효값 목록 -->
		<li id="tabs-rqstvrf"><a href="#tabs-3"><s:message code="VRFC.RSLT" /></a></li> <!-- 검증결과 -->
	</ul>
	<div id="tabs-1">
		<!-- 	상세정보 ajax 로드시 이용 -->
		<div id="detailInfo"></div>
	</div>
	<!-- 유효값 목록 탭 -->
	<div id="tabs-2">
		<%@include file="cdval_rqst.jsp" %>
	</div>
	<div id="tabs-3">
		<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	</div>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>
</body>
</html>