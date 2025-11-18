<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="MENU.DTL.INFO" /></title> <!-- 메뉴상세정보 -->
<script type="text/javascript">
	$(document).ready(function(){

		double_select(connTrgSchJson, $("#frmInput #dbConnTrgId"));
       	$('select', $("#frmInput #dbConnTrgId").parent()).change(function(){
       		double_select(connTrgSchJson, $(this));
       	});
		
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #etcObjDcd").val('${result.etcObjDcd}');

		$("#frmInput #dbConnTrgId").val('${result.dbConnTrgId}');

		$("#frmInput #dbConnTrgId").change();

		$("#frmInput #dbSchId").val('${result.dbSchId}');

		$("#frmInput #dbConnTrgId").attr("disabled", true);
		$("#frmInput #dbSchId").attr("disabled", true);
		$("#frmInput #etcObjDcd").attr("disabled", true);
		$("#frmInput #scrtInfo").attr("disabled", true);
	});
</script>
</head>
<body>
   <div class="stit">기타오브젝트상세정보</div> <!-- 단어 상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_read">
    
    	<div class="tb_basic">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
			<caption>DBMS 정보</caption>
			<colgroup>
			   <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:*;" />  
			</colgroup>
			<tbody>
				<tr>
				    <th scope="row" class="th_require">오브젝트유형</th> 
					<td>
						<select id="etcObjDcd" name="etcObjDcd">
							<option value=""><s:message code="CHC" /></option> <!-- 선택 -->
							<c:forEach var="code" items="${codeMap.etcObjDcd}" varStatus="status">
							  <option value="${code.codeCd}">${code.codeLnm}</option>
							</c:forEach>
					 	</select>
					</td>
					
					<th scope="row" class="th_require">DBMS 정보</th> <!-- DBMS 정보 -->
					<td>
						<select id="dbConnTrgId" class="" name="dbConnTrgId">
					    	<option value="">전체</option> <!-- 전체 -->
					    </select>
					    <select id="dbSchId" class="" name="dbSchId">
					    	<option value="">전체</option> <!-- 전체 -->
					    </select>
					</td>
					
				</tr>
			</tbody> 
		</table>
	</div>
	
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit">오브젝트정보</div>  <!-- 테이블 정보 -->
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend>머리말</legend>
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
               <caption>테이블 이름</caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:*;" />  
              
               </colgroup>
               <tbody>
                   <tr>
					    <th scope="row" class="th_require">오브젝트명</th> 
						<td>
							<input type="text" id="ddlEtcPnm" name="ddlEtcPnm" class="wd98p" value="${result.ddlEtcPnm}" readonly />							
						</td>
						<th scope="row">오브젝트한글명</th> 
						<td>
							<input type="text" id="ddlEtcLnm" name="ddlEtcLnm" class="wd98p" value="${result.ddlEtcLnm}" readonly />							
						</td>				
				   </tr>
                   <tr>
                       <th scope="row"><label for="scrtInfo">스크립트</label></th>  
                       <td colspan=3 >
                       		<textarea id="scrtInfo" name="scrtInfo" rows=20 class="wd98p">${result.scrtInfo}</textarea>
                       </td>
                   </tr>
                                      
               </tbody>
           </table>
       </div>
    </div>
    </form>
   		
</body>
</html>