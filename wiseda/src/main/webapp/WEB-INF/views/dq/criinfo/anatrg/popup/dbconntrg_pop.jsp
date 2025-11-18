<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>

<html>
<head>
<title><s:message code="DIAG.TRGT.DBMS.INQ.1"/></title><!--진단대상 DBMS 조회-->



<script type="text/javascript">

var interval = "";

//page 구분 
var pageFlag = "";

$(document).ready(function() {
   //page 구분
   pageFlag = "${sflag}";
   
   //그리드 초기화 
   initGrid();
   
   //마우스 오버 이미지 초기화
   //imgConvert($('div.tab_navi a img'));
   //그리드 초기화 
   // 조회 Event Bind
   $("#btnSearch").click(function(){ doAction("Search");  });
   // 엑셀내리기 Event Bind
   $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
   //조회버튼 hidden
   $("#btnSave").hide();
   //삭제버튼 hidden
   $("#btnDelete").hide();
   //tree 추가 버튼 hidden
   $("#btnTreeNew").hide();
});

$(window).load(function() {
   $(window).resize();
});


$(window).resize(function(){
    //그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
   setibsheight($("#grid_01"));
   // grid_sheet.SetExtendLastCol(1);    
});


function initGrid()
{
    
    with(grid_sheet){
       
       var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='DQ.HEADER.DBCONNTRG.POP'/>", Align:"Center"}
                ];
        //No.|상태|선택|DB접속대상ID|만료일시|시작일시|DB접속대상물리명|DB접속대상논리명|DBMS종류|
        //DBMS버전|접속대상DB연결문자열|접속대상연결URL|접속대상드라이버명|DB접속계정ID|DB접속계정비밀번호|
        //DB접속상태|담당자명|담당자연락처|객체설명|객체버전|등록유형코드|작성일시|작성자명


        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
            {Type:"Seq",    Width:40,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
            {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
            {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",  Align:"Center", Edit:0, Hidden:1, Sort:0},
            {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",       Align:"Center", Edit:0, Hidden:1}, 
            {Type:"Date",   Width:100,  SaveName:"expDtm",       Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
            {Type:"Date",   Width:100,  SaveName:"strDtm",       Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
            {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"dbConnTrgLnm",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"dbSchId",       Align:"Center", Edit:0, Hidden:1},
            {Type:"Text",   Width:100,  SaveName:"dbSchPnm",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"dbSchLnm",       Align:"Center", Edit:0},
            {Type:"Combo",   Width:100,  SaveName:"dbmsTypCd",       Align:"Center", Edit:0},
            {Type:"Combo",   Width:100,  SaveName:"dbmsVersCd",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"connTrgDbLnkChrw",       Align:"Center", Edit:0, Hidden:1},
            {Type:"Text",   Width:100,  SaveName:"connTrgLnkUrl",       Align:"Center", Edit:0, Hidden:1},
            {Type:"Text",   Width:100,  SaveName:"connTrgDrvrNm",       Align:"Center", Edit:0, Hidden:1},
            {Type:"Text",   Width:100,  SaveName:"dbConnAcId",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"dbConnAcPwd",       Align:"Center", Edit:0, Hidden:1},
            {Type:"Text",   Width:100,  SaveName:"dbLnkSts",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"crgpNm",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"crgpCntel",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"objDescn",       Align:"Center", Edit:0},
            {Type:"Text",   Width:100,  SaveName:"objVers",       Align:"Center", Edit:0, Hidden:1},
            {Type:"Combo",   Width:100,  SaveName:"regTypCd",       Align:"Center", Edit:0},
            {Type:"Date",   Width:100,  SaveName:"writDtm",       Align:"Center", Edit:0, Format:"yyyy-MM-dd"},
            {Type:"Text",   Width:100,  SaveName:"writUserNm",       Align:"Center", Edit:0}
            

        ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

        //콤보 목록 설정...
       SetColProperty("regTypCd",    ${codeMap.regTypCdibs});
       SetColProperty("dbmsTypCd",    ${codeMap.dbmsTypCdibs});
       SetColProperty("dbmsVersCd",    ${codeMap.dbmsVersCdibs});
        InitComboNoMatchText(1, "");
        
//         SetColHidden("ibsStatus",1);
//         SetColHidden("ibsCheck",1);
//         SetColHidden("bizAreaId",1);
//         SetColHidden("objVers",1);
//         SetColHidden("regTypCd",1);
//         SetColHidden("rqstDtm",1);
//         SetColHidden("rqstUserId",1);
//         SetColHidden("rqstUserNm",1);
      
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_sheet);    
    //===========================
   
}


function doAction(sAction)
{
        
    switch(sAction)
    {
        case "Search":
           var param = $('#frmSearch').serialize();
           grid_sheet.DoSearch('<c:url value="/dq/criinfo/anatrg/selectAnaTrgDbms_lst.do" />', param);
           
           break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
    }       
}
 
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    //main 화면 그리드 셋팅
    if(row < 1) return;
    
   //업무영역명 검색
    if(pageFlag == "DBCTRG"){
       $("form#frmDbDetail #dbConnTrgId", opener.document).val(grid_sheet.GetCellValue(row,"dbConnTrgId"));
       $("form#frmDbDetail #dbConnTrgPnm", opener.document).val(grid_sheet.GetCellValue(row,"dbConnTrgPnm"));
       $("form#frmDbDetail #dbConnTrgLnm", opener.document).val(grid_sheet.GetCellValue(row,"dbConnTrgLnm"));
       $("form#frmDbDetail #dbSchId", opener.document).val(grid_sheet.GetCellValue(row,"dbSchId"));
       $("form#frmDbDetail #dbSchPnm", opener.document).val(grid_sheet.GetCellValue(row,"dbSchPnm"));
       $("form#frmDbDetail #dbSchLnm", opener.document).val(grid_sheet.GetCellValue(row,"dbSchLnm"));
    }
    else if(pageFlag == "VRT"){
       $("form#frmVrtDetail #tgtDbConnTrgId", opener.document).val(grid_sheet.GetCellValue(row,"dbConnTrgId"));
       $("form#frmVrtDetail #tgtDbConnTrgPnm", opener.document).val(grid_sheet.GetCellValue(row,"dbConnTrgPnm"));
       $("form#frmVrtDetail #tgtDbSchPnm", opener.document).val(grid_sheet.GetCellValue(row,"dbSchPnm"));
       $("form#frmVrtDetail #tgtDbSchId", opener.document).val(grid_sheet.GetCellValue(row,"dbSchId"));
    }
    
    
   window.close();
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
   if(row < 1) return;
   //그리드 선택 데이터 변수 setting
   var param =  grid_sheet.GetRowJson(row);
   var tmphtml = ' <s:message code="DIAG.TRGT.DBMS.NM"/>'+ ' : ' + param.dbConnTrgLnm ; //진단대상DBMS명









 + param.dbConnTrgLnm ;
   $('#anatrg_sel_title').html(tmphtml);
   
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
   if (stCode == 401) {
      showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
      return;
   }
   if(code < 0) {
      showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
      return;
   }
}


</script>
</head>

<body>
<div class="pop_tit" >
   <!-- 팝업 타이틀 시작 -->
   <div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="DIAG.TRGT.DBMS.INQ.1"/></div><!--컬럼명 조회-->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div><!--창닫기-->


</div>
    <!-- 팝업 타이틀 끝 -->
</div>

<!-- 팝업 내용 시작 -->
      <div class="pop_content">
         <!-- 검색조건 입력폼 -->
         <div id="search_div">
            <div class="stit"><s:message code="INQ.COND2" /></div><!--검색조건-->
           <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DIAG.TRGT.DBMS.INQ.2'/>"><!--진단대상DBMS조회-->


                   <caption><s:message code="DIAG.TRGT.DBMS"/></caption><!--진단대상DBMS-->

                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row"><label for="dbConnTrgId"><s:message code="DIAG.TRGT.DBMS.NM"/></label></th><!--진단대상DBMS명-->
                           <td>
                              <select id="dbConnTrgId"  name="dbConnTrgId" >
                            <option value=""><s:message code="WHL" /></option><!--전체-->

                            <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
                            <option value="${code.codeCd}">${code.codeLnm}</option>
                            </c:forEach>
                        </select>
                           </td>
                           <th scope="row"><label for="dbmsTypCd"><s:message code="DBMS.KIND"/></label></th><!--DBMS종류-->
                           <td>
                           <select id="dbmsTypCd"  name="dbmsTypCd">
                            <option value=""><s:message code="WHL" /></option><!--전체-->

                            <c:forEach var="code" items="${codeMap.dbmsTypCd}" varStatus="status">
                            <option value="${code.codeCd}">${code.codeLnm}</option>
                            </c:forEach>
                        </select>
                           </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM' /></div>
        </form>
      <div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
      <tiles:insertTemplate template="/WEB-INF/decorators/buttonTree.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
   <!-- 그리드 입력 입력 -->
   <div id="grid_01" class="grid_01">
        <script type="text/javascript">createIBSheet("grid_sheet", "100%", "300px");</script>            
   </div>
   <!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

   <!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
   <div class="selected_title_area" style="display:none ;">
          <div class="selected_title" id="anatrg_sel_title"><s:message code="DB.MS" /> : <span></span></div><!--진단대상명-->

   </div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->

<%-- <%= application.getRealPath("/") %> --%>
</body>
</html>