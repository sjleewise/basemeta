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
<title><s:message code="DATA.QLTY.INDC.QLTY.PRES"/></title><!--데이터품질지표별 품질현황-->
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script>
<script type="text/javascript">


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
	
	
	//로드시 처음부터 조회화면 출력되도록
	doAction("Search");

	
});

EnterkeyProcess("Search");

function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        SetMergeSheet(1);
        
        
//         var headers = [
//                     {Text:"<s:message code='DQ.HEADER.DQIPROG_LST'/>"
//                     	,Align:"Center"}
//                 ];

		var headers = [
                    {Text:"<s:message code='DQ.HEADER.DQI.QLTY.LST'/>"
                    	,Align:"Center"}
                ];
        //No.|진단대상ID|진단대상명(물리)|진단대상명|데이터품질지표 ID|데이터품질지표명(LNM)|데이터품질지표명|업무규칙수|분석건수|오류건수|품질점수(%)|DPMO|SIGMA

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:90,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0, ColMerge:1},
                    {Type:"Text",   	Width:50,  SaveName:"dqiId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:50,  SaveName:"dqiLnm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:100,  SaveName:"fullPath",   	Align:"Left", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,  SaveName:"brCnt",   	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:50,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:50,   SaveName:"erCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,   SaveName:"dpmo", 	Align:"Right", Edit:0, ColMerge:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Text",   	Width:50,   SaveName:"anaTyp", 	Align:"Center", Edit:0, Hidden:1, ColMerge:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        
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
			grid_sheet.DoSearch("<c:url value="/dq/report/bizrule/getDqiQuality.do" />", param);

			//차트데이터 생성
// 	    	getLineChart(param);
	    	
	    	break;
	    	
  		case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
//             progChart.Down2Image({FileName:"DqiProg", Type:IBExportType.JPEG, Width:800, Url:"./../../../js/IBChart/jsp/Down2Image.jsp"});
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
	
	//차트데이터 생성
	chartDraw(strVal, endVal);
	
}



function grid_sheet_OnSearchEnd() {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공....
	}
	//차트 클리어
	// progChart.RemoveAll();
	
	if(grid_sheet.SearchRows() == 0) return;
	
	//첫번째 행 클릭상태로 차트데이터를 생성...
	grid_sheet_OnClick(1);
	
}

$(window).on('load',function(){
	
	
});

function chartDraw(strVal, endVal)	{
	
	var category = new Array();
	//차트 카테고리값 설정...
	for(var i=strVal; i<= endVal; i++) {

		category.push (grid_sheet.GetCellValue(i, "dqiLnm"));
	}
	

	
	
	progChart.setOptions(toCamel({
		Chart : {
			BackgroundColor : "#FFFFFF", //차트 배경색 설정
			Type : "column", //차트 Type 설정
			BorderColor : "#FFFFFF"
		},
		Legend : {
			Layout : "vertical", //Legend 모양 설정
			Align : "right", //Legend 가로 정렬 설정
			VerticalAlign : "center" //Legend 세로 정렬 설정
		},
		
		Colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
		
		PlotOptions : {
			Series : {
				DataLabels : { //시리즈의 데이터 레이블 설정
					Enabled : true,
				}
			},
			Column : {
				PointPadding : 0.2 // 컬럼간의 간격 설정
			},
		},
		XAxis : {
			Categories : category,
			TickInterval : 1, //X축 레이블 간격 설정
			Labels : { //X축 레이블 설정
				Enabled : true
			},
			Title : { //X축 제목 설정
				Text : "<s:message code='DATA.QLTY.INDC.NM'/>"/*데이터품질지표명*/
			}
		},
		YAxis : [{
			TickInterval : 20, //Y축 레이블 간격 설정
			Min : 0, //Y축 Min값 설정
			Max : 100, //Y축 Max값 설정
			Title : { //Y축 제목 설정
				Text : "<s:message code='QLTY.SCR.PER'/>"/*품질점수(%)*/
			},
			Opposite : 0
		},{
			TickInterval : 1.2, //Y축 레이블 간격 설정
			Min : 0, //Y축 Min값 설정
			Max : 6, //Y축 Max값 설정
			Title:{
				Text:"<s:message code='SCOLLTMA.PER'/>" /*시그마(%)*/

			},
			Opposite : 1
		}
		]}));
	
	
	//검색결과가 없을경우 리턴...
	if(grid_sheet.SearchRows() == 0) return;
	
	
	
	//시리즈 생성
	// var seriesQuality = progChart.CreateSeries();
	// var seriesSigma = progChart.CreateSeries();
		
	// seriesQuality.SetOptions({
	// 	Name:"<s:message code='QLTY.SCR'/>",
	// 	Type:"spline"
	// });/*품질점수*/

	// seriesSigma.SetOptions({
	// 	Name:"<s:message code='SCOLLTMA'/>", /*시그마*/

	// 	Type:"column",
	// 	YAxis:1
	// });

		
	//시리즈별 데이터 생성
	var pointsQuality = new Array();
	var pointsSigma = new Array();		
	
	for(var i=strVal, j=0; i<=endVal; i++, j++) {
		pointsQuality.push(toCamel({X:j, Y:grid_sheet.GetCellValue(i, "erRate"), Name:grid_sheet.GetCellText(i, "fullPath")}));
		pointsSigma.push(toCamel({X:j, Y:grid_sheet.GetCellValue(i, "sigma"), Name:grid_sheet.GetCellText(i, "fullPath")}));
	}
	
	// seriesQuality.AddPoints(pointsQuality);
	// seriesSigma.AddPoints(pointsSigma);
	
	// progChart.AddSeries(seriesQuality);
	// progChart.AddSeries(seriesSigma);
	
	var seriesQuality = toCamel({
		Name:"<s:message code='QLTY.SCR'/>",
		Type:"spline",
		data: pointsQuality
	});/*품질점수*/

	var seriesSigma = toCamel({
		Name:"<s:message code='SCOLLTMA'/>", /*시그마*/
		Type:"column",
		YAxis:1,
		data: pointsSigma
	});

	progChart.setOptions({
		series: [
			seriesQuality,
			seriesSigma
		]
	}, { append: true, resetData: true })
	
	progChart.draw();
	
}


</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DATA.QLTY.INDC.QLTY.PRES"/></div><!--데이터품질지표별 품질현황-->
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
		 <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='QLTY.PRES.INQ'/>">
                   <caption><s:message code="QLTY.PRES.INQ"/></caption><!--품질현황 조회-->

                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                   <col style="width:10%;" />
                   <col style="width:15%;" />
                  </colgroup>
                   
                  <tbody>  
                   	<tr>                        
                    	<th scope="row"><label for="sortTyp"><s:message code="INQ.BASE"/></label></th><!--검색기준-->
                    	<td>
	                    <select id="sortTyp" name="sortTyp" onChange="setSheetColumn()">
	                    <option value="dbConnTrgLnm"><s:message code="DB.MS" /></option><!--진단대상명-->


	                    <option value="dbcTblNm"><s:message code="TBL.NM" /></option><!--테이블명-->
	                    <option value="dbcColNm"><s:message code="CLMN.NM" /></option><!--컬럼명-->

	                    </select>
	                    </td> 
	                    
	                    <th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->


                        	<td>
                        	 <select id="dbConnTrgId"  name="dbConnTrgId">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
							</select>
                            </td>
                        <th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

	                    	<td>
	                    	<input class="wd90p" type="text" id="dbcTblNm" name="dbcTblNm" />
	                        </td> 
                        <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

	                    	<td>
	                    	<input class="wd90p" type="text" id="dbcColNm" name="dbcColNm" />
	                        </td> 
                   </tr>
                   
                   	<tr>                        
                    	<th scope="row"><label for="bizAreaLnm"><s:message code="BZWR.TRRT.NM" /></label></th><!--업무영역명-->

                        	<td>
                        	<input class="wd60p" type="text" id="bizAreaLnm" name="bizAreaLnm" />
                        	<button class="btnBizAreaLnmPop" id="btnBizAreaLnmPop"><s:message code="INQ" /></button><!--검색-->
                            </td>
                        <th scope="row"><label for="dqiLnm"><s:message code="QLTY.INDC.NM"/></label></th><!--품질지표명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="dqiLnm" name="dqiLnm" />
	                    	<button class="btnDqiLnmPop" id="btnDqiLnmPop"><s:message code="INQ" /></button><!--검색-->
	                        </td> 
                        <th scope="row"><label for="ctqLnm"><s:message code="IMCE.INFO.ITEM.NM"/></label></th><!--중요정보항목명-->
</th>
	                    	<td colspan="3">
	                    	<input class="wd80p" type="text" id="ctqLnm" name="ctqLnm" />
	                    	<button class="btnCtqLnmPop" id="btnCtqLnmPop"><s:message code="INQ" /></button><!--검색-->
	                        </td> 
                   </tr>
                   
                    <tr>                        
                    	<th scope="row"><label for="brNm">업무규칙/프로파일명<%-- <s:message code="BZWR.RGR.NM"/>  --%></label></th><!--업무규칙명-->
                        	<td colspan="3">
                        	<input class="wd90p" type="text" id="brNm" name="brNm" />
                            </td>
                          <th scope="row"><label for="tgtVrfJoinCd"><s:message code="COMPARE.VRFC"/></label></th><!--비교검증-->

	                    	<td>
	                    	    <select id="tgtVrfJoinCd"  name="tgtVrfJoinCd">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <option value="Y">Y</option>
								    <option value="N">N</option>
								</select>
	                        </td>
                        <th scope="row"><label for="anaDgr"><s:message code="ANLY.HIST" /></label></th><!--분석차수-->

	                    	<td>
	                    	<select id="anaDgr"  name="anaDgr">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.anaDgrCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
	                        </td> 
                   </tr>
                   
                   </tbody>
                 </table>   
            </div>
            </fieldset>
	
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