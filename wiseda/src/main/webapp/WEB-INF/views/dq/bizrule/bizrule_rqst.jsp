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
<title><s:message code="BZWR.RULE.REG.DEMD" /></title><!--업무규칙 등록요청-->


<script type="text/javascript">
$(document).ready(function() {

	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('BRA');
	
	//그리드 초기화 
 	initGrid();
	
 	//필수입력항목입니다. 내용을 입력해 주세요. 
// 	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	var requiremessage = "<s:message code='VALID.SHORTREQUIRED' />";
	
	//폼검증
	$("#frmDetail").validate({
		rules: {
			bizAreaLnm		: "required",
			brNm		: "required",
			dqiLnm 	: "required",
			rqstDcd : "required",
			brCrgpUserNm : "required"
// 			sysCdYn		: "required"
		},
		messages: {
			bizAreaLnm		: requiremessage,
			brNm		: requiremessage,
			dqiLnm 	: requiremessage,
			rqstDcd : requiremessage,
			brCrgpUserNm : requiremessage
// 			sysCdYn		: requiremessage
		}
	});
	$("#frmDbDetail").validate({
		rules: {
			dbConnTrgPnm		: "required",
			dbcTblNm		: "required",
			dbcColNm 	: "required",
			cntSql		: "required",
			anaSql 	: "required"
// 			sysCdYn		: "required"
		},
		messages: {
			dbConnTrgPnm		: requiremessage,
			dbcTblNm		: requiremessage,
			dbcColNm 	: requiremessage,
			cntSql		: requiremessage,
			anaSql 	: requiremessage
// 			sysCdYn		: requiremessage
		}
	});
/*	$("#frmVrtDetail").validate({
		rules: {
			tgtDbConnTrgId : {
				required: function(){
					 if($('#tgtDbcTblNm').val() != "" || $('#tgtDbcColNm').val() != "" || $('#tgtKeyDbcColNm').val() != "" || 
							 $('#tgtKeyDbcColVal').val() != "" || $('#tgtCntSql').val() != "" || $('#tgtAnaSql').val() != ""){  return true;  }
		             return false;
				}
			},
			tgtDbcTblNm : {
				required: function(){
					 if($('#tgtDbConnTrgId').val() != "" || $('#tgtDbcColNm').val() != "" || $('#tgtKeyDbcColNm').val() != "" ||
							 $('#tgtKeyDbcColVal').val() != "" || $('#tgtCntSql').val() != "" || $('#tgtAnaSql').val() != ""){  return true;  }
		             return false;
				}
			},
			tgtDbcColNm : {
				required: function(){
					 if($('#tgtDbConnTrgId').val() != "" || $('#tgtDbcTblNm').val() != "" || $('#tgtKeyDbcColNm').val() != "" || 
							 $('#tgtKeyDbcColVal').val() != "" || $('#tgtCntSql').val() != "" || $('#tgtAnaSql').val() != ""){  return true;  }
		             return false;
				}
			},
			tgtKeyDbcColNm : {
				required: function(){
					 if($('#tgtDbConnTrgId').val() != "" || $('#tgtDbcTblNm').val() != "" || $('#tgtDbcColNm').val() != "" || 
							 $('#tgtKeyDbcColVal').val() != "" || $('#tgtCntSql').val() != "" || $('#tgtAnaSql').val() != ""){  return true;  }
		             return false;
				}
			},
			tgtKeyDbcColVal : {
				required: function(){
					 if($('#tgtDbConnTrgId').val() != "" || $('#tgtDbcTblNm').val() != "" || $('#tgtDbcColNm').val() != "" || 
							 $('#tgtKeyDbcColNm').val() != "" || $('#tgtCntSql').val() != "" || $('#tgtAnaSql').val() != ""){  return true;  }
		             return false;
				}
			},
			tgtCntSql : {
				required: function(){
					 if($('#tgtDbConnTrgId').val() != "" || $('#tgtDbcTblNm').val() != "" || $('#tgtDbcColNm').val() != "" || 
							 $('#tgtKeyDbcColNm').val() != "" || $('#tgtKeyDbcColVal').val() != "" || $('#tgtAnaSql').val() != ""){  return true;  }
		             return false;
				}
			},
			tgtAnaSql : {
				required: function(){
					 if($('#tgtDbConnTrgId').val() != "" || $('#tgtDbcTblNm').val() != "" || $('#tgtDbcColNm').val() != "" || 
							 $('#tgtKeyDbcColNm').val() != "" || $('#tgtKeyDbcColVal').val() != "" || $('#tgtCntSql').val() != ""){  return true;  }
		             return false;
				}
			},
			
		},
		messages: {
			tgtDbConnTrgId		: requiremessage,
			tgtDbcTblNm		: requiremessage,
			tgtDbcColNm		: requiremessage,
			tgtKeyDbcColNm		: requiremessage,
			tgtKeyDbcColVal		: requiremessage,
			tgtCntSql		: requiremessage,
			tgtAnaSql		: requiremessage
		}
	}); */
	
	
	 	 	
	//탭 초기화
 	//$( "#tabs" ).tabs();
// 	$("#btnTreeNew").hide();
// 	$("#btnDelete").hide();
	
	//조회
	$("#btnSearch").click(function(){
		
		$('#frmDetail')[0].reset();
		$('#bizrule_sel_title').html(null);
		
		doAction("Search");
		
		});
	
	// 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		
		//등록가능한지 확인한다.vrfCd = 1
		var regchk = grid_sheet.FindText("vrfCd", "<s:message code='REG.POSB' />");/*등록가능*/

		
		if(regchk > 0) {
			if($("#mstFrm #aprLvl").val() > 0 ){
				showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'submitBizruleLst');
			}else{
				showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'Approve');
			}
		} else {
			showMsgBox("INF", "<s:message code='ERR.SUBMIT' />");
			return false;
		}
		
	});	
	
	//변경대상 추가
	$("#btnChangAdd").click(function(event){
		event.preventDefault();	//브라우저 기본 이벤트 제거...ssss
		//팝업 flag 값 설정
		doAction("bizrulePopup");
	});
	
	//화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/bizrule/bizrule_rqst.do" />';
    } );
	
	//전체승인 버튼 이벤트 처리
	$("#btnAllApprove").click(function(){
		doAllApprove(grid_sheet, "1");
	});
	//전체반려 버튼 이벤트 처리
	$("#btnAllReject").click(function(){
		doAllApprove(grid_sheet, "2");
	});
	
	//신규버튼
	$("#btnBlank").click(function(){
		location.href = '<c:url value="/dq/bizrule/bizrule_rqst.do" />';
	});
	
	//검토처리 Event Bind
	$("#btnReqApprove").click(function(){
		//alert("결재처리")
		//그리드 변경대상 체크한다.
		if (!chkSheetDataModified(grid_sheet)) {
			showMsgBox("INF", "<s:message code='ERR.CHKAPPR' />");
			return false;
		}
		// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
		if (chkRvwStsCd(grid_sheet, "rvwStsCd") > -1) {
			//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
			showMsgBox("INF", "<s:message code='ERR.APPROVE' />");
			return false;
		};
		
		//반려 선택시 반려사유를 입력하도록 한다.
		var tmprow = chkRvwCont(grid_sheet, "rvwStsCd", "rvwConts");
		if (tmprow > 0 ) {
			showMsgBox("INF", "<s:message code='ERR.REJECT' />");
			grid_sheet.SetSelectRow(tmprow);
			//선택한 상세정보를 가져온다...
			var param =  grid_sheet.GetRowJson(tmprow);
		
			//선택한 그리드의 row 내용을 보여준다.....
			var tmphtml = '업무규칙 : ' + param.brNm;
			$('#bizrule_sel_title').html(tmphtml);
			
			//var param = grid_sheet.GetRowJson(row);
			var param1 = $("#mstFrm").serialize();
			param1 += "&rqstSno=" + param.rqstSno;
			
			//param = 
// 			loadDetail(param1);
			
			//검증결과 조회
			getRqstVrfLst(param1);
			$("#frmInput #rvwConts").focus();
			return false;
		}
		
		doAction("Approve");
		
	});
	
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
						
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  });
	
	// 추가(기존) Event Bind
	$("#btnChangAdd").click(function(){
		doAction("AddWam");
		
	});

	// 추가(엑셀업로드)Event Bind
	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); });
	
	// 삭제 Event Bind
	$("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code='ERR.CHKDEL' />")) {
			//삭제 확인 메시지
			showMsgBox("CNF", "<s:message code='CNF.DEL' />", 'Delete');
    	}
	});
	
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
		  
	// 폼저장이벤 Event Bind
	$("#btnGridSave").click( function(){ 
// 		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmInput #rvwConts").val());
			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmDetail #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		$("#tabs #tab-1").click();
		if(!$("#frmDetail").valid()) {
			return false;
		}
		$("#tabs #tab-2").click();
		if(!$("#frmDbDetail").valid()) {
			return false;
		}
// 		$("#tabs #tab-3").click();
// 		if(!$("#frmVrtDetail").valid()) {
// 			return false;
// 		}
		$("#tabs #tab-1").click();

		//저장할래요? 확인창...
		var message = "<s:message code='CNF.SAVE' />";
		showMsgBox("CNF", message, 'SaveRow');
		
	} ).show();

	// 폼초기화 버튼 Event Bind
	$("#btnReset").click( function(){ 
		resetForm($("form#frmDetail"));
		resetForm($("form#frmDbDetail"));
// 		resetForm($("form#frmVrtDetail"));
// 		$('#frmDetail')[0].reset();
// 		$('#frmDbDetail')[0].reset();
// 		$('#frmVrtDetail')[0].reset(); 
		
	} );

	$("#btnGridSave1").click( function(){ 
		$("#btnGridSave").click(); 
	} );
	

	$("#btnGridSave2").click( function(){ 
		$("#btnGridSave").click(); 
	} );
	
	
	$("#btnReset1").click( function(){ 
		$("#btnReset").click(); 
	} );
	$("#btnReset2").click( function(){ 
		$("#btnReset").click(); 
	} );
	
	
	$("#valid_bizrule").show();
	$("#buttonRqst").show();
	$("#buttonRqstDbDetail").show();
	$("#buttonRqstVrtDetail").show();
	
});

$(window).on('load',function() {
 	initGrid();
	var rqststep = $("#mstFrm #rqstStepCd").val();
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	//if($("#mstFrm #aprLvl").val() > 0 ){
	//if($("#mstFrm #rqstStepCd").val() != "A" ){
		setDispRqstMainButton(rqststep, grid_sheet);
	//}
	
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
	//dtl폼의 readonly속성 해제/적용 및 required옵션 설정 수행...
	readonlyCheck(rqststep);
	
	$("#isNew").val("I");
// 	loadDetail();

	doAction("Search");
});



$(window).resize(function() {
	//grid_sheet.FitColWidth();
});

function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);

        var headers = [
                    {Text:"<s:message code='DQ.HEADER.BIZRULE.RQST'/>"
                    	,Align:"Center"}                    	
                ];
                /* No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|"
                   	+"업무영역ID|업무영역명|업무규칙ID|업무규칙명|업무규칙담당자ID|업무규칙담당자|"
                    	+"진단대상ID|진단대상명|테이블명|컬럼명|품질지표ID|품질지표명|중요정보항목ID|중요정보항목명|사용여부|설명|건수SQL|분석SQL|"
                    	+"검증대상DBID|검증대상DB명|검증테이블명|검증컬럼명|검증비교KEY컬럼|검증비교값컬럼|검증JOIN방식|"
                    	+"건수SQL(검증)|분석SQL(검증)|등록번호|등록일련번호|등록요청자 */

        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:1},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",		Align:"Center", Edit:0},	
					
                    {Type:"Text",   	Width:100,  SaveName:"bizAreaId",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   	Width:150,  SaveName:"bizAreaLnm",    	Align:"Center", Edit:0, Hidden:0, KeyField:1}, 
                    {Type:"Text",   	Width:100,  SaveName:"brId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:150,  SaveName:"brNm",   	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,  SaveName:"brCrgpUserId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"brCrgpUserNm",   	Align:"Left", Edit:0, Hidden:0, KeyFiled:1},
                    
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbSchId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"dbSchPnm",   	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"dbcTblNm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"dbcColNm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:70,   SaveName:"dqiId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"dqiLnm", 	Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:70,   SaveName:"ctqId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"ctqLnm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:60,   SaveName:"useYn", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:40,   SaveName:"goal",        Align:"Right",  Edit:0}, 
                    {Type:"Float", 		Width:60,  	SaveName:"weight",		Align:"Right", 	 Edit:1, Format:"0.00"},
                    {Type:"Text",   	Width:100,   SaveName:"objDescn", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"cntSql", 	Align:"Center", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"anaSql", 	Align:"Center", Edit:0, Hidden:0, KeyField:1},
                    
                    {Type:"Text",   	Width:50,   SaveName:"tgtDbConnTrgId", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"tgtDbConnTrgPnm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"tgtDbSchId", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"tgtDbSchPnm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"tgtDbcTblNm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"tgtDbcColNm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"tgtKeyDbcColNm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"tgtKeyDbcColVal", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   	Width:70,   SaveName:"tgtVrfJoinCd", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"tgtCntSql", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"tgtAnaSql", 	Align:"Center", Edit:0, Hidden:1},
                    
                    {Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Left", Edit:0 ,Hidden:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
//         SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});
        
        InitComboNoMatchText(1, "");
      
        SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
		SetColProperty("vrfCd", ${codeMap.vrfCdibs});
		SetColProperty("rvwStsCd", ${codeMap.rvwStsCdibs});
		SetColProperty("tgtVrfJoinCd", ${codeMap.tgtVrfJoinCdibs});
        
        //FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        //SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
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
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
    		
    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
    			$("#btnRegRqst").show();
    		}
    		setDispRqstMainButton($("#mstFrm #rqstStepCd").val(), grid_sheet);
			doAction("Search");
			break;
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
	    		json2formmapping ($("#mstFrm"), res.resultVO);

	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
	    		setDispRqstMainButton($("#mstFrm #rqstStepCd").val(), grid_sheet);
				doAction("Search");    		
	    	} 
			
			break;
			
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/dq/bizrule/bizrule_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

//업무규칙 상세 팝업
function brDtlPopup(param){
	
  	var url = '<c:url value="/dq/bizrule/popup/bizrule_dtl_pop.do"/>';

	var popwin = OpenModal(url+"?"+param, "bizruleDtlPop",  1300, 400, "yes"); 
	
	popwin.focus();
}

function doAction(sAction)
{
        
    switch(sAction)
    {
	    case "Search":
			var param = $("#mstFrm").serialize();
	    	grid_sheet.DoSearch("<c:url value="/dq/bizrule/getbizrulerqstlist.do" />", param);
	    	break;
	    	
	    case "LoadExcel":  //엑셀업로드 팝업 호출
// 			grid_sheet.LoadExcel();
			var url = "<c:url value="/dq/bizrule/popup/bizrule_xls.do" />";
// 			var xlspopup = OpenWindow(url ,"bizrulexls","1000","500","yes");
			openLayerPop(url, 800, 600);
			
			break;
	        
		case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'업무규칙등록요청'});
			break;
			
		case "bizrulePopup":
			var url = '<c:url value="/dq/bizrule/popup/bizrule_pop.do"/>';
// 			var param = $("form#frmInput").serialize();
			doAction("New");

			var param = "sflag=CHG"; 
// 			var popwin = OpenModal(url+"?"+param, "bizrulePop",  1000, 600, "no");
// 			popwin.focus();
			openLayerPop(url, 800, 600, param);
			break;
            
  		case "New": //새건 추가
  			
  			$("#isNew").val("I");
  			$("#selectRow").val("");
  			$('#frmDetail')[0].reset();
  			$('#frmDbDetail')[0].reset();
//   			$('#frmVrtDetail')[0].reset();
  			$('#bizrule_sel_title').html(null);
  			//$("#bizAreaLnm").focus();
  			
  			break;
  			
  		//등록가능 일 경우 WAM 영역 저장
  			//차후 결재로직 개발 완료 후 요청 구현
		case "submitBizruleLst":  
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
		        
			break;

		case "Delete" :
			//테크박스가 입력상태인 경우 삭제...
			if(!grid_sheet.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			
			//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
			ibsSaveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
			
			var url = "<c:url value="/dq/bizrule/delBizruleList.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			//폼 초기화
        	$('#frmDetail')[0].reset();
  			$('#frmDbDetail')[0].reset();
//   			$('#frmVrtDetail')[0].reset();
			
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
			
			var url = "<c:url value="/dq/bizrule/approveBizrule.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
  			
            
  		case "SaveRow" :
  			var selRow = 0;
			
			//선택된 행이 있는지 확인하여 있으면 해당셀에 삽입, 없으면 신규로 행을 추가한다...
			if($("#selectRow").val() == "" && $("#isNew").val() == "I") {
				var nrow = grid_sheet.DataInsert(-1);
				form2ibsmapping(nrow, $("#frmDetail"), grid_sheet);
				form2ibsmapping(nrow, $("#frmDbDetail"), grid_sheet);
// 				form2ibsmapping(nrow, $("#frmVrtDetail"), grid_sheet);
				grid_sheet.SetCellValue(nrow, "ibsStatus", "I");
				selRow = -1;
			} else {
				form2ibsmapping($("#selectRow").val(), $("#frmDetail"), grid_sheet);
				form2ibsmapping($("#selectRow").val(), $("#frmDbDetail"), grid_sheet);
// 				form2ibsmapping($("#selectRow").val(), $("#frmVrtDetail"), grid_sheet);
				grid_sheet.SetCellValue($("#selectRow").val(), "ibsStatus", "U");
				selRow = $("#selectRow").val();
			}
			
			var ibsSaveJson = grid_sheet.GetSaveJson(0);

			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/bizrule/regBizruleRqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			param += '&saveFlag=1&selRow=' + selRow;
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
    }
}

function readonlyCheck(rqstStepCd) {
	if (rqstStepCd == "N" || rqstStepCd == "S") {
		$("input[type=text]").attr('readOnly', false);
		$("textarea").attr('readOnly', false);
		$("select").attr('disabled', false);
	} else {
		$("input[type=text]").attr('readOnly', true);
		$("textarea").attr('readOnly', true);
		$("select").attr('disabled', true);
		
		$("#rvwStsCd").attr('disabled', false);
		$("#rvwConts").attr('readOnly', false);
	}
	
	$("#bizAreaLnm").parents("tr").find('th:first-child').attr('class', 'th_require');
	$("#brNm").parents("tr").find('th:first-child').attr('class', 'th_require');
	$("#dqiLnm").parents("tr").find('th:first-child').attr('class', 'th_require');
	$("#dbConnTrgPnm").parents("tr").children('th').attr('class', 'th_require');
// 	$("#dbcTblNm").parents("tr").find('th:first-child').attr('class', 'th_require');
// 	$("#dbcColNm").parents("tr").find('th:first-child').attr('class', 'th_require');
	$("#cntSql").parents("tr").children('th').attr('class', 'th_require');
	$("#brCrgpUserNm").parents("tr").children('th').attr('class', 'th_require');
// 	$("#anaSql").parents("tr").find('th:first-child').attr('class', 'th_require');
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
	
	$("#isNew").val("U");
	$("#selectRow").val(grid_sheet.GetSelectRow());
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="BZWR.RGR.NM"/>' + ' : ' + param.brNm;  //업무규칙명
	$('#bizrule_sel_title').html(tmphtml);
	
	ibs2formmapping(row, $("form#frmDetail"), grid_sheet);
	ibs2formmapping(row, $("form#frmDbDetail"), grid_sheet);
// 	ibs2formmapping(row, $("form#frmVrtDetail"), grid_sheet);
}

function  grid_sheet_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function  grid_sheet_OnSearchEnd (code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//grid_sheet.FitColWidth();
		//조회 성공...
		doAction("New");
	}
}


</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="BZWR.RULE.REG.DEMD" /></div><!--업무규칙 등록요청-->

	</div>
</div>
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
<div class="selected_title_area">
    <div class="selected_title" id="bizrule_sel_title"> <span></span></div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1"><a href="#tabs-1" id="tab-1"><s:message code="BSIC.INFO"/></a></li><!--기본정보-->
	    <li id="tab-2"><a href="#tabs-2" id="tab-2"><s:message code="DIAG.INFO"/></a></li><!--진단정보-->
	    <li id="tab-rqstvrf"><a href="#tabs-rqstvrf"><s:message code="VRFC.RSLT" /></a></li><!--검증결과-->
	    <li id="tabs-rqstchg"><a href="#tabs-4"><s:message code="CHG.ITEM" /></a></li><!--변경항목-->
	  </ul>
	  	<div id="tabs-1">
  			<%@include file="bizrule_detail.jsp" %>
	  	</div>
	  	<div id="tabs-2">
  			<%@include file="bizrule_dbDetail.jsp" %>
	  	</div>
		<div id="tabs-rqstvrf">
			<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	  	</div>
	  	<div id="tabs-4">
		<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
	  	</div>
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
	<!-- 비교검증정보 tab-3 : bizrule_vrtDetail.jsp -->
	

<form name="frmHidden" id="frmHidden">
	<input type="hidden" id="selectRow" name="selectRow"/>
	<input type="hidden" id="isNew" name="isNew"/>
</form>

<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />

<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>
</body>

</html>