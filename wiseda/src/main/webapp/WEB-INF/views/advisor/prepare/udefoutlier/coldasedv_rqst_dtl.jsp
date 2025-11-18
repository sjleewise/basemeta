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

	initColDaseDvGrid();
	
	//조회 
	$("#btnColDaseDvSrch").click(function(){
		
		doColDaseDvAction("TblSearch");
	});

	//저장
	$("#btnColDaseDvSave").click(function(){  
		
		doColDaseDvAction("Save");
	});

	var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

	var obj = $("#frmColDaseDv #srcAnaDaseId");
	
	var creCompId   = $("#frmColDaseDv #creCompId").val();

	getAnaDaseId(obj, udfOtlDtcId, creCompId);

});


function initColDaseDvGrid()
{
	
	with(grid_coldasedv){  
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		//var headtext  = "<s:message code='META.HEADER.STNDDMN.RQST.IFM.1'/>";
		
		var headtext = "<s:message code='BDQ.HEADER.COLDASEDV.RQST.DTL1'/>";
		//No.|상태|선택|데이터셋명|컬럼명|컬럼한글명|데이터타입|ANA_VAR_ID
			
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	  Width:50,  SaveName:"ibsSeq",	   Align:"Center", Edit:0, Hidden:1},
					{Type:"Status",   Width:40,  SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:90,  SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0}, 
					{Type:"Text",     Width:100, SaveName:"anaDaseNm", Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",     Width:150, SaveName:"colPnm",	   Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:150, SaveName:"colLnm",	   Align:"Left", Edit:0, Hidden:0},		
					{Type:"Text",     Width:90,  SaveName:"dataType",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:90,  SaveName:"anaVarId",  Align:"Left", Edit:0, Hidden:1}								
				];
					
		InitColumns(cols);
		
		
 		//FitColWidth();   
	    //SetSheetHeight(200);
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_coldasedv);   

	//===========================   
}

function doColDaseDvAction(action) {

	switch(action){

		case "TblSearch":
		
			var url = "<c:url value='/advisor/prepare/udefoutlier/coldasedv/getUodcColDaseDvColList.do' />";
			
			var param = $("#frmUod, #frmColDaseDv").serialize(); 

			//alert(param);

			grid_coldasedv.DoSearch(url, param);			

			break;
			
		case "Save":

			if(!valSaveCheckColDaseDv()) return;

			ibsSaveJson = grid_coldasedv.GetSaveJson(1);  
			
			if(!checkDelIBS (grid_coldasedv, "체크박스에 체크하세요.")) return; 
			
			var url = "<c:url value="/advisor/prepare/udefoutlier/coldasedv/regColDaseDvlist.do"/>"; 

			//alert(myDiagram.model.toJson());

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmColDaseDv").serialize(); 

			//alert(param);
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;	
	}
	
}

function valSaveCheckColDaseDv(){  

	if($("#frmUod #udfOtlDtcNm").val() == "") {
		showMsgBox("ERR","사용자정의이상값탐지명을 입력하세요.");
		return false;
	}
	
	if($("#frmColDaseDv #anaDaseNm").val() == "") { 
		showMsgBox("ERR","분석데이터셋명을 입력하세요.");
		return false;
	}

	return true;
}


function compColDaseDv(node) { 

	//alert(JSON.stringify(node.data)); 

	//alert(myDiagram.model.toJson()); 
	
	var compKeyId  = node.data.key;
	var compHashNm = node.data.text; 


	$("#frmColDaseDv #creCompId").val(compKeyId);
	$("#frmColDaseDv #creCompNm").val(compHashNm);
	
	//콤포넌트별 div 토글
	toggelDivComp("divCompColDaseDv");

	var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();
	var creCompId   = $("#frmColDaseDv #creCompId").val();

	//alert(udfOtlDtcId);
	
	var obj = $("#frmColDaseDv #srcAnaDaseId");

	getAnaDaseId(obj, udfOtlDtcId, creCompId);


	getAjaxColDaseDv(udfOtlDtcId, creCompId);
}

function getAjaxColDaseDv(udfOtlDtcId, creCompId){ 

	var url = "<c:url value='/advisor/prepare/udefoutlier/coldasedv/getUodcColDaseDvDetail.do' />";

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
			
			// alert(JSON.stringify(data)); 
		    
			$("#frmColDaseDv #srcAnaDaseId").val(data.srcAnaDaseId); 
			$("#frmColDaseDv #anaDaseNm").val(data.anaDaseNm); 
			
			doColDaseDvAction("TblSearch");   
			
		},
		error: function (jqXHR, textStatus, errorThrown) {

			resetForm($("#frmColDaseDv"));

			initColDaseDvGrid();

			grid_coldasedv.RemoveAll();		

			$("#udfOtlDtcId").val(udfOtlDtcId);
			$("#frmColDaseDv #creCompId").val(creCompId);
		}
	});
	
}


</script>
	
	<form name="frmColDaseDv" id="frmColDaseDv" method="post" onsubmit="return false;">
		<input type="hidden" id="creCompId"  name="creCompId"  />
 		<input type="hidden" id="creCompNm"  name="creCompNm"  /> 
 		<input type="hidden" id="mdlJsonInf" name="mdlJsonInf" />
 		<input type="hidden" id="impDataDcd" name="impDataDcd" />
	
  
	<div class="stit">데이터셋선택</div><!-- 데이터셋선택  -->  
	 			
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
		           <th scope="row"  class="th_require">데이터셋</label></th>
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
	 	  
	 <!-- 버튼영역 -->
	 <div class="divLstBtn" >  
        <div class="bt03"> 
		    
		</div>
		<div class="bt02">  
			<button class="btn_search"  id="btnColDaseDvSrch"  name="btnColDaseDvSrch"><s:message code="INQ"/></button> <!-- 조회 -->   
			<button class="btn_search"  id="btnColDaseDvSave"  name="btnColDaseDvSave"><s:message code="STRG" /></button> <!-- 저장 -->                      
    	</div>
     </div>
     <!-- 버튼영역 end -->	
     
     <div style="clear:both; height:10px;"><span></span></div> 	  
       
     <!-- 그리드 입력 입력 -->
     <div class="grid_01" id="grid_coldasedv">  
          <script type="text/javascript">createIBSheet("grid_coldasedv", "100%", "300px");</script>            
     </div>
     
     <div style="clear:both; height:10px;"><span></span></div>
     
     <fieldset>
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
  	
 
	