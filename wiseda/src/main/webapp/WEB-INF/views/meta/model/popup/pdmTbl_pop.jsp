<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title><s:message code="TBL.INQ"/></title> <!-- 테이블 조회 -->

<script type="text/javascript">

var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...

                       
$(document).ready(function() {
	
	// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	
		//======================================================
        // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
        //======================================================
        initSearchPop();	//검색 팝업 초기화
        initSearchButton();	//검색 버튼 초기화
        
    	//버튼 초기화...
    	$("#btnTreeNew, #btnSave, #btnDelete").hide();
        
		//검색조건 display none
// 		$("div#search_div").hide();
		
		//탭 초기화....
		//$( "#tabs" ).tabs();
		
		//마우스 오버 이미지 초기화
		////imgConvert($('div.tab_navi a img'));
		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });
        
        // 변경대상 추가 Event Bind
        $("#btnChangAdd").click(function(){ 
        	
        	openSearchPop("<c:url value='/meta/model/pop/pdmtblSearchPop.do' />"); 
        
        }); //doAction("NewChg"); });

        // 추가 (하위레벨) Event Bind
//         $("#btnNewLow").click(function(){ doAction("NewLow");  });

        // 저장 Event Bind
//         $("#btnSave").click(function(){ doAction("Save");  });

        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
        	if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
	        	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); //선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?
        	}
        }); //doAction("Delete");  });
        
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
        
        $("#subjSearchPop").click(function(event){
		    	
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    	var url = "<c:url value='/meta/subjarea/pop/subjSearchPop.do' />";
		    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
				var popwin = OpenModal(url+"?"+param, "searchpop",  600, 400, "no");
				popwin.focus();
			
		});
        
//         //주제영역 검색팝업 이벤트
//         $("#subjSearchPop").click(function(){
// //         	alert("dddddd");
// //         	$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
// //         	$('div#popSearch iframe').attr('src', "<c:url value='/meta/model/pop/subjSearchPop.do' />");
// //          	$('div#popSearch').dialog("open");
// 			var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
// 			openSearchPop("<c:url value='/meta/subjarea/pop/subjSearchPop.do' />", param);
//         });

        /* //검색제거 버튼...
        $(".btnDelPop").click(function(){
        	//이전값 제거... 텍스트 박스가 아닌 경우는 어떻하지???
        	$(this).prev().val('');
        }); */
        
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        create_selectbox(sysareaJson, $("#sysAreaId"));
      
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	var tmpstr = $("#pdmTblLnm").val();
	if(!isBlankStr(tmpstr)) {
		doAction("Search");
	}
});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.PDMTBL.POP.1'/>"; // No.|상태|선택
// 	        headtext += "|요청구분|검토상태코드|검토내용|검증결과|검증비고|";
// 			headtext += +"|요청번호|요청일련번호|";
			headtext += +"<s:message code='META.HEADER.PDMTBL.POP.2'/>"; //|테이블ID|테이블(물리명)|테이블(논리명)|설명
			headtext += +"<s:message code='META.HEADER.PDMTBL.POP.3'/>";  //|모델논리명|상위주제영역논리명|주제영역ID|주제영역논리명
// 			headtext += +"|표준적용여부|파티션테이블여부|DW대상테이블여부|객체설명";
// 	        headtext += +"|담당사용자ID|담당사용자명|객체설명|객체버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID";
// 	        headtext += +"|보관주기|삭제기준|삭제방법|백업주기";
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        /*
요청번호|요청일련번호|물리모델테이블ID|물리모델테이블물리명|물리모델테이블논리명|이전물리모델테이블물리명|요청구분코드|검토상태코드|검토내용|검증코드|검증비고|모델논리명|상위주제영역논리명|주제영역ID|주제영역논리명|표준적용여부|파티션테이블여부|DW대상테이블여부|담당사용자ID|담당사용자명|객체설명|객체버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID|보관주기|삭제기준|삭제방법|백업주기
        */
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status", Width:40,  SaveName:"ibsStatus",     Align:"Center", Edit:0},
                    {Type:"CheckBox",Width:50, SaveName:"ibsCheck",      Align:"Center", Edit:1, Sort:0},
//                     {Type:"Combo",  Width:80,  SaveName:"rqstDcd", 	     Align:"Center", Edit:1, Hidden:1},
//                     {Type:"Combo",  Width:60,  SaveName:"rvwStsCd",   	 Align:"Center", Edit:1, Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"rvwConts",   	 Align:"Center", Edit:1, Hidden:1},
//                     {Type:"Combo",  Width:120, SaveName:"vrfCd",         Align:"Left", 	 Edit:0, Hidden:1},
//                     {Type:"Text",   Width:40,  SaveName:"vrfRmk"	,    Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Text",   Width:80,  SaveName:"rqstNo",        Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Text",   Width:100, SaveName:"rqstSno",    	 Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:150, SaveName:"pdmTblId",   	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:150, SaveName:"pdmTblPnm",     Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:150,  SaveName:"pdmTblLnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
//                     {Type:"Text",   Width:60,  SaveName:"bfPdmTblPnm",   Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"mdlLnm"	,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"uppSubjLnm",    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"subjId"	,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"subjLnm"	,    Align:"Left",   Edit:0},
//                     {Type:"Combo",  Width:80,  SaveName:"stdAplYn"	,    Align:"Left",   Edit:0},
//                     {Type:"Combo",  Width:40,  SaveName:"partTblYn"	,    Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Combo",  Width:40,  SaveName:"dwTrgTblYn",    Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Text",   Width:40,  SaveName:"crgUserId"	,    Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Text",   Width:100,  SaveName:"crgUserNm"	,    Align:"Left",   Edit:0, Hidden:0},
//                     {Type:"Text",   Width:40,  SaveName:"objVers"   ,    Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Combo",  Width:40,  SaveName:"regTypCd",      Align:"Center", Edit:0, Hidden:1},                        
//                     {Type:"Text",   Width:60,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"frsRqstUserId", Align:"Center", Edit:0, Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"rqstDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"aprvDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"aprvUserId",    Align:"Center", Edit:0, Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"ctdFcy"	,    Align:"Center", Edit:0, Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"delCri"	,    Align:"Center", Edit:0, Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"delMtd"	,    Align:"Center", Edit:0, Hidden:1},
//                     {Type:"Text",   Width:60,  SaveName:"bckFcy"	,    Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
// 	     SetColProperty("rqstDcd", 		{ComboCode:"C|D", 	ComboText:"신규/변경|삭제"});
// 	     SetColProperty("rvwStsCd", 	{ComboCode:"0|1|2", 	ComboText:"검토전|승인|반려"});
// 	     SetColProperty("vrfCd", 		{ComboCode:"1|2|3|4", 	ComboText:"등록가능|검증오류|확인|검증전"});
// 	     SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	     SetColProperty("partTblYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	     SetColProperty("dwTrgTblYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
// 	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        
//         InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        SetColHidden("ibsCheck",1);
        SetColHidden("subjId",1);
        
        
//         FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}


//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "New":        //신규 추가...
//         	var crow = grid_sheet.GetSelectRow();
//         	var clevel = grid_sheet.GetRowLevel(crow);
        	
        	//마지막 행에 추가한다...
        	var row = grid_sheet.DataInsert(-1);
        	tblClick(row);
			
            break;

        case "NewChg":        //변경대상 추가...
        	//테이블 검색 팝업을 오픈한다...
        	

            break;
            
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(grid_sheet);
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	//체크된 행을 Json 리스트로 생성...
//         	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	ibsSaveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(ibsSaveJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
        	
        	var param = "sAction=" + sAction;
        	var url = "<c:url value="/meta/model/delpdmrqstlist.do"/>";
//         	$.postJSON(url, DelJson, ibscallback);
            IBSpostJson(url, param, ibscallback);
        	break;
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = grid_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/model/regpdmrqstlist.do"/>";
        	var param = "&sAction=" + sAction;
        	break;
        
        case "SaveRow":
        	//현재 선택행을 확인 후 1행 이상이면 저장 실행...
			var crow = grid_sheet.GetSelectRow();
			if(crow < 1) return;
//         	var nrow = grid_sheet.DataInsert();
			
			//폼 입력항목 검증... (이건 나중에...)
			
			//폼 내용을 시트에 셋팅...
			var istatus = grid_sheet.GetCellValue(crow, "ibsStatus");
        	form2ibsmapping(crow, $("form#frmInput"), grid_sheet);
        	if(istatus != "I") {grid_sheet.SetCellValue(crow, "ibsStatus", "U");}
			
			//현재 선택행 Json 생성...
//         	var temp = grid_sheet.GetRowJson(crow);
//         	var tmparr = new Array();
//         	tmparr.push(temp);
        	
        	ibsSaveJson = getrowjsonIBS(crow, grid_sheet);
        	
        	var url = "<c:url value="/meta/model/regpdmrqstlist.do"/>";
        	var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallback);
        	
        	break;
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/model/getpdmtbllist.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "SearchRow": //단일 조회...
        	//선택 행 조회
        	var crow = grid_sheet.GetSelectRow();
        	if(crow < 1) return false;
        	
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoRowSearch(crow, "<c:url value="/meta/model/getpdmrqstinfo.do" />",  param ,0);
        break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }       
}
 
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function ibscallback(res){
    var result = res.RESULT.CODE;
    if(result == 0) {
		//공통메세지 팝업 : 성공 메세지...
    	showMsgBox("INF", res.RESULT.MESSAGE);
    
    	postProcessIBS(res);
    	
    } else {
		//공통메시지 팝업 : 실패 메세지...
    	shotMsgBox("ERR", res.RESULT.MESSAGE);
    }
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.ETC.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");    		
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
	    	
			//저장완료시 요청서 번호 셋팅...
	    	if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("SearchRow");    		
	    	}
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 요청서 번호 셋팅...
	    	if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	}
			
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
	
	var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
 
	$("#saveCls").val("U");  //저장구분을 수정 (U) 로 변경 
	
	var usrId = grid_sheet.GetCellValue(row, "arrUsrId");
	
	$("#usrId").val(usrId);  
	   
    //$("#frmInput").attr("action", url).submit();
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    
    if(row < 1) return;
    
    tblClick(row);
    
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
        alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
        alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
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
		
		$('#btnfrmReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
	
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PHYC.MDEL.INQ" /></div> <!-- 물리모델 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<div id="search_div">
<!-- 검색조건 입력폼 -->
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" name="pdmTblId" />
        	<input type="hidden" id="rqstNo" name="rqstNo" />
        	<input type="hidden" id="rqstSno" name="rqstSno" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic">
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
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                 </select>
                            </td>
                           <th scope="row"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM.KRN.ENSN" /></label></th><!-- 주제영역명(한글/영문) -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" />
<!--                                 <input type="hidden" name="subjId" id="subjId" /> -->
                                		<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
		                                <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                            </td>
                       </tr>
                       <tr>                               
                           <th scope="row" ><label for="pdmTblLnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                            <td colspan="3">
                                <span class="input_file">
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" value="${search.pdmTblLnm}"/>
                                </span>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
        
         <!-- 조회버튼영역  -->
         <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="tbl_sle_title"><s:message code="MSG.TBL.CHC" /></div> <!-- 테이블을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="TBL.DFNT.P" /></a></li><!-- 테이블 정의서 -->
	    <li><a href="#tabs-2"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼 목록 -->
	  </ul>
	  <div id="tabs-1">
		<!-- 입력폼 시작 -->
	         <form name="frmInput" id="frmInput" method="post" >
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="input_form_div">
	            <fieldset>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="SUBJ.TRRT.INFO" /></div> <!-- 주제영역 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_basic">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row"><label for="subjId"><s:message code="SUBJ.TRRT.ID" /></label></th> <!-- 주제영역ID -->
	                                <td colspan="3"><span class="input_inactive"><input type="text" id="subjId" name="subjId"/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class="th_require"><label for="mdlLnm"><s:message code="MDEL.LGC.NM"/></label></th> <!-- 모델논리명 -->
	                                <td colspan="3"><input type="text" id="mdlLnm" name="mdlLnm" /></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class="th_require"><label for="uppSubjLnm"><s:message code="UPLOAD.SUBJ.TRRT.LGC.NM"/></label></th><!-- 상위주제영역논리명 -->
	                                <td colspan="3"><input type="text" id="uppSubjLnm" name="uppSubjLnm" /></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class="th_require"><label for="subjLnm"><s:message code="SUBJ.TRRT.LGC.NM" /></label></th><!-- 주제영역 논리명 -->
	                                <td colspan="3">
	                                	<div>
		                                	<input type="text" id="subjLnm" name="subjLnm" />
<!-- 		                                	<button class="btnDelPop" >삭제</button> -->
<!-- 		                                	<button class="btnSearchPop" id="subjSearchPop">검색</button> -->
	                                	</div>
	                                </td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_basic">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row"><label for="pdmTblId"><s:message code="TBL.ID" /></label></th> <!-- 테이블ID -->
	                                <td colspan="3"><span class="input_inactive"><input type="text" id="pdmTblId" name="pdmTblId"/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class="th_require"><label for="pdmTblPnm"><s:message code="TBL.NM.PHYC.NM" /></label></th> <!-- 테이블명(물리명) -->
	                                <td colspan="3"><input type="text" id="pdmTblPnm" name="pdmTblPnm" title="<s:message code='MSG.TBL.NM.INPT' />" /></td> <!-- 테이블명은 반드시 입력해야 합니다. -->
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM.LGC.NM" /></label></th> <!-- 테이블명(논리명) -->
	                                <td colspan="3"><input type="text" id="pdmTblLnm" name="pdmTblLnm" /></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="bfPdmTblPnm"><s:message code="BFR.TBL.NM"/></label></th> <!-- 이전 테이블명 -->
	                                <td colspan="3"><input type="text" id="bfPdmTblPnm" name="bfPdmTblPnm" /></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""></textarea></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class="th_require"><label for="stdAplYn"><s:message code="STRD.APL.YN" /></label></th> <!-- 표준적용 여부 -->
	                                <td colspan="3"><span class="input_check">
	                                    <input type="checkbox" id="stdAplYn" name="stdAplYn" value="Y"/><s:message code="STRD.APL" /></span> <!-- 표준 적용 -->
	                                </td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                </fieldset>
		</div>
	            </form>
		<!-- 입력폼 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>
		<!-- 입력폼 버튼... -->
<!-- 		<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 			<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 			<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 		</div> -->
	  </div>
	  
		<!-- 컬럼 목록 탭 -->
	  <div id="tabs-2">
		<%@include file="./../pdmcol_lst.jsp" %>
	  </div>
	</div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<!-- 	<div class="sub_tab_area">
			<div class="stab">
	        	<div class="stab_108_over">탭제목1</div>
	            <div class="stab_108"><a href="#">탭제목2</a></div>
	        </div>
	</div> -->

<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>


</body>
</html>