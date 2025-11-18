<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ page import="kr.wise.commons.WiseConfig"%>

<c:set var="toplogo"><s:message code="wiseda.site.name" /></c:set>
<c:set var="rqstcntYn"><s:message code="wiseda.rqst.cnt.yn" /></c:set>
<script type="text/javascript">
var dasubmenuheight = 0;
var a_clicked;

$(document).ready(function(){
	//페이지 호출시 처리할 액션...
// 	doAction('Add');
	$(window).resize();
	
});
$(window).on('load',function(){
	
 	//alert("${toplogo}");
	//마우스 오버 이미지 초기화
// 	imgConvert($('ul.ntop_menu a img'));
// 	imgConvert($('ul.ntop_info a img:gt(0)'));
	
	//$("#topmenu_"+"OBJ_00000013078").append('<ul class="n2menu_b" style="display:none;"></ul>');
	
	//로그아웃 처리...
	$("#btnLogout").click(function(){
		//로그아웃 하시겠어요???? 확인창...
		var message = "<s:message code="CNF.LOGOUT" arguments="${sessionScope.loginVO.name}" />";
		showMsgBox("CNF", message, logout);
	});
	
	
	<c:if test="${rqstcntYn == 'Y' }">
	//등록요청, 결재대상 건수 조회 및 처리....
	$.getJSON('<c:url value="/rqstCount.do"/>', function(data){
			if(data ==  null) return;
			$('#tmpCnt').text(data[0]);
			$('#rqstCnt').text(data[1]);
			$('#aprvCnt').text(data[2]);
	}); 
	</c:if>
	
	//정보변경 처리...
	$("#btnInfoChg").click(function(){
		//정보변경 창
		//alert("15.10.29");
		var param = ""; //$("form#frmInput").serialize();
    	openLayerPop("<c:url value='/commons/user/gousrInfoChngPop.do' />", 550, 350);
	});
	
	//시스템 관리 페이지 (관리자만 보이도록)
	<c:if test="${sessionScope.loginVO.isAdminYn == 'Y' }">
	$("#top_menu_01_05").click(function(){
		//관리자 페이지로 이동...
// 		alert("관리자 페이지로 이동합니다.");
// 		window.open('<c:url value="/commons/damgmt/subjarea/subj_lst.do" />', '_blank');
		window.open('<c:url value="/commons/damgmt/db/dbconntrg_lst.do" />', '_blank');
// 		opener.location.href = '<c:url value="/meta/stnd/stnd_lst.do" />';
		
	});
	</c:if>
	
	
	//메인메뉴 마우스 오버시
	$("ul.n4gnb_menu").click(function(){

// 		$("div.n2logo").hide();
		$("ul.n4gnb_sub").show();
		
		//메뉴 높이가 없을 경우 높이계산해서 처리한다.
		if (dasubmenuheight == 0) {
// 			alert($("ul.n2menu_b").eq(0).offset().top);
			var menuh = $(this).height()-$("ul.n4gnb_sub").eq(0).offset().top;
			dasubmenuheight = menuh;
			//alert(menuh);
			$("ul.n4gnb_sub").css('height', dasubmenuheight+'px');
		}
		//$("li[id^=topmenu_]").css('height', menuh+'px');
		$("div.n4gnb_line").css('display', 'block');
	});
	$("ul.n4gnb_menu").mouseleave(function(){

// 		$("div.n2logo").show();
		$("ul.n4gnb_sub").hide();
		$("div.n4gnb_line").css('display', 'none');
		$(a_clicked).removeClass('select');
		//$('ul.n4gnb_sub').removeClass('select');
	});
	
	//인포메뉴 마우스 클릭시
	$("a.da_info_menu").click(function(){
		//클릭 요소를 모두 초기화
		$("ul.n2info_func_menu").hide();
		$("a.da_info_menu").removeClass("n2info_func_select");
		//클릭한 요소만 셋팅
		$(this).addClass("n2info_func_select");
		$(this).next().show();
	});
	
	$("ul.n2info_func_menu").mouseleave(function(){$(this).hide(); $(this).prev().removeClass("n2info_func_select");});
	
	
	//표준분류 존재하면 selectboxsetup 호출
	if($("#stndAsrt").length > 0){
		selectBoxSetup($("#stndAsrt"));
		setTimeout(function(){$("#stndAsrt").change();},500);
	}
	
	/* $("a.n2info_loca_txt").click(function(){
		$("ul.n2info_loca_menu").hide();
		$(this).next().show();
	}); */
	
// 	$("ul.n2info_loca_menu").mouseleave(function(){$(this).hide();});
	
	//운영에서 사용시에는  메타만 사용하기 때문에 hide시킴(ibk신용정보)
// 	$("li#ntop_info_quicklink").hide();
	
	<c:if test="${SUB_MENU != null }">
	
	<c:forEach var="topmenu" items="${TOP_MENU}" varStatus="status">
	    setsubmenu(${status.index}, "${topmenu.menuId}");
	//$("li[id^=topmenu_]").eq(${status.index}).append('<ul class="n2menu_b" style="display:none;"></ul>');
// 	$("li#topmenu_"+"${topmenu.menuId}").append('<ul class="n2menu_b" style="display:none;"></ul>');
	</c:forEach>
	//setlocationmenu("${REQ_MENU.rootMenuId}", "${REQ_MENU.uppMenuId}" );
	</c:if>
	
	

});

function selFunc(obj) {
	$(a_clicked).removeClass('select');
	$(obj).addClass('select');
	//$('ul.n4gnb_sub').addClass('select');
	a_clicked = obj;
}

//화면 사이즈 변경시 메뉴 사이즈 변경
$(window).resize(function(){

	var cnttopmenu = $("li[id^=topmenu_]").length;
	//var totwidth = $("ul.n4gnb_menu").width() - $("div.logo").width();
	var totwidth = $("ul.n4gnb_menu").width()-30;
	var submenuwidth = Math.floor(totwidth / cnttopmenu);
	//var submenuwidth = Math.round(totwidth / cnttopmenu);
	//if (submenuwidth < 140) submenuwidth = 140; 
	
	//console.log(submenuwidth);
	
	//alert("cnttopmenu len >> " + cnttopmenu + "\ntotwidth >>> " + totwidth + "\nsubmenuwidth >>> " + submenuwidth);
	
	$("li[id^=topmenu_]").width(submenuwidth);
	
	dasubmenuheight = 0;
	$("ul.n4gnb_sub").css('height', 'auto');
});
	

function logout(){
	location.href="<c:url value="/logout" />";
} 


<c:if test="${SUB_MENU != null }">
//메뉴 초기화
var submenujson = ${SUB_MENU};

function setsubmenu(idx, menuid) {
	var submenulist = submenujson[menuid];
	
	var cnt = submenulist.length;
	$("li#topmenu_"+menuid).append('<ul class="n4gnb_sub" style="display:none;"></ul>');
		
	 for(i=0;i<cnt;i++) {
		
		 var url = "#";
		 var tmpurl = submenulist[i].filePath;
		 
		 if(!isBlankStr(tmpurl) && tmpurl != "link" && tmpurl != "dir") {
			 url =  containerPath+tmpurl;
		 }
		 
		if(submenulist[i].menuLvl == 1) {
			$("li#topmenu_"+menuid+" ul.n4gnb_sub").append('<li id="menuid_'+submenulist[i].menuId+'"><a href="'+url+'">'+submenulist[i].menuNm+'</a><ul class="n2menu_c"></ul></li>');
		} else if (submenulist[i].menuLvl == 2) {
			$("li#menuid_"+submenulist[i].uppMenuId+" ul.n2menu_c").append('<li><a href="'+url+'">'+submenulist[i].menuNm+'</a></li>');
		}
	 }
	
}

function setlocationmenu(topmenuid, uppmenuid) {
	var submenulist = submenujson[topmenuid];
	if (submenulist == null) return;
	var cnt = submenulist.length;
	 for(i=0;i<cnt;i++) {
		 
		 var url = "#";
		 var tmpurl = submenulist[i].filePath;
		 
		 if(!isBlankStr(tmpurl) && tmpurl != "link" && tmpurl != "dir") {
			 url =  containerPath+tmpurl;
		 }
		 
		 if(submenulist[i].menuLvl == 1) {
			 $("ul.n2info_loca_menu").eq(submenulist[i].menuLvl).append('<li><a href="'+url+'">'+submenulist[i].menuNm+'</a></li>');
		 } else if (submenulist[i].menuLvl == 2 && uppmenuid == submenulist[i].uppMenuId) {
			 $("ul.n2info_loca_menu").eq(submenulist[i].menuLvl).append('<li><a href="'+url+'">'+submenulist[i].menuNm+'</a></li>');
		 }
		 
		 
	 }
}
</c:if>
</script>

    <!-- 상단메뉴 시작 -->
<div class="n4top">
	<div class="n4sys">
		<ul>
	        <c:choose>
		     	<c:when test="${sessionScope.mainSite == 'META' }">
		     		<li><a href="<c:url value="/main.do"/>" target="_blank"><s:message code="META.DATA.SYS" /></a></a></li>
		     	</c:when>
		     	<c:when test="${sessionScope.mainSite == 'DQ' }">
		     		<li><a href="<c:url value="/dqmain.do"/>" target="_blank"><s:message code="DATA.QLTY.SYS" /></a></a></li>
		     	</c:when>
		     	<c:otherwise>
		     		<li><a href="<c:url value="/main.do"/>" target="_blank"><s:message code="META.DATA.SYS" /></a></a></li>
	        		<li><a href="<c:url value="/dqmain.do"/>" target="_blank"><s:message code="DATA.QLTY.SYS" /></a></a></li>
		     	</c:otherwise>
		    </c:choose>
	        
	        <c:if test="${sessionScope.loginVO.isAdminYn == 'Y' }">
				<li id="top_menu_01_05" style="display: block;">
					<a class="ic_set"><%-- <img src="<c:url value="/images/n2info_setting.png" />" alt="<s:message code='SYS.MNG' />"> --%><s:message code="MGR"/></a> <!-- 시스템관리 -->
				</li>
			</c:if>
	    </ul>
	</div>
	<div class="n4gnb">
		<c:choose>
			<c:when test="${toplogo == 'SLC'}">
			  	<div class="logo"><a href="<c:url value="/" />"><img src="<c:url value="/images/logo/logo_slc.png" />" style="padding: 7px; width: 160px; height: 30px;" alt="<s:message code='SLC.META' />" /></a></div> <!-- SLC메타 -->
			</c:when>
			<c:otherwise>
		   		<div class="logo"><a href="<c:url value="/dqmain.do" />"><img src="<c:url value="/img/logo_dq.png" />" alt="<s:message code='WISE.DQ' />" /></a></div> <!-- 위세디큐 -->
		<%--    		<div class="n2logo"><a href="<c:url value="/dqmain.do" />"></a></div> <!-- 위세디큐 --> --%>
			</c:otherwise>
		</c:choose>
        <ul class="n4gnb_menu">
       <c:forEach var="topmenu" items="${TOP_MENU}" varStatus="status">
       	 <li id="topmenu_${topmenu.menuId}">
       	 	<c:choose>
       	 		<c:when test="${topmenu.filePath != null and topmenu.filePath != 'link' and topmenu.filePath != 'dir' }">
       	 		<a onclick="selFunc(this);">
       	 		</c:when>
       	 		<c:otherwise>
       	 		<a onclick="selFunc(this);">
       	 		</c:otherwise>
       	 	</c:choose>
       	 	${topmenu.menuNm}</a>
       	 	 
       	 </li>
       </c:forEach>
        </ul>
        
        <div class="n4gnb_line"><span></span></div>
    </div>
</div>

<!-- 상단메뉴 끝 -->

    <div class="n4info shadow">
    	
        <div class="n4info_loca">
        	${REQ_MENU.fullPath.replaceAll(">", " > ") }
        </div>
            
		<ul class="n4info_list">
			<li><a href="#" class="user">${sessionScope.loginVO.name}<span>님 안녕하세요</span></a></li> <!-- 님 -->
			<li id="btnLogout""><a class="logout"><s:message code="LOG.OUT" /></a></li> <!-- 로그아웃 -->
			<li id="btnInfoChg"><a class="pass"><s:message code="PWD.CHG" /></a></li> <!-- 비밀번호변경 -->
			<c:if test="${rqstcntYn == 'Y' }">
				<li>
					<%-- <a href="#" class="da_info_menu"><img src="<c:url value="/images/n2info_alarm.png"/>" alt="<s:message code='ALAM' />"></a> --%> <!-- 알림 -->
					<ul class="n4info_alarm" style="display:block; width: auto;">
						<li><a class="ic_none" href="<c:url value="/meta/stnd/rqstmy_lst.do?linkFlag=3"/>"><s:message code="TMPR.STRG" /> <strong id="tmpCnt">0</strong><s:message code="CNT" /></a></li> <!-- 임시저장 건-->
						<li><a href="<c:url value="/meta/stnd/rqstmy_lst.do?linkFlag=2"/>"><s:message code="REG.DEMD" /> <strong id="rqstCnt">0</strong><s:message code="CNT" /></a></li> <!-- 등록요청 건-->
						<li><a href="<c:url value="/meta/stnd/rqsttodo_lst.do?linkFlag=1"/>"><s:message code="APRL.TRGT" /> <strong id="aprvCnt">0</strong><s:message code="CNT" /></a></li> <!-- 결재대상 건-->
					</ul>
				</li>
			</c:if>
		</ul>
    </div>

<!-- 메뉴 메인 제목 -->
<div class="ntit" style="display: none;">
	<div class="ntit_tit">
		<c:if test="${REQ_MENU != null }">
		  ${REQ_MENU.menuNm }
		</c:if>
	</div>
    <div class="ntit_loca">
		<c:if test="${REQ_MENU != null }">
		  ${REQ_MENU.fullPath }
		</c:if>
   </div>
</div>
