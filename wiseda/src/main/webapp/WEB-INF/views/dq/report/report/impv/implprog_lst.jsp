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
<title><s:message code="IMPV.PLAN.PRES"/></title><!--개선계획 현황-->
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
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
                    {Text:"<s:message code='DQ.HEADER.IMPLPROG_LST'/>"
                    	,Align:"Center"}
                ];
        //No.|진단대상ID|진단대상명|부서ID|부서명(NM)|부서명|업무영역ID|업무영역명(LNM)|업무영역명|데이터품질지표ID|데이터품질지표명(LNM)|데이터품질지표명|중요정보항목ID|중요정보항목명(LNM)|중요정보항목명|컬럼명|분석차수(숨김)|분석차수|분석일자|업무규칙 분석건수|업무규칙 오류건수|개선계획 등록건수|데이터 분석건수|데이터 오류건수|품질점수(%)|DPMO|SIGMA

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:30,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:10,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:180,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   	Width:10,  SaveName:"deptId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:10,  SaveName:"deptNm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:180,  SaveName:"deptFullPath",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:10,  SaveName:"bizAreaId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:10,  SaveName:"bizAreaLnm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:180,  SaveName:"bizAreaFullPath",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:10,  SaveName:"dqiId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:10,  SaveName:"dqiLnm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:180,  SaveName:"dqiFullPath",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:10,  SaveName:"ctqId",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:10,  SaveName:"ctqLnm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:180,  SaveName:"ctqFullPath",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   	Width:150,  SaveName:"dbcColNm",   	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text", 		Width:10,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1, ColMerge:0},
                    {Type:"Text", 		Width:70,   SaveName:"anaDgrDisp",  Align:"Center", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Date", 		Width:80,   SaveName:"anaStrDtm",  Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd", ColMerge:0},
                    {Type:"Int",   	Width:100,   SaveName:"totBrCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:100,   SaveName:"tgtBrCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:100,   SaveName:"csAnaCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:100,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Int",   	Width:100,   SaveName:"erCnt", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:80,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0},
                    {Type:"Float",   	Width:80,   SaveName:"dpmo", 	Align:"Right", Edit:0, Hidden:1, ColMerge:0},
                    {Type:"Float",   	Width:80,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0, ColMerge:0}
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
			grid_sheet.DoSearch("<c:url value="/dq/report/impv/getImPlProgQuality.do" />", param);


	    	break;
	    	
  		case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
//             progChart.Down2Image({FileName:"ImPlProg", Type:IBExportType.JPEG, Width:800, Url:"./../../../js/IBChart/jsp/Down2Image.jsp"});
            break;
    }
}


function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	if(row < 1) return;

}



function grid_sheet_OnSearchEnd() {
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//조회 성공....
	}
	
	//차트데이터 생성
	chartDraw();
}

$(window).on('load',function(){
	
	
	
// 	chartDraw();
	//초기화면 시트컬럼 설정
	setSheetColumn($("#sortTyp").val());
	

});

function chartDraw()	{
	
	//차트 클리어
	progChart.RemoveAll();
	
	var category = new Array(); //카테고리를 보여줄 변수...
	var titleText = ""; //x축 기준을 설정할 변수...
	
	//시트에서 가져와 push할 헤더값 설정...
	var cellText = "";
	
	//차트 카테고리값 설정... (컬럼명 검색시는 차트표시 안하므로 return)
	if($("#sortTyp").val() == "dbcColNm") {
		return;
	} else if($("#sortTyp").val() == "dbConnTrgLnm") {
		titleText = grid_sheet.GetCellText(0, "dbConnTrgLnm");
		cellText = "dbConnTrgLnm";
		for(var i=0; i<= grid_sheet.SearchRows(); i++) {
			category.push (grid_sheet.GetCellValue(i+1, "dbConnTrgLnm") + "(" + grid_sheet.GetCellValue(i+1, "anaDgrDisp") + ")");
		}
	} else if($("#sortTyp").val() == "deptNm") {
		titleText = grid_sheet.GetCellText(0, "deptFullPath");
		cellText = "deptFullPath";
		for(var i=0; i<= grid_sheet.SearchRows(); i++) {
			category.push (grid_sheet.GetCellValue(i+1, "deptNm") + "(" + grid_sheet.GetCellValue(i+1, "anaDgrDisp") + ")");
		}
	} else if($("#sortTyp").val() == "bizAreaLnm") {
		titleText = grid_sheet.GetCellText(0, "bizAreaFullPath");
		cellText = "bizAreaFullPath";
		for(var i=0; i<= grid_sheet.SearchRows(); i++) {
			category.push (grid_sheet.GetCellValue(i+1, "bizAreaLnm") + "(" + grid_sheet.GetCellValue(i+1, "anaDgrDisp") + ")");
		}
	} else if($("#sortTyp").val() == "dqiLnm") {
		titleText = grid_sheet.GetCellText(0, "dqiFullPath");
		cellText = "dqiFullPath";
		for(var i=0; i<= grid_sheet.SearchRows(); i++) {
			category.push (grid_sheet.GetCellValue(i+1, "dqiLnm") + "(" + grid_sheet.GetCellValue(i+1, "anaDgrDisp") + ")");
		}
	} else if($("#sortTyp").val() == "ctqLnm") {
		titleText = grid_sheet.GetCellText(0, "ctqFullPath");
		cellText = "ctqFullPath";
		for(var i=0; i<= grid_sheet.SearchRows(); i++) {
			category.push (grid_sheet.GetCellValue(i+1, "ctqLnm") + "(" + grid_sheet.GetCellValue(i+1, "anaDgrDisp") + ")");
		}
	}

	
	progChart.SetOptions({
		Chart : {
			BackgroundColor : "#FFFFFF", //차트 배경색 설정
			Type : "column", //차트 Type 설정
			BorderColor : "#EEEEEE"
		},
		Legend : {
			Layout : "horizontal", //Legend 모양 설정
			Align : "center", //Legend 가로 정렬 설정
			VerticalAlign : "bottom" //Legend 세로 정렬 설정
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
				Text : titleText
			}
		},
		YAxis : {
// 			TickInterval : 1, //Y축 레이블 간격 설정
// 			Min : 0, //Y축 Min값 설정
// 			Max : 5, //Y축 Max값 설정
			Title : { //Y축 제목 설정
				Text : "<s:message code='BZWR.RGR.CNT'/>" /*업무규칙수*/

			},
			Opposite : 0
		}
		});
	
	
	
	//검색결과가 없을경우 리턴...
	if(grid_sheet.SearchRows() == 0) return;
	
	//시리즈 생성
	var seriesTotBrCnt = progChart.CreateSeries();
	var seriesTgtBrCnt = progChart.CreateSeries();
	var seriesCsAnaCnt = progChart.CreateSeries();
		
	seriesTotBrCnt.SetOptions({
		Name:"<s:message code='BZWR.RULE.ANLY.CCNT' />",
		Type:"bar"/*업무규칙 분석건수*/

	});
	seriesTgtBrCnt.SetOptions({
		Name:"<s:message code='BZWR.RULE.EROR.CCNT' />", /*업무규칙 오류건수*/

		Type:"bar"
	});
	seriesCsAnaCnt.SetOptions({
		Name:"<s:message code='IMPV.PLAN.REG.CCNT' />",/*개선계획 등록건수*/
		Type:"bar"
	});
		
	//시리즈별 데이터 생성
	var pointsTotBrCnt = new Array();
	var pointsTgtBrCnt = new Array();		
	var pointsCsAnaCnt = new Array();
	
	for(var i=0; i<grid_sheet.SearchRows(); i++) {
		pointsTotBrCnt.push({X:i, Y:grid_sheet.GetCellValue(i+1, "totBrCnt"), Name:grid_sheet.GetCellText(i+1, cellText)+"("+grid_sheet.GetCellText(i+1, "anaDgrDisp")+")"});
		pointsTgtBrCnt.push({X:i, Y:grid_sheet.GetCellValue(i+1, "tgtBrCnt"), Name:grid_sheet.GetCellText(i+1, cellText)+"("+grid_sheet.GetCellText(i+1, "anaDgrDisp")+")"});
		pointsCsAnaCnt.push({X:i, Y:grid_sheet.GetCellValue(i+1, "csAnaCnt"), Name:grid_sheet.GetCellText(i+1, cellText)+"("+grid_sheet.GetCellText(i+1, "anaDgrDisp")+")"});
	}
	
	seriesTotBrCnt.AddPoints(pointsTotBrCnt);
	seriesTgtBrCnt.AddPoints(pointsTgtBrCnt);
	seriesCsAnaCnt.AddPoints(pointsCsAnaCnt);
	
	progChart.AddSeries(seriesCsAnaCnt);
	progChart.AddSeries(seriesTgtBrCnt);
	progChart.AddSeries(seriesTotBrCnt);
	

	
	progChart.Draw();

}

function setSheetColumn(sortTyp) {
	
	grid_sheet.SetColHidden("dbConnTrgLnm", 1);
	grid_sheet.SetColHidden("deptFullPath", 1);
	grid_sheet.SetColHidden("bizAreaFullPath", 1);
	grid_sheet.SetColHidden("dqiFullPath", 1);
	grid_sheet.SetColHidden("ctqFullPath", 1);
	grid_sheet.SetColHidden("dbcColNm", 1);
	grid_sheet.RemoveAll();
	progChart.RemoveAll();
	
	if($("#sortTyp").val() == "dbConnTrgLnm") {
		grid_sheet.SetColHidden("dbConnTrgLnm", 0);
	} else if($("#sortTyp").val() == "deptNm") {
		grid_sheet.SetColHidden("deptFullPath", 0);
	} else if($("#sortTyp").val() == "bizAreaLnm") {
		grid_sheet.SetColHidden("bizAreaFullPath", 0);
	} else if($("#sortTyp").val() == "dqiLnm") {
		grid_sheet.SetColHidden("dqiFullPath", 0);
	} else if($("#sortTyp").val() == "ctqLnm") {
		grid_sheet.SetColHidden("ctqFullPath", 0);
	} else {
		grid_sheet.SetColHidden("dbcColNm", 0);
	}
	doAction("Search");
}


</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="IMPV.PLAN.PRES"/></div><!--개선계획 현황-->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='IMPV.PLAN.INQ' />"> <!--개선계획 조회-->
                   <caption><s:message code="IMPV.PLAN.INQ" /></caption>
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
                    	<th scope="row"><label for="sortTyp"><s:message code="INQ.BASE"/></label></th><!--검색기준-->
	                    <td>
	                    <select id="sortTyp" name="sortTyp" onChange="setSheetColumn()">
	                    <option value="dbConnTrgLnm"><s:message code="DB.MS" /></option><!--진단대상명-->
	                    <option value="deptNm"><s:message code="DEPT.NM" /></option><!--부서명-->
	                    <option value="bizAreaLnm"><s:message code="BZWR.TRRT.NM" /></option><!--업무영역명-->
	                    <option value="dqiLnm"><s:message code="DATA.QLTY.INDC.NM"/></option><!--데이터품질지표명-->
	                    <option value="ctqLnm"<s:message code="IMCE.INFO.ITEM.NM"/></option><!--중요정보항목명-->


	                    <option value="dbcColNm"><s:message code="CLMN.NM" /></option><!--컬럼명-->

	                    </select>
	                    </td> 
                    	<th scope="row"><label for="anaJobNm"><s:message code="BZWR.RGR.NM"/></label></th><!--업무규칙명-->

                        	<td>
                        	<input class="wd60p" type="text" id="anaJobNm" name="anaJobNm"/>
                            </td>
                        <th scope="row"><label for="anaDgr"><s:message code="ANLY.ODR" /></label></th><!--분석차수-->

	                    	<td>
	                    	<select id="anaDgr"  name="anaDgr">
								    <option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.anaDgrCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								</select>
	                        </td>
	                    
                          
                   </tr>
                   	<tr>                        
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
	                    	<input class="wd60p" type="text" id="dbcTblNm" name="dbcTblNm"/>
	                        </td> 
                        <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="dbcColNm" name="dbcColNm"/>
	                        </td> 
                   </tr>
                   	<tr>                        
                    	<th scope="row"><label for="bizAreaLnm"><s:message code="BZWR.TRRT.NM" /></label></th><!--업무영역명-->

                        	<td>
                        	<input class="wd60p" type="text" id="bizAreaLnm" name="bizAreaLnm" class="wd200"/>
                        	<button class="btnBizAreaLnmPop" id="btnBizAreaLnmPop"><s:message code="INQ" /></button><!--검색-->
                            </td>
                        <th scope="row"><label for="dqiLnm"><s:message code="QLTY.INDC.NM"/></label></th><!--품질지표명-->

	                    	<td>
	                    	<input class="wd60p" type="text" id="dqiLnm" name="dqiLnm" class="wd200"/>
	                    	<button class="btnDqiLnmPop" id="btnDqiLnmPop"><s:message code="INQ" /></button><!--검색-->
	                        </td> 
                        <th scope="row"><label for="ctqLnm"><s:message code="IMCE.INFO.ITEM.NM"/></label></th><!--중요정보항목명-->
</th>
	                    	<td>
	                    	<input class="wd60p" type="text" id="ctqLnm" name="ctqLnm" class="wd200"/>
	                    	<button class="btnCtqLnmPop" id="btnCtqLnmPop"><s:message code="INQ" /></button><!--검색-->
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
        	
            <script type="text/javascript">createIBChart("progChart", "100%", "100%");</script>
        </div>


</div>
</body>

</html>