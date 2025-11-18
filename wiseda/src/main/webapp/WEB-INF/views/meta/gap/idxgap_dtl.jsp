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
function initsubgrid_dbc()
{
    
    with(grid_sub_dbc){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.IDXGAP.DTL1'/>"
        	/* No.|DB스키마ID|DB스키마명|테이블명|인덱스명|인덱스컬럼명| */
			+ "<s:message code='META.HEADER.IDXGAP.DTL2'/>";
			/* 컬럼순서|정렬유형|데이터타입|데이터길이|데이터소수점|인덱스설명 */

        var headers= [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dbSchId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"dbSchLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"dbcTblNm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"dbcIdxNm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"dbcIdxColNm"     ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"ord"     ,Align:"Right",   Edit:0},
                    {Type:"Combo",    Width:120,  SaveName:"sortType"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"dataType"     ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:120,  SaveName:"dataLen"     ,Align:"Right",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"dataPnt"     ,Align:"Right",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"descn"     ,Align:"Left",   Edit:0}
                ]; 
                    
        InitColumns(cols);

        SetColProperty("sortType", 	${codeMap.idxColSrtOrdCdibs});
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dbc);    
    //===========================
   
}


function initsubgrid_ddl() {

    with(grid_sub_ddl){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.IDXGAP.DTL.3'/>";//No.|인덱스ID|인덱스명|인덱스컬럼ID|인덱스컬럼명|컬럼순서|정렬유형|데이터타입|데이터자리수|데이터길이|데이터소수점|인덱스컬럼설명

        var headers= [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:40,   SaveName:"ddlIdxId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"ddlIdxPnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,   SaveName:"ddlIdxColId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:180,   SaveName:"ddlIdxColPnm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"ddlIdxColOrd",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:130,   SaveName:"sortTyp",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",    Width:150,  SaveName:"dataType"     ,Align:"Left",   Edit:0, Hidden:0},
	                    {Type:"Text",    Width:120,  SaveName:"dataLen"     ,Align:"Right",   Edit:0},
	                    {Type:"Text",    Width:150,  SaveName:"dataScal"     ,Align:"Right",   Edit:0},
						{Type:"Text",   Width:200,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0}
                   
                ];
        InitColumns(cols);
        SetColProperty("sortTyp", 	${codeMap.idxColSrtOrdCdibs});
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_ddl);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddl.ShowDebugMsg(-1);	
    	
}


$(window).on('load',function() {
	initsubgrid_dbc();
	initsubgrid_ddl();

	
});



</script>


<div id="tblLayer" class="">		
		<div style="float:left; width:49%; ">
		<div class="stit"><s:message code="DBC.IDEX.CLMN.DTL.INFO"/></div> <!-- DBC 인덱스컬럼 상세정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
		<caption>DBC</caption>
	     <colgroup>
	     <col style="width:100%;" />
	    </colgroup>
	     <tbody>  
		<tr>
			<td>
			 	<div id="grid_99" class="grid_01">
				     <script type="text/javascript">createIBSheet("grid_sub_dbc", "100%", "250px");</script>            
				</div>
			</td>
		</tr>
		</tbody>
		</table>
		</div>
	
		<div style="float:right; width:49%; ">
		<div class="stit"><s:message code="DDL.IDEX.CLMN.DTL.INFO"/></div> <!-- DDL 인덱스컬럼 상세정보 -->
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
				    <script type="text/javascript">createIBSheet("grid_sub_ddl", "100%", "250px")</script>
				</div>

			</td>
			</tr>
			</tbody>
			</table>
		</div>
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>