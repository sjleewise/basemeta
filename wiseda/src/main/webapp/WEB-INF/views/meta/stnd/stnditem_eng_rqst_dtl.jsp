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
var infotpinfolstJson = ${codeMap.infotpinfolst} ;

var bscLvl = parseInt("${bscLvl}");
var selectBoxId = "${selectBoxId}";
var selectBoxNm = "${selectBoxNm}";
var firstSelectBoxId = selectBoxId.split("|");
var firstSelectBoxNm = selectBoxNm.split("|");


//도메인그룹 selectbox 
//var bscLvl = parent.bscLvl;
//var selectBoxId = parent.selectBoxId;
//var selectBoxNm = parent.selectBoxNm;
//var firstSelectBoxId = selectBoxId.split("|");
//var firstSelectBoxNm = selectBoxNm.split("|");

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
			sditmLnm	: "required",
			sditmPnm 	: "required",
			objDescn    : "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			sditmLnm	: requiremessage,
			sditmPnm 	: requiremessage, 
			objDescn    : requiremessage
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	$("#divInputBtn").show(); 
	  
		
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

		resetForm($("form#frmInput"));
		
	});
	
	
	
	//표준용어 물리명 검색
    $("#sditmStwdPop").click(function(){
    	var param = $("form#frmInput").serialize();
			param += "&rqstNo="+$("#rqstNo", parent.document).val();
			//openLayerPop("<c:url value='/meta/stnd/sditmdupstwd_pop.do' />", 800, 600, param);
    }).hide();
    
   
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
    create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId",null ,false);
	
	//$("#"+firstSelectBoxId[0]).attr("disabled",true); //도메인그룹은 선택불가
	
   	double_select(dmnginfotpJson, $("#"+firstSelectBoxId[0]));
	
   	$('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
 		//alert("2");

 		double_select(dmnginfotpJson, $(this));
// 		alert($("#dmngId option:selected").text());
		$("#dmngLnm").val($(this).prev().find("option:selected").text());
		$("#infotpLnm").val($("#infotpId option:selected").text());
		
		$("#dataType, #dataLen, #dataScal").val("");
		
		var infoid = $(this).attr('id');
// 		alert(infoid);
		if (infoid == 'infotpId') {
			//인포타입 정보 자동 반영
			var jsonlist = infotpinfolstJson;
			for(var i=0; i < jsonlist.length; i++) {
				if(jsonlist[i].infotpId == $("#infotpId").val()) {
					$("#dataType").val(jsonlist[i].dataType);
					$("#dataLen").val(jsonlist[i].dataLen);
					$("#dataScal").val(jsonlist[i].dataScal);
					break;
				};
			};
		}
	});

   	
    //select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	$("#frmInput #encYn").val('${result.encYn}');
	
	$("#frmInput #dmngId").val('${result.dmngId}');
	
	
});

function click_btnSditmGen () {
	$("#btnSditmGen").click();
}

function click_btnDmnSch () {
	$("#btnDmnSch").click();
}



//항목 자동분할 팝업 리턴값 처리....
function returnItemDivsionPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
// 	alert(retjson.dmnPnm);
	
	$("#frmInput #sditmLnm").val(retjson.dicAsmLnm);
	$("#frmInput #sditmPnm").val(retjson.dicAsmPnm);
	$("#frmInput #dmnLnm").val(retjson.dmnLnm);
	$("#frmInput #dmnPnm").val(retjson.dmnPnm);
// 	$("#frmInput #lnmCriDs").val(retjson.dicAsmDsLnm.replace(/;/g, '_'));
	$("#frmInput #lnmCriDs").val(retjson.dicAsmDsLnm);
	$("#frmInput #pnmCriDs").val(retjson.dicAsmDsPnm);
	
	setDomainInfo ();
	//setDomainInfoinit();
	
	
}

//도메인 팝업 리턴값 처리....
function returnDmnPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #dmnLnm").val(retjson.dmnLnm);
	$("#frmInput #dmnPnm").val(retjson.dmnPnm);
	$("#frmInput #encYn").val(retjson.encYn);
	
	setDomainInfo();
// 	setDomainInfoinit();
	
}

//인포타입명에 따라 해당하는 도메인 그룹, 인포정보를 셋팅한다.
function setDomainInfo2(infotpLnm) {
	if(isBlankStr($("#frmInput #dmnLnm").val())) return;
	var vUrl = "<c:url value='/meta/stnd/getdmninfojson.do'/>";
	var param = "dmnLnm=" + $("#frmInput #dmnLnm").val();
		param += "&dmnPnm="+$("#frmInput #dmnPnm").val();
// 		param += "&rqstSno="+$("#frmInput #rqstSno").val();
		param += "&rqstNo="+$("#mstFrm #rqstNo").val();
		
	ajax2Json(vUrl, param, function(data){
		$("#divMsgPopup").remove();
		
// 			$("#dmnLnm").val("");
			$("#dmngLnm").val("");
			$("#dmngId").val("");
			$("#infotpLnm").val("");
			$("#dataType").val("");
			$("#dataLen").val("");
			$("#dataScal").val("");
			

		    for(var i=0; i<firstSelectBoxId.length; i++){
			    setselectbytext($("#frmInput #"+firstSelectBoxId[i]), data[firstSelectBoxNm[i]]);
				$("#frmInput #"+firstSelectBoxId[i]).change();
		    }

			//setselectbytext($("#frmInput #infotpId"), data.infotpLnm);

			setselectbytext($("#frmInput #infotpId"), infotpLnm);
			
			$("#frmInput #infotpId").change();

	});
}
//도메인 명에 따라 해당하는 도메인 그룹, 인포정보를 셋팅한다.
function setDomainInfo () {
	if(isBlankStr($("#frmInput #dmnLnm").val())) return;
	var vUrl = "<c:url value='/meta/stnd/getdmninfojson.do'/>";
	var param = "dmnLnm=" + $("#frmInput #dmnLnm").val();
		param += "&dmnPnm="+$("#frmInput #dmnPnm").val();
// 		param += "&rqstSno="+$("#frmInput #rqstSno").val();
		param += "&rqstNo="+$("#mstFrm #rqstNo").val();
		
	ajax2Json(vUrl, param, function(data){
		$("#divMsgPopup").remove();
// 			$("#dmnLnm").val("");
			$("#dmngLnm").val("");
			$("#dmngId").val("");
			$("#infotpLnm").val("");
			$("#dataType").val("");
			$("#dataLen").val("");
			$("#dataScal").val("");
			
		    for(var i=0; i<firstSelectBoxId.length; i++){
			    setselectbytext($("#frmInput #"+firstSelectBoxId[i]), data[firstSelectBoxNm[i]]);
				$("#frmInput #"+firstSelectBoxId[i]).change();
		    }

			setselectbytext($("#frmInput #infotpId"), data.infotpLnm);
			$("#frmInput #infotpId").change();

	});
}
//도메인 명에 따라 해당하는 도메인 그룹, 인포정보를 셋팅한다.
function setDomainInfoinit () {

	if(isBlankStr($("#frmInput #dmnLnm").val())) return;
	var vUrl = "<c:url value='/meta/stnd/getdmninfojson.do'/>";
	var param = "dmnLnm=" + $("#frmInput #dmnLnm").val();
	    param += "&dmnPnm=" + $("#frmInput #dmnPnm").val();
// 	    param += "&rqstSno="+$("#frmInput #rqstSno").val();
		param += "&rqstNo="+$("#mstFrm #rqstNo").val();
	ajax2Json(vUrl, param, function(data){
		$("#divMsgPopup").remove();
	    for(var i=0; i<firstSelectBoxId.length; i++){
		    setselectbytext($("#frmInput #"+firstSelectBoxId[i]), data[firstSelectBoxNm[i]]);
			$("#frmInput #"+firstSelectBoxId[i]).change();
	    }
		setselectbytext($("#frmInput #infotpId"), data.infotpLnm);
	
		//setselectbytext($("#frmInput #infotpId"), infotpLnm);
		$("#frmInput #infotpId").change();
	});
}

function setDmngInfoInit(){
	
	if(isBlankStr($("#frmInput #dmngId").val())) return;
	
	double_select(dmnginfotpJson, $("#dmngId"));
	
	$("#frmInput #infotpId").val("${result.infotpId}");
}

</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
	
    <form id="frmInput" name="frmInput" method="post">

	  <input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" />
	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" />
		<fieldset>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

		<div style="clear:both; height:10px;"><span></span></div>
			<div class="stit"><s:message code="STRD.TERMS.INFO" /></div> <!-- 표준용어정보 -->
 		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="STRD.TERMS.INFO" /></legend> <!-- 표준용어정보 -->
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.TERMS.INFO' />"> <!-- 표준용어정보 -->
				<caption><s:message code="STRD.TERMS.INFO.INPT" />	</caption> <!-- 표준용어 정보입력 -->
				<colgroup>
				<col style="width:15%;" />
				<col style="width:35%;" />
				<col style="width:15%;" />
				<col style="width:35%;" />
				</colgroup>
				<tbody>
					<tr>
						<%-- <th scope="row" class="th_require"><label for="sditmLnm"><s:message code="STRD.TERMS.LGC.NM" /></label></th> --%> <!-- 표준용어논리명 -->
						<th scope="row" class="th_require"><label for="sditmLnm"><s:message code="LGC.NM" /></label></th><!-- 영문판용(한글버젼시 위 주석 사용) -->
						<td>
							<input type="text" id="sditmLnm" name="sditmLnm" class="wd200" value="${result.sditmLnm}"  />
                              	
						</td>
						<%-- <th scope="row" class="th_require"><label for="sditmPnm"><s:message code="STRD.TERMS.PHYC.NM" /></label></th> --%> <!-- 표준용어물리명 -->
						<th scope="row" class="th_require"><label for="sditmPnm"><s:message code="PHYC.NM" /></label></th> <!-- 영문판용(한글버젼시 위 주석 사용) -->
						<td ><input type="text" id="sditmPnm" name="sditmPnm" value="${result.sditmPnm}" class="wd200" />
								<button class="btnSearchPop" id="sditmStwdPop"><s:message code="SRCH" /></button></td> <!-- 검색 -->
					</tr>
					
					<!-- 
					<tr>
						<th scope="row" ><label for="lnmCriDs"><s:message code="LGC.NM.BASE.DSTC" /></label></th> 
						<td><input type="text" id="lnmCriDs" name="lnmCriDs" class="wd200" value="${result.lnmCriDs}"  readonly/></td>
						<th scope="row" ><label for="pnmCriDs"><s:message code="PHYC.NM.BASE.DSTC" /></label></th> 
						<td><input type="text" id="pnmCriDs" name="pnmCriDs" class="wd200" value="${result.pnmCriDs}" readonly /></td>
					</tr>
										
					<tr>
						<th scope="row" class=""><label for="dmnLnm"><s:message code="DMN.LGC.NM" /></label></th> 
						<td colspan="3">
							<input type="text" id="dmnLnm" name="dmnLnm" class="wd200" value="${result.dmnLnm}"  />
							<input type="hidden" id="dmnPnm" name="dmnPnm" class="wd200" value="${result.dmnPnm}"  />
							<button class="btnSearchPop" type="button" id="btnDmnSch"	name="btnDmnSch"><s:message code="SRCH" /></button> 
						</td>
					</tr>
					-->
					
					<tr>
						<th scope="row"><label ><s:message code="DMN.GRP.INFO.TY" /></label></th><!-- 도메인그룹/인포타입 -->
						<td>
							<div id="selectBoxDiv"> <span></span></div>
<%-- 							<select id="dmngId" class="" name="dmngId" > --%>
<!-- 								<option value=""></option> -->
<%-- 							</select> --%>
<%-- 							<select id="infotpId" class="" name="infotpId"> --%>
<!-- 								<option value=""></option> -->
<%-- 						 	</select> --%>
						 	<input type="hidden" name="dmngLnm" id="dmngLnm" value="${result.dmngLnm }" >
						 	<input type="hidden" name=infotpLnm id="infotpLnm" value="${result.infotpLnm }">
						</td>
						<th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
						<td>
							<span class="input_inactive"><input type="text" class="wd80" id="dataType"  name="dataType" value="${result.dataType}" readonly/></span>
							<span class="input_inactive"><input type="text" class="wd50"  id="dataLen" name="dataLen"    value="${result.dataLen}" readonly/></span>
							<span class="input_inactive"><input type="text" class="wd50"  id="dataScal" name="dataScal"  value="${result.dataScal}" readonly/></span>
						</td>
					</tr>
<!--  					<tr>
						<th scope="row"><label for="encYn">암호화여부</label></th>
						<td colspan="3">
							<select id="encYn" name="encYn">
								<option value="N">아니오</option> 
								<option value="Y">예</option> 
								</select>
						</td>
					</tr>
-->					
					<tr>
						<th scope="row" class="th_require"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
						<td colspan="3"><textarea class="wd98p" id="objDescn" name="objDescn" >${result.objDescn}</textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
		</fieldset>
		</form>
		<!-- 입력폼 끝 -->
		
		<div style="clear:both; height:10px;"><span></span></div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
		<!-- 입력폼 끝 -->
	</div>
</body>
</html>
