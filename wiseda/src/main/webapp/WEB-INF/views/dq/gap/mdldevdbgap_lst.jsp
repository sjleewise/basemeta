<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<head>
<title>모델개발DB GAP분석 조회</title>
<script type="text/javascript">

EnterkeyProcess("Search");  

var connTrgSchJson = ${codeMap.connTrgSch} ;         
                       
$(document).ready(function() {
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
            	        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
        // 컬럼엑셀내리기 Event Bind
        $("#btnColExcelDown").click( function(){ doAction("ColDown2Excel"); } ).show();
       
        double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
	   	
	   	$("#poiDown").click( function() {
    		doAction("poiDown");
    	}).show();
	   	
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	initColGrid();

	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	checkApproveYn($("#mstFrm"));
	
// 	setibsTabHeight($("#grid_02"), 60);
});


$(window).resize(function(){
                
	//setibsTabHeight($("#grid_02"));      
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
            
        var headtext = "<s:message code='META.HEADER.MDLDEV.DBGAP.LST'/>";
        //No.|상태|GAP상태|주제영역|테이블(물리명)|컬럼GAP개수|모델컬럼개수|DB컬럼개수|승인일시|테이블(논리명)|PDM_TBL_ID|DB_SCH_ID
       
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	     Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Combo",  Width:100,  SaveName:"gapStatus",	 Align:"Center", Edit:0},
                    {Type:"Text",   Width:280,  SaveName:"fullPath",	 Align:"Left",   Edit:0, Hidden:1},                      
                    {Type:"Text",   Width:130,  SaveName:"pdmTblPnm",    Align:"Left",   Edit:0},                     
                    {Type:"Text",   Width:100,  SaveName:"colGapCnt", 	 Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"pdmColCnt", 	 Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"dbcColCnt",    Align:"Center", Edit:0},                     
                    {Type:"Text",   Width:130,  SaveName:"aprvDtm", 	 Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm", Hidden:1},
                    {Type:"Text",   Width:130,  SaveName:"pdmTblLnm", 	 Align:"Left",   Edit:0},                    
                    {Type:"Text",   Width:80,   SaveName:"pdmTblId",   	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbSchId",   	 Align:"Left",   Edit:0, Hidden:1},
                    
                ];
                    
        InitColumns(cols);
	    
        
		SetColProperty("gapStatus", 	{ComboCode:"NOR|NTGT|NPDM|CGAP", ComboText:"정상|개발DB미존재|모델미존재|컬럼GAP"}); 		
		
				        
        InitComboNoMatchText(1, "");
        
		SetColHidden("pdmTblId"	,1);
		
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}



function initColGrid()
{
    
    with(col_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext1  = "";
        var headtext2  = "";
                        
        headtext1 += "<s:message code='META.HEADER.MDLDEV.DBGAP.LST1'/>";
        //No.|상태|GAP상태|모델  |모델      |모델    |모델  |모델      |모델|모델  |모델       |모델  |DB(개발)|DB(개발)  |DB(개발)|DB(개발)|DB(개발)  |DB(개발)|DB(개발)|DB(개발)   |DB(개발)
        headtext2 += "<s:message code='META.HEADER.MDLDEV.DBGAP.LST2'/>";
        //No.|상태|GAP상태|컬럼명|컬럼한글명|컬럼순서|PK여부|데이터타입|길이|소수점|NOTNULL여부|디폴트|컬럼명  |컬럼한글명|컬럼순서|PK여부  |데이터타입|길이    |소수점  |NOTNULL여부|디폴트  
    	        
      
    	SetMergeSheet(msHeaderOnly);
    	
    	headtext1 = headtext1.replace(/[' ']/gi,'');
    	headtext2 = headtext2.replace(/[' ']/gi,'');
    	
        var headers = [
					{Text:headtext1, Align:"Center"},   
                    {Text:headtext2, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	    Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Text",   Width:100,  SaveName:"gapStatus",	   Align:"Center", Edit:0},
					{Type:"Text",   Width:120,  SaveName:"pdmColPnm",	   Align:"Left",   Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"pdmColLnm",    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"pdmColOrd",	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"pdmPkYn",	     Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"pdmDataType",  Align:"Center", Edit:0},                     
                    {Type:"Text",   Width:80,   SaveName:"pdmDataLen",   Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"pdmDataScal",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"pdmNonulYn",   Align:"Center", Edit:0},	
                    {Type:"Text",   Width:80,   SaveName:"pdmDefltVal",  Align:"Center", Edit:0},
					{Type:"Text",   Width:120,  SaveName:"dbcColPnm",	 Align:"Left",     Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"dbcColLnm",    Align:"Left",     Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"dbcColOrd",	 Align:"Center",   Edit:0},                    
                    {Type:"Text",   Width:80,   SaveName:"dbcPkYn",      Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"dbcDataType",  Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"dbcDataLen", 	 Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"dbcDataScal",  Align:"Center",   Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbcNonulYn",   Align:"Center", Edit:0},	
                    {Type:"Text",   Width:80,   SaveName:"dbcDefltVal",  Align:"Center", Edit:0},
                    
                ];
                    
        InitColumns(cols);

        
        InitComboNoMatchText(1, "");
        
		SetColHidden("pdmTblId"	,1);
		SetColHidden("dbcTblId"	,1);
		
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(col_sheet);    
    //===========================
   
}



//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        
		case "Search":
			
			
			if($("#dbSchId").val() == ""){
				
				showMsgBox("ERR", "DB스키마정보를 입력하세요.");
				return;
			}
			
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch("<c:url value="/dq/gap/getMdlDevDbTblGapList.do" />", param); 			
			col_sheet.RemoveAll();
			
			break;
        			
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'모델개발DBC_GAP분석조회'});
            break;
                        
        case "ColDown2Excel":  //컬럼엑셀내려받기
            col_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'모델개발DBC_GAP분석컬럼조회'});
            break; 	
            
        case "poiDown":
        	
	        if($("#dbSchId").val() == ""){
				
				showMsgBox("ERR", "DB스키마정보를 입력하세요.");
				return;
			}
        	
        	//searchBox의 DBMS및 스키마명 선택된 것
        	var dbmsId=$('#dbConnTrgId option:selected').val();		//DBMS
        	var schId=$('#dbSchId option:selected').val();			//스키마명
        	var dbmsLnm=$('#dbConnTrgId option:selected').text();		//DBMS
        	var schLnm=$('#dbSchId option:selected').text();			//스키마명
        	
//         	if(!dbmsName || !schName){			//DBMS와 스키마명이 --선택-- 으로 되어있는 경우 종료
//         		showMsgBox("DBMS와 스키마명을 지정해주십시오.");
//         		return;
//         	}
        	
        	$('#dbmsId').val(dbmsId);		//선택된 DBMS의 값
        	$('#schId').val(schId);		//선택된 스키마의 값
        	$('#dbmsLnm').val(dbmsLnm);		//선택된 DBMS의 값
        	$('#schLnm').val(schLnm);		//선택된 스키마의 값
        	
//         	var tblCnt = grid_sheet.GetTotalRows();
//         	var tblNcnt = grid_sheet.GetSaveJson({StdCol:"gapStatus"}).data.length;
//         	var colCnt = 0;
//         	var colNcnt = 0;
//         	var pdmColCnt = 0;
//         	var pdmColNcnt = 0;
//         	var pdmTblCnt = grid_sheet.GetSaveJson({StdCol:"pdmTblId"}).data.length;
//         	var pdmTblNcnt = grid_sheet.GetSaveJson({StdCol:"gapStatus|pdmTblId"}).data.length;
//         	alert(grid_sheet.GetSaveJson({StdColValue:"gapStatus|pdmTblId"}).data.length);
//         	for(var i=1; i<=tblCnt; i++) {
//         		colCnt += Number(grid_sheet.GetCellValue(i, "dbcColCnt")); //DBC컬럼 총개수
//         		pdmColCnt += Number(grid_sheet.GetCellValue(i, "pdmColCnt")); //PDM컬럼 총개수
//         		colNcnt += Number(grid_sheet.GetCellValue(i, "colGapCnt")); //총 컬럼갭 개수
//         		if(grid_sheet.GetCellValue(i, "pdmColCnt") != null && grid_sheet.GetCellValue(i, "pdmColCnt") != '' && grid_sheet.GetCellValue(i, "pdmColCnt") != 0) {
//         			if(Number(grid_sheet.GetCellValue(i, "pdmColCnt")) > Number(grid_sheet.GetCellValue(i, "colGapCnt")))
//         				pdmColNcnt += Number(grid_sheet.GetCellValue(i, "colGapCnt"));
//         			else 
//         				pdmColNcnt += Number(grid_sheet.GetCellValue(i, "pdmColCnt"));
//         		}
        			
//         	}
        	
//         	$("#tblCnt").val(tblCnt);
//         	$("#tblNcnt").val(tblNcnt);
//         	$("#colCnt").val(colCnt);
//         	$("#colNcnt").val(colNcnt);
//         	$("#pdmColCnt").val(pdmColCnt);
//         	$("#pdmColNcnt").val(pdmColNcnt);
//         	$("#pdmTblCnt").val(pdmTblCnt);
//         	$("#pdmTblNcnt").val(pdmTblNcnt);
        	
        	//hidden input을 가지고있는 poiForm
        	var myForm = document.poiForm;
        	var url ='<c:url value="/dq/result/gapPoiDown.do" />';
        	
        	myForm.action=url;
        	myForm.method="post";
        	myForm.target="poiForm";
//         	myForm.submit();	//dbCodeValue, schCodeValue, sheetNum 세가지의 값을 POST로 넘김
        	
        	break;
    }       
}


//화면상의 모든 액션은 여기서 처리...
function doActionCol(sAction)
{
      
	switch(sAction)
	{
	    		
	    case "Down2Excel":  //엑셀내려받기
	        col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
	        break; 	        	   
	}       
}


//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
//	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	var fullPath = retjson.fullPath;
	var subjId   = retjson.subjId;
	
	//alert(subjId);
	
	//$("#frmSearch #fullPath").val(retjson.fullPath);
	$("#frmSearch #subjLnm").val(fullPath);
	$("#frmSearch #subjId").val(subjId);
	
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
	
// 	var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
 
// 	$("#saveCls").val("U");  //저장구분을 수정 (U) 로 변경 
	
// 	var usrId = grid_sheet.GetCellValue(row, "arrUsrId");
	
// 	$("#usrId").val(usrId);  
	   
//     //$("#frmInput").attr("action", url).submit();
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
            
    if(row < 1) return;
    
	//선택한 상세정보를 가져온다...
	var rowjson =  grid_sheet.GetRowJson(row);
	
	var fullPath    = rowjson.fullPath;  
	var arrFullPath = fullPath.split(">");
	
	var param1 = "";
	console.log(rowjson);
	param1 += "&dbcTblNm=" + rowjson.pdmTblPnm;
	param1 += "&pdmTblId=" + rowjson.pdmTblId;
	param1 += "&dbSchId="  + rowjson.dbSchId;
	param1 += "&pdmTblPnm=" + rowjson.pdmTblPnm;
	
	//alert(param1);
	
	col_sheet.DoSearch("<c:url value="/dq/gap/getMdlDevDbColGapList.do" />", param1); 		 	
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code == 0) {

		for(var i = 1; i <= grid_sheet.RowCount() ; i++) {
			
			if(grid_sheet.GetCellValue(i, "gapStatus") == "NOR") { 			
				grid_sheet.SetCellFontColor(i, "gapStatus", "#0000FF");
			} else{
				grid_sheet.SetCellFontColor(i, "gapStatus", "#FF0000");
			}			
		}
		
	} else {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
}


function col_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code == 0) {

		for(var i = col_sheet.HeaderRows(); i <= col_sheet.RowCount() + col_sheet.HeaderRows() - 1; i++) {
			
			if(col_sheet.GetCellValue(i, "gapStatus") == "NOR") { 			
				col_sheet.SetCellFontColor(i, "gapStatus", "#0000FF");
			} else{
				col_sheet.SetCellFontColor(i, "gapStatus", "#FF0000");
			}	
			
			var pdmPkYn = "N";
			
			if(col_sheet.GetCellValue(i, "pdmPkYn") != "") {
				
				pdmPkYn = col_sheet.GetCellValue(i, "pdmPkYn");
			}
			
			if(pdmPkYn != col_sheet.GetCellValue(i, "dbcPkYn")) { 			
				col_sheet.SetCellFontColor(i, "pdmPkYn", "#FF0000");
				col_sheet.SetCellFontColor(i, "dbcPkYn", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmColOrd") != col_sheet.GetCellValue(i, "dbcColOrd")) { 			
				col_sheet.SetCellFontColor(i, "pdmColOrd", "#FF0000");
				col_sheet.SetCellFontColor(i, "dbcColOrd", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmDataType") != col_sheet.GetCellValue(i, "dbcDataType")) { 			
				col_sheet.SetCellFontColor(i, "pdmDataType", "#FF0000");
				col_sheet.SetCellFontColor(i, "dbcDataType", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmDataLen") != col_sheet.GetCellValue(i, "dbcDataLen")) { 			
				col_sheet.SetCellFontColor(i, "pdmDataLen", "#FF0000");
				col_sheet.SetCellFontColor(i, "dbcDataLen", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmDataScal") != col_sheet.GetCellValue(i, "dbcDataScal")) { 			
				col_sheet.SetCellFontColor(i, "pdmDataScal", "#FF0000");
				col_sheet.SetCellFontColor(i, "dbcDataScal", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmNonulYn") != col_sheet.GetCellValue(i, "dbcNonulYn")) { 			
				col_sheet.SetCellFontColor(i, "pdmNonulYn", "#FF0000");
				col_sheet.SetCellFontColor(i, "dbcNonulYn", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmDefltVal") != col_sheet.GetCellValue(i, "dbcDefltVal")) { 			
				col_sheet.SetCellFontColor(i, "pdmDefltVal", "#FF0000"); 
				col_sheet.SetCellFontColor(i, "dbcDefltVal", "#FF0000");
			} 	
			
		}
		
	} else {
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
	    <div class="menu_title">모델개발DB GAP분석</div>
	</div>
</div>

		<form name="poiForm">
			<input type="hidden" id ="dbmsId" name = "dbmsId" value="">
			<input type="hidden" id ="schId" name = "schId" value="">
			<input type="hidden" id ="dbmsLnm" name = "dbmsLnm" value="">
			<input type="hidden" id ="schLnm" name = "schLnm" value="">
			
			<input type="hidden" id ="gap" name = "gap" value="">
			<input type="hidden" id ="tot" name = "tot" value="">
			
			<input type="hidden" id ="tblCnt" name = "tblCnt" value="">
			<input type="hidden" id ="colCnt" name = "colCnt" value="">
			<input type="hidden" id ="pdmTblCnt" name = "pdmTblCnt" value="">
			<input type="hidden" id ="pdmColCnt" name = "pdmColCnt" value="">
			<input type="hidden" id ="tblNcnt" name = "tblNcnt" value="">
			<input type="hidden" id ="colNcnt" name = "colNcnt" value="">
			<input type="hidden" id ="pdmTblNcnt" name = "pdmTblNcnt" value="">
			<input type="hidden" id ="pdmColNcnt" name = "pdmColNcnt" value="">
			
			<input type="hidden" id ="poiFlag" name = "poiFlag" value="model">
		</form>
		
<form id="frmSearch" name="frmSearch" >

  <div class="stit">검색조건</div> <!-- 검색조건 -->
  <div style="clear:both; height:5px;"><span></span></div>
	<fieldset>
	     <legend>머리말</legend>
	     <div class="tb_basic2">
	         <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="모델개발DB GAP분석">
	            <caption>모델개발DB GAP분석 검색폼</caption>
	            <colgroup>
	            <col style="width:12%;" />
	            <col style="width:35%;" />
	            <col style="width:12%;" />
	            <col style="width:35%;" />
	            </colgroup>             
	            <tbody>                            
					<tr>	 
                        <th scope="row" class="th_require"><label for="dbConnTrgId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
						<td colspan="3"><select id="dbConnTrgId" class="" name="dbConnTrgId">
				             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
				            </select>
				            <select id="dbSchId" class="" name="dbSchId">
				             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
				        	</select>
				        </td> 
					    
<%-- 					    <th scope="row"><label for="fullPath">주제영역</label></th> <!-- 주제영역 -->
	                    <td>
                             <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" class="wd340"/>
                                <input type="hidden" name="subjId" id="subjId" />
                                <button class="btnDelPop"    id="subjDelPop" >삭제</button>
		                        <button class="btnSearchPop" id="subjSearchPop">검색</button>
                             </span>
                        </td> --%>
	                    														         		
					</tr> 				
					
					<tr>	 					     						
						<th scope="row"><label for="gapStatus">GAP상태</label></th>  <!-- GAP상태 -->
	                    <td>
				            <select id="gapStatus" class="" name="gapStatus">
				             	<option value="">선택</option>
				             	<!-- <option value="NOR">정상</option>  -->
				             	<option value="CGAP">컬럼GAP</option>
				             	<option value="NPDM">모델미존재</option>
				             	<option value="NTGT">개발DB미존재</option>
				            </select> 				            
				         </td>			
				         
				         <th scope="row"><label for="pdmTblPnm">테이블명</label></th> <!-- 테이블명 -->
							<td><input type="text" id="pdmTblPnm" name="pdmTblPnm" class="wd200"/></td>		 									
						</tr> 				
					
	            </tbody>
	          </table>   
	      </div>
	 </fieldset>
 </form>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 메뉴 메인 제목 -->

<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
<div style="clear:both; height:5px;"><span></span></div>
    
    
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>


<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs" style="display: none;">
	  <ul>	    
	    <li><a href="#tabs-1">컬럼목록</a></li> <!-- 컬럼 목록 -->	   
	  </ul>	  
      
      <!-- 컬럼 목록 탭 -->
	  <div id="tabs-1">
	      
	      <!-- 버튼영역  -->
	      <div class="divLstBtn"  style="padding-right :0px">	 
			 <div class="bt02">
		        <button class="btn_excel_down" id="btnColExcelDown" name="btnColExcelDown">엑셀내리기</button>  <!-- 엑셀내리기 -->                     
		   	 </div>
	      </div>
	      <!-- 버튼영역  -->
	    
	      <div style="clear:both; height:5px;"><span></span></div>
	  
		  <!-- 그리드 입력 입력 -->
		  <div id="grid_02" class="grid_02">
			    <script type="text/javascript">createIBSheet("col_sheet", "100%", "250px");</script>            
		  </div>
		  <!-- 그리드 입력 입력 -->			
	  </div>	  
	</div>
	
</div>


<!-- </body> --> 
<!-- </html> -->