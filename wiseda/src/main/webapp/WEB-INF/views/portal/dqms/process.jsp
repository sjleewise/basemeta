<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
<title>업무프로세스</title>

<script type="text/javascript">

$(document).ready(function(){
	
	$(".stitSub map area").click(function(){
		var idx = $(".stitSub map area").index(this);
		if(idx == 0){
			//표준등록
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 1){
			//모델/DDL 등록
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 2){
			//코드 등록
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 3){
			//지표관리
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 4){
			//핵심정보관리
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 5){
			//업무규칙관리
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 6){
			//품질측정
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 7){
			//원인분석
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 8){
			//품질개선
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 9){
			//파일검증  아직없음
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 10){
			//CRUD 메트릭스(타시스템)
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 11){
			//소스사용SQL검색(타시스템)
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 12){
			//연관검색(타시스템)
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 13){
			//문자열검색(타시스템)
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 14){
			//윈도우 권한(타시스템)
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 15){
			//온라인 서비스(타시스템)
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 16){
			///FML 추가(타시스템)
			//OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 17){
			//매핑 정의서
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 18){
			///I/F 정의서
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else if(idx == 19){
			//CDC 관리
			OpenWindow(getUrl(idx), idx, '1024px', '768px', 'yes');
		}else{
			return false;
		}
	});		
	
});

//URL 연결
function getUrl(idx){
	var url;    
// 	alert(idx);
	if (idx == "0"){
		//표준등록
		url 		= "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=biz/stnd/stndrqst_ins.jsp";
	}else if (idx == "1"){
		//모델/DDL 등록
   		url 		= "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=biz/da/model/rqst/pdmmodelrqst_ins.jsp";
	}else if (idx == "2"){
		//코드 등록
		url 		= "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=biz/stnd/codebook/rqst/codebookrqst_ins.jsp";
	}else if (idx == "3"){
		//지표관리
		url 		= "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/baseInfo/dqi.jsp&MenuId=M000004775&page_navi=기준정보&gt;품질기준&gt;<font class=history_tit>데이터품질지표 관리</font>";             
	}else if (idx == "4"){
		//핵심정보관리                             
		url 		= "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/baseInfo/ctq.jsp&MenuId=M000004776&page_navi=기준정보&gt;품질기준&gt;<font class=history_tit>중요정보항목 관리</font>";           
	}else if (idx == "5"){
		//업무규칙관리                                                                 
		url 		= "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/bizrule/bizrule.jsp&MenuId=M1294130127156&page_navi=측정&gt;업무규칙&gt;<font class=history_tit>업무규칙 관리</font>";             
	}else if (idx == "6"){
		//품질측정                            
		url		    = "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/bizrule/bizrule_list.jsp&MenuId=M000004742&page_navi=측정&gt;프로파일&gt;<font class=history_tit>프로파일 조회</font>";                   
	}else if (idx == "7"){
		//원인분석                              
		url		    = "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/impv/impv_cause.jsp&MenuId=M1319681959562&page_navi=개선&gt;개선활동&gt;<font class=history_tit>원인분석 관리</font>";                   
	}else if (idx == "8"){
		//품질개선                            
		url		    = "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/impv/impv_act.jsp&MenuId=M1320198324726&page_navi=개선&gt;개선활동&gt;<font class=history_tit>개선활동 관리</font>";                   
	}else if (idx == "9"){
		//파일검증  아직없음                  
		url		    = "<%=WiseConfig.URL_DQ%>frameSet.jsp?main_src=dqviews/impv/impv_act.jsp&MenuId=M1384329699041";                   
	}else if (idx == "10"){
		//CRUD 메트릭스(타시스템)
		url		    = "";                   
	}else if (idx == "11"){
		//소스사용SQL검색(타시스템)
		url		    = "";                   
	}else if (idx == "12"){
		//연관검색(타시스템)
		url		    = "";                   
	}else if (idx == "13"){
		//문자열검색(타시스템)
		url		    = "";                   
	}else if (idx == "14"){
		//윈도우 권한(타시스템)
		url		    = "";                   
	}else if (idx == "15"){
		//온라인 서비스(타시스템)
		url		    = "";                   
	}else if (idx == "16"){
		//FML 추가(타시스템)
		url		    = "";                   
	}else if (idx == "17"){
		//매핑 정의서
		url		    = "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=biz/da/map_nhic/lst/mapetlmain_lst.jsp";                   
	}else if (idx == "18"){
		//I/F 정의서
		url		    = "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=biz/da/map_nhic/lst/mapeaimain_lst.jsp";                   
	}else if (idx == "19"){
		//CDC 관리
		url		    = "<%=WiseConfig.URL_META %>frameSet.jsp?main_src=biz/da/cdc/rqst/pdmcdcrqst_ins.jsp";                   
	}else{	
		return false;
	}
	return url;
}

</script>

</head>
<body>
   <!-- 오른쪽 내용 시작 -->
   <div class="right">
   	<div class="location"><img src='<c:url value="/img/location_home.gif"/>' alt="home"> &gt; 품질관리 &gt; 업무 프로세스</div>
       <div class="stit">업무 프로세스</div>
       <div class="stitSub">
<%--        	<img src= '<c:url value="/img/01_03_img.gif"/>' alt="" usemap="#Map" border="0"><map name="Map"><area shape="rect" coords="46,159,192,190" href="#" alt="표준등록"><area shape="rect" coords="46,238,190,269" href="#" alt="모델/DDL 등록"><area shape="rect" coords="46,316,192,346" href="#" alt="코드 등록"><area shape="rect" coords="243,161,389,190" href="#" alt="지표관리"><area shape="rect" coords="245,192,388,220" href="#" alt="핵심정보관리"><area shape="rect" coords="243,222,390,253" href="#" alt="업무규칙관리"><area shape="rect" coords="243,300,391,330" href="#" alt="품질측정"><area shape="rect" coords="243,332,391,361" href="#" alt="원인분석"><area shape="rect" coords="244,363,390,393" href="#" alt="품질개선"><area shape="rect" coords="243,439,389,470" href="#" alt="파일검증"><area shape="rect" coords="203,546,347,577" href="#" alt="매핑 정의서"><area shape="rect" coords="416,546,558,576" href="#" alt="I/F 정의서"><area shape="rect" coords="627,546,768,575" href="#" alt="CDC 관리"></map> --%>
       	<img src= '<c:url value="/img/01_03_img.gif"/>' alt="" usemap="#Map" border="0"><map name="Map"><area shape="rect" coords="46,159,192,190" href="#" alt="표준등록"><area shape="rect" coords="46,238,190,269" href="#" alt="모델/DDL 등록"><area shape="rect" coords="46,316,192,346" href="#" alt="코드 등록"><area shape="rect" coords="243,161,389,190" href="#" alt="지표관리"><area shape="rect" coords="245,192,388,220" href="#" alt="핵심정보관리"><area shape="rect" coords="243,222,390,253" href="#" alt="업무규칙관리"><area shape="rect" coords="243,300,391,330" href="#" alt="품질측정"><area shape="rect" coords="243,332,391,361" href="#" alt="원인분석"><area shape="rect" coords="244,363,390,393" href="#" alt="품질개선"><area shape="rect" coords="243,439,389,470" href="#" alt="파일검증"><area shape="rect" coords="442,125,586,155"  alt="CRUD 메트릭스"><area shape="rect" coords="443,200,587,230"  alt="소스사용SQL검색"><area shape="rect" coords="443,232,588,261"  alt="연관검색"><area shape="rect" coords="444,263,587,292"  alt="문자열검색"><area shape="rect" coords="640,124,785,154"  alt="윈도우 권한"><area shape="rect" coords="640,156,787,184"  alt="온라인 서비스"><area shape="rect" coords="640,187,785,215"  alt="FML 추가"><area shape="rect" coords="203,546,347,577" href="#" alt="매핑 정의서"><area shape="rect" coords="416,546,558,576" href="#" alt="I/F 정의서"><area shape="rect" coords="627,546,768,575" href="#" alt="CDC 관리"></map>
       </div>
   </div>
   <!-- 오른쪽 내용 끝 -->

</body>
</html>