<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>산출물관리</title>

<script type="text/javascript">

$(document).ready(function(){
	
		// 조회 Event Bind
// 		$("#btnSearch").click(function(){ doAction("Search");  });
				
		// 엑셀내리기 Event Bind
// 		$("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
	
});



$(window).resize(function(){
                
        // Window Resize 시  컬럼  Width%
//         var interval = "5|10|10|10|15|15|35";
        
//         chgSize(mySheet, interval);
});


function doAction(sAction)
{
		
	switch(sAction)
    {
	    case "Search":        //작성완료
	    	var param = "";
	    	param = FormQueryString(document.search);
	    	param += "&pageSize=500";
	    	mySheet.DoSearch("modeltbl_lst.do/selectModelTblList", param);
	    	/*
	    	  var paramInfo = {PageParam: "pageNo", Param: param};
	    	  mySheet.DoSearchPaging("modeltbl_lst.do/selectModelTblList", paramInfo);	    	
	    	*/
			break;
	   
	    case "Down2Excel":	//엑셀내려받기
			mySheet.Down2Excel({HiddenColumn:1, Merge:1});
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
/* function mySheet_OnDblClick(row, col, value, cellx, celly) {
	
}

function mySheet_OnClick(row, col, value, cellx, celly) {
	$("#hdnRow").val(row);
	if(row < 1) return;
} */

</script>
</head>
<body>
        <!-- 오른쪽 내용 시작 -->
        <div class="right">
        	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 개발가이드 &gt; 산출물관리</div>
            <div class="stit">산출물관리</div>
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
                <th>프로젝트명</th>
                <td><input type="text"></td>
                <th>시스템정보</th>
                <td><input type="text"></td>
              </tr>
              <tr>
                <th>프로젝트기간</th>
                 <td><input type="text" class="wd80"> <a href="#"><img src='<c:url value="/img/icon_05.gif"/>' alt="날짜선택"></a> - <input type="text" class="wd80"> <a href="#"><img src='<c:url value="/img/icon_05.gif"/>' alt="날짜선택"></a></td>
              </tr>
              <tr>
                <th>PM명</th>
                <td><input type="text"></td>
                <th>연락처</th>
                <td><input type="text"></td>
              </tr>
              <tr>
                <th>등록자</th>
                <td>홍길동</td>
                <th>등록일</th>
                <td>2013.09.02</td>
              </tr>
              <tr>
                <th>제목</th>
                <td colspan="3"><input type="text"></td>
              </tr>
              <tr>
                <th>내용</th>
                <td colspan="3"><textarea id="" name="" accesskey="" style="width:96%; height:200px;"></textarea></td>
              </tr>
              <tr>
                <th class="bd_none">산출물첨부</th>
                <td colspan="3" class="bd_none">
                	sddsd<br><br><br>asdas
                </td>
              </tr>
            </table>
            
            <ul class="bt">
            	<li><a href="#" class="bt_gray">목록</a></li>
                <li><a href="#" class="bt_gray">저장</a></li>
            </ul> 
            
        </div>
        <!-- 오른쪽 내용 끝 -->
</body>
</html>