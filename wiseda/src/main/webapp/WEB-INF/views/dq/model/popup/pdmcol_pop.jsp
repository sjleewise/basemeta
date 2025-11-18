<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title><s:message code="CLMN.INQ" /></title><!-- 컬럼 검색 -->

<script type="text/javascript">

var popRqst = "${search.popRqst}";

$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
	       
    //그리드 초기화 
//     initGrid();
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });
                  
    if (popRqst == 'Y') {
        // 적용 Event Bind
        $("#popApply").click(function(){ 
        	if(checkDelIBS (grid_sheet, "<s:message code="ERR.APPLY" />")) {
				doAction("Apply");
	    	}
		}).show();
    }
    
    
  //폼 초기화 버튼 초기화...
	$('#popReset').click(function(event){
		event.preventDefault();
// 		alert("초기화버튼");
		$("form[name=frmSearch]")[0].reset();
	}).hide();
  
	//주제영역 팝업 
    $('#subjSearchPop').click(function(event){
    	
	    event.preventDefault();	//브라우저 기본 이벤트 제거...
	
	
    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
    	
    	param += "&lecyDcd=TO";  
    	
		var popwin = OpenModal(url+"?"+param, "subjR7Pop",  600, 400, "no");  
		popwin.focus();
		
	});
                  
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

    //======================================================
    // 셀렉트 박스 초기화
    //======================================================
        
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
//     	alert(1);
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
    
  //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    setautoComplete($("#frmSearch #pdmColLnm"), "PDMCOL");
    
    /* double_select(subjPdmTblJson, $("#subjId"));
	$('select', $("#subjId").parent()).change(function(){
		double_select(subjPdmTblJson, $(this));
	}); */
    
    
});
//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
		 
	//그리드 초기화
	initGrid();
		
	$(window).resize();
// 	doAction("Search");

	if($("#pdmTblLnm").val() != '') {
		$("#pdmTblLnm").attr("readOnly", true);
	}
	if($("#pdmColLnm").val() != '') {
		$("#pdmColLnm").attr("readOnly", true);
	}
});

// EnterkeyProcess("Search");

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
        
        var headText = "<s:message code='META.HEADER.PDMCOL.POP'/>";
        
        //headText = "No.|컬럼ID|주제영역ID|주제영역명|주제영역물리명|물리테이블ID|테이블명|테이블물리명|컬럼명|컬럼물리명|설명|버전|작성일시|작성사용자ID|작성자명"
       
        var headers = [
                       {Text: headText, Align:"Center"}
                   ];
                
                
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",   Width:100, SaveName:"pdmColId"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:10,  SaveName:"subjId",     Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:220, SaveName:"subjLnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:10,  SaveName:"subjPnm", 	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:10,  SaveName:"pdmTblId",     Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:120, SaveName:"pdmTblPnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:10,  SaveName:"pdmTblLnm", 	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:120, SaveName:"pdmColLnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:120, SaveName:"pdmColPnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:100, SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"objVers"   ,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"frsRqstUserId", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"rqstDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},
                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
        
        InitComboNoMatchText(1, "");
                
        FitColWidth();
        
//         SetExtendLastCol(1);    
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
        	
        	if($("#subjLnm").val() == "") {
        		//주제영역을 입력하세요.
        		showMsgBox("ERR","<s:message code='SUBJ.INPUT' />");
        		return;
        	}       
        	
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/model/selectPdmColList.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
            
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
    }       
}
 

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {  

	
	var retjson = jQuery.parseJSON(ret);
	
	//var fullPath = retjson.sysAreaLnm + ">" + retjson.fullPath;
	
	var fullPath = retjson.fullPath;
	
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
	$("#frmSearch #subjLnm").val(fullPath);
	
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
	
	
	
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		parent.returnPdmColPop(JSON.stringify(retjson));
	} else {
		opener.returnPdmColPop(JSON.stringify(retjson));
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
    return;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    if(row < 1) return;
    
    
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
        alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
        alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
	}
	
}





</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="CLMN.INQ" /></div><!-- 컬럼 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PHYC.CLMN.INQ' />"><!-- 물리컬럼조회 -->
                   <caption><s:message code="PHYC.CLMN.INQ" /></caption><!-- 물리컬럼조회 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>       
                       <tr>
                       	   <th scope="row" class="th_require"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th>  <!-- 주제영역명 -->                       
						   <td colspan="3">
                                <span class="input_file"> 
                                	<input type="text" name="subjLnm" id="subjLnm" readonly style="width:75%;" value="${search.subjLnm}" />
                               		<button class="btnDelPop" >삭제</button>
	                                <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                           </td>                                             
                       </tr>                     
                       <tr>                               
                           <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                           <td >
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" value="${search.pdmTblLnm}" />
                           </td>
                      
                           <th scope="row"><label for="pdmColLnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                           <td >
                                <input type="text" name="pdmColLnm" id="pdmColLnm" value="${search.pdmColLnm}"/>
                           </td>
                      </tr>
                      
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.POP' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "370px");</script>             
	</div>
	<!-- 그리드 입력 입력 -->
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>