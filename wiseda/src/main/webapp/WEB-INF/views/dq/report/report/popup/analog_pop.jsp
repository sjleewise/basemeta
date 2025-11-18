<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title></title>

<script type="text/javascript">

var interval = "";

$(document).ready(function() {
        //그리드 초기화 
        initGrid();
        
       //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
       $("div.pop_tit_close").click(function(){
	       	//iframe 형태의 팝업일 경우
	       	if ("${search.popType}" == "I") {
	       		parent.closeLayerPop();
	       	} else {
	       		window.close();
	       	}
       });
});

$(window).on('load',function() {
	$(window).resize();
	doAction("Search");
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});

function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                       {Text:"<s:message code='DQ.HEADER.ANALOG_POP'/>", Align:"Center"}
                   ];
        //No.|분석차수|분석시작일시|분석종료일시|실행상태|실행시간(초)|오류메세지|실행자

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
						{Type:"Seq",    Width:20,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
						{Type:"Int",   Width:40,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0, ColMerge:1},
						{Type:"Date",   Width:70,  SaveName:"anaStrDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
						{Type:"Date",   Width:70,  SaveName:"anaEndDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Combo",   Width:50,  SaveName:"anaStsCd",    Align:"Center", Edit:0},
						{Type:"Text",   Width:40,  SaveName:"anaSec",    Align:"Center", Edit:0}, 
						{Type:"Text",   Width:130,  SaveName:"anaErMsg",    Align:"Center", Edit:0},
						{Type:"Text",   Width:220,  SaveName:"anaUserId",    Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("anaStsCd", 	${codeMap.anaStsCdibs});
        
        InitComboNoMatchText(1, "");

      
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
			var param = "&objId="+"${search.objId}"; 
					param += "&objResTbl="+"${search.objResTbl}";
        	grid_sheet.DoSearch('<c:url value="/dq/report/popup/analog_lst.do" />', param);
       		break;
    }       
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
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
	}
}


</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="ANLY.LOG"/></div><!--분석로그-->

    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
        <div class="stit"><s:message code="ANLY.LOG"/></div><!--분석로그-->

        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" name="objId" id="objId" readonly/>
        </form>
        
		<!-- 그리드 입력 입력 -->
		<div id="grid_01" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
		</div>
		<!-- 그리드 입력 입력 -->
	
		<div style="clear:both; height:5px;"><span></span></div>
	
		<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
		<div style="clear:both; height:5px;"><span></span></div>
	<%-- <%= application.getRealPath("/") %> --%>
	</div>
</div>
</body>
</html>