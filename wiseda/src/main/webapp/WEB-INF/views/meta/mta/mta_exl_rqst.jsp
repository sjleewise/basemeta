<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html lang="ko">
<head>
<title></title>

<script type="text/javascript">
var AUTO_FLAG = false;


$(document).ready(function() {
	
	setCodeSelect("mtaDgr", "L", $("form[name=frmSearch] #mtaDgr"));
	
	// 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

    // 엑셀업로 Event Bind
     $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).show();
	
    // 조회 Event Bin
    $("#btnSearch").click(function(){ doAction("Search");  }).show();
    
    //중앙메타전송
	$("#btnSend").click(function(){ doAction("Send");  }).show();
  
  	//변경대상 추가
	$("#btnChangAdd").hide();
  
	//삭제
    $("#btnDelete").click( function(){
    	doAction("Delete"); 
    } ).show();

   	$("#frmSearch #orgCd").change(function(){   
   	
		var orgCd = $(this).val();  
	
   		getOrgInfoSys(orgCd);   
    });

	$("#frmSearch #infoSysCd").change(function(){

		//var orgCd     = $("#frmSearch #orgCd").val();
		var infoSysCd = $(this).val();

   		getInfoSysDbConnTrg(infoSysCd);   
    });

	$("#frmSearch #dbConnTrgId").change(function(){
		
		var dbConnTrgId = $(this).val();

   		getInfoSysDbSch(dbConnTrgId);   
    });

	//추가
    $("#btnNew").click( function(){
    	doAction("New");
    } );
	
  	//저장
    $("#btnSave").click( function(){
    	doAction("Save"); 
    } ).show();
});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
		
	//그리드 초기화
	initGrid(); 
	
	$(window).resize();
	
});


$(window).resize(function(){
	
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
//	setibsheight($("#grid_01"));        
   	// grid_sheet.SetExtendLastCol(1);    
    $('#layer_div').css({'padding-bottom': '0px'});
});


function initGrid()
{
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
	    var headertext1  = "No.|상태|선택";
	        headertext1 += "|기관코드|기관명|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보";
	    	headertext1 += "|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보";
	    	headertext1 += "|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보";
	    	headertext1 += "|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보";
	    var headertext2  = "No.|상태|선택";
	        headertext2 += "|기관코드|기관명|정보시스템코드|정보시스템명|관련법령(보유목적)|구축년도|운영부서명|담당자명|담당자전화번호|담당자이메일";
	    	headertext2 += "|DBID|논리DB명|물리DB명|DB설명|적용업무|DBMS종류|DBMS버전|운영체제종류|운영체제버전|구축일자|테이블수|데이터용량(MB)|수집제외사유";
	    	headertext2 += "|소유자Id|테이블소유자|테이블영문명|테이블한글명|테이블설명|테이블볼륨(ROW수)|테이블유형|품질진단여부|보존기간|발생주기|공개/비공개여부(테이블)|비공개사유(테이블)|상세관련근거";
	    	headertext2 += "|컬럼영문명|컬럼한글명|컬럼설명|데이터타입|데이터길이|데이터포맷|NOT NULL여부|PK정보|FK정보|제약조건|공개/비공개여부(컬럼)|비공개사유(컬럼)|개인정보여부|암호화여부";
        
        var headers = [
                    {Text:headertext1},
                    {Text:headertext2, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq",         	Align:"Center", Edit:1},
                    {Type:"Status",   Width:40,   SaveName:"ibsStatus",      	Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",       	Align:"Center", Edit:1, Sort:0},
                    //기관코드|기관명|정보시스템코드|정보시스템명|관련법령(보유목적)|구축년도|운영부서명|담당자명|담당자전화번호|담당자이메일
                    {Type:"Text",     Width:150,  SaveName:"orgCd",   	    	Align:"Left",   Edit: 1, Hidden:1},
                    {Type:"Combo",     Width:150,  SaveName:"orgNm",   	   	 	Align:"Center",   Edit: 1},
                    {Type:"Text",     Width:120,  SaveName:"infoSysNm", 	    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",     Width:150,  SaveName:"infoSysCd", 	    Align:"Center",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"relLaw", 	   		Align:"Left",   Edit:1},
                    {Type:"Text",     Width:80,   SaveName:"constYy", 	    	Align:"Right",   Edit:1},
//                     {Type:"Text",     Width:150,  SaveName:"deptId", 	    	Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"deptNm", 	    	Align:"Center",   Edit:1},
//                     {Type:"Text",     Width:150,  SaveName:"chrpId", 	    	Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"crgUserNm", 	    	Align:"Center",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"crgTelNo", 	    Align:"Left",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"crgEmailAddr", 	    Align:"Left",   Edit:1},
					//DBID|논리DB명|물리DB명|DB설명|적용업무|DBMS종류|DBMS버전|운영체제종류|운영체제버전|구축일자|테이블수|데이터용량(MB)|수집제외사유
                    {Type:"Text",     Width:180,  SaveName:"dbConnTrgId",   Align:"Left",   Edit:1, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"dbConnTrgLnm",	Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",     Width:100,  SaveName:"dbConnTrgPnm",	Align:"Center", Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"dbDescn",		Align:"Center", Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"applctnDuty",	Align:"Center", Edit:1},
                    {Type:"Combo",     Width:80,   SaveName:"dbmsTypCd",		Align:"Center", Edit:1},
                    {Type:"Combo",     Width:80,   SaveName:"dbmsVersCd",	Align:"Center", Edit:1},
                    {Type:"Text",     Width:80,   SaveName:"osTypCd",		Align:"Center", Edit:1},
                    {Type:"Text",     Width:80,   SaveName:"osVersCd",		Align:"Center", Edit:1},
                    {Type:"Text",     Width:80,   SaveName:"constDt", 	    Align:"Left",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"tblCnt", 	    Align:"Right",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"dataCpt", 	    Align:"Left",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"colctExRescn", 	Align:"Left",   Edit:1},
					//소유자Id|테이블소유자|테이블영문명|테이블한글명|테이블설명|테이블볼륨(ROW수)|테이블유형|품질진단여부|보존기간|발생주기|공개/비공개여부(테이블)|비공개사유(테이블)|상세관련근거
					{Type:"Text",     Width:80,   SaveName:"dbSchId",   	Align:"Left",   Edit:1, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"dbSchPnm",      Align:"Center", Edit:1}, 
                    {Type:"Text",     Width:100,  SaveName:"dbcTblPnm",     Align:"Left",   Edit:1}, 
                    {Type:"Text",     Width:100,  SaveName:"dbcTblLnm", 	Align:"Left",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"tblDescn",		Align:"Left",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"tblRowCnt",		Align:"Right",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"tblTypCd",		Align:"Center",   Edit:1},
                    {Type:"Combo",     Width:70,   SaveName:"qltyDgnsYn",	Align:"Center",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"psvPerd",		Align:"Left",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"ocrnCycl",		Align:"Left",   Edit:1},
                    {Type:"Combo",     Width:70,   SaveName:"tblOpnYn",		Align:"Center",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"tblNopnRescn",	Align:"Left",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"dtlRelBasis",	Align:"Left",   Edit:1},
                    //컬럼영문명|컬럼한글명|컬럼설명|데이터타입|데이터길이|데이터포맷|NOT NULL여부|PK정보|FK정보|제약조건|공개/비공개여부(컬럼)|비공개사유(컬럼)|개인정보여부|암호화여부
                    {Type:"Text",     Width:100,  SaveName:"dbcColPnm",		Align:"Left",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"dbcColLnm",		Align:"Left",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"colDescn",		Align:"Left",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"dataTyp",		Align:"Center",   Edit:1},
                    {Type:"Text",     Width:80,   SaveName:"dataLen",		Align:"Right",   Edit:1},
                    {Type:"Text",     Width:100,  SaveName:"dataFrm",		Align:"Left",   Edit:1},
                    {Type:"Combo",     Width:70,   SaveName:"nullYn",		Align:"Center",   Edit:1},
                    {Type:"Combo",     Width:70,   SaveName:"pkYn",			Align:"Center",   Edit:1},
                    {Type:"Combo",     Width:70,   SaveName:"fkYn",			Align:"Center",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"rstcCond",		Align:"Left",   Edit:1},
                    {Type:"Combo",     Width:70,   SaveName:"colOpnYn",		Align:"Center",   Edit:1},
                    {Type:"Text",     Width:150,  SaveName:"colNopnRescn",	Align:"Left",   Edit:1},
                    {Type:"Combo",     Width:70,   SaveName:"persInfoYn",	Align:"Center",   Edit:1},
                    {Type:"Combo",     Width:70,   SaveName:"encYn",			Align:"Center",   Edit:1}
                ];
                    
        InitColumns(cols);
        SetLeftCol(2);

	    //콤보 목록 설정...  	    
 	   SetColProperty("orgNm", 	${codeMap.orgCdibs}); 
 	   SetColProperty("infoSysCd", 	${codeMap.infoSysCdibs}); 
 	   SetColProperty("dbmsTypCd", 	${codeMap.dbmstypcdibs}); 
 	   SetColProperty("dbmsVersCd", 	${codeMap.dbmsverscdibs}); 
 	   
 	   SetColProperty("qltyDgnsYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
 	   SetColProperty("tblOpnYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
 	   SetColProperty("nullYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
 	   SetColProperty("pkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
 	   SetColProperty("fkYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
 	   SetColProperty("colOpnYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
 	   SetColProperty("persInfoYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
 	   SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
 	   InitComboNoMatchText(1, "");

               
//         SetColHidden("ibsStatus",1);
        //SetColHidden("infoSysCd",1);
       // SetColHidden("orgCd",1);
        //SetColHidden("dbConnTrgId",1);
        //SetColHidden("mtaTblId",0);

//       	SetColHidden("ibsCheck"	,1);      
      	
      	//FitColWidth();
      	
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
        	var param = $('#frmSearch').serialize();
        	        	
        	grid_sheet.DoSearch("<c:url value="/meta/mta/getMtaExlRqst.do" />", param);

        	break;
                      	      
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'기관메타수동업로드양식'});
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
            
        case "New":  //그리드추가
        	grid_sheet.DataInsert(0);
        	break;
        case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
//         	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
//         	if(DelJson.data.length == 0) return;
        	
//         	var url = '<c:url value="/meta/mta/deleteMtaTbllist.do"/>';
//         	var param = "";
//         	IBSpostJson2(url, DelJson, param, ibscallback);
			break;  
        case "Save":  //저장
			ibsSaveJson = grid_sheet.GetSaveJson(0);
			//데이터 사이즈 확인...
 	 	   	if (ibsSaveJson.Code == "IBS000") return; 
    		//2. 필수입력 누락인 경우
			if (ibsSaveJson.Code == "IBS010") return;
			
			if(ibsSaveJson.data.length == 0){
				showMsgBox("INF", "<s:message code="ERR.CHKSAVE" />");
				return;
			}
			
			//프로파일별 url 셋팅
			var url = "<c:url value="/meta/mta/regMtaExlRqst.do" />";
			
// 			var param = $('form[name=frmSearch]').serialize();
// 			param = encodeURI(param);
	        IBSpostJson2(url, ibsSaveJson, '', ibscallback);
        	break;
        case "Send":  //저장	
	        if(AUTO_FLAG == false){  
	 	 		var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
		    	if(saveJson.data.length == 0) return;
		    	var url = '<c:url value="/meta/mta/autoMtaTbl.do"/>';
		    	var param = "sendStsCd=SA";
		    	IBSpostJson2(url, saveJson, param, ibsPopCallback, true, 'N');
		    	AUTO_FLAG = true;
		    	
	        }
			break;
       	//검증
	    case "CheckData":
		    showPrcMsgCol("F", true);

			var startDataRowIdx = csv_sheet.GetDataFirstRow();
			var endDataLastRowIdx = csv_sheet.LastRow();
			var firstSystem = csv_sheet.GetCellValue(startDataRowIdx, csv_sheet.SaveNameCol("infoSysNm"))+"|"+
			csv_sheet.GetCellValue(startDataRowIdx, csv_sheet.SaveNameCol("dbConnTrgPnm"))
			var match_flag = true;
			for(var i=startDataRowIdx; i<=endDataLastRowIdx; i++) {
				//초기화
				var resultMsg = "";
				var resultCd = "ERR";
				csv_sheet.SetRowFontColor(i, "85,85,85");
				//1. 입력값에 대한 코드 매칭 여부 체크
					resultMsg += matchName2Code(i, "orgCd", "orgNm");
					
				//DB물리정보가 신규건이면 DB스키마 정보는 체크 안함
				//if(!checkName2Code(i, "dbConnTrgId", "dbConnTrgPnm")) {
				//	checkName2Code(i, "dbSchId", "dbSchPnm");
				//}
				//2. 필수입력 항목 체크 및 유효성체크
				var startColIdx = csv_sheet.SaveNameCol("orgNm"); //필수입력항목 체크 시작 컬럼저장명
				var endColIdx = csv_sheet.LastCol();
				var colNameRowIdx = 1; //컬럼명 참고할 rowIndex
				var nullCheckResultMsg = "";
				var dbCheckResultMsg = "";
				var validateCheckResultMsg1 = "";
				var validateCheckResultMsg2 = "";
				var validateCheckResultMsg3 = "";
				var validateCheckResultMsg4 = "";
				var validateCheckResultMsg5 = "";
				var validateCheckResultMsg6 = "";
				var validateCheckResultMsg7 = "";
				var validateCheckResultMsg8 = "";
				var validateCheckResultMsg9 = "";
				var validateCheckResultMsg10 = "";
				var validateCheckResultMsg11 = "";
				var validateCheckResultMsg12 = "";
				var validateCheckResultMsg13 = "";
				var validateCheckResultMsg14 = "";
				var validateCheckResultMsg15 = "";
				var regExp = "";
				
				for(var colIdx=startColIdx; colIdx<=endColIdx; colIdx++) {
					var err_flag = true;
					 //복수 시스템 체크
					if(csv_sheet.SaveNameCol("dbConnTrgPnm") == colIdx && 
							firstSystem != csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("infoSysNm"))+"|"+csv_sheet.GetCellValue(i, colIdx)){
							dbCheckResultMsg += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
							csv_sheet.SetRowFontColor(i, "#FF0000");
					}
					if(colIdx == endColIdx && !isBlankStr(dbCheckResultMsg)) {

						if(!isBlankStr(resultMsg)) {
							resultMsg += ".";
						}
						
						resultMsg += "단일 시스템, 단일 DB로 등록"
					} 
				
					//2-1. 필수입력 항목 체크
					if(err_flag){
				var exceptColIdxArr = [csv_sheet.SaveNameCol("relLaw"),csv_sheet.SaveNameCol("constYy")
				       				,csv_sheet.SaveNameCol("operDeptNm"),csv_sheet.SaveNameCol("crgUserNm")
				       				,csv_sheet.SaveNameCol("crgTelNo"),csv_sheet.SaveNameCol("crgEmailAddr")
				       				,csv_sheet.SaveNameCol("constDt"),csv_sheet.SaveNameCol("constCnd")
				       				,csv_sheet.SaveNameCol("aplyBizNm"),csv_sheet.SaveNameCol("dbmsTypCd")
				       				,csv_sheet.SaveNameCol("dbmsVersCd"),csv_sheet.SaveNameCol("osKndCd")
				       				,csv_sheet.SaveNameCol("osVerNm"),csv_sheet.SaveNameCol("tblCnt")
				       				,csv_sheet.SaveNameCol("dataLen"), csv_sheet.SaveNameCol("fkYn")
				       				,csv_sheet.SaveNameCol("dataFmt"),csv_sheet.SaveNameCol("occrCyl")
				       				,csv_sheet.SaveNameCol("tblTypNm"),csv_sheet.SaveNameCol("prsvTerm")
				       				,csv_sheet.SaveNameCol("tblVol"),csv_sheet.SaveNameCol("pdataExptRsn")
				       				,csv_sheet.SaveNameCol("dataCpct"),csv_sheet.SaveNameCol("pkYn")
				       				,csv_sheet.SaveNameCol("nonulYn"),csv_sheet.SaveNameCol("dbDescn")
				       				,csv_sheet.SaveNameCol("uppSubjId"),csv_sheet.SaveNameCol("subjId")
				       				,csv_sheet.SaveNameCol("subjNm"),csv_sheet.SaveNameCol("dataType")
				       				,csv_sheet.SaveNameCol("nonulYn")
				       				//공개 비공개에서 체크
				       				,csv_sheet.SaveNameCol("nOpenRsn"),csv_sheet.SaveNameCol("openYn")
				       				,csv_sheet.SaveNameCol("nOpenDtlRelBss"),csv_sheet.SaveNameCol("priRsn")
				       				];//필수항목 제외 컬럼명
					if(exceptColIdxArr.indexOf(colIdx) == -1) {
						if(isBlankStr(csv_sheet.GetCellValue(i, colIdx))) {
							if(!isBlankStr(nullCheckResultMsg)) {
								nullCheckResultMsg += ",";
							}
							err_flag = false;
							nullCheckResultMsg += csv_sheet.GetCellValue(colNameRowIdx, colIdx)
							csv_sheet.SetRowFontColor(i, "#FF0000");
						}
					}


					if(colIdx == endColIdx && !isBlankStr(nullCheckResultMsg)) {

						if(!isBlankStr(resultMsg)) {
							resultMsg += ".";
						}
						
						resultMsg += "필수 미입력 및 코드 불일치["+nullCheckResultMsg+"]";
					}
					/* //개행확인
					exceptColIdxArr = [csv_sheet.SaveNameCol("relLaw"),csv_sheet.SaveNameCol("constYy")
					       				, csv_sheet.SaveNameCol("aplyBizNm"),csv_sheet.SaveNameCol("dbDescn")
					       				,csv_sheet.SaveNameCol("tblDescn"),csv_sheet.SaveNameCol("operDeptNm")
					       				,csv_sheet.SaveNameCol("crgUserNm"),csv_sheet.SaveNameCol("subjNm")
					       				,csv_sheet.SaveNameCol("colDescn"),csv_sheet.SaveNameCol("crgEmailAddr")
					       				,csv_sheet.SaveNameCol("crgTelNo"),csv_sheet.SaveNameCol("infoSysNm")
					       				,csv_sheet.SaveNameCol("dbConnTrgPnm"),csv_sheet.SaveNameCol("orgNm")
					       				,csv_sheet.SaveNameCol("dbSchPnm")];//필수항목 제외 컬럼명
				       				regExp = /\r|\n/g;
				       				if(exceptColIdxArr.indexOf(colIdx) == -1) {
										if(regExp.test(csv_sheet.GetCellText(i, colIdx))) {
											if(!isBlankStr(validateCheckResultMsg4)) {
													validateCheckResultMsg4 += ",";
											}
											err_flag = false;
											validateCheckResultMsg4 += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
											csv_sheet.SetRowFontColor(i, "#FF0000");
										}
									}


									if(colIdx == endColIdx && !isBlankStr(validateCheckResultMsg4)) {

										if(!isBlankStr(resultMsg)) {
											resultMsg += ".";
										}
										
										resultMsg += "개행문자 포함["+validateCheckResultMsg4+"]";
									} */
				//특정문자 제거
				exceptColIdxArr = [csv_sheet.SaveNameCol("tblCnt")];//필수항목 제외 컬럼명
				if(exceptColIdxArr.indexOf(colIdx) == -1) {
					csv_sheet.SetCellValue(i, colIdx, replaceAll(replaceAll(replaceAll(replaceAll(replaceAll(csv_sheet.GetCellValue(i, colIdx), "\"", ""), "\'", ""), "\\", "/"), "\r", ""), "\n", ""));
				}
				exceptColIdxArr = 	[csv_sheet.SaveNameCol("relLaw"),csv_sheet.SaveNameCol("constYy")
				       				,csv_sheet.SaveNameCol("aplyBizNm"),csv_sheet.SaveNameCol("dbDescn")
				       				,csv_sheet.SaveNameCol("tblDescn"),csv_sheet.SaveNameCol("operDeptNm")
				       				,csv_sheet.SaveNameCol("crgUserNm"),csv_sheet.SaveNameCol("subjNm")
				       				,csv_sheet.SaveNameCol("colDescn"),csv_sheet.SaveNameCol("crgEmailAddr")
				       				,csv_sheet.SaveNameCol("crgTelNo"),csv_sheet.SaveNameCol("infoSysNm")
				       				,csv_sheet.SaveNameCol("dbConnTrgPnm"),csv_sheet.SaveNameCol("orgNm")
				       				,csv_sheet.SaveNameCol("dbSchPnm"),csv_sheet.SaveNameCol("constCnd")
									,csv_sheet.SaveNameCol("pkYn"),csv_sheet.SaveNameCol("nOpenDtlRelBss")
									,csv_sheet.SaveNameCol("tblDescn"),csv_sheet.SaveNameCol("dbConnTrgLnm")
				       				//combo 제외
				       				,csv_sheet.SaveNameCol("pdataExptRsn"),csv_sheet.SaveNameCol("constDt")
				       				,csv_sheet.SaveNameCol("tblClltDcd"),csv_sheet.SaveNameCol("openRsnCd")
				       				,csv_sheet.SaveNameCol("nOpenRsn"),csv_sheet.SaveNameCol("osVerNm")
				       				,csv_sheet.SaveNameCol("prsvTerm"),csv_sheet.SaveNameCol("occrCyl")
				       				,csv_sheet.SaveNameCol("nonulYn"),csv_sheet.SaveNameCol("fkYn")
				       				,csv_sheet.SaveNameCol("openYn"),csv_sheet.SaveNameCol("prsnInfoYn")
				       				,csv_sheet.SaveNameCol("encTrgYn"),csv_sheet.SaveNameCol("dqDgnsYn")
				       				,csv_sheet.SaveNameCol("dbmsTypCd"),csv_sheet.SaveNameCol("dbmsVersCd")
				       				,csv_sheet.SaveNameCol("osKndCd"),csv_sheet.SaveNameCol("dataType")
				       				,csv_sheet.SaveNameCol("mtaTblPnm"),csv_sheet.SaveNameCol("mtaColPnm")
				       				,csv_sheet.SaveNameCol("dataFmt"),csv_sheet.SaveNameCol("priRsn")
				       				//숫자 제외
				       				,csv_sheet.SaveNameCol("tblCnt"),csv_sheet.SaveNameCol("tblVol")
				       				,csv_sheet.SaveNameCol("dataLen"),csv_sheet.SaveNameCol("dataCpct")
				       				];//필수항목 제외 컬럼명
					//특수문자
					if(exceptColIdxArr.indexOf(colIdx) == -1) {
						if(!checkExpChar2(csv_sheet.GetCellValue(i, colIdx), "_")) {
							if(!isBlankStr(validateCheckResultMsg5)) {
								validateCheckResultMsg5 += ",";
							}
							err_flag = false;
							validateCheckResultMsg5 += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
							csv_sheet.SetRowFontColor(i, "#FF0000");
						}
					}
					if(colIdx == endColIdx && !isBlankStr(validateCheckResultMsg5)) {

						if(!isBlankStr(resultMsg)) {
							resultMsg += ".";
						}
						
						resultMsg += "특수문자 포함["+validateCheckResultMsg5+"]";
					}
				}
				if(err_flag){
					if((csv_sheet.SaveNameCol("dbConnTrgLnm") == colIdx || csv_sheet.SaveNameCol("mtaTblLnm") == colIdx
							|| csv_sheet.SaveNameCol("mtaColLnm") == colIdx ) && !containKOR(csv_sheet.GetCellValue(i, colIdx))) {

						if(!isBlankStr(validateCheckResultMsg2)) {
							validateCheckResultMsg2 += ",";
						}
						err_flag = false;
						validateCheckResultMsg2 += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
						csv_sheet.SetRowFontColor(i, "#FF0000");
					}
					if(colIdx == endColIdx && !isBlankStr(validateCheckResultMsg2)) {

						if(!isBlankStr(resultMsg)) {
							resultMsg += ".";
						}
						
						resultMsg += "한글을 포함하여 입력하세요["+validateCheckResultMsg2+"]";
					}
				} 
				if(err_flag){
					// 숫자만 포함
						/* regExp = /[^0-9\.?]/g;
						if((csv_sheet.SaveNameCol("tblCnt") == colIdx || csv_sheet.SaveNameCol("dataLen") == colIdx) 
								&& regExp.test(csv_sheet.GetCellValue(i, colIdx))) {
							if(!isBlankStr(validateCheckResultMsg10)) {
								validateCheckResultMsg10 += ",";
							}
							err_flag = false;
							validateCheckResultMsg10 += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
							csv_sheet.SetRowFontColor(i, "#FF0000");
						}

						regExp = /[^0-9\.?,?]/g;
						if((csv_sheet.SaveNameCol("tblVol") == colIdx || csv_sheet.SaveNameCol("dataCpct") == colIdx) 
								&& regExp.test(csv_sheet.GetCellValue(i, colIdx))) {
							if(!isBlankStr(validateCheckResultMsg10)) {
								validateCheckResultMsg10 += ",";
							}
							err_flag = false;
							validateCheckResultMsg10 += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
							csv_sheet.SetRowFontColor(i, "#FF0000");
						} */


						if(colIdx == endColIdx && !isBlankStr(validateCheckResultMsg10)) {

							if(!isBlankStr(resultMsg)) {
								resultMsg += ".";
							}
							
							resultMsg += "숫자 Only["+validateCheckResultMsg10+"]";
						}
						// Y or N 포함
						regExp = /Y|N/;
						if((csv_sheet.SaveNameCol("nonulYn") == colIdx || csv_sheet.SaveNameCol("pkYn") == colIdx
								|| csv_sheet.SaveNameCol("prsnInfoYn") == colIdx ||csv_sheet.SaveNameCol("encTrgYn") == colIdx
								|| csv_sheet.SaveNameCol("openYn") == colIdx ||csv_sheet.SaveNameCol("dqDgnsYn") == colIdx
								) && !regExp.test(csv_sheet.GetCellValue(i, colIdx))) {
							if(!isBlankStr(validateCheckResultMsg11)) {
								validateCheckResultMsg11 += ",";
							}
							err_flag = false;
							validateCheckResultMsg11 += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
							csv_sheet.SetRowFontColor(i, "#FF0000");
						}


						if(colIdx == endColIdx && !isBlankStr(validateCheckResultMsg11)) {

							if(!isBlankStr(resultMsg)) {
								resultMsg += ".";
							}
							
							resultMsg += "Y or N 미포함["+validateCheckResultMsg11+"]";
						}
				}
				/* 
						// 선후관계 DBMS typ-ver
 						var DBMS =  [{"typ" : "ORACLE", "ver" : /R10|R11|R12|R08|R09/}
									,{"typ" : "ALTIBASE", "ver" : /AL5|AL6/}								
									,{"typ" : "SYBASEIQ", "ver" : /Q11|Q12|Q15/}													
									,{"typ" : "SYBASEASE", "ver" : /S11|S12|S15/}													
									,{"typ" : "DB2", "ver" : /D09|D10|D11/}
									,{"typ" : "MS-SQL", "ver" : /M00|M05|M08|M12|M14|M16|M17/}		
									,{"typ" : "CUBRID", "ver" : /C10|C08|C09/}
									,{"typ" : "POSTGRESQL", "ver" : /P08|PO9/}
									,{"typ" : "MS-ACCESS", "ver" : /M20|M23/}
									,{"typ" : "TIBERO", "ver" : /TI4|TI5|TI6/}
									,{"typ" : "MARIA", "ver" : /MR1|MR5/}
									,{"typ" : "HANADB", "ver" : /HN1/}
									];

						if(csv_sheet.SaveNameCol("dbmsTypCd") == colIdx) {
							for(var k = 0; k < Object.keys(DBMS).length; k++){
								if(DBMS[k].typ == csv_sheet.GetCellText(i, csv_sheet.SaveNameCol("dbmsTypCd"))){
									if(!DBMS[k].ver.test(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("dbmsVersCd")))){
										if(!isBlankStr(validateCheckResultMsg12)) {
											validateCheckResultMsg12 += ",";
										}
										err_flag = false;
										validateCheckResultMsg12 += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
										csv_sheet.SetRowFontColor(i, "#FF0000");
									}
								}
							}
						}

						if(colIdx == endColIdx && !isBlankStr(validateCheckResultMsg12)) {

							if(!isBlankStr(resultMsg)) {
								resultMsg += ".";
							}
							
							resultMsg += "DBMS Type에 따른 Ver 불일치["+validateCheckResultMsg12+"]";
						}
						// 선후관계 OS os-ver
						var osTyp = ${codeMap.osKndCdibs}.ComboCode.split("|");

						if(csv_sheet.SaveNameCol("osKndCd") == colIdx) {
							for(var k = 0; k < osTyp.length; k++){
								if(osTyp[k] == csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("osKndCd"))){
									if(osTyp[k].substr(0, 1) != csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("osVerNm")).substr(0, 1)){
										if(!isBlankStr(validateCheckResultMsg13)) {
											validateCheckResultMsg13 += ",";
										}
										err_flag = false;
										validateCheckResultMsg13 += csv_sheet.GetCellValue(colNameRowIdx, colIdx);
										csv_sheet.SetRowFontColor(i, "#FF0000");
									}
								}
							}
						}

						if(colIdx == endColIdx && !isBlankStr(validateCheckResultMsg13)) {

							if(!isBlankStr(resultMsg)) {
								resultMsg += ".";
							}
							
							resultMsg += "OS Type에 따른 Ver 불일치["+validateCheckResultMsg13+"]";
						} */
						// 선후관계 공개여부 Tbl-Col
						if(err_flag){
						if(csv_sheet.SaveNameCol("openRsnCd") == colIdx) {
								if("01" == csv_sheet.GetCellValue(i, colIdx)){
									if(!isBlankStr(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("nOpenRsn")))){
										if(!isBlankStr(validateCheckResultMsg14)) {
											validateCheckResultMsg14 += ",";
										}
										err_flag = false;
										validateCheckResultMsg14 += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("nOpenRsn"));
										csv_sheet.SetRowFontColor(i, "#FF0000");
										csv_sheet.SetCellEditable(i, "nOpenRsn", 1);
									}
									if(!isBlankStr(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("nOpenDtlRelBss")))){
										if(!isBlankStr(validateCheckResultMsg14)) {
											validateCheckResultMsg14 += ",";
										}
										err_flag = false;
										validateCheckResultMsg14 += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("nOpenDtlRelBss"));
										csv_sheet.SetRowFontColor(i, "#FF0000");
										csv_sheet.SetCellEditable(i, "nOpenDtlRelBss", 1);
									}
									if(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("openYn")) != "Y"){
										if(!isBlankStr(validateCheckResultMsg14)) {
											validateCheckResultMsg14 += ",";
										}
										err_flag = false;
										validateCheckResultMsg14 += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("openYn"));
										csv_sheet.SetRowFontColor(i, "#FF0000");
									}
									csv_sheet.SetCellValue(i, "priRsn", "");
								}
								else if("02" == csv_sheet.GetCellValue(i, colIdx) || "03" == csv_sheet.GetCellValue(i, colIdx)){
									if(isBlankStr(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("nOpenRsn")))){
										if(!isBlankStr(nullCheckResultMsg)) {
											nullCheckResultMsg += ",";
										}
										err_flag = false;
										nullCheckResultMsg += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("nOpenRsn"));
										csv_sheet.SetRowFontColor(i, "#FF0000");
									}
									if(isBlankStr(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("nOpenDtlRelBss")))){
										if(!isBlankStr(nullCheckResultMsg)) {
											nullCheckResultMsg += ",";
										}
										err_flag = false;
										nullCheckResultMsg += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("nOpenDtlRelBss"));
										csv_sheet.SetRowFontColor(i, "#FF0000");
									}
									if("02" == csv_sheet.GetCellValue(i, colIdx) && csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("openYn")) != "N"){
										if(!isBlankStr(validateCheckResultMsg14)) {
											validateCheckResultMsg14 += ",";
										}
										err_flag = false;
										validateCheckResultMsg14 += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("openYn"));
										csv_sheet.SetRowFontColor(i, "#FF0000");
									}
									if("03" == csv_sheet.GetCellValue(i, colIdx) && isBlankStr(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("openYn")))){
										if(!isBlankStr(validateCheckResultMsg14)) {
											validateCheckResultMsg14 += ",";
										}
										err_flag = false;
										validateCheckResultMsg14 += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("openYn"));
										csv_sheet.SetRowFontColor(i, "#FF0000");
									}
									if("02" == csv_sheet.GetCellValue(i, colIdx)
										|| ("03" == csv_sheet.GetCellValue(i, colIdx) && "Y" == csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("openYn")))){
										csv_sheet.SetCellValue(i, "priRsn", "");
									}
								}
								
							}
						if(csv_sheet.SaveNameCol("openYn") == colIdx) {
							if("Y" == csv_sheet.GetCellValue(i, colIdx)){
								if("N" != csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("prsnInfoYn"))){
									if(!isBlankStr(validateCheckResultMsg14)) {
										validateCheckResultMsg14 += ",";
									}
									err_flag = false;
									validateCheckResultMsg14 += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("prsnInfoYn"));
									csv_sheet.SetRowFontColor(i, "#FF0000");
								}
								if("N" != csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("encTrgYn"))){
									if(!isBlankStr(validateCheckResultMsg14)) {
										validateCheckResultMsg14 += ",";
									}
									err_flag = false;
									validateCheckResultMsg14 += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("encTrgYn"));
									csv_sheet.SetRowFontColor(i, "#FF0000");
								}
								if(!isBlankStr(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("priRsn")))){
									if(!isBlankStr(validateCheckResultMsg14)) {
										validateCheckResultMsg14 += ",";
									}
									err_flag = false;
									validateCheckResultMsg14 += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("priRsn"));
									csv_sheet.SetRowFontColor(i, "#FF0000");
								}
							}
							if("N" == csv_sheet.GetCellValue(i, colIdx)){
								if("03" == csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("openRsnCd")) && isBlankStr(csv_sheet.GetCellValue(i, csv_sheet.SaveNameCol("priRsn")))){
									if(!isBlankStr(nullCheckResultMsg)) {
										nullCheckResultMsg += ",";
									}
									err_flag = false;
									nullCheckResultMsg += csv_sheet.GetCellValue(colNameRowIdx, csv_sheet.SaveNameCol("priRsn"));
									csv_sheet.SetRowFontColor(i, "#FF0000");
								}
							} 
						}
						if(colIdx == endColIdx && !isBlankStr(validateCheckResultMsg14)) {

							if(!isBlankStr(resultMsg)) {
								resultMsg += ".";
							}
							
							resultMsg += "공개여부에 따른 선후 관계불일치["+validateCheckResultMsg14+"]";
						}
					}
				}
				
				if(isBlankStr(resultMsg)) {
					resultCd = "OK";
					
				}else {
					csv_sheet.SetRowFontColor(i, "255,0,0");
				}

				csv_sheet.SetCellValue(i, "prcMsg", resultMsg);
				csv_sheet.SetCellValue(i, "prcCd", resultCd);
				
			}

			
			CHECK_FLAG = false;
			var errCnt = countGridValue(csv_sheet, "prcCd", "ERR");
			if(errCnt == 0) {
				getInfoSysCheck();
				errCnt = countGridValue(csv_sheet, "prcCd", "ERR");
				if(errCnt == 0) {
					getDbConnTrgCheck();
					errCnt = countGridValue(csv_sheet, "prcCd", "ERR");
					if(errCnt == 0) {
					getDbSchCheck();
					errCnt = countGridValue(csv_sheet, "prcCd", "ERR");
						if(errCnt == 0) {
							//중복체크
				     		//var DupRow = csv_sheet.ColValueDupRows("orgNm|infoSysNm|dbConnTrgPnm|dbSchPnm|dbcTblNm|dbcColNm");
				     		var DupRow = csv_sheet.ColValueDupRows("orgNm|infoSysNm|dbConnTrgPnm|dbSchPnm|mtaTblPnm|mtaColPnm");
				 			if(DupRow != "") {
					 			var arrRow = DupRow.split(",");

				 				$.each(arrRow, function(i, rowIdx) {
				 					csv_sheet.SetCellValue(rowIdx, "prcMsg", "<s:message code='MSG.SAME.KEY.EXIS' />");
				 					csv_sheet.SetCellValue(rowIdx, "prcCd", "ERR");
				 					csv_sheet.SetRowFontColor(rowIdx, "255,0,0");
					 			});
				 			}
				 		 errCnt = countGridValue(csv_sheet, "prcCd", "ERR");
				 		 if(errCnt == 0) {
				 			/*var saveJson = csv_sheet.GetSaveJson(1);
				           	var url = '<c:url value="/meta/mta/popup/goCheckData.do"/>';
				           	var param = ""; 
				        	var syn = true;

				        	$.ajax({
				        		url : url,
				        		async : syn,
				        		type : "POST",
				        		data : JSON.stringify(saveJson),
				        		contentType : 'application/json',
				        		dataType : 'json',
				        		success : function(data){
				                		$.each(data.result, function(index,item){
				                    		if(item == -1){
				            				csv_sheet.SetCellValue(index+2, "prcMsg", "업무분류체계 불일치");
				    	 					csv_sheet.SetCellValue(index+2, "prcCd", "ERR");
				    	 					csv_sheet.SetRowFontColor(index+2, "255,0,0");
				                			}        		
				                  		});
				                		dataCheckResult();
				        		}
				        	 }); */
				        	dataCheckResult();
				 		  } else {
				 			dataCheckResult();
				 			}
						}else {
			 			dataCheckResult();
			 			}
					}else {
						dataCheckResult();
					}
				}else {
					dataCheckResult();
				}
			}else {
				dataCheckResult();
			}
				
			break;	
    }       
}

//================================================
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
//================================================
function ibsPopCallback(res) {
	
	$("#checkSendFrame").hide();
	$("#divMsgPopup").remove();
	$("div #step:eq(6) #MsgDiv").show();
	if(res.chk != "fail"){
		
		showMsgBox("INF", "<s:message code="CTR.META.SEND.SUCS" />");
		
	}else{
		$("div #step:eq(6) #MsgDiv").text("전송이 실패되었습니다.");
	}

	grid_sheet.SetColHidden("ibsCheck", 1);
}

function getOrgInfoSys(orgCd){
	
	var ajaxurl =  "<c:url value="/meta/mta/ajax/getOrgInfoSys.do" />";

	var objParam = new Object();   

	objParam.orgCd = orgCd; 

	var param = objParam; 

	//alert(JSON.stringify(param));
	
	$.ajax({
		url: ajaxurl,
		async: false,
		type: "POST",
		data: JSON.stringify(param),
		contentType : 'application/json',
		dataType: 'json',
			
		success: function (data) {


			$("#frmSearch #infoSysCd").find("option").remove().end();
// 			$("#frmSearch #dbConnTrgId").find("option").remove().end();
// 			$("#frmSearch #dbSchId").find("option").remove().end();

			$("#frmSearch #infoSysCd").append("<option value=\"\"><s:message code='WHL' /></option>");
// 			$("#frmSearch #dbConnTrgId").append("<option value=\"\"><s:message code='WHL' /></option>");
// 			$("#frmSearch #dbSchId").append("<option value=\"\"><s:message code='WHL' /></option>");

			/* $("#frmSearch #infoSysCd").prev().html($("#frmSearch #infoSysCd").find('option:first').text());
			$("#frmSearch #dbConnTrgId").prev().html($("#frmSearch #dbConnTrgId").find('option:first').text());
			$("#frmSearch #dbSchId").prev().html($("#frmSearch #dbSchId").find('option:first').text()); */
			
			$.each(data, function(i, map) {

				var infoSysCd = map.INFO_SYS_CD ;
				var infoSysNm = map.INFO_SYS_NM ;
				
				$("#frmSearch #infoSysCd").append("<option value="+ infoSysCd +">"+ infoSysNm +"</option>");			
				
			});		
		},		
		error: function (jqXHR, textStatus, errorThrown) {
				
		}
	});
}



function getInfoSysDbConnTrg(infoSysCd){

	var ajaxurl =  "<c:url value="/meta/mta/ajax/getInfoSysDbConnTrg.do" />";

	var objParam = new Object();   

	//objParam.orgCd     = orgCd;
	objParam.infoSysCd = infoSysCd;   

	var param = objParam; 

	//alert(JSON.stringify(param));
	
	$.ajax({
		url: ajaxurl,
		async: false,
		type: "POST",
		data: JSON.stringify(param),
		contentType : 'application/json',
		dataType: 'json',
			
		success: function (data) {

			$("#frmSearch #dbConnTrgId").find("option").remove().end();
			$("#frmSearch #dbSchId").find("option").remove().end();

			$("#frmSearch #dbConnTrgId").append("<option value=\"\"><s:message code='WHL' /></option>");
			$("#frmSearch #dbSchId").append("<option value=\"\"><s:message code='WHL' /></option>");

			/* $("#frmSearch #dbConnTrgId").prev().html($("#frmSearch #dbConnTrgId").find('option:first').text());
			$("#frmSearch #dbSchId").prev().html($("#frmSearch #dbSchId").find('option:first').text()); */
			
			$.each(data, function(i, map) {

				var dbConnTrgId  = map.DB_CONN_TRG_ID ; 
				var dbConnTrgPnm = map.DB_CONN_TRG_PNM ;
				
				$("#frmSearch #dbConnTrgId").append("<option value="+ dbConnTrgId +">"+ dbConnTrgPnm +"</option>");			
				
			});		
		},		
		error: function (jqXHR, textStatus, errorThrown) {
				
		}
	});
}


function getInfoSysDbSch(dbConnTrgId){

	var ajaxurl =  "<c:url value="/meta/mta/ajax/getInfoSysDbSch.do" />";

	var objParam = new Object();   

	objParam.dbConnTrgId = dbConnTrgId;
	
	var param = objParam; 

	//alert(JSON.stringify(param));
	
	$.ajax({
		url: ajaxurl,
		async: false,
		type: "POST",
		data: JSON.stringify(param),
		contentType : 'application/json',
		dataType: 'json',
			
		success: function (data) {

			$("#frmSearch #dbSchId").find("option").remove().end();

			$("#frmSearch #dbSchId").append("<option value=\"\"><s:message code='WHL' /></option>");
			/* $("#frmSearch #dbSchId").prev().html($("#frmSearch #dbSchId").find('option:first').text()); */
			
			$.each(data, function(i, map) {
				
				var dbSchId  = map.DB_SCH_ID ;
				var dbSchPnm = map.DB_SCH_PNM ;
				
				$("#frmSearch #dbSchId").append("<option value="+ dbSchId +">"+ dbSchPnm +"</option>");			
				
			});		
		},		
		error: function (jqXHR, textStatus, errorThrown) {
				
		}
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
    

}


function grid_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		showMsgBox("INF", "<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		showMsgBox("INF", "<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) { 

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

</head>

<body>

	<div id="layer_div">

		<!-- 메뉴 메인 제목 -->
		<div class="menu_subject">
			<div class="tab">
				<div class="menu_title">
					<s:message code="META.DATA.SCH" />
				</div>
			</div>
		</div>

		<!-- 검색조건 입력폼 -->
		<div id="search_div">
			<div class="stit">
				<s:message code="INQ.COND2" />
			</div>
			<!-- 검색조건 -->

			<form id="frmSearch" name="frmSearch" method="post">
				<fieldset>
					<legend>
						<s:message code="FOREWORD" />
					</legend>
					<!-- 머리말 -->
					<div class="tb_basic">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							summary="기관명조회">
							<caption>
								<s:message code="TBL.NM1" />
							</caption>
							<!-- 테이블 이름 -->
							<colgroup>
								<col style="width: 20%;" />
								<col style="width: 30%;" />
								<col style="width: 20%;" />
								<col style="width: *;" />
							</colgroup>
							<tbody>
								<tr>
									<th scope="row" ><label for="orgNm"><s:message code="ORG.NM" /></label></th> <!-- 기관명 -->
									<td>
										<div class="sbox wd300">
											<label class="sbox_label" for="orgCd"></label> 
											<select id="orgCd" name="orgCd" class="wd300">
												<option value=""><s:message code="WHL" /></option>
												<c:forEach var="code" items="${codeMap.orgCd}"
													varStatus="status">
													<option value="${code.codeCd}">${code.codeLnm}</option>
												</c:forEach>
											</select>
										</div>
									</td>
									<th scope="row"><label for="infoSysCd"><s:message
												code="INFO.SYS.NM" /></label></th>
									<td>
										<div class="sbox wd300">
											<label class="sbox_label" for="infoSysCd"></label> <select
												id="infoSysCd" name="infoSysCd" class="wd300">
												<option value=""><s:message code="WHL" /></option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="dbConnTrgId"><s:message
												code="DB.NM" /></label></th>
									<!-- DBMS/스키마명 -->
									<td>
										<div class="sbox wd300">
											<label class="sbox_label" for="dbConnTrgId"></label> <select
												id="dbConnTrgId" name="dbConnTrgId" class="wd300">
												<option value=""><s:message code="WHL" /></option>
											</select>
										</div>
									</td>

									<th scope="row"><label for="dbConnTrgId">테이블소유자</label></th>
									<!-- DBMS/스키마명 -->
									<td>
										<div class="sbox wd300">
											<label class="sbox_label" for="dbSchId"></label> <select
												id="dbSchId" name="dbSchId" class="wd300">
												<option value=""><s:message code="WHL" /></option>
											</select>
										</div>
									</td>
								</tr>
								<tr>
									<th scope="row"><label for="dbcTblPnm"><s:message
												code="TBL.NM" /></label></th>
									<!-- 테이블명 -->
									<td colspan="5"><span class="input_file"> <input type="text"
											name="dbcTblPnm" id="dbcTblPnm" class="wd98p"
											value="${search.tblNm}" />
									</span></td>
									
									<th scope="row" style="display: none;"><label for="mtaDgr">등록차수</label></th>
									<td colspan="3"  style="display: none;">
										<div class="sbox wd300">
											<label class="sbox_label" for="mtaDgr"></label> <select
												id="mtaDgr" class="wd300" name="mtaDgr">
												<option value=""><s:message code="WHL" /></option>
												<c:forEach var="code" items="${codeMap.mtaDgr}"
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


				<div class="tb_comment">
					<s:message code='ETC.COMM' />
				</div>
			</form>
		</div>
		</div>
		
		<div style="clear: both; height: 10px;"></div>

		<!-- 조회버튼영역  -->
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" /> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />

		<div style="clear: both; height: 10px;"></div>

		<!-- 그리드 입력 입력 -->
		<div id="grid_01" class="grid_01">
			<script type="text/javascript">createIBSheet("grid_sheet", "100%", "460px");</script>
		</div>
		<!-- 그리드 입력 입력 End -->

		<div style="clear: both; height: 5px;"></div>
</body>
</html>

