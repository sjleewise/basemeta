<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<chart lowerLimit="0" 
       upperLimit="100" 
       lowerLimitDisplay="Good" 
       upperLimitDisplay="Bad" 
       gaugeStartAngle="180" 
       gaugeEndAngle="0" 
       palette="1" 
       numberSuffix="%" 
       tickValueDistance="20" 
       showValue="0"
>
<c:forEach var="errChartSubList" items="${errChartSubList}"> 
  <colorRange>
	    <color minValue="0" maxValue="20" code="8BBA00" />
	    <color minValue="20" maxValue="80" code="F6BD0F" />
	    <color minValue="80" maxValue="100" code="FF654F" />
  </colorRange>
  <dials>
    <dial value="${errChartSubList.errRate}" rearExtension="10"/>
    <%-- <dial value="${errChartSubList.errRate}" rearExtension="10" link="javascript:chartProcSubClick()"/> --%>
  </dials>
</c:forEach>
</chart>