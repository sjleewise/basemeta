<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">
//최근 선택 row

$(document).ready(function(){

	//달력팝업 추가...
 	$( "#imActStrDtm" ).datepicker();
	$( "#imActEndDtm" ).datepicker();
	
	$("#shdJobDelRqstYn").attr("disabled", true);
	$("#shdJobDelRqstYn").attr("checked", false);
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_impl.FitColWidth();
});



function initsubgrid_imRslCd() {

    with(grid_sub_imRslCd){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.IMRSL_RQST_DTL'/>", Align:"Center"}
                ];
        //선택|개선활동코드|개선활동유형코드논리명|개선활동유형코드물리명

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Radio",    Width:50,   SaveName:"ibsCheck",    	Align:"Center", Edit:1},
                    {Type:"Text",   Width:140,  SaveName:"codeCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:140,  SaveName:"codeLnm",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:140,  SaveName:"codePnm",    Align:"Center", Edit:0, Hidden:1},
                   
                ];
                    
        InitColumns(cols);
        
        //시트의 헤더 정보를 숨긴다.
        SetRowHidden(0, 1);
        
      	//콤보 목록 설정...
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_imRslCd);    
    //===========================
    //시트의 건수정보 정보를 숨긴다.
  	grid_sub_imRslCd.SetCountPosition(0);
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_impl.ShowDebugMsg(-1);	
    	
}

function initsubgrid_imIlCd() {

    with(grid_sub_imIlCd){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.IMRSL_RQST_DTL2'/>", Align:"Center"}
                ];
        //선택|개선불가코드|개선불가코드논리명|개선불가코드물리명


        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"CheckBox",    Width:50,   SaveName:"ibsCheck", Edit:1, Sort:0},
                    {Type:"Text",   Width:140,  SaveName:"codeCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:140,  SaveName:"codeLnm",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:140,  SaveName:"codePnm",    Align:"Center", Edit:0, Hidden:1}
                   
                ];
                    
        InitColumns(cols);
      	//콤보 목록 설정...
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});
      	
      	//시트의 헤더정보를 숨긴다.
		SetRowHidden(0, 1);
		
      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_imIlCd);    
    //===========================
  	
    //시트의 건수정보 정보를 숨긴다.
  	grid_sub_imIlCd.SetCountPosition(0);
    	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_impl.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//개선결과코드 그리드 초기화
	initsubgrid_imRslCd();
	grid_sub_imRslCd.DoSearch('<c:url value="/dq/impv/impl_rqst_imRslCd.do" />');
	
	//개선불가코드 그리드 초기화
	initsubgrid_imIlCd();
	grid_sub_imIlCd.DoSearch('<c:url value="/dq/impv/impl_rqst_imIlCd.do" />');
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/



function grid_sub_imRslCd_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	var param =  grid_sub_imRslCd.GetRowJson(row);
	
	var ilRow = grid_sub_imRslCd.FindText("codeLnm", "<s:message code='IMPV.IPSB'/>"
, 0);/*개선불가*/
	//원인분석불가 코드 체크시 우측 그리드에 원인분석내역을 그리드에서 선택하게 한다.
	if(grid_sub_imRslCd.GetCellValue(row, "codeLnm") == "<s:message code='IMPV.IPSB'/>"
 && grid_sub_imRslCd.GetCellValue(row, "ibsCheck") == 1) {
		/*개선불가*/
		$("#shdJobDelRqstYn").attr("disabled", false);
		$("#shdJobDelRqstYn").attr("checked", false);
		//$('div[name=csAnaDtlsGrid]').css("display") ="none");
		$("#imActDtlsText").hide();
		$("#imActDtlsGrid").show();
		
	} else {
		$("#shdJobDelRqstYn").attr("disabled", true);
		$("#shdJobDelRqstYn").attr("checked", false);
		$("#imActDtlsGrid").hide();
		$("#imActDtlsText").show();
	}
	

}



function grid_sub_impl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	}
}

function grid_sub_imIlCd_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
	}
}

</script>

<!-- </head> -->
<!-- <body>     -->
 
<div style="clear:both; height:5px;"><span></span></div>

		<!-- 입력폼 시작 -->
	<form id="frmInput" name="frmInput" method="post">
	  <input type="hidden" id="rqstSno" name="rqstSno" value="${result.rqstSno}" >
	  <input type="hidden" id="ibsStatus" name="ibsStatus" value="${saction }" >
<!-- 	  <input type="text" id="bizDtlCd" name="bizDtlCd" 	 value="STWD"> -->
<%-- 	  <input type="hidden" id="stwdId" name="stwdId" value="${result.stwdId}" > --%>
	<fieldset>
	<div id="valid_imrsl">		
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	
	<div style="clear:both; height:20px;"><span></span></div>
	</div>
	<div class="tb_basic3">		
		<div style="float:left; width:49%;">
			<table width="100%" height="150px" border="0" cellspacing="0" cellpadding="0" summary="">
				<caption><s:message code="IMPV.ACVT.REG.DEMD"/></caption><!--개선활동 등록요청-->
			        <colgroup>
			        <col style="width:30%;" />
			        <col style="width:70%;" />
			       </colgroup>
			        <tbody>  
					<tr>
						<th scope="row" class="th_require"><label for="imRslCd"><s:message code="IMPV.RSLT.TY"/></label></th><!--개선결과유형-->
						<td>
							<div id="imRslCdGrid">
							 	<script type="text/javascript">createIBSheet("grid_sub_imRslCd", "100%", "183px");</script>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	
		<div style="float:right; width:50%;">
			<table width="100%" height="150px" border="0" cellspacing="0" cellpadding="0" summary="">
			<caption><s:message code="IMPV.ACVT.DETL"/></caption><!--개선활동내역-->
		        <colgroup>
		        <col style="width:30%;" />
		        <col style="width:40%;" />
		        <col style="width:20%;" />
		        <col style="width:10%;" />
		       </colgroup>
		        <tbody>  
				<tr>
					<th scope="row"><label for=""><s:message code="IMPV.ACVT.DTTM"/></label></th><!--개선활동일시-->

					<td>
						<input id="imActStrDtm" name="imActStrDtm" type="text" class="wd80" value="" readonly/>  - <input id="imActEndDtm" name="imActEndDtm" type="text" class="wd80" value="" readonly/>
					</td>
					<th scope="row"><label for=""><s:message code="SCDU.JOB.DEL.DEMD.YN"/></label></th><!--스케줄작업삭제요청여부-->

					<td><input type="checkbox" id="shdJobDelRqstYn" name="shdJobDelRqstYn" value="Y"></td>
				</tr>
				
				<tr>
					<th scope="row"><label for="imActDtls" id="imActDtls" name="imActDtls"><s:message code="IMPV.ACVT.DETL"/></label></th><!--개선활동내역-->
					<td colspan="3">
						<div id="imActDtlsText">
						<textarea id="imActDtls" name="imActDtls" rows="5" class="wd99p" style="width:99%;height:150px"></textarea>
						</div>
						<div id="imActDtlsGrid" style="display:none"><script type="text/javascript">createIBSheet("grid_sub_imIlCd", "100%", "150px");</script></div>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
	</fieldset>
	</form>
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="otherinfo" style="display:none;"><%@include file="../../meta/stnd/otherinfo.jsp" %></div>
	
	
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divFrmBtnRsl" style="text-align: center;">
		<button class="btn_search" id="btnfrmApplyRsl" name="btnfrmApplyRsl"><s:message code="APL" /></button><!--적용-->


		<button class="btn_search" id="btnfrmResetRsl" name="btnfrmResetRsl" ><s:message code="INON" /></button><!--초기화-->


	</div>
<!-- </body> -->
<!-- </html> -->
