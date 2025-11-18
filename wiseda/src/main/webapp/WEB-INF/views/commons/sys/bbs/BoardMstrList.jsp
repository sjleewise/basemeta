<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
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

//엔터키 이벤트 처리
EnterkeyProcess("Search");


$(document).ready(function(){
	
	//그리드 초기화
	initgrid();
	
	//그리드 하단에서 드래그로 높이 조정
// 	$( ".grid_01" ).resizable({
// 		minHeight: 150,
// 		handles: "s",
// 		resize: function( event, ui ) {
// 			//alert(ui.size.width+":"+ui.size.height);
// 			$(this).children("div:first").css('height', ui.size.height);
// 			//$("#DIV_grid_sheet").css('height', ui.size.height);
			
// 		}
		
// 	});
	
	//$( "#tabs" ).tabs();
	
	
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
//         	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
    	}

// 	//삭제버튼 이벤트 처리
// 	$("#btnDelete").click(function(){
// 		//선택체크박스 확인
// 		var sel = $("#gridlist01").jqGrid('getGridParam','selarrrow');
// 		if(isBlankStr(sel))	{
// 			//alert("삭제할 대상을 선택하세요");
// 			showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
// 			return;
// 		}
		
	//단건저장버튼
	});
	
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

    // 엑셀업로 Event Bind
    $("#btnExcelLoad").click( function(){ 
    	
		var url = '<c:url value="/commons/sys/bbs/popup/bbs_xls.do"/>';
		openLayerPop(url, 800, 550);
    });
    
  //공통코드 초기화 셋팅
    setCodeSelect("COM004", "C", $("form[name=frmSearch] #bbsTyCode"));
    setCodeSelect("COM009", "C", $("form[name=frmSearch] #bbsAttrbCode"));
    setCodeSelect("COM005", "C", $("form[name=frmSearch] #tmplatId"));
    setCodeSelect("cateCode", "C", $("form[name=frmSearch] #cateCode"));
  
    //setCodeSelect("COM_DCD", "L", $("form[name=frmSearch] #commDcdId"));
	

	//페이지 호출시 처리할 액션...
	doAction('Add');
	
	
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
                    {Text:"<s:message code='COMMON.HEADER.BOARDMSTRLIST'/>", Align:"Center"}
                    /* No.|상태|선택|게시판ID|게시판명|게시판설명|게시판유형|게시판속성|답변가능여부|파일첨부여부|첨부파일가능갯수|첨부파일사이즈|사용여부|템플릿 유형|최초등록ID|등록일자|수정자ID|수정일자|게시물 구분 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:25,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:150,  SaveName:"bbsId",     Align:"Left", Edit:0, KeyField:1, Hidden:1}, 
                    {Type:"Text",   Width:110,  SaveName:"bbsNm",     Align:"Left", Edit:0, KeyField:1, Hidden:0}, 
                    {Type:"Text",   Width:350,  SaveName:"bbsIntrcn",     Align:"Left", Edit:0, KeyField:0, Hidden:0}, 
                    {Type:"Combo",   Width:100,  SaveName:"bbsTyCode",     Align:"Left", Edit:0, KeyField:1, Hidden:0}, 
                    {Type:"Combo",   Width:100,  SaveName:"bbsAttrbCode",     Align:"Left", Edit:0, KeyField:1, Hidden:0}, 
                    {Type:"Combo",   Width:40,  SaveName:"replyPosblAt",     Align:"Left", Edit:0, KeyField:0, Hidden:0}, 
                    {Type:"Combo",   Width:40,  SaveName:"fileAtchPosblAt",     Align:"Left", Edit:0, KeyField:1, Hidden:0}, 
                    {Type:"Text",   Width:40,  SaveName:"posblAtchFileNumber",     Align:"Left", Edit:0, KeyField:1, Hidden:0}, 
                    {Type:"Text",   Width:100,  SaveName:"posblAtchFileSize",     Align:"Left", Edit:0, KeyField:0, Hidden:1}, 
                    {Type:"Combo",   Width:40,   SaveName:"useAt",   Align:"Left", Edit:0, KeyField:1, Hidden:0},
                    {Type:"Combo",   Width:160,  SaveName:"tmplatId",   Align:"Left", Edit:0, KeyField:0, Hidden:1 },
                    {Type:"Text",   Width:150,  SaveName:"frstRegisterId",   Align:"Left", Edit:0, KeyField:1, Hidden:1}, 
                    {Type:"Date",   Width:100,  SaveName:"frstRegisterPnttm",   Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss",  KeyField:1, Hidden:0}, 
                    {Type:"Text",   Width:100,  SaveName:"lastUpdusrId",   Align:"Left", Edit:0, KeyField:0, Hidden:1}, 
                    {Type:"Date",   Width:120,  SaveName:"lastUpdusrPnttm",   Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss",  KeyField:1, Hidden:1}, 
                    {Type:"Combo",   Width:100,  SaveName:"cateCode",   Align:"Left", Edit:0, KeyField:0, Hidden:0}, 
                ];
                    
        InitColumns(cols);
        
        SetColProperty("replyPosblAt", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='IMPO.PO'/>"}); /* 불가능|가능 */
        SetColProperty("fileAtchPosblAt", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='IMPO.PO'/>"}); /* 불가능|가능 */
        SetColProperty("useAt", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='NOUSE.USE'/>"}); /* 비사용|사용 */
          SetColProperty("tmplatId", 	${codeMap.bbsTmpltt});
         SetColProperty("bbsAttrbCode", 	${codeMap.bbsAttrib});
         SetColProperty("bbsTyCode", 	${codeMap.bbsTyC});
         SetColProperty("cateCode", 	${codeMap.bbscateCode});

        //InitComboNoMatchText(1, "");
       // FitColWidth();/.
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
    	
}

function doAction(action) {
	switch(action) {
	
	case 'Search' :	//조회
		var param = $('form[name=frmSearch]').serialize();
	
// 		alert($("#searchWrd").val());
		grid_sheet.DoSearch('<c:url value="/commons/sys/bbs/selectAllBoardMstrList.do" />', param);
		break;
		
	case 'Add' : //신규 추가
		loadDetail(); 
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
    		tmpkey += grid_sheet.GetCellValue(arrRow[idx], 'bbsId') +"|";
		}

    	//url 호출
		var urls = '<c:url value="/commons/sys/bbs/deleteBBSMasterInf.do"/>';
		var param = "bbsId="+tmpkey;
		ajax2Json(urls, param, ibscallback);
    	
    	//체크된 행을 Json 리스트로 생성...
/*     	var delJson = grid_sheet.GetSaveJson(0, "ibsCheck");
    	if(delJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
    	
    	var url = '<c:url value="/commons/sys/program/ajaxgrid/deleteProgram.do"/>';
//     	$.postJSON(url, DelJson, ibscallback);
        IBSpostJson2(url, delJson, null, ibscallback); */
    	
		break;
		
	case 'SaveRow': //단건 저장
		//saction (I-입력, U-수정)
		var urls = '<c:url value="/commons/sys/bbs/ajaxgrid/insertBBSMastetInf.do"/>';
		var param = $('form[name=frmInput]').serialize();
		ajax2Json(urls, param, ibscallback);
		
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
	$('div#detailInfo').load('<c:url value="/commons/sys/bbs/ajaxgrid/selectBBSMasterInf.do"/>', param, function(){
		//$('#tabs').show();
		
	});

}

//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
/* function ibscallback(res){
    var result = res.RESULT.CODE;
    if(result == 0) {
		//공통메세지 팝업 : 성공 메세지... (저장 또는 삭제)
    	showMsgBox("INF", res.RESULT.MESSAGE); //서버에서 정의한 메세지 출력시 사용...
//     	showMsgBox("INF", "<s:message code="MSG.SAVE" />");
    
    	postProcessIBS(res);
    	
    } else {
		//공통메시지 팝업 : 실패 메세지... (저장 또는 삭제)
    	showMsgBox("ERR", res.RESULT.MESSAGE); //서버에서 정의한 메세지 출력시 사용...
//     	showMsgBox("ERR", "<s:message code="ERR.SAVE" />");
    }
} */

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
			
// 			alert (res.resultVO.progrmFileNm);
			//전체 내용을 다시 조회 할 경우 사용...
			//doAction("Search"); return;  
			
			//단건 저장 완료시 해당 저장된 행만 그리드에서 다시 불러온다. 신규일 경우 하단에 Row를 하나 추가한 후 내용을 불러온다...
// 			if(!isBlankStr(res.resultVO.bbsId)) {
				
// 				var crow = grid_sheet.GetSelectRow();
// 				if($('#frmInput #saction').val() == "I") {
// 		        	crow = grid_sheet.DataInsert(-1);
// 				}
// 		        grid_sheet.SetCellValue(crow, "ibsStatus", "");
// 				var param = "bbsId="+res.resultVO.bbsId;
// 				grid_sheet.DoRowSearch(crow, "<c:url value="/commons/sys/bbs/selectAllBoardMstrList.do" />",  param ,0);
				
// 			}
	    	doAction("Search");
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
	    		var crow = grid_sheet.GetSelectRow();
	    		grid_sheet.SetCellValue(crow, "ibsStatus", "");
				doAction("SearchRow");    		
	    	} */
			
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

//데이터 저장후 후처리 함수
function aftersaveGrid(data) {
// 	alert(data.result);
	if(data.result > 0) {
		//alert("저장성공");
		//var message = "<s:message code="MSG.SAVE" />";
		showMsgBox("INF", "<s:message code="MSG.SAVE" />");
		
		//그리드에 추가 또는 업데이트
		if("I" == data.saction) {
			$("#gridlist01").addRowData(data.resultVO.progrmFileNm , data.resultVO, 'first');
		} else if ("U" == data.saction) {
			$("#gridlist01").setRowData(data.resultVO.progrmFileNm, data.resultVO);
// 			$("#gridlist01").addRowData("1", myfirstrow);
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
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="BRD"/> : ' + param.bbsNm + ' [' + param.bbsId +']'; /* 게시판 */
	$('#bbs_sel_title').html(tmphtml);
	
	loadDetail(param);
	

}




	




</script>
</head>
<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="BUBD.MNG"/></div> <!-- 게시판관리 -->
<!-- 	<div class="stit">시스템영역 관리</div> -->
	</div>
</div>
<!-- 메뉴 메인 제목 End-->
<div style="clear:both; height:5px;"><span></span></div>

<!-- <div class="adminContents"> -->
<%-- 	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 관리자 &gt; 게시판관리</div> --%>
    
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
    
	<form name="frmSearch" action ="" method="post">
	<div class="tb_basic2">
    <table border="0" cellspacing="0" cellpadding="0" class="" summary="">
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
      	<th><s:message code="BUBD.NM" /></th> <!-- 게시판명 -->
      	<td>
      		<input type="text" name="searchWrd" value="" ;">
      	</td>
      	<th><s:message code="BUBD.PTRN" /></th> <!-- 게시판 유형 -->
      	<td>
      		<select id="bbsTyCode" name="bbsTyCode" value="" ;">
      		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
      		</select>
      	</td>
      </tr>
<!--       <tr> -->
<!--       <th>게시판 속성</th> -->
<!--       	<td> -->
<%--       		<select id="bbsAttrbCode" name="bbsAttrbCode" value="" ;"> --%>
<!--       		<option value=""><s:message code="WHL" /></option> <! 전체 --> 
<%--       		</select> --%>
<!--       	</td> -->
<!--       	<th>템플릿 유형</th> -->
<!--       	<td> -->
<%--       		<select id="tmplatId" name="tmplatId" value="" ;"> --%>
<!--       		<option value=""><s:message code="WHL" /></option> <!-전체 --> 
<%--       		</select> --%>
<!--       	</td> -->
<!--       </tr> -->
<!--       <tr> -->
<!--       <th>답변 가능 여부</th> -->
<!--       	<td> -->
<%--       		<select id="replyPosblAt" name="replyPosblAt" value="" ;"> --%>
<!--       		<option value=""><s:message code="WHL" /></option> < 전체 --> 
<!--       		<option value = "Y">사용(Y)</option> -->
<!--         <option value = "N">비사용(N)</option> -->
<%--       		</select> --%>
<!--       	</td> -->
<!--       	<th>파일 첨부 여부</th> -->
<!--       	<td> -->
<%--       		<select id="fileAtchPosblAt" name="fileAtchPosblAt" value="" ;"> --%>
<!--       		<option value=""><s:message code="WHL" /></option> <!전체 --> 
<!--       		<option value = "Y">사용(Y)</option> -->
<!--         <option value = "N">비사용(N)</option> -->
<%--       		</select> --%>
<!--       	</td> -->
<!--       </tr> -->
<!--       <tr> -->
<!--       <th>게시판 사용 여부</th> -->
<!--       	<td> -->
<%--       		<select id="useAt" name="useAt" value="" ;"> --%>
<!--       		<option value=""><s:message code="WHL" /></option> <! 전체 --> 
<!--       		<option value = "Y">사용(Y)</option> -->
<!--         <option value = "N">비사용(N)</option> -->
<%--       		</select> --%>
<!--       	</td> -->
<!--       		<th>게시물 구분</th> -->
<!--       	<td> -->
<%--       		<select id="cateCode" name="cateCode" value="" ;"> --%>
<!--       		<option value=""><s:message code="WHL" /></option> <- 전체 --> 
<%--       		</select> --%>
<!--       	</td> -->
<!--       	<th>업무 구분</th> -->
<!--       	<td> -->
<%--       		<select id="cateCode" name="cateCode" value="" ;"> --%>
<!--       		<option value=""><s:message code="WHL" /></option> <!전체 --> 
<!--       		<option value = "BBS001">법령고시구분(BBS001)</option> -->
<!--         <option value = "BBS002">지침/매뉴얼(BBS002)</option> -->
<!--         <option value = "BBS003">ISO9001 품질문서(BBS003)</option> -->
<!--         <option value = "BBS004">개발가이드(BBS004)</option> -->
<!--         <option value = "BBS005">산출물관리(BBS005)</option> -->
<!--         <option value = "BBS006">자료실(BBS006)</option> -->
<!--         <option value = "BBS007">알림마당(BBS007)</option> -->
<!--         <option value = "BBS008">지식나눔(BBS008)</option> -->
<!--         <option value = "BBS009">아무거나톡톡(BBS009)</option> -->
<!--         <option value = "BBS010">묻고답하기(BBS010)</option> -->
<!--         <option value = "BBS011">산출물유형(BBS011)</option> -->
<%--       		</select> --%>
<!--       	</td> -->
<!--       </tr> -->
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="BUBD.DTL.INFO" /></a></li> <!-- 게시판 상세정보 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->

   
<%-- <form id="frmExcel" name="frmExcel" action="" method="post" > --%>
<!-- 	<input type="hidden" name="excelhtml" id="excelhtml"> -->
<!-- 	<input type="hidden" name="excelname" id="excelname"> -->
<%-- </form> --%>


<!-- <div id="excel_pop"> -->
<!-- 	<iframe src=""></iframe> -->
<!-- </div> -->

</body>
</html>