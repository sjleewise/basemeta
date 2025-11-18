<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>


<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript">
EnterkeyProcess("Search");

var sysareaJson = ${codeMap.sysarea} ;	//시스템영역 코드 리스트 JSON...


$(document).ready(function(){
	
    $("#btnSearch").click(function(){ doAction("Search");  }).show();
    
    // 엑셀내리기 Event Bind
    $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } ).show();
    
    create_selectbox(sysareaJson, $("#sysAreaId"));
    
    
});
$(window).on('load',function(){
	
	initGrid();
	doAction("Search");
	barchartDrow();
	
});

// $(window).resize(function(){
	
// 	var tmptop = $('div#rank_arrow').offset().top + $('div#rank_arrow').height()+23;
// 	var tmpleft = $('div#rank_arrow').offset().left + $('div#rank_arrow').width() - $('ol.nm_rank_list').width()-5;
// 	//alert(tmptop+":"+tmpleft);
// 	$('ol.nm_rank_list').css({
// 			top:tmptop,
// 			left:tmpleft
// 	});

// });


function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='META.HEADER.METASUBJ.LST'/>", Align:"Center"}
                ];
                //No.|시스템영역|주제영역ID|주제영역논리명|주제영역물리명|주제영역약어|상위주제영역ID|상위주제영역명|주제영역레벨|표준적용여부|표준분류|레거시구분코드|설명|버전|등록유형|작성일시|작성자ID|작성자명|구현테이블수|비표준테이블수|전체경로
        
        var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Combo",  Width:150,  SaveName:"sysAreaId",   Align:"Left", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:250,  SaveName:"subjLnm",   	Align:"Left", Edit:0, KeyField:1, TreeCol:1},
                    {Type:"Text",   Width:120,  SaveName:"subjPnm",   	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"subjAbrNm", 	Align:"Left", Edit:0},
                    {Type:"Text",   Width:100,  SaveName:"uppSubjId", 	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"uppSubjNm", 	Align:"Left", Edit:0},
                    {Type:"Text",   Width:80,   SaveName:"subjLvl", 	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Combo",  Width:80,   SaveName:"stdAplYn",   	Align:"Center", Edit:0},
                    {Type:"Combo",  Width:100,  SaveName:"stndAsrt",    Align:"Center", Edit:0},
                    {Type:"Combo",  Width:90,   SaveName:"lecyDcd",   	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:150,  SaveName:"objDescn",    Align:"Left", 	Edit:0},
                    {Type:"Text",   Width:40,   SaveName:"objVers",     Align:"Right",   Edit:0, Hidden:1},
                    {Type:"Combo",  Width:40,   SaveName:"regTypCd",    Align:"Center", Edit:0, Hidden:1},                        
                    {Type:"Text",   Width:120,  SaveName:"writDtm",  	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
                    {Type:"Text",   Width:50,   SaveName:"writUserId",  	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:80,   SaveName:"writUserNm",  Align:"Left", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"reaTblCnt",  	Align:"Center", Edit:0},
                    {Type:"Text",   Width:60,   SaveName:"noStdTblCnt",  Align:"Center", Edit:0},
                    {Type:"Text",   Width:120,  SaveName:"fullPath",  Align:"Left", Edit:0, Hidden:0}
                    
                ];
                    
        InitColumns(cols);

	     //콤보 목록 설정...
	     SetColProperty("sysAreaId", 	${codeMap.sysareaibs});
	     SetColProperty("stdAplYn", 	{ComboCode:"N|Y", 	ComboText:"<s:message code='COMBO.NO.YES'/>"}); /* 아니요|예 */
	     SetColProperty("lecyDcd", ${codeMap.lecyDcdibs});
	     SetColProperty("regTypCd", 	{ComboCode:"C|U|D", ComboText:"<s:message code='NEW.CHG.DEL' />"});/* 신규|변경|삭제 */
        
        InitComboNoMatchText(1, "");
        
        
      
        // FitColWidth();
        
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
        	grid_sheet.DoSearch("<c:url value="/meta/stts/getMetaSubjList.do" />", param);
//         	grid_sheet.DoSearchScript("testJsonlist");
        	break;
       
        case "Down2Excel":  //엑셀내려받기
            grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
            break;

    }       
}
 
 

function barchartDrow()	{
	$.getJSON('<c:url value="/meta/stts/stndsttsgrph.do"/>', function(data){
		if(data ==  null) return;
		
		var subjName = new Array();
		
		//데이터 길이 확인
		var cnt2 = data.length;
		for(i=0;i<cnt2;i++) {
			subjName[i] =data[i].subjNm;
		}
	
		
		
		testChart.SetOptions({
			Chart : {
				BackgroundColor : "#FFFFFF", //차트 배경색 설정
				Type : "column", //차트 Type 설정
				BorderColor : "#FFFFFF"
			},
			Legend : {
				Layout : "horizontal", //Legend 모양 설정
				Align : "center", //Legend 가로 정렬 설정
				VerticalAlign : "bottom" //Legend 세로 정렬 설정
			},
			
			Colors : ["#AAEE6A","#F0E150","#5DA0A9","#75738B","#7AAAEE","#F06F3E"],
			
			PlotOptions : {
				Series : {
					DataLabels : { //시리즈의 데이터 레이블 설정
						Enabled : false,
					}
				},
				Column : {
					PointPadding : 0.1 // 컬럼간의 간격 설정
				},
			},
			XAxis : {
			    Categories:subjName,  
				TickInterval : 1, //X축 레이블 간격 설정
				Labels : { //X축 레이블 설정
					Enabled : true
				}
				
			},
			YAxis : {
				TickInterval : 20, //Y축 레이블 간격 설정
				Min : 0, //Y축 Min값 설정
				Max : 200, //Y축 Max값 설정
				Title : { //Y축 제목 설정
					Text : ""
				}
			}
		});
		testChart.SetLegendOptions({
			   Enabled:false
		});
		
		testChart.RemoveAll();
		
		//시리즈 3개 생성
// 		var seriesTot 	= testChart.CreateSeries();		
		var seriesStnd 	= testChart.CreateSeries();		
		var seriesNstnd = testChart.CreateSeries();
		
		
		//각 시리즈별 이름과 타입 설정
// 		seriesTot.SetOptions({
// 			//Name:"전체",
// 			Name:"<s:message code='TBL.CNT' />", /* 테이블수 */
// 			Type:"column"
// 		});		
  	seriesStnd.SetOptions({
			Name:"표준",
			Type:"column"
		});		
		seriesNstnd.SetOptions({
			Name:"비표준",
			Type:"column"
		});	

		//시리즈별 데이터 생성
// 		var totpoints = new Array();
		var stndpoints = new Array();
		var nstndpoints = new Array();
		
		//데이터 길이 확인
		var cnt = data.length;
		for(i=0;i<cnt;i++) {
// 			totpoints.push({X:i, Y:data[i].cnt, Name:data[i].subjNm});
			stndpoints.push({X:i, Y:data[i].cntY, Name:data[i].subjNm});
			nstndpoints.push({X:i, Y:data[i].cntN, Name:data[i].subjNm});
		}

// 		seriesTot.AddPoints( totpoints );
		seriesStnd.AddPoints( stndpoints );
		seriesNstnd.AddPoints( nstndpoints );
		
// 		testChart.AddSeries(seriesTot);
		testChart.AddSeries(seriesStnd);
		testChart.AddSeries(seriesNstnd);
		
		testChart.Draw();
	});
}

// function grid_sheet_OnDblClick(row, col, value, cellx, celly) {
    
// 	if(row < 1) return;
	
// 	// 더블클릭으로 해당 OBJ_ID에 대한 데이터를 검색하여 해당 페이지에서 표시한다.
// 	var subjLnm = grid_sheet.GetCellValue(row, "subjLnm");	
	
// 	window.open().location.href = "pdmtbl_lst.do?subjLnm="+encodeURIComponent(subjLnm);
	
	
// }

function grid_sheet_OnClick(row, col, value, cellx, celly) {
    
    $("#hdnRow").val(row);
    
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="SUBJ.TRRT.INQ" /></div> <!-- 주제영역 조회 -->
	</div>
</div>
<!-- 메뉴 메인 제목 -->
<div style="clear:both; height:5px;"><span></span></div>

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
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
                       <tr>                               
                           <th scope="row" class="th_require"><label for="sysAreaId"><s:message code="SYS.TRRT" /></label></th> <!-- 시스템영역 -->
                            <td>
                                <select id="sysAreaId" class="" name="sysAreaId">
                                        <option value=""><s:message code="WHL" /></option> <!-- 전체 -->
                                 </select>
                            </td>
                           <th scope="row"><label for="subjLnm"><s:message code="SUBJ.TRRT.NM" /></label></th> <!-- 주제영역명 -->
                            <td>
                                <span class="input_file">
                                <input type="text" name="subjLnm" id="subjLnm" class="wd200" />
                                </span>
                            </td>
                       </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        <div class="tb_comment"><s:message  code='ETC.COMM2' /></div>
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
		<tiles:insertTemplate template="/WEB-INF/decorators/buttonSearch.jsp" />
</div>
     <div class="">
<%--  		<div class="nm_tit"><s:message code="SUBJ.TRRT.TBL.CNT" /></div> <!-- 주제영역별 테이블수 --> --%>
         <div class="">
         	<script type="text/javascript">
			createIBChart("testChart","100%", "200px");
		</script>
         </div>
     </div>
            
            
     <div style="clear:both; height:5px;"><span></span></div>
     <br/>
     <br/>
        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "400px");</script>            
<%-- 	     <script type="text/javascript">createIBSheet2($("#grid_01"), "grid_sheet", "100%", "100%");</script>             --%>
	</div>
	<!-- 그리드 입력 입력 -->

<div style="clear:both; height:5px;"><span></span></div>

	<!-- 그리드 하단 영역 : 레코드 선택시 내용 표시 및 수정 가능하도록 -->
	<div class="selected_title_area" style="display: none;">
		    <div class="selected_title"><s:message code="SUBJ.TRRT.NM" /> : <span></span></div> <!-- 주제영역명 -->
	</div>
</div>

</body>
</html>