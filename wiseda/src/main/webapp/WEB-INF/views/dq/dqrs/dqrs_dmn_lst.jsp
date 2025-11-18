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
		grid_sheet_Dmn.DoSearch("<c:url value="/dq/dqrs/getDmnLst.do" />", param);
		
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
        
//         var headtext  = "message code='META.HEADER.GOVDMN.REG'";
		var headtext = "No.|상태|선택|검증결과|DB물리명|스키마물리명|DBMS논리명|스키마논리명|도메인그룹명|도메인분류명|도메인명|설명|요청구분코드|데이터타입|전체 자리수|소수점 이하 자리수|출처|등록일시|유저아이디|엑셀여부";
        
		var headers = [
						{Text:headtext, Align:"Center"}
					];
					//No.|검증결과|시스템논리명|DB물리명|스키마물리명|DBMS|스키마명|도메인그룹명|도메인분류명|도메인명|설명|공통표준도메인설명|요청구분코드|데이터타입|데이터길이|
					//데이터크기|출처|등록일시|등록ID|엑셀여부
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",		Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0,	Hidden:0},//NO
						{Type:"Status",   	Width:50,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},//상태
						{Type:"CheckBox", 	Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},//선택
					 	{Type:"Text", 		Width:205,  SaveName:"vrfRmk",   	Align:"Center", Edit:0, Hidden:0},//검증결과
						{Type:"Text",   	Width:205,  SaveName:"dbConnTrgPnm",Align:"Left", 	Hidden:1,KeyField:0},//db물리명 
						{Type:"Text",   	Width:205,  SaveName:"dbSchPnm", 	Align:"Left", 	Hidden:1,KeyField:0},//스키마물리명 
						{Type:"Combo",   	Width:205,  SaveName:"dbConnTrgId",Align:"Left", 	Edit:1,	KeyField:1, Hidden:0},//DBMS명
						{Type:"Combo",   	Width:205,  SaveName:"dbSchId", 	Align:"Left", 	Edit:1, Hidden:0,KeyField:1},//스키마명
						{Type:"Text",   	Width:205,  SaveName:"uppDmngNm", 	Align:"Left", 	Edit:1, Hidden:1,KeyField:0},//도메인그룹명
						{Type:"Text",   	Width:205,  SaveName:"dmngNm",	 	Align:"Left", 	Edit:1, Hidden:1},//도메인분류명
						{Type:"Text",   	Width:205,  SaveName:"dmnNm",	 	Align:"Left", 	Edit:1, Hidden:0,KeyField:1},//도메인명
						{Type:"Text",   	Width:205,  SaveName:"objDescn",	Align:"Left", 	Edit:1, Hidden:0},//공통표준도메인설명
						{Type:"Combo",   	Width:80,   SaveName:"rqstDcd",	 	Align:"Left", 	Edit:0, Hidden:1}, //요청구분코드
						{Type:"Text",   	Width:150,  SaveName:"dataType",	Align:"Center", Edit:1, Hidden:0,KeyField:1},//데이터타입
						{Type:"Text",   	Width:80,   SaveName:"dataLen",	 	Align:"Center", Edit:1, Hidden:0,KeyField:0},//데이터길이
						{Type:"Text",   	Width:70,   SaveName:"dataScal",	Align:"Center", Edit:1, Hidden:0,KeyField:0},//소수점 이하 자리수
						{Type:"Text",   	Width:40,   SaveName:"dicOrgn",	 	Align:"Left", 	Edit:0, Hidden:1},//출처
						{Type:"Date",   	Width:70,   SaveName:"regDtm",	 	Align:"Left", 	Edit:0, Hidden:1, Format:"yyyy-MM-dd"},//등록일시
						{Type:"Text",   	Width:70,   SaveName:"regUserId",	Align:"Left", 	Edit:0, Hidden:1},//등록아이디
						{Type:"Combo",   	Width:70,   SaveName:"excYn",	 	Align:"Left", 	Edit:0, Hidden:1, DefaultValue:"Y"},//엑셀여부
						{Type:"Text",   	Width:70,   SaveName:"dmnId",	 	Align:"Left", 	Edit:0, Hidden:1}
						
						];
                    
        InitColumns(cols);
       
        SetColProperty("dbConnTrgId", ${codeMap.ConnTrgDbmsibs2});
		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		
		SetColProperty("excYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		
      	InitComboNoMatchText(1, "");
        FitColWidth();  
        
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

 function grid_sheet_Dmn_OnSearchEnd(code, message, stCode, stMsg) {
	var totalcnt = grid_sheet_Dmn.SearchRows();
	
	for (var i=1; i<totalcnt+1; i++) {
		var tmpVal = grid_sheet_Dmn.GetCellValue(i, "dbConnTrgId");
		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
			
			if(key == tmpVal) {
				grid_sheet_Dmn.CellComboItem(i, "dbSchId", val);
				return;
			}
		});
	}
}
 
 function grid_sheet_Dmn_OnChange(row, col, value, cellx, celly) {
	if(grid_sheet_Dmn.GetCellProperty(row, col, "SaveName") == "dbConnTrgId") {
		grid_sheet_Dmn.SetCellValue(row, "dbSchId", null);
		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
			if(key == value) {
				grid_sheet_Dmn.CellComboItem(row, "dbSchId", val);
				return;
			}
		});
	}
}
 
 function grid_sheet_Dmn_OnLoadExcel(result) {
	var totalcnt = grid_sheet_Dmn.RowCount("I");
	for (var i=0; i<totalcnt; i++) {
		var tmpVal = grid_sheet_Dmn.GetCellValue(i+1, "dbConnTrgId");

		$.each(${codeMap.ConnTrgSchibs2}, function(key, val) {
			if(key == tmpVal) {
				grid_sheet_Dmn.CellComboItem(i+1, "dbSchId", val);
				grid_sheet_Dmn.SetCellText(i+1, "dbSchId", grid_sheet_Dmn.GetCellText(i+1,"dbSchId"));
				return;
			}
		});
	} 
 }
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