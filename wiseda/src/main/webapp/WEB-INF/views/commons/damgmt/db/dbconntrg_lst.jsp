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
var dbmstypversJson = ${codeMap.dbmstypvers} ;

$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
        //그리드 초기화 
//         initGrid();
        
        // 적용 Event Bind
		$("#btnApply").click(function(){ doAction("ConnChk");  }).hide();
        
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });

        // 저장 Event Bind
        $("#btnSave").click(function(){
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
        $("#btnDelete").click(function(){ 
        	
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
        	}
        //	doAction("Delete");  
        	
        });
        
     // 접속테스트 Event Bind
        $("#btnConn").click(function(){
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
        }).show();
        
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
      
    	//======================================================
    	// 셀렉트 박스 초기화
    	//======================================================
    	double_select(dbmstypversJson, $("#dbmsTypCd"));
    	$('select', $("#dbmsTypCd").parent()).change(function(){
    		double_select(dbmstypversJson, $(this));
    	});
    	
    	//$( "#tabs" ).tabs();
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();

});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headerTxt = "<s:message code='COMMON.HEADER.DBCONNTRG.LST'/>";
        
		if ("${govYn}" == "Y") {
        	headerTxt += "<s:message code='COMMON.HEADER.DBCONNTRG.GOV.LST'/>";
        }
        
        var headers = [
                    {Text:headerTxt, Align:"Center"}
                    /* No.|상태|선택|연결상태|DBMSID|DBMS논리명|DBMS물리명|DBMS유형|DBMS버전|DBLink문자열|연결URL|드라이버명|DB접속계정ID|DB접속계정암호|메타관리여부|정보시스템|담당자명|담당자연락처|설명|버전|등록유형|작성일시|작성자ID|작성자명|접속테스트오류내용 */
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"dbLnkSts",    Align:"Center", Edit:0, Hidden:0},                     
                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgLnm",   Align:"Left", Edit:1, KeyField:1},
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",   Align:"Left", Edit:1, KeyField:1}, 
                    {Type:"Combo",  Width:80,   SaveName:"dbmsTypCd",   	 Align:"Center", Edit:1},
                    {Type:"Combo",  Width:100,   SaveName:"dbmsVersCd",   	 Align:"Center", Edit:1},
                    {Type:"Text",   Width:150,  SaveName:"connTrgDbLnkChrw",     Align:"Left", 	 Edit:1, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"connTrgLnkUrl",     Align:"Left", 	 Edit:1},
                    {Type:"Text",   Width:180,  SaveName:"connTrgDrvrNm",     Align:"Left", 	 Edit:1},
                    {Type:"Text",   Width:120,  SaveName:"dbConnAcId",     Align:"Left", 	 Edit:1},
                    {Type:"Pass",   Width:120,  SaveName:"dbConnAcPwd",     Align:"Left", 	 Edit:1},
                    {Type:"Combo",  Width:80,   SaveName:"metaMngYn",   	 Align:"Center", Edit:1, Hidden:0},
                    {Type:"Combo",  Width:80,   SaveName:"infoSysCd",   	 Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",   Width:80,   SaveName:"crgpNm",     Align:"Left", 	 Edit:1, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"crgpCntel",     Align:"Left", 	 Edit:1, Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"objDescn",     Align:"Center", 	 Edit:1},
                    {Type:"Text",   Width:130,  SaveName:"objVers",      Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:130,  SaveName:"regTypCd",     Align:"Center", Edit:0},        
                    {Type:"Date",   Width:130,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:150,  SaveName:"writUserId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"writUserNm",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"dbLnkStsCtns",   Align:"Left", Edit:0, Hidden:0},
                ];
        
        if ("${govYn}" == "Y") {
        	cols.push(
        			//범정부연계항목
        			{Type:"Text",     Width:100,  SaveName:"applBz" ,Align:"Left",   Edit:1},
					{Type:"Combo",    Width:80,  SaveName:"osKind"    ,Align:"Center",   Edit:1},
					{Type:"Combo",    Width:80,  SaveName:"osVers"    ,Align:"Center",   Edit:1},
					{Type:"Date",     Width:100,  SaveName:"esblhDt",  	 Align:"Center", Edit:1, Hidden:0, Format:"yyyy-MM-dd"},
					{Type:"Text",      Width:80,  SaveName:"tblCnt"    ,Align:"Right",   Edit:1},
					{Type:"Text",     Width:80,  SaveName:"dataCapa"    ,Align:"Right",   Edit:1},
					{Type:"Combo",     Width:100,  SaveName:"gthExcptRsn" ,Align:"Left",   Edit:1}
				);
        }
        
        InitColumns(cols);
        
        InitComboNoMatchText(1, "");

        SetColProperty("infoSysCd", 	${codeMap.infoSysCd});
        SetColProperty("dbmsTypCd", 	${codeMap.dbmstypcdibs});
        SetColProperty("dbmsVersCd", 	${codeMap.dbmsverscdibs});
        SetColProperty("regTypCd", 		${codeMap.regTypCdibs});
		SetColProperty("metaMngYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		
		if ("${govYn}" == "Y") {
			SetColProperty("osKind", 	${codeMap.oskndibs});
			SetColProperty("osVers", 	${codeMap.osversibs});
			SetColProperty("gthExcptRsn", 	${codeMap.gthexcptrsnibs});
		}
        // FitColWidth();  
        
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
        case "New":        //추가
        	//첫행에 추가...
        	var row = grid_sheet.DataInsert(0);
        
        	grid_sheet.SetCellValue(row, "connTrgDrvrNm", "oracle.jdbc.driver.OracleDriver");
        	grid_sheet.SetCellValue(row, "connTrgLnkUrl", "jdbc:oracle:thin:@{ip}:{port}:{sid}");
        	
        	grid_sub.RemoveAll();
        	$('#dbms_sel_title').html('');
        	

        	$("#dbConnTrgIdInfo #dbConnTrgId").val('');
        	//마지막 행에 추가..
        	//grid_sheet.DataInsert(-1);
        
            //var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
        
            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
        case "SubNew":        //추가
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
         	         	
            break;
            
        case "ConnChk" :
        	//체크박스 확인...
        	if(!grid_sheet.CheckedRows("ibsCheck")) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        	}
        	
        	//TODO : 입력상태인 경우 삭제하자...
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
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

        	
		case "SubDelete" :
        	
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
			
        	break;
        	
        	
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
        	
        case "SubSave" :
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

        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/damgmt/db/selectconntrglist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"DBMS접속정보"});
            
            break;
            
        case "SubDown2Excel":  //엑셀내려받기
            
            
            grid_sub.Down2Excel({HiddenColumn:1, Merge:1, FileName:"DBMS스키마정보"});
            
            break;
            
            
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
            
        case "SubLoadExcel":  //엑셀업로
            
            
            grid_sub.LoadExcel({Mode:'HeaderMatch'});
            
            break;
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
    
/* 	if(row < 1) return;
	
	var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
 
	$("#saveCls").val("U");  //저장구분을 수정 (U) 로 변경 
	
	var usrId = grid_sheet.GetCellValue(row, "arrUsrId");
	
	$("#usrId").val(usrId);  
	   
    $("#frmInput").attr("action", url).submit(); */
	if(row < 1) return;
    
	var dbmsTypVers = ${codeMap.dbmstypvers};
	var upCode = grid_sheet.GetCellValue(row, 'dbmsTypCd');
	
	var comboTxt = "";
	var comboCd = "";
	
	for(i=0; i<dbmsTypVers.length; i++) {
		if(dbmsTypVers[i].upcodeCd == upCode) {
			comboTxt += "|" + dbmsTypVers[i].codeLnm;
			comboCd += "|" + dbmsTypVers[i].codeCd;
		}
		
	}
	
	var info = {"ComboCode":comboCd, "ComboText":comboTxt};
	
	grid_sheet.CellComboItem(row, 'dbmsVersCd', info);
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
	//$("#hdnRow").val(row);

	if(row < 1)	return;
	
	var dbmsTypVers = ${codeMap.dbmstypvers};
	var upCode = grid_sheet.GetCellValue(row, 'dbmsTypCd');
	
	var comboTxt = "N/A";
	var comboCd = "";
	
	for(i=0; i<dbmsTypVers.length; i++) {
		if(dbmsTypVers[i].upcodeCd == upCode) {
			comboTxt += "|" + dbmsTypVers[i].codeLnm;
			comboCd += "|" + dbmsTypVers[i].codeCd;
		}
		
	}
	
	var info = {"ComboCode":comboCd, "ComboText":comboTxt};
	
	grid_sheet.CellComboItem(row, 'dbmsVersCd', info);

	
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	//if(grid_sheet.GetColEditable(col)) return;
    //if(grid_sheet.GetCellValue(row, "ibsStatus")!="R") return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	
	if(grid_sheet.GetCellValue(row, "dbConnTrgId") == '') {
		return;
	}
	var dbConnTrgId = "&dbConnTrgId="+grid_sheet.GetCellValue(row, "dbConnTrgId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = 'DBMS명 : ' + param.dbConnTrgLnm;
	$('#dbms_sel_title').html(tmphtml);
	

	$("#dbConnTrgIdInfo #dbConnTrgId").val(param.dbConnTrgId);
// 	$("#frmAprgId #aprgNm").val(param.aprgNm);
	
	//메뉴ID를 토대로 조회한다....
	var param1 = "dbConnTrgId="+param.dbConnTrgId;
	grid_sub.DoSearch('<c:url value="/commons/damgmt/db/dbconntrg_dtl.do" />', param1);
}


function grid_sheet_OnChange(Row, Col, Value) {

	if(grid_sheet.CellSaveName(Row, Col) == "dbmsTypCd") {		
	        if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "ORA") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "oracle.jdbc.driver.OracleDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:oracle:thin:@{ip}:{port}:{sid}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "SYQ") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.sybase.jdbc3.jdbc.SybDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:sybase:Tds:{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "SYA") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.sybase.jdbc3.jdbc.SybDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:sybase:Tds:{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "DB2") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.ibm.as400.access.AS400JDBCDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:as400://{ip}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "UDB") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.ibm.db2.jcc.DB2Driver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:db2://{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "MSQ") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:sqlserver://{ip}:{port};DatabaseName={db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "ALT") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "Altibase.jdbc.driver.AltibaseDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:Altibase://{ip}:{port}/{sid}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "TER") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.teradata.jdbc.TeraDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:teradata://{ip}/CHARSET=ASCII,CLIENT_CHARSET=cp949");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "MYS") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.mysql.jdbc.Driver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:mysql://{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "TIB") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.tmax.tibero.jdbc.TbDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:tibero:thin:@{ip}:{port}:{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "IFX") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.informix.jdbc.IfxDriver");
// 	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:informix-sqli://{host}:{port}/{db명}:INFORMIXSERVER={인스턴스명}");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:informix-sqli://{host}:{port}/{db명}:INFORMIXSERVER={인스턴스명};user:{유져명};password={패스워드}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "MDB") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "mongodb.jdbc.MongoDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "mongodb://{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "HIV") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "org.apache.hive.jdbc.HiveDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:hive2://{ip}:{port}/{db명};auth=noSasl");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "POS") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "org.postgresql.Driver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:postgresql://{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "MRA") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "org.mariadb.jdbc.Driver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:mariadb://{ip}:{port}/{db명}");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "CBR") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "cubrid.jdbc.driver.CUBRIDDriver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:CUBRID:{ip}:{port}:{db명}:::");
	        } else if(grid_sheet.GetCellValue(Row, "dbmsTypCd") == "HAN") {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "com.sap.db.jdbc.Driver");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "jdbc:sap://{ip}:{port}/?databaseName={db명}&autocommit=false");
	        } else {
	        	grid_sheet.SetCellValue(Row, "connTrgDrvrNm", "");
	        	grid_sheet.SetCellValue(Row, "connTrgLnkUrl", "");
	        }
	        
	        
	}
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
		
		grid_sheet.SetSelectRow(1);
		
		for(var i = 1; i <= grid_sheet.RowCount(); i++) {
			
			grid_sheet.SetCellEditable(i,"dbConnTrgPnm",false); 			
		}
	}
	
}



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
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="dbmsTyp"><s:message code="DB.MS.PTRN" /></label></th> <!-- DBMS유형 -->
                            <td>
                                <span class="input_file">
									<select id="dbmsTypCd" class="" name="dbmsTypCd">
										<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									</select>
									<select id="dbmsVersCd" class="" name="dbmsVersCd">
										<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
								 	</select>
                                </span>
                            </td>
                           <th scope="row"><label for="dbConnTrgLnm"><s:message code="DB.MS.NM" /></label></th> <!-- DBMS명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="dbConnTrgLnm" id="dbConnTrgLnm" />
                                </span>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<div class="divLstBtn" >	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> 
			    <button class="btn_apply" id="btnApply" 	name="btnApply"><s:message code="APL" /></button> <!-- 적용 --> 
                <button class="btn_rqst_new" id="btnTreeNew" name="btnTreeNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
				  <ul class="add_button_menu" id="addTreeMenu">
				    <li id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 -->
				    <li id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 업로드 -->
				  </ul>         
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 --> 
			    <button class="btn_search" id="btnConn" 	name="btnConn"><s:message code="CONN.TEST" /></button> <!-- 접속테스트 --> 
			</div>
			<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                        -->
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="dbms_sel_title"><s:message code="DB.MS.NM" /> : <span></span></div> <!-- DBMS명 -->
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="DB.MS.SCHEMA.INFO" /></a></li> <!-- DBMS 스키마정보 -->
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<%@include file="dbconntrg_dtl.jsp" %>
	  </div>
	 </div>



</body>
</html>