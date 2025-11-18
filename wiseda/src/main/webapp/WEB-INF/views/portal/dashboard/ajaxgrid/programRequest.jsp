<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>업무별요청서집계</title> -->

<script type="text/javascript">
//최근 선택 row
// var lastsel;

$(document).ready(function(){
	initgrid3();
// 	$("#grid_03").css('height', 500);
	
// 	alert($("#grid_03").height());

	//업무별 프로그램변경 조회
	$("#btnProgramReqSearch").click(function(){
		doActionProgramRequest('Search');
	});

	//엑셀 다운로드 이벤트 처리
	$("#btnProgramReqDown").click(function(){
		//조회된 그리스 수 확인...
		var sel = $("#gridlist03").jqGrid('getGridParam','records');
		if(sel < 1)	{
			//alert("다운로드할 대상이 없습니다. 조회를 하세요.");
			showMsgBox("ERR", "<s:message code="ERR.EXCEL.DOWN" />");
			return;
		}
		doActionProgramRequest('DownExcel');
		
	});
	
	//달력 및 기간 이벤트 실행
	setDate3();
	
// 	$("#btnBon").click();
// 	doActionProgram('Search');
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	$("#gridlist03").jqGrid('setGridWidth', $("#grid_03").width());        
});

function setDate3() {
	
	//달력...
	$( "#startDateSub" ).datepicker();
	$( "#endDateSub" ).datepicker();
	
	//기간 버튼 클릭 체크
	$("#set_date_req a").click(function(){
	  var btna = $("#set_date_req a");
	  var idx = btna.index(this);
	  btna.removeClass('tb_bt_select').addClass('tb_bt');
	  btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');
	
	  setBetweenDtm( idx, $( "#startDateSub" ), $( "#endDateSub" ));
	});
	
	
	if($( "#startDateSub" ).val() == null || $( "#startDateSub" ).val() == '')
	$("#set_date_req a").eq(2).click();
}

function initgrid3() {

	$("#gridlist03").jqGrid({
//	    	url:'<c:url value="/js/jqgrid/test.json" />',
//	 	mtype:"POST",
		datatype: "json",
//	    	colNames:['<input type="checkbox" onclick="checkBox(event)" />', '프로그램파일명', '프로그램경로', '프로그램명', 'URL', '프로그램설명'],
	   	colNames:['업무명', '개발의뢰서', '시험점검표', '결과보고서', '분배(전체)', '분배(긴급)', '온라인서비스', '데이터베이스', 'FML요청서', '배치프로그램', '윈도우권한', '코드등록', '폐기요청서',  '합계'],
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'prName'		,index:'prName'		, width:100,  sorttype:'text', editable:false},
	   		{name:'cntRequest'	,index:'cntRequest'	, width:60,  sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntCklist'	,index:'cntCklist'	, width:60,  sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntReport'	,index:'cntReport'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntDistNormal',index:'cntDistNormal'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntDistUrgent',index:'cntDistUrgent'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntOnLine'	,index:'cntOnLine'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntDatabase'	,index:'cntDatabase', width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntFml'		,index:'cntFml'		, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntBatch'	,index:'cntBatch'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntWindow'	,index:'cntWindow'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntCode'		,index:'cntCode'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntWaste'	,index:'cntWaste'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntTotal'	,index:'cntTotal'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false, 
	   				formatter : function(value, options, rData){
	   							var rowsum = eval(rData['cntRequest']) + eval(rData['cntCklist'])+ eval(rData['cntReport'])+ eval(rData['cntDistNormal'])+ eval(rData['cntDistUrgent'])+ eval(rData['cntOnLine'])+ eval(rData['cntDatabase'])+ eval(rData['cntFml'])+ eval(rData['cntBatch'])+ eval(rData['cntWindow'])+ eval(rData['cntCode'])+ eval(rData['cntWaste']) ; 			
	   							return addThousandCommas(rowsum);
// 	   							rowsum = (''+rowsum).replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1,'); 
// 								return rowsum;
	   				}
			}		
	   	],
	    rowNum:100,
	   	rowList:[100,500,1000],
// 	   	pager: '#pager01',
//	    	pagerpos: 'center', 	//페이징 위치
//	    	pgbuttons: false, 		//페이징 버튼 유무
//	     	pginput: false, 			//페이징 인풋입력 유무
// 	   	rownumbers:true, 		//행 번호 표시 true/false, 전체 row의 줄번호(서버 데이터는 아니다..)를 보여줄까??
// 	   	rownumWidth:40, 		//줄번호 column의 사이즈..
//	 	emptyrecords: "조회결과가 없습니다.",
	   	sortname: 'prName',
	    viewrecords: true,
	    sortorder: "asc",
	    autowidth: true,
//	     width:$("#grid_03").width(),
	     height: 400 ,
//	     caption:"JSON Example" 
		jsonReader: {
			repeatitems: false, 	//서버에서 받은 data와 Grid 상의 column 순서를 맞출것인지?
//	 		root:'rows', 			//서버의 결과 내용에서 데이터를 읽어오는 기준점
//	 		page:'page', 			// 현재 페이지 currentPage
//	 		total:'total', 			// 총 페이지 수 totalPage
//	 		records:'records', 		// 보여지는 데이터 갯수(레코드) totalRecord
// 			id:'' 		// 키값, 꼭 화면 전체에서 겹치는 값이 없는 column을 설정할 것, 안그러면 에러 발생
		}, 
		loadonce: true,
		scroll: true,
		sortable: true,
		footerrow: true,
		userDataOnFooter: true,
		gridComplete: function() {
	        var cntRequest 	  = $("#gridlist03").jqGrid('getCol', 'cntRequest', false, 'sum');
	        var cntCklist 	= $("#gridlist03").jqGrid('getCol', 'cntCklist', false, 'sum');
	        var cntReport 	= $("#gridlist03").jqGrid('getCol', 'cntReport', false, 'sum');
	        var cntDistNormal = $("#gridlist03").jqGrid('getCol', 'cntDistNormal', false, 'sum');
	        var cntDistUrgent = $("#gridlist03").jqGrid('getCol', 'cntDistUrgent', false, 'sum');
	        var cntOnLine 	= $("#gridlist03").jqGrid('getCol', 'cntOnLine', false, 'sum');
	        var cntDatabase = $("#gridlist03").jqGrid('getCol', 'cntDatabase', false, 'sum');
	        var cntFml 		= $("#gridlist03").jqGrid('getCol', 'cntFml', false, 'sum');
	        var cntBatch 	= $("#gridlist03").jqGrid('getCol', 'cntBatch', false, 'sum');
	        var cntWindow 	= $("#gridlist03").jqGrid('getCol', 'cntWindow', false, 'sum');
	        var cntCode 	= $("#gridlist03").jqGrid('getCol', 'cntCode', false, 'sum');
	        var cntWaste 	= $("#gridlist03").jqGrid('getCol', 'cntWaste', false, 'sum');
	        var cntTotal  	= $("#gridlist03").jqGrid('getCol', 'cntTotal', false, 'sum');
	        $("#gridlist03").jqGrid('footerData', 'set', { 'prName':'소계:', 'cntRequest': cntRequest, 'cntCklist':cntCklist, 'cntReport':cntReport, 'cntDistNormal':cntDistNormal, 'cntDistUrgent':cntDistUrgent, 'cntOnLine':cntOnLine, 'cntDatabase':cntDatabase, 'cntFml':cntFml, 'cntBatch':cntBatch, 'cntWindow':cntWindow, 'cntCode':cntCode, 'cntWaste':cntWaste, 'cntTotal':cntTotal });
	    }
// 		multiselect: true,
	/* 	sortable: true,
	    loadComplete: function () {
	        var $self = $(this);
	        if ($self.jqGrid("getGridParam", "datatype") === "json") {
	            setTimeout(function () {
	                $(this).trigger("reloadGrid"); // Call to fix client-side sorting
	            }, 50);
	        }
	    } */
	/*     loadComplete : function () {
			   $("#gridlist03").jqGrid('setGridParam',{datatype:'local'});
		}, */
//	  	onPaging : function(which_button) {
//	 		   $("#gridlist03").jqGrid('setGridParam',{datatype:'json'});
//	 		   $("#btnSearch").click();
//	 	}

		
	});
	// $("#gridlist03").jqGrid('navGrid','#pager01',{edit:false,add:false,del:false});
	
}

function doActionProgramRequest(action) {
	
	switch(action) {
	case 'Search':
		if($("#startDateSub").val() == ""){
			showMsgBox("ERR", "시작일을 입력하세요!");
			return false;
		}else if($("#endDateSub").val() == ""){
			showMsgBox("ERR", "종료일을 입력하세요!");
			return false;
		}
		var param = $("form[name=formProcRe]").serialize();
		
		$("#gridlist03").jqGrid('setGridParam', {
			url:'<c:url value="/portal/dashboard/ajaxgrid/ProgramReqList.do" />'+'?'+param,
// 			postData: param,
			datatype:'json'
		}).trigger("reloadGrid");
		break;
		
	case 'DownExcel':
// 		alert('llll');
		//그리드 내용을 가져온다.
		var tbody = $("#gridlist03").clone(); //.html();
		//그리드 헤더내용을 가져온다.
		var thead = $("#grid_03 .ui-jqgrid-htable").clone(); //html();
		
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
		$("#frmExcel input[name=excelname]").val("업무별요청서집계.xls");	//엑셀 파일 명...
		$("#frmExcel").attr('action', url).submit();
		break;
	}
}
</script>
<!-- </head> -->
<!-- <body> -->
	     <form name="formProcRe">
	         <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
	           <tr class="tr_th">
	              <th class="bd_none">기간</th>
	              <td class="bd_none" id="set_date_req">
	                  <a class="tb_bt">1일</a>
	                  <a class="tb_bt">3일</a>
	                  <a class="tb_bt">7일</a>
	                  <a class="tb_bt">1개월</a>
	                  <a class="tb_bt">3개월</a>
	                  <a class="tb_bt">6개월</a>
	              </td>
	              <th>등록일</th>
	              <td>
	                 <input id="startDateSub" name="startDateSub" type="text" class="wd80" value="${parammap3.startDateSub }">  - <input id="endDateSub" name="endDateSub" type="text" class="wd80" value="${parammap3.endDateSub }">
	              </td>
	           </tr>
	         </table>
         </form>
         <ul class="bt">
       	    <li><a class="bt_gray" id="btnProgramReqSearch" >조회</a></li>
           	<li><a class="bt_gray" id="btnProgramReqDown" >엑셀내리기</a></li>
         </ul>
         <div style="clear:both; height:5px;"><span></span></div>
	        <!-- 업무별프로그램변경 그리드  -->
	       	<div  id="grid_03" class="grid_03">
			    <table id="gridlist03"></table>
				<div id="pager03"></div>
    		</div>

<!-- </body> -->
<!-- </html> -->