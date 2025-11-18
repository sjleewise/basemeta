<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<c:set var="pdmrelyn"><s:message code="wiseda.pdm.rel" /></c:set>

<html>
<head>
<title><s:message code="TBL.INQ"/></title> <!-- 테이블 조회 -->

<script type="text/javascript">

EnterkeyProcess("Search");
var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...
var regtypcd = ${codeMap.regtypcd} ;
var nopenrsn = ${codeMap.nopenrsn} ;
                       
$(document).ready(function() {
	// 범정부 사용 여부 세션 값
	var govYn = "<%=session.getAttribute("govYn")%>";

	// 범정부 사용 여부 N일때 숨기기
	if ("${govYn}" == "N") {
		$("#gov1").hide(); // 범정부 중앙에타 연계항목
		$("#gov2").hide(); // 범정부 중앙에타 연계항목 테이블
	}

	// 범정부 사용 여부 Y일때 보이기
	if ("${govYn}" == "Y") {
		$("#gov1").show();
		$("#gov2").show();
	}
	
		//$( "#tabs" ).tabs();
		
		// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

        $('#subjSearchPop').click(function(event){
		    	
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
		    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
				var popwin = OpenModal(url+"?"+param, "searchpop",  1200, 600, "no");
				popwin.focus();
			
		});
        
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        create_selectbox(sysareaJson, $("#sysAreaId"));
        create_selectbox(regtypcd, $("#regTypCd"));
        create_selectbox(nopenrsn, $("#priRsn"));
      
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #subjLnm"), "SUBJ");
        
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #pdmTblLnm"), "PDMTBL");
        
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #pdmColLnm"), "PDMCOL");
        
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
/* 	var tmpstr = $("#pdmTblLnm").val();
	if(tmpstr != null && tmpstr != "" && tmpstr != "undefined") {
		doAction("Search");
	} */
	
	//주제영역 조회 페이지에서 더블클릭으로 subjLnm값을 받아오는 경우 자동으로 검색한다.
	var tmpSubjLnm = "${search.subjLnm}";
	if(!isBlankStr(tmpSubjLnm)) {
		$("#frmSearch #subjLnm").val(tmpSubjLnm);
		doAction("Search");
	}
	
	//PK값으로 검색
	if(!isBlankStr("${search.pdmTblId}")) {
		var param ="pdmTblId="+"${search.pdmTblId}";
		grid_sheet.DoSearch("<c:url value="/meta/model/getpdmtbllist.do" />", param);
	}
	
	
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("Search");
	}
	
	//$( "#layer_div" ).show();
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
        
        var headtext = "<s:message code='META.HEADER.PDMTBLCOL.LST'/>";

        // headtext = "No.|주제영역ID|주제영역명|컬럼ID|물리모델테이블ID|물리모델테이블|물리모델테이블물리명|표준용어ID|표준용어명|표준용어물리명|도메인ID|도메인명|도메인물리명|인포타입ID|인포타입명|컬럼명|컬럼한글명|컬럼순서|등록유형|데이터타입|PK여부|PK순서|NOTNULL여부|DEFAULT값|암호화여부|최초요청자ID|최초요청자|최초요청일시|요청자ID|요청자|요청일시|승인자ID|승인자|승인일시|버전|설명


        if ("${govYn}" == "Y") {
			headtext += "<s:message code='META.HEADER.PDMCOL.RQST.8'/>"; //범정부 중앙메타 연계항목
			//headtext += "|공개/비공개여부|비공개사유|개인정보여부|제약조건";
	    }
        
        var headers= [{Text:headtext, Align:"Center"}];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",     Width:50,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"subjId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"fullPath"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"pdmColId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"pdmTblId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:180,  SaveName:"pdmTblLnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"pdmTblPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"sditmId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"sditmLnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"sditmPnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"dmnId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"dmnLnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"dmnPnm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"infotpId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"infotpLnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"pdmColPnm"     ,Align:"Left",   Edit:0},
                    {Type:"Text",    Width:150,  SaveName:"pdmColLnm"     ,Align:"Left",   Edit:0},
                    {Type:"Int",     Width:50,  SaveName:"colOrd"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:50,  SaveName:"regTypCd"     ,Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"dataType"     ,Align:"Center",   Edit:0},
//                     {Type:"Text",    Width:50,  SaveName:"dataLen"     ,Align:"Center",   Edit:0},
//                     {Type:"Text",    Width:50,  SaveName:"dataScal"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:50,  SaveName:"pkYn"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:50,  SaveName:"pkOrd"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:50,  SaveName:"fkYn"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:50,  SaveName:"akYn"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:100,  SaveName:"nonulYn"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"defltVal"     ,Align:"Center",   Edit:0},
                    {Type:"Combo",    Width:80,  SaveName:"encYn"     ,Align:"Center",   Edit:0},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstUserId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"frsRqstUserNm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"frsRqstDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"rqstUserNm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"rqstDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserId"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"aprvUserNm"     ,Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",    Width:120,  SaveName:"aprvDtm"     ,Align:"Center",   Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",    Width:50,  SaveName:"objVers"     ,Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",    Width:200,  SaveName:"objDescn"     ,Align:"Left",   Edit:0}
                ]; 
		
        if ("${govYn}" == "Y") {
        	cols.push(
        			//범정부연계항목
					{Type:"Combo",    Width:100, SaveName:"openYn"    ,Align:"Center",   Edit:0},
					{Type:"Combo",    Width:80,  SaveName:"priRsn"    ,Align:"Center",   Edit:0},
					{Type:"Combo",    Width:80,  SaveName:"prsnInfoYn" ,Align:"Center",   Edit:0},
					{Type:"Text",     Width:80,  SaveName:"constCnd" ,Align:"Center",   Edit:0}
				);
        }
        
        InitColumns(cols);

        SetColProperty("regTypCd", 	{ComboCode:"C|U|D", 	ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        SetColProperty("pkYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"});
        SetColProperty("fkYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"});
        SetColProperty("akYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"});
        SetColProperty("nonulYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"});
        SetColProperty("encYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
        if ("${govYn}" == "Y") {
	      	//범정부 연계 항목
			SetColProperty("openYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
			SetColProperty("priRsn", 	${codeMap.nopenRsnibs});
			SetColProperty("prsnInfoYn",{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        }
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("dmnLnm",1);
        SetColHidden("infotpLnm",1);
        
//         FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}


//그리드 초기화 한다.
var chkinitdtlgrids = false;
function initDtlGrids(){
	if (!chkinitdtlgrids) {
		
		//initColGrid();
		//initTblHlGrid();
		initColHlGrid();
		
		//<c:if test="${ pdmrelyn == 'Y'}">
		//initRelGrid();
		//initRelHistGrid();
		//</c:if>
		
		
	 	chkinitdtlgrids = true;	
	}
	
}  

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	$("#frmSearch #subjLnm").val(retjson.fullPath); //20250728 전체경로 변경(4-2)
// 	$("#frmSearch #fullPath").val(retjson.fullPath);
	
}


//컬럼 클릭시 처리하는 부분...
function colClick(row) {
	initDtlGrids();

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//IBSheect 내용을 Form에 저장...(row, $formobj, grid)
	
    ibs2formmapping(row, $("form#frmInput2"), grid_sheet);
// 	IE에서 chkformsetup script 안먹힘
	if(grid_sheet.GetCellValue(row, "stdAplYn") == "Y") $("#frmInput2 #stdAplYn").prop('checked', true);
	
    $("form[name=frmInput2] #frsRqstDtm").val(grid_sheet.GetCellText(1, "frsRqstDtm"));
    $("form[name=frmInput2] #rqstDtm").val(grid_sheet.GetCellText(1, "rqstDtm"));
    $("form[name=frmInput2] #aprvDtm").val(grid_sheet.GetCellText(1, "aprvDtm"));
    

    $("form#frmSearch input[name=pdmTblId]").val(grid_sheet.GetCellValue(row, "pdmTblId"));
    $("form[name=frmInput2 ] #encYn").val(grid_sheet.GetCellValue(row, "encYn"));
    
    //선택행 셋팅..
    var tmptit = "<s:message code='CHC.TBL.NM'/> : " + param.pdmTblPnm + "["+ param.pdmTblLnm + "]"; //선택 테이블명
    $("#tbl_sle_title").html(tmptit);
    
    //컬럼리스트 조회...
    //doActionCol("Search");
    //doActionTblH("Search");
     doActionColH("Search");

    <c:if test="${ pdmrelyn == 'Y'}">
     var param1 = "paEntyId=" + param.pdmTblId;
     //rel_sheet.DoSearch('<c:url value="/meta/model/getpdmrellist.do" />', param1);
     //relhist_sheet.DoSearch('<c:url value="/meta/model/getpdmrelhistlist.do" />', param1);
    </c:if>
	
    //시트에서 포커스 제거
	//grid_sheet.SetBlur();
	//하단 폼을 보여주고 포커스를 이동한다...
	//모델 논리명으로 이동...
// 	$("#tabs").show();
	//$("#pdmTblPnm").focus().click();
	
// 	alert(typeof "${result.frsRqstDtm}");

}

//주제영역 검색 팝업에서 선택한 내용을 json으로 반환 받는다...
function getSubjPop(subjjson) {
	
	$("form#frmSearch input[name=subjLnm]").val(subjjson.subjLnm);
	$("form#frmSearch input[name=subjId]").val(subjjson.subjId);
	
}


//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
        	$('#frmSearch input[name=pdmTblId]').val('');
        	$('#frmSearch input[name=subjId]').val('');
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/model/getpdmcollist.do" />", param);
        	
        	break;
        	
        case "SearchRow": //단일 조회...
        	//선택 행 조회
        	var crow = grid_sheet.GetSelectRow();
        	if(crow < 1) return false;
        	
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoRowSearch(crow, "<c:url value="/meta/model/getpdmrqstinfo.do" />",  param ,0);
        break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'데이터모델컬럼조회'});
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
function grid_sheet_OnDblClick(row, col, value, CellX, CellY, CellW, CellH) {
//     alert("tbl dbl click");
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
// 	alert("tbl click event");
    
    $("#hdnRow").val(row);
    
  //선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
    
    if(row < 1) return;
    
    colClick(row);

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
		
		if(!isBlankStr("${search.pdmTblId}")) {
			colClick(1);
		}
		
		
			
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
	
}






</script>
</head>

<body>

<div id="layer_div" > 
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
        	<input type="hidden" name="subjId" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
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
                           <th scope="row" class="" style="display: none;"><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 -->
                            <td style="display: none;">
                                <select id="sysAreaId" class="" name="sysAreaId">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                 </select>
                            </td>
                           <th scope="row"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
<!--                                 <input type="hidden" name="subjLnm" id="subjLnm" /> -->
                                <input type="text" name="subjLnm" id="subjLnm" class="wd340"/>
                                		<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
		                                <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                            </td>
                           <th scope="row"><label for="stdAplYn"><s:message code="STRD.APL.YN" /></label></th> <!-- 표준적용여부 -->
                            <td>
                                <select id="stdAplYn" class="" name="stdAplYn">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO" /></option> <!-- 아니오 -->
                                 </select>
                            </td>
                       </tr>
                       <tr>                               
                           <th scope="row" ><label for="pdmTblLnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" class="wd200" value="${search.pdmTblLnm}"/>
                                </span>
                            </td>
                           <th scope="row" ><label for="pdmColLnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmColLnm" id="pdmColLnm" class="wd200" value="${search.pdmColLnm}"/>
                                </span>
                            </td>
                       </tr>
<!--                         <tr>
                       		<th scope="row"><label for="encYn">암호화 정보 포함</label></th>
                            <td colspan="3">
                                <select id="encYn" class="" name="encYn">
                                        <option value="">전체</option> 
                                        <option value="Y">예</option> 
                                 </select>
                            </td>
                       </tr>-->
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
<!-- 조회버튼영역  -->
<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="tbl_sle_title"><s:message code="CLMN.CHC.1"/></div> <!-- 컬럼을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-4" ><a href="#tabs-4"><s:message code="COL.INFO" /></a></li> <!-- 컬럼정보 -->
	    <li id="tab-5" ><a href="#tabs-5"><s:message code="CLMN.HSTR" /></a></li> <!-- 컬럼이력 -->
<!-- 	    <li id="tab-6" ><a href="#tabs-6">종속성</a></li> -->
<!-- 	    <li id="tab-7" ><a href="#tabs-7">Where Used</a></li> -->
	  </ul>
	  <div id="tabs-4">
	  <!-- 컬럼 정보 탭 -->
		<%@include file="pdmcolinfo_dtl.jsp" %>
	  </div>
	  <div id="tabs-5">
		<!-- 컬럼 이력 탭 -->
		<%@include file="pdmtblcolhist_lst.jsp" %>
	  </div>  
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

</body>
</html>