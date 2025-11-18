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
	grid_pc02_init();
});


$(window).on('load',function(){
    $(window).resize();
});


$(window).resize( function(){
    	//그리드 가로 스크롤 방지
    	grid_pc02.FitColWidth();
    }
);

function grid_pc02_init() {

    with(grid_pc02){
    	
     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        //merge 설정
        SetMergeSheet(1);
        
        var headers = [
                       {Text:"<s:message code='DQ.HEADER.CODEANA_EXL'/>", Align:"Center"}
                   ];
           //No.|상태|선택|요청번호|요청일련번호|요청상세일련번호|검토상태|검토내용|요청구분|등록유형|검증결과|진단대상명|스키마명|테이블명|컬럼명|추가조건|유효값유형|사용자정의유효값|"+"코드테이블진단대상명|코드테이블스키마명|코드테이블명|코드값컬럼명|코드ID컬럼명|코드ID|코드테이블추가조건

           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                       {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0 ,ColMerge:0},
                       {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1,ColMerge:0},
                       {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:1 ,ColMerge:0, Sort:0},
                       {Type:"Text",   Width:50,  SaveName:"rqstNo",    	Align:"Center", Edit:0, Hidden:1 ,ColMerge:0}, 			//요청번호
                       {Type:"Text",   Width:50,  SaveName:"rqstSno",    	Align:"Center", Edit:0, Hidden:1 ,ColMerge:0}, 			//요청일련번호
                       {Type:"Text",   Width:50,  SaveName:"rqstDtlSno",    	Align:"Center", Edit:0, Hidden:1 ,ColMerge:0}, 		//요청상세일련번호
                       
                       {Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:1, Hidden:0 ,ColMerge:0},						
   					   {Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1 ,ColMerge:0},	
                       {Type:"Combo",  Width:80,  SaveName:"rqstDcd",	 Align:"Center", Edit:1, KeyField:1 ,ColMerge:0},						
   					   {Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0 ,ColMerge:0},						
   					   {Type:"Combo",  Width:120,  SaveName:"vrfCd",		Align:"Center", Edit:0 ,ColMerge:0},			
   					   
                       {Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:1, KeyField:1 ,ColMerge:0}, 	//진단대상명
                       {Type:"Text",   Width:120,  SaveName:"dbSchPnm",    	Align:"Left", Edit:1, KeyField:1 ,ColMerge:0},		//스키마명
                       {Type:"Text",   Width:120,  SaveName:"dbcTblNm",    	Align:"Left", Edit:1, KeyField:1 ,ColMerge:0}, 			//테이블명
                       {Type:"Text",   Width:120,  SaveName:"objNm",    	Align:"Left", Edit:1, KeyField:1 ,ColMerge:0}, 			    //컬럼명
                       {Type:"Text",   Width:100,  SaveName:"adtCndSql",    	Align:"Left", Edit:1 ,ColMerge:0},	//추가조건
                       
                       {Type:"Combo",   Width:120,  SaveName:"efvaAnaKndCd",    	Align:"Left", Edit:1, KeyField:1 ,ColMerge:0},		//유효값유형
                       {Type:"Text",   Width:100,  SaveName:"userDfEfva",    	Align:"Left", Edit:1 ,ColMerge:0},		//사용자정의유효값
                       
                       {Type:"Text",   Width:80,  SaveName:"cdTblDbConnTrgPnm",    	Align:"Left", Edit:1 ,ColMerge:0}, 	//코드테이블진단대상명
                       {Type:"Text",   Width:80,  SaveName:"cdTblDbSchPnm",    	Align:"Left", Edit:1 ,ColMerge:0},		//코드테이블스키마명
                       {Type:"Text",   Width:80,  SaveName:"cdTblDbcTblNm",    	Align:"Left", Edit:1 ,ColMerge:0}, 			//코드테이블명
                       {Type:"Text",   Width:80,  SaveName:"cdTblDbcColNm",    	Align:"Left", Edit:1 ,ColMerge:0}, 			    //코드값컬럼명
                       {Type:"Text",   Width:80,  SaveName:"cdTblCdIdColNm",    	Align:"Left", Edit:1 ,ColMerge:0}, 			    //코드ID컬럼명
                       {Type:"Text",   Width:80,  SaveName:"cdTblCdId",    	Align:"Left", Edit:1 ,ColMerge:0}, 			    //코드ID
                       {Type:"Text",   Width:100,  SaveName:"cdTblAdtCndSql",    	Align:"Left", Edit:1 ,ColMerge:0}		//코드테이블추가조건
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
      	//콤보 목록 설정
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		//유효값유형
		SetColProperty("efvaAnaKndCd", 	${codeMap.efvaAnaKndCdibs});
		
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);
    }
    
    init_sheet(grid_pc02);    
    
}


function grid_pc02_OnLoadExcel() {
}

function grid_pc02_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	//검증결과가 오류일경우 더블클릭시 검증결과탭 이동...
	if(grid_pc02.GetCellValue(row, "vrfCd") == 2) {
		$("#vrfForColBatch").click();
	}
}

function grid_pc02_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_pc02.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code='CD.ANLY'/>' + ' : ' + param.dbConnTrgPnm + '.' + param.dbSchPnm +'.'+ param.objNm; /*코드분석*/

	$('#pc02_sel_title').html(tmphtml);
	
	//검증결과 검증오류일경우가 아닐경우 RETURN
	if( grid_pc02.GetCellValue(row, "vrfCd") != "2")  return;
	
	//var param = grid_pc02.GetRowJson(row);
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
	//검증결과 조회
	getRqstVrfLst(param1);
	
}


function grid_pc02_OnSaveEnd(code, message) {
	doAction("Search"); 
}

function grid_pc02_OnSearchEnd(code, message, stCode, stMsg) {
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
<div id="grid_pc02" class="grid_01">
     <script type="text/javascript">createIBSheet("grid_pc02", "100%", "400px");</script>
</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area" id="selected_title_area">
	<div class="selected_title" id="pc02_sel_title"></div>
</div>



