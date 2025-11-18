
<!DOCTYPE html>
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

$(document).ready(function(){
	
	//팝업 클릭이벤트시 reset 되는것 방지 위해
	$('form[name=frmInputPC04]').click(function(event){
		event.stopPropagation();	//브라우저 기본 이벤트 제거...
	}); 
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code="VALID.SHORTREQUIRED" />";
	
	//폼검증
	 $("form[name=frmInputPC04]").validate({
		rules: {
			rngOpr1		: "required",
			rngEfva1 	: "required",
			rngOpr2 : {
				required: function(){
					 if($('form[name=frmInputPC04] #rngCnc').val() == ""){ return false;  }
		             return true;
				}
			},
			rngEfva2 : {
				required: function(){
					 if($('form[name=frmInputPC04] #rngCnc').val() == ""){ return false;  }
		             return true;
				}
			},
			dqiLnm		: "required"
		},
		messages: {
			rngOpr1		: requiremessage,
			rngEfva1 	: requiremessage,
			rngOpr2		: requiremessage ,
			rngEfva2		: requiremessage ,
			dqiLnm		: requiremessage
		}
	});  
	
	
	//연산자 
	$("form[name=frmInputPC04] #rngCnc").change(function(event){
		setrngOpr2();
	});
	
	// 품질지표 조회
    $("#frmInputPC04 #btnApply").click(function() {
    	var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=PRFPC04"; 
    		param += "&dqiIds="+$("#frmInputPC04 #dqiId").val();
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
    });
	
	//입력폼 reset
// 	resetPC04();
});


$(window).on('load',function(){
});


$(window).resize( function(){
    }
);

//DQI 팝업 리턴값 처리
function returnDqiPopPC04 (ret) {
	//초기화
	$("#frmInputPC04 #dqiLnm").val("");
	$("#frmInputPC04 #dqiId").val("");
	
	for(var i=0; i<ret.data.length; i++){
		var retjson = JSON.stringify(ret.data[i]);
		var parsejson = jQuery.parseJSON(retjson);
		
		if($("#frmInputPC04 #dqiLnm").val() != null && $("#frmInputPC04 #dqiLnm").val() != "undefined" && $("#frmInputPC04 #dqiLnm").val() != "" ){
			$("#frmInputPC04 #dqiLnm").val($("#frmInputPC04 #dqiLnm").val() + "," + parsejson.dqiLnm);
			$("#frmInputPC04 #dqiId").val($("#frmInputPC04 #dqiId").val() + "," + parsejson.dqiId);
		}else {
			$("#frmInputPC04 #dqiLnm").val(parsejson.dqiLnm);
			$("#frmInputPC04 #dqiId").val(parsejson.dqiId);
		}
	}
}

function setrngOpr2()
{
	if($("form[name=frmInputPC04] #rngCnc").val() == "")
	{
		//연결자 이후 범위연산자 초기화
		$("form[name=frmInputPC04] #rngOpr2").val("");
		$("form[name=frmInputPC04] #rngEfva2").val("");
		
		$("form[name=frmInputPC04] #rngOpr2").hide();
		$("form[name=frmInputPC04] #rngEfva2").hide();
	}
	else
	{
		$("form[name=frmInputPC04] #rngOpr2").show();
		$("form[name=frmInputPC04] #rngEfva2").show();
	}
}

function resetPC04(){
	//프로파일 상세정보 초기화
	$("form[name=frmInputPC04]")[0].reset();
	setrngOpr2();
}

function savePC04(){
	//입력필드 체크박스 확인
	if(!$("form[name=frmInputPC04]").valid()){
		var message = "<s:message code='REQUIRED.INPT.ITEM' />";
		showMsgBox("INF", message); 
		return;
	}
	//저장
	var urls = '<c:url value="/dq/profile/registerProfile.do"/>';
		var param =   "&"+ $("form[name=frmAnaTrg]").serialize(); //진단대상 정보
		     param += "&"+$("form[name=frmInputPC04]").serialize(); //날짜형식분석 정보
		     
	ajax2Json(urls, param, ibscallback);
}

function setDtlPC04(data){
	json2formmapping($("form[name=frmInputPC04]"), data.resultVO);
	setrngOpr2();
}

</script>

<!-- </head> -->
<!-- <body>     -->
	<div id="searchPC04_div" >
		<div style="clear:both; height:5px;"><span></span></div>
		<div class="stit"><s:message code="RNG.ANLY.DTL.INFO"/></div><!--범위분석 상세정보-->
		<div style="clear:both; height:5px;"><span></span></div>
		
		<form id="frmInputPC04" name="frmInputPC04" method="post">
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!--머리말-->
		<div class="tb_basic2" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='RNG.ANLY'/>"> <!--범위분석-->
			   <caption><s:message code="RNG.ANLY"/></caption><!--범위분석-->
			   <colgroup>
			   	<col style="width:15%;" />
		   		<col style="width:35%;" />
			   	<col style="width:15%;" />
		   		<col style="width:35%;" />
			   </colgroup>
			       <tbody>   
			           <tr>
			           		<th scope="row" class="th_require"><s:message code="RNG.OPERATOR"/></th><!--범위연산자-->	
			           		<td>
			           			<select id="rngOpr1"  name="rngOpr1" class="" style="width:;">
									<option value=""><s:message code="CHC" /></option><!--선택-->

									<c:forEach var="code" items="${codeMap.rngOprCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
									<%-- 									
									<option value=">="><</option>
									<option value="<=">></option>
									<option value=">"><=</option>
									<option value="<">>=</option> 
									--%>
								</select>
							</td>
							<th scope="row" class="th_require"><s:message code="RNG.VLD.VAL"/></th><!--범위유효값-->	
							<td>
								<input id="rngEfva1" name="rngEfva1" type="text" class="input" style="width:34%"/>
							</td>
						</tr>
						<tr>
							<th scope="row"><s:message code="COUPLER"/></th> <!--연결자-->

							<td colspan="3">
								<select name="rngCnc"  id="rngCnc" >
									<option value=""><s:message code="CHC" /></option><!--선택-->

									<c:forEach var="code" items="${codeMap.rngCnc}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
<!-- 									<option value="OR">AND</option> -->
<!-- 									<option value="AND">OR</option> -->
								</select>
							</td>
						</tr>
						<tr>
							<th scope="row"><s:message code="RNG.OPERATOR"/></th><!--범위연산자-->
							<td>
								<select id="rngOpr2"  name="rngOpr2" class="" style="width:;">
									<option value=""><s:message code="CHC" /></option><!--선택-->

									<c:forEach var="code" items="${codeMap.rngOprCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
								    <%--
									<option value=">="><</option>
									<option value="<=">></option>
									<option value=">"><=</option>
									<option value="<">>=</option> 
									--%>
								</select>
							</td>
							<th scope="row"><s:message code="RNG.VLD.VAL"/></th><!--범위유효값-->
							<td>
								<input id="rngEfva2" name="rngEfva2" type="text" class="input" style="width:34%"/>
			           		</td>
			           </tr>
			       	   <tr>                               
			               <th scope="row"><label for="adtCndSql"><s:message code="ADDT.COND"/></label></th><!--추가조건-->
			               <td colspan="3">
			                   <input type="text"  class="wd98p" name="adtCndSql" id="adtCndSql" />
			               </td>
			           </tr>
					<tr>
		               <th scope="row" class="th_require"><label for="dqiSearch"><s:message code="QLTY.INDC"/></label></th><!--품질지표-->
		               <td colspan="3">
		                   <input type="text"  style="width: 70%;" name="dqiLnm" id="dqiLnm" readonly />
		                   <input type="hidden" name="dqiId" id="dqiId" />
		                   &nbsp;&nbsp;&nbsp;
		                   <input style="background-color: #2682ca;" type="button" id="btnApply" name="btnApply" class="btn_save" value="<s:message code='INQ' />" />
		               </td>
		           </tr>			           
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			</form>
	</div>
	
<!-- </body> -->
<!-- </html> -->
