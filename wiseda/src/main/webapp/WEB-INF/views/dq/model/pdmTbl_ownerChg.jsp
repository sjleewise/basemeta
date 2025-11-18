<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<c:set var="pdmrelyn"><s:message code="wiseda.pdm.rel" /></c:set>

<html>
<head>
<title><s:message code="TBL.INQ"/></title> <!-- 테이블 조회 -->

<script type="text/javascript">

EnterkeyProcess("Search");
var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...
var regtypcd = ${codeMap.regtypcd} ;

                       
$(document).ready(function() {
	
		//$( "#tabs" ).tabs();
		 $("#frmInput #stdAplYn").prop('disabled', true);
		// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        //$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

        $('#subjSearchPop').click(function(event){
		    	
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
		    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
				var popwin = OpenModal(url+"?"+param, "searchpop",  600, 400, "no");
				popwin.focus();
			
		});
        
        $("#btnGridSave").click(function(){
        	doAction("Save");
        }).show();
        
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        create_selectbox(sysareaJson, $("#sysAreaId"));
        create_selectbox(regtypcd, $("#regTypCd"));
      
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #subjLnm"), "SUBJ");
        
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #pdmTblLnm"), "PDMTBL");
        
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #pdmColLnm"), "PDMCOL");
        
        
        
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
/* 	var tmpstr = $("#pdmTblLnm").val();
	if(tmpstr != null && tmpstr != "" && tmpstr != "undefined") {
		doAction("Search");
	} */
	
	//주제영역 조회 페이지에서 더블클릭으로 subjLnm값을 받아오는 경우 자동으로 검색한다.
	var tmpSubjLnm = "${search.subjLnm}";
	if(!isBlankStr(tmpSubjLnm)) {
		$("#frmSearch #subjLnm").val(tmpSubjLnm);
		doAction("Search");
	}
	
	//PK값으로 검색
	if(!isBlankStr("${search.pdmTblId}")) {
		var param ="pdmTblId="+"${search.pdmTblId}";
		grid_sheet.DoSearch("<c:url value="/meta/model/getpdmtbllist.do" />", param);
	}
	
	
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("Search");
	}
	
	//$( "#layer_div" ).show();
});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.ASIS.TDL.OWNER.CHG'/>";

        //headtext = "No.|상태|선택|시스템ID|모델명|상위주제영역명|주제영역ID|주제영역논리명|주제영역|테이블ID|테이블물리명|테이블논리명|이전테이블(물리명)|표준적용여부|암호화여부|파티션테이블여부|DW대상테이블여부|담당자ID|담당자|설명|객체버전|등록유형코드|최초요청일시|최초사용자|요청일시|요청자|승인일시|승인자|보관주기|삭제기준|삭제방법|백업주기";
        
        var headers = [
	                    {Text:headtext, Align:"Center"}
	                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,  SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,  SaveName:"ibsCheck",      Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Combo",    Width:80,  SaveName:"sysAreaId", 	     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:40,  SaveName:"mdlLnm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:40,  SaveName:"uppSubjLnm",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:40,  SaveName:"subjId"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100, SaveName:"subLnm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:300, SaveName:"fullPath"	,    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150, SaveName:"pdmTblId",   	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:150, SaveName:"pdmTblPnm",     Align:"Left",   Edit:0, KeyField:1}, 
                    {Type:"Text",     Width:220, SaveName:"pdmTblLnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",     Width:60,  SaveName:"bfPdmTblPnm",   Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:90,  SaveName:"stdAplYn"	,    Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:90,  SaveName:"encYn"	,    Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:40,  SaveName:"partTblYn"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:40,  SaveName:"dwTrgTblYn",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:700, SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:40,  SaveName:"objVers"   ,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:40,  SaveName:"regTypCd",      Align:"Center", Edit:0, Hidden:1},                        
                    {Type:"Date",     Width:100, SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"frsRqstUserNm", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",     Width:100, SaveName:"rqstDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"rqstUserNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",     Width:100, SaveName:"aprvDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"aprvUserNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"ctdFcy"	,    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"delCri"	,    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"delMtd"	,    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"bckFcy"	,    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:40,  SaveName:"crgUserId"	,    Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:120, SaveName:"crgUserNm"	,    Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:40,  SaveName:"nxtUserId"	,    Align:"Center",   Edit:1, Hidden:1},
                    {Type:"Popup",    Width:120, SaveName:"nxtUserNm"	,    Align:"Center",   Edit:1 ,KeyField:1,UpdateEdit:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
	     SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("encYn", 		{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("partTblYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("dwTrgTblYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        
        InitComboNoMatchText(1, "");
        
//         SetColHidden("ibsStatus",1);
//         SetColHidden("arrUsrId",1);
        
         //FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}
//사용자조회 팝업 리턴값 처리
function returnUserPop (ret, row) {

	var retjson = jQuery.parseJSON(ret);
	var selectRow = grid_sheet.GetSelectRow();

	//alert(ret+ selectRow);

	grid_sheet.SetCellValue(selectRow, "nxtUserId", retjson.userId);
	grid_sheet.SetCellValue(selectRow, "nxtUserNm", retjson.userNm);
}
function grid_sheet_OnPopupClick(Row,Col) {
	//사용자 검색 팝업 오픈
	if ("nxtUserNm" == grid_sheet.ColSaveName(Col)) {
		var param = "row=" +Row;
		var url = '<c:url value="/commons/damgmt/sysarea/popup/userlst_pop.do" />';
		openLayerPop(url, 700, 500, param);
	}
}
//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
	
	$("#frmSearch #subjLnm").val(retjson.fullPath);
	
}


//주제영역 검색 팝업에서 선택한 내용을 json으로 반환 받는다...
function getSubjPop(subjjson) {
	
	$("form#frmSearch input[name=subjLnm]").val(subjjson.subjLnm);
	$("form#frmSearch input[name=subjId]").val(subjjson.subjId);
	
}


//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	
        	
        	//요청서 마스터 번호로 조회한다...
        	$('#frmSearch input[name=pdmTblId]').val('');
        	$('#frmSearch input[name=subjId]').val('');
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/model/getpdmtbllist.do" />", param);
        	
        	break;
        	
        case "Report": //단일 조회...
        	
			if($("#subjLnm").val() == "") {
        		
				//주제영역을 입력하세요.
				showMsgBox("ERR","<s:message code='SUBJ.INPUT' />")
				return;
        	}
       
        	//var saveJson = grid_sheet.GetSaveJson(1);	

			//if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/model/prtXlsRptPdmTbl.do"/>";
			
			var param = $("#frmSearch").serialize();
			
			$("#frmSearch").attr("target","").attr("action",url).submit();
        	
			break;
			
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;

        case "Save":
            var regJson = grid_sheet.GetSaveJson();
			var url = "<c:url value="/meta/model/regpdmtblownerchg.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			
			IBSpostJson2(url, regJson, param, ibscallback);
        	break;
    }       
}


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_sheet_OnDblClick(row, col, value, CellX, CellY, CellW, CellH) {
//     alert("tbl dbl click");
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
// 	alert("tbl click event");

    //if(row < 1)
         return;

}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
        alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
        alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
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
		
		$('#btnfrmReset').click();
		
		if(!isBlankStr("${search.pdmTblId}")) {
			tblClick(1);
		}
		
		
			
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
	
}






</script>
</head>

<body>

<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PHYC.MDEL.INQ" /></div> <!-- 물리모델 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<div id="search_div">
<!-- 검색조건 입력폼 -->
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" name="pdmTblId" />
        	<input type="hidden" name="subjId" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class="" style="display: none;"><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 -->
                            <td style="display: none;">
                                <select id="sysAreaId" class="" name="sysAreaId">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                 </select>
                            </td>
                           <th scope="row"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
<!--                                 <input type="hidden" name="subjLnm" id="subjLnm" /> -->
                                <input type="text" name="subjLnm" id="subjLnm" class="wd340"/>
                                		<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
		                                <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                            </td>
                           <th scope="row"><label for="stdAplYn"><s:message code="STRD.APL.YN" /></label></th> <!-- 표준적용여부 -->
                            <td>
                                <select id="stdAplYn" class="" name="stdAplYn" >
                                  <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                  <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                  <option value="N"><s:message code="MSG.NO" /></option> <!-- 아니오 -->
                                </select>
                            </td>
                       </tr>
                       <tr>                               
                           <th scope="row" ><label for="pdmTblLnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" class="wd200" value="${search.pdmTblLnm}"/>
                                </span>
                            </td>
                           <th scope="row" ><label for="pdmColLnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmColLnm" id="pdmColLnm" class="wd200" value="${search.pdmColLnm}"/>
                                </span>
                            </td>
                       </tr>
                       <tr>
                       		<th scope="row"><label for="encYn"><s:message code="ENTN.INFO.INCL"/></label></th> <!-- 암호화 정보 포함 -->
                            <td colspan="3">
                                <select id="encYn" class="" name="encYn">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                 </select>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM2' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
<!-- 조회버튼영역  -->
<%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" /> --%>
<div class="bt03">
	<button class="btn_search" id="btnSearch" name="btnSearch" style="display:none"><s:message code="INQ"/></button> <!-- 조회 --> 
</div>
<div id="divInputBtn" style="text-align: right; ">
           <button class="btn_frm_save" type="button" id="btnGridSave" name="btnGridSave"><s:message code="STRG" /></button> <!-- 저장 --> 
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="tbl_sle_title"><s:message code="MSG.TBL.CHC" /></div> <!-- 테이블을 선택하세요. -->
	</div>
<%-- 
<div style="clear:both; height:5px; "><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1" ><a href="#tabs-1"><s:message code="TBL.DFNT.P" /></a></li><!-- 테이블 정의서 -->
	    <li id="tab-2" ><a href="#tabs-2"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼목록 -->
	    <li id="tab-4" ><a href="#tabs-4"><s:message code="CLMN.INFO" /></a></li> <!-- 컬럼정보 -->
	    <li id="tab-6" ><a href="#tabs-6"><s:message code="TBL.HSTR" /></a></li> <!-- 테이블이력 -->
	    <li id="tab-5" ><a href="#tabs-5"><s:message code="CLMN.HSTR" /></a></li> <!-- 컬럼이력 -->
	    <c:if test="${ pdmrelyn == 'Y'}">
	    <li id="tab-3" ><a href="#tabs-3"><s:message code="RLT.LST" /></a></li> <!-- 관계목록 -->
	    <li id="tab-7" ><a href="#tabs-7"><s:message code="RLT.HSTR" /></a></li> <!-- 관계이력 -->
	    </c:if>
<!-- 	    <li id="tab-6" ><a href="#tabs-6">종속성</a></li> -->
<!-- 	    <li id="tab-7" ><a href="#tabs-7">Where Used</a></li> -->
	  </ul>
	  <div id="tabs-1">
		<!-- 입력폼 시작 -->
	         <form name="frmInput" id="frmInput" method="post" >
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="input_form_div">
	            <fieldset>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="SUBJ.TRRT.INFO" /></div> <!-- 주제영역 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
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
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="subjId">주제영역ID</label></th> -->
	                                <td colspan="3"><span class=""><input type="text" id="subjId" name="subjId"/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row" class=""><label for="mdlLnm">모델논리명</label></th> -->
<!-- 	                                <td colspan="3"><input type="text" id="mdlLnm" name="mdlLnm" /></td> -->
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" ><label for="fullPath"><s:message code="SUBJ.TRRT" /></label></th> <!-- 주제영역 -->
	                                <td colspan="3"><input type="text" id="fullPath" name="fullPath" class="wd99p" readonly/></td>

<!-- 	                                <th scope="row" class=""><label for="subjLnm">주제영역 논리명</label></th> -->
	                                <td colspan="1"><span class="" ><input type="text" id="subjLnm" name="subjLnm" readonly/></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
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
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="pdmTblId">테이블ID</label></th> -->
	                                <td colspan="3"><span class=""><input type="text" id="pdmTblId" name="pdmTblId"/></span></td>
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row"><label for="pdmTblPnm"><s:message code="TBL.NM.PHYC.NM" /></label></th> <!-- 테이블명(물리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmTblPnm" name="pdmTblPnm" class="wd98p" readonly /></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM.LGC.NM" /></label></th> <!-- 테이블명(논리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmTblLnm" name="pdmTblLnm" class="wd98p" readonly/></span></td>
	                            </tr>
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="bfPdmTblPnm">이전 테이블명</label></th> -->
<!-- 	                                <td colspan="3"><input type="text" id="bfPdmTblPnm" name="bfPdmTblPnm" /></td> -->
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row"><label for="stdAplYn"><s:message code="STRD.APL.YN" /></label></th> <!-- 표준적용여부 -->
	                                <td >
		                                <input type="checkbox" id="stdAplYn" name="stdAplYn"     value="Y" /><span class="input_check"><s:message code="STRD.APL.YN" /></span> <!-- 표준적용여부 -->
			                            <!-- <input type="checkbox" id="partTblYn" name="partTblYn"   value="Y" /><span class="input_check">파티션테이블여부</span>
			                            <input type="checkbox" id="dwTrgTblYn" name="dwTrgTblYn" value="Y" /><span class="input_check">DW대상테이블여부</span> -->
	                                </td>
	                                	<th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                                    <td>
                                        <select id="regTypCd" class="" name="regTypCd"  disabled="disabled">
									     <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								        </select>
								</td>
	                            </tr>
	                         <!--     <tr>
	                               <th scope="row"><label for="crgUserNm">담당자</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="crgUserNm" name="crgUserNm" class="wd98p" readonly/></span></td> -->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
							
<!-- 	                                <th scope="row"><label for="regTypCd">상태</label></th> -->
<!-- 	                                <td colspan="1"><input type="text"  id="regTypCd" name="regTypCd"></td> -->
<!--                            </tr>-->
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""  class="b0" style="height:50px;width:99%;" readonly></textarea></td>
	                            </tr>
	                            <tr style ="display : none">
	                                <th scope="row"><label for="ctdFcy"><s:message code="STGE.CYCL"/></label></th> <!-- 보관주기 -->
	                                <td colspan="1"><span class=""  ><input type="text"  id="ctdFcy" name="ctdFcy" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="bckFcy"><s:message code="BAUP.CYCL"/></label></th> <!-- 백업주기 -->
	                                <td colspan="1"><span class="" ><input type="text" id="bckFcy" name="bckFcy" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr style ="display : none">
	                                <th scope="row"><label for="delCri"><s:message code="DEL.BASE"/></label></th> <!-- 삭제기준 -->
	                                <td colspan="1"><span class="" ><input type="text" id="delCri" name="delCri" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="delMtd"><s:message code="DEL.MTH"/></label></th> <!-- 삭제방법 -->
	                                <td colspan="1"><span class="" ><input type="text" id="delMtd" name="delMtd" class="wd98p" readonly/></span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                 <div style="clear:both; height:10px;"><span></span></div>
					 <div class="stit"><s:message code="DEMD.DTL.INFO" /></div> <!-- 요청상세정보 -->
					 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <s:message code="CLMN.NM1" /> <!-- 컬럼 이름 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" class=""><label for="frsRqstDtm"><s:message code="FRST.DEMD.DTTM" /></label></th> <!-- 최초요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstDtm" name="frsRqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="frsRqstUserNm"><s:message code="FRST.DMNT" /></label></th><!-- 최초요청자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="rqstUserNm"><s:message code="DMNT" /></label></th> <!-- 요청자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="aprvUserNm"><s:message code="APRR" /></label></th> <!-- 승인자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvUserNm" name="aprvUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
					<%@include file="../stnd/otherinfo.jsp" %>
	                </fieldset>
		</div>
	            </form>
		<!-- 입력폼 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>
		<!-- 입력폼 버튼... -->
<!-- 		<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 			<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 			<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 		</div> -->
	  </div>
	  
	  <div id="tabs-2">
	  <div id="test">
	  	<!-- 컬럼 목록 탭 -->
		<%@include file="pdmcol_lst.jsp" %>
	  </div>
		
	  </div>
	  
	  <div id="tabs-4">
	  <!-- 컬럼 정보 탭 -->
		<%@include file="pdmcolinfo_dtl.jsp" %>
	  </div>
	  <div id="tabs-6">
		<!-- 테이블 이력 탭 -->
		<%@include file="tblhist_dtl.jsp" %>
	  </div>	  

	  <div id="tabs-5">
		<!-- 컬럼 이력 탭 -->
		<%@include file="coltocolhist_dtl.jsp" %>
	  </div>
	<c:if test="${ pdmrelyn == 'Y'}">	  
	  <div id="tabs-3">
	  <!-- 관계 정보 탭 -->
		<%@include file="pdmrellst_dtl.jsp" %>
	  </div>
	  <div id="tabs-7">
		<!-- 관계 이력 탭 -->
		<%@include file="relhist_dtl.jsp" %>
	  </div>
	 </c:if>	  
	  
	</div>
</div> --%>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

</body>
</html>