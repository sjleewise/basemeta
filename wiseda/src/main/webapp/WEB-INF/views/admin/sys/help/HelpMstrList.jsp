<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html lang="ko">
<head>
<title><s:message code="HELP.MNG"/></title> <!-- 도움말 관리 -->

<script type="text/javascript">
//최근 선택 row
var lastsel;

$(document).ready(function(){
	
	//조회 이벤트 처리
	$("#btnSearch").click(function(){ 
		doAction('Search');
	});
	
	//추가 이벤트 처리
	$("#btnNew").click(function(){
		doAction('Add');
	});

	//삭제버튼 이벤트 처리
	$("#btnDelete").click(function(){
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//         	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
    	}
	});
	
	
	// 검색조건에 엔터시 검색처리
	$("#frmSearch").keydown(function(key){
		if(key.keyCode == 13){
			doAction("Search");
		}
	});
	
	
});

$(window).on('load',function(){
	
	//그리드 초기화
	initgrid();
	
	//페이지 호출시 처리할 액션...
	doAction('Search');
	
});


//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	//$("#gridlist01").jqGrid('setGridWidth', $("#grid_01").width());        
});


function initgrid() {

    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
         var headers = [
                    {Text:"<s:message code='ADMIN.HEADER.HELPMSTRLIST'/>", Align:"Center"}
                    /* 선택|번호|도움말ID|메뉴명|메뉴ID|메뉴명|메뉴전체경로|URL|사용여부|도움말보기 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"CheckBox", Width:60,   SaveName:"ibsCheck", Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq",   Align:"Center", Edit:0, Sort:1},
                    {Type:"Text",     Width:50,   SaveName:"helpId",   Align:"Left",   Edit:0, KeyField:1, Hidden:1}, 
                    {Type:"Text",     Width:350,  SaveName:"scrNm",    Align:"Left",   Edit:0, KeyField:0, Hidden:0},
                    {Type:"Text",     Width:350,  SaveName:"menuId",   Align:"Left",   Edit:0, KeyField:0, Hidden:1},
                    {Type:"Text",     Width:350,  SaveName:"menuNm",   Align:"Left",   Edit:0, KeyField:0, Hidden:1},
                    {Type:"Text",     Width:450,  SaveName:"fullPath", Align:"Left",   Edit:0, KeyField:0, Hidden:0},
                    {Type:"Text",     Width:0,    SaveName:"scrUrl",   Align:"Left",   Edit:0, KeyField:0, Hidden:1}, 
                    {Type:"Combo",    Width:70,   SaveName:"useYn",    Align:"Center", Edit:0, KeyField:0, Hidden:0},
                    {Type:"html",     Width:70,   SaveName:"preView",  Align:"Center", Edit:0, KeyField:0, Hidden:0},
                ];
                    
        InitColumns(cols);
        
        SetColProperty("useYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='NUSE.USE'/>"}); /* 미사용|사용 */

        InitComboNoMatchText(1, "");
        FitColWidth();
        //SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
    	
}

function doAction(sAction) {
	switch(sAction) { 
	
 	case 'Search' :	//조회
		var param = $('form[name=frmSearch]').serialize();
		grid_sheet.DoSearch('<c:url value="/admin/sys/help/selectAllHelpMstrList.do" />', param);
		break;
		
	case 'Add' : //신규 추가
		var url = '<c:url value="/admin/sys/help/HelpMstrdtl.do"/>';
		
		location.href = url;
		break;
		 
	case 'Delete' : //삭제
	
		//체크된 행 중에 입력상태인 경우 시트에서 제거...
    	delCheckIBS(grid_sheet);
    	
    	var sRow = grid_sheet.FindCheckedRow("ibsCheck");
    	//받은 결과를 배열로 생성한다.
    	var arrRow = sRow.split("|");
    	var tmpkey = "";
    	for(idx=0; idx<arrRow.length; idx++){ 
    		//alert(arrRow[idx]);
    		tmpkey += grid_sheet.GetCellValue(arrRow[idx], 'helpId') +"|";
		}
    	
    	//url 호출
		var urls = '<c:url value="/admin/sys/help/deleteHelpMasterInf.do"/>';
		var param = "helpId="+tmpkey;
		ajax2Json(urls, param, ibscallback);

		break;

/* 	case 'SaveRow': //단건 저장
		document.frmInput.action = "<c:url value='/admin/sys/help/ajaxgrid/insertHelpMastetInf.do'/>";
		document.frmInput.submit();
		break;
 */		
	
	}
	
	
}
 
//상세정보호출
function loadDetail(param) {
	var url = '<c:url value="/admin/sys/help/HelpMstrdtl.do"/>';
	
	location.href = url+'?'+param;
	
	/* 
	$('div#detailInfo').load('<c:url value="/admin/sys/help/ajaxgrid/selectHelpMasterInf.do"/>', param, function(){
		//$('#tabs').show();
	}); */
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			doAction("Search");
						
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/
function grid_sheet_OnClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
// 	var param =  grid_sheet.GetRowJson(row);
	var param =  "helpId="+grid_sheet.GetCellValue(row, "helpId");
	param = param +"&"+ $('form[name=frmSearch]').serialize();
	
	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="HELP.C"/> : ' + param.scrNm + ' [' + param.helpId +']'; /* 도움말 */
	$('#bbs_sel_title').html(tmphtml);
		
	if ("preView" == grid_sheet.ColSaveName(col) && value.indexOf("BK_btn_grid2") != -1) {
		var url = '<c:url value="/admin/sys/help/popup/helpPop.do"/>';
		
		OpenWindow(url+'?'+param,'helpPop','925','550','yes');
		return false;
		}
	
	loadDetail(param);
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
/* 	if (stCode == 401) {
		showMsgBox("CNF", "s:message code="CNF.LOGIN" /", gologinform); // <>붙여야함
		return;
	} */
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		setGridCellAll(grid_sheet,"preView", "<a class='BK_btn_grid2' ><s:message code='HELP.VIEW'/></a>" ); /* 도움말보기 */
	}
}



</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<h3>${REQ_MENU.menuNm }</h3>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:15px;"><span></span></div>

<!-- <div class="adminContents"> -->
<%-- 	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 관리자 &gt; 게시판관리</div> --%>
    
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    
        
	<form id="frmSearch" name="frmSearch" method="post" onsubmit="return false;">
	<div class="tb_basic2">
    <table border="0" cellspacing="0" cellpadding="0" class="" summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:10%;">
            <col style="width:90%;">
            <%-- <col style="width:12%;">
            <col style="width:38%;"> --%>
        </colgroup>
      <tr>
      	<th>
      	<s:message code="MENU.NM" />
      	<%-- <select id="searchTyp" name="searchTyp" class="wd100" >
      		<c:choose>
      			<c:when test="${param.searchTyp == 'scrUrl'}">
					<option value = "scrNm">화면명</option>
        			<option value = "scrUrl" selected>URL</option>
 	    		</c:when>
 	    		<c:otherwise>
					<option value = "scrNm" selected >화면명</option>
        			<option value = "scrUrl">URL</option>
 	    		</c:otherwise>
      		</c:choose>
      	</select> --%>	
        </th>
      	<td>
      		<input type="text" name="searchWrd" class="wd300" value="${param.searchWrd}" ;">
      	</td>
      </tr>

    </table>
    </div>
    </form>
       
        <!-- 조회버튼영역  -->         
        <div style="clear:both; height:10px;"><span></span></div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonHelp.jsp" />
		 <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    	         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "450px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>