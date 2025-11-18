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
<title>테이블상세정보</title>
<script type="text/javascript">
	$(document).ready(function() {
		
		//("#frmInput #dmnYn").val('result.dmnYn}');
		

	});
</script>
</head>
<body>
	<div class="stit">
		테이블 상세정보
	</div>
	<!-- 단어 상세정보 -->
	<!-- 입력폼 시작 -->
	<form id="frmInput" name="frmInput" action="" method="">

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
<%-- 					<tr>
						<th scope="row"><label for="dbConnTrgPnm">DB명</label></th>
						<td colspan="3"><input type="text" id="dbConnTrgPnm" name="dbConnTrgPnm"
							class="wd98p" value="${result.dbConnTrgPnm}" readonly/></td>
					</tr>
 --%>					<tr>
						<!-- 테이블영문명 -->
						<th scope="row"><label for="mtaTblPnm">테이블영문명</label></th>
						<td><input type="text" id="mtaTblPnm" name="mtaTblPnm"
							class="wd98p" value="${result.mtaTblPnm}" readonly /></td>
						<!-- 테이블한글명 -->
						<th scope="row"><label for="mtaTblLnm">테이블한글명</label></th>
						<td><input type="text" id="mtaTblLnm" name="mtaTblLnm"
							class="wd98p" value="${result.mtaTblLnm}" readonly /></td>
					</tr>
					<tr>
                   	  <th scope="row"><label for="tblTypNm">테이블유형</label></th> 
                      <td><input type="text" id="tblTypNm" name="tblTypNm" class="wd98p" value="${result.tblTypNm }" readonly /></td>
                   	  <th scope="row"><label for="relEntyNm">관련엔티티</label></th> 
                      <td><input type="text" id="relEntyNm" name="relEntyNm" class="wd98p" value="${result.relEntyNm }" readonly /></td>
                   </tr>
                   <tr>
                   	  <th scope="row"><label for="prsvTerm"><s:message code="STGE.CYCL"/></label></th> <!-- 보존기간 -->
                      <td><input type="text" id="prsvTerm" name="prsvTerm" class="wd98p" value="${result.prsvTerm }" readonly/></td>
                      <th scope="row"><label for="occrCyl">갱신주기</label></th>
                      <td><input type="text" id="occrCyl" name="occrCyl" class="wd98p" value="${result.occrCyl }" readonly /></td>
                   </tr>
                   <tr>
                   	  <th scope="row"><label for="subjNm">분류정보</label></th>
                      <td><input type="text" id="subjNm" name="subjNm" class="wd98p" value="${result.subjNm }" readonly /></td>
                   	  <th scope="row"><label for="tagInfNm">태깅정보</label></th>
                      <td><input type="text" id="tagInfNm" name="tagInfNm" class="wd98p" value="${result.tagInfNm }" readonly /></td>
                   </tr>
					<tr>
						<th scope="row"><label for="objDescn"><s:message
									code="CONTENT.TXT" /></label></th>
						<!-- 설명 -->
						<td colspan="3"><textarea id="objDescn" name="objDescn"
								class="wd98p" readonly>${result.objDescn}</textarea></td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
	<!-- 입력폼 끝 -->
	<div style="clear: both; height: 10px;">
		<span></span>
	</div>
	
	<!-- 입력폼 버튼... -->
	<!-- 	<div id="divFrmBtn" style="text-align: center;"> -->
	<!-- 		<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
	<!-- 		<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
	<!-- 	</div> -->


</body>
</html>