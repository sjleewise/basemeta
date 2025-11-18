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
			
// 			var menuId = grid_pop.GetCellValue(row, "menuId");
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
        setautoComplete($("#frmSearch #stwdLnm"), "STWD");
        
    }
    
		
);

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	
	//페이지 호출시 처리할 액션...
    $('#frmSearch #stndAsrt').val('${param.stndAsrt}').prop('selected', true);	
	doAction('Search');
});


$(window).resize(
    
    function(){
    	setibsheight($("#grid_01"));        
    	// grid_pop.SetExtendLastCol(1);    
    }
);


function initGrid()
{
    
    with(grid_pop){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.SELECTSTWD.POP'/>", Align:"Center"}
                ];
                //No.|상태|선택|표준단어ID|표준단어논리명|표준단어물리명|영문의미|한자명|출처구분|요청번호|요청일련번호|객체설명|객체버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:30,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:20,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:20,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Text",   Width:40,  SaveName:"stwdId",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",  Width:50,  SaveName:"stndAsrt",    Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:50,  SaveName:"stwdLnm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:50,  SaveName:"stwdPnm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:60,  SaveName:"engMean",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:40,  SaveName:"cchNm",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:30,  SaveName:"orgDs",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:40,  SaveName:"rqstNo",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:40,  SaveName:"rqstSno",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"objDescn",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:30,  SaveName:"objVers",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Combo",   Width:30,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",   Width:80,  SaveName:"frsRqstDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"}, 
                    {Type:"Text",   Width:20,  SaveName:"frsRqstUserId",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Date",  Width:20,  SaveName:"rqstDtm",     Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:30,  SaveName:"rqstUserId",    Align:"Center", Edit:0, Hidden:1},          
                    {Type:"Date",   Width:30,  SaveName:"aprvDtm",    Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
                    {Type:"Text",   Width:30,  SaveName:"aprvUserId",    Align:"Center", Edit:0, Hidden:1}
                    
                    
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	   	SetColProperty("regTypCd", ${codeMap.regTypCdibs});
        SetColProperty("stndAsrt", 	${codeMap.stndAsrtibs});     	
        //SetColProperty("usergId", 	{ComboCode:"DA|AD|DB|UR|MR|DV", 	ComboText:"DA|관리자|DBA|사용자|모델러|개발자"});
        //SetColProperty("deptId", 	{ComboCode:"부서1|부서2|부서3|부서4|부서5|부서6", 	ComboText:"부서1|부서2|부서3|부서4|부서5|부서6"});   

        //InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        //SetColHidden("writUserNm",1);
        //SetColHidden("arrUsrId",1);
      
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_pop);    
    //===========================
}

function doAction(sAction)
{
        
    switch(sAction)
    {		    
        
//     case "New":        //동일레벨 추가...
//     	//현재행을 가져온다.
//     	var crow = grid_pop.GetSelectRow();
//     	var clevel = grid_pop.GetRowLevel(crow);
    	
//     	//선택된 행의 다음라인에 현재레벨로 추가한다.
//     	var nrow = grid_pop.DataInsert(crow+1, clevel);
		
//     	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
//      	grid_pop.SetCellValue(nrow, "menuLvl"	, grid_pop.GetCellValue(crow, "menuLvl"));
//      	grid_pop.SetCellValue(nrow, "menuOrdr"		, grid_pop.GetRowLevel(nrow));
    
//         break;
            
            
//     	 case "NewLow":        //하위레벨추가...

//          	//현재행을 가져온다.
//          	var crow = grid_pop.GetSelectRow();
//          	var clevel = grid_pop.GetRowLevel(crow);
         	
//          	//선택행의 다음라인에 하위레벨로 추가한다.
//          	var nrow = grid_pop.DataInsert();
         	
//          	//추가되기전 행의 상위 ID와 시스템 ID가 있을경우 추가한 행에 셋팅해준다.
         	
// 	    	grid_pop.SetCellValue(nrow, "upperMenuNo"	, grid_pop.GetCellValue(crow, "menuNo"));
// 	    	grid_pop.SetCellValue(nrow, "menuOrdr"		, grid_pop.GetRowLevel(nrow));
         	
         
//              break;
             
             
        
            
        case "Search":
        	var param = $('#frmSearch').serialize();
        	grid_pop.DoSearch('<c:url value="/meta/stnd/getStndWordlist.do" />', param);
        	
        	
        	break;
        	
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
    		break;

    }       
}


// //팝업 리턴값 제공
function returnPop(stwdId, stwdLnm, stwdPnm, stndAsrt) {
// 	alert(JSON.stringify(ret));
	opener.frmInput.stwdId.value = stwdId;
	opener.frmInput.sbswdLnm.value = stwdLnm;
	opener.frmInput.sbswdPnm.value = stwdPnm;
	opener.frmInput.stndAsrt.value = stndAsrt;
	window.close();
}






/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_pop_OnDblClick(row, col, value, cellx, celly) {
    if(row < 1) return;
    
    var stwdId = grid_pop.GetCellValue(row, "stwdId");
    var stwdLnm = grid_pop.GetCellValue(row, "stwdLnm");
    var stwdPnm = grid_pop.GetCellValue(row, "stwdPnm");
    var stndAsrt = grid_pop.GetCellValue(row, "stndAsrt");
    returnPop(stwdId, stwdLnm, stwdPnm, stndAsrt);
    
//     ibs2formmapping(row, $("form#frmInput", opener.document), grid_pop);
	
// 	window.close();

}

function grid_pop_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_pop.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_pop.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_pop.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="STRD.WORD.NM" /> : ' + param.stwdLnm +' [ <s:message code="STRD.WORD.ID" /> : ' + param.stwdId + ' ]'; //표준단어명, 표준단어ID
	$('#stwd_sel_title').html(tmphtml);
	

	

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
    <div class="pop_tit_txt"><s:message code="STRD.WORD.INQ"/></div> <!-- 표준단어 검색 -->
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
                      <tr>
	                 	   <th scope="row" class="th_require"><label for="stndAsrt"><s:message code="STND.ASRT"/></label></th> <!-- 표준분류 -->
						   <td>
						         <select  id="stndAsrt" name="stndAsrt"  class="wd300">
						                 <option value=""><s:message code="WHL"/></option>
						                 <c:forEach var="code" items="${codeMap.stndAsrt}" varStatus="status">
						         		  <option value="${code.codeCd}">${code.codeLnm}</option>
						         		</c:forEach>
						         </select>
						   </td>                         
                           <th scope="row"><label for="stwdLnm"><s:message code="STRD.WORD.NM" /></label></th> <!-- 표준단어명 -->
                           <td>
                                <span class="input_file">
                                <input type="text" name="stwdLnm" id="stwdLnm" />
                                </span>
                           </td>  

                       <!--     <th scope="row"><label for="usergId">사용자그룹명</label></th>
                                <td><select id="usergId" class="usergId" name="usergId">
                                        <option selected="" value="">---전체----</option>
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
	     <script type="text/javascript">createIBSheet("grid_pop", "100%", "220px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="stwd_sel_title"> <span></span></div>
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

