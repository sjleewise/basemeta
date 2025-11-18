<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
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
      

        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
    	
        $("#btnDelete").click(function(){
        	
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
        	}
//         	doAction("Delete");
        } );
        $("#btnTreeNew").hide();
        
      //기간 버튼 클릭 체크
     	$(".bd_none a").click(function(){
    		var btna = $(".bd_none a"); 
    		var idx = btna.index(this);
    		btna.removeClass('tb_bt_select').addClass('tb_bt');
    		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

    		//alert(idx);
    		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
    		
    	});
   	 	
      
});

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	var linkFlag = "";
	linkFlag= "${linkFlag}";
	if(linkFlag=="1"){
		doAction("Search");
	}else if(linkFlag=="2"){
		//param 값을 등록요청으로 지정.
		$("#frmSearch #rqstStepCd").val("Q");
		doAction("Search");
	}else if(linkFlag=="3"){
		//param 값을 등록요청으로 지정.
		$("#frmSearch #rqstStepCd").val("S");
		doAction("Search");
	}
	
	//달력팝업 추가...
 	$( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker();
	
	// 3: 1개월 
	setBetweenDtm( 3, $( "#searchBgnDe" ), $( "#searchEndDe" ));
	
	//어드민은 수정가능하게 
	if("${sessionScope.loginVO.isAdminYn}" == "Y"){
	
		$("#rqstUserNm").attr("readonly",false);
		$("#rqstUserNm").val("");
	}
	
	$("#rqstUserNm").attr("readonly",false);
	
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
        
        //headText = "Num|Status|Select|Number|Category|Details category|Name|Date|Date of approval|Requester name|Progress status|Approval result|Progress level of approval";
        //headText = "No.|상태|선택|요청번호|요청구분|요청상세구분|요청명|요청일자|승인일자|요청자|진행상태|결재결과|승인진행레벨";
        
        var headText = "<s:message code='META.HEADER.RQSTMY.LST'/>";
        
        var headers = [
                    {Text: headText, Align:"Center"}
                ];
               
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:0};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",      Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status",   Width:30,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",     Width:150,  SaveName:"rqstNo",      Align:"Center", Edit:0, Hidden:0},
                    {Type:"Combo",    Width:120,  SaveName:"bizDcd",      Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",     Width:120,  SaveName:"bizDtlCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",     Width:300,  SaveName:"rqstNm",      Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Date",     Width:180,  SaveName:"rqstDtm",     Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Date",     Width:100,  SaveName:"aprvDtm",     Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",     Width:80,   SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Combo",    Width:80,   SaveName:"rqstStepCd",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",     Width:80,   SaveName:"aprvStatus",  Align:"Center", Edit:0},
                    {Type:"Text",     Width:100,  SaveName:"aprvStepLvl", Align:"Left", Edit:0}
                   
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    SetColProperty("rqstStepCd", ${codeMap.rqstStepCdibs});
	    SetColProperty("bizDcd", ${codeMap.bizDcdibs});
        //SetColHidden("rqstUserNm",1);

        InitComboNoMatchText(1, "");
//         FitColWidth();  
        
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
        case "Search":
        	
			var param = $("#frmSearch").serialize();

			$('#rqst_sel_title').html('');
			//grid_sheet.DoSearchScript("testJsonlist");
			grid_sheet.DoSearch("<c:url value="/meta/stnd/selectRqstMyList.do" />", param);
        	break;

        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1, FileName:'내요청목록조회'});
            
            break;
            
		case "Delete" :
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	
        	var errRow = deleteErrCheck(DelJson);
        	if (errRow == 0) return;
        	
        	//요청자만 임시저장 삭제 가능 
        	if(!delCheck()) return;
        	
        	var url = '<c:url value="/commons/rqstmst/deleteRqstList.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;


    }       
}
    
function deleteErrCheck(delJson) {
	var usergTypCd = "${usergTypCd}";
	for(var i=0; i<delJson.data.length; i++){
		//등록요청건을 걸러낸다.
		if(delJson.data[i].rqstStepCd == 'Q') {
			var message = "<s:message code="REQ.DEL.REQUEST" />";
    		showMsgBox("ERR", message);
    		return 0;
		}
		//관리자가 아닐경우 결재완료건을 걸러낸다.
		if(delJson.data[i].rqstStepCd == 'A' && usergTypCd != 'AD') {
			
			var message = "<s:message code="REQ.DEL.APPROVE" />";
    		showMsgBox("ERR", message);
    		return 0;
		}
		
		
	}
	//에러가 없을 경우 1을 반환.
	return 1;
	
}


function delCheck(){
	
	for(var i = 1; i <= grid_sheet.RowCount(); i++) {
		 
		 if (grid_sheet.GetCellValue(i,"ibsCheck") == "1" ){
			 
			 if(grid_sheet.GetCellValue(i,"rqstStepCd") == "Q" || grid_sheet.GetCellValue(i,"rqstStepCd") == "A") {
					
				//임시저장상태만 삭제 가능합니다.
				showMsgBox("ERR", "<s:message code='ONLY.DRST.DEL.CAN' />");
				return false;
			 } 	
			 			 
// 			 if("${sessionScope.loginVO.isAdminYn}" != "Y"){
					
				var rqstUserId = grid_sheet.GetCellValue(i,"rqstUserId");
				var rqstUserNm = grid_sheet.GetCellValue(i,"rqstUserNm");
				
				if("${sessionScope.loginVO.uniqId}" != rqstUserId && "${sessionScope.loginVO.isAdminYn}" != "Y" && "${sessionScope.loginVO.name}" != rqstUserNm) {
					
					//요청자만 삭제 가능합니다.
					showMsgBox("ERR", "<s:message code='RQR.ONLY.DEL.CANBE' />");
					return false;  
				}  
// 			}
		 }
	}

	return true;
}
    
function deleteErrCheck(delJson) {
	var usergTypCd = "${usergTypCd}";
	for(var i=0; i<delJson.data.length; i++){
		//등록요청건을 걸러낸다.
		if(delJson.data[i].rqstStepCd == 'Q') {
			var message = "<s:message code="REQ.DEL.REQUEST" />";
    		showMsgBox("ERR", message);
    		return 0;
		}
		//관리자가 아닐경우 결재완료건을 걸러낸다.
		if(delJson.data[i].rqstStepCd == 'A' && usergTypCd != 'AD') {
			
			var message = "<s:message code="REQ.DEL.APPROVE" />";
    		showMsgBox("ERR", message);
    		return 0;
		}
		
		
	}
	//에러가 없을 경우 1을 반환.
	return 1;
	
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
	
	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
	var rqstNo = grid_sheet.GetCellValue(row, "rqstNo");
	var bizDcd = grid_sheet.GetCellValue(row, "bizDcd");
	var bizDtlCd = grid_sheet.GetCellValue(row, "bizDtlCd");
	
	//window.open().location.href = "../../goRqstPage.do?rqstNo="+rqstNo + "&bizDcd=" + bizDcd;	
	window.location.href = "../../goRqstPage.do?rqstNo="+rqstNo + "&bizDcd=" + bizDcd + "&bizDtlCd=" + bizDtlCd;
	 
	
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_sheet.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="DEMD.NM" /> : ' + param.rqstNm; /*요청명*/
	$('#rqst_sel_title').html(tmphtml);
	


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
}

</script>
</head>

<body>


<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="MY.DEMD.LST.INQ" /></div> <!-- 내 요청목록 조회 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.WORD.INQ' />"> <!-- 표준단어조회 -->
                   <caption><s:message code="STRD.WORD.INQ.FORM" /></caption> <!-- 표준단어 검색폼 -->
                   
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:23%;" />
                   <col style="width:10%;" />
                   <col style="width:23%;" />
                   <col style="width:10%;" />
                   <col style="width:*;" />
                   </colgroup>
                   
                   <tbody>    
                        <tr>
                             <th scope="row"><label for="bizDcd"><s:message code="BZWR.DSTC" /></label></th> <!-- 업무구분 -->
                             <td>
                             	<select id="bizDcd" class="wd300" name="bizDcd">
                                    <option selected="" value=""><s:message code="WHL" /></option> <!-- 전체 -->
		                            <c:forEach var="code" items="${codeMap.bizDcd}" varStatus="status">
				                    	<option value="${code.codeCd}">${code.codeLnm}</option>
				                    </c:forEach>
	                             </select>
                             </td> 
                             
                             <th scope="row"><label for="rqstNm"><s:message code="DEMD.NM" /></label></th>  <!-- 요청명 -->
                             <td><input type="text" id="rqstNm" class="wd300" name="rqstNm"/></td>
                             
                             <th scope="row"><label for="rqstStepCd"><s:message code="DEMD.STEP" /></label></th>  <!-- 요청단계 -->
                             <td>
                                 <select id="rqstStepCd" name="rqstStepCd" class="wd150" >
                                   <option selected="" value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                   <c:forEach var="code" items="${codeMap.rqstStepCd}" varStatus="status">
				                       <option value="${code.codeCd}">${code.codeLnm}</option>
				                   </c:forEach> 		                
                                 </select>
                             </td>
                         </tr>
                         
                         <tr>                             
                             <th scope="row"><label for="aprvUserNm"><s:message code="APMA" /></label></th>   <!-- 결재자 -->
                             <td>
                                 <input id="aprvUserNm" name="aprvUserNm" type="text" class="wd120" value="" />
                                                                                      
                             <th scope="row"><label for="rqstUserNm"><s:message code="DMNT" /></label></th>   <!-- 요청자 -->
                             <td>
                                 <input id="rqstUserNm" name="rqstUserNm" type="text" class="wd120" value="${sessionScope.loginVO.name}"  />
                             </td>
                             
                             <th scope="row"><label for="rqstNo"><s:message code="DEMD.NO" /></label></th>   <!-- 요청번호 -->
                             <td>
                                 <input id="rqstNo" name="rqstNo" type="text" class="wd200"  />
                             </td>
                         </tr>
                         
                         <tr>
                             <th scope="row"><label for="aprvStatus"><s:message code="APRL.RSLT" /></label></th>  <!-- 결재결과 -->
                             <td>
                                 <select id="aprvStatus" class="wd100" name="aprvStatus">
	                                   <option selected="" value=""><s:message code="WHL" /></option> <!-- 전체 -->
	                                   <c:forEach var="code" items="${codeMap.rvwStsCd}" varStatus="status">
	                                       <c:if test="${code.codeCd != '0'}" >
	                                       		<option value="${code.codeLnm}">${code.codeLnm}</option>
	                                       </c:if>					                       
					                   </c:forEach>		                
                                 </select>
                             </td>
                             
                             <th class="bd_none"><s:message code="TERM" /></th> <!-- 기간 -->
                             <td class="bd_none">
                              	<a href="#" class="tb_bt"><s:message code="DD1" /></a> <!-- 1일 -->
                                <a href="#" class="tb_bt"><s:message code="DD3" /></a> <!-- 3일 -->
                                <a href="#" class="tb_bt" id="seven"><s:message code="DD7" /></a> <!-- 7일 -->
                                <a href="#" class="tb_bt"><s:message code="MN1" /></a> <!-- 1개월 -->
                                <a href="#" class="tb_bt"><s:message code="MN3" /></a> <!-- 3개월 -->
                                <a href="#" class="tb_bt"><s:message code="MN6" /></a> <!-- 6개월 -->
                             </td>
                               
                             <th><s:message code="INQ.TERM" /></th> <!-- 조회기간 -->
   		   					 <td>
   		   						<select id="dtSrchDcd" name="dtSrchDcd">
   		   						    <option value="R"><s:message code="DEMD.DT" /></option> <!-- 요청일자 -->
   		   						    <option value="A"><s:message code="APRV.DT" /></option> <!-- 승인일자 -->
   		   						</select>
   		   						<input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/>
   		   					 </td>
					    </tr> 
                                                                          
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.DATA.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <!-- 더블클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
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
   
	
</div>
</div>

</body>
</html>
