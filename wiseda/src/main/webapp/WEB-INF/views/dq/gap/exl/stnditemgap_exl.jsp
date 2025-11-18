<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title>비표준컬럼분석 조회</title>

<script type="text/javascript">

EnterkeyProcess("Search");


// var connTrgSchJson = ${codeMap.connTrgSch} ;                 
$(document).ready(function() {
	
		
        
});

$(window).load(function() {
	//그리드 초기화 
	initSditmGrid();
	sditm_sheet.SetCellBackColor(0,"sditmPnm","#6F5747");
	sditm_sheet.SetCellBackColor(0,"sditmLnm","#6F5747");
	sditm_sheet.SetCellBackColor(0,"infotpLnm","#6F5747");
	sditm_sheet.SetCellBackColor(0,"dmnDataType","#6F5747");
	sditm_sheet.SetCellBackColor(0,"dmnDataLen","#6F5747");
	sditm_sheet.SetCellBackColor(0,"dmnDataScal","#6F5747");
	
});


$(window).resize(
    
    function(){
                
    	//setibsheight($("#grid_01"));
    }
);


function initSditmGrid()
{
    
    with(sditm_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext1 = "";
//         var headtext2 = "";
                
        headtext1 = "<s:message code='META.HEADER.GAP.STNDITEM.LST'/>";
        
        headtext1 = headtext1.replace(/[' ']/gi,'');
//         headtext2 = headtext2.replace(/[' ']/gi,'');
        
        SetMergeSheet(msHeaderOnly);
        
        var headers = [
					{Text:headtext1, Align:"Center"},   
//                     {Text:headtext2, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",         Align:"Center",  Edit:0},        
                    {Type:"Status", Width:50,   SaveName:"ibsStatus",         Align:"Center",  Edit:0, Hidden:1},     
                    {Type:"Combo",   Width:80,  SaveName:"gapStatus", 	   Align:"Center",  Edit:0},                     
                    {Type:"Text",   Width:130,  SaveName:"orgNm",          Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"dbNm",           Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"tblPnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"tblLnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"colPnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"colLnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"sditmPnm",         Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"sditmLnm",         Align:"Left",    Edit:0}, 
                ];
                    
        InitColumns(cols);
        SetColProperty("gapStatus", 	{ComboCode:"1|0", ComboText:"준수|미준수"}); /* 아니요|예 */
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
//         SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(sditm_sheet);    
    //===========================
   
}







/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function sditm_sheet_OnDblClick(row, col, value, CellX, CellY, CellW, CellH) {
//     alert("tbl dbl click");
	if(row < 1) return;
}

function sditm_sheet_OnClick(row, col, value, cellx, celly) {
// 	alert("tbl click event");

	return;
}



function sditm_sheet_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	    $("#frmInputSditm #anaCd").val(sditm_sheet.GetEtcData("anaCd"));
		$("#frmInputSditm #anaDtm").val(sditm_sheet.GetEtcData("anaDtm"));
		$("#frmInputSditm #totColCnt").val(sditm_sheet.GetEtcData("totColCnt"));
		$("#frmInputSditm #colAplyCnt").val(sditm_sheet.GetEtcData("colAplyCnt"));
		$("#frmInputSditm #colAplyRt").val(sditm_sheet.GetEtcData("colAplyRt"));
	}


	
}







</script>
</head>

<body>

<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
          <form id="frmInputSditm" name="frmInputDBSditm">
        	<div class="tb_basic2">			
				<table border="0" cellspacing="0" cellpadding="0"  summary="">
<%-- 		        <colgroup> --%>
<%-- 		            <col style="width:12%;"> --%>
<%-- 		            <col style="width:38%;"> --%>
<%-- 		            <col style="width:12%;"> --%>
<%-- 		            <col style="width:38%;"> --%>
<%-- 		        </colgroup> --%>
			      <tr>
			        <th>진단내용</th><!-- 업무규칙명 -->
			        <td>
			            <input type="hidden" id="anaCd" name="anaCd" value="03" readonly>
			            <select id="anaCdSelect" name="anaCdSelect" class="wd98p" disabled>
			              <c:forEach var="code" items="${codeMap.niaStndAnaCd}" varStatus="status" >
			                    <c:if test ="${code.codeCd =='03'}">
                                <option value="${code.codeCd}">${code.codeLnm}</option>
                                </c:if>
                          </c:forEach>
			            </select>
			        </td>
			        <th>진단일자</th><!-- 업무규칙명 -->
			        <td>
			            <input type="text" id="anaDtm" name="anaDtm" readonly>
			        </td>
			        <th>전체 컬럼수</th><!-- 업무규칙명 -->
			        <td>
			            <input type="text" id="totColCnt" name="totColCnt" readonly>
			        </td>
			        <th>표준용어 일치건수</th><!-- 업무규칙명 -->
			        <td>
			            <input type="text" id="colAplyCnt" name="colAplyCnt" readonly>
			        </td>
			        <th>표준적용율(%)</th><!-- 업무규칙명 -->
			        <td>
			            <input type="text" id="colAplyRt" name="colAplyRt" readonly>
			        </td>
		          </tr>
		        </table>
			 </div>
        </form>
	<!-- 그리드 입력 입력 -->
	    <div style="clear:both; height:5px;"><span></span></div>
	<div id="grid_01" class="grid_01">  
	     <script type="text/javascript">createIBSheet("sditm_sheet", "100%", "450px");</script>     
		<div style="clear:both; height:10px;"><span></span></div>
	</div>
	<!-- 그리드 입력 입력 -->
</div>

</body>
</html>