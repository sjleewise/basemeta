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
<title><s:message code="TBL.MAPG.DFNT.P.REG.DTL.INFO"/></title> <!-- 테이블매핑정의서등록상세정보 -->

<script type="text/javascript">

$(document).ready(function(){
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.SHORTREQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			rqstDcd		: "required",
			tgtDbPnm 	: "required",
			tgtTblPnm 	: "required",
			mapSno 		: "required",
			srcDbPnm 		: "required",
			srcTblPnm 		: "required"
			
				  
		},
		messages: {
			rqstDcd		: requiremessage,
			tgtDbPnm 	: requiremessage,
			tgtTblPnm	: requiremessage,
			mapSno 		: requiremessage,
			srcDbPnm 		: requiremessage,
			srcTblPnm 		: requiremessage
			
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
// 		$("form[name=frmInput]")[0].reset();
		resetForm($("form#frmInput"));
	});
	
 	$("#divInputBtn").show();
	
	//응용담당자 검색 팝업 호출
	$("#appUserPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	$("#frmInput #userSchTyp").val('app');
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
	
	//전환담당자 검색 팝업 호출
	$("#cnvsUserPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	$("#frmInput #userSchTyp").val('cnvs');
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
	
	//타겟테이블 검색 팝업 호출
	$("#tgtTblNmSearchPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/meta/mapping/popup/tgtTblSearchPop.do'/>", 800, 600, param);
    });
    

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
// 	setDispLstButton(rqststep);
	setDispRqstMainButton(rqststep, null);
	
	
	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	if('${result.rqstDcd}'!=""){$("#frmInput #rqstDcd").val('${result.rqstDcd}');}
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	$("#frmInput #tblMapType").val('${result.tblMapType}');
	

});


//담당자 팝업 리턴값 처리...
function returnUserInfoPop(ret) {
 	//alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	if($("#frmInput #userSchTyp").val() == 'app') //응용담당자
	{
		$("#frmInput #appCrgpId").val(retjson.userId);
		$("#frmInput #appCrgpNm").val(retjson.userNm);
		$("#frmInput #userSchTyp").val('');
		
	}else //전환담당자
	{
		$("#frmInput #cnvsCrgpId").val(retjson.userId);
		$("#frmInput #cnvsCrgpNm").val(retjson.userNm);
		$("#frmInput #userSchTyp").val('');		
	}
}


//타겟테이블팝업 리턴값 처리...
function returnTgtTblInfoPop(ret) {
 	//alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #tgtTblId").val(retjson.tgtTblId);
	$("#frmInput #tgtTblPnm").val(retjson.tgtTblPnm);
	$("#frmInput #tgtTblLnm").val(retjson.tgtTblLnm);
	$("#frmInput #tgtDbPnm").val(retjson.tgtDbPnm);
	$("#frmInput #tgtSubjId").val(retjson.tgtSubjId);
	$("#frmInput #tgtSubjAllPath").val(retjson.tgtSubjAllPath);

}



</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
	  	<input type="hidden" id="userSchTyp" name="userSchTyp" value="" >
		<input type="hidden" id="tgtTblId" name="tgtTblId" value="${result.tgtTblId}" >
		<input type="hidden" id="tgtSubjId" name="tgtSubjId" value="${result.tgtSubjId}" >
		<input type="hidden" id="tgtSubjAllPath" name="tgtSubjAllPath" value="${result.tgtSubjAllPath}" >
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->


	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="MAPG.DFNT.P.INFO"/></div> <!-- 매핑정의서 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
				 <tr>
				 	  <th scope="row" ><label for="mapDfId"><s:message code="MAPG.DFNT.P.ID" /></label></th> <!-- 매핑정의서ID -->
                       <td><span class="input_inactive"><input type="text" id="mapDfId" name="mapDfId" value="${result.mapDfId}"  class="wd98p" readOnly /></span></td>
                       <th scope="row"><label for="mapDfType"><s:message code="MAPG.DFNT.P.PTRN" /></label></th> <!-- 매핑정의서유형 -->
                       <td>
                       	<select id="mapDfType"  name="mapDfType" >
							 <c:forEach var="code" items="${codeMap.mapDfTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="mapSno"><s:message code="MAPG.SRNO" /></label></th> <!-- 매핑일련번호 -->
                       <td ><input type="text" id="mapSno" name="mapSno" value="${result.mapSno}"  /></td>
                       <th scope="row" ><label for="tblMapType"><s:message code="TBL.MAPG.PTRN" /></label></th> <!-- 테이블매핑유형 -->
                       <td>
                       	<select id="tblMapType"  name="tblMapType" >
							 <c:forEach var="code" items="${codeMap.tblMapTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                   </tr>
               </tbody>
           </table>
       </div>
       
       
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="TARG.TOBE.INFO"/></div> <!-- 타겟(TOBE) 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
               	<tr>
              			<th scope="row"><label for="tgtTblLnm"><s:message code="TARG.FLFM.PTRN"/></label></th> <!-- 타겟(TOBE)이행유형 -->
                      	<td >
                       	<select id="tgtFlfType"  name="tgtFlfType" >
							 <c:forEach var="code" items="${codeMap.tgtFlfTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>
                      	</td>
                      	
                  		<th scope="row" class="th_require"><label for="tgtDbPnm"><s:message code="TARG.DB.NM.1"/></label></th> <!-- 타겟(TOBE)DB명 -->
                      	<td ><input type="text" id="tgtDbPnm" name="tgtDbPnm" value="${result.tgtDbPnm}"  /></td>
               	</tr>
				 <tr>
                       	<th scope="row" class="th_require"><label for="tgtTblPnm"><s:message code="TARG.TBL.NM.PHYC.NM"/></label></th> <!-- 타겟(TOBE)테이블명(물리명) -->
                       	<td>
                       		<span class="input_inactive"><input type="text" id="tgtTblPnm" name="tgtTblPnm" value="${result.tgtTblPnm}"  style="width:50%;" /></span>
                      		<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->           		
							<button class="btnSearchPop" id="tgtTblNmSearchPop" name="tgtTblNmSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                       	</td>
                       	<th scope="row"><label for="tgtTblPnm"><s:message code="TARG.TBL.NM.LGC.NM"/></label></th> <!-- 타겟(TOBE)테이블명(논리명) -->
                       	<td>
                      		<span class="input_inactive"><input type="text" id="tgtTblLnm" name="tgtTblLnm" value="${result.tgtTblLnm}"  style="width:50%;" /></span>
                       	</td>
                       	
                   </tr>
               </tbody>
           </table>
       </div>
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="SOUR.INFO"/></div> <!-- 소스(ASIS) 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   <tr>
                       	<th scope="row"><label for="srcSysNm"><s:message code="SOUR.SYS.NM"/></label></th> <!-- 소스(ASIS)시스템명 -->
                       	<td ><input type="text" id="srcSysNm" name="srcSysNm" value="${result.srcSysNm}"  /></td>
                       	<th scope="row"><label for="srcBizNm"><s:message code="SOUR.BZWR.NM"/></label></th> <!-- 소스(ASIS)업무명 -->
                       	<td ><input type="text" id="srcBizNm" name="srcBizNm" value="${result.srcBizNm}"  /></td>
                   </tr>
                   <tr>
                   		<th scope="row" class="th_require"><label for="srcDbPnm"><s:message code="SOUR.ASIS.DB.NM.1"/></label></th> <!-- 소스(ASIS)DB명 -->
                       	<td colspan="3"><input type="text" id="srcDbPnm" name="srcDbPnm" value="${result.srcDbPnm}" /></td>
                   </tr>
                   <tr>
                       	<th scope="row" class="th_require"><label for="srcTblPnm"><s:message code="SOUR.TBL.NM.PHYC.NM"/></label></th> <!-- 소스(ASIS)테이블명(물리명) -->
                       	<td ><input type="text" id="srcTblPnm" name="srcTblPnm" value="${result.srcTblPnm}"  class="wd50p" /></td>
                       	<th scope="row" class=""><label for="srcTblLnm"><s:message code="SOUR.TBL.NM.LGC.NM"/></label></th> <!-- 소스(ASIS)테이블명(논리명) -->
                       	<td ><input type="text" id="srcTblLnm" name="srcTblLnm" value="${result.srcTblLnm}"  class="wd50p" /></td>
                   </tr>
               </tbody>
           </table>
       </div>
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="ADI.INFO"/></div> <!-- 부가 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   <tr>
                   		<th scope="row"><label for="sltSkpCndNm"><s:message code="CHC.SKIP.COND" /></label></th> <!-- 선택/스킵 조건 -->
                       	<td><input type="text" id="sltSkpCndNm" name="sltSkpCndNm" value="${result.sltSkpCndNm}"  style="width:98%;"/></td>
                       	<th scope="row"><label for="jnCndNm"><s:message code="JOIN.COND"/></label></th> <!-- 조인 조건 -->
                       	<td><input type="text" id="jnCndNm" name="jnCndNm" value="${result.jnCndNm}"  style="width:98%;" /></td>
                   </tr>
                   <tr>
                   <th scope="row"><label for="appCrgpNm"><s:message code="APP.CHGR"/></label></th> <!-- 응용담당자 -->
						<td>
							<span class="input_inactive"><input type="text" id="appCrgpId" name="appCrgpId" value="${result.appCrgpId}" readOnly /></span>
							<span class="input_inactive"><input type="text" id="appCrgpNm" name="appCrgpNm" value="${result.appCrgpNm}" readOnly /></span>
						</td>
						<th scope="row"><label for="cnvsCrgpNm"><s:message code="CNVR.CHGR"/></label></th> <!-- 전환담당자 -->
						<td>
							<span class="input_inactive"><input type="text" id="cnvsCrgpId" name=cnvsCrgpId value="${result.cnvsCrgpId}" /></span>
							<span class="input_inactive"><input type="text" id="cnvsCrgpNm" name="cnvsCrgpNm" value="${result.cnvsCrgpNm}" /></span>
							<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
							<button class="btnSearchPop" id="cnvsUserPop" name="cnvsUserPop"><s:message code="SRCH" /></button> <!-- 검색 -->
						</td>
                   </tr>
                    <tr>
                   		<th scope="row"><label for="preFlfDescn"><s:message code="BFHD.FLFM.ATMT"/></label></th> <!-- 사전이행고려사항 -->
                       	<td><input type="text" id="preFlfDescn" name="preFlfDescn" value="${result.preFlfDescn}" style="width:98%;"  /></td>
                       	<th scope="row"><label for="refDescn"><s:message code="REF.MTR" /></label></th> <!-- 참고사항 -->
                       	<td><input type="text" id="refDescn" name="refDescn" value="${result.refDescn}" style="width:98%;" /></td>
                   </tr>
               </tbody>
           </table>
       </div>
       </fieldset>
       </form>
	<!-- 입력폼 끝 -->
	<!-- * 매핑정의서ID는 타겟(TOBE) 테이블명+'_'+매핑일련번호로 자동 치환됩니다.<br>* 테이블매핑유형이 NEW, INITIAL 경우 작성완료 버튼 클릭시 소스(ASIS) 정보는 공백으로  치환됩니다. -->
	<div class="tb_comment"><s:message  code='REQ.TMP.COMM' /></div>
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<!-- 입력폼 버튼... -->
</div>
</body>
</html>
