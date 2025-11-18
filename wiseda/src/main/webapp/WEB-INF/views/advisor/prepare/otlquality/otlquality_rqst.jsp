<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-lite.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart-migr.js"/>'></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#tab-pc03").hide();
		//버튼 이벤트 정의
		$("#btnOtlQltySearch").click(function(e) {
            e.preventDefault();
			doAction("Search");
		});
		
		$("#frmSearch #dbConnTrgId").change(function() {
			$("#frmSearch #dbSchId").find("option").remove().end();
			var val = $("#dbConnTrgId option:selected").val();
			var trgId = ${codeMap.dbSchId};

			$("#frmSearch #dbSchId").append('<option value=""></option>');
			
			for(i=0; i<trgId.length; i++) {
				if(trgId[i].upcodeCd == val) {
					$("#frmSearch #dbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
				}
			}
		});
	});
	
	$(window).on('load',function() {
		//그리드 초기화
		//initGrid();
		initDbmsGrid();
		initTblGrid();
// 		initColGrid();
		initOtlGrid();
		//$(window).resize();
	});

	function doAction(sAction) {
		switch(sAction) {
		case "Search":
			var param = '';
			param = $("#frmSearch").serialize();

			switch($("#tabs").tabs('option','selected')) {
			case 0:
				grid_dbms.DoSearch("<c:url value="/advisor/prepare/otlquality/getDbmsQltyList.do" />", param);
				break;
			case 1:
				grid_tbl.DoSearch("<c:url value="/advisor/prepare/otlquality/getTblQltyList.do" />", param);
				break;
			case 2:
				grid_col.DoSearch("<c:url value="/advisor/prepare/otlquality/getColQltyList.do" />", param);
				break;
			case 3:
				grid_otl.DoSearch("<c:url value="/advisor/prepare/otlquality/getOtlQltyList.do" />", param);
				break;
			break;
			}
		}
	}
</script>

<div style="clear:both; height:10px;"><span></span></div>

<div id="searchTrg_div" >
       
       <form id="frmSearch" name="frmSearch" method="post">
           <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2" >
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.PROF.MNG'/>">
                   <caption><s:message code="CLMN.PROF.MNG"/></caption><!--컬럼프로파일관리-->

                   <colgroup>
	                   <col style="width:10%;" />
	                   <col style="width:25%;" />
	                   <col style="width:10%;" />
	                   <col style="width:25%;" />
	                   <col style="width:5%;" />
                   </colgroup>
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="dbConnTrgId"><s:message code="DBMS.SCHEMA.NM" /></label></th><!--진단대상명-->

                           <td>
                           		<select id="dbConnTrgId"  name="dbConnTrgId" class="wd50p">
	                           		<option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    	<option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
                           		</select>
                           
                           		<select id="dbSchId" name="dbSchId" class="wd48p">
                           		</select>
                               <!-- <input type="text" name="schDbSchNm" id="schDbSchNm" class="wd98p"/> -->
                           </td>
                           
                           <th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th>	<!--테이블명-->

                       		<td>
                       			<%-- <select id="schDbcTblNm" name="schDbcTblNm" class="wd98p">
                           		</select> --%>
                       			<input type="text" name="dbcTblNm" id="schDbcTblNm" class="wd50p"/>
                       		</td>
                       		
                       		<%-- <th scope="row"><label for="dbcColNm"><s:message code="CLMN.NM" /></label></th>	<!--컬럼명-->

                       		<td>
                       			<input type="text" name="dbcColNm" id="schDbcColNm" class="wd98p"/>
                       		</td> --%>
                       		
                       		<td style="text-align:center;">
							    <button class="btn_search" id="btnOtlQltySearch" name="btnOtlQltySearch"><s:message code="INQ" /></button> <!-- 조회 -->
							</td>
                       	</tr>
                       	<tr>
							<th scope="row"><label for="algNm"><s:message code="ALG.NM" /></label></th>	<!--알고리즘명-->

                       		<td colspan="6">
                       			<!-- <input type="text" name="algNm" id="algNm" style="width:30%"/> -->
                       			<select id="otlAlgId"  name="otlAlgId" style="width:30%;">
                           		<option value=""><s:message code='MSG.OTLQUALITY.RQST' /></option><!--알고리즘을 선택하세요.-->
							    <c:forEach var="code" items="${codeMap.otlAlgCd}" varStatus="status">
							    <option value="${code.codeCd}">${code.codeLnm}</option>
							    </c:forEach>
                           		</select>
                       		</td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
           </fieldset>
       </form>
</div>

<div style="clear:both; height:15px;"><span></span></div>

<div id="tabs">
	  <ul>

	    <li id="tab-pc01"><a href="#tabs-pc01"><s:message code='DIAG.TRGT.PRES' /></a></li><!--진단대상별 현황 181022 -->
	    <li id="tab-pc02"><a href="#tabs-pc02"><s:message code='DIAG.TRGT.ALG' /></a></li><!--알고리즘별 현황 181022 -->
	    <li id="tab-pc03"><a href="#tabs-pc03"><s:message code='DIAG.TRGT.TBL.ALG' /></a></li><!--컬럼별 알고리즘 현황 181022 -->
	    <li id="tab-pc04"><a href="#tabs-pc04"><s:message code='DIAG.TRGT.TBL.ALG' /></a></li><!--테이블별 알고리즘 현황 181022 -->

	    <!-- <li id="tab-pc01"><a href="#tabs-pc01">진단대상별 현황</a></li>진단대상별
	    <li id="tab-pc02"><a href="#tabs-pc02">테이블별 현황</a></li>테이블별
	    <li id="tab-pc03"><a href="#tabs-pc03">컬럼별 현황</a></li>컬럼별
	    <li id="tab-pc04"><a href="#tabs-pc04">알고리즘별 현황</a></li>알고리즘별 -->

	  </ul>
	  <div id="tabs-pc01">
		<div style="clear:both; height:5px;"><span></span></div>
		<%@include file="dbms_dtl.jsp" %>
	  </div>
	  <div id="tabs-pc02">
		<div style="clear:both; height:5px;"><span></span></div>
		<%@include file="tbl_dtl.jsp" %>
	  </div>
	  <div id="tabs-pc03">
		<div style="clear:both; height:5px;"><span></span></div>
<%-- 		<%@include file="col_dtl.jsp" %> --%>
	  </div>
	  <div id="tabs-pc04">
		<div style="clear:both; height:5px;"><span></span></div>
		<%@include file="otl_dtl.jsp" %>
	  </div>
	  <%-- <div id="tabs-pc02">
		<div style="clear:both; height:5px;"><span></span></div>	       
		<div id="grid_02" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_alg", "99%", "250px");</script>
		</div>
	  </div>
	  <div id="tabs-pc03">
		<div style="clear:both; height:5px;"><span></span></div>	       
		<div id="grid_03" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_tbl", "99%", "250px");</script>
		</div>
	  </div> --%>
 </div>