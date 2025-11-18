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
<title><s:message code="SYS.TRRT.MNG" /></title> <!-- 시스템영역관리 -->

<script type="text/javascript">

var interval = "";

//엔터키 이벤트 처리
EnterkeyProcess("Search");


$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  });

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
//             	showMsgBox("CNF", "<s:message code='MSG.CHC.TBL.CLMN.DEL.DEL' />", "Delete"); /* 선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까? */
        	}
        //	doAction("Delete");  
        });
        
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );
      
    }
);

$(window).on('load',function() {
// 	alert("window.load"	);
	//그리드 초기화 
	initGrid();

	$(window).resize(); 

});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
   
	setibsheight($("#grid_01"));    
	// grid_sheet.SetExtendLastCol(1);    
});



function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.SYSAREA.LST'/>", Align:"Center"}
                    /* No.|상태|선택|시스템영역ID|시스템영역논리명|시스템영역물리명|시스템영역약어|표준적용여부|레거시구분코드|담당사용자ID|담당사용자명|설명|버전|등록유형|작성일시|작성자ID|작성자명 */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:40,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"sysAreaId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:130,  SaveName:"sysAreaLnm",   Align:"Left", Edit:1, KeyField:1},
                    {Type:"Text",   Width:130,  SaveName:"sysAreaPnm",   Align:"Left", Edit:1, KeyField:1}, 
                    {Type:"Text",   Width:100,   SaveName:"sysAreaAbrNm", Align:"Left", Edit:1, Hidden:0},
//                    {Type:"Combo",  Width:80,   SaveName:"stdAplYn",   	 Align:"Center", Edit:1, ComboCode:"N|Y", ComboText:"아니요|예"},
					{Type:"Combo",  Width:80,   SaveName:"stdAplYn",   	 Align:"Center", Edit:1, ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES' />"},
                    {Type:"Combo",  Width:100,   SaveName:"lecyDcd",   	 Align:"Center", Edit:1},
                    {Type:"Popup",   Width:80,   SaveName:"crgUserId",    Align:"Left", Edit:0, Hidden:0},
                    {Type:"Popup",   Width:80,   SaveName:"crgUserNm",    Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",   Width:250,  SaveName:"objDescn",     Align:"Left", 	 Edit:1},
                    {Type:"Text",   Width:130,  SaveName:"objVers",      Align:"Right",   Edit:0, Hidden:1},
//                    {Type:"Combo",  Width:100,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"신규|변경|삭제",}, 
					{Type:"Combo",  Width:100,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"<s:message code='COMBO.CUD' />",},
                    {Type:"Date",   Width:130,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:100,  SaveName:"writUserId",   Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"writUserNm",   Align:"Left", Edit:0}
                ];
                    
        InitColumns(cols);
        
        InitComboNoMatchText(1, "");
     	SetColProperty("lecyDcd", ${codeMap.lecyDcdibs});
//         SetColHidden("ibsStatus",1);
//         SetColHidden("objVers",1);
//         SetColHidden("regTypCd",1);
//         SetColHidden("writDtm",1);
//         SetColHidden("writUserId",1);
//         SetColHidden("writUserNm",1);
      
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
        case "New":        //추가
        	//첫행에 추가...
        	grid_sheet.DataInsert(0);
        	//마지막 행에 추가..
        	//grid_sheet.DataInsert(-1);
        
            //var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
        
            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
        case "Delete" :
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	
        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/sysarea/delsysarealistu.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	//$.postJSON(url, DelJson, ibscallback); 

        	//자료를 실제 삭제하거나 업데이터만 처리시 key값만 "|" 조인하여 처리함...
        	/* var sRow = grid_sheet.FindCheckedRow("ibsCheck");
        	//받은 결과를 배열로 생성한다.
        	var arrRow = sRow.split("|");
        	var tmpkey = "";
        	for(idx=0; idx<arrRow.length; idx++){ 
        		//alert(arrRow[idx]);
        		tmpkey += grid_sheet.GetCellValue(arrRow[idx], 'sysAreaId') +"|";
    		}

        	//url 호출
    		var urls = '<c:url value="/commons/damgmt/sysarea/delsysarealist.do"/>';
    		var param = "sysAreaId="+tmpkey;
    		ajax2Json(urls, param, ibscallback); */
    		
        	break;
        	
        case "Save" :
           	//TODO 공통으로 처리...
        	
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/commons/damgmt/sysarea/reglist.do"/>";
        	var param = "";
            IBSpostJson2(url, SaveJson, param, ibscallback);

        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/damgmt/sysarea/selectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"시스템 관리"});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
    }       
}
 
//주제영역 팝업 리턴값 처리
function returnUserPop (ret, row) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	grid_sheet.SetCellValue(row, "crgUserId", retjson.userId);
	grid_sheet.SetCellValue(row, "crgUserNm", retjson.userNm);
// 	$("#frmSearch #subjLnm").val(retjson.subjLnm);
// 	$("#frmSearch #fullPath").val(retjson.fullPath);
	
}

function grid_sheet_OnPopupClick(Row,Col) {
	
	//Format이 날짜인 경우 달력 팝업을 오픈한다.
// 	grid_sheet.ShowCalendar();
	var param = "row=" +Row;
	//사용자 검색 팝업 오픈
	if ("crgUserNm" == grid_sheet.ColSaveName(Col)) {
		var url = '<c:url value="/commons/damgmt/sysarea/popup/userlst_pop.do" />';
		openLayerPop(url, 700, 500, param);
	}
}

 
//IBS 그리드 리스트 저장(삭제) 처리 후 콜백함수...
function ibscallback(res){
    var result = res.RESULT.CODE;
    if(result == 0) {
		//공통메세지 팝업 : 성공 메세지... (저장 또는 삭제)
    	showMsgBox("INF", res.RESULT.MESSAGE); //서버에서 정의한 메세지 출력시 사용...
    
    	postProcessIBS(res); 
    	
    } else {
		//공통메시지 팝업 : 실패 메세지... (저장 또는 삭제)
    	showMsgBox("ERR", res.RESULT.MESSAGE); //서버에서 정의한 메세지 출력시 사용...
    }
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				doAction("Search");
				//doActionCol("Search");
		
			break;
		//단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
// 			alert (res.resultVO.progrmFileNm);
			//전체 내용을 다시 조회 할 경우 사용...
			//doAction("Search"); return;  
			
			//단건 저장 완료시 해당 저장된 행만 그리드에서 다시 불러온다. 신규일 경우 하단에 Row를 하나 추가한 후 내용을 불러온다...
			/* if(!isBlankStr(res.resultVO.progrmFileNm)) {
				
				var crow = grid_sheet.GetSelectRow();
				if($('#frmInput #saction').val() == "I") {
		        	crow = grid_sheet.DataInsert(-1);
				}
		        grid_sheet.SetCellValue(crow, "ibsStatus", "");
				var param = "progrmFileNm="+res.resultVO.progrmFileNm;
				grid_sheet.DoRowSearch(crow, "<c:url value="/commons/sys/program/selectProgramRow.do" />",  param ,0);
				
			} */
	    	
			//저장완료시 요청서 번호 셋팅...
	    	/* if(!isBlankStr(res.ETC.rqstNo)) {
	    		//alert(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
	    		var crow = grid_sheet.GetSelectRow();
	    		grid_sheet.SetCellValue(crow, "ibsStatus", "");
				doAction("SearchRow");    		
	    	} */
			
			break;
		//여러건 등록 후처리...
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
    
    if(row < 1) return;
   
}

function grid_sheet_OnSearchEnd(code, message) {

	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		
		for(var i = 1; i <= grid_sheet.RowCount(); i++) {
			
			grid_sheet.SetCellEditable(i,"sysAreaLnm",false); 			
		}
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


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	<div class="menu_title"><s:message code="SYS.MNG" /></div> <!-- 시스템 관리 -->
	<%-- 	<div class="tab_over_s"><img src='<c:url value="/images/tab_over_s.gif" />' alt="" /></div> --%>
<!-- 	    <div class="tab_over_c">주제영역조회</div> -->
	    <div class="stit"><s:message code="SYS.TRRT.MNG" /></div> <!-- 시스템영역 관리 -->
	<%--     <div class="tab_over_x"><img src='<c:url value="/images/tab_over_x.gif" />' alt="close" /></div> --%>
	<%--     <div class="tab_normal_s"><img src='<c:url value="/images/tab_normal_s.gif" />' alt="" /></div> --%>
	<!--     <div class="tab_normal_c">표준단어조회</div> -->
	<%--     <div class="tab_normal_x"><img src='<c:url value="/images/tab_normal_x.gif" />' alt="close" /></div> --%>
	<%--     <div class="tab_normal_s"><img src='<c:url value="/images/tab_normal_s.gif" />' alt="" /></div> --%>
	<!--     <div class="tab_normal_c">표준단어조회표준단어</div> -->
	<%--     <div class="tab_normal_x"><img src='<c:url value="/images/tab_normal_x.gif" />' alt="close" /></div> --%>
	<%--     <div class="tab_normal_s"><img src='<c:url value="/images/tab_normal_s.gif" />' alt="" /></div> --%>
	<!--     <div class="tab_normal_c">표준단어조회</div> -->
	<%--     <div class="tab_normal_x"><img src='<c:url value="/images/tab_normal_x.gif" />' alt="close" /></div> --%>
	</div>
<!-- 	<div class="tab_navi"> -->
<%-- 		<a href="#"><img src='<c:url value="/images/tab_move_left.gif"/>'  alt="이전"  /></a> --%>
<%-- 	    <a href="#"><img src='<c:url value="/images/tab_move_right.gif"/>' alt="다음"  /></a> --%>
<!-- 	</div> -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INQ' />"> <!-- 사용자조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:35%;" />
                   <col style="width:65%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="sysAreaLnm"><s:message code="SYS.NM" /></label></th> <!-- 시스템명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="sysAreaLnm" id="sysAreaLnm" class="wd400p"/>
                                </span>
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
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01" >
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- 	<div class="selected_title_area"> -->
<%-- 		    <div class="selected_title">시스템명 : <span></span></div> --%>
<!-- 	</div> -->
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->


<%-- <%= application.getRealPath("/") %> --%>

</body>
</html>