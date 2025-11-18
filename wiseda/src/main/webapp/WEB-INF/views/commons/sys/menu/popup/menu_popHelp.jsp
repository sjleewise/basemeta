<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html lang="ko">
<head>
<title><s:message code="MENU.SRCH"/></title> <!-- 메뉴 검색 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">

var interval = "";
// var usergJson = ${codeMap.usergroup} ;	//시스템영역 코드 리스트 JSON...

$(document).ready(function() {
		$(".BK_btn_x, #btn_pop_close").click(function(){
    		//iframe 형태의 팝업일 경우
    		if ("${search.popType}" == "I") {
    			// popType - commonVO에 있음
    			parent.closeLayerPop();
    		} else {
    			window.close();
    		}
    	});
	
       //조회 이벤트 처리
		$("#popSearch").click(function(){ 
			doAction('Search'); 
		});
        
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
                
    	setibshe
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
        SetColProperty("dispYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
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
function returnPop(menuId, menuNm, scrUrl) {
// 	alert(JSON.stringify(ret));
	
	opener.frmInput.menuId.value = menuId;
	opener.frmInput.menuNm.value = menuNm;
	opener.frmInput.scrUrl.value = scrUrl;
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
    
//     var menuId = grid_sheet.GetCellValue(row, "menuId");
//     var menuNm = grid_sheet.GetCellValue(row, "menuNm");
//     var scrUrl = grid_sheet.GetCellValue(row, "filePath");
//     returnPop(menuId, menuNm, scrUrl);
    
	//부모창에 선택한다 json row를 리턴한다.....
	var retjson = grid_sheet.GetRowJson(row);
 	
	//iframe 형태의 팝업일 경우
	if ("${search.popType}" == "I") {
		parent.returnPop(JSON.stringify(retjson));
	} else {
		opener.returnPop(JSON.stringify(retjson));
	}
	
	//팝업창 닫기 버튼 클릭....
	$(".BK_btn_x").click();

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
	<div class="pop_tit_txt"><s:message code="MENU.SRCH"/></div> <!-- 메뉴 검색 -->
	<div class="pop_tit_close"><a class="BK_btn_x"<s:message code="CLOSE"/></a></div> <!-- 닫기 -->
		
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div style="clear:both; height:15px;"><span></span></div>
        
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
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonHelpPop.jsp" />         
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "290px");</script>            
	</div>
	<!-- 그리드 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="program_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

<!-- 	<div class="BK_btns BK_align_center">
		<button type="button" id="btn_pop_close" class="BK_btn_default">닫기</button>
	</div>
 -->
</body>
</html>

