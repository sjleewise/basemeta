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
<title><s:message code="USER.MNG" /></title> <!-- 사용자관리 -->

<script type="text/javascript">


//엔터키 이벤트 처리
EnterkeyProcess("Search");

var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

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
//             	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
        	}
        //	doAction("Delete");  
        });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
         $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
        
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
                    {Text:"<s:message code='META.HEADER.DATAFLOW.RQST'/>", Align:"Center"}
                    /* No.|상태|선택|데이터흐름ID|소스DB물리명|소스DB스키마물리명|소스테이블명|소스컬럼명|타겟DB물리명|타겟DB스키마물리명|타겟테이블명|타겟컬럼명|SRC_TBL_ID|SRC_COL_ID|TGT_TBL_ID|TGT_COL_ID */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"dfwColId",    Align:"Left", Edit:1, Hidden:0, KeyField:0},
                    {Type:"Text",   Width:130,  SaveName:"srcDbPnm",    Align:"Left", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",   Width:130,  SaveName:"srcSchPnm",    Align:"Left", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",   Width:130,  SaveName:"srcTblPnm",   Align:"Left", Edit:1, Hidden:0, KeyField:1}, 
                    {Type:"Text",   Width:130,  SaveName:"srcColPnm",   Align:"Left", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",   Width:130,  SaveName:"tgtDbPnm",    Align:"Left", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",   Width:130,  SaveName:"tgtSchPnm",    Align:"Left", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",   Width:130,  SaveName:"tgtTblPnm",   Align:"Left", Edit:1, Hidden:0, KeyField:1}, 
                    {Type:"Text",   Width:130,  SaveName:"tgtColPnm",   Align:"Left", Edit:1, Hidden:0, KeyField:1}
                    
//                     {Type:"Text",   Width:130,  SaveName:"srcTblId",   Align:"Left", Edit:0, Hidden:0, KeyField:0},
//                     {Type:"Text",   Width:130,  SaveName:"srcColId",   Align:"Left", Edit:0, Hidden:0, KeyField:0},
//                     {Type:"Text",   Width:130,  SaveName:"tgtTblId",   Align:"Left", Edit:0, Hidden:0, KeyField:0},
//                     {Type:"Text",   Width:130,  SaveName:"tgtColId",   Align:"Left", Edit:0, Hidden:0, KeyField:0},
//                    {Type:"Popup",   Width:130,  SaveName:"deptId",    Align:"Left", Edit:0, Hidden:1},
//                    {Type:"Popup",   Width:130,  SaveName:"deptNm",    Align:"Left", Edit:1},          
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	//SetColProperty("usergId", ${codeMap.usergp});
//         SetColProperty("exclDwldAuthYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        //SetColProperty("regTypCd", ${codeMap.regTypCdibs});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      
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
        
            //var url = "<c:url value="/cmvw/user/user_lst.do" />";
        
            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
        case "Delete" :
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	var url = '<c:url value="/meta/effect/dataflowDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = "<c:url value="/meta/effect/dataflowReglist.do"/>";
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/meta/effect/dataflowSelectlist.do" />', param);
        	
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
    
    $("#hdnRow").val(row);
    
    
}

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret, row) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	grid_sheet.SetCellValue(row, "deptId", retjson.deptId);
	grid_sheet.SetCellValue(row, "deptNm", retjson.deptNm);
// 	$("#frmSearch #subjLnm").val(retjson.subjLnm);
// 	$("#frmSearch #fullPath").val(retjson.fullPath);
	
}

function grid_sheet_OnPopupClick(Row,Col) {
	
	//Format이 날짜인 경우 달력 팝업을 오픈한다.
// 	grid_sheet.ShowCalendar();
	var param = "row=" +Row;
	//사용자 검색 팝업 오픈
	if ("deptNm" == grid_sheet.ColSaveName(Col)) {
		var url = '<c:url value="/commons/user/popup/deptlst_pop.do" />';
		openLayerPop(url, 700, 500, param);
	}
}



function grid_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
        alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="USER.REG" /></div> <!-- 사용자 등록 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INQ' />"> <!-- 사용자조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                          
                            <th scope="row"><label for="tblPnm"><s:message code="TBL.NM" /></div> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="tblPnm" id="tblPnm" />
                                </span>
                           </td>  

                           <th scope="row"><label for="colPnm"><s:message code="CLMN.NM" /></label></th> <!-- 컬럼명 -->
                           <td>
                                <span class="input_file">
                                <input type="text" name="colPnm" id="colPnm" />
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
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" /> 
</div>        

	<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>