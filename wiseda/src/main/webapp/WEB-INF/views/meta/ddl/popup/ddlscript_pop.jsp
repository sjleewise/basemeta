<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title>DDL SCRIPT</title>

<script type="text/javascript">

var popRqst = "${search.popRqst}";

$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
// 	$("#tabs").tabs();
	

	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
//	     	alert(1);
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
				
    $("#tblChgTypCd").bind("change",function(){ 
		
		var tblChgTypCd = $(this).val();
		
		loadDetail(tblChgTypCd);
	});	
    
	    
});

$(window).on('load',function() {
	
	
	loadDetail("ALT");
	
// 	if("${search.scrnDcd}" != "WAQTBL")
	if("${search.objDcd}" != "TBL"){
		$("#trScrtTyp").hide();
	}
	
});

function loadDetail(){
	
	var scriptJson = opener.grid_sheet.GetSaveJson(0);
	//var objId   = "&objId=";
	var objId   = "";
	var rqstSno = "";
	var rqstDtlSno = "";
	var objDcd = "";  //오브젝트구분
	var rqstNoLst ="";
	var tblTypCd = "";
	var regTypCd = "";
	
	for (var i=0; i<scriptJson.data.length; i++) {
// 		objId += scriptJson.data[i].ddlTblId;
		objId += scriptJson.data[i].objId;
		if(i<scriptJson.data.length-1) {
			objId += "|";
			
		}
		
		rqstNo = scriptJson.data[i].rqstNo;
		
		rqstSno    += scriptJson.data[i].rqstSno;
		rqstDtlSno += scriptJson.data[i].rqstDtlSno;
		tblTypCd += scriptJson.data[i].tblChgTypCd;
		regTypCd += scriptJson.data[i].regTypCd;
		
		if(i<scriptJson.data.length-1) {
			rqstSno    += "|";
			rqstDtlSno += "|";
			tblTypCd += "|";
			regTypCd += "|";
		}
		if("${search.scrnDcd}" != "WAQTBL" && "${search.scrnDcd}" != "WAQIDX"&& "${search.scrnDcd}" != "WAQSEQ" && "${search.scrnDcd}" != "WAQPART"){
			rqstNoLst += scriptJson.data[i].rqstNo;
			objDcd += scriptJson.data[i].objDcd;
			if(i<scriptJson.data.length-1) {
				rqstNoLst += "|";
				objDcd    += "|";
			} 	
		}
	}
	
	var param = "";  
		
	if("${search.scrnDcd}" == "WAQTBL" || "${search.scrnDcd}" == "WAQIDX" || "${search.scrnDcd}" == "WAQSEQ" || "${search.scrnDcd}" == "WAQPART"){
		var objParam = new Object();
		
		objParam.scrnDcd    = "${search.scrnDcd}";
		objParam.rqstNo     = rqstNo; 
		objParam.rqstSno    = rqstSno;
		objParam.rqstDtlSno = rqstDtlSno;
		
		// 테이블일경우에만 테이블변경유형 값 가져온다.
		if("${search.scrnDcd}" == "WAQTBL"){
			if($("#tblChgTypCd").val() != '')
				objParam.creDrpDcd = $("#tblChgTypCd").val();
			else
				objParam.creDrpDcd  = tblTypCd;
		}else{
			objParam.creDrpDcd = '';
		}
		
		param = objParam;
		/*
		param += "&scrnDcd="    + "${search.scrnDcd}";  
		param += "&rqstNo="     + rqstNo;  
		param += "&rqstSno="    + rqstSno;
		param += "&rqstDtlSno=" + rqstDtlSno;
		param += "&creDrpDcd="  + $("#tblChgTypCd").val();
		*/	
		
		$('div#ddlScriptDtl').load('<c:url value="/meta/ddl/ajaxgrid/ddlwaqscript_pop_dtl.do"/>', param, function(){  });
		
	}else if("${search.scrnDcd}" == "TBL" || "${search.scrnDcd}" == "IDX"){
		var objParam = new Object();
		
		objParam.scrnDcd    = "${search.scrnDcd}";
		objParam.rqstNo     = rqstNo; 
		objParam.rqstSno    = rqstSno;
		objParam.rqstDtlSno = rqstDtlSno;
// 		objParam.creDrpDcd  = $("#tblTypCd").val();

		if($("#tblChgTypCd").val() != '')
			objParam.creDrpDcd = $("#tblChgTypCd").val();
		else
			objParam.creDrpDcd  = tblTypCd;
		
		param = objParam;
		
		$('div#ddlScriptDtl').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_pop_dtl.do"/>', param, function(){  });
	} else{
		//모델GAP분석에서 DDL컬럼 더블클릭시 DDL문 조회
		/*
		if("${search.ddlTblId}" != "") { 
			
			var param = "objId=${search.ddlTblId}";	
			$('div#ddlScriptDtl').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_pop_dtl.do"/>', param, function(){});
		} else {	
		
			$("#objId").val(objId);
												
			$('div#ddlScriptDtl').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_pop_dtl.do"/>', $("#frmInput").serializeObject(), function(){}); 						
			
		}
		*/
		var objParam = new Object();
		
		objParam.scrnDcd    = "${search.scrnDcd}";
		objParam.rqstSno    = rqstSno;
		objParam.rqstDtlSno = rqstDtlSno;
// 		objParam.creDrpDcd  = $("#tblChgTypCd").val();
		objParam.objId      = objId;
		objParam.objDcd     = objDcd;
		objParam.regTypCd 	= regTypCd;
		
		if($("#tblChgTypCd").val() != '')
			objParam.creDrpDcd = $("#tblChgTypCd").val();
		else
			objParam.creDrpDcd  = tblTypCd;
		
		objParam.rqstNo     = rqstNoLst; 
		param = objParam;
		$('div#ddlScriptDtl').load('<c:url value="/meta/ddl/ajaxgrid/ddlscript_pop_dtl.do"/>', param, function(){});
	}
	
}


</script>
    
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">DDL Script</div>
    
    <!-- 팝업 타이틀 끝 -->
    
    <div class="tb_basic">
     <table border="0" cellspacing="0" cellpadding="0"  summary="">
      <caption>조회조건</caption>
      <colgroup>
          <col style="width:12%;">
          <col style="width:38%;">
          <col style="width:12%;">
          <col style="width:38%;">
      </colgroup>

      <tbody>                   
      		<tr id="trScrtTyp">
      			<th scope="row"><s:message code="SCRT.TYP" /></th> <!-- 스크립트유형 -->
      			<td>
      				<select id="tblChgTypCd" name="tblChgTypCd">
      					<option value=""></option>
      					<option value="CRE">CREATE</option>
      					<option value="DRP">DROP & CREATE</option>
      					<option value="ALT">ALTER</option>
      					<option value="BAK">BACKUP & INSERT & DROP</option>
      				</select>
      			</td>
      		</tr>			         			
     </tbody>
    </table>
   </div> 
    
    <form id="frmInput" name="frmInput">
    	<input type="hidden" id="objId" name="objId" />
    </form>
    <div id="ddlScriptDtl" name="ddlScriptDtl"></div>

   
</div>
</body>
</html>