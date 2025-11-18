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
<title><s:message code="DDL.IDEX.CLMN.INFO" /></title> <!-- DDL인덱스 컬럼정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #sortTyp").val('${result.sortTyp}');
	});
	

</script>
</head>
<body>
   <div class="stit"><s:message code="DDL.IDEX.CLMN.INFO" /></div> <!-- DDL인덱스 컬럼정보 -->
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
                           		<th scope="row"><label for="ddlIdxColPnm"><s:message code="IDEX.CLMN.NM" /></label></th> <!-- 인덱스컬럼명 -->
                                <td><input type="text" id="ddlIdxColPnm" name="ddlIdxColPnm" class="wd98p" value="${result.ddlIdxColPnm}" readonly/></td>
                     	        <th scope="row"><label for="ddlIdxColLnm"><s:message code="IDEX.CLMN.NM.LGC" /></label></th> <!-- 인덱스컬럼명(논리) -->
                                <td><input type="text" id="ddlIdxColLnm" name="ddlIdxColLnm" class="wd98p" value="${result.ddlIdxColLnm}" readonly/></td>
                            </tr>
                            <tr>
                           		<th scope="row"><label for="ddlIdxColOrd"><s:message code="IDEX.CLMN.SQNC" /></label></th> <!-- 인덱스컬럼순서 -->
                                <td><input type="text" id="ddlIdxColOrd" name="ddlIdxColOrd" class="wd98p" value="${result.ddlIdxColOrd}" readonly/></td>
                                <th scope="row"><label for="sortTyp"><s:message code="ARY.PTRN" /></label></th> <!-- 정렬유형 -->
                                <td colspan="3">
                                <select id="sortTyp" class="wd200" name="sortTyp" value = "${result.sortTyp}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.idxColSrtOrdCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>                          
                            </tr>
                            <tr>
                                <th scope="row"><label for="regTypCd"><s:message code="REG.TY.CD" /></label></th> <!-- 등록유형코드 -->
                                <td colspan="3">
                                <select id="regTypCd" class="wd200" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>                          
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
	<%@include file="../stnd/otherinfo.jsp" %>
	<!-- 입력폼 버튼... -->
<!-- 	<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 		<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 		<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 	</div> -->


</body>
</html>