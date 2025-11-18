<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title></title>

<script type="text/javascript">


$(window).on('load',function() {

	initGridPubSditm();
});


$(window).resize(
);


function initGridPubSditm()
{
    
    with(grid_sheet_pubSditm){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"상태|선택|검증결과|표준분류명|표준용어ID|표준용어명|영문약어명|용어설명|도메인ID|도메인명|데이터타입|데이터길이|소수점 이하 자리수|출처|엑셀여부", Align:"Center"}
					];
					//No.|표준분류|표준용어ID|표준용어논리명|표준용어물리명|논리명기준구분|물리명기준구분|도메인ID|도메인논리명|도메인물리명|데이터타입|데이터길이|데이터소수점길이|도메인그룹|인포타입|인포타입변경여부|암호화여부|전체영문의미|설명|요청번호|요청일련번호|등록유형코드|버전|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 
			var cols = [
						{Type:"Status", 	Width:60,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0}, // 상태
						{Type:"CheckBox", 	Width:60, 	SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0}, // 선택						
						{Type:"Text",		Width:120,	SaveName:"vrfRmk",		Align:"Center", Edit:0}, // 검증결과		
						{Type:"Text",   	Width:200,  SaveName:"dbConnTrgId",Align:"Left", 	KeyField:1, Hidden:0}, // 표준분류명
						{Type:"Text",   	Width:100,  SaveName:"sditmId",   	Align:"Left", 	Edit:0, Hidden:1},
						{Type:"Text",   	Width:200,  SaveName:"sditmLnm",   	Align:"Left", 	Edit:1}, // 표준항목논리명
						{Type:"Text",   	Width:200,  SaveName:"sditmPnm",   	Align:"Left", 	Edit:1, KeyField:1}, // 표준항목물리명						
						{Type:"Text",		Width:200,	SaveName:"objDescn",		Align:"Center", Edit:1, Hidden:0},
						{Type:"Text",   	Width:100,  SaveName:"dmnId",   	Align:"Left", 	Edit:0, Hidden:1}, // 도메인ID
						{Type:"Text",   	Width:150,  SaveName:"dmnNm",   	Align:"Left", 	Edit:1, Hidden:0, KeyField:1}, // 도메인명
						{Type:"Text",   	Width:100,  SaveName:"dataType",   	Align:"Left", 	Edit:1, KeyField:1}, // 데이터타입
						{Type:"Text",   	Width:80,   SaveName:"dataLen",   	Align:"Right", 	Edit:1, KeyField:0}, // 데이터길이
						{Type:"Text",   	Width:80,   SaveName:"dataScal",   	Align:"Right", 	Edit:1, KeyField:0}, // 데이터크기						
						{Type:"Text",		Width:80,	SaveName:"dicOrgn",		Align:"Center",	Edit:1, Hidden:1}, // 출처						
// 						{Type:"Date",		Width:100,	SaveName:"regDtm",		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, // 등록일시
						{Type:"Combo",		Width:80,	SaveName:"excYn",		Align:"Center", Edit:1, Hidden:1, DefaultValue:"Y"}, // 엑셀여부
						{Type:"Combo",		Width:80,	SaveName:"govYn",		Align:"Center", Edit:1, Hidden:1} // 공통인지 아닌지(주제영역 안쓰므로 사용)
						
					];
			
			
                    
        InitColumns(cols);
        
        SetColProperty("excYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("govYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
      
        
        InitComboNoMatchText(1, "Y");
        
        FitColWidth();  
        
        SetExtendLastCol(1);
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet_pubSditm);    
    //===========================
   
}


/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_sheet_pubSditm_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
}

function grid_sheet_pubSditm_OnClick(row, col, value, cellx, celly) {
//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet_pubSditm.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

}


function grid_sheet_pubSditm_OnSaveEnd(code, message) {

}

function grid_sheet_pubSditm_OnSearchEnd(code, message, stCode, stMsg,Response) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />",null,null,Response);
		return;
	}
	var totalcnt = grid_sheet_pubSditm.SearchRows();
}

//엑셀업로드일 경우 Y
function grid_sheet_pubSditm_OnLoadExcel(result) {
	if(result) {
		if(grid_sheet_pubSditm.RowCount() != 0) {
			for(var i=1; i<=grid_sheet_pubSditm.RowCount(); i++) {
				if(grid_sheet_pubSditm.GetCellValue(i, "excYn") == null || grid_sheet_pubSditm.GetCellValue(i, "excYn") == "") {
					grid_sheet_pubSditm.SetCellValue(i, "excYn", "Y");
				}
				grid_sheet_pubSditm.SetCellValue(i, "govYn", "Y");
			}
		}
	}
}

function grid_sheet_pubSditm_OnChange(row, col, value, cellx, celly) {
}

</script>
</head>

<body>


<div style="clear:both; height:5px;"><span></span></div>
 
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet_pubSditm", "100%", "500px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>
	
</body>
</html>