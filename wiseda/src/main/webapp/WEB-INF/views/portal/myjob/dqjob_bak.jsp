<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>품질활동조회</title>
<script type="text/javascript">

$(document).ready(function(){

	$("#formSel").click(function(){
		$("form[name=search]").attr('action', '<c:url value="/portal/myjob/DqJobCtrl.do"/>').submit();
	});
	
	// 게시글 상세조회 Event Bind
	$(".btn_subForm").click(function(){

		var idx = $(".btn_subForm").index(this);
 		getUrl(idx);
		
	});
	
    /* 셋팅 초기화-시스템명 */
    <c:if test="${param.dbmsEnm != null}">
    	$('#dbmsEnm').val('${param.dbmsEnm}');
    </c:if>
    /* 셋팅 초기화-테이블명 */
    <c:if test="${param.tblNm != null}">
    	$('#tblNm').val('${param.tblNm}');
    </c:if>
    /* 셋팅 초기화-원인분석현황 */
    <c:if test="${param.errStatus != null}">
    	$('#errStatus').val('${param.errStatus}');
    </c:if>
    /* 셋팅 초기화-개선활동현황 */
    <c:if test="${param.impStatus != null}">
    	$('#impStatus').val('${param.impStatus}');
    </c:if>
    
});

//게시물상세조회
function getUrl(idx) {

	var objId = $('form[name="subForm"] input[name="objId"]').eq(idx).val();
	var url = "<%=WiseConfig.URL_DQ%>com/popup/pop_bizrule_result_list.jsp?hdnObjId=" + objId;
	OpenWindow(url,"w","800px","700px","true");
//	var popUp = OpenWindow(url,"w","1024px","768px","true");
/* 	$('form[name="subForm"]').eq(idx).attr('action', url);
	$('form[name="subForm"]').eq(idx).attr("target", popUp);
	$('form[name="subForm"]').eq(idx).submit(); */
	
}

</script>

</head>
<body>

<!-- 오른쪽 내용 시작 -->
<div class="right">
	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 나의업무 &gt; 품질활동조회</div>
    <div class="stit">품질활동조회</div>
    <form name="search">
     <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
         <caption>
         조회조건
         </caption>
         <colgroup>
             <col style="width:10%;">
             <col style="width:15%;">
             <col style="width:10%;">
             <col style="width:15%;">
             <col style="width:15%;">
             <col style="width:10%;">
             <col style="width:15%;">
             <col style="width:10%;">
         </colgroup>
         <tr>
	         <th>시스템명</th>
	         <td>
	         <select id="dbmsEnm" name="dbmsEnm">
	         	<option value="">전체</option>
	             <c:forEach var="dbmsEnmCode" items="${dbmsEnmCode}">
	             	<option value="${dbmsEnmCode.dbmsEnm}">${dbmsEnmCode.dbmsEnm}</option>
	             </c:forEach>    
	         </select>
	         </td>
	         <th>테이블명</th>
	         <td><input type="text" id="tblNm" name="tblNm" value=""></td>
	         <th>원인분석현황</th>
	         <td>
	         <select id="errStatus" name="errStatus">
	         	 <option value="">전체</option>
	         	 <c:forEach var="errStatusImpStatusCode" items="${errStatusImpStatusCode}">
	             	<option value="${errStatusImpStatusCode.errStatus}">${errStatusImpStatusCode.errStatus}</option>
	             </c:forEach> 
	         </select>
	         </td>
	         <th>개선활동현황</th>
	         <td>
	         <select id="impStatus" name="impStatus">
	         	 <option value="">전체</option>
	            <c:forEach var="errStatusImpStatusCode" items="${errStatusImpStatusCode}">
	             	<option value="${errStatusImpStatusCode.impStatus}">${errStatusImpStatusCode.impStatus}</option>
	             </c:forEach> 
	         </select>
	         </td>
       	 </tr>
     </table>
    </form>
    <ul class="bt">
        <li><a href="#" class="bt_gray" id="formSel">조회</a></li>
    </ul>
    <div class="bbs_num">[전체건수 : ${totCnt}]</div>
    <div style="overflow-y: hidden; overflow-x: hidden; " >
	    <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="" style="table-layout:fixed;">
	         <caption>
	         게시판리스트
	         </caption>
	         <colgroup>
	         	 <col style="width:90px;">
	             <col style="width:120px;">
	             <col style="width:250px;">
	             <col style="width:50px;">
	             <col style="width:66px;">
	             <col style="width:66px;">
	             <col style="width:50px;">
	             <col style="width:73px;">
	             
	         </colgroup>
	            <tr>
	              <th>시스템명</th>
	              <th>테이블명</th>
	              <th>업무규칙명</th>
	              <th>차수</th>
	              <th>분석건수</th>
	              <th>오류건수</th>
	              <th>원인분석현황</th>
	              <th>개선활동현황</th>
	            </tr>
	    </table>
    </div>
    <!--  테이블 출력을 스크롤 처리한다. -->
    <div style="overflow-y: scroll; overflow-x: hidden; height: 407px;">
	    <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="" style="table-layout:fixed;">
	        <colgroup>
	         	 <col style="width:90px;">
	             <col style="width:120px;">
	             <col style="width:250px;">
	             <col style="width:50px;">
	             <col style="width:66px;">
	             <col style="width:66px;">
	             <col style="width:50px;">
	             <col style="width:57px;">
	        </colgroup>
		    <c:forEach var="result" items="${result}">
		    	<tr class="objIdTr">
				    <td style="text-align: left;">${result.dbmsEnm}</td>
				    <td style="text-align: left;">${result.tblNm}</td>
				    <td class="link_txt">
	               		<form name="subForm" method="post">
							<input type="hidden" name="objId" value="<c:out value='${result.objId}'/>" />
					    	<a class="btn_subForm" style="text-align: left;"><span class="ellipsis" style="width:240px;" ><c:out value="${result.braNm}"/></span></a>
				    	</form>
              	    </td> 
					<td>${result.chasu}</td>
				    <td style="text-align: right;">${result.anaActCnt}</td>
				    <td style="text-align: right;">${result.errcnt}</td>
				    <td>${result.errStatus}</td>
				    <td>${result.impStatus}</td>
			    </tr>
			    
		    </c:forEach>
	    </table>
    </div>
</div>
<!-- 오른쪽 내용 끝 -->


</body>
</html>