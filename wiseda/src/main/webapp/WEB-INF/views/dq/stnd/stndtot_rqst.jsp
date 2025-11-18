<!DOCTYPE html>
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
<title><s:message code="STRD.DATA.DEMD" /></title> <!-- 표준데이터요청 -->
<style type="text/css">
	#divTabs-rqstvrf {display: block; margin-top:440px;}
</style>

<script type="text/javascript">

var grid_name;

$(document).ready(function() {
	
	//탭 초기화....
	//$( "#tabs" ).tabs();
	
	$( "#divTabs-rqstvrf" ).tabs();
	
	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val("${waqMstr.bizDtlCd}");
	
	$("[id$='-${waqMstr.bizDtlCd}'] a").click();
	
	$( "#tab-SDITM a" ).click(function(){
		mstFrmReset("SDITM");
	});
	$( "#tab-DMN a" ).click(function(){
		mstFrmReset("DMN");
	});
	$( "#tab-STWD a" ).click(function(){
		mstFrmReset("STWD");
	});
	
	$("#Msthidden").hide();

	
	// 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	//엑셀 LOAD
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
	
	//추가
    $("#btnNew").click( function(){
    	doAction("New");
    } );
	
	//조회
    $("#btnSearch").click( function(){
    	doAction("Search"); 
    } ).show();
	
	//변경대상 추가
	$("#btnChangAdd").click(function(){
		doAction("AddWam");
	}).show();
	
	//저장
    $("#btnSave").click( function(){
    	doAction("Save"); 
    } ).show();
	
    //삭제
    $("#btnDelete").click( function(){
    	doAction("Delete"); 
    } ).show();
    
    
    
    //화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/stnd/stndtot_rqst.do" />';
    } );
    
		
 // 결재 Event Bind
	$("#btnRegRqst").click(function(){
		//등록가능한지 확인한다.vrfCd = 1
		var regchk = grid_name.FindText("vrfCd", "<s:message code='REG.POSB' />");/*등록가능*/
		
		var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
		var param = $("#mstFrm").serialize();				
		var ibsSaveJson = grid_name.GetSaveJson(1);	
		
		
		for(var i = 1 ; i <= grid_name.GetDataLastRow(); i++) {
			if(grid_name.GetCellValue(i,'vrfCd') != '1') {
				showMsgBox("ERR", "검증이 되지 않은 행이 존재합니다. 검증 결과를 확인하세요.");
				return;
			}
		}
		
		
		if(bizDtlCd == "SDITM"){	
			var url = '<c:url value="/dq/stnd/approveStndItem.do"/>';
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
		}else if(bizDtlCd == "DMN"){ 
			var url = '<c:url value="/dq/stnd/approveStndDmn.do"/>';
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
		}else if(bizDtlCd == "STWD"){ 
			var url = '<c:url value="/dq/stnd/approveStndWord.do"/>';
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
		}
		
		
		
	}).show();
	
});

$(window).on('load',function() {
	$("#btnRegRqst").hide();

	//프로파일별 그리드명 셋팅
	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
	if(bizDtlCd == "SDITM"){
		grid_name = grid_SDITM;
	}else if(bizDtlCd == "DMN"){
		grid_name = grid_DMN;
	}else if(bizDtlCd == "STWD"){
		grid_name = grid_STWD;
	}
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
// 	if(rqststep !=  "A" ){
// 	if($("#mstFrm #aprLvl").val() > 0 ){
		setDispRqstMainButton(rqststep, grid_name);
// 	}

	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));

	doAction("Search"); 
	
});


$(window).resize(function() {
});

//요청정보재조회
function getMstFrm(){
	
	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
	if(bizDtlCd == "SDITM"){
		var urls = '<c:url value="/dq/stnd/getitemrqstlist.do"/>';
	}else if(bizDtlCd == "DMN"){
		var urls = '<c:url value="/dq/stnd/getdmnrqstlist.do"/>';
	}else if(bizDtlCd == "STWD"){
		var urls = '<c:url value="/dq/stnd/getstwdrqstlist.do"/>';
	}
	
	var param = $('form[name=mstFrm]').serialize();
	ajax2Json(urls, param, setMstFrm);
}

//재조회 된 요청정보 셋팅
function setMstFrm(data){
	json2formmapping($("form[name=mstFrm]"), data);
}

//텝 클릭시 요청정보 재조회
//단 등록완료 후 재조회 시 재조회 요청정보 셋팅
function mstFrmReset(bizDtlCd){
	$("form[name=mstFrm] #bizDtlCd").val(bizDtlCd);
	if("${waqMstr.bizDtlCd}" == $("form[name=mstFrm] #bizDtlCd").val() ){
		$("form[name=mstFrm]")[0].reset();
		$("form[name=mstFrm] #bizDcd").val("${waqMstr.bizDcd}");
		$("form[name=mstFrm] #rqstStepCd").val("${waqMstr.rqstStepCd}");
		$("form[name=mstFrm] #bizDtlCd").val("${waqMstr.bizDtlCd}");
	}else{
		//요청번호 신규 생성
		getMstFrm();
	}
}


function doAction(sAction)
{
	//프로파일별 그리드명 셋팅
	var bizDtlCd = $("form[name=mstFrm] #bizDtlCd").val();
	
	if(bizDtlCd == "SDITM"){
		grid_name = grid_SDITM;
	}else if(bizDtlCd == "DMN"){
		grid_name = grid_DMN;
	}else if(bizDtlCd == "STWD"){
		grid_name = grid_STWD;
	}
	
    switch(sAction)
    {
    	case "New":  //그리드추가
    		grid_name.DataInsert(0);
        	break;

		case "AddWam": //기존 표준단어 추가
			var url = "";

			if(bizDtlCd == "SDITM"){
				url = '<c:url value="/dq/stnd/popup/stnditem_pop.do"/>';
			}else if(bizDtlCd == "DMN"){
				url = '<c:url value="/dq/stnd/popup/stnddmn_pop.do"/>';
			}else if(bizDtlCd == "STWD"){
				url = '<c:url value="/dq/stnd/popup/stndword_pop.do"/>';
			}
			var param = "?popRqst=Y"
			var popup = OpenWindow(url+param,"AddWam","800","600","yes");
		
			break;
    	case "LoadExcel":  //엑셀업로드
    		grid_name.LoadExcel({Mode:'HeaderMatch', Append:1});
        	break;
        
    	case "Down2Excel":  //엑셀다운로드
    		if(grid_name.GetTotalRows() == 0 ){
    			grid_name.DataInsert(0);
    		}
    		grid_name.Down2Excel({HiddenColumn:1, Merge:1});
        	break;
        
		case "Search":
			
			//프로파일별 url 셋팅
			var url = "";
			if(bizDtlCd == "SDITM"){
				url = '<c:url value="/dq/stnd/getitemrqstlist.do"/>';
			}else if(bizDtlCd == "DMN"){
				url = '<c:url value="/dq/stnd/getdmnrqstlist.do"/>';
			}else if(bizDtlCd == "STWD"){
				url = '<c:url value="/dq/stnd/getstwdrqstlist.do"/>';
			}
			
			var param = $("#mstFrm").serialize();
			grid_name.DoSearch(url, param);
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
// 			console.log(param);
			getRqstVrfLst(param);
			break;
			
    	case "Save":  //검증
    		//저장 대상의 데이터를 Json 객체로 반환한다.
			ibsSaveJson = grid_name.GetSaveJson(0);
    	
    		//2. 필수입력 누락인 경우
			if (ibsSaveJson.Code == "IBS010") return;
			
			if(ibsSaveJson.data.length == 0){
				showMsgBox("INF", "<s:message code="ERR.CHKSAVE" />");
				return;
			}

			//프로파일별 url 셋팅
			var url = "";
			if(bizDtlCd == "SDITM"){
				url = '<c:url value="/dq/stnd/regitemrqstlist.do"/>';
			}else if(bizDtlCd == "DMN"){
				url = '<c:url value="/dq/stnd/regdmnrqstlist.do"/>';
			}else if(bizDtlCd == "STWD"){
				url = '<c:url value="/dq/stnd/regStndWordRqstlist.do"/>';
			}
			
			
			var param = $('form[name=mstFrm]').serialize();
	        IBSpostJson2(url, ibsSaveJson, param, ibscallback);
	        
	        $("#btnRegRqst").show();
        	break;
        	
    	case "Delete" :
			//체크박스가 입력상태인 경우 삭제...
			if(!grid_name.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
        	
			var url = "";
			if(bizDtlCd == "SDITM"){
				url = '<c:url value="/dq/stnd/delSditmrqstlist.do"/>';
			}else if(bizDtlCd == "DMN"){
				url = '<c:url value="/dq/stnd/deldmnrqstlist.do"/>';
			}else if(bizDtlCd == "STWD"){
				url = '<c:url value="/dq/stnd/delstwdrqstlist.do"/>';
			}
			
			//삭제로직 김경택
			//모든 Row에서 Check 된 것을 저장한 뒤에
			//(if)체크 된 것 중 입력중인 내용만 있으면 Row만 삭제하고 BackEnd 타지않음
			//(else if)체크 된 것 중 vrfCd! = 4 인 것만 있으면, 즉 한번이라도 검증을 탔으면 Front에서  Param 수집 후 Front상의 Row를 삭제한 뒤 BackEnd로 넘어가서 WAQ에서 삭제
			//(else)체크 된 것 중 입력중인 상태와 vrfCd(검증상태)가 4(검증전)가 아닌 것이 동시에 있으면, 
			//입력중인 상태의 Row를 먼저 삭제하고, Param 수집 후 Front에서 Row를 삭제한 뒤에 BackEnd로 넘어가서 WAQ에서 삭제함
			//이 모든 과정은 화면 새로고침을 하지 않음.
			var empty_Row_Location = new Array();
			var vrfed_Row_Location = new Array();
			
			for(var i = 1 ; i <= grid_name.GetDataLastRow(); i++) {
				if(grid_name.GetCellValue(i,'ibsCheck') == '1' && grid_name.GetCellValue(i,'vrfCd') == '4') {
					empty_Row_Location.push(i)
				}else if(grid_name.GetCellValue(i,'ibsCheck') == '1' && grid_name.GetCellValue(i,'vrfCd') != '4'){
					vrfed_Row_Location.push(i)
				} 
			}

			if (empty_Row_Location.length != 0 && vrfed_Row_Location.length == 0){
				var delete_Row = '' 
										
				for(var i = 1; i <=empty_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+empty_Row_Location[i-1]
					}else{
						delete_Row += '|'+empty_Row_Location[i-1]
					}
				}
				grid_name.RowDelete(delete_Row, 0);
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
			}else if(empty_Row_Location.length == 0 && vrfed_Row_Location.length != 0){
				var DelJson = grid_name.GetSaveJson(0, "ibsCheck");
				var param = $("#mstFrm").serialize();
				
				var delete_Row = '' 
					
				for(var i = 1; i <=vrfed_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+vrfed_Row_Location[i-1]
					}else{
						delete_Row += '|'+vrfed_Row_Location[i-1]
					}
				}
				grid_name.RowDelete(delete_Row, 0)
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
				IBSpostJson2(url, DelJson, param, ibscallback)
				
			}else{
				var delete_Row = '' 
					
				for(var i = 1; i <=empty_Row_Location.length; i++){
					if (delete_Row == ''){
						delete_Row += ''+empty_Row_Location[i-1]
					}else{
						delete_Row += '|'+empty_Row_Location[i-1]
					}
				}
				grid_name.RowDelete(delete_Row, 0);
				
				var DelJson = grid_name.GetSaveJson(0, "ibsCheck");
				var param = $("#mstFrm").serialize();
				
				var delete_Row = ''
				
				for(var i = 1; i <=grid_name.GetDataLastRow(); i++){
					if(grid_name.GetCellValue(i,'ibsCheck') == '1') {
						if (delete_Row == ''){
							delete_Row += ''+i
						}else{
							delete_Row += '|'+i
						}
					}
				}
				grid_name.RowDelete(delete_Row, 0);				
				
				empty_Row_Location.clear
				vrfed_Row_Location.clear
				IBSpostJson2(url, DelJson, param, ibscallback);
			}
    }       
}
 

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
	    		//doAction("Search"); 		
	    	} 
			break;
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
	    		 
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
	    		//등록요청 버튼 활성화
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
  					$("#btnRegRqst").show();
	    		} 
  				doAction("Search"); 
	    	} 
			
			break;
				
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/dq/stnd/stndtot_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PROF.EXCL.REG" /></div><!--프로파일 엑셀등록-->

	</div>
</div>


<div style="clear:both; height:10px;"><span></span></div>

<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />


<!-- 			<div class="bt03"> -->
<%-- 			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="WHL.INQ" /></button> <!-- 전체조회 --> --%>
<%--                 <button class="btn_rqst_new" id="btnRqstNew" name="btnRqstNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                          --%>
<!-- 				  <ul class="add_button_menu" id="addButtonMenu"> -->
<%-- 				    <li class="btn_new" id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 --> --%>
<%-- 				    <li class="btn_excel_load" id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 올리기 --> --%>
<!-- 				  </ul>          -->
<!-- 			    <button class="btn_save" id="btnSave" 	name="btnSave">검증</button> 검증  -->
<%-- 			    <button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 --> --%>
<!-- 			    <button class="btn_check" id="btnCheck" 	name="btnCheck">검증</button>  -->
<%-- 			    <button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst"><s:message code="STRG" /></button> <!-- 저장 -->  --%>
<%-- 			    <button class="btn_rqst_new2" id="btnBlank" name="btnBlank" style="display:none;"><s:message code="NEW.LORQ" /></button> <!-- 신규요청서 -->  --%>
<!-- 			</div> -->

<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
	<div id="tabs">
	  <ul>
	    <li id="tab-SDITM"><a href="#tabs-SDITM"><s:message code="STRD.TERMS"/></a></li><!--표준용어-->
	    <li id="tab-DMN"><a href="#tabs-DMN"><s:message code="DMN"/></a></li><!-- 도메인-->
	    <li id="tab-STWD"><a href="#tabs-STWD"><s:message code="STRD.WORD"/></a></li><!--표준단어-->
	  </ul>
	  <div id="tabs-SDITM">
			<div id="detailInfoSDITM"><%@include file="exl/stnditem_exl.jsp" %></div>
	  </div>
	  <div id="tabs-DMN">
			<div id="detailInfoDMN"><%@include file="exl/stnddmn_exl.jsp" %></div>
	  </div>
	  <div id="tabs-STWD">
			<div id="detailInfoSTWD"><%@include file="exl/stndstwd_exl.jsp" %></div>
	  </div>	  
	 </div>

	<div style="clear:both; height:20px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	
	<div id="divTabs-rqstvrf">
		<ul>
			<li id="tabs-rqstvrf"><a href="#tab-rqstvrf"><s:message code="VRFC.RSLT" /></a></li><!--검증결과-->
		</ul>
		<div id="tab-rqstvrf">
			<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
		</div>
	</div>
	
	
	<div id = "Msthidden">
	<form name="mstFrm" id="mstFrm">
				<!-- 전체승인 및 전체반려시 승인/반려 및 반려 사유를 마스터에서 처리한다. -->
				<input type="hidden" name="rvwStsCd" id="rvwStsCd">
				<input type="hidden" name="rvwConts" id="rvwConts">
				<input type="hidden" name="rqstUserId" id="rqstUserId" value="${waqMstr.rqstUserId}" />
				
			    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
					<caption><s:message code="SUBJ.TRRT.INFO" /></caption> <!-- 주제영역정보 -->
					<colgroup>
<%-- 					<col style="width:15%;" /> --%>
<%-- 					<col style="width:85%;" /> --%>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><label for="rqstNo"><s:message code="DEMD.NO" /></label></th> <!-- 요청번호 -->
							<td><input type="text" name="rqstNo" id="rqstNo" class="wd98p" value="${waqMstr.rqstNo}" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstNm"><s:message code="LORQ.NM" /></label></th> <!-- 요청서명 -->
							<td><input type="text" name="rqstNm" id="rqstNm" class="wd98p" value="${waqMstr.rqstNm}" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="bizDcd"><s:message code="BZWR.DSTC" /></label></th> <!-- 업무구분 -->
							<td>
							<!-- bizDcd를 selectBox로만 받을경우 stndtot_rqst에서 null로 인식하여 문제발생. 그래서 hidden값으로 추가 -->
							<input type="hidden" id="bizDcd" 	name="bizDcd" 	value="${waqMstr.bizDcd}" />
							<input type="text" id="bizDcdNm" 	name="bizDcdNm" 	value="${waqMstr.bizDcdNm}" />
<%-- 							<select id="bizDcd" class="" name="bizDcd"> --%>
<%-- 							<c:forEach var="code" items="${codeMap.bizDcd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 					 		</select> --%>
							</td>
						</tr>
						<tr style="display:none">
							<th scope="row"><label for="bizDtlCd"><s:message code="BZWR.DSTC.DTL" /></label></th> <!-- 업무구분상세 -->
							<td><input type="text" name="bizDtlCd" id="bizDtlCd" class="wd98p" value="" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstStepCd"><s:message code="DEMD.PRGS.STS" /></label></th> <!-- 요청진행상태 -->
							<td>
							<input type="hidden" id="rqstStepCd" 	name="rqstStepCd" 	value="${waqMstr.rqstStepCd}" />
							<input type="text" id="rqstStepCdNm" 	name="rqstStepCdNm" 	value="${waqMstr.rqstStepCdNm}" />
<%-- 							<select id="rqstStepCd" class="" name="rqstStepCd"> --%>
<%-- 							<c:forEach var="code" items="${codeMap.rqstStepCd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 					 		</select> --%>
					 		</td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstResn"><s:message code="DEMD.RSN" /></label></th> <!-- 요청사유 -->
							<td><input type="text" name="rqstResn" id="rqstResn" class="wd98p" value="${waqMstr.rqstResn}" /></td>
						</tr>
						<tr style="display:none">
							<th scope="row"><label for="rqstResn"><s:message code="APRL.STEP" /></label></th> <!-- 결재단계 -->
							<td><input type="text" id="aprLvl" 	name="aprLvl" 	value="${waqMstr.aprLvl}" /></td>
						</tr>
					</tbody>
				</table>
			</form>
	</div>

</body>
</html>