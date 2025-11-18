<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title>메타데이터 등록 </title>
<script type="text/javascript">

var SelectedRqstSno = -1;

$(document).ready(function() {
		//탭 초기화....
// 		$("#tabs").tabs().show();
		
		//업무구분상세 초기화...
		$("#mstFrm #bizDtlCd").val('TBL');
		$("#btnExcelDown").hide();
		
// 		// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....

		//마우스 오버 이미지 초기화
// 		//imgConvert($('div.tab_navi a img'));
		
		//검색조건 display none
		$("div#search_div").hide();

		// 등록요청 Event Bind
		$("#btnRegRqst").click(function(){
			showMsgBox("PRC", "<s:message code="REQ.SUBMIT.INIT" />");

			/* var errCnt = countGridValue(grid_sheet, "regStatus", "OK");
			if(errCnt = 0) {
				showMsgBox("ERR", "상태가 완료인 경우에만 등록요청 가능합니다.");
				return false;
			}  */
			var cntrqst = countGridValue(grid_sheet, "vrfCd", "1");
			
			var message = "";
			if (cntrqst > 0) message += "메타데이터 : ["+cntrqst+"건]"; //물리모델, 건
			message +="<br>"+"<s:message code="CNF.RQST" />";
			
			//등록가능한지 확인한다.vrfCd = 1
			
// 			var regchk = grid_sheet.FindText("vrfCd", "등록가능");
			//if(regchk) {alert (regchk);} 
			if(cntrqst > 0) {
				showMsgBox("CNF", message, 'Submit');
			} else {
				showMsgBox("ERR", "<s:message code="ERR.SUBMIT" />");
				return false;
			}
		});	
		
		//전체승인 버튼 이벤트 처리
		$("#btnAllApprove").click(function(){
			showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
// 			doAllApprove(grid_sheet, "1");
			//마스터에 전체승인을 셋팅한다.  
			$("#mstFrm #rvwStsCd").val("1");
			chgallapprove (grid_sheet);
			$("#btnReqApprove").click();
		});
		
		//전체반려 버튼 이벤트 처리
		$("#btnAllReject").click(function(){
			rejectMsgBox("ALLREJECT", "<s:message code="REQ.APPROVE.REJECT" />", reqallreject);
			//doAllReject(itemgrid, "2", mstrvwCont);
			//doAllApprove(grid_sheet, "2");
		});
		
		//검토처리 Event Bind
		$("#btnReqApprove").click(function(){
			showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
			
			//alert("결재처리")
			//그리드 변경대상 체크한다.
			var cntappr = grid_sheet.RowCount();
			if (cntappr <= 0) {
				showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
				return false;
			}
			// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
			if (chkRvwStsCd(grid_sheet, "rvwStsCd") > -1) {
				//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
// 				showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
				approveMsgBox("APPROVE", "<s:message code="ERR.APPROVE.CNF" />");
				return false;
			};
			
			//반려 선택시 반려사유를 입력하도록 한다.
			var tmprow = chkRvwCont(grid_sheet, "rvwStsCd", "rvwConts");
			if (tmprow > 0 ) {
				showMsgBox("INF", "<s:message code="ERR.REJECT" />");
				grid_sheet.SetSelectRow(tmprow);

				return false;
			}
			
			//결재처리 총건수, 승인건수, 반려건수를 메세지로 만든다.
			//승인/반려 건수
			var message = ""; 
			var cntAppr 	= countGridValue(grid_sheet, "rvwStsCd", "1");
			var cntReject  	= countGridValue(grid_sheet, "rvwStsCd", "2");
			if (cntappr > 0) message += "[메타데이터:"+cntappr+"<s:message code='CNT.APRV' />  "+cntAppr+"<s:message code='CNT.RTN' /> "+cntReject+"]<br>";
			// 물리모델, 건 중 승인, 건, 반려, 건
			message += "<s:message code="CNF.APPR" />";

			showMsgBox("CNF", message, "Approve");	
			
// 			doAction("Approve");
			
		});		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function() { 
        	SelectedRqstSno = -1;
            doAction("Search");  
        }).show();
        
    	//추가 Event Bind
    	$("#btnRqstNew").show();
    	
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  }).hide();

        // 변경대상 추가 Event Bind
        $("#btnChangAdd").click(function(){ 
        	
        	//doAction("AddWam");

        	doAction("AddWat");      	
        
        }).show(); //doAction("NewChg"); });
        
        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
        	SelectedRqstSno = -1;
            
    		//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL.TBL" />", 'Delete');
//             	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
        	}
        }).show(); //doAction("Delete");  });


        // 엑셀내리기 Event Bind
        //$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).hide();
        
        /* //검색제거 버튼...
        $(".btnDelPop").click(function(){
        	//이전값 제거... 텍스트 박스가 아닌 경우는 어떻하지???
        	$(this).prev().val('');
        }); */
        
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(sysareaJson, $("#sysAreaId"));
      
      //신규요청서
        $("#btnBlank").click( function(){
    		location.href = '<c:url value="/meta/mta/mtatbl_rqst.do" />';
        } );
        
    }
);

$(window).on('load',function() {
// 	$(".divLstBtn").show();
	
	// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
// 	$("#tabs").tabs().show();
	
	//그리드 초기화 
	initGrid();

	var rqststep = $("#mstFrm #rqstStepCd").val();

	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	checkApproveYn($("#mstFrm"));
		
	doAction("Search");

	//loadDetail();  
	   
});



$(window).resize(
    
    function() {
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid() {
    
    with(grid_sheet) {
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);

        var headtext = "<s:message code='META.HEADER.MTATBL.RQST'/>";
        
        //No.|ibs상태|선택|검토상태|검토내용|요청구분|테이블입력상태|컬럼입력상태|등록유형|검증결과|기관명|정보시스템명|논리DB명|물리DB명|테이블소유자|테이블명|테이블한글명|테이블유형|테이블설명
        //|업무분류체계|품질진단여부|태깅정보|테이블생성일|보존기간|테이블볼륨|개방데이터목록|갱신주기|비공개사유|담당자ID|담당자명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|테이블ID|기관코드|정보시스템코드|DB접속ID
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:90,  SaveName:"regTblStatus",	Align:"Center", Edit:0},						
					{Type:"Text",   Width:90,  SaveName:"regColStatus",	Align:"Center", Edit:0},
					{Type:"Combo",  Width:60,  SaveName:"regTypCd",	Align:"Center", Edit:0},
					{Type:"Combo",  Width:60,  SaveName:"vrfCd",	Align:"Center", Edit:0},
					
					{Type:"Text",   Width:170,  SaveName:"orgNm",         Align:"Left",   Edit:0},
					{Type:"Text",   Width:170,  SaveName:"infoSysNm",     Align:"Left",   Edit:0},
					{Type:"Text",   Width:60,   SaveName:"dbConnTrgId",   Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",  Align:"Left",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"dbSchPnm",      Align:"Left",   Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"mtaTblPnm",     Align:"Left",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"mtaTblLnm",     Align:"Left",   Edit:0},
					{Type:"Combo",  Width:90,   SaveName:"tblTypNm",      Align:"Left",   Edit:0, Hidden:1},
					
					{Type:"Text",   Width:80,   SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
					{Type:"Text",   Width:90,   SaveName:"subjNm"	,     Align:"Left",   Edit:0, Hidden:1},
					{Type:"Combo",  Width:90,   SaveName:"dqDgnsYn"	,     Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"tagInfNm"	,     Align:"Left",   Edit:0, Hidden:1},	
					
					{Type:"Text",   Width:100, SaveName:"tblCreDt"	,    Align:"Center",  Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:90,  SaveName:"prsvTerm"	,    Align:"Right",   Edit:0, Hidden:1},
					{Type:"Text",   Width:90,  SaveName:"tblVol"	,    Align:"Right",   Edit:0, Hidden:1},

					{Type:"Text",   Width:100,  SaveName:"openDataLst",  Align:"Left",    Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"occrCyl",      Align:"Right",   Edit:0, Hidden:1},
					{Type:"Combo",  Width:100,  SaveName:"nopenRsn",     Align:"Center",  Edit:0, Hidden:1},
					
					{Type:"Text",   Width:60,   SaveName:"crgUserId",	 Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"crgUserNm",	 Align:"Left",   Edit:0, Hidden:1},
                   
                    {Type:"Text",   Width:130,  SaveName:"rqstDtm",  	 Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",   Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",   Align:"Center", Edit:0, Hidden:1},					
                    {Type:"Text",   Width:60,   SaveName:"rqstNo",       Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",      Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"mtaTblId",     Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"orgCd",        Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"infoSysCd",    Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"dbSchId" ,     Align:"Left",   Edit:0, Hidden:1}
					
		    		];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		SetColProperty("nopenRsn", 	${codeMap.nopenRsnibs});
		SetColProperty("tblTypNm", 	${codeMap.tblTypCdibs});
		
		//SetColProperty("dbConnTrgId", 	${codeMap.connTrgSchibs});
		
		SetColProperty("dqDgnsYn", 		{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"});
		SetColProperty("regTblStatus", 	{ComboCode:"ERR|OK", ComboText:"미완료|완료"} );
		
		
		//SetColProperty("regColStatus", 	{ComboCode:"ERR|OK", ComboText:"미완료|완료"} );
		

// 		SetColProperty("stdAplYn", 		{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
//		SetColProperty("partTblYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
//		SetColProperty("dwTrgTblYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */ 
        
        InitComboNoMatchText(1, "");
        
/* 		SetColHidden("mtaTblId"	,1); 
		SetColHidden("mdlLnm"	,1);
		SetColHidden("rqstNo"	,1);
		SetColHidden("rqstSno"	,1);*/
        
        // FitColWidth();
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
    switch(sAction)
    {
        case "New":        //신규 추가...
        	loadDetail();
			
            break;
        
        case "AddWam": //기존 테이블 추가
			var url   = "<c:url value="/meta/mta/popup/mtatblSearchPop.do"/>";
			var param = $("#mstFrm #rqstNo").serialize() + "&popRqst=Y";
			
			openLayerPop(url, 800, 700, param);
			break;

        case "AddWat": //WAT 테이블 추가
			var url   = "<c:url value="/meta/mta/popup/mtaWatTblSearch_pop.do"/>";
			
			var param = "popRqst=Y&rqstNo=" + $("#mstFrm #rqstNo").val();
			
			openLayerPop(url, 800, 800, param); 
			break;	
		case "Reset" :
			
			//폼내용 초기화.....
			$("#frmInput")[0].reset();        	

            break;
            
        case "Delete" :
			//트리 시트의 경우 하위 레벨도 체크하도록 변경...
	    	//setTreeCheckIBS(grid_sheet);

	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/mta/delmtatblrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
		case "SaveRow" :
			// 공통으로 처리...
			/* var orgNm = "";

			if($("#frmInput #orgCd").val() != ""){
				orgNm = $("#orgCd option:selected").text();
			}else {
				orgNm = "";
			}
			$("#frmInput #orgNm").val(orgNm);

			var infoSysNm = "";
			
			if($("#frmInput #infoSysCd").val() != ""){
				infoSysNm = $("#infoSysCd option:selected").text();
			}else {
				infoSysNm = "";
			}

			$("#frmInput #infoSysNm").val(infoSysNm);
			 */
		
			var saveJson = getform2IBSjson($("#frmInput"));
			
// 			saveJson = $("#frmInput").serializeArray();
// 			saveJson = $("#frmInput").serializeObject();

			
// 			if (saveJson.Code == "IBS000") return;
			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/mta/regmtatblrqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
			
			break;        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = grid_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/mta/regmtatblrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallback);
        	break;
        
		case "Search":

			
			var param = $("#mstFrm").serialize();

			loadDetail();
			//변경항목 조회
			if(typeof grid_vrf != "undefined") {
				grid_vrf.RemoveAll();
			}
			if(typeof grid_change != "undefined") {
				grid_change.RemoveAll();
			}
			if(typeof grid_changecol != "undefined") {
				grid_changecol.RemoveAll();
			}
			if(param.regTypCd == 'U') {
				getRqstChg(param1);
			}

			grid_sheet.DoSearch("<c:url value="/meta/mta/getmtatblrqstlist.do" />", param);

			//컬럼조회
			doActionCol("Search");
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
						
			break;
		case "ReSearch":

			$("#mstFrm #rqstNo").val("${tempRqstNo.RQST_NO}");
			$("#mstFrm #bizDcd").val("${tempRqstNo.BIZ_DCD}");
			$("#mstFrm #bizDcdNm").val("${tempRqstNo.BIZ_DCD_NM}");
			$("#mstFrm #rqstNm").val("${tempRqstNo.RQST_NM}");
			$("#mstFrm #rqstStepCd").val("${tempRqstNo.RQST_STEP_CD}");
			$("#mstFrm #rqstStepCdNm").val("${tempRqstNo.RQST_STEP_CD_NM}"); 				
			
			var param = $("#mstFrm").serialize();

			var url = '<c:url value="/meta/mta/mtatbl_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
					
			break;
	        	
		case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
			
			break;
	   
		case "Approve" : //결재처리
			
			var saveJson = grid_sheet.GetSaveJson(0);
			
			//2. 필수입력 누락인 경우
// 			alert(saveJson.Code);
			if (saveJson.Code == "IBS000") return;
// 			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/mta/approvemtatbl.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":  //엑셀업로드
			//var url = "<c:url value="/meta/mta/popup/mtatbl_xls.do" />";
			var url = "<c:url value="/meta/mta/popup/mtatblcol_xls.do" />";
 			//var xlspopup = OpenWindow(url ,"mtatblxls","800","600","yes");
			openLayerPop(url, 800, 600);
			break;
			
        case "NewCol": //신규 컬럼 추가..
        	//테이블의 현재 선택행을 확인 후 1행 이상이면 신규 폼 호출한다.
			var crow = grid_sheet.GetSelectRow();
			if(crow < 1) {
				showMsgBox("ERR", "<s:message code="ERR.TBLSEL" />");
				return;
			}
        	
        	//컬럼 입력폼을 불러온다.
        	loadDetailCol();
        	break;
        
        case "SaveColRow":        //컬럼 저장...
			
			//컬럼 순서 재조정
        	chgColOrd();
        
        	var saveJson = col_sheet.GetSaveJson(0);
        	if (saveJson.Code == "IBS000") return;
        	if (saveJson.Code == "IBS010") return;
// 			var saveJson = getform2IBSjson($("#frmColInput"));
// 			saveJson = $("#frmInput").serializeArray();
// 			saveJson = $("#frmInput").serializeObject();

// 			if (saveJson.Code == "IBS000") return;
// 			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/mta/regmtacolrqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
        
            break;

        case "SaveCol": //컬럼 리스트 저장
        
        	var saveJson = col_sheet.GetSaveJson(0);
        	if (saveJson.Code == "IBS000") return;
        	if (saveJson.Code == "IBS010") return;
        	
			var url = "<c:url value="/meta/mta/regmtacolrqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
        	break;
        	
        case "DeleteCol": //컬럼 리스트 삭제...
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(col_sheet);
	    	
	    	var DelJson = col_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/mta/delmtacolrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        
    }       
}

//컬럼 순서 재조정
function chgColOrd() {
	//컬럼 순서 조정 : 1. 신규일 경우 그리드에 추가 후 저장한다.
	//컬럼 순서 조정 : 2. 저장시에는 테이블의 컬럼 순서 재조정 후 저장한다.
	var frmJson = getform2IBSjson($("#frmColInput"));
//		var testjson = col_sheet.GetRowJson(i);
//		alert(frmJson.data[0].ibsStatus);
	var tblsno = frmJson.data[0].rqstSno;
	var colsno = frmJson.data[0].rqstDtlSno;
	var colord = frmJson.data[0].colOrd;
	var insrow = 0; //신규 또는 변경 row 번호
	var preord = 0; //변경시 이전 컬럼순서
	var moverow = 0; //변경시 이동할 row
	//동일 테이블의 첫행을 찾는다.
	var fcol = col_sheet.FindText("rqstSno", tblsno);
	var lcol = col_sheet.LastRow();
	if (frmJson.data[0].ibsStatus == "I") {
		
		//동일한 테이블이 없는 경우 ==> 마지막에 인서트 한다.
		if (fcol ==  -1) {
			insrow = col_sheet.DataInsert(-1);
		} else  {
			//동일한 테이블에 동일한 컬럼오더가 있는지 확인한다.
			for (var i=fcol; i<=lcol; i++) {
				var ibsjson = col_sheet.GetRowJson(i);
				
				if (ibsjson.rqstSno == tblsno) {
					if (ibsjson.colOrd >= colord) {
						insrow = col_sheet.DataInsert(i);
						break;
					}   	
					
				} else {
//						alert(i+1);
					insrow = col_sheet.DataInsert(i);
					break;
				}
			}
			
		}
		
		if (insrow <= 0) insrow = col_sheet.DataInsert(-1);
//			alert(insrow);
		col_sheet.SetRowJson(insrow, frmJson);
		
	//변경일 경우 기존 순서와 틀릴경우 처리한다.	
	} else {
		
		insrow = col_sheet.GetSelectRow();
		if(insrow > 0) {
			preord = col_sheet.GetCellValue(insrow, "colOrd");
		}
		//변경의 경우 순서가 틀릴 경우 row를 이동한다.
		if (preord > 0 && preord != colord) {
			
			//동일한 테이블에 동일한 컬럼오더가 있는지 확인한다.
			for (var i=fcol; i<=lcol; i++) {
				var ibsjson = col_sheet.GetRowJson(i);
				
				if (ibsjson.rqstSno == tblsno) {
					if (ibsjson.colOrd >= colord) {
						moverow = i;
						break;
					}   	
					
				} else {
//						alert(i+1);
					moverow = i;
					break;
				}
			}
			if (moverow <= 0 ) moverow = lcol+1;
		}
		
//			alert(insrow);
		col_sheet.SetRowJson(insrow, frmJson);
		
	}
	
	//테이블 정보를 업데이트 한다.
	var trow = grid_sheet.FindText("rqstSno", tblsno);
	if(trow > 0) {
		var tbljson = grid_sheet.GetRowJson(trow);
		
		col_sheet.SetCellValue(insrow, "rqstNo", tbljson.rqstNo);
		//col_sheet.SetCellValue(insrow, "fullPath", tbljson.fullPath);
		col_sheet.SetCellValue(insrow, "mtaTblPnm", tbljson.mtaTblPnm);
	}
	
	//변경의 경우 순서가 틀릴 경우 row를 이동한다.
	if (preord > 0 && preord != colord) {
//			alert(moverow);
		col_sheet.DataMove(moverow, insrow);
	}
	
	//컬럼 순서를 재조정한다.
	lcol = col_sheet.LastRow();
	var cntcolord = 1;
	for (var i=fcol; i<=lcol; i++) {
		
		if (col_sheet.GetCellValue(i, "rqstSno") == tblsno) {
			if(col_sheet.GetCellValue(i, "rqstDcd") == 'CU') {
				col_sheet.SetCellValue(i, "colOrd", cntcolord);
				//주석var pkyn = col_sheet.GetCellValue(i, "pkYn");
				//주석if (pkyn == "Y" )
				//주석	col_sheet.SetCellValue(i, "pkOrd", cntcolord);
				//주석else 
				//주석	col_sheet.SetCellValue(i, "pkOrd", "");
				
				cntcolord++;
			} 
		} else {
			break;
	  	}
	}
}

//초기화 후 전제반려를 등록요청한다.
function reqallreject() {
	showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
	chgallapprove (grid_sheet);
	$("#btnReqApprove").click();
	
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/meta/mta/ajaxgrid/mtatbl_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
		    var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
		    alert( msg + xhr.status + " " + xhr.statusText );
		  }else{
						  
			//요청자 아닌경우 버튼 disable
			disableBtnRqstUser("${waqMstr.rqstUserId}", "${sessionScope.loginVO.uniqId}");   
		 }
	});
}


/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {

	
	//alert(res.action);
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
// 		showMsgBox("INF", res.RESULT.MESSAGE);
	}

	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
//	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd").val("DMN");
	    		
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");
				loadDetail();
				doActionCol("Search");
	    	} 
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//요청서 저장 및 검증
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...

	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
				
	    		doAction("Search"); 
				loadDetail(); 
		    	
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
	    		
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				
	    	}
			
			break;
		
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/mta/mtatbl_rqst.do" />';
			var param = $('form[name=mstFrm]').serialize();
			location.href = url +"?"+param;
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
	
	var rowJson = grid_sheet.GetRowJson(row);
	
	var url = "<c:url value='/meta/mta/popup/mtaTblColRqst_pop.do'/>";
	
	var param = $("#mstFrm #rqstNo").serialize() + "&popRqst=Y";

	param += "&rqstSno="    + rowJson.rqstSno ; 	
	param += "&searchObj="  + rowJson.mtaTblPnm;
	param += "&subInfo=COL";
	param += "&tblRow="     + grid_sheet.GetSelectRow();
	
	openLayerPop(url, 1100, 780, param); 	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    if(row < 1) return;

  	//선택한 상세정보를 가져온다...
	
	//선택된 요청번호 기억하자!
	var SelectedRqstSno = grid_sheet.GetCellValue(row, "rqstSno");
    
	//tblClick(row);
	searchSubGrid(row);
}

function searchSubGrid(row) {

	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	//if(grid_sheet.GetColEditable(col)) return;
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="TBL" /> : ' + param.mtaTblLnm + ' [' + param.mtaTblPnm +']'; //테이블
	$('#tbl_sle_title').html(tmphtml);
	
	loadDetail(param);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
	col_sheet.DoSearch("<c:url value="/meta/mta/getmtacolrqstlist.do" />", param1);
	
// 	$('div#detailInfocol').empty();
	
	//검증결과 조회
	getRqstVrfLst(param1);

	//변경항목 조회
	if(typeof grid_vrf != "undefined") {
		grid_vrf.RemoveAll();
	}
	if(typeof grid_change != "undefined") {
		grid_change.RemoveAll();
	}
	if(typeof grid_changecol != "undefined") {
		grid_changecol.RemoveAll();
	}
	if(param.regTypCd == 'U') {
		getRqstChg(param1);
	}
}


function grid_sheet_OnSaveEnd(code, message) {

	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
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
	}else {
		researchMtaTbl();
	}
}

/*** 
 * 클릭된 메타데이터 테이블 row 조회
 **/
 var selectedMtaTblRowIdx = -1;
 
function researchMtaTbl() {

	
	if(SelectedRqstSno > 0) {

		selectedMtaTblRowIdx = grid_sheet.FindText("rqstSno", SelectedRqstSno+"", 0, -1);
	}

	if(selectedMtaTblRowIdx > 0) {
		grid_sheet.SetSelectRow(selectedMtaTblRowIdx);
		searchSubGrid(selectedMtaTblRowIdx);
	}
	
	//==========임시저장된 요청서 삭제 ==================
	if($("#mstFrm #rqstStepCd").val() == "N") {

		var tempRqstNo = "${tempRqstNo.RQST_NO}"; 

		var rowCnt = grid_sheet.RowCount();
		
		if(tempRqstNo != "" && rowCnt == 0 ){ 
			
			showReMsgBox("RNF","임시저장한 요청서가 있습니다.불러오시겠습니까?","ReSearch");	
		}
	}		
	//===========================================
	
} 



function reDelAction(){ 

	var DelJson = new Object();
	var arrData = new Array();

	var rowData = new Object();

	rowData.rqstNo = "${tempRqstNo.RQST_NO}";
	rowData.bizDcd = $("#mstFrm #bizDcd").val();

	arrData.push(rowData);
	
	DelJson.data = arrData; 

	//alert(JSON.stringify(DelJson));
	
	if(DelJson.data.length == 0) return; 
	
	var url = '<c:url value="/commons/rqstmst/deleteRqstList.do"/>';
	var param = "";
	IBSpostJson2(url, DelJson, param, ibsReCallback);
}

function ibsReCallback(res){

	var result = res.RESULT.CODE;
	if (result == 0) {
		// alert(res.RESULT.MESSAGE);

		// 공통메세지 팝업 : 성공 메세지...
		//showMsgBox("INF", res.RESULT.MESSAGE);

		$("#btnBlank").click(); 
	} else {
		// alsert("저장실패");
		// 공통메시지 팝업 : 실패 메세지...
		//showMsgBox("ERR", res.RESULT.MESSAGE);
	}	
}

function showReMsgBox(msgType, msg, action, nexturl) {

	var bodyht = $("body").height();
	// alert(bodyht);
	if (bodyht < 500)
		topsize = "center top+20";
	else
		topsize = "center top+200";

	// 메세지 팝업용 div 초기화 후 진행....
	$("#divMsgPopup").remove();

	var sHtml = "";
	sHtml += '<div id="divMsgPopup">                                                            ';
	// sHtml += '<div class="pop_wrap"> ';
	// sHtml += ' <div class="pop_container"> ';
	// sHtml += ' <div class="pop_cont"> ';
	sHtml += '            <div id="divMsgCont" style="text-align: center; padding:20px 10px 0 10px"></div>               ';
	sHtml += '            <div class="pop_bt03_57" style="margin: 15px 0;">                 ';

	if (gLangDcd == "en") {
		// Eglish
		sHtml += '                <button class="da_default_btn" id="btnMsgConf">Confirm</button>                                       ';
		sHtml += '                <button class="da_default_btn" id="btnMsgCancle">Cancel</button>                                     ';
	} else {
		// Korean
		sHtml += '                <button class="da_default_btn" id="btnMsgConf">확인</button>                                       ';
		sHtml += '                <button class="da_default_btn" id="btnMsgCancle">취소</button>                                     ';
	}

	sHtml += '            </div> ';
	sHtml += '            <div id="progressbar" style="display:none; text-align: center;"><img src="'
			+ containerPath
			+ '/images/loading/loading11.gif" alt="처리중"></div> ';
	// sHtml += ' </div> ';
	// sHtml += ' </div> ';
	// sHtml += '</div> ';
	sHtml += '</div>  ';

	$("body").append(sHtml);

	// ========메세지 내용 셋팅=======================

	$("#divMsgCont").html(msg);
	// $("#divMsgCont").css("font-size","13px");

	// ===========메세지 종류==========
	var digtit = "";
	var escyn = true; // esc close 기능
	switch (msgType) {

	
	case "RNF":	
		$("#divMsgTitle").html("Confirm");
		digtit = "Confirm";
		$("#btnMsgCancle").show(); // 취소 버튼 활성화
		break;	
	}

	// ========팝업 호출=======================
	$("#divMsgPopup").dialog({
		modal : true,
		draggable : true,
		resizable : false,
		width : 400,
		position : {
			my : "center top",
			at : topsize,
			of : "body"
		},
		title : digtit,
		closeOnEscape : false,
		// height: auto,
		// istitle: false,
		open : function(event, ui) {
			if ("PRC" == msgType)
				$(".ui-dialog-titlebar-close").hide();
			// $(".ui-dialog-titlebar-close", $(this).parent()).hide();
			$(".ui-dialog-titlebar", $(this).parent()).css('height', '30px');
			$(".ui-dialog-title").css({
				'font-size' : '13px',
				'line-height' : 2
			});
			// $(".ui-dialog-content").removeClass();
			// $(this).removeClass("ui-dialog-content ui-widget-content");
		},
		close : function(event, ui) {
			// alert("colse");
			$(this).remove();
		}
	});

	// 이렇게 하면 페이지 스크롤이 안되잔아...
	// $('body').css('overflow','hidden');

	// =======닫기버튼 Event Bind=======
	$("#btnMsgBoxClose").bind("click",

	function() {

		$("#divMsgPopup").dialog("close");
		// $("#divMsgPopup").dialog("destroy");
		$("#divMsgPopup").remove();
		
	});

	// =======닫기버튼 Event Bind=======
	$("#btnMsgCancle").bind("click",

	function() {

		
		if (msgType == "RNF") {

			reDelAction();

			$("#divMsgPopup").dialog("close");
			// $("#divMsgPopup").dialog("destroy");
			$("#divMsgPopup").remove(); 
		}
	});

	// =======확인버튼 Event Bind=======
	$("#btnMsgConf").bind("click",

	function() {

		$("#divMsgPopup").dialog("close");
		$("#divMsgPopup").remove();

		// $("#divMsgPopup").dialog("destroy");
		if (msgType == "RNF") {
			//cnfNextFunc = nexturl;
			if (typeof action == "string") {
				doAction(action);
			} else if (typeof action == "function") {
				action();
			}
		} 
	});

}


</script>
</head>

<body>

<div id="layer_div" >  
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">메타데이터 등록</div>
	</div>
</div>

<!-- 메뉴 메인 제목 -->

<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />
<div style="clear:both; height:5px;"><span></span></div>
    
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "280px");</script>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
	    <div class="selected_title" id="tbl_sle_title"><s:message code="MSG.TBL.CHC" /></div> <!-- 테이블을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs" style="display: none;">
	  <ul>
	    <li><a href="#tabs-1" id="tblinfo"><s:message code="TBL.DFNT.P" /></a></li><!-- 테이블 정의서 -->
	    <li><a href="#tabs-2" id="collist"><s:message code="CLMN.LST" /></a></li> <!-- 컬럼 목록 -->
	    <c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
	    	<li id="tabs-rqstvrf"><a href="#tabs-3"><s:message code="VRFC.RSLT" /></a></li>    <!-- 검증결과  -->
		    <li id="tabs-rqstchg"><a href="#tabs-5"><s:message code="TBL.CHG.ITEM" /></a></li> <!-- 테이블변경항목 -->
		    <li id="tabs-rqstchg"><a href="#tabs-6" id="colinfo"><s:message code="CLMN.CHG.ITEM" /></a></li> <!-- 컬럼변경항목 -->
	    </c:if>
	  </ul>
	  <div id="tabs-1">
		<!-- 	상세정보 ajax 로드시 이용 -->
		<div id="detailInfo"></div>
	  </div>
	  
	  <!-- 컬럼 목록 탭 -->
	  <div id="tabs-2">
		<%@include file="mtacol_rqst.jsp" %>
	  </div>
	  
	  <c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
	  	  <div id="tabs-3">
			<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
		  </div>
		  <div id="tabs-5">
			<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
		  </div>
		  <div id="tabs-6">
			<%@include file="../../commons/rqstmst/rqstChangeCol_lst.jsp" %>
		  </div>
	  </c:if>
	</div>

</div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<%-- <tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" /> --%>
</c:if>

</body>
</html>