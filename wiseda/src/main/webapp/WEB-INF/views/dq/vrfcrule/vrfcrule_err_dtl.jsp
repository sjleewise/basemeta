<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="VRFC.DTL.INFO" /></title> <!-- 메뉴상세정보 -->
<script type="text/javascript">
$(document).ready(function(){
	
	//조회 결과값 초기 셋팅
	$("#frmInput #vrfcId").val('${result.vrfcId}');
	$("#frmInput #vrfcNm").val('${result.vrfcNm}');
	$("#frmInput #vrfcTyp").val('${result.vrfcTyp}');
	$("#frmInput #vrfcRule").val('${result.vrfcRule}');
	$("#frmInput #vrfcDescn").val('${result.vrfcDescn}');

});
</script>
</head>
<body>
   <div class="stit"><s:message code="VRFC.DTL.INFO" /></div> <!-- 검증룰 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="vrfcId" name="vrfcId" value="${result.vrfcId}" >
   	<input type="hidden" name="objVers" class="wd300" value="${result.objVers}" >
   
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
      <tr>
        <th><s:message code="VRFC.NM" /></th> <!-- 검증룰명 -->
        <td colspan="3">
        <input type="text" name="vrfcNm" class="wd300" style="width:98%;" value="${result.vrfcNm}" readonly="readonly">
        </td>
      </tr>
      
      
      
      <tr>
        <th><s:message code="VRFC.TYP" /></th> <!-- 검증유형 -->
        <td colspan="3">
        <select id="vrfcTyp" name="vrfcTyp" disabled="disabled">
                                       <option selected="" value=""><s:message code="CHC" /></option> <!-- 선택 -->
					                	<c:forEach var="code" items="${codeMap.vrfcTyp}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select>
        </td>
      </tr>
      <tr>
        <th><s:message code="VRFC.RULE" /></th> <!-- 검증룰 -->
        <td colspan="3">
        <%-- <input type="text" name="vrfcRule" class="wd300" style="width:98%;" value="${result.vrfcRule}" > --%>
        	<textarea rows="5" cols="100" name="vrfcRule" style="width:98%;" readonly="readonly">${result.vrfcRule}</textarea>
        </td>
      </tr>
      <tr>
        <th><s:message code="VRFC.DESCN" /></th> <!-- 검증룰 설명 -->
        <td colspan="3">
        <%-- <input type="text" name="vrfcDescn" class="wd300" value="${result.vrfcDescn}"  > --%>
        <textarea rows="5" cols="100" style="width:98%;" name="vrfcDescn" readonly="readonly">${result.vrfcDescn}</textarea>
        </td>
      </tr>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>

</body>
</html>