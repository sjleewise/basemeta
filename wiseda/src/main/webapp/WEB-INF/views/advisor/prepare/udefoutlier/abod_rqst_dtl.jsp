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
		abodGrid();
		
		var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

		var obj = $("#frmAbod #srcAnaDaseId");
		
		var creCompId   = $("#frmAbod #creCompId").val();
		
		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		$("#btnAbodColSrch").click(function() {
			doAbodAction("Search");
		});
		
		$("#btnAbodSave").click(function() {
			doAbodAction("Save");
		});
		
		$("#abodSelectAll").click(function() {
			doAbodAction("SelectAll");
		});
		
		$("#frmAbod #otlRt").keyup(function(e) {
			$("#frmAbod #otlRt").val(floatCheck($("#frmAbod #otlRt").val(), e.key));
		});
		
		$("#frmAbod #otlRt").change(function() {
			if($("#frmAbod #otlRt").val() > 0.05 || !$("#frmAbod #otlRt").val() || $("#frmAbod #otlRt").val() < 0) {
				//alert("이상값 비율을 조건에 맞게 입력해주세요.");
				showMsgBox("ERR", "<s:message code='MSG.ISFR.RQST.DTL2' />");
				$("#frmAbod #otlRt").val('0.01');
				return;
			};
		});
	});
	
	function compAbod(node) { 
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 
	
	
		$("#frmAbod #creCompId").val(compKeyId);
		$("#frmAbod #creCompNm").val(compHashNm);
		
		//콤포넌트별 div 토글
		toggelDivComp("divCompAbod");
	
		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmAbod #creCompId").val();

		var obj = $("#frmAbod #srcAnaDaseId");

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		getAjaxAbod(udfOtlDtcId, creCompId);
		
		doAbodAction("Search");
	}
	
	function getAjaxAbod(udfOtlDtcId, creCompId){
		var url = "<c:url value='/advisor/prepare/udefoutlier/isolationforest/getUodcAbodDetail.do' />";
	
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
				$("#frmAbod #srcAnaDaseId").val(data.srcAnaDaseId);
				$("#frmAbod #anaDaseNm").val(data.anaDaseNm);
				$("#frmAbod #otlColPnm").val(data.otlColPnm);
				$("#frmAbod #otlRt").val(data.otlRt);
				
				doAbodAction("Search");
			},
			error: function (jqXHR, textStatus, errorThrown) {

				resetForm($("#frmAbod"));

				abodGrid();

				abod_sheet.RemoveAll();

				if($("#frmAbod #otlRt").val() == '') {
					$("#frmAbod #otlRt").val('0.01');
				}
				
				$("#frmAbod #creCompId").val(creCompId);				
			}
		});
	}
	
	function abodGrid()
	{
	    
	    with(abod_sheet){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        /* 기존소스_bak 181019 */
	        /* var headtext = "No|선택|상태|컬럼명|컬럼한글명|데이터타입"; */
	        
	        var headtext = "<s:message code='BDQ.HEADER.ISFR.RQST.DTL'/>";
	        //No|선택|상태|컬럼명|컬럼한글명|데이터타입
	
	        var headers = [
	                    {Text:headtext, Align:"Center"}
	                ];
	        
	        
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:0}; 
	        
	        InitHeaders(headers, headerInfo); 
	
	        var cols = [                        
	                    {Type:"Seq",      Width:30,  SaveName:"ibsSeq",    	Align:"Center", Edit:0, Hidden:1},
	                    {Type:"CheckBox", Width:55,  SaveName:"ibsCheck",  	Align:"Center", Edit:1},
	                    {Type:"Status",   Width:0,   SaveName:"ibsStatus", 	Align:"Center", Edit:0, Hidden:1},
	                    {Type:"Text",     Width:150, SaveName:"colPnm",		Align:"Left",   Edit:0},
	                    {Type:"Text",     Width:170, SaveName:"colLnm",		Align:"Left",   Edit:0},
	                    {Type:"Text",     Width:100, SaveName:"dataType",	Align:"Center", Edit:0}
	                    
	                ];
	                    
	        InitColumns(cols);
	        
	        //FitColWidth();

	        SetExtendLastCol(1);	
	    }
	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(abod_sheet);    
	    //===========================
	}
	
	function doAbodAction(action) {
		switch(action) {
		case "Search":
			var url = "<c:url value='/advisor/prepare/udefoutlier/isolationforest/getUodcAbodColList.do' />";
			
			var param = $("#frmUod, #frmAbod").serialize();

			abod_sheet.DoSearch(url, param);
			
			break;
		case "Save":
			var cnt=0;
			for(i=1; i<=abod_sheet.GetDataLastRow(); i++) {
				if(abod_sheet.GetCellValue(i, 'ibsCheck') == 1)
					cnt++;
			}
			if(cnt == 0) {
				showMsgBox("ERR","<s:message code='MSG.ISFR.RQST.DTL' />");/* 컬럼을 선택해주세요. 코드값으로 수정 181019 */
				return;
			}
			cnt = 0;
			
			if(!$("#frmAbod #otlColPnm").val()) {
				showMsgBox("ERR","<s:message code='ERR.BOXPLOT.RQST.DTL2' />");/* 이상치 컬럼명을 입력해주세요. 코드값으로 수정 181019 */
				return;
			};
			
			if($("#frmAbod #otlRt").val() > 0.05 || !$("#frmAbod #otlRt").val() ||$("#frmAbod #otlRt").val() < 0 ) {
				/* 기존소스_bak 181019 */
				/* alert("이상값 비율을 조건에 맞게 입력해주세요."); */
				
				showMsgBox("ERR","<s:message code='MSG.ISFR.RQST.DTL2' />");
				$("#otlRt").val('0.01');
				return;
			};
			
			if(!$("#frmAbod #anaDaseNm").val()) {
				/* 기존소스_bak 181019 */
				/* alert("결과데이터명을 입력해주세요."); */
				
				showMsgBox("ERR","<s:message code='ERR.BOXPLOT.RQST.DTL' />");
				return;
			}
			
			var url = "<c:url value='/advisor/prepare/udefoutlier/isolationforest/regUodcAbod.do'/>"; 

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmAbod").serialize();
			
			ibsSaveJson = abod_sheet.GetSaveJson({StdCol:'ibsCheck'});
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
		case "SelectAll":
			for(i=1; i<=abod_sheet.GetDataLastRow(); i++) {
				if(abod_sheet.GetCellValue(i, 'dataType') == 'NUMBER' || abod_sheet.GetCellValue(i, 'dataType') == '') {
					abod_sheet.SetCellValue(i, 'ibsCheck', 1);
				} else {
					abod_sheet.SetCellValue(i, 'ibsCheck', 0);
				}
			}
			break;
		}
	}
	
	function abod_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {

		var colNm = abod_sheet.ColSaveName(0, Col); 

		if(colNm == "ibsCheck"  && Value == 1 && RaiseFlag == 0) {
			if(abod_sheet.GetCellValue(Row, 'dataType') != 'NUMBER' && abod_sheet.GetCellValue(i, 'dataType') != '') {
				/* 기존소스_bak 181019 */
				/* alert("데이터 타입이 NUMBER인 컬럼만 선택 가능합니다."); */
				
				showMsgBox("ERR","<s:message code='MSG.BOXPLOT.RQST.DTL' />");
				abod_sheet.SetCellValue(Row, 'ibsCheck', 0);
				return;
			}
		}
	}
</script>

<form name="frmAbod" id="frmAbod" method="post" onsubmit="return false;">
	<input type="hidden" id="creCompId"  name="creCompId"  />
	<input type="hidden" id="creCompNm"  name="creCompNm"  /> 
	<input type="hidden" id="mdlJsonInf" name="mdlJsonInf" />
	
	 
	<div class="stit"><s:message code='DATA.SET.CHC' /></div><!-- 데이터셋선택 코드값으로 수정 181019  -->  
				
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
						<th scope="row"  class="th_require"><s:message code='DATA.SET' /></label></th><!-- 데이터셋 코드값으로 수정 181019 -->
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
			<button class="btn_search"  id="btnAbodColSrch"  name="btnAbodColSrch"><s:message code="INQ"/></button> <!-- 조회 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="stit"><s:message code='CLMN.CHC3' /></div><!-- 데이터셋 명  --><!-- 컬럼 선택 코드값으로 수정 181019 -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="abod_sheet">
		<table>
			<tr>
					<script type="text/javascript">createIBSheet("abod_sheet", "100%", "250px");</script>
			</tr>
			<tr>                               
				<th scope="row"  class="th_require" ><label for="otlColPnm"><s:message code='ANRM.VAL.CLMN.NM' /></label></th> <!-- 이상치 컬럼명 코드값으로 수정 181019-->
				<td>
					<input type="text" name="otlColPnm" id="otlColPnm"  class="wd99p" />
				</td>
			</tr>
		</table>
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 버튼영역 -->
	<div class="divLstBtn" >
		<div class="bt02">  
			<button class="btn_search"  id="abodSelectAll"  name="abodSelectAll"><s:message code="WHL.CHC"/></button> <!-- 전체선택 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:5px;"><span></span></div>
	
	<div class="stit"><s:message code='VRB.SET' /></div><!-- 변수 설정 181019 -->
		 
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
					<th scope="row"  class="th_require" ><label for="otlRt"><s:message code='ANRM.VAL.RATE' /></label></th> <!-- 이상값 비율 181019 -->
					<td>
						<input type="text" name="otlRt" id="otlRt"  class="wd99p" value="0.01" />
					</td>
				</tr> 
			</tbody>
		</table>
	</div>
	
	<div style="clear:both; height:5px;"><span></span></div>
	<p style="width:100%; text-align:right;"><s:message code='MSG.ELEV.RQST.DTL' /></p><!-- default : 0.01 / 0.05보다 작은 값 -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<fieldset>
		<div class="stit"><s:message code='RSLT.DATA.NM' /></div><!-- 결과데이터 명  181019-->
		 
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
						<th scope="row"  class="th_require" ><label for="anaDaseNm"><s:message code='RSLT.DATA.NM' /></label></th> <!-- 분석데이터셋명 --><!-- 결과데이터 명  181019-->
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
				<button class="btn_search"  id="btnAbodSave"  name="btnAbodSave"><s:message code="STRG"/></button> <!-- 저장 -->                      
			</div>
		</div>
		<!-- 버튼영역 end -->
		
		<div style="clear:both; height:10px;"><span></span></div>
	</fieldset>
</form>