<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>프로그램담당자변경</title>


</head>
<body>

   <!-- 오른쪽 내용 시작 -->
  <div class="right">
  	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 나의업무 &gt; 프로그램담당자변경</div>
      <div class="stit">프로그램담당자변경</div>
      <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
          <caption>
          조회조건
          </caption>
          <colgroup>
              <col style="width:10%;">
              <col style="width:10%;">
              <col style="width:10%;">
              <col style="width:20%;">
              <col style="width:15%;">
              <col style="width:20%;">
          </colgroup>
        <tr>
          <th>기간</th>
          <td colspan="4"><input type="text" class="wd80"> 
              <a href="#"><img src='<c:url value="/img/icon_05.gif"/>' alt="날짜선택"></a>
               - <input type="text" class="wd80"> 
                 <a href="#"><img src='<c:url value="/img/icon_05.gif"/>' alt="날짜선택"></a>
          </td>
          <td rowspan="2">
          	<input type="button" value="검색">
          </td>
        </tr>
        <tr>
          <th>업무</th>
          <td>
	          <select name="">
	                	<option selected="" value="">전체</option>
	                    <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
	                    <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
	          </select>
          </td>
          <th>요청서</th>
          <td>
	          <select name="">
	                	<option selected="" value="">프로그램 개발/변경 의뢰서</option>
	                    <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
	                    <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
	          </select>
          </td>
          <td>
          	  <input type="radio" value="" style="width:10px">전체
          	  <input type="radio" value="" style="width:10px">작성
          	  <input type="radio" value="" style="width:10px">수신				
          </td>
        </tr>
      </table>

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
            <th>업무</th>
            <th>요청서</th>
            <th colspan="2">제목</th>
            <th>요청자</th>
            <th>요청날짜</th>
            <th>처리여부</th>
          </tr>
          <tr>
            <td>198</td>
            <td>품질포탈</td>
            <td colspan="2" class="link_txt"><a href="#">데이터관리지침데이터관리지침데이터관리지침데이터관리지침데이터관리지침</a></td>
            <td>2013.08.05</td>
            <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
            <td>홍길동</td>
           
          </tr>
          <tr>
            <td>198</td>
            <td>품질포탈</td>
            <td colspan="2" class="link_txt"><a href="#">데이터관리지침</a></td>
            <td>2013.08.05</td>
            <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
            <td>홍길동</td>
         
        </tr>
        <tr>
          <td>198</td>
          <td>품질포탈</td>
          <td colspan="2" class="link_txt"><a href="#">데이터관리지침</a></td>
          <td>2013.08.05</td>
          <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
          <td>홍길동</td>
         
        </tr>
        <tr>
          <td>198</td>
          <td>품질포탈</td>
          <td colspan="2" class="link_txt"><a href="#">데이터관리지침</a></td>
          <td>2013.08.05</td>
          <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
          <td>홍길동</td>
        
        </tr>
        <tr>
          <td>198</td>
          <td>품질포탈</td>
          <td colspan="2" class="link_txt"><a href="#">데이터관리지침</a></td>
          <td>2013.08.05</td>
          <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
          <td>홍길동</td>
       
        </tr>
        <tr>
          <td>198</td>
          <td>품질포탈</td>
          <td colspan="2" class="link_txt"><a href="#">데이터관리지침</a></td>
          <td>2013.08.05</td>
          <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
          <td>홍길동</td>
         
        </tr>
        <tr>
          <td>198</td>
          <td>품질포탈</td>
          <td colspan="2" class="link_txt"><a href="#">데이터관리지침</a></td>
          <td>2013.08.05</td>
          <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
          <td>홍길동</td>
      
        </tr>
        <tr>
          <td>198</td>
          <td>품질포탈</td>
          <td colspan="2" class="link_txt"><a href="#">데이터관리지침</a></td>
          <td>2013.08.05</td>
          <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
          <td>홍길동</td>
       
        </tr>
        <tr>
          <td>198</td>
          <td>품질포탈</td>
          <td colspan="2" class="link_txt"><a href="#">데이터관리지침</a></td>
          <td>2013.08.05</td>
          <td><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
          <td>홍길동</td>
        
        </tr>
        <tr>
          <td class="bd_none">198</td>
          <td class="bd_none">품질포탈</td>
          <td colspan="2" class="link_txt bd_none"><a href="#">제목입니다 제목입니다 제목입니다 제목입니다 제목입니다</a></td>
          <td class="bd_none">2013.08.05</td>
          <td class="bd_none"><img src='<c:url value="/img/icon_06.gif"/>' alt="첨부파일"></td>
          <td class="bd_none">홍길동</td>
         
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