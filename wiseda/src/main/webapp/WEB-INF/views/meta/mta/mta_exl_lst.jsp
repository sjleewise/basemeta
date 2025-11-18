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



$(document).ready(function() {
	
	setCodeSelect("mtaDgrSel", "L", $("form[name=frmSearch] #mtaDgr"));
	
	// 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

    // 엑셀업로 Event Bind
     $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).show();
	
    // 조회 Event Bin
    $("#btnSearch").click(function(){ doAction("Search");  }).show();

  
   	$("#frmSearch #gapStsCd").change(function(){

		grid_sheet.RemoveAll(false);  
    });

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


	<c:if test='${search.gapStsCd == null}'>
		$("#frmSearch #gapStsCd").val("U").find("option:selected"); 
	</c:if>
	<c:if test='${search.gapStsCd != null}'>
		//메인에서 메타데이터등록 클릭 시 이동 
		doAction("Search");
	</c:if>
	
	//SboxSetLabelEvent();

	
   	
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
        
	    var headertext1  = "No.|상태|선택|차수";
	        headertext1 += "|기관코드|기관명|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보|정보시스템정보";
	    	headertext1 += "|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보|데이터베이스정보";
	    	headertext1 += "|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보|테이블정보";
	    	headertext1 += "|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보|컬럼정보";
	    var headertext2  = "No.|상태|선택|차수";
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
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq",         	Align:"Center", Edit:0},
                    {Type:"Status",   Width:40,   SaveName:"ibsStatus",      	Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",       	Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",     Width:40,  SaveName:"mtaDgr",   	   	 	Align:"Center",   Edit: 0},
                    //기관코드|기관명|정보시스템코드|정보시스템명|관련법령(보유목적)|구축년도|운영부서명|담당자명|담당자전화번호|담당자이메일
                    {Type:"Text",     Width:150,  SaveName:"orgCd",   	    	Align:"Left",   Edit: 0, Hidden:1},
                    {Type:"Combo",     Width:150,  SaveName:"orgNm",   	   	 	Align:"Center",   Edit: 0},
                    {Type:"Text",     Width:120,  SaveName:"infoSysNm", 	    Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",     Width:150,  SaveName:"infoSysCd", 	    Align:"Center",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"relLaw", 	   		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:80,  SaveName:"constYy", 	    	Align:"Right",   Edit:0},
//                     {Type:"Text",     Width:150,  SaveName:"deptId", 	    	Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"deptNm", 	    	Align:"Center",   Edit:0},
//                     {Type:"Text",     Width:150,  SaveName:"chrpId", 	    	Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"crgUserNm", 	    	Align:"Center",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"crgTelNo", 	    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"crgEmailAddr", 	    Align:"Left",   Edit:0},
					//DBID|논리DB명|물리DB명|DB설명|적용업무|DBMS종류|DBMS버전|운영체제종류|운영체제버전|구축일자|테이블수|데이터용량(MB)|수집제외사유
                    {Type:"Text",     Width:180,  SaveName:"dbConnTrgId",   Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"dbConnTrgLnm",	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:100,  SaveName:"dbConnTrgPnm",	Align:"Center", Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"dbDescn",		Align:"Center", Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"applctnDuty",	Align:"Center", Edit:0},
                    {Type:"Combo",     Width:80,   SaveName:"dbmsTypCd",		Align:"Center", Edit:0},
                    {Type:"Combo",     Width:80,   SaveName:"dbmsVersCd",	Align:"Center", Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"osTypCd",		Align:"Center", Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"osVersCd",		Align:"Center", Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"constDt", 	    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"tblCnt", 	    Align:"Right",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"dataCpt", 	    Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"colctExRescn", 	Align:"Left",   Edit:0},
					//소유자Id|테이블소유자|테이블영문명|테이블한글명|테이블설명|테이블볼륨(ROW수)|테이블유형|품질진단여부|보존기간|발생주기|공개/비공개여부(테이블)|비공개사유(테이블)|상세관련근거
					{Type:"Text",     Width:80,   SaveName:"dbSchId",   	Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"dbSchPnm",      Align:"Center", Edit:0}, 
                    {Type:"Text",     Width:100,  SaveName:"dbcTblPnm",     Align:"Left",   Edit:0}, 
                    {Type:"Text",     Width:100,  SaveName:"dbcTblLnm", 	Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"tblDescn",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"tblRowCnt",		Align:"Right",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"tblTypCd",		Align:"Center",   Edit:0},
                    {Type:"Combo",     Width:70,   SaveName:"qltyDgnsYn",	Align:"Center",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"psvPerd",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"ocrnCycl",		Align:"Left",   Edit:0},
                    {Type:"Combo",     Width:70,   SaveName:"tblOpnYn",		Align:"Center",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"tblNopnRescn",	Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"dtlRelBasis",	Align:"Left",   Edit:0},
                    //컬럼영문명|컬럼한글명|컬럼설명|데이터타입|데이터길이|데이터포맷|NOT NULL여부|PK정보|FK정보|제약조건|공개/비공개여부(컬럼)|비공개사유(컬럼)|개인정보여부|암호화여부
                    {Type:"Text",     Width:100,  SaveName:"dbcColPnm",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"dbcColLnm",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"colDescn",		Align:"Left",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"dataTyp",		Align:"Center",   Edit:0},
                    {Type:"Text",     Width:80,   SaveName:"dataLen",		Align:"Right",   Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"dataFrm",		Align:"Left",   Edit:0},
                    {Type:"Combo",     Width:70,   SaveName:"nullYn",		Align:"Center",   Edit:0},
                    {Type:"Combo",     Width:70,   SaveName:"pkYn",			Align:"Center",   Edit:0},
                    {Type:"Combo",     Width:70,   SaveName:"fkYn",			Align:"Center",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"rstcCond",		Align:"Left",   Edit:0},
                    {Type:"Combo",     Width:70,   SaveName:"colOpnYn",		Align:"Center",   Edit:0},
                    {Type:"Text",     Width:150,  SaveName:"colNopnRescn",	Align:"Left",   Edit:0},
                    {Type:"Combo",     Width:70,   SaveName:"persInfoYn",	Align:"Center",   Edit:0},
                    {Type:"Combo",     Width:70,   SaveName:"encYn",			Align:"Center",   Edit:0}
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

               
        SetColHidden("ibsStatus",1);
        //SetColHidden("infoSysCd",1);
       // SetColHidden("orgCd",1);
        //SetColHidden("dbConnTrgId",1);
        //SetColHidden("mtaTblId",0);

      	SetColHidden("ibsCheck"	,1);      
      	
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
        	        	
        	grid_sheet.DoSearch("<c:url value="/meta/mta/popup/getMtaExlLst.do" />", param);

        	break;
                      	      
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'기관메타수동업로드양식'});
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
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
									<td><span class="input_file"> <input type="text"
											name="dbcTblPnm" id="dbcTblPnm" class="wd98p"
											value="${search.tblNm}" />
									</span></td>
									
									<th scope="row"><label for="mtaDgr">등록차수</label></th>
									<td colspan="3">
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
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />

		<div style="clear: both; height: 10px;"></div>

		<!-- 그리드 입력 입력 -->
		<div id="grid_01" class="grid_01">
			<script type="text/javascript">createIBSheet("grid_sheet", "100%", "460px");</script>
		</div>
		<!-- 그리드 입력 입력 End -->

		<div style="clear: both; height: 5px;"></div>
</body>
</html>

