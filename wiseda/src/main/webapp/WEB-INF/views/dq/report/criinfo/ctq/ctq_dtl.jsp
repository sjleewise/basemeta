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
<title><s:message code="IMCE.INFO.ITEM.CONTENT2" /></title> <!--중요정보항목 상세정보-->


<script type="text/javascript">
$(document).ready(function(){
	$("#ctqLnm").focus();
	
	//상세화면 READONLY
	$("#frmInput").addClass("tb_read");
	$("#frmInput select").attr('disabled', true);
	$("#frmInput textarea").attr('readOnly', true);
});
</script>
</head>
<body>
   <div class="stit"><s:message code="IMCE.INFO.ITEM.CONTENT2" /></div> <!--중요정보항목 상세정보-->


   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption><s:message code="IMCE.INFO.ITEM.CONTENT2" /></caption> <!--중요정보항목 상세정보-->


        <colgroup>
            <col style="width:20%;" />
            <col style="width:30%;" />
            <col style="width:20%;" />
            <col style="width:30%;" />
        </colgroup>
      <tr>
        <th>><s:message code="IMCE.INFO.ITEM.NM"/></th><!--중요정보항목명-->

        <td>
        	<input type="text" name="ctqLnm" class="wd300" readonly value="${result.ctqLnm}" >
        </td>
        <th><s:message code="UPRN.IMCE.INFO.ITEM.NM"/></th><!--상위중요정보항목명-->

        <td>
        	<input type="text" name="uppCtqLnm" class="wd300" readonly  value="${result.uppCtqLnm}" >
        </td>
      </tr>
      
      <tr>
      	 <th><s:message code="LVL" /></th><!-- 레벨 -->
        <td>
        <input type="text" name="ctqLvl" class="wd300"  readonly value="${result.ctqLvl}" >
        </td>
        <th><s:message code="VERSION.KO" /></th><!-- 버전 -->
        <td>
        <input type="text" name="objVers" class="wd300" readonly value="${result.objVers}" >
        </td>
      </tr>
      
      <tr>
        <th><s:message code="IMCE.INFO.ITEM.CON.NM"/></th><!--중요정보항목설명-->
        <td colspan="3">
        <textarea id="objDescn" name="objDescn" class="wd98p" readonly>${result.objDescn}</textarea>
        </td>
      </tr>

      
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<%@include file="../../../meta/stnd/otherinfo.jsp" %>
</body>
</html>