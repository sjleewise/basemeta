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
<title>비표준용어 상세정보</title>
<script type="text/javascript">
	$(document).ready(function(){
// 		$("#frmInput #regTypCd").val('${result.regTypCd}');
// 		$("#frmInput #infotpId").val('${result.infotpId}');
// 		$("#frmInput #dmngId").val('${result.dmngId}');
		$("#frmInput #infotpChgYn").val('${result.infotpChgYn}');
		$("#frmInput #encYn").val('${result.encYn}');
		
// 		alert('${result.encYn}');
//         create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId", "전체", true);
//     	double_select(dmnginfotpJson, $("#"+firstSelectBoxId[0]));
//     	$('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
//     		double_select(dmnginfotpJson, $(this));
//     	});
    	
//     	setDomainInfoinit();
	});
</script>
</head>
<body>
   <div class="stit">비표준용어 상세정보</div>
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
				<th scope="row"><label for="sditmLnm">비표준용어논리명</label></th>
				<td><input type="text" id="sditmLnm" name="sditmLnm" class="wd98p" value="${result.sditmLnm}" readonly/></td>
				<th scope="row"><label for="sditmPnm">비표준용어물리명</label></th>
				<td><input type="text" id="sditmPnm" name="sditmPnm" class="wd98p" value="${result.sditmPnm}" readonly/></td>
			</tr>
			<tr>
				<th scope="row"><label for="dmnLnm">도메인논리명</label></th>
				<td><input type="text" id="dmnLnm" name="dmnLnm" class="wd98p" value="${result.dmnLnm}" readonly/></td>
				<th scope="row"><label for="dmnPnm">도메인물리명</label></th>
				<td><input type="text" id="dmnPnm" name="dmnPnm" class="wd98p" value="${result.dmnPnm}" readonly/></td>
			</tr>
			<tr>
				<th scope="row"><label >도메인그룹/인포타입</label></th>
				<td>
					<!--<span class="input_inactive"><input type="text" class="wd30p" id="uppDmngLnm"  name="uppDmngLnm" value="${result.uppDmngLnm}" readonly/></span>-->
					<span class="input_inactive"><input type="text" class="wd30p"  id="dmngLnm" name="dmngLnm"    value="${result.dmngLnm}" readonly/></span>
					<span class="input_inactive"><input type="text" class="wd30p"  id="infotpLnm" name="infotpLnm"  value="${result.infotpLnm}" readonly/></span>
<%-- 					<div id="selectBoxDiv"> <span></span></div> --%>
				</td>
				<th scope="row"><label for="dataType">데이터타입</label></th>
				<td>
					<span class="input_inactive"><input type="text" class="wd80" id="dataType"  name="dataType" value="${result.dataType}" readonly/></span>
					<span class="input_inactive"><input type="text" class="wd50"  id="dataLen" name="dataLen"    value="${result.dataLen}" readonly/></span>
					<span class="input_inactive"><input type="text" class="wd50"  id="dataScal" name="dataScal"  value="${result.dataScal}" readonly/></span>
				</td>
			</tr>
			<tr>
				<th scope="row" style="display:none;"><label for="infotpChgYn">인포타입변경여부</label></th>
				<td style="display:none;">
					<select id="infotpChgYn" class="wd200" name="infotpChgYn" value = "${result.infotpChgYn}" disabled="disabled">
					<option value=""></option>
					<option value="Y">예</option>
					<option value="N">아니오</option>
					</select>
				</td>
				<th scope="row"><label for="encYn">암호화여부</label></th>
				<td colspan="3">
					<select id="encYn" class="wd200" name="encYn" value = "${result.encYn}" disabled="disabled">
					<option value=""></option>
					<option value="Y">예</option>
					<option value="N">아니오</option>
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
	<%@include file="otherinfo.jsp" %>
	<!-- 입력폼 버튼... -->
<!-- 	<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 		<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 		<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 	</div> -->


</body>
</html>