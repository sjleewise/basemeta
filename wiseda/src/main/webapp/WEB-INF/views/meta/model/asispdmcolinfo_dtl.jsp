<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page import="kr.wise.commons.WiseMetaConfig" %>

<!-- 컬럼 정보 탭 -->
<%-- 		<%@include file="pdmcol_dtl.jsp" %> --%>
		<!-- 입력폼 시작 -->
	         <form name="frmInput2" id="frmInput2" method="post" >
	   		      <input type="hidden" id="sditmId" name="sditmId"  readonly/>
<!-- 	         <input type="hidden" id="tesibs" name="tesibs" value="testvalue" /> -->
		<div id="input_form_div">
	            <fieldset>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit">테이블 정보</div>
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend>머리말</legend>
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
	                        <caption>
	                        컬럼 이름
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="subjId">주제영역ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="subjId" name="subjId"/></span></td> --%>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row" class=""><label for="mdlLnm">모델논리명</label></th> -->
<!-- 	                                <td colspan="3"><input type="text" id="mdlLnm" name="mdlLnm" /></td> -->
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" class=""><label for="pdmTblLnm">물리모델테이블</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="pdmTblLnm" name="pdmTblLnm" class="wd98p" readonly /></span></td>

	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
		 <div style="clear:both; height:10px;"><span></span></div>
		 <div class="stit">컬럼 상세 정보</div>
		 <div style="clear:both; height:5px;"><span></span></div>
	                <legend>머리말</legend>
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
	                        <caption>
	                        컬럼 이름
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="pdmTblId">테이블ID</label></th> -->
<%-- 	                                <td colspan="3"><span class=""><input type="text" id="pdmTblId" name="pdmTblId"/></span></td> --%>
<!-- 	                            </tr> -->
	                            <tr>
	                                <th scope="row" class=""><label for="pdmColPnm">물리모델컬럼명(물리명)</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="pdmColPnm" name="pdmColPnm" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pdmColLnm">물리모델컬럼명(논리명)</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="pdmColLnm" name="pdmColLnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="colOrd">컬럼순서</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="colOrd" name="colOrd" class="wd98p" readonly/></span></td>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="objVers">버전</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="objVers" name="objVers" readonly/></td> --%>
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                       		    <th scope="row"><label for="regTypCd">등록유형</label></th>
                               		 <td><span class="" >
                                		<select id="regTypCd" class="" name="regTypCd"  disabled="disabled" >
									<option value="">전체</option>
									<option value="C">신규</option>
									<option value="U">변경</option>
									<option value="D">삭제</option>
								</select>
								</span>
								</td>
								</tr>
	                            <tr>
	                                <th scope="row"><label for="objDescn">설명</label></th>
	                                <td colspan="3"><textarea id="objDescn" name="objDescn" accesskey=""  class="b0" style="height:50px;width:99%;" readonly></textarea></td>
	                            </tr>
<!-- 	                            style="width:wd500;" -->
	                            <tr>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="dataType">데이터타입</label></th>
	                                <td colspan="1"><input type="text" id="dataType" name="dataType"  class="wd98p"  readonly/></td>

<!-- 	                                <th scope="row"><label for="sditmLnm">표준용어</label></th> -->
<!-- 	                                <td colspan="1"><input type="text" id="sditmLnm" name="sditmLnm"  class=""  readonly/></td> -->
	                                <th scope="row"><label for="sditmLnm">표준용어</label></th>
	                                <td colspan="1"><input type="text" id="sditmLnm" name="sditmLnm"  class="wd98p" onclick='window.open().location.href="../stnd/sditm_lst.do?objId="+$("#frmInput2 #sditmId").val()'  readonly />
<%-- 	                                <button type="button" class="smenu_link" onclick='window.open().location.href="../stnd/sditm_lst.do?objId="+$("#frmInput2 #sditmId").val()' readonly ><span>표준용어 조회</span></button></td> --%>
	                            </tr>
<!-- 	                            <tr> -->
<!-- 	                                <th scope="row"><label for="dataLen">데이터길이</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="dataLen" name="dataLen" readonly/></td> --%>
<!--  	                            </tr> -->
<!--  	                            <tr> -->
<!-- 	                                <th scope="row"><label for="dataScal">소수점길이</label></th> -->
<%-- 	                                <td colspan="1"><span class="" ><input type="text" id="dataScal" name="dataScal" readonly/></td> --%>
<!-- 	                            </tr> -->
	                            <tr>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="pkYn">PK여부</label></th>
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="pkYn" name="pkYn" value="Y" onclick="return false;"/> PK 적용</span>
	                                </td>
<!-- 	                                <td colspan="1"><input type="text"  id="pkYn" name="pkYn"/></td> -->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="pkOrd">PK순서</label></th>
	                                <td colspan="1"><span class="" ><input type="text"  id="pkOrd" name="pkOrd" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row"><label for="nonulYn">NOTNULL여부</label></th>
	                                <td colspan="1"><span class="input_check" >
	                                    <input type="checkbox" id="nonulYn" name="nonulYn" value="Y" onclick="return false;"/> NOTNULL 적용</span>
	                                </td>
<!-- 	                                <td colspan="1"><input type="text" id="nonulYn" name="nonulYn" /></td> -->
<!-- 	                            </tr> -->
<!-- 	                            <tr> -->
	                                <th scope="row"><label for="defltVal">DEFAULT값</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="defltVal" name="defltVal" class="wd98p" readonly></span></td>
	                            </tr>
	                            <tr>
	                             <th scope="row"><label for="encYn">암호화여부</label></th>
                                 <td colspan = "3"><select id="encYn" class="wd200" name="encYn" value = "${result.encYn}" disabled="disabled">
                                        <option value=""></option>
                                        <option value="Y">예</option>
                                        <option value="N">아니오</option>
                                    </select></td></tr>
	                        </tbody>
	                    </table>
	                </div>
	                <div style="clear:both; height:10px;"><span></span></div>
					<div class="stit">표준사전 상세 정보</div>
					<div style="clear:both; height:5px;"><span></span></div>
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="표준사전 상세정보">
	                        <caption>
	                        표준사전 상세정보
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" class=""><label for="sditmLnm">표준용어논리명</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="sditmLnm" name="sditmLnm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="sditmPnm">표준용어물리명</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="sditmPnm" name="sditmPnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="dmnLnm">도메인논리명</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="dmnLnm" name="dmnLnm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="dmnPnm">도메인물리명</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="dmnPnm" name="dmnPnm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="infotpLnm">인포타입명</label></th>
	                                <td colspan="3"><span class="" ><input type="text" id="infotpLnm" name="infotpLnm" class="wd98p" readonly/></span></td>

	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                
	                 <div style="clear:both; height:10px;"><span></span></div>
					 <div class="stit">요청상세정보</div>
					 <div style="clear:both; height:5px;"><span></span></div>
	                <legend>머리말</legend>
	                <div class="tb_read">
	                    <table width="100%" border="0" cellspacing="0" cellpadding="0" summary="테이블 서머리입니다.">
	                        <caption>
	                        컬럼 이름
	                        </caption>
	                        <colgroup>
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        <col style="width:15%;" />
	                        <col style="width:35%;" />
	                        </colgroup>
	                        <tbody>
	                            <tr>
	                                <th scope="row" class=""><label for="frsRqstDtm">최초요청일시</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstDtm" name="frsRqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="frsRqstUserNm">최초요청사용자명</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="frsRqstUserNm" name="frsRqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="rqstDtm">요청일시</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="rqstDtm" name="rqstDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="rqstUserNm">요청사용자명</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="rqstUserNm" name="rqstUserNm" class="wd98p" readonly/></span></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class=""><label for="aprvDtm">승인일시</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="aprvDtm" name="aprvDtm" class="wd98p" readonly/></span></td>

	                                <th scope="row" class=""><label for="aprvUserNm">승인사용자명</label></th>
	                                <td colspan="1"><span class="" ><input type="text" id="aprvUserNm" name="aprvUserNm" class="wd98p" readonly/></span></td>

	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                </fieldset>
		</div>
	            </form>
		<!-- 입력폼 끝 -->
		<div style="clear:both; height:10px;"><span></span></div>
		<!-- 입력폼 버튼... -->
<!-- 		<div id="divFrmBtn" style="text-align: center;"> -->
<!-- 			<button id="btnfrmSave" name="btnfrmSave">저장</button> -->
<!-- 			<button id="btnfrmReset" name="btnfrmReset" >초기화</button> -->
<!-- 		</div> -->