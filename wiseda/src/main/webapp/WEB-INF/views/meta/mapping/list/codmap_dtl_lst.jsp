<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>





<!-- 입력폼 시작 -->
<form name="frmInput" id="frmInput" method="post" >
<div id="input_form_div">
<fieldset>
	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit"><s:message code="MAPG.DFNT.P.INFO"/></div> <!-- 매핑정의서 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_read">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:10%;" />
               <col style="width:13%;" />
               <col style="width:10%;" />
               <col style="width:17%;" />
               <col style="width:10%;" />
               <col style="width:12%;" />
               <col style="width:10%;" />
               </colgroup>
               <tbody>
				 <tr>
				 	   <th scope="row"><label for="mapDfType"><s:message code="MAPG.DFNT.P.PTRN" /></label></th> <!-- 매핑정의서유형 -->
                       <td>
                       	<select id="mapDfType"  name="mapDfType"  disabled="disabled">
							 <c:forEach var="code" items="${codeMap.mapDfTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                       
                       <th scope="row"><label for="cdCnvsType"><s:message code="CD.CNVR.PTRN" />
</label></th> <!-- 코드전환유형 -->
                       <td>
                       	<select id="cdCnvsType"  name="cdCnvsType" disabled="disabled" >
							 <c:forEach var="code" items="${codeMap.cdCnvsTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                       
                       <th scope="row"><label for="cdMapType"><s:message code="CD.MAPG.PTRN" />
</label></th> <!-- 코드매핑유형 -->
                       <td>
                       	<select id="cdMapType"  name="cdMapType" disabled="disabled">
							 <c:forEach var="code" items="${codeMap.codMapTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                   </tr>
                </tbody>
           </table>
       </div>
                   
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit"><s:message code="TARG.TOBE.INFO"/></div> <!-- 타겟(TOBE) 정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       	<div class="tb_read">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                  
                   <tr>
                   		<th scope="row"><label for="tgtDmnLnm"><s:message code="TARG.CD.DMN.NM.1"/></label></th> <!-- 타겟(TOBE)코드도메인명 -->
                       	<td colspan="3">
                       		<input type="text" id="tgtDmnLnm" name="tgtDmnLnm" value="${result.tgtDmnLnm}"  class="wd98p" readOnly />
                       	</td>
                   </tr>
                   <tr>
                       	<th scope="row"><label for="tgtCdVal"><s:message code="TARG.CD.VAL"/></label></th> <!-- 타겟(TOBE) 코드값 -->
                       	<td><input type="text" id="tgtCdVal" name="tgtCdVal" value="${result.tgtCdVal}"   class="wd98p" readOnly/></td>
                       	
                       	<th scope="row"><label for="tgtCdValNm"><s:message code="TARG.CD.NM"/></label></th> <!-- 타겟(TOBE) 코드명 -->
                       	<td><input type="text" id="tgtCdValNm" name="tgtCdValNm" value="${result.tgtCdValNm}"  class="wd98p" readOnly /></td>
                   </tr>
			  </tbody>
	           </table>   
	       </div>            
                
                
			<div style="clear:both; height:10px;"><span></span></div>
			<div class="stit"><s:message code="SOUR.INFO"/></div> <!-- 소스(ASIS) 정보 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
		       <div class="tb_read">
		           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>      
                  <tr>
                       	<th scope="row"><label for="srcSysNm"><s:message code="SOUR.SYS.NM"/></label></th> <!-- 소스(ASIS)시스템명 -->
                       	<td ><input type="text" id="srcSysNm" name="srcSysNm" value="${result.srcSysNm}"  class="wd98p" readOnly /></td>
                       	<th scope="row"><label for="srcBizNm"><s:message code="SOUR.BZWR.NM"/></label></th> <!-- 소스(ASIS)업무명 -->
                       	<td ><input type="text" id="srcBizNm" name="srcBizNm" value="${result.srcBizNm}"   class="wd98p" readOnly/></td>
                  </tr>
                  <tr>
                   		<th scope="row"><label for="srcDbPnm"><s:message code="SOUR.ASIS.DB.NM.1"/></label></th> <!-- 소스(ASIS)DB명 -->
                       	<td colspan="3"><input type="text" id="srcDbPnm" name="srcDbPnm" value="${result.srcDbPnm}"   class="wd98p" readOnly/></td>
                   </tr>
                   
                   <tr>
                       	<th scope="row"><label for="srcTblPnm"><s:message code="SOUR.TBL.PHYC"/></label></th> <!-- 소스(ASIS)테이블물리명 -->
                       	<td ><input type="text" id="srcTblPnm" name="srcTblPnm" value="${result.srcTblPnm}" class="wd98p" readOnly /></td>
                       	<th scope="row"><label for="srcTblLnm"><s:message code="SOUR.TBL.LGC.NM"/></label></th> <!-- 소스(ASIS)테이블논리명 -->
                       	<td colspan="3"><input type="text" id="srcTblLnm" name="srcTblLnm" value="${result.srcTblLnm}"  class="wd98p" readOnly/></td>
                   </tr>
                   
                   <tr>
                       	<th scope="row"><label for="srcColPnm"><s:message code="SOUR.CLMN.PHYC.NM"/></label></th> <!-- 소스(ASIS)컬럼물리명 -->
                       	<td ><input type="text" id="srcColPnm" name="srcColPnm" value="${result.srcColPnm}"   class="wd98p" readOnly/></td>
                       	<th scope="row"><label for="srcColLnm"><s:message code="SOUR.CLMN.LGC.NM"/></label></th> <!-- 소스(ASIS)컬럼논리명 -->
                       	<td colspan="3"><input type="text" id="srcColLnm" name="srcColLnm" value="${result.srcColLnm}"   class="wd98p" readOnly/></td>
                   </tr>
                   
                   <tr>
                   		<th scope="row"><label for="srcCdVal"><s:message code="SOUR.CD"/></label></th> <!-- 소스(ASIS) 코드 -->
                       	<td ><input type="text" id="srcCdVal" name="srcCdVal" value="${result.srcCdVal}"   class="wd98p" readOnly/></td>
                       	<th scope="row"><label for="srcCdValNm"><s:message code="SOUR.CD.NM"/></label></th> <!-- 소스(ASIS) 코드명 -->
                       	<td colspan="3"><input type="text" id="srcCdValNm" name="srcCdValNm" value="${result.srcCdValNm}"   class="wd98p" readOnly/></td>
                   </tr>
                   <tr>
                   		<th scope="row"><label for="srcUppCdVal"><s:message code="SOUR.UPRN.CD"/></label></th> <!-- 소스(ASIS) 상위코드 -->
                       	<td ><input type="text" id="srcUppCdVal" name="srcUppCdVal" value="${result.srcUppCdVal}"   class="wd98p" readOnly /></td>
                       	<th scope="row"><label for="srcUppCdValNm"><s:message code="SOUR.UPRN.CD.NM"/></label></th> <!-- 소스(ASIS) 상위코드명 -->
                       	<td colspan="3"><input type="text" id="srcUppCdValNm" name="srcUppCdValNm" value="${result.srcUppCdValNm}"   class="wd98p" readOnly/></td>
                   </tr>
                  </tbody>
	           </table>   
	       </div>            
                
                
			<div style="clear:both; height:10px;"><span></span></div>
			<div class="stit"><s:message code="ADI.INFO"/></div> <!-- 부가 정보 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
		       <div class="tb_read">
		           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody> 
                  
                  <tr>
                   <th scope="row"><label for="appCrgpNm"><s:message code="APP.CHGR"/></label></th> <!-- 응용담당자 -->
						<td>
							<input type="text" id="appCrgpNm" name="appCrgpNm" value="${result.appCrgpNm}" readOnly />
						</td>
						<th scope="row"><label for="cnvsCrgpNm"><s:message code="CNVR.CHGR"/></label></th> <!-- 전환담당자 -->
						<td>
							<input type="text" id="cnvsCrgpNm" name="cnvsCrgpNm" value="${result.cnvsCrgpNm}" readOnly/>
						</td>
                   </tr>
                   
               </tbody>
           </table>
		 </div>
       
		
       
       
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit"><s:message code="DEMD.DTL.INFO" /></div> <!-- 요청상세정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
		<div class="tb_read">
		    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
		        <caption>
		    <s:message code="CLMN.NM1" /> <!-- 컬럼 이름 -->
		    </caption>
		    <colgroup>
		    <col style="width:15%;" />
		    <col style="width:35%;" />
		    <col style="width:15%;" />
		    <col style="width:35%;" />
		    </colgroup>
		    <tbody>
		        <tr>
		            <th scope="row" class=""><label for="frsRqstDtm"><s:message code="FRST.DEMD.DTTM" /></label></th> <!-- 최초요청일시 -->
		            <td colspan="1"><span class="" ><input type="text" id="frsRqstDtm" name="frsRqstDtm" readonly/></span></td>
		
		            <th scope="row" class=""><label for="frsRqstUserId"><s:message code="FRSTDEMD.USER.NM" /></label></th> <!-- 최초요청사용자명 -->
		            <td colspan="1"><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" readonly/></span></td>
		        </tr>
		        <tr>
		            <th scope="row" class=""><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
		            <td colspan="1"><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" readonly/></span></td>
		
		            <th scope="row" class=""><label for="rqstUserId"><s:message code="DEMD.USER.NM" /></label></th> <!-- 요청사용자명 -->
		            <td colspan="1"><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" readonly/></span></td>
		        </tr>
		        <tr>
		            <th scope="row" class=""><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
		            <td colspan="1"><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" readonly/></span></td>
		
		            <th scope="row" class=""><label for="aprvUserId"><s:message code="APRV.USER.NM" /></label></th> <!-- 승인사용자명 -->
		            <td colspan="1"><span class="" ><input type="text" id="aprvUserNm" name="aprvUserNm" readonly/></span></td>
		            </tr>
		        </tbody>
		    </table>
		</div>      
       
       
</fieldset>     
</div>
</form>
<!-- 입력폼 끝 -->
<div style="clear:both; height:10px;"><span></span></div>
