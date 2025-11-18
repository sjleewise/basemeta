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
		madGrid();
		
		var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

		var obj = $("#frmMad #srcAnaDaseId");
		
		var creCompId   = $("#frmMad #creCompId").val();

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		$("#btnMadSrch").click(function() {
			doMadAction("Search");
		});
		
		$("#btnMadSave").click(function() {
			doMadAction("Save");
		});
		
		$("#btnMadSelAll").click(function() {
			doMadAction("SelectAll");
		});
	});
	
	function compMad(node) { 
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 
	
	
		$("#frmMad #creCompId").val(compKeyId);
		$("#frmMad #creCompNm").val(compHashNm);
		
		//콤포넌트별 div 토글
		toggelDivComp("divCompMad");
		
		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmMad #creCompId").val();

		var obj = $("#frmMad #srcAnaDaseId");

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		getAjaxMad(udfOtlDtcId, creCompId);
	}
	
	function getAjaxMad(udfOtlDtcId, creCompId){
		var url = "<c:url value='/advisor/prepare/udefoutlier/mad/getUodcMadDetail.do' />";
	
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
				$("#frmMad #srcAnaDaseId").val(data.srcAnaDaseId);
				$("#frmMad #anaDaseNm").val(data.anaDaseNm);
				
				doMadAction("Search");
			},
			error: function (jqXHR, textStatus, errorThrown) {

				
				resetForm($("#frmMad"));

				madGrid();

				mad_sheet.RemoveAll();	

				$("#frmMad #creCompId").val(creCompId);
				
			}
		});
	}
	
	function madGrid()
	{
	    
	    with(mad_sheet){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        /* 기존소스_bak 181019 */
	        /* var headtext = "No|선택|상태|컬럼명|컬럼한글명|데이터타입|이상치 컬럼명|anaVarId"; */
	        
	        var headtext = "<s:message code='BDQ.HEADER.BOXPLOT.RQST.DTL' />";
	        //No|선택|상태|컬럼명|컬럼한글명|데이터타입|이상치 컬럼명|anaVarId
	
	        var headers = [
	                    {Text:headtext, Align:"Center"}
	                ];
	        
	        
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:0};
	        
	        InitHeaders(headers, headerInfo); 
	
	        var cols = [                        
	                    {Type:"Seq",      Width:30,  SaveName:"ibsSeq",    	Align:"Center", Edit:0, Hidden:1},
	                    {Type:"CheckBox", Width:50,  SaveName:"ibsCheck",  	Align:"Center", Edit:1, Sort:0},
	                    {Type:"Status",   Width:0,   SaveName:"ibsStatus", 	Align:"Center", Edit:0, Hidden:1},
	                    {Type:"Text",     Width:110, SaveName:"colPnm",		Align:"Left",   Edit:0},
	                    {Type:"Text",     Width:110, SaveName:"colLnm",		Align:"Left",   Edit:0},
	                    {Type:"Text",     Width:80,  SaveName:"dataType",	Align:"Left",   Edit:0},	                   
	                    {Type:"Text",	  Width:130, SaveName:"otlColPnm", 	Align:"Center", Edit:1},
	                    {Type:"Text",	  Width:130, SaveName:"anaVarId", 	Align:"Center", Edit:0, Hidden:1}
	                ];
	                    
	        InitColumns(cols);
	        
	        FitColWidth();

	        SetExtendLastCol(1);	
	    }
	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(mad_sheet);    
	    //===========================
	}
	
	function doMadAction(action) {
		switch(action) {
		case "Search":
			var url = "<c:url value='/advisor/prepare/udefoutlier/mad/getUodcMadColList.do' />";
			
			var param = $("#frmUod, #frmMad").serialize();

			mad_sheet.DoSearch(url, param);
			
			break;
		case "Save":

			if(!chkSaveVal()) return;
			
			var url = "<c:url value='/advisor/prepare/udefoutlier/mad/regUodcMad.do'/>"; 

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmMad").serialize();
			
			ibsSaveJson = mad_sheet.GetSaveJson({StdCol:'ibsCheck'});
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
		}
	}

	function chkSaveVal(){

		if($("#frmMad #anaDaseNm").val() == ""){

			showMsgBox("ERR","<s:message code='ERR.BOXPLOT.RQST.DTL' />");/* 결과데이터명을 입력하세요. 코드값으로 수정 181019 */
			return false;
		}
		
		var checkVal;
		for(i=1; i<=mad_sheet.GetDataLastRow(); i++) {
			checkVal = mad_sheet.GetCellValue(i, 'ibsCheck');
			
			if(checkVal == 1) {
				if((mad_sheet.GetCellValue(i, 'otlColPnm') == '' )) {
					showMsgBox("ERR","<s:message code='ERR.BOXPLOT.RQST.DTL2' />");/* 이상치 컬럼명을 입력해주세요. 코드값으로 수정 181019 */
					return false;
				}
			}
		}
		
		return true;
	}
	
	function mad_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {
		//console.log(Row + ", " + Col + ", " + Value + ", " + OldValue + ", " + RaiseFlag);
		
		var colNm = mad_sheet.ColSaveName(0, Col);
		
		if(colNm == "ibsCheck" && Value == 1 && RaiseFlag == 0) {
			if(mad_sheet.GetCellValue(Row, 'dataType') != 'NUMBER' && mad_sheet.GetCellValue(Row, 'dataType') != '') {
				showMsgBox("ERR","<s:message code='MSG.BOXPLOT.RQST.DTL' />");/* 데이터 타입이 NUMBER인 컬럼만 선택 가능합니다. 코드값으로 수정 181019 */
				mad_sheet.SetCellValue(Row, 'otlColPnm', '');
				mad_sheet.SetCellValue(Row, 'ibsCheck', 0);
				//alert(mad_sheet.GetCellValue(Row, 'dataType'));
				return;
			}
			
			mad_sheet.CheckAll('ibsCheck', 0, 0);
			for(i=1; i<=mad_sheet.GetDataLastRow(); i++) {
				if(mad_sheet.GetCellValue(i, 'otlColPnm') != '' && i != Row) {
					mad_sheet.SetCellValue(i, 'otlColPnm', '');
				}
			}
			
			var otlColPnmVal = mad_sheet.GetCellValue(Row, 'colPnm') + "_otl";
			mad_sheet.SetCellValue(Row, 'otlColPnm', otlColPnmVal);
			mad_sheet.SetCellValue(Row, 'ibsCheck', 1);
		} else if(Col == 5 && Value == 0 && RaiseFlag == 0) {
			mad_sheet.SetCellValue(Row, 'otlColPnm', '');
		}
	}
</script>

<form name="frmMad" id="frmMad" method="post" onsubmit="return false;">
	<input type="hidden" id="creCompId"  name="creCompId"  />
	<input type="hidden" id="creCompNm"  name="creCompNm"  /> 
	<input type="hidden" id="mdlJsonInf" name="mdlJsonInf" />
	
	 
	<div class="stit"><s:message code='DATA.SET.CHC'/></div><!-- 데이터셋선택  코드값으로 수정 181019-->  
				
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
						<th scope="row"  class="th_require"><s:message code='DATA.SET'/></label></th><!-- 데이터셋 코드값으로 수정 181019 -->
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
			<button class="btn_search"  id="btnMadSrch"  name="btnMadSrch"><s:message code="INQ"/></button> <!-- 조회 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="stit"><s:message code="CLMN.CHC3"/></div><!-- 데이터셋 명  --><!-- 컬럼 선택 코드값으로 수정 181019 -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="mad_sheet">  
		<script type="text/javascript">createIBSheet("mad_sheet", "100%", "250px");</script>
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 버튼영역 -->
	<%-- <div class="divLstBtn" >
		<div class="bt02">  
			<button class="btn_search"  id="btnMadSelAll"  name="btnMadSelAll"><s:message code="WHL.CHC"/></button> <!-- 전체선택 -->                      
		</div>
	</div> --%>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<fieldset>
		<div class="stit"><s:message code="RSLT.DATA.NM" /></div><!-- 결과데이터명  코드값으로 수정 181019 -->
		 
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
						<th scope="row"  class="th_require" ><label for="anaDaseNm"><s:message code="RSLT.DATA.NM" /></label></th> <!-- 분석데이터셋명 --><!-- 결과데이터명  코드값으로 수정 181019 -->
						<td>
							<input type="text" name="anaDaseNm" id="anaDaseNm"  class="wd98p" />
						</td>
					</tr> 
				</tbody>
			</table>
		</div>
		
		<div style="clear:both; height:10px;"><span></span></div>
		
		<!-- 버튼영역 -->
		<div class="divLstBtn" >
			<div class="bt02">
				<button class="btn_search"  id="btnMadSave"  name="btnMadSave"><s:message code="STRG"/></button> <!-- 저장 -->                      
			</div>
		</div>
		<!-- 버튼영역 end -->
		
		<div style="clear:both; height:10px;"><span></span></div>
	</fieldset>
</form>