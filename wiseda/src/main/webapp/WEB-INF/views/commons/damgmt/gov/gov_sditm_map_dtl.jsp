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
			govSditmLnm			: "required",
			govSditmPnm			: "required",
			objDescn			: "required"
		},
		messages: {
			enactDgr            : requiremessage,
			govSditmLnm			: requiremessage,
			govSditmPnm			: requiremessage,
			objDescn			: requiremessage
		}
	});
	
    //표준용어 검색팝업 이벤트
    $("#stndSearchPop").click(function(){
    	event.preventDefault();  //브라우저 기본 이벤트 제거...
    	
    	var param = "sditmLnm="+$("#frmInput #sditmLnm").val(); //$("form#frmInput").serialize();
    	param = encodeURI(param);
		openLayerPop ('<c:url value="/commons/damgmt/gov/popup/selectSditmPop.do" />', 800, 600, param);
    }).show();
	 
		
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
	
	$("#frmInput input[name=govSditmLnm]").focus();
	

	$('#btnDelPop').click(function(event){
    	
    	event.preventDefault();  //브라우저 기본 이벤트 제거...
    	$(this).parent().children().val('');
		$("#sditmId").val('');
		$("#sditmPnm").val('');
});


});


//표준용어 팝업 리턴값 처리...
function returnItemPop(ret){
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #sditmPnm").val(retjson.sditmPnm);
	$("#frmInput #sditmLnm").val(retjson.sditmLnm);
	$("#frmInput #sditmId").val(retjson.sditmId);
	
}


</script>
</head>
<body>
   <div class="stit"><s:message code="SIMIWORD.PRHB.WORD.DTL.INFO" /></div> <!-- 유사어/금지어 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="govSditmId" name="govSditmId" value="${result.govSditmId}" >
   	<input type="hidden" id="sditmId" name="sditmId" value="${result.sditmId}" >
   	<input type="hidden" name="objVers" class="wd300" value="${result.objVers}" >
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
        <th class="th_require" >제정차수</th> <!-- 제정차수 -->
        <td colspan="3">
        <input type="text" name="enactDgr" class="wd300" value="${result.enactDgr}" >
        </td>
      </tr>
      <tr>
        <th class="th_require" >공통표준 용어 논리명</th> <!-- 공통표준 용어 논리명 -->
        <td>
        <input type="text" name="govSditmLnm" class="wd300" value="${result.govSditmLnm}" >
        </td>
        <th class="th_require" >공통표준 용어 영문약어명</th> <!-- 공통표준 용어 영문약어명 -->
        <td>
        <input type="text" name="govSditmPnm" class="wd300" value="${result.govSditmPnm}" >
        </td>
      </tr>
      <tr>
        <th class="" >공통표준 도메인</th> <!-- 공통표준 도메인 -->
        <td>
        <input type="text" name="govDmnLnm" class="wd300" value="${result.govDmnLnm}" >
        </td>
        <th class="" >저장 형식</th> <!-- 저장 형식 -->
        <td>
        <input type="text" name="saveType" class="wd300" value="${result.saveType}" >
        </td>
      </tr>
      <tr>
        <th class="" >표현 형식</th> <!-- 표현 형식 -->
        <td>
        <input type="text" name="examData" class="wd300" value="${result.examData}" >
        </td>
        <th class="" >허용값</th> <!-- 허용값 -->
        <td>
        <input type="text" name="cdValRule" class="wd300" value="${result.cdValRule}" >
        </td>
      </tr>
      <tr>
        <th class="" >행정표준코드명</th> <!-- 행정표준코드명 -->
        <td>
        <input type="text" name="govStndCd" class="wd300" value="${result.govStndCd}" >
        </td>
        <th class="" >소관기관명</th> <!-- 소관기관명 -->
        <td>
        <input type="text" name="govOrgNm" class="wd300" value="${result.govOrgNm}" >
        </td>
      </tr>
      <tr>
        <th class="th_require"><s:message code="CONTENT.TXT" /></th> <!-- 설명 -->
        <td colspan="3">
        <textarea name="objDescn" class="wd400" >${result.objDescn}</textarea>
        </td>
      </tr>
      <tr>
        <th class="" >기관표준 용어 논리명</th> <!-- 기관표준 용어 논리명 -->
        <td>
        <input type="text" id="sditmLnm" name="sditmLnm" class="wd200" value="${result.sditmLnm}" >
        <button class="btnDelPop" id="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
        <button class="btnSearchPop" id="stndSearchPop"><s:message code="SRCH" /></button> <!-- 검색 --> 
        </td>
        <th class="" >기관표준 용어 물리명</th> <!-- 기관표준 용어 물리명 -->
        <td>
        <input type="text" id="sditmPnm" name="sditmPnm" class="wd300" value="${result.sditmPnm}" >
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

	<div style="clear:both; height:50px;"><span></span></div>

</body>
</html>