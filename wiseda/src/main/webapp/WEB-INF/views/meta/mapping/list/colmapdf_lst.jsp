
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
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="input_form_div">
	            <fieldset>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="SUBJ.TRRT.INFO" /></div> <!-- 주제영역 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" ><label for="fullPath"><s:message code="SUBJ.TRRT.PATH"/></label></th> <!-- 주제영역 경로 -->
	                                <td colspan="3"><input type="text" id="fullPath" name="fullPath" class="wd99p" readonly/></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                
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
				 	  <th scope="row" ><label for="mapDfId"><s:message code="MAPG.DFNT.P.ID" /></label></th> <!-- 매핑정의서ID -->
                       <td colspan="3"><input type="text" id="mapDfId" name="mapDfId" class="wd98p" readOnly /></td>
                       <th scope="row"><label for="mapDfType"><s:message code="MAPG.DFNT.P.PTRN" /></label></th> <!-- 매핑정의서유형 -->
                       <td >
                       	<select id="mapDfType"  name="mapDfType"  disabled="disabled">
							 <c:forEach var="code" items="${codeMap.mapDfTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                       <th scope="row" ><label for="colMapType"><s:message code="CLMN.MAPG.PTRN" /></label></th> <!-- 컬럼매핑유형 -->
                       <td >
                       		<select id="colMapType"  name="colMapType"  disabled="disabled">
							 <c:forEach var="code" items="${codeMap.colMapTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                   </tr>
                 </tbody>
           </table>
       </div>           
	                
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
	                        </caption>
	                        <colgroup>
	                       <col style="width:15%;" />
			               <col style="width:12%;" />
			               <col style="width:12%;" />
			               <col style="width:11%;" />
			               <col style="width:15%;" />
			               <col style="width:12%;" />
			               <col style="width:12%;" />
			               <col style="width:11%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                               	<th scope="row"><label for="tgtDbPnm"><s:message code="TARG.DB.NM.1"/></label></th> <!-- 타겟(TOBE)DB명 -->
	                                <td colspan="7"><span class="" ><input type="text" id="tgtDbPnm" name="tgtDbPnm" class="wd98p"  readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for=tgtTblPnm><s:message code="TARG.TBL.NM.PHYC.NM"/></label></th> <!-- 타겟(TOBE)테이블명(물리명) -->
	                                <td colspan="3"><span class="" ><input type="text" id="tgtTblPnm" name="tgtTblPnm" class="wd98p"  readonly /></span></td>
	                                <th scope="row"><label for="tgtTblLnm"><s:message code="TARG.TBL.NM.LGC.NM"/></label></th> <!-- 타겟(TOBE)테이블명(논리명) -->
	                                <td  colspan="3"><span class="" ><input type="text" id="tgtTblLnm" name="tgtTblLnm" class="wd98p"  readonly/></span></td>
	                            </tr>
	                            <tr>
	                            	<th scope="row"><label for="tgtColPnm"><s:message code="TARG.CLMN.NM.PHYC.NM"/></label></th> <!-- 타겟(TOBE)컬럼명(물리명) -->
	                                <td  colspan="3"><span class="" ><input type="text" id="tgtColPnm" name="tgtColPnm" class="wd98p"  readonly/></span></td>
	                               	<th scope="row"><label for="tgtColLnm"><s:message code="TARG.CLMN.NM.LGC.NM"/></label></th> <!-- 타겟(TOBE)컬럼명(논리명) -->
	                                <td colspan="3" ><span class="" ><input type="text" id="tgtColLnm" name="tgtColLnm" class="wd98p"  readonly/></span></td>
	                            </tr>
	                            
	                            
	                            <tr>
							 	  <th scope="row" ><label for="tgtDataType"><s:message code="TARG.DATA.TY"/></label></th> <!-- 타겟(TOBE) 데이터타입 -->
			                       <td><input type="text" id="tgtDataType" name="tgtDataType" value="${result.tgtDataType}" class="wd98p"  readonly /></td>
			                       <th scope="row"><label for="tgtPkYn"><s:message code="TARG.PK.YN"/></label></th> <!-- 타겟(TOBE) PK여부 -->
			                       <td >
			                       	<span class="input_check">
			                       		<input type="checkbox" id="tgtPkYn" name="tgtPkYn" value="Y" onclick="return false;" /> <s:message code="PK.YN" />
			                       	</span> <!-- PK 여부 -->
			                       </td>   
			                       <th scope="row"><label for="tgtNonulYn"><s:message code="TARG.NOTNULL.YN"/></label></th> <!-- 타겟(TOBE) NotNull여부 -->
			                       <td >
			                       	<span class="input_check">
			                       		<input type="checkbox" id="tgtNonulYn" name="tgtNonulYn" value="Y" onclick="return false;" /> <s:message code="NOTNULL.YN" />
			                       	</span> <!-- NotNull 여부 -->
			                       </td>
			                       <th scope="row"><label for="tgtDmnLnm"><s:message code="TARG.CD.DMN.LGC.NM"/></label></th> <!-- 타겟(TOBE)코드도메인논리명 -->
			                       <td ><input type="text" id="tgtDmnLnm" name="tgtDmnLnm"  readonly/></td>							
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
				                       	<td ><input type="text" id="srcSysNm" name="srcSysNm" class="wd98p"  readonly/></td>
				                       	<th scope="row"><label for="srcBizNm"><s:message code="SOUR.BZWR.NM"/></label></th> <!-- 소스(ASIS)업무명 -->
				                       	<td ><input type="text" id="srcBizNm" name="srcBizNm" class="wd98p" readonly/></td>
				                   </tr>
				                   <tr>
				                   		<th scope="row" class=""><label for="srcDbPnm"><s:message code="SOUR.ASIS.DB.NM.1"/></label></th> <!-- 소스(ASIS)DB명 -->
				                       	<td colspan="3"><input type="text" id="srcDbPnm" name="srcDbPnm" class="wd98p" readonly/></td>
				                   </tr>
	                            <tr>
	                            	<th scope="row"><label for="srcTblLnm"><s:message code="SOUR.TBL.NM.1"/></label></th> <!-- 소스(ASIS)테이블명 -->
	                                <td ><span class="" ><input type="text" id="srcTblLnm" name="srcTblPnm" class="wd98p" readonly/></span></td>
	                                <th scope="row"><label for="srcColLnm"><s:message code="SOUR.CLMN.NM.1"/></label></th> <!-- 소스(ASIS)컬럼명 -->
	                                <td ><span class="" ><input type="text" id="srcColLnm" name="srcColPnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            
	                            <tr>
							 	   <th scope="row" class=""><label for="srcColPnm"><s:message code="SOUR.CLMNPHYC.NM"/></label></th> <!-- 소스(ASIS)컬럼(물리명) -->
			                       <td><input type="text" id="srcColPnm" name="srcColPnm" class="wd98p" readonly/></td>
							 	   <th scope="row" class=""><label for="srcColLnm"><s:message code="SOUR.CLMNLGC.NM"/></label></th> <!-- 소스(ASIS)컬럼(논리명) -->
			                       <td><input type="text" id="srcColLnm" name="srcColLnm" class="wd98p" readonly/></td>
			                    </tr>
			                    <tr>
			                       <th scope="row" class=""><label for="srcDataType"><s:message code="SOUR.DATA.TY"/></label></th> <!-- 소스(ASIS) 데이터타입 -->
			                       <td ><input type="text" id="srcDataType" name="srcDataType" class="wd98p" readonly/></td>
			                       <th scope="row"><label for="defltVal"><s:message code="DEFAULT.VAL" /></label></th> <!-- DEFAULT값 -->
			                       <td ><input type="text" id="defltVal" name="defltVal" class="wd98p" readonly /></td>
			                   </tr>
			                   <tr>
			                       <th scope="row"><label for="srcDescn"><s:message code="SOUR.CLMN.TXT"/></label></th> <!-- 소스(ASIS) 컬럼설명 -->
			                       <td colspan="3"><input type="text" id="srcDescn" name="srcDescn" class="wd98p" readonly /></td>
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
			                   		<th scope="row"><label for="colRefDbPnm"><s:message code="CLMN.REF.DB.NM" /></label></th> <!-- 컬럼참조DB명 -->
			                       	<td ><input type="text" id="colRefDbPnm" name="colRefDbPnm" class="wd98p" readonly/></td>
			                       	<th scope="row"><label for="colRefTblPnm"><s:message code="CLMN.REF.TBL.NM" /></label></th> <!-- 컬럼참조테이블명 -->
			                       	<td ><input type="text" id="colRefTblPnm" name="colRefTblPnm" class="wd98p" readonly/></td>
			                   </tr>
	                            <tr>
	                            	<th scope="row"><label for="mapCndNm"><s:message code="MAPG.COND"/></label></th> <!-- 매핑 조건 -->
	                                <td ><span class="" ><input type="text" id="mapCndNm" name="mapCndNm" class="wd98p"  readonly/></span></td>
	                            	<th scope="row"><label for="jnCndNm"><s:message code="JOIN.COND"/></label></th> <!-- 조인 조건 -->
	                                <td ><span class="" ><input type="text" id="jnCndNm" name="jnCndNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                               <th scope="row"><label for="appCrgpNm"><s:message code="APPLI.CHG.R.NM" /></label></th> <!-- 응용담당자명 -->
	                                <td ><span class="" ><input type="text" id="appCrgpNm" name="appCrgpNm" class="wd98p" readonly/></span></td>
	                            	<th scope="row"><label for="cnvsCrgpNm"><s:message code="CNVR.CHG.R.NM" /></label></th> <!-- 전환담당자명 -->
	                                <td ><span class="" ><input type="text" id="cnvsCrgpNm" name="cnvsCrgpNm" class="wd98p" readonly/></span></td>
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
	                                <td ><span class="" ><input type="text" id="frsRqstDtm" name="frsRqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="frsRqstUserNm"><s:message code="FRSTDEMD.USER.NM" /></label></th> <!-- 최초요청사용자명 -->
	                                <td ><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
	                                <td ><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="rqstUserNm"><s:message code="DEMD.USER.NM" /></label></th> <!-- 요청사용자명 -->
	                                <td ><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
	                                <td ><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" class="wd98p"  readonly/></span></td>

	                                <th scope="row" class=""><label for="aprvUserId"><s:message code="APRV.USER.NM" /></label></th> <!-- 승인사용자명 -->
	                                <td ><span class="" ><input type="text" id="aprvUserNm" name="aprvUserNm" class="wd98p"  readonly/></span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                </fieldset>
		</div>
	            </form>
		<!-- 입력폼 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>
		<!-- 입력폼 버튼... -->
<!-- 		<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 			<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 			<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 		</div> -->