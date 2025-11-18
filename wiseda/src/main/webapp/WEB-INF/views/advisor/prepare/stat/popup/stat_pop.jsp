<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
	<head>
		<title><s:message code="CD.TBL.INQ.1"/></title>
		
			<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
			<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
			<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script>		
		
		<script type="text/javascript">
			$(document).ready(function() {
				if('${search.prfId}' != '') {
					$("#tabs").tabs().tabs('select', 0);
				} else if('${search.brId}' != '') {
					$("#tabs").tabs().tabs('select', 1);
				} else if('${search.otlDtcId}' != '') {
					$("#tabs").tabs().tabs('select', 2);
				} else if('${type}' == 'clst') {
					$("#tabs").tabs().tabs('select', 3);
				} else if('${type}' == 'mtch') {
					$("#tabs").tabs().tabs('select', 4);
				}
			});
			
			$(window).resize(function(){
				window_width = window.innerWidth;
				
				$("#chart_mtch").css({"width":window_width-10,"float":"left"});	
			});
			
			$(window).on('load',function() {
				//그리드 초기화
				initGrid();
				showFunc();
				var window_width = window.innerWidth;
				
			   $("#chart_mtch").css({"width":window_width-10,"float":"left"});	
				
				createIBChart("chart_mtch", "chart_mtch", {
					width: "auto",
					height: "250px"
				});					
			});
			
			function initGrid() {
				//그리드 초기 설정
				//진단대상 테이블 grid
				with(grid_prof_dtl){
			    	
			    	var cfg = {SearchMode:2,Page:100};
			        SetConfig(cfg);
			        
			        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.RQST'/>", Align:"Center"} ];
					//테이블명|테이블한글명|컬럼명|컬럼한글명|프로파일ID|프로파일명|분석종류|분석차수|분석총건수|추정오류건수|추정오류율
			        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
			        
			        InitHeaders(headers, headerInfo); 
	
			        var cols = [
			                    {Type:"Text",   Width:150,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0},
			                    {Type:"Text",   Width:150,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0},
			                    {Type:"Text",   Width:150,  SaveName:"dbcColNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:150,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:100,  SaveName:"prfId",    	Align:"Left", Edit:0, Hidden:1},
			                    {Type:"Text",   Width:200,  SaveName:"prfNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:100,  SaveName:"prfKndNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:80,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"esnErCnt"  , Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"###\\%",  SaveName:"erRate"  , Align:"Center", Edit:0, Hidden:0}
			                ];
			        
			        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			        InitColumns(cols);
			        
			        FitColWidth();
			        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			        SetExtendLastCol(1);
			        SetMergeSheet(msPrevColumnMerge); 
			    }
			    //==시트설정 후 아래에 와야함===
				init_sheet(grid_prof_dtl);    
			    //===========================
			    	
				with(grid_br_dtl){
			    	
			    	var cfg = {SearchMode:2,Page:100};
			        SetConfig(cfg);
			        
			        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.RQST2'/>", Align:"Center"} ];
					//테이블명|테이블한글명|컬럼명|컬럼한글명|업무규칙ID|업무규칙명|분석차수|분석총건수|추정오류건수|추정오류율
			        
			        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
			        
			        InitHeaders(headers, headerInfo); 
	
			        var cols = [
			                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0},
			                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0},
			                    {Type:"Text",   Width:100,  SaveName:"dbcColNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:100,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:100,  SaveName:"brId",    	Align:"Left", Edit:0, Hidden:1},
			                    {Type:"Text",   Width:150,  SaveName:"brNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:80,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"erCnt"  , Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"###\\%",  SaveName:"erRate"  , Align:"Center", Edit:0, Hidden:0}
			                ];
			        
			        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			        InitColumns(cols);
			        
			        FitColWidth();
			        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			        SetExtendLastCol(1);
			        SetMergeSheet(msPrevColumnMerge);
			    }
			    //==시트설정 후 아래에 와야함===
				init_sheet(grid_br_dtl);    
			    //===========================
			    	
				with(grid_otl_dtl){
			    	
			    	var cfg = {SearchMode:2,Page:100};
			        SetConfig(cfg);
			        
			        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.RQST3'/>", Align:"Center"} ];
					//테이블명|테이블한글명|컬럼명|컬럼한글명|이상값탐지ID|이상값탐지명|분석차수|분석총건수|추정오류건수|추정오류율
			        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
			        
			        InitHeaders(headers, headerInfo); 
	
			        var cols = [
			                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0},
			                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0},
			                    {Type:"Text",   Width:100,  SaveName:"dbColNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:100,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:100,  SaveName:"otlDtcId",    	Align:"Left", Edit:0, Hidden:1},
			                    {Type:"Text",   Width:150,  SaveName:"otlNm",    	Align:"Left", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:80,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"esnErCnt"  , Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"###\\%",  SaveName:"erRate"  , Align:"Center", Edit:0, Hidden:0}
			                ];
			        
			        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			        InitColumns(cols);
			        
			        FitColWidth();
			        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			        SetExtendLastCol(1);
			        SetMergeSheet(msPrevColumnMerge);
			    }
			    //==시트설정 후 아래에 와야함===
				init_sheet(grid_otl_dtl);    
			    //===========================
			    	
				with(grid_clst_dtl){
			    	
			    	var cfg = {SearchMode:2,Page:100};
			        SetConfig(cfg);
			        
			        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.POP'/>", Align:"Center"} ];
					//No|텍스트 클러스터링 결과|분석총건수|종류수
					
			        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
			        
			        InitHeaders(headers, headerInfo); 
	
			        var cols = [                        
			                    {Type:"Text",    Width:50,   SaveName:"mtcSno",      Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:100,  SaveName:"clstNm",    	Align:"Left", Edit:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"varCnt"  , Align:"Center", Edit:0, Hidden:0}
			                ];
			        
			        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			        InitColumns(cols);
			        
			        FitColWidth();
			        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			        SetExtendLastCol(1);
			        SetMergeSheet(msPrevColumnMerge);
			    }
			    //==시트설정 후 아래에 와야함===
				init_sheet(grid_clst_dtl);    
			    //===========================
			    	
				with(grid_mtch_dtl){
			    	
			    	var cfg = {SearchMode:2,Page:100};
			        SetConfig(cfg);
			        
			        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.POP2'/>", Align:"Center"} ];
					//No|매칭 정확도 구분|분석총건수
					
			        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
			        
			        InitHeaders(headers, headerInfo); 
	
			        var cols = [                        
			                    {Type:"Seq",    Width:50,   SaveName:"seq",      Align:"Center", Edit:0, Hidden:0},
			                    {Type:"Text",   Width:100,  SaveName:"mtchNm",    	Align:"Left", Edit:0},
			                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"varCnt",    	Align:"Center", Edit:0, Hidden:0}
			                ];
			        
			        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
			        InitColumns(cols);
			        
			        FitColWidth();
			        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
			        SetExtendLastCol(1);
			        SetMergeSheet(msPrevColumnMerge);
			    }
			    //==시트설정 후 아래에 와야함===
				init_sheet(grid_mtch_dtl);    
			    //===========================
			    	
			}
			
			function doAction(sAction) {
				switch(sAction) {
				case "profSearch":
					var param = '';
					param = 'prfId=' + '${search.prfId}';
					
					grid_prof_dtl.DoSearch("<c:url value="/advisor/prepare/stat/popup/prof_dtl.do" />", param);
					
					break;
				case "brSearch":
					var param = '';
					param = 'brId=' + '${search.brId}';
					
					grid_br_dtl.DoSearch("<c:url value="/advisor/prepare/stat/popup/br_dtl.do" />", param);
					
					break;
				case "otlSearch":
					var param = '';
					param = 'otlDtcId=' + '${search.otlDtcId}';
					
					grid_otl_dtl.DoSearch("<c:url value="/advisor/prepare/stat/popup/otl_dtl.do" />", param);
					
					break;
				case "clstSearch":
					var param = '';
					param = 'clstId=' + '${search.clstId}';
					
					grid_clst_dtl.DoSearch("<c:url value="/advisor/prepare/stat/popup/clst_dtl.do" />", param);
					
					break;
				case "mtchSearch":
					var param = '';
					param = 'mtcId=' + '${search.mtcId}';
					
					grid_mtch_dtl.DoSearch("<c:url value="/advisor/prepare/stat/popup/mtch_dtl.do" />", param);
					
					break;
				}
			}
			
			function showFunc() {
				switch('${type}') {
				case "prof":
					$("#tab-pc01").show();   $("#tabs-pc01").show();
					$("#tab-pc02").hide();   $("#tabs-pc02").hide();
					$("#tab-pc03").hide();   $("#tabs-pc03").hide();
					$("#tab-pc04").hide();   $("#tabs-pc04").hide();
					$("#tab-pc05").hide();   $("#tabs-pc05").hide();
					
					doAction("profSearch");
					
					break;
				case "br":
					$("#tab-pc01").hide();   $("#tabs-pc01").hide();
					$("#tab-pc02").show();   $("#tabs-pc02").show();
					$("#tab-pc03").hide();   $("#tabs-pc03").hide();
					$("#tab-pc04").hide();   $("#tabs-pc04").hide();
					$("#tab-pc05").hide();   $("#tabs-pc05").hide();
					
					doAction("brSearch");
					
					break;
				case "otl":
					$("#tab-pc01").hide();   $("#tabs-pc01").hide();
					$("#tab-pc02").hide();   $("#tabs-pc02").hide();
					$("#tab-pc03").show();   $("#tabs-pc03").show();
					$("#tab-pc04").hide();   $("#tabs-pc04").hide();
					$("#tab-pc05").hide();   $("#tabs-pc05").hide();
					
					doAction("otlSearch");
					
					break;
				case "clst":
					$("#tab-pc01").hide();   $("#tabs-pc01").hide();
					$("#tab-pc02").hide();   $("#tabs-pc02").hide();
					$("#tab-pc03").hide();   $("#tabs-pc03").hide();
					$("#tab-pc04").show();   $("#tabs-pc04").show();
					$("#tab-pc05").hide();   $("#tabs-pc05").hide();
					
					doAction("clstSearch");
					
					break;
				case "mtch":
					$("#tab-pc01").hide();   $("#tabs-pc01").hide();
					$("#tab-pc02").hide();   $("#tabs-pc02").hide();
					$("#tab-pc03").hide();   $("#tabs-pc03").hide();
					$("#tab-pc04").hide();   $("#tabs-pc04").hide();
					$("#tab-pc05").show();   $("#tabs-pc05").show();
					
					doAction("mtchSearch");
					
					break;
				}
			}
			
			
			function grid_mtch_dtl_OnSearchEnd() {
				chart_mtch.removeAll();
				var lastRow = grid_mtch_dtl.GetDataLastRow();
				var intervalVal = 0;
				
				for(i=1; i<=lastRow; i++) {
					var varCnt = grid_mtch_dtl.GetCellValue(i, "varCnt");
					if(intervalVal < varCnt)
						intervalVal = varCnt;
						
				}
				
				var mtchNm_cell = new Array();
				var varCnt_cell = new Array();
				
				for(var i = lastRow; i>=1; i--){
					var a = grid_mtch_dtl.GetCellValue(i,'mtchNm')
					mtchNm_cell.push(a)
					var b = grid_mtch_dtl.GetCellValue(i,'varCnt')
					varCnt_cell.push(b)
				}
				

				var initData = toCamel({
					"chart": {
					    "type": "column"
					},
					"xAxis": {
						"labels": {
				        },
				        "title": {
				        	"text": "매칭 정확도"
				        },
				        "categories":mtchNm_cell,
				    },
				    "yAxis": [{
				        "labels": {
				            "format": '{value}',
				        },
				        "title": {
				        	"text": "텍스트 매칭",
				        },
				        "max":100
				    }, {
				        "title": {
				            "text": "",
				        },
				        "labels": {
				            "format": '{value}',
				        },
				        "opposite": true
				    }],
				    "series": [{
			              "name": "분석총건수",
			              "type": "column",
			              "data": varCnt_cell,
			              "yAxis": 1,
			              "Cursor" : "pointer",
			          }],
				    "tooltip": {
				        "headerFormat": "",
				        "shared": true
				    }
				});
				
				var pointsCol = new Array();
			    
			    chart_mtch.setOptions(initData);
				chart_mtch.draw();
			}
		</script>
		
	</head>
	
	<body>
		<div id="tabs">
		  <ul>
		    <li id="tab-pc01"><a href="#tabs-pc01">프로파일</a></li><!--프로파일-->
		    <li id="tab-pc02"><a href="#tabs-pc02">업무규칙</a></li><!--업무규칙-->
		    <li id="tab-pc03"><a href="#tabs-pc03">이상값탐지</a></li><!--이상값탐지-->
		    <li id="tab-pc04"><a href="#tabs-pc04">텍스트 클러스터링</a></li><!--텍스트 클러스터링-->
	    	<li id="tab-pc05"><a href="#tabs-pc05">텍스트 매칭</a></li><!--텍스트 매칭-->
		  </ul>
		  <div id="tabs-pc01">
		  	<div class="stit"><s:message code="PROF.STAT" /></div> <!-- 프로파일 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<div id="grid_01" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_prof_dtl", "99%", "250px");</script>            
			</div>
		  </div>
		  <div id="tabs-pc02">
		  	<div class="stit"><s:message code="BR.STAT" /></div> <!-- br -->
			<div style="clear:both; height:5px;"><span></span></div>	       
			<div id="grid_02" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_br_dtl", "99%", "250px");</script>            
			</div>
		  </div>
		  <div id="tabs-pc03">
		  	<div class="stit"><s:message code="OTL.STAT" /></div> <!-- 이상값탐지 -->
			<div style="clear:both; height:5px;"><span></span></div>	       
			<div id="grid_03" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_otl_dtl", "99%", "250px");</script>            
			</div>
		  </div>
		  <div id="tabs-pc04">
		  	<div class="stit"><s:message code="CLST.STAT" /></div> <!-- 클러스터링 -->
			<div style="clear:both; height:5px;"><span></span></div>	       
			<div id="grid_04" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_clst_dtl", "99%", "250px");</script>            
			</div>
		  </div>
		  <div id="tabs-pc05">
		  	<div class="stit"><s:message code="MTCH.STAT" /></div> <!-- 매칭 -->
			<div style="clear:both; height:5px;"><span></span></div>	       
			<div id="grid_05" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_mtch_dtl", "99%", "250px");</script>            
			</div>
			<div id="chart_01" class="grid_01">
				<div style="float:left;" id="chart_mtch"></div>
			</div>
		  </div>
	
	 </div>
	</body>
</html>