<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title>주제영역별오너쉽관리</title>

<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("SearchSubj");

var interval = "";
var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...
// var subjLnmByLVL2Json = ${codeMap.subjLnmByLVL2} ;	
var SubjLstByLvl0Lvl1Json = ${codeMap.SubjLstByLvl0Lvl1} ;	

$(document).ready(function() {
    // 조회 Event Bind
    $("#btnSubjSearch").click(function(){ doAction("SearchSubj");  });
    
    $("#sysAreaId").change(function(){ doAction("Search");  });
	
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("SearchOwner");  });
						
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  });
	
	// 추가(기존) Event Bind
	$("#btnChangAdd").click(function(){
		doAction("AddWam");
	}).hide();
	
	// 삭제 Event Bind
	$("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		showMsgBox("CNF", "<s:message code="CNF.DEL" />", "Delete");
	});
	
    // 저장 Event Bind
    $("#btnSave").click(function(){ 
   		//저장할래요? 확인창...
   		var message = "<s:message code="CNF.SAVE" />";
   		showMsgBox("CNF", message, "Save");	
    }).show();
	
	// 엑셀내리기 Event Bind - Main
	$("#btnSubjExcelDown").click( function(){ doAction("Down2Excel"); } );
	// 엑셀내리기 Event Bind - Sub
	$("#btnExcelDown").click( function(){ doAction("Down2ExcelOwner"); } );
	// 엑셀업로 Event Bind
	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );	
	
    $('#subjSearchPop').button({   //주제영역 검색팝업 이벤트
	    icons: {
	        primary: "ui-icon-search"
	      },
	      text: false, 
	      create: function (event, ui) {
//	     	  $(this).addClass('search_button');
			  $(this).css({
				  'width': '18px',
				  'height': '18px',
				  'vertical-align': 'bottom'
				  });
	    	  
	      }
	    }).click(function(event){
	    	
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		
		//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
		//$('div#popSearch').dialog("open");
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
	    	    param += "&sFlag=SOW";
			var popwin = OpenModal(url+"?"+param, "searchpop",  800, 600, "no");
			popwin.focus();
		
	}).parent().buttonset();
    
    // ERWIN 주제영역 권한추가
    $("#btnErwinProfile").click(function(){
    	var rows = grid_owner_sheet.FindStatusRow("I|U|D");    	
    	
	   	if(!rows) {
	//    		alert("저장할 대상이 없습니다...");
	   		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
	   		return;
	   	}
	   	var SaveJson = grid_owner_sheet.GetSaveJson(0);
		//alert(JSON.stringify(SaveJson));
   	
		if(SaveJson.data.length==0){
			//    		alert("저장할 대상이 없습니다...");
	   		alert("권한 추가할 데이터가 없습니다.");
	   		return;			
		}
   	// 등록은 제외
		for(var i=0; i<SaveJson.data.length; i++){
			var regTypCd = SaveJson.data[i].ibsStatus;
			if(regTypCd!="U"){
				alert("["+ regTypCd + "]저장 후에 ERWIN 권한추가가 가능합니다.");
//				break;
				return;
			}
		}        	
   	
   	//확인창...
		var message = "ERWIN 권한을 추가하시겠습니까?";
		showMsgBox("CNF", message, 'SaveErwinProfile');   	 
    }).hide();            
    
        
	//======================================================
	// 셀렉트 박스 초기화
	//======================================================
	// 시스템영역
	create_selectbox(sysareaJson, $("#sysAreaId"));
	
// 	create_selectbox(SubjLstByLvl0Lvl1Json, $("#frmOnwerSearch #subjId"));
// 	create_selectbox(subjLnmByLVL2Json, $("#frmOnwerSearch #subjId"));
      
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	initUserGrid();
// 	doAction("SearchSubj");
});


$(window).resize(function(){
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.METASUBJ.LST1'/>", Align:"Center"}
                ];
        //No.|시스템영역|주제영역ID|주제영역논리명|주제영역물리명|상위주제영역ID|상위주제영역명|주제영역레벨|표준적용여부|설명|버전|등록유형|작성일시|작성자ID|작성자명|전체경로
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Combo",   Width:100,   SaveName:"sysAreaId",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:150,  SaveName:"subjLnm",   	Align:"Left", Edit:0, TreeCol:1},
                    {Type:"Text",   Width:120,  SaveName:"subjPnm",   	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"uppSubjId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,   SaveName:"uppSubjNm", 	Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"subjLvl", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",  Width:80,   SaveName:"stdAplYn",   	Align:"Center", Edit:0},
                    {Type:"Text",   Width:280,  SaveName:"objDescn",    Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"objVers",     Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:40,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1},                        
                    {Type:"Date",   Width:120,  SaveName:"writDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"writUserId",  Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"writUserNm",  Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:180,  SaveName:"fullPath",  Align:"Left", Edit:0}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
	     SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"N|Y"});
	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"});
        
        InitComboNoMatchText(1, "");
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}


function initUserGrid()
{
    
    with(grid_owner_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.METASUBJ.LST2'/>", Align:"Center"}
                ];
        //No.|상태|선택|주제영역권한ID|주제영역명|주제영역명|주제영역명|팀장여부|사용자ID|사용자명|부서명|요청일시|요청자ID|요청자명|요청번호|요청일련번호
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",	 Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",  Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:60, SaveName:"ibsCheck", Align:"Center", Edit:1, Hidden:0, Sort:0},
					
// 					{Type:"Combo",   Width:100,  SaveName:"subjBizDcd",    Align:"Left", Edit:1, Hidden:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"subjOwnerId",    Align:"Left", Edit:0, Hidden:1},
					{Type:"Combo",   Width:300,  SaveName:"subjId",  Align:"Left", Edit:1, KeyField:1,UpdateEdit:0},
					{Type:"Text",   Width:150,  SaveName:"subjLnm",   	Align:"Left", Edit:0, Hidden:1,UpdateEdit:0},
					{Type:"Text",   Width:150,  SaveName:"subjPnm",  Align:"Left", Edit:0, Hidden:1},
					{Type:"Combo",   Width:60,  SaveName:"ownerYn",  Align:"Center", Edit:1, KeyField:0,Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"userId",  Align:"Left", Edit:0, KeyField:1},
					{Type:"Popup",   Width:130,  SaveName:"userNm",    Align:"Left", Edit:1 ,KeyField:1,UpdateEdit:0},
					{Type:"Text",   Width:100,  SaveName:"deptNm",  Align:"Left", Edit:0, Hidden:0},
					
// 					{Type:"Combo",   Width:100,  SaveName:"stndOwnerYn",    Align:"Left", Edit:1, Hidden:1 },
// 					{Type:"Combo",   Width:100,  SaveName:"pdmOwnerYn",    Align:"Left", Edit:1 ,KeyField:1},
// 					{Type:"Combo",   Width:100,  SaveName:"ddlOwnerYn",    Align:"Left", Edit:1 ,KeyField:1},

					{Type:"Text",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1},
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	//SetColProperty("usergId", ${codeMap.usergibs});
	   	SetColProperty("subjId", ${codeMap.SubjLstByLvl0Lvl1ibs});
        SetColProperty("regTypCd",{ComboCode:"C|U|D", ComboText:"신규|변경|삭제"});
//         SetColProperty("subjBizDcd", 	{ComboCode:"DIC|R9P|DDL", ComboText:"표준사전|R9연계물리모델|DDL생성"});
// 		SetColProperty("stndOwnerYn", 	{ComboCode:"N|Y", ComboText:"N|Y"});
// 		SetColProperty("pdmOwnerYn", 	{ComboCode:"Y|N", ComboText:"Y|N"});
// 		SetColProperty("ddlOwnerYn", 	{ComboCode:"Y|N", ComboText:"Y|N"});

		SetColProperty("ownerYn", 	{ComboCode:"N|Y", 	ComboText:"N|Y"});
		
        InitComboNoMatchText(1, "");
        
        FitColWidth();  
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함===
    init_sheet(grid_owner_sheet);    
    //===========================
}


function doAction(sAction)
{
        
    switch(sAction)
    {
		case "New" :
			//첫행에 추가...
		 	grid_owner_sheet.DataInsert(0);
		 	grid_owner_sheet.SetCellValue(1, "subjId",$("#subjId").val());
		 	grid_owner_sheet.SetCellValue(1, "userNm",$("#userNm").val());
		 	grid_owner_sheet.SetCellValue(1, "deptNm",$("#deptNm").val());		 	
		 	grid_owner_sheet.SetCellValue(1, "subjLnm", $('#frmOnwerSearch #subjId option:selected').text());	// 주제영역명
		 	
			break;
    
	    case "SearchSubj":
	    	var param = $('#frmSearch').serialize();
	    	grid_owner_sheet.RemoveAll();
	    	grid_sheet.DoSearch("<c:url value="/meta/subjarea/getsubjlist.do" />", param);
	    	break;
	    	
        case "SearchOwner":
        	var param = $('#frmOnwerSearch').serialize();
//         	if($('#frmOnwerSearch #subjLnm').val() == "" || $('#frmOnwerSearch #subjLnm').val() == "undefined"){
//         		var message = "<s:message code="ERR.EMPTY"  arguments="주제영역을 선택하십시오." />";
// 				showMsgBox("INF", message); 
//         		return ;
//         	}
        	//grid_owner_sheet.DoSearch('<c:url value="/meta/subjarea/getsubjOnwerlist.do" />', param);
        	
        	grid_owner_sheet.DoSearch('<c:url value="/meta/subjarea/getsubjOnwerDetList.do" />', param);
        	
        	break;	 
        	

        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = grid_owner_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	var SaveJson = grid_owner_sheet.GetSaveJson(0); 
//        	alert(JSON.stringify(SaveJson));

        	
        	if(SaveJson.data.length == 0) return;
            var url = "<c:url value="/meta/subjarea/regsubjonwerlist.do"/>";
//         	var param = $('#frmOnwerSearch').serialize();
        	IBSpostJson2(url, SaveJson, null, ibscallback);
        	break;
        	
        	
        case "Delete" :
	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_owner_sheet);
        	//TODO 공통으로 처리...
        	var rows = grid_owner_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	var SaveJson = grid_owner_sheet.GetSaveJson(0);
        	if(SaveJson.data.length == 0) return;
            var url = "<c:url value="/meta/subjarea/delSubjOwnerList.do"/>";
//         	var param = $('#frmOnwerSearch').serialize();
        	IBSpostJson2(url, SaveJson, null, ibscallback);
        	break;
            
       
		case "LoadExcel":  //엑셀업로
			grid_owner_sheet.LoadExcel({Mode:'HeaderMatch'});
			break;
			
        case "Down2ExcelOwner":  //엑셀내려받기
        //보여지는 컬럼들만 엑셀 다운로드          
        var downColNms = "";
       	for(var i=0; i<grid_owner_sheet.LastCol();i++ ){
       		if(grid_owner_sheet.GetColHidden(i) != 1){
       			downColNms += grid_owner_sheet.ColSaveName(0,i)+ "|";
       		}
       	}


       	grid_owner_sheet.Down2Excel({HiddenColumn:1,  DownCols:downColNms, Merge:1});
//         	grid_owner_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "Down2Excel":
        	grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        	break;        	
        	
        case "SaveErwinProfile" :	// ERWIN 권한추가 
	    	//TODO 공통으로 처리...
	    	var rows = grid_owner_sheet.FindStatusRow("I|U|D");
	    	if(!rows) {
	//     		alert("저장할 대상이 없습니다...");
	    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
	    		return;
	    	}
	    	var SaveJson = grid_owner_sheet.GetSaveJson(0); 
	//    	alert(JSON.stringify(SaveJson));
	
	    	
	    	if(SaveJson.data.length == 0) return;
	        var url = "<c:url value="/meta/subjarea/regErwinProfilelist.do"/>";
	//     	var param = $('#frmOnwerSearch').serialize();			
	    	IBSpostJson2(url, SaveJson, null, ibscallback);
	    	break;
        	
    }       
}
 
//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	$("#frmSearch #fullPath").val(retjson.fullPath);
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			break;
		
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("SearchOwner");
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}


//사용자조회 팝업 리턴값 처리
function returnUserPop (ret, row) {

	var retjson = jQuery.parseJSON(ret);
	var selectRow = grid_owner_sheet.GetSelectRow();

	//alert(ret+ selectRow);

	grid_owner_sheet.SetCellValue(selectRow, "userId", retjson.userId);
	grid_owner_sheet.SetCellValue(selectRow, "userNm", retjson.userNm);
}


function grid_owner_sheet_OnPopupClick(Row,Col) {
	//사용자 검색 팝업 오픈
	if ("userNm" == grid_owner_sheet.ColSaveName(Col)) {
		var param = "row=" +Row;
		var url = '<c:url value="/commons/damgmt/sysarea/popup/userlst_pop.do" />';
		openLayerPop(url, 700, 500, param);
	}
	//주제영역 검색 팝업
	if ("subjId" == grid_owner_sheet.ColSaveName(Col)) {
		var param = "ibsSeq=" +Row;
		    param += "&sFlag=SOW";
		var url = '<c:url value="/meta/subjarea/popup/subjSearchPop.do" />';
		openLayerPop(url,  800, 600, param);
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
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	//하위노드 확인
// 	var childCount = grid_sheet.GetChildNodeCount(row);
 	var subjLvl = grid_sheet.GetCellValue(row, "subjLvl");
//  	if ("3" == subjLvl) {
//  		showMsgBox("INF", "주제영역 3레벨은 선택할 수 없습니다.");
//  		return;
//  	} 
 	
 	$('#frmOnwerSearch #subjId').val(grid_sheet.GetCellValue(row, "subjId"));
	
	//주제영역별 사용자 조회
	gridOnwerSearch(row);
    
}

function gridOnwerSearch(row){
	
	var param = "subjId=" + grid_sheet.GetCellValue(row, "subjId");

	grid_owner_sheet.DoSearch('<c:url value="/meta/subjarea/getsubjOnwerDetList.do" />', param);
}

function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
	}
	
}

</script>

</head>

<body>
	<!-- 메뉴 메인 제목 -->
	<div class="menu_subject">
		<div class="tab">
		    <div class="menu_title">주제영역 관리</div>
		</div>
	</div>
	<!-- 메뉴 메인 제목 -->
	
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 검색조건 입력폼 -->
	<div id="search_div">
		<div class="stit">검색조건</div>
		<div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역조회">
                   <caption>테이블 이름</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
							<th scope="row" class=""><label for="sysAreaId">시스템영역</label></th>
							<td>
								<select id="sysAreaId" class="" name="sysAreaId">
								<option value="">전체</option>
								</select>
							</td>
							<th scope="row"><label for="fullPath">주제영역명</label></th>
							<td>
								<input type="text" name="fullPath" id="fullPath" class="wd90p" />
								<button class="btnDelPop" >삭제</button>
		                        <button class="btnSearchPop" id="subjSearchPop">검색</button>
							</td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment">- 조회결과를 클릭 하시면 상세조회를 하실 수 있습니다.<br>- 데이터를 복사하시려면 복사할 컬럼을 선택하시고 <span style="font-weight:bold; color:#444444;">Ctrl + C</span>를 사용하시면 됩니다.</div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 버튼영역  -->         
		<div class="divLstBtn" style="display: ;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSubjSearch" 	name="btnSubjSearch">조회</button> 
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnSubjExcelDown" name="btnSubjExcelDown">엑셀 내리기</button>                       
	    	</div>
        </div>
         <!-- 버튼영역  -->
	</div>
	
	<div style="clear:both; height:5px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title">주제영역명 : <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">주제영역별 권한</a></li>
	  </ul>
	  <div id="tabs-1">
			<div id="search_div">
			<div class="stit">검색조건</div>
			<div style="clear:both; height:5px;"><span></span></div>
	        
	        <form id="frmOnwerSearch" name="frmOnwerSearch" method="post">
	            <fieldset>
	            <legend>머리말</legend>
	            <div class="tb_basic2">
	                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="사용자조회">
	                   <caption>테이블 이름</caption>
	                   <colgroup>
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   </colgroup>
	                   
	                   <tbody>                            
						<tr>  
							<th scope="row"><label for="subjId">주제영역명</label></th>
							<td >
								<select id="subjId" class="" name="subjId">
								<option value="">전체</option>
								</select>
							</td>
							<th scope="row" class=""><label for="ownerYn">팀장여부</label></th>
							<td>
								<select id="ownerYn" class="" name="ownerYn">
									<option value="">전체</option>
									<option value="Y"><s:message code="MSG.YES" /></option>
									<option value="N"><s:message code="MSG.NO" /></option>
								</select>
						</tr>
						<tr>                               
							<th scope="row" class=""><label for="deptNm">부서명</label></th>
							<td><input type="text" name="deptNm" id="deptNm" /></td>
							<th scope="row"><label for="userNm">사용자명</label></th>
							<td><input type="text" name="userNm" id="userNm" /></td>
						</tr>
	                   </tbody>
	                 </table>   
	            </div>
	            </fieldset>
	            
	        <div class="tb_comment"><s:message  code='ETC.COMM5' /></div>
	        </form>
			<div style="clear:both; height:10px;"><span></span></div>
	         <!-- 버튼영역  -->
			<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />
			&nbsp;<button class="btn_rqst_new" id="btnErwinProfile" 	name="btnErwinProfile">ERWIN 권한추가</button>
	         <!-- 버튼영역  -->
		</div>	
		
		<div style="clear:both; height:5px;"><span></span></div>
		<!-- 그리드 입력 입력 -->
		<div id="grid_01" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_owner_sheet", "100%", "300px");</script>            
		</div>
		<!-- 그리드 입력 입력 -->
	  </div>
	 </div>	
	
	
	
	

</body>
</html>