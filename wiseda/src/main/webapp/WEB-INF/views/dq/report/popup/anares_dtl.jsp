<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	$("#frmAnaRes input[type=text]").css("border-color","transparent").css("width", "98%").attr("readonly", true);
});

$(window).on('load',function() {
});


$(window).resize( function(){
});

</script>

<!-- </head> -->
<!-- <body> -->
	<div id="searchAnaTrgDtl_div" >
	    <div class="stit"><s:message code="ANLY.RSLT.DTL.INFO"/></div><!--분석결과 상세정보-->

		<div style="clear:both; height:5px;"><span></span></div>
		
	 	<form id="frmAnaRes" name="frmAnaRes" method="post">
	 	<fieldset>
	    <legend><s:message code="ANLY.RSLT.DTL.INFO"/></legend><!--분석결과 상세정보-->

		<div class="tb_basic" >
			
			     <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='ANLY.RSLT.DTL.INFO'/>"><!-- /*분석결과 상세정보*/ -->

					<caption><s:message code="ANLY.RSLT.DTL.INFO"/></caption><!--분석결과 상세정보-->

					<colgroup>
					 <col style="width:20%;" />
			   		 <col style="width:80%;" />
					</colgroup>
			       <tbody>   
			       		<tr>                               
			               <th scope="row" ><s:message code="ANLY.CCNT" /></th><!--분석건수-->

			               <td>
			                   <input  type="text"  name="anaCnt" id="anaCnt" value="${anaResVO.anaCnt}" />
			               </td>
			           </tr>                         
			           <tr>
			               <th scope="row" ><s:message code="ESLO.EROR.CCNT"/></th><!--추정오류건수-->

			               <td>
			                   <input type="text" name="erCnt" id="erCnt"  value="${anaResVO.erCnt}"/>
			               </td>
			           </tr>                         
			           <tr>                               
			               <th scope="row"><s:message code="ESLO.EROR.RT"/></th><!--추정오류율(%)-->
			               <td>
			                   <input type="text" name="erRate" id="erRate"  value="${anaResVO.erRate}" />
			               </td>
			           </tr>
			           <tr>
			               <th scope="row"  class=""><s:message code="ANLY.ODR" /></th><!--분석차수-->

			               <td>
			                   <input  type="text"  name="anaDgr" id="anaDgr"   value="${anaResVO.anaDgr}" />
			               </td>
			            </tr>
			            <tr>                       
			       			<th scope="row"  class=""><s:message code="ANLY.STRN.DTTM"/></th><!--분석시작일시-->

			               <td>
			                   <input  type="text"  name="anaStrDtm" id="anaStrDtm" value="${anaResVO.anaStrDtm}" />
			               </td>
			            </tr>
			            <tr>                       
			       			<th scope="row"  class=""><s:message code="ANLY.END.DTTM"/></th><!--분석종료일시-->

			               <td>
			                   <input  type="text"  name="anaEndDtm" id="anaEndDtm" value="${anaResVO.anaEndDtm}" />
			               </td>
			            </tr>
			            <tr>
			               <th scope="row"  class=""><s:message code="ANALIYST"/></th><!--분석자-->

			               <td>
			                   <input  type="text"  name="anaUserNm" id="anaUserNm" value="${anaResVO.anaUserNm}" />
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