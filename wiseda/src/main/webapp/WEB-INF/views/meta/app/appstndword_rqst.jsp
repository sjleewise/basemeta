<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title>APP단어등록요청</title>
<script type="text/javascript">

var interval = "";

$(document).ready(function() {
	//탭 초기화....
	$("#tabs").tabs();

	//업무구분상세 초기화...
// 	alert("요청마스트 부모값으로 업데이트....");
	$("#mstFrm #bizDtlCd").val('APD');
	$("#mstFrm #rqstNo").val($("#mstFrm #rqstNo" , parent.document).val());
	$("#mstFrm #rqstNm").val($("#mstFrm #rqstNm" , parent.document).val());
	$("#mstFrm #bizDcd").val($("#mstFrm #bizDcd" , parent.document).val());
	$("#mstFrm #rqstStepCd").val($("#mstFrm #rqstStepCd" , parent.document).val());
	
	//탭 초기화....
// 	$("#tabs").tabs().hide();
	
	//마우스 오버 이미지 초기화
// 	//imgConvert($('div.tab_navi a img'));
	
	// 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		
		//alert("등록");
		
		//등록가능한지 확인한다.vrfCd = 1
		var regchk = grid_sheet.FindText("vrfCd", "등록가능");
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
	});
	//전체반려 버튼 이벤트 처리
	$("#btnAllReject").click(function(){
		doAllApprove(grid_sheet, "2");
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
			var tmphtml = '표준단어 : ' + param.appStwdLnm + ' [' + param.appStwdPnm +']';
			$('#stndword_sel_title').html(tmphtml);
			
			//var param = grid_sheet.GetRowJson(row);
			var param1 = $("#mstFrm").serialize();
			param1 += "&rqstSno=" + param.rqstSno;
			
			//param = 
			loadDetail(param1, tmprow);
			
			//검증결과 조회
			getRqstVrfLst(param1);
			$("#frmInput #rvwConts").focus();
			return false;
		}
		
		doAction("Approve");
		
	});
	
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  }).show();
	
	//추가 Event Bind
	$("#btnRqstNew").show();
	
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  }).show();
	
	// 추가(기존) Event Bind
	$("#btnChangAdd").click(function(){
		
		doAction("AddWam");
// 		var popup = OpenWindow("<c:url value="/meta/app/popup/stndword_pop.do"/>","stndwordSearch","800","600","yes");
// 		popup.focus();
		
	}).show();

	// 추가(엑셀업로드)Event Bind
	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); }).show();
	
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
	
		  
	// 폼저장이벤 Event Bind
// 	$("#btnGridSave").click( function(){ 	doAction("SaveRow");	} );

	// 폼초기화 버튼 Event Bind
// 	$("#btnReset").click( function(){ doAction("Reset"); } );
	
	//화면리로드
    $("#btnBlank").click( function(){
		parent.location.href = '<c:url value="/meta/app/appstndword_rqst.do" />';
    } );

	
	//======================================================
	// 셀렉트 박스 초기화
	//======================================================
	  
	}
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
// 	alert(grid_sheet.Version());
	var rqststep = $("#mstFrm #rqstStepCd").val();
// 	alert(rqststep);
// 	alert(grid_sheet);
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
// 	setDispRqstMainButton(rqststep, grid_sheet);
// 	$("#btnRegRqst").hide();
// 	callRqstDispButton();
	setDispRqstMainButton(rqststep, grid_sheet);
	checkApproveYn($("#mstFrm"));
	doAction("Search");
	
	//탭 초기화....
// 	$("#tabs").tabs();
	
	//$( "#layer_div" ).show();
	
});

// function callRqstDispButton() {
// 	var rqststep = $("#mstFrm #rqstStepCd").val();
// 	//============================================
// 	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
// 	//============================================
// 	setDispRqstMainButton(rqststep, grid_sheet);
// 	$("#btnRegRqst").hide();
	
// // 	doAction("Search");
// }


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
		
		var headers = [
						{Text:"<s:message code='META.HEADER.APPSTNDWORD.RQST'/>", Align:"Center"}
					];
			//No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|APP단어논리명|APP단어물리명|영문의미|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호
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
						{Type:"Text",   Width:100,  SaveName:"appStwdLnm",   	Align:"Left", Edit:0, KeyField:1},
						{Type:"Text",   Width:100,  SaveName:"appStwdPnm",   	Align:"Left", Edit:0, KeyField:1}, 
						{Type:"Text",   Width:100,   SaveName:"engMean", 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
						{Type:"Text",   Width:80,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:60,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:60,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
						{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0}, 
						{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0}
					];
						
			InitColumns(cols);
			
			//콤보 목록 설정
			SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
			SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
			SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
			SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
			
		
		
		InitComboNoMatchText(1, "");
		
// 		SetColHidden("ibsStatus",1);
		SetColHidden("rqstNo"	,1);
		SetColHidden("rqstSno"	,1);
	  
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
// 			loadDetail();
			resetForm($("form#frmInput"));
	    	$("#frmInput #ibsStatus").val("I");
			
			break;
			
		case "AddWam": //기존 표준단어 추가
// 			var popup = OpenWindow("<c:url value="/meta/app/popup/stndword_pop.do"/>","stndwordSearch","800","600","yes");
		
			var url   = "<c:url value="/meta/app/popup/appstndword_pop.do"/>";
// 			var param = "?popRqst=Y";
// 			var popup = OpenWindow(url+param,"stndwordSearch","800","600","yes");
			var param = "&popRqst=Y";
			openLayerPop(url,"800","600", param) ;
			
		
		break;

		case "Reset" :
			
			//폼내용 초기화.....
			$("#frmInput")[0].reset();
			
			//입력창을 이전 상태로 복원...
			//if($("#gridRow").val() == "") return;
			
			//ibs2formmapping($("#gridRow").val(), $("form#frmInput"), grid_sheet);
			
			break;

		case "Delete" :
			//트리 시트의 경우 하위 레벨도 체크하도록 변경...
	    	//setTreeCheckIBS(grid_sheet);

	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	//나중에 공통함수로 전환.... (그리스명, 조인savenm, joinchar, 체크savenm,);
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	if (DelJson.Code == "IBS000") return;
	    	
	    	
// 	    	var tmpkey = getibscheckjoin(grid_sheet, "rqstSno");
	    	
	    	var url = "<c:url value="/meta/app/delstwdrqstlist.do"/>";
	    	var param = $("#mstFrm").serialize();
// 	    	var param = $("#mstFrm").serialize()+"&joinkey="+tmpkey;
	    	IBSpostJson2(url, DelJson, param, ibscallback);
			
			break;
		case "SaveRow" :
			// 공통으로 처리...
			
			ibsSaveJson = getform2IBSjson($("#frmInput"));
// 			ibsSaveJson = $("#frmInput").serializeArray();
// 			ibsSaveJson = $("#frmInput").serializeObject();

			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/app/regStndWordRqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
			
		case "Search":
			//var param = "rqstNo="+$("#rqstNo", parent.document).val();
// 			alert($("#frmInput #bizDtlCd").val());
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/app/getstwdrqstlist.do" />", param);
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
			
		 	//grid_sheet.DoSearchScript("testJsonlist");
						
			//$('#frmInput')[0].reset();
			
			loadDetail(param, "");
			
			break;
			
		case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
			
			break;
			
		case "Approve" : //결재처리
			
			var saveJson = grid_sheet.GetSaveJson(1);
			
			//2. 필수입력 누락인 경우
// 			alert(saveJson.Code);
			if (saveJson.Code == "IBS010") return 0;
			if(saveJson.data.length == 0) return 0;
			
// 			doAllApprove(grid_sheet, "1");
// 			var url = "<c:url value="/meta/app/approveStndWord.do"/>";
			var url = "<c:url value="/meta/app/regapproveStndWord.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
		case "Down2Excel":  //엑셀내려받기
		
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'비표준단어등록요청'});
			
			break;
		case "LoadExcel":  //엑셀업로드 팝업 호출
// 			grid_sheet.LoadExcel();
			var url = "<c:url value="/meta/app/popup/appstndword_xls.do" />";
// 			var xlspopup = OpenWindow(url ,"stndwordxls","800","600","yes");
			openLayerPop(url, 800, 600);
			
			break;
	}	   
}
 
//상세정보호출
function loadDetail(param, row) {
// 	$('div#detailInfo').load('<c:url value="/meta/app/ajaxgrid/stndword_rqst_dtl.do"/>', param, function( response, status, xhr ) {
// 		  if ( status == "error" ) {
// 			    var msg = "상세정보 호출중 오류발생...";
// 			    alert( msg + xhr.status + " " + xhr.statusText );
// 			  }
// 	});
	if(row != "") {
		ibs2formmapping(row, $("form#frmInput"), grid_sheet);
		$("#frmInput #rqstSno").val(param.rqstSno);
    	$("#frmInput #ibsStatus").val("U");
	}else{
		$("#frmInput #ibsStatus").val("I");
	}
	
}


//================================================
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
//================================================
function ibscallback2(res){
    var result = res.RESULT.CODE;
    if(result == 0) {
//     	alert(res.RESULT.MESSAGE);
		//공통메세지 팝업 : 성공 메세지...
		
//     	showMsgBox("INF", res.RESULT.MESSAGE);
    	//alert(postProcessIBS);
    	
    	if (postProcessIBS != null) {
    		postProcessIBS(res);
    	}
    	
    } else {
//     	alsert("저장실패");
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
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
//	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
	    		
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
//	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
	    		
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
			var url = '<c:url value="/meta/app/appstndword_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
	}
}


/*======================================================================*/
// IBSheet Event 함수 처리
/*======================================================================*/

function grid_sheet_OnLoadExcel() {
	
	ibsSaveJson = grid_sheet.GetSaveJson(0);
	
	var url = "<c:url value="/meta/app/regstwdrqstlist.do"/>";
	var param = $("#frmInput", parent.document).serialize();
	IBSpostJson(url, param, ibscallback);
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
// 	if ("ibsCheck" == grid_sheet.ColSaveName(col)) {
// 		alert(" 체크박스 선택...");
// 		return ;
// 	};
	
	
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = 'APP단어 : ' + param.appStwdLnm + ' [' + param.appStwdPnm +']';
	$('#stndword_sel_title').html(tmphtml);
	
	//var param = grid_sheet.GetRowJson(row);
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
	loadDetail(param, row);

	//검증결과 조회
	getRqstVrfLst(param1);
	
	//변경항목 조회
	grid_change.RemoveAll();
	if(param.regTypCd == 'U' || param.vrfCd == '5') {
		getRqstChg(param1);
		formElementChgReadOnly("appStwdLnm|appStwdPnm",true);
	} else if(param.regTypCd == 'D'){
		formElementChgReadOnly("appStwdLnm|appStwdPnm",true);
	}
	
	
// 	$("#gridRow").val(row);
	
// 	ibs2formmapping(row, $("form#frmInput"), grid_sheet);

// 	$("#divTitle").text("표준단어 : " + grid_sheet.GetCellValue(row,"appStwdLnm"));
	
// 	$("#appStwdLnm").focus().click();
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	//1차결제때 반려할 경우 2차 ~ 결제때 변경불가 
	for(var i=1;i<=grid_sheet.GetTotalRows();i++){
		if(grid_sheet.GetCellValue(i, 3)==2){
	 		 //"rvwStsCd" - 3 검토상태
	 		 //"rvwConts" - 4 검토내용
			grid_sheet.SetCellEditable(i, 3, 0);
			grid_sheet.SetCellEditable(i, 4, 0);
		} 
	} 
	
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code == 0) {
// 		$('#btnfrmReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
	} else {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div id="layer_div" > 
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title">APP단어등록</div>
<!-- 	<div class="stit">시스템영역 관리</div> -->
	</div>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:5px;"><span></span></div>

<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />

<div style="clear:both; height:5px;"><span></span></div>
		
<!-- 그리드 입력 입력 -->
<div id="grid_01" class="grid_01">
	<script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>			
</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area">
	<div class="selected_title" id="stndword_sel_title"></div>
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<div id="tabs">
	<ul>
		<li><a href="#tabs-1">APP단어 상세정보</a></li>
		<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
		<li id="tabs-rqstvrf"><a href="#tabs-2">검증결과</a></li>
		<li id="tabs-rqstchg"><a href="#tabs-3">변경항목</a></li>
		</c:if>
	</ul>
	<div id="tabs-1">
		<!-- 	상세정보 ajax 로드시 이용 -->
<!-- 		<div id="detailInfo"></div> -->
		<%@include file="appstndword_rqst_dtl.jsp" %>
	</div>
	<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
	<div id="tabs-2">
		<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	</div>
<!-- 	변경정보 -->
	<div id="tabs-3">
		<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
	</div>
	</c:if>
</div>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>
<%-- <form name="mstFrm" id="mstFrm" style="display:none;"> --%>
<%-- 	<input type="text" name="rqstNo" id="rqstNo" value="${waqMstr.rqstNo}" /> --%>
<%-- 	<input type="text" name="rqstNm" id="rqstNm" value="${waqMstr.rqstNm}" /> --%>
<%-- 	<input type="text" name="bizDcd" id="bizDcd" value="${waqMstr.bizDcd}" /> --%>
<!-- 	<input type="text" name="bizDtlCd" id="bizDtlCd" value="APD" /> -->
<%-- 	<input type="text" name="rqstStepCd" id="rqstStepCd" value="${waqMstr.rqstStepCd}" /> --%>
<%-- 	<input type="text" name="rqstResn" id="rqstResn" value="${waqMstr.rqstResn}" /> --%>
<%-- </form> --%>
</body>
</html>