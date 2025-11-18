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
	//그리드 초기화
	initsubgrid();
	
	// 엑셀내리기 Event Bind
    $("#ExcelDown").click( function(){ grid_sub_excel(); } );
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_dmnvalue.FitColWidth();
});


function initsubgrid() {

    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.DBMSSCH.LST'/>", Align:"Center"}
                ];
                //No.|DB스키마ID|만료일시|시작일시|DB스키마물리명|DB스키마논리명|DB접속대상ID|DDL대상여부|수집제외여부|DDL대상구분코드|코드전송대상여부|코드자동전송여부|객체설명|객체버전|등록유형코드|작성일시|작성사용자ID

        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",    Width:40,   SaveName:"ibsSeq",    	  	Align:"Center", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"dbSchId",    		Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"expDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1}, 
					{Type:"Text",   Width:100,  SaveName:"strDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1}, 
					{Type:"Text",   Width:300,  SaveName:"dbSchPnm",    	Align:"Center", Edit:0, KeyField:1}, 
					{Type:"Text",   Width:300,  SaveName:"dbSchLnm",    	Align:"Center", Edit:0, KeyField:1}, 
					{Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Center", Edit:0, KeyField:1, Hidden:1}, 
					{Type:"Text",   Width:80,  SaveName:"ddlTrgYn",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Text",   Width:80,  SaveName:"cltXclYn",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Text",   Width:80,  SaveName:"ddlTrgDcd",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Text",   Width:80,  SaveName:"cdSndTrgYn",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Text",   Width:80,  SaveName:"cdAutoSndYn",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Text",   Width:200,  SaveName:"objDescn",    	Align:"Center", Edit:0}, 
					{Type:"Text",   Width:800,  SaveName:"objVers", 	   	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Combo",   Width:80,  SaveName:"regTypCd",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Text",   Width:100,  SaveName:"writDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1}, 
					{Type:"Text",   Width:100,  SaveName:"writUserId",    	Align:"Center", Edit:0, Hidden:1}
                   
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...

      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	initsubgrid();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function grid_sub_excel()
{
	grid_sub.Down2Excel({HiddenColumn:1, Merge:1});
}
</script>

<!-- </head> -->
<!-- <body>     -->
   
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
