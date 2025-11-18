<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="STRD.DATA.DEMD" /></title> <!-- 표준데이터요청 -->

<script type="text/javascript">

var interval = "";

var retapproveWord = -1;
var retapproveItem = -1;

//화면초기화 여부
var inititemifm = false;
var initwordifm = false;

var testitv;

//도메인그룹 selectbox
var bscLvl = parseInt("${bscLvl}");
var selectBoxId = "${selectBoxId}";
var selectBoxNm = "${selectBoxNm}";
var firstSelectBoxId = selectBoxId.split("|");
var firstSelectBoxNm = selectBoxNm.split("|");
    
$(document).ready(function() {
// 	var param = $("#mstFrm").serialize();
	
	var urlitem = '<c:url value="/meta/app/appstnditem_rqst_ifm.do" />';
	var urlword = '<c:url value="/meta/app/appstndword_rqst_ifm.do" />';
	
	$("#stnd_raido_btn").buttonset();
	
// 		$("#tabs").children().hide();
	$("input[name=stnddataradio]").click(function(){
		var param1 = $("#mstFrm").serialize();
// 		alert (param1);
		//각 탭 초기화 여부 확인 후 초기화 한다.
		var tidx = $(this).val(); 
		//alert(tidx);
		$("#tabs-dic").children().hide();
		$("#tabs-"+$(this).val()+'').show();
		
		switch (tidx) {
		case '1':
			if (!inititemifm) {$("#appstnditem_ifm").attr('src', urlitem+'?'+param1); }
			break;
		case '2':
			if (!initwordifm) {$("#appstndword_ifm").attr('src', urlword+'?'+param1); }
			break;
		} 

	});
	
	// 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		showMsgBox("PRC", "<s:message code="REQ.SUBMIT.INIT" />");
		dicifmload(); 
		testitv = setInterval(function(){
			if (inititemifm&&initwordifm) {
				//alert("ok");
				clearInterval(testitv);
				var itemgrid = $('#appstnditem_ifm')[0].contentWindow.grid_sheet;
				var wordgrid = $('#appstndword_ifm')[0].contentWindow.grid_sheet;
				
				var message = requestCountMessage ();
  					message +="<br>"+"<s:message code="CNF.RQST" />";
  					
				//등록가능한지 확인한다.vrfCd = 1
				var regchk1 = itemgrid.FindText("vrfCd", "<s:message code='REG.POSB' />"); //등록가능
				var regchk3 = wordgrid.FindText("vrfCd", "<s:message code='REG.POSB' />"); //등록가능
				
				//if(regchk) {alert (regchk);} 
				if(regchk1 > 0 || regchk3 > 0) {
					 if($("#mstFrm #aprLvl").val() > 0 ){            // 결재라인이 0보다크면 ...
					     showMsgBox("CNF", message, 'Submit');
					    }else{
					     showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'ReqApprove');  // 승인으로가세요~
					    }
				} else {
					showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
					return false;
				}
				
				
			}
		}, 500);
		
		
		
	});	
	
	//전체승인 버튼 이벤트 처리
	$("#btnAllApprove").click(function(){
		showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
		dicifmload();
		testitv = setInterval(function(){
			if (inititemifm&&initwordifm) {
				//alert("ok");
				clearInterval(testitv);
				
				//전체승인일 경우 마스터 정보에 업데이트한다. 
				$("#mstFrm #rvwStsCd").val("1");
				chgallapprove ();
				
				doAction("ReqApprove");
				
			}
		}, 500);
		
		
	});
	//전체반려 버튼 이벤트 처리
	$("#btnAllReject").click(function(){
		rejectMsgBox("ALLREJECT", "<s:message code="REQ.APPROVE.REJECT" />", reqallreject);
		
	});
	
	//검토처리 Event Bind
	$("#btnReqApprove").click(function(){
		showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
		dicifmload();
		testitv = setInterval(function(){
			if (inititemifm&&initwordifm) {
				//alert("ok");
				clearInterval(testitv);
				//showMsgBox("CNF", "<s:message code="CNF.APPR" />", 'ReqApprove');
				doAction("ReqApprove");
			}
		}, 500);
		
	});
		  
});

$(window).on('load',function() {
	
	$("#stnditemradio").click();
	
 	//표준항목 클릭...	
 	var bizDtlCd = "${waqMstr.bizDtlCd}";
	if(bizDtlCd == "API"){
		//표준항목 클릭
	 	$("#stnditemradio").click();
	}else if (bizDtlCd == "APD"){
		//표준단어 클릭
		$("#stndwordradio").click();
	}else{
		$("#stnditemradio").click();
	}
 	
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, null);
	
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
});


$(window).resize(
	
	function(){
				
		// grid_sheet.SetExtendLastCol(1);	
	}
);

//초기화 후 전제반려를 등록요청한다.
function reqallreject() {
	showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
	dicifmload();
	testitv = setInterval(function(){
		if (inititemifm&&initwordifm) {
			//alert("ok");
			clearInterval(testitv);
			
			chgallapprove ();
			
			doAction("ReqApprove");
			//전체승인일 경우 마스터 정보에 업데이트한다. 
			//$("#mstFrm #rvwStsCd").val("2");
			
		}
	}, 500);
}

//전체승인 및 반려 선택시 내용 업데이트...
function chgallapprove () {
	var itemgrid = $('#appstnditem_ifm')[0].contentWindow.grid_sheet;
	var wordgrid = $('#appstndword_ifm')[0].contentWindow.grid_sheet;

	//전체승인 및 반려를 선택했는지 확인하자....
	var mstrvwsts =  $("#mstFrm #rvwStsCd").val();
	var mstrvwCont=  $("#mstFrm #rvwConts").val();
	if ("1" == mstrvwsts ){
//			alert("전체승인");
		doAllApprove(itemgrid, "1");
		doAllApprove(wordgrid, "1");
//			return;
	} else if ("2" == mstrvwsts) {
//			alert("전체반려");
		
		doAllReject(itemgrid, "2", mstrvwCont);
		doAllReject(wordgrid, "2", mstrvwCont);
//			return;
	}
}


//도메인 및 표준단어 iframe 초기화 작업
function dicifmload() {
// 	alert("dicifmload start:" + inititemifm + ":" + initwordifm);
	
	var param = $("#mstFrm").serialize();
	
	
	var urlitem = '<c:url value="/meta/app/appstnditem_rqst_ifm.do" />';
	var urlword = '<c:url value="/meta/app/appstndword_rqst_ifm.do" />';

	if (!inititemifm || !initwordifm) {
		
			if (!initwordifm) {
			$("#tabs-dic").children().hide();
			$("#tabs-3").show();
			$("#appstndword_ifm").attr('src', urlword+'?'+param).load(function(){
				$("#tabs-dic").children().hide();
				$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();
			});
			
		}
	}
	
	
}

function doAction(sAction)
{
		
	switch(sAction)
	{
		case "New": //추가
			loadDetail();
			
			break;
			
		case "AddWam": //기존 표준단어 추가
		
			var popup = OpenWindow("<c:url value="/meta/app/popup/stndword_pop.do"/>","stndwordSearch","800","600","yes");
		
		break;

		case "Reset" :
			
			//폼내용 초기화.....
			$("#frmInput")[0].reset();
			
			//입력창을 이전 상태로 복원...
			//if($("#gridRow").val() == "") return;
			
			//ibs2formmapping($("#gridRow").val(), $("form#frmInput"), grid_sheet);
			
			break;

		case "Delete" :
			//트리 시트의 경우 하위 레벨도 체크하도록 변경...
	    	//setTreeCheckIBS(grid_sheet);

	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	//나중에 공통함수로 전환.... (그리스명, 조인savenm, joinchar, 체크savenm,);
	    	var tmpkey = getibscheckjoin(grid_sheet, "rqstSno");
	    	
	    	var url = "<c:url value="/meta/app/delAPDrqstlist.do"/>";
	    	var param = $("#mstFrm").serialize()+"&joinkey="+tmpkey;
	    	IBSpostJson2(url, null, param, ibscallback);
			
			break;
		case "SaveRow" :
			// 공통으로 처리...
			
			ibsSaveJson = getform2IBSjson($("#frmInput"));
// 			ibsSaveJson = $("#frmInput").serializeArray();
// 			ibsSaveJson = $("#frmInput").serializeObject();

			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/app/regStndWordRqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
			
		case "Search":
			//var param = "rqstNo="+$("#rqstNo", parent.document).val();
// 			alert($("#frmInput #bizDtlCd").val());
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/app/getAPDrqstlist.do" />", param);
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
			
		 	//grid_sheet.DoSearchScript("testJsonlist");
						
			//$('#frmInput')[0].reset();
			
			break;
			
		case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
			
			break;
			
		case "Approve" : //결재처리 실제 결제 처리....
			//alert("approve");
			
			if(retapproveWord >= 0 && retapproveItem >= 0){
			
// 				alert("결재진행 업데이트 가능...");
				var url = "<c:url value="/meta/app/approveStndTot.do"/>";
				var param = $("#mstFrm").serialize();
				IBSpostJson2(url, null, param, ibscallback);
				
			}
			
	   		break;
		case "Down2Excel":  //엑셀내려받기
		
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
			
			break;
			
		case "ReqApprove":  //결재처리
		
//	 		$('#appstnditem_ifm').contents().find("#btnReqApprove").click();
			
			var itemgrid = $('#appstnditem_ifm')[0].contentWindow.grid_sheet;
			var wordgrid = $('#appstndword_ifm')[0].contentWindow.grid_sheet;

			//alert("결재처리")
			//결재 결과를 초기화한다.
			retapproveWord = -1;
			retapproveItem = -1;

			//그리드 결재 대상 count
			retapproveItem = itemgrid.RowCount();
			retapproveWord = wordgrid.RowCount();

			// 표준항목, 도메인, 표준단어 모두 결재할 데이터가 하나도 없을 경우....
			if (retapproveItem == 0 && retapproveWord == 0) {
				showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
				return false;
			}
			
			if($("#mstFrm #aprLvl").val() < 1 ){      // 결재라인이 1보다 작으면
			    doAllApprove($('#appstnditem_ifm')[0].contentWindow.grid_sheet, "1");           // 승인처리로 변경
			    doAllApprove($('#appstndword_ifm')[0].contentWindow.grid_sheet, "1");           // 승인처리로 변경
			}
			
			// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
			if (chkRvwStsCd(itemgrid, "rvwStsCd") > -1 || chkRvwStsCd(wordgrid, "rvwStsCd") > -1) {
				//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
// 				showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
				approveMsgBox("APPROVE", "<s:message code="ERR.APPROVE.CNF" />");
				
				return false;
			};
			
			
			//반려 선택시 반려사유를 입력하도록 한다.
			var tmprow1 = chkRvwCont(itemgrid, "rvwStsCd", "rvwConts");
			var tmprow3 = chkRvwCont(wordgrid, "rvwStsCd", "rvwConts");
			
			//그리드 검토내용 작성여부
			if (tmprow1 > 0  || tmprow3 > 0) {
				showMsgBox("INF", "<s:message code="ERR.REJECT" />");
				return false;
			}
			
			//결재처리 총건수, 승인건수, 반려건수를 메세지로 만든다.
			var message = approveCountMessage ();
				message += "<s:message code="CNF.APPR" />";

			showMsgBox("CNF", message, prcapprdic);	
			
			break;

	}	   
}


//표준사전 각 결재 요청한다.
function prcapprdic() {
	showMsgBox("PRC", "<s:message code="REQ.APPROVE.PRC" />");
	if (retapproveWord != 0) {
		retapproveWord = -1;
 		$('#appstndword_ifm')[0].contentWindow.doAction("Approve");
	}
//		alert(retapproveWord);
	if (retapproveItem != 0) {
		retapproveItem = -1;
		$('#appstnditem_ifm')[0].contentWindow.doAction("Approve");
	}
//		alert(retapproveWord);
}

//등록요청 및 결재대상 건수를 확인하여 리턴한다.
function approveCountMessage () {
	var itemgrid = $('#appstnditem_ifm')[0].contentWindow.grid_sheet;
	var wordgrid = $('#appstndword_ifm')[0].contentWindow.grid_sheet;
	
	//결재처리 건수
	var cntitem = itemgrid.RowCount();
	var cntword = wordgrid.RowCount();
	
	//승인/반려 건수
	var cntitemAppr 	= countGridValue(itemgrid, "rvwStsCd", "1");
	var cntitemReject  	= countGridValue(itemgrid, "rvwStsCd", "2");
	var cntwordAppr  	= countGridValue(wordgrid, "rvwStsCd", "1");
	var cntwordReject  	= countGridValue(wordgrid, "rvwStsCd", "2");
	
	
	var message = "";
	if (cntitem > 0) message += "[<s:message code='APP.STRD.ITEM' />:"+cntitem+"<s:message code='CNT.APRV' /> "+cntitemAppr+"<s:message code='CNT.RTN' /> "+cntitemReject+"<s:message code='CNT' />]<br>";
	//APP표준항목, 건 중 승인, 건, 반려, 건
	if (cntword > 0) message += "[<s:message code='APP.STRD.WORD' />:"+cntword+"<s:message code='CNT.APRV' /> "+cntwordAppr+"<s:message code='CNT.RTN' /> "+cntwordReject+"<s:message code='CNT' />]<br>";
	//APP표준단어, 건 중 승인, 건, 반려, 건
	
	return message;
}

//등록요청 및 결재대상 건수를 확인하여 리턴한다.
function requestCountMessage () {
	var itemgrid = $('#appstnditem_ifm')[0].contentWindow.grid_sheet;
	var wordgrid = $('#appstndword_ifm')[0].contentWindow.grid_sheet;
	
	var cntitem = countGridValue(itemgrid, "vrfCd", "1");
	var cntword = countGridValue(wordgrid, "vrfCd", "1");
	
	var message = "";
	if (cntitem > 0) message += "[<s:message code='APP.STRD.ITEM' />:"+cntitem+"<s:message code='CNT' />]"; //APP표준항목, 건
	if (cntword > 0) message += "[<s:message code='APP.STRD.WORD' />:"+cntword+"<s:message code='CNT' />]"; //APP표준단어, 건
	
	return message;
}


 
function updateMst(mstvo) {
	json2formmapping ($("#mstFrm"), mstvo);
	$("#mstFrm #bizDtlCd").val("");
	if ($("#mstFrm #rqstStepCd").val() == "S")  {
//			$("#btnRegRqst").show();
		$("#btnRegRqst").show();
	}
	setDispRqstMainButton($("#mstFrm #rqstStepCd").val(), null);
	
	if (inititemifm) {
		json2formmapping ($('#appstnditem_ifm').contents().find("#mstFrm"), mstvo);
		$('#appstnditem_ifm').contents().find("#mstFrm #bizDtlCd").val("API");
	}
	if (initwordifm) {
		json2formmapping ($('#appstndword_ifm').contents().find("#mstFrm"), mstvo);
		$('#appstndword_ifm').contents().find("#mstFrm #bizDtlCd").val("APD");
	}
	
	return;

	dicifmload();
	testitv = setInterval(function(){
		if (inititemifm&&initwordifm) {
			//alert("ok");
			clearInterval(testitv);
			json2formmapping ($('#appstndword_ifm').contents().find("#mstFrm"), mstvo);
			json2formmapping ($('#appstnditem_ifm').contents().find("#mstFrm"), mstvo);
		
			$('#appstndword_ifm').contents().find("#mstFrm #bizDtlCd").val("APD");
			$('#appstnditem_ifm').contents().find("#mstFrm #bizDtlCd").val("API");
			
			}
	}, 500);

// 	$('#appstnditem_ifm')[0].contentWindow.grid_sheet;
// 	$('#stnddmn_ifm')[0].contentWindow.grid_sheet;
// 	$('#appstndword_ifm')[0].contentWindow.grid_sheet
	
}

/*======================================================================*/
// IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
// 	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val("APD");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} 
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 마스터 정보 셋팅...
			
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
// 	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val("APD");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/app/appstndtot_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}



</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="APP.DATA.REG" /></div> <!-- APP데이터등록 -->
<!-- 	<div class="stit">시스템영역 관리</div> -->
	</div>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:5px;"><span></span></div>

<div class="divLstBtn" >	 
	<div class="bt03">
		<div id="stnd_raido_btn">
			<input type="radio" id="stnditemradio" name="stnddataradio" value="1"><label for="stnditemradio"><s:message code="APP.ITEM" /></label> <!-- APP항목 -->
			<input type="radio" id="stndwordradio" name="stnddataradio" value="2"><label for="stndwordradio"><s:message code="APP.WORD" /></label> <!-- APP단어 -->
<!-- 			<input type="hidden" id="clickBtnNm" name="clickBtnNm" value=""/> -->
		</div>
	</div>
	<div class="bt02">
		<button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst"><s:message code="REG.DEMD" /></button> <!-- 등록요청 -->
		<span>
         <button class="btn_req_appr"    id="btnReqApprove" name="btnReqApprove"><s:message code="APRL.TRTT" /></button> <!-- 결재처리 -->
         <button class="btn_sel_appr"    id="btnSelApprove" name="btnSelApprove"><s:message code="APRV.RTN.CHC" /></button> <!-- 승인반려선택 -->
        </span>
       	<ul class="appr_button_menu" id="approveButtonMenu">
	   		<li class="btn_all_approve" id="btnAllApprove"><a><span class="ui-icon ui-icon-check"></span><s:message code="WHL.APRV" /></a></li> <!-- 전체승인 -->
		    <li class="btn_all_reject"  id="btnAllReject"><a><span class="ui-icon ui-icon-cancel"></span><s:message code="WHL.RTN" /></a></li> <!-- 전체반려 -->
		</ul> 
	</div>
</div>

<div style="clear:both; height:4px;"><span></span></div>
<div style="clear:both; height:2px; background:#EDEDED;"><span></span></div>
<div style="clear:both; height:6px;"><span></span></div>
<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<div id="tabs-dic">
	<div id="tabs-1">
		<div>
		<iframe id="appstnditem_ifm" name="appstnditem_ifm"  width="100%" height="800"   frameborder="0"></iframe>
		</div>
	</div>
	<div id="tabs-2">
		<div>
		<iframe id="appstndword_ifm" name="appstndword_ifm"  width="100%" height="800"  frameborder="0"></iframe>
		</div>
	</div>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>
</body>
</html>