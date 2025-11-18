<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> --%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title><s:message code="STRD.WORD.SRH" /></title> <!-- 표준단어검색 -->
<script type="text/javascript">

var popRqst = "${search.popRqst}";
var connTrgSchJson = ${codeMap.connTrgSchIdCodeTsf} ;
//alert(popRqst);

EnterkeyProcess('Search');
$(document).ready(function() {
	$(window).focus();
// 		alert("document.ready");
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
		
				
		//탭 초기화....
//	 	$( "#tabs" ).tabs({heightStyle:"fill"});
// 		//$( "#tabs" ).tabs();
                    
        //그리드 초기화 
//         initGrid();
       //조회 이벤트 처리
		$("#popSearch").click(function(){ 
			doAction('Search'); 
		});
		//적용 버튼 이벤트 처리
		if (popRqst == 'Y') {
	        // 적용 Event Bind
	        $("#popApply").click(function(){ 
	        	if(checkDelIBS (grid_pop, "<s:message code="REQ.NO.CHANG" />")) {
					doAction("Apply");
		    	}
			}).show();
	        $("#popDelete").click(function(){ 
	        	if(checkDelIBS (grid_pop, "<s:message code="REQ.NO.DEL" />")) {
					doAction("DelRqst");
		    	}
			}).hide();
        }
	
		//폼 초기화 버튼 초기화...
		$('#popReset').click(function(event){
			event.preventDefault();
	// 		alert("초기화버튼");
			$("form[name=frmPopSearch]")[0].reset();
		}).show();
                      
        
        // 엑셀내리기 Event Bind
        $("#popExcelDown").click( function(){ doAction("Down2Excel"); } );

        // 엑셀업로 Event Bind
         //$("#btnExcelLoad").click( function(){ doAction("LoadExcel"); } ); 
        
        //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
        $("div.pop_tit_close").click(function(){
        	
        	//iframe 형태의 팝업일 경우
        	if ("${search.popType}" == "I") {
        		parent.closeLayerPop();
        	} else {
        		window.close();
        	}
        	
        });
        
      //파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
		//자동완성기능 추후 적용
      //setautoComplete($("#frmSearch #stwdLnm"), "STWD");
       
        //======================================================
        // 셀렉트 박스 초기화
        //======================================================
        // 시스템영역
//         create_selectbox(usergJson, $("#usergId"));
     
       //	double_select(connTrgSchJson, $("#frmSearch #dbConnTrgId"));
	  //	$('select', $("#frmSearch #dbConnTrgId").parent()).change(function(){
	   //		double_select(connTrgSchJson, $(this));
	  // 	});
	  
      //DBMS 스키마 검색 팝업 호출
    	$("#dbmsSchemaPop").click(function(){
        	var param = "tblSpacTypCd=T";
        		param += "&ddlTrgDcd=R";
        	openLayerPop("<c:url value='/commons/damgmt/db/popup/dbschema_pop.do' />", 600, 500, param);
        });
    }
    
		
);

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	$(window).resize();
	//페이지 호출시 처리할 액션...
// 	doAction('Search');
});


$(window).resize(
    
    function(){
    	//그리드 높이 조정 : 그리드 현재 위치부터 페이지 최하단까지 높이로 변경한다.....
    	setibsheight($("#grid_01"));        
    	// grid_pop.SetExtendLastCol(1);    
    }
);

//DBMS 정보 팝업 리턴값 처리
function returnDbSchemaPop(ret) {
 	//alert(ret);
	var retjson = jQuery.parseJSON(ret);
	
	$("#dbConnTrgId").val(retjson.dbConnTrgId);
	$("#dbSchId").val(retjson.dbSchId);
	$("#dbConnTrgPnm").val(retjson.dbConnTrgPnm);
	$("#dbSchPnm").val(retjson.dbSchPnm);
}

function initGrid()
{
    
    with(grid_pop){
    	
      	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext = "<s:message code='META.HEADER.MESSAGETSF.POP'/>";
        //No.|상태|선택|메시지ID|유형구분코드|업무구분코드|시스템구분코드|메시지코드|메시지내용|사용여부|설명|객체버전
//         	headtext += "|요청번호|요청일련번호|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID";			
        
        var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 
        

        var cols = [                        
                    {Type:"Seq",    Width:40,   SaveName:"ibsSeq",       Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",    Align:"Center", Edit:0},
                    {Type:"CheckBox", Width:50,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Sort:0},
                    {Type:"Text",   Width:40,  SaveName:"msgId",    Align:"Center", Edit:0},
                    {Type:"Combo",   Width:80,  SaveName:"typDivCd",    Align:"Left", Edit:0}, 
                    {Type:"Combo",   Width:80,  SaveName:"bizDivCd",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:80,  SaveName:"sysDivCd",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"msgCd",    Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:150,  SaveName:"msgConts",    Align:"Left", Edit:0}, 
                    {Type:"Combo",   Width:60,  SaveName:"useYn",    Align:"Center", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"objDescn",    Align:"Left", Edit:0}, 
                    {Type:"Int",   Width:30,  SaveName:"objVers",    Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);
	     
        //콤보 목록 설정...
	  
       	SetColProperty("typDivCd",	${codeMap.msgPtrnDvcdibs});
		SetColProperty("bizDivCd",	${codeMap.bizDivCdibs});
 	    SetColProperty("useYn", {ComboCode:"Y|N", 	ComboText:"Y|N"});
        //InitComboNoMatchText(1, "");

        //히든 컬럼 설정...
        SetColHidden("ibsStatus"	,1);
        SetColHidden("msgId"		,1);
        SetColHidden("objVers"		,1);
        SetColHidden("orgDs"		,1);
      
//         FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_pop);    
    //===========================
}

function doAction(sAction)
{
        
    switch(sAction)
    {		    
        

            
        case "Search":
        	var param = $('#frmSearch').serialize();

            //if($("#dbSchId option:selected").val()==''){
            //	showMsgBox("INF", "<s:message code="REQ.NO.TGTSCH" />");	
            //	return false;
            //}
        	grid_pop.DoSearch('<c:url value="/meta/stnd/getmessagetsflist_pop.do" />', param);
        	
        	break;
        	
        case "Apply": //적용버튼 액션...
			


        	var saveJson = grid_pop.GetSaveJson(0, "ibsCheck");
			
			//2. 데이
// 			alert(saveJson.Code); 처리대상 행이 없는 경우 리턴한다.
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
	
        	
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
        	//iframe 형태의 팝업일 경우
        	var param = "";
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	param += "&rqstDcd=CU";
        	param += "&tgtDbConnTrgPnm="+$("#dbConnTrgPnm").val();
        	param += "&tgtDbConnTrgId="+$("#dbConnTrgId").val();
        	param += "&tgtDbSchPnm="+$("#dbSchPnm").val();
        	param += "&tgtDbSchId="+$("#dbSchId").val();
        	
        	 if($("#dbSchId option:selected").val()==''){
             	showMsgBox("INF", "<s:message code="REQ.NO.TGTSCH" />");	
             	return false;
             }
        	var url = "<c:url value="/meta/stnd/regMessageTsf.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
        	//조회화면에서 팝업 호출했을 경우....
           // $("div.pop_tit_close").click();
        	break;
        	
 	     case "DelRqst": //삭제요청...
        	var saveJson = grid_pop.GetSaveJson(0, "ibsCheck");
			
			//2. 데이
// 			alert(saveJson.Code); 처리대상 행이 없는 경우 리턴한다.
			if (saveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
        	//wam2waq에 저장 처리한다. 반드시 마스터 폼 id가 #mstFrm이어야 한다....
        	//iframe 형태의 팝업일 경우
        	var param = "";
        	if ("${search.popType}" == "I") {
        		param = $("#mstFrm", parent.document).serialize();
        	} else {
        		param = $("#mstFrm", opener.document).serialize();
        	}
        	param += "&rqstDcd=DD";
        	var url = "<c:url value="/meta/stnd/regCodeTsf.do" />";
			
			IBSpostJson2(url, saveJson, param, ibscallback);
			//처리중입니다. 메세지
// 			showMsgBox("PRC", "<s:message code="REQ.PRC.LOAD" />");
 	        
 	      	break;
    		
    	case 'Detail' : //상세 정보
    		//onSelectRow 그리드 함수에서 처리...
    		break;
    	
    	case "Down2Excel":  //엑셀내려받기
    		
    		grid_pop.Down2Excel({HiddenColumn:1, Merge:1});
			
			break;

    }       
}


// //팝업 리턴값 제공
function returnPop(ret) {
// 	alert(JSON.stringify(ret));
	opener.frmInput.stwdId.value = ret;
	window.close();
}






/*
    row : 행의 index
    col : 컬럼의 index
    value : 해당 셀의 value
    x : x좌표
    y : y좌표
*/
function grid_pop_OnDblClick(row, col, value, cellx, celly) {
    if(row < 1) return;
    
	var retjson = grid_pop.GetRowJson(row);
	
	//요청서용 팝업일 경우.....
	if (popRqst == 'Y') {
		//체크박스 선택/해제 토글 기능.....
		var cellchk = grid_pop.GetCellValue(row, "ibsCheck");
		if(cellchk == '0') {
			grid_pop.SetCellValue(row, "ibsCheck", 1);
		} else {
			grid_pop.SetCellValue(row, "ibsCheck", 0);
		}
		
		return;
	}
    
//     returnPop(stwdId);
    
//     ibs2formmapping(row, $("form#frmInput", opener.document), grid_pop);
	
// 	window.close();

}

function grid_pop_OnClick(row, col, value, cellx, celly) {
	//$("#hdnRow").val(row);
	
	if(row < 1) return;
	
	//선택한 셀의 savename이 아래와 같으면 리턴...
// 	var colsavename = grid_pop.ColSaveName(col);
// 	if ('ibsSeq' == colsavename || 'ibsStatus' == colsavename || 'ibsCheck' == colsavename) return;
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_pop.GetColEditable(col)) return;
	//alert("상세정보 조회 가능"); return;

	//tblClick(row);
	
	//선택한 상세정보를 가져온다...
	var param =  grid_pop.GetRowJson(row);

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '<s:message code="STRD.WORD.NM" /> : ' + param.stwdLnm +' [ <s:message code="PHYC.NM" /> : ' + param.stwdPnm + ' ]'; //표준단어명, 물리명
	$('#stwd_sel_title').html(tmphtml);
	

	

}

//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
function postProcessIBS(res) {
	//alert(res.action);
	
	switch(res.action) {

		//기존 표준단어 요청서에 변경요청 추가 후처리 함수...
		case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
			if(!isBlankStr(res.resultVO.rqstNo)) {
//				alert("dd");
// 	    		alert(res.resultVO.rqstNo);
				if ("${search.popType}" == "I") {
					parent.postProcessIBS(res);
				}else{
					opener.postProcessIBS(res);
				}
		         
				//팝업닫기
 				$("div.pop_tit_close").click();
// 	    		json2formmapping ($("#mstFrm", opener.document), res.resultVO);
	    		
	    		//업무상세메시지는 마스터에 없으므로 강제로 셋팅한다.
// 	    		$("#mstFrm #bizDtlCd", opener.document).val(res.resultVO.bizInfo.bizDtlCd);
// 	    		$("#mstFrm #bizDtlCd", opener.document).val("SDITM");
	    		
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




</script>
</head>

<body>
<div class="pop_tit" >
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt"><s:message code="MESSAGE.INQ"/></div> <!-- 메시지 검색 -->
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->

    <!-- 팝업 내용 시작 -->
    <div class="pop_content">
<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="INQ.COND2" /></div> <!-- 검색조건 -->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MENU.INQ' />"> <!-- 메뉴조회 -->
                   <caption></caption>
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                  </colgroup>
                   
                   <tbody>
                   		<tr>                          
                   		
                            <th scope="row"><label for="msgCd"><s:message code="MSG.CD" /></label></th> <!-- 메시지코드 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="msgCd" id="msgCd" />
                                </span>
                            </td>  
                            <th scope="row"><label for="useYn"><s:message code="USE.YN" /></label></th> <!-- 사용여부 -->
                            <td>
                                <span class="input_file">
                                <select class="wd100" name="useYn" id="useYn" >
                                   <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                   <option value="Y">Y</option>
                                   <option value="N">N</option>
                                </select>
                                </span>
                            </td>  
                       </tr>
                        <tr>                          
                            <th scope="row"><label for="typDivCd"><s:message code="PTRN.DV.CD" /></label></th> <!-- 유형구분코드 -->
                            <td>
                               <select class="wd100" id="typDivCd" name="typDivCd" >
							           <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
							       <c:forEach var="code" items="${codeMap.msgPtrnDvcd}" varStatus="status">
							           <option value="${code.codeCd}">${code.codeLnm}</option>
							       </c:forEach>
							   </select>
                            </td>  
                              <th scope="row"><label for="bizDivCd"><s:message code="BZWR.DV.CD" /></label></th> <!-- 업무구분코드 -->
                            <td>
                               <select class="wd100" id="bizDivCd" name="bizDivCd" >
							        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
							     <c:forEach var="code" items="${codeMap.bizDivCd}" varStatus="status">
							        <option value="${code.codeCd}">${code.codeLnm}</option>
							     </c:forEach>
							  </select>
                            </td>  
                       </tr>

                       <tr>
                       	<th scope="row"><label for="dbSchId"><s:message code="TARG.DB.SCHEMA.NM" /></label></th> <!-- 타겟 DB/스키마명 -->
                        <td colspan="3">
					    <span class="input_inactive"><input type="hidden" class="wd100" id="dbConnTrgId" name="dbConnTrgId"  value="${result.dbConnTrgId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="hidden" class="wd100" id="dbSchId" name="dbSchId"  value="${result.dbSchId}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd100" value="${result.dbConnTrgPnm}" readonly="readonly"/></span>
						<span class="input_inactive"><input type="text" class="wd100" id="dbSchPnm" name="dbSchPnm" class="wd100" value="${result.dbSchPnm}" readonly="readonly"/></span>
						<button class="btnDelPop" ><s:message code="DEL" /></button> <!-- 삭제 -->
						<button class="btnSearchPop" id="dbmsSchemaPop"><s:message code="SRCH" /></button> <!-- 검색 -->
					</td>  
					    </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
   
        <!-- 조회버튼영역  -->
		 <c:choose>
         <c:when test="${search.popRqst == 'Y'}">
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonRqstPop2.jsp" />
         </c:when>
         <c:otherwise>
        <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" />
         </c:otherwise>
         </c:choose>   
        <div style="clear:both; height:5px;"><span></span></div>
      <c:choose>
         <c:when test="${search.popRqst == 'Y'}">
        	<div class="tb_comment"><s:message  code='ETC.POP.CDTSF' /></div>
         </c:when>
         <c:otherwise>
    
         </c:otherwise>
       </c:choose>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_pop", "100%", "300px");</script>            
	</div>
	<!-- 그리드 입력 입력 End -->
   
	<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title" id="stwd_sel_title"> <span></span></div>
	</div>
	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->
	<div style="clear:both; height:5px;"><span></span></div>

   
<%-- <form id="frmExcel" name="frmExcel" action="" method="post" > --%>
<!-- 	<input type="hidden" name="excelhtml" id="excelhtml"> -->
<!-- 	<input type="hidden" name="excelname" id="excelname"> -->
<%-- </form> --%>


<!-- <div id="excel_pop"> -->
<!-- 	<iframe src=""></iframe> -->
<!-- </div> -->

</body>
</html>

