<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">

$(document).ready(function(){
	// DB식별자(스키마)추가 Event Bind
    $("#btnDbSchNew").click(function(){ doAction("DbSchNew");  });

 	// DB식별자(스키마)삭제 Event Bind
    $("#btnDbSchDelete").click(function(){
    	//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_dbsch, "<s:message code="ERR.CHKDEL"/>")) {



			var checkedRows = grid_dbsch.FindCheckedRow("ibsCheck");

			var arrRow = checkedRows.split("|");
			
			if(arrRow.length > 0) {
				for(var idx=0; idx<arrRow.length; idx++) {
					if(grid_dbsch.GetCellValue(arrRow[idx], "checkCnt") > 0 ) {
						showMsgBox("ERR", "<s:message code="ERR.DBMS.SCH.DEL.CHK" />");
						return false;
					}
				}
			}

			//삭제 확인 메시지
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DbSchDelete');
    	}
     });
});


$(window).on('load',function(){
    //$(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	//grid_dbsch.FitColWidth();
    }
);

function initdbschgrid() {

    with(grid_dbsch){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0,UseHeaderSortCancel:1};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DBCONNTRG.DTL'/>", Align:"Center"} 
                    /* No.|상태|선택|만료일시|시작일시|DBID|논리DB명|물리DB명|테이블소유자ID|테이블소유자|테이블소유자한글명|DDL대상여부|수집제외여부|DDL대상구분코드|코드전송대상여부|코드자동전송여부|설명|버전|등록유형코드|작성일시|작성사용자ID */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",    Width:60,   SaveName:"ibsSeq",    	  	Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",  		Align:"Center", Edit:0, Hidden:0}, 
					{Type:"CheckBox", Width:50,   SaveName:"ibsCheck", 		Align:"Center", Edit:1, Sort:0},
					{Type:"Text",   Width:100,  SaveName:"expDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1}, 
					{Type:"Text",   Width:100,  SaveName:"strDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1}, 
					{Type:"Text",   Width:150,  SaveName:"dbConnTrgId",    	Align:"Left",   Edit:0, KeyField:0, Hidden:1}, 
					{Type:"Text",   Width:220,  SaveName:"dbConnTrgLnm",    Align:"Left",   Edit:0, Hidden:0},
					{Type:"Text",   Width:220,  SaveName:"dbConnTrgPnm",    Align:"Left",   Edit:0, Hidden:0},
					{Type:"Text",   Width:150,  SaveName:"dbSchId",    		Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:180,  SaveName:"dbSchPnm",    	Align:"Left",   Edit:1, KeyField:0}, 
					{Type:"Text",   Width:150,  SaveName:"dbSchLnm",    	Align:"Left",   Edit:1, KeyField:0, Hidden:1},
					{Type:"Combo",  Width:120,  SaveName:"ddlTrgYn",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Combo",  Width:120,  SaveName:"cltXclYn",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Combo",  Width:120,  SaveName:"ddlTrgDcd",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Combo",  Width:120,  SaveName:"cdSndTrgYn",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Combo",  Width:120,  SaveName:"cdAutoSndYn",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Text",   Width:200,  SaveName:"objDescn",    	Align:"Left",   Edit:1, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"objVers", 	   	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Combo",  Width:100,  SaveName:"regTypCd",    	Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Text",   Width:100,  SaveName:"writDtm",    		Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1}, 
					{Type:"Text",   Width:100,  SaveName:"writUserId",    	Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"orgCd",    	Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"infoSysCd",    	Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:80,  SaveName:"checkCnt",    	Align:"Right",   Edit:0, Hidden:1}
                   
                ];
                    
        InitColumns(cols);
		SetColBackColor("dbConnTrgLnm","#EAEAEA");
		SetColBackColor("dbConnTrgPnm","#EAEAEA");
      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_dbsch);    
    //===========================
}

/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_dbsch_OnChange(row, col, value) {
	if(row < 1)	return;

	if(grid_dbsch.ColSaveName(col) == "dbSchPnm") {
		grid_dbsch.SetCellValue(row, "dbSchLnm", value);
	}
}

function grid_dbsch_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else {
		
		$("#divMsgPopup").dialog("close");
		if(IS_SAVE_SCH_END) {
			$("#btnNextStep").click();
		}
	}
}

function grid_dbsch_OnClick(row, col, value, cellx, celly) {
    
	if(row < 1)	return;

	var colsavename = grid_dbsch.ColSaveName(col);

	if(colsavename == "ibsCheck") { //체크1,언체크0

		//메타데이터테이블에 해당 테이블소유자 존재여부 확인
		if(grid_dbsch.GetCellValue(row, "checkCnt") > 0 && value == 1) {
			showMsgBox("ERR", "<s:message code="ERR.DBMS.SCH.DEL.CHK" />");
			return false;
		}
	}
}
</script>

<!-- </head> -->
<!-- <body>     -->

<!-- 검색조건 입력폼 -->
<div id="search_div_03" >
<!-- 조회버튼영역  -->
<div class="divLstBtn" style="display:none;">	 
	<div class="bt03">
	    <button class="btn_search" id="btnDbSchNew"  name="btnDbSchNew"><s:message code="ADDT" /></button> <!-- 추가  -->
		<button class="btn_delete" id="btnDbSchDelete" name="btnDbSchDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
	</div>
</div>	
<!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 --> 
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_dbsch", "100%", "280px");</script>
</div>
<!-- 그리드 입력 입력 End -->
	
<!-- </body> -->
<!-- </html> -->
