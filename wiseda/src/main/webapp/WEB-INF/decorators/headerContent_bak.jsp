<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>


<script type="text/javascript">
function logout(){
	var r=confirm("로그아웃을 하시겠습니까?");
	if (r==true)
	  {
		location.href="/cust/view/logout.do?gubun=1";
	  }
	if(r==false)
	{
		return;
	}
} 
</script>

    <!-- 상단메뉴 시작 -->
    <div class="top">
    <table width="100%" height="59" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.LAYOUT.TBL' />"> <!-- 레이아웃을 위한 테이블입니다. -->
      <tr>
        <td width="200" rowspan="2"><h1><a href="#"><img src="<c:url value="/images/top_logo.gif"/>" alt="(주)위세아이텍" /></a></h1></td>
        <td height="27">
        <ul class="top_menu_01">
            <li class="top_menu_01_01"><a href="#">홍길동님</a></li>
            <li class="top_menu_01_02"><a href="#">내정보변경</a></li>
            <li class="top_menu_01_03"><a href="#"><s:message code="LOG.OUT" /></a></li> <!-- 로그아웃 -->
            <li class="top_menu_01_04"><a href="#"><s:message code="SITE.MAP" /></a></li> <!-- 사이트맵 -->
            <li class="top_menu_01_05"><a href="#">시스템관리자</a></li>
        </ul>
        </td>
      </tr>
      <tr>
        <td valign="top" height="32">
            <!-- 메인 메뉴 시작 -->
            <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="레이아웃을 위한 테이블입니다." style="display: none;">
              <tr>
                <td width="7" class="top_bg_01"></td>
                <td class="top_bg_02">
                <ul class="top_menu_02">
                    <li class="top_menu_02_01"><a href="#">표준데이터</a></li>
                    <li class="top_menu_02_02"><a href="#">데이터모델</a></li>
                    <li class="top_menu_02_03"><a href="#">데이터베이스</a></li>
                    <li class="top_menu_02_04"><a href="#">영향도</a></li>
                    <li class="top_menu_02_05"><a href="#">데이터품질</a></li>
                    <li class="top_menu_02_06"><a href="#">업무관리</a></li>
                </ul>
                </td>
                <td width="7" class="top_bg_03"></td>
                <td width="5"></td>
              </tr>
            </table>
            <!-- 메인 메뉴 끝 -->
        </td>
      </tr>
    </table>
    </div>
    <!-- 상단메뉴 끝 -->
