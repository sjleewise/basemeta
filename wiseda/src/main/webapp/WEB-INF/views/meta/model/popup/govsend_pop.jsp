<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>


<html>
<head>
<title><s:message code="CLMN.INQ" /></title><!-- 컬럼 검색 -->

<script type="text/javascript">

var popRqst = "${search.popRqst}";

$(document).ready(function() {
	
	$("#btnSaveBottom").click(function() {
	        
	        if($("#orgNm").val() == "") {
		    	showMsgBox("INF", "기관명을 입력하세요.");
				return false;
	        } else if($("#svrIp").val() == ""){
		    	showMsgBox("INF", "서버IP를 입력하세요.");
				return false;
			}else if($("#port").val() == ""){
				showMsgBox("INF", "포트번호를 입력하세요.");
				return false;
			}else if($("#connId").val() == ""){
				showMsgBox("INF", "접속ID를 입력하세요.");
				return false;
			}else if($("#connPwd").val() == ""){
				showMsgBox("INF", "접속패스워드를 입력하세요.");
				return false;
			}else if($("#ftpPath").val() == ""){
				showMsgBox("INF", "FTP경로를 입력하세요.");
				return false;
			} else{
		    		saveChk = "Y";
		        	doAction("Save");
			}
	    }).show();
	       
    $("#btnConnTest").click(function(){ doAction("ConnTest");  });
                  
    if (popRqst == 'Y') {
        // 적용 Event Bind
        $("#popApply").click(function(){ 
        	if(checkDelIBS (grid_sheet, "<s:message code="ERR.APPLY" />")) {
				doAction("Apply");
	    	}
		}).show();
    }
    
    
  //폼 초기화 버튼 초기화...
	$('#popReset').click(function(event){
		event.preventDefault();
// 		alert("초기화버튼");
		$("form[name=frmInput]")[0].reset();
	}).hide();
  
    //======================================================
    // 셀렉트 박스 초기화
    //======================================================
        
	
    //팝업 닫기 (팝업 타입에 W(윈도우오픈), I(iframe팝업), L(레이어드팝업))
    $("div.pop_tit_close").click(function(){
//     	alert(1);
    	//iframe 형태의 팝업일 경우
    	if ("${search.popType}" == "I") {
    		parent.closeLayerPop();
    	} else {
    		window.close();
    	}
    	
    });
    
    //팝업 닫기2
    $("div.pop_tit_close, #btnCloseBottom").click(function(){
    	parent.closeLayerPop();
    });
    
});

$(window).on('load',function() {
		 
	$(window).resize();
});

// EnterkeyProcess("Search");

$(window).resize(function(){
});


function doAction(sAction)
{
        
    switch(sAction)
    {
    
	    case "Save":
	        //저장 중복처리 방지 버튼 비활성화
	    	$("#btnSaveBottom").attr("disabled", true);
	    
// 	    	if($('#').val() != null && $('#').val() != '') {
// 	    		$('#').val("U");
// 	        } else {
// 	        	$('#').val("I");
// 	        }
	    	var param = $("#frmInput").serialize();
	    	var saveJson = getform2IBSjson($("#frmInput"));
	    	var url = "<c:url value="/meta/model/regGovServerInfo.do"/>";
	    	//alert(JSON.stringify(saveJson));
			IBSpostJson2(url, saveJson, "", ibscallback2);
	
			//IBSpostJson(url, param, ibscallback);
	    	break;
             
        case "ConnTest":
        	var param = $("#frmInput").serialize();
        	var saveJson = getform2IBSjson($("#frmInput"));
        	var url = "<c:url value="/meta/model/connTestGovServer.do"/>";
        	//alert(JSON.stringify(saveJson));
			IBSpostJson2(url, saveJson, "", ibscallback2);
        	break;
    }       
}

function ibscallback2(res) {
	var result = res.RESULT.CODE;
    if(result == 0) {
    	$("#lnkSts").val(res.ETC.newLnkSts);
		$("#lnkStsCnts").val(res.ETC.newLnkStsCnts);
		//공통메세지 팝업 : 성공 메세지...
    	showMsgBox("INF", res.RESULT.MESSAGE);
    	if (!isBlankStr(cnfNextFunc)) {
    		eval(cnfNextFunc);
    		return;
    	}
    } else if (result == 401) {
    	//권한이 없어요...
    	showMsgBox("CNF", res.RESULT.MESSAGE, gologinform);
    } else {
//     	alsert("저장실패");
		//공통메시지 팝업 : 실패 메세지...
    	showMsgBox("ERR", res.RESULT.MESSAGE);
    }
}
</script>
</head>
<body>
<div class="pop_tit"> <!-- 팝업가로사이즈 여기서 조절하면 됩니다 기본은 100% -->
	<!-- 팝업 타이틀 시작 -->
	<div class="pop_tit_icon"></div>
    <div class="pop_tit_txt">연계서버접속정보</div>
    <div class="pop_tit_close"><a><s:message code="CLOSE" /></a></div> <!-- 창닫기 -->
    <!-- 팝업 타이틀 끝 -->
    
    <!-- 팝업 내용 시작 -->
    <div class="pop_content">

<!-- 검색조건 입력폼 -->
<div id="search_div">
        <div style="clear:both; height:5px;"><span></span></div>
        
        <form id="frmInput" name="frmInput" method="post">
        	<input type="hidden" id="ibsStatus" name="ibsStatus" class="wd98p" style="border:0;" value="I" />
            <fieldset>
            <legend><s:message code="FOREWORD" /></legend> <!-- 머리말 -->
            <div class="tb_basic2">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="범정부메타전송"><!-- 물리컬럼조회 -->
                   <caption>범정부메타전송</caption><!-- 물리컬럼조회 -->
                   <colgroup>
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   <col style="width:15%;" />
                   <col style="width:35%;" />
                   </colgroup>
                   
                   <tbody>       
                   <c:if test="${!empty data2.svrIp}">
                       <tr>
                       	   <th scope="row"><label for="orgNm"><s:message code="ORG.NM" /></label></th>  <!-- 기관명 -->                       
						   <td>
<%--                            		<input type="text" name="orgNm" id="orgNm" class="wd95p" readonly value="<c:out value="${data.orgNm}"/>"/> --%>
                           		<input type="text" name="orgNm" id="orgNm" class="wd95p" value="<c:out value="${data2.orgNm}"/>"/>
                           </td>                                             
                       	   <th scope="row"><label for="svrNm">서버명<%-- <s:message code="SVR.NM" /> --%></label></th>  <!-- 서버명 -->                       
						   <td>
                           		<input type="text" name="svrNm" id="svrNm" class="wd95p" value="<c:out value="${data2.svrNm}"/>"/>
                           </td>    
                       </tr> 
					   <tr style="display: none;">
					   		<th scope="row"><label for="lnkdMthd">연계방식<%-- <s:message code="LNKD.MTHD" /> --%></label></th>  <!-- 연계방식 -->                       
						   	<td colspan="3">
                           		<input type="text" name="lnkdMthd" id="lnkdMthd" class="wd98p" value="<c:out value="${data2.lnkdMthd}"/>"/>
                           	</td>
					   </tr>    
                       <tr>
                       	   <th scope="row" class="th_require"><label for="svrIp">서버IP<%-- <s:message code="SVR.IP" /> --%></label></th>  <!-- 서버IP -->                       
						   <td>
                           		<input type="text" name="svrIp" id="svrIp" class="wd95p" value="<c:out value="${data2.svrIp}"/>"/>
                           </td>                                             
                       	   <th scope="row" class="th_require"><label for="port">포트<%-- <s:message code="PORT" /> --%></label></th>  <!-- 포트 -->                       
						   <td>
                           		<input type="text" name="port" id="port" class="wd95p" value="<c:out value="${data2.port}"/>"/>
                           </td>    
                       </tr>   					            
                       <tr>
                       	   <th scope="row" class="th_require"><label for="connId">접속ID<%-- <s:message code="CONN.ID" /> --%></label></th>  <!-- 접속ID -->                       
						   <td>
                           		<input type="text" name="connId" id="connId" class="wd95p" value="<c:out value="${data2.connId}"/>"/>
                           </td>                                             
                       	   <th scope="row" class="th_require"><label for="connPwd">접속PW<%-- <s:message code="CONN.PW" /> --%></label></th>  <!-- 접속PW -->                       
						   <td>
                           		<input type="text" name="connPwd" id="connPwd" class="wd95p" value="<c:out value="${data2.connPwd}"/>"/>
                           </td>    
                       </tr>   					            
                       <tr>
                       	   <th scope="row" class="th_require"><label for="ftpPath">FTP경로<%-- <s:message code="FTP.PATH" /> --%></label></th>  <!-- FTP경로 -->                       
						   <td colspan="3">
                           		<input type="text" name="ftpPath" id="ftpPath" class="wd98p" value="<c:out value="${data2.ftpPath}"/>"/>
                           </td>                                             
                       </tr>   					            
                       <tr>
                       	   <th scope="row"><label for="lnkSts">접속상태</label></th>                       
						   <td colspan="3">
                           		<input type="text" name="lnkSts" id="lnkSts" class="wd98p" value="<c:out value="${data2.lnkSts}"/>" readonly/>
                           </td>                                             
                       </tr>   					            
                       <tr>
                       	   <th scope="row"><label for="lnkStsCnts">접속테스트오류내용</label></th>                       
						   <td colspan="3">
                           		<input type="text" name="lnkStsCnts" id="lnkStsCnts" class="wd98p" value="<c:out value="${data2.lnkStsCnts}"/>" readonly/>
                           </td>                                             
                       </tr>                                            
                   </c:if>
                   <c:if test="${empty data2.svrIp}">
                       <tr>
                       	   <th scope="row"><label for="orgNm"><s:message code="ORG.NM" /></label></th>  <!-- 기관명 -->                       
						   <td>
<%--                            		<input type="text" name="orgNm" id="orgNm" class="wd95p" readonly value="<c:out value="${data.orgNm}"/>"/> --%>
                           		<input type="text" name="orgNm" id="orgNm" class="wd95p" />
                           </td>                                             
                       	   <th scope="row"><label for="svrNm">서버명<%-- <s:message code="SVR.NM" /> --%></label></th>  <!-- 서버명 -->                       
						   <td>
                           		<input type="text" name="svrNm" id="svrNm" class="wd95p"/>
                           </td>    
                       </tr>       
					   <tr style="display: none;">
					   		<th scope="row"><label for="lnkdMthd">연계방식<%-- <s:message code="LNKD.MTHD" /> --%></label></th>  <!-- 연계방식 -->                       
						   	<td colspan="3">
                           		<input type="text" name="lnkdMthd" id="lnkdMthd" class="wd98p"/>
                           	</td>
					   </tr>    
                       <tr>
                       	   <th scope="row" class="th_require"><label for="svrIp">서버IP<%-- <s:message code="SVR.IP" /> --%></label></th>  <!-- 서버IP -->                       
						   <td>
                           		<input type="text" name="svrIp" id="svrIp" class="wd95p"/>
                           </td>                                             
                       	   <th scope="row" class="th_require"><label for="port">포트<%-- <s:message code="PORT" /> --%></label></th>  <!-- 포트 -->                       
						   <td>
                           		<input type="text" name="port" id="port" class="wd95p"/>
                           </td>    
                       </tr>   					            
                       <tr>
                       	   <th scope="row" class="th_require"><label for="connId">접속ID<%-- <s:message code="CONN.ID" /> --%></label></th>  <!-- 접속ID -->                       
						   <td>
                           		<input type="text" name="connId" id="connId" class="wd95p"/>
                           </td>                                             
                       	   <th scope="row" class="th_require"><label for="connPwd">접속PW<%-- <s:message code="CONN.PW" /> --%></label></th>  <!-- 접속PW -->                       
						   <td>
                           		<input type="text" name="connPwd" id="connPwd" class="wd95p"/>
                           </td>    
                       </tr>   					            
                       <tr>
                       	   <th scope="row" class="th_require"><label for="ftpPath">FTP경로<%-- <s:message code="FTP.PATH" /> --%></label></th>  <!-- FTP경로 -->                       
						   <td colspan="3">
                           		<input type="text" name="ftpPath" id="ftpPath" class="wd98p"/>
                           </td>                                             
                       </tr>   					            
                       <tr>
                       	   <th scope="row"><label for="lnkSts">접속상태</label></th>                       
						   <td colspan="3">
                           		<input type="text" name="lnkSts" id="lnkSts" class="wd98p" readonly/>
                           </td>                                             
                       </tr>   					            
                       <tr>
                       	   <th scope="row"><label for="lnkStsCtns">접속테스트오류내용</label></th>                       
						   <td colspan="3">
                           		<input type="text" name="lnkStsCtns" id="lnkStsCtns" class="wd98p" readonly/>
                           </td>                                             
                       </tr> 
                       </c:if>  					            
                   </tbody>
                 </table>   
            </div>
            </fieldset>
            
        </form>
		<div style="clear:both; height:10px;"><span></span></div>
        
         <!-- 조회버튼영역  -->
<%--         <tiles:insertTemplate template="/WEB-INF/decorators/buttonPop.jsp" /> --%>
		<div id="" style="text-align: center;">
	    	<button class="btn_frm_save btn_colse" id="btnConnTest" type="button">접속테스트</button>    
	    	<button class="btn_frm_save btn_colse" id="btnSaveBottom" type="button">저장</button>    
	    	<button class="btn_frm_save btn_colse" id="btnCloseBottom" type="button">닫기</button>        
		</div>
</div>
<div style="clear:both; height:5px;"><span></span></div>
        
    <!-- 팝업 내용 끝 -->
</div>
</body>
</html>