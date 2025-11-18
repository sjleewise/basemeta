<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="kr.wise.commons.WiseMetaConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript">

var dmnginfotpJson = ${codeMap.dmnginfotp} ;

var interval = "";

$(document).ready(function() {
	
		//마우스 오버 이미지 초기화
		//imgConvert($('div.tab_navi a img'));
      

        
    	double_select(dmnginfotpJson, $("#dmngId"));
    	$('select', $("#dmngId").parent()).change(function(){
    		double_select(dmnginfotpJson, $(this));
    	});
    	
    	//$( "#tabs" ).tabs();
    	
    	// 조회 Event Bind
        $("#btnDmnSearch").click(function(){ doAction("DmnSearch");  });
});

$(window).on('load',function() {
// 	alert('window.load');
	initDmnGrid();
	

});




function initDmnGrid()
{
    
    with(grid_dmn){
    	
    	var cfg = {SearchMode:2,Page:100};
        SetConfig(cfg);
        
		var headers = [
						{Text:"<s:message code='DQ.HEADER.COLBATCH_DMN_DTL'/>", Align:"Center"}
					];
			//No.|선택|도메인ID|도메인논리명|도메인물리명|논리명기준구분|도메인그룹명|인포타입|데이터타입|데이터갈이|상위도메인ID|주제영역ID|목록엔티티ID|목록엔티티물리명|목록엔티티논리명|코드값유형코드|코드값부여방식코드|표준항목자동생성여부|데이터형식|담당사용자ID|도메인출처구분|요청번호|요청일련번호|설명|버전|등록유형코드|최초요청일시|최초요청사용자ID|요청일시|요청사용자ID|승인일시|승인사용자ID

			var headerInfo = {Sort:1, ColMove:1, ColResize:1, HeaderCheck:0};
			
			InitHeaders(headers, headerInfo); 

			var cols = [
						{Type:"Seq",	Width:40,   SaveName:"ibsSeq",		Align:"Center", Edit:0},
						{Type:"CheckBox", Width:40,   SaveName:"ibsCheck",    Align:"Center", Edit:1, Hidden:0, Sort:0},
						{Type:"Text",   Width:40,   SaveName:"dmnId", 		Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:100,   SaveName:"dmnLnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dmnPnm", 		Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:60,   SaveName:"lnmCriDs",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"dmngId",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Combo",   Width:100,   SaveName:"infotpId",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataType",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:100,   SaveName:"dataLen",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"uppDmnId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"subjId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyPnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"lstEntyLnm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Combo",   Width:100,   SaveName:"cdValTypCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:120,   SaveName:"cdValIvwCd",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Combo",   Width:140,   SaveName:"sditmAutoCrtYn",	 	Align:"Center", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"dataFrm",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"crgUserId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"dmnOrgDs",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstNo",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Text",   Width:40,   SaveName:"rqstSno",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Text",   Width:500,   SaveName:"objDescn",	 	Align:"Left", Edit:0, Hidden:0},
						{Type:"Text",   Width:40,   SaveName:"objVers",	 	Align:"Right", Edit:0, Hidden:1},
						{Type:"Combo",   Width:80,   SaveName:"regTypCd",	 	Align:"Center", Edit:0, Hidden:0},
						{Type:"Date",   Width:30,   SaveName:"frsRqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:30,   SaveName:"frsRqstUserId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:30,   SaveName:"rqstDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd"},
						{Type:"Text",   Width:30,   SaveName:"rqstUserId",	 	Align:"Left", Edit:0, Hidden:1},
						{Type:"Date",   Width:30,   SaveName:"aprvDtm",	 	Align:"Center", Edit:0, Hidden:1, Format:"yyyy-MM-dd HH:mm:ss"},
						{Type:"Text",   Width:30,  SaveName:"aprvUserId",  Align:"Left", Edit:0, Hidden:1}
					];
                    
        InitColumns(cols);
        SetColProperty("cdValTypCd", 	${codeMap.cdValTypCdibs});
		SetColProperty("cdValIvwCd", 	${codeMap.cdValIvwCdibs});
		SetColProperty("dmngId", 	${codeMap.dmngibs});
		SetColProperty("infotpId",	${codeMap.infotpibs});
		SetColProperty("regTypCd",	${codeMap.regTypCdibs});
		SetColProperty("sditmAutoCrtYn", 	{ComboCode:"N|Y", ComboText:"<s:message code='COMBO.NO.YES'/>"});
        
        //SetColHidden("rqstUserNm",1);
      	InitComboNoMatchText(1, "");
        // FitColWidth();  
        
        SetExtendLastCol(1);    
    }
    
    //==시트설정 후 아래에 와야함=== 
    init_sheet(grid_dmn);    
    //===========================
   
}
	 
function doAction(sAction)
{
        
    switch(sAction)
    {
        case "DmnSearch":
        	
			var param = $("#frmDmnSearch").serialize();
			$('#dmn_sel_title').html('');

			grid_dmn.DoSearch("<c:url value="/meta/stnd/getDomainlist.do" />", param);
			//grid_dmn.DoSearchScript("testJsonlist");
        	break;

    }       
}


</script>


<div style="clear:both; height:5px;"><span></span></div>

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div class="stit"><s:message code="DMN.INQ.COND"/></div><!--도메인 검색조건-->
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmDmnSearch" name="frmDmnSearch" method="post">
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='STRD.ITEM.INQ' />"><!-- /*표준항목조회*/
 -->
                   <caption><s:message code="STRD.ITEM.INQ.FORM"/></caption><!--표준항목 검색폼-->

                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
						<tr>
							<th scope="row"><label for="dmnLnm"><s:message code="DMN.NM" /></label></th><!--도메인명-->
							<td><input type="text" id="dmnLnm" name="dmnLnm" /></td>
							<th scope="row"><label for="infotpId"><s:message code="DMN.GRP.INFO.TY" /></label></th> <!-- 도메인그룹/인포타입 -->
							<td>
								<select id="dmngId" class="wd100" name="dmngId">
									<option value=""><s:message code="WHL" /></option><!--전체-->

								</select>
								<select id="infotpId" class="wd100" name="infotpId">
									<option value=""><s:message code="WHL" /></option><!--전체-->

							 	</select>
							</td>
						</tr>
						<tr>
							<th scope="row"><label for="dataType"><s:message code="DATA.TY" /></label></th><!--데이터타입-->
							<td>
								<select id="dataType" class="wd100" name="dataType">
									<option value="">---<s:message code="WHL" />---</option><!--전체-->
									<c:forEach var="code" items="${codeMap.dataTypeCd}" varStatus="status" >
									<option value="${code.codeCd}">${code.codeLnm}</option>
									</c:forEach>
<!-- 									<option value=""><s:message code="WHL" /></option> -->
<!-- 									<option value="VARCHAR">VARCHAR</option> -->
<!-- 									<option value="CHAR">CHAR</option> -->
<!-- 									<option value="NUMBER">NUMBER</option> -->
<!-- 									<option value="DATE">DATE</option> -->
<!-- 									<option value="TIMESTAMP">TIMESTAMP</option> -->
<!-- 									<option value="CLOB">CLOB</option> -->
<!-- 									<option value="BLOB">BLOB</option> -->
							 	</select>
							</td>
							<th scope="row"><label for="objDescn"><s:message code="CONTENT.TXT" /></label></th> <!--설명-->
							<td colspan="3"><input type="text" class="wd200" id="objDescn" name="objDescn" /></td>
						</tr>
                   </tbody>
                 </table>   
            </div>
            </fieldset>
        <div class="tb_comment">- <s:message code="MSG.DTL.INQ.WIT.ATA.COPY.CLMN.CHC" /> <span style="font-weight:bold; color:#444444;">Ctrl + C</span><s:message code="MSG.CHC.USE" /></div><!--를 사용하시면 됩니다.--><!--클릭을 하시면 상세조회를 하실 수 있습니다. 데이터를 복사하시려면 복사할 컬럼을 선택하시고-->

        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
        <div class="bt03">
        <button class="btn_search" id="btnDmnSearch" 	name="btnDmnSearch"><s:message code="INQ"/><!--조회-->
 
        </div>
</div>
<div style="clear:both; height:5px;"><span></span></div>

<!-- 그리드 입력 입력 -->
	<div class="grid_01" id="grid_01">
	     <script type="text/javascript">createIBSheet("grid_dmn", "100%", "200px");</script>            
	</div>

	<div style="clear:both; height:5px;"><span></span></div>
	
	
	<!-- 검색조건 입력폼 -->
<div id="search_div">
        
        <div class="stit"><s:message code="REG.TRGT"/></div><!--등록 대상-->
        <div style="clear:both; height:5px;"><span></span></div>
        <form id="frmSearchDmn" name="frmSearchDmn" method="post">
        	
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend><!--머리말-->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='PROF.BNDL.REG'/>"><!--프로파일 일괄등록-->
                   <caption><s:message code="COND" /></caption><!--조건-->


                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>                            
					 <tr>                   
			          <th scope="row" class="th_require"><label for="dbSchPnm"><s:message code="DBMS.SCHEMA.NM"/></label></th><!--진단대상명/스키마명-->

                      <td>
			            <select id="dbConnTrgPnm" class="" name="dbConnTrgPnm">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			            </select>
			            <select id="dbSchPnm" class="" name="dbSchPnm">
			             <option value="">---<s:message code="CHC" />---</option><!--선택-->

			             </select>
			                               </td>
			            
                           <th scope="row" class="th_require"><label for="prfKndCd"><s:message code="PROF.KIND"/>
							</label></th><!--프로파일종류-->
                           <td colspan="3">
                               <select id="prfKndCd1" class="wd150" name="prfKndCd1">
                                        <option value="PC01"><s:message code="CLMN.ANLY"/></option><!--컬럼분석-->
                                        <option value="PC02"><s:message code="CD.ANLY"/></option><!--코드분석-->
                                        <option value="PC03"><s:message code="DATE.FRMT.ANLY"/></option><!--날짜형식분석-->
                                        <option value="PC04"><s:message code="RNG.ANLY"/></option><!--범위분석-->
                                        <option value="PC05"><s:message code="STRING.PTRN.ANLY"/></option><!--문자열패턴분석-->

                                    </select>
                           </td>
                        </tr>
                       <tr>                               
                           <th scope="row"><label for="dbcTblNm"><s:message code="TBL.NM" /></label></th><!--테이블명-->

                           <td>
                               <input type="text" name="dbcTblNm" id="dbcTblNm" />
                           </td>
                           <th scope="row"><label for="objNm"><s:message code="CLMN.NM" /></label></th><!--컬럼명-->

                           <td>
                               <input type="text" name="objNm" id="objNm" />
                           </td>
                           
                       </tr>
                       
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
<!--         <div class="tb_comment"><s:message  code='ETC.COMM' /></div>  -->
        </form>
		<div style="clear:both; height:10px;"><span></span></div>

		
</div>     

<div id="profileDtl">
	  	<div id="colDtl">
	  		<%@include file="../dtl/colana_dtl.jsp" %>
	  	</div>

	  	<div id="validDtl">
	  		<%@include file="../dtl/colefva_dtl.jsp" %>
	  	</div>

	  	<div id="dateDtl">
	  		<%@include file="../dtl/coldtfrm_dtl.jsp" %>
	  	</div>
	  	
	  	<div id="rangeDtl">
	  		<%@include file="../dtl/colrng_dtl.jsp" %>
	  	</div>
	  	<div id="patternDtl">
	  		<%@include file="../dtl/colptr_dtl.jsp" %>
	  	</div>
</div>