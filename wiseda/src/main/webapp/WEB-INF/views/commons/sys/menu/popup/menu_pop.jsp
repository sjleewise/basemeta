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
<title><s:message code="UPRN.MENU.ID.INQ" /></title> <!-- 상위메뉴ID 검색 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
	
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
				
		//탭 초기화....
//	 	$( "#tabs" ).tabs({heightStyle:"fill"});
// 		//$( "#tabs" ).tabs();
                    
        //그리드 초기화 
//         initGrid();
       //조회 이벤트 처리
		$("#popSearch").click(function(){ 
			doAction('Search'); 
		});
		//적용 버튼 이벤트 처리
		$("#popApply").click(function(){
// 	 		alert("적용버튼");
			
// 			var menuId = grid_sheet.GetCellValue(row, "menuId");
// 			alert();
// 			alert(menuId);
// 			returnPop(menuId);
		});
	
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
	// 		alert("초기화버튼");
			$("form[name=frmPopSearch]")[0].reset();
		});
                      
        
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
         //$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
        
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
        setautoComplete($("#frmSearch #menuNm"), "MENU");
    }
    

		
);

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	//페이지 호출시 처리할 액션...
	doAction('Search');
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
                    {Text:"<s:message code='COMMON.HEADER.MENU.POP'/>", Align:"Center"}
                    /* No.|상태|선택|메뉴ID|만료일시|시작일시|메뉴명|상위메뉴ID|메뉴레벨|메뉴구분|파일경로|파일명|화면출력여부|화면출력순서|관리자메뉴여부|설명|버전|등록구분코드|작성시각|작성자 */
                ];
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:20,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:20,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:20,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Text",   Width:40,  SaveName:"menuId",    Align:"Center", Edit:0, Hidden:1, KeyField:1},
                    {Type:"Date",   Width:30,  SaveName:"expDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"}, 
                    {Type:"Date",   Width:30,  SaveName:"strDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"}, 
                    {Type:"Text",   Width:80,  SaveName:"menuNm",    Align:"Left", Edit:0, Hidden:0, KeyField:1, TreeCol:1}, 
                    {Type:"Text",   Width:30,  SaveName:"uppMenuId",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:20,  SaveName:"menuLvl",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Combo",   Width:30,  SaveName:"menuDcd",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:50,  SaveName:"filePath",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:50,  SaveName:"fileNm",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",   Width:30,  SaveName:"dispYn",    Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:30,  SaveName:"dispOrd",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:30,  SaveName:"mngrMenuYn",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,  SaveName:"objDescn",    Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:20,  SaveName:"objVers",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Combo",  Width:20,  SaveName:"regTypCd",     Align:"Center", Edit:0, Hidden:1},
                    {Type:"Date",   Width:30,  SaveName:"writDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},          
                    {Type:"Text",   Width:30,  SaveName:"writUserId",    Align:"Center", Edit:0, Hidden:1}
                    
                    /*  {Type:"Text",   Width:130,  SaveName:"userTelno",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"userHtelno",      Align:"Left",   Edit:1, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"emailAddr",      Align:"Left",   Edit:1, Hidden:0},

                                    {Type:"Combo",  Width:100,  SaveName:"regTypCd",     Align:"Center", Edit:0, ComboCode:"C|U|D", ComboText:"신규|변경|삭제",},                        
                    {Type:"Date",   Width:130,  SaveName:"writDtm",  	 Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:130,  SaveName:"writUserId",   Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:130,  SaveName:"writUserNm",   Align:"Center", Edit:0} */
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("regTypCd", ${codeMap.regTypCdibs});
        SetColProperty("dispfYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
        SetColProperty("menuDcd", ${codeMap.menuDcdibs});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        //InitComboNoMatchText(1, "");

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
        
//     case "New":        //동일레벨 추가...
//     	//현재행을 가져온다.
//     	var crow = grid_sheet.GetSelectRow();
//     	var clevel = grid_sheet.GetRowLevel(crow);
    	
//     	//선택된 행의 다음라인에 현재레벨로 추가한다.
//     	var nrow = grid_sheet.DataInsert(crow+1, clevel);
		
//     	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
//      	grid_sheet.SetCellValue(nrow, "menuLvl"	, grid_sheet.GetCellValue(crow, "menuLvl"));
//      	grid_sheet.SetCellValue(nrow, "menuOrdr"		, grid_sheet.GetRowLevel(nrow));
    
//         break;
            
            
//     	 case "NewLow":        //하위레벨추가...

//          	//현재행을 가져온다.
//          	var crow = grid_sheet.GetSelectRow();
//          	var clevel = grid_sheet.GetRowLevel(crow);
         	
//          	//선택행의 다음라인에 하위레벨로 추가한다.
//          	var nrow = grid_sheet.DataInsert();
         	
//          	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
         	
// 	    	grid_sheet.SetCellValue(nrow, "upperMenuNo"	, grid_sheet.GetCellValue(crow, "menuNo"));
// 	    	grid_sheet.SetCellValue(nrow, "menuOrdr"		, grid_sheet.GetRowLevel(nrow));
         	
         
//              break;
             
             
        
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	//alert(param);

        	grid_sheet.DoSearch('<c:url value="/commons/sys/menu/ajaxgrid/selectMenuList.do" />', param);
        	
        	break;
        	
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
    		break;

    }       
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/sys/menu/ajaxgrid/selectMenuDetail.do"/>', param, function(){
		//$('#tabs').show();
		
	});

}

//팝업 리턴값 제공
function returnPop(menuId, menuNm) {
// 	alert(JSON.stringify(ret));
	
	opener.frmInput.uppMenuId.value = menuId;
	opener.frmInput.uppMenuNm.value = menuNm;
	window.close();
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
    
    var menuId = grid_sheet.GetCellValue(row, "menuId");
    var menuNm = grid_sheet.GetCellValue(row, "menuNm");
    returnPop(menuId, menuNm);

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
	var menuId = "&menuId="+grid_sheet.GetCellValue(row, "menuId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="MENU.NM" /> : ' + param.menuNm; /* 메뉴명 */
	$('#program_sel_title').html(tmphtml);
	
	//메뉴ID를 토대로 조회한다....
	loadDetail(menuId);
	

}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
		//요청서 삭제 후처리...
		case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				
				//doActionCol("Search");
		
			break;
		//요청서 단건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG%>" :

			
			break;
		//요청서 여러건 등록 후처리...
		case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
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




</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="UPRN.MENU.ID.INQ" /></div> <!-- 상위메뉴ID 검색 -->
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
                  </colgroup>
                   
                   <tbody>                          
                                              <th scope="row"><label for="menuNm"><s:message code="MENU.NM" /></label></th> <!-- 메뉴명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="menuNm" id="menuNm" />
                                </span>
                                </td>  

                       <!--     <th scope="row"><label for="usergId">사용자그룹명</label></th>
                                <td><select id="usergId" class="usergId" name="usergId">
                                        <option selected="" value="">-전체-</option>
                                        <c:forEach var="code" items="${codeMap.userglist }" varStatus="status" >
                                        <option value="${code.codeCd }">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                    </td>  -->  
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
            
       
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->         
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "220px");</script>            
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

