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
function initsubgrid_dbconntrg()
{
    
    with(grid_sub_dbconntrg){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.CODEGAP.DTL.1'/>"; //No.|DB접속대상ID|DB접속대상명|DB스키마ID|DB스키마명|코드ID|코드명|"
            //+ "코드값ID|코드값|코드값명|코드값물리명|코드값순번|코드값사용여부|코드값설명

        var headers= [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dbConnTrgId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"dbConnTrgLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"dbSchId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"dbSchLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"codeId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"codePnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"codeValId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"codeVal"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:120,  SaveName:"codeValLnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"codeValPnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"codeValOrd"     ,Align:"Left",   Edit:0},
                    {Type:"Combo",    Width:120,  SaveName:"codeValUseYn"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"codeValDescn"     ,Align:"Left",   Edit:0}
                ]; 
                    
        InitColumns(cols);

        SetColProperty("codeValUseYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dbconntrg);    
    //===========================
   
}


function initsubgrid_meta() {

    with(grid_sub_meta){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.CODEGAP.DTL.2'/>"; //No.|도메인ID|도메인명|도메인물리명|코드값ID|코드값|코드값명|표시순서|코드값설명

        var headers= [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:40,   SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"dmnLnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"dmnPnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:120,   SaveName:"cdValId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"cdVal", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"cdValNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Int",   Width:100,   SaveName:"dispOrd",	 	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:200,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_meta);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_meta.ShowDebugMsg(-1);	
    	
}


$(window).on('load',function() {
	initsubgrid_dbconntrg();
	initsubgrid_meta();

	
});

function grid_sub_ddltblcollist_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	var param = "ddlTblId="+grid_sub_ddltblcollist.GetCellValue(row, "ddlTblId");
	
	var url = '<c:url value="/meta/ddl/popup/ddlscript_pop.do"/>';
	OpenModal(url+"?"+param, "DdlScript", 800, 500);

}


</script>


<div id="tblLayer" class="">		
		<div style="float:left; width:49%; ">
		<div class="stit"><s:message code="CONN.TRGT.DB.CD.VAL.DTL.INFO" /></div> <!-- 접속대상DB 코드값 상세정보 -->
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
				     <script type="text/javascript">createIBSheet("grid_sub_dbconntrg", "100%", "250px");</script>            
				</div>
			</td>
		</tr>
		</tbody>
		</table>
		</div>
	
		<div style="float:right; width:49%; ">
		<div class="stit"><s:message code="META.DMN.CD.VAL.DTL.INFO" /></div> <!-- 메타도메인 코드값 상세정보 -->
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
				    <script type="text/javascript">createIBSheet("grid_sub_meta", "100%", "250px")</script>
				</div>

			</td>
			</tr>
			</tbody>
			</table>
		</div>
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>