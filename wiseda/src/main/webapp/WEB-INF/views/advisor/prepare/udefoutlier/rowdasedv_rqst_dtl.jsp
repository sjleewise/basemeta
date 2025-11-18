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

	//조회 
	$("#btnRowDaseDvSrch").click(function(){
		
		doRowDaseDvAction("TblSearch");
	});

	//저장
	$("#btnRowDaseDvSave").click(function(){  
		
		doRowDaseDvAction("Save");
	});

	
	$("input[name='rowSltTypCd']").change(function(){

		//alert($(this).val());  

		var vDcd = $(this).val();

		//$("#rowSltTypCd").val(vDcd);

		if(vDcd == "S") {
			$("#divRdmSel").show();
			$("#divDrtSel").hide();	
				
		}else{
			$("#divRdmSel").hide();
			$("#divDrtSel").show();
		} 		
	});
	

	$("input[name='rdmSmplTypCd']").change(function(){

		var vDcd = $(this).val();

		if(vDcd == "C"){
			$("#rdoRdmSmplCnt").prop("checked",true);
		}else{
			$("#rdoRdmSmplRate").prop("checked",true);
		}
	});
	
	$("#rdmSmplCnt").keyup(function(e) {
		$("#rdmSmplCnt").val(numberCheck($("#rdmSmplCnt").val(), e.key));
	});
	
	$("#rdmSmplRate").keyup(function(e) {
		if($("#rdmSmplRate").val() == 1 || $("#rdmSmplRate").val() == '1.') {
			$("#rdmSmplRate").val(1);
			return;
		}
		$("#rdmSmplRate").val(floatCheck($("#rdmSmplRate").val(), e.key));
	});
	
	$("#sctStrVal").keyup(function(e) {
		$("#sctStrVal").val(numberCheck($("#sctStrVal").val(), e.key));
	});
	
	$("#sctEndVal").keyup(function(e) {
		$("#sctEndVal").val(numberCheck($("#sctEndVal").val(), e.key));
	});

	readyInitVal();

});

function readyInitVal(){

	//==========데이터셋 콤보 조회===============
	var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

	var obj = $("#frmRowDaseDv #srcAnaDaseId");
	
	var creCompId   = $("#frmRowDaseDv #creCompId").val();
	//alert(creCompId + "  readyInitVal");
	
	getAnaDaseId(obj, udfOtlDtcId, creCompId);
	//=====================================

	$("#rdoRdmSel").prop("checked", true); 
	

	
	

	$("#rdoRdmSmplCnt").prop("checked", true); 	
}


function doRowDaseDvAction(action) {

	switch(action){

		case "TblSearch":

			var url = "<c:url value='/advisor/prepare/udefoutlier/daserowdv/getUodcRowDaseDvColList.do' />";
			
			var param = $("#frmUod, #frmRowDaseDv").serialize(); 

			//alert(param);

			grid_daserowdv.DoSearch(url, param);			

			break;
			
		case "Save":

			if(!valSaveCheckRowDaseDv()) return;

			
			var url = "<c:url value="/advisor/prepare/udefoutlier/daserowdv/regRowDaseDv.do"/>"; 

			//alert(myDiagram.model.toJson());

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmRowDaseDv").serialize(); 

			//alert(param);
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;	
	}
	
}

function valSaveCheckRowDaseDv(){  

	if($("#frmUod #udfOtlDtcNm").val() == "") {
		showMsgBox("ERR","사용자정의이상값탐지명을 입력하세요.");
		return false;
	}

	if($("#frmRowDaseDv #anaDaseNm").val() == "") { 
		showMsgBox("ERR","분석데이터셋명을 입력하세요.");
		return false;
	}
	
	
	var chkVal = $(":input:radio[name=rowSltTypCd]:checked").val(); 

	if(chkVal == "S") {

		var vRdoVal = $(":input:radio[name=rdmSmplTypCd]:checked").val();

		//alert(vRdoVal);

		if(vRdoVal == "C"){

			if($("#frmRowDaseDv #rdmSmplCnt").val() == "") { 
				showMsgBox("ERR","갯수을 입력하세요.");
				return false;
			}
		}else{
			if($("#frmRowDaseDv #rdmSmplRate").val() == "") { 
				showMsgBox("ERR","갯수을 입력하세요.");
				return false;
			}
		}
			
	}else{

		if($("#frmRowDaseDv #sctStrVal").val() == "") { 
			showMsgBox("ERR","시작지점값을 입력하세요.");
			return false;
		}

		if($("#frmRowDaseDv #sctEndVal").val() == "") { 
			showMsgBox("ERR","끝지점값을 입력하세요.");
			return false;
		}

		/*
		if($("#frmRowDaseDv #drtDsgVal").val() == "") { 
			showMsgBox("ERR","직접지정값을 입력하세요.");
			return false;
		}
		*/
	}
	
	
	return true;
}


function compRowDaseDv(node) { 

	//alert(JSON.stringify(node.data)); 

	//alert(myDiagram.model.toJson()); 
	
	var compKeyId  = node.data.key;
	var compHashNm = node.data.text; 


	$("#frmRowDaseDv #creCompId").val(compKeyId);
	$("#frmRowDaseDv #creCompNm").val(compHashNm);

	//콤포넌트별 div 토글
	toggelDivComp("divCompRowDaseDv"); 

	var udfOtlDtcId = $("#udfOtlDtcId").val();
	var creCompId   = $("#frmRowDaseDv #creCompId").val();
	//alert(creCompId + "  compRowDaseDv");
	//alert(udfOtlDtcId);
	
	
	var obj = $("#frmRowDaseDv #srcAnaDaseId");

	getAnaDaseId(obj, udfOtlDtcId, creCompId);
			

	getAjaxRowDaseDv(udfOtlDtcId, creCompId);
}

function getAjaxRowDaseDv(udfOtlDtcId, creCompId){ 

	var url = "<c:url value='/advisor/prepare/udefoutlier/daserowdv/getUodcDaseRowDvDetail.do' />";

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
			
			var rowSltTypCd = data.rowSltTypCd;

			if(rowSltTypCd == "S"){
				$("#rdoRdmSel").prop("checked", true);	
				$("#divRdmSel").show();
				$("#divDrtSel").hide();
			}else{ 				
				$("#rdoDrtSel").prop("checked", true);  
				$("#divRdmSel").hide();
				$("#divDrtSel").show();
			} 

			//$("input[name='rowSltTypCd']").change();
		    			
			$("#frmRowDaseDv #srcAnaDaseId").val(data.srcAnaDaseId); 
			$("#frmRowDaseDv #anaDaseNm").val(data.anaDaseNm); 
			$("#frmRowDaseDv #sctStrVal").val(data.sctStrVal);
			$("#frmRowDaseDv #sctEndVal").val(data.sctEndVal);
			$("#frmRowDaseDv #drtDsgVal").val(data.drtDsgVal);

			var rdmSmplTypCd = data.rdmSmplTypCd;

			if(rdmSmplTypCd == "C") {
				$("#frmRowDaseDv #rdoRdmSmplCnt").prop("checked",true);
			}else{
				$("#frmRowDaseDv #rdoRdmSmplRate").prop("checked",true);
			}			
			
			$("#frmRowDaseDv #rdmSmplCnt").val(data.rdmSmplCnt);    
			$("#frmRowDaseDv #rdmSmplRate").val(data.rdmSmplRate);

			var rdmValFix = data.rdmValFix;

			//alert(rdmValFix);

			if(rdmValFix == "1") {

				$("#frmRowDaseDv #rdmValFix").prop("checked",true);
			}
						
		},
		error: function (jqXHR, textStatus, errorThrown) {
			//creCompId = $("#frmRowDaseDv #creCompId").val();
			
			resetForm($("#frmRowDaseDv"));
			
			$("#udfOtlDtcId").val(udfOtlDtcId);
			$("#frmRowDaseDv #creCompId").val(creCompId);

			readyInitVal();
			
			$("#divRdmSel").show();
			$("#divDrtSel").hide();
			
			//alert("3");
		}
	});
	
}




</script>
	
 <form name="frmRowDaseDv" id="frmRowDaseDv" method="post" onsubmit="return false;">
	<input type="hidden" id="creCompId"   name="creCompId"  />
	<input type="hidden" id="creCompNm"   name="creCompNm"  /> 
	<input type="hidden" id="mdlJsonInf"  name="mdlJsonInf" />
	<!-- <input type="hidden" id="rowSltTypCd" name="rowSltTypCd" value="S" />  -->
	
  
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
  			<button class="btn_search"  id="btnRowDaseDvSave"  name="btnRowDaseDvSave"><s:message code="STRG" /></button> <!-- 저장 -->                      
      	</div>
    </div>
    <!-- 버튼영역 end -->	
  	  	
  	<div style="clear:both; height:10px;"><span></span></div>
  	
  	<div class="stit">행선택</div><!-- 행선택  -->  	
  	
  	<div style="clear:both; height:10px;"><span></span></div>
  	  	
    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="행선택">
        <caption>행선택</caption><!-- 행선택 -->
	    <colgroup>  	 	  	  
	 	   <col style="width:*;" />  			  
	    </colgroup>
	    <tbody>   
    	<tr>    		
    		<td style="font-size:12pt;" > 
    			<input type="radio" id="rdoRdmSel" name="rowSltTypCd" value="S" />&nbsp;&nbsp;랜덤샘플링&nbsp;&nbsp;
    			&nbsp;&nbsp;<input type="radio" id="rdoDrtSel" name="rowSltTypCd" value="D" />&nbsp;&nbsp;직접지정     			
    		</td>    		
    	</tr>
    	</tbody>
    </table>  	
    
  	<div style="clear:both; height:10px;"><span></span></div>
    <div style="clear:both; height:10px;"><span></span></div>			
  	
  	<div id="divRdmSel" >
	    <legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
	      		    
		<div class="tb_basic" >
		    
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> 
			   <caption></caption>
			   <colgroup>
				   <col style="width:25%;" />
				   <col style="width:25%;" />  	
				   <col style="width:25%;" />
				   <col style="width:*;" />  			  
			   </colgroup>
			   <tbody>   			       
			   		<tr>                               
			           <th scope="row"  ><input type="radio" id="rdoRdmSmplCnt" name="rdmSmplTypCd" value="C" />&nbsp;갯수</label></th>
			           <td>			              
			               <input type="text" id="rdmSmplCnt" name="rdmSmplCnt" class="wd98p" />			               
			           </td> 	
			           
			           <th scope="row" ><input type="radio" id="rdoRdmSmplRate" name="rdmSmplTypCd" value="R" />&nbsp;비율</label></th>
			           <td>			           	   
			               <input type="text" id="rdmSmplRate" name="rdmSmplRate" class="wd98p" /> 			               			              
			           </td> 			          	          
			       </tr>         			                    			     
			   </tbody>
		   </table> 
		     		    
	  	</div>
	  	
	  	<div style="clear:both; height:20px;"></div>
	  	 
	  	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="행선택">
	        <caption>행선택</caption><!-- 행선택 -->
		    <colgroup>  	 	  	  
		 	   <col style="width:*;" />  			  
		    </colgroup>
		    <tbody>   
	    	<tr>    		
	    		<td style="font-size:10pt;text-align:right;" > 
	    			<input type="checkbox" id="rdmValFix" name="rdmValFix" value="1" />&nbsp;랜덤값고정		
	    		</td>    		
	    	</tr>
	    	</tbody>
	    </table>  	
	  	 	  	 	  	
  	</div>
  	
  	<div id="divDrtSel">
  		<legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
		<div class="tb_basic" >
		    
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
			   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
			   <colgroup>
				   <col style="width:15%;" />
				   <col style="width:35%;" />  	
				   <col style="width:15%;" />
				   <col style="width:*;" />  			 			  
			   </colgroup>
			   <tbody>   
			   		<tr>    
			   		   <tr>                               
			           <th scope="row"  class="th_require">시작지점</label></th>
			           <td>
			               <input type="text" id="sctStrVal" name="sctStrVal" class="wd98p" />
			           </td> 	
			           
			           <th scope="row"  class="th_require">끝지점</label></th>
			           <td>
			               <input type="text" id="sctEndVal" name="sctEndVal" class="wd98p" />
			           </td> 			          	          
			       </tr>
			       <!--                                       
			       <tr>
			           <th scope="row"  class="th_require">입력</label></th>
			           <td colspan="3">
			               <input type="text" id="drtDsgVal" name="drtDsgVal" />
			           </td> 				           			            			          	         
			       </tr>
			        -->                         			      
			   </tbody>
		   </table>   	  
	  	</div>
  	</div>
  
    <div style="clear:both; height:10px;"><span></span></div>
    
    <div style="clear:both; height:10px;"><span></span></div> 	    	          
               
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
	