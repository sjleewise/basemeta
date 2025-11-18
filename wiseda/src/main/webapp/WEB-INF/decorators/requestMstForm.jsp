<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>


<script type="text/javascript">
$(document).ready(function(){
	//요청서 셋팅....
	$("#request_mst_pop").position({
		my: "center top",
        at: "center top",
        of: $("div.n4cont")
	});
	
	$("#npop_bt_top_mst div").click(function(){
		//alert('click');
		if($("#npop_bt_top_mst div").hasClass("npop_bt_top_open") == true) {
			$("#request_mst_pop .npop_cont").slideDown("500", function(){
				$("#npop_bt_top_mst div").removeClass("npop_bt_top_open").addClass("npop_bt_top_close");
			});
		} else {
			$("#request_mst_pop .npop_cont").slideUp("500", function(){
				$("#npop_bt_top_mst div").removeClass("npop_bt_top_close").addClass("npop_bt_top_open");
			});
		}
		
	});
	
	//요청번호 사용자 변경 방지
	$("#mstFrm input").attr("readonly", true);
	
	$("#mstFrm #bizDcd").val('${waqMstr.bizDcd}');
	$("#mstFrm #bizDcdNm").val('${waqMstr.bizDcdNm}');
	
	$("#mstFrm #rqstStepCd").val('${waqMstr.rqstStepCd}');
	$("#mstFrm #rqstStepCdNm").val('${waqMstr.rqstStepCdNm}');
	
});
 
</script>

<!-- 상단 팝업 시작 -->
   <div id="request_mst_pop" class="npop" style="position:absolute; width: 418px;" >
   	<div class="npop_cont" style="display: none;">
       	<div class="npop_process">
           	<div class="npop_process_01"></div>
        </div>
           <div class="npop_content">
		     <div class="tb_read">
				<form name="mstFrm" id="mstFrm">
				<!-- 전체승인 및 전체반려시 승인/반려 및 반려 사유를 마스터에서 처리한다. -->
				<input type="hidden" name="rvwStsCd" id="rvwStsCd">
				<input type="hidden" name="rvwConts" id="rvwConts">
				<input type="hidden" name="rqstUserId" id="rqstUserId" value="${waqMstr.rqstUserId}" />
				
			    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
					<caption><s:message code="SUBJ.TRRT.INFO" /></caption> <!-- 주제영역정보 -->
					<colgroup>
<%-- 					<col style="width:15%;" /> --%>
<%-- 					<col style="width:85%;" /> --%>
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><label for="rqstNo"><s:message code="DEMD.NO" /></label></th> <!-- 요청번호 -->
							<td><input type="text" name="rqstNo" id="rqstNo" class="wd98p" value="${waqMstr.rqstNo}" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstNm"><s:message code="LORQ.NM" /></label></th> <!-- 요청서명 -->
							<td><input type="text" name="rqstNm" id="rqstNm" class="wd98p" value="${waqMstr.rqstNm}" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="bizDcd"><s:message code="BZWR.DSTC" /></label></th> <!-- 업무구분 -->
							<td>
							<!-- bizDcd를 selectBox로만 받을경우 stndtot_rqst에서 null로 인식하여 문제발생. 그래서 hidden값으로 추가 -->
							<input type="hidden" id="bizDcd" 	name="bizDcd" 	value="${waqMstr.bizDcd}" />
							<input type="text" id="bizDcdNm" 	name="bizDcdNm" 	value="${waqMstr.bizDcdNm}" />
<%-- 							<select id="bizDcd" class="" name="bizDcd"> --%>
<%-- 							<c:forEach var="code" items="${codeMap.bizDcd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 					 		</select> --%>
							</td>
						</tr>
						<tr style="display:none">
							<th scope="row"><label for="bizDtlCd"><s:message code="BZWR.DSTC.DTL" /></label></th> <!-- 업무구분상세 -->
							<td><input type="text" name="bizDtlCd" id="bizDtlCd" class="wd98p" value="" /></td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstStepCd"><s:message code="DEMD.PRGS.STS" /></label></th> <!-- 요청진행상태 -->
							<td>
							<input type="hidden" id="rqstStepCd" 	name="rqstStepCd" 	value="${waqMstr.rqstStepCd}" />
							<input type="text" id="rqstStepCdNm" 	name="rqstStepCdNm" 	value="${waqMstr.rqstStepCdNm}" />
<%-- 							<select id="rqstStepCd" class="" name="rqstStepCd"> --%>
<%-- 							<c:forEach var="code" items="${codeMap.rqstStepCd}" varStatus="status"> --%>
<%-- 							  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 							</c:forEach> --%>
<%-- 					 		</select> --%>
					 		</td>
						</tr>
						<tr>
							<th scope="row"><label for="rqstResn"><s:message code="DEMD.RSN" /></label></th> <!-- 요청사유 -->
							<td><input type="text" name="rqstResn" id="rqstResn" class="wd98p" value="${waqMstr.rqstResn}" /></td>
						</tr>
						<tr style="display:none">
							<th scope="row"><label for="rqstResn"><s:message code="APRL.STEP" /></label></th> <!-- 결재단계 -->
							<td><input type="text" id="aprLvl" 	name="aprLvl" 	value="${waqMstr.aprLvl}" /></td>
						</tr>
					</tbody>
				</table>
			</form>
			</div>
           </div>
       </div>
       <div id="npop_bt_top_mst" class="npop_bt_top">
<!--        	<div class="npop_bt_top_close"></div> -->
           <div class="npop_bt_top_open"></div> 
<!--            열고 닫을때 화살표 방향바뀜 -->
       </div>
       
   </div>
   <!-- 상단 팝업 끝 -->
