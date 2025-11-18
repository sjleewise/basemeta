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
	
		
	$("ul.bt li:last").click(function(){
		location.href = '<c:url value="/portal/commonWrite/CommonWriteCtrl.do"/>';
	});
	
	setDate();
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
	
//날짜관련
function setDate() {
	
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
	$("#set_date_update a").eq(2).click();
}


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
                <th>경로</th>
                <td colspan="3"><input type="text"></td>
              </tr>
              <tr>
                <th>검색구분</th>
                <td>
                <select name="">
                	<option selected="" value="">프로젝트명</option>
                    <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
                    <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
                </select>
                </td>
                <th>검색단어</th>
                <td><input type="text"></td>
              </tr>
              <tr>
                <th>등록자</th>
                <td><input type="text"></td>
                <th>등록일</th>
                <td>
                   <input id="startDate" name="startDate" type="text" class="wd80">  - <input id="endDate" name="endDate" type="text" class="wd80">
                </td>   
              </tr>
              <tr>
                <th class="bd_none">기간</th>
                <td colspan="3" class="bd_none" id="set_date_update">
                	<a href="#" class="tb_bt_select">1일</a>
                    <a href="#" class="tb_bt">3일</a>
                    <a href="#" class="tb_bt">7일</a>
                    <a href="#" class="tb_bt">1개월</a>
                    <a href="#" class="tb_bt">3개월</a>
                    <a href="#" class="tb_bt">6개월</a>
                </td>
              </tr>
            </table>
            
            <ul class="bt">
            	<li><a href="#" class="bt_gray">조회</a></li>
                <li><a href="#" class="bt_gray">글쓰기</a></li>
            </ul>
            
            
            <div class="bbs_num">[20/117]</div>
            <table border="0" cellspacing="0" cellpadding="0" class="tb_bbslist" summary="">
                <caption>
                게시판리스트
                </caption>
                <colgroup>
                    <col style="width:10%;">
                    <col style="width:10%;">
                    <col style="width:40%;">
                    <col style="width:10%;">
                    <col style="width:10%;">
                    <col style="width:10%;">
                    <col style="width:10%;">
                </colgroup>
              <tr>
                <th>NO</th>
                <th>프로젝트명</th>
                <th>제목</th>
                <th>작성일시</th>
                <th>첨부파일</th>
                <th>작성자</th>
                <th>조회수</th>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침데이터관리지침데이터관리지침데이터관리지침데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td>198</td>
                <td>품질포탈</td>
                <td class="link_txt"><a href="#">데이터관리지침</a></td>
                <td>2013.08.05</td>
                <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
                <td>홍길동</td>
                <td>1402</td>
              </tr>
              <tr>
                <td class="bd_none">198</td>
                <td class="bd_none">품질포탈</td>
                <td class="link_txt bd_none"><a href="#">제목입니다 제목입니다 제목입니다 제목입니다 제목입니다</a></td>
                <td class="bd_none">2013.08.05</td>
                <td class="bd_none"><img src='<c:url value="/img/icon_06.gif"/>'></td>
                <td class="bd_none">홍길동</td>
                <td class="bd_none">1402</td>
              </tr>
            </table>
            
            <div class="paging">
            	<a href="#" class="paging_navi">&lt; 이전페이지</a>
                <a href="#" class="paging_select">1</a>
                <a href="#" class="paging_num">2</a>
                <a href="#" class="paging_num">3</a>
                <a href="#" class="paging_num">4</a>
                <a href="#" class="paging_num">5</a>
                <a href="#" class="paging_navi">다음페이지 &gt;</a>
            </div>
            
        </div>
        <!-- 오른쪽 내용 끝 -->
        
</body>
</html>