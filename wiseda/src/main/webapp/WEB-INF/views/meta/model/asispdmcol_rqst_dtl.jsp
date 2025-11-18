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
<title>물리모델컬럼상세정보</title>

<script type="text/javascript">

$(document).ready(function(){
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmColInput").validate({
		rules: {
			rqstDcd		: "required",
			pdmColPnm	: "required",
// 			pdmColLnm 	: "required",
			colOrd		: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			pdmColPnm	: requiremessage,
// 			pdmColLnm 	: requiremessage,
			colOrd		: requiremessage
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
	$('#btnColRowSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = col_sheet.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmColInput #rvwConts").val());
			col_sheet.SetCellValue(srow, "rvwConts", $("#frmColInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmColInput").valid()) return false;
		
		//PK여부 체크시 PK순서 입력여부 확인한다.
// 		alert($("#frmColInput #pkYn").is(":checked"));
		if($("#frmColInput #pkYn").is(":checked")) {
			if (isBlankStr($("#frmColInput #pkOrd").val())) {
// 				alert("PK여부를 체크하시면 PK순서를 입력해야 한다.");
				showMsgBox("ERR", "<s:message code="ERR.PKYN" />");
				return;
			}
// 			alert($("#frmColInput #nonulYn").is(":checked"));
			if(!$("#frmColInput #nonulYn").is(":checked")) {
				showMsgBox("ERR", "<s:message code="ERR.NOTNULL" />");
				return;
			}
		}
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
// 		showMsgBox("CNF", message, 'SaveCol');
		showMsgBox("CNF", message, 'SaveColRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnColReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
		$("form[name=frmColInput]")[0].reset();
		
	});
	
    //표준용어 검색팝업 이벤트
    $("#stndSearchPop").click(function(){
    	
    	var param = "sditmLnm="+$("#frmColInput #pdmColLnm").val(); //$("form#frmInput").serialize();
    	param = encodeURI(param);
		openLayerPop ("<c:url value='/meta/stnd/popup/stnditem_pop.do' />", 800, 600, param);
//			var param = $("form#frmColInput").serialize(); //$("form#frmColInput").serialize();
//			openSearchPop("<c:url value='/meta/stnd/pop/sditmSearch_pop.do' />");
    });
    
    //컬럼 검색팝업 이벤트
    $("#colSearchPop").click(function(){
//			var param = $("form#frmColInput").serialize(); //$("form#frmColInput").serialize();
//			openSearchPop("<c:url value='/meta/model/pop/pdmcolSearchPop.do' />");
    });
	

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	if(rqststep == "Q" || rqststep == "A") {
		$("button.btn_frm_save, button.btn_frm_reset").hide();
		
		$("#input_col_form_div .reviewStatus").hide();
	}
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
// 	setDispLstButton(rqststep);
	
	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmColInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmColInput #regTypCd").val('${result.regTypCd}');
	$("#frmColInput #vrfCd").val('${result.vrfCd}');
	
	//check box 값 초기화...
	chkformsetup($("#frmColInput #pkYn"), '${result.pkYn}');
	chkformsetup($("#frmColInput #nonulYn"), '${result.nonulYn}');
	chkformsetup($("#frmColInput #encYn"), '${result.encYn}');
	

});


//표준용어 팝업 리턴값 처리...
function returnItemPop(ret){
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmColInput #pdmColPnm").val(retjson.sditmPnm);
	$("#frmColInput #pdmColLnm").val(retjson.sditmLnm);
	$("#frmColInput #sditmId").val(retjson.sditmId);
	$("#frmColInput #dataType").val(retjson.dataType);
	$("#frmColInput #dataLen").val(retjson.dataLen);
	$("#frmColInput #dataScal").val(retjson.dataScal);
	//$("#frmColInput #encYn").val(retjson.encYn);
	chkformsetup($("#frmColInput #encYn"), retjson.encYn);
	
}

</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_col_form_div">
     <form name="frmColInput" id="frmColInput" method="post" >
     	<input type="hidden" id="rqstSno" 	name="rqstSno" 		value="${result.rqstSno}" >
     	<input type="hidden" id="rqstDtlSno" 	name="rqstDtlSno" 	value="${result.rqstDtlSno}" >
	  	<input type="hidden" id="ibsStatus" 	name="ibsStatus" 	value="${saction }" >
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	 <div class="stit">컬럼 정보</div>
	 <div style="clear:both; height:5px;"><span></span></div>
     <legend>머리말</legend>
     <div class="tb_basic">
         <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
             <caption>
             테이블 이름
             </caption>
             <colgroup>
             <col style="width:15%;" />
             <col style="width:35%;" />
             <col style="width:15%;" />
             <col style="width:35%;" />
             </colgroup>
             <tbody>
<!--                  <tr> -->
<!--                      <th scope="row"><label for="pdmColId">컬럼ID</label></th> -->
<%--                      <td colspan="3"><span class="input_inactive"><input type="text" id="pdmColId" name="pdmColId"/></span></td> --%>
<!--                  </tr> -->
                 <tr>
                     <th scope="row" class="th_require"><label>컬럼명</label></th>
                     <td colspan="3">
                     	<span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span>물리명 : <input type="text" id="pdmColPnm" name="pdmColPnm" class="wd200" value="${result.pdmColPnm }" />
                     	<span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span>논리명 : <input type="text" id="pdmColLnm" name="pdmColLnm" class="wd200" value="${result.pdmColLnm }" />
                     	<button class="btnDelPop" >삭제</button>
                      	<button class="btnSearchPop" id="stndSearchPop">검색</button>
                     </td>
                 </tr>
                 <tr style="display:none">
                     <th scope="row"><label for="bfPdmColPnm">이전 컬럼명</label></th>
                     <td colspan="3"><input type="text" id="bfPdmColPnm" name="bfPdmColPnm" class="wd200" value="${result.bfPdmColPnm }" /></td>
                 </tr>
<!--                  <tr> -->
<!--                      <th scope="row"><label for="sditmId">표준용어</label></th> -->
<%--                      <td colspan="3"><span class="input_inactive"><input type="text" id="sditmId" name="sditmId" /> --%>
<!--                      		<button class="btnDelPop" >삭제</button> -->
<!--                       	<button class="btnSearchPop" id="stndSearchPop">검색</button> -->
<%--                      </span></td> --%>
<!--                  </tr> -->
                 <tr>
                     <th scope="row" class="th_require"><label for="colOrd">컬럼순서</label></th>
                     <td colspan="3"><input type="text" id="colOrd" name="colOrd" class="wd200" value="${result.colOrd }" /></td>
                 </tr>
                 <tr>
                     <th scope="row"><label>데이터 </label></th>
                     <td colspan="3">
                     	<span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span>데이터타입 : <input type="text" id="dataType" name="dataType" value="${result.dataType }" />
                     	<span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span>데이터길이 : <input type="text" id="dataLen" name="dataLen"  value="${result.dataLen }" />
                     	<span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span>데이터소수점길이 : <input type="text" id="dataScal" name="dataScal" value="${result.dataScal }" />
                     </td>
                 </tr>
                 <tr>
                     <th scope="row"><label>PK 정보</label></th>
                     <td colspan="3">
                     <span class="input_check"><input type="checkbox" id="pkYn" name="pkYn" value="Y"/> PK 여부</span>
                     <span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span>PK 순서 : <input type="text" id="pkOrd" name="pkOrd" value="${result.pkOrd }" />
                     </td>
                 </tr>
                 <tr>
                     <th scope="row"><label for="nonulYn">NOT NULL 여부</label></th>
                     <td>
                     <span class="input_check">
                         <input type="checkbox" id="nonulYn" name="nonulYn" value="Y"/> NOT NULL 여부</span>
                     </td>
                     <th scope="row"><label for="nonulYn">암호화 여부</label></th>
                     <td>
                     <span class="input_check">
                         <input type="checkbox" id="encYn" name="encYn" value="Y"/> 암호화 여부</span>
                     </td>
                 </tr>
                 <tr>
                     <th scope="row"><label for="defltVal">DEFAULT 값</label></th>
                     <td colspan="3"><input type="text" class="wd300" id="defltVal" name="defltVal" value="${result.defltVal }" /></td>
                 </tr>
                 <tr>
                     <th scope="row"><label for="objDescnCol">설명</label></th>
                     <td colspan="3"><textarea class="wd98p" id="objDescnCol" name="objDescn" accesskey="">${result.objDescn }</textarea></td>
                 </tr>
             </tbody>
         </table>
     </div>
    </fieldset>
    </form>
	<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div id="divColBtn" style="text-align: center;">
		<button class="btn_frm_save" id="btnColRowSave" name="btnColRowSave">저장</button>
		<button class="btn_frm_reset" id="btnColReset" name="btnColReset" >초기화</button>
	</div>
</div>
</body>
</html>
