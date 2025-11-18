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
<title><s:message code="PHYC.MDEL.CHG.HSTR.INQ" /></title> <!-- 물리모델 변경이력 조회 -->

<script type="text/javascript">

var interval = "";

EnterkeyProcess("Search");
var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...
var regtypcd = ${codeMap.regtypcd} ;	//시스템영역 코드리스트 JSON...

$(document).ready(function() {
		//$( "#tabs" ).tabs();
	    $("#tab-5").hide();
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
        
		//$("#partTblYn").hide();
		//$("#dwTrgTblYn").hide();
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

		// 시스템영역
    	create_selectbox(regtypcd, $("#regTypCd"));
    	create_selectbox(regtypcd, $("#frmSearch #regTypCd"));
    	//create_selectbox(sysareaJson, $("#sysAreaId"));
    	
    	$("#frmInput").addClass("tb_read");
    	
    	$("#frmInput2").addClass("tb_read");
    	

});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
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
        
        var headtext = "<s:message code='META.HEADER.PDMTBLHIST.LST'/>";

        /*No.|상태|선택|"
            +"시스템영역|요청번호|요청일련번호|"
            +"모델논리명|상위주제영역논리명|주제영역ID|주제영역논리명|등록유형|버전|주제영역|"
            +"테이블ID|테이블(물리명)|테이블(논리명)|이전테이블(물리명)|"
            +"표준적용여부|파티션테이블여부|DW대상테이블여부|시작일시|만료일시|"
            +"담당사용자ID|담당사용자명|객체설명|최초요청일시|최초요청사용자ID|최초사용사명|요청일시|요청사용자ID|요청사용자명|승인일시|승인사용자ID|승인사용자명"
            +"보관주기|삭제기준|삭제방법|백업주기*/
        
//                     {Text:"No.|상태|선택|요청번호|요청일련번호|물리모델테이블ID|물리모델테이블물리명|물리모델테이블논리명|이전물리모델테이블물리명|요청구분코드|검토상태코드|검토내용|검증코드|검증비고|모델논리명|상위주제영역논리명|주제영역ID|주제영역논리명|표준적용여부|파티션테이블여부|DW대상테이블여부|담당사용자ID|담당사용자명|객체설명|객체버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID|보관주기|삭제기준|삭제방법|백업주기", Align:"Center"}
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
                    {Type:"Status", Width:40,  SaveName:"ibsStatus",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox",Width:50, SaveName:"ibsCheck",      Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Combo",  Width:60,  SaveName:"sysAreaId", 	     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"rqstNo",        Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:100, SaveName:"rqstSno",    	 Align:"Left",   Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"mdlLnm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"uppSubjLnm",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"subjId"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"subLnm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:50,  SaveName:"regTypCd",      Align:"Center", Edit:0},                        
                    {Type:"Text",   Width:50,  SaveName:"objVers"   ,    Align:"Center",   Edit:0},
                    {Type:"Text",   Width:130,  SaveName:"fullPath"	,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:150, SaveName:"pdmTblId",   	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:130, SaveName:"pdmTblPnm",     Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"pdmTblLnm", 	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:130,  SaveName:"bfPdmTblPnm",   Align:"Left",   Edit:0, Hidden:1},

                    {Type:"Combo",  Width:70,  SaveName:"stdAplYn"	,    Align:"Center",   Edit:0},
                    {Type:"Combo",  Width:40,  SaveName:"partTblYn"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:40,  SaveName:"dwTrgTblYn",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",   Width:80,  SaveName:"strDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:0},
                    {Type:"Date",   Width:80,  SaveName:"expDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:0},
                    {Type:"Text",   Width:40,  SaveName:"crgUserId"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"crgUserNm"	,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:230,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",   Width:60,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"frsRqstUserId", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"frsRqstUserNm", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",   Width:60,  SaveName:"rqstDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"rqstUserNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",   Width:60,  SaveName:"aprvDtm"   ,    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"aprvUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"aprvUserNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"ctdFcy"	,    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"delCri"	,    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"delMtd"	,    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"bckFcy"	,    Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
	     SetColProperty("rqstDcd", 		${codeMap.rqstDcdibs});
	     SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
	     SetColProperty("vrfCd", 		${codeMap.vrfCdibs});
	     SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("partTblYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("dwTrgTblYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
        
        InitComboNoMatchText(1, "");
	    SetSheetHeight("250");
        FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmSearch #subjLnm").val(retjson.subjLnm);
	$("#frmSearch #fullPath").val(retjson.fullPath);
	
}

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	$("form#frmSearch input[name=pdmTblId]").val('');
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/model/getpdmtblhist.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    initColGrid();
    initColHlGrid();
 	col_sheet.SetColHidden("objVers",0);
    if(row < 1) return;
    
  //IBSheect 내용을 Form에 저장...(row, $formobj, grid)
    ibs2formmapping(row, $("form#frmInput"), grid_sheet);
  
    $("form[name=frmInput] #frsRqstDtm").val(grid_sheet.GetCellText(row, "frsRqstDtm"));
    $("form[name=frmInput] #rqstDtm").val(grid_sheet.GetCellText(row, "rqstDtm"));
    $("form[name=frmInput] #aprvDtm").val(grid_sheet.GetCellText(row, "aprvDtm"));
    $("form[name=frmInput] #strDtm").val(grid_sheet.GetCellText(row, "strDtm"));
    $("form[name=frmInput] #expDtm").val(grid_sheet.GetCellText(row, "expDtm"));
	
    $("form#frmSearch input[name=pdmTblId]").val(grid_sheet.GetCellValue(row, "pdmTblId"));
	
    //선택행 셋팅..
//     var tmpPnm = $("#pdmTblPnm").val();
//     alert(tmpPnm);
//     var tmpLnm = $("#pdmTblLnm").val();
//     alert(tmpLnm);
//     alert(grid_sheet.GetCellValue(row, "pdmTblLnm"));
    
    var tmptit = "<s:message code="CHC.TBL.NM"/> : " + $("#pdmTblPnm").val() + "("+grid_sheet.GetCellValue(row, "pdmTblLnm")+")"; //선택 테이블명
//     alert(tmptit);
    $("#tbl_sle_title").html(tmptit);
    
    //컬럼리스트 조회...
    //doActionCol("Search");
    
       var paramCol = $('#frmSearch').serialize();
       paramCol +="&objVers="+grid_sheet.GetCellValue(row, "objVers");

      col_sheet.DoSearch('<c:url value="/meta/model/getpdmcolchglist.do" />', paramCol);
       
//     doActionTblS("Search");
//    doActionTblH("Search");
     doActionColH("Search");
//     doActionIdxC("Search");
	var param =  grid_sheet.GetRowJson(row);
	var param1 = "paEntyId=" + param.pdmTblId;
     //rel_sheet.DoSearch('<c:url value="/meta/model/getpdmrellistHistPage.do" />', param1);
     //relhist_sheet.DoSearch('<c:url value="/meta/model/getpdmrelhistlistHistPage.do" />', param1);
	
    //시트에서 포커스 제거
	//grid_sheet.SetBlur();
	//하단 폼을 보여주고 포커스를 이동한다...
	//모델 논리명으로 이동...
	$("#tabs").show();
	//$("#pdmTblPnm").focus().click();
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		grid_sheet_OnClick(1);
		 $("form#frmSearch input[name=pdmTblId]").val(null);
	}
	
}


</script>
</head>

<body>

 <div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	   <div class="menu_title"><s:message code="PHYC.MDEL.CHG.HSTR.INQ" /></div> <!-- 물리모델 변경이력 조회 -->
	</div>
	
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        <input type="hidden" name="pdmTblId" />
        	<input type="hidden" name="subjId" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CHG.HSTR.INQ' />"> <!-- 변경이력조회 -->
                   <caption><s:message code="PHYC.MDEL.CHG.HSTR.INQ.FORM" /></caption> <!-- 물리모델 변경이력 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                         <tr>
<!--                        <th scope="row" ><label for="sysAreaId">시스템영역</label></th>
                            <td>
                                <select id="sysAreaId" class="" name="sysAreaId">
                                        <option value="">전체</option> 
                                 </select>
                            </td>-->
<!--                        
<!--                                 </tr> -->
<!--                             <tr> -->
                            <th scope="row"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="hidden" name="subjLnm" id="subjLnm" />
                                <input type="text" name="fullPath" id="fullPath" class="wd280"/>
                                		<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
		                                <button class="btnSearchPop" id="subjSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </span>
                            </td>
                                 <th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 --> 
                                 <td> 
                                 <select class="wd100" id="regTypCd" class="" name="regTypCd"> 
 									<option value=""><s:message code="WHL" /></option> <!-- 전체 --> 
 								</select>
 								</td> 
                            </tr>
  						    <tr>
                                <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                                <td><input type="text" id="pdmTblLnm" name="pdmTblLnm" value="${pdmTblLnm}" class="wd200" /></td>
                                
                                 <th scope="row" ><label for="pdmColLnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmColLnm" id="pdmColLnm" value="${search.pdmColLnm}" class="wd200"/>
                                </span>
                            </td>
                                </tr>
                                 <tr>
                                         <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                                         <td class="bd_none">
                                         	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                                         	<a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                                         	<a href="#" class="tb_bt"><s:message code="DD7" /></a> <!-- 7일 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
                                         </td>
                                         
                                         <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" readonly />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}" readonly />
      		   					</td>
  						    </tr> 
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
		<!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "150px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="tbl_sle_title"><s:message code="MSG.TBL.CHC" /></div> <!-- 테이블을 선택하세요. -->
	</div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div style="clear:both; height:5px;"><span></span></div>
<%-- <%= application.getRealPath("/") %> --%>
	<!-- 선택 레코드의 내용을 탭처리... -->
	 <div id="tabs">
	  <ul>
	    <li id="tab-1"><a href="#tabs-1"><s:message code="TBL.INFO"/></a></li> <!-- 테이블정보 -->
	    <li id="tab-2"><a href="#tabs-2"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼목록 -->
	    <li id="tab-3" style="display:none;"><a href="#tabs-3"><s:message code="RLT.LST" /></a></li> <!-- 관계목록 -->
	    <li id="tab-4"><a href="#tabs-4"><s:message code="CLMN.INFO" /></a></li> <!-- 컬럼정보 -->
	    <li id="tab-5"><a href="#tabs-5"><s:message code="TBL.HSTR" /></a></li> <!-- 테이블이력 -->
	    <li id="tab-6"><a href="#tabs-6"><s:message code="CLMN.HSTR" /></a></li> <!-- 컬럼이력 -->
	    <li id="tab-7" style="display:none;"><a href="#tabs-7"><s:message code="RLT.HSTR" /></a></li> <!-- 관계이력 -->
<!-- 	    <li id="tab-6"><a href="#tabs-6">종속성</a></li> -->
<!-- 	    <li id="tab-7"><a href="#tabs-7">Where Used</a></li> -->
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
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="subjId">주제영역ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="subjId" name="subjId"/></span></td> --%>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row" class=""><label for="mdlLnm">모델논리명</label></th> -->
<!-- 	                                <td colspan="3"><input type="text" id="mdlLnm" name="mdlLnm" /></td> -->
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" ><label for="fullPath"><s:message code="SUBJ.TRRT.PATH"/></label></th> <!-- 주제영역 경로 -->
	                                <td colspan="3"><input type="text" id="fullPath" name="fullPath" class="wd98p" readonly/></td>

<!-- 	                                <th scope="row" class=""><label for="subjLnm">주제영역 논리명</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="subjLnm" name="subjLnm" readonly/></td> --%>
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
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="pdmTblId">테이블ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="pdmTblId" name="pdmTblId"/></span></td> --%>
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" class=""><label for="pdmTblPnm"><s:message code="TBL.NM.PHYC.NM" /></label></th> <!-- 테이블명(물리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmTblPnm" name="pdmTblPnm" class="wd98p" readonly /></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pdmTblLnm"><s:message code="TBL.NM.LGC.NM" /></label></th> <!-- 테이블명(논리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmTblLnm" name="pdmTblLnm" class="wd98p" readonly/></span></td>
	                            </tr>
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="bfPdmTblPnm">이전 테이블명</label></th> -->
<!-- 	                                <td colspan="3"><input type="text" id="bfPdmTblPnm" name="bfPdmTblPnm" /></td> -->
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row"><label for="stdAplYn"><s:message code="STRD.APL.YN" /></label></th> <!-- 표준적용 여부 -->
	                                <td colspan="3">
		                                <input type="checkbox" id="stdAplYn" name="stdAplYn"     value="Y" /><span class="input_check"><s:message code="STRD.APL.YN" /></span> <!-- 표준적용여부 -->
<!--                             <input type="checkbox" id="partTblYn" name="partTblYn"   value="Y" /><span class="input_check">파티션테이블여부</span>
			                            <input type="checkbox" id="dwTrgTblYn" name="dwTrgTblYn" value="Y" /><span class="input_check">DW대상테이블여부</span>-->
	                                </td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="crgUserNm"><s:message code="CHRG.USER" /></label></th> <!-- 담당사용자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="crgUserNm" name="crgUserNm" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
								<th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                                <td>
                                <select id="regTypCd" class="" name="regTypCd"  disabled="disabled">
									<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									<option value="C"><s:message code="NEW" /></option> <!-- 신규 -->
									<option value="U"><s:message code="CHG" /></option> <!-- 변경 -->
									<option value="D"><s:message code="DEL" /></option> <!-- 삭제 -->
								</select>
								</td>
<!-- 	                                <th scope="row"><label for="regTypCd">상태</label></th> -->
<!-- 	                                <td colspan="1"><input type="text"  id="regTypCd" name="regTypCd"></td> -->
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""  class="b0" style="height:50px;width:99%;" readonly></textarea></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="strDtm"><s:message code="STRN.DTTM" /></label></th><!-- 시작일시 -->
	                                <td colspan="1"><span class=""  ><input type="text"  id="strDtm" name="strDtm" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="expDtm"><s:message code="END.DTTM2" /></label></th> <!-- 만료일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="expDtm" name="expDtm" class="wd98p" readonly/></span></td>
	                            </tr>
<!--  <tr>
	                                <th scope="row"><label for="ctdFcy">보관주기</label></th>
	                                <td colspan="1"><span class=""  ><input type="text"  id="ctdFcy" name="ctdFcy" class="wd98p" readonly/></span></td>-->	                            
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!--	                                <th scope="row"><label for="bckFcy">백업주기</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="bckFcy" name="bckFcy" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="delCri">삭제기준</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="delCri" name="delCri" class="wd98p" readonly/></span></td>-->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!--	                                <th scope="row"><label for="delMtd">삭제방법</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="delMtd" name="delMtd" class="wd98p" readonly/></span></td>
	                            </tr>-->
	                        </tbody>
	                    </table>
	                </div>
	                 <div style="clear:both; height:10px;"><span></span></div>
	                 <%@include file="../stnd/otherinfo.jsp" %>
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
<!-- 	  컬럼 목록 탭 -->
		<%@include file="pdmcolforhist_lst.jsp" %>
	  </div>
  	  <div id="tabs-3">
<!-- 	  관계 목록 -->
			<%@include file="pdmrellst_dtl.jsp" %>
	  </div>
	  <div id="tabs-4">
<!-- 	  테이블 형상이력 -->
<%-- 			<%@include file="tblStatushist_dtl.jsp" %>	 --%>
		<!-- 입력폼 시작 -->
	         <form name="frmInput2" id="frmInput2" method="post" >
	         		<input type="hidden" id="sditmId" name="sditmId"  readonly/>
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="input_form_div">
	            <fieldset>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_basic">
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
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="subjId">주제영역ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="subjId" name="subjId"/></span></td> --%>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row" class=""><label for="mdlLnm">모델논리명</label></th> -->
<!-- 	                                <td colspan="3"><input type="text" id="mdlLnm" name="mdlLnm" /></td> -->
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" class=""><label for="pdmTblId"><s:message code="PHYC.MDEL.TBL" /></label></th> <!-- 물리모델테이블 -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmTblLnm" name="pdmTblLnm" class="wd98p" readonly /></span></td>

	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="CLMN.DTL.INFO"/></div> <!-- 컬럼 상세 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_basic">
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
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="pdmTblId">테이블ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="pdmTblId" name="pdmTblId"/></span></td> --%>
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" class=""><label for="pdmColPnm"><s:message code="PHYC.MDEL.CLMN.NM.PHYC.NM"/></label></th> <!-- 물리모델컬럼명(물리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmColPnm" name="pdmColPnm" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pdmColLnm"><s:message code="PHYC.MDEL.CLMN.NM.LGC.NM"/></label></th> <!-- 물리모델컬럼명(논리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmColLnm" name="pdmColLnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="colOrd"><s:message code="CLMN.SQNC" /></label></th> <!-- 컬럼순서 -->
	                                <td colspan="1"><span class="" ><input type="text" id="colOrd" name="colOrd" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="objVers">버전</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="objVers" name="objVers" readonly/></td> --%>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                       		    <th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                               		 <td><span class="" >
                                		<select id="regTypCd" class="" name="regTypCd"  disabled="disabled">
									<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									<option value="C"><s:message code="NEW" /></option> <!-- 신규 -->
									<option value="U"><s:message code="CHG" /></option> <!-- 변경 -->
									<option value="D"><s:message code="DEL" /></option> <!-- 삭제 -->
								</select>
								</span>
								</td>
								</tr>
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""  class="b0" style="height:50px;width:99%;" readonly></textarea></td>
	                            </tr>
<!-- 	                            style="width:wd500;" -->
	                            <tr>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
	                                <td colspan="1"><input type="text" id="dataType" name="dataType"  class="wd98p"  readonly/></td>
                                <th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 -->
	                            <td colspan="1">
	                                  <select id="encYn" name="encYn"  class="wd100"  readonly>
	                                     <option value = "N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
	                                     <option value = "Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
	                                  </select>
	                            </td>    
<!-- 	                                <th scope="row"><label for="sditmLnm">표준용어</label></th>
	                                <td colspan="1"><input type="text" id="sditmLnm" name="sditmLnm"  class="wd98p" onclick='window.open().location.href="../stnd/sditm_lst.do?objId="+$("#frmInput2 #sditmId").val()'  readonly /> -->
<%-- 	                                <button type="button" class="smenu_link" onclick='window.open().location.href="../stnd/sditm_lst.do?objId="+$("#frmInput2 #sditmId").val()' readonly ><span>표준용어 조회</span></button></td> --%>
	                            </tr>
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="dataLen">데이터길이</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="dataLen" name="dataLen" readonly/></td> --%>
<!--  	                            </tr> -->
<!--  	                            <tr> -->
<!-- 	                                <th scope="row"><label for="dataScal">소수점길이</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="dataScal" name="dataScal" readonly/></td> --%>
<!-- 	                            </tr> -->
	                            <tr>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="pkYn"><s:message code="PK.YN" /></label></th> <!-- PK여부 -->
	                                <td colspan="1"><span class="" >
	                                    <input type="checkbox" id="pkYn" name="pkYn" value="Y" onclick="return false;"/> <s:message code="PK.APL"/></span><!-- PK 적용 -->
	                                </td>
<!-- 	                                <td colspan="1"><input type="text"  id="pkYn" name="pkYn"/></td> -->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pkOrd"><s:message code="PK.SQNC" /></label></th> <!-- PK순서 -->
	                                <td colspan="1"><span class="" ><input type="text"  id="pkOrd" name="pkOrd" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="nonulYn"><s:message code="NOTNULL.YN" /></label></th><!-- NOTNULL여부 -->
	                                <td colspan="1"><span class="" >
	                                    <input type="checkbox" id="nonulYn" name="nonulYn" value="Y" onclick="return false;"/> <s:message code="NOTNULL.APL"/></span><!-- NOTNULL 적용 -->
	                                </td>
<!-- 	                                <td colspan="1"><input type="text" id="nonulYn" name="nonulYn" /></td> -->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="defltVal"><s:message code="DEFAULT.VAL" /></label></th> <!-- DEFAULT값 -->
	                                <td colspan="1"><span class="" ><input type="text" id="defltVal" name="defltVal" class="wd98p" readonly /></span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                 <div style="clear:both; height:10px;"><span></span></div>
					 <%@include file="../stnd/otherinfo.jsp" %>
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

	   <div id="tabs-5">

<!-- 	  테이블 이력 -->
			<%@include file="tblhist_dtl.jsp" %>
	  </div>
	   <div id="tabs-6">
<!-- 	  컬럼 이력 -->
			<%@include file="coltocolhist_dtl.jsp" %>
	  </div>
	  <div id="tabs-7">
<!-- 	  관계 이력 -->
			<%@include file="relhist_dtl.jsp" %>
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</div>

</body>
</html>