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
// 	initprfpc01grid();
});


$(window).on('load',function(){

	setibsTabHeight($("#grid_03"), 100);
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
//     	grid_pc01.FitColWidth();
    }
);

function grid_pc01_excel()
{
	grid_pc01.Down2Excel({HiddenColumn:1, Merge:1});
}


function initprfpc01grid() {

    with(grid_pc01){
    	
     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(0);

        var headers = [
                       {Text:"<s:message code='DQ.HEADER.COLANA_LST'/>", Align:"Center"}
                   ];
           //No.|상태|선택|최근분석차수|최근분석일시|진단대상ID|스키마ID|진단대상명|진단대상(논리명)|스키마명|스키마(논리명)|테이블명|테이블한글명|컬럼명|컬럼한글명|순서|데이터빈도분석|최대최소값분석|NULL분석|"+"길이분석|분석건수|카디널리티건수|PRF_ID|작업ID|작업명|Null 건수|Space 건수|최소값1|최소값2|최소값3|최대값1|최대값2|최대값3|최소길이|최대길이

           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                       {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                       {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0 ,Hidden:1},
                       {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                       {Type:"Int",   Width:100,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0 ,ColMerge:1}, 
                       {Type:"Date",   Width:100,  SaveName:"anaStrDtm",    	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd hh:mm:ss" ,ColMerge:1}, 
                       {Type:"Text",   Width:150,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1 ,ColMerge:1},
                       {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1 ,ColMerge:1}, 
                       {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:0 ,ColMerge:1}, 
                       {Type:"Text",   Width:100,  SaveName:"dbConnTrgLnm",    	Align:"Left", Edit:0, Hidden:0 ,ColMerge:1}, 
                       {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:0 ,ColMerge:1}, 
                       {Type:"Text",   Width:100,  SaveName:"dbSchLnm",    	Align:"Left", Edit:0, Hidden:0 ,ColMerge:1}, 
                       {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:0 ,ColMerge:1},
                       {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0 ,ColMerge:1,Hidden:1},
                       {Type:"Text",   Width:100,  SaveName:"dbcColNm",    	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Text",   Width:100,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:1, ColMerge:0},
                       {Type:"Int",   Width:100,  SaveName:"ord",    	Align:"Center", Edit:0, Hidden:1, ColMerge:0}, 
                       {Type:"Text",   Width:100,  SaveName:"crdAnaYn",    	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Text",   Width:100,  SaveName:"minMaxValAnaYn",    	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Text",   Width:100,  SaveName:"aonlYn",    	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Text",   Width:100,  SaveName:"lenAnaYn",    	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Int",   Width:100,  SaveName:"anaCnt",    	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                       {Type:"Int",   Width:100,  SaveName:"esnErCnt",    	Align:"Right", Edit:0, Hidden:1, ColMerge:1},
                       {Type:"Text",   Width:100,  SaveName:"prfId",    	Align:"Center", Edit:0, Hidden:1, ColMerge:0},
                       {Type:"Text",   Width:100,  SaveName:"shdJobId",    	Align:"Center", Edit:0, Hidden:1},
                       {Type:"Text",   Width:100,  SaveName:"etcJobNm",    	Align:"Center", Edit:0, Hidden:1},
                       {Type:"Int",   Width:100,  SaveName:"nullCnt",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Int",   Width:100,  SaveName:"spaceCnt",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   Width:100,  SaveName:"minVal1",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   Width:100,  SaveName:"minVal2",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   Width:100,  SaveName:"minVal3",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   Width:100,  SaveName:"maxVal1",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   Width:100,  SaveName:"maxVal2",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   Width:100,  SaveName:"maxVal3",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Int",   Width:100,  SaveName:"minLen",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Int",   Width:100,  SaveName:"maxLen",    	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   	Width:50,   SaveName:"stddevVal", 	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   	Width:50,   SaveName:"varianceVal", 	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   	Width:50,   SaveName:"avgVal", 	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   	Width:50,   SaveName:"unqCnt", 	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   	Width:50,   SaveName:"minCntVal", 	Align:"Center", Edit:0, Hidden:0},
                       {Type:"Text",   	Width:50,   SaveName:"maxCntVal", 	Align:"Center", Edit:0, Hidden:0}
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
    init_sheet(grid_pc01);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_pc01.ShowDebugMsg(-1);	
    	
}


/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_pc01_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	var param = "dbConnTrgId="+grid_pc01.GetCellValue(row, "dbConnTrgId");
	 param += "&dbSchId="+grid_pc01.GetCellValue(row, "dbSchId");
	 param +="&dbcTblNm="+grid_pc01.GetCellValue(row, "dbcTblNm");
	 param +="&dbcColNm="+grid_pc01.GetCellValue(row, "dbcColNm");
	 param +="&prfId="+grid_pc01.GetCellValue(row, "prfId");
	 param +="&anaDgr="+grid_pc01.GetCellValue(row, "anaDgr");
	 param +="&anaStrDtm="+grid_pc01.GetCellValue(row, "anaStrDtm");
	 param +="&prfKndCd="+"PC01";
// 	 param +="&prfKndCd="+grid_pt01.GetCellValue(row, "prfId");
	
    //테이블 컬럼 프로파일 구분
	prfDtlPopup(param);	
}

function grid_pc01_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//분석일시 정보를 위한 선택된 row 번호 저장
// 	$("#frmSearch #sheetRow").val(row);
// 	$("#frmSearch #anaStrDtm").val(grid_pc01.GetCellValue(row, "anaStrDtm"));
// 	$("#frmSearch #prfId").val(grid_pc01.GetCellValue(row, "prfId"));
// 	$("#frmSearch #prfKndCd").val("PC01");

	anaStrDtms = grid_pc01.GetCellValue(row, "anaStrDtm");
	objId = grid_pc01.GetCellValue(row, "prfId");
	prfKndCds = "PC01";
}

function grid_pc01_OnSearchEnd(code, message, stCode, stMsg) {
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


<div class="grid_01" id="grid_03">
     <script type="text/javascript">createIBSheet("grid_pc01", "100%", "420px");</script>
</div>

