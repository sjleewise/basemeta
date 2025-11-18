<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>테이블담당자변경</title>
<script type="text/javascript">

$(document).ready(function(){
	
	//그리드 초기화
	initgrid();
	
	//조회버튼 이벤트 처리
	$("#formBtn").click(function(){
// 		document.search.pageIndex.value = "1";
// 		$("form[name=search]").attr('action', '<c:url value="/portal/myjob/OwnShipCtrl.do"/>').submit();
		doAction("Search");
	});
	
	//엑셀 다운로드 이벤트 처리..
	$("#btnDownExcel").click(function(){
		//조회된 그리스 수 확인...
		var sel = $("#gridlist01").jqGrid('getGridParam','records');
		if(sel < 1)	{
			//alert("다운로드할 대상이 없습니다. 조회를 하세요.");
			showMsgBox("ERR", "<s:message code="ERR.EXCEL.DOWN" />");
			return;
		}
		doAction('DownExcel');
	});
	
	
	//변경버튼 이벤트 처리
	$("#formBtnUpdate").click(function(){
		
		//선택체크박스 확인
		var sel = $("#gridlist01").jqGrid('getGridParam','selarrrow');
		if(isBlankStr(sel))	{
			//alert("삭제할 대상을 선택하세요");
// 			showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
  		   	showMsgBox("ERR", "변경할 테이블을 선택 하세요!");
			return false;
		}
		
		if($("#mngUserIdAf").val() == ""){
			   showMsgBox("ERR", "변경후 담당자ID을 입력하세요!");
				return false;
		}
		if($("#mngUserAf").val() == "") {
			   showMsgBox("ERR", "변경후 담당자을 입력하세요!");
			   return false;
		}
		
		//변경 확인 메시지
		//alert("테이블 담당자를 변경하시겠습니까?");
		showMsgBox("CNF", "<s:message code="CNF.TABLE.UPDATE" />", 'Update');
		
// 		if($("input:checked[id=objId]").is(":checked") && $("#mngUserIdAf").val() != "" && $("#mngUserAf").val() != ""){
// 			$("form[name=search]").attr('action', '<c:url value="/portal/myjob/OwnShipUpdateCtrl.do"/>').submit();
// 		}else if($("input:checked[id=objId]").is(":checked") == false){
//   		   showMsgBox("ERR", "변경할 테이블을 선택 하세요!");
// 		   return false;
// 		}else if($("#mngUserIdAf").val() == ""){
// 		   showMsgBox("ERR", "변경후 담당자ID을 입력하세요!");
// 			return false;
// 		}else if($("#mngUserAf").val() == "") {
// 		   showMsgBox("ERR", "변경후 담당자을 입력하세요!");
// 		   return false;
// 		}else {
// 			return false;
// 		}
	});
	
	//팝업 이벤트
	$("#searchIdAf").click(function(){
		
		OpenWindow('<c:url value="/portal/pop/popUserSearch.do"/>', 'popupOwnerShip', '600px', '500px', 'yes');
		
	});
	
	// 게시글 상세조회
	$(".btn_subForm").click(function(){

		var idx = $(".btn_subForm").index(this);
 		getUrl(idx);
		
	});

	//체크박스 전체선택
	$("#allChk").click(function(){                 
	    if ($(this).is(":checked")){                
	       $(".objId").attr("checked", true);          //전체선택 체크된경우 
	      }else{
	       $(".objId").attr("checked", false);          //전체선택 체크 해제된경우
	      }
	 });
	
});

//프로그램 검색팝업 리턴값 처리
function returnUserPop(ret) {
//		alert(ret);
	//리턴 값을 json 형태로 변환 {name:insomnia, age:11}
	if(ret) {
		var obj = $.parseJSON(ret);
		$('form[name=search] input[name=mngUserIdAf]').val(obj.empNo);
		$('form[name=search] input[name=mngUserAf]').val(obj.userKnm);
		
	}
	
//		alert(obj.progrmFileNm);
//		alert(obj.progrmKoreanNm);
}

function doAction(action) {
	
	switch(action) {
	case 'Search':
// 		$('div#tabs-1').load('<c:url value="/portal/dashboard/ajaxgrid/ProgramList.do"/> div#tabs-1');
		var param = $("form[name=search]").serialize();
		$("#gridlist01").jqGrid('setGridParam', {
			url:'<c:url value="/portal/myjob/ajaxgrid/OwnShipList.do" />'+'?'+param,
// 			postData: param,
			datatype:'json',
		}).trigger("reloadGrid");
		break;
		
	case 'Update':
		var param = $("form[name=search]").serialize();
		var sel = $("#gridlist01").jqGrid('getGridParam','selarrrow');
		var urls = '<c:url value="/portal/myjob/ajaxgrid/OwnShipUpdate.do"/>';
		param += "&objIds="+sel;
		ajax2Json(urls, param, afterUpdateGrid);
		break;
		
	case 'DownExcel':
// 		alert('llll');
		//그리드 내용을 가져온다.
		var tbody = $("#gridlist01").clone(); //.html();
		//그리드 헤더내용을 가져온다.
		var thead = $("#grid_01 .ui-jqgrid-htable").clone(); //html();
		
		//그리드 헤더에서 순번, 체크박스 컬럼을 제거한다.
		//$('th:eq(0), th:eq(1)', thead).remove();
		//alert(thead.html());
		
		//그리드내용에서 첫번째 더미row를 제거한다.
		$('tr:eq(0)', tbody).remove();
		//그리드 내용에서 각 row별 순번, 체크박스 컬럼을 제거한다.
		/* $('tr', tbody).each(function (){
			$('td:eq(0), td:eq(1)', $(this)).remove();
		}); */
		//alert(tbody.html());
		
		var html = "<table border='1'>" + thead.html()  + tbody.html() + "</table>";
		
		var url = '<c:url value="/commons/excel/exceldown.do" />';
		$("#frmExcel input[name=excelhtml]").val(html);		//엑셀 내용...
		$("#frmExcel input[name=excelname]").val("결재대상.xls");	//엑셀 파일 명...
		$("#frmExcel").attr('action', url).submit();
		break;
	}
}

//데이터 저장후 후처리 함수
function afterUpdateGrid(data) {
	
	if(data.result > 0) {
		//테이블 담당자를 변경하였습니다.
		showMsgBox("INF", "<s:message code="MSG.TABLE" />");
		//그리드  다시 조회
		$("#formBtn").click();
	}else {
		//테이블 담당자 변경이 실패했습니다.
		showMsgBox("INF", "<s:message code="ERR.TABLE" />");
	}
	
}

function initgrid() {

	$("#gridlist01").jqGrid({
// 	    url:'<c:url value="/portal/myjob/ajaxgrid/dqJobList.do" />'+'?'+$("form[name=search]").serialize(),
// 	 	mtype:"POST",
		datatype: "json",
//	    	colNames:['<input type="checkbox" onclick="checkBox(event)" />', '프로그램파일명', '프로그램경로', '프로그램명', 'URL', '프로그램설명'],
	   	colNames:['테이블ID', '데이터모델', '상위주제영역', '주제영역', '테이블명', '테이블명(한글)', '담당자명', '담당자ID'],
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'objId'		 ,index:'objId'	 		, width:60,  sorttype:'text' , editable:false},
	   		{name:'dataModelNm'	 ,index:'dataModelNm'	, width:60,  sorttype:'text' , align:'center', editable:false},
	   		{name:'primarySubjNm',index:'primarySubjNm'	, width:80,  sorttype:'text' , align:'center', editable:false},
	   		{name:'subjNm'		 ,index:'subjNm'		, width:80,  sorttype:'text' , align:'center', editable:false},
	   		{name:'objEnm'		 ,index:'objEnm'		, width:120, sorttype:'text' , editable:false},
	   		{name:'objKnm'		 ,index:'objKnm'		, width:120, sorttype:'text' , editable:false},
	   		{name:'mngUser'		 ,index:'mngUser'		, width:50,  sorttype:'text' , align:'center', editable:false},
	   		{name:'mngUserId'	 ,index:'mngUserId'		, width:50,  sorttype:'text' , align:'center', editable:false},
	   	],
	    rowNum:100,
	   	rowList:[100,500,1000],
	   	pager: '#pager01',
//	    	pagerpos: 'center', 	//페이징 위치
//	    	pgbuttons: false, 		//페이징 버튼 유무
//	     	pginput: false, 			//페이징 인풋입력 유무
// 	   	rownumbers:true, 		//행 번호 표시 true/false, 전체 row의 줄번호(서버 데이터는 아니다..)를 보여줄까??
// 	   	rownumWidth:40, 		//줄번호 column의 사이즈..
//	 	emptyrecords: "조회결과가 없습니다.",
	   	sortname: 'objId',
	    viewrecords: true,
	    sortorder: "asc",
	    autowidth: true,
//	    width:$("#grid_01").width(),
	    height: 300 ,
//	    caption:"JSON Example" 
		jsonReader: {
			repeatitems: false, 	//서버에서 받은 data와 Grid 상의 column 순서를 맞출것인지?
	//	 	root:'rows', 			//서버의 결과 내용에서 데이터를 읽어오는 기준점
	//	 	page:'page', 			// 현재 페이지 currentPage
	//	 	total:'total', 			// 총 페이지 수 totalPage
	//	 	records:'records', 		// 보여지는 데이터 갯수(레코드) totalRecord
			id:'objId' 		// 키값, 꼭 화면 전체에서 겹치는 값이 없는 column을 설정할 것, 안그러면 에러 발생
		}, 
		loadonce: true,
		scroll: true,
		sortable: true,
		multiselect: true,
		onCellSelect : function(id, icol, cont) {
// 	 		alert("onCellSelect - " + id + ":" + icol + ":" + cont);
	 		if(icol == 5) {
	 			getUrl(id);
	 		}
		}
// 	   	onSelectRow: function(id){
// 	 		//alert("onSelectRow - " + id);
// 	 		//var id = jQuery("#list5").jqGrid('getGridParam','selrow');
// 	 		if(id){
// 	 			var ret = $("#gridlist01").jqGrid('getRowData',id);
// //	  			alert("id="+ret.id+" invdate="+ret.invdate+"...");
// 				//팝업창 호출
// 				inqire_notice(ret);
// 				//loadDetail(ret);
// 				//lastsel=id;
// 	 		} else { 
// 	 			//alert("Please select row");
// 	 		}
// //	  		return false;
// 		}

		
	});
	// $("#gridlist01").jqGrid('navGrid','#pager01',{edit:false,add:false,del:false});
	
}


//목록조회
function select_noticeList(pageNo) {
	document.search.pageIndex.value = pageNo;
	document.search.action = "<c:url value='/portal/myjob/OwnShipCtrl.do'/>";
	document.search.submit();
}

//게시물상세조회
function getUrl(id) {

	//var objId = $('form[name="subForm"] input[name="objId"]').eq(idx).val();
	var url = "<%=WiseConfig.URL_META %>biz/tble/tab/tableinfo_tab.jsp?ENTT_ID=" + id;
	OpenWindow(url,"w","1024px","400px","true");
	
}

</script>
</head>
<body>
        <!-- 오른쪽 내용 시작 -->
        <div class="right">
        	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 나의업무 &gt; 테이블담당자변경</div>
            <div class="stit">테이블담당자변경</div>
            
            <form name="search">
                <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
            <table style="width:99%;" border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
                <caption>
                조회조건
                </caption>
                <colgroup>
                    <col style="width:20%;">
                    <col style="width:15%;">
                    <col style="width:15%;">
                    <col style="width:20%;">
                    <col style="width:30%;">
                </colgroup>
	            <tr>
	                <th>변경후 담당자ID</th>
	                   <td><input type="text" name="mngUserIdAf" id="mngUserIdAf" style="width:100px;"></td>
	                <td>
	                   <a id="searchIdAf" class="tb_bt2" style="vertical-align:left;">검색</a> 
	                   <!-- <ul class="bt">
		            	  <li><a class="bt_gray" id="mngUserIdAf">조회</a></li>
		               </ul> -->
		            </td>   
	                <th>변경후 담당자</th>
	                <td><input type="text" name="mngUserAf" id="mngUserAf" style="width:100px;"></td> 
	            </tr>
	        </table>
	        </form>    
            <ul class="bt" style="padding:8px 5px 8px 0px;">
            	<li><a class="bt_gray" id="formBtn">조회</a></li>
                <li><a class="bt_gray" id="formBtnUpdate">변경</a></li>
            </ul>
            
            
        <div style="clear:both; height:5px;"><span></span></div>
	    <!-- 그리드 추가  -->
	    <div  id="grid_01" class="grid_01">
		    <table id="gridlist01"></table>
			<div id="pager01"></div>
	 	</div>
  </div>      
        <!-- 오른쪽 내용 끝 -->

<!-- 엑셀다운로드 -->
<form id="frmExcel" name="frmExcel" action="" method="post" >
	<input type="hidden" name="excelhtml" id="excelhtml">
	<input type="hidden" name="excelname" id="excelname">
</form>         
</body>
</html>