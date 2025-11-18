<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <div class="divLstBtn" style="display: none;">  
            <div class="bt03">
			    <button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="WHL.INQ" /></button> <!-- 전체조회 -->
			    <button class="btn_search" id="btnMartSch"  name="btnMartSch" style="display:none;"><s:message code="ERWIN.INQ" /></button> <!-- ERWIN조회 --> 
                <button class="btn_search" id="btnMartExSch"  name="btnMartExSch" style="display:none;"><s:message code="EXERD.INQ" /></button> <!-- EXERD조회 --> 
                <button class="btn_rqst_new" id="btnRqstNew" name="btnRqstNew"><s:message code="ADDT" /></button> <!-- 추가 -->                                                         
				  <ul class="add_button_menu" id="addButtonMenu" style="display:none;">
				    <li class="btn_new" id="btnNew"><a><span class="ui-icon ui-icon-pencil"></span><s:message code="NEW.ADDT" /></a></li> <!-- 신규 추가 -->
				    <li class="btn_chang_add" id="btnChangAdd"><a><span class="ui-icon ui-icon-folder-open"></span><s:message code="CHG.TRGT.ADDT" /></a></li> <!-- 변경대상 추가 -->
				    <li class="btn_excel_load" id="btnExcelLoad"><a><span class="ui-icon ui-icon-document"></span><s:message code="EXCL.UPLOAD" /></a></li> <!-- 엑셀 올리기 -->
				  </ul>         
			    <button class="btn_save" id="btnSave" 	name="btnSave"><s:message code="STRG" /></button> <!-- 저장 --> 
			    <button class="btn_delete" id="btnDelete" 	name="btnDelete"><s:message code="DEL" /></button> <!-- 삭제 -->
<!-- 			    <button class="btn_check" id="btnCheck" 	name="btnCheck">검증</button>  -->
			    <button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst" style="display:none;"><s:message code="REG.DEMD" /></button> <!-- 등록요청 --> 
			    <button class="btn_search"    id="btnMetaSrch"  name="btnMetaSrch" style="display:none;" ><s:message code="META.SRCH" /></button>  <!-- 메타연동조회 -->
			    <button class="btn_search"    id="btnWatSrch"  name="btnWatSrch" style="display:none;" >자동추출</button>  <!-- 메타연동조회 -->  
			    <button class="btn_rqst_new2" id="btnBlank" name="btnBlank" style="display:none;"><s:message code="NEW.LORQ" /></button> <!-- 신규요청서 --> 
			    <button class="btn_search" id="btnSend" name="btnSend" style="display:none;"><s:message code="SND.MTA" /></button> <!-- 중앙메타전송 --> 
			</div>
			<div class="bt02">
<!-- 	          <button class="btn_excel_load" id="btnExcelLoad" name="btnExcelLoad">엑셀 업로드</button>                        -->
<!-- 	          <button class="btn_all_approve" id="btnAllApprove" name="btnAllApprove">전체승인</button>                        -->
<!-- 	          <button class="btn_all_reject"  id="btnAllReject" name="btnAllReject">전체반려</button> -->
	          <span id="aprBtn" style="display:none;">
	          <button class="btn_req_appr"    id="btnReqApprove" name="btnReqApprove" ><s:message code="APRL.TRTT" /></button>
	          <button class="btn_sel_appr"    id="btnSelApprove" name="btnSelApprove" ><s:message code="APRV.RTN.CHC" /></button> <!-- 결재처리 승인반려선택--> 
	          </span>
	          <ul class="appr_button_menu" id="approveButtonMenu" style="display:none;">
			    <li class="btn_all_approve" id="btnAllApprove"><a><span class="ui-icon ui-icon-check"></span><s:message code="WHL.APRV" /></a></li> <!-- 전체승인 -->
			    <li class="btn_all_reject"  id="btnAllReject"><a><span class="ui-icon ui-icon-cancel"></span><s:message code="WHL.RTN" /></a></li> <!-- 전체반려 -->
			  </ul>                        
	                      
	                     
	          <button class="btn_search"      id="btnDDL"       name="btnDDL" style="display:none;" ><s:message code="DDL.INQ" /></button>  <!-- DDL -->                        
	          <button class="btn_excel_down"  id="btnExcelDown"  name="btnExcelDown"><s:message code="EXCL.DOWNLOAD" /></button> <!-- 엑셀 내리기 -->                       
	    	</div>
        </div>	

