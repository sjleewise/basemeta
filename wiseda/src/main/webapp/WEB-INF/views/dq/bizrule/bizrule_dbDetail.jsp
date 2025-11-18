<!-- <!DOCTYPE html> -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title>업무규칙조회 기본정보</title> -->

<script type="text/javascript">

$(document).ready(function() {
   //진단대상명 검색 팝업
   $('#btnDbConnTrgPop').click(function(event){
             event.preventDefault();   //브라우저 기본 이벤트 제거...
             var url = '<c:url value="/dq/criinfo/anatrg/popup/dbconntrg_pop.do"/>';
             var popwin = OpenModal(url+"?sflag=DBCTRG", "dbConnTrgPop",  900, 550, "no");
            popwin.focus();
   });
   
   //테이블명 검색 팝업
   $('#btnSearchTblPop').click(function(event){
       event.preventDefault();   //브라우저 기본 이벤트 제거...

       var param = $("form[name=frmDbDetail]").serialize(); 
       var url = '<c:url value="/dq/criinfo/anatrg/popup/tbllst_pop.do"/>';

       var popwin = OpenModal(url + "?sflag=TBLLST&" + param, "tblLstPop",  700, 800, "no");

      popwin.focus();
   });

   //컬럼명 검색 팝업
   $('#btnSearchColPop').click(function(event){
       event.preventDefault();   //브라우저 기본 이벤트 제거...
       
       var param = $("form[name=frmDbDetail]").serialize(); 
       var url = '<c:url value="/dq/profile/popup/collst_pop.do"/>';
       
       var popwin = OpenModal(url+"?sflag=COLLST&" + param, "colLstPop",  700, 900, "no");
       
      popwin.focus();
   });


});

</script>
<!-- </head> -->
<!-- <body> -->
        <div class="stit"><s:message code="BZWR.RGR.DIAG.INFO"/></div><!-- 업무규칙 진단정보 -->
         <div style="clear:both; height:5px;"><span></span></div>
         <div class="tb_basic">
         <form name="frmDbDetail" id="frmDbDetail"  method="post"> 
            <input type="hidden" id="dbConnTrgId" name="dbConnTrgId" value="${result.dbConnTrgId}"/>
            <input type="hidden" id="dbSchId" name="dbSchId" value="${result.dbSchId}"/>
            <input type="hidden" id="dbConnTrgLnm" name="dbConnTrgLnm" value="${result.dbConnTrgLnm}"/>
            <input type="hidden" id="dbSchLnm" name="dbSchLnm" value="${result.dbSchLnm}"/>
            
            <table border="0" cellspacing="0" cellpadding="0"  summary="">
              <caption>
              <s:message code="BZWR.RGR.DIAG.INFO"/>              
              </caption><!-- 업무규칙 진단정보 -->
              <colgroup>
                  <col style="width:10%;">
                  <col style="width:26%;">
                  <col style="width:10%;">
                  <col style="width:22%;">
                  <col style="width:10%;">
                  <col style="width:22%;">
              </colgroup>
               <tr>
                 <th scope="row" class="th_require"><s:message code="DB.MS.SCHEMA.NM" /></th><!-- 진단대상명 -->
                 <td>
                      
                    <input type="text" id="dbConnTrgPnm" name="dbConnTrgPnm" value="${result.dbConnTrgPnm }"  readonly/>
                    <input type="text" id="dbSchPnm" name="dbSchPnm" value="${result.dbSchPnm }" readonly/>
                        <button class="btnSearchPop" id="btnDbConnTrgPop" ><s:message code="SRCH" /></button><!-- 검색 -->
                        
                 </td>
                     <th><s:message code="TBL.NM" /></th><!-- 테이블명 -->
                    <td>
                           <input type="text" id="dbcTblNm" name="dbcTblNm" value="${result.dbcTblNm }" style="width:50%;" readonly/>
                           <button class="btnSearchPop" id="btnSearchTblPop" ><s:message code="SRCH" /></button><!-- 검색 -->
                    </td>
                    <th><s:message code="CLMN.NM" /></th><!-- 컬럼명 -->
                    <td>
                            <input type="text" id="dbcColNm" name="dbcColNm" value="${result.dbcColNm }"  style="width:50%;" readonly/>
                           <button class="btnSearchPop" id="btnSearchColPop" ><s:message code="SRCH" /></button><!-- 검색 -->
                    </td>
               </tr>
               <tr>
               <th><s:message code="CCNT.SQL"/></th><!-- 건수SQL -->
                 <td>
                    <textarea style="height:150px;width:99%;word-spacing:5px;"  id="cntSql" name="cntSql" accesskey=""  readonly>${result.cntSql}</textarea>
                 </td>
                 <th><s:message code="ANLY.SQL" /></th><!-- 분석SQL -->
                 <td colspan="3">
                    <textarea style="height:150px;width:99%;word-spacing:5px;"  id="anaSql" name="anaSql" accesskey="" readonly>${result.anaSql}</textarea>
                 </td>
               </tr>
             </table>
             </form>
         </div>
         <div style="clear:both; height:5px;"><span></span></div> 
         <div id="buttonRqstDbDetail" style="text-align: center;display: none;">
         <button class="btn_frm_save" type="button" id="btnGridSave1" name="btnGridSave1"><s:message code="STRG" /></button><!--  저장 -->
            <button class="btn_frm_reset" type="button" id="btnReset1" name="btnReset1"><s:message code="INON" /></button><!-- 초기화 -->
         </div>
         
<!-- </body> -->
<!-- </html>-->