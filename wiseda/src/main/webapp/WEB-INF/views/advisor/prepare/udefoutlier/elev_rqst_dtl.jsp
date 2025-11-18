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
		elevGrid();
		
		var udfOtlDtcId = $("#frmUod #udfOtlDtcId").val();

		var obj = $("#frmElev #srcAnaDaseId");
		
		var creCompId   = $("#frmElev #creCompId").val();
		
		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		$("#btnElevColSrch").click(function() {
			doElevAction("Search");
		});
		
		$("#btnElevSave").click(function() {
			doElevAction("Save");
		});
		
		$("#elevSelectAll").click(function() {
			doElevAction("SelectAll");
		});
		
		$("#frmElev #otlRt").keyup(function(e) {
			$("#frmElev #otlRt").val(floatCheck($("#frmElev #otlRt").val(), e.key));
		});
		
		$("#frmElev #otlRt").change(function() {
			if($("#frmElev #otlRt").val() > 0.05 || !$("#frmElev #otlRt").val() || $("#frmElev #otlRt").val() < 0) {
				
				/* 기존소스_bak 181019 */
				/* alert("이상값 비율을 조건에 맞게 입력해주세요."); */
				//alert("<s:message code='MSG.ISFR.RQST.DTL2' />");
				showMsgBox("ERR", "<s:message code='MSG.ISFR.RQST.DTL2' />");
				$("#frmElev #otlRt").val('0.01');
				return;
			};
		});
	});
	
	function compElev(node) { 
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 
	
	
		$("#frmElev #creCompId").val(compKeyId);
		$("#frmElev #creCompNm").val(compHashNm);
		
		//콤포넌트별 div 토글
		toggelDivComp("divCompElev");
	
		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmElev #creCompId").val();

		var obj = $("#frmElev #srcAnaDaseId");

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
		
		getAjaxElev(udfOtlDtcId, creCompId);
		
		doElevAction("Search");
	}
	
	function getAjaxElev(udfOtlDtcId, creCompId){
		var url = "<c:url value='/advisor/prepare/udefoutlier/elev/getUodcElevDetail.do' />";
	
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
				$("#frmElev #srcAnaDaseId").val(data.srcAnaDaseId);
				$("#frmElev #anaDaseNm").val(data.anaDaseNm);
				$("#frmElev #otlColPnm").val(data.otlColPnm);
				$("#frmElev #otlRt").val(data.otlRt);
				
				doElevAction("Search");
			},
			error: function (jqXHR, textStatus, errorThrown) {
				resetForm($("#frmElev"));

				elevGrid();

				elev_sheet.RemoveAll();
				
				if($("#frmElev #otlRt").val() == '') {
					$("#frmElev #otlRt").val('0.01');
				}

				$("#frmElev #creCompId").val(creCompId);			
			}
		});
	}
	
	function elevGrid()
	{
	    
	    with(elev_sheet){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headtext = "<s:message code='BDQ.HEADER.DATACLEANSING.RQST.DTL4'/>";
			//No.|선택|상태|컬럼명|컬럼한글명|데이터타입
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
	                    {Type:"Text",     Width:100, SaveName:"dataType",	Align:"Center", Edit:0},
	                    
	                ];
	                    
	        InitColumns(cols);
	        
	        //FitColWidth();
	        
	        SetExtendLastCol(1);
	    }
	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(elev_sheet);    
	    //===========================
	}
	
	function doElevAction(action) {
		switch(action) {
		case "Search":
			var url = "<c:url value='/advisor/prepare/udefoutlier/elev/getUodcElevColList.do' />";
			
			var param = $("#frmUod, #frmElev").serialize();

			elev_sheet.DoSearch(url, param);
			
			break;
		case "Save":
			var cnt=0;
			for(i=1; i<=elev_sheet.GetDataLastRow(); i++) {
				if(elev_sheet.GetCellValue(i, 'ibsCheck') == 1)
					cnt++;
			}
			if(cnt == 0) {
				alert("컬럼을 선택해주세요.");
				return;
			}
			cnt = 0;
			
			if(!$("#frmElev #otlColPnm").val()) {
				alert("이상치 컬럼명을 입력해주세요.");
				return;
			};
			
			if($("#frmElev #otlRt").val() > 0.5 || !$("#frmElev #otlRt").val()) {
				alert("이상값 비율을 조건에 맞게 입력해주세요.");
				return;
			};
			
			if(!$("#frmElev #anaDaseNm").val()) {
				alert("결과데이터명을 입력해주세요.");
				return;
			}
			
			var url = "<c:url value='/advisor/prepare/udefoutlier/elev/regUodcElev.do'/>"; 

			$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());

			var param = $("#frmUod, #frmElev").serialize();
			
			ibsSaveJson = elev_sheet.GetSaveJson({StdCol:'ibsCheck'});
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
			break;
		case "SelectAll":
			for(i=1; i<=elev_sheet.GetDataLastRow(); i++) {
				if(elev_sheet.GetCellValue(i, 'dataType') == 'NUMBER' || elev_sheet.GetCellValue(i, 'dataType') == '') {
					elev_sheet.SetCellValue(i, 'ibsCheck', 1);
				} else {
					elev_sheet.SetCellValue(i, 'ibsCheck', 0);
				}
			}
			break;
		}
	}
	
	function elev_sheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) {

		var colNm = elev_sheet.ColSaveName(0, Col);
		
		if(colNm == "ibsCheck" && Value == 1 && RaiseFlag == 0) {
			if(elev_sheet.GetCellValue(Row, 'dataType') != 'NUMBER') {
				alert("데이터 타입이 NUMBER인 컬럼만 선택 가능합니다.");
				elev_sheet.SetCellValue(Row, 'ibsCheck', 0);
				return;
			}
		}
	}
</script>

<form name="frmElev" id="frmElev" method="post" onsubmit="return false;">
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
			<button class="btn_search"  id="btnElevColSrch"  name="btnElevColSrch"><s:message code="INQ"/></button> <!-- 조회 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="stit">컬럼 선택</div><!-- 데이터셋 명  -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="elev_sheet">
		<table>
			<tr>
					<script type="text/javascript">createIBSheet("elev_sheet", "100%", "250px");</script>
			</tr>
			<tr>                               
				<th scope="row"  class="th_require" ><label for="otlColPnm">이상치 컬럼명</label></th> <!-- 이상치 컬럼명 -->
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
			<button class="btn_search"  id="elevSelectAll"  name="elevSelectAll"><s:message code="WHL.CHC"/></button> <!-- 전체선택 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:5px;"><span></span></div>
	
	<div class="stit">변수 설정</div><!-- 변수 설정  -->
		 
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
					<th scope="row"  class="th_require" ><label for="otlRt">이상값 비율</label></th> <!-- 이상값 비율 -->
					<td>
						<input type="text" name="otlRt" id="otlRt"  class="wd99p" value="0.01" />
					</td>
				</tr> 
			</tbody>
		</table>
	</div>
	
	<div style="clear:both; height:5px;"><span></span></div>
	<p style="width:100%; text-align:right;">default : 0.01 / 0.05보다 작은 값</p>
	
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
				<button class="btn_search"  id="btnElevSave"  name="btnElevSave"><s:message code="STRG"/></button> <!-- 저장 -->                      
			</div>
		</div>
		<!-- 버튼영역 end -->
		
		<div style="clear:both; height:10px;"><span></span></div>
	</fieldset>
</form>