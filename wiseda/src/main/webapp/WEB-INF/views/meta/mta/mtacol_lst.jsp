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
//     	grid_sub_mtacol.Down2Excel({HiddenColumn:1});
    });

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_mtacol.FitColWidth();
});


function initsubgrid_mtacol() {

    with(grid_sub_mtacol){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0,UseHeaderSortCancel:1};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        //SetMergeSheet(1);

        var headText  = "<s:message code='META.HEADER.MTACOL.LST'/>";
        //No.|컬럼ID|컬럼영문명|컬럼한글명|테이블ID|컬럼설명|컬럼한글명(변경)|연관엔티티명|연관속성명|컬럼순서|데이터타입|데이터길이|소수점길이|데이터포맷|NOTNULL여부|PK정보|PK순서|FK정보|DEFAULT값|제약조건|공개여부|개인정보여부|암호화여부|비공개사유
        var headers = [
                    {Text:headText, Align:"Center"}
                ];
                //No.|컬럼ID|컬럼영문명|컬럼한글명|테이블ID|연관엔티티명|연관속성명|컬럼순서|데이터타입|길이|소수점길이|PK 여부|PK 순서|NOT NULL여부|DEFAULT 값|데이터형식|컬럼설명|개방여부|개인정보여부|암호화여부
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        //KeyField 필수
        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
//                  {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
//                  {Type:"CheckBox", Width:60, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
					{Type:"Text",    Width:40,  SaveName:"mtaColId"    ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"mtaColPnm"   ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150, SaveName:"mtaColLnm"   ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:40,  SaveName:"mtaTblId"    ,Align:"Left",   Edit:0, Hidden:1},

                    {Type:"Text",    Width:90,  SaveName:"colRelEntyNm"    ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:90,  SaveName:"colRelAttrNm"    ,Align:"Left",   Edit:0},
                    {Type:"Int",     Width:60,  SaveName:"colOrd"      ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:80,  SaveName:"dataType"    ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:50,  SaveName:"dataLen"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:70,  SaveName:"dataScal"    ,Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:55,  SaveName:"pkYn"        ,Align:"Center",   Edit:0},
                    {Type:"Int",     Width:55,  SaveName:"pkOrd"       ,Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:100,  SaveName:"nonulYn"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"defltVal"    ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:70,  SaveName:"dataFmt"    ,Align:"Left",   Edit:0},

                    {Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
                    {Type:"Combo",   Width:60,  SaveName:"openYn"        ,Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:70,  SaveName:"prsnInfoYn"        ,Align:"Center",   Edit:0},
                    {Type:"Combo",   Width:70,  SaveName:"encTrgYn"        ,Align:"Center",   Edit:0}

                ];
                    
        InitColumns(cols);

        var ynComboData = {ComboCode:"N|Y", ComboText:"N|Y"};
      	//콤보 목록 설정...
		SetColProperty("pkYn",		ynComboData);
		SetColProperty("nonulYn",	ynComboData);
		SetColProperty("openYn", 	ynComboData);
		SetColProperty("prsnInfoYn",	ynComboData);
		SetColProperty("encTrgYn",	ynComboData);
	    
		//콤보코드일때 값이 없는 경우 셋팅값
		InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_mtacol);    
    //===========================

    	
}

$(window).on('load',function() {
	//컬럼정보 그리드 초기화
// 	initsubgrid_mtacol();

});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_mtacol_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_mtacol_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_mtacol_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_mtacol", "100%", "200px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
