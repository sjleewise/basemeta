<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
var pageSize = 1000;
var pageNo = 1;
var appendVal = false;

$(document).ready(function() {
	
// 	alert('${search.rqstNo}');
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code='VALID.PRFREQUIRED' />";
	
    // 조회 Event Bind
    $("#btnAnaTrgSearch").click(function(){ doAction("SearchAnaTrgTbl");  });

    // 데이터매칭 실행 Event Bind
    $("#btnMatchExec").click(function(){ 
        //doAction("TextMatchExec"); 

        //quartz 스케줄 등록 
    	doAction("regScheduler"); 
    });
    
    //도메인 저장...
    $("#btnSaveOutlier").click(function(){
    	doAction("SaveOutlierDection");
    }).show();
    
    //알고리즘 변경시 이벤트처리 : 알고리즘에 해당하는 변수목록을 가져와서 셋팅한다.
    $("#frmInput #otlAlgId").change(function(){
    	//알고리즘 변수 호출 
    	var param = "algId="+ $(this).val();
    	$('div#alg_arg_input').load('<c:url value="/advisor/prepare/outlier/getoutlierparamlist.do"/>', param, function(){});
    });

    //텍스트매칭 추가 
    $("#btnAddMatch").click(function(){

    	if($("#frmSearch #srcDbConnTrgId").val() == ""){
    		var message = "<s:message code="VALID.PRFREQUIRED" />";
    		showMsgBox("INF", message); 
    		return;
    	}
      	var url = '<c:url value="/advisor/prepare/textcluster/popup/textmatchtblcol_pop.do"/>'; 
      	
    	var param = $("#frmSearch").serialize();  

    	param += "&srcDbConnTrgNm=" + $("#srcDbConnTrgId option:selected").text() ;

    	openLayerPop(url ,1000 ,800,  param);
    });
    
    $("#btnDelMatch").click(function() {
    	doAction("DelMatch");
    });

    /* $("#btnPrfSchRqst").click(function(){
    	doAction("PrfSchRqst");
    }); */
    
    //div size 정의
//     divSize();
    
    //진단대상 상세정보 READONLY SETTING
// 	$("#frmAnaTrg input[type=text]").css("border-color","transparent").css("width", "47%");
	//컬럼분석 input 요소 objNm 추가
	//prf_dtl 추가 시 테이블분석 키명 input 요소 objNm 존재 하기때문에 배열로 인식됨
// 	$("div#div_objNm").html("<input type='hidden'  class='wd50p' name='objNm' id='objNm' />");
	//컬럼관련 진단대상 요소 show
// 	$("form[name=frmAnaTrg] #colPrfTrLayer").show();
	
	$("#frmSearch #srcDbConnTrgId").change(function() {
		$("#frmSearch #srcDbcSchNm").find("option").remove().end();
		var val = $("#srcDbConnTrgId option:selected").text();
		var trgId = ${codeMap.schDbSchNm};

		$("#frmSearch #srcDbcSchNm").append('<option value=""></option>');
		
		for(i=0; i<trgId.length; i++) {
			if(trgId[i].upcodeCd == val) {
				$("#frmSearch #srcDbcSchNm").append('<option value="' + trgId[i].codeCd + '">' + trgId[i].codeLnm + '</option>');
			}
		}
	});
	
	$("#frmSearch #srcDbcSchNm").change(function() {
		var url = "<c:url value='/advisor/prepare/textcluster/getTblList.do' />";
		
		var param = $("#frmSearch").serialize();
		
		$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: replacerXssParam(param),
			dataType: 'json',
			success: function (data) {
				$("#frmSearch #srcDbcTblNm").find("option").remove().end();
				$("#frmSearch #tgtDbcTblNm").find("option").remove().end();
				
				$("#frmSearch #srcDbcTblNm").append('<option value=""></option>');
				$("#frmSearch #tgtDbcTblNm").append('<option value=""></option>');
				
				for(i=0; i<data.length; i++) {
					$("#frmSearch #srcDbcTblNm").append('<option value="' + data[i].srcDbcTblNm + '">' + data[i].srcDbcTblNm + '</option>');
					$("#frmSearch #tgtDbcTblNm").append('<option value="' + data[i].srcDbcTblNm + '">' + data[i].srcDbcTblNm + '</option>');
				}
			},
			error: function (jqXHR, textStatus, errorThrown) {
							
			}
		});
	});
	
	//파라미터 : (자동완성 대상 오브젝트, 검색할 단어종류, 최대표시 갯수(default-10개))
	//setautoComplete($("#frmSearch #srcDbcSchNm"), "DBSCH");
	//setautoComplete($("#frmSearch #srcDbcTblNm"), "DBCTBL");
	//setautoComplete($("#frmSearch #tgtDbcTblNm"), "DBCTBL");
});



$(window).on('load',function() {
	//그리드 초기화 
	initGrid();
	
	//그리드 높이 초기화
	setibsheight($("#grid_01"));
	setibsheight($("#grid_02"));
	
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
                    {Text:"<s:message code='BDQ.HEADER.TEXTMATCHRQST.LST'/>", Align:"Center"}
                ];
		//No|상태|선택|매칭ID|소스DBID|DB명|스키마ID|스키마명|소스테이블명|소스테이블한글명|타겟DBID|타겟DB명|타겟스키마ID|타겟스키마명|타겟테이블명|타겟테이블논리명
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
					{Type:"Text",   Width:200,  SaveName:"mtcId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"srcDbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"srcDbConnTrgNm",    	Align:"Center", Edit:0, Hidden:0}, 
                    {Type:"Text",   Width:100,  SaveName:"srcDbcSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"srcDbcSchNm",    	Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"srcDbcTblNm",    	Align:"Center", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"srcDbcTblLnm",    	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"tgtDbConnTrgNm",    	Align:"Center", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"tgtDbcSchId",    	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"tgtDbcSchNm",    	Align:"Center", Edit:0, Hidden:1},
                    {Type:"Text",   Width:100,  SaveName:"tgtDbcTblNm",    	Align:"Center", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"tgtDbcTblLnm",    	Align:"Left", Edit:0, Hidden:1},
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
    
	//진단대상 컬럼 grid
   	with(grid_col){
   		
   		var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);
           SetMergeSheet(1);
           
           var headers = [
                       {Text:"<s:message code='BDQ.HEADER.TEXTMATCHRQST.LST4'/>", Align:"Center"}
                   ];
           //No.|상태|선택|매칭ID|매칭순번|소스DBID|소스스키마ID|소스테이블명|소스컬럼명|타겟DBID|타겟스키마ID|타겟테이블명|타겟컬럼명

           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 
           var cols = [                        
                    {Type:"Seq",   Width:70,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Text",   Width:200,  SaveName:"mtcId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"mtcColSno",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
                    {Type:"Text",   Width:200,  SaveName:"srcDbConnTrgId",  Align:"Left", Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"srcDbcSchId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"srcDbcTblNm",   	Align:"Left", Edit:0, ColMerge:1},
					{Type:"Text",   Width:200,  SaveName:"srcDbcColNm",   	Align:"Left", Edit:0 },

					{Type:"Text",   Width:200,  SaveName:"tgtDbConnTrgId",  Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"tgtDbcSchId",   	Align:"Left", Edit:0, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"tgtDbcTblNm",   	Align:"Left", Edit:0, ColMerge:1},
					{Type:"Text",   Width:200,  SaveName:"tgtDbcColNm",   	Align:"Left", Edit:0},
					
               ];
       
       //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
       InitColumns(cols);
       //콤보 목록 설정...
// 	   SetColProperty("dmngNm", {ComboText: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그', ComboCode: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'});
// 	     'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'

       InitComboNoMatchText(1, "");
       
       //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
       
       FitColWidth();
       SetExtendLastCol(1); 
   
   	}
	
    //===시트설정 후 아래에 와야함===
    init_sheet(grid_col);    
    //===========================	

    	
    //데이터매칭 결과 조회
   	with(grid_resdata){
   		
   		var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);
           //헤더 머지...
           SetMergeSheet(5);
           
           var headers = [
                       {Text:"<s:message code='BDQ.HEADER.TEXTMATCHRQST.LST5'/>", Align:"Center"}
                   ];
           //No.|상태|선택|매칭ID|매칭일련번호|컬럼건수|소스컬럼|소스컬럼|소스컬럼|소스컬럼|소스컬럼|타겟컬럼|타겟컬럼|타겟컬럼|타겟컬럼|타겟컬럼|매칭확률

           var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 
           var cols = [                        
                    {Type:"Seq",   Width:70,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Text",   Width:60,  SaveName:"mtcId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
                    {Type:"Int",   Width:60,  SaveName:"mtcSno",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
                    {Type:"Int",   Width:60,  SaveName:"colCnt",  Align:"Left", Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"srcColNm1",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"srcColNm2",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"srcColNm3",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"srcColNm4",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"srcColNm5",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm1",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm2",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm3",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm4",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm5",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Float",   Width:80,  SaveName:"mtcPrb",   	Align:"Center", Edit:0, KeyField:0, Format:"##0.0"},
               ];
       
       //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
       InitColumns(cols);
       //콤보 목록 설정...
// 	   SetColProperty("dmngNm", {ComboText: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그', ComboCode: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'});
// 	     'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'

       InitComboNoMatchText(1, "");
       
       //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
       
       FitColWidth();
       SetExtendLastCol(1); 
   
   	}
	
    //===시트설정 후 아래에 와야함===
    init_sheet(grid_resdata);    
    //===========================	

    //데이터매칭 순위 결과 조회
   	with(grid_tgtdtl){
   		
   		var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);
           //헤더 머지...
           SetMergeSheet(5);
           
           var headers = [
                       {Text:"<s:message code='BDQ.HEADER.TEXTMATCHRQST.LST5'/>", Align:"Center"}
                   ];
           //No.|상태|선택|매칭ID|매칭일련번호|컬럼건수|소스컬럼|소스컬럼|소스컬럼|소스컬럼|소스컬럼|타겟컬럼|타겟컬럼|타겟컬럼|타겟컬럼|타겟컬럼|매칭확률

           var headerInfo = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 
           var cols = [                        
                    {Type:"Seq",   Width:70,   SaveName:"ibsSeq",    	Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                    {Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:1, Sort:0},
                    {Type:"Text",   Width:60,  SaveName:"mtcId",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
                    {Type:"Int",   Width:60,  SaveName:"mtcSno",   	Align:"Left", Edit:0, KeyField:0, Hidden:1},
                    {Type:"Int",   Width:60,  SaveName:"colCnt",  Align:"Left", Edit:0, KeyField:0, Hidden:1},
					{Type:"Text",   Width:200,  SaveName:"srcColNm1",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"srcColNm2",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"srcColNm3",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"srcColNm4",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"srcColNm5",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm1",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm2",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm3",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm4",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Text",   Width:200,  SaveName:"tgtColNm5",   	Align:"Center", Edit:0, KeyField:0},
					{Type:"Float",   Width:80,  SaveName:"mtcPrb",   	Align:"Center", Edit:0, KeyField:0, Format:"##0.0"},
               ];
       
       //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
       InitColumns(cols);
       //콤보 목록 설정...
// 	   SetColProperty("dmngNm", {ComboText: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그', ComboCode: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'});
// 	     'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'

       InitComboNoMatchText(1, "");
       
       //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
       
       FitColWidth();
       SetExtendLastCol(1); 
   
   	}
	
    //===시트설정 후 아래에 와야함===
    init_sheet(grid_tgtdtl);    
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
	        	if (isBlankStr( $("#frmSearch #srcDbConnTrgId").val())) {
	        		showMsgBox("ERR", "<s:message code="MSG.DB.MS.INFO.CHC" />"); //DBMS정보를 선택해주세요.
	        		return;
	        	}
	//         	param += "&tblColGb=PC";
	        	param += "&"+$('form[name=frmSearch]').serialize();
        	} else {
	        	param = "rqstNo="+aparam;
        	}
        	
//         	grid_tbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getPrfTblLst.do" />", param);
        	grid_tbl.DoSearch("<c:url value="/advisor/prepare/textcluster/getMatchTblList.do" />", param);
        	break;
        	
       	/*진단대상 컬럼 조회*/
        case "SearchAnaTrgCol":
        	var param = $("#frmDataSet").serialize();
        	grid_col.DoSearch("<c:url value="/advisor/prepare/textcluster/getMatchColList.do" />", param);
        	break;
        
        //테이블의 summary를 조회해서 저장한다....
        case "GetSummary":
        	var url = "<c:url value="/advisor/prepare/summary/regSummaryDataset.do"/>";
			//var param = ""; //$("#mstFrm").serialize();
			IBSpostJson2(url, SaveJson, aparam, ibscallback);
        	break;
        //컬럼의 histogram를 조회한다.
        case "getHistogram":
        	var url = "<c:url value="/advisor/prepare/summary/gethistodtl.do"/>";
			var param = ""; //$("#mstFrm").serialize();
			
			histo_grid.DoSearch(url, aparam);
			
			/* $.getJSON(url+"?"+aparam, function(data){
				//데이터가 	
				var tmphtml="<tr><td colspan='2' style='text-align: center;'>조회된 데이터가 없습니다.</td></tr>";
				if(data !=  null || data.length > 0) {
					tmphtml="";
					var cnt = data.length;
					for(i=0;i<cnt;i++) {
						tmphtml += "<tr><td class='' style='width:60%;'> "+data[i].sctStrVal+"~"+data[i].sctEndVal+" </td>";
						tmphtml += "<td class='' style='width:40%;'>"+data[i].sctVal+"</td></tr>";
					}
					
				}
				
				$("#histo_tbl_body tr").html(tmphtml);
			}); */
        	break;
        	
        case "SaveHistogram":
			var url = "<c:url value="/advisor/prepare/summary/reghistogram.do"/>";
			var param = ""; //$("#mstFrm").serialize();
			IBSpostJson2(url, null, aparam, ibscallback);
        	break;
        case "TextMatchExec": //데이터매칭 실행...
        	if (isBlankStr( $("#frmDataSet #mtcId").val())) {
        		showMsgBox("ERR", "데이터매칭정보가 없습니다.<br>데이터매칭 목록을 선택 후 실행해 주십시요."); //DBMS정보를 선택해주세요.
        		return;
        	}
        	
//         	var SaveJson = grid_tbl.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
// 			if (SaveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(SaveJson.data.length == 0) return;
			
			var url = "<c:url value="/advisor/prepare/textcluster/execdatamatch.do"/>";
        	var param = $("#frmDataSet").serialize();
			IBSpostJson2(url, null, param, ibscallback);
        	break;
       	//실시간실행 스케줄 등록
        case "regScheduler":
            
        	if (isBlankStr( $("#frmDataSet #mtcId").val())) {
        		showMsgBox("ERR", "데이터매칭정보가 없습니다.<br>데이터매칭 목록을 선택 후 실행해 주십시요."); //DBMS정보를 선택해주세요.
        		return;
        	}
        	              	
   			var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
   			var param = "&shdKndCd=TM"; //파이썬 실행 (텍스트매칭)

   			var shdJobNm = '[텍스트매칭]' + $("#frmDataSet #srcDbcTblNm").val() + "-" + $("#frmDataSet #tgtDbcTblNm").val() ;

			
   			var temp = {'shdJobId': $("#frmDataSet #mtcId").val()
   						, 'shdJobNm' : shdJobNm
   						, 'shdJobKndCd':'TM'};
   			
   			var tmparr = new Array();
   			tmparr.push(temp);
   			
   			var SaveJson = {"data": tmparr};
   			
   		    IBSpostJson2(urls, SaveJson, param, schedulerCallBack);

           	break;
        case "SearchResult":
            //데이터매칭 결과 조회 ...
            var url = "<c:url value="/advisor/prepare/textcluster/getmatchdata.do"/>";
            //alert(pageSize * (pageNo-1));
            var param = $("#frmDataSet").serialize() + "&iPageNo=" + (pageSize * (pageNo-1)) + "&iPageSize=" + (pageSize * pageNo);
            grid_resdata.DoSearch(url, param,{Append:appendVal});
            	
        	break;
        case "SearchTgtResult":
            //데이터매칭 순위 결과 조회 ...
            var url = "<c:url value="/advisor/prepare/textcluster/getmatchtgtdata.do"/>";
//             var param = $("#frmDataSet").serialize();
            grid_tgtdtl.DoSearch(url, aparam);
            	
        	break;
            	
        case "SaveOutlierDection":
        	
        	var algtxt = $("#frmInput #otlAlgId option:selected").text();
        	//알고리즘 선택 필수...
        	if (isBlankStr($("#frmInput #otlAlgId").val())) {
        		showMsgBox("INF", "이상값 탐지 알고리즘이 없습니다.<br>알고리즘을 선택 후 실행하십시요."); return;
        	}
        	//데이터셋 선택 필수...
        	if (isBlankStr($("#frmInput #daseId").val())) {
        		showMsgBox("INF", "선택된 데이터셋이 없습니다.<br>데이터셋을 선택 후 실행하십시요."); return;
        	}
        	
        	var SaveJson = grid_col.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (SaveJson.Code == "IBS000") {
				showMsgBox("INF", "이상값 탐지대상이 없습니다.<br>변수목록에서 대상을 체크 후 실행하십시요.") ; 
				return; 	// 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
			}
							// 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
							// Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
	    	
						
			if(algtxt != "단변량" && SaveJson.data.length < 2) {
	    		showMsgBox("INF", "다변량 이상값 탐지의 경우 변수를 최소 2개이상 선택하셔야 합니다.<br>변수목록에서 2개이상 체크 후 실행하십시요.");
				return;
	    	}
			
			var url = "<c:url value="/advisor/prepare/outlier/regoutlier.do"/>";
			var param = $("#frmInput").serialize();
			IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
        	

        case "DtlReset":
        	//컬럼분석 텝 강제 클릭
//     		$("#tab-pc01 a").click();
    		//컬럼분석 form reset
			resetPC01();
			//코드분석 RESET
			resetPC02();
			//코드분석 RESET
			resetPC03();
			//범위분석 RESET
			resetPC04();
			//패턴분석 RESET
			resetPC05();
        	
    	    break;
        
        	
        case "Down2Excel":  //엑셀내려받기
            grid_tbl.Down2Excel({HiddenColumn:1, Merge:1});
            break;
            
        case "PrfSchRqst":
        	var url   = "<c:url value="/dq/profile/popup/prfschrqst_pop.do"/>";
        	var popup = OpenWindow(url+param,"SQL","1200","750","yes");

        	break;
        	
        case "DelMatch":
        	ibsSaveJson = grid_tbl.GetSaveJson({StdCol:'ibsCheck'});
        	
        	if(ibsSaveJson.data.length == 0) {
				showMsgBox("ERR","체크박스에 체크하세요.");
				return;
			}
        	
        	var url = "<c:url value="/advisor/prepare/textcluster/delmatchdata.do"/>";

			var param = $("#frmSearch").serialize();
			
			IBSpostJson2(url, ibsSaveJson, param, ibscallback);
			
        	break;
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
				//도메인판별 요청결과 조회로 이동
// 				$("#frmDataSet #rqstNo").val(res.resultVO.rqstNo);
// 				showMsgBox("CNF", "도메인판별 결과로 이동하시겠습니까?", "DmnPredictResult");
				
				if (res.resultVO != null && !isBlankStr(res.resultVO.anlVarId)) {
					//히스토그램 조회를 한다.
					var row = grid_col.GetSelectRow();
					var rowjson =  grid_col.GetRowJson(row);
					var param1 = "anlVarId="+rowjson.anlVarId;
					doAction("getHistogram", param1);
				} else {
					var row = grid_tbl.GetSelectRow();
					
					//그리드 선택 데이터 변수 setting
					var rowjson =  grid_tbl.GetRowJson(row);
					
				  //그리드 선택 데이터 변수 setting
					var param = "&srcDbConnTrgId=" + rowjson.dbConnTrgId; 
					    param += "&schDbSchId="    + rowjson.dbSchId;
					    param += "&schDbcTblNm="   + rowjson.dbcTblNm;
					    param += "&daseId="   + rowjson.daseId;
	//			 	    param += "&metaAsscAnly="  + grid_tbl.GetCellValue(row,"metaAsscAnly");
	//			  	    param += "&schDbcColNm="   + $("form[name=frmSearch] input[name='schDbcColNm']").val();
	//			  	    param += "&schRegYn="      + $("form[name=frmSearch] select[name='schRegYn']").val();
				 	    //테이블 컬럼 프로파일 구분
	//			  	    param += "&tblColGb=PC";
				 	     
					 //컬럼목록 조회
					doAction("SearchAnaTrgCol", param);
				}
				
				break;
			//데이터매칭 요청 후 처리...
			case "<%=WiseMetaConfig.RqstAction.SUBMIT%>":
				doAction("SearchResult");
				break;
			//요청서 결재단계별 승인 완료 후처리
			case "<%=WiseMetaConfig.RqstAction.APPROVE%>":

				break;
			
			default : 
				// 아무 작업도 하지 않는다...
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
	
	grid_col.FitColWidth();
	
// 	grid_prf.FitColWidth();
	
}


function loadColDetail(param){
	ajax2Json('<c:url value="/dq/profile/getAnaTrgColDetail.do"/>', param, setAnaTgtDtl);
}

function grid_tbl_OnClick(row, col, value, cellx, celly) {
	if(row < 1 || grid_tbl.GetColEditable(col)) return;
	
	pageNo = 1;
	appendVal = false;
	
	//그리드 선택 데이터 변수 setting
	var rowjson =  grid_tbl.GetRowJson(row);
	
	$("#frmDataSet #srcDbConnTrgNm").val(rowjson.srcDbConnTrgNm);
	$("#frmDataSet #srcDbcSchNm").val(rowjson.srcDbcSchNm);
	$("#frmDataSet #srcDbcTblNm").val(rowjson.srcDbcTblNm);
	$("#frmDataSet #srcDbcTblLnm").val(rowjson.srcDbcTblLnm);
	$("#frmDataSet #tgtDbcTblNm").val(rowjson.tgtDbcTblNm);
	$("#frmDataSet #tgtDbcTblLnm").val(rowjson.tgtDbcTblLnm);
	$("#frmDataSet #srcDbcSchId").val(rowjson.srcDbcSchId);
	
	//데이터매칭 폼셋팅
	$("#frmDataSet #mtcId").val(rowjson.mtcId);
	
	/* var colnm = grid_tbl.ColSaveName(0, col);
	if("ibsCheck" == colnm) return; */
	
	//화면 RESET
//    	resetPrfInfo("PC");
	
	//상세화면
//    	doAction("DtlReset");
	
  //그리드 선택 데이터 변수 setting
	var param = "&srcDbConnTrgId=" + rowjson.dbConnTrgId; 
	    param += "&schDbSchId="    + rowjson.dbSchId;
	    param += "&schDbcTblNm="   + rowjson.dbcTblNm;
	    param += "&daseId="   + rowjson.daseId;
// 	    param += "&metaAsscAnly="  + grid_tbl.GetCellValue(row,"metaAsscAnly");
//  	    param += "&schDbcColNm="   + $("form[name=frmSearch] input[name='schDbcColNm']").val();
//  	    param += "&schRegYn="      + $("form[name=frmSearch] select[name='schRegYn']").val();
 	    //테이블 컬럼 프로파일 구분
//  	    param += "&tblColGb=PC";
 	     
	 //컬럼목록 조회
	doAction("SearchAnaTrgCol");
	 //데이터매칭 결과 조회...
	doAction("SearchResult");
	 
	 //summary 없는경우만 조회 및 저장....
// 	 if (rowjson.summaryYn == 'N')
// 		doAction("GetSummary", param);
}

function grid_col_OnClick(row, col, value, cellx, celly) {
// 	if(row < 1 || grid_col.GetColEditable(col)) return;
	if(row < 1 ) return;
	
	
	return;
	//체크박스일 경우 NUMBER 형이 아니면 체크 못하도록 한다.
	var colnm = grid_col.ColSaveName(0, col);
	if ("ibsCheck" == colnm) {
		var rowjson =  grid_col.GetRowJson(row);
		if ("NUMBER" != rowjson.dataType) {
			showMsgBox("INF", 'NUMBER형이 아닌경우 이상값탐지를 체크할 수 없습니다.<br>NUMBER형만 체크해 주십시요.');
			grid_col.SetCellValue(row, col, 0);
		}
	}
	
	
	return;
	//그리드 선택 데이터 변수 setting
	var rowjson =  grid_col.GetRowJson(row);
	var param = "objId="+rowjson.prfId;
	var param1 = "anlVarId="+rowjson.anlVarId
		param1 += "&dbSchId="+rowjson.dbSchId;
		param1 += "&dbTblNm="+rowjson.dbcTblNm;
		param1 += "&dbColNm="+rowjson.dbcColNm;

// 	$("div#vardtl_stit").text(rowjson.dbcColNm +" 상세정보");
	
	//컬럼분석 프로파일 id로 확인...
	$('div#colanaresdtl').load('<c:url value="/dq/report/ajaxgrid/getColAnaResDtl.do"/>', param, function(){});
	//컬럼의 summary 조회
	$('div#summary_ds_dtl').load('<c:url value="/advisor/prepare/summary/getsummary_dtl.do"/>', param1, function(){});
	
	param += "&objIdCol=PRF_ID";
	param += "&erDataSnoCol=ESN_ER_DATA_SNO";
	param += "&colNm=, COL_NM1, COL_NM2";
	param += "&objErrTbl=WAM_PRF_ERR_DATA";
// 	param += "&objDate=20170919140100";
	getDataPattern(param);
	
	//히스토그램 정보가 있을 경우 내용 조회 없을 경우 변수타입에 따라 히스토그램 grpc 호출 및 저장
// 	var histoyn = rowjson.histoYn; 
	if ("N" == rowjson.histoYn && "NUMBER" == rowjson.dataType) {
		//histo grpc를 호출해서 저장한다.
		doAction("SaveHistogram", param1);
	} else {
		//히스토그램 내용을 조회한다.
		doAction("getHistogram", param1);
	}
	
// 	getDmnPredictResult(param1);
	//프로파일정보 reset
   	/* resetPrfInfo("PT");
   	
    //텝 상세화면 RESET
   	doAction("DtlReset");
    
    var metaAsscAnly = grid_col.GetCellValue(row, "metaAsscAnly");
	
	var param  = "&dbConnTrgId="  + grid_col.GetCellValue(row, "dbConnTrgId"); 
	    param += "&dbSchId="      + grid_col.GetCellValue(row, "dbSchId");
	    param += "&dbcTblNm="     + grid_col.GetCellValue(row, "dbcTblNm");
	    param += "&dbcColNm="     + grid_col.GetCellValue(row, "dbcColNm");
	   
	    //테이블 컬럼 프로파일 구분
	    param += "&tblColGb=PC";
	    
	    param += "&metaAsscAnly=" + metaAsscAnly;
	    
	    
     
 	loadColDetail(param); */
	
}


function grid_resdata_OnClick(row, col, value, cellx, celly) {
	if(row < 1 || grid_resdata.GetColEditable(col)) return;
// 	if(row < 1 ) return;
	var rowjson =  grid_resdata.GetRowJson(row);
	var param = "mtcId="+rowjson.mtcId;
	    param += "&mtcSno="+rowjson.mtcSno;
	
	doAction("SearchTgtResult", param);
	
	
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
		
	}
}

//진단대상 컬럼 조회 오류
function grid_resdata_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code  < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		//컬럼수만큼 보여주고 나머지는 히든
		var headrow = grid_resdata.HeaderRows(); 
		var cntCol = grid_resdata.GetCellValue(headrow, "colCnt");
		for(var i=1;i<6;i++){
			if (i <= cntCol)  grid_resdata.SetColHidden("srcColNm"+i, 0);
			else grid_resdata.SetColHidden("srcColNm"+i, 1);
			if (i <= cntCol)  grid_resdata.SetColHidden("tgtColNm"+i, 0);
			else grid_resdata.SetColHidden("tgtColNm"+i, 1);
		}
		grid_resdata.FitSize(0, 1);
		
	}
}

function grid_tgtdtl_OnSearchEnd(code, message, stCode, stMsg) {
	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code  < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		//컬럼수만큼 보여주고 나머지는 히든
		var headrow = grid_tgtdtl.HeaderRows(); 
		var cntCol = grid_tgtdtl.GetCellValue(headrow, "colCnt");
		for(var i=1;i<6;i++){
			if (i <= cntCol)  grid_tgtdtl.SetColHidden("srcColNm"+i, 0);
			else grid_tgtdtl.SetColHidden("srcColNm"+i, 1);
			if (i <= cntCol)  grid_tgtdtl.SetColHidden("tgtColNm"+i, 0);
			else grid_tgtdtl.SetColHidden("tgtColNm"+i, 1);
		}
		grid_tgtdtl.FitSize(0, 1);
		
	}
}

function grid_resdata_OnVScroll(vpos, oldvpos, isTop, isBottom) {
	if(isBottom) {
		//if(grid_resdata.GetDataLastRow()<(pageNo*pageSize)) return;
		appendVal = true;
		pageNo++;
		doAction("SearchResult");
		return;
	}
}

</script>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 데이터셋 목록 검색 -->
<div id="anatrg" style="width: 25%; float: left;">

<div class="stit">데이터 매칭 목록</div><!--검색조건-->
<div style="clear:both; height:5px;"><span></span></div>

	<div id="searchTrg_div" >
	        
	        <form id="frmSearch" name="frmSearch" method="post">
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
	                           <th scope="row" class="th_require"><label for="srcDbConnTrgId"><s:message code="DB.MS" /></label></th><!--진단대상명-->
	                           <td>
	                           		<select id="srcDbConnTrgId"  name="srcDbConnTrgId" class="wd98p">
	                           		<option value=""><s:message code="WHL" /></option><!--전체-->

								    <c:forEach var="code" items="${codeMap.connTrgDbmsCd}" varStatus="status">
								    <option value="${code.codeCd}">${code.codeLnm}</option>
								    </c:forEach>
	                           		</select>
	                           </td>
	                       </tr>
	                       	<tr>                               
	                           <th scope="row"><label for="srcDbcSchNm"><s:message code="SCHEMA.NM" /></label></th><!--스키마명-->

	                           <td>
	                               <!-- <input type="text" name="srcDbcSchNm" id="srcDbcSchNm" class="wd98p"/> -->
	                               <select id="srcDbcSchNm" name="srcDbcSchNm" class="wd98p"></select>
	                           </td>
	                       </tr>
	                       <tr>
	                       		<th scope="row"><label for="srcDbcTblNm">소스 <s:message code="TBL.NM" /></label></th>	<!--테이블명-->

	                       		<td>
	                       			<!-- <input type="text" name="srcDbcTblNm" id="srcDbcTblNm" class="wd98p"/> -->
	                       			<select id="srcDbcTblNm" name="srcDbcTblNm" class="wd98p"></select>
	                       		</td>
	                       </tr>
	                       <tr>
	                       		<th scope="row"><label for="tgtDbcTblNm">타겟 <s:message code="TBL.NM" /></label></th>	<!--테이블명-->

	                       		<td>
	                       			<!-- <input type="text" name="tgtDbcTblNm" id="tgtDbcTblNm" class="wd98p"/> -->
	                       			<select id="tgtDbcTblNm" name="tgtDbcTblNm" class="wd98p"></select>
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
			    <button class="btn_search"  id="btnAnaTrgSearch" name="btnAnaTrgSearch">조회</button> <!-- 분석실행 --> 
			      
			    
<%-- 			    <button class="btn_search" id="btnPattenSearch" 	name="btnPattenSearch"><s:message code="DATA.PTRN" /></button> <!-- 데이터패턴 -->  --%>
<%-- 			    <button class="btn_search" id="btnLogSearch" 	name="btnPattenSearch"><s:message code="LOG" /></button> <!-- 로그 -->  --%>
<%-- 			    <button class="btn_search" id="btnSqlSearch" 	name="btnSqlSearch"><s:message code="SQL" /></button> <!-- 분석SQL --> --%>
			    
<%-- 			    <button class="btn_save" id="btnPrfSave" 	name="btnPrfSave"><s:message code="STRG" /></button> <!-- 저장 -->  --%>
<!-- 				<button class="btn_rqst_new2" id="btnDmnPdtExec" 	name="btnDmnPdtExec">도메인판별 실행조회 -->
<%-- 			    <button class="btn_delete" id="btnPrfDelete" 	name="btnPrfDelete"><s:message code="DEL" /></button> <!-- 삭제 -->  --%>
			    
			</div>
			<div class="bt02">
			    <button class="btn_rqst_new" id="btnDelMatch" name="btnDelMatch">삭제</button> <!-- 텍스트매칭삭제 --> 
				<button class="btn_rqst_new" id="btnAddMatch" name="btnAddMatch">추가</button> <!-- 텍스트매칭추가 -->
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
<div id="colprf" style="width:30%; float: left;">
<div class="stit">데이터매칭 테이블 정보</div><!--검색조건-->
<div style="clear:both; height:5px;"><span></span></div>
   <!-- 데이터셋 상세 -->
   	<div id="searchDsDtl_div" >
		
	 	<form id="frmDataSet" name="frmDataSet" method="post">
			<input type="hidden" name="srcDbcSchId" id="srcDbcSchId"  />
			<input type="hidden" name="mtcId" id="mtcId"  />
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
		<div class="tb_read" >
			<table width="100%"  summary="<s:message code='PROF.MNG'/>"> <!-- 프로파일 관리 -->
			   <caption><s:message code='PROF.MNG'/></caption><!-- 프로파일 관리 -->
			   <colgroup>
			   <col style="width:20%;" />
			   <col style="width:30%;" />
			   <col style="width:20%;" />
			   <col style="width:30%;" />
			   </colgroup>
			       <tbody>   
			       		<tr>                               
			               <th ><label for="srcDbConnTrgNm"><s:message code="DB.MS" /></label></th><!-- 진단대상명 -->
			               <td colspan="1">
			                   <input  type="text"  name="srcDbConnTrgNm" id="srcDbConnTrgNm"  class="wd99p" readonly />
			               </td>
			               <th ><label for="srcDbcSchNm"><s:message code="SCHEMA.NM" /></label></th><!-- 스키마명 -->
			               <td colspan="1">
			                   <input type="text" name="srcDbcSchNm" id="srcDbcSchNm"  class="wd99p" readonly /> 
			               </td>
			           </tr>                         
			           <tr>                               
			               <th scope="row"  ><label for="srcDbcTblNm">소스 <s:message code="TBL.NM" /></label></th><!-- 테이블명 -->
			               <td colspan="3">
			                   <input type="text" name="srcDbcTblNm" id="srcDbcTblNm"  class="wd99p" readonly/>
			               </td>
			               <%-- <th scope="row"><label for="srcDbcTblLnm">소스 <s:message code="TBL.KRN.NM" /></label></th><!-- 테이블한글명 -->
			               <td>
			                   <input type="text" name="srcDbcTblLnm" id="srcDbcTblLnm"  class="wd99p" readonly />
			               </td> --%>
			           </tr>
			           <tr>                               
			               <th scope="row"  ><label for="tgtDbcTblNm">타겟 <s:message code="TBL.NM" /></label></th><!-- 테이블명 -->
			               <td colspan="3">
			                   <input type="text" name="tgtDbcTblNm" id="tgtDbcTblNm"  class="wd99p" readonly/>
			               </td>
			               <%-- <th scope="row"><label for="tgtDbcTblLnm">타겟 <s:message code="TBL.KRN.NM" /></label></th><!-- 테이블한글명 -->
			               <td>
			                   <input type="text" name="tgtDbcTblLnm" id="tgtDbcTblLnm"  class="wd99p" readonly />
			               </td> --%>
			           </tr>
			       </tbody>
			     </table>   
			</div>
			</fieldset>
			</form>
	</div>
<%-- 	<%@include file="dtl/prf_dtl.jsp" %> --%>
	<!--  데이터셋 상세 끝 -->

<%-- 	<div style="clear:both; height:10px;"><span></span></div> --%>
	<!-- 버튼영역  -->
<%-- 	<tiles:insertTemplate template="/WEB-INF/decorators/buttonProfile.jsp" /> --%>
	    <!-- 조회버튼영역  -->
	<div style="clear:both; height:10px;"><span></span></div>
	        <div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
<%--             	<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> --%>
<!-- 			    <button class="btn_search" id="btnAnaTrgSearch" 	name="btnAnaTrgSearch">조회</button> 분석실행  -->
<%-- 			    <button class="btn_search" id="btnPattenSearch" 	name="btnPattenSearch"><s:message code="DATA.PTRN" /></button> <!-- 데이터패턴 -->  --%>
<%-- 			    <button class="btn_search" id="btnLogSearch" 	name="btnPattenSearch"><s:message code="LOG" /></button> <!-- 로그 -->  --%>
<%-- 			    <button class="btn_search" id="btnSqlSearch" 	name="btnSqlSearch"><s:message code="SQL" /></button> <!-- 분석SQL --> --%>
			    
<%-- 			    <button class="btn_save" id="btnPrfSave" 	name="btnPrfSave"><s:message code="STRG" /></button> <!-- 저장 -->  --%>
				<button class="btn_rqst_new2" id="btnMatchExec" 	name="btnMatchExec">데이터매칭 실행</button>
<%-- 			    <button class="btn_delete" id="btnPrfDelete" 	name="btnPrfDelete"><s:message code="DEL" /></button> <!-- 삭제 -->  --%>
			    
			</div>
        </div>	
	
	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit">데이터매칭 컬럼 목록 </div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
	
	<div id="grid_02" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_col", "99%", "250px");</script>            
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>

</div>

<div style="width: 1%;float: left;">&nbsp;</div>

<!-- 컬럼프로파일 상세 조회 -->
<div id="text_match_result" style="width:43%; float: left;">
	<div class="stit" >데이터매칭 결과</div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
	<div id="grid_03" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_resdata", "99%", "350px");</script>            
	</div>
	<div style="clear:both; height:10px;"><span></span></div>

	<div class="stit" >데이터매칭 순위</div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
		<div id="grid_04" class="grid_01">
		     <script type="text/javascript">createIBSheet("grid_tgtdtl", "99%", "250px");</script>            
		</div>
		<div style="clear:both; height:10px;"><span></span></div>
	</div>
</div>

