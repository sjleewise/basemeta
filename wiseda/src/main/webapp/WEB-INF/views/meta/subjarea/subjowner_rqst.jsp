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
<title>주제영역권한 등록요청</title>
<script type="text/javascript">
var vRqstResn = "";

$(document).ready(function() {
	
	//======================================================
    // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
    //======================================================
    //업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('SOW');
	
	//탭 초기화....
	$("#tabs").tabs();
	
	//결재처리(A), 등록요청(Q)일 경우 tab hidden
	var rqststep = $("#mstFrm #rqstStepCd").val();
	if(rqststep == "Q" || rqststep == "A" ){
		$("#tabs_layer").hide();
	}else{
		$("#tabs_layer").show();
	}
	
	// 그리드 저장
	$("#btnSave").click(function(){
		showMsgBox("CNF", "<s:message code="CNF.SAVE" />", 'Save');
	}).show();	
	
	// 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		
// 		showMsgBox("PRC", "<s:message code="REQ.SUBMIT.INIT" />");
		var cntrqst = countGridValue(grid_sheet, "vrfCd", "1");
		
		var message = "";
		if (cntrqst > 0) message += "[주제영역권한 :"+cntrqst+"건]";
		message +="<br>"+"<s:message code="CNF.RQST" />";
		
		//등록가능한지 확인한다.vrfCd = 1
		if(cntrqst > 0) {
			showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Submit');
		} else {
			showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
			return false;
		}
		
	});	
	
	//전체승인 버튼 이벤트 처리
	$("#btnAllApprove").click(function(){
		//showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
		//마스터에 전체승인을 셋팅한다.  
		$("#mstFrm #rvwStsCd").val("1");
		chgallapprove (grid_sheet);
		//$("#btnReqApprove").click();
		
	});
	
	//전체반려 버튼 이벤트 처리
	$("#btnAllReject").click(function(){
		rejectMsgBox("ALLREJECT", "<s:message code="REQ.APPROVE.REJECT" />", reqallreject);
	});
	
	//검토처리 Event Bind
	$("#btnReqApprove").click(function(){
// 		showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
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
// 			approveMsgBox("APPROVE", "<s:message code="ERR.APPROVE.CNF" />");
			showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
			return false;
		};
		
		//반려 선택시 반려사유를 입력하도록 한다.
		var tmprow = chkRvwCont(grid_sheet, "rvwStsCd", "rvwConts");
		if (tmprow > 0 ) {
			showMsgBox("INF", "<s:message code="ERR.REJECT" />");
			grid_sheet.SetSelectRow(tmprow);
			return false;
		}
		
		
		//결재처리 총건수, 승인건수, 반려건수를 메세지로 만든다.
		//승인/반려 건수
		var message = ""; 
		var cntAppr 	= countGridValue(grid_sheet, "rvwStsCd", "1");
		var cntReject  	= countGridValue(grid_sheet, "rvwStsCd", "2");
		if (cntappr > 0) message += "[주제영역권한:"+cntappr+"건 중 승인 "+cntAppr+"건, 반려 "+cntReject+"건]<br>";
		message += "<s:message code="CNF.APPR" />";

		showMsgBox("CNF", message, "Approve");	

// 		doAction('Approve');
		
	});
	
	

	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
						
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  });

	// 추가(기존) Event Bind
	$("#btnChangAdd").click(function(){
		doAction("AddWam");
	}).hide();
	
	// 삭제 Event Bind
	$("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
    	}
	});
				  
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
	// 엑셀업로 Event Bind
	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );	

	//화면리로드
    $("#btnBlank").click( function(){
    	location.href = '<c:url value="/meta/subjarea/subjowner_rqst.do" />';
    } );
	
	//요청사유 전체반영
   	$("#rqstResnAllApply").click(function(){
   		//선택해
   		var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
   		if(saveJson.data.length < 1){
   			showMsgBox("INF", "반영할 데이터를 선택 하십시오");
   			return;
   		}else{
   			//팝업호출
   			var param = ""; //$("form#frmInput").serialize();
   			openLayerPop ("<c:url value='/meta/stnd/popup/rqstResnPop.do' />", 600, 250, param);
   		}
	});
	

}
);

$(window).on('load',function() {
	initGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	if(rqststep=="A"){
		grid_sheet.SetEditable(0);
	}else if(rqststep=="Q"){
		grid_sheet.SetColEditable("subjBizDcd",0);
		grid_sheet.SetColEditable("subjLnm",0);
		grid_sheet.SetColEditable("userNm",0);
		grid_sheet.SetColEditable("rqstResn",0);
	}
	doAction("Search");
});


$(window).resize(function(){
	
});


function initGrid()
{
	
	with(grid_sheet){
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headtext  = "<s:message code='META.HEADER.SUBJ.OWNER.RQST'/>";
		//No.|상태|선택|검토상태|검토내용|요청사유|요청구분|등록유형|검증결과|DSUBJ_OWNER_ID|주제영역명|subjPnm|subjId|사용자ID|사용자명|요청일시|요청자ID|요청자명|요청번호|요청일련번호
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo);

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	 Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",  Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck", Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:1, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"rqstResn", Align:"Left", Edit:1, Hidden:0, KeyField:0},
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:1, KeyField:1},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",	Align:"Center", Edit:0},
					
// 					{Type:"Combo",   Width:100,  SaveName:"subjBizDcd",  Align:"Left", Edit:1, Hidden:0, KeyField:1},
					{Type:"Text",   Width:150,  SaveName:"subjOwnerId",  Align:"Left", Edit:0, Hidden:1},
					{Type:"Popup",   Width:400,  SaveName:"subjLnm",  Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:150,  SaveName:"subjPnm",  Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"subjId",  Align:"Left", Edit:0, Hidden:1},
					//{Type:"Text",   Width:150,  SaveName:"userId",  Align:"Left", Edit:0, KeyField:1},
					//{Type:"Popup",   Width:130,  SaveName:"userNm",    Align:"Left", Edit:1 ,KeyField:1},
					{Type:"Text",   Width:150,  SaveName:"userId",  Align:"Left", Edit:0},
					{Type:"Text",   Width:130,  SaveName:"userNm",    Align:"Left", Edit:0 },
					
// 					{Type:"Combo",   Width:100,  SaveName:"stndOwnerYn",    Align:"Left", Edit:1, Hidden:1 },
// 					{Type:"Combo",   Width:100,  SaveName:"pdmOwnerYn",    Align:"Left", Edit:1 ,KeyField:1},
// 					{Type:"Combo",   Width:100,  SaveName:"ddlOwnerYn",    Align:"Left", Edit:1 ,KeyField:1},

					{Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:0},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
				];
					
		InitColumns(cols);
		
		//콤보 목록 설정
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		
// 		SetColProperty("subjBizDcd", 	{ComboCode:"DIC|R9P|DDL", ComboText:"표준사전|R9연계물리모델|DDL생성"});
// 		SetColProperty("stndOwnerYn", 	{ComboCode:"N|Y", ComboText:"N|Y"});
// 		SetColProperty("pdmOwnerYn", 	{ComboCode:"Y|N", ComboText:"Y|N"});
// 		SetColProperty("ddlOwnerYn", 	{ComboCode:"Y|N", ComboText:"Y|N"});
		
		InitComboNoMatchText(1, "");
		
		FitColWidth();  
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
		case "New" :
			//첫행에 추가...
		 	grid_sheet.DataInsert(0);
			break;
			
			
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/subjarea/getsubjownerrqstlist.do" />", param);
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
			
			break;			
			
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = grid_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/subjarea/subjownerrqstlist.do"/>";
            var param = $('form[name=mstFrm]').serialize();
            IBSpostJson2(url, ibsSaveJson, param, ibscallback);
        	break;
        	
		case "Delete" :
	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
			if (DelJson.Code == "IBS000") return; 
			
			var url = "<c:url value="/meta/subjarea/delsubjownerrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
			
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
			
			var url = "<c:url value="/meta/subjarea/approveSubjOwer.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
	   
		case "Down2Excel":  //엑셀내려받기
		   //보여지는 컬럼들만 엑셀 다운로드          
            var downColNms = "";
           	for(var i=0; i<=grid_sheet.LastCol();i++ ){
           		if(grid_sheet.GetColHidden(i) != 1){
           			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
           		}
           	}
            grid_sheet.Down2Excel({HiddenColumn:1,  DownCols:downColNms, Merge:1});
			break;
			
		case "LoadExcel":  //엑셀업로
		
// 			grid_sheet.LoadExcel({Mode:'HeaderMatch',ExtendParam:'excelCnt='+parent.parent.$("#mstFrm").find("input[name=excelCnt]").val()});
			grid_sheet.LoadExcel({Mode:'HeaderMatch'});
			break;
			
		case "allReject":
        	doAllReject(grid_sheet, "2",$("#mstFrm #rvwConts").val());
       		break;
       		
        case "updateRqstResn":
        	var saveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(saveJson.data.length == 0) return;
    		var param = $("#mstFrm").serialize();
//     		param += $("#frmInput").serialize();
    		param += "&tblNm="+ "WAQ_SUBJ_OWNER";
    		param += "&rqstResn="+ encodeURIComponent(vRqstResn );
    		var url = "<c:url value='/meta/stnd/ajax/updateRqstResnAll.do' />";
    		IBSpostJson2(url, saveJson, param, ibscallback);
    		
        	break;       		
       		
       		
	}	   
}

 
 
/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
//  alert(res.action);
// 	alert(res.resultVO.rqstNo);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    			$("#rqstResnAllApply").show();
	    		}
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
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    			$("#rqstResnAllApply").show();
	    		}
				doAction("Search");    		
	    	} 
			break;
			
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/subjarea/subjowner_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}
 

//초기화 후 전제반려를 등록요청한다.
function reqallreject() {
	//showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
	chgallapprove (grid_sheet);
	//$("#btnReqApprove").click();
	
}
 
//사용자조회 팝업 리턴값 처리
function returnUserPop (ret, row) {
	var retjson = jQuery.parseJSON(ret);
	grid_sheet.SetCellValue(row, "userId", retjson.userId);
	grid_sheet.SetCellValue(row, "userNm", retjson.userNm);
}

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret, row) {

	var retjson = jQuery.parseJSON(ret);
   var selectedRow = grid_sheet.GetSelectRow();
// 	alert(JSON.stringify(retjson));
//     alert(retjson.fullPath);
//     alert(retjson.subjPnm);
//     alert(retjson.subjId);
    
	grid_sheet.SetCellValue(selectedRow, "subjLnm", retjson.fullPath);
	grid_sheet.SetCellValue(selectedRow, "subjPnm", retjson.subjPnm);
	grid_sheet.SetCellValue(selectedRow, "subjId", retjson.subjId);
}

//요청사유전체반영
function returnRqstResnAllPop (rqstResn) {
	vRqstResn = rqstResn;
	doAction("updateRqstResn");
}

function grid_sheet_OnPopupClick(Row,Col) {
	//사용자 검색 팝업 오픈
	if ("userNm" == grid_sheet.ColSaveName(Col)) {
		var param = "row=" +Row;
		var url = '<c:url value="/commons/damgmt/sysarea/popup/userlst_pop.do" />';
		openLayerPop(url, 700, 500, param);
	}
	//주제영역 검색 팝업
	if ("subjLnm" == grid_sheet.ColSaveName(Col)) {
		var param = "ibsSeq=" +Row;
		    param += "&sFlag="+$("#mstFrm #bizDtlCd").val();
		    param += "&subjBizDcd="+grid_sheet.GetCellValue(Row, "subjBizDcd");
		var url = '<c:url value="/meta/subjarea/popup/subjSearchPop.do" />';
		openLayerPop(url,  800, 600, param);
	}
}



function grid_sheet_OnLoadExcel() {
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
	var tmphtml = '주제영역권한 : ' + param.subjLnm ;

	$('#tritm_sel_title').html(tmphtml);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
	//검증결과 조회
	getRqstVrfLst(param1);

}


function grid_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	if(code == 0) {
	} else {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function grid_sheet_OnLoadExcel(result,code,msg) {
	var excelCnt = parent.parent.$("#mstFrm").find("input[name=excelCnt]").val();
	if(code==1){
	}else{
		// ibmsg.xml msg 사용 msg
// 		showMsgBox("ERR", msg);
		showMsgBox("ERR", "엑셀업로드 가능 건수는 <font color=red>"+excelCnt+ "</font> 건 입니다.<br>일괄등록 엑셀 데이터 건수를 확인 하십시오.");
	}
}
</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title">주제영역권한 등록</div>
	</div>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />
<!-- 검색조건 입력폼 -->
<div style="clear:both; height:5px;"><span></span></div>
		
<!-- 그리드 입력 입력 -->
<div id="grid_01" class="grid_01">
	<script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>			
</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area">
	<div class="selected_title" id="tritm_sel_title"></div>
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<div id = "tabs_layer">
<div id="tabs">
	<ul>
		<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
		<li id="tabs-rqstvrf"><a href="#tabs-2">검증결과</a></li>
		</c:if>
	</ul>
	<div id="tabs-1">

	</div>
	<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
	<div id="tabs-2">
		<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	</div>
	</c:if>
</div>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>

</body>
</html>