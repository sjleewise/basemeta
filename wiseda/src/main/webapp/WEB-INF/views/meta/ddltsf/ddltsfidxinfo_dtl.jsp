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
<title>DDL인덱스 상세정보</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #pkIdxYn").val('${result.pkIdxYn}');
		$("#frmInput #ukIdxYn").val('${result.ukIdxYn}');
		$("#frmInput #idxTypCd").val('${result.idxTypCd}');
		
	});
	

</script>
</head>
<body>
   <div class="stit">DDL인덱스 상세정보</div>
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
                     	        <th scope="row"><label for="ddlIdxPnm">DDL인덱스명</label></th>
                                <td><input type="text" id="ddlIdxPnm" name="ddlIdxPnm" class="wd200" value="${result.ddlIdxPnm}" readonly/></td>
                           		<th scope="row"><label for="ddlIdxLnm">DDL인덱스명(논리)</label></th>
                                <td><input type="text" id="ddlIdxLnm" name="ddlIdxLnm" class="wd200" value="${result.ddlIdxLnm}" readonly/></td>
                            </tr>
                            <tr>
                           		<th scope="row"><label for="srcDbConnTrgPnm">소스DBMS명</label></th>
                                <td><input type="text" id="srcDbConnTrgPnm" name="srcDbConnTrgPnm" class="wd200" value="${result.srcDbConnTrgPnm}" readonly/></td>
                                <th scope="row"><label for="srcDbSchPnm">소스스키마명</label></th>
                                <td><input type="text" id="srcDbSchPnm" name="srcDbSchPnm" class="wd200" value="${result.srcDbSchPnm}" readonly/></td>
                                
                            </tr>
                            <tr>
                           		<th scope="row"><label for="tgtDbConnTrgPnm">타겟DBMS명</label></th>
                                <td><input type="text" id="tgtDbConnTrgPnm" name="tgtDbConnTrgPnm" class="wd200" value="${result.tgtDbConnTrgPnm}" readonly/></td>
                                <th scope="row"><label for="tgtDbSchPnm">타겟스키마명</label></th>
                                <td><input type="text" id="tgtDbSchPnm" name="tgtDbSchPnm" class="wd200" value="${result.tgtDbSchPnm}" readonly/></td>
                                
                            </tr>
                            <tr>
                                <th scope="row"><label for="ddlTblPnm">DDL테이블명</label></th>
                                <td><input type="text" id="ddlTblPnm" name="ddlTblPnm" class="wd200" value="${result.ddlTblPnm}" readonly/></td>
                                <th scope="row"><label for="idxSpacPnm">인덱스스페이스명</label></th>
                                <td><input type="text" id="idxSpacPnm" name="idxSpacPnm" class="wd200" value="${result.idxSpacPnm}" readonly/></td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="pkIdxYn">PK인덱스여부</label></th>
                                <td>
                                <select id="pkIdxYn" class="wd200" name="pkIdxYn" value = "${result.pkIdxYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y">예</option>
                                        <option value="N">아니오</option>
                                    </select></td>  
                                <th scope="row"><label for="ukIdxYn">UNIQUE 인덱스여부</label></th>
                                <td>
                                <select id="ukIdxYn" class="wd200" name="ukIdxYn" value = "${result.ukIdxYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y">예</option>
                                        <option value="N">아니오</option>
                                    </select></td>  
                            </tr>
                            <tr>
                                <th scope="row"><label for="idxTypCd">인덱스유형코드</label></th>
                                <td>
                                <select id="idxTypCd" class="wd200" name="idxTypCd" value = "${result.idxTypCd}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.idxTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>  
                                <th scope="row"><label for="regTypCd">등록유형코드</label></th>
                                <td>
                                <select id="regTypCd" class="wd200" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select></td>  
                            </tr>
                            
                            <tr>
                           		<th scope="row"><label for="objDescn">설명</label></th>
                                <td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p" readonly>${result.objDescn}</textarea></td>
                                
                            </tr>
                   </tbody>
    </table>
    </div>
    
    </form>
    
   		<!-- 입력폼 끝 -->
	<div style="clear:both; height:10px;"><span></span></div>
	<%@include file="../stnd/otherinfo.jsp" %>
	<!-- 입력폼 버튼... -->
<!-- 	<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 		<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 		<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 	</div> -->


</body>
</html>