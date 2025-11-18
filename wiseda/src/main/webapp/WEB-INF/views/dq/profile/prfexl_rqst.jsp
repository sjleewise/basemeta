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
	$( "#tab-PC02 a" ).click(function(){
		mstFrmReset("PC02");
	});
	$( "#tab-PC03 a" ).click(function(){
		mstFrmReset("PC03");
	});
	$( "#tab-PC04 a" ).click(function(){
		mstFrmReset("PC04");
	});
	$( "#tab-PC05 a" ).click(function(){
		mstFrmReset("PC05");
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
  	
  //스키마자동추출조회
    $("#btnWatSrch").click( function(){
    	doAction("WatSrch");  
    } ).hide();
    
    
    //화면리로드
    $("#btnBlank").click( function(){
		location.href = '<c:url value="/dq/profile/prfexl_rqst.do" />';
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
	}else if(prfKndCd == "PC02"){
		grid_name = grid_pc02;
	}else if(prfKndCd == "PC03"){
		grid_name = grid_pc03;
	}else if(prfKndCd == "PC04"){
		grid_name = grid_pc04;
	}else if(prfKndCd == "PC05"){
		grid_name = grid_pc05;
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
	var urls = '<c:url value="/dq/profile/ajaxgrid/getRqstForm.do"/>';
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
		$("form[name=mstFrm] #rqstNo").val("${waqMstr.rqstNo}");
		$("form[name=mstFrm] #rqstNm").val("${waqMstr.rqstNm}");
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
	}else if(prfKndCd == "PC02"){
		grid_name = grid_pc02;
	}else if(prfKndCd == "PC03"){
		grid_name = grid_pc03;
	}else if(prfKndCd == "PC04"){
		grid_name = grid_pc04;
	}else if(prfKndCd == "PC05"){
		grid_name = grid_pc05;
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
			}else if(prfKndCd == "PC02"){
				url = '<c:url value="/dq/profile/getPrfPC02ExlLst.do"/>';
			}else if(prfKndCd == "PC03"){
				url = '<c:url value="/dq/profile/getPrfPC03ExlLst.do"/>';
			}else if(prfKndCd == "PC04"){
				url = '<c:url value="/dq/profile/getPrfPC04ExlLst.do"/>';
			}else if(prfKndCd == "PC05"){
				url = '<c:url value="/dq/profile/getPrfPC05ExlLst.do"/>';
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
			}else if(prfKndCd == "PC02"){
				url = '<c:url value="/dq/profile/regExlPC02Lst.do"/>';
			}else if(prfKndCd == "PC03"){
				url = '<c:url value="/dq/profile/regExlPC03Lst.do"/>';
			}else if(prfKndCd == "PC04"){
				url = '<c:url value="/dq/profile/regExlPC04Lst.do"/>';
			}else if(prfKndCd == "PC05"){
				url = '<c:url value="/dq/profile/regExlPC05Lst.do"/>';
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
			}else if(prfKndCd == "PC02"){
				url = '<c:url value="/dq/profile/approvePrfPC02.do"/>';
			}else if(prfKndCd == "PC03"){
				url = '<c:url value="/dq/profile/approvePrfPC03.do"/>';
			}else if(prfKndCd == "PC04"){
				url = '<c:url value="/dq/profile/approvePrfPC04.do"/>';
			}else if(prfKndCd == "PC05"){
				url = '<c:url value="/dq/profile/approvePrfPC05.do"/>';
			}
			
			var param = $('form[name=mstFrm]').serialize();
			param = encodeURI(param);
			
			IBSpostJson2(url, saveJson, param, ibscallback);
		
	   		break;
	   	
		case "MetaSrch" : //bmt를 위해 메타연동조회 기능 추가
			
			if(prfKndCd == "PT01"  //관계
					|| prfKndCd == "PC02" //코드 
						|| prfKndCd == "PC03" //날짜 
							|| prfKndCd == "PC04" //범위 
					){

				//프로파일별 url 셋팅
				var url = "";

				if(prfKndCd == "PC04"){
					url = '<c:url value="/dq/profile/getPrfMetaSrchPc04Lst.do"/>';
				}else if(prfKndCd == "PC03"){
					url = '<c:url value="/dq/profile/getPrfMetaSrchPc03Lst.do"/>';
				}else if(prfKndCd == "PC02"){
					url = '<c:url value="/dq/profile/getPrfMetaSrchPc02Lst.do"/>';
				}else if(prfKndCd == "PT01"){
					url = '<c:url value="/dq/profile/getPrfMetaSrchPt01Lst.do"/>';
				}
				
				var param = $("#mstFrm").serialize() ;
				param = encodeURI(param);
				grid_name.DoSearch(url, param); //bizDtlCd 				

			
			}
			else{
				showMsgBox("ERR", "해당 프로파일은 메타연동을 지원하지 않습니다");
			}
				
			break;
			
		case "WatSrch" : //bmt를 위해 메타연동조회 기능 추가
			
			if(prfKndCd == "PC05"  //패턴
					|| prfKndCd == "PC02" //코드 
						|| prfKndCd == "PC03" //날짜 
							|| prfKndCd == "PC04" //범위 
					){

				//프로파일별 url 셋팅
				var url = "";

				if(prfKndCd == "PC04"){
					url = '<c:url value="/dq/profile/getPrfWatPc04Lst.do"/>';
				}else if(prfKndCd == "PC03"){
					url = '<c:url value="/dq/profile/getPrfWatPc03Lst.do"/>';
				}else if(prfKndCd == "PC02"){
					url = '<c:url value="/dq/profile/getPrfWatPc02Lst.do"/>';
				}else if(prfKndCd == "PC05"){
					url = '<c:url value="/dq/profile/getPrfWatPt01Lst.do"/>';
				}
				
				var param = $("#mstFrm").serialize() ;
				param += "&dbConnTrgId="+$("#dbConnTrgId").val();
				param += "&dbSchId="+$("#dbSchId").val();
				param += "&dbcColNm="+$("#dbcColNm").val();
				param += "&paramVal="+$("#paramVal").val();
				param = encodeURI(param);
				grid_name.DoSearch(url, param); //bizDtlCd 				

			
			}
			else{
				showMsgBox("ERR", "해당 프로파일은 자동추출을 지원하지 않습니다");
			}
				
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
			var url = '<c:url value="/dq/profile/prfexl_rqst.do" />';
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

<!-- 검색조건 입력폼 -->
<div id="search_div" style="display:none;">
        <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        <input type="hidden" name="sheetRow" id="sheetRow"  />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.INQ'/>"><!--프로파일조회-->

                   <caption><s:message code="PROF.INQ"/></caption><!--프로파일조회-->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:20%;" />
                   <col style="width:15%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class=""><label for="dbSchId"><s:message code="DB.MS.SCHEMA.NM"/></label></th><!--진단대상명/스키마명-->

                      <td colspan="3">
			            <select id="dbConnTrgId" class="" name="dbConnTrgId">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			            </select>
			            <select id="dbSchId" class="" name="dbSchId">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			             </select>
			           </td>
                       </tr>
                       <tr>   
                           <th scope="row"><label for="dbcColNm">컬럼명 규칙</label></th>

                           <td>
                               <input type="text" name="dbcColNm" id="dbcColNm" />
                           </td>
                           
                           <th scope="row"><label for="paramVal">유효값</label></th>
                           <td>
                               <input type="text" name="paramVal" id="paramVal" />
                           </td>
                        </tr>    
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:5px;"><span></span></div>
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
	    <li id="tab-PC02"><a href="#tabs-pc02"><s:message code="ANA.CD"/></a></li><!--코드분석-->
	    <li id="tab-PC03"><a href="#tabs-pc03"><s:message code="DATE.FRMT"/></a></li><!--날짜형식분석-->
	    <li id="tab-PC04"><a href="#tabs-pc04"><s:message code="ANA.RNG"/></a></li><!--범위분석-->
	    <li id="tab-PC05"><a href="#tabs-pc05"><s:message code="STRING.PTRN"/></a></li><!--문자열패턴분석-->
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
	  <div id="tabs-pc02">
			<div id="detailInfoPC02"><%@include file="exl/codeana_exl.jsp" %></div>
	  </div>
	  <div id="tabs-pc03">
			<div id="detailInfoPC03"><%@include file="exl/dateana_exl.jsp" %></div>
	  </div>
	  <div id="tabs-pc04">
			<div id="detailInfoPC04"><%@include file="exl/rangeana_exl.jsp" %></div>
	  </div>
	  <div id="tabs-pc05">
			<div id="detailInfoPC05"><%@include file="exl/patternana_exl.jsp" %></div>
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