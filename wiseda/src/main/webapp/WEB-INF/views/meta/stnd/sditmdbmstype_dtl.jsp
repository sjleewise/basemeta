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
	initsubgrid_sditmdbmstype();

});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_sditmdbmstype.FitColWidth();
});


function initsubgrid_sditmdbmstype() {

    with(grid_sub_sditmdbmstype){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};

        SetConfig(cfg);

        var headerText = "<s:message code='META.HEADER.SDITMDBMSTYPE.DTL'/>";

        headerText = "No.|표준용어논리명|표준용어물리명|논리데이터타입|논리데이터길이|논리소수점|DBMS|물리데이터타입|물리데이터길이|물리소수점";
        
        var headers = [
	                    {Text: headerText, Align:"Center"}
	                  ];
	             
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                     {Type:"Seq",    Width:80,   SaveName:"ibsSeq",    	 Align:"Center", Edit:0},             
                     {Type:"Text",   Width:170,  SaveName:"sditmLnm",    Align:"Center", Edit:0, Hidden:0},
                     {Type:"Text",   Width:170,  SaveName:"sditmPnm",    Align:"Center", Edit:0, Hidden:0},                     
                     {Type:"Text",   Width:120,  SaveName:"lgcDataType", Align:"Center", Edit:0, Hidden:0},
                     {Type:"Text",   Width:120,  SaveName:"lgcDataLen",  Align:"Center", Edit:0, Hidden:0},
                     {Type:"Text",   Width:120,  SaveName:"lgcDataScal", Align:"Center", Edit:0, Hidden:0},
                     {Type:"Text",   Width:120,  SaveName:"dbmsTypNm",   Align:"Center", Edit:0, Hidden:0},
                     {Type:"Text",   Width:120,  SaveName:"phyDataType", Align:"Center", Edit:0, Hidden:0},
                     {Type:"Text",   Width:120,  SaveName:"phyDataLen",  Align:"Center", Edit:0, Hidden:0},
                     {Type:"Text",   Width:120,  SaveName:"phyDataScal", Align:"Center", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
 	    //SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
 	    //SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});

        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_sditmdbmstype);    
    //===========================
  	    	
}

$(window).on('load',function() {
	
	initsubgrid_sditmdbmstype();
	
});
	 


/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_sditmdbmstype_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_sditmdbmstype_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}

function grid_sub_sditmdbmstype_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_sditmdbmstype", "100%", "250px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
