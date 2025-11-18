<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="SCDU.MNG.BTCH.INFO" /></title> <!-- 스케줄 관리 배치정보 -->

<script type="text/javascript">
$(document).ready(function() {
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			shdLnm	: "required",
			shdUseYn	: "required",
// 			shdBprYn	: "required",
			shdKndNm: "required",
			shdKndCd : "required",
			erDataLdCnt : {
			    required: false,
			    range: [1, 1000]
		    },
		    pkDataLdCnt : {
			    required: false,
			    range: [1, 5]
		    }
		},
		messages: {
			shdLnm	: requiremessage,
			shdUseYn	: requiremessage,
			shdBprYn	: requiremessage,
			shdKndNm: requiremessage,
			shdKndCd: requiremessage,
			erDataLdCnt: "<s:message code='MSG.EROR.DATA.LOAD.CCNT' />", /* 오류데이터 적재건수는 1000건을 넘을 수 없습니다. */
			pkDataLdCnt: "<s:message code='MSG.PK.DATA.LOAD.CCNT' />" /* PK데이터 적재건수는 5건을 넘을 수 없습니다. */
		}
	});

	//select box 값 초기화... 
// 	$("#frmInput #anaDgr").val('${result.anaDgr}');

	$("#shdKndCd").change(function(){
		
		//작업정보 초기화
		grid_sub.RemoveAll();
		
		var selVal = $("#shdKndCd option:selected").val(); 
		var selText = $("#shdKndCd option:selected").text();
		
		if (selVal == "QP"){		//프로파일
			setCodeSelect("prfAnaDgr", "L", $("form[name=frmInput] #anaDgr"));
			$("#Rdegree").show();
			$("#Pk_data").show();
			$("#er_data_layer").show();
		}else if(selVal == "QB"){	//업무규칙
			setCodeSelect("brAnaDgr", "L", $("form[name=frmInput] #anaDgr"));
			$("#Rdegree").show();
			$("#Pk_data").hide();
			$("#er_data_layer").show();
		}else if(selVal == "CR"){	//검증룰
			setCodeSelect("vrfcAnaDgr", "L", $("form[name=frmInput] #anaDgr"));
			$("#Rdegree").show();
			$("#Pk_data").hide();
			$("#er_data_layer").show();
		}else{
			$("#Rdegree").hide();
			$("#Pk_data").hide();
			$("#er_data_layer").hide();
		}
		
	});
	
});	
</script>

</head>
<body>
	  	<div class="stit"><s:message code="SCDU.DTL.INFO" /></div> <!-- 스케줄 상세정보 -->
			<div style="clear:both; height:5px;"><span></span></div>
			<div class="tb_basic">
			<form name="frmInput" id="frmInput" class="frmInput" method="post" action="">
			<input type="hidden" id="saction" name="saction" value="${saction }"/>
			<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" />
			<input type="hidden" id="shdId" name="shdId" value="${result.shdId}" />
			<input type="hidden" id="regTypCd" name="regTypCd" value="${result.regTypCd}" />
			<input type="hidden" id="Sseq" name="Sseq" />
			<input type="hidden" id="formChkDetail" name="formChkDetail" />
			<input type="hidden" id="formChkDtl" name="formChkDtl" />
			<input type="hidden" id="shdJobId" name="shdJobId" />
				<table border="0" cellspacing="0" cellpadding="0"  summary="">
		        <caption>
		        <s:message code="SCDU.DTL.INFO" /></div> <!-- 스케줄 상세정보 -->
		        </caption>
		        <colgroup>
		            <col style="width:12%;">
		            <col style="width:38%;">
		            <col style="width:12%;">
		            <col style="width:38%;">
		        </colgroup>
			      <tr>
			        <th><s:message code="SCDU.PTRN" /></th> <!-- 스케줄유형 -->
			        <td colspan="3">
<!--                        		<input type="text" id="shdKndNm" name="shdKndNm" readonly/> -->
						<select id="shdKndCd"  name="shdKndCd" class="input" >
                      		<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.shdKndCd}" varStatus="status">
							<option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
						</select>
			        </td>
			      </tr>
			      <tr>
			        <th><s:message code="SCDU.NM" /></th> <!-- 스케줄명 -->
			        <td colspan="3">
			        <input type="text" id="shdLnm" name="shdLnm" class="wd60p"  value="${result.shdLnm }"/>
			        </td>
			      </tr>
			      <tr>
                     <th scope="row"><label for="objDescn"><s:message code="SCDU.TXT" /></label></th> <!-- 스케줄 설명 -->
                     <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey="" value="${result.objDescn}" class="input" style="width:98%;" ></textarea></td>
                  </tr>
			      <tr>
              	      
			        <th><s:message code="SCDU.USE.YN" /></th> <!-- 스케줄사용여부 -->
			        <td>
			        <select id="shdUseYn" name="shdUseYn" class="input" value="${result.shdUseYn }">
			        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
			        	<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
			        </select>
			        </td>

			        <th><s:message code="SCDU.BNDL.TRTT.YN" /></th> <!-- 스케줄일괄처리여부 -->
			        <td>
			        <select id="shdBprYn" name="shdBprYn" class="input" value="${result.shdBprYn }">
			        	<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
			        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
			        	<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
			        </select>
			        </td>
			      </tr>
			 <tr id="er_data_layer" style="display:none;">
			        <th><s:message code="EROR.DATA.LOAD.YN" /></th> <!-- 오류데이터 적재여부 -->
			        <td>
			        <select id="erDataLdYn" name="erDataLdYn" class="input" value="${result.erDataLdYn }">
			        	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
			        	<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
			        </select>
			        </td>
	                 <th scope="row"><label for="erDataLdCnt"><s:message code="EROR.DATA.LOAD.CCNT" /></label></th> <!-- 오류데이터 적재건수 -->
	                 <td colspan="1"><input type="text" id="erDataLdCnt" name="erDataLdCnt" value="${result.erDataLdCnt}" class="input" /></td>
             </tr>
              
             <tr id="Pk_data" style="display:none;">
				<th><s:message code="PK.DATA.LOAD.YN" /></th> <!-- PK데이터 적재여부 -->
			      <td>
			      <select id="pkDataLdYn" name="pkDataLdYn" class="input" value="${result.pkDataLdYn }">
			      	<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
			      	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
			      	<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
			      </select>
			      </td>
               <th scope="row"><label for="pkDataLdCnt"><s:message code="PK.DATA.LOAD.CCNT" /></label></th> <!-- PK데이터 적재건수 -->
               <td colspan="1"><input type="text" id="pkDataLdCnt" name="pkDataLdCnt" value="${result.pkDataLdCnt}" class="input" /></td>
           </tr>

				<tr id="Rdegree" style="display:none;">
			        <th><s:message code="ANLY.ODR" /></th> <!-- 분석차수 -->
			        <td>
			        <select id="anaDgr" name="anaDgr" class="input" value="${result.anaDgr}">
			        		<option value="">--- <s:message code="CHC" /> ---</option> <!-- 선택 -->
						    <c:forEach var="code" items="${codeMap.anaDgr}" varStatus="status">
						    <option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>
			        </select>
			        </td>
			        
			         <th scope="row"><label for="anaDgrAutoIncYn"><s:message code="ANLY.ODR.ATMD.INC.YN" /></label></th> <!-- 분석차수 자동증가여부 -->
                        <td colspan="1"><span class="input_check" >
                            <input type="checkbox" id="anaDgrAutoIncYn" name="anaDgrAutoIncYn" value="Y" /> </span>
                        </td>
                 </tr>

			    </table>
			    </form>
			</div>
			<div style="clear:both; height:10px;"><span></span></div> 
			
			<!-- 배치정보 상세 -->
			<%@include file="schedule_batch_dtl.jsp" %>
			<!--  배치정보 상세 끝 -->
</body>


</html>