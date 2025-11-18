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
<title><s:message code="ETCL.JOB.INQ" /></title> <!-- ETCL 작업조회 -->
<script type="text/javascript" src='<c:url value="/js/diagram/cytoscape.min.js"/>'></script>
<%-- <script src="http://cytoscape.github.io/cytoscape.js/api/cytoscape.js-latest/cytoscape.min.js"></script> --%>
<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
var etclTskTypCd = ${codeMap.etclTskTypCd} ;

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
	   
	   create_selectbox(etclTskTypCd, $("#frmSearch #taskType"));		
	   //double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   //	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   //		double_select(connTrgSchJson, $(this));
	   	//});
    	
	   	//$( "#tabs" ).tabs();
	   	
        
      
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	initGridSrc();
	initGridTgt();
	
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
                    	{Text:"<s:message code='META.HEADER.ETCLASK.LST'/>", Align:"Center"}
                    	/* No.|영역명|작업명|valid|enable|작업타입|작업타입명|설명|ID|영역ID|작업ID|MAPPING_ID|작업최종저장날짜|용어매핑명|용어최종저장날짜 */
                    	
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      	Align:"Center", Edit:0},
                    {Type:"Text",   Width:170,   SaveName:"fieldNm",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"taskNm",   	Align:"Left", 	Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"valid",   		Align:"Left", 	Edit:0},
                    {Type:"Combo",   Width:80,   SaveName:"enable",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"taskType",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"taskTypeNm",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:10,   SaveName:"description",   		Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"objId",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"fieldId",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"taskId",   	Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"mappingId",   	Align:"Left", 	Edit:0} ,
                    {Type:"Text",   Width:60,   SaveName:"taskLastSaved",   	Align:"Left", 	Edit:0} ,
                    {Type:"Text",   Width:60,   SaveName:"termsMappingNm",   	Align:"Left", 	Edit:0} ,
                    {Type:"Text",   Width:60,   SaveName:"termsLastSaved",   	Align:"Left", 	Edit:0}
                ];
                    
        InitColumns(cols);
        
        SetColProperty("valid", 	{ComboCode:"1|0", 	ComboText:"N|Y"});
        SetColProperty("enable", 	{ComboCode:"1|0", 	ComboText:"N|Y"});

        InitComboNoMatchText(1, "");
        
        
	     SetColHidden("valid"	,1);
	     SetColHidden("enable"	,1);
	     SetColHidden("taskType"	,1);
	     SetColHidden("taskTypeNm"	,1);
	     SetColHidden("description"	,1);
	     SetColHidden("objId"	,1);
	     SetColHidden("fieldId"	,1);
	     SetColHidden("taskId"	,1);
	     SetColHidden("mappingId"	,1);
	     SetColHidden("taskLastSaved"	,1);
	     SetColHidden("termsMappingNm"	,1);
	     SetColHidden("termsLastSaved"	,1);
		
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function initGridSrc()
{
    
    with(source_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [ {Text:"<s:message code='META.ADD.ETCL.WORK.1'/>"}                    
                ];
        //No.|OBJID|SRC_ID|SRC_FIELD_ID|소스(테이블)명|필드(컬럼)명
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq"		, Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"objId"		, Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"srcId"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"srcFieldId"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"srcTblNm"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"srcFieldNm"	, Align:"Left"  , Edit:0}
                   
                ];
                    
        InitColumns(cols);

        
        SetColHidden("objId"	,1);
        SetColHidden("srcId"	,1);
        SetColHidden("srcFieldId"	,1);
		
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(source_sheet);    
    //===========================
   
}

function initGridTgt()
{
    
    with(target_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [ {Text:"<s:message code='META.ADD.ETCL.WORK.2'/>"} /* 타겟(테이블)명 */                    
                ];
        //No.|OBJID|TARGET_ID|TARGET_FIELD_ID|필드(테이블)명|필드(컬럼)명
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq"		, Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"objId"		, Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"tgtId"		, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"tgtFieldId"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:300,   SaveName:"tgtTblNm"	, Align:"Left"  , Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"tgtFieldNm"	, Align:"Left"  , Edit:0}
                   
                ];
                    
        InitColumns(cols);

        
        SetColHidden("objId"	,1);
        SetColHidden("tgtId"	,1);
        SetColHidden("tgtFieldId"	,1);
		
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(target_sheet);    
    //===========================
   
}



function doAction(sAction)
{
        
    switch(sAction)
    {
        
                    
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/effect/getEtclTaskList.do" />", param);
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
	

	//영역정보 탭 설정
	$("form[name=frmField] #taskNm").val(param.taskNm);
	$("form[name=frmField] #taskTypeNm").val(param.taskTypeNm);
	$("form[name=frmField] #lastSaveDay").val(param.taskLastSaved);
	$("form[name=frmField] #valid").val(grid_sheet.GetCellText(row, "valid"));
	$("form[name=frmField] #enable").val(grid_sheet.GetCellText(row, "enable"));
	$("form[name=frmField] #desctiption").val(param.desctiption);
	
	
	
	//용어정보 탭 설정
	$("form[name=frmTerms] #termsMappingNm").val(param.termsMappingNm);
	$("form[name=frmTerms] #termsLastSaved").val(param.termsLastSaved);
	

	
	//패밍ID가 존재하는 경우에만 조회. 없는 경우에는 전체조회가 되므로 조회하지 않는다.
	if(param.mappingId != "")
	{
		var param1 = "mappingId=" + param.mappingId;
		
		//소스정보 탭 설정
		source_sheet.DoSearch("<c:url value="/meta/effect/getEtclTaskSource.do" />", param1);
		
		//타겟정보 탭 설정
		target_sheet.DoSearch("<c:url value="/meta/effect/getEtclTaskTarget.do" />", param1);			
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
	    <div class="menu_title"><s:message code="ETCL.JOB.INQ" /></div> <!-- ETCL 작업조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='ETCL.JOB.INFO.INQ'/>"> <!-- ETCL작업정보조회 -->
                   <caption><s:message code="JOB.NM" /></caption> <!-- 작업 이름 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   </colgroup>
                   
                   <tbody>                            
                       
                       <tr> 
                           <th scope="row"><label for="fieldNm"><s:message code="TRRT.NM" /></label></th> <!-- 영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="fieldNm" id="fieldNm" class="wd60p" />
                                </span>
                            </td>
                       </tr>
                       <tr> 
                           <th scope="row"><label for="taskNm"><s:message code="JOB.NM2" /></label></th> <!-- 작업명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="taskNm" id="taskNm" class="wd60p" />
                                </span>
                            </td>
                       </tr>
                       <tr>                               
                          <th scope="row" class=""><label for="taskType"><s:message code="JOB.TXT" /></label></th> <!-- 작업유형 -->
	                      <td >
				            <select id="taskType" class="" name="taskType">
				             <option value="">----<s:message code="CHC" />----</option> <!-- 선택 -->
				            </select>
				         
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "342px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
	</div>
</div>
<!-- 우측 DIV -->
<div style="float:left; width: 65%;">
	<div>
		<div class="stit"><s:message code="MSG.JOB.INFO.CHC" /></div> <!-- 작업정보 -->
        <div style="clear:both; height:5px;"><span></span></div>
        <!--  <div id="cy" style="border:1px solid #ddd; width: 100%; height: 500px;"></div> -->
        
    <!-- 우측위치 -->
        
	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs" style="border:1px solid #ddd; width: 100%; height: 467px;">
	  <ul>
	    <li id="tab-1" ><a href="#tabs-1"><s:message code="TRRT.INFO" /></a></li> <!-- 영역정보 -->
	    <li id="tab-2" ><a href="#tabs-2"><s:message code="TERMS.INFO" /></a></li> <!-- 용어정보 -->
	    <li id="tab-3" ><a href="#tabs-3"><s:message code="SOUR.INFO" /></a></li> <!-- 소스정보 -->
	    <li id="tab-4" ><a href="#tabs-4"><s:message code="TARG.INFO" /></a></li> <!-- 타겟정보 -->
	  </ul>

	  
	   <div id="tabs-1">
		<!-- 영역정보 시작 -->
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
	                                <th scope="row" class=""><label for="taskNm"><s:message code="JOB.NM2" /></label></th> <!-- 작업명 -->
	                                <td colspan="3" ><span class="" ><input type="text" id="taskNm" name="taskNm" style="width:100%;" readonly /></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="taskTypeNm"><s:message code="JOB.TXT" /></label></th> <!-- 작업유형 -->
	                                <td colspan="1"><span class="" ><input type="text" id="taskTypeNm" name="taskTypeNm" readonly/></span></td>

	                                <th scope="row" class=""><label for="lastSaveDay"<s:message code="LAST.STRG.DATE" /></label></th> <!-- 최종저장날짜 -->
	                                <td colspan="1"><span class="" ><input type="text" id="lastSaveDay" name="lastSaveDay" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="valid">IS_VALID</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="valid" name="valid" readonly/></span></td>

	                                <th scope="row" class=""><label for="enable">IS_ENABLED</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="enable" name="enable" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="desctiption"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><span class="" ><input type="text" id="desctiption" name="desctiption" readonly/></span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                </fieldset>
		</div>
	            </form>
		<!-- 영역정보 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>
	  </div> <!-- tabs-1 종료 -->
	  
	  
	  <div id="tabs-2">
		<!-- 용어정보 시작 -->
	         <form name="frmTerms" id="frmTerms" method="post" >
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="terms_form_div">
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
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" class=""><label for="termsMappingNm"><s:message code="MAPG.NM" /></label></th> <!-- 매핑명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="termsMappingNm" name="termsMappingNm" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="termsLastSaved"><s:message code="LAST.STRG.DATE" /></label></th> <!-- 최종저장날짜 -->
	                                <td colspan="1"><span class="" ><input type="text" id="termsLastSaved" name="termsLastSaved" readonly/></span></td>

	                          
	                            </tr>
	                          
	                        </tbody>
	                    </table>
	                </div>
	                </fieldset>
		</div>
	            </form>
		<!-- 입력폼 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>
	  </div> <!-- tabs-2 종료 -->
	  
	  
	  <div id="tabs-3">
	  	<div style="clear:both; height:5px;"><span></span></div>
	        
		<!-- 그리드 입력 입력 -->
		<div id="grid_02" class="grid_01">
		     <script type="text/javascript">createIBSheet("source_sheet", "100%", "415px");</script>            
	    </div>
	  </div> <!-- tabs-3 종료 -->
	  
	 <div id="tabs-4">
	  	<div style="clear:both; height:5px;"><span></span></div>
	        
		<!-- 그리드 입력 입력 -->
		<div id="grid_03" class="grid_01">
		     <script type="text/javascript">createIBSheet("target_sheet", "100%", "415px");</script>            
	    </div>
	  </div> <!-- tabs-4 종료 -->
	  
	 </div> <!-- tabs 설정 -->
        
        
        
	</div>

</div>


	

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>