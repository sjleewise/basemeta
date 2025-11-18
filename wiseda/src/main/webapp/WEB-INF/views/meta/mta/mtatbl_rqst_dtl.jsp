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
<title>테이블상세정보</title>
<script type="text/javascript">
var connTrgSchJson = ${codeMap.connTrgSch};

$(document).ready(function(){
	//$("form#frmInput #objDescn,#openDataLst").height("98px");
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	
 	$("#frmInput").validate({
		rules: {
			mtaTblPnm	: "required",
			mtaTblLnm	: "required",
			tblVol		: "required",
			tblCreDt	: "required",
			subjNm		: "required",
			nopenRsn	: "required",
			objDescn	: "required"
		},
		messages: {
			mtaTblPnm	: requiremessage,
			mtaTblLnm	: requiremessage,
			tblVol		: requiremessage,
			tblCreDt	: requiremessage,
			subjNm		: requiremessage,
			nopenRsn	: requiremessage,
			objDescn	: requiremessage
		}
	});
	
	
	//======================================================
	// 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
	//======================================================
	initSearchButton();
	$("#tblCreDt").datepicker();  
	//alert("조회완료");
// 	if ("U" == $("#saction").val()) {
// 		//alert("업데이트일경우");
// 		$("input[name=progrmFileNm]").attr('readonly', true);
// 	}
	
	//폼 저장 버튼 초기화...
	$('#btnGridSave').click(function(event) {
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		
		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
		if ($("#mstFrm #rqstStepCd").val() == "Q") {

			alert(("#mstFrm #rqstStepCd").val());
			
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
	$('#btnReset').click(function(event) {
		event.preventDefault();  //브라우저 기본 이벤트 제거...

		resetForm($("form#frmInput").find("readonly,disabled"));
		
	});


	/* $("#nopenRsnSearchPop").click(function(){
		var param = ""; //$("form#frmInput").serialize();
		openLayerPop ("<c:url value='/meta/mta/popup/nopenRsnSearchPop.do' />", 800, 600, param);
    }); */

	//BRM조회 
	$("#btnBrmCls").click(function(){ 
		
 		var param = "";
	
		param += "option=recommend"; 
		param += "&type=6";
		param += "&se=C";
		
		openLayerPop ("<c:url value='/meta/mta/popup/brmSearchpop.do' />", 800, 628, param);
	});


    $("#tblVol").keydown(function(event){

    	//숫자입력 체크 
        if(!numberCheck(event)) return false;         
    });

    $("#frmInput #mtaTblLnm").keyup(function(event){
		
    	//숫자입력 체크 
    	//if(!korengCheck(event)) return false;

    	var intext = $(this).val(); 
    	
    	intext = korengCheck2(intext);       

    	$(this).val(intext); 
        
    });
    

	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : (N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);

 	$('select', $("#frmInput #dbConnTrgId").parent()).change(function(e){
   		double_select(connTrgSchJson, $(this));
   	});
	double_select(connTrgSchJson, $("#frmInput #dbConnTrgId"));

	
	$("#frmInput #dbConnTrgId").val('${result.dbConnTrgId}').change().find("option").attr("disabled", "disabled");
	$("#frmInput #dbSchId").val('${result.dbSchId}').find("option").attr("disabled", "disabled");

	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmInput #rqstDcd").val('${result.rqstDcd}');
	$("#frmInput #regTypCd").val('${result.regTypCd}');
	$("#frmInput #vrfCd").val('${result.vrfCd}');
	
	$("#frmInput #rvwStsCd").val('${result.rvwStsCd}');
	$("#frmInput #rvwConts").val('${result.rvwConts}');

	$("#frmInput #orgCd").val('${result.orgCd}'); 
	$("#frmInput #infoSysCd").val('${result.infoSysCd}');   
	$("#frmInput #dqDgnsYn").val('${result.dqDgnsYn}');
	$("#frmInput #tblTypNm").val('${result.tblTypNm}');
	$("#frmInput #nopenRsn").val('${result.nopenRsn}');
	$("#frmInput #prsvTerm").val('${result.prsvTerm}');
	$("#frmInput #occrCyl").val('${result.occrCyl}');
	//======================================================
	
	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();

	$("#btnSearch").hide();

	$("#btnCloseBottom").hide();
	
	SboxSetLabelEvent();
	
});

//화면 로드시 공통 처리부분 여기에 추가한다.
$(window).on('load',function() {
	
	//요청자 아닌경우 버튼 disable
	disableBtnRqstUser("${waqMstr.rqstUserId}", "${sessionScope.loginVO.uniqId}"); 	
	
});

//비공개사유 팝업 리턴값 처리
/* function returnNopenRstPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmInput #nopenRsn").val(retjson.codeCd);
	$("#frmInput #nopenRsnCdNm").val(retjson.codeLnm);
	
} */

function returnBrmInfoPop(brmFullPath, brmId, upperBrmId){	

	//alert(JSON.stringify(data));
	
	$("#frmInput #subjNm").val(brmFullPath);     //주제영역전체경로  
 	$("#frmInput #subjId").val(brmId);           //주제영역id
 	$("#frmInput #uppSubjId").val(upperBrmId);   //주제영역id
	
	/*
	var sData = jQuery.parseJSON(data);    
	
	$("#frmInput #subjNm").val(sData.subj_path); //주제영역전체경로 
 	$("#frmInput #subjId").val(sData.subj_id);   //주제영역id
 	$("#frmInput #uppSubjId").val(sData.upp_subj_id);   //주제영역id
 	*/  
 	
}

function numberCheck(event){

	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	if( (keyID >= 48 && keyID <= 57) ||
		(keyID >= 96 && keyID <= 105) || keyID == 8		
	    ){

	    return true;
	}else{
		return false;
	}	
}


function korengCheck2(intext){

	//var deny = /[`~!@#$%^&*()_+-={}[]|\\\'\":;/\?]/gi ;
	
	var deny = /[\{\}\[\]\/?.,;:|\)*~`!^\-+<>@\#$%&\\\=\(\'\"]/gi;
	
	if(deny.test(intext)) {

		intext = intext.replace(deny,"");
		
	    return intext; 
	}else{
		return intext;
	}		
}


function korengCheck(event){

	event = event || window.event;

	var keyID = (event.which) ? event.which : event.keyCode;

	//alert(keyID);

	if(keyID == 8) { 
		return true;

	}else if( keyID == 16){ 

		return false;
		
	}else if( keyID >= 48 && keyID <= 57){

		return true;
	}else if( keyID == 189 || keyID == 187){

		return false;
	}else if( (keyID < 65) || (keyID > 122 && keyID <= 127) ){

	    return false;
	}else{
		return true;
	}		
}

</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmInput" id="frmInput" method="post" >
     	<input type="hidden" id="rqstSno"   name="rqstSno"   value="${result.rqstSno}" />
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" /> 	
	  	
	  	<input type="hidden" id="rvwStsCd"  name="rvwStsCd" />  	  
	  	<input type="hidden" id="rvwConts"  name="rvwConts"  /> 	  	
	  	<input type="hidden" id="rqstDcd"   name="rqstDcd"  value="${result.rqstDcd}"  />	  
	  	<input type="hidden" id="regTypCd"  name="regTypCd" value="${result.regTypCd}" />	  
	  	<input type="hidden" id="vrfCd"     name="vrfCd"    value="${result.vrfCd}" />	
	  	<input type="hidden" id="vrfRmk"    name="vrfRmk"  />
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<%-- <tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />  --%>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">시스템정보</div>
	<div style="clear:both; height:5px;"><span></span></div>
 	<legend>시스템정보</legend> 
	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!--  서머리입니다. -->
			<caption>시스템정보</caption> <!-- 시스템정보 -->
			<colgroup>
			<col style="width:15%;" />
            <col style="width:35%;" />
            <col style="width:15%;" />
            <col style="width:35%;" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row" ><s:message code="ORG.NM"/></th>
					<td>
						<div class="sbox wd300">
						<label class="sbox_label" for="orgCd"></label>
						<select id="orgCd" class="wd300" name="orgCd" disabled="disabled">
			          	    <option value="" ></option>
                            <c:forEach var="code" items="${codeMap.orgCd}" varStatus="status" >
                            	<option value="${code.codeCd}" >${code.codeLnm}</option>
                            </c:forEach>
                       	</select>
                       	</div>
                       	<!-- <input type="hidden" id="orgNm" name="orgNm" /> -->
					</td>
					<th scope="row"><s:message code="INFO.SYS.NM"/></th> 
					<td>
						<div class="sbox wd300">
						<label class="sbox_label" for="infoSysCd"></label>
						<select id="infoSysCd" class="wd300" name="infoSysCd" disabled="disabled">
			          	    <option value="" ></option>
                            <c:forEach var="code" items="${codeMap.infoSysCd}" varStatus="status" >
                            	<option value="${code.codeCd}" >${code.codeLnm}</option>
                            </c:forEach>
                        </select>
                        </div>
                        <!-- <input type="hidden" id="infoSysNm" name="infoSysNm" /> -->
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
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
                       <th scope="row"><label for="dbConnTrgId">DB명/테이블소유자</label></th>  
                       <td colspan="3">
                       		<div class="sbox wd300">
							<label class="sbox_label" for="dbConnTrgId"></label> 
	                       	<select id="dbConnTrgId" class="" name="dbConnTrgId" disabled> 
								<option value="" ><s:message code="CHC" /></option>
							</select> 
							</div>
							<div class="sbox wd200">
							<label class="sbox_label" for="dbSchId"></label> 
							<select id="dbSchId" class="" name="dbSchId" disabled>
								<option value=""><s:message code="CHC" /></option>
							</select>
							</div>
							<input type="hidden" id="dbConnTrgPnm" name="dbConnTrgPnm" value="${result.dbConnTrgPnm }"  />
						</td>
                   </tr>
                  
                   <tr>
                       <!-- <th scope="row" class="th_require"><label for="mtaTblPnm">테이블명</label></th>  --><!-- 테이블명(물리명) -->
                       <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="mtaTblPnm">테이블명</label><!-- css 사용 일때  g_ico1 . g_ico2. g_ico3. --> <!-- 이미지 사용 일때 <span class="ico_ga_img"><img class="" src="/wiseda/images/mj_mata_gr_01.png" alt="" title=""> <img class="" src="/wiseda/images/mj_mata_gr_02.png" alt="" title=""> <img class="" src="/wiseda/images/mj_mata_gr_03.png" alt="" title=""></span> --></th> <!-- 테이블명(물리명) -->
                       <td colspan="3"><input type="text" id="mtaTblPnm" name="mtaTblPnm" style="width:80%;" value="${result.mtaTblPnm }" title="<s:message code='MSG.TBL.NM.INPT' />" readonly/></td> <!-- 테이블명은 반드시 입력해야 합니다. -->
                                           
                   </tr>
                   <tr>  
                       <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="mtaTblLnm">테이블한글명</label></th><!-- 테이블명(논리명) -->
                       <td colspan="3"><input type="text" id="mtaTblLnm" name="mtaTblLnm" style="width:80%;" value="${result.mtaTblLnm }"  /></td>
                   </tr>
                   <tr>
                   	  <th scope="row"><label for="tblTypNm">테이블유형</label></th> 
                      <td colspan="3">
                      	<div class="sbox wd100">
							<label class="sbox_label" for="tblTypNm"></label>                     
	                      	<select id="tblTypNm" name="tblTypNm" class="wd100" >
	                            <option value="" ><s:message code="CHC" /></option>                
	                            <c:forEach var="code" items="${codeMap.tblTypCd}" varStatus="status" >
	                            	<option value="${code.codeCd}" >${code.codeLnm}</option>
	                            </c:forEach>
	                      	</select>
	                    </div>
                      </td>
                  <%--  	  <th scope="row"><label for="relEntyNm">엔터티명</label></th> 
                      <td><input type="text" id="relEntyNm" name="relEntyNm" class="wd98p" value="${result.relEntyNm }" /></td> --%>
                   </tr>
                    <tr class="ht50">
                       <th scope="row" class="th_require"><label for="objDescn">테이블<s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" >${result.objDescn }</textarea></td>
                   </tr>
                   <tr>
                   	  <th scope="row" class="th_require"><label for="subjNm">업무분류체계</label></th>
                      <td colspan="3">
	                      <input type="text" id="subjNm" name="subjNm" readonly style="width:80%;" value="${result.subjNm }" /> 
	                      <button class="btnSearchPop" type="button" id="btnBrmCls"	name="btnBrmCls">BRM검색</button>
	                      <input type="hidden" id="subjId"     name="subjId"  />
	                      <input type="hidden" id="uppSubjId"  name="uppSubjId"  />  	                      
                      </td>
                   	  <%-- <th scope="row" class="th_require"><label for="tagInfNm">태깅정보</label></th>
                      <td><input type="text" id="tagInfNm" name="tagInfNm" class="wd98p" value="${result.tagInfNm }" placeholder="테이블의 데이터를 가장 잘 설명할 수 있는 핵심 키워드를 입력하세요"/></td> --%>
                   </tr>
                   
                   <tr>
                      <th scope="row" class="th_require"><label for="dqDgnsYn">품질진단여부</label></th>
                      <td> 
                      		<div class="sbox wd100">
							<label class="sbox_label" for="dqDgnsYn"></label> 
                      		<select id="dqDgnsYn" name="dqDgnsYn">
                      			<option value="N"><s:message code="MSG.NO" /></option>
                   				<option value="Y"><s:message code="MSG.YES" /></option>
                      		</select>
                      		</div>
                      		<!-- <input type="checkbox" id="dqDgnsYn" name="dqDgnsYn" value="Y"/>품질진단여부</span>  -->
					  </td>  
                      <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="tblCreDt">테이블생성일</label></th>
					  <td><input type="text" id="tblCreDt" name="tblCreDt" value="${result.tblCreDt}" readonly/></td>
                   </tr>
                   <tr>
                   	  <th scope="row"><label for="prsvTerm">보존기간</label></th> <!-- 보관주기 -->
                      <td>
                      	<div class="sbox wd100">
							<label class="sbox_label" for="prsvTerm"></label>           
                          <select id="prsvTerm" name="prsvTerm">	
                              <option value="" ><s:message code="CHC" /></option>   	            	  
	                    	  <c:forEach var="code" items="${codeMap.prsvTerm}" varStatus="status" >
	                           	<option value="${code.codeCd}" >${code.codeLnm}</option>
	                          </c:forEach>
                          </select>
                          </div>
                    	  <%-- <input type="text" id="prsvTerm" name="prsvTerm" class="wd98p" value="${result.prsvTerm }"/> --%>
                      </td> 
                      <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="tblVol">테이블볼륨(row수)</label></th>
                      <td>
                      	<input type="number" id="tblVol" name="tblVol" class="wd98p" value="${result.tblVol }" />
                      </td> 
                   </tr>
                   <tr class="ht50">
                       <th scope="row"><label for="openDataLst">개방데이터목록</label></th> <!-- 설명 -->
                       <td colspan="3"><textarea id="openDataLst" name="openDataLst" class="wd98p" >${result.openDataLst }</textarea></td>
                   </tr>
                   <tr>
					   <th scope="row"><label for="occrCyl">갱신주기</label></th>
                       <td>
                       		<div class="sbox wd100">
							<label class="sbox_label" for="occrCyl"></label>  
                       		<select id="occrCyl" name="occrCyl">	
	                              <option value="" ><s:message code="CHC" /></option>   	            	  
		                    	  <c:forEach var="code" items="${codeMap.occrCyl}" varStatus="status" >
		                           	<option value="${code.codeCd}" >${code.codeLnm}</option>
		                          </c:forEach>
                            </select>
                            </div>
                       		<%-- <input type="text" id="occrCyl" name="occrCyl" class="wd200" value="${result.occrCyl }" /> --%>
                       </td>
                   		
                   		<th scope="row" class="th_require"><label for="nopenRsn">비공개사유</label></th>
                       	<td>
                       		<div class="sbox wd400">
							<label class="sbox_label" for="nopenRsn"></label>
                       		<select id="nopenRsn" name="nopenRsn" class="wd400">
	                            <c:forEach var="code" items="${codeMap.nopenRsnCd}" varStatus="status" >
	                            	<option value="${code.codeCd}" >${code.codeLnm}</option>
	                            </c:forEach>
	                      	</select>
	                      	</div>
	               		</td>
                   </tr>
                   <%-- <tr class="ht50">
                       <th scope="row" class="th_require"><label for="nopenRsn">비공개사유</label></th>
                       	<td colspan="3">
						<input type="hidden" id="nopenRsn" name="nopenRsn" value="${result.nopenRsn}"/>
						<span class="input_inactive">
							<textarea id="nopenRsnCdNm" name="nopenRsnCdNm" readonly style="width:90%">${result.nopenRsnCdNm }</textarea> 							
						</span>
						<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						<button class="btnSearchPop" id="nopenRsnSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
					</td>
                   </tr> --%>
               </tbody>
           </table>
       </div>
       </fieldset>
       </form>
	<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<c:if test="${saction == 'U' }">
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" />
	</c:if>
	<!-- 입력폼 버튼... -->
</div>
</body>
</html>