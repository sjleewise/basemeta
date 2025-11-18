<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><s:message code="QLTY.MNG" /></title> <!-- 품질관리 -->
<!-- <link rel="stylesheet" type="text/css" href="css/design.css"> -->
</head>
<body>
<div class="wrap">
	<!-- top 시작 -->
	<div class="top">
    	<h1 class="top_logo"><a href="#"><img src="/images/gnb/m_top_wiseda.png" alt="WISEDA"></a><a href="#"></h1>
        <ul class="top_info">
        	<li><span class="top_info_user"><s:message code="META.MGR" /></span><s:message code="MSG.HELLO" /></li> <!-- 메타관리자 님 안녕하세요 -->
            <li><s:message code="FLIN.ING" /> <span class="top_info_num">74</span><s:message code="CNT" /></li> <!-- 작성중 건 -->
            <li><s:message code="APRL" /> <span class="top_info_num">14</span><s:message code="CNT" /></li> <!-- 결재 건 -->
            <li><a href="#"><s:message code="LOG.OUT" /></a></li> <!-- 로그아웃 -->
            <li>&nbsp;</li>
        </ul>
    </div>
    <!-- top 끝 -->
    
    
    
    <!-- gnb 시작 -->
    <div class="gnb">
        <ul class="gnb_bt">
            <li><a href="#"><img src="img/gnb_01.gif" onMouseOver="this.src='img/gnb_01_.gif'" onMouseOut="this.src='img/gnb_01.gif'" alt="<s:message code='QLTY.MNG' />"></a></li><!-- 품질관리 -->
            <li><a href="#"><img src="img/gnb_02.gif" onMouseOver="this.src='img/gnb_02_.gif'" onMouseOut="this.src='img/gnb_02.gif'" alt="<s:message code='PLCY.GUIDE' />"></a></li> <!-- 정책/지침 -->
            <li><a href="#"><img src="img/gnb_03.gif" onMouseOver="this.src='img/gnb_03_.gif'" onMouseOut="this.src='img/gnb_03.gif'" alt="<s:message code='DEV.GUIDE' />"></a></li> <!-- 개발가이드 -->
            <li><a href="#"><img src="img/gnb_04.gif" onMouseOver="this.src='img/gnb_04_.gif'" onMouseOut="this.src='img/gnb_04.gif'" alt="<s:message code='MY.BZWR' />"></a></li> <!-- 나의업무 -->
            <li><a href="#"><img src="img/gnb_05.gif" onMouseOver="this.src='img/gnb_05_.gif'" onMouseOut="this.src='img/gnb_05.gif'" alt="<s:message code='QLTY.MNG.PRES' />"></a></li> <!-- 품질관리현황 -->
            <li><a href="#"><img src="img/gnb_06.gif" onMouseOver="this.src='img/gnb_06_.gif'" onMouseOut="this.src='img/gnb_06.gif'" alt="<s:message code='COMMUNITY' />"></a></li> <!-- 커뮤니티 -->
            <li><a href="#"><img src="img/gnb_07_.gif" alt="<s:message code='WHL.VIEW' />"></a></li> <!-- 전체보기 -->
        </ul>
    </div>
    <!-- gnb 끝 -->
    
    
    
    <!-- 메인 컨테이너 시작 -->
    <div class="container">
    
    	<div class="main_search" style="z-index:5;">
        	<div class="main_search_tit"><img src="img/main_search_tit.gif" alt="<s:message code='INTG.INQ' />"></div> <!-- 통합검색 -->
            <div class="main_search_input"><input type="text"><button class="main_search_bt"><s:message code="INQ" /></button></div> <!-- 검색 -->
            <div class="main_search_txt"><s:message code="MSG.INTG.INQ.CNTN.FIND" /></div> <!-- 통합검색을 이용하시면 원하시는 내용을 더욱 빠르게 찾아볼 수 있습니다. -->
            <div class="main_search_popular">
            	<div class="main_search_popular_tit"><img src="img/main_search_popular_tit.gif" alt="<s:message code='POPULA.INQ.WORD' />"></div> <!-- 인기검색어 -->
                <div class="main_search_popular_link">
                	<a href="#"><s:message code="QLTY.MNG" /></a> <!-- 품질관리 -->
                    <a href="#"><s:message code="STRD.MDEL" /></a> <!-- 표준모델 -->
                    <a href="#"><s:message code="META.DATA" /> <s:message code="META.DATA" /> <s:message code="META.DATA" /></a> <!-- 메타데이터 메타데이터 메타데이터 -->
                </div>
            </div>
        </div>
        

        <div class="main_icon">
        	<ul class="main_icon_list">
            	<li><a href="#"><img src="img/main_icon_01.gif" onMouseOver="this.src='img/main_icon_01_.gif'" onMouseOut="this.src='img/main_icon_01.gif'" alt="<s:message code='STRDZATION' />"></a></li> <!-- 표준화 -->
                <li><a href="#"><img src="img/main_icon_02.gif" onMouseOver="this.src='img/main_icon_02_.gif'" onMouseOut="this.src='img/main_icon_02.gif'" alt="<s:message code='TBL.REG' />"></a></li> <!-- 테이블등록 -->
                <li><a href="#"><img src="img/main_icon_03.gif" onMouseOver="this.src='img/main_icon_03_.gif'" onMouseOut="this.src='img/main_icon_03.gif'" alt="<s:message code='CD.MNG' />"></a></li> <!-- 코드관리 -->
                <li><a href="#"><img src="img/main_icon_04.gif" onMouseOver="this.src='img/main_icon_04_.gif'" onMouseOut="this.src='img/main_icon_04.gif'" alt="<s:message code='BZWR.RULE' />"></a></li> <!-- 업무규칙 -->
                <li class="bd_none"><a href="#"><img src="img/main_icon_05.gif" onMouseOver="this.src='img/main_icon_05_.gif'" onMouseOut="this.src='img/main_icon_05.gif'" alt="<s:message code='PROF' />"></a></li> <!-- 프로파일 -->
            </ul>
        </div>
        
        
        <div class="main_bbs">
        	<div class="main_tab_tit">
            	<ul class="main_tab">
                	<li class="main_tab_select"><a href="#"><s:message code="PBNC.MTR" /></a></li> <!-- 공지사항 -->
                </ul>
                <div class="main_more"><a href="#"><span>+</span> <s:message code="VIEW.MORE" /></a></div> <!-- 더보기 -->
            </div>
            <ul class="main_bbs_list">
            	<li>
                	<div class="main_bbs_item"><a href="#"><s:message code="MSG.QLTY.MNG.POTAL.OPEN" /> <s:message code="MSG.QLTY.MNG.POTAL.OPEN" /></a></div> <!-- 품질관리 포탈이 오픈하였습니다. 품질관리 포탈이 오픈하였습니다. -->
	                <div class="main_bbs_date">2013.08.28</div>
                </li>
                <li>
                	<div class="main_bbs_item"><a href="#"><s:message code="MSG.QLTY.MNG.POTAL.OPEN" /></a></div> <!-- 품질관리 포탈이 오픈하였습니다. -->
	                <div class="main_bbs_date">2013.08.28</div>
                </li>
                <li>
                	<div class="main_bbs_item"><a href="#"><s:message code="MSG.QLTY.MNG.POTAL.OPEN" /></a></div> <!-- 품질관리 포탈이 오픈하였습니다. -->
	                <div class="main_bbs_date">2013.08.28</div>
                </li>
                <li>
                	<div class="main_bbs_item"><a href="#"><s:message code="MSG.QLTY.MNG.POTAL.OPEN" /></a></div> <!-- 품질관리 포탈이 오픈하였습니다. -->
	                <div class="main_bbs_date">2013.08.28</div>
                </li>
                <li>
                	<div class="main_bbs_item"><a href="#"><s:message code="MSG.QLTY.MNG.POTAL.OPEN" /></a></div> <!-- 품질관리 포탈이 오픈하였습니다. -->
	                <div class="main_bbs_date">2013.08.28</div>
                </li>
            </ul>
        </div>
        
        
        <div class="main_data">
        	<div class="main_tab_tit">
            	<ul class="main_tab">
                	<li class="main_tab_select"><a href="#"><s:message code="REG.DEMD.PRES" /></a></li> <!-- 등록요청현황 -->
                    <li><a href="#"><s:message code="APRL.TRGT.PRES" /></a></li> <!-- 결재대상현황 -->
                </ul>
                <div class="main_more"><a href="#"><span>+</span> <s:message code="VIEW.MORE" /></a></div> <!-- 더보기 -->
            </div>
            <table border="0" cellspacing="0" cellpadding="0" class="main_table" summary="<s:message code='REG.DEMD.PRES' />"> <!-- 등록요청현황 -->
            <caption>
            <s:message code="REG.DEMD.PRES" /> <!-- 등록요청현황 -->
            </caption>
              <tr>
                <th><s:message code="DEMD.DSTC" /></th> <!-- 요청구분 -->
                <th width="270"><s:message code="DEMD.NM" /></th> <!-- 요청명 -->
                <th><s:message code="DEMD.DT" /></th> <!-- 요청일자 -->
                <th class="bd_none"><s:message code="APRV.PRGS.LVL" /></th> <!-- 승인진행레벨 -->
              </tr>
              <tr>
                <td class="txt_center"><s:message code="MAPG.DFNT.P" /></td> <!-- 매핑정의서 -->
                <td><a href="#" class="wd260 ellipsis"><s:message code=TRNS.YM.CASE"/></a></td> <!-- TRNS_YM 외 21건 TRNS_YM 외 21건 TRNS_YM 외 21건 TRNS_YM 외 21건 TRNS_YM 외 21건 -->
                <td class="txt_center">2013.08.28</td>
                <td class="txt_center bd_none"><s:message code="MODELER" /></td> <!-- 모델러 -->
              </tr>
              <tr>
                <td class="txt_center"><s:message code="MAPG.DFNT.P" /></td> <!-- 매핑정의서 -->
                <td><a href="#" class="wd260 ellipsis"><s:message code=TRNS.YM.CASE1"/></a></td> <!-- TRNS_YM 외 21건 -->
                <td class="txt_center">2013.08.28</td>
                <td class="txt_center bd_none"><s:message code="MODELER" /></td> <!-- 모델러 -->
              </tr>
              <tr>
                <td class="txt_center"><s:message code="MAPG.DFNT.P" /></td> <!-- 매핑정의서 -->
                <td><a href="#" class="wd260 ellipsis"><s:message code=TRNS.YM.CASE1"/></a></td> <!-- TRNS_YM 외 21건 -->
                <td class="txt_center">2013.08.28</td>
                <td class="txt_center bd_none"><s:message code="MODELER" /></td> <!-- 모델러 -->
              </tr>
              <tr>
                <td class="txt_center"><s:message code="MAPG.DFNT.P" /></td> <!-- 매핑정의서 -->
                <td><a href="#" class="wd260 ellipsis"><s:message code=TRNS.YM.CASE1"/></a></td> <!-- TRNS_YM 외 21건 -->
                <td class="txt_center">2013.08.28</td>
                <td class="txt_center bd_none"><s:message code="MODELER" /></td> <!-- 모델러 -->
              </tr>
              <tr>
                <td class="txt_center"><s:message code="MAPG.DFNT.P" /></td> <!-- 매핑정의서 -->
                <td><a href="#" class="wd260 ellipsis"><s:message code=TRNS.YM.CASE1"/></a></td> <!-- TRNS_YM 외 21건 -->
                <td class="txt_center">2013.08.28</td>
                <td class="txt_center bd_none"><s:message code="MODELER" /></td> <!-- 모델러 -->
              </tr>
            </table>
        </div>
        
        <div class="main_link">
        	<div class="main_file">
            	<div class="main_file_tit"><s:message code="RQRD.INSL.FILE" /></div> <!-- 필수설치파일 -->
                <ul class="main_file_cont">
                	<li><a href="#"><img src="img/main_file_grid.gif" alt="GRID"></a></li>
                    <li><a href="#"><img src="img/main_file_erd.gif" alt="ERD Viewer"></a></li>
                </ul>
            </div>
            <div class="main_quick">
            	<div class="main_quick_tit"><s:message code="SHOTCUT" /></div> <!-- 바로가기 -->
                <ul class="main_quick_cont">
                	<li><a href="#"><s:message code="STRD.ITEM.INQ" /></a></li> <!-- 표준항목조회 -->
                    <li><a href="#"><s:message code="TBL.INQ" /></a></li> <!-- 테이블조회 -->
                    <li><a href="#"><s:message code="SUBJ.TRRT.INQ" /></a></li> <!-- 주제영역조회 -->
                    <li><a href="#"><s:message code="ELCT.MENU" /></a></li> <!-- 전자메뉴얼 -->
                    <li><a href="#"><s:message code="DATA.QLTY.CNFR" /></a></li> <!-- 데이터품질확인 -->
                </ul>
            </div>
        </div>
        <div class="main_chart_01">
        	<div class="main_chart_01_tit"><s:message code="MDEL.VS.DB.MTCH.RT" /></div> <!-- 모델 vs DB 일치율 -->
            <div class="main_chart_01_cont"><img src="img/ex_chart_01.gif" alt=""></div> <!-- 차트 사이즈 132px X 157px -->
        </div>
        <div class="main_chart_02">
        	<div class="main_chart_02_tit"><s:message code="PTRN.EROR.RT" /></div> <!-- 유형별 오류율 -->
            <div class="main_chart_02_cont"><img src="img/ex_chart_02.gif" alt=""></div> <!-- 차트 사이즈 132px X 157px -->
        </div>
    </div>
    <!-- 메인 컨테이너 끝 -->
    
    
    
    
    <!-- footer 시작 -->
    <div class="footer">
    	<div class="footer_cont">
        	<div class="footer_logo"><img src="img/footer_logo.gif" alt="평생건강 with you"></div>
            <div class="footer_address">121-749) <s:message code="CUST1.CMP.DTAD" /></div> <!-- 서울특별시 마포구 독막로 311(염리동) 국민건강보험공단 -->
            <div class="footer_copy">Copyright(C) 2013 By National Health Insurance. All right reserved.</div>
        </div>
    </div>
    <!-- footer 끝 -->
    
    
    

</div>







<!-- 전체보기 시작 -->
<div class="gnb_all" style="position:absolute; top:110px; left:0px; z-index:10; display:none;">
    <div class="gnb_all_top">
        <div class="gnb_all_tit"><s:message code="WHL.VIEW" /></div> <!-- 전체보기 -->
        <div class="gnb_all_close"><a href="#"><img src="img/gnb_all_close.gif" alt="<s:message code='CLOSE'/>"></a></div> <!-- 닫기 -->
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
            <li><a href="#"><s:message code="LAW.NTIF" /></a></li><!--  법령/고시 -->
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
    <div><img src="img/quick_tit.gif" alt="Quick Link"></div>
    <ul class="quick_list">
        <li><a href="#" target="_blank"><img src="img/quick_bt_01.gif" onMouseOver="this.src='img/quick_bt_01_.gif'" onMouseOut="this.src='img/quick_bt_01.gif'" alt="<s:message code='META.DATA' />"></a></li> <!-- 메타데이터 -->
        <li><a href="#" target="_blank"><img src="img/quick_bt_02.gif" onMouseOver="this.src='img/quick_bt_02_.gif'" onMouseOut="this.src='img/quick_bt_02.gif'" alt="<s:message code='DATA.QLTY' />"></a></li> <!-- 데이터품질 -->
        <li><a href="#" target="_blank"><img src="img/quick_bt_03.gif" onMouseOver="this.src='img/quick_bt_03_.gif'" onMouseOut="this.src='img/quick_bt_03.gif'" alt="<s:message code='IMPACHART.ANLY' />"></a></li> <!-- 영향도분석 -->
        <li><a href="#" target="_blank"><img src="img/quick_bt_04.gif" onMouseOver="this.src='img/quick_bt_04_.gif'" onMouseOut="this.src='img/quick_bt_04.gif'" alt="<s:message code='SW.SHAPE.MNG' />"></a></li> <!-- SW형상관리 -->
        <li><a href="#" target="_blank"><img src="img/quick_bt_05.gif" onMouseOver="this.src='img/quick_bt_05_.gif'" onMouseOut="this.src='img/quick_bt_05.gif'" alt="<s:message code='DIST.MNG' />"></a></li> <!-- 분배관리 -->
        <li><a href="#" target="_blank"><img src="img/quick_bt_06.gif" onMouseOver="this.src='img/quick_bt_06_.gif'" onMouseOut="this.src='img/quick_bt_06.gif'" alt="<s:message code='IT.TALENT.MNG' />"></a></li> <!-- IT인재관리 -->
    </ul>
</div>
<!-- Quick Link 끝 -->







</body>
</html>
