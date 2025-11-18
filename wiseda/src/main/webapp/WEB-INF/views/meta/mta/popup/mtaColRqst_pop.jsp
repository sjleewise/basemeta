<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->

<script type="text/javascript">

var moveColInfo = false;


$(document).ready(function() {


		//====================================================== 
		// 폼 검증 초기화...
		//======================================================
		//필수입력항목입니다. 내용을 입력해 주세요. 
		var requiremessage = "<s:message code="VALID.REQUIRED" />";
		//폼검증
		$("#frmColInput").validate({
			rules: {
				mtaColPnm		: "required",
				mtaColLnm		: "required",
				dataType		: "required",
				priRsn			: "required", 
				objDescnCol		: "required"
			},
			messages: {
				mtaColPnm		: requiremessage,
				mtaColLnm		: requiremessage,
				dataType		: requiremessage,
				priRsn			: requiremessage,
				objDescnCol		: requiremessage
			}
		});
	

	
        //버튼 초기화...
    	$("#popReset").hide();
    	$("#popExcelDown").hide();
    	$("#btnColReset").hide();
    	
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close, #btnCloseBottom").click(function() {
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        });

        $("#btnColRowSaveMove").click(function(event) {
        	event.preventDefault();  //브라우저 기본 이벤트 제거...

        	moveColInfo = true;

        	$('#btnColRowSave').click();

         });

      //폼 저장 버튼 초기화...
    	$('#btnColRowSave').click(function(event){
    		event.preventDefault();  //브라우저 기본 이벤트 제거...
    		//요청단계가 등록요청('Q') 상태인 경우에는 검토내용만 IBSheet에 셋팅한다. rqstStepCd
			/* if(chkByteByStr($('#objDescnCol').val(), 30)) {
				showMsgBox("ERR", "컬럼설명을 상세하게 기재하세요");
				$('#objDescnCol').focus();
		    	return false;
			} */
    		
    		if(!$("#frmColInput").valid()) return false;
    		
    		if ($("#mstFrm #rqstStepCd", document.parent).val() == "Q") {
    			var srow = col_sheet.GetSelectRow();
//     			alert(srow);
//     			alert($("#frmColInput #rvwConts").val());
    			//col_sheet.SetCellValue(srow, "rvwConts", $("#frmColInput #rvwConts").val());
    			return;
    		}
    				
    		//IBSheet 저장용 JSON 전역변수 초기화
    		ibsSaveJson = null;
    		
    		//변경한 시트 단건 내용을 저장...
//     		alert("단건저장");
    		//폼 검증...
    		//if(!$("#frmColInput").valid()) return false;
    		
    		//필수입력 체크 
	     	if(!checkSaveCol()) return false;
	     		
      		
    		//저장할래요? 확인창...
    		//var message = "<s:message code="CNF.SAVE" />";

    		//showMsgBox("CNF", message, 'SaveColRow');
    		
    	});

    	/* $("#priRsnSearchPop").click(function(){
    		var param = "sFlag=COL";

    		var vUrl = "<c:url value='/meta/mta/popup/nopenRsnSearchPop.do' />"; 

    		vUrl = vUrl + "?" + param;
    		
    		//openLayerPopup("<c:url value='/meta/mta/popup/nopenRsnSearchPop.do' />", 800, 600, param);

    		OpenWindow(vUrl, "noopenRsnSearchPop", 800, 600, null);
        }); */
        	    	
    	//추천용어 구현전
    	$("#btnItem").click(function(){

    		if(isBlankStr($("#frmColInput #mtaColLnm").val())) {
    			showMsgBox("ERR", "컬럼한글명을 입력해주세요.");
    			return false;
    		}
    		
    		var param = ""; 
    		    		
    		param += "&keyword=" + encodeURIComponent($("#frmColInput #mtaColLnm").val());      
    		
    		//param += "&keyword=" + $("#frmColInput #mtaColLnm").val(); 

    		var vUrl = "<c:url value='/meta/mta/popup/rcmdTermSearchpop.do' />"; 
    		
    		//alert(vUrl); 

    		//OpenWindow(vUrl, "reconItem", 800, 600, null); 
    		
    	    openLayerPop(vUrl, 700, 500, param);     		
    	});
        
        $("#btnColHelp").click(function(){

    		var vUrl = "<c:url value='/meta/admin/popup/infosys_help.do?typCd=col' />"; 
    		OpenWindow(vUrl, "infosys_help", 991, 811, null);
        });

        /*
    	$("#mtaColLnm").keydown(function(event){

        	var temp = korengCheck($(this).val());

        	$(this).val(temp);
    		    
   	    	//if(!korengCheck(event)) return false;
   	    });

    	$("#mtaColLnm").keyup(function(event){

        	var temp = korengCheck($(this).val());

        	$(this).val(temp);
    		    
   	    	//if(!korengCheck(event)) return false;
   	    });
    	*/
   	    
    	//공개여부 Y이면
/*     	if($("#frmColInput #openYn").val() == "Y") {
    		    		
    		$("#frmColInput #openYn").val('${result.openYn}').attr("disabled", true);  
        	$("#frmColInput #prsnInfoYn").val('${result.prsnInfoYn}').attr("disabled", true);  
        	$("#frmColInput #encTrgYn").val('${result.encTrgYn}').attr("disabled", true);  
        	$("#frmColInput #priRsn").val('${result.priRsn}').attr("disabled", true);

        	$('#frmColInput #tr_require').hide();  
    	}else{ */

			var openRsnCd = $(parent.document).find('#frmPopTblInput #openRsnCd').val();

			$("#frmColInput #openYn").val('${result.openYn}');
        	$("#frmColInput #prsnInfoYn").val('${result.prsnInfoYn}');
        	$("#frmColInput #encTrgYn").val('${result.encTrgYn}');
        	$("#frmColInput #priRsn").val('${result.priRsn}').attr("disabled", false);
        	$("#frmColInput #priRsn").prev().removeClass('d_readonly');
        	
			// 비공개 이면 공개여부 N 이고 비공개 선택 불가
			if(openRsnCd == "02") {
				$("#frmColInput #openYn").attr("disabled", "disabled");  
				$("#frmColInput #openYn").prev().addClass('d_readonly');
	        	$('#frmColInput #tr_require').hide();  
	        	$("#frmColInput #prsnInfoYn").attr("disabled", false);  
	        	$("#frmColInput #encTrgYn").attr("disabled", false);  
	        	$("#frmColInput #prsnInfoYn").prev().removeClass('d_readonly');
	        	$("#frmColInput #encTrgYn").prev().removeClass('d_readonly');
	        	$('#frmColInput #tr_require').hide();  
			}
			// 부분공개 이면 공개여부 선택 가능
			else if(openRsnCd == "03") {
				if($("#frmColInput #openYn").val() != "Y") {
					$('#frmColInput #tr_require').show();  
	    		}else{
	    			$('#frmColInput #tr_require').hide();  
		    	}
				$("#frmColInput #openYn").change(function() {
					// 비공개 일때 비공개 사유 선택 가능
					if($(this).val() == "Y") {
						$('#frmColInput #tr_require').hide();  
			        	$("#frmColInput #prsnInfoYn").val('N').prop("disabled", true);  
			        	$("#frmColInput #encTrgYn").val('N').prop("disabled", true); 
			        	$("#frmColInput #prsnInfoYn").prev().addClass('d_readonly');
			        	$("#frmColInput #encTrgYn").prev().addClass('d_readonly');
					} else {
						$('#frmColInput #tr_require').show();  
			        	$("#frmColInput #prsnInfoYn").val('').prop("disabled", false);
			        	$("#frmColInput #encTrgYn").val('').prop("disabled", false);
			        	$("#frmColInput #prsnInfoYn").prev().removeClass('d_readonly');
			        	$("#frmColInput #encTrgYn").prev().removeClass('d_readonly');
					}
			    	SboxSetLabelEvent();
				});
			}else{
				$("#frmColInput #openYn").val('Y').attr("disabled", true);  
	        	$("#frmColInput #prsnInfoYn").val('N').attr("disabled", true);  
	        	$("#frmColInput #encTrgYn").val('N').attr("disabled", true);  
	        	$("#frmColInput #priRsn").val('').attr("disabled", true);
	        	$("#frmColInput #openYn").prev().addClass('d_readonly');
	        	$("#frmColInput #prsnInfoYn").prev().addClass('d_readonly');
	        	$("#frmColInput #prsnInfoYn").prev().addClass('d_readonly');
	        	$("#frmColInput #encTrgYn").prev().addClass('d_readonly');

	        	$('#frmColInput #tr_require').hide();  
	        }
//     	}

	$('#nonulYn').val('${result.nonulYn}');
	$('#pkYn').val('${result.pkYn}');
// 	$('#fkYn').val('${result.fkYn}');
    	    
    	SboxSetLabelEvent();
    	
});

$(window).on('load',function() {
	
	$(window).resize();
	
	checkStwdCol("${result.mtaColLnm}");
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_sheet.FitColWidth();
	
});


//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
	 switch(sAction)
	 {     
	     case "SaveColRow":        //컬럼 저장...
	 	     		    
		    $("#frmColInput select").attr("disabled", false); 
	    		
	     	var saveJson = getform2IBSjson($("#frmColInput"));

	     	$("#frmColInput select").attr("disabled", true);
	     		  	
			var url = "<c:url value="/meta/mta/regmtacolrqstlist.do"/>"; 
			
			var param = "";  

			param += "&rqstNo="     + "${result.rqstNo}";
			param += "&rqstSno="    + "${result.rqstSno}";
			param += "&rqstDtlSno=" + "${result.rqstDtlSno}";
			param += "&rqstStepCd=" + "${result.rqstStepCd}";
			param += "&bizDcd=MTA";
			param += "&tblNm=WAQ_MTA_TBL"; 
			param += "&bizDtlCd=TBL"; 

			//alert(param); 
			
			IBSpostJson2(url, saveJson, param, ibsReCallback);
	    
	       	break;       
	 }       
}
//한글(완성형)이 포함되었는지 여부
function containKOR(str) {
	var chk = /[가-힣]/g;
	if(chk.test(str)) {
		return true;
	} else {
		return false;
	}
}

//공백 체크 
function checkSapce(val) { 
	
	var chk = /\s/g;  
	
	if(chk.test(val)) {
		return false;
	}
	
	return true;
} 


//특수문자 체크 
function checkExpChar(val) {  
	
	var chk = /[^가-힝a-zA-Z0-9]/g;  
	
	if(chk.test(val)) {
		return false;
	}
	
	return true;
}

// 문자열 바이트 체크 (문자열, 체크할 바이트 수)
function chkByteByStr(str, byte) {
	if(isBlankStr(byte)) {
		return false;
	} 
	var pattern = /[\u0000-\u007f]|([\u0080-\u07ff]|(.))/g;
	var chkByte = str.replace(pattern, "$&$1$2").length;
	if(chkByte < byte) {
		return true;
	} else {
		return false;
	}
}

//특수문자 체크 (허용할 문자 추가 체크)
function checkExpChar2(val, allow) {  
	
	// allow 는 제외하고 체크
	var temp = val.split(allow).join("").split(" ").join("");
	
	var chk = /[^가-힝a-zA-Z0-9]/g;  
	
	if(chk.test(temp)) {
		return false;
	}
	
	return true;
}
function checkSaveCol() {

	var vUrl ="<c:url value="/meta/mta/popup/checkMtaColNm.do"/>";   

	var param = "";  
	param += "&rqstNo="     + "${result.rqstNo}";
	param += "&rqstSno="    + "${result.rqstSno}";
	param += "&rqstDtlSno=" + "${result.rqstDtlSno}";
	param += "&mtaColLnm=" + $("#mtaColLnm").val();
	$.ajax({
		type : "POST",
		url : vUrl,
		dataType : "json",
		// async: false,
		data : param,
		success : function(res) {
			if(res.chk == "N") {
				showMsgBox("ERR","테이블 내에 중복된 컬럼명이 있습니다.");
				return false;
			}
			if($("#mtaColPnm").val() == "") {
				showMsgBox("ERR","컬럼영문명을 입력하세요.");
				return false;
			}
			
			if($("#mtaColLnm").val() == "") {
				showMsgBox("ERR","컬럼한글명을 입력하세요.");
				return false;
			}
			
			var mtaColLnm = $("#mtaColLnm").val();
			
			if(!containKOR(mtaColLnm)) {
				showMsgBox("ERR","컬럼한글명은 한글을 포함하여 입력하세요.");   
				return false;
			}
			
			/* if(!checkSapce(mtaColLnm)) {  
				showMsgBox("ERR","컬럼한글명은 공백을 입력할 수 없습니다.");   
				return false;
			} */
			
			if(!checkExpChar2(mtaColLnm, "_")) {  
				showMsgBox("ERR","컬럼한글명은 특수문자를 입력할 수 없습니다.");   
				return false;
			}
			
			if($("#dataType").val() == "") {
				showMsgBox("ERR","데이터타입을 입력하세요.");
				return false;
			}
			
			if($("#prsnInfoYn").val() == "") {
				showMsgBox("ERR","개인정보여부를 입력하세요.");
				return false;
			}
				
			if($("#openYn").val() == "") {
				showMsgBox("ERR","공개여부를 입력하세요.");
				return false;
			}
			
			if($("#encTrgYn").val() == "") {
				showMsgBox("ERR","암호화여부를 입력하세요.");
				return false;
			}
			
			if($("#objDescnCol").val().trim() == "") {
				showMsgBox("ERR","컬럼설명을 입력하세요.");
				return false;
			}

    		var message = "<s:message code="CNF.SAVE" />";

    		showMsgBox("CNF", message, 'SaveColRow');
		},
		error : function(res) {

		}
	});	
	
}


function ibsReCallback(res) {
	
	var result = res.RESULT.CODE;
	if (result == 0) {
		// alert(res.RESULT.MESSAGE);

		// 공통메세지 팝업 : 성공 메세지...
		if(!moveColInfo) showMsgBox("INF", res.RESULT.MESSAGE);
		if (!isBlankStr(cnfNextFunc)) {
			eval(cnfNextFunc);
			return;
		}
		// alert(postProcessIBS);
		if (postProcessPopColIBS != null) {
			postProcessPopColIBS(res);
		}
	} else if (result == 401) {
		// 권한이 없어요...
		showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
	} else {
		// alsert("저장실패");
		// 공통메시지 팝업 : 실패 메세지...
		showMsgBox("ERR", res.RESULT.MESSAGE);
	}
}

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessPopColIBS(res) {
	
	
	//alert(res.action);
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
// 		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	//alert(res.action);
	
	switch(res.action) {
		
		//요청서 저장 및 검증
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
			
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {

		    	var tblRow = "${result.tblRow}";  

				//parent.selectedMtaTblRowIdx = tblRow;
				
				//저장 후 다음 컬럼정보 이동
				if(moveColInfo) {
					parent.moveColInfo("${result.rqstDtlSno}");

				}else {

					parent.doAction("SearchCol"); 
					
					//iframe 형태의 팝업일 경우
		        	if ("${search.popType}" == "I") {
		        		parent.closeLayerPop();
		        	} else {
		        		window.close();
		        	}
				}
		 	 	
	    	 }
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;		
	}	
}

//비공개사유 팝업 리턴값 처리
/* function returnNopenRstPopCol (ret) {
	var retjson = jQuery.parseJSON(ret);
	
	$("#frmColInput #priRsn").val(retjson.codeCd);
	$("#frmColInput #priRsnCdNm").val(retjson.codeLnm);
	
} */

function returnRcmdtermPop(ret){
	$("#mtaColLnm").val(ret.TERM_NM);
}

function korengCheck(inText){

	var re = /[^가-힝,ㄱ-ㅎ,a-z,A-Z,0-9]/gi;

	if(re.test(inText)){
		inText = inText.replace(re,"");	
	}

	return inText;
}

function checkStwdCol(chkTerm) {
	
	var vUrl ="<c:url value="/meta/stnd/api/request/getWamStwdList.do"/>";   
	
	param = "stwdLnm=" + chkTerm;
	
	$.ajax({
		type : "POST",
		url : vUrl,
		dataType : "json",
		async: false,
		data : param,
		success : function(res) {
			
			//alert(JSON.stringify(res.DATA));
			
			if(res.DATA.length == 0){
				$("#frmColInput #divColStnd").html("비표준");
				$("#frmColInput #divColStnd").css("color","red");				
			}else{
				$("#frmColInput #divColStnd").html("표준"); 
			} 
		},
		error : function(res) {

			// alert(res.data);
		}
	});	
}



</script>


<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">컬럼정보수정 (No.${result.colOrd})</div> <!-- 주제영역 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 입력폼 시작 -->
<div id="input_col_form_div">
     <form name="frmColInput" id="frmColInput" method="post" >
        <input type="hidden" id="rqstNo" 	  name="rqstNo" 	value="${result.rqstNo}" >
     	<input type="hidden" id="rqstSno" 	  name="rqstSno" 	value="${result.rqstSno}" >
     	<input type="hidden" id="rqstDtlSno"  name="rqstDtlSno" value="${result.rqstDtlSno}" >
	  	<input type="hidden" id="ibsStatus"   name="ibsStatus" 	value="${saction }" >

		<input type="hidden" id="mtaTblId" 	name="mtaTblId" value="${result.mtaTblId}" >
		<input type="hidden" id="mtaColId" 	name="mtaColId" value="${result.mtaColId}" >	  	
	  	<input type="hidden" id="colOrd"    name="colOrd"   value="${result.colOrd }"/>
	  	<input type="hidden" id="pkOrd"     name="pkOrd"    value="${result.pkOrd }" />
	  	
	  	<input type="hidden" id="rqstDcd"   name="rqstDcd"  value="${result.rqstDcd}"  />	  
	  	<input type="hidden" id="regTypCd"  name="regTypCd" value="${result.regTypCd}" />	  
	  	<input type="hidden" id="vrfCd"     name="vrfCd"    value="${result.vrfCd}" />	
	  	<input type="hidden" id="vrfRmk"    name="vrfRmk"  />
	  	
	  	<input type="hidden" id="rvwStsCd"  name="rvwStsCd" />  	  
	  	<input type="hidden" id="rvwConts"  name="rvwConts"  /> 	  		  	
	  	
    <fieldset>
    
    <div class="bt02">
    	<a id="btnColHelp"><img src="<c:url value="/images/icon_help.png"/>" /></a>		   
    </div>

	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<%-- <tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" /> --%>
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->

	<div style="clear:both; height:10px;"><span></span></div>
	 <div class="stit"><s:message code="CLMN.INFO" /></div> <!-- 컬럼 정보 -->
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
                     <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga" >&nbsp;</span> <label>컬럼영문명</label> </th> <!-- 컬럼명 -->
                     <td>
                     	<input type="text" id="mtaColPnm" name="mtaColPnm" class="wd200" class="d_readonly" value="${result.mtaColPnm }" readonly /> <!-- 물리명 -->                     	
                     </td>
                     <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label>컬럼한글명</label> </th> <!-- 컬럼명 -->
                     <td>
                     	<input type="text" id="mtaColLnm" name="mtaColLnm" style="width:50%;" value="${result.mtaColLnm }" maxlength=100 /> <!-- 논리명 -->
                     	<span id="divColStnd"></span>
                     	<button class="btnSearchPop" type="button" id="btnItem"	name="btnItem">표준용어검색</button>                     	
                     </td>
                 </tr>
                 <tr class="ht50">
                     <th scope="row" class="th_require"><label for="objDescnCol">컬럼<s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                     <td colspan="3"><textarea class="wd98p ht50" id="objDescnCol" name="objDescnCol" accesskey="">${result.objDescn }</textarea></td>
                 </tr>
                 <tr>
                     <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label><s:message code="DATA.TY" /> </label></th><!-- 데이터 -->
                     <td>
                     	<input type="text" id="dataType" name="dataType" class="wd200 d_readonly"  value="${result.dataType }" readonly /> <!-- 데이터타입 -->
                     </td>
                     <th scope="row" class="g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label><s:message code="DATA.LNGT" /> </label></th><!-- 데이터 -->
                     <td>
                     	<input type="text" id="dataLen" name="dataLen" class="wd200 d_readonly" value="${result.dataLen }" readonly /> <!-- 데이터길이 -->
                     </td>
                 </tr>
                 <tr>
            <%--          <th scope="row"><label for="defltVal"><s:message code="DEFAULT.VAL" /></label></th> <!-- DEFAULT 값 -->
                     <td><input type="text" class="wd300" id="defltVal" name="defltVal" value="${result.defltVal }" /></td> --%>
                     <th scope="row"><label for="dataFmt">데이터포맷(샘플값)</label></th> 
                     <td colspan="3">
                         <input type="text" id="dataFmt" name="dataFmt" class="wd200" value="${result.dataFmt }"/>
                     </td>
                 </tr>
                 <tr>
                 	<th scope="row" class="g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="nonulYn"><s:message code="NOTNULL.YN" /> </label></th> <!-- NOT NULL 여부 -->
                    <td>             	
						<div class="sbox wd100">
						<label class="sbox_label d_readonly" for="nonulYn "></label>
                     	<select id="nonulYn" name="nonulYn"  disabled  >
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   		</select>
                   		</div>
                   	</td>
                     <th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="pkYn"><s:message code="PK.INFO" /> </label></th><!-- PK 정보 -->
                     <td>
                     	<div class="sbox wd100">
						<label class="sbox_label d_readonly" for="pkYn"></label>
                     	<select id="pkYn" name="pkYn" disabled>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   		</select>
                   		</div>
                     </td>
                 </tr>
                 <tr>
                     <th scope="row" class="g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="fkYn"><s:message code="FK.INFO" /></label> </th><!-- FK 정보 -->
                     <td>
                     	<%-- <div class="sbox wd100">
						<label class="sbox_label d_readonly" for="fkYn"></label>
                     	<select id="fkYn" name="fkYn" disabled>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   		</select>
                   		</div> --%>
                   		<input type="text" id="fkYn" name="fkYn" class="wd95p d_readonly" value="${result.fkYn }"/>
                     </td>
                     <th scope="row" class="g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="constCnd">제약조건</label> </th> 
                     <td>
                         <input type="text" id="constCnd" name="constCnd" class="wd200 d_readonly" value="${result.constCnd }"/>
                     </td>
                 </tr>
                 <tr>
                     <th scope="row" class="th_require"><label for="openYn">공개/비공개여부</label></th> <!-- 공개여부 -->
                     <td colspan="3">
                     	<div class="sbox wd100">
						<label class="sbox_label" for="openYn"></label>
                     	<select id="openYn" name="openYn">
                     		<option value=""><s:message code="CHC" /></option>
                   			<option value="Y">공개</option>
                   			<option value="N">비공개</option>
                   		</select>
                   		</div>
                     	<%--<span class="input_check"><input type="checkbox" id="openYn" name="openYn" value="Y"/>개방여부</span>  --%>
                     </td>
                 </tr>
                 <tr class="ht50" id="tr_require" style="display:none;">
                    <%-- <th scope="row" class="th_require"><label for="priRsn">비공개사유</label></th>
                    <td colspan="3"> 
						<input type="hidden" id="priRsn" name="priRsn" value="${result.priRsn}" readonly="readonly"/>
						<span class="input_inactive">
						    <input type="text" id="priRsnCdNm" name="priRsnCdNm"  readonly value="${result.priRsnCdNm }" style="width:80%;" /> 							
						</span>
					 	<button class="btnDelPop" ><s:message code="DEL" /></button><!-- 삭제 -->
					 	<button class="btnSearchPop" id="priRsnSearchPop"><s:message code="SRCH" /></button> <!-- 검색 -->
					</td> --%>
					<th scope="row" class="th_require"><label for="priRsn">비공개사유</label></th>
                      	<td colspan="3">
                      		<div class="sbox wd400">
							<label class="sbox_label" for="priRsn"></label>
                      		<select id="priRsn" name="priRsn" class="wd400">
                      			<option value=""><s:message code="CHC" /></option>
	                            <c:forEach var="code" items="${codeMap.nopenRsnCd}" varStatus="status" >
	                            	<option value="${code.codeCd}" >${code.codeLnm}</option>
	                            </c:forEach>
                      		</select>
                      	</div>
               		</td>
                  </tr>
                 <tr>
                     <th scope="row" class="th_require"><label for="prsnInfoYn">개인정보여부</label></th>
                     <td>
                     	<div class="sbox wd100">
						<label class="sbox_label" for="prsnInfoYn"></label>
                         <select id="prsnInfoYn" name="prsnInfoYn" >
                            <option value=""><s:message code="CHC" /></option>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   		</select>
                   		</div>
                    	<%--  <span class="input_check"><input type="checkbox" id="prsnInfoYn" name="prsnInfoYn" value="Y"/>개인정보여부</span> --%> 
                     </td>
                 	<th scope="row" class="th_require"><label for="encTrgYn">암호화여부</label></th>
                     <td>
                     	<div class="sbox wd100">
						<label class="sbox_label" for="encTrgYn"></label>
                     	<select id="encTrgYn" name="encTrgYn" >
                     		<option value=""><s:message code="CHC" /></option>
                   			<option value="Y"><s:message code="MSG.YES" /></option>
                   			<option value="N"><s:message code="MSG.NO" /></option>
                   		</select>
                   		</div>
                     	<%-- <span class="input_check"><input type="checkbox" id="encTrgYn" name="encTrgYn" value="Y"/>암호화여부</span>  --%>
                    </td>
                 </tr>
                 <%-- <tr>
                     <th scope="row" class="th_require"><label for="priRsn">비공개사유</label></th> 
                     <td colspan="3"><textarea class="wd98p" id="priRsn" name="priRsn" accesskey="">${result.priRsn }</textarea></td>
                 </tr> --%>
             </tbody>
         </table>
     </div>
    </fieldset>
    </form>
	<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 입력폼 버튼... -->
	<div class="tb_comment g_ico g_ico1">&nbsp;<span class="ico_ga">&nbsp;</span>보유DB로 부터 메타데이터를 자동수집하는 항목으로 입력하지 않습니다. 단, 컬럼한글명은 수정 가능합니다.</div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divColBtn" style="text-align: center;">
		<button class="btn_frm_save" id="btnColRowSave" name="btnColRowSave"><s:message code="STRG" /></button> <!-- 저장 -->
		<button class="btn_frm_save" id="btnColRowSaveMove" name="btnColRowSaveMove"><s:message code="STRG.AF.CLMN.INFO.MOVE" /></button> <!-- 저장 후 다음 컬럼정보 -->
		<button class="btn_frm_reset" id="btnColReset" name="btnColReset" ><s:message code="INON" /></button> <!-- 초기화 -->
		<button class="btn_frm_save btn_colse" id="btnCloseBottom" type="button">닫기</button><!-- 닫기 -->
	</div>
	
</div>
</body>


