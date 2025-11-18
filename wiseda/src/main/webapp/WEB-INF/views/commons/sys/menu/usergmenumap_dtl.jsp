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
	initsubgrid();

		
	 // 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("SearchMenu");  });
                  
    // 추가 Event Bind
    $("#btnTreeNew").hide();
    
    // 저장 Event Bind
    $("#btnSave").click(function(){
    	//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_sub.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');	
    	
    }).show();

    // 삭제 Event Bind
    $("#btnDelete").click(function(){ 

    }).hide();
    
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2ExcelMenu"); } );

    // 엑셀업로 Event Bind
    $("#btnExcelLoad").hide(); 
	

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub.FitColWidth();
});


function initsubgrid() {

    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                       {Text:"<s:message code='COMMON.HEADER.USERGMENUMAP.DTL'/>", Align:"Center"}
                       /* No.|상태|권한부여|메뉴ID|만료일시|시작일시|메뉴명|메뉴구분|상위메뉴ID|메뉴레벨|파일경로|파일명|화면출력여부|화면출력순서|관리자메뉴여부|이미지경로|설명|버전|등록구분|작성시각|작성자 */
                   ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:70,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:40,   SaveName:"menuId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",     Width:30,   SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",     Width:30,   SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",     Width:250,  SaveName:"menuNm",    Align:"Left", Edit:0, Hidden:0, KeyField:1, TreeCol:1}, 
                    {Type:"Combo",    Width:100,  SaveName:"menuDcd",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:30,   SaveName:"uppMenuId",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:70,   SaveName:"menuLvl",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:200,  SaveName:"filePath",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:200,  SaveName:"fileNm",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",    Width:100,  SaveName:"dispYn",    Align:"Center", Edit:0}, 
                    {Type:"Text",     Width:100,  SaveName:"dispOrd",    Align:"Right", Edit:0, Hidden:0}, 
                    {Type:"Combo",    Width:100,  SaveName:"mngrMenuYn",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:70,   SaveName:"imgPath",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",     Width:80,   SaveName:"objDescn",    Align:"Left", Edit:0}, 
                    {Type:"Text",     Width:20,   SaveName:"objVers",    Align:"Right", Edit:0, Hidden:1}, 
                    {Type:"Combo",    Width:20,   SaveName:"regTypCd",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",     Width:30,   SaveName:"writDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},          
                    {Type:"Text",     Width:30,   SaveName:"writUserId",    Align:"Center", Edit:0, Hidden:1}
                    
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
	    SetColProperty("dispYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("mngrMenuYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("menuDcd", 	${codeMap.menuDcdibs});
// 		SetColProperty("aprgId", ${codeMap.approvegroupibs});
		SetColProperty("regTypCd", ${codeMap.regTypCdibs});
      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
  	grid_sub.SetCountPosition(0);
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	
});
	 


function grid_sub_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}

//체크  이벤트가 발생한다.
function grid_sub_OnChange(Row, Col) {

	 var vChk = grid_sub.GetCellValue(Row,"ibsCheck") ;
	
	 var menuId = grid_sub.GetCellValue(Row,"menuId");
 	 
 	 for(var i = 1; i <= grid_sub.RowCount(); i++) {
 		 
 		 var uppMenuId =  grid_sub.GetCellValue(i,"uppMenuId");
 		 
 		 if(uppMenuId == menuId){
 			 
 			grid_sub.SetCellValue(i,"ibsCheck", vChk); 
 		 }
 		 
 	 }
	      
}


function grid_sub_OnSearchEnd(code, message, stCode, stMsg) {
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
    
    
        <form id="frmUsergId" name="frmUsergId" method="post">
      	  <input type="hidden" id="usergId" name="usergId" />
        </form>
	
    
     <!-- 조회버튼영역  -->
    <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "300px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
	
<!-- </body> -->
<!-- </html> -->
