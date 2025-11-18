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
<title>결재요청조회</title>

<script type="text/javascript">

$(document).ready(function(){
		
	// 조회 버튼 Event Bind
	$("#formBtn").click(function(){
		/* alert("성공"); */
// 		document.frm.pageIndex.value = "1";
	 	$("#T1").attr('action', '<c:url value="/portal/myjob/apprReq.do"/>').submit(); 	
	});

	// 게시글 상세조회 Event Bind
	$(".btn_subForm").click(function(){
 		var idx = $(".btn_subForm").index(this);
		/* alert(idx); */ 
		inqire_notice(idx); 
		/* var orgl_user_nm = grid_sheet.CellValue(Row,'orglUser');	 */
	});

	//달력
	$( "#ntceBgnde" ).datepicker();
	$( "#ntceEndde" ).datepicker();
	
	//기간 버튼 클릭 체크
	$("#set_date_update a").click(function(){
	  var btna = $("#set_date_update a");
	  var idx = btna.index(this);
	  btna.removeClass('tb_bt_select').addClass('tb_bt');
	  btna.eq(idx).removeClass('tb_bt').addClass('tb_bt_select');
	
	  setBetweenDtm( idx, $( "#ntceBgnde" ), $( "#ntceEndde" ));
	  
	 });
	
	
	  //셋팅 초기화-요청구분
	  <c:if test="${searchVO.ofBizType != null}">
	  	$('#ofBizType').val('${searchVO.ofBizType}');
	  </c:if>
// 	$("#set_date_update a").eq(2).click();	

});

//목록조회
function select_noticeList(pageNo) {
// 	document.frm.pageIndex.value = pageNo;
	document.frm.submit();
}


//게시물상세조회
function inqire_notice(idx) {
	url = getRequestUrl($('form[name="subForm"] input[name="requestType"]').eq(idx).val());
	OpenWindow(url+"?popGb=pop&strSearchId="+$('form[name="subForm"] input[name="requestId"]').eq(idx).val()+"&strSt="+$('form[name="subForm"] input[name="requestStatus"]').eq(idx).val()+"&termGb="+$('form[name="subForm"] input[name="requestType"]').eq(idx).val(), $('form[name="subForm"] input[name="requestType"]').eq(idx).val(), "1024px", "768px", true);
}

//결재진행보기
function getRequestUrl(termGb)
{
		
	var url     = "<%=WiseConfig.URL_META%>";
	url = url.substring(0, url.length-1 );
// 	alert(url);
	//============================================================
	// 사전관리
	//============================================================
	if (termGb == "1")		//유사어	
	{
		url 		+= "/biz/stnd/term/termsynonymrqst_ins.jsp";
	}
	else if (termGb == "2") //표준단어	
	{
   		url 		+= "/biz/stnd/stwd/stwdrqst_ins.jsp";
	}
	else if (termGb == "3") //항목
	{
		url 		+= "/biz/stnd/item/itemrqst_ins.jsp";
	}
	else if (termGb == "STND") //표준
	{
		url 		+= "/biz/stnd/stndrqst_ins.jsp";
	}
	else if (termGb == "SI") //유사항목	
	{
		url 		+= "/biz/da/stnd/rqst/synmitemrqst_ins.jsp";
	}
	else if (termGb == "4") //복합어	
	{
		url 		+= "/biz/stnd/cmwd/cmwdrqst_ins.jsp";
	}
	else if (termGb == "15") //업무용어 
	{
		url 		+= "/biz/stnd/bzwd/bzwdrqst_ins.jsp";
	}
	else if (termGb == "21") //도메인그룹		
	{
		url 		+= "/biz/stnd/dmn/rqst/domngrprqst_ins.jsp";
	}
	else if (termGb == "5") //도메인
	{
			url 		+= "/biz/stnd/dmn/rqst/domnrqst_ins.jsp";
	}
	else if (termGb == "30") //유효값		
	{
		url 		+= "/biz/stnd/dmn/rqst/instrqst_ins.jsp";
	}
	else if (termGb == "58") //ASIS도메인		
	{
		url 		+= "/biz/da/asis/rqst/asisdmnrqst_ins.jsp";
	}
	else if (termGb == "59") //ASIS유효값		
	{
		url 		+= "/biz/asis/rqst/modelasisinstrqst_ins.jsp";
	}
	else if (termGb == "PR") //변수항목
	{
		url 		+= "/biz/da/stnd/param/rqst/paramrqst_ins.jsp";
	}
	else if (termGb == "DR") //도메인부가정보
	{
		url 		+= "/biz/da/dmnref/rqst/dmnrefrqst_ins.jsp";
	}
	else if (termGb == "DMIS") //도메인유효값		
	{
		url 		+= "/biz/da/dmninst/rqst/dmninstrqst_ins.jsp";
	}
	else if (termGb == "CDTSF") //코드운영이관 처리
	{
			url 		+= "/biz/stnd/cdtsf/cdtsfrqst_ins.jsp";			
	}
	else if (termGb == "CDTSF2") //코드운영이관 처리
	{
		url 		+= "/biz/stnd/cdtsf/khgc_cdtsfrqst_ins.jsp";
	}
	else if (termGb == "CNST") //상수항목요청 처리
	{
		url 		+= "/biz/stnd/cnst/cnstrqst_ins.jsp";
	}else if (termGb == "DMCL") //도메인그룹
	{
		url 		+= "/biz/stnd/dmn/rqst/dmngrprqst_ins.jsp";
	}
	//============================================================
	// 논리모델관리
	//============================================================
	else if (termGb == "EA") //엔티티+속성
	{
		url 		+= "/biz/da/ldm/rqst/enttrqst_ins.jsp";
	}
	else if (termGb == "6") //엔티티	
	{
		url 		+= "/biz/da/ldm/rqst/enttmultirqst_ins.jsp";
	}
	else if (termGb == "7") //속성
	{
		url 		+= "/biz/da/ldm/rqst/attrmultirqst_ins.jsp";
	}
	else if (termGb == "EL") //논리모델
	{
		url 		+= "/biz/da/ldm/rqst/ldmmodelrqst_ins.jsp";
	}
	else if (termGb == "ELI") //논리모델(검증)	
	{
		url 		+= "/biz/da/cldm/rqst/cldmmodelrqst_ins.jsp";
	}
	else if (termGb == "EC") //개념모델	
	{
		url 		+= "/biz/da/cdm/rqst/cdmmodelrqst_ins.jsp";
	}
	else if (termGb == "AC") //테이블+엔티티	
	{
		url 		+= "/biz/da/model/rqst/modelrqst_ins.jsp";
	}
	else if (termGb == "PAC") //물리모델 등록요청	
	{
		url 		+= "/biz/da/model/rqst/pdmmodelrqst_ins.jsp";
	}
	else if (termGb == "PACNHIC") //건보물리모델 등록요청	
	{
		url 		+= "/biz/da/model_nhic/rqst/pdmmodelrqst_ins.jsp";
	}
	else if (termGb == "CM") //개념모델 등록요청	
	{
		url 		+= "/biz/da/cdm/rqst/cdmmodelrqst_ins.jsp";
	}
	else if (termGb == "LM") //개념모델 등록요청
	{
		url 		+= "/biz/da/ldm/rqst/ldmmodelrqst_ins.jsp";
	}
	//============================================================
	// 관계정보관리
	//============================================================
	else if (termGb == "66") //엔티티
	{
		url 		+= "/biz/model/rel/relrqst_ins.jsp";
	}
	else if (termGb == "77") //속성
	{
		url 		+= "/biz/model/rel/relattrrqst_ins.jsp";
	}
	//============================================================
	// 물리모델관리
	//============================================================
	else if (termGb == "PT") //테이블+컬럼	
	{
		url 		+= "/biz/da/pdm/rqst/tblrqst_ins.jsp";
	}
	else if (termGb == "10") //테이블	
	{
		url 		+= "/biz/tble/tblerqst_ins.jsp";
	}
	else if (termGb == "TN") //테이블명	
	{
		url 		+= "/biz/da/tbl/rqst/tablenmrqst_ins.jsp";
	}
	else if (termGb == "TNS") //테이블명(표준단어)	
	{
		url 		+= "/biz/da/tbl/rqst/tblenmrqst_ins.jsp";
	}
	else if (termGb == "11") //컬럼
	{
		url 		+= "/biz/tble/columnrqst_ins.jsp";
	}
	else if (termGb == "12") //인덱스		
	{
		url 		+= "/biz/index/idxrqst_ins.jsp";
	}
	else if (termGb == "13") //인덱스컬럼
	{
		url 		+= "/biz/index/idxcolrqst_ins.jsp";
	}
	else if (termGb == "14") //인덱스/인덱스 컬럼 동시
	{
		url 		+= "/biz/index/rqst/idxrqst_ins.jsp";
	}
	else if (termGb == "EP") //물리모델	
	{
		url 		+= "/biz/da/pdm/rqst/pdmmodelrqst_ins.jsp";
	}
	else if (termGb == "LP") //논리+물리모델	
	{
		url 		+= "/biz/da/mart/rqst/modelrqst_ins.jsp";
	}
	else if (termGb == "R7LP") //R7논리+물리모델	
	{
		url 		+= "/biz/da/mart/rqst/modelrqst_r7_ins.jsp";
	}
	else if (termGb == "ACX") //속성+컬럼
	{
		url 		+= "/biz/da/model/rqst/attrcolrqst_ins.jsp";
	}
	else if (termGb == "PDMREL") //관계정보(물리모델)
	{
		url         += "/biz/da/pdm/rqst/relrqst_ins.jsp";
	}
    //============================================================
	// 매핑관리
	//============================================================
	else if (termGb == "81") //매핑정의-테이블매핑	
	{
		url 		+= "/biz/da/map/rqst/tblmaprqst_ins.jsp";
	}
    else if (termGb == "88") //테이블매핑
	{
		url 		+= "/biz/mapping/tblmaprqst_ins.jsp";
	}
	else if (termGb == "99") //컬럼매핑	
	{
		url 		+= "/biz/mapping/colmaprqst_ins2.jsp";
	}
	else if (termGb == "55") //유효값매핑	
	{
		url 		+= "/biz/mapping/codemaprqst_ins.jsp";
	}
	else if (termGb == "89") //WIS테이블매핑정의서		
	{
		url 		+= "/biz/da/map_wis/rqst/map_tbl_ins.jsp";
	}
	else if (termGb == "102") //WIS테이블매핑정의서삭제	
	{
		url 		+= "/biz/da/map_wis/rqst/map_del_ins.jsp";
	}
	else if (termGb == "90") //WIS컬럼매핑정의서	
	{
		url 		+= "/biz/da/map_wis/rqst/map_col_ins.jsp";
	}
	else if (termGb == "103") //WIS컬럼매핑정의서	삭제
	{
		url 		+= "/biz/da/map_wis/rqst/map_del_ins.jsp";
	}
	else if (termGb == "91") //WIS코드매핑정의서	
	{
		url 		+= "/biz/da/map_wis/rqst/map_cd_ins.jsp";
	}else if (termGb == "MTC") //DS매핑정의서		
	{
		url 		+= "/biz/da/map_ds/rqst/map_ins.jsp";
	}
	else if (termGb == "87") //DS매핑정의서		
	{
		url 		+= "/biz/da/map_ds/rqst/map_tbl_ins.jsp";
	}
	else if (termGb == "104") //WIS코드매핑정의서	삭제	
	{
		url 		+= "/biz/da/map_wis/rqst/map_del_ins.jsp";
	}
	else if (termGb == "105") //WIS매핑정의서파일	삭제	
	{
		url 		+= "/biz/da/map_wis/rqst/map_file_lst.jsp";
	}
	else if (termGb == "82") //NPS코드매핑정의서		
	{
		url 		+= "/biz/da/map_nps/rqst/map_cd_ins.jsp";
	}
	else if (termGb == "84") //NPS코드매핑정의서		
	{
		url 		+= "/biz/da/map_nps/rqst/map_cd_del_ins.jsp";
	}
	else if (termGb == "TMP") //테이블 매핑 3.5버전
	{
		url 		+= "/biz/da/mapping/rqst/map_tbl_ins.jsp";
	}
	else if (termGb == "CMP") //컬럼 매핑 3.5버전
	{
		url 		+= "/biz/da/mapping/rqst/map_col_ins.jsp";
	}else if (termGb == "KCMP") //컬럼 매핑 3.5버전
	{
		url 		+= "/biz/da/map_kotsa/rqst/map_col_ins.jsp";
	}
	else if (termGb == "CDMP") //코드 매핑 3.5버전
	{
		url 		+= "/biz/da/mapping/rqst/map_cd_ins.jsp";
	}
	else if (termGb == "TMF") //코드 매핑 3.5버전
	{
		url 		+= "/biz/da/map/rqst/map_col_funcrqst_ins.jsp";
	}
	else if (termGb == "PB" )
	{
		url 		+= "/biz/da/mart/rqst/modelrqst_da_ins.jsp"
	}
	//============================================================
	// ASIS모델관리
	//============================================================
	else if (termGb == "8") //테이블(AS-IS)	
	{
		url 		+= "/biz/da/asis/rqst/asistblrqst_ins.jsp";
	}
	else if (termGb == "9") //컬럼(AS-IS)	
	{
		url 		+= "/biz/da/asis/rqst/asiscolrqst_ins.jsp";
	}
	else if (termGb == "101") //ASIS모델	
	{
		url 		+= "/biz/asis/rqst/modelrqst_ins.jsp";
	}
	//============================================================
	// DDL관리
	//============================================================
	else if (termGb == "22") //DDL 테이블		
	{
		url 		+= "/biz/ddl/tblerqst_ins.jsp";

	}
	else if (termGb == "DT") //DDL 멀티테이블		
	{
		url 		+= "/biz/da/ddl/rqst/tblmultirqst_ins.jsp";
	}
	else if (termGb == "DI") //DDL 멀티인덱스		
	{
		url 		+= "/biz/da/ddl/rqst/idxmultirqst_ins.jsp";
	}
	else if (termGb == "23") //DDL 인덱스		
	{
		url 		+= "/biz/ddl/idxrqst_ins_n.jsp";
	}
	else if (termGb == "BT") //DDL 테이블	신규	
	{
		url 		+= "/biz/da/dbddl/rqst/ddltblrqst_ins.jsp";
	}
	else if (termGb == "BI") //DDL 인덱스	신규
	{
		url 		+= "/biz/da/dbddl/rqst/ddlidxrqst_ins.jsp";
	}
	else if (termGb == "FTI") //DDL 이관요청 	신규
	{
		url 		+= "/biz/da/dbddltsf/rqst/ddltsfrqst_ins.jsp";
	}
	else if (termGb == "DDVW") //DDL VIEW 	신규	
	{
		url 		+= "/biz/da/dbobj/rqst/ddlviewrqst_ins.jsp";
	}
	else if (termGb == "DDFC") //DDL Function 	신규	
	{
		url 		+= "/biz/da/dbobj/rqst/ddletcrqst_ins.jsp";
	}
	else if (termGb == "DDPC") //DDL Procedure 	신규	
	{
		url 		+= "/biz/da/dbobj/rqst/ddlprocrqst_ins.jsp";
	}
	else if (termGb == "DDUS") //계정 권한 	신규
	{
		url 		+= "/biz/da/dbobj/rqst/ddluserrqst_ins.jsp";
	}
	else if (termGb == "DCM") //DDL복사테이블 관리
	{
		url 		+= "/biz/da/dbddl/rqst/ddlcopytblrqst_ins.jsp";
	}
	else if (termGb == "SEQ") //SEQUENCT 관리 
	{
		url 		+= "/biz/da/dbobj/rqst/ddlseqrqst_ins.jsp";
	}
	else if (termGb == "DPT")	//파티션테이블
	{
		url 		+= "/biz/da/dbddl/rqst/ddlpartrqst_ins.jsp";
	}
	else if (termGb == "DPI")	//파티션인덱스
	{
		url 		+= "/biz/da/dbddl/rqst/ddlpartidxrqst_ins.jsp";
	}
	else if (termGb == "PDM")	//PDM-DDL매핑
	{
		url 		+= "/biz/da/pdmddlmap/pdmddlmap_ins.jsp";
	}else if (termGb == "FOB") //DDL 이관요청 	
	{
		url 		+= "/biz/da/dbddltsf/rqst/ddltsfrqst_wlc_ins.jsp";
	}

    //============================================================
	// 프로그램관리
	//============================================================
	else if (termGb == "71") //프로그램 등록요청
	{
		url 		+= "/biz/apparch/pgminfo/pgmrqst_ins.jsp";
	}
	else if (termGb == "72") //프로그램 기능 등록요청
	{
		url 		+= "/biz/apparch/pgminfo/funcrqst_ins.jsp";
	}
	else if (termGb == "73") //프로그램-기능 매핑 등록요청
	{
		url 		+= "/biz/apparch/pgminfo/funcrelrqst_ins.jsp";
	}
	else if (termGb == "74") //프로그램기능-테이블 매핑 등록요청
	{
		url 		+= "/biz/apparch/pgminfo/functblrelrqst_ins.jsp";
	}
	//============================================================
	// 시스템 관리 20070507 배준곤 추가
	//============================================================
	else if (termGb == "92") //하드웨어등록
	{
		url 		+= "/admin/system/rqst/hwrqst_ins.jsp";
	}
	else if (termGb == "93") //소프트웨어등록
	{
		url 		+= "/admin/system/rqst/swrqst_ins.jsp";
	}
	else if (termGb == "94") //서버구성 등록
	{
		url 		+= "/admin/system/rqst/servercomprqst_ins.jsp";
	}
	else if (termGb == "95") //시스템구성 등록
	{
		url 		+= "/admin/system/rqst/systemcomprqst_ins.jsp";
	}

	//============================================================
	// 메시지 관리 20080115 배준곤 추가
	//============================================================
	else if (termGb == "MG") // 메시지 등록
	{
		url 		+= "/biz/msg/rqst/msgrqst_ins.jsp";
	}

    //============================================================
	// DQ(데이터품질) 2007.04.18 최용준 추가.
	// termGb 상수는
	// wiseitech\wisedq\common\dqconst\CObjectConst.java
	// 에 따로 정의
	// 300번대로 시작함
	//============================================================
	else if (termGb == "301") //지표등록
	{
		url 		+= "/biz/wisedq/prepst/indicator/idcrqst_ins.jsp";
	}
	else if (termGb == "302") //상세지표
	{
		url 		+= "/biz/wisedq/prepst/indicator/idcdtlrqst_ins.jsp";
	}
	else if (termGb == "303") //측정대상
	{
		url 		+= "/biz/wisedq/prepst/ctqobject/ctqobjrqst_ins.jsp";
	}
	else if (termGb == "304") //지표별측정대상등록
	{
		url 		+= "/biz/wisedq/prepst/msrrule/msrrulerqst_ins.jsp";
	}
	else if (termGb == "CDTP")	//코드유형
	{
		url			+= "/biz/da/dmnclsntype/rqst/clsn_type_vv_ins.jsp";
	}
	else if (termGb == "IFT")	//테이블인터페이스
	{
		url			+= "/biz/da/itfc/rqst/tblitfcrqst_ins.jsp";
	}
	else if (termGb == "IFC")	//컬럼인터페이스
	{
		url			+= "/biz/da/itfc/rqst/colitfcrqst_ins.jsp";
	}
	else if (termGb == "MMI")	//기준항목
	{
		url			+= "/biz/da/mmi/rqst/dbmmirqst_ins.jsp";
	}
	else if (termGb == "ADN")	//의뢰번호
	{
		url			+= "/biz/stnd/rqst/apvdocnolst_lst.jsp";
	}
	//============================================================
	// mainframe
	//============================================================
	else if (termGb == "MFAT")	//테이블
	{
		url			+= "/biz/da/mainframe/rqst/asistblrqst_ins.jsp";
	}
	else if (termGb == "MFAC")	//컬럼
	{
		url			+= "/biz/da/mainframe/rqst/asiscolrqst_ins.jsp";
	}
	else if (termGb == "MFACD")	//코드
	{
		url			+= "/biz/da/mainframe/rqst/asiscoderqst_ins.jsp";
	}
	else if (termGb == "NHMP")	//매핑정의서
	{
		url			+= "/biz/da/map_nhic/rqst/mapspecrqst_ins.jsp";
	}
	else if (termGb == "NHMD")	//매핑정의서 삭제
	{
		url			+= "/biz/da/map_nhic/rqst/mapfiledel_ins.jsp";
	}
	else if (termGb == "GRT")	//권한신청
	{
		url			+= "/biz/da/dbddl/rqst/ddlgrtrqst_ins.jsp";
	}
	else if (termGb == "TUNE")	//튜닝신청
	{
		url			+= "/biz/tune/rqst/dbtunerqst_ins.jsp";
	}else if (termGb == "PDMCDC")	//CDC신청
	{
		url			+= "/biz/da/cdc/rqst/pdmcdcrqst_ins.jsp";
	}
	else if (termGb == "IFMODEL")	//인터페이스모델일경우
	{
		url			+= "/biz/tble/tblhist_lst.jsp";
	}
	else if (termGb == "IFMODEL")	//인터페이스모델일경우
	{
		url			+= "/biz/tble/tblhist_lst.jsp";
	}
	//건강보험공단 코드북관리
	else if (termGb == "CDBCMN")	//코드/변경 공통
	{
		url			+= "/biz/stnd/codebook/rqst/codebookrqst_ins.jsp";
	}
	else if (termGb == "CDBBIZ")	//코드/변경 업무
	{
		url			+= "/biz/stnd/codebook/rqst/codebookrqst_ins.jsp";
	}
	else if (termGb == "DDI")	//코드/변경 업무
	{
		url			+= "/biz/da/model/rqst/dupdatarqst_ins.jsp";
	}
	return url;
}

</script>
</head>
<body>
        <!-- 오른쪽 내용 시작 -->
        <div class="right">
            <div class="stit">결재대상 조회</div>
			<form id ="T1"  name="frm" action ="<c:url value='/portal/myjob/apprReq.do'/>" method="post">
				<input type="hidden" name="requestId"  value="<c:out value='${result.requestId}'/>" />
				<input type="hidden" name="requestType" value="<c:out value='${result.requestType}'/>" />
				<input type="hidden" name="requestTypeNm" value="<c:out value='${result.requestTypeNm}'/>" />
				<input type="hidden" name="bizNm" value="<c:out value='${result.bizNm}'/>" />
				<input type="hidden" name="rqstUser" value="<c:out value='${result.rqstUser}'/>" />
				<input type="hidden" name="requestStatus" value="<c:out value='${result.requestStatus}'/>" />
				<input type="hidden" name="requestStatusNm" value="<c:out value='${result.requestStatusNm}'/>" />
				<input type="hidden" name="orglDttm" value="<c:out value='${result.orglDttm}'/>" />
				<input type="hidden" name="reqXpnDdlDate" value="<c:out value='${result.reqXpnDdlDate}'/>" />	
<%-- 				<input name="pageIndex" type="hidden" value="<c:out value='${searchVO.pageIndex}'/>"/>					 --%>
				<input name="userId" type="hidden" value="<c:out value='${LoginVO.id}'/>"/>					
            <table border="0" cellspacing="0" cellpadding="0" class="tb_write" summary="">
                <caption>
                조회조건
                </caption>
                <colgroup>
                    <col style="width:15%;">
                    <col style="width:35%;">
                    <col style="width:15%;">
                    <col style="width:35%;">
                </colgroup>
              <tr>
                <th>요청구분</th>
                <td>
                <select id="ofBizType" name="ofBizType">
                	<option  value="">전체</option>
                	<c:forEach var="code" items="${codeMap.ofBizType}" varStatus="status">
                    <option value="${code.codeCd}">${code.codeLnm}</option>
                    </c:forEach>
                </select>
                </td>
     			<th></th>
                <td></td>
              </tr>
              <tr>
                   <tr>
                 <th class="bd_none">기간</th>
                 <td class="bd_none" id="set_date_update">
                  <a href="#" class="tb_bt">1일</a>
                     <a href="#" class="tb_bt">3일</a>
                     <a href="#" class="tb_bt">7일</a>
                     <a href="#" class="tb_bt">1개월</a>
                     <a href="#" class="tb_bt">3개월</a>
                     <a href="#" class="tb_bt">6개월</a>
                 </td>
                 <th>요청일자</th>
                    <td><input id="ntceBgnde" name="ntceBgnde" type="text" class="wd80" value="${searchVO.ntceBgnde}">  
                    	- <input id="ntceEndde" name="ntceEndde" type="text" class="wd80" value="${searchVO.ntceEndde}">
               </tr> 
            </table>
            </form>    
            <ul class="bt">
            	<li id="btn_read"><a id ="formBtn" class="bt_gray"><s:message code="BTN.READ"/></a></li>
            </ul>
       	    <!--  전체건수 출력 -->
            <div class="bbs_num">[전체건수 : ${resultCnt}]</div>
            <div style="overflow-y: hidden; overflow-x:hidden;" >
            <table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="" style="table-layout: fixed;">
                <caption>
                게시판리스트
                </caption>
                <colgroup>
                    <col style="width:71px;">
                    <col style="width:87px;">
                    <col style="width:267px;">
                    <col style="width:71px;">
                    <col style="width:95px;">
                    <col style="width:71px;">
                    <col style="width:87px;"> 
                </colgroup>
              <tr class="tr_th">
                <th>요청번호</th>
                <th>요청구분</th>
                <th>요청명</th>
                <th>요청일자</th>
                <th>반영희망일자</th>
                <th>최초요청자</th>
                <th>진행상태</th>
			</tr>
			</table>
			</div>
			<!--  테이블 출력을 스크롤 처리한다. -->
            <div style="overflow-y: scroll;overflow-x:hidden;  height: 407px;">
           	<table border="0" cellspacing="0" cellpadding="0" class="tb_grid" summary="" style="table-layout: fixed;">
                <caption>
                게시판리스트
                </caption>
                <colgroup>
                    <col style="width:71px;">
                    <col style="width:87px;">
                    <col style="width:267px;">
                    <col style="width:71px;">
                    <col style="width:95px;">
                    <col style="width:71px;">
                    <col style="width:71px;"> 
               
                </colgroup>
              <c:forEach var="result" items="${resultList}" varStatus="status">
              <tr>
<%-- 		        <td><c:out value="${(result.pageIndex-1) * result.pageSize + status.count}"/></td> --%> 
              			<td><c:out value="${result.requestId}"/></td>
				    	<td><c:out value="${result.requestTypeNm}"/></td>
              	<td class="link_txt">
               		<form name="subForm" method="post" action="<c:url value='/portal/myjob/apprReq.do'/>">						
						<input type="hidden" name="requestId"  value="<c:out value='${result.requestId}'/>" />
						<input type="hidden" name="requestType" value="<c:out value='${result.requestType}'/>" />
						<input type="hidden" name="requestTypeNm" value="<c:out value='${result.requestTypeNm}'/>" />
						<input type="hidden" name="bizNm" value="<c:out value='${result.bizNm}'/>" />
						<input type="hidden" name="rqstUser" value="<c:out value='${result.rqstUser}'/>" />
						<input type="hidden" name="requestStatus" value="<c:out value='${result.requestStatus}'/>" />
						<input type="hidden" name="requestStatusNm" value="<c:out value='${result.requestStatusNm}'/>" />
						<input type="hidden" name="orglDttm" value="<c:out value='${result.orglDttm}'/>" />
						<input type="hidden" name="reqXpnDdlDate" value="<c:out value='${result.reqXpnDdlDate}'/>" />			
						
						<a class="btn_subForm" style="text-align: left;"><span class="ellipsis" style="width:250px;" ><c:out value="${result.bizNm}"/></span></a>
<%-- 				<input class="btn_subForm" type="submit" style="background-color:white; cursor:pointer; border:solid 0px black;text-align:left;" value='<c:out value="${result.nttSj}"/>'> --%>
			    	</form>
              	</td> 
						 <td><c:out value="${result.orglDttm}"/></td>
						 <td><c:out value="${result.reqXpnDdlDate}"/></td>
						 <td><c:out value="${result.rqstUser}"/></td>
						 <td><c:out value="${result.requestStatusNm}"/></td>      
              </tr>
             </c:forEach>
               <c:if test="${fn:length(resultList) == 0}">
              	<tr>
              		<td class="bd_none" colspan="7"><s:message code="MSG.NODATA" /></td>
              	</tr>
              </c:if>
            </table>
            </div>    
           <c:if test="${pageui != null and pageui != '' }">
           	 <div class="paging">
	  				${pageui}
            </div>
           </c:if>      
        </div>
        <!-- 오른쪽 내용 끝 -->
        
</body>
</html>