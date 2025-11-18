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
<title><s:message code="BZWR.TRRT.INQ.CON" /></title><!-- 업무영역 상세정보 -->
<script type="text/javascript">
$(document).ready(function(){
	$("#bizAreaLnm").focus();
});
</script>
</head>
<body>
   <div class="stit"><s:message code="BZWR.TRRT.INQ.CON" /></div><!-- 업무영역 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	
   	<div class="tb_read">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption><s:message code="BZWR.TRRT.INQ.CON" /></caption><!-- 업무영역 상세정보 -->
        <colgroup>
            <col style="width:20%;" />
            <col style="width:30%;" />
            <col style="width:20%;" />
            <col style="width:30%;" />
        </colgroup>
      <tr>
        <th><s:message code="BZWR.TRRT.NM" /></th><!-- 업무영역명 -->
        <td>
        	<input type="text" name="bizAreaLnm" class="wd300" style="width:70%" readonly value="${result.bizAreaLnm}" >
        </td>
        <th><s:message code="UPRN.BZWR.TRRT.NM"/></th><!-- 상위업무영역명 -->
        <td>
        	<input type="text" name="uppBizAreaLnm" class="wd300" style="width:70%" readonly  value="${result.uppBizAreaLnm}" >
        </td>
      </tr>
      
      <tr>
      	 <th><s:message code="LVL" /></th><!-- 레벨 -->
        <td>
        <input type="text" name="bizAreaLvl" class="wd300" style="width:70%" readonly value="${result.bizAreaLvl}" >
        </td>
        <th><s:message code="VERSION.KO" /></th><!-- 버전 -->
        <td>
        <input type="text" name="objVers" class="wd300" style="width:70%" readonly value="${result.objVers}" >
        </td>
      </tr>
      <tr>
      	<th><s:message code="DEPT.NM" /></th><!-- 버전 -->
        <td>
        <input type="text" name="deptNm" class="wd300" style="width:70%" readonly value="${result.deptNm}" >
        </td>
        <th><s:message code="BZWR.TRRT.CONTENT2" /></th><!-- 업무영역설명 -->
        <td>
        <textarea name="objDescn" id="objDescn" class="wd98p" readonly>${result.objDescn}</textarea>
        </td>
      </tr>
      
      
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
<!--    		요청상세정보 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<%@include file="../../../meta/stnd/otherinfo.jsp" %>
</body>
</html>