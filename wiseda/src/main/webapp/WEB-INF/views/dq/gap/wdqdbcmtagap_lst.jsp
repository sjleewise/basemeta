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
<title>DB간 GAP분석 조회</title>
<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgDbmsSch} ;
EnterkeyProcess("Search");  
                       
$(document).ready(function() {
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
            	        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
        // 컬럼엑셀내리기 Event Bind
        $("#btnColExcelDown").click( function(){ doAction("ColDown2Excel"); } ).show();  
       
//         //소스스킵마 변경 
//  		$("#dbSchPnm").change(function(){
        	
//  			 var dbSchPnm = $(this).val();
 			  			
//  			getTgtOrgDbSch(dbSchPnm); 
        	
//         })
    	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});   
    }
);

$(window).on('load',function() {

	//그리드 초기화 
	initGrid();
	
	//그리드 컬럼 초기화
	initColGrid();
	
// 	//DBC 스키마 조회
// 	getSrcDbSch();
	
// 	//기관메타 스키마 조회
// 	getTgtMtaDbSch();
});


$(window).resize(function(){
                
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.DBSRCTGTGAP.LST3'/>";
        //No.|상태|GAP상태|소스DB명|타겟DB명|테이블(물리명)|컬럼GAP개수|DB컬럼개수(소스)|DB컬럼개수(타겟)|수집일시|테이블(논리명)|dbSchPnm|tgtDbSchPnm
       
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	     Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Combo",  Width:100,  SaveName:"gapStatus",	  Align:"Center", Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"srcDbSchPnm",	  Align:"Left",   Edit:0},                      
                    {Type:"Text",   Width:200,  SaveName:"tgtDbSchPnm",	  Align:"Left",   Edit:0},
                    {Type:"Text",   Width:130,  SaveName:"dbcTblNm",      Align:"Left",   Edit:0},                     
                    {Type:"Text",   Width:100,  SaveName:"colGapCnt", 	  Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:150,  SaveName:"srcDbcColCnt",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:150,  SaveName:"tgtDbcColCnt",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:130,  SaveName:"dbcTblLnm",     Align:"Left",   Edit:0},                    
                    {Type:"Text",   Width:80,   SaveName:"dbSchPnm",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"tgtDbSchPnm",    Align:"Left",   Edit:0, Hidden:1},
                    
                ];
                    
        InitColumns(cols);
	    
        
		SetColProperty("gapStatus", 	{ComboCode:"NOR|NTGT|NSRC|CGAP|NTGD|NTGS", ComboText:"정상|기관메타 테이블미존재|DBC 테이블미존재|컬럼GAP|기관메타 DBMS 미존재|기관메타 SCH 미존재"}); 		
		
				        
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
        
        var headtext1  = "<s:message code='META.HEADER.DBSRCTGTGAP.LST4'/>";
        var headtext2  = "<s:message code='META.HEADER.DBSRCTGTGAP.LST5'/>";
                        
        //No.|상태|GAP상태|소스DBC|소스DBC   |소스DBC |소스DBC |소스DBC   |소스DBC|소스DBC|소스DBC    |소스DBC |타겟DBC|타겟DBC   |타겟DBC |타겟DBC |타겟DBC   |타겟DBC |타겟DBC |타겟DBC    |타겟DBC
        //No.|상태|GAP상태|컬럼명 |컬럼한글명|컬럼순서|PK여부  |데이터타입|길이   |소수점 |NOTNULL여부|디폴트  |컬럼명 |컬럼한글명|컬럼순서|PK여부  |데이터타입|길이    |소수점  |NOTNULL여부|디폴트 
    	        
      
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
					
					{Type:"Text",   Width:100,  SaveName:"gapStatus",	    Align:"Center", Edit:0},
					{Type:"Text",   Width:140,  SaveName:"srcDbcColPnm",	Align:"Left",   Edit:0},
                    {Type:"Text",   Width:140,  SaveName:"srcDbcColLnm",    Align:"Left",   Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"srcDbcPkYn",	    Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"srcDbcDataType",  Align:"Center", Edit:0},                     
                    {Type:"Text",   Width:100,   SaveName:"srcDbcDataLen",   Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"srcDbcNonulYn",   Align:"Center", Edit:0},	
					{Type:"Text",   Width:140,  SaveName:"tgtDbcColPnm",	Align:"Left",     Edit:0},
                    {Type:"Text",   Width:140,  SaveName:"tgtDbcColLnm",    Align:"Left",     Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"tgtDbcPkYn",      Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"tgtDbcDataType",  Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"tgtDbcDataLen", 	Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:100,   SaveName:"tgtDbcNonulYn",   Align:"Center", Edit:0}	
                    
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
			
			if($("#dbConnTrgId").val() == ""){
				
				showMsgBox("ERR", "DBC스키마정보를 입력하세요.");
				return;
			}
			
			if($("#dbSchId").val() == ""){
				
				showMsgBox("ERR", "기관메타DB스키마정보를 입력하세요.");
				return;
			}			
			
			var param = $("#frmSearch").serialize();
						
// 			console.log(param);
			
			//alert(param);
			                                              
// 			grid_sheet.DoSearch("<c:url value="/meta/gap/getDbSrcTgtTblGapList.do" />", param); 			
			grid_sheet.DoSearch("<c:url value="/meta/gap/getDbcMtaTblGapList.do" />", param); 						
			
			col_sheet.RemoveAll();
			
			//doActionCol("Search");  
			
			break;
        			
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'DBC간GAP분석조회'});
            break;
                        
        case "ColDown2Excel":  //컬럼엑셀내려받기
            col_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'DBC간GAP분석컬럼조회'});
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

function getSrcDbSch() {
			
	var url = "<c:url value="/meta/gap/getDbcSchList.do" />";
		
	getComboData(url, true, $("#dbSchPnm"));
}


function getTgtMtaDbSch(dbSchPnm) {
		
	var url = "<c:url value="/meta/gap/getTgtMtaDbSchList.do" />";
	
// 	var param = "dbSchPnm=" + dbSchPnm;  
	
	getComboData(url, true, $("#tgtDbSchPnm"));
}

//================================================
//소스DB스키마 조회
//================================================
function getComboData(url, bSel, obj, param) {
			
	url = url + "?" + param;
	
	//alert(url); 
	
	$.ajax({
		url: url,
		async: false,
		type: "POST",
		data: "",
		dataType: 'json',
		success: function (data) {
			
			if(data){
				 
				//alert(JSON.stringify(data) );
								
				obj.find("option").remove().end(); 
				 
				if(bSel){
				  obj.append("<option value=\"\">선택</option>");						 
				}
				 
				$.each(data.DATA, function(i, map){
				   
// 					var schPnm = map.dbConnTrgPnm +"."+ map.dbSchPnm + " ("+ map.ddlTrgDcd  +")";
					var schPnm = map.dbConnTrgPnm +"."+ map.dbSchPnm;
					
				    obj.append("<option value="+ schPnm +">"+ schPnm +"</option>");
				});	
								
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			
		}
	}); 	
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
		
	var param1 = "";
	
	param1 += "&dbSchPnm=" + rowjson.srcDbSchPnm;
	param1 += "&tgtDbSchPnm=" + rowjson.tgtDbSchPnm;
	param1 += "&dbcTblNm="   + rowjson.dbcTblNm;
	
// 	alert(param1);
	
	col_sheet.DoSearch("<c:url value="/meta/gap/getDbcMtaColGapList.do" />", param1); 		 	
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
			
			var srcDbcPkYn = "N"; 
			
			if(col_sheet.GetCellValue(i, "srcDbcPkYn") != "") {
				
				srcDbcPkYn = col_sheet.GetCellValue(i, "srcDbcPkYn");
			}
			
			if(srcDbcPkYn != col_sheet.GetCellValue(i, "tgtDbcPkYn")) { 			
				col_sheet.SetCellFontColor(i, "srcDbcPkYn", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtDbcPkYn", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcDbcColOrd") != col_sheet.GetCellValue(i, "tgtDbcColOrd")) { 			
				col_sheet.SetCellFontColor(i, "srcDbcColOrd", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtDbcColOrd", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcDbcDataType") != col_sheet.GetCellValue(i, "tgtDbcDataType")) { 			
				col_sheet.SetCellFontColor(i, "srcDbcDataType", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtDbcDataType", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcDbcDataLen") != col_sheet.GetCellValue(i, "tgtDbcDataLen")) { 			
				col_sheet.SetCellFontColor(i, "srcDbcDataLen", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtDbcDataLen", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcDbcDataScal") != col_sheet.GetCellValue(i, "tgtDbcDataScal")) { 			
				col_sheet.SetCellFontColor(i, "srcDbcDataScal", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtDbcDataScal", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcDbcNonulYn") != col_sheet.GetCellValue(i, "tgtDbcNonulYn")) { 			
				col_sheet.SetCellFontColor(i, "srcDbcNonulYn", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtDbcNonulYn", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcDbcDefltVal") != col_sheet.GetCellValue(i, "tgtDbcDefltVal")) { 			
				col_sheet.SetCellFontColor(i, "srcDbcDefltVal", "#FF0000"); 
				col_sheet.SetCellFontColor(i, "tgtDbcDefltVal", "#FF0000");
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
	    <div class="menu_title">DB간 GAP분석</div>
	</div>
</div>

<form id="frmSearch" name="frmSearch" >

  <input type="hidden" id="dbConnTrgPnm" name="dbConnTrgPnm" value="">

  <div class="stit">검색조건</div> <!-- 검색조건 -->
  <div style="clear:both; height:5px;"><span></span></div>
	<fieldset>
	     <legend>머리말</legend>
	     <div class="tb_basic2">
	         <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="DB간 GAP분석">
	            <caption>DB간 GAP분석 검색폼</caption>
	            <colgroup>
	            <col style="width:12%;" />
	            <col style="width:35%;" />
	            <col style="width:12%;" />
	            <col style="width:35%;" />
	            </colgroup>             
	            <tbody>                            
					<tr>	 
<!-- 					    <th scope="row" class="th_require"><label for="srcDbConnTrgId">DBC(DBMS/스키마명)</label></th> DBMS/스키마명  -->
<!-- 						<td>							  -->
<%-- 				             <select id="dbSchPnm" class="" name="dbSchPnm" style="width:250px;">				              --%>
<%-- 				             </select> --%>
<!-- 				        </td> -->
						<th scope="row" class="th_require"><label for="dbConnTrgId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
						<td>
							<select id="dbConnTrgId" class="" name="dbConnTrgId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					        </select>
					        <select id="dbSchId" class="" name="dbSchId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					        </select>
					    </td>
<!-- 					    <th scope="row" class="th_require"><label for="tgtDbcConnTrgId">기관메타(DBMS/스키마명)</label></th> DBMS/스키마명  -->
<!-- 						<td>							  -->
<%-- 				             <select id="tgtDbSchPnm" class="" name="tgtDbSchPnm" style="width:250px;"> 				             	 --%>
<%-- 				             </select> --%>
<!-- 				        </td> -->
						<th scope="row"><label for="gapStatus">GAP상태</label></th>  <!-- GAP상태 -->
	                    <td>
				            <select id="gapStatus" class="" name="gapStatus">
				             	<option value="">선택</option>
				             	<!-- <option value="NOR">정상</option>  -->
				             	<option value="NOR">정상</option>
				             	<option value="CGAP">컬럼GAP</option>
				             	<option value="NSRC">DBC미존재</option>
				             	<option value="NTGT">기관메타미존재</option>
				            </select> 				            
				         </td>													         		
					</tr> 				
					
					<tr>	 					     							
				         <th scope="row"><label for="dbcTblNm">테이블명</label></th> <!-- 테이블명 -->
							<td  colspan="3"><input type="text" id="dbcTblNm" name="dbcTblNm" class="wd200"/></td>		 									
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
			    <script type="text/javascript">createIBSheet("col_sheet", "100%", "400px");</script>            
		  </div>
		  <!-- 그리드 입력 입력 -->			
	  </div>	  
	</div>
	
</div>


<!-- </body> --> 
<!-- </html> -->