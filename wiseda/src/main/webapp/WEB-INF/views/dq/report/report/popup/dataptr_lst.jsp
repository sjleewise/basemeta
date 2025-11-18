<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->

<script type="text/javascript">
//IBSHEET 헤더명
var HeaderText = "No.|"+"${headerVO.headerText}"+"|PK_DATA_YN";
//IBSHEET 헤더 건수
var HeaderCnt = "${headerVO.colCnt}";

$(document).ready(function() {
	//그리드 초기화 
	initGrid();
});

$(window).on('load',function() {
	$(window).resize();

});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid(){
	
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
       
        var headers = [ {Text : HeaderText, Align:"Center"} ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [  
						{Type:"Text",    Width:50,   SaveName:"erDataSno",  Align:"Center", Edit:0}
                       ];   
        
        if(HeaderCnt != ""){
        	 //그리드 SaveName 설정
    		var colNm = "";
    		for(var i=1; i<=HeaderCnt; i++){
    			colNm = "colNm" + i;
    			cols.push(  {Type:"Text", Width:"70",  SaveName:colNm, Align:"Center", Edit:0}  ) ;
    		} 
    		cols.push({Type:"Text",    Width:20,   SaveName:"pkDataYn",  Align:"Center", Edit:0, Hidden:1});
        }
           
        InitColumns(cols);
        FitColWidth();
        SetExtendLastCol(1);    
        
        //추정오류데이터 존재하지 않을경우
        if(HeaderCnt == ""){
       	var Row = DataInsert();
			SetCellValue(Row, "erDataSno", "<s:message code='INQ.DATA.NO.1'/>"); /*조회된 데이터가 없습니다.*/

       }
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);
    //===========================
    	
  	 if(HeaderCnt != ""){
		getDataPattern();
  	 }
}

function getDataPattern(){
	
	var param =  $("#frmSearch").serialize();

	grid_sheet.DoSearch('<c:url value="/dq/report/popup/dataptr_lst.do" />', param);
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	if(grid_sheet.GetCellValue(row, "pkDataYn") != 1) {return;}
	
	var param = "?objId="+"${search.objId}";
    param += "&objDate="+"${search.objDate}".replace(/ /g, ''); 
    param += "&objIdCol=PK_DATA";		  
    param += "&objResTbl=WAM_PRF_RESULT";
    param += "&objErrTbl=WAM_PRF_ERR_DATA_PKDATA";
    param += "&erDataSnoCol=ESN_ER_DATA_SNO";
    param += "&erDataSno="+grid_sheet.GetCellValue(row, "erDataSno");
    param += "&erDataPkSnoCol=ESN_ER_DATA_PK_SNO";
    
	var url = '<c:url value="/dq/report/popup/pkdata_pop.do" />';
 	var popup = OpenWindow(url+param, "PK_DATA", "800", "600", "yes"); 
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
	}
}
</script>

<!-- </head> -->
<!-- <body> -->
<!-- 그리드 입력 입력 -->
<div id="grid_01" class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "480px");</script>            
</div>

<!-- </body> -->
<!-- </html> -->