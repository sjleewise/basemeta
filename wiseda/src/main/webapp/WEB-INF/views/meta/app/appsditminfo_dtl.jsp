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
<title><s:message code="APP.ITEM.DTL.INFO" /></title> <!-- APP항목 상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		
	});
</script>
</head>
<body>
   <div class="stit"><s:message code="APP.ITEM.DTL.INFO" /></div> <!-- APP항목 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_read">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>

		<tbody>  
			<tr>
				<th scope="row"><label for="sditmLnm"><s:message code="APP.ITEM.LGC.NM" /></label></th> <!-- APP항목논리명 -->
				<td><input type="text" id="sditmLnm" name="sditmLnm" class="wd98p" value="${result.appSditmLnm}" readonly/></td>
				<th scope="row"><label for="sditmPnm"><s:message code="APP.ITEM.PHYC.NM" /></label></th> <!-- APP항목물리명 -->
				<td><input type="text" id="sditmPnm" name="sditmPnm" class="wd98p" value="${result.appSditmPnm}" readonly/></td>
			</tr>
			<tr>
				<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
				<td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly>${result.objDescn}</textarea></td>
			</tr>
		</tbody>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<%@include file="otherinfo.jsp" %>
	<!-- 입력폼 버튼... -->
<!-- 	<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 		<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 		<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 	</div> -->


</body>
</html>