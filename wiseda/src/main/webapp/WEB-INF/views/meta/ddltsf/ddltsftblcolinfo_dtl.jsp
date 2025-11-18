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
<title>DDL테이블 컬럼정보</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #nonulYn").val('${result.nonulYn}');
		$("#frmInput #pkYn").val('${result.pkYn}');
	});
	

</script>
</head>
<body>
   <div class="stit">DDL테이블 컬럼정보</div>
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
         	  <th scope="row"><label for="tgtDbConnTrgPnm">타겟DBMS명</label></th>
              <td><input type="text" id="tgtDbConnTrgPnm" name="tgtDbConnTrgPnm" class="wd200" value="${result.tgtDbConnTrgPnm}" readonly/></td>
              <th scope="row"><label for="srcDbConnTrgPnm">소스DBMS명</label></th>
              <td><input type="text" id="srcDbConnTrgPnm" name="srcDbConnTrgPnm" class="wd200" value="${result.srcDbConnTrgPnm}" readonly/></td>
          </tr>
          <tr>
         		<th scope="row"><label for="tgtDbSchPnm">타겟DB스키마명</label></th>
              <td><input type="text" id="tgtDbSchPnm" name="tgtDbSchPnm" class="wd200" value="${result.tgtDbSchPnm}" readonly/></td>
              <th scope="row"><label for="srcDbSchPnm">소스DB스키마명</label></th>
              <td><input type="text" id="srcDbSchPnm" name="srcDbSchPnm" class="wd200" value="${result.srcDbSchPnm}" readonly/></td>
          </tr>  
      						      
          <tr>
   	        <th scope="row"><label for="ddlColLnm">이관DDL컬럼논리명</label></th>
              <td><input type="text" id="ddlColLnm" name="ddlColLnm" class="wd200" value="${result.ddlColLnm}" readonly/></td>
         		<th scope="row"><label for="ddlColPnm">이관DDL컬럼물리명</label></th>
              <td><input type="text" id="ddlColPnm" name="ddlColPnm" class="wd200" value="${result.ddlColPnm}" readonly/></td>
          </tr>
          <tr>
         		<th scope="row"><label for="colOrd">컬럼순서</label></th>
              <td><input type="text" id="colOrd" name="colOrd" class="wd200" value="${result.colOrd}" readonly/></td>
              <th scope="row"><label for="dataType">데이터타입</label></th>
              <td><input type="text" id="dataType" name="dataType" class="wd200" value="${result.dataType}" readonly/></td>
          </tr>
          <tr>
         		<th scope="row"><label for="dataLen">데이터길이</label></th>
              <td><input type="text" id="dataLen" name="dataLen" class="wd200" value="${result.dataLen}" readonly/></td>
              <th scope="row"><label for="dataScal">데이터소수점길이</label></th>
              <td><input type="text" id="dataScal" name="dataScal" class="wd200" value="${result.dataScal}" readonly/></td>
          </tr>
          <tr>
         		<th scope="row"><label for="pkYn">PK여부</label></th>
              <td><select id="pkYn" class="wd200" name="pkYn" value = "${result.pkYn}" disabled="disabled">
                      <option value=""></option>
                      <option value="Y">예</option>
                      <option value="N">아니오</option>
                  </select></td>
              <th scope="row"><label for="pkOrd">PK순서</label></th>
              <td><input type="text" id="pkOrd" name="pkOrd" class="wd200" value="${result.pkOrd}" readonly/></td>
          </tr>
          <tr>
         		<th scope="row"><label for="nonulYn">NOTNULL 여부</label></th>
              <td><select id="nonulYn" class="wd200" name="nonulYn" value = "${result.nonulYn}" disabled="disabled">
                      <option value=""></option>
                      <option value="Y">예</option>
                      <option value="N">아니오</option>
                  </select></td>
              <th scope="row"><label for="defltVal">DEFAULT값</label></th>
              <td><input type="text" id="defltVal" name="defltVal" class="wd200" value="${result.defltVal}" readonly/></td>
              
          </tr>
          <tr>
              <th scope="row"><label for="regTypCd">등록유형코드</label></th>
              <td colspan="3">
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