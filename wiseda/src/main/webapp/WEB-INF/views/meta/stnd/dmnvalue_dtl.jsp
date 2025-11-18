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
// 	initsubgrid_dmnvalue();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_dmnvalue.FitColWidth();
});


function initsubgrid_dmnvalue() {

    with(grid_sub_dmnvalue){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
    
    	var headtext  = "<s:message code='META.HEADER.DMNVALUE.DTL.1'/>";
		    headtext += "|<s:message code='META.HEADER.DMNVALUE.DTL.2'/>";
		    headtext += "|<s:message code='META.HEADER.DMNVALUE.DTL.3'/>";
		    headtext += "|<s:message code='META.HEADER.DMNVALUE.DTL.4'/>";
		    headtext += "|<s:message code='META.HEADER.DMNVALUE.DTL.5'/>";

        //headtext  = "No.|코드값ID|코드값|코드값명|도메인ID|상위코드값ID|요청번호|요청일련번호|요청상세일련번호";
        //headtext += "|상위코드값|사용여부";
        //headtext += "|기타1|기타1명|기타2|기타2명|기타3|기타3명|기타4|기타4명|기타5|기타5명";
        //headtext += "|적요1|적요2";
        //headtext += "|설명|버전|등록유형|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID|표시순서|적용시작일자|적용종료일자|비고";

        var headers = [
                    {Text: headtext , Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
//                  {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
//                  {Type:"CheckBox", Width:60, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",   Width:30,   SaveName:"cdValId",       Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,  SaveName:"cdVal",         Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:120,  SaveName:"cdValNm",       Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:10,   SaveName:"dmnId",         Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:60,   SaveName:"uppCdValId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:10,   SaveName:"rqstNo",        Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:10,   SaveName:"rqstSno",       Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:10,   SaveName:"rqstDtlSno",    Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"uppCdVal",      Align:"Left", Edit:0},
					{Type:"Combo",  Width:80,   SaveName:"useYn",	      Align:"Center", Edit:0},
					{Type:"Text",   Width:60,   SaveName:"vvNote1",       Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNoteNm1",     Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNote2",       Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNoteNm2",     Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNote3",       Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNoteNm3",     Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNote4",       Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNoteNm4",     Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNote5",       Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"vvNoteNm5",     Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"outlCntn1",     Align:"Left", Edit:1, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"outlCntn2",     Align:"Left", Edit:1, Hidden:1},
                    {Type:"Text",   Width:250,  SaveName:"objDescn",      Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:10,   SaveName:"objVers",       Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Combo",  Width:100,  SaveName:"regTypCd",      Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",   Width:10,   SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"}, 
                    {Type:"Text",   Width:10,   SaveName:"frsRqstUserId", Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",   Width:10,   SaveName:"rqstDtm",       Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:10,   SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},          
                    {Type:"Date",   Width:10,   SaveName:"aprvDtm",       Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:10,   SaveName:"aprvUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"dispOrd",       Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"aplStrDt",      Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:100,  SaveName:"aplEndDt",      Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:50,   SaveName:"reMark",        Align:"Left", Edit:0, Hidden:0} 
                   
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("useYn", 	{ComboCode:"Y|N", ComboText:"Y|N"});
      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
        SetSheetHeight(250);
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dmnvalue);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_dmnvalue.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//도메인 유효값 그리드 초기화
// 	initsubgrid_dmnvalue();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_dmnvalue_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_dmnvalue_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_dmnvalue_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_dmnvalue", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
