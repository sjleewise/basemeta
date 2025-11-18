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
<title>DDL시퀀스상세정보</title>

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
			ddlObjPnm	: "required",
			grtorDbPnm 	: "required", 
			grtorSchPnm 	: "required",
			grtedDbPnm 	: "required", 
			grtedSchPnm 	: "required",

//			incby 	: "required", 
//			strtwt 	: "required", 
//			minval 	: "required", 
//			maxval 	: "required",
		},
		messages: {
			rqstDcd		: requiremessage,
			ddlObjPnm	: requiremessage,
			grtorDbPnm 	: requiremessage, 
			grtorSchPnm 	: requiremessage,
			grtedDbPnm 	: requiremessage, 
			grtedSchPnm 	: requiremessage,

//			incby 	: requiremessage,
//			strtwt 	: requiremessage,
//			minval 	: requiremessage,
//			maxval 	: requiremessage,
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
		resetForm($("form#frmInput"));

	});
	
	
	
	//DBMS ROLE 검색 팝업 호출
	$("#DbmsRolePop").click(function(){
    	var param = "dbConnTrgPnm=" + $("form#frmInput #grtorDbPnm").val();
    	openLayerPop("<c:url value='/meta/ddl/popup/dbrole_pop.do' />", 600, 500, param);
    });

	//테이블 검색 팝업 호출
	$("#objSearchPop").click(function(){
		var url   = "<c:url value="/meta/ddl/popup/ddlallobjforgrt_pop.do"/>";
		var param = "";
		openLayerPop(url, 1000, 600, param);
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
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	
	$("#frmInput #rvwStsCd").val('${result.rvwStsCd}');
	$("#frmInput #rvwConts").val('${result.rvwConts}');
	$("#frmInput #ddlObjTypCd").val('${result.ddlObjTypCd}');
	
	//신규추가(빈화면)일 경우 SELECT 권한 기본 체크
	if($("#frmInput #regTypCd").val()=="" && $("#frmInput #vrfCd").val()!="5"){
		$("#frmInput #selectYn").prop('checked', true);
	}

	if("${result.selectYn}" == "Y") $("#frmInput #selectYn").prop('checked', true);
	if("${result.insertYn}" == "Y") $("#frmInput #insertYn").prop('checked', true);
	if("${result.updateYn}" == "Y") $("#frmInput #updateYn").prop('checked', true);
	if("${result.deleteYn}" == "Y") $("#frmInput #deleteYn").prop('checked', true);
	//if("${result.executeYn}" == "Y") $("#frmInput #executeYn").prop('checked', true);
	
	
	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();
	

});

//DBMS 정보 팝업 리턴값 처리
function returnDbRolePop(ret) {
	var retjson = jQuery.parseJSON(ret);

	$("#frmInput #grtedDbPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #grtedSchPnm").val(retjson.rolePnm);

}


//오브젝트검색 팝업 리턴값 처리
function returnDdlTblPop (ret) {
	var retjson = jQuery.parseJSON(ret);

	$("#frmInput #grtorDbPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #grtorSchPnm").val(retjson.dbSchPnm);
	$("#frmInput #ddlObjPnm").val(retjson.objPnm);
	$("#frmInput #ddlObjTypCd").val(retjson.objDcd);
	
}



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
	<div class="stit">DBMS 정보</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>DBMS 정보</legend>
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>DBMS 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="th_require">Grantor DBMS 정보</th>
					<td>
						<span class="input_inactive"><input type="hidden" class="wd100" id="grtorDbId" name="grtorDbId"  value="${result.grtorDbId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="hidden" class="wd100" id="grtorSchId" name="grtorSchId"  value="${result.grtorSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="grtorDbPnm" name="grtorDbPnm"  value="${result.grtorDbPnm}"  readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="grtorSchPnm" name="grtorSchPnm"  value="${result.grtorSchPnm}"  readonly="readonly"/></span>
					</td>
					<th scope="row" class="th_require"><label for="ddlSeqPnm">오브젝트명</label></th>
                    <td>
                    	<span class="input_inactive"><input type="hidden" class="wd100" id="ddlObjId" name="ddlObjId"  value="${result.ddlObjId}" readonly="readonly"/></span>
                    	<input type="text" id="ddlObjPnm" name="ddlObjPnm"  class="wd200" value="${result.ddlObjPnm }"  readonly="readonly"/>
                    	<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="objSearchPop">검색</button>
                   	</td>
				</tr>
				<tr>
					<th scope="row" class="th_require">Granted to DBMS 정보</th>
					<td>
						<span class="input_inactive"><input type="hidden" class="wd100" id="grtedDbId" name="grtedDbId"  value="${result.grtedDbId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="hidden" class="wd100" id="grtedSchId" name="grtedSchId"  value="${result.grtedSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="grtedDbPnm" name="grtedDbPnm"  value="${result.grtedDbPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="grtedSchPnm" name="grtedSchPnm"  value="${result.grtedSchPnm}" readonly="readonly"/></span>
						<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="DbmsRolePop">검색</button>
					</td>
					<th scope="row"><label for="ddlObjTypCd">오브젝트유형</label></th>
                    <td>
                   		<select id="ddlObjTypCd" name="ddlObjTypCd" value = "${result.ddlObjTypCd}" class="wd100" disabled="disabled">
                    		<option value="" selected></option>
                    	<c:forEach var="code" items="${codeMap.objDcd}" varStatus="status" >
                           <option value="${code.codeCd}">${code.codeLnm}</option>
                        </c:forEach>
                        </select>
                    </td>
				</tr>
			</tbody>
		</table>
	</div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit">권한 정보</div>
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend>머리말</legend>
       <div class="tb_read">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
               <caption>
               시퀀스 이름
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:85%;" />
               </colgroup>
               <tbody>

					
 <!--                  	<tr>
	                    <th scope="row"><label for="rqstResn">요청사유</label></th>
	                    <td colspan="3"><textarea id="rqstResn" name="rqstResn" class="wd98p">${result.rqstResn }</textarea></td>
	                </tr>	-->
                   <tr>
                       <th scope="row"><label for="objDescn">설명</label></th>  <!--설명  -->
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn }</textarea></td>
                   </tr>
                   <tr>
                       <th scope="row"><label for="grantYn">권한</label></th>
                       <td><!-- 
                       		<span class="input_check"><input type="checkbox" id="selectYn" name="selectYn" disabled="disabled"/> SELECT</span>&nbsp;&nbsp;&nbsp;
                       		<span class="input_check"><input type="checkbox" id="insertYn" name="insertYn" disabled="disabled"/> INSERT</span>&nbsp;&nbsp;&nbsp;
                       		<span class="input_check"><input type="checkbox" id="updateYn" name="updateYn" disabled="disabled"/> UPDATE</span>&nbsp;&nbsp;&nbsp;
                       		<span class="input_check"><input type="checkbox" id="deleteYn" name="deleteYn" disabled="disabled"/> DELETE</span>&nbsp;&nbsp;&nbsp;
                       		<span class="input_check"><input type="checkbox" id="executeYn" name="executeYn" disabled="disabled"/> EXECUTE</span> -->
                       		<input type="checkbox" id="selectYn" name="selectYn"/> SELECT&nbsp;&nbsp;&nbsp;
                       		<input type="checkbox" id="insertYn" name="insertYn"/> INSERT&nbsp;&nbsp;&nbsp;
                       		<input type="checkbox" id="updateYn" name="updateYn"/> UPDATE&nbsp;&nbsp;&nbsp;
                       		<input type="checkbox" id="deleteYn" name="deleteYn"/> DELETE&nbsp;&nbsp;&nbsp;
                       </td>
<!--                        <th scope="row"><label for="synonymYn">SYNONYM여부</label></th>
                       <td>
                       		<select id="synonymYn"  name="synonymYn" value="${result.synonymYn}" class="wd100">
							       <option value="Y" selected>Y</option>
							       <option value="N">N</option>
							</select>  
                       </td> -->
                   </tr>
                   <tr>
                       <th scope="row"><label for="scrtInfo">스크립트정보</label></th>
                       <td colspan="3"><textarea id="scrtInfo" name="scrtInfo" class="wd98p" rows="10" readonly>${result.scrtInfo }</textarea></td>
                   </tr>
               </tbody>
           </table>
       </div>
       </fieldset>
       </form>
	<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<div style="clear:both; height:30px;"><span></span></div>
	<!-- 입력폼 버튼... -->
</div>
</body>
</html>
