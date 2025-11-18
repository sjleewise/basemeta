<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
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
//최근 선택 row

$(document).ready(function(){
	//그리드 초기화
	initsubgrid_codecfcsysitemhist();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_ddltblchange.FitColWidth();
});


function initsubgrid_codecfcsysitemhist() {

    with(grid_sub_codecfcsysitemhist){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        var headers = [
                    {Text:"<s:message code='META.HEADER.CODECFCSYSITEMHIST.DTL'/>", Align:"Center"}
                    /* No.|코드분류체계ID|만료일시|시작일시|코드분류체계유형|코드분류체계명|분류체계항목순번|분류체계항목명|형식|자릿수|구분자|참조테이블|참조컬럼|참조ID|설명|버전|등록유형코드|최초요청일시|최초요청자명|요청일시|요청자명|승인일시|승인자명 */
                ];
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                   	 	{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
                   		{Type:"Text",   Width:100, SaveName:"codeCfcSysId",   	 Align:"Left",   Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"expDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
						{Type:"Date",   Width:100,   SaveName:"strDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
						{Type:"Combo",   Width:150,   SaveName:"codeCfcSysCd",	 	Align:"Left", Edit:0},
	                    {Type:"Text",   Width:150, SaveName:"codeCfcSysLnm",   	 Align:"Left",   Edit:0},
	                    {Type:"Text",    Width:100, SaveName:"codeCfcSysItemSeq"   ,Align:"Left",   Edit:0},
						{Type:"Combo",    Width:100,  SaveName:"codeCfcSysItemCd"    ,Align:"Left",   Edit:0},
	                    {Type:"Text",    Width:100, SaveName:"codeCfcSysItemFrm"   ,Align:"Left",   Edit:0},
	                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemLen" ,Align:"Left",   Edit:0, Hidden:0},
	                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemSpt"    ,Align:"Left",   Edit:0},
	                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefTbl"      ,Align:"Left",   Edit:0},
	                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefCol"  ,Align:"Left",   Edit:0},
	                    {Type:"Text",    Width:100,  SaveName:"codeCfcSysItemRefId"  ,Align:"Left",   Edit:0},
						{Type:"Text",   Width:300,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
						{Type:"Text",   Width:80,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
						{Type:"Combo",   Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
						{Type:"Date",   Width:30,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:30,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
						{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Left", Edit:0, Hidden:1, ColMerge:0},
						{Type:"Date",   Width:30,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:30,  SaveName:"aprvUserNm",  Align:"Center", Edit:0, Hidden:1}
                   
                ];
                    
        InitColumns(cols);
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("codeCfcSysCd", 	${codeMap.codeCfcSysCdibs});
		SetColProperty("codeCfcSysItemCd", 	${codeMap.codeCfcSysItemCdibs});
      	//콤보 목록 설정...

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_codecfcsysitemhist);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddltblchange.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	initsubgrid_codecfcsysitemhist();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_codecfcsysitemhist_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_codecfcsysitemhist_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_codecfcsysitemhist_OnSearchEnd(code, message, stCode, stMsg) {
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

<!-- </head> -->
<!-- <body>     -->
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    

    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
<%--     <tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" /> --%>
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub_codecfcsysitemhist", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
