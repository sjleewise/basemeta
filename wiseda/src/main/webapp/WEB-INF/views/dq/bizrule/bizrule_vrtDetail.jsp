<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
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
<!-- <title>업무규칙조회 기본정보</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
	//검증대상명 검색 팝업
	$('#btnVrtDbPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	var url = '<c:url value="/dq/criinfo/anatrg/popup/dbconntrg_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=VRT", "vrtDbPop",  900, 670, "no");
				popwin.focus();
	});

	//검증테이블명 검색 팝업
	$('#btnVrtTblPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
// 		    	var param = $("form[name=frmVrtDetail]").serialize();
		    	var param = "&dbConnTrgId="+$("form#frmVrtDetail #tgtDbConnTrgId").val();
    			param += "&dbSchPnm="+$("form#frmVrtDetail #tgtDbSchPnm").val();
		    	var url = '<c:url value="/dq/criinfo/anatrg/popup/tbllst_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=VRTTBL"+param, "vrtTblPop",  700, 800, "no");
				popwin.focus();
	});

	//검증컬럼명 검색 팝업
	$('#btnVrtColPop').click(function(event){
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		    	
		    	var param = "&dbConnTrgId="+$("form#frmVrtDetail #tgtDbConnTrgId").val();
		    			param += "&dbSchPnm="+$("form#frmVrtDetail #tgtDbSchPnm").val();
		    			param += "&dbcTblNm="+$("form#frmVrtDetail #tgtDbcTblNm").val();
		    			
		    	var url = '<c:url value="/dq/profile/popup/collst_pop.do"/>';
		    	var popwin = OpenModal(url+"?sflag=VRTCOL"+param, "vrtColPop",  700, 900, "no");
				popwin.focus();
	});
	
	
// 	 setCodeSelect("TGT_VRF_JOIN_CD", "C", $("form[name=frmVrtDetail] #tgtVrfJoinCd"));
	 
	 $("#frmVrtDetail #tgtVrfJoinCd").val('${result.tgtVrfJoinCd}');
	 
// 	 setCodeSelect("TGT_VRF_JOIN_CD", "C", $("form[name=frmVrtDetail] #tgtVrfJoinCd"));
	 
// 	 $("#frmVrtDetail #tgtVrfJoinCd").val('${result.tgtVrfJoinCd}'); 
});

</script>
<!-- </head> -->
<!-- <body> -->
	  	<div class="stit"><s:message code="BZWR.RGR.COMPARE.VRFC.INFO"/></div><!--업무규칙 비교검증정보-->

			<div style="clear:both; height:5px;"><span></span></div>
			<div class="tb_basic">
			<form name="frmVrtDetail" id="frmVrtDetail" method="post" action="">
				<input type="hidden" id="tgtDbConnTrgId" name="tgtDbConnTrgId" class="input"  value="${result.tgtDbConnTrgId }" readonly/>
				<input type="hidden" id="tgtDbSchId" name="tgtDbSchId" value="${result.dbSchId}"/>
	            <input type="hidden" id="tgtDbConnTrgLnm" name="tgtDbConnTrgLnm" value="${result.dbConnTrgLnm}"/>
				<table border="0" cellspacing="0" cellpadding="0"  summary="">
		        <caption>
		        <s:message code="BZWR.RGR.COMPARE.VRFC.INFO"/>
		        </caption><!--업무규칙 비교검증정보-->

		        <colgroup>
		           <col style="width:10%;">
		            <col style="width:26%;">
		            <col style="width:10%;">
		            <col style="width:22%;">
		            <col style="width:10%;">
		            <col style="width:22%;">
		        </colgroup>
			      <tr>
			        <th  class="th_require"><s:message code="VRFC.TRGT.DB.NM"/></th><!--검증대상DB명-->
			        <td>
			        	<input type="text" id="tgtDbConnTrgPnm" name="tgtDbConnTrgPnm" class="input"  value="${result.dbConnTrgPnm }" readonly/>
			        	<input type="text" id="tgtDbSchPnm" name="tgtDbSchPnm" class="input"  value="${result.dbSchPnm }" readonly/>
			        	<button class="btnSearchPop" id="btnVrtDbPop"><s:message code="SRCH" /></button><!--검색-->
			        </td>
			         	<th  class="th_require"><s:message code="VRFC.TRGT.TBL.NM"/></th><!--검증대상테이블명-->
				        <td>
	                  		<input type="text" id="tgtDbcTblNm" name="tgtDbcTblNm" value="${result.tgtDbcTblNm }" readonly/>
	                  		<button class="btnSearchPop" id="btnVrtTblPop"><s:message code="SRCH" /></button><!--검색-->
				        </td>
				        <th  class="th_require"><s:message code="VRFC.TRGT.CLMN.NM"/></th><!--검증대상컬럼명-->
				        <td>
	                   		<input type="text" id="tgtDbcColNm" name="tgtDbcColNm" value="${result.tgtDbcColNm }" readonly/>
	                   		<button class="btnSearchPop" id="btnVrtColPop"><s:message code="SRCH" /></button><!--검색-->
				        </td>
			      </tr>
			      <tr>
			        <th  class="th_require"><s:message code="VRFC.COMPARE.KEY.CLMN"/></th><!--검증비교KEY컬럼-->
			        <td>
			        	<input type="text" id="tgtKeyDbcColNm" name="tgtKeyDbcColNm" class="input"  value="${result.tgtKeyDbcColNm }" readonly/>
			        </td>
			         	<th  class="th_require"><s:message code="VRFC.COMPARE.VAL.CLMN"/></th><!--검증비교값컬럼-->
				        <td>
	                  		<input type="text" id="tgtKeyDbcColVal" name="tgtKeyDbcColVal" value="${result.tgtKeyDbcColVal }" readonly/>
				        </td>
				        <th  class="th_require"><s:message code="VRFC.JOIN.MTHD.2"/></th><!--검증JOIN방식-->
				        <td>
                          <select id="tgtVrfJoinCd"  name="tgtVrfJoinCd">
						    <option selected="" value=""><s:message code="CHC" /></option><!--선택-->

					                	<c:forEach var="code" items="${codeMap.tgtVrfJoinCd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
						</select>
<!-- 	                   		<input type="text" id="tgtVrfJoinCd" name="tgtVrfJoinCd" readonly/> -->
				        </td>
			      </tr>
			      <tr>
			      <th  class="th_require"><s:message code="CCNT.SQL.VRFC"/></th><!--건수SQL(검증)--></th>
			        <td>
			        	<textarea style="height:130px;width:99%;"  id="tgtCntSql" name="tgtCntSql" accesskey="" readonly>${result.tgtCntSql }</textarea>
			        </td>
			        <th  class="th_require"><s:message code="ANLY.SQL.VRFC"/></th><!--분석SQL(검증)-->

			        <td colspan="4">
			        	<textarea style="height:130px;width:99%;"  id="tgtAnaSql" name="tgtAnaSql" accesskey="" readonly>${result.tgtAnaSql }</textarea>
			        </td>
			      </tr>
			    </table>
			    </form>
			</div>
			<div style="clear:both; height:5px;"><span></span></div>
			<div id="buttonRqstVrtDetail" style="text-align: center;display: none;">
			<button class="btn_frm_save" type="button" id="btnGridSave2" name="btnGridSave2"><s:message code="STRG" /></button><!--저장-->

 
            <button class="btn_frm_reset" type="button" id="btnReset2" name="btnReset2"><s:message code="INON" /></button><!--초기화-->

			</div>
<!-- </body> -->
<!-- </html> -->