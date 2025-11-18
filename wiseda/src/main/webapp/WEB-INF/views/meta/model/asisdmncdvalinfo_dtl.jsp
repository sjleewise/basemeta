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

	});
</script>
</head>
<body>
   <div class="stit">ASIS유효값 상세정보</div>
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
			<th scope="row" ><label for="asisSystemNm">시스템명</label></th>
			<td>
				<input type="text" id="asisSystemNm" name="asisSystemNm" class="wd200" value="${result.asisSystemNm}"  />
			</td>
			<th scope="row" ><label for="asisDit">시스템구분</label></th>
			<td>
				<input type="text" id="asisDit" name="asisDit" class="wd200" value="${result.asisDit}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="asisGroupNm">ASIS그룹명</label></th>
			<td>
				<input type="text" id="asisGroupNm" name="asisGroupNm" class="wd200" value="${result.asisGroupNm}"  />
			</td>
			<th scope="row" ><label for="asisDbNm">ASISDB명</label></th>
			<td>
				<input type="text" id="asisDbNm" name="asisDbNm" class="wd200" value="${result.asisDbNm}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="asisTblNm">테이블명</label></th>
			<td>
				<input type="text" id="asisTblNm" name="asisTblNm" class="wd200" value="${result.asisTblNm}"  />
			</td>
			<th scope="row" ><label for="asisColNm">컬럼명</label></th>
			<td>
				<input type="text" id="asisColNm" name="asisColNm" class="wd200" value="${result.asisColNm}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="dfdColNm">참조테이블명</label></th>
			<td>
				<input type="text" id="dfdColNm" name="dfdColNm" class="wd200" value="${result.dfdColNm}"  />
			</td>
			<th scope="row" ><label for="dfdColValue">참조컬럼명</label></th>
			<td>
				<input type="text" id="dfdColValue" name="dfdColValue" class="wd200" value="${result.dfdColValue}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="codeValue">코드값</label></th>
			<td>
				<input type="text" id="codeValue" name="codeValue" class="wd200" value="${result.codeValue}"  />
			</td>
			<th scope="row" ><label for="codeNm">코드명</label></th>
			<td>
				<input type="text" id="codeNm" name="codeNm" class="wd200" value="${result.codeNm}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="codeDispNum">표시순번</label></th>
			<td>
				<input type="text" id="codeDispNum" name="codeDispNum" class="wd200" value="${result.codeDispNum}"  />
			</td>
			<th scope="row" ><label for="objDescn">설명</label></th>
			<td>
				<input type="text" id="objDescn" name="objDescn" class="wd200" value="${result.objDescn}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="appTeamNm">응용팀</label></th>
			<td>
				<input type="text" id="appTeamNm" name="appTeamNm" class="wd200" value="${result.appTeamNm}"  />
			</td>
			<th scope="row" ><label for="appChrgNm">KB담당자</label></th>
			<td>
				<input type="text" id="appChrgNm" name="appChrgNm" class="wd200" value="${result.appChrgNm}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="appSiNm">SI담당자</label></th>
			<td>
				<input type="text" id="appSiNm" name="appSiNm" class="wd200" value="${result.appSiNm}"  />
			</td>
			<th scope="row" ><label for="tsfTeamNm">전환팀</label></th>
			<td>
				<input type="text" id="tsfTeamNm" name="tsfTeamNm" class="wd200" value="${result.tsfTeamNm}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="tsfChrgNm">전환담당자</label></th>
			<td>
				<input type="text" id="tsfChrgNm" name="tsfChrgNm" class="wd200" value="${result.tsfChrgNm}"  />
			</td>
			<th scope="row" ><label for="lastChgDt">최종수정일자</label></th>
			<td>
				<input type="text" id="lastChgDt" name="lastChgDt" class="wd200" value="${result.lastChgDt}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="addNote1">비고1</label></th>
			<td>
				<input type="text" id="addNote1" name="addNote1" class="wd200" value="${result.addNote1}"  />
			</td>
			<th scope="row" ><label for="addNote2">비고2</label></th>
			<td>
				<input type="text" id="addNote2" name="addNote2" class="wd200" value="${result.addNote2}"  />
			</td>
		</tr>
		<tr>
			<th scope="row" ><label for="addNote3">비고3</label></th>
			<td>
				<input type="text" id="addNote3" name="addNote3" class="wd200" value="${result.addNote3}"  />
			</td>
			<th scope="row" ><label for="addNote4">비고4</label></th>
			<td>
				<input type="text" id="addNote4" name="addNote4" class="wd200" value="${result.addNote4}"  />
			</td>
		</tr>
    </tbody>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<%@include file="../stnd/otherinfo.jsp" %>
</body>
</html>