<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>프로그램보유현황</title> -->

<script type="text/javascript">
//최근 선택 row
// var lastsel;

$(document).ready(function(){
	initgrid();
// 	$("#grid_01").css('height', 500);
	
// 	alert($("#grid_01").height());

	$("#btnBon").click(function(){
		doActionProgram('Search');
	});

	//엑셀 다운로드 이벤트 처리
	$("#btnDownBon").click(function(){
		//조회된 그리스 수 확인...
		var sel = $("#gridlist01").jqGrid('getGridParam','records');
		if(sel < 1)	{
			//alert("다운로드할 대상이 없습니다. 조회를 하세요.");
			showMsgBox("ERR", "<s:message code="ERR.EXCEL.DOWN" />");
			return;
		}
		doActionProgram('DownExcel');
		
	});
	
// 	$("#btnBon").click();
// 	doActionProgram('Search');
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	$("#gridlist01").jqGrid('setGridWidth', $("#grid_01").width());        
});

function initgrid() {

	$("#gridlist01").jqGrid({
//	    	url:'<c:url value="/js/jqgrid/test.json" />',
//	 	mtype:"POST",
		datatype: "json",
//	    	colNames:['<input type="checkbox" onclick="checkBox(event)" />', '프로그램파일명', '프로그램경로', '프로그램명', 'URL', '프로그램설명'],
	   	colNames:['업무명', 'CLIENT 수', 'SERVICE 수', 'BATCH 수', 'FUNCTION 수', 'JSP 수', 'JAVA 수', '기타 수', '폐기 수', '합계'],
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'prName'		,index:'prName'		, width:100,  sorttype:'text', editable:false},
	   		{name:'cntClient'	,index:'cntClient'	, width:60,  sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntService'	,index:'cntService'	, width:60,  sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntBatch'	,index:'cntBatch'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntFunc'		,index:'cntFunc'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},		
	   		{name:'cntJsp'		,index:'cntJsp'		, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},		
	   		{name:'cntJava'		,index:'cntJava'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},		
	   		{name:'cntEtc'		,index:'cntEtc'		, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},		
	   		{name:'cntWaste'	,index:'cntWaste'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false},		
	   		{name:'cntTotal'	,index:'cntTotal'	, width:60, sorttype:'int' , align:'right', formatter:'integer', editable:false, 
	   				formatter : function(value, options, rData){
	   							var rowsum = eval(rData['cntClient']) + eval(rData['cntService'])+ eval(rData['cntBatch'])+ eval(rData['cntFunc'])+ eval(rData['cntJsp'])+ eval(rData['cntJava'])+ eval(rData['cntEtc'])+ eval(rData['cntWaste']) ; 			
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
//	     width:$("#grid_01").width(),
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
	        var cntClient = $("#gridlist01").jqGrid('getCol', 'cntClient', false, 'sum');
	        var cntService = $("#gridlist01").jqGrid('getCol', 'cntService', false, 'sum');
	        var cntBatch = $("#gridlist01").jqGrid('getCol', 'cntBatch', false, 'sum');
	        var cntFunc = $("#gridlist01").jqGrid('getCol', 'cntFunc', false, 'sum');
	        var cntJsp = $("#gridlist01").jqGrid('getCol', 'cntJsp', false, 'sum');
	        var cntJava = $("#gridlist01").jqGrid('getCol', 'cntJava', false, 'sum');
	        var cntEtc = $("#gridlist01").jqGrid('getCol', 'cntEtc', false, 'sum');
	        var cntWaste = $("#gridlist01").jqGrid('getCol', 'cntWaste', false, 'sum');
	        var cntTotal = $("#gridlist01").jqGrid('getCol', 'cntTotal', false, 'sum');
	        $("#gridlist01").jqGrid('footerData', 'set', { 'prName':'합계:', 'cntClient': cntClient, 'cntService':cntService, 'cntBatch':cntBatch, 'cntFunc':cntFunc, 'cntJsp':cntJsp, 'cntJava':cntJava, 'cntEtc':cntEtc, 'cntWaste':cntWaste, 'cntTotal':cntTotal });
// 	        $('table.ui-jqgrid-ftable tr td').css('background-color', '#e6f3fe');
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
			   $("#gridlist01").jqGrid('setGridParam',{datatype:'local'});
		}, */
//	  	onPaging : function(which_button) {
//	 		   $("#gridlist01").jqGrid('setGridParam',{datatype:'json'});
//	 		   $("#btnSearch").click();
//	 	}

		
	});
	// $("#gridlist01").jqGrid('navGrid','#pager01',{edit:false,add:false,del:false});
	
}

function doActionProgram(action) {
	
	switch(action) {
	case 'Search':
// 		$('div#tabs-1').load('<c:url value="/portal/dashboard/ajaxgrid/ProgramList.do"/> div#tabs-1');
		var param="";
		$("#gridlist01").jqGrid('setGridParam', {
			url:'<c:url value="/portal/dashboard/ajaxgrid/ProgramList.do" />'+'?'+param,
// 			postData: param,
			datatype:'json'
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
		$("#frmExcel input[name=excelname]").val("프로그램본수.xls");	//엑셀 파일 명...
		$("#frmExcel").attr('action', url).submit();
		break;
	}
}
</script>
<!-- </head> -->
<!-- <body> -->
	  	 <form name="formBon">
            <ul class="bt">
            	<li><a class="bt_gray" id="btnBon" >조회</a></li>
            	<li><a class="bt_gray" id="btnDownBon" >엑셀내리기</a></li>
            </ul>
         </form> 
         <div style="clear:both; height:5px;"><span></span></div>
	        <!-- 프로그램본수 그리드  -->
	       	<div  id="grid_01" class="grid_01">
			    <table id="gridlist01"></table>
				<div id="pager01"></div>
    		</div>

<!-- </body> -->
<!-- </html> -->