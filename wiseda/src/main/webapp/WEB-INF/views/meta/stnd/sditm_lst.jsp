<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>

<script type="text/javascript">

var dmnginfotpJson = ${codeMap.dmnginfotp} ;

var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
	// 범정부 사용 여부 세션 값
	var govYn = "<%=session.getAttribute("govYn")%>";
	
		//$( "#tabs" ).tabs();
		
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      
//      $("#btnSearch").hide();
//         $("#btnTreeNew").hide();
//         $("#btnSave").hide();
//         $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
        
        var bscLvl = parseInt("${bscLvl}");
        var selectBoxId = "${selectBoxId}";
        var firstSelectBoxId = selectBoxId.split("|");
        
     	//divID,  selectbox건수, selectbox ID
        create_selectbox2($("#selectBoxDiv"), bscLvl+1, selectBoxId+"|infotpId", '<s:message code="WHL" />'); /*전체*/
        
    	double_selectStndAsrt(dmnginfotpJson, $("#"+firstSelectBoxId[0]),$("#stndAsrt option:selected").val());
    	$('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
    		double_select(dmnginfotpJson, $(this));
    	});
    	
    	//$( "#tabs" ).tabs();
    	
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #sditmLnm"), "SDITM");
    	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    	setautoComplete($("#frmSearch #dmnLnm"), "DMN");
    	
    	//기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});
		
     	//달력팝업 추가...
     	$( "#searchBgnDe" ).datepicker();
    	$( "#searchEndDe" ).datepicker();
    	
    	$("#frmSearch #stndAsrt").change(function() {
            $("#"+firstSelectBoxId[0]).val("");
            double_selectStndAsrt(dmnginfotpJson, $("#"+firstSelectBoxId[0]),$("#stndAsrt option:selected").val());
    	   	$('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
    	// 		alert("2");
    			double_selectStndAsrt(dmnginfotpJson, $(this),$("#stndAsrt option:selected").val());
    	// 		alert($("#dmngId option:selected").text());
    			$("#dmngLnm").val($(this).prev().find("option:selected").text());
    			$("#infotpLnm").val($("#infotpId option:selected").text());
    			
    			$("#dataType, #dataLen, #dataScal").val("");
    			
    			var infoid = $(this).attr('id');
    	// 		alert(infoid);
    			if (infoid == 'infotpId') {
    				//인포타입 정보 자동 반영
    				var jsonlist = infotpinfolstJson;
    				for(var i=0; i < jsonlist.length; i++) {
    					if(jsonlist[i].infotpId == $("#infotpId").val()) {
    						$("#dataType").val(jsonlist[i].dataType);
    						$("#dataLen").val(jsonlist[i].dataLen);
    						$("#dataScal").val(jsonlist[i].dataScal);
    						break;
    					};
    				};
    			}
    		});
    	});
    	
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	loadDetail();
	
	// 타 탭에서 더블클릭으로 검색내용이 있을시 조회해준다.
	if(!isBlankStr("${sditmId}")) {
		var param = "sditmId="+"${sditmId}";
		grid_sheet.DoSearch("<c:url value="/meta/stnd/getsditmlist.do" />", param);
	} 
	
	var linkFlag = "";
	linkFlag = "${linkFlag}";
	if(linkFlag == '1') {
		doAction("Search");
	}
	
	
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
        
        var headtext = "<s:message code='META.HEADER.SDITM.LST'/>";
        
       	if ("${govYn}" == "Y") {
           	headtext += "<s:message code='META.HEADER.STNDITEM.RQST.IFM.4'/>"; //범정부 중앙메타 연계항목
        }
       	
        //headtext = "No.|표준분류|표준용어ID|표준용어논리명|표준용어물리명|논리명기준구분|물리명기준구분|도메인ID|도메인논리명|도메인물리명|데이터타입|데이터길이|데이터소수점길이|도메인그룹|인포타입|인포타입변경여부|암호화여부|중복여부|고객정보변환여부|전체영문의미|설명|요청번호|요청일련번호|등록유형코드|버전|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID";
        //headtext += "|공개/비공개 여부|비공개사유|개인정보여부|제약조건";
        
		var headers = [
						{Text:headtext, Align:"Center"}
					];
					
			
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
		
		InitHeaders(headers, headerInfo); 
		var cols = [
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
					{Type:"Combo",  Width:100,  SaveName:"stndAsrt",   	Align:"Center", Edit:0},
					{Type:"Text",   Width:100,  SaveName:"sditmId",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"sditmLnm",   	Align:"Left", Edit:0},
					{Type:"Text",   Width:120,  SaveName:"sditmPnm",   	Align:"Left", Edit:0},
					{Type:"Text",   Width:150,  SaveName:"lnmCriDs",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"pnmCriDs",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"dmnId",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"dmnLnm",   	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"dmnPnm",   	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"dataType",   	Align:"Left", Edit:0, Hidden:0}, 
					{Type:"Text",   Width:80,   SaveName:"dataLen",   	Align:"Right", Edit:0, Hidden:0},
					{Type:"Text",   Width:80,   SaveName:"dataScal",   	Align:"Right", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,  SaveName:"dmngId",   	Align:"Center", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,  SaveName:"infotpId",   	Align:"Left", Edit:0, Hidden:0},
					{Type:"Combo",  Width:100,  SaveName:"infotpChgYn", Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",  Width:80,   SaveName:"encYn",   	Align:"Center", Edit:0},
					{Type:"Combo",  Width:80,   SaveName:"dupYn",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Combo",  Width:130,  SaveName:"persInfoCnvYn", Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:200,  SaveName:"fullEngMean", Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"sditmPnmCamel", Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:200,  SaveName:"objDescn",   	Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:150,  SaveName:"rqstNo",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"rqstSno",   	Align:"Right", Edit:0, Hidden:1},
					{Type:"Combo",  Width:80,   SaveName:"regTypCd",   	Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"objVers",   	Align:"Right", Edit:0, Hidden:1},
					{Type:"Date",   Width:100,  SaveName:"frsRqstDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,  SaveName:"frsRqstUserId",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Date",   Width:100,  SaveName:"rqstDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
					{Type:"Text",   Width:100,  SaveName:"rqstUserId",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Date",   Width:100,  SaveName:"aprvDtm",   	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
					{Type:"Text",   Width:100,  SaveName:"aprvUserId",   	Align:"Left", Edit:0, Hidden:1}
					
				];
		
		if ("${govYn}" == "Y") {
			cols.push(
					//범정부연계항목
					{Type:"Combo",  Width:100,  SaveName:"openYn"  , 		Align:"Center",   Edit:0},
					{Type:"Combo",  Width:100,  SaveName:"priRsn"  ,    	Align:"Left",   Edit:0},
					{Type:"Combo",  Width:100,  SaveName:"prsnInfoYn"  ,    Align:"Center",   Edit:0},
					{Type:"Text",   Width:100,  SaveName:"constCnd"  ,		Align:"Left",   Edit:0}
			);
		}
                    
        InitColumns(cols);
        SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		//SetColProperty("persInfoGrd", 	${codeMap.persInfoGrdibs});
		SetColProperty("infotpChgYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("dupYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("persInfoCnvYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		
		if ("${govYn}" == "Y") {
			//범정부연계항목 콤보 목록 설정
			SetColProperty("openYn",		{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
			SetColProperty("priRsn",		${codeMap.nopenRsnCdibs});
			SetColProperty("prsnInfoYn",	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
			
		}
		
      	InitComboNoMatchText(1, "");
        //SetColHidden("rqstUserNm",1);
      
        // FitColWidth();  
        
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
        	
        	//그리드 초기화 한다.
			initDtlGrids();
        	
			var param = $("#frmSearch").serialize();
			loadDetail();
			$('#sditm_sel_title').html('');
			grid_sub_sditmwhereused.RemoveAll();
			grid_sub_sditmchange.RemoveAll();
			grid_sub_sditminit.RemoveAll();
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getsditmlist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
		    //보여지는 컬럼들만 엑셀 다운로드          
		    var downColNms = "";
		      
	     	for(var i=0; i<=grid_sheet.LastCol();i++ ){
	     		if(grid_sheet.GetColHidden(i) != 1){
	     			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
	     		}
	     	}
          
            //grid_sheet.Down2Excel({HiddenColumn:1,DownCols:downColNms, Merge:1});
            
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'표준용어조회'});  
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}

//그리드 초기화 한다.
var chkinitdtlgrids = false;
function initDtlGrids(){
	if (!chkinitdtlgrids) {
		
		//표준용어 - 단어구성정보 그리드 초기화
		initsubgrid_sditminit();
		//표준용어 - 변경이력 그리드 초기화
		initsubgrid_sditmchange();
		//표준용어 - Where used 그리드 초기화
		initsubgrid_sditmwhereused();
		//사전비교 용어
	 	initsubgrid_diccomparisonSditm();
		
	 	chkinitdtlgrids = true;	
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
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var sditmId = "&sditmId="+grid_sheet.GetCellValue(row, "sditmId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = "<s:message code='STRD.TERMS.LGC.NM' /> : " + param.sditmLnm; //표준용어논리명
	$('#sditm_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(sditmId);
	
	
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo1').load('<c:url value="/meta/stnd/ajaxgrid/sditminfo_dtl.do"/>', param, function(){
		
		if(!isBlankStr(param)) {
			initDtlGrids();
			grid_sub_sditmchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmchange_dtl.do" />', param);
			grid_sub_sditminit.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditminit_dtl.do" />', param);

			grid_sub_sditmdbmstype.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmdbmsdatatype_dtl.do" />', param);
			
			grid_sub_sditmwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmwhereused_dtl.do" />', param);	
			grid_sub_diccomparisonSditm.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmcomparison_dtl.do" />', param);
		}
		
		//$('#tabs').show();
	});
}

function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg,Response) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />",null,null,Response);
		return;
	}
	if(!isBlankStr("${sditmId}")) {
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);

		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = "<s:message code='STRD.TERMS.LGC.NM' /> : " + param.sditmLnm + " [ <s:message code='STRD.TERMSID' /> : " + param.sditmId + " ]"; //표준용어논리명, 표준용어ID
		$('#sditm_sel_title').html(tmphtml);
		
		var sditmId = "";
		sditmId = grid_sheet.GetCellValue(1, "sditmId");
		param = "sditmId="+sditmId;
		
		loadDetail(param);
// 		grid_sub_sditmchange.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmchange_dtl.do" />', param);
// 		grid_sub_sditminit.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditminit_dtl.do" />', param);
// 		grid_sub_sditmwhereused.DoSearch('<c:url value="/meta/stnd/ajaxgrid/sditmwhereused_dtl.do" />', param);
	}
}

</script>
</head>

<body>

 <div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="STRD.TERMS.INQ" /></div> <!-- 표준용어 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.TERMS.INQ' />"> <!-- 표준용어조회 -->
                   <caption><s:message code="STRD.TERMS.INQ.FORM" /></caption> <!-- 표준용어 검색폼 -->
                   <colgroup>
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   <col style="width:5%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                            
                   		<tr>
                   		    <th scope="row"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!-- 표준분류 -->
					        <td>
						        <select  id="stndAsrt" name="stndAsrt"  class="wd300">
	 					                <option value=""><s:message code="WHL"/></option>
						                <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
						        		  <option value="${code.codeCd}">${code.codeLnm}</option>
						        		</c:forEach> 
						        </select>
					        </td>
					        
                   			<th scope="row"><label for="sditmLnm"><s:message code="STRD.TERMS.NM" /></label></th> <!-- 표준용어명 -->
							<td><input type="text" id="sditmLnm" name="sditmLnm" class="wd200"/></td>                   		    
					        
							<th scope="row"><label for="infotpId"><s:message code="DMN.GRP.INFO.TY" /></label></th><!-- 도메인그룹/인포타입 -->
							<td>
								<div id="selectBoxDiv"> <span></span></div>


							</td>
							<th scope="row"><label for="dmnLnm"><s:message code="DMN.NM.ADD.2" /></label></th> <!-- 도메인명 -->
							<td><input type="text" id="dmnLnm" name="dmnLnm" class="wd200"/></td>
						<tr>
						<th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th> <!-- 데이터타입 -->
							<td>
								<select id="dataType" class="" name="dataType">
								<option value="">---<s:message code="WHL" />---</option> <!-- 전체 -->
								<c:forEach var="code" items="${codeMap.dataTypeCd}" varStatus="status" >
								<option value="${code.codeCd}">${code.codeLnm}</option>
								</c:forEach>
							 	</select>
							</td>
							<th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 -->
							<td>
								<select id="encYn" name="encYn">
									<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
									<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
									</select>
							</td>
					
<%-- 						<th scope="row"><label for="persInfoCnvYn"><s:message code="PERS.YN" /></label></th> <!-- 개인정보여부 --> --%>
<!-- 							<td> -->
<%-- 								<select id="persInfoCnvYn" name="persInfoCnvYn"> --%>
<%-- 									<option value=""><s:message code="WHL" /></option> <!-- 전체 --> --%>
<%-- 									<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 --> --%>
<%-- 									<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니오 --> --%>
<%-- 									</select> --%>
<!-- 							</td> -->
<%-- 							<th scope="row"><label for="persInfoGrd"><s:message code="PERS.GRD" /></label></th> <!-- 개인정보등급 --> --%>
<!-- 							<td> -->
<%-- 								<select  id="persInfoGrd" name="persInfoGrd"> --%>
<%-- 							        <option value=""><s:message code="CHC"/></option> --%>
<%-- 							        <c:forEach var="code" items="${codeMap.persInfoGrd}" varStatus="status"> --%>
<%-- 									  <option value="${code.codeCd}">${code.codeLnm}</option> --%>
<%-- 									</c:forEach> --%>
<%-- 								</select> --%>
<!-- 							</td> -->
						
                                         <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                                         <td class="bd_none">
                                         	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                                         	<a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                                         	<a href="#" class="tb_bt" id="seven"><s:message code="DD7" /></a> <!-- 7일 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                                         	<a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
                                         </td>
                                         <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
      		                             <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/></td>
						</tr>
						<tr>
							<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
							<td colspan="7"><input type="text" id="objDescn" name="objDescn" class="wd98p" /></td>
						</tr>

                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
 
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="sditm_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="TERMS.INFO" /></a></li>     <!-- 용어정보 -->
<%-- 	    <li><a href="#tabs-1"><s:message code="INFO" /></a></li>       <!-- 영문판용(한글버젼시 위 주석 사용) --> --%>
	    <li><a href="#tabs-2"><s:message code="TERMS.CMPS" /></a></li>     <!-- 용어구성 -->
<%-- 	    <li><a href="#tabs-2"><s:message code="CONF" /></a></li>       <!-- 영문판용(한글버젼시 위 주석 사용) --> --%>
	    <li><a href="#tabs-3"><s:message code="CHG.HSTR" /></a></li>       <!-- 변경이력 -->
	    <li><a href="#tabs-4"><s:message code="TERMS.DBMSTYPE" /></a></li> <!-- DBMS데이터타입 -->
	    <li><a href="#tabs-5">Where Used</a></li>
	    <li><a href="#tabs-6"><s:message code="DIC.COMPARISON" /></a></li>
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<div id="detailInfo1"></div>
	  </div>
	  <div id="tabs-2">
			<%@include file="sditminit_dtl.jsp" %>
	  </div>
	  <div id="tabs-3">
			<%@include file="sditmchange_dtl.jsp" %>
	  </div>
	   <div id="tabs-4">
			<%@include file="sditmdbmstype_dtl.jsp" %>
	  </div>
	   <div id="tabs-5">
			<%@include file="sditmwhereused_dtl.jsp" %>
	  </div>
	  <div id="tabs-6">
			<%@include file="dicComparisonSditm_dtl.jsp" %>
	  </div>
	 </div>
	 </div>
</body>
</html>