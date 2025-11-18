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
<title><s:message code="DB.C.TBL.DTL.INFO" /></title> <!-- DBC테이블 상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTyp").val('${result.regTyp}');
		$("#frmInput #dbmsType").val('${result.dbmsType}');
		
	});
	

</script>
</head>
<body>
   <div class="stit"><s:message code="DB.C.TBL.DTL.INFO" /></div> <!-- DBC테이블 상세정보 -->
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
                                <th scope="row"><label for="dbcTblKorNm"><s:message code="DB.C.TBL.KRN.NM" /></label></th> <!-- DBC테이블한글명 -->
                                <td><input type="text" id="dbcTblKorNm" name="dbcTblKorNm" class="wd98p" value="${result.dbcTblKorNm}" readonly/></td>
                                <th scope="row"><label for="ddlTblLnm"><s:message code="DL.TBL.LGC.NM" /></label></th> <!-- DDL테이블논리명 -->
                                <td><input type="text" id="ddlTblLnm" name="ddlTblLnm" class="wd98p" value="${result.ddlTblLnm}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="dbcTblSpacNm"><s:message code="DB.C.TBL.SPACE.NM" /></label></th> <!-- DBC테이블스페이스명 -->
                                <td><input type="text" id="dbcTblSpacNm" name="dbcTblSpacNm" class="wd98p" value="${result.dbcTblSpacNm}" readonly/></td>
                                <th scope="row"><label for="pdmTblLnm"><s:message code="PHYC.MDEL.TBL.NM" /></label></th> <!-- 물리모델테이블명 -->
                                <td><input type="text" id="pdmTblLnm" name="pdmTblLnm" class="wd98p" value="${result.pdmTblLnm}" readonly/></td>
                                <th scope="row"><label for="dbmsType"><s:message code="DB.MS.TY" /></label></th> <!-- DBMS타입 -->
                                <td><select id="dbmsType" class="wd200" name="dbmsType" value = "${result.dbmsType}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.dbmsTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td> 
                            </tr>
                            <tr>
                            	<th scope="row"><label for="colEacnt"><s:message code="CLMN.CNT1" /></label></th> <!-- 컬럼갯수 -->
                                <td><input type="text" id="colEacnt" name="colEacnt" class="wd98p" value="${result.colEacnt}" readonly/></td>
                                <th scope="row"><label for="rowEacnt"><s:message code="LOW.CNT" /></label></th> <!-- 로우갯수 -->
                                <td><input type="text" id="rowEacnt" name="rowEacnt" class="wd98p" value="${result.rowEacnt}" readonly/></td>
                                <th scope="row"><label for="tblSize"><s:message code="TBL.SIZE" /></label></th> <!-- 테이블크기 -->
                                <td><input type="text" id="tblSize" name="tblSize" class="wd98p" value="${result.tblSize}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="dataSize"><s:message code="DATA.SIZE" /></label></th> <!-- 데이터크기 -->
                                <td><input type="text" id="dataSize" name="dataSize" class="wd98p" value="${result.dataSize}" readonly/></td>
                                <th scope="row"><label for="idxSize"><s:message code="IDEX.SIZE" /></label></th> <!-- 인덱스크기 -->
                                <td><input type="text" id="idxSize" name="idxSize" class="wd98p" value="${result.idxSize}" readonly/></td>
                                <th scope="row"><label for="nuseSize"><s:message code="UN.USE.SIZE" /></label></th> <!-- 미사용크기 -->
                                <td><input type="text" id="nuseSize" name="nuseSize" class="wd98p" value="${result.nuseSize}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="bfColEacnt"><s:message code="BFR.CLMN.CNT" /></label></th> <!-- 이전컬럼갯수 -->
                                <td><input type="text" id="bfColEacnt" name="bfColEacnt" class="wd98p" value="${result.bfColEacnt}" readonly/></td>
                                <th scope="row"><label for="bfRowEacnt"><s:message code="BFR.LOW.CNT" /></label></th> <!-- 이전로우갯수 -->
                                <td><input type="text" id="bfRowEacnt" name="bfRowEacnt" class="wd98p" value="${result.bfRowEacnt}" readonly/></td>
                                <th scope="row"><label for="bfTblSize"><s:message code="BFR.TBL.SIZE" /></label></th> <!-- 이전테이블크기 -->
                                <td><input type="text" id="bfTblSize" name="bfTblSize" class="wd98p" value="${result.bfTblSize}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="bfDataSize"><s:message code="BFR.DATA.SIZE" /></label></th> <!-- 이전데이터크기 -->
                                <td><input type="text" id="bfDataSize" name="bfDataSize" class="wd98p" value="${result.bfDataSize}" readonly/></td>
                                <th scope="row"><label for="bfIdxSize"><s:message code="BFR.IDEX.SIZE" /></label></th> <!-- 이전인덱스크기 -->
                                <td><input type="text" id="bfIdxSize" name="bfIdxSize" class="wd98p" value="${result.bfIdxSize}" readonly/></td>
                                <th scope="row"><label for="bfNuseSize"><s:message code="BFR.UN.USE.SIZE" /></label></th> <!-- 이전미사용크기 -->
                                <td><input type="text" id="bfNuseSize" name="bfNuseSize" class="wd98p" value="${result.bfNuseSize}" readonly/></td>
                            </tr>
                            <tr>
                            	<th scope="row"><label for="anaDtm"><s:message code="ANLY.DTTM" /></label></th> <!-- 분석일시 -->
                                <td><input type="text" id="anaDtm" name="anaDtm" class="wd98p" value="<fmt:formatDate value="${result.anaDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                                <th scope="row"><label for="crtDtm"><s:message code="CRTN.DTTM" /></label></th> <!-- 생성일시 -->
                                <td><input type="text" id="crtDtm" name="crtDtm" class="wd98p" value="<fmt:formatDate value="${result.crtDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                                <th scope="row"><label for="chgDtm"><s:message code="CHG.DTTM" /></label></th> <!-- 변경일시 -->
                                <td><input type="text" id="chgDtm" name="chgDtm" class="wd98p" value="<fmt:formatDate value="${result.chgDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                            </tr>
                           
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