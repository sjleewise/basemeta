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
});

$(window).on('load',function() {
});


$(window).resize( function(){
});


//진단대상 상세정보
function getDataPattern(){
}

</script>

<!-- </head> -->
<!-- <body> -->
	<div id="searchAnaTrgDtl_div" >
		<div class="stit"><s:message code="DIAG.TRGT.DTL.INFO"/></div><!--진단대상 상세정보-->


		<div style="clear:both; height:5px;"><span></span></div>
		
	 	<form id="frmAnaTrg" name="frmAnaTrg" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>">
			   <caption><s:message code="PROF.MNG"/></caption><!--프로파일 관리-->

			   <colgroup>
			   <col style="width:12%;" />
			   <col style="width:38%;" />
			   <col style="width:12%;" />
			   <col style="width:38%;" />
			   </colgroup>
			       <tbody>   
			       		<tr>                               
			               <th scope="row"  class=""><label for="dbConnTrgLnm"><s:message code="DB.MS" /></label></th><!--진단대상명-->


			               <td>
			                   <input  type="text"  name="dbConnTrgLnm" id="dbConnTrgLnm"  class="b0" readonly />
			               </td>
			               <th scope="row"  class=""><label for="dbSchLnm"><s:message code="SCHEMA.NM" /></label></th><!--스키마명-->

			               <td>
			                   <input type="text" name="dbSchLnm" id="dbSchLnm"  class="b0" readonly />
			               </td>
			           </tr>                         
			           <tr>                               
			               <th scope="row"  class=""><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

			               <td>
			                   <input type="text" name="dbcTblNm" id="dbcTblNm"  class="b0" readonly/>
			               </td>
			               <th scope="row"><label for="dbcTblKorNm"><s:message code="TBL.KRN.NM" /></label></th><!--테이블한글명-->

			               <td>
			                   <input type="text" name="dbcTblKorNm" id="dbcTblKorNm"  class="b0" readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">
			           		<th scope="row"  class=""><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th>	<!--컬럼명-->

			           		<td>
			           			<input type="text" name="dbcColNm" id="dbcColNm"   readonly />
			           		</td>
			           		 <th scope="row"><label for="dbcColKorNm"><s:message code="CLMN.KRN.NM" /></label></th><!--컬럼한글명-->

			               <td>
			                   <input type="text" name="dbcColKorNm" id="dbcColKorNm"   readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">                               
			               <th scope="row"><label for="pkYn">PK</label></th>
			               <td>
			                   <input type="text" name="pkYn" id="pkYn"   readonly/>
			               </td>
			               <th scope="row"><label for="nullYn"><s:message code="NULL.YN" /></label></th><!--Null여부-->
			                <td>
			                   <input type="text" name="nullYn" id="nullYn"   readonly />
			               </td>
			           </tr>
			           <tr id="colPrfTrLayer">                               
			                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th><!--데이터타입-->
			               <td>
			                   <input type="text" name="dataType" id="dataType"  readonly />
			               </td>
			               <th scope="row"><label for="defltVal">DEFAULT</label></th>
			               <td>
			                   <input type="text" name="defltVal" id="defltVal"    readonly/>
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