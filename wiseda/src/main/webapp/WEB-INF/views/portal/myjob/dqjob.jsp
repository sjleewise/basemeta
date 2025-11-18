<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title>등록요청조회</title>

<script type="text/javascript">

var interval = "";

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	doAction("Search");
	
});

$(window).resize(
	    
	    function(){
	                
	    	// grid_sheet.SetExtendLastCol(1);    
	    }
	);


// var ofBizType = ${codeMap.ofBizType} ;
$(document).ready(function(){


	// 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("Search");  });
            
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
    $("#btnDelete").hide();
    $("#btnTreeNew").hide();
	

  	//엔터시 조회
	EnterkeyProcess("Search");



	
	  
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	
	
});

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	
			var param = $("#frmSearch").serialize();

			$('#rqst_sel_title').html('');
			grid_sheet.DoSearch("<c:url value="/portal/myjob/ajaxgrid/dqJobList.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;


        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;


    }       
}

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.JOB'/>", Align:"Center"}
                ];
        //No.|업무규칙ID|시스템명|테이블명|업무규칙명|차수|분석건수|오류건수|원인분석현황|개선활동현황
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Text",   Width:40,  SaveName:"objId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:70,  SaveName:"dbmsEnm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:130,  SaveName:"tblNm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:130,  SaveName:"braNm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:40,  SaveName:"chasu",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:70,  SaveName:"anaActCnt",    Align:"Right", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:70,  SaveName:"errcnt",    Align:"Right", Edit:0, Hidden:0},
                    {Type:"Text",   Width:70,  SaveName:"errStatus",    Align:"Center", Edit:0},
                    {Type:"Text",   Width:70,  SaveName:"impStatus",    Align:"Center", Edit:0}
                   
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    
        //SetColHidden("rqstUserNm",1);

        InitComboNoMatchText(1, "");
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			doAction("Search");

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
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
	var rqstNo = grid_sheet.GetCellValue(row, "rqstNo");	
	if (grid_sheet.GetCellValue(row, "bizDcd") == 'DIC') {
		window.open().location.href = "../../meta/stnd/stndword_rqst.do?rqstNo="+rqstNo;	
	} 
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
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
	



}


function grid_sheet_OnSaveEnd(code, message) {

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
 <!-- 오른쪽 내용 시작 -->
<div class="right">
	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 나의업무 &gt; 품질활동 조회</div>
	<div class="stit">품질활동 조회</div>
	
	  
	<form id="frmSearch" name="frmSearch" method="post">
     <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
         <caption>
         조회조건
         </caption>
         <colgroup>
             <col style="width:10%;">
             <col style="width:15%;">
             <col style="width:10%;">
             <col style="width:15%;">
             <col style="width:15%;">
             <col style="width:10%;">
             <col style="width:15%;">
             <col style="width:10%;">
         </colgroup>
         <tr>
	         <th>시스템명</th>
	         <td>
	         <select id="dbmsEnm" name="dbmsEnm">
	         	<option value="">전체</option>
	             <c:forEach var="dbmsEnmCode" items="${dbmsEnmCode}">
	             	<option value="${dbmsEnmCode.dbmsEnm}">${dbmsEnmCode.dbmsEnm}</option>
	             </c:forEach>    
	         </select>
	         </td>
	         <th>테이블명</th>
	         <td><input type="text" id="tblNm" name="tblNm" value=""></td>
	         <th>원인분석현황</th>
	         <td>
	         <select id="errStatus" name="errStatus">
	         	 <option value="">전체</option>
	         	 <c:forEach var="errStatusImpStatusCode" items="${errStatusImpStatusCode}">
	             	<option value="${errStatusImpStatusCode.errStatus}">${errStatusImpStatusCode.errStatus}</option>
	             </c:forEach> 
	         </select>
	         </td>
	         <th>개선활동현황</th>
	         <td>
	         <select id="impStatus" name="impStatus">
	         	 <option value="">전체</option>
	            <c:forEach var="errStatusImpStatusCode" items="${errStatusImpStatusCode}">
	             	<option value="${errStatusImpStatusCode.impStatus}">${errStatusImpStatusCode.impStatus}</option>
	             </c:forEach> 
	         </select>
	         </td>
       	 </tr>
     </table>
    </form>
	<div style="clear:both; height:10px;"><span></span></div>
       <!-- 조회버튼영역  -->         
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
	
	<div style="clear:both; height:5px;"><span></span></div>
	       <!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	
</div>
<!-- 오른쪽 내용 끝 -->

  
</body>
</html>