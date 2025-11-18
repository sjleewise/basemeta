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
<title><s:message code="DMN.DTL.INFO" /></title> <!-- 도메인 상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
// 		$("#frmInput #regTypCd").val('${result.regTypCd}');
// 		$("#frmInput #infotpId").val('${result.infotpId}');
// 		$("#frmInput #sditmAutoCrtYn").val('${result.sditmAutoCrtYn}');
		$("#frmInput #cdValTypCd").val('${result.cdValTypCd}');
		$("#frmInput #cdValIvwCd").val('${result.cdValIvwCd}');
		$("#frmInput #encYn").val('${result.encYn}');
// 		$("#frmInput #stndAsrt").val('${result.stndAsrt}');
		$("#frmInput #dupYn").val('${result.dupYn}');
		
//         create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId", "전체", true);
//     	double_select(dmnginfotpJson, $("#"+firstSelectBoxId[0]));
//     	$('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
//     		double_select(dmnginfotpJson, $(this));
//     	});
//     	setDomainInfoinit();
	});
	
// 	$(window).on('load',function() {
// 		$("#frmInput #regTypCd").val('${result.regTypCd}');
// 		$("#frmInput #infotpId").val('${result.infotpId}');
// 		$("#frmInput #sditmAutoCrtYn").val('${result.sditmAutoCrtYn}');
// 		$("#frmInput #cdValTypCd").val('${result.cdValTypCd}');
// 		$("#frmInput #cdValIvwCd").val('${result.cdValIvwCd}');
// 	});
</script>
</head>
<body>
   <div class="stit"><s:message code="DMN.DTL.INFO" /></div> <!-- 도메인 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_read">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /><!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>

      <tbody>               
                            <tr>
                     	        <%-- <th scope="row"><label for="dmnLnm"><s:message code="DMN.LGC.NM" /></label></th> --%> <!-- 도메인논리명 -->
                     	        <th scope="row"><label for="sditmLnm"><s:message code="LGC.NM" /></label></th> <!-- 영문판용(한글버젼시 위 주석 사용) -->
                                <td><input type="text" id="dmnLnm" name="dmnLnm" class="wd98p" value="${result.dmnLnm}" readonly/></td>
                           		<%-- <th scope="row"><label for="dmnPnm"><s:message code="DMN.PHYC.NM" /></label></th> --%> <!-- 도메인물리명 -->
                           		<th scope="row"><label for="sditmLnm"><s:message code="PHYC.NM" /></label></th> <!-- 영문판용(한글버젼시 위 주석 사용) -->
                                <td><input type="text" id="dmnPnm" name="dmnPnm" class="wd98p" value="${result.dmnPnm}" readonly/></td>
                            </tr>
                            <tr>
                     	        <%-- <th scope="row"><label for="dmnLnm"><s:message code="DMN.LGC.NM" /></label></th> --%> <!-- 논리명기준구분 -->
                     	        <th scope="row"><label for="sditmLnm"><s:message code="LGC.NM.BASE.DSTC" /></label></th> <!-- 영문판용(한글버젼시 위 주석 사용) -->
                                <td><input type="text" id="sditmLnm" name="sditmLnm" class="wd98p" value="${result.lnmCriDs}" readonly/></td>
                           		<%-- <th scope="row"><label for="dmnPnm"><s:message code="DMN.PHYC.NM" /></label></th> --%> <!-- 물리명기준구분 -->
                           		<th scope="row"><label for="pnmCriDs"><s:message code="PHYC.NM.BASE.DSTC" /></label></th> <!-- 영문판용(한글버젼시 위 주석 사용) -->
                                <td><input type="text" id="pnmCriDs" name="pnmCriDs" class="wd98p" value="${result.pnmCriDs}" readonly/></td>
                            </tr>
                            <tr>
<%--                            		<th scope="row"><label ><s:message code="DMN.GRP.INFO.TY" /></label></th><!-- 도메인그룹/인포타입 --> --%>
<!--                                 <td> -->
<%--                                 	<span class="input_inactive"><input type="text" class="wd30p" id="uppDmngLnm"  name="uppDmngLnm" value="${result.uppDmngLnm}" readonly/></span> --%>
<%-- 									<span class="input_inactive"><input type="text" class="wd30p"  id="dmngLnm" name="dmngLnm"    value="${result.dmngLnm}" readonly/></span> --%>
<%-- 									<span class="input_inactive"><input type="text" class="wd30p"  id="infotpLnm" name="infotpLnm"  value="${result.infotpLnm}" readonly/></span> --%>
<%-- <%--                                 	<div id="selectBoxDiv"> <span></span></div> --%>
<!--                                  </td> -->
                                 
                                <th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
								<td colspan='3'>
									<span class="input_inactive"><input type="text" class="wd80" id="dataType"  name="dataType" value="${result.dataType}" readonly/></span>
									<span class="input_inactive"><input type="text" class="wd50"  id="dataLen" name="dataLen"    value="${result.dataLen}" readonly/></span>
									<span class="input_inactive"><input type="text" class="wd50"  id="dataScal" name="dataScal"  value="${result.dataScal}" readonly/></span>
								</td>
                                 
                            </tr>
<!--                             <tr> -->
<!--                            		<th scope="row"><label >데이터타입변환</label></th> -->
<%--                                 <td colspan="3"><c:if test="${result.oraDataType ne null }">Oracle&nbsp;:&nbsp;${result.oraDataType}&nbsp;&nbsp;MySQL&nbsp;:&nbsp;${result.myDataType}&nbsp;&nbsp;MS-SQL&nbsp;:&nbsp;${result.msDataType}</c:if></td> --%>
<!--                             </tr> -->
                            
<!--                             <tr> -->
<%--                            		<th scope="row"><label for="cdValTypCd"><s:message code="CD.VAL.TY.CD" /></label></th> <!-- 코드값유형코드 --> --%>
<%--                                 <td><select id="cdValTypCd" class="wd200" name="cdValTypCd" value = "${result.cdValTypCd}" disabled="disabled" > --%>
<!--                                         <option value=""></option> -->
<%--                                         <c:forEach var="code" items="${codeMap.cdValTypCd}" varStatus="status" > --%>
<%--                                         <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%--                                         </c:forEach> --%>
<%--                                     </select></td> --%>
<%--                                 <th scope="row"><label for="cdValIvwCd"><s:message code="CD.VAL.GRAN.MH.CD" /></label></th> <!-- 코드값부여방식코드 --> --%>
<%--                                 <td><select id="cdValIvwCd" class="wd200" name="cdValIvwCd" value = "${result.cdValIvwCd}" disabled="disabled" > --%>
<!--                                         <option value=""></option> -->
<%--                                         <c:forEach var="code" items="${codeMap.cdValIvwCd}" varStatus="status" > --%>
<%--                                         <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%--                                         </c:forEach> --%>
<%--                                     </select></td> --%>
<!--                             </tr> -->
<!--                             <tr> -->
<%--                            		<th scope="row"><label for="lstEntyLnm"><s:message code="LST.ENTY.LGC.NM" /></label></th> <!-- 목록엔터티논리명 --> --%>
<%--                                 <td><input type="text" id="lstEntyLnm" name="lstEntyLnm" class="wd98p" value="${result.lstEntyLnm}" readonly/></td> --%>
<%--                                 <th scope="row"><label for="lstEntyPnm"><s:message code="LST.ENTY.PHYC.NM" /></label></th> <!-- 목록엔터티물리명 --> --%>
<%--                                 <td><input type="text" id="lstEntyPnm" name="lstEntyPnm" class="wd98p" value="${result.lstEntyPnm}" readonly/></td> --%>
                                
<!--                             </tr> -->
<!--                             <tr> -->
<%--                            		<th scope="row"><label for="lstAttrLnm"><s:message code="LST.ATRB.LGC.NM" /></label></th> <!-- 목록어트리뷰트논리명 --> --%>
<%--                                 <td><input type="text" id="lstAttrLnm" name="lstAttrLnm" class="wd98p" value="${result.lstAttrLnm}" readonly/></td> --%>
<%--                                 <th scope="row"><label for="lstAttrPnm"><s:message code="LST.ATRB.PHYC.NM" /></label></th> <!-- 목록어트리뷰트물리명 --> --%>
<%--                                 <td><input type="text" id="lstAttrPnm" name="lstAttrPnm" class="wd98p" value="${result.lstAttrPnm}" readonly/></td> --%>
                                
<!--                             </tr> -->
<!--                             <tr> -->
<%-- 								<th scope="row"><label for="sditmAutoCrtYn"><s:message code="STRD.TERMS.ATMD.CRTN.YN" /></label></th> <!-- 표준용어자동생성여부 --> --%>
<!--                                 <td> -->
<%--                                 	<select id="sditmAutoCrtYn" class="wd200" name="sditmAutoCrtYn" value = "${result.sditmAutoCrtYn}" disabled="disabled" > --%>
<!--                                         <option value=""></option> -->
<%--                                         <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%--                                         <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 --> --%>
<%--                                     </select> --%>
<!--                                 </td> -->
                                
<%--                                 <th scope="row"><label for="dmnDscd"><s:message code="LC.CD" /></label></th> <!-- 대분류코드 --> --%>
<!-- 				     	        <td > -->
<!-- 									<input type="text" id="dmnDscd" name="dmnDscd" class="wd200"/> -->
<!-- 					            </td> -->
<!--                             </tr> -->
                            
<!--                             <tr> -->
<%--                                 <th scope="row"><label for="sditmAutoCrtYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 --> --%>
<!--                                 <td > -->
<%--                                 	<select id="encYn" class="wd200" name="encYn" value = "${result.encYn}" disabled="disabled" > --%>
<!--                                         <option value=""></option> -->
<%--                                         <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%--                                         <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 --> --%>
<%--                                     </select> --%>
<!--                                 </td> -->
<!-- 				                <th scope="row"><label for="dupYn">중복여부</label></th> 암호화여부 -->
<!-- 								<td> -->
<%-- 									<select id="dupYn" class="wd200" name="dupYn" value="${result.dupYn}" disabled="disabled"> --%>
<!-- 									<option value=""></option> -->
<%-- 									<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%-- 									<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 --> --%>
<%-- 									</select> --%>
<!-- 								</td> -->
<!--                             </tr> -->
                            
<!--                             <tr> -->
<%-- 			                    <th scope="row"><label for="dmnMinVal"><s:message code="MINV" /></label></th> <!-- 최소값 --> --%>
<!-- 			  	           	    <td> -->
<%-- 			  	           	    	<input type="text" id="dmnMinVal" name="dmnMinVal" value="${result.dmnMinVal}" readonly /> --%>
<!-- 			  	           	    </td> 			 -->
			  	           	    
<%-- 			  	           	    <th scope="row"><label for="dmnMaxVal"><s:message code="MAXV" /></label></th> <!-- 최대값 --> --%>
<!-- 			  	           	    <td> -->
<%-- 			  	           	    	<input type="text" id="dmnMaxVal" name="dmnMaxVal" value="${result.dmnMaxVal}" readonly  /> --%>
<!-- 			  	           	    </td>								 		 -->
<!-- 			                </tr> -->
                            
                            <tr>
                           		<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                                <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly>${result.objDescn} </textarea></td>
                                
                            </tr>
                   </tbody>
    </table>
    </div>
    
    </form>
    
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<%@include file="otherinfo.jsp" %>
	<!-- 입력폼 버튼... -->
<!-- 	<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 		<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 		<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 	</div> -->


</body>
</html>