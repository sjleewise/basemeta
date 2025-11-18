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
			menuNm			: "required",
			mngrMenuYn		: "required",
			dispYn			: "required",
			dispOrd		 	: {
				required : true,
				number : true
			}
	
		},
		messages: {
			menuNm			: requiremessage,
			mngrMenuYn		: requiremessage,
			dispYn			: requiremessage,
			dispOrd	 		: {
				required : requiremessage,
				number : numbermessage
			}

		}
	});
	
	//조회 결과값 초기 셋팅
	$("#frmInput #mngrMenuYn").val('${result.mngrMenuYn}');
	$("#frmInput #dispYn").val('${result.dispYn}');
	$("#frmInput #menuDcd").val('${result.menuDcd}');
	
	//alert("조회완료");
	if ("U" == $("#saction").val()) {
		//alert("업데이트일경우");
		$("input[name=menuId]").attr('readonly', true);
	}
	
	$('.btnDelPop').click(function(event){
	    	
	    	event.preventDefault();  //브라우저 기본 이벤트 제거...
	    	$(this).parent().children().val('');
		
	});
	 $('.btnSearchPop').click(function(event){
		    	
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    	var url = "<c:url value='/commons/sys/menu/selectMenuPopList.do' />";
				var param = $("form#frmInput").serialize(); //$("form#frmInput").serialize();
				var popwin = OpenModal(url+"?"+param, "menupop",  600, 400, "no");
				popwin.focus();
			
			
		});
	 
	 $('.btnSearchFilePop').click(function(event){
		    	
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    	var url = "<c:url value='/commons/sys/menu/selectFilePopList.do' />";
				var param = $("form#frmInput").serialize(); //$("form#frmInput").serialize();
				var popwin = OpenModal(url+"?"+param, "filepop",  600, 400, "no");
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
		//$("form[name=frmInput]")[0].reset();
		
		resetForm($("form[name=frmInput]"));
		
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
	
	$("#frmInput input[name=menuNm]").focus();


});
</script>
</head>
<body>
   <div class="stit"><s:message code="MENU.DTL.INFO" /></div> <!-- 메뉴 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="menuId" name="menuId" value="${result.menuId}" >
   	<input type="hidden" id="uppMenuId" name="uppMenuId" value="${result.uppMenuId}" >
<%--    	<input type="hidden" id="filePath" name="filePath" value="${result.filePath}" > --%>
   	<input type="hidden" name="objVers" class="wd300" value="${result.objVers}" >
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
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
      <tr>
        <th><s:message code="MENU.NM" /></th> <!-- 메뉴명 -->
        <td colspan="3">
        <input type="text" name="menuNm" class="wd300" value="${result.menuNm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="MENU.DSTC" /></th> <!-- 메뉴구분 -->
        <td colspan="3">
        <select id="menuDcd" name="menuDcd">
                                       <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
					                	<c:forEach var="code" items="${codeMap.menuDcd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select>
        </td>
      </tr>
      <tr>
        <th><s:message code="UPRN.MENU.NM" /></th> <!-- 상위메뉴명 -->
        <td colspan="3">
        <input type="text" name="uppMenuNm" class="wd300" value="${result.uppMenuNm}" readonly >
        <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
        <button class="btnSearchPop" id="upperMenuSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
        </td>
      </tr>
      <tr>
        <th><s:message code="FILE.NM" /></th> <!-- 파일명 -->
        <td colspan="3">
        <input type="text" name="fileNm" class="wd300" value="${result.fileNm}" >
        <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
        <button class="btnSearchPop" id="fileSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
        </td>
      </tr>
      <tr>
        <th><s:message code="FILE.PATH" /></th> <!-- 파일경로 -->
        <td colspan="3">
        <input type="text" name="filePath" class="wd300" value="${result.filePath}"  >
        </td>
      </tr>
      <tr>
        <th><s:message code="SCRN.OTPT.YN" /></th> <!-- 화면출력여부 -->
        <td colspan="3">
        <select name="dispYn" id="dispYn">
        	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
        	<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
        </select>
        </td>
      </tr>
      <tr>
        <th><s:message code="SCRN.OTPT.SQNC" /></th> <!-- 화면출력순서 -->
        <td colspan="3">
        <input type="text" name="dispOrd" class="wd300" value="${result.dispOrd}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="MGR.MENU.YN" /></th> <!-- 관리자메뉴여부 -->
        <td colspan="3">
        <select name="mngrMenuYn" id="mngrMenuYn">
        	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
        	<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
        </select>
        </td>
      </tr>
      <tr>
        <th><s:message code="IMG.PATH" /></th> <!-- 이미지경로 -->
        <td colspan="3">
        <input type="text" name="imgPath" class="wd300" value="${result.imgPath}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="CONTENT.TXT" /></th> <!-- 설명 -->
        <td colspan="3">
        <textarea name="objDescn" class="wd300">${result.objDescn}</textarea>
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
		<button class="btn_frm_reset" id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>


</body>
</html>