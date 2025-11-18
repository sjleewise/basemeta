<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<script type="text/javascript">

$(document).ready(function(){
		
	$("#ddlscript").css("font-size", "13px");	
	
});


</script>


   <div class="stit">DDL SCRIPT</div>
   <!-- 입력폼 시작 -->
   	<form id="frmInputDdlScript" name="frmInputDdlScript" method="post">
   	
   	<div class="tb_basic">
    <table border="0" cellspacing="0" cellpadding="0"  summary="DDL">
        <caption>DDL</caption>
        <colgroup>
            <col style="width:*;">            
        </colgroup>
        
	    <tbody>                         		
				<tr>
	            	<td> 	            		
	            		<textarea id="ddlscript" name="ddlscript" rows="30" style="width:99%;font-family:굴림체 !important;" readOnly >${ddlscript}</textarea>         		
	                </td>  
	           </tr>         	              
	    </tbody>
    </table>
    </div>
   
    </form>
    