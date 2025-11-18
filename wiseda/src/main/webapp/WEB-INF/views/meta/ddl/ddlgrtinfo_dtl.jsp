<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title>DDL권한 상세정보</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #ddlObjTypCd").val('${result.ddlObjTypCd}');
		
		if("${result.selectYn}" == "Y") $("#frmInput #selectYn").prop('checked', true);
		if("${result.insertYn}" == "Y") $("#frmInput #insertYn").prop('checked', true);
		if("${result.updateYn}" == "Y") $("#frmInput #updateYn").prop('checked', true);
		if("${result.deleteYn}" == "Y") $("#frmInput #deleteYn").prop('checked', true);
		if("${result.executeYn}" == "Y") $("#frmInput #executeYn").prop('checked', true);

	});
	
	
	

</script>
</head>
<body>
   <div class="stit">DDL권한 상세정보</div>
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class="tb_read">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        조회조건
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>

      <tbody>                   
                            
                            <tr>
								<th scope="row"><label for="regTypCd">등록유형코드</label></th>
                                <td colspan="3">
                                   <select id="regTypCd" class="wd200" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                </td> 
								
							</tr>
                            <tr>
                            	<th scope="row">Grantor DBMS 정보</th>
								<td>
									<span class="input_inactive"><input type="hidden" class="wd100" id="grtorDbId" name="grtorDbId"  value="${result.grtorDbId}" readonly="readonly"/></span>
									<span class="input_inactive"><input type="hidden" class="wd100" id="grtorSchId" name="grtorSchId"  value="${result.grtorSchId}" readonly="readonly"/></span>
									<span class="input_inactive"><input type="text" class="wd100" id="grtorDbPnm" name="grtorDbPnm"  value="${result.grtorDbPnm}"  readonly="readonly"/></span>
									<span class="input_inactive"><input type="text" class="wd100" id="grtorSchPnm" name="grtorSchPnm"  value="${result.grtorSchPnm}"  readonly="readonly"/></span>
								</td>
								<th scope="row"><label for="ddlSeqPnm">오브젝트명</label></th>
			                    <td>
			                    	<span class="input_inactive"><input type="hidden" class="wd100" id="ddlObjId" name="ddlObjId"  value="${result.ddlObjId}" readonly="readonly"/></span>
			                    	<input type="text" id="ddlObjPnm" name="ddlObjPnm"  class="wd200" value="${result.ddlObjPnm }"  readonly="readonly"/>
			                   	</td>
		                       
		                   </tr>
		                   <tr>
		                   		<th scope="row">Granted to DBMS 정보</th>
								<td>
									<span class="input_inactive"><input type="hidden" class="wd100" id="grtedDbId" name="grtedDbId"  value="${result.grtedDbId}" readonly="readonly"/></span>
									<span class="input_inactive"><input type="hidden" class="wd100" id="grtedSchId" name="grtedSchId"  value="${result.grtedSchId}" readonly="readonly"/></span>
									<span class="input_inactive"><input type="text" class="wd100" id="grtedDbPnm" name="grtedDbPnm"  value="${result.grtedDbPnm}" readonly="readonly"/></span>
									<span class="input_inactive"><input type="text" class="wd100" id="grtedSchPnm" name="grtedSchPnm"  value="${result.grtedSchPnm}" readonly="readonly"/></span>
								</td>
								<th scope="row"><label for="ddlObjTypCd">오브젝트유형</label></th>
			                    <td>
			                   		<select id="ddlObjTypCd" name="ddlObjTypCd" value = "${result.ddlObjTypCd}" class="wd100" disabled="disabled">
			                    		<option value="" selected></option>
			                    	<c:forEach var="code" items="${codeMap.objDcd}" varStatus="status" >
			                           <option value="${code.codeCd}">${code.codeLnm}</option>
			                        </c:forEach>
			                        </select>
			                    </td>
		                   </tr>
		
		                   <tr>
		                   	   <th scope="row"><label for="grantYn">권한</label></th>
		                       <td>
		                       		<span class="input_check"><input type="checkbox" id="selectYn" name="selectYn" disabled="disabled"/> SELECT</span>&nbsp;&nbsp;&nbsp;
		                       		<span class="input_check"><input type="checkbox" id="insertYn" name="insertYn" disabled="disabled"/> INSERT</span>&nbsp;&nbsp;&nbsp;
		                       		<span class="input_check"><input type="checkbox" id="updateYn" name="updateYn" disabled="disabled"/> UPDATE</span>&nbsp;&nbsp;&nbsp;
		                       		<span class="input_check"><input type="checkbox" id="deleteYn" name="deleteYn" disabled="disabled"/> DELETE</span>&nbsp;&nbsp;&nbsp;
		                       		<span class="input_check"><input type="checkbox" id="executeYn" name="executeYn" disabled="disabled"/> EXECUTE</span>
		                       </td>
		                       <th scope="row"><label for="objDescn">설명</label></th>
		                       <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly>${result.objDescn }</textarea></td>
		                   </tr>
                   </tbody>
    </table>
    </div>
    
    </form>
    
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<%@include file="../stnd/otherinfo.jsp" %>

</body>
</html>