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
	setDate2();
	setDate3();
    
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

function setDate2() {
	//달력...
	$( "#startDate" ).datepicker();
	$( "#endDate" ).datepicker();
	
	//기간 버튼 클릭 체크
	$("#set_date_update a").click(function(){
	  var btna = $("#set_date_update a");
	  var idx = btna.index(this);
	  btna.removeClass('tb_bt_select').addClass('tb_bt');
	  btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');
     	
	  setBetweenDtm( idx, $( "#startDate" ), $( "#endDate" ));
	});

	if($( "#startDate" ).val() == null || $( "#startDate" ).val() == '')
		$("#set_date_update a").eq(2).click();
}

function setDate3() {
	
	//달력...
	$( "#startDateSub" ).datepicker();
	$( "#endDateSub" ).datepicker();
	
	//기간 버튼 클릭 체크
	$("#set_date_req a").click(function(){
	  var btna = $("#set_date_req a");
	  var idx = btna.index(this);
	  btna.removeClass('tb_bt_select').addClass('tb_bt');
	  btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');
	
	  setBetweenDtm( idx, $( "#startDateSub" ), $( "#endDateSub" ));
	});
	
	
	if($( "#startDateSub" ).val() == null || $( "#startDateSub" ).val() == '')
	$("#set_date_req a").eq(2).click();
}

function doAction(action) {
	
	switch(action) {
	case 'searchTab1':
		$('div#tabs-1').load('<c:url value="/portal/dashboard/ajax/ProgramList.do"/> div#tabs-1');
		break;

	case 'searchTab2':
		if($("#startDate").val() == "" ){
			showMsgBox("ERR", "시작일을 입력하세요!");
			return false;
		}else if($("#endDate").val() == ""){
        	showMsgBox("ERR", "종료일을 입력하세요!");
			return false;
		}
		var param = $("form[name=formProc]").serialize();
		
		$('div#tabs-2').load('<c:url value="/portal/dashboard/ajax/ProgramProcAssistUpdate.do"/> div#tabs-2', param, function(){
			setDate2();
		});
		break;
		
	case 'searchTab3':
		if($("#startDateSub").val() == ""){
			showMsgBox("ERR", "시작일을 입력하세요!");
			return false;
		}else if($("#endDateSub").val() == ""){
			showMsgBox("ERR", "종료일을 입력하세요!");
			return false;
		}
		var param = $("form[name=formProcRe]").serialize();
		
		$('div#tabs-3').load('<c:url value="/portal/dashboard/ajax/ProgramProcAssistRequest.do"/> div#tabs-3', param, function(){
			setDate3();
		});
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
	  	 <form name="formBon">
            <ul class="bt">
            	<li><a class="bt_gray" id="btnBon" onclick="javascript:doAction('searchTab1'); return false;">조회</a></li>
            </ul>
	        <!-- 업무별 프로그램 변경  -->
	        <div class="grid_num"></div>
	        <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="">
	         <tr class="tr_th">
	           <th>업무명</th>
	           <!-- <th>업무ID</th> -->
	           <th>CLIENT 수</th>
	           <th>SERVICE 수</th>
	           <th>BATCH 수</th>
	           <th>FUNCTION 수</th>
	           <th>JSP 수</th>
	           <th>JAVA 수</th>
	           <th>기타 수</th>
	           <th>폐기 수	</th>
	           <th>합계</th>
	         </tr>
			 <c:forEach var="listProc" items="${listProc}" varStatus="status">
			 <c:if test="${status.count == 1}">
	         <tr class="tr_subtotal" style="cursor:default;">
	           <td>${listProc.prName}</td>
	           <%-- <td>${listProc.prId}</td> --%>
	           <td>${listProc.cntClient}</td>
	           <td>${listProc.cntService}</td>
	           <td>${listProc.cntBatch}</td>
	           <td>${listProc.cntFunc}</td>
	           <td>${listProc.cntJsp}</td>
	           <td>${listProc.cntJava}</td>
	           <td>${listProc.cntEtc}</td>
	           <td>${listProc.cntWaste}</td>
	           <td class="tr_total">${listProc.cntTotal}</td>
	         </tr>
	         </c:if>
	         <c:if test="${status.count % 2 == 0}">
	         <tr style="cursor:default;">
	           <td>${listProc.prName}</td>
	           <%-- <td>${listProc.prId}</td> --%>
	           <td>${listProc.cntClient}</td>
	           <td>${listProc.cntService}</td>
	           <td>${listProc.cntBatch}</td>
	           <td>${listProc.cntFunc}</td>
	           <td>${listProc.cntJsp}</td>
	           <td>${listProc.cntJava}</td>
	           <td>${listProc.cntEtc}</td>
	           <td>${listProc.cntWaste}</td>
	           <td class="tr_total">${listProc.cntTotal}</td>
	         </tr>
	         </c:if>
	         <%-- <c:if test="${status.count != 1} && ${status.count % 2 == 1}"> --%>
	         <c:if test="${status.count > 1 && status.count % 2 == 1}">
	         <tr class="tr_line" style="cursor:default;">
	           <td>${listProc.prName}</td>
	           <%-- <td>${listProc.prId}</td> --%>
	           <td>${listProc.cntClient}</td>
	           <td>${listProc.cntService}</td>
	           <td>${listProc.cntBatch}</td>
	           <td>${listProc.cntFunc}</td>
	           <td>${listProc.cntJsp}</td>
	           <td>${listProc.cntJava}</td>
	           <td>${listProc.cntEtc}</td>
	           <td>${listProc.cntWaste}</td>
	           <td class="tr_total">${listProc.cntTotal}</td>
	         </tr>
	         </c:if>
	         </c:forEach>
	         <tr>
	         	<td>${pcDp.totSum}</td>
	         </tr>
	         <c:if test="${fn:length(listProc) == 0}">
	              	<tr>
	              		<td class="bd_none" colspan="10"><s:message code="MSG.NODATA" /></td>
	              	</tr>
	         </c:if>
	       </table>
         </form> 
	  </div>
	  <div id="tabs-2">
	     <form name="formProc">
	         <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
	           <tr class="tr_th">
	               <th class="bd_none">기간</th>
	               <td class="bd_none" id="set_date_update">
	                   <a class="tb_bt">1일</a>
	                   <a class="tb_bt">3일</a>
	                   <a class="tb_bt">7일</a>
	                   <a class="tb_bt">1개월</a>
	                   <a class="tb_bt">3개월</a>
	                   <a class="tb_bt">6개월</a>
	               </td>
	               <th>등록일</th>
	               <td>
	                  <input id="startDate" name="startDate" type="text" class="wd80" value="${ parammap.startDate}">  - <input id="endDate" name="endDate" type="text" class="wd80" value="${ parammap.endDate}">
	               </td>
	           </tr>
	         </table>
	         <ul class="bt">
	       	    <li><a class="bt_gray" id="btnProc"   onclick="javascript:doAction('searchTab2'); return false;">조회</a></li>
	         </ul>
         </form>
         <div class="grid_num"></div>
         <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="">
	         <tr class="tr_th">
	           <th>업무명</th>
	           <!-- <th>업무ID</th> -->
	           <th>신규 수</th>
	           <th>수정 수</th>
	           <th>삭제 수</th>
	           <th>합계</th>
	         </tr>
			 <c:forEach var="listUpdateProc" items="${listUpdateProc}" varStatus="status">
			 <c:if test="${status.count == 1}">
	         <tr class="tr_subtotal" style="cursor:default;">
	           <td>${listUpdateProc.prName}</td>
	           <%-- <td>${listUpdateProc.prId}</td> --%>
	           <td>${listUpdateProc.cntNew}</td>
	           <td>${listUpdateProc.cntModify}</td>
	           <td>${listUpdateProc.cntDelete}</td>
        	   <td class="tr_total">${listUpdateProc.cntTotal}</td>
	         </tr>
	         </c:if>
	         <c:if test="${status.count % 2 == 0}">
	         <tr style="cursor:default;">
	           <td>${listUpdateProc.prName}</td>
	           <%-- <td>${listUpdateProc.prId}</td> --%>
	           <td>${listUpdateProc.cntNew}</td>
	           <td>${listUpdateProc.cntModify}</td>
	           <td>${listUpdateProc.cntDelete}</td>
        	   <td class="tr_total">${listUpdateProc.cntTotal}</td>
	         </tr>
	         </c:if>
	         <c:if test="${status.count > 1 && status.count % 2 == 1}">
	         <tr class="tr_line" style="cursor:default;">
	           <td>${listUpdateProc.prName}</td>
	           <%-- <td>${listUpdateProc.prId}</td> --%>
	           <td>${listUpdateProc.cntNew}</td>
	           <td>${listUpdateProc.cntModify}</td>
	           <td>${listUpdateProc.cntDelete}</td>
	           <td class="tr_total">${listUpdateProc.cntTotal}</td>
	         </tr>
	         </c:if>
	         </c:forEach>
	         <c:if test="${fn:length(listUpdateProc) == 0}">
              	<tr>
              		<td class="bd_none" colspan="5"><s:message code="MSG.NODATA" /></td>
              	</tr>
	         </c:if>
         </table>
	  </div>  
	  <div id="tabs-3">
	     <form name="formProcRe">
	         <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
	           <tr class="tr_th">
	              <th class="bd_none">기간</th>
	              <td class="bd_none" id="set_date_req">
	                  <a class="tb_bt">1일</a>
	                  <a class="tb_bt">3일</a>
	                  <a class="tb_bt">7일</a>
	                  <a class="tb_bt">1개월</a>
	                  <a class="tb_bt">3개월</a>
	                  <a class="tb_bt">6개월</a>
	              </td>
	              <th>등록일</th>
	              <td>
	                 <input id="startDateSub" name="startDateSub" type="text" class="wd80" value="${parammap3.startDateSub }">  - <input id="endDateSub" name="endDateSub" type="text" class="wd80" value="${parammap3.endDateSub }">
	              </td>
	           </tr>
	         </table>
	         <ul class="bt">
	           	<li><a class="bt_gray" id="btnProcRe" onclick="javascript:doAction('searchTab3'); return false;">조회</a></li>
	         </ul> 
         </form>
         <div class="grid_num"></div>
	         <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="">
	             <%--  
	             <colgroup>
                    <col style="width:18%;">
                    <col style="width:7%;">
                    <col style="width:7%;">
                    <col style="width:8%;">
                    <col style="width:8%;">
                    <col style="width:9%;">
                    <col style="width:7%;">
                    <col style="width:6%;">
                    <col style="width:6%;">
                    <col style="width:6%;">
                    <col style="width:6%;">
                    <col style="width:6%;">
                    <col style="width:6%;">
                 </colgroup> 
		          --%>
		         <tr class="tr_th">
		           <th rowspan="2">업무명</th>
		           <!-- <th>업무ID</th> -->
		           <th rowspan="2">개발의뢰서</th>
		           <th rowspan="2">시험점검표</th>
		           <th rowspan="2">결과보고서</th>
		           <th colspan="2">분배</th>
		           <th rowspan="2">온라인서비스</th>
		           <th rowspan="2">데이터베이스</th>
		           <th rowspan="2">FML요청서</th>
		           <th rowspan="2">배치프로그램</th>
		           <th rowspan="2">윈도우권한</th>
		           <th rowspan="2">코드등록</th>
		           <th rowspan="2">폐기요청서</th>
		           <th rowspan="2">합계</th>
		         </tr>
		         <tr class="tr_th">
		           <th>분배(전체)</th>
		           <th>분배(긴급)</th>
		         </tr>
				 <c:forEach var="listRequestProc" items="${listRequestProc}" varStatus="status">
				 <c:if test="${status.count == 1}">
		         <tr class="tr_subtotal" style="cursor:default;">
		           <td>${listRequestProc.prName}</td>
		           <%-- <td>${listRequestProc.prId}</td> --%>
		           <td>${listRequestProc.cntRequest}</td>
		           <td>${listRequestProc.cntCklist}</td>
		           <td>${listRequestProc.cntReport}</td>
		           <td>${listRequestProc.cntDistNormal}</td>
		           <td>${listRequestProc.cntDistUrgent}</td>
		           <td>${listRequestProc.cntOnLine}</td>
		           <td>${listRequestProc.cntDatabase}</td>
		           <td>${listRequestProc.cntFml}</td>
		           <td>${listRequestProc.cntBatch}</td>
		           <td>${listRequestProc.cntWindow}</td>
		           <td>${listRequestProc.cntCode}</td>
		           <td>${listRequestProc.cntWaste}</td>
		           <td class="tr_total">${listRequestProc.cntTotal}</td>
		         </tr>
		         </c:if>
		         <c:if test="${status.count % 2 == 0}">
		         <tr style="cursor:default;">
		           <td>${listRequestProc.prName}</td>
		           <%-- <td>${listRequestProc.prId}</td> --%>
		           <td>${listRequestProc.cntRequest}</td>
		           <td>${listRequestProc.cntCklist}</td>
		           <td>${listRequestProc.cntReport}</td>
		           <td>${listRequestProc.cntDistNormal}</td>
		           <td>${listRequestProc.cntDistUrgent}</td>
		           <td>${listRequestProc.cntOnLine}</td>
		           <td>${listRequestProc.cntDatabase}</td>
		           <td>${listRequestProc.cntFml}</td>
		           <td>${listRequestProc.cntBatch}</td>
		           <td>${listRequestProc.cntWindow}</td>
		           <td>${listRequestProc.cntCode}</td>
		           <td>${listRequestProc.cntWaste}</td>
		           <td class="tr_total">${listRequestProc.cntTotal}</td>
		         </tr>
		         </c:if>
		         <c:if test="${status.count > 1 && status.count % 2 == 1}">
		         <tr class="tr_line" style="cursor:default;">
		           <td>${listRequestProc.prName}</td>
		           <%-- <td>${listRequestProc.prId}</td> --%>
		           <td>${listRequestProc.cntRequest}</td>
		           <td>${listRequestProc.cntCklist}</td>
		           <td>${listRequestProc.cntReport}</td>
		           <td>${listRequestProc.cntDistNormal}</td>
		           <td>${listRequestProc.cntDistUrgent}</td>
		           <td>${listRequestProc.cntOnLine}</td>
		           <td>${listRequestProc.cntDatabase}</td>
		           <td>${listRequestProc.cntFml}</td>
		           <td>${listRequestProc.cntBatch}</td>
		           <td>${listRequestProc.cntWindow}</td>
		           <td>${listRequestProc.cntCode}</td>
		           <td>${listRequestProc.cntWaste}</td>
		           <td class="tr_total">${listRequestProc.cntTotal}</td>
		         </tr>
		         </c:if>
		         </c:forEach>
		         <c:if test="${fn:length(listRequestProc) == 0}">
		              	<tr>
		              		<td class="bd_none" colspan="14"><s:message code="MSG.NODATA" /></td>
		              	</tr>
		         </c:if>         
	         </table>
	     </div>
   	</div>
   </div>
   <!-- 오른쪽 내용 끝 -->

</body>
</html>