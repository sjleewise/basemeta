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
		$("#frmInput #isttUsImpoYn").val('${result.isttUsImpoYn}');
		$("#frmInput #rpstErcdYn").val('${result.rpstErcdYn}');
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
			<td colspan="3">
				<input type="text" id="errCd" name="errCd" class="wd200" value="${result.errCd}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="isdErrCasCts">전산메시지오류원인내용</label></th>
			<td ><input type="text" id="isdErrCasCts" name="isdErrCasCts" value="${result.isdErrCasCts}" class="wd98p" />
		<th scope="row" ><label for="osdErrCasCts">고객메시지오류원인내용</label></th>
		<td ><input type="text" id="osdErrCasCts" name="osdErrCasCts" value="${result.osdErrCasCts}" class="wd98p" />
		</tr>   
		<tr>
			<th scope="row"><label for="isttUsImpoYn">기관사용불가여부</label></th>
			<td>
			<select id="isttUsImpoYn" name="isttUsImpoYn">
				<option value="">선택</option>
				<option value="N">N</option>
				<option value="Y">Y</option>
			</select>
		</td>
		<th scope="row"><label for="rpstErcdYn">대표오류코드여부</label></th>
		<td>
			<select id="rpstErcdYn" name="rpstErcdYn">
				<option value="">선택</option>
				<option value="N">N</option>
				<option value="Y">Y</option>
			</select>
			</td>
		</tr>
    </tbody>
    </table>
    </div>
 		<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit">대외기관오류코드</div>
 		<div style="clear:both; height:5px;"><span></span></div>
		<legend>대외기관오류코드</legend>
		<div class="tb_read">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="대외기관오류코드">
				<caption>대외기관오류코드	입력</caption>
				<colgroup>
	            <col style="width:12%;">
	            <col style="width:38%;">
	            <col style="width:12%;">
	            <col style="width:38%;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" ><label for="dmnLnm">도메인명</label></th>
						<td ><input type="text" id="dmnLnm" name="dmnLnm" value="${result.dmnLnm}" class="wd98p" />
						<th scope="row" ><label for="dmnPnm">도메인물리명</label></th>
						<td ><input type="text" id="dmnPnm" name="dmnPnm" value="${result.dmnPnm}" class="wd98p" />
					</tr>
					<tr>
						<th scope="row" ><label for="cdVal">유효값(코드값)</label></th>
						<td ><input type="text" id="cdVal" name="cdVal" value="${result.cdVal}" class="wd98p" />
						<th scope="row" ><label for="cdValNm">유효값명(코드값명)</label></th>
						<td ><input type="text" id="cdValNm" name="cdValNm" value="${result.cdValNm}" class="wd98p" />
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