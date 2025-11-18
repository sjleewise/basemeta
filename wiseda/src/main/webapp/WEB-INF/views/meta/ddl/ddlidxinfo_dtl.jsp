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
<title><s:message code="DDL.IDEX.DTL.INFO" /></title> <!-- DDL인덱스 상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #pkIdxYn").val('${result.pkIdxYn}');
		$("#frmInput #ukIdxYn").val('${result.ukIdxYn}');
		$("#frmInput #idxTypCd").val('${result.idxTypCd}');
		
	});
	

</script>
</head>
<body>
   <div class="stit"><s:message code="DDL.IDEX.DTL.INFO" /></div> <!-- DDL인덱스 상세정보 -->
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
                     	        <th scope="row"><label for="ddlIdxPnm"><s:message code="DDL.IDEX.NM" /></label></th> <!-- DDL인덱스명 -->
                                <td><input type="text" id="ddlIdxPnm" name="ddlIdxPnm" class="wd98p" value="${result.ddlIdxPnm}" readonly/></td>
                           		<th scope="row"><label for="ddlIdxLnm"><s:message code="DDL.IDEX.NM.LGC" /></label></th> <!-- DDL인덱스명(논리) -->
                                <td><input type="text" id="ddlIdxLnm" name="ddlIdxLnm" class="wd98p" value="${result.ddlIdxLnm}" readonly/></td>
                            </tr>
                            <tr>
                           		<th scope="row"><label for="dbConnTrgPnm"><s:message code="DB.MS.NM" /></label></th> <!-- DBMS명 -->
                                <td><input type="text" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd98p" value="${result.dbConnTrgPnm}" readonly/></td>
                                <th scope="row"><label for="dbSchPnm"><s:message code="SCHEMA.NM" /></label></th> <!-- 스키마명 -->
                                <td><input type="text" id="dbSchPnm" name="dbSchPnm" class="wd98p" value="${result.dbSchPnm}" readonly/></td>
                                
                            </tr>
                            <tr>
                                <th scope="row"><label for="ddlTblPnm"><s:message code="DDL.TBL.NM" /></label></th> <!-- DDL테이블명 -->
                                <td><input type="text" id="ddlTblPnm" name="ddlTblPnm" class="wd98p" value="${result.ddlTblPnm}" readonly/></td>
                                <th scope="row"><label for="idxSpacPnm"><s:message code="IDEX.SPACE.NM" /></label></th> <!-- 인덱스스페이스명 -->
                                <td><input type="text" id="idxSpacPnm" name="idxSpacPnm" class="wd98p" value="${result.idxSpacPnm}" readonly/></td>
                            </tr>
<!--                             <tr> -->
<%--                                 <th scope="row"><label for="pkIdxYn"><s:message code="K.IDEX.YN" /></label></th> <!-- PK인덱스여부 --> --%>
<!--                                 <td> -->
<%--                                 <select id="pkIdxYn" class="wd200" name="pkIdxYn" value = "${result.pkIdxYn}" disabled="disabled"> --%>
<!--                                         <option value=""></option> -->
<%--                                         <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%--                                         <option value="N"><s:message code="MSG.NO" /></option> <!-- 아니오 --> --%>
<%--                                     </select></td>   --%>
<%--                                 <th scope="row"><label for="ukIdxYn"><s:message code="UNIQUE.IDEX.YN" /></label></th> <!-- UNIQUE 인덱스여부 --> --%>
<!--                                 <td> -->
<%--                                 <select id="ukIdxYn" class="wd200" name="ukIdxYn" value = "${result.ukIdxYn}" disabled="disabled"> --%>
<!--                                         <option value=""></option> -->
<%--                                         <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%--                                         <option value="N"><s:message code="MSG.NO" /></option> <!-- 아니오 --> --%>
<%--                                     </select></td>   --%>
<!--                             </tr> -->
                            <tr>
<%--                                 <th scope="row"><label for="idxTypCd"><s:message code="IDEX.TY.CD" /></label></th> <!-- 인덱스유형코드 --> --%>
<!--                                 <td> -->
<%--                                 <select id="idxTypCd" class="wd200" name="idxTypCd" value = "${result.idxTypCd}" disabled="disabled"> --%>
<%--                                         <c:forEach var="code" items="${codeMap.idxTypCd}" varStatus="status" > --%>
<%--                                         <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%--                                         </c:forEach> --%>
<%--                                     </select></td>   --%>
								<th scope="row"><label for="ukIdxYn"><s:message code="UNIQUE.IDEX.YN" /></label></th> <!-- UNIQUE 인덱스여부 -->
                                <td>
                                <select id="ukIdxYn" class="wd200" name="ukIdxYn" value = "${result.ukIdxYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO" /></option> <!-- 아니오 -->
                                    </select></td> 
                                <th scope="row"><label for="regTypCd"><s:message code="REG.TY.CD" /></label></th> <!-- 등록유형코드 -->
                                <td>
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