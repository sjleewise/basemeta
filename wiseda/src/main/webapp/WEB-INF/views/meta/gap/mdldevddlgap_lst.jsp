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

var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...

var connTrgSchJson = ${codeMap.devConnTrgSch} ;
                       
$(document).ready(function() {
		//탭 초기화....
// 		$("#tabs").tabs().show();
		
		/*
		$('#btnSearch').button({
	       icons: {
			          primary: "ui-icon-refresh"
			      }	, 
		   label: "조회"   
		});
		*/
				
		$('#subjSearchPop').click(function(event){
		    	
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
		    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
		    	
		    	param += "&lecyDcd=TO";
		    	
				var popwin = OpenModal(url+"?"+param, "subjGapPop",  600, 400, "no"); 
				popwin.focus();
			
		});
		
		$("#subjDelPop").click(function(){
        	
        	$("#fullPath").val(""); 
        	
        }).show();   
        
                    
		
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
            	        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
        // 컬럼엑셀내리기 Event Bind
        $("#btnColExcelDown").click( function(){ doAction("ColDown2Excel"); } ).show();
        
        $("#subjDelPop").click(function(){
        	
        	$("#fullPath").val("");
        	$("#subjLnm").val(""); 
        	
        }).show();   
       
        double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
       
     	// 시스템영역      
        //create_selectbox(sysareaJson, $("#sysAreaId"));
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
	
	//loadDetail();

	//doAction("Search");
	
// 	//$( "#layer_div" ).show();
	
});


$(window).resize(function(){
                
	//setibsTabHeight($("#grid_02"));      
});


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.MDLDEV.DDLGAP.LST'/>";
        //No.|상태|GAP상태|주제영역|테이블(물리명)|컬럼GAP개수|모델컬럼개수|DDL컬럼개수|승인일시|테이블(논리명)|PDM_TBL_ID|DB_SCH_ID
       
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	     Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
					
					{Type:"Combo",  Width:100,  SaveName:"gapStatus",	 Align:"Center", Edit:0},
                    {Type:"Text",   Width:280,  SaveName:"fullPath",	 Align:"Left",   Edit:0},                      
                    {Type:"Text",   Width:130,  SaveName:"pdmTblPnm",    Align:"Left",   Edit:0},                     
                    {Type:"Text",   Width:100,  SaveName:"colGapCnt", 	 Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"pdmColCnt", 	 Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"ddlColCnt",    Align:"Center", Edit:0},                    
                    {Type:"Text",   Width:130,  SaveName:"aprvDtm", 	 Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm"},
                    {Type:"Text",   Width:130,  SaveName:"pdmTblLnm", 	 Align:"Left",   Edit:0},                    
                    {Type:"Text",   Width:80,   SaveName:"pdmTblId",   	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"dbSchId",   	 Align:"Left",   Edit:0, Hidden:1},
                    
                ];
                    
        InitColumns(cols);
	    
        
		SetColProperty("gapStatus", 	{ComboCode:"NOR|NTGT|NPDM|CGAP", ComboText:"정상|개발DDL미존재|모델미존재|컬럼GAP"}); 		
		
				        
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
                        
        headtext1 += "<s:message code='META.HEADER.MDLDEV.DDLGAP.LST1'/>";
        //No.|상태|GAP상태|모델  |모델      |모델    |모델  |모델      |모델|모델  |모델       |모델  |DDL(개발)|DDL(개발) |DDL(개발)|DDL(개발)|DDL(개발) |DDL(개발)|DDL(개발)|DDL(개발)  |DDL(개발)
        headtext2 += "<s:message code='META.HEADER.MDLDEV.DDLGAP.LST2'/> ";
        //No.|상태|GAP상태|컬럼명|컬럼한글명|컬럼순서|PK여부|데이터타입|길이|소수점|NOTNULL여부|디폴트|컬럼명   |컬럼한글명|컬럼순서 |PK여부   |데이터타입|길이     |소수점   |NOTNULL여부|디폴트   
    	        
      
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
					{Type:"Text",   Width:120,  SaveName:"ddlColPnm",	 Align:"Left",     Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"ddlColLnm",    Align:"Left",     Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"ddlColOrd",	 Align:"Center",   Edit:0},                    
                    {Type:"Text",   Width:80,   SaveName:"ddlPkYn",      Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"ddlDataType",  Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"ddlDataLen", 	 Align:"Center",   Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"ddlDataScal",  Align:"Center",   Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"ddlNonulYn",   Align:"Center", Edit:0},	
                    {Type:"Text",   Width:80,   SaveName:"ddlDefltVal",  Align:"Center", Edit:0},
                    
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
						
			//param += "&sysAreaLnm=" + $("#sysAreaId option:selected").text();
			
			//alert(param);
			                                              
			grid_sheet.DoSearch("<c:url value="/meta/gap/getMdlDevDdlTblGapList.do" />", param); 			
			
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
	
	param1 += "&ddlTblPnm=" + rowjson.pdmTblPnm;
	param1 += "&pdmTblId="  + rowjson.pdmTblId;
	param1 += "&dbSchId="   + rowjson.dbSchId;
	param1 += "&fullPath="  + rowjson.fullPath;
	
	//alert(param1);
	
	col_sheet.DoSearch("<c:url value="/meta/gap/getMdlDevDdlColGapList.do" />", param1); 		 	
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
			
			if(pdmPkYn != col_sheet.GetCellValue(i, "ddlPkYn")) { 			
				col_sheet.SetCellFontColor(i, "pdmPkYn", "#FF0000");
				col_sheet.SetCellFontColor(i, "ddlPkYn", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmColOrd") != col_sheet.GetCellValue(i, "ddlColOrd")) { 			
				col_sheet.SetCellFontColor(i, "pdmColOrd", "#FF0000");
				col_sheet.SetCellFontColor(i, "ddlColOrd", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmDataType") != col_sheet.GetCellValue(i, "ddlDataType")) { 			
				col_sheet.SetCellFontColor(i, "pdmDataType", "#FF0000");
				col_sheet.SetCellFontColor(i, "ddlDataType", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmDataLen") != col_sheet.GetCellValue(i, "ddlDataLen")) { 			
				col_sheet.SetCellFontColor(i, "pdmDataLen", "#FF0000");
				col_sheet.SetCellFontColor(i, "ddlDataLen", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmDataScal") != col_sheet.GetCellValue(i, "ddlDataScal")) { 			
				col_sheet.SetCellFontColor(i, "pdmDataScal", "#FF0000");
				col_sheet.SetCellFontColor(i, "ddlDataScal", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmNonulYn") != col_sheet.GetCellValue(i, "ddlNonulYn")) { 			
				col_sheet.SetCellFontColor(i, "pdmNonulYn", "#FF0000");
				col_sheet.SetCellFontColor(i, "ddlNonulYn", "#FF0000");
			} 	
			
			if(col_sheet.GetCellValue(i, "pdmDefltVal") != col_sheet.GetCellValue(i, "ddlDefltVal")) { 			
				col_sheet.SetCellFontColor(i, "pdmDefltVal", "#FF0000"); 
				col_sheet.SetCellFontColor(i, "ddlDefltVal", "#FF0000");
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
					    <th scope="row" class="th_require"><label for="dbConnTrgId">DBMS/스키마명</label></th> <!--DBMS/스키마명  -->
						<td>
							 <select id="dbConnTrgId" class="" name="dbConnTrgId">
				             	<option value="">전체</option> <!-- 전체 -->
				             </select>
				             <select id="dbSchId" class="" name="dbSchId">
				             	<option value="">전체</option> <!-- 전체 -->
				             </select>
				        </td>
					    
					    <th scope="row"><label for="fullPath">주제영역</label></th> <!-- 주제영역 -->
	                    <td>
                             <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" class="wd340"/>
                                <input type="hidden" name="subjId" id="subjId" />
                                <button class="btnDelPop"    id="subjDelPop" >삭제</button>
		                        <button class="btnSearchPop" id="subjSearchPop">검색</button>
                             </span>
                        </td>
	                    														         		
					</tr> 				
					
					<tr>	 					     						
						<th scope="row"><label for="gapStatus">GAP상태</label></th>  <!-- GAP상태 -->
	                    <td>
				            <select id="gapStatus" class="" name="gapStatus">
				             	<option value="">선택</option>
				             	<!-- <option value="NOR">정상</option>  -->
				             	<option value="CGAP">컬럼GAP</option>
				             	<option value="NPDM">모델미존재</option>
				             	<option value="NTGT">개발DDL미존재</option>
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