<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">
//최근 선택 row

$(document).ready(function(){

		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_dbctblchange.FitColWidth();
});


function initsubgrid_dbctblchange() {

    with(grid_sub_dbctblchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.DBCTBLCHANGE.DTL1'/>";
        /* No.|DB스키마명|만료일시|시작일시|주제영역명|DBC테이블명|DBC테이블한글명|DDL테이블명|DBC테이블스페이스명|물리모델테이블명 */
	    	headtext += "<s:message code='META.HEADER.DBCTBLCHANGE.DTL2'/>";
	    	/* |DBMS타입|컬럼갯수|로우갯수|테이블크기|데이터크기|인덱스크기|미사용크기|이전컬럼갯수|이전로우갯수|이전테이블크기 */
	    	headtext += "<s:message code='META.HEADER.DBCTBLCHANGE.DTL3'/>";
	    	/* |이전데이터크기|이전인덱스크기|이전미사용크기|분석일시|생성일시|변경일시|등록유형|등록일시|수정일시|버전|설명 */
    
	    var headers = [
	                {Text:headtext, Align:"Center"}
	            ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:120,   SaveName:"dbSchPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Date",   Width:150,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
	                    {Type:"Date",   Width:150,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:120,   SaveName:"fullPath", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbcTblNm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbcTblKorNm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"ddlTblPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbcTblSpacNm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"pdmTblPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:120,   SaveName:"dbmsType", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"colEacnt", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"rowEacnt", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"tblSize", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dataSize", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"idxSize", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"nuseSize", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"bfColEacnt", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"bfRowEacnt", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"bfTblSize", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"bfDataSize", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"bfIdxSize", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"bfNuseSize", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Date",   Width:120,   SaveName:"anaDtm", 		Align:"Left", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Date",   Width:120,   SaveName:"crtDtm", 		Align:"Left", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Date",   Width:120,   SaveName:"chgDtm", 		Align:"Left", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Combo",   Width:120,   SaveName:"regTyp", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Date",   Width:120,   SaveName:"regDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Date",   Width:120,   SaveName:"updDtm", 		Align:"Left", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:120,   SaveName:"vers", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"descn", 		Align:"Left", Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
		SetColProperty("regTyp", 	${codeMap.regTypCdibs});
		SetColProperty("dbmsType", 	${codeMap.dbmsTypCdibs});
// 		SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 		SetColProperty("nullYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dbctblchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_dbctblchange.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	initsubgrid_dbctblchange();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_dbctblchange_OnDblClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	
}

function grid_sub_dbctblchange_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

</script>

<!-- </head> -->
<!-- <body>     -->
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    
    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
<%--     <tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" /> --%>
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub_dbctblchange", "100%", "250px")</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
