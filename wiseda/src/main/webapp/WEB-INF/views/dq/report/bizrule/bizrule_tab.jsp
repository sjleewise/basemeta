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
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script> --%>


<script type="text/javascript">
$(document).ready(function() {

 	
	//탭 초기화
 	//$( "#tabs" ).tabs();
	
	$("#btnTreeNew").hide();
	
	$("#btnDelete").hide();
	

	
	//조회
	$("#btnSearch").click(function(){
		
		if($("#dbConnTrgId").val() == ""){
			
			showMsgBox("ERR", "DBMS를 입력하세요.");
			return;
		}
		
		var tidx = $("#tabs .ui-tabs-panel:visible").attr("id");
		switch (tidx) {
		case 'tabs-1':
			var url = '<c:url value="/dq/report/bizrule/getBizruleProgQuality.do" />';
			var param = $('#frmSearch').serialize();
			
// 			console.log(param);
			
			grid_sheet1.DoSearch(url, param);
			break;
		case 'tabs-2':
			var url = '<c:url value="/dq/report/bizrule/getBizareaProgQuality.do" />';
			var param = $('#frmSearch').serialize();
			
// 			console.log(param);
			
			grid_sheet2.DoSearch(url, param);
			break;
		case 'tabs-3':
			var url = '<c:url value="/dq/report/bizrule/getDqiProgQuality.do" />';
			var param = $('#frmSearch').serialize();
			
// 			console.log(param);
			
			grid_sheet3.DoSearch(url, param);
			break;
		case 'tabs-4':
			var url = '<c:url value="/dq/report/bizrule/getCtqProgQuality.do" />';
			var param = $('#frmSearch').serialize();
			
// 			console.log(param);
			
			grid_sheet4.DoSearch(url, param);
			break;
		case 'tabs-5':
			var url = '<c:url value="/dq/report/bizrule/getBizruleQuality.do" />';
			var param = $('#frmSearch').serialize();
			
// 			console.log(param);
			
			grid_sheet5.DoSearch(url, param);
			break;
		} 
		
		}).show();
	
	//엑셀다운로드
	$("#btnExcelDown").click(function(){	
	
		var tidx = $("#tabs .ui-tabs-panel:visible").attr("id");
		switch (tidx) {
		case 'tabs-1':
			grid_sheet1.Down2Excel({HiddenColumn:1, Merge:1, FileName:'업무규칙품질추이'});
			break;
		case 'tabs-2':
			grid_sheet2.Down2Excel({HiddenColumn:1, Merge:1, FileName:'업무영역별품질현황'});
			break;
		case 'tabs-3':
			 grid_sheet3.Down2Excel({HiddenColumn:1, Merge:1, FileName:'데이터품질지표별품질현황'});
			break;
		case 'tabs-4':
			grid_sheet4.Down2Excel({HiddenColumn:1, Merge:1, FileName:'중요정보항목별품질현황'});
			break;
		case 'tabs-5':
			grid_sheet5.Down2Excel({HiddenColumn:1, Merge:1, FileName:'진단대상품질현황'});
			break;
		} 
		
		
		
	
	});
	
	//업무영역명 검색 팝업
	$('#btnBizAreaLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/bizarea/popup/bizarea_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=BIZLNM", "bizAreaPop",  800, 600, "no");
				popwin.focus();
	});
	
	//품질지표명 검색 팝업
	$('#btnDqiLnmPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/dqi/popup/dqi_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=DQILNM", "ctqLstPop",  800, 600, "no");
				popwin.focus();
	});
	
	//중요정보항목명 검색 팝업
	$('#btnCtqLnmPop').click(function(event){
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

/* ibchart 설정  */
$(window).on('load',function(){
});

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
	                    <th scope="row" class = "th_require"><label for="dbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->

                        	<td colspan="3">
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
                        	<button class="btnSearchPop" id="btnBizAreaLnmPop"><s:message code="INQ" /></button><!--검색-->
                            </td>
                        <th scope="row"><label for="dqiLnm"><s:message code="QLTY.INDC.NM"/></label></th><!--품질지표명-->
	                    	<td>
	                    	<input class="wd60p" type="text" id="dqiLnm" name="dqiLnm" />
	                    	<button class="btnSearchPop" id="btnDqiLnmPop"><s:message code="INQ" /></button><!--검색-->
	                        </td> 
                        <th scope="row"><label for="ctqLnm"><s:message code="IMCE.INFO.ITEM.NM"/></label></th><!--중요정보항목명-->
</th>
	                    	<td colspan="3">
	                    	<input class="wd80p" type="text" id="ctqLnm" name="ctqLnm" />
	                    	<button class="btnSearchPop" id="btnCtqLnmPop"><s:message code="INQ" /></button><!--검색-->
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
	<div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div><!--를 사용하시면 됩니다.--><!--클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고-->

</form>

<!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<div style="clear:both; height:5px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

</div>

<div id="layer_div" >
<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabs">
	<ul>
	<li><a href="#tabs-1">업무규칙 품질추이</a></li> 
	<li><a href="#tabs-2">업무영역별 품질현황</a></li> 
	<li><a href="#tabs-3">데이터품질지표별 품질현황</a></li>
	<li><a href="#tabs-4">중요정보항목별 품질현황</a></li> 
	<li><a href="#tabs-5">진단대상별 품질현황</a></li> 
	</ul>


	<div id="tabs-1"> 
		<%@include file="bizruleprog_ifm.jsp" %> 
	</div>
	<div id="tabs-2"> 
		<%@include file="bizareaprog_ifm.jsp" %> 
	</div>
	<div id="tabs-3"> 
		<%@include file="dqiprog_ifm.jsp" %> 
	</div>
	<div id="tabs-4"> 
		<%@include file="ctqprog_ifm.jsp" %> 
	</div>
	<div id="tabs-5"> 
		<%@include file="bizrulequality_ifm.jsp" %> 
	</div>

</div>
</div>

</body>
</html>