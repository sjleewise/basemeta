<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%-- <%@ page import="kr.wise.commons.WiseMetaConfig" %> --%>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	//그리드 초기화 
// 	initColGrid();
	
	$("#btnSubSearch").hide();
	$("#btnSubTreeNew").hide();
	$("#btnSubDelete").hide();
	
        // 엑셀내리기 Event Bind
        $("#btnSubExcelDown").click( function(){ doActionCol("Down2Excel"); } );

});

$(window).on('load',function() {
	//그리드 초기화 
// 	initColGrid();
});


function initColGrid()
{
    
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.PDMCOL.LST'/>";

        var headers= [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",			Align:"Center", Edit:0},
                    {Type:"Text",    Width:100, SaveName:"pdmColId",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"dbConnTrgId",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"dbConnTrgLnm",	Align:"Left", 	Edit:0},
                    {Type:"Text",    Width:150, SaveName:"dbConnTrgPnm",	Align:"Left", 	Edit:0}, 
                    {Type:"Text",    Width:150, SaveName:"dbSchId",			Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"dbSchLnm",		Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150, SaveName:"dbSchPnm",		Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100, SaveName:"pdmTblId",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180, SaveName:"pdmTblLnm",		Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:180, SaveName:"pdmTblPnm",		Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150, SaveName:"pdmColSno",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"pdmColPnm",		Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150, SaveName:"pdmColLnm",		Align:"Left",   Edit:0},
                    {Type:"Text",    Width:50,  SaveName:"colOrd",			Align:"Center", Edit:0},
                    {Type:"Text",    Width:100, SaveName:"dataType",		Align:"Center", Edit:0},
                    {Type:"Text",    Width:50,  SaveName:"dataLen",			Align:"Center", Edit:0},
                    {Type:"Text",    Width:50,  SaveName:"dataScal",		Align:"Center", Edit:0},
                    {Type:"Combo",   Width:50,  SaveName:"pkYn",			Align:"Center", Edit:0},
                    {Type:"Text",    Width:50,  SaveName:"pkOrd",			Align:"Center", Edit:0},
                    {Type:"Combo",   Width:100, SaveName:"nonulYn",			Align:"Center", Edit:0},
                    {Type:"Text",    Width:100, SaveName:"defltVal",		Align:"Center", Edit:0},
                    {Type:"Combo",   Width:50,  SaveName:"regTypCd",		Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",    Width:100, SaveName:"rqstUserId",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"rqstUserNm",		Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120, SaveName:"rqstDtm",			Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:50,  SaveName:"objVers",			Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",    Width:300, SaveName:"objDescn",		Align:"Left",   Edit:0}
                ]; 
                    
        InitColumns(cols);

        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        SetColProperty("pkYn", 	{ComboCode:"N|Y", 	ComboText:"N|Y"});
        SetColProperty("nonulYn", 	{ComboCode:"N|Y", 	ComboText:"N|Y"});
        
        InitComboNoMatchText(1, "");
        
//         FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
   
}

//화면상의 모든 액션은 여기서 처리...
function doActionCol(sAction)
{
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
        	var param = $('#frmSearch').serialize();
        	col_sheet.DoSearch("<c:url value="/dq/model/getpdmcollist.do" />", param);
        	break;

        case "Down2Excel":  //엑셀내려받기
            col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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
function col_sheet_OnDblClick(row, col, value, cellx, celly) { 
// 	alert(1);
	if(row < 1) return;
	
// 	alert(col_sheet.GetCellText(1, "sditmId"));
	
// 	$("#frmInput2").reset();

    ibs2formmapping(row, $("form#frmInput2"), col_sheet);
    
    $("form[name=frmInput2] #rqstDtm").val(col_sheet.GetCellText(row, "rqstDtm"));
    
    $("form#colfrmSearch input[name=pdmColId]").val(col_sheet.GetCellValue(row, "pdmColId"));
    
    
//     doActionColH("Search");
    
    $("#tab-4 a").click();
}

function col_sheet_OnClick(row, col, value, cellx, celly) {
//     alert("clickevent");
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
    ibs2formmapping(row, $("form#frmInput2"), col_sheet);
    
    $("form[name=frmInput2] #rqstDtm").val(col_sheet.GetCellText(row, "rqstDtm"));
    
    $("form#colfrmSearch input[name=pdmColId]").val(col_sheet.GetCellValue(row, "pdmColId"));
    
    doActionColH("SearchOne");

}

// function col_sheet_OnSaveEnd(code, message) {
// 	//alert(code);
// 	if (code == 0) {
// 		alert("저장 성공했습니다.");
// 	} else {
// 		alert("저장 실패했습니다.");
// 	}
// }

function col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		 ibs2formmapping(1, $("form#frmInput2"), col_sheet);
		
		 $("form#colfrmSearch input[name=pdmColId]").val(col_sheet.GetCellValue(1, "pdmColId"));
		 
		    $("form[name=frmInput2] #rqstDtm").val(col_sheet.GetCellText(1, "rqstDtm"));
		
		doActionColH("Search");
		
		
		//$('#btnColReset').click();
// 		alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
	
}


</script>
<!-- </head> -->
<!-- <body> -->

<div id="col_search_div">
<!-- 검색조건 입력폼 -->
<!--         <div class="stit">검색조건</div>  -->
        
        <form id="colfrmSearch" name="colfrmSearch" method="post">
        <input type="hidden" name="pdmColId" />
        	<input type="hidden" id="rqstNo" name="rqstNo" />
        	<input type="hidden" id="rqstSno" name="rqstSno" />
        </form>
</div>

<tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div id="grid_99" class="grid_01">
     <script type="text/javascript">createIBSheet("col_sheet", "100%", "400px");</script>            
</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- </body> -->
<!-- </html> -->