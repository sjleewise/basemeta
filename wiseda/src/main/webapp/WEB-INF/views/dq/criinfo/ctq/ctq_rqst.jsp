<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="IMCE.INFO.ITEM.REG.DEMD" /></title><!-- 중요정보항목 등록요청 -->
<script type="text/javascript">

var interval = "";

$(document).ready(function() {
	
	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('CTQ');

	
	//탭 초기화....
// 	$("#tabs").tabs({ heightStyle: "auto" });
	$("#tabs").tabs();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
	// 재조회 Event Bind
	$("#btnSearch").click(function(){ doAction("VrfSearch");  });
						
	// 추가 Event Bind
	$("#btnNew").click(function(){ doAction("New");  });
	
	//저장 기존 검증
	$("#btnSave").click(function(){
		//저장 대상의 데이터를 Json 객체로 반환한다.
		ibsSaveJson = grid_sheet.GetSaveJson(0);
		
		if(ibsSaveJson.data.length == 0){
			var message = "<s:message code="ERR.CHKSAVELIST" />";
			showMsgBox("ERR", message); 
			return;
		}
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'VrfSaveList'); 
	}).hide();
	
	//삭제 Event Bind
	$("#btnDelete").click(function(){ doAction("VrfDelete");  });
	
	//등록
	$("#btnRegRqst").click(function(){ 
		
		//등록가능한지 확인한다.vrfCd = 1
		var regchk = grid_sheet.FindText("vrfCd", "<s:message code='REG.POSB' />");/*등록가능*/

		
		if(regchk > 0) {
			if($("#mstFrm #aprLvl").val() > 0 ){
				showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'submitCtqLst');
			}else{
				showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Approve');
			}
		} else {
			showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
			return false;
		}
	});
	
	//변경대상 추가
	$("#btnChangAdd").click(function(event){
		event.preventDefault();	//브라우저 기본 이벤트 제거...ssss
		//팝업 flag 값 설정
		doAction("ctqPopup", "CHG");
	});
				  
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
	// 엑셀업로 Event Bind
    $("#btnExcelLoad").click( function(){
    	doAction("LoadExcel"); 
    	//팝업 사용 엑셀업로드
    	//검증결과 상세조회 때문에 미사용
    	//var url = '<c:url value="/dq/criinfo/ctq/popup/ctq_xls.do"/>';
		//openLayerPop(url, 800, 550);
    } );
	
 	 //화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/criinfo/ctq/ctq_rqst.do" />';
    } );
	
	
	//상세정보 저장 버튼
    $("#btnGridSave").click( function(){
    	//폼 검증...
		if(!$("#frmInput").valid()) return false;
    	var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'VrfSaveRow'); 
    } );
    
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		doAction("Reset");
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
			var tmphtml = '<s:message code="IMCE.INFO.ITEM"/>' + ' : ' + param.ctqLnm; //중요정보항목
			$('#divTitle').html(tmphtml);
			
			//var param = grid_sheet.GetRowJson(row);
			var param1 = $("#mstFrm").serialize();
			param1 += "&rqstSno=" + param.rqstSno;
			
			//param = 
			loadDetail(param1);
			
			//검증결과 조회
			getRqstVrfLst(param1);
			$("#frmInput #rvwConts").focus();
			return false;
		}
		
		doAction("Approve");
		
	});
	
	
	//코드 초기화
	 setCodeSelect("RQST_DCD", "C", $("form[name=frmInput] #rqstDcd"));
	 setCodeSelect("REG_TYP_CD", "C", $("form[name=frmInput] #regTypCd"));
	 setCodeSelect("VRF_CD", "C", $("form[name=frmInput] #vrfCd"));
	 
});


//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	//if($("#mstFrm #aprLvl").val() > 0 ){
// 	if($("#mstFrm #rqstStepCd").val() != "A" ){
		setDispRqstMainButton(rqststep, grid_sheet);
// 	}
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
	
	loadDetail();

	doAction("VrfSearch");
	
});


function initGrid()
{
	
	with(grid_sheet){
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headers = [
					{Text:"<s:message code='DQ.HEADER.CTQ.RQST'/>", Align:"Center"}
				];
		//선택|No.|상태|검토상태|검토내용|요청구분|등록유형|검증결과|중요정보항목명|레벨|상위중요정보항목명|목표치|가중치|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호


		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
	                {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Seq",    Width:50,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
	                {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
	                {Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},
	                {Type:"Combo",  Width:100,  SaveName:"rqstDcd",	 Align:"Center", Edit:1, KeyField:1},						
					{Type:"Combo",  Width:100,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0,},						
					{Type:"Text",   Width:130,  SaveName:"ctqLnm",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:60,  SaveName:"ctqLvl",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:130,  SaveName:"uppCtqLnm",   	Align:"Left", Edit:1, KeyField:0}, 
					{Type:"Float",  Width:60,  	SaveName:"goal",		Align:"Left", 	 Edit:1, Format:"0.00"},
					{Type:"Float",  Width:60,  	SaveName:"weight",		Align:"Left", 	 Edit:1, Format:"0.00"},
					{Type:"Text",   Width:300,  SaveName:"objDescn",	Align:"Left", 	 Edit:1},
					{Type:"Text",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Left", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", Edit:0}
				];
		
		InitColumns(cols);
		
		SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
		SetColProperty("vrfCd", ${codeMap.vrfCdibs});
		SetColProperty("rvwStsCd", ${codeMap.rvwStsCdibs});
		
		InitComboNoMatchText(1, "");
		
		SetColHidden("rqstDtm",1);
		SetColHidden("rqstUserId",1);
// 		SetColHidden("rqstUserNm",1);
		SetColHidden("rqstNo",1);
		SetColHidden("rqstSno",1);
	  
		FitColWidth();  
		
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_sheet);	
	//===========================
	
	//grid_sheet.SetColHidden(Col, Hidden)

   
}



function doAction(sAction, param)
{
		
	switch(sAction)
	{
		case "New": //추가
			loadDetail();
			
			break;

		case "Reset" :
			$("form[name=frmInput]")[0].reset();
			break;
			
		case "SaveRow" :
			// 공통으로 처리...
			
			ibsSaveJson = getform2IBSjson($("#frmInput"));
// 			ibsSaveJson = $("#frmInput").serializeArray();
// 			ibsSaveJson = $("#frmInput").serializeObject();

			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/criinfo/ctq/regVrfCtqList.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
			
		case "Approve" : //결재처리
			
			//프로파일 검토상태코드 승인으로 변경
			//결재프로세스 태울경우 제거
			if($("form[name=mstFrm] #aprLvl").val() == 0){
				doAllApprove(grid_sheet, "1");
			}
		
			var saveJson = grid_sheet.GetSaveJson(0);
			
			//2. 필수입력 누락인 경우
// 			alert(saveJson.Code);
			if (saveJson.Code == "IBS010") return;
			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/criinfo/ctq/approveCtq.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
			
		case "ctqPopup":
			var url = '<c:url value="/dq/criinfo/ctq/popup/ctq_pop.do"/>';
// 			var param = $("form#frmInput").serialize(); 
			if (param == 'ADD'){
				loadDetail();
			}
			var param = "sflag="+param; 
// 			var popwin = OpenModal(url+"?"+param, "ctqPop",  800, 600, "no");
// 			popwin.focus();
			openLayerPop(url, 800, 600, param);
			break;
			
		case "VrfSearch":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/dq/criinfo/ctq/getVrfCtqListIBS.do" />", param);
						
			break;
			
		case "VrfSaveList" :
			//저장 대상의 데이터를 Json 객체로 반환한다.
			ibsSaveJson = grid_sheet.GetSaveJson(0);
			
			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/criinfo/ctq/regVrfCtqList.do"/>";
			var param = $('form[name=frmInput]').serialize();
	        IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
			
		case "VrfSaveRow" :
			// 공통으로 처리...
			if($("#gridRow").val() == "") {
				var message = "<s:message code="ERR.CHKSAVE" />";
				showMsgBox("ERR", message); 
				return;
			}
			//선택 체크
			grid_sheet.SetCellValue($("#gridRow").val(), "ibsCheck", "1"); 
			//폼 내용을 IBS에 매핑하여 저장
			form2ibsmapping($("#gridRow").val(), $("form#frmInput"), grid_sheet);
			
			doAction("VrfSaveList");
			
			break;
			
		case "VrfDelete" :
			//테크박스가 입력상태인 경우 삭제...
			if(!grid_sheet.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			
			//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
			ibsSaveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
			
			var url = "<c:url value="/dq/criinfo/ctq/delVrfCtqList.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			//폼 초기화
        	$('#frmInput')[0].reset();
			
			break;
		
		//등록가능 일 경우 WAM 영역 저장
		//차후 결재로직 개발 완료 후 요청 구현
		case "submitCtqLst":  
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
	        
			break;
	  
	    case "LoadExcel":  //엑셀업로드
// 			grid_sheet.LoadExcel();
			
			var url = "<c:url value="/dq/criinfo/ctq/popup/ctq_xls.do" />";
			
// 			var xlspopup = OpenWindow(url ,"ctqxls","800","600","yes");
			openLayerPop(url, 800, 600);
	        break;
	        
		case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'중요정보항목등록요청'});
			break;
			
	}	   
}
 

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
//	alert(res.action);
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :

			//if(!isBlankStr(res.resultVO.rqstNo)) {
//	    		alert(res.resultVO.rqstNo);
//	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("VrfSearch");
//			}
		
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
	    		
	    		
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("VrfSearch");    		
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/dq/criinfo/ctq/ctq_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
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
	//조회 조건 셋팅
	ibsToFormFiled(row);
	
// 	//검증결과 상세조회
// 	if($("#rqstNo").val() != ""){
// 		getRqstVrfLst();
// 	}
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	//검증결과 조회
	getRqstVrfLst(param1);
	
	//변경항목 조회
	grid_change.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1);
	}
}

//검증결과 상세조회
function getRqstVrfLst() {
	var param = "rqstNo="+$("#rqstNo").val();
         param += "&rqstSno="+$("#rqstSno").val();
         param += "&bizDtlCd="+$("#bizDcd").val();
    
	grid_vrf.DoSearch("<c:url value="/commons/rqstmst/getRqstVrfLst.do" />", param);
}

function ibsToFormFiled(row){
	if(row < 1) return;
	
	ibs2formmapping(row, $("form#frmInput"), grid_sheet);

	$("#divTitle").text("<s:message code='IMCE.INFO.ITEM'/>" + " : "  + grid_sheet.GetCellValue(row,"ctqLnm")); //중요정보항목
	$("#gridRow").val(row);
	
	if(row > 1){
		//grid 요청일련번호
		var ibsRqstSno = grid_sheet.GetCellValue(row-1,"rqstSno");
		$("#rqstSno").val(parseInt(ibsRqstSno) +1 );
		
	}else{
		$("#rqstSno").val(row);
	}
	
	$("#ctqLnm").focus().click();
}


function grid_sheet_OnSaveEnd(code, message) {
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
}


//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/criinfo/ctq/ajaxgrid/ctq_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "<s:message code='MSG.DTL.INFO2' />"; //상세정보 호출중 오류발생...
			    alert( msg + xhr.status + " " + xhr.statusText );
			  }
	});
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="IMCE.INFO.ITEM.REG.DEMD" /></div><!-- 중요정보항목 등록요청 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />
<!-- 검색조건 입력폼 -->
<div style="clear:both; height:5px;"><span></span></div>
		
<!-- 그리드 입력 입력 -->
<div id="grid_01" class="grid_01" style="height:49%;">
	<script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>			
</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area">
	<div class="selected_title" id="divTitle"></div>
</div>

<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<div id="tabs">
<!-- 비 순차적 목록테그 -->
	<ul>
		<li><a href="#tabs-1"><s:message code="DTL.INFO" /></a></li><!--상세정보-->
		<li id="tabs-rqstvrf"><a href="#tabs-2"><s:message code="VRFC.RSLT" /></a></li><!--검증결과-->
		<li id="tabs-rqstchg"><a href="#tabs-3"><s:message code="CHG.ITEM" /></a></li><!--변경항목-->
	</ul>
	<div id="tabs-1">
		<div id="detailInfo"></div>
	<!-- 입력폼 끝 -->
	</div>
	<!-- 검증결과 조회 시작 -->
	<div id="tabs-2">
			<div id="vrfInfo"><%@include file="../../../commons/rqstmst/rqstvrf_lst.jsp" %></div>
	  </div>
	<div id="tabs-3">
		<%@include file="../../../commons/rqstmst/rqstChange_lst.jsp" %>
	  </div>
	<!-- 검증결과 조회 끝 -->
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />

<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>
</body>
</html>