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
	//$("form#frmPopTblInput #objDescn,#openDataLst").height("98px");
	//======================================================
	// 폼 검증 초기화...
	//======================================================
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.REQUIRED" />";
	//폼검증
	
	/*
 	$("#frmPopTblInput").validate({
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
	*/
	
	

	//BRM조회 
	$("#frmPopTblInput #btnBrmCls").click(function(){ 
		
			
 		var keyword = "";

// 		keyword += $("#frmPopTblInput #mtaTblLnm").val() + $("#frmPopTblInput #objDescn").val();
		
		keyword += $("#frmPopTblInput #mtaTblLnm").val();

		for(var i = 1; i <= col_sheet.RowCount(); i++) {
			
			keyword += col_sheet.GetCellValue(i, "mtaColLnm"); 
		}

		keyword = keyword.substring(0, 300);
		
		keyword = keyword.replace(/\r/g,"").replace(/\n/g,"");
 		
		var param = "";  		
		
		param += "option=recommend"; 
		param += "&type=6";
		param += "&se=C";
		param += "&keyword=" + encodeURIComponent(keyword);
		param += "&popType=W";  
		//alert(param);
		openLayerPop ("<c:url value='/meta/mta/popup/brmSearchpop.do' />", 800, 680, param); 
		
		//OpenWindow("<c:url value='/meta/mta/popup/brmSearchpop.do' />", 800, 500); 		
	});


    $("#tblVol").keydown(function(event){

    	//숫자입력 체크 
        if(!numberCheck(event)) return false;         
    });

    /* 
    $("#frmPopTblInput #mtaTblLnm").keyup(function(event){
		
    	//숫자입력 체크 
    	//if(!korengCheck(event)) return false;

    	var intext = $(this).val(); 
    	
    	intext = korengCheck2(intext);       

    	$(this).val(intext); 
        
    });
    */ 
    
    $("#frmPopTblInput #openRsnCd").change(function(event){
		
    	var openRsnCd = $(this).val();
    	
    	//코드값 변경시 세팅 
    	setChgOpenRnsCd(openRsnCd);
    });

	//요청서 단계에 따라 저장,초기화 버튼 숨기기...
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//===============요청상세탭화면 ==================
	//요청단계 : (N-작성전, S-임시저장, Q-등록요청, A-결재처리)
	setDispLstButton(rqststep);

	//======================================================
	// 셀렉트 박스 초기화 
	//======================================================
	/*
 	$('select', $("#frmPopTblInput #dbConnTrgId").parent()).change(function(e){
   		double_select(connTrgSchJson, $(this));
   	});

	double_select(connTrgSchJson, $("#frmPopTblInput #dbConnTrgId"));

 	$("#frmPopTblInput #dbConnTrgId").val('${result.dbConnTrgId}').change().find("option").attr("disabled", "disabled"); 
 	
 	$("#frmPopTblInput #dbSchId").val('${result.dbSchId}').find("option:selected").attr("disabled", "disabled");;
 	*/
 	$.each(connTrgSchJson, function(i, d) {
		if(d.codeCd == "${result.dbConnTrgId}") {
			$('#dbConnTrgId').val(d.codeLnm);
		}
		if(d.codeCd == "${result.dbSchId}") {
			$('#dbSchId').val(d.codeLnm);
		}
	});
 	//$("#frmPopTblInput #dbSchId").val('${result.dbSchId}').find("option:selected").attr("selected","selected").attr("disabled", "disabled");;      
 	
 	
	//select box 값 초기화... (요청구분, 등록타입, 검증결과)
	$("#frmPopTblInput #rqstDcd").val('${result.rqstDcd}');  
	$("#frmPopTblInput #regTypCd").val('${result.regTypCd}').find("option:selected").attr("disabled", "disabled");  
	$("#frmPopTblInput #vrfCd").val('${result.vrfCd}').find("option:selected").attr("disabled", "disabled");      
	$("#frmPopTblInput #regTblStatus").val('${result.regTblStatus}').find("option:selected").attr("disabled", "disabled");	
	
	$("#frmPopTblInput #rvwStsCd").val('${result.rvwStsCd}');
	$("#frmPopTblInput #rvwConts").val('${result.rvwConts}');

	$("#frmPopTblInput #orgCd").val('${result.orgCd}'); 
	$("#frmPopTblInput #infoSysCd").val('${result.infoSysCd}');   
	$("#frmPopTblInput #dqDgnsYn").val('${result.dqDgnsYn}');
	$("#frmPopTblInput #tblTypNm").val('${result.tblTypNm}');
	$("#frmPopTblInput #nopenRsn").val('${result.nopenRsn}');
	$("#frmPopTblInput #prsvTerm").val('${result.prsvTerm}');
	$("#frmPopTblInput #occrCyl").val('${result.occrCyl}');
	$("#frmPopTblInput #openRsnCd").val('${result.openRsnCd}').find("option:selected");
	
	$("#frmPopTblInput #openRsnCd").change();		
	
	//======================================================
	
	//폼내의 버튼을 보여준다.
	$("#divInputBtn").show();

	$("#btnSearch").hide(); 
	
	//SboxSetLabelEvent();  

	//추천용어 구현전
	$("#btnItem").click(function(){
		
	
		if(isBlankStr($("#frmPopTblInput #mtaTblLnm").val())) {
			showMsgBox("ERR", "테이블한글명을 입력해주세요.");
			return false;
		}
		
		var param = ""; 
		    		
		param += "&keyword=" + encodeURIComponent($("#frmPopTblInput #mtaTblLnm").val());      
		
		//param += "&keyword=" + $("#frmColInput #mtaColLnm").val(); 

		var vUrl = "<c:url value='/meta/mta/popup/rcmdTermSearchpop.do' />"; 
		
		//alert(vUrl); 

		//OpenWindow(vUrl, "reconItem", 800, 600, null); 
		
	    openLayerPop(vUrl, 800, 500, param);     		
	});

	//SboxSetLabelEvent();
	
});


function setChgOpenRnsCd(openRsnCd){
	
	//공개:01,비공개:02,부분공개:03
	switch(openRsnCd) {
	    		
		case "02":
		case "03":	
		
			$("#trNopenRsn").show();	
			
			//체크박스 세팅 
    		setNopenRsnChk();         	  
			break;
		default:

			$("#trNopenRsn").hide();
    		$("#divNopenRsn").html("");    		
    		$("#divTblEnd").css("height","20px");  	
    		        		
    		$(":checkbox[name='chkNopenRsn']").prop("checked",false);
    		
    		$("#nopenDtlRelBss").val("");
    		
			break; 	
	}
    	    	
	for(var i = 1; i <= col_sheet.RowCount() ; i++) { 
	
		if(openRsnCd == "01") {  
	    		
			col_sheet.SetCellValue(i,"openYn","Y"); 
			col_sheet.SetCellValue(i,"prsnInfoYn","N");
			col_sheet.SetCellValue(i,"encTrgYn","N");
			col_sheet.SetCellValue(i,"priRsn","");
			
		}else if(openRsnCd == "02") {
			
			col_sheet.SetCellValue(i,"openYn","N"); 
			col_sheet.SetCellValue(i,"prsnInfoYn","Y");
			col_sheet.SetCellValue(i,"encTrgYn","Y");
			col_sheet.SetCellValue(i,"priRsn","");
			
		}else if(openRsnCd == "03") {  	
			
			col_sheet.SetCellValue(i,"openYn","N"); 
			col_sheet.SetCellValue(i,"prsnInfoYn","");
			col_sheet.SetCellValue(i,"encTrgYn","");
			col_sheet.SetCellValue(i,"priRsn","");
		}
	}
	
	//컬럼값 변경후 상태도 
	updateNopenRsn();
}

function updateNopenRsn(){
		
	var param = "";  

	param += "?rqstNo="     + "${result.rqstNo}";
	param += "&rqstSno="    + "${result.rqstSno}";
	param += "&rqstDtlSno=" + "${result.rqstDtlSno}";
	param += "&rqstStepCd=S"; 	
	param += "&bizDcd=MTA";
	param += "&tblNm=WAQ_MTA_TBL"; 
	param += "&bizDtlCd=TBL"; 

	
	//var vUrl = "<c:url value="/meta/mta/regmtacolrqstlist.do"/>" + param;

	var saveJson = getform2IBSjson($("#frmPopTblInput")); 
		
	var ibsSaveJson = col_sheet.GetSaveJson(0);	 
	
	var data = new Object(); 
	
	data.dataTbl = saveJson.data;
	data.dataCol = ibsSaveJson.data;
			
	var vUrl = "<c:url value="/meta/mta/regMtaTblColRqstList.do"/>" + param;	
	
	$.ajax({
		type : "POST",
		url : vUrl,
		dataType : "json",
		contentType : 'application/json', 
		async: true,
		data : JSON.stringify(data),
		success : function(res) {

			var param = "";
			
			param += "&rqstNo="  + "${result.rqstNo}";
    		param += "&rqstSno=" + "${result.rqstSno}";
    		
    	    setTblInfo(param, false); 
						 
			doAction("SearchCol");
		},
		error : function(res) {

			// alert(res.data);
		}
	});	
}


function returnRcmdtermPop(ret){
	$("#mtaTblLnm").val(ret.TERM_NM);
}

//화면 로드시 공통 처리부분 여기에 추가한다.
$(window).on('load',function() {
	// 등록유형이 삭제인 경우
	if("${result.regTypCd}" == "D") {
		$.each($('#frmPopTblInput input,textarea,select'), function(i, d) {
			$(this).prop('readonly', true);
			$(this).addClass('d_readonly');
			// 콤보박스의 경우 readonly가 안먹히고, disable 시 값이 안넘어 가므로 별도 처리 
			if($(this).prop('tagName').toLowerCase() == 'select') {
				$(this).attr('onFocus', 'this.initialSelect = this.selectedIndex');
				$(this).attr('onChange', 'this.selectedIndex = this.initialSelect');
				$(this).unbind('change');
				$(this).prev().css('background', '#F3F3F3');
				// 클릭 방지를 위해 투명한 레이아웃으로 덮어씌움
				$(this).parent().find('.noclick').remove();
				var $layout = $('<div class="noclick"></div>');
				$layout.css({
					width: ($(this).width()+2)+'px'
				    , height: ($(this).height()+2)+'px'
				    , background: '#FFFFFFFF'
				    , position: 'absolute'
				    , opacity: 0
				    , 'z-index': 99999999
				    , top: 0
				});
				$(this).parent().append($layout);
			}
		});
	}
});

//비공개사유 팝업 리턴값 처리
/* function returnNopenRstPop (ret) {
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmPopTblInput #nopenRsn").val(retjson.codeCd);
	$("#frmPopTblInput #nopenRsnCdNm").val(retjson.codeLnm);
	
} */

function returnBrmInfoPop(brmFullPath, brmId, upperBrmId){	

	//alert(JSON.stringify(data));
	
	$("#frmPopTblInput #subjNm").val(brmFullPath);     //주제영역전체경로  
 	$("#frmPopTblInput #subjId").val(brmId);           //주제영역id
 	$("#frmPopTblInput #uppSubjId").val(upperBrmId);   //주제영역id
	
	/*
	var sData = jQuery.parseJSON(data);      
	
	$("#frmPopTblInput #subjNm").val(sData.subj_path); //주제영역전체경로 
 	$("#frmPopTblInput #subjId").val(sData.subj_id);   //주제영역id
 	$("#frmPopTblInput #uppSubjId").val(sData.upp_subj_id);   //주제영역id
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

function setNopenRsnChk() {
	
	var sNopenChkBox = "";
	var cnt = 1;	
	
	<c:set var="cnt" value="0" /> 
	
	<c:forEach var="code" items="${codeMap.nopenRsnCd}" varStatus="status" >
				
		if("${code.codeCd}" != "00"){
			
			<c:choose>
				<c:when test="${cnt == 3}" >  
				
					sNopenChkBox += "<input type='checkbox' id='chkNopenRsn${code.codeCd}' name='chkNopenRsn' value='${code.codeCd}' style='border:#FFF 0px solid;'/> ${code.codeLnm}";
					sNopenChkBox += "<br>";
					
					<c:set var="cnt" value="0" /> 
				</c:when> 						
				<c:otherwise> 
					sNopenChkBox += "<input type='checkbox' id='chkNopenRsn${code.codeCd}' name='chkNopenRsn'  value='${code.codeCd}' style='border:#FFF 0px solid;'/> ${code.codeLnm}&nbsp;&nbsp;";
				</c:otherwise>
			</c:choose>
			
			<c:set var="cnt" value="${cnt + 1}" />  
		}
	    
	</c:forEach> 
	
	sNopenChkBox += "<br><b>&nbsp;상세관련근거&nbsp;&nbsp;<input type='text' id='nopenDtlRelBss' name='nopenDtlRelBss'  style='width:80%;'  />"
	
	$("#divTblEnd").css("height","120px");  
	
	$("#frmPopTblInput #divNopenRsn").html(sNopenChkBox);
	
	//=========체크박스 값 세팅========
	var nopenRsn = "${result.nopenRsn}";
	
	arrNopenRsn = nopenRsn.split("|");
	
	for(var i = 0; i < arrNopenRsn.length; i++) {
		
		
		$("input[name=chkNopenRsn][value='"+  arrNopenRsn[i] + "']").prop("checked",true); 
	}
	
	$("#frmPopTblInput #nopenDtlRelBss").val("${result.nopenDtlRelBss}");
	//=================================
	
	//$("#divInputBtn").remove();
	
	
}

</script>
</head>
<body>
<!-- 입력폼 시작 -->
<div id="input_form_div">
     <form name="frmPopTblInput" id="frmPopTblInput" method="post" >
        <input type="hidden" id="rqstNo"    name="rqstNo"    value="${result.rqstNo}" />   
     	<input type="hidden" id="rqstSno"   name="rqstSno"   value="${result.rqstSno}" />
	  	<input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction}" /> 	
	  	
	  	<input type="hidden" id="rvwStsCd"  name="rvwStsCd" />  	  
	  	<input type="hidden" id="rvwConts"  name="rvwConts"  /> 	  	
	  	<input type="hidden" id="rqstDcd"   name="rqstDcd"  value="${result.rqstDcd}"  />	  
	  	<%-- <input type="hidden" id="regTypCd"  name="regTypCd" value="${result.regTypCd}" /> --%>	   	  
	  	<input type="hidden" id="vrfRmk"    name="vrfRmk"  />
	  	<input type="hidden" id="nopenRsn"  name="nopenRsn"  />  
    <fieldset>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<%-- <tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />  --%>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<%-- 
	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">시스템정보</div>
	<div style="clear:both; height:5px;"><span></span></div>
 	<legend>시스템정보</legend>  --%>
	<div class="tb_basic" style="display:none;">
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
					<th scope="row" >등록유형</th>
					<td>
					  <div class="sbox wd100">
						<label class="sbox_label" for="regTypCd"></label>
						<select id="regTypCd"  name="regTypCd" class="wd100" disabled >
			          	    <option value="" ></option>
                            <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" > 
                            	<option value="${code.codeCd}" >${code.codeLnm}</option>
                            </c:forEach>
                       	</select> 
                      </div>                       	
					</td>
					<th scope="row" >검증결과</th>
					<td>
					  <div class="sbox wd100">
						<label class="sbox_label" for="vrfCd"></label>
						<select id="vrfCd"  name="vrfCd" class="wd100" disabled >
			          	    <option value="" ></option>
                            <c:forEach var="code" items="${codeMap.vrfCd}" varStatus="status" > 
                            	<option value="${code.codeCd}" >${code.codeLnm}</option>
                            </c:forEach>
                       	</select> 
                      </div>                       	
					</td>					
				</tr>
			    <tr>					
					<th scope="row">테이블입력상태</th> 
					<td colspan="3">
					  <div class="sbox wd100">
						<label class="sbox_label" for="regTblStatus"></label>
						<select id="regTblStatus" name="regTblStatus" class="wd100"  disabled="disabled">
			          	    <option value="" ></option>
                            <option value="OK" >완료</option>
                            <option value="ERR" >미완료</option>
                        </select> 
                      </div>
					</td>
				</tr>
				<tr>
					<th scope="row" ><s:message code="ORG.NM"/></th>
					<td>
					  <div class="sbox wd100">
						<label class="sbox_label" for="orgCd"></label>
						<select id="orgCd" class="wd100" name="orgCd" disabled="disabled">
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
					  <div class="sbox wd100">
						<label class="sbox_label" for="infoSysCd"></label>
						<select id="infoSysCd" class="wd100" name="infoSysCd" disabled="disabled">
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
<%-- 	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit"><s:message code="TBL.INFO" /></div> <!-- 테이블 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div> --%>
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
                       <th scope="row"><label for="dbConnTrgId">DB명</label></th>  
                       <td  >                                   		                
<%-- 	                       	<select id="dbConnTrgId" class="" name="dbConnTrgId" disabled> 
								<option value="" ><s:message code="CHC" /></option>
							</select>
							<select id="dbSchId" class="" name="dbSchId" disabled > 
								<option value=""><s:message code="CHC" /></option>
							</select>
 --%>							
							<input type="text" id="dbConnTrgId" class="wd98p d_readonly" readonly />
							<input type="hidden" id="dbConnTrgPnm" name="dbConnTrgPnm" value="${result.dbConnTrgPnm }"  />
						</td>	
						<th scope="row"><label for="dbConnTrgId">테이블소유자</label></th>  
                       <td >                                   		                
<%-- 	                       	<select id="dbConnTrgId" class="" name="dbConnTrgId" disabled> 
								<option value="" ><s:message code="CHC" /></option>
							</select>
							<select id="dbSchId" class="" name="dbSchId" disabled > 
								<option value=""><s:message code="CHC" /></option>
							</select>
 --%>							
							<input type="text" id="dbSchId" class="wd95p d_readonly" readonly/>																				
						</td>						
                   </tr>
                   <tr>
                       <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="mtaTblPnm">테이블영문명</label><!-- css 사용 일때  g_ico1 . g_ico2. g_ico3. --> <!-- 이미지 사용 일때 <span class="ico_ga_img"><img class="" src="/wiseda/images/mj_mata_gr_01.png" alt="" title=""> <img class="" src="/wiseda/images/mj_mata_gr_02.png" alt="" title=""> <img class="" src="/wiseda/images/mj_mata_gr_03.png" alt="" title=""></span> --></th> <!-- 테이블명(물리명) -->
                       <td><input type="text" id="mtaTblPnm" name="mtaTblPnm" class="wd98p d_readonly" value="${result.mtaTblPnm }" title="<s:message code='MSG.TBL.NM.INPT' />" readonly/></td> <!-- 테이블명은 반드시 입력해야 합니다. -->
                       <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="mtaTblLnm">테이블한글명</label></th><!-- 테이블명(논리명) -->
                       <td>
                       		<input type="text" id="mtaTblLnm" name="mtaTblLnm" maxlength="100" style="width:50%;" value="${result.mtaTblLnm }"  />
                       		<span id="divStnd"></span>
	                     	<button class="btnSearchPop" type="button" id="btnItem"	name="btnItem">표준용어검색</button>
                       </td>       
                   </tr>
                   <tr class="ht50">
                       <th scope="row" class="th_require"><label for="objDescn">테이블<s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn }</textarea></td>
                   </tr>
                   <tr>
                   	  <th scope="row" class="th_require"><label for="subjNm">업무분류체계</label></th>
                      <td colspan="3">
	                      <input type="text" id="subjNm" name="subjNm" readonly style="width:90%;" class="d_readonly" value="${result.subjNm }" /> 
	                      <button class="btnSearchPop" type="button" id="btnBrmCls"	name="btnBrmCls">BRM검색</button>
	                      <input type="hidden" id="subjId"     name="subjId"  />
	                      <input type="hidden" id="uppSubjId"  name="uppSubjId"  />  	                      
                      </td>
                   </tr>
                   <tr>
					   <th scope="row" class="th_require"><label for="openRsnCd">공개/비공개여부</label></th>
                       <td>
						  <div class="sbox wd100">
							<label class="sbox_label" for="openRsnCd"></label>
                       		<select id="openRsnCd" name="openRsnCd">	
	                              <option value="" ><s:message code="CHC" /></option>   	            	  
		                    	  <c:forEach var="code" items="${codeMap.openRsnCd}" varStatus="status" >
		                           	<option value="${code.codeCd}" >${code.codeLnm}</option>
		                          </c:forEach>
                            </select> 
                          </div>             		
                       </td>     
                   	  <th scope="row"><label for="tblTypNm">테이블유형</label></th> 
                      <td>
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
                   </tr>
                   <tr id="trNopenRsn">					                   
                       <th scope="row" class="th_require"><label for="nopenRsnCd">비공개사유</label></th>
                       <td colspan="3">
                       		<div id="divNopenRsn"></div>   		  
                       </td>                   		                   		
                   </tr>
                   <tr>
                      <th scope="row" class="th_require"><label for="dqDgnsYn">품질진단여부</label></th>
                      <td> 
						  <div class="sbox wd100">
							<label class="sbox_label" for="dqDgnsYn"></label>
                      		<select id="dqDgnsYn" name="dqDgnsYn" class="wd100">
                      		    <option value="" ><s:message code="CHC" /></option>   
                      			<option value="Y"><s:message code="MSG.YES" /></option>
                      			<option value="N"><s:message code="MSG.NO" /></option>                    				
                      		</select>
                      	  </div>
                      		<!-- <input type="checkbox" id="dqDgnsYn" name="dqDgnsYn" value="Y"/>품질진단여부</span>  -->
                      		<span class="sbox_label"> &nbsp; 2018년 품질진단수준평가 여부를 선택합니다.</span>
					  </td>  
					   <th scope="row"><label for="occrCyl">발생주기</label></th>
                       <td>
						  <div class="sbox wd100">
							<label class="sbox_label" for="occrCyl"></label>
                       		<select id="occrCyl" name="occrCyl" class="wd100">	
	                              <option value="" ><s:message code="CHC" /></option>   	            	  
		                    	  <c:forEach var="code" items="${codeMap.occrCyl}" varStatus="status" >
		                           	<option value="${code.codeCd}" >${code.codeLnm}</option>
		                          </c:forEach>
                            </select>                        		
                          </div>
                       </td>    
                   </tr>
                   <tr>
                   	  <th scope="row"><label for="prsvTerm">보존기간</label></th> <!-- 보관주기 -->
                      <td>
						  <div class="sbox wd100">
							<label class="sbox_label" for="prsvTerm"></label>          
                          <select id="prsvTerm" name="prsvTerm" class="wd100">	
                              <option value="" ><s:message code="CHC" /></option>   	            	  
	                    	  <c:forEach var="code" items="${codeMap.prsvTerm}" varStatus="status" >
	                           	<option value="${code.codeCd}" >${code.codeLnm}</option>
	                          </c:forEach>
                          </select>
                          </div>
                    	  <%-- <input type="text" id="prsvTerm" name="prsvTerm" class="wd98p" value="${result.prsvTerm }"/> --%>
                      </td> 
                      <th scope="row" class="g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="tblVol">테이블볼륨(ROW수)</label></th>
                      <td>
                      	<input type="number" id="tblVol" name="tblVol" class="wd95p d_readonly" value="${result.tblVol }" readonly />
                      </td> 
                   </tr>
                   <tr class="ht50">
                       <th scope="row"><label for="openDataLst">개방데이터목록</label></th> <!-- 설명 -->
                       <td colspan="3"><textarea id="openDataLst" name="openDataLst" class="wd98p d_readonly" readonly>${result.openDataLst }</textarea></td>
                   </tr>
               </tbody>
           </table>
       </div>
       </fieldset>
      
	
	
	 </form>
</div>
</body>
</html>