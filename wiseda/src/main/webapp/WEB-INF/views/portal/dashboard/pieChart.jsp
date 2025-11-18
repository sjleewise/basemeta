<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<chart palette='1'  
       showValues='1'
       showBorder='1'   
       decimals='0' 
       formatNumberScale='0' 
       useRoundEdges='0' 
       rotateYAxisName='0' 
       baseFontSize ='12' 
       baseFont='굴림' 
       outCnvBaseFontSize ='12' 
       outCnvBaseFont ='굴림' 
       yAxisValuesStep= '1' 
       bgColor='FFFFFF'
       showBorder='0'
       divLineIsDashed='0'
       divLineDashLen='1'
       divLineDashGap='2'
       bubbleScale   = '10' 
       is3D 		 = '1' 
       showFormBtn   = '1' 
       showAboutMenuItem	= '0'
	   maxColWidth = '180'
	   LegendPosition = "bottom"
	   startingAngle = '0'
	   isSliced = '1'
	   showPercentageValues = '0'
	   exportEnabled = '0'
	   exportAtClient = '0'
       pieRadius     ="80"
	
> 
<c:forEach var="listPie" items="${listPie}"> 
	<set label="완료 ${listPie.finCnt}건" value='${listPie.finPst}' isSliced='1'/>
	<set label="미진행 ${listPie.notprcCnt}건"  value='${listPie.notprcPst}' isSliced='0'/> 
	<set label="진행중 ${listPie.prcCnt}건" value='${listPie.prcPst}' isSliced='0'/>  
<%-- <set label="완료 ${listPie.finCnt}건" value='${listPie.finPst}' isSliced='1' link="javascript:pieChartClick()" />
	 <set label="미진행 ${listPie.notprcCnt}건"  value='${listPie.notprcPst}' isSliced='0' link="javascript:pieChartClick()"/> 
	 <set label="진행중 ${listPie.prcCnt}건" value='${listPie.prcPst}' isSliced='0' link="javascript:pieChartClick()" />   --%>
</c:forEach>
<styles>
	<definition>
		<style name='myCaptionFont' type='font' font='Gulim' size='14' color='666666' bold='1' underline='1'/>
		<style name='AxisFont' type='font' font='Gulim' size='12' bold='1'/>
		<style name='fonta' type='font' font='Gulim' size='1'/>
	</definition>
	<application>
		<apply toObject='Caption' styles='myCaptionFont' />
		<apply toObject='XAxisName' styles='AxisFont' />
		<apply toObject='YAxisName' styles='AxisFont' />
		<apply toObject='tooltext' styles='fonta' />
	</application>
</styles>
</chart>
