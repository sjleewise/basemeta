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
// 	var numbermessage = "<s:message code="VALID.NUMBER" />";
	
 	//폼검증
	$("#frmDtlInput").validate({
		rules: {
			shdStrDtm	: "required",

			shdDlyVal : {
							required: function(){
								if($('#shdTypCd').val() == "D" && $("input:radio[name='shdDly']:checked").val() == ""){  
									 return true;
								 }else if($("input:radio[name='shdDly']:checked").val() == "02" && $("#shdDlyVal").val() == ""){  
									 return true;
								 }
					             return false;
							}
						},
		
			shdWklVal : {
				required: function(){
					if($('#shdTypCd').val() == "W"){
							 if($('#shdWklVal').val() == "" || $('input[name="schdWkl"]').is(":checked") == "false"){  return true;  }
					}
					return false;
				}
			},
			
			shdMnyVal : {
					required: function(){
						if($('#shdTypCd').val() == "M"){
							 if($('input[name="schdMny"]').is(":checked") == "false" || $('#shdMnyVal').val() == ""){  return true;  }
						}
						return false;
					}
				}
			},
			messages: {
				shdStrDtm	: requiremessage,
				shdDlyVal		: "<s:message code='MSG.RQRD.INPT.ITEM.EXCP.RQRD.INPT.ITEM.CNTN.INPT' />", /* 필수입력항목입니다. '매일'과 '평일'을 제외한 나머지는 날자 간격이 필수입력항목입니다. 내용을 입력해 주세요. */
				shdWklVal : requiremessage,
				shdMnyVal	: requiremessage
			}
	});
 	
	
	//달력팝업 추가...
//  	$( "#shdStrDtm" ).datepicker();
	
	$('#btnDtlSave').click(function(){
 		$("#btnSave").click();
 	}).hide();
	
	//초기화버튼
	$('#btnDtlReset').click(function(){
// 		$("form[name=frmSearch]")[0].reset();
		doAction('Add');
// 		$(".input").val('');
// 		$(".select").val('');
// 		$(".dtlInput").val('');
// 		$("form[name=frmDtlInput] input:checkbox").attr("checked", false);
// 		$("form[name=frmInput] input:checkbox").attr("checked", false);
// 		$("input:radio[name='shdDly']").attr("checked", false);
	}).hide();

	$("#bCd").show();
	$("#shdTypCd").change(function(){
		var selVal = $("form#frmDtlInput #shdTypCd option:selected").val(); 
		
		$("form[name=frmDtlInput] #shdDlyVal").val('');
		$("form[name=frmDtlInput] #shdWklVal").val('');
		$("form[name=frmDtlInput] #shdMnyVal").val('');
		$("form[name=frmDtlInput] input:checkbox").attr("checked", false);
		$("input:radio[name='shdDly']").attr("checked", false);
		
		if(selVal == "W"){	// 매주
			$("select[name=shdStrHr]").attr("disabled",false);
			$("select[name=shdStrMnt]").attr("disabled",false);
			$("#weekCd").show();
			$("#bCd").hide();
			$("#dayCd").hide();
			$("#monthCd").hide();
		}else if(selVal == "D"){		//매일
			$("select[name=shdStrHr]").attr("disabled",false);
			$("select[name=shdStrMnt]").attr("disabled",false);
			$("#dayCd").show();
			$("#weekCd").hide();
			$("#bCd").hide();
			$("#monthCd").hide();
		}else if(selVal == "M"){		//매달
			$("select[name=shdStrHr]").attr("disabled",false);
			$("select[name=shdStrMnt]").attr("disabled",false);
			$("#weekCd").hide();
			$("#bCd").hide();
			$("#dayCd").hide();
			$("#monthCd").show();
		}else if(selVal == "H"){		//매시
			$("form[name=frmDtlInput] #shdStrMnt").val('');
			$("select[name=shdStrHr]").attr("disabled",false);
			$("select[name=shdStrMnt]").attr("disabled",true);
			$("#weekCd").hide();
			$("#bCd").show();
			$("#dayCd").hide();
			$("#monthCd").hide();	
			$("#shdStrHr").focus();
		}else if(selVal == "N"){		//매분
			$("form[name=frmDtlInput] #shdStrHr").val('');
			$("select[name=shdStrHr]").attr("disabled",true);
			$("select[name=shdStrMnt]").attr("disabled",false);
			$("#weekCd").hide();
			$("#bCd").show();
			$("#dayCd").hide();
			$("#monthCd").hide();
			$("#shdStrMnt").focus();
		}else{
			$("select[name=shdStrHr]").attr("disabled",false);
			$("select[name=shdStrMnt]").attr("disabled",false);
			$("#weekCd").hide();
			$("#bCd").show();
			$("#dayCd").hide();
			$("#monthCd").hide();
		}
	});
	
// 	$("#btnDtlSave").click(function(event){
//  		event.preventDefault();  //브라우저 기본 이벤트 제거...
//  		//폼 검증
//  		if(!$("#frmDtlInput").valid()){
// //  			$("#formChkDtl").val(false);
//  			return false;
//  		}else{
// //  			$("#formChkDtl").val(true);
// //  			if($("#formChkDetail").val() == false){
// 	 			$("#tab-1 a").click();
// 	 	 		if(!$("#frmInput").valid()) return false;
// //  			}
//  		}
 		
//  		//저장할래요? 확인창...
// 		var message = "<s:message code="CNF.SAVE" />";
// 		showMsgBox("CNF", message, 'Save');
//  	});
	

	
});

$(window).on('load',function() {
});

	
</script>

</head>
<body>
	<div class="stit"><s:message code="BTCH.DTL.INFO" /></div> <!-- 배치 상세정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<div class="tb_basic">
	<form name="frmDtlInput" id="frmDtlInput"  class="frmInput"  method="post" action="">
<%-- 		<input type="hidden" id="schdTypCd" name="schdTypCd" value="${result.shdTypCd }"/> --%>
		<table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="BTCH.DTL.INFO" /><!-- 배치 상세정보 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
	      <tr>
	        <th><s:message code="BTCH.FRMN" /></th> <!-- 배치형태 -->
	        <td colspan="3">
	        	<select id="shdTypCd"  name="shdTypCd" class="dtlInput" >
					<c:forEach var="code" items="${codeMap.shdTypeCd}" varStatus="status">
					<option value="${code.codeCd}">${code.codeLnm}</option>
					</c:forEach>
				</select>
	        </td>
	      </tr>
<!--    	      <tr id="bCd" style="display:none;"> -->
<!-- 	        <th>배치상세</th> -->
<!-- 	        <td colspan="3"> -->
<!-- 	        <input type="text" id="bcd" name="bcd"/> -->
<!-- 	        </td> -->
<!-- 	      </tr> -->
	      <tr id="dayCd" style="display:none;">
	        <th><s:message code="BTCH.DTL" /></th> <!-- 배치상세 -->
	        <td colspan="3" >
	        <input type="radio" name="shdDly" class="dtlInput" value="00"/> <s:message code="EVERYDAY" /> <!-- 매일 -->
	        <input type="radio" name="shdDly" class="dtlInput" value="01"/> <s:message code="WEEKDAY" /> <!-- 평일 -->
	        <input type="radio" name="shdDly" class="dtlInput" value="02"/> <s:message code="EVERY" /> <!-- 매 -->
	        <input type="text" style="width:20px;" id="shdDlyVal" name="shdDlyVal" class="dtlInput" value="${result.shdDlyVal }"/> <s:message code="EACHDAY" /> <!-- 일 마다 -->
	        </td>
	      </tr>
	      <tr id="weekCd" style="display:none;">
	        <th><s:message code="BTCH.DTL" /></th> <!-- 배치상세 -->
	        <td colspan="3" >
	        <s:message code="EVERY" /> <input type="text" style="width:20px;" class="dtlInput" id="shdWklVal" name="shdWklVal" value="${result.shdWklVal }"/> <s:message code="EACHWEEK" /> <!-- 매 --> <!-- 주 마다 -->
	        	<c:forEach var="code" items="${codeMap.shdWeekCd}" varStatus="status">
				<span class="input_check" ><input type="checkbox" value="${code.codeCd}" id="schdWkl" name="schdWkl${status.count }"/>${code.codeLnm}</span>
				</c:forEach>
				<s:message code="TO.DD" /> <!-- 요일에 -->
	        </td>
	      </tr>
	      <tr id="monthCd" style="display:none;">
	        <th><s:message code="BTCH.DTL" /></th> <!-- 배치상세 -->
	        <td colspan="3" >
	        <s:message code="MM" /> <c:forEach var="code" items="${codeMap.shdMonthCd}" varStatus="status"> <!-- 월 -->
				<span class="input_check" ><input type="checkbox" value="${code.codeCd}" id="schdMny" name="schdMny${status.count }"/>${code.codeLnm}</span>
				</c:forEach>
				<s:message code="DATE" /> <input type="text" style="width:40px;" id="shdMnyVal" name="shdMnyVal" class="dtlInput" value="${result.shdMnyVal }" /> <!-- 날짜 -->
	        </td>
	      </tr>
	      <tr>
	        <th><s:message code="STRN.DD" /></th> <!-- 시작일 -->
	        <td>
	        <input type="text" id="shdStrDtm" name="shdStrDtm" class="dtlInput"  />
<!-- 	        </td> -->

<!-- 	        <th>시작시간</th> -->
<!-- 	        <td> -->
	        	<select id="shdStrHr"  name="shdStrHr" class="dtlInput" style="display:none;">
					<c:forEach var="code" items="${codeMap.schdHourCd}" varStatus="status">
					<option value="${code.codeCd}">${code.codeLnm}</option>
					</c:forEach>
				</select>
	        	<select id="shdStrMnt"  name="shdStrMnt" class="dtlInput" style="display:none;">
					<c:forEach var="code" items="${codeMap.schdMinCd}" varStatus="status">
					<option value="${code.codeCd}">${code.codeLnm}</option>
					</c:forEach>
				</select>
	        </td>
	      </tr>
	    </table>
    </form>
    <div style="clear:both; height:10px;"><span></span></div>
    <div id="divDtlBtn" style="text-align: center;">
			<button id="btnDtlSave" name="btnDtlSave" class="btn_save"><s:message code="STRG" /></button> <!-- 저장 -->
			<button id="btnDtlReset" name="btnDtlReset" class="btn_reset"><s:message code="INON" /></button> <!-- 초기화 -->
		</div>
	</div>
</body>

</html>