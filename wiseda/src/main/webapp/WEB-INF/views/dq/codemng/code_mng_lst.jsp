<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="CODE.MNG" /></title>
<!--코드 관리-->


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
			cdRuleNm		: "required",
			dbConnTrgId 	: "required",
			cdClsColNm 		: "required",
			cdClsNmColNm 	: "required",
			cdIdColNm 		: "required",
			cdNmColNm 		: "required",
			cdSql 			: "required",
			cdTypCd 		: "required"
		},
		messages: {
			cdRuleNm		: requiremessage,
			dbConnTrgId 	: requiremessage,
			cdClsColNm 		: requiremessage,
			cdClsNmColNm 	: requiremessage,
			cdIdColNm 		: requiremessage,
			cdNmColNm 		: requiremessage,
			cdSql 			: requiremessage,
			cdTypCd 		: requiremessage
		}
	});
	
	//조회
	$("#btnSearch").click(function(){
		
		$('#frmDetail')[0].reset();
		$('#bizrule_sel_title').html(null);
		
		doAction("Search");
	});
	
	// 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		
		//등록가능한지 확인한다.vrfCd = 1
		//var regchk = grid_sheet.FindText("vrfCd", "<s:message code='REG.POSB' />");/*등록가능*/

		
		/* if(regchk > 0) {
			if($("#mstFrm #aprLvl").val() > 0 ){
				showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'submitBizruleLst');
			}else{
				showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'Approve');
			}
		} else {
			showMsgBox("INF", "<s:message code='ERR.SUBMIT' />");
			return false;
		} */
		
		showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'SaveRow');
		
	}).hide();	
	
	// 등록요청 Event Bind
	$("#btnSave").click(function(){
		showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'AddRow');
	}).hide();	
	
	$("#btnExcelLoad").hide();
	
	//변경대상 추가
	$("#btnChangAdd").click(function(event){
		//팝업 flag 값 설정
		doAction("AddWam");
	});
	
	//화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/bizrule/bizrule_rqst.do" />';
    } );
	//검증버튼
    $("#btnCheck").click( function(){
   		/* var param = "code=Y&row=1&dbConnTrgId="+$("#frmDetail #dbConnTrgId").val();
   		var url = '<c:url value="/dq/criinfo/anatrg/popup/vrfcrule_pop.do" />';
    	var popwin = OpenModal(url+"?"+param, "codePop",  800, 600, "no");
		popwin.focus(); */
		doAction('CheckSQL');
    });
	
	
	
	//$("#btnRegRqst").show();
	$("#btnNewLow").hide();
	//$("#btnNew").hide();
	
	//신규버튼
	$("#btnBlank").click(function(){
		location.href = '<c:url value="/dq/bizrule/bizrule_rqst.do" />';
	});
	
				
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  });
	
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
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		/* if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmDetail #rvwConts").val());
			return;
		} */
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
		//폼 검증...
		$("#tabs #tab-1").click();
		if(!$("#frmDetail").valid()) {
			return false;
		}

		$("#tabs #tab-1").click();

		//저장할래요? 확인창...
		var message = "<s:message code='CNF.SAVE' />";
		showMsgBox("CNF", message, 'AddRow');
		
		
		
	} );

	// 폼초기화 버튼 Event Bind
	$("#btnReset").click( function(){ 
		resetForm($("form#frmDetail"));
 		resetForm($("form#frmHidden"));
//		resetForm($("form#frmVrtDetail"));
		
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
	
	$("#btnGridSave").click( function(){ 
		doAction("AddRow");
	} ).show();
	
	$("#valid_bizrule").show();
	$("#buttonRqst").show();
	$("#buttonRqstDbDetail").show();
	$("#buttonRqstVrtDetail").show();
	
	/* //임시 메뉴목록 등장 함수
	var val = $("#dbConnTrgId option:selected").val();
	var trgId = ${codeMap.devConnTrgSch} ;
	//$("#frmSearch #dbConnTrgId").append('<option value=""></option>');
	
	for(i=0; i<trgId.length; i++) {
		if(trgId[i].upcodeCd == val) {
			$("#frmSearch #dbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
		}
	}
	
	
	$("#frmSearch #dbConnTrgId").change(function() {
		$("#frmSearch #dbSchId").find("option").remove().end();
		var val = $("#dbConnTrgId option:selected").val();

		$("#frmSearch #dbSchId").append('<option value=""><s:message code="CHC" /></option> ');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == val && val!="") {
				$("#frmSearch #dbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
	}); */
	
// 	doAction("Search");
});

$(window).load(function() {
 	initGrid();
	var rqststep = $("#mstFrm #rqstStepCd").val();
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
	//dtl폼의 readonly속성 해제/적용 및 required옵션 설정 수행...
	readonlyCheck(rqststep);
	
	$("#isNew").val("I");
});



$(window).resize(function() {
});

function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);

        var headers = [
                    {Text:"<s:message code='DQ.HEADER.CODE.RQST'/>"
                    	,Align:"Center"}                    	
                ];
                /* No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|"
                   	+"업무영역ID|업무영역명|업무규칙ID|업무규칙명|업무규칙담당자ID|업무규칙담당자|"
                    	+"진단대상ID|진단대상명|테이블명|컬럼명|품질지표ID|품질지표명|중요정보항목ID|중요정보항목명|사용여부|설명|건수SQL|분석SQL|"
                    	+"등록번호|등록일련번호|등록요청자 */
/* No.|상태|선택|등록유형|코드규칙ID|DB정보|진단대상DBMS명|스키마ID|스키마물리명|코드유형|검증코드명|코드생성SQL|분류코드|분류코드명|공통코드|공통코드명|설명 */
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                     
        			// No/상태/선택/등록유형
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:1},
                    {Type:"CheckBox",   Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",      Width:80,   SaveName:"regTypCd",	Align:"Center", Edit:0, Hidden:1},
					
					// 코드규칙ID|DB정보|진단대상DBMS명|스키마ID|스키마물리명
                    {Type:"Text",   	Width:100,  SaveName:"cdRuleId",     Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Combo",   	Width:100,  SaveName:"dbConnTrgId",  Align:"Center", Edit:0, Hidden:0, KeyField:1}, 
                    {Type:"Text",   	Width:100,  SaveName:"dbConnTrgLnm", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"dbSchId", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"dbSchPnm", Align:"Center", Edit:0, Hidden:1},
                    
                    // 코드유형|검증코드명|코드생성SQL|분류코드|분류코드명
                    {Type:"Combo",   	Width:100,  SaveName:"cdTypCd",      Align:"Center", Edit:0, Hidden:0},  
                    {Type:"Text",   	Width:100,  SaveName:"cdRuleNm",   	 Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,  SaveName:"cdSql",   	 Align:"Left", Edit:0, Hidden:0, KeyField:1},
                    {Type:"Text",   	Width:100,  SaveName:"cdClsColNm",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"cdClsNmColNm", Align:"Left", Edit:0, Hidden:1},
                    
                    // 공통코드/공통코드명/설명
                    {Type:"Text",   	Width:50,   SaveName:"cdIdColNm",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"cdNmColNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"objDescn",   	 Align:"Left", Edit:0, Hidden:0}
                    
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

        SetColProperty("cdTypCd", ${codeMap.cdTypCdibs});
        SetColProperty("dbConnTrgId", ${codeMap.connTrgDbmsibs});
        
        InitComboNoMatchText(1, "");
      
        FitColWidth();
        
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
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
		
    		/* if ($("#mstFrm #rqstStepCd").val() == "S")  {
    			$("#btnRegRqst").show();
    		} */
    		//setDispRqstMainButton($("#mstFrm #rqstStepCd").val(), grid_sheet);
			doAction("Search");
			break;
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
 			//showMsgBox("INF", res.RESULT.MESSAGE);
			doAction("Search");
			break;
		
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
			break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
	    		json2formmapping ($("#mstFrm"), res.resultVO);

	    		/* if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		} */
	    		//setDispRqstMainButton($("#mstFrm #rqstStepCd").val(), grid_sheet);
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
	    	
// 			if($("#dbConnTrgId").val() == ""){
				
// 				showMsgBox("ERR", "DBMS정보를 입력하세요.");
// 				return;
// 			}
			
			
			var param = $("#frmSearch").serialize();
	    	grid_sheet.DoSearch("<c:url value="/dq/codemng/getcodelist.do" />", param);
	    	break;
	 
  		case "New": //새건 추가
  			
  			$("#isNew").val("I");
  			$("#selectRow").val("");
  			$('#frmDetail')[0].reset();
  			//$('#frmDbDetail')[0].reset();
//   			$('#frmVrtDetail')[0].reset();
  			$('#bizrule_sel_title').html(null);
  			
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
			
			var url = "<c:url value="/dq/codemng/delcodeList.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			//폼 초기화
        	$('#frmDetail')[0].reset();
			
			break;
			
  		case "AddRow" :
     		//saction (I-입력, U-수정)
     		if(!$("#frmDetail").valid()) {
				return false;
			}
     		
     		var urls = '<c:url value="/dq/codemng/regCode.do"/>';
     		var param = $('form[name=frmDetail]').serialize();
     		param += '&saction=' + $('#isNew').val();
     		ajax2Json2(urls, param, ibscallback);
     		
     		
			break;
  		case "CheckSQL" :
  			if(!$("#frmDetail").valid()) {
  				return false;
  			}
  			
  			var urls = '<c:url value="/dq/codemng/checkSQL.do"/>';
     		var param = $('form[name=frmDetail]').serialize();
     		
//      		console.log(param);
     		
     		ajax2Json2(urls, param, ibscallback);
     		
  			break;
  		   	
   	    case "LoadExcel":  //엑셀업로드 팝업 호출
   	    //엑셀 업로드 기능 주석
//    			var url = "<c:url value="/dq/codemng/popup/code_xls.do" />";
   			openLayerPop(url, 800, 600);
   			
   			break;
   	        
   		case "Down2Excel":  //엑셀내려받기
   			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
   			break;
   			
    			
    }
}

//================================================
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
//================================================
function ibscallbackCheckSQL(res){
    var result = res.RESULT.CODE;
    if(result == 0) {
//     	alert(res.RESULT.MESSAGE);
        		
    	postProcessIBSCheckSQL(res);		
    	
		//공통메세지 팝업 : 성공 메세지...
    	showMsgBox("INF", res.RESULT.MESSAGE);
    
    } else if (result == 401) {
    	//권한이 없어요...
    	showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
    } else {
//     	alsert("저장실패");
		//공통메시지 팝업 : 실패 메세지...
    	showMsgBox("ERR", res.RESULT.MESSAGE);
    }
}

function postProcessIBSCheckSQL(){

	var param = "";

    param +=  "code=Y&row=1";
	param +=  "&dbConnTrgId="+ $("#frmDetail #dbConnTrgId").val();
		
	var url = '<c:url value="/dq/criinfo/anatrg/popup/vrfcrule_pop.do" />';
	
	openLayerPop(url, 800, 700, param);
}


function ajax2Json2(urls, param, callback, syncyn) {
	var ajaxurl = urls;
	var syn = true;
	if (syncyn) syn = syncyn;
	
//	alert(syn);
//	if(param != null && param != "") {
//		ajaxurl = urls + "?" +param;
//	}
//	showMsgBox("PRC", '처리중이니 잠시 기다려 주십시요.');
	
//	setTimeout(function(){
		$.ajax({
			url: ajaxurl,
			async: syn,
			type: "POST",
			data: param,
			dataType: 'json',
		beforeSend: function () {
			//처리중입니다. 메세지
							
			// 처리중이니 잠시 기다려 주십시요.
			showMsgBox("PRC", gMSG_PRC_WAIT);
			
		},
			success: callback
//			error: function (jqXHR, textStatus, errorThrown) {
//				//alert('에러발생...' + textStatus);
//				var res = {RESULT : {CODE:-1, MESSAGE:'시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.'}};
//				ibscallback(res);
//			}
		});
//	}, 10);
	
	
}

function readonlyCheck(rqstStepCd) {
	$("input[type=text]").attr('readOnly', false);
	$("textarea").attr('readOnly', false);
	$("select").attr('disabled', false);
	/* if (rqstStepCd == "N" || rqstStepCd == "S") {
		$("input[type=text]").attr('readOnly', false);
		$("textarea").attr('readOnly', false);
		$("select").attr('disabled', false);
		
	} else {
		
		$("input[type=text]").attr('readOnly', true);
		$("textarea").attr('readOnly', true);
		$("select").attr('disabled', true);
	} */
	
	$("#brNm").parents("tr").find('th:first-child').attr('class', 'th_require');
	$("#dqiLnm").parents("tr").find('th:first-child').attr('class', 'th_require');
	$("#dbConnTrgPnm").parents("tr").children('th').attr('class', 'th_require');
	$("#cntSql").parents("tr").children('th').attr('class', 'th_require');
}




function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	
	$("#isNew").val("U");
	$("#selectRow").val(grid_sheet.GetSelectRow());
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '검증코드명' + ' : ' + param.cdRuleNm;  //업무규칙명
	$('#bizrule_sel_title').html(tmphtml);
	
	ibs2formmapping(row, $("form#frmDetail"), grid_sheet);
	ibs2formmapping(row, $("form#frmDbDetail"), grid_sheet);
	//ibs2formmapping(row, $("form#frmVrtDetail"), grid_sheet);
}

function  grid_sheet_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function  grid_sheet_OnSearchEnd (code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공...
		doAction("New");
	}
}


</script>
</head>
<body>
	<div class="menu_subject">
		<div class="tab">
			<div class="menu_title">
				<s:message code="CODE.MNG" />
			</div>
			<!--업무규칙 등록요청-->

		</div>
	</div>
	<div style="clear: both; height: 5px;">
		<span></span>
	</div>
	
	<!-- 검색조건 입력폼 -->
	<div id="search_div">
	       <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	       <div style="clear:both; height:5px;"><span></span></div>
	       
	       <form id="frmSearch" name="frmSearch" method="post">
	           <fieldset>
	           <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	           <div class="tb_basic2">
	               <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회-->
	
	                  <caption><s:message code="DIAG.TRGT.DBMS"/></caption><!--진단대상DBMS-->
	
	                  <colgroup>
	                  <col style="width:15%;" />
	                  <col style="width:35%;" />
	                  <col style="width:15%;" />
	                  <col style="width:35%;" />
	                  </colgroup>
	                  
	                  <tbody>                            
	                      <tr>                               
	                          <th scope="row"><label for="dbConnTrgId"><s:message code="DIAG.TRGT.DBMS.NM"/></label></th><!--진단대상DBMS명-->
	
	                          <td>
					             <select id="dbConnTrgId"  name="dbConnTrgId" class="wd260">
	                       			<option value=""><s:message code="WHL" /></option><!--전체-->
		
								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
		                     	</select>
	                          </td>
	                          <th scope="row"><label for=cdTypCd>코드유형</label></th>
	                          <td>
	                          		<select id="cdTypCd"  name="cdTypCd" class="wd150">
		                       		<option value=""><s:message code="WHL" /></option><!--전체-->
			
								    <c:forEach var="code" items="${codeMap.cdTypCd}" varStatus="status">
								   	 <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
                     	</select>
	                          </td>
	                      </tr>
<!-- 	                      <tr>                                -->
<!-- 	                          <th scope="row"><label for="cdIdColNm">공통코드</label></th> -->
<!-- 	                          <td colspan="3"> -->
<!-- 	                          		<input type="text" name="cdIdColNm" id="cdIdColNm" class="wd260" /> -->
<!-- 	                          </td> -->
<!-- 	                      </tr> -->
	                  </tbody>
	                </table>   
	           </div>
	           </fieldset>
	           
	       <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
	       </form>
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	       
    <!-- 조회버튼영역  -->
	<%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
	<div class="bt03">
		<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ" /></button> <!-- 전체조회 -->
		<button class="btn_search" id="btnNew" name="btnNew"><s:message code="ADDT" /></button> <!-- 추가 -->  
		<button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
		<%-- <button class="btn_excel_down"  id="btnExcelDown"  name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> --%>
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
		<script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>
	</div>
	<!-- 그리드 입력 입력 -->
	<div style="clear: both; height: 10px;">
		<span></span>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<%-- 	<div class="selected_title_area">
		<div class="selected_title" id="bizrule_sel_title">
			<span></span>
		</div>
	</div> --%>
	<div style="clear: both; height: 5px;">
		<span></span>
	</div>
	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
		<ul>
			<li id="tab-1"><a href="#tabs-1" id="tab-1"><s:message
						code="CODE.DTL.INFO" /></a></li>
			<!--코드상세정보-->
		</ul>
		<div id="tabs-1">
			<%@include file="code_detail.jsp"%>
		</div>
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->



	<form name="frmHidden" id="frmHidden">
		<input type="hidden" id="selectRow" name="selectRow" /> 
		<input type="hidden" id="isNew" name="isNew" />
	</form>

</body>

</html>