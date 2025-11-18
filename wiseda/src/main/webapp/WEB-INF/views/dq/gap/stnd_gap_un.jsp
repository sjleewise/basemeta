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
		
		//// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
    	
    	$( "#tab-DBSDITM a" ).click(function(){
    		grid_name = dbsditm_sheet;
    		bizDcd ="DBSDITM";
    		grid_name.FitColWidth();
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	$( "#tab-DBDMN a" ).click(function(){
    		grid_name = dbdmn_sheet;
    		bizDcd ="DBDMN";
    		grid_name.FitColWidth();
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	
    	$( "#tab-SDITM a" ).click(function(){
    		grid_name = sditm_sheet;
    		bizDcd ="SDITM";
    		grid_name.FitColWidth();
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	$( "#tab-DMN a" ).click(function(){
    		grid_name = dmn_sheet;
    		bizDcd ="DMN";
    		grid_name.FitColWidth();
//     	    setTimeout(function(){doAction("Search")},500);
    	});
    	
 
    	$("#btnSearch").text("진단");
    	$("#btnSave").click(function(){doAction("Save");}).show();
    	$("#btnTreeNew").hide();
    	$("#btnDelete").hide();
});

$(window).load(function() {
	grid_name = dbsditm_sheet;
	bizDcd ="DBSDITM";
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
        
        	 var url = "";
             if(bizDcd=="DBSDITM"){
            	 url = "<c:url value="/dq/gap/getDBStndItemGapUneqList.do" />"
             }else if(bizDcd=="DBDMN"){
            	 url = "<c:url value="/dq/gap/getDBDmnGapUneqList.do" />"
             }else if(bizDcd=="SDITM"){
            	 url = "<c:url value="/dq/gap/getStndItemGapUneqList.do" />"
             }else if(bizDcd=="DMN"){
            	 url = "<c:url value="/dq/gap/getDmnGapUneqList.do" />"
             }
        	
        	grid_name.DoSearch(url, param);
        	
        	break;
        	
    case "Save":	//요청서 재조회...
        
    	var param = "";
    	ibsSaveJson = null;
    	 var url = "";
         if(bizDcd=="DBSDITM"){
        	 if($("#frmInputDBSditm #anaDtm").val()==''){
        		 showMsgBox("INF","진단을 먼저 수행하여 주십시오.");
        		 return;
        	 }
        	 param = $("#frmInputDBSditm").serialize();
         }else if(bizDcd=="DBDMN"){
        	 if($("#frmInputDBDmn #anaDtm").val()==''){
        		 showMsgBox("INF","진단을 먼저 수행하여 주십시오.");
        		 return;
        	 }
        	 param = $("#frmInputDBDmn").serialize();
         }else if(bizDcd=="SDITM"){
        	 if($("#frmInputSditm #anaDtm").val()==''){
        		 showMsgBox("INF","진단을 먼저 수행하여 주십시오.");
        		 return;
        	 }
        	 param = $("#frmInputSditm").serialize();
         }else if(bizDcd=="DMN"){
        	 if($("#frmInputDmn #anaDtm").val()==''){
        		 showMsgBox("INF","진단을 먼저 수행하여 주십시오.");
        		 return;
        	 }
        	 param = $("#frmInputDmn").serialize();
         }
         url = "<c:url value="/dq/gap/saveGapResult.do" />"

        IBSpostJson2(url, ibsSaveJson, param, ibscallback);
    	
    	break;	
        	
      
        case "Down2Excel":  //엑셀내려받기
            var fileName = "";
            if(bizDcd=="DBSDITM"){
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

<div id="search_div">
<!-- 검색조건 입력폼 -->
<!--         <div class="stit">검색조건</div> 검색조건 -->
        
        <div style="clear:both; height:5px;"><span></span></div>
        
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" name="pdmTblId" />
        	<input type="hidden" name="subjId" />
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
<!--                 <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="비표준컬럼조회"> -->
<%--                    <caption></caption> --%>
<%--                    <colgroup> --%>
<%--                    <col style="width:15%;" /> --%>
<%--                    <col style="width:35%;" /> --%>
<%--                    <col style="width:15%;" /> --%>
<%--                    <col style="width:35%;" /> --%>
<%--                    </colgroup> --%>
                   
<!--                    <tbody>                             -->
<!--                        <tr>                                                          -->
                           
<!--                        </tr> -->
                      
<!--                    </tbody> -->
<!--                  </table>    -->
            </div>
            </fieldset>
        </form>
            
<%--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div> --%>
<%-- 		<div style="clear:both; height:10px;"><span></span></div> --%>
</div>
<!-- 조회버튼영역  -->
<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:10px;"><span></span></div>
<div id="tabs">
	  <ul>
	    <li id="tab-DBSDITM"><a href="#tabs-DBSDITM">DB<s:message code="STRD.TERMS"/></a></li><!--DB표준용어-->
	    <li id="tab-DBDMN"><a href="#tabs-DBDMN">DB<s:message code="DMN"/></a></li><!-- DB도메인-->
	    <li id="tab-SDITM"><a href="#tabs-SDITM">기관<s:message code="STRD.TERMS"/></a></li><!--기관표준용어-->
	    <li id="tab-DMN"><a href="#tabs-DMN">기관<s:message code="DMN"/></a></li><!--기관도메인-->
	  </ul>
	  <div id="tabs-DBSDITM">
			<div id="detailInfoDBSDITM"><%@include file="exl/dbstnditemgap_exl_un.jsp" %></div>
	  </div>
	  <div id="tabs-DBDMN">
			<div id="detailInfoDBDMN"><%@include file="exl/dbstnddmngap_exl_un.jsp" %></div>
	  </div>
	  <div id="tabs-SDITM">
			<div id="detailInfoSDITM"><%@include file="exl/stnditemgap_exl_un.jsp" %></div>
	  </div>
	   <div id="tabs-DMN">
			<div id="detailInfoDMN"><%@include file="exl/stnddmngap_exl_un.jsp" %></div>
	  </div>	 	  
</div>
	 
</body>
</html>