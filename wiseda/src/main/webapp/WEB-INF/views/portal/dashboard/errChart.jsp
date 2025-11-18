<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<chart lowerLimit="0" 
       upperLimit="100" 
       lowerLimitDisplay="Bad" 
       upperLimitDisplay="Good"   
       gaugeStartAngle="180" 
       gaugeEndAngle="0" 
       palette="1" 
       numberSuffix="%" 
       tickValueDistance="20" 
       showValue="0"
       
>
<%-- <c:forEach var="errlist" items="${errChartList}">  --%>
  <colorRange>
	    <color minValue="0" maxValue="20" code="FF654F" />
	    <color minValue="20" maxValue="80" code="F6BD0F" />
	    <color minValue="80" maxValue="100" code="8BBA00" />   
  </colorRange>
  <dials link="javascript:chartProcClick()">
    <dial value="${errChartList.noErrPst}" rearExtension="10"/>
    <%-- <dial value="${errlist.noErrPst}" rearExtension="10" link="javascript:chartProcClick()"/> --%>
  </dials>
<%-- </c:forEach> --%>
</chart>