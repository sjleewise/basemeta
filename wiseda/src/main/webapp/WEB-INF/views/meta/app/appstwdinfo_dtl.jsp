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
		$("#frmInput #wdDcd").val('${result.wdDcd}');
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
                                
                                <th scope="row"><label for="orgDs"><s:message code="ORGN.DSTC" /></label></th> <!-- 출처구분 -->
                                <td><input type="text" id="orgDs" name="orgDs" class="wd98p" value="${result.orgDs}" readonly/></td>
                                <th scope="row"><label for="regTypCd"><s:message code="REG.TY.CD" /></label></th> <!-- 등록유형코드 -->
                                <td>
                                <select id="regTypCd" class="wd200" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.regTypCdValue}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>
                                
                            </tr>
                            <tr>
                     	        <th scope="row"><label for="appStwdLnm"><s:message code="APP.WORD.LGC.NM" /></label></th> <!-- APP단어논리명 -->
                                <td><input type="text" id="appStwdLnm" name="appStwdLnm" class="wd98p" value="${result.appStwdLnm}" readonly/></td>
                           		<th scope="row"><label for="appStwdPnm"><s:message code="APP.WORD.PHYC.NM" /></label></th> <!-- APP단어물리명 -->
                                <td><input type="text" id="appStwdPnm" name="appStwdPnm" class="wd98p" value="${result.appStwdPnm}" readonly/></td>
                                                                
                            </tr>
                            <tr>
                           		<th scope="row"><label for="engMean"><s:message code="ENSN.MEAN" /></label></th> <!-- 영문의미 -->
                                <td><input type="text" id="engMean" name="engMean" class="wd98p" value="${result.engMean}" readonly/></td>
                            	<th scope="row"><label for="wdDcd"><s:message code="APP.WORD.DSTC" /></label></th> <!-- APP단어구분 -->
                                <td>
                                	<select id="wdDcd" class="wd200" name="wdDcd" value="${result.wdDcd}" disabled="disabled">
                                		<option value=""></option>
                                        <c:forEach var="code" items="${codeMap.wdDcd}" varStatus="status" >
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