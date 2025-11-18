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
 	$( "#imPlStrDtm" ).datepicker();
	$( "#imPlEndDtm" ).datepicker();
	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
// 	grid_sub_impl.FitColWidth();
});



function initsubgrid_imPlCd() {

    with(grid_sub_imPlCd){
    	
    	var cfg = {SearchMode:2,Page:100, DragMode:0};
//     	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.IMPL_RQST_IMPL_DTL'/>", Align:"Center"}
                ];

                //선택|개선계획코드|개선계획코드논리명|개선계획코드물리명

        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"CheckBox",    Width:50,   SaveName:"ibsCheck",    	Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",   Width:140,  SaveName:"codeCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:140,  SaveName:"codeLnm",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:140,  SaveName:"codePnm",    Align:"Center", Edit:0, Hidden:1},
                   
                ];
                    
        InitColumns(cols);
        
        //시트의 헤더정보를 숨긴다.
        SetRowHidden(0, 1);
      	//콤보 목록 설정...
// 	    SetColProperty("commDcdId", 	${codeMap.commDcdIdibs});

      	//콤보코드일때 값이 없는 경우 셋팅값
//         InitComboNoMatchText(1, "");

      	FitColWidth();
        SetExtendLastCol(1);
    }
    
   	//==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sub_imPlCd);    
    //===========================

    //시트의 건수정보를 숨긴다.
    grid_sub_imPlCd.SetCountPosition(0);

}


$(window).on('load',function() {
	
	//개선계획코드 그리드 초기화
	initsubgrid_imPlCd();
	grid_sub_imPlCd.DoSearch('<c:url value="/dq/impv/impl_rqst_imPlCd.do" />');
	
});
	 



/*
row : 행의 index
col : 컬럼의 index
value : 해당 셀의 value
x : x좌표
y : y좌표
*/

function grid_sub_imPlCd_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
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

			
	<div class="tb_basic3">
	<div style="float:left; width:49%;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BZWR.RULE.INQ' />"> <!--업무규칙 조회-->

	<caption><s:message code="IMPV.PLAN.DTL.REG.DEMD"/></caption><!--개선계획상세 등록요청-->
                   <colgroup>
                   <col style="width:30%;" />
                   <col style="width:70%;" />
                  </colgroup>
                   <tbody>  
	<tr>
		<th scope="row" class="th_require"><label for="imPlCd"><s:message code="IMPV.PLAN.TY"/></label></th><!--개선계획유형-->
		<td width="30%"><div id="imPlCdGrid">
		 	<script type="text/javascript">createIBSheet("grid_sub_imPlCd", "100%", "183px");</script>
		</div></td>
		
	</tr>
	</tbody>
	</table>
	</div>
	
	<div style="float:right; width:49%;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='BZWR.RULE.INQ' />"> <!--업무규칙 조회-->

	<caption><s:message code="IMPV.PLAN.DTL.REG.DEMD"/></caption><!--개선계획상세 등록요청-->
                   <colgroup>
                   <col style="width:30%;" />
                   <col style="width:70%;" />
                  </colgroup>
                   <tbody>  
	<tr>
	<th scope="row" height="20%"><label for=""><s:message code="IMPV.PLAN.DTTM"/></label></th><!--개선계획일시-->
		<td width="30%" height="20%"><input id="imPlStrDtm" name="imPlStrDtm" type="text" class="wd80" value="" readonly/>  - <input id="imPlEndDtm" name="imPlEndDtm" type="text" class="wd80" value="" readonly/></td>
		</tr>
		<tr>
		
	<th scope="row"><label for="imPlDtls"><s:message code="IMPV.PLAN.DETL"/></label></th><!--개선계획내역-->
		<td width="30%">
			<textarea id="imPlDtls" name="imPlDtls" rows="5" class="wd99p" style="width:99%;height:150px"></textarea>
			</td>
					
	</tr>
	
	</tbody>
	</table>
	</div>
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divFrmBtnIm" style="text-align: center;">
		<button class="btn_search" id="btnfrmApplyIm" name="btnfrmApplyIm"><s:message code="APL" /></button><!--적용-->
<%--         <button class="btn_search" id="btnfrmApplyAllIm" name="btnfrmApplyAllIm"><s:message code="BNDL" /><s:message code="APL" /></button><!--적용--> --%>
		<button class="btn_search" id="btnfrmResetIm" name="btnfrmResetIm" ><s:message code="INON" /></button><!--초기화-->


	</div>
<!-- </body> -->
<!-- </html> -->
