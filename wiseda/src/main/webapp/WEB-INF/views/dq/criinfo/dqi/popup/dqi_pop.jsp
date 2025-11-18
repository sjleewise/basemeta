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
<title><s:message code="DATA.QLTY.GIPO.INQ" /></title><!-- 데이터품질지표 조회 -->

<script type="text/javascript">

var interval = "";

//page 구분
var pageFlag = "";
var dqiIds = "${dqiIds}";

$(document).ready(function() {
		//page 구분
		pageFlag = "${sflag}";
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		//탭 초기화....
		////$( "#tabs" ).tabs();
        //그리드 초기화 
        // 조회 Event Bind
        $("#popSearch").click(function(){ doAction("Search");  });
        // 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } ).hide();

        //상세 페이지
       // loadDetail();
        
        initButton(pageFlag);
        
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
        setautoComplete($("#frmSearch #dqiLnm"), "DQILNM");
        
        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
 	       	//iframe 형태의 팝업일 경우
 	       	if ("${search.popType}" == "I") {
 	       		parent.closeLayerPop();
 	       	} else {
 	       		window.close();
 	       	}
        });
        

});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	$(window).resize();
	$("#dqiLnm").focus();
	//doAction("Search");
});



$(window).resize(function(){
        //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	setibsheight($("#grid_01"));
    	// grid_sheet.SetExtendLastCol(1);    
});

function initButton(sflag) {
	if (sflag == "CHG") { //업무영역 등록요청의 변경대상 추가용 팝업인 경우 초기화버튼, 적용버튼을 지정한다.
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
	// 		alert("초기화버튼");
			$("form[name=frmSearch]")[0].reset();
		});
      
		//적용 버튼 이벤트 처리
		$("#popApply").click(function(){
// 	 		alert("적용버튼");
			//선택체크박스 확인 : 적용할 대상이 없습니다..
			if(checkDelIBS (grid_pop, "<s:message code="ERR.APPLY" />")) {
				doAction("Apply");
	    	}
		}).show();
		
	} else if ( sflag == "BRDTL" || sflag.indexOf('PRF') != -1 || sflag == "VRFC") { //업무영역 등록요청의 변경대상 추가용 팝업인 경우 초기화버튼, 적용버튼을 지정한다.
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
			$("form[name=frmSearch]")[0].reset();
		}).hide();
      
		//적용 버튼 이벤트 처리
		$("#popApply").click(function(){
// 	 		alert("적용버튼");
			//선택체크박스 확인 : 적용할 대상이 없습니다..
			if(checkDelIBS (grid_pop, "<s:message code="ERR.APPLY" />")) {
				doAction("ApplyDqiLst");
	    	}
		}).show();
		
	} else { //그외의 경우는 초기화 및 적용버튼을 숨긴다.
		 // 초기화버튼 Hide
        $("#popReset").click(function(){}).hide();
        // 적용버튼 Hide
        $("#popApply").click(function(){}).hide();
		
	}
		
}

function initGridCol(sflag) {
	if (sflag == "CHG") { //변경대상 추가용일시 선택컬럼 표시
		
        grid_pop.SetColHidden("ibsCheck",0);
        
	} else if(pageFlag == "BRDTL" || sflag.indexOf('PRF') != -1 || sflag == "VRFC"){
		grid_pop.SetColHidden("ibsCheck",0);
	} else {
		
        grid_pop.SetColHidden("ibsCheck",1);
	}
}



function initGrid()
{
    
    with(grid_pop){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.DQI.POP'/>", Align:"Center"}
                ];
                //No.|상태|선택|데이터품질지표ID|데이터품질지표명|상위데이터품질지표명|데이터품질지표레벨|데이터품질지표설명|버전|등록유형|요청일시|요청자ID|요청자명|전체경로

        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:0, Sort:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"dqiId",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:150,  SaveName:"dqiLnm",   	Align:"Left", Edit:0, TreeCol:1},
                    {Type:"Text",   Width:150,   SaveName:"uppDqiLnm", 	Align:"Left", Edit:0},
                    {Type:"Text",   Width:70,   SaveName:"dqiLvl", 	Align:"Center", Edit:0},
                    {Type:"Float",  Width:60,  	SaveName:"goal",		Align:"Left", 	 Edit:1, Format:"0.00"},
					{Type:"Float",  Width:60,  	SaveName:"weight",		Align:"Left", 	 Edit:1, Format:"0.00"},
                    {Type:"Text",   Width:400,  SaveName:"objDescn",    Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:50,  SaveName:"objVers",     Align:"Left",   Edit:0},
                    {Type:"Combo",  Width:40,  SaveName:"regTypCd",    Align:"Center", Edit:0},                        
                    {Type:"Text",   Width:80,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,  SaveName:"rqstUserNm",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,  SaveName:"fullPath",  Align:"Left", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"vrfcTyp",     Align:"Left", Edit:0}
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
	    SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW' />|<s:message code='CHG' />|<s:message code='DEL' />"}); /*"신규|변경|삭제"*/

	 
	     //변경대상 추가용인지 확인하여 다중선택이 가능/불가능으로 선택해준다...
        initGridCol(pageFlag);
	     
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        SetColHidden("dqiId",1);
        SetColHidden("objVers",1);
        SetColHidden("regTypCd",1);
        SetColHidden("rqstDtm",1);
        SetColHidden("rqstUserId",1);
        SetColHidden("rqstUserNm",1);
        SetColHidden("fullPath",1);
        SetColHidden("vrfcTyp",1);
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_pop);    
    //===========================
   
}


function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_pop.DoSearch("<c:url value="/dq/criinfo/dqi/getDqiList.do" />", param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_pop.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
            
		case "Apply": //적용버튼 액션...
		
        	//요청서에서 팝업 호출했을 경우....
 	 		//TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.      	
//         	var retval = getibscheckjoin(grid_pop, "stwdId");
//         	alert(retval);

        	var saveJson = grid_pop.GetSaveJson(0, "ibsCheck");
			
			//2. 데이
// 			alert(saveJson.Code); 처리대상 행이 없는 경우 리턴한다.
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
			//if(saveJson.data.length == 0) return;
        	
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	var url = "<c:url value="/dq/criinfo/dqi/regWam2WaqDqi.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
//         	parent.setStndWordPop(retval);
        	
        
        	break;
		case "ApplyDqiLst": //적용버튼 액션...
			var retjson = grid_pop.GetSaveJson(0);
			if (retjson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
			   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
			   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
			
			//최하위 DQI만 선택 가능 하게.....
			for(var i=0; i<retjson.data.length; i++){
				if(retjson.data[i].dqiLvl < 2){
//				 		var message = "<s:message code="MSG.INPUT"  arguments="<s:message code='CD.TBL.INFO'/>" />";/**/
		    		showMsgBox("INF", "<s:message code='INF.DQI.POP' />");

					/* 기존소스 181017 */
		    		/* showMsgBox("INF", "최하위 DQI만 선택 할 수 있습니다."); */ 
					return false;
				}
// 				if(retjson.data[i].vrfcTyp == "" || retjson.data[i].vrfcTyp == "CD" ){
// 			    		showMsgBox("INF", "검증룰에 적용할 수 없는 품질 지표 입니다."); 
// 						return false;
// 				}				
			}
			
			//iframe 형태의 팝업일 경우
			if ("${search.popType}" == "I") {
				if(pageFlag == "BRDTL"){
					parent.returnDqiPop(retjson);
				}else if(pageFlag == "PRFPC02"){
					parent.returnDqiPopPC02(retjson);
				}else if(pageFlag == "PRFPC03"){
					parent.returnDqiPopPC03(retjson);
				}else if(pageFlag == "PRFPC04"){
					parent.returnDqiPopPC04(retjson);
				}else if(pageFlag == "PRFPC05"){
					parent.returnDqiPopPC05(retjson);
				}else if(pageFlag == "PRFPT01"){
					parent.returnDqiPopPT01(retjson);
				}else if(pageFlag == "PRFPT02"){
					parent.returnDqiPopPT02(retjson);
				}else if(pageFlag == "PRFOUT"){
					parent.returnDqiPop(retjson);
				}else if(pageFlag == "VRFC"){
	    				if(grid_pop.CheckedRows('ibsCheck') >1) {
	    					showMsgBox("INF", "<s:message code='MSG.VRFC.DQI.ERR' />");
	    					return false;
	    				}
	    				
	    				parent.returnDqiPopVrfc(retjson);
	    			}
			} else {
				if(pageFlag == "BRDTL"){
					opener.returnDqiPop(retjson);
				}else if(pageFlag == "PRFPC02"){
					opener.returnDqiPopPC02(retjson);
				}else if(pageFlag == "PRFPC03"){
					opener.returnDqiPopPC03(retjson);
				}else if(pageFlag == "PRFPC04"){
					opener.returnDqiPopPC04(retjson);
				}else if(pageFlag == "PRFPC05"){
					opener.returnDqiPopPC05(retjson);
				}else if(pageFlag == "PRFPT01"){
					opener.returnDqiPopPT01(retjson);
				}else if(pageFlag == "PRFPT02"){
					opener.returnDqiPopPT02(retjson);
				}else if(pageFlag == "PRFOUT"){
					opener.returnDqiPop(retjson);
				}else if(pageFlag == "VRFC"){
    				if(grid_pop.CheckedRows('ibsCheck') >1) {
    					showMsgBox("INF", "<s:message code='MSG.VRFC.DQI.ERR' />");
    					return false;
    				}
    				
    				opener.returnDqiPopVrfc(retjson);
    			}
//					opener.returnDqiPop(JSON.stringify(retjson));
			}
			$(".pop_tit_close").click();
			
			break;
    }
}


//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/criinfo/dqi/detail/getDqiDetail.do"/>', param, function(){
		//$('#tabs').show();
	});
}

function grid_pop_OnClick(row, col, value, cellx, celly) {
	 
}

function grid_pop_OnDblClick(row, col, value, cellx, celly) {
	//main 화면 그리드 셋팅
	 if(row < 1) return;
	 
	 //변경대상 추가
	 if(pageFlag == "ADD"){
//		    opener.doAction("New");

//			ibs2formmapping(row, $("form#frmInput", opener.document), grid_pop);
			$("#dqiLnm", parent.document).val(grid_pop.GetCellValue(row,"dqiLnm"));
			$("#uppDqiLnm", parent.document).val(grid_pop.GetCellValue(row,"uppDqiLnm"));
			$("#objDescn", parent.document).val(grid_pop.GetCellValue(row,"objDescn"));
			$("#vrfcTyp", parent.document).val(grid_pop.GetCellValue(row,"vrfcTyp"));
			
			$("#divTitle", parent.document).text("<s:message code='DATA.QLTY.INDC'/>" + ':' + grid_pop.GetCellValue(row,"dqiLnm")); /*데이터품질지표*/ 
			
			$("#dqiLnm", parent.document).focus().click();
	 }
	 //데이터품질지표요청 상위데이터품질지표 
	 else if(pageFlag == "UPP"){
		$("#uppDqiLnm", parent.document).val(grid_pop.GetCellValue(row,"dqiLnm"));
	 }
	 //업무규칙 등록
	 else if(pageFlag == "BRA"){
		 
	 }
	//변경대상 추가시
	 else if(pageFlag == "CHG") {
		 if (grid_pop.GetCellValue(row, "ibsCheck") =="0") {
		 	grid_pop.SetCellValue(row, "ibsCheck", "1");
		 } else {
			grid_pop.SetCellValue(row, "ibsCheck", "0");
		 }
		 return;
	 }
	 //업무영역명 검색
	 else if(pageFlag == "BIZ"){
		 $("#dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"dqiLnm"));
		 $("#dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
	 }
	//업무규칙 기본정보 탭 - 업무영역명 검색
	 else if(pageFlag == "BRDTL" || pageFlag.indexOf("PRF") != -1 || pageFlag == "VRFC" ){
// 		 doAction("ApplyDqiLst");
		if(grid_pop.GetCellValue(row,"dqiLvl") < 2){
    		showMsgBox("INF", "<s:message code='INF.DQI.POP' />");
    		
    		/* 기존소스 bak 181017 */
    		/* showMsgBox("INF", "최하위 DQI만 선택 할 수 있습니다."); */
			return false;
		}
		
// 		if(grid_pop.GetCellValue(row,"vrfcTyp") == "" || grid_pop.GetCellValue(row,"vrfcTyp") == "CD"){
//     		showMsgBox("INF", "검증룰에 적용할 수 잆는 품질지표 입니다.");    	
// 			return false;
// 		}
		
		if(pageFlag == "BRDTL"){
			$("#dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"fullPath"));
			$("#dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
		}else if(pageFlag == "PRFPC02"){
			$("#frmInputPC02 #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"fullPath"));
			$("#frmInputPC02 #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
		}else if(pageFlag == "PRFPC03"){
			$("#frmInputPC03 #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"fullPath"));
			$("#frmInputPC03 #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
		}else if(pageFlag == "PRFPC04"){
			$("#frmInputPC04 #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"fullPath"));
			$("#frmInputPC04 #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
		}else if(pageFlag == "PRFPC05"){
			$("#frmInputPC05 #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"fullPath"));
			$("#frmInputPC05 #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
		}else if(pageFlag == "PRFPT01"){
			$("#frmInputPT01 #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"fullPath"));
			$("#frmInputPT01 #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
		}else if(pageFlag == "PRFPT02"){
			$("#frmInputPT02 #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"fullPath"));
			$("#frmInputPT02 #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
		}else if(pageFlag == "PRFOUT"){
			$("#frmInput #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"dqiLnm"));
			$("#frmInput #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
		}else if(pageFlag == "VRFC"){
			$("#frmInput #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"dqiLnm"));
			$("#frmInput #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));

			var vrfcTyp = grid_pop.GetCellValue(row,"vrfcTyp");

			$("#frmInput #vrfcTyp", opener.document).val(vrfcTyp);
		}
	 }
	 else if(pageFlag == "DQILNM"){
		 $("#dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"dqiLnm"));
	 }
	 else if(pageFlag.indexOf('PRF') != -1) {
		 $("form#frmInput"+pageFlag.substring('PRF'.length)+" #dqiLnm", opener.document).val(grid_pop.GetCellValue(row,"dqiLnm"));
		 $("form#frmInput"+pageFlag.substring('PRF'.length)+" #dqiId", opener.document).val(grid_pop.GetCellValue(row,"dqiId"));
	 }
	 $("div.pop_tit_close").click();
}

function grid_pop_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
	
	//선택dqi 그리드 셋팅
	if(dqiIds != null && dqiIds != "undefined" && dqiIds != "" ){
		var arrDqiId = dqiIds.split(",");
		for(var i=0; i<arrDqiId.length; i++){
			var chkRow = grid_pop.FindText("dqiId", arrDqiId[i]);
			grid_pop.SetCellValue(chkRow, "ibsCheck", "1");
		}
	}
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
				if ("${search.popType}" == "I") {
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}
				//팝업닫기
				$("div.pop_tit_close").click();
// 				opener.postProcessIBS(res);
// 				$("div.pop_tit_close").click();
				
// 	    		alert(res.resultVO.rqstNo);
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("SDITM");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
// 	    			$("#btnRegRqst", opener.document).show();
// 	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
// 				opener.doAction("VrfSearch");    		
	    	} 
			
			break;
	
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}


</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="DATA.QLTY.GIPO.INQ" /></div><!-- 데이터품질지표 조회 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
		<div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DATA.QLTY.INDC.INQ'/>"><!--데이터품질지표조회-->
                   <caption><s:message code="DATA.QLTY.INDC"/></caption><!--데이터품질지표-->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="dqiLnm"><s:message code="DATA.QLTY.INDC.NM"/></label></th><!--데이터품질지표명-->
                           <td>
                               <input type="text" name="dqiLnm" id="dqiLnm" />
                           </td>
                           <th scope="row"><label for="uppDqiLnm"><s:message code="UPRN.DATA.QLTY.INDC.NM"/></label></th><!-- 상위데이터품질지표명 -->
                           <td>
                               <input type="text" name="uppDqiLnm" id="uppDqiLnm" />
                           </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<!--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div>  -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_pop", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display:none ;">
		    <div class="selected_title" id="dqi_sel_title"><s:message code="DATA.QLTY.INDC.NM"/> : <span></span></div><!--데이터품질지표명-->
	</div>
<!-- 
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1">데이터품질지표 상세정보</a></li>




	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo"></div>
	  </div>
	 </div>
 -->
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>