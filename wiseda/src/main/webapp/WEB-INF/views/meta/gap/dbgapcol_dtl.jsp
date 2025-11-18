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
function initColGrid() {

    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(5);
        var headers = [
                       {Text:"<s:message code='META.HEADER.DBGAPCOL.DTL1'/>"},
                       /* No.|소스(Source)|소스(Source)|소스(Source)|소스(Source)|타겟(Target)|타겟(Target)|타겟(Target)|타겟(Target)|에러내용|에러내용 */
                   	{Text:"<s:message code='META.HEADER.DBGAPCOL.DTL2'/>", Align:"Center"}
                       /* No.|스키마명|테이블명|컬럼명|컬럼존재여부|스키마명|테이블명|컬럼명|컬럼존재여부|컬럼에러여부|컬럼에러내용 */
                   
                       ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Combo",   Width:120,   SaveName:"srcDbSchId",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:120,   SaveName:"srcTblPnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"srcColPnm",   Align:"Left", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"srcColExtncExs",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:120,   SaveName:"tgtDbSchId",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"tgtTblPnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"tgtColPnm",   Align:"Left", Edit:0},
                    {Type:"Combo",   Width:100,   SaveName:"tgtColExtncExs",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"colErrExs",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"colErrDescn",   Align:"Left", Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
		SetColProperty("srcDbSchId", 	${codeMap.dbSchLnmibs});
		SetColProperty("tgtDbSchId", 	${codeMap.dbSchLnmibs});
		SetColProperty("srcColExtncExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("tgtColExtncExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("colErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     col_sheet.ShowDebugMsg(-1);	
    	
}

function initSrcGrid() {

    with(src_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.DBGAPIDX.DTL.3'/>", Align:"Center"} //No.|스키마ID|스키마명|테이블명|컬럼명|컬럼한글명|컬럼순서|데이터타입|길이|자리수|소수점길이|PK여부|PK순서|NOTNULL여부|DEFAULT길이|DEFAULT값
                ];
        
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
						{Type:"Text",   Width:100,   SaveName:"defltVal",	 	Align:"Left", Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
// 		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("nullYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("ddlColErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("pdmColErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(src_sheet);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     src_sheet.ShowDebugMsg(-1);	
    	
}

function initTgtGrid() {

    with(tgt_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.DBGAPCOL.DTL.3'/>", Align:"Center"} 
                    //No.|스키마ID|스키마명|테이블명|컬럼명|컬럼한글명|컬럼순서|데이터타입|길이|자리수|소수점길이|PK여부|PK순서|NOTNULL여부|DEFAULT길이|DEFAULT값
                ];
        
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
						{Type:"Text",   Width:100,   SaveName:"defltVal",	 	Align:"Left", Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
        SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("nullYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("ddlColErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("pdmColErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(tgt_sheet);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     tgt_sheet.ShowDebugMsg(-1);	
    	
}


$(window).on('load',function() {
	initSrcGrid();
	initTgtGrid();
	initColGrid();
	
});


function col_sheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	if(col_sheet.GetCellValue(Row, "colErrExs") == "Y"){
		src_sheet.SetRowFontColor(bfSrcRow, "#000000");
		tgt_sheet.SetRowFontColor(bfTgtRow, "#000000");
		
		var srcRow = src_sheet.FindText("dbcColNm", col_sheet.GetCellValue(Row, "srcColPnm"));
		var tgtRow = tgt_sheet.FindText("dbcColNm", col_sheet.GetCellValue(Row, "tgtColPnm"));
		
		src_sheet.SetRowFontColor(srcRow, '#FF0000');
		tgt_sheet.SetRowFontColor(tgtRow, '#FF0000');
		
		bfSrcRow = srcRow;
		bfTgtRow = tgtRow;
	}
}



</script>
	<div class="stit"><s:message code="GAP.DTL.INFO"/></div> <!-- GAP 상세정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("col_sheet", "100%", "250px")</script>
	</div>
<div style="clear:both; height:10px;"><span></span></div>
<div id="tblLayer" class="">		
		<div style="float:left; width:49%; ">
		<div class="stit"><s:message code="SOUR.CLMN.DTL.INFO"/></div> <!-- 소스컬럼 상세정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
		<caption>SRC</caption>
	     <colgroup>
	     <col style="width:100%;" />
	    </colgroup>
	     <tbody>  
		<tr>
			<td>
				 	<div id="grid_99" class="grid_01">
					     <script type="text/javascript">createIBSheet("src_sheet", "100%", "200px");</script>            
					</div>

			</td>
		</tr>
		</tbody>
		</table>
		</div>
	
		<div style="float:right; width:50%; ">
		<div class="stit"><s:message code="TARG.CLMN.DTL.INFO"/></div> <!-- 타겟컬럼 상세정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
			<table width="100%" height="150px" border="0" cellspacing="0" cellpadding="0" summary="">
			<caption>TGT</caption>
            <colgroup>
            <col style="width:100%;" />
           </colgroup>
            <tbody>  
			<tr>
			<td>
			<div class="grid_01">
				    <script type="text/javascript">createIBSheet("tgt_sheet", "100%", "200px")</script>
				</div>

			</td>
			</tr>
			</tbody>
			</table>
		</div>
</div>

	<div style="clear:both; height:10px;"><span></span></div>