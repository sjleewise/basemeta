<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
            	<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->
			    <button class="btn_search" id="btnExec" 	name="btnExec"><s:message code="ANLY.EXCT" /></button> <!-- 분석실행 --> 
			    <button class="btn_search" id="btnPattenSearch" 	name="btnPattenSearch"><s:message code="DATA.PTRN" /></button> <!-- 데이터패턴 --> 
			    <button class="btn_search" id="btnLogSearch" 	name="btnPattenSearch"><s:message code="LOG" /></button> <!-- 로그 --> 
			    <button class="btn_search" id="btnSqlSearch" 	name="btnSqlSearch"><s:message code="SQL" /></button> <!-- 분석SQL -->
			    
			    <button class="btn_save" id="btnPrfSave" 	name="btnPrfSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			    <button class="btn_delete" id="btnPrfDelete" 	name="btnPrfDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
			    <button class="btn_save" id="btnRefresh" 	name="btnRefresh"><s:message code="REFRESH" /></button> <!-- 새로고침 --> 
			    
			</div>
			<div class="bt02">
			  <button class="btn_excel_down" id="poiDown" name="poiDown" style="display:none">Poi다운로드</button>
			  <button class="btn_excel_down" id="btnReport"    name="btnReport" style="display:none"><s:message code="REPORT" /></button> <!-- 보고서-->
	          <button class="btn_search" id="btnPrfSchRqst" name="btnPrfSchRqst"><s:message code="BNDL.REG" /></button> <!-- 일괄등록 -->                       
	          <button class="btn_excel_down" id="btnBrExcelDown" name="btnBrExcelDown" style="display:none"><s:message code="BZWR.RULE.CNVR.EXCL.DOWNLOAD" /></button> <!-- 업무규칙전환 엑셀내리기 -->                       
	          <button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	          <button class="btn_excel_down" id="btnPdfDown" name="btnPdfDown" style="display: none;">Pdf 내리기</button> <!-- 엑셀 내리기 -->
	          <button class="btn_excel_down" id="btnHwpDown" name="btnHwpDown" style="display: none;">한글 내리기</button> 
	    	</div>
        </div>	

