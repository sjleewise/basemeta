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
<title><s:message code="DIAG.TRGT.DBMS.DTL.INFO"/></title><!--진단대상DBMS 상세정보-->



<script type="text/javascript">
$(document).ready(function(){
	$("#frmAnaTrgDtl #dbmsTypCd").val('${result.dbmsTypCd}');
	$("#frmAnaTrgDtl #dbmsVersCd").val('${result.dbmsVersCd}');
	
	$("#frmAnaTrgDtl input[type=text]").attr("readonly", true);
	$("#frmAnaTrgDtl input[type=password]").attr("readonly", true);
	//$("#frmAnaTrgDtl select").attr("disabled", true);
	
	//상세화면 READONLY
	$(".tb_basic").addClass("tb_read");
	$("#frmAnaTrgDtl select").attr('disabled', true);
	
});
</script>
</head>
<body>
   <div class="stit"><s:message code="DIAG.TRGT.DBMS.DTL.INFO"/></div><!--진단대상DBMS 상세정보-->



   <!-- 입력폼 시작 -->
   	<form id="frmAnaTrgDtl" name="frmAnaTrgDtl" action ="" method="post">
   	<input type="hidden" name="dbConnTrgId" class="wd300" value="${result.dbConnTrgId}" >
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption><s:message code="DIAG.TRGT.DBMS.DTL.INFO"/></caption><!--진단대상DBMS 상세정보-->



        <colgroup>
            <col style="width:20%;" />
            <col style="width:30%;" />
            <col style="width:20%;" />
            <col style="width:30%;" />
        </colgroup>
      <tr>
        <th><s:message code="DB.CONN.TRGT.PHYC.NM" /></th><!--DB접속대상물리명-->
        <td>
        <input type="text" name="dbConnTrgPnm" class="wd300" value="${result.dbConnTrgPnm}" >
        </td>
        <th><s:message code="DB.CONN.TRGT.LGC.NM" /></th><!--DB접속대상논리명-->
        <td>
        <input type="text" name="dbConnTrgLnm" class="wd300" value="${result.dbConnTrgLnm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="DBMS.KIND"/></th><!--DBMS종류-->
        <td>
                                <select id="dbmsTypCd" class="wd300" name="dbmsTypCd" value = "${result.dbmsTypCd}" >
                                        <c:forEach var="code" items="${codeMap.dbmsTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>
        <th><s:message code="DB.MS.VERSION" /></th><!--DBMS버전-->
        <td>
        <select id="dbmsVersCd" class="wd300" name="dbmsVersCd" value = "${result.dbmsVersCd}" >
                                        <c:forEach var="code" items="${codeMap.dbmsVersCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>
      </tr>
      <tr>
        <th><s:message code="CONN.TRGT.DB.CNCT.STRING"/></th><!--접속대상DB연결문자열-->

        <td>
        	<input type="text" name="connTrgDbLnkChrw" class="wd300" value="${result.connTrgDbLnkChrw}" >
        </td>
        <th><s:message code="CONN.TRGT.CNCT.URL"/></th><!--접속대상연결URL-->

        <td>
        	<input type="text" name="connTrgLnkUrl" class="wd300" value="${result.connTrgLnkUrl}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="CONN.TRGT.DRIVER.NM"/></th><!--접속대상드라이버명-->

        <td>
        	<input type="text" name="connTrgDrvrNm" class="wd300" value="${result.connTrgDrvrNm}" >
        </td>
        <th><s:message code="DB.CONN.STS"/></th><!--DB접속상태-->
        <td>
        	<input type="text" name="dbLnkSts" class="wd300" value="${result.dbLnkSts}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="DB.CONN.ACNT.ID" /></th><!--DB접속계정ID-->
        <td>
        	<input type="text" name="dbConnAcId" class="wd300" value="${result.dbConnAcId}" >
        </td>
        <th><s:message code="DB.CONN.ACNT.PWD"/></th><!--DB접속계정비밀번호-->
        <td>
        	<input type="password" name="dbConnAcPwd" class="wd300" value="${result.dbConnAcPwd}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="CONTENT.TXT" /></th><!--설명-->

        <td colspan="3">
        	<textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn}</textarea>
        </td>
      </tr>
      
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>

</body>
</html>