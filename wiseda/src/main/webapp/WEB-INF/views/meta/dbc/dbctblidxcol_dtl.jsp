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

		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_dbctblidxcol.FitColWidth();
});


function initsubgrid_dbctblidxcol() {

    with(grid_sub_dbctblidxcol){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.DBCTBLIDXCOL.DTL'/>", Align:"Center"}
                    /* No.|DB스키마ID|DB스키마명|DBC테이블명|DBC인덱스명|DBC인덱스컬럼명|DBC인덱스컬럼한글명|순서|정렬타입|데이터타입|데이터자리수|데이터길이|데이터소수점|설명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:130,   SaveName:"dbSchId", 		Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"dbSchLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"dbcTblNm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"dbcIdxNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dbcIdxColNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbcIdxColKorNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"ord",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"sortType",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataType",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataPnum",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataLen",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataPnt",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:300,   SaveName:"descn",	 	Align:"Left", Edit:0, Hidden:0}
						
                   
                ];
                    
        InitColumns(cols);

        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dbctblidxcol);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_dbctblidxcol.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	initsubgrid_dbctblidxcol();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_dbctblidxcol_OnDblClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	//선택한 상세정보를 가져온다...
	

}

function grid_sub_dbctblidxcol_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	
	

}


function grid_sub_dbctblidxcol_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_dbctblidxcol", "100%", "250px")</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
