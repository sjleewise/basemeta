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
		knnGrid();
		
		var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

		var obj = $("#frmKnn #srcAnaDaseId");
		
		var creCompId   = $("#frmKnn #creCompId").val();
		
		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		$("#btnKnnColSrch").click(function() {
			doKnnAction("Search");
		});
		
		$("#btnKnnSave").click(function() {
			doKnnAction("Save");
		});
		
		$("#knnSelectAll").click(function() {
			doKnnAction("SelectAll");
		});
		
		$("#frmKnn #otlRt").keyup(function(e) {
			$("#frmKnn #otlRt").val(floatCheck($("#frmKnn #otlRt").val(), e.key));
		});
		
		$("#frmKnn #otlRt").change(function() {
			if($("#frmKnn #otlRt").val() > 0.05 || !$("#frmKnn #otlRt").val() || $("#frmKnn #otlRt").val() < 0) {
				//alert("이상값 비율을 조건에 맞게 입력해주세요.");
				showMsgBox("ERR", "<s:message code='MSG.ISFR.RQST.DTL2' />");
				$("#frmKnn #otlRt").val('0.01');
				return;
			};
		});
	});
	
	function compKnn(node) { 
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 
	
	
		$("#frmKnn #creCompId").val(compKeyId);
		$("#frmKnn #creCompNm").val(compHashNm);
		
		//콤포넌트별 div 토글
		toggelDivComp("divCompKnn");
	
		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmKnn #creCompId").val();

		var obj = $("#frmKnn #srcAnaDaseId");

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		getAjaxKnn(udfOtlDtcId, creCompId);
		
		doKnnAction("Search");
	}
	
	function getAjaxKnn(udfOtlDtcId, creCompId){
		var url = "<c:url value='/advisor/prepare/udefoutlier/isolationforest/getUodcKnnDetail.do' />";
	
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
				$("#frmKnn #srcAnaDaseId").val(data.srcAnaDaseId);
				$("#frmKnn #anaDaseNm").val(data.anaDaseNm);
				$("#frmKnn #otlColPnm").val(data.otlColPnm);
				$("#frmKnn #otlRt").val(data.otlRt);
				
				doKnnAction("Search");
			},
			error: function (jqXHR, textStatus, errorThrown) {

				resetForm($("#frmKnn"));

				knnGrid();

				knn_sheet.RemoveAll();

				if($("#frmKnn #otlRt").val() == '') {
					$("#frmKnn #otlRt").val('0.01');
				}
				
				$("#frmKnn #creCompId").val(creCompId);				
			}
		});
	}
	
	function knnGrid()
	{
	    
	    with(knn_sheet){
	    	
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
	    init_sheet(knn_sheet);    
	    //===========================
	}
	
	function doKnnAction(action) {
		switch(action) {
		case "Search":
			var url = "<c:url value='/advisor/prepare/udefoutlier/isolationforest/getUodcKnnColList.do' />";
			
			var param = $("#frmUod, #frmKnn").serialize();

			knn_sheet.DoSearch(url, param);
			
			break;
		case "Save":
			var cnt=0;
			for(i=1; i<=knn_sheet.GetDataLastRow(); i++) {
				if(knn_sheet.GetCellValue(i, 'ibsCheck') == 1)
					cnt++;
			}
			if(cnt == 0) {
				showMsgBox("ERR","<s:message code='MSG.ISFR.RQST.DTL' />");/* 컬럼을 선택해주세요. 코드값으로 수정 181019 */
				return;
			}
			cnt = 0;
			
			if(!$("#frmKnn #otlColPnm").val()) {
				showMsgBox("ERR","<s:message code='ERR.BOXPLOT.RQST.DTL2' />");/* 이상치 컬럼명을 입력해주세요. 코드값으로 수정 181019 */
				return;
			};
			
			if($("#frmKnn #otlRt").val() > 0.05 || !$("#frmKnn #otlRt").val() ||$("#frmKnn #otlRt").val() < 0 ) {
				/* 기존소스_bak 181019 */
				/* alert("이상값 비율을 조건에 맞게 입력해주세요."); */
				
				showMsgBox("ERR","<s:message code='MSG.ISFR.RQST.DTL2' />");
				$("#otlRt").val('0.01');
				return;
			};
			
			if(!$("#frmKnn #anaDaseNm").val()) {
				/* 기존소스_bak 181019 */
				/* alert("결과데이터명을 입력해주세요."); */
				
				showMsgBox("ERR","<s:message code='ERR.BOXPLOT.RQST.DTL' />");
				return;
			}
			
			var url = "<c:url value='/advisor/prepare/udefoutlier/isolationforest/regUodcKnn.do'/>"; 

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmKnn").serialize();
			
			ibsSaveJson = knn_sheet.GetSaveJson({StdCol:'ibsCheck'});
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
		case "SelectAll":
			for(i=1; i<=knn_sheet.GetDataLastRow(); i++) {
				if(knn_sheet.GetCellValue(i, 'dataType') == 'NUMBER' || knn_sheet.GetCellValue(i, 'dataType') == '') {
					knn_sheet.SetCellValue(i, 'ibsCheck', 1);
				} else {
					knn_sheet.SetCellValue(i, 'ibsCheck', 0);
				}
			}
			break;
		}
	}
	
	function knn_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {

		var colNm = knn_sheet.ColSaveName(0, Col); 

		if(colNm == "ibsCheck"  && Value == 1 && RaiseFlag == 0) {
			if(knn_sheet.GetCellValue(Row, 'dataType') != 'NUMBER' && knn_sheet.GetCellValue(i, 'dataType') != '') {
				/* 기존소스_bak 181019 */
				/* alert("데이터 타입이 NUMBER인 컬럼만 선택 가능합니다."); */
				
				showMsgBox("ERR","<s:message code='MSG.BOXPLOT.RQST.DTL' />");
				knn_sheet.SetCellValue(Row, 'ibsCheck', 0);
				return;
			}
		}
	}
</script>

<form name="frmKnn" id="frmKnn" method="post" onsubmit="return false;">
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
			<button class="btn_search"  id="btnKnnColSrch"  name="btnKnnColSrch"><s:message code="INQ"/></button> <!-- 조회 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="stit"><s:message code='CLMN.CHC3' /></div><!-- 데이터셋 명  --><!-- 컬럼 선택 코드값으로 수정 181019 -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="knn_sheet">
		<table>
			<tr>
					<script type="text/javascript">createIBSheet("knn_sheet", "100%", "250px");</script>
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
			<button class="btn_search"  id="knnSelectAll"  name="knnSelectAll"><s:message code="WHL.CHC"/></button> <!-- 전체선택 -->                      
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
				<button class="btn_search"  id="btnKnnSave"  name="btnKnnSave"><s:message code="STRG"/></button> <!-- 저장 -->                      
			</div>
		</div>
		<!-- 버튼영역 end -->
		
		<div style="clear:both; height:10px;"><span></span></div>
	</fieldset>
</form>