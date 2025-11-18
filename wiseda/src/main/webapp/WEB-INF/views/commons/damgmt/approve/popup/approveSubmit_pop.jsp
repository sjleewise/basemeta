<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<html>
<head>
<title><s:message code="REG.DEMD.APMA.CHC" /></title> <!-- 등록요청/결재자 선택 -->

<script type="text/javascript">

var interval = "";
// var usergJson = ${codeMap.userglist} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		// 등록요청 Event Bind
		$("#btnRegRqst").click(function(){
			
			doAction("Submit");
			
		}).show();	
		
		//버튼 초기화...
		$("#btnTreeNew, #btnSave, #btnDelete").hide();
                    
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnNew").click(function(){ doAction("New");  }); 

        // 저장 Event Bind
         $("#btnSave").click(function(){ doAction("Save");  }); 

        // 삭제 Event Bind
         $("#btnDelete").click(function(){ doAction("Delete");  }); 
        
         // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } );  
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
        
    	$('div.pop_tit_close').click(function(){
    		//alert('test');
    		//$('div#popSearch', parent.document).width(); //.dialog("close");
    		parent.closeLayerPop();
    		
    		
    	});
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
                    {Text:"<s:message code='COMMON.HEADER.APPROVESUBMIT.POP'/>", Align:"Center"} 
                    /* No.|상태|선택|결재레벨|결재자|결재그룹|결재상태|결재일|결재방법 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                      {Type:"Seq",      Width:70,  SaveName:"ibsSeq",    Align:"Center", Edit:0},
                   	  {Type:"Status",   Width:30,  SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
                   	  {Type:"CheckBox", Width:40,  SaveName:"ibsCheck",  Align:"Center", Edit:1, Sort:0 },              
                   	  {Type:"Int",     Width:60,  SaveName:"aprLvl",    Align:"Center", Edit:0, }, 
                   	  {Type:"Combo",    Width:60,  SaveName:"userId",    Align:"Left",   Edit:1, KeyField:1 }, 
//                       {Type:"Text",     Width:100, SaveName:"userNm",    Align:"Left",   Edit:0,  }, 
                      {Type:"Combo",    Width:100, SaveName:"aprgId",    Align:"Left",   Edit:0, },
                      {Type:"Combo",    Width:60,  SaveName:"rvwStsCd",  Align:"Center", Edit:0,  }, 
                      {Type:"Text",     Width:60,  SaveName:"aprvDtm",    Align:"Center", Edit:0,  Format:"yyyy-MM-dd"}, 
                      {Type:"Combo",     Width:60,  SaveName:"aprFrmlCd", Align:"Center", Edit:0,  }, 
                 //   {Type:"Text",   Width:50,  SaveName:"objDescn",     Align:"Left", 	 Edit:1},
                 //   {Type:"Text",   Width:50,  SaveName:"objVers",      Align:"Left",   Edit:0, Hidden:0},
                 //   {Type:"Combo",  Width:50,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"신규|변경|삭제",},                        
                 //   {Type:"Date",   Width:60,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                 //   {Type:"Text",   Width:60,  SaveName:"writUserId",   Align:"Center", Edit:0, Hidden:0},
                 //   {Type:"Text",   Width:60,  SaveName:"writUserNm",   Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
// 	   	SetColProperty("usergId", ${codeMap.usergp});
        SetColProperty("aprgId"		, ${codeMap.approvegroupibs});
        SetColProperty("rvwStsCd"	, ${codeMap.rvwStsCdibs});
        SetColProperty("aprFrmlCd"	, ${codeMap.aprFrmlCdibs});
        
        InitComboNoMatchText(1, "<s:message code='CHC' />"); /* 선택 */

        SetColHidden("ibsSeq",1);
//         SetColHidden("ibsStatus",1);
        SetColHidden("ibsCheck",1);
        SetColHidden("userNm",1);
        //SetColHidden("arrUsrId",1);
      
        FitColWidth();  
//         SetComboOpenMode(1);
        
        //SetExtendLastCol(1);    
        //grid_sheet.DoSearch('<c:url value="/commons/user/getuserlistbydept.do" />');
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
    doAction("Search");
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
        	//체크박스 확인...
        	if(!grid_sheet.CheckedRows("ibsCheck")) {
        		showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
        	}
        	
        	//TODO : 입력상태인 경우 삭제하자...
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	var url = "<c:url value="/cmvw/user/userDellist.do"/>";
        	$.postJSON(url, DelJson, ibscallback);
        	break;
        	
        case "Submit" : //등록요청 처리한다. 처리후에는 부모창을 리프레시 해야함...
//         	var SaveJson = grid_sub.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	var SaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	var url = "<c:url value="/commons/damgmt/approve/regapproveprocess.do"/>";
        	$('#frmSearch #rqstResn').val($("#search_div2 #rqstResn").val());
        	var param = $('#frmSearch').serialize(); //요청서 마스터 보내기...
        	IBSpostJson2(url, SaveJson, param, ibscallback);
        	break; 
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);
        	grid_sheet.DoSearch('<c:url value="/commons/damgmt/approve/getapproveline.do" />', param);
        	
        	break;
       
         case "Down2Excel":  //엑셀내려받기
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
/*        case "LoadExcel":  //엑셀업로
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});         
            break; */
    }       
}
 

/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				//doAction("Search");
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :
			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			//저장완료시 마스터 정보 셋팅...
			
	    	 if(!isBlankStr(res.resultVO.rqstNo)) {
//	    		alert(res.resultVO.rqstNo);
	    		//json2formmapping ($("#mstFrm"), res.resultVO);
//	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
	    		
//	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				//doAction("Search");    		
	    	} 
			
			break;
		case "<%=WiseMetaConfig.RqstAction.SUBMIT%>":
// 			alert("등록요청 완료");
			//부모창을 리로드 한다....
			//TODO 화면 재조회 (요청 업무에 따라 가져온다.....) getRequestUrl(...업무구분코드...)
			var param = $('#frmSearch').serialize();
			var url = "<c:url value="${bizinfo.url}"/>";
// 			var url = "<c:url value="/meta/stnd/stndword_rqst.do"/>";
			parent.location.href = url+"?"+param;
			
		break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
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
		//결제자 id에 콤보코드를 셋팅한다.
		
		var aprvjsonlist = ${aprvcodelist} ;
		
		var cnt  = aprvjsonlist.length;
// 		grid_sheet.CellComboItem(1, "userId", {ComboText:"메타관리자|배성재|이승은|이재왕|장유정",ComboCode:"meta|090028|001972|100677|001514"});
		
		for(i=0; i<cnt; i++) {
// 			var info = aprvjsonlist[i];
			//결재자 리스트 셋팅...
			grid_sheet.CellComboItem(i+1, "userId", aprvjsonlist[i]);
			
			//그룹 결재인 경우는 그룹명 셋팅...
			var aprgid = grid_sheet.GetCellValue(i+1, "aprgId");
			
			if (grid_sheet.GetCellValue(i+1, "aprFrmlCd") == 'G') {
				grid_sheet.CellComboItem(i+1, "userId", ${codeMap.approvegroupibs});
				grid_sheet.SetCellValue(i+1, "userId", grid_sheet.GetCellValue(i+1, "aprgId"));
				grid_sheet.SetCellEditable(i+1, "userId", 0);
				
			} 
			
		}
		
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
 	
//  	var rowJson = grid_sheet.GetRowJson(row);
//  	parent.returnUserInfoPop(JSON.stringify(rowJson));
//  	$('div.pop_tit_close').click();
 	
	
// 	var url = "<c:url value="/cmvw/user/cmvwuser_rqst.do" />";
 
// 	$("#saveCls").val("U");  //저장구분을 수정 (U) 로 변경 
	
// 	var usrId = grid_sheet.GetCellValue(row, "arrUsrId");
	
// 	$("#usrId").val(usrId);  
	   
//     $("#frmInput").attr("action", url).submit(); 
    
//     //팝업 닫기 버튼 클릭...
//     $('div.pop_tit_close').click();
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
    
//     $("#sltGb").val(    grid_sheet.GetCellValue(row,"arrRqstDcd"));
//     $("#stwLnm").val(   grid_sheet.GetCellValue(row,"arrStwLnm"));
//     $("#stwPnm").val(   grid_sheet.GetCellValue(row,"arrStwPnm"));
//     $("#stwEfn").val(   grid_sheet.GetCellValue(row,"arrStwEfn"));
//     $("#cchNm").val(    grid_sheet.GetCellValue(row,"arrCchNm"));
//     $("#objDescn").val( grid_sheet.GetCellValue(row,"arrObjDescn"));
    
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

<div class="pop_tit" > <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="REG.DEMD.APMA.CHC" /></div> <!-- 등록요청/결재자 선택 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->

<div class="pop_content">

<!-- 메뉴 메인 제목 -->
<%-- <div style="clear:both; height:5px;"><span></span></div> --%>

<!-- 검색조건 입력폼 -->
        <form id="frmSearch" name="frmSearch" method="post">
        	<input type="hidden" name="rqstNo" id="rqstNo" value="${reqmst.rqstNo }">
			<input type="hidden" id="rqstNm" 		name="rqstNm" 		value="${reqmst.rqstNm}">
			<input type="hidden" id="bizDcd" 		name="bizDcd" 		value="${reqmst.bizDcd}">
			<input type="hidden" id="bizDtlCd" 	name="bizDtlCd" 	value="${reqmst.bizDtlCd}">
			<input type="hidden" id="rqstStepCd" 	name="rqstStepCd"	value="${reqmst.rqstStepCd}">
			<input type="hidden" id="rqstResn" 	name="rqstResn" 	value="${reqmst.rqstResn}">
        </form>
<div id="search_div" style="display: none;"> 
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic">
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

                         <th scope="row"><label for="userNm"><s:message code="USER.NM" /></label></th> <!-- 사용자명 -->
                             <td>
<%--                              	<select id="usergId" class="usergId" name="usergId"> --%>
<!--                                       <option selected="" value="">-<s:message code="WHL" /></option> <! 전체 --> 
<%--                                       <c:forEach var="code" items="${codeMap.userglist }" varStatus="status" > --%>
<%--                                       <option value="${code.codeCd }">${code.codeLnm}</option> --%>
<%--                                       </c:forEach> --%>
<%--                                   </select> --%>
                            </td>   
                   </tbody>
                 </table>   
            
            </fieldset>
<%--            </form>          --%>
<%--         <div class="tb_comment"><s:message  code='ETC.POP' /></div> --%>
         <!-- 조회버튼영역  -->
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" /> --%>

</div>

        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst"><s:message code="REG.DEMD" /></button> <!-- 등록요청 --> 
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	
        
		<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "150px");</script>            
	</div>
	<div style="clear:both; height:5px;"><span></span></div>
	<div id="search_div2"> 
       <fieldset>
       <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='USER.INQ' />"> <!-- 사용자조회 -->
              <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
              <colgroup>
              <col style="width:15%;" />
              <col style="width:35%;" />
             </colgroup>
              
              <tbody>                          
                	<th scope="row"><label for="demDrsn"><s:message code="DEMD.RSN" /></label></th> <!-- 요청사유 -->
                       <td>
                          	<textarea id="rqstResn" name="rqstResn" class="wd98p" rows="4"></textarea>
                       </td>  
              </tbody>
            </table>   
       </fieldset>
	</div>
	<!-- 그리드 입력 입력 -->
	<div class="tb_comment"><s:message  code='REQ.SUBMIT.COMM' /></div>
</div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>