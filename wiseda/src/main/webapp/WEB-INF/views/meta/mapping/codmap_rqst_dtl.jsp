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
<title><s:message code="CD.MAPG.DFNT.P.REG.DTL.INFO"/></title> <!-- 코드매핑정의서등록상세정보 -->

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
			tgtDmnLnm 	: "required",
			tgtCdVal 	: "required",
			mapSno 		: "required"
		},
		messages: {
			rqstDcd		: requiremessage,
			tgtDmnLnm 	: requiremessage,
			tgtCdVal	: requiremessage,
			mapSno 		: requiremessage
			
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
	
	$("#divInputBtn").show();
	
	//타겟코드도메인 검색 팝업 호출
	$("#tgtDmnLnmSearchPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/meta/mapping/popup/tgtCdDmnSearchPop.do'/>", 800, 600, param);
    });
    

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
	
	$("#frmInput #mapDfType").val('${result.mapDfType}');
	$("#frmInput #cdCnvsType").val('${result.cdCnvsType}');
	$("#frmInput #cdMapType").val('${result.cdMapType}');
	

	//응용담당자 검색 팝업 호출
	$("#appUserPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	$("#frmInput #userSchTyp").val('app');
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
	
	//전환담당자 검색 팝업 호출
	$("#cnvsUserPop").click(function(){
    	var param = ""; //$("form#frmInput").serialize();
    	$("#frmInput #userSchTyp").val('cnvs');
    	openLayerPop("<c:url value='/commons/user/popup/userSearchPop.do' />", 800, 600, param);
    });
	
	
});


//담당자 팝업 리턴값 처리...
function returnUserInfoPop(ret) {
 	//alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	if($("#frmInput #userSchTyp").val() == 'app') //응용담당자
	{
		$("#frmInput #appCrgpId").val(retjson.userId);
		$("#frmInput #appCrgpNm").val(retjson.userNm);
		$("#frmInput #userSchTyp").val('');
		
	}else //전환담당자
	{
		$("#frmInput #cnvsCrgpId").val(retjson.userId);
		$("#frmInput #cnvsCrgpNm").val(retjson.userNm);
		$("#frmInput #userSchTyp").val('');		
	}
}


//타겟코드도메인팝업 리턴값 처리...
function returnTgtCdDmnInfoPop(ret) {
 	//alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #tgtDmnId").val(retjson.tgtDmnId);
	$("#frmInput #tgtDmnLnm").val(retjson.tgtDmnLnm);
	$("#frmInput #tgtCdVal").val(retjson.tgtCdVal);
	$("#frmInput #tgtCdValNm").val(retjson.tgtCdValNm);
	

}



</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" >
	  	<input type="hidden" id="userSchTyp" name="userSchTyp" value="" >
	  	<input type="hidden" id="tgtDmnId" name="tgtDmnId" value="${result.tgtDmnId}" >
		
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->


	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="MAPG.DFNT.P.INFO"/></div> <!-- 매핑정의서 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:18%;" />
               <col style="width:15%;" />
               <col style="width:18%;" />
               <col style="width:15%;" />
               <col style="width:18%;" />
       

               </colgroup>
               <tbody>
				 <tr>
				 	   <th scope="row"><label for="mapDfType"><s:message code="MAPG.DFNT.P.PTRN" /></label></th> <!-- 매핑정의서유형 -->
                       <td>
                       	<select id="mapDfType"  name="mapDfType" >
							 <c:forEach var="code" items="${codeMap.mapDfTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                       
                       <th scope="row"><label for="cdCnvsType"><s:message code="CD.CNVR.PTRN" />
</label></th> <!-- 코드전환유형 -->
                       <td>
                       	<select id="cdCnvsType"  name="cdCnvsType" >
							 <c:forEach var="code" items="${codeMap.cdCnvsTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                       
                       <th scope="row"><label for="cdMapType"><s:message code="CD.MAPG.PTRN" />
</label></th> <!-- 코드매핑유형 -->
                       <td>
                       	<select id="cdMapType"  name="cdMapType" >
							 <c:forEach var="code" items="${codeMap.codMapTypCd}" varStatus="status">
						    	<option value="${code.codeCd}">${code.codeLnm}</option>
						    </c:forEach>   
						</select>							
                       </td>
                   </tr>
                
                </tbody>
	           </table>
	       </div> 
             
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit"><s:message code="TARG.ASIS.INFO"/></div> <!-- 타겟(ASIS) 정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                  
                   <tr>
                   		<th scope="row" class="th_require"><label for="tgtDmnLnm"><s:message code="TARG.CD.DMN.NM.1"/></label></th> <!-- 타겟(TOBE)코드도메인명 -->
                       	<td colspan="3">
                       		<span class="input_inactive"><input type="text" id="tgtDmnLnm" name="tgtDmnLnm" value="${result.tgtDmnLnm}"  /></span>
                       		<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->                     		
							<button class="btnSearchPop" id="tgtDmnLnmSearchPop" name="tgtDmnLnmSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
                       	</td>
                   </tr>
                   <tr>
                       	<th scope="row" class="th_require"><label for="tgtCdVal"><s:message code="TARG.CD.VAL"/></label></th> <!-- 타겟(TOBE) 코드값 -->
                       	<td><input type="text" id="tgtCdVal" name="tgtCdVal" value="${result.tgtCdVal}" /></td>
                       	
                       	<th scope="row"><label for="tgtCdValNm"><s:message code="TARG.CD.NM"/></label></th> <!-- 타겟(TOBE) 코드명 -->
                       	<td><input type="text" id="tgtCdValNm" name="tgtCdValNm" value="${result.tgtCdValNm}"  /></td>
                   </tr>
			  </tbody>
	           </table>
	       </div> 
             
		<div style="clear:both; height:10px;"><span></span></div>
		<div class="stit"><s:message code="SOUR.INFO"/></div> <!-- 소스(ASIS) 정보 -->
		<div style="clear:both; height:5px;"><span></span></div>
		<legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>      
                  <tr>
                       	<th scope="row"><label for="srcSysNm"><s:message code="SOUR.SYS.NM"/></label></th> <!-- 소스(ASIS)시스템명 -->
                       	<td ><input type="text" id="srcSysNm" name="srcSysNm" value="${result.srcSysNm}"  /></td>
                       	<th scope="row"><label for="srcBizNm"><s:message code="SOUR.BZWR.NM"/></label></th> <!-- 소스(ASIS)업무명 -->
                       	<td ><input type="text" id="srcBizNm" name="srcBizNm" value="${result.srcBizNm}"  /></td>
                  </tr>
                  <tr>
                   		<th scope="row"><label for="srcDbPnm"><s:message code="SOUR.ASIS.DB.NM.1"/></label></th> <!-- 소스(ASIS)DB명 -->
                       	<td colspan="3"><input type="text" id="srcDbPnm" name="srcDbPnm" value="${result.srcDbPnm}"  /></td>
                   </tr>
                   
                   <tr>
                       	<th scope="row"><label for="srcTblPnm"><s:message code="SOUR.TBL.PHYC"/></label></th> <!-- 소스(ASIS)테이블물리명 -->
                       	<td ><input type="text" id="srcTblPnm" name="srcTblPnm" value="${result.srcTblPnm}" /></td>
                       	<th scope="row"><label for="srcTblLnm"><s:message code="SOUR.TBL.LGC.NM"/></label></th> <!-- 소스(ASIS)테이블논리명 -->
                       	<td colspan="3"><input type="text" id="srcTblLnm" name="srcTblLnm" value="${result.srcTblLnm}" /></td>
                   </tr>
                   
                   <tr>
                       	<th scope="row"><label for="srcColPnm"><s:message code="SOUR.CLMN.PHYC.NM"/></label></th> <!-- 소스(ASIS)컬럼물리명 -->
                       	<td ><input type="text" id="srcColPnm" name="srcColPnm" value="${result.srcColPnm}"  /></td>
                       	<th scope="row"><label for="srcColLnm"><s:message code="SOUR.CLMN.LGC.NM"/></label></th> <!-- 소스(ASIS)컬럼논리명 -->
                       	<td colspan="3"><input type="text" id="srcColLnm" name="srcColLnm" value="${result.srcColLnm}"  /></td>
                   </tr>
                   
                   <tr>
                   		<th scope="row"><label for="srcCdVal"><s:message code="SOUR.CD"/></label></th> <!-- 소스(ASIS) 코드 -->
                       	<td ><input type="text" id="srcCdVal" name="srcCdVal" value="${result.srcCdVal}"  /></td>
                       	<th scope="row"><label for="srcCdValNm"><s:message code="SOUR.CD.NM"/></label></th> <!-- 소스(ASIS) 코드명 -->
                       	<td colspan="3"><input type="text" id="srcCdValNm" name="srcCdValNm" value="${result.srcCdValNm}"  /></td>
                   </tr>
                   <tr>
                   		<th scope="row"><label for="srcUppCdVal"><s:message code="SOUR.UPRN.CD"/></label></th> <!-- 소스(ASIS) 상위코드 -->
                       	<td ><input type="text" id="srcUppCdVal" name="srcUppCdVal" value="${result.srcUppCdVal}"  /></td>
                       	<th scope="row"><label for="srcUppCdValNm"><s:message code="SOUR.UPRN.CD.NM"/></label></th> <!-- 소스(ASIS) 상위코드명 -->
                       	<td colspan="3"><input type="text" id="srcUppCdValNm" name="srcUppCdValNm" value="${result.srcUppCdValNm}"  /></td>
                   </tr>
               </tbody>
           </table>
       </div>
       
       
		<div style="clear:both; height:10px;"><span></span></div>
	 	<div class="stit"><s:message code="ADI.INFO"/></div> <!-- 부가 정보 -->
	 	<div style="clear:both; height:5px;"><span></span></div>
	    <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
		<div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
               <caption>
               <s:message code="MAPG.DFNT.P.NM"/> <!-- 매핑정의서 이름 -->
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:10%;" />
               <col style="width:13%;" />
               <col style="width:10%;" />
               <col style="width:17%;" />
               <col style="width:10%;" />
               <col style="width:12%;" />
               <col style="width:10%;" />
               </colgroup>
               <tbody>      
                   <tr>
                   <th scope="row"><label for="appCrgpNm"><s:message code="APP.CHGR"/></label></th> <!-- 응용담당자 -->
						<td colspan="3">
							<span class="input_inactive"><input type="text" id="appCrgpId" name="appCrgpId" value="${result.appCrgpId}" readOnly /></span>
							<span class="input_inactive"><input type="text" id="appCrgpNm" name="appCrgpNm" value="${result.appCrgpNm}" readOnly /></span>
						</td>
						<th scope="row"><label for="cnvsCrgpNm"><s:message code="CNVR.CHGR"/></label></th> <!-- 전환담당자 -->
						<td colspan="3">
							<span class="input_inactive"><input type="text" id="cnvsCrgpId" name=cnvsCrgpId value="${result.cnvsCrgpId}" /></span>
							<span class="input_inactive"><input type="text" id="cnvsCrgpNm" name="cnvsCrgpNm" value="${result.cnvsCrgpNm}" /></span>
							<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
							<button class="btnSearchPop" id="cnvsUserPop" name="cnvsUserPop"><s:message code="SRCH" /></button> <!-- 검색 -->
						</td>
                   </tr>
               </tbody>
           </table>
       </div>
       
       </fieldset>
       </form>
	<!-- 입력폼 끝 -->
<!-- 	* 매핑정의서ID는 타겟(TOBE) 테이블명+'_'+매핑일련번호로 자동 치환됩니다.<br>* 테이블매핑유형이 NEW, INITIAL 경우 작성완료 버튼 클릭시 소스(ASIS) 정보는 공백으로  치환됩니다. -->
	<div class="tb_comment"><s:message  code='REQ.CMP.CODE' /></div>
	<div style="clear:both; height:10px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	<!-- 입력폼 버튼... -->
</div>
</body>
</html>
