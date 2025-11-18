<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title>시스템 상세정보</title>
<script type="text/javascript">
$(document).ready(function() {
	
});
</script>
</head>
<body>
	<div class="stit">
		시스템 상세정보
	</div>
	<!-- 단어 상세정보 -->
	<!-- 입력폼 시작 -->
	<form id="frmInfoSys" name="frmInfoSys" method="post">

		<div class="tb_read">
			<table border="0" cellspacing="0" cellpadding="0" summary="">
				<caption>
					<s:message code="INQ.COND" />
					<!-- 조회조건 -->
				</caption>
				<colgroup>
					<col style="width: 12%;">
					<col style="width: 38%;">
					<col style="width: 12%;">
					<col style="width: 38%;">
				</colgroup>


				<tbody>
					<tr>
						<th scope="row"><label for="orgNm"><s:message
									code="ORG.NM" /></label></th>
						<!-- 기관명 -->
						<td colspan="3">
						<input type="text" id="orgNm" name="orgNm" class="wd98p" value="${result.orgNm}" readonly/></td>
					</tr>
					<tr>
						<th scope="row"><label for="infoSysNm">정보시스템명</label></th>
						<td><input type="text" id="infoSysNm" name="infoSysNm"
							class="wd98p" value="${result.infoSysNm}" readonly /></td>
						<th scope="row"><label for="constYy">구축년도</label></th>
						<td><input type="text" id="constYy" name="constYy"
							class="wd98p" value="${result.constYy}" readonly /></td>
					</tr>
					<tr>
						<th scope="row"><label for="relLaw">관련법령(보유목적)</label></th>
						<td colspan="3"><textarea id="relLaw" name="relLaw"
								class="wd98p" readonly>${result.relLaw}</textarea></td>
					</tr>
<%-- 					<tr>
						<th scope="row"><label for="constPurp">보유목적</label></th>
						<td colspan="3"><input type="text" id="constPurp" name="constPurp"
							class="wd98p" value="${result.constPurp}" readonly /></td>
					</tr> --%>
					<tr>
						<th scope="row"><label for="operDeptNm">운영부서명</label></th>
						<td><input type="text" id="operDeptNm" name="operDeptNm"
							class="wd98p" value="${result.operDeptNm}" readonly /></td>
						<th scope="row"><label for="crgUserNm">담당자명</label></th>
						<td><input type="text" id="crgUserNm" name="crgUserNm"
							class="wd98p" value="${result.crgUserNm}" readonly /></td>
					</tr>
					<tr>
						<th scope="row"><label for="crgTelNo">전화번호</label></th>
						<td><input type="text" id="crgTelNo" name="crgTelNo"
							class="wd98p" value="${result.crgTelNo}" readonly /></td>
						<th scope="row"><label for="crgEmailAddr">이메일</label></th>
						<td><input type="text" id="crgEmailAddr" name="crgEmailAddr"
							class="wd98p" value="${result.crgEmailAddr}" readonly /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<!-- 입력폼 끝 -->
	<div style="clear: both; height: 10px;">
		<span></span>
	</div>
</body>
</html>