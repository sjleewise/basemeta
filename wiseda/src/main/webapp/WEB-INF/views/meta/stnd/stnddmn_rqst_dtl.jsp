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
<title><s:message code="STRD.WORD.REG.DTL.INFO" /></title><!-- 표준단어등록상세정보 -->

<script type="text/javascript">
var dmnginfotpJson = ${codeMap.dmnginfotp} ;


//도메인그룹 selectbox 
var bscLvl = parent.bscLvl;
var selectBoxId = parent.selectBoxId;
var selectBoxNm = parent.selectBoxNm;
var firstSelectBoxId = selectBoxId.split("|");
var firstSelectBoxNm = selectBoxNm.split("|");

$(document).keypress(function(e) {
	  if(e.which == 13) {
		  //$("#dmnLnm").focus();
		  return false;
	  }
	});
	
$(document).ready(function(){
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	var shotrequiremessage = "<s:message code="VALID.SHORTREQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			rqstDcd		: "required",
			dmnLnm		: "required",
			dmnPnm		: "required",
// 			dmngId 	: "required",
			infotpId	: "required",
			lnmCriDs	: "required",
			pnmCriDs	: "required",
			objDescn    : "required",
			stndAsrt	: "required",
			
		},
		messages: {
			rqstDcd		: requiremessage,
			dmnLnm		: requiremessage,
			dmnPnm		: requiremessage,
// 			dmngId 	: requiremessage,
			infotpId	: requiremessage,
			lnmCriDs	: shotrequiremessage,
			pnmCriDs	: shotrequiremessage,
			objDescn    : shotrequiremessage,
			stndAsrt	: requiremessage,
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
		
	}).show();
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
		$("form[name=frmInput]")[0].reset();
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
	
	//주제영역 검색 팝업 호출
	$("#subjSearchPop").click(function(){
		var param = ""; //$("form#frmInput").serialize();
		openLayerPop ("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", 800, 600, param);
// 		OpenWindow("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", "dkafda",  800, 600, "yes");
// 		openSearchPop("<c:url value='/meta/model/pop/subjSearchPop.do' />", param);
    });
	
	//목록엔티티 검색 팝업 호출
	$("#lstEntyPop").click(function(){
		var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/meta/model/popup/pdmtblSearchPop.do' />", 800, 600, param);
    });
	
	//목록어트리뷰트 검색 팝업 호출
	$("#lstAttrPop").click(function(){
		var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/meta/model/popup/pdmcol_pop.do' />", 800, 600, param);
    });
	
	$("#lstAttrClr").click(function(){
		$("#lstAttrPnm").val('');
		$("#lstAttrLnm").val('');
		$("#lstEntyPnm").val('');
		$("#lstEntyLnm").val('');
		  
	}).show();
	
	//담당자 검색 팝업 호출
	$("#userPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
    
    
    //도메인 자동분할 팝업
	$('#btnDmnGen').click(function(){
		
		if($("#stndAsrt").val()==''){
			showMsgBox("INF","<s:message  code='MSG.STND.ASRT.CHC' />");
			return;
			
		}
// 		var param = $("form#frmInput").serialize();
		var param = "";
			param += "rqstNo="+$("#mstFrm #rqstNo").val();
		    param += "&dmnLnm="+ encodeURIComponent($("#dmnLnm").val());
		    param += "&stndAsrt="+ $("#stndAsrt").val();
		openLayerPop ("<c:url value='/meta/stnd/popup/dmndivsion_pop.do' />", 1000, 600, param);
		
// 		var popup = OpenWindow("<c:url value="/meta/stnd/dmndvcanlst_pop.do"/>"+param,"dmnGen","800","600","yes");
// 		popup.focus();
	});
	
	//도메인 분리 기능
	$('#btnDmnDiv').click(function(){

		if($("#stndAsrt").val()==''){
			showMsgBox("INF","<s:message  code='MSG.STND.ASRT.CHC' />");
			return;
			
		}
		
		if($("#dmnLnm").val() == "") return;
		var vUrl = "<c:url value='/meta/stnd/getdmndivjson.do'/>";
		var param = "dmnLnm=" + $("#dmnLnm").val();
			param += "&rqstNo="+$("#mstFrm #rqstNo").val();
			param += "&stndAsrt="+ $("#stndAsrt").val();
		ajax2Json(vUrl, param, function(data){
			$("#divMsgPopup").remove();
			$("#dmnLnm, #dmnPnm, #lnmCriDs").val("");
			$("#dmnLnm").val(data.dmnLnm);
			$("#dmnPnm").val(data.dmnPnm);
			$("#lnmCriDs").val(data.lnmCriDs);
			$("#pnmCriDs").val(data.pnmCriDs);
			if($("#dmnPnm").val().indexOf("[D]") > -1) {
				var message = "<s:message  code='DMN.DUPWORD' />";
				showMsgBox("CNF", message, click_btnDmnGen );
// 				showMsgBox("INF", message );
// 				$("#dmnStwdPop").show();
			}
		  });
		
	});
	
	//부모도메인명 검색 팝업 호출
	$("#dmnPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/meta/stnd/popup/stnddmn_pop.do' />", 1000, 600, param);
    });
	
	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	$("#frmInput #stndAsrt").val('${result.stndAsrt}');

	
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
	//(도메인그룹, 인포타입) - 데이터 타입관련 정보 자동 셋팅한다....
 	//divID,  selectbox건수, selectbox ID
    create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId");

    $("#frmInput #stndAsrt").change(function() {
    	          $("#lnmCriDs").val("");
    	          $("#pnmCriDs").val("");
    	          $("#dmnPnm").val("");
    	          $("#"+firstSelectBoxId[0]).val("");
                  double_selectStndAsrt(dmnginfotpJson, $("#"+firstSelectBoxId[0]),$("#stndAsrt option:selected").val());
	              $('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
	              	double_selectStndAsrt(dmnginfotpJson, $(this),$("#stndAsrt option:selected").val());
	              	
	              	//$("#dmngLnm").val($(this).prev().find("option:selected").text());
	              	$("#dmngLnm").val($("#dmngId").find("option:selected").text());
	              	$("#infotpLnm").val($("#infotpId option:selected").text());
                  
 	              	if($("#dmngLnm").val().indexOf("코드") < 0){
 	              		$("#cdValTypCd").prop("disabled", true);
 	              		$("#cdValIvwCd").prop("disabled", true);
 	              		$("#dmnDscd").prop("disabled", true);
 	              		if($("#dmngLnm").val()!=""){
 	              		   $("#cdValTypCd").val("");
 	              		   $("#cdValIvwCd").val("");
 	              		   $("#dmnDscd").val("");
 	              		}
 	              	}else{
 	              		$("#cdValTypCd").prop("disabled", false);
 	              		$("#cdValIvwCd").prop("disabled", false);
 	              		$("#dmnDscd").prop("disabled", false);
 	              	}
                  
                    /*
	              	$("#dataType, #dataLen, #dataScal").val("");
	              	var infoid = $(this).attr('id');
	              	//인포타입 정보 자동 반영
	              	if (infoid == 'infotpId') {
	              		var jsonlist = infotpinfolstJson; 
	              		for(var i=0; i < jsonlist.length; i++) {
	              			if(jsonlist[i].infotpId == $("#infotpId").val()) {
	              				$("#dataType").val(jsonlist[i].dataType);
	              				$("#dataLen").val(jsonlist[i].dataLen);
	              				$("#dataScal").val(jsonlist[i].dataScal);
// 	              				var dataTypeTrans = "Oracle   :   "+jsonlist[i].oraDataType
// 	    						+"                       MySQL   :   "+jsonlist[i].myDataType
// 	    						+"                       MS-SQL   :   "+jsonlist[i].msDataType;
// 	    						$("#dataTypeTrans2").text(dataTypeTrans)
	              				break;
	              			};
	              		};
	              	}
	              	*/
	     });
    });
	setDomainInfoinit ();

	//도메인그룹/인포타입을 매핑하기위한 변수... 이거 어떻게 바꾸나요......?
// 	var uppDmngLnm = '${result.uppDmngLnm}';
// 	var dmngLnm = '${result.dmngLnm}';

//     for(var i=0; i<firstSelectBoxId.length; i++){
//     	var temp = firstSelectBoxNm[i];
// 	    setselectbytext($("#frmInput #"+firstSelectBoxId[i]), eval(firstSelectBoxNm[i]));
// 		$("#frmInput #"+firstSelectBoxId[i]).change();
//     }
// 	setselectbytext($("#frmInput #infotpId"), '${result.infotpLnm}');
// 	$("#infotpLnm").val($("#infotpId option:selected").text());
// 	$("#frmInput #infotpId").change();
	
	//도메인그룹/인포타입 초기화...
	
// 	setselectbytext($("#frmInput #"+firstSelectBoxId[0]), '${result.uppDmngLnm}');
// 	$("#frmInput #"+firstSelectBoxId[0]).change();
// 	setselectbytext($("#frmInput #"+firstSelectBoxId[1]), '${result.dmngLnm}');
// 	$("#frmInput #"+firstSelectBoxId[1]).change();
// 	setselectbytext($("#frmInput #infotpId"), '${result.infotpLnm}');
// 	$("#infotpLnm").val($("#infotpId option:selected").text());
// 	$("#frmInput #dmngId").val('${result.dmngId}');
// 	$("#frmInput #infotpId").val('${result.infotpId}');
// 	$("#frmInput #infotpId").change();
	
	//코드값유형/부여방식 초기화...
	$("#frmInput #cdValTypCd").val('${result.cdValTypCd}');
	$("#frmInput #cdValIvwCd").val('${result.cdValIvwCd}');
	
	//항목자동생성여부 초기화...
	chkformsetup($("#frmInput input[name=sditmAutoCrtYn]"), '${result.sditmAutoCrtYn}');
// 	$("#frmInput input[name=sditmAutoCrtYn]").val('${result.dmngId}');

	if($("#dmngLnm").val().indexOf("코드") < 0){ /*코드*/
		$("#cdValTypCd").prop("disabled", true);
		$("#cdValIvwCd").prop("disabled", true);
		$("#dmnDscd").prop("disabled", true);
	}else{
		$("#cdValTypCd").prop("disabled", false);
		$("#cdValIvwCd").prop("disabled", false);
		$("#dmnDscd").prop("disabled", false);
	}

});


function click_btnDmnGen() {
	$("#btnDmnGen").click();
}

//도메인 명에 따라 해당하는 도메인 그룹, 인포정보를 셋팅한다.
function setDomainInfoinit (param) {
	if(isBlankStr($("#frmInput #dmnLnm").val())) return;
	var vUrl = "<c:url value='/meta/stnd/getdmninfojson.do'/>";
// 	var param = "dmnLnm=" + $("#frmInput #dmnLnm").val();
// 		param += "&dmnPnm="+$("#frmInput #dmnPnm").val();
// 		param += "&rqstSno="+$("#frmInput #rqstSno").val();
// 		param += "&rqstNo="+$("#mstFrm #rqstNo").val();
// 		param += "&stndAsrt="+$("#frmInput #stndAsrt").val();
	ajax2Json(vUrl, param, function(data){
		$("#divMsgPopup").remove();
	    for(var i=0; i<firstSelectBoxId.length; i++){
		    setselectbytext($("#frmInput #"+firstSelectBoxId[i]), data[firstSelectBoxNm[i]]);
			$("#frmInput #"+firstSelectBoxId[i]).change();
	    }
		
		setselectbytext($("#frmInput #infotpId"), data.infotpLnm);
		$("#frmInput #infotpId").change();
	});
}
//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	et);
	
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #subjId").val(retjson.subjId);
	$("#frmInput #fullPath").val(retjson.fullPath);
	
}

//도메인 자동분할 팝업 리턴값 처리....
function returnDmnDivsionPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #dmnLnm").val(retjson.dicAsmLnm);
	$("#frmInput #dmnPnm").val(retjson.dicAsmPnm);
// 	$("#frmInput #lnmCriDs").val(retjson.dicAsmDsLnm.replace(/;/g, '_'));
	$("#frmInput #lnmCriDs").val(retjson.dicAsmDsLnm);
	$("#frmInput #pnmCriDs").val(retjson.dicAsmDsPnm);
}

//목록엔티티 팝업 리턴값 처리...
function returnPdmtblPop (ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #lstEntyId").val(retjson.pdmTblId);
	$("#frmInput #lstEntyLnm").val(retjson.pdmTblLnm);
	$("#frmInput #lstEntyPnm").val(retjson.pdmTblPnm);
	$("#frmInput #lstAttrId").val("");
	$("#frmInput #lstAttrLnm").val("");
	$("#frmInput #lstAttrPnm").val("");
}

//목록어트리뷰트 팝업 리턴값 처리...
function returnPdmColPop (ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #lstEntyId").val(retjson.pdmTblId);
	$("#frmInput #lstEntyLnm").val(retjson.pdmTblLnm);
	$("#frmInput #lstEntyPnm").val(retjson.pdmTblPnm);
	$("#frmInput #lstAttrId").val(retjson.pdmColId);
	$("#frmInput #lstAttrLnm").val(retjson.pdmColLnm);
	$("#frmInput #lstAttrPnm").val(retjson.pdmColPnm);
}


//담당자 팝업 리턴값 처리...
function returnUserInfoPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #crgUserId").val(retjson.userId);
	$("#frmInput #crgUserNm").val(retjson.userNm);
}

//부모도메인명 팝업 리턴값 처리...
function returnDmnPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #uppDmnId").val(retjson.dmnId);
	$("#frmInput #uppDmnLnm").val(retjson.dmnLnm);
}

</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
	<form id="frmInput" name="frmInput" method="post">
<%-- 	  <input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" > --%>
<%-- 	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" > --%>
	  <input type="hidden" id="rqstSno" name="rqstSno" value="" >
	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="" >
	<fieldset>
	
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	<!-- <div class="stit">주제영역 정보</div>
	<div style="clear:both; height:5px;"><span></span></div> 
	<legend>주제영역정보</legend>-->
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />" style = "display:none;"><!-- 테이블 서머리입니다. -->
			<caption><s:message code="SUBJ.TRRT.INFO" /></div> <!-- 주제영역 정보 -->
			<colgroup>
			<col style="width:15%;" />
			<col style="width:85%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><label for="subjId"><s:message code="SUBJ.TRRT" /></label></th> <!-- 주제영역 -->
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
		<div class="stit"><s:message code="DMN.INFO" /></div> <!-- 도메인정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
	<legend><s:message code="DMN.INFO" /></legend> <!-- 도메인정보 -->
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DMN.INFO' />"> <!-- 도메인정보 -->
			<caption><s:message code="DMN.INFO.INPT" />	</caption> <!-- 도메인 정보입력 -->
			<colgroup>
			<col style="width:15%;" />
			<col style="width:35%;" />
			<col style="width:15%;" />
			<col style="width:35%;" />
			</colgroup>
			<tbody>
			    <tr>
			    	<th scope="row" class="th_require"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					<td colspan="3">
					<select  id="stndAsrt" name="stndAsrt"  class="wd300">
					        <option value=""><s:message code="CHC"/></option>
					        <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
					</select>
					</td>
			    </tr>
				<tr>
					<th scope="row" class="th_require"><label for="dmnLnm"><s:message code="LGC.NM" /></label></th> <!-- 도메인논리명 -->
					<td>
						<input type="text" id="dmnLnm" name="dmnLnm" value="${result.dmnLnm}" class="wd200" />
					   	<button class="btnDelPop" 	 type="button" id="btnDmnClr"	name="btnDmnClr" ><s:message code="CLR" /></button> <!-- 지우기 -->
					   	<button class="btnSearchPop" type="button" id="btnDmnGen"	name="btnDmnGen" ><s:message code="ATMD.DIV" /></button> <!-- 자동분할 -->
					   	<button class="btnSearchPop" type="button" id="btnDmnDiv"	name="btnDmnDiv" ><s:message code="SPRT" /></button><!-- 분리 -->
					</td>
					<th scope="row" class="th_require"><label for="dmnPnm"><s:message code="PHYC.NM" /></label></th> <!-- 도메인물리명 -->
					<td >
						<div>
							<input type="text" id="dmnPnm" name="dmnPnm" value="${result.dmnPnm}" class="wd200" readonly/>
<!-- 							<button class="btnSearchPop" id="dmnStwdPop">검색</button> -->
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label for="lnmCriDs"><s:message code="LGC.NM.BASE.DSTC" /></label></th> <!-- 논리명기준구분 -->
					<td><input type="text" class="wd300" id="lnmCriDs" name="lnmCriDs" value="${result.lnmCriDs}"  readonly/></td>
					<th scope="row" class="th_require"><label for="lnmCriDs"><s:message code="PHYC.NM.BASE.DSTC" /></label></th> <!-- 물리명기준구분 -->
					<td><input type="text" class="wd300" id="pnmCriDs" name="pnmCriDs" value="${result.pnmCriDs}" readonly/></td>
				</tr>
				<tr>
					<th scope="row" class="th_require"><label ><s:message code="DMN.GRP.INFO.TY" /></label></th><!-- 도메인그룹/인포타입 -->
					<td>
						<div id="selectBoxDiv"> <span></span></div>
<%-- 						<select id="dmngId" class="" name="dmngId"> --%>
<!-- 							<option value="">선택</option> -->
<%-- 						</select> --%>
<%-- 						<select id="infotpId" class="" name="infotpId"> --%>
<!-- 							<option value="">선택</option> -->
<%-- 					 	</select> --%>
					 	<input type="hidden" name="dmngLnm" id="dmngLnm" value="${result.dmngLnm }">
					 	<input type="hidden" name=infotpLnm id="infotpLnm" value="${result.infotpLnm }">
					</td>
					<th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
					<td>
						<span class="input_inactive"><input type="text" class="wd80" id="dataType" name="dataType" value="${result.dataType}" readonly/></span>
						<span class="input_inactive"><input type="text" class="wd80" id="dataLen"  name="dataLen"  value="${result.dataLen}" readonly/></span>
						<span class="input_inactive"><input type="text" class="wd80" id="dataScal" name="dataScal" value="${result.dataScal}" readonly/></span>
					</td>
				</tr>
				<tr>
					<th scope="row"><label><s:message code="CD.VAL.PTRN.GRAN.MTHD" /></label></th> <!-- 코드값유형/부여방식 -->
					<td colspan="3">
						<select id="cdValTypCd" class="" name="cdValTypCd">
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.cdValTypCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
					 	</select>
						<select id="cdValIvwCd" class="" name="cdValIvwCd">
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.cdValIvwCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
					 	</select>
					</td>
<!-- 					<th scope="row"><label for="dataTypeTrans">데이터타입변환</label></th> -->
<!-- 					<td id="dataTypeTrans2"></td> -->
					<%-- 
					 <th scope="row"><label for="dmnDscd"><s:message code="LC.CD" /></label></th> <!-- 대분류코드 -->
				     <td >
						<input type="text" id="dmnDscd" name="dmnDscd" class="wd200"/>
					</td>
					--%>
				</tr>
				<tr>
					<th scope="row"><label for="lstEntyLnm"><s:message code="LST.ENTY.LGC.NM.PHYC.NM" /></label></th> <!-- 목록엔티티 논리명/물리명 -->
					<td>
						<span class="input_inactive"><input type="hidden" id="lstEntyId" name="lstEntyId"   value="${result.lstEntyId}" /></span>
						<span class="input_inactive" style="display:none"><input type="text" class="wd120" id="lstEntyLnm" name="lstEntyLnm" value="${result.lstEntyLnm}" /></span>
						<span class="input_inactive"><input type="text" class="wd200" id="lstEntyPnm" name="lstEntyPnm" value="${result.lstEntyPnm}" /></span>
						<button class="btnDelPop" id="lstEntyClr"><s:message code="DEL" /></button> <!-- 삭제 -->
						<button class="btnSearchPop" id="lstEntyPop" style="display:none;"><s:message code="SRCH" /></button> <!-- 검색 -->
					</td>
					<th scope="row"><label for="lstEntyLnm"><s:message code="LST.ATRB.LGC.NM.PHYC.NM" /></label></th> <!-- 목록어트리뷰트 논리명/물리명 -->
					<td>
						<span class="input_inactive"><input type="hidden" id="lstAttrId" name="lstAttrId"   value="${result.lstAttrId}" /></span>
						<span class="input_inactive" style="display:none"><input type="text" class="wd120" id="lstAttrLnm" name="lstAttrLnm" value="${result.lstAttrLnm}" /></span>
						<span class="input_inactive"><input type="text" class="wd200" id="lstAttrPnm" name="lstAttrPnm" value="${result.lstAttrPnm}" /></span>
						<button class="btnDelPop" id="lstAttrClr"><s:message code="DEL" /></button> <!-- 삭제 -->
						<button class="btnSearchPop" id="lstAttrPop"><s:message code="SRCH" /></button> <!-- 검색 -->
					</td>
				</tr>
				<tr>
				    <th scope="row"><label for="sditmAutoCrtYn"><s:message code="TERMS.ATMD.CRTN.YN" /></label></th> <!-- 용어자동생성여부 -->
					<td>
						<span class="input_check"><input type="checkbox" id="sditmAutoCrtYn" name="sditmAutoCrtYn" value="Y" /><s:message code="TERMS.ATMD.CRTN" /></span> <!-- 용어자동생성 -->
					</td>
					<th scope="row"><label for="dataFrm"><s:message code="DATA.FRMT.CD.ID" /></label></th> <!-- 데이터형식/코드ID -->
					<td>
						<input type="text" id="dataFrm" name="dataFrm" class="wd200"/>
						<input type="text" id="cdId" name="cdId" class="wd200"/>						
					</td>
					
				</tr>
				
				<tr>
					<th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 -->
					<td>
						<select id="encYn" name="encYn">
							<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
							<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
						</select>
					</td>
				
				 	<th scope="row"><label for="uppDmnLnm"><s:message code="PRNT.DMN.NM" /></label></th> <!-- 부모도메인명 -->
					<td>
						<span class="input_inactive"><input type="hidden" id="uppDmnId" name="uppDmnId"   value="${result.uppDmnId}" /></span>
						<span class="input_inactive"><input type="text"  id="uppDmnLnm" name="uppDmnLnm" value="${result.uppDmnLnm}" /></span>
						<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						<button class="btnSearchPop" id="dmnPop"><s:message code="SRCH" /></button> <!-- 검색 -->
					</td>
			   </tr>
<!-- 			   <tr> -->
<!-- 			   		<th scope="row"><label for="dupYn">중복여부</label></th> 암호화여부 -->
<!-- 					<td> -->
<%-- 						<select id="dupYn" name="dupYn"> --%>
<%-- 							<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 --> --%>
<%-- 							<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%-- 						</select> --%>
<!-- 					</td> -->
<!-- 			   </tr> -->
			   
			   <tr>
			        <th scope="row"><label for="dmnMinVal"><s:message code="MINV" /></label></th> <!-- 최소값 -->
					<td>
						<input type="text" id="dmnMinVal" name="dmnMinVal" />
					</td> 			
					
					<th scope="row"><label for="dmnMaxVal"><s:message code="MAXV" /></label></th> <!-- 최대값 -->
					<td>
						<input type="text" id="dmnMaxVal" name="dmnMaxVal" />
					</td>								 		
			   </tr>
			   
				<tr>
					<th scope="row" class="th_require"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
					<td colspan="3"><textarea class="wd98p" id="objDescn" name="objDescn">${result.objDescn}</textarea></td>
				</tr>
				<!--<tr>
					 <th scope="row"><label for="crgUserNm">담당자</label></th>
					<td>
						<span class="input_inactive"><input type="text" class="wd80" id="crgUserId" name="crgUserId" value="${result.crgUserId}" /></span>
						<span class="input_inactive"><input type="text" class="wd80" id="crgUserNm" name="crgUserNm" value="${result.crgUserNm}" /></span>
						<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="userPop" name="userPop">검색</button>
					</td> 
				</tr>-->
			</tbody>
		</table>
	</div>
	</fieldset>
	</form>
	<!-- 입력폼 끝 -->
	<!-- 	* 도메인명을 ;로 분리하여 입력한후 '분리'버튼을 클릭하시면 자동으로 분리되어 단어구분에 입력됩니다.(ex)개인;법인;구분코드) -->
	<div class="tb_comment"><s:message  code='REQ.DMN.COMM' /></div>
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<!-- 입력폼 끝 -->
</div>
</body>
</html>
