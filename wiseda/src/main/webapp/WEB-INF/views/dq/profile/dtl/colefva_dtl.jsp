<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">

$(document).ready(function(){
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.SHORTREQUIRED" />";
	
	//폼검증 : 코드테이블 연계분석시
	 $("form[name=frmInputPC02]").validate({
		rules: {
			dqiLnm		: "required"
		},
		messages: {
			dqiLnm		: requiremessage
		}
	});  
	
	//폼검증 : 코드테이블 연계분석시
	 $("form[name=frmInputPC02_cdtbl]").validate({
		rules: {
			cdTblDbConnTrgLdm	: "required",
			cdTblDbcTblNm 		: "required",
			cdTblDbcColNm		: "required",
			dqiLnm		: "required",
			cdTblCdId : {
				required: function(){
					 if($('#cdTblCdIdColNm').val() == ""){  return false;  }
		             return true;
				}
			}
		},
		messages: {
			cdTblDbConnTrgLdm	: requiremessage,
			cdTblDbcTblNm 		: requiremessage,
			cdTblDbcColNm		: requiremessage,
			dqiLnm		: requiremessage,
 			cdTblCdId			: "<s:message code='CD.ID.CLMN.NM.INPT.CD.ID.RQRD.INPT.ITEM'/>"/*코드ID컬럼명 입력시 코드ID는 필수 입력 항목입니다.*/

		}
	});  
	
	
	//코드테이블 정보는 팝업에서 선택 직접입력 불가
	//직접입력시 진단대상, 테이블, 컬럼명 검증해야 함
	$("form[name=frmInputPC02_cdtbl] #cdTblDbConnTrgLdm").css("border-color","transparent") .css("width", "47%");
	$("form[name=frmInputPC02_cdtbl] #cdTblDbSchLdm").css("border-color","transparent") .css("width", "47%");
	$("form[name=frmInputPC02_cdtbl] #cdTblDbcTblNm").css("border-color","transparent") .css("width", "47%");
	$("form[name=frmInputPC02_cdtbl] #cdTblDbcTblKorNm").css("border-color","transparent") .css("width", "47%");
	$("form[name=frmInputPC02_cdtbl] #cdTblDbcColNm").css("border-color","transparent") .css("width", "47%");
	$("form[name=frmInputPC02_cdtbl] #cdTblDbcColKorNm").css("border-color","transparent") .css("width", "47%");
	$("form[name=frmInputPC02_cdtbl] #cdTblCdIdColNm").css("border-color","transparent") .css("width", "47%");
	$("form[name=frmInputPC02_cdtbl] #cdTblCdIdColKorNm").css("border-color","transparent") .css("width", "47%");
	
	
	//사용자 정의 유효값 초기화
	initUserDfGrid();
	
	//유효값 유형에 따른 입력폼 변경
	$("form[name=frmInputPC02] #efvaAnaKndCd").change(function(){
		setFormByEfvaKndCd($("form[name=frmInputPC02] #efvaAnaKndCd option:selected").val());
	});
	
	//사용자정의 유효값 버튼
	//그리드 추가
	$("div#div_userdf #btnNew_PC02").click(function(){
		grid_efva_userdf_sheet.DataInsert(0);
	});
	//엑셀 업로드
	$("div#div_userdf #btnExcelLoad_PC02").click(function(){
		grid_efva_userdf_sheet.LoadExcel({Mode:"HeaderMatch"});
	});
	//엑셀다운로드
	$("div#div_userdf #btnExcelDown_PC02").click(function(){
		grid_efva_userdf_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	}).hide();
	
	
	// 코드테이블 정보 조회
	$("#btnCodeTblSearch").click(function(event){
    	event.preventDefault();	//브라우저 기본 이벤트 제거...
    	//팝업 flag 값 설정
    	codeTblColPopup();
	}).parent().buttonset();
	
	//META domain 조회
	$("#btnMetaDmnValSearch").click(function(event){
    	event.preventDefault();	//브라우저 기본 이벤트 제거...
    	metaCdDmnPopup();
	});
	 
	
	//코드 초기화
	//setCodeSelect("EFVA_ANA_KND_CD", "C", $("form[name=frmInputPC02] #efvaAnaKndCd"));

	// 품질지표 조회
    $("#frmInputPC02 #btnApply").click(function() {
    	var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=PRFPC02"; 
    		param += "&dqiIds="+$("#frmInputPC02 #dqiId").val();
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
    });
	
    $("#btnMetaDmnValSearch").removeClass('ui-button ui-widget ui-state-default ui-button-text-only ui-corner-left');
	$("#btnCodeTblSearch").removeClass('ui-button ui-widget ui-state-default ui-button-text-only ui-corner-left');
});


$(window).on('load',function(){
	setFormByEfvaKndCd($("form[name=frmInputPC02] #efvaAnaKndCd option:selected").val());
});


$(window).resize( function(){
	grid_efva_userdf_sheet.FitColWidth();  
});

function codeTblColPopup(){
	
	if($("form[name=frmAnaTrg] #dbConnTrgId").val() == ""){
		var message = "<s:message code="VALID.PRFREQUIRED" />";
		showMsgBox("INF", message); 
		return;
	}
  	var url = '<c:url value="/dq/profile/popup/anatrgcodetblcol_pop.do"/>';
	var param = $("form[name=frmAnaTrg]").serialize(); 
	openLayerPop(url ,800 ,800,  param);
} 

//메타 코드유효값 조회
function metaCdDmnPopup(){
	var url = '<c:url value="/dq/profile/popup/metadmn_pop.do"/>';
	var param =  $("form[name=frmAnaTrg]").serialize(); 
	
	console.log(param);
	
    var popup = OpenWindow(url+"?"+param, "META_DMN", "800", "700", "auto");
}

function  resetPC02(){
	$("form[name=frmInputPC02]")[0].reset();
	$("form[name=frmInputPC02_cdtbl]")[0].reset();
	setFormByEfvaKndCd($("form[name=frmInputPC02] #efvaAnaKndCd option:selected").val());
	grid_efva_userdf_sheet.RemoveAll();
}

//DQI 팝업 리턴값 처리
function returnDqiPopPC02 (ret) {
	//초기화
	$("#frmInputPC02 #dqiLnm").val("");
	$("#frmInputPC02 #dqiId").val("");
	
	for(var i=0; i<ret.data.length; i++){
		var retjson = JSON.stringify(ret.data[i]);
		var parsejson = jQuery.parseJSON(retjson);
		
		if($("#frmInputPC02 #dqiLnm").val() != null && $("#frmInputPC02 #dqiLnm").val() != "undefined" && $("#frmInputPC02 #dqiLnm").val() != "" ){
			$("#frmInputPC02 #dqiLnm").val($("#frmInputPC02 #dqiLnm").val() + "," + parsejson.dqiLnm);
			$("#frmInputPC02 #dqiId").val($("#frmInputPC02 #dqiId").val() + "," + parsejson.dqiId);
		}else {
			$("#frmInputPC02 #dqiLnm").val(parsejson.dqiLnm);
			$("#frmInputPC02 #dqiId").val(parsejson.dqiId);
		}
	}
}

function savePC02(){
	
	//입력필드 체크박스 확인
	if(!$("form[name=frmInputPC02]").valid()){
		var message = "<s:message code='REQUIRED.INPT.ITEM' />";
		showMsgBox("INF", message); 
		return;
	}   

	//필수입력항목 체크
	var EfvaKndCd = $("form[name=frmInputPC02] #efvaAnaKndCd option:selected").val();
	var urls = '<c:url value="/dq/profile/registerProfile.do"/>';
	var param = "";
	     param += "&"+ $("form[name=frmAnaTrg]").serialize(); //진단대상 정보
         param += "&"+$("form[name=frmInputPC02]").serialize(); //코드분석 정보
	//메타연계
	if(EfvaKndCd == "META"){
		//차후 진단대상 컬럼 도메인 정보 유무에 따라 리턴
 		ajax2Json(urls, param, ibscallback); 
	}
	//코드테이블
	else if(EfvaKndCd == "CTBL"){
		//form 체크
	 	if(!$("form[name=frmInputPC02_cdtbl]").valid()){
	 		var message = "<s:message code='REQUIRED.INPT.ITEM' />";
    		showMsgBox("INF", message); 
	 		return false;
	 	}
		
	 	param += "&"+$("form[name=frmInputPC02_cdtbl]").serialize(); //코드테이블 정보
 	 	ajax2Json(urls, param, ibscallback); 
	}
	//사용자정의
	else if(EfvaKndCd == "USER"){
		//사용자정의유효값
		var ibsSaveJsonPc02UserDf = grid_efva_userdf_sheet.GetSaveJson(1);
// 		if($("form[name=frmAnaTrg] #regTypCd").val() != "C"){
// 			ibsSaveJsonPc02UserDf = grid_efva_userdf_sheet.GetSaveJson(1);
// 		}
		
		//코드분석 삭제 일 경우 사용자정의유효값 전체 삭제
		if($("form[name=frmAnaTrg] #regTypCd").val() != "D"){
			
			//사용자정의 유효값 입력 확인
			if(ibsSaveJsonPc02UserDf.data.length == 0){ 
				var message = "<s:message code='USER.DFNT.VLD.VAL.INPT'/>"; /*사용자정의유효값을 입력하십시오.*/
				showMsgBox("ERR", message); 
				return;
			}
			
			//동일 중복컬럼 확인
			var DupRow = grid_efva_userdf_sheet.ColValueDupRows("userDfEfva");
			if(DupRow != ""){
				var message = "<s:message code='ERR.EMPTY'  arguments="<s:message code='SAME.USER.DFNT.VLD.VAL.EXIS'/>" />"; /*동일한 사용자정의유효값이 존재 합니다.*/
				showMsgBox("INF", message); 
				return;
			}
			
			//사용자정의 유효값 그리드 삭제 선택일경우 삭제대상 건수 와 전체건수 비교
			if(grid_efva_userdf_sheet.FindStatusRow("D") != ""){
				var arrDelRows = grid_efva_userdf_sheet.FindStatusRow("D").split(";").length ;
				if(arrDelRows == ibsSaveJsonPc02UserDf.data.length){
					var message = "<s:message code='ERR.EMPTY' arguments="<s:message code='DEL.TRGT.USER.DFNT.VLD.VAL.CCNT.CCNT.SAME'/>" />";/*삭제대상 사용자정의유효값 건수와 총건수가 동일합니다.*/
					showMsgBox("ERR", message); 
					return;
				}
			}
		}
		
		urls = '<c:url value="/dq/profile/regColPrfPC02UserDf.do"/>';
		IBSpostJson2(urls, ibsSaveJsonPc02UserDf, param, ibscallback);
	}
    
	
}

function initUserDfGrid()
{
   
	with(grid_efva_userdf_sheet){
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headers = [
					{Text:"<s:message code='DQ.HEADER.COLEFVA_DTL'/>", Align:"Center"}  //|객체설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호
				];
		//삭제|No.|상태|요청구분|등록유형|검증결과|검토상태코드|사용자정의유효값|사용자정의유효값명


		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
	                {Type:"DelCheck", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
					{Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
	                {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,  SaveName:"rqstDcd",	 Align:"Center", Edit:1, KeyField:0, Hidden:1},						
					{Type:"Combo",  Width:100,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:100,  SaveName:"vrfCd",		Align:"Center", Edit:0,},						
					{Type:"Combo",  Width:100,  SaveName:"rvwStsCd",		Align:"Center", Edit:0,},						
					{Type:"Text",   Width:200,  SaveName:"userDfEfva",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:200,  SaveName:"userDfEfvaNm",	Align:"Left", 	 Edit:1}
// 					{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
// 					{Type:"Date",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
// 					{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:0},
// 					{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
// 					{Type:"Text",   Width:100,  SaveName:"rqstNo",  Align:"Center", Edit:0},
// 					{Type:"Text",   Width:100,  SaveName:"rqstSno",  Align:"Center", Edit:0}
				];
		
		InitColumns(cols);
		
		SetColProperty("rqstDcd", ${codeMap.rqstDcdibs});
// 		SetColProperty("regTypCd", ${codeMap.regTypCd});
// 		SetColProperty("vrfCd", ${codeMap.vrfCd});
// 		SetColProperty("rvwStsCd", ${codeMap.rvwStsCd});
		
		InitComboNoMatchText(1, "");
		
		SetColHidden("regTypCd",1);
		SetColHidden("vrfCd",1);
		SetColHidden("rvwStsCd",1);
// 		SetColHidden("objDescn",1);
// 		SetColHidden("rqstDtm",1);
// 		SetColHidden("rqstUserId",1);
// 		SetColHidden("rqstUserNm",1);
// 		SetColHidden("rqstNo",1);
// 		SetColHidden("rqstSno",1);
	  
		FitColWidth();  
		
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_efva_userdf_sheet);	
	//===========================
	
	//grid_sheet.SetColHidden(Col, Hidden)
   
}	

function setFormByEfvaKndCd(EfvaKndCd){
	//유효값 입력 필드 리셋
	//clearPC02();
	//메타연계
	if(EfvaKndCd == "META"){
		//메타연계 도메인 유효값 정보 조회 팝업
		$("#btnMetaDmnValSearch").show();
		$("#btnCodeTblSearch").hide();
		$("div#searchPC02_div #div_userdf").hide();
		$("div#searchPC02_div #div_cdtbl").hide();
	}
	//코드테이블
	else if(EfvaKndCd == "CTBL"){
		$("#btnMetaDmnValSearch").hide();
		$("#btnCodeTblSearch").show();
		$("div#searchPC02_div #div_userdf").hide();
		$("div#searchPC02_div #div_cdtbl").show();
	}
	//사용자정의
	else if(EfvaKndCd == "USER"){
		$("#btnMetaDmnValSearch").hide();
		$("#btnCodeTblSearch").hide();
		$("div#searchPC02_div #div_userdf").show();
		$("div#searchPC02_div #div_cdtbl").hide();
	}
}


function clearPC02(){
	//사용자정의 유효값 초기화
	grid_efva_userdf_sheet.RemoveAll();
	//코드테이블정보 초기화
	$('form[name=frmInputPC02_cdtbl]')[0].reset();
	
	//메타유효값조회 
	$("#btnMetaDmnValSearch").hide();
	
	//코드테이블 조회
	$("#btnCodeTblSearch").hide();
}

function setDtlPC02(data){
	//유효값유형
	var vEfvaAnaKndCd = data.resultVO.efvaAnaKndCd;
	//유효값유형별 상세정보 화면 setting
	setFormByEfvaKndCd(vEfvaAnaKndCd);
	//유효값유형
	$('form[name=frmInputPC02] #efvaAnaKndCd').val(vEfvaAnaKndCd);
	//추가조건
	$('form[name=frmInputPC02] #adtCndSql').val(data.resultVO.adtCndSql); 
	
	$('form[name=frmInputPC02] #dqiId').val(data.resultVO.dqiId); 
	$('form[name=frmInputPC02] #dqiLnm').val(data.resultVO.dqiLnm); 

	//메타연계
	if(vEfvaAnaKndCd == "META"){
	}
	//공통코드 일경우 
	if(vEfvaAnaKndCd == "CTBL"){
		json2formmapping($("form[name=frmInputPC02_cdtbl]"), data.resultVO);
	}
	//사용자 정의 유효값 일경우 사용자유효값 조회
	if(vEfvaAnaKndCd == "USER"){
		param = "&prfId="+data.resultVO.prfId; 
		grid_efva_userdf_sheet.DoSearch("<c:url value="/dq/profile/getColPrfPC02UserLst.do" />", param);
	}
}


function grid_efva_userdf_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		
	}
}

</script>

<!-- </head> -->
<!-- <body>     -->
<!-- 그리드 입력 입력 -->
<div id="searchPC02_div" >
	<div style="clear:both; height:5px;"><span></span></div>
	<div class="stit"><s:message code="CD.ANLY.DTL.INFO"/><!--코드분석 상세정보--></div>
	<div style="clear:both; height:5px;"><span></span></div>
	
	<form id="frmInputPC02" name="frmInputPC02" method="post"> 
 	<fieldset>
    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	<div class="tb_basic2" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CD.ANLY'/>"><!-- /*코드분석*/-->
		   <caption><s:message code="CD.ANLY"/></caption><!--코드분석-->
		   <colgroup>
		   <col style="width:15%;" />
		   <col style="width:85%;" />
		   </colgroup>
		       <tbody>  
		       	   <tr>  
		               <th scope="row" class="th_require"><label for=""><s:message code="VLD.VAL.TY"/></label></th><!--유효값유형-->

		               <td>
		                   <select id="efvaAnaKndCd"  name="efvaAnaKndCd" class="" style="width:30%;">
								<c:forEach var="code" items="${codeMap.efvaAnaKndCd}" varStatus="status">
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							</select>
							&nbsp;&nbsp;&nbsp;
							<!-- 메타유효값 조회 버튼 -->
							<button class="btn_search" id="btnMetaDmnValSearch" name="btnMetaDmnValSearch"><s:message code="META.VLD.VAL.INQ"/></button><!--메타유효값조회-->
							<!-- 코드테이블 조회 버튼 -->
							<button class="btn_search" id="btnCodeTblSearch" name="btnCodeTblSearch"><s:message code="CD.TBL.INQ.2"/></button> 
		               </td><!--코드테이블조회-->

		           </tr>
		           <tr>
		               <th scope="row"><label for="adtCndSql"><s:message code="ADDT.COND"/></label></th><!--추가조건-->

		               <td>
		                   <input type="text"  class="wd98p" name="adtCndSql" id="adtCndSql" />
		               </td>
		           </tr>
		           <tr>
		               <th scope="row" class="th_require"><label for="dqiSearch"><s:message code="QLTY.INDC"/></label></th><!--품질지표-->
		               <td>
		                   <input type="text"  style="width: 70%;" name="dqiLnm" id="dqiLnm" readonly/>
		                   <input type="hidden" name="dqiId" id="dqiId" />
		                   &nbsp;&nbsp;&nbsp;
		                   <input style="background-color: #2682ca;" type="button" id="btnApply" name="btnApply" class="btn_save" value="<s:message code='INQ' />" />
		               </td>
		           </tr>
		       </tbody>
		     </table>   
		</div>
		</fieldset>
		</form>

		<div id="div_cdtbl" name="div_cdtbl"> 
		<div style="clear:both; height:5px;"><span></span></div>
		<div class="stit"><s:message code="CD.TBL.DTL.INFO"/></div><!--코드테이블 상세정보-->

		<div style="clear:both; height:5px;"><span></span></div>
		<form id="frmInputPC02_cdtbl" name="frmInputPC02_cdtbl" method="post">
		<input type="hidden" name="cdTblDbConnTrgId" id="cdTblDbConnTrgId"  />
		<input type="hidden" name="cdTblDbSchId" id="cdTblDbSchId" />
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic2" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CD.ANLY'/>"><!-- /*코드분석*/
 -->
			   <caption><s:message code="CD.ANLY"/></caption><!--코드분석-->

			   <colgroup>
			   <col style="width:15%;" />
			   <col style="width:35%;" />
			   <col style="width:15%;" />
			   <col style="width:35%;" />
			   </colgroup>
			       <tbody>  
			           <tr>
			           		<th scope="row"><label for="cdTblDbConnTrgLdm"><s:message code="CD.TBL.DIAG.TRGT.NM"/></label></th>	<!--코드테이블진단대상명-->

			           		<td>
			           			<input type="text" name="cdTblDbConnTrgLdm" id="cdTblDbConnTrgLdm"  class="readonly50p" readonly />
			           		</td>
			               <th scope="row"><label for="cdTblDbSchLdm"><s:message code="CD.TBL.SCHEMA.NM"/></label></th><!--코드테이블스키마명-->

			               <td>
			                   <input type="text" name="cdTblDbSchLdm" id="cdTblDbSchLdm"  class="readonly50p" readonly />
			               </td>
			           </tr>
			           <tr>
			           		<th scope="row"><label for="cdTblDbcTblNm"><s:message code="CD.TBL.NM"/><!--코드테이블명-->
</label></th>	
			           		<td>
			           			<input type="text" name="cdTblDbcTblNm" id="cdTblDbcTblNm" class="readonly50p" readonly />
			           		</td>
			               <th scope="row"><label for="cdTblDbcTblKorNm"><s:message code="CD.TBL.KRN.NM"/></label></th><!--코드테이블한글명-->

			               <td>
			                   <input type="text" name="cdTblDbcTblKorNm" id="cdTblDbcTblKorNm" class="readonly50p" readonly  />
			               </td>
			           </tr>
			           <tr>
			           		<th scope="row"><label for="cdTblDbcColNm"><s:message code="CD.VAL.CLMN.NM"/></label></th>	<!--코드값컬럼명-->
			           		<td>
			           			<input type="text" name="cdTblDbcColNm" id="cdTblDbcColNm" class="readonly50p" readonly />
			           		</td>
			               <th scope="row"><label for="cdTblDbcColKorNm"><s:message code="CD.VAL.CLMN.KRN.NM"/></label></th><!--코드값컬럼한글명-->

			               <td>
			                   <input type="text" name="cdTblDbcColKorNm" id="cdTblDbcColKorNm" class="readonly50p" readonly />
			               </td>
			           </tr>
			           <tr>
			           		<th scope="row"><label for="cdTblCdIdColNm"><s:message code="CD.ID.CLMN.NM"/></label></th><!--코드ID컬럼명-->

			           		<td>
			           			<input type="text" name="cdTblCdIdColNm" id="cdTblCdIdColNm" class="readonly50p" readonly />
			           		</td>
			               <th scope="row"><label for="cdTblCdIdColKorNm"><s:message code="CD.ID.CLMN.KRN.NM"/></label></th><!--코드ID컬럼한글명-->

			               <td>
			                   <input type="text" name="cdTblCdIdColKorNm" id="cdTblCdIdColKorNm" class="readonly50p" readonly/>
			               </td>
			           </tr>
			           <tr>
			               <th scope="row"><label for="cdTblCdId"><s:message code="CD.ID" /></label></th><!--코드ID-->

			               <td colspan="3">
			                   <input type="text" name="cdTblCdId" id="cdTblCdId" />
			               </td>
			           </tr>
			       	 
			       	 	<tr>                               
			               <th scope="row"><label for="cdTblAdtCndSql"><s:message code="CD.TBL.ADDT.COND"/></label></th><!--코드테이블추가조건-->

			               <td colspan="3">
			                   <input type="text"  class="wd98p" name="cdTblAdtCndSql" id="cdTblAdtCndSql" />
			               </td>
			           </tr>
			           
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			</form>
		</div>
		
		<div id="div_userdf" name="div_userdf">
			<div style="clear:both; height:5px;"><span></span></div>
			<div class="stit"><s:message code="USER.DFNT.VLD.VAL.DTL.INFO"/></div><!--사용자정의유효값 상세정보-->

			<div style="clear:both; height:5px;"><span></span></div>
			<!-- 추가 엑셀내리기  -->
			<div class="divLstBtn" >	 
            <div class="bt03">
                <button class="btn_rqst_new" id="btnRqstNew_PC02" name="btnRqstNew_PC02"><s:message code="ADDT" /></button><!--추가-->

				  <ul class="add_button_menu" id="addButtonMenu_PC02">
				    <li class="btn_new" id="btnNew_PC02"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li><!--신규 추가-->
				    <li class="btn_excel_load" id="btnExcelLoad_PC02"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UP" /></a></li><!--엑셀 올리기-->
				  </ul>         
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown_PC02" name="btnExcelDown_PC02"><s:message code="EXCL.DOWNLOAD" /></button><!--엑셀 내리기-->
                       
	    	</div>
        </div>
			<!-- 검색조건 입력폼 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<form id="frmInputPC02_userdf" name="frmInputPC02_userdf" method="post">
			<div id="grid_userdf" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_efva_userdf_sheet", "100%", "130px");</script>            
			</div>
			</form>
		</div>
</div>
<!-- 그리드 입력 입력 End -->
	
<!-- </body> -->
<!-- </html> -->
