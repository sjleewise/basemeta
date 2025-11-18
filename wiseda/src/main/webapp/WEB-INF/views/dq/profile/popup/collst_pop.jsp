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
<title></title>

<script type="text/javascript">
var interval = "";

//page 구분 
var pageFlag = "";

$(document).ready(function() {
	//page 구분
	pageFlag = "${search.sflag}";
	
	//그리드 초기화 
	initGrid();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
    // 조회 Event Bind
    $("#btnAnaTrgSearch").click(function(){doAction("SearchAnaTrgCol");});
    
    $("form#frmSearch #schDbConnTrgId").val("${search.dbConnTrgId}");
    $("form#frmSearch #schDbSchNm").val("${search.dbSchPnm}");
    $("form#frmSearch #schDbcTblNm").val("${search.dbcTblNm}");
    
    if($("form#frmSearch #schDbConnTrgId").val() != "" ){$("select[name=schDbConnTrgId]").attr('disabled', true);}
    if($("form#frmSearch #schDbSchNm").val() != "" ){$("input[name=schDbSchNm]").attr('readOnly', true);}
    if($("form#frmSearch #schDbcTblNm").val() != "" ){$("input[name=schDbcTblNm]").attr('readOnly', true);}
    
    //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	setautoComplete($("#frmSearch #schDbSchNm"), "DBSCH");
   	setautoComplete($("#frmSearch #schDbcTblNm"), "DBCTBL");
	setautoComplete($("#frmSearch #schDbcColNm"), "DBCCOL");
});


//엔터키 처리한다.
EnterkeyProcess("SearchAnaTrgCol");

$(window).on('load',function() {
	  //그리드 가로 스크롤 방지
	grid_col.FitColWidth();
	$(window).resize();
	  
	  
});

$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));
	// grid_sheet.SetExtendLastCol(1);    
});
	
function initGrid()
{
   	with(grid_col){
   		
   		var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);
           
           var headers = [
                       {Text : "<s:message code='DQ.HEADER.COLLST_POP'/>", Align : "Center"	}
                   ];
           //선택|No|상태|Position|진단대상ID|진단대상물리명|진단대상명|스키마ID|스키마명|테이블명|테이블한글명|컬럼명|컬럼한글명|Pk여부|Data Type|Null여부|Default

           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                   {Type:"CheckBox", Width:70,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:1, Sort:0},
	   				   {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0, Hidden:0},
	   	               {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:70,  SaveName:"ord",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgLnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
	                   {Type:"Text",   Width:100,  SaveName:"dbSchLnm",    	Align:"Left", Edit:0, Hidden:1}, 
	                   
	                   {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:120,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
	                   {Type:"Text",   Width:120,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0},
	                   {Type:"Text",   Width:70,  SaveName:"pkYn",    	Align:"Center", Edit:0},
	                   {Type:"Text",   Width:100,  SaveName:"dataType",    	Align:"Left", Edit:0},
	                   {Type:"Text",   Width:80,  SaveName:"nullYn",    	Align:"Left", Edit:0, Hidden:0},
	                   {Type:"Text",   Width:80,  SaveName:"defltVal",    	Align:"Left", Edit:0, Hidden:0},
	                   {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:100,  SaveName:"schDbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
	                   {Type:"Text",   Width:100,  SaveName:"schDbSchNm",    	Align:"Left", Edit:0, Hidden:1}
               ];
       
       //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
       InitColumns(cols);

       InitComboNoMatchText(1, "");
       
       //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
       SetExtendLastCol(1); 
       
       FitColWidth();
   
   	}
	
    //==시트설정 후 아래에 와야함===
    init_sheet(grid_col);    
    //===========================	
}	

function doAction(sAction, param)
{
        
    switch(sAction)
    {
       	/*진단대상 컬럼 조회*/
        case "SearchAnaTrgCol":
        	param = $('form[name=frmSearch]').serialize();
        	param += "&schDbConnTrgId=" + $("#schDbConnTrgId").val();
        	grid_col.DoSearch("<c:url value="/dq/criinfo/anatrg/getAnaTrgColLst.do" />", param);
        	break;
    }       
}

function grid_col_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_col_OnDblClick(row, col, value, cellx, celly){
	if(row < 1) return;
	
	//컬럼명 검색
	 if(pageFlag == "COLLST"){
	 $("form#frmDbDetail #dbConnTrgId", opener.document).val(grid_col.GetCellValue(row,"dbConnTrgId"));
	 $("form#frmDbDetail #dbConnTrgPnm", opener.document).val(grid_col.GetCellValue(row,"dbConnTrgPnm"));
	 $("form#frmDbDetail #dbConnTrgLnm", opener.document).val(grid_col.GetCellValue(row,"dbConnTrgLnm"));
	 $("form#frmDbDetail #dbSchid", opener.document).val(grid_col.GetCellValue(row,"dbSchid"));
	 $("form#frmDbDetail #dbSchLnm", opener.document).val(grid_col.GetCellValue(row,"dbSchLnm"));
	 $("form#frmDbDetail #dbcTblNm", opener.document).val(grid_col.GetCellValue(row,"dbcTblNm"));
	 $("form#frmDbDetail #dbcColNm", opener.document).val(grid_col.GetCellValue(row,"dbcColNm"));
	 }
	//검증컬럼명 검색
	 if(pageFlag == "VRTCOL"){
	 $("form#frmVrtDetail #tgtDbConnTrgId", opener.document).val(grid_col.GetCellValue(row,"dbConnTrgId"));
	 $("form#frmVrtDetail #tgtDbConnTrgPnm", opener.document).val(grid_col.GetCellValue(row,"dbConnTrgPnm"));
	 $("form#frmVrtDetail #tgtDbSchLnm", opener.document).val(grid_col.GetCellValue(row,"dbSchLnm"));
	 $("form#frmVrtDetail #tgtDbSchPnm", opener.document).val(grid_col.GetCellValue(row,"dbSchPnm"));
	 $("form#frmVrtDetail #tgtDbcTblNm", opener.document).val(grid_col.GetCellValue(row,"dbcTblNm"));
	 $("form#frmVrtDetail #tgtDbcColNm", opener.document).val(grid_col.GetCellValue(row,"dbcColNm"));
	 }
	
	window.close();
}

//진단대상 컬럼 조회 오류
function grid_col_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code  < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		//상세 화면 RESET
		//doAction("DtlReset");
	}
}

</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="CLMN.NM.INQ"/></div><!--컬럼명 조회-->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->S


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
	        	<input type="hidden" id="schDbConnTrgLdm" name="schDbConnTrgLdm" value="${result.dbConnTrgLnm}"/>
	            <fieldset>
	            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	            <div class="tb_basic2" >
	                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.PROF.MNG'/>">
	                   <caption><s:message code="CLMN.PROF.MNG"/></caption><!--컬럼프로파일관리-->

	                   <colgroup>
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   </colgroup>
	                   
	                   <tbody>                            
	                       <tr>                               
	                           <th scope="row"><label for="schDbConnTrgId"><s:message code="DIAG.TRGT.NM" /></label></th><!--진단대상명-->


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
	                               <input type="text" name="schDbSchNm" id="schDbSchNm" value="${result.dbSchPnm }" class="wd240"/>
	                           </td>
	                       </tr>
	                       <tr>
	                       		<th scope="row"><label for="schDbcTblNm" ><s:message code="TBL.NM" /></label></th>	<!--테이블명-->

	                       		<td>
	                       			<input type="text" name="schDbcTblNm" id="schDbcTblNm" value="${result.dbcTblNm}" class="wd240"/>
	                       		</td>
	                       </tr>
	                       <tr>                               
	                           <th scope="row"><label for="schDbcColNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->
	                           <td>
	                               <input type="text" name="schDbcColNm" id="schDbcColNm" value="${search.dbcColNm}" class="wd240" />
	                           </td>
	                       </tr>
<!-- 	                       <tr>                                -->
<!-- 	                           <th scope="row"><label for="schRegYn"><s:message code="REG.YN" /></label></th><-->
<!-- 	                           <td> -->
<%-- 	                           		<select id="schRegYn"  name="schRegYn"> --%>
<!-- 	                           			<option value=""><s:message code="WHL" /></option> -->
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
		<button class="btn_search" id="btnAnaTrgSearch" 	name="btnAnaTrgSearch"><s:message code="INQ"/><!--조회-->
 
	</div>
	
	<div style="clear:both; height:5px;"><span></span></div>
	        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_col", "99%", "450px");</script>            
	</div>
	
</div>

</body>
</html>