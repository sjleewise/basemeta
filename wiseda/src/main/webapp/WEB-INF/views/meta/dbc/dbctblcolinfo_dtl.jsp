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
<title><s:message code="TOP.UPPER.MOVE" /></title> <!-- DBC테이블 컬럼정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTyp").val('${result.regTyp}');
		$("#frmInput #nullYn").val('${result.nullYn}');
		$("#frmInput #pkYn").val('${result.pkYn}');
		$("#frmInput #ddlColExtncYn").val('${result.ddlColExtncYn}');
	});
	

</script>
</head>
<body>
   <div class="stit"><s:message code="TOP.UPPER.MOVE" /></div> <!-- DBC테이블 컬럼정보 -->
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
                     	        <th scope="row"><label for="dbConnTrgLnm"><s:message code="DB.CONN.TRGT.NM" /></label></th> <!-- DB접속대상명 -->
                                <td><input type="text" id="dbConnTrgLnm" name="dbConnTrgLnm" class="wd98p" value="${result.dbConnTrgLnm}" readonly/></td>  
                                <th scope="row"><label for="dbSchLnm"><s:message code="DB.SCHEMA.NM" /></label></th> <!-- DB스키마명 -->
                                <td><input type="text" id="dbSchLnm" name="dbSchLnm" class="wd98p" value="${result.dbSchLnm}" readonly/></td>                     
                     	        <th scope="row"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
                                <td><input type="text" id="subjLnm" name="subjLnm" class="wd98p" value="${result.subjLnm}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="dbcTblNm"><s:message code="DB.C.TBL.NM" /></label></th> <!-- DBC테이블명 -->
                                <td><input type="text" id="dbcTblNm" name="dbcTblNm" class="wd98p" value="${result.dbcTblNm}" readonly/></td>
                                <th scope="row"><label for="dbcColNm"><s:message code="DB.C.CLMN.NM" /></label></th> <!-- DBC컬럼명 -->
                                <td><input type="text" id="dbcColNm" name="dbcColNm" class="wd98p" value="${result.dbcColNm}" readonly/></td>
                                <th scope="row"><label for="dbcColKorNm"><s:message code="DB.C.CLMN.KRN.NM" /></label></th> <!-- DBC컬럼한글명 -->
                                <td><input type="text" id="dbcColKorNm" name="dbcColKorNm" class="wd98p" value="${result.dbcColKorNm}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="ddlColLnm"><s:message code="DDL.CLMN.NM" /></label></th> <!-- DDL컬럼명 -->
                                <td><input type="text" id="ddlColLnm" name="ddlColLnm" class="wd98p" value="${result.ddlColLnm}" readonly/></td>
                                <th scope="row"><label for="pdmColLnm"><s:message code="PHYC.MDEL.CLMN.NM" /></label></th> <!-- 물리모델컬럼명 -->
                                <td><input type="text" id="pdmColLnm" name="pdmColLnm" class="wd98p" value="${result.pdmColLnm}" readonly/></td>
                                <th scope="row"><label for="itmId"><s:message code="STRD.TERMS.NM" /></label></th> <!-- 항목명 -->
                                <td><input type="text" id="itmId" name="itmId" class="wd98p" value="${result.itmId}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
                                <td><input type="text" id="dataType" name="dataType" class="wd98p" value="${result.dataType}" readonly/></td>
                                <th scope="row"><label for="dataLen"><s:message code="DATA.LNGT" /></label></th> <!-- 데이터갈이 -->
                                <td><input type="text" id="dataLen" name="dataLen" class="wd98p" value="${result.dataLen}" readonly/></td>
                                <th scope="row"><label for="dataPnum"><s:message code="DATA.DGTCCNT" /></label></th> <!-- 데이터자리수 -->
                                <td><input type="text" id="dataPnum" name="dataPnum" class="wd98p" value="${result.dataPnum}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="dataPnt"><s:message code="DATA.FPOINT" /></label></th> <!-- 데이터소수점 -->
                                <td><input type="text" id="dataPnt" name="dataPnt" class="wd98p" value="${result.dataPnt}" readonly/></td>
                                <th scope="row"><label for="nullYn"><s:message code="NULL.YN" /></label></th> <!-- 널여부 -->
                                <td><select id="nullYn" class="wd200" name="regTyp" value="${result.nullYn}" disabled="disabled">
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                        <option value=""><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                    </select></td> 
                                <th scope="row"><label for="defltLen"><s:message code="DEFAULT.LNGT" /></label></th> <!-- DEFAULT길이 -->
                                <td><input type="text" id="defltLen" name="defltLen" class="wd98p" value="${result.defltLen}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="defltVal"><s:message code="DEFAULT.VAL" /></label></th> <!-- DEFAULT값 -->
                                <td><input type="text" id="defltVal" name="defltVal" class="wd98p" value="${result.defltVal}" readonly/></td>
                                <th scope="row"><label for="pkYn"><s:message code="PK.YN" /></label></th> <!-- PK여부 -->
                                <td><select id="pkYn" class="wd200" name="regTyp" value="${result.pkYn}" disabled="disabled">
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                        <option value=""><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                    </select></td> 
                                <th scope="row"><label for="ord"><s:message code="SQNC" /></label></th> <!-- 순서 -->
                                <td><input type="text" id="ord" name="ord" class="wd200" value="${result.ord}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="pkOrd"><s:message code="PK.SQNC" /></label></th> <!-- PK순서 -->
                                <td><input type="text" id="pkOrd" name="pkOrd" class="wd98p" value="${result.pkOrd}" readonly/></td>
                                <th scope="row"><label for="ddlColExtncYn"><s:message code="DDL.CLMN.EXIS.YN" /></label></th> <!-- DDL컬럼존재여부 -->
                                <td><select id="ddlColExtncYn" class="wd200" name="regTyp" value="${result.ddlColExtncYn}" disabled="disabled">
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                        <option value=""><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                    </select></td> 
                                <th scope="row"><label for="colDescn"><s:message code="CLMN.TXT" /></label></th> <!-- 컬럼설명 -->
                                <td><input type="text" id="colDescn" name="colDescn" class="wd98p" value="${result.colDescn}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="regTyp"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                                <td><select id="regTyp" class="wd200" name="regTyp" value="${result.regTyp}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td> 
                                <th scope="row"><label for="regDtm"><s:message code="REG.DTTM" /></label></th> <!-- 등록일시 -->
                                <td><input type="text" id="regDtm" name="regDtm" class="wd98p" readonly value="<fmt:formatDate value="${result.regDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
                                <th scope="row"><label for="updDtm"><s:message code="REVS.DTTM" /></label></th> <!-- 수정일시 -->
                                <td><input type="text" id="updDtm" name="updDtm" class="wd98p" readonly value="<fmt:formatDate value="${result.updDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"/></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="descn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                                <td colspan="5"><textarea id="descn" name="descn" class="wd98p">${result.descn}</textarea></td>
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