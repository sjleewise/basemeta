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

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_sheet.FitColWidth();
});


function initsubgrid() {

    with(grid_sub_sheet) {
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0,UseHeaderSortCancel:1};
        SetConfig(cfg);
        SetMergeSheet(5);

        var headText1 = "<s:message code='META.HEADER.MTAGAP.DTL.1'/>";
        var headText2 = "<s:message code='META.HEADER.MTAGAP.DTL.2'/>";
        //var headText1 = "No.|GAP상태|GAP유형|메타정보|메타정보|메타정보|메타정보|메타정보|메타정보|메타정보|메타정보|메타정보|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타|Collector수집메타";
        //var headText2 = "No.|GAP상태|GAP유형|컬럼ID|컬럼영문명|컬럼한글명|컬럼순서|PK|타입|길이|소수점|NOTNULL|shcID|테이블영문명|컬럼영문명|컬럼한글명|컬럼순서|PK|타입|길이|소수점|NOTNULL";
        
        var headers = [
                    {Text:headText1, Align:"Center"},
                    {Text:headText2, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Combo",  Width:100,  SaveName:"gapStatus",     Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"gapConts",      Align:"Left",   Edit:0, Hidden:0},
                    
                    {Type:"Text",   Width:100,  SaveName:"mtaColId",     Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:150,  SaveName:"mtaColPnm",    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:150,  SaveName:"mtaColLnm",    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:70,   SaveName:"mtaColOrd",      Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:50,   SaveName:"mtaPkYn",      Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"mtaDataType",   Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:70,   SaveName:"mtaDataLen",  Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"mtaDataScal",   Align:"Right",  Edit:0, Hidden:1},
                    {Type:"Text",   Width:90,   SaveName:"mtaNonulYn",   Align:"Center",  Edit:0},    
                    
                    {Type:"Text",   Width:100,  SaveName:"dbcDbSchId",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"dbcTblPnm",    Align:"Left",   Edit:0, Hidden:1},  
                    {Type:"Text",   Width:150,  SaveName:"dbcColPnm",   Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:150,  SaveName:"dbcColLnm",   Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:70,   SaveName:"dbcColOrd",     Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:50,   SaveName:"dbcPkYn",     Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"dbcDataType",  Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:70,   SaveName:"dbcDataLen", Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"dbcDataScal",  Align:"Right",  Edit:0, Hidden:1},
                    {Type:"Text",   Width:90,   SaveName:"dbcNonulYn", Align:"Center", Edit:0}
                   
                ];
                    
        InitColumns(cols);
        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        InitComboNoMatchText(1, "");
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_sheet);    
    //===========================
}

$(window).on('load',function() {
	//그리드 초기화
// 	initsubgrid();
});
	 
/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_sheet_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
}

function grid_sub_sheet_OnClick(row, col, value, cellx, celly) {

	if(row < 1) return;
}

function grid_sub_sheet_OnSearchEnd(code, message, stCode, stMsg) {
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
    
	<form name="frmCodeDtl" action ="" method="post">
	<div class="tb_basic" style="display: none;">
    <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
      <caption>
      <s:message code="INQ.COND" /><!-- 조회조건 -->
      </caption>
      <colgroup>
          <col style="width:12%;">
          <col style="width:38%;">
          <col style="width:12%;">
          <col style="width:38%;">
      </colgroup>
      <tr>
      	<th><s:message code="PGM.NM" /></th> <!-- 프로그램명 -->
      	<td>
<%--       		<input type="text" name="searchWrd" value="${searchVO.searchWrd}"> --%>
      	</td>
      </tr>
    </table>
    </div>
    </form>
    <div style="clear:both; height:5px;"><span></span></div>
    
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub_sheet", "100%", "250px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
<!-- </body> -->
<!-- </html> -->