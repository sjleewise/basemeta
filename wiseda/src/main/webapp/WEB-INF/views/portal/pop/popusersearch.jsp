<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>품질관리</title>
<script type="text/javascript">

$(document).ready(function(){
	
	//그리드 초기화
	initgrid();
	
// 	$(".resultTR").click(function(){
//  		var idx = $(".resultTR").index(this);
//  		opener.search.mngUserIdAf.value =  $('.empNO').eq(idx).text();
//  		opener.search.mngUserAf.value =  $('.userKnm').eq(idx).text();
 		
//  		window.close();
		
// 	});
	
	$("#formBtn").click(function(){
		
// 		document.search.pageIndex.value = "1";
// 		$("form[name=search]").attr('action', '<c:url value="/portal/pop/popUserSearch.do"/>').submit();
		
		doAction("Search");
	});
	
    $("#btnMsgBoxClose").click(function(){
		
	    window.close();
	});
	
	
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	$("#gridlist01").jqGrid('setGridWidth', $("#grid_01").width());        
});

function doAction(action) {
	
	switch(action) {
	case 'Search':
// 		$('div#tabs-1').load('<c:url value="/portal/dashboard/ajaxgrid/ProgramList.do"/> div#tabs-1');
		var param = $("form[name=search]").serialize();
		$("#gridlist01").jqGrid('setGridParam', {
			url:'<c:url value="/portal/pop/ajaxgrid/popUserSearch.do" />'+'?'+param,
// 			postData: param,
			datatype:'json',
		}).trigger("reloadGrid");
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
		$("#frmExcel input[name=excelname]").val("사용자정보.xls");	//엑셀 파일 명...
		$("#frmExcel").attr('action', url).submit();
		break;
	}
}


function initgrid() {

	$("#gridlist01").jqGrid({
// 	    url:'<c:url value="/portal/myjob/ajaxgrid/dqJobList.do" />'+'?'+$("form[name=search]").serialize(),
// 	 	mtype:"POST",
		datatype: "json",
//	    	colNames:['<input type="checkbox" onclick="checkBox(event)" />', '프로그램파일명', '프로그램경로', '프로그램명', 'URL', '프로그램설명'],
	   	colNames:['사용자ID', '사용자명', '부서', '사용자그룹'],
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'empNo'		,index:'empNo'	 	, width:60,  sorttype:'text' , align:'center',editable:false},
	   		{name:'userKnm'		,index:'userKnm'	, width:80,  sorttype:'text' , align:'center', editable:false},
	   		{name:'deptCd'		,index:'deptCd'		, width:120, sorttype:'text' , editable:false},
	   		{name:'userGroupId'	,index:'userGroupId', width:50 , sorttype:'text' , align:'center', editable:false},
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
	   	sortname: 'empNo',
	    viewrecords: true,
	    sortorder: "asc",
	    autowidth: true,
//	     width:$("#grid_01").width(),
	     height: 280 ,
//	     caption:"JSON Example" 
		jsonReader: {
			repeatitems: false, 	//서버에서 받은 data와 Grid 상의 column 순서를 맞출것인지?
//	 		root:'rows', 			//서버의 결과 내용에서 데이터를 읽어오는 기준점
//	 		page:'page', 			// 현재 페이지 currentPage
//	 		total:'total', 			// 총 페이지 수 totalPage
//	 		records:'records', 		// 보여지는 데이터 갯수(레코드) totalRecord
			id:'empNo' 		// 키값, 꼭 화면 전체에서 겹치는 값이 없는 column을 설정할 것, 안그러면 에러 발생
		}, 
		loadonce: true,
		scroll: true,
		sortable: true,
// 		multiselect: true,
	   	onSelectRow: function(id){
	 		//alert("onSelectRow - " + id);
	 		//var id = jQuery("#list5").jqGrid('getGridParam','selrow');
	 		if(id){
	 			var ret = $("#gridlist01").jqGrid('getRowData',id);
//	  			alert("id="+ret.id+" invdate="+ret.invdate+"...");
				//팝업창 호출
				returnPop(ret);
				//loadDetail(ret);
				//lastsel=id;
	 		} else { 
	 			//alert("Please select row");
	 		}
//	  		return false;
		}

		
	});
	// $("#gridlist01").jqGrid('navGrid','#pager01',{edit:false,add:false,del:false});
	
}

//팝업 리턴값 제공
function returnPop(ret) {
// 	alert(JSON.stringify(ret));
	opener.returnUserPop(JSON.stringify(ret));
	window.close();
}

//목록조회
// function select_noticeList(pageNo) {
// 	document.search.pageIndex.value = pageNo;
// 	document.search.action = '<c:url value="/portal/pop/popUserSearch.do"/>';
// 	document.search.submit();
// }

</script>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

</head>
<body>
<div class="pop_wrap">
	<div class="pop_top">
    	<div class="pop_tit_01">사용자 검색</div>
        <div class="pop_close" id="btnMsgBoxClose"><a href="javascript:closePop();"><span>X</span> 닫기</a></div>
    </div>
	<div class="pop_container">
    	<div class="pop_cont">
	           <form name="search">
	                <input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>
	            <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
	                <caption>
	                조회조건
	                </caption>
	                <colgroup>
	                    <col style="width:15%;">
	                    <col style="width:35%;">
	                    <col style="width:15%;">
	                    <col style="width:35%;">
	                </colgroup>
		            <tr>
		                <th>사용자ID</th>
		                <td><input type="text" name="empNo" id="empNo"></td>
		                <th>사용자명</th>
		                <td><input type="text" name="userKnm" id="userKnm"></td> 
		            </tr>
		        </table>
		        </form>    
	            <ul class="bt">
	            	<li><a class="bt_gray" id="formBtn">조회</a></li>
	                
	            </ul>
	            
	    <div style="clear:both; height:5px;"><span></span></div>
	    <!-- 그리드 추가  -->
	    <div  id="grid_01" class="grid_01">
		    <table id="gridlist01"></table>
			<div id="pager01"></div>
	 	</div>
		            
    </div>

	</div>
</div>
</body>
</html>