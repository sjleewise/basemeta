<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<!-- 입력폼 시작 -->
   	<form id="frmInputDdlScript" name="frmInputDdlScript" action ="" method="">
   	   	
   	<div class="tb_basic">
   	
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
      <caption>조회조건</caption>
      <colgroup>
          <col style="width:12%;">
          <col style="width:38%;">
          <col style="width:12%;">
          <col style="width:38%;">
      </colgroup>

      <tbody>                         		 
			<tr>
            	<td colspan="4"><textarea rows="35" class="wd98p" readonly style="font-size:12px;font-family:굴림체;" >${ddlscript }</textarea></td>  
           </tr>         
              
     </tbody>
    </table>
    </div>
    
    </form>

