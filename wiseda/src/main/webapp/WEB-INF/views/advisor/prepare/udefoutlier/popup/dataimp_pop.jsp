<!DOCTYPE html>
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="UDF.DATA" /></title><!-- 업무영역 엑셀업로드 -->

<script type="text/javascript">

$(document).ready(function() {
	
	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
	       	//iframe 형태의 팝업일 경우
	       	if ("${search.popType}" == "I") {
	       		parent.closeLayerPop();
	       	} else {
	       		window.close();
	       	}
    });
	
	
	//엑셀 올리기 버튼 셋팅 및 클릭 이벤트 처리...
	$('#btnExcelUp').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		doAction('LoadText');
	});
});

$(window).on('load',function() {
	initDataGrid();
	
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
});


function initDataGrid(){

    with(grid_data){
    	
   		var cfg = {SearchMode:2,Page:100}; 
        SetConfig(cfg);
      
        
        var headText = "";
        
       	headText = "${headerVO.headerText}";
       	
        var headers = [
                    {Text:headText, Align:"Center"}
                ];

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		
        InitHeaders(headers, headerInfo); 
        
        var cols = [];
                    
        //그리드 SaveName 설정
        var HeaderCnt = ${headerVO.colCnt};
        
		var colNm = "";
		
		for(var i = 0; i <= HeaderCnt; i++){
			colNm = "colNm" + i;
			
			cols.push(  {Type:"Text", Width:100,  SaveName:colNm, Align:"Center", Edit:0}  ) ;
			
		}
    
	    //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	    InitColumns(cols);
	    
	    //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	    
	    FitColWidth();  
	    SetExtendLastCol(1); 
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_data);
    //===========================

    doAction("LoadData");
}

function doAction(sAction)
{
        
    switch(sAction)
    {
 	    case "LoadData":  //엑셀업로드
 	      
 	    	grid_data.LoadSearchData('${data}', {Sync:1});
 	        
 	        break;
    }       
}

</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="UDF.DATA" /></div><!-- 엑셀업로드-업무영역 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
    <!-- 팝업 타이틀 끝 -->

<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_data", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>

