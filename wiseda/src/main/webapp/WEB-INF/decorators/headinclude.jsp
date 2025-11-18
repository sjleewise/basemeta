<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
//======================================================
//  web app path 초기화 : /wiseda
//======================================================
var tmppath = '<c:url value="/" />';
var containerPath = tmppath.substr(0, tmppath.lastIndexOf("/"));
// alert (containerPath);
</script>

<%-- <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/css/themes/ui-flick/jquery-ui-1.8.11.custom.css"/>' /> --%>
<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/css/themes/metablue/jquery-ui-1.9.2.custom.min.css"/>' />
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/ui.jqgrid.css"/>"  /> --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/design.css"/>"  />


<%-- <script type="text/javascript" src='<c:url value="/js/jquery/jquery-1.6.4.min.js"/>'></script> --%>
<!-- 기존 -->
<script type="text/javascript" src='<c:url value="/js/jquery/jquery-1.9.1.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/jquery/jquery-ui-1.9.2.custom.js"/>'></script>
<!-- 기존 -->
<%-- <script type="text/javascript" src='<c:url value="/js/jquery/jquery-3.5.1.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/jquery/jquery-ui-1.12.1.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/jquery/json.min.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/jquery.wise.js"/>'></script> --%>

<%-- <script type="text/javascript" src='<c:url value="/js/jqgrid/i18n/grid.locale-kr.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/jqgrid/jquery.jqGrid.src.js"/>'></script> --%>
<script type="text/javascript" src='<c:url value="/js/jquery/validate/jquery.validate.js"/>'></script>

<script type="text/javascript" src='<c:url value="/js/IBSheet/ibsheet.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBSheet/ibsheetinfo.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBSheetConfig.js"/>'></script>

<%-- <script type="text/javascript" src='<c:url value="/js/IBTab/ibtab.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/IBTab/ibtabinfo.js"/>'></script> --%>
<%-- <script type="text/javascript" src='<c:url value="/js/IBTab/ibtabscroll.js"/>'></script> --%>

<script type="text/javascript" src='<c:url value="/js/common.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/request.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/multifileupload.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/documentready.js"/>'></script>

<%-- <script type="text/javascript" src='<c:url value="/js/jquery/plugins/jQuery.tree.js"/>'></script> --%>