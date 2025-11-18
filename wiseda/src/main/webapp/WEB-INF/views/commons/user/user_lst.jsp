<!DOCTYPE html>
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

var pwRow;
var pwCol;

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
         $("#btnExcelLoad").click( function(){ 
        	 var message = "기존 시트에 있는 데이터가 지워지고 업로드 됩니다. <br> 업로드 하시겠습니까?";
     		showMsgBox("CNF", message, 'LoadExcel');
     		//doAction('LoadExcel');
        } ); 
       
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
                
    	setibsheight($("#grid_01"));
    }
);


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.USER.LST.GS'/>", Align:"Center"}
                    /* No.|업무대행|상태|선택|사용자ID|만료일시|시작일시|로그인ID|사용자명|사용자그룹명|부서ID|부서명|직급명|사용자전화번호|사용자휴대폰번호|이메일주소|엑셀다운로드권한여부|ID사용만료일자|비밀번호만료일자|설명|버전|등록유형|요청일시|요청사용자ID|승인구분코드|작성일시|승인사용자ID|비밀번호변경 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",      Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"gologin",  Image:containerPath+"/images/login_go.gif" ,ImgAlign:"Left", Align:"Center", Edit:0, Hidden:1},
                    {Type:"Status",   Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:130,  SaveName:"userId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Date",     Width:130,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",     Width:130,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",     Width:130,  SaveName:"loginAcId",    Align:"Left", Edit:1, Hidden:0, KeyField:1},
                    {Type:"Text",     Width:130,  SaveName:"userNm",    Align:"Left", Edit:1, KeyField:1}, 
                    {Type:"Combo",    Width:130,  SaveName:"usergId",    Align:"Left", Edit:1, KeyField:1},
                    {Type:"Popup",    Width:130,  SaveName:"deptId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Popup",    Width:130,  SaveName:"deptNm",    Align:"Left", Edit:1},          
                    {Type:"Text",     Width:120,  SaveName:"jgdNm",     Align:"Left", 	 Edit:1},
                    {Type:"Text",     Width:130,  SaveName:"userTelno",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:130,  SaveName:"userHtelno",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:130,  SaveName:"emailAddr",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Combo",    Width:130,  SaveName:"exclDwldAuthYn",      Align:"Center",   Edit:1, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"idUseExpDt",      Align:"Center",   Edit:1, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"pwdExpDt",      Align:"Center",   Edit:1, Hidden:1},
                    {Type:"Text",     Width:230,  SaveName:"objDescn",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",     Width:130,  SaveName:"objVers",      Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:80,   SaveName:"regTypCd",      Align:"Center",   Edit:0, Hidden:0},
                    {Type:"Text",     Width:130,  SaveName:"rqstDtm",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"rqstUserId",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Combo",    Width:130,  SaveName:"aprvDcd",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"aprvDtm",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:130,  SaveName:"aprvUserId",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",     Width:50,  SaveName:"isLock",      Align:"Center",   Edit:0, Hidden:0},
                    {Type:"html",     Width:100,  SaveName:"pwChg",  Align:"Center", Edit:0, KeyField:0, Hidden:1},
                    {Type:"Text",     Width:100,  SaveName:"pwInit",  Align:"Center", Edit:0, KeyField:0, Hidden:0, DefaultValue:"비밀번호초기화"}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("usergId", ${codeMap.usergp});
        SetColProperty("exclDwldAuthYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("regTypCd", ${codeMap.regTypCdibs});
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
        	
        	if(DelJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/user/userDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        case "Save" :
        	//TODO 공통으로 처리...
        	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
//         	ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
			
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = "<c:url value="/commons/user/userReglist.do"/>";
         	var param = "";
         	
            IBSpostJson2(url, SaveJson, param, ibscallback);
            
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/user/userSelectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:"사용자 관리"});
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch', Append:1});
            
            break;
        case "PwInit":
        	var url = "<c:url value="/commons/user/userPwInit.do"/>";
         	var param = "userId=" + grid_sheet.GetCellValue(pwRow, 'userId');

         	$.ajax({
					url: url,
					type: "POST",
					data: replacerXssParam(param),
					dataType: 'json',
				beforeSend: function () {
					// 처리중이니 잠시 기다려 주십시요.
					showMsgBox("PRC", gMSG_PRC_WAIT);
					
				},
				complete: function () {
					 $("#divMsgPopup").dialog("close"); 
				},
				success: function() {
					doAction('Search');
				}
			});
        	
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
    
	if ("gologin" == grid_sheet.ColSaveName(col)) {
		var rowjson = grid_sheet.GetRowJson(row);
		var param = "?loginAcId="+rowjson.loginAcId;
		var url = '/wiseda/commons/user/userloginbyadmin.do';
		window.open(url+param, "_blank");
	}
	
	if ("pwChg" == grid_sheet.ColSaveName(col)) {
		var url = '<c:url value="/commons/user/gousrInfoChngPop.do"/>';
		var param = grid_sheet.GetRowJson(row);
		param = param+"&"+"loginAcId="+param.loginAcId;
		
		openLayerPop(url, 700,  300, param);
		//OpenWindow(url+'?'+param,'passwordChange','925','550','yes');
		return false;
	}

	if ("pwInit" == grid_sheet.ColSaveName(col)) {
		var message = "비밀번호 초기화 및 잠금을 해제하시겠습니까?";
		pwRow = row;
		pwCol = col;
		showMsgBox("CNF", message, "PwInit");
	}
    
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
		openLayerPop(url, 800, 500, param);
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	//alert(grid_sheet.GetDataBackColor()+":"+ grid_sheet.GetDataAlternateBackColor());
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		//setGridCellAll(grid_sheet,"pwChg", "<a class='BK_btn_grid2' ><s:message code='PWD.CHG' /></a>" ); /* 비밀번호변경 */
		//setGridCellAll(grid_sheet,"pwInit", "비밀번호초기화");
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
                                              <th scope="row"><label for="userNm"><s:message code="USER.NM" /></label></th> <!-- 사용자명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="userNm" id="userNm" />
                                </span>
                                </td>  

                           <th scope="row"><label for="usergId"><s:message code="USER.GRP.NM" /></label></th> <!-- 사용자그룹명 -->
                                <td><select id="usergId" class="usergId" name="usergId">
                                        <option selected="" value="">---<s:message code="WHL" />----</option> <!-- 전체 -->
                                        <c:forEach var="code" items="${codeMap.userglist }" varStatus="status" >
                                        <option value="${code.codeCd }">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
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
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->


</body>
</html>