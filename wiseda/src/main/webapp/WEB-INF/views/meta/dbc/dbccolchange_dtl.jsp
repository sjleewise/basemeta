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


function initsubgrid_dbccolchange() {

    with(grid_sub_dbccolchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.DBCCOLCHANGE.DTL1'/>";
        /* No.|DB스키마명|만료일시|시작일시|주제영역명|DBC테이블명|DBC컬럼명|DBC컬럼한글명|DDL컬럼명|물리모델컬럼명 */
	    	headtext += "<s:message code='META.HEADER.DBCCOLCHANGE.DTL2'/>";
	    	/* |항목명|데이터타입|데이터길이|데이터자리수|데이터소수점|널여부|DEFAULT길이|DEFAULT값|PK여부|순서 */
	    	headtext += "<s:message code='META.HEADER.DBCCOLCHANGE.DTL3'/>";
	    	/* |PK순서|컬럼설명|등록유형|등록일시|수정일시|버전|설명 */
    
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
						{Type:"Text",   Width:120,   SaveName:"dbcColNm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbcColKorNm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"ddlColPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"pdmColPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"itmLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dataType", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dataLen", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dataPnum", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dataPnt", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Combo",   Width:120,   SaveName:"nullYn", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"defltLen", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"defltVal", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:120,   SaveName:"pkYn", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"ord", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"pkOrd", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"colDescn", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:120,   SaveName:"regTyp", 		Align:"Center", Edit:0, Hidden:0},
						{Type:"Date",   Width:120,   SaveName:"regDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Date",   Width:120,   SaveName:"updDtm", 		Align:"Left", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:120,   SaveName:"vers", 		Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"descn", 		Align:"Left", Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
		SetColProperty("regTyp", 	${codeMap.regTypCdibs});
// 		SetColProperty("dbmsType", 	${codeMap.dbmsTypCdibs});
		SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("nullYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dbccolchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_dbctblchange.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	initsubgrid_dbccolchange();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_dbccolchange_OnDblClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	
}

function grid_sub_dbccolchange_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_dbccolchange", "100%", "250px")</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
