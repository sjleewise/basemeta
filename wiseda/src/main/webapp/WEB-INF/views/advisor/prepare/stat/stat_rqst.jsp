<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>


<script type="text/javascript">
	$(document).ready(function() {
		//버튼 이벤트 정의
		$("#btnStatSearch").click(function(e) {
            e.preventDefault();
			doAction("Search");
		});
		
		$("#frmSearch #schDbConnTrgId").change(function() {
			$("#frmSearch #schDbSchNm").find("option").remove().end();
			var val = $("#schDbConnTrgId option:selected").text();
			var trgId = ${codeMap.schDbSchNm};

			$("#frmSearch #schDbSchNm").append('<option value=""></option>');
			
			for(i=0; i<trgId.length; i++) {
				if(trgId[i].upcodeCd == val) {
					$("#frmSearch #schDbSchNm").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
				}
			}
		});
	});
	
	$(window).on('load',function() {
		//그리드 초기화
		initGrid();
		//$(window).resize();
	});
	
	function initGrid() {
		//그리드 초기 설정
		//진단대상 테이블 grid
		with(grid_prof){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.RQST'/>", Align:"Center"} ];
			//테이블명|테이블한글명|컬럼명|컬럼한글명|프로파일ID|프로파일명|분석종류|분석차수|분석총건수|추정오류건수|추정오류율
	        	
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
	                    {Type:"Text",   Width:150,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, },
	                    {Type:"Text",   Width:150,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0},
	                    {Type:"Text",   Width:150,  SaveName:"dbcColNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:150,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:100,  SaveName:"prfId",    	Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",   Width:200,  SaveName:"prfNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:100,  SaveName:"prfKndNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:80,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"esnErCnt"  , Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"###\\%",  SaveName:"erRate"  , Align:"Center", Edit:0, Hidden:0},
	                ];
	        
	        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	        InitColumns(cols);
	        
	        FitColWidth();
	        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	        SetExtendLastCol(1);
	        SetMergeSheet(msPrevColumnMerge);
	    }
	    //==시트설정 후 아래에 와야함===
		init_sheet(grid_prof);    
	    //===========================
	    	
		with(grid_br){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.RQST2'/>", Align:"Center"} ];
			//테이블명|테이블한글명|컬럼명|컬럼한글명|업무규칙ID|업무규칙명|분석차수|분석총건수|추정오류건수|추정오류율
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
	                    {Type:"Text",   Width:200,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbcColNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:100,  SaveName:"brId",    	Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",   Width:200,  SaveName:"brNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:80,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"erCnt"  , Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"###\\%",  SaveName:"erRate"  , Align:"Center", Edit:0, Hidden:0},
	                ];
	        
	        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	        InitColumns(cols);
	        
	        FitColWidth();
	        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	        SetExtendLastCol(1);
	        SetMergeSheet(msPrevColumnMerge);
	    }
	    //==시트설정 후 아래에 와야함===
		init_sheet(grid_br);    
	    //===========================
	    	
		with(grid_otl){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.RQST3'/>", Align:"Center"} ];
			//테이블명|테이블한글명|컬럼명|컬럼한글명|이상값탐지ID|이상값탐지명|분석차수|분석총건수|추정오류건수|추정오류율
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
	                    {Type:"Text",   Width:200,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbColNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:100,  SaveName:"otlDtcId",    	Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",   Width:200,  SaveName:"otlNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:80,  SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"esnErCnt"  , Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"###\\%",  SaveName:"erRate"  , Align:"Center", Edit:0, Hidden:0},
	                ];
	        
	        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	        InitColumns(cols);
	        
	        FitColWidth();
	        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	        SetExtendLastCol(1);
	        SetMergeSheet(msPrevColumnMerge);
	    }
	    //==시트설정 후 아래에 와야함===
		init_sheet(grid_otl);    
	    //===========================
	    	
		with(grid_cluster){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.RQST6'/>", Align:"Center"} ];
			//mtcId|테이블명|테이블한글명|컬럼명|컬럼한글명|분석총건수|클러스터링건수
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
						{Type:"Text",   Width:200,  SaveName:"clstId",    	Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",   Width:200,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbColNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:200,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"clstCnt"  , Align:"Center", Edit:0, Hidden:0},
	                ];
	        
	        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	        InitColumns(cols);
	        
	        FitColWidth();
	        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	        SetExtendLastCol(1);
	        SetMergeSheet(msPrevColumnMerge);
	    }
	    //==시트설정 후 아래에 와야함===
		init_sheet(grid_cluster);    
	    //===========================
	    	
		with(grid_match){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headers = [ {Text:"<s:message code='BDQ.HEADER.STAT.RQST5'/>", Align:"Center"} ];
			//mtcId|소스테이블명|소스테이블한글명|소스컬럼명|소스컬럼한글명|타겟테이블명|타겟테이블한글명|타겟컬럼명|타겟컬럼한글명|분석총건수
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 

	        var cols = [
						{Type:"Text",   Width:200,  SaveName:"mtcId",    	Align:"Left", Edit:0, Hidden:1},
	                    {Type:"Text",   Width:200,  SaveName:"srcDbcTblNm",    	Align:"Left", Edit:0, ColMerge:1},
	                    {Type:"Text",   Width:200,  SaveName:"srcDbcTblKorNm",    	Align:"Left", Edit:0, ColMerge:1},
	                    {Type:"Text",   Width:200,  SaveName:"srcDbcColNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:200,  SaveName:"srcDbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:200,  SaveName:"tgtDbcTblNm",    	Align:"Left", Edit:0, ColMerge:1},
	                    {Type:"Text",   Width:200,  SaveName:"tgtDbcTblKorNm",    	Align:"Left", Edit:0, ColMerge:1},
	                    {Type:"Text",   Width:200,  SaveName:"tgtDbcColNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Text",   Width:200,  SaveName:"tgtDbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
	                    {Type:"Float",   Width:80, Format:"#,###",  SaveName:"anaCnt",    	Align:"Center", Edit:0, Hidden:0}
	                ];
	        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	        InitColumns(cols);
	        
	        FitColWidth();
	        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	        SetExtendLastCol(1);
	        SetMergeSheet(1);
	    }
	    //==시트설정 후 아래에 와야함===
		init_sheet(grid_match);    
	    //===========================
	    	
	}
	
	function doAction(sAction) {
		switch(sAction) {
		case "Search":
			var param = '';
			param = $("#frmSearch").serialize();
			
			//console.log($("#tabs").tabs().tabs('selected'));
			//console.log($("#tabs").tabs('option', 'selected'));
			
			switch($("#tabs").tabs('option','selected')) {
			case 0:
				grid_prof.DoSearch("<c:url value="/advisor/prepare/stat/getProfStatList.do" />", param);
				break;
			case 1:
				grid_br.DoSearch("<c:url value="/advisor/prepare/stat/getBrStatList.do" />", param);
				break;
			case 2:
				grid_otl.DoSearch("<c:url value="/advisor/prepare/stat/getOtlStatList.do" />", param);
				break;
			case 3:
				grid_cluster.DoSearch("<c:url value="/advisor/prepare/stat/getClstStatList.do" />", param);
				break;
			case 4:
				grid_match.DoSearch("<c:url value="/advisor/prepare/stat/getMtchStatList.do" />", param);
				break;
			}
			
			break;
		}
	}
	
	function grid_prof_OnDblClick(row, col, value, cellx, celly) {
       	var rowjson =  grid_prof.GetRowJson(row);

		var param = "?" + $("#frmSearch").serialize();
    	    param += "&prfId=" + rowjson.prfId;
    	    param += "&prfNm=" + encodeURIComponent(rowjson.prfNm);
    	    param += "&prfKndNm="   + encodeURIComponent(rowjson.prfKndNm);  
    	    param += "&anaDgr="  + rowjson.anaDgr;
		
	    var url = '<c:url value="/advisor/prepare/stat/popup/prof_pop.do" />';

	 	var vPop = OpenWindow(url+param, "", 800, 600);
	}
	function grid_br_OnDblClick(row, col, value, cellx, celly) {
		
		var rowjson =  grid_br.GetRowJson(row);

		var param = "?" + $("#frmSearch").serialize();
    	    param += "&brId=" + rowjson.brId;
    	    param += "&brNm=" + encodeURIComponent(rowjson.brNm);  
    	    param += "&anaDgr="  + rowjson.anaDgr;
		
    	    var url = "<c:url value='/advisor/prepare/stat/popup/br_pop.do' />";

	 	var vPop = OpenWindow(url+param, "", 800, 600);
	}
	function grid_otl_OnDblClick(row, col, value, cellx, celly) {
		
		var rowjson =  grid_otl.GetRowJson(row);

		var param = "?" + $("#frmSearch").serialize();
    	    param += "&otlDtcId=" + rowjson.otlDtcId;
    	    param += "&otlNm=" + encodeURIComponent(rowjson.otlNm);
    	    param += "&anaDgr="  + rowjson.anaDgr;
		
    	var url = "<c:url value='/advisor/prepare/stat/popup/otl_pop.do' />";

	 	var vPop = OpenWindow(url+param, "", 800, 600);
	}
	
	function grid_cluster_OnDblClick(row, col, value, cellx, celly) {
		
		var rowjson =  grid_cluster.GetRowJson(row);
	
		var param = "?" + $("#frmSearch").serialize();
		    param += "&clstId=" + rowjson.clstId;
		
		var url = "<c:url value='/advisor/prepare/stat/popup/clst_pop.do' />";
	
	 	var vPop = OpenWindow(url+param, "", 800, 600);
	}
	
	function grid_match_OnDblClick(row, col, value, cellx, celly) {
		
		var rowjson =  grid_match.GetRowJson(row);
	
		var param = "?" + $("#frmSearch").serialize();
		    param += "&mtcId=" + rowjson.mtcId;
		
		var url = "<c:url value='/advisor/prepare/stat/popup/mtch_pop.do' />";
	
	 	var vPop = OpenWindow(url+param, "", 800, 600);
	}
</script>


<div style="clear:both; height:10px;"><span></span></div>

<div id="searchTrg_div" >
       
       <form id="frmSearch" name="frmSearch" method="post">
           <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2" >
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.PROF.MNG'/>">
                   <caption><s:message code="CLMN.PROF.MNG"/></caption><!--컬럼프로파일관리-->

                   <colgroup>
	                   <col style="width:10%;" />
	                   <col style="width:25%;" />
	                   <col style="width:5%;" />
	                   <col style="width:15%;" />
	                   <col style="width:5%;" />
	                   <col style="width:15%;" />
	                   <col style="width:5%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class="th_require"><label for="schDbConnTrgId"><s:message code="DBMS.SCHEMA.NM" /></label></th><!--진단대상명-->

                           <td>
                           		<select id="schDbConnTrgId"  name="schDbConnTrgId" class="wd50p">
	                           		<option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    	<option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
                           		</select>
                           
                           		<select id="schDbSchNm" name="schDbSchNm" class="wd48p">
                           		</select>
                               <!-- <input type="text" name="schDbSchNm" id="schDbSchNm" class="wd98p"/> -->
                           </td>
                           
                           <th scope="row"><label for="schDbcTblNm"><s:message code="TBL.NM" /></label></th>	<!--테이블명-->

                       		<td>
                       			<%-- <select id="schDbcTblNm" name="schDbcTblNm" class="wd98p">
                           		</select> --%>
                       			<input type="text" name="schDbcTblNm" id="schDbcTblNm" class="wd98p"/>
                       		</td>
                       		
                       		<th scope="row"><label for="schDbcColNm"><s:message code="CLMN.NM" /></label></th>	<!--컬럼명-->

                       		<td>
                       			<input type="text" name="schDbcColNm" id="schDbcColNm" class="wd98p"/>
                       		</td>
                       		
                       		<td>
							    <button class="btn_search" id="btnStatSearch" name="btnStatSearch"><s:message code='INQ' /></button> <!-- 조회 -->
							</td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
           </fieldset>
       </form>
</div>

<div style="clear:both; height:15px;"><span></span></div>

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
		     <script type="text/javascript">createIBSheet("grid_prof", "99%", "250px");</script>            
		</div>
	  </div>
	  <div id="tabs-pc02">
	  	<div class="stit"><s:message code="BR.STAT" /></div> <!-- br -->
		<div style="clear:both; height:5px;"><span></span></div>	       
		<div id="grid_02" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_br", "99%", "250px");</script>            
		</div>
	  </div>
	  <div id="tabs-pc03">
	  	<div class="stit"><s:message code="OTL.STAT" /></div> <!-- 이상값탐지 -->
		<div style="clear:both; height:5px;"><span></span></div>	       
		<div id="grid_03" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_otl", "99%", "250px");</script>            
		</div>
	  </div>
	  <div id="tabs-pc04">
	  	<div class="stit"><s:message code="CLST.STAT" /></div> <!-- 클러스터링 -->
		<div style="clear:both; height:5px;"><span></span></div>	       
		<div id="grid_04" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_cluster", "99%", "250px");</script>            
		</div>
	  </div>
	  <div id="tabs-pc05">
	  	<div class="stit"><s:message code="MTCH.STAT" /></div> <!-- 매칭 -->
		<div style="clear:both; height:5px;"><span></span></div>	       
		<div id="grid_05" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_match", "99%", "250px");</script>            
		</div>
	  </div>
 </div>
