<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>


<script type="text/javascript">
$(document).ready(function(){
	$('div.gnb_all').show();
});
	
</script>

<!-- 전체보기 시작 -->
<div class="gnb_all" style="position:absolute; top:110px; left:0px; z-index:10; display:none;">
    <div class="gnb_all_top">
        <div class="gnb_all_tit"><s:message code="WHL.VIEW" /></div> <!-- 전체보기 -->
        <div class="gnb_all_close"><a href="#"><img src='<c:url value="/img/gnb_all_close.gif" />' alt="닫기"></a></div>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit"><s:message code="QLTY.MNG" /></div> <!-- 품질관리 -->
        <ul class="gnb_all_menu_list">
            <li><a href="#"><s:message code="QLTY.MNG.SYST" /></a></li> <!-- 품질관리체계 -->
            <li><a href="#"><s:message code="SYS.PRES" /></a></li> <!-- 시스템 현황 -->
            <li><a href="#"><s:message code="BZWR.PROC" /></a></li> <!-- 업무 프로세스 -->
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit"><s:message code="PLCY.GUIDE" /></div> <!-- 정책/지침 -->
        <ul class="gnb_all_menu_list">
            <li><a href="#"><s:message code="LAW.NTIF" /></a></li> <!-- 법령/고시 -->
            <li><a href="#"><s:message code="GUIDE.MANUL" /></a></li> <!-- 지침/매뉴얼 -->
            <li><a href="#"><s:message code="ISO9001.QLTY.DOCU" /></a></li> <!-- ISO9001 품질문서 -->
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit"><s:message code="DEV.GUIDE" /></div> <!-- 개발가이드 -->
        <ul class="gnb_all_menu_list">
            <li><a href="#"><s:message code="DEV.GUIDE" /></a></li> <!-- 개발가이드 -->
            <li><a href="#"><s:message code="CALC.MNG" /></a></li> <!-- 산출물관리 -->
            <li><a href="#"><s:message code="DTA.WIT" /></a></li> <!-- 자료실 -->
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit"><s:message code="MY.BZWR" /></div> <!-- 나의업무 -->
        <ul class="gnb_all_menu_list">
            <li><a href="#"><s:message code="REG.DEMD.INQ" /></a></li> <!-- 등록요청 조회 -->
            <li><a href="#"><s:message code="APRL.TRGT.INQ" /></a></li> <!-- 결재대상 조회 -->
            <li><a href="#"><s:message code="QLTY.ACVT.INQ" /></a></li> <!-- 품질활동 조회 -->
            <li><a href="#"><s:message code="TBL.CHG.R.CHG" /></a></li> <!-- 테이블 담당자 변경 -->
            <li><a href="#"><s:message code="PGM.CHG.R.CHG" /></a></li> <!-- 프로그램 담당자 변경 -->
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit"><s:message code="QLTY.MNG.PRES" /></div> <!-- 품질관리현황 -->
        <ul class="gnb_all_menu_list">
            <li><a href="#"><s:message code="DATA.SYNE.PRES" /></a></li> <!-- 데이터 종합현황 -->
            <li><a href="#"><s:message code="DATA.QLTY.PRES" /></a></li> <!-- 데이터 품질현황 -->
            <li><a href="#"><s:message code="PGM.HOLD.PRES" /></a></li> <!-- 프로그램 보유현황 -->
        </ul>
    </div>
    <div class="gnb_all_menu">
        <div class="gnb_all_menu_tit"><s:message code="COMMUNITY" /></div> <!-- 커뮤니티 -->
        <ul class="gnb_all_menu_list">
            <li><a href="#"><s:message code="ALAM.YARD" /></a></li> <!-- 알림마당 -->
            <li><a href="#"><s:message code="KNOW.SHARE" /></a></li> <!-- 지식나눔 -->
            <li><a href="#"><s:message code="WORD.RING.YARD" />????</a></li> <!-- 어울림마당 -->
            <li><a href="#"><s:message code="QNA" /></a></li> <!-- 묻고답하기 -->
        </ul>
    </div>
</div>
<!-- 전체보기 끝 -->




<!-- Quick Link 시작 -->
<div class="quick" style="position:absolute; top:120px; left:1010px; display:none;">
    <div><img src='<c:url value="/img/quick_tit.gif" />' alt="Quick Link"></div>
    <ul class="quick_list">
        <li><a href="#" target="_blank"><img src='<c:url value="/img/quick_bt_01.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_01_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_01.gif" />'" alt="<s:message code='META.DATA' />"></a></li><!-- 메타데이터 -->
        <li><a href="#" target="_blank"><img src='<c:url value="/img/quick_bt_02.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_02_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_02.gif" />'" alt="<s:message code='DATA.QLTY' />"></a></li> <!-- 데이터품질 -->
        <li><a href="#" target="_blank"><img src='<c:url value="/img/quick_bt_03.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_03_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_03.gif" />'" alt="<s:message code='IMPACHART.ANLY' />"></a></li> <!-- 영향도분석 -->
        <li><a href="#" target="_blank"><img src='<c:url value="/img/quick_bt_04.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_04_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_04.gif" />'" alt="<s:message code='SW.SHAPE.MNG' />"></a></li> <!-- SW형상관리 -->
        <li><a href="#" target="_blank"><img src='<c:url value="/img/quick_bt_05.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_05_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_05.gif" />'" alt="<s:message code='DIST.MNG' />"></a></li> <!-- 분배관리 -->
        <li><a href="#" target="_blank"><img src='<c:url value="/img/quick_bt_06.gif" />' onMouseOver="this.src='<c:url value="/img/quick_bt_06_.gif" />'" onMouseOut="this.src='<c:url value="/img/quick_bt_06.gif" />'" alt="<s:message code='IT.TALENT.MNG' />"></a></li> <!-- IT인재관리 -->
    </ul>
</div>
<!-- Quick Link 끝 -->