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
<title></title>

<script type="text/javascript">

EnterkeyProcess("Search");

var analyzeFlag = "N";
$(document).ready(function() {
	
		$( "#tabs" ).tabs();
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();

        $("#btnAnaCol").click(function(){ 
        	if(grid_sheet.GetTotalRows() == 0) return;
        	doAction("AnalyzeColGap");  
        }).show();

        $("#btnSave").click(function(){
        	if("Y" != analyzeFlag) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	        	
        }).hide();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
 
});

$(window).load(function() {
	initGridSheet();
});


$(window).resize(
    
    function(){
                
    	//setibsheight($("#grid_01"));
    }
);

function initGridSheet()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        //SetMergeSheet(msHeaderOnly);
        
        var headers = [
            {Text:"<s:message code='NIA.HEADER.COL.DEF.GAP'/>", Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
            {Type:"Seq",      Width:40,    SaveName:"ibsSeq",       Align:"Center", Edit:0},
            {Type:"Status",   Width:40,    SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
            {Type:"CheckBox", Width:50,    SaveName:"ibsCheck",     Align:"Center", Edit:0, Hidden:1, Sort:0},
            {Type:"Combo",     Width:100,   SaveName:"gapStatus",    Align:"Left",   Edit:0, Hidden:1},
            {Type:"Text",     Width:100,   SaveName:"orgNm",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"dbNm",    	    Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"tblPnm",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"tblLnm",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"colPnm",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"colLnm",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"stndCdYn",    	Align:"Left", Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"stndCdNm",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"colDescn",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"dataType",    	Align:"Left", Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"dataLen",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"dataScal",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"dataFrm",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"notNullYn",    Align:"Left", Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"pkYn",    	    Align:"Left", Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"fkYn",    	    Align:"Left", Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"constrnt",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"prsnYn",    	Align:"Left",   Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"encYn",    	Align:"Left", Edit:0, Hidden:0},
            {Type:"Text",     Width:100,   SaveName:"openYn",    	Align:"Left", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);

	   	SetColProperty("gapStatus", 	{ComboCode:"APLY|GAP", 	ComboText:"일치|불일치"}); /* 아니요|예 */
        
        InitComboNoMatchText(1, "");
        

        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//현행용어사전 조회
       
        	var param = "searchFlag=All";
        	var url = "<c:url value="/dq/model/getNiaPdmColList.do" />";
        	
        	grid_sheet.DoSearch(url, param);
        	
        	break;
        	
        case "AnalyzeColGap":	//컬럼정의 불일치 진단
	        
	    	var param = "";
	    	var url = "<c:url value="/dq/gap/getMtaColGapList.do" />";
	    	analyzeFlag = "Y";
	    	grid_sheet.DoSearch(url, param);
	    
    		break;
    	
        case "Save":	//진단결과 저장
        
	    	var param = $("#frmResult").serialize();
	    
	    	var url = "<c:url value="/dq/gap/regMtaColGapList.do" />";
	    	
	    	ajax2Json(url, param, ibscallback);
	    	
    		break;
    	
        case "Down2Excel":  //엑셀내려받기
        	grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName: '범정부 공통표준용어 사용 진단.xlsx'});
            break;
     
    }       
}

function grid_sheet_OnSearchEnd(code, message) {
    if(code == 0) {
    	if("Y" == analyzeFlag) {
    		$("#result_div").show();
    		grid_sheet.SetColHidden('gapStatus',0);
    		var colAplyCnt = grid_sheet.GetEtcData('colAplyCnt');
    		$("#colAplyCnt").val(colAplyCnt);
    		var colAplyRt = (Number($("#colAplyCnt").val()) / Number($("#totColCnt").val())) * 100;
    		
    		$("#colAplyRt").val(colAplyRt.toFixed(2));
	    	$("#btnSave").show();
    		
    	} else {
    		$("#result_div").hide();
    		grid_sheet.SetColHidden('gapStatus',1);
    		$("#totColCnt").val(grid_sheet.GetTotalRows());
    	}
	} else {
	    alert("조회 중에 오류가 발생하였습니다..");
	}
}

</script>
</head>

<body>
		<div class="divLstBtn" >	 
            <div class="bt03">
            	<button class="btn_search" 	id="btnSearch" 	name="btnSearch">현행용어사전 조회</button> <!-- 조회 -->
			    <button class="btn_search" 	id="btnAnaCol" 	name="btnAnaCol">범정부 공통표준용어 사용 진단</button>
			    <button class="btn_save" 	id="btnSave" 	name="btnSave">진단결과 저장</button> <!-- 저장 --> 
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->
	    	</div>
        </div>	

<div style="clear:both; height:10px;"><span></span></div>
<div id="result_div" style="display: none;">
        <div class="stit"><s:message code="ANA.RSLT" /></div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmResult" name="frmResult" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BFHD.INTG.INQ' />"> <!-- 사전통합조회 -->
                   <caption><s:message code="BFHD.INTG.INQ.FORM" /></caption> <!-- 사전통합 검색폼 -->
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:13%;" />
                   <col style="width:20%;" />
                   <col style="width:13%;" />
                   <col style="width:20%;" />
                   <col style="width:13%;" />
                   </colgroup>
                   <tbody>      
                            <tr>
						         <th scope="row"><label for="totColCnt">전체 컬럼수</label></th>
                                <td><input type="text" id="totColCnt" name="totColCnt" class="wd98p" readonly="readonly"/></td>
	                             <th scope="row"><label for="colAplyCnt">일치 컬럼수</label></th>
                                <td><input type="text" id="colAplyCnt" name="colAplyCnt" class="wd98p" readonly="readonly"/></td>
	                             <th scope="row"><label for="colAplyRt">사용율(%)</label></th>
                                <td><input type="text" id="colAplyRt" name="colAplyRt" class="wd98p" readonly="readonly"/></td>
                            </tr>
                            <tr>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
</div>

<div style="clear:both; height:10px;"><span></span></div>
	<div id="grid_01" class="grid_01">  
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "650px");</script>     
		<div style="clear:both; height:10px;"><span></span></div>
	</div>
	 
</body>
</html>