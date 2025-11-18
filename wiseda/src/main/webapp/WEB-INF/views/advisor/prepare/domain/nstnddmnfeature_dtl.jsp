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
	
// 	$("#frmnstnddmnfeature input[type=text]").css("border-color","transparent").css("width", "98%").attr("readonly", true);
    $("#frmnstnddmnfeature input[type=text]").attr("readonly", true);
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
		
	 	<form id="frmnstnddmnfeature" name="frmnstnddmnfeature" method="post">
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
			                   <input  type="text"  name="dbColNm" id="dbColNm"  value="${nstnddmnfeatureVo.dbColNm}"  />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="">컬럼형태소논리명</th><!--Null 건수-->
			               <td>
			                   <input type="text" name="colMrpLnm" id="colMrpLnm"  value="${nstnddmnfeatureVo.colMrpLnm}"   />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="">컬럼형태소물리명</th><!--Space 건수-->
			               <td>
			                   <input type="text" name="colMrpPnm" id="colMrpPnm"  value="${nstnddmnfeatureVo.colMrpPnm}"   />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="">데이터타입</th><!--최소값1-->

			               <td>
			                   <input  type="text"  name="dataType" id="dataType"  value="${nstnddmnfeatureVo.dataType}"  />
			               </td>
			           </tr>                         
			       		<tr>
			               <th scope="row"  class="">PK여부</th><!--최소값3-->
			               <td>
			                   <input  type="text"  name="pkYn" id="pkYn"   value="${nstnddmnfeatureVo.pkYn}"   />
			               </td>
			           </tr>                         
			       		<tr>                               
			               <th scope="row"  class="">최대길이</th><!--최소값2-->

			               <td>
			                   <input  type="text"  name="maxlen" id="maxlen"   value="${nstnddmnfeatureVo.maxLen}"  />
			               </td>
			           </tr>                         
			       		<tr>
			                <th scope="row"  class="">최소길이</th><!--최대값2-->

			               <td>
			                   <input  type="text"  name="minLen" id="minLen"   value="${nstnddmnfeatureVo.minLen}"   />
			               </td>
			            </tr>
			       		<tr>                               
			                <th scope="row"  class="">일자여부</th><!--최대값3-->

			               <td>
			                   <input  type="text"  name="dtYn" id="dtYn"   value="${nstnddmnfeatureVo.dtYn}"  />
			               </td>
			            </tr>                         
			       		<tr>
			               <th scope="row"  class="">전화번호여부</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="telYn" id="telYn"   value="${nstnddmnfeatureVo.telYn}"   />
			               </td>
			           </tr>                         
			           <tr>
			               <th scope="row"  class="">공배율</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="spaceRt" id="spaceRt"   value="${nstnddmnfeatureVo.spaceRt}"   />
			               </td>
			           </tr>
			           <tr>
			               <th scope="row"  class="">엔터값여부</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="crlfYn" id="crlfYn"   value="${nstnddmnfeatureVo.crlfYn}"   />
			               </td>
			           </tr>
			           <tr>
			               <th scope="row"  class="">영문여부</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="alphaYn" id="alphaYn"   value="${nstnddmnfeatureVo.alphaYn}"   />
			               </td>
			           </tr>
			           <%-- <tr>
			               <th scope="row"  class="">데이터포맷</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="dataFmt" id="dataFmt"   value="${nstnddmnfeatureVo.dataFmt}"   />
			               </td>
			           </tr> --%>
			           <tr>
			               <th scope="row"  class="">숫자여부</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="numYn" id="numYn"   value="${nstnddmnfeatureVo.numYn}"   />
			               </td>
			           </tr>
			           <tr>
			               <th scope="row"  class="">백단위율</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="hundRt" id="hundRt"   value="${nstnddmnfeatureVo.hundRt}"   />
			               </td>
			           </tr>
			           <tr>
			               <th scope="row"  class="">건수율</th><!--최소길이-->

			               <td>
			                   <input  type="text"  name="cntRt" id="cntRt"   value="${nstnddmnfeatureVo.cntRt}"   />
			               </td>
			           </tr>
			       </tbody>
			     </table>   
			</div>
			
			</fieldset>
			 
			</form>
	</div>
	
