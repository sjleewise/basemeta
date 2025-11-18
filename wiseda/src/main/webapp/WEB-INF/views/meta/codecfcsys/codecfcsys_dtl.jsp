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
<title><s:message code="CD.CLS.SYST.DTL.INFO" /></title> <!-- 코드분류체계 상세정보 -->

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
			codeCfcSysCd	: "required",
			codeCfcSysLnm 	: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			codeCfcSysCd	: requiremessage,
			codeCfcSysLnm 	: requiremessage
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
		$("form[name=frmInput]")[0].reset();
		
	});
	

	
	//담당자 검색 팝업 호출
	$("#userPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
	
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
	$("#frmInput #codeCfcSysCd").val('${result.codeCfcSysCd}');
});


//담당자 팝업 리턴값 처리...
function returnUserInfoPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #crgUserId").val(retjson.userId);
	$("#frmInput #crgUserNm").val(retjson.userNm);
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
	<div id="validreviewDiv">
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	</div>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="CD.CLS.SYST.DTL.INFO" /></div> <!-- 코드분류체계 상세정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code="MSG.TBL.SMRY" />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="CD.CLS.SYST" /> <!-- 코드분류체계 -->
               </caption>
               <colgroup>
               <col style="width:30%;" />
               <col style="width:70%;" />
               </colgroup>
               <tbody>
                   <tr>
                       <th scope="row" class="th_require"><label for="codeCfcSysCd"><s:message code="CD.CLS.SYST.PTRN.SPC" /></label></th> <!-- 코드분류체계 유형 -->
                       <td >
                       	    <select id="codeCfcSysCd" class="" name="codeCfcSysCd" disabled="disabled">
			    				<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							    <c:forEach var="code" items="${codeMap.codeCfcSysCd}" varStatus="status">
							    <option value="${code.codeCd}">${code.codeLnm}</option>
							    </c:forEach>
							</select>
                       </td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="codeCfcSysLnm"><s:message code="CD.CLS.SYST.NM" /></label></th> <!-- 코드분류체계명 -->
                       <td><input type="text" id="codeCfcSysLnm" name="codeCfcSysLnm"  class="wd90p"  value="${result.codeCfcSysLnm}" readonly/></td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="codeCfcSysFrm"><s:message code="CD.CLS.SYST.FRMT" /></label></th> <!-- 코드분류체계형식 -->
                       <td><input type="text" id="codeCfcSysFrm" name="codeCfcSysFrm" value="${result.codeCfcSysFrm}" class="wd90p" readOnly /></td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="crgUserNm"><s:message code="CHG.R" /></label></th> <!-- 담당자 -->
						<td>
							<span class="input_inactive"><input type="hidden" id="crgUserId" name="crgUserId" value="${result.crgUserId}" /></span>
							<input type="text" id="crgUserNm" name="crgUserNm" value="${result.crgUserNm}" class="wd90p" readonly />
							<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
							<button class="btnSearchPop" id="userPop" name="userPop"><s:message code="SRCH" /></button> <!-- 검색 -->
						</td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                       <td><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn }</textarea></td>
                   </tr>
               </tbody>
           </table>
       </div>
       </fieldset>
       </form>
	<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="rqstInputBtn">
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	</div>
	<!-- 입력폼 버튼... -->
</div>
</body>
</html>
