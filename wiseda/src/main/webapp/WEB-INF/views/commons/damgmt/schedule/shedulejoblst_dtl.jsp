<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//검색조건 display none
// 		$("div#col_search_div").hide();
// 		$("form#frmColInput").hide();
 
 	    $("#btnColExcelDown").click(function(){ 
	 	   	var params = { FileName : "JobList.xls",  SheetName : "Sheet", HiddenColumn:1, Merge:1} ;
	 		joblst_sheet.Down2Excel(params);
 	  	});
		
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initJobLstGrid();
	
});


$(window).resize();


function initJobLstGrid()
{
    
    with(joblst_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='COMMON.HEADER.SHEDULEJOBLST.DTL'/>";
        /* No.|작업명|시작일시|종료일시|진행시간(초)|작업상태|측정건수|오류건수|오류율(%)|실행자|에러정보|스케줄작업ID|프로파일종류코드 */
        					
 
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:40,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",     Width:300,  SaveName:"objNm",        Align:"Left", Edit:0},
                    {Type:"Date",     Width:130,  SaveName:"anaStrDtm",        Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",     Width:130,  SaveName:"anaEndDtm",        Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Int",     Width:80,  SaveName:"anaSec",        Align:"Center", Edit:0},
                    {Type:"Combo",     Width:100,  SaveName:"anaStsCd",        Align:"Center", Edit:0},
                    {Type:"Int",     Width:80,  SaveName:"anaCnt",        Align:"Right", Edit:0,Hidden:1},
                    {Type:"Int",     Width:80,  SaveName:"erCnt",        Align:"Rigth", Edit:0,Hidden:1},
                    {Type:"Float",     Width:80,  SaveName:"erRate",        Align:"Right", Edit:0,Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"userNm",        Align:"Left", Edit:0},
                    {Type:"Text",     Width:250,  SaveName:"anaErMsg",        Align:"Left", Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"shdJobId",        Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:150,  SaveName:"prfKndCd",        Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
        
        SetColProperty("anaStsCd", ${codeMap.anaStsCdibs});
        SetColProperty("shdKndCd", ${codeMap.schdKndCdibs});

        InitComboNoMatchText(1, "");
        
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(joblst_sheet);    
    //===========================
   
}


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function joblst_sheet_OnDblClick(row, col, value, cellx, celly) {
//     alert("colh dblclick event");
	if(row < 1) return;
	
	if($("#jobfrmSearch #shdKndCd").val() == "QB"){
	 	var param = "&brId="+joblst_sheet.GetCellValue(row, "shdJobId");
	 	     param += "&objId="+joblst_sheet.GetCellValue(row, "shdJobId");
		     param += "&objDate="+joblst_sheet.GetCellValue(row, "anaStrDtm").replace(/ /g, ''); 
		     param += "&objIdCol=BR_ID";		  
	         param += "&objResTbl=WAM_BR_RESULT";
		     param += "&objErrTbl=WAM_BR_ERR_DATA";
		     param += "&erDataSnoCol=ER_DATA_SNO";
		     
	   	var url = '<c:url value="/dq/bizrule/popup/bizrule_dtl_pop.do"/>';
	 	var popwin = OpenModal(url+"?"+param, "bizruleDtlPop",  1000, 640, "yes"); 
		
	}else if($("#jobfrmSearch #shdKndCd").val() == "QP"){
		var param = "prfId="+joblst_sheet.GetCellValue(row, "shdJobId");
			 param += "&anaDgr="+$("#jobfrmSearch #anaDgr").val();
			 param += "&anaStrDtm="+joblst_sheet.GetCellValue(row, "anaStrDtm");
			 param += "&prfKndCd="+joblst_sheet.GetCellValue(row, "prfKndCd");
			 
			var url = '<c:url value="/dq/profile/popup/prfdtl_pop.do"/>';
			var popwin = OpenModal(url+"?"+param, "prfDtlPop",  1100, 750, "yes"); 
	}else{
		// 작업 정보가 존재하지 않습니다.
		var message = "<s:message code="MSG.NODATA" />";
		showMsgBox("MSG", message);
	}
	
}

function joblst_sheet_OnClick(row, col, value, cellx, celly) {
//     alert("colh click event");
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
}

function joblst_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
// 		grid_sheet_OnClick(1);
	}
}

</script>
<!-- </head> -->

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>
<div id="search_div">
	<form id="jobfrmSearch" name="jobfrmSearch" method="post">
				<input type="hidden" name="shdId" id="shdId" />
				<input type="hidden" name="shdStrDtm" id="shdStrDtm"  />
				<input type="hidden" name="shdKndCd" id="shdKndCd"  />
				<input type="hidden" name="shdJobId" id="shdJobId" />
				<input type="hidden" name="prfKndCd" id="prfKndCd" />
				<input type="hidden" name="anaDgr" id="anaDgr" />
</form>


<div class="divLstBtn" >	 
	<div class="bt02">
       <button class="btn_excel_down" id="btnColExcelDown" name="btnColExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
 	</div>
 </div>
 <div style="clear:both; height:5px;"><span></span></div>
 
</div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_02" class="grid_01">
	     <script type="text/javascript">createIBSheet("joblst_sheet", "100%", "300px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "joblst_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>
<!-- </body> -->
<!-- </html> -->