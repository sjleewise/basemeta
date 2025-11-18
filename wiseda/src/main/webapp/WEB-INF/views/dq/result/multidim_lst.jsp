<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title>다차원분석</title>

<script type="text/javascript"
	src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript"
	src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<script type="text/javascript">
	var connTrgSchJson = ${codeMap.connTrgSch};

	EnterkeyProcess("Search");

	$(document)
			.ready(
					function() {

						//그리드 초기화 
						initGrid();
						//top5 초기화
						top5Ajax();

						//탭 초기화
						//$( "#tabs" ).tabs();

						$("#btnTreeNew").hide();

						$("#btnDelete").hide();

						//조회
						$("#btnSearch").click(function() {

							$('#bizrule_sel_title').html(null);

							doAction("Search");

						}).show();

						//엑셀다운로드
						$("#btnExcelDown").click(function() {

							doAction("Down2Excel");

						});

						//업무영역명 검색 팝업
						$('.btnBizAreaLnmPop')
								.click(
										function(event) {
											event.preventDefault(); //브라우저 기본 이벤트 제거...
											var url = '<c:url value="/dq/criinfo/bizarea/popup/bizarea_pop.do"/>';
											var popwin = OpenModal(url
													+ "?sflag=BIZLNM",
													"bizAreaPop", 800, 600,
													"no");
											popwin.focus();
										});

						//품질지표명 검색 팝업
						$('.btnDqiLnmPop')
								.click(
										function(event) {
											event.preventDefault(); //브라우저 기본 이벤트 제거...
											var url = '<c:url value="/dq/criinfo/dqi/popup/dqi_pop.do"/>';
											var popwin = OpenModal(url
													+ "?sflag=DQILNM",
													"ctqLstPop", 800, 600, "no");
											popwin.focus();
										});

						//중요정보항목명 검색 팝업
						$('.btnCtqLnmPop')
								.click(
										function(event) {
											event.preventDefault(); //브라우저 기본 이벤트 제거...
											var url = '<c:url value="/dq/criinfo/ctq/popup/ctq_pop.do"/>';
											var popwin = OpenModal(url
													+ "?sflag=CTQLNM",
													"ctqPop", 800, 600, "no");
											popwin.focus();
										});

						//bizrule_detail.jsp를 등록요청페이지와 공유하므로, 필요없는 부분 hide...

						//======================================================
						// 셀렉트 박스 초기화
						//======================================================
						double_select(connTrgSchJson,
								$("#frmSearch #dbConnTrgId"));
						$('select', $("#frmSearch #dbConnTrgId").parent())
								.change(function() {
									double_select(connTrgSchJson, $(this));
								});

						//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
						setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
						setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");

					});

	EnterkeyProcess("Search");

	function initGrid() {
		with (grid_sheet) {

			var cfg = {
				SearchMode : 2,
				Page : 100
			};
			SetConfig(cfg);

			SetMergeSheet(1);

			var headers = [ {
				Text : "<s:message code='DQ.HEADER.MULTIDIM.LST1'/>",
				Align : "Center"
			}, {
				Text : "<s:message code='DQ.HEADER.MULTIDIM.LST2'/>",
				Align : "Center"
			} ];

			//No.|업무영역명|지표명|진단구분|진단대상ID|진단대상명|스키마ID|스키마명|BRID|업무규칙명|1차|1차|1차|2차|2차|2차|3차|3차|3차
			//No.|업무영역명|지표명|진단구분|진단대상ID|진단대상명|스키마ID|스키마명|BRID|업무규칙명|분석건수|오류건수|오류율(%)|분석건수|오류건수|오류율(%)|분석건수|오류건수|오류율(%)

			var headerInfo = {
				Sort : 1,
				ColMove : 1,
				ColResize : 1,
				HeaderCheck : 1
			};

			InitHeaders(headers, headerInfo);

			var cols = [ {
				Type : "Seq",
				Width : 20,
				SaveName : "ibsSeq",
				Align : "Center",
				Edit : 0,
				Hidden : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "bizAreaLnm",
				Align : "Left",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "uppDqiLnm",
				Align : "Left",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dqiLnm",
				Align : "Left",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dbConnTrgId",
				Align : "Left",
				Edit : 0,
				Hidden : 1,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dbConnTrgPnm",
				Align : "Left",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dbSchId",
				Align : "Left",
				Edit : 0,
				Hidden : 1,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "dbSchPnm",
				Align : "Left",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "brId",
				Align : "Center",
				Edit : 0,
				Hidden : 1,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "brNm",
				Align : "Center",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			},
			//                     {Type:"Text", 		Width:60,   SaveName:"anaDgr",  Align:"Center", Edit:0, Hidden:0, ColMerge:0},
			{
				Type : "Text",
				Width : 100,
				SaveName : "anaCnt",
				Align : "Right",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "erCnt",
				Align : "Right",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 80,
				SaveName : "erRate",
				Align : "Center",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "anaCnt2",
				Align : "Right",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "erCnt2",
				Align : "Right",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 80,
				SaveName : "erRate2",
				Align : "Center",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "anaCnt3",
				Align : "Right",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 100,
				SaveName : "erCnt3",
				Align : "Right",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			}, {
				Type : "Text",
				Width : 80,
				SaveName : "erRate3",
				Align : "Center",
				Edit : 0,
				Hidden : 0,
				ColMerge : 0
			} ];

			//각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			InitColumns(cols);

			FitColWidth();
			//마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			SetExtendLastCol(1);
		}

		//==시트설정 후 아래에 와야함=== 
		init_sheet(grid_sheet);
		//===========================

	}

	function doAction(sAction) {

		switch (sAction) {
		case "Search":
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch(
					"<c:url value="/dq/result/getMultiDimLst.do" />", param);

			break;

		case "Down2Excel": //엑셀내려받기
			grid_sheet.Down2Excel({
				HiddenColumn : 1,
				Merge : 1,
				FileName : '프로파일품질추이'
			});
			//   			progChart.Down2Image({FileName:"ProfileProg", Type:IBExportType.JPEG, Width:800, Url:"./../../../js/IBChart/jsp/Down2Image.jsp"});
			break;
		}
	}

	//지표별 품질현황
	function dqiChartDraw() {
			var data = grid_sheet.GetSaveJson({AllSave:1});
			//console.log(data);
			if (data == null)
				return;
			//console.log(grid_sheet.ColValueDup("uppDqiLnm"));
			//	       gapChart.RemoveAll();

			//시리즈 생성
			//	       var series    = gapChart.CreateSeries();      

			//각 시리즈별 이름과 타입 설정
			//	       series.setOptions({
			//	          Name:"<s:message code='MDEL.VS.DB.MTCH.RT' />", /* 모델 VS DB일치율 */
			//	          Type:"pie"
			//	       });
			//console.log(data);
			//console.log(data.data.length);
			var dqiLst = [];
			var dupCol = grid_sheet.ColValueDup("uppDqiLnm");
			for (i = 0; i < data.data.length; i++) {
				for(j=0;j<dupCol.lenth;j++){
					if(dupCol[j]>i || i==dupCol[j]){
						break;
					}						
				}
				dqiLst.push(data.data[i].uppDqiLnm);
				
			}			

			//시리즈별 데이터 생성
			var points = new Array();
			var errSum = [];
			var dataCnt = [];
			for (i = 0; i < dqiLst.length; i++) {//초기화
				errSum.push(0.0);
				dataCnt.push(-1);
			}

			for (var i = 0; i < data.data.length; i++) {
				//console.log(data.DATA[i].uppDqiLnm);	         
				for (var j = 0; j < dqiLst.length; j++) {
					if (data.data[i].uppDqiLnm == dqiLst[j]) {
						if (data.data[i].erRate != "") {
							errSum[j] += parseFloat(data.data[i].erRate);
							if (dataCnt[j] == -1) {
								dataCnt[j] = 1;
							} else {
								dataCnt[j]++;
							}
						}
						if (data.data[i].erRate2 != "") {
							errSum[j] += parseFloat(data.data[i].erRate2);
							if (dataCnt[j] == -1) {
								dataCnt[j] = 1;
							} else {
								dataCnt[j]++;
							}
						}
						if (data.data[i].erRate3 != "") {							
							errSum[j] += parseFloat(data.data[i].erRate3);
							if (dataCnt[j] == -1) {
								dataCnt[j] = 1;
							} else {
								dataCnt[j]++;
							}
						}
					}
				}

			}

			for (var i = 0; i < dqiLst.length; i++) {
				points.push({
					x : 0,
					y : errSum[i] / dataCnt[i],
					name : dqiLst[i],
					sliced : 1
				});
			}

			//	       series.AddPoints(points);

			//	       gapChart.AddSeries(series);

			var series = {
				name : "<s:message code='DATA.QLTY.INDC.QLTY.PRES' />",
				type : "pie",
				data : points

			}

			dqiChart.setOptions({
				series : [ series ]
			}, {
				append : true,
				resetData : true
			});

			dqiChart.draw();	

	}

	//업무영역별 품질현황
	function bizAreaChartDraw() {
		// 	$.getJSON('<c:url value="/dq/criinfo/bizarea/getBizAreaList.do"/>', function(data){
		// 		if(data ==  null) return;
		// 		var bizArea = [];//업무 영역 저장
		// 		for(var i = 0; i< data.DATA.length;i++){
		// 			if(data.DATA[i].level == "1" && data.DATA[i].regTypCd == "C")
		// 			//console.log(data.DATA[i].bizAreaLnm);
		// 			bizArea.push(data.DATA[i].bizAreaLnm);

		// 		}
		var data = grid_sheet.GetSaveJson({AllSave:1});
			if (data == null)
				return;
			console.log()
			var subjName = new Array();
			var erSum = [];
			var dataCnt = [];

			var bizArea = [];//업무 영역 저장
			var dupCol = grid_sheet.ColValueDup("bizAreaLnm");
			for (i = 0; i < data.data.length; i++) {
				for(j=0;j<dupCol.lenth;j++){
					if(dupCol[j]>i || i==dupCol[j]){
						break;
					}						
				}
				bizArea.push(data.data[i].bizAreaLnm);
				
			}			

			//데이터 길이 확인
			for (var i = 0; i < bizArea.length; i++) {
				//erSum = new Array();
				erSum[i] = [ 0, 0, 0 ];
				dataCnt[i] = [ -1, -1, -1 ];
			}

			var cnt2 = data.data.length;
			for (i = 0; i < bizArea.length; i++) {
				subjName[i] = bizArea[i];
			}
			//       rqstChart.RemoveAll();

			//요청현황 차트 초기화
			bizAreaChart.setOptions({
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

				colors : [ "#7AAAEE", "#F06F3E", "#AAEE6A", "#F0E150",
						"#5DA0A9", "#75738B" ],

				plotOptions : {
					series : {
						DataLabels : { //시리즈의 데이터 레이블 설정
							Enabled : true,
						}
					},
					column : {
						pointPadding : 0.1
					// 컬럼간의 간격 설정
					},
				},
				xAxis : {
					categories : subjName, /* 등록요청현황 */
					tickInterval : 1, //X축 레이블 간격 설정
					labels : { //X축 레이블 설정
						enabled : true
					}
				},
				yAxis : {
					//             TickInterval : 30, //Y축 레이블 간격 설정
					min : 0, //Y축 Min값 설정
					title : { //Y축 제목 설정
						text : ""
					}
				}
			});

			//시리즈 3개 생성
			//      var seriesCnt    = rqstChart.CreateSeries();      
			//      var seriesAprv    = rqstChart.CreateSeries();      
			///      var seriesRjct    = rqstChart.CreateSeries();      

			//각 시리즈별 이름과 타입 설정
			var seriesCnt = {
				name : "1차", // 총계<s:message code='AGGT' />
				type : "column"
			};
			var seriesAprv = {
				name : "2차", // 승인
				type : "column"
			};
			var seriesRjct = {
				name : "3차", // 반려 
				type : "column"
			};
			/*var seriesTot = {
			   //Name:"전체",
			   name:"품질점수", // 테이블수 
			   type:"column"
			};*/

			//시리즈별 데이터 생성
			var pointsCnt = new Array();
			var pointsAprv = new Array();
			var pointsRjct = new Array();
			//var totpoints = new Array();
			//console.log(data.DATA[0].anaDgr);

			for (i = 0; i < data.data.length; i++) {
				for (var j = 0; j < bizArea.length; j++) {
					if (data.data[i].bizAreaLnm == bizArea[j]) {
						if (data.data.erRate != "") {
							erSum[i][0] += parseFloat(data.data[i].erRate);
							if (dataCnt[i][0] == -1) {
								dataCnt[i][0] = 1;
							} else {
								dataCnt[i][0]++;
							}
						}
						if (data.data.erRate2 != "") {
							erSum[i][1] += parseFloat(data.data[i].erRate2);
							if (dataCnt[i][1] == -1) {
								dataCnt[i][1] = 1;
							} else {
								dataCnt[i][0]++;
							}
						}
						if (data.data.erRate3 != "") {
							erSum[i][2] += parseFloat(data.data[i].erRate3);
							if (dataCnt[i][2] == -1) {
								dataCnt[i][2] = 1;
							} else {
								dataCnt[i][2]++;
							}
						}
					}
				}

			}

			for (i = 0; i < bizArea.length; i++) {
				pointsCnt.push({
					x : i,
					y : 100 - erSum[i][0] / dataCnt[i][0],
					name : "<s:message code='QLTY.SCR' />"
				});
				pointsAprv.push({
					x : i,
					y : 100 - erSum[i][1] / dataCnt[i][1],
					name : "<s:message code='QLTY.SCR' />"
				});
				pointsRjct.push({
					x : i,
					y : 100 - erSum[i][2] / dataCnt[i][2],
					name : "<s:message code='QLTY.SCR' />"
				});
			}

			//       seriesCnt.AddPoints(pointsCnt);
			//       seriesAprv.AddPoints(pointsAprv);
			//       seriesRjct.AddPoints(pointsRjct);

			seriesCnt.data = pointsCnt;
			seriesAprv.data = pointsAprv;
			seriesRjct.data = pointsRjct;

			//       rqstChart.AddSeries(seriesCnt);
			//       rqstChart.AddSeries(seriesAprv);
			//       rqstChart.AddSeries(seriesRjct);

			bizAreaChart.setOptions({
				series : [ seriesCnt, seriesAprv, seriesRjct ]
			//
			}, {
				append : true,
				resetData : true
			});

			bizAreaChart.draw();
		
	}

	//top5 ajax 함수
	function top5Ajax() {

		var url = "<c:url value="/dq/result/getMultiDimLstTop5.do" />";
		$.ajax({
			url : url,
			type : 'GET',
			success : function(data) {

				var list = '';
				if (data == null) {
					for (var i = 0; i < 5; i++) {
						'<tr><td></td><td></td><td></td><td></td><td></td></tr>'
					}
				} else if (data != null) {
					data
							.forEach(function(item) {
								list += '<tr>';
								list += '<td class="txtcenter">'
										+ item.dbConnTrgPnm + '</td>';
								list += '<td class="txtcenter">'
										+ item.dbcTblNm + '</td>';
								list += '<td class="txtcenter">' + item.anaCnt
										+ '</td>';
								list += '<td class="txtcenter">' + item.erCnt
										+ '</td>';
								list += '<td class="txtcenter">' + item.erRate
										+ '</td>';

								list += '</tr>';

							});
				}

				$("#top5result").empty();
				$("#top5result").append(list);
			}
		});
	}

	function grid_sheet_OnClick(row, col, value, cellx, celly) {

		if (row < 1)
			return;

		//클릭한 진단대상명의 시작과 끝위치를 구한다.
		var tmpDB = grid_sheet.GetCellValue(row, "dbConnTrgLnm");
		var strVal = 0;
		var endVal = 0;
		for (var i = 0; i < grid_sheet.SearchRows(); i++) {
			if (grid_sheet.GetCellValue(i + 1, "dbConnTrgLnm") == tmpDB) {
				if (strVal == 0)
					strVal = (i + 1);
			} else {
				if (strVal != 0 && endVal == 0) {
					endVal = i;
					break;
				}
			}

			if (i + 1 == grid_sheet.SearchRows())
				endVal = (i + 1);
		}

		//차트데이터 생성
		// 	chartDraw(strVal, endVal);

	}

	function grid_sheet_OnSearchEnd(code) {
		if (code < 0) {
			showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
			return;
		}
		//차트 클리어
		// progChart.RemoveAll();

		if (grid_sheet.SearchRows() == 0)
			return;
		//var data = grid_sheet.GetSaveJson({AllSave:1});
		dqiChartDraw();
		bizAreaChartDraw();
		//첫번째 행 클릭상태로 차트데이터를 생성...
		// 	grid_sheet_OnClick(1);
	}

	$(window).on(
			'load',
			function() {
				//차트들
				var con1 = document.getElementById("dqiChart");
				var con3 = document.getElementById("bizAreaChart");
				createIBChart(con1, "dqiChart", {
					width : "auto",
					height : "210px"
				});
				createIBChart(con3, "bizAreaChart", {
					width : "auto",
					height : "210px"
				});

				//차트 초기화
				dqiChart.setOptions({
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

					colors : [ "#7AAAEE", "#F06F3E", "#AAEE6A", "#F0E150",
							"#5DA0A9", "#75738B" ],

					plotOptions : {
						pie : {
							innerSize : 20,
							slicedOffset : 20,
							allowPointSelect : true,
							startAngle : 60
						},
						series : {
							dataLabels : {
								enabled : true,
								align : "center",
								color : "#333333",
								formatter : function(params) {
									var name = params.name
									var value = params.value || null
									if (value == 'undefined' || value == ""
											|| value == null) {
										return name;
									} else {
										return name + ': ' + value;
									}
								}
							}
						},
						column : {
							pointPadding : 0.1
						// 컬럼간의 간격 설정
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

				//Top5 표 생성			
				top5Ajax();
				//지표별 품질현황 생성
				dqiChartDraw();
				//업무영역별 품질현황 생성
				bizAreaChartDraw();

			});
</script>
</head>
<body>
	<div class="menu_subject">
		<div class="tab">
			<div class="menu_title">
				<s:message code="PROF.QLTY.TRANSITION" />
			</div>
			<!--프로파일 품질추이-->

		</div>
	</div>
	<div style="clear: both; height: 5px;">
		<span></span>
	</div>
	<div id="search_div">
		<div class="stit">
			<s:message code="INQ.COND2" />
		</div>
		<!--검색조건-->
		<div style="clear: both; height: 5px;">
			<span></span>
		</div>
		<form id="frmSearch" name="frmSearch" method="post">
			<input type="hidden" id="bizAreaId" name="bizAreaId" /> <input
				type="hidden" id="dqiId" name="dqiId" /> <input type="hidden"
				id="ctqId" name="ctqId" /> <input type="hidden" id="brId"
				name="brId" /> <input type="hidden" id="baseDttm" name="baseDttm" />
			<fieldset>
				<legend>
					<s:message code="FOREWORD" />
				</legend>
				<!--머리말-->
				<div class="tb_basic2">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						summary="<s:message code='BZWR.RULE.INQ' />">
						<!--업무규칙 조회-->

						<caption>
							<s:message code="BZWR.RULE.INQ" />
						</caption>
						<!--업무규칙 조회-->

						<colgroup>
							<col style="width: 10%;" />
							<col style="width: 10%;" />
							<col style="width: 10%;" />
							<col style="width: 10%;" />
							<col style="width: 10%;" />

						</colgroup>

						<tbody>
							<tr>
								<th scope="row"><label for="brNm"><s:message
											code="BZWR.RGR.NM" /></label></th>
								<!--업무규칙명-->
								<td><input type="text" name="brNm" id="brNm" /></td>
								<th scope="row"><label for="bizAreaLnm"><s:message
											code="BZWR.TRRT.NM" /></label></th>
								<!--업무영역명-->
								<td><input type="text" name="bizAreaLnm" id="bizAreaLnm" />
								</td>
								<th scope="row"><label for="dqiLnm"><s:message
											code="DATA.QLTY.INDC.NM" /></label></th>
								<!--데이터품질지표명-->
								<td colspan="5"><input type="text" name="dqiLnm"
									id="dqiLnm" /></td>
								<%-- <th scope="row"><label for="uppDqiLnm"><s:message code="UPRN.DATA.QLTY.INDC.NM"/></label></th><!-- 상위데이터품질지표명 -->
                        <td>
                            <input type="text" name="uppDqiLnm" id="uppDqiLnm" />
                        </td> --%>

							</tr>
							<tr>
								<th scope="row"><label for="dbSchId"><s:message
											code="DB.NM" /></label></th>
								<!--DB명-->
								<td><select id="dbConnTrgId" class="" name="dbConnTrgId">
										<option value="">---
											<s:message code="CHC" />---
										</option>
										<!--선택-->
								</select> <select id="dbSchId" class="" name="dbSchId"
									style="display: none">
										<option value="">---
											<s:message code="CHC" />---
										</option>
										<!--선택-->
								</select></td>
								<th scope="row"><label for="dbcTblNm"><s:message
											code="TBL.NM" /></label></th>
								<!--테이블명-->
								<td><input type="text" name="dbcTblNm" id="dbcTblNm" /></td>
								<th scope="row"><label for="dbcColNm"><s:message
											code="CLMN.NM" /></label></th>
								<!--컬럼명-->
								<td><input type="text" name="dbcColNm" id="dbcColNm" /></td>
								<th scope="row"><label for="bizAreaLnm"><s:message
											code="BZWR.TRRT.NM" /></label></th>
								<!--업무영역명-->
								<td><input type="text" name="bizAreaLnm" id="bizAreaLnm" />
								</td>
							</tr>

							<%-- <tr>                               
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
                         
                           
                        </tr>     --%>
						</tbody>
					</table>
				</div>
			</fieldset>
			<div class="tb_comment">
				-
				<s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" />
				<span style="font-weight: bold; color: #444444;">Ctrl + C</span>
				<s:message code="MSG.CHC.USE" />
			</div>
			<!--를 사용하시면 됩니다.-->
			<!--클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고-->

		</form>

		<!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
		<div style="clear: both; height: 5px;">
			<span></span>
		</div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
		<div style="clear: both; height: 5px;">
			<span></span>
		</div>

		<!-- 그리드 입력 입력 -->
		<div class="grid_01">
			<script type="text/javascript">
				createIBSheet("grid_sheet", "100%", "250px");
			</script>
		</div>

		<div style="clear: both; height: 20px;">
			<span></span>
		</div>


		<!--  하단 chart및 표 -->
		<div style="display: flex; justify-content: center;">
			<!--지표별 품질현황 파이형차트-->
			<div class="n4m_box31">
				<div class="tit">지표별 품질 현황</div>
				<!-- <s:message code='DATA.QLTY.INDC.QLTY.PRES' />-->
				<div id="dqiChart" class="chart">
					<%--                    <script type="text/javascript"> --%>
					<!--                 createIBChart("dqiChart", "375px", "210px"); -->
					<%--                  </script> --%>
				</div>
			</div>
			<!--업무영역별 품질현황 막대형차트-->
			<div class="n4m_box32">
				<div class="tit">
					<s:message code='BZWR.TRRT.QLTY.PRES' />
				</div>
				<div id="bizAreaChart" class="chart">
					<%--                    <script type="text/javascript"> --%>
					<!--                  createIBChart("bizAreaChart", "375px", "210px"); -->
					<%--                  </script> --%>
				</div>
			</div>
			<!--오류상위 Top 5 표-->
			<div class="n4m_box22">
				<div class="tit">
					<s:message code="ERR.TOP.FIVE" />
				</div>
				<table border="0" cellspacing="0" cellpadding="0" class="n4m_tb_02"
					summary="">
					<caption>
						<s:message code="GRID" />
						<!-- 그리드 -->
					</caption>

					<!--  DB명 | 테이블 | 진단건수 | 오류건수 | 오류율  -->
					<colgroup>
						<col style="width: 25%;">
						<col style="width: 25%;">
						<col style="width: 15%;">
						<col style="width: 15%;">
						<col style="width: 20%;">
					</colgroup>

					<tr class="tr_th">
						<th style="height: 23px;"><s:message code="DB.NM" /></th>
						<!-- DB명 -->
						<th><s:message code="TBL.NM" /></th>
						<!-- 테이블 -->
						<th><s:message code="DIAG.CCNT" /></th>
						<!-- 진단건수 -->
						<th><s:message code="EROR.CCNT" /></th>
						<!-- 오류건수 -->
						<th><s:message code="EROR.RT" /></th>
						<!-- 오류율 -->
					</tr>

					<tbody id="top5result">
						<%-- <c:forEach begin="0" end="5" step="1" varStatus="status">
		             	<tr>
		             		<td></td>
		             		<td></td>
		             		<td></td>
		             		<td></td>
		             		<td></td>
		             	</tr>
		             </c:forEach> --%>
					</tbody>
				</table>
			</div>

		</div>
	</div>
	<%-- IBChartLite Init Scripts --%>
	<script type="text/javascript">
		/*createIBChart("chart1", "progChart", {
		 width: "100%",
		 height: "350px"	
		 });*/
	</script>
</body>

</html>