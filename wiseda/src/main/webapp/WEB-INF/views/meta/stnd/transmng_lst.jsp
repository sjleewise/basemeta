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
<title></title>

<script type="text/javascript">


var interval = "";
var initTblGrid = false;
var initIdxGrid = false;
EnterkeyProcess("Search");

$(document).ready(function() {
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
        $("#grid_02").hide();
//      $("#btnSearch").hide();
        $("#btnTreeNew").hide();
//         $("#btnSave").hide();
        $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
		// 저장 Event Bind
		$("#btnSave").click(function(){
			//var rows = grid_sheet.FindStatusRow("I|U|D");
			var rows ="";
        	if($("#objDcd").val()=="DMN"){
			   rows = grid_sheet.IsDataModified();
        	}else{
        	   rows = grid_sheet2.IsDataModified();
        	}
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save");
		}).show();
		
		//DDL조회 Event Bind
		$("#btnDelete").click(function(){
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code='MSG.INQ.DDL.CHC' />"); //조회할 DDL을 선택해 주세요.
        		return;
        	}
        	//doAction("ShowDdl");
		}).hide();
    	
// 		$( "#tabsTbl" ).hide();
// 		$( "#tabsIdx" ).hide();
		
    	//======================================================
	   	 // 셀렉트 박스 초기화
	   	//======================================================
	$("#objDcd").change(function(){
		if($("#objDcd").val()=="SDITM"){
	        $("#grid_02").show();
	        $("#grid_01").hide();
	        $("#sditmLnm").attr("disabled", false);
	        $("#sditmLnm").val("");
		}else{
			$("#grid_02").hide();
	        $("#grid_01").show();
	        $("#sditmLnm").attr("disabled", true);
	        $("#sditmLnm").val("");
		}
		
		
	});
	   	

});

$(window).on('load',function() {
// 	alert('window.load');
			
	
		initGrid();
		initGrid2();
	//달력팝업 추가...

	
	//$( "#layer_div" ).show();
	
});




function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.TRANSMNG.LST.1'/>|"
							+"<s:message code='META.HEADER.TRANSMNG.LST.2'/>|"
							+"<s:message code='META.HEADER.TRANSMNG.LST.3'/>", Align:"Center"}
						] 
						//No.|상태|암호화여부|테스트변환여부|도메인ID|도메인논리명|도메인물리명|도메인그룹|인포타입|데이터타입|길이|소수점
						//코드값유형코드|코드값부여방식코드|대분류코드
						//설명|등록유형코드
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Status", Width:50,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"encYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"CheckBox",   Width:100,   SaveName:"transYn",	 	Align:"Center", Edit:1, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:130,   SaveName:"dmnLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,   SaveName:"dmnPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:150,   SaveName:"dmngId",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:150,   SaveName:"infotpId",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:130,   SaveName:"dataType",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataLen",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataScal",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"cdValTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:120,   SaveName:"cdValIvwCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:80,   SaveName:"dmnDscd",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:500,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
					];
                    
        InitColumns(cols);
        SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
        // FitColWidth();  
        SetSheetHeight("400");
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

function initGrid2()
{
    
    with(grid_sheet2){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='META.HEADER.TRANSMNG.LST.4'/>", Align:"Center"}
					];
					//No.|상태|암호화여부|테스트변환여부|표준용어ID|표준용어논리명|표준용어물리명|도메인ID|도메인논리명|도메인물리명|데이터타입|길이|소수점|도메인그룹|인포타입|설명
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 
			var cols = [
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"Status", Width:50,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"encYn",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"CheckBox",   Width:100,   SaveName:"transYn",	 	Align:"Center", Edit:1, Hidden:0},
						{Type:"Text",   Width:100,  SaveName:"sditmId",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"sditmLnm",   	Align:"Left", Edit:0},
						{Type:"Text",   Width:150,  SaveName:"sditmPnm",   	Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"dmnId",   	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:150,  SaveName:"dmnLnm",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:150,  SaveName:"dmnPnm",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,  SaveName:"dataType",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,  SaveName:"dataLen",   	Align:"Right", Edit:0, Hidden:0},
						{Type:"Text",   Width:80,  SaveName:"dataScal",   	Align:"Right", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,  SaveName:"dmngId",   	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,  SaveName:"infotpId",   	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:500,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Combo",   Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:1},
					];
                    
        InitColumns(cols);
        SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("infotpChgYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
		SetColProperty("encYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */

      	InitComboNoMatchText(1, "");
        //SetColHidden("rqstUserNm",1);
      
        // FitColWidth();  
        SetSheetHeight("400");
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet2);    
    //===========================
   
}




		 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	
        	
			var param = $("#frmSearch").serialize();
            if($("#objDcd").val()=="DMN"){
			    grid_sheet.DoSearch("<c:url value="/meta/stnd/getdmntranslist.do" />", param);
            }else{
            	grid_sheet2.DoSearch("<c:url value="/meta/stnd/getsditmtranslist.do" />", param);
            }
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        case "Save" :
        	//TODO 공통으로 처리...
         	var SaveJson = "";
        	if($("#objDcd").val()=="DMN"){
        	    SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	    //데이터 사이즈 확인...
        	    if(SaveJson.data.w == 0) return;
        	    var url = '<c:url value="/meta/stnd/saveDmnTransYnPrc.do"/>';
         	    var param = "";
        	}else{
        	    SaveJson = grid_sheet2.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
            	
            	//데이터 사이즈 확인...
            	if(SaveJson.data.w == 0) return;
            	//alert(JSON.stringify(SaveJson));
            	var url = '<c:url value="/meta/stnd/saveSditmTransYnPrc.do"/>';
             	var param = "";
        		
        	}
             IBSpostJson2(url, SaveJson, param, ibscallback);
             
        	break;
               	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로드
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}
    
    
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			doAction("Search");
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
//$("#hdnRow").val(row);
	
	if(row < 1) return;

}




function grid_sheet_OnSaveEnd(code, message) {

}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
 	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DDL.TBL.DEMD.MNG" /></div> <!-- DDL테이블 요청관리 -->
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
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="objDcd"><s:message code="DSTC" /></label></th> <!-- 구분 -->
							<td colspan="3">
							    <select id = "objDcd" name="objDcd">
							        <option value="DMN" selected><s:message code="DMN" /></option> <!-- 도메인 -->
							        <option value="SDITM"><s:message code="STRD.TERMS" /></option> <!-- 표준용어 -->
							    </select>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="dmnLnm"><s:message code="DMN.NM" /></label></th> <!-- 도메인명 -->
							<td>
                               <input type="text" id="dmnLnm" name="dmnLnm"/>
							</td>
							<th scope="row"><label for="sditmLnm"><s:message code="STRD.TERMS.NM" /></label></th> <!-- 표준용어명 -->
							<td>
                               <input type="text" id="sditmLnm" name="sditmLnm" disabled/>
							</td>
						</tr>
						<tr>
						<th scope="row"><label for="encYn"><s:message code="ENTN.YN" /></label></th> <!-- 암호화여부 -->
							<td>
                               <select id="encYn" name="encYn">
                                 <option value="">--<s:message code="WHL" />--</option> <!-- 전체 -->
                                 <option value = "Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                 <option value = "N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                               </select>
							</td>
							<th scope="row"><label for="transYn"><s:message code="TESTCONV.YN"/></label></th> <!-- 테스트변환여부 -->
							<td>
                               <select id="transYn" name="transYn">
                                 <option value="">--<s:message code="WHL" />--</option> <!-- 전체 -->
                                 <option value = "Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
                                 <option value = "N"><s:message code="MSG.NO"/></option> <!-- 아니오 -->
                               </select>
							</td>
						</tr>
						
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<div class="grid_02" id="grid_02">
	     <script type="text/javascript">createIBSheet("grid_sheet2", "100%", "300px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="ddl_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>


	 </div>
</body>
</html>