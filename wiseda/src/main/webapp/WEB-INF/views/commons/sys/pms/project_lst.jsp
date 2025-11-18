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
<title><s:message code="PRJT.MNG" /></title> <!-- 프로젝트 관리 -->

<script type="text/javascript">

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
       
        	
        });
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
     // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 

        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
        //create_selectbox(usergJson, $("#usergId"));
        //create_selectbox(datatpJson, $("#dataType"));
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
                    {Text:"<s:message code='COMMON.HEADER.PROJECT.LST'/>", Align:"Center"}
                    /* No.|상태|선택|프로젝트ID|프로젝트명|PM명|PM전화번호|설명|시작일자|종료일자|사용여부|최초등록요청자|등록요청일자|승인자ID|승인자명|승인일자|등록유형 */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:130,  SaveName:"prjId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"prjNm",    Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"pmNm",    Align:"Left", Edit:1, Hidden:0},
                    {Type:"Text",   Width:110,  SaveName:"pmTel",    Align:"Center", Edit:1},
                    {Type:"Text",   Width:330,  SaveName:"description",    Align:"Left", Edit:1},
                    {Type:"Date",   Width:130,  SaveName:"staDttm",    Align:"Center", Edit:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",   Width:120,  SaveName:"endDttm",     Align:"Center", Edit:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Combo",   Width:100,  SaveName:"useYn",      Align:"Center", Edit:1, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"orglUser",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",   Width:100,  SaveName:"orglDttm",      Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:100,  SaveName:"updtUser",      Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"updtUserNm",      Align:"Left", Edit:0, Hidden:0},
                    {Type:"Date",   Width:150,  SaveName:"aprvDttm",      Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Combo",   Width:100,  SaveName:"orglType",      Align:"Center", Edit:0, Hidden:0}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
      	SetColProperty("orglType", ${codeMap.regTypCdibs});
        SetColProperty("useYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
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
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/commons/sys/pms/ajaxgrid/deleteProgram.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
           	
        	ibsSaveJson = grid_sheet.GetSaveJson(0);
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/commons/sys/pms/ajaxgrid/saveProject.do"/>";
        	var param = "";
            IBSpostJson(url, param, ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/sys/pms/ajaxgrid/selectProjectList.do" />', param);
        	
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
    

}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
//     $("#hdnRow").val(row);
    
    if(row < 1) return;
    

    
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
	    <div class="menu_title"><s:message code="PRJT.MNG" /></div> <!-- 프로젝트 관리 -->
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
                          <tr>
					      	<th><s:message code="PRJT.NM" /></th> <!-- 프로젝트명 -->
					      	<td><input type="text" name="prjNm" id="prjNm"/></td>
					      	<th><s:message code="PM.NM" /></th> <!-- PM명 -->
					      	<td><input type="text" name="pmNm" id="pmNm"/></td>
					      </tr>

                   </tbody> 
                 </table>   
            </div>
            </fieldset> 
        </form>
        
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
		 <div style="clear:both; height:5px;"><span></span></div> 

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px")</script>            

	</div>
	<!-- 그리드 입력 입력 -->
</div>
<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>