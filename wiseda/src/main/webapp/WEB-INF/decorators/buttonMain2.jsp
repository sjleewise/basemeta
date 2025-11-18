<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> 
			    <button class="btn_search" id="btnNew" 		name="btnNew" style="display:none"><s:message code="REG" /></button> <!-- 등록 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete" style="display:none"><s:message code="DEL" /></button> <!-- 삭제 --> 
			</div>
			<div class="bt02">
	          <button class="btn_excel_down" id="btnExcelLoad" name="btnExcelLoad" style="display:none"><s:message code="EXCEL.UPLOAD" /></button> <!-- 업로드 -->                       
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown" style="display:none"><s:message code="EXCEL.DOWNLOAD" /></button> <!-- 다운로드 -->                       
	    	</div>
        </div> 

