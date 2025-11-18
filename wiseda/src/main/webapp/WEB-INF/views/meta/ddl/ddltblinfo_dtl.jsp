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
<title><s:message code="DDL.TBL.DTL.INFO" /></title> <!-- DDL테이블 상세정보 -->

<script type="text/javascript">
	
$(document).ready(function(){
	$("#frmInput #regTypCd").val('${result.regTypCd}');
		
});
	

</script>

</head>
<body>
   <div class="stit"><s:message code="DDL.TBL.DTL.INFO" /></div> <!-- DDL테이블 상세정보 -->
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
                   <th scope="row"><label for="ddlTblLnm"><s:message code="DL.TBL.LGC.NM" /></label></th><!-- DDL테이블논리명 -->
                     <td><input type="text" id="ddlTblLnm" name="ddlTblLnm" class="wd98p" value="${result.ddlTblLnm}" readonly/></td>
                		<th scope="row"><label for="ddlTblPnm"><s:message code="DL.TBL.PHYC.NM" /></label></th><!-- DDL테이블물리명 -->
                     <td><input type="text" id="ddlTblPnm" name="ddlTblPnm" class="wd98p" value="${result.ddlTblPnm}" readonly/></td>
                 </tr>
                 <tr>
                	 <th scope="row"><label for="dbConnTrgLnm"><s:message code="DB.MS.NM" /></label></th> <!-- DBMS명 -->
                     <td><input type="text" id="dbConnTrgLnm" name="dbConnTrgLnm" class="wd98p" value="${result.dbConnTrgPnm}" readonly/></td>
                     <th scope="row"><label for="dbSchLnm"><s:message code="SCHEMA.NM" /></label></th> <!-- 스키마명 -->
                     <td><input type="text" id="dbSchLnm" name="dbSchLnm" class="wd98p" value="${result.dbSchPnm}" readonly/></td>                     
                 </tr>
                 <tr>
                     <th scope="row"><label for="pdmTblLnm"><s:message code="PHYC.MDEL.TBL.NM" /></label></th><!-- 물리모델테이블명 -->
                     <td><input type="text" id="pdmTblLnm" name="pdmTblLnm" class="wd98p" value="${result.pdmTblPnm}" readonly/></td>
                     <th scope="row"><label for="regTypCd"><s:message code="REG.TY.CD" /></label></th><!-- 등록유형코드 -->
                     <td>
                     	<select id="regTypCd" class="wd200" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
                             <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
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
	<%@include file="../stnd/otherinfo.jsp" %>
	<!-- 입력폼 버튼... -->
<!-- 	<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 		<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 		<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 	</div> -->


</body>
</html>