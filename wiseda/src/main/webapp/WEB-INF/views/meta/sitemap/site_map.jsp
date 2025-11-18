<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<head>
<title></title>

<script type="text/javascript">

//alert("${REQ_MENU.menuDcd}");
var interval = "";
var favoriteList ="";
//EnterkeyProcess("Search");

$(document).ready(function() {
	
	$.ajaxSetup({ cache: false });
	
	$('#btnFavoriteSave').click(function(event){
		event.preventDefault();  //브라우저 기본 이벤트 제거...
		doSave();
	});
	
	
	$('#mymenu').change(function(){
		$(location).attr('href','/wiseda/'+$('#mymenu option:selected').val());
	});


	
	
});

$(window).on('load',function() {
	getFavoriteList();
});

$(window).resize(function(){
   
});

function getFavoriteList(){
	//인기검색어 스크롤 텍스트 처리하도록 처리
		$.getJSON('<c:url value="/meta/sitemap/favoriteList.json"/>', function(data){
		if(data ==  null) return;
		$("select[name='mymenu'] option").remove();
		    $("#mymenu").append("<option value='' selected>----[<s:message code='CHC.FAVO.MENU' />]----</option>"); /*선택한 즐겨찾기 메뉴*/
		for(var i=0; i<data.length; i++){
		    $("#mymenu").append("<option value='"+data[i].filePath +"'>"+ data[i].fullPath+"</option>");
		}
		});	
}

function doSave()
{
	
	var chkObj 	= 	$('form[name="frmInput"] input[name="menuid"]') ;
	var chkCnt	=	0;
	var chkMenuList	=	"";
	
	//alert(chkObj.length);
	for(var i=0; i < chkObj.length; i++) {
		if (chkObj[i].checked == true)
		{
			chkCnt ++;
			if(chkCnt > 1) {
				chkMenuList += "|" +chkObj[i].value ; 
			} else {
				chkMenuList = chkObj[i].value ; 
			}
		}
	}

	var urls =  "<c:url value="/meta/sitemap/regfavoritemenu.do"/>";
	
    ajax2Json(urls, chkMenuList, ibscallback);
	

}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	getFavoriteList();
	//getFavoriteListForFooter();
}

</script>
</head>

<body>


<div id="layer_div">
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="SITE.MAP" /></div> <!-- 사이트맵 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<div class="stit"><s:message code="SITE.MAP" /></div> <!-- 사이트맵 -->
<div class="bt01">

     <select id="mymenu" name ="mymenu">
						
	 </select> 
	 <button class="btn_frm_save" type="button" id="btnFavoriteSave" name="btnFavoriteSave"><s:message code="STRG" /></button> <!-- 저장 -->
</div>

<div style="clear:both; height:5px;"><span></span></div>
   <!-- 입력폼 시작 -->
   	<form id="frmInput" name="frmInput" action ="" method="">
   	
   	<div class = "tb_basic4">
    <table border="0" cellspacing="0" cellpadding="0"  summary=""  >
        <colgroup>
            <col style="width:25%;">
            <col style="width:25%;">
            <col style="width:25%;">
            <col style="width:25%;">
        </colgroup>
      <tbody>                   
                                           
           <c:set var="cnt" value="0" /> 
               
                <c:forEach var="sitemap" items="${result}">

                  <c:if test="${sitemap.menuLvl eq '0'}">
                     <c:if test="${cnt%4 eq '0'}">
                       <tr>
                     </c:if>
                       <c:set var="cnt" value="${cnt+1}" />  
                       <td style ="vertical-align:top; padding-top:20px;">
                         <div class ="sitemap_title">${sitemap.menuNm}</div>
                  </c:if>
               		
                   <c:if test="${sitemap.menuLvl eq '1'}">
                          <div class ="sitemap_title2">${sitemap.menuNm}</div>
                   </c:if>
                   <c:if test="${sitemap.menuLvl eq '2'}">
                          <div class = "sitemap_text1">
                              <c:if test="${sitemap.favoriteYn eq 'Y'}">
                                  <input  type="checkbox" name="menuid" value="${sitemap.menuId}" checked = "checked")>
                              </c:if>
                              <c:if test="${sitemap.favoriteYn eq 'N'}">
                                  <input  type="checkbox" name="menuid" value="${sitemap.menuId}">
                              </c:if>
                          <a style="color:#3c77c8;" href="<c:url value="${sitemap.filePath}"/>">${sitemap.menuNm}</a>
                          
                          </div>
                   </c:if>
                    <c:if test="${cnt%4 eq '-1'}">
                       </tr>
                     </c:if>
                                                   
            </c:forEach>
            </tr>            
      </tbody>
    </table>
    </div>
    </form>
</div>

</body>
