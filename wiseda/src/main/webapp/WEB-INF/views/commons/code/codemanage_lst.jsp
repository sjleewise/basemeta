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
<title><s:message code="COM.CD.MNG" /></title> <!-- 공통코드 관리 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">
//최근 선택 row
// var lastsel;
//엔터키 이벤트 처리
EnterkeyProcess("Search");
$(document).ready(function(){
	
	//그리드 초기화
	initGrid();
	
	//그리드 하단에서 드래그로 높이 조정 ==> documentready.js에서 초기화
// 	bindibsresize();
	
	//탭 초기화....
// 	$( "#tabs" ).tabs({heightStyle:"fill"});
	//$( "#tabs" ).tabs();
	
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
	
	//추가 버튼...
/* 	$('button.btn_rqst_new').button({
	       icons: {
	          primary: "ui-icon-circle-plus",
	          secondary: "ui-icon-triangle-1-s"
	        }
	}).unbind('click').click(function() {
		doAction('Add');
	}); */
	
	//조회 이벤트 처리
	$("#btnSearch").click(function(){ 
		doAction('Search'); 
	});
	
	//추가 이벤트 처리
	$("#btnNew").click(function(){
		doAction('Add');
		//var su = $("#gridlist01").jqGrid('addRow');
// 		var su=$("#gridlist01").jqGrid('addRowData', 99, {});
// 		alert(su);
// 		$("#gridlist01").jqGrid();
// 		if(su) alert("Succes. Write custom code to add data in server"); else alert("Can not update");
	});

	//삭제버튼 이벤트 처리
	$("#btnDelete").click(function(){
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL.CODE" />", 'Delete');
//         	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
    	}
		
	});
	
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

    // 엑셀업로 Event Bind
    $("#btnExcelLoad").click( function(){ 
    	
// 		var url = '<c:url value="/commons/sys/program/popup/program_xls.do"/>';
		var url = '<c:url value="/commons/code/popup/commdcd_xls.do"/>';
// 		$('div#excel_pop iframe').attr('src', url);
		openLayerPop(url, 800, 550);
		
    	//doAction("LoadExcel"); 
    });
	
    $("#tab-1").click(function(){
    	//alert("tab1 click");
//     	$(window).resize();
    });
    $("#tab-2").click(function(){
//     	alert("window resize");
//     	$(window).resize();
    });

    //공통코드 초기화 셋팅
    setCodeSelect("COM_DCD", "L", $("form[name=frmSearch] #commDcdId"));
    
	//페이지 호출시 처리할 액션...
	loadDetail();
	//doAction('Add');
	
	
});

$(window).resize(function() {
    if(this.resizeTO) clearTimeout(this.resizeTO);
    this.resizeTO = setTimeout(function() {
        $(this).trigger('resizeEnd');
    }, 300);
});

//화면 재조정시 그리드 사이즈 조정...
$(window).bind('resizeEnd', function() {
    //do something, window hasn't changed size in 500ms
    //TODO 윈도우 리사이즈 기능 확인필요...
 	//grid_sheet.FitColWidth();
});
// $(window).resize(function(){
	
// });


function initGrid() {

    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.CODEMANAGE.LST'/>", Align:"Center"}
                    /* No.|상태|선택|공통코드ID|공통코드|공통코드명|공통코드설명|상위공통코드ID|시스템코드여부 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:60, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"commDcdId",   Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"commDcd",   	Align:"Left", Edit:0, KeyField:0},
                    {Type:"Text",   Width:100,  SaveName:"commDcdNm",   Align:"Left", Edit:0, KeyField:0 }, 
                    {Type:"Text",   Width:150,  SaveName:"objDescn",   	Align:"Left", Edit:0, }, 
                    {Type:"Text",   Width:120,  SaveName:"uppCommDcdId",Align:"Left", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:50,  SaveName:"sysCdYn", 	Align:"Center", Edit:0},
                ];
                    
        InitColumns(cols);
        //콤보 목록 설정...
        SetColProperty("sysCdYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	     SetColProperty("lecyDcd", 		{ComboCode:"N|Y", 	ComboText:"아니요|예"});
 		 //SetColProperty("regTypCd", 	${codeMap.regTypCdibs}); //{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"}
       
       //콤보코드일때 값이 없는 경우 셋팅값
       InitComboNoMatchText(1, "");
       
//        SetColHidden("ibsStatus",1);
//        SetColHidden("objVers",1);

        
        FitColWidth();
//         SetExtendLastCol(1);
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
		grid_sheet.DoSearch('<c:url value="/commons/code/getcodelist.do" />', param);
// 		
		break;

	case 'SearchDtlCode' :	//조회
		var param = $('form[name=frmInput]').serialize();
// 		param = "" + "commDcdId="+param.commDcdId;
		grid_sub.DoSearch('<c:url value="/commons/code/getcodeDtllist.do" />', param);
		break;
	case 'SearchAllDtl' :	//조회
// 		var param = $('form[name=frmInput]').serialize();
		
		//bug : 파라미터를 강제로 셋팅하지 않을 경우 이전 DoSearch 했던 파라메터 정보가 doSearch 할때 날라감....  
// 		var param = "commDcdId=";
		loadDetail();
		grid_sub.DoSearch('<c:url value="/commons/code/getcodeDtllist.do" />');
		break;
	case 'Detail' : //상세 정보
		//onclick 그리드 함수에서 처리...
		break;
	case 'Add' : //신규 추가
		//grid_sheet.DataInsert(0);
	
		loadDetail();
	
		$('#program_sel_title').html('<s:message code='MSG.COM.CD.DTL.INFO.INQ' />'); /* 공통코드를 클릭하시면 상세정보를 조회합니다. */
		//상세코드 리스트 그리드 초기화
		grid_sub.RemoveAll();
		break;
	case 'AddDtlCode' : //상세코드 신규 추가
		//첫행에 추가...
    	var nrow = grid_sub.DataInsert(0);

    	
    	//마지막 행에 추가..
    	//grid_sheet.DataInsert(-1);
    	
    	//공통코드 ID 셋팅    	
    	grid_sub.SetCellValue (nrow, 'commDcdId', $('#frmInput #commDcdId').val());
    	grid_sub.SetCellValue (nrow, "useYn", "Y");
    	
		break;
		
	case 'Save'	: //공통코드 및 상세코드 리스트 저장
       // var url = "<c:url value="/commons/code/regDtlCodelist.do"/>";
        //공통코드 상위 폼 정보도 같이 저장
       // var param = $('form[name=frmInput]').serialize();
        //IBSpostJson2(url, ibsSaveJson, param, ibscallback);
        //단건저장으로 해야 정상 작동하여 임시로 수정.(14.07.14)
        var urls = '<c:url value="/commons/code/saveCodeRow.do"/>';
		var param = $('form[name=frmInput]').serialize();
		
		ajax2Json(urls, param, ibscallback);

		break;
	case 'SaveRow': //단건 저장
		alert("SaveRow!!");
		//saction (I-입력, U-수정)
		var urls = '<c:url value="/commons/code/saveCodeRow.do"/>';
		var param = $('form[name=frmInput]').serialize();
		ajax2Json(urls, param, ibscallback);
		
		break;
	case 'SaveDtlCode': //상세코드 저장
		var SaveJson = grid_sub.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//     	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
    	//데이터 사이즈 확인...
    	if(SaveJson.data.length == 0) return;
    	
        var url = "<c:url value="/commons/code/regDtlCodelistWithout.do"/>";
        //공통코드 상위 폼 정보도 같이 저장
        var param = $('form[name=frmInput]').serialize();
        IBSpostJson2(url, SaveJson, param, ibscallback);
		
		break;
	case 'Delete' : //삭제
	
		//트리 시트의 경우 하위 레벨도 체크하도록 변경...
    	//setTreeCheckIBS(grid_sheet);

    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
    	delCheckIBS(grid_sheet);
    	
    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
    	if(DelJson.data.length == 0) return;
    	
    	var url = "<c:url value="/commons/code/delCodelist.do"/>";
    	var param = "";
    	IBSpostJson2(url, DelJson, param, ibscallback);
    	
		break;

	case 'DeleteDtl' : //상세코드 삭제
	
		//트리 시트의 경우 하위 레벨도 체크하도록 변경...
    	//setTreeCheckIBS(grid_sheet);

    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
    	delCheckIBS(grid_sub);
    	
    	var DelJson = grid_sub.GetSaveJson(0, "ibsCheck");
    	if(DelJson.data.length == 0) return;
    	
    	var url = "<c:url value="/commons/code/delDtlCodelist.do"/>";
    	var param = "";
    	IBSpostJson2(url, DelJson, param, ibscallback);
    	
		break;
		
    case "Down2Excel":  //엑셀내려받기
        
        grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        
        break;
    case "LoadExcel":  //엑셀업로드
      
        grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        
        break;
        
    case "ComCodeSet": //공통코드리스트를 가져온다
//     	var url = "<c:url value="/commons/code/getComCodeList.do"/>";
//     	var url = "<c:url value="/commons/code/getComboIbs.do"/>";
//     	var param = {codenm:"COM_DCD", type:"LI"};
//     	ajax2Json(url, param, codesetting);
		//공통코드 IBS셋팅 (코드명, 타입(LI,CI), 그리드명, saveName)
		setComboIBS("COM_DCD", "LI", grid_sub, "commDcdId");

		//공통코드 셀렉트 셋팅 (코드명, 타입(L,C), select)
		setCodeSelect("COM_DCD", "L", $("form[name=frmSearch] #commDcdId"));
    break;

	}
}

//공통코드 리스트를 가져와서 상세코드 그리드 및 select에 셋팅한다.
// function codesetting (data) {
// 	//alert(data.COM_DCD);
// 	if(data){
// 		grid_sub.SetColProperty("commDcdId", data);
// 	}
// }

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/code/ajaxgrid/selectCodeDetail.do"/>', param, function(){
// 		//$('#tabs').show();
		
		
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
				//공통코드 리스트 재조회
				doAction("ComCodeSet");
		
			break;
		//상세 그리드 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL_DTL%>" :
				doAction("SearchDtlCode");
				//doActionCol("Search");
				//공통코드 리스트 재조회
// 				doAction("ComCodeSet");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
// 			alert (res.resultVO.commDcdId);
			//전체 내용을 다시 조회 할 경우 사용...
			//doAction("Search"); return;  
			
			//단건 저장 완료시 해당 저장된 행만 그리드에서 다시 불러온다. 신규일 경우 하단에 Row를 하나 추가한 후 내용을 불러온다...
			if(!isBlankStr(res.resultVO.commDcdId)) {
				
				var crow = grid_sheet.GetSelectRow();
				if($('#frmInput #saction').val() == "I") {
		        	crow = grid_sheet.DataInsert(-1);
				}
		        grid_sheet.SetCellValue(crow, "ibsStatus", "");
				var param = "commDcdId="+res.resultVO.commDcdId;
				grid_sheet.DoRowSearch(crow, "<c:url value="/commons/code/getcodelist.do" />",  param ,0);
				
			}
			
			//공통코드 리스트 재조회
			doAction("ComCodeSet");
	    	
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
			doAction("SearchDtlCode");
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
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
	var tmphtml = '<s:message code='COM.DV.CD' /> : ' + param.commDcd + ' [' + param.commDcdNm +']'; /* 공통구분코드 */
	$('#program_sel_title').html(tmphtml);
	
	loadDetail(param);
	
	
	//공통코드에 포함되는 상세코드 리스트를 조회한다.
// 	alert("공통상세코드 조회");
	var param1 = "commDcdId="+param.commDcdId;
	grid_sub.DoSearch('<c:url value="/commons/code/getcodeDtllist.do" />', param1);
// 	doAction("SearchDtlCode");
	

}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code="MSG.STRG.SCS" />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//form 내용을 초기화 한다.....
		doAction('Add');
		//$('#btnfrmReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}

}


</script>
</head>
<body>
<!-- 메뉴 메인 제목 Start-->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="COM.CD.MNG" /></div> <!-- 공통코드관리 -->
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
      	<th><s:message code="COM.CD" /></th> <!-- 공통코드 -->
        <td>
	        <select  id="commDcdId" name="commDcdId" title="<s:message code='COM.CD.SEL'/>"> <!-- 공통코드선택 -->
	        	<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
	        </select>
        </td>
      	<th><s:message code="COM.CD.NM" /></th> <!-- 공통코드명 -->
      	<td>
      		<input type="text" name="commDcdNm" class="wd200" value="${searchVO.commDcdNm}"> 
      	</td>
      </tr>
      <tr>
      	<th><s:message code="DTL.CD" /></th> <!-- 상세코드 -->
        <td>
	        <input type="text" name="commDtlCd" class="wd200"/> 
        </td>
      	<th><s:message code="DTL.CD.NM" /></th> <!-- 상세코드명 -->
      	<td>
      		<input type="text" name="commDtlCdNm" class="wd200"/> 
      	</td>
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
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
	    <li id="tab-1"><a href="#tabs-1"><s:message code="COM.CD.DTL.INFO" /></a></li> <!-- 공통코드상세정보 -->
	    <li id="tab-2"><a href="#tabs-2"><s:message code="DTL.CD.LST" /></a></li> <!-- 상세코드리스트 -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo"></div>
<%-- 		<%@include file="codemanage_dtl.jsp" %> --%>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  <div id="tabs-2">
	  	<%@include file="codedtlmanage_lst.jsp" %>
			<!-- 	상세코드리스트 grid import -->
<!-- 			<div id="detailInfo"></div> -->
			<!-- 	상세코드리스트 grid import end -->
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