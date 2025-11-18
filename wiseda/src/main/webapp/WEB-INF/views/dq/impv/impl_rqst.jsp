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
<title><s:message code="IMPV.PLAN.REG.DEMD" /></title><!--개선계획 등록요청-->

<script type="text/javascript">
$(document).ready(function() {

	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('IMP');
	
	//탭 초기화
 	//$( "#tabs" ).tabs();
	$("#btnTreeNew").hide();
	$("#btnDelete").hide();
	
	
	
	//조회
	$("#btnSearch").click(function(){ doAction("Search"); });
	
	//저장버튼
	$("#btnSave").click(function(){ 
		//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_sheet.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');	
    	//doAction("Save"); 
		
	});
	
	//엑셀다운로드
	$("#btnExcelDown").click(function(){	doAction("Down2Excel");	});
	
	//적용 버튼 초기화...
	$('#btnfrmApplyCs').click(function(){
		if($('#selectRow').val() == "") {
			showMsgBox("ERR", "<s:message code='BZWR.RGR.SEL'/>"); /*업무규칙을 먼저 선택하세요.*/
				return;
			}
				
		doAction("Apply");
		
	});
	
	//적용 버튼 초기화...
	$('#btnfrmApplyIm').click(function(){
		if($('#selectRow').val() == "") {
			showMsgBox("ERR", "<s:message code='BZWR.RGR.SEL'/>"); /*업무규칙을 먼저 선택하세요.*/
				return;
			}
				
		doAction("Apply");
		
	});
	
	//적용 버튼 초기화...
	$('#btnfrmApplyAllCs').click(function(){
		if($('#selectRow').val() == "") {
			showMsgBox("ERR", "<s:message code='BZWR.RGR.SEL'/>"); /*업무규칙을 먼저 선택하세요.*/
				return;
			}
				
		doAction("ApplyAll");
		
	});
	
	//적용 버튼 초기화...
	$('#btnfrmApplyAllIm').click(function(){
		if($('#selectRow').val() == "") {
			showMsgBox("ERR", "<s:message code='BZWR.RGR.SEL'/>"); /*업무규칙을 먼저 선택하세요.*/
				return;
			}
				
		doAction("ApplyAll");
		
	});
	
	
	//원인분석상세 초기화 버튼 초기화...
	$('#btnfrmResetCs').click(function(){
		if($('#selectRow').val() != "") {
			grid_sheet_OnClick($('#selectRow').val());
		}
	});


	//개선계획상세 초기화 버튼 초기화...
	$('#btnfrmResetIm').click(function(){
		if($('#selectRow').val() != "") {
			grid_sheet_OnClick($('#selectRow').val());
		}
	});
	
	//등록
	$("#btnRegRqst").click(function(){ 
		
		//등록가능한지 확인한다.vrfCd = 1
		var regchk = grid_sheet.FindText("vrfCd", "<s:message code='REG.POSB' />");/*등록가능*/

		
		if(regchk > 0) {
			if($("#mstFrm #aprLvl").val() > 0 ){
				showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'submitImPlLst');
			}else{
				showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Approve');
			}
		} else {
			showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
			return false;
		}
		 
	});
	
	//화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/impv/impl_rqst.do" />';
    } );
	
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
			var tmphtml = '<s:message code="BZWR.RULE" /> : ' + param.anaJobNm; /* 업무규칙 */ 
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
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	setautoComplete($("#frmSearch #bizAreaLnm"), "BIZLNM");
	setautoComplete($("#frmSearch #dqiLnm"), "DQILNM");
	setautoComplete($("#frmSearch #ctqLnm"), "CTQLNM");
	setautoComplete($("#frmSearch #brNm"), "BRNM");
	
});

EnterkeyProcess("Search");

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.IMPL.RQST'/>" ,Align:"Center"}
                ];
                //No.|상태|검토상태|검토내용|요청구분|등록유형|검증결과|최근분석차수|분석차수|최근분석일자|업무영역명|업무규칙ID|업무규칙명|진단대상ID|진단대상명|테이블명|컬럼명|품질지표ID|품질지표명|중요정보항목ID|중요정보항목명|사용여부|분석건수|오류건수|"+"오류율(%)|DPMO|SIGMA|원인분석코드|원인분석코드명|원인분석시작일자|원인분석종료일자|원인분석내역|원인분석불가코드|원인분석불가코드명|개선계획코드|걔선계획코드명|개선계획시작일자|개선계획종료일자|개선계획내역|분석구분코드|요청번호|요청일련번호

        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    	Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:0},
                    {Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},	
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:0, Hidden:0, KeyField:1},						
					{Type:"Combo",  Width:100,  SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:0},						
					{Type:"Combo",  Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:50,   SaveName:"anaDgrDisp",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:50,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,  SaveName:"anaStrDtm",    	Align:"Left", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   	Width:80,  SaveName:"bizAreaLnm",    	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:80,  SaveName:"anaJobId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:80,  SaveName:"anaJobNm",   	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"dbcTblNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:60,   SaveName:"dbcColNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"dqiId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"dqiLnm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"ctqId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"ctqLnm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:30,   SaveName:"useYn", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:40,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:40,   SaveName:"erCnt", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:40,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:40,   SaveName:"dpmo", 	Align:"Right", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:40,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaCd", 	Align:"Center", Edit:0, Hidden:1, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaCdNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaStrDtm", 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaEndDtm", 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaDtls", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaIlCd", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaIlCdNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"imPlCd", 	Align:"Center", Edit:0, Hidden:1, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"imPlCdNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"imPlStrDtm", 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:100,   SaveName:"imPlEndDtm", 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:100,   SaveName:"imPlDtls", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:40,   SaveName:"anaKndCd", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
                    
                  
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
//         SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});
        
        SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
		SetColProperty("vrfCd", ${codeMap.vrfCdibs});
		SetColProperty("rvwStsCd", ${codeMap.rvwStsCdibs});
        
        InitComboNoMatchText(1, "");
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

$(window).on('load',function() {
	initGrid();
	var rqststep = "";
	rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	//if($("#mstFrm #aprLvl").val() > 0 ){
// 	if($("#mstFrm #rqstStepCd").val() != "A" ){
		setDispRqstButton(rqststep, grid_sheet);
// 	}
	
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	doAction("Search");
	
});


function doAction(sAction)
{
        
    switch(sAction)
    {
	    case "Search":
			
	    	switch($("#mstFrm #rqstStepCd").val()) {
	    		case "N":
	    			var param = $("#frmSearch").serialize();
	    	    	grid_sheet.DoSearch("<c:url value="/dq/impv/getErrBizruleList.do" />", param);		
	    			break;
	    		
	    		case "S":
	    		case "Q":
	    		case "A":
	    		default:
	    			
	    			var param = $("#mstFrm").serialize();
					grid_sheet.DoSearch("<c:url value="/dq/impv/getVrfImPlListIBS.do" />", param);
	    			break;
	    	}
	    	
	    	break;
	    	
	    case "Save" :
			// 공통으로 처리...
			
			ibsSaveJson = grid_sheet.GetSaveJson(0);
// 			ibsSaveJson = $("#frmInput").serializeArray();
// 			ibsSaveJson = $("#frmInput").serializeObject();

			if(ibsSaveJson.data.length == 0) return;
			
				
			var url = "<c:url value="/dq/impv/regImPlRqstList.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
			
	    	
  		case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'개선계획등록요청'});
            break;
            
  		case "Apply":
//   			alert("ApplyCsAna");

			if($("#rqstStepCd").val() == "Q") {
				grid_sheet.SetCellValue($('#selectRow').val(), "rvwConts", $('#rvwConts').val());
				return;
			}
			
  			  
			if($("select[name=rqstDcd").val() == "") {
				showMsgBox("ERR", "<s:message code='DEMD.DSTC.SEL'/>");  /*요청구분을 선택하세요..*/
				return;
  			}

  			if($("input[name=csAnaStrDtm]").val() > $("input[name=csAnaEndDtm]").val()) {
  				showMsgBox("ERR", "<s:message code='CAUS.ANLY.STRN.DTTM.END.DTTM.FRONT.PSTN'/>"); /* 원인분석시작일시는 종료일시보다 앞에 위치해야 합니다. */
				return;
  			}
  			
  			if($("input[name=imPlStrDtm]").val() > $("input[name=imPlEndDtm]").val()) {
  				showMsgBox("ERR", "<s:message code='IMPV.PLAN.STRN.DTTM.END.DTTM.FRONT.PSTN'/>"); /*개선계획시작일시는 종료일시보다 앞에 위치해야 합니다.*/
				return;
  			}
  			
  			if($("input[name=imPlStrDtm]").val() < $("input[name=csAnaEndDtm]").val()) {
  				showMsgBox("ERR", "<s:message code='IMPV.PLAN.STRN.DTTM.CAUS.ANLY.STRN.DTTM.BACK.PSTN'/>"); /*개선계획시작일시는 원인분석시작일시보다 뒤에 위치해야 합니다.*/
				return;
  			}
  			
  			grid_sheet.SetCellValue($('#selectRow').val(), "csAnaDtls", "");
  			grid_sheet.SetCellValue($('#selectRow').val(), "csAnaIlCd", "");  				
  			grid_sheet.SetCellValue($('#selectRow').val(), "csAnaIlCdNm", "");
  			
  			var csAnaCdTot = grid_sub_csAnaCd.SearchRows(); //원인분석유형코드 총 갯수 확인...
  			var csAnaIlCdTot = grid_sub_csAnaIlCd.SearchRows(); //원인분석불가코드 총 갯수 확인...
  			var selectRows = "";
  			var selectRowsText = "";
  			
  			var csAnaSelectCheck = 0; //원인분석유형코드 미선택인지 확인하는 변수...
  			var csAnaIlSelectCheck = 0; //원인분석불가코드 미선택인지 확인하는 변수...
  			
  			var ilRow = grid_sub_csAnaCd.FindText("codeLnm", "<s:message code='CAUS.ANLY.IPSB'/>", 0); //원인분석불가 값의 열 확인...
  			
  			//원인분석유형이 원인분석불가인 경우 원인분석불가코드의 값을 받아온다.
  			if (grid_sub_csAnaCd.GetCellValue(ilRow, "ibsCheck") == 1) {
  				for(var i=1; i<=csAnaIlCdTot; i++) {
  					if(grid_sub_csAnaIlCd.GetCellValue(i, "ibsCheck") == 1) {
  						
  						if(selectRowsText != "") {
  							selectRows += ", ";
  	  						selectRowsText += ", ";
  						}
  						csAnaIlSelectCheck += 1;
  						selectRows += grid_sub_csAnaIlCd.GetCellValue(i, "codeCd");
  						selectRowsText += grid_sub_csAnaIlCd.GetCellValue(i, "codeLnm");
  						
  						
  					}
  					
  				}
  				if(csAnaIlSelectCheck == 0) {
  					showMsgBox("ERR", "<s:message code='CAUS.ANLY.DETL.NO.SEL'/>");/*원인분석내역이 선택되지 않았습니다.*/
  					return;
  				}
  				
  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaCd", grid_sub_csAnaCd.GetCellValue(ilRow, "codeCd"));
  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaCdNm", grid_sub_csAnaCd.GetCellValue(ilRow, "codeLnm"));
  				grid_sheet.SetCellValue($('#selectRow').val(), "rqstDcd", $("select[name=rqstDcd]").val());
  				if($("input[name=csAnaStrDtm]").val() != "" && $("input[name=csAnaEndDtm]").val() != "") {
  					grid_sheet.SetCellValue($('#selectRow').val(), "csAnaStrDtm", $("input[name=csAnaStrDtm]").val());
  	  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaEndDtm", $("input[name=csAnaEndDtm]").val());	
  				}
  				
  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaIlCd", selectRows);
  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaIlCdNm", selectRowsText);
  				
  				
  			} else { //원인분석유형이 여러가지이므로, 체크된 행을 콤마로 연결한다.
  	  			for(var i=1; i<=csAnaCdTot; i++){
  	  				if(grid_sub_csAnaCd.GetCellValue(i, "ibsCheck") == 1) {
  	  					
  	  					if(selectRowsText != "") {
  	  						selectRows += ", ";
	  						selectRowsText += ", ";
						}
  	  					csAnaSelectCheck += 1;
  	  					selectRows += grid_sub_csAnaCd.GetCellValue(i, "codeCd");
  	  					selectRowsText += grid_sub_csAnaCd.GetCellValue(i, "codeLnm");
  	  					
  	  				}
  	  			}
  			
  	  			if(csAnaSelectCheck == 0) {
					showMsgBox("ERR", "<s:message code='CAUS.ANLY.TY.NO.SEL'/>");/*원인분석유형이 선택되지 않았습니다.*/
					return;
				}

				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaCd", selectRows);
				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaCdNm", selectRowsText);
				grid_sheet.SetCellValue($('#selectRow').val(), "rqstDcd", $("select[name=rqstDcd]").val());
				if($("input[name=csAnaStrDtm]").val() != "" && $("input[name=csAnaEndDtm]").val() != "") {
  					grid_sheet.SetCellValue($('#selectRow').val(), "csAnaStrDtm", $("input[name=csAnaStrDtm]").val());
  	  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaEndDtm", $("input[name=csAnaEndDtm]").val());	
  				}
				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaDtls", $("textarea[name=csAnaDtls]").val());
  	  			
  			}
  			
  			var imPlCdTot = grid_sub_imPlCd.SearchRows(); //개선계획코드 총 갯수 확인...
  			
  			var selectRows = "";
  			var selectRowsText = "";
  			
  			var imPlSelectCheck = 0; //개선계획코드 미선택인지 확인하는 변수...
  			  			
  	  			for(var i=1; i<=imPlCdTot; i++){
  	  				if(grid_sub_imPlCd.GetCellValue(i, "ibsCheck") == 1) {
  	  					
  	  					if(selectRowsText != "") {
  	  						selectRows += ", ";
	  						selectRowsText += ", ";
						}
  	  					imPlSelectCheck += 1;
  	  					selectRows += grid_sub_imPlCd.GetCellValue(i, "codeCd");
  	  					selectRowsText += grid_sub_imPlCd.GetCellValue(i, "codeLnm");
  	  					
  	  				}
  	  			}
  			
  	  			if(imPlSelectCheck == 0) {
					showMsgBox("ERR", "<s:message code='IMPV.PLAN.TY.NO.SEL'/>"); /*개선계획유형이 선택되지 않았습니다.*/
					return;
				}

				grid_sheet.SetCellValue($('#selectRow').val(), "imPlCd", selectRows);
				grid_sheet.SetCellValue($('#selectRow').val(), "imPlCdNm", selectRowsText);
				if($("input[name=imPlStrDtm]").val() != "" && $("input[name=imPlEndDtm]").val() != "") {
  					grid_sheet.SetCellValue($('#selectRow').val(), "imPlStrDtm", $("input[name=imPlStrDtm]").val());
  	  				grid_sheet.SetCellValue($('#selectRow').val(), "imPlEndDtm", $("input[name=imPlEndDtm]").val());	
  				}
				grid_sheet.SetCellValue($('#selectRow').val(), "imPlDtls", $("textarea[name=imPlDtls]").val());
  			
  			
  			break;
  			
  			
  		//등록가능 일 경우 WAM 영역 저장
  			//차후 결재로직 개발 완료 후 요청 구현
		case "submitImPlLst":  
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
		        
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
			
			var url = "<c:url value="/dq/impv/approveImPl.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
	   		
			case "ApplyAll":
//   			alert("ApplyCsAna");

			if($("#rqstStepCd").val() == "Q") {
				grid_sheet.SetCellValue($('#selectRow').val(), "rvwConts", $('#rvwConts').val());
				return;
			}
			
  			  
			if($("select[name=rqstDcd").val() == "") {
				showMsgBox("ERR", "<s:message code='DEMD.DSTC.SEL'/>");  /*요청구분을 선택하세요..*/
				return;
  			}

  			if($("input[name=csAnaStrDtm]").val() > $("input[name=csAnaEndDtm]").val()) {
  				showMsgBox("ERR", "<s:message code='CAUS.ANLY.STRN.DTTM.END.DTTM.FRONT.PSTN'/>"); /* 원인분석시작일시는 종료일시보다 앞에 위치해야 합니다. */
				return;
  			}
  			
  			if($("input[name=imPlStrDtm]").val() > $("input[name=imPlEndDtm]").val()) {
  				showMsgBox("ERR", "<s:message code='IMPV.PLAN.STRN.DTTM.END.DTTM.FRONT.PSTN'/>"); /*개선계획시작일시는 종료일시보다 앞에 위치해야 합니다.*/
				return;
  			}
  			
  			if($("input[name=imPlStrDtm]").val() < $("input[name=csAnaEndDtm]").val()) {
  				showMsgBox("ERR", "<s:message code='IMPV.PLAN.STRN.DTTM.CAUS.ANLY.STRN.DTTM.BACK.PSTN'/>"); /*개선계획시작일시는 원인분석시작일시보다 뒤에 위치해야 합니다.*/
				return;
  			}
  			
  			grid_sheet.SetCellValue($('#selectRow').val(), "csAnaDtls", "");
  			grid_sheet.SetCellValue($('#selectRow').val(), "csAnaIlCd", "");  				
  			grid_sheet.SetCellValue($('#selectRow').val(), "csAnaIlCdNm", "");
  			
  			var csAnaCdTot = grid_sub_csAnaCd.SearchRows(); //원인분석유형코드 총 갯수 확인...
  			var csAnaIlCdTot = grid_sub_csAnaIlCd.SearchRows(); //원인분석불가코드 총 갯수 확인...
  			var selectRows = "";
  			var selectRowsText = "";
  			
  			var csAnaSelectCheck = 0; //원인분석유형코드 미선택인지 확인하는 변수...
  			var csAnaIlSelectCheck = 0; //원인분석불가코드 미선택인지 확인하는 변수...
  			
  			var ilRow = grid_sub_csAnaCd.FindText("codeLnm", "<s:message code='CAUS.ANLY.IPSB'/>", 0); //원인분석불가 값의 열 확인...
  			
  			//원인분석유형이 원인분석불가인 경우 원인분석불가코드의 값을 받아온다.
  			if (grid_sub_csAnaCd.GetCellValue(ilRow, "ibsCheck") == 1) {
  				for(var i=1; i<=csAnaIlCdTot; i++) {
  					if(grid_sub_csAnaIlCd.GetCellValue(i, "ibsCheck") == 1) {
  						
  						if(selectRowsText != "") {
  							selectRows += ", ";
  	  						selectRowsText += ", ";
  						}
  						csAnaIlSelectCheck += 1;
  						selectRows += grid_sub_csAnaIlCd.GetCellValue(i, "codeCd");
  						selectRowsText += grid_sub_csAnaIlCd.GetCellValue(i, "codeLnm");
  						
  						
  					}
  					
  				}
  				if(csAnaIlSelectCheck == 0) {
  					showMsgBox("ERR", "<s:message code='CAUS.ANLY.DETL.NO.SEL'/>");/*원인분석내역이 선택되지 않았습니다.*/
  					return;
  				}
  				
  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaCd", grid_sub_csAnaCd.GetCellValue(ilRow, "codeCd"));
  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaCdNm", grid_sub_csAnaCd.GetCellValue(ilRow, "codeLnm"));
  				grid_sheet.SetCellValue($('#selectRow').val(), "rqstDcd", $("select[name=rqstDcd]").val());
  				if($("input[name=csAnaStrDtm]").val() != "" && $("input[name=csAnaEndDtm]").val() != "") {
  					grid_sheet.SetCellValue($('#selectRow').val(), "csAnaStrDtm", $("input[name=csAnaStrDtm]").val());
  	  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaEndDtm", $("input[name=csAnaEndDtm]").val());	
  				}
  				
  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaIlCd", selectRows);
  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaIlCdNm", selectRowsText);
  				
  				
  			} else { //원인분석유형이 여러가지이므로, 체크된 행을 콤마로 연결한다.
  	  			for(var i=1; i<=csAnaCdTot; i++){
  	  				if(grid_sub_csAnaCd.GetCellValue(i, "ibsCheck") == 1) {
  	  					
  	  					if(selectRowsText != "") {
  	  						selectRows += ", ";
	  						selectRowsText += ", ";
						}
  	  					csAnaSelectCheck += 1;
  	  					selectRows += grid_sub_csAnaCd.GetCellValue(i, "codeCd");
  	  					selectRowsText += grid_sub_csAnaCd.GetCellValue(i, "codeLnm");
  	  					
  	  				}
  	  			}
  			
  	  			if(csAnaSelectCheck == 0) {
					showMsgBox("ERR", "<s:message code='CAUS.ANLY.TY.NO.SEL'/>");/*원인분석유형이 선택되지 않았습니다.*/
					return;
				}

				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaCd", selectRows);
				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaCdNm", selectRowsText);
				grid_sheet.SetCellValue($('#selectRow').val(), "rqstDcd", $("select[name=rqstDcd]").val());
				if($("input[name=csAnaStrDtm]").val() != "" && $("input[name=csAnaEndDtm]").val() != "") {
  					grid_sheet.SetCellValue($('#selectRow').val(), "csAnaStrDtm", $("input[name=csAnaStrDtm]").val());
  	  				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaEndDtm", $("input[name=csAnaEndDtm]").val());	
  				}
				grid_sheet.SetCellValue($('#selectRow').val(), "csAnaDtls", $("textarea[name=csAnaDtls]").val());
  	  			
  			}
  			
  			var imPlCdTot = grid_sub_imPlCd.SearchRows(); //개선계획코드 총 갯수 확인...
  			
  			var selectRows = "";
  			var selectRowsText = "";
  			
  			var imPlSelectCheck = 0; //개선계획코드 미선택인지 확인하는 변수...
  			  			
  	  			for(var i=1; i<=imPlCdTot; i++){
  	  				if(grid_sub_imPlCd.GetCellValue(i, "ibsCheck") == 1) {
  	  					
  	  					if(selectRowsText != "") {
  	  						selectRows += ", ";
	  						selectRowsText += ", ";
						}
  	  					imPlSelectCheck += 1;
  	  					selectRows += grid_sub_imPlCd.GetCellValue(i, "codeCd");
  	  					selectRowsText += grid_sub_imPlCd.GetCellValue(i, "codeLnm");
  	  					
  	  				}
  	  			}
  			
  	  			if(imPlSelectCheck == 0) {
					showMsgBox("ERR", "<s:message code='IMPV.PLAN.TY.NO.SEL'/>"); /*개선계획유형이 선택되지 않았습니다.*/
					return;
				}

				grid_sheet.SetCellValue($('#selectRow').val(), "imPlCd", selectRows);
				grid_sheet.SetCellValue($('#selectRow').val(), "imPlCdNm", selectRowsText);
				if($("input[name=imPlStrDtm]").val() != "" && $("input[name=imPlEndDtm]").val() != "") {
  					grid_sheet.SetCellValue($('#selectRow').val(), "imPlStrDtm", $("input[name=imPlStrDtm]").val());
  	  				grid_sheet.SetCellValue($('#selectRow').val(), "imPlEndDtm", $("input[name=imPlEndDtm]").val());	
  				}
				grid_sheet.SetCellValue($('#selectRow').val(), "imPlDtls", $("textarea[name=imPlDtls]").val());
  			
  			
  			break;
  			
    }
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	
// 	//검증결과 상세조회
// 	if($("#rqstNo").val() != "" && $("#rqstStepCd").val() == "S"){
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
	
	if(row < 1) return;
	//선택한 상세정보를 가져온다...
	$('#selectRow').val(row);
	
// 	var param =  grid_sheet.GetRowJson(row);
	//선택한 그리드의 row 내용을 보여준다.....
// 	var tmphtml = '<s:message code="BZWR.RGR.NM"/>' + ' : ' + param.anaJobNm; /*업무규칙명*/
// 	$('#bizrule_sel_title').html(tmphtml);
	
	//기존 체크내역은 모두 해제한다.
	grid_sub_csAnaCd.CheckAll(0, 0);
	grid_sub_csAnaIlCd.CheckAll(0, 0);
	$("select[name=rqstDcd]").val("");
	$("input[name=csAnaStrDtm]").val("");
	$("input[name=csAnaEndDtm]").val("");
	$("textarea[name=csAnaDtls]").val("");
	grid_sub_imPlCd.CheckAll(0, 0);
	$("input[name=imPlStrDtm]").val("");
	$("input[name=imPlEndDtm]").val("");
	$("textarea[name=imPlDtls]").val("");
	
	
	//원인분석유형이 원인분석 불가일경우/아닐경우 매핑처리...
	if (grid_sheet.GetCellValue(row, "csAnaCd") == "") {
		
	} else if(grid_sheet.GetCellValue(row, "csAnaCd") == "09") {
		$("#csAnaDtlsGrid").show();
		$("#csAnaDtlsText").hide();
				
		grid_sub_csAnaCd.SetCellValue(grid_sub_csAnaCd.FindText("codeLnm", "<s:message code='CAUS.ANLY.IPSB'/>", 0), "ibsCheck", "1");/*원인분석불가*/
		$("select[name=rqstDcd]").val(grid_sheet.GetCellValue(row, "rqstDcd"));
		var csAnaDtls = grid_sheet.GetCellValue(row, "csAnaIlCdNm");
		var dtlsStr = csAnaDtls.split(", ");
		
		for (var i=0; i<dtlsStr.length; i++){
			grid_sub_csAnaIlCd.SetCellValue(grid_sub_csAnaIlCd.FindText("codeLnm", dtlsStr[i], 0), "ibsCheck", "1");
		}
	} else { //원인분석유형이 다양할경우...
		$("#csAnaDtlsGrid").hide();
		$("#csAnaDtlsText").show();
		
		var csAnaCd = grid_sheet.GetCellValue(row, "csAnaCd");
		var cdsStr = csAnaCd.split(", ");
		for (var i=0; i<cdsStr.length; i++){
			grid_sub_csAnaCd.SetCellValue(grid_sub_csAnaCd.FindText("codeCd", cdsStr[i], 0), "ibsCheck", "1");
		}
		$("select[name=rqstDcd]").val(grid_sheet.GetCellValue(row, "rqstDcd"));
		$("textarea[name=csAnaDtls]").val(grid_sheet.GetCellValue(row, "csAnaDtls"));
		
	}

	
	if (grid_sheet.GetCellValue(row, "imPlCd") == "") {
		
	} else { 
		
		var imPlCd = grid_sheet.GetCellValue(row, "imPlCd");
		var cdsStr = imPlCd.split(", ");
		for (var i=0; i<cdsStr.length; i++){
			grid_sub_imPlCd.SetCellValue(grid_sub_imPlCd.FindText("codeCd", cdsStr[i], 0), "ibsCheck", "1");
		}
		$("textarea[name=imPlDtls]").val(grid_sheet.GetCellValue(row, "imPlDtls"));

	}
	
	
	//날짜가 Text형태이므로, substr을 이용해 포맷을 변환하여 나타낸다.
	convertDate();
	
}

//============================================
// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
//============================================
function setDispRqstButton(rqststepcd, objgrid)
{		
	var rqststep = rqststepcd;
	
	switch(rqststep) {
	
	//작성전...
	case 'N': 
		$("#btnSearch").show().find(".ui-button-text").text("<s:message code='BTN.READ'/>");/* 조회 */
		//추가, 저장, 삭제 버튼 숨기기.....
		$("button.btn_rqst_new, button.btn_save, button.btn_delete").hide();
		$("#btnSave").show();
		
		
		
		break;
	//임시저장 : 저장 및 검증 완료
	case 'S':
		//등록요청 버튼을 보여준다....
		$("#btnRegRqst").show();
		$("button.btn_rqst_new, button.btn_save, button.btn_delete").hide();
		$("#btnSearch").show().find(".ui-button-text").text("<s:message code='RE.INQ' />");//재조회
		objgrid.SetColHidden("vrfCd"	,0);
		objgrid.SetColHidden("rvwStsCd"	,0);
		objgrid.SetColHidden("rvwConts"	,0);
		objgrid.SetColHidden("anaDgrDisp"	,1);
		objgrid.SetColHidden("bizAreaLnm"	,1);
		objgrid.SetColHidden("dbConnTrgId"	,1);
		objgrid.SetColHidden("dbConnTrgLnm"	,1);
		objgrid.SetColHidden("dbcTblNm"	,1);
		objgrid.SetColHidden("dbcColNm"	,1);
		objgrid.SetColHidden("dqiId"	,1);
		objgrid.SetColHidden("dqiLnm"	,1);
		objgrid.SetColHidden("ctqId"	,1);
		objgrid.SetColHidden("ctqLnm"	,1);
		objgrid.SetColHidden("anaCnt"	,1);
		objgrid.SetColHidden("erCnt"	,1);
		objgrid.SetColHidden("erRate"	,1);
		objgrid.SetColHidden("dpmo"	,1);
		objgrid.SetColHidden("sigma"	,1);
		
		$("#btnSave").show();
		
		break;
	//등록요청 : 결재 요청 완료
	case 'Q':
		//검증결과 탭을 숨긴다.
		$("#tabs-rqstvrf").hide();
		
		//추가, 저장, 삭제 버튼 숨기기.....
		$("button.btn_rqst_new, button.btn_save, button.btn_delete").hide();
		
		//검토처리 버튼 보여주기....
		$("#btnReqApprove").parent().show();
		
		//검토상태를 보여주면서 수정 가능하도록 처리...
		objgrid.SetColHidden("rvwStsCd"	,0);
		objgrid.SetColHidden("rvwConts"	,0);
		objgrid.SetColHidden("ibsCheck"	,1);
		objgrid.SetColHidden("rqstDcd"	,1);
		objgrid.SetColHidden("vrfCd"	,1);
		objgrid.SetColHidden("anaDgrDisp"	,1);
		objgrid.SetColHidden("bizAreaLnm"	,1);
		objgrid.SetColHidden("dbConnTrgId"	,1);
		objgrid.SetColHidden("dbConnTrgLnm"	,1);
		objgrid.SetColHidden("dbcTblNm"	,1);
		objgrid.SetColHidden("dbcColNm"	,1);
		objgrid.SetColHidden("dqiId"	,1);
		objgrid.SetColHidden("dqiLnm"	,1);
		objgrid.SetColHidden("ctqId"	,1);
		objgrid.SetColHidden("ctqLnm"	,1);
		objgrid.SetColHidden("anaCnt"	,1);
		objgrid.SetColHidden("erCnt"	,1);
		objgrid.SetColHidden("erRate"	,1);
		objgrid.SetColHidden("dpmo"	,1);
		objgrid.SetColHidden("sigma"	,1);
		
		objgrid.SetColProperty("rvwStsCd", {Edit:1}	);
		
		$("#reviewStatus").show();
		
		break;
	//결재처리 : 승인이 완료된 상태
	case 'A':
		//검증결과 탭을 숨긴다.
		$("#tabs-rqstvrf").hide();
		
		//추가, 저장, 삭제 버튼 숨기기.....
		$("button.btn_rqst_new, button.btn_save, button.btn_delete").hide();
		
		//검토처리 버튼 보여주기....
// 		$("#btnReqApprove").show();
		
		//검토상태를 보여주면서 수정 가능하도록 처리...
		objgrid.SetColHidden("rvwStsCd"	,0);
		objgrid.SetColHidden("rvwConts"	,0);
		objgrid.SetColHidden("ibsCheck"	,1);
		objgrid.SetColHidden("rqstDcd"	,1);
		objgrid.SetColHidden("vrfCd"	,1);
		objgrid.SetColHidden("anaDgrDisp"	,1);
		objgrid.SetColHidden("bizAreaLnm"	,1);
		objgrid.SetColHidden("dbConnTrgId"	,1);
		objgrid.SetColHidden("dbConnTrgLnm"	,1);
		objgrid.SetColHidden("dbcTblNm"	,1);
		objgrid.SetColHidden("dbcColNm"	,1);
		objgrid.SetColHidden("dqiId"	,1);
		objgrid.SetColHidden("dqiLnm"	,1);
		objgrid.SetColHidden("ctqId"	,1);
		objgrid.SetColHidden("ctqLnm"	,1);
		objgrid.SetColHidden("anaCnt"	,1);
		objgrid.SetColHidden("erCnt"	,1);
		objgrid.SetColHidden("erRate"	,1);
		objgrid.SetColHidden("dpmo"	,1);
		objgrid.SetColHidden("sigma"	,1);
		
// 		objgrid.SetColProperty("rvwStsCd", {Edit:1}	);
		
		$("#reviewStatus").show();
		
		//신규버튼 보여주기.....
		$("#btnBlank").show();
		
		break;
		
	}

	objgrid.FitColWidth(); 
}

// //검증결과 상세조회
// function getRqstVrfLst() {
	
// 	var param = "rqstNo="+$("#rqstNo").val();
//          param += "&rqstSno="+grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "rqstSno");
//          param += "&bizDtlCd="+$("#bizDcd").val();
    
// 	grid_vrf.DoSearch("<c:url value="/commons/rqstmst/getRqstVrfLst.do" />", param);
// }


/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
//	alert(res.action);
	
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
	    		
	    		setDispRqstButton($("#mstFrm #rqstStepCd").val(), grid_sheet);
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");
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
	    		setDispRqstButton($("#mstFrm #rqstStepCd").val(), grid_sheet);
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/dq/impv/impl_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

//날짜 변환함수...
function convertDate() {
	
	var tempStr = grid_sheet.GetCellValue($('#selectRow').val(), "csAnaStrDtm");
	if(tempStr != "") {
		var str = tempStr.substr(0,4) + "-" + tempStr.substr(4,2) + "-" + tempStr.substr(6,2);
		$("input[name=csAnaStrDtm]").val(str);	
	}
	var tempStr = grid_sheet.GetCellValue($('#selectRow').val(), "csAnaEndDtm");
	if(tempStr != "") {
		var str = tempStr.substr(0,4) + "-" + tempStr.substr(4,2) + "-" + tempStr.substr(6,2);
		$("input[name=csAnaEndDtm]").val(str);	
	}
	var tempStr = grid_sheet.GetCellValue($('#selectRow').val(), "imPlStrDtm");
	if(tempStr != "") {
		var str = tempStr.substr(0,4) + "-" + tempStr.substr(4,2) + "-" + tempStr.substr(6,2);
		$("input[name=imPlStrDtm]").val(str);	
	}
	var tempStr = grid_sheet.GetCellValue($('#selectRow').val(), "imPlEndDtm");
	if(tempStr != "") {
		var str = tempStr.substr(0,4) + "-" + tempStr.substr(4,2) + "-" + tempStr.substr(6,2);
		$("input[name=imPlEndDtm]").val(str);	
	}

}


//업무규칙 상세 팝업
function brDtlPopup(row){
	
	var param = "brId="+grid_sheet.GetRowJson(row).anaJobId;
	var objId =grid_sheet.GetRowJson(row).anaJobId;
	var anaStrDtm = grid_sheet.GetRowJson(row).anaStrDtm.replace(/ /g, ''); 
	
	if(objId == ""){
		showMsgBox("ERR", "<s:message code='ERR.EMPTY' arguments="<s:message code='INQ.DATA.SEL'/>"/>"); /*조회할 데이터를 선택하십시오.*/
		return;
	}
	
 	var param1 = "&objId="+objId;
	     param1 += "&objDate="+anaStrDtm;
	     param1 += "&objIdCol=BR_ID";		  
         param1 += "&objResTbl=WAM_BR_RESULT";
	     param1 += "&objErrTbl=WAM_BR_ERR_DATA";
	     param1 += "&erDataSnoCol=ER_DATA_SNO";
  	var url = '<c:url value="/dq/bizrule/popup/bizrule_dtl_pop.do"/>';

	var popwin = OpenModal(url+"?"+param+param1, "bizruleDtlPop",  1000, 640, "yes"); 
	
	popwin.focus();
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	brDtlPopup(row);	
}

function  grid_sheet_OnSearchEnd (code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	}
}

</script>
</head>
<body>

<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="IMPV.PLAN.REG.DEMD" /></div><!--개선계획 등록요청-->
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div id="search_div">
	<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
	<form id="frmSearch" name="frmSearch" method="post">
		 <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BZWR.RULE.INQ' />"> <!--업무규칙 조회-->

                   <caption><s:message code="IMPV.PLAN.REG.DEMD" /></caption><!--개선계획 등록요청-->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                  </colgroup>
                   
                   <tbody>  
                   	<tr>                        
                    	<th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->
                        	<td>
                        	 <select id="dbConnTrgId"  name="dbConnTrgId">
								    <option value=""><s:message code="WHL" /></option><!--전체-->
								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
							</select>
                            </td>
                        <th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->
	                    	<td>
	                    	<input type="text" id="dbcTblNm" name="dbcTblNm"/>
	                        </td> 
                        <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->
	                    	<td>
	                    	<input type="text" id="dbcColNm" name="dbcColNm"/>
	                        </td> 
                   </tr>
                   	<tr>                        
                    	<th scope="row"><label for="bizAreaLnm"><s:message code="BZWR.TRRT.NM" /></label></th><!--업무영역명-->
                        	<td>
                        	<input type="text" id="bizAreaLnm" name="bizAreaLnm"/>
                            </td>
                        <th scope="row"><label for="dqiLnm"><s:message code="QLTY.INDC.NM"/></label></th><!--품질지표명-->
	                    	<td>
	                    	<input type="text" id="dqiLnm" name="dqiLnm"/>
	                        </td> 
                        <th scope="row"><label for="ctqLnm"><s:message code="IMCE.INFO.ITEM.NM"/></label></th><!--중요정보항목명-->
	                    	<td>
	                    	<input type="text" id="ctqLnm" name="ctqLnm"/>
	                        </td> 
                   </tr>
                   	<tr>                        
                    	<th scope="row"><label for="brNm"><s:message code="BZWR.RGR.NM"/></label></th><!--업무규칙명-->
                        	<td colspan="3">
                        	<input type="text" id="brNm" name="brNm"/>
                            </td>
                        <th scope="row"><label for="anaDgr"><s:message code="ANLY.HIST" /></label></th><!--분석차수-->
	                    	<td>
	                    	<select id="anaDgr"  name="anaDgr">
								    <option value=""><s:message code="WHL" /></option><!--전체-->
								    <c:forEach var="code" items="${codeMap.brAnaDgrCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
	                        </td> 
                        
                   </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div><!--를 사용하시면 됩니다.--><!--클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고-->
</form>

<div style="clear:both; height:5px;"><span></span></div>

 <!-- 조회버튼영역  -->         
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>
<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- <div class="selected_title_area"> -->
<%--     <div class="selected_title" id="bizrule_sel_title"> <span></span></div> --%>
<!-- </div> -->
<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1"><a href="#tabs-1"><s:message code="CAUS.ANLY.DTL"/></a></li><!--원인분석상세-->
	    <li id="tab-2"><a href="#tabs-2"><s:message code="IMPV.PLAN.DTL"/></a></li><!--개선계획상세-->
	    <li id="tabs-rqstvrf"><a href="#tabs-3"><s:message code="VRFC.RSLT" /></a></li><!--검증결과-->
	    <li id="tabs-rqstchg"><a href="#tabs-4"><s:message code="CHG.ITEM" /></a></li><!--변경항목-->
	  </ul>
	  <div id="tabs-1">
	  		<div id="csDetailInfo"><%@include file="impl_rqst_csana_dtl.jsp" %></div>
	  </div>
	  <div id="tabs-2">
			<div id="imDetailInfo"><%@include file="impl_rqst_impl_dtl.jsp" %></div>
	  </div>
	  <div id="tabs-3">
	  		<div id="vrfDetailInfo"><%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %></div>
	  </div>
	  <div id="tabs-4">
		<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
	  </div>
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</div>
<form id="frmHidden" name="frmHidden" method="post">
	<input type="hidden" id="selectRow" name="selectRow"/>
</form>


<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>
<div style="clear:both; height:20px;"><span></span></div>
</body>

</html>