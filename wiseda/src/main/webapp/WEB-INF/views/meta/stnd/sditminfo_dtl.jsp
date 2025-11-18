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
<title><s:message code="STRD.TERMS.DTL.INFO" /></title><!-- 표준용어 상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		// 범정부 사용 여부 세션 값
		var govYn = "<%=session.getAttribute("govYn")%>";
		
		// 범정부 사용 여부 N일때 숨기기
	 	if ("${govYn}" == "N") {
	 		$("#gov1").hide(); // 범정부 중앙에타 연계항목
	 		$("#gov2").hide(); // 범정부 중앙에타 연계항목 테이블
	 	}
		
		// 범정부 사용 여부 Y일때 보이기
	 	if ("${govYn}" == "Y") {
	 		$("#gov1").show();
	 		$("#gov2").show();
	 	}

		$("#frmInput #infotpChgYn").val('${result.infotpChgYn}');
		$("#frmInput #encYn").val('${result.encYn}');
 		$("#frmInput #persInfoCnvYn").val('${result.persInfoCnvYn}');
		$("#frmInput #stndAsrt").val('${result.stndAsrt}');
		$("#frmInput #dupYn").val('${result.dupYn}');
		
		if('${result.dupYn}' == ""){
			$("#frmInput #dupYn").val('N');
		}
		
		if('${result.encYn}' == ""){
			$("#frmInput #encYn").val('N');
		}

// 		$("#frmInput #persInfoGrd").val('${result.persInfoGrd}');
		$("#frmInput #openYn").val('${result.openYn}');
		$("#frmInput #priRsn").val('${result.priRsn}');
		$("#frmInput #prsnInfoYn").val('${result.prsnInfoYn}');
		
	});
</script>
</head>
<body>
   <div class="stit"><s:message code="STRD.TERMS.DTL.INFO" /></div><!-- 표준용어 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_read">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /><!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>

		<tbody> 
		    <tr>
		     <th scope="row"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					         <td colspan="3">
					         <select  id="stndAsrt" name="stndAsrt"  class="wd300" disabled>
					                 <option value=""></option>
					                 <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					         		  <option value="${code.codeCd}">${code.codeLnm}</option>
					         		</c:forEach>
					         </select>
		    </tr> 
			<tr>
				<%-- <th scope="row"><label for="sditmLnm"><s:message code="STRD.TERMS.LGC.NM" /></label></th> --%> <!-- 표준용어논리명 -->
				<th scope="row"><label for="sditmLnm"><s:message code="LGC.NM" /></label></th> <!-- 영문판용(한글버젼시 위 주석 사용) -->
				<td><input type="text" id="sditmLnm" name="sditmLnm" class="wd98p" value="${result.sditmLnm}" readonly/></td>
				<%-- <th scope="row"><label for="sditmPnm"><s:message code="STRD.TERMS.PHYC.NM" /></label></th> --%> <!-- 표준용어물리명 -->
				<th scope="row"><label for="sditmLnm"><s:message code="PHYC.NM" /></label></th> <!-- 영문판용(한글버젼시 위 주석 사용) -->
				<td><input type="text" id="sditmPnm" name="sditmPnm" class="wd98p" value="${result.sditmPnm}" readonly/></td>
			</tr>
			<tr>
				<th scope="row"><label for="dmnLnm"><s:message code="DMN.LGC.NM" /></label></th> <!-- 도메인논리명 -->
				<td><input type="text" id="dmnLnm" name="dmnLnm" class="wd98p" value="${result.dmnLnm}" readonly/></td>
				<th scope="row"><label for="dmnPnm"><s:message code="DMN.PHYC.NM" /></label></th> <!-- 도메인물리명 -->
				<td><input type="text" id="dmnPnm" name="dmnPnm" class="wd98p" value="${result.dmnPnm}" readonly/></td>
			</tr>
			<tr>
				<th scope="row"><label ><s:message code="DMN.GRP.INFO.TY" /></label></th><!-- 도메인그룹/인포타입 -->
				<td>
					<span class="input_inactive"><input type="text" class="wd30p" id="uppDmngLnm"  name="uppDmngLnm" value="${result.uppDmngLnm}" readonly/></span>
					<span class="input_inactive"><input type="text" class="wd30p"  id="dmngLnm" name="dmngLnm"    value="${result.dmngLnm}" readonly/></span>
					<span class="input_inactive"><input type="text" class="wd30p"  id="infotpLnm" name="infotpLnm"  value="${result.infotpLnm}" readonly/></span>
<%-- 					<div id="selectBoxDiv"> <span></span></div> --%>
				</td>
				<th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
				<td>
					<span class="input_inactive"><input type="text" class="wd80" id="dataType"  name="dataType" value="${result.dataType}" readonly/></span>
					<span class="input_inactive"><input type="text" class="wd50"  id="dataLen" name="dataLen"    value="${result.dataLen}" readonly/></span>
					<span class="input_inactive"><input type="text" class="wd50"  id="dataScal" name="dataScal"  value="${result.dataScal}" readonly/></span>
				</td>
			</tr>
			<tr>
				<th scope="row" style="display:none;"><label for="infotpChgYn"><s:message code="INFO.TYPE.CHG.YN" /></label></th> <!-- 인포타입변경여부 -->
				<td style="display:none;">
					<select id="infotpChgYn" class="wd200" name="infotpChgYn" disabled="disabled">
					<option value=""></option>
					<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
					<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
					</select>
				</td>
				<th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 -->
				<td>
					<select id="encYn" class="wd200" name="encYn" disabled="disabled">
					<option value=""></option>
					<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
					<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
					</select>
				</td>
				
				<th scope="row"><label for="persInfoCnvYn">고객정보변환여부</label></th> <!-- 고객정보변환여부 --> 
				<td>
					<select id="persInfoCnvYn" class="wd200" name="persInfoCnvYn" disabled="disabled">
					<option value=""></option>
					<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
					<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
					</select>
				</td>
<!-- 				<th scope="row"><label >데이터타입변환</label></th> -->
<%-- 	            <td><c:if test="${result.oraDataType ne null }">Oracle&nbsp;:&nbsp;${result.oraDataType}&nbsp;&nbsp;MySQL&nbsp;:&nbsp;${result.myDataType}&nbsp;&nbsp;MS-SQL&nbsp;:&nbsp;${result.msDataType}</c:if></td> --%>
			</tr>
<!-- 			<tr> -->
<%-- 				<th scope="row"><label for="persInfoCnvYn"><s:message code="PERS.YN" /></label></th> <!-- 개인정보여부 --> --%>
<!-- 				<td> -->
<%-- 					<select id="persInfoCnvYn" class="wd200" name="persInfoCnvYn" value = "${result.persInfoCnvYn}" disabled="disabled"> --%>
<!-- 					<option value=""></option> -->
<%-- 					<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%-- 					<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 --> --%>
<%-- 					</select> --%>
<!-- 				</td> -->
<%-- 				<th scope="row"><label for="persInfoGrd"><s:message code="PERS.GRD" /></label></th> <!-- 개인정보등급 --> --%>
<!-- 				<td> -->
<%-- 					<select  id="persInfoGrd" name="persInfoGrd" disabled="disabled"> --%>
<%-- 				        <option value=""><s:message code="CHC"/></option> --%>
<%-- 				        <c:forEach var="code" items="${codeMap.persInfoGrd}" varStatus="status"> --%>
<%-- 						  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 						</c:forEach> --%>
<%-- 					</select> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 		    <tr> -->
<!-- 		     	<th scope="row"><label for="dupYn">중복여부</label></th> 중복여부 -->
<!-- 				<td> -->
<%-- 					<select id="dupYn" class="wd200" name="dupYn" disabled="disabled"> --%>
<!-- 					<option value=""></option> -->
<%-- 					<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%-- 					<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 --> --%>
<%-- 					</select> --%>
<!-- 				</td> -->
<!-- 		    </tr> -->
			<tr>
				<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
				<td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly>${result.objDescn}</textarea></td>
			</tr>
		</tbody>
    </table>
    </div>
    <!-- 여기서부터 범정부 연계 항목 -->
	 <div style="clear:both; height:10px;"><span></span></div>
	 <div id="gov1" class="stit"><s:message code="GOV.CEN.MET" /></div> <!-- 범정부 중앙에타 연계항목 -->
	 <div style="clear:both; height:5px;"><span></span></div>
		 <legend><s:message code="GOV.CEN.MET" /></legend> <!-- 범정부 중앙에타 연계항목 -->
 		 <div id="gov2" class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
               </caption>
               <colgroup>
               <col style="width:12%;">
               <col style="width:38%;">
               <col style="width:12%;">
               <col style="width:38%;">
        	   </colgroup>
               <tbody>
                   <tr>
                       <th scope="row"><s:message code="OPEN.RSN.CD"/></th> <!-- 공개/비공개 여부 -->
                       <td>
                       		<select id="openYn" name="openYn" onchange="OpenRsn()" disabled="disabled">
                       			<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
								<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
							</select>
                       </td>
                       <th scope="row"><s:message code="NOPEN.RSN"/></th> <!-- 비공개사유 -->
                       <td>
                       		<select id="priRsn" name="priRsn" disabled="disabled">
							  <option value=""><s:message code="CHC.NON" /></option> <!-- 선택안함 -->
							<c:forEach var="code" items="${codeMap.nopenRsnCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
							</select>
                       </td>
                   </tr>
                   <tr>
                       <th scope="row"><s:message code="PRSN.INFO.YN"/></th> <!-- 개인정보 여부 -->
                       <td colspan="3">
                       		<select id="prsnInfoYn" name="prsnInfoYn" disabled="disabled">
                       			<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
								<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
							</select>
                       </td>
                       <%-- <th scope="row" class="th_require"><s:message code="ENC.TRG.YN"/></th> <!-- 암호화여부 -->
                       <td>
                       		<select id="encTrgYn" name="encTrgYn">
                       			<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
								<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
							</select>
                       </td> --%>
                   </tr>
                   <tr>
                   	   <th scope="row"><s:message code="CONST.CND"/></th> <!-- 제약조건 -->
                       <td colspan="3">
                       		<input type="text" id="constCnd" name="constCnd" class="wd500" value="${result.constCnd}" readonly/>
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

	<div style="clear:both; height:30px;"><span></span></div>

</body>
</html>