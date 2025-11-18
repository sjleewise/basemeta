<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
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
	
		//검색조건 display none
// 		$("div#col_search_div").hide();
// 		$("form#frmColInput").hide();
 		$("#btnColExcelDown").hide();
		
    }
);

$(window).on('load',function() {
	//그리드 초기화 
// 	initTblHlGrid();
	
});


$(window).resize();


function initTblHlGrid()
{
    
    with(tblh_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        var headtext = "<s:message code='META.HEADER.ASIS.TDL.HIST.DTL'/>";
    	//No.|만료일시|시작일시|상위주제영역|주제영역|테이블물리명|테이블논리명|등록유형|표준여부|파티션테이블여부|DW대상테이블여부|담당자|최초요청자|최초요청일시|요청자|요청일시|승인자|승인일시|버전|설명|보관주기|백업주기|삭제기준|삭제방법
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Date",    Width:100,  SaveName:"expDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd", ColMerge:0},	
                    {Type:"Date",    Width:100,  SaveName:"strDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd", ColMerge:0},	
                    {Type:"Text",    Width:100,  SaveName:"uppSubjLnm"     ,Align:"Left",   Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Text",    Width:100,  SaveName:"subjLnm"     ,Align:"Left",   Edit:0, ColMerge:1},
                    {Type:"Text",    Width:150,  SaveName:"pdmTblPnm"     ,Align:"Left",   Edit:0, ColMerge:1},
                    {Type:"Text",    Width:150,  SaveName:"pdmTblLnm"     ,Align:"Left",   Edit:0, ColMerge:1},
                    {Type:"Combo",    Width:50,  SaveName:"regTypCd"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Combo",    Width:50,  SaveName:"stdAplYn"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Combo",    Width:100,  SaveName:"partTblYn"     ,Align:"Center",   Edit:0, ColMerge:0, Hidden :1},
                    {Type:"Combo",    Width:100,  SaveName:"dwTrgTblYn"     ,Align:"Center",   Edit:0, ColMerge:0, Hidden :1},
                    {Type:"Text",    Width:100,  SaveName:"crgUserNm"     ,Align:"Left",   Edit:0, ColMerge:0, Hidden :1},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstUserNm"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Date",    Width:100,  SaveName:"frsRqstDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserNm"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Date",    Width:100,  SaveName:"rqstDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserNm"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Date",    Width:100,  SaveName:"aprvDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Text",    Width:50,  SaveName:"objVers"     ,Align:"Center",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:300,  SaveName:"objDescn"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"ctdFcy"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"bckFcy"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"delCri"     ,Align:"Left",   Edit:0, ColMerge:0},
                    {Type:"Text",    Width:100,  SaveName:"delMtd"     ,Align:"Left",   Edit:0, ColMerge:0},
                ];
                    
        InitColumns(cols);
        
        SetColProperty("dwTrgTblYn", 	{ComboCode:"Y|", 	ComboText:"아니요|예"});
        SetColProperty("partTblYn", 	{ComboCode:"Y|", 	ComboText:"아니요|예"});
        SetColProperty("stdAplYn", 	{ComboCode:"Y|", 	ComboText:"적용|비적용"});
        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"신규|변경|삭제"});
//         SetColProperty("regTypCd", 	${codeMap.regtypcd});

        InitComboNoMatchText(1, "");
        
//         FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(tblh_sheet);    
    //===========================
   
}

//화면상의 모든 액션은 여기서 처리...
function doActionTblH(sAction)
{
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
//         	var param = $('#frmSearch').serialize();
        	var param = $('#frmSearch').serialize();
//         	alert(param);
        	tblh_sheet.DoSearch("<c:url value="/meta/model/getasishistbllist.do" />", param);
//         	tblh_sheet.DoSearchScript("testJsonlist");
}
 


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function tblh_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	return;
}

function tblh_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
}

</script>
<!-- </head> -->

<!-- <body> -->
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("tblh_sheet", "100%", "200px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "tblh_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- </body> -->
<!-- </html> -->