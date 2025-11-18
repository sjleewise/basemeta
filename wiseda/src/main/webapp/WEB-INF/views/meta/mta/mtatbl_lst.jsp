<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>

<script type="text/javascript">
var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
    // 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("Search");  }).show();

    $("#btnExcelDown").hide();      
    // 엑셀내리기 Event Bind
    //$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
    
    $("#btnSearchOrgCd").hide();
	<c:if test='${sessionScope.loginVO.orgLvlUseYn == null or (sessionScope.loginVO.orgLvlUseYn != null and sessionScope.loginVO.orgLvlUseYn eq "Y")}'>
	$("#btnSearchOrgCd,#frmSearch #orgNm").click(function() {
     	$("#frmSearch #orgNm").val("");
    	$("#frmSearch #orgCd").val("");
    	
    	var url = "<c:url value='/meta/admin/popup/popMetaGiCode.do' />";
// 		var popwin = OpenModal(url, "searchpop",  1000, 600, "no");
   		openLayerPop(url, 1000, 600);
   	}).show();
   	</c:if>

    SboxSetLabelEvent();
});

$(window).on('load',function() {
	initGrid();
	loadDetail();
});

$(window).resize(
	    
    function(){

    }
);

var chkinitdtlgrids = false;
function initDtlGrids(){
	if (!chkinitdtlgrids) {
		//컬럼정보 그리드 초기화
	 	initsubgrid_mtacol();
	 	initsubgrid_mtatbl();
	 	
	 	chkinitdtlgrids = true;	
	}
}

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);

		var headtext = "<s:message code='META.HEADER.DBDEFN.LST'/>";
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                    /* No.|기관명|소속기관|정보시스템명|DBMSID|논리DB명|DB명|DBMS종류|DBMS버전|DBLink문자열|연결URL|드라이버명|DB접속계정ID|DB접속계정암호|담당자명|담당자연락처|설명|버전|등록유형|작성일시|작성자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Combo",   Width:200,  SaveName:"topOrgCd",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",   Width:200,  SaveName:"orgCd",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Combo",   Width:200,  SaveName:"infoSysCd",    Align:"Left", Edit:0, Hidden:0},
                     
                    {Type:"Text",   Width:200,  SaveName:"dbConnTrgId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:200,  SaveName:"dbConnTrgLnm",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"dbConnTrgPnm",   Align:"Left", Edit:0}, 
                    {Type:"Combo",  Width:150,   SaveName:"dbmsTypCd",   	 Align:"Center", Edit:0},
                    {Type:"Combo",  Width:150,   SaveName:"dbmsVersCd",   	 Align:"Center", Edit:0},
                    {Type:"Text",   Width:150,  SaveName:"connTrgDbLnkChrw",     Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",   Width:300,  SaveName:"connTrgLnkUrl",     Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",   Width:300,  SaveName:"connTrgDrvrNm",     Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",   Width:120,  SaveName:"dbConnAcId",     Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Pass",   Width:120,  SaveName:"dbConnAcPwd",     Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"crgpNm",     Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"crgpCntel",     Align:"Left", 	 Edit:1, Hidden:1},
                    
                    {Type:"Text",   Width:100,  SaveName:"objDescn",     Align:"Left", 	 Edit:0, Ellipsis:1},
                    {Type:"Text",   Width:130,  SaveName:"objVers",      Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:130,  SaveName:"regTypCd",     Align:"Center", Edit:0, Hidden:1},   
                    {Type:"Date",   Width:130,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:150,  SaveName:"writUserId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"writUserNm",   Align:"Left", Edit:0, Hidden:1}
                ];
        
        InitColumns(cols);

        SetColProperty("topOrgCd", 	${codeMap.orgCdibs});
        SetColProperty("orgCd", 	${codeMap.orgCdibs});
        SetColProperty("infoSysCd", 	${codeMap.infoSysCdibs});

        SetColProperty("dbmsTypCd", 	${codeMap.dbmstypcdibs});
        SetColProperty("dbmsVersCd", 	${codeMap.dbmsverscdibs});
        SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
        
        // FitColWidth();  
		InitComboNoMatchText(1, "");
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//그리드 초기화 한다.
var chkinitdtlgrids = false;
function initDtlGrids(){
	if (!chkinitdtlgrids) {
		
		//if(typeof grid_sub_dbsch != "undefined") initsubgrid_dbsch();
		if(typeof grid_sub_mtatbl != "undefined") initsubgrid_mtatbl();
		
	 	chkinitdtlgrids = true;	
	}
	
}  

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":

       /*  	if(isBlankStr($("#frmSearch #orgNm").val())) {
				showMsgBox("INF", "<s:message code="MSG.ORG.CHC" />");
				return false;
			} */
            
        	//그리드 초기화 한다.
			initDtlGrids();
			var param = $("#frmSearch").serialize();

			//INIT
			$('#program_sel_title').html('');
			loadDetail();
// 			grid_sub_dbsch.RemoveAll();
			grid_sub_mtatbl.RemoveAll();
			
			grid_sheet.DoSearch("<c:url value="/meta/mta/getDbdefnList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
        	//grid_sheet.Down2Text({FileName:'메타데이터.csv', ColDelim:","});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        	
            
            break;
    }       
}

//상세정보호출
function loadDetail(param) {
	
	$('div#detailInfo2').load('<c:url value="/meta/mta/ajaxgrid/dbdefninfo_dtl.do"/>', param, function() {

		
		if(!isBlankStr(param)) {
			//그리드 초기화 한다. 
			initDtlGrids();
			
// 			grid_sub_dbsch.DoSearch('<c:url value="/meta/mta/dbconntrg_dtl.do" />', param);
			grid_sub_mtatbl.DoSearch('<c:url value="/meta/mta/getMtaTbllist.do" />', param);
		}

		loadInfoSysDetail(param);
		
		//$('#tabs').show();
	});
}


function loadInfoSysDetail(param) {

	$('div#detailInfo').load('<c:url value="/meta/mta/ajaxgrid/infosysinfo_dtl.do"/>', param, function(){
	
	});
}

/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	//if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;
	
	//선택한 상세정보를 가져온다...
	var rowData =  grid_sheet.GetRowJson(row);
	var param =   "&dbConnTrgId="+ rowData.dbConnTrgId 
				+ "&orgCd="+ rowData.orgCd
				+ "&infoSysCd="+ rowData.infoSysCd ;

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = "DB명 : " + rowData.dbConnTrgPnm; 
	$('#program_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(param);
}


function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
		
}

function returnOrgCd(orgCd,orgNm,topOrgCd,fullPath){
	$("#frmSearch #orgNm").val(orgNm);
	$("#frmSearch #orgCd").val(orgCd);
}

</script>
</head>

<body>

<div id="layer_div" >

<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="META.DATA.SCH" /></div>
	</div>
</div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />">
                   <caption><s:message code="META.DATA.FORM" /></caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="orgNm"><s:message code="ORG.NM" /></label></th>
                                <td>
                                	<input type="hidden" id="orgCd" name="orgCd" value="${sessionScope.loginVO.orgCd}"/>
                                	<input type="text" id="orgNm" name="orgNm" class="wd80p d_readonly" value="${sessionScope.loginVO.orgNm}" readonly/>
                                	<button class="btn_frm_save" type="button" id="btnSearchOrgCd" name="btnSearchOrgCd"><s:message code="SRCH" /></button> <!-- 검색 -->
                                </td>
                                <th scope="row"><label for="infoSysNm"><s:message code="INFO.SYS.NM" /></label></th>
                                <td><input type="text" id="infoSysNm" name="infoSysNm" class="wd95p" /></td>
                                <%-- <th scope="row"><label for="infoSysCd"><s:message code="INFO.SYS.NM" /></label></th>
		                        <td>
		                        	<div class="sbox wd300">
									<label class="sbox_label" for="infoSysCd"></label>
		                        	<select id="infoSysNm" name="infoSysCd">
										<option value=""><s:message code="WHL" /></option>
										<c:forEach var="code" items="${codeMap.infoSysCd}" varStatus="status">
											<option value="${code.codeLnm}">${code.codeLnm}</option>
										</c:forEach>
									</select>
									</div>
		                        </td> --%>
                            </tr>
                            <tr>
                                <th scope="row"><label for="dbConnTrgLnm"><s:message code="DB.NM" /></label></th>
                                <td colspan="3"><input type="text" id="dbConnTrgLnm" name="dbConnTrgLnm" class="wd98p" /></td>
                               <!--  <th scope="row"><label for="mtaTblLnm">테이블명</label></th>
                                <td><input type="text" id="mtaTblLnm" name="mtaTblLnm" class="wd200" /></td> -->
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
</div>

       <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />         

	<div style="clear:both; height:10px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "280px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	  	<li><a href="#tabs-1"><s:message code="SYS" /></a></li>
	  	<li><a href="#tabs-2"><s:message code="DB.INFO" /></a></li>
	    <li><a href="#tabs-3"><s:message code="TBL.LST" /></a></li>
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	  <div id="tabs-2">
	  	<div id="detailInfo2"></div>
	  </div>
	  <div id="tabs-3">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<%@include file="mtatbllst_dtl.jsp" %>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
	</div>

</body>
</html>

