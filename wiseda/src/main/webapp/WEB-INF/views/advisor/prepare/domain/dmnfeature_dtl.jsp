<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
});

$(window).on('load',function() {
});


$(window).resize( function(){
});

</script>

<!-- </head> -->
<!-- <body> -->
	<div id="dmn_feature_dtl_div" >
		<div class="stit">도메인판별 파생변수</div><!--컬럼분석 상세정보-->

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
			       <tbody>   
			       		<tr>                               
			               <th scope="row"  class="">컬럼명</th><!--분석건수-->

			               <td >
			                   <input  type="text"  name="dbColNm" id="dbColNm"  value="${dmnfeatureVo.dbColNm}"  />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="">컬럼형태소논리명</th><!--Null 건수-->
			               <td>
			                   <input type="text" name="colMrpLnm" id="colMrpLnm"  value="${dmnfeatureVo.colMrpLnm}"   />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="">컬럼형태소물리명</th><!--Space 건수-->
			               <td>
			                   <input type="text" name="colMrpPnm" id="colMrpPnm"  value="${dmnfeatureVo.colMrpPnm}"   />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="">데이터타입</th><!--최소값1-->

			               <td>
			                   <input  type="text"  name="dataType" id="dataType"  value="${dmnfeatureVo.dataType}"  />
			               </td>
			           </tr>                         
			       		<tr>
			               <th scope="row"  class="">날짜여부</th><!--최소값3-->
			               <td>
			                   <input  type="text"  name="dtYn" id="dtYn"   value="${dmnfeatureVo.dtYn}"   />
			               </td>
			               <%--  <th scope="row"  class="">변수타입</th><!--최대값1-->
			               <td>
			                   <input  type="text"  name="varType" id="varType"   value="${dmnfeatureVo.varType}"  />
			               </td> --%>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="">소수점여부</th><!--최소값2-->

			               <td>
			                   <input  type="text"  name="fpoYn" id="fpoYn"   value="${dmnfeatureVo.fpoYn}"  />
			               </td>
			           </tr>                         
			       		<tr>
			                <th scope="row"  class="">숫자여부</th><!--최대값2-->

			               <td>
			                   <input  type="text"  name="nmbYn" id="nmbYn"   value="${dmnfeatureVo.nmbYn}"   />
			               </td>
			            </tr>
			       		<tr>                               
			                <th scope="row"  class="">길이변경여부</th><!--최대값3-->

			               <td>
			                   <input  type="text"  name="lenChgYn" id="lenChgYn"   value="${dmnfeatureVo.lenChgYn}"  />
			               </td>
			            </tr>                         
			       		<tr>
			               <th scope="row"  class="">길이초과여부</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="dataLenExcYn" id="dataLenExcYn"   value="${dmnfeatureVo.dataLenExcYn}"   />
			               </td>
			           </tr>                         
			           
			       </tbody>
			     </table>   
			</div>
			
			</fieldset>
			 
			</form>
	</div>
	
