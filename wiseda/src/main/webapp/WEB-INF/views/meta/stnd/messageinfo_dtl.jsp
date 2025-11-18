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
<title><s:message code="MENU.DTL.INFO" /></title> <!-- 메뉴상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #useYn").val('${result.useYn}');
		$("#frmInput #bizDivCd").val('${result.bizDivCd}');
		$("#frmInput #typDivCd").val('${result.typDivCd}');

	});
</script>
</head>
<body>
   <div class="stit"><s:message code="MSG.DTL.INFO" /></div> <!-- 메시지 상세정보 -->
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
                                <th scope="row"><label for="regTypCd"><s:message code="REG.TY.CD" /></label></th> <!-- 등록유형코드 -->
                                <td>
                                <select id="regTypCd" class="wd200" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                  </select></td>
                                  <th scope="row"><label for="useYn"><s:message code="USE.YN" /></label></th> <!-- 사용여부 -->
                                 <td>
                                <select id="useYn" class="wd200" name="useYn" value="${result.useYn}" disabled="disabled">
                                		<option value=""></option>
                                		<option value="Y">Y</option>
                                        <option value="N">N</option>
                                </select>
                             </td>
                            </tr>
                            <tr>
                     	       	<th scope="row" ><label for="typDivCd"><s:message code="PTRN.DV.CD" /></label></th> <!-- 유형구분코드 -->
						          <td>
						          	<!--  <input type="text" id="typDivCd" name="typDivCd" class="wd200" value="" />-->
						          	<select id="typDivCd" name="typDivCd" disabled="disabled">
						          	  <option value=""></option>
						          	<c:forEach var="code" items="${codeMap.msgPtrnDvcd}" varStatus="status">
						          	  <option value="${code.codeCd}">${code.codeLnm}</option>
						          	</c:forEach>
						          	</select>
						          	
						          </td>
						          <th scope="row" ><label for="bizDivCd"><s:message code="BZWR.DV.CD" /></label></th> <!-- 업무구분코드 -->
						          <td>
						          	<select id="bizDivCd" name="bizDivCd" disabled="disabled">
						          	  <option value=""></option>
						          	<c:forEach var="code" items="${codeMap.bizDivCd}" varStatus="status">
						          	  <option value="${code.codeCd}">${code.codeLnm}</option>
						          	</c:forEach>
						          	</select>
						          </td>
                                                                
                            </tr>
                            <tr>
                           		<th scope="row"><label for="msgCd"><s:message code="MSG.CD" /></label></th> <!-- 메시지코드 -->
                                <td><input type="text" id="msgCd" name="msgCd" class="wd98p" value="${result.msgCd}" readonly/></td>
                            	<th scope="row"><label for="msgConts"><s:message code="MSG.CNTN" /></label></th> <!-- 메시지내용 -->
                                <td><input type="text" id="msgConts" name="msgConts" class="wd98p" value="${result.msgConts}" readonly/></td>

                            </tr>
                            <tr>
                           		<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
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