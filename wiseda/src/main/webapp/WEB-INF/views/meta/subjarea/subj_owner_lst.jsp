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
<title>주제영역권한 조회</title>
<script type="text/javascript">

EnterkeyProcess("Search");
$(document).ready(function() {
	// 조회 Event Bind
	$("#btnSearch").click(function(){ doAction("Search");  }).show();
	// 엑셀내리기 Event Bind
	$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
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
});


$(window).resize(function(){
	
});


function initGrid()
{
	
	with(grid_sheet){
		
		var cfg = {SearchMode:2,Page:100};
		SetConfig(cfg);
		
		var headtext  = "<s:message code='META.HEADER.SUBJ.OWNER.LST'/>";
		//No.|SUBJ_OWNER_ID|주제영역명|주제영역물리명|주제영역레벨|subjId|사용자ID|사용자명|부서명|요청일시|요청자ID|요청자명|요청번호|요청일련번호
		var headers = [
					{Text:headtext, Align:"Center"}
				];
		
		var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
		
		InitHeaders(headers, headerInfo); 

		var cols = [						
					{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	 Align:"Center", Edit:0},
					
// 					{Type:"Combo",   Width:150,  SaveName:"subjBizDcd",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:150,  SaveName:"subjOwnerId",  Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:400,  SaveName:"subjLnm",  Align:"Left", Edit:0},
					{Type:"Text",   Width:150,  SaveName:"subjPnm",  Align:"Left", Edit:0, Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"subjLvl",    Align:"Left", Edit:0 },
					{Type:"Text",   Width:150,  SaveName:"subjId",  Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"userId",  Align:"Left", Edit:0},
					{Type:"Text",   Width:120,  SaveName:"userNm",    Align:"Left", Edit:0 },
					{Type:"Text",   Width:120,  SaveName:"deptNm",    Align:"Left", Edit:0 },
					
					{Type:"Text",   Width:100,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:0},
					{Type:"Text",   Width:100,  SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:0},
					{Type:"Text",   Width:60,  SaveName:"rqstNo",  Align:"Center", Edit:0, Hidden:1}, 
					{Type:"Int",   Width:60,  SaveName:"rqstSno",  Align:"Center", Edit:0, Hidden:1}
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
function returnSubjPop (ret) {
	var retjson = jQuery.parseJSON(ret);
// 	$("#frmSearch #subjLnm").val(retjson.subjLnm);
//     console.log(retjson);
	$("#frmSearch #subjLnm").val(retjson.fullPath);
	$("#frmSearch #subjId").val(retjson.subjId);
}

function doAction(sAction)
{
		
	switch(sAction)
	{
			
		case "Search":
			var param = $("#frmSearch").serialize();
        	grid_sheet.DoSearch('<c:url value="/meta/subjarea/getsubjOnwerlist.do" />', param);
			break;			
	   
		case "Down2Excel":  //엑셀내려받기
			grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
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


</script>
</head>

<body>
<!-- 메뉴 메인 제목 Start-->
<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">주제영역권한 조회</div>
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
                            <td colspan="3">
                                <span class="input_file">
                                <input type="hidden" name="subjId" id="subjId" class="wd90p" readOnly/>
                                <input type="text" name="subjLnm" id="subjLnm" class="wd90p" readOnly/>
                                		<button class="btnDelPop" >삭제</button>
		                                <button class="btnSearchPop" id="subjSearchPop">검색</button>
                                </span>
                            </td>
<!-- 							<th scope="row"><label for="subjBizDcd">업무구분코드</label></th> -->
<!--                             <td> -->
<%--                                 <select id="subjBizDcd" name="subjBizDcd" class="" > --%>
<!--                                 	<option value="">전체</option> -->
<!--                                 	<option value="DIC">표준사전</option> -->
<!--                                 	<option value="R9P">R9연계물리모델</option> -->
<!--                                 	<option value="DDL">DDL생성</option> -->
<%-- 					            </select> --%>
<!--                             </td> -->
						</tr>
						<tr>
							<th scope="row"><label for="userNm">사용자명/사용자ID</label></th>
							<td><input type="text" id="userNm" name="userNm" class="wd200" value="<c:out value="${sessionScope.loginVO.isAdminYn!='Y'?sessionScope.loginVO.name:''}"/>"/></td>
							<th scope="row"><label for="deptNm">부서명</label></th>
							<td><input type="text" id="deptNm" name="deptNm" class="wd200"/></td>
						</tr>
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
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
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