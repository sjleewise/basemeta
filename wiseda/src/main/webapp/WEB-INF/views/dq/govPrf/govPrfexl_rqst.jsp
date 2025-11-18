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
<title><s:message code="PROF.EXCL.REG" /></title><!--프로파일 엑셀등록-->
<style type="text/css">
	#divTabs-rqstvrf {display: block; margin-top:440px;}
</style>

<script type="text/javascript">

var grid_name;

$(document).ready(function() {
	
	//탭 초기화....
	//$( "#tabs" ).tabs();
	
	$( "#divTabs-rqstvrf" ).tabs();
	
	//업무구분상세 초기화...
	$("#mstFrm #bizDtlCd").val("${waqMstr.bizDtlCd}");
	
	$("[id$='-${waqMstr.bizDtlCd}'] a").click();
	
	$( "#tab-PT01 a" ).click(function(){
		mstFrmReset("PT01");
	});
	$( "#tab-PT02 a" ).click(function(){
		mstFrmReset("PT02");
	});
	$( "#tab-PC01 a" ).click(function(){
		mstFrmReset("PC01");
	});
	
	// 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	//엑셀 LOAD
    $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
	
	//추가
    $("#btnNew").click( function(){
    	doAction("New");
    } );
	
	//변경대상 추가
	 $("#btnChangAdd").hide();
	
	//조회
    $("#btnSearch").click( function(){
    	doAction("Search"); 
    } ).show();
	
	//저장
    $("#btnSave").click( function(){
    	doAction("Save"); 
    } ).show();
	
    //삭제
    $("#btnDelete").click( function(){
    	doAction("Delete"); 
    } ).show();
    
  	//메타연동조회
    $("#btnMetaSrch").click( function(){
    	doAction("MetaSrch");  
    } ).hide();
    
    
    //화면리로드
    $("#btnBlank").click( function(){
    	//화면 리로드 하는 경우 prfexl_rqst로 가기 떄문에 #tab-PT01, #tab-PT02, #tab-PC01 제외하고 주석 처리해야함
		location.href = '<c:url value="/dq/govPrf/govPrfexl_rqst.do" />';
    } );
    
		
 // 등록요청 Event Bind
	$("#btnRegRqst").click(function(){
		//등록가능한지 확인한다.vrfCd = 1
		var regchk = grid_name.FindText("vrfCd", "<s:message code='REG.POSB' />");/*등록가능*/
		
		if(regchk > 0) {
			if($("#mstFrm #aprLvl").val() > 0 ){
				showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'Submit');
			}else{
				showMsgBox("CNF", "<s:message code='CNF.SUBMIT' />", 'Approve');
			}
		} else {
			showMsgBox("INF", "<s:message code='ERR.SUBMIT' />");
			return false;
		}
		
	});
 
	//전체승인 버튼 이벤트 처리
	$("#btnAllApprove").click(function(){
		doAllApprove(grid_name, "1");
	});
	//전체반려 버튼 이벤트 처리
	$("#btnAllReject").click(function(){
		doAllApprove(grid_name, "2");
	});
	
	//검토처리 Event Bind
	$("#btnReqApprove").click(function(){
		//alert("결재처리")
		//그리드 변경대상 체크한다.
		if (!chkSheetDataModified(grid_name)) {
			showMsgBox("INF", "<s:message code="ERR.CHKAPPR" />");
			return false;
		}
		// 승인시 승인 또는 반려가 선택되지 않은게 있는지 확인한다. (grid_sheet, 검토상태 savename)
		if (chkRvwStsCd(grid_name, "rvwStsCd") > -1) {
			//alert("검토내역 중 승인이나 반려가 선택되지 않았습니다.");
			showMsgBox("INF", "<s:message code="ERR.APPROVE" />");
			return false;
		};
		
		//반려 선택시 반려사유를 입력하도록 한다.
// 		var tmprow = chkRvwCont(grid_name, "rvwStsCd", "rvwConts");
// 		if (tmprow > 0 ) {
// 			showMsgBox("INF", "<s:message code="ERR.REJECT" />");
// // 			grid_name.SetSelectRow(tmprow);
// // 			//선택한 상세정보를 가져온다...
// // 			var param =  grid_name.GetRowJson(tmprow);
			
// // 			//var param = grid_sheet.GetRowJson(row);
// // 			var param1 = $("#mstFrm").serialize();
// // 			param1 += "&rqstSno=" + param.rqstSno;
			
// // 			//param = 
// // 			//loadDetail(param1);
			
// // 			//검증결과 조회
// // 			getRqstVrfLst(param1);
// // 			$("#frmInput #rvwConts").focus();
// 			return false;
// 		}
		
		doAction("Approve");
		
	});
	
});

$(window).on('load',function() {
	//프로파일별 그리드명 셋팅
	var prfKndCd = "${waqMstr.bizDtlCd}";
	if(prfKndCd == "PT01"){
		grid_name = grid_pt01;
	}else if(prfKndCd == "PT02"){
		grid_name = grid_pt02;
	}else if(prfKndCd == "PC01"){
		grid_name = grid_pc01;
	}
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
// 	if(rqststep !=  "A" ){
// 	if($("#mstFrm #aprLvl").val() > 0 ){
		setDispRqstMainButton(rqststep, grid_name);
// 	}

	//검토처리 버튼 보여주기....
	checkApproveYn($("#mstFrm"));

	doAction("Search"); 
	
});


$(window).resize(function() {
});

//요청정보재조회
function getMstFrm(){
	var urls = '<c:url value="/dq/govPrf/ajaxgrid/getRqstForm.do"/>';
	var param = $('form[name=mstFrm]').serialize();
	ajax2Json(urls, param, setMstFrm);
}

//재조회 된 요청정보 셋팅
function setMstFrm(data){
	json2formmapping($("form[name=mstFrm]"), data);
}

//텝 클릭시 요청정보 재조회
//단 등록완료 후 재조회 시 재조회 요청정보 셋팅
function mstFrmReset(bizDtlCd){
	$("form[name=mstFrm] #bizDtlCd").val(bizDtlCd);
	if("${waqMstr.bizDtlCd}" == $("form[name=mstFrm] #bizDtlCd").val() ){
		$("form[name=mstFrm]")[0].reset();
		$("form[name=mstFrm] #bizDcd").val("${waqMstr.bizDcd}");
		$("form[name=mstFrm] #rqstStepCd").val("${waqMstr.rqstStepCd}");
		$("form[name=mstFrm] #bizDtlCd").val("${waqMstr.bizDtlCd}");
	}else{
		//요청번호 신규 생성
		getMstFrm();
	}
}

function doAction(sAction)
{
	//프로파일별 그리드명 셋팅
	var prfKndCd = $("form[name=mstFrm] #bizDtlCd").val();
	
	if(prfKndCd == "PT01"){
		grid_name = grid_pt01;
	}else if(prfKndCd == "PT02"){
		grid_name = grid_pt02;
	}else if(prfKndCd == "PC01"){
		grid_name = grid_pc01;
	}
	
    switch(sAction)
    {
    	case "New":  //그리드추가
    		grid_name.DataInsert(0);
        	break;

    	case "LoadExcel":  //엑셀업로드
    		grid_name.LoadExcel({Mode:'HeaderMatch', Append:1});
        	break;
        
    	case "Down2Excel":  //엑셀다운로드
    		if(grid_name.GetTotalRows() == 0 ){
    			grid_name.DataInsert(0);
    		}
    		grid_name.Down2Excel({HiddenColumn:1, Merge:1});
        	break;
        
		case "Search":
			
			//프로파일별 url 셋팅
			var url = "";
			if(prfKndCd == "PT01"){
				url = '<c:url value="/dq/profile/getPrfPT01ExlLst.do"/>';
			}else if(prfKndCd == "PT02"){
				url = '<c:url value="/dq/profile/getPrfPT02ExlLst.do"/>';
			}else if(prfKndCd == "PC01"){
				url = '<c:url value="/dq/profile/getPrfPC01ExlLst.do"/>';
			}
			
			var param = $("#mstFrm").serialize();
			param = encodeURI(param);
			grid_name.DoSearch(url, param);
			
			//전체 검증결과 조회 (rqstNo, bizDtlCd)
			getRqstVrfLst(param);
			
			break;
			
    	case "Save":  //저장
    		//저장 대상의 데이터를 Json 객체로 반환한다.
			ibsSaveJson = grid_name.GetSaveJson(0);
    	
    		//2. 필수입력 누락인 경우
			if (ibsSaveJson.Code == "IBS010") return;
			
			if(ibsSaveJson.data.length == 0){
				showMsgBox("INF", "<s:message code="ERR.CHKSAVE" />");
				return;
			}
			
			//프로파일별 url 셋팅
			var url = "";
			if(prfKndCd == "PT01"){
				url = '<c:url value="/dq/profile/regExlPT01Lst.do"/>';
			}else if(prfKndCd == "PT02"){
				url = '<c:url value="/dq/profile/regExlPT02Lst.do"/>';
			}else if(prfKndCd == "PC01"){
				url = '<c:url value="/dq/profile/regExlPC01Lst.do"/>';
			}
			
			var param = $('form[name=mstFrm]').serialize();
			param = encodeURI(param);
	        IBSpostJson2(url, ibsSaveJson, param, ibscallback);
        	break;
        	
    	case "Delete" :
			//테크박스가 입력상태인 경우 삭제...
			if(!grid_name.CheckedRows("ibsCheck")) {
				//삭제할 대상이 없습니다...
				showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
				return;
			}
			
			//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_name);
        	
        	var tmpkey = getibscheckjoin(grid_name, "rqstSno");
			
        	if(tmpkey < 0) return;
        	
			var url = '<c:url value="/dq/profile/delPrfExlLst.do"/>';
			var param = $("#mstFrm").serialize()+"&joinkey="+tmpkey;
			param = encodeURI(param);
			IBSpostJson2(url, null, param, ibscallback);

			break;
			
	    case "Submit" : //등록요청...
			//결재자 팝업 호출.....
			var param = $("#mstFrm").serialize();
			param = encodeURI(param);
			doRequest(param);
			
			break;
			
		case "Approve" : //결재처리
			
			//프로파일 검토상태코드 승인으로 변경
			//결재프로세스 태울경우 제거
			/* if($("form[name=mstFrm] #aprLvl").val() == 0){
				doAllApprove(grid_name, "1");
			} */
		
			var saveJson = grid_name.GetSaveJson(1);
			
			//2. 필수입력 누락인 경우
			if (saveJson.Code == "IBS010") return;
			if(saveJson.data.length == 0) return;
			
			var url = "";
			if(prfKndCd == "PT01"){
				url = '<c:url value="/dq/profile/approvePrfPT01.do"/>';
			}else if(prfKndCd == "PT02"){
				url = '<c:url value="/dq/profile/approvePrfPT02.do"/>';
			}else if(prfKndCd == "PC01"){
				url = '<c:url value="/dq/profile/approvePrfPC01.do"/>';
			}
			
			var param = $('form[name=mstFrm]').serialize();
			param = encodeURI(param);
			
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
	   	
    }       
}
 

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
	    			$("#btnRegRqst").show();
	    		}
	    		doAction("Search"); 		
	    	} 
			break;
			
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			//저장완료시 마스터 정보 셋팅...
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
	    		 
	    		json2formmapping ($("#mstFrm"), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
	    		$("#mstFrm #bizDtlCd").val(res.resultVO.bizDtlCd);
	    		
	    		//등록요청 버튼 활성화
	    		if ($("#mstFrm #rqstStepCd").val() == "S")  {
  					$("#btnRegRqst").show();
	    		} 
  				doAction("Search"); 
	    	} 
			
			break;
				
		//요청서 결재단계별 승인 완료 후처리
		case "<%=WiseMetaConfig.RqstAction.APPROVE%>":
			var url = '<c:url value="/dq/govPrf/govPrfexl_rqst.do" />';
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
	    <div class="menu_title"><s:message code="PROF.EXCL.REG" /></div><!--프로파일 엑셀등록-->

	</div>
</div>


<div style="clear:both; height:10px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />

<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>
	<div id="tabs">
	  <ul>
	    <li id="tab-PT01"><a href="#tabs-pt01"><s:message code="RLT"/></a></li><!--관계분석-->
	    <li id="tab-PT02"><a href="#tabs-pt02"><s:message code="DUP"/></a></li><!--중복분석-->
	    <li id="tab-PC01"><a href="#tabs-pc01"><s:message code="ANA.CLMN"/></a></li><!--컬럼분석-->
	  </ul>
	  <div id="tabs-pt01">
			<div id="detailInfoPT01"><%@include file="exl/relana_exl.jsp" %></div>
	  </div>
	  <div id="tabs-pt02">
			<div id="detailInfoPT02"><%@include file="exl/unqana_exl.jsp" %></div>
	  </div>
	  <div id="tabs-pc01">
			<div id="detailInfoPC01"><%@include file="exl/colana_exl.jsp" %></div>
	  </div>
	 </div>

	<div style="clear:both; height:5px;"><span></span></div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div style="clear:both; height:10px;"><span></span></div>
	<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	
	<div id="divTabs-rqstvrf">
		<ul>
			<li id="tabs-rqstvrf"><a href="#tab-rqstvrf"><s:message code="VRFC.RSLT" /></a></li><!--검증결과-->
		</ul>
		<div id="tab-rqstvrf">
			<%@include file="../../commons/rqstmst/rqstvrf_lst.jsp" %>
		</div>
	</div>

<tiles:insertTemplate template="/WEB-INF/decorators/requestMstForm.jsp" />
<c:if test="${waqMstr.rqstStepCd == 'Q' or waqMstr.rqstStepCd == 'A' }">
<tiles:insertTemplate template="/WEB-INF/decorators/approveStatus.jsp" />
</c:if>

</body>
</html>