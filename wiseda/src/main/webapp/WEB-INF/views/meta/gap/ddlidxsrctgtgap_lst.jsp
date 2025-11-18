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
<title>DDL간 GAP분석 조회</title>
<script type="text/javascript">

EnterkeyProcess("Search");  

var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...

var connTrgSchJson = ${codeMap.devConnTrgSch} ;
                       
$(document).ready(function() {
		
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
            	        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
        // 컬럼엑셀내리기 Event Bind
        $("#btnColExcelDown").click( function(){ doAction("ColDown2Excel"); } ).show();
        
        $("#subjDelPop").click(function(){
        	
        	$("#fullPath").val(""); 
        	
        }).show();   
       
        //소스스킵마 변경 
 		$("#srcDbSchId").change(function(){
        	
 			 var srcDbSchId = $(this).val();
 			  			
 			 getTgeDbSch(srcDbSchId); 
        	
        });
        
    }
);

$(window).on('load',function() {
// 	$(".divLstBtn").show();
	
	// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
// 	$("#tabs").tabs().show();
	
	//그리드 초기화 
	initGrid();
	
	initColGrid();

	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	checkApproveYn($("#mstFrm"));
	
	setibsTabHeight($("#grid_02"), 60);
	
	//소스스키마 조회
	getSrcDbSch();
	
});


$(window).resize(function(){
                
	//setibsTabHeight($("#grid_02"));      
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "";
            
            
        headtext += "<s:message code='META.HEADER.DDLLIDX.SRCTGTGAP.LST'/>";
       //No.|상태|GAP상태|소스DB명|타겟DB명|DDL테이블명|DDL인덱스명|인덱스컬럼GAP개수|DDL인덱스컬럼개수(소스)|DDL인덱스컬럼개수(타겟)|수집일시|테이블(논리명)|srcDbSchId|tgtDbSchId
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	     Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Combo",  Width:100,  SaveName:"gapStatus",	    Align:"Center", Edit:0},
                    {Type:"Text",   Width:200,  SaveName:"srcDbSchPnm",	    Align:"Left",   Edit:0},                      
                    {Type:"Text",   Width:200,  SaveName:"tgtDbSchPnm",	    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:180,  SaveName:"ddlTblPnm",       Align:"Left",   Edit:0},
                    {Type:"Text",   Width:180,  SaveName:"ddlIdxPnm",       Align:"Left",   Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"colGapCnt", 	    Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:120,  SaveName:"srcDdlIdxColCnt", Align:"Center", Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"tgtDdlIdxColCnt", Align:"Center", Edit:0},
                    {Type:"Text",   Width:130,  SaveName:"aprvDtm", 	    Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm"},
                    {Type:"Text",   Width:130,  SaveName:"ddlTblLnm",       Align:"Left",   Edit:0},                    
                    {Type:"Text",   Width:80,   SaveName:"srcDbSchId",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"tgtDbSchId",      Align:"Left",   Edit:0, Hidden:1},
                    
                ];
                    
        InitColumns(cols);
	    
        
		SetColProperty("gapStatus", 	{ComboCode:"NOR|NTGT|NSRC|CGAP", ComboText:"정상|타겟인덱스미존재|소스인덱스미존재|컬럼GAP"}); 		
		
				        
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
                        
        headtext1 += "<s:message code='META.HEADER.DDLLIDX.SRCTGTGAP.LST1'/>";
        headtext2 += "<s:message code='META.HEADER.DDLLIDX.SRCTGTGAP.LST2'/>";
    	        
      
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
					{Type:"Text",   Width:130,  SaveName:"gapStatus",	    Align:"Center", Edit:0},
					
					{Type:"Text",   Width:150,  SaveName:"srcDdlTblPnm",	Align:"Left",   Edit:0},
					{Type:"Text",   Width:170,  SaveName:"srcDdlIdxPnm",	Align:"Left",   Edit:0},
					{Type:"Text",   Width:80,   SaveName:"srcUkIdxYn",   	Align:"Center", Edit:0},
					{Type:"Text",   Width:170,  SaveName:"srcDdlIdxColPnm",	Align:"Left",   Edit:0},                     
                    {Type:"Text",   Width:100,  SaveName:"srcDdlIdxColOrd",	Align:"Center", Edit:0},                                        
                    {Type:"Text",   Width:100,  SaveName:"srcSortTyp",	    Align:"Center", Edit:0},
                    
                    {Type:"Text",   Width:150,  SaveName:"tgtDdlTblPnm",	Align:"Left",   Edit:0},
                    {Type:"Text",   Width:170,  SaveName:"tgtDdlIdxPnm",	Align:"Left",   Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"tgtUkIdxYn",   	Align:"Center", Edit:0},
					{Type:"Text",   Width:170,  SaveName:"tgtDdlIdxColPnm",	Align:"Left",   Edit:0},                     
                    {Type:"Text",   Width:100,  SaveName:"tgtDdlIdxColOrd",	Align:"Center", Edit:0},                                        
                    {Type:"Text",   Width:100,  SaveName:"tgtSortTyp",	    Align:"Center", Edit:0},
                    
                ];
                    
        InitColumns(cols);

        
        InitComboNoMatchText(1, "");
        
		SetColHidden("pdmTblId"	,1);
		SetColHidden("ddlTblId"	,1);
		
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
			
			
			if($("#srcDbSchId").val() == ""){
				
				showMsgBox("ERR", "소스DB스키마정보를 입력하세요.");
				return;
			}
			
			if($("#tgtDbSchId").val() == ""){
				
				showMsgBox("ERR", "타겟DB스키마정보를 입력하세요.");
				return;
			}
									
			var param = $("#frmSearch").serialize();
									
			//alert(param);
			                                              
			grid_sheet.DoSearch("<c:url value="/meta/gap/getDdlIdxSrcTgtTblGapList.do" />", param); 			
			
			col_sheet.RemoveAll();
			
			//doActionCol("Search");  
			
			break;
        			
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
                        
        case "ColDown2Excel":  //컬럼엑셀내려받기
            col_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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
	
	//var fullPath = retjson.sysAreaLnm + ">" + retjson.fullPath;
	
	var fullPath = retjson.fullPath;
	var subjId   = retjson.subjId;
	
	//alert(subjId);
	
	//$("#frmSearch #fullPath").val(retjson.fullPath);
	$("#frmSearch #subjLnm").val(fullPath);
	$("#frmSearch #subjId").val(subjId);
	
}



function getSrcDbSch() {
			
	var url = "<c:url value="/meta/gap/getSrcDbSchList.do" />";
		
	getComboData(url, true, $("#srcDbSchId"));
}


function getTgeDbSch(srcDbSchId) {
		
	var url = "<c:url value="/meta/gap/getTgtDbSchList.do" />";
	
	var param = "srcDbSchId=" + srcDbSchId;  
		
	getComboData(url, true, $("#tgtDbSchId"), param);
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
				   
					var schPnm = map.dbConnTrgPnm +"."+ map.dbSchPnm + " ("+ map.ddlTrgDcd  +")";
										
				    obj.append("<option value="+ map.dbSchId +">"+ schPnm +"</option>");					   						   
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
	
	param1 += "&srcDbSchId=" + rowjson.srcDbSchId;
	param1 += "&tgtDbSchId=" + rowjson.tgtDbSchId;
	param1 += "&ddlTblPnm="  + rowjson.ddlTblPnm;
	param1 += "&ddlIdxPnm="  + rowjson.ddlIdxPnm;
	
	//alert(param1);
	
	col_sheet.DoSearch("<c:url value="/meta/gap/getDdlIdxSrcTgtColGapList.do" />", param1); 		 	
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
			
			if(col_sheet.GetCellValue(i, "srcUkIdxYn") != col_sheet.GetCellValue(i, "tgtUkIdxYn")) { 			
				col_sheet.SetCellFontColor(i, "srcUkIdxYn", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtUkIdxYn", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcDdlIdxColPnm") != col_sheet.GetCellValue(i, "tgtDdlIdxColPnm")) { 			
				col_sheet.SetCellFontColor(i, "srcDdlIdxColPnm", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtDdlIdxColPnm", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcDdlIdxColOrd") != col_sheet.GetCellValue(i, "tgtDdlIdxColOrd")) { 			
				col_sheet.SetCellFontColor(i, "srcDdlIdxColOrd", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtDdlIdxColOrd", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "srcSortTyp") != col_sheet.GetCellValue(i, "tgtSortTyp")) { 			
				col_sheet.SetCellFontColor(i, "srcSortTyp", "#FF0000");
				col_sheet.SetCellFontColor(i, "tgtSortTyp", "#FF0000");
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
	    <div class="menu_title">DDL간 GAP분석</div>
	</div>
</div>

<form id="frmSearch" name="frmSearch" >

  <div class="stit">검색조건</div> <!-- 검색조건 -->
  <div style="clear:both; height:5px;"><span></span></div>
	<fieldset>
	     <legend>머리말</legend>
	     <div class="tb_basic2">
	         <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="DB간 GAP분석">
	            <caption>DDL간 GAP분석 검색폼</caption>
	            <colgroup>
	            <col style="width:12%;" />
	            <col style="width:35%;" />
	            <col style="width:12%;" />
	            <col style="width:35%;" />
	            </colgroup>             
	            <tbody>                            
					<tr>	 
					    <th scope="row" class="th_require"><label for="srcDbSchId">소스DBMS/스키마명</label></th> <!--DBMS/스키마명  -->
						<td>							 
				             <select id="srcDbSchId" class="" name="srcDbSchId" style="width:250px;">				             
				             </select>
				        </td>
					    
					    <th scope="row" class="th_require"><label for="tgtDbSchId">타겟DBMS/스키마명</label></th> <!--DBMS/스키마명  -->
						<td>							 
				             <select id="tgtDbSchId" class="" name="tgtDbSchId" style="width:250px;"> 	  			             	
				             </select>
				        </td>
	                    														         		
					</tr> 				
					
					<tr>	 					     						
						<th scope="row"><label for="gapStatus">GAP상태</label></th>  <!-- GAP상태 -->
	                    <td>
				            <select id="gapStatus" class="" name="gapStatus">
				             	<option value="">선택</option>
				             	<!-- <option value="NOR">정상</option>  -->
				             	<option value="CGAP">컬럼GAP</option>
				             	<option value="NSRC">소스미존재</option>
				             	<option value="NTGT">타겟미존재</option>
				            </select> 				            
				         </td>			
				         
				         <th scope="row"><label for="ddlTblPnm">테이블명</label></th> <!-- 테이블명 -->
							<td><input type="text" id="ddlTblPnm" name="ddlTblPnm" class="wd200"/></td>		 									
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