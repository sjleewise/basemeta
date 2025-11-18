<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>

<html>
<head>
<title><s:message code="MATP.DTL.INFO" /></title> <!-- 수도권매립지 상세정보 -->
<script type="text/javascript">
$(document).ready(function(){
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
// 	var numbermessage = "<s:message code="VALID.NUMBER" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			slcGlossaryNm			: "required",
		},
		messages: {
			slcGlossaryNm			: requiremessage,
		}
	});
	
	//조회 결과값 초기 셋팅
// 	$("#frmInput #symnDcd").val('');

	
// 	alert($("#ibsStatus").val());
	if ("U" == $("#ibsStatus").val()) {
// 		//alert("업데이트일경우");
		$("form#frmInput input[name=slcGlossaryNm]").attr('readonly', true);
	}
	
		
	//폼 저장 버튼 초기화...
	$('#btnfrmSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("form#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	
	
	//폼 초기화 버튼 초기화...
	$('#btnfrmReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
		resetForm($("form#frmInput"));
// 		$("form[name=frmInput]")[0].reset();
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
	
	$("#frmInput input[name=slcGlossaryNm]").focus();


});
</script>
</head>
<body>
   <div class="stit"><s:message code="MATP.TECH.TERMS.DTL.INFO" /></div> <!-- 수도권매립지 기술용어 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
<%--    	<input type="hidden" id="saction" name="saction" value="${saction}" > --%>
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="SIMIWORD.PRHB.WORD.INQ.REVS.REG" /> <!-- 유사어/금지어 조회/수정/등록 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
      <tr>
        <th><s:message code="TERMS.CLS.NM" /></th> <!-- 용어분류명 -->
        <td colspan="3">
        <input type="text" name="glossaryClasNm" class="wd300" value="${result.glossaryClasNm}" >
        </td>
      </tr>
      <tr>
        <th class="th_require" ><s:message code="MATP.TERMS.NM" /></th> <!-- 수도권매립지용어명 -->
        <td colspan="3">
        <input type="text" name="slcGlossaryNm" class="wd300" value="${result.slcGlossaryNm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="MATP.TERMS.ENSN.NM" /></th> <!-- 수도권매립지용어영문명 -->
        <td colspan="3">
        <input type="text" name="slcGlossaryEngNm" class="wd300" value="${result.slcGlossaryEngNm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="TERMS.ORGN.TXT" /></th> <!-- 용어출처설명 -->
        <td colspan="3">
        <input type="text" name="glossarySrcExpl" class="wd300" value="${result.glossarySrcExpl}">
        </td>
      </tr>
      <tr>
        <th><s:message code="TERMS.TXT" /></th> <!-- 용어설명 -->
        <td colspan="3">
        <textarea name="glossaryExpl" rows="10" class="wd500" >${result.glossaryExpl}</textarea>
        </td>
      </tr>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div id="divFrmBtn" style="text-align: center;">
		<button id="btnfrmSave" name="btnfrmSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>


</body>
</html>