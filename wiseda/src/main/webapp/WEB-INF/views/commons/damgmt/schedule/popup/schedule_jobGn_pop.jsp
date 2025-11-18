<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title>작업 입력</title>
<script type="text/javascript">

$(document).ready(function() {
	
	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
    	
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
	
	$("#btnPopApply").click(function(){
		var etcJobNm = $("#etcJobNm").val();
		var etcJobKndCd = $("#etcJobKndCd").val();
		var etcJobDtls = $("#etcJobDtls").val();
		var shdId = $("#shdId").val();
		var shdKndCd = $("#shdKndCd").val();
		
		if(etcJobNm == ""){
			showMsgBox("ERR", "<s:message code="MSG.JOB.NM.INPT" />"); /* 작업명을 입력해주세요. */
			$("#etcJobNm").focus();
			return false;
		}else if(etcJobKndCd == ""){
			showMsgBox("ERR", "<s:message code="MSG.JOB.PTRN.CHC" />"); /* 작업유형을 선택해주세요. */
			$("#etcJobKndCd").focus();
			return false;
		}else if(etcJobDtls == ""){
			showMsgBox("ERR", "<s:message code="MSG.GENR.BTCH.JOB.DETL.INPT" />"); /* 일반배치 작업내역을 입력해주세요 */
			$("#etcJobDtls").focus();
			return false;
		}
		
		var toSheet = opener.grid_sub;
		var toRow = toSheet.RowCount();
		
// 		var cdVal = toSheet.GetCellValue((toRow-1), 5);
// 	    if(cdVal != shdKndCd){
// 	    	alert("같은 스케줄유형만 등록 가능합니다."); 
// 	    	return;
// 	    }

		toSheet.DataInsert(toRow+1);
		toSheet.SetCellValue(toRow+1, 7, etcJobNm);
		toSheet.SetCellValue(toRow+1, 8, etcJobDtls);
		toSheet.SetCellValue(toRow+1, 9, etcJobKndCd);
		toSheet.SetCellValue(toRow+1, 5, shdKndCd);
		toSheet.SetCellValue(toRow+1, 3, shdId);
		window.close();
	}).show();
		
});

//팝업 리턴값 제공
function returnPop(fileNm, url) {
	
	opener.frmInput.fileNm.value = fileNm;
	opener.frmInput.filePath.value = url;
	window.close();
}




</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="GENR.BTCH" /></div> <!-- 일반배치 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="JOB.INPT" /></div> <!-- 작업 입력 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        <input type="hidden" id="shdKndCd" name="shdKndCd" value="${shdKndCd }"/>
        <input type="hidden" id="shdId" name="shdId" value="${shdId }"/>
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='JOB.INQ' />"> <!-- 작업검색 -->
                   <caption><s:message code="JOB.INQ" /></caption> <!-- 작업검색 -->
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                  </colgroup>
                   
                   <tbody>   
                   <tr>
						<th scope="row"><label for="etcJobNm"><s:message code="JOB.NM2" /></label></th> <!-- 작업명 -->
                       	<td colspan="2">
	                        <input type="text" name="etcJobNm" id="etcJobNm" />
                        </td>
                        <th scope="row"><label for="etcJobKndCd"><s:message code="JOB.PTRN" /></label></th> <!-- 작업유형 -->
                       	<td colspan="3">
	                        <select id="etcJobKndCd"  name="etcJobKndCd" class="" >
<!--                            		<option value=""> <s:message code="CHC" /> </option> <!- 선택 --> 
								<c:forEach var="code" items="${codeMap.etcJobKndCd}" varStatus="status">
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							</select>
                        </td>
                   </tr>
                   <tr>
						<th scope="row"><label for="etcJobDtls"><s:message code="GENR.BTCH" /><br/><s:message code="JOB.DETL" /></label></th> <!-- 일반배치 --><!-- 작업내역 -->
                       	<td colspan="5">
	                        <textarea id="etcJobDtls" name="etcJobDtls" class="wd99p"></textarea>
                        </td>
                   </tr>
                   </tbody>
                 </table>   
                 <div style="clear:both; height:10px;"><span></span></div>
	                 <div id="divFrmBtn" style="text-align: center;">
						<button class="btn_apply" id="btnPopApply" name="btnfrmSave"><s:message code="APL" /></button> <!-- 적용 -->
					</div>
            </div>
            </fieldset>
        </form>
	<div style="clear:both; height:10px;"><span></span></div>
        
</body>
</html>

