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
<title><s:message code="STRD.TERMS.DTL.INFO" /></title> <!-- 표준용어 상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		
		$("#frmInput #pgmType").val('${result.pgmType}');
		
	});
</script>
</head>
<body>
<!--    <div class="stit">프로그램 정보</div> -->
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
	        <th scope="row"><label for="pgmFileNm"><s:message code="PGM.FILE.NM" /></label></th> <!-- 프로그램 파일명 -->
           	<td><input type="text" id="pgmFileNm" name="pgmFileNm" class="wd300" value="${result.pgmFileNm}" readonly/></td>
<%--            	<td>${result.pgmFileNm}</td> --%>
      		<th scope="row"><label for="pgmNm"><s:message code="PGM.NM" /></label></th> <!-- 프로그램명 -->
           	<td><input type="text" id="pgmNm" name="pgmNm" class="wd300" value="${result.pgmNm}" readonly/></td>
<%--            	<td>${result.pgmNm}</td> --%>
                                           
       </tr>
       <tr>
	        <th scope="row"><label for="systemNm"><s:message code="BZWR.SYS" /></label></th> <!-- 업무시스템 -->
           	<td><input type="text" id="systemNm" name="systemNm" class="wd300" value="${result.systemNm}" readonly/></td>
      		<th scope="row"><label for="hostIp">HOST IP</label></th>
           	<td><input type="text" id="hostIp" name="hostIp" class="wd300" value="${result.hostIp}" readonly/></td>
                                           
       </tr>
       <tr>
	        <th scope="row"><label for="categoryType"><s:message code="PGM.DSTC" /></label></th> <!-- 프로그램 구분 -->
           	<td><input type="text" id="categoryType" name="categoryType" class="wd300" value="${result.categoryType}" readonly/></td>
      		<th scope="row"><label for="pgmType"><s:message code="PGM.PTRN" /></label></th> <!-- 프로그램 유형 -->
           	<td><select id="pgmType" class="" name="pgmType" disabled="disabled">
	             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
	             <c:forEach items="${codeMap.langType }" var="code" varStatus="status">
	             <option value="${code.codeCd }" >${code.codeLnm}</option>
	             </c:forEach>
	            </select></td>
       </tr>
       <tr>
	        <th scope="row"><label for="orglUser"><s:message code="DFTM" /></label></th> <!-- 작성자 -->
           	<td><input type="text" id="orglUser" name="orglUser" class="wd300" value="${result.orglUser}" readonly/></td>
      		<th scope="row"><label for="orglDttm"><s:message code="FLIN.DTTM" /></label></th> <!-- 작성일시 -->
           	<td><input type="text" id="orglDttm" name="orglDttm" class="wd300" value="<fmt:formatDate value="${result.orglDttm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
      </tr>
       <tr>
	        <th scope="row"><label for="updtUser"><s:message code="EDIR" /></label></th> <!-- 변경자 -->
           	<td><input type="text" id="updtUser" name="updtUser" class="wd300" value="${result.updtUser}" readonly/></td>
      		<th scope="row"><label for="updtDttm"><s:message code="CHG.DTTM" /></label></th> <!-- 변경일시 -->
           	<td><input type="text" id="updtDttm" name="updtDttm" class="wd300" value="<fmt:formatDate value="${result.updtDttm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
      </tr>
       <tr>
	        <th scope="row"><label for="pgmFilePath"></label></th> <!-- 파일경로 -->
           	<td colspan="3"><input type="text" id="pgmFilePath" name="pgmFilePath" class="wd400" value="${result.pgmFilePath}" readonly/></td>
      </tr>
                     
    </tbody>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
<!-- 	<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 		<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 		<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 	</div> -->


</body>
</html>