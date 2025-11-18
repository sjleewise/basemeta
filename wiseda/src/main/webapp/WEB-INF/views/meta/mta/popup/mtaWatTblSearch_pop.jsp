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
<title>메타데이터 테이블 검색</title>

<script type="text/javascript">

var connTrgSchJson = ${codeMap.connTrgSch}; 
var popRqst = "${search.popRqst}";

$(document).ready(function() {
	$(window).focus();
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
                
    //그리드 초기화 
//     initGrid();
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });

    
    $("#popDelete").hide();

    if (popRqst == 'Y') {
        // 적용 Event Bind
        $("#popApply").click(function(){ 
	         if(checkDelIBS (search_grid_sheet, "<s:message code="REQ.NO.CHANG" />")) {
			 	doAction("Apply");
		     }
		 }).show();
    }
    
  //폼 초기화 버튼 초기화...
	$('#popReset').click(function(event){
		event.preventDefault();
// 		alert("초기화버튼");
		$("form[name=frmSearch]")[0].reset();
	});
                  
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

    //======================================================
    // 셀렉트 박스 초기화
    //======================================================
    
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close, #btnCloseBottom").click(function(){

    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });

 	/*
    double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
   	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});
   	*/

   	$("#frmSearch #gapStsCd").change(function(){

		//search_grid_sheet.RemoveAll(false);  
    });

   	$("#frmSearch #orgCd").change(function(){

		var orgCd = $(this).val();
		
   		getOrgInfoSys(orgCd);   
    });

	$("#frmSearch #infoSysCd").change(function(){

		var orgCd     = $("#frmSearch #orgCd").val();
		var infoSysCd = $(this).val();

   		getInfoSysDbConnTrg(orgCd, infoSysCd);   
    });

	$("#frmSearch #dbConnTrgId").change(function(){
		
		var dbConnTrgId = $(this).val();

   		getInfoSysDbSch(dbConnTrgId);   
    });
   	

   	$("#regStatus").val("C");


   	$("#btnSearchOrgCd, #frmSearch #orgNm").click(function(){
    	$("#frmSearch #orgCd").val("");
    	$("#frmSearch #orgNm").val("");
    	var url = "<c:url value='/meta/admin/popup/popMetaGiCode.do' />";
		//var popwin = OpenModal(url, "searchpop",  1000, 600, "no");
    		openLayerPop(url, 1000, 600);
   	});

   	SboxSetLabelEvent();
   	
});
//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	//그리드 초기화
	initGrid();
	$(window).resize();
	/* if($("#fullPath").val() != '') {
		$("#fullPath").attr("readOnly", true);
	} */
	
	//doAction("Search");
});


$(window).resize(function(){
		 //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
		setibsheight($("#grid_01"));        
    	// search_grid_sheet.SetExtendLastCol(1);    
    
});


function initGrid()
{
    with(search_grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.MTAWAT.TBL.SEARCH'/>";
        //No.|ibs상태|선택|상태|데이터구분|기관코드|기관명|정보시스템ID|정보시스템명|DBID|논리DB명|물리DB명|테이블소유자|테이블소유자|테이블ID|테이블명|테이블한글명|테이블설명|등록유형코드
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",      Width:50,  SaveName:"ibsSeq",         Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,  SaveName:"ibsStatus",      Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,  SaveName:"ibsCheck",       Align:"Center", Edit:1, Sort:0},
                    {Type:"Combo",    Width:60,  SaveName:"gapStsCd",       Align:"Center", Edit: 0},
                    {Type:"Combo",    Width:60,  SaveName:"tblClltDcd",     Align:"Center", Edit: 0},
                    {Type:"Text",     Width:80,  SaveName:"orgCd",   	    Align:"Left",   Edit: 0, Hidden:1},
                    {Type:"Text",     Width:80,  SaveName:"orgNm",   	    Align:"Left",   Edit: 0},
                    {Type:"Text",     Width:120, SaveName:"infoSysCd", 	    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:120, SaveName:"infoSysNm", 	    Align:"Left",   Edit:0},
                                                                            
                    {Type:"Text",     Width:180, SaveName:"dbConnTrgId",    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:80,  SaveName:"dbConnTrgLnm",	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:80,  SaveName:"dbConnTrgPnm",	Align:"Center", Edit:0},
                    {Type:"Text",     Width:80,  SaveName:"dbSchId",   	    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:80,  SaveName:"dbSchPnm",       Align:"Center", Edit:0}, 
                                      
                    {Type:"Text",     Width:80,  SaveName:"mtaTblId",   	Align:"Left",   Edit: 0, Hidden:1},
                    {Type:"Text",     Width:190, SaveName:"mtaTblPnm",      Align:"Left",   Edit:0}, 
                    {Type:"Text",     Width:100, SaveName:"mtaTblLnm", 	    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100, SaveName:"objDescn",		Align:"Left",   Edit:0}
                ];
                    
        InitColumns(cols);
        SetLeftCol(2);

	    //콤보 목록 설정...  	    
	    SetColProperty("gapStsCd", ${codeMap.gapStsCdibs});
	     
 	    SetColProperty("tblClltDcd", ${codeMap.tblClltDcdibs});
 	   
        InitComboNoMatchText(1, "");

               
        SetColHidden("ibsStatus",1);
        //SetColHidden("infoSysCd",1);
       // SetColHidden("orgCd",1);
        //SetColHidden("dbConnTrgId",1);
        //SetColHidden("mtaTblId",0);

      	if(popRqst == 'N') {
     		SetColHidden("ibsCheck"	,1);      
      	}
//         FitColWidth();
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(search_grid_sheet);    
    //===========================
   
}

function doAction(sAction)
{
        
    switch(sAction)
    {
             
        case "Search":
        	
        	if($("#orgCd").val() == "") {
        		showMsgBox("ERR","기관명을 선택하세요");
        		return;
        	}   
        	
        	var param = $('#frmSearch').serialize();
        	
        	//search_grid_sheet.DoSearch("<c:url value="/meta/mta/popup/getMtaTblList.do" />", param);
        	        	
        	search_grid_sheet.DoSearch("<c:url value="/meta/mta/popup/getWatTblList.do" />", param);

        	break;
       
        case "Apply":
        	//요청서에서 팝업 호출했을 경우....
 	 		//TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.      	
//         	var retval = getibscheckjoin(grid_pop, "stwdId");

        	var saveJson = search_grid_sheet.GetSaveJson(0, "ibsCheck");
			
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
        	
        	param += "&gapStsCd=" + $("#gapStsCd").val();  

        	var url = "<c:url value="/meta/mta/regWat2WaqMtaTbl.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
        	//조회화면에서 팝업 호출했을 경우....
        	break;
        	
        case "DelRqst": //삭제요청...
        	var saveJson = search_grid_sheet.GetSaveJson(0, "ibsCheck");
			
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
        	var param = "";
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	param += "&rqstDcd=DD";
        	var url = "<c:url value="/meta/mta/regWam2WaqMtaTbl.do" />";
			IBSpostJson2(url, saveJson, param, ibscallback);
        
        	break; 
        	      
        case "Down2Excel":  //엑셀내려받기
            search_grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
            search_grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }       
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
			$("#frmSearch #dbConnTrgId").find("option").remove().end();
			$("#frmSearch #dbSchId").find("option").remove().end();

			$("#frmSearch #infoSysCd").append("<option value=\"\"><s:message code='WHL' /></option>");
			$("#frmSearch #dbConnTrgId").append("<option value=\"\"><s:message code='WHL' /></option>");
			$("#frmSearch #dbSchId").append("<option value=\"\"><s:message code='WHL' /></option>");

			$("#frmSearch #infoSysCd").prev().html($("#frmSearch #infoSysCd").find('option:first').text());
			$("#frmSearch #dbConnTrgId").prev().html($("#frmSearch #dbConnTrgId").find('option:first').text());
			$("#frmSearch #dbSchId").prev().html($("#frmSearch #dbSchId").find('option:first').text());
			
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



function getInfoSysDbConnTrg(orgCd, infoSysCd){

	var ajaxurl =  "<c:url value="/meta/mta/ajax/getInfoSysDbConnTrg.do" />";

	var objParam = new Object();   

	objParam.orgCd     = orgCd;
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

			$("#frmSearch #dbConnTrgId").prev().html($("#frmSearch #dbConnTrgId").find('option:first').text());
			$("#frmSearch #dbSchId").prev().html($("#frmSearch #dbSchId").find('option:first').text());
			
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
			$("#frmSearch #dbSchId").prev().html($("#frmSearch #dbSchId").find('option:first').text());
			
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

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
// 	    		alert(res.resultVO.rqstNo);
				if ("${search.popType}" == "I") {
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}

				if(res.RESULT.CODE == 0) {
					var checkedRowsIdx = search_grid_sheet.FindCheckedRow("ibsCheck");

					search_grid_sheet.RowDelete(checkedRowsIdx);
					//doAction("Search");
				}

				//팝업닫기
// 				$("div.pop_tit_close").click();
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("DMN");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
// 	    			$("#btnRegRqst", opener.document).show();
// 	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
// 				opener.doAction("Search");    		
	    	} 
			
// 			opener.doAction("Search");
			
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

/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function search_grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	var retjson = search_grid_sheet.GetRowJson(row);

/* 	if(search_grid_sheet.SaveNameCol("dbcTblPnm") == col) {

		var param =  "tblNm=" + retjson.dbcTblPnm + "&dbConnTrgId=" + retjson.dbConnTrgId 
				  + "&orgCd=" + retjson.orgCd
				  + "&infoSysCd=" + retjson.infoSysCd 
				  + "&dbSchId=" + retjson.dbSchId;
		var url = "<c:url value="/meta/mta/popup/mtagaptbl_pop.do"/>";
 
		//OpenModal(url + "?" + param, "mtaGappop", 800, 700, "no");
		openLayerPop(url, 800, 700, param);
	}*/
	
	//요청서용 팝업일 경우.....
	if (popRqst == 'Y') {
		//체크박스 선택/해제 토글 기능.....
		var cellchk = search_grid_sheet.GetCellValue(row, "ibsCheck");
		if(cellchk == '0') {
			search_grid_sheet.SetCellValue(row, "ibsCheck", 1);
		} else {
			search_grid_sheet.SetCellValue(row, "ibsCheck", 0);
		}
		
		return;
	}
	
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		parent.returnMtatblPop(JSON.stringify(retjson));
	} else {
		opener.returnMtatblPop(JSON.stringify(retjson));
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".pop_tit_close").click();
	
    return;
	
}

function search_grid_sheet_OnClick(row, col, value, cellx, celly) {
    //$("#hdnRow").val(row);
    if(row < 1) return;
    

}


function search_grid_sheet_OnSaveEnd(code, message) {
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function search_grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {

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

function returnOrgCd(orgCd, orgNm){
	$("#frmSearch #orgCd").val(orgCd);
	$("#frmSearch #orgNm").val(orgNm);
}

</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="REG.INQ" /></div>
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" id="rqstNo" name="rqstNo" value="${search.rqstNo}"/>
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="기관명조회">
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   <col style="width:20%;" />
                   <col style="width:*;" />
                   </colgroup>
                   <tbody>   
                       <tr>                               
                           <th scope="row" class="th_require"><label for="orgNm"><s:message code="ORG.NM"/></label></th><!-- 기관명 -->
                           <td>
                           		<%-- <input type="hidden" id="orgCd" name="orgCd"/>
                           		<input type="text" id="orgNm" name="orgNm" class="wd60p d_readonly" readonly/>
		                        <button class="btn_frm_save" type="button" id="btnSearchOrgCd" name="btnSearchOrgCd"><s:message code="SRCH" /></button> <!-- 검색 --> --%>
                           		<div class="sbox wd200">
								<label class="sbox_label" for="orgCd"></label>
                                <select id="orgCd" name="orgCd" class="wd200"  >
						            <option value=""><s:message code="WHL" /></option>
	                                <c:forEach var="code" items="${codeMap.orgCd}" varStatus="status" >
	                                       <option value="${code.codeCd}">${code.codeLnm}</option>
	                                </c:forEach>
                                </select>
                                </div>
                           </td>
                           <th scope="row" ><label for="infoSysCd"><s:message code="INFO.SYS.NM"/></label></th>
                           <td>
                           		<div class="sbox wd200">
								<label class="sbox_label" for="infoSysCd"></label>
                                <select id="infoSysCd" name="infoSysCd"  class="wd200" >
					            	<option value=""><s:message code="WHL" /></option>                                
                                </select>
                                </div>
                           </td>
                        </tr>      
                        <tr>  
							<th scope="row"><label for="dbConnTrgId">DB명</label></th>
							<!-- DBMS/스키마명 -->
							<td>
								<div class="sbox wd200">
								<label class="sbox_label" for="dbConnTrgId"></label>
								<select id="dbConnTrgId" name="dbConnTrgId" style="width:80%;">
									<option value=""><s:message code="WHL" /></option> 
								</select>
								</div>						
							</td>
							
							<th scope="row"><label for="dbSchId">소유자명</label></th>
							<!-- DBMS/스키마명 -->
							<td>
								<div class="sbox wd200">
								<label class="sbox_label" for="dbSchId"></label>							
								<select id="dbSchId" name="dbSchId" style="width:80%;">
									<option value=""><s:message code="WHL" /></option>									
								</select>
								</div>
							</td>
                       </tr>
                       <tr>
                       <th scope="row"><label for="tblNm"><s:message code="TBL.NM" /></label></th> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="dbcTblNm" id="dbcTblNm" class="wd98p" value="${search.tblNm}"/>
                                </span>
                            </td>
						<th scope="row"><label for="gapStsCd"><s:message code="STS" /></label></th>  <!-- GAP유형 -->
                           <td>
                           	<div class="sbox wd200">
							<label class="sbox_label" for="gapStsCd"></label>
							<select id="gapStsCd"  name="gapStsCd" class=""> 							
								<c:forEach var="code" items="${codeMap.gapStsCd}" varStatus="status">
									<option value="${code.codeCd }">${code.codeLnm}</option>
								</c:forEach>
						 	</select>
						 	</div>
							</td>
                       </tr>
                       <tr>
                       <th scope="row"><label for="tblClltDcd">데이터구분</label></th>
						<td colspan="3">
							<div class="sbox wd200">
							<label class="sbox_label" for="tblClltDcd"></label>
							<select id="tblClltDcd" class="wd200" name="tblClltDcd">
								<option value=""><s:message code="WHL" /></option>
								<c:forEach var="code" items="${codeMap.tblClltDcd}" varStatus="status">
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
            
         <c:choose>
         <c:when test="${search.popRqst == 'Y'}">
        	<div class="tb_comment"><s:message  code='ETC.MTA.RQST.POP' /></div>
         </c:when>
         <c:otherwise>
        	<div class="tb_comment"><s:message  code='ETC.POP' /></div>
         </c:otherwise>
         </c:choose>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<c:choose>
         <c:when test="${search.popRqst == 'Y'}">
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" />
         </c:when>
         <c:otherwise>
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
         </c:otherwise>
         </c:choose>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("search_grid_sheet", "100%", "400px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
	<div style="clear:both; height:15px;"><span></span></div>		
		<div id="" style="text-align: center;">
           <button class="btn_frm_save btn_colse" id="btnCloseBottom" type="button">닫기</button>           
        </div>
	
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>