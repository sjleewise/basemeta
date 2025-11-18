<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->

<script type="text/javascript">

$(document).ready(function() {
	
	$("#btnDataPtn").click(function(event){
    	event.preventDefault();	//브라우저 기본 이벤트 제거...
	}).parent().buttonset();
	
	$("#anaCnt").val(numCheck("${colAnaResVO.anaCnt}"));
	$("#nullCnt").val(numCheck("${colAnaResVO.nullCnt}"));
	$("#spaceCnt").val(numCheck("${colAnaResVO.spaceCnt}"));
	$("#minLen").val(numCheck("${colAnaResVO.minLen}"));
	$("#maxLen").val(numCheck("${colAnaResVO.maxLen}"));
	
// 	$("#frmColAna input[type=text]").css("border-color","transparent").css("width", "98%").attr("readonly", true);
    $("#frmColAna input[type=text]").attr("readonly", true);
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
	<div id="searchAnaResDtl_div" >
		<div class="stit"><s:message code="CLMN.ANLY.DTL.INFO"/></div><!--컬럼분석 상세정보-->

		<div style="clear:both; height:10px;"><span></span></div>
		
	 	<form id="frmColAna" name="frmColAna" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_read" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.ANLY.DTL.INFO'/>">
			   <caption><s:message code="CLMN.ANLY.DTL.INFO"/></caption><!--컬럼분석 상세정보-->
			   <colgroup>
			   <col style="width:20%;" />
			   <col style="width:30%;" />
			   <col style="width:20%;" />
			   <col style="width:30%;" />
			   </colgroup>
			       <tbody>   
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="ANLY.CCNT" /></th><!--분석건수-->

			               <td colspan="3">
			               		<input  type="text"  name="anaCnt" id="anaCnt"  value="${colAnaResVO.anaCnt}"  />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="NULL.CCNT"/></th><!--Null 건수-->
			               <td>
				               <input type="text" name="nullCnt" id="nullCnt"  value="${colAnaResVO.nullCnt}"   />
			               </td>
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="SPACE.CCNT"/></th><!--Space 건수-->
			               <td>
				               <input type="text" name="spaceCnt" id="spaceCnt"  value="${colAnaResVO.spaceCnt}"   />
			               </td>
			           </tr>                         
			       		<!-- 
			       		<tr>                               
			               <th scope="row"  class=""><s:message code="DATA.PTRN.INQ"/></th>
			               <td colspan="3">
			                   메타유효값 조회 버튼
							<button class="btn_search" id="btnDataPtn" 	name="btnDataPtn"><s:message code="DATA.PTRN.INQ"/></button>
			               </td>
			           </tr>         
			           -->                 
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="MIN.VAL.1"/></th><!--최소값1-->

			               <td>
				               <input  type="text"  name="minVal1" id="minVal1"  value="${colAnaResVO.minVal1}"  />
			               </td>
			                <th scope="row"  class=""><s:message code="MAX.VAL.1"/></th><!--최대값1-->
			               <td>
				               <input  type="text"  name="maxVal1" id="maxVal1"   value="${colAnaResVO.maxVal1}"  />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="MIN.VAL.2"/></th><!--최소값2-->

			               <td>
				               <input  type="text"  name="minVal2" id="minVal2"   value="${colAnaResVO.minVal2}"  />
			               </td>
			                <th scope="row"  class="" nowrap="nowrap"><s:message code="MAX.VAL.2"/></th><!--최대값2-->

			               <td>
				               <input  type="text"  name="maxVal2" id="maxVal2"   value="${colAnaResVO.maxVal2}"   />
			               </td>
			            </tr>
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="MIN.VAL.3"/></th><!--최소값3-->
			               <td>
				               <input  type="text"  name="minVal3" id="minVal3"   value="${colAnaResVO.minVal3}"   />
			               </td>
			                <th scope="row"  class="" nowrap="nowrap"><s:message code="MAX.VAL.3"/></th><!--최대값3-->

			               <td>
				               <input  type="text"  name="maxVal3" id="maxVal3"   value="${colAnaResVO.maxVal3}"  />
			               </td>
			            </tr>
			            
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="MIN.LNGT" /></th><!--최소길이-->

			               <td>
				               <input  type="text"  name="minLen" id="minLen"   value="${colAnaResVO.minLen}"   />
			               </td>
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="MAX.LNGT" /></th><!--최대길이-->

			               <td>
				               <input  type="text"  name="maxLen" id="maxLen"   value="${colAnaResVO.maxLen}"  />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="STRD.DVTN" /></th><!--표준편차-->

			               <td>
				               <input  type="text"  name="stddevVal" id="stddevVal"   value="${colAnaResVO.stddevVal}"   />
			               </td>
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="VARIANCE.VAL" /></th><!--분산-->

			               <td>
				               <input  type="text"  name="varianceVal" id="varianceVal"   value="${colAnaResVO.varianceVal}"  />
			               </td>
			           </tr>                         
			           
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="AVR" /></th><!--평균-->

			               <td>
				               <input  type="text"  name="avgVal" id="avgVal"   value="${colAnaResVO.avgVal}"   />
			               </td>
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="UNQ.CNT" /></th><!--유일값수-->

			               <td>
				               <input  type="text"  name="unqCnt" id="unqCnt"   value="${colAnaResVO.unqCnt}"  />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="MIN.CNT.VAL" /></th><!--최소빈도값-->

			               <td>
				               <input  type="text"  name="minCntVal" id="minCntVal"   value="${colAnaResVO.minCntVal}"   />
			               </td>
			               <th scope="row"  class="" nowrap="nowrap"><s:message code="MAX.CNT.VAL" /></th><!--최대빈도값-->

			               <td>
				               <input  type="text"  name="maxCntVal" id="maxCntVal"   value="${colAnaResVO.maxCntVal}"  />
			               </td>
			           </tr>                         
			       </tbody>
			     </table>   
			</div>
			
			<div style="clear:both; height:10px;"><span></span></div>
			<div class="stit"><s:message code="RECENTLY.ANLY.SPEC.INFO1"/></div><!--최근분석실행 상세정보-->

			<div style="clear:both; height:5px;"><span></span></div>
			
			<div class="tb_read" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.ANLY.DTL.INFO'/>">
			   <caption><s:message code="CLMN.ANLY.DTL.INFO"/></caption><!--컬럼분석 상세정보-->

			   <colgroup>
			   <col style="width:20%;" />
			   <col style="width:80%;" />
			   </colgroup>
			       <tbody>
			       		<tr>
			               <th scope="row"  class=""><s:message code="ANLY.ODR" /></th><!--분석차수-->

			               <td>
			                   <input  type="text"  name="anaDgr" id="anaDgr"  value="${colAnaResVO.anaDgr}"   />
			               </td>
			            </tr>
			            <tr>                       
			       			<th scope="row"  class=""><s:message code="ANLY.DTTM" /></th><!--분석일시-->

			               <td>
<%-- 			                   <input  type="text"  name="prfId" id="prfId"  value="${colAnaResVO.prfId}"  /> --%>
			                   <input  type="text"  name="AnaStrDtm" id="AnaStrDtm"  value="${colAnaResVO.schAnaStrDtm}"  />
			               </td>
			            </tr>
			            <tr>
			             
			               <th scope="row"  class=""><s:message code="ANALIYST"/></th><!--분석자-->

			               <td>
			                   <input  type="text"  name="anaUserNm" id="anaUserNm"  value="${colAnaResVO.anaUserNm}"  />
			               </td>
			           </tr>
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			 
			</form>
	</div>
	

<!-- </body> -->
<!-- </html> -->