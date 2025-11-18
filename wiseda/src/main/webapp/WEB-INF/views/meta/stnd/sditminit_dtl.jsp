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
// 	initsubgrid_sditminit();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_sditminit.FitColWidth();
});


function initsubgrid_sditminit() {

    with(grid_sub_sditminit){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                       {Text:"<s:message code='META.HEADER.SDITMINIT.DTL'/>", Align:"Center"}
                   ];
                   //No.|표준단어객체ID|표준단어일련번호|표준단어ID|요청번호|요청일련번호|단어유형|표준분류|단어논리명|단어물리명|영문의미|설명|비고
           
           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                       {Type:"Seq",    Width:50,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
                       {Type:"Text",   Width:120,  SaveName:"objId",    Align:"Left", Edit:0, Hidden:1},
                       {Type:"Text",   Width:90,  SaveName:"wdSno",    Align:"Left", Edit:0, Hidden:1}, 
                       {Type:"Text",   Width:80,  SaveName:"stwdId",    Align:"Left", Edit:0, Hidden:1}, 
                       {Type:"Text",   Width:90,  SaveName:"rqstNo",    Align:"Center", Edit:0, Hidden:1},
                       {Type:"Text",   Width:90,  SaveName:"rqstSno",    Align:"Center", Edit:0, Hidden:1},
                       {Type:"Combo",   Width:140,  SaveName:"bizDtlCd",    Align:"Center", Edit:0, Hidden:0},
                       {Type:"Combo",   Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0},
                       {Type:"Text",   Width:140,  SaveName:"stwdLnm",    Align:"Left", Edit:0, Hidden:0},
                       {Type:"Text",   Width:140,  SaveName:"stwdPnm",    Align:"Left", Edit:0, Hidden:0},
                       {Type:"Text",   Width:140,  SaveName:"engMean",    Align:"Left", Edit:0, Hidden:0},
                       {Type:"Text",   Width:250,  SaveName:"objDescn",    Align:"Left", Edit:0, Hidden:0},
                       {Type:"Text",   Width:50,  SaveName:"reMark",    Align:"Left", Edit:0, Hidden:0}
                       
                      
                   ];
                       
           InitColumns(cols);
         	//콤보 목록 설정...
         	SetColProperty("bizDtlCd", 	${codeMap.bizdtlcdibs});
         	SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_sditminit);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_sditminit.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//표준용어 - 단어구성정보 그리드 초기화
// 	initsubgrid_sditminit();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_sditminit_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
	var objId = grid_sub_sditminit.GetCellValue(row, "stwdId");	
	if (grid_sub_sditminit.GetCellValue(row, "bizDtlCd") == 'STWD') {
		window.open().location.href = "stwd_lst.do?objId="+objId;	
	} else if (grid_sub_sditminit.GetCellValue(row, "bizDtlCd") == 'DMN') {
		window.open().location.href = "dmn_lst.do?objId="+objId;
	} else if (grid_sub_sditminit.GetCellValue(row, "bizDtlCd") == 'SDITM') {
		window.open().location.href = "sditm_lst.do?objId="+objId;
	} else if (grid_sub_sditminit.GetCellValue(row, "bizDtlCd") == 'TABLE') {
// 		location.href = "stwd_lst.do?objId="+objId;
	} else if (grid_sub_sditminit.GetCellValue(row, "bizDtlCd") == 'INFO') {
// 		location.href = "stwd_lst.do?objId="+objId;
	} else if (grid_sub_sditminit.GetCellValue(row, "bizDtlCd") == 'SYMN') {
		window.open().location.href = "symn_lst.do?objId="+objId;
	}
	
}

function grid_sub_sditminit_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_sditminit_OnSearchEnd(code, message, stCode, stMsg) {
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
	     <script type="text/javascript">createIBSheet("grid_sub_sditminit", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
