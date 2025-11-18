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
<title><s:message code="PEDC.LORQ.TRTT.PRES"/></title> <!-- 기간별 요청서 처리현황 -->

<!-- ibchart.js  -->
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchart.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/IBChart/ibchartinfo.js"/>'></script>
<script type="text/javascript">



$(document).ready(function() {

		//마우스 오버 이미지 초기화
// 		//imgConvert($('div.tab_navi a img'));
      
//      $("#btnSearch").hide();
        $("#btnTreeNew").hide();
        $("#btnSave").hide();
        $("#btnDelete").hide();
//      $("#btnExcelDown").hide();
      
        // 조회 Event Bind
        $("#btnSearch").click(function(){ doAction("Search");  });
                
        // 엑셀내리기 Event Bind
        $("#btnExcelDown").click( function(){ doAction("Down2Excel"); } );
        
//         create_selectbox(bizdtlcdJson, $("#bizDtlCd"));
        $( "#tabsStwd" ).hide();
        $( "#tabsDmn" ).hide();
        $( "#tabsSditm" ).hide();
	
        
      //달력팝업 추가...
	 	$( "#searchBgnDe" ).datepicker();
		$( "#searchEndDe" ).datepicker();
    
		//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	//	setautoComplete($("#frmSearch #dmnLnm"), "DMN");
	
});

//엔터키 처리한다.
EnterkeyProcess("Search");

$(window).on('load',function() {
	initGrid();
	//$( "#layer_div" ).show();
});



function chartDrow()	{

	var categories = new Array();
	//카테고리 설정
	for(var i=0; i<= grid_sheet.SearchRows(); i++) {
		categories.push (grid_sheet.GetCellText(i+1, "bizDcd"));
	}
	
	rqstChart.SetOptions({
		Chart : {
			BackgroundColor : "#ffffff", //차트 배경색 설정
			Type : "column" ,           //차트 Type 설정
			BorderColor : "#FFFFFF" 

		},
		
		Legend : {
			Layout : "vertical", //Legend 모양 설정
			Align : "right", //Legend 가로 정렬 설정
			VerticalAlign : "center" //Legend 세로 정렬 설정
		},
		
		PlotOptions : {
			Series : {
				DataLabels : { //시리즈의 데이터 레이블 설정
					Enabled : true,
				}
			},
			Column : {
				PointPadding : 0 // 컬럼간의 간격 설정
			   ,PointWidth : 20 //포인트의 넓이를 설정한다.
			},
		},
		XAxis : {
			Categories : categories,
			TickInterval : 1, //X축 레이블 간격 설정
			Labels : { //X축 레이블 설정
				Enabled : true,
				Align : "center"
			},
			Title:{ //X축 제목 설정
				Text : "<s:message code='BZWR.DSTC' />" //업무구분
			}

		},
		YAxis : {
			TickInterval : 50, //Y축 레이블 간격 설정
			Min : 0, //Y축 Min값 설정
			Title : { //Y축 제목 설정
				Text : "<s:message code='CCNT'/>" //건수
			}
		}
	});


	//시리즈 생성
	var series = new Array();
	var bizDcd = "";
	for(var i=2; i<grid_sheet.SearchRows()+2; i++){
		series[i-2] = rqstChart.CreateSeries();
		//각 시리즈별 이름과 타입 설정
		bizDcd = grid_sheet.GetCellText(i, "bizDcd");
		series[i-2].SetOptions({
			Name:bizDcd,
			Type:"column"
		});	
	}
	//시리즈별 데이터 생성
	var points = new Array();
	var name = "총합계";
	for(var i=2, j=0; i<grid_sheet.SearchRows()+2; i++, j++){
		 points.push({X:i-1, Y:grid_sheet.GetCellValue(i,"rqstTot"),Name:name });
		 //각각 시리즈의 데이터를 담아 시리즈를 등록
		 series[j].AddPoints(points);
		 rqstChart.AddSeries(series[j]);
		 points.pop();
	} 
	 
	rqstChart.Draw();
};






function initGrid()
{
    
    with(grid_sheet){
    	
    	var cfg = {SearchMode:2,Page:100,SizeMode:0};
        SetConfig(cfg);
        
        SetMergeSheet(msHeaderOnly);
		var headers = [
						{Text:"<s:message code='META.HEADER.PERIODRQST.LST.1'/>"}
					,{Text:"<s:message code='META.HEADER.PERIODRQST.LST.2'/>", Align:"Center"}
						]
					;
					//구분|총합계|요청서 처리상태|요청서 처리상태
					//구분|총합계|반려건수|승인건수
			
			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:1};
			
			InitHeaders(headers, headerInfo); 

			var cols = [						
						{Type:"Combo",   Width:300,  SaveName:"bizDcd",		Align:"Left", Edit:0},
						{Type:"Text",   Width:300,  SaveName:"rqstTot",		Align:"Right", Edit:0},
						{Type:"Text",   Width:300,  SaveName:"rjctCnt",		Align:"Right", Edit:0},
						{Type:"Text",   Width:300,  SaveName:"aprvCnt",		Align:"Right", Edit:0}
					];
                    
        InitColumns(cols);
        
     //   SetColProperty("bizDtlCd", 	${codeMap.bizdtlcdibs});
        
        //SetColHidden("rqstUserNm",1);
        
        //업무구분을 한글명칭으로
        SetColProperty("bizDcd", ${codeMap.bizDcdibs});

//         FitColWidth();  
        
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
    	grid_sheet.DoSearch("<c:url value="/meta/report/getPeriodRqst.do" />", param);
		break;
	case "Down2Excel": //엑셀내려받기
		grid_sheet.Down2Excel({HiddenColumn:1, Merge:1});
		break;
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
	}
	//차트데이터 생성, .DoSearch()로 조회가 되었을때 실행
	chartDrow();
}


</script>


</head>

<body>

<div id="layer_div" >
<!-- 메뉴 메인 제목 -->
<div class="menu_subject">
	<div class="tab">
	    <div class="menu_title"><s:message code="PEDC.LORQ.TRTT.PRES"/></div> <!-- 기간별 요청서 처리현황 -->
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
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='DMN.GRP' />"> <!-- 도메인그룹 -->
                   <caption><s:message code="LORQ.INQ.FORM"/></caption> <!-- 요청서 검색폼 -->
                   <colgroup>
                   <col style="width:10%;" />
                   <col style="width:25%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   <col style="width:10%;" />
                   <col style="width:20%;" />
                   </colgroup>
                   
                   <tbody>                            
                            <tr>
                                <th scope="row"><label for="dtmDs"><s:message code="QT.DSTC"/></label></th> <!-- 분기구분 -->
                                <td>
                                <select id="dtmDs" class="wd100" name="dtmDs">
									<option value=""><s:message code="WHL" /></option> <!-- 전체 -->
									<option value='1'><s:message code="QT1"/></option> <!-- 1분기 -->
				  					<option value='2'><s:message code="QT2"/></option> <!-- 2분기 -->
				  					<option value='3'><s:message code="QT3"/></option> <!-- 3분기 -->
				  					<option value='4'><s:message code="QT4"/></option> <!-- 4분기 -->
								</select>
								</td>
                                <th scope="row"><label for="searchBgnDe"><s:message code="TERM" /></label></th> <!-- 기간 -->
                                <td><input id="searchBgnDe" name="searchBgnDe" type="text" class="wd80"/>
                              	- <input id="searchEndDe" name="searchEndDe" type="text" class="wd80"/></td>
                            </tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div>
        <!-- 클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고 -->
        <!-- 를 사용하시면 됩니다. -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
         <!-- 버튼영역(데이터패턴, 로그, SQL분석)  -->
<div style="clear:both; height:5px;"><span></span></div>
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonMain.jsp" />
<div style="clear:both; height:5px;"><span></span></div>

</div>

<!-- 그리드 입력 입력 -->
<div class="grid_01">
     <script type="text/javascript">createIBSheet("grid_sheet", "100%", "250px");</script>            
</div>
<!-- 그리드 입력 입력 End -->


<div style="clear:both; height:5px;"><span></span></div>

<!--ibchart 생성  -->
<div style="clear:both; height:5px;"><span></span></div>
	
	<div class="main_chart_01"  >
	    <script type="text/javascript">
	    	createIBChart("rqstChart", "100%", "350px");
	    </script>
	</div>
</div>


</body>
</html>