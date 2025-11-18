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
// 	initsubgrid_appprgmchange();
	
		
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
//     	grid_sub_appprgmchange.Down2Excel({HiddenColumn:1});
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
// 	grid_sub_appprgmchange.FitColWidth();
});


function initsubgrid_appprgmchange() {

    with(grid_sub_appprgmchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        var headers = [
                    {Text:"<s:message code='META.HEADER.APPPRGMITEM.CHG.DTL'/>", Align:"Center"}
                    /* No.|응용프로그램용어ID|만료일자|시작일자|응용프로그램용어논리명|응용프로그램용어물리명|응용프로그램용어구분|요청번호|요청일련번호|버전|등록유형|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID|비고|설명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",    	Align:"Center", Edit:0, ColMerge:0},
//                  {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
//                  {Type:"CheckBox", Width:60, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",   Width:120,  SaveName:"appPrgmId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",   Width:150,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0}, 
                    {Type:"Date",   Width:150,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0}, 
                    {Type:"Text",   Width:200,  SaveName:"appPrgmLnm",    Align:"Left", Edit:0, Hidden:0, ColMerge:1}, 
                    {Type:"Text",   Width:200,  SaveName:"appPrgmPnm",    Align:"Left", Edit:0, Hidden:0, ColMerge:1}, 
                    {Type:"Combo",   Width:120,  SaveName:"appPrgmDcd",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:90,  SaveName:"rqstNo",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:90,  SaveName:"rqstSno",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"objVers",    Align:"Center", Edit:0, Hidden:0, ColMerge:0}, 
                    {Type:"Combo",   Width:100,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:0, ColMerge:0}, 
                    {Type:"Date",   Width:100,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"}, 
                    {Type:"Text",   Width:100,  SaveName:"frsRqstUserId",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",  Width:100,  SaveName:"rqstDtm",     Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:100,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},          
                    {Type:"Date",   Width:100,  SaveName:"aprvDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:100,  SaveName:"aprvUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"reMark",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:500,  SaveName:"objDescn",    Align:"Left", Edit:0, Hidden:0, ColMerge:0}
                   
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("appPrgmDcd", 	${codeMap.appPrgmDcdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_appprgmchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_appprgmchange.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//APP용어 변경이력 그리드 초기화
// 	initsubgrid_appprgmchange();

});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_appprgmchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_appprgmchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_appprgmchange_OnSearchEnd(code, message) {
	//alert(grid_sub_appprgmchange.GetDataBackColor()+":"+ grid_sub_appprgmchange.GetDataAlternateBackColor());
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
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
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
	     <script type="text/javascript">createIBSheet("grid_sub_appprgmchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
