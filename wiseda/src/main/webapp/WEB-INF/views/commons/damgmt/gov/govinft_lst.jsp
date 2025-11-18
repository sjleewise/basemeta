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
<title>범정부연계</title>

<script type="text/javascript">
//엔터키 이벤트 처리
// EnterkeyProcess("Search");

var interval = "";

$(document).ready(function() {
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
        
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
        //
        $("#btnSend").click( function(){ doAction("Send"); } );
        
        $("#btnCmMapSend").click( function(){ doAction("CmMapSend"); } );

        
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        create_selectbox(sysareaJson, $("#sysAreaId"));
      
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:1,Page:100};
        SetConfig(cfg);
        var headtext = "No|상태|선택"; //3
            headtext += "|기관코드|기관명|정보시스템코드|정보시스템명|접속대상DB ID|DB 객체버전|등록유형코드|사용자ID|작성일시|물리DB명|논리DB명|DBMS정보(유형)|DBMS정보(버전)|DB설명|테이블수|구축일자|데이터용량|적용업무|운영체제정보|수집제외사유"; //20
            headtext += "|메타테이블ID|테이블소유자ID|테이블 객체버전|테이블 등록유형코드|테이블 등록번호|테이블 등록일련번호|테이블 등록일시|테이블 승인(처리)일시|테이블 사용자ID|테이블영문명|테이블한글명|테이블소유자|테이블설명|테이블유형|업무분류체계ID|업무분류체계명|보존기간|테이블볼륨|공개/비공개여부|비공개사유|상세관련근거|개방데이터목록|발생주기|품질진단여부"; //24
            headtext += "|메타컬럼ID|컬럼순서|비공개사유|컬럼 등록번호|컬럼 등록일련번호|컬럼 등록상세일련번호|컬럼 객체버전|컬럼 등록유형코드|컬럼 등록일시|컬럼 승인(처리)일시|컬럼 사용자ID|컬럼영문명|컬럼한글명|매핑컬럼영문명|매핑컬럼한글명|PK정보|FK정보|데이터타입|데이터길이|데이터포맷|NOTNULL여부|제약조건|공개/비공개여부|개인정보여부|암호화여부|컬럼설명"; //26
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:1, Sort:0, Hidden:1}, //3
                    
                    {Type:"Text",	Width:100,	SaveName:"orgCd",			Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"orgNm",			Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"infoSysCd",       Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"infoSysNm",       Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dbConnTrgId",     Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"dbObjVers",       Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Combo",	Width:100,	SaveName:"dbRegTypCd",      Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dbWritUserId",    Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Date",	Width:100,	SaveName:"dbWritDtm",       Align:"Center"	,Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",	Width:100,	SaveName:"dbConnTrgPnm",    Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dbConnTrgLnm",    Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dbmsTypCd",       Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dbmsVersCd",      Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dbObjDescn",     	Align:"Left"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"tblCnt",          Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"constDt",         Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dataCpct",        Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"aplyBizNm",       Align:"Left"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"osVerNm",         Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"pdataExptRsn",    Align:"Left"	,Edit:0, Hidden:0}, //20 23
                    
                    {Type:"Text",	Width:100,	SaveName:"mtaTblId",        Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"dbSchId",         Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"tblObjVers",      Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Combo",	Width:100,	SaveName:"tblRegTypCd",     Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"tblRqstNo",       Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"tblRqstSno",      Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Date",	Width:100,	SaveName:"tblFrsRqstDtm",   Align:"Center"	,Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",	Width:100,	SaveName:"tblAprvDtm",      Align:"Center"	,Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",	Width:100,	SaveName:"tblRqstUserId",   Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"mtaTblPnm",       Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"mtaTblLnm",       Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dbSchPnm",        Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"tblObjDescn",     Align:"Left"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"tblTypNm",        Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"subjId",          Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"subjNm",          Align:"Left"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"prsvTerm",        Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"tblVol",          Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"openRsnCd",       Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"nopenRsn",        Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"nopenDtlRelBss",  Align:"Left"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"openDataLst",     Align:"Left"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"occrCyl",         Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dqDgnsYn",        Align:"Center"	,Edit:0, Hidden:0}, //24 47
                    
                    {Type:"Text",	Width:100,	SaveName:"mtaColId",        Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"colOrd",          Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"priRsn",          Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"colRqstNo",       Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"colRqstSno",      Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"colRqstDtlSno",   Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"colObjVers",      Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Combo",	Width:100,	SaveName:"colRegTypCd",     Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Date",	Width:100,	SaveName:"colRqstDtm",      Align:"Center"	,Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",	Width:100,	SaveName:"colAprvDtm",      Align:"Center"	,Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",	Width:100,	SaveName:"colRqstUserId",   Align:"Center"	,Edit:0, Hidden:1},
                    {Type:"Text",	Width:100,	SaveName:"mtaColPnm",       Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"mtaColLnm",       Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"govMtaColPnm",    Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"govMtaColLnm",    Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"pkYn",            Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"fkYn",            Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dataType",        Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dataLen",         Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"dataFmt",         Align:"Left"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"nonulYn",         Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"constCnd",        Align:"Left"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"openYn",          Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"prsnInfoYn",      Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"encTrgYn",        Align:"Center"	,Edit:0, Hidden:0},
                    {Type:"Text",	Width:100,	SaveName:"colObjDescn",     Align:"Left"	,Edit:0, Hidden:0}  //26 73

                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
	    SetColProperty("dbRegTypCd", 	{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"}); 
	    SetColProperty("tblRegTypCd", 	{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"}); 
	    SetColProperty("colRegTypCd", 	{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"}); 
	    
	    
        InitComboNoMatchText(1, "");
        
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
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/commons/damgmt/gov/govInftTotList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'범정부연계 조회'});
            break;
            
        case "Send":
        	var myForm = document.govForm;
        	var url ='<c:url value="/commons/damgmt/gov/govInftCsvDown.do" />';
        	
        	myForm.action=url;
        	myForm.method="post";
        	myForm.target="govForm";
        	myForm.submit();
        	
        	break;
        	
        case "CmMapSend":
        	var myForm = document.govForm;
        	var url ='<c:url value="/commons/damgmt/gov/govInftMapCsvDown.do" />';
        	
        	myForm.action=url;
        	myForm.method="post";
        	myForm.target="govForm";
        	myForm.submit();
        	
        	break;
    }       
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			  doAction("Search");
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
}


function grid_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}




</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">범정부연계조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
<form name="govForm">
</form>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />">
                
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="subjNm"><s:message code="SUBJ.TRRT.NM" /> </label></th>
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjNm" id="subjNm" />
                                </span>
                            </td>
                           <th scope="row"><label for="dbConnTrgPnm">DBMS 명 </label></th>
                            <td>
                                <span class="input_file">
                                <input type="text" name="dbConnTrgPnm" id="dbConnTrgPnm" />
                                </span>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<div class="divLstBtn">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnSend" name="btnSend">범정부연계 파일 전송</button> <!-- 엑셀 내리기 -->
				<button class="btn_excel_down" id="btnCmMapSend" name="btnCmMapSend">범정부연계 파일 전송(공통표준매핑)</button> <!-- 엑셀 내리기 -->
	        	<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->
	    	</div>
        </div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>
</body>
</html>