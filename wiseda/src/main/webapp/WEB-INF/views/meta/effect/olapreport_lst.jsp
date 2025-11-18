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
<title><s:message code="OLAP.RPT.INQ" /></title> <!-- OLAP 보고서조회 -->
<script type="text/javascript" src='<c:url value="/js/diagram/cytoscape.min.js"/>'></script>
<%-- <script src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script> --%>
<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");

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
	   
	   //create_selectbox(etclTskTypCd, $("#frmSearch #taskType"));		
	   //double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   //	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   //		double_select(connTrgSchJson, $(this));
	   	//});
    	
	   	//$( "#tabs" ).tabs();
	   	
        
      
});

$(window).on('load',function() {
	
	//그리드 초기화 
	initGrid();
	initGridRptDtl();
	
	doAction("Search");
	
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
                    	{Text:"<s:message code='META.HEADER.OLAPREPORT.LST'/>", Align:"Center"}
                    	/* No.|프로젝트ID|프로젝트명|최초등록일시|최종변경일시|설명 */
                    	
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      	Align:"Center", Edit:0},
                    {Type:"Text",   Width:170,   SaveName:"prjId",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"prjNm",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"orglDttm",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"updtDttm",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:10,   SaveName:"description",   		Align:"Left", 	Edit:0}
                ];
                    
        InitColumns(cols);
        
        
	     SetColHidden("prjId"	,1);
	     SetColHidden("orglDttm"	,1);
	     SetColHidden("updtDttm"	,1);
	     SetColHidden("description"	,1);
	     
		
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function initGridRptDtl()
{
    
    with(report_dtl_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [ {Text:"<s:message code='META.ADD.ETCL.REPORT.1'/>"}                    
                ];
        //No.|PRJ_ID|보고서명|설명
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq"		, Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"prjId"		, Align:"Center", Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"reportNm"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"description"	, Align:"Left"  , Edit:0}
                   
                ];
                    
        InitColumns(cols);

        
        SetColHidden("prjId"	,1);

        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(report_dtl_sheet);    
    //===========================
   
}


function doAction(sAction)
{
        
    switch(sAction)
    {
        
                    
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/effect/getOlapReportList.do" />", param);
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
    
	if(row < 1) return;

	tskClick(row);
    
}



//선택한 작업의 영역정보, 용어정보, 소스 및 타겟 정보를 가지고 온다.
function tskClick(row) {
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	

	//프로젝트 탭 설정
	$("form[name=frmField] #prjNm").val(param.prjNm);
	$("form[name=frmField] #orglDttm").val(param.orglDttm);
	$("form[name=frmField] #updtDttm").val(param.updtDttm);
	$("form[name=frmField] #description").val(param.description);

	
	//프로젝트ID가 존재하는 경우에만 조회. 없는 경우에는 전체조회가 되므로 조회하지 않는다.
	if(param.prjId != "")
	{
		var param1 = "prjId=" + param.prjId;
		
		//보고서목록 탭 설정
		report_dtl_sheet.DoSearch("<c:url value="/meta/effect/getOlapReportDetailList.do" />", param1);
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
	}
	
	//조회 후 1row 선택
	tskClick(1);
	
	
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="OLAP.RPT.INQ" /></div> <!-- OLAP 보고서조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>


<!-- 좌측 DIV -->
<div style="float:left; width: 35%;">
<div style="padding-right: 10px">

<!-- 검색조건 입력폼 -->
	<div id="search_div">
        <div style="clear:both; height:5px;"><span></span></div>
		<div style="clear:both; height:10px;"><span></span></div>
        
        
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "465px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
	</div>
</div>
<!-- 우측 DIV -->
<div style="float:left; width: 65%;">
	<div>
		<div class="stit"><s:message code="RPT.INFO" /></div> <!-- 보고서정보 -->
        <div style="clear:both; height:5px;"><span></span></div>
        <!--  <div id="cy" style="border:1px solid #ddd; width: 100%; height: 500px;"></div> -->
        
    <!-- 우측위치 -->
        
	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs" style="border:1px solid #ddd; width: 100%; height: 467px;">
	  <ul>
	    <li id="tab-1" ><a href="#tabs-1"><s:message code="PRJT" /></a></li> <!-- 프로젝트 -->
	    <li id="tab-2" ><a href="#tabs-2"><s:message code="RPT.LST" /></a></li> <!-- 보고서목록 -->

	  </ul>

	  
	   <div id="tabs-1">
		<!-- 프로젝트정보 시작 -->
	         <form name="frmField" id="frmField" method="post" >
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="input_form_div">
	            <fieldset>
	
	                
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
	                                <th scope="row" class=""><label for="prjNm"><s:message code="PRJT.NM" /></label></th> <!-- 프로젝트명 -->
	                                <td colspan="3" ><span class="" ><input type="text" id="prjNm" name="prjNm" style="width:100%;" readonly /></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="orglDttm"><s:message code="FRST.REG.DTTM" /></label></th> <!-- 최초등록일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="orglDttm" name="orglDttm" readonly/></span></td>

	                                <th scope="row" class=""><label for="lastSaveDay"><s:message code="LAST.CHG.DTTM" /></label></th> <!-- 최종변경일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="updtDttm" name="updtDttm" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="description"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><span class="" ><input type="text" id="description" name="description" readonly/></span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                </fieldset>
		</div>
	            </form>
		<!-- 프로젝트정보 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>
	  </div> <!-- tabs-1 종료 -->
	  
	  
	  <div id="tabs-2">
	  	<div style="clear:both; height:5px;"><span></span></div>
	        
		<!-- 그리드 입력 입력 -->
		<div id="grid_02" class="grid_01">
		     <script type="text/javascript">createIBSheet("report_dtl_sheet", "100%", "415px");</script>            
	    </div>
	  </div> <!-- tabs-2 종료 -->
	  
	
	  
	 </div> <!-- tabs 설정 -->
        
        
        
	</div>

</div>


	

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>