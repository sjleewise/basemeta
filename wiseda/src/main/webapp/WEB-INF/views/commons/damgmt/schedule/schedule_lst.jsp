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
<title><s:message code="SCDU.MNG" /></title> <!-- 스케줄 관리 -->

<script type="text/javascript">
$(document).ready(function() {

	
	//탭 초기화
 	//$( "#tabs" ).tabs();
	$("#btnSubTreeNew").hide();
	//$("#btnTreeNew").hide();
// 	$("#btnJobSave").hide();
	$("#btnSubExcelDown").hide();
	$("#ui-id-2").hide();
	
	//조회
	$("#btnSearch").click(function(){ doAction("Search"); });
	
	//신규추가
	$("#btnNew").click(function(){ doAction("Add");  });
	
	// 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
    $("#btnSchStart").removeAttr('display');
    $("#btnSchStop").removeAttr('display');
    $("#btnImdExec").removeAttr('display');
	$("#btnSchStart").click(function(){ doAction("SchStart"); }).hide();
	$("#btnSchStop").click(function(){ doAction("SchStop"); }).hide();
	$("#btnImdExec").click(function(){ 
		if(checkDelIBS (grid_sheet, "<s:message code='MSG.IMD.SCHEDULE.CHC' />")) { //즉시 실행할 스케줄을 선택하세요.
			showMsgBox("CNF", "<s:message code='CNF.IMD.SCHEDULE' />", "ImdExec"); /* 선택한 스케줄을 즉시 실행 하시겠습니까? */
		} 
	}).hide();

	
	$("#btnSave").click(function(){
		$("#tab-1 a").click();
		//폼 검증
 		if(!$("#frmInput").valid()){
 			return false;
 		}else{
//  			$("#tab-2 a").click();
 	 		if(!$("#frmDtlInput").valid()) return false;
	 		if(grid_sub.RowCount() < 1){
	 			showMsgBox("ERR", "<s:message code='MSG.JOB.INFO.CHC' />"); /* 작업정보를 선택해주세요 */
	 			$("#tab-3 a").click();
				return false;
			}
 		}
 		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');
	//}).show();
	}).hide();
	
	
	//삭제버튼 이벤트 처리
	$("#btnDelete").click(function(){
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
        	showMsgBox("CNF", "<s:message code='MSG.CHC.SCDU.JOB.INFO.DEL' />", "Delete"); /* 선택한 스케줄의 작업 정보도 같이 삭제 됩니다.<br>삭제 하시겠습니까? */
    	}
	});
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #shdLnm"), "SHDLNM");
	
});

$(window).on('load',function(){
	
	//그리드 초기화 
 	initGrid();
	//상세정보 초기화
 	loadDetail();
	loadDtl();	
	
});

$(window).resize(
    function(){
    	//그리드 가로 스크롤 방지
//     	grid_sheet.FitColWidth();
    }
);

EnterkeyProcess("Search");

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [{Text:"<s:message code='COMMON.HEADER.SCHEDULE.JOB.RQST'/>", Align:"Center"}];
      /*   No.|선택|상태|스케줄명(물리명)|스케줄명|스케줄유형코드|스케줄유형|분석차수|분석차수|분석차수자동증가여부|배치형태|시작일자|스케줄사용여부|스케줄일괄처리여부|스케줄설명|시작시간|시작분|로그조회|매월|매월값|매주|매주값|매일|매일값|등록유형코드|스케줄ID|"
		+"오류데이터적재여부|오류데이터적재건수|PK데이터적재여부|PK데이터적재건수| */
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    	Width:40,   SaveName:"ibsSeq",          Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", 	Width:40,   SaveName:"ibsCheck",        Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Status", 	Width:40,   SaveName:"ibsStatus",       Align:"Center", Edit:1, Hidden:1},
                    {Type:"Text", 		Width:100,  SaveName:"shdPnm",  	    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text", 		Width:200,  SaveName:"shdLnm",  	    Align:"Left",   Edit:0, Hidden:0, KeyField:0},
                    {Type:"Combo",   	Width:100,  SaveName:"shdKndCd",        Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   	Width:100,  SaveName:"shdKndNm",        Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   	Width:50,   SaveName:"anaDgr",   		Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"anaDgrNm",   		Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"anaDgrAutoIncYn", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   	Width:50,   SaveName:"shdTypCd", 	    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"shdStrDtm", 	    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:70,   SaveName:"shdUseYn", 	    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"shdBprYn", 	    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"objDescn", 	    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdStrHr", 	    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdStrMnt", 	    Align:"Center", Edit:0, Hidden:1},
        
                    {Type:"Button",   	Width:60,   SaveName:"sButton", 	    	Align:"Center", Edit:1, Hidden:1, HeaderHtml: "<input type='button' value='버튼' />", DefaultValue:"LOG" },
                    {Type:"Text",   	Width:70,   SaveName:"shdMny", 	        Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdMnyVal", 	    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdWkl", 	        Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdWklVal", 	    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdDly", 	        Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdDlyVal", 	    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"regTypCd", 	    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdId", 			Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"erDataLdYn", 		Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"erDataLdCnt", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"pkDataLdYn", 		Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"pkDataLdCnt", 	Align:"Center", Edit:0, Hidden:1}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});
        SetColProperty("shdTypCd", ${codeMap.schdTypeCdibs});
        
        InitComboNoMatchText(1, "");
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function grid_sheet_OnButtonClick(Row,Col) {
	var param = "shdId="+grid_sheet.GetCellValue(Row, "shdId")
	    	  + "&shdLnm="+encodeURIComponent(grid_sheet.GetCellValue(Row, "shdLnm"));
	console.dir(param);
	//로그조회 팝업 오픈
	if ("sButton" == grid_sheet.ColSaveName(Col)) {
// 		var url = '<c:url value="/commons/damgmt/schedule/schedule_log_pop.do" />';
// 		openLayerPop(url, 700, 500, param);

		url = "<c:url value='/commons/damgmt/schedule/schedule_log_pop.do' />";
		popwin = OpenModal(url+"?"+param, "scheduleJopPop",  1200, 800, "no"); 	
		popwin.focus();
	}
}

//상세정보호출
function loadDetail(param) {
	$('div#dtlInfo').load('<c:url value="/commons/damgmt/schedule/ajaxgrid/schedule_dtl.do"/>', param, function(){
// 		$('#btnDtlSave').show();
// 		$('#btnDtlReset').show();
		setTimeout(function(){
			$('#btnDtlSave').show();
			$('#btnDtlReset').show();
		}, 100);
		$('#shdStrHr').show();
		$('#shdStrMnt').show();
		//달력팝업 추가...
		$( "#shdStrDtm" ).datepicker();
		//$('#tabs').show();
	});
}

//배치정보호출
function loadDtl(param) {
	$('div#detailInfo').load('<c:url value="/commons/damgmt/schedule/ajaxgrid/schedule_batch_dtl.do"/>', param, function(){
		//$('#tabs').show();
	});
}

function doAction(sAction)
{
        
    switch(sAction)
    {
	    case "Search":
// 			var param = $("#frmSearch").serialize();
			var param = "shdKndCd="+$("#schdKndCd").val()+"&shdLnm="+$("#frmSearch #shdLnm").val();
// 			param = encodeURL(param);
	    	grid_sheet.DoSearch("<c:url value="/commons/damgmt/schedule/getScheduleList.do" />", param);
	    	break;
	    	
		case 'SearchAllDtl' :	//조회
			var param1 = "shdId="+$("#shdId").val()+"&shdKndCd="+$("#shdKndCd").val();
// 			param1 = encodeURL(param1);
			grid_sub.DoSearch("<c:url value="/commons/damgmt/schedule/getScheduleJobList.do" />", param1);
			break;
	    	
	    case 'Add' : //신규 추가
    		loadDetail();
    		loadDtl();
//     		$("#frmSearch #shdKndCd").val('');
    		$('#schedule_sel_title').html('<s:message code="MSG.SCDU.NM.DTL.INFO.INQ" />'); /* 스케줄명을 클릭하시면 상세정보를 조회합니다. */
    		grid_sub.RemoveAll();
    		break;
	    	
	    case 'Save' :
	    	//작업 중복
	    	var DupRow = grid_sub.ColValueDupRows("shdJobId|shdKndCd|shdJobNm");
			if(DupRow != ""){
				var message = "<s:message code='MSG.SAME.SCDU.JOB.EXIS' />"; /* 동일한 스케줄작업이 존재 합니다. */
    			showMsgBox("INF", message); 
	   			$("#tab-3 a").click();
				return;
			}
   			
   			//배치형태
			var shdTypeCd = $("#shdTypCd").val();
			//매주
	    	var schWeekOm = "";
	    	if(shdTypeCd == "W"){
				schWeekOm = Number("0");
				for(var i=0;i<7;i++){
					if(schdWkl[i].checked){
						schWeekOm += Number(schdWkl[i].value);
					}
				}
	    	}
			//매달
	    	var schMonOm = "";
	    	if(shdTypeCd == "M"){
				schMonOm = Number("0");
				for(var i=0;i<12;i++){
					if(schdMny[i].checked){
						schMonOm += Number(schdMny[i].value);
					}
				}
	    	}
			
	    	//작업그리드 정보
	    	var SaveJson = grid_sub.GetSaveJson(1); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
	    	
// 	    	if(ibsSaveJson.data.length == 0) return;
	    		
		  	var url = "<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>";
		  	var param = $('.frmInput').serialize();
		  	   param += "&shdMny="+schMonOm+"&shdWkl="+schWeekOm;
// 		  	param = encodeURI(param);
	        IBSpostJson2(url, SaveJson, param, ibscallback);
	    	
	    	break;
	    	
	    	
	    case 'Delete' : //삭제
	    	
	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	if(DelJson.data.length == 0) return;
	    	
	    	var url = "<c:url value="/commons/damgmt/schedule/delSchedule.do"/>";
	    	var param = "";
	    	IBSpostJson2(url, DelJson, param, ibscallback);
	    	
			break;
			
		case 'DeleteJob' :
		    	
	    	delCheckIBS(grid_sub);
	    	
	    	var DelJson = grid_sub.GetSaveJson(0, "ibsCheck");
	    	if(DelJson.data.length == 0) return;
	    	
	    	var url = "<c:url value="/commons/damgmt/schedule/delScheduleJob.do"/>";
	    	var param = "";
	    	IBSpostJson2(url, DelJson, param, ibscallback);
	    	
	    	break;
	    	
	    case "Down2Excel":  //엑셀내려받기
	        
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'스케줄관리'});
            
            break;
            
	    case "SchStart":
	    	var startUrl = "<c:url value="/commons/damgmt/schedule/schStart.do"/>";
	    	schFunc(startUrl);
	    	break;
	    case "SchStop":
	    	var stopUrl = "<c:url value="/commons/damgmt/schedule/schStop.do"/>";
	    	schFunc(stopUrl);
	    	break;	    	
	    case "ImdExec":
	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	var imdJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	if(imdJson.data.length == 0) return;
	    	
	    	var url = "<c:url value="/commons/damgmt/schedule/ajaxgrid/insertScheduleImdExec.do"/>";
	    	var param = "scrnDcd=SHDMNG";
	    	IBSpostJson2(url, imdJson, param, ibscallback);
			break;
    }
    
}

function schFunc(url) {
	var param = "";
	
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		async: true,
		data : param,
		success : function(res) {
			console.log(res);
// 			showMsgBox("INF", message);
		},
		error : function(res) {
			
// 			showMsgBox("INF", message);
		}
	});	
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
// 	alert(res.action);
	
	switch(res.action) {
		//삭제 후 처리
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
			break;
		//단건 저장 후 처리
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			$("#frmSearch #shdKndCd").val('');
			doAction("Search");
			break;
		case "<%=WiseMetaConfig.IBSAction.DEL_DTL%>" :
			doAction("SearchAllDtl");
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}


function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	
	$("#tab-1 a").click();
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var shdId = "&shdId="+grid_sheet.GetCellValue(row, "shdId");
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '스케줄명 : ' + param.shdLnm;
	$('#schedule_sel_title').html(tmphtml);
// 	$("#frmSearch #shdKndCd").val(grid_sheet.GetCellValue(row, "shdKndCd"));
	grid_sheet.SetCellValue(row, "ibsStatus", "");
	var schdTypCd = grid_sheet.GetCellValue(row, "shdTypCd");
	$("#Sseq").val(row);
	
	if (param.shdKndCd == "QP"){		//프로파일
		setCodeSelect("prfAnaDgr", "L", $("form[name=frmInput] #anaDgr"));
		$("#Rdegree").show();
		$("#Pk_data").show();
		$("#er_data_layer").show();
	}else if(param.shdKndCd == "QB"){	//업무규칙
		setCodeSelect("brAnaDgr", "L", $("form[name=frmInput] #anaDgr"));
		$("#Rdegree").show();
		$("#Pk_data").hide();
		$("#er_data_layer").show();
	}else if(param.shdKndCd == "CR"){	//검증룰
		setCodeSelect("vrfcAnaDgr", "L", $("form[name=frmInput] #anaDgr"));
		$("#Rdegree").show();
		$("#Pk_data").show();
		$("#er_data_layer").show();
	}else{
		$("#Rdegree").hide();
		$("#Pk_data").hide();
		$("#er_data_layer").hide();
	}
	
	if(param.shdTypCd == "W"){	// 매주
		$("select[name=shdStrHr]").attr("disabled",false);
		$("select[name=shdStrMnt]").attr("disabled",false);
		$("#weekCd").show();
		$("#bCd").hide();
		$("#dayCd").hide();
		$("#monthCd").hide();
	}else if(param.shdTypCd == "D"){		//매일
		$("select[name=shdStrHr]").attr("disabled",false);
		$("select[name=shdStrMnt]").attr("disabled",false);
		$("#dayCd").show();
		$("#weekCd").hide();
		$("#bCd").hide();
		$("#monthCd").hide();
	}else if(param.shdTypCd == "M"){		//매달
		$("select[name=shdStrHr]").attr("disabled",false);
		$("select[name=shdStrMnt]").attr("disabled",false);
		$("#weekCd").hide();
		$("#bCd").hide();
		$("#dayCd").hide();
		$("#monthCd").show();
	}else if(param.shdTypCd == "H"){		//매시
		$("select[name=shdStrHr]").attr("disabled",false);
		$("select[name=shdStrMnt]").attr("disabled",true);
		$("#weekCd").hide();
		$("#bCd").show();
		$("#dayCd").hide();
		$("#monthCd").hide();	
	}else if(param.shdTypCd == "N"){		//매분
		$("select[name=shdStrHr]").attr("disabled",true);
		$("select[name=shdStrMnt]").attr("disabled",false);
		$("#weekCd").hide();
		$("#bCd").show();
		$("#dayCd").hide();
		$("#monthCd").hide();
	}else{
		$("select[name=shdStrHr]").attr("disabled",false);
		$("select[name=shdStrMnt]").attr("disabled",false);
		$("#weekCd").hide();
		$("#bCd").show();
		$("#dayCd").hide();
		$("#monthCd").hide();
	}
	
	
	ibs2formmapping(row, $("form#frmInput"), grid_sheet);
	ibs2formmapping(row, $("form#frmDtlInput"), grid_sheet);
	
	$("#shdStrDtm").val(grid_sheet.GetCellText(row, "shdStrDtm"));
	
	$('input:radio[name=shdDly]:input[value="'+grid_sheet.GetCellValue(row, "shdDly")+'"]').attr('checked',true);
	
// 	if(param.shdKndCd=="QP"){
// 		$("#anaDgr1").val(param.anaDgr);
// 	}else if(param.shdKndCd == "QB"){
// 		$("#anaDgr2").val(param.anaDgr);
// 	}
	

	var checkValue = "";
	var preLen = "";
	var name = "";
	if(schdTypCd == "M"){
		checkValue = grid_sheet.GetCellValue(row, "shdMny");
		preLen = 12;
		name = "schdMny"
	}else if(schdTypCd == "W"){
		checkValue = grid_sheet.GetCellValue(row, "shdWkl");
		preLen = 7;
		name = "schdWkl";
	}
	
	checkArr(preLen, checkValue, name);
	
	$("#ibsStatus").val("U");
	$("#regTypCd").val("U");
	
	var param1 = "shdId="+param.shdId+"&shdKndCd="+param.shdKndCd;
	grid_sub.DoSearch("<c:url value="/commons/damgmt/schedule/getScheduleJobList.do" />", param1);
// 	$("#frmSearch #shdKndCd").val('');
}

function checkArr(preLen, oldStr, name){
	
	var diff = preLen - oldStr.length;
	var filler = "0";

	for(var i=0 ; i < diff ; i++){
	  oldStr = filler + oldStr;
	}
	
	for(var oi=0; oi < preLen ; oi++){
		if(oldStr.charAt(oi) == "1"){
			$("input:checkbox[name="+name+(preLen-oi)+"]").attr("checked", true);			
		}
	}
	
}

function grid_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		doAction('Add');
	}
}
	
</script>

</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="SCDU.MNG" /></div> <!-- 스케줄 관리 -->
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div id="search_div">
	<div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<form id="frmSearch" name="frmSearch" method="post">
		 <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SCDU.INQ' />"> <!-- 스케줄 조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>  
                   	<tr>                        
                         <th scope="row"><label for="shdLnm"><s:message code="SCDU.NM" /></label></th> <!-- 스케줄명 -->
                            <td>
                               <input type="text" name="shdLnm" id="shdLnm" value="${search.shdLnm}"/>
                            </td>

                            <th scope="row"><label for="shdKndCd"><s:message code="SCDU.PTRN" /></label></th> <!-- 스케줄유형 -->
	                             <td>
	                             	<select id="schdKndCd"  name="schdKndCd" class="" >
	                             		<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
										<c:forEach var="code" items="${codeMap.shdKndCd}" varStatus="status">
										<option value="${code.codeCd}">${code.codeLnm}</option>
										</c:forEach>
									</select>
	                             </td> 
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
	<!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
	<div style="clear:both; height:5px;"><span></span></div>
</form>

 <!-- 조회버튼영역  -->         
<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />   
<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>
<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area">
    <div class="selected_title" id="schedule_sel_title"> <span></span></div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1"><a href="#tabs-1"><s:message code="DTL.INFO" /></a></li> <!-- 상세정보 -->
<!-- 	    <li id="tab-2"><a href="#tabs-2">배치정보</a></li> -->
	    <li id="tab-3"><a href="#tabs-3"><s:message code="JOB.INFO" /></a></li> <!-- 작업정보 -->
	  </ul>
	  <div id="tabs-1">
	  		<div id="dtlInfo">
	  		<%@include file="schedule_dtl.jsp" %>
	  		</div>
	  </div>
<!-- 	  <div id="tabs-2"> -->
<!-- 			<div id="detailInfo"></div> -->
<%-- 			<%@include file="schedule_dtl.jsp" %> --%>
<!-- 	  </div> -->
	  <div id="tabs-3">
	  	<%@include file="schedule_job_lst.jsp" %>
	  </div>
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</div>
</body>

</html>