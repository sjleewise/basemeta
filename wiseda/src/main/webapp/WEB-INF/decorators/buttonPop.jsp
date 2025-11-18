<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
			    <button class="btn_search" id="popSearch"  name="popSearch"><s:message code="INQ"/></button> <!-- 조회 -->
            	<button class="btn_reset"  id="popReset"   name="popReset" ><s:message code="INON" /></button> <!-- 초기화 -->
			    <button class="btn_apply"  id="popApply"   name="popApply"><s:message code="APL" /></button> <!-- 적용 -->
<!-- 			    <button class="btn_delete" id="btnDelete" 	name="btnDelete">삭제요청</button>  -->
<!--                 <button class="btn_rqst_new" id="btnTreeNew" name="btnTreeNew">추가</button>     -->
<!-- 				  <ul class="add_button_menu" id="addTreeMenu"> -->
<!-- 				    <li id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span>신규 추가</a></li> -->
<!-- 				    <li id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span>엑셀 업로드</a></li>  -->
<!-- 				  </ul>          -->
<!-- 			    <button class="btn_save" id="btnSave" 	name="btnSave">저장</button>  -->
<!-- 			    <button class="btn_delete" id="btnDelete" 	name="btnDelete">삭제</button>  -->
			</div>
			<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                        -->
	          <button class="btn_excel_down" id="popExcelDown" name="popExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	

