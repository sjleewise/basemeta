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
		dataClnGrid();
		
		var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

		var obj = $("#frmDataCln #srcAnaDaseId");
		
		var creCompId   = $("#frmDataCln #creCompId").val();
		
		getAnaDaseId(obj, udfOtlDtcId, creCompId);  
		
		$("#btnColSrch").click(function() {
			doCleansingAction("Search");
		});
		
		$("#btnSelectAll").click(function() {
			doCleansingAction("SelectAll");
		});
		
		$("#btnDataClnSave").click(function() {
			doCleansingAction("Save");
		});
	});
	
	function compDataCln(node) { 
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 
	
	
		$("#frmDataCln #creCompId").val(compKeyId);
		$("#frmDataCln #creCompNm").val(compHashNm);
		
		//콤포넌트별 div 토글
		toggelDivComp("divCompDataCleansing");
	
		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmDataCln #creCompId").val();

		var obj = $("#frmDataCln #srcAnaDaseId");

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		getAjaxDataCln(udfOtlDtcId, creCompId);
	}
	
	function getAjaxDataCln(udfOtlDtcId, creCompId){
		var url = "<c:url value='/advisor/prepare/udefoutlier/datacln/getDataClnDetail.do' />";
	
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
				$("#frmDataCln #srcAnaDaseId").val(data.srcAnaDaseId);
				$("#frmDataCln #anaDaseNm").val(data.anaDaseNm);
				$('input:radio[name=resPrcDcd]:input[value='+data.resPrcDcd +']').prop("checked",true);
				
				doCleansingAction("Search");
			},
			error: function (jqXHR, textStatus, errorThrown) {

				resetForm($("#frmDataCln"));

				dataClnGrid();

				datacln_sheet.RemoveAll();

				$("#frmDataCln #creCompId").val(creCompId);
				$('input:radio[name=resPrcDcd]:input[value=01]').prop("checked",true);
			}
		});
	}
	
	function dataClnGrid()
	{
	    
	    with(datacln_sheet){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headtext = "<s:message code='BDQ.HEADER.DATACLEANSING.RQST.DTL3'/>";
	        //No.|선택|상태|컬럼명|컬럼한글명|데이터타입|결측치처리
	
	        var headers = [
	                    {Text:headtext, Align:"Center"}
	                ];
	        
	        
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 
	
	        var cols = [                        
	                    {Type:"Seq",      Width:30,  SaveName:"ibsSeq",    	Align:"Center", Edit:0, Hidden:1},
	                    {Type:"CheckBox", Width:55,  SaveName:"ibsCheck",  	Align:"Center", Edit:1, Sort:0},
	                    {Type:"Status",   Width:0,   SaveName:"ibsStatus", 	Align:"Center", Edit:0, Hidden:1},
	                    {Type:"Text",     Width:130, SaveName:"colPnm",		Align:"Left",   Edit:0},
	                    {Type:"Text",     Width:130, SaveName:"colLnm",		Align:"Left",   Edit:0},
	                    {Type:"Text",     Width:100, SaveName:"dataType",	Align:"Left",   Edit:0},	                   
	                    {Type:"Text",	  Width:0,   SaveName:"anaVarId", 	Align:"Center", Edit:0, Hidden:1},
	                    {Type:"Combo",	  Width:100, SaveName:"resPrcDcd", 	Align:"Center", Edit:1, Hidden:0}
	                ];
	                    
	        InitColumns(cols);
	        
	        SetColProperty("resPrcDcd", {ComboCode:"|01|02|03|04", ComboText:"|결측치삭제|0|평균값|중간값"});

	        SetExtendLastCol(1);	
	    }

	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(datacln_sheet);
	    //===========================
	}
	
	function doCleansingAction(action) {
		switch(action) {
		case "Search":
			var url = "<c:url value='/advisor/prepare/udefoutlier/datacln/getUodcDataClnColList.do' />";
			
			var param = $("#frmUod, #frmDataCln").serialize();

			datacln_sheet.DoSearch(url, param);
			
			break;
		case "Save":

			//필수 입력 체크
			if(!valSaveCheckCleansing()) return;
			
			var url = "<c:url value='/advisor/prepare/udefoutlier/datacln/regUodcDataCln.do'/>"; 

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmDataCln").serialize();
			
			ibsSaveJson = datacln_sheet.GetSaveJson({StdCol:'ibsCheck'});
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
		case "SelectAll":
			if(datacln_sheet.CheckedRows('ibsCheck') > 0)
				datacln_sheet.CheckAll('ibsCheck', 0);
			else
				datacln_sheet.CheckAll('ibsCheck', 1);
			break;
		}
	}

	function valSaveCheckCleansing(){  

		if($("#frmDataCln #anaDaseNm").val() == "") { 
			showMsgBox("ERR","분석데이터셋명을 입력하세요.");
			return false;
		}
		
		/* for(i=1; i<=datacln_sheet.GetDataLastRow(); i++) {
			if(datacln_sheet.GetCellValue(i, 'ibsCheck')==1 && datacln_sheet.GetCellValue(i, 'resPrcDcd')=='') {
				showMsgBox("ERR","결측치 처리 값을 입력하세요.");
				return false;
			} else if(datacln_sheet.GetCellValue(i, 'ibsCheck')==0 && datacln_sheet.GetCellValue(i, 'resPrcDcd')!='') {
				datacln_sheet.SetCellValue(i, 'resPrcDcd', '');
			}
		} */
		
		/* if(!$('input:radio[name=resPrcDcd]').is(':checked')) {
			showMsgBox("ERR","결측치 처리 값을 입력하세요.");
			return false;
		} */
		
		return true;
	}
	
	function datacln_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {
		var checkVal = datacln_sheet.GetCellValue(Row, 'ibsCheck');
		var dataType = datacln_sheet.GetCellValue(Row, 'dataType');
		//alert(checkVal);
		if(RaiseFlag==0 && checkVal==0){
			datacln_sheet.SetCellValue(Row, 'resPrcDcd', '');
			return;
		} 
		
		if(dataType=='VARCHAR') {
			var resPrcDcd = datacln_sheet.GetCellValue(Row, 'resPrcDcd');
			if(resPrcDcd=='03' || resPrcDcd=='04') {
				datacln_sheet.SetCellValue(Row, 'resPrcDcd', '');
			}	
		}
	}
</script>

<form name="frmDataCln" id="frmDataCln" method="post" onsubmit="return false;">
	<input type="hidden" id="creCompId"  name="creCompId"  />
	<input type="hidden" id="creCompNm"  name="creCompNm"  /> 
	<input type="hidden" id="mdlJsonInf" name="mdlJsonInf" />
	
	 
	<div class="stit">데이터셋 선택</div><!-- 데이터셋선택  -->  
				
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
		<div class="bt02">  
			<button class="btn_search"  id="btnColSrch"  name="btnColSrch"><s:message code="INQ"/></button> <!-- 조회 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="stit">컬럼 선택</div><!-- 데이터셋 명  -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="datacln_sheet">  
		<script type="text/javascript">createIBSheet("datacln_sheet", "100%", "250px");</script>
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 버튼영역 -->
	<div class="divLstBtn" >
		<div class="bt02">  
			<button class="btn_search"  id="btnSelectAll"  name="btnSelectAll"><s:message code="WHL.CHC"/></button> <!-- 전체선택 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<%-- <div class="stit">결측치 처리</div><!-- 결측치 처리  -->
	<div style="text-align:center; width:90%; margin:0 auto;">
		<table style="text-align:center;" width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>">
			<colgroup>
				<col style="width:25%;" />
				<col style="width:25%;" />
				<col style="width:25%;" />
				<col style="width:25%;" />
			</colgroup>
			
			<tr>
				<td style="text-align:center;">
					<input type="radio" name="resPrcDcd" id="resPrcDcd1" value="01" checked> 결측치 삭제
				</td>
				<td style="text-align:center;">
					<input type="radio" name="resPrcDcd" id="resPrcDcd2" value="02"> 0
				</td>
				<td style="text-align:center;">
					<input type="radio" name="resPrcDcd" id="resPrcDcd3" value="03"> 평균값
				</td>
				<td style="text-align:center;">
					<input type="radio" name="resPrcDcd" id="resPrcDcd4" value="04"> 중간값
				</td>
			</tr>
		</table>
	</div> --%>
	
	<div style="clear:both; height:20px;"><span></span></div>
	
	<fieldset>
		<div class="stit">데이터셋 명</div><!-- 데이터셋 명  -->
		 
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
		
		<div style="clear:both; height:10px;"><span></span></div>
		
		<!-- 버튼영역 -->
		<div class="divLstBtn" >
			<div class="bt02">
				<button class="btn_search"  id="btnDataClnSave"  name="btnDataClnSave"><s:message code="STRG"/></button> <!-- 저장 -->                      
			</div>
		</div>
		<!-- 버튼영역 end -->
		
		<div style="clear:both; height:10px;"><span></span></div>
	</fieldset>
</form>