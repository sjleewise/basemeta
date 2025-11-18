<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- <html> -->
<!-- <head> -->
<!-- <title></title> -->
<script type="text/javascript">

$(document).ready(function() {
	//#### select change event #### 
	$("#frmDbInput #orgCd").change(function(event) {
		var seletedVal = $(this).val();
		var comboObj = $("#frmDbInput #infoSysCd");
        
        var filterJson = [];
        var idxCnt = 0;

        comboObj.prev().html(comboObj.find('option:first').text());
        
		$.each(${codeMap.infoSysCdUserMapJson}, function(idx, item) {

			if(seletedVal == item.upcodeCd) {
				filterJson[idxCnt] = {codeCd:item.codeCd, codeLnm:item.codeLnm};
				idxCnt++;
			}
		});
        
		create_selectbox(filterJson, comboObj);
		//setCodeSelect3("infoSysCd", "M", "lnm", $("#frmDbInput #infoSysCd"), ["orgCd","infoSysCd"]);
    });

	$("#frmDbInput #infoSysCd,#dbConnTrgLnm").change(function(event) {
		$("#frmShdInput #shdLnm").val("");
    });

    $("#osKndCd").change(function(event) {
    	var seletedVal = $(this).val();
        
		var comboObj = $("#osVerNm");
        
        var filterJson = [];
        var idxCnt = 0;

		comboObj.prev().html(comboObj.find('option:first').text());
        
		$.each(${codeMap.osKndVersJson}, function(idx, item) {

			if(seletedVal == item.upcodeCd) {
				filterJson[idxCnt] = {codeCd:item.codeCd, codeLnm:item.codeLnm};
				idxCnt++;
			}
		});
        
		create_selectbox(filterJson, comboObj);
/* 
		var osKndNm = isBlankStr($("#osKndCd").val()) ? "" : $("#osKndCd").find('option:selected').text() ;
		$("#osVerNm").val(osKndNm); */
    });
/* 
    $("#osVersCd").change(function(event) {
    	var osKndNm = isBlankStr($("#osKndCd").val()) ? "" : $("#osKndCd").find('option:selected').text();
        var osVerNm = isBlankStr($("#osVersCd").val()) ? "" : " - "+ $("#osVersCd").find('option:selected').text();

    	$("#osVerNm").val(osKndNm + osVerNm);
    }); */

	
    $("#dbmsTypCd").change(function(event) {

        var dbmsTypCd = $(this).val();
        
        var dbmsVersJson = [];
        var tempCode = [];
        var tempCodeNm = [];

        $("#dbmsVersCd").prev().html($("#dbmsVersCd").find('option:first').text());
        
		$.each(dbmsTypeVersJson, function(key, item) {

			if(dbmsTypCd == key) {
				tempCode = item.ComboCode.split("|");
				tempCodeNm = item.ComboText.split("|");

				$.each(tempCode, function(idx, val) {
					dbmsVersJson[idx] = {codeCd:tempCode[idx], codeLnm:tempCodeNm[idx]};
				});
			}
		});
        
    	create_selectbox(dbmsVersJson, $("#frmDbInput #dbmsVersCd"));
    });

    //change 이벤트(DBMS버전)
    $("#dbmsVersCd").change(function() {
    	var dbmsTypCd = $("#dbmsTypCd").val();
		var dbmsVersCd = $("#dbmsVersCd").val();
        
		var drvrNm = ""; 
		var appendInput = "";
		var isExistsUrlParams = false;

		//STEP2 초기화
		$("tr[id^=connTrgLnkUrl_tr]").remove();
		$("#connTrgLnkUrl").val("");
		$("#dbConnAcId").val("");
		$("#dbConnAcPwd").val("");

		CONNTEST_YN = "N";
		$("#dbLnkSts").text("");

		setBtnDisabled($("#btnSubConn"), false);

		$("#dbConnAcId,#dbConnAcPwd").attr("readonly", false);
		$("#dbConnAcId,#dbConnAcPwd").removeClass("d_readonly");
		//$("#gpucFsvrId").attr("disabled",false);

		if(dbmsVersCd != "") {
			$.each(dbmsJdbcJson, function(key, item) {
				if(key == dbmsTypCd) {
					$.each(item, function(subKey, subItem) {

						isExistsUrlParams = !isBlankStr(subItem.param);

						if(isBlankStr(subItem.dbmsVersCd)) {
							//기본세팅(공통)
							initLnkUrl = subItem.lnkUrl;
							drvrNm = subItem.drvrNm;

							$.each(subItem.inputId, function(arrIdx, createId) {

								appendInput = appenInputConnTrgLnkUrl("I", arrIdx, createId, subItem, isExistsUrlParams, appendInput);
								
							});
						}

						//지정된 버전정보가 있을 경우
						if(subItem.dbmsVersCd == dbmsVersCd) {
							initLnkUrl = subItem.lnkUrl;
							drvrNm = subItem.drvrNm;

							$.each(subItem.inputId, function(arrIdx, createId) {

								appendInput = appenInputConnTrgLnkUrl("I", arrIdx, createId, subItem, isExistsUrlParams, appendInput);
								
							});
						}

					});
				}
			});

			$("#dbmsTypNm_tr").after(appendInput);

			if(!isBlankStr(appendInput)) {
				//이벤트 생성
				createChangeEventConnTrgLnkUrl();
			}
		}
		
		$("#connTrgDrvrNm").val(drvrNm);

    });

	<c:if test='${search.ibsStatus eq "U" and result.dbmsTypCd != null}'>
		$("#dbmsTypCd").change();

		<c:if test='${result.dbmsVersCd != null}'>
			$("#dbmsVersCd").val('${result.dbmsVersCd}');
	
			<c:if test='${result.dbmsTypNm == null}'>
			var dbmsTypNm = isBlankStr($("#dbmsTypCd").val()) ? "" : $("#dbmsTypCd").find('option:selected').text() ;
			var dbmsVersNm = isBlankStr($("#dbmsVersCd").val()) ? "" : $("#dbmsVersCd").find('option:selected').text() ;
				
			$("#dbmsTypNm").val(dbmsTypNm + " " + dbmsVersNm);
			</c:if>

		<c:choose>
			<c:when test="${result.connTrgLnkUrl == null or result.connTrgLnkUrl == ''}">
				$("#dbmsVersCd").change();
			</c:when>
			<c:otherwise>
			var dbmsTypCd = $("#dbmsTypCd").val();
			var dbmsVersCd = $("#dbmsVersCd").val();
	        
			var drvrNm = ""; 
			var appendInput = "";
			var isExistsUrlParams = false; //param 여부

			//STEP2 초기화
			$("tr[id^=connTrgLnkUrl_tr]").remove();
			//$("#connTrgLnkUrl").val("");
			//$("#dbConnAcId").val("");
			//$("#dbConnAcPwd").val("");

			//CONNTEST_YN = "N";
			//$("#dbLnkSts").text("");
			//setBtnDisabled($("#btnSubConn"), false);

			//$("#dbConnAcId,#dbConnAcPwd").attr("readonly", false);
			//$("#dbConnAcId,#dbConnAcPwd").removeClass("d_readonly");
			//$("#gpucFsvrId").attr("disabled",false);

			var tempJdbcIdArr = []; //연결url 태그 ID
			var tempJdbcArr = []; //연결url 파라메타제외한 구분자
			var tempJdbcUrl = "";
//"CBR" : [{dbmsVersCd:"", drvrNm: "cubrid.jdbc.driver.CUBRIDDriver", lnkUrl: "jdbc:cubrid:{ip}:{port}:{dbName}:public::", inputId: ["ip","port","dbName"], param: ["charset"]}]
			if(dbmsVersCd != "") {
				$.each(dbmsJdbcJson, function(key, item) {

					if(key == dbmsTypCd) {

						var dbmsVersCnt = item.length;
						
						$.each(item, function(subKey, subItem) {
							isExistsUrlParams = !isBlankStr(subItem.param);

							if(isBlankStr(subItem.dbmsVersCd)) {
								//기본세팅
								initLnkUrl = subItem.lnkUrl;
								drvrNm = subItem.drvrNm;
								tempJdbcUrl = subItem.lnkUrl;
								
								$.each(subItem.inputId, function(arrIdx, createId) {
									var findStr = "{"+createId+"}";	//connTrgLnkUrl_
									//여기서 부터 개발 시작!!!! 
									tempJdbcIdArr[arrIdx] = createId;
 									tempJdbcArr[arrIdx] = tempJdbcUrl.substr(0, tempJdbcUrl.indexOf(findStr));
									tempJdbcUrl = tempJdbcUrl.replace((tempJdbcArr[arrIdx]+findStr), "");

									//param 부분 남음
									if(subItem.inputId.length-1 == arrIdx && !isBlankStr(tempJdbcUrl) && isExistsUrlParams) {

										tempJdbcIdArr[arrIdx+1] = subItem.param[0];

										if( "${result.connTrgLnkUrl}".lastIndexOf(tempJdbcUrl+"?"+subItem.param[0]+"=") > -1 ) {
											tempJdbcArr[arrIdx+1] = tempJdbcUrl+"?"+subItem.param[0]+"=";
										}else {
											tempJdbcArr[arrIdx+1] = tempJdbcUrl;
										}
									}
									
									appendInput = appenInputConnTrgLnkUrl("U", arrIdx, createId, subItem, isExistsUrlParams, appendInput);
									
								});
							}
							
							if(subItem.dbmsVersCd == dbmsVersCd) {
								initLnkUrl = subItem.lnkUrl;
								drvrNm = subItem.drvrNm;

								tempJdbcUrl = subItem.lnkUrl;
								tempJdbcIdArr = [];
								tempJdbcArr = [];
								
								$.each(subItem.inputId, function(arrIdx, createId) {
									
									var findStr = "{"+createId+"}";	//connTrgLnkUrl_
									//여기서 부터 개발 시작!!!! 
									tempJdbcIdArr[arrIdx] = createId;
 									tempJdbcArr[arrIdx] = tempJdbcUrl.substr(0, tempJdbcUrl.indexOf(findStr));
									tempJdbcUrl = tempJdbcUrl.replace((tempJdbcArr[arrIdx]+findStr), "");

									//param 부분 남음
									if(subItem.inputId.length-1 == arrIdx && !isBlankStr(tempJdbcUrl) && isExistsUrlParams) {

										tempJdbcIdArr[arrIdx+1] = subItem.param[0];

										if( "${result.connTrgLnkUrl}".lastIndexOf(tempJdbcUrl+"?"+subItem.param[0]+"=") > -1 ) {
											tempJdbcArr[arrIdx+1] = tempJdbcUrl+"?"+subItem.param[0]+"=";
										}else {
											tempJdbcArr[arrIdx+1] = tempJdbcUrl;
										}
									}

									appendInput = appenInputConnTrgLnkUrl("U", arrIdx, createId, subItem, isExistsUrlParams, appendInput);
								});
							}
						});
					}
				});

				$("#dbmsTypNm_tr").after(appendInput);
				//dblinkUrl 정보 있을 경우 input 박스에 세팅 start
				
					var connTrgLnkUrl = $("#connTrgLnkUrl").val();
					var lastIdx = connTrgLnkUrl.length - 1;
					var startIdx = 0;
					var endIdx = 0;

					var tempStr = connTrgLnkUrl;
					
					$.each(tempJdbcIdArr, function(urlInputIdx, urlInputId) {
						
						startIdx = startIdx + (tempStr.indexOf(tempJdbcArr[urlInputIdx]) + (tempJdbcArr[urlInputIdx].length) ) ;
						tempStr = connTrgLnkUrl.substr(startIdx, lastIdx);
						
						//endIdx = (tempJdbcIdArr.length-1) == urlInputIdx ? lastIdx : (startIdx + (tempStr.indexOf(tempJdbcArr[urlInputIdx+1])));
						
						var valSize = 0;
						if((tempJdbcIdArr.length-1) == urlInputIdx) {
							valSize = (lastIdx+1 - startIdx);
						}else {
							valSize = tempStr.indexOf(tempJdbcArr[urlInputIdx+1]);
						}
						
						var setValue = connTrgLnkUrl.substr(startIdx, valSize);
						
						$("#connTrgLnkUrl_"+urlInputId).val(setValue);
						
					});
				//end
				
				if(!isBlankStr(appendInput)) {

					$("input[id^=connTrgLnkUrl_],#dbConnAcId,#dbConnAcPwd").keyup(function(event) {
						event.preventDefault();

						CONNTEST_YN = "N";
						$("#dbLnkSts").text("");
						setBtnDisabled($("#btnSubConn"), false);
					});

					//이벤트 생성
					createChangeEventConnTrgLnkUrl();
				}
			}
			
			$("#connTrgDrvrNm").val(drvrNm);
			
			</c:otherwise>
		</c:choose>
	    		
		</c:if>
	</c:if>

	SboxSetLabelEvent();
});

$(window).on('load',function() {
	//달력팝업 추가...
// 	$("#constDt" ).datepicker();
});

$(window).resize( function(){
	
});

/** ConnTrgLnkUrl tag 생성  */
function appenInputConnTrgLnkUrl(ibsStatus, arrIdx, createId, subKey, isExistsUrlParams, appendInput) {

	if(arrIdx == 0) {
		appendInput = "<tr id='connTrgLnkUrl_tr"+arrIdx+"'><th rowspan='"+(subKey.inputId.length+(isExistsUrlParams? subKey.param.length : 0))+"' class='th_require' scope='row'><label for='connTrgLnkUrl_"+createId+"'><s:message code='CNCT.URL'/>입력정보</label></th>";
	}else {
		appendInput += "<tr id='connTrgLnkUrl_tr"+arrIdx+"'>";
	}
	appendInput += "<td colspan='3'>";
	appendInput += "<span>"+(createId.toUpperCase())+"</span>: <input type='text' id='connTrgLnkUrl_"+createId+"' name='"+createId+"'/>";
	appendInput += "</td></tr>";

	//마지막 생성 시 param은 필수 항목 아님 
	if(subKey.inputId.length-1 == arrIdx && isExistsUrlParams) {
		$.each(subKey.param, function(arrIdx2, createId2) {

			appendInput += "<tr class='param' id='connTrgLnkUrl_tr"+(arrIdx+(arrIdx2+1))+"'>";
			appendInput += "<td colspan='3'>";
			appendInput += "<span>"+(createId2.toUpperCase())+"</span>: <input type='text' id='connTrgLnkUrl_"+createId2+"' name='"+createId2+"' class='param' placeholder='필수입력 아님'/>";
			appendInput += "</td></tr>";
		});
	}

	return appendInput;
}

/** 접속정보 이벤트 추가  */
function createChangeEventConnTrgLnkUrl() {

	var tempLnkUrl = "";
	
	 $("input[id^=connTrgLnkUrl_]").change(function(event) {
		 event.preventDefault();
		 tempLnkUrl = initLnkUrl;

		var regexp = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;	//ip유효성 정규식
		var regexp1 = /^[0-9]*$/;

		var objName = $(this).attr("name");
		var objVal = $(this).val();

		if(objName == "ip" && !regexp.test(objVal)) {
			showMsgBox("ERR", "유효하지 않은 IP입니다.");
			return false;
		}

		if(objName == "port" && !regexp1.test(objVal)){
			showMsgBox("ERR", "PORT를 정수로 입력하세요.");
			return false;
		}

		var paramCnt = $("input[id^=connTrgLnkUrl_].param").length;
		
		 $.each($("input[id^=connTrgLnkUrl_]:not(.param)"), function(idx, selObj) {
			 
			 tempLnkUrl = tempLnkUrl.replace("{"+selObj.name+"}",  selObj.value);

			 //param 세팅
			 if($("input[id^=connTrgLnkUrl_]:not(.param)").length == idx+1 && paramCnt > 0) {
				 var tempCtn = 0;
				 
				$.each($("input[id^=connTrgLnkUrl_].param"), function(paramIdx, paramSelObj) {
					
					if(!isBlankStr(paramSelObj.value)) {
						tempLnkUrl +=  (tempCtn == 0 ? "?" : "&") +paramSelObj.name+"="+paramSelObj.value;
						tempCtn++;
					}
				});
			 }
	     });

		 $("#connTrgLnkUrl").val(tempLnkUrl);
	});
}

</script>

<!-- </head> -->
<!-- <body>     -->

<div class="tb_basic">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="<s:message code='MSG.TBL.SMRY' />"> <!-- 테이블 서머리입니다. -->
		<caption><s:message code="TBL.NM1" /> <!-- 테이블 이름 --></caption>
            <colgroup>
            <col style="width:15%;" />
            <col style="width:35%;" />
            <col style="width:15%;" />
            <col style="width:35%;" />
            </colgroup>
            <tbody>
                <tr>
                    <th scope="row" class="th_require"><label for="orgCd"><s:message code="ORG.NM"/></label> </th> <!-- 기관명 -->
                    <td>
                    <div class="sbox wd99p">
					<label class="sbox_label <c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'>d_readonly</c:if>" for="orgCd"></label>
					<select id="orgCd" class="" name="orgCd" <c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'>disabled</c:if>>
					<%-- <option value=""><s:message code="CHC" /></option> --%>
						<c:forEach var="code" items="${codeMap.orgCd}" varStatus="status">
							<c:if test="${fn:length(codeMap.orgCd) > 1 and status.index == 0}">
								<option value=""><s:message code="CHC" /></option>
							</c:if>
							<c:choose>
								<c:when test='${search.ibsStatus eq "I"}'>
								<option value="${code.codeCd}" <c:if test="${code.codeCd eq sessionScope.loginVO.orgCd}">selected</c:if> >
									${code.codeLnm}
								</option>
								</c:when>
								<c:when test='${search.ibsStatus eq "U"}'>
								<option value="${code.codeCd}" <c:if test="${result != null and code.codeCd eq result.orgCd}">selected</c:if> >
									${code.codeLnm}
								</option>
								</c:when>
							</c:choose>
						</c:forEach>
					</select>
					</div>              	
                    </td>
                    <th scope="row" class="th_require"><label for="infoSysCd"><s:message code="INFO.SYS.NM"/></label> </th> <!-- 정보시스템명 -->
                    <td> 
                    	<div class="sbox wd99p">
					<label class="sbox_label <c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'>d_readonly</c:if>" for="infoSysCd"></label>
					<select id="infoSysCd" class="wd300" name="infoSysCd" <c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'>disabled</c:if>>
						<option value=""><s:message code="CHC"/></option>
						<c:forEach var="code" items="${codeMap.infoSysCdUserMap}" varStatus="status">
							<c:choose>
								<c:when test='${search.ibsStatus eq "I"}'>
									<c:if test="${code.upcodeCd !=null and code.upcodeCd eq sessionScope.loginVO.orgCd}">
										<option value="${code.codeCd}">${code.codeLnm}</option>
									</c:if>
								</c:when>
								<c:when test='${search.ibsStatus eq "U"}'>
									<c:if test='${code.upcodeCd != null and code.upcodeCd eq result.orgCd}'>
										<option value="${code.codeCd}" <c:if test='${code.codeCd eq result.infoSysCd}'>selected</c:if>>${code.codeLnm}</option>
									</c:if>
								</c:when>
							</c:choose>
						</c:forEach>
					</select>
					</div>
                    </td>
                </tr>
			<tr>
				<th scope="row" class="th_require"><label for="dbConnTrgLnm"><s:message code="DB.CONN.TRG.LNM" /></label></th>
				<td><input type="text" id="dbConnTrgLnm" name="dbConnTrgLnm" class="wd98p" value="${result.dbConnTrgLnm}"/></td>
				<th scope="row" class="th_require"><label for="dbConnTrgPnm"><s:message code="DB.CONN.TRG.PNM" /></label></th>
				<td><input type="text" id="dbConnTrgPnm" name="dbConnTrgPnm" class="wd98p<c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'> d_readonly</c:if>" value="${result.dbConnTrgPnm}" <c:if test='${search.ibsStatus eq "U" and  result.cenmYn eq "Y"}'>readonly</c:if>/></td>
			</tr>
			<tr>
				<th scope="row"><label for="objDescn"><s:message code="DB.TXT" /></label></th><!-- DB설명 -->
				<td colspan="3"><textarea id="objDescn" name="objDescn" class="wd98p">${result.objDescn}</textarea></td>
			</tr>
			<tr>
				<th scope="row" class="th_require"><label for="aplyBizNm"><s:message code="APL.BIZ.NM" /></label></th> <!-- 적용업무 -->
				<!-- 설명 -->
				<td colspan="3"><textarea id="aplyBizNm" name="aplyBizNm"
						class="wd98p" placeholder="상세하게 기재하세요(10자이상)">${result.aplyBizNm}</textarea></td>
			</tr>
			<tr>
				<th scope="row" class="th_require"><label for="dbmsTypCd"><s:message code="DBMS.KIND" /></label></th> <!-- DBMS종류 -->
				<td><div class="sbox wd99p">
					<label class="sbox_label" for="dbmsTypCd"></label>
					<select id="dbmsTypCd" class="wd300" name="dbmsTypCd">
						<option value=""><s:message code="CHC"/></option>
						<c:forEach var="code" items="${codeMap.dbmsTypCd}"
							varStatus="status">
							<option value="${code.codeCd}" <c:if test='${search.ibsStatus eq "U" and code.codeCd eq result.dbmsTypCd}'>selected</c:if> >${code.codeLnm}</option>
						</c:forEach>
					</select>
					</div></td>
				<th scope="row" class="th_require"><label for="dbmsVersCd"><s:message code="DBMS.VERS" /></label></th>
				<td>
					<div class="sbox wd99p">
					<label class="sbox_label" for="dbmsVersCd"></label>
					<select id="dbmsVersCd" class="wd300" name="dbmsVersCd">
						<option value=""><s:message code="CHC"/></option>
					</select>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row" class="th_require"><label for="osKndCd"><s:message code="OS.KND.NM" /></label></th> <!-- 운영체제종류 -->
				<td>
					<div class="sbox wd99p">
					<label class="sbox_label" for="osKndCd"></label>
					<select id="osKndCd" class="wd300" name="osKndCd">
						<option value=""><s:message code="CHC"/></option>
						<c:forEach var="code" items="${codeMap.osKndVers}" varStatus="status">
							<c:if test="${code.upcodeCd == null}">
							<option value="${code.codeCd}"<c:if test='${result.osVerNm != null and code.codeCd eq fn:substring(result.osVerNm,0,1)}'>selected</c:if>>${code.codeLnm}</option>
							</c:if>
						</c:forEach>
					</select>
					</div>
				</td>
				<th scope="row" class="th_require"><label for="osVerNm"><s:message code="OS.VER" /></label></th> <!-- 운영체제버전 -->
				<td>	
					<span class="input_check"></span>
					<div class="sbox wd99p">
					<label class="sbox_label" for="osVerNm"></label>
					<select id="osVerNm" class="wd300" name="osVerNm">
						<option value=""><s:message code="CHC"/></option>
						<c:forEach var="code" items="${codeMap.osKndVers}" varStatus="status">
							<c:if test='${code.upcodeCd != null and result.osVerNm != null and code.upcodeCd eq fn:substring(result.osVerNm,0,1)}'>
								<option value="${code.codeCd}" <c:if test='${result.osVerNm != null and code.codeCd eq result.osVerNm}'>selected</c:if>>${code.codeLnm}</option>
							</c:if>
						</c:forEach>
						
					</select>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row"><label for="constDt"><s:message code="CNST.DT" /></label></th>
				
				
				<%-- <td><input type="text" id="constDt" name="constDt" class="wd80p" value="<fmt:formatDate value="${result.constDt}" type="both" pattern="yyyy-MM-dd"/>"/></td> --%>
				<td><input type="text" id="constDt" name="constDt" class="wd98p" value="${result.constDt}" placeholder="YYYYMMDD형식으로 입력하세요."/></td>
				<th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span><label for="tblCnt"><s:message code="TBL.CNT" /></label></th> <!-- 테이블수 -->
				<td><input type="text" id="tblCnt" name="tblCnt"
					class="at_r wd98p d_readonly" value="${result.tblCnt}" readonly/></td>
			</tr>
			<tr>
				<th scope="row" class="th_require g_ico g_ico1"><span class="ico_ga">&nbsp;</span> <label for="dataCpct"><s:message code="DATA.CPCT" /></label></th> <!--데이터용량(MB)  -->
				<td><input type="text" id="dataCpct" name="dataCpct"
					class="at_r wd98p d_readonly" value="${result.dataCpct}" readonly/></td>
				<th scope="row" <c:if test='${result.cenmYn == null or result.cenmYn eq "N"}'>class="th_require"</c:if>><label for="pdataExptRsn"><s:message code="PDATA.EXPT.RSN" /></label></th>
				<td>
					<div class="sbox wd99p">
					<label class="sbox_label <c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'>d_readonly</c:if>" for="pdataExptRsn"></label>
					<select id="pdataExptRsn" class="wd300" name="pdataExptRsn" <c:if test='${search.ibsStatus eq "U" and result.cenmYn eq "Y"}'>disabled</c:if>>
						<option value=""><s:message code="CHC"/></option>
						<c:forEach var="code" items="${codeMap.pdataExptRsn}"
							varStatus="status">
							<option value="${code.codeCd}" <c:if test='${search.ibsStatus eq "U" and code.codeCd eq result.pdataExptRsn }'>selected</c:if>>${code.codeLnm}</option>
						</c:forEach>
					</select>
					</div>
				</td>
			</tr>
               </tbody>
              </table>
     <div class="tb_comment g_ico g_ico1">&nbsp;<span class="ico_ga">&nbsp;</span>로 표시된 항목은 보유DB로부터 메타데이터를 자동수집하는 항목으로 입력하지 않습니다.</div>
    </div>
	
<!-- </body> -->
<!-- </html> -->
