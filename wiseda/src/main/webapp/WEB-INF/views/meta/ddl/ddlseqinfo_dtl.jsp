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
<title>DDL시퀀스 상세정보</title>
<script type="text/javascript">
	$(document).ready(function(){
		$("#frmInput select").attr('disabled', false);
		$("#frmInput #regTypCd").val('${result.regTypCd}');
		$("#frmInput #nmRlTypCd").val('${result.nmRlTypCd}');
		$("#frmInput #usTypCd").val('${result.usTypCd}');
		$("#frmInput #cycYn").val('${result.cycYn}');
		$("#frmInput #ordYn").val('${result.ordYn}');
		$("#frmInput #aplReqTypCd").val("${result.aplReqTypCd}");
		$("#frmInput #aplReqdt").val("${result.aplReqdt}");
		
		$("#frmInput #seqClasCd").val('${result.seqClasCd}');
		$("#frmInput #seqInitCd").val('${result.seqInitCd}');
		
		
		if("${result.nmRlTypCd}" == "E"){
			$("#tableTr").show();
			$("#colTr").hide();
			$("#l1l3Tr").hide();			
			
		}else if("${result.nmRlTypCd}" == "C"){
			$("#tableTr").hide();
			$("#colTr").show();
			$("#l1l3Tr").hide();

		}else if("${result.nmRlTypCd}" == "S"){
			$("#tableTr").hide();
			$("#colTr").hide();
			$("#l1l3Tr").show();
		}
		$("#frmInput select").attr('disabled', true);
		
	});
	
	
	

</script>
</head>
<body>
   <div class="stit">DDL시퀀스 상세정보</div>
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
						 <%-- <tr>
					        <th scope="row"><label for="prjMngNo">SR번호/서브프로젝트번호</label></th>
                            <td colspan="3">
                                <span class="input_file">
                                <input type="text" name="srMngNo" id="srMngNo" value="${result.srMngNo}" class="wd200" readonly/>
                                <input type="text" name="prjMngNo" id="prjMngNo" value="${result.prjMngNo}" class="wd200" readonly/>
                                </span>
                            </td>
						</tr>  --%>
                            <tr>
                            	<th scope="row" ><label for="ddlSeqPnm">시퀀스물리명</label></th>
		                       <td ><input type="text" id="ddlSeqPnm" name="ddlSeqPnm"  class="wd300" value="${result.ddlSeqPnm }" readonly /></td>
		                       <th scope="row"><label for="regTypCd">등록유형코드</label></th>
                                <td>
                                   <select id="regTypCd" class="wd200" name="regTypCd" value = "${result.regTypCd}" disabled="disabled">
                                        <c:forEach var="code" items="${codeMap.regTypCd}" varStatus="status" >
                                        <option value="${code.codeCd}">${code.codeLnm}</option>
                                        </c:forEach>
                                    </select>
                                </td> 
		                       
		                       
		                   </tr>
		                   <tr>
		                   		<th scope="row"><label for="dbConnTrgPnm">DBMS명</label></th>
                                <td><input type="text" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd98p" value="${result.dbConnTrgPnm}" readonly/></td>
                                <th scope="row"><label for="dbSchPnm">스키마명</label></th>
                                <td><input type="text" id="dbSchPnm" name="dbSchPnm" class="wd98p" value="${result.dbSchPnm}" readonly/></td>
		                   </tr>
		                   
		                    <%-- <tr>
		                       <th scope="row" ><label for="nmRlTypCd">명명규칙</label></th>
		                       <td>
		                       		<select id="nmRlTypCd" name="nmRlTypCd" value = "${result.nmRlTypCd}" class="wd100" disabled="disabled">
		                        		<option value="" selected></option>
		                        	<c:forEach var="code" items="${codeMap.nmRlTypCd}" varStatus="status" >
		                               <option value="${code.codeCd}">${code.codeLnm}</option>
		                            </c:forEach>
		                        </select>
		                       </td >
		                       <th scope="row" ><label for="usTypCd">사용용도</label></th>
		                       <td>
		                       		<select id="usTypCd" name="usTypCd" value = "${result.usTypCd}" class="wd100" disabled="disabled">
		                        		<option value="" selected></option>
		                        	<c:forEach var="code" items="${codeMap.usTypCd}" varStatus="status" >
		                               <option value="${code.codeCd}">${code.codeLnm}</option>
		                            </c:forEach>
		                      </td>
		                       
		                   </tr> --%>
		                   
		                   <tr id="tableTr" style="display:none;" >
		                       <th scope="row" ><label for="pdmTblPnm">테이블명</label></th>
		                       <td colspan="3">
		                       		<input type="text" id="pdmTblPnm" name="pdmTblPnm"  class="wd300" value="${result.pdmTblPnm }" readonly />
		                       </td>
		                       
		                       
		                       
		                   </tr>
		                   <tr id="colTr" style="display:none;">
		                   		<th scope="row"><label for="pdmColPnm">컬럼명</label></th>
		                       <td colspan="3">
		                       		<input type="text" id="pdmColPnm" name="pdmColPnm"  class="wd300"  value="${result.pdmColPnm }"  readonly />
		                       </td>
		                   </tr>
		                   <tr id="l1l3Tr" style="display:none;"> 
								<th scope="row">L1코드</th>
								<td>
									<input type="text" class="wd150" id="l1cdPnm" name="l1cdPnm"  value="${result.l1cdPnm}" readonly="readonly"/>

								</td>
								<th scope="row">L3코드</th>
								<td>
									
									<input type="text" class="wd150" id="l3cdPnm" name="l3cdPnm"  value="${result.l3cdPnm}" readonly="readonly"/>

								</td>
							</tr>
							<%-- <tr>
		                   	   <th scope="row" ><label for="grtLst">권한목록</label></th>
		                       <td colspan="3">
		                       		<input type="text" id="grtLst" name="grtLst"  class="wd400" value="${result.grtLst }" readonly />
		                       </td>
		                   </tr> --%>
							
							<tr>
		                       <th scope="row" ><label for="incby">INCREMENT BY</label></th>
		                       <td><input type="text" id="incby" name="incby"  class="wd300" value="${result.incby }" readonly /></td>
		                       <th scope="row"><label for="strtwt">START WITH</label></th>
		                       <td><input type="text" id="strtwt" name="strtwt"  class="wd300"  value="${result.strtwt }"  readonly /></td>
		                   </tr>
		                   <tr>
		                       <th scope="row"><label for="minval">MIN VALUE</label></th>
		                       <td><input type="text" id="minval" name="minval"  class="wd300"  value="${result.minval }"  readonly /></td>
		                       <th scope="row" ><label for="maxval">MAX VALUE</label></th>
		                       <td><input type="text" id="maxval" name="maxval"  class="wd300" value="${result.maxval }" readonly /></td>
		                       
		                   </tr>
		                   <tr>
		                       <th scope="row" ><label for="cycYn">CYCLE 여부</label></th>
		                       <td>
		                       		<select id="cycYn"  name="cycYn" value="${result.cycYn}" class="wd100" disabled="disabled">
									       <option value="" selected></option>
									       <option value="Y">Y</option>
									       <option value="N">N</option>
									</select>                       		
		                       </td>
		                       <th scope="row" ><label for="ordYn">ORDER 여부</label></th>
		                       <td>
		                       		<select id="ordYn"  name="ordYn" value="${result.ordYn}" class="wd100" disabled="disabled">
									       <option value="" selected></option>
									       <option value="Y">Y</option>
									       <option value="N">N</option>
									</select>                       		
		                       </td>
		                   </tr>
		                   <tr>
		                   	   <th scope="row" ><label for="cacheSz">CACHE SIZE</label></th>
		                       <td colspan="3">
		                       		<input type="text" id="cacheSz" name="cacheSz"  class="wd300" value="${result.cacheSz }" readonly />
		                       </td>
		                   </tr>
		                   
		                  <%--  <tr>
		                       <th scope="row" ><label for="pckgNm">패키지명</label></th>
		                       <td>
		                       		<input type="text" id="pckgNm" name="pckgNm"  class="wd300" value="${result.pckgNm }" readonly />                  		
		                       </td>                   
		                       <th scope="row" ><label for="seqClasCd">클래스명</label></th>
		                       <td>
		                       		<select id="seqClasCd"  name="seqClasCd" class="wd100"  disabled="disabled">
										<option value="" selected></option>
										<c:forEach var="code" items="${codeMap.seqClasCd}" varStatus="status" >
										<option value="${code.codeCd}">${code.codeLnm}</option>
										</c:forEach>                       
									</select>                      		
		                       </td>
							</tr>
							<tr>
		                       <th scope="row"><label for="seqInitCd">초기화구분코드</label></th>
		                       <td>
		                       		<select id="seqInitCd"  name="seqInitCd" class="wd200"  disabled="disabled">
										<option value="" selected>선택</option>
										<c:forEach var="code" items="${codeMap.seqInitCd}" varStatus="status" >
										<option value="${code.codeCd}">${code.codeLnm}</option>
										</c:forEach>                       
									</select>                        		
		                       </td>
		                       <th scope="row" ><label for="bizIfnc">초기화실패업무영향도</label></th>
		                       <td>
		                       		<input type="text" id="bizIfnc" name="bizIfnc"  class="wd98p" value="${result.bizIfnc }" readonly />                  		
		                       </td>                        
		                   </tr>  		                   
		                   
		                   
		                   <tr>
		                   	<th scope="row"><label for="aplReqTypCd">적용요청구분</label></th>
							<td>
						    	<select id="aplReqTypCd" class="wd200" name="aplReqTypCd" value = "${result.aplReqTypCd}" disabled="disabled">
						    	    <option value=""></option>
						            <c:forEach var="code" items="${codeMap.aplReqTypCd}" varStatus="status" >
						            <option value="${code.codeCd}">${code.codeLnm}</option>
						            </c:forEach>
						        </select>
							</td>
							<th scope="row"><label for="aplReqdt">적용요청일자</label></th>
							<td><input type="text" id="aplReqdt" name="aplReqdt" class="dtlInput" value="${result.aplReqdt }" readonly /></td>
							</tr> --%>
		
		                   <tr>
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