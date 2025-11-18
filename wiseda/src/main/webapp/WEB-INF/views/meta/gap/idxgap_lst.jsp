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
<title><s:message code="IDEX.GAP.ANLY.INQ"/></title> <!-- 인덱스GAP분석 조회 -->

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");
var connTrgSchJson = ${codeMap.connTrgSch} ;

var initGridIdxYn2 =false;
var initGridIdxYn3 =false;
var initGridIdxYn4 =false;

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
                    {Text:"<s:message code='META.HEADER.IDXGAP.LST1'/>"},
                    /* No.|상태|GAP내용|접속대상ID|접속대상명|스키마ID|스키마명|테이블명|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DDL|DBC|DBC|DBC|DBC|DBC|DBC|DBC|DBC|DBC */
                    	{Text:"<s:message code='META.HEADER.IDXGAP.LST2'/>", Align:"Center"}
                    /* No.|상태|GAP내용|접속대상ID|접속대상명|스키마ID|스키마명|테이블명|인덱스ID|인덱스명|인덱스명(논리)|인덱스유형|PK여부|UQ여부|컬럼수|인덱스스페이스ID|인덱스스페이스명|테이블명|인덱스명|테이블스페이스명|PK여부|UQ여부|컬럼수|인덱스설명|인덱스에러내용|인덱스컬럼에러내용 */
                    
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Combo",   Width:60,   SaveName:"gapStatus",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"gapConts",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:10,   SaveName:"dbConnTrgId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgLnm",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:10,   SaveName:"dbSchId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"dbSchLnm",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,   SaveName:"ddlTblPnm",   Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:10,   SaveName:"ddlIdxId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:130,   SaveName:"ddlIdxPnm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:100,   SaveName:"ddlIdxLnm",   Align:"Left", Edit:0,Hidden:1},
                    {Type:"Combo",   Width:80,   SaveName:"idxTypCd",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:60,   SaveName:"ddlPkYn",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:60,   SaveName:"ddlUqYn",   Align:"Center", Edit:0},
                    {Type:"Int",   Width:80,    SaveName:"ddlIdxColCnt",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:10,   SaveName:"idxSpacId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:130,   SaveName:"idxSpacPnm",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,   SaveName:"dbcTblNm",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:130,   SaveName:"dbcIdxNm",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:120,   SaveName:"dbcTblSpacNm",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:60,   SaveName:"pkYn",   Align:"Center", Edit:0},
                    {Type:"Combo",   Width:60,   SaveName:"uqYn",   Align:"Center", Edit:0},
                    {Type:"Int",   Width:80,   SaveName:"colEacnt",   Align:"Center", Edit:0},
                    {Type:"Text",   Width:150,   SaveName:"descn",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:180,   SaveName:"ddlIdxErrDescn",   Align:"Left", Edit:0,Hidden:1},
                    {Type:"Text",   Width:180,   SaveName:"ddlIdxColErrDescn",   Align:"Left", Edit:0, Hidden:1},
                    
                    
                    
                ];
                    
        InitColumns(cols);

        SetColProperty("gapStatus", 	{ComboCode:"NML|GAP", 	ComboText:"<s:message code='NROM' />|GAP"}); /* 정상|GAP */
        SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("uqYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("ddlPkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("ddlUqYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("idxTypCd", 	${codeMap.idxTypCdibs});
	     //콤보 목록 설정...
        InitComboNoMatchText(1, "");
        
        
        SetSheetHeight(700);
        FitColWidth();
        
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
        	grid_sheet.DoSearch("<c:url value="/meta/gap/getDdlDbcIdxGapList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2ExcelBuffer(true);  
        	grid_sheet.Down2Excel({FileName:'idxgap_list',SheetName:'<s:message code="IDEX.GAP.ANLY"/>', HiddenColumn:1, Merge:1});  /*인덱스GAP분석*/
        	grid_sheet.Down2ExcelBuffer(false);   
        	
            break;
    }       
}
 



//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
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
	var param = grid_sheet.RowSaveStr(row);
		param += "&gapDcd=" + $('#frmSearch #gapDcd').val();
	url = "<c:url value="/meta/gap/idxddldbcgap_pop.do"/>";
	openLayerPop(url, "1100",  "650", param);
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
	var dbc = "&dbSchId="+grid_sheet.GetCellValue(row, "dbSchId")+"&dbcIdxNm="+grid_sheet.GetCellValue(row, "dbcIdxNm");
	var ddl = "&dbSchId="+grid_sheet.GetCellValue(row, "dbSchId")+"&ddlIdxPnm="+grid_sheet.GetCellValue(row, "dbcIdxNm");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="DB.C.IDEX.NM" /> : ' + param.dbcIdxNm; /*인덱스명*/
	$('#sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
//	grid_sub_dbc.DoSearch('<c:url value="/meta/gap/selectDbcIdxColList.do" />', dbc);
//	grid_sub_ddl.DoSearch('<c:url value="/meta/gap/selectDdlIdxColList.do" />', ddl);
    
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


function grid_sub_ddl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}  
}

function grid_sub_dbc_OnSearchEnd(code, message, stCode, stMsg) {
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

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="IDEX.INQ"/></div> <!-- 인덱스 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='IDEX.INQ'/>"> <!-- 인덱스 조회 -->
                   <caption><s:message code="IDEX.INQ"/></caption> <!-- 인덱스 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                        
                   <tr>
                   <th scope="row"><label for="subjId"><s:message code="GAP.COMPAREARE"/></label></th> <!-- GAP비교대상 -->
                             <td>
                                <select id = "gapDcd" name ="gapDcd">> 
                                    <option value ="DC"><s:message code="DEV.DDL.VS.DEV.DBC"/></option> <!-- 개발DDL VS 개발DBC -->
                                    <option value ="TC"><s:message code="TEST.DDL.VS.TEST.DBC"/></option> <!-- 테스트DDL VS 테스트DDL -->
                                    <option value ="RC"><s:message code="OPR.DDL.VS.OPR.DBC"/></option> <!-- 운영DDL VS 운영DBC -->
<!--                                     <option value ="CC">개발DBC VS 운영DBC</option> -->
                                </select> 
                            </td>
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
				           <th scope="row"><label for="ddlTblPnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                           <td><input type="text" name="ddlTblPnm" id="ddlTblPnm" class="wd200" /></td>
                           <th scope="row"><label for="gapStatus"><s:message code="STS" /></label></th> <!-- 상태 -->
                            <td>
                                <select id="gapStatus" class="" name="gapStatus">
                                        <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
                                        <option value="NML"><s:message code="NROM" /></option> <!-- 정상 -->
                                        <option value="GAP">GAP</option>
                                 </select>
                            </td>
                            </tr>
                            <tr>
                           <th scope="row"><label for="dbcIdxNm"><s:message code="IDEX.NM" /></label></th> <!-- 인덱스명 -->
                            <td colspan="3">
                                <span class="input_file">
                                <input type="text" name="dbcIdxNm" id="dbcIdxNm" class="wd200" />
                                </span>
                            </td>
<!--                            <th scope="row"><label for="dbcIdxColNm">인덱스컬럼명</label></th> -->
<!--                             <td> -->
<%--                                 <span class="input_file"> --%>
<!--                                 <input type="text" name="dbcIdxColNm" id="dbcIdxColNm" class="wd60p" /> -->
<%--                                 </span> --%>
<!--                             </td> -->
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "700px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
<!-- 	<div id="tabs"> -->
<!-- 	  <ul> -->
<!-- 	    <li><a href="#tabs-1">상세정보</a></li> -->
	    
<!-- 	  </ul> -->
<!-- 	  <div id="tabs-1"> -->
<!-- 			<! 	상세정보 ajax 로드시 이용 --> 
<%-- 			<%@include file="idxgap_dtl.jsp" %> --%>
			
<!-- 			<!-	상세정보 ajax 로드시 이용 END --> 
<!-- 	  </div> -->
	  
<!-- 	 </div> -->
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>