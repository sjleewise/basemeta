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
<title><s:message code='SUBJ.TRRT.INQ' /></title> <!-- 주제영역조회 -->

<script type="text/javascript">

var interval = "";

$(document).ready(function() {
		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
     	
        // POI 다운로드 Event Bind
        $("#poiDown2").click( function(){ doAction("PoiDown"); } ).show();

        $('#subjSearchPop').click(function(event){
		    event.preventDefault();	//브라우저 기본 이벤트 제거...
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
			var popwin = OpenModal(url+"?"+param, "searchpop",  600, 400, "no");
			popwin.focus();
		
		});

      //연계서버접속정보 팝업
		$("#btnSend").click(function(){ 
        	doAction("GovSend");
        }).show();
      
});

$(window).on('load',function() {
	//$( "#layer_div" ).show();
	//그리드 초기화 
	initGrid();

	$(window).resize();

});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.GOV.EXCL.LST1'/>"},
                    {Text:"<s:message code='META.HEADER.GOV.EXCL.LST2'/>", Align:"Center"}
                ];
                //No.|데이터구분|기관명|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보
        		//No.|데이터구분|기관명|정보시스템명|관련법령(보유목적)|구축년도|운영부서명|담당자명|담당자전화번호|담당자이메일|논리DB명|물리DB명|DB설명|적용업무|DBMS종류|DBMS버전|운영체제종류|운영체제버전|구축일자|테이블수|데이터용량(MB)|수집제외사유|테이블소유자|테이블영문명|테이블한글명|테이블설명|테이블볼륨(ROW수)|테이블유형|업무분류체계|업무분류체계ID|상위업무분류체계ID|품질진단여부|보존기간|발생주기|공개/비공개여부(테이블)|비공개사유(테이블)|상세관련근거|컬럼영문명|컬럼한글명|컬럼설명|데이터타입|데이터길이|데이터포맷|NOT NULL여부|PK정보|FK정보|제약조건|공개/비공개여부(컬럼)|비공개사유(컬럼)|개인정보여부|암호화여부
        		
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",          Align:"Center", Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"dataDiv",   	    Align:"Left",   Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:120,  SaveName:"orgNm",   	    Align:"Left",   Edit:0}, //기관명 
                    //정보시스템정보
                    {Type:"Text",   Width:120,  SaveName:"infoSysNm",   	Align:"Left",   Edit:0}, 
//                     {Type:"Text",   Width:120,  SaveName:"relLaw",   	    Align:"Left",   Edit:0}, 
//                     {Type:"Text",   Width:120,  SaveName:"constYy",   	    Align:"Center",   Edit:0}, 
//                     {Type:"Text",   Width:120,  SaveName:"operDeptNm",   	Align:"Left",   Edit:0}, 
//                     {Type:"Text",   Width:120,  SaveName:"crgUserNm",   	Align:"Left",   Edit:0}, 
//                     {Type:"Text",   Width:120,  SaveName:"crgTelNo",   	    Align:"Left",   Edit:0}, 
//                     {Type:"Text",   Width:120,  SaveName:"crgEmailAddr",   	Align:"Left",   Edit:0}, 
                    //데이터베이스정보
                    {Type:"Text",   Width:120,  SaveName:"dbLnm",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dbPnm",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dbDescn",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"applBz",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dbmsTypCd",   	Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dbmsVersCd",   	Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"osKind",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"osVers",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"esblhDt",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"tblCnt",   	    Align:"Right",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dataCapa",   	    Align:"Right",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"gthExcptRsn",   	Align:"Left",   Edit:0}, 
                    //테이블정보
                    {Type:"Text",   Width:120,  SaveName:"schPnm",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"tblPnm",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"tblLnm",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"tblDescn",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"tblVol",   	    Align:"Right",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"tblDiv",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"subjLnm",   	    Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:120,  SaveName:"subjId",   	    Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:120,  SaveName:"uppSubjId",   	Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:120,  SaveName:"dqDgnsYn",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"prsvTerm",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"occrCyl",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"openRsnCd",   	Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"tblNopenRsn",   	Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"nopenDtlRelBss",  Align:"Left",   Edit:0}, 
                    //컬럼정보
                    {Type:"Text",   Width:120,  SaveName:"colPnm",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"colLnm",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"colDescn",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dataType",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dataLen",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"dataFormat",   	Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"nonulYn",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"pkYn",   	        Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"fkYn",   	        Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"constCnd",   	    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"openYn",   	    Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"colNopenRsn",   	Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"prsnInfoYn",   	Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"encYn",   	    Align:"Center",   Edit:0}
                    
                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
// 	    SetColProperty("sysAreaId", ${codeMap.sysareaibs});
// 	    SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
        InitComboNoMatchText(1, "");
        
        
      
        // FitColWidth();
        
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
        	grid_sheet.DoSearch("<c:url value="/meta/model/getGovList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'범정부엑셀업로드템플릿.xlsx', TitleText:'양식 변경하지 마세요.', UserMerge:'0,0,1,3'});
            break;

        case "PoiDown":
        	
        	//hidden input을 가지고있는 poiForm
        	var myForm = document.poiForm;
        	var url ='<c:url value="/meta/model/poiDown.do" />';
        	
        	frmSearch.action=url;
        	frmSearch.method="post";
        	frmSearch.target="myForm";
        	frmSearch.submit();	//dbCodeValue, schCodeValue, sheetNum 세가지의 값을 POST로 넘김

        	break;
        	
		case "GovSend":
        	
        	var url   = "<c:url value="popup/gov_send_pop.do"/>";
			var param = "popRqst=Y";
			
			openLayerPop(url, 800, 350, param);
			
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
	}
	
}

//주제영역 검색 팝업에서 선택한 내용을 json으로 반환 받는다...
function getSubjPop(subjjson) {
	
	$("form#frmSearch input[name=subjLnm]").val(subjjson.subjLnm);
	$("form#frmSearch input[name=subjId]").val(subjjson.subjId);
	
}

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	
	console.log(retjson);
	
	$("#frmSearch #subjPnm").val(retjson.fullPath);
	$("#frmSearch #subjId").val(retjson.subjId);
	
}

</script>
</head>

<body>

<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" id="subjId" name="subjId" />
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
                       		<th scope="row"><label for="infoSysNm"><s:message code="INFO.SYS.NM" /></label></th> <!-- 정보시스템명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="infoSysNm" id="infoSysNm" class="wd340" />
                                </span>
                            </td>
                            
                           <th scope="row"><label for="subjPnm"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjPnm" id="subjPnm" class="wd340"/>
		                                <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                            </td>
                       </tr>
                       <tr>
                       		<th scope="row"><label for="dbPnm"><s:message code="DB.MS.NM" /></label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="dbPnm" id="dbPnm" class="wd340"/>
                                </span>
                            </td>
                            
                           <th scope="row"><label for="schPnm"><s:message code="SCHEMA.NM" /></label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="schPnm" id="schPnm" class="wd340"/>
                                </span>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM2' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
</body>
</html>