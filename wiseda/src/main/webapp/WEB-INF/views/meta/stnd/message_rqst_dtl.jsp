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
<title><s:message code="MSG.REG.DTL.INFO" /></title> <!-- 메시지등록상세정보 -->

<script type="text/javascript">
//var dmnginfotpJson = ${codeMap.dmnginfotp} ;
//var infotpinfolstJson = ${codeMap.infotpinfolst} ;

//도메인그룹 selectbox 
//var bscLvl = parent.bscLvl;
//var selectBoxId = parent.selectBoxId;
//var selectBoxNm = parent.selectBoxNm;
//var firstSelectBoxId = selectBoxId.split("|");
//var firstSelectBoxNm = selectBoxNm.split("|");

$(document).ready(function(){
	$("#frmInput #useYn").val('${result.useYn}');
    $("#rvwStsCd").val('${result.rvwStsCd}');
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
			msgCd       : "required",
			msgConts 	: "required",
			bizDivCd 	: "required",
			typDivCd 	: "required",
		},
		messages: {
			rqstDcd		: requiremessage,
			msgCd	    : requiremessage,
			msgConts	: requiremessage,
			bizDivCd	: requiremessage,
			typDivCd	: requiremessage,
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	$("#divInputBtn").show();
	//alert("조회완료");
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=progrmFileNm]").attr('readonly', true);
// 	}
	
	
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
		
		//논리명기준구분, 물리명기준구분 검증....
		/*if ($("#frmInput #sditmLnm").val() != $("#frmInput #dmnLnm").val() ) {
			if (isBlankStr($("#frmInput #lnmCriDs").val()) || isBlankStr($("#frmInput #pnmCriDs").val()) ) {
// 			alert("표준항목논리명과 도메인논리명이 틀릴경우 논리명기준구분과 물리명기준구분을 작성해야 합니다.");
			showMsgBox("ERR", message);
			return false;
			}
		}*/
		
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
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
	
	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	$("#frmInput #bizDivCd").val('${result.bizDivCd}');
	$("#frmInput #typDivCd").val('${result.typDivCd}');
	//$("#frmInput #encYn").val('${result.encYn}');
	

	
// 	$("#frmInput #stwdLnm").focus();

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();

	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
	$("#typDivCd").change(function(){
		messageCodeGen();
		//alert("dd");
	});
	$("#bizDivCd").change(function(){
		messageCodeGen();
		//alert("dd");
	});
});	


/**** 메시지코드 생성 함수 */
function messageCodeGen(){
	var typDivCdtmp = $("#typDivCd").val();
	var bizDivCdtmp = $("#bizDivCd").val();
	var rqststep = $("#mstFrm #rqstStepCd").val();

	if(rqststep =='N'||rqststep =='S'){
	   $("#msgCd").val("M"+typDivCdtmp+bizDivCdtmp);
	}
}

</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
	
		<form id="frmInput" name="frmInput" method="post">
 	  <input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
 	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" > 
 	  <input type="hidden" id="sysDivCd" name ="sysDivCd" value = "M">
		<fieldset>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

		<div style="clear:both; height:10px;"><span></span></div>
			<div class="stit"><s:message code="MSG.INFO" /></div> <!-- 메시지정보 -->
 		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="MSG.INFO" /></legend> <!-- 메시지정보 -->
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.INFO' />"> <!-- 메시지정보 -->
				<caption><s:message code="MSG.INFO.INPT" />	</caption> <!-- 메시지 정보입력 -->
				<colgroup>
				<col style="width:15%;" />
				<col style="width:35%;" />
				<col style="width:15%;" />
				<col style="width:35%;" />
				</colgroup>
				<tbody>
				<tr>
				<tr>
						<th scope="row" class="th_require"><label for="typDivCd"><s:message code="PTRN.DV.CD" /></label></th> <!-- 유형구분코드 -->
						<td>
							<!--  <input type="text" id="typDivCd" name="typDivCd" class="wd200" value="" />-->
							<select id="typDivCd" name="typDivCd">
							  <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.msgPtrnDvcd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
							</select>
							
						</td>
						<th scope="row" class="th_require"><label for="bizDivCd"><s:message code="BZWR.DV.CD" /></label></th> <!-- 업무구분코드 -->
						<td>
							<select id="bizDivCd" name="bizDivCd">
							  <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.bizDivCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
							</select>
						</td>
				</tr>
				<tr>
						<th scope="row" class="th_require"><label for="msgCd"><s:message code="MSG.CD" /></label></th> <!-- 메시지코드 -->
						<td>
							<input type="text" id="msgCd" name="msgCd" class="wd200" value="${result.msgCd}"  />
						</td>
							<th scope="row" class="row"><label for="useYn"><s:message code="USE.YN" /></label></th> <!-- 사용여부 -->
						<td>
							<select id="useYn"  name="useYn" value="${result.useYn}">
							       <option value="Y">Y</option>
							       <option value="N">N</option>
							</select>
						</td>
				 </tr>
				 <tr>
						<th scope="row" class="th_require"><label for="msgConts"><s:message code="MSG.CNTN" /></label></th> <!-- 메시지내용 -->
						<td colspan="3" ><input type="text" id="msgConts" name="msgConts" value="${result.msgConts}" class="wd98p" />
				 </tr>
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
		<div class="tb_comment"><s:message  code='REQ.MSG.COMM' /></div> 
		<div style="clear:both; height:10px;"><span></span></div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
		<!-- 입력폼 끝 -->
	</div>
</body>
</html>
