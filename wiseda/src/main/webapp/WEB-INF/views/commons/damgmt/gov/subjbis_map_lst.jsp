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
<title>주제영역 업무분류체계 매핑</title>

<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";

$(document).ready(function() {
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });

        // 저장 Event Bind
        $("#btnSave").click(function(){ 
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
        	
        }).show();
        
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	$(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:1,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"No.|상태|주제영역ID|주제영역논리명|전체경로|업무분류체계ID|업무분류체계명", Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"Text",     Width:100,   SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:300,   SaveName:"subjNm",   	Align:"Left", Edit:0},
                    {Type:"Text",     Width:400,   SaveName:"fullPath",     Align:"Left", Edit:0},
                    {Type:"Text",     Width:200,   SaveName:"bisId",        Align:"Left", Edit:1},
                    {Type:"Text",     Width:400,   SaveName:"bisNm",        Align:"Left", Edit:1}
                ];
                    
        InitColumns(cols);
        
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
        case "Save" :
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;

            var url = "<c:url value="/commons/damgmt/gov/regSubjBisMap.do"/>";
        	var param = "";
        	
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/commons/damgmt/gov/getSubjBisMapList.do" />", param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        
            break;
    }       
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			  doAction("Search");
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}


function grid_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		showMsgBox("INF", "<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		showMsgBox("INF", "<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
	}
	
}




</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">주제영역 업무분류체계 매핑</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역 업무분류체계 매핑">
                
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>
                           <th scope="row"><label for="subjNm"><s:message code="SUBJ.TRRT.NM" /> </label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjNm" id="subjNm" />
                                </span>
                            </td>
                            <th scope="row"><label for="bisNm">업무분류체계명 </label></th>
                            <td>
                                <span class="input_file">
                                <input type="text" name="bisNm" id="bisNm" />
                                </span>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<div class="divLstBtn">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			</div>
			<div class="bt02">
			  <button class="btn_excel_down" id="btnBisDown" name="btnBisDown" onclick='location.href="<c:url value="/bisdown.jsp"/>"'>업무분류체계 목록 다운로드</button>
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>
</body>
</html>