<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>

<html>
<head>
<title></title>

<script type="text/javascript">

var interval = "";
EnterkeyProcess("Search");

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      

		 // 엑셀업로드용 저장 Event Bind
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
//             	showMsgBox("CNF", "선택한 테이블에 속한 컬럼도 삭제됩니다.<br>삭제 하시겠습니까?", "Delete");
        	}
        //	doAction("Delete");  
        });
   	 
    	// 추가 Event Bind
        $("#btnNew").click(function(){ doAction("Add");  }); 
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
//         $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("DirectDown2Excel"); } );
    
   	 	// 엑셀업로 Event Bind
        $("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
		
		
		
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
		setautoComplete($("#frmSearch #slcGlossaryNm"), "SLCITEM");
    }
    
    
    
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	// 타 탭에서 더블클릭으로 검색내용이 있을시 조회해준다.
	doAction("Search");
	
	
	//탭 초기화....
// 	$( "#tabs" ).tabs({heightStyle:"fill"});
	//$( "#tabs" ).tabs();
	//loadDetail();
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
        
//     	glossarySeq       용어순번 
//      glossaryClasNm    용어분류명 
//     	slcGlossaryNm     수도권매립지용어명 
//     	slcGlossaryEngNm  수도권매립지용어영문명 
//     	glossaryExpl      용어설명 
//     	glossarySrcExpl   용어출처설명 
//     	regstDt           등록일자 
//     	regstpsnId        등록자ID 
//     	updDt             수정일자 
//     	upderId           수정자ID 
        
        var headers = [
                    {Text:"No.|<s:message code='META.HEADER.SLCGLOSSATY.LST'/>", Align:"Center"}
                ];
                //No.|상태|선택|용어분류명|수도권매립지용어명|수도권매립지용어영문명|용어설명|용어출처설명|버전|등록유형코드|등록일시|등록자ID|등록자명|요청일시|수정일시|수정자ID
        
        var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:20,   SaveName:"ibsStatus",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:20,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:40,  SaveName:"glossaryClasNm",    Align:"Left", Edit:0, Hidden:0 }, 
                    {Type:"Text",   Width:80,  SaveName:"slcGlossaryNm",    Align:"Left", Edit:0, Hidden:0 ,KeyField:1}, 
                    {Type:"Text",   Width:80,  SaveName:"slcGlossaryEngNm",    Align:"Left", Edit:0, Hidden:0 }, 
                    {Type:"Text",   Width:120,  SaveName:"glossaryExpl",    Align:"Left", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:80,  SaveName:"glossarySrcExpl",    Align:"Left", Edit:0, Hidden:0 }, 
                    {Type:"Text",   Width:30,  SaveName:"objVers",    Align:"Right", Edit:0, Hidden:1}, 
                    {Type:"Combo",   Width:30,  SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"regstDt",    Align:"Center", Edit:0, Hidden:0, Format:"yyyy-MM-dd HH:mm:ss"}, 
                    {Type:"Text",   Width:40,  SaveName:"regstpsnId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:40,  SaveName:"regstpsnNm",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",  Width:20,  SaveName:"updDt",     Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:30,  SaveName:"upderId",    Align:"Left", Edit:0, Hidden:1},          
                ];
                    
        InitColumns(cols);
        
     	//콤보 목록 설정...
	    SetColProperty("regTypCd", ${codeMap.regTypCdibs});
// 	    SetColProperty("symnDcd", ${codeMap.symnDcdibs});
        //SetColHidden("rqstUserNm",1);

        InitComboNoMatchText(1, "");
        FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/meta/stnd/slcglossary_dtl.do"/>', param, function(){
		//$('#tabs').show();
	});
}


		 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
        	
			var param = $("form#frmSearch").serialize();
			loadDetail();
			$('#symn_sel_title').html('');
			grid_sheet.DoSearch("<c:url value="/meta/stnd/getSlcGlossarylist.do" />", param);
			//grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case 'SaveRow': //단건 저장
    		//saction (I-입력, U-수정)
    		var urls = '<c:url value="/meta/stnd/regSlcGlossary.do"/>';
    		var param = "";
    		
			var SaveJson = getform2IBSjson($("form#frmInput"));
// 			ibsSaveJson = $("#frmInput").serializeArray();
// 			ibsSaveJson = $("#frmInput").serializeObject();

			if(SaveJson.data.length == 0) return;
    		
//     		ajax2Json(urls, param, ibscallback);
			IBSpostJson2(urls, SaveJson, param, ibscallback);
			
    		break;
    		
        case 'Save' : //엑셀 업로드용 저장
        	//TODO 공통으로 처리...
         	var SaveJson = grid_sheet.GetSaveJson(0); // doSave와 동일
        	
        	//ibsSaveJson = grid_sheet.GetSaveJson(1); //doAllSave와 동일한 대상을 가져옴...
        	//데이터 사이즈 확인...
        	if(SaveJson.data.length == 0) return;
        	
        	var url = '<c:url value="/meta/stnd/regSlcGlossary.do"/>';
        	
         	var param = "";
             IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
        	
        case "Down2Excel":  //엑셀내려받기
        
          
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            
            break;
        case "DirectDown2Excel":  //엑셀내려받기
        
            var param = {
        			URL:"<c:url value="/meta/stnd/getSlcGlossaryXls.do"/>"  
        		    ,ExtendParam: $("form#frmSearch").serialize()
        		    ,FileName:"test.xls"
        	};
            grid_sheet.DirectDown2Excel(param);
            
            break;
        case "LoadExcel":  //엑셀업로
        
          
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            
            break;
            
		case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
//         	setTreeCheckIBS(grid_sheet);
        	
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(grid_sheet);
        	
        	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
        	if(DelJson.data.length == 0) return;
        	var url = '<c:url value="/meta/stnd/delSlcGlossary.do"/>';
        	var param = "";
        	IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
            
        case 'Add' : //신규 추가
    		$('#symn_sel_title').html('<s:message code="MSG.CNTN.INPT.STRG.BTN" />'); //내용을 입력 후 저장버튼을 클릭하세요.
    		loadDetail();
    		break;
    }       
}
    
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	
// 	alert(res.action);
	
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
	
	if ("I" == param.ibsStatus) {
		//그리드에 저장할 내용이 있습니다.저장하시겠습니까?
    	showMsgBox("CNF", "<s:message code="CNF.GRID.SAVE" />", 'Save');	
		return;
	}
	

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="MATP.TERMS.NM" /> : ' + param.slcGlossaryNm + '['+param.slcGlossaryEngNm +']'; //수도권매립지용어명
	$('#symn_sel_title').html(tmphtml);
	
	//선택한 용어를 조회한다....
	loadDetail(param);

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
	if (grid_sheet.SearchRows() == 1){
		//선택한 상세정보를 가져온다...
		var param =  grid_sheet.GetRowJson(1);
		//선택한 그리드의 row 내용을 보여준다.....
		//선택한 그리드의 row 내용을 보여준다.....
    var tmphtml = '<s:message code="MATP.TERMS.NM" /> : ' + param.slcGlossaryNm + '['+param.slcGlossaryEngNm +']'; //수도권매립지용어명
		$('#symn_sel_title').html(tmphtml);
		
		loadDetail(param);
	}
}

</script>
</head>

<body>
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="SIMIWORD.PRHB.WORD.MNG" /></div> <!-- 유사어/금지어 관리 -->
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
                   <caption><s:message code="MATP.INQ.FORM" /></caption> <!-- 수도권매립지 검색폼 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="slcGlossaryNm"><s:message code="MATP.TERMS.NM" /></label></th> <!-- 수도권매립지용어명 -->
                                <td><input type="text" id="slcGlossaryNm" class="wd300" name="slcGlossaryNm" /></td>
                                <th scope="row"><label for="glossaryClasNm"><s:message code="TERMS.CLS.NM" /></label></th> <!-- 용어분류명 -->
                                <td><input type="text" id="glossaryClasNm" class="wd300" name="glossaryClasNm" /></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="glossarySrcExpl"><s:message code="TERMS.ORGN.TXT" /></label></th> <!-- 용어출처설명 -->
                                <td><input type="text" id="glossarySrcExpl" class="wd300" name="glossarySrcExpl" /></td>
                                <th scope="row"><label for="glossaryExpl"><s:message code="CONTENT.TXT" /></label></th> <!-- 설명 -->
                                <td><input type="text" id="glossaryExpl" class="wd300" name="glossaryExpl"/></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
       <!-- 조회버튼영역  -->
       <tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />         
<%-- 		<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />          --%>
<div style="clear:both; height:5px;"><span></span></div>

        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "200px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="symn_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li><a href="#tabs-1"><s:message code="MATP.TECH.TERMS.DTL" /></a></li> <!-- 수도권매립지 기술용어상세 -->
	    
<!-- 	    <li><a href="#tabs-2">컬럼 목록</a></li> -->
	  </ul>
	  <div id="tabs-1">
			<!-- 	상세정보 ajax 로드시 이용 -->
			<div id="detailInfo"></div>
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
			
			<!-- 	상세정보 ajax 로드시 이용 END -->
	  </div>
	 </div>
	<!-- 선택 레코드의 내용을 탭처리 END -->
</body>
</html>
