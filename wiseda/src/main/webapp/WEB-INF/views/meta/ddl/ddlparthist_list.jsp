<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
       // 엑셀내리기 Event Bind
		$("#btnDdlPartHistExcelDown").click(function(){
			part_hist_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"ddlparthist.xls"});
		});
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initPartHistGrid();
});


$(window).resize(
    function(){
    }
);


function initPartHistGrid()
{
    
    with(part_hist_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        
        var headtext  = "<s:message code='META.HEADER.DDLPART.HIST.LST'/>";
        //No.|만료일시|시작일시|시스템영역아이디|시스템영역명|주제영역명|SR번호|서브프로젝트번호|DDL테이블ID|DB접속대상명|DB스키마명|테이블물리명|테이블논리명|테이블스페이스|스크립트정보|DDL파티션ID|파티션유형|파티션키|서브파티션유형|서브파티션키|요청번호|요청일련번호|전일배치CUD|설명|요청사유|등록유형|버전|요청자ID|요청자명|요청부서|요청일시|승인자ID|승인자명|승인일시|최초요청일시|최초요청자ID|적용요청일자|적용요청구분

	    var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",	      Align:"Center", Edit:0},
                    {Type:"Date",   Width:100,   SaveName:"expDtm", 	  Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
					{Type:"Date",   Width:100,   SaveName:"strDtm", 	  Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
					
					{Type:"Text",   Width:100,  SaveName:"sysAreaId", 	  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:80,   SaveName:"sysAreaLnm", 	  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"fullPath",	  Align:"Left", Edit:0},
					
					{Type:"Text",   Width:100,  SaveName:"srMngNo", 	  Align:"Left", Edit:0, Hidden:1, ColMerge:1},
					{Type:"Text",   Width:100,  SaveName:"prjMngNo", 	  Align:"Left", Edit:0, Hidden:1, ColMerge:1},
					{Type:"Text",   Width:60,   SaveName:"ddlTblId",	  Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm",  Align:"Left",   Edit:0},
					{Type:"Text",   Width:120,  SaveName:"dbSchPnm",      Align:"Left",   Edit:0},
					{Type:"Text",   Width:140,  SaveName:"ddlTblPnm",     Align:"Left",   Edit:0}, 
					{Type:"Text",   Width:100,  SaveName:"ddlTblLnm", 	  Align:"Left",   Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"tblSpacPnm",    Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"scrtInfo",  	  Align:"Left",   Edit:0, Hidden:1},
					
					{Type:"Text",   Width:40,   SaveName:"ddlPartId", 	  Align:"Left",   Edit:0, Hidden:1},
					{Type:"Combo",  Width:60,   SaveName:"partTypCd", 	  Align:"Left",   Edit:0, KeyField:0},
					{Type:"Text",   Width:120,  SaveName:"partKey",       Align:"Left",   Edit:0, KeyField:0},
					{Type:"Combo",  Width:60,   SaveName:"subPartTypCd",  Align:"Left",   Edit:0},
					{Type:"Text",   Width:120,  SaveName:"subPartKey",    Align:"Left",   Edit:0},

                    {Type:"Text",   Width:40,   SaveName:"rqstNo",	 	  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	  Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",  Width:80,   SaveName:"cudYn",	 	  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:400,  SaveName:"objDescn",	  Align:"Left",   Edit:0, Hidden:0},
					
					{Type:"Text",   Width:100,  SaveName:"rqstResn",	  Align:"Left",   Edit:1},
					{Type:"Combo",  Width:80,   SaveName:"regTypCd",	  Align:"Center", Edit:0, Hidden:0, ColMerge:0},
					{Type:"Text",   Width:80,   SaveName:"objVers",	 	  Align:"Center", Edit:0, Hidden:0, ColMerge:0},
		   
                    {Type:"Text",   Width:100,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},          
                    {Type:"Text",   Width:80,   SaveName:"rqstUserNm",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"rqstUserDept",  Align:"Left", Edit:1},
                    {Type:"Date",   Width:100,  SaveName:"rqstDtm",       Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss",},
                    {Type:"Text",   Width:100,  SaveName:"aprvUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"aprvUserNm",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Date",   Width:100,  SaveName:"aprvDtm",       Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss",},
                    {Type:"Date",   Width:100,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss",}, 
                    {Type:"Text",   Width:100,  SaveName:"frsRqstUserId", Align:"Center", Edit:0, Hidden:1}, 		   
                    
					{Type:"Text",   Width:100,   SaveName:"aplReqdt",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",  Width:100,   SaveName:"aplReqTypCd",	Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
        
        SetColHidden("rqstNo",1);
        SetColHidden("rqstSno",1);
        
		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("partTypCd", ${codeMap.partTypCdibs});
		SetColProperty("subPartTypCd", ${codeMap.partTypCdibs});
		SetColProperty("cudYn", 	{ComboCode:"N|Y", 	ComboText:"N|Y"});
		SetColProperty("aplReqTypCd", 	${codeMap.aplReqTypCdibs});
      
        // FitColWidth();
        SetSheetHeight(250);
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(part_hist_sheet);    
    //===========================
   
}



//화면상의 모든 액션은 여기서 처리...
function doActionPartMain(sAction)
{
        
    switch(sAction)
    {
        case "Search":	
        	var param = $('#frmSearch').serialize();
        	part_hist_sheet.DoSearch("<c:url value="/meta/ddl/getddlidxcolrqstlist.do" />", param);
        	break;
        	
        case "Down2Excel":  //엑셀내려받기
            part_hist_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
    }       
}


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function part_hist_sheet_OnDblClick(row, col, value, cellx, celly) {
}

function part_hist_sheet_OnClick(row, col, value, cellx, celly) {
}


function part_hist_sheet_OnSaveEnd(code, message) {
}

function part_hist_sheet_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	}
}
</script>

     <!-- 조회버튼영역  -->
     <div class="divLstBtn" style="display: none;">
            <div class="bt03">
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnDdlPartHistExcelDown" name="btnDdlPartHistExcelDown">엑셀 내리기</button>                       
	    	</div>
	 </div>
     <!-- 버튼영역  -->
	<div style="clear:both; height:5px;"><span></span></div>
	<div class="tb_comment"></div>
	<!-- 그리드 입력 입력 -->
	<div id="part_hist_grid" class="grid_01">
		<script type="text/javascript">createIBSheet("part_hist_sheet", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>


<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- </body> -->
<!-- </html> -->