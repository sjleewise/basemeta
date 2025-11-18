
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
							 	  <th scope="row" ><label for="mapDfId"><s:message code="MAPG.DFNT.P.ID" /></label></th> <!-- 매핑정의서ID -->
			                       <td><input type="text" id="mapDfId" name="mapDfId" value="${result.mapDfId}"  class="wd98p" readOnly /></td>
			                       <th scope="row"><label for="mapDfType"><s:message code="MAPG.DFNT.P.PTRN" /></label></th> <!-- 매핑정의서유형 -->
			                       <td>
			                       	<select id="mapDfType"  name="mapDfType" disabled="disabled">
										 <c:forEach var="code" items="${codeMap.mapDfTypCd}" varStatus="status">
									    	<option value="${code.codeCd}">${code.codeLnm}</option>
									    </c:forEach>   
									</select>							
			                       </td>
			                   </tr>
			                   <tr>
			                       <th scope="row" ><label for="mapSno"><s:message code="MAPG.SRNO" /></label></th> <!-- 매핑일련번호 -->
			                       <td ><input type="text" id="mapSno" name="mapSno" value="${result.mapSno}"  class="wd98p" readOnly /></td>
			                       <th scope="row" ><label for="tblMapType"><s:message code="TBL.MAPG.PTRN" /></label></th> <!-- 테이블매핑유형 -->
			                       <td>
			                       	<select id="tblMapType"  name="tblMapType" disabled="disabled">
										 <c:forEach var="code" items="${codeMap.tblMapTypCd}" varStatus="status">
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
		                            <th scope="row"><label for="tgtTblLnm"><s:message code="TARG.FLFM.PTRN"/></label></th> <!-- 타겟(TOBE)이행유형 -->
			                       	<td >
				                       	<select id="tgtFlfType"  name="tgtFlfType" disabled="disabled" >
											 <c:forEach var="code" items="${codeMap.tgtFlfTypCd}" varStatus="status">
										    	<option value="${code.codeCd}">${code.codeLnm}</option>
										    </c:forEach>   
										</select>
			                       	</td>
			                   		<th scope="row"><label for="tgtDbPnm"><s:message code="TARG.DB.NM.1"/></label></th> <!-- 타겟(TOBE)DB명 -->
			                       	<td ><input type="text" id="tgtDbPnm" name="tgtDbPnm" value="${result.tgtDbPnm}"  class="wd98p" readOnly/></td>
			                     </tr>
			                     <tr>
			                       	<th scope="row"><label for="tgtTblPnm"><s:message code="TARG.TBL.NM.PHYC.NM"/></label></th> <!-- 타겟(TOBE)테이블명(물리명) -->
			                       	<td><input type="text" id="tgtTblPnm" name="tgtTblPnm" value="${result.tgtTblPnm}"  class="wd98p" readOnly/></td>
			                       	<th scope="row"><label for="tgtTblPnm"><s:message code="TARG.TBL.NM.LGC.NM"/></label></th> <!-- 타겟(TOBE)테이블명(논리명) -->
			                       	<td><input type="text" id="tgtTblLnm" name="tgtTblLnm" value="${result.tgtTblLnm}"  class="wd98p" readOnly/></td>
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
			                       	<th scope="row"><label for="srcSysNm"><s:message code="SOUR.SYS.NM"/></label></th> <!-- 소스(ASIS)시스템명 -->
			                       	<td ><input type="text" id="srcSysNm" name="srcSysNm" value="${result.srcSysNm}"  class="wd98p" readOnly/></td>
			                       	<th scope="row"><label for="srcBizNm"><s:message code="SOUR.BZWR.NM"/></label></th> <!-- 소스(ASIS)업무명 -->
			                       	<td ><input type="text" id="srcBizNm" name="srcBizNm" value="${result.srcBizNm}"  class="wd98p" readOnly/></td>
			                   </tr>
	                            <tr>
			                   		<th scope="row"><label for="srcDbPnm"><s:message code="SOUR.ASIS.DB.NM.1"/></label></th> <!-- 소스(ASIS)DB명 -->
			                       	<td colspan="3"><input type="text" id="srcDbPnm" name="srcDbPnm" value="${result.srcDbPnm}"  class="wd98p" readOnly/></td>
			                    </tr>
			                    <tr>
			                       	<th scope="row"><label for="srcTblPnm"><s:message code="SOUR.TBL.NM.PHYC.NM"/></label></th> <!-- 소스(ASIS)테이블명(물리명) -->
			                       	<td ><input type="text" id="srcTblPnm" name="srcTblPnm" value="${result.srcTblPnm}" class="wd98p" readOnly /></td>
			                       	<th scope="row"><label for="srcTblLnm"><s:message code="SOUR.TBL.NM.LGC.NM"/></label></th> <!-- 소스(ASIS)테이블명(논리명) -->
			                       	<td ><input type="text" id="srcTblLnm" name="srcTblLnm" value="${result.srcTblLnm}" class="wd98p" readOnly /></td>
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
			                   		<th scope="row"><label for="sltSkpCndNm"><s:message code="CHC.SKIP.COND" /></label></th> <!-- 선택/스킵 조건 -->
			                       	<td><input type="text" id="sltSkpCndNm" name="sltSkpCndNm" value="${result.sltSkpCndNm}"  class="wd98p" readOnly/></td>
			                       	<th scope="row"><label for="jnCndNm"><s:message code="JOIN.COND"/></label></th> <!-- 조인 조건 -->
			                       	<td><input type="text" id="jnCndNm" name="jnCndNm" value="${result.jnCndNm}"  class="wd98p" readOnly /></td>
			                   </tr>
			                   <tr>
			                   <th scope="row"><label for="appCrgpNm"><s:message code="APP.CHGR"/></label></th> <!-- 응용담당자 -->
									<td><input type="text" id="appCrgpNm" name="appCrgpNm" value="${result.appCrgpNm}" readOnly /></td>
									<th scope="row"><label for="cnvsCrgpNm"><s:message code="CNVR.CHGR"/></label></th> <!-- 전환담당자 -->
									<td><input type="text" id="cnvsCrgpNm" name="cnvsCrgpNm" value="${result.cnvsCrgpNm}" readOnly/>	</td>
			                   </tr>
			                    <tr>
			                   		<th scope="row"><label for="preFlfDescn"><s:message code="BFHD.FLFM.ATMT"/></label></th> <!-- 사전이행고려사항 -->
			                       	<td><input type="text" id="preFlfDescn" name="preFlfDescn" value="${result.preFlfDescn}" class="wd98p" readOnly  /></td>
			                       	<th scope="row"><label for="refDescn"><s:message code="REF.MTR" /></label></th> <!-- 참고사항 -->
			                       	<td><input type="text" id="refDescn" name="refDescn" value="${result.refDescn}" class="wd98p" readOnly/></td>
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

	                                <th scope="row" class=""><label for="frsRqstUserNm"><s:message code="FRSTDEMD.USER.NM" /></label></th> <!-- 최초요청사용자명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" readonly/></span></td>

	                                <th scope="row" class=""><label for="rqstUserNm"><s:message code="DEMD.USER.NM" /></label></th> <!-- 요청사용자명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" readonly/></span></td>

	                                <th scope="row" class=""><label for="aprvUserNm"><s:message code="APRV.USER.NM" /></label></th> <!-- 승인사용자명 -->
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
		<!-- 입력폼 버튼... -->
<!-- 		<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 			<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 			<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 		</div> -->