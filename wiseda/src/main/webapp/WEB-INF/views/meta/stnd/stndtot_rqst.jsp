<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>  
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="STRD.DATA.DEMD" /></title> <!-- 표준데이터요청 -->

<script type="text/javascript">
//$("#mainContent").hide();
var interval = "";

var retapproveWord = -1;
var retapproveDmn = -1;
var retapproveItem = -1;

//화면초기화 여부
var inititemifm = false;
var initdmnifm = false;
var initwordifm = false;

var testitv;

//도메인그룹 selectbox
var bscLvl = parseInt("${bscLvl}");
var selectBoxId = "${selectBoxId}";
var selectBoxNm = "${selectBoxNm}";
var firstSelectBoxId = selectBoxId.split("|");
var firstSelectBoxNm = selectBoxNm.split("|");

//탭 셀렉트 코드
var stndTabSel = "${stndTabSel}";
//현재 선택된 탭
var selectedTab = "1"; 

$(document).ready(function() {
// 	var param = $("#mstFrm").serialize();
	
	var urlitem = '<c:url value="/meta/stnd/stnditem_rqst_ifm.do" />';
	var urldmn = '<c:url value="/meta/stnd/stnddmn_rqst_ifm.do" />';
	var urlword = '<c:url value="/meta/stnd/stndword_rqst_ifm.do" />';
// 	$("#stndword_ifm").attr('src', urlword+'?'+param);
// 	$("#stnddmn_ifm").attr('src', urldmn+'?'+param);
// 	$("#stnditem_ifm").attr('src', urlitem+'?'+param);
	
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
		//if (!inititemifm) {$("#stnditem_ifm").attr('src', urlitem+'?'+param1); }
		//if (!initdmnifm) {$("#stnddmn_ifm").attr('src', urldmn+'?'+param1); }
		//if (!initwordifm) {$("#stndword_ifm").attr('src', urlword+'?'+param1); }
		switch (tidx) {
		case '1':
			if (!inititemifm) {$("#stnditem_ifm").attr('src', urlitem+'?'+param1); }
			selectedTab ='1';
			//$("#mainContent").show();
			break;
		case '2':
			if (!initdmnifm) {$("#stnddmn_ifm").attr('src', urldmn+'?'+param1); }
			selectedTab ='2';
			//$("#mainContent").show();
			break;
		case '3':
			if (!initwordifm) {$("#stndword_ifm").attr('src', urlword+'?'+param1); }
			selectedTab ='3';
			//$("#mainContent").show();
			break;
		} 
		$("#stnd_raido_btn").show();
		
	});
	
// 	$("#stndwordradio").click();

// 	$("#mstFrm").attr({
// 			target : 'stnditem_ifm',
// 			type   : 'POST',
// 			action : urlitem
		
// 	}).submit();
// 	$("#mstFrm").attr({
// 			target : 'stnddmn_ifm',
// 			type   : 'POST',
// 			action : urldmn
		
// 	}).submit();
// 	$("#mstFrm").attr({
// 			target : 'stndword_ifm',
// 			type   : 'POST',
// 			action : urlword
		
// 	}).submit();
	
	
	//attr('checked', 'checked');
	
	//업무구분상세 초기화...
	//$("#mstFrm #bizDtlCd").val('STWD');
	
	//탭 초기화....
	//$("#tabs").tabs();
	
	//마우스 오버 이미지 초기화
// 	//imgConvert($('div.tab_navi a img'));
	
	// 등록요청 Event Bind
	$("#btnRegRqst").click(function(){

		//showMsgBox("PRC", "<s:message code="REQ.SUBMIT.INIT" />");

		dicifmload(); 

		testitv = setInterval(function(){
			if (inititemifm&&initdmnifm&&initwordifm) {
			
				clearInterval(testitv);

				var itemgrid = $('#stnditem_ifm')[0].contentWindow.grid_sheet;
				var dmngrid  = $('#stnddmn_ifm')[0].contentWindow.grid_sheet;
				var wordgrid = $('#stndword_ifm')[0].contentWindow.grid_sheet;
				
				
				var message = requestCountMessage ();
  					message +="<br>"+"<s:message code="CNF.RQST" />";
				
				//등록가능한지 확인한다.vrfCd = 1
				var regchk1 = itemgrid.FindText("vrfCd", "<s:message code='REG.POSB' />"); //등록가능
				var regchk2 = dmngrid.FindText("vrfCd", "<s:message code='REG.POSB' />"); //등록가능
				var regchk3 = wordgrid.FindText("vrfCd", "<s:message code='REG.POSB' />"); //등록가능
				
				//등록가능한지 확인한다.vrfCd = 3
				var regchk4 = itemgrid.FindText("vrfCd", "<s:message code='CNFR' />"); //확인
				var regchk5 = dmngrid.FindText("vrfCd", "<s:message code='CNFR' />"); //확인
				var regchk6 = wordgrid.FindText("vrfCd", "<s:message code='CNFR' />"); //확인
				
				var regerrchk1 = itemgrid.FindText("vrfCd", "<s:message code='VRFC.EROR' />"); //검증오류
				var regerrchk2 = dmngrid.FindText("vrfCd", "<s:message code='VRFC.EROR' />"); //검증오류
				var regerrchk3 = wordgrid.FindText("vrfCd", "<s:message code='VRFC.EROR' />"); //검증오류
				//$("#tabs-1").click();
				//$("#tabs-3").hide();
				//if(regchk) {alert (regchk);} 
				if(regchk1 > 0 || regchk2 > 0 || regchk3 > 0
						|| regchk4 > 0 || regchk5 > 0 || regchk6 > 0) {
					if(regerrchk1 > 0 || regerrchk2 >0 || regerrchk3> 0 ){
						showMsgBox("INF", "<s:message code="ERR.SUBMIT2" />");
					}else{
						showMsgBox("CNF", message, 'Submit');
					}
				} else {
					showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
					return false;
				}
				
				
			}
		}, 500);
		
		//return;
		
		
	});	
	
	//전체승인 버튼 이벤트 처리
	$("#btnAllApprove").click(function(){
		//showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");

		dicifmload();
		testitv = setInterval(function(){
			if (inititemifm&&initdmnifm&&initwordifm) {

				clearInterval(testitv);
				
				//전체승인일 경우 마스터 정보에 업데이트한다. 
				$("#mstFrm #rvwStsCd").val("1");
				chgallapprove ();
				
				//doAction("ReqApprove");  //전체승인시 승인여부만 바귀도록 주석처리
				
// 				doAllApprove($('#stnditem_ifm')[0].contentWindow.grid_sheet, "1");
// 				doAllApprove($('#stnddmn_ifm')[0].contentWindow.grid_sheet, "1");
// 				doAllApprove($('#stndword_ifm')[0].contentWindow.grid_sheet, "1");
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
			if (inititemifm&&initdmnifm&&initwordifm) {
				clearInterval(testitv);
				//showMsgBox("CNF", "<s:message code="CNF.APPR" />", 'ReqApprove');
				doAction("ReqApprove");
			}
		}, 500);
		
	});
	
	if(stndTabSel =="3")
 	{
 	    $("#stndwordradio").click();
 	}else if(stndTabSel =="2"){
 		$("#stnddmnradio").click();
 	}else{
 		$("#stnditemradio").click();
 	}
	

		  
});

$(window).on('load',function() {
// 	var param = $("#mstFrm").serialize();
	
// 	var urlitem = '<c:url value="/meta/stnd/stnditem_rqst_ifm.do" />';
// 	var urldmn = '<c:url value="/meta/stnd/stnddmn_rqst_ifm.do" />';
// 	var urlword = '<c:url value="/meta/stnd/stndword_rqst_ifm.do" />';
	
	
 	//표준용어 클릭...
 	//$("#stnditemradio").click();
 	//$("#stnddmnradio").click();
 	//$("#stndwordradio").click();
 	



//  	alert($("input[name=stnddataradio]:checked").val());
//  	$("#stnddmn_ifm").attr('src', urldmn+'?'+param); initdmnifm = true;
//  	$("#stndword_ifm").attr('src', urlword+'?'+param); initwordifm = true;

// 	$("#stndwordradio").click();
	
	//표준용어 페이지를 호출한다....
// 	var param = $("#mstFrm").serialize();
// 	var urlitem = '<c:url value="/meta/stnd/stnditem_rqst_ifm.do" />';
// 	var urldmn = '<c:url value="/meta/stnd/stnddmn_rqst_ifm.do" />';
// 	var urlword = '<c:url value="/meta/stnd/stndword_rqst_ifm.do" />';
	
	//표준단어 클릭...
// 	$("#stndwordradio").click();
// 	$("#stndword_ifm").attr('src', urlword+'?'+param).load(function(){
// 		var rqststep = $("#mstFrm #rqstStepCd").val();
// 		var wordgrid = $('#stndword_ifm')[0].contentWindow.grid_sheet;
// 		setDispRqstMainButton(rqststep, wordgrid);
// 		var param = $("#mstFrm").serialize();
// 		wordgrid.DoSearch("<c:url value="/meta/stnd/getstwdrqstlist.do" />", param);
		//표준도메인 클릭...
// 		$("#stnddmnradio").click();
// 		$("#stnddmn_ifm").attr('src', urldmn+'?'+param).load(function(){
			//표준용어 클릭...
			//$("#stnditemradio").click();
// 			$("#stnditem_ifm").attr('src', urlitem+'?'+param);
// 		});
		
// 	});
// 	$("#stndword_ifm").attr('src', urlword+'?'+param);
// 	$("#stnddmn_ifm").attr('src', urldmn+'?'+param);
// 	$("#stnditem_ifm").attr('src', urlitem+'?'+param);
// 	//표준용어 클릭...
// 	$("#stnditemradio").click();
	
	
	
// 	doAction("Search");

// 	testitv = setInterval(function(){
// 			clearInterval(testitv);
// 			dicifmload();
// 	}, 500);
	//$("#mainContent").show();
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
    setTimeout(function(){setDispRqstMainButton(rqststep, null);},300);
	
    //검토처리 버튼 보여주기....
    checkApproveYn($("#mstFrm"));
    $("#bt02btn").show();
});


$(window).resize(
	
	function(){
				
		// grid_sheet.SetExtendLastCol(1);	
	}
);

//초기화 후 전제반려를 등록요청한다.
function reqallreject() {
	//showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
	dicifmload();
	testitv = setInterval(function(){
		if (inititemifm&&initdmnifm&&initwordifm) {
			//alert("ok");
			clearInterval(testitv);
			
			chgallapprove ();
			
			//doAction("ReqApprove");
			//전체승인일 경우 마스터 정보에 업데이트한다. 
			//$("#mstFrm #rvwStsCd").val("2");
			
//				doAllApprove($('#stnditem_ifm')[0].contentWindow.grid_sheet, "2");
//				doAllApprove($('#stnddmn_ifm')[0].contentWindow.grid_sheet, "2");
//				doAllApprove($('#stndword_ifm')[0].contentWindow.grid_sheet, "2");
		}
	}, 500);
}

//전체승인 및 반려 선택시 내용 업데이트...
function chgallapprove () {
	var itemgrid = $('#stnditem_ifm')[0].contentWindow.grid_sheet;
	var dmngrid  = $('#stnddmn_ifm')[0].contentWindow.grid_sheet;
	var wordgrid = $('#stndword_ifm')[0].contentWindow.grid_sheet;

	//전체승인 및 반려를 선택했는지 확인하자....
	var mstrvwsts =  $("#mstFrm #rvwStsCd").val();
	var mstrvwCont=  $("#mstFrm #rvwConts").val();
	if ("1" == mstrvwsts ){
//			alert("전체승인");
        if(selectedTab =='1'){
		   doAllApprove(itemgrid, "1");
        }
        else if(selectedTab =='2'){
           doAllApprove(dmngrid, "1");
        }
        else if(selectedTab =='3'){
		  doAllApprove(wordgrid, "1");
        }
//			return;
	} else if ("2" == mstrvwsts) {
//			alert("전체반려");
		if(selectedTab =='1'){
		  doAllReject(itemgrid, "2", mstrvwCont);
		}
		else if(selectedTab =='2'){
		  doAllReject(dmngrid, "2", mstrvwCont);
		 }
        else if(selectedTab =='3'){
		  doAllReject(wordgrid, "2", mstrvwCont);
        }
//			return;
	}
}


//도메인 및 표준단어 iframe 초기화 작업
function dicifmload() {
// 	alert("dicifmload start:" + inititemifm + ":" + initdmnifm + ":" + initwordifm);
	
	var param = $("#mstFrm").serialize();
	
	var urlitem = '<c:url value="/meta/stnd/stnditem_rqst_ifm.do" />';
	var urldmn = '<c:url value="/meta/stnd/stnddmn_rqst_ifm.do" />';
	var urlword = '<c:url value="/meta/stnd/stndword_rqst_ifm.do" />';
	
	if (!inititemifm || !initdmnifm || !initwordifm) {
      
		//showMsgBox("INF", "<s:message code="CNF.GRID.SAVE" />");
		
// 		if (confirm("미확인 내용이 있습니다. 확인할래? ")) {
// 			if (!inititemifm) {$("#stnditemradio").click();}
		 if (!inititemifm) {
				$("#tabs-dic").children().hide();
				$("#tabs-1").show();
				$("#stnditem_ifm").attr('src', urlitem+'?'+param).on('load',function(){
					$("#tabs-dic").children().hide();
					$("#tabs-1").hide();
					$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();
					if (!initdmnifm) {
						$("#tabs-dic").children().hide();
						$("#tabs-2").show();
						$("#stnddmn_ifm").attr('src', urldmn+'?'+param).on('load',function(){
							$("#tabs").children().hide();
							$("#tabs-2").hide();
							$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();
							
						});
					} 
					
					//용어탭 확인
					if (!initwordifm) {
						$("#tabs-dic").children().hide();
						$("#tabs-3").show();
						$("#stndword_ifm").attr('src', urlword+'?'+param).on('load',function(){

							$("#tabs").children().hide();
							$("#tabs-3").hide();
							$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();
							
						});
					} 
				});
				
	     } 
		 else if (!initdmnifm) {
// 			$("#stnddmnradio").click();
			$("#tabs-dic").children().hide();
			$("#tabs-2").show();
			$("#stnddmn_ifm").attr('src', urldmn+'?'+param).on('load',function(){
				$("#tabs-dic").children().hide();
				$("#tabs-2").hide();
				$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();
				if (!initwordifm) {				
					$("#tabs-dic").children().hide();
					$("#tabs-3").show();
					$("#stndword_ifm").attr('src', urlword+'?'+param).on('load',function(){
						$("#tabs").children().hide();
						$("#tabs-3").hide();
						$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();
						
					});
				} 
				//용어탭 확인
				if (!inititemifm) {
					$("#tabs-dic").children().hide();
					$("#tabs-1").show();
					$("#stnditem_ifm").attr('src', urlitem+'?'+param).on('load',function(){
						$("#tabs").children().hide();
						$("#tabs-1").hide();
						$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();						
					});
				}
			}); 
		} else if (!initwordifm) {
			$("#tabs-dic").children().hide();
			$("#tabs-3").show();
			$("#stndword_ifm").attr('src', urlword+'?'+param).on('load',function(){
				$("#tabs-dic").children().hide();
				$("#tabs-3").hide();
				$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();
				if (!initdmnifm) {
					$("#tabs-dic").children().hide();
					$("#tabs-2").show();
					$("#stnddmn_ifm").attr('src', urldmn+'?'+param).on('load',function(){
						$("#tabs").children().hide();
						$("#tabs-2").hide();
						$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();			
					});
				} 
				//용어탭 확인
				if (!inititemifm) {
					$("#tabs-dic").children().hide();
					$("#tabs-1").show();
					$("#stnditem_ifm").attr('src', urlitem+'?'+param).on('load',function(){
						$("#tabs").children().hide();
						$("#tabs-1").hide();
						$("#tabs-"+$("input[name=stnddataradio]:checked").val()+'').show();
					});
				}
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
		
			var popup = OpenWindow("<c:url value="/meta/stnd/popup/stndword_pop.do"/>","stndwordSearch","800","600","yes");
		
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
	 
	    	var url = "<c:url value="/meta/stnd/delstwdrqstlist.do"/>";
	    	var param = $("#mstFrm").serialize()+"&joinkey="+tmpkey;
	    	IBSpostJson2(url, null, param, ibscallback);
			
			break;
		case "SaveRow" :
			// 공통으로 처리...
			
			ibsSaveJson = getform2IBSjson($("#frmInput"));
// 			ibsSaveJson = $("#frmInput").serializeArray();
// 			ibsSaveJson = $("#frmInput").serializeObject();

			if(ibsSaveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/stnd/regStndWordRqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			//여기
			
			break;
			
		case "Search":
			//var param = "rqstNo="+$("#rqstNo", parent.document).val();
// 			alert($("#frmInput #bizDtlCd").val());
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getstwdrqstlist.do" />", param);
			
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
			if(retapproveWord >= 0 && retapproveDmn >= 0 && retapproveItem >= 0){
				   
// 				alert("결재진행 업데이트 가능...");
				var url = "<c:url value="/meta/stnd/approveStndTot.do"/>";
				var param = $("#mstFrm").serialize();
				
				var itemJson = $('#stnditem_ifm')[0].contentWindow.grid_sheet.GetSaveJson(1);
				var dmnJson  = $('#stnddmn_ifm')[0].contentWindow.grid_sheet.GetSaveJson(1);
				var wordJson = $('#stndword_ifm')[0].contentWindow.grid_sheet.GetSaveJson(1);

				//alert(itemJson.Code);
				
				//=====필수입력항목 누락 체크 ====
				if (itemJson.data.length > 0 && itemJson.Code == "IBS000") return 0;
				if (dmnJson.data.length > 0  && dmnJson.Code == "IBS000")  return 0; 
				if (wordJson.data.length > 0 && wordJson.Code == "IBS000") return 0;
				//==========================
				
				var stndInfo = new Object(); 
				
				stndInfo.item = itemJson.data;
				stndInfo.dmn  = dmnJson.data;
				stndInfo.word = wordJson.data;
								 								
				var ibsSaveJson = stndInfo;
				
				//alert(JSON.stringify(ibsSaveJson));
								
				IBSpostJson2(url, ibsSaveJson, param, ibscallback);
				
			}
			
	   		break;
		case "Down2Excel":  //엑셀내려받기
		
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
			
			break;
			
		case "ReqApprove":  //결재처리

//	 		$('#stnditem_ifm').contents().find("#btnReqApprove").click();
			
			var itemgrid = $('#stnditem_ifm')[0].contentWindow.grid_sheet;
			var dmngrid  = $('#stnddmn_ifm')[0].contentWindow.grid_sheet;
			var wordgrid = $('#stndword_ifm')[0].contentWindow.grid_sheet;

			//alert("결재처리")
			//결재 결과를 초기화한다.
			retapproveWord = -1;
			retapproveDmn  = -1;
			retapproveItem = -1;

			
// 			if (!chkSheetDataModified(itemgrid)) retapproveItem = 0;
// 			if (!chkSheetDataModified(dmngrid)) retapproveDmn = 0;
// 			if (!chkSheetDataModified(wordgrid)) retapproveWord = 0;
			//그리드 결재 대상 count
			retapproveItem = itemgrid.RowCount();
			retapproveDmn  = dmngrid.RowCount();
			retapproveWord = wordgrid.RowCount();

			// 표준용어, 도메인, 표준단어 모두 결재할 데이터가 하나도 없을 경우....
			if (retapproveItem == 0 && retapproveDmn == 0 && retapproveWord == 0) {
				showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
				return false;
			}
			
			// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
			//if (chkRvwStsCd(itemgrid, "rvwStsCd") > -1  || chkRvwStsCd(dmngrid, "rvwStsCd") > -1 || chkRvwStsCd(wordgrid, "rvwStsCd") > -1) {
				//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
 				//showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
				//approveMsgBox("APPROVE", "<s:message code="ERR.APPROVE.CNF" />");
				//return false;
				
				//};
				
// 			console.log('${codeMap.rvwStsCdibs}');
            if (chkRvwStsCd(itemgrid, "rvwStsCd") > -1){
	            showMsgBox("INF", "<s:message code="ERR.APPROVE.SDITM" />");
	            return false;
            }else if(chkRvwStsCd(dmngrid, "rvwStsCd") > -1){
	            showMsgBox("INF", "<s:message code="ERR.APPROVE.DMN" />");
	            return false;
            }else if(chkRvwStsCd(wordgrid, "rvwStsCd") > -1) {
	            showMsgBox("INF", "<s:message code="ERR.APPROVE.WORD" />");
	            return false;
            }
				
						
			//반려 선택시 반려사유를 입력하도록 한다.
			var tmprow1 = chkRvwCont(itemgrid, "rvwStsCd", "rvwConts");
			var tmprow2 = chkRvwCont(dmngrid, "rvwStsCd", "rvwConts");
			var tmprow3 = chkRvwCont(wordgrid, "rvwStsCd", "rvwConts");
			
			//그리드 검토내용 작성여부
			if (tmprow1 > 0  || tmprow2 > 0 || tmprow3 > 0) {
				showMsgBox("INF", "<s:message code="ERR.REJECT" />");
				return false;
			}

			//결재처리 총건수, 승인건수, 반려건수를 메세지로 만든다.
			var message = approveCountMessage ();
				message += "<s:message code="CNF.APPR" />";

			dicifmload();
						
			showMsgBox("CNF", message, "Approve");
			//showMsgBox("CNF", message, prcapprdic);	
			
			break;

	}	   
}


//표준사전 각 결재 요청한다.
function prcapprdic() {
	showMsgBox("PRC", "<s:message code="REQ.APPROVE.PRC" />");
	if (retapproveWord != 0) {
		retapproveWord = -1;
 		$('#stndword_ifm')[0].contentWindow.doAction("Approve");
	}
//		alert(retapproveWord);
	if (retapproveDmn != 0) {
		retapproveDmn = -1;
 		$('#stnddmn_ifm')[0].contentWindow.doAction("Approve");
	}
//		alert(retapproveWord);
	if (retapproveItem != 0) {
		retapproveItem = -1;
		$('#stnditem_ifm')[0].contentWindow.doAction("Approve");
	}
//		alert(retapproveWord);
}

//등록요청 및 결재대상 건수를 확인하여 리턴한다.
function approveCountMessage () {
	var itemgrid = $('#stnditem_ifm')[0].contentWindow.grid_sheet;
	var dmngrid  = $('#stnddmn_ifm')[0].contentWindow.grid_sheet;
	var wordgrid = $('#stndword_ifm')[0].contentWindow.grid_sheet;
	
	//결재처리 건수
	var cntitem = itemgrid.RowCount();
	var cntdmn  = dmngrid.RowCount();
	var cntword = wordgrid.RowCount();
	
	//승인/반려 건수
	var cntitemAppr 	= countGridValue(itemgrid, "rvwStsCd", "1");
	var cntitemReject  	= countGridValue(itemgrid, "rvwStsCd", "2");
	var cntdmnAppr  	= countGridValue(dmngrid, "rvwStsCd", "1");
	var cntdmnReject   	= countGridValue(dmngrid, "rvwStsCd", "2");
	var cntwordAppr  	= countGridValue(wordgrid, "rvwStsCd", "1");
	var cntwordReject  	= countGridValue(wordgrid, "rvwStsCd", "2");
	
	
	var message = "";
	if (cntitem > 0) message += "[<s:message code='STRD.TERMS' />:"+cntitem+"<s:message code='CNT.APRV' /> "+cntitemAppr+"<s:message code='CNT.RTN' /> "+cntitemReject+"<s:message code='CNT' />]<br>";
	//표준용어,건 중 승인 ,건, 반려 ,건
	if (cntdmn > 0) message += "[<s:message code='DMN' />:"+cntdmn+"<s:message code='CNT.APRV' /> "+cntdmnAppr+"<s:message code='CNT.RTN' /> "+cntdmnReject+"<s:message code='CNT' />]<br>";
	//도메인,건 중 승인 ,건, 반려 ,건
	if (cntword > 0) message += "[<s:message code='STRD.WORD' />:"+cntword+"<s:message code='CNT.APRV' /> "+cntwordAppr+"<s:message code='CNT.RTN' /> "+cntwordReject+"<s:message code='CNT' />]<br>";
	//표준단어,건 중 승인 ,건, 반려 ,건
	
	return message;
}

//등록요청 및 결재대상 건수를 확인하여 리턴한다.
function requestCountMessage () {
	var itemgrid = $('#stnditem_ifm')[0].contentWindow.grid_sheet;
	var dmngrid  = $('#stnddmn_ifm')[0].contentWindow.grid_sheet;
	var wordgrid = $('#stndword_ifm')[0].contentWindow.grid_sheet;
	
	var cntitem = countGridValue(itemgrid, "vrfCd", "1");
	var cntdmn  = countGridValue(dmngrid, "vrfCd", "1");
	var cntword = countGridValue(wordgrid, "vrfCd", "1");
	
	cntitem += countGridValue(itemgrid, "vrfCd", "3");
	cntdmn  += countGridValue(dmngrid, "vrfCd", "3");
	cntword += countGridValue(wordgrid, "vrfCd", "3");
	
	var message = "";
	if (cntitem > 0) message += "[<s:message code='STRD.TERMS' />:"+cntitem+"<s:message code='CNT' />]"; //표준용어,건 
	if (cntdmn > 0) message += "[<s:message code='DMN' />:"+cntdmn+"<s:message code='CNT' />]"; //도메인,건
	if (cntword > 0) message += "[<s:message code='STRD.WORD' />:"+cntword+"<s:message code='CNT' />]"; //표준단어,건
	
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
		json2formmapping ($('#stnditem_ifm').contents().find("#mstFrm"), mstvo);
		$('#stnditem_ifm').contents().find("#mstFrm #bizDtlCd").val("SDITM");
	}
	if (initdmnifm) {
		json2formmapping ($('#stnddmn_ifm').contents().find("#mstFrm"), mstvo);
		$('#stnddmn_ifm').contents().find("#mstFrm #bizDtlCd").val("DMN");
	}
	if (initwordifm) {
		json2formmapping ($('#stndword_ifm').contents().find("#mstFrm"), mstvo);
		$('#stndword_ifm').contents().find("#mstFrm #bizDtlCd").val("STWD");
	}
	
	return;

	dicifmload();
	testitv = setInterval(function(){
		if (inititemifm&&initdmnifm&&initwordifm) {
			//alert("ok");
			clearInterval(testitv);
			json2formmapping ($('#stndword_ifm').contents().find("#mstFrm"), mstvo);
			json2formmapping ($('#stnddmn_ifm').contents().find("#mstFrm"), mstvo);
			json2formmapping ($('#stnditem_ifm').contents().find("#mstFrm"), mstvo);
		
			$('#stndword_ifm').contents().find("#mstFrm #bizDtlCd").val("STWD");
			$('#stnddmn_ifm').contents().find("#mstFrm #bizDtlCd").val("DMN");
			$('#stnditem_ifm').contents().find("#mstFrm #bizDtlCd").val("SDITM");
			
			
		}
	}, 500);

// 	$('#stnditem_ifm')[0].contentWindow.grid_sheet;
// 	$('#stnddmn_ifm')[0].contentWindow.grid_sheet;
// 	$('#stndword_ifm')[0].contentWindow.grid_sheet
	
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
	    		$("#mstFrm #bizDtlCd").val("STWD");
	    		
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
	    		$("#mstFrm #bizDtlCd").val("STWD");
	    		
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
			var url = '<c:url value="/meta/stnd/stndtot_rqst.do" />';
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
	<div class="menu_title"><s:message code="STRD.DATA.REG"/></div> <!-- 표준데이터등록 -->
<!-- 	<div class="stit">시스템영역 관리</div> -->
	</div>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:5px;"><span></span></div>

<div class="divLstBtn" >	 
	<div class="bt03">
		<div id="stnd_raido_btn" style="display:none;">
			<input type="radio" id="stnditemradio" name="stnddataradio" value="1"><label for="stnditemradio"><s:message code="STRD.TERMS" /></label> <!-- 표준용어 -->
			<input type="radio" id="stnddmnradio" name="stnddataradio"  value="2"><label for="stnddmnradio"><s:message code='DMN' /></label> <!-- 도메인 -->
			<input type="radio" id="stndwordradio" name="stnddataradio" value="3"><label for="stndwordradio"><s:message code='STRD.WORD' /></label> <!-- 표준단어 -->
<!-- 			<input type="hidden" id="clickBtnNm" name="clickBtnNm" value=""/> -->
		</div>
	</div>
	<div class="bt02" id="bt02btn" style="display:none;">
		<button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst" style=""><s:message code="REG.DEMD" /></button> <!-- 등록요청 -->
		<span>
         <button class="btn_req_appr"    id="btnReqApprove" name="btnReqApprove" style=";"><s:message code="APRL.TRTT" /></button> <!-- 결재처리 -->
         <button class="btn_sel_appr"    id="btnSelApprove" name="btnSelApprove" style=";"><s:message code="APRV.RTN.CHC" /></button> <!-- 승인반려선택 -->
        </span>
       	<ul class="appr_button_menu" id="approveButtonMenu" style="">
	   		<li class="btn_all_approve" id="btnAllApprove" ><a><span class="ui-icon ui-icon-check"></span><s:message code="WHL.APRV" /></a></li> <!-- 전체승인 -->
		    <li class="btn_all_reject"  id="btnAllReject" ><a><span class="ui-icon ui-icon-cancel"></span><s:message code="WHL.RTN" /></a></li> <!-- 전체반려 -->
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
		<iframe id="stnditem_ifm" name="stnditem_ifm"  width="100%" height="1000"   frameborder="0" scrolling ="no"></iframe>
		</div>
	</div>
	<div id="tabs-2">
		<div>
		<iframe id="stnddmn_ifm" name="stnddmn_ifm"  width="100%" height="1000"   frameborder="0" scrolling ="no"></iframe>
		</div>
	</div>
	<div id="tabs-3">
		<div>
		<iframe id="stndword_ifm" name="stndword_ifm"  width="100%" height="1000"  frameborder="0" scrolling ="no"></iframe>
		</div>
	</div>
</div>
<div style="clear:both; height:10px;"><span></span></div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>
</body>
</html>