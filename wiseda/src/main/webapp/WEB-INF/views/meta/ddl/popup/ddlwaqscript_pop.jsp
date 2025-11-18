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
	//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
	
    $('#tblChgTypCd').change(function(){
    	loadDetail();
    });
	    
});

$(window).on('load',function() {
	loadDetail();
	
});

function loadDetail(){
	var scriptJson = null;
	var bizDtlCd = null;
	var param = null;
	var url = null;
	//iframe 형태의 팝업일 경우
   	if ("${search.popType}" == "I") {
   		scriptJson = parent.grid_sheet.GetSaveJson({StdCol:"ddl"});
   		bizDtlCd = parent.$("#mstFrm #bizDtlCd").val();
   	} else {
   		scriptJson = opener.grid_sheet.GetSaveJson({StdCol:"ddl"});
   		bizDtlCd = opener.$("#mstFrm #bizDtlCd").val();
   	}
// 	alert(bizDtlCd );
   	param = "&chgTypCd="+$('#tblChgTypCd').val();
   	
   	if(bizDtlCd == "DDLTBL"){
   		url = '<c:url value="/meta/ddl/ajaxgrid/getddltblwaqscriptlist.do"/>';
   	}else if(bizDtlCd == "DDLIDX"){
   		url = '<c:url value="/meta/ddl/ajaxgrid/getddlidxwaqscriptlist.do"/>';
   	}else if(bizDtlCd == "DDLPART"){
   		url = '<c:url value="/meta/ddl/ajaxgrid/getddlpartwaqscriptlist.do"/>';
   	}else if(bizDtlCd == "DDLSEQ"){
   		url = '<c:url value="/meta/ddl/ajaxgrid/getddlseqwaqscriptlist.do"/>';
   	}else if(bizDtlCd == "ETC"){
   		url = '<c:url value="/meta/ddl/ajaxgrid/getddletcwaqscriptlist.do"/>';
   	}

	IBSpostJson2(url, scriptJson, param, ibscallbackScrt); 
}

function ibscallbackScrt(res){

	$("#divMsgPopup").remove();
	
	$("#ddl_script_info").val(res.resultVO.scrtInfo);
}


//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" :
			//저장완료시 요청서 번호 셋팅...
    		$("#ddl_script_info").val(res.resultVO.scrtInfo);
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}

</script>
    
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">DDL Script</div>
    <div class="pop_tit_close"><a>창닫기</a></div>
    <!-- 팝업 타이틀 끝 -->
    <div class = "pop_content" >
    <span class="tb_comment">스크립트 유형</span>     
    <select id="tblChgTypCd" class="wd200" name="tblChgTypCd" value = "">
	    <option value=""></option>
	    <option value="CRO">CREATE ONLY</option>
	    <option value="DRP">DROP&CREATE</option>
	    <option value="DRO">DROP ONLY</option>
	    <option value="ALT">ALTER</option>
	    <option value="COY">COMMENT ONLY</option>
	    <option value="GNS">GRANT&SYNONYM</option>
	    <!--   <option value="RNT">RENAME</option> -->
    </select>  
    </div>
    <div id="ddlScriptDtl">
    <!-- 입력폼 시작 -->
   	<form id="frmInputDdlScript" name="frmInputDdlScript" action ="" method="">
   	
   	<div style="clear:both; height:0px;"><span></span></div>
   	<div class="tb_basic">
   	
        
   
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        조회조건
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>

      <tbody>                   			       
			<tr> 
            	<td colspan="4">
            		<textarea id="ddl_script_info" rows="30" style="font-size:12px;font-family:굴림체;" class="wd98p"></textarea>
            	</td>  
           </tr>         
              
     </tbody>
    </table>
    </div>
    
    </form>
    </div>

   
</div>
</body>
</html>