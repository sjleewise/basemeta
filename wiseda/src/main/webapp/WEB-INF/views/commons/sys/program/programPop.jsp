<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title></title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

<script type="text/javascript">
//최근 선택 row
var poplastsel;

//그리드 이름 id
var popgridnm = "popgrid01";
$(document).ready(function(){
	
	initgrid();
	
	//달력팝업 추가...
	/* $( "#searchBgnDe" ).datepicker();
	$( "#searchEndDe" ).datepicker(); */
	
	
	//기간 버튼 클릭 체크
/* 	$(".bd_none a").click(function(){
		var btna = $(".bd_none a"); 
		var idx = btna.index(this);
		btna.removeClass('tb_bt_select').addClass('tb_bt');
		btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');

		//alert(idx);
		setBetweenDtm( idx, $( "#searchBgnDe" ), $( "#searchEndDe" ));
		
	}); */
	
	//추가 버튼...
// 	$('button.btn_rqst_new').unbind('click').click(function() {
// 		doAction('Add');
// 	});
	
	//조회 이벤트 처리
	$("#popSearch").click(function(){ 
		doAction('Search'); 
	});
	//적용 버튼 이벤트 처리
	$("#popApply").click(function(){
// 		alert("적용버튼");
		var id = jQuery("#"+popgridnm).jqGrid('getGridParam','selrow');
 		if(id){
 			var ret = $("#"+popgridnm).jqGrid('getRowData',id);
			returnPop(ret);
 		} else {
 			//선택한 row가 없네...
 			showMsgBox("ERR", "<s:message code="ERR.APPLY" />");
 			
 		}
	}).show();

	//폼 초기화 버튼 초기화...
	$('#popReset').click(function(event){
		event.preventDefault();
// 		alert("초기화버튼");
		$("form[name=frmPopSearch]")[0].reset();
	});
	
	//팝업 닫기 버튼 클릭 이벤트 - common.js 공통함수
	$('div.pop_tit_close').click(function(){
		
		self.close()	;
		
		//$('div#excel_pop', parent.document).dialog("close");
		
	});
	
	//추가 이벤트 처리
// 	$("#popNew").click(function(){
// 		doAction('Add');
// 		//var su = $("#"+popgridnm).jqGrid('addRow');
// // 		var su=$("#"+popgridnm).jqGrid('addRowData', 99, {});
// // 		alert(su);
// // 		$("#"+popgridnm).jqGrid();
// // 		if(su) alert("Succes. Write custom code to add data in server"); else alert("Can not update");
// 	});

	//삭제버튼 이벤트 처리
// 	$("#popDelete").click(function(){
// 		//선택체크박스 확인
// 		var sel = $("#"+popgridnm).jqGrid('getGridParam','selarrrow');
// 		if(isBlankStr(sel))	{
// 			//alert("삭제할 대상을 선택하세요");
// 			showMsgBox("ERR", "<s:message code="ERR.CHKDEL" />");
// 			return;
// 		}
		
// 		//삭제 확인 메시지
// 		//alert("삭제하시겠어요?");
// 		showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'Delete');
// 	});
	
	//엑셀 다운로드 버튼
	$("#popExcelDown").click(function(e){
		//조회된 그리스 수 확인...
		var sel = $("#"+popgridnm).jqGrid('getGridParam','records');
		if(sel < 1)	{
			//alert("다운로드할 대상이 없습니다. 조회를 하세요.");
			showMsgBox("ERR", "<s:message code="ERR.EXCEL.DOWN" />");
			return;
		}
		
		doAction('ExcelDown');
	    //e.preventDefault();

// 		var postd = $("#"+popgridnm).jqGrid('getGridParam', 'postData');
		//$("#"+popgridnm).jqGrid('excelExport', {url : ''});
		//alert(postd);
	}).hide();
	
	//엔터키 이벤트 설정 ("Search" - doAction('Search') 실행, 함수명 제공시 함수호출)
	EnterkeyProcess('Search');
// 	EnterkeyProcess();

	
});



//화면 재조정시 그리드 사이즈 조정...
$(window).resize(function(){
	$("#"+popgridnm).jqGrid('setGridWidth', $("#popgrid_01").width());        
});


function initgrid() {

	$("#"+popgridnm).jqGrid({
//	    	url:'<c:url value="/js/jqgrid/test.json" />',
//	 	mtype:"POST",
		datatype: "json",
//	    	colNames:['<input type="checkbox" onclick="checkBox(event)" />', '프로그램파일명', '프로그램경로', '프로그램명', 'URL', '프로그램설명'],
	   	colNames:[<s:message code='COMMON.HEADER.PROGRAMLIST'/>],
	   	/* '프로그램명', '저장경로', '프로그램한글명', 'URL', '프로그램설명' */
	   	colModel:[
//	    		{name:'chkbox',index:'chkbox', width:55, sortable:false, edittype:'checkbox', editoptions: { value:"Yes:No" }, editable:true},
	   		{name:'progrmFileNm',index:'progrmFileNm', width:55, sorttype:'text', editable:true},
	   		{name:'progrmStrePath',index:'progrmStrePath', width:55, sortable:false, hidden:true, editable:true},
	   		{name:'progrmKoreanNm',index:'progrmKoreanNm', width:90, sorttype:'text', editable:true},
	   		{name:'url',index:'url', width:100, sorttype:'text', editable:true, hidden:true},
	   		{name:'progrmDc',index:'progrmDc', width:150, sortable:false, editable:true, hidden:true,}		
	   	],
	    rowNum:100,
	   	rowList:[100,500,1000],
	   	pager: '#poppager01',
//	    	pagerpos: 'center', 	//페이징 위치
//	    	pgbuttons: false, 		//페이징 버튼 유무
//	     	pginput: false, 			//페이징 인풋입력 유무
	   	rownumbers:true, 		//행 번호 표시 true/false, 전체 row의 줄번호(서버 데이터는 아니다..)를 보여줄까??
	   	rownumWidth:40, 		//줄번호 column의 사이즈..
//	 	emptyrecords: "조회결과가 없습니다.",
	   	sortname: 'progrmFileNm',
	    viewrecords: true,
	    sortorder: "desc",
	    autowidth: true,
//	     width:$("#grid_01").width(),
//	     height:"auto",
//	     caption:"JSON Example" 
		jsonReader: {
			repeatitems: false, 	//서버에서 받은 data와 Grid 상의 column 순서를 맞출것인지?
//	 		root:'rows', 			//서버의 결과 내용에서 데이터를 읽어오는 기준점
//	 		page:'page', 			// 현재 페이지 currentPage
//	 		total:'total', 			// 총 페이지 수 totalPage
//	 		records:'records', 		// 보여지는 데이터 갯수(레코드) totalRecord
			id:'progrmFileNm' 		// 키값, 꼭 화면 전체에서 겹치는 값이 없는 column을 설정할 것, 안그러면 에러 발생
		}, 
		loadonce: true,
		scroll: 1,
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
			   $("#"+popgridnm).jqGrid('setGridParam',{datatype:'local'});
		}, */
//	  	onPaging : function(which_button) {
//	 		   $("#"+popgridnm).jqGrid('setGridParam',{datatype:'json'});
//	 		   $("#btnSearch").click();
//	 	}, 
// 	   	onSelectRow: function(id){
	   		//return false;
	 		//alert("onSelectRow - " + id);
	 		//var id = jQuery("#list5").jqGrid('getGridParam','selrow');
	 		/* if(id && id!==poplastsel){
	 			var ret = $("#"+popgridnm).jqGrid('getRowData',id);
//	  			alert("id="+ret.id+" invdate="+ret.invdate+"...");
				loadDetail(ret);
				poplastsel=id;
	 		} else { 
	 			//alert("Please select row");
	 		} */
//	  		return false;
	 		
	/* 		if(id && id!==poplastsel){
				$('#gridlist01').jqGrid('saveRow', lastsel, false, 'clientArray');
				$('#gridlist01').jqGrid('editRow', id, true, null, null, 'clientArray');
				lastsel=id;
			} */
// 		},
		// 그리드 더블 클릭 시 이벤트
		ondblClickRow : function(rowid) {
			// 선택한 내용 return
// 			alert("click dbl :"+rowid);
			if(rowid) {
				var ret = $("#"+popgridnm).jqGrid('getRowData', rowid);
				returnPop(ret);
			}
		}
// 		onCellSelect : function(id, icol, cont) {
//	 		alert("onCellSelect - " + id + ":" + icol + ":" + cont);
// 		}
		
		//editurl: ""
	});
	// $("#"+popgridnm).jqGrid('navGrid','#poppager01',{edit:false,add:false,del:false});
}

function doAction(action) {
	switch(action) {
	
	case 'Search' :	//조회
// 		var param = $('form[name=frmPopSearch]').serializeArray();
		var param = $('form[name=frmPopSearch]').serialize();
// 		alert(param);
// 		$("#"+popgridnm).jqGrid('clearGridData'); // 이전 데이터 삭제
		$("#"+popgridnm).jqGrid('setGridParam', {
			url:'<c:url value="/commons/sys/program/ajaxgrid/selectProgramList.do" />'+'?'+param,
// 			postData: param,
			datatype:'json'
		}).trigger("reloadGrid");
		break;
	case 'Detail' : //상세 정보
		//onSelectRow 그리드 함수에서 처리...
		break;
	case 'Add' : //신규 추가
		loadDetail();
		break;
	case 'SaveRow': //단건 저장
		//saction (I-입력, U-수정)
		var urls = '<c:url value="/commons/sys/program/ajaxgrid/saveProgram.do"/>';
		var param = $('form[name=frmInput]').serialize();
		ajax2Json(urls, param, aftersaveGrid);
		
		break;
	case 'Delete' : //삭제
		var sel = $("#"+popgridnm).jqGrid('getGridParam','selarrrow');
		//url 호출
		var urls = '<c:url value="/commons/sys/program/ajaxgrid/deleteProgram.do"/>';
		var param = "progrmFileNm="+sel;
		ajax2Json(urls, param, afterDelGrid);
		break;
	case 'ExcelDown' : //엑셀다운로드
		//그리드 내용을 가져온다.
		var tbody = $("#"+popgridnm).clone(); //.html();
		//그리드 헤더내용을 가져온다.
		var thead = $(".ui-jqgrid-htable").clone(); //html();
		
		//그리드 헤더에서 순번, 체크박스 컬럼을 제거한다.
		$('th:eq(0), th:eq(1)', thead).remove();
		//alert(thead.html());
		
		//그리드내용에서 첫번째 더미row를 제거한다.
		$('tr:eq(0)', tbody).remove();
		//그리드 내용에서 각 row별 순번, 체크박스 컬럼을 제거한다.
		$('tr', tbody).each(function (){
			$('td:eq(0), td:eq(1)', $(this)).remove();
		});
		//alert(tbody.html());
		
		var html = "<table border='1'>" + thead.html()  + tbody.html() + "</table>";
		
		var url = '<c:url value="/commons/excel/exceldown.do" />';
		$("#frmExcel input[name=excelhtml]").val(html);		//엑셀 내용...
		$("#frmExcel input[name=excelname]").val("<s:message code='PGM.LST.XLS' />");	//엑셀 파일 명... /* 프로그램리스트.xls */
		$("#frmExcel").attr('action', url).submit();
		
		//tableToExcel('gridlist01', 'test');
		//window.open('data:application/vnd.ms-excel' , html);
    	//e.preventDefault();

	break;

	}
}


//팝업 리턴값 제공
function returnPop(ret) {
// 	alert(JSON.stringify(ret));
	opener.returnProgramPop(JSON.stringify(ret));
	window.close();
}

//상세정보호출
function loadDetail(param) {
	$('div#detailInfo').load('<c:url value="/commons/sys/program/ajaxgrid/selectProgramDetail.do"/>', param, function(){});

}

//데이터 저장후 후처리 함수
function aftersaveGrid(data) {
// 	alert(data.result);
	if(data.result > 0) {
		//alert("저장성공");
		//var message = "<s:message code="MSG.SAVE" />";
		showMsgBox("INF", "<s:message code="MSG.SAVE" />");
		
		//그리드에 추가 또는 업데이트
		if("I" == data.saction) {
			$("#"+popgridnm).addRowData(data.resultVO.progrmFileNm , data.resultVO, 'first');
		} else if ("U" == data.saction) {
			$("#"+popgridnm).setRowData(data.resultVO.progrmFileNm, data.resultVO);
// 			$("#"+popgridnm).addRowData("1", myfirstrow);
		}
		
		//상세정보 초기화
		loadDetail();
	} else {
		//alert("저장실패");
		showMsgBox("ERR", "<s:message code="ERR.SAVE" />");
	}
}

//삭제 후 처리함수
function afterDelGrid(data) {
	//alert(data.result);
	if(data.result > 0) {
		showMsgBox("INF", "<s:message code="MSG.DEL" />");
		//리스트 다시 조회
		$("#btnSearch").click();
		//입력 폼 초기화
		loadDetail();
	} else {
		showMsgBox("ERR", "<s:message code="ERR.DEL" />");
	}
}


</script>
</head>
<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="PGM.INQ" /></div> <!-- 프로그램조회 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
   	<div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
	<form name="frmPopSearch" action ="" method="post">
	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>
<%--       <tr>
        <th>구분</th>
        <td>
        	<select id="cateCode" name="cateCode">
        		<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
         	<c:forEach var="code" items="${codeMap.cateCode}" varStatus="status">
             <option value="${code.codeCd}">${code.codeLnm}</option>
             </c:forEach>
        	</select>
        </td>
        <th>게시물검색</th>
        <td>
	        <select  id="searchCnd" name="searchCnd" title="검색조건선택">
	        	<option value="0">제목</option>
	            <option value="1">내용</option>
	            <option value="2">작성자</option>
	        </select>
        <input type="text" name="searchWrd" value="${searchVO.searchWrd}" title="검색어 입력" style="display: inline; width: 150px;">
        </td>
      </tr>
      <tr>
        <th class="bd_none">기간</th>
        <td class="bd_none">
        	<a href="#" class="tb_bt">1일</a>
            <a href="#" class="tb_bt">3일</a>
            <a href="#" class="tb_bt">7일</a>
            <a href="#" class="tb_bt">1개월</a>
            <a href="#" class="tb_bt">3개월</a>
            <a href="#" class="tb_bt">6개월</a>
        </td>
        <th>등록일</th>
      		   <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80" value="${searchVO.searchBgnDe}" >  - <input id="searchEndDe" name="searchEndDe" type="text" class="wd80" value="${searchVO.searchEndDe}">
      </tr> --%>
      <tr>
      	<th><s:message code="PGM.NM" /></th> <!-- 프로그램명 -->
      	<td>
      		<input type="text" name="searchWrd" value="${searchVO.searchWrd}">
      	</td>
      </tr>
    </table>
    </div>
    </form>
    <div style="clear:both; height:10px;"><span></span></div>
    
     <!-- 조회버튼영역  -->
    <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
     <!-- 조회버튼영역  -->
</div>    
    <div style="clear:both; height:10px;"><span></span></div>
  	
  	<!-- 그리드 입력 입력 -->
    <div  id="popgrid_01" >
    <table id="popgrid01"></table>
	<div id="poppager01"></div>
    </div>
   <!-- 그리드 입력 입력 -->
   
    <div style="clear:both; height:10px;"><span></span></div>
	
	<div id="detailInfo"></div>
   
</div>
</div>
<form id="frmExcel" name="frmExcel" action="" method="post" >
	<input type="hidden" name="excelhtml" id="excelhtml">
	<input type="hidden" name="excelname" id="excelname">
</form>

</body>
</html>