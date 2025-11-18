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
<title><s:message code="MDEL.GAP.ANLY.INQ" /></title> <!-- 모델GAP분석 조회 -->

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
var dbGapSrcShdIdJson = ${codeMap.dbGapSrcShdId} ;

var dbGapTgtShdIdJson = ${codeMap.dbGapTgtShdId} ;

$(document).ready(function() {
	
		//alert(sysareaJson[0].codeCd + ":" + sysareaJson[0].codeLnm);
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
		//달력팝업 추가...
	 	$( "#searchBgnDe" ).datepicker();
		$( "#searchEndDe" ).datepicker();
		
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ 
        	doAction("Search");  
        	
        });
        
        $("#btnDelete").hide();
        
        $("#btnTreeNew").change(function(){}).hide();
                      
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        
    	//DB매핑 검색 팝업 호출
    	$("#searchPop").click(function(){
    		var param = ""; //$("form#frmInput").serialize();
    		openLayerPop ("<c:url value='/commons/damgmt/db/popup/dbmap_pop.do' />", 800, 600, param);
//     		OpenWindow("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", "dkafda",  800, 600, "yes");
//     		openSearchPop("<c:url value='/meta/model/pop/subjSearchPop.do' />", param);
        });
    	//DB매핑 검색 팝업 호출
    	$("#searchPop2").click(function(){
    		var param = ""; //$("form#frmInput").serialize();
    		openLayerPop ("<c:url value='/commons/damgmt/db/popup/dbmap_pop.do' />", 800, 600, param);
//     		OpenWindow("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", "dkafda",  800, 600, "yes");
//     		openSearchPop("<c:url value='/meta/model/pop/subjSearchPop.do' />", param);
        });

        
    	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
	   	double_select(dbGapSrcShdIdJson, $("#frmSearch #srcDbConnTrgId"));
	   	$('select', $("#frmSearch #srcDbConnTrgId").parent()).change(function(){
	   		double_select(dbGapSrcShdIdJson, $(this));
	   	});
	   	
	   	double_select(dbGapTgtShdIdJson, $("#frmSearch #tgtDbConnTrgId"));
	   	$('select', $("#frmSearch #tgtDbConnTrgId").parent()).change(function(){
	   		double_select(dbGapTgtShdIdJson, $(this));
	   	});
    	
	   	//$( "#tabs" ).tabs();
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
});

//주제영역 팝업 리턴값 처리
function returnDbMapPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	$("#frmSearch #srcDbConnTrgId").val(retjson.srcDbConnTrgId);
	$("#frmSearch #tgtDbConnTrgId").val(retjson.tgtDbConnTrgId);
	$("#frmSearch #srcDbConnTrgId").change();
	$("#frmSearch #tgtDbConnTrgId").change();
	$("#frmSearch #srcDbSchId").val(retjson.srcDbSchId);
	$("#frmSearch #tgtDbSchId").val(retjson.tgtDbSchId);
}


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
        
        SetMergeSheet(5);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.DBGAP.LST1'/>"},
                    /* No.|상태|소스(Source)|소스(Source)|소스(Source)|소스(Source)|소스(Source)|소스(Source)|타겟(Target)|타겟(Target)|타겟(Target)|타겟(Target)|타겟(Target)|타겟(Target)|에러내용|에러내용|에러내용|에러내용|에러내용|에러내용 */
                    	{Text:"<s:message code='META.HEADER.DBGAP.LST2'/>", Align:"Center"}
                    /* No.|상태|DBMS명|스키마명|테이블명|테이블명(논리)|DBMS유형|테이블존재여부|DBMS명|스키마명|테이블명|테이블명(논리)|DBMS유형|테이블존재여부|테이블에러여부|테이블에러내용|인덱스에러여부|인덱스에러내용|컬럼에러여부|컬럼에러내용 */
                    
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Combo",   Width:60,   SaveName:"gapStatus",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"srcConnTrgId",   Align:"Left", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"srcDbSchId",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:120,   SaveName:"srcTblPnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"srcTblLnm",   Align:"Left", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"srcDbmsType",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:100,   SaveName:"srcTblExtncExs",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"tgtConnTrgId",   Align:"Left", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"tgtDbSchId",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"tgtTblPnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"tgtTblLnm",   Align:"Left", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"tgtDbmsType",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"Combo",   Width:100,   SaveName:"tgtTblExtncExs",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"tblErrExs",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"tblErrDescn",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",   Width:80,   SaveName:"idxErrExs",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"idxErrDescn",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",   Width:80,   SaveName:"colErrExs",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"colErrDescn",   Align:"Left", Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);
        SetColProperty("srcConnTrgId", 	${codeMap.connTrgDbmsibs});
        SetColProperty("tgtConnTrgId", 	${codeMap.connTrgDbmsibs});
        SetColProperty("srcDbSchId", 	${codeMap.dbSchLnmibs});
        SetColProperty("tgtDbSchId", 	${codeMap.dbSchLnmibs});
        SetColProperty("srcDbmsType", 	${codeMap.dbmsTypCdibs});
        SetColProperty("tgtDbmsType", 	${codeMap.dbmsTypCdibs});
        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        SetColProperty("srcTblExtncExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("tgtTblExtncExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("tblErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("idxErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("colErrExs", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
	     //콤보 목록 설정...
        InitComboNoMatchText(1, "");
	     
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}


function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/dbgap/getDbGapList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2ExcelBuffer(true);  
        
        	if(grid_sheet.SearchRows()>0){
        		grid_sheet.Down2Excel({FileName:'gap_list',SheetName:'<s:message code="DB.GAP.ANLY"/>', HiddenColumn:1, Merge:1}); /*DB GAP분석*/
        	}
        	if(col_sheet.SearchRows()>0){
        		col_sheet.Down2Excel({FileName:'gap_list',SheetName:'<s:message code="CLMN.GAP.DTL.INFO"/>', HiddenColumn:1, Merge:1}); /*컬럼GAP 상세정보*/
        	}
        	if(grid_sub_ddltblcollist.SearchRows()>0){
        		src_sheet.Down2Excel({FileName:'gap_list',SheetName:'<s:message code="SOUR.CLMN"/>', HiddenColumn:1, Merge:1}); /*소스컬럼*/
        	}
        	if(grid_sub_dbctblcollist.SearchRows()>0){
        		tgt_sheet.Down2Excel({FileName:'gap_list',SheetName:'<s:message code="TARG.CLMN"/>', HiddenColumn:1, Merge:1}); /*타겟컬럼*/
        	}
        	//인덱스
//         	if(idx_sheet.SearchRows()>0){
//         		col_sheet.Down2Excel({FileName:'gap_list',SheetName:'컬럼GAP 상세정보', HiddenColumn:1}); 
//         	}
//         	if(idx_src_sheet.SearchRows()>0){
//         		idx_src_sheet.Down2Excel({FileName:'gap_list',SheetName:'소스인덱스컬럼', HiddenColumn:1}); 
//         	}
//         	if(idx_tgt_sheet.SearchRows()>0){
//         		idx_tgt_sheet.Down2Excel({FileName:'gap_list',SheetName:'타겟인덱스컬럼', HiddenColumn:1}); 
//         	}
        	grid_sheet.Down2ExcelBuffer(false);   
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
	var src = "&srcDbSchId="+grid_sheet.GetCellValue(row, "srcDbSchId")+"&srcTblPnm="+grid_sheet.GetCellValue(row, "srcTblPnm");
	var tgt = "&tgtDbSchId="+grid_sheet.GetCellValue(row, "tgtDbSchId")+"&tgtTblPnm="+grid_sheet.GetCellValue(row, "tgtTblPnm");
	var gap = src+tgt;

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="TARG.TBL.NM" /> : ' + param.tgtTblPnm + ', <s:message code="SOUR.TBL.NM2" /> : ' + param.srcTblPnm;
    //타겟테이블명, 소스테이블명
	$('#sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	col_sheet.DoSearch("<c:url value="/meta/gap/getGapColList.do" />", gap);
	src_sheet.DoSearch("<c:url value="/meta/gap/getGapSrcList.do" />", src);
	tgt_sheet.DoSearch("<c:url value="/meta/gap/getGapTgtList.do" />", tgt);
	
	//인덱스 GAP
	idx_sheet.DoSearch("<c:url value="/meta/gap/getGapIdxList.do" />", gap);
	idx_src_sheet.DoSearch("<c:url value="/meta/gap/getGapIdxColSrcList.do" />", src);
	idx_tgt_sheet.DoSearch("<c:url value="/meta/gap/getGapIdxColTgtList.do" />", tgt);
    
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
}

function src_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function tgt_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}


function idx_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function idx_src_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function idx_tgt_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"></div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <input type="hidden" id="bpdmRow" name="bpdmRow" readonly="readonly"/>
            <input type="hidden" id="bddlRow" name="bddlRow" readonly="readonly"/>
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DBGAP.ANLY.INQ' />"><!-- DBGAP 분석조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="srcDbSchId"><s:message code="SOUR.DB.INFO" /></label></th> <!-- 소스DB정보 -->
                            <td>
                            <select id="srcDbConnTrgId" class="" name="srcDbConnTrgId">
				             <option value="">----<s:message code="CHC" />----</option> <!-- 선택 -->
				            </select>
				            <select id="srcDbSchId" class="" name="srcDbSchId">
				             <option value="">----<s:message code="CHC" />----</option> <!-- 선택 -->
				             </select>
						 	<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						 	<button class="btnSearchPop" id="searchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                            </td>
                           
                            <th scope="row" class=""><label for="tgtDbSchId"><s:message code="TARG.DB.INFO" /></label></th> <!-- 타겟DB정보 -->
	                      <td>
				            <select id="tgtDbConnTrgId" class="" name="tgtDbConnTrgId">
				             <option value="">----<s:message code="CHC" />----</option> <!-- 선택 -->
				            </select>
				            <select id="tgtDbSchId" class="" name="tgtDbSchId">
				             <option value="">----<s:message code="CHC" />----</option> <!-- 선택 -->
				             </select>
				             <button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						 	<button class="btnSearchPop" id="searchPop2"><s:message code="SRCH" /></button> <!-- 검색 -->
				           </td>
                        </tr>
                        <tr>                          
                           <th scope="row"><label for="gapStatus"><s:message code="STS" /></label></th> <!-- 상태 -->
                            <td>
                                <select id="gapStatus" class="" name="gapStatus">
                                        <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
                                        <option value="NML"><s:message code="NROM" /></option> <!-- 정상 -->
                                        <option value="GAP">GAP</option>
                                 </select>
                            </td>
                            <th scope="row"><label for="tgtTblPnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                            <td>
                                <input type="text" id="tgtTblPnm" name="tgtTblPnm"/>
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
<%-- 		<%@ include file="" %> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title"><s:message code="SUBJ.TRRT.NM" /> : <span></span></div> <!-- 주제영역명 -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="CLMN.GAP.DTL.INFO"/></a></li> <!-- 컬럼GAP 상세정보 -->
	    <li><a href="#tabs-2"><s:message code="IDEX.GAP.DTL.INFO"/></a></li> <!-- 인덱스GAP 상세정보 -->
	  </ul>
	  
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="dbgapcol_dtl.jsp" %>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  <div id="tabs-2">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="dbgapidx_dtl.jsp" %>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  
	 </div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>