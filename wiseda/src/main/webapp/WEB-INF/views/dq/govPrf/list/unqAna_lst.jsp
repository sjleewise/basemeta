<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">

$(document).ready(function(){
// 	initprfpt02grid();
});


$(window).on('load',function(){
	
	setibsTabHeight($("#grid_02"), 100);
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
//     	grid_pt02.FitColWidth();
    }
);

function grid_pt02_excel()
{
	grid_pt02.Down2Excel({HiddenColumn:1, Merge:1});
}


function initprfpt02grid() {

    with(grid_pt02){
    	
     	var cfg = {SearchMode:2,Page:100};
       
     	SetConfig(cfg);
        
        SetMergeSheet(0);
        
        var headers = [
//                        {Text:"No.|최근분석차수|최근분석일시|진단대상ID|스키마ID|진단대상명|스키마명|중복키명|테이블명|테이블한글명|컬럼명|컬럼한글명|분석총건수|추정오류건수|추정오류율(%)|DPMO|SIGMA|프로파일ID", Align:"Center"}
                       {Text:"<s:message code='DQ.HEADER.UNQANA_LST'/>", Align:"Center"}
                   ];
        //No.|상태|선택|최근분석차수|최근분석일시|진단대상ID|스키마ID|진단대상명|스키마명|중복키명|테이블명|테이블한글명|분석총건수|추정오류건수|추정오류율(%)|DPMO|SIGMA|프로파일ID|작업ID|작업명|

           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
							{Type:"Seq",    Width:70,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
							{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0 ,Hidden:1},
							{Type:"CheckBox", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                     	    {Type:"Int",   Width:150,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0, ColMerge:1},
                       		{Type:"Date",   Width:150,  SaveName:"anaStrDtm",    	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss", ColMerge:1}, 
                       		{Type:"Text",   Width:150,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                       		{Type:"Text",   Width:150,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                      	    {Type:"Text",   Width:100,  SaveName:"dbConnTrgLnm",    	Align:"Left", Edit:0, Hidden:0, ColMerge:1}, 
                      	    {Type:"Text",   Width:150,  SaveName:"dbSchLnm",    	Align:"Left", Edit:0, Hidden:0, ColMerge:1}, 
                      	    {Type:"Text",   Width:150,  SaveName:"objNm",    	Align:"Left", Edit:0, Hidden:0, ColMerge:0}, 
                       		{Type:"Text",   Width:150,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
		                    {Type:"Text",   Width:150,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0, ColMerge:0,Hidden:1},
// 		                    {Type:"Text",   Width:150,  SaveName:"dbcColNm",    	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
// 		                    {Type:"Text",   Width:150,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
							{Type:"Int",     Width:150,  SaveName:"anaCnt",    	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
							{Type:"Int",     Width:150,  SaveName:"esnErCnt",    	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
							{Type:"Float",     Width:150,  SaveName:"colErrRate",    	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
							{Type:"Int",     Width:150,  SaveName:"dpmo",    	Align:"Center", Edit:0, Hidden:1, ColMerge:0},
							{Type:"Float",     Width:150,  SaveName:"sigma",    	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
							{Type:"Text",   Width:100,  SaveName:"prfId",    	Align:"Center", Edit:0, Hidden:1, ColMerge:0},
							{Type:"Text",   Width:100,  SaveName:"shdJobId",    	Align:"Center", Edit:0, Hidden:1},
							{Type:"Text",   Width:100,  SaveName:"etcJobNm",    	Align:"Center", Edit:0, Hidden:1},
							{Type:"Float",     Width:150,  SaveName:"stndRate",    	Align:"Center", Edit:0, Hidden:0, ColMerge:0}
                      
                ];
        
      
        
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...	     
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_pt02);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_pt02.ShowDebugMsg(-1);	
    	
}


/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_pt02_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	var param = "dbConnTrgId="+grid_pt02.GetCellValue(row, "dbConnTrgId");
	 param += "&dbSchId="+grid_pt02.GetCellValue(row, "dbSchId");
	 param +="&dbcTblNm="+grid_pt02.GetCellValue(row, "dbcTblNm");
	 param +="&objNm="+grid_pt02.GetCellValue(row, "objNm");
	 param +="&prfId="+grid_pt02.GetCellValue(row, "prfId");
	 param +="&anaDgr="+grid_pt02.GetCellValue(row, "anaDgr");
	 param +="&anaStrDtm="+grid_pt02.GetCellValue(row, "anaStrDtm");
	 param +="&prfKndCd="+"PT02";
// 	 param +="&prfKndCd="+grid_pt01.GetCellValue(row, "prfId");
	
    //테이블 컬럼 프로파일 구분
	prfDtlPopup(param);	
}

function grid_pt02_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//분석일시 정보를 위한 선택된 row 번호 저장
// 	$("#frmAnaTrg #sheetRow").val(row);
// 	$("#frmSearch #prfId").val(grid_pt02.GetCellValue(row, "prfId"));
// 	$("#frmSearch #anaStrDtm").val(grid_pt02.GetCellValue(row, "anaStrDtm"));
// 	$("#frmSearch #prfKndCd").val("PT02");

	anaStrDtms = grid_pt02.GetCellValue(row, "anaStrDtm");
	objId = grid_pt02.GetCellValue(row, "prfId");
	prfKndCds = "PT02";
}

function grid_pt02_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}
</script>


<div class="grid_02" id="grid_02">
     <script type="text/javascript">createIBSheet("grid_pt02", "100%", "420px");</script>
</div>

