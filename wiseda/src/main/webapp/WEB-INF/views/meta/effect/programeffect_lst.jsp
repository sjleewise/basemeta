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
<title><s:message code="PGM.IMPACHART.INQ" /></title> <!-- 프로그램영향도 조회 -->
<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
<%-- var connTrgSchJson = ${codeMap.connTrgSch} ; --%>

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ 
        	doAction("Search");  
        	
        });
        
        $("#btnTreeNew").hide();
        
        $("#btnDelete").hide();
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        
	   	//======================================================
	   	 // 탭 설정...
	   	//======================================================
	   	//$( "#tabs" ).tabs();
	   	
	   	
	   	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
// 	   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
// 	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
// 	   		double_select(connTrgSchJson, $(this));
// 	   	});
    	
	   	
        
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	//setautoComplete($("#frmSearch #subjLnm"), "SUBJ");
      
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	initGrid_tblcrud();
	initGrid_relprgm();
	initGrid_relfunc();
	loadDetail();
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
        
        SetMergeSheet(5);
        
        var headers = [
                    	{Text:"<s:message code='META.HEADER.PROGRAMEFFECT.LST'/>", Align:"Center"}
                    	/* No.|상세업무시스템ID|시스템|업무시스템|상세업무시스템|구분|프로그램 파일명|프로그램유형|프로그램명|파일경로|작성자|프로그램ID */
                    
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      	Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"subsystemId",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"systemNm",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"bizsystemNm",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"subsystemNm",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"categoryType",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"pgmFileNm",   	Align:"Left", 	Edit:0},
                    {Type:"Combo",  Width:120,   SaveName:"pgmType",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"pgmNm",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:150,   SaveName:"pgmFilePath",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"orglUser",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"pgmId",   		Align:"Left", 	Edit:0}
                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
        SetColProperty("pgmType", 	${codeMap.langTypeibs});
//         SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        
	     //콤보 목록 설정...
//         InitComboNoMatchText(1, "");
	     
	     //히든 컬럼 설정...
	     SetColHidden("subsystemId"		,1);
	     SetColHidden("orglUser"	,1);
	     SetColHidden("pgmId"	,1);
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function initGrid_tblcrud()
{
    
    with(tblcrud_grid){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [
                    	{Text:"<s:message code='META.HEADER.PROGRAMEFFECT.LST.1'/>", Align:"Center"} //No.|주제영역|테이블(물리명)|테이블(논리명)|CREATE|READ|UPDATE|DELETE|테이블ID
                    
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Text",   Width:150,   SaveName:"fullPath",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"tblEnm",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"pdmTblLnm",   Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"createYn",   Align:"Center", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"readYn",   	Align:"Center", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"updateYn",   Align:"Center", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"deleteYn",   Align:"Center", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"pdmTblId",   	Align:"Center", 	Edit:0}
                ];
                    
        InitColumns(cols);

	    //콤보 목록 설정...
//         SetColProperty("pgmType", 	${codeMap.langTypeibs});
//         SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        
	     //콤보 목록 설정...
//         InitComboNoMatchText(1, "");
	     
	     //히든 컬럼 설정...
	     SetColHidden("pdmTblId"		,1);
// 	     SetColHidden("orglUser"	,1);
// 	     SetColHidden("pgmId"	,1);
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(tblcrud_grid);    
    //===========================
   
}

function initGrid_relprgm()
{
    
    with(relprgm_grid){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [
                   	{Text:"<s:message code='META.HEADER.PROGRAMEFFECT.LST.2'/>", Align:"Center"}//No.|상세업무시스템ID|시스템|업무시스템|상세업무시스템|구분|프로그램 파일명|프로그램유형|프로그램명|파일경로|작성자|프로그램ID
                   
               ];
       
       var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
       
       InitHeaders(headers, headerInfo); 

       var cols = [                        
                   {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      	Align:"Center", Edit:0},
                   {Type:"Text",   Width:60,   SaveName:"subsystemId",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:80,   SaveName:"systemNm",   		Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:80,   SaveName:"bizsystemNm",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:80,   SaveName:"subsystemNm",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:60,   SaveName:"categoryType",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:100,   SaveName:"pgmFileNm",   	Align:"Left", 	Edit:0},
                   {Type:"Combo",  Width:120,   SaveName:"pgmType",   		Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:100,   SaveName:"pgmNm",   		Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:150,   SaveName:"pgmFilePath",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:80,   SaveName:"orglUser",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:60,   SaveName:"pgmId",   		Align:"Left", 	Edit:0}
               ];
                   
       InitColumns(cols);

	    //콤보 목록 설정...
       SetColProperty("pgmType", 	${codeMap.langTypeibs});
//        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
       
	     //콤보 목록 설정...
//        InitComboNoMatchText(1, "");
	     
	     //히든 컬럼 설정...
	     SetColHidden("subsystemId"		,1);
	     SetColHidden("categoryType"		,1);
	     SetColHidden("pgmFilePath"		,1);
	     SetColHidden("orglUser"	,1);
	     SetColHidden("pgmId"	,1);
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(relprgm_grid);    
    //===========================
   
}

function initGrid_relfunc()
{
    
    with(relfunc_grid){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [
                   	{Text:"<s:message code='META.HEADER.PROGRAMEFFECT.LST.3'/>", Align:"Center"}//No.|상세업무시스템ID|시스템|업무시스템|상세업무시스템|프로그램 파일명|함수명|테이블명|프로그램ID|함수ID
                   
               ];
       
       var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
       
       InitHeaders(headers, headerInfo); 

       var cols = [                        
                   {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      	Align:"Center", Edit:0},
                   {Type:"Text",   Width:60,   SaveName:"subsystemId",  Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:80,   SaveName:"systemNm",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:80,   SaveName:"bizsystemNm",  Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:80,   SaveName:"subsystemNm",  Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:100,   SaveName:"pgmFileNm",   Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:150,   SaveName:"fucEnm",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:150,   SaveName:"tblEnm",   	Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:60,   SaveName:"pgmId",   		Align:"Left", 	Edit:0},
                   {Type:"Text",   Width:60,   SaveName:"fucId",   		Align:"Left", 	Edit:0},
               ];
                   
       InitColumns(cols);

	    //콤보 목록 설정...
//        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
       
	     //콤보 목록 설정...
//        InitComboNoMatchText(1, "");
	     
	     //히든 컬럼 설정...
	     SetColHidden("subsystemId"		,1);
	     SetColHidden("pgmId"	,1);
	     SetColHidden("fucId"	,1);
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(relfunc_grid);    
    //===========================
   
}


function doAction(sAction)
{
        
    switch(sAction)
    {
        
                    
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/effect/getProgrameEffectList.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;

    }       
}
 



//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
		
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			

			
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			  doAction("Search");
			
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load("<c:url value="/meta/effect/getProgrameEffectDetail.do" />", param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
			    alert( msg + xhr.status + " " + xhr.statusText );
			  }
	});
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
	
	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
// 	var subjLnm = grid_sheet.GetCellValue(row, "subjLnm");	
	
// 	window.open().location.href = "pdmtbl_lst.do?subjLnm="+subjLnm;
	
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
		
// 	var dbc = "&dbSchId="+grid_sheet.GetCellValue(row, "dbSchId")+"&dbcTblNm="+grid_sheet.GetCellValue(row, "dbcTblNm");
// 	var ddl = "&ddlTblId="+grid_sheet.GetCellValue(row, "ddlTblId");
// 	var pdm = "&pdmTblId="+grid_sheet.GetCellValue(row, "pdmTblId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="PGM.NM" /> : ' + param.pgmNm; /*프로그램명*/
	$('#sel_prg_title').html(tmphtml);
	
	loadDetail(param);
	
	
	var param1 = "subsystemId="+param.subsystemId + "&pgmId="+param.pgmId;
	tblcrud_grid.DoSearch('<c:url value="/meta/effect/getProgramEffectTblCRUD.do" />', param1);
	relprgm_grid.DoSearch('<c:url value="/meta/effect/getRelProgramList.do" />', param1);
	relfunc_grid.DoSearch('<c:url value="/meta/effect/getRelFuncList.do" />', param1);
	
	
// 	grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcollist_dtl.do" />', ddl);
// 	grid_sub_dbctblcollist.DoSearch('<c:url value="/meta/dbc/ajaxgrid/dbctblcollist_dtl.do" />', dbc);
    
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
                          <th scope="row" class=""><label for=subsystemNm><s:message code="DTL.BZWR.SYS" /></label></th> <!-- 상세업무시스템 -->
	                      <td>
	                      		<span class="input_file">
                                <input type="text" name="subsystemNm" id="subsystemNm" class="wd60p" />
                                </span>
				           </td>
                          <th scope="row" class=""><label for="pgmType"><s:message code="PGM.PTRN" /></label></th> <!-- 프로그램유형 -->
	                      <td>
				            <select id="pgmType" class="" name="pgmType">
				             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
				             <c:forEach items="${codeMap.langType }" var="code" varStatus="status">
				             <option value="${code.codeCd }" >${code.codeLnm}</option>
				             </c:forEach>
				            </select>
				           </td>
                                                     
                       </tr>
                       <tr> 
                           <th scope="row"><label for="pgmNm"><s:message code="PGM.FILE.NM" /></label></th> <!-- 프로그램(파일)명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pgmNm" id="pgmNm" class="wd60p" />
                                </span>
                            </td>
                           <th scope="row"><label for="tblEnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="tblEnm" id="tblEnm" class="wd60p" />
                                </span>
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
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" >
		    <div class="selected_title" id="sel_prg_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="PGM.INFO" /></a></li> <!-- 프로그램정보 -->
	    <li><a href="#tabs-2"><s:message code="TBL.CRUD" /></a></li> <!-- 테이블CRUD -->
	    <li><a href="#tabs-3"><s:message code="REL.PGM" /></a></li> <!-- 관련프로그램 -->
	    <li><a href="#tabs-4"><s:message code="REL.FUNC" /></a></li> <!-- 관련함수 -->
	    
	  </ul>
	  <div id="tabs-1">
		<!-- 	상세정보 ajax 로드시 이용 -->
		<div id="detailInfo"></div>        
	  </div>
	  <div id="tabs-2">
		  	<!-- 그리드 입력 입력 -->
		<div id="grid_02" class="grid_01">
		     <script type="text/javascript">createIBSheet("tblcrud_grid", "100%", "200px");</script>            
	<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
		</div>
		<!-- 그리드 입력 입력 -->
	  </div>
	  <div id="tabs-3">
	  	<!-- 그리드 입력 입력 -->
		<div id="grid_03" class="grid_01">
		     <script type="text/javascript">createIBSheet("relprgm_grid", "100%", "200px");</script>            
	<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
		</div>
		<!-- 그리드 입력 입력 -->
	  </div>
	  <div id="tabs-4">
	  	<!-- 그리드 입력 입력 -->
		<div id="grid_04" class="grid_01">
		     <script type="text/javascript">createIBSheet("relfunc_grid", "100%", "200px");</script>            
	<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
		</div>
		<!-- 그리드 입력 입력 -->
	  </div>
	 </div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>