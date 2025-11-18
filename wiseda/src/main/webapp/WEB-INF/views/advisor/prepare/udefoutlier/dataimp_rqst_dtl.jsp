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
	var connTrgSchHiveJson = ${codeMap.connTrgSchHive};
	
	$("#dataBtn").hide();
	
	$("input[name='rdoSelImp']").change(function(){

		//alert($(this).val());
		
		var dataImpDcd = $(this).val();

		if(dataImpDcd == "T") {			
			$("#divSelTbl").show();
			$("#divSelRes").hide();
			$("#divSelCsv").hide();
			$("#divSelHF").hide();

			$("#frmDaseImp #impDataDcd").val("T");
			
		}else if(dataImpDcd == "R") {
			$("#divSelTbl").hide();
			$("#divSelRes").show();
			$("#divSelCsv").hide();
			$("#divSelHF").hide();

			$("#frmDaseImp #impDataDcd").val("R");
		}else if(dataImpDcd == "C") {
			$("#divSelTbl").hide();
			$("#divSelRes").hide();
			$("#divSelCsv").show();
			$("#divSelHF").hide();

			$("#frmDaseImp #impDataDcd").val("C");
		}else if(dataImpDcd == "H") {
			$("#divSelTbl").hide();
			$("#divSelRes").hide();
			$("#divSelCsv").hide();
			$("#divSelHF").show();

			$("#frmDaseImp #impDataDcd").val("H");
		}
	});
    
	//조회 
	$("#btnDaseImpSrch").click(function(){
		
		doDataImpAction("TblSearch");
	});

	//저장
	$("#btnTblDataImpSave").click(function(){
		
		doDataImpAction("Save");
	});
	
	//조회 
	$("#btnDaseImpSrch2").click(function(){
		doDataImpAction("HiveTblSearch");
	});

	//저장
	$("#btnTblDataImpSave2").click(function(){
		
		doDataImpAction("HiveSave");
	});

	double_select(connTrgSchJson, $("#frmSelTbl #dbConnTrgId"));
   	$('select', $("#frmSelTbl #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});
   	
   	double_select(connTrgSchHiveJson, $("#frmSelHiveTbl #dbConnTrgId"));
   	$('select', $("#frmSelHiveTbl #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchHiveJson, $(this));
   	});

   	initDataImpGrid();
   	initDataImpGrid_hive();
   	
   	$("#frmSelTbl #dbSchId").change(function() {
   		tblSelect();
	});
   	
   	$("#frmSelHiveTbl #dbSchId").change(function() {
   		tblSelect2();
	});
});

function tblSelect() {
	var url = "<c:url value='/advisor/prepare/textcluster/getTblList.do' />";
	
	var param = new Object();
	param.srcDbConnTrgId = $("#frmSelTbl #dbConnTrgId option:selected").val();
	param.srcDbcSchNm = $("#frmSelTbl #dbSchId option:selected").text();
	
	$.ajax({
		url: url,
		async: false,
		type: "POST",
		data: replacerXssParam(param),
		dataType: 'json',
		success: function (data) {
			$("#frmSelTbl #dbcTblNm").find("option").remove().end();
			
			$("#frmSelTbl #dbcTblNm").append('<option value=""></option>');
			
			for(i=0; i<data.length; i++) {
				$("#frmSelTbl #dbcTblNm").append('<option value="' + data[i].srcDbcTblNm + '">' + data[i].srcDbcTblNm + '</option>');
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {
						
		}
	});
}

function tblSelect2() {
	var url = "<c:url value='/advisor/prepare/textcluster/getHiveTblList.do' />";
	
	var param = new Object();
	param.srcDbConnTrgId = $("#frmSelHiveTbl #dbConnTrgId option:selected").val();
	param.srcDbcSchNm = $("#frmSelHiveTbl #dbSchId option:selected").text();
	
	$.ajax({
		url: url,
		async: false,
		type: "POST",
		data: replacerXssParam(param),
		dataType: 'json',
		success: function (data) {
			$("#frmSelHiveTbl #dbcTblNm").find("option").remove().end();
			
			$("#frmSelHiveTbl #dbcTblNm").append('<option value=""></option>');
			
			for(i=0; i<data.length; i++) {
				$("#frmSelHiveTbl #dbcTblNm").append('<option value="' + data[i].srcDbcTblNm + '">' + data[i].srcDbcTblNm + '</option>');
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {
						
		}
	});
}

$(function(){
	$("#btn").click(function(){
		if(!valSaveCheckDaseImp()) return;
		
		var formData = new FormData();

		formData.append("csvFile", $("input[name=csvFile]")[0].files[0]);
		
		var url = "<c:url value='/advisor/prepare/udefoutlier/daseimp/regDataImpFile.do' />";
		
		$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

		var param = $("#frmUod, #frmDaseImp").serializeArray();
		
		$.each(param, function(key, input){
			formData.append(input.name, input.value);
		});
		formData.append("anaDaseNm", $("#divSelCsv #anaDaseNm").val());
		
		$.ajax({
			url: url,
			data: formData,
			processData: false,
			contentType: false,
			type: 'POST',
			beforeSend: function () {
				// 처리중이니 잠시 기다려 주십시요.
				showMsgBox("PRC", gMSG_PRC_WAIT);
			},
			success: function(data){
				showMsgBox("INF", "<s:message code="MSG.SAVE" />");
				$("#dataBtn").show();
			},
			error: function(data) {
				showMsgBox("ERR", "<s:message code="ERR.SAVE" />");
			}
		});
	});
	
	$("#dataBtn").click(function() {
		var url = "<c:url value='/advisor/prepare/udefoutlier/popup/dataimp_pop.do' />";
		
		var param = "?" + $("#frmUod #udfOtlDtcId").serialize();

		var vPop = OpenWindow(url+param, 500, 350);
	});
	
	$("#hSaveBtn").click(function(){
		//if(!valSaveCheckDaseImp()) return;
		
		//var formData = new FormData();

		//formData.append("csvFile", $("input[name=csvFile]")[0].files[0]);
		
		var url = "<c:url value='/advisor/prepare/udefoutlier/daseimp/regDataImpHadoopFile.do' />";
		
		$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

		var param = $("#frmUod, #frmDaseImp").serializeArray();
		
		/* $.each(param, function(key, input){
			formData.append(input.name, input.value);
		});
		formData.append("anaDaseNm", $("#divSelCsv #anaDaseNm").val()); */
		
		$.ajax({
			url: url,
			data: replacerXssParam(param),
			processData: false,
			contentType: false,
			type: 'POST',
			beforeSend: function () {
				// 처리중이니 잠시 기다려 주십시요.
				showMsgBox("PRC", gMSG_PRC_WAIT);
			},
			success: function(data){
				showMsgBox("INF", "<s:message code="MSG.SAVE" />");
				//$("#dataBtn").show();
			},
			error: function(data) {
				showMsgBox("ERR", "<s:message code="ERR.SAVE" />");
			}
		});
	});
});

function initDataImpGrid()
{
	
	with(grid_dataimp){  
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
				
		var headtext = "<s:message code='BDQ.HEADER.DATAIMP.RQST.DTL2'/>";
		
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	  Width:50,  SaveName:"ibsSeq",	   Align:"Center", Edit:0, Hidden:1},
					{Type:"Status",   Width:40,  SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:90,  SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					
					{Type:"Text",     Width:150, SaveName:"colPnm",	   Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:150, SaveName:"colLnm",	   Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:80,  SaveName:"dataType",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:80,  SaveName:"anaVarId",  Align:"Left", Edit:0, Hidden:1},
					
				];
					
		InitColumns(cols);
		
		
 		//FitColWidth();   
	    //SetSheetHeight(200);
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_dataimp);   

	//===========================   
}

function initDataImpGrid_hive()
{
	
	with(grid_dataimp_hive){  
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
				
		/* 기존소스_bak 181018 */
		/* var headtext = "No.|상태|선택|컬럼명|컬럼한글명|데이터타입"; */
		
		var headtext = "<s:message code='BDQ.HEADER.DATAIMP.RQST.DTL'/>";
		
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	  Width:50,  SaveName:"ibsSeq",	   Align:"Center", Edit:0, Hidden:1},
					{Type:"Status",   Width:40,  SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:90,  SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					
					{Type:"Text",     Width:150, SaveName:"colPnm",	   Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:150, SaveName:"colLnm",	   Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:80,  SaveName:"dataType",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",     Width:80,  SaveName:"anaVarId",  Align:"Left", Edit:0, Hidden:1},
					
				];
					
		InitColumns(cols);
		
		
 		//FitColWidth();   
	    //SetSheetHeight(200);
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_dataimp_hive);   

	//===========================   
}

function doDataImpAction(action) {

	switch(action){

		case "TblSearch":

			if (isBlankStr( $("#frmSelTbl #dbConnTrgId").val())) {
        		showMsgBox("ERR", "<s:message code="MSG.DB.MS.INFO.CHC" />"); //DBMS정보를 선택해주세요.
        		return;
        	}

			var url = "<c:url value='/advisor/prepare/udefoutlier/daseimp/getUodcDaseImpColList.do' />";
			
			var param = $("#frmSelTbl, #frmUod, #frmDaseImp").serialize(); 

			//alert(param);

			grid_dataimp.DoSearch(url, param);			

			break;
			
		case "Save":

			if(!valSaveCheckDaseImp()) return;

			ibsSaveJson = grid_dataimp.GetSaveJson(1);

			
			if(ibsSaveJson.data.length == 0) {
				showMsgBox("ERR","체크박스에 체크하세요.");
				return;
			}	 
			
			var url = "<c:url value="/advisor/prepare/udefoutlier/daseimp/regDataImptlist.do"/>";

			//alert(myDiagram.model.toJson());

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmSelTbl, #frmUod, #frmDaseImp").serialize();
			
			//alert(param);
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
			
		case "HiveTblSearch":

			if (isBlankStr( $("#frmSelHiveTbl #dbConnTrgId").val())) {
        		showMsgBox("ERR", "<s:message code="MSG.DB.MS.INFO.CHC" />"); //DBMS정보를 선택해주세요.
        		return;
        	}

			var url = "<c:url value='/advisor/prepare/udefoutlier/daseimp/getUodcDaseImpHiveColList.do' />";
			
			var param = $("#frmSelHiveTbl, #frmUod, #frmDaseImp").serialize(); 

			//alert(param);

			grid_dataimp_hive.DoSearch(url, param);			

			break;
			
		case "HiveSave":

			if(!valSaveCheckDaseImp()) return;

			ibsSaveJson = grid_dataimp_hive.GetSaveJson(1);

			
			if(ibsSaveJson.data.length == 0) {
				/* 기존소스_bak 181018 */
				/* showMsgBox("ERR","체크박스에 체크하세요."); */
				
				showMsgBox("ERR","<s:message code='CHK.CHK' />");
				return;
			}	 
			
			var url = "<c:url value="/advisor/prepare/udefoutlier/daseimp/regDataImptlist.do"/>";

			//alert(myDiagram.model.toJson());

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmSelHiveTbl, #frmUod, #frmDaseImp").serialize();
			
			//alert(param);
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
	}
	
}

function valSaveCheckDaseImp(){ 

	if($("#frmUod #udfOtlDtcNm").val() == "") {
		showMsgBox("ERR","사용자정의이상값탐지명을 입력하세요.");
		return false;
	}
	
	if($("#frmSelTbl #anaDaseNm").val() == "" && $("#divSelCsv #anaDaseNm").val() == "" && $("#frmSelHiveTbl #anaDaseNm").val() == "") { 
		/* 기존소스_bak 181018 */
		showMsgBox("ERR","분석데이터셋명을 입력하세요.");
		
		/* showMsgBox("ERR","<s:message code='ERR.DATAIMP.RQST.DTL2' />"); */
		return false;
	}

	return true;
}


function compDataImp(node) {

	//alert(JSON.stringify(node.data));

	//alert(myDiagram.model.toJson()); 
	
	var compKeyId  = node.data.key;
	var compHashNm = node.data.text; 


	$("#frmDaseImp #creCompId").val(compKeyId);
	$("#frmDaseImp #creCompNm").val(compHashNm);
	
	//콤포넌트별 div 토글
	toggelDivComp("divCompDataImp");  
	
	<c:choose>
		<c:when test="${wadUod.ibsStatus eq 'U' }" >
	
			var udfOtlDtcId = $("#udfOtlDtcId").val();
			var creCompId   = $("#creCompId").val();
	
			getAjaxDaseImpTbl(udfOtlDtcId, creCompId);
		</c:when>
		<c:otherwise>
			$("#rdoSelTbl").attr("checked",true);
			$("#frmDaseImp #impDataDcd").val("T");
			$("#divSelTbl").show();
			$("#divSelRes").hide();
			$("#divSelCsv").hide();
			$("#divSelHF").hide();
		</c:otherwise>
	</c:choose>
}

function getAjaxDaseImpTbl(udfOtlDtcId, creCompId){

	var url = "<c:url value='/advisor/prepare/udefoutlier/daseimp/getUodcDaseImpDetail.do' />";

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
			
			if(data.impDataDcd == "T"){
				$("#rdoSelTbl").attr("checked", true);
				
				$("#frmSelTbl #dbConnTrgId").val(data.dbConnTrgId);

				$("#frmSelTbl #dbConnTrgId").change();
				
				$("#frmSelTbl #dbSchId").val(data.dbSchId);
				tblSelect();
				$("#frmSelTbl #dbcTblNm").val(data.dbcTblNm);

				$("#frmSelTbl #anaDaseNm").val(data.anaDaseNm); 
				
				$("#divSelTbl").show();
				$("#divSelRes").hide();
				$("#divSelCsv").hide();
				$("#divSelHF").hide();

				$("#frmDaseImp #impDataDcd").val("T");
				
				doDataImpAction("TblSearch");
			}else if(data.impDataDcd == "R"){
				$("#rdoSelRes").attr("checked", true);
			}else if(data.impDataDcd == "C"){
				$("#rdoSelCsv").attr("checked", true);
				
				//console.log(data);
				
				$("#divSelCsv #anaDaseNm").val(data.anaDaseNm);
				$("#divSelCsv #fileName").val(data.fileName);
				$("#divSelCsv #filePath").val(data.filePath);
				
				$("#divSelTbl").hide();
				$("#divSelRes").hide();
				$("#divSelCsv").show();
				$("#divSelHF").hide();

				$("#frmDaseImp #impDataDcd").val("C");
				$("#dataBtn").show();
			}else if(data.impDataDcd == "H"){
				$("#rdoSelHF").attr("checked", true);
				
				$("#frmSelHiveTbl #dbConnTrgId").val(data.dbConnTrgId);

				$("#frmSelHiveTbl #dbConnTrgId").change();
				
				$("#frmSelHiveTbl #dbSchId").val(data.dbSchId);
				
				tblSelect2();
				
				$("#frmSelHiveTbl #dbcTblNm").val(data.dbcTblNm);

				$("#frmSelHiveTbl #anaDaseNm").val(data.anaDaseNm); 
				
				$("#divSelTbl").hide();
				$("#divSelRes").hide();
				$("#divSelCsv").hide();
				$("#divSelHF").show();

				$("#frmDaseImp #impDataDcd").val("H");
				
				doDataImpAction("HiveTblSearch");
			}
			 
		},
		error: function (jqXHR, textStatus, errorThrown) {
			resetForm($("#frmDaseImp"));  
			resetForm($("#frmSelTbl"));
			initDataImpGrid();
			grid_dataimp.RemoveAll();

			$("#frmDaseImp #creCompId").val(creCompId);
		}
	});
	
}

</script>



	<div class="stit">데이터임포트</div><!--데이터임포트 -->
  	
  	<table  width="100%;" border="0" cellspacing="0" cellpadding="0"> 
  		<colgroup>
	    <col style="width:*;" />	  
	    </colgroup>
	    
  	    <tbody>   
		<tr>
			<td class="ht30">
				<input type="radio" id="rdoSelTbl" name="rdoSelImp" value="T" />&nbsp;테이블에서 불러오기
			</td>
		</tr>
		
		<tr>
			<td class="ht30" >
				<input type="radio" id="rdoSelCsv" name="rdoSelImp" value="C" />&nbsp;<s:message code='UDF.CSV.DATA.LOAD'/>
			</td>
		</tr>
		<tr>
			<td class="ht30" >
				<input type="radio" id="rdoSelHF" name="rdoSelImp" value="H" />&nbsp;Hive
			</td>
		</tr>
		 
		</tbody>
	</table>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<form name="frmDaseImp" id="frmDaseImp" method="post" onsubmit="return false;">
		<input type="hidden" id="creCompId"  name="creCompId"  />
 		<input type="hidden" id="creCompNm"  name="creCompNm"  />  		
 		<input type="hidden" id="impDataDcd" name="impDataDcd" />
	</form>
  	
  	<div id="divSelTbl" >
  		<div class="stit">테이블선택</div><!--테이블선택 -->
  		<form name="frmSelTbl" id="frmSelTbl" method="post" onsubmit="return false;">  	
  			
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
			           <th scope="row"  class="th_require"><label for="dbConnTrgPnm"><s:message code="DB.MS" /></label></th><!-- 진단대상명 -->
			           <td>
			               <select class = "wd100" id="dbConnTrgId" class="" name="dbConnTrgId">
				             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
				            </select>
				            <select class = "wd100" id="dbSchId" class="" name="dbSchId">
				             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
				            </select>
			           </td> 			          	          
			       </tr>                         
			       <tr>                               
			           <th scope="row"  class="th_require" ><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!-- 테이블명 -->
			           <td>
			               <!-- <input type="text" name="dbcTblNm" id="dbcTblNm"  class="wd99p" /> -->
			               <select style="width:99%;" id="dbcTblNm" class="" name="dbcTblNm">
				             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
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
				<button class="btn_search"  id="btnDaseImpSrch"  name="btnDaseImpSrch"><s:message code="INQ"/></button> <!-- 조회 -->   
				<button class="btn_search"  id="btnTblDataImpSave"   name="btnTblDataImpSave"><s:message code="STRG" /></button> <!-- 저장 -->                      
	    	</div>
      </div>
      <!-- 버튼영역 end -->	
      
      <div style="clear:both; height:10px;"><span></span></div> 	  
        
      <!-- 그리드 입력 입력 -->
      <div class="grid_01" id="grid_dataimp">  
           <script type="text/javascript">createIBSheet("grid_dataimp", "100%", "300px");</script>            
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
	  
  	</div>  <!-- divSelTbl end -->
  	
  	<!-- divSelCsv -->
  	<div id="divSelCsv">
  		<%-- <%@include file="./popup/dataimp_csv.jsp" %> --%>
  		<!-- 파일업로드 구현 -->
  		
			<div class="tb_basic" >
				<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> 
					<caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
					<colgroup>
						<col style="width:25%;" />
						<col style="width:*;" />  			  
					</colgroup>
					<tbody>    			   		      
						<tr>
							<th scope="row"  class="th_require" ><label for="anaDaseNm"><s:message code='ANLY.DATA.SET.NM'/></label></th> <!-- 분석데이터셋명 코드값으로 수정 181018 -->
							<td>
								<input type="text" name="anaDaseNm" id="anaDaseNm"  class="wd98p" />
							</td>
						</tr>
						<tr>
							<th scope="row"  ><label for="fileName"><s:message code='UDF.SAVED.DATA'/></label></th>
							<td>
								<input type="text" name="fileName" id="fileName"  class="wd98p" />
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align:center;">
								<form id="ajaxform" method="post" enctype="multipart/form-data" onsubmit="return false;">
						  			<input type="file" name="csvFile" id="csvFile" accept=".csv" style="width:98%;"/>
						  			<input type="hidden" name="filePath" id="filePath" />
						  		</form>
					  		</td>
						</tr>
					</tbody>
				</table>
				
				<div style="clear:both; height:10px;"><span></span></div>
				
				<div class="bt02">
					<button class="btn_search"  id="dataBtn"   name="btn"><s:message code='UDF.DATA'/></button> <!-- 데이터보기 -->
					<button class="btn_search"  id="btn"   name="btn"><s:message code="STRG" /></button> <!-- 저장 -->                      
		    	</div>
			</div>
		
  	</div>
  	<!-- divSelCsv end -->
  	
  	<!-- divSelHF -->
  	<div id="divSelHF">
			<div class="stit"><s:message code='TBL.CHC'/></div><!--테이블선택 코드값으로 수정 181018-->
		  		<form name="frmSelHiveTbl" id="frmSelHiveTbl" method="post" onsubmit="return false;">  	
		  			
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
					           <th scope="row"  class="th_require"><label for="dbConnTrgPnm"><s:message code="DB.MS" /></label></th><!-- 진단대상명 -->
					           <td>
					               <select class = "wd100" id="dbConnTrgId" class="" name="dbConnTrgId">
						             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
						            </select>
						            <select class = "wd100" id="dbSchId" class="" name="dbSchId">
						             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
						            </select>
					           </td> 			          	          
					       </tr>                         
					       <tr>                               
					           <th scope="row"  class="th_require" ><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!-- 테이블명 -->
					           <td>
					               <!-- <input type="text" name="dbcTblNm" id="dbcTblNm"  class="wd99p" /> -->
					               <select style="width:99%;" id="dbcTblNm" class="" name="dbcTblNm">
						             <option value=""><s:message code="CHC" /></option> <!-- 선택 -->
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
						<button class="btn_search"  id="btnDaseImpSrch2"  name="btnDaseImpSrch2"><s:message code="INQ"/></button> <!-- 조회 -->   
						<button class="btn_search"  id="btnTblDataImpSave2"   name="btnTblDataImpSave2"><s:message code="STRG" /></button> <!-- 저장 -->                      
			    	</div>
		      </div>
		      <!-- 버튼영역 end -->	
		      
		      <div style="clear:both; height:10px;"><span></span></div> 	  
		        
		      <!-- 그리드 입력 입력 -->
		      <div class="grid_01" id="grid_dataimp_hive">  
		           <script type="text/javascript">createIBSheet("grid_dataimp_hive", "100%", "300px");</script>            
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
					           <th scope="row"  class="th_require" ><label for="anaDaseNm"><s:message code='ANLY.DATA.SET.NM'/></label></th> <!-- 분석데이터셋명 코드값으로 수정 181018 -->
					           <td>
					               <input type="text" name="anaDaseNm" id="anaDaseNm"  class="wd99p" />
					           </td>
					       </tr>
					   </tbody>
				   </table>   
				  
			  </div>
			  
			  </fieldset>
		</form>
		
  	</div>
  	<!-- divSelHF end -->
  	  	
  	