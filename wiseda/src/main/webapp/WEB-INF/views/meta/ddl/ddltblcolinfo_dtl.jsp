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
<title><s:message code="DDL.TBL.CLMN.INFO" /></title> <!-- DDL테이블 컬럼정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #nonulYn").val('${result.nonulYn}');
		$("#frmInput #pkYn").val('${result.pkYn}');
		$("#frmInput #fkYn").val('${result.fkYn}');
		$("#frmInput #akYn").val('${result.akYn}');
		$("#frmInput #encYn").val('${result.encYn}');
	
	});
	

</script>
</head>
<body>
   <div class="stit"><s:message code="DDL.TBL.CLMN.INFO" /></div> <!-- DDL테이블 컬럼정보 -->
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
                     	        <th scope="row"><label for="ddlColLnm"><s:message code="DDL.CLMN.LGC.NM" /></label></th><!-- DDL컬럼논리명 -->
                                <td><input type="text" id="ddlColLnm" name="ddlColLnm" class="wd98p" value="${result.ddlColLnm}" readonly/></td>
                           		<th scope="row"><label for="ddlColPnm"><s:message code="DDL.CLMN.PHYC.NM" /></label></th><!-- DDL컬럼물리명 -->
                                <td><input type="text" id="ddlColPnm" name="ddlColPnm" class="wd98p" value="${result.ddlColPnm}" readonly/></td>
                            </tr>
                            <tr>
                           		<th scope="row"><label for="colOrd"><s:message code="CLMN.SQNC" /></label></th><!-- 컬럼순서 -->
                                <td><input type="text" id="colOrd" name="colOrd" class="wd98p" value="${result.colOrd}" readonly/></td>
                                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
                                <td><input type="text" id="dataType" name="dataType" class="wd98p" value="${result.dataType}" readonly/></td>
                            </tr>
                            <tr>
                           		<th scope="row"><label for="dataLen"><s:message code="DATA.LNGT" /></label></th> <!-- 데이터길이 -->
                                <td><input type="text" id="dataLen" name="dataLen" class="wd98p" value="${result.dataLen}" readonly/></td>
                                <th scope="row"><label for="dataScal"><s:message code="DATA.FPOINT" /></label></th> <!-- 데이터소수점 -->
                                <td><input type="text" id="dataScal" name="dataScal" class="wd98p" value="${result.dataScal}" readonly/></td>
                            </tr>
                            <tr>
                           		<th scope="row"><label for="pkYn"><s:message code="PK.YN" /></label></th> <!-- PK여부 -->
                                <td><select id="pkYn" class="wd200" name="pkYn" value = "${result.pkYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                    </select></td>
                                <th scope="row"><label for="pkOrd"><s:message code="PK.SQNC" /></label></th> <!-- PK순서 -->
                                <td><input type="text" id="pkOrd" name="pkOrd" class="wd98p" value="${result.pkOrd}" readonly/></td>
                            </tr>
                            <tr>
                           		<th scope="row"><label for="fkYn"><s:message code="FK.YN" /></label></th> <!-- FK여부 -->
                                <td><select id="fkYn" class="wd200" name="fkYn" value = "${result.fkYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                    </select></td>
                                <th scope="row"><label for="akYn"><s:message code="AK.YN" /></label></th> <!-- AK여부 -->
                                <td><select id="akYn" class="wd200" name="akYn" value = "${result.akYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                    </select></td>
                            </tr>
                            <tr>
                           		<th scope="row"><label for="`"><s:message code="NOTNULL.YN" /></label></th><!-- NOTNULL 여부 -->
                                <td><select id="nonulYn" class="wd200" name="nonulYn" value = "${result.nonulYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                    </select></td>
                                <th scope="row"><label for="defltVal"><s:message code="DEFAULT.VAL" /></label></th> <!-- DEFAULT값 -->
                                <td><input type="text" id="defltVal" name="defltVal" class="wd98p" value="${result.defltVal}" readonly/></td>
                                
                            </tr>
                            <tr>
                                <th scope="row"><label for="regTypCd"><s:message code="REG.TY.CD" /></label></th><!-- 등록유형코드 -->
                                <td>
                                <select id="regTypCd" class="wd200" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>                          
                                 <th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th><!-- 암호화여부 -->
                                 <td><select id="encYn" class="wd200" name="encYn" value = "${result.encYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
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