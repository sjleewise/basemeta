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
<title><s:message code="USER.INQ2" /></title> <!-- 사용자 검색 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

var interval = "";


$(document).ready(function() {
	
// 		alert("document.ready");
		//var usergJson = ${codeMap.userglist};	//시스템영역 코드 리스트 JSON...
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
		
		
		//탭 초기화....
//	 	$( "#tabs" ).tabs({heightStyle:"fill"});
// 		//$( "#tabs" ).tabs();
                    
                    
        //그리드 초기화 
//         initGrid();

		$("#btnSearch").click(function(){ 
			doAction('Search'); 
		});
		
		$("#btnSave").click(function(){
			console.log('${param}');
			//var rows = grid_sheet.FindStatusRow("I|U|D");
        	var rows = grid_sheet.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
        	//저장할래요? 확인창...
    		var message = "<s:message code="CNF.APRGUSERSAVE" arguments="${param.aprgNm}" />";
    		showMsgBox("CNF", message, 'Save');	
        	//doAction("Save"); 
			
		}).show();
	
		      
        
        // 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } ).hide();

        
        //적용 버튼 이벤트 처리
		$("#btnApply").hide();
		$("#btnTreeNew").hide(); 
        $("#btnDelete").hide(); 
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
         //create_selectbox(usergJson, $("#usergId"));
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
        setautoComplete($("#frmSearch #userNm"), "USRNM");
        setautoComplete($("#frmSearch #deptNm"), "DEPT");

        
    }
    
		
);
        //엔터키 처리한다.
        EnterkeyProcess("Search");

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	//페이지 호출시 처리할 액션...
	//doAction('Search');
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
                    {Text:"<s:message code='COMMON.HEADER.APPROVEUSER.POP'/>", Align:"Center"}
                    /* No.|상태|선택|사용자ID|사용자명|직급명|사용자그룹명|부서ID|부서명 */
                ];
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                      {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                      {Type:"Status", Width:30,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                   	  {Type:"CheckBox", Width:40,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},              
                      {Type:"Text",   Width:50,  SaveName:"userId",    Align:"Center", Edit:0, Hidden:0}, 
                      {Type:"Text",   Width:50,  SaveName:"userNm",    Align:"Center", Edit:0, KeyField:0 }, 
                      {Type:"Text",   Width:30,  SaveName:"jgdNm",    Align:"Center", Edit:0, KeyField:0 }, 
                 //	  {Type:"Text",   Width:130,  SaveName:"usergLnm",    Align:"Center", Edit:0, Hidden:1},
                      {Type:"Combo",   Width:70,  SaveName:"usergId",    Align:"Center", Edit:0},
                      {Type:"Text",   Width:80,  SaveName:"deptId",    Align:"Center", Hidden:1, Edit:0, KeyField:0},
                   	  {Type:"Text",   Width:200,  SaveName:"deptNm",    Align:"Center", Edit:0, KeyField:0},          
                 //   {Type:"Text",   Width:50,  SaveName:"objDescn",     Align:"Left", 	 Edit:1},
                 //   {Type:"Text",   Width:50,  SaveName:"objVers",      Align:"Left",   Edit:0, Hidden:0},
                 //   {Type:"Combo",  Width:50,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"신규|변경|삭제",},                        
                 //   {Type:"Date",   Width:60,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                 //   {Type:"Text",   Width:60,  SaveName:"writUserId",   Align:"Center", Edit:0, Hidden:0},
                 //   {Type:"Text",   Width:60,  SaveName:"writUserNm",   Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("usergId", ${codeMap.usergp});

        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      
        FitColWidth();  
        
        //SetExtendLastCol(1);    
       // grid_sheet.DoSearch('<c:url value="/cmvw/user/userSelectlist.do" />');
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
        	var param = $('#frmSearch').serialize();
        	//alert(param);

        	grid_sheet.DoSearch('<c:url value="/commons/user/userSelectlistOrderByDeptNm.do" />', param);
        	
        	break;
        	
        case "Save":
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); //트랜젝션이 있는 경우만 가져옴 : doSave와 동일
        	
        	//ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/commons/damgmt/approve/approveuserSave.do"/>';
        	
         	var param = "aprgId=${param.aprgId}";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
    		


    }       
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
	//alert(res.action);
	
	switch(res.action) {

		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
			
			var param1 = "aprgId=${param.aprgId}";
			opener.grid_sub.DoSearch('<c:url value="/commons/damgmt/approve/approvegroupuser_lst.do" />', param1);
 			doAction("Search");

			break;
		
		default : 
			// 아무 작업도 하지 않는다...
			break;
			
	}
	
}




//팝업 리턴값 제공
// function returnPop(fileNm, url) {
// // 	alert(JSON.stringify(ret));
	
// 	opener.frmInput.fileNm.value = fileNm;
// 	opener.frmInput.filePath.value = url;
// 	window.close();
// }






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
	if(grid_sheet.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	var userId = "&userId="+grid_sheet.GetCellValue(row, "userId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="USER.NM" /> : ' + param.userNm +' [ <s:message code="USER.ID" /> : ' + param.userId + ' ]'; /* 사용자명 */ /* 사용자ID */
	$('#program_sel_title').html(tmphtml);
	
	
}



</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="USER.INQ2" /></div>  <!-- 사용자 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MENU.INQ' />"> <!-- 메뉴조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>                          
                        <tr><th scope="row"><label for="userNm"><s:message code="USER.NM" /></label></th> <!-- 사용자명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="userNm" id="userNm" />
                                </span>
                                </td>  

                           <th scope="row"><label for="usergId"><s:message code="USER.GRP.NM" /></label></th> <!-- 사용자그룹명 -->
                                <td><select id="usergId" class="usergId" name="usergId">
                                        <option selected="" value="">----<s:message code="WHL" />----</option> <!-- 전체 -->
                                        <c:forEach var="code" items="${codeMap.userglist }" varStatus="status" >
                                        <option value="${code.codeCd }">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                    </td>   
                       </tr>
                       <tr><th scope="row"><label for="deptNm"><s:message code="DEPT.NM" /></label></th> <!-- 부서명 -->
                                <td colspan="3">
                                <span class="input_file">
                                <input type="text" name="deptNm" id="deptNm" />
                                </span>
                                </td>  </tr>
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

   
<%-- <form id="frmExcel" name="frmExcel" action="" method="post" > --%>
<!-- 	<input type="hidden" name="excelhtml" id="excelhtml"> -->
<!-- 	<input type="hidden" name="excelname" id="excelname"> -->
<%-- </form> --%>


<!-- <div id="excel_pop"> -->
<!-- 	<iframe src=""></iframe> -->
<!-- </div> -->

</body>
</html>

