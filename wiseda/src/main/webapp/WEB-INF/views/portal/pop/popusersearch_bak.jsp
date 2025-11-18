<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>품질관리</title>
<script type="text/javascript">

$(document).ready(function(){
	
	$(".resultTR").click(function(){
 		var idx = $(".resultTR").index(this);
 		opener.search.mngUserIdAf.value =  $('.empNO').eq(idx).text();
 		opener.search.mngUserAf.value =  $('.userKnm').eq(idx).text();
 		
 		window.close();
		
	});
	
	$("#formBtn").click(function(){
		
		document.search.pageIndex.value = "1";
		$("form[name=search]").attr('action', '<c:url value="/portal/pop/popUserSearch.do"/>').submit();
	});
	
    $("#btnMsgBoxClose").click(function(){
		
	    window.close();
	});
	
	
});

//목록조회
function select_noticeList(pageNo) {
	document.search.pageIndex.value = pageNo;
	document.search.action = '<c:url value="/portal/pop/popUserSearch.do"/>';
	document.search.submit();
}

</script>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

</head>
<body>
<div class="pop_wrap">
	<div class="pop_top">
    	<div class="pop_tit_01">사용자 검색</div>
        <div class="pop_close" id="btnMsgBoxClose"><a href="javascript:closePop();"><span>X</span> 닫기</a></div>
    </div>
	<div class="pop_container">
    	<div class="pop_cont">
        	<p class="pd10">
	           <form name="search">
	                <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
	            <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
	                <caption>
	                조회조건
	                </caption>
	                <colgroup>
	                    <col style="width:15%;">
	                    <col style="width:35%;">
	                    <col style="width:15%;">
	                    <col style="width:35%;">
	                </colgroup>
		            <tr>
		                <th>사용자ID</th>
		                <td><input type="text" name="empNo" id="empNo"></td>
		                <th>사용자명</th>
		                <td><input type="text" name="userKnm" id="userKnm"></td> 
		            </tr>
		        </table>
		            
	            <ul class="bt">
	            	<li><a class="bt_gray" id="formBtn">조회</a></li>
	                
	            </ul>
		            
	            <div class="bbs_num"></div>
	            <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="">
	                <caption>
	                사용자리스트
	                </caption>
	                <colgroup>
	                    <col style="width:5%;">
	                    <col style="width:15%;">
	                    <col style="width:20%;">
	                    <col style="width:15%;">
	                    <col style="width:15%;">
	                    <col style="width:15%;">
	                    <col style="width:15%;">
	                </colgroup>
	              <tr class="tr_th">
	                <th>번호</th>
	                <th>사용자ID</th>
	                <th>사용자명</th>
	                <th>부서</th>
	                <th>사용자그룹</th>
	              </tr>
	              <c:forEach var="result" items="${result}" varStatus="status">
	              <tr class="resultTR">
	                <td>${status.count}</td>
	                <td><div class="empNO" >${result.empNo}</div></td>
	                <td><div class="userKnm" >${result.userKnm}</div></td>
	                <td><div style="text-align:center;">${result.deptCd}</div></td>
					<td><div style="text-align:center;">${result.userGroupId}</div></td>
	              </tr>
					</c:forEach>
	              <c:if test="${fn:length(result) == 0}">
	              	<tr>
	              		<td class="bd_none" colspan="7"><s:message code="MSG.NODATA" /></td>
	              	</tr>
	              </c:if>
 	           </table>
 	           <c:if test="${pageui != null and pageui != '' }">
	            <div class="paging">
		  			${pageui}
	            </div>
	            </c:if>
 	          </form>   
            </p>
        </div>
    </div>

</div>
</body>
</html>