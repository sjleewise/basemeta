<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="DTL.INFO" /></title> <!-- 상세정보 -->
<script type="text/javascript">
 	$(document).ready(function(){
 		$("#frmInput #shdKndCd").val('${result.shdKndCd}');
 		$("#frmInput #shdTypCd").val('${result.shdTypCd}');
 		
//  		alert("${result.shdKndCd}");
 	}); 
</script>
</head>
<body>
		<!-- 입력폼 시작 -->
	  	<form name="frmInput" id="frmInput" method="post" >
		<div id="input_form_div">
	     
	     <fieldset>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit"><s:message code="SCDU.INFO" /></div> <!-- 스케줄 정보 -->
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_basic">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	                        <caption>
	                        <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	   		                    <tr>
	        		                <th scope="row"><label for="shdKndCd"><s:message code="SCDU.KIND" /></label></th> <!-- 스케줄 종류 -->
                              		  <td colspan="3">
                              			  <select id="shdKndCd" class="" name="shdKndCd"  value="${result.shdKndCd}" disabled="disabled">
										  <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								  		  <c:forEach var="code" items="${codeMap.shdKndCd}" varStatus="status">
										  <option value="${code.codeCd}">${code.codeLnm}</option>
										  </c:forEach>
										  </select>
									</td>

<!-- 	        		                <th scope="row"><label for="regTypCd">등록 유형</label></th> -->
<!--                               		  <td> -->
<%--                               			  <select id="regTypCd" class="" name="regTypCd"  disabled="disabled"> --%>
<!-- 								  		  <option value=""><s:message code="WHL" /></option> <! 전체 --> 
<%-- 										  </select> --%>
<!-- 									</td> -->
	                	       </tr>
	                	       
	                            <tr>
	                                <th scope="row"><label for="shdPnm"><s:message code="SCDU.NM.PHYC.NM" /></label></th> <!-- 스케줄명(물리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="shdPnm" name="shdPnm" value="${result.shdPnm}" readonly /></span></td>


	                                <th scope="row"><label for="shdLnm"><s:message code="SCDU.NM.LGC.NM" /></label></th> <!-- 스케줄명(논리명) -->
	                                <td colspan="1"><span class="" ><input type="text" id="shdLnm" name="shdLnm" value="${result.shdLnm}"  readonly/></span></td>
	                            </tr>

	                            <tr>
	                                <th scope="row"><label for="shdUseYn"><s:message code="SCDU.USE.YN" /></label></th> <!-- 스케줄 사용 여부 -->
	                                <td><span class="input_check"  >
	                                    <input type="checkbox" id="shdUseYn" name="shdUseYn" value="Y"  onclick="return false;" /> <s:message code="SCDU.USE" /></span> <!-- 스케줄 사용 -->
	                                </td>
	                               
	                               
	                                <th scope="row"><label for="shdBprYn"><s:message code="SCDU.BNDL.TRTT.YN" /></label></th> <!-- 스케줄 일괄처리여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="shdBprYn" name="shdBprYn" value="Y" onclick="return false;"/> <s:message code="BNDL.TRTT" /></span> <!-- 일괄처리 -->
	                                </td>
	                            </tr>
	                            
	                            <tr>
	                                <th scope="row"><label for="erDataLdYn"><s:message code="EROR.DATA.LOAD.YN" /></label></th> <!-- 오류데이터 적재여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="erDataLdYn" name="erDataLdYn" value="Y" onclick="return false;"/> <s:message code="EROR.DATA" /></span> <!-- 오류데이터 -->
	                                </td>


	                                <th scope="row"><label for="erDataLdCnt"><s:message code="EROR.DATA.LOAD.CCNT" /></label></th> <!-- 오류데이터 적재건수 -->
	                                <td colspan="1"><span class="" ><input type="text" id="erDataLdCnt" name="erDataLdCnt" value="${result.erDataLdCnt}"  readonly/></span></td>
	                            </tr>

	                            <tr>
	                                <th scope="row"><label for="pkDataLdYn"><s:message code="PK.DATA.LOAD.YN" /></label></th> <!-- PK데이터 적재여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="pkDataLdYn" name="pkDataLdYn" value="Y" onclick="return false;"/> <s:message code="PK.DATA" /></span> <!-- PK데이터 -->
	                                </td>


	                                <th scope="row"><label for="pkDataLdCnt"><s:message code="PK.DATA.LOAD.CCNT" /></label></th> <!-- PK데이터 적재건수 -->
	                                <td colspan="1"><span class="" ><input type="text" id="pkDataLdCnt" name="pkDataLdCnt" value="${result.pkDataLdCnt}" readonly/></span></td>
	                            </tr>
	                            
	                            <tr>
	                                <th scope="row"><label for="anaDgr"><s:message code="ANLY.ODR" /></label></th> <!-- 분석차수 -->
	                                <td colspan="1"><span class="" ><input type="text" id="anaDgr" name="anaDgr" value="${result.anaDgr}" readonly/></span></td>


	                                <th scope="row"><label for="anaDgrAutoIncYn"><s:message code="ANLY.ODR.ATMD.INC.YN" /></label></th> <!-- 분석차수 자동증가여부 -->
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="anaDgrAutoIncYn" name="anaDgrAutoIncYn" value="Y" onclick="return false;"/> <s:message code="ANLY.ODR.ATMD.INC" /></span> <!-- 분석차수 자동증가 -->
	                                </td>
	                            </tr>
	                            
	                             <tr>
	                             	<th scope="row"><label for="shdStrDtm"><s:message code="SCDU.STRN.TKTM" /></label></th> <!-- 스케줄시작시간 -->
	                                <td colspan="1"><span class="" ><input type="text" id="shdStrDtm" name="shdStrDtm" value="<fmt:formatDate value="${result.frsRqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></span></td>
	                             	                             
	                             
	        		                <th scope="row"><label for="shdTypCd"><s:message code="SCDU.PTRN" /></label></th> <!-- 스케줄 유형 -->
                              		  <td>
                              			  <select id="shdTypCd" class="" name="shdTypCd" value="${result.shdTypCd}"  disabled="disabled">
								  		  <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								  		  <c:forEach var="code" items="${codeMap.shdTypCd}" varStatus="status">
										  <option value="${code.codeCd}">${code.codeLnm}</option>
										  </c:forEach>
										  </select>
									</td>
	                	       </tr>
	                            
	                            <tr>
	                                <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey="" value="${result.objDescn}" class="wd99p" readonly></textarea></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
<%-- 	                 <div style="clear:both; height:10px;"><span></span></div> --%>
					 <div class="stit" style="display: none"><s:message code="REG.DEMD.APRV.INFO" /></div> <!-- 등록 요청/승인 정보 -->
<%-- 					 <div style="clear:both; height:5px;"><span></span></div> --%>
	                <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	                <div class="tb_basic" style="display: none">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
	                        <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" class=""><label for="frsRqstUserNm"><s:message code="FRST.DMNT" /></label></th> <!-- 최초요청자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" value="${result.frsRqstUserNm}" readonly/></span></td>

	                                <th scope="row" class=""><label for="frsRqstDtm"><s:message code="FRST.DEMD.DTTM" /></label></th> <!-- 최초요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstDtm" name="frsRqstDtm" value="<fmt:formatDate value="${result.frsRqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"  readonly /></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="rqstUserNm"><s:message code="DMNT" /></label></th> <!-- 요청자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" value="${result.rqstUserNm}" readonly /></span></td>

	                                <th scope="row" class=""><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" value="<fmt:formatDate value="${result.rqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="aprvUserNm"><s:message code="APRR" /></label></th> <!-- 승인자 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvUserNm" name="aprvUserNm" value="${result.aprvUserNm}" readonly/></span></td>

	                                <th scope="row" class=""><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
	                                <td colspan="1"><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" value="<fmt:formatDate value="${result.aprvDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                </fieldset>
		</div>
	            </form>
		<!-- 입력폼 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>

</body>
</html>