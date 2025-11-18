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

$(document).ready(function() {
});

$(window).on('load',function() {
	//그리드 초기화 
	initdmnpdtGrid();
// 	$(window).resize();
	

});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
// 	setibsheight($("#grid_01"));
	// dmnpdt_sheet.SetExtendLastCol(1);    
});


function initdmnpdtGrid(){
	
    with(dmnpdt_sheet){
    	
   		var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='BDQ.HEADER.DMNPDTRESULT.DTL'/>", Align:"Center"}
                ];
        //Rank|상태|변수ID|도메인명|판별확률(%)

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                {Type:"Int",   Width:70,   SaveName:"dmnRnk",    	Align:"Center", Edit:0},
                {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"anlVarId",    	Align:"Left", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"dmngLnm",    	Align:"Left", Edit:0},
                {Type:"Float",   Width:100,  SaveName:"dmnPrb",    	Align:"Center", Edit:0, Hidden:0, Format:"#0.0"},
            ];
    
    //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
    InitColumns(cols);
    //콤보 목록 설정...
// 	   SetColProperty("dmngNm", {ComboText: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그', ComboCode: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'});
//	     'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'

    InitComboNoMatchText(1, "");
    
    //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
    
    FitColWidth();
    SetExtendLastCol(1); 
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(dmnpdt_sheet);
    //===========================
}

function getDmnPredictResult(param){
	
	//var param =  $("#frmSearch").serialize();

	dmnpdt_sheet.DoSearch('<c:url value="/advisor/prepare/domain/getnstnddmnpdtresultlist.do" />', param);
}

function dmnpdt_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function dmnpdt_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function dmnpdt_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
// 		setibsheight($("#grid_11"));
	}
}
</script>

<!-- </head> -->
<!-- <body> -->
<!-- 그리드 입력 입력 -->
<div id="grid_11" class="grid_01">
     <script type="text/javascript">createIBSheet("dmnpdt_sheet", "100%", "400px");</script>            
</div>

<!-- </body> -->
<!-- </html> -->