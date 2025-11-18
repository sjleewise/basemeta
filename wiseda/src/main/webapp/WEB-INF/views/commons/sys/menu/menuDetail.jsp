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
			menuNo			: "required",
			menuNm 			: "required",
// 			upperMenuNo 	: "required",
			menuOrdr 		: "required",
			progrmFileNm	: "required"
		},
		messages: {
			menuNo			: requiremessage,
			menuNm 			: requiremessage,
// 			upperMenuNo 	: requiremessage,
			menuOrdr 		: requiremessage,
			progrmFileNm	: requiremessage
		}
	});
	
	//alert("조회완료");
	if ("U" == $("#saction").val()) {
		//alert("업데이트일경우");
		$("input[name=menuNo]").attr('readonly', true);
	}
	
	//프로그램파일명
	$("input[name=progrmFileNm]").attr('readonly', true);
	
	
	
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
	
    //프로그램 검색팝업
    $("#progrmSearchPop").click(function(){
//     	alert("dddddd");
//     	$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
//     	$('div#popSearch iframe').attr('src', "<c:url value='/meta/model/pop/progrmSearchPop.do' />");
//      	$('div#popSearch').dialog("open");
		var url = "<c:url value='/commons/sys/program/pop/programPop.do' />";
		var param = $("form#frmInput").serialize(); //$("form#frmInput").serialize();
		var popwin = OpenModal(url+"?"+param, "progrmopp",  600, 400, "no");
		popwin.focus();
    });
	
	$("#frmInput input[name=menuNo]").focus();


});

	//프로그램 검색팝업 리턴값 처리
	function returnProgramPop(ret) {
// 		alert(ret);
		//리턴 값을 json 형태로 변환 {name:insomnia, age:11}
		var obj = $.parseJSON(ret);
		
// 		alert(obj.progrmFileNm);
// 		alert(obj.progrmKoreanNm);
		
		$('form[name=frmInput] input[name=progrmFileNm]').val(obj.progrmFileNm);
		
	}
</script>
</head>
<body>
   <div class="stit"><s:message code="MENU.DTL.INFO" /></div> <!-- 메뉴상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
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
        <th><s:message code="MENU.ID" /></th> <!-- 메뉴ID -->
        <td colspan="3">
        <input type="text" name="menuNo" class="wd300" value="${result.menuNo}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="MENU.NM" /></th> <!-- 메뉴명 -->
        <td colspan="3">
        <input type="text" name="menuNm" class="wd300" value="${result.menuNm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="PGM.FILE.NM" /></th> <!-- 프로그램파일명 -->
        <td colspan="3">
        <div>
	        <input type="text" name="progrmFileNm" class="wd300" value="${result.progrmFileNm}" >
        	<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
        	<button class="btnSearchPop" id="progrmSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
       	</div>
        </td>
      </tr>
      <tr>
        <th><s:message code="MENU.TXT" /></th> <!-- 메뉴설명 -->
        <td colspan="3">
        <input type="text" name="menuDc" class="wd300" value="${result.menuDc}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="UPRN.MENU.NO" /></th> <!-- 상위메뉴번호 -->
        <td colspan="3">
        <input type="text" name="upperMenuNo" class="wd300" value="${result.upperMenuNo}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="MENU.SQNC" /></th> <!-- 메뉴순서 -->
        <td colspan="3">
        <input type="text" name="menuOrdr" class="wd300" value="${result.menuOrdr}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="REL.IMG.PATH" /></th> <!-- 관련이미지 경로 -->
        <td colspan="3">
        <input type="text" name="relateImagePath" class="wd300" value="${result.relateImagePath}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="REL.IMG.NM" /></th> <!-- 관련이미지명 -->
        <td colspan="3">
        <input type="text" name="relateImageNm" class="wd300" value="${result.relateImageNm}" >
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