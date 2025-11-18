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
<title><s:message code="IMPV.PLAN.INQ" /></title><!--개선계획 조회-->

<script type="text/javascript">
$(document).ready(function() {

	//업무영역명 검색 팝업
	$('.btnBizAreaLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/bizarea/popup/bizarea_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=BIZLNM", "bizAreaPop",  800, 600, "no");
				popwin.focus();
	}); //0421 ) 삭제 ??
	
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
	
	
	//탭 초기화
 	//$( "#tabs" ).tabs();
	$("#btnTreeNew").hide();
	$("#btnDelete").hide();
	$("#valid_csana").hide();
	
	//조회
	$("#btnSearch").click(function(){ doAction("Search"); });
	
	
	//엑셀다운로드
	$("#btnExcelDown").click(function(){	doAction("Down2Excel");	});
	
	//조회화면의 요청상세정보 show...
	$("#otherinfo").show();
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	setautoComplete($("#frmSearch #bizAreaLnm"), "BIZLNM");
	setautoComplete($("#frmSearch #dqiLnm"), "DQILNM");
	setautoComplete($("#frmSearch #ctqLnm"), "CTQLNM");
	setautoComplete($("#frmSearch #brNm"), "BRNM");
	
	
});



$(window).on('load',function() {
	initGrid();
	//원인분석상세, 개선계획상세를 읽기전용으로 바꾼다...
	$("#valid_imrsl").hide();
	$("#divFrmBtnIm").hide();
	$("#divFrmBtnCs").hide();
	$("#csAnaDtls").attr("readOnly", true);
	$("#imPlDtls").attr("readOnly", true);

	//PK값으로 검색
	var anaJobId ="";
	var anaStrDtm="";
		anaJobId="${search.anaJobId}";
		anaStrDtmLink="${search.anaStrDtmLink}";
		param ="anaJobId="+anaJobId;
		param+="&anaStrDtmLink="+anaStrDtmLink;
		if((anaJobId != null && anaJobId != "" && anaJobId !="undefined")|| (anaStrDtmLink != null && anaStrDtmLink != "" && anaStrDtmLink !="undefined")){
			grid_sheet.DoSearch("<c:url value="/dq/impv/getCanImRsl.do" />", param);	
		}
		
});

EnterkeyProcess("Search");

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.IMPL.LST'/>",Align:"Center"}
                ];
        //No.|상태|최근분석차수|분석차수|최근분석일자|업무영역명|업무규칙ID|업무규칙명|진단대상ID|진단대상명|테이블명|컬럼명|품질지표ID|품질지표명|중요정보항목ID|중요정보항목명|사용여부|분석건수|오류건수|"+"오류율(%)|DPMO|SIGMA|원인분석코드|원인분석코드명|원인분석시작일자|원인분석종료일자|원인분석내역|원인분석불가코드|원인분석불가코드명|개선계획코드|걔선계획코드명|개선계획시작일자|개선계획종료일자|개선계획내역|분석구분코드|

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    	Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Status", 	Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:1, Hidden:1},
                    {Type:"Text", 		Width:50,   SaveName:"anaDgrDisp",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text", 		Width:50,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,  SaveName:"anaStrDtm",    	Align:"Left", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   	Width:80,  SaveName:"bizAreaLnm",    	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:80,  SaveName:"anaJobId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:80,  SaveName:"anaJobNm",   	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"dbcTblNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:60,   SaveName:"dbcColNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"dqiId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"dqiLnm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:70,   SaveName:"ctqId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:70,   SaveName:"ctqLnm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:30,   SaveName:"useYn", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:40,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:40,   SaveName:"erCnt", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:40,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:40,   SaveName:"dpmo", 	Align:"Right", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:40,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0},
                    {Type:"Combo",   	Width:40,   SaveName:"prgsStat", 	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaCd", 	Align:"Center", Edit:0, Hidden:1, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaCdNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaStrDtm", 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaEndDtm", 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaDtls", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaIlCd", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"csAnaIlCdNm", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,   SaveName:"imPlCd", 	Align:"Center", Edit:0, Hidden:1, KeyField:1},
                    {Type:"Text",   	Width:100,   SaveName:"imPlCdNm", 	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:100,   SaveName:"imPlStrDtm", 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:100,   SaveName:"imPlEndDtm", 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   	Width:100,   SaveName:"imPlDtls", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:40,   SaveName:"anaKndCd", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:100,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:100,  SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:1}
                    
                    
                  
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
//         SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});
        SetColProperty("prgsStat", {ComboText: "지연|진행중|완료", ComboCode: "01|02|03"});  
        InitComboNoMatchText(1, "");
      
        FitColWidth();
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
			
   			var param = $("#frmSearch").serialize();
   	    	grid_sheet.DoSearch("<c:url value="/dq/impv/getCanImRsl.do" />", param);		
   			break;

	    case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'개선계획조회'});
            break;
    }
}

//이력조회
function getImplHstLst(param) {
	
	console.log(param);
	
	grid_hst.DoSearch("<c:url value="/dq/impv/getImplHstLst.do" />", param);
}


//업무규칙 상세 팝업
function brDtlPopup(row){
	
	var param = "brId="+grid_sheet.GetRowJson(row).anaJobId;
	var objId =grid_sheet.GetRowJson(row).anaJobId;
	var anaStrDtm = grid_sheet.GetRowJson(row).anaStrDtm.replace(/ /g, ''); 
	
	if(objId == ""){
		showMsgBox("ERR", "<s:message code='ERR.EMPTY' arguments="<s:message code='INQ.DATA.SEL'/>"/>");/*조회할 데이터를 선택하십시오.*/

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

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	//선택한 상세정보를 가져온다...
	
	//그리드시트를 읽기전용으로 하려 했으나, window.load나 document.ready에서는 먹히질 않음...
	//별수 없이 클릭시 edit:1로 변경, 로직 완료시 edit:0으로 변경하게 함.
 	grid_sub_imPlCd.SetColProperty("ibsCheck",  {Edit:1});
 	grid_sub_csAnaCd.SetColProperty("ibsCheck",  {Edit:1});
 	grid_sub_csAnaIlCd.SetColProperty("ibsCheck",  {Edit:1});
	
	$('#selectRow').val(row);
	
	var param =  grid_sheet.GetRowJson(row);
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="BZWR.RGR.NM"/>'+' : ' + param.anaJobNm; //업무규칙명
	$('#bizrule_sel_title').html(tmphtml);
	
	//기존 체크내역은 모두 해제한다.
	grid_sub_csAnaCd.CheckAll(0, 0);
	grid_sub_csAnaIlCd.CheckAll(0, 0);
	$("select[name=rqstDcd]").val("");
	$("input[name=csAnaStrDtm]").val("");
	$("input[name=csAnaEndDtm]").val("");
	$("textarea[name=csAnaDtls]").val("");
	grid_sub_imPlCd.CheckAll(0, 0);
	$("input[name=imPlStrDtm]").val("");
	$("input[name=imPlEndDtm]").val("");
	$("textarea[name=imPlDtls]").val("");
	
	//원인분석유형이 원인분석 불가일경우/아닐경우 매핑처리...
	if (grid_sheet.GetCellValue(row, "csAnaCd") == "") {
		
	} else if(grid_sheet.GetCellValue(row, "csAnaCd") == "09") {
		$("#csAnaDtlsGrid").show();
		$("#csAnaDtlsText").hide();
				
		grid_sub_csAnaCd.SetCellValue(grid_sub_csAnaCd.FindText("codeLnm", "<s:message code='CAUS.ANLY.IPSB'/>", 0), "ibsCheck", "1"); /*원인분석불가*/
		$("select[name=rqstDcd]").val(grid_sheet.GetCellValue(row, "rqstDcd"));
		var csAnaDtls = grid_sheet.GetCellValue(row, "csAnaIlCdNm");
		var dtlsStr = csAnaDtls.split(", ");
		
		csAnaIlCd_sheet_init(dtlsStr);
		
		for (var i=0; i<dtlsStr.length; i++){
			grid_sub_csAnaIlCd.SetCellValue(grid_sub_csAnaIlCd.FindText("codeLnm", dtlsStr[i], 0), "ibsCheck", "1");
		}
		for (var i=0; i<dtlsStr.length; i++){
			grid_sub_csAnaIlCd.SetCellValue(grid_sub_csAnaIlCd.FindText("codeLnm", dtlsStr[i], 0), "ibsCheck", "1");
		}
	} else { //원인분석유형이 다양할경우...
		$("#csAnaDtlsGrid").hide();
		$("#csAnaDtlsText").show();
		
		var csAnaCd = grid_sheet.GetCellValue(row, "csAnaCd");
		var cdsStr = csAnaCd.split(", ");
		for (var i=0; i<cdsStr.length; i++){
			grid_sub_csAnaCd.SetCellValue(grid_sub_csAnaCd.FindText("codeCd", cdsStr[i], 0), "ibsCheck", "1");
		}
		$("select[name=rqstDcd]").val(grid_sheet.GetCellValue(row, "rqstDcd"));
		$("textarea[name=csAnaDtls]").val(grid_sheet.GetCellValue(row, "csAnaDtls"));
		
	}

	
	if (grid_sheet.GetCellValue(row, "imPlCd") == "") {
		
	} else { 
		
		var imPlCd = grid_sheet.GetCellValue(row, "imPlCd");
		var cdsStr = imPlCd.split(", ");
		for (var i=0; i<cdsStr.length; i++){
			grid_sub_imPlCd.SetCellValue(grid_sub_imPlCd.FindText("codeCd", cdsStr[i], 0), "ibsCheck", "1");
		}
		$("textarea[name=imPlDtls]").val(grid_sheet.GetCellValue(row, "imPlDtls"));

	}
	
	ibs2formmapping(row, $("form#frmOther"), grid_sheet);
	$("#frsRqstDtm").val(grid_sheet.GetCellText(row, "frsRqstDtm"));
	$("#rqstDtm").val(grid_sheet.GetCellText(row, "rqstDtm"));
	$("#aprvDtm").val(grid_sheet.GetCellText(row, "aprvDtm"));
	
	
	//날짜가 Text형태이므로, substr을 이용해 포맷을 변환하여 나타낸다.
	convertDate();
	
	grid_sub_imPlCd.SetColProperty("ibsCheck",  {Edit:0});
 	grid_sub_csAnaCd.SetColProperty("ibsCheck",  {Edit:0});
 	grid_sub_csAnaIlCd.SetColProperty("ibsCheck",  {Edit:0});
	
 	var anaJobId = "&anaJobId="+grid_sheet.GetCellValue(row, "anaJobId")+"&anaStrDtm="+grid_sheet.GetCellValue(row, "anaStrDtm");
 	//이력조회
	getImplHstLst(anaJobId);
}


function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	brDtlPopup(row);	
}

function  grid_sheet_OnSearchEnd (code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	}
}

//날짜 변환함수...
function convertDate() {
	
	var tempStr = grid_sheet.GetCellValue($('#selectRow').val(), "csAnaStrDtm");
	if(tempStr != "") {
		var str = tempStr.substr(0,4) + "-" + tempStr.substr(4,2) + "-" + tempStr.substr(6,2);
		$("input[name=csAnaStrDtm]").val(str);	
	}
	var tempStr = grid_sheet.GetCellValue($('#selectRow').val(), "csAnaEndDtm");
	if(tempStr != "") {
		var str = tempStr.substr(0,4) + "-" + tempStr.substr(4,2) + "-" + tempStr.substr(6,2);
		$("input[name=csAnaEndDtm]").val(str);	
	}
	var tempStr = grid_sheet.GetCellValue($('#selectRow').val(), "imPlStrDtm");
	if(tempStr != "") {
		var str = tempStr.substr(0,4) + "-" + tempStr.substr(4,2) + "-" + tempStr.substr(6,2);
		$("input[name=imPlStrDtm]").val(str);	
	}
	var tempStr = grid_sheet.GetCellValue($('#selectRow').val(), "imPlEndDtm");
	if(tempStr != "") {
		var str = tempStr.substr(0,4) + "-" + tempStr.substr(4,2) + "-" + tempStr.substr(6,2);
		$("input[name=imPlEndDtm]").val(str);	
	}

}


</script>
</head>
<body>

<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="IMPV.PLAN.INQ" /></div><!--개선계획 조회-->
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div id="search_div">
	<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
	<form id="frmSearch" name="frmSearch" method="post">
		 <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BZWR.RULE.INQ' />"> <!--업무규칙 조회-->

                   <caption><s:message code="IMPV.PLAN.INQ" /></caption><!--개선계획 조회-->
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
	                    	<input class="wd60p" type="text" id="dbcTblNm" name="dbcTblNm"/>
	                        </td> 
                        <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="dbcColNm" name="dbcColNm"/>
	                        </td> 
                   </tr>
                   	<tr>                        
                    	<th scope="row"><label for="bizAreaLnm"><s:message code="BZWR.TRRT.NM" /></label></th><!--업무영역명-->
                        	<td>
                        	<input class="wd60p" type="text" id="bizAreaLnm" name="bizAreaLnm" class="wd200"/>
                        	<button class="btnBizAreaLnmPop" id="btnBizAreaLnmPop"><s:message code="INQ" /></button><!--검색-->
                            </td>
                        <th scope="row"><label for="dqiLnm"><s:message code="QLTY.INDC.NM"/></label></th><!--품질지표명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="dqiLnm" name="dqiLnm" class="wd200"/>
	                    	<button class="btnDqiLnmPop" id="btnDqiLnmPop"><s:message code="INQ" /></button><!--검색-->
	                        </td> 
                        <th scope="row"><label for="ctqLnm"><s:message code="IMCE.INFO.ITEM.NM"/></label></th><!--중요정보항목명-->
</th>
	                    	<td>
	                    	<input class="wd60p" type="text" id="ctqLnm" name="ctqLnm" class="wd200"/>
	                    	<button class="btnCtqLnmPop" id="btnCtqLnmPop"><s:message code="INQ" /></button><!--검색-->
	                        </td> 
                   </tr>
                   	<tr>                        
                    	<th scope="row"><label for="brNm"><s:message code="BZWR.RGR.NM"/></label></th><!--업무규칙명-->

                        	<td>
                        	<input class="wd60p" type="text" id="brNm" name="brNm"/>
                            </td>
                        <th scope="row"><label for="prgsStat">진행상태</label></th><!--진행상태-->

	                    	<td>
	                    	<select id="prgsStat"  name="prgsStat">
								<option value="">전체</option>
							    <option value="01">지연</option>
							    <option value="02">진행중</option>	  
							    <option value="03">완료</option>	  
							</select>
	                        </td> 
                        <th scope="row"><label for="anaDgr"><s:message code="ANLY.HIST" /></label></th><!--분석차수-->

	                    	<td>
	                    	<select id="anaDgr"  name="anaDgr">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.brAnaDgrCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
							</select>
	                        </td> 
                          
                   </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div><!--를 사용하시면 됩니다.--><!--클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고-->

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
    <div class="selected_title" id="bizrule_sel_title"> <span></span></div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1"><a href="#tabs-1"><s:message code="CAUS.ANLY.DTL"/></a></li><!--원인분석상세-->
	    <li id="tab-2"><a href="#tabs-2"><s:message code="IMPV.PLAN.DTL"/></a></li><!--개선계획상세-->
	    <li id="tab-3"><a href="#tabs-3"><s:message code="CHG.HSTR" /></a></li><!--변경이력-->
	  </ul>
	  <div id="tabs-1">
	  		<div id="csDetailInfo"><%@include file="impl_rqst_csana_dtl.jsp" %></div>
	  </div>
	  <div id="tabs-2">
			<div id="imDetailInfo"><%@include file="impl_rqst_impl_dtl.jsp" %></div>
	  </div>
	  <div id="tabs-3">
			<div id="imHstInfo"><%@include file="impl_hst_lst.jsp" %></div>
	  </div>
	</div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</div>
<form id="frmHidden" name="frmHidden" method="post">
	<input type="hidden" id="selectRow" name="selectRow"/>
</form>


</body>

</html>