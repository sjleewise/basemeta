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
<title><s:message code="PHYC.MDEL.REG.DTL.INFO"/></title> <!-- 도메인그룹등록상세정보 -->

<script type="text/javascript">

$(document).ready(function(){
	$("#objDescn").height("98px");
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			rqstDcd		: "required",
			stndAsrt    : "required",
			dmngPnm	    : "required",
			dmngLnm 	: "required",
			dmngLvl 	: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			stndAsrt    : requiremessage,
			dmngPnm	    : requiremessage,
			dmngLnm 	: requiremessage,
			dmngLvl 	: requiremessage
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
		
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		resetForm($("form#frmInput"));		
	});
	
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	if('${result.rqstDcd}'!=""){$("#frmInput #rqstDcd").val('${result.rqstDcd}');}
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	
	$("#frmInput #rvwStsCd").val('${result.rvwStsCd}');
	$("#frmInput #rvwConts").val('${result.rvwConts}');
	 $("#frmInput #stndAsrt").val("${result.stndAsrt}");
	 $("#frmInput #cdDmnYn").val("${result.cdDmnYn}");

	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();

});



//화면 로드시 공통 처리부분 여기에 추가한다.
$(window).on('load',function() {
	
});




</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" >
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="TBL.INFO" /></div> <!-- 도메인그룹 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 도메인그룹 서머리입니다. -->
               <caption>
               <s:message code="TBL.NM1" /> <!-- 도메인그룹 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   <tr>
			    	   <th scope="row" class="th_require"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					   <td colspan="3">
					      <select  id="stndAsrt" name="stndAsrt"  class="wd300">
					              <option value=""><s:message code="CHC"/></option>
					              <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					      		  <option value="${code.codeCd}">${code.codeLnm}</option>
					      		</c:forEach>
					      </select>
					   </td>
			       </tr>
                   <tr>
                       <th scope="row" class="th_require"><label for="dmngLnm"><s:message code="DMN.GRP.LGC.NM" /></label></th> <!-- 도메인그룹명(논리) -->
                       <td><input type="text" id="dmngLnm" name="dmngLnm" class="wd500" value="${result.dmngLnm }"/></td> <!-- 도메인그룹명은 반드시 입력해야 합니다. -->
                       <th scope="row" class="th_require"><label for="dmngPnm"><s:message code="DMN.GRP.PHYC.NM" /></label></th> <!-- 도메인그룹명(물리) -->
                       <td><input type="text" id="dmngPnm" name="dmngPnm" class="wd500" value="${result.dmngPnm }"  /></td>
                   </tr>
                   <tr>
                       <th scope="row" ><label for="uppDmngLnm"><s:message code="UPRN.DMN.GRP.LGC.NM" /></label></th> <!-- 상위도메인그룹논리명 -->
                       <td><input type="text" id="uppDmngLnm" name="uppDmngLnm" class="wd500" value="${result.uppDmngLnm }"/></td> <!-- 도메인그룹명은 반드시 입력해야 합니다. -->
                       <th scope="row" class="th_require"><label for="dmngLvl"><s:message code="DMN.GRP.LVL" /></label></th> <!-- 레벨 -->
                       <td><input type="text" id="dmngLvl" name="dmngLvl" class="wd500" value="${result.dmngLvl }"  /></td>
                   </tr>
                   <tr>
                       <th scope="row" ><label for="cdDmnYn"><s:message code="CD.DMN.YN" /></label></th>
                       <td colspan="3">
                            <select id="cdDmnYn" name="cdDmnYn">
							  <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
							  <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
						    </select>
					   </td> 
                   </tr>
                   
                   <tr class="ht100">
                       <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class = "wd98p" >${result.objDescn }</textarea></td>
                   </tr>
                  
               </tbody>
           </table>
       </div>
       </fieldset>
       </form>
	<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<!-- 입력폼 버튼... -->
</div>
</body>
</html>
