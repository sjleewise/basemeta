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

$(document).ready(function(){
	// 범정부 사용 여부 세션 값
	var govYn = "<%=session.getAttribute("govYn")%>";
	
	// 범정부 사용 여부 N일때 숨기기
 	if ("${govYn}" == "N") {
 		$("#gov1").hide(); // 범정부 중앙에타 연계항목
 		$("#gov2").hide(); // 범정부 중앙에타 연계항목 테이블
 	}
	
	// 범정부 사용 여부 Y일때 보이기
 	if ("${govYn}" == "Y") {
 		$("#gov1").show();
 		$("#gov2").show();
 	}
	
	// 처음 공개/비공개여부가 '선택'일 경우 비공개사유 콤보박스 선택할 수 없도록 제한
 	if ("${govYn}" == "Y" && $('#openYn').val() != '02' ) {
 		$('#priRsn').attr("disabled", true);
 	}
	
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
// 			lnmCriDs	: "required",
// 			dmnLnm	: "required"
// 			lnmCriDs	: "required",
// 			pnmCriDs	: "required"	
			objDescn    : "required",
			stndAsrt	: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			sditmLnm	: requiremessage,
			sditmPnm 	: requiremessage,
// 			lnmCriDs	: requiremessage,
// 			dmnLnm	: requiremessage
// 			lnmCriDs	: shotrequiremessage,
// 			pnmCriDs	: shotrequiremessage
			objDescn    : requiremessage,
			stndAsrt	: requiremessage
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

	$("#encYnSel").change(function encValChg(){
		$("#encYn").val($("#encYnSel").val());
	});
	$("#dupYnSel").change(function dupValChg(){
		$("#dupYn").val($("#dupYnSel").val());
	});
	
	
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
		
		//논리명기준구분, 물리명기준구분 검증....
		if ($("#frmInput #sditmLnm").val() != $("#frmInput #dmnLnm").val() ) {
			if (isBlankStr($("#frmInput #lnmCriDs").val()) || isBlankStr($("#frmInput #pnmCriDs").val()) ) {
// 			alert("표준용어논리명과 도메인논리명이 틀릴경우 논리명기준구분과 물리명기준구분을 작성해야 합니다.");
			var message = "<s:message code="VALID.LNMCRIDS" />"; 
			
			showMsgBox("ERR", message);
			return false;
			}
		}
		
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');

	}).show();
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
// 		$("form[name=frmInput]")[0].reset();
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
	
	$('#btnSditmClr').click(function(){
		$("#sditmLnm").val("");
		$("#sditmPnm").val("");
		$("#lnmCriDs").val("");
		$("#pnmCriDs").val("");
		$("#dmnLnm").val("");
		$("#dmnPnm").val("");
		$("#dmngLnm").val("");
		$("#dmngId").val("").change();
		//$("#infotpId").val("").change();
		$("#infotpLnm").val("");
		$("#dataType").val("");
		$("#dataLen").val("");
		$("#dataScal").val("");
		$("#frmInput #encYn").val("");
		$("#frmInput #dupYn").val("");
		$("#frmInput #encYnSel").val("N");
		$("#frmInput #dupYnSel").val("N");
	});
	
// 	$('#btnDmnClr').click(function(){ $("#dmnLnm").val("");  });

// 	$('#btnDmnSch').click(function(){ setDmn(); });

	
	//표준용어 물리명 검색
    $("#sditmStwdPop").click(function(){
    	var param = $("form#frmInput").serialize();
			param += "&rqstNo="+$("#rqstNo", parent.document).val();
			//openLayerPop("<c:url value='/meta/stnd/sditmdupstwd_pop.do' />", 800, 600, param);
    }).hide();
    
    //도메인 검색 팝업 호출
	$("#btnDmnSch").click(function(){
		var param = "dmnLnm="+encodeURIComponent($("#dmnLnm").val());
		openLayerPop ("<c:url value='/meta/stnd/popup/stnddmn_pop.do' />", 1000, 600, param);
// 		OpenWindow("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", "dkafda",  800, 600, "yes");
// 		openSearchPop("<c:url value='/meta/model/pop/subjSearchPop.do' />", param);
    });
	
	//표준용어분리
	$('#btnSditmDiv').click(function(){
		
		if($("#stndAsrt").val()==''){
			showMsgBox("INF","<s:message  code='MSG.STND.ASRT.CHC' />");
			return;
			
		}
		
		if($("#sditmLnm").val() == "") return;
		var vUrl = "<c:url value='/meta/stnd/getsditmdivjson.do'/>";
		var param = "sditmLnm=" + $("#sditmLnm").val();
			param += "&rqstNo="+$("#mstFrm #rqstNo").val();
			param += "&stndAsrt="+ $("#stndAsrt").val();
		ajax2Json(vUrl, param, function(data){
			$("#divMsgPopup").remove();
					$("#sditmLnm").val("");
					$("#sditmPnm").val("");
					$("#lnmCriDs").val("");
					$("#pnmCriDs").val("");
					$("#dmnLnm").val("");
					$("#dmnPnm").val("");
					$("#dmngLnm").val("");
					$("#uppDmngId").val("");
					$("#dmngId").val("");
					$("#infotpLnm").val("");
					$("#dataType").val("");
					$("#dataLen").val("");
					$("#dataScal").val("");
// 					set_infoType_select($("#dmngId").val());
					$("#frmInput #uppDmngId").val(data.uppDmngId).change();
					$("#frmInput #dmngId").val(data.dmngId).change();
// 					$("#frmInput #dmngId").change();
					$("#frmInput #infotpId").val(data.infotpId).change();
// 					$("#frmInput #infotpId").change();
					$("#sditmLnm").val(data.sditmLnm);
					$("#sditmPnm").val(data.sditmPnm);
					$("#lnmCriDs").val(data.lnmCriDs);
					$("#pnmCriDs").val(data.pnmCriDs);
					$("#frmInput #encYn").val(data.encYn);
					$("#frmInput #dupYn").val(data.dupYn);
					$("#frmInput #encYnSel").val(data.encYn);
					$("#frmInput #dupYnSel").val(data.dupYn);
					if($("#sditmPnm").val().indexOf("[D]") > -1) {
						var message = "<s:message  code='ITEM.DUPWORD' />";
						showMsgBox("CNF", message, click_btnSditmGen );
					};
					$("#dmnLnm").val(data.dmnLnm);
					$("#dmnPnm").val(data.dmnPnm);
					if($("#dmnPnm").val().indexOf("[D]") > -1) {
						var message = "<s:message  code='ITEM.DUPDMN' />";
						showMsgBox("CNF", message, click_btnDmnSch );
					};
					
// 					$("#dmngLnm").val(data.dmngLnm);
// 					$("#infotpLnm").val(data.infotpLnm);
// 					$("#dataType").val(data.dataType);
// 					$("#dataLen").val(data.dataLen);
// 					$("#dataScal").val(data.dataScal);
		});

	});

	//항목자동분할
	$("#btnSditmGen").click(function(){
		if($("#stndAsrt").val()==''){
			showMsgBox("INF","<s:message  code='MSG.STND.ASRT.CHC' />");
			return;
			
		}
//			openSearchPop("<c:url value='/meta/stnd/sditmlst_pop.do' />");
			var param = "";
				param += "rqstNo="+$("#mstFrm #rqstNo").val();
			    param += "&sditmLnm="+ encodeURIComponent($("#sditmLnm").val());
			    param += "&stndAsrt="+ $("#stndAsrt").val();
			openLayerPop ("<c:url value='/meta/stnd/popup/stnditemdivision_pop.do' />", 1000, 600, param);
// 			var popup = OpenWindow("<c:url value="/meta/stnd/sditmdvcanlst_pop.do"/>"+param,"sditmGen","800","600","yes");
// 			popup.focus();
	});
	
	// 인포타입 변경 이벤트
// 	$("#infotpLnm").change( function(){
// 		var jsonlist = infotpinfolstJson; 
// 		$("#dataType").val("");
// 		$("#dataLen").val("");
// 		$("#dataScal").val("");
// 		for(var i=0; i < jsonlist.length; i++) {
// 			if(jsonlist[i].infotpId == $("#infotpLnm").val()) {
// 				$("#dataType").val(jsonlist[i].dataType);
// 				$("#dataLen").val(jsonlist[i].dataLen);
// 				$("#dataScal").val(jsonlist[i].dataScal);
// 				break;
// 			};
// 		};
// 	} );
	
	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	$("#frmInput #encYn").val('${result.encYn}');
	$("#frmInput #stndAsrt").val('${result.stndAsrt}');	
// 	$("#frmInput #persInfoGrd").val('${result.persInfoGrd}');	
	$("#frmInput #dupYn").val('${result.dupYn}');
 	$("#frmInput #persInfoCnvYn").val('${result.persInfoCnvYn}');
 	$("#frmInput #encYnSel").val('${result.encYn}');
	$("#frmInput #dupYnSel").val('${result.dupYn}');

 	$("#frmInput #openYn").val('${result.openYn}');
 	$("#frmInput #priRsn").val('${result.priRsn}');
 	$("#frmInput #prsnInfoYn").val('${result.prsnInfoYn}');

	
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
	//create_selectbox2(생성할 divId, 생성할 갯수, 생성할 아이디, 첫칸 빈칸표시여부, readOnly여부)
    create_selectbox2($("#selectBoxDiv"), bscLvl + 1, selectBoxId+"|infotpId",null ,false);
	
	$("#"+firstSelectBoxId[0]).attr("disabled",true); //도메인그룹은 선택불가
	
	$("#frmInput #stndAsrt").change(function() {
        $("#lnmCriDs").val("");
        $("#pnmCriDs").val("");
        $("#dmnPnm").val("");
        $("#"+firstSelectBoxId[0]).val("");
        double_selectStndAsrt(dmnginfotpJson, $("#"+firstSelectBoxId[0]),$("#stndAsrt option:selected").val());
	   	$('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
	// 		alert("2");
			double_selectStndAsrt(dmnginfotpJson, $(this),$("#stndAsrt option:selected").val());
	// 		alert($("#dmngId option:selected").text());
			//$("#dmngLnm").val($(this).prev().find("option:selected").text());
	        $("#dmngLnm").val($("#dmngId").find("option:selected").text());
			$("#infotpLnm").val($("#infotpId option:selected").text());
			
// 			$("#dataType, #dataLen, #dataScal").val("");

			/*
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
// 						var dataTypeTrans = "Oracle   :   "+jsonlist[i].oraDataType
// 						+"                       MySQL   :   "+jsonlist[i].myDataType
// 						+"                       MS-SQL   :   "+jsonlist[i].msDataType;
// 						$("#dataTypeTrans").text(dataTypeTrans)
						break;
					};
				};
			}
			*/
		});
	});
	// 	alert('${result.dmngLnm}');
		//도메인그룹/인포타입 초기화...
		setDomainInfo ();
	// 	setDomainInfoinit ();
	
		$('#persSearchPop').button({   //주제영역 검색팝업 이벤트
    	    icons: {
    	        primary: "ui-icon-search"
    	      },
    	      text: false, 
    	      create: function (event, ui) {
//    	     	  $(this).addClass('search_button');
    			  $(this).css({
    				  'width': '18px',
    				  'height': '18px',
    				  'vertical-align': 'middle',
    				  'border-radius' : '8px 8px 8px 8px',
    				  'border-bottom' : '1px solid #ddd'
    				  });
    	    	  
    	      }
    	    }).click(function(event){
    	    	
    		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
    		
    		//$('div#popSearch iframe').attr('src', "/wiseda/meta/test/pop/testpop.do");
    		//$('div#popSearch').dialog("open");
    	    	 /* var url = "https://wiki.ebaykorea.com/pages/viewpage.action?pageId=68833315"; */
    	    	/* window.open(url, '_blank'); */
  	    	   var url = "/wiseda/meta/stnd/popup/persSearchPop.do";
  	    	   var popwin = OpenModal(url, "searchpop",  1200, 600, "no");
  			   popwin.focus();
  		    	
    		
    	}).parent().buttonset();	
		
// 	//개인정보여부 예로 체크하면 개인정보등급 필수 
// 	$('#persInfoCnvYn').change(function(event){	
// 		if($("#persInfoCnvYn").val()=='Y'){
// 			$("#tabs-1 #frmInput").validate().settings.rules.persInfoGrd=({required:true});
// 			$("#tabs-1 #frmInput").validate().settings.messages.persInfoGrd=("필수입력항목입니다. 내용을 입력해 주세요.");
// 			$("#frmInput > fieldset > div.tb_basic > table > tbody > tr:nth-child(7) > th:nth-child(3)").addClass("th_require");
// 		}else{
// 			$("#tabs-1 #frmInput").validate().settings.rules.persInfoGrd=({required:false});
// 			$("#tabs-1 #frmInput").validate().settings.messages.persInfoGrd=("");
// 			$("#frmInput > fieldset > div.tb_basic > table > tbody > tr:nth-child(7) > th:nth-child(3)").removeClass("th_require");
// 		}
		
// 	});
	
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
	$("#frmInput #dupYn").val(retjson.dupYn);
	$("#frmInput #encYnSel").val(retjson.encYn);
	$("#frmInput #dupYnSel").val(retjson.dupYn);
	
	setDomainInfo();
// 	setDomainInfoinit();
	
}

//인포타입명에 따라 해당하는 도메인 그룹, 인포정보를 셋팅한다.
function setDomainInfo2(infotpLnm, encYn, dupYn) {
	$("#encYnSel").attr('disabled', false);
	$("#dupYnSel").attr('disabled', false);
	
	if(isBlankStr($("#frmInput #dmnLnm").val())) return;
	var vUrl = "<c:url value='/meta/stnd/getdmninfojson.do'/>";
	var param = "dmnLnm=" + $("#frmInput #dmnLnm").val();
		param += "&dmnPnm="+$("#frmInput #dmnPnm").val();
// 		param += "&rqstSno="+$("#frmInput #rqstSno").val();
		param += "&rqstNo="+$("#mstFrm #rqstNo").val();
		param += "&stndAsrt="+$("#frmInput #stndAsrt").val();
	ajax2Json(vUrl, param, function(data){
		$("#divMsgPopup").remove();
		
// 			$("#dmnLnm").val("");
			$("#dmngLnm").val("");
			$("#dmngId").val("");
			$("#infotpLnm").val("");
// 			if(typeof data.dataType != "undefined"){
// 				$("#dataType").val(data.dataType);
// 			}
// 			if(typeof data.dataLen != "undefined"){
// 				$("#dataLen").val(data.dataLen);
// 			}
// 			if(typeof data.dataScal != "undefined"){
// 				$("#dataScal").val(data.dataScal);
// 			}
			if(typeof data.encYn != "undefined" && data.encYn == encYn){
				$("#encYn").val(data.encYn);
				$("#encYnSel").val(data.encYn);
			} else {
				$("#encYn").val(encYn);
				$("#encYnSel").val(encYn);
			}
			if(typeof data.dupYn != "undefined" && data.dupYn == dupYn){
				$("#dupYn").val(data.dupYn);
				$("#dupYnSel").val(data.dupYn);
			} else {
				$("#dupYn").val(dupYn);
				$("#dupYnSel").val(dupYn);
			}
			
			if(data.encYn == "Y" && data.encYn == encYn){
				$("#encYnSel").attr('disabled', true);
			}
			if(data.dupYn == "Y" && data.dupYn == dupYn){
				$("#dupYnSel").attr('disabled', true);
			}

			

		    for(var i=0; i<firstSelectBoxId.length; i++){
			    setselectbytext($("#frmInput #"+firstSelectBoxId[i]), data[firstSelectBoxNm[i]]);
				$("#frmInput #"+firstSelectBoxId[i]).change();
		    }

// 			setselectbytext($("#frmInput #infotpId"), data.infotpLnm);

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
		param += "&stndAsrt="+$("#frmInput #stndAsrt").val();
		
	ajax2Json(vUrl, param, function(data){
		$("#divMsgPopup").remove();
// 			$("#dmnLnm").val("");
			$("#dmngLnm").val("");
			$("#dmngId").val("");
			$("#infotpLnm").val("");
			if(typeof data.dataType != "undefined"){
				$("#dataType").val(data.dataType);
			}
			if(typeof data.dataLen != "undefined"){
				$("#dataLen").val(data.dataLen);
			}
			if(typeof data.dataScal != "undefined"){
				$("#dataScal").val(data.dataScal);
			}
			if(typeof data.encYn != "undefined"){
				$("#encYn").val(data.encYn);
				$("#encYnSel").val(data.encYn);
			}
			if(typeof data.dupYn != "undefined"){
				$("#dupYn").val(data.dupYn);
				$("#dupYnSel").val(data.dupYn);
			}
			
			if(data.encYn == "Y"){
				$("#encYnSel").attr('disabled', true);
			}
			if(data.dupYn == "Y"){
				$("#dupYnSel").attr('disabled', true);
			}
			
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

//범정부 연계 항목
//공개/비공개 여부에 따른 비공개사유 콤보박스 제어
function OpenRsn(){
	if($("#openYn").val() == 'N'){				//비공개 선택
		
		$('#priRsn').attr("disabled",false);	//비공개사유 콤보박스 활성화
		
	}else{
		
		$('#priRsn').attr("disabled",true);	    //비공개사유 콤보박스 비활성화
		$('#priRsn').val('');					//비공개사유 값 초기화
		
	}
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
				    <th scope="row" class="th_require"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!-- 표준분류  -->
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
						<%-- <th scope="row" class="th_require"><label for="sditmLnm"><s:message code="STRD.TERMS.LGC.NM" /></label></th> --%> <!-- 표준용어논리명 -->
						<th scope="row" class="th_require"><label for="sditmLnm"><s:message code="LGC.NM" /></label></th><!-- 영문판용(한글버젼시 위 주석 사용) -->
						<td>
							<input type="text" id="sditmLnm" name="sditmLnm" class="wd200" value="${result.sditmLnm}"  />
                              	<button class="btnDelPop" type="button" id="btnSditmClr"	name="btnSditmClr"><s:message code="CLR" /></button> <!-- 지우기 -->
                              	<button class="btnSearchPop" type="button" id="btnSditmGen"	name="btnSditmGen"><s:message code="ATMD.DIV" /></button> <!-- 자동분할 -->
                              	<button class="btnSearchPop" type="button" id="btnSditmDiv"	name="btnSditmDiv"><s:message code="SPRT" /></button><!-- 분리 -->
						</td>
						<%-- <th scope="row" class="th_require"><label for="sditmPnm"><s:message code="STRD.TERMS.PHYC.NM" /></label></th> --%> <!-- 표준용어물리명 -->
						<th scope="row" class="th_require"><label for="sditmPnm"><s:message code="PHYC.NM" /></label></th> <!-- 영문판용(한글버젼시 위 주석 사용) -->
						<td ><input type="text" id="sditmPnm" name="sditmPnm" value="${result.sditmPnm}" class="wd200" />
								<button class="btnSearchPop" id="sditmStwdPop"><s:message code="SRCH" /></button></td> <!-- 검색 -->
					</tr>
					<tr>
						<th scope="row" ><label for="lnmCriDs"><s:message code="LGC.NM.BASE.DSTC" /></label></th> <!-- 논리명기준구분 -->
						<td><input type="text" id="lnmCriDs" name="lnmCriDs" class="wd200" value="${result.lnmCriDs}"  readonly/></td>
						<th scope="row" ><label for="pnmCriDs"><s:message code="PHYC.NM.BASE.DSTC" /></label></th> <!-- 물리명기준구분 -->
						<td><input type="text" id="pnmCriDs" name="pnmCriDs" class="wd200" value="${result.pnmCriDs}" readonly /></td>
					</tr>
					<tr>
						<th scope="row" class=""><label for="dmnLnm"><s:message code="DMN.LGC.NM" /></label></th> <!-- 도메인논리명 -->
						<td colspan="3">
								<input type="text" id="dmnLnm" name="dmnLnm" class="wd200" value="${result.dmnLnm}"  />
								<input type="hidden" id="dmnPnm" name="dmnPnm" class="wd200" value="${result.dmnPnm}"  />
<%-- 							<span class="input_inactive"><input type="text" id="dmnLnm" name="dmnLnm"  value="${result.dmnLnm}" readonly /></span> --%>
<!--  								<button class="btnDelPop" type="button" id="btnDmnClr"	name="btnDmnClr">지우기</button> -->
								<button class="btnSearchPop" type="button" id="btnDmnSch"	name="btnDmnSch"><s:message code="SRCH" /></button> <!-- 검색 -->
							</td>
					</tr>
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
 					<tr>
						<th scope="row"><label for="encYnSel"><s:message code="ENTN.YN"/></label></th> <!-- 암호화여부 -->
						<td>
							<input type="hidden" name="encYn" id="encYn" value="${result.encYn }" >
							<select id="encYnSel" name="encYnSel">
								<option value="N"><s:message code="MSG.NO"/></option> 
								<option value="Y"><s:message code="MSG.YES"/></option> 
							</select>
						</td>
											
						<th scope="row"><label for="persInfoCnvYn">고객정보변환여부</label></th> 
						<td>
							<select id="persInfoCnvYn" name="persInfoCnvYn">
								<option value="N">아니오</option> 
								<option value="Y">예</option> 
							</select>
						</td>
						
<!-- 						<th scope="row"><label for="dataTypeTrans">데이터타입변환</label></th> -->
<!-- 						<td id="dataTypeTrans"></td> -->
					</tr>
<!-- 					<tr> -->
<!-- 				   		<th scope="row"><label for="dupYnSel">중복여부</label></th> 중복여부 -->
<!-- 						<td> -->
<%-- 							<input type="hidden" name="dupYn" id="dupYn" value="${result.dupYn }" > --%>
<%-- 							<select id="dupYnSel" name="dupYnSel"> --%>
<%-- 								<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 --> --%>
<%-- 								<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%-- 							</select> --%>
<!-- 						</td> -->
<!-- 				    </tr> -->
<!--  					<tr> -->

<!-- 						<th scope="row"><label for="persInfoCnvYn">개인정보여부</label></th> -->
<!-- 						<td> -->
<%-- 							<select id="persInfoCnvYn" name="persInfoCnvYn"> --%>
<!-- 								<option value="N">아니오</option>  -->
<!-- 								<option value="Y">예</option>  -->
<%-- 							</select> --%>
<!-- 						</td> -->
<!-- 						<th scope="row"><label for="persInfoGrd">개인정보등급</label></th> -->
<!-- 						<td> -->
<%-- 							<select  id="persInfoGrd" name="persInfoGrd"> --%>
<%-- 						        <option value=""><s:message code="CHC"/></option> --%>
<%-- 						        <c:forEach var="code" items="${codeMap.persInfoGrd}" varStatus="status"> --%>
<%-- 								  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 								</c:forEach> --%>
<%-- 							</select> --%>
<!-- 							<button class="btnSearchPop ui-button ui-widget ui-button-icon-only ui-corner-right" id="persSearchPop" role="button" aria-disabled="false" title="검색" > -->
<%-- 								<span class="ui-button-icon-primary ui-icon ui-icon-search"></span> --%>
<%-- 								<span class="ui-button-text">검색</span> --%>
<!-- 							</button> -->
<!-- 						</td> -->
<!-- 					</tr> -->
					
					<tr>
						<th scope="row" class="th_require"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
						<td colspan="3"><textarea class="wd98p" id="objDescn" name="objDescn" >${result.objDescn}</textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- 여기서부터 범정부 연계 항목 -->
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div id="gov1" class="stit"><s:message code="GOV.CEN.MET" /></div> <!-- 범정부 중앙에타 연계항목 -->
		 <div style="clear:both; height:5px;"><span></span></div>
 		 <legend><s:message code="GOV.CEN.MET" /></legend> <!-- 범정부 중앙에타 연계항목 -->
	 		 <div id="gov2" class="tb_basic">
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
	                       <th scope="row"><s:message code="OPEN.RSN.CD"/></th> <!-- 공개/비공개 여부 -->
	                       <td>
	                       		<select id="openYn" name="openYn" onchange="OpenRsn()">
	                       			<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
									<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
									<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
								</select>
	                       </td>
	                       <th scope="row"><s:message code="NOPEN.RSN"/></th> <!-- 비공개사유 -->
	                       <td>
	                       		<select id="priRsn" name="priRsn">
								  <option value=""><s:message code="CHC.NON" /></option> <!-- 선택안함 -->
								<c:forEach var="code" items="${codeMap.nopenRsnCd}" varStatus="status">
								  <option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
								</select>
	                       </td>
	                   </tr>
	                   <tr>
	                       <th scope="row"><s:message code="PRSN.INFO.YN"/></th> <!-- 개인정보 여부 -->
	                       <td colspan="3">
	                       		<select id="prsnInfoYn" name="prsnInfoYn">
	                       			<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
									<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
									<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
								</select>
	                       </td>
	                       <%-- <th scope="row" class="th_require"><s:message code="ENC.TRG.YN"/></th> <!-- 암호화여부 -->
	                       <td>
	                       		<select id="encTrgYn" name="encTrgYn">
	                       			<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
									<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
									<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
								</select>
	                       </td> --%>
	                   </tr>
	                   <tr>
	                   	   <th scope="row"><s:message code="CONST.CND"/></th> <!-- 제약조건 -->
	                       <td colspan="3">
	                       		<input type="text" id="constCnd" name="constCnd" class="wd500" value="${result.constCnd}" />
	                       </td>
	                   </tr> 
	               </tbody>
	           </table>
	       </div>
		</fieldset>
		</form>
		<!-- 입력폼 끝 -->
		<!-- 	* 항목을 ;로 분리하여 입력한후 '분리'버튼을 클릭하시면 자동으로 분리되어 단어구분에 입력됩니다. 마지막단어에는 도메인으로 입력하셔야 됩니다. (예: 가상;계약자;계좌번호) -->
		<div class="tb_comment"><s:message  code='REQ.ITEM.COMM' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
		<!-- 입력폼 끝 -->
	</div>
</body>
</html>
