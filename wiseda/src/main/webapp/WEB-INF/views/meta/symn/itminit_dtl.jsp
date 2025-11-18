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
	initsubgrid_dmnvalue();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_dmnvalue.FitColWidth();
});


function initsubgrid_dmnvalue() {

    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
   //  	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(msHeaderOnly);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.ITMINIT.DTL.1'/>"}
                    ,{Text:"<s:message code='META.HEADER.ITMINIT.DTL.2'/>", Align:"Center"}
                ];
                //No.|소스|소스|소스|소스|소스|타겟|타겟|타겟|타겟|타겟
                //No.|항목한글명|항목영문명|도메인명|단어한글명|단어영문명|항목한글명|항목영문명|도메인명|단어한글명|단어영문명
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
				{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
				{Type:"Text",   Width:100,  SaveName:"srcItemKnm",		Align:"Left", Edit:0, Hidden:1},
				{Type:"Text",   Width:100,  SaveName:"srcItemEnm",		Align:"Center", Edit:0, Hidden:1},
				{Type:"Text",   Width:100,  SaveName:"domainKnm",		Align:"Left", Edit:0, Hidden:1},
				{Type:"Text",   Width:200,  SaveName:"srcTrmKnm",		Align:"Left", Edit:0},
				{Type:"Text",   Width:200,  SaveName:"srcTrmEnm",		Align:"Left", Edit:0},
				{Type:"Text",   Width:100,  SaveName:"tgtItemKnm",		Align:"Left", Edit:0, Hidden:1},
				{Type:"Text",   Width:100,  SaveName:"tgtItemEnm",		Align:"Left", Edit:0, Hidden:1},
				{Type:"Text",   Width:100,  SaveName:"domainKnm",		Align:"Left", Edit:0, Hidden:1},
				{Type:"Text",   Width:200,  SaveName:"tgtTrmKnm",		Align:"Left", Edit:0},
				{Type:"Text",   Width:200,  SaveName:"tgtTrmEnm",		Align:"Left", Edit:0}
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
//	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
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
//     grid_sub_dmnvalue.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//initsubgrid_dmnvalue();
	
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
	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
