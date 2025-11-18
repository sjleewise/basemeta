<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
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
//최근 선택 row

$(document).ready(function(){
	
	//그리드 초기화
	initsubgrid();
	
	//그리드 하단에서 드래그로 높이 조정
// 	$( ".grid_01" ).resizable({
// 		minHeight: 150,
// 		handles: "s",
// 		resize: function( event, ui ) {
// 			//alert(ui.size.width+":"+ui.size.height);
// 			$(this).children("div:first").css('height', ui.size.height);
// 			//$("#DIV_grid_sub").css('height', ui.size.height);
			
// 		}
		
// 	});
	
	//달력팝업 추가...
	/* $( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker(); */
	
	
	//기간 버튼 클릭 체크
/* 	$(".bd_none a").click(function(){
		var btna = $(".bd_none a"); 
		var idx = btna.index(this);
		btna.removeClass('tb_bt_select').addClass('tb_bt');
		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

		//alert(idx);
		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
		
	}); */
	
// 	//$( "#tabs" ).tabs();
	
	//조회 이벤트 처리
	$("#btnSubSearch").click(function(){ 
		var param = $('form[name=frmInput]').serialize();
		
		//doAction('dtlSearch');
		if(!isBlankStr(param))
			doAction('SearchDtlCode');
		else
			doAction('SearchAllDtl');
	}).find(".ui-button-text").text("<s:message code='WHL.INQ' />"); /* 전체조회 */
	
	//추가 이벤트 처리
	$("#btnSubNew").click(function(){
		doAction('AddDtlCode');
		
	});
	
	//상세코드 저장 이벤트
	$("#btnSubSave").click(function(){
		
		//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_sub.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
//     	$( "#tabs" ).tabs( "option", "active", 1 );
//     	return;

		//공통코드ID를 필수입력항목에 포함한다.
		grid_sub.SetColProperty("commDcdId", 	{KeyField:1});

		//폼내용 저장 버튼을 클릭하여 저장 프로세스를 수행한다.
    	//$('#btnfrmSave').click();
		
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveDtlCode');	
    	//doAction("Save"); 
		
	}).show();

	//삭제버튼 이벤트 처리
	$("#btnSubDelete").click(function(){
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sub, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteDtl');
//         	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
    	}
		
	});
	
    // 엑셀내리기 Event Bind
    $("#btnSubExcelDown").click( function(){ 
    	grid_sub.Down2Excel({HiddenColumn:1, Merge:1});
    });

    //엑셀업로 Event Bind
    $("#btnSubExcelLoad").click( function(){ 
    	
// 		var url = '<c:url value="/commons/sys/program/popup/program_xls.do"/>';
		var url = '<c:url value="/commons/code/popup/commdtlcd_xls.do"/>';
// 		$('div#excel_pop iframe').attr('src', url);
		openLayerPop(url, 800, 550);
		
    	//doAction("LoadExcel"); 
    });
	

	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub.FitColWidth();
});


function initsubgrid() {

    with(grid_sub){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.CODEDTLMANAGE.LST'/>", Align:"Center"}
                    /* No.|상태|선택|공통코드|상세코드ID|상세코드|상세코드명|출력순서 |사용여부|상세코드설명|버전|상위상세코드ID */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq",    	 Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,   SaveName:"ibsStatus",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",       Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Combo",    Width:120,  SaveName:"commDcdId",      Align:"Left", Edit:1, KeyField:0}, 
                    {Type:"Text",     Width:120,  SaveName:"commDtlCdId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:120,  SaveName:"commDtlCd",   	 Align:"Left", Edit:1, KeyField:1},
                    {Type:"Text",     Width:120,  SaveName:"commDtlCdNm",    Align:"Left", Edit:1, KeyField:1}, 
                    {Type:"Int",      Width:90,   SaveName:"dispOrd", 	     Align:"Center", Edit:1},
                    {Type:"Combo",    Width:90,   SaveName:"useYn", 	     Align:"Center", Edit:1},
                    {Type:"Text",     Width:200,  SaveName:"objDescn",       Align:"Left", Edit:1}, 
                    {Type:"Int",      Width:50,   SaveName:"objVers", 	     Align:"Center", Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"uppCommDtlCdId", Align:"Left", Edit:1}                     
                ];
                    
        InitColumns(cols);
        
      	//콤보 목록 설정...
	    SetColProperty("commDcdId",  ${codeMap.commDcdIdibs});
	    SetColProperty("useYn",     {ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */

      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
        SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function(){
	
//     $(window).resize();
});




function grid_sub_OnDragStart(Row, Col) {
	
	//alert(Row + ":" + Col);
	
}

/*  그리드 시트에서 드래그 & 드랍 중에서 드랍 이벤트 발생시 처리하는 함수
 *  드랍이벤트 발생시 그리드 데이터를 이동 후 순번 채번을 다시한다.
 */
// function grid_sub_OnDropEnd(FromSheet, FromRow, ToSheet, ToRow, X, Y, Type) {
function grid_sub_OnDropEnd(FromObj, FromRow, ToObj, ToRow) {
	
 	alert(FromObj + ":" + FromRow + ":" + ToObj + ":" + ToRow);
	
	//return;
	
	//드랍위치가 그리드 Row 바깥일 경우는 그냥 리턴한다.
	if (ToRow <= 0) {alert('<s:message code='MSG.NOT.PSTN' />'); return;} /* 올바른 위치가 아님... */
	//드랍위치가 기존이랑 동일할 경우...
	else if (FromRow == ToRow) {return ;}
	
	//드래그 시작 Row를 드랍시 Row로 이동한다.
	var moveRow = grid_sub.DataMove(ToRow, FromRow);
// 	alert(moveRow);
	//Row 이동 후 순번 채번을 다시한다.
	var startRow ;
	if (FromRow > moveRow) startRow = moveRow;
	else startRow = FromRow;
	var cntrow = grid_sub.RowCount();
// 	alert(startRow + ":" +cntrow);
	for (i=startRow; i<=cntrow;i++) {
		grid_sub.SetCellValue(i, "dispOrd", i);
	}

}

	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sub_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sub_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}



function grid_sub_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//loadDetail();
		
		//$('#program_sel_title').html('공통코드를 클릭하시면 상세정보를 조회합니다.');
		
	}

}
</script>

<!-- </head> -->
<!-- <body>     -->
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    <div class="stit"><s:message code="DTL.CD.LST" /></div> <!-- 상세코드 리스트 -->
    
	<form name="frmCodeDtl" action ="" method="post">
	<div class="tb_basic" style="display: none;">
    <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
<%--       <tr>
        <th>구분</th>
        <td>
        	<select id="cateCode" name="cateCode">
        		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
         	<c:forEach var="code" items="${codeMap.cateCode}" varStatus="status">
             <option value="${code.codeCd}">${code.codeLnm}</option>
             </c:forEach>
        	</select>
        </td>
        <th>게시물검색</th>
        <td>
	        <select  id="searchCnd" name="searchCnd" title="검색조건선택">
	        	<option value="0">제목</option>
	            <option value="1">내용</option>
	            <option value="2">작성자</option>
	        </select>
        <input type="text" name="searchWrd" value="${searchVO.searchWrd}" title="검색어 입력" style="display: inline; width: 150px;">
        </td>
      </tr>
      <tr>
        <th class="bd_none">기간</th>
        <td class="bd_none">
        	<a href="#" class="tb_bt">1일</a>
            <a href="#" class="tb_bt">3일</a>
            <a href="#" class="tb_bt">7일</a>
            <a href="#" class="tb_bt">1개월</a>
            <a href="#" class="tb_bt">3개월</a>
            <a href="#" class="tb_bt">6개월</a>
        </td>
        <th>등록일</th>
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" >  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}">
      </tr> --%>
      <tr>
      	<th><s:message code="PGM.NM" /></th> <!-- 프로그램명 -->
      	<td>
<%--       		<input type="text" name="searchWrd" value="${searchVO.searchWrd}"> --%>
      	</td>
      </tr>
    </table>
    </div>
    </form>
    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
    <tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" />
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sub", "100%", "250px");</script>
	</div>
	<!-- 그리드 입력 입력 End -->
			
	<div style="clear:both; height:5px;"><span></span></div>
	
<!-- 	디버그 메시지 표시를 위한 TextArea 생성 -->
<!-- 	<textarea name="txtErr" rows="10" cols="70" ></textarea> -->
	
<!-- </body> -->
<!-- </html> -->
