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
<title>정보시스템 관리</title><!--진단대상 DBMS 조회-->



<script type="text/javascript">

var interval = "";

$(document).ready(function() {
	//그리드 초기화 
	initGrid();

	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	//저장 버튼 hidden
	$("#btnSave").click(function() { doAction("Save"); }).show();
	
});

$(window).load(function() {
	
});


$(window).resize(
    function(){
    }
);

EnterkeyProcess("Search");

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "No.|상태|선택|DB접속대상ID|DB접속대상물리명|DB접속대상논리명|DBMS종류|DBMS버전|접속대상연결URL|정보시스템";
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];


        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:0, Hidden:1, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgLnm",    	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:100,  SaveName:"dbmsTypCd",    	Align:"Center", Edit:0},
                    {Type:"Combo",   Width:100,  SaveName:"dbmsVersCd",    	Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"connTrgLnkUrl",    	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"infoSysCd",    	Align:"Center", Edit:1}
                    

                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("dbmsTypCd", 	${codeMap.dbmsTypCdibs});
	    SetColProperty("dbmsVersCd", 	${codeMap.dbmsVersCdibs});
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
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
        	grid_sheet.DoSearch('<c:url value="/dq/dqrs/getInfoSys.do" />', param);
        	
        	break;
        	
        case "Save":
        	var param = '';
			var SaveJson = grid_sheet.GetSaveJson(0); 
        	
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/dq/dqrs/updateInfoSys.do"/>';
        	var param = "";
        	
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'진단대상DBMS조회'});
            break;
    }       
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;

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
	    <div class="menu_title">정보시스템 관리</div><!--진단대상DBMS 조회-->


	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회-->


                   <caption><s:message code="DIAG.TRGT.DBMS"/></caption><!--진단대상DBMS-->

                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS"/></label></th><!--진단대상DBMS명-->


                           <td>
                              <select id="dbConnTrgId"  name="dbConnTrgId">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
                           </td>
                           <th scope="row"><label for="dbmsTypCd"><s:message code="DBMS.KIND"/></label></th><!--DBMS종류-->
                           <td>
                           <select id="dbmsTypCd"  name="dbmsTypCd">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.dbmsTypCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
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
		<div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->       
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
		</div>
		<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    </div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>
</body>
</html>