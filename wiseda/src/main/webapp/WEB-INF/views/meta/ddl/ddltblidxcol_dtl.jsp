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
	
	$("#btnIdxColExcelDown").click(function(){
		
		doActionIdxCol("Down2Excel");
	}).show();
	
	$("#divLstBtn").show();

	initsubgrid_ddltblidxcol();	
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){

	//setibsTabHeight($("#grid_idx"), 0);  
});


function initsubgrid_ddltblidxcol() {

    with(grid_sub_ddltblidxcol){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        var headers = [
                    {Text:"<s:message code='META.HEADER.DDLTBLIDXCOL.DTL'/>", Align:"Center"}
                    /* No.|DDL인덱스명|DDL인덱스컬럼ID|DDL인덱스컬럼물리명|DDL인덱스컬럼논리명|DDL컬럼ID|DDL인덱스컬럼순서|정렬유형|데이터타입|데이터길이|NOTNULL여부|DEFAULT값|요청번호|요청일련번호|요청상세일련번호|설명|버전|최초요청일시|최초요청사용자명|요청일시|요청사용자명|승인일시|승인사용자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,    SaveName:"ibsSeq",		    Align:"Center", Edit:0},
					{Type:"Text",   Width:150,   SaveName:"ddlIdxPnm",	 	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
					{Type:"Text",   Width:40,    SaveName:"ddlIdxColId", 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,   SaveName:"ddlIdxColPnm", 	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
					{Type:"Text",   Width:150,   SaveName:"ddlIdxColLnm",   Align:"Left", Edit:0, Hidden:0, ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"ddlColId",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:120,   SaveName:"ddlIdxColOrd",	Align:"Right", Edit:0, Hidden:0},
					{Type:"Combo",  Width:80,    SaveName:"sortTyp",	 	Align:"Center", Edit:0, Hidden:0 },
					{Type:"Text",   Width:100,   SaveName:"dataType",	 	Align:"Center", Edit:0, Hidden:1 },
					{Type:"Text",   Width:100,   SaveName:"dataLen",	 	Align:"Center", Edit:0, Hidden:1 },
					{Type:"Combo",  Width:100,   SaveName:"nonulYn",	 	Align:"Center", Edit:0, Hidden:1 },
					{Type:"Text",   Width:100,   SaveName:"defltVal",	 	Align:"Center", Edit:0, Hidden:1 },
					{Type:"Text",   Width:40,    SaveName:"rqstNo",	 	    Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:40,    SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:40,    SaveName:"rqstDtlSno",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:300,   SaveName:"objDescn",	 	Align:"Left",   Edit:0, Hidden:0},
					{Type:"Text",   Width:80,    SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:30,    SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:30,    SaveName:"frsRqstUserNm",	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:90,    SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
					{Type:"Text",   Width:90,    SaveName:"aprvUserNm",     Align:"Left",   Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
        SetColProperty("sortTyp", 	${codeMap.idxColSrtOrdCdibs});
        SetColProperty("nonulYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
      //콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
//         FitColWidth();
        SetSheetHeight(250);
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_ddltblidxcol);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddltblidxcol.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
 	//initsubgrid_ddltblidxcol();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_ddltblidxcol_OnDblClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	//선택한 상세정보를 가져온다...
	

}

function grid_sub_ddltblidxcol_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	
	

}


function grid_sub_ddltblidxcol_OnSearchEnd(code, message, stCode, stMsg) {
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
 
<!-- 버튼영역  -->
<div class="divLstBtn" style="padding-right: 10px">
	<div class="bt03"></div>
	<div class="bt02">
		<button class="btn_excel_down" id="btnIdxColExcelDown" name="btnIdxColExcelDown"><s:message code="EXCL.DOWNLOAD" /></button><!-- 엑셀 내리기 -->
	</div>
</div>
<!-- 버튼영역  --> 
 
     
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div id="grid_idx" class="grid_01" >
	     <script type="text/javascript">createIBSheet("grid_sub_ddltblidxcol", "100%", "350px")</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
