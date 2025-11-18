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

                       
$(document).ready(function() {
	
		//$( "#tabs" ).tabs();
		 $("#frmInput #stdAplYn").prop('disabled', true);
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
				var popwin = OpenModal(url+"?"+param, "searchpop",  600, 400, "no");
				popwin.focus();
			
		});
        
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        create_selectbox(sysareaJson, $("#sysAreaId"));
        create_selectbox(regtypcd, $("#regTypCd"));
      
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #subjLnm"), "SUBJ");
        
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #ldmEntyLnm"), "PDMTBL");
        
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #ldmAttrLnm"), "PDMCOL");
        
        
        
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
/* 	var tmpstr = $("#ldmEntyLnm").val();
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
	if(!isBlankStr("${search.ldmEntyId}")) {
		var param ="ldmEntyId="+"${search.ldmEntyId}";
		grid_sheet.DoSearch("<c:url value="/meta/ldm/getpdmtbllist.do" />", param);
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
        
        var headtext = "<s:message code='META.HEADER.PDMTBL.LST'/>";

//         headtext = "No.|상태|선택|시스템ID|모델명|상위주제영역명|주제영역ID|주제영역논리명|주제영역|엔티티ID|엔티티명|표준적용여부|담당자ID|담당자|설명|객체버전|등록유형코드|최초요청일시|최초사용자|요청일시|요청자|승인일시|승인자";
        //headtext = "Num|State|Choice|System area|Logical name|Upper SubjectArea name|SubjectAreaID|SubjectArea logical name|SubjectArea|table ID|Physical name|Logical name|Before the table (physical name)|Standard|encrypt|Partition|DW target table|ManagerID|Manager|Description|Version of the object|Registration type code|First request date|First time user|Request date|Requester|Approval date|Approver|Retention period|Deletion criteria|Deletion method|Backup Cycles";
        
        var headers = [
	                    {Text:headtext, Align:"Center"}
	                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,  SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,  SaveName:"ibsCheck",      Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Combo",    Width:80,  SaveName:"sysAreaId", 	     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:40,  SaveName:"mdlLnm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:40,  SaveName:"uppSubjLnm",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:40,  SaveName:"subjId"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100, SaveName:"subLnm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:350, SaveName:"fullPath"	,    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150, SaveName:"ldmEntyId",   	 Align:"Left",   Edit:0, Hidden:1},                    
                    {Type:"Text",     Width:250, SaveName:"ldmEntyLnm", 	 Align:"Left",   Edit:0},                    
                    {Type:"Combo",    Width:110, SaveName:"stdAplYn"	,    Align:"Center",   Edit:0},                    
                    {Type:"Text",     Width:40,  SaveName:"crgUserId"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100, SaveName:"crgUserNm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100, SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:40,  SaveName:"objVers"   ,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:40,  SaveName:"regTypCd",      Align:"Center", Edit:0, Hidden:1},                        
                    {Type:"Date",     Width:100, SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"frsRqstUserNm", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",     Width:100, SaveName:"rqstDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"rqstUserNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",     Width:100, SaveName:"aprvDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",     Width:60,  SaveName:"aprvUserNm",    Align:"Center", Edit:0, Hidden:1} 
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
	     SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("encYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("partTblYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("dwTrgTblYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        
        InitComboNoMatchText(1, "");
        
//         SetColHidden("ibsStatus",1);
//         SetColHidden("arrUsrId",1);
        
        	    SetSheetHeight("250");
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
		
		initColGrid();
		initTblHlGrid();
		initColHlGrid();
		
		<c:if test="${ pdmrelyn == 'Y'}">
		initRelGrid();
		initRelHistGrid();
		</c:if>
		
		
	 	chkinitdtlgrids = true;	
	}
	
}  

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
 	$("#frmSearch #subjLnm").val(retjson.fullPath);
	
}


//테이블 클릭시 처리하는 부분...
function tblClick(row) {
	initDtlGrids();

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	//IBSheect 내용을 Form에 저장...(row, $formobj, grid)
	
    ibs2formmapping(row, $("form#frmInput"), grid_sheet);

    $("form[name=frmInput] #frsRqstDtm").val(grid_sheet.GetCellText(1, "frsRqstDtm"));
    $("form[name=frmInput] #rqstDtm").val(grid_sheet.GetCellText(1, "rqstDtm"));
    $("form[name=frmInput] #aprvDtm").val(grid_sheet.GetCellText(1, "aprvDtm"));
    

    $("form#frmSearch input[name=ldmEntyId]").val(grid_sheet.GetCellValue(row, "ldmEntyId"));
	    
    //선택행 셋팅..
    var tmptit = "<s:message code='ENTY.NM'/>" + " : " +  param.ldmEntyLnm; //선택 테이블명
    $("#tbl_sle_title").html(tmptit);
    
    //컬럼리스트 조회...
    doActionCol("Search");
    
    doActionTblH("Search");
    
    doActionColH("Search");

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
        	
        	var param = $('#frmSearch').serialize();
        	        	
        	grid_sheet.DoSearch("<c:url value="/meta/ldm/getLdmEntyList.do" />", param);
        	
        	break;
        	       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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
		
		if(!isBlankStr("${search.ldmEntyId}")) {
			tblClick(1);
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
        	<input type="hidden" name="ldmEntyId" />
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
                                <select id="stdAplYn" class="" name="stdAplYn" disabled="disabled">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO" /></option> <!-- 아니오 -->
                                 </select>
                            </td>
                       </tr>
                       <tr>                               
                           <th scope="row" ><label for="ldmEntyLnm"><s:message code="ENTY.NM" /></div> <!-- 엔티티명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="ldmEntyLnm" id="ldmEntyLnm" class="wd200" value="${search.ldmEntyLnm}"/>
                                </span>
                            </td>
                           <th scope="row" ><label for="ldmAttrLnm"><s:message code="ATTR.NM" /></label></th> <!-- 어트리뷰트명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="ldmAttrLnm" id="ldmAttrLnm" class="wd200" value="${search.ldmAttrLnm}"/>
                                </span>
                            </td>
                       </tr>
                       
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM2' /></div>
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
		    <div class="selected_title" id="tbl_sle_title"><s:message code="MSG.TBL.CHC" /></div> <!-- 테이블을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-1" ><a href="#tabs-1"><s:message code="ENTY.DFNT.P" /></a></li><!-- 엔티티 정의서 -->
	    <li id="tab-2" ><a href="#tabs-2"><s:message code="ATTR.LST" /></a></li>   <!-- 어트리뷰트목록 -->
	    <li id="tab-4" ><a href="#tabs-4"><s:message code="ATTR.INFO" /></a></li>  <!-- 어트리뷰트정보 -->
	    <li id="tab-6" ><a href="#tabs-6"><s:message code="ENTY.HSTR" /></a></li>  <!-- 엔티티 이력 -->
	    <li id="tab-5" ><a href="#tabs-5"><s:message code="ATTR.HSTR" /></a></li>  <!-- 어트리뷰트이력 -->	    
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
	                <div class="tb_read">
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
	                                <th scope="row" ><label for="fullPath"><s:message code="SUBJ.TRRT" /></label></th> <!-- 주제영역 -->
	                                <td colspan="3"><input type="text" id="fullPath" name="fullPath" class="wd99p" readonly/></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
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
	                                <th scope="row"><label for="ldmEntyLnm"><s:message code="TBL.NM.LGC.NM" /></label></th> <!-- 테이블명(논리명) -->
	                                <td colspan="3"><span class="" ><input type="text" id="ldmEntyLnm" name="ldmEntyLnm" class="wd98p" readonly/></span></td>
	                            </tr>
                 
	                            <tr>
	                                <th scope="row"><label for="stdAplYn"><s:message code="STRD.APL.YN" /></label></th> <!-- 표준적용여부 -->
	                                <td >
		                                <input type="checkbox" id="stdAplYn" name="stdAplYn"     value="Y" /><span class="input_check"><s:message code="STRD.APL.YN" /></span> <!-- 표준적용여부 -->			                           
	                                </td>
	                                	<th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                                    <td>
                                        <select id="regTypCd" class="" name="regTypCd"  disabled="disabled">
									     <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								        </select>
								    </td>
	                            </tr>
	                        
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""  class="b0" style="height:50px;width:99%;" readonly></textarea></td>
	                            </tr>
	                            
	                        </tbody>
	                    </table>
	                </div>
	                 <div style="clear:both; height:10px;"><span></span></div>
					 <div class="stit"><s:message code="DEMD.DTL.INFO" /></div> <!-- 요청상세정보 -->
					 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <s:message code="CLMN.NM1" /> <!-- 컬럼 이름 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" class=""><label for="frsRqstDtm"><s:message code="FRST.DEMD.DTTM" /></label></th> <!-- 최초요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstDtm" name="frsRqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="frsRqstUserNm"><s:message code="FRST.DMNT" /></label></th><!-- 최초요청자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="rqstUserNm"><s:message code="DMNT" /></label></th> <!-- 요청자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="aprvUserNm"><s:message code="APRR" /></label></th> <!-- 승인자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvUserNm" name="aprvUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
<%-- 					<%@include file="../stnd/otherinfo.jsp" %> --%>
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
	  
	  <div id="tabs-2">
	  <div id="test">
	  	<!-- 컬럼 목록 탭 -->
		<%@include file="ldmattr_lst.jsp" %>
	  </div>
		
	  </div>
	  
	  <div id="tabs-4">
	  <!-- 컬럼 정보 탭 -->
		<%@include file="ldmattrinfo_dtl.jsp" %> 
	  </div>
	  <div id="tabs-6">
		<!-- 테이블 이력 탭 -->
		<%@include file="ldmentyhist_dtl.jsp" %>
	  </div>	  

	  <div id="tabs-5">
		<!-- 컬럼 이력 탭 -->
		<%@include file="ldmattrhist_dtl.jsp" %>
	  </div>
	
	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

</body>
</html>