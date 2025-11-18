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

$(document).ready(function() {
	
});

$(window).on('load',function() {
	initoutdataGrid();
});


$(window).resize( function(){
});


function initoutdataGrid(){
	
    with(grid_outdata){
    	
   		var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
        var headers = [
                    {Text:"<s:message code='BDQ.HEADER.OUTLIERPRF.LST5'/>", Align:"Center"}
                ];
        //No|상태|컬럼ID|컬럼명|컬럼수|컬럼1|컬럼2|컬럼3|컬럼4|컬럼5|컬럼6|컬럼7|컬럼8|컬럼9|컬럼10|이상값여부|비고

        var headerInfo = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
        
        InitHeaders(headers, headerInfo); 

        var cols = [                        
        		{Type:"Seq",    Width:50,   SaveName:"ibsSeq",      Align:"Center", Edit:0},
                {Type:"Status", Width:40,   SaveName:"ibsStatus",   Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"anlVarId",    	Align:"Left", Edit:0, Hidden:1},
                {Type:"Text",   Width:150,  SaveName:"anlVarNm",    	Align:"Left", Edit:0, Hidden:0},
                {Type:"Text",   Width:100,  SaveName:"colCnt",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm1",    	Align:"Center", Edit:0, Hidden:0},
                {Type:"Text",   Width:100,  SaveName:"colNm2",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm3",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm4",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm5",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm6",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm7",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm8",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm9",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Text",   Width:100,  SaveName:"colNm10",    	Align:"Center", Edit:0, Hidden:1},
                {Type:"Combo",   Width:100,  SaveName:"outYn",    	Align:"Center", Edit:1, Hidden:0, DefaultValue:"Y"},
                {Type:"Text",   Width:100,  SaveName:"outUdt",    	Align:"Center", Edit:0, Hidden:0},
            ];
    
    //각 컬럼의 데이터 타입, 포맷 및 기능들을 설정한다..
    InitColumns(cols);
    //콤보 목록 설정...
	SetColProperty("outYn", {ComboText: '예|아니요', ComboCode: 'Y|N'});

    InitComboNoMatchText(1, "예");
    
    //마지막 컬럼의 너비를 전체 너비에 맞게 자동으로 맞출것인지 여부를 확인하거나 설정한다
    
    FitColWidth();
    SetExtendLastCol(1); 
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_outdata);
    //===========================
    	
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

		
	}
}
</script>

<div class="stit">추정 이상값 데이터</div><!--컬럼분석 상세정보-->
<div style="clear:both; height:5px;"><span></span></div>
		<div id="outlier_update_div" >
	        <form id="frmUpdate" name="frmUpdate" method="post">
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
	                           <th scope="row" class="th_require"><label for="otlAlgId">이상값 처리</label></th><!--진단대상명-->
	                           <td>
	                           		<select id="otlAlgId"  name="otlAlgId" class="wd98p">
	                           		<option value="">이상값 처리방법을 선택하세요.</option><!--전체-->
								    <option value="01">최대/최소값으로 대체</option>
								    <option value="02">이상값 무효화</option>
								    <option value="03">이상값 삭제</option>
	                           		</select>
	                           </td>
	                       </tr>
	                   </tbody>
	                 </table>   
	            </div>
				<div style="clear:both; height:10px;"><span></span></div>
	            </fieldset>
	            
	        </form>
	</div>
<div style="clear:both; height:10px;"><span></span></div>
	<div class="divLstBtn" style="display: none;">	 
            <div class="bt03">
				<button class="btn_rqst_new2" id="btnUpdateOutlier" 	name="btnUpdateOutlier">이상값 처리요청</button>
			</div>
        </div>		
	
<div style="clear:both; height:10px;"><span></span></div>

<!-- 그리드 입력 입력 -->
<div id="grid_out01" class="grid_01">
     <script type="text/javascript">createIBSheet("grid_outdata", "99%", "400px");</script>            
</div>
	
<div style="clear:both; height:10px;"><span></span></div>
