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
<title>DDL시퀀스상세정보</title>

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
			ddlSeqPnm	: "required",
			dbConnTrgPnm 	: "required", 
			dbSchPnm 	: "required",
			nmRlTypCd 	: "required", 
			usTypCd 	: "required",
			incby 	: "required", 
			strtwt 	: "required", 
			minval 	: "required", 
			maxval 	: "required",
			aplReqdt : "required", 
			grtLst : "required", 
			seqInitCd	: {
				required: function(){
					if($('#usTypCd').val() == 'D' ){
						return true;
					}
					return false;
				}
			},
			bizIfnc			: {
				required: function(){
					if($('#seqInitCd').val() == '8' || $('#seqInitCd').val() == '9'){
						return true;
					}
					return false;
				}
			},
			rqstResn		: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			ddlSeqPnm	: requiremessage,
			dbConnTrgPnm 	: requiremessage, 
			dbSchPnm 	: requiremessage,
			nmRlTypCd 	: requiremessage,
			usTypCd 	: requiremessage,
			incby 	: requiremessage,
			strtwt 	: requiremessage,
			minval 	: requiremessage,
			maxval 	: requiremessage,
			aplReqdt 	: requiremessage,
			seqInitCd 	: requiremessage,
			bizIfnc 	: requiremessage,
			grtLst 	: requiremessage,
			rqstResn	: requiremessage
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	

	//폼 저장 버튼 초기화...
	$('#btnGridSave').button({

	}).click(function(event){
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
		
		//요청사유 길이 체크
	/* 	if (chechkByteSize($("#frmInput #rqstResn").val()) < 20) {
			showMsgBox("INF", "요청사유는 한글기준 최소 10자 이상 입력하세요.");
			return;
		} */
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').button({
	}).click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		resetForm($("form#frmInput"));
		//케시사이즈초기값 100	
		$("#frmInput #cacheSz").val("100"); 
		$("#frmInput #cycYn").val("Y"); 

	});
	

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
	
	$("#frmInput #rvwStsCd").val('${result.rvwStsCd}');

	$("#frmInput #nmRlTypCd").val('${result.nmRlTypCd}');
	$("#frmInput #usTypCd").val('${result.usTypCd}');
	$("#frmInput #cycYn").val('${result.cycYn}');
	$("#frmInput #ordYn").val('${result.ordYn}');
	$("#frmInput #aplReqTypCd").val('${result.aplReqTypCd}');
	$("#frmInput #seqClasCd").val('${result.seqClasCd}');
	$("#frmInput #seqInitCd").val('${result.seqInitCd}');
	
	
	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();
	
 	$( "#aplReqdt" ).datepicker();
	
	
	//요청서 상태에 따른 버튼 show //sr번호/프로젝트번호만 
	setDispRqstSrMngNoButton($("#mstFrm #rqstStepCd").val());	
	//DDL이관
	if($("#mstFrm #bizDcd").val() == "DTT" || $("#mstFrm #bizDcd").val() == "DTS"){
		$("#btnGridSave").hide();
		$("#btnReset").hide();
// 		$("#aplReqDtAllApply").show();
	}else{
// 		$("#aplReqDtAllApply").hide();
	}
	

});


//DBMS 정보 팝업 리턴값 처리
function returnDbSchemaPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);

	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbSchPnm").val(retjson.dbSchPnm);

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
<%-- 	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" /> --%>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	

	<div style="clear:both; height:10px;display:none"><span></span></div>
	<div class="stit"  style="display:none">DBMS 정보</div>
	<div style="clear:both; height:5px;display:none"><span></span></div>
	<legend>DBMS 정보</legend>
	<div class="tb_basic" style="display:none">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>DBMS 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require">DBMS 정보</th>
					<td colspan="3">
						<span class="input_inactive"><input type="hidden" class="wd100" id="dbSchId" name="dbSchId"  value="${result.dbSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbConnTrgPnm" name="dbConnTrgPnm"  value="${result.dbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbSchPnm" name="dbSchPnm"  value="${result.dbSchPnm}" readonly="readonly"/></span>
						<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="DbmsSchemaPop">검색</button>
					</td>
					
					
				</tr>
			</tbody>
		</table>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">DBMS 소스 타겟 정보</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>DBMS 소스 타겟 정보</legend>
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>DBMS 소스 타겟 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="*" />              
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require">DBMS / 스키마(소스)</th> <!-- DBMS 정보(소스) -->
					<td colspan="3">
						<span class="input_inactive"><input type="hidden" class="wd100" id="srcDbSchId" name="srcDbSchId"  value="${result.srcDbSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="srcDbConnTrgPnm" name="srcDbConnTrgPnm"  value="${result.srcDbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="srcDbSchPnm" name="srcDbSchPnm"  value="${result.srcDbSchPnm}" readonly="readonly"/></span>
					</td>
					<th scope="row" class="th_require">DBMS / 스키마(타겟)</th> <!-- DBMS 정보(타겟) -->
					<td>					    
						<span class="input_inactive"><input type="text" class="wd100" id="tgtDbConnTrgPnm" name="tgtDbConnTrgPnm"  value="${result.tgtDbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="tgtDbSchPnm" name="tgtDbSchPnm"  value="${result.tgtDbSchPnm}" readonly="readonly"/></span>
						
						<input type="hidden" id="tgtDbConnTrgId" name="tgtDbConnTrgId"  value="${result.tgtDbConnTrgId}" readonly />
						<input type="hidden" id="tgtDbSchId" name="tgtDbSchId"  value="${result.tgtDbSchId}" readonly />
					</td>
				</tr> 
			</tbody>
		</table>
	</div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit">시퀀스 정보</div>
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend>머리말</legend>
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
               <caption>
               시퀀스 이름
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>

                   <tr>
                       <th scope="row" class="th_require"><label for="ddlSeqPnm">시퀀스물리명</label></th>
                       <td colspan="3"><input type="text" id="ddlSeqPnm" name="ddlSeqPnm"  class="wd300" value="${result.ddlSeqPnm }" readonly/></td>
                       <th scope="row" style="display:none;"><label for="ddlSeqLnm">시퀀스논리명</label></th>
                       <td style="display:none;"><input type="text" id="ddlSeqLnm" name="ddlSeqLnm"  class="wd300"  value="${result.ddlSeqLnm }" readonly/></td>
                       
                   </tr>
					<tr>
                       <th scope="row" class="th_require" ><label for="incby">INCREMENT BY (증가값)</label></th>
                       <td><input type="text" id="incby" name="incby"  class="wd300" value="${result.incby }" readonly/></td>
                       <th scope="row" class="th_require" ><label for="strtwt">START WITH</label></th>
                       <td><input type="text" id="strtwt" name="strtwt"  class="wd300"  value="${result.strtwt }" readonly/></td>
                   </tr>
                   <tr>
                       <th scope="row" class="th_require" ><label for="minval">MIN VALUE</label></th>
                       <td><input type="text" id="minval" name="minval"  class="wd300"  value="${result.minval }" readonly/></td>
                       <th scope="row" class="th_require" ><label for="maxval">MAX VALUE</label></th>
                       <td><input type="text" id="maxval" name="maxval"  class="wd300" value="${result.maxval }" readonly/></td>
                       
                   </tr>
                   <tr>
                       <th scope="row" ><label for="cycYn">CYCLE 여부</label></th>
                       <td>
                       		<select id="cycYn"  name="cycYn" value="${result.cycYn}" class="wd100" disabled>
							       <option value=""></option>
							       <option value="Y" selected>Y</option>
							       <option value="N">N</option>
							</select>                       		
                       </td>
                       <th scope="row" ><label for="ordYn">ORDER 여부</label></th>
                       <td>
                       		<select id="ordYn"  name="ordYn" value="${result.ordYn}" class="wd100" disabled>
							       <option value="" ></option>
							       <option value="Y">Y</option>
							       <option value="N" selected>N</option>
							</select>                       		
                       </td>
                   </tr>
                   <tr>
                   	   <th scope="row" ><label for="cacheSz">CACHE SIZE</label></th>
                       <td colspan="3">
                       		<input type="text" id="cacheSz" name="cacheSz"  class="wd300" value="${result.cacheSz }" readonly/>
                       </td>
                   </tr>
		
                   <tr>
                       <th scope="row"><label for="objDescn">설명</label></th>
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly>${result.objDescn }</textarea></td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="scrtInfo">스크립트정보</label></th>
                       <td colspan="3"><textarea id="scrtInfo" name="scrtInfo" class="wd98p" rows="3" readonly>${result.scrtInfo }</textarea></td>
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
