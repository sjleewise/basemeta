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
// 		$("#frmInput #errTlgLanDscd").val('${result.errTlgLanDscd}');
// 		$("#frmInput #errTpCd").val('${result.errTpCd}');
		$("#frmInput #msgTyp").val('${result.msgTyp}');
		$("#frmInput #msgDit").val('${result.msgDit}');
		$("#frmInput #bizDit").val('${result.bizDit}');
// 		$("#frmInput #stdErrCdYn").val('${result.stdErrCdYn}');
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
<!-- 			<th scope="row"><label for="errTlgLanDscd">오류전문언어구분코드</label></th> -->
<!-- 			<td> -->
<%-- 				<select id="errTlgLanDscd" name="errTlgLanDscd" disabled="disabled"> --%>
<!-- 				  <option value="">선택</option> -->
<%-- 				<c:forEach var="code" items="${codeMap.errTlgLanDscd}" varStatus="status"> --%>
<%-- 				  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 				</c:forEach> --%>
<%-- 				</select> --%>
<!-- 			</td> -->
<!-- 			<th scope="row"><label for="errTpCd">오류유형코드</label></th> -->
<!-- 			<td> -->
<%-- 				<select id="errTpCd" name="errTpCd" disabled="disabled"> --%>
<!-- 				  <option value="">선택</option> -->
<%-- 				<c:forEach var="code" items="${codeMap.errTpCd}" varStatus="status"> --%>
<%-- 				  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 				</c:forEach> --%>
<%-- 				</select> --%>
<!-- 			</td> -->
			<th scope="row"><label for="msgTyp">메시지유형</label></th>
			<td>
				<select id="msgTyp" name="msgTyp" disabled="disabled">
				  <option value="">선택</option>
				<c:forEach var="code" items="${codeMap.msgTyp}" varStatus="status">
				  <option value="${code.codeCd}">${code.codeLnm}</option>0
				</c:forEach>
				</select>
			</td>
			<th scope="row"><label for="msgDit">메시지구분</label></th>
			<td>
				<select id="msgDit" name="msgDit" disabled="disabled">
				  <option value="">선택</option>
				<c:forEach var="code" items="${codeMap.msgDit}" varStatus="status">
				  <option value="${code.codeCd}">${code.codeLnm}</option>0
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<th scope="row"><label for="bizDit">업무구분</label></th>
			<td>
				<select id="bizDit" name="bizDit" disabled="disabled">
				  <option value="">선택</option>
				<c:forEach var="code" items="${codeMap.bizDit}" varStatus="status">
				  <option value="${code.codeCd}">${code.codeLnm}</option>0
				</c:forEach>
				</select>
			</td>
<!-- 			<th scope="row"><label for="stdErrCdYn">표준오류코드여부</label></th> -->
<!-- 			<td> -->
<%-- 				<select id="stdErrCdYn" name="stdErrCdYn" disabled="disabled"> --%>
<!-- 				  <option value="">선택</option> -->
<%-- 				<c:forEach var="code" items="${codeMap.stdErrCdYn}" varStatus="status"> --%>
<%-- 				  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 				</c:forEach> --%>
<%-- 				</select> --%>
<!-- 			</td> -->
            <th scope="row">오류코드적용일자</th> 
			<td>
				<input id="errCdAplDt" name="errCdAplDt" type="text" class="wd200" value="${result.errCdAplDt}" />
			</td>
		</tr>
		<tr>
			<th scope="row"><label for="stdErrCd">오류코드</label></th>
			<td>
				<input type="text" id="errCd" name="errCd" class="wd200" value="${result.errCd}"  />
			</td>
			<th scope="row">AS-IS오류코드</th> 
			<td>
				<input id="stsyErrCd" name="stsyErrCd" type="text" class="wd200" value="${result.stsyErrCd}" />
			</td>
		</tr>
		<tr>
            <th scope="row">담당파트</th> 
			<td>
				<input id="tskDmn" name="tskDmn" type="text" class="wd200" value="${result.tskDmn}" />
			</td>
                 <th scope="row">담당자명</th> 
			<td>
				<input id="mngUser" name="mngUser" type="text" class="wd200" value="${result.mngUser}" />
			</td>
		</tr>
		<tr>
			<th scope="row"><label for="isdErrCasCts">전산메시지오류원인내용</label></th>
			<td colspan="3" ><input type="text" id="isdErrCasCts" name="isdErrCasCts" value="${result.isdErrCasCts}" class="wd98p" />
		</tr>
<!-- 		<tr> -->
<!-- 			<th scope="row" ><label for="osdErrCasCts">고객메시지오류원인내용</label></th> -->
<%-- 			<td colspan="3" ><input type="text" id="osdErrCasCts" name="osdErrCasCts" value="${result.osdErrCasCts}" class="wd98p" /> --%>
<!-- 		</tr> -->
		<tr>
			<th scope="row"><label for="actnCd">조치코드</label></th>
			<td colspan="3">
				<input type="text" id="actnCd" name="actnCd" class="wd200" value="${result.actnCd}"  />
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