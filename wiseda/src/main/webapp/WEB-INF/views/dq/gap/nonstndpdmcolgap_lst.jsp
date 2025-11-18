<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script> --%>

<html>
<head>
<title>비표준컬럼분석 조회</title>

<script type="text/javascript">

EnterkeyProcess("Search");


var connTrgSchJson = ${codeMap.connTrgSch} ;                 
$(document).ready(function() {
	
		//$( "#tabs" ).tabs();
		
		//// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
    	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   		double_select(connTrgSchJson, $(this));
	   	});
		var con = document.getElementById("gapChart");
		createIBChart(con,"gapChart", {width:"auto", height:"150px"});
		
		$("#poiDown").click( function() {
    		doAction("poiDown");
    	}).show();
        
});

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	//setibsheight($("#grid_01"));
});


$(window).resize(
    
    function(){
                
    	//setibsheight($("#grid_01"));
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
                    {Type:"Text",   Width:300,  SaveName:"fullPath"	,      Align:"Left",    Edit:0, Hidden:1},                    
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
         	
        	if (isBlankStr($("#frmSearch #dbConnTrgId").val(), 'O') 
           	|| isBlankStr($("#frmSearch #dbSchId").val(), 'O')
           		) {
           		showMsgBox("INF", "조회할 DBMS/스키마를 선택하십시오.");
           		return;
           	}
                    	        	
        	var param = $('#frmSearch').serialize();
        
        	param += "&dbmsTypCd=ORA";
        	
        	grid_sheet.DoSearch("<c:url value="/dq/gap/getNonStndPdmColGapList.do" />", param);
        	
        	break;
        	
      
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'모델비표준컬럼조회'});
            break;
            
        case "poiDown":
        	
        	if (isBlankStr($("#frmSearch #dbConnTrgId").val(), 'O') 
                 	|| isBlankStr($("#frmSearch #dbSchId").val(), 'O')) {
                showMsgBox("INF", "조회할 DBMS/스키마를 선택하십시오.");
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
        	
        	//hidden input을 가지고있는 poiForm
        	var myForm = document.poiForm;
        	var url ='<c:url value="/dq/result/gapPoiDown.do" />';
        	
        	myForm.action=url;
        	myForm.method="post";
        	myForm.target="poiForm";
        	myForm.submit();	//dbCodeValue, schCodeValue, sheetNum 세가지의 값을 POST로 넘김
        	
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

	drawChart();
	
}


function drawChart()	{
	var tot = grid_sheet.GetTotalRows();
	var gap = grid_sheet.GetSaveJson({StdCol:"gapStatus"}).data.length;
	
	$("#gap").val(gap);
	$("#tot").val(tot);
	
	var dbmsNm = $("#dbConnTrgId option:selected").text();

// 		gapChart.removeAll();
		var gapRate = new Array();
		var category = new Array();

		var gap = parseFloat(Math.round((1-gap/tot)*10000)/100);
		gapRate.push({y:gap, name:dbmsNm});
		category.push(dbmsNm);
		var seriesGapRate = {
			name:"표준준수율",
			type:"column",
			data: gapRate
		};
		gapChart.setOptions({
			series: [
				seriesGapRate
			]}, { append: true, resetData: true })
		gapChart.setOptions({
			chart : {
				backgroundColor : "#FFFFFF", //차트 배경색 설정
				type : "column", //차트 Type 설정
				borderColor : "#FFFFFF" //차트 테두리 색 설정
			},
			legend : {
				layout : "vertical", //Legend 모양 설정
				align : "left", //Legend 가로 정렬 설정
				verticalAlign : "center", //Legend 세로 정렬 설정
				enabled : true
			},
			
			plotOptions : {
				series : {
					dataLabels : { //시리즈의 데이터 레이블 설정
						enabled : true,
					}
				},
				column : {
					pointPadding : 0.1,  // 컬럼간의 간격 설정
			//		stacking : "normal"
				},
			},
			yAxis : {
				categories : category,
				tickInterval : 0, //X축 레이블 간격 설정
				labels : { //X축 레이블 설정
					enabled : true,
					staggerLines : 2
				},
				title : { 
					text : ""
				}
			}, 
			xAxis : {
				max : 100,
			}
		})));
		gapChart.draw();
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
			
			<input type="hidden" id ="poiFlag" name = "poiFlag" value="stnd">
		</form>
        
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
<%--                            <th scope="row"><label for="subjLnm">주제영역명</label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" class="wd340"/>
                                <button class="btnDelPop" >삭제</button>
		                        <button class="btnSearchPop" id="subjSearchPop">검색</button>
                                </span>
                            </td> --%>
                           	<th scope="row" class="th_require"><label for="dbConnTrgId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
							<td><select id="dbConnTrgId" class="" name="dbConnTrgId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					            <select id="dbSchId" class="" name="dbSchId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select></td> 
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
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "650px");</script>     
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="chart" id="gapChart">
		</div>       
	</div>
	<!-- 그리드 입력 입력 -->


</body>
</html>