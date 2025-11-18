<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html lang="ko">
<head>
<title><s:message code="HELP.DTL.INFO"/></title> <!-- 도움말 상세정보 -->
<script type="text/javascript" src='<c:url value="/js/ckeditor/ckeditor.js" />'></script>
<script type="text/javascript">
$(document).ready(function(){
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	
	$("#frmInput").validate({
		rules: {
			scrNm	: "required",
			menuNm	: "required",
			scrUrl	: "required",
			helpCtnt	: "required"
		},
		messages: {
			scrNm	: requiremessage,
			menuNm	: requiremessage,
			scrUrl	: requiremessage,
			helpCtnt	: requiremessage
		}
		
/*  		showErrors : function(errorMap, errorList){
		if(errorList.length){
			alert(errorList[0].message);
			}
		}  */
	});
	 
 	//조회 결과값 초기 셋팅
	$("#frmInput #useYn").val('${result.useYn}');
		
	var useYn = '${result.useYn}';
	
	// 수신여부 값 설정
	if(useYn != "" && useYn == "N"){
		$(':input:radio[name="useYn"]:input[value="N"]').attr("checked", true);
	}else if(useYn != "" && useYn =="Y"){
		$(':input:radio[name="useYn"]:input[value="Y"]').attr("checked", true);
	}
	
	if ("U" == $("#saction").val()) {
		//alert("업데이트일경우");
		$("input[name=helpId]").attr('readonly', true);
	}
	//$("input[name=helpId]").attr('readonly', true);
	
	var editor = CKEDITOR.instances["helpCtnt"];
    if(editor){editor.destroy(true);}
	CKEDITOR.replace( 'helpCtnt', {height:'365px', readOnly:false, enterMode:'2', shiftEnterMode:'2', 
		//toolbarStartupExpanded:true, 
		filebrowserUploadUrl:'<c:url value="/js/ckeditor/upload.jsp?" />'
			+'realUrl='+'<c:url value="/images/ckeditor/" />'
		    +'&realDir=/images/ckeditor/'
	});
	
	
	$('.btnSearchPop').click(function(event){
		
		var url = "<c:url value='/commons/sys/menu/selectMenuPopHelp.do' />";
// 		var popwin = OpenModal(url+"?"+param, "menupop",  600, 550, "no");
// 		popwin.focus();
		
   		openLayerPop (url, 600, 550, "");
//     		OpenWindow("/wiseda/meta/subjarea/popup/subjSearchPop.do", "dkafda",  800, 600, "yes");
//     		openSearchPop("/wiseda/meta/model/pop/subjSearchPop.do", param);
		
	});

	$('.btnDelPop').click(function(event){
		
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		$(this).parent().children().val('');
		$(':input:text[name="scrUrl"]').val('');

	}).show();
	
	
	//폼 저장 버튼 초기화...
	$('#btnfrmSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		/* if(!$("#frmInput").valid()){ 
			alert("다음 항목에 입력 여부를 확인해 주십시오.\n\n*화면명, 메뉴명, URL, 사용여부, 도움말 내용*");
			return false;
		} */
		
		if (isBlankStr($("#frmInput input[name=scrNm]").val())) {
			showMsgBox("ERR", "<s:message code='MSG.PAGE.NM.INPUT'/>"); /* 화면명을 입력하십시오. */
			return;
		}
		
		if (isBlankStr($("#frmInput input[name=menuNm]").val())) {
			showMsgBox("ERR", "<s:message code='MSG.MENU.NM.INPUT'/>"); /* 메뉴명을 입력하십시오. */
			return;
		}
		
		if (isBlankStr($("#frmInput input[name=scrUrl]").val())) {
			showMsgBox("ERR", "<s:message code='MSG.URL.NM.INPUT'/>"); /* URL을 입력하십시오. */
			return;
		}
		
		if ($("#frmInput input:radio[name='useYn']:checked").length<1) {
			showMsgBox("ERR", "<s:message code='MSG.USEYN.NM.INPUT'/>"); /* 사용여부를 체크하십시오. */
			return;
		} 
		
		var editorCtnt = CKEDITOR.instances.helpCtnt.getData();
 		if (editorCtnt == "") {
			showMsgBox("ERR", "<s:message code='MSG.PAGE.NM.INPUT'/>"); /* 도움말 내용을 입력하십시오. */
			return;
		}
		
		//저장할래요? 확인창...
		//var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", "<s:message code='MSG.HELP.REG.MANU'/>", 'SaveRow'); /* 이미 도움말이 등록된 메뉴일 경우<br>새로 저장하는 도움말로 변경됩니다.<br><br>저장하시겠습니까? */
			
	});
		
		
		//폼 초기화 버튼 초기화...
	$('#btnfrmReset').click(function(event){
		var url = '<c:url value="/admin/sys/help/HelpMstrList.do" />';
		var param = "searchTyp="+$("#frmInput input[name=searchTyp]").val();
		param = param+"&searchWrd="+$("#frmInput input[name=searchWrd]").val();
		location.href=url+'?'+param;
		
/*  		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
 		$(':input:text[name="scrNm"]').val('');
 		$(':input:text[name="scrUrl"]').val('');
 		$(':input:text[name="menuNm"]').val('');
 		$(':input:radio[name="useYn"]:input[value="Y"]').attr("checked", false);
 		$(':input:radio[name="useYn"]:input[value="N"]').attr("checked", false);
		CKEDITOR.instances.helpCtnt.setData(""); */
	});
		
	
	$('#btnfrmPreview').click(function(){
				
		var url = '<c:url value="/admin/sys/help/popup/helpPop.do"/>';
		var srcNm = $("#frmInput input[name=scrNm]").val();
		var helpCtnt = CKEDITOR.instances.helpCtnt.getData();
		var winName = "helpPop";
		
		$("#frmPreview input[name=scrNm]").val(srcNm);
		$("#frmPreview input[name=helpCtnt]").val(helpCtnt);
		$("#frmPreview").attr("action", url);
		$("#frmPreview").attr("target", winName);
		OpenWindow('',winName,'925','550','yes');
		$("#frmPreview").submit(); //get 방식으로 요청할 수 있는 미리보기 내용의 크기가 제한되므로 픔으로 post방식을 사용한다
		
		
	});
	
	$("#frmInput input[name=scrNm]").focus();
	
});


function returnPop (ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);

	$("#frmInput input[name=menuId]").val(retjson.menuId);
	$("#frmInput input[name=menuNm]").val(retjson.menuNm);
	$("#frmInput input[name=scrUrl]").val(retjson.filePath);
}

function doAction(sAction) {
	switch(sAction) { 
	
	case 'SaveRow': //단건 저장
		//saction (I-입력, U-수정)
				
		/* var urls = '<c:url value="/admin/sys/help/ajaxgrid/insertHelpMastetInf.do"/>';
		var param = $('form[name=frmInput]').serialize();
		ajax2Json(urls, param, ibscallback);
		
		break;
		 */
		document.frmInput.action = "<c:url value='/admin/sys/help/ajaxgrid/insertHelpMastetInf.do'/>";
		document.frmInput.submit();
		break;
		//location.href='<c:url value="/admin/sys/help/HelpMstrList.do" />';
	}
	
}

</script>
</head>
<body>
<div class="menu_subject">
	<h3><s:message code="PAGE.HELP.TEXT"/></h3> <!-- 화면별도움말 -->
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:15px;"><span></span></div>
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="searchTyp" name="searchTyp" value="${param.searchTyp}" >
   	<input type="hidden" id="searchWrd" name="searchWrd" value="${param.searchWrd}" >
   	<input type="hidden" name="helpId" class="wd300" value="${result.helpId}" >
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="" style="table-layout: fixed;">
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
        <th class="th_require"><SPAN class="BK_impt">&nbsp;&nbsp;&nbsp;<s:message code="PAGE.NAME"/></SPAN></th> <!-- 화면명 -->
      	  <td colspan="3">
        	<input type="text" name="scrNm" class="wd300"value="<c:out value="${result.scrNm}" />" >
        </td>
      </tr>
      
      <tr>
       <th class="th_require"><SPAN class="BK_impt">&nbsp;&nbsp;&nbsp;<s:message code="MENU.NM" /></SPAN></th> <!-- 메뉴명 -->
        <td >
         <input type="hidden" name="menuId" class="BK_readonly" value="${result.menuId}" >
         <input type="text" name="menuNm" class="BK_readonly wd220" value="${result.menuNm}" readonly >
         <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
         <button class="btnSearchPop" id="MenuSearchPop"><s:message code="INQ" /></button> <!-- 검색 -->
        </td>
        <th><SPAN class="BK_impt">&nbsp;&nbsp;&nbsp;URL</SPAN></th>
        	<td>
        	<input type="text" name="scrUrl" class="BK_readonly wd300" readonly value="<c:out value="${result.scrUrl}" />" >
        </td>
      </tr>
      
            
       <tr>
        <th><SPAN class="BK_impt">&nbsp;&nbsp;&nbsp;<s:message code="USE.YN"/></SPAN></th> <!-- 사용여부 -->
        <td colspan="3">
     		<label><input type="radio" name="useYn" value="Y"/><s:message code="USE" /></label> <!-- 사용 -->
			<label><input type="radio" name="useYn" value="N"/><s:message code="NUSE"/></label> <!-- 미사용 -->
        </td>
      </tr>
      

      
      <tr>
        <th><SPAN class="BK_impt">&nbsp;&nbsp;&nbsp;<s:message code="HELP.CONTENT"/></SPAN></th> <!-- 도움말 내용 -->
        <td colspan="3" style="height:500px;">
        <textarea id="helpCtnt" name="helpCtnt" accesskey="" style="width:96%; height:100%;">${result.helpCtnt}</textarea>
        </td>
      </tr>
   
    
       
      
    </table>
    </div>
    </form>
     <!-- 미리보기폼 시작 -->
   	<form id="frmPreview" name="frmPreview" action ="" method="post">
   	<input type="hidden" name="scrNm">
   	<input type="hidden" name="helpCtnt">
   	</form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div id="divFrmBtn" style="text-align: center;">
		<button class="btn_frm_save" id="btnfrmSave" name="btn_frm_save "><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_excel_down" id="btnfrmPreview" name="btnfrmPreview" ><s:message code="PREVIEW"/></button> <!-- 미리보기 -->
		<button class="btn_excel_down"  id="btnfrmReset" name="btnfrmReset" ><s:message code="LST"/></button> <!-- 목록 -->
	</div>


</body>
</html>