<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- <html> -->
<!-- <head> -->
<!-- <title>DDL파티션 서브 리스트</title> -->

<script type="text/javascript">

$(document).ready(function() {
	
		//======================================================
        // 폼 검색 버튼 초기화 및 클릭 이벤트 처리 ...
        //======================================================
        //imgConvert($('div.tab_navi a img'));
		
		
        // 추가 Event Bind
        $("#btnPartSubNew").click(function(){ 
        	doActionSub("New");
// 			doAction("NewCol");

        });
        
        // 저장 Event Bind
        $("#btnPartSubSave").click(function(){ 
        	//저장할 대상이 있는지 체크한다.
        	if(!sub_sheet.IsDataModified()) {
        		//저장할 내역이 없습니다.
        		showMsgBox("ERR", "<s:message code="ERR.CHKSAVE" />");
        		return;
        	}
        	
//         	//저장할래요? 확인창...
//     		var message = "<s:message code="CNF.SAVE" />";
//     		showMsgBox("CNF", message, 'SaveCol');
        	doActionSub("Save");  
        
        }).show();

        // 삭제 Event Bind
        $("#btnPartSubDelete").click(function(){ 
        	//선택체크박스 확인 : 삭제할 대상이 없습니다..
        	if(checkDelIBS (sub_sheet, "<s:message code="ERR.CHKDEL" />")) {
        		//삭제 확인 메시지
    			//alert("삭제하시겠어요?");
    			showMsgBox("CNF", "<s:message code="CNF.DEL" />", 'DeleteSub');
        	}
        	
        }); //doActionSub("Delete");  });
        
                
        // 엑셀내리기 Event Bind
        $("#btnPartSubExcelDown").click( function(){ doActionSub("Down2Excel"); } );

        // 엑셀업로드 Event Bind
        // 기능제한
        $("#btnPartSubExcelLoad").click( function(){ doActionSub("LoadExcel"); } );
        
        
        
    }
);

$(window).on('load',function() {
	//그리드 초기화 
	initPartSubGrid();
	
	var rqststep = $("#mstFrm #rqstStepCd").val();
	
	//============================================
	// 요청단계별 버튼 및 그리드 처리... (요청단계 : N-작성전, S-임시저장, Q-등록요청, A-결재처리), grid_sheet
	//============================================
	setDispRqstMainButton(rqststep, sub_sheet);
	sub_sheet.SetColHidden("rvwStsCd"	,1);
	sub_sheet.SetColHidden("rvwConts"	,1);
	
	/* if(rqststep == "Q" || rqststep == "A") {
		$(".btn_move_top").parent().hide();
	} */
	
// 	doActionSub("Search");
	
	
});


$(window).resize(
    
    function(){
                
    	// sub_sheet.SetExtendLastCol(1);    
    }
);


function initPartSubGrid()
{
    
    with(sub_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headtext  = "<s:message code='META.HEADER.DDLPART.SUB.RQST'/>";
        //No.|상태|선택|검토상태|검토내용|요청구분|등록유형|검증결과|파티션ID|주파티션ID|서브파티션ID|DDL테이블ID|DDL테이블물리명|파티션명|서브파티션명|구분값|테이블스페이스|설명|요청일시|요청자ID|요청자명|요청번호|요청일련번호|요청상세일련번호

	    var headers = [
                    {Text:headtext, Align:"Center"}
                ];
        
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",	Width:50,   SaveName:"ibsSeq",	  Align:"Center", Edit:0},
					{Type:"Status", Width:40,   SaveName:"ibsStatus", Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Combo",  Width:80,   SaveName:"rvwStsCd",  Align:"Center", Edit:0, Hidden:1},						
					{Type:"Text",   Width:80,   SaveName:"rvwConts",  Align:"Left", Edit:0, Hidden:1},						
					{Type:"Combo",  Width:80,   SaveName:"rqstDcd",	  Align:"Center", Edit:1, KeyField:1},						
					{Type:"Combo",  Width:60,   SaveName:"regTypCd",  Align:"Center", Edit:0},						
					{Type:"Combo",  Width:80,   SaveName:"vrfCd",	  Align:"Center", Edit:0},
					
					{Type:"Text",    Width:40,  SaveName:"ddlPartId"    ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:40,  SaveName:"ddlMainPartId"    ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",    Width:40,  SaveName:"ddlSubPartId"    ,Align:"Left",   Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"ddlTblId",	 Align:"Left",   Edit:0, Hidden:1},
					
					{Type:"Text",   Width:140,  SaveName:"ddlTblPnm",     Align:"Left",   Edit:1, KeyField:1},
					{Type:"Text",   Width:120,  SaveName:"ddlPartPnm",    Align:"Left",   Edit:1, Hidden:0},
					{Type:"Text",   Width:120,  SaveName:"ddlPartSubPnm", Align:"Left",   Edit:1, KeyField:1},
                    {Type:"Text",   Width:200,  SaveName:"ddlPartSubVal", Align:"Left",   Edit:1, KeyField:0},
                    {Type:"Text",   Width:120,  SaveName:"tblSpacPnm",    Align:"Left",   Edit:0, KeyField:0, Hidden:1},

					{Type:"Text",   Width:200,  SaveName:"objDescn",	Align:"Left", 	 Edit:0, Hidden:1},
					{Type:"Text",   Width:120,  SaveName:"rqstDtm",  	Align:"Center", Edit:0, Format:"yyyy-MM-dd HH:mm:ss", Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserId",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:50,   SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
					{Type:"Text",   Width:60,   SaveName:"rqstNo",  Align:"Center", Edit:0}, 
					{Type:"Int",    Width:60,   SaveName:"rqstSno",  Align:"Center", Edit:0},
					{Type:"Int",    Width:50,   SaveName:"rqstDtlSno",  Align:"Center", Edit:0}
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
		SetColProperty("rvwStsCd", 	${codeMap.rvwStsCdibs});
		SetColProperty("rqstDcd", 	${codeMap.rqstDcdibs});
		SetColProperty("regTypCd", 	${codeMap.regTypCdibs});
		SetColProperty("vrfCd", 	${codeMap.vrfCdibs});
        
        InitComboNoMatchText(1, "");
        
        SetColHidden("rqstNo",1);
        SetColHidden("rqstSno",1);
        SetColHidden("rqstDtlSno",1);
        //SetColHidden("arrUsrId",1);
      
        // FitColWidth();
        SetSheetHeight(250);
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(sub_sheet);    
    //===========================
   
}


//검색 팝업에서 선택한 내용을 json으로 반환 받는다...
function getPopData(jsondata) {
	
	//alert(jsondata.subjLnm);
}


//화면상의 모든 액션은 여기서 처리...
function doActionSub(sAction)
{
        
    switch(sAction)
    {
        case "New":        //신규 추가...
        	//파티션의 현재 선택행을 확인 후 1행 이상이면 신규 폼 호출한다.
			var crow = grid_sheet.GetSelectRow();
			if(crow < 1) {
				showMsgBox("INF", "선택된 파티션이 없습니다. 파티션정의서 작성 후 상단그리드에서 해당 파티션을 선택하세요.");
				return;
			}
			
			//파티션 선택행이 있을경우 선택된 행아래 아니면 맨마지막에 행추가...
			var partrow = sub_sheet.DataInsert();
			
			var mjson = grid_sheet.GetRowJson(crow);
			sub_sheet.SetCellValue(partrow, "ddlTblPnm"	, mjson.ddlTblPnm);
			//파티션 명명규칙 default 값설정
			var partnm = "";
			var partid = "";
			var pmrow = col_sheet.GetSelectRow();
			if(pmrow >= 1) {
				partnm = col_sheet.GetCellValue(pmrow, "ddlPartPnm");
				partid = col_sheet.GetCellValue(pmrow, "ddlMainPartId");
				sub_sheet.SetCellValue(partrow, "ddlPartPnm", partnm);
				sub_sheet.SetCellValue(partrow, "ddlMainPartId", partid);
			} else {
				//선택된 주파티션이 없을 경우 파티션 타입정보를 가지고 셋팅한다.
				if ("RANGE" == mjson.partTypCd) {
					if (isBlankStr(mjson.subPartTypCd))
						 partnm = mjson.ddlTblPnm+"_RXXX";
					else partnm = mjson.ddlTblPnm+"_CXXX";
				} else if ("LIST" == mjson.partTypCd) {
					partnm = mjson.ddlTblPnm+"_LXXX";
				} else if ("HASH" == mjson.partTypCd) {
					partnm = mjson.ddlTblPnm+"_HXXX";
				}
			}
			
			//주파티션명 셋팅	
// 			sub_sheet.SetCellValue(partrow, "ddlPartPnm", partnm);
			//서브파티션명 셋팅
// 			sub_sheet.SetCellValue(partrow, "ddlPartSubPnm", partnm+"_SXXX");
			sub_sheet.SetCellValue(partrow, "ddlPartSubPnm", "SXXX");
			
        	
            break;

        case "NewChg":        //변경대상 추가...
        	//테이블 검색 팝업을 오픈한다...

            break;
            
        case "Delete" :
        	
        	//트리 시트의 경우 하위 레벨도 체크하도록 변경...
        	//setTreeCheckIBS(sub_sheet);
        	
        	//체크된 행 중에 입력상태인 경우 시트에서 제거...
        	delCheckIBS(sub_sheet);
        	
        	//체크된 행을 Json 리스트로 생성...
        	var DelJson = sub_sheet.GetSaveJson(0, "ibsCheck");
//         	ibsSaveJson = sub_sheet.GetSaveJson(0, "ibsCheck");
			if (DelJson.Code == "IBS000") return;
        	if (DelJson.Code == "IBS010") return;
//         	if(ibsSaveJson.data.length == 0) return;	//항목이 없는 경우 저장하지 않는다...
        	
        	var param = $('form[name=mstFrm]').serialize();
		    	param += "&sAction=" + sAction;
		    	param += "&ddlTrgDcd="+"${waqMstr.ddlTrgDcd}"; //개발단계
        	var url = "<c:url value="/meta/ddl/delddlsubpart.do"/>";
            IBSpostJson2(url, DelJson, param, ibscallback);
        	break;
        case "Save" :
        	var saveJson = sub_sheet.GetSaveJson(0);
        	if (saveJson.Code == "IBS000") return;
        	if (saveJson.Code == "IBS010") return;
        	
			var url = "<c:url value="/meta/ddl/regddlsubpart.do"/>";
			var param = $('form[name=mstFrm]').serialize();
			    param += "&sAction=" + sAction;
			    param +="&" + $('#frmInput').serialize();
			    param += "&ddlTrgDcd="+"${waqMstr.ddlTrgDcd}"; //개발단계
// 			var partobj = $("#frmInput").serializeObject();    
// 			saveJson.ddlpartobj = partobj;    
			IBSpostJson2(url, saveJson, param, ibscallback);
        	
        	break;  	
        
        case "Search":	//요청서 재조회...
        	//요청 마스터 번호가 있는지 확인...
        	
        	//요청서에 저장할 내역이 있는지 확인...
        	
        	//요청서 마스터 번호로 조회한다...
//         	var param = $('#colfrmSearch').serialize();
        	var param = $('#mstFrm').serialize();
        		param += "&ddlTrgDcd="+"${waqMstr.ddlTrgDcd}"; //개발단계
        	sub_sheet.DoSearch("<c:url value="/meta/ddl/getddlidxcolrqstlist.do" />", param);
//         	sub_sheet.DoSearchScript("testJsonlist");
        	break;
        	
        
       
        case "Down2Excel":  //엑셀내려받기
            sub_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;
        case "LoadExcel":  //엑셀업로드

        	//파티션의 현재 선택행을 확인 후 1행 이상이면 신규 폼 호출한다.
			var crow = grid_sheet.GetSelectRow();
			if(crow < 1) {
				showMsgBox("INF", "선택된 파티션이 없습니다. 파티션정의서 작성 후 상단그리드에서 해당 파티션을 선택하세요.");
				return;
			}
			
//         	sub_sheet.LoadExcel({Mode:'HeaderMatch', Append:1});
        	sub_sheet.LoadExcel({Mode:'HeaderMatch', Append:1,ExtendParam:'excelCnt='+$("#mstFrm #excelCnt").val()});
        	
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
function sub_sheet_OnDblClick(row, col, value, cellx, celly) {
    
	if(row < 1) return;
	
	//변경항목 조회
	//선택한 상세정보를 가져온다...
	var param =  sub_sheet.GetRowJson(row);
	
	var param1 = $("#mstFrm").serialize();
	param1 += "&rqstSno=" + param.rqstSno + "&rqstDtlSno=" + param.rqstDtlSno + "&subInfo=COL";
	$("#tabs #colinfo").click();
	grid_changecol.RemoveAll();
	if(param.regTypCd == 'U') {
		getRqstChg(param1, 'COL');
	}
	
}

function sub_sheet_OnClick(row, col, value, cellx, celly) {
    
    if(row < 1) return;
  	//선택한 셀이 Edit 가능한 경우는 리턴...(chechk 박스 선택시에만 리턴한다.)
	if(sub_sheet.GetColEditable(col)) return;
	
	//선택한 상세정보를 가져온다...
	var param = sub_sheet.GetRowJson(row);
	
	//선택한 그리드의 row 내용을 보여준다.....
// 	var tmphtml = '컬럼명 : ' + param.ddlIdxColPnm + ' [' + param.ddlIdxColLnm +']';
// 	$('#col_sel_title').html(tmphtml);
	
// 	loadDetailCol(param);
	
	var param1  = "bizDtlCd=DDLPARTS";
	    param1 += "&rqstNo="+param.rqstNo;
	    param1 += "&rqstSno="+param.rqstSno;
	    param1 += "&rqstDtlSno="+param.rqstDtlSno;
		 
	getRqstVrfLst(param1);
    
	if(param.regTypCd == 'U') {
		var param2 = $("#mstFrm").serialize();
		param2 += "&rqstSno=" + param.rqstSno + "&rqstDtlSno=" + param.rqstDtlSno + "&subInfo=COL";
		grid_changecol.RemoveAll();
		getRqstChg(param2, 'COL');
	}
    
}


function sub_sheet_OnSaveEnd(code, message) {
	//alert(code);
	if (code == 0) {
		alert("저장 성공했습니다.");
	} else {
		alert("저장 실패했습니다.");
	}
}

function sub_sheet_OnSearchEnd(code, message) {
	//alert(sub_sheet.GetDataBackColor()+":"+ sub_sheet.GetDataAlternateBackColor());
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	} else {
// 		$('div#detailInfocol').empty();
// 		$('#btnColReset').click();
		//alert("Search End");
		//테이블 요청 리스트가 조회되면...
		//첫번째 행을 선택하고 하위 컬럼 요청서를 조회한다...
		
	}
	
}

function sub_sheet_OnLoadExcel(result,code,msg) {
	var excelCnt = $("#mstFrm #excelCnt").val();
	if(code==1){
	}else{
		// ibmsg.xml msg 사용 msg
// 		showMsgBox("ERR", msg);
		showMsgBox("ERR", "엑셀업로드 가능 건수는 <font color=red>"+excelCnt+ "</font> 건 입니다.<br>일괄등록 엑셀 데이터 건수를 확인 하십시오.");
	}	
}


</script>

<!-- </head> -->

<!-- <body> -->
<%--  	<div style="clear:both; height:10px;"><span></span></div> --%>
 	<div class="stit">서브파티션 정보</div>
 	<div style="clear:both; height:5px;"><span></span></div>
    <legend>머리말</legend>
       <div class="tb_basic">
           <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
               <caption>
               테이블 이름
               </caption>
               <colgroup>
               <col style="width:15%;" />
               <col style="width:35%;" />
               <col style="width:15%;" />
               <col style="width:35%;" />
               </colgroup>
               <tbody>
                   <tr>
                      <th scope="row" ><label for="sub_subPartTypCd">서브파티션 유형</label></th>
                       <td>
                       	<select id="sub_subPartTypCd" name="sub_subPartTypCd" >
                       		<option value="">---선택---</option>
                        	<c:forEach var="code" items="${codeMap.partTypCd}" varStatus="status" >
                               <option value="${code.codeCd}">${code.codeLnm}</option>
                            </c:forEach>
                        </select>
                       </td>
                       <th scope="row" ><label for="sub_subPartKey">서브파티션키</label></th>
                   		<td >
                     	<input type="text" id="sub_subPartKey" name="sub_subPartKey" class="wd200" readonly="readonly" />
                     	<button class="btnSearchPop" id="sub_subPartKeyPop">검색</button>
                     	</td>
                   </tr>
               </tbody>
           </table>
       </div>

 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="stit">서브파티션 리스트</div>
 	<div style="clear:both; height:10px;"><span></span></div>
 	<div class="tb_comment">
	<b>* 서브파티션 명명규칙</b>
	<br/>- 테이블명+_+주파티션명+S+XXX
	</div>
	<div style="clear:both; height:10px;"><span></span></div>
         <!-- 버튼영역  -->
        <div class="divLstBtn" style="padding-right :10px">	 
            <div class="bt03">
<!-- 			    <button class="btn_search" id="btnSearch" 	name="btnSearch">재조회</button>  -->
                <button class="btn_rqst_new2" id="btnPartSubNew" name="btnPartSubNew">추가</button>                                                         
				  <%-- <ul class="add_button_menu">
				    <li class="btn_new" id="btnPartSubNew"><a><span class="ui-icon ui-icon-pencil"></span>컬럼 추가</a></li>
				    <li class="btn_chang_add" id="btnColChangAdd"><a><span class="ui-icon ui-icon-folder-open"></span>변경대상 추가</a></li>
				    <li class="btn_excel_load" id="btnPartSubExcelLoad"><a><span class="ui-icon ui-icon-document"></span>엑셀 올리기</a></li>
				  </ul> --%>         
			    <button class="btn_save" id="btnPartSubSave" 	name="btnPartSubSave">저장</button> 
			    <button class="btn_delete" id="btnPartSubDelete" 	name="btnPartSubDelete">삭제</button> 
<!-- 			    <button class="btn_check" id="btnCheck" 	name="btnCheck">검증</button>  -->
<!-- 			    <button class="btn_reg_rqst" id="btnRegRqst" name="btnRegRqst">등록</button>  -->
			</div>
            <!-- <div class="bt03" style="padding-left: 10px;">
				<button class="btn_move_top"   id="btnColMoveTop">맨 위로 이동</button>
				<button class="btn_move_up"   id="btnColMoveUp">위로 이동</button>
				<button class="btn_move_down" id="btnColMoveDown">아래로 이동</button>
				<button class="btn_move_bottom" id="btnColMoveBottom">맨 아래로 이동</button>
			</div> -->
			<!-- <div class="bt03" style="padding-left: 10px;">
				<button class="btnSearchPop"   id="ddlColAutoPop">간편추가</button>
			</div> -->
			<div class="bt02">
	          <button class="btn_excel_down" id="btnPartSubExcelLoad" name="btnPartSubExcelLoad">엑셀 업로드</button>                       
	          <button class="btn_excel_down" id="btnPartSubExcelDown" name="btnPartSubExcelDown">엑셀 내리기</button>                       
	    	</div>
        </div>
         <!-- 버튼영역  -->
<div style="clear:both; height:5px;"><span></span></div>
<div class="tb_comment"></div>
	<!-- 그리드 입력 입력 -->
	<div id="grid_03" class="grid_01">
	     <script type="text/javascript">createIBSheet("sub_sheet", "100%", "150px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "sub_sheetƒ", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->
<div style="clear:both; height:5px;"><span></span></div>


<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<!-- </body> -->
<!-- </html> -->