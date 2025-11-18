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

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
        
    	//추가 Event Bind
    	$("#btnTreeNew").show();
    	
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  }).show();
        
        // 저장 Event Bind
        $("#btnSave").click(function(){
        	var dataRows = grid_sheet.GetSaveJson(1); //행 갯수 확인

        	//var rows = grid_sheet.FindStatusRow("I|U|D");
//         	var rows = grid_sheet.IsDataModified();
 	 	   	if (dataRows.Code == "IBS010") return; 
 	 	   	
        	if(dataRows.data.length == 0) {
         		//alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code='MSG.ABRV.CRTN'/>?"; //약어를 생성하시겠습니까
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save"); 	
		}).show().text("<s:message code='STRD.WORD.ABRV.CRTN'/>"); /*약어생성*/
        
        //요청서작성 Event Bind
        $("#btn_rqst").click(function(){
        	var dataRows = grid_sheet.FindCheckedRow("ibsCheck"); //행 갯수 확인
        	if(dataRows == "") {
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVELIST" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code='STRD.WORD.LORQ.FLIN'/>?"; //표준단어 요청서를 작성하시겠습니까
    		showMsgBox("CNF", message, 'RqstWrite');	
        	//doAction("Save"); 	
		}).show();

        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 

        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
//     		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
//     			//삭제 확인 메시지
//     			//alert("삭제하시겠어요?");
//     			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
// //             	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
//         	}
        	doAction("Delete");  
        }).show();
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

        // 엑셀업로 Event Bind
         $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).show();

        // 약어생성Rule 적용버튼
		$("#btnApply").click(function(){
// 			var dataRows = grid_sheet.GetSaveJson(1);
			
// 			if(dataRows.Code == "IBS010") return;
			
// 			if(dataRows.data.length == 0){
//         		showMsgBox("ERR", "<s:message code="ERR.CHKAPPLY" />");
//         		return;
// 			}
			
			doAction("Apply");
		}).show();
        
// 		alert("document.ready");
        setCodeSelect("abrTempList", "L", $("form[name=frmSearch] #abrId"));
    }
    
    
    
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	$(window).resize();
	//$( "#layer_div" ).show();
});


$(window).resize(
    
    function(){
    	setibsheight($("#grid_01"));  
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.STWDABBREVIATED.LST'/>", Align:"Center"}
                ];
                //No.|상태|선택|표준분류|표준단어명|영문의미|약어생성Rule|약어길이|추천약어|영문앞자리|영문앞자음|약어길이|영문앞뒤자음|영문앞뒤약어길이|오류1|오류2|오류3|오류4|오류5|도메인여부|출처구분|한자|설명|한글의미|한글표기명|오류6|단어유형|등록의뢰번호|세부순번|시작시각|등록자|결과코드|결과내용|정의
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Combo",   Width:60,  SaveName:"stndAsrt",      Align:"Center", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",   Width:60,  SaveName:"termKnm",      Align:"Center", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",   Width:120,  SaveName:"engFullNm",    Align:"Center", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Combo",   Width:120,  SaveName:"genRule",     Align:"Center", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Combo",   Width:50,  SaveName:"abrLength",   Align:"Center", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",   Width:80,  SaveName:"initEnm06",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"initEnm01",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"initEnm02",    Align:"Center", Edit:0, Hidden:0},          
                    {Type:"Text",   Width:80,  SaveName:"initEnm03",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"initEnm04",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"initEnm05",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"errTxt01",     Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"errTxt02",   	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"errTxt03",     Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"errTxt04",     Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"errTxt05",     Align:"Center", Edit:0, Hidden:0},
                    {Type:"Combo",   Width:50,  SaveName:"dmnYn",      Align:"Center", Edit:1, Hidden:1}, 
                    {Type:"Text",   Width:50,  SaveName:"srcType",      Align:"Center", Edit:1, Hidden:0}, 
                    {Type:"Text",   Width:30,  SaveName:"chinChar",     Align:"Center", Edit:1, Hidden:0}, 
                    {Type:"Text",   Width:80,  SaveName:"description",  Align:"Center", Edit:1, KeyField:0, KeyField:1},
                    {Type:"Text",   Width:30,  SaveName:"korFullNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:30,  SaveName:"korDispNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:30,  SaveName:"errTxt06",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:20,  SaveName:"dictionType",  Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"abrId",   		Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"dtlSeq",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"strDtm",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:60,  SaveName:"orglUser",     Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"resultCd",     Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:30,  SaveName:"resultTxt",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:30,  SaveName:"definition",   Align:"Center", Edit:1, Hidden:1}
                    
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    SetColProperty("genRule", {ComboCode:"|1|2|3|4|5", 	ComboText:"|<s:message code='META.HEADER.STWDCHANGE.DTL.1'/>"}); //중복제거, 자음우선, 첫번째 모음살림|영문앞자리|영문앞자음|약어길이|영문 앞 뒤 약어 길이 기준
	    SetColProperty("abrLength", {ComboCode:"|2|3|4|5", 	ComboText:"|2|3|4|5"});
	    //SetColProperty("wdDcd", 	${codeMap.wdDcdibs});
        //SetColHidden("rqstUserNm",1);
       	SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		SetColProperty("dmnYn", {ComboCode:"N|Y", 	ComboText:"N|Y"});
        FitColWidth();  
        
        // 초기 약어생성Rule, 약어길이 세팅
        var info = {DefaultValue:"1"};
        var info2 = {DefaultValue:"4"};
        SetColProperty(0, "genRule" ,info);
        SetColProperty(0, "abrLength" ,info2);

        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}




		 
function doAction(sAction ,abrId)
{
        
    switch(sAction)
    {
    
    	case "New":        //추가
	    	//첫행에 추가...
	    	//grid_sheet.DataInsert(0);
	    	//마지막 행에 추가..
	    	var rownum = grid_sheet.DataInsert(-1); 
			
 			var genrule = $("#genRule").val(); //약어생성Rule
 			var abrlen = $("#abrLength").val(); //약어길이
 			
 			if(genrule != ""){
 	 			grid_sheet.SetCellValue(rownum, "genRule",genrule);
 			}

 			if(abrlen != ""){
 	 			grid_sheet.SetCellValue(rownum, "abrLength",abrlen);
 			}
 			
        	break;
        	
    	case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	//delCheck(grid_sheet);
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/meta/stnd/delStwdAbrLst.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	
//         	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
//         	var url = '<c:url value="/commons/user/usergDellist.do"/>';
//         	var param = "";
//         	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
           	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(1); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var genRule = $("#frmGenCon #genRule").val();
        	var abrLength = $("#frmGenCon #abrLength").val();
        	
        	//약어생성Rule, 약어길이 선택했는지 파악후 미선택시 return...
        	//시트상에서 약어생성Rule과 길이가 선택되었다면 패스, 안되어있을시만 적용하도록 한다...
        	for(var i=0; i<SaveJson.data.length; i++) {
        		if(grid_sheet.GetCellValue(i+1, "genRule") == "") {
        			if(genRule == '') {
        				showMsgBox("ERR", "<s:message code='ABRV.CRTN.RULE.CHC'/>"); /*약어생성Rule을 선택해 주세요.*/
        				return;
        			} else {
        				grid_sheet.SetCellValue(i+1, "genRule", genRule);
        			}
        		} 
        		if(grid_sheet.GetCellValue(i+1, "abrLength") == "") {
        			if(abrLength == '') {
        				showMsgBox("ERR", "<s:message code='ABRV.LNGT.CHC'/>"); /*약어길이를 선택해 주세요.*/
        				return;
        			} else {
        				grid_sheet.SetCellValue(i+1, "abrLength", abrLength);
        			}
        		}
        	}

        	
        	SaveJson = grid_sheet.GetSaveJson(1); //약어생성 Rule 및 약어길이가 Update되므로,, 다시 SaveJson을 생성한다.
        	var url = "<c:url value="/meta/stnd/ajaxgrid/generateStwdAbbreviated.do"/>";
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
        	
        case "Search":
        	var tmpstr = "";
        	if(abrId != null && abrId != "" && abrId != "undefined") {
        		tmpstr = "&abrId="+abrId;
        	}else{
	           	if($("select[name=abrId]").val() == '') {
	        		showMsgBox("ERR", "<s:message code='MSG.INQ.LST.CHC' />"); //조회목록을 먼저 선택하세요.
	        		return;
	        	}else{
	        		tmpstr = "&abrId="+$("select[name=abrId]").val() ;
	        	}
        	}
			grid_sheet.DoSearch("<c:url value="/meta/stnd/ajaxgrid/searchStwdAbbreviated_lst.do" />", tmpstr);

        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'표준단어약어생성'});
            
            break;
        case "LoadExcel":  //엑셀업로
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
            
        case "AbrIdSet": //공통코드리스트를 가져온다
    		//공통코드 셀렉트 셋팅 (코드명, 타입(L,C), select)
        	setCodeSelect("abrTempList", "L", $("form[name=frmSearch] #abrId"));
       		break;
       		
        case "RqstWrite":
        	var SaveJson = grid_sheet.GetSaveJson(1);
        	
        	//데이터 사이즈 확인...
 	 	   	if (SaveJson.Code == "IBS000") return; 
 	 	   	if(SaveJson.data.length == 0) return;
 	 	   	
        	var url = "<c:url value="/meta/stnd/regStndWordByAbr.do"/>";
        	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
        	
 	        case "Apply":
 	           	//TODO 공통으로 처리...
//  	        	var SaveJson = grid_sheet.GetSaveJson(0, "ibsCheck"); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
	         	var SaveJson = grid_sheet.GetSaveJson({"ValidKeyField":0}); //doAllSave와 동일한 대상을 가져옴...

 	        	//데이터 사이즈 확인...
 	        	if(SaveJson.data.length == 0) return;
 	        	
 	        	var genRule = $("#frmGenCon #genRule").val();
 	        	var abrLength = $("#frmGenCon #abrLength").val();
 	        	
 	        	if((genRule == '') || (abrLength == '')){
 	        		showMsgBox("ERR","약어생성조건에서 약어생성Rule과 약어길이를 선택해주세요.");
 	        		return;
 	        	}
 	        	
 	        	for(var i=0; i<SaveJson.data.length; i++) {
 	        		
 	        		var row = SaveJson.data[i].ibsSeq;
 	        		
 	        		if(grid_sheet.GetCellValue(i+1, "genRule") == ""){
 	        			grid_sheet.SetCellValue(row, "genRule", genRule);
 	        		}
 	        		
 	        		if(grid_sheet.GetCellValue(i+1, "abrLength") == ""){
 	        			grid_sheet.SetCellValue(row, "abrLength", abrLength);
 	        		}
 	        	}
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
	if(row < 1)	return;

	// 후보 약어를 선택시 빈칸이 아닌경우에 한해 추천약어의 내용을 바꾼다. (조건의 col값은 시트가 변경될때 변경해야함)
	if(col < 13 && col > 7 && grid_sheet.GetCellValue(row, col) != "" && grid_sheet.GetCellValue(row, col+5) == ""){

		grid_sheet.SetCellValue(row, "initEnm06", grid_sheet.GetCellValue(row, col));
		grid_sheet.SetCellFontColor(row, "initEnm06", "#0000FF");
	}
	

}


function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	for(var i=1; i<grid_sheet.RowCount()+1; i++) {
		for(var j=12; j<17; j++) {
			if(grid_sheet.GetCellValue(i, j) != "") {
				grid_sheet.SetCellFontColor(i,j-4,"#FF0000");
			}
		}
		
	}
	  

}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.ETC.abrId)) {
				setCodeSelect("abrTempList", "L", $("form[name=frmSearch] #abrId"));
				doAction("Search", res.ETC.abrId);    		
	    	} 
			break;
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		
		case "REQ_WRITE":
			
			var rqstNo = res.resultVO.rqstNo;
			var bizDcd = res.resultVO.bizDcd;
			
			var url = "../../goRqstPage.do?rqstNo="+rqstNo+"&bizDcd="+bizDcd; 
			window.open().location.href=url;
			
			break;
		
		//약어생성 후처리...
		default : 
			grid_sheet.DoSearch("<c:url value="/meta/stnd/ajaxgrid/searchStwdAbbreviated_lst.do" />", "abrId="+res.action);
			$("#frmGenCon #genRule").val('');
			$("#frmGenCon #abrLength").val('');
			$("#frmInput #abrId").val(res.action);
			
			doAction("AbrIdSet");
			break;
	}
	
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="STRD.WORD.ABRV.CRTN" /></div> <!-- 표준단어 약어생성 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.WORD.INQ' />"> <!-- 표준단어조회 -->
                   <caption><s:message code="STRD.WORD.INQ.FORM" /></caption> <!-- 표준단어 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="abrId"><s:message code="INQ.LST" /></label></th> <!-- 조회목록 -->
                            <td colspan="3">
                                <span id="selectAbrId"><select id="abrId"  name="abrId" class="wd400">
								    <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
<%-- 								    <c:forEach var="code" items="${codeMap.abrNm}" varStatus="status"> --%>
<%-- 								    <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 								    </c:forEach> --%>
								</select></span>
                            </td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.EXIS.ENSN.ABRV.DISPLAY.DATA.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <!-- 존재하는 영문약어는 빨간색으로 표시됩니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
		
		<form id="frmGenCon" name="frmGenCon" method="post">
		<div class="stit"><s:message code="ABRV.CRTN.COND"/></div> <!-- 약어생성조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.WORD.INQ' />"> <!-- 표준단어조회 -->
                   <caption><s:message code="ABRV.CRTN.COND"/></caption> <!-- 약어생성조건 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="genRule"><s:message code="ABRV.CRTN.RULE"/></label></th> <!-- 약어생성Rule -->
                            <td>
                                <select id="genRule"  name="genRule" class="wd230">
								    <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								    <option value="1"><s:message code="DUP.DEL.CONSONANT.FOAL.FIRST.VOWEL"/></option> <!-- 중복제거, 자음우선, 첫번째 모음살림 -->
								    <option value="2"><s:message code="ENSN.BFDGT"/></option> <!-- 영문앞자리 -->
								    <option value="3"><s:message code="ENSN.BF.CONSONANT"/></option> <!-- 영문앞자음 -->
								    <option value="4"><s:message code="ABRV.LNGT"/></option> <!-- 약어길이 -->
								    <option value="5"><s:message code="ENSN.BFAF.ABRV.LNGT.BASE"/></option> <!-- 영문 앞 뒤 약어 길이 기준 -->
								</select>
                            </td>
                            <th scope="row"><label for="abrLength"><s:message code="ABRV.LNGT"/></label></th><!-- 약어길이 -->
                            <td>
                                <select id="abrLength"  name="abrLength" class="wd100">
								    <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								    <option value="2">2</option>
								    <option value="3">3</option>
								    <option value="4">4</option>
								    <option value="5">5</option>
<%-- 								    <c:forEach var="code" items="${codeMap.abrNm}" varStatus="status"> --%>
<%-- 								    <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 								    </c:forEach> --%>
								</select>
                            </td>
                            </tr>
                   </tbody>
                 </table>  
                 <div class="tb_comment">- <s:message code="SHEET.CHC.ABRV.CRTN.RULE.ABRV.LNGT.CHC.APL"/></div>
                 <!-- 시트에서 선택하지 않은 약어생성Rule, 약어길이는 여기서 선택하여 적용합니다. -->
                 <div style="clear:both; height:10px;"><span></span></div> 
            </div>
            </fieldset>
        
        </form>
       <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonStndDiv.jsp" />
	</div>         
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
	  
	<div style="clear:both; height:5px;"><span></span></div>
</div>

</body>
</html>
