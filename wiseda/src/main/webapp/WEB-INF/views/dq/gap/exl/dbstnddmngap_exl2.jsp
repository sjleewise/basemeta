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
	initDbDmnGrid();
	
	
});


$(window).resize(
    
    function(){
                
    	//setibsheight($("#grid_01"));
    }
);


function initDbDmnGrid()
{
    
    with(dbdmn_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext1 = "";
//      var headtext2 = "";
             
      headtext1 = "<s:message code='META.HEADER.STND.GAP.RESULT.LST'/>";
     //No.|상태|진단일시|총건수|일치건수|일치율(%)|불일치건수|불일치율(%)]
     
     headtext1 = headtext1.replace(/[' ']/gi,'');
//      headtext2 = headtext2.replace(/[' ']/gi,'');
     
     SetMergeSheet(msHeaderOnly);
     
     var headers = [
					{Text:headtext1, Align:"Center"},   
//                  {Text:headtext2, Align:"Center"}
             ];
     
     var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
     
     InitHeaders(headers, headerInfo); 

     var cols = [                        
         {Type:"Seq",    Width:70,   SaveName:"ibsSeq",         Align:"Center",  Edit:0},       
         {Type:"Status", Width:50,   SaveName:"ibsStatus",         Align:"Center",  Edit:0, Hidden:1},    
         {Type:"Text",   Width:250,  SaveName:"anaDtm", 	   Align:"Center",  Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},                     
         {Type:"Text",   Width:300,  SaveName:"totColCnt",          Align:"Left",    Edit:0}, 
         {Type:"Text",   Width:300,  SaveName:"colAplyCnt",           Align:"Left",    Edit:0, Hidden:1}, 
         {Type:"Text",   Width:300,  SaveName:"colAplyRt",         Align:"Left",    Edit:0, Hidden:1}, 
         {Type:"Text",   Width:300,  SaveName:"colErrCnt",         Align:"Left",    Edit:0}, 
         {Type:"Text",   Width:300,  SaveName:"colErrRt",         Align:"Left",    Edit:0}, 
     ];
                    
        InitColumns(cols);
        
        
        InitComboNoMatchText(1, "");
        

        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(dbdmn_sheet);    
    //===========================
   
}







/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function dbdmn_sheet_OnDblClick(row, col, value, CellX, CellY, CellW, CellH) {
//     alert("tbl dbl click");
	if(row < 1) return;
}

function dbdmn_sheet_OnClick(row, col, value, cellx, celly) {
// 	alert("tbl click event");

	return;
}



function dbdmn_sheet_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
// 		for(var i = dbdmn_sheet.HeaderRows(); i <= dbdmn_sheet.RowCount() + dbdmn_sheet.HeaderRows() - 1; i++) {
			
// 			dbdmn_sheet.SetCellFontColor(i, "gapStatus", "#FF0000");
			
// 			var pdmColPnm = dbdmn_sheet.GetCellValue(i, "pdmColPnm"); 
			
// 			pdmColPnm = pdmColPnm.replace(/[0-9,_]+$/g, ''); //컬럼명끝 _ 숫자  제거
				
// 			if(pdmColPnm != dbdmn_sheet.GetCellValue(i, "sditmPnm")) { 			
// 				dbdmn_sheet.SetCellFontColor(i, "pdmColPnm", "#FF0000");
// 			} 	
			
// 			if(dbdmn_sheet.GetCellValue(i, "pdmDataType") != dbdmn_sheet.GetCellValue(i, "sditmDataType")) { 			
// 				dbdmn_sheet.SetCellFontColor(i, "pdmDataType", "#FF0000");
// 			} 	
			
// 			if(dbdmn_sheet.GetCellValue(i, "pdmDataType") != dbdmn_sheet.GetCellValue(i, "sditmDataType")) { 			
// 				dbdmn_sheet.SetCellFontColor(i, "pdmDataType", "#FF0000");
// 			} 	
			
// 			if(dbdmn_sheet.GetCellValue(i, "pdmDataLen") != dbdmn_sheet.GetCellValue(i, "sditmDataLen")) { 			
// 				dbdmn_sheet.SetCellFontColor(i, "pdmDataLen", "#FF0000");
// 			} 	
			
// 			if(dbdmn_sheet.GetCellValue(i, "pdmDataScal") != dbdmn_sheet.GetCellValue(i, "dbcDataScal")) { 			
// 				dbdmn_sheet.SetCellFontColor(i, "pdmDataScal", "#FF0000");
// 			} 	
// 		}
	}


	
}







</script>
</head>

<body>

<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">  
	     <script type="text/javascript">createIBSheet("dbdmn_sheet", "100%", "650px");</script>     
		<div style="clear:both; height:10px;"><span></span></div>
	</div>
	<!-- 그리드 입력 입력 -->
</div>

</body>
</html>