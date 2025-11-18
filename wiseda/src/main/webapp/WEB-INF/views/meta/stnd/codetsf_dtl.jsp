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
<title><s:message code="MENU.DTL.INFO" /></title> <!-- 메뉴상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #useYn").val('${result.useYn}');
		$("#frmInput #cdValTypCd").val('${result.cdValTypCd}');

	});
</script>
</head>
<body>
   <div class="stit"><s:message code="MSG.DTL.INFO" /></div> <!-- 메시지 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_basic">
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
						<th scope="row" ><label for="tgtDbConnTrgId"><s:message code="TARG.DB.SCHEMA.NM" /></label></th> <!-- 타겟DB/스키마명 -->
						<td>
		                        <input type ="text" id="tgtDbConnTrgPnm" class="wd100" name="tgtDbConnTrgPnm" value ="${result.tgtDbConnTrgPnm}" disabled />
					            <input type ="text" id="tgtDbSchPnm" class="wd100" name="tgtDbSchPnm" value ="${result.tgtDbSchPnm}" disabled />
					           
						</td>
						<th scope="row" ><label for="dmnLnm"><s:message code="DMN.NM" /></label></th> <!-- 도메인명 -->
						<td>
								<input type="text" id="dmnLnm" name="dmnLnm" class="wd200" value="${result.dmnLnm}" readonly />
							
						</td>
				</tr>
				<tr style = "display:none;">
						<th scope="row"><label for="cdValTypCd"><s:message code="CD.PTRN" /></label></th> <!-- 코드유형 -->
						<td>
							<select id="cdValTypCd" name = "cdValTypCd" value = "${result.cdValTypCd}" disabled>
							<option><s:message code="CHC" /></option> <!-- 선택 -->
							<option value = "O"><s:message code="SMPL.CD" /></option> <!-- 단순코드 -->
							<option value = "C"><s:message code="COEX.CD" /></option> <!-- 복잡코드 -->
							</select>
						</td>
						
				 </tr>
				<tr>
						<th scope="row"><label for="cdVal"><s:message code="CD.VAL" /></label></th> <!-- 코드값 -->
						<td>
							<input type="text" id="cdVal" name="cdVal" class="wd200" value="${result.cdVal}" readonly />
						</td>
							<th scope="row" class="row"><label for="cdValNm"><s:message code="CD.VAL.NM" /></label></th> <!-- 코드값명 -->
						<td>
							<input type="text" id="cdValNm" name="cdValNm" class="wd200" value="${result.cdValNm}" readonly />
						</td>
				 </tr>
				 <tr>
						<th scope="row"><label for="dispOrd"><s:message code="DISPLAY.SQNC" /></label></th> <!-- 표시순서 -->
						<td colspan="3"><input type="text" id="dispOrd" name="dispOrd" value="${result.dispOrd}" readonly />
				 </tr>
				 <tr>
						<th scope="row" ><label for="useYn"><s:message code="USE.YN" /></label></th> <!-- 사용여부 -->
						<td >	<select id="useYn"  name="useYn" value="${result.useYn}" disabled>
							       <option value=""></option>
							       <option value="Y">Y</option>
							       <option value="N">N</option>
							</select>
						</td>
						<th scope="row" ><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
						<td >	<select id="regTypCd"  name="regTypCd" value="${result.regTypCd}" disabled>
							       <option value=""></option>
							     <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status">
							       <option value="${code.codeCd}">${code.codeLnm}</option>
							     </c:forEach>
							</select>
						</td>
					
				 </tr>
				 <tr style = "display:none;">
				 <th scope="row" class="row"><label for="dmnDscd"><s:message code="LC.CD" /></label></th> <!-- 대분류코드 -->
						<td>
							<input type="text" id="dmnDscd" name="dmnDscd" class="wd200" value="${result.dmnDscd}" readonly />
						</td>
							<th scope="row" ><label for="uppCdVal"><s:message code="UPRN.CD.VAL" /></label></th> <!-- 상위코드값 -->
						<td><input type="text" id="uppCdVal" name="uppCdVal" value="${result.uppCdVal}" class="wd98p" readonly/>
				 </tr>
				    <tr style = "display:none;">
						<th scope="row"><label><s:message code="ETC" /></label></th> <!-- 기타 -->
						<td colspan="3">
						   <input type="text" id="vvNote1" name="vvNote1" value="${result.vvNote1}" class="wd100" readonly />
						   <input type="text" id="vvNote2" name="vvNote2" value="${result.vvNote2}" class="wd100" readonly />
						   <input type="text" id="vvNote3" name="vvNote3" value="${result.vvNote3}" class="wd100" readonly />
						   <input type="text" id="vvNote4" name="vvNote4" value="${result.vvNote4}" class="wd100" readonly />
						   <input type="text" id="vvNote5" name="vvNote5" value="${result.vvNote5}" class="wd100" readonly />
						</td>
					</tr>
				    <tr style = "display:none;">
						<th scope="row"><label><s:message code="ETC.NM" /></label></th> <!-- 기타명 -->
						<td class="bd_none" colspan="3">
						   <input type="text" id="vvNoteNm1" name="vvNoteNm1" value="${result.vvNoteNm1}" class="wd100" readonly />
						   <input type="text" id="vvNoteNm2" name="vvNoteNm2" value="${result.vvNoteNm2}" class="wd100" readonly />
						   <input type="text" id="vvNoteNm3" name="vvNoteNm3" value="${result.vvNoteNm3}" class="wd100" readonly />
						   <input type="text" id="vvNoteNm4" name="vvNoteNm4" value="${result.vvNoteNm4}" class="wd100" readonly />
						   <input type="text" id="vvNoteNm5" name="vvNoteNm5" value="${result.vvNoteNm5}" class="wd100" readonly />
						</td>
					</tr>
					 <tr style = "display:none;">
						<th scope="row"><label for="outlCntn1"><s:message code="OUTL.1" /></label></th> <!-- 적요1 -->
						<td colspan="3"><textarea class="wd98p" id="outlCntn1" name="outlCntn1" readonly>${result.outlCntn1}</textarea></td>
					</tr>
					 <tr style = "display:none;">
						<th scope="row"><label for="outlCntn2"><s:message code="OUTL.2" /></label></th> <!-- 적요2 -->
						<td colspan="3"><textarea class="wd98p" id="outlCntn2" name="outlCntn2" readonly>${result.outlCntn2}</textarea></td>
					</tr>
                    <tr>
						<th scope="row"><label for="objDescn"><s:message code="RMRK" /></label></th> <!-- 비고 -->
						<td colspan="3"><textarea class="wd98p" id="objDescn" name="objDescn" readonly>${result.objDescn}</textarea></td>
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