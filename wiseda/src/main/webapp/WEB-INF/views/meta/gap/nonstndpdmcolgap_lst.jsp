<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title>비표준컬럼분석 조회</title>

<script type="text/javascript">

EnterkeyProcess("Search");

                       
$(document).ready(function() {
	
		//$( "#tabs" ).tabs();
		
		//// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

        $('#subjSearchPop').button({   //주제영역 검색팝업 이벤트
		    icons: {
		        primary: "ui-icon-search"
		      },
		      text: false, 
		      create: function (event, ui) {
	//	     	  $(this).addClass('search_button');
				  $(this).css({
					  'width': '18px',
					  'height': '18px',
					  'vertical-align': 'bottom'
					  });
		    	  
		      }
		    }).click(function(event){
		    	
 		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
			
			//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
			//$('div#popSearch').dialog("open");
		    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
		    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
		    	
		    	param += "&lecyDcd=TO";
		    	
				var popwin = OpenModal(url+"?"+param, "subjPdmPop",  600, 400, "no");
				popwin.focus();
			
		}).parent().buttonset(); 
        
        
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	setibsheight($("#grid_01"));
});


$(window).resize(
    
    function(){
                
    	setibsheight($("#grid_01"));
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext1 = "";
        var headtext2 = "";
                
        headtext1 = "<s:message code='META.HEADER.NONSTND.PDMCOLGAP.LST'/>";
        //No.|GAP상태|모델    |모델    |모델        |모델  |모델   |모델      |모델      |모델|모델  |표준항목      |표준항목      |표준항목  |표준항목|표준항목|표준항목|표준항목
        headtext2 = "<s:message code='META.HEADER.NONSTND.PDMCOLGAP.LST1'/>";
        //No.|GAP상태|주제영역|테이블명|테이블한글명|컬럼명|컬럼명S|컬럼한글명|데이터타입|길이|소수점|표준항목논리명|표준항목물리명|데이터타입|길이    |소수점  |변경일시|비고

        
        headtext1 = headtext1.replace(/[' ']/gi,'');
        headtext2 = headtext2.replace(/[' ']/gi,'');
        
        SetMergeSheet(msHeaderOnly);
        
        var headers = [
					{Text:headtext1, Align:"Center"},   
                    {Text:headtext2, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",         Align:"Center",  Edit:0},                    
                    {Type:"Text",   Width:120,  SaveName:"gapStatus", 	   Align:"Center",  Edit:0},                     
                    {Type:"Text",   Width:300,  SaveName:"fullPath"	,      Align:"Left",    Edit:0},                    
                    {Type:"Text",   Width:130,  SaveName:"pdmTblPnm",      Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:130,  SaveName:"pdmTblLnm", 	   Align:"Left",    Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"pdmColPnm",      Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:80,   SaveName:"pdmColSpnm",     Align:"Center",  Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"pdmColLnm", 	   Align:"Left",    Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"pdmDataType",    Align:"Center",  Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"pdmDataLen",     Align:"Center",  Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"pdmDataScal",    Align:"Center",  Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"sditmLnm",       Align:"Left",    Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"sditmPnm", 	   Align:"Left",    Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"sditmDataType",  Align:"Center",  Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"sditmDataLen",   Align:"Center",  Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"sditmDataScal",  Align:"Center",  Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"aprvDtm",        Align:"Center",  Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:100,  SaveName:"remark",         Align:"Center",  Edit:0},
                    
                ];
                    
        InitColumns(cols);
        
        
        InitComboNoMatchText(1, "");
        

        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}



//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	//var fullPath = retjson.sysAreaLnm + ">" + retjson.fullPath;
	
	var fullPath = retjson.fullPath;
	
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
 	$("#frmSearch #subjLnm").val(fullPath);
	
}


//주제영역 검색 팝업에서 선택한 내용을 json으로 반환 받는다...
function getSubjPop(subjjson) {
	
	$("form#frmSearch input[name=subjLnm]").val(subjjson.subjLnm);
	$("form#frmSearch input[name=subjId]").val(subjjson.subjId);
	
}


//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
        	if (isBlankStr($("#frmSearch #subjLnm").val(), 'O') ) {
           	showMsgBox("INF", "조회할 주제영역명을 입력 하십시오.");
           	return;
        }
                    	        	
        	var param = $('#frmSearch').serialize();
        
        	param += "&dbmsTypCd=TIB";
        	
        	grid_sheet.DoSearch("<c:url value="/meta/gap/getNonStndPdmColGapList.do" />", param);
        	
        	break;
        	
      
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'모델비표준컬럼조회'});
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
function grid_sheet_OnDblClick(row, col, value, CellX, CellY, CellW, CellH) {
//     alert("tbl dbl click");
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
// 	alert("tbl click event");

	return;
}



function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
		for(var i = grid_sheet.HeaderRows(); i <= grid_sheet.RowCount() + grid_sheet.HeaderRows() - 1; i++) {
			
			grid_sheet.SetCellFontColor(i, "gapStatus", "#FF0000");
			
			var pdmColPnm = grid_sheet.GetCellValue(i, "pdmColPnm"); 
			
			pdmColPnm = pdmColPnm.replace(/[0-9,_]+$/g, ''); //컬럼명끝 _ 숫자  제거
				
			if(pdmColPnm != grid_sheet.GetCellValue(i, "sditmPnm")) { 			
				grid_sheet.SetCellFontColor(i, "pdmColPnm", "#FF0000");
				//grid_sheet.SetCellFontColor(i, "sditmPnm",  "#FF0000"); 
			} 	
			
			if(grid_sheet.GetCellValue(i, "pdmDataType") != grid_sheet.GetCellValue(i, "sditmDataType")) { 			
				grid_sheet.SetCellFontColor(i, "pdmDataType", "#FF0000");
				//grid_sheet.SetCellFontColor(i, "sditmDataType", "#FF0000");
			} 	
			
			if(grid_sheet.GetCellValue(i, "pdmDataType") != grid_sheet.GetCellValue(i, "sditmDataType")) { 			
				grid_sheet.SetCellFontColor(i, "pdmDataType", "#FF0000");
				//grid_sheet.SetCellFontColor(i, "sditmDataType", "#FF0000");
			} 	
			
			if(grid_sheet.GetCellValue(i, "pdmDataLen") != grid_sheet.GetCellValue(i, "sditmDataLen")) { 			
				grid_sheet.SetCellFontColor(i, "pdmDataLen", "#FF0000");
				//grid_sheet.SetCellFontColor(i, "sditmDataLen", "#FF0000");
			} 	
			
			if(grid_sheet.GetCellValue(i, "pdmDataScal") != grid_sheet.GetCellValue(i, "dbcDataScal")) { 			
				grid_sheet.SetCellFontColor(i, "pdmDataScal", "#FF0000");
				//grid_sheet.SetCellFontColor(i, "dbcDataScal", "#FF0000");
			} 	
		}
	}
	
}






</script>
</head>

<body>

<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">비표준컬럼 조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<div id="search_div">
<!-- 검색조건 입력폼 -->
        <div class="stit">검색조건</div> <!-- 검색조건 -->
        
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" name="pdmTblId" />
        	<input type="hidden" name="subjId" />
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="비표준컬럼조회">
                   <caption>비표준컬럼조회</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                                                         
                           <th scope="row" class="th_require"><label for="subjLnm">주제영역명</label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" class="wd340"/>
                                <button class="btnDelPop" >삭제</button>
		                        <button class="btnSearchPop" id="subjSearchPop">검색</button>
                                </span>
                            </td>
                            
                            <th scope="row" ><label for="pdmTblLnm">테이블명</label></th>  <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" class="wd200" value="${search.pdmTblLnm}"/>
                                </span>
                            </td>
                       </tr>
                      
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
<!-- 조회버튼영역  -->
<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />

<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01"> 
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->


</body>
</html>