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
<title>DB정보</title>
<script type="text/javascript">
$(document).ready(function() {
	
});
</script>
</head>
<body>
	<div class="stit">
		DB 상세정보
	</div>
	<!-- 단어 상세정보 -->
	<!-- 입력폼 시작 -->
	<form id="frmDbdefn" name="frmDbdefn" action="" method="post">

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
						<th scope="row"><s:message code="ORG.NM" /></th>
						<td><input type="text" id="orgNm" name="orgNm"
							class="wd98p" value="${result.orgNm}" readonly /></td>
						<th scope="row"><label for="infoSysNm"><s:message code="INFO.SYS.NM" /></label></th>
						<td><input type="text" id="infoSysNm" name="infoSysNm"
							class="wd98p" value="${result.infoSysNm}" readonly /></td>
					</tr>
					<tr>
						<th scope="row"><label for="dbConnTrgLnm">논리DB명</label></th>
						<td><input type="text" id="dbConnTrgLnm" name="dbConnTrgLnm"
							class="wd98p" value="${result.dbConnTrgLnm}" readonly /></td>
						<th scope="row"><label for="dbConnTrgPnm">물리DB명</label></th>
						<td><input type="text" id="dbConnTrgPnm" name="dbConnTrgPnm"
							class="wd98p" value="${result.dbConnTrgPnm}" readonly /></td>
					</tr>
					<tr>
						<th scope="row"><label for="objDescn">DB설명</label></th><!-- DB설명 -->
						<td colspan="3"><textarea id="objDescn" name="objDescn"
								class="wd98p">${result.objDescn}</textarea></td>
					</tr>
					<tr>
						<th scope="row"><label for="aplyBizNm">적용업무</label></th>
						<!-- 설명 -->
						<td colspan="3"><textarea id="aplyBizNm" name="aplyBizNm"
								class="wd98p" readonly>${result.aplyBizNm}</textarea></td>
					</tr>
					<tr>
						<th scope="row"><label for="dbmsTypNm">DBMS정보</label></th>
						<td><input type="text" id="dbmsTypNm" name="dbmsTypNm"
							class="wd98p" value="${result.dbmsTypNm}  ${result.dbmsVersNm}" readonly /></td>
						<th scope="row"><label for="osVerNm">운영체제정보</label></th>
						<td><input type="text" id="osVerNm" name="osVerNm"
							class="wd98p" value="${result.osVerNm}" readonly /></td>
					</tr>
					<tr>
						<th scope="row"><label for="constDt">구축일자</label></th>
						<td><input type="text" id="constDt" name="constDt"
							class="wd98p" value="${result.constDt}" readonly /></td>
						<th scope="row"><label for="tblCnt">테이블수</label></th>
						<td><input type="text" id="tblCnt" name="tblCnt"
							class="wd98p" value="${result.tblCnt}" readonly /></td>
					</tr>
					<tr>
						<th scope="row"><label for="dataCpct">데이터용량(MB)</label></th>
						<td><input type="text" id="dataCpct" name="dataCpct"
							class="wd98p" value="${result.dataCpct}" readonly /></td>
						<th scope="row"><label for="pdataExptRsnNm">수집제외사유</label></th>
						<td><input type="text" id="pdataExptRsnNm" name="pdataExptRsnNm"
							class="wd98p" value="${result.pdataExptRsnNm}" readonly /></td>
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