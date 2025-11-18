<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>


<script type="text/javascript">
var interval = "";
var refParam;
var otlDtcIdVal = "";

$(document).ready(function() {
// 	alert('${search.rqstNo}');
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code='VALID.PRFREQUIRED' />";
	//폼검증

	//조회버튼 숨기기...
	$("#btnSearch").hide();
	$("#btnPdfDown").hide();
	
	//저장버튼 
	$("#btnPrfSave").click(function(){ 
		//이상값 탐지 저장...
		doAction("SaveOutlierDection"); 
	}).show();
	
	$("#btnRefresh").click(function() {
    	doAction("SearchOutRes", refParam);
    }).show();
  	
	//분석실행
	$("#btnExec").click(function(){ 
		//스케줄 등록
// 		regScheduler();	
		doAction("regScheduler");
	});
	
	//데이터패턴
	$("#btnPattenSearch").click(function(event){ 

		event.preventDefault(); 
		
		doAction("getDataPattern");
// 		getDataPattern();
	
	});
	
	//로그조회
	$("#btnLogSearch").click(function(){ 
		doAction("getAnaLog");
// 		 getAnaLog();
		
	});
	
	//분석SQL 보기
	$("#btnSqlSearch").click(function(){ 
// 		SearchSql();
	}).hide();
	
	//일괄등록 버튼 Hide...
    $("#btnPrfSchRqst").click(function(){
    	doAction("PrfSchRqst");
    }).hide();	
	
	//삭제
	$("#btnPrfDelete").click(function(){
		//삭제할 이상값 탐지 확인...
		var row = grid_outlier.GetSelectRow();
       	if (row < 1) {
       		showMsgBox("ERR", "<s:message code='ERR.OUTLIERPRF.LST' />");

       		/* 기존소스 181017 */
       		/* showMsgBox("ERR", "삭제할 이상값 탐지 프로파일이 없습니다.<br>이상값 프로파일 목록에서 선택 후 실행하십시요."); */
			return;
       	}
       	
       	showMsgBox("CNF", "<s:message code='CNF.DEL' />", 'DelOutlierDection');
		
	});
	
	//엑셀내리기
	$("#btnExcelDown").click(function(){ }).hide();
	
    // 조회 Event Bind
    $("#btnAnaTrgSearch").click(function(){ doAction("SearchAnaTrgTbl");  });
    
    // 품질지표 일괄 적용
    $("#btnApply").click(function() {
    	var url = "<c:url value='/dq/criinfo/dqi/popup/dqi_pop.do' />";
    	var param = "sflag=PRFOUT";
    		param += "&dqiIds="+$("#frmInput #dqiId").val();
    	var popup = OpenWindow(url+"?"+param,"dqiSearch","800","600","yes");
    });
    
    
    //알고리즘 변경시 이벤트처리 : 알고리즘에 해당하는 변수목록을 가져와서 셋팅한다.
    $("#frmInput #otlAlgId").change(function(){
    	//알고리즘 변수 호출 
    	var param = "algId="+ $(this).val();
    	$('div#alg_arg_input').load('<c:url value="/advisor/prepare/outlier/getoutlierparamlist.do"/>', param, function(){});
    });
    
//     $("#frmSearch #schDbConnTrgId").find("option").remove().end();
	var val = $("#schDbConnTrgId option:selected").val();
	var trgId = ${codeMap.schDbSchId};

// 	$("#frmSearch #schDbSchId").append('<option value="">선택</option>');
	
	for(i=0; i<trgId.length; i++) {
		if(trgId[i].upcodeCd == val || trgId[i].upcodeCd == null) {
			$("#frmSearch #schDbConnTrgId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
		}
	}
	
	
	$("#frmSearch #schDbConnTrgId").change(function() {
		$("#frmSearch #schDbSchId").find("option").remove().end();
		var val = $("#schDbConnTrgId option:selected").val();
		var trgId = ${codeMap.schDbSchId};

		$("#frmSearch #schDbSchId").append('<option value="">전체</option>');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == val) {
				$("#frmSearch #schDbSchId").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
	});

	/*
	$("#frmSearch #schDbSchId").change(function() {
		var url = "<c:url value='/advisor/prepare/textcluster/getTblList.do' />";
		
		var param = new Object();
		param.srcDbConnTrgId = $("#schDbConnTrgId option:selected").val();
		param.srcDbcSchNm = $("#schDbSchId option:selected").val();
		
		$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: param,
			dataType: 'json',
			success: function (data) {
				$("#frmSearch #schDbcTblNm").find("option").remove().end();
				
				$("#frmSearch #schDbcTblNm").append('<option value=""></option>');
				
				for(i=0; i<data.length; i++) {
					$("#frmSearch #schDbcTblNm").append('<option value="' + data[i].srcDbcTblNm + '">' + data[i].srcDbcTblNm + '</option>');
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
							
			}
		});
	});
	*/

	$("#btnReset").click(function(){
		otlDtcIdVal = "";
		resetFunc();
	});
	
	$("#btnReset").show();
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	//setautoComplete($("#frmSearch #schDbSchNm"), "DBSCH");
	//setautoComplete($("#frmSearch #schDbcTblNm"), "DBCTBL");
// 	setautoComplete($("#frmSearch #schDbcColNm"), "DBCCOL");
});



$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	//그리드 높이 초기화
	setibsheight($("#grid_01"));
// 	setibsheight($("#grid_02"));
	
	<c:if test="${search.rqstNo != null and search.rqstNo != '' }">
	doAction("SearchAnaTrgTbl", "${search.rqstNo}");
	
	</c:if>
	
	$('div#alg_arg_input').load('<c:url value="/advisor/prepare/outlier/getoutlierparamlist.do"/>', function(){});
	//그리드 사이즈 조절 초기화...		
//  	bindibsresize();
});

$(window).resize(function() {
		//div size 정의
// 		divSize();
});

EnterkeyProcess("SearchAnaTrgTbl");
	
function initGrid()
{
   
	//진단대상 테이블 grid
    with(grid_tbl){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='BDQ.HEADER.TEXTCLUST.RQST' />", Align:"Center"}
                    //No|상태|선택|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|
                    //테이블한글명|데이터셋ID|데이터셋(논리명)|데이터셋(물리명)|summary여부|메타연계분석
                    
                    /* 기존소스.bak 181017 */
                    /* {Text:"No|상태|선택|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|테이블한글명|데이터셋ID|데이터셋(논리명)|데이터셋(물리명)|summary여부|메타연계분석", Align:"Center"} */
                ];

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"dbSchPnm",    	Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"daseId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"daseLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dasePnm",    	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"summaryYn"  , Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"metaAsscAnly"  , Align:"Left", Edit:0, Hidden:1}, 
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
// 	    SetColProperty("regTypCd", 	${codeMap.regTypCdibs});

        
//         InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    //==시트설정 후 아래에 와야함===
	init_sheet(grid_tbl);    
    //===========================	

    //이상값 결과 grid
    with(grid_outlier){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='BDQ.HEADER.OUTLIERPRF.LST' />", Align:"Center"}
                    //No|상태|이상값탐지ID|데이터셋ID|이상값알고리즘ID|알고리즘유형|프로파일명|알고리즘(논리명)|알고리즘(물리명)|분석시작일시|분석종료일시|분석시간|분석변수ID|
                    //분석차수|분석건수|추정오류건수|추정오류율(%)|분석로그ID|분석사용자ID|분석자|품질지표|품질지표ID
                    
                    
                    
                    /* 기존소스_bak 181017 */
                    /* {Text:"No|상태|이상값탐지ID|데이터셋ID|이상값알고리즘ID|알고리즘유형|프로파일명|알고리즘(논리명)|알고리즘(물리명)|분석시작일시|분석종료일시|분석시간|분석변수ID|분석차수|분석건수|추정오류건수|추정오류율(%)|분석로그ID|분석사용자ID|분석자|품질지표|품질지표ID", Align:"Center"} */
                ];
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"otlDtcId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"daseId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"otlAlgId",    Align:"Left", Edit:0, Hidden:1},
                    {Type:"Combo",  Width:100,  SaveName:"algTypCd",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:150,  SaveName:"otlNm",    	Align:"Left",   Edit:0, Hidden:0},
                    {Type:"Text",   Width:150,  SaveName:"algLnm",    	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:150,  SaveName:"algPnm",    	Align:"Center", Edit:0, Hidden:1},
                      
                    {Type:"Text",   Width:100,  SaveName:"anaStrDtm",   Align:"Center",Format:"yyyy-MM-dd HH:mm:ss",  Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:100,  SaveName:"anaEndDtm",   Align:"Center",Format:"yyyy-MM-dd HH:mm:ss",  Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"anaTime",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"anlVarId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"anaDgr",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Float",  Width:100,  SaveName:"anaCnt",    	Align:"Center", Format:"#,##0", Edit:0}, 
                    {Type:"Float",  Width:100,  SaveName:"esnErCnt",    Align:"Center", Format:"#,##0", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"esnErPer",    Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"anaLogId",    Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"anaUserId",   Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"rqstUserNm",  Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"dqiLnm",  	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"dqiId",  		Align:"Center", Edit:0, Hidden:1},
                ];
        
        //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
        InitColumns(cols);

	     //콤보 목록 설정...
	    SetColProperty("algTypCd", 	${codeMap.algTypCdibs});

        
        InitComboNoMatchText(1, "");
        
        FitColWidth();
        //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
        SetExtendLastCol(1);    
    }
    //==시트설정 후 아래에 와야함===
	init_sheet(grid_outlier);    
    //===========================	
    
	//진단대상 컬럼 grid
   	with(grid_col){
   		
   		var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);

           var headText = "";

           headText += "<s:message code='BDQ.HEADER.OUTLIERPRF.LST2' />";
           //선택|Pos|상태|타겟변수여부|변수ID|VAR_SNO|프로파일ID|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|컬럼명|컬럼한글명
           headText += "<s:message code='BDQ.HEADER.OUTLIERPRF.LST3' />";
           //|Pk여부|데이터타입|변수타입|히스토그램여부|추천도메인|추천도메인확률|최종도메인|추가조건|설명|Null여부|Default|메타연계분석|최소값|최대값|조건|연산자|값
           
           /* 기존소스_bak 181017 */
           /* headText += "선택|Pos|상태|타겟변수여부|변수ID|VAR_SNO|프로파일ID|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|컬럼명|컬럼한글명";
           headText += "|Pk여부|데이터타입|변수타입|히스토그램여부|추천도메인|추천도메인확률|최종도메인|추가조건|설명|Null여부|Default|메타연계분석|최소값|최대값|조건|연산자|값"; */
           
           var headers = [
                       {Text: headText, Align:"Center"}
                   ];
           
           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                   {Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
                   {Type:"Int",    Width:40,   SaveName:"ord",    	Align:"Center", Edit:0},
                   {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"tgtVarYn",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"anlVarId",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"varSno",       Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"prfId",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",  Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm", Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1},                    
                   {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:120,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
                   {Type:"Text",   Width:120,  SaveName:"dbcColKorNm",  Align:"Left", Edit:0, Hidden:0},
                   {Type:"Text",   Width:70,   SaveName:"pkYn",       	Align:"Center", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"dataType",    	Align:"Center", Edit:0},
                   {Type:"Text",   Width:80,  SaveName:"varType",    	Align:"Center", Edit:0, Hidden:0},                   
                   {Type:"Text",   Width:100,  SaveName:"histoYn",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"dmnPdt",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Float",  Width:100,  SaveName:"dmnPrb",    	Align:"Center", Edit:0, Hidden:1, Format:"#0.0"},
                   {Type:"Combo",  Width:100,  SaveName:"dmngNm",    	Align:"Center", Edit:0, Hidden:0},
                   {Type:"Text",   Width:300,  SaveName:"otlAddCnd",    Align:"Left", Edit:1, Hidden:1},//추가조건
                   {Type:"Text",   Width:80,   SaveName:"objDesc",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"nullYn",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"defltVal",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"metaAsscAnly", Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"dmnMinVal",    Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"dmnMaxVal",    Align:"Left", Edit:0, Hidden:1},
                   {Type:"Combo",  Width:50,  SaveName:"condCd",    	Align:"Center", Edit:1, Hidden:0},//조건
                   {Type:"Combo",  Width:50,  SaveName:"oprCd",    	Align:"Center", Edit:1, Hidden:0},//연산자
                   {Type:"Text",   Width:80,  SaveName:"oprVal",    	Align:"Center", Edit:1, Hidden:0}//값
                   //{Type:"Text",  Width:80,  SaveName:"dqiId",    	Align:"Center", Edit:1, Hidden:1},//품질지표
                   //{Type:"Popup",  Width:50,  SaveName:"dqiLnm",    	Align:"Center", Edit:1, Hidden:0}//품질지표
                   
               ];
       
       //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
       InitColumns(cols);
       //콤보 목록 설정...
	   SetColProperty("dmngNm", {ComboText: "<s:message code='BDQ.HEADER.TEXTCLUST.RQST3' />", ComboCode: "<s:message code='BDQ.HEADER.TEXTCLUST.RQST3' />"});
       
		/* 기존소스_bak 181017 */       
	   /* SetColProperty("dmngNm", {ComboText: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그', ComboCode: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'}); */
	   
// 	     'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'
	   SetColProperty("oprCd", {ComboCode:"|>=|>|=|!=|<|<=|between", ComboText:"|>=|>|=|!=|<|<=|between"});
	   SetColProperty("condCd", {ComboCode:"|AND|OR", ComboText:"|AND|OR"});

	 	//품질지표 콤보 목록 설정...
	    //SetColProperty("dqiPop", ${codeMap.dqiPop});
	   
       InitComboNoMatchText(1, "");
       
       //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
       
       FitColWidth();
       SetExtendLastCol(1); 
   
   	}
	
    //===시트설정 후 아래에 와야함===
    init_sheet(grid_col);    
    //===========================	
}	

function doAction(sAction, aparam)
{
    switch(sAction)
    {
       	/*진단대상 테이블 조회*/
        case "SearchAnaTrgTbl":
	        var	param = "";
        	if (isBlankStr(aparam)) {
	        	if (isBlankStr( $("#frmSearch #schDbConnTrgId").val())) {
	        		showMsgBox("ERR", "<s:message code="MSG.DB.MS.INFO.CHC" />"); //DBMS정보를 선택해주세요.
	        		return;
	        	}
	//         	param += "&tblColGb=PC";
	        	param += "&"+$('form[name=frmSearch]').serialize();
        	} else {
	        	param = "rqstNo="+aparam;
        	}
        	
//         	grid_tbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getPrfTblLst.do" />", param);
        	grid_tbl.DoSearch("<c:url value="/advisor/prepare/dataset/getDataSetList.do" />", param);
        	break;
        	
       	/*진단대상 컬럼 조회*/
        case "SearchAnaTrgCol":
        	grid_col.DoSearch("<c:url value="/advisor/prepare/dataset/getAanVarList.do" />", aparam);
        	break;
       	/*이상값탐지 대상 컬럼 조회*/
        case "SearchAlgVal":
//         	grid_col.DoSearch("<c:url value="/advisor/prepare/outlier/getAlgVarList.do" />", aparam);
        	grid_col.DoSearch("<c:url value="/advisor/prepare/outlier/getOtlVarList.do" />", aparam);
        	break;
       	/*이상값탐지 단변량 결과 데이터 조회*/
        case "SearchOutData":
        	grid_outdata.DoSearch("<c:url value="/advisor/prepare/outlier/getOutData.do" />", aparam);
        	break;
       	/*이상값탐지 다변량 결과 데이터 조회*/
        case "SearchOutData2":
        	grid_outdata.DoSearch("<c:url value="/advisor/prepare/outlier/getOutData2.do" />", aparam);
        	break;
		//이상값 탐지 결과 조회...
        case "SearchOutRes":
        	refParam = aparam;
        	grid_outlier.DoSearch("<c:url value="/advisor/prepare/outlier/getoutlierbyds.do" />", aparam);
        	break;
		//이상값 탐지 결과 차트 생성 ...
        case "CreateOutChart":
			
//         	grid_outlier.DoSearch("<c:url value="/advisor/prepare/outlier/getoutlierbyds.do" />", aparam);
        	break;
        
        case "SaveOutlierDection":
        	
        	var algtxt = $("#frmInput #otlAlgId option:selected").text();
        	//데이터셋 선택 필수...
        	if (isBlankStr($("#frmInput #daseId").val())) {
        		showMsgBox("INF", "<s:message code='INF.TEXTMATCHRQST2' />");
        		
        		/* 기존소스_bak 181017 */
        		/* showMsgBox("INF", "선택된 데이터셋이 없습니다.<br>데이터셋을 선택 후 실행하십시요."); */
        		return;
        	}
        	//알고리즘 선택 필수...
        	if (isBlankStr($("#frmInput #otlAlgId").val())) {
        		showMsgBox("INF", "<s:message code='INF.TEXTMATCHRQST' />");
        		
        		/* 기존소스_bak 181017 */
        		/* showMsgBox("INF", "이상값 탐지 알고리즘이 없습니다.<br>알고리즘을 선택 후 실행하십시요."); */
        		return;
        	}
        	
        	var SaveJson = grid_col.GetSaveJson(0);
	    	//2. 처리대상 행이 없는 경우 리턴한다.

			if (SaveJson.Code == "IBS000") {
// 				showMsgBox("INF", "이상값 탐지대상이 없습니다.<br>변수목록에서 대상을 체크 후 실행하십시요.") ; 
// 				return; 	// 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
			}
							// 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
							// Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
							
			var sRow = grid_col.FindCheckedRow("ibsCheck");

			//alert(sRow.split("\|").length);

			var chkCnt = sRow.split("\|").length;
	    	
			if(algtxt == "Robust Regression" && chkCnt != 2) {
				showMsgBox("INF", "<s:message code='INF.OUTLIERPRF.LST' />");
				
				/* 기존소스_bak 181017 */
				/* showMsgBox("INF", "이변량 이상값 탐지의 경우 변수를 2개만 선택하셔야 합니다.<br>변수목록에서 2개 체크 후 실행하십시요."); */
				return;
			}
			
			if(algtxt != "Box Plot" && chkCnt < 2) {
	    		showMsgBox("INF", "<s:message code='INF.TEXTMATCHRQST4' />");
	    		
	    		/* 기존소스_bak 181017 */
	    		/* showMsgBox("INF", "다변량 이상값 탐지의 경우 변수를 최소 2개이상 선택하셔야 합니다.<br>변수목록에서 2개이상 체크 후 실행하십시요."); */
				return;
	    	}
			
			for(i=1; i<=grid_col.GetDataLastRow(); i++) {
				/* if(grid_col.GetCellValue(i, 'ibsCheck') == '0') {
					grid_col.SetCellValue(i, 'condCd', '');
					grid_col.SetCellValue(i, 'oprCd', '');
					grid_col.SetCellValue(i, 'oprVal', '');
				} */
				
				if(grid_col.GetCellValue(i, 'ibsCheck') == '0') {
					grid_col.SetCellValue(i, 'dqiId', '');
				}
				
				if(grid_col.GetCellValue(i, 'ibsCheck') == '1'
						&& grid_col.GetCellValue(i, 'oprCd') == 'between'
							&& grid_col.GetCellValue(i, 'oprVal') == '') {
					showMsgBox("INF", "<s:message code='INF.OUTLIERPRF.LST2' />");
					
					/* 기존소스_bak 181017 */
					/* showMsgBox("INF", "값을 입력해주세요.(A AND B)"); */
					return;
				}
				
				if(grid_col.GetCellValue(i, 'ibsCheck') == '1'
					&& grid_col.GetCellValue(i, 'condCd') != ''
						&& grid_col.GetCellValue(i, 'oprCd') == '') {
					showMsgBox("INF", "<s:message code='INF.OUTLIERPRF.LST3' />");
					
					/* 기존소스_bak 181017 */
					/* showMsgBox("INF", "연산자를 선택해주세요."); */
					return;
				}
				
				if(grid_col.GetCellValue(i, 'ibsCheck') == '1'
					&& grid_col.GetCellValue(i, 'condCd') != ''
						&& grid_col.GetCellValue(i, 'oprCd') != ''
							&& grid_col.GetCellValue(i, 'oprVal') == '') {
					showMsgBox("INF", "<s:message code='INF.OUTLIERPRF.LST4' />");
					
					/* 기존소스_bak 181017 */
					/* showMsgBox("INF", "값을 입력해주세요."); */
					return;
				}

				/* if(grid_col.GetCellValue(i, 'ibsCheck') == '1'
						&& (grid_col.GetCellValue(i, 'dqiId') == null
						|| grid_col.GetCellValue(i, 'dqiId') == '')) {
					showMsgBox("INF", "품질지표를 선택해주세요.");
					return;
				} */
				
			}
			
			if($("#frmInput #dqiId") == '' || $("#frmInput #dqiLnm") == '' ) {
				showMsgBox("INF", "<s:message code='INF.OUTLIERPRF.LST5' />");
				
				/* 기존소스_bak 181017 */
				/* showMsgBox("INF", "품질지표를 선택해주세요."); */
				return;
			}
			
			//TODO : 체크박스 중에 NUMBER가 아닌 경우 확인 필요...
			SaveJson = grid_col.GetSaveJson({AllSave:1});
			
			var url = "<c:url value="/advisor/prepare/outlier/regoutlier.do"/>";

			var param = $("#frmInput").serialize();
			
			if($("#frmInput #resetFlag").val() == "Y") {

				param += "&otlDtcId=";
			}else{
				
				var row = grid_outlier.GetSelectRow();
				
				param += "&otlDtcId=" + grid_outlier.GetCellValue(row, "otlDtcId");
			}
			
			param += "&dqiId=" + $("#frmInput #dqiId").val();
			
			IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
	      
        case "DelOutlierDection":

        	var row = grid_outlier.GetSelectRow();
        	
        	var url = "<c:url value="/advisor/prepare/outlier/deloutlier.do"/>";
 			//var param = $("#frmInput").serialize();
			var param = "otlDtcId=" +grid_outlier.GetCellValue(row, "otlDtcId");

			IBSpostJson2(url, null, param, ibscallback);
        	
        	break;
        	
        case "DmnPredictResult":
        	//도메인 판별 결과 조회 화면으로 이동...
        	var url = "<c:url value="/advisor/prepare/domain/dmnpredict_lst.do"/>";
			var param = "rqstNo="+$("#frmDataSet #rqstNo").val(); //$("#mstFrm").serialize(); 
        	location.href =  url+"?"+param;
        	
        	break;
        case "DtlReset":
        	//이상값 탐지 상세 정보 리셋...
        	$("#frmInput #otlAlgId").val('');
			$("#frmInput #otlAlgId").change();
    	    break;
        
        	
        case "Down2Excel":  //엑셀내려받기
            grid_tbl.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "PrfSchRqst":
        	var url   = "<c:url value="/dq/profile/popup/prfschrqst_pop.do"/>";
        	var popup = OpenWindow(url+param,"SQL","1200","750","yes");

        	break;
        //실시간실행 스케줄 등록
        case "regScheduler":
        	var row = grid_outlier.GetSelectRow();
           	if (row < 1) {
           		/* 기존소스_bak 181017 */
           		/* showMsgBox("ERR", "이상값 탐지를 실행할 프로파일이 없습니다.<br>이상값 프로파일 목록에서 선택 후 실행하십시요."); */
           		
           		showMsgBox("ERR", "<s:message code='ERR.OUTLIERPRF.LST2' />");
    			return;
           	}
           	
			var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
			var param = "&shdKndCd=PY"; //이상값프로파일...
		    
			var temp = {'shdJobId': grid_outlier.GetCellValue(row, "otlDtcId")
						, 'shdJobNm' : '[이상값프로파일-'+grid_outlier.GetCellValue(row, "algLnm")+']'+$("#frmDataSet #dbcTblNm").val()
						, 'etcJobNm' : '[이상값프로파일-'+grid_outlier.GetCellValue(row, "algLnm")+']'+$("#frmDataSet #dbcTblNm").val()
						, 'shdJobKndCd':'OD'};
			
			var tmparr = new Array();
			tmparr.push(temp);
			
			var SaveJson = {"data": tmparr};
			
// 		    ibsSaveJson = getform2IBSjson($("#frmAnaTrg"));
		    IBSpostJson2(urls, SaveJson, param, schedulerCallBack);

        	break;
        //로그조회...
        case "getAnaLog":
        	var row = grid_outlier.GetSelectRow();
           	if (row < 1) {
           		/* 기존소스_bak 181017 */
           		/* showMsgBox("ERR", "로그를 조회할 이상값 프로파일이 없습니다.<br>이상값 프로파일 목록에서 선택 후 실행하십시요."); */
           		
           		showMsgBox("ERR", "<s:message code='ERR.OUTLIERPRF.LST3' />");
    			return;
           	}
           	
           	var param = "?objId="+grid_outlier.GetCellValue(row, "otlDtcId");
    		param += "&objResTbl=WAD_OTL_RESULT";
	        var url = '<c:url value="/dq/report/popup/analog_pop.do" />';
	        var popup = OpenWindow(url+param, "LOG_SEARCH", "800", "600", "yes");

        	break;
        //이상값 조회...
        case "getDataPattern":
        	var row = grid_outlier.GetSelectRow();
           	if (row < 1) {
           		showMsgBox("ERR", "<s:message code="INQ.DATA.SEL" />"); /*조회할 데이터를 선택하십시오.*/
    			return;
           	}
           	var rowjson =  grid_outlier.GetRowJson(row);

           	if(rowjson.anaCnt == ""){ 

           		/* 기존소스_bak 181017 */
           		/* showMsgBox("ERR","분석결과가 없습니다."); */
           		
           		showMsgBox("ERR","<s:message code='ERR.OUTLIERPRF.LST4' />");
				return;
            }

			var algPnm = rowjson.algPnm.substring(0,1);

           	var param = "?daseId="    + rowjson.daseId;
	    	    param += "&otlDtcId=" + rowjson.otlDtcId;
	    	    param += "&otlAlgId=" + rowjson.otlAlgId;
	    	    param += "&algPnm="   + algPnm;  
	    	    param += "&dbSchId="  + $("#frmInput #dbSchId").val();
	    	    param += "&dbSchPnm=" + $("#frmDataSet #dbSchPnm").val();
	    	    
           	    param += "&objId="   + rowjson.otlDtcId;
    			param += "&objDate=" + rowjson.anaStrDtm; 
			
    		
    	    var url = '<c:url value="/advisor/prepare/outlier/popup/outlierdata_pop.do" />';

    	 	var popup = OpenWindow(url+param, "popErrData", "800", "600", "yes"); 

        	break;
    }       
    
}


//스케줄 등록 결과 콜백 함수...
function schedulerCallBack(data){
	//스케줄등록 오류
	if(data.RESULT.CODE < 0){
		showMsgBox("ERR", data.RESULT.MESSAGE); 
	}else{
		showMsgBox("INF", data.RESULT.MESSAGE); 
	}
}


/*======================================================================*/
	//IBSpostJson2 후처리 함수
	/*======================================================================*/
	//IBS 리스트 저장, 단건 저장, 삭제 상태에 따라 후처리 하는 함수...
	function postProcessIBS(res) {
		
		switch(res.action) {
			//요청서 삭제 후처리...
			case "<%=WiseMetaConfig.IBSAction.DEL%>" :
				
				break;
			//요청서 단건 등록 후처리...
			case "<%=WiseMetaConfig.IBSAction.REG%>" :
				
				break;
			//요청서 저장 및 검증
			case "<%=WiseMetaConfig.RqstAction.REGISTER%>" :
					var row = grid_tbl.GetSelectRow();
					
					//그리드 선택 데이터 변수 setting
					var rowjson =  grid_tbl.GetRowJson(row);
					
				  //그리드 선택 데이터 변수 setting
					var param = "&schDbConnTrgId=" + rowjson.dbConnTrgId; 
					    param += "&schDbSchId="    + rowjson.dbSchId;
					    param += "&schDbcTblNm="   + rowjson.dbcTblNm;
					    param += "&daseId="   + rowjson.daseId;
	//			 	    param += "&metaAsscAnly="  + grid_tbl.GetCellValue(row,"metaAsscAnly");
	//			  	    param += "&schDbcColNm="   + $("form[name=frmSearch] input[name='schDbcColNm']").val();
	//			  	    param += "&schRegYn="      + $("form[name=frmSearch] select[name='schRegYn']").val();
				 	    //테이블 컬럼 프로파일 구분
	//			  	    param += "&tblColGb=PC";
				 	
				 	    
				 	    
				 	    //이상값탐지결과 조회...
					var param1 = "daseId="+rowjson.daseId;
					doAction("SearchOutRes", param1);
					
					//이상값 프로파일 상세정보 초기화...
					doAction("DtlReset");
					 //컬럼목록 조회
					doAction("SearchAnaTrgCol", param);
				
				break;
			//요청서 결재단계별 승인 완료 후처리
			case "<%=WiseMetaConfig.RqstAction.APPROVE%>":

				break;
			
			default : 
				// 아무 작업도 하지 않는다...
				break;
				
		}
		
	}


function divSize(){
	 // 진단대상 전체
//   $("#anatrg").attr("style","width:30%;float:left;");
  // 진단대상 조회조건
//   $("#searchTrg_div").attr("style","width:99%;height:100%;float:left;");
  
  // 프로파일 상세 전체
//   $("#colprf").attr("style","width:69%;height:100%;float:left;");
  // 진단대상 컬럼 상세
//   $("#searchAnaTrgDtl_div").attr("style","width:100%;float:right;");
  //프로파일 텝
//   $("#tabs").attr("style","width:99%;float:right;");
  
  //그리드 가로 스크롤 방지
	grid_tbl.FitColWidth();
	
	//grid_col.FitColWidth();
	
// 	grid_prf.FitColWidth();
	
}


function loadColDetail(param){
	ajax2Json('<c:url value="/dq/profile/getAnaTrgColDetail.do"/>', param, setAnaTgtDtl);
}

function resetFunc() {
	$("#frmInput #resetFlag").val("Y");
	$("#frmInput #otlAlgId").val("");

	//그리드 선택 데이터 변수 setting 
	var rowjson =  grid_tbl.GetRowJson(grid_tbl.GetSelectRow());
			
	//이상값탐지 폼셋팅
	$("#frmInput #daseId").val(rowjson.daseId);
	$("#frmInput #dbSchId").val(rowjson.dbSchId);
	
    //그리드 선택 데이터 변수 setting
	var param = "&schDbConnTrgId=" + rowjson.dbConnTrgId; 
	    param += "&schDbSchId="    + rowjson.dbSchId;
	    param += "&schDbcTblNm="   + rowjson.dbcTblNm;
	    param += "&daseId="        + rowjson.daseId;

	//이상값 프로파일 상세정보 초기화...	
	doAction("SearchAnaTrgCol", param);
}

function grid_tbl_OnClick(row, col, value, cellx, celly) {
	if(row < 1 || grid_tbl.GetColEditable(col)) return;
	
	$("#btnReset").trigger('click');
	
	//그리드 선택 데이터 변수 setting
	var rowjson =  grid_tbl.GetRowJson(row);
	
	$("#frmInput #dqiLnm").val("");
	$("#frmInput #dqiId").val("");
	
	//테이블정보 셋팅
	$("#frmDataSet #dbConnTrgPnm").val(rowjson.dbConnTrgPnm);
	$("#frmDataSet #dbSchPnm").val(rowjson.dbSchPnm);
	$("#frmDataSet #dbcTblNm").val(rowjson.dbcTblNm);
	$("#frmDataSet #dbcTblKorNm").val(rowjson.dbcTblKorNm);
	
	//이상값탐지 폼셋팅
	$("#frmInput #daseId").val(rowjson.daseId);
	$("#frmInput #dbSchId").val(rowjson.dbSchId);
	
	/* var colnm = grid_tbl.ColSaveName(0, col);
	if("ibsCheck" == colnm) return; */
	
	//화면 RESET
//    	resetPrfInfo("PC");
	
	//상세화면
//    	doAction("DtlReset");
	
  //그리드 선택 데이터 변수 setting
	var param = "&schDbConnTrgId=" + rowjson.dbConnTrgId; 
	    param += "&schDbSchId="    + rowjson.dbSchId;
	    param += "&schDbcTblNm="   + rowjson.dbcTblNm;
	    param += "&daseId="   + rowjson.daseId;
// 	    param += "&metaAsscAnly="  + grid_tbl.GetCellValue(row,"metaAsscAnly");
//  	    param += "&schDbcColNm="   + $("form[name=frmSearch] input[name='schDbcColNm']").val();
//  	    param += "&schRegYn="      + $("form[name=frmSearch] select[name='schRegYn']").val();
 	    //테이블 컬럼 프로파일 구분
//  	    param += "&tblColGb=PC";
 	     
	//이상값탐지결과 조회...
	var param1 = "daseId="+rowjson.daseId;
	doAction("SearchOutRes", param1);
	
	//이상값 프로파일 상세정보 초기화...
	doAction("DtlReset");
	doAction("SearchAnaTrgCol", param);
}


//DQI 팝업 리턴값 처리
function returnDqiPop (ret) {
	//초기화
	$("#frmInput #dqiLnm").val("");
	$("#frmInput #dqiId").val("");
	
	for(var i=0; i<ret.data.length; i++){
		var retjson = JSON.stringify(ret.data[i]);
		var parsejson = jQuery.parseJSON(retjson);
		
		if($("#frmInput #dqiLnm").val() != null && $("#frmInput #dqiLnm").val() != "undefined" && $("#frmInput #dqiLnm").val() != "" ){
			$("#frmInput #dqiLnm").val($("#frmInput #dqiLnm").val() + "," + parsejson.dqiLnm);
			$("#frmInput #dqiId").val($("#frmInput #dqiId").val() + "," + parsejson.dqiId);
		}else {
			$("#frmInput #dqiLnm").val(parsejson.dqiLnm);
			$("#frmInput #dqiId").val(parsejson.dqiId);
		}
	}
}

//이상값 탐지결과 그리드 클릭시....
function grid_outlier_OnClick(row, col, value, cellx, celly) {
	if(row < 1 || grid_outlier.GetColEditable(col)) return;

	$("#frmInput #resetFlag").val("") ;
	$("#frmInput #dqiLnm").val("");
	$("#frmInput #dqiId").val("");
	
	//그리드 선택 데이터 변수 setting
	var rowjson =  grid_outlier.GetRowJson(row);
	
	//이상값탐지 알고리즘 셋팅....
	$("#frmInput #daseId").val(rowjson.daseId);
	$("#frmInput #otlAlgId").val(rowjson.otlAlgId);
// 	$("#frmInput #dbSchId").val(rowjson.dbSchId);
// 	$("#frmInput #dbSchId").val(rowjson.dbSchId);
	
	//알고리즈 변수조회...
	var argparam = "otlDtcId="+rowjson.otlDtcId;
	 	argparam += "&otlAlgId="+rowjson.otlAlgId;
    $('div#alg_arg_input').load('<c:url value="/advisor/prepare/outlier/getoutlierparambyid.do"/>', argparam, function(){});
// 	$("#frmInput #otlAlgId").change();
	
	//이상값탐지 변수목록 조회...
	var param = "daseId="+rowjson.daseId;
	    param += "&otlDtcId="+rowjson.otlDtcId;
	    param += "&otlAlgId="+rowjson.otlAlgId;
	    param += "&dbSchId="+$("#frmInput #dbSchId").val();
	    param += "&dbSchPnm="+$("#frmDataSet #dbSchPnm").val();
	
	otlDtcIdVal = rowjson.otlDtcId;
	
	$("#frmInput #dqiLnm").val(grid_outlier.GetCellValue(row, 'dqiLnm'));
	$("#frmInput #dqiId").val(grid_outlier.GetCellValue(row, 'dqiId'));
	
	doAction("SearchAlgVal", param);
	
	//이상값탐지 결과 차트
	if ("Box Plot" == rowjson.algPnm) {
// 		createBoxPlot("<c:url value="/advisor/prepare/outlier/getchartdata.do" />", param);
// 		doAction("SearchOutData", param);
		
	} else {
// 		createScatter("<c:url value="/advisor/prepare/outlier/getscatterdata.do" />", param);
// 		doAction("SearchOutData2", param);
	}
// 	doAction("CreateOutChart", param);
	
	//이상값 결과 리스트 조회...
// 	setoutdatagridcol(rowjson);
}

function grid_col_OnClick(row, col, value, cellx, celly) {
	if(row < 1 ) return;
	
	//체크박스일 경우 NUMBER 형이 아니면 체크 못하도록 한다.
	var colnm = grid_col.ColSaveName(0, col);
	if ("ibsCheck" == colnm) {
		var rowjson =  grid_col.GetRowJson(row);
		if ("NUMBER" != rowjson.dataType) {
			
			/* 기존소스_bak 181017 */
			/* showMsgBox("INF", 'NUMBER형이 아닌경우 이상값탐지를 체크할 수 없습니다.<br>NUMBER형만 체크해 주십시요.'); */
			showMsgBox("INF", "<s:message code='INF.TEXTMATCHRQST5' />");
			grid_col.SetCellValue(row, col, 0);
		}
	}
	
	
	return;
	
}


//진단대상 테이블 조회 오류
function grid_tbl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
// 		resetPrfInfo("PC");
	}
}

//진단대상 컬럼 조회 오류
function grid_col_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code  < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		//도메인 최종이 없는 경우 도메인 추천내용으로 제공한다.
		var headrow = grid_col.HeaderRows(); 
		var datarow = grid_col.RowCount();
		for(var i = headrow; i < datarow+headrow; i++) {
			var dmnnm = grid_col.GetCellValue(i, "dmngNm");
			if(isBlankStr(dmnnm)) {
				grid_col.SetCellValue(i, "dmngNm", grid_col.GetCellValue(i, "dmnPdt"));
				dmnnm = grid_col.GetCellValue(i, "dmngNm");
				
				if(isBlankStr(dmnnm)) {
					switch(grid_col.GetCellValue(i, "dmnPdt")) {
					case "코드" :
						grid_col.SetCellValue(i, "dmngNm", 'Code');
						break;
					case "플래그" :
						grid_col.SetCellValue(i, "dmngNm", 'Flag');
						break;
					case "수" :
						grid_col.SetCellValue(i, "dmngNm", 'Number');
						break;
					case "금액" :
						grid_col.SetCellValue(i, "dmngNm", 'Price');
						break;
					case "번호" :
						grid_col.SetCellValue(i, "dmngNm", 'No');
						break;
					case "율" :
						grid_col.SetCellValue(i, "dmngNm", 'Rate');
						break;
					case "날짜" :
						grid_col.SetCellValue(i, "dmngNm", 'Date');
						break;
					case "연락처" :
						grid_col.SetCellValue(i, "dmngNm", 'Contact Information');
						break;
					case "명칭" :
						grid_col.SetCellValue(i, "dmngNm", 'Designation');
						break;
					case "내용" :
						grid_col.SetCellValue(i, "dmngNm", 'Content');
						break;
					case "ID" :
						grid_col.SetCellValue(i, "dmngNm", 'ID');
						break;
					}
				}
			}
		}
	}
}

/* function grid_col_OnChange(Row, Col, Value, OldValue, RaiseFlag) {
	if(RaiseFlag==0 && grid_col.GetCellValue(Row, 'oprCd')=='between') {
		grid_col.SetCellValue(Row, 'condCd', '');
	}
} */

</script>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 데이터셋 목록 검색 -->
<div id="anatrg" style="width: 30%; float: left;">

<div class="stit"><s:message code='DATA.SET.LST'/></div><!--검색조건--><!-- 데이터셋 목록 코드값으로 수정 181017 -->
<div style="clear:both; height:5px;"><span></span></div>

	<div id="searchTrg_div" >
	        
	        <form id="frmSearch" name="frmSearch" method="post" onsubmit="return:false;">
	            <fieldset>
	            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
	            <div class="tb_basic2" >
	                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.PROF.MNG'/>">
	                   <caption><s:message code="CLMN.PROF.MNG"/></caption><!--컬럼프로파일관리-->

	                   <colgroup>
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   <col style="width:15%;" />
	                   <col style="width:35%;" />
	                   </colgroup>
	                   
	                   <tbody>                            
	                       <tr>                               
	                           <th scope="row" class="th_require"><label for="schDbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->


	                           <td>
	                           		<select id="schDbConnTrgId"  name="schDbConnTrgId" class="wd98p">
	                           		<option value=""><s:message code="CHC" /></option><!--전체-->
	                           		</select>
	                           </td>
	                       </tr>
	                       	<tr>                               
	                           <th scope="row"><label for="schDbSchId"><s:message code="SCHEMA.NM" /></label></th><!--스키마명-->

	                           <td>
	                               <!-- <input type="text" name="schDbSchId" id="schDbSchId" class="wd98p"/> -->
	                               <select id="schDbSchId" name="schDbSchId" class="wd98p"></select>
	                           </td>
	                       </tr>
	                       <tr>
	                       		<th scope="row"><label for="schDbcTblNm"><s:message code="TBL.NM" /></label></th>	<!--테이블명-->

	                       		<td>
	                       			<input type="text" name="schDbcTblNm" id="schDbcTblNm" class="wd98p"/>
<%-- 	                       			<select id="schDbcTblNm" name="schDbcTblNm" class="wd98p"></select> --%>
	                       		</td>
	                       </tr>
	                       
	                       
	                   </tbody>
	                 </table>   
	            </div>
	            </fieldset>
	            
	        </form>
	</div>
	
    <!-- 조회버튼영역  -->
	<div style="clear:both; height:10px;"><span></span></div>
	        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
<%--             	<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> --%>
			    <button class="btn_search" id="btnAnaTrgSearch" 	name="btnAnaTrgSearch"><s:message code="INQ" /></button> <!-- 분석실행 --><!-- 조회 코드값으로 수정 181017 --> 
<%-- 			    <button class="btn_search" id="btnPattenSearch" 	name="btnPattenSearch"><s:message code="DATA.PTRN" /></button> <!-- 데이터패턴 -->  --%>
<%-- 			    <button class="btn_search" id="btnLogSearch" 	name="btnPattenSearch"><s:message code="LOG" /></button> <!-- 로그 -->  --%>
<%-- 			    <button class="btn_search" id="btnSqlSearch" 	name="btnSqlSearch"><s:message code="SQL" /></button> <!-- 분석SQL --> --%>
			    
<%-- 			    <button class="btn_save" id="btnPrfSave" 	name="btnPrfSave"><s:message code="STRG" /></button> <!-- 저장 -->  --%>
<!-- 				<button class="btn_rqst_new2" id="btnDmnPdtExec" 	name="btnDmnPdtExec">도메인판별 실행조회 -->
<%-- 			    <button class="btn_delete" id="btnPrfDelete" 	name="btnPrfDelete"><s:message code="DEL" /></button> <!-- 삭제 -->  --%>
			    
			</div>
        </div>	
	
	
	<div style="clear:both; height:5px;"><span></span></div>
	        
	<!-- 그리드 입력 입력 -->
	<div id="grid_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_tbl", "99%", "200px");</script>            
	</div>
	
</div>
<div style="width: 1%;float: left;">&nbsp;</div>
<!-- 컬럼프로파일 상세 조회 -->
<div id="colprf" style="width:69%; float: left;">
<div class="stit"><s:message code="DATA.SET" /></div><!--검색조건--><!-- 데이터셋 코드값으로 수정 181017 -->
<div style="clear:both; height:5px;"><span></span></div>
   <!-- 데이터셋 상세 -->
   	<div id="searchDsDtl_div" >
		
	 	<form id="frmDataSet" name="frmDataSet" method="post" onsubmit="return:false;">
			<input type="hidden" name="rqstNo"   id="rqstNo"  />
			
	 	<!-- 프로파일 ID, 프로파일 종류 셋팅 -->
			<!-- <input type="hidden" name="dbConnTrgId" id="dbConnTrgId"  /> 
			<input type="hidden" name="dbSchId" id="dbSchId"  />
			<input type="hidden" name="tblColGb" id="tblColGb" value="" />     
			<input type="hidden" name="prfKndCd" id="prfKndCd" />
			<input type="hidden" name="prfId" id="prfId" />   
			<input type="hidden" name="regTypCd" id="regTypCd" /> 
			<input type="hidden" name="schAnaStrDtm" id="schAnaStrDtm"  />
			 분석실행 작업ID
			<input type="hidden" name="shdJobId" id="shdJobId"  />
			<input type="hidden" name="etcJobNm" id="etcJobNm"  />
			
			<input type="hidden" name="metaAsscAnly" id="metaAsscAnly"  />
			
			<input type="hidden" name="dmnMinVal" id="dmnMinVal"  />
			<input type="hidden" name="dmnMaxVal" id="dmnMaxVal"  />
			<input type="hidden" name="dmnLnm"    id="dmnLnm"  />
			<input type="hidden" name="infotpLnm" id="infotpLnm"  />
			
			컬럼프로파일 : 컬럼명 테이블프로파일 : 관계키명, 중복분석명사용
			<div id="div_objNm"></div> -->
			
	 	<fieldset>
	    <legend><s:message code="FOREWORD" /></legend><!-- 머리말 -->
		<div class="tb_basic2" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
			   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
			   <colgroup>
			   <col style="width:15%;" />
			   <col style="width:35%;" />
			   <col style="width:15%;" />
			   <col style="width:35%;" />
			   </colgroup>
			       <tbody>   
			       		<tr>                               
			               <th scope="row"  ><label for="dbConnTrgPnm"><s:message code="DB.MS" /></label></th><!-- 진단대상명 -->
			               <td>
			                   <input  type="text"  name="dbConnTrgPnm" id="dbConnTrgPnm"  class="wd99p" readonly />
			               </td>
			               <th scope="row"  ><label for="dbSchPnm"><s:message code="SCHEMA.NM" /></label></th><!-- 스키마명 -->
			               <td>
			                   <input type="text" name="dbSchPnm" id="dbSchPnm"  class="wd99p" readonly />
			               </td>
			           </tr>                         
			           <tr>                               
			               <th scope="row"  ><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!-- 테이블명 -->
			               <td>
			                   <input type="text" name="dbcTblNm" id="dbcTblNm"  class="wd99p" readonly/>
			               </td>
			               <th scope="row"><label for="dbcTblKorNm"><s:message code="TBL.KRN.NM" /></label></th><!-- 테이블한글명 -->
			               <td>
			                   <input type="text" name="dbcTblKorNm" id="dbcTblKorNm"  class="wd99p" readonly />
			               </td>
			           </tr>
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			</form>
	</div>
<%-- 	<%@include file="dtl/prf_dtl.jsp" %> --%>
	<!--  데이터셋 상세 끝 -->

	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit"><s:message code="ANRM.VAR.DTCT.LST" /> </div><!--검색조건--><!-- 이상값 탐지 목록 코드값으로 수정 181017 -->
	<div style="clear:both; height:5px;"><span></span></div>
	
	<div id="grid_outlier_01" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_outlier", "99%", "200px");</script>            
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<!-- 버튼영역  -->
	<tiles:insertTemplate template="/WEB-INF/decorators/buttonProfile.jsp" />
	
	<div style="clear:both; height:10px;"><span></span></div>
	<!-- 이상값탐지결과 탭처리... -->
	<div id="tabs">
	  <ul>
	    <li id="tab-out01"><a href="#tabs-out01"><s:message code="ANRM.VAL.PROF" /></a></li><!--컬럼분석--><!-- 이상값 프로파일 코드값으로 수정 181017 -->
<!-- 	    <li id="tab-out02"><a href="#tabs-out02">이상값목록</a></li>코드분석 -->
	  </ul>
	  <div id="tabs-out01">
	  	<!-- 이상값 프로파일 상세 조회 -->
		<div id="vardtltab" >
		<div class="stit" ><s:message code="ANRM.VAR.DTCT.ALGO" /></div><!--검색조건--><!-- 이상값 탐지 알고리즘 코드값으로 수정 181017 --> 
		<div style="clear:both; height:5px;"><span></span></div>
				<div id="outlier_setting_div" >
			        <form id="frmInput" name="frmInput" method="post" onsubmit="return:false;">
			        	<input type="hidden" name="daseId" id="daseId" />
			        	<input type="hidden" name="dbSchId" id="dbSchId" />
			        	<input type="hidden" name="resetFlag" id="resetFlag" />
			        	
			            <fieldset>
			            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
			            <div class="tb_basic2" >
			                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='CLMN.PROF.MNG'/>">
			                   <caption><s:message code="CLMN.PROF.MNG"/></caption><!--컬럼프로파일관리-->
		
			                   <colgroup>
			                   <col style="width:10%;" />
			                   <col style="width:30%;" />
			                   <col style="width:10%;" />
			                   <col style="width:50%;" />
			                   </colgroup>
			                   
			                   <tbody>                            
			                       <tr>                               
			                           <th scope="row" class="th_require"><label for="otlAlgId"><s:message code='ALG' /></label></th><!--알고리즘-->
			                           <td>
			                           		<select id="otlAlgId"  name="otlAlgId">
			                           		<option value=""><s:message code="CHC" /></option><!--선택-->
										    <c:forEach var="code" items="${codeMap.otlAlgCd}" varStatus="status">
										    <option value="${code.codeCd}">${code.codeLnm}</option>
										    </c:forEach>
			                           		</select>
			                           		&nbsp;&nbsp;&nbsp;
			                           		<input style="background-color: #2682ca;" type="button" id="btnReset" name="btnReset" class="btn_save" value="<s:message code='INON' />" />
			                           </td>
			                           <th scope="row"  class="th_require"><label for="btnApply"><s:message code="QLTY.INDC.NM" /></label></th><!-- 품질지표명 코드값으로 수정 181017 -->
			                           <td>
			                           		<input type="text" name="dqiLnm" id="dqiLnm"  style="width:70%;" readOnly>
			                           		<input type="hidden" name="dqiId" id="dqiId">
			                           		&nbsp;&nbsp;&nbsp;
		                           			<input style="background-color: #2682ca;" type="button" id="btnApply" name="btnApply" class="btn_save" value="<s:message code='INQ' />" />
			                           </td> 			                           
			                       </tr>			                       
					                                   
			                   </tbody>
			                 </table>   
			            </div>
			            </fieldset>
			            
			            <!-- 			알고리즘 옵션	 -->
			            <div id="alg_arg_input">
			            </div>
			        </form>
			</div>

			            
			<div style="clear:both; height:10px;"><span></span></div>
			<div class="stit"><s:message code="ANRM.VAR.DTCT.CLMN.VRB.LST" /> </div><!--검색조건--><!-- 이상값탐지 컬럼(변수) 목록 코드값으로 수정 181017 -->
			<div style="clear:both; height:5px;"><span></span></div>
			
			<div id="grid_02" class="grid_01">
			     <script type="text/javascript">createIBSheet("grid_col", "99%", "250px");</script>            
			</div>
			
			<div style="clear:both; height:10px;"><span></span></div>
		
	  </div>

	 </div>
	<!-- 이상값탐지결과 탭 끝 -->
	

</div>




