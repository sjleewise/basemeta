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

$(document).ready(function() {
	
// 	alert('${search.rqstNo}');
	
	//필수입력항목입니다. 내용을 입력해 주세요. 
	var requiremessage = "<s:message code='VALID.PRFREQUIRED' />";
	//폼검증

	
	//프로파일 종류 컬럼분석CD 강제 셋팅
	//$("#prfKndCd").val("PC01");
	
    // 조회 Event Bind
    $("#btnAnaTrgSearch").click(function(){ doAction("SearchAnaTrgTbl");  });
    
    //도메인 저장...
    $("#btnSaveDmn").click(function(){
    	doAction("SaveDmnResult");
    }).show();
    

    $("#btnRefresh").click(function() {
    	doAction("SearchAnaTrgCol", refParam);
    });
    
    $("#btnAnaTbl").click(function() {
    	doAction("regSchGetSummary");
    });
    
    $("#btnAna").click(function() {
    	if(grid_col.CheckedRows('ibsCheck') == 0) {
    		/* 기존소스_bak 181019  */
    		/* alert("체크된 행이 없음"); */
    		
    		alert("<s:message code='NO.CHK.ROW' />");
    		return;
    	}
    	
    	/* var cnt = 0;
    	
    	for(i=1; i<=grid_col.GetDataLastRow(); i++) {
    		if(grid_col.GetCellValue(i, 'ibsCheck') == 1 && grid_col.GetCellValue(i, 'varType') == 'categorical') {
    			cnt++;
    		}
    	}

    	if(cnt == 0) { */
    		doAction("regSchHistogram");
    	//}
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
	
// 	$("#frmSearch #schDbConnTrgId").find("option").remove().end();
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
	
	$("#frmSearch #schDbSchId").change(function() {
		var url = "<c:url value='/advisor/prepare/textcluster/getTblList.do' />";
		
		var param = new Object();
		param.srcDbConnTrgId = $("#schDbConnTrgId option:selected").val();
		param.srcDbcSchId = $("#schDbSchId option:selected").val();
		
		$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: replacerXssParam(param),
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
	setibsheight($("#grid_02"));
	
	<c:if test="${search.rqstNo != null and search.rqstNo != '' }">
	doAction("SearchAnaTrgTbl", "${search.rqstNo}");
	
	</c:if>
	
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
                ];
					//No|상태|선택|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|스키마명|테이블명|
					//테이블한글명|데이터셋ID|데이터셋(논리명)|데이터셋(물리명)|summary여부|메타연계분석
        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
                    {Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                    {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
					{Type:"CheckBox", Width:40, SaveName:"ibsCheck",  Align:"Center", Edit:1, Hidden:0, Sort:0},
                    {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:80,   SaveName:"dbSchPnm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0}, 
                    {Type:"Text",   Width:100,  SaveName:"dbcTblKorNm",    	Align:"Left", Edit:0, Hidden:0},
                    {Type:"Text",   Width:100,  SaveName:"daseId",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"daseLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                    {Type:"Text",   Width:100,  SaveName:"dasePnm",    	Align:"Left", Edit:0, Hidden:1},
                    {Type:"Text",   Width:60,  SaveName:"summaryYn"  , Align:"Center", Edit:0, Hidden:0}, 
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
    
	//진단대상 컬럼 grid
   	with(grid_col){
   		
   		var cfg = {SearchMode:2,Page:100};
           SetConfig(cfg);
           
           var headers = [
                       
                       {Text:"<s:message code='BDQ.HEADER.SUMMARYDS.LST' />", Align:"Center"}
                   ];
						//Pos|선택|상태|타겟변수여부|변수ID|프로파일ID|주제영역ID|주제영역명|진단대상ID|진단대상명|스키마ID|
						//스키마명|테이블명|컬럼명|컬럼한글명|Pk여부|데이터타입|변수타입|결과그래프종류|추천도메인|추천도메인확률|
						//최종도메인|Null여부|Default|메타연계분석|최소값|최대값
           var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
           
           InitHeaders(headers, headerInfo); 

           var cols = [                        
                   {Type:"Int",   Width:40,   SaveName:"ord",    	Align:"Center", Edit:0},
                   {Type:"CheckBox",   Width:40,   SaveName:"ibsCheck",    	Align:"Center", Edit:1},
                   {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"tgtVarYn",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"anlVarId",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"prfId",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"subjId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"subjLnm",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbConnTrgPnm",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbSchId",    	Align:"Left", Edit:0, Hidden:1}, 
                   {Type:"Text",   Width:100,  SaveName:"dbSchPnm",    	Align:"Left", Edit:0, Hidden:1},                    
                   {Type:"Text",   Width:100,  SaveName:"dbcTblNm",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:120,  SaveName:"dbcColNm",    	Align:"Left", Edit:0}, 
                   {Type:"Text",   Width:120,  SaveName:"dbcColKorNm",    	Align:"Left", Edit:0, Hidden:0},
                   {Type:"Text",   Width:70,   SaveName:"pkYn",    	Align:"Center", Edit:0, Hidden:1},
                   {Type:"Text",   Width:100,  SaveName:"dataType",    	Align:"Left", Edit:0},
                   {Type:"Text",   Width:100,  SaveName:"varType",    	Align:"Left", Edit:0, Hidden:0},
                   {Type:"Text",   Width:100,  SaveName:"histoYn",    	Align:"Center", Edit:0, Hidden:0},
                   {Type:"Text",   Width:100,  SaveName:"dmnPdt",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Float",   Width:100,  SaveName:"dmnPrb",    	Align:"Center", Edit:0, Hidden:1, Format:"#0.0"},
                   {Type:"Combo",   Width:80,  SaveName:"dmngNm",    	Align:"Left", Edit:0, Hidden:0},
                   {Type:"Text",   Width:80,   SaveName:"objDesc",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"nullYn",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"defltVal",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"metaAsscAnly",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"dmnMinVal",    	Align:"Left", Edit:0, Hidden:1},
                   {Type:"Text",   Width:80,   SaveName:"dmnMaxVal",    	Align:"Left", Edit:0, Hidden:1},
               ];
       
       //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
       InitColumns(cols);
       //콤보 목록 설정...
	   /* 기존소스_bak 181019  */
       /* SetColProperty("dmngNm", {ComboText: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그', ComboCode: 'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'}); */
       
       SetColProperty("dmngNm", {ComboText: "<s:message code='BDQ.HEADER.TEXTCLUST.RQST3' />", ComboCode: "<s:message code='BDQ.HEADER.TEXTCLUST.RQST3' />"});
// 	     'ID|금액|날짜|내용|명칭|번호|수|연락처|율|코드|플래그'

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

        	grid_col.RemoveAll();
        	histo_grid.RemoveAll();
        	
//         	grid_tbl.DoSearch("<c:url value="/dq/criinfo/anatrg/getPrfTblLst.do" />", param);
        	grid_tbl.DoSearch("<c:url value="/advisor/prepare/dataset/getDataSetList.do" />", param);
        	break;
        	
       	/*진단대상 컬럼 조회*/
        case "SearchAnaTrgCol":

        	//grid_col.DoSearch("<c:url value="/advisor/prepare/dataset/getAanVarList.do" />", aparam);

        	grid_col.DoSearch("<c:url value="/advisor/prepare/dataset/getDaseColList.do" />", aparam);

        	refParam = aparam;
        	break;
        
        //테이블의 summary를 조회해서 저장한다....
        case "GetSummary":
        	var url = "<c:url value="/advisor/prepare/summary/regSummaryDataset.do"/>";
			//var param = ""; //$("#mstFrm").serialize();
			IBSpostJson2(url, SaveJson, aparam, ibscallback);
        	break;
        case "regSchGetSummary":
        	
	   		var row = grid_tbl.GetSelectRow();
	   		
	        var rowjson =  grid_tbl.GetRowJson(row);
     
   			var tmparr = new Array();

   			for(i=1; i<=grid_tbl.GetDataLastRow(); i++) {
   				if(grid_tbl.GetCellValue(i, 'ibsCheck') == 1) {
										   	   				
			   		var row = i; //grid_col.GetSelectRow();
			   		
			        var rowjson =  grid_tbl.GetRowJson(row);
			        
		   			var shdJobNm = '[Summary]' + rowjson.dbcTblNm ;
		
		   			var temp = {  'shdJobId': rowjson.daseId
		   						, 'shdJobNm' : shdJobNm
		   						, 'etcJobNm' : shdJobNm
		   						, 'shdJobKndCd':'SM'
		   						, 'dbcTblNm' : rowjson.dbcTblNm
		   						, 'dbSchId' : rowjson.dbSchId
		   	   				   };
		
			   		tmparr.push(temp); 
   				}
   			}

   			SaveJson = {"data": tmparr};


			var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
        	
   			var param = "";

   			param +=  "&shdKndCd=SM"; //파이썬 실행 (Summary)
   			param +=  "&dbSchId=" +  rowjson.dbSchId;
   			param +=  "&dbcTblNm=" + rowjson.dbcTblNm;

   			//alert(JSON.stringify(SaveJson));
   			
   		    IBSpostJson2(urls, SaveJson, param, ibscallback);
   		    
        	break;
        //컬럼의 histogram를 조회한다.
        case "getHistogram":
        	var url = "<c:url value="/advisor/prepare/summary/gethistodtl.do"/>";
			var param = ""; //$("#mstFrm").serialize();

	 		varTyp = grid_col.GetCellValue(grid_col.GetSelectRow(),'varType');

	 		if(varTyp == 'numeric') {
	 			$("#grid_histo").css('display', 'block');
	 		} else {
	 			$("#grid_histo").css('display', 'none');
	 		}
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
        case "regSchHistogram": 
           //분석요청
        	var urls = '<c:url value="/commons/damgmt/schedule/ajaxgrid/insertSchedule.do"/>';
   			var param = "&shdKndCd=HS"; //파이썬 실행 (히스토그램)

   			var tmparr = new Array();
   			
   			for(i=1; i<=grid_col.GetDataLastRow(); i++) {
   				if(grid_col.GetCellValue(i, 'ibsCheck') == 1) {
										   	   				
			   		var row = i; //grid_col.GetSelectRow();
			   		
			        var rowjson =  grid_col.GetRowJson(row);
			       
			        if(rowjson.anlVarId == "") {
			        	/* 기존소스_bak 181019  */
			        	/* showMsgBox("ERR","Summary분석 후 실행 가능합니다.");  */
			        	
			        	showMsgBox("ERR","<s:message code='ERR.SUMMARYDS.LST' />"); 
						return;
					}
					
		   			var shdJobNm = "<s:message code='HSTO' />" + rowjson.dbcTblNm +"."+ rowjson.dbcColNm;
		
		   			var temp = {  'shdJobId': rowjson.anlVarId
		   						, 'shdJobNm' : shdJobNm
		   						, 'etcJobNm' : shdJobNm
		   						, 'shdJobKndCd':'HS'};
		
			   		tmparr.push(temp);
   				}
   			}

   			SaveJson = {"data": tmparr};
   			
   			IBSpostJson2(urls, SaveJson, param, ibscallback);
   		    
        	break;
        case "DmnPdtExec":
        	var SaveJson = grid_tbl.GetSaveJson(0, "ibsCheck");
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (SaveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(SaveJson.data.length == 0) return;
			
			var url = "<c:url value="/advisor/prepare/domain/regdmnpredict.do"/>";
			var param = ""; //$("#mstFrm").serialize();
			IBSpostJson2(url, SaveJson, param, ibscallback);
        	break;
        case "SaveDmnResult":
        	//변경사항이 있는지 확인한다.....
        	var rows = grid_col.IsDataModified();
        	if(!rows) {
//         		alert("저장할 대상이 없습니다...");
        		/* 기존소스_bak 181019  */
        		/* showMsgBox("ERR", "변경한 변수가 없습니다.<br>목록에서 변수내용을 변경 후 실행해 주십시요."); */
        		
        		showMsgBox("ERR", "<s:message code='ERR.SUMMARYDS.LST2' />");
        		return;
        	}
        	
        	
        	var SaveJson = grid_col.GetSaveJson(0);
	    	//2. 처리대상 행이 없는 경우 리턴한다.
// 			alert(saveJson.Code); 
			if (SaveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
												   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
												   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
// 	    	if(SaveJson.data.length == 0) return;
			
			var url = "<c:url value="/advisor/prepare/domain/regdmnresult.do"/>";
			var param = ""; //$("#mstFrm").serialize();
			IBSpostJson2(url, SaveJson, param, ibscallback);
        	
        	break;
        	
        case "DmnPredictResult":
        	//도메인 판별 결과 조회 화면으로 이동...
        	var url = "<c:url value="/advisor/prepare/domain/dmnpredict_lst.do"/>";
			var param = "rqstNo="+$("#frmDataSet #rqstNo").val(); //$("#mstFrm").serialize(); 
        	location.href =  url+"?"+param;
        	
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
					var param = "&schDbConnTrgId=" + rowjson.dbConnTrgId; 
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
	
	grid_col.FitColWidth();
	
// 	grid_prf.FitColWidth();
	
}


function loadColDetail(param){
	ajax2Json('<c:url value="/dq/profile/getAnaTrgColDetail.do"/>', param, setAnaTgtDtl);
}

function grid_tbl_OnClick(row, col, value, cellx, celly) {
	if(row < 1 || grid_tbl.GetColEditable(col)) return;
	
	//그리드 선택 데이터 변수 setting
	var rowjson =  grid_tbl.GetRowJson(row);
	
	$("#frmDataSet #dbConnTrgPnm").val(rowjson.dbConnTrgPnm);
	$("#frmDataSet #dbSchPnm").val(rowjson.dbSchPnm);
	$("#frmDataSet #dbcTblNm").val(rowjson.dbcTblNm);
	$("#frmDataSet #dbcTblKorNm").val(rowjson.dbcTblKorNm);
	
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
	    param += "&daseId="        + rowjson.daseId;
// 	    param += "&metaAsscAnly="  + grid_tbl.GetCellValue(row,"metaAsscAnly");
//  	    param += "&schDbcColNm="   + $("form[name=frmSearch] input[name='schDbcColNm']").val();
//  	    param += "&schRegYn="      + $("form[name=frmSearch] select[name='schRegYn']").val();
 	    //테이블 컬럼 프로파일 구분
//  	    param += "&tblColGb=PC";
 	     
	 //컬럼목록 조회
	 doAction("SearchAnaTrgCol", encodeURI(param));
	
	 //summary 없는경우만 조회 및 저장....
	 //if (rowjson.summaryYn == 'N' && rowjson.daseId != ""){
	 
	 if (rowjson.summaryYn == 'N' ){

		 //doAction("GetSummary", param);

		 //doAction("regSchGetSummary", param); 

	 }
		
}

function grid_col_OnClick(row, col, value, cellx, celly) {
	if(row < 1 || grid_col.GetColEditable(col)) return;
	
	
	//그리드 선택 데이터 변수 setting
	var rowjson =  grid_col.GetRowJson(row);
	var param = "objId="+rowjson.prfId;
	var param1 = "anlVarId="+rowjson.anlVarId
		param1 += "&dbSchId="+rowjson.dbSchId;
		param1 += "&dbTblNm="+rowjson.dbcTblNm;
		param1 += "&dbColNm="+rowjson.dbcColNm;

	$("div#vardtl_stit").text(rowjson.dbcColNm + " " +"<s:message code='DTL.INFO' />");/* 상세정보 */
	
	//컬럼분석 프로파일 id로 확인...
	$('div#colanaresdtl').load('<c:url value="/dq/report/ajaxgrid/getColAnaResDtl.do"/>', encodeURI(param), function(){});
	//컬럼의 summary 조회
	$('div#summary_ds_dtl').load('<c:url value="/advisor/prepare/summary/getsummary_dtl.do"/>', encodeURI(param1), function(){});
	
	param += "&objIdCol=PRF_ID";
	param += "&erDataSnoCol=ESN_ER_DATA_SNO";
	param += "&colNm=, COL_NM1, COL_NM2";
	param += "&objErrTbl=WAM_PRF_ERR_DATA";
// 	param += "&objDate=20170919140100";
	getDataPattern(encodeURI(param));
	
	//히스토그램 정보가 있을 경우 내용 조회 없을 경우 변수타입에 따라 히스토그램 grpc 호출 및 저장
// 	var histoyn = rowjson.histoYn; 
	if ("N" == rowjson.histoYn && "NUMBER" == rowjson.dataType) {
		//histo grpc를 호출해서 저장한다.
		//doAction("SaveHistogram", param1);
		
		//2018.05.15 자동 분석 실행 주석 처리
		//doAction("regSchHistogram", param1);  
	} else {
		//히스토그램 내용을 조회한다.
		doAction("getHistogram", encodeURI(param1));
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
			}
			
			switch(dmnnm) {
			case '코드':
			case '플래그':
			case '날짜':
				grid_col.SetCellValue(i, "varType", "categorical");
				break;
			}
		}
		
		for(i=1; i<=grid_col.GetDataLastRow(); i++) {
			var histo = grid_col.GetCellValue(i, 'histoYn');
			var varTyp = grid_col.GetCellValue(i, 'varType');
			
			if(histo == 'Y') {
				if(varTyp == 'numeric') {
					grid_col.SetCellValue(i, 'histoYn', "<s:message code='HSTO2' />");/* 히스토그램 */
				} else if(varTyp == 'categorical') {
					grid_col.SetCellValue(i, 'histoYn', "<s:message code='BAR.GRPH' />");/* 막대그래프 */
				} else {
					grid_col.SetCellValue(i, 'histoYn', "<s:message code='BAR.GRPH' />");/* 막대그래프 */
				}
			} else {
				grid_col.SetCellValue(i, 'histoYn', '');
			}
		}
	}
}



</script>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 데이터셋 목록 검색 -->
<div id="anatrg" style="width: 25%; float: left;">

<div class="stit"><s:message code='DATA.SET.LST' /></div><!--검색조건--><!-- 데이터셋 목록 -->
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
	                       			<!-- <input type="text" name="schDbcTblNm" id="schDbcTblNm" class="wd98p"/> -->
	                       			<select id="schDbcTblNm" name="schDbcTblNm" class="wd98p"></select>
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
			    <button class="btn_search" id="btnAnaTrgSearch" 	name="btnAnaTrgSearch"><s:message code='INQ' /></button> <!-- 조회 --> 
			    <button class="btn_search" id="btnAnaTbl" 			name="btnAnaTbl">분석요청</button> <!-- 분석요청 --> 
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
<div id="colprf" style="width:43%; float: left;">
<div class="stit"><s:message code='DATA.SET' /></div><!--데이터셋-->
<div style="clear:both; height:5px;"><span></span></div>
   <!-- 데이터셋 상세 -->
   	<div id="searchDsDtl_div" >
		
	 	<form id="frmDataSet" name="frmDataSet" method="post">
			<input type="hidden" name="rqstNo" id="rqstNo"  />
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

<%-- 	<div style="clear:both; height:10px;"><span></span></div> --%>
	<!-- 버튼영역  -->
<%-- 	<tiles:insertTemplate template="/WEB-INF/decorators/buttonProfile.jsp" /> --%>
	
	
	<div style="clear:both; height:10px;"><span></span></div>
	<div class="stit"><s:message code='CLMN.VRB.LST' /></div><!--검색조건-->
	<div style="clear:both; height:5px;"><span></span></div>
	
	<div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
<%--             	<button class="btn_search" id="btnSearch" 	name="btnSearch"><s:message code="INQ"/></button> <!-- 조회 --> --%>
<!-- 			    <button class="btn_search" id="btnAnaTrgSearch" 	name="btnAnaTrgSearch">조회</button> 분석실행  -->
<%-- 			    <button class="btn_search" id="btnPattenSearch" 	name="btnPattenSearch"><s:message code="DATA.PTRN" /></button> <!-- 데이터패턴 -->  --%>
<%-- 			    <button class="btn_search" id="btnLogSearch" 	name="btnPattenSearch"><s:message code="LOG" /></button> <!-- 로그 -->  --%>
<%-- 			    <button class="btn_search" id="btnSqlSearch" 	name="btnSqlSearch"><s:message code="SQL" /></button> <!-- 분석SQL --> --%>
			    
<%-- 			    <button class="btn_save" id="btnPrfSave" 	name="btnPrfSave"><s:message code="STRG" /></button> <!-- 저장 -->  --%>
				<!-- <button class="btn_rqst_new2" id="btnSaveDmn" 	name="btnSaveDmn">변수 저장</button> -->
				<button class="btn_search" id="btnRefresh" 	name="btnRefresh"><s:message code='REFRESH' /></button><!-- 새로고침 -->
				<button class="btn_search" id="btnAna" 	name="btnAna"><s:message code='ANLY.RQST' /></button><!-- 분석요청 -->
<%-- 			    <button class="btn_delete" id="btnPrfDelete" 	name="btnPrfDelete"><s:message code="DEL" /></button> <!-- 삭제 -->  --%>
			    
			</div>
        </div>	
	<div style="clear:both; height:5px;"><span></span></div>	       
	<div id="grid_02" class="grid_01">
	     <script type="text/javascript">createIBSheet("grid_col", "99%", "250px");</script>            
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>

</div>

<div style="width: 1%;float: left;">&nbsp;</div>

<!-- 컬럼프로파일 상세 조회 -->
<div id="vardtltab" style="width:30%; float: left;">
<div class="stit" id="vardtl_stit"><s:message code='VRB.DTL.INF' /></div><!--변수 상세 정보-->
<div style="clear:both; height:5px;"><span></span></div>

	<!-- 도메인판별상세결과 -->
	<!-- 선택 레코드의 내용을 탭처리... -->
	<div id="tabs" style='display:inline;'>
	  <ul>
	    <li id="tab-pc01"><a href="#tabs-pc01"><s:message code='DATA.DSTR' /></a></li><!--데이터분포-->
	    <li id="tab-pc02"><a href="#tabs-pc02"><s:message code='SUMR' /></a></li><!--Summary-->
	    <li id="tab-pc03"><a href="#tabs-pc03"><s:message code='CLMN.ANLY' /></a></li><!--컬럼분석-->
	    <li id="tab-pc04"><a href="#tabs-pc04"><s:message code='DATA.PTRN' /></a></li><!--데이터패턴-->
	  </ul>
	  <div id="tabs-pc01">
<!-- 	  <div id="datasethisto"></div> -->
	  	<%@include file="histogram_dtl.jsp" %>
	  </div>
	  <div id="tabs-pc02">
	  <div id="summary_ds_dtl"></div>
	  </div>
	  <div id="tabs-pc03">
	  <div id="colanaresdtl"></div>
	  </div>
	  <div id="tabs-pc04">
	  	<%@include file="../domain/colpc01dataptr_dtl.jsp" %>
	  </div>

	 </div>
	<!-- 프로파일 종류별 텝 끝 -->

</div>

