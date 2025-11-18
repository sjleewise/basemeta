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

		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_ddltblrelchange.FitColWidth();
});


function initsubgrid_ddltblrelchange() {

    with(grid_sub_ddltblrelchange){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        var headers = [
                    {Text:"<s:message code='META.HEADER.DDLTBLRELCHANGE.DTL'/>", Align:"Center"}
                    /* No.|DDL관계ID|만료일시|시작일시|DDL관계명|DDL관계논리명|관계유형|카디널리티유형|Parent Optionality유형|부모엔터티주제영역ID|부모엔터티주제영역명|부모엔터티ID|부모엔터티명|부모엔터티속성ID|부모엔터티속성명|자식엔터티주제영역ID|자식엔터티주제영역명|자식엔터티ID|자식엔터티명|자식엔터티속성ID|자식엔터티속성명|요청번호|요청일련번호|요청상세일련번호|설명|버전|등록유형코드|최초요청일시|최초요청사용자명|요청일시|요청사용자명|승인일시|승인사용자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Text",   Width:40,   SaveName:"ddlRelId", 		Align:"Left", Edit:0, Hidden:1, ColMerge:0},
						{Type:"Date",   Width:80,   SaveName:"expDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
						{Type:"Date",   Width:80,   SaveName:"strDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
						{Type:"Text",   Width:140,   SaveName:"ddlRelPnm", 		Align:"Left", Edit:0, Hidden:0, ColMerge:1},
						{Type:"Text",   Width:140,   SaveName:"ddlRelLnm", 		Align:"Left", Edit:0, Hidden:0, ColMerge:1},
						{Type:"Combo",    Width:100,  SaveName:"relTypCd"     ,Align:"Center",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Combo",    Width:100,  SaveName:"crdTypCd"     ,Align:"Center",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Combo",    Width:100,  SaveName:"paOptTypCd"     ,Align:"Center",   Edit:0, Hidden:0, ColMerge:0},
	                    {Type:"Text",    Width:100,  SaveName:"paSubjId"     ,Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",    Width:100,  SaveName:"paSubjPnm"     ,Align:"Left",   Edit:0, ColMerge:0},
	                    {Type:"Text",    Width:100,  SaveName:"paEntyId"     ,Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",    Width:100,  SaveName:"paEntyPnm"     ,Align:"Left",   Edit:0, ColMerge:0},
	                    {Type:"Text",    Width:100,  SaveName:"paAttrId"     ,Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",    Width:100,  SaveName:"paAttrPnm"     ,Align:"Left",   Edit:0, ColMerge:0},
	                    {Type:"Text",    Width:100,  SaveName:"chSubjId"     ,Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",    Width:100,  SaveName:"chSubjPnm"     ,Align:"Left",   Edit:0, ColMerge:0},
	                    {Type:"Text",    Width:100,  SaveName:"chEntyId"     ,Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",    Width:100,  SaveName:"chEntyPnm"     ,Align:"Left",   Edit:0, ColMerge:0},
	                    {Type:"Text",    Width:100,  SaveName:"chAttrId"     ,Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",    Width:100,  SaveName:"chAttrPnm"     ,Align:"Left",   Edit:0, ColMerge:0},
						{Type:"Text",   Width:40,   SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstDtlSno",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:180,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
						{Type:"Text",   Width:80,   SaveName:"objVers",	 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
						{Type:"Combo",   Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0, ColMerge:0},
						{Type:"Date",   Width:30,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:30,   SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Date",   Width:80,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
						{Type:"Text",   Width:80,  SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:0, ColMerge:0}
                   
                ];
                    
        InitColumns(cols);
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("relTypCd", 	${codeMap.relTypCdibs});
        SetColProperty("crdTypCd", 	${codeMap.crdTypCdibs});
        SetColProperty("paOptTypCd", 	${codeMap.paOptTypCdibs});
		
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_ddltblrelchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddltblrelchange.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
// 	initsubgrid_ddltblrelchange();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_ddltblrelchange_OnDblClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	//선택한 상세정보를 가져온다...
	

}

function grid_sub_ddltblrelchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	
	

}


function grid_sub_ddltblrelchange_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_ddltblrelchange", "100%", "150px")</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
