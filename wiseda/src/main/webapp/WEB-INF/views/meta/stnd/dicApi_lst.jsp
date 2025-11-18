<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

EnterkeyProcess("Search");

$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
  
    // 조회 Event Bind
    $("#btnSearch").click(function(){ doAction("Search");  }).show();
                  
	//추가 Event Bind
	$("#btnTreeNew").show();
	
    // 추가 Event Bind
    $("#btnNew").click(function(){ doAction("New");  }).show();
    
    // 저장 Event Bind
    $("#btnSave").click(function(){
		icons: { primary: "ui-icon-lightbulb" }
	}).click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...    		
		//저장할래요? 확인창...
		var message = "검색 후 저장 하시겠습니까?"; //표준도메인 자동분할을 실행하시겠습니까?
		showMsgBox("CNF", message, 'Save');	
	}).show().text("저장"); //저장
	
	
    // 삭제 Event Bind
    $("#btnDelete").click(function(){
    	showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
    }).show();
    
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();

    // 엑셀업로 Event Bind
     $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).show();
    
     setCodeSelect("dicRqstNo", "L", $("form[name=frmSearch] #dicRqstNo"));

});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	//페이지 호출시 처리할 액션...
// 	doAction('Search');
	
	//$( "#layer_div" ).show();
});


$(window).resize(
    
    function(){
    	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	setibsheight($("#grid_01"));        
    	// grid_sheet.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        

		//헤더 : ibs(3개) 단어명 출처 영문명 설명
		var headtext  = "<s:message code='META.HEADER.DIC.API.LST'/>";
		//No.|상태|선택|요청번호|단어명|사전종류|영문명|설명

	var headers = [
				{Text:headtext, Align:"Center"}
			];
	
	var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
	
	InitHeaders(headers, headerInfo); 

	var cols = [						
				{Type:"Seq",      Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
				{Type:"Status",   Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
				{Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0},
				
				{Type:"Text",   Width:130,  SaveName:"dicRqstNo",  Align:"Left", Edit:1, Hidden:1}, 
				{Type:"Text",    Width:150,   SaveName:"wdName",	  Align:"Left", Edit:1, KeyField:1},						
				{Type:"Text",    Width:150,   SaveName :"dicOrgn",	  Align:"Center", Edit:0},			
				{Type:"Text",    Width:150,   SaveName:"ennm",	  Align:"Left", Edit:0},	
				{Type:"Text",    Width:150,   SaveName:"dicDesc",	  Align:"Left", Edit:0},	
					
			];
				
		InitColumns(cols);
		
		//콤보 목록 설정..
		// SetColProperty("dicOrgn", ${codeMap.dicOrgnibs});

		InitComboNoMatchText(1, "");
		
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
}

function doAction(sAction, param)
{
	
    switch(sAction)
    {		    
	    case "New":
	    	var iRow = grid_sheet.DataInsert(-1);
	    	break;
	    	
        case "Save":
        	var param = $("#frmSearch #dicOrgn").val();
        	
        	var url = "<c:url value="/meta/stnd/postKoreaApi.do"/>";
          	var savejson = grid_sheet.GetSaveJson(0);
          	
          	for(var i=0; i<savejson.data.length; i++){
          		savejson.data[i].dicOrgn = param;
          	}
          	//grid_sheet.SetColProperty("dicOrgn", ${codeMap.dicOrgnibs});
          	if(savejson.data.length == 0) return;
          	console.log(savejson);
          	IBSpostJson2(url,savejson,param,ibscallback);
          	
          	break;
          	
        case "Search":
        	var tmpstr = "";
        	if(param != null && param != "" && param != "undefined") {
        		tmpstr = "&dicRqstNo="+param + "&wdName="+$("#wdName").val();
        	}else{
        		if($("#dicRqstNo").val() == "") {
            		showMsgBox("ERR", "<s:message code='MSG.INQ.LST.CHC' />"); //조회목록을 먼저 선택하세요.
            		return; 
            	}
        		tmpstr = "&dicRqstNo="+ $("#dicRqstNo").val() + "&wdName="+$("#wdName").val();
        	}        	
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getDicList.do" />", tmpstr);
    		break;
        	
 	 	case "Delete" : 
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	
			var prop = {KeyField:0};
        	grid_sheet.SetColProperty(0,"wdName",prop);
        	
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");

        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/meta/stnd/delDicList.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
			break;   
			
 	    case "LoadExcel":  //엑셀업로드
 	    	grid_sheet.LoadExcel({Mode:'HeaderMatch'});
 	        break;
 	        
        case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel(    { HiddenColumn:1 , Merge:1, FileName:'사전API조회'}    );
			break;
	    		

    }       
}

function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    if(row < 1) return;
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	

}

function postProcessIBS(res) {
	switch(res.action) {
		
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			if(!isBlankStr(res.ETC.dicrqstno)) {
				setCodeSelect("dicRqstNo", "L", $("form[name=frmSearch] #dicRqstNo"));
				doAction("Search", res.ETC.dicrqstno);    		
			} 
			break;
			
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :

			if(!isBlankStr(res.ETC.dicrqstno)) {
				setCodeSelect("dicRqstNo", "L", $("form[name=frmSearch] #dicRqstNo"));
				doAction("Search",res.ETC.dicrqstno);      		
	    	} 
			break;
	}	
}

</script>
</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">

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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIC.INQ' />"> <!-- 사전조회 -->
                   <caption><s:message code="DIC.INQ.FORM" /></caption> <!-- 사전 검색폼 -->
                   
                   <colgroup>
                   <col style="width:11%;" />
                   <col style="width:30%;" />
                   <col style="width:11%;" />
                   <col style="width:15%;" />                   
                   <col style="width:11%;" />
                   <col style="width:*;" />
                   </colgroup>
                   
                   <tbody>                            
                        <tr>
                            <th scope="row"><label for="dicRqstNo"><s:message code="INQ.LST" /></label></th> <!-- 조회목록 -->
							<td>
								<span id="selectDicRqstNo"><select id="dicRqstNo"  name="dicRqstNo" class="wd400">
								<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
								</select></span>
							</td>
	                        
	                        <th scope="row"><label for="dicOrgn"><s:message code="DIC.ORGN" /></label></th> <!-- 분할구분 -->
							<td>
								<select id="dicOrgn"  name="dicOrgn" class="wd300">
				                 	<%-- <option value=""><s:message code="WHL"/></option> --%>
					                 <c:forEach var="code" items="${codeMap.dicOrgn}" varStatus="status">
					         		  <option value="${code.codeCd}">${code.codeLnm}</option>
					         		</c:forEach> 									
								</select>
							</td>
					
							
                        </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.EXIS.ENSN.ABRV.DISPLAY.DATA.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <!-- 존재하는 영문약어는 빨간색으로 표시됩니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
        </form>
        
        
		<div style="clear:both; height:10px;"><span></span></div>
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonStndDiv.jsp" />
	</div>

	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div> 	
	<!-- 그리드 입력 입력 End -->
   	
</div>


</body>
</html>

