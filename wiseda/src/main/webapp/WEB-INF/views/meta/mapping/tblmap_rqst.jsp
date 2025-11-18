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
<title><s:message code="TBL.MAPG.DFNT.P.REG"/></title> <!-- 테이블매핑정의서 등록 -->

<script type="text/javascript">

var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...

                       
$(document).ready(function() {

		//$( "#tabs" ).tabs();
		
		//업무구분상세 초기화...
		$("#mstFrm #bizDtlCd").val('TMP');
		
		// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		//검색조건 display none
// 		$("div#search_div").hide();

		// 등록요청 Event Bind
		$("#btnRegRqst").click(function(){
			
			//alert("등록");
			
			//등록가능한지 확인한다.vrfCd = 1
			var regchk = grid_sheet.FindText("vrfCd", "<s:message code='REG.POSB' />"); //등록가능
			//if(regchk) {alert (regchk);} 
			if(regchk > 0) {
				showMsgBox("CNF", "<s:message code="CNF.SUBMIT" />", 'Submit');
			} else {
				showMsgBox("INF", "<s:message code="ERR.SUBMIT" />");
				return false;
			}
			
		});	
		
		//전체승인 버튼 이벤트 처리
		$("#btnAllApprove").click(function(){
			doAllApprove2Header(grid_sheet, "1");
//	 		doAllApprove(cdval_sheet, "1");
			
		});
		
		//전체반려 버튼 이벤트 처리
		$("#btnAllReject").click(function(){
			doAllApprove2Header(grid_sheet, "2");
//	 		doAllApprove(cdval_sheet, "2");
		});
		
		//검토처리 Event Bind
		$("#btnReqApprove").click(function(){
			//alert("결재처리")
			//그리드 변경대상 체크한다.
			if (!chkSheetDataModified(grid_sheet)) {
				showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
				return false;
			}
			// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
			if (chkRvwStsCd(grid_sheet, "rvwStsCd") > -1) {
				//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
				showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
				return false;
			};
			
			//반려 선택시 반려사유를 입력하도록 한다.
			var tmprow = chkRvwCont(grid_sheet, "rvwStsCd", "rvwConts");
			if (tmprow > 0 ) {
				showMsgBox("INF", "<s:message code="ERR.REJECT" />");
				grid_sheet.SetSelectRow(tmprow);
				//선택한 상세정보를 가져온다...
				var param =  grid_sheet.GetRowJson(tmprow);
			
				//선택한 그리드의 row 내용을 보여준다.....
				var tmphtml = '<s:message code="TBL" /> : ' + param.tgtTblLnm + ' [' + param.tgtTblPnm +']'; //테이블
				$('#tbl_sle_title').html(tmphtml);
				
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
                    
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
        
    	//추가 Event Bind
    	$("#btnRqstNew").show();
    	
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  }).show();

        
        // 변경대상 추가 Event Bind
        $("#btnChangAdd").click(function(){ 
        	
        	doAction("AddWam");
        	
//         	openSearchPop("<c:url value='/meta/model/pop/pdmtblSearchPop.do' />"); 
        
        }).show(); //doAction("NewChg"); });
        
        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
    		//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
        	}
        }).show(); //doAction("Delete");  });


        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).show();
        
     	// 신규요청서 Event Bind
        $("#btnBlank").click( function(){ doAction("NewRqst"); } );
        
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
      
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, grid_sheet);
	checkApproveYn($("#mstFrm"));
	
	loadDetail();

	doAction("Search");
	
	//$( "#layer_div" ).show();
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
          
        var headers = [
                       {Text: "<s:message code='META.HEADER.TBLMAP.RQST1'/>"},
                       /* No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|테이블매핑ID|매핑정의서ID|매핑정의서유형|매핑일련번호|테이블매핑유형|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|선택/스킵조건|조인조건|사전이행 고려사항|응용담당자ID|응용담당자명|전환담당자ID|전환담당자명|참고사항|요청일시|요청자ID|요청자명|요청번호|요청일련번호 */
                       {Text: "<s:message code='META.HEADER.TBLMAP.RQST2'/>", Align:"Center"}
                       /* No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|테이블매핑ID|매핑정의서ID|매핑정의서유형|매핑일련번호|테이블매핑유형|DB명|테이블명(물리명)|테이블명(논리명)|이행유형|시스템명|업무명|DB명|테이블명(물리명)|테이블명(논리명)|선택/스킵조건|조인조건|사전이행 고려사항|응용담당자ID|응용담당자명|전환담당자ID|전환담당자명|참고사항|요청일시|요청자ID|요청자명|요청번호|요청일련번호 */
                   ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:0, KeyField:1},						
					{Type:"Combo",  Width:60,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
					{Type:"Combo",  Width:60,  SaveName:"vrfCd",	Align:"Center", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"tblMapId", 	 Align:"Center",   Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"mapDfId", 	 Align:"Center",   Edit:0, KeyField:1},
					{Type:"Combo",   Width:100,  SaveName:"mapDfType", 	 Align:"Center",   Edit:0},
					
					
					{Type:"Text",   Width:100,  SaveName:"mapSno", 	 Align:"Center",   Edit:0, KeyField:1},
					{Type:"Combo",   Width:100,  SaveName:"tblMapType", 	 Align:"Center",   Edit:0},
					
					{Type:"Text",   Width:100,  SaveName:"tgtDbPnm", 	 Align:"Center",   Edit:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"tgtTblPnm", 	 Align:"Center",   Edit:0, KeyField:1},
					{Type:"Text",   Width:100,  SaveName:"tgtTblLnm", 	 Align:"Center",   Edit:0},
					{Type:"Combo",   Width:100,  SaveName:"tgtFlfType", 	 Align:"Center",   Edit:0},
					
					{Type:"Text",   Width:100,  SaveName:"srcSysNm", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"srcBizNm", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"srcDbPnm", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"srcTblPnm", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"srcTblLnm", 	 Align:"Center",   Edit:0},
					
					{Type:"Text",   Width:100,  SaveName:"sltSkpCndNm", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"jnCndNm", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"preFlfDescn", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"appCrgpId", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"appCrgpNm", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"cnvsCrgpId", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"cnvsCrgpNm", 	 Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"refDescn", 	 Align:"Center",   Edit:0},
				
                    {Type:"Text",   Width:130,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:150,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
		
		SetColProperty("mapDfType", 	${codeMap.mapDfTypCdibs});
		SetColProperty("tgtFlfType", 	${codeMap.tgtFlfTypCdibs});
		SetColProperty("tblMapType", 	${codeMap.tblMapTypCdibs});		
		
        
        InitComboNoMatchText(1, "");
        
		
		//SetColHidden("rqstNo"	,1);
		//SetColHidden("rqstSno"	,1);
        
        
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
            
        case "NewRqst": //신규요청서
       	 var url = '<c:url value="/meta/mapping/tblmap_rqst.do" />';
		 location.href = url;
        break;
        
        case "AddWam": //기존 테이블 추가
			var url   = "<c:url value="/meta/mapping/popup/tblMapSearchPop.do"/>";
			var param = "popRqst=Y";
// 			var popup = OpenWindow(url+param,"tblMapSearch","800","600","yes");
			openLayerPop(url, 800, 600, param);
		
		break;

		case "Reset" :
			
			//폼내용 초기화.....
// 			$("#frmInput")[0].reset();        	

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
			
			var url = "<c:url value="/meta/mapping/deltblmaprqstlist.do"/>";
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
			
			var url = "<c:url value="/meta/mapping/regtblmaprqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			//alert(saveJson.Code);
			IBSpostJson2(url, saveJson, param, ibscallback);
			//alert("저장완료.");
			
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
        	
            var url = "<c:url value="/meta/mapping/regtblmaprqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallback);
        	break;
        
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/mapping/gettblmaprqstlist.do" />", param);
			//코드도메인 조회...
// 			var param = "rqstNo="+$("#mstFrm #rqstNo").val();
// 			cdval_sheet.DoSearch("<c:url value="/meta/stnd/getcdvalrqstlist.do" />", param);
//			doActionCol("Search");
			
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
// 			alert(saveJson.Code);
			if (saveJson.Code == "IBS000") return;
// 			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/mapping/approvetblmap.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'테이블매핑정의서등록요청'});
            break;
            
        case "LoadExcel":  //엑셀업로드
			var url = "<c:url value="/meta/mapping/popup/tblmap_xls.do" />";
			
// 			var xlspopup = OpenWindow(url ,"tblmapxls","800","600","yes");
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
			
			var saveJson = getform2IBSjson($("#frmColInput"));
// 			saveJson = $("#frmInput").serializeArray();
// 			saveJson = $("#frmInput").serializeObject();

// 			if (saveJson.Code == "IBS000") return;
			if(saveJson.data.length == 0) return;
			
			var url = "<c:url value="/meta/model/regpdmcolrqstlist.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
        
            break;
        case "SaveCol": //컬럼 리스트 저장
        	var saveJson = col_sheet.GetSaveJson(0);
        	if (saveJson.Code == "IBS000") return;
        	if (saveJson.Code == "IBS010") return;
        	
			var url = "<c:url value="/meta/model/regpdmcolrqstlist.do"/>";
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
			
			var url = "<c:url value="/meta/model/delpdmcolrqstlist.do"/>";
			var param = $("#mstFrm").serialize();
			IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
    }       
}
 

//상세정보호출
function loadDetail(param) {
	//alert("여기??");
	$('div#detailInfo').load('<c:url value="/meta/mapping/ajaxgrid/tblmap_rqst_dtl.do"/>', param, function( response, status, xhr ) {
		  if ( status == "error" ) {
			    var msg = "<s:message code='MSG.DTL.INFO.EROR' />..."; //상세정보 호출중 오류발생
			    alert( msg + xhr.status + " " + xhr.statusText );
			  }
	});
}


/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
// 	alert(res.action);
	if ('<%=WiseMetaConfig.RqstAction.APPROVE%>' != res.action) {
		showMsgBox("INF", res.RESULT.MESSAGE);
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
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");
				loadDetail();
		//		doActionCol("Search");
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
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");
				loadDetail();
		//		doActionCol("Search");
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/mapping/tblmap_rqst.do" />';
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
	
// 	var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
 
// 	$("#saveCls").val("U");  //저장구분을 수정 (U) 로 변경 
	
// 	var usrId = grid_sheet.GetCellValue(row, "arrUsrId");
	
// 	$("#usrId").val(usrId);  
	   
//     //$("#frmInput").attr("action", url).submit();
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    
    if(row < 1) return;
    
//     tblClick(row);
	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="TARG.TBL" /> : ' + param.tgtTblLnm + ' [' + param.tgtTblPnm +']'; //타겟테이블
	$('#tbl_sle_title').html(tmphtml);
	
	loadDetail(param);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno;
	
// 	$('div#detailInfocol').empty();
	
	//검증결과 조회
	getRqstVrfLst(param1);
	
	//변경항목 조회
	grid_change.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1);
	}
    
}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
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
	if(code < 0 ) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->

<div id="layer_div" >

<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="TBL.MAPG.DFNT.P.REG"/></div><!-- 테이블매핑정의서 등록 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />
<div style="clear:both; height:5px;"><span></span></div>
  
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="tbl_sle_title"><s:message code="MSG.TBL.CHC" /></div> <!-- 테이블을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="TBL.MAPG.DFNT.P"/></a></li> <!-- 테이블매핑정의서 -->
	    <li id="tabs-rqstvrf"><a href="#tabs-2"><s:message code="VRFC.RSLT" /></a></li> <!-- 검증결과 -->
	    <li id="tabs-rqstchg"><a href="#tabs-3"><s:message code="CHG.ITEM" /></a></li> <!-- 변경항목 -->
	  </ul>
	  <div id="tabs-1">
		<!-- 	상세정보 ajax 로드시 이용 -->
		<div id="detailInfo"></div>
	  </div>
	  
		<!-- 컬럼 목록 탭 -->
	  <div id="tabs-2">
		<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
	  </div>
	  <div id="tabs-3">
		<%@include file="../../commons/rqstmst/rqstChange_lst.jsp" %>
	  </div>
	</div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<!-- 	<div class="sub_tab_area">
			<div class="stab">
	        	<div class="stab_108_over">탭제목1</div>
	            <div class="stab_108"><a href="#">탭제목2</a></div>
	        </div>
	</div> -->
</div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>

</body>
</html>