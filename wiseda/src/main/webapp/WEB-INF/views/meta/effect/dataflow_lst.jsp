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
<title><s:message code="DATA.FLOWCHART.INQ" /></title> <!-- 데이터흐름도 조회 -->
<script type="text/javascript" src='<c:url value="/js/diagram/cytoscape.min.js"/>'></script>
<%-- <script src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script> --%>
<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSch} ;

$(document).ready(function() {
	
		//alert(sysareaJson[0].codeCd + ":" + sysareaJson[0].codeLnm);
		
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
	   	 // 셀렉트 박스 초기화
	   	//======================================================
	   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
    	
	   	//$( "#tabs" ).tabs();
	   	
        
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	//setautoComplete($("#frmSearch #subjLnm"), "SUBJ");
      
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	initGridColMap();
	
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
        
        SetMergeSheet(5);
        
        var headers = [
                    	{Text:"<s:message code='META.HEADER.DATAFLOW.LST'/>", Align:"Center"}
                    	/* No.|DB접속대상ID|DB접속대상|DB스키마ID|DB스키마|DDL테이블ID|테이블물리명|테이블논리명| */
                    
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      	Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"dbConnTrgId",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"dbConnTrgPnm",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbSchId",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"dbSchPnm",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:10,   SaveName:"ddlTblId",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"ddlTblPnm",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"ddlTblLnm",   	Align:"Left", 	Edit:0}
                ];
                    
        InitColumns(cols);

//         SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        
	     //콤보 목록 설정...
//         InitComboNoMatchText(1, "");
	     SetColHidden("dbConnTrgId"	,1);
	     SetColHidden("dbSchId"		,1);
	     SetColHidden("ddlTblId"	,0);
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function initGridColMap()
{
    
    with(colmap_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [ {Text:"<s:message code='META.HEADER.DATAFLOW.LST.1'/>"},
                    	{Text:"<s:message code='META.HEADER.DATAFLOW.LST.2'/>", Align:"Center"}                    
                ];
        
        /* var headers = [ {Text:"<s:message code='META.HEADER.DATAFLOW.LST.1'/>"}, //No.|소스테이블|소스테이블|소스테이블|소스테이블|소스테이블|선택테이블|선택테이블|선택테이블|선택테이블|선택테이블|타겟테이블|타겟테이블|타겟테이블|타겟테이블|타겟테이블
                    	{Text:"<s:message code='META.HEADER.DATAFLOW.LST.2'/>", Align:"Center"} //No.|DB접속대상|DB스키마|테이블(물리명)|컬럼(물리명)|데이터타입|DB접속대상|DB스키마|테이블(물리명)|컬럼(물리명)|데이터타입|DB접속대상|DB스키마|테이블(물리명)|컬럼(물리명)|데이터타입
                    
                ];
         */
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq"		, Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"srcDbPnm"		, Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"srcDbSch"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"srcTblPnm"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"srcColPnm"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"srcDataYype"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"dbPnm"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"dbSch"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"tblPnm"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"colPnm"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"dataType"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"tgtDbPnm"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"tgtDbSch"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"tgtTblPnm"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"tgtColPnm"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"tgtDataType"	, Align:"Left"  , Edit:0}
                ];
                    
        InitColumns(cols);

//         SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        
	     //콤보 목록 설정...
//         InitComboNoMatchText(1, "");
	     SetColHidden("dbConnTrgId"	,1);
	     SetColHidden("dbSchId"		,1);
	     SetColHidden("ddlTblId"	,1);
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(colmap_sheet);    
    //===========================
   
}



function doAction(sAction)
{
        
    switch(sAction)
    {
        
                    
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/effect/getDataflowTblList.do" />", param);
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

	//tblClick(row);
	
	
	getsrc2tgtdiagram(row);
	
	
	//drawdataflow(param);
// 	var dbc = "&dbSchId="+grid_sheet.GetCellValue(row, "dbSchId")+"&dbcTblNm="+grid_sheet.GetCellValue(row, "dbcTblNm");
// 	var ddl = "&ddlTblId="+grid_sheet.GetCellValue(row, "ddlTblId");
// 	var pdm = "&pdmTblId="+grid_sheet.GetCellValue(row, "pdmTblId");

	//선택한 그리드의 row 내용을 보여준다.....
// 	var tmphtml = '테이블명 : ' + param.pdmTblPnm;
// 	$('#sel_title').html(tmphtml);
	
// 	grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcollist_dtl.do" />', ddl);
// 	grid_sub_dbctblcollist.DoSearch('<c:url value="/meta/dbc/ajaxgrid/dbctblcollist_dtl.do" />', dbc);
    
}

//선택한 테이블의 다이어그램 및 컬럼 소스 타겟 정보를 가져온다....
function getsrc2tgtdiagram(row) {
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	param.dataFlowFlag = $('#dataFlowFlag').val();
	
	console.log(param);
	
	//데이터 흐름 정보 가져오기....
	$.getJSON('<c:url value="/meta/effect/getdataflowlist.do"/>', param,  function(data){
		if(data ==  null) return;
// 		alert(data);
		drawdataflow(data);
 		
	}); 
	
	var param1 = "ddlTblId="+param.ddlTblId;
	param1 += "&dataFlowFlag="+param.dataFlowFlag;
	
	//컬럼 소스 타겟 정보를 가져온다....
	colmap_sheet.DoSearch("<c:url value="/meta/effect/getdataflowcolmaplist.do" />", param1);
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

//데이트 흐르 다이어그램 그리기....
function drawdataflow(param) {

 var elesJson = param;
 
 console.log(elesJson);

 $('#cy').cytoscape({
	  style: cytoscape.stylesheet()
	    .selector('node')
	      .css({
	        'content': 'data(name)',
// 	        'font-size': 15,
	        'width': '200px',
	        'height': '100px',
	        'text-valign': 'center',
	        'shape': 'rectangle',
	        'color': 'white',
	        'text-outline-width': 2,
// 	        'text-outline-color': '#888'
	        'text-outline-color': '#6FB1FC',
	         'background-color': '#6FB1FC', 
	      })
	    .selector('edge')
	      .css({
	        'target-arrow-shape': 'triangle',
	        'width': 4, 
	        'line-color': '#6FB1FC',
	        'source-arrow-color': '#6FB1FC',
	        'target-arrow-color': '#6FB1FC', 
	      })
	    .selector(':selected')
	      .css({
	        'background-color': '#F5A45D',
// 	        'line-color': '#bbb',
// 	        'target-arrow-color': '#bbb',
// 	        'source-arrow-color': '#bbb'
	      })
	    .selector('.faded')
	      .css({
	        'opacity': 0.25,
	        'text-opacity': 0, 
	      }),
	  
	  elements: elesJson,
// 	  elements: {
// 	    nodes: [
// 	      { data: { id: 'j', name: '한글1' } },
// 	      { data: { id: 'e', name: '한글2' } },
// 	      { data: { id: 'k', name: '한글3' } },
// 	      { data: { id: 'g', name: '한글4' } }
// 	    ],
// 	    edges: [
// 	      { data: { source: 'j', target: 'e' } },
// 	      { data: { source: 'j', target: 'k' } },
// 	      { data: { source: 'j', target: 'g' } },
// 	      { data: { source: 'e', target: 'j' } },
// 	      { data: { source: 'e', target: 'k' } },
// 	      { data: { source: 'k', target: 'j' } },
// 	      { data: { source: 'k', target: 'e' } },
// 	      { data: { source: 'k', target: 'g' } },
// 	      { data: { source: 'g', target: 'j' } }
// 	    ]
// 	  },
	  
	  layout: {
		  name: 'breadthfirst',
		    directed: true,
// 		    circle: true,
// 			fit: true,
		    padding: 10, 
	  },
	  
	  ready: function(){
	    window.cy = this;
	    
// 	    // giddy up...
	    
// 	    cy.elements().unselectify();
	    
	    cy.on('tap', 'node', function(){
// 	    	alert("sss");
	    	//alert(this.data('id'));
	    	var nameVal = this.data('name').split('.');
	    	var id = nameVal[1];
	    	var crow = grid_sheet.FindText("ddlTblPnm", id);
// 	    	alert (id + ":"+ crow);
	    	grid_sheet.SetSelectRow(crow);
	    	getsrc2tgtdiagram(crow);
	    	
	    });
	
// 	    cy.on('tap', 'node', function(e){
// 	      var node = e.cyTarget; 
// 	      var neighborhood = node.neighborhood().add(node);
	      
// 	      cy.elements().addClass('faded');
// 	      neighborhood.removeClass('faded');
// 	    });
	    
// 	    cy.on('tap', function(e){
// 	      if( e.cyTarget === cy ){
// 	        cy.elements().removeClass('faded');
// 	      }
// 	    });
	  }
	});
}
</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="SUBJ.TRRT.INQ" /></div> <!-- 주제영역 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>


<!-- 좌측 DIV -->
<div style="float:left; width: 35%;">
<div style="padding-right: 10px">

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
                   <col style="width:10%;" />
                   <col style="width:25%;" />
<%--                    <col style="width:10%;" /> --%>
<%--                    <col style="width:25%;" /> --%>
<%--                    <col style="width:10%;" /> --%>
<%--                    <col style="width:25%;" /> --%>
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                          <th scope="row" class=""><label for="dbSchId"><s:message code="DB.SCHMA.INFO"/></label></th> <!-- DB/스키마정보 -->
	                      <td>
				            <select id="dbConnTrgId" class="" name="dbConnTrgId">
				             <option value="">----<s:message code="CHC" />----</option> <!-- 선택 -->
				            </select>
				            <select id="dbSchId" class="" name="dbSchId">
				             <option value="">----<s:message code="CHC" />----</option> <!-- 선택 -->
				             </select>
				           </td>
                                                     
                       </tr>
                       <tr> 
                           <th scope="row"><label for="ddlTblPnm"><s:message code="TBL" /></label></th> <!-- 테이블 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="ddlTblPnm" id="ddlTblPnm" class="wd60p" />
                                </span>
                            </td>
                       </tr>
                       <tr> 
                           <th scope="row"><label for="ddlColPnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="ddlColPnm" id="ddlColPnm" class="wd60p" />
                                </span>
                            </td>
                       </tr>
                       <tr>                               
                          <th scope="row" class=""><label for="dataFlowFlag">조회 기준</th> <!-- DB/스키마정보 -->
	                      <td>
				            <select id="dataFlowFlag" class="" name="dataFlowFlag">
				             <option value="map">매핑정의서</option> <!-- 선택 -->
				             <option value="flow">데이터흐름관리</option>
				            </select>
				           </td>
                                                     
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<%--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div> --%>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
<%-- 		<%@ include file="" %> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
	</div>
</div>
<!-- 우측 DIV -->
<div style="float:left; width: 65%;">
	<div>
		<div class="stit"><s:message code="DATA.FLOWCHART" /></div> <!-- 데이터 흐름도 -->
        <div style="clear:both; height:5px;"><span></span></div>
        <div id="cy" style="border:1px solid #ddd; width: 100%; height: 515px;"></div>
	</div>
<%-- 	<div style="clear:both; height:10px;"><span></span></div> --%>
<!-- 	<div> -->
<!-- 		<div class="stit">컬럼소스타겟</div> -->
<%--         <div style="clear:both; height:5px;"><span></span></div> --%>
<!-- 	</div> -->
</div>


	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title" id="sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="CLMN.SOUR.TARG" /></a></li> <!-- 컬럼소스타겟 -->
	    
	  </ul>
	  <div id="tabs-1">
	  	<div style="clear:both; height:5px;"><span></span></div>
	        
		<!-- 그리드 입력 입력 -->
		<div id="grid_02" class="grid_01">
		     <script type="text/javascript">createIBSheet("colmap_sheet", "100%", "300px");</script>            
	<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
		</div>
	  </div>
	  
	 </div>
</div>	 

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>