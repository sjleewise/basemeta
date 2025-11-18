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
<title><s:message code="CD.CLS.SYST.INQ" /></title> <!-- 코드분류체계 조회 -->

<script type="text/javascript">

$(document).ready(function() {
	
		
		//탭 초기화....
		//$( "#tabs" ).tabs();
			
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));

		
                
        //그리드 초기화 
//         initGrid();
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                      
        // 추가 Event Bind
        $("#btnTreeNew").click(function(){ doAction("New");  }).hide();
                        
        // 삭제 Event Bind
        $("#btnDelete").click(function(){ 
    		//선택체크박스 확인 : 삭제할 대상이 없습니다..
    		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
    			//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL.TBL" />", 'Delete');
        	}
        }).hide(); 


        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ).hide();
        
        
        
        
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	loadDetail();
	
	
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
        
        var headtext  = "<s:message code='META.HEADER.CODECFCSYS.LST1'/>";
        /* No.|코드분류체계ID|코드분류체계유형|코드분류체계명|코드분류체계포멧 */
        	headtext += "<s:message code='META.HEADER.CODECFCSYS.LST2'/>";
        	/* |담당자ID|담당자명|설명|등록유형|요청일시|요청자ID|요청자명|승인일시|승인자ID|승인자명|요청번호|요청일련번호 */
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
                    {Type:"Text",   Width:100, SaveName:"codeCfcSysId",   	 Align:"Left",   Edit:0, Hidden:1},
                    {Type:"Combo",   Width:150,   SaveName:"codeCfcSysCd",	 	Align:"Left", Edit:0},
                    {Type:"Text",   Width:150, SaveName:"codeCfcSysLnm",   	 Align:"Left",   Edit:0},
                    {Type:"Text",   Width:150, SaveName:"codeCfcSysFrm",     Align:"Left",   Edit:0}, 

                    {Type:"Text",   Width:50,   SaveName:"crgUserId",	 Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,   SaveName:"crgUserNm",	 Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:400,  SaveName:"objDescn",    Align:"Left",   Edit:0},
                    {Type:"Combo",   Width:100,   SaveName:"regTypCd",	 	Align:"Center", Edit:0},
                    
                    {Type:"Text",   Width:50,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
					{Type:"Text",   Width:50,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:50,  SaveName:"aprvDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
					{Type:"Text",   Width:50,  SaveName:"aprvUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"aprvUserNm",  Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:50,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:50,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		//분류체계유형
		SetColProperty("codeCfcSysCd", 	${codeMap.codeCfcSysCdibs});
        
        InitComboNoMatchText(1, "");
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}




//화면상의 모든 액션은 여기서 처리...
function doAction(sAction)
{
        
    switch(sAction)
    {
        
        
		case "Search":
			loadDetail();
			codecfc_sheet.RemoveAll();
			var param = $("#frmSearch").serialize();
			grid_sheet.DoSearch("<c:url value="/meta/codecfcsys/getCodeCfcSysList.do" />", param);

						
			break;
        	
		
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2ExcelBuffer(true);  
            
        	if(grid_sheet.SearchRows()>0){
        		grid_sheet.Down2Excel({FileName:'CodeCfcSys_list',SheetName:'<s:message code="CD.CLS.SYST" />', HiddenColumn:1, Merge:1}); 
        	} //코드분류체계
        	if(codecfc_sheet.SearchRows()>0){
        		codecfc_sheet.Down2Excel({FileName:'CodeCfcSys_list',SheetName:'<s:message code="CD.CLS.SYST.ITEM" />', HiddenColumn:1, Merge:1}); 
        	} //코드분류체계 항목
        	
        	grid_sheet.Down2ExcelBuffer(false);  
            break;
            
        
       
    }       
}
 

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/meta/codecfcsys/ajaxgrid/codecfcsys_dtl.do"/>', param, function( response, status, xhr ) {
		//dtl페이지 버튼, 요청상태 hide...
    	$("#rqstInputBtn").hide();
    	$("#validreviewDiv").hide();
    	$("#frmInput button").hide();
    	//readonly 속성 적용
        $("#frmInput").addClass("tb_read");
    	$("#frmInput select").attr('disabled', true);
    	$("#frmInput textarea").attr('readOnly', true);
    	$("#frmInput input").attr('readOnly', true);
    	$("#frmInput th").removeClass("th_require");
    	
		  if ( status == "error" ) { 
			    var msg = "<s:message code='MSG.DTL.INFO.EROR' />...";  //상세정보 호출중 오류발생
			    alert( msg + xhr.status + " " + xhr.statusText );
			  }
	});
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
    
    if(row < 1) return;
    
	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(grid_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="CD.CLS.SYST" /> : ' + param.codeCfcSysLnm;  //코드분류체계
	$('#codecfc_sel_title').html(tmphtml);
	
	var codeCfcSysId = "&codeCfcSysId="+grid_sheet.GetCellValue(row, "codeCfcSysId");
	

	loadDetail(codeCfcSysId);
	
	codecfc_sheet.DoSearch("<c:url value="/meta/codecfcsys/getCodeCfcSysItemList.do" />", codeCfcSysId);
	grid_sub_codecfcsyshist.DoSearch("<c:url value="/meta/codecfcsys/getCodeCfcSysHistList.do" />", codeCfcSysId);
	grid_sub_codecfcsysitemhist.DoSearch("<c:url value="/meta/codecfcsys/getCodeCfcSysItemHistList.do" />", codeCfcSysId);
    
}


function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		
	}
	
}


</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="CD.CLS.SYST.INQ" /></div> <!-- 코드분류체계 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
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
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="codeCfcSysCd"><s:message code="CD.CLS.SYST.PTRN" /></label></th> <!-- 코드분류체계유형 -->
                                <td><select id="codeCfcSysCd" name="codeCfcSysCd">
                                		<option value="">---<s:message code="WHL" />---</option> <!-- 전체 -->
                                        <c:forEach var="code" items="${codeMap.codeCfcSysCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>
                                <th scope="row"><label for="codeCfcSysLnm"><s:message code="CD.CLS.SYST.NM" /></label></th> <!-- 코드분류체계명 -->
                                <td><input type="text" id="codeCfcSysLnm" name="codeCfcSysLnm" class="wd200"/></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="codeCfcSysFrm"><s:message code="CD.CLS.SYST.FORMAT" /></label></th> <!-- 코드분류체계포맷 -->
                                <td><input type="text" id="codeCfcSysFrm" name="codeCfcSysFrm" class="wd200"/></td>
                                <th scope="row"><label for="crgUserNm"><s:message code="CHG.R.NM" /></label></th> <!-- 담당자명 -->
                                <td><input type="text" id="crgUserNm" name="crgUserNm" class="wd200" /></td>
                            </tr>
                            
                           
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
</div>
<div style="clear:both; height:5px;"><span></span></div>
<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />

<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "150px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="codecfc_sel_title"<s:message code="MSG.CD.CLS.SYST.CHC" /></div> <!-- 코드분류체계를 선택하세요. -->
	</div>

<div style="clear:both; height:5px;"><span></span></div>
	<!-- 선택 레코드의 카테고리 별로 있을 경우 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-2"><s:message code="CD.CLS.SYST.INFO" /></a></li> <!-- 코드분류체계 정보 --> 
	    <li><a href="#tabs-1"><s:message code="CD.CLS.SYST.LST" /></a></li> <!-- 코드분류체계 목록 -->
	    <li><a href="#tabs-3"><s:message code="CD.CLS.SYST.CHG.HSTR" /></a></li> <!-- 코드분류체계 변경이력 -->
	    <li><a href="#tabs-4"<s:message code="CD.CLS.SYST.CHG.HSTR" /></a></li> <!-- 코드분류체계목록 변경이력 -->
	  </ul>
	  
	  <!-- 코드분류체계목록 목록 탭 -->
	  <div id="tabs-2">
	  	<div id="detailInfo"></div>
	  </div>
	  
	  <div id="tabs-1">
		<%@include file="codecfcsys_item_dtl.jsp" %>
	  </div>
	  
	  <div id="tabs-3">
		<%@include file="codecfcsyshist_dtl.jsp" %>
	  </div>
	  <div id="tabs-4">
		<%@include file="codecfcsysitemhist_dtl.jsp" %>
	  </div>
	</div>
</body>
</html>