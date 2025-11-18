/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CodeListService.java
 * 2. Package : kr.wise.egmd.cmcd.service
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 15. 오후 4:39:41
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 15. 		: 신규 개발.
 */
package kr.wise.commons.code.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CodeListMapper;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.damgmt.approve.service.WaaRqstAprp;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.admin.service.WaaInfoSys;
import kr.wise.meta.admin.service.WaaOrg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <PRE>
 * 1. ClassName : CodeListService
 * 2. Package  : kr.wise.egmd.cmcd.service
 * 3. Comment  :
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 15.
 * </PRE>
 */
@Service ("codeListService")
public class CodeListServiceImpl implements CodeListService {

	Logger logger = LoggerFactory.getLogger(getClass());

	//private List<CodeListVo> codeList;

	@Inject
	private CodeListMapper codeListMapper;

	/**
	 * <PRE>
	 * 1. MethodName : getCodeList
	 * 2. Comment    : 코드 리스트 조회
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 18.
	 * </PRE>
	 *   @return List<CodeListVo>
	 *   @param codenm
	 *   @return
	 */
	//@Override
	@Override
	public List<CodeListVo> getCodeList(String codenm) {
		List<CodeListVo> codeList = null;
		
		//사용자 정보
		String userId = null;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		userId = user.getUniqId();
		
		/* 
		if(!UtilString.null2Blank(user.getIsAdminYn()).equals("Y")){
			userId = user.getUniqId();
		}
		*/

		if(WiseMetaConfig.CodeListAction.CODE_LIST_PRJ.getAction().equals(codenm)) {
			codeList = codeListMapper.getProjectCodeList();
		} else if (WiseMetaConfig.CodeListAction.CL_CODE.getAction().equals(codenm)) {
			codeList = codeListMapper.getClCodeList();
		} else if (WiseMetaConfig.CodeListAction.COM_CODE.getAction().equals(codenm)) {
			codeList = codeListMapper.getComCodeList();
		} else if(WiseMetaConfig.CodeListAction.sysarea.getAction().equals(codenm)) {
			codeList = codeListMapper.getSysareaCodeList();
	    } else if(WiseMetaConfig.CodeListAction.dmng.getAction().equals(codenm)) {
			codeList = codeListMapper.getDmngCodeList();
		} else if(WiseMetaConfig.CodeListAction.infotp.getAction().equals(codenm)) {
			codeList = codeListMapper.getInfoTpCodeList();
		} else if(WiseMetaConfig.CodeListAction.dmnginfotp.getAction().equals(codenm)) {
			codeList = codeListMapper.getDmngInfoTpCodeList();
		} else if(WiseMetaConfig.CodeListAction.usergroup.getAction().equals(codenm)) {
			codeList = codeListMapper.getUsergroupCodeList();
		} else if(WiseMetaConfig.CodeListAction.userglnm.getAction().equals(codenm)) {
			codeList = codeListMapper.getUsergrouplnmCodeList();
		} else if(WiseMetaConfig.CodeListAction.dbmstypvers.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbmsTypVersCodeList();
		} else if(WiseMetaConfig.CodeListAction.COM_DCD.getAction().equals(codenm)) {
			codeList = codeListMapper.getComDcdList();
		}else if(WiseMetaConfig.CodeListAction.bbsAttrbCode.getAction().equals(codenm)) {
			codeList = codeListMapper.getBbsAttrbCodeList();
		}else if(WiseMetaConfig.CodeListAction.bbsTyCode.getAction().equals(codenm)) {
			codeList = codeListMapper.getBbsTyCodeList();
		}else if(WiseMetaConfig.CodeListAction.tmplatId.getAction().equals(codenm)) {
			codeList = codeListMapper.gettmplatIdList();
		}else if(WiseMetaConfig.CodeListAction.cateCode.getAction().equals(codenm)) {
			codeList = codeListMapper.getcateCodeList();
		}else if(WiseMetaConfig.CodeListAction.connTrgDbms.getAction().equals(codenm)) {
			codeList = codeListMapper.getConnTrgDbms();
		}else if(WiseMetaConfig.CodeListAction.abrTempList.getAction().equals(codenm)) {
			codeList = codeListMapper.getAbrTempList(userId);  
		}
		//표준항목 자동분할 ID
		else if(WiseMetaConfig.CodeListAction.dvRqstNo.getAction().equals(codenm)) {
			codeList = codeListMapper.getDvRqstNoList(userId); 
		}		
		//사전 조회 ID
		else if(WiseMetaConfig.CodeListAction.dicRqstNo.getAction().equals(codenm)) {
			codeList = codeListMapper.getDicList(userId); 
		}
		//표준도메인 자동분할 ID
		else if(WiseMetaConfig.CodeListAction.dvDmnRqstNo.getAction().equals(codenm)) {
			codeList = codeListMapper.getDvDmnRqstNoList(userId); 
		}
		else if(WiseMetaConfig.CodeListAction.dbmsDataType.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbmsDataType();
		}else if(WiseMetaConfig.CodeListAction.dbmsDataTyp.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbmsDataTyp();
			
		}else if(WiseMetaConfig.CodeListAction.subj.getAction().equals(codenm)) {
			codeList = codeListMapper.getSubjLnm();
			
		}else if(WiseMetaConfig.CodeListAction.subjLvl1.getAction().equals(codenm)) {
		    //주제영역 1레벨
            codeList = codeListMapper.getSubjLvl1();	

		}else if(WiseMetaConfig.CodeListAction.subjLvl2.getAction().equals(codenm)) {
			//주제영역 2레벨
			codeList = codeListMapper.getSubjLvl2();	
		
		}else if(WiseMetaConfig.CodeListAction.subjLvl3.getAction().equals(codenm)) {
			//주제영역 3레벨
			codeList = codeListMapper.getSubjLvl3();	
					
		}else if(WiseMetaConfig.CodeListAction.dbSch.getAction().equals(codenm)) {
            
            codeList = codeListMapper.getDbSchLnm();
        
		}else if(WiseMetaConfig.CodeListAction.devDbSch.getAction().equals(codenm)) {
            
            codeList = codeListMapper.getDevDbSchLnm();
                
		}else if(WiseMetaConfig.CodeListAction.connTrgSch.getAction().equals(codenm)) {
			codeList = codeListMapper.getConnTrgSch();
		}else if(WiseMetaConfig.CodeListAction.prfAnaDgr.getAction().equals(codenm)) {
			codeList = codeListMapper.getPrfAnaDgr();
		}else if(WiseMetaConfig.CodeListAction.brAnaDgr.getAction().equals(codenm)) {
			codeList = codeListMapper.getBrAnaDgr();
		}else if(WiseMetaConfig.CodeListAction.vrfcAnaDgr.getAction().equals(codenm)) {
			codeList = codeListMapper.getVrfcAnaDgr();
		}else if(WiseMetaConfig.CodeListAction.otlAnaDgr.getAction().equals(codenm)) {
			codeList = codeListMapper.getOtlAnaDgr(codenm);
		}else if(WiseMetaConfig.CodeListAction.uodAnaDgr.getAction().equals(codenm)) {
			codeList = codeListMapper.getUodAnaDgr(codenm);
		}else if(WiseMetaConfig.CodeListAction.tmAnaDgr.getAction().equals(codenm)) {
			codeList = codeListMapper.getTmAnaDgr(codenm);
		}else if(WiseMetaConfig.CodeListAction.connTrgSchId.getAction().equals(codenm)) {
			codeList = codeListMapper.getConnTrgSchId();
		}else if (CodeListAction.LangType.getAction().equals(codenm)) {
			codeList = codeListMapper.getLangType();
		}else if  (CodeListAction.dmngLowDgr.getAction().equals(codenm)) {
			codeList = codeListMapper.getDmngLowDgr();
		}else if  (CodeListAction.subjPdmTbl.getAction().equals(codenm)) {
			codeList = codeListMapper.getSubjPdmTbl();
		}else if  (CodeListAction.dbSchForDoubleSelectIBS.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbSchForDoubleSelectIBS();
		}else if  (CodeListAction.dbConnTrgIdInDbRole.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbConnTrgIdInDbRole();
		}else if  (CodeListAction.roleNmForDoubleSelectIBS.getAction().equals(codenm)) {
			codeList = codeListMapper.getRoleNmForDoubleSelectIBS();
		}else if  (CodeListAction.dbRoleNm.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbRoleNm();
		}else if  (CodeListAction.dbSchLnm.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbSchLnmOnly();
		}else if  (CodeListAction.tblSpac.getAction().equals(codenm)) {
			codeList = codeListMapper.getTblSpacLnm();
		} else if  (CodeListAction.tblSpacId.getAction().equals(codenm)) { // 테이블스페이스id.
			codeList = codeListMapper.getTblSpacId();
		}
		 else if  (CodeListAction.idxSpacId.getAction().equals(codenm)) { // 인덱스스페이스id.
			codeList = codeListMapper.getIdxSpacId();
		}
		//DB간 GAP
		else if  (CodeListAction.dbGapSrcShdId.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbGapSrcSchId();
		}
		else if  (CodeListAction.dbGapTgtShdId.getAction().equals(codenm)) {
			codeList = codeListMapper.getDbGapTgtSchId();
		}
		
		//도메인 유효값 조회
		else if  (CodeListAction.clSystmTyCode.getAction().equals(codenm)) {
			codeList = codeListMapper.getClSystmTyCode();
		}
		else if  (CodeListAction.clSystmTyIemCode.getAction().equals(codenm)) {
			codeList = codeListMapper.getClSystmTyIemCode();
		}else if(CodeListAction.connTrgSchId2.getAction().equals(codenm)) { //진단대상, 스키마명 (ID반환) (double_select) 메타관리대상만
			codeList = codeListMapper.getConnTrgSchId2();
		}else if(CodeListAction.connTrgSchIdCodeTsf.getAction().equals(codenm)) { //진단대상, 스키마명 (ID반환) (double_select) 메타관리대상만
			codeList = codeListMapper.getConnTrgSchIdCodeTsf();
		}else if(CodeListAction.infoTpCodeListIBS.getAction().equals(codenm)) { //진단대상, 스키마명 (ID반환) (double_select) 메타관리대상만
			codeList = codeListMapper.getInfoTpCodeListIBS();
		}else if(CodeListAction.codedmnginfotp.getAction().equals(codenm)) { //진단대상, 스키마명 (ID반환) (double_select) 메타관리대상만
			codeList = codeListMapper.getCodeDmngInfoTpCodeList();
		
		}else if(WiseMetaConfig.CodeListAction.devConnTrgSchId.getAction().equals(codenm)) {
			codeList = codeListMapper.getDevConnTrgSchId();	  
		
		}else if(CodeListAction.devConnTrgSchId2.getAction().equals(codenm)) { //진단대상, 스키마명 (ID반환) (double_select) 메타관리대상만
			codeList = codeListMapper.getDevConnTrgSchId2();
		}else if(CodeListAction.chnlTpDtls.getAction().equals(codenm)) { //메시지 코드중 채널유형코드 / 채널세부코드  (double_select) 
			codeList = codeListMapper.getchnlTpDtls();
		}else if(CodeListAction.bizDtls.getAction().equals(codenm)) { //메시지 코드중 메시지유형 / 업무구분  (double_select) 
			codeList = codeListMapper.getbizDtls();
		}
		
		
		// 도메인그룹 전체경로
		else if  (CodeListAction.dmngLowDgrStndAsrtFullPath.getAction().equals(codenm)) {
			codeList = codeListMapper.getDmngLowDgrStndAsrtFullPath();
		}
		//표준분류|인포타입명
		else if  (CodeListAction.infotpStndAsrtFullPath.getAction().equals(codenm)) {
			codeList = codeListMapper.getInfotpStndAsrtFullPath();
		}
		
		//주제영역권한
		else if(CodeListAction.subjLnmByLvl1.getAction().equals(codenm)) { 
			//주제영역 1레벨
			codeList = codeListMapper.getSubjLnmByLvl1();
		} 
		else if(CodeListAction.sysareaNsubjLnmByLvl1.getAction().equals(codenm)) { 
			//주제영역 1레벨 시스템영역
			codeList = codeListMapper.getSysareaNsubjLnmByLvl1();
		} 
		else if(CodeListAction.subjLnmByLVL2.getAction().equals(codenm)) { 
			codeList = codeListMapper.getSubjLnmByLvl2();
		} 
		else if(CodeListAction.SubjLstByLvl0Lvl1.getAction().equals(codenm)) { 
			codeList = codeListMapper.getSubjLstByLvl0Lvl1();
		} 
		else if(CodeListAction.deptNm.getAction().equals(codenm)) { 
			codeList = codeListMapper.getDeptNmList();
		} 
//		else if(CodeListAction.l1cdPnm.getAction().equals(codenm)) { 
//			codeList = codeListMapper.getL1cdPnmList();
//		} 
		else if(CodeListAction.subjLnmByLVL1Pnm.getAction().equals(codenm)) { 
			codeList = codeListMapper.getSubjLnmByLvl1Pnm();
		} 
		else if(CodeListAction.subjLnmByLVL2Pnm.getAction().equals(codenm)) { 
			codeList = codeListMapper.getSubjLnmByLvl2Pnm();
		}else if("OTL_ALG_ID".equals(codenm)) {  //이상값탐지 알고리즘 코드 조회(outlier detection) 
			codeList = codeListMapper.getOtlAlgId();
		}else if("OTL_TYP_CD".equals(codenm)) {  //이상값 처리 코드 조회
			codeList = codeListMapper.getOtlTypCd();
		}else if("OTL_DQI_MAP".equals(codenm)) {  //이상값 화면 품질지표 조회
			codeList = codeListMapper.getOtlDqiMap();
			
		}else if("getConnTrgSchIdMdl".equals(codenm)) {  //이상값 화면 품질지표 조회
			codeList = codeListMapper.getConnTrgSchIdMdl();
		}else if(CodeListAction.orgCd.getAction().equals(codenm)) { 
			codeList = codeListMapper.getOrgCd();
		
		}else if(CodeListAction.infoSysCd.getAction().equals(codenm)) { //�젙蹂댁떆�뒪�뀥肄붾뱶

			codeList = codeListMapper.getInfoSysCd(); 
		}else if(WiseMetaConfig.CodeListAction.connTrgSchIdHive.getAction().equals(codenm)) {
			codeList = codeListMapper.getConnTrgSchIdHive();
		}else if(WiseMetaConfig.CodeListAction.mtaDgr.getAction().equals(codenm)) {
			codeList = codeListMapper.getMtaDgr();
		}else if(WiseMetaConfig.CodeListAction.mtaDgrSel.getAction().equals(codenm)) {
			codeList = codeListMapper.getMtaDgrSel();
		}else if("WORD_DV".equals(codenm)) {
			codeList = codeListMapper.getWapWordDvList();
		}else if("connTrgRoleIdReqOnly".equals(codenm)) {
			codeList = codeListMapper.getConnTrgRoleIdReqOnly();
		}
		
		
		return codeList;
				
	}

	/**
	 * <PRE>
	 * 1. MethodName : getComCodeList
	 * 2. Comment    : 공통 코드 리스트 조회 (메타, DQ 공통 코드 조회)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 18.
	 * </PRE>
	 *   @return List<CodeListVo>
	 *   @param type  - "META", "DQ"
	 *   @param codenm - codeid
	 *   @return
	 */
	//@Override
	@Override
	public List<CodeListVo> getComCodeList(String type, String codenm) {
		List<CodeListVo> codeList = null;

		if(WiseConfig.META.equals(type)) {
			codeList = codeListMapper.metaComCodeList(codenm);
		} else if (WiseConfig.DQ.equals(type)) {
			codeList = codeListMapper.dqComCodeList(codenm);
		} else if (WiseConfig.PORTAL.equals(type)) {
			codeList = codeListMapper.ptComCodeList(codenm);
		}

		return codeList;
	}

	/**
	 * <PRE>
	 * 1. MethodName : getCodeListJson
	 * 2. Comment    : 코드 리스트를 Json String 형태로 제공
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 18.
	 * </PRE>
	 *   @return String
	 *   @param codenm
	 *   @return
	 */
	//@Override
	@Override
	public String getCodeListJson (String codenm) {

		List<CodeListVo> list =  getCodeList(codenm);
		ObjectMapper om = new ObjectMapper();
		String result = null;

		try {
			result = om.writeValueAsString(list);
		} catch (Exception e) {
			logger.error("[ERROR] : getCodeListJson({})", codenm);
		}

		logger.debug(result);

		return result;

	}


	/**
	 * <PRE>
	 * 1. MethodName : getCodeListIBS
	 * 2. Comment    : 코드 리스트를 IBSheet용 Vo로 반환...
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 18.
	 * </PRE>
	 *   @return ComboIBSVo
	 *   @param codenm
	 *   @return
	 */
	//@Override
	@Override
	public ComboIBSVo getCodeListIBS(String codenm) {

		ComboIBSVo comboIbs = new ComboIBSVo();
		StringBuilder code = new StringBuilder();
		StringBuilder text = new StringBuilder();

		List<CodeListVo> codeList = getCodeList(codenm);

		int i = 0;
		for (CodeListVo vo : codeList) {
			if(i++ > 0) {
				code.append("|");
				text.append("|");
			}
			code.append(vo.getCodeCd());
			text.append(vo.getCodeLnm());
		}

		comboIbs.ComboCode = code.toString();
		comboIbs.ComboText = text.toString();

		return comboIbs;
	}

	/** 결재라인별 콤보코드를 생성해서 반환한다. @return insomnia */
	@Override
	public List<ComboIBSVo> getApproveListIBS(ArrayList<WaaRqstAprp> approveList) {

		List<ComboIBSVo> combolist = new ArrayList<ComboIBSVo>();

		StringBuilder code = new StringBuilder();
		StringBuilder text = new StringBuilder();

		int prelvl = 1;

		int i = 0;
		for (WaaRqstAprp vo : approveList) {
			if(prelvl != vo.getAprLvl()) {
				//콤보코드를 리스트에 저장한다.
				ComboIBSVo comboIbs = new ComboIBSVo();
				comboIbs.ComboCode = code.toString();
				comboIbs.ComboText = text.toString();

//				aprvcodelist.put(prelvl, UtilJson.convertJsonString(comboIbs));

				combolist.add(comboIbs);

				//초기화 작업...
				i = 0;
				code.setLength(0);
				text.setLength(0);

				prelvl = vo.getAprLvl();
			}

			if(i++ > 0) {
				code.append("|");
				text.append("|");
			}
			code.append(vo.getAprvUserId());
			text.append(vo.getAprvUserNm());
		//
		}

		//콤보코드를 리스트에 저장한다.
		ComboIBSVo comboIbs = new ComboIBSVo();
		comboIbs.ComboCode = code.toString();
		comboIbs.ComboText = text.toString();


		combolist.add(comboIbs);


		return combolist;
	}


	@Override
	public ComboIBSVo getCodeListIBS(List<CodeListVo> codeList) {

		ComboIBSVo comboIbs = new ComboIBSVo();
		StringBuilder code = new StringBuilder();
		StringBuilder text = new StringBuilder();

//		List<CodeListVo> codeList = getCodeList(codenm);

		int i = 0;
		for (CodeListVo vo : codeList) {
			if(i++ > 0) {
				code.append("|");
				text.append("|");
			}
			code.append(vo.getCodeCd());
			text.append(vo.getCodeLnm());
		}

		comboIbs.ComboCode = code.toString();
		comboIbs.ComboText = text.toString();

		return comboIbs;
	}

	/** 목록성코드리스트 처리-코드명을 enum 클래스 이용 insomnia */
	@Override
	public List<CodeListVo> getCodeList(CodeListAction codenm) {
		List<CodeListVo> codeList = null;

		if(CodeListAction.CODE_LIST_PRJ.equals(codenm)) {
			codeList = codeListMapper.getProjectCodeList();
		} else if (CodeListAction.CL_CODE.equals(codenm)) {
			codeList = codeListMapper.getClCodeList();
		} else if (CodeListAction.COM_CODE.equals(codenm)) {
			codeList = codeListMapper.getComCodeList();
		} else if(CodeListAction.sysarea.equals(codenm)) {
			codeList = codeListMapper.getSysareaCodeList();
	    } else if(CodeListAction.dmng.equals(codenm)) {
			codeList = codeListMapper.getDmngCodeList();
		} else if(CodeListAction.infotp.equals(codenm)) {
			codeList = codeListMapper.getInfoTpCodeList();
		} else if(CodeListAction.dmnginfotp.equals(codenm)) {
			codeList = codeListMapper.getDmngInfoTpCodeList();
		} else if(CodeListAction.usergroup.equals(codenm)) {
			codeList = codeListMapper.getUsergroupCodeList();
		} else if(CodeListAction.userglnm.equals(codenm)) {
			codeList = codeListMapper.getUsergrouplnmCodeList();
		} else if(CodeListAction.dbmstypvers.equals(codenm)) {
			codeList = codeListMapper.getDbmsTypVersCodeList();
		} else if(CodeListAction.COM_DCD.equals(codenm)) {
			codeList = codeListMapper.getComDcdList();
		} else if(CodeListAction.approvegroup.equals(codenm)) {
			codeList = codeListMapper.getApproveGroup();
		} else if(CodeListAction.connTrgDbms.equals(codenm)) {
			codeList = codeListMapper.getConnTrgDbms();
		} else if(CodeListAction.cateCode.getAction().equals(codenm)) {
			codeList = codeListMapper.getcateCodeList();
		} else if(CodeListAction.abrTempList.getAction().equals(codenm)) {
			codeList = codeListMapper.getAbrTempList(null);
		} else if(CodeListAction.anaDgr.equals(codenm)) { //프로파일 실행차수
			codeList = codeListMapper.getAnaDgr();
		} else if(CodeListAction.bizrAnaDgr.equals(codenm)) { //업무규칙 실행차수
			codeList = codeListMapper.getBizrAnaDgr();
		} else if(CodeListAction.dbmsDataType.equals(codenm)) { //DBMS 데이터타입
			codeList = codeListMapper.getDbmsDataType();
		} else if(CodeListAction.dbmsDataTyp.equals(codenm)) { //DBMS 데이터타입(double_select)
			codeList = codeListMapper.getDbmsDataTyp();
		} else if(CodeListAction.subj.equals(codenm)) { //DBMS 데이터타입(double_select)
			codeList = codeListMapper.getSubjLnm();
		} else if(CodeListAction.dbSch.equals(codenm)) { //DBMS 데이터타입(double_select)
			codeList = codeListMapper.getDbSchLnm();
		} else if(CodeListAction.connTrgSch.equals(codenm)) { //진단대상, 스키마명(PNM반환) (double_select)
			codeList = codeListMapper.getConnTrgSch();
		} else if(CodeListAction.connTrgSchId.equals(codenm)) { //진단대상, 스키마명 (ID반환) (double_select)
			codeList = codeListMapper.getConnTrgSchId();
		} else if(CodeListAction.LangType.equals(codenm)) { //프로그램 영향도 언어 타입
			codeList = codeListMapper.getLangType();
		} else if  (CodeListAction.dmngLowDgr.equals(codenm)) { // 최하위 도메인그룹 리스트 반환....
			codeList = codeListMapper.getDmngLowDgr();
		} else if  (CodeListAction.subjPdmTbl.equals(codenm)) { // 주제영역/물리테이블 리스트 조회...
			codeList = codeListMapper.getSubjPdmTbl();
		} else if  (CodeListAction.dbSchForDoubleSelectIBS.equals(codenm)) { // IBSheet용 더블select 스키마명
			codeList = codeListMapper.getDbSchForDoubleSelectIBS();
		} else if  (CodeListAction.dbConnTrgIdInDbRole.equals(codenm)) { // WAA_DB_ROLE에 등록된 DBMS만 가져오는 쿼리...
			codeList = codeListMapper.getDbConnTrgIdInDbRole();
		} else if  (CodeListAction.roleNmForDoubleSelectIBS.equals(codenm)) { // IBSheet용 더블SELECT ROLE명
			codeList = codeListMapper.getRoleNmForDoubleSelectIBS();
		} else if  (CodeListAction.dbRoleNm.equals(codenm)) { // IBSheet용 더블SELECT ROLE명
			codeList = codeListMapper.getDbRoleNm();
		} else if  (CodeListAction.dbSchLnm.equals(codenm)) { // 스키마명만...
			codeList = codeListMapper.getDbSchLnmOnly();
		} else if  (CodeListAction.tblSpac.equals(codenm)) { // 테이블스페이스명.
			codeList = codeListMapper.getTblSpacLnm();
		} else if  (CodeListAction.tblSpacId.equals(codenm)) { // 테이블스페이스id.
			codeList = codeListMapper.getTblSpacId();
		}
		 else if  (CodeListAction.idxSpacId.equals(codenm)) { // 인덱스스페이스id.
			codeList = codeListMapper.getIdxSpacId();
		}
		//도메인코드
		else if  (CodeListAction.clSystmTyCode.getAction().equals(codenm)) {
			codeList = codeListMapper.getClSystmTyCode();
		}
		else if  (CodeListAction.clSystmTyIemCode.getAction().equals(codenm)) {
			codeList = codeListMapper.getClSystmTyIemCode();
		}else if(CodeListAction.codedmnginfotp.getAction().equals(codenm)) { //진단대상, 스키마명 (ID반환) (double_select) 메타관리대상만
			codeList = codeListMapper.getCodeDmngInfoTpCodeList();
		}else if(CodeListAction.chnlTpDtls.getAction().equals(codenm)) { //메시지 코드중 채널유형코드 / 채널세부코드  (double_select) 
			codeList = codeListMapper.getchnlTpDtls();
		}else if(CodeListAction.bizDtls.getAction().equals(codenm)) { //메시지 코드중 메시지유형 / 업무구분  (double_select)  
			codeList = codeListMapper.getbizDtls();
		}else if(CodeListAction.brAnaDgr.equals(codenm)) {
			//getAction 할 필요 X
			codeList = codeListMapper.getBrAnaDgr();
		}

		return codeList;
	}

	/** IBS용 목록성코드리스트 처리-코드명을 enum 클래스 이용 insomnia */
	@Override
	public ComboIBSVo getCodeListIBS(CodeListAction codenm) {
		ComboIBSVo comboIbs = new ComboIBSVo();
		StringBuilder code = new StringBuilder();
		StringBuilder text = new StringBuilder();

		List<CodeListVo> codeList = getCodeList(codenm);

		int i = 0;
		for (CodeListVo vo : codeList) {
			if(i++ > 0) {
				code.append("|");
				text.append("|");
			}
			code.append(vo.getCodeCd());
			text.append(vo.getCodeLnm());
		}

		comboIbs.ComboCode = code.toString();
		comboIbs.ComboText = text.toString();

		return comboIbs;
	}

	/** insomnia */
	@Override
	public Map<String, ComboIBSVo> getDbmsDataTypIBS(List<CodeListVo> list) {
		int fidx = 0;
		int tidx = -1;
		int cnt = 0;
		String preupcd = "";
		Map<String, ComboIBSVo> map = new HashMap<String, ComboIBSVo>();
		for (CodeListVo codevo : list) {

			String upcd = codevo.getUpcodeCd();
			if (cnt == 0) {
				preupcd = upcd;
			}

			if(!preupcd.equals(upcd)) {
				tidx = cnt;
				ComboIBSVo comboibs = getCodeListIBS(list.subList(fidx, tidx));
				map.put(preupcd, comboibs);

				fidx = cnt;

			}

			cnt++;
			preupcd = upcd;

		}
		tidx = list.size();
		ComboIBSVo comboibs = getCodeListIBS(list.subList(fidx, tidx));
		map.put(preupcd, comboibs);
	
		return map;
	}

	
	/**
	 * 표준분류에 따른 도메인그룹 변경을 위해 표준분류를 키값으로
	 * */
	@Override
	public Map<String, ComboIBSVo> getDmngByStndAsrtIBS(List<CodeListVo> list) {
		int fidx = 0;
		int tidx = -1;
		int cnt = 0;
		String preStndAsrt = "";
		Map<String, ComboIBSVo> map = new HashMap<String, ComboIBSVo>();
		for (CodeListVo codevo : list) {

			String stndAsrt = codevo.getStndAsrt();
			if (cnt == 0) {
				preStndAsrt = stndAsrt;
			}

			if(!preStndAsrt.equals(stndAsrt)) {
				tidx = cnt;
				ComboIBSVo comboibs = getCodeListIBS(list.subList(fidx, tidx));
				map.put(preStndAsrt, comboibs);

				fidx = cnt;

			}

			cnt++;
			preStndAsrt = stndAsrt;

		}
		tidx = list.size();
		ComboIBSVo comboibs = getCodeListIBS(list.subList(fidx, tidx));
		map.put(preStndAsrt, comboibs);
	
		return map;
	}
	
	//2019.5.21
	@Override
	public List<CodeListVo> getAuthOrgCdList(LoginVO user) {
		
		return codeListMapper.selectAuthOrgCdList(user);  
	}
	@Override
	public List<CodeListVo> getInfoSysCdEachList(String orgCd) {

		return codeListMapper.selectInfoSysCdEachList(orgCd);  
	}

	
}
