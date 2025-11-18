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

<!-- 작업처리번호 -->
<!-- wrkPrcNo -->

<html>
<head>
<title>DDL권한 요청</title>

<script type="text/javascript">
                    
$(document).ready(function() {
	

	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('DDLGRT');

	
	$(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	

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

		$("#btnNew").click(function(){ doAction("New");  });

		$("li#btnChangAdd a").html('<span class="ui-icon ui-icon-folder-open"></span>변경대상 추가');

		// 등록요청 Event Bind
		$("#btnRegRqst").click(function(){
		
			showMsgBox("PRC", "<s:message code="REQ.SUBMIT.INIT" />");
			var cntrqst = countGridValue(grid_sheet, "vrfCd", "1");
			
			var message = "";
			if (cntrqst > 0) message += "[DDL 권한:"+cntrqst+"건]";
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
			//allApproveMsgBox("ALLAPPROVE", "<s:message code="REQ.APPROVE.ALLAPRV" />", "allApprove");
			$("#mstFrm #rvwStsCd").val("1");
			chgallapprove (grid_sheet);
		});
		//전체반려 버튼 이벤트 처리
		$("#btnAllReject").click(function(){
			rejectMsgBox("ALLREJECT", "<s:message code="REQ.APPROVE.REJECT" />", "allReject");
// 			doAllApprove(grid_sheet, "2");
		});
		
		//검토처리 Event Bind
		$("#btnReqApprove").click(function(){
			//그리드 변경대상 체크한다.
			if (!chkSheetDataModified(grid_sheet)) {
				showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
				return false;
			}
			// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
			if (chkRvwStsCd(grid_sheet, "rvwStsCd") > -1) {
				showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
				return false;
			};
			
			//반려 선택시 반려사유를 입력하도록 한다.
			var tmprow = chkRvwCont(grid_sheet, "rvwStsCd", "rvwConts");
			if (tmprow > 0 ) {
				showMsgBox("INF", "<s:message code="ERR.REJECT2" />");
				grid_sheet.SetSelectRow(tmprow);
				//선택한 상세정보를 가져온다...
				var param =  grid_sheet.GetRowJson(tmprow);
			
				//선택한 그리드의 row 내용을 보여준다.....
				var tmphtml = '표준단어 : ' + param.msgCd + ' [' + param.msgConts +']';
				$('#msg_sel_title').html(tmphtml);
				
				//var param = grid_sheet.GetRowJson(row);
				var param1 = $("#mstFrm").serialize();
				param1 += "&rqstSno=" + param.rqstSno;
				
				//param = 
				loadDetail(param1);
				
				//검증결과 조회
//	 			getRqstVrfLst(param1);
//	 			$("#frmInput #rvwConts").focus();
				return false;
			}
			
			doAction("Approve");
			
		});		
                    
		//화면리로드
        $("#btnBlank").click( function(){
    		location.href = '<c:url value="/meta/ddl/ddlgrt_rqst.do" />';
        } );
		
		
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });

        
        // 변경대상 추가 Event Bind
        $("#btnChangAdd").click(function(){ 
        	
        	doAction("AddWam");
 
        
        }); //doAction("NewChg"); });
        
        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
    		//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			showMsgBox("CNF", "<s:message code="CNF.DEL.TBL" />", 'Delete');
        	}
        }); //doAction("Delete");  });


        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
        

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
        SetMergeSheet(5);
        
        var headtext2  = "No.|상태|선택|검토상태|검토내용|요청사유|요청구분|등록유형|검증결과";
	    	headtext2 += "|DDL권한ID|Grantor|Grantor|Grantor|Grantor";
	    	headtext2 += "|오브젝트ID|오브젝트명|오브젝트논리명|오브젝트유형";
	    	headtext2 += "|Granted to|Granted to|Granted to|Granted to";
	    	headtext2 += "|권한|권한|권한|권한|권한|SYNONYM여부";
	    	headtext2 += "|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";
        
        var headtext  = "No.|상태|선택|검토상태|검토내용|요청사유|요청구분|등록유형|검증결과";
        	headtext += "|DDL권한ID|DB접속대상ID|DB접속대상물리명|DB사용자ID|DB사용자물리명";
        	headtext += "|오브젝트ID|오브젝트명|오브젝트논리명|오브젝트유형";
        	headtext += "|DB접속대상ID|DB접속대상물리명|ROLE ID|ROLE명";
        	headtext += "|SELECT|INSERT|UPDATE|DELETE|EXECUTE|SYNONYM여부";
        	headtext += "|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호";
      
        var headers = [
                    {Text:headtext2, Align:"Center"}
                    ,{Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstResn",	Align:"Left", 	 Edit:1, Hidden:1},
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:80,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:120,  SaveName:"vrfCd",	Align:"Center", Edit:0},
                    
					
					{Type:"Text",   Width:0,   SaveName:"ddlGrtId",	 Align:"Center",   Edit:0, Hidden:1},		
					{Type:"Text",   Width:100,   SaveName:"grtorDbId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"grtorDbPnm",	 Align:"Center",   Edit:0, Hidden:0, KeyField:0},		
					{Type:"Text",   Width:100,   SaveName:"grtorSchId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"grtorSchPnm",	 Align:"Center",   Edit:0, KeyField:0},
					
					{Type:"Text",   Width:100,   SaveName:"ddlObjId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"ddlObjPnm",	 Align:"Center",   Edit:0, KeyField:0},
					{Type:"Text",   Width:100,   SaveName:"ddlObjLnm",	 Align:"Center",   Edit:0, KeyField:0},
					{Type:"Combo",   Width:100,   SaveName:"ddlObjTypCd",	 Align:"Center",   Edit:0, KeyField:0},
					
					{Type:"Text",   Width:100,   SaveName:"grtedDbId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"grtedDbPnm",	 Align:"Center",   Edit:0, Hidden:0, KeyField:0},		
					{Type:"Text",   Width:100,   SaveName:"grtedSchId",	 Align:"Center",   Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:100,   SaveName:"grtedSchPnm",	 Align:"Center",   Edit:0, KeyField:0},
					
					{Type:"CheckBox",   Width:50,   SaveName:"selectYn",	 Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
					{Type:"CheckBox",   Width:50,   SaveName:"insertYn",	 Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
					{Type:"CheckBox",   Width:50,   SaveName:"updateYn",	 Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
                    {Type:"CheckBox",   Width:50,   SaveName:"deleteYn",     Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
                    {Type:"CheckBox",   Width:50,   SaveName:"executeYn",    Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N", Hidden:1},
                    {Type:"Text",   Width:50,   SaveName:"synonymYn",     Align:"Center",   Edit:0, Hidden:1},
                    
					{Type:"Text",   Width:100,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
                    {Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		SetColProperty("ddlObjTypCd",	${codeMap.objDcdibs});
		
//		SetColProperty("nmRlTypCd", 	${codeMap.nmRlTypCdibs});
//		SetColProperty("usTypCd", 	${codeMap.usTypCdibs});
		
//		SetColProperty("cycYn", 		{ComboCode:"N|Y", 	ComboText:"N|Y"});
//		SetColProperty("ordYn", 		{ComboCode:"N|Y", 	ComboText:"N|Y"});
        
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
        	$('#grt_sel_title').html('');
        	grid_change.RemoveAll();
        	grid_vrf.RemoveAll();
        	
            break;
        
        case "AddWam": //기존 권한 추가
// 			var url   = "<c:url value="/meta/ddl/popup/ddlgrt_pop.do"/>";
// 			var param = "popRqst=Y";
// 			openLayerPop(url, 800, 650, param);

	         var url   = "<c:url value="/meta/ddl/popup/ddlGrtPop.do"/>";
	         var param = "popRqst=Y";
	         openLayerPop(url, 1000, 650, param);
		
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
			if (DelJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(DelJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/ddl/delddlgrtrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
		case "SaveRow" :
			// 공통으로 처리...
			
			var saveJson = getform2IBSjson($("#frmInput"));
// 			saveJson = $("#frmInput").serializeArray();
// 			saveJson = $("#frmInput").serializeObject();

// 			if (saveJson.Code == "IBS000") return;
			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/ddl/regddlgrtrqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			
			IBSpostJson2(url, saveJson, param, ibscallback);
			
			break;        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = grid_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/ddl/regddlgrtrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
            param += "&ddlTrgDcd=D"; //개발단계
        	IBSpostJson(url, param, ibscallback);
        	break;
        
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/ddl/getddlgrtrqstlist.do" />", param);
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
			
			//2. 필수입력 누락인 경우
			if (saveJson.Code == "IBS000") return;
// 			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/ddl/approveddlgrt.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":  //엑셀업로드
        	var url = "<c:url value="/meta/ddl/popup/ddlgrt_xls.do" />";
			
// 			var xlspopup = OpenWindow(url ,"pdmtblxls","800","600","yes");
			openLayerPop(url, 800, 600);
			break;
			
        case "allReject":
        	//doAllReject(grid_sheet, "2",$("#mstFrm #rvwConts").val());
        	chgallapprove (grid_sheet);
       	break;
		case "allApprove":
			doAllApproveWithRvwCont(grid_sheet, "1",$("#mstFrm #rvwConts").val());
       	break;
       	
		
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
	$('div#detailInfo').load('<c:url value="/meta/ddl/ajaxgrid/ddlgrt_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "상세정보 호출중 오류발생...";
			    alert( msg + xhr.status + " " + xhr.statusText );
	      } else {
	    	  
	      }
		  
	});
}


/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
// 		showMsgBox("INF", res.RESULT.MESSAGE);
	}
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd").val("DMN");
	    		
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
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
				

	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/ddl/ddlgrt_rqst.do" />';
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
    
    if(row < 2) return;
    
//     tblClick(row);
	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '권한 : ' + param.ddlObjPnm;
	$('#grt_sel_title').html(tmphtml);
	
	
	loadDetail(param);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	

	//검증결과 조회
	getRqstVrfLst(param1);
	
	
	//변경항목 조회
	grid_change.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1);
	}
	
    
}


function grid_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
	} else {
		alert("저장 실패했습니다.");
	}
}

function grid_sheet_OnSearchEnd(code, message) {
	if(code == 0) {
// 		$('#btnfrmReset').click();
		//권한 요청 리스트가 조회되면...
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
				<div class="menu_title">DDL권한 등록</div>
			</div>
		</div>
		<!-- 메뉴 메인 제목 -->
		<div style="clear: both; height: 5px;">
			<span></span>
		</div>
		<!-- 
		<div class="tb_comment">
       		<b>* 권한 명명규칙</b>
       		<br/>- 엔티티기반 : 테이블명+_+S+X(사용용도유형1자리(일반:G/일별:D/일중:E))+##(일련번호2자리)
       		<br/>- 컬럼기반 : 컬럼명+_+S+X(사용용도유형1자리(일반:G/일별:D/일중:E))+#(일련번호1자리)
       		<br/>- 주제영역기반 : L1코드+L3코드+###(일련번호3자리)+N+_+##(자릿수2자리)+_(옵션)+YYYYMMDD(일자,옵션)+_+S+X(사용용도유형1자리(일반:G/일별:D/일중:E))
       	</div>
		 -->
		<div style="clear: both; height: 5px;">
			<span></span>
		</div>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />

		<div style="clear: both; height: 5px;">
			<span></span>
		</div>

		<!-- 그리드 입력 입력 -->
		<div id="grid_01" class="grid_01">
			<script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>
			<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
		</div>
		<!-- 그리드 입력 입력 -->

		<div style="clear: both; height: 5px;">
			<span></span>
		</div>

		<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
		<div class="selected_title_area">
			<div class="selected_title" id="grt_sel_title">권한을 선택하세요.</div>
		</div>

		<div style="clear: both; height: 5px;">
			<span></span>
		</div>
		<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">권한 정의서</a></li>
				
				<c:if
					test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
					<li id="tabs-rqstvrf"><a href="#tabs-2">검증결과</a></li>
					<li id="tabs-rqstchg"><a href="#tabs-3">권한변경항목</a></li>
				</c:if>
			</ul>
			<div id="tabs-1">
				<!-- 	상세정보 ajax 로드시 이용 -->
				<div id="detailInfo"></div>
			</div>

			
			<c:if
				test="${waqMstr.rqstStepCd == 'N' or waqMstr.rqstStepCd == 'S'}">
				<div id="tabs-2">
					<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp"%>
				</div>
				<div id="tabs-3">
					<%@include file="../../commons/rqstmst/rqstChange_lst.jsp"%>
				</div>
			</c:if>
		</div>


		<div style="clear: both; height: 25px;">
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