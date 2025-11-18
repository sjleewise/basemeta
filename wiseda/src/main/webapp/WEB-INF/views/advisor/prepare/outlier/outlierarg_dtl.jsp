<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>


<script type="text/javascript">

$(document).ready(function() {
	
// 	$("#frmdmnfeature input[type=text]").css("border-color","transparent").css("width", "98%").attr("readonly", true);
//     $("#frmdmnfeature input[type=text]").attr("readonly", true);

	$("#argVals").keyup(function(e) {
		$("#argVals").val(floatCheck($("#argVals").val(), e.key));
	});
});

$(window).on('load',function() {
});


$(window).resize( function(){
});
</script>


<div class="tb_basic2" >
    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="알고리즘변수">
<%--        <caption>알고리즘변수</caption><!--알고리즘변수--> --%>
       <colgroup>
       <col style="width:10%;" />
       <col style="width:90%;" />
       </colgroup>
<!--        <thead> -->
<!--        		<tr> -->
<!--        			<th scope="row">알고리즘 변수명</th> -->
<!--        			<th scope="row">변수 파라미터 값</th> -->
<!--        		</tr> -->
<!--        </thead> -->
       <tbody>   
       <c:forEach items="${alGarglist }" var="alGargVo" varStatus="status">
           <tr>                               
               <th scope="row"><label>${alGargVo.argLnm }</label></th><!--스키마명-->
               <td>
                   <input type="hidden" name="algArgIds" id="algArgIds" class="wd30p" value="${alGargVo.algArgId }"/>
                   <input type="text" name="argVals" id="argVals" class="wd30p" value="${alGargVo.argDefltVal }"/>
               </td>
           </tr>
       </c:forEach>                         
       </tbody>
     </table>   
</div>
	
