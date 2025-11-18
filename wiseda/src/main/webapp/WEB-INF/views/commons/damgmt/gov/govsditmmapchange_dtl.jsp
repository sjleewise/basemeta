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
	initgrid_sub_govsditmmapchange();

	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	
});


function initgrid_sub_govsditmmapchange() {

    with(grid_sub_govsditmmapchange){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"No.|상태|선택|공통표준용어ID|차수|공통표준용어명|공통표준용어영문약어명|공통표준용어설명|공통표준도메인ID|공통표준도메인명|저장 형식|표현 형식|허용값|행정표준코드명|소관기관명|기관표준용어ID|기관표준용어논리명|기관표준단어물리명|등록유형코드|등록일자|등록자ID|등록자명", Align:"Center"}
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:80,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:80,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:100,   SaveName:"ibsCheck",    Align:"Center", Edit:0, Hidden:0, Sort:0},   
                    
//                     {Type:"Combo",   Width:130,  SaveName:"stndAsrt",    Align:"Left", Edit:1, KeyField:1,UpdateEdit:0},
                    {Type:"Text",   Width:100,  SaveName:"govSditmId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:120,  SaveName:"enactDgr",    Align:"Left", Edit:0, KeyField:1},
                    {Type:"Text",   Width:300,  SaveName:"govSditmLnm",    Align:"Center", Edit:0 , KeyField:1},
                    {Type:"Text",   Width:340,  SaveName:"govSditmPnm",    Align:"Center", Edit:0, KeyField:1},
                    
                    {Type:"Text",   Width:300,  SaveName:"objDescn",    Align:"Left", Edit:0, KeyField:1}, 
                    {Type:"Text",   Width:200,  SaveName:"govDmnId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:200,  SaveName:"govDmnLnm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:200,  SaveName:"saveType",     Align:"Center", Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"examData",     Align:"Center", Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"cdValRule",      Align:"Center",   Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"govStndCd",      Align:"Center",   Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"govOrgNm",      Align:"Center",   Edit:0},
                    
                    {Type:"Text",   Width:200,  SaveName:"sditmId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:300,  SaveName:"sditmLnm",    Align:"Center", Edit:0, KeyField:0},
                    {Type:"Text",   Width:300,  SaveName:"sditmPnm",    Align:"Center", Edit:0, KeyField:0},
                    
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
    init_sheet(grid_sub_govsditmmapchange);    
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
function grid_sub_govsditmmapchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_govsditmmapchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_govsditmmapchange_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_govsditmmapchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
