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
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_symnchange.FitColWidth();
});


function initsubgrid_symnchange() {

    with(grid_sub_symnchange){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.ADMIN.SYMN.LST'/>", Align:"Center"}
                    /* No.|상태|선택|유사어ID|유사어논리명|유사어물리명|유사어구분코드|표준단어ID|대체어논리명|대체어물리명|
                    	설명|버전|등록유형코드|최초요청일시|최초요청사용자ID|최초요청사용자명|요청일시|요청사용자ID|승인일시|승인사용자ID */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:70,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Combo",   Width:100,  SaveName:"stndAsrt",    Align:"Center", Edit:0, KeyField:1,UpdateEdit:0},
                    {Type:"Text",   Width:40,  SaveName:"symnId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"symnLnm",    Align:"Left", Edit:0, Hidden:0 ,KeyField:1}, 
                    {Type:"Text",   Width:200,  SaveName:"symnPnm",    Align:"Left", Edit:0, Hidden:0 ,KeyField:1}, 
                    {Type:"Combo",   Width:100,  SaveName:"symnDcd",    Align:"Center", Edit:0, Hidden:0 ,KeyField:1}, 
                    {Type:"Text",   Width:40,  SaveName:"stwdId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:200,  SaveName:"sbswdLnm",    Align:"Left", Edit:0, Hidden:0 ,KeyField:0}, 
                    {Type:"Text",   Width:200,  SaveName:"sbswdPnm",    Align:"Left", Edit:0, Hidden:0 ,KeyField:0},
                    {Type:"Text",   Width:400,  SaveName:"objDescn",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:50,  SaveName:"objVers",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Combo",   Width:50,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Date",   Width:150,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:40,  SaveName:"frsRqstUserId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"frsRqstUserNm",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",  Width:100,  SaveName:"rqstDtm",     Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:30,  SaveName:"rqstUserId",    Align:"Left", Edit:0, Hidden:0},          
                    {Type:"Date",   Width:100,  SaveName:"aprvDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:100,  SaveName:"aprvUserId",    Align:"Left", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
        SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});     	
	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});
	    SetColProperty("symnDcd", ${codeMap.symnDcdibs});
        //SetColHidden("rqstUserNm",1);

        InitComboNoMatchText(1, "");
        FitColWidth();  
        
        SetExtendLastCol(1);  
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_symnchange);    
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
function grid_sub_symnchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_symnchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_symnchange_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_symnchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
