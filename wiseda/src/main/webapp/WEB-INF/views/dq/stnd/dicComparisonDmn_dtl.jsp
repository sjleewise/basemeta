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
// 	initsubgrid_diccomparison();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_diccomparisonDmn.FitColWidth();
});


function initsubgrid_diccomparisonDmn() {

    with(grid_sub_diccomparisonDmn){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.DIC.COMPARISON.DMN.DTL'/>", Align:"Center"}
                ];
                //No.|개체유형|개체ID|개체명|개체논리명|개체물리명|도메인ID|비고
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
//                  {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
//                  {Type:"CheckBox", Width:60, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
//                     {Type:"Combo",   Width:100,  SaveName:"objType",    Align:"Center", Edit:0, Hidden:0},
                	{Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0},
                	{Type:"Text",   Width:200,   SaveName:"dmnLnm", 		Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:200,   SaveName:"dmnPnm", 		Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:200,   SaveName:"lnmCriDs",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:200,   SaveName:"pnmCriDs",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",   Width:200,   SaveName:"dmngId",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Combo",   Width:130,   SaveName:"infotpId",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:130,   SaveName:"dataType",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"dataLen",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"dataScal",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"objDescn",	 	Align:"Center", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
//       	SetColProperty("objType", 	{ComboCode:"STWD|INFO|SDITM|TABLE|DMN|SYMN", 	ComboText:"<s:message code="STRD.WORD" />|<s:message code="INFO.TYPE" />|<s:message code="STRD.TERMS" />|<s:message code="TBL" />|<s:message code="DMN" />|<s:message code="SIMIWORD"/>"});
      	SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
      	
      	SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		
      	/* 표준단어|인포타입|표준용어|테이블|도메인|유사어 */
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetSheetHeight("250");
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_diccomparisonDmn);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_diccomparisonDmn.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//표준용어 - Where used 그리드 초기화
// 	initsubgrid_diccomparison();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_diccomparisonDmn_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
	var objId = grid_sub_diccomparisonDmn.GetCellValue(row, "objId");	
	if (grid_sub_diccomparisonDmn.GetCellValue(row, "objType") == 'STWD') {
		window.open().location.href = "<c:url value="/dq/stnd/stwd_lst.do"/>"+"?objId="+objId;	
	} else if (grid_sub_diccomparisonDmn.GetCellValue(row, "objType") == 'DMN') {
		window.open().location.href = "<c:url value="/dq/stnd/dmn_lst.do"/>"+"?objId="+objId;
	} else if (grid_sub_diccomparisonDmn.GetCellValue(row, "objType") == 'SDITM') {
		window.open().location.href = "<c:url value="/dq/stnd/sditm_lst.do"/>"+"?objId="+objId;
	} else if (grid_sub_diccomparisonDmn.GetCellValue(row, "objType") == 'TABLE') {
		window.open().location.href = "<c:url value="/dq/model/pdmtbl_lst.do"/>"+"?pdmTblId="+objId;
	} else if (grid_sub_diccomparisonDmn.GetCellValue(row, "objType") == 'INFO') {
// 		location.href = "stwd_lst.do?objId="+objId;
	} else if (grid_sub_diccomparisonDmn.GetCellValue(row, "objType") == 'SYMN') {
// 		window.open().location.href = "<c:url value="/dq/stnd/symn_lst.do"/>"+"?objId="+objId;
	}
}

function grid_sub_diccomparisonDmn_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_diccomparisonDmn_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_diccomparisonDmn", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
