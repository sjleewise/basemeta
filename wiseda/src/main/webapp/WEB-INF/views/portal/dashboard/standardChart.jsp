<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<chart  palette='3'
		showValues='1'
		showBorder='0' 
		decimals='2'
		formatNumberScale='0'
		useRoundEdges='0'
		rotateYAxisName='0'
		baseFontSize ='12'
		baseFont='±¼¸²'
		outCnvBaseFontSize ='12'
		outCnvBaseFont ='±¼¸²'
		yAxisValuesStep= '1'
		bgColor='FFFFFF'
		divLineIsDashed='0'
		divLineDashLen='1'
		divLineDashGap='2'
		xAxisMinValue = '2'
		xAxisMaxValue = '10'
		bubbleScale   = '3'
		is3D 		 = '1'
		showFormBtn   = '1'
		showAboutMenuItem	= '0'
		aboutMenuItemLabel	= "About Wise I Tech"
		aboutMenuItemLink	= "n-http://www.wise.co.kr"
		numberSuffix	= ''     
		maxColWidth = '80'
		LegendPosition = "right"
		isSliced = '1'
		showPercentageValues = '0'
		exportEnabled = '0'
		exportAtClient = '0'
		plotSpacePercent ="13"  
		valuePadding = "5"      
		CanvasPadding = "0"    
>


<categories>
<c:forEach var="list" items="${list}">
  <category label="${list.subjNm}" />   
</c:forEach>
</categories>


<dataset seriesName="ÀüÃ¼"  showValues="1" >
<c:forEach var="list" items="${list}">
	<set label="ÀüÃ¼" value="${list.cnt}"/>
</c:forEach>
</dataset>

<dataset seriesName="Ç¥ÁØ"  showValues="1" >
<c:forEach var="list" items="${list}">
	<set label="Ç¥ÁØ" value="${list.cntY}"/>
</c:forEach>
</dataset>

<dataset seriesName="ºñÇ¥ÁØ"  showValues="1" >
<c:forEach var="list" items="${list}">
	<set label="ºñÇ¥ÁØ" value="${list.cntN}"/>
</c:forEach>
</dataset>

<styles>
	<definition>
		<style name='myCaptionFont' type='font' font='±¼¸²' size='14' color='666666' bold='1' underline='1'/>
		<style name='AxisFont' type='font' font='±¼¸²' size='12' bold='1'/>
		<style name='fonta' type='font' font='±¼¸²' size='1'/>
	</definition>
	<application>
		<apply toObject='Caption' styles='myCaptionFont' />
		<apply toObject='XAxisName' styles='AxisFont' />
		<apply toObject='YAxisName' styles='AxisFont' />
		<apply toObject='tooltext' styles='fonta' />
	</application>
</styles>

</chart>

