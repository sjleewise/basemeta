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
<title>정보시스템관리</title>
<script type="text/javascript">

//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
	                   
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  }).show();

        // 저장 Event Bind
        $("#btnSave").click(function(){
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.SAVE" />";
    		showMsgBox("CNF", message, 'Save');
        	//doAction("Save"); 	
		}).hide();

        // 삭제 Event Bind
        $("#btnDelete").click(function() { 
        	
//         	showMsgBox("ERR","정보시스템 삭제는 콜센터에 문의하세요.");
//         	return;

        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
//     			showMsgBox("CNF", "<s:message code="CNF.DEL" />"+"<br>정보시스템 관련 산출물도 함께 삭제 됩니다.", 'Delete');
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
        	}
        //	doAction("Delete");  
        }).show();
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).hide();

        // 엑셀업로 Event Bind
         $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).hide(); 
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
         $("#btnSearchOrgCd").hide();
     	<c:if test="${sessionScope.loginVO.orgLvlUseYn == null or (sessionScope.loginVO.orgLvlUseYn != null and sessionScope.loginVO.orgLvlUseYn eq 'Y')}">
             $("#orgNm, #btnSearchOrgCd").click(function(){
             	$("#frmSearch #orgCd").val("");
             	$("#frmSearch #orgNm").val("");
             	var url = "<c:url value='/meta/admin/popup/popMetaGiCode.do' />";
             	openLayerPop(url, 1000, 600);
            	}).show();
         </c:if>

//          SboxSetLabelEvent();

    }
);


$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
});


$(window).resize(
    
    function(){
//     	setibsheight($("#grid_01"));
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,UseHeaderSortCancel:1};
        SetConfig(cfg);
		//var headText = "No.|상태|선택|기관명|정보시스템코드|정보시스템명"; //|만료일시|시작일시";
			//headText += "|관련법령(보유목적)|구축년도|운영부서명|담당자명|전화번호|이메일|작성자ID|작성일시|설명|버전";
        var headText = "<s:message code='ADMIN.INFO.SYS.MNG.HEAD.LST'/>";
        var headers = [
                    {Text:headText, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        
		//Ellipsis
		//Wrap 줄바꿈
        var cols = [                        
                    {Type:"Seq",      Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status",   Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:60,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Combo",     Width:250,  SaveName:"orgCd",     Align:"Left", 	 Edit:0},

                    //입력필드 아님
                    {Type:"Text",     Width:250,  SaveName:"infoSysCd",      Align:"Left",   Edit:0, Hidden:1},
                    //입력필드 아님
                    {Type:"Text",     Width:450,  SaveName:"infoSysNm",      Align:"Left",   Edit:0, Hidden:0},

//                     {Type:"Text",     Width:130,  SaveName:"expDtm",      Align:"Left",   Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
//                     {Type:"Text",     Width:130,  SaveName:"strDtm",      Align:"Left",   Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},

                    {Type:"Text",     Width:130,  SaveName:"relLaw",      Align:"Left",   Edit:0, Hidden:1},
//                     {Type:"Text",      Width:100,  SaveName:"constYy",      Align:"Center",   Edit:1, Hidden:0, EditLen:4, KeyField:1},
                    {Type:"Combo",      Width:80	,  SaveName:"constYy",      Align:"Center",   Edit:0, Hidden:0},
//                     {Type:"Int",      Width:130,  SaveName:"totInvBdgt",      Align:"Right",   Edit:1, Hidden:0},
//                     {Type:"Int",      Width:130,  SaveName:"operBdgt",      Align:"Right",   Edit:1, Hidden:0},
// 사용자정보에서 가져옴
//입력필드 아님
                    {Type:"Text",     Width:230,  SaveName:"operDeptNm",      Align:"Left",   Edit:0, Hidden:0, Ellipsis:1},
                    {Type:"Text",     Width:110,  SaveName:"crgUserNm",      Align:"Left",   Edit:0, Hidden:0, Ellipsis:1 },
                    {Type:"Text",     Width:130,  SaveName:"crgTelNo",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"crgEmailAddr",      Align:"Left",   Edit:0, Hidden:1},
                 // 사용자정보에서 가져옴
                    {Type:"Text",     Width:130,  SaveName:"writUserId",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"writUserNm",      Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Date",     Width:130,  SaveName:"writDtm",      Align:"Left",   Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},

                    {Type:"Text",     Width:130,  SaveName:"objVers",      Align:"Left",   Edit:1, Hidden:1}
                ];
                    
        InitColumns(cols);

        var constYyComboText = "";
		var constStartYy = 1970;
		var constEndYy = 2070;
        
		for(var i=constStartYy; i<=constEndYy ; i++) {
			
			constYyComboText += i;
			
			if(i != constEndYy) {
				constYyComboText += "|";
			}
		}

        //콤보 목록 설정...
	   	SetColProperty("orgCd", ${codeMap.orgCdibs});
       // SetColProperty("exclDwldAuthYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        //SetColProperty("regTypCd", ${codeMap.regTypCdibs});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   
        /* SetColProperty("constYy", 	{ComboCode:"1970|1971|1972|1973|1974|1975|1976|1977|1978|1979|1980|1981|1982|1983|1984|1985|1986|1987|1988|1989|1990|1991|1992|1993|1994|1995|1996|1997|1998|1999|2000|2001|2002|2003|2004|2005|2006|2007|2008|2009|2010|2011|2012|2013|2014|2015|2016|2017|2018|2019|2020|2021|2022|2023|2024|2025|2026|2027|2028|2029|2030", 	
                                     ComboText:"1970|1971|1972|1973|1974|1975|1976|1977|1978|1979|1980|1981|1982|1983|1984|1985|1986|1987|1988|1989|1990|1991|1992|1993|1994|1995|1996|1997|1998|1999|2000|2001|2002|2003|2004|2005|2006|2007|2008|2009|2010|2011|2012|2013|2014|2015|2016|2017|2018|2019|2020|2021|2022|2023|2024|2025|2026|2027|2028|2029|2030"}); */
        SetColProperty("constYy", 	{ComboCode:constYyComboText, ComboText:constYyComboText});
                                        

        SetColBackColor("infoSysCd","#EAEAEA");
//        SetColBackColor("operDeptNm","#EAEAEA");
//        SetColBackColor("crgUserNm","#EAEAEA");
        SetColBackColor("crgTelNo","#EAEAEA");
        SetColBackColor("crgEmailAddr","#EAEAEA");
        
        InitComboNoMatchText(1, "");

//        FitColWidth();  
        
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
        	
    		var url = "<c:url value='/meta/admin/popup/adminsysinfo_pop.do' />";
			openLayerPop(url, 902, 416);
        	
            break;
            
        case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/meta/admin/delInfoSysList.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :

//         	if(!$("#frm").valid()) return false;
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = "<c:url value="/meta/admin/regInfoSysList.do"/>";
         	var param = "";
         
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
            
        case "Search":
            
	        /* var orgNm = $("#orgNm").val();
	    	if(orgNm == ""){
				showMsgBox("ERR", "<s:message code="MSG.ORG.CHC" />");
				return;
			} */
			
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/meta/admin/getInfoSysList.do" />', param);
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
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
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			doAction("Search");
			closeLayerPop();
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






/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_sheet_OnDblClick(Row, col, value, cellx, celly) {
    
	if(Row < 1) return;
	
	var url = "<c:url value='/meta/admin/popup/adminsysinfo_pop.do' />";
	var param = "&infoSysCd="+grid_sheet.GetCellValue(Row, "infoSysCd");
	param += "&orgCd="+grid_sheet.GetCellValue(Row, "orgCd");
	param += "&constYy="+grid_sheet.GetCellText(Row, "constYy");
	openLayerPop(url, 900, 416, param);
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
}

function grid_sheet_OnPopupClick(Row,Col) {
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
		setGridCellAll(grid_sheet,"pwChg", "<a class='BK_btn_grid2' ><s:message code='PWD.CHG' /></a>" ); /* 비밀번호변경 */
	}

}


function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}

function returnOrgCd(orgCd,orgNm) {
	$("#frmSearch #orgCd").val(orgCd);
	$("#frmSearch #orgNm").val(orgNm);
}

//숫자만 입력하기
function onlyNumberSet(text){
	if(window.event.keyCode < 48 || window.event.keyCode > 57){
		return false;
	}
}

function fnPaste(){
	var regex = /\D/ig;
	if(regex.test(window.clipboardData.getData("text"))){
		return false;
	} else{
		return true;
	}
}





</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code='INFO.SYS.MNG' /></div>
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='INFO.SYS.MNG.INQ' />">
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>                          
                          <%-- <th scope="row"><label for="orgNm"><s:message code="ADMIN.SEARCHG.GINM" /></label></th> <!-- 기관명 -->
                          <td>
                          	<input type="hidden" id="orgCd" name="orgCd" value="${sessionScope.loginVO.orgCd }"/>
                          	<input type="text" id="orgNm" name="orgNm" class="wd80p d_readonly" readonly value="${sessionScope.loginVO.orgNm }"/>
                          	<button class="btn_frm_save" type="button" id="btnSearchOrgCd" name="btnSearchOrgCd"><s:message code="SRCH" /></button> <!-- 검색 -->
                          </td>  --%>

                          <th scope="row"><label for="infoSysNm"><s:message code="ADMIN.GICODE.GISYS.NM" /></label></th> <!-- 정보시스템명 -->
                          <td colspan="3">
                          	<input type="text" id="infoSysNm" name="infoSysNm" class="wd80p"> 
                          	<%-- <select id="infoSysNm" name="infoSysNm" class="wd300">
								<option value=""><s:message code="WHL" /></option>
								<c:forEach var="code" items="${codeMap.infoSysCd}" varStatus="status">
									<option value="${code.codeLnm}">${code.codeLnm}</option>
								</c:forEach>
							</select> --%>
                          </td>   
                       </tr>
                       <tr>
                       	<th scope="row"><label for="constYy"><s:message code="CONST.YEAR" /></label></th> <!-- 구축년도 -->
                       	<td>
                       		<input type="text" id="constYy" name="constYy" class="wd80p" maxlength="4" onKeyPress="return onlyNumberSet(this);" onPaste="return fnPaste();" onkeyup="this.value=this.value.replace(/[\ㄱ-하-ㅣ가-힣]/g,'');"/> 
                       	</td>
                       	<!--  -->
                       	<th scope="row"><label for="crgUserNm"><s:message code="CHG.R.NM" /></label></th> <!-- 담당자명 -->
                       	<td>
                       		<input type="text" id="crgUserNm" name="crgUserNm" class="wd80p"> 
                       	</td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />   
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
        <div class="tb_comment"><s:message code="ETC.INFO.SYS.COMM" /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   		
        <!-- 조회버튼영역  -->         
		<%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> --%>
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain2.jsp" />
		
</div>        

	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "538px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

</body>
</html>