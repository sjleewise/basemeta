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
<title><s:message code="DB.MS.MNG" /></title> <!-- DBMS관리 -->

<script type="text/javascript">
//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";
var dbmsversibs = ${codeMap.dbmsversibs};
// var dbmstypversJson = ${codeMap.dbmstypvers} ;

$(document).ready(function() {
        // 조회 Event Bind
        $("#btnSearch").click(function() { 
			$("#dbConnTrgIdInfo #dbConnTrgId").val('');
            doAction("Search");  
        });
                      
        // 등록 Event Bind
        $("#btnNew").hide();
        <c:if test="${sessionScope.loginVO.orgLvlUseYn != null}">
        	//관리자는 등록 못함
	        $("#btnNew").click(function(){ doAction("New");  }).show();
        </c:if>

        // 저장 Event Bind
        $("#btnSave").click(function() {
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save"); 
        }).show();

        // 삭제 Event Bind
        $("#btnDelete").click(function() {

        	//2019.03.19 추가
			showMsgBox("ERR","DB정보 삭제는 콜센터에 문의하세요.");
        	return;
        	
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {

    			if(grid_sheet.CheckedRows("ibsCheck") > 1) {
    				showMsgBox("ERR", "<s:message code="ONLY.ONE.DEL.CAN" />");
    				return false;
    			}
        		
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
        	}
        //	doAction("Delete");  
        	
        });
        
     	// 접속테스트 Event Bind
     	$("#btnConn").hide();
/*         $("#btnConn").click(function(){
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CONN.TEST" />");
        		return;
        	}

        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.CONN.TEST" />";
    		showMsgBox("CNF", message, 'ConnTest');	
        	//doAction("Save"); 
        }).show(); */
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        //$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
      
    	//======================================================
    	// 셀렉트 박스 초기화
    	//======================================================
    	
    	//$( "#tabs" ).tabs();
    	$("#btnSearchOrgCd").hide();
    	<c:if test="${sessionScope.loginVO.orgLvlUseYn == null or (sessionScope.loginVO.orgLvlUseYn != null and sessionScope.loginVO.orgLvlUseYn eq 'Y')}">
	        $("#orgNm, #btnSearchOrgCd").click(function(){
	        	$("#frmSearch #orgCd").val("");
	        	$("#frmSearch #orgNm").val("");
	        	var url = "<c:url value='/meta/admin/popup/popMetaGiCode.do' />";
	//     		var popwin = OpenModal(url, "searchpop",  1000, 600, "no");
	        		openLayerPop(url, 1000, 600);
	       	}).show();
	    </c:if>
	    SboxSetLabelEvent();
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
});


$(window).resize(
    
    //function(){
    //	setibsheight($("#grid_01"));        
    	// grid_sheet.SetExtendLastCol(1);    
   // }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DBCONNTRG.LST'/>", Align:"Center"}
                    /* No.|상태|선택|연결상태|수집상태|기관명|정보시스템|DBMSID|논리DB명|물리DB명|DB설명
                    |적용업무|DBMS종류|DBMS버전|운영체제 종류 및 버전|구축일자|테이블수|데이터용량|수집제외사유|테이블소유자
                    |DBLink문자열|연결URL|드라이버명|DB접속계정ID|DB접속계정암호|행공센FTP명|담당자명|담당자연락처|버전|등록유형|작성일시|작성자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:0};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",     Width:40,   SaveName:"ibsSeq",			 Align:"Center", Edit:0},
                    {Type:"Status",  Width:50,   SaveName:"ibsStatus",		 Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox",Width:50,   SaveName:"ibsCheck", 		 Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",    Width:70,  SaveName:"dbLnkSts", 		 Align:"Center", Edit:0, Hidden:0, Ellipsis:1},
                    {Type:"Text",    Width:80,  SaveName:"dbcSts", 		 Align:"Center", Edit:0, Hidden:0, Ellipsis:1},
                    {Type:"Combo",   Width:200,  SaveName:"orgCd",   	 	 Align:"Left", 	 Edit:0, KeyField:1},
                    {Type:"Combo",   Width:200,  SaveName:"infoSysCd",   	 Align:"Left", 	 Edit:0, KeyField:1},
                    {Type:"Text",    Width:80,   SaveName:"dbConnTrgId",     Align:"Left", 	 Edit:0, Hidden:1}, 
                    {Type:"Text",    Width:150,  SaveName:"dbConnTrgLnm",    Align:"Left", 	 Edit:0, KeyField:1, Ellipsis:1, Hidden:1},
                    {Type:"Text",    Width:130,  SaveName:"dbConnTrgPnm",    Align:"Left", 	 Edit:0, KeyField:1, Ellipsis:1}, 
                    {Type:"Text",    Width:150,  SaveName:"objDescn",     	 Align:"Left",   Edit:0, Hidden:1},
                    
                    {Type:"Text",    Width:130,  SaveName:"aplyBizNm",     	 Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Combo",   Width:110,   SaveName:"dbmsTypCd",   	 Align:"Center", Edit:0, KeyField:0},
                    {Type:"Combo",   Width:120,   SaveName:"dbmsVersCd",   	 Align:"Center", Edit:0, KeyField:0},
                    {Type:"Text",    Width:130,  SaveName:"osVerNm",     	 Align:"Left", 	 Edit:0, KeyField:0, Hidden:1}, 
                    {Type:"Date",    Width:100,  SaveName:"constDt",     	 Align:"Center", Edit:0, Format:"yyyy-MM-dd", Hidden:1},       
                    {Type:"Int",     Width:100,  SaveName:"tblCnt",     	 Align:"Right",  Edit:0, Hidden:1},        
                    {Type:"Text",    Width:100,  SaveName:"dataCpct",     	 Align:"Right",  Edit:0, Hidden:1},        
                    {Type:"Combo",   Width:120,  SaveName:"pdataExptRsn",    Align:"Center", 	 Edit:0, Ellipsis:1},
                    {Type:"Text",     Width:130,  SaveName:"dbSchPnm", 	    Align:"Center",   Edit:0},       
                    {Type:"Text",    Width:150,  SaveName:"connTrgDbLnkChrw",Align:"Left", 	 Edit:0, Hidden:1},
                    /* {Type:"PopupEdit",   Width:200,  SaveName:"connTrgLnkUrl",   Align:"Left", 	 Edit:0, KeyField:1}, */
                    {Type:"Text",   Width:200,  SaveName:"connTrgLnkUrl",   Align:"Left", 	 Edit:0, KeyField:0, Hidden:1},
                    {Type:"Text",    Width:200,  SaveName:"connTrgDrvrNm",   Align:"Left", 	 Edit:0, KeyField:0, Hidden:1},
                    {Type:"Text",    Width:120,  SaveName:"dbConnAcId",      Align:"Left", 	 Edit:0, KeyField:0, Hidden:1},
                    {Type:"Pass",    Width:120,  SaveName:"dbConnAcPwd",     Align:"Left", 	 Edit:0, KeyField:0, Hidden:1},
					{Type:"Combo",   Width:100,   SaveName:"gpucFsvrId",   	 Align:"Center", 	 Edit:0, Hidden:0},
                    {Type:"Text",    Width:100,   SaveName:"crgpNm",     	 Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",    Width:100,  SaveName:"crgpCntel",       Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",    Width:130,  SaveName:"objVers",      	 Align:"Right",  Edit:0, Hidden:1},
                    {Type:"Combo",   Width:130,  SaveName:"regTypCd",     	 Align:"Center", Edit:0, Hidden:1},       
                    {Type:"Date",    Width:130,  SaveName:"writDtm",  	 	 Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",    Width:150,  SaveName:"writUserId",   	 Align:"Left", 	 Edit:0, Hidden:1},
                    {Type:"Text",    Width:150,  SaveName:"writUserNm",   	 Align:"Left", 	 Edit:0, Hidden:1}
                ];
        
        InitColumns(cols);
        
        InitComboNoMatchText(1, "");
        SetColProperty("dbmsTypCd", 	${codeMap.dbmstypcdibs}); 
        SetColProperty("dbmsVersCd", 	${codeMap.dbmsverscdibs});
        SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
// 		SetColProperty("infoSysCd", 	${codeMap.infoSysCdibs});
		SetColProperty("infoSysCd", 	${codeMap.infoSysCdUserMapibs});

		SetColProperty("orgCd", 	${codeMap.orgCdibs});
		SetColProperty("gpucFsvrId", 	${codeMap.gpucFsvribs});
		SetColProperty("pdataExptRsn", 	${codeMap.pdataExptRsnibs});

		//SetColBackColor("dbLnkSts","#EAEAEA");
		//SetColBackColor("dbConnTrgPnm","#EAEAEA");
		//SetColBackColor("connTrgDrvrNm","#EAEAEA");
		//SetColBackColor("regTypCd","#EAEAEA");
        
        SetExtendLastCol(1);    
        //FitColWidth();  
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}

function doAction(sAction)
{
        
    switch(sAction)
    {

    	case "New":
			var url   = '<c:url value="/meta/mta/popup/dbconnTrgRqst_pop.do"/>';
			var param = "";
			
			openLayerPop(url, 900, 600, param); 
        	break;
    
        case "New2":        //추가
        	//첫행에 추가...
        	var row = grid_sheet.DataInsert(0);

        	setComboVal();
        
//         	grid_sheet.SetCellValue(row, "connTrgDrvrNm", "oracle.jdbc.driver.OracleDriver");
//         	grid_sheet.SetCellValue(row, "connTrgLnkUrl", "jdbc:oracle:thin:@{ip}:{port}:{sid}");
        	grid_sheet.SetCellValue(row, "pdataExptRsn", "1");
        	grid_sheet.SetCellBackColor(1,"dbConnTrgPnm", "#FFFFFF");
        	
        	grid_sub.RemoveAll();
        	$('#dbms_sel_title').html('');
        	$("#dbConnTrgIdInfo #dbConnTrgId").val('');
        	//마지막 행에 추가..
        	//grid_sheet.DataInsert(-1);
        
            //var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
        
            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
/*         case "SubNew":        //추가
        	//첫행에 추가...
        	if($("#dbConnTrgIdInfo #dbConnTrgId").val() == '') {
        		showMsgBox("ERR", "DBMS를 먼저 선택하세요.");
        		return;
        	}
        	
        	var row = grid_sub.DataInsert(-1);
        	
         	grid_sub.SetCellValue(row, "dbConnTrgId", $('#dbConnTrgIdInfo #dbConnTrgId').val());
         	grid_sub.SetCellValue(row, "dbConnTrgPnm",grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "dbConnTrgPnm"));
         	grid_sub.SetCellValue(row, "dbConnTrgLnm",grid_sheet.GetCellValue(grid_sheet.GetSelectRow(), "dbConnTrgLnm"));
         	
         	grid_sub.SetCellValue(row, "ddlTrgYn", "Y");
         	         	
            break; */
            
        case "ConnChk" :
        	//체크박스 확인...
        	if(!grid_sheet.CheckedRows("ibsCheck")) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        	}
        	
        	//TODO : 입력상태인 경우 삭제하자...
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = "<c:url value="/commons/damgmt/db/chkconntrglist.do"/>";
        	$.postJSON(url, DelJson, ibscallback);
        	break;
        	
        case "Delete" :
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	//테크박스가 입력상태인 경우 삭제...
			if(!grid_sheet.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			
        	
			ibsSaveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
			
			var url = "<c:url value="/commons/damgmt/db/delconntrglist.do"/>";
// 			var param = $('form[name=frmInput]').serialize();
			var param = "";
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);

        	break;
        	
	/* 	case "SubDelete" :
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sub);
        	
        	//체크박스가 입력상태인 경우 삭제...
			if(!grid_sub.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			
			ibsSaveJson = grid_sub.GetSaveJson(0, "ibsCheck");
			
			var url = "<c:url value="/commons/damgmt/db/delSchList.do"/>";
// 			var param = $('form[name=frmInput]').serialize();
			var param = "";
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
        	break; */
        	
        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	var url = "<c:url value="/commons/damgmt/db/regconntrglist.do"/>";
         	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);
            
            break;
            
        case "ConnTest" :
        	
        	var SelectJson = grid_sheet.GetSaveJson(0);
        	var tmpVal = 0;
			for(var i=0; i<SelectJson.data.length; i++) {
// 				if(SelectJson.data[i].ibsStatus == 'I'){
// 					tmpVal = 0;
// 					break;
// 				}
				if(SelectJson.data[i].ibsCheck == '1'){
					tmpVal = 1;
					break;
				}
			}
			if (tmpVal == 0) {
				showMsgBox("ERR", "<s:message code="ERR.CONN.TEST" />");
        		return;
			}
        	
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	var url = "<c:url value="/commons/damgmt/db/dbConnTrgConnTest.do"/>";
         	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);
            
            break;
        	
/*         case "SubSave" :
        	var SaveJson = grid_sub.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         ibsSaveJson = grid_sub.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0){
        		var message = "<s:message code="ERR.CHKSAVE" />";
				showMsgBox("ERR", message); 
        		return;
        	}
        	
        	var url = "<c:url value="/commons/damgmt/db/regSchList.do"/>";
         	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);

        	break; */
            
        case "Search":
            
/* 	        var orgNm = $("#orgNm").val();
	    	if(orgNm == ""){
				showMsgBox("ERR", "<s:message code="MSG.ORG.CHC" />");
				return;
			} */
			
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch('<c:url value="/commons/damgmt/db/selectconntrglist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
            
/*         case "SubDown2Excel":  //엑셀내려받기
            
            grid_sub.Down2Excel({HiddenColumn:1, Merge:1});
            
            break; */
    }       
}
 

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//접속테스트 후 처리...
		case "<%=WiseMetaConfig.IBSAction.CONNTEST%>" :
			doAction("Search");
			
			break;
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
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		case "SCH" :  //스키마 저장/삭제 했을시...
			
			var param1 = "dbConnTrgId="+$('#dbConnTrgIdInfo #dbConnTrgId').val();
			grid_sub.DoSearch('<c:url value="/commons/damgmt/db/dbconntrg_dtl.do" />', param1);
			
			break;
	    	
		default : 
			// 아무 작업도 하지 않는다...
			break;
	}
	
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
    
    	//선택한 상세정보를 가져온다...
    	var rowData =  grid_sheet.GetRowJson(row);
    	var dbConnTrgId = rowData.dbConnTrgId;
    	var orgCd = rowData.orgCd;
    	
    	
    	if(isBlankStr(dbConnTrgId)) return;

    	$("#dbConnTrgIdInfo #dbConnTrgId").val(dbConnTrgId);
    	//메뉴ID를 토대로 조회한다....
    	var param = "dbConnTrgId="+dbConnTrgId+"&orgCd="+orgCd;

    	var url   = '<c:url value="/meta/mta/popup/dbconnTrgRqst_pop.do"/>';
		//var url   = '<c:url value="/meta/mta/popup/dbconnTrgRqst_pop.do"/>';  
		
		openLayerPop(url, 900, 600, param); 
    	
/*     	var param =  grid_sheet.GetRowJson(row);
    	
    	if(grid_sheet.GetCellValue(row, "dbConnTrgId") == '') {
    		return;
    	}
    	var dbConnTrgId = "&dbConnTrgId="+grid_sheet.GetCellValue(row, "dbConnTrgId");

    	//선택한 그리드의 row 내용을 보여준다.....
    	var tmphtml = ' <s:message code="DB.NM" /> : ' + param.dbConnTrgLnm;
    	$('#dbms_sel_title').html(tmphtml);
    	

    	$("#dbConnTrgIdInfo #dbConnTrgId").val(param.dbConnTrgId);
//     	$("#frmAprgId #aprgNm").val(param.aprgNm);
    	
    	//메뉴ID를 토대로 조회한다....
    	var param1 = "dbConnTrgId="+param.dbConnTrgId;
    	grid_sub.DoSearch('<c:url value="/commons/damgmt/db/dbconntrg_dtl.do" />', param1); */
}

function returnDbConnTrgRqstPop() {
	doAction("Search");
	showMsgBox("INF", "접속대상 DB정보 등록이 완료되었습니다. 스케줄 수집 상태를 확인하고 메타데이터를 등록하세요.");
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
	//$("#hdnRow").val(row);
	if(row < 1)	return;

	var colsavename = grid_sheet.ColSaveName(col);

	if(colsavename == "ibsCheck") { //체크1,언체크0
		var checkedRows = grid_sheet.FindCheckedRow("ibsCheck");

		var arrRow = checkedRows.split("|");
		
		if(arrRow.length > 1) {
			for(idx=0; idx<arrRow.length; idx++) {
				if(arrRow[idx] != row) {
					grid_sheet.SetCellValue(arrRow[idx], col, 0);
				}
			}
		}
	}else {
		if(grid_sheet.CheckedRows("ibsCheck") > 1) {
			showMsgBox("ERR", "<s:message code="ONLY.ONE.DEL.CAN" />");
			return false;
		}
	}

	//grid_sheet.CheckAll(""", 0);       
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	//if(grid_sheet.GetColEditable(col)) return;
    //if(grid_sheet.GetCellValue(row, "ibsStatus")!="R") return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
}


function grid_sheet_OnChange(Row, Col, Value) {
}


function grid_sheet_OnSaveEnd(code, message) {
// 	alert(code);
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}


function grid_sheet_OnSearchEnd(code, message) {
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {

		var selectedDbConnTrgId = $("#dbConnTrgIdInfo #dbConnTrgId").val();
		
		if(isBlankStr(selectedDbConnTrgId)) {
			grid_sheet.SetSelectRow(1);
		}else {

			//선택된 row 찾기
			grid_sheet.SetSelectRow(grid_sheet.FindText("dbConnTrgId", selectedDbConnTrgId, 0, -1));
		}
		
	/* 	for(var i = 1; i <= grid_sheet.RowCount(); i++) {
			
			grid_sheet.SetCellEditable(i,"dbConnTrgPnm",false); 			
		} */
	}
	
}

function grid_sheet_OnMouseMove(Button, Shift, X, Y) {

	//마우스 위치를 행과 컬럼과 값 가져오기
	var row = grid_sheet.MouseRow();
	var col = grid_sheet.MouseCol();
	var saveName = grid_sheet.ColSaveName(0, col);
	var sText = grid_sheet.GetCellText(row, col);

	if(row < 1)	return;

	if(saveName == "dbLnkSts") {

		if(!isBlankStr(sText) && sText != "성공") {

			grid_sheet.SetToolTipText(row, "dbLnkSts", sText);
		}
	}
}


<c:if test='${sessionScope.loginVO.orgLvlUseYn == null or (sessionScope.loginVO.orgLvlUseYn != null and sessionScope.loginVO.orgLvlUseYn eq "Y")}'>
function returnOrgCd(orgCd, orgNm) {
	$("#frmSearch #orgCd").val(orgCd);
	$("#frmSearch #orgNm").val(orgNm);
}
</c:if>

/* function grid_sheet_OnPopupClick(Row,Col) {
	
	if ("connTrgLnkUrl" == grid_sheet.ColSaveName(Col)) {
		var url = '<c:url value="/commons/damgmt/db/popup/dbLnkUrl_pop.do" />';
		var param = "row="+Row;
			param += "&dbmsTypCd="+grid_sheet.GetCellValue(Row, "dbmsTypCd");
			param += "&dbmsTypNm="+grid_sheet.GetCellText(Row, "dbmsTypCd");
			param += "&dbmsVersCd="+grid_sheet.GetCellValue(Row, "dbmsVersCd");
			param += "&dbmsVersNm="+grid_sheet.GetCellText(Row, "dbmsVersCd");
		openLayerPop(url, 700, 300, param);
	}
} */

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="DB.MS.MNG" /></div> <!-- DBMS 관리 -->
	    <div class="stit"><s:message code="DB.MS.MNG" /></div> <!-- DBMS 관리 -->
	</div>
	
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        <form id="dbConnTrgIdInfo" name="dbConnTrgIdInfo" method="">
        <input type="hidden" id="dbConnTrgId" name="dbConnTrgId" value="${record.dbConnTrgId}" />
        </form>
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='TARG.DB.MS.INQ' />"> <!-- 타겟DBMS 조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:7%;" />
                   <col style="width:32%;" />
                   <col style="width:7%;" />
                   <col style="width:23%;" />
                   <col style="width:7%;" />
                   <col style="width:23%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <%-- <th scope="row"><label for="dbmsTyp"><s:message code="DBMS.KIND.VERS" /></label></th> <!-- DBMS종류 및 버전 -->
                            <td>
                                <span class="input_file">
									<select id="dbmsTypCd" class="wd200" name="dbmsTypCd">
										<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									</select>
									<select id="dbmsVersCd" class="wd200" name="dbmsVersCd">
										<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								 	</select>
                                </span>
                            </td> --%>
                           <th scope="row"><label for="orgNm"><s:message code="ADMIN.SEARCHG.GINM" /></label></th> <!-- 기관명 -->
                          <td>
                          	<input type="hidden" id="orgCd" name="orgCd" value="${sessionScope.loginVO.orgCd}"/>
                           	<input type="text" id="orgNm" name="orgNm" class="wd80p d_readonly" value="${sessionScope.loginVO.orgNm}" readonly/>
                           	<button class="btn_frm_save" type="button" id="btnSearchOrgCd" name="btnSearchOrgCd"><s:message code="SRCH" /></button> <!-- 검색 -->
                           </td>

                          <th scope="row"><label for="infoSysNm"><s:message code="ADMIN.GICODE.GISYS.NM" /></label></th> <!-- 정보시스템명 -->
                          <td>
                          	<input type="text" id="infoSysNm" name="infoSysNm" class="wd98p"> 
                          	<%-- <select id="infoSysNm" name="infoSysNm" class="wd300">
								<option value=""><s:message code="WHL" /></option>
								<c:forEach var="code" items="${codeMap.infoSysCdUserMap}" varStatus="status">
									<option value="${code.codeLnm}">${code.codeLnm}</option>
								</c:forEach>
							</select> --%>
                          </td>
                          <th scope="row"><label for="dbConnTrgLnm"><s:message code="DB.NM" /></label></th> <!-- DB명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="dbConnTrgLnm" id="dbConnTrgLnm" class="wd98p" />
                                </span>
                            </td>
                       </tr>
                       <tr>   
                           <th scope="row"><label for="dbLnkSts"><s:message code="CNCT.STS" /></label></th> <!-- 연결상태 -->
                            <td><div class="sbox wd300"><label class="sbox_label" for="dbLnkSts"></label>
                                <select id="dbLnkSts" class="wd300" name="dbLnkSts">
                                		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
										<option value="Y"><s:message code="SCS" /></option> <!-- 성공 -->
										<option value="N"><s:message code="FALR" /></option> <!-- 실패 -->
								</select>
								</div>
                            </td>
                            <th scope="row"><label for="dbcSts"><s:message code="SCH.STS" /></label></th> <!-- 스케줄상태 -->
                            <td><div class="sbox wd300"><label class="sbox_label" for="dbLnkSts"></label>
                                <select id="dbcSts" class="wd300" name="dbcSts">
										<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
										<option value="<s:message code='SCS' />"><s:message code="SCS" /></option> <!-- 성공 -->
										<option value="<s:message code='FALR' />"><s:message code="FALR" /></option> <!-- 실패 -->
										<option value="<s:message code='DB.COLLECT.NO' />"><s:message code="DB.COLLECT.NO" /></option> <!-- 미수집 -->
								</select>
								</div>
                            </td>
                            <th scope="row"><label for="pdataExptRsn"><s:message code="COLLT.EXCP.YN" /></label></th> <!-- 수집제외사유 -->
                            <td>
                                <div class="sbox wd300"><label class="sbox_label" for="pdataExptRsn"></label>
                                <select id="pdataExptRsn" class="wd300" name="pdataExptRsn">
										<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
										<c:forEach var="code" items="${codeMap.pdataExptRsn}"
													varStatus="status">
													<option value="${code.codeCd}">${code.codeLnm}</option>
										</c:forEach>
								</select>
                                </div>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />
        <div class="tb_comment"><s:message code="ETC.COMM2" /></div> 
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<div class="divLstBtn" >	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> 
			    <button class="btn_search" id="btnNew" 		name="btnNew"><s:message code="REG" /></button> <!-- 등록 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
			    <button class="btn_search" id="btnConn" 	name="btnConn" style="display:none;"><s:message code="CONN.TEST" /></button> <!-- 접속테스트 --> 
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCEL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
</div>
<div style="clear:both; height:10px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01"> <!-- "560px" -->
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "464px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
</body>
</html>