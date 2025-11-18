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
			commDcd		: "required",
			commDcdNm 	: "required",
			sysCdYn		: "required"
		},
		messages: {
			commDcd		: requiremessage,
			commDcdNm 	: requiremessage,
			sysCdYn		: requiremessage
		}
	});
	
	//조회 결과값 초기 셋팅
	$("#frmInput #sysCdYn").val('${result.sysCdYn}');
	
	//alert("조회완료");
	if ("U" == $("#saction").val()) {
		//alert("업데이트일경우");
		$("input[name=progrmFileNm]").attr('readonly', true);
	}
	
	
	//폼 저장 버튼 초기화...
	$('#btnfrmSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//공통코드ID를 필수입력항목에서 제외한다.
		grid_sub.SetColProperty("commDcdId", 	{KeyField:0});
    	//저장대상 있는 경우만 가져옴... validation 체크 함...
    	ibsSaveJson = grid_sub.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//     	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...

		//validation 체크 결과가 Message에 담긴다. (예 : KeyFieldError)
    	//alert(ibsSaveJson.Message);
    	if (ibsSaveJson.Message == "KeyFieldError") {
    		//ibs 오류일 경우 강제로 탭을 변경한다.
	    	$("#tab-2 a").click();
    		return false ;
    	}
    	
//     	$("#tabs-1").show();
		//강제로 탭을 클릭하여 폼 내용을 보여준다.
    	$("#tab-1 a").click();
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');
		
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
   <div class="stit"><s:message code="COM.CD.DTL.INFO" /></div> <!-- 공통코드 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="commDcdId" name="commDcdId" value="${result.commDcdId}" >
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
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
        <input type="text" name="commDcd" class="wd300" value="${result.commDcd}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="COM.CD.NM" /></th> <!-- 공통코드명 -->
        <td colspan="3">
        <input type="text" name="commDcdNm" class="wd300" value="${result.commDcdNm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="COM.CD.TXT" /></th> <!-- 공통코드설명 -->
        <td colspan="3">
        <textarea name="objDescn" class="wd300">${result.objDescn}</textarea>
        </td>
      </tr>
      <tr>
        <th><s:message code="UPRN.COM.CD" /></th> <!-- 상위공통코드 -->
        <td colspan="3">
        <input type="text" name="uppCommDcdId" class="wd300" value="${result.uppCommDcdId}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="SYS.COM.CD.YN" /></th> <!-- 시스템공통코드여부 -->
        <td colspan="3">
        <select name="sysCdYn" id="sysCdYn">
        	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
        	<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
        </select>
<%--         <input type="text" name="sysCdYn" class="wd300" value="${result.sysCdYn}" > --%>
        </td>
      </tr>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div id="divFrmBtn" style="text-align: center;">
		<button class="btn_save" id="btnfrmSave" name="btnfrmSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_reset" id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>
</body>
</html>