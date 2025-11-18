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
		
		$("#btnUsrDefSave").click(function() {
			doUsrDefAction("Save");
		});
		
		/* $('#userDefScrtConts').keyup(function(e){
			if(e.keyCode == 13) {
				var txt = $('#userDefScrtConts').val();
				$('#userDefScrtConts').val(txt + '\r\n');
			}
		}); */
	});
	
	function compUsrDef(node) { 
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 
	
	
		$("#frmUsrDef #creCompId").val(compKeyId);
		$("#frmUsrDef #creCompNm").val(compHashNm);
		
		//콤포넌트별 div 토글
		toggelDivComp("divCompUsrDef");
	
		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmUsrDef #creCompId").val();

		getAjaxUsrDef(udfOtlDtcId, creCompId);
	}
	
	function getAjaxUsrDef(udfOtlDtcId, creCompId){
		$("#frmUsrDef #userDefScrtConts").val("");
		
		var url = "<c:url value='/advisor/prepare/udefoutlier/usrdef/getDataUsrDef.do' />";
	
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
				$("#frmUsrDef #userDefScrtConts").val(data.userDefScrtConts);
			},
			error: function (jqXHR, textStatus, errorThrown) {
				
			}
		});
	}
	
	function doUsrDefAction(action) {
		switch(action) {
		case "Save":
			//사용자 정의 스크립트 저장
			var url = "<c:url value='/advisor/prepare/udefoutlier/usrdef/regUodcUsrDef.do'/>"; 
			
			//str = str.replace(/(?:\r\n|\r|\n)/g, '<br />');

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmUsrDef").serialize();
			
			$.ajax({
				url: url,
				async: false,
				type: "POST",
				data: replacerXssParam(param),
				dataType: 'json',
				success: function (data) {
					alert(data);
				},
				error: function (jqXHR, textStatus, errorThrown) {
					
				}
			});
			
			break;
		}
	}
</script>

<form name="frmUsrDef" id="frmUsrDef" method="post" onsubmit="return false;">
	<input type="hidden" id="creCompId"  name="creCompId"  />
	<input type="hidden" id="creCompNm"  name="creCompNm"  /> 
	<input type="hidden" id="mdlJsonInf" name="mdlJsonInf" />
	
	 
	<div class="stit">사용자 정의 스크립트</div><!-- 사용자 정의 스크립트  -->  
				
	<fieldset>
		<legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
		<div class="tb_basic" >
		    <textarea id="userDefScrtConts" name="userDefScrtConts" style="width:99%;" rows="20" ></textarea>
		</div>
	</fieldset>
	
	
	
	<div style="clear:both; height:20px;"><span></span></div>
	
	<!-- 버튼영역 -->
	<div class="divLstBtn" >
		<div class="bt02">
			<button class="btn_search"  id="btnUsrDefSave"  name="btnUsrDefSave"><s:message code="STRG"/></button> <!-- 저장 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
</form>