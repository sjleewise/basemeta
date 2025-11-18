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
		$("#frmInput #appPrgmDcd").val('${result.appPrgmDcd}');
	});
</script>
</head>
<body>
   <div class="stit"><s:message code="WORD.DTL.INFO" /></div> <!-- 단어 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_read">
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

		
      <tbody>                   
                        <tr>
                 	        <th scope="row"><label for="appPrgmLnm">응용프로그램용어논리명</label></th>
                            <td><input type="text" id="appPrgmLnm" name="appPrgmLnm" class="wd98p" value="${result.appPrgmLnm}" readonly/></td>
                       		<th scope="row"><label for="appPrgmPnm">응용프로그램용어물리명</label></th>
                            <td><input type="text" id="appPrgmPnm" name="appPrgmPnm" class="wd98p" value="${result.appPrgmPnm}" readonly/></td>
                                                            
                        </tr>
                        <tr>
                        	<th scope="row"><label for="appPrgmDcd">응용프로그램용어구분</label></th> <!-- APP단어구분 -->
                            <td>
                            	<select id="appPrgmDcd" class="wd200" name="appPrgmDcd" value="${result.appPrgmDcd}" disabled="disabled">
                            		<option value=""></option>
                                    <c:forEach var="code" items="${codeMap.appPrgmDcd}" varStatus="status" >
                                    <option value="${code.codeCd}">${code.codeLnm}</option>
                                    </c:forEach>
                                </select>
                            </td>
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