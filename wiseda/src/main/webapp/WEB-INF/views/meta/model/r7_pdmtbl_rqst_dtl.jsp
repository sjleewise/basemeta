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
<title><s:message code="PHYC.MDEL.REG.DTL.INFO"/></title> <!-- 물리모델등록상세정보 -->

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
			pdmTblPnm	: "required",
			pdmTblLnm 	: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			pdmTblPnm	: requiremessage,
			pdmTblLnm 	: requiremessage
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
	
	//주제영역 검색 팝업 호출
	$("#subjSearchPop").click(function(){
		var param = ""; //$("form#frmInput").serialize();
		openLayerPop ("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", 800, 600, param);
// 		OpenWindow("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", "dkafda",  800, 600, "yes");
// 		openSearchPop("<c:url value='/meta/model/pop/subjSearchPop.do' />", param);
    });
	
	//담당자 검색 팝업 호출
	$("#userPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
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
	
	//check box 값 초기화...
	chkformsetup($("#frmInput #stdAplYn"), '${result.stdAplYn}');
	chkformsetup($("#frmInput #partTblYn"), '${result.partTblYn}');
	chkformsetup($("#frmInput #dwTrgTblYn"), '${result.dwTrgTblYn}');
	

});

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #subjId").val(retjson.subjId);
	$("#frmInput #fullPath").val(retjson.fullPath);
	
}

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
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit"><s:message code="SUBJ.TRRT.INFO" /></div> <!-- 주제영역 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<legend><s:message code="SUBJ.TRRT.INFO" /></legend> <!-- 주제영역정보 -->
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
			<caption><s:message code="SUBJ.TRRT.INFO" /></caption> <!-- 주제영역정보 -->
			<colgroup>
			<col style="width:15%;" />
			<col style="width:85%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><s:message code="SUBJ.TRRT" /></th><!-- 주제영역 -->
					<td colspan="3">
						<span class="input_inactive"><input type="hidden" id="subjId" name="subjId" value="${result.subjId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd300" id="fullPath" name="fullPath"  value="${result.fullPath}" readonly="readonly"/></span>
						 	<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						 	<button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
<!-- 						<input type="text" id="mdlLnm" name="mdlLnm" /> -->
<!-- 						<input type="text" id="uppSubjLnm" name="uppSubjLnm" /> -->
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
                       <th scope="row" class="th_require"><label for="pdmTblPnm"><s:message code="TBL.NM.PHYC.NM" /></label></th> <!-- 테이블명(물리명) -->
                       <td ><input type="text" id="pdmTblPnm" name="pdmTblPnm" value="${result.pdmTblPnm }" title="<s:message code='MSG.TBL.NM.INPT' />" /></td> <!-- 테이블명은 반드시 입력해야 합니다. -->
                       <th scope="row"><label for="bfPdmTblPnm"><s:message code='BFR.TBL.NM'/></label></th> <!--이전 테이블명 -->
                       <td ><input type="text" id="bfPdmTblPnm" name="bfPdmTblPnm" value="${result.bfPdmTblPnm }"  /></td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM.LGC.NM" /></label></th> <!-- 테이블명(논리명) -->
                       <td><input type="text" id="pdmTblLnm" name="pdmTblLnm" value="${result.pdmTblLnm }"  /></td>
                       <th scope="row"><label for="crgUserNm"><s:message code="CHG.R" /></label></th> <!-- 담당자 -->
						<td> 
							<span class="input_inactive"><input type="text" id="crgUserId" name="crgUserId" value="${result.crgUserId}" /></span>
							<span class="input_inactive"><input type="text" id="crgUserNm" name="crgUserNm" value="${result.crgUserNm}" /></span>
							<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
							<button class="btnSearchPop" id="userPop" name="userPop"><s:message code="SRCH" /></button> <!-- 검색 -->
						</td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn }</textarea></td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><s:message code="APL.YN"/></th> <!-- 적용 여부 -->
                       <td colspan="3">	
                           <input type="checkbox" id="stdAplYn" name="stdAplYn"     value="Y" /><span class="input_check"><s:message code="STRD.APL.YN" /></span> <!-- 표준적용여부 -->
                       </td>
                   </tr>
                   <tr>
                   	  <th scope="row"><label for="ctdFcy"><s:message code="STGE.CYCL"/></label></th> <!-- 보관주기 -->
                      <td><input type="text" id="ctdFcy" name="ctdFcy" value="${result.ctdFcy }" /></td>
                   	  <th scope="row"><label for="bckFcy"><s:message code="BAUP.CYCL"/></label></th> <!-- 백업주기 -->
                      <td><input type="text" id="bckFcy" name="bckFcy" value="${result.bckFcy }" /></td>
                   </tr>
                   <tr>
                   	  <th scope="row"><label for="delCri"><s:message code="DEL.BASE"/></label></th> <!-- 삭제기준 -->
                      <td><input type="text" id="delCri" name="delCri" value="${result.delCri }" /></td>
                   	  <th scope="row"><label for="delMtd"><s:message code="DEL.MTH"/></label></th> <!-- 삭제방법 -->
                      <td><input type="text" id="delMtd" name="delMtd" value="${result.delMtd }" /></td>
                   </tr>
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
