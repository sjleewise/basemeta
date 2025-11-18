<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title>표준단어등록상세정보</title> -->

<script type="text/javascript">
$(document).ready(function(){
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			rqstDcd		: "required",
			appStwdLnm		: "required",
			appStwdPnm 	: "required"
// 			,wdDcd		: "required"
// 			sysCdYn		: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			appStwdLnm		: requiremessage,
			appStwdPnm 	: requiremessage
// 			,wdDcd		: requiremessage
// 			sysCdYn		: requiremessage
		}
	});
	
	
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
	
	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	if('${result.rqstDcd}'!=""){$("#frmInput #rqstDcd").val('${result.rqstDcd}')};
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	//단어구분
// 	$("#frmInput #wdDcd").val('${result.wdDcd}');
	
	$("#frmInput #appStwdLnm").focus();

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
	<form id="frmInput" name="frmInput" method="post">
<%-- 	  <input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" > --%>
<%-- 	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" > --%>
	  <input type="hidden" id="rqstSno" name="rqstSno" value="" >
	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="" >
<!-- 	  <input type="text" id="bizDtlCd" name="bizDtlCd" 	 value="STWD"> -->
<%-- 	  <input type="hidden" id="stwdId" name="stwdId" value="${result.stwdId}" > --%>
	<fieldset>
	
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	
	<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit"><s:message code="APP.WORD.INFO" /></div> <!-- APP단어정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
	<legend><s:message code="APP.WORD.INFO" /></legend> <!-- APP단어정보 -->
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.WORD.INFO' />"> <!-- 표준단어정보 -->
			<caption><s:message code="APP.WORD.INFO.INPT" />	</caption> <!-- APP단어 정보입력 -->
			<colgroup>
			<col style="width:15%;" />
			<col style="width:85%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require"><label for="appStwdLnm"><s:message code="APP.WORD.LGC.NM" /></label></th> <!-- APP단어논리명 -->
					<td><input type="text" id="appStwdLnm" name="appStwdLnm" class="wd300" value="${result.appStwdLnm }" /></td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="appStwdPnm"><s:message code="APP.WORD.PHYC.NM" /></label></th> <!-- APP단어물리명 -->
					<td><input type="text" id="appStwdPnm" name="appStwdPnm"  class="wd300" value="${result.appStwdPnm}" /></td>
				</tr>
				<tr>
					<th scope="row"><label for="engMean"><s:message code="ENSN.MEAN" /></label></th> <!-- 영문의미 -->
					<td><input type="text" id="engMean" name="engMean"  class="wd300" value="${result.engMean }" /></td>
				</tr>
				<tr>
					<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
					<td><textarea id="objDescn" name="objDescn"  class="wd98p" >${result.objDescn }</textarea></td>
				</tr>
			</tbody>
		</table>
	</div>
	</fieldset>
	</form>
	<!-- 	* 단어영문명에는 단어영문의 약어를 입력하셔야 하고, 영문의미는 단어의 영문설명으로 입력하셔야 합니다. -->
	<div class="tb_comment">
<%-- 	<s:message  code='REQ.STWD.COMM' /> --%>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<!-- 입력폼 끝 -->
<!-- </body> -->
<!-- </html> -->
