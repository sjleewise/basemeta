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

</script>
<!-- </head> -->
<!-- <body> -->
	  	<form name="frmDetail" id="frmDetail" method="post" action="">
			<div style="clear:both; height:5px;"><span></span></div>
			<%-- <div id="valid_bizrule" style="display: none;">
			<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
			<div style="clear:both; height:10px;"><span></span></div>
			</div> --%>
			<div class="tb_basic2">
	  			<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" />
	  			<input type="hidden" id="cdRuleId" name="cdRuleId" value="${result.cdRuleId}" />
			
				
				<div class="stit">코드 기본정보</div><!-- 코드 기본정보 -->
				<table border="0" cellspacing="0" cellpadding="0"  summary="" height="150">
		        <caption>코드 기본정보</caption><!-- 코드 기본정보 -->
		        <colgroup>
		            <col style="width:8%;">
		            <col style="width:42%;">
		            <col style="width:8%;">
		            <col style="width:42%;">
		        </colgroup>
			      <tr>
			        <th class="th_require">검증코드명</th><!-- 업무규칙명 -->
			        <td>
                    	<input type="text" class="wd45p" id="cdRuleNm" name="cdRuleNm" value="${result.cdRuleNm }" readonly/>
			        </td>		
			        
			        <th scope="row" class="th_require" rowspan="5">코드생성SQL</th>
			        <td rowspan="5">
				        <textarea style="height:180px;width:98%;word-spacing:5px;"  id="cdSql" name="cdSql" accesskey="" readonly>${result.cdSql }</textarea>
				        <div style="clear:both; height:5px;"><span></span></div>
				        <button class="btn_frm_save" type="button" id="btnCheck" name="btnCheck" style="margin-left: 45%;">검증</button>
			        </td>
			        	        			        		      
			      </tr>
			      
			      <tr>			       
			        <th class="th_require">DB명</th><!-- DB명 -->
			        <td>
				        <select id="dbConnTrgId"  name="dbConnTrgId" class="wd150">
                       		<option value=""><s:message code="WHL" /></option><!--전체-->
	
						    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
						    <option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>
                     	</select>
		           </td>			        			       
			      </tr>
			      
			      <tr>			       
			        <th class="th_require">코드유형</th>
			        <td>
				        <select id="cdTypCd"  name="cdTypCd" class="wd150">
                       		<option value=""><s:message code="WHL" /></option><!--전체-->
	
						    <c:forEach var="code" items="${codeMap.cdTypCd}" varStatus="status">
						    <option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>
                     	</select>
		           </td>			        			       
			      </tr>
			      
			      <tr>			       
			        <th>설명</th>
			        <td>
			        	<textarea style="height:90px;width:98%;word-spacing:5px;"  id="objDescn" name="objDescn" accesskey="" readonly>${result.objDescn }</textarea>
			        </td>  						       
			      </tr>
			      
			     
			      <!-- 
			      <tr>
			      	<th class="th_require">컬럼구분</th>
			      	<td>
			      		<table border="0" cellspacing="0" cellpadding="0"  summary="" style="height:95%;">
			      			<colgroup>
			      				<col style="width:15%;">
			      				<col style="width:85%;">
			      			</colgroup>
			      			<tr>
			      				<th class="th_require">컬럼구분</th>
			      				<th class="th_require">컬럼명</th>
			      			</tr>
			      			<tr>
			      				<td>분류코드</td>
			      				<td><input class="wd70p" type="text" name="cdClsColNm" id="cdClsColNm"/></td>
			      			</tr>
			      			<tr>
			      				<td>분류코드명</td>
			      				<td><input class="wd70p" type="text" name="cdClsNmColNm" id="cdClsNmColNm"/></td>
			      			</tr>
			      			<tr>
			      				<td>공통코드</td>
			      				<td><input class="wd70p" type="text" name="cdIdColNm" id="cdIdColNm"/></td>
			      			</tr>
			      			<tr>
			      				<td>공통코드명</td>
			      				<td><input class="wd70p" type="text" name="cdNmColNm" id="cdNmColNm"/></td>
			      			</tr>
			      		</table>
			      						      
			      	</td>
			      	
			      </tr>
			       -->
			      </table>
			</div>
			    </form>
			<%-- <div style="clear:both; height:10px;"><span></span></div>
			<div id="otherinfo" style="display:none;"><%@include file="../../meta/stnd/otherinfo.jsp" %></div> --%>

			<div style="clear:both; height:10px;"><span></span></div>
			<div id="buttonRqst" style="display: none;">
			<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
			</div>
<!-- </body> -->
<!-- </html> -->