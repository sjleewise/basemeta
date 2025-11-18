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
<title>DDL파티션상세정보</title>

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
			ddlTblPnm	: "required",
			dbConnTrgPnm 	: "required", 
			dbSchPnm 	: "required", 
			partTypCd 	: "required", 
			partKey 	: "required", 
			aplReqdt : "required", 
			rqstResn	: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			ddlTblPnm	: requiremessage,
			dbConnTrgPnm 	: requiremessage, 
			dbSchPnm 	: requiremessage, 
			partTypCd 	: requiremessage, 
			partKey 	: requiremessage, 
			aplReqdt 	: requiremessage,
			rqstResn	: requiremessage
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
	
	
	// 파티션키 선택 이벤트 처리
    $("#frmInput #partKeyPop, #frmInput #subPartKeyPop").click(function(){ 
//  	doActionCol("New");
// 		doAction("NewCol");

    	//alert($("#frmColInput #ibsStatus").val());
//  	if($("#frmInput #ddlTblPnm").val() == ""){
	
		if(isBlankStr($("#frmInput #ddlTblPnm").val())){        		
       		showMsgBox("ERR", "선택된 테이블이 없습니다.<br>파티션테이블을 선택후 컬럼을 검색하세요.");
       		return;
    	}
    	var param = $("form#frmInput").serialize();
    		param += "&popRqst=Y";
    		
    	if ($(this).attr("id") == "partKeyPop") {
    		param += "&vrfRmk=DDLPART";
    	} else {
    		param += "&vrfRmk=DDLSUBPART";
    	}
    	
    	openLayerPop("<c:url value='/meta/ddl/popup/ddlcol_pop.do' />", 900, 700, param);
        });
	
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
		
		//서브파티션유형이 RANGE는 작성할 수 없음 		
	    if($("#frmInput #subPartTypCd").val() == "RANGE"){ 
	    	showMsgBox("ERR", "서브파티션은 RANGE파티션을 작성할수 없습니다.");
	    	return;
	    }

		/*
	  	//요청사유 길이 체크
		if (chechkByteSize($("#frmInput #rqstResn").val()) < 20) {
			showMsgBox("INF", "요청사유는 한글기준 최소 10자 이상 입력하세요.");
			return;
		}
		*/
	    	
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
	
	
	//DBMS 스키마 검색 팝업 호출
	$("#dbmsSchemaPop").click(function(){
    	var param = "dbConnTrgPnm=" + $("form#frmInput #dbConnTrgPnm").val();
    		param += "&dbSchPnm=" + $("form#frmInput #dbSchPnm").val();
    		param += "&tblSpacTypCd=T";
    		param += "&ddlTrgDcd=D";
    	openLayerPop("<c:url value='/commons/damgmt/db/popup/dbschema_pop.do' />", 900, 600, param);
    });
	
	//테이블명 검색팝업 이벤트
    $("#tblPop").click(function(){

    	var param = "";
    	param += "rqstNo=" + $("#mstFrm #rqstNo").val();
		openLayerPop ("<c:url value='/meta/ddl/popup/ddltblforpart_pop.do' />", 900, 600, param);
    });
	
    
// 	$("#frmInput #stwdLnm").focus();
    	$("#divInputBtn").show();
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
	
	$("#frmInput #partTypCd").val('${result.partTypCd}');
	$("#frmInput #subPartTypCd").val('${result.subPartTypCd}');

	$("#main_partTypCd").val('${result.partTypCd}');
	$("#sub_subPartTypCd").val('${result.subPartTypCd}');
	$("#aplReqTypCd").val('${result.aplReqTypCd}');
	
	$("#frmInput #cudYn").attr('disabled', false);
	$("#frmInput #cudYn").val('${result.cudYn}');
	$("#frmInput #cudYn").attr('disabled', true);
	
	
	//check box 값 초기화...
	
	//주파티션타입 변경시 주파티션 리스트에 있는 내용도 같이 변경...
	$("#frmInput #partTypCd").change(function(){
		$("#main_partTypCd").val($(this).val());
		if (isBlankStr($(this).val())) {
			$("#frmInput #partKey").val('');
			$("#main_partKey").val('');
		}
	});
	$("#frmInput #subPartTypCd").change(function(){
		$("#sub_subPartTypCd").val($(this).val());	
		if (isBlankStr($(this).val())) {
			$("#frmInput #subPartKey").val('');
			$("#sub_subPartKey").val('');
		}
	});
	
	$("#main_partKey").val("${result.partKey }");
	$("#sub_subPartKey").val("${result.subPartKey }");
	//달력팝업 추가...
 	$( "#aplReqdt" ).datepicker();
	
	//프로젝트 검색 팝업 호출
	$("#prjSearchPop").click(function(){
		var param = ""; //$("form#frmInput").serialize();
		openLayerPop ("<c:url value='/meta/subjarea/popup/projSearchPop.do' />", 800, 600, param);
    }); 
	//sr번호/프로젝트번호 초기화 
	$("#prjbtnDelPop").click(function(){
		$("#frmInput #prjMngNo").val("");
		$("#frmInput #srMngNo").val("");
    });
	
	//요청서 상태에 따른 버튼 show //sr번호/프로젝트번호만 
	setDispRqstSrMngNoButton($("#mstFrm #rqstStepCd").val()); 
	//DDL이관
	if($("#mstFrm #bizDcd").val() == "DTT"){
// 		$("#btnGridSave").hide();
// 		$("#btnReset").hide();
// 		$("#aplReqDtAllApply").show();
	}else{
// 		$("#aplReqDtAllApply").hide();
	}
	
	//스위치테이블 일경우 요청자 알림 기능
	//우동원차장(2018-03-20) 동일한 화면에서 클릭할 때마다 계속 띄워지는 것은 아닌것 같은데요...
	if(!switchTblMsgBox){
		if("${result.ddlTblPnm }".indexOf('_W01') > 0) {
			showMsgBox("INF", "테이블 : "+ "${result.ddlTblPnm }" +" 은(는)<br> 스위치 테이블입니다.");
			switchTblMsgBox = true;
		}
	}

});

//프로젝트 팝업 리턴값 처리
function returnPrjPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	$("#frmInput #prjMngNo").val(retjson.prjMngNo);
	$("#frmInput #srMngNo").val(retjson.srMngNo);
	
}

//프로젝트 팝업 리턴값 처리
function returnPrjAllPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	$("#frmInput #prjMngNo").val(retjson.prjMngNo);
	$("#frmInput #srMngNo").val(retjson.srMngNo);
	vPrjMngNo = retjson.prjMngNo;
	vSrMngNo = retjson.srMngNo;
	doAction("updateSrMngNo");
}	

//DBMS 정보 팝업 리턴값 처리
function returnDbSchemaPop(ret) {
 	//alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbSchPnm").val(retjson.dbSchPnm);
	$("#frmInput #dbSchId").val(retjson.dbSchId);
}
//DBMS 정보 팝업 리턴값 처리
function returnDdlTblPop(ret) {
// 	alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #ddlTblPnm").val(retjson.ddlTblPnm);
	$("#frmInput #ddlTblId").val(retjson.ddlTblId);
	
	$("#frmInput #dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#frmInput #dbSchPnm").val(retjson.dbSchPnm);
	$("#frmInput #dbConnTrgId").val(retjson.dbConnTrgId);
	$("#frmInput #dbSchId").val(retjson.dbSchId);
	

	$("#frmInput #tblSpacId").val(retjson.tblSpacId);
	$("#frmInput #tblSpacPnm").val(retjson.tblSpacPnm);
	
	if(retjson.prjMngNo != null){
		$("#frmInput #prjMngNo").val(retjson.prjMngNo);
		$("#frmInput #srMngNo").val(retjson.srMngNo);
	}
}
//파티션 키 리턴값 처리
function returnPartKeyPop (retval, type) {
	if ("DDLPART" == type) {
		$("#frmInput #partKey").val(retval);
		$("#main_partKey").val(retval);
	} else {
		$("#frmInput #subPartKey").val(retval);
		$("#sub_subPartKey").val(retval);
	}
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
    
    <!-- 
	<div class="stit">프로젝트 정보</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>프로젝트정보</legend>
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>프로젝트정보</caption>
			<colgroup>
			<col style="width:15%;" />
			<col style="width:85%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" id="tdsrMngNo">SR번호/서브프로젝트번호</th>
					<td colspan="3"> 
						<span class="input_inactive"><input type="text" class="wd240" id="srMngNo" name="srMngNo"  value="${result.srMngNo }" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd240" id="prjMngNo" name="prjMngNo"  value="${result.prjMngNo }" readonly="readonly"/></span>
						 	<button class="btnDelPop" id="prjbtnDelPop">삭제</button>
						 	<button class="btnSearchPop" id="prjSearchPop">검색</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>	 	
	 -->

	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">DDL테이블 정보</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<legend>주제영역정보</legend>
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>DDL테이블 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
			</colgroup>
			<tbody>
			
				<tr>
					<th scope="row" class="th_require">테이블명</th>
					<td >
						<span class="input_inactive"><input type="hidden" class="wd100" id="ddlTblId" name="ddlTblId"  value="${result.ddlTblId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="ddlTblPnm" name="ddlTblPnm" class="wd200" value="${result.ddlTblPnm}" readonly="readonly"/></span>
						<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="tblPop">검색</button>
					</td>	
					<th scope="row" class="th_require">DBMS 정보</th>
					<td>
						<span class="input_inactive"><input type="hidden" class="wd100" id="dbConnTrgId" name="dbConnTrgId"  value="${result.dbConnTrgId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="hidden" class="wd100" id="dbSchId" name="dbSchId"  value="${result.dbSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd200" value="${result.dbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbSchPnm" name="dbSchPnm" class="wd200" value="${result.dbSchPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="hidden" class="wd100" id="tblSpacId" name="tblSpacId" class="wd200" value="${result.tblSpacId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="hidden" class="wd100" id="tblSpacPnm" name="tblSpacPnm" class="wd200" value="${result.tblSpacPnm}" readonly="readonly"/></span>
						<button class="btnDelPop" >삭제</button>
						<button class="btnSearchPop" id="dbmsSchemaPop">검색</button>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit">파티션 정보</div>
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend>머리말</legend>
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
               <caption>
               테이블 이름
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   <tr>
                      <th scope="row" class="th_require"><label for="partTypCd">파티션 유형</label></th>
                       <td>
                       	<select id="partTypCd" name="partTypCd" >
                       		<option value="">---선택---</option>
                        	<c:forEach var="code" items="${codeMap.partTypCd}" varStatus="status" >
                               <option value="${code.codeCd}">${code.codeLnm}</option>
                            </c:forEach>
                        </select>
                       </td>
                       <th scope="row" class="th_require"><label for="partKey">파티션키</label></th>
                   		<td >
                     	<input type="text" id="partKey" name="partKey" class="wd200" readonly="readonly" value="${result.partKey }" />
                     	<button class="btnSearchPop" id="partKeyPop">검색</button>
                     	</td>
                   </tr>
                   <tr>
                      <th scope="row" ><label for="subPartTypCd">서브파티션 유형</label></th>
                       <td>
                       	<select id="subPartTypCd" name="subPartTypCd" >
                       		<option value="">---선택---</option>
                        	<c:forEach var="code" items="${codeMap.partTypCd}" varStatus="status" >
                               <option value="${code.codeCd}">${code.codeLnm}</option>
                            </c:forEach>
                        </select>
                       </td>
                       <th scope="row" ><label for="subPartKey">서브파티션키</label></th>
                   		<td >
                     	<input type="text" id="subPartKey" name="subPartKey" class="wd200" readonly="readonly" value="${result.subPartKey }" />
                     	<button class="btnSearchPop" id="subPartKeyPop">검색</button>
                     	</td>
                   </tr>
                  
                   <tr>
                       <th scope="row"><label for="objDescn">설명</label></th>
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn }</textarea></td>
                   </tr>
                    <!-- 
					<tr>
                       <th scope="row" id="tdaplReqTypCd"><label for="aplReqTypCd">적용요청구분</label></th>
                       <td>
                      	  <select id="aplReqTypCd" class="" name="aplReqTypCd">
							<option value="">선택</option>
							<c:forEach var="code" items="${codeMap.aplReqTypCd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
				 		  </select>
                       </td>
                   	   <th scope="row" id="tdaplReqdt" class="th_require"><label for="aplReqdt">적용요청일자</label></th>
                       <td>
                       		<span class="input_inactive"><input type="text" id="aplReqdt" name="aplReqdt" class="dtlInput" value="${result.aplReqdt }" readonly /></span>
                       </td>
                   </tr>  
                    -->    
                                
                   <tr style="display:none;">
                       <th scope="row"><label for="scrtInfo">스크립트정보</label></th>
                       <td colspan="3"><textarea id="scrtInfo" name="scrtInfo" class="wd98p" rows="10" disabled="disabled">${result.scrtInfo }</textarea></td>
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
