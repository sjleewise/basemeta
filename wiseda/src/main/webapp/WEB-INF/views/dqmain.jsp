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
	var con1 = document.getElementById("analyzeChart");
	var con2 = document.getElementById("qualityChart");
	var con3 = document.getElementById("approveChart");
	
	/** IBChartLite Init Scripts */
	createIBChart(con1, "analyzeChart", {
		width: "auto",
		height: "220px"
	});
	createIBChart(con2, "qualityChart", {
		width: "auto",
		height: "220px"
	});
	createIBChart(con3, "approveChart", {
		width: "auto",
		height: "220px"
	});
	
	//imgConvert($(".main_search_bt, .nm_sch_popular_bt img, .nm_notice_more img, .nm_more img"));
	
	var curr = new Date();
	//var currDate = curr.getFullYear()+'<s:message code="YR" />'+(curr.getMonth()+1)+'<s:message code="MM" />'+curr.getDate()+'<s:message code="DD" />'; /* 년 월 일*/
	var currDate = (curr.getMonth()+1)+'/'+curr.getDate()+'/'+curr.getFullYear(); /* 년 월 일*/
	$(".nm_tit_txt").text(currDate);
	
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
 		$.getJSON('<c:url value="/portal/totalsearch_dq/TotalSearchWord.json"/>', function(data){
			if(data ==  null) return;

			var divaction = $('#nm_sch_popular_word_dq'); //스크롤 div 대상
			
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

			//setInterval(scrolltext, 5*1000);
	 		
		}); 
		
		//기준정보 링크
 		$('.nm_sd_item:eq(0)').click(function(){ 
			window.open("<c:url value='/dq/criinfo/anatrg/anadbmsrqst.do?linkFlag=1'/>");			
		});
		$('.nm_sd_item:eq(1)').click(function(){ 
			window.open("<c:url value='/dq/criinfo/bizarea/bizarea_lst.do?linkFlag=1'/>");			
		});
		$('.nm_sd_item:eq(2)').click(function(){ 
			window.open("<c:url value='/dq/criinfo/dqi/dqi_lst.do?linkFlag=1'/>");			
		});
		$('.nm_sd_item:eq(3)').click(function(){ 
			window.open("<c:url value='/dq/criinfo/ctq/ctq_lst.do?linkFlag=1'/>");			
		});
		$('.nm_sd_item:eq(4)').click(function(){ 
			window.open("<c:url value='/dq/profile/profile_list.do?linkFlag=1'/>");			
		});
		$('.nm_sd_item:eq(5)').click(function(){ 
			window.open("<c:url value='/dq/bizrule/bizrule_lst.do?linkFlag=1'/>");			
		});
		
		
		//업무영역별 개선현황 링크
		$(".tr_line td:first-child").bind("click",function(){
			var url = "";
			url += "./dq/bizrule/bizrule_lst.do?linkFlag="+$(this).text();  
			window.open().location.href = url;
		});
		
 		//getChart();
 		
});

$(window).resize(function(){

	/*
	var tmptop = $('div#rank_arrow').offset().top + $('div#rank_arrow').height()+23;
	var tmpleft = $('div#rank_arrow').offset().left + $('div#rank_arrow').width() - $('ol.nm_rank_list').width()-5;
	//alert(tmptop+":"+tmpleft);
	$('ol.nm_rank_list').css({
			top:tmptop,
			left:tmpleft
	});
	*/ 
                
        // Window Resize 시  컬럼  Width%
//         var interval = "5|10|10|10|15|15|35";
        
//         chgSize(mySheet, interval);
});

$(window).load(function(){
	
	analyzeChartDraw();
	approveChartDraw();
	qualityChartDraw();
	
	

	
});

function numberFormat(inputNumber) {
   return inputNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function analyzeChartDraw()	{
	
	$.getJSON('<c:url value="/portal/dashboard/dqAnalyzeChart.do"/>', function(data){

		if(data ==  null) return;

		//alert(JSON.stringify(data));

		var categories;

		//data에서 차수데이터가 없을시 카테고리 설정...
		if(data[0].prfAnaDgr == null || data[0].prfAnaDgr == "undefined" || data[0].prfAnaDgr == "") {
			categories = new Array("<s:message code='PROF' />", "<s:message code='DQ.BR' />"); /* 프로파일 업무규칙 */ 
		} else {
			categories = new Array("<s:message code='PROF' /> ("+data[0].prfAnaDgr+")", "<s:message code='DQ.BR' /> ("+data[0].brAnaDgr+")"); /* 프로파일 차 업무규칙 차 */
		}
		
		//진단현황 차트 초기화
		analyzeChart.setOptions({
			chart : {
				backgroundColor : "#FFFFFF", //차트 배경색 설정
				type : "column", //차트 Type 설정
				borderColor : "#FFFFFF" //차트 테두리 색 설정
			},
			legend : {
				layout : "horizontal", //Legend 모양 설정
				align : "center", //Legend 가로 정렬 설정
				verticalAlign : "bottom" //Legend 세로 정렬 설정
			},
			
			colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
			
			plotOptions : {
				series : {
					dataLabels : { //시리즈의 데이터 레이블 설정
						enabled : true,
					}
				},
				column : {
					pointPadding : 0.1 // 컬럼간의 간격 설정
				},
			},
			xAxis : {
				categories : categories,
				
				tickInterval : 1, //X축 레이블 간격 설정
				labels : { //X축 레이블 설정
					enabled : true
				}
			},
			yAxis : {
//	 			tickInterval : 30, //Y축 레이블 간격 설정
				min : 0, //Y축 Min값 설정
				title : { //Y축 제목 설정
					text : ""
				}
			}
		});
		
		// analyzeChart.RemoveAll();

		//시리즈 생성
		// var seriesBrPrfCnt = analyzeChart.CreateSeries();
		// var seriesAnaCnt = analyzeChart.CreateSeries();
		// var seriesErrCnt = analyzeChart.CreateSeries();
				
		//각 시리즈별 이름과 타입 설정
		// seriesBrPrfCnt.SetOptions({
		// 	Name:"<s:message code='WHL.CCNT' />", /* 전체건수 */
		// 	Type:"column"
		// });
		// seriesAnaCnt.SetOptions({
		// 	Name:"<s:message code='ANLY.CCNT' />", /* 분석건수 */
		// 	Type:"column"
		// });		
		// seriesErrCnt.SetOptions({
		// 	Name:"<s:message code='EROR.CCNT' />", /* 오류건수 */
		// 	Type:"column"
		// });		

		
		//시리즈별 데이터 생성
		var pointsBrPrfCnt = new Array();
		var pointsAnaCnt = new Array();
		var pointsErrCnt = new Array();
		
		//데이터 길이 확인
// 		var cnt = data.length;
		pointsBrPrfCnt.push({x:0, y:numberFormat(data[0].prfCnt), name:"<s:message code='PROF.CCNT' />"}); /* 프로파일건수 */
		pointsBrPrfCnt.push({x:1, y:numberFormat(data[0].brCnt), name:"<s:message code='BZWR.RULE.CCNT' />"}); /* 업무규칙건수 */
		pointsAnaCnt.push({x:0, y:numberFormat(data[0].prfAnaCnt), name:"<s:message code='PROF.ANLY.CCNT' />"}); /* 프로파일 분석건수 */
		pointsAnaCnt.push({x:1, y:numberFormat(data[0].brAnaCnt), name:"<s:message code='BZWR.RULE.ANLY.CCNT' />"}); /* 업무규칙 분석건수 */
		pointsErrCnt.push({x:0, y:numberFormat(data[0].prfErrCnt), name:"<s:message code='PROF.EROR.CCNT' />"}); /* 프로파일 오류건수 */
		pointsErrCnt.push({x:1, y:numberFormat(data[0].brErrCnt), name:"<s:message code='BZWR.RULE.EROR.CCNT' />"}); /* 업무규칙 오류건수 */
		
		
		// seriesBrPrfCnt.AddPoints(pointsBrPrfCnt);
		// seriesAnaCnt.AddPoints(pointsAnaCnt);
		// seriesErrCnt.AddPoints(pointsErrCnt);
		
		// analyzeChart.AddSeries(seriesBrPrfCnt);
		// analyzeChart.AddSeries(seriesAnaCnt);
		// analyzeChart.AddSeries(seriesErrCnt);

		var seriesBrPrfCnt = {
			name:"<s:message code='WHL.CCNT' />", /* 전체건수 */
			type:"column",
			data: pointsBrPrfCnt
		};
		var seriesAnaCnt = {
			name:"<s:message code='ANLY.CCNT' />", /* 분석건수 */
			type:"column",
			data: pointsAnaCnt
		};		
		var seriesErrCnt = {
			name:"<s:message code='EROR.CCNT' />", /* 오류건수 */
			type:"column",
			data: pointsErrCnt
		};
		
		analyzeChart.setOptions({
			series: [
				seriesBrPrfCnt,
				seriesAnaCnt,
				seriesErrCnt
			]
		}, { append: true, resetData: true })

		analyzeChart.draw();
		
	});
}

function qualityChartDraw()	{
	
	$.getJSON('<c:url value="/portal/dashboard/dqQualityChart.do"/>', function(data){

		if(data ==  null) return;

		var categories;

		//data에서 차수데이터가 없을시 카테고리 설정...
		if(data[0].prfAnaDgr == null || data[0].prfAnaDgr == "undefined" || data[0].prfAnaDgr == "") {
			categories = new Array("<s:message code='PROF' />", "<s:message code='DQ.BR' />"); /* 프로파일 업무규칙 */ 
		} else {
			categories = new Array("<s:message code='PROF' /> ("+data[0].prfAnaDgr+")", "<s:message code='DQ.BR' /> ("+data[0].brAnaDgr+")"); /* 프로파일 차 업무규칙 차 */
		}

		//품질현황 차트 초기화
		qualityChart.setOptions({
			chart : {
				backgroundColor : "#FFFFFF", //차트 배경색 설정
				type : "column", //차트 Type 설정
				borderColor : "#FFFFFF" //차트 테두리 색 설정
			},
			legend : {
				layout : "horizontal", //Legend 모양 설정
				align : "center", //Legend 가로 정렬 설정
				verticalAlign : "bottom" //Legend 세로 정렬 설정
			},
			
			colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
			
			plotOptions : {
				series : {
					dataLabels : { //시리즈의 데이터 레이블 설정
						enabled : true,
					}
				},
				column : {
					pointPadding : 0.1 // 컬럼간의 간격 설정
				},
			},
			xAxis : {
				categories : categories, /* 프로파일 차 업무규칙 차 */
				tickInterval : 1, //X축 레이블 간격 설정
				labels : { //X축 레이블 설정
					enabled : true
				}
			},
			yAxis : {
//	 			tickInterval : 30, //Y축 레이블 간격 설정
				max : 100, //Y축 Max값 설정
				title : { //Y축 제목 설정
					text : ""
				}
			}
		});
		
		// qualityChart.RemoveAll();

		//시리즈 생성
		// var seriesPrfQs = qualityChart.CreateSeries();
		// var seriesBrQs = qualityChart.CreateSeries();
		
				
		// //각 시리즈별 이름과 타입 설정
		// seriesPrfQs.SetOptions({
		// 	Name:"<s:message code='PROF' />", /* 프로파일 */
		// 	Type:"column"
		// });
		// //각 시리즈별 이름과 타입 설정
		// seriesBrQs.SetOptions({
		// 	Name:"<s:message code='DQ.BR' />", /* 업무규칙 */
		// 	Type:"column"
		// });
		
		//시리즈별 데이터 생성
		var pointsPrfQs = new Array();
		var pointsBrQs = new Array();

		
		//데이터 길이 확인
// 		var cnt = data.length;
		pointsPrfQs.push({x:0, y:data[0].prfQs, name:"<s:message code='PROFILE.QUL.SCO'/>"}); /* 프로파일 품질점수 */
		pointsBrQs.push({x:1, y:data[0].brQs, name:"<s:message code='WRK.QUL.SCO'/>"}); /* 업무규칙 품질점수 */
		
		
		// seriesPrfQs.AddPoints(pointsPrfQs);
		// seriesBrQs.AddPoints(pointsBrQs);
		
		// qualityChart.AddSeries(seriesPrfQs);
		// qualityChart.AddSeries(seriesBrQs);

		//각 시리즈별 이름과 타입 설정
		var seriesPrfQs = {
			name:"<s:message code='PROF' />", /* 프로파일 */
			type:"column",
			data: pointsPrfQs
		};
		//각 시리즈별 이름과 타입 설정
		var seriesBrQs = {
			name:"<s:message code='DQ.BR' />", /* 업무규칙 */
			type:"column",
			data: pointsBrQs
		};

		qualityChart.setOptions({
			series: [
				seriesPrfQs,
				seriesBrQs
			]
		}, { append: true, resetData: true })
		
		qualityChart.draw();
		
	});
}

function approveChartDraw()	{
	
	$.getJSON('<c:url value="/portal/dashboard/dqApproveChart.do"/>', function(data){

		if(data ==  null) return;
		
		var categories;
		//data에서 차수데이터가 없을시 카테고리 설정...
		if(data[0].brAnaDgr == null || data[0].brAnaDgr == "undefined" || data[0].brAnaDgr == "") {
			categories = new Array("<s:message code='RCNT.ODR' />"); /* 최근차수 */
		} else {
			categories = new Array("<s:message code='RCNT.ODR' /> ("+data[0].brAnaDgr+"<s:message code='ODR'/>)"); /* 최근차수 차 */
		}
		
		//개선현황차트
		approveChart.setOptions({
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
			
			colors : ["#7AAAEE","#F06F3E","#AAEE6A","#F0E150","#5DA0A9","#75738B"],
			
			plotOptions : {
				series : {
					dataLabels : { //시리즈의 데이터 레이블 설정
						enabled : true,
					}
				},
				column : {
					pointPadding : 0.1 // 컬럼간의 간격 설정
				},
			},
			xAxis : {
				categories : categories,
				tickInterval : 1, //X축 레이블 간격 설정
				labels : { //X축 레이블 설정
					enabled : true
				}
			},
			yAxis : {
//	 			tickInterval : 30, //Y축 레이블 간격 설정
				min : 0, //Y축 Min값 설정
				title : { //Y축 제목 설정
					text : ""
				}
			},
			grid : {
				bottom : 80
			}
		});

		// approveChart.RemoveAll();

		//시리즈 생성
		// var seriesBrAnaCnt = approveChart.CreateSeries();
		// var seriesBrErrCnt = approveChart.CreateSeries();
		// var seriesCsCnt = approveChart.CreateSeries();
		// var seriesImCnt = approveChart.CreateSeries();
				
		//각 시리즈별 이름과 타입 설정
		// seriesBrAnaCnt.SetOptions({
		// 	Name:"<s:message code='ANLY.CCNT' />", /* 분석건수 */
		// 	Type:"column"
		// });
		// seriesBrErrCnt.SetOptions({
		// 	Name:"<s:message code='EROR.CCNT' />", /* 오류건수 */
		// 	Type:"column"
		// });		
		// seriesCsCnt.SetOptions({
		// 	Name:"<s:message code='IMPV.PLAN.REG.CCNT' />", /* 개선계획 등록건수 */
		// 	Type:"column"
		// });		
		// seriesImCnt.SetOptions({
		// 	Name:"<s:message code='IMPV.RSLT.REG.CCNT' />", /* 개선결과 등록건수 */
		// 	Type:"column"
		// });	
		
		//시리즈별 데이터 생성
		var pointsBrAnaCnt = new Array();
		var pointsBrErrCnt = new Array();
		var pointsCsCnt = new Array();
		var pointsImCnt = new Array();
		
		//데이터 길이 확인
// 		var cnt = data.length;
		pointsBrAnaCnt.push({x:0, y:data[0].brAnaCnt, name:"<s:message code='ANLY.CCNT' />"}); /* 분석건수 */
		pointsBrErrCnt.push({x:0, y:data[0].brErrCnt, name:"<s:message code='EROR.CCNT' />"}); /* 오류건수 */
		pointsCsCnt.push({x:0, y:data[0].csCnt, name:"<s:message code='IMPV.PLAN.REG.CCNT' />"}); /* 개선계획 등록건수 */
		pointsImCnt.push({x:0, y:data[0].imCnt, name:"<s:message code='IMPV.RSLT.REG.CCNT' />"}); /* 개선결과 등록건수 */
		
		
		// seriesBrAnaCnt.AddPoints(pointsBrAnaCnt);
		// seriesBrErrCnt.AddPoints(pointsBrErrCnt);
		// seriesCsCnt.AddPoints(pointsCsCnt);
		// seriesImCnt.AddPoints(pointsImCnt);
		
		// approveChart.AddSeries(seriesBrAnaCnt);
		// approveChart.AddSeries(seriesBrErrCnt);
		// approveChart.AddSeries(seriesCsCnt);
		// approveChart.AddSeries(seriesImCnt);

		var seriesBrAnaCnt = {
			name:"<s:message code='ANLY.CCNT' />", /* 분석건수 */
			type:"column",
			data: pointsBrAnaCnt
		};
		var seriesBrErrCnt = {
			name:"<s:message code='EROR.CCNT' />", /* 오류건수 */
			type:"column",
			data: pointsBrErrCnt
		};		
		var seriesCsCnt = {
			name:"<s:message code='IMPV.PLAN.REG.CCNT' />", /* 개선계획 등록건수 */
			type:"column",
			data: pointsCsCnt
		};		
		var seriesImCnt = {
			name:"<s:message code='IMPV.RSLT.REG.CCNT' />", /* 개선결과 등록건수 */
			type:"column",
			data: pointsImCnt
		};

		approveChart.setOptions({
			series: [
				seriesBrAnaCnt,
				seriesBrErrCnt,
				seriesCsCnt,
				seriesImCnt
			]
		}, { append: true, resetData: true })
		
		approveChart.draw();
		
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
	    	$("form[name=search]").attr('action', '<c:url value="/portal/totalsearch_dq/TotalSearchCtrl.do"/>').submit();
	    
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
            <div class="search"><input type="text" name="searchNm" id="searchNm" placeholder="<s:message code='MSG.INQ.WORD.INPT' />"> <button id="btnTotSearch" class="main_search_bt" >검색</button></div> <!-- 검색어를 입력하세요 --> <!-- 검색 -->
            <div class="txt_explain"><s:message code="MSG.SEARCH.USE.WNT"/></div> <!-- 통합검색을 이용하면 원하시는 내용을 더욱 빠르게 찾아볼 수 있습니다. -->
            <div class="txt_word">
            	<%-- <div class="nm_sch_popular_tit"><img src="<c:url value="/images/nm_search_popular.gif"/>" alt="<s:message code='POPULA.INQ.WORD' />"></div> --%> <!-- 인기검색어 -->
                <span>Top Keywords</span>
                <div class="nm_sch_popular_word" id="nm_sch_popular_word_dq" style="position:relative; padding-left:10px; overflow: hidden;"> 
                	<a href="#"><s:message code="INQ.WORD" />01</a>, <!-- 검색어 -->
                    <a href="#"><s:message code="INQ.WORD" />02</a>, <!-- 검색어 -->
                    <a href="#"><s:message code="INQ.WORD" />03</a> <!-- 검색어 -->
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
        	<div class="tit"><s:message code='BASE.INFO' /></div> <!-- 기준정보 -->
            <div class="bx">
            	<ul class="bx_left">
	            <c:forEach var="curstnd" items="${criresult}" begin="0" end="2" step="1">
	               	<li><span>${curstnd.cntName}</span><a href="<c:url value='${curstnd.page}'/>">${curstnd.totCnt}</a></li>
	            </c:forEach>
	            </ul>
	            <ul class="bx_right">
	            <c:forEach var="curstnd" items="${criresult}" begin="3" step="1">
	               	<li><span>${curstnd.cntName}</span><a href="<c:url value='${curstnd.page}'/>">${curstnd.totCnt}</a></li>
	            </c:forEach>
	            </ul>
            </div>
        </div>
        
        <!-- 데이터모델 -->
        <div class="n4m_box24">
        	<div class="tit"><s:message code="BZWR.TRRT.PART.IMPV.PRES" /></div> <!-- 업무영역별 개선현황 -->
            <!-- <div class="nm_tit_txt"></div> -->
            <!-- <div class="nm_dm_cont"> -->
            	<table border="0" cellspacing="0" cellpadding="0" class="n4m_tb_02" summary="">
                <caption>
                <s:message code="RCNT.ODR.IMPV.PRES" /> <!-- 최근차수 개선현황 -->
                </caption>
                
                <colgroup>
                    <col style="width:18%;">
                    <col style="width:17%;">
                    <col style="width:17%;">
                    <col style="width:17%;">
                    <col style="width:17%;">
                    <col style="width:17%;">
                </colgroup>
                    
                  <tr class="tr_th">
                    <th style="height:23px;"><s:message code="BZWR.TRRT.NM" /></th> <!-- 업무영역명 -->
                    <th><s:message code="BZWR.RULE.CCNT" /></th> <!-- 업무규칙건수 -->
                    <th><s:message code="ANLY.CCNT" /></th> <!-- 분석건수 -->
                    <th><s:message code="EROR.CCNT" /></th> <!-- 오류건수 -->
                    <th><s:message code="IMPV.PLAN" /><!-- <br><s:message code="CMPL.CCNT" /> --></th> <!-- 개선계획 완료건수 -->
                    <th><s:message code="IMPV.RSLT" /><!-- <br><s:message code="CMPL.CCNT" /> --></th> <!-- 개선결과 완료건수 -->
                  </tr>
		             <c:forEach var="upresult"   end="6"  items="${bizareaImpvResult}" varStatus="status">
			             <tr style="cursor:default;">
			                <td class="txtcenter">${upresult.bizareaNm}</td>
			                <td class="txtcenter">${upresult.braCnt}</td>  
			                <td class="txtcenter">${upresult.anaCnt}</td>
			                <td class="txtcenter">${upresult.errCnt}</td>
			                <td class="txtcenter">${upresult.csCnt}</td>
			                <td class="txtcenter">${upresult.imCnt}</td>
			             </tr>
		             </c:forEach>
                </table>
            <!-- </div> -->
        </div>
        
       <!-- 차트 --> 
       <!-- <div class="nm_chart"> -->
        	<%-- <div class="nm_chart_navi"><a href="#"><img src="<c:url value="/images/nm_chart_navi_next.gif"/>"  alt="<s:message code='NXT.PAGE' />"></a></div> --%> <!-- 다음페이지 -->
        	
        	<div class="n4m_box31">
        		<div class="tit"><s:message code="DIAG.PRES" /></div> <!-- 진단현황 -->
                <div id="analyzeChart" class="chart">
                	<%-- <script type="text/javascript">
		    		createIBChart("analyzeChart", "100%", "220px");
		  			</script> --%>
                </div>
            </div>
            
        	<div class="n4m_box32">
        		<div class="tit"><s:message code="QLTY.PRES" /></div> <!-- 품질현황 --> 
                <div id="qualityChart" class="chart">
                	<%-- <script type="text/javascript">
		    					createIBChart("qualityChart", "100%", "220px");
		  			</script> --%>
                </div>
            </div>

            <div class="n4m_box33">
        		<div class="tit"><s:message code="IMPV.ACVT.PRES" /></div> <!-- 개선활동현황 -->
                <div id="approveChart" class="chart">
                	<%-- <script type="text/javascript">
		    		createIBChart("approveChart", "100%", "220px");
		  			</script> --%>
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
