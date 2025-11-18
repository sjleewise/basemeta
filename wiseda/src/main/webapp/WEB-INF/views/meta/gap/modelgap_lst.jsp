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
<title>모델GAP분석 조회</title>

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSch} ;

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
        	
        }).show();
        
        $("#btnDelete").hide();
        
        $("#btnTreeNew").change(function(){}).hide();
                      
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

        
        $('#subjSearchPop').button({   //주제영역 검색팝업 이벤트
		    icons: {
		        primary: "ui-icon-search"
		      },
		      text: false, 
		      create: function (event, ui) {
	//	     	  $(this).addClass('search_button');
				  $(this).css({
					  'width': '18px',
					  'height': '18px',
					  'vertical-align': 'bottom'
					  });
		    	  
		      }
		});
        
    	//주제영역 검색 팝업 호출
    	$("#subjSearchPop").click(function(){
    		var param = ""; //$("form#frmInput").serialize();
    		openLayerPop ("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", 800, 600, param);
//     		OpenWindow("<c:url value='/meta/subjarea/popup/subjSearchPop.do' />", "dkafda",  800, 600, "yes");
//     		openSearchPop("<c:url value='/meta/model/pop/subjSearchPop.do' />", param);
        }).show();
        
    	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
	   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
    	
	   	//$( "#tabs" ).tabs();
	   	
        
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #subjLnm"), "SUBJ");
      
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	//$( "#layer_div" ).show();
});

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmSearch #subjId").val(retjson.subjId);
	$("#frmSearch #fullPath").val(retjson.fullPath);
	
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
        
        var headText1 = "<s:message code='META.HEADER.MODELGAP.LST'/>";
        //No.|상태|GAP내용|주제영역명|모델마트|모델마트|모델마트|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)|물리모델(PDM)
        var headText2 = "<s:message code='META.HEADER.MODELGAP.LST1'/>";
        //No.|상태|GAP내용|주제영역명|테이블명|테이블한글명|컬럼수|테이블ID|테이블명|테이블한글명|컬럼수|물리테이블오류내용|물리컬럼오류내용
        
      
        var headers = [
                        {Text: headText1},
                    	{Text: headText2, Align:"Center"}
                    
                	  ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Combo",   Width:60,   SaveName:"gapStatus",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:220,   SaveName:"subjLnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:10,   SaveName:"pdmTblId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,   SaveName:"pdmTblPnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"pdmTblLnm",   Align:"Left", Edit:0},
                    {Type:"Int",   Width:60,   SaveName:"pdmColCnt",   Align:"Right", Edit:0},
                    {Type:"Text",   Width:200,   SaveName:"pdmTblErrDescn",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:200,   SaveName:"pdmColErrDescn",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgLnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbSchLnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:10,   SaveName:"ddlTblId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,   SaveName:"ddlTblPnm",   Align:"Left", Edit:0},
                    {Type:"Int",   Width:60,   SaveName:"ddlColCnt",   Align:"Right", Edit:0},
                    {Type:"Int",   Width:10,   SaveName:"pdmGapColCntDdl",   Align:"Right", Edit:0, Hidden:1},
                    {Type:"Text",   Width:200,   SaveName:"ddlTblErrDescn",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:200,   SaveName:"ddlColErrDescn",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:10,   SaveName:"dbSchId",   Align:"Left", Edit:0,Hidden:1},
                    {Type:"Text",   Width:120,   SaveName:"dbcTblNm",   Align:"Left", Edit:0},
                    {Type:"Int",   Width:60,   SaveName:"dbcColCnt",   Align:"Right", Edit:0},
                    {Type:"Int",   Width:10,   SaveName:"pdmGapColCntDbc",   Align:"Right", Edit:0, Hidden:1},
                    {Type:"Int",   Width:10,   SaveName:"ddlGapColCntDbc",   Align:"Right", Edit:0, Hidden:1}
                    
                    
                    
                    
                ];
                    
        InitColumns(cols);

        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"정상|GAP"});
        
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
        	
        	if($("#dbSchId").val() == "") {
        		
        		//스키마명을 입력하세요.
        		showMsgBox("ERR","스키마명을 입력하세요."); 
        		return;
        	}
        	
        	var param = $('#frmSearch').serialize();
        	
        	grid_sub_dbctblcollist.RemoveAll();
        	
        	col_sheet.RemoveAll();
        	grid_sub_ddltblcollist.RemoveAll();         	
        	
        	grid_sheet.DoSearch("<c:url value="/meta/gap/getModelGapAnalyze.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2ExcelBuffer(true);  
        
        	if(grid_sheet.SearchRows()>0){
        		grid_sheet.Down2Excel({FileName:'gap_list',SheetName:'모델GAP분석', HiddenColumn:1, Merge:1}); 
        	}
        	if(col_sheet.SearchRows()>0){
        		col_sheet.Down2Excel({FileName:'gap_list',SheetName:'물리모델컬럼', HiddenColumn:1, Merge:1}); 
        	}
        	if(grid_sub_ddltblcollist.SearchRows()>0){
        		grid_sub_ddltblcollist.Down2Excel({FileName:'gap_list',SheetName:'DDL컬럼', HiddenColumn:1, Merge:1}); 
        	}
        	if(grid_sub_dbctblcollist.SearchRows()>0){
        		grid_sub_dbctblcollist.Down2Excel({FileName:'gap_list',SheetName:'DBC컬럼', HiddenColumn:1, Merge:1}); 
        	}
        	grid_sheet.Down2ExcelBuffer(false);   
        	
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
// 	var subjLnm = encodeURIComponent(grid_sheet.GetCellValue(row, "subjLnm"));	
	
// 	window.open().location.href = "../model/pdmtbl_lst.do?subjLnm="+subjLnm;
	
	
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
	var dbc = "&dbSchId="+grid_sheet.GetCellValue(row, "dbSchId")+"&dbcTblNm="+grid_sheet.GetCellValue(row, "dbcTblNm");
	var ddl = "&ddlTblId="+grid_sheet.GetCellValue(row, "ddlTblId");
	var pdm = "&pdmTblId="+grid_sheet.GetCellValue(row, "pdmTblId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '테이블명 : ' + param.pdmTblPnm;
	$('#sel_title').html(tmphtml);
	
	col_sheet.RemoveAll();
	grid_sub_ddltblcollist.RemoveAll();
	grid_sub_dbctblcollist.RemoveAll();
	
	//메뉴ID를 토대로 조회한다....
	if(grid_sheet.GetCellValue(row, "pdmTblId")!="")
	col_sheet.DoSearch("<c:url value="/meta/model/getpdmcollist.do" />", pdm);
	if(grid_sheet.GetCellValue(row, "ddlTblId")!="")
	grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcollist_dtl.do" />', ddl);
	if(grid_sheet.GetCellValue(row, "dbcTblNm")!="")
	grid_sub_dbctblcollist.DoSearch('<c:url value="/meta/dbc/ajaxgrid/dbctblcollist_dtl.do" />', dbc);
    
}


function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}


function col_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function grid_sub_ddltblcollist_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

function grid_sub_dbctblcollist_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />"); 
		return;
	}
}




function grid_sub_dbctblcollist_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
	var bpdmRow = $("#frmSearch #bpdmRow").val();
	var bddlRow = $("#frmSearch #bddlRow").val();
	
	if(bpdmRow > 0){
		grid_sub_ddltblcollist.SetRowFontColor(bpdmRow, "#000000");
		col_sheet.SetRowFontColor(bddlRow, "#000000");
	}
	
// 	ddl컬럼
	var ddlRow = grid_sub_ddltblcollist.FindText("ddlColPnm", grid_sub_dbctblcollist.GetCellValue(row, "dbcColNm"));
// 	물리모델
	var pdmRow = col_sheet.FindText("pdmColPnm", grid_sub_dbctblcollist.GetCellValue(row, "dbcColNm"));
	
	if(grid_sub_dbctblcollist.GetCellValue(row, "colErrConts") != ""){
		grid_sub_ddltblcollist.SetRowFontColor(ddlRow, "#FF0000");
		col_sheet.SetRowFontColor(pdmRow, "#FF0000");
	}
	
	$("#frmSearch #bpdmRow").val(pdmRow);
	$("#frmSearch #bddlRow").val(ddlRow);
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">모델GAP조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <input type="hidden" id="bpdmRow" name="bpdmRow" readonly="readonly"/>
            <input type="hidden" id="bddlRow" name="bddlRow" readonly="readonly"/>
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="모델GAP조회">
                   <caption>모델GAP조회</caption>
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="subjId">주제영역명</label></th> <!-- 주제영역명 -->
                            <td>
                            <input type="hidden" id="subjId" name="subjId" readonly="readonly"/>
							<input type="text" class="wd60p" id="fullPath" name="fullPath" readonly="readonly"/>
						 	<button class="btnDelPop" style="display:none;"></button>
						 	<button class="btnSearchPop" id="subjSearchPop" style="display:none;"></button>
                            </td>
                           
                          <th scope="row" class="th_require"><label for="dbSchId"> DBMS/스키마명</label></th>  <!-- DBMS/스키마명 -->
	                      <td colspan="3">
				            <select id="dbConnTrgId" class="" name="dbConnTrgId">
				             <option value="">선택</option>
				            </select>
				            <select id="dbSchId" class="" name="dbSchId">
				             <option value="">선택</option>
				             </select>
				           </td>
                       </tr>
                       <tr> 

                           <th scope="row"><label for="tblNm">테이블명</label></th>   <!-- 테이블명 -->
                           <td><input type="text" name="tblNm" id="tblNm" class="wd60p" /></td>

                           <th scope="row"><label for="searchBgnDe">모델승인일자</label></th>  <!-- 모델승인일자 -->
                           <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80"/>
                              - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80"/>
                           </td>
                           
                           <th scope="row"><label for="gapStatus">상태</label></th>  <!-- 상태 -->
                           <td>
                                <select id="gapStatus" class="" name="gapStatus">
                                    <option value="">선택</option>
                                    <option value="NML">정상</option>
                                    <option value="GAP">GAP</option>
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
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
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
		    <div class="selected_title">주제영역명 : <span></span></div>
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
	    <li><a href="#tabs-1">상세정보</a></li> <!-- 상세정보 -->
	    
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="modelgap_dtl.jsp" %>
			
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  
	 </div>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>