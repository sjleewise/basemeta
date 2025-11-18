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
		$("#frmInput #errTpCd").val('${result.errTpCd}');
		$("#frmInput #errCdUsYn").val('${result.errCdUsYn}');
	 	$("#frmInput #chnlTpCd").val('${result.chnlTpCd}');
	 	$("#frmInput #chnlDtlsClcd").val('${result.chnlDtlsClcd}');
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
			<th scope="row"><label for="errCd">오류코드</label></th>
			<td>
				<input type="text" id="errCd" name="errCd" class="wd200" value="${result.errCd}"  />
			</td>
			<th scope="row"><label for="errTlgLanDscd">채널오류전문언어구분코드</label></th>
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
			<th scope="row"><label for="errTpCd">오류유형코드</label></th>
			<td>
				<select id="errTpCd" name="errTpCd" disabled="disabled">
				  <option value="">선택</option>
				<c:forEach var="code" items="${codeMap.errTpCd}" varStatus="status">
				  <option value="${code.codeCd}">${code.codeLnm}</option>
				</c:forEach>
				</select>
			</td>
			<th scope="row"><label for="chnlErrMsgCts">채널오류메시지내용</label></th>
			<td ><input type="text" id="chnlErrMsgCts" name="chnlErrMsgCts" value="${result.chnlErrMsgCts}" class="wd98p" />
		</tr>
		<tr>
			<th scope="row"><label for="chnlErrCd">채널오류코드</label></th>
			<td>
				<input type="text" id="chnlErrCd" name="chnlErrCd" class="wd200" value="${result.chnlErrCd}"  />
			</td>
			<th scope="row"><label for="errCdUsYn">오류코드사용여부</label></th>
			<td>
				<select id="errCdUsYn" name="errCdUsYn" disabled="disabled">
				  <option value="">선택</option>
				<c:forEach var="code" items="${codeMap.errCdUsYn}" varStatus="status">
				  <option value="${code.codeCd}">${code.codeLnm}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th scope="row"><label for="chnlTpCd">채널유형코드 </label></th>
			<td >
				<select id="chnlTpCd" name="chnlTpCd" disabled="disabled">
				  <option value="">선택</option>
				<c:forEach var="code" items="${codeMap.chnlTpCd}" varStatus="status">
				  <option value="${code.codeCd}">${code.codeLnm}</option>
				</c:forEach>
				</select>
			</td>
			<th scope="row"><label for="chnlDtlsClcd">채널세부코드</label></th>
			<td >
				<select id="chnlDtlsClcd" name="chnlDtlsClcd" disabled="disabled">
				  <option value="">선택</option>
				<c:forEach var="code" items="${codeMap.chnlDtlsClcd}" varStatus="status">
				  <option value="${code.codeCd}">${code.codeLnm}</option>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th scope="row"><label for="actnCd">조치코드</label></th>
			<td>
				<input type="text" id="actnCd" name="actnCd" class="wd200" value="${result.actnCd}"  />
			</td>
            <th scope="row">오류코드적용일자</th> 
			<td >
				<input id="errCdAplDt" name="errCdAplDt" type="text" class="wd200" value="${result.errCdAplDt}" />
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