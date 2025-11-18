<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 -->
			    <button class="btn_apply" id="btnApply" 	name="btnApply"><s:message code="APL" /></button> <!-- 적용 --> 
                <button class="btn_rqst_new" id="btnTreeNew" name="btnTreeNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
				  <ul class="add_button_menu" id="addTreeMenu">
				    <li id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 -->
				    <li id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 업로드 -->
				  </ul>         
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 --> 
			    <button class="btn_search" id="btnSchStart" name="btnDelete" style="display:none">스케줄러 기동</button> 
			    <button class="btn_search" id="btnSchStop" 	name="btnDelete" style="display:none">스케줄러 중지</button> 
			    <button class="btn_search" id="btnImdExec" 	name="btnDelete" style="display:none"><s:message code="IMD.EXCT" /></button> <!-- 즉시 실행 -->
			    <button class="btn_search" id="btnJobStop" 	name="btnJobStop" style="display:none">스케줄 정지</button>
			    <button class="btnDdlSearch" id="btnDdlSearch" 	name="btnDdlSearch" style="display:none">DDL조회</button>  
			    
			</div>
			<div class="bt02">
	          <button class="btn_search"      id="btnDDL"       name="btnDDL" style="display:none;" ><s:message code="DDL.INQ" /></button>  <!-- DDL -->     
				<button class="btn_excel_down" id="poiDown" name="poiDown" style="display:none">품질진단종합현황 내려받기</button>
<!-- 	          <button class="btn_excel_load" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                        -->
				<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
				<button class="btn_save" id="btn_rqst" 	name="btn_rqst"  style="display:none;"><s:message code="LORQ.FLIN" /></button> <!-- 요청서작성 --> 
	    	</div>
        </div>	

