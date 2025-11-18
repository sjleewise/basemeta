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
<title><s:message code="BZWR.RGR.QLTY.TRANSITION"/></title><!--업무규칙 품질추이-->


<!-- ibchart.js  -->
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
	
	
	
	//로드시 처음부터 조회화면 출력되도록
	doAction("Search");
	
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
	
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbSchLnm"), "DBSCH");
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	setautoComplete($("#frmSearch #bizAreaLnm"), "BIZLNM");
	setautoComplete($("#frmSearch #dqiLnm"), "DQILNM");
	setautoComplete($("#frmSearch #ctqLnm"), "CTQLNM");
	setautoComplete($("#frmSearch #brNm"), "BRNM");
	

});

EnterkeyProcess("Search");


/* ibchart 설정  */
$(window).on('load',function(){
	bizruleChart.SetOptions({
		Chart : {
			BackgroundColor : "#ffffff", //차트 배경색 설정
			Type : "line" //차트 Type 설정
		},
		
		Legend : {
			Layout : "vertical", //Legend 모양 설정
			Align : "right", //Legend 가로 정렬 설정
			VerticalAlign : "top" //Legend 세로 정렬 설정
		},
		
		PlotOptions : {
			Series : {
				DataLabels : { //시리즈의 데이터 레이블 설정
					Enabled : true,
				}
			},
			Column : {
				PointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		XAxis : {
			TickInterval : 1, //X축 레이블 간격 설정
			Labels : { //X축 레이블 설정
				Enabled : true
			},
			Title:{ //X축 제목 설정
				Text : "<s:message code='ANLY.ODR' />"/*분석차수*/

			}
		},
		YAxis : {
			TickInterval : 20, //Y축 레이블 간격 설정
			Min : 0, //Y축 Min값 설정
			Max :100,
			Title : { //Y축 제목 설정
				Text : "<s:message code='QLTY.SCR.PER'/>" /*품질점수(%)*/
			}
		}
	});
	
});


function chartDrow()	{
		
		//차트 클리어
		bizruleChart.RemoveAll();
		
		//검색결과가 없을경우 리턴...
		if(grid_sheet.SearchRows() == 0) return;
		
		//진단대상논리명 종류
		var tmpDbConnTrgLnm = "";
		var dbConnTrgLnm;
		
		//최대차수를 배열로 구현 각각의 시리즈에 다른값 적용하기 위해.
		var maxAnaDgr = new Array();
		var countAnaDgr=0;
		var count=0;
		
		//분기되는 row의 번호 1부터 시작한다.
		var tmpCount=1;
		var strCount=1;
		
		//프로파일컬럼 시작위치
		var tmpStrDbConnTrgLnm = "";
		var strDbConnTrgLnm;
		
		for(var i=0; i<grid_sheet.SearchRows(); i++) {
			 
			//최대차수 계산...
			 countAnaDgr++;
			 tmpCount++;
			//진단대상논리명 추출(차수에 따른 중복 제거)
			if(grid_sheet.GetCellText(i+1, "dbConnTrgLnm") != grid_sheet.GetCellText(i+2, "dbConnTrgLnm")) {
				
				//각 시리즈에서 최대차수값을 저장.
				maxAnaDgr[count]=countAnaDgr;
				countAnaDgr=0;
				count++;
				
				if(tmpDbConnTrgLnm != "") {
					tmpDbConnTrgLnm += "|";
					tmpStrDbConnTrgLnm += "|";
				}
				tmpDbConnTrgLnm += grid_sheet.GetCellText(i+1, "dbConnTrgLnm");	
				//각 시리즈의 첫번째 start값 
				tmpStrDbConnTrgLnm += strCount;
				strCount=tmpCount;
				
			}else{
				
			}
			
		}
		dbConnTrgLnm = tmpDbConnTrgLnm.split("|");
		strDbConnTrgLnm = tmpStrDbConnTrgLnm.split("|");
		/* alert(tmpDbConnTrgLnm);
		alert(tmpStrDbConnTrgLnm); */
		
		//시리즈 생성
		var series = new Array();
		for(var i=0; i<dbConnTrgLnm.length; i++) {
			series[i] = bizruleChart.CreateSeries();
			//각 시리즈별 이름과 타입 설정
			series[i].SetOptions({
				Name:dbConnTrgLnm[i],
				Type:"spline"
			});	
		}
		
		//시리즈별 데이터 생성
		var points = new Array();
			
		for(var i=0; i<dbConnTrgLnm.length; i++) {
			points[i] = new Array();
			for(var j=0;j<maxAnaDgr[i];j++) {	
				var row = parseInt(strDbConnTrgLnm[i]) + parseInt(j);
				points[i].push({X:j+1, Y:grid_sheet.GetCellValue(row, "erRate"), Name:grid_sheet.GetCellValue(row, "anaDgrDisp")});
			}
		}
		for(var i=0; i<dbConnTrgLnm.length; i++) {
			series[i].AddPoints(points[i]);
			
		}
		
		for(var i=0; i<dbConnTrgLnm.length; i++) {
			bizruleChart.AddSeries(series[i]);
		}
		
		 bizruleChart.Draw();
};




function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(1);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.BIZRULEPROG_LST'/>" ,Align:"Center"}
                ];
        //No.|진단대상ID|진단대상명|진단대상논리명|분석차수(숨김)|분석차수|분석일자|분석건수|오류건수|<s:message code="DIAG.TRGT.NM" /><!--진단대상명-->|DPMO|SIGMA

        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
        var cols = [                        
                    {Type:"Seq",    	Width:20,   SaveName:"ibsSeq",      Align:"Center", Edit:0, Hidden:0 ,ColMerge:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgId",   	Align:"Left", Edit:0, Hidden:1,ColMerge:0},
                    {Type:"Text",   	Width:50,  SaveName:"dbConnTrgPnm",   	Align:"Left", Edit:0, Hidden:1 ,ColMerge:1},
                    {Type:"Text",   	Width:90,  SaveName:"dbConnTrgLnm",   	Align:"Left", Edit:0, Hidden:0 ,ColMerge:1},
                    {Type:"Text", 		Width:60,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:1,ColMerge:0},
                    {Type:"Text", 		Width:60,   SaveName:"anaDgrDisp",  Align:"Center", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Date", 		Width:60,   SaveName:"anaStrDtm",  Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd",ColMerge:0},
                    {Type:"Int",   	Width:50,   SaveName:"anaCnt", 	Align:"Right", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Int",   	Width:50,   SaveName:"erCnt", 	Align:"Right", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Text",   	Width:50,   SaveName:"erRate", 	Align:"Right", Edit:0, Hidden:0,ColMerge:0},
                    {Type:"Text",   	Width:50,   SaveName:"dpmo", 	Align:"Right", Edit:0, ColMerge:0, Hidden:1},
                    {Type:"Text",   	Width:50,   SaveName:"sigma", 	Align:"Right", Edit:0, Hidden:0,ColMerge:0}
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
	    	grid_sheet.DoSearch("<c:url value="/dq/report/bizrule/getBizruleProgQuality.do" />", param);
	    	
	    	break;
	    	
  		case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
//             progChart.Down2Image({FileName:"BizruleProg", Type:IBExportType.JPEG, Width:800, Url:"./../../../js/IBChart/jsp/Down2Image.jsp"});
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
	//차트데이터 생성, .DoSearch()로 조회가 되었을때 실행
	chartDrow();
}

 
</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="BZWR.RGR.QLTY.TRANSITION"/></div><!--업무규칙 품질추이-->

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
                        	<input class="wd80p" type="text" id="bizAreaLnm" name="bizAreaLnm" />
                        	<button class="btnBizAreaLnmPop" id="btnBizAreaLnmPop"><s:message code="INQ" /></button><!--검색-->
                            </td>
                        <th scope="row"><label for="dqiLnm"><s:message code="QLTY.INDC.NM"/></label></th><!--품질지표명-->
	                    	<td>
	                    	<input class="wd80p" type="text" id="dqiLnm" name="dqiLnm" />
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
                    	<th scope="row"><label for="brNm"><s:message code="BZWR.RGR.NM"/> </label></th><!--업무규칙명-->
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

<div style="clear:both; height:5px;"><span></span></div>
	
	<div class="main_chart_01"  >
	    <script type="text/javascript">
	    	createIBChart("bizruleChart", "100%", "400px");
	    </script>
	</div>
</div>

</body>
</html>