<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<script type="text/javascript">

$(document).ready(function(){

	
	//저장
	$("#btnDataFilterSave").click(function(){  
		
		doDataFilterAction("Save");
	});

	//조건설정
	$("#btnCndSet").click(function(){

		if($("#frmDataFilter #anaDaseNm").val() == ""){

			showMsgBox("ERR","분석데이터셋명을 입력하세요.");
			return;
		}

		var url = "<c:url value='/advisor/prepare/udefoutlier/popup/datafilter_pop.do' />";

		var param = "?anaDaseId=" + $("#frmDataFilter #srcAnaDaseId").val();  

		//alert(param);

		OpenWindow(url + param,"cndSet","500","400","true"); 
		
		//openLayerPop(url, 500,  400, param) ;
	});

	//==========데이터셋 콤보 조회===============
	var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

	var obj = $("#frmDataFilter #srcAnaDaseId");
	
	var creCompId   = $("#frmDataFilter #creCompId").val();
	
	getAnaDaseId(obj, udfOtlDtcId, creCompId);
	//=====================================

	
});


function doDataFilterAction(action) {

	switch(action){
	
		case "Save":

			if(!valSaveCheckDataFilter()) return;

			
			var url = "<c:url value="/advisor/prepare/udefoutlier/datafilter/regDaseCndDv.do"/>"; 

			//alert(myDiagram.model.toJson());

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmDataFilter").serialize(); 

			//alert(param);
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;	
	}
	
}

function valSaveCheckDataFilter(){  

	if($("#frmUod #udfOtlDtcNm").val() == "") {
		showMsgBox("ERR","사용자정의이상값탐지명을 입력하세요.");
		return false;
	}

	if($("#frmDataFilter #anaDaseNm").val() == "") { 
		showMsgBox("ERR","분석데이터셋명을 입력하세요.");
		return false;
	}
	
	
	return true;
}


function compDataFilter(node) { 

	//alert(JSON.stringify(node.data)); 

	//alert(myDiagram.model.toJson()); 
	
	var compKeyId  = node.data.key;
	var compHashNm = node.data.text; 


	$("#frmDataFilter #creCompId").val(compKeyId);
	$("#frmDataFilter #creCompNm").val(compHashNm);

	//콤포넌트별 div 토글
	toggelDivComp("divCompDataFilter"); 

	var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();
	var creCompId   = $("#frmDataFilter #creCompId").val();

	//alert(udfOtlDtcId);
	
	var obj = $("#frmDataFilter #srcAnaDaseId");

	getAnaDaseId(obj, udfOtlDtcId, creCompId);

	getAjaxDataFilter(udfOtlDtcId, creCompId);
}

function getAjaxDataFilter(udfOtlDtcId, creCompId){ 

	var url = "<c:url value='/advisor/prepare/udefoutlier/datafilter/getUodcDaseCndDvDetail.do' />";

	var param = new Object();

	param.udfOtlDtcId = udfOtlDtcId;
	param.creCompId   = creCompId;  

	//alert(param);

	$.ajax({
		url: url,
		async: false,
		type: "POST",
		data: replacerXssParam(param),
		dataType: 'json',
		success: function (data) {
			
			//alert(JSON.stringify(data));
			
			//alert(data.srcAnaDaseId);
			
			$("#frmDataFilter #srcAnaDaseId").val(data.srcAnaDaseId);
			$("#frmDataFilter #cndConts").val(data.cndConts);  
			$("#frmDataFilter #anaDaseNm").val(data.anaDaseNm); 
			
						
		},
		error: function (jqXHR, textStatus, errorThrown) {

			resetForm($("#frmDataFilter")); 

			$("#frmDataFilter #creCompId").val(creCompId);				
		}
	});
	
}




</script>
	
 <form name="frmDataFilter" id="frmDataFilter" method="post" onsubmit="return false;">
	<input type="hidden" id="creCompId"   name="creCompId"  />
	<input type="hidden" id="creCompNm"   name="creCompNm"  /> 
	<input type="hidden" id="mdlJsonInf"  name="mdlJsonInf" />
	
  
	<div class="stit">데이터셋선택</div><!-- 데이터셋선택  -->  	
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
		           <th scope="row"  class="th_require">데이터셋</label></th>
		           <td>
		               <select  id="srcAnaDaseId" class="" name="srcAnaDaseId" >  		             
			           </select>				            
		           </td> 			          	          
		       </tr>                         			      
		   </tbody>
	   </table>   	  
  	</div>
  	
  	<div style="clear:both; height:10px;"><span></span></div>
  	
  	<!-- 버튼영역 -->
    <div class="divLstBtn" >   
        <div class="bt03"> 
  		    
  		</div>
  		<div class="bt02">   			
  			<button class="btn_search"  id="btnDataFilterSave"  name="btnDataFilterSave"><s:message code="STRG" /></button> <!-- 저장 -->                      
      	</div>
    </div>
    <!-- 버튼영역 end -->	
  	  	
  	<div style="clear:both; height:10px;"><span></span></div>
  	
  	<div class="stit">선택한조건</div><!-- 선택한조건  -->  	
  	
  	<div style="clear:both; height:10px;"><span></span></div>
  	  	    
  	
    <legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
	<div class="tb_basic" >
	    
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
		   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
		   <colgroup>
			   <col style="width:15%;" />
			   <col style="width:*;" />  					   		 
		   </colgroup>
		   <tbody>   
		   		<tr>                               
		           <th scope="row"  class="th_require">조건</label></th>
		           <td>
		               <input type="text" id="cndConts" name="cndConts" style="width:98%;" readOnly />
		           </td> 	 			           			            			          	         
		       </tr>                         			      
		   </tbody>
	   </table>   	  
  	</div>  	
  	
  	<div style="clear:both; height:20px;"><span></span></div> 	  
  	
  	<!-- 버튼영역 -->
    <div class="divLstBtn" >   
        <div class="bt03"> 
  		    
  		</div>
  		<div class="bt02">   			
  			<button class="btn_search"  id="btnCndSet"  name="btnCndSet">조건설정</button> <!-- 조건설정 -->                      
      	</div>
    </div>
    <!-- 버튼영역 end -->	
  	
    <div style="clear:both; height:20px;"><span></span></div> 	    	          
               
    <fieldset>
    <div class="stit">데이터셋명</div><!-- 데이터셋명  -->  	
  
    <legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
	<div class="tb_basic" >
	    
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> 
		   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
		   <colgroup>
			   <col style="width:25%;" />
			   <col style="width:*;" />  			  
		   </colgroup>
		   <tbody>    			   		      
		       <tr>                               
		           <th scope="row"  class="th_require" ><label for="anaDaseNm">분석데이터셋명</label></th> <!-- 분석데이터셋명 -->
		           <td>
		               <input type="text" name="anaDaseNm" id="anaDaseNm"  class="wd99p" />
		           </td>
		       </tr> 
		   </tbody>
	   </table>   
	     		 
  	</div>
    
    </fieldset>
  
  </form>
	