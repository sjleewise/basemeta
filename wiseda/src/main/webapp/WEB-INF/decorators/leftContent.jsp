<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
 $(document).ready(function(){
	 //마우스 오버시 이미지 변환 
	 imgConvert($('div.smenu_favor_bt a img'));
	 
	 //메뉴 이동....
	 $("#menuStndRqst").click(function(){
// 		location.href="<c:url value="/meta/stnd/stnd_rqst.do" />"; 
		location.href="<c:url value="/meta/stnd/stndnew_rqst.do" />"; 
	 });
	 $("#menuStdnLst").click(function(){
			location.href="<c:url value="/meta/stnd/stwd_lst.do" />"; 
		 });
	 $("#menuStdmnLst").click(function(){
			location.href="<c:url value="/meta/stnd/dmn_lst.do" />"; 
		 });
	 $("#menuSditmLst").click(function(){
			location.href="<c:url value="/meta/stnd/sditm_lst.do" />"; 
		 });

	 $("#menuRegSubjRqst").click(function(){
			location.href="<c:url value="/meta/model/subj_lst.do" />"; 
		 });
	 $("#menuSubjLst").click(function(){
			location.href="<c:url value="/meta/model/subj_lst.do" />"; 
		 });
	 $("#menuModelPdmRqst").click(function(){
			location.href="<c:url value="/meta/model/modelpdm_rqst.do" />"; 
		 });
 	 $("#menuModelPdmLst").click(function(){
			location.href="<c:url value="/meta/model/pdmtbl_lst.do" />"; 
		 });
 	 $("#menuModelPdmHist").click(function(){
			location.href="<c:url value="/meta/model/pdmtblhist_lst.do" />"; 
		 });
	 $("#menuUserGroupRqst").click(function(){
			location.href="<c:url value="/cmvw/user/usergroup_lst.do" />"; 
		 });
	 $("#menuUserRqst").click(function(){
			location.href="<c:url value="/cmvw/user/user_lst.do" />"; 
		 });
	 $("#menuMenuRqst").click(function(){
			location.href="<c:url value="/cmvw/menu/menu_lst.do" />"; 
		 });
	 $("#menuSystemCrud").click(function(){
			location.href="<c:url value="/cmvw/sysarea/sysarea_lst.do" />"; 
		 });
	 $("#menuCodeCrud").click(function(){	
			location.href="<c:url value="/egmd/cmcd/cmcdcode_lst.do" />"; 
		 });
 });
 

function setMenu(){
	
	$("#menuDicRqst").click(
	     function(){      
	                     
	         tab_cont.InsertItem2("StndDicRqst", "<s:message code='BFHD.INTG.REG' />", "<c:url value='/meta/stnd/stndwd_rqst.do'/>?pageName=StndDicRqst", ""); /* 사전통합등록 */
	         
	         $('#divSideBar').click();
	     }
	        	        
	);	
	
	$("#menuCmvwUserRqst").click(
         function(){      
                         
             tab_cont.InsertItem2("CmvwUserRqst", "<s:message code='USER.REG' />", "<c:url value='/cmvw/user/cmvwuser_lst.do'/>?pageName=CmvwUserRqst", ""); /* 사용자등록 */
             
             $('#divSideBar').click();
         }
                        
    );  
	
	$("#menuTestRqst").click(
         function(){      
                         
             tab_cont.InsertItem2("TestRqst", "<s:message code='TEST' />", "<c:url value='/meta/test/test_rqst.do'/>?pageName=TestRqst", ""); /* 테스트 */
             
             $('#divSideBar').click();
         }
                        
    );  
		
}

</script>


<!-- sub 메뉴 시작 -->
<div id="divMenuMainBar" class="smenu_in"> <!-- 접힐때 position 값은 class="smenu_in" 으로 바꾸면 됩니다. -->
   <div class="smenu_bg">
       <div class="smenu_mywork"><s:message code="MY.BZWR" /></div> <!-- 나의 업무 -->
       <div>· <s:message code="DEMD" /> <span style="font-weight:bold;"><a href="#">13</a></span><s:message code="CNT" /> <span style="color:#999999;">I</span> <s:message code="APRL" /> <span style="font-weight:bold;"><a href="#">30</a></span><s:message code="CNT" /> · </div> <!-- 요청 건 결재 건-->
       <div style="clear:both; height:7px;"><span></span></div>
       <div class="smenu_favor"><s:message code="FAVO" /></div> <!-- 즐겨찾기 -->
       <div class="smenu_favor_bt"><a href="#"><img src='<c:url value="/images/smenu_bt_move.gif" />'  alt="<s:message code='MOVE' />" /></a></div> <!-- 이동 -->
       <div class="smenu_favor_bt"><a href="#"><img src='<c:url value="/images/smenu_bt_delete.gif"/>'  alt="<s:message code='DEL' />" /></a></div> <!-- 삭제 -->
       <div class="smenu_favor_bt"><a href="#"><img src='<c:url value="/images/smenu_bt_add.gif"/>'  alt="<s:message code='ADDT' />" /></a></div> <!-- 추가 -->
       <div>
           <select id="" class="" name="" style="width:270px;">
               <option selected="" value=""><s:message code="MSG.ADDT.BTN.TAB.REG" /></option> <!-- 추가버튼을 누르면 현재 탭이 등록됩니다. -->
               <option value=""><s:message code="CELL" />1</option> <!-- 셀 -->
               <option value=""><s:message code="CELL" />2</option> <!-- 셀 -->
           </select>
       </div>
       
       <div style="clear:both; height:23px;"><span></span></div>
       
       <div class="smenu_tit">WISE META MENU</div>
       <div style="clear:both; height:10px;"><span></span></div>
       <div class="smenu_tree">
            <div class="smenu_folder_02"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#"><s:message code="STRD.REG.DEMD" /></a></div> <!-- 표준등록요청 -->
            <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuStndRqst" name="menuStndRqst"><s:message code="STRD.REG.DEMD" /></a></div> <!-- 표준 등록요청 -->
            <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuStdnLst" name="menuStdnLst"><s:message code="STRD.WORD.INQ" /></a></div> <!-- 표준단어 조회 -->
            <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuStdmnLst" name="menuStdmnLst"><s:message code="STRD.DMN.INQ" /></a></div> <!-- 표준도메인 조회 -->
            <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuSditmLst" name="menuSditmLst"><s:message code="STRD.ATRB.INQ" /></a></div> <!-- 표준속성 조회 -->
		<!-- </div> -->
<!--         <div class="smenu_tit">데이터모델</div> -->
<!--        <div style="clear:both; height:10px;"><span></span></div> -->
<!--        <div class="smenu_tree"> -->
            <div class="smenu_folder_02"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#"><s:message code="DATA.MDEL" /></a></div> <!-- 데이터 모델 -->
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuRegSubjRqst" name="menuRegSubjRqst"><s:message code="SUBJ.TRRT.REG" /></a></div> <!-- 주제영역 등록 --> 
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuSubjLst"" name="menuSubjLst"><s:message code="SUBJ.TRRT.INQ" /></a></div> <!-- 주제영역 조회 --> 
              <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuModelPdmRqst" name="menuModelPdmRqst"><s:message code="PHYC.MDEL.REG.DEMD" /></a></div> <!-- 물리모델 등록요청 -->  
              <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuModelPdmLst" name="menuModelPdmLst"><s:message code="PHYC.MDEL.INQ" /></a></div> <!-- 물리모델 조회 -->  
              <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuModelPdmHist" name="menuModelPdmHist"><s:message code="PHYC.MDEL.CHG.HSTR.INQ" /></a></div> <!-- 물리모델 변경이력 조회 -->  
 		 
 		     <div class="smenu_folder_02"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#"><s:message code="DATA.PRES" /></a></div> <!-- 데이터 현황 -->
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="" name=""><s:message code="MDEL.VS.DB.GAP.ANLY" /></a></div> <!-- 모델 vs DB 갭분석 --> 
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id=""" name=""><s:message code="COLLT.DB.INQ" /></a></div> <!-- 수집 DB 조회 --> 
 		 
 		     <div class="smenu_folder_02"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#"><s:message code="MGR" /></a></div> <!-- 관리자 -->
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuUserGroupRqst" name="menuUserGroupRqst"><s:message code="USER.GRP.REG" /></a></div> <!-- 사용자그룹 등록 --> 
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuUserRqst" name="menuUserRqst"><s:message code="USER.REG" /></a></div> <!-- 사용자 등록 --> 
			<div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuMenuRqst" name="menuMenuRqst"><s:message code="MENU.REG" /></a></div> <!-- 메뉴 등록 --> 
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="" name=""><s:message code="AUTH.REG" /></a></div> <!-- 권한 등록 --> 
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuSystemCrud" name="menuSystemCrud"><s:message code="SYS.MNG" /></a></div> <!-- 시스템 관리 --> 
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="" name=""><s:message code="DB.MS.MNG" /></a></div> <!-- DBMS 관리 --> 
             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuCodeCrud" name="menuCodeCrud"><s:message code="COM.CD.MNG" /></a></div> <!-- 공통코드 관리 --> 



<%--             <div class="smenu_folder_01"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#">물리모델</a></div> --%>
<%--             <div class="smenu_folder_01"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#">데이터베이스</a></div> --%>
<%--             <div class="smenu_folder_01"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#">영향도</a></div> --%>
<%--             <div class="smenu_folder_01"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#">데이터품질</a></div> --%>
<%--             <div class="smenu_folder_01"><a href="#"><img src='<c:url value="/images/smenu_folder_01.gif"/>' alt="" /></a> <a href="#">업무관리</a></div> --%>
<%--             <div class="smenu_folder_03"><a href="#"><img src='<c:url value="/images/smenu_folder_03.gif"/>' alt="" /></a> <a id="menuCmvwUserRqst" name="menuCmvwUserRqst">사용자등록</a></div> --%>
            
            <!-- 
            <div class="smenu_folder_01"><a href="#"><img src="images/smenu_folder_01.gif" alt="" /></a> <a href="#">등록요청</a></div>
            <div class="smenu_folder_02"><a href="#"><img src="images/smenu_folder_02.gif" alt="" /></a> <a href="#">데이터모델</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_01"><a href="#"><img src="images/smenu_folder_01.gif" alt="" /></a> <a href="#">데이터모델 관리</a></div>
            <div class="smenu_folder_01"><a href="#"><img src="images/smenu_folder_01.gif" alt="" /></a> <a href="#">맵핑정보</a></div>
            <div class="smenu_folder_01"><a href="#"><img src="images/smenu_folder_01.gif" alt="" /></a> <a href="#">등록요청</a></div>
            <div class="smenu_folder_02"><a href="#"><img src="images/smenu_folder_02.gif" alt="" /></a> <a href="#">데이터모델</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
            <div class="smenu_folder_03"><a href="#"><img src="images/smenu_folder_03.gif" alt="" /></a><a href="#">DB이관/GAP분석 비교대상 관리</a></div>
             -->
        </div>
    </div>
    <div class="smenu_bt_bar" id="divSideBar">
        <div class="smenu_bt_bar_in" id="divSideBarCont"><a href="#">&nbsp;</a></div>
    </div>
</div>
<!-- sub 메뉴 끝 -->    

	<!-- 
		<table border="1">
			<tr>
				<td>
			    	<ul id="files">
						<li><a href="documents">My Documents</a>
							<ul>
								<li><a href="documents/Christines_Files/">Christine's Files</a>
									<ul>
										<li><a href="#">Resume.doc</a></li>
										<li><a href="#">Cover-letter.doc</a></li>
										<li><a href="#">Gift Registry.doc</a></li>
									</ul>
								</li>
								<li><a href="documents/Nathans_Stuff/">Nathan's Stuff</a>
									<ul>
										<li><a href="#">Birthday Parties.doc</a></li>
										<li><a href="#">Area Playgrounds.doc</a></li>
									</ul>
								</li>
								<li><a href="documents/Travel_Ideas/">Travel Ideas</a>
									<ul>
										<li><a href="#">Potential Places.doc</a></li>
										<li><a href="#">Travel Funds.doc</a></li>
									</ul>
								</li>
								<li><a href="documents/Wedding_Plan/">Wedding Plan</a>
									<ul>
										<li><a href="#">Guests.doc</a></li>
										<li><a href="#">Services.doc</a></li>
									</ul>
								</li>
							</ul>
						</li>
						<li><a href="movies">My Movies</a>
							<ul>
								<li><a href="#">The Big Lebowski.m4v</a></li>
								<li><a href="#">Planet Earth.m4v</a></li>
							</ul>
						</li>
						<li><a href="music">My Music</a>
							<ul>
								<li><a href="#">Bloc Party - Pioneers.mp3</a></li>
								<li><a href="#">Fleet Foxes - Blue Ridge Mountains.mp3</a></li>
							</ul>
						</li>
						<li><a href="photos">My Photos</a>
							<ul>
								<li><a href="#">yellow-flower.jpg</a></li>
								<li><a href="#">orange-flower.jpg</a></li>
								<li><a href="#">red-flower.jpg</a></li>
								<li><a href="#">white-flower.jpg</a></li>
								<li><a href="#">bumblebees.jpg</a></li>
							</ul>
						</li>
					</ul>
				</td>
				<td width="15px" id="tdSideBar">&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
	  -->
	  
	  