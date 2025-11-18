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
 	initsubgrid_codetsfchange();
	
		
	$("#btnSubTreeNew").hide();
	//조회 이벤트 처리
	$("#btnSubSearch").hide();
	
	//추가 이벤트 처리
	$("#btnSubNew").hide();
	
	//상세코드 저장 이벤트
	$("#btnSubSave").hide();

	//삭제버튼 이벤트 처리
	$("#btnSubDelete").hide();
	
    // 엑셀내리기 Event Bind
    $("#btnSubExcelDown").click( function(){ 
//     	grid_sub_codetsfchange.Down2Excel({HiddenColumn:1});
    });

    //엑셀업로 Event Bind
    $("#btnSubExcelLoad").click( function(){ 
    	
// 		var url = '<c:url value="/commons/sys/program/popup/program_xls.do"/>';
		//var url = '<c:url value="/commons/code/popup/commdtlcd_xls.do"/>';
// 		$('div#excel_pop iframe').attr('src', url);
		//openLayerPop(url, 800, 550);
		
    	//doAction("LoadExcel"); 
    }).hide();
	

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_codetsfchange.FitColWidth();
});


function initsubgrid_codetsfchange() {

    with(grid_sub_codetsfchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(0);
    	var headtext  = "<s:message code='META.HEADER.CODETSFCHANGE.DTL.1'/>";
		headtext += "|<s:message code='META.HEADER.CODETSFCHANGE.DTL.2'/>";
		headtext += "|<s:message code='META.HEADER.CODETSFCHANGE.DTL.3'/>";
		headtext += "|<s:message code='META.HEADER.CODETSFCHANGE.DTL.4'/>";
		headtext += "|<s:message code='META.HEADER.CODETSFCHANGE.DTL.5'/>";
		headtext += "|<s:message code='META.HEADER.CODETSFCHANGE.DTL.6'/>";

    //headtext  = "No.|상태|만료일시|시작일시";
    //headtext += "|타겟DB_ID|타겟DB명|타겟스키마ID|타겟스키마명";
    //headtext += "|도메인ID|도메인논리명|코드유형|대분류코드|코드ID|코드값|코드값명|상위코드ID|상위코드값";
    //headtext += "|표시순서|사용여부|버전|등록유형";
    //headtext += "|기타1|기타1명|기타2|기타2명|기타3|기타3명|기타4|기타4명|기타5|기타5명|적요1|적요2";
    //headtext += "|비고|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호";
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        var headers = [
   	     			{Text:headtext, Align:"Center"}
   	     		];
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
	                {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",   Width:100,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0}, 
                    {Type:"Date",   Width:100,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0}, 
	                {Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgId",   	Align:"Left", Edit:0,Hidden:1},
	                {Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgPnm",   	Align:"Left", Edit:0,Hidden:0},
	                {Type:"Text",   Width:100,  SaveName:"tgtDbSchId",   	Align:"Left", Edit:0,Hidden:1},
	                {Type:"Text",   Width:100,  SaveName:"tgtDbSchPnm",   	Align:"Left", Edit:0,Hidden:0},
	                {Type:"Text",   Width:100,   SaveName:"dmnId",	 	Align:"Left", Edit:0, KeyField:0, Hidden:1},
	                {Type:"Text",   Width:100,   SaveName:"dmnLnm",	 	Align:"Left", Edit:0, KeyField:0, Hidden:0},
	                {Type:"Combo",   Width:100,  SaveName:"cdValTypCd",   	Align:"Left", Edit:0},
	                {Type:"Text",   Width:100,  SaveName:"dmnDscd",   	Align:"Left", Edit:0},
	                {Type:"Text",   Width:60,  SaveName:"cdValId",   	Align:"Left", Edit:0,Hidden:1},
	                {Type:"Text",   Width:60,  SaveName:"cdVal",   	Align:"Left", Edit:0, KeyField:1},
	                {Type:"Text",   Width:100,  SaveName:"cdValNm",   	Align:"Left", Edit:0, KeyField:1},
	                {Type:"Text",   Width:60,  SaveName:"uppCdValId",   	Align:"Left", Edit:0, Hidden:1},
	                {Type:"Text",   Width:100,  SaveName:"uppCdVal",   	Align:"Left", Edit:0},
	                {Type:"Text",   Width:60,  SaveName:"dispOrd",   	Align:"Left", Edit:0},
	                {Type:"Combo",  Width:80,  SaveName:"useYn",		Align:"Center", Edit:0},
	                {Type:"Text",   Width:60,  SaveName:"objVers",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Combo",   Width:100,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:0, ColMerge:0}, 
	                {Type:"Text",   Width:60,  SaveName:"vvNote1",   	Align:"Left", Edit:0},
	                {Type:"Text",   Width:60,  SaveName:"vvNoteNm1",   	Align:"Left", Edit:0},
	                {Type:"Text",   Width:60,  SaveName:"vvNote2",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"vvNoteNm2",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"vvNote3",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"vvNoteNm3",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"vvNote4",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"vvNoteNm4",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"vvNote5",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"vvNoteNm5",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"outlCntn1",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:60,  SaveName:"outlCntn2",   	Align:"Left", Edit:0},
                   	{Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
                   	{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:0},
                   	{Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
                   	{Type:"Text",   Width:50,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0},
                   	{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
                   	{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("useYn", 	{ComboCode:"N|Y", ComboText:"N|Y"});
        SetColProperty("cdValTypCd",${codeMap.cdValTypCdibs});
	    
      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_codetsfchange);    
    //===========================
  	

    	
}

$(window).on('load',function() {
	//표준단어 변경이력 그리드 초기화
// 	initsubgrid_stwdchange();

});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_codetsfchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_codetsfchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_codetsfchange_OnSearchEnd(code, message, stCode, stMsg) {
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
    
	<form name="frmCodeDtl" action ="" method="post">
	<div class="tb_basic" style="display: none;">
    <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
        <caption>
        <s:message code="INQ.COND" /><!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
<%--       <tr>
        <th>구분</th>
        <td>
        	<select id="cateCode" name="cateCode">
        		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
         	<c:forEach var="code" items="${codeMap.cateCode}" varStatus="status">
             <option value="${code.codeCd}">${code.codeLnm}</option>
             </c:forEach>
        	</select>
        </td>
        <th>게시물검색</th>
        <td>
	        <select  id="searchCnd" name="searchCnd" title="검색조건선택">
	        	<option value="0">제목</option>
	            <option value="1">내용</option>
	            <option value="2">작성자</option>
	        </select>
        <input type="text" name="searchWrd" value="${searchVO.searchWrd}" title="검색어 입력" style="display: inline; width: 150px;">
        </td>
      </tr>
      <tr>
        <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
        <td class="bd_none">
        	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
            <a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
            <a href="#" class="tb_bt"><s:message code="DD7" /></a> <!-- 7일 -->
            <a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
            <a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
            <a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
        </td>
        <th>등록일</th>
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" >  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}">
      </tr> --%>
      <tr>
      	<th><s:message code="PGM.NM" /></th> <!-- 프로그램명 -->
      	<td>
<%--       		<input type="text" name="searchWrd" value="${searchVO.searchWrd}"> --%>
      	</td>
      </tr>
    </table>
    </div>
    </form>
    <div style="clear:both; height:5px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
<%--     <tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" /> --%>
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub_codetsfchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
