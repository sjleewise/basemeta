<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="PEDC.LORQ.TRTT.PRES"/></title> <!-- 기간별 요청서 처리현황 -->

<!-- ibchart.js  -->
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>

<script type="text/javascript">



$(document).ready(function() {
	 
		//initGrid();

		//마우스 오버 이미지 초기화
// 		//imgConvert($('div.tab_navi a img'));
      	
//      $("#btnSearch").hide();
        $("#btnTreeNew").hide();
        $("#btnSave").hide();
        $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
//         create_selectbox(bizdtlcdJson, $("#bizDtlCd"));
        $( "#tabsStwd" ).hide();
        $( "#tabsDmn" ).hide();
        $( "#tabsSditm" ).hide();
	
        
      //달력팝업 추가...
	 	$( "#searchBgnDe" ).datepicker();
		$( "#searchEndDe" ).datepicker();
		
		//탭 초기화....
		//$( "#tabs" ).tabs();
    
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
//		setautoComplete($("#frmSearch #dmnLnm"), "DMN");
		
	
});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	//$( "#layer_div" ).show();
});





function doAction(sAction)
{
	switch(sAction)
	{
	case "Search":
		var param = $('#frmSearch').serialize();

		grid_sheet01.DoSearch("<c:url value="/meta/report/getDdlRqstPeriod.do" />", param);
		grid_sheet02.DoSearch("<c:url value="/meta/report/getDdlRqstBiz.do" />", param);
		grid_sheet03.DoSearch("<c:url value="/meta/report/getDdlRqstDept.do" />", param);
		grid_sheet04.DoSearch("<c:url value="/meta/report/getDdlRqstDb.do" />", param);
	
		
		//각각의 탭을 클릭하며 이동시
		$('#tab-PT01').click(function () {  
		    if($("#div_chart_01").css("display") == "none"){
		    	$('#div_chart_01').css("display", "block");
				$('#div_chart_02').css("display", "none");
				$('#div_chart_03').css("display", "none");
				$('#div_chart_04').css("display", "none");
		        chartDrow();
		    }  
		});
		$('#tab-PT02').click(function () {  
		    if($("#div_chart_02").css("display") == "none"){
		    	$('#div_chart_01').css("display", "none");
				$('#div_chart_02').css("display", "block");
				$('#div_chart_03').css("display", "none");
				$('#div_chart_04').css("display", "none");
		        chartDrow2();
		    }  
		});
		$('#tab-PT03').click(function () {  
		    if($("#div_chart_03").css("display") == "none"){
		    	$('#div_chart_01').css("display", "none");
				$('#div_chart_02').css("display", "none");
				$('#div_chart_03').css("display", "block");
				$('#div_chart_04').css("display", "none");
		        chartDrow3();
		    }  
		});
		$('#tab-PT04').click(function () {  
		    if($("#div_chart_04").css("display") == "none"){
		    	$('#div_chart_01').css("display", "none");
				$('#div_chart_02').css("display", "none");
				$('#div_chart_03').css("display", "none");
				$('#div_chart_04').css("display", "block");
		        chartDrow4();
		    }  
		}); 
		
		break;
	case "Down2Excel": //엑셀내려받기
		//여러개의 시트를 1개의 엑셀문서로 다운받는다.
		grid_sheet01.Down2ExcelBuffer(true);  
	
		if(grid_sheet01.SearchRows()>0){
			grid_sheet01.Down2Excel({FileName:'ddlRqst_list',SheetName:'<s:message code="MTHY.DDL.LORQ.TRTT.PRES"/>', HiddenColumn:1, Merge:1});  //월별 DDL요청서 처리현황
    	}
    	if(grid_sheet02.SearchRows()>0){
    		grid_sheet02.Down2Excel({FileName:'ddlRqst_list',SheetName:'<s:message code="BZWR.DDL.LORQ.TRTT.PRES"/>', HiddenColumn:1, Merge:1}); //업무별 DDL요청서 처리현황
    	}
    	if(grid_sheet03.SearchRows()>0){
    		grid_sheet03.Down2Excel({FileName:'ddlRqst_list',SheetName:'<s:message code="DEPT.DDL.LORQ.TRTT.PRES"/>', HiddenColumn:1, Merge:1}); //부서별 DDL요청서 처리현황
    	}
    	if(grid_sheet04.SearchRows()>0){
    		grid_sheet04.Down2Excel({FileName:'ddlRqst_list',SheetName:'<s:message code="DB.DDL.LORQ.TRTT.PRES"/>', HiddenColumn:1, Merge:1}); //DB별 DDL요청서 처리현황
    	}
    	
    	grid_sheet01.Down2ExcelBuffer(false);
    	
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
	    <div class="menu_title"><s:message code="DBA.DDL.LORQ.TRTT.PRES"/></div> <!-- DBA DDL요청서 처리현황 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DBA.DDL.LORQ.TRTT.PRES'/>"> <!-- DBA DDL요청서 처리현황 -->
                   <caption><s:message code="LORQ.INQ.FORM"/></caption> <!-- 요청서 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:85%;" />
              <%--      <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" /> --%>
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <%-- <th scope="row"><label for="t]Ds">유형</label></th>
                                <td>
                                <select id="dtmDs" class="wd100" name="dtmDs">
									<option value='1' selected>월별 DDL요청서 처리현황</option>
				  					<option value='2'>업무별 DDL요청서 처리현황</option>
				  					<option value='3'>부서별 DDL요청서 처리현항</option>
				  					<option value='4'>DB별 DDL요청서 처리현황</option>
								</select>
								</td> --%>
                                <th scope="row"><label for="searchBgnDe"><s:message code="TERM" /></label></th> <!-- 기간 -->
                                <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80"/>
                              	- <input id="searchEndDe" name="searchEndDe" type="text" class="wd80"/></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
         <!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<div style="clear:both; height:5px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

</div>



<div id="tabs">
	  <ul>
	    <li id="tab-PT01"><a href="#tabs-pt01"><s:message code="MTHY.DDL.LORQ.TRTT.PRES"/></a></li> <!-- 월별 DDL요청서 처리현황 -->
	    <li id="tab-PT02"><a href="#tabs-pt02"><s:message code="BZWR.DDL.LORQ.TRTT.PRES"/></a></li> <!-- 업무별 DDL요청서 처리현황 -->
	    <li id="tab-PT03"><a href="#tabs-pt03"><s:message code="DEPT.DDL.LORQ.TRTT.PRES"/></a></li> <!-- 부서별 DDL요청서 처리현항 -->
	    <li id="tab-PT04"><a href="#tabs-pt04"><s:message code="DB.DDL.LORQ.TRTT.PRES"/></a></li> <!-- DB별 DDL요청서 처리현황 -->
	  </ul>
	  <div id="tabs-pt01">
		 	<div id="detailInfoPT01"><%@include file="list/ddlRqstPeriod_lst.jsp" %></div> 
	  </div>
	  <div id="tabs-pt02">
			<div id="detailInfoPT02"><%@include file="list/ddlRqstBiz_lst.jsp" %></div>
	  </div>
	  <div id="tabs-pt03">
			<div id="detailInfoPT03"><%@include file="list/ddlRqstDept_lst.jsp" %></div>
	  </div>
	  <div id="tabs-pt04">
			<div id="detailInfoPT04"><%@include file="list/ddlRqstDb_lst.jsp" %></div>
	  </div> 
</div>


<div style="clear:both; height:5px;"><span></span></div>
</div>

</body>
</html>