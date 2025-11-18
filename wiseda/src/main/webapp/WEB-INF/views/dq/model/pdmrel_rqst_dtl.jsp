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
<title><s:message code="PHYC.MDEL.RLT.DTL.INFO" /></title> <!-- 물리모델관계상세정보 -->

<script type="text/javascript">

$(document).ready(function(){
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmRelInput").validate({
		rules: {
			rqstDcd		: "required",
			pdmRelPnm	: "required",
			relTypCd	: "required",
			crdTypCd	: "required",
			paOptTypCd	: "required",
			paSubjPnm	: "required",
			paEntyPnm	: "required",
			paAttrPnm	: "required",
			chAttrPnm	: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			pdmRelPnm	: requiremessage,
			relTypCd	: requiremessage,
			crdTypCd	: requiremessage,
			paOptTypCd	: requiremessage,
			paSubjPnm	: requiremessage,
			paEntyPnm	: requiremessage,
			paAttrPnm	: requiremessage,
			chAttrPnm	: requiremessage
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
	$('#btnRelRowSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = rel_sheet.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmColInput #rvwConts").val());
			rel_sheet.SetCellValue(srow, "rvwConts", $("#frmRelInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmRelInput").valid()) return false;
		
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRelRow');
		
// 		doAction("SaveRelRow");
		
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnRelReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
		$("form[name=frmRelInput]")[0].reset();
		
	});
	
    //부모주제영역 검색팝업 이벤트
    $("#paSubjPop").click(function(){
    	var param = "sFlag=PDMREL";
		$("#frmRelInput #subjFlag").val("PA");
		openLayerPop ("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", 800, 600, param);
//			var param = $("form#frmColInput").serialize(); //$("form#frmColInput").serialize();
//			openSearchPop("<c:url value='/meta/stnd/pop/sditmSearch_pop.do' />");
    });
    //부모 엔터티 검색팝업 이벤트
    $("#paEntyPop").click(function(){
    	var param = "sFlag=PDMREL";
    		param += "&fullPath="+$("#frmRelInput #paSubjPnm").val();
		$("#frmRelInput #subjFlag").val("PA");
		openLayerPop ("<c:url value='/meta/model/popup/pdmtblSearchPop.do' />", 800, 600, param);
//			var param = $("form#frmColInput").serialize(); //$("form#frmColInput").serialize();
//			openSearchPop("<c:url value='/meta/stnd/pop/sditmSearch_pop.do' />");
    });
    //부모 속성명 검색팝업 이벤트
    $("#paAttrPop").click(function(){
    	var param = "sFlag=PDMREL";
    		param += "&pdmTblLnm="+$("#frmRelInput #paEntyPnm").val();
		$("#frmRelInput #subjFlag").val("PA");
		openLayerPop ("<c:url value='/meta/model/popup/pdmcol_pop.do' />", 800, 600, param);
//			var param = $("form#frmColInput").serialize(); //$("form#frmColInput").serialize();
//			openSearchPop("<c:url value='/meta/stnd/pop/sditmSearch_pop.do' />");
    });
    //자식 속성명 검색팝업 이벤트
    $("#chAttrPop").click(function(){
    	var param = "sFlag=PDMREL";
    	    param += "&pdmTblLnm="+$("#frmRelInput #chEntyPnm").val();
//     	    param += "&chSubjPnm="+$("#frmRelInput #chSubjPnm").val();
		$("#frmRelInput #subjFlag").val("CH");
		openLayerPop ("<c:url value='/meta/model/popup/pdmcol_pop.do' />", 800, 600, param);
//			var param = $("form#frmColInput").serialize(); //$("form#frmColInput").serialize();
//			openSearchPop("<c:url value='/meta/stnd/pop/sditmSearch_pop.do' />");
    });
    

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	if(rqststep == "Q" || rqststep == "A") {
		$("button.btn_frm_save, button.btn_frm_reset").hide();
		
		$("#input_rel_form_div .reviewStatus").hide();
	}
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
// 	setDispLstButton(rqststep);
	
	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmRelInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmRelInput #regTypCd").val('${result.regTypCd}');
	$("#frmRelInput #vrfCd").val('${result.vrfCd}');
	
	$("#frmRelInput #relTypCd").val('${result.relTypCd}');
	$("#frmRelInput #crdTypCd").val('${result.crdTypCd}');
	$("#frmRelInput #paOptTypCd").val('${result.paOptTypCd}');
	
	//check box 값 초기화...

	

});



</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_rel_div">
     <form name="frmRelInput" id="frmRelInput" method="post" >
     	<input type="hidden" id="rqstSno" 	name="rqstSno" 		value="${result.rqstSno}" >
     	<input type="hidden" id="rqstDtlSno" 	name="rqstDtlSno" 	value="${result.rqstDtlSno}" >
	  	<input type="hidden" id="ibsStatus" 	name="ibsStatus" 	value="${saction }" >
	  	<input type="hidden" id="subjFlag" name="subjFlag" style="disabled:disabled;" />
<%-- 	  	<input type="hidden" id="chSubjPnm" name="chSubjPnm" value="${result.chSubjPnm }" > --%>
<%-- 	  	<input type="hidden" id="chEntyPnm" name="chEntyPnm" value="${result.chEntyPnm }" > --%>
	  	
    <fieldset>
	
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="RLT.BSIC.INFO" /></div> <!-- 관계 기본정보 -->
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
<!--                    <tr> -->
<!--                        <th scope="row"><label for="pdmTblId">테이블ID</label></th> -->
<%--                        <td colspan="3"><span class="input_inactive"><input type="text" id="pdmTblId" name="pdmTblId"/></span></td> --%>
<!--                    </tr> -->
                   <tr>
                       <th scope="row" class="th_require"><label for="pdmRelPnm"><s:message code="RLT.NM.PHYC.NM" /></label></th> <!-- 관계명(물리명) -->
                       <td ><input type="text" class="wd220" id="pdmRelPnm" name="pdmRelPnm" value="${result.pdmRelPnm }" title="<s:message code='RLT.NM.INPT' />" /></td> <!-- 관계명은 반드시 입력해야 합니다. -->
                       <th scope="row"><label for="pdmRelLnm"><s:message code="RLT.NM.LGC.NM" /></label></th> <!-- 관계명(논리명) -->
                       <td><input type="text" class="wd220"  id="pdmRelLnm" name="pdmRelLnm" value="${result.pdmRelLnm }"  /></td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="relTypCd"><s:message code="RLT.PTRN" /></label></th> <!-- 관계유형 -->
                       <td><select id="relTypCd" class="" name="relTypCd" >
			    				<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							    <c:forEach var="code" items="${codeMap.relTypCd}" varStatus="status">
							    <option value="${code.codeCd}">${code.codeLnm}</option>
							    </c:forEach>
							</select></td>
					   <th scope="row" class="th_require"><label for="crdTypCd"><s:message code="CADINALITY.PTRN" /></label></th> <!-- 카디널리티유형 -->
                       <td><select id="crdTypCd" class="" name="crdTypCd" >
			    				<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							    <c:forEach var="code" items="${codeMap.crdTypCd}" varStatus="status">
							    <option value="${code.codeCd}">${code.codeLnm}</option>
							    </c:forEach>
							</select></td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="paOptTypCd">Parent Optionality <s:message code="TY" /></label></th> <!-- 타입 -->
                       <td colspan="3"><select id="paOptTypCd" class="" name="paOptTypCd" >
			    				<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							    <c:forEach var="code" items="${codeMap.paOptTypCd}" varStatus="status">
							    <option value="${code.codeCd}">${code.codeLnm}</option>
							    </c:forEach>
							</select></td>
					  
                   </tr>
                   <tr>
                       <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn }</textarea></td>
                   </tr>
               </tbody>
           </table>
       </div>
       
    
    <div style="clear:both; height:10px;"><span></span></div>
    <div class="stit"><s:message code="PRNT.ENTY.INFO" /></div> <!-- 부모엔터티 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic" id="relColInfo">
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
                       <th scope="row" class="th_require"><label for="paSubjPnm"><s:message code="PRNT.ENTY.SUBJ.TRRT"/></label></th> <!-- 부모엔터티 주제영역명 -->
                       <td colspan="3"><input type="text" class="wd220" id="paSubjPnm" name="paSubjPnm" readonly value="${result.paSubjPnm }"/>
                       <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
        			   <button class="btnSearchPop" id="paSubjPop"><s:message code="SRCH" /></button> <!-- 검색 -->
        			   </td>
<!--                        <th scope="row"><label for="chSubjPnm">자식엔터티 주제영역명</label></th> -->
<%--                        <td><input type="text" class="wd220" id="chSubjPnm" name="chSubjPnm" readonly value="${result.chSubjPnm }"  /> --%>
<!--                        <button class="btnDelPop" >삭제</button> -->
<!--         			   <button class="btnSearchPop" id="chSubjPop">검색</button> -->
<!--                        </td> -->
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="paEntyPnm"><s:message code="PRNT.ENTY.NM" /></label></th> <!-- 부모엔터티명 -->
                       <td colspan="3"><input type="text" class="wd220" id="paEntyPnm" name="paEntyPnm" value="${result.paEntyPnm }" />
                       <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
        			   <button class="btnSearchPop" id="paEntyPop"><s:message code="SRCH" /></button> <!-- 검색 -->
        			   </td>
<!--                        <th scope="row"><label for="chEntyPnm">자식엔터티명</label></th> -->
<%--                        <td><input type="text" class="wd220" id="chEntyPnm" name="chEntyPnm" value="${result.chEntyPnm }"  /></td> --%>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="paAttrPnm"><s:message code="PRNT.ENTY.ATRB.NM"/></label></th> <!-- 부모엔터티 속성명 -->
                       <td colspan="3"><input type="text" class="wd220" id="paAttrPnm" name="paAttrPnm" value="${result.paAttrPnm }" />
                       <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
        			   <button class="btnSearchPop" id="paAttrPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                       </td>
                   </tr>
                   
               </tbody>
           </table>
       </div>
       
       
       <div style="clear:both; height:10px;"><span></span></div>
    <div class="stit"><s:message code="CHILD.ENTY.INFO" /></div> <!-- 자식엔터티 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic" id="relColInfo">
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
                       <th scope="row" class="th_require"><label for="chSubjPnm"><s:message code="CHILD.ENTY.SUBJ.TRRT"/></label></th> <!-- 자식엔터티 주제영역명 -->
                       <td colspan="3"><span class="input_inactive"><input type="text" class="wd220" id="chSubjPnm" name="chSubjPnm" readonly value="${result.chSubjPnm }"/></span>
        			   </td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="chEntyPnm"><s:message code="CHILD.ENTY.NM" /></label></th> <!-- 자식엔터티명 -->
                       <td colspan="3"><span class="input_inactive"><input type="text" class="wd220" id="chEntyPnm" name="chEntyPnm" value="${result.chEntyPnm }" /></span>
        			   </td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="chAttrPnm"><s:message code="CHILD.ENTY.ATRB.NM"/></label></th> <!-- 자식엔터티 속성명 -->
                       <td colspan="3"><input type="text" class="wd220" id="chAttrPnm" name="chAttrPnm" value="${result.chAttrPnm }"  />
                       <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
        			   <button class="btnSearchPop" id="chAttrPop"><s:message code="SRCH" /></button> <!-- 검색 -->
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
		<button class="btn_frm_save" id="btnRelRowSave" name="btnRelRowSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_frm_reset" id="btnRelReset" name="btnRelReset" ><s:message code="INON" /></button> <!-- 초기화 -->
</div>
</body>
</html>
