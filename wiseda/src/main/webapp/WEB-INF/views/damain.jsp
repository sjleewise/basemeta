<!DOCTYPE html>
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script> --%>

<script type="text/javascript">
//텍스트 롤링 함수
function scrolltext() {
    var textlist = $('#scroll_test');
    textlist.animate({marginTop:'-16px'}, 1000, function(){
        textlist.css('marginTop', '0px').append(textlist.find('li:first'));
    });
}
		
$(document).ready(function(){
	
	imgConvert($(".main_search_bt"));
	
	var curr = new Date();
	var curr2 = new Date(Date.parse(curr)-7*1000*60*60*24);
	var currDate = curr.getFullYear()+'<s:message code="YR" />'+(curr.getMonth()+1)+'<s:message code="MM" />'+curr.getDate()+'<s:message code="DD" />'; /* 년 월 일*/
	var currDate2 = "<s:message code="BASE.DD" /> : " + curr2.getFullYear()+'<s:message code="YR" />'+(curr2.getMonth()+1)+'<s:message code="MM" />'+curr2.getDate()+'<s:message code="DD" />  - '; /* 기준일 년 월 일*/
// 	var currDate = (curr.getMonth()+1)+'/'+curr.getDate()+'/'+curr.getFullYear(); /* 년 월 일*/
// 	var currDate2 = (curr2.getMonth()+1)+'/'+curr2.getDate()+'/'+ curr2.getFullYear()+'  - '; /* 기준일 년 월 일*/
	$(".nm_tit_txt").text(currDate2 +currDate);
	
	$("#searchNm").focus();
	
	$(".ntit_tit").hide();
	$(".ntit_loca").hide();
	
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
		// 인기검색어 더보기 버튼..
		$('div.nm_sch_popular_bt').click(function(){
			var tmptop = $(this).offset().top + $(this).height()+2;
			var tmpleft = $(this).offset().left - $('ol.nm_rank_list').width()+5;
			//alert(tmptop+":"+tmpleft);
			$('ol.nm_rank_list').css({
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
		
		$('ol.nm_rank_list').mouseleave(function(){
			$(this).hide();
		});
		
 		
		//인기검색어 스크롤 텍스트 처리하도록 처리
 		$.getJSON('<c:url value="/portal/totalsearch/TotalSearchWord.json"/>', function(data){
			if(data ==  null) return;

			var divaction = $('#nm_sch_popular_word'); //스크롤 div 대상
	 		arrpapul = new Array();
	 		cntpapul = 0;
	 		cntp = 0;
	 		$('ol.nm_rank_list').empty();
			for(var i=0; i<data.length; i++) {
					var lilink = "<li class='nm_rank_0"+(i+1)+"'><a href='javascript:goSearch(\""+data[i].searchWord+"\");'>"+data[i].searchWord+"</a></li>";
					$('ol.nm_rank_list').append(lilink);

					if(i > 2) continue; 
					
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

			for(i = 0; i < cntpapul; i++) {

				if(i > 2) continue; 
				
				searchHtml +=arrpapul[i];
			}
			searchHtml += '</ul>';
			divaction.html(searchHtml);
// 			setInterval(scrolltext, 5*1000);
	 		
		}); 
		
		
 		//표준데이터 항목/단어/도메인 링크
 		$('.nm_sd_item:eq(0), .nm_sd_item:eq(1), .nm_sd_item:eq(5)').click(function(){
 			if($(this).children().next(".nm_sd_item_04").text() == "AUTH") {
				window.open("<c:url value='/"+$(this).children().next(".nm_sd_item_03").text()+"?linkFlag=1'/>");			
 			} else if ($(this).children().next(".nm_sd_item_04").text() == "NOTAUTH") {
 				showMsgBox("ERR", "<s:message code="ERR.ACCESS" />");
 			}
 			
 		});
//  		$('.nm_sd_item:eq(1)').click(function(){ 
//  			if($(this).children().next(".nm_sd_item_04").text() == "AUTH") {
// 				window.open("<c:url value='/"+$(this).children().next(".nm_sd_item_03").text()+"?linkFlag=1'/>");			
//  			} else if ($(this).children().next(".nm_sd_item_04").text() == "NOTAUTH") {
 				
//  			}			
// 		});
//  		$('.nm_sd_item:eq(5)').click(function(){ 
//  			if($(this).children().next(".nm_sd_item_04").text() == "AUTH") {
// 				window.open("<c:url value='/"+$(this).children().next(".nm_sd_item_03").text()+"?linkFlag=1'/>");			
//  			} else if ($(this).children().next(".nm_sd_item_04").text() == "NOTAUTH") {
 				
//  			}			
// 		});
 		
 		//데이터 모델 물리/ddl/dbms 링크
//  		$('.tr_line:eq(0)').click(function(){ 
// 			window.open("<c:url value='/meta/ddl/ddltbl_lst.do?linkFlag=1'/>");			
// 		});
//  		$('.tr_line:eq(1)').click(function(){ 
// 			window.open("<c:url value='/meta/model/pdmtbl_lst.do?linkFlag=1'/>");			
// 		});
//  		$('.tr_line:eq(2)').click(function(){ 
// 			window.open("<c:url value='/meta/dbc/dbctbl_lst.do?linkFlag=1'/>");			
// 		});
 		
//  		$('.tr_line2:eq(0)').click(function(){ 
// 			window.open("<c:url value='/meta/model/pdmtbl_lst.do?linkFlag=1'/>");			
// 		});
//  		$('.tr_line2:eq(1)').click(function(){ 
// 			window.open("<c:url value='/meta/dbc/dbctbl_lst.do?linkFlag=1'/>");			
// 		});
//  		$('.tr_line2:eq(2)').click(function(){ 
//  			window.open("<c:url value='/meta/ddl/ddltbl_lst.do?linkFlag=1'/>");			
// 		});
		$('.tr_line, .tr_line2').click(function(){
			if($(this).children('td:nth-child(7)').text() == "AUTH") {
				window.open("<c:url value='/"+$(this).children("td:nth-child(6)").text()+"?linkFlag=1'/>");			
 			} else if ($(this).children('td:nth-child(7)').text() == "NOTAUTH") {
 				showMsgBox("ERR", "<s:message code="ERR.ACCESS" />");
 			}	
		});
				
//  		getChart();


		//결재목록이 존재할 경우 결재탭 클릭
		if("${aprvList[0].rqstNo}"!=''){
			doDiv('aprv');	
		}
 		
});

$(window).resize(function(){
	
// 	var tmptop = $('div#rank_arrow').offset().top + $('div#rank_arrow').height()+23;
// 	var tmpleft = $('div#rank_arrow').offset().left + $('div#rank_arrow').width() - $('ol.nm_rank_list').width()-5;
// 	alert(tmptop+":"+tmpleft);
// 	$('ol.nm_rank_list').css({
// 			top:tmptop,
// 			left:tmpleft
// 	});
                
        // Window Resize 시  컬럼  Width%
//         var interval = "5|10|10|10|15|15|35";
        
//         chgSize(mySheet, interval);
});

$(window).on('load',function(){
	var con1 = document.getElementById("gapChart");
	var con2 = document.getElementById("testChart");
	var con3 = document.getElementById("rqstChart");
	//"375px",
	createIBChart(con1, "gapChart", {
      width: "auto",
      height: "210px"
    });
    createIBChart(con2, "testChart", {
      width:  "auto",
      height: "210px"
    });
    createIBChart(con3, "rqstChart", {
      width:  "auto",
      height: "210px"
    });
	    
	//요청현황 차트 초기화
	rqstChart.setOptions({
		chart : {
			backgroundColor : "#FFFFFF", //차트 배경색 설정
			type : "column", //차트 Type 설정
			borderColor : "#FFFFFF" //차트 테두리 색 설정
		},
		legend : {
			layout : "vertical", //Legend 모양 설정
			align : "right", //Legend 가로 정렬 설정
			verticalAlign : "center" //Legend 세로 정렬 설정
		},
		
		colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
		
		plotOptions : {
			series : {
				DataLabels : { //시리즈의 데이터 레이블 설정
					Enabled : true,
				}
			},
			column : {
				pointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		xAxis : {
			categories : ['<s:message code="REG.DEMD.PRES" />'], /* 등록요청현황 */
			tickInterval : 1, //X축 레이블 간격 설정
			labels : { //X축 레이블 설정
				enabled : true
			}
		},
		yAxis : {
// 			TickInterval : 30, //Y축 레이블 간격 설정
			min : 0, //Y축 Min값 설정
			title : { //Y축 제목 설정
				text : ""
			}
		}
	});
	
	//모델 VS DB일치율(GAP) 차트 초기화
	gapChart.setOptions({
		chart : {
			backgroundColor : "#FFFFFF", //차트 배경색 설정
			type : "column", //차트 Type 설정
			borderColor : "#FFFFFF" //차트 테두리 색 설정
		},
		legend : {
			layout : "vertical", //Legend 모양 설정
			align : "right", //Legend 가로 정렬 설정
			verticalAlign : "center" //Legend 세로 정렬 설정
		},
		
		colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
		
		plotOptions : {
			pie:{
				innerSize:20,
				slicedOffset:20,
				allowPointSelect:true,
				startAngle:60
			},
			series : {
				dataLabels:{
					enabled:true,
					align:"center",
					color : "#333333",
					formatter: function (params) {
			            var name = params.name
			            var value = params.value || null
			            if (value == 'undefined' || value == "" || value == null) {
			              return name;
			            } else {
			              return name + ': ' + value;
			            }
			          }
				}
			},
			column : {
				pointPadding : 0.1 // 컬럼간의 간격 설정
			},
		},
		xAxis : {
			tickInterval : 1, //X축 레이블 간격 설정
			labels : { //X축 레이블 설정
				enabled : false
			}
		},
		yAxis : {
			tickInterval : 30, //Y축 레이블 간격 설정
			min : 0, //Y축 Min값 설정
			title : { //Y축 제목 설정
				text : ""
			}
		}
		
	});
	
	rqstChartDraw();
	barchartDrow();
	gapChartDraw();
	
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
	//testChart.draw();
	
});

function rqstChartDraw()	{
	$.getJSON('<c:url value="/meta/report/getRqstStatusList.do"/>', function(data){

		if(data ==  null) return;

// 		rqstChart.RemoveAll();

		//시리즈 3개 생성
// 		var seriesCnt 	= rqstChart.CreateSeries();		
// 		var seriesAprv 	= rqstChart.CreateSeries();		
// 		var seriesRjct 	= rqstChart.CreateSeries();		

		//각 시리즈별 이름과 타입 설정
		var seriesCnt = {
			name:"<s:message code='AGGT' />", /* 총계 */
			type:"column"
		};		
		var seriesAprv = {
			name:"<s:message code='APRV' />", /* 승인 */
			type:"column"
		};		
		var seriesRjct = {
			name:"<s:message code='RTN' />", /* 반려 */
			type:"column"
		};		

		
		//시리즈별 데이터 생성
		var pointsCnt = new Array();
		var pointsAprv = new Array();
		var pointsRjct = new Array();

		//데이터 길이 확인
		var cnt = data.length;
		for(i=0;i<cnt;i++) {
			pointsCnt.push({x:i, y:data[i].rqstCnt, name:"<s:message code='AGGT' />"}); /* 총계 */
			pointsAprv.push({x:i, y:data[i].rqstAprvCnt, name:"<s:message code='APRV.CCNT' />"}); /* 승인건수 */
			pointsRjct.push({x:i, y:data[i].rqstRjctCnt, name:"<s:message code='RTN.CCNT' />"}); /* 반려건수 */
		}

// 		seriesCnt.AddPoints(pointsCnt);
// 		seriesAprv.AddPoints(pointsAprv);
// 		seriesRjct.AddPoints(pointsRjct);

		seriesCnt.data=pointsCnt;  
		seriesAprv.data=pointsAprv;
		seriesRjct.data=pointsRjct;
		

// 		rqstChart.AddSeries(seriesCnt);
// 		rqstChart.AddSeries(seriesAprv);
// 		rqstChart.AddSeries(seriesRjct);

		rqstChart.setOptions({
			series : [seriesCnt, seriesAprv, seriesRjct]
		}, {
			 append : true
			,resetData : true
		});

		rqstChart.draw();

	});
}


function barchartDrow()	{
	$.getJSON('<c:url value="/portal/dashboard/standardChartListjson.do"/>', function(data){
		if(data ==  null) return;
		
		var subjName = new Array();
		
		//데이터 길이 확인
		var cnt2 = data.length;
		for(i=0;i<cnt2;i++) {
			subjName[i] =data[i].subjNm;
		}
	
		
		
		testChart.setOptions({
			chart : {
				backgroundColor : "#FFFFFF", //차트 배경색 설정
				type : "column", //차트 Type 설정
				borderColor : "#FFFFFF"
			},
			legend : {
				layout : "horizontal", //Legend 모양 설정
				align : "center", //Legend 가로 정렬 설정
				verticalAlign : "bottom" //Legend 세로 정렬 설정
			},
			
			colors : ["#AAEE6A","#F0E150","#5DA0A9","#75738B","#7AAAEE","#F06F3E"],
			
			plotOptions : {
				series : {
					dataLabels : { //시리즈의 데이터 레이블 설정
						enabled : false,
					}
				},
				column : {
					pointPadding : 0.1 // 컬럼간의 간격 설정
				},
			},
			xAxis : {
			    categories:subjName,  
				tickInterval : 1, //X축 레이블 간격 설정
				labels : { //X축 레이블 설정
					enabled : true
				}
				
			},
			yAxis : {
				tickInterval : 100, //Y축 레이블 간격 설정
				min : 0, //Y축 Min값 설정
// 				max : 200, //Y축 Max값 설정
				title : { //Y축 제목 설정
					text : ""
				}
			},
			legend : {
				enabled:false
			}
		});
// 		testChart.setLegendOptions({
// 			   enabled:false
// 		});
		
// 		testChart.RemoveAll();
		
		//시리즈 3개 생성
// 		var seriesTot 	= testChart.createSeries();		
		//var seriesStnd 	= testChart.CreateSeries();		
		//var seriesNstnd = testChart.CreateSeries();
		
		
		//각 시리즈별 이름과 타입 설정
		var seriesTot = {
			//Name:"전체",
			name:"<s:message code='TBL.CNT' />", /* 테이블수 */
			type:"column"
		};		
  	//seriesStnd.setOptions({
			//Name:"표준",
			//Type:"column"
	//	});		
	//	seriesNstnd.setOptions({
	//		Name:"비표준",
	//		Type:"column"
	//	});	

		//시리즈별 데이터 생성
		var totpoints = new Array();
		//var stndpoints = new Array();
		//var nstndpoints = new Array();
		
		//데이터 길이 확인
		var cnt = data.length;
		for(i=0;i<cnt;i++) {
			totpoints.push({x:i, y:data[i].cnt, name:data[i].subjNm});
			//stndpoints.push({X:i, Y:data[i].cntY, Name:data[i].subjNm});
			//nstndpoints.push({X:i, Y:data[i].cntN, Name:data[i].subjNm});
		}

// 		seriesTot.AddPoints( totpoints );
		seriesTot.data = totpoints;
		//seriesStnd.AddPoints( stndpoints );
		//seriesNstnd.AddPoints( nstndpoints );
		
// 		testChart.AddSeries(seriesTot);
		testChart.setOptions({
			series : [seriesTot]
		}, {
			append : true
			, resetData : true
		});
		
		//testChart.AddSeries(seriesStnd);
		//testChart.AddSeries(seriesNstnd);
		
		testChart.draw();
	});
}

function gapChartDraw()	{
	$.getJSON('<c:url value="/meta/gap/getGapChart.do"/>', function(data){
		if(data ==  null) return;
		
// 		gapChart.RemoveAll();

		//시리즈 생성
// 		var series 	= gapChart.CreateSeries();		

		//각 시리즈별 이름과 타입 설정
// 		series.setOptions({
// 			Name:"<s:message code='MDEL.VS.DB.MTCH.RT' />", /* 모델 VS DB일치율 */
// 			Type:"pie"
// 		});		
		
		//시리즈별 데이터 생성
		var points = new Array();

		points.push({x:0, y:data[0].nmlCnt, name:"<s:message code='NROM' />", sliced:1}); /* 정상 */
		points.push({x:0, y:data[0].gapCnt, name:"GAP", Sliced:0});
		
		

// 		series.AddPoints(points);
		
// 		gapChart.AddSeries(series);

		var series = {
				name:"<s:message code='MDEL.VS.DB.MTCH.RT' />", /* 모델 VS DB일치율 */
				type:"pie",
				data: points
		
		}
		gapChart.setOptions({
			series : [series]
		}, {
			append : true
			, resetData : true
		});

		gapChart.draw();

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
// 	var chartUrl = '<c:url value="/swffile/fusionwidgets/AngularGauge.swf"/>';
// 	var chart = new FusionCharts(chartUrl, "ChartId", "300px", "170px", "0", "0");
// 	   chart.setDataURL('<c:url value="/portal/dashboard/ErrChartCtrl.do"/>');
// 	   chart.render("errChartList");
	   
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
						
		//window.open().location.href=url;
		window.location.href=url;
	}
}

</script>
	<!-- 등록요청, 결재대상 Top5 -->
	    <div class="n4m_box11" id="rqstListDiv">
<!--         	<div class="main_tab_tit"> -->
            	<ul class="n4m_tab">
                    <li><a class="select" href="javascript:doDiv('rqst')"><s:message code="REG.DEMD.PRES" /></a></li> <!-- 등록요청현황 -->
                    <li><a href="javascript:doDiv('aprv')"><s:message code="APRL.TRGT.PRES" /></a></li> <!-- 결재대상현황 -->
                </ul>
                <%-- <div class="nm_more"><a href="<c:url value="/meta/stnd/rqstmy_lst.do?linkFlag=1"/>"><img src="<c:url value="/images/nm_bt_more.gif"/>"  alt="<s:message code='VIEW.MORE' />"></a></div> --%> <!-- 더보기 -->
<!--             </div> -->
			<!-- <div class="nm_tab_cont"> -->
            <table border="0" cellspacing="0" cellpadding="0" class="n4m_tb_01" summary="<s:message code='REG.DEMD.PRES' />"> <!-- 등록요청현황 -->
            <caption>
            <s:message code='REG.DEMD.PRES' /> <!-- 등록요청현황 -->
            </caption>
            <colgroup>
				<col style="width:15%;">
	        	<col style="width:47%;">
	        	<col style="width:18%;">
	        	<col style="width:20%;">
            </colgroup> 
			  <tr>
			    <th><s:message code="DEMD.DSTC" /></th> <!-- 요청구분 -->
                <th ><s:message code="DEMD.NM" /></th> <!-- 요청명 -->
                <th><s:message code="DEMD.DT" /></th> <!-- 요청일자 -->
                <th><s:message code="APRV.PRGS.LVL" /></th> <!-- 승인진행레벨 -->
			  </tr>
			  <c:forEach var="reqItem" items="${reqList}">
              <tr>
                <td class="txtcenter">${reqItem.bizDcdNm}</td>
				<td><a class="ellipsis" style="text-align:left;" href="javascript:openRqstPage('${reqItem.rqstNo}','${reqItem.bizDcd}')">${reqItem.rqstNm}</a></td>
				<td class="date"><fmt:formatDate value="${reqItem.rqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
				<td class="txtcenter">${reqItem.aprvStepLvl}</td>  
              </tr>
             </c:forEach>
            </table>
            <!-- </div> -->
        </div>
        <div class="n4m_box11" id="aprvListDiv" style="display:none;">
<!--         	<div class="main_tab_tit"> -->
            	<ul class="n4m_tab">
                	<li><a href="javascript:doDiv('rqst')"><s:message code='REG.DEMD.PRES' /></a></li> <!-- 등록요청현황 -->
                    <li><a class="select" href="javascript:doDiv('aprv')"><s:message code="APRL.TRGT.PRES" /></a></li> <!-- 결재대상현황 -->
                </ul>
                <%-- <div class="nm_more"><a href="<c:url value="/meta/stnd/rqsttodo_lst.do?linkFlag=1"/>"><img src="<c:url value="/images/nm_bt_more.gif"/>"  alt="<s:message code='VIEW.MORE' />"></a></div> --%> <!-- 더보기 -->
<!--             </div> -->
			<!-- <div class="nm_tab_cont"> -->
            <table border="0" cellspacing="0" cellpadding="0" class="n4m_tb_01" summary="<s:message code='APRL.TRGT.PRES' />"> <!-- 결재대상현황 -->
            <caption>
            <s:message code="APRL.TRGT.PRES" /> <!-- 결재대상현황 -->
            </caption>
            <colgroup>
				<col style="width:15%;">
				<col style="width:47%;">
				<col style="width:18%;">
				<col style="width:20%;">
            </colgroup> 
			  <tr>
                <th><s:message code="DEMD.DSTC" /></th> <!-- 요청구분 -->
                <th ><s:message code="DEMD.NM" /></th> <!-- 요청명 -->
                <th><s:message code="DEMD.DT" /></th> <!-- 요청일자 -->
                <th><s:message code="DMNT" /></th> <!-- 요청자 -->
			  </tr>
			  <c:forEach var="aprvList" items="${aprvList}">
              <tr>
                <td class="txtcenter">${aprvList.bizDcdNm}</td>
				<td><a class="ellipsis" style="text-align:left;" href="javascript:openRqstPage('${aprvList.rqstNo}','${aprvList.bizDcd}')">${aprvList.rqstNm}</a></td>
				<td class="date"><fmt:formatDate value="${aprvList.rqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
				<td class="txtcenter">${aprvList.rqstUserNm}</td>  
              </tr>
             </c:forEach>
            </table>
            <!-- </div> -->
        </div>
        
        <!-- 통합검색 -->
	<form name="search">
		<input type="hidden" id="bbsId" name="bbsId" />
		<input type="hidden" id="nttId" name="nttId" />
    	<div class="n4m_box12">
        	<div class="tit"><s:message code='INTG.INQ' /></div><!-- 통합검색 -->
            <div class="search"><input type="text" name="searchNm" id="searchNm" placeholder="<s:message code='MSG.INQ.WORD.INPT' />"> <button class="main_search_bt">검색</button></div> <!-- 검색어를 입력하세요 --> <!-- 검색 -->
            <div class="txt_explain"><s:message code="MSG.SEARCH.USE.WNT"/></div> <!-- 통합검색을 이용하면 원하시는 내용을 더욱 빠르게 찾아볼 수 있습니다. -->
            <div class="txt_word">
            	<%-- <div class="nm_sch_popular_tit"><img src="<c:url value="/images/nm_search_popular.gif"/>" alt="<s:message code='POPULA.INQ.WORD' />"></div> --%> <!-- 인기검색어 -->
                <span>Top Keywords</span>
                <div class="nm_sch_popular_word" id="nm_sch_popular_word" style="position:relative; text-overflow:ellipsis; padding-left:10px;  height: 16px; overflow: hidden;">
                	<a href="#"><s:message code="INQ.WORD" />01</a>, <!-- 검색어 -->
                    <a href="#"><s:message code="INQ.WORD" />02</a>, <!-- 검색어 -->
                    <a href="#" style="text-overflow:ellipsis;"><s:message code="INQ.WORD" />03</a> <!-- 검색어 -->
                </div>
                <%-- <div class="nm_sch_popular_bt" id="rank_arrow" ><img src="<c:url value="/images/nm_search_popular_bt.gif"/>" alt="<s:message code='VIEW.MORE' />"></div> --%> <!-- 더보기 -->
<!--                 <ol class="nm_rank_list"> -->
<!--                     <li class="nm_rank_01"><a href="#">메타데이터</a></li> -->
<!--                     <li class="nm_rank_02"><a href="#">메타데이터</a></li> -->
<!--                     <li class="nm_rank_03"><a href="#">메타데이터</a></li> -->
<!--                     <li class="nm_rank_04"><a href="#">메타데이터</a></li> -->
<!--                     <li class="nm_rank_05"><a href="#">메타데이터</a></li> -->
<!--                     <li class="nm_rank_06"><a href="#">메타데이터</a></li> -->
<!--                     <li class="nm_rank_07"><a href="#">메타데이터</a></li> -->
<!--                     <li class="nm_rank_08"><a href="#">메타데이터</a></li> -->
<!--                     <li class="nm_rank_09"><a href="#">메타데이터메타데이터메타데이터</a></li> -->
<!--                     <li class="nm_rank_10"><a href="#">메타데이터</a></li> -->
<!--                 </ol> -->
            </div>
            <!-- 공지사항 -->
	        <div class="notice">
	        	<span>[<s:message code="PBNC.MTR" />] </span> <!-- 공지사항 -->
	            <%-- <div class="nm_notice_more"><a href="<c:url value="/portal/community/NoticeCtrl.do"/>"><img src="<c:url value="/images/nm_notice_more.gif" />"  alt="<s:message code='VIEW.MORE' />"></a></div> --%> <!-- 더보기 -->
	            <!-- <ul class="nm_notice_list"> -->
	            <c:forEach var="bbsList" items="${bbsList}" end="0">
	            	<!-- <li> -->
	                	<a class="ellipsis" href="javascript:goBbs('${bbsList.nttId}')">${bbsList.nttSj}</a>
		                <span class="date">${fn:substring(bbsList.frstRegisterPnttm,0,10)}</span>
	                <!-- </li> -->
	           </c:forEach>
	            <!-- </ul> -->
	        </div>
        </div>
        </form>
        
        <!-- 표준데이터 -->
        <div class="n4m_box21">
        	<div class="tit"><s:message code="STRD.DATA" /></div> <!-- 표준데이터 -->
        	
        	<div class="bx">
            	<ul class="bx_left">
	            <c:forEach var="curstnd" items="${stndresult}" begin="0" end="2" step="1">
	               	<li><span>${curstnd.cntName}</span><a href="/wiseda${curstnd.page}?linkFlag=1">${curstnd.totCnt}</a></li>
	            </c:forEach>
	            </ul>
	            <ul class="bx_right">
	            <c:forEach var="curstnd" items="${stndresult}" begin="3" step="1">
	               	<li><span>${curstnd.cntName}</span><a href="/wiseda${curstnd.page}?linkFlag=1">${curstnd.totCnt}</a></li>
	            </c:forEach>
	            </ul>
            </div>
            
            <%-- <div class="nm_sd_cont">
            <c:forEach var="curstnd" items="${stndresult}">
                <div class="nm_sd_item">
               		<div class="nm_sd_item_01">${curstnd.cntName}</div>
                   	<div class="nm_sd_item_02"><span>${curstnd.totCnt}</span></div> <!-- 건 -->
                   	<div class="nm_sd_item_03" style="display:none;">${curstnd.page}</div>
                   	<div class="nm_sd_item_04" style="display:none;">${curstnd.auth}</div>
                   	
            	</div>
            </c:forEach>
            </div> --%>
        </div>
        
        <!-- 데이터모델 -->
        <div class="n4m_box22">
        	<div class="tit"><s:message code="DATA.MDEL" /> </div> <!-- 데이터 모델 -->
            	<table border="0" cellspacing="0" cellpadding="0" class="n4m_tb_02" summary="">
                <caption>
                	<s:message code="GRID"/> <!-- 그리드 -->
                </caption>
                
                <colgroup>
			        <col style="width:24%;">
			        <col style="width:19%;">
			        <col style="width:19%;">
			        <col style="width:19%;">
			        <col style="width:19%;">
		        </colgroup>
                    
                  <tr class="tr_th">
                    <th style="height:23px;"><s:message code="ENTY.NM" /></th> <!-- 개체명 -->
                    <th><s:message code="WHL.TCNT" /></th> <!-- 전체총수 -->
                    <th><s:message code="NEW" /></th> <!-- 신규 -->
                    <th><s:message code="CHG" /></th> <!-- 변경 -->
                    <th><s:message code="DEL" /></th> <!-- 삭제 -->
                  </tr>
		             <c:forEach var="upresult" items="${modelresult}" end="3" step="1" varStatus="status">
		             	<c:if test="${status.count % 2 == 0}">
			             <tr>
			                <td class="txtcenter">${upresult.termTypeNm}</td>
			                <td class="txtcenter">${upresult.curCnt}</td>  
			                <td class="txtcenter">${upresult.insCnt}</td>
			                <td class="txtcenter">${upresult.updCnt}</td>
			                <td class="txtcenter">${upresult.delCnt}</td>
			                <td style="display:none;">${upresult.page}</td>
			                <td style="display:none;">${upresult.auth}</td>
			             </tr>
		             	</c:if>
		             	<c:if test="${status.count % 2 == 1}">
			             <tr class="even">
			                <td class="txtcenter">${upresult.termTypeNm}</td>
			                <td class="txtcenter">${upresult.curCnt}</td>  
			                <td class="txtcenter">${upresult.insCnt}</td>
			                <td class="txtcenter">${upresult.updCnt}</td>
			                <td class="txtcenter">${upresult.delCnt}</td>
			                <td style="display:none;">${upresult.page}</td>
			                <td style="display:none;">${upresult.auth}</td>
			           	 </tr>
		             	</c:if>
		             </c:forEach>
                </table>
        </div>
        
       <!-- 차트 --> 
       <!-- <div class="nm_chart"> -->
        	<!-- <div class="nm_chart_navi"><a href="#"><img src="<c:url value="/images/nm_chart_navi_next.gif"/>"  alt="다음페이지"></a></div> -->
        	<div class="n4m_box31">
        		<div class="tit"><s:message code="MDEL.VS.DB.MTCH.RT" /></div> <!-- 모델 VS DB일치율 -->
                <div id="gapChart" class="chart">
<%--                 	<script type="text/javascript"> --%>
<!-- 			    	createIBChart("gapChart", "375px", "210px"); -->
<%-- 			  		</script> --%>
                </div>
            </div>
            <div class="n4m_box32">
        		<div class="tit"><s:message code="SUBJ.TRRT.TBL.CNT" /></div> <!-- 주제영역별 테이블수 -->
                <div id="testChart" class="chart">
<%--                 	<script type="text/javascript"> --%>
<!--  		    		createIBChart("testChart", "375px", "210px"); -->
<%-- 		  			</script> --%>
                </div>
            </div>
            <div class="n4m_box33">
        		<div class="tit"><s:message code="REG.DEMD.PRES" /></div> <!-- 등록요청현황 -->
                <div id="rqstChart" class="chart">
<%--                 	<script type="text/javascript"> --%>
<!--  		    		createIBChart("rqstChart", "375px", "210px"); -->
<%-- 		  			</script> --%>
                </div>
            </div>
        <!-- </div> -->

    <!-- 메인 컨테이너 끝 -->
    <div id="chart2div" style="display: none;"></div>

<div id="rank_list" >
<ol class="nm_rank_list" style="display: none;">
    <li class="nm_rank_01"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_02"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_03"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_04"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_05"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_06"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_07"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_08"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
    <li class="nm_rank_09"><a href="#"><s:message code="META.DATA" /><s:message code="META.DATA" /><s:message code="META.DATA" /></a></li> <!-- 메타데이터메타데이터메타데이터 -->
    <li class="nm_rank_10"><a href="#"><s:message code="META.DATA" /></a></li> <!-- 메타데이터 -->
</ol>
</div>







