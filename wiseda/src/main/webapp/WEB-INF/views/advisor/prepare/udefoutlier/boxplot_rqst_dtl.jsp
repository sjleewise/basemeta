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
		boxplotGrid();
		
		var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

		var obj = $("#frmBoxplot #srcAnaDaseId");
		
		var creCompId   = $("#frmBoxplot #creCompId").val();

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		$("#btnBoxplotSrch").click(function() {
			doBoxplotAction("Search");
		});
		
		$("#btnBoxplotSave").click(function() {
			doBoxplotAction("Save");
		});
		
		$("#btnBoxplotSelAll").click(function() {
			doBoxplotAction("SelectAll");
		});
	});
	
	function compBoxplot(node) { 
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 
	
	
		$("#frmBoxplot #creCompId").val(compKeyId);
		$("#frmBoxplot #creCompNm").val(compHashNm);
		
		//콤포넌트별 div 토글
		toggelDivComp("divCompBoxplot");
		
		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmBoxplot #creCompId").val();

		var obj = $("#frmBoxplot #srcAnaDaseId");

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		getAjaxBoxplot(udfOtlDtcId, creCompId);
	}
	
	function getAjaxBoxplot(udfOtlDtcId, creCompId){
		var url = "<c:url value='/advisor/prepare/udefoutlier/boxplot/getUodcBoxplotDetail.do' />";
	
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
				$("#frmBoxplot #srcAnaDaseId").val(data.srcAnaDaseId);
				$("#frmBoxplot #anaDaseNm").val(data.anaDaseNm);
				
				doBoxplotAction("Search");
			},
			error: function (jqXHR, textStatus, errorThrown) {

				
				resetForm($("#frmBoxplot"));

				boxplotGrid();

				boxplot_sheet.RemoveAll();	

				$("#frmBoxplot #creCompId").val(creCompId);
				
			}
		});
	}
	
	function boxplotGrid()
	{
	    
	    with(boxplot_sheet){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headtext = "<s:message code='BDQ.HEADER.BOXPLOT.RQST.DTL1'/>";
			//No.|선택|상태|컬럼명|컬럼한글명|데이터타입|이상치 컬럼명|anaVarId
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
	    init_sheet(boxplot_sheet);    
	    //===========================
	}
	
	function doBoxplotAction(action) {
		switch(action) {
		case "Search":
			var url = "<c:url value='/advisor/prepare/udefoutlier/boxplot/getUodcBoxplotColList.do' />";
			
			var param = $("#frmUod, #frmBoxplot").serialize();

			boxplot_sheet.DoSearch(url, param);
			
			break;
		case "Save":

			if(!chkSaveVal()) return;
			
			var url = "<c:url value='/advisor/prepare/udefoutlier/boxplot/regUodcBoxplot.do'/>"; 

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmBoxplot").serialize();
			
			ibsSaveJson = boxplot_sheet.GetSaveJson({StdCol:'ibsCheck'});
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
		}
	}

	function chkSaveVal(){

		if($("#frmBoxplot #anaDaseNm").val() == ""){

			showMsgBox("ERR","결과데이터명을 입력하세요.");
			return false;
		}
		
		var checkVal;
		for(i=1; i<=boxplot_sheet.GetDataLastRow(); i++) {
			checkVal = boxplot_sheet.GetCellValue(i, 'ibsCheck');
			
			if(checkVal == 1) {
				if((boxplot_sheet.GetCellValue(i, 'otlColPnm') == '' )) {
					alert('이상치 컬럼명을 입력해주세요.');
					return false;
				}
			}
		}
		
		return true;
	}
	
	function boxplot_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {
		//console.log(Row + ", " + Col + ", " + Value + ", " + OldValue + ", " + RaiseFlag);
		
		var colNm = boxplot_sheet.ColSaveName(0, Col);
		
		if(colNm == "ibsCheck" && Value == 1 && RaiseFlag == 0) {
			if(boxplot_sheet.GetCellValue(Row, 'dataType') != 'NUMBER') {
				alert("데이터 타입이 NUMBER인 컬럼만 선택 가능합니다.");
				boxplot_sheet.SetCellValue(Row, 'otlColPnm', '');
				boxplot_sheet.SetCellValue(Row, 'ibsCheck', 0);
				return;
			}
			
			boxplot_sheet.CheckAll('ibsCheck', 0, 0);
			for(i=1; i<=boxplot_sheet.GetDataLastRow(); i++) {
				if(boxplot_sheet.GetCellValue(i, 'otlColPnm') != '' && i != Row) {
					boxplot_sheet.SetCellValue(i, 'otlColPnm', '');
				}
			}
			
			var otlColPnmVal = boxplot_sheet.GetCellValue(Row, 'colPnm') + "_otl";
			boxplot_sheet.SetCellValue(Row, 'otlColPnm', otlColPnmVal);
			boxplot_sheet.SetCellValue(Row, 'ibsCheck', 1);
		} else if(Col == 5 && Value == 0 && RaiseFlag == 0) {
			boxplot_sheet.SetCellValue(Row, 'otlColPnm', '');
		}
	}
</script>

<form name="frmBoxplot" id="frmBoxplot" method="post" onsubmit="return false;">
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
			<button class="btn_search"  id="btnBoxplotSrch"  name="btnBoxplotSrch"><s:message code="INQ"/></button> <!-- 조회 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="stit">컬럼 선택</div><!-- 데이터셋 명  -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="boxplot_sheet">  
		<script type="text/javascript">createIBSheet("boxplot_sheet", "100%", "250px");</script>
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 버튼영역 -->
	<%-- <div class="divLstBtn" >
		<div class="bt02">  
			<button class="btn_search"  id="btnBoxplotSelAll"  name="btnBoxplotSelAll"><s:message code="WHL.CHC"/></button> <!-- 전체선택 -->                      
		</div>
	</div> --%>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<fieldset>
		<div class="stit">결과데이터 명</div><!-- 결과데이터 명  -->
		 
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
						<th scope="row"  class="th_require" ><label for="anaDaseNm">결과데이터명</label></th> <!-- 분석데이터셋명 -->
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
				<button class="btn_search"  id="btnBoxplotSave"  name="btnBoxplotSave"><s:message code="STRG"/></button> <!-- 저장 -->                      
			</div>
		</div>
		<!-- 버튼영역 end -->
		
		<div style="clear:both; height:10px;"><span></span></div>
	</fieldset>
</form>