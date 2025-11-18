<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>
<script type="text/javascript">
$(document).ready(function() {
});

$(window).on('load',function() {
	initGridHelp();
	doAction("Search");
});


function initGridHelp()
{
    
    with(grid_sheet_help){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
		//var headText = "No.|상태|선택|기관명|정보시스템코드|정보시스템명"; //|만료일시|시작일시";
			//headText += "|관련법령(보유목적)|구축년도|운영부서명|담당자명|전화번호|이메일|작성자ID|작성일시|설명|버전";
        var headText = "<s:message code='HELP.CMT.HEAD.LST'/>";
            

        var headers = [
                    {Text:headText, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
		//Ellipsis
		//Wrap 줄바꿈
        var cols = [                        
                    {Type:"Text",     Width:60,   SaveName:"sno",       Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:60,   SaveName:"typCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text", 	  Width:60,   SaveName:"typNm",    Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",     Width:60,  SaveName:"itemNo",     Align:"Center", 	 Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"itemNm",      Align:"Center",   Edit:0},
                    {Type:"Text",     Width:60,  SaveName:"colctMthd",      Align:"Center",   Edit:0},
                    {Type:"Html",     Width:200,  SaveName:"regMth",      Align:"Left",   Edit:0}
                ];
                    
        InitColumns(cols);

//         FitColWidth();  
        
        SetExtendLastCol(1);  
          
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet_help);    
    //===========================

    grid_sheet_help.SetCountPosition(0);
}

function doAction(sAction)
{
    switch(sAction)
    {		    
        case "Search":
        	var param = $("#frmInput").serialize();
        	grid_sheet_help.DoSearch('<c:url value="/meta/admin/popup/getInfoSysHelp.do" />',param);
        	break;
    }
}

function grid_sheet_help_OnSearchEnd(code, message, stCode, stMsg) {
	
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} 
	grid_sheet_help.SetExtendLastCol(1);

	$("#typNm").text(grid_sheet_help.GetCellValue(1, "typNm"));
}
</script>
</head>

<body>
<form action="" id="frmInput" name="frmInput" method="post">
<input type="hidden" id="typCd" name="typCd" value="${typCd}" />
</form>
	<table style="width:990px; margin:0 auto; border:8px solid #c9d2d6; padding:3px;" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<td style="border-top:5px solid #39505e; background:#0087bd; height: 80px; padding: 0 26px;">
					<p style="font-size:20px; color:#fff;">[도움말] <span id="typNm"></span> 정보</p>
				</td>
			</tr>
			<tr>
				<td style="background:#fff; padding:25px; font-size:14px; color:#252525;"> 
					<%-- <img src="<c:url value='/images/help_01.png' />"  /> --%>
					<div id="grid_01" class="grid_01">
						<c:if test = "${typCd eq 'db' }">
					     <script type="text/javascript">createIBSheet("grid_sheet_help", "100%", "436px");</script>    
					    </c:if>  
					    <c:if test = "${typCd eq 'info' }">
					     <script type="text/javascript">createIBSheet("grid_sheet_help", "100%", "264px");</script>    
					    </c:if>
					    <c:if test = "${typCd eq 'tbl' }">
					     <script type="text/javascript">createIBSheet("grid_sheet_help", "100%", "664px");</script>    
					    </c:if>  
					    <c:if test = "${typCd eq 'col' }">
					     <script type="text/javascript">createIBSheet("grid_sheet_help", "100%", "940px");</script>    
					    </c:if>         
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	
</body>

</html>