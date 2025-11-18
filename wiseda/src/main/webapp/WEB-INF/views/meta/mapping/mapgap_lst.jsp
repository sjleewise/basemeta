<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title>컬럼 매핑정의서 GAP 조회 </title>

<script type="text/javascript">


$(document).ready(function() {
	 
		//$( "#tabs" ).tabs();
		
		//마우스 오버 이미지 초기화
// 		//imgConvert($('div.tab_navi a img'));
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  }).show();
        
        $("#btnApply").click(function(){ doAction("Apply");  }).show();
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
	
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
		setautoComplete($("#frmSearch #dmnLnm"), "DMN");
        
        $('#subjSearchPop').click(function(event){
	    	
		    	event.preventDefault();	//브라우저 기본 이벤트 제거...
		
		//$('div#popSearch iframe').attr('src', "<c:url value='/meta/test/pop/testpop.do' />");
		//$('div#popSearch').dialog("open");
	    	var url = "<c:url value='/meta/subjarea/popup/subjSearchPop.do' />";
	    	var param = $("form#frmSearch").serialize(); //$("form#frmInput").serialize();
			var popwin = OpenModal(url+"?"+param, "searchpop",  600, 500, "no");
			popwin.focus();
		
	});
        
 		$("#btnDelPop").click(function(){
        	
        	$("#fullPath").val(""); 
//         	$("#subjLnm").val("");
        	
        }).show();   
	
}); 

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
// 	alert('window.load');
	initGrid();
	
	//$( "#layer_div" ).show();
});



function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
					{Text:"<s:message code='META.HEADER.MAPGAP.LST'/>", Align:"Center"}
					];
			//NO.|선택|매핑컬럼ID|GAP 상태|신규 컬럼(건)|삭제 컬럼(건)|변경 컬럼(건)|FULL_PATH|테이블ID|테이블명(논리명)|테이블명(물리명)|응용담당자|전혼담당자명
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Seq",	Width:50,   SaveName:"ibsSeq",	Align:"Center", Edit:0},
						{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0, Hidden:0},
						
						{Type:"Text",   Width:130,  SaveName:"colMapId",		Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,  SaveName:"gapStaus",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"regC",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"regD",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"regU",		Align:"Left", Edit:0},
						{Type:"Text",   Width:100,  SaveName:"fullPath",		Align:"Left", Edit:0},
						{Type:"Text",   Width:130,  SaveName:"pdmTblId",		Align:"Left", Edit:0, Hidden:1}, 
						{Type:"Text",   Width:130,  SaveName:"pdmTblPnm",		Align:"Left", Edit:0}, 
						{Type:"Text",   Width:130,  SaveName:"pdmTblLnm",		Align:"Left", Edit:0},		
						{Type:"Text",   Width:80,  SaveName:"appCrgpNm",		Align:"right", Edit:0},
						{Type:"Text",   Width:80,  SaveName:"cnvsCrgpNm",		Align:"right", Edit:0},

						{Type:"Date",   Width:80,  SaveName:"aprvDtm",			Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						{Type:"Date",   Width:80,  SaveName:"rqstDtm",			Align:"Left", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						{Type:"Date",   Width:80,  SaveName:"frsRqstDtm",		Align:"right", Edit:0, Format:"yyyy-MM-dd HH:mm:ss" ,Hidden:1},
						
						{Type:"Text",   Width:80,  SaveName:"aprvUserNm",		Align:"Left", Edit:0 ,Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"rqstUserNm",		Align:"right", Edit:0 ,Hidden:1},
						{Type:"Text",   Width:80,  SaveName:"frsRqstUserNm",	Align:"right", Edit:0,Hidden:1}
						
					];
                    
        InitColumns(cols);
        
        //매핑구분, 이행구분 콤보코드로 작성
        SetColProperty("gapStaus",${codeMap.mapColGapibs});
        //SetColHidden("rqstUserNm",1);

        InitComboNoMatchText(1, "");
        
        FitColWidth();
        
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
    	grid_sheet.DoSearch("<c:url value="/meta/mapping/getColMapGap.do" />", param);
		break;
	case "Down2Excel": //엑셀내려받기
		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
		break;
    case "Apply":

    	var saveJson = grid_sheet.GetSaveJson(0, "ibsCheck");

		if (saveJson.Code == "IBS000") return;
		col_sheet.RemoveAll();
       	var param = "";
       	var pdmTblPnm = "pdmTblPnm1=";
       	var fullPath = "&fullPath1=";
       	var json = jQuery.parseJSON(JSON.stringify(saveJson));
       	var retjsonLen = json.data.length;
		var opt = { Append : 1 };
       	for(var i=0; i<retjsonLen;++i){
       		col_sheet.DoSearch("<c:url value="/meta/mapping/getColMapGapDtl.do" />", pdmTblPnm+json.data[i].pdmTblPnm+fullPath+json.data[i].fullPath,opt);
    	}
       	
//        	for(var i=0; i<retjsonLen;i++){
// 	       	pdmTblPnm += json.data[i].pdmTblPnm+"' , '";
// 	       	fullPath += json.data[i].fullPath+"' , '";
//     	}
//        	pdmTblPnm = pdmTblPnm.slice(0,-5);
//        	fullPath = fullPath.slice(0,-5);
//        	param = pdmTblPnm +fullPath;
//        	alert(pdmTblPnm+"/"+fullPath+"/"+param);

//        	col_sheet.DoSearch("<c:url value="/meta/mapping/getSelColMapGapDtl2.do" />", param,opt);
//        	col_sheet.DoSearch("<c:url value="/meta/mapping/getSelColMapGapDtl2.do" />", param);
    	
//        	var url = "<c:url value='/meta/mapping/getSelColMapGapDtl2.do' />";
//        	col_sheet.DoSearch(IBSpostJsonNoMsg(url, saveJson, param, ibscallback),param);
    	
    	break;
	}
}


function grid_sheet_OnClick(row, col, value, cellx, celly) {
	
	
	if(row < 1) return;
	
	
	//선택한 셀이 Edit 가능한 경우는 리턴...
	if(grid_sheet.GetColEditable(col)) return;

	//선택한 상세정보를 가져온다...
	var param =  grid_sheet.GetRowJson(row);
	//var dmnId = "&dmnId="+grid_sheet.GetCellValue(row, "dmnId");

	//선택한 그리드의 row 내용을 보여준다.....
	var tmphtml = '주제영역 : ' + param.fullpath + ' , 테이블명 : '+param.pdmTblPnm;
	$('#dmn_sel_title').html(tmphtml);
	
	//ibs2formmapping() 함수를 통해  sheet의 데이터와 폼의 데이터 id일치하면 뿌려줌
	ibs2formmapping(row, $("form#frmInput"), grid_sheet);
	
	$("form[name=frmInput] #frsRqstDtm").val(grid_sheet.GetCellText(row, "frsRqstDtm"));
	$("form[name=frmInput] #rqstDtm").val(grid_sheet.GetCellText(row, "rqstDtm"));
	$("form[name=frmInput] #aprvDtm").val(grid_sheet.GetCellText(row, "aprvDtm"));

	//컬럼목록 조회
	$("form#frmSearch input[name=pdmTblPnm1]").val(grid_sheet.GetCellValue(row, "pdmTblPnm"));
	$("form#frmSearch input[name=fullPath1]").val(grid_sheet.GetCellValue(row, "fullPath"));
	doActionCol("Search");
	
// 	$("form#frmSearch input[name=pdmTblPnm]").val("");
// 	$("form#frmSearch input[name=fullPath]").val("");
	//var param1 = "dmnId="+param.dmnId+"&tgtDmnId="+param.tgtDmnId;
	
	//tabs에 데이터 상세 
	//grid_sub.DoSearch('<c:url value="/meta/symn/ajaxgrid/getDmnStruct_dtl.do"/>',param1);
	
}

//주제영역 팝업 리턴값 처리
function returnSubjPop (ret) {
// 	alert(ret);
	
	var retjson = jQuery.parseJSON(ret);
	
	//$("#frmSearch #subjLnm").val(retjson.subjLnm);
 	$("#frmSearch #fullPath").val(retjson.fullPath); 
	
}
</script>
</head>
<body>

<div id="layer_div" >   
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title">컬럼 매핑정의서 GAP 조회</div>
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit">검색조건</div>
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmSearch" name="frmSearch" method="post">
            <fieldset>
            <legend>머리말</legend>
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="도메인그룹">
                   <caption>컬럼 매핑정의서 GAP 조회 검색폼</caption>
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   <col style="width:10%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                   	            <th scope="row" ><label for="fullPath">주제영역</label></th>
                                <td colspan = "3">
                                <span class="input_file">
                                	<input type="text" name="fullPath" id="fullPath" class="wd340"/>
                                	<input type="hidden" name="fullPath1" id="fullPath1" />
                                	<button class="btnDelPop" id="btnDelPop">삭제</button>
		                            <button class="btnSearchPop" id="subjSearchPop">검색</button>
                                </span>
                           		</td>
                                <th scope="row"><label for="">GAP상태</label></th>
                           		<td>
		                        <select id="gapStaus" class="wd200" name="gapStaus">
		                        	<option value="">전체</option>
		                        	<c:forEach var="code" items="${codeMap.mapColGap}" varStatus="status">
									<option value="${code.codeCd}">${code.codeLnm} </option>	
									</c:forEach>
		                        </select>
	                            </td>
                            </tr>
                            <tr>
                                <th scope="row"><label for="pdmTblPnm">테이블명</label></th>
                                <td><input type="text" id="pdmTblPnm" name="pdmTblPnm" class="wd300"/>
                                <input type="hidden" id="pdmTblPnm1" name="pdmTblPnm1" /></td>
                                <th scope="row"><label for="appCrgpNm">응용담당자명</label></th>
                                <td><input type="text" id="appCrgpNm" name="appCrgpNm" class="wd300"/></td>
                                <th scope="row"><label for="cnvsCrgpNm">전환담당자명</label></th>
                                <td><input type="text" id="cnvsCrgpNm" name="cnvsCrgpNm" class="wd300"/></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 <span style="font-weight:bold; color:#444444;">Ctrl + C</span>를 사용하시면 됩니다. - <span style="font-weight:bold; color:#444444;">정상</span> : TOBE 테이블과 컬럼매핑정의서가 일치하는 경우 입니다. - <span style="font-weight:bold; color:#444444;">컬럼GAP</span> : TOBE모델이 변경(컬럼추가,삭제)되여 컬럼 매핑정의서와 GAP이 있는 경우입니다.<span style="font-weight:bold; color:#444444;">(체크대상:데이터타입,코드도메인명,컬럼순서)</span></div>
        <div class="tb_comment">- <span style="font-weight:bold; color:#444444;">매핑테이블만 존재</span> : 테이블매핑정의서는 존재하나 컬럼 매핑정의서가 없는 경우입니다.(테이블 매핑 구분 M,R만 비교) - <span style="font-weight:bold; color:#444444;">TOBE 테이블만 존재</span> : TOBE 테이블이 존재하나 테이블매핑정의서와 컬럼매핑정의서가 없는 경우입니다.  - <span style="font-weight:bold; color:#444444;">TOBE 테이블 미존재</span> : 컬럼매핑정의서는 존재하나 TOBE 테이블이 삭제 되었을 경우 입니다. </div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
<div style="clear:both; height:5px;"><span></span></div>
<div class="bt03">
	<button class="btn_search" id="btnSearch" name="btnSearch" style="display:none">조회</button> 
	<button class="btn_apply" id="btnApply" name="btnApply" style="display:none">상세조회</button> 
</div>
<div class="bt02">
	<button class="btn_excel_down" id="btnExcelDown" name="btnExcelDown" style="display:none">엑셀 내리기</button>                       
</div>
<div style="clear:both; height:5px;"><span></span></div>

</div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>
<!-- 그리드 입력 입력 End -->


<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
<div class="selected_title_area">
	    <div class="selected_title" id="dmn_sel_title"> <span></span></div>
</div>
<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 End-->

<div style="clear:both; height:5px;"><span></span></div>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">상세정보</a></li>
	</ul>
	<div id="tabs-1">
		 <%@include file="mapgapdtl_lst.jsp" %> 
	</div>
</div>
</div>
</body>
</html>