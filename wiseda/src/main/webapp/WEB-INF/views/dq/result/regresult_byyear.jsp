<!DOCTYPE html>
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
<title><s:message code="DIAG.TRGT.DB.MS.INQ" /></title><!--진단대상DBMS 조회-->



<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");
var interval = "";

$(document).ready(function() {
	//그리드 초기화 
// 	initGrid();
	
	//그리드 사이즈 조절 초기화...		
	bindibsresize();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	//$( "#tabs" ).tabs();
	//그리드 초기화 
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	//조회버튼 hidden
	$("#btnSave").click( function(){ doAction("Save"); }).show();
	
// 	$("#expRsnCntnApply").click(function(){ doAction("Apply");  }).show();
	
// 	$("#expRsnCntDelete").click(function(){ doAction("Delete");  }).show();
	
 	$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); }).hide();
	
	//삭제버튼 
	$("#btnDelete").click( function(){ doAction("Delete"); }).show();
	
    // 추가 Event Bind
    $("#btnNew").click(function(){ doAction("New");  }).show();
	

	//doAction("Search");
	
});

$(window).on('load',function() {
	initGrid();
});

$(window).resize(
    function(){

    	setibsheight($("#grid_01"));

    	//그리드 가로 스크롤 방지
    	//grid_sheet.FitColWidth();
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
 
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.RESULT.RGST'/>", Align:"Center"}
                ];
            
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:60,   SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                	{Type:"CheckBox", Width:80, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
                	{Type:"Text",     Width:60,  SaveName:"yrDqiId",    	Align:"Left", Edit:1,Hidden:1}, 
                	{Type:"Text",     Width:90,  SaveName:"evalYr",    	Align:"Left", Edit:1 ,KeyField:1}, //평가연도
                    {Type:"Combo",     Width:200,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:1,Hidden:0,KeyField:1}, //dbms id    
                    {Type:"Text",     Width:200,  SaveName:"dataStndDfs",    	Align:"Left", Edit:1,KeyField:1}, //데이터 표준확산                  
                    {Type:"Text",     Width:200,  SaveName:"dataStrucStab",    	Align:"Left", Edit:1,KeyField:1},   // 데이터 구조 안정화            
                    {Type:"Text",     Width:200,  SaveName:"dataLinkMng",      Align:"Left", Edit:1,KeyField:1}  ,  //데이터 연계 관리    
                    {Type:"Text",     Width:200,  SaveName:"dqi",      Align:"Left", Edit:1,KeyField:1}  ,  //데이터 품질진단         
                    {Type:"Text",     Width:200,  SaveName:"dataResAct",      Align:"Left", Edit:1,KeyField:1}, //품질진단 결과조치    
                    {Type:"Text",     Width:200,  SaveName:"dataErrRate",      Align:"Left", Edit:1,KeyField:1}, //데이터 오류율    
                    {Type:"Text",     Width:500,  SaveName:"regTyp",      Align:"Left", Edit:1, Hidden:1} ,//데이터 오류율    
                    {Type:"Text",     Width:500,  SaveName:"strDtm",      Align:"Left", Edit:1, Hidden:1}, 
                    {Type:"Text",     Width:500,  SaveName:"expDtm",      Align:"Left", Edit:1, Hidden:1} 
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
      	SetColProperty("dbConnTrgId", ${codeMap.connTrgDbmsibs});
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
//         SetExtendLastCol(1);    
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
        	
// 			if($("#dbConnTrgId").val() == ""){
// 				showMsgBox("ERR", "DBMS명을 입력하세요.");
// 				return;
// 			}
			
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch('<c:url value="/dq/result/yrdqireg/getRegTbl.do" />', param);
        	break;
       
        case "Save":
        	//TODO 공통으로 처리...
         	//var SaveJson = grid_sheet.GetSaveJson(0,"ibsCheck");  //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        //	var ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...

        	var SaveJson = grid_sheet.GetSaveJson(0);
			if(SaveJson.data.length == 0) return;	
        
            var url = "<c:url value="/dq/result/yrdqireg/regYrDqiTbl.do"/>";
        	var param = "";
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":
 	    	grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        	break;
        	
        	
        case "Delete":
        	
			delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
		//	if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/dq/result/yrdqireg/delYrDqi.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
			
			break;
			
        case "New":
        	var row = grid_sheet.DataInsert(-1);
        	break;
    }       
}
 



function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
	if(row < 1) return;
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

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.action) {
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
				doAction("Search");    		
			break;
			//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
		
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DIAG.TRGT.DB.MS.INQ" /></div><!--진단대상DBMS 조회-->

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
                      <th scope="row" ><label for="dbConnTrgId"><s:message code="DB.MS" /></label></th>
	                    <td>
				         <select id="dbConnTrgId"  name="dbConnTrgId">
								  <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>          
						</td>  
                           <th scope="row"><label for="evalYr">평가연도</label></th>
                           <td>
                           		<input type="text" name="evalYr" id="evalYr" />
                           </td>
                       </tr>
                    
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:20px;"><span></span></div>
            
           
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
          <div style="clear:both; height:5px;"><span></span></div>
         <!-- 조회버튼영역  -->
        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> 
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG"/></button> <!-- 저장 -->   
			    <button class="btn_rqst_new2" id="btnNew" 	name="btnNew"><s:message code="ADDT"/></button> <!-- 추가 -->   
			    <button class="btn_delete" id="btnDelete" name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
 	            <button class="btn_excel_down" id="btnExcelLoad" name="btnExcelLoad">엑셀 일괄등록</button>                     
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	
</body>
</html>