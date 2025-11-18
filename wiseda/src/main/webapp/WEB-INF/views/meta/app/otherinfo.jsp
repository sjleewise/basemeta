<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="stit"><s:message code="DEMD.DTL.INFO" /></div> <!-- 요청상세정보 -->
   <!-- 입력폼 시작 -->
   	<form id="frmOther" name="frmOther" action ="" method="">
   	
   	<div class="tb_read">
    <table border="0" cellspacing="0" cellpadding="0"  summary="">
        <caption>
        <s:message code="INQ.COND" /> <!-- 조회조건 -->
        </caption>
        <colgroup>
            <col style="width:12%;">
            <col style="width:38%;">
            <col style="width:12%;">
            <col style="width:38%;">
        </colgroup>

      <tbody>                   
      		
      						
                            <tr>
                           		<th scope="row"><label for="frsRqstDtm"><s:message code="FRST.DEMD.DTTM" /></label></th> <!-- 최초요청일시 -->
                                <td><input type="text" id="frsRqstDtm" name="frsRqstDtm" class="wd98p" value="<fmt:formatDate value="${result.frsRqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                                <th scope="row"><label for="frsRqstUserNm"><s:message code="FRST.DMNT.NM" /></label></th> <!-- 최초요청자명 -->
                                <td><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" class="wd98p" value="${result.frsRqstUserNm}" readonly/></td>
                                
                            </tr>
                            <tr>
                           		<th scope="row"><label for="rqstDtm"><s:message code="DEMD.DTTM" /></label></th> <!-- 요청일시 -->
                                <td><input type="text" id="rqstDtm" name="rqstDtm" class="wd98p" value="<fmt:formatDate value="${result.rqstDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                                <th scope="row"><label for="rqstUserNm"><s:message code="DMNT.NM" /></label></th> <!-- 요청자명 -->
                                <td><input type="text" id="rqstUserNm" name="rqstUserNm" class="wd98p" value="${result.rqstUserNm}" readonly/></td>
                                
                            </tr>
                            <tr>
                           		<th scope="row"><label for="aprvDtm"><s:message code="APRV.DTTM" /></label></th> <!-- 승인일시 -->
                                <td><input type="text" id="aprvDtm" name="aprvDtm" class="wd98p" value="<fmt:formatDate value="${result.aprvDtm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly/></td>
                                <th scope="row"><label for="aprvUserNm"><s:message code="APRR.NM" /></label></th> <!-- 승인자명 -->
                                <td><input type="text" id="aprvUserNm" name="aprvUserNm" class="wd98p" value="${result.aprvUserNm}" readonly/></td>
                                
                            </tr>
                   </tbody>
    </table>
    </div>
    
    </form>