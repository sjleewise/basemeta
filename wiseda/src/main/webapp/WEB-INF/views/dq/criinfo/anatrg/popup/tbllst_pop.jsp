<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="TBL.NM.INQ"/></title><!--테이블명 조회-->


<script type="text/javascript">
var interval = "";

//page 구분 
var pageFlag = "";

$(document).ready(function() {
	//page 구분
	pageFlag = "${search.sflag}";

	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
 	
    // 조회 Event Bind
    $("#btnAnaTrgSearch").click(function(){ doAction("SearchAnaTrgTbl");  });

    $("form#frmSearch #schDbConnTrgId").val("${search.dbConnTrgId}");
    $("form#frmSearch #schDbSchNm").val("${search.dbSchPnm}");
    
    if($("form#frmSearch #schDbConnTrgId").val() != "" ){
        // $("select[name=schDbConnTrgId]").attr('disabled', true);
    }
	
    //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #schDbSchNm"), "DBSCH");
   	setautoComplete($("#frmSearch #schDbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #schDbcColNm"), "DBCCOL");
});


//엔터키 처리한다.
EnterkeyProcess("SearchAnaTrgTbl");


$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	$(window).resize();
});

$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});

	
function initGrid()
{
   
	//진단대상 테이블 grid
    with(grid_tbl){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.TBLLST.POP'/>", Align:"Center"}
                ];
        //No|주제영역ID|주제영역명|진단대상ID|진단대상물리명|진단대상명|스키마ID|스키마명|테이블명|테이블한글명

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"dbConnTrgLnm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"dbSchLnm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"schDbcTblNm",    	Align:"Left", Edit:0, Hidden:1}
                    
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);
        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    //==시트설정 후 아래에 와야함===
	init_sheet(grid_tbl);    
   
}	

function doAction(sAction, param)
{
        
    switch(sAction)
    {
       	/*진단대상 테이블 조회*/
        case "SearchAnaTrgTbl":
        	param = "";
        	param += "&tblColGb=PT";
        	param += "&"+$('form[name=frmSearch]').serialize();
        	grid_tbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getPrfTblLstNotRedline.do" />", param);
        	break;
        	
    }       
}

//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function postProcessIBS(res) {
	//요청번호 셋팅
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			//테이블 프로파일 리스트 조회
			getPrfLst("PT");
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			//프로파일 ID 초기화
			$("form[name=frmAnaTrg] #prfId").val("");
			$("form[name=frmAnaTrg] #regTypCd").val("");
		
			 //입력폼 form reset
			 doAction("DtlReset");
			//테이블 프로파일 리스트 조회
			getPrfLst("PT");
			
			break;
		//여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			break;
		default : 
			// 아무 작업도 하지 않는다...
			break;			
	}
}

// function loadTblDetail(param){
// 	ajax2Json('<c:url value="/dq/profile/getAnaTrgTblDetail.do"/>', param, setAnaTgtDtl);
// }

function grid_tbl_OnDblClick(row, col, value, cellx, celly){
	if(row < 1) return;
	
	//테이블명 검색
	 if(pageFlag == "TBLLST"){
	 $("form#frmDbDetail #dbcTblNm", opener.document).val(grid_tbl.GetCellValue(row,"dbcTblNm"));
	 $("form#frmDbDetail #dbConnTrgId", opener.document).val(grid_tbl.GetCellValue(row,"dbConnTrgId"));
	 $("form#frmDbDetail #dbConnTrgPnm", opener.document).val(grid_tbl.GetCellValue(row,"dbConnTrgPnm"));
	 $("form#frmDbDetail #dbSchId", opener.document).val(grid_tbl.GetCellValue(row,"dbSchId"));
	 $("form#frmDbDetail #dbSchLnm", opener.document).val(grid_tbl.GetCellValue(row,"dbSchLnm"));
	 }
	//검증테이블명 검색
	 if(pageFlag == "VRTTBL"){
	 $("form#frmVrtDetail #tgtDbcTblNm", opener.document).val(grid_tbl.GetCellValue(row,"dbcTblNm"));
	 $("form#frmVrtDetail #tgtDbConnTrgId", opener.document).val(grid_tbl.GetCellValue(row,"dbConnTrgId"));
	 $("form#frmVrtDetail #tgtDbConnTrgPnm", opener.document).val(grid_tbl.GetCellValue(row,"dbConnTrgPnm"));
	 $("form#frmVrtDetail #tgtDbSchLnm", opener.document).val(grid_tbl.GetCellValue(row,"dbSchLnm"));
	 $("form#frmVrtDetail #tgtDbSchPnm", opener.document).val(grid_tbl.GetCellValue(row,"dbSchPnm"));
	 }
	
	window.close();
}

function grid_tbl_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	
}

//진단대상 테이블 조회 오류
function grid_tbl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0 ) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		doAction("Reset");
	}
}

</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="TBL.NM.INQ"/></div><!--테이블명 조회-->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->
</div>
    <!-- 팝업 타이틀 끝 -->
</div>
<!-- 팝업 내용 시작 -->
		<div class="pop_content">
			<!-- 검색조건 입력폼 -->
			<div id="search_div">
				<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
	        <div style="clear:both; height:5px;"><span></span></div>
	        
	        <form id="frmSearch" name="frmSearch" method="post">
	            <fieldset>
	            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	            <div class="tb_basic2" >
	                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='TBL.PROF.MNG'/>"><!-- /*테이블프로파일관리*/
 -->
	                   <caption><s:message code="TBL.INQ"/></caption><!--테이블 조회-->

	                   <colgroup>
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   </colgroup>
	                   
	                   <tbody>                            
	                       <tr>                               
	                           <th scope="row"><label for="schDbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->


	                           <td>
	                           		<select id="schDbConnTrgId"  name="schDbConnTrgId" >
	                           		<option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
	                           		</select>
	                           </td>
	                       </tr>
	                       	<tr>                               
	                           <th scope="row"><label for="schDbSchNm"><s:message code="SCHEMA.NM" /></label></th><!--스키마명-->

	                           <td>
	                               <input type="text" name="schDbSchNm" id="schDbSchNm"  value="${result.dbSchPnm }" class="wd200"/>
	                           </td>
	                       </tr>
	                       <tr>
	                       		<th scope="row"><label for="schDbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

	                       		<td>
	                       			<input type="text" name="schDbcTblNm" id="schDbcTblNm"  class="wd200"/>
	                       		</td>
	                       </tr>
	                       <tr>                               
	                           <th scope="row"><label for="schDbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

	                           <td>
	                               <input type="text" name="schDbcColNm" id="schDbcColNm"  class="wd200"/>
	                           </td>
	                       </tr>
<!-- 	                       <tr>                                -->
<!-- 	                           <th scope="row"><label for="schRegYn"><s:message code="REG.YN" /></label></th>등록여부-->
<!-- 	                           <td> -->
<%-- 	                           		<select id="schRegYn"  name="schRegYn"> --%>
<!-- 	                           			<option value=""><s:message code="WHL" /></option><전체-->
 
<!-- 	                           			<option value="Y">Y</option> -->
<!-- 	                           			<option value="N">N</option> -->
<%-- 	                           		</select> --%>
<!-- 	                           </td> -->
<!-- 	                       </tr> -->
	                   </tbody>
	                 </table>   
	            </div>
	            </fieldset>
	            
	        </form>
	</div>
	
    <!-- 조회버튼영역  -->
	<div style="clear:both; height:10px;"><span></span></div>
	<div id="divFrmBtn" style="text-align: left;">
		<button class="btn_search" id="btnAnaTrgSearch" 	name="btnAnaTrgSearch"><s:message code="INQ"/></button> <!-- 조회 -->
	</div>
	
	<div style="clear:both; height:5px;"><span></span></div>
	        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_tbl", "100%", "350px");</script>            
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
	
	
</div>

	<div style="clear:both; height:10px;"><span></span></div>
	

</body>
</html>