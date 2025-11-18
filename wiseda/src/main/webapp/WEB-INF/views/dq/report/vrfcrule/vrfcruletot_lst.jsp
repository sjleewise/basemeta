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
<title><s:message code="PROF.QLTY.TRANSITION"/></title><!--프로파일 품질추이-->

<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script> --%>
<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch} ;
var selected = true;
$(document).ready(function() {
	
	$("#prog").click(function () { 
	
		if ($("#hidden1").css("display") == "none") { 
			$("#hidden1").css("display", "");
			$("#hidden2").css("display", "");
			$("#colspan").attr("colspan",0);
			//$("#hidden3").css("display", "");
			//$("#hidden4").css("display", "");
			selected = true;
		} 
		//alert(selected);
	}); 
	$("#result").click(function () { 
		
		if ($("#hidden1").css("display") != "none") { 
			$("#hidden1").css("display", "none");
			$("#hidden2").css("display", "none");
			$("#colspan").attr("colspan",3);
			//$("#hidden3").css("display", "none");
			//$("#hidden4").css("display", "none"); 
			selected = false;
		}
		//alert(selected);
	}); 
	
	

	$("#btnExcelDown").click(function(){	
		var tidx = $("#tabs .ui-tabs-panel:visible").attr("id");
		switch (tidx) {
		case 'tabs-1':
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'검증룰품질추이'});
			break;
		case 'tabs-2':
			grid_sheet2.Down2Excel({HiddenColumn:1, Merge:1, FileName:'검증룰결과현황'});
			break;
		
		} 

	});
	
	
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
	
	
	//업무영역명 검색 팝업
	
	//======================================================
	 // 셀렉트 박스 초기화
	//======================================================
	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
		double_select(connTrgSchJson, $(this));
	});
	
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #dbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #dbcColNm"), "DBCCOL");
	
	
});

EnterkeyProcess("Search");





function doAction(sAction)
{
        
    switch(sAction)
    {
	    case "Search":
	    	
			if($("#dbConnTrgId").val() == ""){
				
				showMsgBox("ERR", "DBMS/스키마정보를 입력하세요.");
				return;
				}
			if($("#dbSchId").val() == ""){
				
				showMsgBox("ERR", "스키마정보를 입력하세요.");
				return;
				}
	    	
			
			var tidx = $("#tabs .ui-tabs-panel:visible").attr("id");
			switch (tidx) {
			case 'tabs-1':
				var url = '<c:url value="/dq/report/vrfcrule/getVrfcruleProgQuality.do" />';
				var param = $('#frmSearch').serialize();
				grid_sheet.DoSearch(url, param);
				chartDraw();
				break;
			case 'tabs-2':
				var url = '<c:url value="/dq/vrfcrule/selectVrfcErrList.do" />';
				var param = $('#frmSearch').serialize();
				grid_sheet2.DoSearch(url, param);
				break;
			}
			
			break;
  	
    }
}








$(window).on('load',function(){
});




</script>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PROF.QLTY.TRANSITION"/></div><!--프로파일 품질추이-->

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
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                  </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class="th_require"><label for="dbSchId"><s:message code="DBMS.SCHEMA.NM"/></label></th><!--진단대상명/스키마명-->

                      <td>
			            <select id="dbConnTrgId" class="" name="dbConnTrgId">
			            <option value=""><s:message code="WHL" /></option><!--전체-->

			            </select>
			            <select id="dbSchId" class="" name="dbSchId">
			             <option value=""><s:message code="WHL" /></option><!--전체-->

			             </select>
			           </td>
                           
                              <th scope="row"><label for="vrfcTyp">검증분류</label></th>
                         		  <td id="colspan">
                           		<select id="vrfcTyp" name="vrfcTyp" >
                           		   <option value="">전체</option> 
                         		   <c:forEach var="code" items="${codeMap.vrfcTyp}" varStatus="status" >
                                   <option value="${code.codeCd}">${code.codeLnm}</option>
                                   </c:forEach>
                           		</select>
                           </td>
	                       <th id="hidden1" style="display:'';" scope="row"><label for="anaDgr"><s:message code="ANLY.HIST" /></label></th><!--분석차수-->

                           <td id="hidden2" style="display:'';" scope="row">
                              <select id="anaDgr"  name="anaDgr">
								    <option value=""><s:message code="WHL" /></option><!--전체-->
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



<div style="clear:both; height:20px;"><span></span></div>



</div>

<div id="layer_div" >

<div style="clear:both; height:5px;"><span></span></div>
 
 <div id="tabs">
	<ul>
		<li><a href="#tabs-1" id="prog">품질추이</a></li> <!-- 표준용어 상세정보 -->
		<li><a href="#tabs-2" id="result">결과현황</a></li> <!-- 표준용어 상세정보 -->
	</ul>

	<div id="tabs-1" > 
		<%@include file="vrfcruleprog_tab.jsp" %> 
	</div>
	<div id="tabs-2" > 
		<%@include file="vrfcrule_err_tab.jsp" %> 
	</div>

 </div>
 
</div>
<%-- IBChartLite Init Scripts --%>

</body>

</html>