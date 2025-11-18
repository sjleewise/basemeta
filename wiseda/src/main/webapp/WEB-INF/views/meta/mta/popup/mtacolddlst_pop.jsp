<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title></title>

<script type="text/javascript">
$(document).ready(function() {
	$(window).focus();

    //그리드 초기화 
     initGrid();
     doAction("Search");
     
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  }).hide();
    
  //폼 초기화 버튼 초기화...
	$('#popReset').click(function(event){
		event.preventDefault();
		$("#mtaColPnm").val("");
	}).hide();

	//변경요청
	$("#popApply").click(function(){
		doAction("SaveCol");
	}).show();
	
	//삭제요청
    $("#popDelete").click(function(){ }).hide();       
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close, #btnCloseBottom").click(function(){
   		parent.closeLayerPop();
    });
    
});
//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	//그리드 초기화
	initGrid();
	$(window).resize();
});


$(window).resize(function(){
		 //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
// 		setibsheight($("#grid_01"));        
    	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid() 
{
    with(grid_tbl_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);

        var headtext = "<s:message code='META.HEADER.MTATBL.LST.POP'/>";
        //var headtext = "No.|테이블ID|테이블영문명|테이블한글명|테이블설명|테이블한글명(변경)";
        
        var headers = [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:80,   SaveName:"ibsSeq",		Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"mtaTblId",    Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:250,  SaveName:"mtaTblPnm",   Align:"Left",   Edit:0},
					{Type:"Text",   Width:250,  SaveName:"mtaTblLnm",   Align:"Left",   Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"objDescn" ,   Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"mtaTblLnmAf", Align:"Left", Edit:1},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
      	InitComboNoMatchText(1, "");
        SetExtendLastCol(1); 
    }

	with(grid_col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0,UseHeaderSortCancel:1};
        SetConfig(cfg);

        var headText  = "<s:message code='META.HEADER.MTACOL.LST.POP'/>";
        //No.|컬럼ID|컬럼영문명|컬럼한글명|테이블ID|컬럼설명|컬럼한글명(변경)|연관엔티티명|연관속성명|컬럼순서|데이터타입|데이터길이|소수점길이|데이터포맷|NOTNULL여부|PK정보|PK순서|FK정보|DEFAULT값|제약조건|공개여부|개인정보여부|암호화여부|비공개사유
        var headers = [{Text:headText, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq"		,Align:"Center", Edit:0},
					{Type:"Text",    Width:50,  SaveName:"mtaColId"		,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:200, SaveName:"mtaColPnm"	,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:250, SaveName:"mtaColLnm"	,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:50,  SaveName:"mtaTblId"		,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150, SaveName:"objDescn"		,Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",    Width:200, SaveName:"mtaColLnmAf"	,Align:"Left",   Edit:1},

                    {Type:"Text",    Width:90,  SaveName:"colRelEntyNm"	,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:90,  SaveName:"colRelAttrNm"	,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Int",     Width:50,  SaveName:"colOrd"		,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"dataType"		,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",    Width:80,  SaveName:"dataLen"		,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",    Width:50,  SaveName:"dataScal"		,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",    Width:120, SaveName:"dataFmt"		,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",   Width:100,  SaveName:"nonulYn"		,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:50,  SaveName:"pkYn"			,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Int",     Width:50,  SaveName:"pkOrd"		,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"fkYn"			,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",    Width:100, SaveName:"defltVal"		,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:120, SaveName:"constCnd"		,Align:"Left",   Edit:0, Hidden:1},
                    
                    {Type:"Combo",   Width:80,  SaveName:"openYn"       ,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:80,  SaveName:"prsnInfoYn"   ,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:80,  SaveName:"encTrgYn"     ,Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:150, SaveName:"priRsn"		,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1}

                ];
                    
        InitColumns(cols);

        var ynComboData = {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"};
      	//콤보 목록 설정...
		SetColProperty("pkYn",		ynComboData);
// 		SetColProperty("fkYn",		ynComboData);
		
		SetColProperty("nonulYn",	ynComboData);
		SetColProperty("openYn", 	ynComboData);
		SetColProperty("prsnInfoYn",	ynComboData);
		SetColProperty("encTrgYn",	ynComboData);

		SetColProperty("priRsn", 	${codeMap.nopenRsnibs}); //비공개사유
	    
		//콤보코드일때 값이 없는 경우 셋팅값
		InitComboNoMatchText(1, "");
       
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
   	init_sheet(grid_tbl_sheet); 
    init_sheet(grid_col_sheet);    
    //===========================
}

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":

        	var param = $('#frmSearch').serialize();
        	grid_tbl_sheet.DoSearch('<c:url value="/meta/mta/getMtaTbllist.do" />', param);
        	grid_col_sheet.DoSearch('<c:url value="/meta/mta/ajaxgrid/mtacol_lst.do" />', param);

        	break;

        case "SaveCol":
            var SaveJsonTbl = grid_tbl_sheet.GetSaveJson(0);
            var SaveJsonCol = grid_col_sheet.GetSaveJson(0);
            var mtaTblLnmAf = "";
            if(SaveJsonTbl.data.length == 0 && SaveJsonCol.data.length == 0){
            	showMsgBox("ERR", "<s:message code="REQ.NO.CHANG" />");
                return;
            }

           	mtaTblLnmAf = grid_tbl_sheet.GetCellValue(1, "mtaTblLnmAf");
            parent.returnMtaColPop(JSON.stringify(SaveJsonCol), mtaTblLnmAf);
            $(".pop_tit_close").click();
            break;
        	
        case "Down2Excel":  //엑셀내려받기
        	grid_col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
        	grid_col_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }       
}
/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_col_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	//팝업창 닫기 버튼 클릭....
	//$(".pop_tit_close").click();
	
    return;
	
}

function grid_col_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    if(row < 1) return;
    
    
}


function grid_col_sheet_OnSaveEnd(code, message) {

}

function grid_col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
	}
	
}

</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">테이블/컬럼상세</div> <!-- 테이블 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
	<!-- 검색조건 입력폼 -->
    <form id="frmSearch" name="frmSearch" method="post">
    	<input type="hidden" name="mtaTblId" id="mtaTblId" value="${search.mtaTblId}"/>
    </form>

	<div id="search_div">
        <div style="clear:both; height:5px;"><span></span></div>
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" />
	</div>
	<div style="clear:both; height:15px;"><span></span></div>
	
	<div class="tit_02"><s:message code="TBL.INFO" /></div> <!-- 테이블정보 -->
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_tbl_sheet", "100%", "62px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
	<div style="clear:both; height:15px;"><span></span></div>		

	<div class="tit_02"><s:message code="COL.INFO" /></div> <!-- 컬럼정보 -->
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_col_sheet", "100%", "330px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
	<div style="clear:both; height:15px;"><span></span></div>		
	<div id="" style="text-align: center;">
    	<button class="btn_frm_save btn_colse" id="btnCloseBottom" type="button">닫기</button>           
    </div>
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>