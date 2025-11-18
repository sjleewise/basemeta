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
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			progrmFileNm	: "required",
			progrmStrePath 	: "required",
			url				: "required"
		},
		messages: {
			progrmFileNm	: requiremessage,
			progrmStrePath 	: requiremessage,
			url				: requiremessage
		}
	});
	
	//alert("조회완료");
	if ("U" == $("#saction").val()) {
		//alert("업데이트일경우");
		$("input[name=progrmFileNm]").attr('readonly', true);
	}
	
	
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
	
	$("#frmInput input[name=progrmFileNm]").focus();


});
</script>
</head>
<body>
   <div class="stit_02"><s:message code="PGM.DTL" /></div> <!-- 프로그램상세 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
    <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
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
        <th><s:message code="PGM.NM" /></th> <!-- 프로그램명 -->
        <td colspan="3">
        <input type="text" name="progrmFileNm" class="wd300" value="${result.progrmFileNm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="STRG.PATH" /></th> <!-- 저장경로 -->
        <td colspan="3">
        <input type="text" name="progrmStrePath" class="wd300" value="${result.progrmStrePath}" >
        </td>
      </tr>
      <tr>
        <th<s:message code="PGM.KRN.NM" /></th> <!-- 프로그램한글명 -->
        <td colspan="3">
        <input type="text" name="progrmKoreanNm" class="wd300" value="${result.progrmKoreanNm}" >
        </td>
      </tr>
      <tr>
        <th>URL</th>
        <td colspan="3">
        <input type="text" name="url" class="wd300" value="${result.url}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="PGM.TXT" /></th> <!-- 프로그램설명 -->
        <td colspan="3">
        <input type="text" name="progrmDc" class="wd300" value="${result.progrmDc}" >
        </td>
      </tr>
    </table>
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