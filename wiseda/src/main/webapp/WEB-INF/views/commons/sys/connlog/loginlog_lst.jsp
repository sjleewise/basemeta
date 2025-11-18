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
		//var su = $("#logingrid01").jqGrid('addRow');
// 		var su=$("#logingrid01").jqGrid('addRowData', 99, {});
// 		alert(su);
// 		$("#logingrid01").jqGrid();
// 		if(su) alert("Succes. Write custom code to add data in server"); else alert("Can not update");
	});

	//삭제버튼 이벤트 처리
	$("#btnDelete").click(function(){
		//선택체크박스 확인
		var sel = $("#logingrid01").jqGrid('getGridParam','selarrrow');
		if(isBlankStr(sel))	{
			//alert("삭제할 대상을 선택하세요");
			showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
			return;
		}
		
		//삭제 확인 메시지
		//alert("삭제하시겠어요?");
		showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
	}).hide();
	
	//엑셀 다운로드 버튼
	$("#btnExcelDown").click(function(e){
		//조회된 그리스 수 확인...
		/* var sel = $("#logingrid01").jqGrid('getGridParam','records');
		if(sel < 1)	{
			//alert("다운로드할 대상이 없습니다. 조회를 하세요.");
			showMsgBox("ERR", "<s:message code="ERR.EXCEL.DOWN" />");
			return;
		} */
		
		doAction('Down2Excel');
	    //e.preventDefault();

// 		var postd = $("#logingrid01").jqGrid('getGridParam', 'postData');
		//$("#logingrid01").jqGrid('excelExport', {url : ''});
		//alert(postd);
	});
	
	// 엑셀업로 Event Bind
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
	
	

	
});

$(window).on('load',function(){
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
	
});


//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	
	grid_sheet.FitColWidth();
	//$("#logingrid01").jqGrid('setGridWidth', $("#grid_01").width());        
});


function initgrid() {
	
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.LOGINLOG.LST'/>", Align:"Center"}
                    /* No.|로그ID|접속자|접속ID|접속IP|접속유형|접속일자 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
//                     {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
//                     {Type:"CheckBox", Width:40,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"logId",     Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:150,  SaveName:"loginNm",   Align:"Center", Edit:0, },
                    {Type:"Text",   Width:150,  SaveName:"loginId",   Align:"Center", Edit:0, }, 
                    {Type:"Text",   Width:150,   SaveName:"loginIp",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:100,   SaveName:"loginMthd", Align:"Center", Edit:0},
//                     {Type:"Combo",  Width:80,   SaveName:"creatDt",   	 Align:"Center", Edit:1, ComboCode:"N|Y", ComboText:"아니요|예",},
//                     {Type:"Text",   Width:60,   SaveName:"crgUserId",    Align:"Center", Edit:1, Hidden:1},
//                     {Type:"Text",   Width:150,  SaveName:"objDescn",     Align:"Left", 	 Edit:1},
//                     {Type:"Text",   Width:130,  SaveName:"objVers",      Align:"Left",   Edit:0, Hidden:0},
//                     {Type:"Combo",  Width:130,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"신규|변경|삭제",},                        
                    {Type:"Date",   Width:130,  SaveName:"creatDt",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd hh:MM:ss"}
                ];
                    
        InitColumns(cols);
        SetColProperty("loginMthd", 	{ComboCode:"I|O", 	ComboText:"<s:message code='LOIN' />|<s:message code='LOG.OUT' />"}); /* 로그인 */ /* 로그아웃 */
        //InitComboNoMatchText(1, "");
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================

}

function doAction(action) {
	switch(action) {
	
	case 'Search' :	//조회
// 		var param = $('form[name=frmSearch]').serializeArray();
		var param = $('form[name=frmSearch]').serialize();
// 		alert(param);
		grid_sheet.DoSearch('<c:url value="/commons/sys/connlog/selectLoginLogList.do" />', param);
// 		$("#logingrid01").jqGrid('clearGridData'); // 이전 데이터 삭제
		/* $("#logingrid01").jqGrid('setGridParam', {
			url:'<c:url value="/commons/sys/connlog/ajaxgrid/selectLoginLogList.do" />'+'?'+param,
// 			postData: param,
			datatype:'json'
		}).trigger("reloadGrid"); */
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
		var sel = $("#logingrid01").jqGrid('getGridParam','selarrrow');
		//url 호출
		var urls = '<c:url value="/commons/sys/program/ajaxgrid/deleteProgram.do"/>';
		var param = "progrmFileNm="+sel;
		ajax2Json(urls, param, afterDelGrid);
		break;
    case "Down2Excel":  //엑셀내려받기
        
        grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        
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
			$("#logingrid01").addRowData(data.resultVO.progrmFileNm , data.resultVO, 'first');
		} else if ("U" == data.saction) {
			$("#logingrid01").setRowData(data.resultVO.progrmFileNm, data.resultVO);
// 			$("#logingrid01").addRowData("1", myfirstrow);
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
	<div class="menu_title"><s:message code="USER.CONN.HSTR.INQ" /></div> <!-- 사용자 접속이력 조회 -->
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
    <div style="clear:both; height:5px;"><span></span></div>
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
        		<option value="">전체</option>
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
      	<th><s:message code="CONN.USER.NM" /></th> <!-- 접속자명 -->
      	<td colspan="3">
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
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- 	<div class="selected_title_area"> -->
<%-- 		    <div class="selected_title"><s:message code="SYS.NM.COLN" /> <span></span></div> <!-- 시스템명 : --> --%>
<!-- 	</div> -->
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->

	<!-- 	상세정보 ajax 로드시 이용 -->
	<div id="detailInfo"></div>
	<!-- 	상세정보 ajax 로드시 이용 END -->
   
<!-- </div> -->
<form id="frmExcel" name="frmExcel" action="" method="post" >
	<input type="hidden" name="excelhtml" id="excelhtml">
	<input type="hidden" name="excelname" id="excelname">
</form>

</body>
</html>