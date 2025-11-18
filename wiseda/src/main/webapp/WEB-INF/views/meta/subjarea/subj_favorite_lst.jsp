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
<title>관심주제영역 관리</title>
<script type="text/javascript">

EnterkeyProcess("Search");
$(document).ready(function() {
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  }).show();
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	
	// 추가(신규) Event Bind
	$("#btnNew").click(function(){ doAction("New");  });
	
	// 그리드 저장
	$("#btnSave").click(function(){
		showMsgBox("CNF", "<s:message code="CNF.SAVE" />", 'Save');
	}).show();
	
	// 삭제 Event Bind
	$("#btnDelete").click(function(){ 
		//선택체크박스 확인 : 삭제할 대상이 없습니다..
		if(checkDelIBS (grid_sheet, "<s:message code="ERR.CHKDEL" />")) {
			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
    	}
	});	
	
 	//달력팝업 추가...
//  $( "#searchBgnDe" ).datepicker();
// 	$( "#searchEndDe" ).datepicker();
	//기간 버튼 클릭 체크
 	$(".bd_none a").click(function(){
		var btna = $(".bd_none a"); 
		var idx = btna.index(this);
		btna.removeClass('tb_bt_select').addClass('tb_bt');
		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

		//alert(idx);
		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
		
	}); 
	
    $('#subjSearchPop').button({   //주제영역 검색팝업 이벤트
	    icons: {
	        primary: "ui-icon-search"
	      },
	      text: false, 
	      create: function (event, ui) {
//	     	  $(this).addClass('search_button');
			  $(this).css({
				  'width': '18px',
				  'height': '18px',
				  'vertical-align': 'bottom'
				  });
	    	  
	      }
	    }).click(function(event){
	    	
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		
		//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
		//$('div#popSearch').dialog("open");
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
	    	    param = "&sFlag=SOW";
			var popwin = OpenModal(url+"?"+param, "searchpop",  800, 600, "no");
			popwin.focus();
		
	}).parent().buttonset();
});

$(window).on('load',function() {
	initGrid();
// 	doAction("Search");
	//$("button.btn_rqst_new, button.btn_save, button.btn_delete").show(); // 버튼 노출	
	//$("button.btn_chang_add, button.btn_excel_load").hide(); // 버튼 숨김
	$("#btnChangAdd").hide();
	$("#btnExcelLoad").hide();
	
});



$(window).resize(function(){
	
});


function initGrid()
{
	with(grid_sheet){
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headtext  = "<s:message code='META.HEADER.SUBJ.FVRT.LST'/>";
		//No.|상태|선택|주제영역명|주제영역물리명|사용자ID|사용자명|부서명|등록일시|subjId|일련번호
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	 Align:"Center", Edit:0},					
					{Type:"Status", Width:40,   SaveName:"ibsStatus",  Align:"Center", Edit:0, Hidden:1},
					
					{Type:"CheckBox", Width:60, SaveName:"ibsCheck", Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Popup",  Width:400,  SaveName:"subjLnm",  Align:"Left", Edit:1, KeyField:1, UpdateEdit:0},
					{Type:"Text",   Width:150,  SaveName:"subjPnm",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"userId",  Align:"Left", Edit:0},
					{Type:"Text",   Width:120,  SaveName:"userNm",    Align:"Left", Edit:0 },
					{Type:"Text",   Width:120,  SaveName:"deptNm",    Align:"Left", Edit:0 },					
					
					{Type:"Text",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:0},
					{Type:"Text",   Width:150,  SaveName:"subjId",  Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:150,  SaveName:"subjOwnerId",  Align:"Left", Edit:0, Hidden:1}
				];
					
		InitColumns(cols);
		
		//콤보 목록 설정
// 		SetColProperty("subjBizDcd", 	{ComboCode:"DIC|R9P|DDL", ComboText:"표준사전|R9연계물리모델|DDL생성"});
		
		InitComboNoMatchText(1, "");
		
		FitColWidth();  
		SetExtendLastCol(1);	
	}
	
	//==시트설정 후 아래에 와야함=== 
	init_sheet(grid_sheet);	
	//===========================
   
}


//주제영역 팝업 리턴값 처리
function returnSubjPop (ret, row) {
	var retjson = jQuery.parseJSON(ret);
//     console.log(retjson);
	
	// 추가 > row
	if(row){
		// console.log("retjson --> "+retjson.subjLvl);
		if(getFavoriteSubjId(retjson.subjId)>0){
			alert("이미 존재하는 주제영역입니다.");
			return;
		}
		grid_sheet.SetCellValue(row, "subjLnm", retjson.fullPath);
		grid_sheet.SetCellValue(row, "subjPnm", retjson.subjPnm);
		grid_sheet.SetCellValue(row, "subjId", retjson.subjId);		
	}else{
		// 검색 팝업
		$("#frmSearch #subjLnm").val(retjson.fullPath);
		$("#frmSearch #subjId").val(retjson.subjId);		
	}
}

function doAction(sAction)
{				
	switch(sAction)
	{			
		case "Search":
			var param = $("#frmSearch").serialize();
        	grid_sheet.DoSearch('<c:url value="/meta/subjarea/getsubjFavoritelist.do" />', param);
			break;			
		case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
			break;        	
	    case "SearchSubj":
	    	var param = $('#frmSearch').serialize();
	    	grid_owner_sheet.RemoveAll();
	    	grid_sheet.DoSearch("<c:url value="/meta/subjarea/getsubjlist.do" />", param);
	    	break;			

		case "New" :
			//첫행에 추가...
		 	grid_sheet.DataInsert(0);
			
		 	grid_sheet.SetCellValue(1, "subjId",$("#subjId").val());
		 	grid_sheet.SetCellValue(1, "userNm",$("#userNm").val());
		 	grid_sheet.SetCellValue(1, "deptNm",$("#deptNm").val());
			break;	
        case "Save" :
        	//TODO 공통으로 처리...
        	var rows = grid_sheet.FindStatusRow("I|U|D");
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	ibsSaveJson = grid_sheet.GetSaveJson(0);	//DoSave와 동일...
//         	ibsSaveJson = grid_sheet.GetSaveJson(1);	//DoAllSave와 동일...
        	//데이터 사이즈 확인...
        	if(ibsSaveJson.data.length == 0) return;
        	
            var url = "<c:url value="/meta/subjarea/subjFavoritelist.do"/>";
            var param = $('form[name=mstFrm]').serialize();
            IBSpostJson2(url, ibsSaveJson, param, ibscallback);            
        	break;
        	
   		case "Delete" :
   	    	//체크된 행 중에 입력상태인 경우 시트에서 제거...
   	    	delCheckIBS(grid_sheet);
   	    	
   	    	var DelJson = grid_sheet.GetSaveJson(0, "ibsCheck");
   	    	//2. 처리대상 행이 없는 경우 리턴한다.
   			if (DelJson.Code == "IBS000") return; 
   			
   			var url = "<c:url value="/meta/subjarea/delsubjFavoritelist.do"/>";
   			var param = $("#mstFrm").serialize();
   			IBSpostJson2(url, DelJson, param, ibscallback);
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
	if(row < 1) return;

}

function grid_sheet_OnPopupClick(Row,Col) {
	//주제영역 검색 팝업
	if ("subjLnm" == grid_sheet.ColSaveName(Col)) {
		var param = "ibsSeq=" +Row;
		    param += "&sFlag="+$("#mstFrm #bizDtlCd").val();
		    param += "&subjBizDcd="+grid_sheet.GetCellValue(Row, "subjBizDcd");
		var url = '<c:url value="/meta/subjarea/popup/subjSearchPop.do" />';
		openLayerPop(url,  800, 600, param);
	}
}


/*======================================================================*/
//IBSpostJson2 후처리 함수
/*======================================================================*/
//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
//alert(res.action);
//	alert(res.resultVO.rqstNo);
	doAction("Search");
}

//=======기존 주제영역 조회=====	 
// param1: subjId
function getFavoriteSubjId(subjId){
 
	 var ret = 0;
	 
	 var vUrl = "<c:url value="/meta/subj/subjFavoriteCntBySubjId.do" />";
	 
	 var param = "subjId=" + subjId;
	 
	 $.ajax({
		  type: "POST",			  
		  url: vUrl,
		  dataType: "json",
		  async: false,
		  data: replacerXssParam(param),
		  success: function(res){
			  //console.log("res --- >"+res);
			  ret = res;
		  },
		  error: function(res){
			 // alert(res.data);
			 alert("오류가 발생하였습니다.");
			 return;
		  }
	 });
	 return ret;
}	
</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">관심주제영역권한 조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="표준용어조회">
                   <caption>표준용어 검색폼</caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="subjLnm">주제영역명</label></th>
                            <td>                            
                                <span class="input_file">
                                <input type="hidden" name="subjId" id="subjId" class="wd90p" readOnly/>
                                <input type="text" name="subjLnm" id="subjLnm" class="wd90p" readOnly/>
                                		<button class="btnDelPop" >삭제</button>
		                                <button class="btnSearchPop" id="subjSearchPop">검색</button>
                                </span>
                            </td><th scope="row"></th>
                            <td></td>                            
                            <!--
							<th scope="row"><label for="subjBizDcd">업무구분코드</label></th>
                            <td>
                                <select id="subjBizDcd" name="subjBizDcd" class="" >
                                	<option value="">전체</option>
                                	<option value="DIC">표준사전</option>
                                	<option value="R9P">R9연계물리모델</option>
                                	<option value="DDL">DDL생성</option>
					            </select>
                            </td>                             
                             -->
						</tr>
<!--
						<tr>
							<th scope="row"><label for="userNm">사용자명/사용자ID</label></th>
							<td><input type="text" id="userNm" name="userNm" class="wd200" value="<c:out value="${sessionScope.loginVO.isAdminYn!='Y'?sessionScope.loginVO.name:''}"/>"/></td>
							<th scope="row"><label for="deptNm">부서명</label></th>
							<td><input type="text" id="deptNm" name="deptNm" class="wd200"/></td>
						</tr> 
 -->						
<!-- 						<tr> -->
<!-- 	                        <th class="bd_none">기간</th> -->
<!-- 	                        <td class="bd_none"> -->
<!-- 	                        	<a href="#" class="tb_bt">1일</a> -->
<!-- 	                        	<a href="#" class="tb_bt">3일</a> -->
<!-- 	                        	<a href="#" class="tb_bt" id="seven">7일</a> -->
<!-- 	                        	<a href="#" class="tb_bt">1개월</a> -->
<!-- 	                        	<a href="#" class="tb_bt">3개월</a> -->
<!-- 	                        	<a href="#" class="tb_bt">6개월</a> -->
<!-- 	                        </td> -->
<!-- 	                        <th>조회기간</th> -->
<%--       		  				<td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" />  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}"/></td> --%>
<!--   						</tr> -->
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment"><s:message  code='ETC.COMM3' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqst.jsp" />
</div>

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "500px");</script>            
	</div>

	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area">
		    <div class="selected_title" id="dmn_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->

</div>


</body>
</html>