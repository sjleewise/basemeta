<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!-- 조회버튼영역   중앙 정렬 style="margin:0 auto; width:XXXpx;버튼 픽셀크기 -->
		<div id="divInputBtn" style="text-align: center; display: none;">	
            	<button class="btn_frm_save"   id="btnSave"   name="btnSave" style="display:none"><s:message code="BTN.SAVE" /></button> <!-- 저장 -->
			    <button class="btn_frm_save"  id="btnSend"   name="btnSend" style="display:none">저장 후 전송</button> 			    
			    <button class="btn_frm_save" id="btnCloseBottom" type="button">닫기</button>
        </div>	
