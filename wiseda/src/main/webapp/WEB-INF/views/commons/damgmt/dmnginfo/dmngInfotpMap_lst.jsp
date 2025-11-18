<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="DMN.GRP.INFO.TY.MAPG" /></title> <!-- 도메인그룹 인포타입 매핑 -->

<script type="text/javascript">

var interval = "";
var dmngJson = ${codeMap.dmng} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ 
        	doAction("New");  
        	
        });

        // 저장 Event Bind
        $("#btnSave").click(function(){ 
        	//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//도메인그룹명 인포타입 중복체크
        	//중복 체크한다. 도메인그룹+인포타입 으로...
        	var duprows= grid_sheet.ColValueDupRows("dmngId|infotpId");
//         	alert(duprows);
        	if(!isBlankStr(duprows)) {
        		showMsgBox("ERR", duprows+"<s:message code="ERR.DUP.INFOMAP"  />");
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

        var bscLvl = parseInt("${bscLvl}");
        var selectBoxId = "${selectBoxId}";
        var firstSelectBoxId = selectBoxId.split("|");
        
        //selectBox갯수가 1개일 경우 double_select가 먹지 않으므로 분기하여 처리...
        if(firstSelectBoxId.length == 1) {
        	create_selectbox2($("#selectBoxDiv"), bscLvl, selectBoxId, '<s:message code="CHC" />'); /* 선택 */
        	create_selectbox(dmngJson, $("#"+firstSelectBoxId[0]));
        } else {
          create_selectbox2($("#selectBoxDiv"), bscLvl, selectBoxId, '<s:message code="CHC" />'); /* 선택 */
       	  //표준분류 적용(이베이코리아)
       	  $("#stndAsrt").change(function() {
        	   //divID,  selectbox건수, selectbox ID
//                double_select(dmngJson, $("#"+firstSelectBoxId[0]));
        	   $("#"+firstSelectBoxId[0] +" option:eq(0)").attr("selected", "selected");
               double_selectStndAsrt(dmngJson, $("#"+firstSelectBoxId[0]),$("#stndAsrt option:selected").val());
               
        	   $('select', $("#"+firstSelectBoxId[0]).parent()).change(function(){
        	    	double_select(dmngJson, $(this));
        	   });
       	  });
        }
        
    }
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	
});


$(window).resize(function(){
	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
	setibsheight($("#grid_01"));        
    	// grid_sheet.SetExtendLastCol(1);    
    
});

EnterkeyProcess("Search");

function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='COMMON.HEADER.DMNGINFOMAP.LST'/>", Align:"Center"}
                    /* No.|상태|선택|도메인그룹명|인포타입명|만료일시|시작일시|데이터타입|데이터길이|데이터소수점길이|버전|설명|작성시각|작성사용자ID */
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:60,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:60,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:80,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},    
//                     {Type:"Combo",   Width:130,  SaveName:"stndAsrt",    Align:"Left", Edit:1, KeyField:1,UpdateEdit:0},
                    {Type:"Combo",   Width:200,  SaveName:"dmngId",    Align:"Left", Edit:1, KeyField:1,UpdateEdit:0}, 
                    {Type:"Combo",   Width:200,  SaveName:"infotpId",    Align:"Left", Edit:1, KeyField:1,UpdateEdit:0},
                    {Type:"Date",   Width:130,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Date",   Width:130,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:130,  SaveName:"dataType",    Align:"Left", Edit:0, KeyField:0},          
                    {Type:"Int",   Width:130,  SaveName:"dataLen",    Align:"Center", Edit:0, Format:"NullInteger" , AcceptKeys:"N"},
                    {Type:"Int",   Width:120,  SaveName:"dataScal",     Align:"Center", Edit:0, Format:"NullInteger" ,AcceptKeys:"N"},
                    {Type:"Text",   Width:130,  SaveName:"objVers",      Align:"Center",   Edit:0, Hidden:1},
                    {Type:"Text",   Width:130,  SaveName:"objDescn",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Date",   Width:130,  SaveName:"writDtm",      Align:"Left",   Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:130,  SaveName:"writUserId",      Align:"Left",   Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);
	     
//         SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});
		console.log(${codeMap.dmngibs});
        SetColProperty("dmngId", 	${codeMap.dmngibs});
        SetColProperty("infotpId", 	${codeMap.infotpibs});
        //콤보 목록 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      	InitComboNoMatchText(1,"");
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
        
    	case "New":        //추가
        	//첫행에 추가...
        	grid_sheet.DataInsert(0);
        	//마지막 행에 추가..
        	//grid_sheet.DataInsert(-1);
        
            //var url = "<c:url value="/cmvw/user/user_lst.do" />";
        
            //$("#frmInput").attr("action", url).submit();
                        
            break;
            
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(grid_sheet);
        	
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/commons/damgmt/dmnginfo/dmngInfotpMapDellist.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        	
        	
        	
        case "Save" :
           
        	var saveJson = grid_sheet.GetSaveJson(0);
//         	ibsSaveJson = grid_sheet.GetSaveJson(0);
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);
        	//데이터 사이즈 확인...
        	if(saveJson.data.length == 0) return;
        	
        	//중복 확인...
//         	var dupCheck = grid_sheet.ColValueDup("dmngId|infotpId", 0);
//         	if(dupCheck != -1) {
//         		showMsgBox("ERR", "<s:message code="ERR.DUP" />");
//         		return;
//         	}
        	
            var url = "<c:url value="/commons/damgmt/dmnginfo/dmngInfotpMapReglist.do"/>";
//         	var param = "commDcdNm=test";
            IBSpostJson2(url, saveJson, "", ibscallback);
        	break;
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/damgmt/dmnginfo/dmngInfotpMapSelectlist.do" />', param);
        	
        	break;
       
        case "Down2Excel":  //엑셀내려받기
        
          
        //보여지는 컬럼들만 엑셀 다운로드          
        var downColNms = "";
       	for(var i=0; i<=grid_sheet.LastCol();i++ ){
       		if(grid_sheet.GetColHidden(i) != 1){
       			downColNms += grid_sheet.ColSaveName(0,i)+ "|";
       		}
       	}


            grid_sheet.Down2Excel({HiddenColumn:1,  DownCols:downColNms, Merge:1, FileName:"도메인그룹-인포타입 매핑관리"});
            
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
	    <div class="menu_title"><s:message code="DMN.GRP.INFO.TY.MAPG" /></div> <!-- 도메인그룹 인포타입 매핑 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <!--<div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 --> 
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INQ' />"> <!-- 사용자조회 -->
                   <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                    <tbody>                       
                        <tr>
                         <th scope="row" class="th_require"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!--  -->
					         <td colspan="3">
					         <select  id="stndAsrt" name="stndAsrt"  class="wd300">
					                 <option value=""><s:message code="WHL"/></option>
					                 <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
					         		  <option value="${code.codeCd}">${code.codeLnm}</option>
					         		</c:forEach>
					         </select>
					         </td>
                        </tr>
                        <tr>  
                          <th scope="row"><label for="dmngId"><s:message code="DMN.GRP.LGC.NM" /></label></th> <!-- 도메인그룹논리명 -->
                           <td>
                               <div id="selectBoxDiv"> <span></span></div>
                            </td>  
                          <th scope="row"><label for="infotpLnm"><s:message code="INFO.TYPE.LGC.NM" /></label></th> <!-- 인포타입논리명 -->
                           <td>
                           <input type="text"  id="infotpLnm" name="infotpLnm"  />
                           </td>
                        </tr>  
                   </tbody> 
                 </table>   
            </div>
            </fieldset>
            
            <input type="hidden" name="saveCls" id="saveCls"  />   
            <input type="hidden" name="usrId"   id="usrId" />   
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
</div>   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            

	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

</body>
</html>