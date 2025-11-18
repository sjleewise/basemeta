<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<html>
<head>
<title></title>

<script type="text/javascript">

//IBSHEET 헤더명
var HeaderText = "No.|"+"${headerVO.headerText}";
//IBSHEET 헤더 건수
var HeaderCnt = "${headerVO.colCnt}";

$(document).ready(function() {
	//탭 초기화....
	//$( "#tabs" ).tabs();
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
     	if ("${headerVO.popType}" == "I") {
     		parent.closeLayerPop();
     	} else {
     		window.close();
     	}
     });
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
});

$(window).resize( function(){
	grid_sheet.FitColWidth();
});

function initGrid(){

	with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
       
        var headers = [ {Text : HeaderText, Align:"Center"} ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [  
						{Type:"Text",    Width:50,   SaveName:"erDataSno",  Align:"Center", Edit:0, Hidden:0}
                       ];   
        
        if(HeaderCnt != ""){
        	 //그리드 SaveName 설정
    		var colNm = "";
    		for(var i=1; i<=HeaderCnt; i++){
    			colNm = "colNm" + i;
    			cols.push(  {Type:"Text", Width:"70",  SaveName:colNm, Align:"Center", Edit:0}  ) ;
    		} 
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

	grid_sheet.DoSearch('<c:url value="/dq/report/popup/pk_dataptr_lst.do" />', param);
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
</head>

<body>
	<div class="pop_tit" >
		<!-- 팝업 타이틀 시작 -->
		<div class="pop_tit_icon"></div>
	    <div class="pop_tit_txt"><s:message code="PK.DATA.PTRN.INQ"/></div><!--PK데이터패턴조회-->
	    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
	    <!-- 팝업 타이틀 끝 -->
	
         
		<form id="frmSearch" name="frmSearch" method="post">
	     <input  type="hidden"  name="objId" id="objId" value="${search.objId}" />
	     <input  type="hidden"  name="objIdCol" id="objIdCol" value="${search.objIdCol}" />
	     <input  type="hidden"  name="objResTbl" id="objResTbl" value="${search.objResTbl}" />
	     <input  type="hidden"  name="objErrTbl" id="objErrTbl" value="${search.objErrTbl}" />
	     <input  type="hidden"  name="erDataSnoCol" id="erDataSnoCol" value="${search.erDataSnoCol}" />
	     <input  type="hidden"  name="erDataPkSnoCol" id="erDataPkSnoCol" value="${search.erDataPkSnoCol}" />
	     <input  type="hidden"  name="erDataSno" id="erDataSno" value="${search.erDataSno}" />
	     <input  type="hidden"  name="objDate" id="objDate" value="${search.objDate}" />
	     <input  type="hidden"  name="objGb" id="objGb" value="${search.objGb}" />
	     <input  type="hidden"  name="colNm" id="colNm" value="${search.colNm}" />
	     <input  type="hidden"  name="colCnt" id="colCnt" value="${search.colCnt}" />
		</form>
	
	<!-- 팝업 내용 시작 -->
	   <div class="pop_content">
			<div id="tabs">
				<ul>
					<li id="tab-01"><a href="#tabs-01"><s:message code="DATA.PTRN.INQ"/></a></li><!--데이터패턴조회-->
				</ul>
				<div id="tabs-01">
			    <!-- 그리드 입력 입력 -->
					<div id="grid_01" class="grid_01">
					     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "480px");</script>            
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>