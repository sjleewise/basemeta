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
// 	initsubgrid_sditmchange();
	initgrid_sub_govstwdmapchange();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_symnchange.FitColWidth();
});


function initgrid_sub_govstwdmapchange() {

    with(grid_sub_govstwdmapchange){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"No.|상태|선택|공통표준단어ID|제정차수|공통표준단어명|공통표준단어영문약어명|공통표준단어영문명|공통표준단어설명|형식단어여부|공통표준도메인분류명|이음동의어 목록|금칙어 목록|기관표준단어ID|기관표준단어논리명|기관표준단어물리명|등록유형코드|등록일자|등록자ID|등록자명", Align:"Center"}
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:0, Hidden:1, Sort:0},   
                    
//                     {Type:"Combo",   Width:130,  SaveName:"stndAsrt",    Align:"Left", Edit:0, KeyField:1,UpdateEdit:0},
                    {Type:"Text",   Width:200,  SaveName:"govStwdId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"enactDgr",    Align:"Left", Edit:0, KeyField:1},
                    {Type:"Text",   Width:400,  SaveName:"govStwdLnm",    Align:"Center", Edit:0 , KeyField:1},
                    {Type:"Text",   Width:200,  SaveName:"govStwdPnm",    Align:"Center", Edit:0, KeyField:1},
                    {Type:"Text",   Width:130,  SaveName:"engMean",    Align:"Center", Edit:0},
                    {Type:"Text",   Width:400,  SaveName:"objDescn",    Align:"Left", Edit:0, KeyField:1},          
                    {Type:"Combo",   Width:80,  SaveName:"dmnYn",    Align:"Center", Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"govDmngLnm",     Align:"Center", Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"synonyms",     Align:"Center", Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"prohibits",      Align:"Center",   Edit:0},
                    
                    {Type:"Text",   Width:200,  SaveName:"stwdId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"stwdLnm",    Align:"Center", Edit:0, KeyField:0},
                    {Type:"Text",   Width:200,  SaveName:"stwdPnm",    Align:"Center", Edit:0, KeyField:0},
                    
                    {Type:"Combo",   Width:30,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Date",   Width:40,  SaveName:"regDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:40,  SaveName:"regUserId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"regUserNm",    Align:"Left", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
//         SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});     	
	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});
        //SetColHidden("rqstUserNm",1);

        InitComboNoMatchText(1, "");
        FitColWidth();  
        
        SetExtendLastCol(1);  
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_govstwdmapchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_symnchange.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//표준용어 - 변경이력 그리드 초기화
// 	initsubgrid_sditmchange();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_govstwdmapchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_govstwdmapchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_govstwdmapchange_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_govstwdmapchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
