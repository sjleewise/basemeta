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
	grid_SDITM_init();
});


$(window).on('load',function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	grid_SDITM.FitColWidth();
    }
);

function grid_SDITM_init() {

    with(grid_SDITM){
    	
     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        //merge 설정
        SetMergeSheet(0);
        
		var headtext  = "<s:message code='DQ.HEADER.STNDITEM.RQST.IFM.1'/>";
 		headtext += "<s:message code='DQ.HEADER.STNDITEM.RQST.IFM.2'/>";
 		headtext += "<s:message code='DQ.HEADER.STNDITEM.RQST.IFM.3'/>";

// 		headtext  = "No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|";
// 		headtext += "표준용어논리명|표준용어물리명|논리명기준구분|물리명기준구분|도메인논리명|도메인물리명|도메인그룹|인포타입ID|인포타입논리명|데이터타입|길이|암호화여부|개인정보여부";
// 		headtext += "설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";
		
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	  Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	  Align:"Center", Edit:1, KeyField:1, ColMerge:0},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	  Align:"Center", Edit:0, ColMerge:0},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",	  Align:"Center", Edit:0, ColMerge:0},							
					
					{Type:"Text",   Width:150,  SaveName:"sditmLnm",      Align:"Left", Edit:1, KeyField:1},
					{Type:"Text",   Width:150,  SaveName:"sditmPnm",      Align:"Left", Edit:1, KeyField:1}, 
					{Type:"Text",   Width:100,  SaveName:"lnmCriDs",      Align:"Left", Edit:1}, 
					{Type:"Text",   Width:100,  SaveName:"pnmCriDs",      Align:"Left", Edit:1}, 
					{Type:"Text",   Width:100,  SaveName:"dmnLnm", 	      Align:"Left", Edit:1, Hidden:0, ColMerge:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"dmnPnm", 	      Align:"Left", Edit:1, Hidden:0, ColMerge:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"dmngLnm",       Align:"Left", Edit:1, Hidden:1, ColMerge:0},
					{Type:"text",   Width:60,   SaveName:"infotpId",      Align:"Left", Edit:1, Hidden:1, ColMerge:0},
					{Type:"Text",   Width:90,   SaveName:"infotpLnm",     Align:"Left", Edit:1, Hidden:1, ColMerge:0},
					{Type:"Text",   Width:100,   SaveName:"dataType",      Align:"Left", Edit:1, Hidden:1, ColMerge:0},
					{Type:"Text",   Width:80,   SaveName:"dataLen",	      Align:"Right", Edit:1, Hidden:1, ColMerge:0},
					{Type:"Text",   Width:80,   SaveName:"dataScal",	      Align:"Right", Edit:1, Hidden:1, ColMerge:0},
					{Type:"Combo",  Width:130,  SaveName:"encYn",	      Align:"Center", Edit:1, Hidden:1,ColMerge:0},
					{Type:"Combo",  Width:130,  SaveName:"persInfoYn", 	  Align:"Center", Edit:1, Hidden:1,ColMerge:0},
					
					{Type:"Text",   Width:120,  SaveName:"objDescn",	  Align:"Left", 	 Edit:1, KeyField:1, ColMerge:0},
					{Type:"Text",   Width:130,  SaveName:"rqstDtm",  	  Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1, ColMerge:0},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",    Align:"Center", Edit:0, ColMerge:0},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",        Align:"Center", Edit:0, Hidden:1, ColMerge:0}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",       Align:"Center", Edit:0, Hidden:1, ColMerge:0}
				];
					
		InitColumns(cols);
		
		//콤보 목록 설정
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
//		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
		
// 		SetColProperty("dmngId", 	${codeMap.dmngibs});
		//SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("dupYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("persInfoYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		
		InitComboNoMatchText(1, "");
		
	  
		// FitColWidth();  
	    //SetSheetHeight(200);
		SetExtendLastCol(1);	
	}
    
    init_sheet(grid_SDITM);    
    
}


function grid_SDITM_OnLoadExcel() {
}

function grid_SDITM_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_SDITM_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_SDITM.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '표준용어' + ' : ' +  param.sditmLnm +"["+ param.sditmPnm+"]";
	
	$('#SDITM_sel_title').html(tmphtml);
	
	//검증결과 검증오류일경우가 아닐경우 RETURN
	if( grid_SDITM.GetCellValue(row, "vrfCd") != "2")  return;
	
	//var param = grid_SDITM.GetRowJson(row);

	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;

	//검증결과 조회
	getRqstVrfLst(param1);
	
}


function grid_SDITM_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function grid_SDITM_OnSearchEnd(code, message, stCode, stMsg) {
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
<div id="grid_SDITM" class="grid_SDITM">
     <script type="text/javascript">createIBSheet("grid_SDITM", "100%", "400px");</script>
</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area" id="selected_title_area">
	<div class="selected_title" id="SDITM_sel_title"></div>
</div>



