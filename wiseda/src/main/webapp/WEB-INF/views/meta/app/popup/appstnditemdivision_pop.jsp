<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!-- <html> -->
<!-- <head> -->
<!-- <title>APP항목 자동분할</title> -->

<script type="text/javascript">
$(document).ready(function() {
	
// 	$("#btnExcelDown").button({
// 		icons: { primary: "ui-icon-document" }
		
// 	});
	
	$('#btnSearch').button({
		icons: { primary: "ui-icon-search" }
	});
	
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
    	
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
	
	
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	if(!isBlankStr( $("#trgLnm").val()))
		doAction("Search");
});


$(window).resize(function(){
        //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	setibsheight($("#grid_01"));
    	// grid_sheet.SetExtendLastCol(1);    
});


function initGrid()
{
	
	with(grid_sheet){
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headers = [
					{Text:"<s:message code='META.HEADER.APPSTNDITEMDIVISION.POP'/>", Align:"Center"}
					/* No.|APP항목논리명|APP항목물리명|논리명기준구분|물리명기준구분 */
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",			Align:"Center", Edit:0},
					{Type:"Text",   Width:150,  SaveName:"dicAsmLnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:150,  SaveName:"dicAsmPnm",		Align:"Left", Edit:0}, 
// 					{Type:"Text",   Width:60,  SaveName:"dmnLnm",			Align:"Left", Edit:0},
// 					{Type:"Text",   Width:60,  SaveName:"dmnPnm",			Align:"Left", Edit:0}, 
					{Type:"Text",   Width:150,   SaveName:"dicAsmDsLnm",		Align:"Left", Edit:0},
					{Type:"Text",   Width:150,   SaveName:"dicAsmDsPnm",		Align:"Left", Edit:0},
				];
					
		InitColumns(cols);
		
		InitComboNoMatchText(1, "");
		
		//SetColHidden("rqstUserId",1);
	  
		// FitColWidth();  
		
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_sheet);	
	//===========================
   
}

function doAction(sAction)
{
		
	switch(sAction)
	{
		case "Search":
// 			var param = "trgLnm="+$("#trgLnm").val();
// 			    param += "&rqstNo="+$("#rqstNo").val();
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/app/getsditmdvcanlist.do" />", param);
			
			break;
	   
		case "Down2Excel":  //엑셀내려받기
		
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
			
			break;
	}	   
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;
	
	var retjson = grid_sheet.GetRowJson(row);
	
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		
		parent.returnItemDivsionPop(JSON.stringify(retjson));
		
// 		parent.closeLayerPop();
	} else {
		opener.returnItemDivsionPop(JSON.stringify(retjson));
// 		window.close();
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
// 	$("#sditmLnm", opener.document).val(grid_sheet.GetCellValue(row,"dicAsmLnm"));
// 	$("#sditmPnm", opener.document).val(grid_sheet.GetCellValue(row,"dicAsmPnm"));
// 	$("#dmnLnm", opener.document).val(grid_sheet.GetCellValue(row,"dmnLnm"));
// 	$("#dmnPnm", opener.document).val(grid_sheet.GetCellValue(row,"dmnPnm"));
// 	$("#lnmCriDs", opener.document).val(grid_sheet.GetCellValue(row,"dicAsmDsLnm").replace(/;/g, '_'));
	
// 	$("#divSditmTitle", opener.document).text("표준항목 : " + grid_sheet.GetCellValue(row,"dicAsmLnm"));
	
// 	$("#sditmLnm", opener.document).focus().click();
	
// 	opener.setDmn();
	
// 	window.close();
}

</script>
<!-- </head> -->
<!-- <body> -->
<div class="pop_tit" style="width:100%;"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="APP.ITEM.ATMD.DIV" /></div> <!-- APP항목 자동분할 -->
    <div class="pop_tit_close"></div>
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
            <div style="clear:both; height:5px;"><span></span></div>
            <form id="frmSearch" name="frmSearch" method="post" onSubmit="return false;">
                <fieldset>
                <legend><s:message code="APP.ITEM.ATMD.DIV" /></legend> <!-- APP항목 자동분할 -->
                <div class="tb_basic">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.ITEM.INQ' />"> <!-- 표준항목조회 -->
                        <caption><s:message code="APP.ITEM.ATMD.DIV" /> <!-- APP항목 자동분할 -->
                        </caption>
                        <colgroup>
                        <col style="width:15%;" />
                        <col style="width:85%;" />
                        </colgroup>
                        <tbody>
                            <tr>
                                <th scope="row"><label for="trgLnm"><s:message code="APP.ITEM.LGC.NM" /></label></th><!-- APP항목논리명 -->
                                <td>
                                <input type="text" id="trgLnm" name="trgLnm" value="${search.appSditmLnm}"/>
                                <input type="hidden" id="rqstNo" name="rqstNo" value="${search.rqstNo}"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                </fieldset>
            </form>
        <div style="clear:both; height:5px;"><span></span></div>
	   	<div class="bt02">
<!-- 			<button class="buttons" id="btnExcelDown" 	name="btnExcelDown">저장</button> -->
			<button class="buttons" id="btnSearch" 	name="btnSearch"><s:message code="ATMD.DIV" /></button> <!-- 자동분할 -->
		</div>
            
    <!-- 팝업 내용 시작 -->
    <!-- 검색조건 입력폼 -->
	<div style="clear:both; height:5px;"><span></span></div>
			
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
		<script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>			
	</div>
	<!-- 그리드 입력 입력 -->
	</div>
</div>
<!-- </body> -->
<!-- </html>