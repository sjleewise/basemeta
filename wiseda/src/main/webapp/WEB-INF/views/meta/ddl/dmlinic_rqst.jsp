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
<title><s:message code="DML.JOB.REG" /></title><!-- DML작업 등록 -->

<script type="text/javascript">

                       
$(document).ready(function() {
	
	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val('DML');

	
	// $(document).tooltip();  // 옵션 세부 조정 후 전체 적용....
	
	$("#btnRqstNew").hide();
	$("#btnDelete").hide();
	$("#btnExcelDown").hide();
	

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
		$("li#btnNew").hide();
		$("li#btnChangAdd a").html('<span class="ui-icon ui-icon-folder-open"></span><s:message code="TBL.ADDT" />'); //테이블 추가

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
			doAllApprove(grid_sheet, "1");
//	 		doAllApprove(cdval_sheet, "1");
			
		});
		//전체반려 버튼 이벤트 처리
		$("#btnAllReject").click(function(){
			doAllApprove(grid_sheet, "2");
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
				var tmphtml = '<s:message code="TBL" /> : ' + param.ddlTblPnm + ' [' + param.ddlTblLnm +']'; //테이블
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
                    
		//화면리로드
        $("#btnBlank").click( function(){
    		location.href = '<c:url value="/meta/ddl/dmlinic_rqst.do" />';
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
        	
//         	openSearchPop("<c:url value='/meta/model/pop/pdmtblSearchPop.do' />"); 
        
        }); //doAction("NewChg"); });
        
        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
    		//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL.TBL" />", 'Delete');
//             	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
        	}
        }); //doAction("Delete");  });


        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

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
      
    }
);

$(window).on('load',function() {
	//그리드 초기화 
// 	initGrid();
	initdbschemaGrid();

	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, null);
	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));
	
// 	loadDetail();

// 	doAction("Search");
	doAction("SearchDbms");
	
	
});


$(window).resize(
    
    function(){
                
    	// grid_sheet.SetExtendLastCol(1);    
    }
);






//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "New":        //신규 추가...
        	//loadDetail();
			
            break;
        
        case "AddWam": //기존 테이블 추가
			var url   = "<c:url value="/meta/ddl/popup/ddltbl_pop.do"/>";
			var param = "popRqst=Y";
// 			var popup = OpenWindow(url+param,"pdmtbl_ddlrqst","800","600","yes");
			openLayerPop(url, 800, 600, param);
		
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
			
			var url = "<c:url value="/meta/model/delddltblrqstlist.do"/>";
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
			
			var url = "<c:url value="/meta/ddl/regdmlworkrqstlist.do"/>";
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
        	
            var url = "<c:url value="/meta/ddl/regddltblrqstlist.do"/>";
            var param = $("form#frmSearch").serialize() + "&sAction=" + sAction;
        	IBSpostJson(url, param, ibscallback);
        	break;
        
		case "Search":
			var param = $("#mstFrm").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/ddl/getddltblrqstlist.do" />", param);
			//컬럼 리스트 조회...
// 			doActionCol("Search");
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
						
			break;
			
        case "SearchDbms":
        	//var param = $('#frmSearch').serialize();
        	dbschema_grid.DoSearch("<c:url value="/commons/damgmt/db/getdbschemalist.do" />", null);
//         	grid_sheet.DoSearchScript("testJsonlist");
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
			
			var url = "<c:url value="/meta/ddl/approveddltbl.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "LoadExcel":  //엑셀업로드
			var url = "<c:url value="/meta/model/popup/pdmtbl_xls.do" />";
			
// 			var xlspopup = OpenWindow(url ,"pdmtblxls","800","600","yes");
			openLayerPop(url, 800, 600);
			break;
			
    }       
}
 



/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
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
	    		}
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");
				
				
// 				loadDetail();
// 				doActionCol("Search");
	    	} 
			
			break;
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/meta/ddl/ddltbl_rqst.do" />';
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



</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DDL.TBL.REG" /></div> <!-- DDL테이블 등록 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />

<div style="clear:both; height:5px;"><span></span></div>
        
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title" id="tbl_sle_title"><s:message code="MSG.TBL.CHC" /></div><!-- 테이블을 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"></a></li> <!-- DML 요청서 -->
	  </ul>
	  <div id="tabs-1">
		<!-- 	상세정보 ajax 로드시 이용 -->
		<%@include file="dmlinic_rqst_dtl.jsp" %>
	  </div>
	  
	</div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
<!-- 	<div class="sub_tab_area">
			<div class="stab">
	        	<div class="stab_108_over">탭제목1</div>
	            <div class="stab_108"><a href="#">탭제목2</a></div>
	        </div>
	</div> -->

<div style="clear:both; height:5px;"><span></span></div>

<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>

</body>
</html>