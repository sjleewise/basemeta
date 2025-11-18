<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
            	
			    <button class="btn_search" id="btnSearch" name="btnSearch" style="display:none"><s:message code="INQ"/></button> <!-- 조회 --> 
			    <button class="btn_rqst_new" id="btnTreeNew" name="btnTreeNew" style="display:none"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
				<ul class="add_button_menu" id="addTreeMenu" style="display:none;">
					<li id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 -->
				    <li id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 업로드 -->
				</ul>
				<button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete" style="display:none;"><s:message code="DEL" /></button> <!-- 삭제 -->
			    <button class="btn_delete" id="btnSend" 	name="btnSend" style="display: none;">연계서버접속정보 등록</button>
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="poiDown"    name="poiDown" style="display:none">품질평가용 자료 내려받기</button> <!-- POI down-->
				<button class="btn_excel_down" id="poiDown2"    name="poiDown2" style="display:none">범정부연계 자료 전송</button> <!-- POI down-->
			    <button class="btn_excel_down" id="btnReport"    name="btnReport" style="display:none"><s:message code="REPORT" /></button> <!-- 보고서-->
			    <button class="btn_excel_down" id="tblDefDown"    name="tblDefDown" style="display:none">테이블정의서 다운로드</button> <!-- 테이블정의서 down-->
			    <button class="btn_excel_down" id="colDefDown"    name="colDefDown" style="display:none">컬럼정의서 다운로드</button> <!-- 컬럼정의서 down-->
			    <button class="btn_save" id="btnCreStnd" name="btnCreStnd" style="display:none">표준자동생성</button> <!-- 엑셀 내리기 -->
				<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown" style="display:none"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	

