<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<script type="text/javascript">
//======================================================
//  web app path 초기화 : /wiseda
//======================================================
var tmppath = '<c:url value="/" />';
var containerPath = tmppath.substr(0, tmppath.lastIndexOf("/"));
// alert (containerPath);

var gLangDcd = '<s:message code="wiseda.langDcd" />';

var gMSG_PRC_WAIT              = '<s:message code="PRC.WAIT" />'; //처리중이니 잠시 기다려 주십시요.
var gMSG_LOGIN_INFO_NOT_EXISTS = '<s:message code="LOGIN.INFO.NOT.EXISTS" />'; //로그인 정보가 없습니다.<br>로그인 화면으로 이동하시겠습니까?
var gMSG_SYS_NO_USE		       = '<s:message code="SYS.NO.USE" />'; //시스템을 이용할수 없습니다.<br>관리자에게 문의하세요.

</script>

<link rel="stylesheet" type="text/css" href='<c:url value="/css/themes/metablue/jquery-ui-1.9.2.custom.min.css"/>' />
<%-- <link rel="stylesheet" type="text/css" href='<c:url value="/css/themes/metablue-1.12.1/jquery-ui-1.12.1.css"/>' /> --%>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/da-import.css"/>"  />


<script type="text/javascript" src='<c:url value="/js/jquery/jquery-1.9.1.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/jquery/jquery-3.5.1.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/js/jquery/jquery-ui-1.9.2.custom.min.js"/>'></script>
<%-- <script type="text/javascript" src='<c:url value="/js/jquery/jquery-ui-1.12.1.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/js/jquery/validate/jquery.validate.js"/>'></script>

<script type="text/javascript" src='<c:url value="/js/IBSheet/ibleaders.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBSheet/ibsheet.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBSheet/ibsheetinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBSheetConfig.js"/>'></script>

<script type="text/javascript" src='<c:url value="/js/common.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/request.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/multifileupload.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/documentready.js"/>'></script>
