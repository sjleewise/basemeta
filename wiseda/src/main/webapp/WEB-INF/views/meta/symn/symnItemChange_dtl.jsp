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
// 	initsubgrid_stwdchange();
	
		
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
//     	grid_sub_stwdchange.Down2Excel({HiddenColumn:1});
    });

    //엑셀업로 Event Bind
    $("#btnSubExcelLoad").click( function(){ 
    	
// 		var url = '<c:url value="/commons/sys/program/popup/program_xls.do"/>';
		var url = '<c:url value="/commons/code/popup/commdtlcd_xls.do"/>';
// 		$('div#excel_pop iframe').attr('src', url);
		openLayerPop(url, 800, 550);
		
    	//doAction("LoadExcel"); 
    }).hide();
	

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_stwdchange.FitColWidth();
});


function initsubgrid_stwdchange() {

    with(grid_sub_stwdchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        var headers = [
                    {Text:"<s:message code='META.HEADER.SYMNITEM.HIST'/>", Align:"Center"}
                ];
                //No.|유사항목ID|만료일자|시작일자|유사항목논리명|유사항목물리명|유사항목타입|참조항목ID|참조항목논리명|참조항목물리명|요청번호|요청일련번호|설명|버전|등록유형|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID|비고
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",    	Align:"Center", Edit:0, ColMerge:0},
//                  {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
//                  {Type:"CheckBox", Width:60, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"symnItemId"     ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"expDtm"          ,    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0}, 
					{Type:"Text",   Width:120,  SaveName:"strDtm"          ,    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0}, 
					{Type:"Text",   Width:120,  SaveName:"symnItemLnm"    ,     Align:"Left", Edit:0, Hidden:0, ColMerge:1}, 
					{Type:"Text",   Width:120,  SaveName:"symnItemPnm"    ,     Align:"Left", Edit:0, Hidden:0, ColMerge:1}, 
					{Type:"Text",   Width:120,  SaveName:"symnItemType"   ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"sditmId"         ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"sditmLnm"        ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"sditmPnm"        ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"rqstNo"          ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"rqstSno"         ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"objDescn"        ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"objVers"         ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"regTypCd"       ,    Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"frsRqstDtm"     ,     Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"}, 
					{Type:"Text",   Width:120,  SaveName:"frsRqstUserId" ,    Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm"         ,    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"}, 
					{Type:"Text",   Width:120,  SaveName:"rqstUserId"     ,    Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"aprvDtm"         ,     Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"}, 
					{Type:"Text",   Width:120,  SaveName:"aprvUserId"     ,    Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"pnmCriDs"       ,    Align:"Left", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	   // SetColProperty("wdDcd", 	${codeMap.wdDcdibs});
	    //SetColProperty("dmnYn", 	{ComboCode:"N|Y", ComboText:"N|Y"});
	    
      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_stwdchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_stwdchange.ShowDebugMsg(-1);	
    	
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
function grid_sub_stwdchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_stwdchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_stwdchange_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_stwdchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
