<!DOCTYPE html>
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
<title><s:message code="MENU.MNG" /></title> <!-- 메뉴 관리 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">
//엔터키 이벤트 처리
//EnterkeyProcess("Search");

var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnRqstNew").click(function(){ doAction("Add");  });
        
     	// 추가 Event Bind
//         $("#btnNewLow").click(function(){ doAction("NewLow");  });

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
		}).show();

        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 

        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
				showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
            	//showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
        	}
        //	doAction("Delete");  
        });
        
        // 엑셀내리기 Event Bind
       	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
         $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
        
         doAction("Search");
});

$(window).on('load',function() {
	initGrid();
	
	//페이지 호출시 처리할 액션...
	doAction('Add');
});


$(window).resize(
    
    function(){
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,VScrollMode:1};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='VRFC.RULE.HEADER'/>", Align:"Center"}
                    /* No.|상태|선택|검증룰ID|만료일시|시작일시|검증룰명|검증유형|검증룰|검증룰설명|설명|버전|등록구분코드|작성시각|작성자 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",      Width:20,  SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status",   Width:20,  SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:20,  SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:40,  SaveName:"vrfcId",      Align:"Left",	 Edit:0, Hidden:1}, 
                    {Type:"Date",     Width:30,  SaveName:"expDtm",      Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",     Width:30,  SaveName:"strDtm",      Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",     Width:100, SaveName:"vrfcNm",      Align:"Left",	 Edit:0, Hidden:0, KeyField:1}, 
                    {Type:"Combo",    Width:50,  SaveName:"vrfcTyp",     Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:180, SaveName:"vrfcRule",    Align:"Left",	 Edit:0, Hidden:0},
                    {Type:"Text",     Width:20,  SaveName:"vrfcDescn",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:100, SaveName:"dqiId",       Align:"Left",	 Edit:1, Hidden:1},
                    {Type:"Text",     Width:50,	 SaveName:"dqiLnm",      Align:"Left",	 Edit:1, Hidden:0},
                    {Type:"Text",     Width:80,  SaveName:"objDescn",    Align:"Left",	 Edit:0, Hidden:1}, 
                    {Type:"Text",     Width:20,  SaveName:"objVers",     Align:"Right",	 Edit:0, Hidden:1}, 
                    {Type:"Combo",    Width:50,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",     Width:30,  SaveName:"writDtm",     Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},          
                    {Type:"Text",     Width:30,  SaveName:"writUserId",  Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("regTypCd", ${codeMap.regTypCdibs});
	   	SetColProperty("vrfcTyp", ${codeMap.vrfcTypibs});

        InitComboNoMatchText(1, "");
      

        FitColWidth();  
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
        case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	
        	for(var i = 0; i < DelJson.data.length; i++){
        		if(DelJson.data[i].vrfcId.indexOf("STAT")==0){
        			showMsgBox("ERR", "<s:message code="ERR.VRFC.BASIC.DEL" />", '');
        			return;
        		}
        	}
        	var url = '<c:url value="/dq/vrfcrule/deleteVrfc.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case 'SaveRow': //단건 저장
        
        
    		var vrfc_Nm = new Array()
            
        	for(var i = 1; i <= grid_sheet.GetDataLastRow(); i++){
        		vrfc_Nm.push(grid_sheet.GetCellValue(i,'vrfcNm'))
        	}
        	 
			if(vrfc_Nm.includes($("#detailInfo #frmInput input[name=vrfcNm]").val()) == true && $("#detailInfo #ibsStatus").val() == 'I'){
				showMsgBox("ERR", "<s:message code="VRFC.NM.EXIST" />", '');
				vrfc_Nm.clear
				return;
			}
        
    		//saction (I-입력, U-수정)
        	if($("#frmInput #vrfcId").val().indexOf("STAT")==0){
    			showMsgBox("ERR", "<s:message code="ERR.VRFC.BASIC.MODIFY" />", '');
    			return;
        	}
        	$("#vrfcTyp").attr("disabled",false);
        	
    		var urls = '<c:url value="/dq/vrfcrule/saveVrfcRow.do"/>';
    		var param = $('form[name=frmInput]').serialize();
    		ajax2Json(urls, param, ibscallback);
    		break;
    		
        case 'Save' : //엑셀 업로드용 저장
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일

        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/dq/vrfcrule/saveVrfcs.do"/>';
        	
         	var param = "";
         	
            IBSpostJson2(url, SaveJson, param, ibscallback);
            
        	break;	
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);

        	grid_sheet.DoSearch('<c:url value="/dq/vrfcrule/selectVrfcList.do" />', param);
        	
        	break;
        	
    	case 'Add' : //신규 추가
    		$('#program_sel_title').html('<s:message code="MSG.VRFC.SRCH"/>'); /* 메뉴를 클릭하시면 상세정보를 조회합니다. */
    		loadDetail();
    		break;
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
    		break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
        
            break;
    }       
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/dq/vrfcrule/selectVrfcDetail.do"/>', param, function(){});
}

 
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
			doAction("Add");	
			doAction("Search");
				
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			$("#vrfcTyp").attr("disabled",true);
			doAction("Add");
			doAction("Search");

			
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
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	//if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var vrfcId = "&vrfcId="+grid_sheet.GetCellValue(row, "vrfcId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="VRFC.NM" /> : ' + param.vrfcNm; /* 메뉴명 */
	$('#program_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(vrfcId);
	

}



function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); /* 저장 성공했습니다. */
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); /* 저장 실패했습니다. */
	}
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="MENU.MNG" /></div> <!-- 메뉴 관리 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="메뉴조회">
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                          
                            <th scope="row"><label for="vrfcTyp"><s:message code="VRFC.TYP" /></label></th> <!-- 검증유형 -->
                            <td>
                            	<select id="vrfcTypP" name="vrfcTyp" class="wd80">
	                                <option selected="" value=""><s:message code="WHL" /></option>
	                                <c:forEach var="code" items="${codeMap.vrfcTyp }" varStatus="status" >
	                                <option value="${code.codeCd }">${code.codeLnm}</option>
	                                </c:forEach>
                               </select>
                           </td>  
                            <th scope="row"><label for="vrfcNm"><s:message code="VRFC.NM" /></label></th> <!-- 검증룰명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="vrfcNm" id="vrfcNm" style="width:98%;" />
                                </span>
                            </td>  
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div> 
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 --> <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<%-- <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />  --%>
		<div class="bt03">
			<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ" /></button> <!-- 전체조회 -->
			<button class="btn_search" id="btnRqstNew" name="btnRqstNew"><s:message code="ADDT" /></button> <!-- 추가 -->  
			<button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			<button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
		</div>
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<!-- <div id="tabs">
	 <ul>
	    <li><a href="#tabs-1"><s:message code="VRFC.DTL.INFO" /></a></li>  메뉴 상세정보 
	  </ul>  
	  <div id="tabs-1">
				상세정보 ajax 로드시 이용
			<div id="detailInfo"></div>
				상세정보 ajax 로드시 이용 END
	  </div>
	 </div> -->
	 
	 <div id="detailInfo"></div>
	<!-- 선택 레코드의 내용을 탭처리 END -->

</body>
</html>
