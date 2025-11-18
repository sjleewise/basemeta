<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<script type="text/javascript">
	$(document).ready(function() {
		var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

		var obj = $("#frmSaveRes #srcAnaDaseId");
		
		getSelectBox(obj, udfOtlDtcId);
		
		$("#btnSaveRes").click(function() {
						
			doSaveResAction("Save");
		}); 

		$('input:radio[name=svResTypCd]:input[value="01"]').prop("checked",true);
	});
	
	function compSaveResult(node) { 
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 
	
	
		$("#frmSaveRes #creCompId").val(compKeyId);
		$("#frmSaveRes #creCompNm").val(compHashNm);
		
		//콤포넌트별 div 토글
		toggelDivComp("divCompSaveResult");
	
		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmSaveRes #creCompId").val();
		
		var obj = $("#frmSaveRes #srcAnaDaseId");

		getSelectBox(obj, udfOtlDtcId);
		
		getAjaxSaveRes(udfOtlDtcId, creCompId);
	}
	
	function getAjaxSaveRes(udfOtlDtcId, creCompId){
		var url = "<c:url value='/advisor/prepare/udefoutlier/saveres/getUodcIsfrDetail.do' />";
	
		var param = new Object();
	
		param.udfOtlDtcId = udfOtlDtcId;
		param.creCompId   = creCompId;
	
		$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: replacerXssParam(param),
			dataType: 'json',
			success: function (data) {
				
				$("#frmSaveRes #srcAnaDaseId").val(data.srcAnaDaseId);	
		
				$('input:radio[name=svResTypCd]:input[value='+ data.svResTypCd +']').prop("checked",true);
			},
			error: function (jqXHR, textStatus, errorThrown) {

				resetForm($("#frmSaveRes"));

				$('input:radio[name=svResTypCd]:input[value="01"]').prop("checked",true);

				$("#frmSaveRes #creCompId").val(creCompId);
			}
		});
	}
	
	function doSaveResAction(action) {
		switch(action) {
			case "Save":
				//데이터베이스에 저장 및 파이썬 코드로 변환, 저장
				var url = "<c:url value='/advisor/prepare/udefoutlier/saveres/regUodcSvRes.do'/>"; 
	
				$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

				var param = $("#frmUod, #frmSaveRes").serialize();
					
				IBSpostJson(url, param, ibscallback); 
				
			break;
		}
	}
	
	function getSelectBox(obj, udfOtlDtcId) {
		var url = "<c:url value='/advisor/prepare/udefoutlier/getAnaDaseIdSaveres.do' />";

	  	var param = new Object();

	  	param.udfOtlDtcId = udfOtlDtcId;
	  	
	  	//alert(param);

	  	$.ajax({
	  		url: url,
	  		async: false,
	  		type: "POST",
	  		data: replacerXssParam(param),
	  		dataType: 'json',
	  		success: function (res) {
	  			obj.html("");

	  			$.each(res.DATA, function(i,map){ 

	  				addOption(obj, map.anaDaseId, map.anaDaseNm); 
	  			});
	  					
	  			
	  		},
	  		error: function (jqXHR, textStatus, errorThrown) {
	  						
	  		}
	  	});
	}
</script>

<form name="frmSaveRes" id="frmSaveRes" method="post" onsubmit="return false;">
	<input type="hidden" id="creCompId"  name="creCompId"  />
	<input type="hidden" id="creCompNm"  name="creCompNm"  /> 
	<input type="hidden" id="mdlJsonInf" name="mdlJsonInf" />
	
	 
	<div class="stit">결과데이터 선택</div><!-- 결과데이터 선택  -->  
				
	<fieldset>
		<legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
		<div class="tb_basic" >
		    
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
				<caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
				<colgroup>
					<col style="width:25%;" />
					<col style="width:*;" />  			  
				</colgroup>
				<tbody>   
					<tr>                               
						<th scope="row"  class="th_require">결과 데이터</label></th>
						<td>
							<select id="srcAnaDaseId" class="" name="srcAnaDaseId">
							</select> 				            
						</td> 			          	          
					</tr>                         			      
				</tbody>
			</table>
		</div>
	</fieldset>
	
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="stit">저장 형태</div><!-- 저장 형태  -->
	<div style="text-align:center; width:90%; margin:0 auto;">
		<table style="text-align:center;" width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>">
			<colgroup>
				<col style="width:33%;" />
				<col style="width:33%;" />
				<col style="width:33%;" />
			</colgroup>
			
			<tr>
				<td style="text-align:center;">
					<input type="radio" id="rdoSvResCSV" name="svResTypCd" value="01" checked> .CSV
				</td>
				<td style="text-align:center;">
					<input type="radio" id="rdoSvResXls" name="svResTypCd" value="02"> .xls
				</td>
				<td style="text-align:center;">
					<input type="radio" id="rdoSvResDbase" name="svResTypCd" value="03"> 데이터베이스
				</td>
			</tr>
		</table>
	</div>
	
	<div style="clear:both; height:20px;"><span></span></div>
	
	<!-- 버튼영역 -->
	<div class="divLstBtn" >
		<div class="bt02">
			<button class="btn_search"  id="btnSaveRes"  name="btnSaveRes"><s:message code="STRG"/></button> <!-- 저장 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
</form>