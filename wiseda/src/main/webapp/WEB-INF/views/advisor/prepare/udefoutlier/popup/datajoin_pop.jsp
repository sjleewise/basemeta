<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title><s:message code="CD.TBL.INQ.1"/></title>
	
	<script type="text/javascript">
		$(document).ready(function() {
			//팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
			$("div.pop_tit_close").click(function(){
				//iframe 형태의 팝업일 경우
				if ("${search.popType}" == "I") {
					parent.closeLayerPop();
				} else {
					window.close();
				}
			});
		});
		
		$(window).on('load',function() {
			makeSelBox(${colList}, $("#frmJoinPop #leftAnaVarId"));
			makeSelBox(${colList}, $("#frmJoinPop #rightAnaVarId"));
			$("#frmJoinPop #udfOtlDtcId").val('${search.getUdfOtlDtcId()}');
			$("#frmJoinPop #creCompId").val('${search.getCreCompId()}');
		});
		
		function makeSelBox(jsonlist, selobj) {
			var combosel = selobj; //셋팅한 셀렉트 박스
			
			var codeSplit = jsonlist.ComboCode.split("|");
			var textSplit = jsonlist.ComboText.split("|");
			var cnt = codeSplit.length;
			
			for(i=0; i< cnt; i++){
				combosel.append("<option value='"+codeSplit[i]+"'>"+textSplit[i]+"</option>");
			}
		}
		
		function changeSelBox(anaDaseId, selobj, check) {
			if(anaDaseId == '') {
				$(selobj).find("option").remove();
				selobj.append('<option value=""><s:message code="CHC" /></option>');
				return;
			}
			
			if(check == 'L') {
				$("#leftAnaDaseId").val(anaDaseId);
			} else {
				$("#rightAnaDaseId").val(anaDaseId);
			}
			
			var url = "<c:url value='/advisor/prepare/udefoutlier/datajoin/getSubSelBox.do' />";

			var param = new Object();

			param.anaDaseId = anaDaseId;

			$.ajax({
				url: url,
				async: false,
				type: "POST",
				data: replacerXssParam(param),
				dataType: 'json',
				success: function (data) {
					$(selobj).find("option").remove();
					var cnt = data.length;
					for(i=0; i< cnt; i++){
						selobj.append("<option value='"+data[i].anaVarId+"'>"+data[i].colPnm+"</option>");
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
				}
			});
		}
		
		function doAction(action) {
			switch(action) {
				case "Save":
					var lastIndex = parent.grid_join.GetDataLastRow();
					var lAnaDaseNm = $("#frmJoinPop #leftAnaVarId option:selected").text();
					var rAnaDaseNm = $("#frmJoinPop #rightAnaVarId option:selected").text();
					var lColPnm = $("#frmJoinPop #leftColPnm option:selected").text();
					var rColPnm = $("#frmJoinPop #rightColPnm option:selected").text();
					
					if(lAnaDaseNm == '선택' || rAnaDaseNm == '선택') {
						alert('데이터셋을 선택해주세요.');
						return;
					}
					if(lAnaDaseNm == rAnaDaseNm) {
						alert('Left 데이터셋과 Right 데이터셋이 같습니다.');
						return;
					}
					
					for(i=1; i<=lastIndex; i++) {
						var pLAnaDaseNm = parent.grid_join.GetCellValue(i, 'leftAnaDaseNm');
						var pRAnaDaseNm = parent.grid_join.GetCellValue(i, 'rightAnaDaseNm');
						
						if(lAnaDaseNm != pLAnaDaseNm) {
							alert('Left 데이터셋이 다릅니다.');
							return;
						}
						if(rAnaDaseNm != pRAnaDaseNm) {
							alert('Right 데이터셋이 다릅니다.');
							return;
						}
						
						if(lAnaDaseNm == pLAnaDaseNm
								&& rAnaDaseNm == pRAnaDaseNm
									&& lColPnm == parent.grid_join.GetCellValue(i, 'leftColPnm')
										&& rColPnm == parent.grid_join.GetCellValue(i, 'rightColPnm')) {
							alert('동일한 조인 컬럼이 존재합니다.');
							return;
						}
					}
					
					parent.grid_join.DataInsert(-1);
					lastIndex = parent.grid_join.GetDataLastRow();
					parent.grid_join.SetCellValue(lastIndex, 'leftAnaVarId', $("#frmJoinPop #leftColPnm").val());
					parent.grid_join.SetCellValue(lastIndex, 'leftAnaDaseNm', $("#frmJoinPop #leftAnaVarId option:selected").text());
					parent.grid_join.SetCellValue(lastIndex, 'leftColPnm', $("#frmJoinPop #leftColPnm option:selected").text());
					parent.grid_join.SetCellValue(lastIndex, 'rightAnaVarId', $("#frmJoinPop #rightColPnm").val()); 
					parent.grid_join.SetCellValue(lastIndex, 'rightAnaDaseNm', $("#frmJoinPop #rightAnaVarId option:selected").text());
					parent.grid_join.SetCellValue(lastIndex, 'rightColPnm', $("#frmJoinPop #rightColPnm option:selected").text());
					parent.grid_join.SetCellValue(lastIndex, 'leftAnaDaseId', $("#frmJoinPop #leftAnaDaseId").val()); 
					parent.grid_join.SetCellValue(lastIndex, 'rightAnaDaseId', $("#frmJoinPop #rightAnaDaseId").val()); 
					
					parent.closeLayerPop();
					
					break;
			}
		}
		
	</script>
</head>

<body>
	<form name="frmJoinPop" id="frmJoinPop" method="post" onsubmit="return false;">
		<input	type="hidden" id="udfOtlDtcId" name="udfOtlDtcId" />
		<input	type="hidden" id="creCompId" name="creCompId" />
		<input	type="hidden" id="leftAnaDaseId" name="leftAnaDaseId" />
		<input	type="hidden" id="rightAnaDaseId" name="rightAnaDaseId" />
		
		<div class="pop_tit">
			<!-- 팝업 타이틀 시작 -->
			<div class="pop_tit_icon"></div>
			<div class="pop_tit_txt">조인 추가</div><!-- 조건설정 -->
			<div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->  
		</div>
		<!-- 팝업 타이틀 끝 -->
		
		<div class="stit">조인할 데이터셋/칼럼 선택 (Left)</div>
	
		<div style="clear:both; height:10px;"><span></span></div>
		
		<!-- 더블셀렉트 영역 -->
		<div class="tb_basic" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
				<caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
				<colgroup>
					<col style="width:25%;" />
					<col style="width:*;" />  			  
				</colgroup>
				<tbody>   
					<tr>                               
						<th scope="row"  class="th_require"><s:message code="DATA.SET" /></th><!-- 진단대상명 -->
						<td>
							<select class = "wd100" id="leftAnaVarId"  name="leftAnaVarId" onchange="changeSelBox(this.value, $('#frmJoinPop #leftColPnm'), 'L')">
								<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							</select>
						</td> 			          	          
					</tr>
					<tr>                               
						<th scope="row"  class="th_require"><s:message code="CLMN" /></th><!-- 진단대상명 -->
						<td>
							<select class = "wd100" id="leftColPnm" name="leftColPnm">
								<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							</select>
						</td> 			          	          
					</tr>
				</tbody>
			</table>
		</div>
		<!-- 더블셀렉트 영역 end -->
		
		<div style="clear:both; height:10px;"><span></span></div>
		
		<div class="stit">조인할 데이터셋/칼럼 선택 (Right)</div>
	
		<div style="clear:both; height:10px;"><span></span></div>
		
		<!-- 더블셀렉트 영역 -->
		<div class="tb_basic" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
				<caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
				<colgroup>
					<col style="width:25%;" />
					<col style="width:*;" />  			  
				</colgroup>
				<tbody>   
					<tr>                               
						<th scope="row"  class="th_require"><s:message code="DATA.SET" /></th><!-- 진단대상명 -->
						<td>
							<select class = "wd100" id="rightAnaVarId" name="rightAnaVarId" onchange="changeSelBox(this.value, $('#frmJoinPop #rightColPnm'), 'R')">
								<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							</select>
						</td> 			          	          
					</tr>
					<tr>                               
						<th scope="row"  class="th_require"><s:message code="CLMN" /></th><!-- 진단대상명 -->
						<td>
							<select class = "wd100" id="rightColPnm" name="rightColPnm">
								<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							</select>
						</td> 			          	          
					</tr>
				</tbody>
			</table>
		</div>
		<!-- 더블셀렉트 영역 end -->
		
		<div style="clear:both; height:10px;"><span></span></div>
		
		<!-- 버튼영역  --> 
        <div class="bt01" style="width: 100%;" align="right">
		    <button class="btn_search" id="popSave" name="popSave" onclick="doAction('Save')">확인</button> <!-- 확인 -->
		</div>
	    <!-- 버튼영역  end -->
    </form>
</body>
</html>