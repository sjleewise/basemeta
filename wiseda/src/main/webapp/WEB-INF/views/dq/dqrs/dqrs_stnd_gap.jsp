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

<html>
<head>
<title>표준준수 분석</title>

<script type="text/javascript">

EnterkeyProcess("Search");


var connTrgSchJson = ${codeMap.connTrgSch};
var pubSditmConnTrgId = ${codeMap.pubSditmConnTrgId};

$(document).ready(function() {
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	   	
		var con = document.getElementById("gapChart");
		createIBChart(con,"gapChart", {width:"auto", height:"150px"});
	   	
		
		for(i=0; i<pubSditmConnTrgId.length; i++) {
 			$("#frmSearch #pubDbConnTrgId").append('<option value="' + pubSditmConnTrgId[i].codeCd + '">' + pubSditmConnTrgId[i].codeLnm + '</option>');
 		}
		
		//======================================================
	    // 셀렉트 박스 초기화
	    //======================================================
	 	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	 	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	 		double_select(connTrgSchJson, $(this));
	 	});

		$("#poiDown").click( function() {
    		doAction("poiDown");
    	}).show();
		
		$("#frmSearch #stndType").change(function(){
			var Sort = $("#stndType").val();
		
			grid_sheet.RemoveAll();
		});
		//2022.06.16 ip 마스킹 처리 기능 추가
		$("#poiDownMasking").click( function() {
    		doAction("poiDownMasking");
    	}).show();
		
});

$(window).on('load',function() {
	//그리드 초기화
	
	initGrid();
});


$(window).resize(
    
    function(){

    }
);

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext1 = "";
        var headtext2 = "";
                
//         headtext1 = "s:message code='META.HEADER.NONSTND.DBCCOLGAP.LST'";
		headtext1 = "No.|GAP상태|물리DB    |물리DB    |물리DB        |물리DB  |물리DB   |물리DB      |물리DB      |물리DB|물리DB  |표준항목      |표준항목      |표준항목  |표준항목|표준항목|표준항목|표준항목";
        //No.|GAP상태|DBC    |DBC    |DBC        |DBC  |DBC   |DBC      |DBC      |DBC|DBC  |표준항목      |표준항목      |표준항목  |표준항목|표준항목|표준항목|표준항목
//         headtext2 = "s:message code='META.HEADER.NONSTND.DBCCOLGAP.LST1'";
		headtext2 = "No.|GAP상태|주제영역|테이블명|테이블한글명|컬럼명|컬럼명S|컬럼한글명|데이터타입|길이|소수점|표준항목논리명|표준항목물리명|데이터타입|길이    |소수점  |변경일시|비고";
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
        
//         FitColWidth();  

        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function selectBoxRest(item){
	var $params = $(item).closest('table').find('select');
	current = $params.index(item);
	
	while(++current < $params.length){
		$params.get(current).options.length = 1;
	}
}

function recursion_double_select(url, param, item){
	
	var ajaxUrl = url;
	var ajaxData = param;
	var sub = item;
	
	$.ajax({
		type:'get',
		url: ajaxUrl,
		data: { sysAreaId : ajaxData },
		success: function(jsonData){

	        double_select(jsonData, sub);
		   	$('select', sub.parent()).change(function(){
		   		double_select(jsonData, $(this));
		   	});
		},
		error : function(error) {
	        alert("Error!");
		}
	})
}


//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	var fullPath = retjson.fullPath;
	
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
	var Sort = $("#stndAsrt").val();
        
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
                    	        	
        	var param = $('#frmSearch').serialize();

			if($("#dbConnTrgId").val() == ""){
				
				showMsgBox("ERR", "DBMS/스키마정보를 입력하세요.");
				return;
			}
			if($("#dbSchId").val() == ""){
				
				showMsgBox("ERR", "스키마정보를 입력하세요.");
				return;
			}
			
			if($("#stndAsrt").val() == ""){
				
				showMsgBox("ERR", "진단대상표준사전 정보를 입력하세요.");
				return;
			}
			
			//표준분류명 선택 되어 있으면 공통용 파라미터 넣어준다.
			if($("#chkPubUse").is(':checked') == true){
				param += "&sditmDbConnTrgId="+$("#pubDbConnTrgId option:selected").val();
			}
			
       		grid_sheet.DoSearch("<c:url value="/dq/dqrs/getDqrsStndColGapLst.do" />", param);
        	
        	break;
        	
      
        case "Down2Excel":  //엑셀내려받기
        
	    	if(Sort == 'ORG'){
	    		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'표준진단(기관표준/DBC)'});
	    	} else if(Sort == 'GOV'){
	    		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'표준진단(범정부표준/DBC)'});
	    	} else {
	    		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'표준진단(개별 DB/DBC)'});
	    	}

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
//         	var stndSort=$('#stndType option:selected').val(); // 표준진단 Sort
        	var asrt=$('#stndAsrt option:selected').val(); // 표준분류
        	
        	var pubDbConnTrgId = null;
        	//표준분류명 선택 되어 있으면 공통용 파라미터 넣어준다.
			if($("#chkPubUse").is(':checked') == true){
				pubDbConnTrgId = $("#pubDbConnTrgId option:selected").val();
			}
        	
			$('#sditmDbConnTrgId').val(pubDbConnTrgId);
			
        	$('#dbmsId').val(dbmsId);		//선택된 DBMS의 값
        	$('#schId').val(schId);		//선택된 스키마의 값
        	$('#dbmsLnm').val(dbmsLnm);		//선택된 DBMS의 값
        	$('#schLnm').val(schLnm);		//선택된 스키마의 값
//         	$('#stndSort').val(stndSort); // 선택된 표준진단 값
        	$('#asrt').val(asrt); // 선택된 표준분류 값
        	$('#maskingYn').val("N"); // 마스킹 유무 구분
        	
        	//hidden input을 가지고있는 poiForm
        	var myForm = document.poiForm;
        	var url ='<c:url value="/dq/dqrs/gapPoiDown.do" />';
        	
        	myForm.action=url;
        	myForm.method="post";
        	myForm.target="poiForm";
        	myForm.submit();	//dbCodeValue, schCodeValue, sheetNum 세가지의 값을 POST로 넘김
        	
        	break;
        	
        	
        	case "poiDownMasking":

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
            	var asrt=$('#stndAsrt option:selected').val(); // 표준분류
            	
            	//표준분류명 선택 되어 있으면 공통용 파라미터 넣어준다.
    			if($("#chkPubUse").is(':checked') == true){
    				var pubDbConnTrgId = $("#pubDbConnTrgId option:selected").val();
    				$("#pubDbConnTrgId").val(pubDbConnTrgId);
    			}
            	
            	$('#dbmsId').val(dbmsId);		//선택된 DBMS의 값
            	$('#schId').val(schId);		//선택된 스키마의 값
            	$('#dbmsLnm').val(dbmsLnm);		//선택된 DBMS의 값
            	$('#schLnm').val(schLnm);		//선택된 스키마의 값
            	$('#asrt').val(asrt); // 선택된 표준분류 값
            	$('#maskingYn').val("Y"); // 마스킹 유무 구분
            	//hidden input을 가지고있는 poiForm
            	var myForm = document.poiForm;
            	var url ='<c:url value="/dq/dqrs/gapPoiDown.do" />';
            	
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
			var pdmColLnm = grid_sheet.GetCellValue(i, "pdmColLnm");
			
			pdmColPnm = pdmColPnm.replace(/[0-9,_]+$/g, ''); //컬럼명끝 _ 숫자  제거
				
			if(pdmColPnm != grid_sheet.GetCellValue(i, "sditmPnm")) { 			
				grid_sheet.SetCellFontColor(i, "pdmColPnm", "#FF0000");
			} 	
			
			if(grid_sheet.GetCellValue(i, "pdmColLnm") != grid_sheet.GetCellValue(i, "sditmLnm")) { 			
				grid_sheet.SetCellFontColor(i, "pdmColLnm", "#FF0000");
			} 	
			if(grid_sheet.GetCellValue(i, "pdmDataType") != grid_sheet.GetCellValue(i, "sditmDataType")) { 			
				grid_sheet.SetCellFontColor(i, "pdmDataType", "#FF0000");
			} 	
			
			if(grid_sheet.GetCellValue(i, "pdmDataType") != grid_sheet.GetCellValue(i, "sditmDataType")) { 			
				grid_sheet.SetCellFontColor(i, "pdmDataType", "#FF0000");
			} 	
			
			if(grid_sheet.GetCellValue(i, "pdmDataLen") != grid_sheet.GetCellValue(i, "sditmDataLen")) { 			
				grid_sheet.SetCellFontColor(i, "pdmDataLen", "#FF0000");
			} 	
			
			if(grid_sheet.GetCellValue(i, "pdmDataScal") != grid_sheet.GetCellValue(i, "dbcDataScal") 
														&& grid_sheet.GetCellValue(i, "pdmDataScal") != 0 
														&& grid_sheet.GetCellValue(i, "dbcDataScal") != 0) { 			
				grid_sheet.SetCellFontColor(i, "pdmDataScal", "#FF0000");
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
		});
		gapChart.draw();
}





</script>
</head>

<body>

<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">표준준수 분석</div>
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
			<input type="hidden" id ="stndSort" name="stndSort" value="">
			<input type="hidden" id ="asrt" name="asrt" value="">
			<input type="hidden" id ="poiFlag" name = "poiFlag" value="stnd">
			<input type="hidden" id ="maskingYn" name = "maskingYn" value="">
			<input type="hidden" id ="sditmDbConnTrgId" name = "sditmDbConnTrgId" value="">
		</form>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" name="pdmTblId" />
        	<input type="hidden" name="subjId" />
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="비표준컬럼조회">
                   <caption>표준준수 분석</caption>
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   </colgroup>
                   
                   <tbody>
                       <tr>                                                
                           	<th scope="row" class="th_require"><label for="dbConnTrgId"><s:message code="DB.MS.SCHEMA.NM" /></label></th> <!-- DBMS/스키마명 -->
							<td>
								<select id="dbConnTrgId" name="dbConnTrgId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					            <select id="dbSchId" name="dbSchId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					        </td> 
					        <th scope="row"><input type="checkbox" id="chkPubUse" name="chkPubUse">표준분류명</th>
							<td>
								<select id="pubDbConnTrgId" name="pubDbConnTrgId">
					             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					            </select>
					        </td> 
					        <th scope="row" ><label for="pdmTblLnm">테이블명</label></th>  <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="pdmTblLnm" id="pdmTblLnm" class="wd200" value="${search.pdmTblLnm}"/>
                                </span>
                            </td>
                       </tr>                   
                       <tr>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        <div class="tb_comment">- 표준분류명 체크 시 공통 표준용어로 진단합니다.</div>
		<div style="clear:both; height:10px;"><span></span></div>
</div>
<!-- 조회버튼영역  -->
<div class="divLstBtn" style="display: none;">	 
	<div class="bt03">
		<button class="btn_search" id="btnSearch" name="btnSearch" style="display:none"><s:message code="INQ"/></button> <!-- 조회 --> 
	</div>
	<div class="bt02">
		<button class="btn_excel_down" id="poiDownMasking"    name="poiDownMasking" style="display:none">품질평가용 자료 내려받기(마스킹)</button> <!-- 2022.06.16 ip 마스킹 처리를 위한 기능 추가-->
		<button class="btn_excel_down" id="poiDown"    name="poiDown" style="display:none">품질평가용 자료 내려받기</button> <!-- 보고서-->
		<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown" style="display:none"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	</div>
</div>	

<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">  
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "550px");</script>     
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="chart" id="gapChart">
		</div>       
	</div>
</body>
</html>