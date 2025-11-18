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
<title><s:message code="DDL.IDEX.CLMN.DTL.INFO" /></title> <!-- DDL인덱스컬럼상세정보 -->

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
			ddlIdxColPnm	: "required",
			ddlIdxColLnm 	: "required",
			ddlIdxColOrd	: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			ddlIdxColPnm	: requiremessage,
			ddlIdxColLnm 	: requiremessage,
			ddlIdxColOrd	: requiremessage
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
		showMsgBox("CNF", message, 'SaveColRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnColReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
		$("form[name=frmColInput]")[0].reset();
		
	});
	
    //표준용어 검색팝업 이벤트
    $("#ddlColPop").click(function(){
    	
    	var param = "ddlTblPnm="+$("#frmInput #ddlTblPnm").val(); //$("form#frmInput").serialize();
    		param += "&dbConnTrgId="+$("#frmInput #dbConnTrgId").val(); //$("form#frmInput").serialize();
    		param += "&dbSchId="+$("#frmInput #dbSchId").val(); //$("form#frmInput").serialize();
    	alert(param);
		openLayerPop ("<c:url value='/meta/ddl/popup/ddlcol_pop.do' />", 800, 600, param);
//			var param = $("form#frmColInput").serialize(); //$("form#frmColInput").serialize();
//			openSearchPop("<c:url value='/meta/stnd/pop/sditmSearch_pop.do' />");
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
	$("#frmColInput #ddlIdxColOrd").val('${result.ddlIdxColOrd}');
	$("#frmColInput #sortTyp").val('${result.sortTyp}');

});


//팝업 리턴값 처리...
function returnDdlColPop(ret){
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmColInput #ddlIdxColPnm").val(retjson.ddlColPnm);
	$("#frmColInput #ddlIdxColLnm").val(retjson.ddlColLnm);
	
}

//DDL 인덱스 간편추가 팝업 리턴값 처리...
function returnDdlColAutoPop(ret){
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmColInput #ddlIdxColPnm").val(retjson.ddlColPnm);
	$("#frmColInput #ddlIdxColLnm").val(retjson.ddlColLnm);
	
	//로직 이동 이상익
	//컬럼순서 디폴트값 세팅 로직
	var lastrow = col_sheet.LastRow();
	//var param = col_sheet.GetRowJson(lastrow);
	//컬럼목록 미존재시 컬럼순서 1부터 시작
	var validx = 1;
	if(lastrow > 0){
			validx = col_sheet.GetRowJson(1).ddlIdxColOrd;	//첫째줄 값으로 비교 시작
			var col = null;
			for(var i = 1 ; i <= lastrow ; i++){		// 0부터 시작하면 컬럼명부터 나오기때문에 1부터 시작.
				col = col_sheet.GetRowJson(i).ddlIdxColOrd;	
				if(col > validx){						// 비교해서 앞의 값보다 크면 저장
					validx = col;
				}
			}
		validx = validx+1;								// 제일 큰값 + 1
	}
	
//컬럼순서에 값 세팅
	$("#frmColInput #ddlIdxColOrd").val(validx);
	$("#frmColInput #rqstDcd").val("CU").attr("selected","selected");		//요청구분 기본값 세팅
	
	
	doAction('SaveColRow');
	
	
}

</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_col_form_div">
     <form name="frmColInput" id="frmColInput" method="post" >
     	<input type="hidden" id="rqstSno" 	name="rqstSno" 		value="${result.rqstSno}" >
     	<input type="hidden" id="rqstDtlSno" 	name="rqstDtlSno" 	value="${result.rqstDtlSno}" >
     	<input type="hidden" id="rqstDtlDtlSno" 	name="rqstDtlDtlSno" 	value="${result.rqstDtlDtlSno}" >
	  	<input type="hidden" id="ibsStatus" 	name="ibsStatus" 	value="${saction }" > 	
	  	<input type="hidden" id="ddlIdxId" 	name="ddlIdxId" 	value="${result.ddlIdxId}" >


    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
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
<!--                  <tr> -->
<!--                      <th scope="row"><label for="pdmColId">컬럼ID</label></th> -->
<%--                      <td colspan="3"><span class="input_inactive"><input type="text" id="pdmColId" name="pdmColId"/></span></td> --%>
<!--                  </tr> -->
                 <tr>
                     <th scope="row" class="th_require"><label><s:message code="IDEX.CLMN.NM.PHYC.NM" /></label></th> <!-- 인덱스컬럼명(물리명) -->
                     <td colspan="3">
                        <input type="hidden" id="ddlColId" name="ddlColId" value="${result.ddlColId }" />
                     	<input type="text" id="ddlIdxColPnm" name="ddlIdxColPnm" value="${result.ddlIdxColPnm }" />
                     	<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
                      	<button class="btnSearchPop" id="ddlColPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                     </td>
                 </tr>
				 <tr>
                     <th scope="row" class="th_require"><label><s:message code="IDEX.CLMN.NM.LGC.NM" /></label></th> <!-- 인덱스컬럼명(논리명) -->
                     <td colspan="3">
                     	<input type="text" id="ddlIdxColLnm" name="ddlIdxColLnm" value="${result.ddlIdxColLnm }" />
                     </td>
                 </tr>
<!--                  <tr> -->
<!--                      <th scope="row"><label for="sditmId">표준용어</label></th> -->
<%--                      <td colspan="3"><span class="input_inactive"><input type="text" id="sditmId" name="sditmId" /> --%>
<!--                      		<button class="btnDelPop" >삭제</button> -->
<!--                       	<button class="btnSearchPop" id="stndSearchPop">검색</button> -->
<%--                      </span></td> --%>
<!--                  </tr> -->
                 <tr>
                     <th scope="row" class="th_require"><label for="ddlIdxColOrd"><s:message code="IDEX.CLMN.SQNC" /></label></th> <!-- 인덱스컬럼순서 -->
                     <td ><input type="text" id="ddlIdxColOrd" name="ddlIdxColOrd" value="${result.ddlIdxColOrd }" /></td>
                     <th scope="row"><label for="sortTyp"><s:message code="ARY.SQNC" /></label></th><!-- 정렬순서 -->
                     <td >
                     	<select id="sortTyp" name="sortTyp" class="wd70" value="${result.sortTyp}">
                        	<c:forEach var="code" items="${codeMap.idxColSrtOrdCd}" varStatus="status" >
                               <option value="${code.codeCd}">${code.codeLnm}</option>
                            </c:forEach>
                        </select>
                     </td>
                 </tr>
                 
                 <tr>
                     <th scope="row"><label for="objDescnCol"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
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
		<button class="btn_frm_save" id="btnColRowSave" name="btnColRowSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_frm_reset" id="btnColReset" name="btnColReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>
</div>
</body>
</html>
