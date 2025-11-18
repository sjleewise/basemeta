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
<title>ASIS유효갑등록상세정보</title>

<script type="text/javascript">

$(document).ready(function(){
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
			actnCd				: "required",
			errTlgLanDscd		: "required",
			actnMsgCtnt			: "required",
		},
		messages: {
			actnCd				: requiremessage,
			errTlgLanDscd		: requiremessage,
			actnMsgCtnt			: requiremessage,
		}
	});
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	$("#divInputBtn").show();
	//alert("조회완료");
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
	
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
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
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		resetForm($("form#frmInput"));
		
	});
	
	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();

	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
	//달력팝업 추가...
 	$( "#lastChgDt" ).datepicker();
	
	if($("#frmInput #lastChgDt").val() == ""){
	 	calClean();
	}
 	
 	//검색버튼 팝업
 	$('#btnasisColNmSch').click(function(event){
		var url   = "<c:url value="/meta/model/popup/asispdmcol_pop.do"/>";
// 		var param = "?scrnDcd=Y";
		var param = "";
		openLayerPop (url, 800, 600, param);
// 		var popup = OpenWindow(url+param,"wamMsgSearch","800","600","yes");
// 		popup.focus();
	});
 	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	
});	


// 달력 초기화
function calClean(){
	//날짜 초기화
	$( "#lastChgDt" ).val(''); 
	
	//오늘 날짜를 구해서 EndDate에 셋팅한다.
	var curDt = new Date();
	$( "#lastChgDt" ).val(converDateString(curDt));
}

//도메인 팝업 리턴값 처리....
function returnStdPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #errCd").val(retjson.errCd);
	
}

function returnPdmColPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
// 	$("#frmInput #subjId").val(retjson.subjId);
// 	$("#frmInput #subjPnm").val(retjson.subjPnm);
// 	$("#frmInput #subjLnm").val(retjson.subjLnm);
	
// 	$("#frmInput #pdmTblId").val(retjson.pdmTblId);
// 	$("#frmInput #pdmTblPnm").val(retjson.pdmTblPnm);
	$("#frmInput #asisTblNm").val(retjson.pdmTblLnm);
	
// 	$("#frmInput #pdmColId").val(retjson.pdmColId);
// 	$("#frmInput #pdmColPnm").val(retjson.pdmColPnm);
	$("#frmInput #asisColNm").val(retjson.pdmColLnm);
	
	$("#frmInput #asisSystemNm").val(retjson.systemNm);
	$("#frmInput #asisDbNm").val(retjson.dbmsNm);
	
}

/**** 메시지코드 생성 함수 */
// function messageCodeGen(){
// 	var typDivCdtmp = $("#typDivCd").val();
// 	var bizDivCdtmp = $("#bizDivCd").val();
// 	var rqststep = $("#mstFrm #rqstStepCd").val();

// 	if(rqststep =='N'||rqststep =='S'){
// 	   $("#msgCd").val("M"+typDivCdtmp+bizDivCdtmp);
// 	}
// }

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
			<div class="stit">AS-IS유효값정보</div>
 		<div style="clear:both; height:5px;"><span></span></div>
		<legend>메시지정보</legend>
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메시지정보">
				<caption> 정보입력	</caption>
				<colgroup>
				<col style="width:15%;" />
				<col style="width:35%;" />
				<col style="width:15%;" />
				<col style="width:35%;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" ><label for="asisSystemNm">시스템명</label></th>
						<td>
							<input type="text" id="asisSystemNm" name="asisSystemNm" class="wd200" value="${result.asisSystemNm}"  />
						</td>
						<th scope="row" ><label for="asisDit">시스템구분</label></th>
						<td>
							<input type="text" id="asisDit" name="asisDit" class="wd200" value="${result.asisDit}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" ><label for="asisGroupNm">ASIS그룹명</label></th>
						<td>
							<input type="text" id="asisGroupNm" name="asisGroupNm" class="wd200" value="${result.asisGroupNm}"  />
						</td>
						<th scope="row" class="th_require"><label for="asisDbNm">ASISDB명</label></th>
						<td>
							<input type="text" id="asisDbNm" name="asisDbNm" class="wd200" value="${result.asisDbNm}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" class="th_require"><label for="asisTblNm">테이블명</label></th>
						<td>
							<input type="text" id="asisTblNm" name="asisTblNm" class="wd200" value="${result.asisTblNm}"  />
						</td>
						<th scope="row" class="th_require"><label for="asisColNm">컬럼명</label></th>
						<td>
							<input type="text" id="asisColNm" name="asisColNm" class="wd200" value="${result.asisColNm}"  />
							<button class="btnSearchPop" type="button" id="btnasisColNmSch"	name="btnasisColNmSch">검색</button>
						</td>
					</tr>
					<tr>
						<th scope="row" ><label for="dfdColNm">참조테이블명</label></th>
						<td>
							<input type="text" id="dfdColNm" name="dfdColNm" class="wd200" value="${result.dfdColNm}"  />
						</td>
						<th scope="row" ><label for="dfdColValue">참조컬럼명</label></th>
						<td>
							<input type="text" id="dfdColValue" name="dfdColValue" class="wd200" value="${result.dfdColValue}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" class="th_require"><label for="codeValue">코드값</label></th>
						<td>
							<input type="text" id="codeValue" name="codeValue" class="wd200" value="${result.codeValue}"  />
						</td>
						<th scope="row" ><label for="codeNm">코드명</label></th>
						<td>
							<input type="text" id="codeNm" name="codeNm" class="wd200" value="${result.codeNm}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" ><label for="codeDispNum">표시순번</label></th>
						<td>
							<input type="text" id="codeDispNum" name="codeDispNum" class="wd200" value="${result.codeDispNum}"  />
						</td>
						<th scope="row" ><label for="objDescn">설명</label></th>
						<td>
							<input type="text" id="objDescn" name="objDescn" class="wd200" value="${result.objDescn}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" ><label for="appTeamNm">응용팀</label></th>
						<td>
							<input type="text" id="appTeamNm" name="appTeamNm" class="wd200" value="${result.appTeamNm}"  />
						</td>
						<th scope="row" ><label for="appChrgNm">KB담당자</label></th>
						<td>
							<input type="text" id="appChrgNm" name="appChrgNm" class="wd200" value="${result.appChrgNm}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" ><label for="appSiNm">SI담당자</label></th>
						<td>
							<input type="text" id="appSiNm" name="appSiNm" class="wd200" value="${result.appSiNm}"  />
						</td>
						<th scope="row" ><label for="tsfTeamNm">전환팀</label></th>
						<td>
							<input type="text" id="tsfTeamNm" name="tsfTeamNm" class="wd200" value="${result.tsfTeamNm}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" ><label for="tsfChrgNm">전환담당자</label></th>
						<td>
							<input type="text" id="tsfChrgNm" name="tsfChrgNm" class="wd200" value="${result.tsfChrgNm}"  />
						</td>
						<th scope="row" ><label for="lastChgDt">최종수정일자</label></th>
						<td>
							<input type="text" id="lastChgDt" name="lastChgDt" class="wd200" value="${result.lastChgDt}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" ><label for="addNote1">비고1</label></th>
						<td>
							<input type="text" id="addNote1" name="addNote1" class="wd200" value="${result.addNote1}"  />
						</td>
						<th scope="row" ><label for="addNote2">비고2</label></th>
						<td>
							<input type="text" id="addNote2" name="addNote2" class="wd200" value="${result.addNote2}"  />
						</td>
					</tr>
					<tr>
						<th scope="row" ><label for="addNote3">비고3</label></th>
						<td>
							<input type="text" id="addNote3" name="addNote3" class="wd200" value="${result.addNote3}"  />
						</td>
						<th scope="row" ><label for="addNote4">비고4</label></th>
						<td>
							<input type="text" id="addNote4" name="addNote4" class="wd200" value="${result.addNote4}"  />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		</fieldset>
		</form>
		<!-- 입력폼 끝 -->
<%-- 		<div class="tb_comment"><s:message  code='REQ.MSG.COMM' /></div>  --%>
		<div style="clear:both; height:10px;"><span></span></div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
		<!-- 입력폼 끝 -->
	</div>
</body>
</html>
