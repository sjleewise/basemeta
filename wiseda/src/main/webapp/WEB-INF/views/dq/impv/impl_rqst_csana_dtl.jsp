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
 	$( "#csAnaStrDtm" ).datepicker();
	$( "#csAnaEndDtm" ).datepicker();
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_impl.FitColWidth();
});



function initsubgrid_csAnaCd() {

    with(grid_sub_csAnaCd){
    	
    	var cfg = {SearchMode:2,Page:20, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"impl_rqst_csana_dtl.jsp", Align:"Center"}
                ];
        //선택|원인분석코드|원인분석유형코드논리명|원인분석유형코드물리명


        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"CheckBox",    Width:50,   SaveName:"ibsCheck",    	Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",   Width:140,  SaveName:"codeCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:140,  SaveName:"codeLnm",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:140,  SaveName:"codePnm",    Align:"Center", Edit:0, Hidden:1}
                   
                ];
                    
        InitColumns(cols);
        
        //시트의 헤더 정보를 숨긴다.
        SetRowHidden(0, 1);
        
      	//콤보 목록 설정...
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
        InitComboNoMatchText(1, "");

       FitColWidth();
       SetExtendLastCol(0);
    }
    
   	//==시트설정 후 아래에 와야함=== 
   init_sheet(grid_sub_csAnaCd);    
    //===========================
    //시트의 건수정보 정보를 숨긴다.
  	grid_sub_csAnaCd.SetCountPosition(0);
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_impl.ShowDebugMsg(-1);	
    	
}

function initsubgrid_csAnaIlCd() {

    with(grid_sub_csAnaIlCd){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.IMPL.RQST.CSANA.DTL2'/>", Align:"Center"}
                ];
                //선택|원인분석불가코드|원인분석불가코드논리명|원인분석불가코드물리명

        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"CheckBox",    Width:50,   SaveName:"ibsCheck",    	Align:"Center", Edit:1, Sort:0},
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
    init_sheet(grid_sub_csAnaIlCd);    
    //===========================
  	
    //시트의 건수정보 정보를 숨긴다.
  	grid_sub_csAnaIlCd.SetCountPosition(0);
    	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_impl.ShowDebugMsg(-1);	
    	
}

$(window).on('load',function() {
	//원인분석유형코드 그리드 초기화
	initsubgrid_csAnaCd();
	grid_sub_csAnaCd.DoSearch('<c:url value="/dq/impv/impl_rqst_csAnaCd.do" />');
	//원인분석불가코드 그리드 초기화
	initsubgrid_csAnaIlCd();
	grid_sub_csAnaIlCd.DoSearch('<c:url value="/dq/impv/impl_rqst_csAnaIlCd.do" />');
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/

function grid_sub_csAnaCd_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	var ilRow = grid_sub_csAnaCd.FindText("codeLnm", "<s:message code='CAUS.ANLY.IPSB'/>", 0);/*원인분석불가*/

	//원인분석불가 코드 체크시 우측 그리드에 원인분석내역을 그리드에서 선택하게 한다.
	if(grid_sub_csAnaCd.GetCellValue(row, "codeLnm") == "<s:message code='CAUS.ANLY.IPSB'/>" && grid_sub_csAnaCd.GetCellValue(row, "ibsCheck") == 1) {
		//기존 선택된 모든 체크를 해제하고, 원인분석불가만 체크한다. /*원인분석불가*/

		grid_sub_csAnaCd.CheckAll(0, 0);
		grid_sub_csAnaCd.SetCellValue(ilRow, "ibsCheck", "1");
		//$('div[name=csAnaDtlsGrid]').css("display") ="none");
		$("#csAnaDtlsText").hide();
		$("#csAnaDtlsGrid").show();
		
	} else {
		grid_sub_csAnaCd.SetCellValue(ilRow, "ibsCheck", "0");
		$("#csAnaDtlsGrid").hide();
		$("#csAnaDtlsText").show();
		
	}
}



function grid_sub_csAnaCd_OnSearchEnd(code, message, stCode, stMsg) {
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

function grid_sub_csAnaIlCd_OnSearchEnd(code, message, stCode, stMsg) {
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
	<div id="valid_csana">			
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	<tiles:insertTemplate template="/WEB-INF/decorators/validreview.jsp" />
	<!-- 요청서 공통 부분 (검토결과, 요청구분, 검증결과) -->
	
	<div style="clear:both; height:20px;"><span></span></div></div>
	
	<div id="tblLayer" class="tb_basic3">		
		<div style="float:left; width:49%;">
		<table width="100%" height="150px" border="0" cellspacing="0" cellpadding="0" summary="">
		<caption<s:message code="CAUS.ANLY.DTL.REG.DEMD"/>></caption><!--원인분석상세 등록요청-->

	     <colgroup>
	     <col style="width:30%;" />
	     <col style="width:70%;" />
	    </colgroup>
	     <tbody>  
		<tr>
			<th scope="row" class="th_require"><label for="csAnaCd"><s:message code="CAUS.ANLY.TY"/></label></th><!--원인분석유형-->

			<td width="30%">
				<div id="csAnaCdGrid"  class="grid_01">
				 	<script type="text/javascript">createIBSheet("grid_sub_csAnaCd", "100%", "183px");</script>
				</div>
			</td>
		</tr>
		</tbody>
		</table>
		</div>
	
		<div style="float:right; width:49%;">
			<table width="100%" height="150px" border="0" cellspacing="0" cellpadding="0" summary="">
			<caption<s:message code="CAUS.ANLY.DTL.REG.DEMD"/>></caption><!--원인분석상세 등록요청-->

            <colgroup>
            <col style="width:30%;" />
            <col style="width:70%;" />
           </colgroup>
            <tbody>  
			<tr>
				<th scope="row"><label for=""><s:message code="CAUS.ANLY.DTTM"/></label></th><!--원인분석일시-->

				<td width="30%"><input id="csAnaStrDtm" name="csAnaStrDtm" type="text" class="wd80" value="" readonly/>  - <input id="csAnaEndDtm" name="csAnaEndDtm" type="text" class="wd80" value="" readonly/></td>
			</tr>
			<tr>
				<th scope="row"><label for="csAnaDtls"><s:message code="CAUS.ANLY.DETL"/></label></th><!--원인분석내역-->

				<td width="30%">
					<div id="csAnaDtlsText">
					<textarea id="csAnaDtls" name="csAnaDtls" rows="5" class="wd99p" style="width:99%;height:150px"></textarea>
					</div>
					<div id="csAnaDtlsGrid" style="display:none"><script type="text/javascript">createIBSheet("grid_sub_csAnaIlCd", "100%", "150px");</script></div>
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
	<div id="divFrmBtnCs" style="text-align: center;">
		<button class="btn_search" id="btnfrmApplyCs" name="btnfrmApplyCs"><s:message code="APL" /></button><!--적용-->
<%--         <button class="btn_search" id="btnfrmApplyAllCs" name="btnfrmApplyAllCs"><s:message code="BNDL" /><s:message code="APL" /></button><!--적용--> --%>

		<button class="btn_search" id="btnfrmResetCs" name="btnfrmResetCs" ><s:message code="INON" /></button><!--초기화-->


	</div>
	<div style="clear:both; height:30px;"><span></span></div>
<!-- </body> -->
<!-- </html> -->
