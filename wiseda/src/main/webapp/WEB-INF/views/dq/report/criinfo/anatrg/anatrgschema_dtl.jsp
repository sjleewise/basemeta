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
<title><s:message code="DIAG.TRGT.SCHEMA.DTL.INFO"/></title><!--진단대상스키마 상세정보-->

<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
</head>
<body>
   <div class="stit"><s:message code="DIAG.TRGT.SCHEMA.DTL.INFO"/></div><!--진단대상스키마 상세정보-->

   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption><s:message code="DIAG.TRGT.SCHEMA.DTL.INFO"/></caption><!--진단대상스키마 상세정보-->

        <colgroup>
            <col style="width:20%;" />
            <col style="width:30%;" />
            <col style="width:20%;" />
            <col style="width:30%;" />
        </colgroup>

      <tr>
        <th><s:message code="DB.SCHEMA.ID" /></th><!--DB스키마ID-->
        <td>
        	<input type="text" name="dbSchId" class="wd300" readonly value="${result.dbSchId}" >
        </td>
        <th><s:message code="DB.CONN.TRGT.ID" /></th><!--DB접속대상ID-->
        <td>
        	<input type="text" name="dbConnTrgId" class="wd300" readonly value="${result.dbConnTrgId}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="DIAG.TRGT.DBMS.PHYC.NM"/></th><!--진단대상DBMS물리명-->
        <td>
        	<input type="text" name="dbConnTrgPnm" class="wd300" readonly value="${result.dbConnTrgLnm}" >
        </td>
        <th><s:message code="DIAG.TRGT.DBMS.LGC.NM"/></th><!--진단대상DBMS논리명-->

        <td>
        	<input type="text" name="dbConnTrgLnm" class="wd300" readonly value="${result.dbConnTrgLnm}" >
        </td>
      </tr>
      <tr>
        <th><s:message code="DB.SCHEMA.PHYC.NM" /></th><!--DB스키마물리명-->
        <td>
        <input type="text" name="dbSchPnm" class="wd300"  readonly value="${result.dbSchPnm}" >
        </td>
        <th><s:message code="DB.SCHEMA.LGC.NM" /></th><!--DB스키마논리명-->
        <td>
        <input type="text" name="dbSchLnm" class="wd300" readonly value="${result.dbSchLnm}" >
        </td>
      </tr>

      <tr>
        <th><s:message code="CONTENT.TXT" /></th><!--설명-->

        <td>
        	<textarea name="objDescn" class="wd300" readonly>${result.objDescn}</textarea>
        </td>
        <th><s:message code="VERSION.KO" /></th><!--버전-->
        <td>
        	<input type="text" name="objVers" class="wd300" readonly  value="${result.objVers}" >
        </td>
      </tr>

      
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>

</body>
</html>