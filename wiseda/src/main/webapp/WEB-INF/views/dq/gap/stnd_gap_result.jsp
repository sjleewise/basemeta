<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title></title>

<script type="text/javascript">

EnterkeyProcess("Search");

var grid_name ;
var bizDcd ;

// var connTrgSchJson = ${codeMap.connTrgSch} ;                 
$(document).ready(function() {
	
		$( "#tabs" ).tabs();
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
    	
    	$( "#tab-COLDEFGAP a" ).click(function(){
    		grid_name = coldefgap_sheet;
    		bizDcd ="DBSDITM";
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	$( "#tab-MTACOLGAP a" ).click(function(){
    		grid_name = mtacolgap_sheet;
    		bizDcd ="MTACOLGAP";
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	
    	$( "#tab-DBSDITM a" ).click(function(){
    		grid_name = dbsditm_sheet;
    		bizDcd ="DBSDITM";
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	$( "#tab-DBDMN a" ).click(function(){
    		grid_name = dbdmn_sheet;
    		bizDcd ="DBDMN";
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	
    	$( "#tab-SDITM a" ).click(function(){
    		grid_name = sditm_sheet;
    		bizDcd ="SDITM";
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	$( "#tab-DMN a" ).click(function(){
    		grid_name = dmn_sheet;
    		bizDcd ="DMN";
//     	    setTimeout(function(){doAction("Search")},500);
    	});
});

$(window).load(function() {
	grid_name = coldefgap_sheet;
	bizDcd ="COLDEFGAP";
});


$(window).resize(
    
    function(){
                
    	//setibsheight($("#grid_01"));
    }
);

//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":	//요청서 재조회...
       
        	var param = $('#frmSearch').serialize();
        
        	var url = "<c:url value="/dq/gap/getStndGapResultLst.do" />"
            if(bizDcd=="COLDEFGAP"){
            	 param += "&anaCd=10";
             }else if(bizDcd=="MTACOLGAP"){
            	 param += "&anaCd=11";
             }else if(bizDcd=="DBSDITM"){
            	 param += "&anaCd=05";
             }else if(bizDcd=="DBDMN"){
            	 param += "&anaCd=06";
             }else if(bizDcd=="SDITM"){
            	 param += "&anaCd=07";
             }else if(bizDcd=="DMN"){
            	 param += "&anaCd=08";
             }
        	
        	grid_name.DoSearch(url, param);
        	
        	break;
        	
      
        case "Down2Excel":  //엑셀내려받기
            var fileName = "";
            if(bizDcd=="COLDEFGAP"){
            	fileName = '컬럼정의 불일치 진단결과.xlsx';
            }else if(bizDcd=="MTACOLGAP"){
            	fileName = '범정부 공통표준용어 사용 진단결과.xlsx';
            }else if(bizDcd=="DBSDITM"){
            	fileName = 'DB표준용어진단.xlsx';
            }else if(bizDcd=="DBDMN"){
            	fileName = 'DB표준용어진단.xlsx';
            }else if(bizDcd=="SDITM"){
            	fileName = '기관표준용어진단.xlsx';
            }else if(bizDcd=="DMN"){
            	fileName = '기관표준용어진단.xlsx';
            }
        	grid_name.Down2Excel({HiddenColumn:1, Merge:1, FileName: fileName});
            break;
     
    }       
}


</script>
</head>

<body>

<div id="layer_div" > 
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"></div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

		<div style="clear:both; height:10px;"><span></span></div>
</div>
<!-- 조회버튼영역  -->
<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
<div style="clear:both; height:10px;"><span></span></div>
<div id="tabs">
	  <ul>
	    <li id="tab-COLDEFGAP"><a href="#tabs-COLDEFGAP">컬럼정의 불일치 진단결과</a></li>
	    <li id="tab-MTACOLGAP"><a href="#tabs-MTACOLGAP">범정부 공통표준용어 사용 진단결과</a></li>
	    <li id="tab-DBSDITM"><a href="#tabs-DBSDITM">DB<s:message code="STRD.TERMS"/></a></li><!--DB표준용어-->
	    <li id="tab-DBDMN"><a href="#tabs-DBDMN">DB<s:message code="DMN"/></a></li><!-- DB도메인-->
	    <li id="tab-SDITM"><a href="#tabs-SDITM">기관<s:message code="STRD.TERMS"/></a></li><!--기관표준용어-->
	    <li id="tab-DMN"><a href="#tabs-DMN">기관<s:message code="DMN"/></a></li><!--기관도메인-->
	  </ul>
	  <div id="tabs-COLDEFGAP">
			<div id="detailInfoCOLDEFGAP"><%@include file="exl/coldefgap_exl.jsp" %></div>
	  </div>
	  <div id="tabs-MTACOLGAP">
			<div id="detailInfoMTACOLGAP"><%@include file="exl/mtacolgap_exl.jsp" %></div>
	  </div>
	  <div id="tabs-DBSDITM">
			<div id="detailInfoDBSDITM"><%@include file="exl/dbstnditemgap_exl2.jsp" %></div>
	  </div>
	  <div id="tabs-DBDMN">
			<div id="detailInfoDBDMN"><%@include file="exl/dbstnddmngap_exl2.jsp" %></div>
	  </div>
	  <div id="tabs-SDITM">
			<div id="detailInfoSDITM"><%@include file="exl/stnditemgap_exl2.jsp" %></div>
	  </div>
	   <div id="tabs-DMN">
			<div id="detailInfoDMN"><%@include file="exl/stnddmngap_exl2.jsp" %></div>
	  </div>
</div>
	 
</body>
</html>