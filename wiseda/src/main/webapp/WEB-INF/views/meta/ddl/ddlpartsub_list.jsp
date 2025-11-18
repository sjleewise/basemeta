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
<!-- <title>DDL파티션 서브 리스트</title> -->

<script type="text/javascript">
$(document).ready(function() {
        // 엑셀내리기 Event Bind
		$("#btnDdlPartSubExcelDown").click(function(){
			sub_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"ddlpartsub.xls"});
		});
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initPartSubGrid();
});


$(window).resize(
    function(){
    }
);


function initPartSubGrid()
{
    
    with(sub_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.DDLPART.SUB.LST'/>";
        //No.|파티션ID|주파티션ID|서브파티션ID|DDL테이블ID|DDL테이블물리명|파티션명|서브파티션명|구분값|테이블스페이스|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호

	    var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Text",    Width:40,  SaveName:"ddlPartId"    ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:40,  SaveName:"ddlMainPartId"    ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:40,  SaveName:"ddlSubPartId"    ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"ddlTblId",	 Align:"Left",   Edit:0, Hidden:1},
					
					{Type:"Text",   Width:140, SaveName:"ddlTblPnm",     Align:"Left",   Edit:1},
					{Type:"Text",    Width:150,  SaveName:"ddlPartPnm"    ,Align:"Left",   Edit:1},
					{Type:"Text",    Width:200,  SaveName:"ddlPartSubPnm"    ,Align:"Left",   Edit:1},
                    {Type:"Text",    Width:200, SaveName:"ddlPartSubVal"   ,Align:"Left",   Edit:1},
                    {Type:"Text",   Width:120,  SaveName:"tblSpacPnm"	,Align:"Left",   Edit:0, Hidden:0},

					{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",  Align:"Center", Edit:0}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",  Align:"Center", Edit:0},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

        SetColHidden("rqstNo",1);
        SetColHidden("rqstSno",1);
        SetColHidden("rqstDtlSno",1);
      
        // FitColWidth();
        SetSheetHeight(250);
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(sub_sheet);    
    //===========================
   
}


//화면상의 모든 액션은 여기서 처리...
function doActionSub(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
        	var param = $('#frmSearch').serialize();
        	sub_sheet.DoSearch("<c:url value="/meta/ddl/getddlidxcolrqstlist.do" />", param);
        	break;
        	
       
        case "Down2Excel":  //엑셀내려받기
            sub_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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
function sub_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function sub_sheet_OnClick(row, col, value, cellx, celly) {
    if(row < 1) return;
}


function sub_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function sub_sheet_OnSearchEnd(code, message) {
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
				<button class="btn_excel_down" id="btnDdlPartSubExcelDown" name="btnDdlPartSubExcelDown">엑셀 내리기</button>                       
	    	</div>
	 </div>
     <!-- 버튼영역  -->
	<div style="clear:both; height:5px;"><span></span></div>
	<div class="tb_comment"></div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_03" class="grid_01">
	     <script type="text/javascript">createIBSheet("sub_sheet", "100%", "150px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>


<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- </body> -->
<!-- </html> -->