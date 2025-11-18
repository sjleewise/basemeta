
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
<title><s:message code="CD.MAPG.DFNT.P.SRH"/></title> <!-- 코드매핑정의서 검색 -->

<script type="text/javascript">

var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...
var popRqst = "${search.popRqst}";

$(document).ready(function() {
	
	//마우스 오버 이미지 초기화
	//imgConvert($('div.tab_navi a img'));
	
                
    //그리드 초기화 
//     initGrid();
    // 조회 Event Bin
    $("#popSearch").click(function(){ doAction("Search");  });
                  
    if (popRqst == 'Y') {
        // 적용 Event Bind
        $("#popApply").click(function(){ 
        	if(checkDelIBS (grid_sheet, "<s:message code="ERR.APPLY" />")) {
				doAction("Apply");
	    	}
		}).show();

        $("#popDelete").click(function(){ 
	         if(checkDelIBS (grid_sheet, "<s:message code="REQ.NO.DEL" />")) {
	 			doAction("DelRqst");
	     	}
		}).show();
		
    }
    
    
  //폼 초기화 버튼 초기화...
	$('#popReset').click(function(event){
		event.preventDefault();
// 		alert("초기화버튼");
		$("form[name=frmSearch]")[0].reset();
	});
                  
            
    // 엑셀내리기 Event Bind
    $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

    //======================================================
    // 셀렉트 박스 초기화
    //======================================================
    // 시스템영역
    create_selectbox(sysareaJson, $("#sysAreaId"));
    $("#sysAreaId").change(function(){
    	$("#btnSearch").click();
    });
    
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
//     	alert(1);
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
    
  //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
    setautoComplete($("#frmSearch #tgtTgtDmnLnm"), "CODMAP");
    
});
//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	//그리드 초기화
	initGrid();
	$(window).resize();
	doAction("Search");
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
        
  		var headers = [
                       {Text: "<s:message code='META.HEADER.DOLMAPSEARCH.POP1'/>"},
                       /* No.|상태|선택|코드매핑ID|매핑정의서유형|코드전환유형|코드매핑유형|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|타겟(TOBE)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|소스(ASIS)|응용담당자ID|응용담당자명|전환담당자ID|전환담당자명 */
                       {Text: "<s:message code='META.HEADER.DOLMAPSEARCH.POP2'/>", Align:"Center"}
                       /* No.|상태|선택|코드매핑ID|매핑정의서유형|코드전환유형|코드매핑유형|코드도메인ID|코드도메인명|코드|코드명|시스템명|업무명|DB명|테이블물리명|테이블논리명|컬럼물리명|컬럼논리명|코드|코드명|상위코드|상위코드명|응용담당자ID|응용담당자명|전환담당자ID|전환담당자명|요청일시 */
                   ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [ 
                    
				{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
				{Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
				{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
				//{Type:"Combo",  Width:80,  SaveName:"rvwStsCd",	Align:"Center", Edit:0, Hidden:1},						
				//{Type:"Text",   Width:80,  SaveName:"rvwConts",	Align:"Left", Edit:0, Hidden:1},						
				//{Type:"Combo",  Width:80,  SaveName:"rqstDcd",	Align:"Center", Edit:1, KeyField:1},						
				//{Type:"Combo",  Width:60,  SaveName:"regTypCd",	Align:"Center", Edit:0},						
				//{Type:"Combo",  Width:60,  SaveName:"vrfCd",	Align:"Center", Edit:0},
				
				{Type:"Text",   Width:80,  SaveName:"cdMapId",	Align:"Center", Edit:0, Hidden:1},
				{Type:"Combo",   Width:100,  SaveName:"mapDfType", 	 Align:"Center",   Edit:0},
				
				{Type:"Combo",   Width:80,  SaveName:"cdCnvsType",	Align:"Center", Edit:0, Hidden:0},
				{Type:"Combo",   Width:80,  SaveName:"cdMapType",	Align:"Center", Edit:0, Hidden:0},
				
				{Type:"Text",   Width:80,  SaveName:"tgtDmnId",	Align:"Center", Edit:0, Hidden:1},
				{Type:"Text",   Width:80,  SaveName:"tgtDmnLnm",	Align:"Center", Edit:0, Hidden:0, KeyField:1},
				{Type:"Text",   Width:80,  SaveName:"tgtCdVal",	Align:"Center", Edit:0, Hidden:0, KeyField:1},
				{Type:"Text",   Width:80,  SaveName:"tgtCdValNm",	Align:"Center", Edit:0, Hidden:0},
				
				{Type:"Text",   Width:100,  SaveName:"srcSysNm", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"srcBizNm", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"srcDbPnm", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"srcTblPnm", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"srcTblLnm", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"srcColPnm", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"srcColLnm", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:80,  SaveName:"srcCdVal",	Align:"Center", Edit:0, Hidden:0},
				{Type:"Text",   Width:80,  SaveName:"srcCdValNm",	Align:"Center", Edit:0, Hidden:0},				
				{Type:"Text",   Width:80,  SaveName:"srcUppCdVal",	Align:"Center", Edit:0, Hidden:0},
				{Type:"Text",   Width:80,  SaveName:"srcUppCdValNm",	Align:"Center", Edit:0, Hidden:0},				
			
				{Type:"Text",   Width:100,  SaveName:"appCrgpId", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"appCrgpNm", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"cnvsCrgpId", 	 Align:"Center",   Edit:0},
				{Type:"Text",   Width:100,  SaveName:"cnvsCrgpNm", 	 Align:"Center",   Edit:0},		

                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
//		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
//		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		
		SetColProperty("mapDfType", 	${codeMap.mapDfTypCdibs});
		SetColProperty("cdCnvsType", 	${codeMap.cdCnvsTypCdibs});
		SetColProperty("cdMapType", 	${codeMap.codMapTypCdibs});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("ibsStatus",1);
        //SetColHidden("pdmTblId",1);

      	if(popRqst == 'N') {
     		SetColHidden("ibsCheck"	,1);      
      	}
        
        
        //FitColWidth();
        
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
        	var param = $('#frmSearch').serialize();
        	grid_sheet.DoSearch("<c:url value="/meta/mapping/popup/getcodmaplist.do" />", param);
        	break;
       
            
        case "Apply":
        	
        	var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
			
			
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
			//if(saveJson.data.length == 0) return;
        	
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
	       	var param = "";
        	
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}

        	param += "&rqstDcd=CU";
        	
        	var url = "<c:url value='/meta/mapping/regWam2WaqCodMap.do' />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
         	//parent.setStndWordPop(retval);
        	
        	//조회화면에서 팝업 호출했을 경우....
        	break;

 		case "DelRqst":

			var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");
						
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
			//if(saveJson.data.length == 0) return;
        	
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
	       	var param = "";
        	
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}

        	param += "&rqstDcd=DD";
        	
        	var url = "<c:url value='/meta/mapping/regWam2WaqCodMap.do' />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	 		 	
        	
        	break;     
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드
            grid_sheet.LoadExcel({Mode:'HeaderMatch'});
            break;
    }       
}
 

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {
	
		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
// 	    		alert(res.resultVO.rqstNo);
				if ("${search.popType}" == "I") {
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}
				//팝업닫기
				$("div.pop_tit_close").click();
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세코드는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("DMN");
	    		
// 	    		$("form#frmSearch input[name=rqstNo]").val(res.resultVO.rqstNo);
// 	    		if ($("#mstFrm #rqstStepCd", opener.document).val() == "S")  {
// 	    			$("#btnRegRqst", opener.document).show();
// 	    		}
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
// 				opener.doAction("Search");    		
	    	} 
			
// 			opener.doAction("Search");
			
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
// 	    		$("form#frmSearch input[name=rqstSno]").val(res.ETC.rqstSno);
				doAction("Search");    		
	    	} */
			
			break;
		
		default : 
			// 아무 작업도 하지 않는다...
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
		parent.returnPdmtblPop(JSON.stringify(retjson));
	} else {
		opener.returnPdmtblPop(JSON.stringify(retjson));
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
	//alert(code);
	if (code == 0) {
		alert("<s:message code='MSG.STRG.SCS' />"); //저장 성공했습니다.
	} else {
		alert("<s:message code='MSG.STRG.FALR' />"); //저장 실패했습니다.
	}
}

function grid_sheet_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
		
	}
	
}


</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="CD.MAPG.DFNT.P.SRH"/></div> <!-- 코드매핑정의서 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!--    		<div class="stab">
           	<div class="stab_108_over">요청서목록</div>
               <div class="stab_108"><a href="#">요청서목록</a></div>
               <ul class="bt02">
                   <li class="bt02_50"><a href="#">엑셀읽기</a></li>
                   <li class="bt02_50"><a href="#">저장</a></li>
               </ul>
           </div> -->
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='SUBJ.TRRT.INQ' />"> <!-- 주제영역조회 -->
                   <caption><s:message code="TBL.NM1" /></caption> <!-- 테이블 이름 -->
                   <colgroup>
                   <col style="width:30%;" />
                   <col style="width:30%;" />
                   <col style="width:15%;" />
                   <col style="width:30%;" />

                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
<!--                            <th scope="row" class="th_require"><label for="sysAreaId">시스템영역</label></th> -->
<!--                             <td> -->
<%--                                 <select id="sysAreaId" class="" name="sysAreaId"> --%>
<!--                                         <option value="">전체</option> -->
<%--                                  </select> --%>
<!--                             </td> -->
                           <th scope="row"><label for="tgtDmnLnm"><s:message code="TARG.CD.DMN.NM.1"/></label></th> <!-- 타겟(TOBE)코드도메인명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="tgtDmnLnm" id="tgtDmnLnm" style="width:98%;" value="${search.tgtDmnLnm}"/>
                                </span>
                            </td>
                            <th scope="row"><label for="tgtCdVal"><s:message code="TARG.CD.VAL"/></label></th> <!-- 타겟(TOBE) 코드값 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="tgtCdVal" id="tgtCdVal" style="width:98%;" value="${search.tgtCdVal}"/>
                                </span>
                            </td>
                            
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.POP' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop.jsp" />
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
    </div>
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>