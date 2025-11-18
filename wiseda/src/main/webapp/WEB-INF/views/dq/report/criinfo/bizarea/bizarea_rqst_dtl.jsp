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
<title>표준단어등록상세정보</title>

<script type="text/javascript">
$(document).ready(function(){
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code='VALID.REQUIRED' />";
	//폼검증
	$("#frmInput").validate({
		rules: {			 
			rqstDcd	: "required",
			bizAreaLnm	: "required"
		},
		messages: {
			rqstDcd	: requiremessage,
			bizAreaLnm	: requiremessage
		}
	});
	
	
	//alert("조회완료");
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=progrmFileNm]").attr('readonly', true);
// 	}
	
	$('.btnDelPop').click(function(event){
	    	//브라우저 기본 이벤트 제거...
	    	event.preventDefault();  
	    	$(this).parent().children().val('');
		
	});
	$('.btnSearchPop').click(function(event){
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
 		    	$('#frmInput')[0].reset();
 		    	//팝업 flag 값 설정
				doAction("bizareaPopup", "ADD");
		});
	
	$('.btnSearchUppBizAreaPop').click(function(event){
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
 		    	//팝업 flag 값 설정
				doAction("bizareaPopup", "UPP");
		});
	
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
		var message = "<s:message code='CNF.SAVE' />";
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
		
		$("#frmInput #bizAreaLnm").focus();
		
	});
	
	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');

	
	$("#frmInput #bizAreaLnm").focus();

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
	  <input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" >
<!-- 	  <input type="text" id="bizDtlCd" name="bizDtlCd" 	 value="STWD"> -->
<%-- 	  <input type="hidden" id="stwdId" name="stwdId" value="${result.stwdId}" > --%>
	<fieldset>
	
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	
	<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit"><s:message code="BZWR.AREA1.INFO" /></div><!-- 업무영역정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
	<legend><s:message code="BZWR.AREA1.INFO" /></legend><!-- 업무영역정보 -->
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BZWR.TRRT' />"><!-- 업무영역 -->
					<caption><s:message code="BZWR.AREA1.INFO.WIRTE" /></caption><!-- 업무영역 정보입력 -->
					<!-- 표에서 열의 그룹을 정의 -->
					<colgroup>
					<col style="width:30%;" />
					<col style="width:70%;" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row" class="th_require"><label for="bizAreaLnm"><s:message code="BZWR.TRRT.NM" /></label></th><!-- 업무영역명 -->
							<td>
								<input type="text" id="bizAreaLnm" name="bizAreaLnm" class="wd300"/>
								<button class="btnDelPop" ><s:message code="DEL" /></button><!--삭제-->

        						<button class="btnSearchPop" id="btnSearchPop"><s:message code="INQ" /></button><!--검색-->
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="uppBizAreaLnm"><s:message code="UPRN.BZWR.TRRT.NM"/></label></th><!-- 상위업무영역명 -->
							<td>
								<input type="text" id="uppBizAreaLnm" name="uppBizAreaLnm" class="wd300" />
								<button class="btnDelPop" ><s:message code="DEL" /></button><!--삭제-->

        						<button class="btnSearchUppBizAreaPop" id="btnSearchUppBizAreaPop"><s:message code="INQ" /></button><!--검색-->
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th><!--설명-->

							<td><textarea id="objDescn" name="objDescn" class="wd300"></textarea></td>
						</tr>
					</tbody>
				</table>
	</div>
	</fieldset>
	</form>
	<div class="tb_comment"></div>
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<!-- 입력폼 끝 -->
</body>
</html>
