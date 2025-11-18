<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>


<script type="text/javascript">
$(document).ready(function(){
// $(window).on('load',function(){
	//요청서 셋팅....
	var npoptop = $(".n4info").offset().top + $('.n4info').height();
 	
 	$("#npop_approve_sts").css({
 		'position' : 'fixed',
 		'z-index' : '150',
 		'top'     : npoptop-2,
 		'right'   :  '0px',
 	});
	
	var widthappr = $("#npop_cont_apprsts").width();
// 	alert(widthappr);
	$("#npop_approve_sts").css({
 		'marginRight': -widthappr
 	});
	
	$("div.npop_bt_right").click(function(){
		//alert('click');
// 		$("#npop_cont_apprsts").effect('slide', {direction:'right'},  500);
// 		return false;
		if($(".npop_bt_right div").hasClass("npop_bt_right_open") == true) {
			$("#npop_approve_sts").stop().animate({'marginRight': 0}, 300, function(){
				$(".npop_bt_right div").removeClass("npop_bt_right_open").addClass('npop_bt_right_close');
			});
		} else {
			$("#npop_approve_sts").stop().animate({'marginRight': -widthappr}, 300, function(){
				$(".npop_bt_right div").removeClass("npop_bt_right_close").addClass('npop_bt_right_open');
			});
		}
		
	});
	
});
 
</script>

<!-- 오른쪽 팝업 시작 -->
<div id="npop_approve_sts" class="npop" >
	<div class="npop_bt_right">
    	<div class="npop_bt_right_open"></div>
    </div>
	<div id="npop_cont_apprsts" class="npop_cont" >
        <div class="npop_content">
        	<iframe src='<c:url value="/commons/damgmt/approve/popup/approveProcess_pop.do"/>' frameborder='0' height='230px' width='600px'  scrolling='no' ></iframe>
        </div>
    </div>
</div>
<!-- 오른쪽 팝업 끝 -->
