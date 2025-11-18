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
<title></title>

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      

		 // 엑셀업로드용 저장 Event Bind
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
		}).show();
        
     // 삭제 Event Bind
        $("#btnDelete").click(function(){ 

        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
        	}
        //	doAction("Delete");  
        });
   	 
    	// 추가 Event Bind
        $("#btnNew").click(function(){ doAction("Add");  }); 
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
    
   	 	// 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
		
		//탭 초기화....
//	 	$( "#tabs" ).tabs({heightStyle:"fill"});
		//$( "#tabs" ).tabs();
		loadDetail();
		
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
// 		setautoComplete($("#frmSearch #symnLnm"), "SYMN");
		
    }
    
    
    
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	
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
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DBTBLSPAC.LST'/>", Align:"Center"}
                    /* No.|상태|선택|테이블스페이스ID|만료일자|시작일자|테이블스페이스물리명|테이블스페이스논리명|DB명|테이블스페이스유형|DDL표시여부|디폴트테이블스페이스여부|데이터파일|테이블스페이스그룹|초기사이즈|다음사이즈|최대사이즈|경고사이즈|한계사이즈|테이블스페이스스크립트|설명|버전|등록유형|작성일시|작성자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:30,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:40,  SaveName:"dbTblSpacId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",   Width:40,  SaveName:"expDtm",    Align:"Left", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",   Width:40,  SaveName:"strDtm",    Align:"Left", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:70,  SaveName:"dbTblSpacPnm",    Align:"Left", Edit:0, Hidden:0, KeyField:1}, 
                    {Type:"Text",   Width:70,  SaveName:"dbTblSpacLnm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Combo",   Width:60,  SaveName:"dbConnTrgId",    Align:"Left", Edit:0, Hidden:0, KeyField:1}, 
                    {Type:"Combo",   Width:60,  SaveName:"tblSpacTypCd",    Align:"Center", Edit:0, Hidden:0, KeyField:1}, 
                    {Type:"Combo",   Width:60,  SaveName:"ddlDisplay",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Combo",   Width:80,  SaveName:"defaultUsage",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:80,  SaveName:"dbTblSpacDatafile",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:30,  SaveName:"dbTblSpacGroup",    Align:"Right", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:30,  SaveName:"dbTblSpacInitSize",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"dbTblSpacNextSize",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"dbTblSpacMaxSize",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"dbTblSpacWrn",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",  Width:20,  SaveName:"dbTblSpacMgn",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",  Width:20,  SaveName:"dbTblSpacScript",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"objDescn",    Align:"Left", Edit:0, Hidden:0},          
                    {Type:"Text",   Width:30,  SaveName:"objVers",    Align:"Left", Edit:0, Hidden:1},          
                    {Type:"Combo",   Width:40,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:0},          
                    {Type:"Date",   Width:60,  SaveName:"writDtm",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},          
                    {Type:"Text",   Width:40,  SaveName:"writUserId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"writUserNm",    Align:"Left", Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});
	    SetColProperty("dbConnTrgId", ${codeMap.connTrgDbmsibs});
	    SetColProperty("ddlDisplay", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("defaultUsage", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	    SetColProperty("tblSpacTypCd", ${codeMap.tblSpacTypCdibs});
        //SetColHidden("rqstUserNm",1);

        InitComboNoMatchText(1, "");
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/damgmt/db/ajaxgrid/dbtblspac_dtl.do"/>', param, function(){

		//$('#tabs').show();
	});
}


		 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	
			var param = $("#frmSearch").serialize();
			loadDetail();
			$('#tblspac_sel_title').html('');
			grid_sheet.DoSearch("<c:url value="/commons/damgmt/db/getDbTblSpacList.do" />", param);
        	break;
       
        case 'SaveRow': //단건 저장
    		//saction (I-입력, U-수정)
    		var urls = '<c:url value="/commons/damgmt/db/regDbTblSpac.do"/>';
    		var param = $('form[name=frmInput]').serialize();
    		ajax2Json(urls, param, ibscallback);
    		break;
    		
        case 'Save' : //엑셀 업로드용 저장
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	
        	//ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/db/regDbTblSpacList.do"/>';
        	
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;	
        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
            
		case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
//         	setTreeCheckIBS(grid_sheet);
        	
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/commons/damgmt/db/delDbTblSpacList.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
            
        case 'Add' : //신규 추가
    		$('#tblspac_sel_title').html('');
    		loadDetail();
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
			doAction("Search");

			
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
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	//if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var dbTblSpacId = "&dbTblSpacId="+grid_sheet.GetCellValue(row, "dbTblSpacId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="TBL.SPACE.NM" /> : ' + param.dbTblSpacPnm + ' (' + param.dbTblSpacLnm + ')'; /* 테이블스페이스명 */
	$('#tblspac_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(dbTblSpacId);

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
	if (grid_sheet.SearchRows() == 1){
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);
		//선택한 그리드의 row 내용을 보여준다.....
		var tmphtml = '<s:message code="TBL.SPACE.NM" /> : ' + param.dbTblSpacPnm + ' (' + param.dbTblSpacLnm + ')'; /* 테이블스페이스명 */
		$('#tblspac_sel_title').html(tmphtml);
		
		var dbTblSpacId = "";
		dbTblSpacId = grid_sheet.GetCellValue(1, "dbTblSpacId");
		param = "dbTblSpacId="+dbTblSpacId;
		loadDetail(param);
	}

	
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="DB.TBLSPC.MNG" /></div> <!-- DB테이블스페이스 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='INQ.FORM' />"> <!-- 검색폼 -->
                   <caption><s:message code="INQ.FORM" /></caption> <!-- 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="dbConnTrgId"><s:message code="DB.MS.NM" /></label></th> <!-- DBMS명 -->
                                <td><select id="dbConnTrgId" class="wd300" name="dbConnTrgId">
                                       <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
					                	<c:forEach var="code" items="${codeMap.connTrgDbms}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select></td>
                                <th scope="row"><label for="dbTblSpacLnm"><s:message code="DB.TBL.SPACE.NM" /></label></th> <!-- DB테이블스페이스명 -->
                                <td><input type="text" id="dbTblSpacLnm" class="wd300" name="dbTblSpacLnm" /></td>
                            </tr>
                            <tr>
                            <th scope="row"><label for="tblSpacTypCd"><s:message code="TBL.SPACE.PTRN" /></label></th> <!-- 테이블스페이스 유형 -->
                                <td><select id="tblSpacTypCd" class="wd300" name="tblSpacTypCd">
                                       <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
					                	<c:forEach var="code" items="${codeMap.tblSpacTypCd}" varStatus="status">
					                    <option value="${code.codeCd}">${code.codeLnm}</option>
					                    </c:forEach>
                                    </select></td>
                                <th scope="row"><label for="ddlDisplay"><s:message code="DDL.DISPLAY.YN" /></label></th> <!-- DDL표시여부 -->
                                <td><select id="ddlDisplay" class="wd300" name="ddlDisplay">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
					                	<option value="Y"><s:message code="MSG.YES" /></option> <!-- 예 -->
										<option value="N"><s:message code="MSG.NO"/></option> <!-- 아니요 -->
                                    </select></td>
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
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="tblspac_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="TBL.SPACE.DTL" /></a></li> <!-- 테이블스페이스 상세 -->
	    
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
			
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->

</body>
</html>
