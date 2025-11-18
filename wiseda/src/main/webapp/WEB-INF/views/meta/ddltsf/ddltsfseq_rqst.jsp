<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig"%>

<!-- 관계 등록 여부 -->
<%-- <c:set var="pdmrelyn"><s:message code="wiseda.pdm.rel" /></c:set> --%>
<html>
<head>
<title>DDL시퀀스 등록</title>

<script type="text/javascript">
var vPrjMngNo = "";
var vSrMngNo = "";
                       
$(document).ready(function() {
	

	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('DDLSEQ');

	
	// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	

		//======================================================
        // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
        //======================================================
//         initSearchPop();	//검색 팝업 초기화
//         initSearchButton();	//검색 버튼 초기화

		//탭 초기화....
		//$( "#tabs" ).tabs();
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		//검색조건 display none
		$("div#search_div").hide();

	    $("li#btnNew a").html('<span class="ui-icon ui-icon-folder-open"></span>이관대상추가');
	    $("li#btnChangAdd a").html('<span class="ui-icon ui-icon-folder-open"></span>이관대상삭제');
	    
		// 등록요청 Event Bind
		$("#btnRegRqst").click(function(){
			
// 			showMsgBox("PRC", "<s:message code="REQ.SUBMIT.INIT" />");
			var cntrqst = countGridValue(grid_sheet, "vrfCd", "1");
			
			var message = "";
			if (cntrqst > 0) message += "[DDL 시퀀스:"+cntrqst+"건]";
			message +="<br>"+"<s:message code="CNF.RQST" />";
			
			//등록가능한지 확인한다.vrfCd = 1
// 			var regchk = grid_sheet.FindText("vrfCd", "등록가능");
			//if(regchk) {alert (regchk);} 
			if(cntrqst > 0) {
				showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Submit');
			} else {
				showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
				return false;
			}
			
		});	
		
		//전체승인 버튼 이벤트 처리
		$("#btnAllApprove").click(function(){
			//showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
// 			doAllApprove(grid_sheet, "1");
			//마스터에 전체승인을 셋팅한다.  
			$("#mstFrm #rvwStsCd").val("1");
			chgallapprove(grid_sheet);
			$("#btnReqApprove").click();
			
		});
		//전체반려 버튼 이벤트 처리
		$("#btnAllReject").click(function(){
			rejectMsgBox("ALLREJECT", "<s:message code="REQ.APPROVE.REJECT" />", reqallreject);
// 			doAllApprove(grid_sheet, "2");
		});
		
		//검토처리 Event Bind
		$("#btnReqApprove").click(function(){
// 			showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
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
// 				approveMsgBox("APPROVE", "<s:message code="ERR.APPROVE.CNF" />");
				showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
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
			if (cntappr > 0) message += "[DDL 시퀀스:"+cntappr+"건 중 승인 "+cntAppr+"건, 반려 "+cntReject+"건]<br>";
			message += "<s:message code="CNF.APPR" />";

			showMsgBox("CNF", message, "Approve");	
			
// 			doAction("Approve");
			
		});		
                    
		//화면리로드
        $("#btnBlank").click( function(){
    		location.href = '<c:url value="/meta/ddltsf/ddltsfseq_rqst.do" />';
        } );
		
		
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });

        // 이관대상추가 Event Bind
        $("#btnNew").click(function(){
			var url   = "<c:url value="/meta/ddltsf/popup/ddltsfseq_pop.do"/>";
			var param = "popRqst=Y&rqstDcd=CU";
			openLayerPop(url, 800, 600, param);
        });
        
        // 이관대상삭제 Event Bind
        $("#btnChangAdd").click(function(){ 
        	//doAction("AddWam");
			var url   = "<c:url value="/meta/ddltsf/popup/ddltsfseq_pop.do"/>";
			var param = "popRqst=Y&rqstDcd=DD";
			openLayerPop(url, 800, 600, param);
        });
        
        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
    		//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL.TBL" />", 'Delete');
        	}
        }); //doAction("Delete");  });


        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).hide();
        
		//DDL조회 Event Bind
		$("#btnDdlSearch").button({
		       icons: { primary: "ui-icon-refresh" }
		}).click(function(){
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
        		showMsgBox("ERR", "조회할 DDL을 선택해 주세요.");
        		return;
        	}
        	
        	doAction("ShowDdl");
		});
		
	      //sr번호/프로젝트번호 전체반영
    	$("#prjAllApply").click(function(){
    		//선택해
    		var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
    		if(saveJson.data.length < 1){
    			showMsgBox("INF", "반영할 데이터를 선택 하십시오");
    			return;
    		}else{
    			//팝업호출
    			var param = "&sFlag=REQ"; //$("form#frmInput").serialize();
    			openLayerPop ("<c:url value='/meta/subjarea/popup/projSearchPop.do' />", 800, 600, param);
    		}
        });
	      
		//적용요청정보 전체반영
	   	$("#aplReqDtAllApply").click(function(){
	   		//선택해
	   		var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	   		if(saveJson.data.length < 1){
	   			showMsgBox("INF", "반영할 데이터를 선택 하십시오");
	   			return;
	   		}else{
	   			//팝업호출
	   			var param = ""; //$("form#frmInput").serialize();
	   			openLayerPop ("<c:url value='/meta/ddltsf/popup/aplRegDtPop.do' />", 400, 400, param);
	   		}
		});	
		
		//요청사유 전체반영
	   	$("#rqstResnAllApply").click(function(){
	   		//선택해
	   		var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	   		if(saveJson.data.length < 1){
	   			showMsgBox("INF", "반영할 데이터를 선택 하십시오");
	   			return;
	   		}else{
	   			//팝업호출
	   			var param = ""; //$("form#frmInput").serialize();
	   			openLayerPop ("<c:url value='/meta/stnd/popup/rqstResnPop.do' />", 600, 250, param);
	   		}
		});	

    }
);

$(window).load(function() {
	//그리드 초기화 
	initGrid();

	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
	loadDetail();

	doAction("Search");

	//작성전, 임시저장 상태일 경우만 DDL조회 비활성화
	/* if(rqststep == "N" || rqststep == "S"){
		$("#btnDdlSearch").hide();
		grid_sheet.SetColHidden("ddl"	,1);
	}else{
		$("#btnDdlSearch").show();
		grid_sheet.SetColHidden("ddl"	,0);
	} */
		
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
        
       // No.|상태|선택|검토상태|검토내용|요청사유|요청구분|등록유형|검증결과
       //|SR번호|서브프로젝트번호|DDL시퀀스ID|시퀀스(물리명)|시퀀스(논리명)
       //|DB접속대상(물리명)|DB사용자(물리명)|소스DB접속대상|소스DB스키마|타겟DB접속대상|타겟DB스키마
       //|명명규칙|사용용도|테이블명|컬럼명|L1코드|L3코드|권한목록
       //|INCREMENT BY|START WITH|MIN VALUE|MAX VALUE|CYCLE여부|ORDER여부|CACHE SIZE
       //|패키지명|클래스명|초기화구분코드|초기화실패업무영향도
       //|스크립트정보|적용요청구분|적용요청일자|DDL|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호

        var headtext  = "<s:message code='META.HEADER.DDLTSFSEQ.RQST1'/>";
        	headtext += "<s:message code='META.HEADER.DDLTSFSEQ.RQST2'/>";
            headtext += "<s:message code='META.HEADER.DDLTSFSEQ.RQST3'/>";
            headtext += "<s:message code='META.HEADER.DDLTSFSEQ.RQST4'/>";
            headtext += "<s:message code='META.HEADER.DDLTSFSEQ.RQST5'/>";
            
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
					{Type:"Text",   Width:100,  SaveName:"rqstResn",	Align:"Left", 	 Edit:0, Hidden:1},
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:0, KeyField:0},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",	Align:"Center", Edit:0},
                    
					{Type:"Text",   Width:100,   SaveName:"srMngNo",	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,   SaveName:"prjMngNo",	 Align:"Left",   Edit:0, Hidden:1},
					
					{Type:"Text",   Width:0,   SaveName:"ddlSeqId",	 Align:"Center",   Edit:0, Hidden:1},		
					{Type:"Text",   Width:130,   SaveName:"ddlSeqPnm",	 Align:"Center",   Edit:0, KeyField:0},
					{Type:"Text",   Width:0,   SaveName:"ddlSeqLnm",	 Align:"Center",   Edit:0, Hidden:1},		
					{Type:"Text",   Width:100,   SaveName:"dbConnTrgPnm",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"dbSchPnm",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
	                
                    {Type:"Text",     Width:100,  SaveName:"srcDbConnTrgPnm", Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"srcDbSchPnm",     Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"tgtDbConnTrgPnm", Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"tgtDbSchPnm",     Align:"Left",   Edit:0},
                    
					{Type:"Combo",   Width:100,   SaveName:"nmRlTypCd",	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Combo",   Width:100,   SaveName:"usTypCd",	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"pdmTblPnm",	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"pdmColPnm",	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"l1cdPnm",	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"l3cdPnm",	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"grtLst",	 Align:"Center",   	   Edit:0, Hidden:1},
					
					{Type:"Text",   Width:100,   SaveName:"incby",	 Align:"Center",   Edit:0, KeyField:0},
					{Type:"Text",   Width:100,   SaveName:"strtwt",	 Align:"Center",   Edit:0, KeyField:0},
					{Type:"Text",   Width:100,   SaveName:"minval",	 Align:"Center",   Edit:0, KeyField:0},
					{Type:"Text",   Width:100,   SaveName:"maxval",	 Align:"Center",   Edit:0, KeyField:0},
					
					{Type:"Combo",   Width:100,   SaveName:"cycYn",	 Align:"Center",   Edit:0},
					{Type:"Combo",   Width:100,   SaveName:"ordYn",	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,   SaveName:"cacheSz",	 Align:"Center",   Edit:0},
               
					{Type:"Text",   Width:100,   SaveName:"pckgNm",	 Align:"Center",   	   Edit:0, Hidden:1},
					{Type:"Combo",   Width:100,   SaveName:"seqClasCd",	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Combo",   Width:100,   SaveName:"seqInitCd",	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"bizIfnc",	 Align:"Center",   Edit:0, Hidden:1},
					
					
                    {Type:"Text",   Width:40,  SaveName:"scrtInfo",     Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:80,  SaveName:"aplReqTypCd",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,  SaveName:"aplReqdt",  Align:"Left",   	Edit:0, Hidden:1},
                    
                    {Type:"CheckBox",   Width:70,   SaveName:"ddl",	 	Align:"Center", Edit:1, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0}, 
					{Type:"Text",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		
		SetColProperty("nmRlTypCd", 	${codeMap.nmRlTypCdibs});
		SetColProperty("usTypCd", 	${codeMap.usTypCdibs});
		
		SetColProperty("cycYn", 		{ComboCode:"N|Y", 	ComboText:"N|Y"});
		SetColProperty("ordYn", 		{ComboCode:"N|Y", 	ComboText:"N|Y"});
		SetColProperty("aplReqTypCd", ${codeMap.aplReqTypCdibs});
		
		SetColProperty("seqClasCd", ${codeMap.seqClasCdibs});
		SetColProperty("seqInitCd", ${codeMap.seqInitCdibs});
		
        
        InitComboNoMatchText(1, "");

		SetColHidden("rqstNo"	,1);
		SetColHidden("rqstSno"	,1);
        
        
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
        	$('#seq_sle_title').html('');
        	grid_change.RemoveAll();
        	var rqststep = $("#mstFrm #rqstStepCd").val();
        	if(rqststep == "N" || rqststep == "S") {
        		grid_vrf.RemoveAll();	
        	}
            break;
        
        case "AddWam": //기존 시퀀스 추가
			var url   = "<c:url value="/meta/ddltsf/popup/ddltsfseq_pop.do"/>";
			var param = "popRqst=Y";
			openLayerPop(url, 820, 600, param);
		break;

		case "Reset" :
			//폼내용 초기화.....
			$("#frmInput")[0].reset();        	
            break;
            
        case "Delete" :
	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
	    	delCheckIBS(grid_sheet);
	    	
	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
			
			var url = "<c:url value="/meta/model/delddlseqrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
        	break;

        case "SaveRow" :
			// 공통으로 처리...
			var saveJson = getform2IBSjson($("#frmInput"));

			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/ddltsf/regddltsfseqrqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			param += "&ddlTrgDcd=D"; //개발단계
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
        	
            var url = "<c:url value="/meta/ddl/regddlseqrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
            param += "&ddlTrgDcd=D"; //개발단계
        	IBSpostJson(url, param, ibscallback);
        	break;
        
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/ddltsf/getddltsfseqrqstlist.do" />", param);
			//컬럼 리스트 조회...
// 			doActionCol("Search");
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
						
			break;
        	
		case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			doRequest(param);
			
			break;
	   
		case "Approve" : //결재처리
			
			var saveJson = grid_sheet.GetSaveJson(0);
			//개발DDL 발행 예외처리(2018-03-21) 차후 관리자 메뉴에서 제어
			//적용요청일자 체크 현재일자보다 작을경우 반려만 가능
			//2. 필수입력 누락인 경우
// 			alert(saveJson.Code);
			if (saveJson.Code == "IBS000") return;
// 			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/ddl/approveddlseq.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":  //엑셀업로드
        	var url = "<c:url value="/meta/ddl/popup/ddlseq_xls.do" />";
			
// 			var xlspopup = OpenWindow(url ,"pdmtblxls","800","600","yes");
			openLayerPop(url, 800, 600);
			break;
			
		case "ShowDdl" :
			var SelectJson = grid_sheet.GetSaveJson({StdCol:"ddl"});
			     	
			var tmpVal = 0;
			for(var i=0; i<SelectJson.data.length; i++) {
				if(SelectJson.data[i].ddl == '1'){
					tmpVal = 1;
					break;
				}
			}
			if (tmpVal == 0) {
				showMsgBox("ERR", "조회할 DDL을 선택해 주세요.");
				return;
			}
			
			var url = '<c:url value="/meta/ddl/popup/ddlwaqscript_pop.do"/>';
			OpenModal(url, "DdlScript", 800, 600);
		break;

        case "updateSrMngNo":
        	var saveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(saveJson.data.length == 0) return;
        	
        	var param = $('form[name=mstFrm]').serialize();
        	param += "&tblNm="+ "WAQ_DDL_SEQ";
        	param += "&ddlTrgDcd=D";
//     		param += "&prjMngNo="+ $("#frmInput #prjMngNo").val();
//     		param += "&srMngNo="+ $("#frmInput #srMngNo").val();
    		param += "&prjMngNo="+vPrjMngNo;
    		param += "&srMngNo="+ vSrMngNo;
    		var url = "<c:url value='/meta/model/ajax/updateSrMngNo.do' />";
    		IBSpostJson2(url, saveJson, param, ibscallback);
    		
        	break;
        	
        case "updateAplReqdt":
        	var saveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(saveJson.data.length == 0) return;
    		var param = $("#mstFrm").serialize();
    		param += "&tblNm="+ "WAQ_DDL_SEQ";
    		param += "&ddlTrgDcd="+"D"; //개발단계
    		param += "&aplReqTypCd="+ $("#frmInput #aplReqTypCd").val();
    		param += "&aplReqdt="+ $("#frmInput #aplReqdt").val();
    		
    		var url = "<c:url value='/meta/ddl/ajax/updateAplReqdtAll.do' />";
    		IBSpostJson2(url, saveJson, param, ibscallback);
    		
        	break;
        	
        case "updateRqstResn":
        	var saveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(saveJson.data.length == 0) return;
    		var param = $("#mstFrm").serialize();
//     		param += $("#frmInput").serialize();
    		param += "&tblNm="+ "WAQ_DDL_SEQ";
    		param += "&ddlTrgDcd="+"D"; //개발단계
    		param += "&rqstResn="+ encodeURIComponent($("#frmInput #rqstResn").val() );
    		var url = "<c:url value='/meta/stnd/ajax/updateRqstResnAll.do' />";
    		IBSpostJson2(url, saveJson, param, ibscallback);
    		
        	break;	        	
		
    }       
}

function returnAplReqdtAllPop (aplReqTypCd, aplReqdt) {
	$("#frmInput #aplReqTypCd").val(aplReqTypCd);
	$("#frmInput #aplReqdt").val(aplReqdt);
	doAction("updateAplReqdt");
}

//요청사유전체반영
function returnRqstResnAllPop (rqstResn) {
	$("#frmInput #rqstResn").val(rqstResn);
	doAction("updateRqstResn");
}

//초기화 후 전제반려를 등록요청한다.
function reqallreject() {
	//showMsgBox("PRC", "<s:message code="REQ.APPROVE.VALID" />");
	chgallapprove (grid_sheet);
	//$("#btnReqApprove").click();
	
}


//상세정보호출
function loadDetail(param) {
	
	
	$('div#detailInfo').load('<c:url value="/meta/ddltsf/ajaxgrid/ddltsfseq_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "상세정보 호출중 오류발생...";
			    alert( msg + xhr.status + " " + xhr.statusText );
	      } else {
	    	  if(param!=null){
	    	    $("#rvwStsCd").val(param.rvwStsCd);
	    	    $("#rvwConts").val(param.rvwConts);
	    	  }
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
	    			$("#aplReqDtAllApply").show();
	    			$("#prjAllApply").show(); 
	    			$("#rqstResnAllApply").show();
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				$("div#detailInfo").empty();

				doAction("Search");
				
// 				col_sheet.RemoveAll();
				
// 				loadDetail();
// 				doActionCol("Search");
	    	} 
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//요청서 저장 및 검증
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
			
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
// 	    		alert(res.resultVO.rqstNo);
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd").val("DMN");
	    		
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    			$("#aplReqDtAllApply").show();
	    			$("#prjAllApply").show(); 
	    			$("#rqstResnAllApply").show();
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");
				

	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/ddltsf/ddltsfseq_rqst.do" />';
			if(!isBlankStr(res.resultVO.rqstNo)) {
				url = containerPath + res.resultVO.bizInfo.url;
			}
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
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    
    if(row < 1) return;
    var rqststep = $("#mstFrm #rqstStepCd").val();
    
//     tblClick(row);
	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '시퀀스 : ' + param.ddlSeqPnm;
	$('#seq_sle_title').html(tmphtml);
	
	loadDetail(param);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;

	if(rqststep == "N" || rqststep == "S"){
		grid_vrf.RemoveAll();
		getRqstVrfLst(param1);
	}
	
	//변경항목 조회
	grid_change.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1);
	}
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
// 		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if(code == 0) {
// 		$('#btnfrmReset').click();
		//alert("Search End");
		//시퀀스 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
		var rqstsno = $("#frmInput #rqstSno").val();
		if (!isBlankStr(rqstsno)) {
			var param = $("#mstFrm").serialize();
				param += "&rqstSno=" + rqstsno;
			loadDetail(param);
			return;
    	} 
		
// 		var crow = grid_sheet.GetSelectRow();
// 		if(crow > 0){
// 			var param = grid_sheet.GetRowJson(crow);
// 			loadDetail(param);
// 			return;
// 		}

		
	} else {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
}


</script>
</head>

<body>

	<div id="layer_div">
		<!-- 메뉴 메인 제목 -->
		<div class="menu_subject">
			<div class="tab">
				<div class="menu_title">DDL시퀀스 등록</div>
			</div>
		</div>
		<!-- 메뉴 메인 제목 -->
		<div style="clear: both; height: 5px;">
			<span></span>
		</div>
		
		<div style="clear: both; height: 5px;">
			<span></span>
		</div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />

		<div style="clear: both; height: 5px;">
			<span></span>
		</div>

		<!-- 그리드 입력 입력 -->
		<div id="grid_01" class="grid_01">
			<script type="text/javascript">createIBSheet("grid_sheet", "100%", "450px");</script>
			<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
		</div>
		<!-- 그리드 입력 입력 -->

		<div style="clear: both; height: 5px;">
			<span></span>
		</div>

		<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
		<div class="selected_title_area">
			<div class="selected_title" id="seq_sle_title"></div>
		</div>

		<div style="clear: both; height: 5px;">
			<span></span>
		</div>
		<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">시퀀스 정의서</a></li>
				
				<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
					<li id="tabs-rqstvrf"><a href="#tabs-2">검증결과</a></li>
			    </c:if>
					<li id="tabs-rqstchg"><a href="#tabs-3">시퀀스변경항목</a></li>
				
			</ul>
			<div id="tabs-1">
				<!-- 	상세정보 ajax 로드시 이용 -->
				<div id="detailInfo"></div>
			</div>
			
			<c:if test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
			<div id="tabs-2">
				<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp"%>
			</div>
			</c:if>
			<div id="tabs-3">
				<%@include file="../../commons/rqstmst/rqstChange_lst.jsp"%>
			</div>
		</div>


		<div style="clear: both; height: 5px;">
			<span></span>
		</div>

		<tiles:insertTemplate
			template="/WEB-INF/decorators/requestMstForm.jsp" />
		<c:if
			test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
			<tiles:insertTemplate
				template="/WEB-INF/decorators/approveStatus.jsp" />
		</c:if>
	</div>
</body>
</html>