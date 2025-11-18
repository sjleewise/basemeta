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
	
	$(".btnMsgConf").click(function(){
		
		$("form[name=search]").attr('action', '<c:url value="/portal/myjob/OwnShipCtrl.do"/>').submit();
	});
	
	$("#formBtnUpdate").click(function(){
		
		//alert($("input:checked[id=objId]").is(":checked"));
		
		var message = "<s:message code="CNF.LOGOUT" arguments="${sessionScope.loginVO.name}" />";
		
		if($("input:checked[id=objId]").is(":checked") && $("#mngUserIdBe").val() != "" && $("#mngUserBe").val() != "" && $("#mngUserIdAf").val() != "" && $("#mngUserAf").val() != ""){
			$("form[name=search]").attr('action', '<c:url value="/portal/myjob/OwnShipUpdateCtrl.do"/>').submit();
		}else if($("#objId").val() == ""){
			alert("선택을 체크하세요!");
		}else if($("#mngUserIdBe").val() == ""){
			alert("변경전 담당자ID을 입력하세요!");
		}else if($("#mngUserBe").val() == ""){
			alert("변경전 담당자을 입력하세요!");
		}else if($("#mngUserIdAf").val() == ""){
			alert("변경후 담당자ID을 입력하세요!");
		}else {
			alert("변경후 담당자을 입력하세요!");
		}
	});
	
});

</script>

</head>
<body>
<div class="pop_wrap">
	<div class="pop_top">
    	<div class="pop_tit_01">사용자 검색</div>
        <div class="pop_close" id="btnMsgBoxClose"><a href="javascript:closePop();"><span>X</span> 닫기</a></div>
    </div>
	<div class="pop_container">
    	<div class="pop_cont">
        	<div class="pop_tit_02">두번째 타이틀</div>
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
		                <td><input type="text" name="userId" id="userId"></td>
		                <th>사용자명</th>
		                <td><input type="text" name="userNm" id="userNm"></td> 
		            </tr>
		        </table>
		            
	            <ul class="bt">
	            	<li><a class="bt_gray" id="formBtn">조회</a></li>
	                <li><a class="bt_gray" id="formBtnUpdate">변경</a></li>
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
	                <th>선택</th>
	                <th>사용자ID</th>
	                <th>사용자명</th>
	                <th>부서</th>
	                <th>사용자그룹</th>
	              </tr>
	              <tr>
	                <td><input type="checkBox" name="objId" id="objId" value="선택"></td>
	                <td><div>사용자ID</div></td>
	                <td class="link_txt"><div>사용자명</div></a></td>
	                <td><div style="text-align:center;">부서</div></td>
					<td><div style="text-align:center;">사용자그룹</div></td>
	              </tr>
<%-- 	              <c:if test="${fn:length(result) == 0}">
	              	<tr>
	              		<td class="bd_none" colspan="7"><s:message code="MSG.NODATA" /></td>
	              	</tr>
	              </c:if>
 --%>	           </table>
<%-- 	           <c:if test="${pageui != null and pageui != '' }">
	            <div class="paging">
		  			${pageui}
	            </div>
	            </c:if>
 --%>	          </form>   

            </p>
        </div>
    </div>

</div>
</body>
</html>