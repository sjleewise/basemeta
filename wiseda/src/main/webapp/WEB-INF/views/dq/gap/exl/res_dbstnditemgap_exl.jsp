<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title>비표준컬럼분석 조회</title>

<script type="text/javascript">

EnterkeyProcess("Search");


// var connTrgSchJson = ${codeMap.connTrgSch} ;                 
$(document).ready(function() {
	
		
        
});

$(window).load(function() {
	//그리드 초기화 
	initDbSditmGrid();
	
});


$(window).resize(
    
    function(){
                
    	//setibsheight($("#grid_01"));
    }
);


function initDbSditmGrid()
{
    
    with(dbsditm_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext1 = "";
//         var headtext2 = "";
                
        headtext1 = "No.|상태|진단내용|진단일자|전체컬럼수|표준용어일치건수|표준적용율(%)";
        
        headtext1 = headtext1.replace(/[' ']/gi,'');
//         headtext2 = headtext2.replace(/[' ']/gi,'');
        
        SetMergeSheet(msHeaderOnly);
        
        var headers = [
					{Text:headtext1, Align:"Center"},   
//                     {Text:headtext2, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
        	         {Type:"Seq",    Width:50,   SaveName:"ibsSeq",         Align:"Center",  Edit:0},        
                     {Type:"Status", Width:50,   SaveName:"ibsStatus",         Align:"Center",  Edit:0, Hidden:1},     
                     {Type:"Combo",   Width:130,  SaveName:"anaCd",          Align:"Left",    Edit:0}, 
                     {Type:"Text",   Width:130,  SaveName:"anaDtm",           Align:"Left",    Edit:0,Format:"yyyy-MM-dd HH:mm:ss"}, 
                     {Type:"Int",   Width:130,  SaveName:"totColCnt",         Align:"Right",    Edit:0}, 
                     {Type:"Int",   Width:130,  SaveName:"colAplyCnt",         Align:"Right",    Edit:0}, 
                     {Type:"Text",   Width:130,  SaveName:"colAplyRt",         Align:"Right",    Edit:0}, 
                ];
                    
        InitColumns(cols);
    	
        SetColProperty("anaCd", 	${codeMap.niaStndAnaCdibs});
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(dbsditm_sheet);    
    //===========================
   
}







/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function dbsditm_sheet_OnDblClick(row, col, value, CellX, CellY, CellW, CellH) {
//     alert("tbl dbl click");
	if(row < 1) return;
}

function dbsditm_sheet_OnClick(row, col, value, cellx, celly) {
// 	alert("tbl click event");

	return;
}



function dbsditm_sheet_OnSearchEnd(code, message ,StCode, StMsg, Response) {
	if(code < 0) {
		console.log(StCode);
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
// 		//진단결과 조회

	 
	}
}


</script>
</head>

<body>

<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
     
        <div style="clear:both; height:5px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">  
	     <script type="text/javascript">createIBSheet("dbsditm_sheet", "100%", "450px");</script>     
		<div style="clear:both; height:10px;"><span></span></div>
	</div>
	<!-- 그리드 입력 입력 -->
</div>

</body>
</html>