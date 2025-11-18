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
 	initsubgrid_sditmchange();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_sditmchange.FitColWidth();
});


function initsubgrid_sditmchange() {

    with(grid_sub_sditmchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.SDITMCHANGE.DTL'/>", Align:"Center"}
                ];
                //No.|표준용어ID|만료일시|시작일시|표준분류|표준용어논리명|표준용어물리명|논리명기준구분|도메인ID|도메인그룹|인포타입|인포타입변경여부|암호화여부|요청번호|요청일련번호|등록유형|버전|설명|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID|비고
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
            		
                    {Type:"Text",   Width:100,  SaveName:"sditmId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",   Width:100,  SaveName:"expDtm",   	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
					{Type:"Date",   Width:100,  SaveName:"strDtm",   	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,  SaveName:"sditmLnm",   	Align:"Left", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"sditmPnm",   	Align:"Left", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"lnmCriDs",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"dmnId",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Combo",   Width:100,  SaveName:"dmngId",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Combo",   Width:100,  SaveName:"infotpId",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",   Width:100,  SaveName:"infotpChgYn",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",   Width:80,  SaveName:"encYn",   	Align:"Center", Edit:0, Hidden:1},

					{Type:"Text",   Width:100,  SaveName:"rqstNo",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstSno",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",   Width:80,  SaveName:"regTypCd",   	Align:"Center", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"objVers",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:250,  SaveName:"objDescn",   	Align:"Left", Edit:0},
					{Type:"Date",   Width:100,  SaveName:"frsRqstDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,  SaveName:"frsRqstUserId",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:100,  SaveName:"rqstDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,  SaveName:"rqstUserId",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:100,  SaveName:"aprvDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,  SaveName:"aprvUserId",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,  SaveName:"reMark",    Align:"Left", Edit:0, Hidden:0}
                   
                ];
                    
        InitColumns(cols);
        SetColProperty("infotpChgYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		//SetColProperty("persInfoCnvYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
      	//콤보 목록 설정...
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_sditmchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_sditmchange.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//표준용어 - 변경이력 그리드 초기화
// 	initsubgrid_sditmchange();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_sditmchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_sditmchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_sditmchange_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_sditmchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
