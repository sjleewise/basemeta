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
<title><s:message code="DB.C.SEQ.DTL.INFO" /></title> <!-- DBC테이블 상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTyp").val('${result.regTyp}');
		$("#frmInput #dbmsType").val('${result.dbmsType}');
		$("#frmInput #ordYn").val('${result.ordYn}');
		$("#frmInput #cycYn").val('${result.cycYn}');
		
	});
	

</script>
</head>
<body>
   <div class="stit"><s:message code="DB.C.SEQ.DTL.INFO" /></div>
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_read">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:15%;">
            <col style="width:18%;">
            <col style="width:15%;">
            <col style="width:18%;">
            <col style="width:15%;">
            <col style="width:18%;">
        </colgroup>

      <tbody>                   
   		
   						<tr>
   							 <th scope="row"><label for="dbmsType"><s:message code="DB.MS.TY" /></label></th> <!-- DBMS타입 -->
                             <td><select id="dbmsType" class="wd200" name="dbmsType" value = "${result.dbmsType}" disabled="disabled">
                                     <option value="" selected></option>
                                     <c:forEach var="code" items="${codeMap.dbmsTypCd}" varStatus="status" >
                                     <option value="${code.codeCd}">${code.codeLnm}</option>
                                     </c:forEach>
                                 </select></td> 
                  	        <th scope="row"><label for="dbConnTrgLnm"><s:message code="DB.CONN.TRGT.NM" /></label></th> <!-- DB접속대상명 -->
                             <td><input type="text" id="dbConnTrgLnm" name="dbConnTrgLnm" class="wd98p" value="${result.dbConnTrgLnm}" readonly/></td>  
                             <th scope="row"><label for="dbSchLnm"><s:message code="DB.SCHEMA.NM" /></label></th> <!-- DB스키마명 -->
                             <td><input type="text" id="dbSchLnm" name="dbSchLnm" class="wd98p" value="${result.dbSchLnm}" readonly/></td>                     
                         	 
                         </tr>
                         <tr>

                             <th scope="row"><label for="dbcSeqNm"><s:message code="DB.C.SEQ.NM" /></label></th> <!-- DBC시퀀스명 -->
                             <td><input type="text" id="dbcSeqNm" name="dbcSeqNm" class="wd98p" value="${result.dbcSeqNm}" readonly/></td>
                         	<th scope="row"><label for="cycYn"><s:message code="CYC.YN" /></label></th>
                             <td><select id="cycYn" class="wd200" name="cycYn" value = "${result.cycYn}" disabled="disabled">
                                     <option value="" selected></option>
                                     <option value="Y">Y</option>
                                     <option value="N">N</option>
                                 </select></td> 
                       		<th scope="row"><label for="ordYn"><s:message code="ORD.YN" /></label></th>
                             <td><select id="ordYn" class="wd200" name="ordYn" value = "${result.ordYn}" disabled="disabled">
                                     <option value="" selected></option>
                                     <option value="Y">Y</option>
                                     <option value="N">N</option>
                                 </select></td> 
                         </tr>
                         <tr>
                         	<th scope="row"><label for="minval"><s:message code="MIN.VAL" /></label></th>
                             <td><input type="text" id="minval" name="minval" class="wd98p" value="${result.minval}" readonly/></td>
                             <th scope="row"><label for="maxval"><s:message code="MAX.VAL" /></label></th>
                             <td><input type="text" id="maxval" name="maxval" class="wd98p" value="${result.maxval}" readonly/></td>
                             <th scope="row"><label for="incby"><s:message code="INCBY" /></label></th>
                             <td><input type="text" id="incby" name="incby" class="wd98p" value="${result.incby}" readonly/></td>
                         </tr>
                         <tr>
                         	<th scope="row"><label for="cacheSz"><s:message code="CACHE.SZ" /></label></th>
                             <td><input type="text" id="cacheSz" name="cacheSz" class="wd98p" value="${result.cacheSz}" readonly/></td>
                             <th scope="row"><label for="lstNum"><s:message code="CUR.VAL" /></label></th>
                             <td><input type="text" id="lstNum" name="lstNum" class="wd98p" value="${result.lstNum}" readonly/></td>
                         </tr>
<%--                          <tr>
                         	<th scope="row"><label for="anaDtm"><s:message code="ANLY.DTTM" /></label></th> <!-- 분석일시 -->
                             <td><input type="text" id="anaDtm" name="anaDtm" class="wd98p" value="<fmt:formatDate value="${result.anaDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                             <th scope="row"><label for="crtDtm"><s:message code="CRTN.DTTM" /></label></th> <!-- 생성일시 -->
                             <td><input type="text" id="crtDtm" name="crtDtm" class="wd98p" value="<fmt:formatDate value="${result.crtDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                             <th scope="row"><label for="chgDtm"><s:message code="CHG.DTTM" /></label></th> <!-- 변경일시 -->
                             <td><input type="text" id="chgDtm" name="chgDtm" class="wd98p" value="<fmt:formatDate value="${result.chgDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                         </tr> --%>
                        
                         <tr>
                         	<th scope="row"><label for="regTyp"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                             <td><select id="regTyp" class="wd200" name="regTyp" value = "${result.regTyp}" disabled="disabled">
                                     <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
                                     <option value="${code.codeCd}">${code.codeLnm}</option>
                                     </c:forEach>
                                 </select></td> 
                             <th scope="row"><label for="regDtm"><s:message code="REG.DTTM" /></label></th> <!-- 등록일시 -->
                             <td><input type="text" id="regDtm" name="regDtm" class="wd98p" readonly value="<fmt:formatDate value="${result.regDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
                             <th scope="row"><label for="updDtm"><s:message code="REVS.DTTM" /></label></th> <!-- 수정일시 -->
                             <td><input type="text" id="updDtm" name="updDtm" class="wd98p" readonly value="<fmt:formatDate value="${result.updDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
                         </tr>
   <%--                       <tr>
                             <th scope="row"><label for="descn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                             <td colspan="5"><textarea id="descn" name="descn" class="wd98p">${result.descn}</textarea></td>
                         </tr> --%>
                         
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