<!DOCTYPE html>
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
	grid_STWD_init();
});


$(window).on('load',function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	grid_STWD.FitColWidth();
    }
);

function grid_STWD_init() {

    with(grid_STWD){
    	
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headers = [
					{Text:"<s:message code='DQ.HEADER.STNDWORD.RQST'/>", Align:"Center"}
				];
				//No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|표준단어논리명|표준단어물리명|영문의미|한자|출처구분|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:60,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
					{Type:"CheckBox", Width:60, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:100,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:100,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:1, KeyField:1},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",		Align:"Center", Edit:0},
					
					{Type:"Text",   Width:150,  SaveName:"stwdLnm",   	Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:150,  SaveName:"stwdPnm",   	Align:"Left", Edit:1, KeyField:1}, 
					{Type:"Text",   Width:150,   SaveName:"engMean", 	Align:"Left", Edit:1, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"cchNm",	 	Align:"Left", Edit:1, Hidden:0},
					{Type:"Text",   Width:100,   SaveName:"orgDs",	 	Align:"Left", Edit:1, Hidden:0},
					
					{Type:"Text",   Width:150,  SaveName:"objDescn",	Align:"Left", 	 Edit:1},
					{Type:"Text",   Width:80,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:60,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
					{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
				];
					
		InitColumns(cols);
		
		//콤보 목록 설정
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		
		
		InitComboNoMatchText(1, "");
		
		SetColHidden("rqstUserId",1);
	  
// 		FitColWidth();  
		
		SetExtendLastCol(1);	
	}
    
    init_sheet(grid_STWD);    
    
}


function grid_STWD_OnLoadExcel() {
}

function grid_STWD_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_STWD_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_STWD.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '표준단어명 ' + ' : ' +  param.stwdLnm+"["+param.stwdPnm+"]";
	
	$('#STWD_sel_title').html(tmphtml);
	
	//검증결과 검증오류일경우가 아닐경우 RETURN
	if( grid_STWD.GetCellValue(row, "vrfCd") != "2")  return;
	
	//var param = grid_STWD.GetRowJson(row);

	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;

	//검증결과 조회
	getRqstVrfLst(param1);
	
}


function grid_STWD_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function grid_STWD_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공....
	}
}

</script>

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
<div id="grid_STWD" class="grid_STWD">
     <script type="text/javascript">createIBSheet("grid_STWD", "100%", "380px");</script>
</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area" id="selected_title_area">
	<div class="selected_title" id="STWD_sel_title"></div>
</div>



