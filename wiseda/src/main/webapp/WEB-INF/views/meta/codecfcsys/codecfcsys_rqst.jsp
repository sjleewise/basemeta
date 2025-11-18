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
<title><s:message code="CD.CLS.SYST.REG" /></title> <!-- 코드분류체계 등록 -->

<script type="text/javascript">

$(document).ready(function() {
	
		//업무구분상세 초기화...
		//코드분류체게
		$("#mstFrm #bizDtlCd").val('CCS');
		
		// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
		
		//탭 초기화....
		//$( "#tabs" ).tabs();
			
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		//검색조건 display none
		$("div#search_div").hide();
	
		// 등록요청 Event Bind
		$("#btnRegRqst").click(function(){
			//등록가능한지 확인한다.vrfCd = 1
			var regchk = grid_sheet.FindText("vrfCd", "<s:message code="REG.POSB" />"); //등록가능
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
				var tmphtml = '<s:message code="CD.CLS.SYST" /> : ' + param.pdmTblLnm + ' [' + param.pdmTblPnm +']'; //코드분류체계
				$('#codecfc_sel_title').html(tmphtml);
				
				var param1 = $("#mstFrm").serialize();
				param1 += "&rqstSno=" + param.rqstSno;
				
				//param = 
				loadDetail(param1);
				
				return false;
			}
			
			doAction("Approve");
			
		});		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });
        
        // 변경대상 추가 Event Bind
        $("#btnChangAdd").click(function(){ 
        	doAction("AddWam");
        }); 
        
        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
    		//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL.TBL" />", 'Delete');
        	}
        }); 


        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).hide();
        
      //화면리로드
        $("#btnBlank").click( function(){
    		location.href = '<c:url value="/meta/codecfcsys/codecfcsys_rqst.do" />';
        } );
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();

	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	checkApproveYn($("#mstFrm"));
	
	loadDetail();

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
        
        var headtext  = "<s:message code='META.HEADER.CODECFCSYS.RQST1'/>";
        /* No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과 */
        	headtext += "<s:message code='META.HEADER.CODECFCSYS.RQST2'/>";
        	/* |코드분류체계유형|코드분류체계명|코드분류체계형식 */
        	headtext += "<s:message code='META.HEADER.CODECFCSYS.RQST3'/>";
        	/* |담당자ID|담당자명|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호 */
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:60,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:60,  SaveName:"vrfCd",	Align:"Center", Edit:0},
                    
                    {Type:"Combo",   Width:150,   SaveName:"codeCfcSysCd",	 	Align:"Left", Edit:0, KeyField:1},
                    {Type:"Text",   Width:150, SaveName:"codeCfcSysLnm",   	 Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",   Width:150, SaveName:"codeCfcSysFrm",     Align:"Left",   Edit:0, KeyField:1}, 

                    {Type:"Text",   Width:60,   SaveName:"crgUserId",	 Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"crgUserNm",	 Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Aligwn:"Left",   Edit:0},
                    {Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		
		SetColProperty("codeCfcSysCd", 	${codeMap.codeCfcSysCdibs});
        
        InitComboNoMatchText(1, "");
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}




//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "New":        //신규 추가...
        	loadDetail();
			
            break;
        
        case "AddWam": //기존 코드분류체계 추가
			var url   = "<c:url value="/meta/codecfcsys/popup/codecfcsys_pop.do"/>";
			var param = "popRqst=Y";
			openLayerPop(url, 800, 600, param);
		break;

		case "Reset" :
			//폼내용 초기화.....
			$("#frmInput")[0].reset();        	
            break;
            
        case "Delete" :
	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
			var url = "<c:url value="/meta/codecfcsys/delCodeCfcSysRqstList.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
		case "SaveRow" :
			// 공통으로 처리...
			
			var saveJson = getform2IBSjson($("#frmInput"));
			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/codecfcsys/regCodeCfcSysRqst.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
			
			break;        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = grid_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/model/regpdmtblrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallback);
        	break;
        
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/codecfcsys/getcodecfcsysrqstlist.do" />", param);
			doActionCodeCfc("Search");
			
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
			if (saveJson.Code == "IBS000") return;
			
			var url = "<c:url value="/meta/codecfcsys/approveCodeCfcSys.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":  //엑셀업로드
			var url = "<c:url value="/meta/model/popup/pdmtbl_xls.do" />";
			
// 			var xlspopup = OpenWindow(url ,"pdmtblxls","800","600","yes");
			openLayerPop(url, 800, 600);
			break;
			
        case "SaveCodeCfcItemRow":        //코드분류체계아이템 저장...
			
			var saveJson = getform2IBSjson($("#frmCodeCfcItemInput"));
// 			if (saveJson.Code == "IBS000") return;
			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/codecfcsys/regCodeCfcSysItemRqstList.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
        
            break;
            
            
        case "SaveCodeCfcItem": //코드분류체계아이템 리스트 저장
        
        	var saveJson = codecfc_sheet.GetSaveJson(1);
        
        	if (saveJson.Code == "IBS000") return;
        	if (saveJson.Code == "IBS010") return;
        	
			//체크로직     
// 			if(!checkCodeCfcItem()) return;
			
			var url = "<c:url value="/meta/codecfcsys/regCodeCfcSysItemRqstList.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
        	break;

        case "DeleteCol": //코드분류체계아이템 리스트 삭제...
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(codecfc_sheet);
	    	
	    	var DelJson = codecfc_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/codecfcsys/delCodeCfcSysItemRqstList.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
    }       
}
 

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/meta/codecfcsys/ajaxgrid/codecfcsys_dtl_rqst.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "<s:message code='MSG.DTL.INFO.EROR' />...";  //상세정보 호출중 오류발생
			    			    alert( msg + xhr.status + " " + xhr.statusText );
			  }
	});
}


function checkCodeCfcItem(){
	var dupRow = "";
	dupRow = codecfc_sheet.ColValueDup("codeCfcSysCd|codeCfcSysLnm|codeCfcSysItemSeq", 0);
	
	if(dupRow != -1){
		var message = "<s:message code="ERR.EMPTY"  arguments="<s:message code='MSG.SAME.CLS.SYST.ITEM.SEQ.EXIS' />" />"; //동일한 분류체계항목순번이 존재 합니다.
		showMsgBox("INF", message); 
		return false;
	}
	
	//동일 분류체계항목 (key : 코드분류체계명|항목)
	dupRow = codecfc_sheet.ColValueDup("codeCfcSysLnm|codeCfcSysItemCd", 0);
	if(dupRow != -1) {
		var message = "<s:message code="ERR.EMPTY"  arguments="<s:message code='MSG.SAME.CLS.SYST.ITEM.EXIS' />" />"; //동일한 분류체계항목이 존재 합니다.
		showMsgBox("INF", message); 
		return false;
	}
	
	//자릿수 포멧자릿수 동일여부 (for 문 돌려~)
	var totRow = codecfc_sheet.GetTotalRows();
	var vFrm = "";
	var vFrmLen = "";
	for(var i=1; i<=totRow; i++){
		
		vFrm = codecfc_sheet.GetCellValue(i, "codeCfcSysItemFrm");
		vFrmLen = codecfc_sheet.GetCellValue(i, "codeCfcSysItemLen");
		
		if(vFrmLen != ""){
			//글자 길이 체크
    		if(vFrm.replace(/ /g, '').length != vFrmLen){
    			var message = "<s:message code="ERR.EMPTY"  arguments="<s:message code='MSG.FRMT.LNGT.DIGIT.SAME.CLS.SYST.ITEM.EXIS' />" />"; //형식길이와 자릿수가 동일하지 않은 분류체계항목이 존재 합니다.
				showMsgBox("INF", message); 
    		  	return false;
    		}
		}
	}
	
	return true;
}
/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	
	//alert(res.action);
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
//	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}

	    		doAction("Search");
				loadDetail();
				doActionCodeCfc("Search");
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
	    		}
				doAction("Search");
				loadDetail();
				doActionCodeCfc("Search");
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/codecfcsys/codecfcsys_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
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
	var tmphtml = '<s:message code="CD.CLS.SYST" /> : ' + param.codeCfcSysLnm; //코드분류체계
	$('#codecfc_sel_title').html(tmphtml);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	var loadDetailParam = "rqstNo=" + param.rqstNo + "&rqstSno=" + param.rqstSno;
	loadDetail(loadDetailParam);
// 	codecfc_sheet.DoSearch("<c:url value="/meta/codecfcsys/getcodecfcsysitemrqstlist.do" />", param1);
	
	
	//검증결과 조회
	getRqstVrfLst(param1);
	
	//변경항목 조회
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
	}else{
		
	}
	
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="CD.CLS.SYST.REG" /></div> <!-- 코드분류체계 등록 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />

<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "150px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="codecfc_sel_title"><s:message code="MSG.CD.CLS.SYST.CHC" /></div> <!-- 코드분류체계를 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-2"><s:message code="CD.CLS.SYST.INFO" /></a></li> <!-- 코드분류체계 정보 --> 
	    <li><a href="#tabs-1"><s:message code="CD.CLS.SYST.LST" /></a></li> <!-- 코드분류체계 목록 -->
	    <li id="tabs-rqstvrf"><a href="#tabs-3"><s:message code="VRFC.RSLT" /></a></li> <!-- 검증결과 -->
	    <li id="tabs-rqstchg"><a href="#tabs-4"><s:message code="CHG.ITEM" /></a></li> <!-- 변경항목 -->
	  </ul>
	  		<!-- 코드분류체계목록 목록 탭 -->
	  <div id="tabs-2">
	  	<div id="detailInfo"></div>
	  </div>
	  <div id="tabs-1">
		<%@include file="codecfcsys_item_lst.jsp" %>
	  </div>
	  <div id="tabs-3">
		<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	  </div>
	  <div id="tabs-4">
		<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
	</div>
	</div>

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>

</body>
</html>