<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//검색조건 display none
// 		$("div#col_search_div").hide();
// 		$("form#frmColInput").hide();
 		$("#btnColExcelDown").hide();
		
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initTblSlGrid();
});


$(window).resize();


function initTblSlGrid()
{
    
    with(tbls_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.TBLSTATUSHIST.DTL.1'/>";

        //No.|객체버전|기준일자|주제영역명|물리모델테이블 ID|물리모델테이블명(물리명)|물리모델테이블명(논리명)
    
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"objVers"     ,Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Date",    Width:100,  SaveName:"strDtm"     ,Align:"Left",   Edit:0, Format:"YYYY-MM-DD hh24:mi:ss"},
                    {Type:"Text",    Width:100,  SaveName:"subjLnm"     ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:250,  SaveName:"pdmTblId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:250,  SaveName:"pdmTblPnm"     ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmTblLnm"     ,Align:"Left",   Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);

        InitComboNoMatchText(1, "");
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(tbls_sheet);    
    //===========================
   
    with(tbls_sheet2){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.TBLSTATUSHIST.DTL.2'/>";

        //No.|컬럼명|컬럼한글명|데이터타입|설명
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
					{Type:"Text",    Width:250,  SaveName:"pdmColPnm"     ,Align:"Left",   Edit:0, Hidden:0},
					{Type:"Text",    Width:100,  SaveName:"pdmColLnm"     ,Align:"Left",   Edit:0, Hidden:0},
					{Type:"Text",    Width:100,  SaveName:"dataType"     ,Align:"Center",   Edit:0, Hidden:0},
					{Type:"Text",    Width:100,  SaveName:"objDescn"     ,Align:"Left",   Edit:0}
                ];
                    
        InitColumns(cols);

        InitComboNoMatchText(1, "");
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(tbls_sheet2);    
    //===========================
}

//화면상의 모든 액션은 여기서 처리...
function doActionTblS(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//테이블 형상이력 기준일자 조회
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
//         	var param = $('#frmSearch').serialize();
        	var param = $('#frmSearch').serialize();
        	tbls_sheet.DoSearch("<c:url value="/meta/model/getstathistbllist.do" />", param);
//         	tbls_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "SearchRow": //기준일자별 테이블 형상이력 상세 조회
        	//선택 행 조회
        	var crow = tbls_sheet.GetSelectRow();
        	if(crow < 1) return false;
        	var param = $('#frmSearch').serialize();
        	tbls_sheet2.DoSearchRow(crow, "<c:url value="/meta/model/getcoldtllist.do" />",  param);
        break;
       
//         case "Down2Excel":  //엑셀내려받기
//             tbls_sheet.Down2Excel({HiddenColumn:1});
//             break;
    }       
}
 


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function col_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	return;
}

function tbls_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
    doActionTblS("SearchRow");
    
}

</script>
<!-- </head> -->

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>

<div id="col_search_div">
<!-- 검색조건 입력폼 -->
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" id="rqstNo" name="rqstNo" />
        	<input type="hidden" id="rqstSno" name="rqstSno" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>
	                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
        
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("tbls_sheet", "100%", "120px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "tbls_sheet", "100%", "100%");</script>             --%>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="grid_02" class="grid_02">
	     <script type="text/javascript">createIBSheet("tbls_sheet2", "100%", "150px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_02 "), "tbls_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- </body> -->
<!-- </html> -->