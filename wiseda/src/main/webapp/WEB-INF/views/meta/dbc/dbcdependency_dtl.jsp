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

$(document).ready(function(){
	$("#btnDbcDependencyExcelDown").click(function(){
		grid_sub_dpnd.Down2Excel({HiddenColumn:1, Merge:1, FileName:"dbcdependency.xls"});
	});
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
});


function initsubgrid_dpnd() {

    with(grid_sub_dpnd){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(msAll);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.DBCTBLDEPENDENCY.DTL'/>", Align:"Center"}
                ];
        // No.|DBMS명|DBMS명|DB스키마명|DB스키마명|의존객체명|의존객체유형|참조DB스키마명|참조객체명|참조객체유형|참조객체링크명|의존객체타입
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0 ,ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"dbConnTrgPnm", 		Align:"Left", Edit:0, Hidden:1,ColMerge:1},
					{Type:"Text",   Width:100,   SaveName:"dbConnTrgLnm", 		Align:"Left", Edit:0, Hidden:0,ColMerge:1},
					{Type:"Text",   Width:100,   SaveName:"dbSchPnm", 		Align:"Left", Edit:0, Hidden:1,ColMerge:1},
					{Type:"Text",   Width:100,   SaveName:"dbSchLnm", 		Align:"Left", Edit:0, Hidden:0,ColMerge:1},
					{Type:"Text",   Width:150,   SaveName:"dpndNm", 		Align:"Left", Edit:0, Hidden:0,ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"dpndObjTyp",	 	Align:"Center", Edit:0, Hidden:0,ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"repObjDbSchPnm",	 	Align:"Left", Edit:0, Hidden:0,ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"repObjNm",	 	Align:"Left", Edit:0, Hidden:0,ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"repObjTyp",	 	Align:"Center", Edit:0, Hidden:0,ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"repObjLinkNm",	 	Align:"Left", Edit:0, Hidden:0,ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"dpndTyp",	 	Align:"Center", Edit:0, Hidden:0,ColMerge:0},
                	];
                    
        InitColumns(cols);

        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dpnd);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_dpnd.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	initsubgrid_dpnd();
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_dpnd_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_sub_dpnd_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_sub_dpnd_OnSearchEnd(code, message) {
	//alert(grid_sub_dpnd.GetDataBackColor()+":"+ grid_sub_dpnd.GetDataAlternateBackColor());
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
     <div class="divLstBtn" style="display: none;">
            <div class="bt03">
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnDbcDependencyExcelDown" name="btnDbcDependencyExcelDown">엑셀 내리기</button>                       
	    	</div>
	 </div>
     <!-- 버튼영역  -->
</div>

 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sub_dpnd", "100%", "250px")</script>
</div>
<!-- 그리드 입력 입력 End -->
		
<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
