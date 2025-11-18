<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

$(document).ready(function(){
	//그리드 초기화
	inithstgrid();
	
	// 엑셀내리기 Event Bind
    $("#btnSubExcelDown").click( function(){ grid_hst_excel(); } );
	
	//버튼숨기기
    $("#btnSubSearch").hide();
    $("#btnSubDelete").hide();
    $("#btnSubTreeNew").hide();
		
});


$(window).on('load',function(){
//     $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
//     	grid_hst.FitColWidth();
    }
);

function grid_hst_excel()
{
        grid_hst.Down2Excel({HiddenColumn:1, Merge:1});
}


function inithstgrid() {

    with(grid_hst){
    	
     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                       {Text:"<s:message code='DQ.HEADER.ANATRGDBMS.HST.LST'/>", Align:"Center"}
                   ];
                   //No.|상태|선택|DB접속대상ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DBMS종류|DBMS버전|
                   //접속대상DB연결문자열|접속대상연결URL|접속대상드라이버명|DB접속계정ID|DB접속계정비밀번호|DB접속상태|
                   //담당자명|담당자연락처|객체설명|객체버전|등록유형코드|작성일시|작성자명

           
           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                       {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                       {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                       {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:0, Hidden:1, Sort:0},
                       {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Center", Edit:0, Hidden:1}, 
                       {Type:"Date",   Width:100,  SaveName:"expDtm",    	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"},
                       {Type:"Date",   Width:100,  SaveName:"strDtm",    	Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd"},
                       {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Center", Edit:0},
                       {Type:"Text",   Width:100,  SaveName:"dbConnTrgLnm",    	Align:"Center", Edit:0},
                       {Type:"Combo",   Width:100,  SaveName:"dbmsTypCd",    	Align:"Center", Edit:0},
                       {Type:"Combo",   Width:100,  SaveName:"dbmsVersCd",    	Align:"Center", Edit:0},
                       {Type:"Text",   Width:100,  SaveName:"connTrgDbLnkChrw",    	Align:"Center", Edit:0, Hidden:1},
                       {Type:"Text",   Width:100,  SaveName:"connTrgLnkUrl",    	Align:"Center", Edit:0, Hidden:1},
                       {Type:"Text",   Width:100,  SaveName:"connTrgDrvrNm",    	Align:"Center", Edit:0, Hidden:1},
                       {Type:"Text",   Width:100,  SaveName:"dbConnAcId",    	Align:"Center", Edit:0},
                       {Type:"Text",   Width:100,  SaveName:"dbConnAcPwd",    	Align:"Center", Edit:0, Hidden:1},
                       {Type:"Text",   Width:100,  SaveName:"dbLnkSts",    	Align:"Center", Edit:0},
                       {Type:"Text",   Width:100,  SaveName:"crgpNm",    	Align:"Center", Edit:0},
                       {Type:"Text",   Width:100,  SaveName:"crgpCntel",    	Align:"Center", Edit:0},
                       {Type:"Text",   Width:100,  SaveName:"objDescn",    	Align:"Center", Edit:0},
                       {Type:"Text",   Width:100,  SaveName:"objVers",    	Align:"Center", Edit:0, Hidden:1},
                       {Type:"Combo",   Width:100,  SaveName:"regTypCd",    	Align:"Center", Edit:0},
                       {Type:"Date",   Width:100,  SaveName:"writDtm",    	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                       {Type:"Text",   Width:100,  SaveName:"writUserNm",    	Align:"Center", Edit:0}
                ];
        
        
        
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...	     
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("dbmsTypCd", 	${codeMap.dbmsTypCdibs});
	    SetColProperty("dbmsVersCd", 	${codeMap.dbmsVersCdibs});
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_hst);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_hst.ShowDebugMsg(-1);	
    	
}


/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_hst_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_hst_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_hst_OnSearchEnd(code, message, stCode, stMsg) {
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

	<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_hst", "100%", "150px");</script>
</div>
<!-- 그리드 입력 입력 End -->
	
<!-- </body> -->
<!-- </html> -->
