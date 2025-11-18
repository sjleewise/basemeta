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
// 	initsubgrid_ddlseqchange();
	$("#btnSeqChangeExcelDown").click(function(){
		grid_sub_ddlseqchange.Down2Excel({HiddenColumn:1, Merge:1, FileName:"ddlseqchange.xls"});
	});
});

$(window).on('load',function() {
	initsubgrid_ddlseqchange();
});
	 

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_ddlseqchange.FitColWidth();
});


function initsubgrid_ddlseqchange() {
    with(grid_sub_ddlseqchange){
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        
        var headtext  = "<s:message code='META.HEADER.DDLSEQCHG.DTL'/>";
        //No.|만료일시|시작일시|SR번호|서브프로젝트번호|DDL시퀀스ID|시퀀스(물리명)|시퀀스(논리명)|DB접속대상(물리명)|DB사용자(물리명)|명명규칙|사용용도|테이블명|컬럼명|L1코드|L3코드|권한목록|INCREMENT BY|START WITH|MAX VALUE|MIN VALUE|CYCLE여부|ORDER여부|CACHE SIZE|패키지명|클래스명|초기화구분코드|초기화실패업무영향도|스크립트정보|설명|요청번호|요청일련번호|요청사유|등록유형|버전|요청자ID|요청자명|요청부서|요청일시|승인자ID|승인자명|승인일시|최초요청일시|최초요청자ID|적용요청일자|적용요청구분
    	
    	 var headers = [
    	                {Text:headtext, Align:"Center"}
    	            ];
    	       
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Date",   Width:100,   SaveName:"expDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"},
						{Type:"Date",   Width:100,   SaveName:"strDtm", 		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:100,  SaveName:"srMngNo", 		Align:"Left", Edit:0, Hidden:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"prjMngNo", 		Align:"Left", Edit:0, Hidden:0, Hidden:1},
						{Type:"Text",   Width:0,   SaveName:"ddlSeqId",	 Align:"Center",   Edit:0, Hidden:1},		
						{Type:"Text",   Width:100,   SaveName:"ddlSeqPnm",	 Align:"Center",   Edit:0, KeyField:0},
						{Type:"Text",   Width:0,   SaveName:"ddlSeqLnm",	 Align:"Center",   Edit:0, Hidden:1},		
						{Type:"Text",   Width:100,   SaveName:"dbConnTrgPnm",	 Align:"Center",   Edit:0, KeyField:0},
						{Type:"Text",   Width:100,   SaveName:"dbSchPnm",	 Align:"Center",   Edit:0, KeyField:0},
						
						{Type:"Combo",   Width:100,   SaveName:"nmRlTypCd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"usTypCd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"pdmTblPnm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"pdmColPnm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"l1cdPnm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"l3cdPnm",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"grtLst",	 Align:"Center",   Edit:0, Hidden:1},
						
						{Type:"Text",   Width:100,   SaveName:"incby",	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,   SaveName:"strtwt",	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,   SaveName:"minval",	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,   SaveName:"maxval",	 Align:"Center",   Edit:0},
						
						{Type:"Combo",   Width:100,   SaveName:"cycYn",	 Align:"Center",   Edit:0},
						{Type:"Combo",   Width:100,   SaveName:"ordYn",	 Align:"Center",   Edit:0},
						{Type:"Text",   Width:100,   SaveName:"cacheSz",	 Align:"Center",   Edit:0},
	               
						{Type:"Text",   Width:100,   SaveName:"pckgNm",	 	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,  SaveName:"seqClasCd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,  SaveName:"seqInitCd",	 Align:"Center",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"bizIfnc",	 Align:"Center",   Edit:0, Hidden:1},
						
	                    {Type:"Text",   Width:40,  SaveName:"scrtInfo",     Align:"Left",   Edit:0, Hidden:1},
						{Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
						
	                    {Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"rqstResn",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",  Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:0},
			   
	                    {Type:"Text",   Width:100,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},          
	                    {Type:"Text",   Width:80,  SaveName:"rqstUserNm",    Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:100,  SaveName:"rqstUserDept",   	Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Date",   Width:100,  SaveName:"rqstDtm",     Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
	                    {Type:"Text",   Width:100,  SaveName:"aprvUserId",    Align:"Center", Edit:0, Hidden:1},
	                    {Type:"Text",   Width:100,  SaveName:"aprvUserNm",    Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Date",   Width:100,  SaveName:"aprvDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
	                    {Type:"Date",   Width:100,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1}, 
	                    {Type:"Text",   Width:100,  SaveName:"frsRqstUserId",    Align:"Center", Edit:0, Hidden:1},
	                    
						{Type:"Text",   Width:100,   SaveName:"aplReqdt",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"aplReqTypCd",	 	Align:"Center", Edit:0, Hidden:1}
	                ];
                    
        InitColumns(cols);
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		
		SetColProperty("nmRlTypCd", 	${codeMap.nmRlTypCdibs});
		SetColProperty("usTypCd", 	${codeMap.usTypCdibs});
		SetColProperty("aplReqTypCd", 	${codeMap.aplReqTypCdibs});
		
		SetColProperty("cycYn", 		{ComboCode:"N|Y", 	ComboText:"N|Y"});
		SetColProperty("ordYn", 		{ComboCode:"N|Y", 	ComboText:"N|Y"});
		SetColProperty("seqClasCd", ${codeMap.seqClasCdibs});
		SetColProperty("seqInitCd", ${codeMap.seqInitCdibs});
		
      	//콤보 목록 설정...

      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");

      	SetSheetHeight(250);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_ddlseqchange);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddlseqchange.ShowDebugMsg(-1);	
    	
}

/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_ddlseqchange_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_ddlseqchange_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_ddlseqchange_OnSearchEnd(code, message) {
	//alert(grid_sub_ddlseqchange.GetDataBackColor()+":"+ grid_sub_ddlseqchange.GetDataAlternateBackColor());
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
    
</div>
 <!-- 검색조건 입력폼 End -->    
<!-- 조회버튼영역  -->
    <div class="divLstBtn" style="display: none;">
            <div class="bt03">
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnSeqChangeExcelDown" name="btnSeqChangeExcelDown">엑셀 내리기</button>                       
	    	</div>
    </div>
<!-- 조회버튼영역  -->
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub_ddlseqchange", "100%", "150px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
