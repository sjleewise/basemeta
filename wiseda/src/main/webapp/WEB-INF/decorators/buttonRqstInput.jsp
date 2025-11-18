<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<!-- 조회버튼영역   중앙 정렬 style="margin:0 auto; width:XXXpx;버튼 픽셀크기 -->
		<div id="divInputBtn" style="text-align: center; display: none;">
           <button class="btn_save" type="button" id="btnGridSave" name="btnGridSave"><s:message code="STRG" /></button> <!-- 저장 --> 
           <button class="btn_save" type="button" id="btnReset" name="btnReset"><s:message code="INON" /></button> <!-- 초기화 -->
        </div>
