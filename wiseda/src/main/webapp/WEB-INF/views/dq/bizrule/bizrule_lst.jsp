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
<title><s:message code="BZWR.RULE.INQ" /></title><!--업무규칙 조회-->

<script type="text/javascript">
$(document).ready(function() {

	//그리드 초기화 
 	initGrid();
 	
	//탭 초기화
 	//$( "#tabs" ).tabs();
	
	//팝업 버튼
//  	$("button").hide();
	$("#frmDetail button").hide();
	$("#frmDbDetail button").hide();
	$("#frmVrtDetail button").hide();
	
// 	//저장
	$("#btnPrfSave").hide();
// 	//삭제
	$("#btnPrfDelete").hide();
// 	//분석SQL
	$("#btnSqlSearch").hide();
// 	//일괄등록
	$("#btnPrfSchRqst").hide();
	//분석실행
	$("#btnExec").click(function(){
		regScheduler();
	}).show();
	
	
	
	// 조회화면에서 탭 내용 입력창 속성 변환(탭 등록요청이랑 공동 사용)
	//$("form#frmDetail").attr("class", "tb_read").attr("readonly", true);
	//$("form#frmDbDetail").attr("class", "tb_read").attr("readonly", true);
	//$("form#frmVrtDetail").attr("class", "tb_read").attr("readonly", true);
	//$("#frmDetail textarea").attr("class", "b0").attr("readonly", true);
	//$("#frmDbDetail textarea").attr("class", "b0").attr("readonly", true);
	//$("#frmVrtDetail textarea").attr("class", "b0").attr("readonly", true);
	//$("#frmDetail select").attr("disabled", true);
	
	//조회
	$("#btnSearch").click(function(){
		
		$('#frmDetail')[0].reset();
		$('#frmOther')[0].reset();
		$('#bizrule_sel_title').html(null);
		
		doAction("Search");
		
		}).show();
	
	//엑셀다운로드
	$("#btnExcelDown").click(function(){	doAction("Down2Excel");	}).show();
	
	//업무영역명 검색 팝업
	$('.btnBizAreaLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/bizarea/popup/bizarea_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=BIZLNM", "bizAreaPop",  800, 600, "no");
				popwin.focus();
	});
	
	//품질지표명 검색 팝업
	$('.btnDqiLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/dqi/popup/dqi_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=DQILNM", "ctqLstPop",  800, 600, "no");
				popwin.focus();
	});
	
	//중요정보항목명 검색 팝업
	$('.btnCtqLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/ctq/popup/ctq_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=CTQLNM", "ctqPop",  800, 600, "no");
				popwin.focus();
	});
	
	//bizrule_detail.jsp를 등록요청페이지와 공유하므로, 필요없는 부분 hide...
	$("#buttonRqst").hide();
// 	$("#valid_bizrule").hide();
	
	//데이터패턴
	$("#btnPattenSearch").click(function(){ 
		getDataPattern();
	}).show();
	
	//로그조회
	$("#btnLogSearch").click(function(){ 
		 getAnaLog();
	}).show();
	
	//상세화면 READONLY
	$("#frmDetail").addClass("tb_read");
	$("#frmDetail select").attr('disabled', true);
	$("#frmDetail textarea").attr('readOnly', true);
	
	$("#frmDbDetail").addClass("tb_read");
	$("#frmDbDetail select").attr('disabled', true);
	$("#frmDbDetail textarea").attr('readOnly', true);
	
	$("#frmVrtDetail").addClass("tb_read");
	$("#frmVrtDetail select").attr('disabled', true);
	$("#frmVrtDetail textarea").attr('readOnly', true);
	
	$("#otherinfo").show();
	
	//보고서출력
	$("#btnReport").click(function(){
    	
    	doAction("Report");
    	  
    }).show();
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbSchLnm"), "DBSCH");
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	setautoComplete($("#frmSearch #bizAreaLnm"), "BIZLNM");
	setautoComplete($("#frmSearch #dqiLnm"), "DQILNM");
	setautoComplete($("#frmSearch #ctqLnm"), "CTQLNM");
	setautoComplete($("#frmSearch #brNm"), "BRNM");
});

EnterkeyProcess("Search");

$(window).on('load',function() {
	//PK값으로 검색
	var brId ="";
	brId="${search.brId}";
	param ="brId="+brId;
	if(brId != null && brId != "" && brId !="undefined"){
		grid_sheet.DoSearch("<c:url value="/dq/bizrule/getBizruleList.do" />", param);
	}
	
	//dqmain 업무영역별 개선현황 링크
	var linkFlag = "";
	linkFlag = "${linkFlag}";
	if(linkFlag != null && linkFlag != "" && linkFlag != "undefined"){
		if(linkFlag == "1"){
			//doAction("Search");
		}else if(linkFlag == "<s:message code='COM.CD' />"){/*공통코드*/
			$("#frmSearch #bizAreaLnm").val("<s:message code='COM.CD' />");/*공통코드*/
		}else if(linkFlag == "<s:message code='INTERNET'/>"){	/*인터넷*/
			$("#frmSearch #bizAreaLnm").val("<s:message code='INTERNET'/>");/*인터넷*/
		}else if(linkFlag == "<s:message code='ANLY.STAT'/>"){
			$("#frmSearch #bizAreaLnm").val("<s:message code='ANLY.STAT'/>");/*분석통계*/
		}else if(linkFlag == "<s:message code='QLTY.MNG' />"){
			$("#frmSearch #bizAreaLnm").val("<s:message code='QLTY.MNG' />");/*품질관리*/
		}else if(linkFlag == "<s:message code='INTN.OPR'/>"){ /*기관운영*/
 			$("#frmSearch #bizAreaLnm").val("<s:message code='INTN.OPR'/>"); /*기관운영*/
		}else if(linkFlag == "<s:message code='CUST.SUPR'/>"){ /*고객지원*/
			$("#frmSearch #bizAreaLnm").val("<s:message code='CUST.SUPR'/>"); /*고객지원*/
		}else if(linkFlag == "<s:message code='MDLC.SLRY'/>"){ /*의료급여*/
			$("#frmSearch #bizAreaLnm").val("<s:message code='MDLC.SLRY'/>");/*의료급여*/
		}
		doAction("Search");
	}
});



function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.BIZRULE.LST'/>" ,Align:"Center"}
                ];
        
        
                //No.|상태|선택|최근분석차수|분석차수|최근분석일자|업무영역ID|업무영역명|업무규칙ID|업무규칙명|진단대상ID|진단대상명|진단대상논리명|테이블명|컬럼명|품질지표ID|품질지표명|중요정보항목ID|중요정보항목명|사용여부
                //|분석건수|오류건수|오류율(%)|DPMO|SIGMA|업무규칙담당자ID|업무규칙담당자|설명|건수SQL|분석SQL|검증대상명ID|검증대상명|검증테이블명|검증컬럼명|검증비교KEY컬럼|검증비교값컬럼"
                //|건수SQL(검증)|분석SQL(검증)|증JOIN방식|최초요청일시|최초요청자명|요청일시|요청자명|승인일시|승인자명|작업ID|작업명"

        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:1},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text", 		Width:50,   SaveName:"anaDgrDisp",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:60,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:90,  SaveName:"baseDttm",    	Align:"Left", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   	Width:100,  SaveName:"bizAreaId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   	Width:100,  SaveName:"bizAreaLnm",    	Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   	Width:100,  SaveName:"brId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"brNm",   	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgPnm",   	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgLnm",   	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbSchPnm",   	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:90,   SaveName:"dbcTblNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"dbcColNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"dqiId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"dqiLnm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"ctqId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"ctqLnm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:30,   SaveName:"useYn", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",  		Width:40,   SaveName:"goal",        Align:"Right",  Edit:0}, 
                    {Type:"Float", 		Width:60,  	SaveName:"weight",		Align:"Right", 	 Edit:1, Format:"0.00"},
                    
                    {Type:"Int",   	    Width:50,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Int",   	    Width:50,   SaveName:"erCnt", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,   SaveName:"dpmo", 	Align:"Right", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0},
                    
                    {Type:"Text",   	Width:50,   SaveName:"brCrgpUserId", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"brCrgpUserNm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"objDescn", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"cntSql", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"anaSql", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"tgtDbConnTrgId", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"tgtDbConnTrgPnm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"tgtDbcTblNm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"tgtDbcColNm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"tgtKeyDbcColNm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"tgtKeyDbcColVal", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   	Width:70,   SaveName:"tgtVrfJoinCd", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"tgtCntSql", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"tgtAnaSql", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:100,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:100,  SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:1},
					
					{Type:"Text",   Width:100,  SaveName:"shdJobId",    	Align:"Center", Edit:0, Hidden:1, ColMerge:0},
					{Type:"Text",   Width:100,  SaveName:"etcJobNm",    	Align:"Center", Edit:0, Hidden:1, ColMerge:0}
//                     {Type:"Text",   	Width:50,   SaveName:"dbSchLnm", 	Align:"Center", Edit:0, Hidden:1}
                  
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        SetColProperty("tgtVrfJoinCd", ${codeMap.tgtVrfJoinCdibs});
        
        InitComboNoMatchText(1, "");
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//업무규칙 상세 팝업
function brDtlPopup(param){
	
	var objId = $("form[name=frmSearch] #brId").val();
	var anaStrDtm = $("form[name=frmSearch] #baseDttm").val().replace(/ /g, ''); 
	
	if(objId == ""){
		showMsgBox("ERR", "<s:message code='ERR.EMPTY'/>"); /*조회할 데이터를 선택하십시오.*/

		return;
	}
	
 	var param1 = "&objId="+objId;
	     param1 += "&objDate="+anaStrDtm;
	     param1 += "&objIdCol=BR_ID";		  
         param1 += "&objResTbl=WAM_BR_RESULT";
	     param1 += "&objErrTbl=WAM_BR_ERR_DATA";
	     param1 += "&erDataSnoCol=ER_DATA_SNO";
	     
  	var url = '<c:url value="/dq/bizrule/popup/bizrule_dtl_pop.do"/>';

	var popwin = OpenModal(url+"?"+param+param1, "bizruleDtlPop",  1000, 640, "yes"); 
	
	popwin.focus();
}

function doAction(sAction)
{
        
    switch(sAction)
    {
	    case "Search":
	    	$("form[name=frmSearch] #brId").val("");
			var param = $("#frmSearch").serialize();
	    	grid_sheet.DoSearch("<c:url value="/dq/bizrule/getBizruleList.do" />", param);
	    	break;
	    	
	    case "Report": //단일 조회...
        	
	        
        	var saveJson = grid_sheet.GetSaveJson(0);
	    
        	if(saveJson.data.length == 0) {
        		//선택을 체크하세요!
        		showMsgBox("ERR","<s:message code='CHC.CHCR' />");
        		return;
        	}
	    
	        var arrBrId = "";
	        
	        //alert(JSON.stringify(saveJson));
	    	        
	    	$.each(saveJson.data, function(i, data){
	    		
	    		
	    		arrBrId +=  "," + data.brId ;
	    	});
	    	
	    	arrBrId = arrBrId.substring(1);
	    	
	    	arrBrId = arrBrId.replace(",","','");
	    	
	    	
	    	$("#frmSearch #brId").val(arrBrId);
	    	

			//if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/bizrule/prtXlsRptBizrule.do"/>";   
			
			
			
			$("#frmSearch").attr("target","").attr("action",url).submit();  
        	
			break;
	    	
  		case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
    }
}

//데이터패턴 조회
function getDataPattern(){
 	var objId = $("form[name=frmSearch] #brId").val();
	var anaStrDtm = $("form[name=frmSearch] #baseDttm").val().replace(/ /g, ''); 
	
	if(objId == ""){
		showMsgBox("ERR", "<s:message code='INQ.DATA.SEL' />"); /*조회할 데이터를 선택하십시오.*/

		return;
	}
	
 	var param = "?objId="+objId;
	     param += "&objDate="+anaStrDtm;
	     param += "&objIdCol=BR_ID";		  
         param += "&objResTbl=WAM_BR_RESULT";
	     param += "&objErrTbl=WAM_BR_ERR_DATA";
	     param += "&erDataSnoCol=ER_DATA_SNO";
	     param += "&erDataSno="+0;
	
    var url = '<c:url value="/dq/report/popup/datapattern_pop.do" />';
 	var popup = OpenWindow(url+param, "DATA_PATTERN", "800", "600", "yes"); 
}

//로그조회
function getAnaLog(){
	var objId = $("form[name=frmSearch] #brId").val();
	if(objId == ""){
		showMsgBox("ERR", "<s:message code='INQ.DATA.SEL' />"); /*조회할 데이터를 선택하십시오.*/

		return;
	}
	var param = "?objId="+objId;
	 		param += "&objResTbl=WAM_BR_RESULT";
    var url = '<c:url value="/dq/report/popup/analog_pop.do" />';
    var popup = OpenWindow(url+param, "LOG_SEARCH", "800", "600", "yes");
}


//실시간실행 스케줄 등록
function regScheduler(){
	var ibsSaveJson = grid_sheet.GetSaveJson(0);
	if(ibsSaveJson.data.length == 0){
		showMsgBox("ERR", "<s:message code='ANLY.DATA.CHC' />"); /*분석할 데이터를 선택하십시오*/

		return;
	}
	
	var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
	var param = "&shdKndCd=QB"; //프로파일
    IBSpostJson2(urls, ibsSaveJson, param, schedulerCallBack);
}

function schedulerCallBack(data){
	//스케줄등록 오류
	if(data.RESULT.CODE < 0){
		showMsgBox("ERR", data.RESULT.MESSAGE); 
	}else{
		showMsgBox("INF", data.RESULT.MESSAGE); 
	}
}


function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	var param = "brId="+grid_sheet.GetRowJson(row).brId;
		
	brDtlPopup(param);	
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="BZWR.RGR.NM"/>' + ' : ' + param.brNm;  //업무규칙명
	$('#bizrule_sel_title').html(tmphtml);
	
	ibs2formmapping(row, $("form#frmDetail"), grid_sheet);
	ibs2formmapping(row, $("form#frmDbDetail"), grid_sheet);
	ibs2formmapping(row, $("form#frmVrtDetail"), grid_sheet);
	ibs2formmapping(row, $("form#frmOther"), grid_sheet);
	$("#frsRqstDtm").val(grid_sheet.GetCellText(row, "frsRqstDtm"));
	$("#rqstDtm").val(grid_sheet.GetCellText(row, "rqstDtm"));
	$("#aprvDtm").val(grid_sheet.GetCellText(row, "aprvDtm"));
	
// 	var objDescn = grid_sheet.GetCellValue(row, "objDescn");
// 	var result = objDescn.replaceAll("\\\\\\r\\\\\\n", "<br>");
// 	$("#objDescn").val(result);
	//분석일시 정보를 위한 선택된 row 번호 저장
	$("#frmSearch #baseDttm").val(grid_sheet.GetCellValue(row, "baseDttm"));
	$("#frmSearch #brId").val(grid_sheet.GetCellValue(row, "brId"));
	
	var brId = "&brId="+grid_sheet.GetCellValue(row, "brId");
	//이력조회
	getBizRuleHstLst(brId);
}

//이력조회
function getBizRuleHstLst(param) {
	
	grid_hst.DoSearch("<c:url value="/dq/bizrule/getBizRuleHstLst.do" />", param);
}

function  grid_sheet_OnSearchEnd (code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	}
}

</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="BZWR.RULE.INQ" />	<!--업무규칙 조회--></div>
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div id="search_div">
	<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
	<form id="frmSearch" name="frmSearch" method="post">
		<input type="hidden" id="bizAreaId" name="bizAreaId"/>
		<input type="hidden" id="dqiId" name="dqiId"/>
		<input type="hidden" id="ctqId" name="ctqId"/>
		<input type="hidden" id="brId" name="brId"/>
		<input type="hidden" id="baseDttm" name="baseDttm"/>
		 <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code="BZWR.RULE.INQ" />	<!--업무규칙 조회-->">
                   <caption><s:message code="BZWR.RULE.INQ" />	<!--업무규칙 조회--></caption>
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                  </colgroup>
                   
                   <tbody>  
                   	<tr>                        
                    	<th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->


                        	<td>
                        	 <select id="dbConnTrgId"  name="dbConnTrgId">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
							</select>
                            </td>
                        <th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="dbcTblNm" name="dbcTblNm" />
	                        </td> 
                        <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="dbcColNm" name="dbcColNm" />
	                        </td> 
                   </tr>
                   	<tr>                        
                    	<th scope="row"><label for="bizAreaLnm"><s:message code="BZWR.TRRT.NM" /></label></th><!-- 업무영역명 -->
                        	<td>
                        	<input class="wd60p" type="text" id="bizAreaLnm" name="bizAreaLnm" />
                        	<button class="btnBizAreaLnmPop" id="btnBizAreaLnmPop"><s:message code="INQ" /></button><!--검색-->
                            </td>
                        <th scope="row"><label for="dqiLnm"><s:message code="QLTY.INDC.NM"/></label></th><!--품질지표명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="dqiLnm" name="dqiLnm" />
	                    	<button class="btnDqiLnmPop" id="btnDqiLnmPop"><s:message code="INQ" /></button><!--검색-->
	                        </td> 
                        <th scope="row"><label for="ctqLnm"><s:message code="IMCE.INFO.ITEM.NM"/></label></th><!--중요정보항목명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="ctqLnm" name="ctqLnm" />
	                    	<button class="btnCtqLnmPop" id="btnCtqLnmPop"><s:message code="INQ" /></button><!--검색-->
	                        </td> 
                   </tr>
                   	<tr>                        
                    	<th scope="row"><label for="brNm"><s:message code="BZWR.RGR.NM"/></label></th><!--업무규칙명-->

                        	<td >
                        	<input class="wd60p" type="text" id="brNm" name="brNm" />
                            </td>
                        <th scope="row"><label for="anaDgr"><s:message code="ANLY.ODR" /></label></th><!--분석차수-->

	                    	<td>
	                    	    <select id="anaDgr"  name="anaDgr">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.brAnaDgrCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
	                        </td> 
                        <th scope="row"><label for="erYn"><s:message code="EROR.XN"/></label></th><!--오류유무-->

	                    	<td>
	                    	<select id="erYn" name="erYn">
	                    		<option value=""><s:message code="WHL" /></option><!--전체-->

	                    		<option value="Y">Y</option>
	                    		<option value="N">N</option>
	                    	</select>
	                        </td> 
	                        <%--  <th scope="row"><label for="tgtVrfJoinCd"><s:message code="COMPARE.VRFC"/></label></th><!--비교검증--> --%>

<!-- 	                    	<td> -->
<%-- 	                    	    <select id="tgtVrfJoinCd"  name="tgtVrfJoinCd"> --%>
<%-- 								    <option value=""><s:message code="WHL" /></option><!--전체--> --%>

<!-- 								    <option value="Y">Y</option> -->
<!-- 								    <option value="N">N</option> -->
<%-- 								</select> --%>
<!-- 	                        </td>  -->
                     </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" />
 <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div><!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!--를 사용하시면 됩니다.-->
</form>

<!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<div style="clear:both; height:5px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonProfile.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>
<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area">
    <div class="selected_title" id="bizrule_sel_title"> <span></span></div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1"><a href="#tabs-1"><s:message code="BSIC.INFO"/></a></li><!--기본정보-->
	    <li id="tab-2"><a href="#tabs-2"><s:message code="DIAG.INFO"/></a></li><!--진단정보-->
	    <li id="tab-4"><a href="#tabs-4"><s:message code="CHG.HSTR" /></a></li><!--변경이력-->
	  </ul>
	  <div id="tabs-1">
	  			<%@include file="bizrule_detail.jsp" %>
	  	</div>
	  <div id="tabs-2">
	  			<%@include file="bizrule_dbDetail.jsp" %>
	  	</div>
	  <div id="tabs-4">
	  			<%@include file="bizrule_hst_lst.jsp" %>
	  	</div>
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
	<!-- 비교검증정보(COMPARE.VRFC.INFO, tabs-3) bizrule_vrtDetail.jsp -->
</div>
</body>

</html>