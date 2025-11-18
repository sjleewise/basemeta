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


function initIdxGrid() {

    with(idx_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(5);
        var headers = [
                       {Text:"<s:message code='META.HEADER.DBGAPIDX.DTL1'/>"},
                       /* No.|소스(Source)|소스(Source)|소스(Source)|소스(Source)|타겟(Target)|타겟(Target)|타겟(Target)|타겟(Target)|에러내용|에러내용|에러내용|에러내용 */
                   	{Text:"<s:message code='META.HEADER.DBGAPIDX.DTL2'/>", Align:"Center"}
                       /* No.|스키마명|테이블명|인덱스명|인덱스존재여부|스키마명|테이블명|인덱스명|인덱스존재여부|인덱스에러여부|인덱스에러내용|인덱스컬럼에러여부|인덱스컬럼에러내용 */
                   
                       ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Combo",   Width:120,   SaveName:"srcDbSchId",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:120,   SaveName:"srcTblPnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"srcIdxPnm",   Align:"Left", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"srcIdxExtncExs",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:120,   SaveName:"tgtDbSchId",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"tgtTblPnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"tgtIdxPnm",   Align:"Left", Edit:0},
                    {Type:"Combo",   Width:100,   SaveName:"tgtIdxExtncExs",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"idxErrExs",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"idxErrDescn",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",   Width:80,   SaveName:"idxColErrExs",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"idxColErrDescn",   Align:"Left", Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);
		SetColProperty("srcDbSchId", 	${codeMap.dbSchLnmibs});
		SetColProperty("tgtDbSchId", 	${codeMap.dbSchLnmibs});
		SetColProperty("srcIdxExtncExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("tgtIdxExtncExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("idxErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("idxColErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(idx_sheet);    
    //===========================
    	
}

function initIdxSrcGrid() {

    with(idx_src_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                       {Text:"<s:message code='META.HEADER.DBGAPIDX.DTL.1'/>", Align:"Center"}
                   ];
                   //No.|스키마명|테이블명|인덱스명|인덱스컬럼명|인덱스한글명컬럼명|순서|정렬타입|데이터타입|데이터자리수|데이터길이|데이터소수점

           
           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                       {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
   						{Type:"Combo",   Width:100,   SaveName:"srcDbSchId", 		Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:100,   SaveName:"srcTblPnm", 		Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:100,   SaveName:"srcIdxPnm", 		Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:100,   SaveName:"srcIdxColPnm", 		Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:100,   SaveName:"srccIdxColLnm", 		Align:"Left", Edit:0, Hidden:1},
   						{Type:"Text",   Width:80,   SaveName:"srcOrd",	 	Align:"Center", Edit:0, Hidden:0},
   						{Type:"Combo",   Width:80,   SaveName:"srcSortType",	 	Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:80,   SaveName:"srcDataType",	 	Align:"Center", Edit:0, Hidden:0},
   						{Type:"Text",   Width:80,   SaveName:"srcDataPnum",	 	Align:"Center", Edit:0, Hidden:0},
   						{Type:"Text",   Width:80,   SaveName:"srcDataLen",	 	Align:"Center", Edit:0, Hidden:0},
   						{Type:"Text",   Width:80,   SaveName:"srcDataPnt",	 	Align:"Center", Edit:0, Hidden:0}
                   ];
           	InitColumns(cols);
        
        SetColProperty("srcDbSchId", 	${codeMap.dbSchLnmibs});		
		SetColProperty("srcSortType", ${codeMap.sortTypeibs});
		
        FitColWidth();
        SetExtendLastCol(1);
    }
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(idx_src_sheet);    
    //===========================
}

function initIdxTgtGrid() {

    with(idx_tgt_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                       {Text:"<s:message code='META.HEADER.DBGAPIDX.DTL.2'/>", Align:"Center"}
                   ];
           //No.|스키마명|테이블명|인덱스명|인덱스컬럼명|인덱스한글명컬럼명|순서|정렬타입|데이터타입|데이터자리수|데이터길이|데이터소수점


           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                       {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
   						{Type:"Combo",   Width:100,   SaveName:"tgtDbSchId", 		Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:100,   SaveName:"tgtTblPnm", 		Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:100,   SaveName:"tgtIdxPnm", 		Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:100,   SaveName:"tgtIdxColPnm", 		Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:100,   SaveName:"tgtcIdxColLnm", 		Align:"Left", Edit:0, Hidden:1},
   						{Type:"Text",   Width:80,   SaveName:"tgtOrd",	 	Align:"Center", Edit:0, Hidden:0},
   						{Type:"Combo",   Width:80,   SaveName:"tgtSortType",	 	Align:"Left", Edit:0, Hidden:0},
   						{Type:"Text",   Width:80,   SaveName:"tgtDataType",	 	Align:"Center", Edit:0, Hidden:0},
   						{Type:"Text",   Width:80,   SaveName:"tgtDataPnum",	 	Align:"Center", Edit:0, Hidden:0},
   						{Type:"Text",   Width:80,   SaveName:"tgtDataLen",	 	Align:"Center", Edit:0, Hidden:0},
   						{Type:"Text",   Width:80,   SaveName:"tgtDataPnt",	 	Align:"Center", Edit:0, Hidden:0}
                   ];
           	InitColumns(cols);
           	
            SetColProperty("tgtDbSchId", 	${codeMap.dbSchLnmibs});		
    		SetColProperty("tgtSortType", ${codeMap.sortTypeibs});
    		
           	FitColWidth();
           	SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(idx_tgt_sheet);    
    //===========================
}


$(window).on('load',function() {
	initIdxGrid();
	initIdxSrcGrid();
	initIdxTgtGrid();
	
});


</script>
	<div class="stit"><s:message code="GAP.DTL.INFO"/></div> <!-- GAP 상세정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("idx_sheet", "100%", "250px")</script>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>

	<div id="tblLayer" class="">		
		<div style="float:left; width:49%; ">
			<div class="stit"><s:message code="SOUR.IDEX.DTL.INFO"/></div> <!-- 소스인덱스컬럼 상세정보 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<div class="grid_01">
			     <script type="text/javascript">createIBSheet("idx_src_sheet", "100%", "200px")</script>
			</div>
		</div>
	
		<div style="float:right; width:50%; ">
			<div class="stit"><s:message code="TARG.IDEX.DTL.INFO"/></div> <!-- 타겟인덱스컬럼 상세정보 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<div class="grid_01">
			     <script type="text/javascript">createIBSheet("idx_tgt_sheet", "100%", "200px")</script>
			</div>
		</div>
	</div>

	<div style="clear:both; height:10px;"><span></span></div>