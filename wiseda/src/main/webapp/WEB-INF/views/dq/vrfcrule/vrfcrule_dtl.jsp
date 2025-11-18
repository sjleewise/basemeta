<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="VRFC.DTL.INFO" /></title> <!-- 메뉴상세정보 -->
<script type="text/javascript">
$(document).ready(function(){
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<br><s:message code="VALID.REQUIRED" />";

	//폼검증
	$("#frmInput").validate({
		rules: {
			vrfcNm			: "required",
			vrfcTyp		: "required",
			vrfcRule			: "required",
			dqiLnm			: "required"
	
		},
		messages: {
			vrfcNm			: requiremessage,
			vrfcTyp		: requiremessage,
			vrfcRule			: requiremessage,
			dqiLnm			: requiremessage

		}
	});
	
	//조회 결과값 초기 셋팅
	$("#frmInput #vrfcId").val('${result.vrfcId}');
	$("#frmInput #vrfcNm").val('${result.vrfcNm}');
	$("#frmInput #vrfcTyp").val('${result.vrfcTyp}'); 	
	
	if ("U" == $("#saction").val()) {
		//alert("업데이트일경우");
		$("input[name=vrfcId]").attr('readonly', true);
	}
	
	
	//폼 저장 버튼 초기화...
	$('#btnfrmSave').click(function(event){
		//event.preventDefault();  //브라우저 기본 이벤트 제거...
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	
	
	//폼 초기화 버튼 초기화...
	$('#btnfrmReset').click(function(event){
		//resetForm($("form[name=frmInput]"));
		doAction("Add");
		
	});
	
	//$("#frmInput input[name=vrfcNm]").focus();
	
	// 품질지표 조회
    $("#frmInput #btnApply").click(function() {
    	var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=VRFC"; 
    		param += "&dqiIds="+$("#frmInput #dqiId").val();
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
    });
	
	//검증유형이 채워져있는 경우에만 검증유형 disable 처리. (검증유형 비어있으면 신규로 판단)
	if($("#frmInput #vrfcTyp").val() != ''){
		$("#frmInput #vrfcTyp").attr("disabled",true);	
	} else {
		$("#frmInput #vrfcTyp").attr("disabled",false);
	}
});

</script>
</head>
<body>
   <div class="stit"><s:message code="VRFC.DTL.INFO" /></div> <!-- 검증룰 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="post">
   	<input type="hidden" id="saction" name="saction" value="${saction}" >
   	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
   	<input type="hidden" id="vrfcId" name="vrfcId" value="${result.vrfcId}" >
   	<input type="hidden" id="objVers" name="objVers" value="${result.objVers}" >
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
      <tr>
        <th class="th_require"><s:message code="VRFC.NM" /></th> <!-- 검증룰명 -->
        <td colspan="3">
        	<input type="text" name="vrfcNm" class="wd300" style="width:98%;" value="${result.vrfcNm}" >
        </td>
      </tr>
      
      <tr >
      	 <th scope="row" class="th_require"><label for="dqiSearch"><s:message code="QLTY.INDC"/></label></th><!--품질지표-->
         <td colspan="1">
             <input type="text"  style="width: 70%;" name="dqiLnm" id="dqiLnm" value="${result.dqiLnm}" readonly />
             <input type="hidden" name="dqiId" id="dqiId" value="${result.dqiId}" />
              &nbsp;&nbsp;&nbsp;
             <input style="background-color: #2682ca;" type="button" id="btnApply" name="btnApply" class="btn_save" value="<s:message code='INQ' />" />
         </td>
         
        <th class="th_require"><s:message code="VRFC.TYP" /></th> <!-- 검증유형 -->
        <td colspan="1">
        	<select id="vrfcTyp" name="vrfcTyp" class="wd80" > <!-- onFocus='this.initialSelect = this.selectedIndex;' onChange='this.selectedIndex = this.initialSelect;' -->
                <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
	            <c:forEach var="code" items="${codeMap.vrfcTyp}" varStatus="status">
	            <option value="${code.codeCd}">${code.codeLnm}</option>
	            </c:forEach>
            </select>
        </td>
      </tr>
      <tr>
        <th class="th_require"><s:message code="VRFC.RULE" /></th> <!-- 검증룰 -->
        <td colspan="3">
        <%-- <input type="text" name="vrfcRule" class="wd300" style="width:98%;" value="${result.vrfcRule}" > --%>
        	<textarea rows="2" cols="100" name="vrfcRule" style="width:98%;word-spacing:5px;">${result.vrfcRule}</textarea>
        </td>
      </tr>
      
      <tr>
        <th><s:message code="VRFC.DESCN" /></th> <!-- 검증룰 설명 -->
        <td colspan="3">
        <%-- <input type="text" name="vrfcDescn" class="wd300" value="${result.vrfcDescn}"  > --%>
        <textarea rows="3" cols="100" style="width:98%;word-spacing:5px;" name="vrfcDescn" >${result.vrfcDescn}</textarea>
        </td>
      </tr>
    </table>
    </div>
    </form>
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div id="divFrmBtn" style="text-align: center;">
		<button class="btn_frm_save" id="btnfrmSave" name="btnfrmSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_frm_reset" id="btnfrmReset" name="btnfrmReset" ><s:message code="INON" /></button> <!-- 초기화 -->
	</div>


</body>
</html>