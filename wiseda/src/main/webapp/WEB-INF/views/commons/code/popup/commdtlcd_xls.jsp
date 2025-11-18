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

$(document).ready(function(){
	
	console.log(${codeMap.regTypCdibs});
	
	//그리드 초기화
	initgrid();
	
	//그리드 하단에서 드래그로 높이 조정
/* 	$( ".grid_01" ).resizable({
		minHeight: 150,
		handles: "s",
		resize: function( event, ui ) {
			//alert(ui.size.width+":"+ui.size.height);
			$(this).children("div:first").css('height', ui.size.height);
			//$("#DIV_grid_sheet").css('height', ui.size.height);
			
		}
		
	}); */
	
	//팝업 닫기 버튼 클릭 이벤트 - common.js 공통함수
	$('div.pop_tit_close').click(function(){
		
		parent.closeLayerPop();
		
		//$('div#excel_pop', parent.document).dialog("close");
		
	});
	
	
	//엑셀 올리기 버튼 셋팅 및 클릭 이벤트 처리...
	$('#btnExcelUp').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		doAction('LoadExcel');
	});
	
	//엑셀 저장 버튼 초기화...
	$('#btnSaveExl').click(function(event){
		//var rows = grid_sheet.FindStatusRow("I|U|D");
    	var rows = grid_sheet.IsDataModified();
    	if(!rows) {
//     		alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');	
    	//doAction("Save");  
	});
	
	 // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ 
    	grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
    });

	//$('#btnSaveExl').click();
	
});



function initgrid() {

    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.COMMDTLCD.XLS'/>", Align:"Center"}
                    /* No.|상태|삭제|요청구분|공통코드|상세코드ID|상세코드|상세코드명|상세코드설명|버전|상위상세코드ID|출력순서 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"DelCheck", Width:60,   SaveName:"ibsCheck",    Align:"Center", Hidden:0},
                    {Type:"Combo",  Width:60,   SaveName:"regTypCd",     Align:"Center", Edit:1},
                    {Type:"Combo",   Width:100,  SaveName:"commDcdId",   Align:"Left", Edit:1, KeyField:0}, 
                    {Type:"Text",   Width:100,  SaveName:"commDtlCdId",   Align:"Left", Edit:1}, 
                    {Type:"Text",   Width:100,  SaveName:"commDtlCd",   	Align:"Left", Edit:1, KeyField:1},
                    {Type:"Text",   Width:120,  SaveName:"commDtlCdNm",   Align:"Left", Edit:1, KeyField:1}, 
                    {Type:"Text",   Width:150,  SaveName:"objDescn",   Align:"Left", Edit:1}, 
                    {Type:"Int",    Width:50,   SaveName:"objVers", 	Align:"Center", Edit:1},
                    {Type:"Text",   Width:100,  SaveName:"uppCommDtlCdId",Align:"Left", Edit:1},
                    {Type:"Int",   Width:50,  SaveName:"dispOrd", 	Align:"Center", Edit:1},
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
// 	    SetColProperty("sysCdYn", 	{ComboCode:"N|Y", 	ComboText:"아니요|예"});
      	SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("commDcdId", ${codeMap.commDcdIdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
        SetColHidden("objVers",1);
        FitColWidth();
        //SetExtendLastCol(1);
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
		grid_sheet.DoSearch('<c:url value="/commons/sys/program/selectProgramListIBS.do" />', param);
// 		
		break;
	case 'Detail' : //상세 정보
		//onSelectRow 그리드 함수에서 처리...
		break;
	case 'Add' : //신규 추가
		loadDetail();
		$('#program_sel_title').html('<s:message code='MSG.PGM.DTL.INFO.INQ' />'); /* 프로그램을 클릭하시면 상세정보를 조회합니다. */
		break;
		
	case 'Save' : //엑셀 일괄 저장
		var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//     	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
    	//데이터 사이즈 확인...
    	if(SaveJson.data.length == 0) return;
    	
    	//alert(SaveJson.data.length); return;
        var url = "<c:url value="/commons/code/regDtlCodeListXls.do"/>";
    	var param = "";
        IBSpostJson2(url, SaveJson, param, ibscallback);
	break;
	
	case 'SaveRow': //단건 저장
		//saction (I-입력, U-수정)
		var urls = '<c:url value="/commons/sys/program/saveProgramRow.do"/>';
		var param = $('form[name=frmInput]').serialize();
		ajax2Json(urls, param, ibscallback);
		
		break;
	case 'Delete' : //삭제
	
		//트리 시트의 경우 하위 레벨도 체크하도록 변경...
    	//setTreeCheckIBS(grid_sheet);
    	
    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
    	delCheckIBS(grid_sheet);
    	
    	var sRow = grid_sheet.FindCheckedRow("ibsCheck");
    	//받은 결과를 배열로 생성한다.
    	var arrRow = sRow.split("|");
    	var tmpkey = "";
    	for(idx=0; idx<arrRow.length; idx++){ 
    		//alert(arrRow[idx]);
    		tmpkey += grid_sheet.GetCellValue(arrRow[idx], 'progrmFileNm') +"|";
		}

    	//url 호출
		var urls = '<c:url value="/commons/sys/program/deleteProgramList.do"/>';
		var param = "progrmFileNm="+tmpkey;
		ajax2Json(urls, param, ibscallback);
    	
    	//체크된 행을 Json 리스트로 생성...
/*     	var delJson = grid_sheet.GetSaveJson(0, "ibsCheck");
    	if(delJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
    	
    	var url = '<c:url value="/commons/sys/program/ajaxgrid/deleteProgram.do"/>';
//     	$.postJSON(url, DelJson, ibscallback);
        IBSpostJson2(url, delJson, null, ibscallback); */
    	
		break;
		
    case "Down2Excel":  //엑셀내려받기
        
        grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        
        break;
    case "LoadExcel":  //엑셀업로드
      
        grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        
        break;

	}
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
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			
			//엑셀추가 내용 부모페이지에서 확인
			//parent.doAction("Search");
			$("#btnSearch", parent.document).click();
			//엑셀 업로드 팝업창 닫기
			//parent.closeLayerPop();
			
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

}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
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
		//doAction('Add');
		//$('#btnfrmReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}

}
</script>
</head>
<body>
<div class="pop_tit" style="width:800px;">
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code='EXCL.UPLOAD.COM.DTCD' /></div> <!-- 엑셀업로드-공통상세코드 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!--    		<div class="stab">
           	<div class="stab_108_over">요청서목록</div>
               <div class="stab_108"><a href="#">요청서목록</a></div>
               <ul class="bt02">
                   <li class="bt02_50"><a href="#">엑셀읽기</a></li>
                   <li class="bt02_50"><a href="#">저장</a></li>
               </ul>
           </div> -->    
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="EXCL.UP" /></div> <!-- 엑셀 올리기 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic" style="display: none;">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class="th_require"><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 -->
                            <td>
                                <select id="sysAreaId" class="" name="sysAreaId">
                                        <th scope="row" class="th_require"><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 -->
                                 </select>
                            </td>
                           <th scope="row" class="th_require"><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" value="${subjLnm}"/>
                                </span>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.EXCEL' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
	<div class="divLstBtn" style="display: none;">  
        <div class="bt03">
			<button class="da_default_btn" id="btnExcelUp" name="btnExcelUp"><s:message code="EXCL.UP" /></button><!--엑셀 올리기-->
			<button class="da_default_btn" id="btnSaveExl" name="btnSaveExl"><s:message code="STRG" /></button><!--저장-->
		</div>  
		<div class="bt02">
			<button class="btn_excel_down"  id="btnExcelDown"  name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
		</div>
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
    
	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
	<div style="clear:both; height:10px;"><span></span></div>
   
	</div>
</div>

</body>
</html>