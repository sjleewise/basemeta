<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">
//최근 선택 row
var lastsel;

$(document).ready(function(){
	
	//그리드 초기화
	initgrid();
	
	//그리드 하단에서 드래그로 높이 조정
	$( ".grid_01" ).resizable({
		minHeight: 150,
		handles: "s",
		resize: function( event, ui ) {
			//alert(ui.size.width+":"+ui.size.height);
			$(this).children("div:first").css('height', ui.size.height);
			//$("#DIV_grid_sheet").css('height', ui.size.height);
			
		}
		
	});
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	
	//기간 버튼 클릭 체크
 	$(".bd_none a").click(function(){
		var btna = $(".bd_none a"); 
		var idx = btna.index(this);
		btna.removeClass('tb_bt_select').addClass('tb_bt');
		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

		//alert(idx);
		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
		
	}); 
	
	//추가 버튼...
	$('button.btn_rqst_new').unbind('click').click(function() {
		doAction('Add');
	}).hide();
	
	//조회 이벤트 처리
	$("#btnSearch").click(function(){ 
		doAction('Search'); 
	});
	
	//추가 이벤트 처리
	$("#btnNew").click(function(){
		doAction('Add');
		//var su = $("#connstatgrid01").jqGrid('addRow');
// 		var su=$("#connstatgrid01").jqGrid('addRowData', 99, {});
// 		alert(su);
// 		$("#connstatgrid01").jqGrid();
// 		if(su) alert("Succes. Write custom code to add data in server"); else alert("Can not update");
	});

	//삭제버튼 이벤트 처리
	$("#btnDelete").click(function(){
		//선택체크박스 확인
		var sel = $("#connstatgrid01").jqGrid('getGridParam','selarrrow');
		if(isBlankStr(sel))	{
			//alert("삭제할 대상을 선택하세요");
			showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
			return;
		}
		
		//삭제 확인 메시지
		//alert("삭제하시겠어요?");
		showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
	}).hide();
	
	 // 엑셀내리기 Event Bind
	$("#btnExcelDown").hide();
    $("#btnExcelDown1").click( function(){ doAction("Down2Excel1"); } );
    $("#btnExcelDown2").click( function(){ doAction("Down2Excel2"); } );

    // 엑셀업로 Event Bind
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
	
	

	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	//$("#connstatgrid01").jqGrid('setGridWidth', $("#grid_01").width());        
	//$("#connstatgrid02").jqGrid('setGridWidth', $("#grid_02").width());        
});


function initgrid() {

	with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.CONNSTAT.LST1'/>", Align:"Center"}
                    /* No.|접속일자|접속자|접속ID|접속횟수 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
//                     {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
//                     {Type:"CheckBox", Width:40,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"creatDt",     Align:"Center", Edit:0, KeyField:1, Hidden:0}, 
                    {Type:"Text",   Width:150,  SaveName:"loginNm",   Align:"Center", Edit:0, },
                    {Type:"Text",   Width:150,  SaveName:"loginId",   Align:"Center", Edit:0, }, 
                    {Type:"Int",    Width:150,  SaveName:"statsCo",   Align:"Right", Format:"Integer", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);
        //InitComboNoMatchText(1, "");
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================

    with(grid_sheet1){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.CONNSTAT.LST2'/>", Align:"Center"}
                    /* No.|접속일자|접속자(수)|접속통계(합계) */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
//                     {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
//                     {Type:"CheckBox", Width:40,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"creatDt",     Align:"Center", Edit:0, KeyField:1, Hidden:0}, 
//                     {Type:"Text",   Width:150,  SaveName:"loginNm",   Align:"Center", Edit:0, },
                    {Type:"Text",   Width:150,  SaveName:"loginId",   Align:"Center", Edit:0, }, 
                    {Type:"Int",    Width:150,  SaveName:"statsCo",   Align:"Right", Format:"Integer", Edit:0, Hidden:0},
                ];
                    
        InitColumns(cols);
        //InitComboNoMatchText(1, "");
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet1);    
    //===========================

}

function doAction(action) {
	switch(action) {
	
	case 'Search' :	//조회
// 		var param = $('form[name=frmSearch]').serializeArray();
		var param = $('form[name=frmSearch]').serialize();
// 		alert(param);
		grid_sheet1.DoSearch('<c:url value="/commons/sys/connlog/selectConnStatTot.do" />', param);
		grid_sheet.DoSearch('<c:url value="/commons/sys/connlog/selectConnStatList.do" />', param);

		break;
	case 'Detail' : //상세 정보
		//onSelectRow 그리드 함수에서 처리...
		break;
	case 'Add' : //신규 추가
		loadDetail();
		break;
	case 'SaveRow': //단건 저장
		//saction (I-입력, U-수정)
		var urls = '<c:url value="/commons/sys/program/ajaxgrid/saveProgram.do"/>';
		var param = $('form[name=frmInput]').serialize();
		ajax2Json(urls, param, aftersaveGrid);
		
		break;
	case 'Delete' : //삭제
		var sel = $("#connstatgrid01").jqGrid('getGridParam','selarrrow');
		//url 호출
		var urls = '<c:url value="/commons/sys/program/ajaxgrid/deleteProgram.do"/>';
		var param = "progrmFileNm="+sel;
		ajax2Json(urls, param, afterDelGrid);
		break;
		
	case 'Down2Excel1' :
		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
		break;
		
	case 'Down2Excel2' :
		grid_sheet1.Down2Excel({HiddenColumn:1, Merge:1});
		break;
		
	case "LoadExcel":  //엑셀업로드
        grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        break;	
		


	}
}


//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/sys/program/ajaxgrid/selectProgramDetail.do"/>', param, function(){});

}

//데이터 저장후 후처리 함수
function aftersaveGrid(data) {
// 	alert(data.result);
	if(data.result > 0) {
		//alert("저장성공");
		//var message = "<s:message code="MSG.SAVE" />";
		showMsgBox("INF", "<s:message code="MSG.SAVE" />");
		
		//그리드에 추가 또는 업데이트
		if("I" == data.saction) {
			$("#connstatgrid01").addRowData(data.resultVO.progrmFileNm , data.resultVO, 'first');
		} else if ("U" == data.saction) {
			$("#connstatgrid01").setRowData(data.resultVO.progrmFileNm, data.resultVO);
// 			$("#connstatgrid01").addRowData("1", myfirstrow);
		}
		
		//상세정보 초기화
		loadDetail();
	} else {
		//alert("저장실패");
		showMsgBox("ERR", "<s:message code="ERR.SAVE" />");
	}
}

//삭제 후 처리함수
function afterDelGrid(data) {
	//alert(data.result);
	if(data.result > 0) {
		showMsgBox("INF", "<s:message code="MSG.DEL" />");
		//리스트 다시 조회
		$("#btnSearch").click();
		//입력 폼 초기화
		loadDetail();
	} else {
		showMsgBox("ERR", "<s:message code="ERR.DEL" />");
	}
}
</script>
</head>
<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="USER.CONN.STAT" /></div> <!-- 사용자 접속 통계 -->
<!-- 	<div class="stit">시스템영역 관리</div> -->
	</div>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:5px;"><span></span></div>

<!-- <div class="adminContents"> -->
<%-- 	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 관리자 &gt; 프로그램관리</div> --%>

 <!-- 검색조건 입력폼 -->
<div id="search_div"> 
    <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
	<form name="frmSearch" action ="" method="post">
	<div class="tb_basic2">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
<%--
       <tr>
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
--%>
      <tr>
      	<th><s:message code="TERM.DSTC" /></th> <!-- 기간구분 -->
      	<td>
	        <select  id="pdKind" name="pdKind" title="<s:message code='INQ.COND.CHC' />"> <!-- 검색조건선택 -->
	        	<option value="D"><s:message code="DALY" /></option> <!-- 일별 -->
	            <option value="M"><s:message code="MTHY" /></option> <!-- 월별 -->
	            <option value="Y"><s:message code="YRLY" /></option> <!-- 연도별 -->
	        </select>
      	</td>
      	<th><s:message code="CONN.USER.NM" /></th> <!-- 접속자명 -->
      	<td>
      		<input type="text" name="loginNm" value="${searchVO.loginNm}">
      	</td>
      </tr>
      <tr>
        <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
        <td class="bd_none">
        	<a href="#" class="tb_bt">1<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">3<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">7<s:message code="DD" /></a> <!-- 일 -->
            <a href="#" class="tb_bt">1<s:message code="MN" /></a> <!-- 개월 -->
            <a href="#" class="tb_bt">3<s:message code="MN" /></a> <!-- 개월 -->
            <a href="#" class="tb_bt">6<s:message code="MN" /></a> <!-- 개월 -->
        </td>
        <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" >  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}">
      </tr> 
    </table>
    </div>
    </form>
    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
    <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->
     
    <div style="clear:both; height:5px;"><span></span></div>
  	
  	<!-- 그리드 입력 입력 -->
  	<div class="stit" style="display: inline;"><s:message code="PEDC.CONN.STAT" /></div> <!-- 기간별 접속통계 -->
	<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                        -->
       <button class="btn_excel_down" id="btnExcelDown2" name="btnExcelDown2"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
 	</div>
    <div style="clear:both; height:5px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet1", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->


    <div style="clear:both; height:10px;"><span></span></div>
  	
  	<!-- 그리드 입력 입력 -->
  	<div class="stit" style="display: inline;"><s:message code="PEDC.USER.CONN.STAT" /></div> <!-- 기간별 사용자별 접속통계 -->
  		<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                        -->
       <button class="btn_excel_down" id="btnExcelDown1" name="btnExcelDown1"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
 	</div>
    <div style="clear:both; height:5px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
	
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- 	<div class="selected_title_area"> -->
<%-- 		    <div class="selected_title"><s:message code="SYS.NM.COLN" /> <span></span></div> <!-- 시스템명 : --> --%>
<!-- 	</div> -->
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
   
    <div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 	상세정보 ajax 로드시 이용 -->
	<div id="detailInfo"></div>
	<!-- 	상세정보 ajax 로드시 이용 END -->
   
<form id="frmExcel" name="frmExcel" action="" method="post" >
	<input type="hidden" name="excelhtml" id="excelhtml">
	<input type="hidden" name="excelname" id="excelname">
</form>

</body>
</html>