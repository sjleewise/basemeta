<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<script type="text/javascript">
	function compJoin(node) {
		var compKeyId  = node.data.key;
		var compHashNm = node.data.text; 


		$("#frmJoin #creCompId").val(compKeyId);
		$("#frmJoin #creCompNm").val(compHashNm);

		//콤포넌트별 div 토글
		toggelDivComp("divCompJoin");

		var udfOtlDtcId = $("#udfOtlDtcId").val();
		var creCompId   = $("#frmJoin #creCompId").val();

		var obj = $("#frmJoin #srcAnaDaseId");

		getAnaDaseId(obj, udfOtlDtcId, creCompId);
	
		getAjaxDataJoin(udfOtlDtcId, creCompId);
	}

	function getAjaxDataJoin(udfOtlDtcId, creCompId){
		var url = "<c:url value='/advisor/prepare/udefoutlier/datajoin/getUodcDaseJnDetail.do' />";

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
				$("#frmJoin #anaDaseNm").val(data.anaDaseNm);
				$("#frmJoin #jnTypCd").val(data.jnTypCd);
			},
			error: function (jqXHR, textStatus, errorThrown) {

				resetForm($("#frmJoin"));

				initGrid();

				grid_join.RemoveAll();

				$("#frmJoin #creCompId").val(creCompId);
			}
		});
		
		initGrid();
		doAction("Search");
	}
	
	$(document).ready(function(){
		//추가
		$("#btnJnColAdd").click(function(){
			
			var url = "<c:url value='/advisor/prepare/udefoutlier/popup/datajoin_pop.do' />";
	
			var param = $("#frmUod, #frmJoin").serialize();

			openLayerPop(url, 500,  400, param) ;
		});
		
		//삭제
		$("#btnJnColDel").click(function() {
			doAction("Delete");
		});
		
		//저장
		$("#btnJnSave").click(function(){
			doAction("Apply");
		});
	});
	
	function initGrid()
	{
	    
	    with(grid_join){
	    	
	    	var cfg = {SearchMode:2,Page:100};
	        SetConfig(cfg);
	        
	        var headtext = "<s:message code='BDQ.HEADER.DATAJOIN.RQST.DTL1'/>";
			//No.|상태|선택|leftAnaVarId|데이터셋명|Left Column|rightAnaVarId|데이터셋명|Right Column|leftAnaDaseId|rightAnaDaseId
	        var headers = [
	                    {Text:headtext, Align:"Center"}
	                ];
	        
	        
	        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	        
	        InitHeaders(headers, headerInfo); 
	
	        var cols = [                        
	                    {Type:"Seq",      Width:30,  SaveName:"ibsSeq",    	   Align:"Center", Edit:0},
	                    {Type:"Status",   Width:0,   SaveName:"ibsStatus", 	   Align:"Center", Edit:0, Hidden:1},
	                    {Type:"CheckBox", Width:50,  SaveName:"ibsCheck",  	   Align:"Center", Edit:1},
	                    {Type:"Text",     Width:96,  SaveName:"leftAnaVarId",  Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",     Width:96,  SaveName:"leftAnaDaseNm",  Align:"Left",   Edit:0},
	                    {Type:"Text",     Width:110, SaveName:"leftColPnm",    Align:"Left",   Edit:0},                   
	                    {Type:"Text",     Width:96,  SaveName:"rightAnaVarId", Align:"Left",   Edit:0, Hidden:1},
	                    {Type:"Text",     Width:96,  SaveName:"rightAnaDaseNm", Align:"Left",   Edit:0},
	                    {Type:"Text",     Width:110, SaveName:"rightColPnm",   Align:"Left",   Edit:0},
	                    {Type:"Text",	  Width:0,	 SaveName:"jnColSno",	   Align:"Center", Edit:0, Hidden:1},
	                    {Type:"Text",	  Width:0,	 SaveName:"leftAnaDaseId",	   Align:"Center", Edit:0, Hidden:1},
	                    {Type:"Text",	  Width:0,	 SaveName:"rightAnaDaseId",	   Align:"Center", Edit:0, Hidden:1}
	                ];
	                    
	        InitColumns(cols);
	        
	        FitColWidth();         
	    }
	    
	    //==시트설정 후 아래에 와야함=== 
	    init_sheet(grid_join);    
	    //===========================
	}
	
	function doAction(action){
	
		switch(action){
			case "Delete":

				//삭제할 대상이 없습니다. 
				if(checkDelIBS(grid_join, "<s:message code='ERR.CHKDEL' />")) {

					var url = "<c:url value='/advisor/prepare/udefoutlier/datajoin/delDaseJnCol.do' />";
					
					$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());
	
					var param = $("#frmUod, #frmJoin").serialize(); 
					
					ibsDeleteJson = grid_join.GetSaveJson({StdCol:'ibsCheck'});
					
					IBSpostJson2(url, ibsDeleteJson, param, ibscallback_join );
					
				}
	
				break;
			case "Search":
	
				var url = "<c:url value='/advisor/prepare/udefoutlier/datajoin/getUodcJoinColList.do' />";
				
				var param = $("#frmUod, #frmJoin").serialize();
	
				grid_join.DoSearch(url, param);
				
				break;
			case "Apply":
				if(!valSaveCheckDaseJoin()) return;
				
				var url = "<c:url value='/advisor/prepare/udefoutlier/datajoin/regUodcJoin.do'/>"; 
				
				$("#leftAnaDaseId").val(grid_join.GetCellValue(1, 'leftAnaDaseId'));
				$("#rightAnaDaseId").val(grid_join.GetCellValue(1, 'rightAnaDaseId'));

				$("#frmUod #mdlJsonInf").val(myDiagram.model.toJson());
	
				var param = $("#frmUod, #frmJoin").serialize(); 
				
				ibsSaveJson = grid_join.GetSaveJson(1);   
				
				IBSpostJson2(url, ibsSaveJson, param, ibscallback);
							
				break;
		}
	}
	
	function valSaveCheckDaseJoin(){ 
		if(grid_join.GetDataRows() < 1) {
			showMsgBox("ERR","조인 컬럼을 추가하세요.");
			return false;
		}

		if($("#frmJoin #anaDaseNm").val() == "") { 

			showMsgBox("ERR","분석데이터셋명을 입력하세요.");
			return false;
		}

		return true;
	}
	
	//delCheckIBS() 동작 후 삭제 되지 않은 행이 남은 경우 boolean 값 반환
	function delCheckIBS2(ibsobj) {
		var bRtn = false;
		var iRow = ibsobj.FindCheckedRow("ibsCheck");
		var iRows = iRow.split("|");
		
		for(var i=0; i<iRows.length;i++ ) {
			if ("U" == ibsobj.GetCellValue(iRows[i], "ibsStatus")) {
				bRtn = true;
			}
		}
		
		return bRtn;
	}

	function ibscallback_join(res){

		var result = res.RESULT.CODE;
		
	    if(result == 0) {	    	
			//공통메세지 팝업 : 성공 메세지...
	    	showMsgBox("INF", res.RESULT.MESSAGE);
	    	if (!isBlankStr(cnfNextFunc)) {
	    		eval(cnfNextFunc);
	    		return;
	    	}
	    	//alert(postProcessIBS);
	    	if (postProcessIBS_JOIN != null) {
	    		postProcessIBS_JOIN(res);
	    	}
	    } else if (result == 401) {
	    	//권한이 없어요...
	    	showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
	    } else {
	     	//alsert("저장실패");
			//공통메시지 팝업 : 실패 메세지...
	    	showMsgBox("ERR", res.RESULT.MESSAGE);
	    }
	}
	
	/*======================================================================*/
	//IBSpostJson2 후처리 함수
	/*======================================================================*/
	//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
	function postProcessIBS_JOIN(res) {
		
		
		switch(res.action) {
			//요청서 삭제 후처리...
			case "<%=WiseMetaConfig.IBSAction.DEL%>" :
                 
				for(var i = grid_join.RowCount(); i > 0 ; i--){

		    		if(grid_join.GetCellValue(i,"ibsCheck") == "1"){

		    			grid_join.RowDelete(i,0); 
			    	}		    		
			    }
								
				break;
			//요청서 단건 등록 후처리...
			case "<%=WiseMetaConfig.IBSAction.REG%>" :
				
				break;
			//요청서 저장 및 검증
			case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
				//저장완료시 마스터 정보 셋팅...
				
				
				break;
			//요청서 결재단계별 승인 완료 후처리
			case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
				
				break;
			
			default : 
				// 아무 작업도 하지 않는다...
				break;
				
		}
		
	}

	
	
</script>

<form name="frmJoin" id="frmJoin" method="post" onsubmit="return false;">
	<input type="hidden" id="creCompId"  name="creCompId"  />
	<input type="hidden" id="creCompNm"  name="creCompNm"  /> 
	<input type="hidden" id="leftAnaDaseId"  name="leftAnaDaseId"  />
	<input type="hidden" id="rightAnaDaseId"  name="rightAnaDaseId"  /> 
	<!-- <input type="hidden" id="mdlJsonInf" name="mdlJsonInf" />
	<input type="hidden" id="impDataDcd" name="impDataDcd" /> -->
	
	
	<div class="stit">조인 컬럼</div><!-- 조인 컬럼  -->  
	
	<!-- 그리드 입력 -->
	<div class="grid_01" id="grid_jncol">  
		<script type="text/javascript">createIBSheet("grid_join", "100%", "300px");</script>             
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 버튼영역 -->
	<div class="divLstBtn" >  
		<div class="bt03"> 
		</div>
		
		<div class="bt02">  
			<button class="btn_search"  id="btnJnColAdd"  name="btnJnColAdd"><s:message code="ADDT"/></button> <!-- 추가 -->   
			<button class="btn_search"  id="btnJnColDel"  name="btnJnColDel"><s:message code="DEL" /></button> <!-- 삭제 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	
	<div class="stit">조인 타입</div><!-- 조인 타입  -->
	
	<div style="clear:both; height:10px;"><span></span></div>
	
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
						<th scope="row"  class="th_require">조인 타입</label></th>
						<td>
							<select id="jnTypCd" class="" name="jnTypCd">
								<option value="01">Inner</option>
								<option value="02">Left</option>
								<option value="03">Right</option>
								<option value="04">Full Outer</option>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</fieldset>
	
	<div style="clear:both; height:10px;"><span></span></div>	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="stit">데이터셋 명</div><!-- 데이터셋 명  -->
	
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
					<!-- <tr>
						<input type="radio" name="saveTyp" value="left"> 데이터셋 업데이트(left)<br>
						<input type="radio" name="saveTyp" value="right"> 데이터셋 업데이트(right)<br>
						<input type="radio" name="saveTyp" value="new" checked> 새 데이터셋<br><br>
					</tr> -->
					
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
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 버튼영역 -->
	<div class="divLstBtn" >  
		<div class="bt03"> 
		</div>
		
		<div class="bt02">  
			<button class="btn_search"  id="btnJnSave"  name="btnJnSave"><s:message code="STRG"/></button> <!-- 저장 -->                      
		</div>
	</div>
	<!-- 버튼영역 end -->
</form>