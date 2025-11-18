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
	
	$( "#grid_01" ).resizable({
		minHeight: 50,
		handles: "s",
		resize: function( event, ui ) {
			$(this).children("div:first").css('height', ui.size.height);
		}
	});
		
});


$(window).on('load',function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	grid_hst.FitColWidth();
    }
);

function inithstgrid() {

    with(grid_hst){
    	
     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.CTQ.HST.LST'/>", Align:"Center"}
                ];
        //No.|상태|선택|중요정보항목ID|중요정보항목명|상위중요정보항목명|중요정보항목레벨|중요정보항목설명|버전|등록유형|시작일시|만료일시|최초요청일시|최초요청사용자ID|최초요청사용자명|요청일시|요청자ID|요청자명|승인일시|승인자ID|승인자명

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:1},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"ctqId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:150,  SaveName:"ctqLnm",   	Align:"Left", Edit:0},
                    {Type:"Text",   Width:150,   SaveName:"uppCtqLnm", 	Align:"Left", Edit:0},
                    {Type:"Text",   Width:70,   SaveName:"ctqLvl", 	Align:"Center", Edit:0},
                    {Type:"Text",  Width:40,   SaveName:"weight",      Align:"Right",  Edit:0},
                    {Type:"Text",  Width:40,   SaveName:"goal",        Align:"Right",  Edit:0}, 
                    {Type:"Text",   Width:200,  SaveName:"objDescn",    Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:50,  SaveName:"objVers",     Align:"Left",   Edit:0},
                    {Type:"Combo",  Width:80,  SaveName:"regTypCd",    Align:"Center", Edit:0},   
                    {Type:"Date",   Width:80,  SaveName:"strDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Date",   Width:80,  SaveName:"expDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Date",   Width:80,  SaveName:"frsRqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"frsRqstUserId",  	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"frsRqstUserNm",  	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Date",   Width:80,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Date",   Width:80,  SaveName:"aprvDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"aprvUserId",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"aprvUserNm",  Align:"Center", Edit:0, Hidden:0}
                ];
        
        
        
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
	    SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW' />|<s:message code='CHG' />|<s:message code='DEL' />"}); /*"신규|변경|삭제"*/

        
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
