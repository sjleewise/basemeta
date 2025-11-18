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
<title><s:message code="PHYC.MDEL.RLT.REG.DTL.INFO" /></title> <!-- 물리모델관계등록상세정보 -->

<script type="text/javascript">

$(document).ready(function(){
	
	
	//======================================================
    // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
    //======================================================
    //imgConvert($('div.tab_navi a img'));
	
	$("#mstFrm #bizDtlCd").val('PRELM');
	
	// 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		
		showMsgBox("PRC", "<s:message code="REQ.SUBMIT.INIT" />");
		var cntrqst = countGridValue(rel_sheet, "vrfCd", "1");
		
		var message = "";
		if (cntrqst > 0) message += "[<s:message code='DDL.TBL' />:"+cntrqst+"<s:message code='CNT' />]"; //DDL 테이블, 건
		message +="<br>"+"<s:message code="CNF.RQST" />";
		
		//등록가능한지 확인한다.vrfCd = 1
//			var regchk = rel_sheet.FindText("vrfCd", "등록가능");
		//if(regchk) {alert (regchk);} 
		if(cntrqst > 0) {
			showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Submit');
		} else {
			showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
			return false;
		}
		
	});	
	
	//전체승인 버튼 이벤트 처리
	$("#btnAllApprove").click(function(){
		showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
//			doAllApprove(rel_sheet, "1");
		//마스터에 전체승인을 셋팅한다.  
		$("#mstFrm #rvwStsCd").val("1");
		chgallapprove (rel_sheet);
		$("#btnReqApprove").click();
	});
	
	//전체반려 버튼 이벤트 처리
	$("#btnAllReject").click(function(){ 
				
		rejectMsgBox("ALLREJECT", "<s:message code="REQ.APPROVE.REJECT" />", reqallreject);
		//doAllReject(itemgrid, "2", mstrvwCont);
		//doAllApprove(rel_sheet, "2");
	});
	
	//검토처리 Event Bind
	$("#btnReqApprove").click(function(){
		showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
		
		//alert("결재처리")
		//그리드 변경대상 체크한다.
		var cntappr = rel_sheet.RowCount();
		if (cntappr <= 0) {
			showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
			return false;
		}
		
		// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (rel_sheet, 검토상태 savename)
		if (chkRvwStsCd(rel_sheet, "rvwStsCd") > -1) {
			//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
//				showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
			approveMsgBox("APPROVE", "<s:message code="ERR.APPROVE.CNF" />");
			return false;
		};
				
		//반려 선택시 반려사유를 입력하도록 한다.
		var tmprow = chkRvwCont(rel_sheet, "rvwStsCd", "rvwConts");
		if (tmprow > 0 ) {
			showMsgBox("INF", "<s:message code="ERR.REJECT" />");
			rel_sheet.SetSelectRow(tmprow);
						
			//선택한 상세정보를 가져온다...
//				var param =  rel_sheet.GetRowJson(tmprow);
		
			//선택한 그리드의 row 내용을 보여준다.....
//				var tmphtml = '테이블 : ' + param.pdmTblLnm + ' [' + param.pdmTblPnm +']';
//				$('#tbl_sle_title').html(tmphtml);
			
			//var param = rel_sheet.GetRowJson(row);
//				var param1 = $("#mstFrm").serialize();
//				param1 += "&rqstSno=" + param.rqstSno;
			
			//param = 
//				loadDetail(param1);
			
			//검증결과 조회
// 			getRqstVrfLst(param1);
// 			$("#frmInput #rvwConts").focus();
			return false;
		}
		
		//결재처리 총건수, 승인건수, 반려건수를 메세지로 만든다.
		//승인/반려 건수
		var message = ""; 
		var cntAppr 	= countGridValue(rel_sheet, "rvwStsCd", "1");
		var cntReject  	= countGridValue(rel_sheet, "rvwStsCd", "2");
		
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
	
	 // 변경대상 추가 Event Bind
    $("#btnChangAdd").click(function(){ 
    	
    	doAction("AddWam");
    	
    }).show();
		
	 
    // 삭제 Event Bind
    $("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (rel_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete'); 
    	}
    }).show(); 

    
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

    // 엑셀업로드 Event Bind
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
    
    //신규요청서
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/meta/pdmrel/pdmrelation_rqst.do" />';
    } );
    
    $("#tabs-rqstchg").hide();

});

$(window).on('load',function() {
	//그리드 초기화 
	initRelGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
		
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, rel_sheet);
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
	doAction("Search");
 	
	loadDetailRel();
});


function initRelGrid()
{
    
    with(rel_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.ASIS.PDM.REL.RQST'/>";
		//No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|관계ID|관계물리명|관계논리명|부모엔터티주제영역ID|부모엔터티주제영역명|부모엔터티ID|부모엔터티명|부모엔터티속성ID|부모엔터티속성명|자식엔터티주제영역ID|자식엔터티주제영역명|자식엔터티ID|자식엔터티명|자식엔터티속성ID|자식엔터티속성명|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호|이전관계명|이전부모엔터티명|이전부모엔터티속성명|이전자식엔터티명|이전자식엔터티속성명
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,   SaveName:"rvwStsCd",  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,   SaveName:"rvwConts",  Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,   SaveName:"rqstDcd",	  Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:60,   SaveName:"regTypCd",  Align:"Center", Edit:0},						
					{Type:"Combo",  Width:80,   SaveName:"vrfCd",	  Align:"Center", Edit:0},
					
					{Type:"Text",    Width:100, SaveName:"pdmRelId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"pdmRelPnm"   , Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",    Width:100, SaveName:"pdmRelLnm"   , Align:"Left",   Edit:0},
                    {Type:"Combo",   Width:100, SaveName:"relTypCd"    , Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Combo",   Width:100, SaveName:"crdTypCd"    , Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Combo",   Width:100, SaveName:"paOptTypCd"  , Align:"Left",   Edit:0, KeyField:1},
                    {Type:"Text",    Width:100, SaveName:"paSubjId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"paFullPath"  , Align:"Left",   Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",    Width:100, SaveName:"paTblId"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"paTblPnm"    , Align:"Left",   Edit:0, Hidden:0, KeyField:1},
                    
                    {Type:"Text",    Width:100, SaveName:"chSubjId"    , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"chFullPath"  , Align:"Left",   Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",    Width:100, SaveName:"chTblId"     , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"chTblPnm"    , Align:"Left",   Edit:0, Hidden:0},
                    
                    {Type:"Text",   Width:120,  SaveName:"objDescn"    , Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm"     , Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId"  , Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm"  , Align:"Center", Edit:0},
					{Type:"Text",   Width:60,   SaveName:"rqstNo"      , Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno"     , Align:"Center", Edit:0, Hidden:1},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno"  , Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Text",    Width:100, SaveName:"bfPdmRelPnm" , Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:100, SaveName:"bfPaTblPnm"  , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"bfPaColPnm"  , Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:100, SaveName:"bfChTblPnm"  , Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"bfChColPnm"  , Align:"Left",   Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});

		SetColProperty("relTypCd", 	${codeMap.relTypCdibs});
		SetColProperty("crdTypCd", 	${codeMap.crdTypCdibs});
		SetColProperty("paOptTypCd", 	${codeMap.paOptTypCdibs});
		
		
        
        InitComboNoMatchText(1, "");
        

        
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(rel_sheet);    
    //===========================
   
}


//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "New":        //신규 추가...
        	//테이블의 현재 선택행을 확인 후 1행 이상이면 신규 폼 호출한다.
			var crow = rel_sheet.GetSelectRow();
			if(crow < 1) {
				showMsgBox("ERR", "<s:message code="ERR.TBLSEL" />");
				return;
			}
        	
        	//컬럼 입력폼을 불러온다.
        	loadDetailRel();
        
            break;
            
        case "AddWam": //기존 테이블 추가
        
			var url   = "<c:url value="/meta/pdmrel/popup/pdmrel_pop.do"/>";
			var param = "popRqst=Y";
			
			openLayerPop(url, 800, 700, param);
			break;
            
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	var param = $('#mstFrm').serialize();
        	rel_sheet.DoSearch("<c:url value="/meta/pdmrel/getPdmRelMstRqstList.do" />", param);

        	break;
        	
      	
            	
        	                        
        case "Delete" :
        	        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(rel_sheet);
        	
        	//체크된 행을 Json 리스트로 생성...
        	ibsSaveJson = rel_sheet.GetSaveJson(0, "ibsCheck");
        	if(ibsSaveJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
        	        	
        	var url = "<c:url value="/meta/pdmrel/delPdmRelMstRqstList.do"/>";

        	var param = $("form#mstFrm").serialize();
        	
            IBSpostJson2(url, ibsSaveJson, param, ibscallback);
            
        	break;
        	
      	case "SaveRelRow":        
  	    	
   	    	var saveJson = getform2IBSjson($("#frmRelInput"));
   	
   	    	if(saveJson.data.length == 0) return;
           	
   			var url = "<c:url value="/meta/pdmrel/regpdmrelrqstlist.do"/>";
   			var param = $('form[name=mstFrm]').serialize();
   			
   			IBSpostJson2(url, saveJson, param, ibscallback);
   			
   	        break;
                       
        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = rel_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = rel_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = rel_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/pdmrel/regpdmrelrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallbackCol);
        	break;
        	
        case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param); 
			
			break;
    	   
   		case "Approve" : //결재처리
   			
   			var saveJson = rel_sheet.GetSaveJson(0);
   		
   			
   			//2. 필수입력 누락인 경우
   			//alert(saveJson.Code);
   			if (saveJson.Code == "IBS000") return;
   			//if(saveJson.data.length == 0) return;
   			
   			var url = "<c:url value="/meta/pdmrel/approvePdmRel.do"/>";
   			var param = $('form[name=mstFrm]').serialize();
   			IBSpostJson2(url, saveJson, param, ibscallback);
   		
   	   		break;    
         	
        
	   
        
       	case "NewCol":      
           	
           	//테이블의 현재 선택행을 확인 후 1행 이상이면 신규 폼 호출한다.
   			var crow = rel_sheet.GetSelectRow();
           	
   			if(crow < 1) {
   				showMsgBox("ERR", "<s:message code="ERR.TBLSEL" />");
   				return;
   			}
   			
   			
   			var rqstSno    = rel_sheet.GetCellValue(crow, "rqstSno");        	
   			var rqstDtlSno = rel_sheet.GetCellValue(crow, "rqstDtlSno");
   			var pdmRelId   = rel_sheet.GetCellValue(crow, "pdmRelId");		
   			var pdmRelPnm  = rel_sheet.GetCellValue(crow, "pdmRelPnm");
   			var pdmRelLnm  = rel_sheet.GetCellValue(crow, "pdmRelLnm");
   			var paSubjId   = rel_sheet.GetCellValue(crow, "paSubjId");
   			var paFullPath  = rel_sheet.GetCellValue(crow, "paFullPath");
   			var paTblId   = rel_sheet.GetCellValue(crow, "paTblId");
   			var paTblPnm  = rel_sheet.GetCellValue(crow, "paTblPnm");
   			
   			var chSubjId   = rel_sheet.GetCellValue(crow, "chSubjId");
   			var chFullPath  = rel_sheet.GetCellValue(crow, "chFullPath");
   			var chTblId   = rel_sheet.GetCellValue(crow, "chTblId");
   			var chTblPnm  = rel_sheet.GetCellValue(crow, "chTblPnm");
   			
   			
   			var row = col_sheet.DataInsert();
   			
   	        col_sheet.SetCellValue(row, "rqstSno", rqstSno);        	
   	        col_sheet.SetCellValue(row, "rqstDtlSno", rqstDtlSno);
   	        col_sheet.SetCellValue(row, "pdmRelId", pdmRelId);	        
   	        col_sheet.SetCellValue(row, "pdmRelPnm", pdmRelPnm);
   	        col_sheet.SetCellValue(row, "pdmRelLnm", pdmRelLnm);
   	        col_sheet.SetCellValue(row, "paSubjId", paSubjId);
   	        col_sheet.SetCellValue(row, "paFullPath", paFullPath);
   	        col_sheet.SetCellValue(row, "paTblId", paTblId);
   	        col_sheet.SetCellValue(row, "paTblPnm", paTblPnm);	        
   	        col_sheet.SetCellValue(row, "chSubjId", chSubjId); 
   	        col_sheet.SetCellValue(row, "chFullPath", chFullPath);
   	        col_sheet.SetCellValue(row, "chTblId", chTblId);
   	        col_sheet.SetCellValue(row, "chTblPnm", chTblPnm);
   						
            break;
        
		case "SearchCol":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	var row = rel_sheet.GetSelectRow();
		
			var rqstSno    = rel_sheet.GetCellValue(row, "rqstSno");
			var rqstDtlSno = rel_sheet.GetCellValue(row, "rqstDtlSno");
        	
        	var param = $('#mstFrm').serialize();
				
			param += "&rqstSno="    + rqstSno;
			param += "&rqstDtlSno=" + rqstDtlSno;
			
        	col_sheet.DoSearch("<c:url value="/meta/pdmrel/getPdmRelColRqstList.do" />", param);

        	break;    
       	case "SaveCol":
       		
       		//TODO 공통으로 처리...
        	var rows = col_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
         		//alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	var ibsColJson = col_sheet.GetSaveJson(0);	//DoSave와 동일...
         	//ibsSaveJson = col_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsColJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/pdmrel/regPdmRelColRqstList.do"/>";
            
            var param = $("form#mstFrm").serialize();
            
        	IBSpostJson2(url, ibsColJson, param, ibscallbackCol);
        	break;
        
        case "DeleteCol":
          		
			delCheckIBS(col_sheet);
        	
        	//체크된 행을 Json 리스트로 생성...
        	ibsSaveJson = col_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(ibsSaveJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
        	        	
        	var url = "<c:url value="/meta/pdmrel/delPdmRelColRqstList.do"/>";

        	var param = $("form#mstFrm").serialize();
        	
            IBSpostJson(url, param, ibscallbackCol);
           	break;	
        	
        case "Down2Excel":  //엑셀내려받기
            rel_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":  //엑셀업로드
        	
			var url = "<c:url value="/meta/pdmrel/popup/pdmrel_xls.do" />";
			
			openLayerPop(url, 800, 600);
			
            break;
    }       
}

//초기화 후 전제반려를 등록요청한다.
function reqallreject() {
	showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
	
	chgallapprove(rel_sheet); 
		
	$("#btnReqApprove").click();
	
}


//상세정보호출
function loadDetailRel(param) {
	
	$('div#detailInfoRel').load('<c:url value="/meta/pdmrel/ajaxgrid/pdmrelation_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
	        var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
	        alert( msg + xhr.status + " " + xhr.statusText );
	      }
		  
		  //신규일 경우 선택한 테이블의 rqstsno를 업데이트 한다.
		  if ($("#frmRelInput #ibsStatus").val() == "I") {
			  var crow = rel_sheet.GetSelectRow();
			  if(crow > 0) {
				  $("#frmRelInput #rqstSno").val(rel_sheet.GetCellValue(crow, "rqstSno"));
				  $("#frmRelInput #chFullPath").val(rel_sheet.GetCellValue(rel_sheet.GetSelectRow(), "fullPath"));
				  $("#frmRelInput #chTblPnm").val(rel_sheet.GetCellValue(rel_sheet.GetSelectRow(), "pdmTblPnm"));
			  }
		  }
		  
		  
	});
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
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {

	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}

				doAction("Search");
				loadDetailRel(); 				
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
				
				loadDetailRel();
				
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/pdmrel/pdmrelation_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

function rel_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	//변경항목 조회
	//선택한 상세정보를 가져온다...
	var param =  rel_sheet.GetRowJson(row);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno + "&rqstDtlSno=" + param.rqstDtlSno + "&subInfo=REL";
	$("#tabs #relinfo").click();
	grid_changecol2.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1, 'REL');
	}
	
}

function rel_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
  	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(rel_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param = rel_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="RLT.NM" /> : ' + param.pdmRelPnm + ' [' + param.pdmRelLnm +']'; //관계명
	$('#rel_sel_title').html(tmphtml);
	
	//선택한 시트 열번호를 저장한다...
	$("#sheetRow #sheetRow").val(row);
	
	
	//값이 변경된게 있을경우 ibs2wformmapping이 실행되도록 한다.
	if(rel_sheet.GetCellValue(row, "ibsStatus") == 'R') {
		
		loadDetailRel(param);
	} else {
		
	 	ibs2formmapping(row, $("#frmRelInput"), rel_sheet);
	}
	
	var param1  = "bizDtlCd=PRELM";
	    param1 += "&rqstNo="     + param.rqstNo;
	    param1 += "&rqstSno="    + param.rqstSno;
	    param1 += "&rqstDtlSno=" + param.rqstDtlSno;
	    
	
	doAction("SearchCol");    
	
	getRqstVrfLst(param1);
    
    
}

//주제영역 팝업 리턴값 처리...
function returnSubjPopRel(ret){
	var retjson = jQuery.parseJSON(ret);
	if($("#frmRelInput #subjFlag").val() == "PA") {
		$("#frmRelInput #paFullPath").val(retjson.fullPath);
		
	} else if ($("#frmRelInput #subjFlag").val() == "CH") {
		$("#frmRelInput #chFullPath").val(retjson.fullPath);
		
	} 
}
//테이블 팝업 리턴값 처리...
function returnPdmtblPop(ret){
	
	var retjson = jQuery.parseJSON(ret);
	
	
	if($("#frmRelInput #subjFlag").val() == "PA") {
		
		$("#frmRelInput #paFullPath").val(retjson.fullPath);
		$("#frmRelInput #paTblPnm").val(retjson.pdmTblPnm);
		
	} else if ($("#frmRelInput #subjFlag").val() == "CH") {
		
		$("#frmRelInput #chFullPath").val(retjson.fullPath);
		$("#frmRelInput #chTblPnm").val(retjson.pdmTblPnm); 		
	} 
}

//컬럼명 팝업 리턴값 처리...
function returnPdmColPop(ret){
	var retjson = jQuery.parseJSON(ret);
	
	var crow = col_sheet.GetSelectRow();
			
	//alert($("#frmRelInput #subjFlag").val());
		
	if($("#frmRelInput #subjFlag").val() == "PA") {
		$("#frmRelInput #paFullPath").val(retjson.subjLnm);
		$("#frmRelInput #paTblPnm").val(retjson.pdmTblPnm);
		$("#frmRelInput #paColPnm").val(retjson.pdmColPnm);
		
	} else if ($("#frmRelInput #subjFlag").val() == "CH") {
		
		$("#frmRelInput #chFullPath").val(retjson.subjLnm);
		$("#frmRelInput #chTblPnm").val(retjson.pdmTblPnm);
		$("#frmRelInput #chColPnm").val(retjson.pdmColPnm);
		
	} else if ($("#frmRelInput #subjFlag").val() == "CPA") {
			
		col_sheet.SetCellValue(crow, "paColPnm", retjson.pdmColPnm);
		
	} else if ($("#frmRelInput #subjFlag").val() == "CCH") {
		
		col_sheet.SetCellValue(crow, "chColPnm", retjson.pdmColPnm);
	}
}


</script>
</head>
<body>
<div style="clear:both; height:5px;"><span></span></div>
	 <!-- 버튼영역  -->
     <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" /> 
     <!-- 버튼영역  -->
	
	<div style="clear:both; height:5px;"><span></span></div>
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("rel_sheet", "100%", "150px");</script>            
	</div>
	<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="rel_sel_title"><s:message code="MSG.RLT.CHC" /></div> <!-- 관계를 선택하세요. -->
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	
	<div id="tabs" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="RLT.DFNT.P" /></a></li><!-- 관계 정의서 -->
	    <li><a href="#tabs-2"><s:message code="RLT.CLMN" /></a></li> <!-- 관계컬럼 -->
	    
	    <c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
	    <li id="tabs-rqstvrf"><a href="#tabs-3"><s:message code="VRFC.RSLT" /></a></li> <!-- 검증결과 -->
	    <li id="tabs-rqstchg"><a href="#tabs-4"><s:message code="TBL.CHG.ITEM" /></a></li> <!-- 테이블변경항목 -->	    	    
	    </c:if>
	  </ul>
	  
	  <div id="tabs-1">
		<!-- 상세정보 ajax 로드시 이용 -->
		<div id="detailInfoRel"></div>
	  </div>
	  <div id="tabs-2"> 		
		<%@include file="pdmrelation_col_rqst.jsp" %>
	  </div>
	  		
	  <c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
	  <div id="tabs-3">
		<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	  </div>
	  <div id="tabs-4">
		<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
	  </div>
	  
	  </c:if>
	</div>


	<form id="sheetRow" name="sheetRow">
	<input type="hidden" id="sheetRow" name="sheetRow" style="disabled:disabled;" />
	</form>
	
	
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>
	
</body>
</html>
