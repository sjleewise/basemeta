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
			symnLnm			: "required",
			symnPnm			: "required",
// 			sbswdLnm		: "required",
// 			sbswdPnm		: "required",
// 			symnDcd			: "required",
		},
		messages: {
			symnLnm			: requiremessage,
			symnPnm			: requiremessage,
// 			sbswdLnm		: requiremessage,
// 			sbswdPnm		: requiremessage,
// 			symnDcd			: requiremessage,
		}
	});
	
	//조회 결과값 초기 셋팅
// 	$("#frmInput #symnDcd").val('${result.symnDcd}');

	
	//alert("조회완료");
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=menuId]").attr('readonly', true);
// 	}
	
	$('.btnDelPop').click(function(event){
	    	
	    	event.preventDefault();  //브라우저 기본 이벤트 제거...
	    	$(this).parent().children().val('');
		
	});
	 $('.btnSearchPop').click(function(event){
		    	
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    	var url = "<c:url value='/meta/stnd/popup/selectStwdPop.do' />";
				var param = $("form#frmInput").serialize(); //$("form#frmInput").serialize();
				var popwin = OpenModal(url+"?"+param, "menupop",  800, 600, "no");
				popwin.focus();
			
			
		});
	 
		
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
		
		$("#symnDcd").val("HM");
		
	});
	
	$("#frmInput input[name=symnLnm]").focus();


});
</script>
</head>
<body>
   <div class="stit">
<%--    <s:message code="SIMIWORD.PRHB.WORD.DTL.INFO" /> --%>
   동음이의어 상세정보
   </div> <!-- 유사어/금지어 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="symnId" name="symnId" value="${result.symnId}" >
   	<input type="hidden" id="stwdId" name="stwdId" value="${result.stwdId}" >
   	<input type="hidden" name="objVers" class="wd300" value="${result.objVers}" >
   	<input type="hidden" id="frsRqstUserId" name="frsRqstUserId" value="${result.frsRqstUserId}" >
   	<input type="hidden" id="rqstUserId" name="rqstUserId" value="${result.rqstUserId}" >
   	<input type="hidden" id="aprvUserId" name="aprvUserId" value="${result.aprvUserId}" >
   	<!-- 
   	impl 에서 구현
   	<input type="hidden" id="expDtm" name="expDtm" value="${result.expDtm}" >
   	<input type="hidden" id="strDtm" name="strDtm" value="${result.strDtm}" >
   	<input type="hidden" id="writDtm" name="writDtm" value="${result.writDtm}" >
   	<input type="hidden" id="regTypCd" name="regTypCd" value="${result.regTypCd}" >
   	<input type="hidden" id="writUserId" name="writUserId" value="${result.writUserId}" >
   	 -->
   
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
<%--         <s:message code="SIMIWORD.PRHB.WORD.INQ.REVS.REG" /> <!-- 유사어/금지어 조회/수정/등록 --> --%>
		동음이의어 등록
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
      <tr>
        <th class="th_require" >
<%--         <s:message code="SIMIWORD.LGC.NM" /> --%>
        동음이의어 논리명
        </th> <!-- 유사어논리명 -->
        <td colspan="3">
        <input type="text" name="symnLnm" class="wd300" value="${result.symnLnm}" >
        <input type="hidden" name="symnDcd" id="symnDcd" value="HM"/>
        </td>
      </tr>
      <tr>
        <th class="th_require" >
<%--         <s:message code="SIMIWORD.PHYC.NM" /> --%>
        동음이의어 물리명
        </th> <!-- 유사어물리명 -->
        <td colspan="3">
        <input type="text" name="symnPnm" class="wd300" value="${result.symnPnm}" >
        </td>
      </tr>
        
      <tr>
        <th><s:message code="CONTENT.TXT" /></th> <!-- 설명 -->
        <td colspan="3">
        <textarea name="objDescn" class="wd400" >${result.objDescn}</textarea>
        </td>
      </tr>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div id="divFrmBtn" style="text-align: center;">
		<button class="btn_frm_save" id="btnfrmSave" name="btnfrmSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_frm_save" id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>


</body>
</html>