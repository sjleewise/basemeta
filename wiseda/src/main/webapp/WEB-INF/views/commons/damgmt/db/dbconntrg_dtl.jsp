<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>
<script type="text/javascript">
//최근 선택 row

$(document).ready(function(){
	//그리드 초기화
	initsubgrid();
		

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	// 조회 Event Bind
    $("#btnSubSearch").click(function(){ doAction("SubSearch");  }).hide();
                  
    // 추가 Event Bind
    $("#btnSubNew").click(function(){ doAction("SubNew");  });

    // 저장 Event Bind
    $("#btnSubSave").click(function(){ 
    
    	//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_sub.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
   	    //스키마 다건 등록시 DBMS접속정보에서 선택된 DBMS의 ID로 셋팅되는 문제 방지용 주석처리
// 		var dbConnTrgId = grid_sheet.GetCellValue(grid_sheet.GetSelectRow(),'dbConnTrgId');
//     	for(var i = 1; i <= grid_sub.GetTotalRows(); i++){
//     		grid_sub.SetCellValue(i, "dbConnTrgId", dbConnTrgId);
//     	}

    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SubSave');	
    	//doAction("Save"); 
    	
    }).show();

    // 삭제 Event Bind
    $("#btnSubDelete").click(function(){ 
    	//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sub, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'SubDelete');
//         	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "SubDelete");
    	}
      });
	
	// 엑셀내리기 Event Bind
    $("#btnSubExcelDown").click( function(){ doAction("SubDown2Excel"); } );

    // 엑셀업로 Event Bind
    $("#btnSubExcelLoad").click( function(){ doAction("SubLoadExcel"); } );
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_dmnvalue.FitColWidth();
});


function initsubgrid() {

    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DBCONNTRG.DTL'/>", Align:"Center"} 
                    /* No.|상태|선택|DB스키마ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DB접속대상ID|DB스키마물리명|DB스키마논리명|테이블스페이스ID|테이블스페이스|인덱스스페이스ID|인덱스스페이스|DDL대상여부|수집제외여부|DDL대상구분코드|코드전송대상여부|코드자동전송여부|설명|버전|등록유형코드|작성일시|작성사용자ID */
                    /*No.|상태|선택|DB스키마ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DB접속대상ID|DB스키마물리명|DB스키마논리명|테이블스페이스ID|테이블스페이스|인덱스스페이스ID|인덱스스페이스|DDL대상여부|수집제외여부|DDL대상구분코드|컬럼프로파일여부|도메인판별여부|코드전송대상여부|코드자동전송여부|설명|버전|등록유형코드|작성일시|작성사용자ID */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",    Width:40,   SaveName:"ibsSeq",    	  	Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",  		Align:"Center", Edit:0},
					{Type:"CheckBox", Width:50,   SaveName:"ibsCheck", 		Align:"Center", Edit:1, Sort:0},
					{Type:"Text",   Width:100,  SaveName:"dbSchId",    		Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"expDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1}, 
					{Type:"Text",   Width:100,  SaveName:"strDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1}, 
					{Type:"Text",   Width:150,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:150,  SaveName:"dbConnTrgLnm",    	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, KeyField:0, Hidden:1}, 
					{Type:"Text",   Width:150,  SaveName:"dbSchPnm",    	Align:"Left", Edit:1, KeyField:1}, 
					{Type:"Text",   Width:150,  SaveName:"dbSchLnm",    	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:150,  SaveName:"dbTblSpacId",    	Align:"Center", Edit:1, Hidden :1},
					{Type:"Popup",   Width:150,  SaveName:"dbTblSpacPnm",    	Align:"Center", Edit:1},
					{Type:"Text",   Width:150,  SaveName:"dbIdxSpacId",    	Align:"Center", Edit:1, Hidden :1},
					{Type:"Popup",   Width:150,  SaveName:"dbIdxSpacPnm",    	Align:"Center", Edit:1},
					{Type:"Combo",   Width:80,  SaveName:"ddlTrgYn",    	Align:"Center", Edit:1},  
					{Type:"Combo",   Width:80,  SaveName:"cltXclYn",    	Align:"Center", Edit:1, Hidden:1}, 
					{Type:"Combo",   Width:100,  SaveName:"ddlTrgDcd",    	Align:"Center", Edit:1}, 
					{Type:"Combo",   Width:100,  SaveName:"colPrfYn",    	Align:"Center", Edit:1},
					{Type:"Combo",   Width:80,  SaveName:"dmnPdtYn",    	Align:"Center", Edit:1, Hidden:1}, 
					{Type:"Combo",   Width:80,  SaveName:"cdSndTrgYn",    	Align:"Center", Edit:1, Hidden:1}, 
					{Type:"Combo",   Width:80,  SaveName:"cdAutoSndYn",    	Align:"Center", Edit:1, Hidden :1}, 
					{Type:"Text",   Width:450,  SaveName:"objDescn",    	Align:"Left", Edit:1}, 
					{Type:"Text",   Width:100,  SaveName:"objVers", 	   	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Combo",   Width:80,  SaveName:"regTypCd",    	Align:"Center", Edit:0}, 
					{Type:"Text",   Width:100,  SaveName:"writDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1}, 
					{Type:"Text",   Width:100,  SaveName:"writUserId",    	Align:"Left", Edit:0, Hidden:1}
                   
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
	    SetColProperty("ddlTrgYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("cltXclYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("cdSndTrgYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("cdAutoSndYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("dmnPdtYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("colPrfYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("ddlTrgDcd", 	${codeMap.ddlTrgDcdibs});
	    //SetColProperty("dbTblSpacPnm", ${codeMap.tblSpacPnmibs});
	    //SetColProperty("dbIdxSpacPnm", 	${codeMap.idxSpacPnmibs});
	    

      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
//         FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
// 	initsubgrid();
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

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
//팝업 리턴값 처리
function returnTblSpacPop (ret, row) {
   
	var retjson = jQuery.parseJSON(ret);

    grid_sub.SetCellValue(row, "dbTblSpacId", retjson.dbTblSpacId);
    grid_sub.SetCellValue(row, "dbTblSpacPnm", retjson.dbTblSpacPnm);

	
}
function returnIdxSpacPop (ret, row) {

	var retjson = jQuery.parseJSON(ret);
	
	grid_sub.SetCellValue(row, "dbIdxSpacId", retjson.dbTblSpacId);
	grid_sub.SetCellValue(row, "dbIdxSpacPnm", retjson.dbTblSpacPnm);

	
}

function grid_sub_OnPopupClick(row,col) {
	
	//Format이 날짜인 경우 달력 팝업을 오픈한다.
// 	grid_sheet.ShowCalendar();
	var param = "row=" +row;
    param += "&dbConnTrgId="+ grid_sub.GetCellValue(row,"dbConnTrgId");
    //alert(grid_sub.GetCellValue(row,"dbConnTrgPnm"));
	//팝업 오픈
	if ("dbTblSpacPnm" == grid_sub.ColSaveName(col)) {
		var url = '<c:url value="/commons/damgmt/db/popup/dbspac_pop.do" />';
		param += "&tblSpacTypCd=T";
		openLayerPop(url, 700, 500, param);
	}
	else if ("dbIdxSpacPnm" == grid_sub.ColSaveName(col)) {
		var url = '<c:url value="/commons/damgmt/db/popup/dbspac_pop.do" />';
		param += "&tblSpacTypCd=I";
		openLayerPop(url, 700, 500, param);
	}
}
</script>

</head>
<body>    
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    

    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
    <tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" />
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "250px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
		
</body>
</html>
