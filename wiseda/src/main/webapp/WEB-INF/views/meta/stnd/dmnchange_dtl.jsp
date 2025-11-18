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
 	initsubgrid_dmnchange();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_dmnchange.FitColWidth();
});


function initsubgrid_dmnchange() {

    with(grid_sub_dmnchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        var headers = [
                    {Text:"<s:message code='META.HEADER.DMNCHANGE.DTL'/>", Align:"Center"}
                ];
                //No.|도메인ID|만료일시|시작일시|표준분류|도메인논리명|도메인물리명|논리명기준구분|도메인그룹|인포타입|상위도메인ID|주제영역ID|목록엔티티ID|목록엔티티물리명|목록엔티티논리명|코드값유형코드|코드값부여방식코드|표준용어자동생성여부|데이터형식|담당사용자ID|도메인출처구분|요청번호|요청일련번호|설명|버전|등록유형|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID|비고
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
					{Type:"Text",   Width:100,   SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},
					{Type:"Date",   Width:100,   SaveName:"expDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Date",   Width:100,   SaveName:"strDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
					{Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Left", Edit:0},
					{Type:"Text",   Width:120,   SaveName:"dmnLnm", 		Align:"Left", Edit:0, Hidden:0, ColMerge:1},
					{Type:"Text",   Width:120,   SaveName:"dmnPnm", 		Align:"Left", Edit:0, Hidden:0, ColMerge:1},
					{Type:"Text",   Width:60,   SaveName:"lnmCriDs",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Combo",   Width:120,   SaveName:"dmngId",	 	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
					{Type:"Combo",   Width:120,   SaveName:"infotpId",	 	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
					{Type:"Text",   Width:60,   SaveName:"uppDmnId",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"subjId",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"lstEntyId",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"lstEntyPnm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"lstEntyLnm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",   Width:60,   SaveName:"cdValTypCd",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",   Width:60,   SaveName:"cdValIvwCd",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",   Width:120,   SaveName:"sditmAutoCrtYn",	 	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
					{Type:"Text",   Width:60,   SaveName:"dataFrm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"crgUserId",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"dmnOrgDs",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:250,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",   Width:60,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
					{Type:"Date",   Width:60,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:60,   SaveName:"frsRqstUserId",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:60,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:60,   SaveName:"rqstUserId",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:60,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:150,  SaveName:"aprvUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,  SaveName:"reMark",  Align:"Left", Edit:0, Hidden:0}
                  
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});


      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_dmnchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_dmnchange.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//도메인 변경 이력
// 	initsubgrid_dmnchange();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_dmnchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_dmnchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_dmnchange_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_dmnchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
