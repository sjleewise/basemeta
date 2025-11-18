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
EnterkeyProcess("Search");
var crow = "";
$(document).ready(function() {
    // 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("Search");  }).show();

    $("#btnExcelDown").hide();      

	//폼 초기화 버튼 초기화...
	$('#btnReset').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		grid_sheet.SetCellValue(crow, "rvwConts", "");
		crow = grid_sheet.GetSelectRow();
		$("#rvwConts").val('');
	}); 

	$('#btnGridSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE.DDL" />";
		showMsgBox("CNF", message, 'SaveDDL');
	});


	$("#btnSearchOrgCd").hide();
	<c:if test="${sessionScope.loginVO.orgLvlUseYn == null or (sessionScope.loginVO.orgLvlUseYn != null and sessionScope.loginVO.orgLvlUseYn eq 'Y')}">
		$("#frmSearch #orgNm, #btnSearchOrgCd").click(function() {
	    	$("#frmSearch #orgCd").val("");
	    	$("#frmSearch #orgNm").val("");
	    	var url = "<c:url value='/meta/admin/popup/popMetaGiCode.do' />";
			//var popwin = OpenModal(url, "searchpop",  1000, 600, "no");
	    		openLayerPop(url, 1000, 600);
	   	}).show();
	</c:if>

   	$("#btnCloseBottom").hide();

});

$(window).on('load',function() {
	initGrid();
});

$(window).resize(
	    
);

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);

        var headtext = "<s:message code='META.HEADER.MTATBL.LST'/>";
        //var headtext = "No.|기관|정보시스템|DB명|테이블소유자|테이블명|테이블한글명|테이블유형|엔티티명|테이블설명|주제영역|태깅정보|보존기간|테이블볼륨|예상발생량|테이블생성일|비공개사유|개방데이터목록|갱신주기|테이블ID";
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:80,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
                    {Type:"Combo",  Width:200,  SaveName:"orgCd",    	Align:"Left",   Edit:0, Hidden:0},
					{Type:"Combo",  Width:200,  SaveName:"infoSysCd",   Align:"Left",   Edit:0, Hidden:0},
					{Type:"Combo",  Width:150,  SaveName:"dbConnTrgId", Align:"Left",   Edit:0},
					{Type:"Combo",  Width:150,  SaveName:"dbSchId",    	Align:"Left",   Edit:0},
					{Type:"Text",   Width:150,  SaveName:"mtaTblPnm",   Align:"Left",   Edit:0},
					{Type:"Text",   Width:200,  SaveName:"mtaTblLnm",   Align:"Left",   Edit:0},
					{Type:"Text",   Width:90,   SaveName:"tblTypNm"	,   Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:90,   SaveName:"relEntyNm",   Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"objDescn" ,   Align:"Left",   Edit:0},
					
					{Type:"Text",   Width:100,  SaveName:"subjNm"	,   Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"tagInfNm"	,   Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:90,   SaveName:"prsvTerm"	,   Align:"Left",   Edit:0, Hidden:1},
                    
					{Type:"Text",   Width:90,   SaveName:"tblVol"	,   Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:90,   SaveName:"exptOccrCnt", Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"tblCreDt"	,   Align:"Center", Edit:0, Format:"Ymd", Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"nopenRsn"	,   Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"openDataLst", Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:90,   SaveName:"occrCyl"	,   Align:"Left",   Edit:0, Hidden:1},
					
					{Type:"Text",   Width:60,   SaveName:"mtaTblId"	,   Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"dbSchPnm"	,   Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"dbmsTypCd",   Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"rvwConts"	,   Align:"Center", Edit:0, Hidden:1},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
		SetColProperty("orgCd", ${codeMap.orgCdibs});
		SetColProperty("infoSysCd", 	${codeMap.infoSysCdibs});
		SetColProperty("dbConnTrgId", 	${codeMap.connTrgSchibs});
		SetColProperty("dbSchId", ${codeMap.connTrgSchibs});

      	InitComboNoMatchText(1, "");
        //SetColHidden("rqstUserNm",1);

//         FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	var orgNm = $("#frmSearch #orgNm").val();
        	$('#btnReset').click();
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/mta/getMtaTbllist.do" />", param);
        	break;

        case "SaveDDL":

            var param = $("#frmInput").serialize();
			var url  = "<c:url value='/meta/mta/regDbChg.do'/>";
			ajax2Json(url, param, callback);
            break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":  //엑셀업로
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }       
}

function returnMtaColPop(ret, mtaTblLnmAf){
	var retjson = jQuery.parseJSON(ret);
	var dbms = grid_sheet.GetCellValue(crow, "dbmsTypCd");
	var tblNm = grid_sheet.GetCellValue(crow, "mtaTblPnm");
	var dbSchPnm = grid_sheet.GetCellValue(crow, "dbSchPnm");
	var clctSql = "";

	// 테이블 변경
	if(mtaTblLnmAf != ""){
		switch(dbms){
			case "MSQ" :
				if(grid_sheet.GetCellValue(crow, "mtaTblLnm") == ""){
					clctSql += "EXEC sp_addextendedproperty 'MS_Description', '" + mtaTblLnmAf + "', 'user', " + dbSchPnm 
							+ ", 'table', " + tblNm;
				}else{
					clctSql += "EXEC sp_updateextendedproperty 'MS_Description', '" + mtaTblLnmAf + "', 'user', " + dbSchPnm 
							+ ", 'table', " + tblNm;
				}
				break;
			case "CBR" :
				clctSql += "MERGE INTO _cub_schema_comments A USING ("
					+ " SELECT '" + tblNm + "' AS table_name, '*' AS column_name, '" + mtaTblLnmAf + "' AS description, '" + dbSchPnm + "' AS last_updated_user"
					+ " FROM db_root ) B ON (A.table_name = B.table_name AND A.column_name = B.column_name AND A.last_updated_user = B.last_updated_user)"
					+ " WHEN MATCHED THEN"
					+ " UPDATE SET description = B.description, last_updated = SYSDATETIME"
					+ " WHEN NOT MATCHED THEN"
					+ " INSERT ( table_name, column_name, description, last_updated, last_updated_user )"
					+ " VALUES ( B.table_name, B.column_name, B.description, SYSDATETIME, B.last_updated_user )";
				break;
			case "MYS" :
				clctSql += "ALTER TABLE " + tblNm + " COMMENT '" + mtaTblLnmAf + "'";
				break;
			case "MRA" :
				clctSql += "ALTER TABLE " + tblNm + " COMMENT '" + mtaTblLnmAf + "'";
				break;
			default :
				clctSql += "COMMENT ON TABLE " + dbSchPnm + "." + tblNm + " IS '" + mtaTblLnmAf + "'";
				break;
		}
		clctSql += "\r\n";
	}

	// 컬럼 변경
 	$.each(retjson.data, function(idx, val) {
		switch(dbms){
			case "MSQ" :
				if(val.mtaColLnm == ""){
					clctSql += "EXEC sp_addextendedproperty 'MS_Description', '" + val.mtaColLnmAf + "', 'user', " + dbSchPnm 
							+ ", 'table', " + tblNm + ", 'column', " + val.mtaColPnm ;
				}else{
					clctSql += "EXEC sp_updateextendedproperty 'MS_Description', '" + val.mtaColLnmAf + "', 'user', " + dbSchPnm 
							+ ", 'table', " + tblNm + ", 'column', " + val.mtaColPnm ;
				}
				break;
			case "CBR" :
				clctSql += "MERGE INTO _cub_schema_comments A USING ("
					+ " SELECT '" + tblNm + "' AS table_name, '" + val.mtaColPnm + "' AS column_name, '" + val.mtaColLnmAf + "' AS description, '" + dbSchPnm + "' AS last_updated_user"
					+ " FROM db_root ) B ON (A.table_name = B.table_name AND A.column_name = B.column_name AND A.last_updated_user = B.last_updated_user)"
					+ " WHEN MATCHED THEN"
					+ " UPDATE SET description = B.description, last_updated = SYSDATETIME"
					+ " WHEN NOT MATCHED THEN"
					+ " INSERT ( table_name, column_name, description, last_updated, last_updated_user )"
					+ " VALUES ( B.table_name, B.column_name, B.description, SYSDATETIME, B.last_updated_user )"; 
				break;
			case "MYS" :
				clctSql += "ALTER TABLE " + tblNm + " MODIFY " + val.mtaColPnm + " " 
						+ val.dataType + "(" + val.dataLen + ") COMMENT '" + val.mtaColLnmAf + "'";
				break;
			case "MRA" :
				clctSql += "ALTER TABLE " + tblNm + " MODIFY " + val.mtaColPnm + " " 
						+ val.dataType + "(" + val.dataLen + ") COMMENT '" + val.mtaColLnmAf + "'";
				break;
			default :
				clctSql += "COMMENT ON COLUMN " + dbSchPnm + "." + tblNm + "." + val.mtaColPnm + " IS '" + val.mtaColLnmAf + "'";
				break;
		}
		clctSql += "\r\n";
	});

 	$("#frmInput #orgCd").val(grid_sheet.GetCellValue(crow, "orgCd"));
 	$("#frmInput #orgNm").val(grid_sheet.GetCellText(crow, "orgCd"));
    $("#frmInput #infoSysCd").val(grid_sheet.GetCellValue(crow,"infoSysCd"));
    $("#frmInput #infoSysNm").val(grid_sheet.GetCellText(crow,"infoSysCd"));
    $("#frmInput #dbConnTrgId").val(grid_sheet.GetCellValue(crow,"dbConnTrgId"));
    $("#frmInput #dbConnTrgPnm").val(grid_sheet.GetCellText(crow,"dbConnTrgId"));
    $("#frmInput #dbSchPnm").val(dbSchPnm);
    $("#frmInput #mtaTblPnm").val(tblNm);
	$("#frmInput #rvwConts").val(clctSql);

}

function callback(msg){
	alert(msg);
	doAction("Search");
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

	var url   = "<c:url value="/meta/mta/popup/mtacolDdlPop.do"/>";
	var param =    "mtaTblId=" + grid_sheet.GetCellValue(row, "mtaTblId");
	
	openLayerPop(url, 800, 650, param);
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
	$('#btnReset').click();
}


function grid_sheet_OnSaveEnd(code, message) {

	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}

	doAction("Search");
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

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			doAction("Search");
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
	}
}

function returnOrgCd(orgCd, orgNm){
	$("#frmSearch #orgCd").val(orgCd);
	$("#frmSearch #orgNm").val(orgNm);
}

</script>
</head>

<body>

<div id="layer_div" >

<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DB.CHANGE.INQ" /></div> <!-- 보유DB변경 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DB.CHANGE.INQ' />">
                   <caption><s:message code="DB.CHANGE.INQ.FORM" /></caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><!-- class="th_require" --><label for="orgNm"><s:message code="ORG.NM" /></label></th>
		                       	<td>
		                       		<input type="hidden" id="orgCd" name="orgCd" value="${sessionScope.loginVO.orgCd }"/>
		                        	<input type="text" id="orgNm" name="orgNm" class="wd80p d_readonly" value="${sessionScope.loginVO.orgNm}" readonly="readonly"/>
		                        	<button class="btn_frm_save" type="button" id="btnSearchOrgCd" name="btnSearchOrgCd"><s:message code="SRCH" /></button> <!-- 검색 -->
		                        </td>
		                        
		                        <th scope="row"><label for="infoSysNm"><s:message code="INFO.SYS.NM" /></label></th>
		                        <td><input type="text" id="infoSysNm" name="infoSysNm" class="wd98p"></td>
		                        <%-- <td>
		                        	<select id="infoSysNm" name="infoSysNm" class="wd300">
										<option value=""><s:message code="WHL" /></option>
										<c:forEach var="code" items="${codeMap.infoSysCd}" varStatus="status">
											<option value="${code.codeLnm}">${code.codeLnm}</option>
										</c:forEach>
									</select>
		                        </td> --%>
                            </tr>
                            <tr>
                                <th scope="row"><label for="dbConnTrgPnm"><s:message code="DB.NM" /></label></th>
                                <td><input type="text" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd98p" /></td>
                                <th scope="row"><label for="mtaTblLnm"><s:message code="TBL.NM" /></label></th>
                                <td><input type="text" id="mtaTblLnm" name="mtaTblLnm" class="wd98p" /></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM2.COL' /></div>
        </form>
</div>
<div style="clear:both; height:10px;"><span></span></div>

       <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />         

	<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "280px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:10px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	  	<li><a href="#tabs-1">Comment DDL</a></li>
	  </ul>
	  <div id="tabs-1">
			<form id="frmInput" name="frmInput" method="post">
			<div id="detailInfo">
			<textarea id="rvwConts" name="rvwConts" class="wd98p" style="width:100%;height:150px;font-family:NanumGothic;padding:2px;" readonly  title="commentDDL"></textarea>
			</div>
				<input type="hidden" id="orgCd" name="orgCd" value="" />
				<input type="hidden" id="orgNm" name="orgNm" value="" />
				<input type="hidden" id="infoSysCd" name="infoSysCd" value="" />
				<input type="hidden" id="infoSysNm" name="infoSysNm" value="" />
				<input type="hidden" id="dbConnTrgId" name="dbConnTrgId" value="" />
				<input type="hidden" id="dbConnTrgPnm" name="dbConnTrgPnm" value="" />
				<input type="hidden" id="dbSchPnm" name="dbSchPnm" value="" />
				<input type="hidden" id="mtaTblPnm" name="mtaTblPnm" value="" />
			</form>
			<div style="clear:both; height:10px;"><span></span></div>
			<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstInput.jsp" /> 
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
	</div>

</body>
</html>

