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
 	$( "#csAnaStrDtm" ).datepicker();
	$( "#csAnaEndDtm" ).datepicker();
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_impl.FitColWidth();
});



function initsubgrid_csAnaCd() {

    with(grid_sub_csAnaCd){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.IMPL_RQST_IMIL_DTL'/>", Align:"Center"}
                ];
                //선택|원인분석코드|원인분석유형코드논리명|원인분석유형코드물리명

        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"CheckBox",    Width:50,   SaveName:"ibsCheck",    	Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",   Width:140,  SaveName:"codeCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:140,  SaveName:"codeLnm",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:140,  SaveName:"codePnm",    Align:"Center", Edit:0, Hidden:1},
                   
                ];
                    
        InitColumns(cols);
        SetRowHidden(0, 1);
      	//콤보 목록 설정...
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_csAnaCd);    
    //===========================
  	
    //저장 처리 과정을 디버깅 메시지를 팝업으로 표시 (-1)
//     grid_sub_impl.ShowDebugMsg(-1);	
    	
}

function initsubgrid_csAnaIlCd() {

    with(grid_sub_csAnaIlCd){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.IMPL_RQST_IMIL_DTL2'/>", Align:"Center"}
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
		SetRowHidden(0, 1);
      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");
       
      	//히든컬럼 셋팅
//        SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
        FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_csAnaIlCd);    
    //===========================
  	
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
function grid_sub_impl_OnDblClick(row, col, value, cellx, celly) {

	if(row < 1) return;
	
	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
	var objId = grid_sub_impl.GetCellValue(row, "objId");	
	if (grid_sub_impl.GetCellValue(row, "objType") == 'STWD') {
		window.open().location.href = "stwd_lst.do?objId="+objId;	
	} else if (grid_sub_impl.GetCellValue(row, "objType") == 'DMN') {
		window.open().location.href = "dmn_lst.do?objId="+objId;
	} else if (grid_sub_impl.GetCellValue(row, "objType") == 'SDITM') {
		window.open().location.href = "sditm_lst.do?objId="+objId;
	} else if (grid_sub_impl.GetCellValue(row, "objType") == 'TABLE') {
// 		location.href = "stwd_lst.do?objId="+objId;
	} else if (grid_sub_impl.GetCellValue(row, "objType") == 'INFO') {
// 		location.href = "stwd_lst.do?objId="+objId;
	} else if (grid_sub_impl.GetCellValue(row, "objType") == 'SYMN') {
		window.open().location.href = "symn_lst.do?objId="+objId;
	}
	
}

function grid_sub_impl_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	

}

function grid_sub_csAnaCd_OnClick(row, col, value, cellx, celly) {

	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	if (grid_sub_csAnaCd.GetCellValue(row, "ibsCheck") =="0") {
		grid_sub_csAnaCd.SetCellValue(row, "ibsCheck", "1");
	 } else {
		 grid_sub_csAnaCd.SetCellValue(row, "ibsCheck", "0");
	 }
	
	var ilRow = grid_sub_csAnaCd.FindText("codeLnm", "<s:message code='CAUS.ANLY.IPSB'/>", 0);/*원인분석불가*/

	//원인분석불가 코드 체크시 우측 그리드에 원인분석내역을 그리드에서 선택하게 한다.
	if(grid_sub_csAnaCd.GetCellValue(row, "codeLnm") == "<s:message code='CAUS.ANLY.IPSB'/>" && grid_sub_csAnaCd.GetCellValue(row, "ibsCheck") == 1) {
		//기존 선택된 모든 체크를 해제하고, 원인분석불가만 체크한다./*원인분석불가*/
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





function grid_sub_impl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}
</script>

<!-- </head> -->
<!-- <body>     -->
 <!-- 검색조건 입력폼 -->
<div id="search_div">       
    

    
     <!-- 조회버튼영역  -->
<%--     <tiles:insertTemplate template="/WEB-INF/decorators/buttonSub.jsp" /> --%>
     <!-- 조회버튼영역  -->
</div>
 <!-- 검색조건 입력폼 End -->    
<div style="clear:both; height:5px;"><span></span></div>

			
	<div class="tb_basic">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="">
	<caption><s:message code="IMPV.IPSB.DTL.REG.DEMD"/></caption><!--개선불가상세 등록요청-->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   <tbody>  
	<tr>
		<th scope="row" rowspan="2"><label for="csAnaCd"><s:message code="IMPV.IPSB.TY"/></label></th><!--개선불가유형-->
		<td width="30%" rowspan="2"><div id="csAnaCdGrid">
		 	<script type="text/javascript">createIBSheet("grid_sub_csAnaCd", "100%", "200px");</script>
		</div></td>
		<th scope="row" height="20%"><label for=""><s:message code="CAUS.ANLY.DTTM"/></label></th><!--원인분석일시-->

		<td width="30%" height="20%"><input id="csAnaStrDtm" name="csAnaStrDtm" type="text" class="wd80" value="" />  - <input id="csAnaEndDtm" name="csAnaEndDtm" type="text" class="wd80" value=""/></td>
	</tr>
	<tr>
	<th scope="row"><label for="csAnaDtls"><s:message code="CAUS.ANLY.DETL"/></label></th><!--원인분석내역-->

		<td width="30%"><div id="csAnaDtlsText">
			<textarea id="csAnaDtls" name="csAnaDtls" rows="5" class="wd99p" style="width:99%;height:150px"></textarea>
			</div>
			<div id="csAnaDtlsGrid" style="display:none"><script type="text/javascript">createIBSheet("grid_sub_csAnaIlCd", "100%", "150px");</script></div>
			</td>
					
	</tr>
	
	</tbody>
	</table>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divFrmBtn" style="text-align: center;">
		<button id="btnfrmApplyCs" name="btnfrmApplyCs"><s:message code="APL" /></button><!--적용-->
		<button id="btnfrmResetCs" name="btnfrmResetCs" ><s:message code="INON" /></button><!--초기화-->


	</div>
<!-- </body> -->
<!-- </html> -->
