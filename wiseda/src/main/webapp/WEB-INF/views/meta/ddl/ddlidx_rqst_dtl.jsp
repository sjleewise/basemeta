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
<title><s:message code="DDL.IDEX.DTL.INFO" /></title> <!-- DDL인덱스상세정보 -->

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
			rqstDcd		 : "required",
			ddlTblPnm	 : "required",
			ddlIdxPnm	 : "required",
			dbConnTrgPnm : "required", 
			dbSchPnm 	 : "required",
			ddlIdxColAsm : "required", 
		},
		messages: { 
			rqstDcd		 : requiremessage,
			ddlTblPnm	 : requiremessage,
			ddlIdxPnm	 : requiremessage,
			dbConnTrgPnm : requiremessage, 
			dbSchPnm 	 : requiremessage, 
			ddlIdxColAsm : requiremessage,
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
		   
// 		$("form[name=frmInput]")[0].reset();
		resetForm($("form#frmInput"));  
		
		$("#rqstDcd").val("CU");
		
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
	
	
	//DBMS 스키마 검색 팝업 호출
	$("#dbmsSchemaPop").click(function(){
    	var param = "dbConnTrgPnm=" + $("form#frmInput #dbConnTrgPnm").val();
    		param += "&dbSchPnm=" + $("form#frmInput #dbSchPnm").val();
    		param += "&tblSpacTypCd=I";
    	openLayerPop("<c:url value='/commons/damgmt/db/popup/dbschema_pop.do' />", 600, 500, param);
    });
	
	//테이블명 검색팝업 이벤트
    $("#tblPop").click(function(){
    	var param = "";
		openLayerPop ("<c:url value='/meta/ddl/popup/ddltblforidx_pop.do' />", 800, 600, param);
//			var param = $("form#frmColInput").serialize(); //$("form#frmColInput").serialize();
//			openSearchPop("<c:url value='/meta/stnd/pop/sditmSearch_pop.do' />");
    });
	
	//인덱스컬럼 조회
    $("#ddlIdxColPop").click(function(event){ 
    	    	
    	event.preventDefault();  //브라우저 기본 이벤트 제거...
    	
    	if($("#ddlTblPnm").val() == "") {
    		
    		//테이블명을 입력하세요.
    		showMsgBox("ERR", "<s:message code='TBL.INPUT' />");
    		return;
    	}
    	
    	var param = "";
    	
    	param = $("#frmInput").serialize();
    	
    	param += "&popRqst=Y"
    	// alert(param);
    	
		openLayerPop("<c:url value='/meta/ddl/popup/ddlidxcol_pop.do' />", 900, 600, param);
    	
    }).show();

    
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
	if('${result.rqstDcd}'!=""){$("#frmInput #rqstDcd").val('${result.rqstDcd}');}
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	
	$("#frmInput #rvwStsCd").val('${result.rvwStsCd}');
	$("#frmInput #rvwConts").val('${result.rvwConts}');
	
	$("#frmInput #idxTypCd").val('${result.idxTypCd}');
	$("#frmInput #pkIdxYn").val('${result.pkIdxYn}');
	$("#frmInput #ukIdxYn").val('${result.ukIdxYn}');
	
	//check box 값 초기화...
	$("#divInputBtn").show(); 
    			
	//요청자 아닌경우 버튼 disable 
	//disableBtnRqstUser("${result.rqstUserId}", "${sessionScope.loginVO.uniqId}");  	
	
});




//DBMS 정보 팝업 리턴값 처리
function returnDbSchemaPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbSchPnm").val(retjson.dbSchPnm);
	$("#frmInput #dbSchId").val(retjson.dbSchId);
	$("#frmInput #idxSpacPnm").val(retjson.dbTblSpacPnm);
}
//DBMS 정보 팝업 리턴값 처리
function returnDdlTblPop(ret) {
 	//alert(ret);
	var retjson = $.parseJSON(ret);
	
	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbConnTrgId").val(retjson.dbConnTrgId);
	$("#frmInput #dbSchPnm").val(retjson.dbSchPnm);
	$("#frmInput #dbSchId").val(retjson.dbSchId);
	$("#frmInput #ddlTblPnm").val(retjson.ddlTblPnm);
	$("#frmInput #ddlTblId").val(retjson.ddlTblId);
	$("#frmInput #idxSpacId").val(retjson.idxSpacId);
	$("#frmInput #idxSpacPnm").val(retjson.idxSpacPnm);
// 	$("#frmInput #objDescn").val(retjson.objDescn);
}

//인덱스컬럼 팝업 리턴값 처리
function returnDdlIdxCol(ddlIdxColAsm) {
 	
	$("#frmInput #ddlIdxColAsm").val(ddlIdxColAsm);
	
}


</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
<%-- <form name="frmInput" id="frmInput" method="post" enctype="multipart/form-data"> --%>
     <form name="frmInput" id="frmInput" method="post">
     	<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
     	<input type="hidden" id="rqstDtlSno" name="rqstDtlSno" value="${result.rqstDtlSno}" >
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" >
	    <fieldset>
	
		<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
		<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
		<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit"><s:message code="DDL.TBL.INFO" /></div> <!-- DDL테이블 정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="SUBJ.TRRT.INFO" /></legend> <!-- 주제영역정보 -->
		<div class="tb_basic">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
				<caption><s:message code="DDL.TBL.INFO" /></caption> <!-- DDL테이블 정보 -->
				<colgroup>
				   <col style="width:15%;" />
	               <col style="width:35%;" />
	               <col style="width:15%;" />
	               <col style="width:35%;" />
				</colgroup>
				<tbody>
				
					<tr>
						<th scope="row" class="th_require"><s:message code="TBL.NM" /></th>  <!-- 테이블명 -->
						<td colspan="3">
							<span class="input_inactive"><input type="hidden" class="wd100" id="ddlTblId" name="ddlTblId"  value="${result.ddlTblId}" readonly="readonly"/></span>
							<span class="input_inactive"><input type="text"  id="ddlTblPnm" name="ddlTblPnm" style="width:300px;" value="${result.ddlTblPnm}" readonly="readonly"/></span>
							<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
							<button class="btnSearchPop" id="tblPop"><s:message code="SRCH" /></button> <!-- 검색 -->
						</td>	
					</tr>
					<tr>
						
					</tr>
					
					
					<tr>
						<th scope="row" class="th_require"><s:message code="DB.MS.INFO" /></th> <!-- DBMS정보 -->
						<td>
							<span class="input_inactive"><input type="hidden" class="wd100" id="dbConnTrgId" name="dbConnTrgId"  value="${result.dbConnTrgId}" readonly="readonly"/></span>
							<span class="input_inactive"><input type="hidden" class="wd100" id="dbSchId" name="dbSchId"  value="${result.dbSchId}" readonly="readonly"/></span>
							<span class="input_inactive"><input type="text" class="wd100" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd200" value="${result.dbConnTrgPnm}" readonly="readonly"/></span>
							<span class="input_inactive"><input type="text" class="wd100" id="dbSchPnm" name="dbSchPnm" class="wd200" value="${result.dbSchPnm}" readonly="readonly"/></span>
							<!-- 
							<button class="btnDelPop" >삭제</button>
							<button class="btnSearchPop" id="dbmsSchemaPop">검색</button>
							 -->
						</td>
						<th scope="row"><s:message code="IDEX.TBL.SPACE" /></th> <!-- 인덱스 테이블스페이스 -->
						<td>
							<span class="input_inactive"><input type="hidden" class="wd100" id="idxSpacId" name="idxSpacId"  value="${result.idxSpacId}" readonly="readonly"/></span>
							<span class="input_inactive"><input type="text" class="wd300" id="idxSpacPnm" name="idxSpacPnm"  value="${result.idxSpacPnm}" readonly="readonly"/></span>
						</td>
						
					</tr>
								
				</tbody>
			</table>
		</div>
		
	 	<div style="clear:both; height:10px;"><span></span></div>
	 	<div class="stit"><s:message code="IDEX.INFO" /></div> <!-- 인덱스 정보 -->
	 	<div style="clear:both; height:5px;"><span></span></div>
	    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
	       <div class="tb_basic">
	           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
	               <caption>
	               <s:message code="TBL.NM1" /> <!-- 테이블 이름 -->
	               </caption>
	               <colgroup>
	               <col style="width:15%;" />
	               <col style="width:35%;" />
	               <col style="width:15%;" />
	               <col style="width:35%;" />
	               </colgroup>
	               <tbody>
	               		<tr>
	                       <th scope="row" class="th_require"><label for="ddlIdxPnm"><s:message code="IDEX.NM.PHYC.NM" /></label></th> <!--인덱스명(물리명)  -->
	                                    
	                       <td>
	                        <input type="text" id="ddlIdxPnm" name="ddlIdxPnm" class="wd200" value="${result.ddlIdxPnm }" title="인덱스명은 반드시 입력해야 합니다."/>
	                       </td>
	                       
	                       <th scope="row"><label for="ukIdxYn"><s:message code="UNIQUE.YN" /></label></th> <!--UNIQUE여부  -->
	                       <td>
	                      	  <select id="ukIdxYn" class="" name="ukIdxYn">
								<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
								<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
					 		  </select>
	                       </td>                                                                     
	                   </tr>
	                   
	                   <tr>
	                       <th scope="row" class="th_require"><label for="ddlIdxColAsm"><s:message code="IDEX.CLMN" /></label></th>  <!--인덱스컬럼  -->
	                       <td colspan="3">
	                       		<span class="input_inactive"><input type="text" id="ddlIdxColAsm" name="ddlIdxColAsm" style="width:90%;" value="${result.ddlIdxColAsm }" readonly /></span>
	                       		<button class="btnSearchPop" id="ddlIdxColPop" name="ddlIdxColPop"><s:message code="SRCH" /></button> <!-- 검색 -->
	                       </td>
	                       
	                   </tr>     
	                   
	                   <tr>
	                       <th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th>  <!--설명  -->
	                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn }</textarea></td>
	                   </tr>   
	                  <!--  <tr>
	                   		<th scope="row"><label for="atchFile">첨부파일</label></th>
	                   		<td><input type="file" name="atchFile" id="atchFile"></td>
	                   </tr> -->
	                              
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
