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
<!-- <title>코드분류체계목록 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//======================================================
        // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
        //======================================================
        //imgConvert($('div.tab_navi a img'));
		
    
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initCodeCfcGrid();
		
	
});


$(window).resize(
    
    function(){
                
    	// codecfc_sheet.SetExtendLastCol(1);    
    }
);


function initCodeCfcGrid()
{
    
    with(codecfc_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.CODECFCSYS.ITEM.DTL1'/>";
        /* No.|분류체계유형|분류체계명|분류체계항목순번|분류체계항목명|형식|자릿수|구분자|참조테이블|참조컬럼|참조ID */
		    headtext += "<s:message code='META.HEADER.CODECFCSYS.ITEM.DTL2'/>";
		    /* |설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호 */

	    var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Combo",   Width:150,   SaveName:"codeCfcSysCd",	 	Align:"Left", Edit:0},
					{Type:"Text",    Width:100, SaveName:"codeCfcSysLnm"    ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100, SaveName:"codeCfcSysItemSeq"   ,Align:"Left",   Edit:0},
					{Type:"Combo",    Width:100,  SaveName:"codeCfcSysItemCd"    ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100, SaveName:"codeCfcSysItemFrm"   ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemLen" ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemSpt"    ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefTbl"      ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefCol"  ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefId"  ,Align:"Left",   Edit:0},
                    
                    {Type:"Text",   Width:120,  SaveName:"objDescn",	Align:"Left", 	 Edit:0},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		//분류체계유형
		SetColProperty("codeCfcSysCd", 	${codeMap.codeCfcSysCdibs});
		//분류체계항목
		SetColProperty("codeCfcSysItemCd", 	${codeMap.codeCfcSysItemCdibs});
	    
        InitComboNoMatchText(1, "");
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(codecfc_sheet);    
    //===========================
   
}

/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function codecfc_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function codecfc_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
  	
    
}


function codecfc_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function codecfc_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		
	}
	
}
</script>


<div style="clear:both; height:5px;"><span></span></div>
       
<!-- 그리드 입력 입력 -->
<div id="grid_02" class="grid_01">
	<script type="text/javascript">createIBSheet("codecfc_sheet", "100%", "250px");</script>            
</div>
<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>

