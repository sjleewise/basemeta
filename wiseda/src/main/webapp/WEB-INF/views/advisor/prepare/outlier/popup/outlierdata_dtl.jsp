
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page import="kr.wise.commons.WiseMetaConfig"%>


<script type="text/javascript">

$(document).ready(function() {
	$("#btnOtlynUpdate").click(function() {
		doAction2('SaveYn');
	});
	
	$("#btnUpdateOutlier").click(function() {
		doAction2('SaveRpl');
	});
	
	setCodeSelect("OTL_TYP_CD", "L", $("#otlRpl"));
});



function initoutdataGrid(){

    with(grid_outdata){
    	
   		var cfg = {SearchMode:2,Page:100}; 
        SetConfig(cfg);
      
        
        var headText = "";
        if ($("#frmSearch #algPnm").val() == "Box Plot") {
        	headText = "컬럼값";
        } else {
        	headText = "${headerVO.headerText}";
        }
        var headers = [
                    /* {Text:"No|상태|컬럼ID|컬럼명|컬럼수|"+headText+"|이상값여부|비고", Align:"Center"} */
                    
                    /* 기존소스_bak 181017 */
                    /* {Text:"No|상태||컬럼ID|컬럼명|컬럼수|otlSno|"+headText+"|이상값여부|otlRpl", Align:"Center"} */
                    
                    {Text:"<s:message code='BDQ.HEADER.OUTLIERDATA.DTL'/>"+headText+"<s:message code='BDQ.HEADER.OUTLIERDATA.DTL2'/>", Align:"Center"}
                ];

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		
        InitHeaders(headers, headerInfo); 

        var cols = [                        
        		{Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"otlDtcId",    	Align:"Left", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"anlVarId",    	Align:"Left", Edit:0, Hidden:1},
                {Type:"Text",   Width:150,  SaveName:"anlVarNm",    	Align:"Left", Edit:0, Hidden:0},
                {Type:"Text",   Width:100,  SaveName:"colCnt",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"otlSno",    	Align:"Center", Edit:0, Hidden:1},
            ];
        
        //그리드 SaveName 설정
        var HeaderCnt = ${headerVO.colCnt};

        /*
        if("${headerVO.colCnt}" == ""){ 
        	HeaderCnt = 0;
        }else{
        	HeaderCnt = ${headerVO.colCnt};
        }
        */
        
		var colNm = "";
		
		for(var i = 1; i <= HeaderCnt; i++){
			colNm = "colNm" + i;
			cols.push(  {Type:"Text", Width:100,  SaveName:colNm, Align:"Center", Edit:0}  ) ;
		} 
		cols.push(  {Type:"Combo",   Width:100,  SaveName:"otlYn",    	Align:"Center", Edit:1, Hidden:0, DefaultValue:"N"}  ) ;
		cols.push(  {Type:"Combo",   Width:100,  SaveName:"otlRpl",    	Align:"Center", Edit:0, Hidden:1}  ) ;
    
	    //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
	    InitColumns(cols);
	    //콤보 목록 설정...
		
	    /* 기존소스_bak 181017 */
	    /* SetColProperty("otlYn", {ComboText: '예|아니요', ComboCode: 'Y|N'});
		SetColProperty("otlRpl", {ComboText: '|최대값|최소값|무효화|삭제', ComboCode: '|01|02|03|04'}); */
	    
	    SetColProperty("otlYn", {ComboText: "<s:message code='BDQ.HEADER.OUTLIERDATA.DTL3'/>", ComboCode: "<s:message code='BDQ.HEADER.OUTLIERDATA.DTL4'/>"});
		SetColProperty("otlRpl", {ComboText: "<s:message code='BDQ.HEADER.OUTLIERDATA.DTL5'/>", ComboCode: "<s:message code='BDQ.HEADER.OUTLIERDATA.DTL6'/>"});
	
	    //InitComboNoMatchText(1, "예");
	    
	    //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
	    
	    FitColWidth();  
	    SetExtendLastCol(1); 
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_outdata);
    //===========================
    	
}

function doAction2(param) {
	switch(param) {
	case 'SaveYn':
		//변경사항이 있는지 확인한다.....
    	var rows = grid_outdata.IsDataModified();
    	if(!rows) {
			//alert("저장할 대상이 없습니다...");
    		showMsgBox("ERR", "변경한 이상값여부가 없습니다.");
    		return;
    	}
    	
    	
    	var SaveJson = grid_outdata.GetSaveJson(0);
    	//2. 처리대상 행이 없는 경우 리턴한다.
		//alert(saveJson.Code); 
		if (SaveJson.Code == "IBS000") return; // 처리대상이 없는 경우 Code : "IBS000", Message : "NoTargetRows" 
											   // 필수입력 누락인 경우 Code : "IBS010", Message : "KeyFieldError"
											   // Validation 오류인 경우 Code : "IBS020", Message : "InvalidInputError"
		
		var url = "<c:url value="/advisor/prepare/outlier/updateOtlYn.do"/>";
		var param = ""; //$("#mstFrm").serialize();
		IBSpostJson2(url, SaveJson, param, ibscallback);
    	
    	break;
    	
	case 'SaveRpl':
		var url = "<c:url value="/advisor/prepare/outlier/updateOtlRpl.do"/>";
		
		var param = new Object();
		param.otlDtcId = grid_outdata.GetCellValue(1, 'otlDtcId');
		param.otlRpl = $("#otlRpl").val();
		param.anlVarId = grid_outdata.GetCellValue(1, 'anlVarId');
		param.colCnt = grid_outdata.GetCellValue(1, 'colCnt');

		$.ajax({
			url: url,
			async: false,
			type: "POST",
			data: replacerXssParam(param),
			dataType: 'json',
			beforeSend: function () {
				// 처리중이니 잠시 기다려 주십시요.
				showMsgBox("PRC", gMSG_PRC_WAIT);
			},
			success: function(res) {
				var result = res.RESULT.CODE;
			    if(result == 0) {
					//공통메세지 팝업 : 성공 메세지...
			    	showMsgBox("INF", res.RESULT.MESSAGE);
			    	if (!isBlankStr(cnfNextFunc)) {
			    		eval(cnfNextFunc);
			    		return;
			    	}
			    	if (postProcessIBS != null) {
			    		postProcessIBS(res);
			    	}
			    } else if (result == 401) {
			    	//권한이 없어요...
			    	showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
			    } else {
					//공통메시지 팝업 : 실패 메세지...
			    	showMsgBox("ERR", res.RESULT.MESSAGE);
			    }
			}
		});
		
		break;
	}
}


function setoutdatagridcol(rowjson){
	if ("단변량" == rowjson.algLnm) {
		//단변량 에 맞게 셋팅한다.
	} else {
		//다변량에 맞게 셋팅한다. 여기서 할건 아닌거 같고...
	}	
}


function grid_outdata_OnLoadData(data) {
// 	if(data != null) {
		//차트를 그리자...
// 		var jsondata = jQuery.parseJSON(data)
// 	}
	
}


//추정 이상값 데이터 조회이벤트...
function grid_outdata_OnSearchEnd(code, message, stCode, stMsg) {

	if (stCode == 401) {
		showMsgBox("CNF", "<s:message code="CNF.LOGIN" />", gologinform);
		return;
	}
	if(code  < 0) {
		showMsgBox("ERR", "<s:message code="ERR.SEARCH" />");
		return;
	}else{
		setibsheight($("#grid_out01"));
		
		var colnms = grid_outdata.GetEtcData("colnms");
// 		alter(colnms);
		//컬럼헤드를 정리한다....
		for(i=1;i<11;i++) {
			grid_outdata.SetColHidden("colNm"+i , 1);
		}

		if(!isBlankStr(colnms)) {
			//컬럼겟수만큼 보여주고 나머지는 히든....
			grid_outdata.SetColHidden("anlVarNm" , 1);
			var chktemp = colnms.split("|");
	 		var cnt  = chktemp.length;
	 		for(i=0;i<cnt;i++) {
			grid_outdata.SetColHidden("colNm"+(i+1) , 0);
	 			
	 		}
			
		} else {
			//단병량에 맞는 셋팅을 한다....
			grid_outdata.SetColHidden("anlVarNm" , 0);
			grid_outdata.SetColHidden("colNm1" , 0);
		}
		
		grid_outdata.SetEtcData("colnms", "");
		grid_outdata.FitColWidth();
		grid_outdata.SetExtendLastCol(1);

		for(i=1; i<=grid_outdata.GetDataLastRow(); i++) {
			if(grid_outdata.GetCellValue(i, 'otlYn')=='Y') {
				$("#otlRpl").val(grid_outdata.GetCellValue(i, 'otlRpl'));
				return;
			}
		}
	}
}
</script>

<div class="stit"><s:message code='ESLO.ANRM.VAL.DATA'/></div><!--컬럼분석 상세정보-->	<!-- 추정 이상값 데이터 코드값으로 수정 181017 -->
<div style="clear:both; height:5px;"><span></span></div>
		<div id="outlier_update_div" >
	        
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
	                           <th scope="row" class="th_require"><label for="otlRpl"><s:message code='ANRM.VAR.TRTT'/></label></th><!--진단대상명--><!-- 이상값 처리 코드값으로 수정 181017 -->
	                           <td>
	                           		<select id="otlRpl"  name="otlRpl" class="wd98p">
	                           		<option value=""><s:message code='ANRM.VAR.TRTT.SLT'/></option><!--전체--><!-- 이상값 처리방법을 선택하세요. 코드값으로 수정 181017 -->
								    <!-- <option value="01">최대값으로 대체</option>
								    <option value="02">최소값으로 대체</option>
								    <option value="03">이상값 무효화</option>
								    <option value="04">이상값 삭제</option> -->
	                           		</select>
	                           </td>
	                       </tr>
	                   </tbody>
	                 </table>   
	            </div>
				<div style="clear:both; height:10px;"><span></span></div>
	            </fieldset>
	            
	       
	</div>
	
	<div style="clear:both; height:10px;"><span></span></div>
	
	<div class="divLstBtn" style="display: none;">	 
        <div class="bt03">
			<button class="btn_rqst_new2" id="btnUpdateOutlier" name="btnUpdateOutlier"><s:message code='ANRM.VAR.TRTT.RQST'/></button><!-- 이상값 처리요청 코드값으로 수정 181017 -->
		</div>
		<div class="bt02">
			<button class="btn_rqst_new2" id="btnOtlynUpdate" name="btnOtlynUpdate"><s:message code='ANRM.VAR.YN.SAVE'/></button><!-- 이상값 여부 저장 코드값으로 수정 181017 -->
		</div>
    </div>		
	
<div style="clear:both; height:10px;"><span></span></div> 

<!-- 그리드 입력 입력 -->
<div id="grid_out01" class="grid_01">
     <script type="text/javascript">createIBSheet("grid_outdata", "99%", "300px");</script>            
</div>
	
<div style="clear:both; height:10px;"><span></span></div>
