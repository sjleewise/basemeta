<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
StringBuffer sb   =   new StringBuffer();
sb.append("<chart caption='<s:message code="MEAL" />'>"); /* 식단 */
sb.append("<set name='<s:message code="BREAD" />' value='100'/>"); /* 빵 */
sb.append("<set name='<s:message code="MILK" />' value='200'/>"); /* 우유 */
sb.append("<set name='<s:message code="JUICE" />' value='250'/>"); /* 주스 */
sb.append("</chart>");
String xmlData    =   sb.toString();
 
%>

<html>
<head>
<title><s:message code="MAIN.SCRN" /></title> <!-- 메인화면 -->
<script type="text/javascript" src='<c:url value="/js/FusionCharts/FusionCharts/FusionCharts.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/FusionCharts/FusionWidgets/FusionCharts.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript">
//텍스트 롤링 함수
function scrolltext() {
    var textlist = $('#scroll_test');
    textlist.animate({marginTop:'-12px'}, 1000, function(){
        textlist.css('marginTop', '0px').append(textlist.find('li:first'));
    });
}
		
$(document).ready(function(){
	
// 	$.getJSON('<c:url value="/portal/dashboard/deptStandardChart.do"/>', function(data){
	
// 		alert("여기 수행");
// 		if(data ==  null) return;
// // 		$('#rqstCnt').text(data[0]);
// // 		$('#aprvCnt').text(data[1]);
 		
// 	}); 
	
	
	$("#searchNm").focus();
	
		// 조회 Event Bind
 		$(".main_search_bt").click(function(event){
 			event.preventDefault();
 			if($.trim($('form[name="search"] input[name="searchNm"]').val()) == "") {
 				showMsgBox("ERR", "<s:message code='MSG.INQ.COND.INPT' />"); /* 검색조건을 입력해주세요. */
 				return false;
 			}
 			if($.trim($('form[name="search"] input[name="searchNm"]').val()).length < 2) {
 				showMsgBox("ERR", "<s:message code='MSG.INQ.COND.2WORD.INPT' />"); /* 검색조건은 2자 이상 입력해야 합니다. */
 				return false;
 			}
 			$('form[name="search"] input[name="searchNm"]').val($.trim($('form[name="search"] input[name="searchNm"]').val()));
 			doAction("Search");
 		});
		// 인기검색어
		
		$('div#rank_arrow').click(function(){
			var tmptop = $(this).offset().top + $(this).height()+23;
			var tmpleft = $(this).offset().left + $(this).width() - $('ol.main_rank_list').width()-5;
			//alert(tmptop+":"+tmpleft);
			$('ol.main_rank_list').css({
					top:tmptop,
					left:tmpleft
			}).show();
/* 			$('ol.main_rank_list').show().position(
					 {
				  		my: "top+100 right-100",
						at: "right+10 bottom+10",
						of: "div#rank_arrow",
						collision: "none"
				      }		
			); */
		});
		
		$('ol.main_rank_list').mouseleave(function(){
			$(this).hide();
		});
		
 		
		//인기검색어 스크롤 텍스트 처리하도록 처리
 		$.getJSON('<c:url value="/portal/totalsearch/TotalSearchWord.json"/>', function(data){
			if(data ==  null) return;

			var divaction = $('div.main_search_popular_link'); //스크롤 div 대상
	 		arrpapul = new Array();
	 		cntpapul = 0;
	 		cntp = 0;
	 		$('ol.main_rank_list').empty();
			for(var i=0; i<data.length; i++) {
					var lilink = "<li class='main_rank_0"+(i+1)+"'><a href='javascript:goSearch(\""+data[i].searchWord+"\");'>"+data[i].searchWord+"</a></li>";
					$('ol.main_rank_list').append(lilink);
				
					if(cntp%3 == 0) {
						arrpapul[cntpapul] = "<li style='overflow: hidden; white-space: nowrap; text-overflow:ellipsis; display:block;'>";
						arrpapul[cntpapul] += '<a href="javascript:goSearch(\''+data[i].searchWord+'\');">'+data[i].searchWord+'</a>';
						cntpapul++;
					} else {
						arrpapul[cntpapul-1] += ',   <a href="javascript:goSearch(\''+data[i].searchWord+'\');">'+data[i].searchWord+'</a>';
					}
					if(cntp%3 == 2) {
						arrpapul[cntpapul-1] += "</li>";
					}
					cntp++;
			}
			if((cntp-1)%3 != 2) {
				arrpapul[cntpapul-1] += "</li>";
			}
			searchHtml = '<ul id="scroll_test">';
			for(i=0;i<cntpapul;i++) {
				searchHtml +=arrpapul[i];
			}
			searchHtml += '</ul>';
			divaction.html(searchHtml);
			setInterval(scrolltext, 5*1000);
	 		
		}); 
				
 		getChart();

		
 		//메인팝업띄우기 
 		$(".main_icon .main_icon_list li a").click(function(){
 			
 			var idx = $(".main_icon .main_icon_list li a").index(this);   
 			if(idx == 0){
 				OpenWindow('<c:url value="/portal/pop/mainPop1.do" />', 'popupOwnerShip', '800px', '395px', 'yes');
 			}else if(idx == 1){
 				OpenWindow('<c:url value="/portal/pop/mainPop2.do" />', 'popupOwnerShip', '800px', '570px', 'yes');
 			}else if(idx == 2){
 				OpenWindow('<c:url value="/portal/pop/mainPop3.do" />', 'popupOwnerShip', '800px', '460px', 'yes');
 			}else if(idx == 3){
 				OpenWindow('<c:url value="/portal/pop/mainPop4.do" />', 'popupOwnerShip', '800px', '610px', 'yes');
 			}else if(idx == 4){
 				OpenWindow('<c:url value="/portal/pop/mainPop5.do" />', 'popupOwnerShip', '800px', '602px', 'yes');
 			}else{
 				return false;
 			}
 		});
 		
 		
 		$( "#rightchart" ).button({
 		      icons: {
 		        primary: "ui-icon-circle-triangle-e"
 		      },
 		      text: false
 		    }).click(function(){
 			$('#chart1').hide();
 			$('#chart3').show();
 			$('#widgetsChart').attr("style","width:100%;height:170px;");
 			$(this).hide();
 		});
 		$( "#leftchart" ).button({
 		      icons: {
 		        primary: "ui-icon-circle-triangle-w"
 		      },
 		      text: false
 		    }).click(function(){
 			$('#chart1').show();
 			$( "#rightchart" ).show();
 			$('#chart3').hide();
 		});
 		
});

$(window).resize(function(){
	
	var tmptop = $('div#rank_arrow').offset().top + $('div#rank_arrow').height()+23;
	var tmpleft = $('div#rank_arrow').offset().left + $('div#rank_arrow').width() - $('ol.main_rank_list').width()-5;
	//alert(tmptop+":"+tmpleft);
	$('ol.main_rank_list').css({
			top:tmptop,
			left:tmpleft
	});
                
        // Window Resize 시  컬럼  Width%
//         var interval = "5|10|10|10|15|15|35";
        
//         chgSize(mySheet, interval);
});

$(window).on('load',function(){
	testChart.SetOptions({
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
				PointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		XAxis : {
			TickInterval : 2, //X축 레이블 간격 설정
			Labels : { //X축 레이블 설정
				Enabled : false
			}
		},
		YAxis : {
			TickInterval : 30, //Y축 레이블 간격 설정
			Min : 0, //Y축 Min값 설정
			Title : { //Y축 제목 설정
				Text : ""
			}
		}
	});
	
	//품질현황 차트 초기화
	widgetsChart.SetOptions({
		Chart : {
			BackgroundColor : "#FFFFFF", //차트 배경색 설정
			Type : "column", //차트 Type 설정
			BorderColor : "#FFFFFF" //차트 테두리 색 설정
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
				PointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		XAxis : {
			TickInterval : 2, //X축 레이블 간격 설정
			Labels : { //X축 레이블 설정
				Enabled : false
			}
		},
		YAxis : {
			TickInterval : 30, //Y축 레이블 간격 설정
			Min : 0, //Y축 Min값 설정
			Title : { //Y축 제목 설정
				Text : ""
			}
		}
	});
	widgetsChartDraw();
	barchartDrow();
	
	
	//Series 설정
// 	testChart.SetSeriesOptions([{
// 		Name : "매출목표",
// 		Data : [
// 				{Y:60, Color : "#4572A7"},
// 				{Y:38, Color : "#AA4643"},
// 				{Y:35, Color : "#89A54E"},
// 				{Y:73, Color : "#80699B"},
// 				{Y:80, Color : "#3D96AE"},
// 				{Y:35, Color : "#DB843D"}
// 		]
// 	}], 1);
	//testChart.Draw();
	
});

function widgetsChartDraw()	{
	$.getJSON('<c:url value="/portal/dashboard/widgetsChartListJson.do"/>', function(data){

		if(data ==  null) return;

		widgetsChart.RemoveAll();

		//시리즈 3개 생성
		var series 	= widgetsChart.CreateSeries();		

		
		
		//각 시리즈별 이름과 타입 설정
		series.SetOptions({
			Name:"<s:message code='QLTY' />",/* 품질 */
			Type:"column"
		});		

		
		//시리즈별 데이터 생성
		var points = new Array();

		//데이터 길이 확인
		var cnt = data.length;
		for(i=0;i<cnt;i++) {
			points.push({X:i, Y:data[i].errRate, Name:data[i].bizareaNm});
		}

		series.AddPoints(points);

		widgetsChart.AddSeries(series);

		widgetsChart.Draw();

	});
}


function barchartDrow()	{
	$.getJSON('<c:url value="/portal/dashboard/standardChartListjson.do"/>', function(data){
		if(data ==  null) return;
		
		testChart.RemoveAll();
		
		//시리즈 3개 생성
		var seriesTot 	= testChart.CreateSeries();		
		var seriesStnd 	= testChart.CreateSeries();		
		var seriesNstnd = testChart.CreateSeries();
		
		
		//각 시리즈별 이름과 타입 설정
		seriesTot.SetOptions({
			Name:"<s:message code='WHL' />", /* 전체 */
			Type:"column"
		});		
		seriesStnd.SetOptions({
			Name:"<s:message code='STRD' />", /* 표준 */
			Type:"column"
		});		
		seriesNstnd.SetOptions({
			Name:"<s:message code='NO.STRD' />", /* 비표준 */
			Type:"column"
		});	
		
		//시리즈별 데이터 생성
		var totpoints = new Array();
		var stndpoints = new Array();
		var nstndpoints = new Array();
		
		//데이터 길이 확인
		var cnt = data.length;
		for(i=0;i<cnt;i++) {
			totpoints.push({X:i, Y:data[i].cnt, Name:data[i].subjNm});
			stndpoints.push({X:i, Y:data[i].cntY, Name:data[i].subjNm});
			nstndpoints.push({X:i, Y:data[i].cntN, Name:data[i].subjNm});
		}

		seriesTot.AddPoints( totpoints );
		seriesStnd.AddPoints( stndpoints );
		seriesNstnd.AddPoints( nstndpoints );
		
		testChart.AddSeries(seriesTot);
		testChart.AddSeries(seriesStnd);
		testChart.AddSeries(seriesNstnd);
		
		testChart.Draw();
	});
}

$(document).keypress(function(e) {
	  if(e.which == 13) {
	    // enter pressed
//     alert("you pressed enter key");
		  $(".main_search_bt").click();
	  }
	});

function fn_egov_downFile(atchFileId, fileSn){
	window.open("<c:url value='/commons/fms/filedown.do?atchFileId="+atchFileId+"&fileSn="+fileSn+"'/>");
}


function doAction(sAction)
{
		
	switch(sAction)
    {
	    case "Search":        //작성완료
	    	$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch/TotalSearchCtrl.do"/>').submit();
	    
			break;
	   
	    case "Down2Excel":	//엑셀내려받기
			mySheet.Down2Excel({HiddenColumn:1, Merge:1});
			break;	   
    }		
}

function goSearch(searchWord)
{
		$('form[name="search"] input[name="searchNm"]').val(searchWord);
		doAction("Search");
}

function doDiv(status) {
	if(status == "rqst") {
		document.getElementById("aprvListDiv").style.display = "none";
		document.getElementById("rqstListDiv").style.display = "";
	} else {
		document.getElementById("rqstListDiv").style.display = "none";
		document.getElementById("aprvListDiv").style.display = "";
	}
	
}

function getChart() {
	var chartUrl = '<c:url value="/swffile/fusionwidgets/AngularGauge.swf"/>';
	var chart = new FusionCharts(chartUrl, "ChartId", "300px", "170px", "0", "0");
	   chart.setDataURL('<c:url value="/portal/dashboard/ErrChartCtrl.do"/>');
	   chart.render("errChartList");
	   
//    	var chartUrl = '<c:url value="/swffile/fusioncharts/MSColumn2D.swf"/>';
// 	var chart = new FusionCharts(chartUrl, "ChartId", "325px", "170px", "0", "0");
// 	   chart.setDataURL('<c:url value="/portal/dashboard/widgetsChartList.do"/>');
// 	   chart.render("widgetsChart");
	   
//    	var chartUrl = '<c:url value="/swffile/fusioncharts/MSColumn2D.swf"/>';
// 	var chart = new FusionCharts(chartUrl, "ChartId", "325px", "170px", "0", "0");
// 	   chart.setDataURL('<c:url value="/portal/dashboard/standardChartList.do"/>');
// 	   chart.render("standardChart");
}

/*
	row : 행의 index
	col : 컬럼의 index
	value : 해당 셀의 value
	x : x좌표
	y : y좌표
*/
/* function mySheet_OnDblClick(row, col, value, cellx, celly) {
	
}

function mySheet_OnClick(row, col, value, cellx, celly) {
	$("#hdnRow").val(row);
	if(row < 1) return;
} */


//게시물상세조회
function goBbs(nttId) {
	$('form[name="search"] input[name="bbsId"]').val('BBSMSTR_000000000007');
	$('form[name="search"] input[name="nttId"]').val(nttId);
	$("form[name=search]").attr('action', '<c:url value="/commons/bbs/selectBoardArticle.do"/>').submit();
    
}
function openRqstPage(rqstNo, bizDcd) {
	if(bizDcd != "") {
		var url = "goRqstPage.do?rqstNo="+rqstNo+"&bizDcd="+bizDcd; 
						
		window.open().location.href=url;
	}
}




</script>
</head>
<body>
    <!-- 메인 컨테이너 시작 -->
    <div class="container">
    <form name="search">
    	<div class="main_search" style="z-index:5;">
        	<div class="main_search_tit"><img src='<c:url value="/img/main_search_tit.gif"/>' alt="<s:message code='INTG.INQ' />"></div> <!-- 통합검색 -->
            <div class="main_search_input"><input type="text" name="searchNm" id="searchNm"><button class="main_search_bt"><s:message code='INQ' /></button></div> <!-- 검색 -->
            <div class="main_search_txt"><s:message code="MSG.INTG.INQ.CNTN.FIND" /></div> <!-- 통합검색을 이용하시면 원하시는 내용을 더욱 빠르게 찾아볼 수 있습니다. -->
            <div class="main_search_popular">
            	<div class="main_search_popular_tit"><img src='<c:url value="/img/main_search_popular_tit.gif" />' alt="<s:message code='POPULA.INQ.WORD' />"></div> <!-- 인기검색어 -->
                <div class="main_search_popular_link" style="position:relative; padding-left:10px;  padding-top:10px; height: 15px; overflow: hidden;">
                </div>
                <div id="rank_arrow" style="position: absolute; top: 5px; right: 10px; padding: 5px; cursor: pointer;"><img alt="" src='<c:url value="/img/rank_arrow.gif" />'></div>
            </div>
        </div>

		<input type="hidden" name="bbsId" />
		<input type="hidden" name="nttId" />
   </form>

        <div class="main_icon">
        	<ul class="main_icon_list">
            	<li><a><img src='<c:url value="/img/main_icon_01.gif" />' onMouseOver="this.src='<c:url value="/img/main_icon_01_.gif" />'" onMouseOut="this.src='<c:url value="/img/main_icon_01.gif"/>'" alt="<s:message code='STRDZATION' />"></a></li> <!-- 표준화 -->
                <li><a><img src='<c:url value="/img/main_icon_02.gif" />' onMouseOver="this.src='<c:url value="/img/main_icon_02_.gif" />'" onMouseOut="this.src='<c:url value="/img/main_icon_02.gif"/>'" alt="<s:message code='TBL.REG' />"></a></li> <!-- 테이블등록 -->
                <li><a><img src='<c:url value="/img/main_icon_03.gif" />' onMouseOver="this.src='<c:url value="/img/main_icon_03_.gif"/>'" onMouseOut="this.src='<c:url value="/img/main_icon_03.gif"/>'" alt="<s:message code='CD.MNG' />"></a></li> <!-- 코드관리 -->
                <li><a><img src='<c:url value="/img/main_icon_04.gif" />' onMouseOver="this.src='<c:url value="/img/main_icon_04_.gif"/>'" onMouseOut="this.src='<c:url value="/img/main_icon_04.gif"/>'" alt="<s:message code='BZWR.RULE' />"></a></li> <!-- 업무규칙 -->
                <li class="bd_none"><a><img src='<c:url value="/img/main_icon_05.gif" />' onMouseOver="this.src='<c:url value="/img/main_icon_05_.gif" />'" onMouseOut="this.src='<c:url value="/img/main_icon_05.gif"/>'" alt="<s:message code='PROF' />"></a></li> <!-- 프로파일 -->
            </ul>
        </div>
        
        
        <div class="main_bbs">
        	<div class="main_tab_tit">
            	<ul class="main_tab">
                	<li class="main_tab_select"><a href="<c:url value="/portal/community/NoticeCtrl.do"/>"><s:message code='ALAM.YARD' /></a></li> <!-- 알림마당 -->
                </ul>
                <div class="main_more"><a href="<c:url value="/portal/community/NoticeCtrl.do"/>"><span>+</span> <s:message code="VIEW.MORE" /></a></div> <!-- 더보기 -->
            </div>
            <ul class="main_bbs_list">
            	<c:forEach var="bbsList" items="${bbsList}">
            	<li>
                	<div class="main_bbs_item"><a href="javascript:goBbs('${bbsList.nttId}')">${bbsList.nttSj}</a></div>
	                <div class="main_bbs_date">${fn:substring(bbsList.frstRegisterPnttm,0,10)}</div>
                </li>
                </c:forEach>
            </ul>
        </div>
        
        
        <div class="main_data" id="rqstListDiv">
        	<div class="main_tab_tit">
            	<ul class="main_tab">
                	<li class="main_tab_select"><a href="javascript:doDiv('rqst')"><s:message code="REG.DEMD.PRES" /></a></li> <!-- 등록요청현황 -->
                    <li><a href="javascript:doDiv('aprv')"><s:message code="APRL.TRGT.PRES" /></a></li> <!-- 결재대상현황 -->
                </ul>
                <div class="main_more"><a href="<c:url value="/portal/myjob/request.do"/>"><span>+</span> <s:message code="VIEW.MORE" /></a></div> <!-- 더보기 -->
            </div>
            <table border="0" cellspacing="0" cellpadding="0" class="main_table" summary="<s:message code='REG.DEMD.PRES' />"> <!-- 등록요청현황 -->
            <caption>
            <s:message code="REG.DEMD.PRES" /> <!-- 등록요청현황 -->
            </caption>
			  <tr>
			    <th><s:message code="DEMD.DSTC" /></th> <!-- 요청구분 -->
                <th width="270"><s:message code="DEMD.NM" /></th> <!-- 요청명 -->
                <th><s:message code="DEMD.DT" /></th> <!-- 요청일자 -->
                <th class="bd_none"><s:message code="APRV.PRGS.LVL" /></th> <!-- 승인진행레벨 -->
			  </tr>
			  <c:forEach var="reqItem" items="${reqList}">
              <tr>
                <td class="txt_left">${reqItem.bizDcdNm}</td>
				<td><a class="wd260 ellipsis" href="javascript:openRqstPage('${reqItem.rqstNo}','${reqItem.bizDcd}')">${reqItem.rqstNm}</a></td>
				<td class="txt_center"><fmt:formatDate value="${reqItem.rqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
				<td class="txt_center bd_none">${reqItem.aprvStepLvl}</td>  
              </tr>
             </c:forEach>
            </table>
        </div>
        <div class="main_data" id="aprvListDiv" style="display:none;">
        	<div class="main_tab_tit">
            	<ul class="main_tab">
                	<li><a href="javascript:doDiv('rqst')"><s:message code="REG.DEMD.PRES" /></a></li> <!-- 등록요청현황 -->
                    <li class="main_tab_select"><a href="javascript:doDiv('aprv')"><s:message code="APRL.TRGT.PRES" /></a></li> <!-- 결재대상현황 -->
                </ul>
                <div class="main_more"><a href="<c:url value="/portal/myjob/apprReq.do"/>"><span>+</span> <s:message code="VIEW.MORE" /></a></div> <!-- 더보기 -->
            </div>
            <table border="0" cellspacing="0" cellpadding="0" class="main_table" summary="<s:message code='APRL.TRGT.PRES' />"> <!-- 결재대상현황 -->
            <caption>
            <s:message code="APRL.TRGT.PRES" /> <!-- 결재대상현황 -->
            </caption>
			  <tr>
                <th><s:message code="DEMD.DSTC" /></th> <!-- 요청구분 -->
                <th width="270"><s:message code="DEMD.NM" /></th> <!-- 요청명 -->
                <th><s:message code="DEMD.DT" /></th> <!-- 요청일자 -->
                <th class="bd_none"><s:message code="DMNT" /></th> <!-- 요청자 -->
			  </tr>
			  <c:forEach var="aprvList" items="${aprvList}">
              <tr>
                <td class="txt_left">${aprvList.bizDcdNm}</td>
				<td><a class="wd260 ellipsis" href="javascript:openRqstPage('${aprvList.rqstNo}','${aprvList.bizDcd}')">${aprvList.rqstNm}</a></td>
				<td class="txt_center"><fmt:formatDate value="${aprvList.rqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
				<td class="txt_center bd_none">${aprvList.rqstUserNm}</td>  
              </tr>
             </c:forEach>
            </table>
        </div>
        
        <div class="main_link">
        	<div class="main_file">
            	<div class="main_file_tit"><s:message code="RQRD.INSL.FILE" /></div> <!-- 필수설치파일 -->
                <ul class="main_file_cont">
                	<li><a href="javascript:fn_egov_downFile('52                  ','0')"><img src='<c:url value="/img/main_file_grid.gif"/>' alt="GRID"></a></li>
                    <li><a href="javascript:fn_egov_downFile('55                  ','0')"><img src='<c:url value="/img/main_file_erd.gif"/>' alt="ERD Viewer"></a></li>
                    <li><a href="javascript:fn_egov_downFile('54                  ','0')"><img src='<c:url value="/img/main_file_edcm2.gif"/>' alt="EDCM2"></a></li>
                </ul>
            </div>
            <div class="main_quick">
            	<div class="main_quick_tit"><s:message code="SHOTCUT" /></div> <!-- 바로가기 -->
                <ul class="main_quick_cont">
                    <li><a href="meta/stnd/sditm_lst.do" target="_blank"><s:message code="STRD.TERMS.INQ" /></a></li> <!-- 표준용어조회 -->
                    <li><a href="meta/model/pdmtbl_lst.do" target="_blank"><s:message code="TBL.INQ" /></a></li> <!-- 테이블조회 -->
                    <li><a href="meta/subjarea/subj_lst.do" target="_blank"><s:message code="SUBJ.TRRT.INQ" /></a></li> <!-- 주제영역조회 -->
<!--                     <li><a href="#">전자메뉴얼</a></li> -->
                    <li><a href="<%=WiseConfig.URL_DQ%>" target="_blank"><s:message code="DATA.QLTY.CNFR" /></a></li> <!-- 데이터품질확인 -->
                </ul>
            </div>
        </div>
        <div class="main_chart_01" id="chart1">
        	<div class="main_chart_01_tit"><s:message code="MDEL.VS.DB.MTCH.RT" /></div> <!-- 모델 vs DB 일치율 -->
            <div class="main_chart_01_cont" id="errChartList"></div> <!-- 차트 사이즈 132px X 157px -->
        </div>
        <div class="main_chart_02" id="chart2">
        	<div class="main_chart_02_tit"><s:message code="SUBJ.TRRT.STRD.OBSERV.RT" /><div style="float: right; border:none; background: #ffffff; margin-top: -5px;" id="rightchart" ><s:message code="VIEW.MORE" /></div></div> <!-- 주제영역별 표준준수율 더보기 -->
            
	            <script type="text/javascript">
		    	createIBChart("testChart", "325px", "170px");
		  		</script>
             <!-- 차트 사이즈 132px X 157px -->
        </div>
        <div class="main_chart_02" id="chart3" style="display: none;">
        	<div class="main_chart_01_tit"><s:message code="BZWR.TRRT.QLTY.PRES" /><div style="float: right; border:none; background: #ffffff; margin-top: -5px;" id="leftchart" ><s:message code="VIEW.MORE" /></div></div> <!-- 업무영역별 품질현황 더보기 -->
            
            	<script type="text/javascript">
		    	createIBChart("widgetsChart", "325px", "170px");
		  		</script>
            </div> <!-- 차트 사이즈 132px X 157px -->
        
    </div>
    <!-- 메인 컨테이너 끝 -->
    <div id="chart2div" style="display: none;"></div>



<div id="rank_list">
<ol class="main_rank_list" style="background: #ffffff; display: none;">
	<li class="nm_rank_01"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_02"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_03"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_04"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_05"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_06"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_07"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_08"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_09"><a href="#"><s:message code="META.DATA" /><s:message code="META.DATA" /><s:message code="META.DATA" /></a></li> <!-- 메타데이터메타데이터메타데이터 -->
</ol>
</div>
</body>
</html>