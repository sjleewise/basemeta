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
<title><s:message code="CD.REG.DTL.INFO" /></title> <!-- 코드등록상세정보 -->

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSchIdCodeTsf} ;
//도메인그룹 selectbox 
//var bscLvl = parent.bscLvl;
//var selectBoxId = parent.selectBoxId;
//var selectBoxNm = parent.selectBoxNm;
//var firstSelectBoxId = selectBoxId.split("|");
//var firstSelectBoxNm = selectBoxNm.split("|");

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
			msgCd       : "required",
			msgConts 	: "required",

		},
		messages: {
			rqstDcd		: requiremessage,
			msgCd	    : requiremessage,
			msgConts	: requiremessage,
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	//$("#divInputBtn").show();
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
	
 	double_select(connTrgSchJson, $("#frmInput #tgtDbConnTrgId"));
   	$('select', $("#frmInput #tgtDbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});

	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	$("#frmInput #useYn").val('${result.useYn}');
	 $("#rvwStsCd").val(grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "rvwStsCd"));
	if(grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "rvwConts")!=-1&&grid_sheet.GetSelectRow()!=-1){
	   	$("#rvwConts").val(grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "rvwConts")); 
	}
    $("#frmInput #tgtDbConnTrgPnm").val('${result.tgtDbConnTrgPnm}');
    $("#frmInput #tgtDbSchPnm").val('${result.tgtDbSchPnm}');
    $("#frmInput #cdValTypCd").val('${result.cdValTypCd}');
    

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();

	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
 
});	




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
			<div class="stit"><s:message code="CD.INFO" /></div> <!-- 코드정보 -->
 		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="CD.INFO" /></legend> <!-- 코드정보 -->
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CD.INFO' />"> <!-- 코드정보 -->
				<caption><s:message code="CD.INFO.INPT" />	</caption> <!-- 코드 정보입력 -->
				<colgroup>
				<col style="width:15%;" />
				<col style="width:35%;" />
				<col style="width:15%;" />
				<col style="width:35%;" />
				</colgroup>
				<tbody>
				
				<tr>
						<th scope="row" ><label for="tgtDbConnTrgId"><s:message code="TARG.DB.SCHEMA.NM" /></label></th> <!-- 타겟DB/스키마명 -->
						<td>
		                        <input type ="text" id="tgtDbConnTrgPnm" class="wd100" name="tgtDbConnTrgPnm" value ="${result.tgtDbConnTrgPnm}" disabled />
					            <input type ="text" id="tgtDbSchPnm" class="wd100" name="tgtDbSchPnm" value ="${result.tgtDbSchPnm}" disabled />
					           
						</td>
						<th scope="row" ><label for="dmnLnm"><s:message code="DMN.NM" /></label></th> <!-- 도메인명 -->
						<td>
								<input type="text" id="dmnLnm" name="dmnLnm" class="wd200" value="${result.dmnLnm}" readonly />
							
						</td>
				</tr>
				<tr>
						<th scope="row"><label for="cdValTypCd"><s:message code="CD.PTRN" /></label></th> <!-- 코드유형 -->
						<td colspan ="3">
							<select id="cdValTypCd" name = "cdValTypCd" value = "${result.cdValTypCd}" disabled>
							<option><s:message code="CHC" /></option> <!-- 선택 -->
							<option value = "O"><s:message code="SMPL.CD" /></option> <!-- 단순코드 -->
							<option value = "C"><s:message code="COEX.CD" /></option> <!-- 복잡코드 -->
							</select>
						</td>
						
						
				 </tr>
				<tr>
						<th scope="row"><label for="cdVal"><s:message code="CD.VAL" /></label></th> <!-- 코드값 -->
						<td>
							<input type="text" id="cdVal" name="cdVal" class="wd200" value="${result.cdVal}" readonly />
						</td>
							<th scope="row" class="row"><label for="cdValNm"><s:message code="CD.VAL.NM" /></label></th> <!-- 코드값명 -->
						<td>
							<input type="text" id="cdValNm" name="cdValNm" class="wd200" value="${result.cdValNm}" readonly />
						</td>
				 </tr>
				 
				 <tr>
				 		<th scope="row"><label for="dispOrd"><s:message code="DISPLAY.SQNC" /></label></th> <!-- 표시순서 -->
						<td><input type="text" id="dispOrd" name="dispOrd" value="${result.dispOrd}"  readonly />
						<th scope="row" ><label for="useYn"><s:message code="USE.YN" /></label></th> <!-- 사용여부 -->
						<td >	<select id="useYn"  name="useYn" value="${result.useYn}" disabled>
							       <option value=""></option>
							       <option value="Y">Y</option>
							       <option value="N">N</option>
							</select>
						</td>
					
					
				 </tr>
				 <tr style ="display:none;">
				 <th scope="row" class="row"><label for="dmnDscd"><s:message code="LC.CD" /></label></th> <!-- 대분류코드 -->
						<td>
							<input type="text" id="dmnDscd" name="dmnDscd" class="wd200" value="${result.dmnDscd}" readonly />
						</td>
						<th scope="row" ><label for="uppCdVal"><s:message code="UPRN.CD.VAL" /></label></th> <!-- 상위코드값 -->
						<td><input type="text" id="uppCdVal" name="uppCdVal" value="${result.uppCdVal}" class="wd98p" readonly/>
				 </tr>
				 </tr>
				    <tr style ="display:none;">
						<th scope="row"><label><s:message code="ETC" /></label></th> <!-- 기타 -->
						<td colspan="3" >
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						  <caption><label><s:message code="ETC" /></caption> <!-- 기타 -->
				          <colgroup>
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				          </colgroup>
						  <tbody>
						   <th><s:message code="ETC.1" /> </th> <!-- 기타1 -->
						   <td><input type="text" id="vvNote1" name="vvNote1" value="${result.vvNote1}" class="wd100" readonly /></td>
						   <th><s:message code="ETC.2" /> </th> <!-- 기타2 -->
						   <td><input type="text" id="vvNote2" name="vvNote2" value="${result.vvNote2}" class="wd100" readonly /></td>
						   <th><s:message code="ETC.3" /> </th> <!-- 기타3 -->
						   <td><input type="text" id="vvNote3" name="vvNote3" value="${result.vvNote3}" class="wd100" readonly /></td>
						   <th><s:message code="ETC.4" /> </th> <!-- 기타4 -->
						   <td><input type="text" id="vvNote4" name="vvNote4" value="${result.vvNote4}" class="wd100" readonly /></td>
						   <th><s:message code="ETC.5" /> </th> <!-- 기타5 -->
						   <td><input type="text" id="vvNote5" name="vvNote5" value="${result.vvNote5}" class="wd100" readonly /></td>
						  </tbody>
						</table>
						</td>
					</tr>
				    <tr style ="display:none;">
						<th scope="row"><label><s:message code="ETC.NM" /></label></th> <!-- 기타명 -->
						<td colspan="3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" >
						 <caption><label><s:message code="ETC.NM" /></caption> <!-- 기타명 -->
				          <colgroup>
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				              <col style="width:5%;" />
				              <col style="width:15%;" />
				          </colgroup>
						<tbody>
						   <th><s:message code="ETC.NM.1" /></th> <!-- 기타1명 -->
						   <td><input type="text" id="vvNoteNm1" name="vvNoteNm1" value="${result.vvNoteNm1}" class="wd100" readonly /></td>
						   <th><s:message code="ETC.NM.2" /></th> <!-- 기타2명 -->					   
						   <td><input type="text" id="vvNoteNm2" name="vvNoteNm2" value="${result.vvNoteNm2}" class="wd100" readonly /></td>
						   <th><s:message code="ETC.NM.3" /></th> <!-- 기타3명 -->					   
						   <td><input type="text" id="vvNoteNm3" name="vvNoteNm3" value="${result.vvNoteNm3}" class="wd100" readonly /></td>
						   <th><s:message code="ETC.NM.4" /></th> <!-- 기타4명 -->						   
						   <td><input type="text" id="vvNoteNm4" name="vvNoteNm4" value="${result.vvNoteNm4}" class="wd100" readonly /></td>
						   <th><s:message code="ETC.NM.5" /></th> <!-- 기타5명 -->				   
						   <td><input type="text" id="vvNoteNm5" name="vvNoteNm5" value="${result.vvNoteNm5}" class="wd100" readonly /></td>
						</tbody>
						</table>
						</td>
					</tr>
					 <tr style ="display:none;">
						<th scope="row"><label for="objDescn"><s:message code="OUTL.1" /></label></th> <!-- 적요1 -->
						<td colspan="3"><textarea class="wd98p" id="outlCntn1" name="outlCntn1" readonly>${result.outlCntn1}</textarea></td>
					</tr>
					 <tr style ="display:none;">
						<th scope="row"><label for="objDescn"><s:message code="OUTL.2" /></label></th> <!-- 적요2 -->
						<td colspan="3"><textarea class="wd98p" id="outlCntn2" name="outlCntn2" readonly>${result.outlCntn2}</textarea></td>
					</tr>
                    <tr>
						<th scope="row"><label for="objDescn"><s:message code="RMRK" /></label></th> <!-- 비고 -->
						<td colspan="3"><textarea class="wd98p" id="objDescn" name="objDescn" readonly>${result.objDescn}</textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
		</fieldset>
		</form>
		<!-- 입력폼 끝 -->
		<!-- 	* 항목을 ;로 분리하여 입력한후 '분리'버튼을 클릭하시면 자동으로 분리되어 단어구분에 입력됩니다. 마지막단어에는 도메인으로 입력하셔야 됩니다. (예: 가상;계약자;계좌번호) -->
		<div class="tb_comment"></div> 
		<div style="clear:both; height:10px;"><span></span></div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
		<!-- 입력폼 끝 -->
	</div>
</body>
</html>
