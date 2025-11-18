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
<title>비공개사유 검색</title> <!-- 주제영역조회 -->

<script type="text/javascript">

$(document).ready(function() {
	    //버튼 초기화...
    	$("#popReset").hide();
    	$("#popExcelDown").hide();
        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function() {
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        });
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	$(window).resize();
	doAction("Search");
});

$(window).resize(function(){
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
   	setibsheight($("#grid_01"));
});


function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.NOPENRSN.SEARCH.POP'/>";
		//NO.|비공개사유코드|비공개사유
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:30,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"codeCd",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:250, Height: 100,  SaveName:"codeLnm",   	Align:"Left", Edit:0, Wrap:1}
                ];
                    
        InitColumns(cols);
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
        	var param = "";
        	grid_sheet.DoSearch('<c:url value="/meta/mta/popup/getNopenRsnList.do" />', param);
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	var retjson = grid_sheet.GetRowJson(row);
	
	//메타데이터 테이블, 컬럼에 사용
 	if ("${sFlag}" == "COL") {
		//iframe 형태의 팝업일 경우
		if ("${search.popType}" == "I") {
			
			parent.returnNopenRstPopCol(JSON.stringify(retjson));
			
//	 		parent.closeLayerPop();
		} else {
			
			opener.returnNopenRstPopCol(JSON.stringify(retjson));
	 		window.close();
		}
	} else { 
		
		//iframe 형태의 팝업일 경우
		if ("${search.popType}" == "I") {
			
			parent.returnNopenRstPop(JSON.stringify(retjson));
	// 		parent.closeLayerPop();
		} else {
			opener.returnNopenRstPop(JSON.stringify(retjson));
	// 		window.close();
		}
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
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
    <div class="pop_tit_txt">비공개사유 검색</div> <!-- 주제영역 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div style="clear:both; height:5px;"><span></span></div>
        <div class="tb_comment">- 「공공기관의 정보공개에 관한 법률」 제9조에 따른 비공개 대상 정보 중 택일하여 선택</div>
		<div style="clear:both; height:10px;"><span></span></div>
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "260px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
	
	<div style="clear:both; height:5px;"><span></span></div>
	
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div style="clear:both; height:5px;"><span></span></div>
	</div>
</body>
</html>