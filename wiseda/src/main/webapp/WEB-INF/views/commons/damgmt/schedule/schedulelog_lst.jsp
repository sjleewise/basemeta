<!DOCTYPE html>
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
<title><s:message code="SCDU.LOG.INQ" /></title> <!-- 스케줄로그 조회 -->

<script type="text/javascript">

var regtypcd = ${codeMap.regtypcd} ;
// var shdkndcd = ${codeMap.shdkndcd} ;
var shdtypcd = ${codeMap.shdtypcd} ;

$(document).ready(function() {
	//그리드 초기화 
 	initGrid();
	
	 $("#btnTreeNew").hide();
	 $("#btnDelete").hide();
	
	//탭 초기화....
	//$( "#tabs" ).tabs();
	
	 // 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("Search");  });
	 
    $("#btnDelete").click(function(){
    	
    	//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
    	}
//     	doAction("Delete");
    } ).show();
    
	$("#btnImdExec").click(function(){ 
		if(checkDelIBS (grid_sheet, "<s:message code="MSG.RE.SCHEDULE.CHC" />")) { /* 재실행할 스케줄을 선택하세요. */
			showMsgBox("CNF", "<s:message code="CNF.RE.SCHEDULE" />", "ImdExec"); /* 선택한 스케줄을 재실행 하시겠습니까? */
		} 
	}).text("<s:message code="RE.EXCT" />").hide(); /* 재실행 */
	 
    $("#btnExcelDown").click(function(){ 
    	grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'스케줄로그조회'});
  	});
    
	 
    create_selectbox(regtypcd, $("#regTypCd"));
//     create_selectbox(shdkndcd, $("#shdKndCd"));
    create_selectbox(shdtypcd, $("#shdTypCd"));
    
    loadDetailShd();
    
    $("#btnJobStop").removeAttr('display');
	$("#btnJobStop").click(function(){ doAction("JobStop"); }).hide();
	
});

$(window).on('load',function() {
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	//기간 버튼 클릭 체크
 	$(".bd_none a").click(function(){
		var btna = $(".bd_none a"); 
		var idx = btna.index(this);
		btna.removeClass('tb_bt_select').addClass('tb_bt');
		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

		//alert(idx);
		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
		
	}); 

	//당일 
 	setBetweenDtm( 0, $( "#searchBgnDe" ), $( "#searchEndDe" ));
		
// 	$("#divDtlBtn").hide();
	$('#btnDtlSave').hide();
// 	$('#btnDtlReset').hide();
// 	$( "#shdStrDtm" ).hide();
	 
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #shdLnm"), "SHDLNM");
	setautoComplete($("#frmSearch #anaUserNm"), "USRNM");
	setautoComplete($("#frmSearch #objNm"), "OBJNM");
	
});

$(window).resize(
    function(){
    	//그리드 가로 스크롤 방지
    	grid_sheet.FitColWidth();
    }
);

EnterkeyProcess("Search");

function initGrid()
{
    
    with(grid_sheet){
    	
    
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.SCHEDULELOG.LST1'/>" /* No.|선택|상태|스케줄Id|스케줄명|스케줄시작일시|시작일시|종료일시|진행시간(초)|총건수|성공건수|실패건수|스케줄유형|실행자| */
                    		+"<s:message code='COMMON.HEADER.SCHEDULELOG.LST2'/>" 
                    		/* 버전|등록 유형|스케줄명(물리명)|스케줄 사용여부|스케줄 일괄처리여부|오류데이터 적재여부|오류데이터 적재건수| */
                    		+"<s:message code='COMMON.HEADER.SCHEDULELOG.LST3'/>" 
                    		/* PK데이터 적재여부|PK데이터 적재건수|분석차수|분석차수 자동증가여부|배치형태|설명| */
                    		+"<s:message code='COMMON.HEADER.SCHEDULELOG.LST4'/>", Align:"Center"} 
                    		/* 최초요청자|최초요청일시|요청자|요청일시|승인자|승인일시|스케줄작업ID */ 
                ];
        //종료시간이 테이블에 없음
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    	Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"CheckBox", 	Width:40,   SaveName:"ibsCheck",   Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:1},
                    {Type:"Text", 		Width:130,   SaveName:"shdId",  Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:250,   SaveName:"shdLnm",  Align:"Left", Edit:0},
                    {Type:"Date", 		Width:150,   SaveName:"shdStrDtm",  Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date", 		Width:150,   SaveName:"anaStrDtm",  Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date", 		Width:150,   SaveName:"anaEndDtm",  Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Int", 		Width:80,   SaveName:"anaSec",  Align:"Center", Edit:0},
                    {Type:"Int", 		Width:80,   SaveName:"jobCnt",  Align:"Right", Edit:0},
                    {Type:"Int", 		Width:80,   SaveName:"sucCnt",  Align:"Right", Edit:0},
                    {Type:"Int", 		Width:80,   SaveName:"erCnt",  Align:"Right", Edit:0},
                    {Type:"Combo", 		Width:100,   SaveName:"shdKndCd",  Align:"Center", Edit:0},
                    {Type:"Text", 		Width:100,   SaveName:"userNm",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:40,   SaveName:"objVers",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", 		Width:40,   SaveName:"regTypCd",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:100,   SaveName:"shdPnm",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", 		Width:100,   SaveName:"shdUseYn",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", 		Width:100,   SaveName:"shdBprYn",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", 		Width:100,   SaveName:"erDataLdYn",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:100,   SaveName:"erDataLdCnt",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", 		Width:100,   SaveName:"pkDataLdYn",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:100,   SaveName:"pkDataLdCnt",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:100,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", 		Width:100,   SaveName:"anaDgrAutoIncYn",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", 		Width:100,   SaveName:"shdTypCd",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:100,   SaveName:"objDescn",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:100,   SaveName:"frsRqstUserNm",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date", 		Width:100,   SaveName:"frsRqstDtm",  Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text", 		Width:100,   SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date", 		Width:100,   SaveName:"rqstDtm",  Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text", 		Width:100,   SaveName:"aprvUserNm",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date", 		Width:100,   SaveName:"aprvDtm",  Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   	Width:70,   SaveName:"shdJobId", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo", 		Width:100,   SaveName:"prfKndCd",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdStrHr", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdStrMnt", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdMny", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdMnyVal", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdWkl", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdWklVal", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdDly", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"shdDlyVal", 	Align:"Center", Edit:0, Hidden:1}
                ];
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});
        SetColProperty("anaStsCd", ${codeMap.anaStsCdibs});
        SetColProperty("regTypCd", ${codeMap.regTypCdibs});
        SetColProperty("shdTypCd", ${codeMap.schdTypeCdibs});
        SetColProperty("shdUseYn", {ComboCode:"N|Y", 	ComboText:"|<s:message code='USE' />"}); /* 사용 */
        SetColProperty("shdBprYn", {ComboCode:"N|Y", 	ComboText:"|<s:message code='BNDL.TRTT' />"}); /* 일괄처리 */
        SetColProperty("erDataLdYn", {ComboCode:"N|Y", 	ComboText:"|<s:message code='EROR.DATA.LOAD' />"}); /* 오류데이터 적재 */
        SetColProperty("pkDataLdYn", {ComboCode:"N|Y", 	ComboText:"|<s:message code='PK.DATA.LOAD' />"}); /* PK데이터 적재 */
        SetColProperty("anaDgrAutoIncYn", {ComboCode:"N|Y", 	ComboText:"|<s:message code='ATMD.INC' />"}); /* 자동증가 */
        
//         InitComboNoMatchText(1, "");
      
//         FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function doAction(sAction)
{
        
    switch(sAction)
    {
    		case "Search":
    			
    			joblst_sheet.RemoveAll();
    			frmInput.reset();
    			frmDtlInput.reset();
    			var param = $("#frmSearch").serialize();
//     			alert(param);
    	    	grid_sheet.DoSearch("<c:url value="/commons/damgmt/schedule/getScheduleLogList.do" />", param);
    			break;
    		
    		case "Delete" :
            	
            	var DelJson = grid_sheet.GetSaveJson(0,"ibsCheck");
            	if(DelJson.data.length == 0) return;
            	
            	var url = '<c:url value="/commons/damgmt/schedule/deleteScheduleLogList.do"/>';
            	var param = $("#frmSearch").serialize();
				
            	IBSpostJson2(url, DelJson, param, ibscallback);
            	break;
           
        	case "ImdExec":
        		//체크된 행 중에 입력상태인 경우 시트에서 제거...
        		var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        		if(DelJson.data.length == 0) return;
        		
        		var url = "<c:url value="/commons/damgmt/schedule/ajaxgrid/insertScheduleImdExec.do"/>";
        		var param = "scrnDcd=SHDLOG";
        		IBSpostJson2(url, DelJson, param, ibscallback);
        		
        		break;

        	case "JobStop" :
                	
               	var DelJson = grid_sheet.GetSaveJson(0,"ibsCheck");
               	if(DelJson.data.length == 0) return;
//                	for(var i=0; i < DelJson.data.length; i++){
//                		if(DelJson.data[i].anaEndDtm != ""){
//                			showMsgBox("ERR","이미 종료된 스케줄은 정지할 수 없습니다.");
//                			return;
//                		}
//                	}

               	var url = '<c:url value="/commons/damgmt/schedule/ajaxgrid/stopSchedule.do"/>';
               	var param = "";
    			
               	IBSpostJson2(url, DelJson, param, ibscallback);
               	break;        		
    }
    
}


//상세정보호출(컬럼목록에서 선택시)
function loadDetailShd(param) {
	$('div#dtlInfo').load('<c:url value="/commons/damgmt/schedule/ajaxgrid/schedule_dtl.do"/>', param, function(){
// 		$("#frmInput input[type=checkbox]").css("border-color","transparent").attr("readonly","true");
// 		$("#frmDtlInput input[type=text]").css("border-color","transparent").attr("readonly","true");
		$(".input").attr("disabled", "true");
		$(".input_check").attr("disabled", "true");
		$(".dtlInput").attr("disabled", "true");
		//상세화면 READONLY
		$("#frmInput").addClass("tb_read");
		$("#frmDtlInput").addClass("tb_read");
		$("#frmInput input[type=text]").attr("readonly", true);
		$("#frmInput textarea").attr("class", "b0");
	});
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
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
// 		grid_sheet_OnClick(1);
	}
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	
	if(isBlankStr("${shdLnm}")){
		$("#tab-1 a").click();
	}else{
		$("#tab-2 a").click();
	}
	
	var param =  grid_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="SCDU.NM" /> : ' + param.shdLnm ; /* 스케줄명 */
	$('#schedule_sel_title').html(tmphtml);
	
	$("#jobfrmSearch #shdId").val(grid_sheet.GetCellValue(row, "shdId"));
	$("#jobfrmSearch #shdStrDtm").val(grid_sheet.GetCellValue(row, "shdStrDtm"));
	$("#jobfrmSearch #shdKndCd").val(grid_sheet.GetCellValue(row, "shdKndCd"));
	$("#jobfrmSearch #prfKndCd").val(grid_sheet.GetCellValue(row, "prfKndCd"));
	$("#jobfrmSearch #anaDgr").val(grid_sheet.GetCellValue(row, "anaDgr"));
	$("#jobfrmSearch #shdJobId").val(grid_sheet.GetCellValue(row, "shdJobId"));
	
	var schdTypCd = grid_sheet.GetCellValue(row, "shdTypCd");
	
	if (param.shdKndCd == "QP"){		//프로파일
		setCodeSelect("prfAnaDgr", "L", $("form[name=frmInput] #anaDgr"));
		$("#Rdegree").show();
		$("#Pk_data").show();
	}else if(param.shdKndCd == "QB"){	//업무규칙
		setCodeSelect("brAnaDgr", "L", $("form[name=frmInput] #anaDgr"));
		$("#Rdegree").show();
		$("#Pk_data").hide();
	}else{
		$("#Rdegree").hide();
		$("#Pk_data").hide();
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
	
// 	if(param.shdKndCd=="QP"){
// 		$("#anaDgr1").val(param.anaDgr);
// 	}else if(param.shdKndCd == "QB"){
// 		$("#anaDgr2").val(param.anaDgr);
// 	}
	
	$('input:radio[name=shdDly]:input[value="'+grid_sheet.GetCellValue(row, "shdDly")+'"]').attr('checked',true);
	
	var param = $('#jobfrmSearch').serialize();
	
	joblst_sheet.DoSearch("<c:url value="/commons/damgmt/schedule/getshdjoblst.do" />", param);
	
	//스케줄 상세정보 조회
// 	loadDetailShd(param);
	
}
</script>

</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="SCDU.LOG.INQ" /></div> <!-- 스케줄로그 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="스케줄 조회">
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

                         <th scope="row"><label for="anaUserNm"><s:message code="EXCT.USER" /></label></th> <!-- 실행자 -->
                            <td>
                               <input type="text" name="anaUserNm" id="anaUserNm" value="${search.anaUserId}"/>
                            </td>
                    </tr>
                    <tr>    
                    
<%--                    		 <th scope="row"><label for="objNm"><s:message code="JOB.NM2" /></label></th> <!-- 작업명 -->
                            <td>
                               <input type="text" name="objNm" id="objNm" value="${search.objNm}"/> 
                            </td> --%>

							 <th scope="row"><label for="anaStsCd"><s:message code="JOB.STS" /></label></th> <!-- 작업상태 -->
	                             <td>
	                             	<select id="anaStsCd"  name="anaStsCd" class="" >
	                             		<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
										<c:forEach var="code" items="${codeMap.anaStsCd}" varStatus="status">
										<option value="${code.codeCd}">${code.codeLnm}</option>
										</c:forEach>
									</select>
	                             </td> 
                            <th scope="row"><label for="shdKndCd"><s:message code="SCDU.PTRN" /></label></th> <!-- 스케줄유형 -->
	                             <td>
	                             	<select id="shdKndCd"  name="shdKndCd" class="" >
	                             		<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
										<c:forEach var="code" items="${codeMap.shdKndCd}" varStatus="status">
										<option value="${code.codeCd}">${code.codeLnm}</option>
										</c:forEach>
									</select>
	                             </td> 
	                      </tr>
                                         <tr>
                                         <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                                         <td class="bd_none">
								            <a href="#" class="tb_bt">1<s:message code="DD" /></a> <!-- 일 -->
						                    <a href="#" class="tb_bt">3<s:message code="DD" /></a> <!-- 일 -->
						                    <a href="#" class="tb_bt">7<s:message code="DD" /></a> <!-- 일 -->
						                    <a href="#" class="tb_bt">1<s:message code="MN" /></a> <!-- 개월 -->
						                    <a href="#" class="tb_bt">3<s:message code="MN" /></a> <!-- 개월 -->
						                    <a href="#" class="tb_bt">6<s:message code="MN" /></a> <!-- 개월 -->
                                         </td>
                                         <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" readonly>  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}" readonly>
  						    </tr> 
                   </tbody>
                 </table>   
            </div>
            </fieldset>
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
</form>

 <!-- 조회버튼영역  -->         
<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
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
	    <li id="tab-1"><a href="#tabs-1"><s:message code="SCDU.DTL.INFO" /></a></li> <!-- 스케줄 상세정보 -->
	    <li id="tab-2"><a href="#tabs-2"><s:message code="JOB.LST" /></a></li> <!-- 작업목록 -->
	  </ul>
	  
	   <div id="tabs-1">
			<div id="dtlInfo"></div>
	  </div>
	  
	  <div id="tabs-2">
	  		<div id="dtlJobLst">
	  			<%@include file="shedulejoblst_dtl.jsp" %>
	  		</div>
	  </div>
	  
	 
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</div>
</body>

</html>