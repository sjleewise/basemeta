<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title></title>

<script type="text/javascript">


$(window).on('load',function() {
// 	alert('window.load');
	initGridDmn();
// 	loadDetail();
	
	// 타 탭에서 더블클릭으로 검색내용이 있을시 조회해준다.
	if(!isBlankStr("${dmnId}")) {
		var param = "dmnId="+"${dmnId}";
		grid_sheet_Dmn.DoSearch("<c:url value="/meta/govstnd/getDomainlist.do" />", param);
		
	}
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("SearchDmn");
	}
	
	
});




function initGridDmn()
{
    
    with(grid_sheet_Dmn){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.GOVDMN.REG'/>";
        
		var headers = [
						{Text:headtext, Align:"Center"}
					];
					//No.|검증결과|시스템논리명|DB물리명|스키마물리명|도메인그룹명|도메인분류명|도메인명|공통표준도메인설명|요청구분코드|데이터타입|데이터길이|
					//데이터크기|출처|등록일시|등록ID|엑셀여부
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",		Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0,	Hidden:0},//NO
						{Type:"Status",   	Width:50,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},//상태
						{Type:"CheckBox", 	Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},//선택
					 	{Type:"Text", 		Width:205,  SaveName:"vrfRmk",   	Align:"Center", Edit:0, Hidden:0},//검증결과
						{Type:"Text",   	Width:205,  SaveName:"dbConnTrgPnm",Align:"Left", 	Edit:1,	KeyField:1},//db물리명
						{Type:"Text",   	Width:205,  SaveName:"dbSchPnm", 	Align:"Left", 	Edit:1, Hidden:0,KeyField:1},//스키마물리명
						{Type:"Text",   	Width:205,  SaveName:"uppDmngNm", 	Align:"Left", 	Edit:1, Hidden:0,KeyField:1},//도메인그룹명
						{Type:"Text",   	Width:205,  SaveName:"dmngNm",	 	Align:"Left", 	Edit:1, Hidden:0},//도메인분류명
						{Type:"Text",   	Width:205,  SaveName:"dmnNm",	 	Align:"Left", 	Edit:1, Hidden:0,KeyField:1},//도메인명
						{Type:"Combo",   	Width:205,  SaveName:"objDescn",	Align:"Left", 	Edit:0, Hidden:1},//공통표준도메인설명
						{Type:"Combo",   	Width:80,   SaveName:"rqstDcd",	 	Align:"Left", 	Edit:0, Hidden:1}, //요청구분코드
						{Type:"Text",   	Width:150,  SaveName:"dataType",	Align:"Center", Edit:1, Hidden:0,KeyField:1},//데이터타입
						{Type:"Text",   	Width:80,   SaveName:"dataLen",	 	Align:"Center", Edit:1, Hidden:0,KeyField:1},//데이터길이
						{Type:"Text",   	Width:70,   SaveName:"dataScal",	Align:"Center", Edit:1, Hidden:0,KeyField:1},//
						{Type:"Text",   	Width:40,   SaveName:"dicOrgn",	 	Align:"Left", 	Edit:0, Hidden:1},//출처
						{Type:"Date",   	Width:70,   SaveName:"regDtm",	 	Align:"Left", 	Edit:0, Hidden:1, Format:"yyyy-MM-dd"},//등록일시
						{Type:"Text",   	Width:70,   SaveName:"regUserId",	Align:"Left", 	Edit:0, Hidden:1},//등록아이디
						{Type:"Text",   	Width:70,   SaveName:"excYn",	 	Align:"Left", 	Edit:0, Hidden:1, DefaultValue:"Y"},//엑셀여부
						{Type:"Text",   	Width:70,   SaveName:"dmnId",	 	Align:"Left", 	Edit:0, Hidden:1}
					];
                    
        InitColumns(cols);
       
        SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
      	InitComboNoMatchText(1, "");
        // FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet_Dmn);    
    //===========================
   
}




//그리드 초기화 한다.
 var chkinitdtlgrids = false; 

function initDtlGrids(){
	
	if (!chkinitdtlgrids) {
		
		
	 	chkinitdtlgrids = true;	
	}
	
}
    

/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
/* 
function grid_sheet_Dmn_OnClick(row, col, value, cellx, celly) {
//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet_Dmn.GetColEditable(col)) return;

	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet_Dmn.GetRowJson(row);
	var dmnId = "&dmnId="+grid_sheet_Dmn.GetCellValue(row, "dmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="DMN.LGC.NM" /> : ' + param.dmnLnm; //도메인논리명
	$('#dmn_sel_title').html(tmphtml);
	
	
	
	var param1 = "dmnId="+param.dmnId;
	
    }
function grid_sheet_Dmn_OnSaveEnd(code, message) {

}

function grid_sheet_Dmn_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
}
//엑셀업로드일 경우 Y
function grid_sheet_Dmn_OnLoadExcel(result) {
	if(result) {
		if(grid_sheet_Dmn.RowCount() != 0) {
			for(var i=1; i<=grid_sheet_Dmn.RowCount(); i++) {
				if(grid_sheet_Dmn.GetCellValue(i, "excYn") == null || grid_sheet_Dmn.GetCellValue(i, "excYn") == "") {
					grid_sheet_Dmn.SetCellValue(i, "excYn", "Y");
				}
			}
		}
	}
}
 */
</script>
</head>

<body>

	<div style="clear: both; height: 5px;">
		<span></span>
	</div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
		<script type="text/javascript">createIBSheet("grid_sheet_Dmn", "100%", "500px");</script>
	</div>

	<!-- 그리드 입력 입력 End -->

	<div style="clear: both; height: 5px;">
		<span></span>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear: both; height: 5px;">
		<span></span>
	</div>


</body>
</html>