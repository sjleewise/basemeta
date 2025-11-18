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
<title>메뉴상세정보</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #errTlgLanDscd").val('${result.errTlgLanDscd}');
		$("#frmInput #actnUseYn").val('${result.actnUseYn}');
		$("#frmInput #actnDelYn").val('${result.actnDelYn}');
	});
</script>
</head>
<body>
   <div class="stit">메시지 상세정보</div>
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_read">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        조회조건
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
      <tbody>               
		<tr>
			<th scope="row" ><label for="actnCd">조치코드</label></th>
			<td>
				<input type="text" id="actnCd" name="actnCd" class="wd200" value="${result.actnCd}"  disabled="disabled"/>
			</td>
			<th scope="row" ><label for="errTlgLanDscd">언어구분코드</label></th>
			<td>
				<select id="errTlgLanDscd" name="errTlgLanDscd" disabled="disabled">
				  <option value="">선택</option>
				<c:forEach var="code" items="${codeMap.errTlgLanDscd}" varStatus="status">
				  <option value="${code.codeCd}">${code.codeLnm}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="actnMsgCtnt">조치내용</label></th>
			<td ><input type="text" id="actnMsgCtnt" name="actnMsgCtnt" value="${result.actnMsgCtnt}" class="wd98p" disabled="disabled" />
			<th scope="row">조치코드적용일자</th> 
				<td >
					<input id="actnCdAplDt" name="actnCdAplDt" type="text" class="wd200" value="${result.actnCdAplDt}" disabled="disabled" />
				</td>
		</tr>
		<tr>
			<th scope="row"><label for="actnUseYn">조치코드사용여부</label></th>
			<td>
				<select id="actnUseYn" name="actnUseYn" disabled="disabled">
					<option value="">선택</option>
					<option value="N">미사용</option>
					<option value="Y">사용</option>
				</select>
			</td>
			<th scope="row"><label for="actnDelYn">조치코드삭제여부</label></th>
			<td>
				<select id="actnDelYn" name="actnDelYn" disabled="disabled">
					<option value="">선택</option>
					<option value="N">미삭제</option>
					<option value="Y">삭제</option>
				</select>
			</td>
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