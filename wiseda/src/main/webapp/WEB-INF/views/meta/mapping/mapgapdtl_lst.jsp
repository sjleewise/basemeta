<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%-- <%@ page import="kr.wise.commons.WiseMetaConfig" %> --%>

<!-- <html> -->
<!-- <head> -->
<!-- <title>컬럼 등록</title> -->

<script type="text/javascript">

$(document).ready(function() {
	//그리드 초기화 
// 	initColGrid();   // 이놈이 문제구만... 레디에서 작업하면... 머지가 안됨.. 야..호
	
	$("#btnSubSearch").hide();
	$("#btnSubTreeNew").hide();
	$("#btnSubDelete").hide();
	
        // 엑셀내리기 Event Bind
        $("#btnSubExcelDown").click( function(){ doActionCol("Down2Excel"); } );

});

$(window).on('load',function() {
	//그리드 초기화 
	initColGrid();
	
	$("#btnSubSearch").hide();
	$("#btnSubTreeNew").hide();
	$("#btnSubDelete").hide();
	
	// 엑셀내리기 Event Bind
    $("#btnSubExcelDown").click( function(){ doActionCol("Down2Excel"); } );
});

// $(window).resize();


function initColGrid()
{
    
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
		var headers = [
						{Text:"<s:message code='META.HEADER.MAPGAP.DTL.LST'/>"}
					   ,{Text:"<s:message code='META.HEADER.MAPGAP.DTL.LST1'/>"}
						];
// 						{Text:"NO.|상태|GAP|매핑구분|매핑정의서ID|컬럼매핑구분|물리모델 \n도메인|물리모델 \n데이터타입|물리모델 \n컬럼순서|타겟(TOBE)\n담당구분|타겟(TOBE)\nDB명|타겟(TOBE)\n테이블명(물리명)|타겟(TOBE)\n테이블명(논리명)|타겟(TOBE)\nPOSITION|타겟(TOBE)\n컬럼명(물리명)|타겟(TOBE)\n컬럼명(논리명)|타겟(TOBE)\n데이터타입|타겟(TOBE)\nPK여부|타겟(TOBE)\nNull여부|타겟(TOBE)\n코드도메인명|소스(ASIS)\n시스템명|소스(ASIS)\n업무명|소스(ASIS)\nDB명|소스(ASIS)\n테이블명(물리명)|소스(ASIS)\n테이블명(논리명)|소스(ASIS)\n컬럼명(물리명)|소스(ASIS)\n컬럼명(논리명)|소스(ASIS)\n데이터타입|소스(ASIS)\nDefault값|소스(ASIS)\n컬럼설명|매핑조건|컬럼참조DB명|컬럼참조테이블명|조인조건|응용담당자명|전환담당자명|승인일시|요청일시|최초요청일시|승인사용자명|요청사용자명|최초요청사용자명|TBL_ID|OBJ_ID|FULLPATH|타겟테이블ID"}
		var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",			Align:"Center", Edit:0},
					{Type:"Combo",  Width:70, 	SaveName:"regTypCd",		Align:"Left", Edit:0},
					{Type:"Text",  Width:100, 	SaveName:"gapStaus",		Align:"Left", Edit:0},
					{Type:"Combo",  Width:70, 	SaveName:"mapDfType",		Align:"Left", Edit:0},
					{Type:"Text",   Width:130,  SaveName:"mapDfId",			Align:"Left", Edit:0},
					{Type:"Combo",  Width:130,  SaveName:"colMapType",		Align:"Left", Edit:0},

					{Type:"Text",   Width:100,  SaveName:"pdmDmnLnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"pdmDataType",		Align:"Left", Edit:0},	
					{Type:"Text",   Width:70,  	SaveName:"pdmColOrd",		Align:"Right", Edit:0},
					
					{Type:"Text",   Width:100,  SaveName:"tgtChrg",		Align:"Left", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"tgtDbPnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:130,  SaveName:"tgtTblPnm",		Align:"Left", Edit:0}, 
					{Type:"Text",   Width:130,  SaveName:"tgtTblLnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:70,  	SaveName:"colOrd",			Align:"Right", Edit:0},
					
					{Type:"Text",  Width:100, 	SaveName:"tgtColPnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"tgtColLnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"tgtDataType",		Align:"Left", Edit:0},	
					{Type:"Combo",   Width:60,  	SaveName:"tgtPkYn",			Align:"Left", Edit:0}, 
					{Type:"Combo",   Width:60,  	SaveName:"tgtNonulYn",		Align:"Left", Edit:0},
					{Type:"Text",   Width:130,  SaveName:"tgtDmnLnm",		Align:"Left", Edit:0},
					
					{Type:"Text",  Width:100, SaveName:"srcSysNm",			Align:"Left", Edit:0},
					{Type:"Text",   Width:100, SaveName:"srcBizNm",			Align:"Left", Edit:0},
					{Type:"Text",   Width:100, SaveName:"srcDbPnm",			Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"srcTblPnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"srcTblLnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"srcColPnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"srcColLnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"srcDataType",		Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"defltVal",			Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"srcDescn",			Align:"Left", Edit:0},
					
					{Type:"Text",   Width:80,  SaveName:"mapCndNm",			Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"colRefDbPnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"colRefTblPnm",		Align:"Left", Edit:0},
					
					{Type:"Text",   Width:80,  SaveName:"jnCndNm",			Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"appCrgpNm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:80,  SaveName:"cnvsCrgpNm",		Align:"Left", Edit:0},
					
					{Type:"Date",   Width:80,  SaveName:"aprvDtm",			Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
					{Type:"Date",   Width:80,  SaveName:"rqstDtm",			Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
					{Type:"Date",   Width:80,  SaveName:"frsRqstDtm",		Align:"right", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
					
					{Type:"Text",   Width:80,  SaveName:"aprvUserNm",		Align:"Left", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:80,  SaveName:"rqstUserNm",		Align:"right", Edit:0 ,Hidden:1},
					{Type:"Text",   Width:80,  SaveName:"frsRqstUserNm",	Align:"right", Edit:0,Hidden:1},
					
					{Type:"Text",   Width:80,  SaveName:"fullPath",	Align:"right", Edit:0,Hidden:1},
					{Type:"Text",   Width:80,  SaveName:"pdmTblId",	Align:"right", Edit:0,Hidden:1},
					{Type:"Text",   Width:80,  SaveName:"tgtColId",	Align:"right", Edit:0,Hidden:1}
                ]; 
                    
        InitColumns(cols);

        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"신규|변경|삭제"});
        SetColProperty("mapDfType", ${codeMap.mapDfTypCdibs});
        SetColProperty("colMapType",${codeMap.colMapTypCdibs});
        SetColProperty("tgtPkYn", 		{ComboCode:"N|Y", 	ComboText:"아니요|예"});
		SetColProperty("tgtNonulYn", 		{ComboCode:"N|Y", 	ComboText:"아니요|예"});
        
        
        InitComboNoMatchText(1, "");
        
//         FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
//     col_sheet.SetDataRowMerge(1);
    //===========================
   
}

//화면상의 모든 액션은 여기서 처리...
function doActionCol(sAction)
{
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
        	var param = $('#frmSearch').serialize();
        	col_sheet.DoSearch("<c:url value="/meta/mapping/getColMapGapDtl.do" />", param);
//         	col_sheet.DoSearchScript("testJsonlist");
        	break;

        case "Down2Excel":  //엑셀내려받기
            col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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
function col_sheet_OnDblClick(row, col, value, cellx, celly) { 

}

function col_sheet_OnClick(row, col, value, cellx, celly) {
// 	resetForm($("form#frmInput2"));
	
//	alert(1);
	if(row < 1) return; 
	
//  	alert(col_sheet.GetCellText(1, "sditmId"));
	
// 	$("#frmInput2").reset();

    ibs2formmapping(row, $("form#frmInput2"), col_sheet);
    
    $("form[name=frmInput2] #frsRqstDtm").val(col_sheet.GetCellText(row, "frsRqstDtm"));
    $("form[name=frmInput2] #rqstDtm").val(col_sheet.GetCellText(row, "rqstDtm"));
    $("form[name=frmInput2] #aprvDtm").val(col_sheet.GetCellText(row, "aprvDtm"));
    
    $("form#colfrmSearch input[name=pdmColId]").val(col_sheet.GetCellValue(row, "pdmColId"));
    
    $("#tab-3 a").click(); 
}

function col_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	ibs2formmapping(1, $("form#frmInput2"), col_sheet);
   	$("form#colfrmSearch input[name=pdmColId]").val(col_sheet.GetCellValue(1, "pdmColId"));
   	$("form[name=frmInput2] #frsRqstDtm").val(col_sheet.GetCellText(1, "frsRqstDtm"));
    $("form[name=frmInput2] #rqstDtm").val(col_sheet.GetCellText(1, "rqstDtm"));
    $("form[name=frmInput2] #aprvDtm").val(col_sheet.GetCellText(1, "aprvDtm"));
	}
}
</script>
<!-- </head> -->
<!-- <body> -->

<div id="col_search_div">
<!-- 검색조건 입력폼 -->
<!--         <div class="stit">검색조건</div> -->
        
        <form id="colfrmSearch" name="colfrmSearch" method="post">
        <input type="hidden" name="pdmColId" />
        	<input type="hidden" id="rqstNo" name="rqstNo" />
        	<input type="hidden" id="rqstSno" name="rqstSno" />
        </form>
</div>

<tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div id="grid_99" class="grid_01">
     <script type="text/javascript">createIBSheet("col_sheet", "100%", "200px");</script>            
</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- </body> -->
<!-- </html> -->