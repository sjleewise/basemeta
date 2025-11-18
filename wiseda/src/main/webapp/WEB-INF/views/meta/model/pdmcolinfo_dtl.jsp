<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- 컬럼 정보 탭 -->
<%-- 		<%@include file="pdmcol_dtl.jsp" %> --%>
		<!-- 입력폼 시작 -->
	         <form name="frmInput2" id="frmInput2" method="post" >
	   		      <input type="hidden" id="sditmId" name="sditmId"  readonly/>
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="input_form_div">
	            <fieldset>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
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
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="subjId">주제영역ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="subjId" name="subjId"/></span></td> --%>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row" class=""><label for="mdlLnm">모델논리명</label></th> -->
<!-- 	                                <td colspan="3"><input type="text" id="mdlLnm" name="mdlLnm" /></td> -->
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" class=""><label for="pdmTblLnm"><s:message code="TBL.NM1" /></label></th> <!-- 물리모델테이블 -->
	                                <td colspan="3"><span class="" ><input type="text" id="pdmTblPnm" name="pdmTblPnm" class="wd98p" readonly /></span></td>

	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="CLMN.DTL.INFO"/></div> <!-- 컬럼 상세 정보 -->
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
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="pdmTblId">테이블ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="pdmTblId" name="pdmTblId"/></span></td> --%>
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" class=""><label for="pdmColPnm"><s:message code="PHYC.MDEL.CLMN.NM.PHYC.NM"/></label></th> <!-- 물리모델컬럼명(물리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmColPnm" name="pdmColPnm" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pdmColLnm"><s:message code="PHYC.MDEL.CLMN.NM.LGC.NM"/></label></th> <!-- 물리모델컬럼명(논리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="pdmColLnm" name="pdmColLnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="colOrd"><s:message code="CLMN.SQNC" /></label></th> <!-- 컬럼순서 -->
	                                <td colspan="1"><span class="" ><input type="text" id="colOrd" name="colOrd" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="objVers">버전</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="objVers" name="objVers" readonly/></td> --%>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                       		    <th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                               		 <td><span class="" >
                                		<select id="regTypCd" class="" name="regTypCd"  disabled="disabled" >
									<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									<option value="C"><s:message code="NEW" /></option> <!-- 신규 -->
									<option value="U"><s:message code="CHG" /></option> <!-- 변경 -->
									<option value="D"><s:message code="DEL" /></option> <!-- 삭제 -->
								</select>
								</span>
								</td>
								</tr>
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""  class="b0" style="height:50px;width:99%;" readonly></textarea></td>
	                            </tr>
<!-- 	                            style="width:wd500;" -->
	                            <tr>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
	                                <td colspan="1"><input type="text" id="dataType" name="dataType"  class="wd98p"  readonly/></td>

<!-- 	                                <th scope="row"><label for="sditmLnm">표준용어</label></th> -->
<!-- 	                                <td colspan="1"><input type="text" id="sditmLnm" name="sditmLnm"  class=""  readonly/></td> -->
	                                <th scope="row"><label for="sditmLnm"><s:message code="STRD.TERMS" /></label></th> <!-- 표준용어 -->
	                                <td colspan="1"><input type="text" id="sditmLnm" name="sditmLnm"  class="wd98p"  readonly />
<%-- 	                                <button type="button" class="smenu_link" onclick='window.open().location.href="../stnd/sditm_lst.do?objId="+$("#frmInput2 #sditmId").val()' readonly ><span>표준용어 조회</span></button></td> --%>
	                            </tr>
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="dataLen">데이터길이</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="dataLen" name="dataLen" readonly/></td> --%>
<!--  	                            </tr> -->
<!--  	                            <tr> -->
<!-- 	                                <th scope="row"><label for="dataScal">소수점길이</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="dataScal" name="dataScal" readonly/></td> --%>
<!-- 	                            </tr> -->
	                            <tr>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="pkYn"><s:message code="PK.YN" /></label></th> <!-- PK여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="pkYn" name="pkYn" value="Y" onclick="return false;"/></span><!-- PK 적용 -->
	                                </td>
<!-- 	                                <td colspan="1"><input type="text"  id="pkYn" name="pkYn"/></td> -->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pkOrd"><s:message code="PK.SQNC" /></label></th> <!-- PK순서 -->
	                                <td colspan="1"><span class="" ><input type="text"  id="pkOrd" name="pkOrd" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="fkYn"><s:message code="FK.YN" /></label></th> <!-- FK여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="fkYn" name="fkYn" value="Y" onclick="return false;"/></span><!-- FK 적용 -->
	                                </td>
<!-- 	                                <td colspan="1"><input type="text"  id="pkYn" name="pkYn"/></td> -->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="akYn"><s:message code="AK.YN" /></label></th> <!-- AK여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="akYn" name="akYn" value="Y" onclick="return false;"/></span><!-- AK 적용 -->
	                                </td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="nonulYn"><s:message code="NOTNULL.YN" /></label></th><!-- NOTNULL여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="nonulYn" name="nonulYn" value="Y" onclick="return false;"/></span><!-- NOTNULL 적용 -->
	                                </td>
<!-- 	                                <td colspan="1"><input type="text" id="nonulYn" name="nonulYn" /></td> -->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="defltVal"><s:message code="DEFAULT.VAL" /></label></th> <!-- DEFAULT값 -->
	                                <td colspan="1"><span class="" ><input type="text" id="defltVal" name="defltVal" class="wd98p" readonly></span></td>
	                            </tr>
	                            <tr>
	                             <th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 -->
                                 <td colspan = "3"><select id="encYn" class="wd200" name="encYn" value = "${result.encYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                        <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                                    </select></td></tr>
	                        </tbody>
	                    </table>
	                </div>
	                <div style="clear:both; height:10px;"><span></span></div>
<%-- 					<div class="stit"><s:message code="STRD.BFHD.DTL.INFO" /></div> <!-- 표준사전 상세정보 -->
					<div style="clear:both; height:5px;"><span></span></div>
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.BFHD.DTL.INFO' />"> <!-- 표준사전 상세정보 -->
	                        <caption>
	                        <s:message code="STRD.BFHD.DTL.INFO" /><!-- 표준사전 상세정보 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" class=""><label for="sditmLnm"><s:message code="STRD.TERMS.LGC.NM" /></label></th> <!-- 표준용어논리명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="sditmLnm" name="sditmLnm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="sditmPnm"><s:message code="STRD.TERMS.PHYC.NM" /></label></th> <!-- 표준용어물리명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="sditmPnm" name="sditmPnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="dmnLnm"><s:message code="DMN.LGC.NM" /></label></th> <!-- 도메인논리명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="dmnLnm" name="dmnLnm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="dmnPnm"><s:message code="DMN.PHYC.NM" /></label></th> <!-- 도메인물리명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="dmnPnm" name="dmnPnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="infotpLnm"><s:message code="INFO.TY.NM" /></label></th> <!-- 인포타입명 -->
	                                <td colspan="3"><span class="" ><input type="text" id="infotpLnm" name="infotpLnm" class="wd98p" readonly/></span></td>

	                            </tr>
	                        </tbody>


	                        
	                    </table>
	                </div>
	                --%>
	                <!-- 여기서부터 범정부 항목 -->
	                <div style="clear:both; height:10px;"><span></span></div>
					<div id="gov3" class="stit"><s:message code="GOV.CEN.MET" /></div> <!-- 범정부 중앙에타 연계항목 -->
					<div style="clear:both; height:5px;"><span></span></div>
					<legend><s:message code="GOV.CEN.MET" /></legend> <!-- 범정부 중앙에타 연계항목 -->
					<div id="gov4" class="tb_basic">
						<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
							<caption><s:message code="GOV.CEN.MET" /></caption> <!-- 범정부 중앙에타 연계항목 -->
							<colgroup>
			               <col style="width:15%;" />
			               <col style="width:35%;" />
			               <col style="width:15%;" />
			               <col style="width:35%;" />
			               </colgroup>
							<tbody>
								<tr>
									<th scope="row"><s:message code="OPEN.YN" /></th> <!-- 공개/비공개여부 -->
									<td>
										<select id="openYn" name="openYn" value = "${result.openYn}" disabled="disabled">
											<option value=""></option>
			                                <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
			                                <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
										</select>
									</td>
									<th scope="row"><s:message code="NOPEN.RSN"/></th> <!-- 비공개사유 -->
			                        <td>
			                       		<select id="priRsn" name="priRsn" disabled="disabled">
										  <option value=""><s:message code="CHC.NON" /></option> <!-- 선택 -->
<%-- 										<c:forEach var="code" items="${codeMap.nopenRsn}" varStatus="status"> --%>
<%-- 										  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 										</c:forEach> --%>
										</select>
			                        </td>
								</tr>
								<tr>
									<th scope="row"><s:message code="PRSN.INFO.YN" /></th> <!-- 개인정보여부 -->
									<td colspan="3">
										<select id="prsnInfoYn" name="prsnInfoYn" value = "${result.prsnInfoYn}" disabled="disabled">
											<option value=""></option>
			                                <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
			                                <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
										</select>
									</td>
								</tr>
								<tr>
									<th scope="row"><s:message code="CONST.CND" /></th> <!-- 제약조건 -->
									<td colspan="3"><textarea id="constCnd" name="constCnd" class = "wd98p" accesskey="" readonly>${result.constCnd }</textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
					<!-- 여기까지 -->
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
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstDtm" name="frsRqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="frsRqstUserNm"><s:message code="FRSTDEMD.USER.NM" /></label></th> <!-- 최초요청사용자명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="rqstUserNm"><s:message code="DEMD.USER.NM" /></label></th> <!-- 요청사용자명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="aprvUserNm"><s:message code="APRV.USER.NM" /></label></th> <!-- 승인사용자명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvUserNm" name="aprvUserNm" class="wd98p" readonly/></span></td>

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