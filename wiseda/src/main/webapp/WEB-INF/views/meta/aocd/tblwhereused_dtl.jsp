<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
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
	//그리드 초기화
	initsubgrid_tblwhereused();
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_tblwhereused.FitColWidth();
});


function initsubgrid_tblwhereused() {

    with(grid_sub_tblwhereused){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.WHEREUSED.DTL.3'/>", Align:"Center"}
                ];
                //No.|개체유형|PDM_TBL_ID|개체논리명|개체물리명|OWNER
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
                    {Type:"Combo",   Width:140,  SaveName:"objType",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:140,  SaveName:"pdmTblId",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:140,  SaveName:"objLnm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:140,  SaveName:"objPnm",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    Align:"Center", Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
      	SetColProperty("objType", 	{ComboCode:"DDL|DBC", 	ComboText:"DDL|DBC"});

        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_tblwhereused);    
    //===========================
    	
}

$(window).on('load',function() {
	initsubgrid_tblwhereused();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_tblwhereused_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_tblwhereused_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_tblwhereused_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_tblwhereused", "100%", "250px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
