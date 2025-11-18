<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>


<script type="text/javascript">

$(document).ready(function() {
	
// 	$("#frmdmnfeature input[type=text]").css("border-color","transparent").css("width", "98%").attr("readonly", true);
    $("#frmdmnfeature input[type=text]").attr("readonly", true);

    $("#totCnt").val(numCheck("${summaryresVo.totCnt}"));
    $("#unqVal").val(numCheck("${summaryresVo.unqVal}"));
    $("#mnVal").val(numCheck("${summaryresVo.mnVal}"));
    $("#stdDvt").val(numCheck("${summaryresVo.stdDvt}"));
    $("#maxVal").val(numCheck("${summaryresVo.maxVal}"));
    $("#qrtCnt1").val(numCheck("${summaryresVo.qrtCnt1}"));
    $("#mdnVal").val(numCheck("${summaryresVo.mdnVal}"));
    $("#qrtCnt3").val(numCheck("${summaryresVo.qrtCnt3}"));
    $("#minVal").val(numCheck("${summaryresVo.minVal}"));
    $("#topVal").val(numCheck("${summaryresVo.topVal}"));
    $("#frqVal").val(numCheck("${summaryresVo.frqVal}"));
});

$(window).on('load',function() {
});


$(window).resize( function(){
});

function numCheck(num) {
	var result = num;
	
	if(!isNaN(result)) {
		//Numeric
		result = result.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}
	
	return result;
}

</script>

<!-- </head> -->
<!-- <body> -->
	<div id="dmn_feature_dtl_div" >
		<div class="stit"><s:message code='SMR.RSLT'/></div><!--컬럼분석 상세정보-->		<!-- Summary 결과 코드값으로 수정 181016 -->

		<div style="clear:both; height:10px;"><span></span></div>
		
	 	<form id="frmdmnfeature" name="frmdmnfeature" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_read" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.ANLY.DTL.INFO'/>">
			   <caption><s:message code="CLMN.ANLY.DTL.INFO"/></caption><!--컬럼분석 상세정보-->
			   <colgroup>
			   <col style="width:40%;" />
			   <col style="width:60%;" />
<%-- 			   <col style="width:20%;" /> --%>
<%-- 			   <col style="width:30%;" /> --%>
			   </colgroup>
			   	   <thead>
			   	   		<tr>
			   	   			<th><s:message code='ITEM'/></th>		<!-- 항목 코드값으로 수정 181016 -->
			   	   			<th><s:message code='VAL2'/></th>		<!-- 값 코드값으로 수정 181016 -->
			   	   		</tr>
			   	   </thead>
			       <tbody>   
			       		<tr>                               
			               <th scope="row"  class=""><s:message code='VRB.TY'/></th>		<!-- 변수타입 코드값으로 수정 181016 -->
			               <!--분석건수-->

			               <td >
			                   <input  type="text"  name="varType" id="varType"  value="${summaryresVo.varType}"  />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class=""><s:message code='WHL.CCNT'/></th>		<!-- 전체건수 코드값으로 수정 181016 -->
			               <!--Null 건수-->
			               <td>
							   <input type="text" name="totCnt" id="totCnt"  value="${summaryresVo.totCnt}" />
			               </td>
			           </tr>
			           <tr>                               
			               <th scope="row"  class=""><s:message code='UNIQUE.CCNT'/></th> <!-- 유니크 건수 코드값으로 수정 181016 -->
			               <!--최소값2-->

			               <td>
			               		<input  type="text"  name="unqVal" id="unqVal"   value="${summaryresVo.unqVal}"  />
			               </td>
			           </tr>
			           <tr>
			                <th scope="row"  class=""><s:message code='AVR'/></th>		<!-- 평균 코드값으로 수정 181016 -->
			                <!--최대값2-->

			               <td>
				               <input  type="text"  name="mnVal" id="mnVal"   value="${summaryresVo.mnVal}"   />
			               </td>
			            </tr>
			       		<tr>                               
			                <th scope="row"  class=""><s:message code='STRD.DVTN'/></th>	<!-- 표준편차 코드값으로 수정 181016 -->
			                <!--최대값3-->

			               <td>
				               <input  type="text"  name="stdDvt" id="stdDvt"   value="${summaryresVo.stdDvt}"  />
			               </td>
			            </tr>                                 
			       		<tr>                               
			               <th scope="row"  class=""><s:message code='MAXV'/></th>	<!-- 최대값 코드값으로 수정 181016 -->
			               <!--최소값1-->

			               <td>
				               <input  type="text"  name="maxVal" id="maxVal"  value="${summaryresVo.maxVal}"  />
			               </td>
			           </tr>
			           <tr>
			               <th scope="row"  class=""><s:message code='QRTL1'/></th>		<!-- 1분위수 코드값으로 수정 181016 -->
			               <!--최소길이-->

			               <td>
				               <input  type="text"  name="qrtCnt1" id="qrtCnt1"   value="${summaryresVo.qrtCnt1}"   />
			               </td>
			           </tr>
			           <tr>                               
			               <th scope="row"  class=""><s:message code='MDNV'/></th>		<!-- 2분위수(중앙값) 코드값으로 수정 181016 -->
			               <!--Space 건수-->
			               <td>
				               <input type="text" name="mdnVal" id="mdnVal"  value="${summaryresVo.mdnVal}"   />
			               </td>
			           </tr>                         
			       		<tr>
			               <th scope="row"  class=""><s:message code='QRTL3'/></th>		<!-- 3분위수 코드값으로 수정 181016 -->
			               <!--최소길이-->

			               <td>
				               <input  type="text"  name="qrtCnt3" id="qrtCnt3"   value="${summaryresVo.qrtCnt3}"   />
			               </td>
			           </tr>                                  
			       		<tr>                               
			               <th scope="row"  class=""><s:message code='MINV'/></th>		<!-- 최소값 코드값으로 수정 181016 -->
			               <!--최소값1-->

			               <td>
				               <input  type="text"  name="minVal" id="minVal"  value="${summaryresVo.minVal}"  />
			               </td>
			           </tr>                         
			       		<tr>
			               <th scope="row"  class=""><s:message code='MODE'/></th>		<!-- 최빈값 코드값으로 수정 181016 -->
			               <!--최대값1-->
			               <td>
				               <input  type="text"  name="topVal" id="topVal"   value="${summaryresVo.topVal}"  />
			               </td>
			           </tr>                         
			       		<tr>
			               <th scope="row"  class=""><s:message code='MODE.CCNT'/></th>		<!-- 최빈값갯수 코드값으로 수정 181016 -->
			               <!--최소값3-->
			               <td>
				               <input  type="text"  name="frqVal" id="frqVal"   value="${summaryresVo.frqVal}"   />
			               </td>
			           </tr>                         
			       </tbody>
			     </table>   
			</div>
			
			</fieldset>
			 
			</form>
	</div>
	
