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
<title>메시지등록상세정보</title>

<script type="text/javascript">
var bizDtlsJson = ${codeMap.bizDtls} ;

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
// 			errTlgLanDscd		: "required",
// 			errTpCd   			: "required",
			msgTyp 				: "required",
			msgDit 				: "required",
			bizDit 				: "required",
// 			stdErrCdYn 			: "required",
// 			errCd	 			: "required",
			isdErrCasCts		: "required",
			tskDmn				: "required",
			mngUser				: "required",
		},
		messages: {
// 			errTlgLanDscd		: requiremessage,
// 			errTpCd   			: requiremessage,
			msgTyp				: requiremessage,
			msgDit				: requiremessage,
			bizDit				: requiremessage,
// 			stdErrCdYn			: requiremessage,
// 			errCd				: requiremessage,
			isdErrCasCts		: requiremessage,
			tskDmn				: requiremessage,
			mngUser				: requiremessage,
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
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
// 		$("form[name=frmInput]")[0].reset();
		resetForm($("form#frmInput"));
		
	});
	
 	//검색버튼 팝업
 	$('#btnactnCdSch').click(function(event){
		var url   = "<c:url value="/meta/stnd/popup/messagecode_pop.do"/>";
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
// 	$("#frmInput #errTlgLanDscd").val('${result.errTlgLanDscd}');
	$("#frmInput #errTpCd").val('${result.errTpCd}');
	$("#frmInput #msgDit").val('${result.msgDit}');
// 	$("#frmInput #msgTyp").val('${result.msgTyp}');
// 	$("#frmInput #bizDit").val('${result.bizDit}');
// 	$("#frmInput #stdErrCdYn").val('${result.stdErrCdYn}');
	

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();

	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
// 	$("#typDivCd").change(function(){
// 		messageCodeGen();
// 		//alert("dd");
// 	});
// 	$("#bizDivCd").change(function(){
// 		messageCodeGen();
// 		//alert("dd");
// 	});
	
	//달력팝업 추가...
 	$( "#errCdAplDt" ).datepicker();
	
	if($("#frmInput #errCdAplDt").val() == ""){
	 	calClean();
	}
	
// 	if($("#frmInput #bizDit").val() == ""){
// 		$("#frmInput #bizDit").val('COR');
// 	}
	create_selectbox2($("#selectBoxDiv"), 2, "msgTyp|bizDit",null ,false);
	
 	double_select(bizDtlsJson, $("#msgTyp"));
 	$('select', $("#msgTyp").parent()).change(function(){
 		double_select(bizDtlsJson, $(this));
 	});
	if("${result.msgTyp}" != ""){
		setBizDit();
	}
});	


//업구구분 셋팅
function setBizDit(){
$("#frmInput #msgTyp").val('${result.msgTyp}');
$("#frmInput #msgTyp").change();
$("#frmInput #bizDit").val('${result.bizDit}');
$("#frmInput #bizDit").change();
}

// 달력 초기화
function calClean(){
	//날짜 초기화
	$( "#errCdAplDt" ).val(''); 
	
	//오늘 날짜를 구해서 EndDate에 셋팅한다.
	var curDt = new Date();
	$( "#errCdAplDt" ).val(converDateString(curDt));
}

//팝업 리턴값 처리....
function returnCodePop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #actnCd").val(retjson.actnCd);
	
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
			<div class="stit">메시지정보</div>
 		<div style="clear:both; height:5px;"><span></span></div>
		<legend>메시지정보</legend>
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메시지정보">
				<caption>메시지 정보입력	</caption>
				<colgroup>
				<col style="width:15%;" />
				<col style="width:35%;" />
				<col style="width:15%;" />
				<col style="width:35%;" />
				</colgroup>
				<tbody>
					<tr>
<!-- 						<th scope="row" class="th_require"><label for="errTlgLanDscd">오류전문언어구분코드</label></th> -->
<!-- 						<td> -->
<%-- 							<select id="errTlgLanDscd" name="errTlgLanDscd"> --%>
<!-- 							  <option value="">선택</option> -->
<%-- 							<c:forEach var="code" items="${codeMap.errTlgLanDscd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 							</select> --%>
<!-- 						</td> -->
<!-- 						<th scope="row" class="th_require"><label for="errTpCd">오류유형코드</label></th> -->
<!-- 						<td> -->
<%-- 							<select id="errTpCd" name="errTpCd"> --%>
<!-- 							  <option value="">선택</option> -->
<%-- 							<c:forEach var="code" items="${codeMap.errTpCd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 							</select> --%>
<!-- 						</td> -->
						<th scope="row" class="th_require"><label for="bizDits">메시지유형 / 업무구분</label></th>
						<td>
							<div id="selectBoxDiv"> <span></span></div>
<%-- 							<select id="msgTyp" name="msgTyp"> --%>
<!-- 							  <option value="">선택</option> -->
<%-- 							<c:forEach var="code" items="${codeMap.msgTyp}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 							</select> --%>
							
						</td>
						<th scope="row" class="th_require"><label for="msgDit">메시지구분</label></th>
						<td>
							<select id="msgDit" name="msgDit">
							  <option value="">선택</option>
							<c:forEach var="code" items="${codeMap.msgDit}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
							</select>
							
						</td>
					</tr>
					<tr>
<!-- 						<th scope="row" class="th_require"><label for="bizDit">업무구분</label></th> -->
<!-- 						<td> -->
<%-- 							<select id="bizDit" name="bizDit"> --%>
<!-- 							  <option value="">선택</option> -->
<%-- 							<c:forEach var="code" items="${codeMap.bizDit}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 							</select> --%>
							
<!-- 						</td> -->
<!-- 						<th scope="row" class="th_require"><label for="stdErrCdYn">표준오류코드여부</label></th> -->
<!-- 						<td> -->
<%-- 							<select id="stdErrCdYn" name="stdErrCdYn"> --%>
<!-- 							  <option value="">선택</option> -->
<%-- 							<c:forEach var="code" items="${codeMap.stdErrCdYn}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 							</select> --%>
<!-- 						</td> -->
						<th scope="row" ><label for="stdErrCd">오류코드</label></th>
						<td>
							<input type="text" id="errCd" name="errCd" class="wd200" value="${result.errCd}"  readonly/>
<!-- 							<button class="btnSearchPop" type="button" id="btnErrCdSch"	name="btnErrCdSch">검색</button> -->
						</td>
						<th scope="row"><label for="actnCd">조치코드</label></th>
						<td >
							<input type="text" id="actnCd" name="actnCd" class="wd200" value="${result.actnCd}"  />
							<button class="btnSearchPop" type="button" id="btnactnCdSch"	name="btnactnCdSch">검색</button>
						</td>
					</tr>
					<tr>
	                    <th scope="row" class="th_require">담당파트</th> 
	 					<td>
	 						<input id="tskDmn" name="tskDmn" type="text" class="wd200" value="${result.tskDmn}" />
	 					</td>
	                    <th scope="row" class="th_require">담당자명</th> 
	 					<td>
	 						<input id="mngUser" name="mngUser" type="text" class="wd200" value="${result.mngUser}" />
	 					</td>
	 				</tr>
					<tr>
						<th scope="row" class="th_require"><label for="isdErrCasCts">전산메시지오류원인내용</label></th>
						<td colspan="3" ><input type="text" id="isdErrCasCts" name="isdErrCasCts" value="${result.isdErrCasCts}" class="wd98p" />
					</tr>
<!-- 					<tr> -->
<!-- 						<th scope="row" ><label for="osdErrCasCts">고객메시지오류원인내용</label></th> -->
<%-- 						<td colspan="3" ><input type="text" id="osdErrCasCts" name="osdErrCasCts" value="${result.osdErrCasCts}" class="wd98p" /> --%>
<!-- 					</tr> -->
					<tr>
	                    <th scope="row">오류코드적용일자</th> 
	 					<td>
	 						<input id="errCdAplDt" name="errCdAplDt" type="text" class="wd200" value="${result.errCdAplDt}" />
	 					</td>
	                    <th scope="row">AS-IS오류코드</th> 
	 					<td>
	 						<input id="stsyErrCd" name="stsyErrCd" type="text" class="wd200" value="${result.stsyErrCd}" />
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
