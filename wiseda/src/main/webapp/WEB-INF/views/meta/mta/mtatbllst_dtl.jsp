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
$(document).ready(function(){
	//그리드 초기화
	initsubgrid_mtatbl();
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_mtatbl.FitColWidth();
});


function initsubgrid_mtatbl() 
{
    
    with(grid_sub_mtatbl){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);

        var headtext = "<s:message code='META.HEADER.MTATBL.LST'/>";
//var headtext = "No.|기관명|정보시스템명|DB명|테이블소유자|테이블영문명|테이블한글명|테이블유형|엔터티명|테이블설명|업무분류체계|품질진단여부|태깅정보|테이블생성일|보존기간|테이블볼륨(ROW수)|예상발생량|개방데이터목록|갱신주기|비공개사유|공개/비공개여부|테이블ID|물리스미마명";
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Combo",  Width:90,  SaveName:"orgCd",    Align:"Left",   Edit:0, Hidden:1},
					{Type:"Combo",  Width:90,  SaveName:"infoSysCd",    Align:"Left",   Edit:0, Hidden:1},
					{Type:"Combo",  Width:285,  SaveName:"dbConnTrgId",    Align:"Left",   Edit:0, Ellipsis:1},
					{Type:"Combo",  Width:285,  SaveName:"dbSchId",    Align:"Left",   Edit:0, Ellipsis:1},
					{Type:"Text",   Width:285,  SaveName:"mtaTblPnm",    Align:"Left",   Edit:0, Ellipsis:1},
					{Type:"Text",   Width:285,  SaveName:"mtaTblLnm",    Align:"Left",   Edit:0, Ellipsis:1},
					{Type:"Text",   Width:90,  SaveName:"tblTypNm"	,    Align:"Left",   Edit:0, Hidden:1},
					
					{Type:"Text",   Width:90,  SaveName:"relEntyNm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0, Ellipsis:1, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"subjNm"	,    Align:"Left",   Edit:0, Ellipsis:1, Hidden:1},
					{Type:"Combo",  Width:100,  SaveName:"dqDgnsYn",    Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"tagInfNm"	,    Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"tblCreDt"	,    Align:"Center",   Edit:0, Hidden:1, Format:"yyyy-MM-dd"},

					{Type:"Text",   Width:90,  SaveName:"prsvTerm"	,    Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"tblVol"	,    Align:"Right",   Edit:0, Hidden:1},
					{Type:"Text",   Width:90,  SaveName:"exptOccrCnt"	,    Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"openDataLst"	,    Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:90,  SaveName:"occrCyl"	,    Align:"Center",   Edit:0, Hidden:1},
					{Type:"Combo",  Width:100,  SaveName:"nopenRsn"	,    Align:"Left",   Edit:0, Hidden:1},
					{Type:"Combo",  Width:100,  SaveName:"openRsnCd"	,    Align:"Center",   Edit:0},
					
					{Type:"Text",   Width:60,  SaveName:"mtaTblId"	,    Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"dbSchPnm"	,    Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:60,  SaveName:"gapStsCd"	,    Align:"Center",   Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
//	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});
		SetColProperty("dqDgnsYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"});
		SetColProperty("orgCd", ${codeMap.orgCdibs});
		SetColProperty("infoSysCd", 	${codeMap.infoSysCdibs});
		SetColProperty("dbConnTrgId", 	${codeMap.connTrgSchibs});
		SetColProperty("dbSchId", ${codeMap.connTrgSchibs});
		SetColProperty("nopenRsn", 	${codeMap.nopenRsnibs});
		SetColProperty("openRsnCd", ${codeMap.openRsnCdibs});
		
		
      	InitComboNoMatchText(1, "");
        //SetColHidden("rqstUserNm",1);

        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_mtatbl);    
    //===========================
   
}

$(window).on('load',function() {
	initsubgrid_mtatbl();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_mtatbl_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;

	var url   = "<c:url value="/meta/mta/popup/mtacolPop.do"/>";
	var param =    "mtaTblId=" + grid_sub_mtatbl.GetCellValue(row, "mtaTblId");
/* 				+ "&mtaTblPnm="+ grid_sub_mtatbl.GetCellValue(row, "mtaTblPnm") 
				+ "&gapStsCd="+ grid_sub_mtatbl.GetCellValue(row, "gapStsCd")
				+ "&dbSchId="+ grid_sub_mtatbl.GetCellValue(row, "dbSchId"); */
	openLayerPop(url, 1200, 680, param);
}

function grid_sub_mtatbl_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

}

function grid_sub_mtatbl_OnSearchEnd(code, message, stCode, stMsg) {
	
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

<!-- </head> -->
<!-- <body>     -->
 <!-- 검색조건 입력폼 -->
<div id="search_div">       

	<div class="tb_comment"><s:message  code='ETC.COMM2.COL' /></div>
    <div style="clear:both; height:5px;"><span></span></div>
    
</div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub_mtatbl", "100%", "280px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
<!-- </body> -->
<!-- </html> -->
