<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${SUB_MENU != null}">
<script type="text/javascript">
 $(window).on('load',function(){
	 
	 //메뉴 초기화
	 var submenujson = ${SUB_MENU};
	 
	 var lvl1menu = "";
	 var lvl2menu = "";
	 var cnt = submenujson.length;
	 
	 for(i=0;i<cnt;i++) {
		 //최상위 메뉴일 경우...
		 if (submenujson[i].menuLvl == 0) {
			 $("div.nleft_tit").text(submenujson[i].menuNm);
		 } else if (submenujson[i].menuLvl == 1) {
			 lvl1menu = "";
			 lvl1menu += '<div class="side_menu_acco">';
			 lvl1menu += '<h3>'+submenujson[i].menuNm+'</h3>';
			 lvl1menu += '<div id="menuid_'+submenujson[i].menuId+'">';
			    
			 lvl1menu += '</div>';
			 lvl1menu += '</div>';
			 $("div.nleft_menu").append(lvl1menu);
		 } else if (submenujson[i].menuLvl == 2) {
			 var url = "#";
			 var tmpurl = submenujson[i].filePath;
			 
			 if(!isBlankStr(tmpurl) && tmpurl != "link" && tmpurl != "dir") {
				 url =  containerPath+tmpurl;
			 }
			 lvl2menu = "";
			 lvl2menu += '<div class="smenu_folder_03"><span class="smenu_link ui-icon ui-icon-newwin"></span>';
			 lvl2menu += '<a href="'+url+'" >'+submenujson[i].menuNm+'</a></div>';
			 
			 $("#menuid_"+submenujson[i].uppMenuId ).append(lvl2menu);
		 }
	 }
	 
	 	$("#menuLeft").css({
	 		'position' : 'fixed',
	 		'z-index' : '1000',
	 		'marginLeft': '-281px'
	 		
	 	});
		$('.nleft_bt_sub').click(function(){
			
// 		    if($(".nleft_bt_sub").hasClass("smenu_bt_bar_in") == true) 
// 			{
				$("#menuLeft").stop().animate({'marginLeft':'10px'},200).animate({'marginLeft': '0px'}, 100, function(){
					
					$("#mainContent").one('click', function(){
						$("#menuLeft").stop().animate({'marginLeft':'-284px'},200).animate({'marginLeft':'-281px'},100);
					});
					
				});
				
			
// 				$(".nleft_bt_sub").removeClass();
// 				$(".nleft_bt_sub").toggleClass("smenu_bt_bar_out");
										
				//var vLeftframe = document.all.leftframe;
				//vLeftframe.style.visibility = "hidden";
// 			} else {
// 				$("#menuLeft").stop().animate({'marginLeft':'-284px'},200).animate({'marginLeft':'-276px'},100);
			
// 				$(".nleft_bt_sub").removeClass();
// 				$(".nleft_bt_sub").toggleClass("smenu_bt_bar_in");
				//var vLeftframe = document.all.leftframe;
				//vLeftframe.style.visibility = "visible";
// 			}
		});
		//마우스 오버가 아닐 경우...
// 		$(".nleft_cont").mouseleave(function(){
// 			$("#menuLeft").stop().animate({'marginLeft':'-284px'},200).animate({'marginLeft':'-281px'},100);
// 		});
		
// 		$('.nleft_bt_sub').mouseenter(function(){
// 			$(this).click();
// 		});
	 
	 $('.side_menu_acco').accordion({
	      collapsible: true, 
// 	      active: false,
	      heightStyle: "content"
	    });
	 
	 //마우스 오버시 이미지 변환 
// 	 imgConvert($('div.smenu_favor_bt a img'));
	 
	 $("div.nleft").show();
	 
	 
 });
 

</script>
    <!-- 왼쪽서브메뉴 시작 -->
    
	 
    <div class="nleft" style="display: none;"> <!-- left:-281px; -->
    	<div class="nleft_cont" style="z-index: 1100;">
    		<div class="nleft_tit"><s:message code="QLTY.BASE" /></div> <!-- 품질기준 -->
            <div class="nleft_menu">
<!--             	제이쿼리메뉴 들어가면 됨. -->

            </div>
        </div>
        <div class="nleft_bt_sub"><img src="<c:url value="/images/nleft_bt_sub.gif"/>" alt="<s:message code='SB.MENU' />"></div> <!-- 서브메뉴 -->
    </div>
    <!-- 왼쪽서브메뉴 끝 -->
</c:if>
