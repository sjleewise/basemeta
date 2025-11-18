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
// 	grid_sub_ddltblcollist.FitColWidth();
});


function initsubgrid_ddltblcollist() {

    with(grid_sub_ddltblcollist){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.DDLTBLCOLLIST.DTL'/>", Align:"Center"}
                    /* No.|DDL컬럼ID|DDL컬럼물리명|DDL컬럼논리명|DDL테이블ID|컬럼순서|데이터타입|데이터길이|데이터소수점길이|PK여부|PK순서|NOTNULL여부|DEFAULT값|요청번호|요청일련번호|요청상세일련번호|암호화여부|등록유형|버전|설명|최초요청일시|최초요청사용자명|요청일시|요청사용자명|승인일시|승인사용자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},

					{Type:"Text",   Width:40,    SaveName:"ddlColId", 		Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:220,   SaveName:"ddlColPnm", 		Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:220,   SaveName:"ddlColLnm", 		Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"ddlTblId",	 	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"colOrd",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:130,   SaveName:"dataType",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:80,    SaveName:"dataLen",	 	Align:"Right", Edit:0, Hidden:1},
					{Type:"Text",   Width:80,    SaveName:"dataScal",	 	Align:"Right", Edit:0, Hidden:1},
					{Type:"Combo",  Width:80,    SaveName:"pkYn",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:80,    SaveName:"pkOrd",	 	Align:"Right", Edit:0, Hidden:0},
					{Type:"Combo",  Width:80,    SaveName:"fkYn",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Combo",  Width:80,    SaveName:"akYn",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,   SaveName:"nonulYn",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:80,    SaveName:"defltVal",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:40,    SaveName:"rqstNo",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:40,    SaveName:"rqstSno",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:40,    SaveName:"rqstDtlSno",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",  Width:80,    SaveName:"encYn",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Combo",  Width:80,    SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:80,    SaveName:"objVers",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:220,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
					{Type:"Date",   Width:30,    SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:30,    SaveName:"frsRqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:100,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,   SaveName:"rqstUserNm",	 	Align:"Center", Edit:0, Hidden:1},
					{Type:"Date",   Width:80,    SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:80,    SaveName:"aprvUserNm",  Align:"Left", Edit:0, Hidden:1}
                   
                ];
                    
        InitColumns(cols);
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("fkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("akYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("nonulYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
		SetSheetHeight(250);
        
		FitColWidth();
		
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_ddltblcollist);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_ddltblcollist.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
 	//initsubgrid_ddltblcollist();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_ddltblcollist_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	//선택한 상세정보를 가져온다...
	var ddlColId = "&ddlColId="+grid_sub_ddltblcollist.GetCellValue(row, "ddlColId");
	ddlColId += "&objVers="+grid_sub_ddltblcollist.GetCellValue(row, "objVers");
	ddlColId += "&rqstNo="+grid_sub_ddltblcollist.GetCellValue(row, "rqstNo");
	ddlColId += "&regTypCd="+grid_sub_ddltblcollist.GetCellValue(row, "regTypCd");

	//DDL컬럼ID를 토대로 조회한다....
	loadDetailCol(ddlColId);
	//var param2 = "&ddlTblId="+grid_sub_ddltblcollist.GetCellValue(row, "ddlTblId");
	//grid_sub_ddltblcolchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcolchange_dtl.do" />', param2);

	$("#colinfo").click();
	
}

// function grid_sub_ddltblcollist_OnClick(row, col, value, cellx, celly) {

// 	//$("#hdnRow").val(row);
	
// 	if(row < 1) return;
	
// 	var ddlColId = "&ddlColId="+grid_sub_ddltblcollist.GetCellValue(row, "ddlColId")+"&objVers="+grid_sub_ddltblcollist.GetCellValue(row, "objVers");

// 	//DDL컬럼ID를 토대로 조회한다....
// 	loadDetailCol(ddlColId);
	
// 	//doActionColH("Search");
// 	//var param2 = "&ddlTblId="+grid_sub_ddltblcollist.GetCellValue(row, "ddlTblId");
// 	//grid_sub_ddltblcolchange.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcolchange_dtl.do" />', param2);	
	

// }
//상세정보호출(컬럼목록에서 선택시)
function loadDetailCol(param) {
	if("${wamwahFlag}" == "WAH") {
		
		$('div#detailInfo2').load('<c:url value="/meta/ddl/ajaxgrid/ddltblcolinfohist_dtl.do"/>', param, function(){
					
			//$('#tabs').show();
		});
	} else {
		
		$('div#detailInfo2').load('<c:url value="/meta/ddl/ajaxgrid/ddltblcolinfo_dtl.do"/>', param, function(){
					
			//$('#tabs').show();
		});

			
		
	}
}



function grid_sub_ddltblcollist_OnSearchEnd(code, message, stCode, stMsg) {
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
    
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.DATA.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
	<!-- 더블클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
	<!-- 를 사용하시면 됩니다. -->
    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
<%--     <tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" /> --%>
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub_ddltblcollist", "100%", "150px")</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
