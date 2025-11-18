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
<title>DDL테이블상세정보</title>

<script type="text/javascript">

$(document).ready(function(){
	
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	$("#frmInput").validate({
		rules: {
			rqstDcd		: "required",
			etcObjDcd	: "required",
			dbSchId 	: "required",
			ddlEtcPnm   : "required",			
			
		},
		messages: {
			rqstDcd		: requiremessage,
			etcObjDcd	: requiremessage,
			dbSchId 	: requiremessage, 
			ddlEtcPnm   : requiremessage, 			
			
		}
	});
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	
	
	//alert("조회완료");
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=progrmFileNm]").attr('readonly', true);
// 	}
	
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		
		if ($("#mstFrm #rqstStepCd").val() == "Q") {
			var srow = grid_sheet.GetSelectRow();
// 			alert(srow);
// 			alert($("#frmInput #rvwConts").val());
			grid_sheet.SetCellValue(srow, "rvwConts", $("#frmInput #rvwConts").val());
			return;
		}
				
		//IBSheet 저장용 JSON 전역변수 초기화
		ibsSaveJson = null;
		
		//변경한 시트 단건 내용을 저장...
// 		alert("단건저장");
		//폼 검증...
		if(!$("#frmInput").valid()) return false;
		
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'SaveRow');
		
	});
	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//alert("폼 초기화");
// 		$("form[name=frmInput]")[0].reset();
		resetForm($("form#frmInput"));
		/* var row = grid_sheet.GetSelectRow();
		if(row < 1) {
			$("form#frmInput")[0].reset();
		    //선택행 셋팅..
		    var tmptit = "테이블을 선택하세요.";
		    $("#tbl_sle_title").html(tmptit);
		} else {
			tblClick(row);
		} */
		
	});
	
	
	double_select(connTrgSchJson, $("#frmInput #dbConnTrgId"));
   	$('select', $("#frmInput #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});

    
// 	$("#frmInput #stwdLnm").focus();

	//alert('1');
	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);
	
	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	$("#frmInput #rvwStsCd").val('${result.rvwStsCd}');

	
	$("#frmInput #etcObjDcd").val('${result.etcObjDcd}');
	$("#frmInput #dbConnTrgId").val('${result.dbConnTrgId}');

	$("#frmInput #dbConnTrgId").change();

	$("#frmInput #dbSchId").val('${result.dbSchId}');

	//check box 값 초기화...
	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();
	
	

});

//DBMS 정보 팝업 리턴값 처리
function returnDbSchemaPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #tblSpacPnm").val(retjson.dbTblSpacPnm);
	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbSchPnm").val(retjson.dbSchPnm);
// 	$("#frmInput #tblSpacPnm").val(retjson.tblSpacPnm);
}


</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" >
	  	
	  	<input type="hidden" id="dbConnTrgPnm" name="dbConnTrgPnm"  />
	  	<input type="hidden" id="dbSchPnm" name="dbSchPnm"          />
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	
	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">DBMS 정보</div>  <!-- DBMS 정보 -->
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>주제영역정보</legend>
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>DBMS 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:*;" />  
			</colgroup>
			<tbody>
				<tr>
				    <th scope="row" class="th_require">오브젝트유형</th> 
					<td>
						<select id="etcObjDcd" name="etcObjDcd">
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.etcObjDcd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
					 	</select>
					</td>
					
					<th scope="row" class="th_require">DBMS 정보</th> <!-- DBMS 정보 -->
					<td>
						<select id="dbConnTrgId" class="" name="dbConnTrgId">
					    	<option value="">전체</option> <!-- 전체 -->
					    </select>
					    <select id="dbSchId" class="" name="dbSchId">
					    	<option value="">전체</option> <!-- 전체 -->
					    </select>
					</td>
					
				</tr>
			</tbody> 
		</table>
	</div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit">오브젝트정보</div>  <!-- 테이블 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend>머리말</legend>
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
               <caption>테이블 이름</caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:*;" />  
              
               </colgroup>
               <tbody>
                   <tr>
					    <th scope="row" class="th_require">오브젝트명</th> 
						<td>
							<input type="text" id="ddlEtcPnm" name="ddlEtcPnm" class="wd98p" value="${result.ddlEtcPnm}" />							
						</td>
						<th scope="row">오브젝트한글명</th> 
						<td>
							<input type="text" id="ddlEtcLnm" name="ddlEtcLnm" class="wd98p" value="${result.ddlEtcLnm}" />							
						</td>				
				   </tr>
                   <tr>
                       <th scope="row"><label for="scrtInfo">스크립트</label></th>  
                       <td colspan=3 >
                       		<textarea id="scrtInfo" name="scrtInfo" rows=20 class="wd98p">${result.scrtInfo}</textarea>
                       </td>
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
