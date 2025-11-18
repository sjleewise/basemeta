<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<script type="text/javascript">

var bfSrcRow = "";
var bfTgtRow = "";

function initColGrid()
{
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.MODELGAP.DTL'/>";
        //No.|컬럼ID|물리모델테이블ID|물리모델테이블|표준용어ID|표준용어|컬럼명|컬럼한글명|컬럼순서|등록유형|

        var headers= [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmColId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"pdmTblId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"pdmTblPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"sditmId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"sditmLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"pdmColPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"pdmColLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:120,  SaveName:"colOrd"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:50,  SaveName:"regTypCd"     ,Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:120,  SaveName:"dataType"     ,Align:"Center",   Edit:0},
//                     {Type:"Text",    Width:50,  SaveName:"dataLen"     ,Align:"Center",   Edit:0},
//                     {Type:"Text",    Width:50,  SaveName:"dataScal"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:120,  SaveName:"pkYn"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:120,  SaveName:"pkOrd"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:150,  SaveName:"nonulYn"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:250,  SaveName:"defltVal"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstUserId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstUserNm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"frsRqstDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserNm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"rqstDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserNm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"aprvDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:50,  SaveName:"objVers"     ,Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:300,  SaveName:"objDescn"     ,Align:"Left",   Edit:0, Hidden:1}
                ]; 
                    
        InitColumns(cols);

        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"신규|변경|삭제"});
        SetColProperty("pkYn", 	{ComboCode:"N|Y", 	ComboText:"아니요|예"});
        SetColProperty("nonulYn", 	{ComboCode:"N|Y", 	ComboText:"아니요|예"});
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
   
}

function initsubgrid_dbctblcollist() {

    with(grid_sub_dbctblcollist){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.MODELGAP.DTL1'/>", Align:"Center"}
                ];
        //No.|컬럼ID|물리모델테이블ID|물리모델테이블|표준용어ID|표준용어|컬럼명|컬럼한글명|컬럼순서|등록유형|
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:40,   SaveName:"dbSchId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:120,   SaveName:"dbSchLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbcTblNm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbcColNm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"dbcColKorNm", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ord",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataType",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"dataLen",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"dataPnum",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"dataPnt",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Combo",   Width:80,   SaveName:"pkYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"pkOrd",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"nullYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"defltLen",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"defltVal",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:220,   SaveName:"descn",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"ddlColErrExs",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"pdmColErrExs",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:220,   SaveName:"colErrConts",	 	Align:"Left", Edit:0, Hidden:0},
                   
                ];
                    
        InitColumns(cols);
// 		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
		SetColProperty("nullYn", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
		SetColProperty("ddlColErrExs", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
		SetColProperty("pdmColErrExs", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dbctblcollist);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_dbctblcollist.ShowDebugMsg(-1);	
    	
}

function initsubgrid_ddltblcollist() {

    with(grid_sub_ddltblcollist){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.MODELGAP.DTL2'/>", Align:"Center"}
                ];
        //데이터타입|PK여부|PK순서|NOTNULL여부|DEFAULT값|
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:40,   SaveName:"ddlColId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlTblPnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"ddlColPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"ddlColLnm", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"ddlTblId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"colOrd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataType",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"dataLen",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"dataScal",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Combo",   Width:80,   SaveName:"pkYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"pkOrd",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"nonulYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"defltVal",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstDtlSno",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:220,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:30,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:30,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:80,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:80,  SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"scrtInfo",  Align:"Left", Edit:0, Hidden:1},
                   
                ];
                    
        InitColumns(cols);
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
		SetColProperty("nonulYn", 	{ComboCode:"N|Y", ComboText:"아니요|예"});
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_ddltblcollist);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddltblcollist.ShowDebugMsg(-1);	
    	
}


$(window).on('load',function() {
	initsubgrid_dbctblcollist();
	initColGrid();
	initsubgrid_ddltblcollist();
});


function grid_sub_dbctblcollist_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if(grid_sub_dbctblcollist.GetCellValue(Row, "colErrConts") != ""){
		
		col_sheet.SetRowFontColor(bfSrcRow, "#000000");
		grid_sub_ddltblcollist.SetRowFontColor(bfTgtRow, "#000000");
		
		var srcRow = col_sheet.FindText("dbcColNm", grid_sub_dbctblcollist.GetCellValue(Row, "srcColPnm"));
		var tgtRow = grid_sub_ddltblcollist.FindText("dbcColNm", grid_sub_dbctblcollist.GetCellValue(Row, "tgtColPnm"));
		
		col_sheet.SetRowFontColor(srcRow, '#FF0000');
		grid_sub_ddltblcollist.SetRowFontColor(tgtRow, '#FF0000');
		
		bfSrcRow = srcRow;
		bfTgtRow = tgtRow;
	}
}

function grid_sub_ddltblcollist_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
// 	var param = "ddlTblId="+grid_sub_ddltblcollist.GetCellValue(row, "ddlTblId");
	
// 	var url = '<c:url value="/meta/ddl/popup/ddlscript_pop.do"/>';
// 	OpenModal(url+"?"+param, "DdlScript", 800, 500);

}


</script>


<div class="stit">DBC컬럼 상세정보</div>
<div style="clear:both; height:5px;"><span></span></div>
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sub_dbctblcollist", "100%", "200px")</script>
</div>
<div style="clear:both; height:10px;"><span></span></div>

<div id="tblLayer" class="">		
		<div style="float:left; width:49%; ">
		<div class="stit">물리모델컬럼 상세정보</div>
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
				     <script type="text/javascript">createIBSheet("col_sheet", "100%", "250px");</script>            
				</div>
			</td>
		</tr>
		</tbody>
		</table>
		</div>
	
		<div style="float:right; width:49%; ">
		<div class="stit">DDL컬럼 상세정보</div>
		<div style="clear:both; height:5px;"><span></span></div>
			<table width="100%" height="150px" border="0" cellspacing="0" cellpadding="0" summary="">
			<caption>DDL</caption>
            <colgroup>
            <col style="width:100%;" />
           </colgroup>
            <tbody>  
			<tr>
			<td>
			<div class="grid_01">
				    <script type="text/javascript">createIBSheet("grid_sub_ddltblcollist", "100%", "250px")</script>
				</div>

			</td>
			</tr>
			</tbody>
			</table>
		</div>
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>