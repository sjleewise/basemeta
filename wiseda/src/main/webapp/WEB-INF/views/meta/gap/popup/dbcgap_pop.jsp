<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<script type="text/javascript">

var bfSrcRow = "";
var bfTgtRow = "";

$(document).ready(function() {
      $("div.pop_tit_close").click(function(){
      	//iframe 형태의 팝업일 경우
      	if ("${search.popType}" == "I") {
      		parent.closeLayerPop();
      	} else {
      		window.close();
      	}
      	
      });
      
      $(".bt03").hide();
      // 엑셀내리기 Event Bind
      $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
      initGapCollist();
});

function initGapCollist() {

    with(gapcollist_grid){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(5);
        var header1 = "<s:message code='META.HEADER.DBCGAP.POP1'/>";
        /* No.|상태|테이블명|테이블한글명|컬럼명|DBC|DBC|DBC|DBC|DBC|DBC|DBC|DBC|DBC|DBC|DBC(운영)|DBC(운영)|DBC(운영)|DBC(운영)|DBC(운영)|DBC(운영)|DBC(운영)|DBC(운영)|DBC(운영) */
        var header2 = "<s:message code='META.HEADER.DBCGAP.POP2'/>";
        /* No.|상태|테이블명|테이블한글명|컬럼명|순서|데이터타입|길이|소수점|PK여부|PK순서|NOTNULL|DEFAULT|컬럼명|순서|데이터타입|길이|소수점|PK여부|PK순서|NOTNULL|DEFAULT */
        var headers = [
                       {Text:header1},
                   	{Text:header2, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                        {Type:"Seq",	Width:30,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
                        {Type:"Combo",    Width:30,  SaveName:"gapStatus"     ,Align:"Center",   Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dbcTblNm",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dbcTblLnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"dbcColPnm",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:30,   SaveName:"dbcColOrd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"dbcDataType",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"dbcDataLen",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"dbcDataScal",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:40,   SaveName:"dbcPkYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:50,   SaveName:"dbcPkOrd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:50,   SaveName:"dbcNonulYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:50,   SaveName:"dbcDefltVal",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:30,   SaveName:"dbcRealColPnm",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:30,   SaveName:"dbcRealColOrd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,   SaveName:"dbcRealDataType",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"dbcRealDataLen",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"dbcRealDataScal",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:40,   SaveName:"dbcRealPkYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:50,   SaveName:"dbcRealPkOrd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:50,   SaveName:"dbcRealNonulYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:50,   SaveName:"dbcRealDefltVal",	 	Align:"Center", Edit:0, Hidden:0},
                   
                ];
                    
        InitColumns(cols);
        //콤보 목록 설정...
        InitComboNoMatchText(1, "");
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("dbcPkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("dbcNonulYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("dbcRealPkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("dbcRealNonulYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
	    SetSheetHeight(500);
	    FitColWidth();
        
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(gapcollist_grid);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddltblcollist.ShowDebugMsg(-1);	
    	
}


$(window).on('load',function() {
	//initColGrid();
	//initGapCollist();
	doAction("Search");
	
});





function doAction(sAction)
{
    switch(sAction)
    {		    
        case "Search":
        	var param = "dbSchId=${search.dbSchId}&dbRealSchId=${search.dbRealSchId}&dbcTblNm=${search.dbcTblNm}&dbcRealTblNm=${search.dbcRealTblNm}";
           
            //if($("#dbSchId option:selected").val()==''){
            //	showMsgBox("INF", "<s:message code="REQ.NO.TGTSCH" />");	
            //	return false;
            //}
        	gapcollist_grid.DoSearch('<c:url value="/meta/gap/getDbcColGap.do" />', param);
        	//alert(param);
        	break;
        case "Down2Excel":  //엑셀내려받기
        	gapcollist_grid.Down2ExcelBuffer(true);  
        
        	if(gapcollist_grid.SearchRows()>0){
        		gapcollist_grid.Down2Excel({FileName:'dbcgapcol_list',SheetName:'<s:message code="DBC.DEV.VS.DBC.OPR.CLMN.LST"/>', HiddenColumn:1, Merge:1}); /*DBC(개발) VS DBC(운영)컬럼목록*/ 
        	}

        	gapcollist_grid.Down2ExcelBuffer(false);   
        	
            break;	
    }
}


</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="CLMN.LST" /></div> <!-- 컬럼목록 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
<div class = "pop_content" >
     <!-- 조회버튼영역  -->
<%-- 		<%@ include file="" %> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
<div id="tblLayer" class="">		
		<div>
		<div class="stit"><s:message code="CLMN.LST.INFO"/></div> <!-- 컬럼목록정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
		<caption>PDM</caption>
	     <colgroup>
	     <col style="width:100%;" />
	    </colgroup>
	     <tbody>  
		<tr>
			<td>
			 	<div id="grid_99" class="grid_01">
				     <script type="text/javascript">createIBSheet("gapcollist_grid", "100%", "100%");</script>            
				</div>
			</td>
		</tr>
		</tbody>
		</table>
		</div>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	</div>
</body>
</html>
	