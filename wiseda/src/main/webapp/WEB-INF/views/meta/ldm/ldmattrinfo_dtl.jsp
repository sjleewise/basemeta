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
	                            <tr>
	                                <th scope="row" class=""><label for="ldmEntyLnm"><s:message code="ENTY.NM" /></label></th> <!-- 엔티티명 -->
	                                <td colspan="1"><span class="" ><input type="text" id="ldmEntyLnm" name="ldmEntyLnm" class="wd98p" readonly /></span></td>

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

	                            <tr>	                               
	                                <th scope="row"><label for="ldmAttrLnm"><s:message code="ATTR.NM"/></label></th> <!-- 어트리뷰트명 -->
	                                <td colspan="3"><span class="" ><input type="text" id="ldmAttrLnm" name="ldmAttrLnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="colOrd"><s:message code="CLMN.SQNC" /></label></th> <!-- 컬럼순서 -->
	                                <td colspan="1">
	                                	<span class="" ><input type="text" id="attrOrd" name="attrOrd" class="wd98p" readonly/></span>
	                                </td>

	                       		    <th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
                               	    <td>
                               		    <span class="" >
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
	                                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
	                                <td colspan="1"><input type="text" id="dataType" name="dataType"  class="wd98p"  readonly/></td>

	                                <th scope="row"><label for="sditmLnm"><s:message code="STRD.TERMS" /></label></th> <!-- 표준용어 -->
	                                <td colspan="1"><input type="text" id="sditmLnm" name="sditmLnm"  class="wd98p"  readonly />
	                            </tr>
	                           
	                            <tr>
	                                <th scope="row"><label for="pkYn"><s:message code="PK.YN" /></label></th> <!-- PK여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="pkYn" name="pkYn" value="Y" onclick="return false;"/></span><!-- PK 적용 -->
	                                </td>

	                                <th scope="row"><label for="pkOrd"><s:message code="PK.SQNC" /></label></th> <!-- PK순서 -->
	                                <td colspan="1"><span class="" ><input type="text"  id="pkOrd" name="pkOrd" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="nonulYn"><s:message code="NOTNULL.YN" /></label></th><!-- NOTNULL여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="nonulYn" name="nonulYn" value="Y" onclick="return false;"/></span><!-- NOTNULL 적용 -->
	                                </td>

	                                <th scope="row"><label for="defltVal"><s:message code="DEFAULT.VAL" /></label></th> <!-- DEFAULT값 -->
	                                <td colspan="1"><span class="" ><input type="text" id="defltVal" name="defltVal" class="wd98p" readonly></span></td>
	                            </tr>
	                            
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""  class="b0" style="height:50px;width:99%;" readonly></textarea></td>
	                            </tr>
	                           
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