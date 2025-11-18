<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title>DDL 권한 검색</title> 

<script type="text/javascript">

var popRqst = "${search.popRqst}";
var connTrgSchJson = ${codeMap.connTrgSch} ;
var connTrgRoleJson = ${codeMap.connTrgRole} ;

$(document).ready(function() {
   
   //마우스 오버 이미지 초기화
   //imgConvert($('div.tab_navi a img'));
   
   //    $("#tabs").tabs();
      
                   
      //그리드 초기화 
   //  initGrid();
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });
                  
    if (popRqst == 'Y') {
        // 변경요청 Event Bind
        $("#popApply").click(function(){ 
           if(checkDelIBS (grid_sheet, "<s:message code="ERR.APPLY" />")) {
               doAction("Apply");
             }
         }).show(); 
        
        //삭제요청
        $("#popDelete").click(function(){ 
           
           if(checkDelIBS (grid_sheet, "<s:message code="REQ.NO.DEL" />")) {
               doAction("DelRqst");
             }
         }).show();
    }
    
    
   //폼 초기화 버튼 초기화...
   $('#popReset').button({
          icons: {
             primary: "ui-icon-power"
           }
   }).click(function(event){
      event.preventDefault();

      $("form[name=frmSearch]")[0].reset();
   });
   
   double_select(connTrgSchJson, $("#frmSearch #grtorDbId"));
      $('select', $("#frmSearch #grtorDbId").parent()).change(function(){
         double_select(connTrgSchJson, $(this));
      });
      
      double_select(connTrgRoleJson, $("#frmSearch #grtedDbId"));
      $('select', $("#frmSearch #grtedDbId").parent()).change(function(){
         double_select(connTrgRoleJson, $(this));
      });      
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );
    
   //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
       //iframe 형태의 팝업일 경우
       if ("${search.popType}" == "I") {
          parent.closeLayerPop();
       } else {
          window.close();
       }
       
    });
});

$(window).on('load',function() {
   //그리드 초기화
   initGrid();
   $(window).resize();
//    doAction("Search");
});

EnterkeyProcess("Search");

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
      SetMergeSheet(5);
        
        var headtext2  = "No.|상태|선택";
          headtext2 += "|DDL권한ID|Grantor|Grantor|Grantor|Grantor";
          headtext2 += "|오브젝트ID|오브젝트명|오브젝트유형";
          headtext2 += "|Granted to|Granted to|Granted to|Granted to";
          headtext2 += "|권한|권한|권한|권한|권한|SYNONYM여부";
          headtext2 += "|설명|요청자명";
        
        
        var headtext = "No.|상태|선택";
        
           headtext += "|DDL권한ID|DB접속대상ID|DB접속대상물리명|DB사용자ID|DB사용자물리명";
          headtext += "|오브젝트ID|오브젝트명|오브젝트유형";
          headtext += "|DB접속대상ID|DB접속대상물리명|ROLE ID|ROLE명";
          headtext += "|SELECT|INSERT|UPDATE|DELETE|EXECUTE|SYNONYM여부";
       
          headtext += "|설명|요청자명";
           
        var headers = [
                    {Text:headtext2, Align:"Center"}
                    ,{Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,  SaveName:"ibsSeq",        Align:"Center", Edit:0},
                    {Type:"Status", Width:40,  SaveName:"ibsStatus",     Align:"Center", Edit:0},
                    {Type:"CheckBox",Width:50, SaveName:"ibsCheck",      Align:"Center", Edit:1, Sort:0},
               
                    {Type:"Text",   Width:0,   SaveName:"ddlGrtId",    Align:"Center",   Edit:0, Hidden:1},      
               {Type:"Text",   Width:100,   SaveName:"grtorDbId",    Align:"Center",   Edit:0, KeyField:0, Hidden:1},
               {Type:"Text",   Width:100,   SaveName:"grtorDbPnm",    Align:"Center",   Edit:0, Hidden:0, KeyField:0},      
               {Type:"Text",   Width:100,   SaveName:"grtorSchId",    Align:"Center",   Edit:0, KeyField:0, Hidden:1},
               {Type:"Text",   Width:100,   SaveName:"grtorSchPnm",    Align:"Center",   Edit:0, KeyField:0},
               
               {Type:"Text",   Width:100,   SaveName:"ddlObjId",    Align:"Center",   Edit:0, KeyField:0, Hidden:1},
               {Type:"Text",   Width:100,   SaveName:"ddlObjPnm",    Align:"Center",   Edit:0, KeyField:0},
               {Type:"Combo",   Width:100,   SaveName:"ddlObjTypCd",    Align:"Center",   Edit:0, KeyField:0},
               
               {Type:"Text",   Width:100,   SaveName:"grtedDbId",    Align:"Center",   Edit:0, KeyField:0, Hidden:1},
               {Type:"Text",   Width:100,   SaveName:"grtedDbPnm",    Align:"Center",   Edit:0, Hidden:0, KeyField:0},      
               {Type:"Text",   Width:100,   SaveName:"grtedSchId",    Align:"Center",   Edit:0, KeyField:0, Hidden:1},
               {Type:"Text",   Width:100,   SaveName:"grtedSchPnm",    Align:"Center",   Edit:0, KeyField:0},
               
               {Type:"CheckBox",   Width:50,   SaveName:"selectYn",    Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
               {Type:"CheckBox",   Width:50,   SaveName:"insertYn",    Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
               {Type:"CheckBox",   Width:50,   SaveName:"updateYn",    Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
                    {Type:"CheckBox",   Width:50,   SaveName:"deleteYn",     Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
                    {Type:"CheckBox",   Width:50,   SaveName:"executeYn",    Align:"Center",   Edit:0, Sort:0, HeaderCheck:0, TrueValue:"Y", FalseValue:"N"},
                    {Type:"Text",   Width:50,   SaveName:"synonymYn",     Align:"Center",   Edit:0, Hidden:1},
               
               {Type:"Text",   Width:150,  SaveName:"objDescn"  ,    Align:"Left",   Edit:0},
               {Type:"Text",   Width:40,  SaveName:"rqstUserNm"   ,    Align:"Left",   Edit:0},
               
               
                ];
                    
        InitColumns(cols);

        //콤보 목록 설정...        
      SetColProperty("ddlObjTypCd",   ${codeMap.objDcdibs});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
       
//         SetColHidden("objDescn",1);
         if(popRqst == 'N') {
           SetColHidden("ibsCheck"   ,1);      
         }
        SetColHidden("rqstUserNm",1);
        
        
        FitColWidth();
        
//         SetExtendLastCol(1);    
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
           
//            if ( isBlankStr($("#frmSearch #ddlTblPnm").val(), 'O') 
//                 && isBlankStr($("#frmSearch #ddlIdxPnm").val(), 'O')) {
//              showMsgBox("INF", "검색조건이 없습니다.<br>DDL테이블명,DDL인덱스명 중<br>최소 1개이상 검색조건을 입력후 조회하십시요.");
//              return;
//           }
           
           
           var param = $('#frmSearch').serialize();
//            param += "&pkIdxYn=N";
           grid_sheet.DoSearch("<c:url value="/meta/ddl/getDdlGrtlist.do" />", param);
//            grid_sheet.DoSearchScript("testJsonlist");
           break;
       
            
        case "Apply":
           //요청서에서 팝업 호출했을 경우....
           //TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.         
//            var retval = getibscheckjoin(grid_pop, "stwdId");
//            alert(retval);

           var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
         
         //2. 데이
         if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
                                       // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
                                       // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
         //if(saveJson.data.length == 0) return;
           
           //wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
           if ("${search.popType}" == "I") {
              param = $("#mstFrm", parent.document).serialize();
           } else {
              param = $("#mstFrm", opener.document).serialize();
           }
           param += "&rqstDcd=CU";
           param += "&ddlTrgDcd=D"; //개발단계
           var url = "<c:url value="/meta/ddl/regWam2WaqDdlGrt.do" />";
         
         IBSpostJson2(url, saveJson, param, ibscallback);
           
//            parent.setStndWordPop(retval);
           
           //조회화면에서 팝업 호출했을 경우....
           break;
           
        case "DelRqst":
               //요청서에서 팝업 호출했을 경우....
               //TODO 임시코드확인 (ibsheet에서 체크된 row의 특정 컬럼내용을 "|" 조인으로 조합하여 제공한다.         
//                var retval = getibscheckjoin(grid_pop, "stwdId");
//                alert(retval);

               var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
             
             //2. 데이
             if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
                                           // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
                                           // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
             //if(saveJson.data.length == 0) return;
               
               //wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
               if ("${search.popType}" == "I") {
                  param = $("#mstFrm", parent.document).serialize();
               } else {
                  param = $("#mstFrm", opener.document).serialize();
               }
               param += "&rqstDcd=DD";
               var url = "<c:url value="/meta/ddl/regWam2WaqDdlGrt.do" />";
             
             IBSpostJson2(url, saveJson, param, ibscallback);
               
//                parent.setStndWordPop(retval);
               
               //조회화면에서 팝업 호출했을 경우....
               break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }   
}
 




/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
   if(row < 1) return;
   
   var retjson = grid_sheet.GetRowJson(row);   
   
   //요청서용 팝업일 경우.....
   if (popRqst == 'Y') {
      //체크박스 선택/해제 토글 기능.....
      var cellchk = grid_sheet.GetCellValue(row, "ibsCheck");
      if(cellchk == '0') {
         grid_sheet.SetCellValue(row, "ibsCheck", 1);
      } else {
         grid_sheet.SetCellValue(row, "ibsCheck", 0);
      }
      
      return;
   }
   
   
   //iframe 형태의 팝업일 경우
   if ("${search.popType}" == "I") {
      parent.returnDdlIdxPop(JSON.stringify(retjson));
   } else {
      opener.returnDdlIdxPop(JSON.stringify(retjson));
   }
   
   //팝업창 닫기 버튼 클릭....
   $(".pop_tit_close").click();
   
    return;
   
}

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    //$("#hdnRow").val(row);
    if(row < 1) return;
    
    
}


function grid_sheet_OnSaveEnd(code, message) {
   if (code == 0) {
      alert("저장 성공했습니다.");
   } else {
      alert("저장 실패했습니다.");
   }
}

function grid_sheet_OnSearchEnd(code, message) {
   if(code < 0) {
      showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
      return;
   } else {
      
   }
   
}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
   
   switch(res.action) {
   
      //기존 표준단어 요청서에 변경요청 추가 후처리 함수...
      case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
         if(!isBlankStr(res.resultVO.rqstNo)) {
            if ("${search.popType}" == "I") {
               parent.postProcessIBS(res);
            }else{
               opener.postProcessIBS(res);
            }
            //팝업닫기
            $("div.pop_tit_close").click();
//              json2formmapping ($("#mstFrm", opener.document), res.resultVO);
             
             //업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
//              $("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
//              $("#mstFrm #bizDtlCd", opener.document).val("STWD");
             
//              $("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
//              if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
//                 $("#btnRegRqst", opener.document).show();
//              }
//              $("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
//             opener.doAction("Search");          
          } 
         
         break;
   
      //요청서 삭제 후처리...
      case "<%=WiseMetaConfig.IBSAction.DEL%>" :
            
            //doActionCol("Search");
      
         break;
      //요청서 단건 등록 후처리...
      case "<%=WiseMetaConfig.IBSAction.REG%>" :

         
         break;
      //요청서 여러건 등록 후처리...
      case "<%=WiseMetaConfig.IBSAction.REG_LIST%>" : 
         //저장완료시 요청서 번호 셋팅...
          /* if(!isBlankStr(res.ETC.rqstNo)) {
             //alert(res.ETC.rqstNo);
             $("form#frmSearch input[name=rqstNo]").val(res.ETC.rqstNo);
//              $("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
            doAction("Search");          
          } */
         
         break;
      
      default : 
         // 아무 작업도 하지 않는다...
         break;
         
   }
   
}

</script>

</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
   <!-- 팝업 타이틀 시작 -->
   <div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">권한 검색</div><!-- 권한 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
   <div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="주제영역조회">
                   <caption>테이블 이름</caption>
                   <colgroup>
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   <col style="width:20%;" />
                   <col style="width:30%;" />
                   </colgroup>
                   
                   <tbody>   
                         <tr>                               
                     <th scope="row"><label for="grtorSchPnm">Grantor to<br>DBMS/스키마</label></th>
                            <td>
                                 <select id="grtorDbId" class="" name="grtorDbId">
                               <option value="">선택</option>
                            </select>
                            <select id="grtorSchId" class="" name="grtorSchId">
                               <option value="">선택</option>
                            </select>
                            </td>
                            
                            <th scope="row"><label for="grtedSchPnm">Granted to<br>DBMS/ROLE</label></th>
                            <td>
                                 <select id="grtedDbId" class="" name="grtedDbId">
                               <option value="">선택</option>
                            </select>
                            <select id="grtedSchId" class="" name="grtedSchId">
                               <option value="">선택</option>
                            </select>
                            </td>
                                              
                       </tr>
                                                
                       <tr>                               
                           <th scope="row"><label for="ddlObjPnm">오브젝트명</label></th> <!-- 테이블명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="ddlObjPnm" id="ddlObjPnm" value="${search.ddlObjPnm}"/>
                                </span>
                            </td>
                              <th scope="row"><label for="ddlObjTypCd">오브젝트구분</label></th>
                     <td colspan="3">
                     <select id="ddlObjTypCd" class="wd100" name="ddlObjTypCd">
<%--                             <option value=""><s:message code="COMBO.ALL" /></option> <!-- 전체 --> --%>
                            <c:forEach var="code" items="${codeMap.objDcd}" varStatus="status">
                            <c:if test="${code.codeCd == 'TBL'}" >
                                 <option value="${code.codeCd}">${code.codeLnm}</option>
                            </c:if>
                            </c:forEach>
                        </select>
                     </td>
                            
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<%--         <div class="tb_comment"><s:message  code='ETC.POP' /></div> --%>
        </form>
        
      <div style="clear:both; height:10px;"><span></span></div>
         <!-- 조회버튼영역  -->
        <c:choose>
            <c:when test="${search.popRqst == 'Y'}">
              <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" />
            </c:when>
            <c:otherwise>
              <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
            </c:otherwise>
         </c:choose> 
</div>
      <div style="clear:both; height:5px;"><span></span></div>
        
   <!-- 그리드 입력 입력 -->
   <div id="grid_01" class="grid_01">
        <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
<%--         <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
   </div>
   <!-- 그리드 입력 입력 -->
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>