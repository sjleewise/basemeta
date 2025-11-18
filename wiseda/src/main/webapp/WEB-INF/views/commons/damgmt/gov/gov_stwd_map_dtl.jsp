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
<title><s:message code="MENU.DTL.INFO" /></title> <!-- 메뉴상세정보 -->
<script type="text/javascript">
$(document).ready(function(){
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	var numbermessage = "<s:message code="VALID.NUMBER" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			enactDgr            : "required",
			govStwdLnm			: "required",
			govStwdPnm			: "required",
			objDescn			: "required"
		},
		messages: {
			enactDgr            : requiremessage,
			govStwdLnm			: requiremessage,
			govStwdPnm			: requiremessage,
			objDescn			: requiremessage
		}
	});
	
	$('#btnDelPop').click(function(event){
	    	
	    	event.preventDefault();  //브라우저 기본 이벤트 제거...
	    	$(this).parent().children().val('');
			$("#stwdId").val('');
			$("#stwdPnm").val('');
		
	});

	$("#stwdIdSearchPop").click(function(){
		event.preventDefault();	//브라우저 기본 이벤트 제거...
		
    	var param = $("form#frmInput").serialize();
    	openLayerPop("<c:url value='/commons/damgmt/gov/popup/selectStwdPop.do' />", 1000, 480, param);
    });
	 
		
	//폼 저장 버튼 초기화...
	$('#btnfrmSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//변경한 시트 단건 내용을 저장...
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	
	
	//폼 초기화 버튼 초기화...
	$('#btnfrmReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		resetForm($("form#frmInput"));
		
	});
	
	$("#frmInput input[name=govStwdLnm]").focus();


});

function returnStwdVal(ret) {
	var retjson = jQuery.parseJSON(ret);

	$("#frmInput #stwdId").val(retjson.stwdId);
	$("#frmInput #stwdLnm").val(retjson.stwdLnm);
	$("#frmInput #stwdPnm").val(retjson.stwdPnm);
}
</script>
</head>
<body>
   <div class="stit"><s:message code="SIMIWORD.PRHB.WORD.DTL.INFO" /></div> <!-- 유사어/금지어 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="govStwdId" name="govStwdId" value="${result.govStwdId}" >
   	<input type="hidden" id="stwdId" name="stwdId" value="${result.stwdId}" >
   	<input type="hidden" id="regUserId" name="regUserId" value="${result.regUserId}" >
   
   	
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
        <th class="th_require" >제정차수</th> <!-- 유사어논리명 -->
        <td>
        <input type="text" name="enactDgr" class="wd300" value="${result.enactDgr}" >
        </td>
        <th class="th_require" >공통표준 단어 논리명</th> <!-- 유사어논리명 -->
        <td>
        <input type="text" name="govStwdLnm" class="wd300" value="${result.govStwdLnm}" >
        </td>
      </tr>
      <tr>
        <th class="th_require" >공통표준 단어 영문약어명</th> <!-- 유사어물리명 -->
        <td>
        <input type="text" name="govStwdPnm" class="wd300" value="${result.govStwdPnm}" >
        </td>
        <th class="" >공통표준 단어 영문명</th> <!-- 유사어물리명 -->
        <td>
        <input type="text" name="engMean" class="wd300" value="${result.engMean}" >
        </td>
      </tr>
      <tr>
        <th class="" >형식단어여부</th> <!-- 유사어논리명 -->
        <td>
        	<select  id="dmnYn" name="dmnYn"  class="wd300">
			        <option value="Y">Y</option>
			        <option value="N">N</option>
			</select>
        </td>
        <th class="" >공통표준도메인분류명</th> <!-- 유사어물리명 -->
        <td>
        <input type="text" name="govDmngLnm" class="wd300" value="${result.govDmngLnm}" >
        </td>
      </tr>
      <tr>
        <th class="" >이음등의어 목록</th> <!-- 유사어논리명 -->
        <td>
        <input type="text" name="synonyms" class="wd300" value="${result.synonyms}" >
        </td>
        <th class="" >금칙어 목록</th> <!-- 유사어물리명 -->
        <td>
        <input type="text" name="prohibits" class="wd300" value="${result.prohibits}" >
        </td>
      </tr>
      <tr>
        <th class="th_require"><s:message code="CONTENT.TXT" /></th> <!-- 설명 -->
        <td colspan="3">
        <textarea name="objDescn" class="wd400" >${result.objDescn}</textarea>
        </td>
      </tr>
      <tr>
        <th class="" >기관표준 단어 논리명</th> <!-- 유사어논리명 -->
        <td>
        <input type="text" id="stwdLnm" name="stwdLnm" class="wd300" value="${result.stwdLnm}" readonly >
        <button class="btnDelPop" id="btnDelPop"><s:message code="DEL" /></button> <!-- 삭제 -->
        <button class="btnSearchPop" id="stwdIdSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->  
        </td>
        <th class="" >기관표준 단어 물리명</th> <!-- 유사어물리명 -->
        <td>
        <input type="text" id="stwdPnm" name="stwdPnm" class="wd300" value="${result.stwdPnm}" readonly >
        </td>
      </tr>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div id="divFrmBtn" style="text-align: center;">
		<button class="btn_search" id="btnfrmSave" name="btnfrmSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_search" id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>


</body>
</html>