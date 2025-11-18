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
<title><s:message code="DB.MAPG.SOUR.TARG.MNG" /></title> <!-- DB매핑-소스타겟관리 -->

<script type="text/javascript">

var interval = "";
var connTrgSchJson = ${codeMap.connTrgSch} ;
var dbSchJson = ${codeMap.dbSch} ;


$(document).ready(function() {
	//그리드 초기화 
	initGrid();
	
	//그리드 사이즈 조절 초기화...		
// 	bindibsresize();
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	//탭 초기화....
	$( "#tabs" ).tabs().hide();
	//그리드 초기화 
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  });
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
    // 저장 Event Bind
    $("#btnSave").click(function(){ 

    	var rows = grid_sheet.IsDataModified();
    	if(!rows) {
    		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
    		return;
    	}
    	
    	//저장할래요? 확인창...
		var message = "<s:message code="CNF.SAVE" />";
		showMsgBox("CNF", message, 'Save');	
    	//doAction("Save"); 
    	
    }).show();
    
    
    // 삭제 Event Bind
    $("#btnDelete").click(function(){ 
    	//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			//삭제 확인 메시지
			//alert("삭제하시겠어요?");
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//         	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
    	}
    //	doAction("Delete"); 
    	
    });
    	
	//조회버튼 hidden
// 	$("#btnSave").hide();
	//삭제버튼 hidden
// 	$("#btnDelete").hide();
	//tree 추가 버튼 hidden
// 	$("#btnTreeNew").hide();
	$("#btnExcelLoad").hide();
	
	// 추가 Event Bind
        $("#btnNew").click(function(){ 
        	doAction("New");  
        	
        });
	
	
	//======================================================
   	 // 셀렉트 박스 초기화
   	//======================================================
   	/*
   	double_select(connTrgSchJson, $("#frmSearch #srcDbConnTrgId"));
   	$('select', $("#frmSearch #srcDbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});
   	double_select(connTrgSchJson, $("#frmSearch #tgtDbConnTrgId"));
   	$('select', $("#frmSearch #tgtDbConnTrgId").parent()).change(function(){
   		double_select(connTrgSchJson, $(this));
   	});
   	*/
   	create_selectbox(dbSchJson, $("#frmSearch #srcDbSchId"));
   	create_selectbox(dbSchJson, $("#frmSearch #tgtDbSchId"));
	
	//상세 페이지
// 	loadDetail();
});

$(window).on('load',function() {

	//dqmain 링크
	var linkFlag = "";
	linkFlag = "${linkFlag}";
	if(linkFlag == '1') {
		doAction("Search");
	}
	
});


$(window).resize(
    function(){

    	setibsheight($("#grid_01"));
        
    	//그리드 가로 스크롤 방지
//     	grid_sheet.FitColWidth();
    }
);

EnterkeyProcess("Search");

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        SetMergeSheet(5);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DBMAP.LST1'/>"},
                    /* No.|상태|선택|DB맵ID|소스(Source)|소스(Source)|소스(Source)|타겟(Target)|타겟(Target)|타겟(Target)|DDL이관여부|설명|버전|등록유형|만료일시|시작일시|작성일시|작성자 */
                    {Text:"<s:message code='COMMON.HEADER.DBMAP.LST2'/>", Align:"Center"} 
                    /* No.|상태|선택|DB맵ID|DB스키마명|DDL대상|스키마명|DB스키마명|DDL대상|스키마명|DDL이관여부|설명|버전|등록유형|만료일시|시작일시|작성일시|작성자 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"dbMapId",    	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:180,  SaveName:"srcDbSchId",    	Align:"Center", Edit:1, KeyField:1}, 
                    {Type:"Combo",   Width:70,  SaveName:"srcDdlTrgDcd",    	Align:"Center", Edit:0 }, 
                    {Type:"Text",   Width:100,  SaveName:"srcDbSchPnm",    	Align:"Center", Edit:0}, 
                    //{Type:"Text",   Width:100,  SaveName:"srcDbConnTrgPnm", Align:"Center", Edit:0}, 
                    {Type:"Combo",   Width:180,  SaveName:"tgtDbSchId",    	Align:"Center", Edit:1, KeyField:1}, 
                    {Type:"Combo",   Width:70,  SaveName:"tgtDdlTrgDcd",    	Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"tgtDbSchPnm",    	Align:"Center", Edit:0}, 
                  //  {Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgPnm", Align:"Center", Edit:0}, 
//                     {Type:"Text",   Width:100,  SaveName:"expDtm",    	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},	             
                    {Type:"Combo",   Width:100,  SaveName:"ddlTsfYn",    	Align:"Center", Edit:1},
                    {Type:"Text",   Width:330,  SaveName:"objDescn",    	Align:"Left", Edit:1},
                     {Type:"Text",   Width:100,  SaveName:"objVers",    	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:100,  SaveName:"regTypCd",    	Align:"Center", Edit:0},
                    {Type:"Date",   Width:100,  SaveName:"expDtm",    	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",   Width:100,  SaveName:"strDtm",    	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",   Width:120,  SaveName:"writDtm",    	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:70,  SaveName:"writUserNm",    	Align:"Center", Edit:0} 
                    
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
	    SetColProperty("srcDbSchId", 	${codeMap.dbSchibs});
	    SetColProperty("tgtDbSchId", 	${codeMap.dbSchibs});
	    SetColProperty("srcDdlTrgDcd", 	${codeMap.ddlTrgDcdibs});
	    SetColProperty("tgtDdlTrgDcd", 	${codeMap.ddlTrgDcdibs});
	    SetColProperty("ddlTsfYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        InitComboNoMatchText(1, "");
        
//        SetColHidden("ibsStatus"	,1);
//       SetColHidden("regTypCd"		,1);
//        SetColHidden("srcDbSchId"	,1);
//        SetColHidden("tgtDbSchId"	,1);
//         SetColHidden("ibsCheck",1);
//         SetColHidden("objVers",1);
//         SetColHidden("rqstDtm",1);
         SetColHidden("regTypCd",1);
          SetColHidden("srcDbSchPnm"	,1);
          SetColHidden("tgtDbSchPnm"	,1);
          
          
//         FitColWidth();

        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
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
    	case "New":        //추가
    	
    	//마지막 행에 추가..
    	grid_sheet.DataInsert(-1);
    	grid_sheet.SetCellValue(grid_sheet.GetSelectRow(), "tgtDdlTrgDcd", null);
    	grid_sheet.SetCellValue(grid_sheet.GetSelectRow(), "srcDdlTrgDcd", null);

                    
        break;
        
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch('<c:url value="/commons/damgmt/db/getdbmaplist.do" />', param);
        	
        	break;
        	
        case "Save" :
            
        	ibsSaveJson = grid_sheet.GetSaveJson(0);
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
        	//동일 소스 타겟  확인
        	for(var i=0; i<ibsSaveJson.data.length; i++){
        		if(ibsSaveJson.data[i].srcDbSchId  == ibsSaveJson.data[i].tgtDbSchId) {
	        		var message = "<s:message code='MSG.SAME.SOUR.SCHEMA.NM.TARG.SCHEMA.NM.EXIS' />" ; /* 동일한 소스스키마명 타겟스키마명이 존재합니다. */
	    			showMsgBox("INF", message); 
					return;
        		}
        	}
        	
        	//동일 DBMS 매핑 확인
			var DupRow = grid_sheet.ColValueDupRows("srcDbSchId|tgtDbSchId");
			if(DupRow != ""){
				var message = "<s:message code='MSG.SAME.DB.MS.MAPG.EXIS' />" ; /* 동일한 DBMS간 매핑이 존재 합니다. */
    			showMsgBox("INF", message); 
				return;
			}
			
			
            var url = '<c:url value="/commons/damgmt/db/dbMapSaveList.do"/>';
			var param = "";
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
        	break;
        	
        	
        case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/db/dbMapDelList.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
    }       
}
 
//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/damgmt/db/getdbmapDetail.do"/>', param, function(){
		//$('#tabs').show();
	});
}

//DBMS 스키마정보
function dbconntrgDtl(param) {
	grid_sub.DoSearch("<c:url value="/dq/criinfo/anatrg/selectDbSch_lst.do" />", param);
}

//이력조회
function getTrgDbmsHstLst(param) {
	grid_hst.DoSearch("<c:url value="/dq/criinfo/ajaxgrid/getTrgDbmsHstLst.do" />", param);
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	if(row < 1) return;
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		//상세정보조회
//    		loadDetail();
	}
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
		break;
		
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
				
		break;
		
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
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
	    <div class="menu_title"><s:message code="DB.MS.GAP.ANLY.SVR.MNG" /></div> <!-- DBMS GAP분석 서버관리 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='TRGT.DB.MS.INQ' />"> <!-- 대상DBMS조회 -->
                   <caption><s:message code="TRGT.DB.MS" /></caption> <!-- 대상DBMS -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="srcDbSchId"><s:message code="SOUR.DB.SCHEMA.NM2" /></label></th> <!-- 소스DB스키마명 -->
                           <td>
				            <select id="srcDbSchId" class="" name="srcDbSchId">
				             <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
				             </select>
                           </td>
                           <th scope="row"><label for="tgtDbSchId"><s:message code="TARG.DB.SCHEMA.NM" /></label></th> <!-- 타겟DB스키마명 -->
                           <td>
				            <select id="tgtDbSchId" class="" name="tgtDbSchId">
				             <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
				             </select>
                           </td>
                       </tr>
                       <tr>
                       	<th scope="row"><label for="ddlTsfYn"><s:message code="DDL.TFCT.YN" /></label></th> <!-- DDL이관여부 -->
                           <td colspan="3">
                            <select id="ddlTsfYn" class="" name="ddlTsfYn">
				            <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
				             <option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
							 <option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
				            </select>
				           </td>
                       </tr>
                       <tr>
                       	<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                           <td colspan="3">
                           	<textarea id="objDescn" name="objDescn" rows="2" class="wd98p"></textarea>
                           </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->
<!-- 
<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabs">	 -->
	  <ul>
<!--	    <li><a href="#tabs-1"><s:message code="DTL.INFO" /></a></li> --><!-- 상세정보 -->
<!-- 	    <li><a href="#tabs-2">DBMS 스키마정보</a></li> -->
<!-- 	    <li><a href="#tabs-3">변경이력</a></li> -->
	  </ul>
	  <!-- div id="tabs-1">
			<div id="detailInfo"></div>
	  </div -->
	  
	 </div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>