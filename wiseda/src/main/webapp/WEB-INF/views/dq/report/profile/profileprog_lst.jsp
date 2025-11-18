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
<title><s:message code="PROF.QLTY.TRANSITION"/></title><!--프로파일 품질추이-->

<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script> --%>
<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;
$(document).ready(function() {

	//그리드 초기화 
 	initGrid();
 	

	//탭 초기화
 	//$( "#tabs" ).tabs();
	

	$("#btnTreeNew").hide();
	
	$("#btnDelete").hide();
	
	//조회
	$("#btnSearch").click(function(){
		
		$('#bizrule_sel_title').html(null);
		
		doAction("Search");
		
		}).show();
	
	//엑셀다운로드
	$("#btnExcelDown").click(function(){	
		
		doAction("Down2Excel");	
		
	});
	
	//업무영역명 검색 팝업
	$('.btnBizAreaLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/bizarea/popup/bizarea_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=BIZLNM", "bizAreaPop",  800, 600, "no");
				popwin.focus();
	});
	
	//품질지표명 검색 팝업
	$('.btnDqiLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/dqi/popup/dqi_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=DQILNM", "ctqLstPop",  800, 600, "no");
				popwin.focus();
	});
	
	//중요정보항목명 검색 팝업
	$('.btnCtqLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/ctq/popup/ctq_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=CTQLNM", "ctqPop",  800, 600, "no");
				popwin.focus();
	});
	
	//bizrule_detail.jsp를 등록요청페이지와 공유하므로, 필요없는 부분 hide...
	
	//======================================================
	 // 셀렉트 박스 초기화
	//======================================================
	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
		double_select(connTrgSchJson, $(this));
	});
	
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	
	
});

EnterkeyProcess("Search");

function initGrid()
{
    with(grid_sheet){
    
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(1);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.PROFILEPROG_LST'/>"
                    	,Align:"Center"}
                ];
        //No.|진단대상ID|진단대상명|진단대상논리명|프로파일종류|분석차수(숨김)|분석차수|분석일자|분석건수|오류건수|품질점수(%)|DPMO|SIGMA

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:0, Hidden:1, ColMerge:1},
                    {Type:"Text",   	Width:90,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Combo",   	Width:90,  SaveName:"prfKndCd",   	Align:"Center", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Text", 		Width:60,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1, ColMerge:0},
                    {Type:"Text", 		Width:60,   SaveName:"anaDgrDisp",  Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Date", 		Width:60,   SaveName:"anaStrDtm",  Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Int",   	Width:50,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:50,   SaveName:"esnErCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:50,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:50,   SaveName:"dpmo", 	Align:"Right", Edit:0, Hidden:1, ColMerge:0},
                    {Type:"Float",   	Width:50,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        SetColProperty("prfKndCd", ${codeMap.prfKndCdibs});
        
        InitComboNoMatchText(1, "");
        
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
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
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch("<c:url value="/dq/report/profile/getProfileProgQuality.do" />", param);

			//차트데이터 생성
// 	    	getLineChart(param);

	    	break;

  		case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'프로파일품질추이'});
//   			progChart.Down2Image({FileName:"ProfileProg", Type:IBExportType.JPEG, Width:800, Url:"./../../../js/IBChart/jsp/Down2Image.jsp"});
            break;
    }
}


function grid_sheet_OnClick(row, col, value, cellx, celly) {

	if(row < 1) return;

	//클릭한 진단대상명의 시작과 끝위치를 구한다.
	var tmpDB = grid_sheet.GetCellValue(row, "dbConnTrgLnm");
	var strVal = 0;
	var endVal = 0;
	for(var i=0; i<grid_sheet.SearchRows(); i++) {
		if(grid_sheet.GetCellValue(i+1, "dbConnTrgLnm") == tmpDB) {
			if(strVal == 0) strVal = (i+1);
		} else {
			if(strVal != 0 && endVal == 0) {
				endVal = i;
				break;
			}
		}

		if(i+1 == grid_sheet.SearchRows()) endVal = (i+1);
	}
	
	console.log("strVal:" + strVal + " endVal:"+endVal);

	//차트데이터 생성
	chartDraw(strVal, endVal);

}



function grid_sheet_OnSearchEnd(code) {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	//차트 클리어
	// progChart.RemoveAll();

	if(grid_sheet.SearchRows() == 0) return;

	//첫번째 행 클릭상태로 차트데이터를 생성...
	grid_sheet_OnClick(1);
}

$(window).on('load',function(){
	chartDraw();
});

function chartDraw(strVal, endVal)	{
	
	//차트 클리어
	progChart.removeAll();

	//검색결과가 없을경우 리턴...
	if(grid_sheet.SearchRows() == 0) return;

	//프로파일종류
	var tmpPrfKndCd = "";
	var prfKndCd;

	//최대차수
	var maxAnaDgr = 0;

	//프로파일컬럼 시작위치
	var tmpStrPrfKndCd = "";
	var strPrfKndCd;

	//각 프로파일컬럼 갯수
	var tmpPrfCntVal = 1;
	var tmpPrfCnt = "";
	var prfCnt;
	var firstFlag = true;
	
	var minVal = 999;
	
	for(var i=strVal; i<=endVal; i++) {
	
		//최대차수 계산...
		if(grid_sheet.GetCellValue(i, "anaDgr") > maxAnaDgr) {
			maxAnaDgr = grid_sheet.GetCellValue(i, "anaDgr");
		}
		
		if(grid_sheet.GetCellValue(i, "erRate") < minVal) {
			minVal = grid_sheet.GetCellValue(i, "erRate");
		}
	
		console.log("prfKndCd:"+grid_sheet.GetCellText(i, "prfKndCd"));
		console.log("dbConnTrgLnm:"+grid_sheet.GetCellText(i, "dbConnTrgLnm"));
		
		//프로파일종류 추출(차수에 따른 중복 제거)
		if(grid_sheet.GetCellText(i-1, "dbConnTrgLnm") != grid_sheet.GetCellText(i, "dbConnTrgLnm") || grid_sheet.GetCellText(i-1, "prfKndCd") != grid_sheet.GetCellText(i, "prfKndCd")) {
			if(tmpPrfKndCd != "") {
				tmpPrfKndCd += "|";
				tmpStrPrfKndCd += "|";
				if(tmpPrfCnt != "")
					tmpPrfCnt += "|";
			}
			tmpPrfKndCd += grid_sheet.GetCellText(i, "prfKndCd") + "(" + grid_sheet.GetCellText(i, "dbConnTrgLnm") + ")";
			tmpStrPrfKndCd += i;
			if(firstFlag) {
				firstFlag = false;
			} else {
				tmpPrfCnt += tmpPrfCntVal;
				tmpPrfCntVal = 1;
			}
		
		} else {
			tmpPrfCntVal++;
		}

		if(i == endVal){
			if(tmpPrfCnt != "") {
				tmpPrfCnt += "|";
			}
		
			tmpPrfCnt += tmpPrfCntVal;
		}
					
	}
	console.log("!!!!!:"+tmpPrfKndCd);
	
	prfKndCd = tmpPrfKndCd.split("|");
	strPrfKndCd = tmpStrPrfKndCd.split("|");
	prfCnt = tmpPrfCnt.split("|");

	console.log(prfKndCd);
	console.log(strPrfKndCd);
	console.log(prfCnt);
	
	//
	progChart.setOptions({
		chart : {
			backgroundColor : "#FFFFFF", //차트 배경색 설정
			type : "column", //차트 Type 설정
			borderColor : "#FFFFFF"
		},
		legend : {
			layout : "vertical", //Legend 모양 설정
			align : "right", //Legend 가로 정렬 설정
			verticalAlign : "center" //Legend 세로 정렬 설정
		},

		colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],

		plotOptions : {
			series : {
				dataLabels : { //시리즈의 데이터 레이블 설정
					enabled : true
				}
			},
			column : {
				pointPadding : 0.02 // 컬럼간의 간격 설정
			},
		},
		xAxis : {
			tickInterval : 1, //X축 레이블 간격 설정
			labels : { //X축 레이블 설정
				enabled : true
			},
			title : { //X축 제목 설정
				text : "<s:message code='ANLY.ODR' />"/*분석차수*/
			}
		},
		yAxis : {
			tickInterval : 10, //Y축 레이블 간격 설정
			min : 60, //Y축 Min값 설정
			max : 100, //Y축 Max값 설정
			title : { //Y축 제목 설정
				text : "<s:message code='QLTY.SCR.PER'/>"/*품질점수(%)*/

			},
			margin : 1000
		}
	});

	//시리즈 생성
	var series = new Array();
	for(var i=0; i<prfKndCd.length; i++) {
		// series[i] = progChart.CreateSeries();
		//각 시리즈별 이름과 타입 설정
		series[i] = {
			name:prfKndCd[i],
			type:"spline"
		};
	}
	
	//시리즈별 데이터 생성
	var points = new Array();
	
	var category = new Array();
		
	for(var i=0; i<prfKndCd.length; i++) {
		points[i] = new Array();
		for(var j=0;j<prfCnt[i];j++) {
			var row = parseInt(strPrfKndCd[i]) + parseInt(j);
			points[i].push({x:grid_sheet.GetCellText(row, "anaDgrDisp"), y:grid_sheet.GetCellValue(row, "erRate"), name:grid_sheet.GetCellText(row, "anaDgrDisp")});
// 			alert(i + "번째 배열값 : " + points[i]);
			category.push(grid_sheet.GetCellValue(row, "anaDgrDisp"));
		}
	}
	for(var i=0; i<prfKndCd.length; i++) {
		series[i].data = points[i];
	}

	console.log(prfKndCd.length);
	
	category = Array.from(new Set(category));
	
	// for(var i=0; i<prfKndCd.length; i++) {
	// 	progChart.AddSeries(series[i]);
	// }

	progChart.setOptions({
		series: series,
		xAxis : {
			categories : category
		},
		yAxis : {
			tickInterval : 10, //Y축 레이블 간격 설정
// 			min : minVal, //Y축 Min값 설정
			min : 0, //Y축 Min값 설정
			max : 100 //Y축 Max값 설정
		}
	}, { append: true, resetData: true })

	progChart.draw();

}


</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PROF.QLTY.TRANSITION"/></div><!--프로파일 품질추이-->

	</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<div id="search_div">
	<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
	<form id="frmSearch" name="frmSearch" method="post">
		<input type="hidden" id="bizAreaId" name="bizAreaId"/>
		<input type="hidden" id="dqiId" name="dqiId"/>
		<input type="hidden" id="ctqId" name="ctqId"/>
		<input type="hidden" id="brId" name="brId"/>
		<input type="hidden" id="baseDttm" name="baseDttm"/>
		 <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BZWR.RULE.INQ' />"> <!--업무규칙 조회-->

                   <caption><s:message code="BZWR.RULE.INQ" /></caption><!--업무규칙 조회-->

                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                  </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class=""><label for="dbSchId"><s:message code="DBMS.SCHEMA.NM"/></label></th><!--진단대상명/스키마명-->

                      <td>
			            <select id="dbConnTrgId" class="" name="dbConnTrgId">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			            </select>
			            <select id="dbSchId" class="" name="dbSchId">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			             </select>
			           </td>
                           
                           
                           <th scope="row"><label for="prfKndCd"><s:message code="PROF.KIND"/></label></th><!--프로파일종류-->

	                         <td>
                               <select id="prfKndCd"  name="prfKndCd">
								    <option value="">
								    <option value="PT01"><s:message code="RLT.ANLY"/></option><!--관계분석-->
								    <option value="PT02"><s:message code="DUP.ANLY"/></option><!--중복분석-->

								    <option value="PC02"><s:message code="CD.ANLY"/></option>
								    <option value="PC03"><s:message code="DATE.FRMT.ANLY"/></option><!--날짜형식분석-->
								    <option value="PC04"><s:message code="RNG.ANLY"/></option><!--범위분석-->
								    <option value="PC05"><s:message code="STRING.PTRN.ANLY"/></option><!--문자열패턴분석-->
								    
								</select>
                           </td>
	                        <th scope="row"><label for="anaDgr"><s:message code="ANLY.HIST" /></label></th><!--분석차수-->

                           <td>
                              <select id="anaDgr"  name="anaDgr">
								    <option value="">
								    <c:forEach var="code" items="${codeMap.anaDgrCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
                           </td>
                       </tr>
                       <tr>                               
                           <th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

                           <td>
                               <input type="text" name="dbcTblNm" id="dbcTblNm" />
                           </td>
                           <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

                           <td colspan="3">
                               <input type="text" name="dbcColNm" id="dbcColNm" />
                           </td>
                         
                           
                        </tr>    
                   </tbody>
                 </table>   
            </div>
            </fieldset>
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div><!--를 사용하시면 됩니다.--><!--클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고-->

</form>

<!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<div style="clear:both; height:5px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>

<div style="clear:both; height:20px;"><span></span></div>
<div class="main_chart_01" id="chart1">
        
            <%-- <script type="text/javascript">createIBChart("progChart", "100%", "350px");</script> --%>
        </div>


</div>
<%-- IBChartLite Init Scripts --%>
<script type="text/javascript">
createIBChart("chart1", "progChart", {
	width: "auto",
	height: "350px"
});
</script>
</body>

</html>