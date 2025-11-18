<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<script type="text/javascript">

      $(document).ready(function(){
    	  $("#btnRvwSave").click(function(){
    		  if(grid_sheet.GetSelectRow() < 0){
  			    	showMsgBox("INF", "<s:message code="ERR.NOSELECT" />");
  			    	
    			  return false;
    		  }
    		  grid_sheet.SetCellValue(grid_sheet.GetSelectRow(), "rvwConts",$("#rvwConts").val());
    		  grid_sheet.SetCellValue(grid_sheet.GetSelectRow(), "rvwStsCd",$("#rvwStsCd").val());

    	  }).hide();
    	  if($("#mstFrm #rqstStepCd").val()=='Q'){
    		  $("#btnRvwSave").show();
    	  } 
      });
</script>
	<div class="reviewStatus" id="reviewStatus" style="display: none;">
		<div class="stit"><s:message code="REVW.STS" /></div> <!-- 검토상태 -->
		<div class="bt02">
					<button class="btn_reg_rqst" type="button" id="btnRvwSave" name="btnRvwSave"><s:message code="REVW.CNTN.RFLC" /></button> <!-- 검토내용반영 -->
				</div> 
		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="REVW.RSLT" /></legend> <!-- 검토결과 -->
		<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='REVW.CNTN.INFO' />"> <!-- 검토내용정보 -->
			<caption><s:message code="REVW.CNTN.INFO.INPT" />	</caption> <!-- 검토내용 정보입력 -->
			<colgroup>
			<col style="width:15%;" />
			<col style="width:*;" />
			
			</colgroup>
			<tbody>
			<tr>
				<th scope="row"><label for="rvwStsCd"><s:message code="REVW.STS" /></label> <!-- 검토상태 -->
				</th>
				<td>
					<select id="rvwStsCd"  name="rvwStsCd" >
					    <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
					    <c:forEach var="code" items="${codeMap.rvwStsCd}" varStatus="status">
					    <option value="${code.codeCd}">${code.codeLnm}</option>
					    </c:forEach>
					</select>
				
				</td>
<!-- 				<th scope="row"><label for="allAply">전체적용</label></th> -->
<!-- 				<td> -->
<!-- 					<span class="input_check"><input type="checkbox" id="allAply" name="allAply" value="Y"/>검토내용 전체적용</span> -->
<!-- 				</td> -->
			</tr>
			<tr>
				<th scope="row"><label for="rvwConts"><s:message code="REVW.CNTN" /></label></th> <!-- 검토내용 -->
				<td colspan="3"><textarea id="rvwConts" name="rvwConts"  class="wd98p" >${result.rvwConts }</textarea></td>
			</tr>
			</tbody>
		</table>
		</div>
		<div style="clear:both; height:10px;"><span></span></div>
	</div>
	<div style="float:left; width:49%;">
		<div class="stit"><s:message code="DEMD.STS" /></div> <!-- 요청상태 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="DEMD.STS" /></legend> <!-- 요청상태 -->
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DEMD.STS' />">
				<caption><s:message code="STRD.WORD.DEMD.STS" /></caption> <!-- 표준단어 요청상태 -->
				<colgroup>
				<col style="width:10%;" />
				<col style="width:40%;" />
				<col style="width:10%;" />
				<col style="width:40%;" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" class="th_require"><label for="rqstDcd"><s:message code="DEMD.DSTC" /></label></th> <!-- 요청구분 -->
						<td>
							<select id="rqstDcd"  name="rqstDcd">
<!-- 							    <option value="">선택</option> -->
							    <c:forEach var="code" items="${codeMap.rqstDcd}" varStatus="status">
							    <option value="${code.codeCd}">${code.codeLnm}</option>
							    </c:forEach>
							</select>
						</td>
					
						<th scope="row"><label for="regTypCd"><s:message code="REG.PTRN" /></label></th> <!-- 등록유형 -->
						<td>
							<select id="regTypCd"  name="regTypCd" disabled="disabled">
							    <option value=""></option>
							    <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status">
							    <option value="${code.codeCd}">${code.codeLnm}</option>
							    </c:forEach>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div style="float:right; width:49%;">
		<div class="stit"><s:message code="VRFC.RSLT" /></div> <!-- 검증결과 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
				<caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
				<colgroup>
				<col style="width:10%;" />
				<col style="width:40%;" />
				<col style="width:10%;" />
				<col style="width:40%;" />
				</colgroup>
				<tbody>
				<tr>
					<th scope="row"><label for="vrfCd"><s:message code="VRFC.RSLT" /></label></th> <!-- 검증결과 -->
					<td>
						<select id="vrfCd"  name="vrfCd" disabled="disabled">
						    <option value=""></option>
						    <c:forEach var="code" items="${codeMap.vrfCd}" varStatus="status">
						    <option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>
						</select>
					</td>
				
					<th scope="row"><label for="vrfRmk"><s:message code="VRFC.RMRK" /></label></th> <!-- 검증비고 -->
					<td><input type="text" name="vrfRmk" id="vrfRmk" class="wd200" value="${result.vrfRmk }" disabled="disabled" /></td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>

