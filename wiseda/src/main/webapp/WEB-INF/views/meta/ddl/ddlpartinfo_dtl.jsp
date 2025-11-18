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
<title>DDL파티션 상세정보</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput select").attr('disabled', false);
		$("#frmInput #partTypCd").val('${result.partTypCd}');
		$("#frmInput #subPartTypCd").val('${result.subPartTypCd}');
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #cudYn").val('${result.cudYn}');
		$("#frmInput #aplReqTypCd").val("${result.aplReqTypCd}");
		$("#frmInput #aplReqdt").val("${result.aplReqdt}");
		$("#frmInput select").attr('disabled', true);
	});
	

</script>
</head>
<body>
   <div class="stit">DDL파티션 상세정보</div>
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
				<th scope="row"><label for="dbConnTrgPnm">DBMS명</label></th>
				<td>
					<input type="text" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd98p" value="${result.dbConnTrgPnm}" readonly/>
				</td>
				<th scope="row"><label for="dbSchPnm">스키마명</label></th>
				<td><input type="text" id="dbSchPnm" name="dbSchPnm" class="wd98p" value="${result.dbSchPnm}" readonly/></td>
			</tr> 
			            
			<tr>
				<th scope="row"><label for="ddlTblPnm">DDL테이블명</label></th>
				<td colspan="3"><input type="text" id="ddlTblPnm" name="ddlTblPnm" class="wd98p" value="${result.ddlTblPnm}" readonly/></td>
			</tr>
			
			<tr>
				<th scope="row"><label for="partTypCd">파티션 유형</label></th>
				<td>
					<select id="partTypCd" name="partTypCd" value = "${result.partTypCd}" disabled="disabled">>
						<option value="">---선택---</option>
						<c:forEach var="code" items="${codeMap.partTypCd}" varStatus="status" >
						<option value="${code.codeCd}">${code.codeLnm}</option>
						</c:forEach>
					</select>
				</td>
				<th scope="row"><label for="partKey">파티션키</label></th>
				<td><input type="text" id="partKey" name="partKey" class="wd200" value="${result.partKey}" readonly/></td>
			</tr>
			              
			<tr>
				<th scope="row" ><label for="subPartTypCd">서브파티션 유형</label></th>
				<td>
					<select id="subPartTypCd" name="subPartTypCd" value = "${result.partTypCd}" disabled="disabled">
					<option value="">---선택---</option>
					<c:forEach var="code" items="${codeMap.partTypCd}" varStatus="status" >
					<option value="${code.codeCd}">${code.codeLnm}</option>
					</c:forEach>
					</select>
				</td>
				<th scope="row" ><label for="subPartKey">서브파티션키</label></th>
				<td><input type="text" id="subPartKey" name="subPartKey" class="wd200" value="${result.subPartKey}" readonly/></td>
			</tr>
			<tr>
				<th scope="row"><label for="regTypCd">등록유형코드</label></th>
				<td colspan="3">
					<select id="regTypCd" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
					<c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
					<option value="${code.codeCd}">${code.codeLnm}</option>
					</c:forEach>
					</select>
				</td>  
			</tr>
           
			<tr>
				<th scope="row"><label for="objDescn">설명</label></th>
			    <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly>${result.objDescn}</textarea></td>
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