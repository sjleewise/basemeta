<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%-- <%@ page import="kr.wise.commons.WiseMetaConfig" %> --%>


<script type="text/javascript">

$(document).ready(function() {
	//그리드 초기화 
// 	initRelHistGrid();
    // 엑셀내리기 Event Bind
    $("#btnRelHistExcelDown").click( function(){ doActionRelH("Down2Excel"); } );

});

$(window).on('load',function() {
	//그리드 초기화 
// 	initRelHistGrid();
});


function initRelHistGrid()
{
    
    with(relhist_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
//         SetMergeSheet(1);
        var headtext = "<s:message code='META.HEADER.RELHIST.DTL'/>";

            /*No.|관계ID|만료일시|시작일시|관계물리명|관계논리명|관계유형|카디널리티유형|Parent Optionality유형"
            + "|부모엔터티주제영역ID|부모엔터티주제영역명|부모엔터티ID|부모엔터티명|부모엔터티속성ID|부모엔터티속성명"
            + "|자식엔터티주제영역ID|자식엔터티주제영역명|자식엔터티ID|자식엔터티명|자식엔터티속성ID|자식엔터티속성명"
            + "|등록유형|요청자ID|요청자|요청일시|승인자ID|승인자|승인일시|버전|설명*/

        var headers= [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",    Width:50,  SaveName:"pdmRelId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:100,	SaveName:"expDtm"		,Align:"Center",	Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Date",    Width:100,	SaveName:"strDtm"		,Align:"Center",	Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",    Width:100,  SaveName:"pdmRelPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmRelLnm"     ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Combo",    Width:100,  SaveName:"relTypCd"     ,Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Combo",    Width:100,  SaveName:"crdTypCd"     ,Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Combo",    Width:100,  SaveName:"paOptTypCd"     ,Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"paSubjId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"paSubjPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"paEntyId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"paEntyPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"paAttrId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"paAttrPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"chSubjId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"chSubjPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"chEntyId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"chEntyPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"chAttrId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"chAttrPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Combo",    Width:50,  SaveName:"regTypCd"     ,Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserNm"     ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Date",    Width:120,  SaveName:"rqstDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserNm"     ,Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Date",    Width:120,  SaveName:"aprvDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:50,  SaveName:"objVers"     ,Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Text",    Width:300,  SaveName:"objDescn"     ,Align:"Left",   Edit:0}
                ]; 
                    
        InitColumns(cols);

        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        SetColProperty("relTypCd", 	${codeMap.relTypCdibs});
        SetColProperty("crdTypCd", 	${codeMap.crdTypCdibs});
        SetColProperty("paOptTypCd", 	${codeMap.paOptTypCdibs});
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(relhist_sheet);    
    //===========================
   
}

//화면상의 모든 액션은 여기서 처리...
function doActionRelH(sAction)
{
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
//         	var param = $('#frmSearch').serialize();
        	relhist_sheet.DoSearch("<c:url value="/meta/model/getpdmrelhistlist.do" />", param);
//         	relhist_sheet.DoSearchScript("testJsonlist");
        	break;

        case "Down2Excel":  //엑셀내려받기
            relhist_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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
function relhist_sheet_OnDblClick(row, col, value, cellx, celly) { 
// 	alert(1);
	if(row < 1) return;
	
// 	alert(relhist_sheet.GetCellText(1, "sditmId"));
	
// 	$("#frmInput2").reset();
    
}

function relhist_sheet_OnClick(row, col, value, cellx, celly) {
//     alert("clickevent");
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
}

function relhist_sheet_OnSearchEnd(code, message, stCode, stMsg) {
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
<!-- <body> -->

<div id="rel_search_div">
<!-- 검색조건 입력폼 -->
<!--         <div class="stit">검색조건</div>  -->
        
</div>

<div class="divLstBtn" >	 
			<div class="bt02">     
	          <button class="btn_excel_down" id="btnRelHistExcelDown" name="btnRelHistExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 --> 
	    	</div>
        </div>	

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div id="grid_99" class="grid_01">
     <script type="text/javascript">createIBSheet("relhist_sheet", "100%", "200px");</script>            
</div>
<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- </body> -->
<!-- </html> -->