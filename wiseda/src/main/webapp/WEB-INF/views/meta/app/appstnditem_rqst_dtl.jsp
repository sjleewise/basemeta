<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="APP.ITEM.REG.DTL.INFO" /></title> <!-- App항목등록상세정보 -->

<script type="text/javascript">

$(document).ready(function(){
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	var shotrequiremessage = "<s:message code="VALID.SHORTREQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			rqstDcd		: "required",
			appSditmLnm	: "required",
			appSditmPnm	: "required",
// 			lnmCriDs	: "required",
// 			dmnLnm	: "required"
// 			lnmCriDs	: "required",
// 			pnmCriDs	: "required"	
		},
		messages: {
			rqstDcd		: requiremessage,
			appSditmLnm	: requiremessage,
			appSditmPnm	: requiremessage,
// 			lnmCriDs	: requiremessage,
// 			dmnLnm	: requiremessage
// 			lnmCriDs	: shotrequiremessage,
// 			pnmCriDs	: shotrequiremessage
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmInput #rvwConts").val());
			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	}).show();
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
// 		$("form[name=frmInput]")[0].reset();
		resetForm($("form#frmInput"));
		/* var row = grid_sheet.GetSelectRow();
		if(row < 1) {
			$("form#frmInput")[0].reset();
		    //선택행 셋팅..
		    var tmptit = "테이블을 선택하세요.";
		    $("#tbl_sle_title").html(tmptit);
		} else {
			tblClick(row);
		} */
		
	});
	
	$('#btnSditmClr').click(function(){
		$("#appSditmLnm").val("");
		$("#appSditmPnm").val("");
		$("#lnmCriDs").val("");
		$("#pnmCriDs").val("");
	});
	
// 	$('#btnDmnClr').click(function(){ $("#dmnLnm").val("");  });

// 	$('#btnDmnSch').click(function(){ setDmn(); });

	
	//APP항목 물리명 검색
    $("#sditmStwdPop").click(function(){
    	var param = $("form#frmInput").serialize();
			param += "&rqstNo="+$("#rqstNo", parent.document).val();
			openLayerPop("<c:url value='/meta/app/sditmdupstwd_pop.do' />", 800, 600, param);
    }).hide();
 
	
	//APP항목분리
	$('#btnSditmDiv').click(function(){
		if($("#appSditmLnm").val() == "") return;
		var vUrl = "<c:url value='/meta/app/getsditmdivjson.do'/>";
		var param = "appSditmLnm=" + $("#appSditmLnm").val();
			param += "&rqstNo="+$("#mstFrm #rqstNo").val();
		ajax2Json(vUrl, param, function(data){
					$("#appSditmLnm").val("");
					$("#appSditmPnm").val("");
					$("#lnmCriDs").val("");
					$("#pnmCriDs").val("");
					$("#appSditmLnm").val(data.appSditmLnm);
					$("#appSditmPnm").val(data.appSditmPnm);
					$("#lnmCriDs").val(data.lnmCriDs);
					$("#pnmCriDs").val(data.pnmCriDs);
					if($("#appSditmPnm").val().indexOf("[D]") > -1) {
						var message = "<s:message  code='ITEM.DUPWORD' />";
						showMsgBox("CNF", message, click_btnSditmGen );
					};
		});

	}).hide();

	//항목자동분할
	$("#btnSditmGen").click(function(){
//			openSearchPop("<c:url value='/meta/stnd/sditmlst_pop.do' />");
			var param = "";
				param += "rqstNo="+$("#mstFrm #rqstNo").val();
			    param += "&appSditmLnm="+$("#appSditmLnm").val();
			openLayerPop ("<c:url value='/meta/app/popup/appstnditemdivision_pop.do' />", 800, 500, param);
// 			var popup = OpenWindow("<c:url value="/meta/stnd/sditmdvcanlst_pop.do"/>"+param,"sditmGen","800","600","yes");
// 			popup.focus();
	}).hide();
	
	
	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	if('${result.rqstDcd}'!=""){$("#frmInput #rqstDcd").val('${result.rqstDcd}')};
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	$("#frmInput #encYn").val('${result.encYn}');
	

	
// 	$("#frmInput #stwdLnm").focus();

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);

});

function click_btnSditmGen () {
	$("#btnSditmGen").click();
}

function click_btnDmnSch () {
	$("#btnDmnSch").click();
}



//항목 자동분할 팝업 리턴값 처리....
function returnItemDivsionPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #appSditmLnm").val(retjson.dicAsmLnm);
	$("#frmInput #appSditmPnm").val(retjson.dicAsmPnm);
	$("#frmInput #lnmCriDs").val(retjson.dicAsmDsLnm);
	$("#frmInput #pnmCriDs").val(retjson.dicAsmDsPnm);
	

}


</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
	
		<form id="frmInput" name="frmInput" method="post">
<%-- 	  <input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" > --%>
<%-- 	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" > --%>
	  <input type="hidden" id="rqstSno" name="rqstSno" value="" >
	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="" >
		<fieldset>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

		<div style="clear:both; height:10px;"><span></span></div>
			<div class="stit"><s:message code="APP.ITEM.INFO" /></div> <!-- APP항목정보 -->
 		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="APP.ITEM.INFO" /></legend> <!-- APP항목정보 -->
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='APP.ITEM.INFO' />"> <!-- APP항목정보 -->
				<caption><s:message code="APP.ITEM.INFO.INPT" />	</caption> <!-- APP항목 정보입력 -->
				<colgroup>
				<col style="width:15%;" />
				<col style="width:35%;" />
				<col style="width:15%;" />
				<col style="width:35%;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" class="th_require"><label for="appSditmLnm"><s:message code="APP.ITEM.LGC.NM" /></label></th> <!-- App항목논리명 -->
						<td>
							<input type="text" id="appSditmLnm" name="appSditmLnm" class="wd200" value="${result.appSditmLnm}"  />
                              	<button class="btnDelPop" type="button" id="btnSditmClr"	name="btnSditmClr"><s:message code="CLR" /></button> <!-- 지우기 -->
                              	<button class="btnSearchPop" type="button" id="btnSditmGen"	name="btnSditmGen"><s:message code="ATMD.DIV" /></button> <!-- 자동분할 -->
                              	<button class="btnSearchPop" type="button" id="btnSditmDiv"	name="btnSditmDiv"><s:message code="SPRT" /></button> <!-- 분리 -->
						</td>
						<th scope="row" class="th_require"><label for="appSditmPnm"><s:message code="APP.ITEM.PHYC.NM" /></label></th> <!-- App항목물리명 -->
						<td ><input type="text" id="appSditmPnm" name="appSditmPnm" value="${result.appSditmPnm}" class="wd200" />
								<button class="btnSearchPop" id="sditmStwdPop"><s:message code="SRCH" /></button></td> <!-- 검색 -->
					</tr>
<!-- 					<tr> -->
<%-- 						<th scope="row" ><label for="lnmCriDs"><s:message code="LGC.NM.BASE.DSTC" /></label></th> <!-- 논리명기준구분 -->  --%>
<%-- 						<td><input type="text" id="lnmCriDs" name="lnmCriDs" class="wd200" value="${result.lnmCriDs}"  readonly/></td> --%>
<%-- 						<th scope="row" ><label for="pnmCriDs"><s:message code="PHYC.NM.BASE.DSTC" /></label></th> <!-- 물리명기준구분 -->  --%>
<%-- 						<td><input type="text" id="pnmCriDs" name="pnmCriDs" class="wd200" value="${result.pnmCriDs}" readonly /></td> --%>
<!-- 					</tr> -->
					<tr>
						<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
						<td colspan="3"><textarea class="wd98p" id="objDescn" name="objDescn" >${result.objDescn}</textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
		</fieldset>
		</form>
		<!-- 입력폼 끝 -->
		<!-- 	* 항목을 ;로 분리하여 입력한후 '분리'버튼을 클릭하시면 자동으로 분리되어 단어구분에 입력됩니다. 마지막단어에는 도메인으로 입력하셔야 됩니다. (예: 가상;계약자;계좌번호) -->
		<div class="tb_comment">
<%-- 		<s:message  code='REQ.ITEM.COMM' /> --%>
		</div>
		<div style="clear:both; height:10px;"><span></span></div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
		<!-- 입력폼 끝 -->
	</div>
</body>
</html>
