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
	//그리드 초기화
 	initGrid1();
	//그리드 사이즈 조절 초기화...		
	//bindibsresize();
});


$(window).on('load',function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
//     	alert(2);
    	grid_sheet1.FitColWidth();
    }
);

function initGrid1()
{
    with(grid_sheet1){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"상태|선택|검증결과|시스템영역|DB물리명|스키마물리명|표준용어ID|표준용어논리명|표준용어물리명|도메인ID|도메인명|데이터타입|데이터길이|데이터크기|출처|엑셀여부", Align:"Center"}
					];
					//No.|표준분류|표준용어ID|표준용어논리명|표준용어물리명|논리명기준구분|물리명기준구분|도메인ID|도메인논리명|도메인물리명|데이터타입|데이터길이|데이터소수점길이|도메인그룹|인포타입|인포타입변경여부|암호화여부|전체영문의미|설명|요청번호|요청일련번호|등록유형코드|버전|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 
			var cols = [
						{Type:"Status", Width:60,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0}, // 상태
						{Type:"CheckBox", Width:40, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0}, // 선택
						
						{Type:"Text",	Width:100,	SaveName:"vrfRmk",		Align:"Center", Edit:0}, // 검증결과
						
						{Type:"Combo",	Width:100,	SaveName:"sysAreaId",	Align:"Left", Edit:1, Hidden:0}, // 시스템물리명
						
						{Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:1}, // DB물리명
						{Type:"Text",   Width:120,  SaveName:"dbSchPnm",   	Align:"Left", Edit:1}, // 스키마물리명
				
						{Type:"Text",   Width:100,  SaveName:"sditmId",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:120,  SaveName:"sditmLnm",   	Align:"Left", Edit:1}, // 표준항목논리명
						{Type:"Text",   Width:120,  SaveName:"sditmPnm",   	Align:"Left", Edit:1}, // 표준항목물리명
						
						{Type:"Text",   Width:100,  SaveName:"dmnId",   	Align:"Left", Edit:0, Hidden:1}, // 도메인ID
						{Type:"Text",   Width:120,  SaveName:"dmnNm",   	Align:"Left", Edit:1, Hidden:0}, // 도메인명

						{Type:"Text",   Width:100,  SaveName:"dataType",   	Align:"Left", Edit:1}, // 데이터타입
						{Type:"Text",   Width:80,   SaveName:"dataLen",   	Align:"Right", Edit:1}, // 데이터길이
						{Type:"Text",   Width:80,   SaveName:"dataScal",   	Align:"Right", Edit:1}, // 데이터크기
						
						{Type:"Text",	Width:100,	SaveName:"dicOrgn",		Align:"Center",	Edit:1}, // 출처
						
// 						{Type:"Date",	Width:100,	SaveName:"regDtm",		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, // 등록일시
						{Type:"Text",	Width:80,	SaveName:"excYn",		Align:"Center", Edit:1, Hidden:1, DefaultValue:"Y"} // 엑셀여부
						
					];
                    
        InitColumns(cols);
      
        FitColWidth();  
        
        SetExtendLastCol(1);    
        SetColProperty("sysAreaId", ${codeMap.sysareaibs});
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet1);    
    //===========================
   
}

/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sheet1_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_sheet1_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_sheet1_OnSearchEnd(code, message, stCode, stMsg) {
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

	<div style="clear:both; height:10px;"><span></span></div>
<%-- 		<div class="stit"><s:message code="CHG.ITEM.DTL.INFO" /></div> <!-- 변경항목 상세정보 --> --%>
		<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet1", "100%", "400px");</script>
</div>
<!-- 그리드 입력 입력 End -->
	
<!-- </body> -->
<!-- </html> -->
