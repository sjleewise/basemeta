<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>최근 작업 내역조회</title>

<script type="text/javascript">


$(document).ready(function(){
	
	//그리드 초기화
	initgrid();
	
// 	$('table.tb_grid tr:even').addClass('tr_line');
	
	// 조회 버튼 Event Bind
	$("#formBtn").click(function(){
// 		document.frm.pageIndex.value = "1";
// 	 	$("#T1").attr('action', '<c:url value="/portal/myjob/recentWork.do"/>').submit(); 
// 		$('div#ajax_load_table').load('<c:url value="/portal/myjob/ajax/recentWork.do"/> div#ajax_load_table' , $('#T1').serialize(), function (){
// 			// 게시글 상세조회 Event Bind
// 			 $(".btn_subForm").click(function(){
// 		 		var idx = $(".btn_subForm").index(this);
// 				inqire_notice(idx); 
// 			});
// 		});
		
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
	
	// 게시글 상세조회 Event Bind
	/* $(".btn_subForm").click(function(){
 		var idx = $(".btn_subForm").index(this);
		inqire_notice(idx); 
	}); */
	//달력
	$( "#ntceBgnde" ).datepicker();
	$( "#ntceEndde" ).datepicker();
	
	//기간 버튼 클릭 체크
	$("#set_date_update a").click(function(){
	  var btna = $("#set_date_update a");
	  var idx = btna.index(this);
	  btna.removeClass('tb_bt_select').addClass('tb_bt');
	  btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');
	
	  setBetweenDtm( idx, $( "#ntceBgnde" ), $( "#ntceEndde" ));
	  
	 });
	$("#set_date_update a").eq(2).click();
	
// 	$("#formBtn").click();
	

});

//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	$("#gridlist01").jqGrid('setGridWidth', $("#grid_01").width());        
});

function doAction(action) {
	
	switch(action) {
	case 'Search':
// 		$('div#tabs-1').load('<c:url value="/portal/dashboard/ajaxgrid/ProgramList.do"/> div#tabs-1');
		var param = $("form[name=frm]").serialize();
		$("#gridlist01").jqGrid('setGridParam', {
			url:'<c:url value="/portal/myjob/ajaxgrid/recentWorkList.do" />'+'?'+param,
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
		$("#frmExcel input[name=excelname]").val("결재대상.xls");	//엑셀 파일 명...
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
	   	colNames:['요청서ID', '업무ID', '타겟ID', '업무명', '요청서',  '제목',  '요청자',  '요청날자', '처리여부'],
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'formId'		,index:'formId'	 	, width:60,  sorttype:'text' , align:'center', editable:false, hidden:true},
	   		{name:'prjId'		,index:'prjId'	 	, width:60,  sorttype:'text' , align:'center', editable:false, hidden:true},
	   		{name:'targetId'	,index:'targetId'	, width:60,  sorttype:'text' , align:'center', editable:false, hidden:true},
	   		{name:'prName'		,index:'prName'	 	, width:80,  sorttype:'text' , editable:false},
	   		{name:'formName'	,index:'formName'	, width:80,  sorttype:'text' , editable:false},
	   		{name:'reqSubj'		,index:'reqSubj'	, width:120,  sorttype:'text' , editable:false},
	   		{name:'usName'		,index:'usName'		, width:50,  sorttype:'text' , align:'center', editable:false},
	   		{name:'reqDate'		,index:'reqDate'	, width:60,  sorttype:'text' , align:'center', editable:false},
	   		{name:'statusNow'	,index:'statusNow'	, width:80,  sorttype:'text' , align:'center', editable:false},
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
	   	sortname: 'formName',
	    viewrecords: true,
	    sortorder: "asc",
	    autowidth: true,
//	     width:$("#grid_01").width(),
	     height: 300 ,
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
// 		multiselect: true,
	   	onSelectRow: function(id){
	 		//alert("onSelectRow - " + id);
	 		//var id = jQuery("#list5").jqGrid('getGridParam','selrow');
	 		if(id){
	 			var ret = $("#gridlist01").jqGrid('getRowData',id);
//	  			alert("id="+ret.id+" invdate="+ret.invdate+"...");
				//팝업창 호출
				inqire_notice(ret);
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

//목록조회
function select_noticeList(pageNo) {
//		document.frm.pageIndex.value = pageNo;
	document.frm.submit();
}

//게시물상세조회
function inqire_notice(ret) {
 	
	/* 새창열기 post 방식
	var idx = $(".btn_subForm").index(this);
    var url = getRequestUrl($('form[name="subForm"] input[name="formId"]').eq(idx).val(),idx);	
	var popUp = OpenWindow(url,"w","1024px","768px","true");
 	$('form[name="subForm"]').eq(idx).attr('action', url);
	$('form[name="subForm"]').eq(idx).attr("target", popUp);
	$('form[name="subForm"]').eq(idx).submit();
    */
	
    //새창열기 get 방식 
	var url = getRequestUrl(ret.formId, ret);	
    OpenWindow(url,"w","800px","1000px","true");
}

//요청서 내용 상세보기
function getRequestUrl(formId,ret)
{
	var url     = "http://10.1.60.58/form";

	if (formId == "0000000000000000")
	{
		url 		+= "/request/request_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}
	else if (formId == "0000000000000100") 
	{
   		url 		+= "/checklist/checklist_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}		
	else if (formId == "0000000000000110") 
	{
   		url 		+= "/windows/windows_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}
	else if (formId == "0000000000000120") 
	{
   		url 		+= "/online/online_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}		
	else if (formId == "0000000000000130") 
	{
   		url 		+= "/db/db_view_meta_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}		
	else if (formId == "0000000000000140") 
	{
   		url 		+= "/fml/fml_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}		
	else if (formId == "0000000000000200") 
	{
   		url 		+= "/report/report_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}	
	else if (formId == "0000000000000300") 
	{
   		url 		+= "/division/division_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}							
	else if (formId == "0000000000000320") 
	{
   		url 		+= "/batch/batch_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}				
	else if (formId == "0000000000000330") 
	{
   		url 		+= "/code/code_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}				
	else if (formId == "0000000000000400") 
	{
   		url 		+= "/reboot/reboot_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}				
	else if (formId == "0000000000000500") 
	{
   		url 		+= "/waste/waste_view_pop_ext.asp?selPR_ID="+ret.prjId+"&selREQ_ID="+ret.targetId+"&page_style=assist";
	}	
	return url;
}

</script>
</head>
<body>
        <!-- 오른쪽 내용 시작 -->
<!-- <div class="container"> -->
        <div style="width: 762px;">
<%--         	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 나의업무 &gt; 최근 작업 내역조회</div> --%>
<!--             <div class="stit">최근 작업 내역조회</div> -->
			<form id ="T1"  name="frm" action ="<c:url value='/portal/myjob/RecentWork.do'/>" method="post">
            <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
                <caption>
                조회조건
                </caption>
                <colgroup>
                    <col style="width:10%;">
                    <col style="width:40%;">
                    <col style="width:10%;">
                    <col style="width:40%;">
                </colgroup>
<%--               <tr>
                <th>업무</th>
                <td>
                <select name="workGB">
                	<option value="">전체</option>
                    <option value="1">선택1</option>
                    <option value="2">선택2</option>
                    <option value="3">선택3</option>
                </select>
                </td>
              	<th>요청서ID</th>
              	<td>
              		<input type="text" name="reqGB" class="wd80">
              	</td>
              </tr> --%>
                   <tr>
                 <th class="bd_none">기간</th>
                 <td class="bd_none" id="set_date_update">
                  <a href="#" class="tb_bt">1일</a>
                     <a href="#" class="tb_bt">3일</a>
                     <a href="#" class="tb_bt">7일</a>
                     <a href="#" class="tb_bt">1개월</a>
                     <a href="#" class="tb_bt">3개월</a>
                     <a href="#" class="tb_bt">6개월</a>
                 </td>
                 <th>요청일자</th>
                    <td><input id="ntceBgnde" name="ntceBgnde" type="text" class="wd80" value="${searchVO.ntceBgnde}">
                      - <input id="ntceEndde" name="ntceEndde" type="text" class="wd80" value="${searchVO.ntceEndde}">
               </tr>
            </table>
            </form>         
            <ul class="bt">
            	<li id="btn_read"><a id ="formBtn" class="bt_gray"><s:message code="BTN.READ"/></a></li>
            	<li><a class="bt_gray" id="btnDownExcel" >엑셀내리기</a></li>
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
				