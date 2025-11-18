<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>업무별프로그램변경</title> -->

<script type="text/javascript">
//최근 선택 row
// var lastsel;

$(document).ready(function(){
	initgrid2();
// 	$("#grid_02").css('height', 500);
	
// 	alert($("#grid_02").height());

	//업무별 프로그램변경 조회
	$("#btnProgramUpdateSearch").click(function(){
		doActionProgramUpdate('Search');
	});

	//엑셀 다운로드 이벤트 처리
	$("#btnProgramUpdateDown").click(function(){
		//조회된 그리스 수 확인...
		var sel = $("#gridlist02").jqGrid('getGridParam','records');
		if(sel < 1)	{
			//alert("다운로드할 대상이 없습니다. 조회를 하세요.");
			showMsgBox("ERR", "<s:message code="ERR.EXCEL.DOWN" />");
			return;
		}
		doActionProgramUpdate('DownExcel');
		
	});
	
	//달력 및 기간 이벤트 실행
	setDate2();
	
// 	$("#btnBon").click();
// 	doActionProgram('Search');
});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	$("#gridlist02").jqGrid('setGridWidth', $("#grid_02").width());        
});

function setDate2() {
	//달력...
	$( "#startDate" ).datepicker();
	$( "#endDate" ).datepicker();
	
	//기간 버튼 클릭 체크
	$("#set_date_update a").click(function(){
	  var btna = $("#set_date_update a");
	  var idx = btna.index(this);
	  btna.removeClass('tb_bt_select').addClass('tb_bt');
	  btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');
     	
	  setBetweenDtm( idx, $( "#startDate" ), $( "#endDate" ));
	});

	if($( "#startDate" ).val() == null || $( "#startDate" ).val() == '')
		$("#set_date_update a").eq(2).click();
}

function initgrid2() {

	$("#gridlist02").jqGrid({
//	    	url:'<c:url value="/js/jqgrid/test.json" />',
//	 	mtype:"POST",
		datatype: "json",
//	    	colNames:['<input type="checkbox" onclick="checkBox(event)" />', '프로그램파일명', '프로그램경로', '프로그램명', 'URL', '프로그램설명'],
	   	colNames:['', '업무명', '신규 수', '수정 수', '삭제 수',  '합계'],
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'keyId'		,index:'keyId'		, width:100,  sorttype:'text', editable:false, hidden:true},
	   		{name:'prName'		,index:'prName'		, width:100,  sorttype:'text', editable:false},
	   		{name:'cntNew'		,index:'cntNew'		, width:80,  sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntModify'	,index:'cntModify'	, width:80,  sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntDelete'	,index:'cntDelete'	, width:80, sorttype:'int' , align:'right', formatter:'integer', editable:false},
	   		{name:'cntTotal'	,index:'cntTotal'	, width:100, sorttype:'int' , align:'right', formatter:'integer', editable:false, 
	   				formatter : function(value, options, rData){
	   							var rowsum = eval(rData['cntNew']) + eval(rData['cntModify'])+ eval(rData['cntDelete']) ; 			
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
//	     width:$("#grid_02").width(),
	     height: "auto" ,
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
	        var cntNew 	  = $("#gridlist02").jqGrid('getCol', 'cntNew', false, 'sum');
	        var cntModify = $("#gridlist02").jqGrid('getCol', 'cntModify', false, 'sum');
	        var cntDelete = $("#gridlist02").jqGrid('getCol', 'cntDelete', false, 'sum');
	        var cntTotal  = $("#gridlist02").jqGrid('getCol', 'cntTotal', false, 'sum');
	        $("#gridlist02").jqGrid('footerData', 'set', { 'prName':'소계:', 'cntNew': cntNew, 'cntModify':cntModify, 'cntDelete':cntDelete, 'cntTotal':cntTotal });
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
			   $("#gridlist02").jqGrid('setGridParam',{datatype:'local'});
		}, */
//	  	onPaging : function(which_button) {
//	 		   $("#gridlist02").jqGrid('setGridParam',{datatype:'json'});
//	 		   $("#btnSearch").click();
//	 	}

		
	});
	// $("#gridlist02").jqGrid('navGrid','#pager01',{edit:false,add:false,del:false});
	
}

function doActionProgramUpdate(action) {
	
	switch(action) {
	case 'Search':
// 		$('div#tabs-1').load('<c:url value="/portal/dashboard/ajaxgrid/ProgramList.do"/> div#tabs-1');
		if($("#startDate").val() == "" ){
			showMsgBox("ERR", "시작일을 입력하세요!");
			return false;
		}else if($("#endDate").val() == ""){
        	showMsgBox("ERR", "종료일을 입력하세요!");
			return false;
		}
		
		var param = $("form[name=formProc]").serialize();
		
		$("#gridlist02").jqGrid('setGridParam', {
			url:'<c:url value="/portal/dashboard/ajaxgrid/ProgramUpdateList.do" />'+'?'+param,
// 			postData: param,
			datatype:'json'
		}).trigger("reloadGrid");
		break;
		
	case 'DownExcel':
// 		alert('llll');
		//그리드 내용을 가져온다.
		var tbody = $("#gridlist02").clone(); //.html();
		//그리드 헤더내용을 가져온다.
		var thead = $("#grid_02 .ui-jqgrid-htable").clone(); //html();
		
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
		$("#frmExcel input[name=excelname]").val("프로그램변경.xls");	//엑셀 파일 명...
		$("#frmExcel").attr('action', url).submit();
		break;
	}
}
</script>
<!-- </head> -->
<!-- <body> -->
         <form name="formProc">
	         <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
	           <tr class="tr_th">
	               <th class="bd_none">기간</th>
	               <td class="bd_none" id="set_date_update">
	                   <a class="tb_bt">1일</a>
	                   <a class="tb_bt">3일</a>
	                   <a class="tb_bt">7일</a>
	                   <a class="tb_bt">1개월</a>
	                   <a class="tb_bt">3개월</a>
	                   <a class="tb_bt">6개월</a>
	               </td>
	               <th>등록일</th>
	               <td>
	                  <input id="startDate" name="startDate" type="text" class="wd80" value="${ parammap.startDate}">  - <input id="endDate" name="endDate" type="text" class="wd80" value="${ parammap.endDate}">
	               </td>
	           </tr>
	         </table>
         </form> 
         <ul class="bt">
       	    <li><a class="bt_gray" id="btnProgramUpdateSearch" >조회</a></li>
           	<li><a class="bt_gray" id="btnProgramUpdateDown" >엑셀내리기</a></li>
         </ul>
         <div style="clear:both; height:5px;"><span></span></div>
	        <!-- 업무별프로그램변경 그리드  -->
	       	<div  id="grid_02" class="grid_02">
			    <table id="gridlist02"></table>
				<div id="pager02"></div>
    		</div>

<!-- </body> -->
<!-- </html> -->