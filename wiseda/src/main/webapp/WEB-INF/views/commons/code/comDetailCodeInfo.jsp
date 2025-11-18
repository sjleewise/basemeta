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
<title><s:message code="PGM.DTL.INFO" /></title> <!-- 프로그램상세정보 -->
<script type="text/javascript">
$(document).ready(function(){
	
	//======================================================
    // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
    //======================================================
    //initSearchPop2();	//검색 팝업 초기화
    initSearchButton();	//검색 버튼 초기화
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			codeId	: "required",
			code 	: "required",
			codeNm 	: "required",
			useAt	: "required"
		},
		messages: {
			codeId	: requiremessage,
			code 	: requiremessage,
			codeNm 	: requiremessage,
			useAt	: requiremessage
		}
	});
	
	$('form[name=frmInput] select[name=useAt]').val('${result.useAt}');
	$('form[name=frmInput] select[name=codeId]').val('${result.codeId}');

	//alert("조회완료");
	if ("U" == $("#saction").val()) {
		//alert("업데이트일경우");
// 		$("form[name=frmInput] select[name=codeId]").attr('disabled', true);
		$("form[name=frmInput] select[name=codeId] option").not(':selected').attr('disabled', true);
		$("form[name=frmInput] input[name=code]").attr('readonly', true);
	}
	
	
	//프로그램파일명
	//$("input[name=progrmFileNm]").attr('readonly', true);
	
	
	
	//폼 저장 버튼 초기화...
	$('#btnfrmSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnfrmReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
		$("form[name=frmInput]")[0].reset();
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
	

});

</script>
</head>
<body>
   <div class="stit"><s:message code="COM.CD.DTL.INFO" /></div> <!-- 공통코드상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" name="keyId" value="${result.keyId}" >
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0" class="tb_write2" summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
        <tr>
      <th><s:message code="COM.CD" /></th> <!-- 공통코드 -->
        <td colspan="3">
        <select name="codeId">
        	<option value=""><s:message code="MSG.CHC" /></option> <!-- 선택하세요. -->
       		<c:forEach var="code" items="${codeMap.comCodeList}" varStatus="status">
                 <option value="${code.codeCd}">${code.codeLnm}</option>
            </c:forEach>
        </select>
      </td>
      </tr>
      <tr>
        <th><s:message code="COM.DTL.CD" /></th> <!-- 공통상세코드 -->
        <td colspan="3">
        <input type="text" name="code" class="wd300" value="${result.code}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="COM.DTL.CD.NM" /></th> <!-- 공통상세코드명 -->
        <td colspan="3">
        <input type="text" name="codeNm" class="wd300" value="${result.codeNm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="COM.DTL.CD.TXT" /></th> <!-- 공통상세코드설명 -->
        <td colspan="3">
        <textarea name="codeDc" rows="3" class="wd300">${result.codeDc}</textarea>
        </td>
      </tr>
      <tr>
        <th><s:message code="USE.YN"/></th> <!-- 사용여부 -->
        <td colspan="3">
        <select name="useAt">
        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
			<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
        </select>
<%--         <input type="text" name="useAt" class="wd300" value="${result.useAt}" > --%>
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
		<button id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기
	</div>


</body>
</html>