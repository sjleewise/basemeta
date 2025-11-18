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
<title>시퀀스 GAP분석 조회</title>

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSch} ;
var initGridYn2 = false;

$(document).ready(function() {
        $("#grid_02").hide();                     
		//달력팝업 추가...
	 	$( "#searchBgnDe" ).datepicker();
		$( "#searchEndDe" ).datepicker();
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ 
        	doAction("Search");  
        	
        });
        $("#btnDelete").hide();
        $("#btnTreeNew").change(function(){}).hide();
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

    	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
	   	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
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
        var headtext1 =  "No.|상태|GAP내용|주제영역ID|주제영역명|DBID|DB명|DB논리명|스키마ID|Owner명|스키마논리명"
                        +"|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL"
                        +"|DBC|DBC|DBC|DBC|DBC|DBC|DBC|DBC|DBC";
        var headtext2 =  "No.|상태|GAP내용|주제영역ID|주제영역명|DBID|DB명|DB논리명|스키마ID|Owner명|스키마논리명"
        				+"|DDL시퀀스ID|DDL시퀀스명|DDL시퀀스오류내용|MIN VALUE|MAX VALUE|INCREMENT BY|CYCLE여부|ORDER여부|CACHE SIZE"
                        +"|DBC시퀀스ID|DBC시퀀스명|DBC시퀀스오류내용|MIN VALUE|MAX VALUE|INCREMENT BY|CYCLE여부|ORDER여부|CACHE SIZE";
        var headers = [
                    {Text: headtext1},
                    	{Text:headtext2, Align:"Center"}
                    
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq"			,Align:"Center", Edit:0},
                    {Type:"Combo",  Width:40,   SaveName:"gapStatus"		,Align:"Center", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"gapConts"			,Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"subjId"			,Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"fullPath"			,Align:"Left", Edit:0, Hidden:1},

                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgId"		,Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgPnm"		,Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgLnm"		,Align:"Left", Edit:0, Hidden:1},
                    
                    {Type:"Text",   Width:80,   SaveName:"dbSchId"			,Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"dbSchPnm"			,Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbSchLnm"			,Align:"Left", Edit:0, Hidden:1},

                    {Type:"Text",   Width:80,   SaveName:"ddlSeqId"			,Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"ddlSeqNm"			,Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"ddlSeqErrDescn"	,Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:70,   SaveName:"ddlminval"		,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"ddlmaxval"		,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"ddlincby"			,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"ddlcycYn"			,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"ddlordYn"			,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"ddlcacheSz"		,Align:"Left", Edit:0, Hidden:0},
                    
                    {Type:"Text",   Width:80,   SaveName:"dbcSeqId"			,Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"dbcSeqNm"			,Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbcSeqErrDescn"	,Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:70,   SaveName:"dbcminval"		,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"dbcmaxval"		,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"dbcincby"			,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"dbccycYn"			,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"dbcordYn"			,Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:70,   SaveName:"dbccacheSz"		,Align:"Left", Edit:0, Hidden:0}
                    
                ]; 

        InitColumns(cols);

        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"정상|GAP"});
        
	     //콤보 목록 설정...
        InitComboNoMatchText(1, "");
        
        SetSheetHeight(700);
      
        FitColWidth();
        
        //SetExtendLastCol(1);    
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
        	var gapDcd = $("#gapDcd").val();

        	grid_sheet.DoSearch("<c:url value="/meta/gap/getSeqDdlDbcGapList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2ExcelBuffer(true);  
        
        	if(grid_sheet.SearchRows()>0){
        		grid_sheet.Down2Excel({FileName:'시퀀스GAP분석',SheetName:'시퀀스GAP분석', HiddenColumn:1, Merge:1}); 
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
	return;

	/* var param = grid_sheet.RowSaveStr(row);
	var url = "";
	param += "&"+$("#gapDcd").val();
		url = "<c:url value="/meta/gap/seqddldbcgap_pop.do"/>";
	openLayerPop(url, "1100",  "650", param); */
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
// 	if(grid_sheet.GetColEditable(col)) return;
// 	//alert("상세정보 조회 가능"); return;

// 	//tblClick(row);
	
// 	//선택한 상세정보를 가져온다...
 	var param =  grid_sheet.GetRowJson(row);
 	var dbc = "&dbSchId="+grid_sheet.GetCellValue(row, "dbSchId")+"&dbcTblNm="+grid_sheet.GetCellValue(row, "dbcTblNm");
 	var ddl = "&ddlTblId="+grid_sheet.GetCellValue(row, "ddlTblId");
 	var pdm = "&pdmTblId="+grid_sheet.GetCellValue(row, "pdmTblId");

// 	//선택한 그리드의 row 내용을 보여준다.....
//  	var tmphtml = '테이블명 : ' + param.pdmTblPnm;
//  	$('#sel_title').html(tmphtml);
	
// 	//메뉴ID를 토대로 조회한다....
 	//col_sheet.DoSearch("<c:url value="/meta/model/getgappdmcollist.do" />", pdm);
 	//grid_sub_ddltblcollist.DoSearch('<c:url value="/meta/ddl/ajaxgrid/ddltblcollistgap_dtl.do" />', ddl);
 	//grid_sub_dbctblcollist.DoSearch('<c:url value="/meta/dbc/ajaxgrid/dbctblcollist_dtl.do" />', dbc);
    
}


function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">주제영역 조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <input type="hidden" id="bpdmRow" name="bpdmRow" readonly="readonly"/>
            <input type="hidden" id="bddlRow" name="bddlRow" readonly="readonly"/>
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역조회">
                   <caption>테이블 이름</caption>
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
                           <th scope="row"><label for="subjId">GAP비교대상</label></th>
                             <td>
                                <select id = "gapDcd" name ="gapDcd">
                                    <option value ="DC">개발DDL VS 개발DBC</option>
                                    <option value ="TC">테스트DDL VS 테스트DBC</option>
                                    <option value ="RC">운영DDL VS 운영DBC</option>
                                    <!-- <option value ="CC">개발DBC VS 운영DBC</option> -->
                                </select> 
                            </td> 
<!--                            <th scope="row"><label for="subjId">주제영역명</label></th>
                            <td>
                            <input type="hidden" id="subjId" name="subjId" readonly="readonly"/>
							<input type="text" class="wd60p" id="fullPath" name="fullPath" readonly="readonly"/>
						 	<button class="btnSearchPop" id="subjSearchPop">검색</button>
					        <button class="btnDelPop" id="subjSearchDel" >삭제</button>
                            </td> -->
                           
                            <th scope="row" class=""><label for="dbSchId">DB/스키마정보</label></th>
	                      <td colspan="3">
				            <select id="dbConnTrgId" class="" name="dbConnTrgId">
				             <option value="">----선택----</option>
				            </select>
				            <select id="dbSchId" class="" name="dbSchId">
				             <option value="">----선택----</option>
				             </select>
				           </td>
                       </tr>
                       <tr> 
                           <th scope="row"><label for="ddlSeqNm">시퀀스명</label></th>
                           <td><input type="text" name="ddlSeqNm" id="ddlSeqNm" class="wd60p" /></td>

                            <th scope="row"><label for="searchBgnDe">승인일자</label></th>
                            <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80"/>
                              - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80"/></td>
                           <th scope="row"><label for="gapStatus">상태</label></th>
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
            
        <div class="tb_comment"><s:message  code='ETC.COMM2' /></div>
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
	</div>

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title">주제영역명 : <span></span></div>
	</div>

<div style="clear:both; height:5px;"><span></span></div>
</body>
</html>