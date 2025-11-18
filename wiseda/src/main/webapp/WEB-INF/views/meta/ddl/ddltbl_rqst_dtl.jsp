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
<title><s:message code="DDL.TBL.DTL.INFO" /></title> <!-- DDL테이블상세정보 -->

<script type="text/javascript">

$(document).ready(function(){
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			rqstDcd		: "required",
			ddlTblPnm	: "required",
			dbConnTrgPnm 	: "required", 
			dbSchPnm 	: "required", 
		},
		messages: {
			rqstDcd		: requiremessage,
			ddlTblPnm	: requiremessage,
			dbConnTrgPnm 	: requiremessage, 
			dbSchPnm 	: requiremessage, 
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	
	//alert("조회완료");
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=progrmFileNm]").attr('readonly', true);
// 	}
	
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmInput #rvwConts").val());
			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		//alert($("form#frmInput #ddlTblPnm").val());
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
// 		$("form[name=frmInput]")[0].reset();
		resetForm($("form#frmInput"));
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
	
	
	//DBMS 스키마 검색 팝업 호출
	$("#DbmsSchemaPop").click(function(){
    	var param = "dbConnTrgPnm=" + $("form#frmInput #dbConnTrgPnm").val();
    		param += "&dbSchPnm=" + $("form#frmInput #dbSchPnm").val();
    		param += "&tblSpacTypCd=T";
    	openLayerPop("<c:url value='/commons/damgmt/db/popup/dbschema_pop.do' />", 600, 500, param);
    });
	

    
// 	$("#frmInput #stwdLnm").focus();

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	
	$("#frmInput #tblChgTypCd").val('${result.tblChgTypCd}');
	$("#frmInput #rvwStsCd").val('${result.rvwStsCd}');
	$("#frmInput #rvwConts").val('${result.rvwConts}');
	//check box 값 초기화...
	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();
	
	$("#btnDbmsSchemaPop").hide();

});

//DBMS 정보 팝업 리턴값 처리
function returnDbSchemaPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #tblSpacPnm").val(retjson.dbTblSpacPnm);
	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbSchPnm").val(retjson.dbSchPnm);
// 	$("#frmInput #tblSpacPnm").val(retjson.tblSpacPnm);
}


</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" >
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit"><s:message code="SUBJ.TRRT.INFO" /></div> <!-- 주제영역 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<legend><s:message code="SUBJ.TRRT.INFO" /></legend> <!-- 주제영역정보 -->
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"><!-- 테이블 서머리입니다. -->
			<caption><s:message code="SUBJ.TRRT.INFO" /></caption> <!-- 주제영역정보 -->
			<colgroup>
			<col style="width:15%;" />
			<col style="width:85%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><s:message code="SUBJ.TRRT" /></th> <!-- 주제영역 -->
					<td colspan="3">
						<span class="input_inactive"><input type="text" class="wd300" id="fullPath" name="fullPath"  value="${result.fullPath}" readonly="readonly"/></span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit"><s:message code="DB.MS.INFO" /></div> <!-- DBMS 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<legend><s:message code="SUBJ.TRRT.INFO" /></legend> <!-- 주제영역정보 -->
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"><!-- 테이블 서머리입니다. -->
			<caption><s:message code="DB.MS.INFO" /></caption> <!-- DBMS 정보 -->
			<colgroup>
			   <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require"><s:message code="DB.MS" /></th> <!-- DBMS 정보 -->
					<td>
						<span class="input_inactive"><input type="hidden" class="wd100" id="dbSchId" name="dbSchId"  value="${result.dbSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbConnTrgPnm" name="dbConnTrgPnm"  value="${result.dbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbSchPnm" name="dbSchPnm"  value="${result.dbSchPnm}" readonly="readonly"/></span>
						<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						<button class="btnSearchPop" id="btnDbmsSchemaPop"><s:message code="SRCH" /></button> <!-- 검색 -->
					</td>
					<th scope="row"><s:message code="TBL.SPACE" /></th> <!-- 테이블스페이스 -->
					<td>
						<span class="input_inactive"><input type="hidden" class="wd100" id="tblSpacId" name="tblSpacId"  value="${result.tblSpacId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="tblSpacPnm" name="tblSpacPnm"  value="${result.tblSpacPnm}" readonly="readonly"/></span>
					</td>
					
				</tr>
			</tbody>
		</table>
	</div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"><!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   <tr>
                       <th scope="row" class="th_require"><label for="ddlTblPnm"><s:message code="TBL.NM.PHYC.NM" /></label></th> <!-- 테이블명(물리명) -->
                       <td><input type="text" id="ddlTblPnm" name="ddlTblPnm"  class="wd300" value="${result.ddlTblPnm }" title="<s:message code='MSG.TBL.NM.INPT' />" readOnly /></td> <!-- 테이블명은 반드시 입력해야 합니다. -->
                       <th scope="row"><label for="ddlTblLnm"><s:message code="TBL.NM.LGC.NM" /></label></th> <!-- 테이블명(논리명) -->
                       <td><input type="text" id="ddlTblLnm" name="ddlTblLnm"  class="wd300"  value="${result.ddlTblLnm }" readOnly /></td>
                       
                   </tr>
                   <tr>
                       <th scope="row"><label for="tblChgTypCd"><s:message code="TBL.CHG.PTRN" /></label></th> <!-- 테이블변경유형 -->
                       <td colspan="3">
                      	  <select id="tblChgTypCd" class="" name="tblChgTypCd" disabled>
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.tblChgTypCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
				 		  </select>
                       </td>
                       
                   </tr>
                   <tr>
                       <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly="readonly">${result.objDescn }</textarea></td>
                   </tr>
                   
                   <!-- 
                   <tr>
                       <th scope="row"><label for="scrtInfo"><s:message code="SCRIPT.INFO" /></label></th>  스크립트정보 
                       <td colspan="3"><textarea id="scrtInfo" name="scrtInfo" class="wd98p" rows="10" readonly>${result.scrtInfo }</textarea></td>
                   </tr>
                    -->
               </tbody>
           </table>
       </div>
       </fieldset>
       </form>
	<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<!-- 입력폼 버튼... -->
</div>
</body>
</html>
