<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<script type="text/javascript">

var bfSrcRow = "";
var bfTgtRow = "";

$(document).ready(function() {
      $("div.pop_tit_close").click(function(){
      	//iframe 형태의 팝업일 경우
      	if ("${search.popType}" == "I") {
      		parent.closeLayerPop();
      	} else {
      		window.close();
      	}
      	
      });
      
      $(".bt03").hide();
      // 엑셀내리기 Event Bind
      $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
});

function initGapCollist() {

    with(gapcollist_grid){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(5);
        var header1 = "<s:message code='META.HEADER.PDMDDLGAP.POP1'/>";
        /* No.|상태|컬럼ID|물리모델테이블ID|테이블명|테이블한글명|표준용어ID|표준용어|컬럼명|컬럼한글명|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL */
        var header2 = "<s:message code='META.HEADER.PDMDDLGAP.POP2'/>";
        /* No.|상태|컬럼ID|물리모델테이블ID|테이블명|테이블한글명|표준용어ID|표준용어|컬럼명|컬럼한글명|순서|데이터타입|길이|소수점|PK여부|PK순서|NOTNULL|DEFAULT|DDL컬럼ID|DDL테이블명|DDL컬럼명|DDL컬럼논리명|DDL테이블ID|순서|데이터타입|길이|소수점|PK여부|PK순서|NOTNULL|DEFAULT */
        var headers = [
                       {Text:header1},
                   	{Text:header2, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                        {Type:"Seq",	Width:30,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
                        {Type:"Combo",    Width:30,  SaveName:"gapStatus"     ,Align:"Center",   Edit:0, Hidden:0},
                        {Type:"Text",    Width:100,  SaveName:"pdmColId"     ,Align:"Left",   Edit:0, Hidden:1},
                        {Type:"Text",    Width:100,  SaveName:"pdmTblId"     ,Align:"Left",   Edit:0, Hidden:1},
                        {Type:"Text",    Width:100,  SaveName:"pdmTblPnm"     ,Align:"Left",   Edit:0, Hidden:0},
                        {Type:"Text",    Width:100,  SaveName:"pdmTblLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                        {Type:"Text",    Width:100,  SaveName:"sditmId"     ,Align:"Left",   Edit:0, Hidden:1},
                        {Type:"Text",    Width:100,  SaveName:"sditmLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                        {Type:"Text",    Width:100,  SaveName:"pdmColPnm"     ,Align:"Left",   Edit:0},
                        {Type:"Text",    Width:100,  SaveName:"pdmColLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                        {Type:"Text",    Width:30,  SaveName:"pdmColOrd"     ,Align:"Center",   Edit:0},
                        {Type:"Text",    Width:80,  SaveName:"pdmDataType"     ,Align:"Center",   Edit:0},
                        {Type:"Text",    Width:40,  SaveName:"pdmDataLen"     ,Align:"Center",   Edit:0},
                        {Type:"Text",    Width:40,  SaveName:"pdmDataScal"     ,Align:"Center",   Edit:0},
                        {Type:"Combo",    Width:50,  SaveName:"pdmPkYn"     ,Align:"Center",   Edit:0},
                        {Type:"Text",    Width:40,  SaveName: "pdmPkOrd"     ,Align:"Center",   Edit:0},
                        {Type:"Combo",    Width:50,  SaveName:"pdmNonulYn"     ,Align:"Center",   Edit:0},
                        {Type:"Text",    Width:50,  SaveName:"pdmDefltVal"     ,Align:"Center",   Edit:0},
						{Type:"Text",   Width:50,   SaveName:"ddlColId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlTblPnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlColPnm", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlColLnm", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlTblId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:30,   SaveName:"ddlColOrd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"ddlDataType",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"ddlDataLen",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"ddlDataScal",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:40,   SaveName:"ddlPkYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:50,   SaveName:"ddlPkOrd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:50,   SaveName:"ddlNonulYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:50,   SaveName:"ddlDefltVal",	 	Align:"Center", Edit:0, Hidden:0},
                   
                ];
                    
        InitColumns(cols);
        //콤보 목록 설정...
        InitComboNoMatchText(1, "");
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("ddlPkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("ddlNonulYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("pdmPkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("pdmNonulYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
		FitColWidth();
        SetSheetHeight(500);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(gapcollist_grid);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddltblcollist.ShowDebugMsg(-1);	
    	
}


$(window).on('load',function() {
	//initColGrid();
	initGapCollist();
	doAction("Search");
});



function grid_sub_ddltblcollist_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;

}

function doAction(sAction)
{
    switch(sAction)
    {		    
        case "Search":
        	var param = "ddlTblId=${search.ddlTblId}&pdmTblId=${search.pdmTblId}";
           
            //if($("#dbSchId option:selected").val()==''){
            //	showMsgBox("INF", "<s:message code="REQ.NO.TGTSCH" />");	
            //	return false;
            //}
        	gapcollist_grid.DoSearch('<c:url value="/meta/gap/getPdmDdlColGap.do" />', param);
        	//alert(param);
        	break;
        case "Down2Excel":  //엑셀내려받기
        	gapcollist_grid.Down2ExcelBuffer(true);  
        
        	if(gapcollist_grid.SearchRows()>0){
        		gapcollist_grid.Down2Excel({FileName:'pdmddlgapcol_list',SheetName:'<s:message code="PHYC.VS.DDL.GAP.CLMN.LST"/>', HiddenColumn:1, Merge:1}); //물리모델VSDDLGAP컬럼목록 
        	}

        	gapcollist_grid.Down2ExcelBuffer(false);   
        	
            break;	
    }
}


</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="CLMN.LST" /></div> <!-- 컬럼목록 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
<div class = "pop_content" >
     <!-- 조회버튼영역  -->
<%-- 		<%@ include file="" %> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
<div id="tblLayer" class="">		
		<div>
		<div class="stit"><s:message code="CLMN.LST.INFO"/></div> <!-- 컬럼목록정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
		<caption>PDM</caption>
	     <colgroup>
	     <col style="width:100%;" />
	    </colgroup>
	     <tbody>  
		<tr>
			<td>
			 	<div id="grid_99" class="grid_01">
				     <script type="text/javascript">createIBSheet("gapcollist_grid", "100%", "100%");</script>            
				</div>
			</td>
		</tr>
		</tbody>
		</table>
		</div>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	</div>
</body>
</html>
	