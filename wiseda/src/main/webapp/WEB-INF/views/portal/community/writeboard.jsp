<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>글쓰기</title>
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->

</head>
<body>
 <div class="right">
        	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 커뮤니티 &gt; 공통 &gt; 글쓰기</div>
            <div class="stit">글쓰기</div>
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

</body>
</html>