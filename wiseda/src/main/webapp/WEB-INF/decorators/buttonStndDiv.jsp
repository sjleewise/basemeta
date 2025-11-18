<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch" style="display:none"><s:message code="INQ"/></button> <!-- 조회 --> 
			    <button class="btn_apply" id="btnApply" 	name="btnApply" style="display:none"><s:message code="APL" /></button> <!-- 적용 --> 
                <button class="btn_rqst_new" id="btnTreeNew" name="btnTreeNew" style="display:none"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
				  <ul class="add_button_menu" id="addTreeMenu" style="display:none;">
				    <li id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 -->
				    <li id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 업로드 -->
				  </ul>
				<button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 -->          
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete" style="display:none"><s:message code="DEL" /></button> <!-- 삭제 -->
			</div>
			<div class="bt02">
				<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown" style="display:none"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
				<button class="btn_save" id="btn_rqst" 	name="btn_rqst"  style="display:none;"><s:message code="LORQ.FLIN" /></button> <!-- 요청서작성 --> 
	    	</div>
        </div>	

