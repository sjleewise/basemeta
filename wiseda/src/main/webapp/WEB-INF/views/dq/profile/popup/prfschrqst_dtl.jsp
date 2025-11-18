<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>



<!-- 검색조건 입력폼 -->
<div id="search_div">
        
        <div class="stit"><s:message code="REG.TRGT"/></div><!--등록 대상-->
        <div style="clear:both; height:5px;"><span></span></div>
        <form id="frmAnaTrg" name="frmAnaTrg" method="post">
        	
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.BNDL.REG'/>"><!--프로파일 일괄등록-->
                   <caption><s:message code="COND" /></caption><!--조건-->


                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:30%;" />
                   <col style="width:15%;" />
                   <col style="width:40%;" />
                   </colgroup>
                   
                   <tbody>                            
					 <tr>
					 <th scope="row" class="th_require"><label for="prfKndCd"><s:message code="PROF.KIND"/>
						</label></th><!--프로파일종류-->
                      <td>
                          <select id="prfKndCd" class="wd150" name="prfKndCd">
							<option value="PT01"><s:message code="RLT.ANLY"/></option><!--관계분석-->
							<option value="PC01"><s:message code="CLMN.ANLY"/></option><!--컬럼분석-->
							<option value="PC02"><s:message code="CD.ANLY"/></option><!--코드분석-->
							<option value="PC03"><s:message code="DATE.FRMT.ANLY"/></option><!--날짜형식분석-->
							<option value="PC04"><s:message code="RNG.ANLY"/></option><!--범위분석-->
							<option value="PC05"><s:message code="STRING.PTRN.ANLY"/></option><!--문자열패턴분석-->
<%--                                  <c:forEach var="code" items="${codeMap.prfKndCd}" varStatus="status"> --%>
<%-- 								    <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 									</c:forEach> --%>
                          </select>
                      </td>                   
			          <th scope="row" class="th_require"><label for="dbSchPnm"><s:message code="DBMS.SCHEMA.NM"/></label></th><!--진단대상명/스키마명-->


                      <td>
			            <select id="dbConnTrgPnm" class="" name="dbConnTrgPnm">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			            </select>
			            <select id="dbSchPnm" class="" name="dbSchPnm">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			             </select>
			           </td>
			            
                           
                        </tr>
                       <tr>                               
                           <th scope="row" ><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

                           <td>
                               <input type="text" name="dbcTblNm" id="dbcTblNm" />
                           </td>
                           
                           <th scope="row" class="th_require" id="thdbcObjNm" ><label for="objNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

                           <td>
                               <input type="text" name="objNm" id="objNm" />
                           </td>
                           
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:5px;"><span></span></div>
	</div>     

	<div id="profileDtl">
		  	<div id="tblDtl_PT01" style="display:none;">
		  		<%@include file="../dtl/tblrel_dtl.jsp" %>
		  	</div>
		  	<div id="tblDtl_PT02" style="display:none;">
		  		<%@include file="../dtl/tblunq_dtl.jsp" %>
		  	</div>
		  	<div id="colDtl_PC01" style="display:none;">
		  		<%@include file="../dtl/colana_dtl.jsp" %>
		  	</div>
		  	<div id="colDtl_PC02" style="display:none;">
		  		<%@include file="../dtl/colefva_dtl.jsp" %>
		  	</div>
		  	<div id="colDtl_PC03" style="display:none;">
		  		<%@include file="../dtl/coldtfrm_dtl.jsp" %>
		  	</div>
		  	<div id="colDtl_PC04" style="display:none;">
		  		<%@include file="../dtl/colrng_dtl.jsp" %>
		  	</div>
		  	<div id="colDtl_PC05" style="display:none;">
		  		<%@include file="../dtl/colptr_dtl.jsp" %>
		  	</div>
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<button class="btn_search" id="btnModelMartSearch" 	name="btnModelMartSearch" style="text-align: center;"><s:message code="MDEL.MART.LNKD.INQ"/></button><!--모델마트연계 조회--> 
