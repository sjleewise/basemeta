<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>프로그램보유현황</title>

<script type="text/javascript">

$(document).ready(function(){
	
	//탭 초기화....
// 	$( "#tabs" ).tabs({heightStyle:"fill"});
	//$( "#tabs" ).tabs();
    
	//달력 및 기간 이벤트 실행
// 	setDate3();
    
	//프로그램본수 탭 실행
	$('#tabs1').click(function(){
		doAction('searchTab1');
	});
    //업무별 프로그램 변경 탭 실행
	$('#tabs2').click(function(){
		doAction('searchTab2');
	});
    //업무별 요청서 집계 탭 실행
	$('#tabs3').click(function(){
		doAction('searchTab3');
	});
	//화면 처음 load실행 시 
	$('#tabs1').click();
	
});


function doAction(action) {
	
	switch(action) {
	case 'searchTab1':
// 		$('div#tabs-1').load('<c:url value="/portal/dashboard/ajaxgrid/programBon.do"/>');
		$(window).resize();
		break;

	case 'searchTab2':
// 		$('div#tabs-2').load('<c:url value="/portal/dashboard/ajaxgrid/programUpdate.do"/>');
		$(window).resize();
		break;
		
	case 'searchTab3':
// 		$('div#tabs-3').load('<c:url value="/portal/dashboard/ajaxgrid/programRequest.do"/>');
		$(window).resize();
		break;
	}

}
</script>
</head>
<body>

   <!-- 오른쪽 내용 시작 -->
   <div class="right">
   	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 데이터현황 &gt; 프로그램보유현황</div>
    <div class="stit">프로그램 보유현황</div>
   	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1" id="tabs1">프로그램본수</a></li>
	    <li><a href="#tabs-2" id="tabs2">업무별 프로그램 변경</a></li>
	    <li><a href="#tabs-3" id="tabs3">업무별 요청서 집계</a></li>
	  </ul>
	  <div id="tabs-1">
	  <%@ include file="ajaxgrid/programbon.jsp" %>
	  </div>
	  <div id="tabs-2">
	  <%@ include file="ajaxgrid/programUpdate.jsp" %>
	  </div>  
	  <div id="tabs-3">
	  <%@ include file="ajaxgrid/programRequest.jsp" %>
	   </div>
   	</div>
   </div>
   <!-- 오른쪽 내용 끝 -->
<form id="frmExcel" name="frmExcel" action="" method="post" >
	<input type="hidden" name="excelhtml" id="excelhtml">
	<input type="hidden" name="excelname" id="excelname">
</form>
</body>
</html>