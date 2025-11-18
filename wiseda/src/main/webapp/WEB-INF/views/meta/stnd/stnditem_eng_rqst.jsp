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
<title><s:message code="STRD.TERMS.REG.DEMD"/></title> <!-- 표준용어등록요청 -->
<script type="text/javascript">



$(document).ready(function() {
	
	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('SDENG');
	
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
		showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
//			doAllApprove(grid_sheet, "1");
		//마스터에 전체승인을 셋팅한다.  
		$("#mstFrm #rvwStsCd").val("1");
		chgallapprove (grid_sheet);
		$("#btnReqApprove").click();
		
	});
	
	//전체반려 버튼 이벤트 처리
	$("#btnAllReject").click(function(){
		rejectMsgBox("ALLREJECT", "<s:message code="REQ.APPROVE.REJECT" />", reqallreject);
//			doAllApprove(grid_sheet, "2");
	});
	
	//검토처리 Event Bind
	$("#btnReqApprove").click(function(){
		
		showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
		
		//alert("결재처리")
		//그리드 변경대상 체크한다.
		var cntappr = grid_sheet.RowCount();
		if (cntappr <= 0) {
			showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
			return false;
		}
						
		
		// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
		if (chkRvwStsCd(grid_sheet, "rvwStsCd") > -1) {
			//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
//				showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
			approveMsgBox("APPROVE", "<s:message code="ERR.APPROVE.CNF" />");
			return false;
		};
		
		//반려 선택시 반려사유를 입력하도록 한다.
		var tmprow = chkRvwCont(grid_sheet, "rvwStsCd", "rvwConts");
		if (tmprow > 0 ) {
			showMsgBox("INF", "<s:message code="ERR.REJECT" />");
			grid_sheet.SetSelectRow(tmprow);
			//선택한 상세정보를 가져온다...

			return false;
		}
		
		//결재처리 총건수, 승인건수, 반려건수를 메세지로 만든다.
		//승인/반려 건수
		var message = ""; 
		var cntAppr 	= countGridValue(grid_sheet, "rvwStsCd", "1");
		var cntReject  	= countGridValue(grid_sheet, "rvwStsCd", "2");
		
		if(gLangDcd == "en") {
			//Eglish
			if (cntappr > 0) message += "[total "+cntappr+" approve "+cntAppr+", reject "+cntReject+"]<br>";
		}else{
			//Korean
			if (cntappr > 0) message += "["+cntappr+"건 중 승인 "+cntAppr+"건, 반려 "+cntReject+"건]<br>";
		}
		
		
		message += "<s:message code="CNF.APPR" />";

		showMsgBox("CNF", message, "Approve");	
		
		
	});
	
	

// 	$("#btnDmnSch").click(function(){ setDmn($("#dmnLnm").val());  });

	
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
						
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  });
	
	// 추가(기존) Event Bind
	$("#btnChangAdd").click(function(){
		doAction("AddWam");
// 		var popup = OpenWindow("<c:url value="/meta/stnd/sditmlst_pop.do"/>","sditmSch","800","600","yes");
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

	//======================================================
	// 셀렉트 박스 초기화
	//======================================================
	  
	}
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
		
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
	doAction("Search");
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
		
		var headtext  = "<s:message code='META.HEADER.STNDITEM.RQST.IFM.1'/>";
		headtext += "|<s:message code='META.HEADER.STNDITEM.RQST.IFM.2'/>";
		headtext += "|<s:message code='META.HEADER.STNDITEM.RQST.IFM.3'/>";

		//headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
		//headtext += "|표준분류|표준용어논리명|표준용어물리명|논리명기준구분|물리명기준구분|도메인논리명|도메인물리명|도메인그룹|인포타입ID|인포타입논리명|데이터타입|길이|소수점|암호화여부|고객정보변환여부";
		//headtext += "|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";
		
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	 Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",  Align:"Center", Edit:0, Hidden:0},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck", Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",	Align:"Center", Edit:0},							

					{Type:"Combo",  Width:100,  SaveName:"stndAsrt",      Align:"Left", Edit:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"sditmLnm",  Align:"Left", Edit:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"sditmPnm",  Align:"Left", Edit:0, KeyField:1}, 
					{Type:"Text",   Width:100,  SaveName:"lnmCriDs",  Align:"Left", Edit:0, KeyField:0}, 
					{Type:"Text",   Width:100,  SaveName:"pnmCriDs",  Align:"Left", Edit:0, KeyField:0}, 
					{Type:"Text",   Width:60,   SaveName:"dmnLnm", 	  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,   SaveName:"dmnPnm",    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"dmngLnm",   Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",  Width:60,   SaveName:"infotpId",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"infotpLnm", Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:90,   SaveName:"dataType",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:90,   SaveName:"dataLen",	  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:90,   SaveName:"dataScal",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",  Width:130,  SaveName:"encYn",	  Align:"Center", Edit:0},	
					{Type:"Combo",  Width:130,  SaveName:"persInfoCnvYn", Align:"Center", Edit:0},					
					
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
		
// 		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"N|Y"});
		
		InitComboNoMatchText(1, "");
		
// 		SetColHidden("dmngId"	,1);
		SetColHidden("infotpId"	,1);
		SetColHidden("rqstNo"	,1);
		SetColHidden("rqstSno"	,1);
		SetColHidden("dmngLnm"	,0);
		SetColHidden("dmnLnm"	,1);
		SetColHidden("dmnPnm"	,1);
		SetColHidden("lnmCriDs"	,1);
		SetColHidden("pnmCriDs"	,1);
	  
		// FitColWidth();  
		
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_sheet);	
	//===========================
   
}


//초기화 후 전제반려를 등록요청한다.
function reqallreject() {
	showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
	chgallapprove (grid_sheet);
	$("#btnReqApprove").click();
	
}



function doAction(sAction)
{
		
	switch(sAction)
	{
		case "New": //추가
			loadDetail();
			
			break;
			
		case "AddWam": //기존 표준용어 추가
			var url   = "<c:url value="/meta/stnd/popup/stnditem_pop.do"/>";
			var param = "?popRqst=Y";
			var popup = OpenWindow(url+param,"stnditemSearch","800","600","yes");
			
			break;
		case "Reset" :
			//폼내용 초기화.....
			$("#frmInput")[0].reset();
			//입력창을 이전 상태로 복원...
// 			if($("#gridRow").val() == "") return;
			
// 			$("#dmngId").val(grid_sheet.GetCellValue($("#gridRow").val(),"dmngLnm"));
// 			set_infoType_select($("#dmngId").val());
// 			ibs2formmapping($("#gridRow").val(), $("form#frmInput"), grid_sheet);
// 			$("#dmngId").val(grid_sheet.GetCellValue($("#gridRow").val(),"dmngLnm"));
// 			$("#dmngLnm").val(grid_sheet.GetCellText($("#gridRow").val(),"dmngLnm"));
// 			$("#infotpLnm").val(grid_sheet.GetCellValue($("#gridRow").val(),"infotpId"));

// 			$("#sditmStwdPop").hide();
			
			break;

		case "Delete" :
			//트리 시트의 경우 하위 레벨도 체크하도록 변경...
	    	//setTreeCheckIBS(grid_sheet);

	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (DelJson.Code == "IBS000") return; 
			
// 	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/delSditmrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
			
			break;
		case "SaveRow":
			// 공통으로 처리...
			var saveJson = getform2IBSjson($("#frmInput"));
// 			ibsSaveJson = $("#frmInput").serializeArray();
// 			ibsSaveJson = $("#frmInput").serializeObject();

			if (saveJson.Code == "IBS000") return;
// 			if(ibsSaveJson.data.length == 0) return;
			
			//var url = "<c:url value="/meta/stnd/regitemrqstlist.do"/>";
			
			var url = "<c:url value="/meta/stnd/regItemEngRqstlist.do"/>";
			
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);

			break;
		case "Save" :
						
// 			if($("#gridRow").val() == "") return;

// 			form2ibsmapping($("#gridRow").val(), $("form#frmInput"), grid_sheet);
// 			grid_sheet.SetCellValue($("#gridRow").val(),"infotpLnm",$("#infotpLnm option:selected").text());
// 			grid_sheet.SetCellValue($("#gridRow").val(),"infotpId",$("#infotpLnm").val());
// 			ibsSaveJson = grid_sheet.GetSaveJson(0);
// 			if(ibsSaveJson.data.length == 0) return;
			
// 			var url = "<c:url value="/meta/stnd/regsditmrqstlist.do"/>";
// 			var param = $("#frmInput", parent.document).serialize();
// 			IBSpostJson(url, param, ibscallback);
			
			break;
			
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getItemEngRqstList.do" />", param);

			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
			
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
			
			var url = "<c:url value="/meta/stnd/approveStndItemEng.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
	   
		case "Down2Excel":  //엑셀내려받기
		
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
			
			break;
		case "LoadExcel":  //엑셀업로
		
// 			grid_sheet.LoadExcel({Mode:'HeaderMatch'});
			var url = "<c:url value="/meta/stnd/popup/stnditem_xls.do" />";
			
			var xlspopup = OpenWindow(url ,"stnditemxls","800","600","yes");
		
			break;
	}	   
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/meta/stnd/ajaxgrid/stnditem_eng_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
			    alert( msg + xhr.status + " " + xhr.statusText );
		  } else {
			  
			  setDmngInfoInit();
			 
			  //오류가 아닐 경우 도메인정보를 셋팅한다.
			  //setDomainInfo ();
		  }
	});
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
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd").val("SDITM");
	    		
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
// 	    		$("#mstFrm #bizDtlCd").val("SDITM");
	    		
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
			var url = '<c:url value="/meta/stnd/stnditem_eng_rqst.do" />';
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
	
// 	var url = "<c:url value="/meta/stnd/regsditmrqstlist.do"/>";
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
	
	$("#gridRow").val(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="STRD.TERMS" /> : ' + param.sditmLnm + ' [' + param.sditmPnm +']'; //표준용어
	$('#stnditem_sel_tit').html(tmphtml);
	
	loadDetail(param);
	
	//검증결과 조회
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
	//alert(param1);
	
	//검증결과 조회
	getRqstVrfLst(param1);
	
	//변경항목 조회
	//grid_change.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1);
	}

}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
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
	
	loadDetail();
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="STRD.TERMS.REG"/></div> <!-- 표준용어등록 -->
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
	<div class="selected_title" id="stnditem_sel_title"></div>
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><s:message code="STRD.TERMS.INFO.DTL" /></a></li> <!-- 표준용어정보 상세 -->
		
		<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
		<li id="tabs-rqstvrf"><a href="#tabs-2"><s:message code="VRFC.RSLT" /></a></li> <!-- 검증결과 -->
		</c:if>
	</ul>
	<div id="tabs-1">
	<!-- 	상세정보 ajax 로드시 이용 -->
	<div id="detailInfo"></div>

	</div>
	<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
	<div id="tabs-2">
		<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	</div>
	</c:if>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>

</body>
</html>