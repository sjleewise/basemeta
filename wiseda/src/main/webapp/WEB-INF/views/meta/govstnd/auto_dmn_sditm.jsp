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
<title></title>

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch};
// var sysareaJson = ${codeMap.sysarea} ;	//시스템영역
var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
      
// 		create_selectbox(sysareaJson, $("#sysAreaId"));
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
        
    	//추가 Event Bind
    	$("#btnTreeNew").show();
    	
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  }).show();
        
        // 저장 Event Bind
        $("#btnSave").click(function(){ doAction("Save"); }).show();
        
        // 삭제 Event Bind
    	$("#btnDelete").click( function(){ 	doAction("Delete"); }).show();
  
        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).show();
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
    	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
	   	
// 	   	getInfoSys();
});

function getInfoSys(){
	
	var ajaxUrl = "<c:url value="/meta/govstnd/getInfoSys.do" />";
	
	$.ajax({
		url: ajaxUrl,
		async: false,
		type: "POST",
		contentType: 'application/json',
		dataType: 'json',
		
		success: function(jsonData){
			
			$("#frmSearch #infoSysCd").find("option").remove().end();
			
			$("#frmSearch #infoSysCd").append("<option value=\"\"><s:message code='WHL' /></option>");
			
			$.each(jsonData, function(index,item){
				var infoSysCd = item.INFO_SYS_CD;
				var infoSysNm = item.INFO_SYS_NM;
				
// 				console.log(infoSysNm);
				
				$("#frmSearch #infoSysCd").append("<option value="+ infoSysCd +">"+ infoSysNm +"</option>");
			});
		},
		error: function (jqXHR, textStatus, errorThrown) {
			
		}
	});
}

$(window).on('load',function() {

// 	initGrid();
});


$(window).resize(
);


// function initGrid()
// {
    
//     with(grid_sheet){
    	
//     	var cfg = {SearchMode:2,Page:100};
//         SetConfig(cfg);
        
// 		var headers = [
// 						{Text:"상태|선택|검증결과|시스템영역|DB물리명|스키마물리명|표준용어ID|표준용어논리명|표준용어물리명|도메인ID|도메인명|데이터타입|데이터길이|데이터크기|출처|엑셀여부", Align:"Center"}
// 					];
// 					//No.|표준분류|표준용어ID|표준용어논리명|표준용어물리명|논리명기준구분|물리명기준구분|도메인ID|도메인논리명|도메인물리명|데이터타입|데이터길이|데이터소수점길이|도메인그룹|인포타입|인포타입변경여부|암호화여부|전체영문의미|설명|요청번호|요청일련번호|등록유형코드|버전|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
			
// 			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
// 			InitHeaders(headers, headerInfo); 
// 			var cols = [
// 						{Type:"Status", Width:60,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:0}, // 상태
// 						{Type:"CheckBox", Width:40, SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0}, // 선택
						
// 						{Type:"Text",	Width:100,	SaveName:"vrfRmk",		Align:"Center", Edit:0}, // 검증결과
						
// 						{Type:"Combo",	Width:100,	SaveName:"sysAreaId",	Align:"Left", Edit:1, Hidden:0}, // 시스템물리명
						
// 						{Type:"Text",   Width:120,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:1}, // DB물리명
// 						{Type:"Text",   Width:120,  SaveName:"dbSchPnm",   	Align:"Left", Edit:1}, // 스키마물리명
				
// 						{Type:"Text",   Width:100,  SaveName:"sditmId",   	Align:"Left", Edit:0, Hidden:1},
// 						{Type:"Text",   Width:120,  SaveName:"sditmLnm",   	Align:"Left", Edit:1}, // 표준항목논리명
// 						{Type:"Text",   Width:120,  SaveName:"sditmPnm",   	Align:"Left", Edit:1}, // 표준항목물리명
						
// 						{Type:"Text",   Width:100,  SaveName:"dmnId",   	Align:"Left", Edit:0, Hidden:1}, // 도메인ID
// 						{Type:"Text",   Width:120,  SaveName:"dmnNm",   	Align:"Left", Edit:1, Hidden:0}, // 도메인명

// 						{Type:"Text",   Width:100,  SaveName:"dataType",   	Align:"Left", Edit:1}, // 데이터타입
// 						{Type:"Text",   Width:80,   SaveName:"dataLen",   	Align:"Right", Edit:1}, // 데이터길이
// 						{Type:"Text",   Width:80,   SaveName:"dataScal",   	Align:"Right", Edit:1}, // 데이터크기
						
// 						{Type:"Text",	Width:100,	SaveName:"dicOrgn",		Align:"Center",	Edit:1}, // 출처
						
// // 						{Type:"Date",	Width:100,	SaveName:"regDtm",		Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, // 등록일시
// 						{Type:"Text",	Width:80,	SaveName:"excYn",		Align:"Center", Edit:1, Hidden:1, DefaultValue:"Y"} // 엑셀여부
						
// 					];
                    
//         InitColumns(cols);
      
//         FitColWidth();  
        
//         //SetExtendLastCol(1);    
//         SetColProperty("sysAreaId", ${codeMap.sysareaibs});
//     }
    
//     //==시트설정 후 아래에 와야함=== 
//     init_sheet(grid_sheet);    
//     //===========================
   
// }
		 
function doAction(sAction)
{
        
    switch(sAction)
    {
    	case "New" :
    		
    		grid_sheet.DataInsert(0);
    		grid_sheet.SetCellValue(1, "excYn", "N");
    		
    		break;
    		
		case "Search" :
			//그리드 초기화 한다.
			//initDtlGrids();
			
			var url = '<c:url value="/meta/govstnd/getdiagSditmList.do" />';
			var param = $('#frmSearch').serialize();
			
// 			console.log(param);
			
			grid_sheet.DoSearch(url, param);
			
			break;
			
        case "Down2Excel" :  //엑셀내려받기
		    //보여지는 컬럼들만 엑셀 다운로드          
		    var downColNms = "";
		      
	     	for(var i=0; i<=grid_sheet.LastCol();i++ ){
	     		if(grid_sheet.GetColHidden(i) != 1){
	     			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
	     		}
	     	}
	     	
            //grid_sheet.Down2Excel({HiddenColumn:1,DownCols:downColNms, Merge:1});
            
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'진단표준용어조회'});
            
            break;
            
        case "LoadExcel" :  //엑셀업로
        	
            grid_sheet.LoadExcel({Mode:'HeaderMatch', Append:0});
        
            break;
            
        case "Save" :
        	
        	ibsSaveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(ibsSaveJson.Code == "IBS010") return;
        	
        	if(ibsSaveJson.data.length == 0) {
        		showMsgBox("INF", "<s:message code="ERR.CHKSAVE" />");
				return;
        	}
        	
        	var url = '<c:url value="/meta/govstnd/addDiagSditmList.do" />';
        	var param = '';
        	
        	
        	IBSpostJson2(url, ibsSaveJson, param, ibscallback);
        	
        	break;
        	
        case "Delete" :
        	
        	var url ='<c:url value="/meta/govstnd/deleteDiagSditmList.do" />';
        	var param = "";
        	
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(DelJson.data.length == 0) {return;}
        	
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	
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
//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

}

function postProcessIBS(res) {
	switch(res.action) {
	//요청서 삭제 후처리...
	case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			doAction("Search");
	
		break;
	//요청서 단건 등록 후처리...
	case "<%=WiseMetaConfig.IBSAction.REG%>" :
		
		doAction("Search");
		
		break;
	//요청서 여러건 등록 후처리...
	case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
		doAction("Search");
		
		break;
	
	default : 
		// 아무 작업도 하지 않는다...
		break;
		
}

}

function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg,Response) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />",null,null,Response);
		return;
	}
}

//엑셀업로드일 경우 Y
function grid_sheet_OnLoadExcel(result) {
	if(result) {
		if(grid_sheet.RowCount() != 0) {
			for(var i=1; i<=grid_sheet.RowCount(); i++) {
				if(grid_sheet.GetCellValue(i, "excYn") == null || grid_sheet.GetCellValue(i, "excYn") == "") {
					grid_sheet.SetCellValue(i, "excYn", "Y");
				}
			}
		}
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- <!-- 메뉴 메인 제목 --> 
<!-- <div class="menu_subject"> -->
<!-- 	<div class="tab"> -->
<%-- 	    <div class="menu_title"><s:message code="STRD.TERMS.INQ" /></div> <!-- 표준용어 조회 --> --%>
<!-- 	</div> -->
<!-- </div> -->
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
<div class="stit"><s:message code="STRD.COND" /></div> <!-- 자동생성 조건 -->

        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.TERMS.INQ' />"> <!-- 표준용어조회 -->
                   <caption><s:message code="STRD.TERMS.INQ.FORM" /></caption> <!-- 표준용어 검색폼 -->
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:80%;" />                 
                   </colgroup>
                   
                   <tbody>                     
                   		<tr>               
							<th scope="row"><label for="dbConnTrgId">DBMS/스키마명</label></th> <!-- DBMS/스키마명 -->
							
							<td>
								<select class = "wd100" id="dbConnTrgId" name="dbConnTrgId">
									<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					            <select class = "wd100" id="dbSchId" name="dbSchId">
					            	<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					       
					        </td>              
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
<%--         <tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" /> --%>
			<button class="btn_search" id="btnSearch">조회</button>
			<button class="btn_autoCre" id="btnAutoCre">자동생성</button>
			
</div>
<div style="clear:both; height:10px;"><span></span></div>
<div style="clear:both; height:10px;"><span></span></div>
<div style="clear:both; height:10px;"><span></span></div>
 
 <div id="tabs">
	<ul>
	<li><a href="#tabs-1">표준용어</a></li> <!-- 표준용어 상세정보 -->
	<li><a href="#tabs-2">도메인</a></li> <!-- 표준용어 상세정보 -->
	


	</ul>
<%-- 	<c:if test="${waqMstr.rqstStepCd!='A' }">  --%>
	<!-- 	변경정보 --> 
	<div id="tabs-1"> 
		<%@include file="../../meta/govstnd/autoSditm_lst.jsp" %> 
	</div>
	<div id="tabs-2"> 
		<%@include file="../../meta/govstnd/autoDmn_lst.jsp" %> 
	</div>

</div>
 
 

	</div>
</body>
</html>