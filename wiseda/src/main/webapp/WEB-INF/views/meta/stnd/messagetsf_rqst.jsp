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
<title><s:message code="CD.REG.DEMD" /></title> <!-- 코드 등록요청 -->
<script type="text/javascript">

$(document).ready(function() {

	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('MST');
	
	//======================================================
    // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
    //======================================================
//     initSearchPop();	//검색 팝업 초기화
// 	initSearchButton();	//검색 버튼 초기화
    
    $("li#btnNew a").html('<span class="ui-icon ui-icon-folder-open"></span><s:message code="MSG.TFCT.DEMD" />'); /* 메시지이관요청 */
    $("li#btnChangAdd a").html('<span class="ui-icon ui-icon-folder-open"></span><s:message code="TFCT.DEL.DEMD" />'); /* 이관삭제요청 */
    

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
//		doAllApprove(grid_sheet, "2");
// 		doAllApprove(cdval_sheet, "2");
		rejectMsgBox("ALLREJECT", "<s:message code="REQ.APPROVE.REJECT" />", "allReject");
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
			var tmphtml = '<s:message code="STRD.WORD" /> : ' + param.msgCd + ' [' + param.msgConts +']';/*표준단어*/
			$('#msg_sel_title').html(tmphtml);
			
			//var param = grid_sheet.GetRowJson(row);
			var param1 = $("#mstFrm").serialize();
			param1 += "&rqstSno=" + param.rqstSno;
			
			//param = 
			loadDetail(param1);
			
			//검증결과 조회
// 			getRqstVrfLst(param1);
// 			$("#frmInput #rvwConts").focus();
			return false;
		}
		
		doAction("Approve");
		
	});
	
	

// 	$("#btnDmnSch").click(function(){ setDmn($("#dmnLnm").val());  });

	
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");   });
						
	// 추가(신규/변경) Event Bind
	$("#btnNew").click(function(){ doAction("Add");  });
	
	// 삭제요청 Event Bind
	$("#btnChangAdd").click(function(){
		doAction("AddWam");
	//	alert("미개발");
// 		var popup = OpenWindow("<c:url value="/meta/stnd/sditmlst_pop.do"/>","sditmSch","800","600","yes");
// 		popup.focus();
	});
	
	// 삭제 Event Bind
	$("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 코드
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//         	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
    	}
	});
				  
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
	// 엑셀업로 Event Bind
	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).hide();	

	//======================================================
	// 셀렉트 박스 초기화
	//======================================================
	
	//화면리로드
    $("#btnBlank").click( function(){
    	location.href = '<c:url value="/meta/stnd/messagetsf_rqst.do" />';
    } );
	
	



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
		
		var headtext  = "<s:message code='META.HEADER.MESSAGETST.RQST.1'/>";
		headtext += "|<s:message code='META.HEADER.MESSAGETST.RQST.2'/>";
		headtext += "|<s:message code='META.HEADER.MESSAGETST.RQST.3'/>";
		headtext += "|<s:message code='META.HEADER.MESSAGETST.RQST.4'/>";

		//headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과";
		//headtext += "|타겟DB_ID|타겟DB명|타겟스키마ID|타겟스키마명";
		//headtext += "|메시지ID|유형구분코드|업무구분코드|시스템구분코드|메시지코드|메시지내용|사용여부";
		//headtext += "|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호";
	
	     var headers = [
	     			{Text:headtext, Align:"Center"}
	     		];
	     
	     var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
	     
	     InitHeaders(headers, headerInfo); 
         
	     var cols = [						
	     			{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
	     			{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
	     			{Type:"CheckBox", Width:40,   SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
	     			{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:1, Hidden:1},						
	     			{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:1, Hidden:1},						
	     			{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:0, KeyField:1},						
	     			{Type:"Combo",  Width:60,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
	     			{Type:"Combo",  Width:80,  SaveName:"vrfCd",		Align:"Center", Edit:0},
	     			{Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgId",   	Align:"Left", Edit:0,Hidden:1},
	     			{Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgPnm",   	Align:"Left", Edit:0,Hidden:0},
	     			{Type:"Text",   Width:100,  SaveName:"tgtDbSchId",   	Align:"Left", Edit:0,Hidden:1},
	     			{Type:"Text",   Width:100,  SaveName:"tgtDbSchPnm",   	Align:"Left", Edit:0,Hidden:0},
                    {Type:"Text",   Width:40,  SaveName:"msgId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:100,  SaveName:"typDivCd",    Align:"Left", Edit:0}, 
                    {Type:"Combo",   Width:100,  SaveName:"bizDivCd",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"sysDivCd",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"msgCd",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:300,  SaveName:"msgConts",    Align:"Left", Edit:0}, 
                    {Type:"Combo",   Width:60,  SaveName:"useYn",    Align:"Center", Edit:0},
	 	    		{Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
	 	    		{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
	 	    		{Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
	 	    		{Type:"Text",   Width:50,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
	 	    		{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
	 	    		{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
	 	    	];
	 	    		
	            InitColumns(cols);
	            
	            SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
	            SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
	            SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	            SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
	            SetColProperty("useYn", 	{ComboCode:"Y|N", ComboText:"Y|N"});	            
	          	SetColProperty("typDivCd",	${codeMap.msgPtrnDvcdibs});
	    		SetColProperty("bizDivCd",	${codeMap.bizDivCdibs});

	            //InitComboNoMatchText(1, "");

	            InitComboNoMatchText(1, "");
	            //히든 컬럼 설정...

	            //SetColHidden("rqstUserId",1);
                SetSheetHeight("250");
	            // FitColWidth();  
	            if($("#mstFrm #rqstStepCd").val() =="A"){
	            	SetColEditable("rvwStsCd",0);
	            	SetColEditable("rvwConts",0);

	            }
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
			
		case "Add": //코드이관 요청
			var url   = "<c:url value="/meta/stnd/popup/messagetsf_pop.do"/>";
			var param = "?popRqst=Y";
			var popup = OpenWindow(url+param,"messagetsfSearch","800","600","yes");
			
			break;
		case "AddWam": //이관 삭제요청
			var url   = "<c:url value="/meta/stnd/popup/messagetsfwam_pop.do"/>";
			var param = "?popRqst=Y";
			var popup = OpenWindow(url+param,"messagetsfwamSearch","800","600","yes");
			
			break;			
		case "Reset" :
			//폼내용 초기화.....
			$("#frmInput")[0].reset();
			//입력창을 이전 상태로 복원...
			
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
			
			var url = "<c:url value="/meta/stnd/delmsgtsfrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
			
			break;
			
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getmsgtsfrqstlist.do" />", param);

			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			//getRqstVrfLst(param); 임시주석
			
			
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
			
			var url = "<c:url value="/meta/stnd/approvemsgtsf.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
	   
		case "Down2Excel":  //엑셀내려받기
		
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
			
			break;
		case "LoadExcel":  //엑셀업로
		
// 			grid_sheet.LoadExcel({Mode:'HeaderMatch'});
			var url = "<c:url value="/meta/stnd/popup/message_xls.do" />";
			
			var xlspopup = OpenWindow(url ,"message_xls","800","600","yes");
		
			break;
         case "allReject":
	        	doAllReject(grid_sheet, "2",$("#mstFrm #rvwConts").val());
	       	break;
	}	   
}

//상세정보호출
function loadDetail(param) {
	//alert( JSON.stringify(param));
	$('div#detailInfo').load('<c:url value="/meta/stnd/ajaxgrid/messagetsf_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
			    alert( msg + xhr.status + " " + xhr.statusText );
		  } else {
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
			var url = '<c:url value="/meta/stnd/messagetsf_rqst.do" />';
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
	var tmphtml = '<s:message code="STRD.TERMS" /> : ' + param.msgCd + ' [' + param.msgConts +']'; //표준용어
	$('#stnditem_sel_tit').html(tmphtml);
	
	loadDetail(param);


	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	//검증결과 조회
	getRqstVrfLst(param1);
	
	grid_change.RemoveAll();
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
	<div class="menu_title"><s:message code="MSG.REG" /></div> <!-- 메시지등록 -->
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
	<div class="selected_title" id="msg_sel_title"></div>
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><s:message code="CD.DTL" /></a></li> <!-- 코드 상세 -->
		<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
		<li id="tabs-rqstvrf"><a href="#tabs-2"><s:message code="VRFC.RSLT" /></a></li> <!-- 검증결과 -->
		</c:if>
		<c:if test="${waqMstr.rqstStepCd!= 'A'}">
		<li id="tabs-rqstchg"><a href="#tabs-3"><s:message code="CHG.ITEM" /></a></li> <!-- 변경항목 -->
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
	<c:if test="${waqMstr.rqstStepCd!='A' }">
	<div id="tabs-3">
		<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
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