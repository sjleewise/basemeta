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
<title>메타데이터컬럼상세정보</title>

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
			mtaColPnm		: "required",
			mtaColLnm		: "required",
			dataType		: "required",
			priRsn			: "required",
			objDescnCol		: "required"
		},
		messages: {
			mtaColPnm		: requiremessage,
			mtaColLnm		: requiremessage,
			dataType		: requiremessage,
			priRsn			: requiremessage,
			objDescnCol		: requiremessage
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

    /* $("#priRsnSearchPop").click(function(){
		var param = "sFlag=COL";
		openLayerPop ("<c:url value='/meta/mta/popup/nopenRsnSearchPop.do' />", 800, 600, param);
    }); */

	
	//폼 저장 버튼 초기화...
	$('#btnColRowSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = col_sheet.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmColInput #rvwConts").val());
			//col_sheet.SetCellValue(srow, "rvwConts", $("#frmColInput #rvwConts").val());
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
			//if (isBlankStr($("#frmColInput #pkOrd").val())) {
// 				alert("PK여부를 체크하시면 PK순서를 입력해야 한다.");
				//showMsgBox("ERR", "<s:message code="ERR.PKYN" />");
			//	return;
			//}
// 			alert($("#frmColInput #nonulYn").is(":checked"));
			//if(!$("#frmColInput #nonulYn").is(":checked")) {
			//	showMsgBox("ERR", "<s:message code="ERR.NOTNULL" />");
		//		return;
		//	}
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
		$("#colOrd").prop("readonly", false);
		
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

	$("#frmColInput #pkYn").val('${result.pkYn}');
// 	$("#frmColInput #fkYn").val('${result.fkYn}');
	$("#frmColInput #nonulYn").val('${result.nonulYn}');
	$("#frmColInput #openYn").val('${result.openYn}');
	$("#frmColInput #prsnInfoYn").val('${result.prsnInfoYn}');
	$("#frmColInput #encTrgYn").val('${result.encTrgYn}');

	/*
	//check box 값 초기화...
	chkformsetup($("#frmColInput #pkYn"), '${result.pkYn}');
	chkformsetup($("#frmColInput #fkYn"), '${result.fkYn}');
	chkformsetup($("#frmColInput #nonulYn"), '${result.nonulYn}');
	chkformsetup($("#frmColInput #openYn"), '${result.openYn}');
	chkformsetup($("#frmColInput #prsnInfoYn"), '${result.prsnInfoYn}');
	chkformsetup($("#frmColInput #encTrgYn"), '${result.encTrgYn}');

	if("${result.pkYn}" == "Y") $("#frmInput #pkYn").prop('checked', true);
	if("${result.fkYn}" == "Y") $("#frmInput #fkYn").prop('checked', true);
	if("${result.nonulYn}" == "Y") $("#frmInput #nonulYn").prop('checked', true);
	if("${result.openYn}" == "Y") $("#frmInput #openYn").prop('checked', true);
	if("${result.prsnInfoYn}" == "Y") $("#frmInput #prsnInfoYn").prop('checked', true);
	if("${result.encTrgYn}" == "Y") $("#frmInput #encTrgYn").prop('checked', true);  */

});


//추천용어 구현전
$("#btnItem").click(function(){
	var param = "";

// 	alert("추천용어 구현전");
		
	//openLayerPop ("<c:url value='/meta/stnd/popup/stnditemdivision_pop.do' />", 800, 500, param);
//		var popup = OpenWindow("<c:url value="/meta/stnd/sditmdvcanlst_pop.do"/>"+param,"sditmGen","800","600","yes");
//		popup.focus();
    openLayerPop ("<c:url value='/meta/mta/popup/rcmdTermSearchpop.do' />", 1200, 500, param);
	rcmdTermSearch_pop.jsp
});


//비공개사유 팝업 리턴값 처리
/* function returnNopenRstPopCol (ret) {
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmColInput #priRsn").val(retjson.codeCd);
	$("#frmColInput #priRsnCdNm").val(retjson.codeLnm);
	
} */

function returnRcmdtermPop(ret){
	$("#mtaColLnm").val(ret.TERM_NM);
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

		<input type="hidden" id="mtaTblId" 	name="mtaTblId" 	value="${result.mtaTblId}" >
		<input type="hidden" id="mtaColId" 	name="mtaColId" 	value="${result.mtaColId}" >	  	
	  	<input type="hidden" id="colOrd" name="colOrd" value="${result.colOrd }"/>
	  	<input type="hidden" id="pkOrd" name="pkOrd" value="${result.pkOrd }" />
	  	
	  	<input type="hidden" id="rqstDcd"   name="rqstDcd"  value="${result.rqstDcd}"  />	  
	  	<input type="hidden" id="regTypCd"  name="regTypCd" value="${result.regTypCd}" />	  
	  	<input type="hidden" id="vrfCd"     name="vrfCd"    value="${result.vrfCd}" />	
	  	<input type="hidden" id="vrfRmk"    name="vrfRmk"  />
	  	
	  	<input type="hidden" id="rvwStsCd"  name="rvwStsCd" />  	  
	  	<input type="hidden" id="rvwConts"  name="rvwConts"  /> 	  		  	
	  	
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<%-- <tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" /> --%>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	 <div class="stit"><s:message code="CLMN.INFO" /></div> <!-- 컬럼 정보 -->
	 <div style="clear:both; height:5px;"><span></span></div>
     <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
     <div class="tb_basic">
         <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
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
                     <th scope="row" class="th_require g_ico g_ico1"><label>컬럼명</label><span class="ico_ga">&nbsp;</span> </th> <!-- 컬럼명 -->
                     <td>
                     	<input type="text" id="mtaColPnm" name="mtaColPnm" class="wd200" value="${result.mtaColPnm }" readonly /> <!-- 물리명 -->                     	
                     </td>
                     <th scope="row" class="th_require g_ico g_ico1"><label>컬럼한글명</label><span class="ico_ga">&nbsp;</span> </th> <!-- 컬럼명 -->
                     <td>
                     	<input type="text" id="mtaColLnm" name="mtaColLnm" class="wd200" value="${result.mtaColLnm }" /> <!-- 논리명 -->
                     	<button class="btnSearchPop" type="button" id="btnItem"	name="btnItem">추천용어</button>                     	
                     </td>
                 </tr>
                 <tr class="ht50">
                     <th scope="row" class="th_require"><label for="objDescnCol">컬럼<s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                     <td colspan="3"><textarea class="wd98p ht50" id="objDescnCol" name="objDescn" accesskey="">${result.objDescn }</textarea></td>
                 </tr>
                <%--  <tr>
                     <th scope="row"><label for="colRelEntyNm">연관엔티티명</label></th>
                     <td>
                     	<input type="text" id="colRelEntyNm" name="colRelEntyNm" class="wd200" value="${result.colRelEntyNm }" />  
                     </td>
                     <th scope="row"><label for="colRelAttrNm">연관속성명</label></th> 
                     	<td>
                     	<input type="text" id="colRelAttrNm" name="colRelAttrNm" class="wd200" value="${result.colRelAttrNm }" /> 
                     </td>
                 </tr> --%>
                 <%-- <tr>
                     <th scope="row" class="th_require"><label for="colOrd"><s:message code="CLMN.SQNC" /></label></th> <!-- 컬럼순서 -->
                     <td colspan="3"><input type="text" id="colOrd" name="colOrd" class="wd200" value="${result.colOrd }" readOnly/></td>
                 </tr> 
                 <tr>
                     <th scope="row" class="th_require"><label><s:message code="DATA" /></label></th><!-- 데이터 -->
                     <td colspan="3">
                     	<span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span><s:message code="DATA.TY" /> : <input type="text" id="dataType" name="dataType" value="${result.dataType }" /> <!-- 데이터타입 -->
                     	<span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span><s:message code="DATA.LNGT" /> : <input type="text" id="dataLen" name="dataLen"  value="${result.dataLen }" /> <!-- 데이터길이 -->
                     	<span class="ui-icon ui-icon-carat-1-e frm_input_icon"></span><s:message code="DATA.FPOINT.LNGT" /> : <input type="text" id="dataScal" name="dataScal" value="${result.dataScal }" /> <!-- 데이터소수점길이 -->
                     </td>
                 </tr> --%>
                 <tr>
                     <th scope="row" class="th_require g_ico g_ico1"><label><s:message code="DATA.TY" /><span class="ico_ga">&nbsp;</span> </label></th><!-- 데이터 -->
                     <td colspan="3">
                     	<input type="text" id="dataType" name="dataType" class="wd200" value="${result.dataType }" readonly /> <!-- 데이터타입 -->
                     </td>
                 </tr>
                  <tr>
                     <th scope="row" class="g_ico g_ico1"><label><s:message code="DATA.LNGT" /><span class="ico_ga">&nbsp;</span> </label></th><!-- 데이터 -->
                     <td colspan="3">
                     	<input type="text" id="dataLen" name="dataLen" class="wd200" value="${result.dataLen }" readonly /> <!-- 데이터길이 -->
                     </td>
                 </tr>
                 <tr>
            <%--          <th scope="row"><label for="defltVal"><s:message code="DEFAULT.VAL" /></label></th> <!-- DEFAULT 값 -->
                     <td><input type="text" class="wd300" id="defltVal" name="defltVal" value="${result.defltVal }" /></td> --%>
                     <th scope="row"><label for="dataFmt">데이터포맷</label></th> 
                     <td colspan="3">
                         <input type="text" id="dataFmt" name="dataFmt" class="wd200" value="${result.dataFmt }"/>
                     </td>
                 </tr>
                 <tr>
                 	<th scope="row" class="g_ico g_ico1"><label for="nonulYn"><s:message code="NOTNULL.YN" /><span class="ico_ga">&nbsp;</span> </label></th> <!-- NOT NULL 여부 -->
                     <td colspan="3">
                     	<select id="nonulYn" name="nonulYn" disabled >
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   		</select>
                   		<%--
	                     <span class="input_check">
	                         <input type="checkbox" id="nonulYn" name="nonulYn" value="Y"/><s:message code="NOTNULL.YN" />
	                     </span> <!-- NOTNULL 여부 -->
                     </td>
                      --%>
                 </tr>
                 <tr>
                     <th scope="row" class="th_require g_ico g_ico1"><label for="pkYn"><s:message code="PK.INFO" /><span class="ico_ga">&nbsp;</span> </label></th><!-- PK 정보 -->
                     <td>
                     	<select id="pkYn" name="pkYn" disabled>
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   		</select>
                     	<%-- <span class="input_check"><input type="checkbox" id="pkYn" name="pkYn" value="Y"/> <s:message code="PK.YN" /> </span> <!-- PK 여부 --> --%>                     
                     </td>
                     <th scope="row" class="g_ico g_ico1"><label for="fkYn"><s:message code="FK.INFO" /></label><span class="ico_ga">&nbsp;</span> </th><!-- FK 정보 -->
                     <td>
                     	<%-- <select id="fkYn" name="fkYn" disabled>
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   		</select> --%>
                     	<%-- <span class="input_check"><input type="checkbox" id="fkYn" name="fkYn" value="Y"/> <s:message code="FK.YN" /> </span> <!-- FK 여부 --> --%>
                     	<input type="text" id="fkYn" name="fkYn" class="wd95p" value="${result.fkYn }"/>
                     </td>
                 </tr>
                 <tr>
                     <th scope="row" class="g_ico g_ico1"><label for="constCnd">제약조건</label><span class="ico_ga">&nbsp;</span> </th> 
                     <td>
                         <input type="text" id="constCnd" name="constCnd" class="wd200" value="${result.constCnd }"/>
                     </td>
                     <th scope="row" class="th_require"><label for="openYn">공개여부</label></th> <!-- 암호화 여부 -->
                     <td>
                     	<select id="openYn" name="openYn">
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   		</select>
                     	<%--<span class="input_check"><input type="checkbox" id="openYn" name="openYn" value="Y"/>개방여부</span>  --%>
                     </td>
                 </tr>
                 <tr>
                     <th scope="row" class="th_require"><label for="prsnInfoYn">개인정보여부</label></th>
                     <td>
                         <select id="prsnInfoYn" name="prsnInfoYn" >
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   		</select>
                    	<%--  <span class="input_check"><input type="checkbox" id="prsnInfoYn" name="prsnInfoYn" value="Y"/>개인정보여부</span> --%> 
                     </td>
                 	<th scope="row" class="th_require"><label for="encTrgYn">암호화여부</label></th>
                     <td>
                     	<select id="encTrgYn" name="encTrgYn" >
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   		</select>
                     	<%-- <span class="input_check"><input type="checkbox" id="encTrgYn" name="encTrgYn" value="Y"/>암호화여부</span>  --%>
                    </td>
                 </tr>
                 <%-- <tr>
                     <th scope="row" class="th_require"><label for="priRsn">비공개사유</label></th> 
                     <td colspan="3"><textarea class="wd98p" id="priRsn" name="priRsn" accesskey="">${result.priRsn }</textarea></td>
                 </tr> --%>
                 <tr class="ht50">
                    <%-- <th scope="row" class="th_require"><label for="priRsn">비공개사유</label></th>
                    <td colspan="3"> 
						<input type="hidden" id="priRsn" name="priRsn" value="${result.priRsn}" readonly="readonly"/>
						<span class="input_inactive">
						    <input type="text" id="priRsnCdNm" name="priRsnCdNm"  readonly value="${result.priRsnCdNm }" style="width:80%;" /> 							
						</span>
					 	<button class="btnDelPop" ><s:message code="DEL" /></button><!-- 삭제 -->
					 	<button class="btnSearchPop" id="priRsnSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
					</td> --%>
						<th scope="row" class="th_require"><label for="priRsn">비공개사유</label></th>
                       	<td colspan="3">
                       		<select id="priRsn" name="priRsn" class="wd400">
	                            <c:forEach var="code" items="${codeMap.nopenRsnCd}" varStatus="status" >
	                            	<option value="${code.codeCd}" >${code.codeLnm}</option>
	                            </c:forEach>
	                      	</select>
	               		</td>
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
		<button class="btn_frm_save" id="btnColRowSave" name="btnColRowSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_frm_reset" id="btnColReset" name="btnColReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>
</div>
</body>
</html>