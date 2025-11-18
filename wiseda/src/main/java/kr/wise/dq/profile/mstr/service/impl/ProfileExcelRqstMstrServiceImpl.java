/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileServiceImpl.java
 * 2. Package : kr.wise.dq.criinfo.profile.service.impl
 * 3. Comment : 
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:35:18
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.profile.mstr.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqMstrMapper;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtls;
import kr.wise.commons.rqstmst.service.WaqRqstVrfDtlsMapper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaMapper;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaMapper;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaAnaMapper;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfMapper;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.WaqPrfPtrAnaMapper;
import kr.wise.dq.profile.colptrana.service.WaqPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WaqPrfRngAnaMapper;
import kr.wise.dq.profile.colrngana.service.WaqPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.ProfileExcelRqstMstrService;
import kr.wise.dq.profile.mstr.service.WaqPrfDqiMapMapper;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrMapper;
import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColMapper;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelTblMapper;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelTblVO;
import kr.wise.dq.profile.tblunq.service.WaqPrfUnqColMapper;
import kr.wise.dq.profile.tblunq.service.WaqPrfUnqColVO;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ColProfileServiceImpl.java
 * 3. Package  : kr.wise.dq.criinfo.profile.service.impl
 * 4. Comment  : 
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 3. 24. 오후 1:35:18
 * </PRE>
 */
@Service("ProfileExcelRqstMstrService")
public class ProfileExcelRqstMstrServiceImpl implements ProfileExcelRqstMstrService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	@Inject
	private RequestMstService requestMstService;
	
	@Inject
	private RequestApproveService requestApproveService;
	
	@Inject
	private CmcdCodeService cmcdCodeService;
	
	@Inject
	private WaqMstrMapper waqMstrMapper;
	
	@Inject
	private WaqRqstVrfDtlsMapper waqRqstVrfDtlsMapper;
	
	@Inject
	private WaqPrfMstrMapper waqPrfMstrMapper;
	
	//컬럼분석
	@Inject
	private WaqPrfColAnaMapper waqPc01Mapper;
	
	//유효값분석
	@Inject
	private WaqPrfEfvaAnaMapper waqPc02Mapper;
	@Inject
	private WaqPrfEfvaUserDfMapper waqPc02UserDfMapper;
	
	
	/*날짜형식분석 (PC03) */
	@Inject
	private WaqPrfDtfrmAnaMapper waqPc03Mapper;
	
	/*범위분석 (PC04) */
	@Inject
	private WaqPrfRngAnaMapper waqPc04Mapper;
	
	/*유효분석 (PC05) */
	@Inject
	private WaqPrfPtrAnaMapper waqPc05Mapper;
	
	/* 관계분석 (PT01) */
	@Inject
	private WaqPrfRelTblMapper waqPt01TblMapper;
	@Inject
	private WaqPrfRelColMapper waqPt01ColMapper;
	
	/*중복분석 (PT02) */
	@Inject
	private WaqPrfUnqColMapper waqPt02Mapper;
	
//	/*유효값분석 (PC02) */
//	@Inject
//	private WamPrfEfvaAnaMapper pc02WamMapper;
//	@Inject
//	private WahPrfEfvaAnaMapper pc02WahMapper;
//	/*유효값분석 사용자 정의 (PC02 - USER) */
//	@Inject
//	private WamPrfEfvaUserDfMapper pc02UserDfWamMapper;
//	@Inject
//	private WahPrfEfvaUserDfMapper pc02UserDfWahMapper;
	
	
	/* 프로파일 마스터 저장 */
	public int register(WaqMstr mstVo, List<?> reglist) throws Exception {
		
		String prfKndCd = mstVo.getBizDtlCd();
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if( "N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}
		
		int result = 0;
		String rqstNo = mstVo.getRqstNo();

		
		WaqPrfMstrVO waqPrfMstrVo = new WaqPrfMstrVO();
		
		
		if(reglist != null && prfKndCd.equals("PC01")){
			for (WaqPrfColAnaVO saveVo : (ArrayList<WaqPrfColAnaVO>) reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				saveVo.setRqstNo(rqstNo);
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setPrfKndCd(prfKndCd);
				
				String prfNm = prfKndCd + "|" +saveVo.getDbConnTrgPnm() + "|" + saveVo.getDbSchPnm() + "|"+ saveVo.getDbcTblNm() + "|" + saveVo.getObjNm();
				saveVo.setPrfNm(prfNm);
				
				waqPrfMstrVo = saveVo;
				
				logger.debug(" waqPrfMstrVo {} ", waqPrfMstrVo);
				
				String tmpStstus = saveVo.getIbsStatus();
				
				if("I".equals(tmpStstus)) {
					result += waqPrfMstrMapper.insertSelective(waqPrfMstrVo);
					result += waqPc01Mapper.insertSelective(saveVo);
				} 
				else if ("U".equals(tmpStstus)) {
					result += waqPrfMstrMapper.updateByPrimaryKeySelective(waqPrfMstrVo);
					result += waqPc01Mapper.updateByPrimaryKeySelective(saveVo);
				} 
			}
		}
		
		//유효값분석
		else if(reglist != null && prfKndCd.equals("PC02")){
			for (WaqPrfEfvaUserDfVO saveVo : (ArrayList<WaqPrfEfvaUserDfVO>) reglist) {
				//프로파일 마스터
				waqPrfMstrVo = new WaqPrfMstrVO();
				//유효값정보 테이블
				WaqPrfEfvaAnaVO waqPrfEfvaAnaVO = new WaqPrfEfvaAnaVO();
				
				saveVo.setRqstNo(rqstNo);
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setPrfKndCd(prfKndCd);
				
				String prfNm = prfKndCd + "|" +saveVo.getDbConnTrgPnm() + "|" + saveVo.getDbSchPnm() + "|"+ saveVo.getDbcTblNm() + "|" + saveVo.getObjNm() + "|" + saveVo.getEfvaAnaKndCd();
				saveVo.setPrfNm(prfNm);
				
				//프로파일 마스터
				waqPrfMstrVo = saveVo;
				//유효값정보
				waqPrfEfvaAnaVO = saveVo;
				
				logger.debug(" waqPrfEfvaAnaVO {} ", waqPrfEfvaAnaVO);
				
				String tmpStstus = saveVo.getIbsStatus();
				
				if("I".equals(tmpStstus)) {
					result += waqPrfMstrMapper.insertSelective(waqPrfMstrVo);
					//유효값분석정보
					result += waqPc02Mapper.insertSelective(waqPrfEfvaAnaVO);
					//사용자정의유효값
					if(waqPrfEfvaAnaVO.getEfvaAnaKndCd().equals("USER")){
						result += waqPc02UserDfMapper.insertSelective(saveVo);
					}
				} 
				else if ("U".equals(tmpStstus)) {
					result += waqPrfMstrMapper.updateByPrimaryKeySelective(waqPrfMstrVo);
					//유효값분석정보
					result += waqPc02Mapper.updateByPrimaryKeySelective(waqPrfEfvaAnaVO);
					//사용자정의유효값
					if(waqPrfEfvaAnaVO.getEfvaAnaKndCd().equals("USER")){
						result += waqPc02UserDfMapper.updateByPrimaryKeySelective(saveVo);
					}
				} 
			}
		}
	
		//날짜형식분석
		else if(reglist != null && prfKndCd.equals("PC03")){
			for (WaqPrfDtfrmAnaVO saveVo : (ArrayList<WaqPrfDtfrmAnaVO>) reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				saveVo.setRqstNo(rqstNo);
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setPrfKndCd(prfKndCd);
				
				String prfNm = prfKndCd + "|" +saveVo.getDbConnTrgPnm() + "|" + saveVo.getDbSchPnm() + "|"+ saveVo.getDbcTblNm() + "|" + saveVo.getObjNm();
				saveVo.setPrfNm(prfNm);
				
				waqPrfMstrVo = saveVo;
				
				logger.debug(" waqPrfMstrVo {} ", waqPrfMstrVo);
				
				String tmpStstus = saveVo.getIbsStatus();
				
				if("I".equals(tmpStstus)) {
					result += waqPrfMstrMapper.insertSelective(waqPrfMstrVo);
					result += waqPc03Mapper.insertSelective(saveVo);
				} 
				else if ("U".equals(tmpStstus)) {
					result += waqPrfMstrMapper.updateByPrimaryKeySelective(waqPrfMstrVo);
					result += waqPc03Mapper.updateByPrimaryKeySelective(saveVo);
				} 
			}
		}
		
		//범위분석
		else if(reglist != null && prfKndCd.equals("PC04")){
			for (WaqPrfRngAnaVO saveVo : (ArrayList<WaqPrfRngAnaVO>) reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				saveVo.setRqstNo(rqstNo);
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setPrfKndCd(prfKndCd);
				
				String prfNm = prfKndCd + "|" +saveVo.getDbConnTrgPnm() + "|" + saveVo.getDbSchPnm() + "|"+ saveVo.getDbcTblNm() + "|" + saveVo.getObjNm();
				saveVo.setPrfNm(prfNm);
				
				waqPrfMstrVo = saveVo;
				
				logger.debug(" waqPrfMstrVo {} ", waqPrfMstrVo);
				
				String tmpStstus = saveVo.getIbsStatus();
				
				if("I".equals(tmpStstus)) {
					result += waqPrfMstrMapper.insertSelective(waqPrfMstrVo);
					result += waqPc04Mapper.insertSelective(saveVo);
				} 
				else if ("U".equals(tmpStstus)) {
					result += waqPrfMstrMapper.updateByPrimaryKeySelective(waqPrfMstrVo);
					result += waqPc04Mapper.updateByPrimaryKeySelective(saveVo);
				} 
			}
		}
		
		//유효패턴분석
		else if(reglist != null && prfKndCd.equals("PC05")){
			for (WaqPrfPtrAnaVO saveVo : (ArrayList<WaqPrfPtrAnaVO>) reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				saveVo.setRqstNo(rqstNo);
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setPrfKndCd(prfKndCd);
				
				String prfNm = prfKndCd + "|" +saveVo.getDbConnTrgPnm() + "|" + saveVo.getDbSchPnm() + "|"+ saveVo.getDbcTblNm() + "|" + saveVo.getObjNm();
				saveVo.setPrfNm(prfNm);
				
				waqPrfMstrVo = saveVo;
				
				logger.debug(" waqPrfMstrVo {} ", waqPrfMstrVo);
				
				String tmpStstus = saveVo.getIbsStatus();
				
				if("I".equals(tmpStstus)) {
					result += waqPrfMstrMapper.insertSelective(waqPrfMstrVo);
					if(!UtilString.null2Blank(saveVo.getUserDfPtr()).equals("") ){
						result += waqPc05Mapper.insertSelective(saveVo);
					}
				} 
				else if ("U".equals(tmpStstus)) {
					result += waqPrfMstrMapper.updateByPrimaryKeySelective(waqPrfMstrVo);
					if(!UtilString.null2Blank(saveVo.getUserDfPtr()).equals("") ){
						result += waqPc05Mapper.updateByPrimaryKeySelective(saveVo);
					}
				} 
			}
		}
		
		//관계분석
		else if(reglist != null && prfKndCd.equals("PT01")){
			for (WaqPrfRelColVO saveVo : (ArrayList<WaqPrfRelColVO>) reglist) {
				//프로파일 마스터
				waqPrfMstrVo = new WaqPrfMstrVO();
				//관계테이블
				WaqPrfRelTblVO waqPrfRelTblVO = new WaqPrfRelTblVO();
				
				saveVo.setRqstNo(rqstNo);
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setPrfKndCd(prfKndCd);
				
				String prfNm = prfKndCd + "|" +saveVo.getDbConnTrgPnm() + "|" + saveVo.getDbSchPnm() + "|"+ saveVo.getDbcTblNm() + "|" + saveVo.getObjNm();
				saveVo.setPrfNm(prfNm);
				
				//프로파일 마스터
				waqPrfMstrVo = saveVo;
				//관계테이블
				waqPrfRelTblVO = saveVo;
				
				logger.debug(" waqPrfMstrVo {} ", waqPrfMstrVo);
				
				String tmpStstus = saveVo.getIbsStatus();
				
				if("I".equals(tmpStstus)) {
					result += waqPrfMstrMapper.insertSelective(waqPrfMstrVo);
					result += waqPt01TblMapper.insertSelective(waqPrfRelTblVO);
					result += waqPt01ColMapper.insertSelective(saveVo);
				} 
				else if ("U".equals(tmpStstus)) {
					result += waqPrfMstrMapper.updateByPrimaryKeySelective(waqPrfMstrVo);
//					result += waqPrfDqiMapMapper.updateWAQPrfNmByKey(waqPrfMstrVo);
					result += waqPt01TblMapper.updateByPrimaryKeySelective(waqPrfRelTblVO);
					result += waqPt01ColMapper.updateByPrimaryKeySelective(saveVo);
				} 
			}
		}
		
		//중복분석
		else if(reglist != null && prfKndCd.equals("PT02")){
			for (WaqPrfUnqColVO saveVo : (ArrayList<WaqPrfUnqColVO>) reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				saveVo.setRqstNo(rqstNo);
				saveVo.setFrsRqstUserId(userid);
				saveVo.setRqstUserId(userid);
				saveVo.setPrfKndCd(prfKndCd);
				
				String prfNm = prfKndCd + "|" +saveVo.getDbConnTrgPnm() + "|" + saveVo.getDbSchPnm() + "|"+ saveVo.getDbcTblNm() + "|" + saveVo.getObjNm();
				saveVo.setPrfNm(prfNm);
				
				waqPrfMstrVo = saveVo;
				
				logger.debug(" waqPrfMstrVo {} ", waqPrfMstrVo);
				
				String tmpStstus = saveVo.getIbsStatus();
				
				if("I".equals(tmpStstus)) {
					result += waqPrfMstrMapper.insertSelective(waqPrfMstrVo);
					result += waqPt02Mapper.insertSelective(saveVo);
				} 
				else if ("U".equals(tmpStstus)) {
					result += waqPrfMstrMapper.updateByPrimaryKeySelective(waqPrfMstrVo);
					result += waqPt02Mapper.updateByPrimaryKeySelective(saveVo);
				}
			}
		}
		
		
		
		
		//임시저장 상태로 변경....
		mstVo.setRqstStepCd("S"); 
		requestMstService.updateRqstPrcStep(mstVo);
		
		return result;
	}
	
	
	public int check(WaqMstr mstVo) {
		String rqstNo = mstVo.getRqstNo();
		String bizDcd = mstVo.getBizDcd();
		String bizDtlDcd = mstVo.getBizDtlCd();
		String prfKndCd = mstVo.getBizDtlCd();
		int result = 0;
		
		WaqRqstVrfDtls waqRqstVrfDtls = new WaqRqstVrfDtls();
		waqRqstVrfDtls.setRqstNo(rqstNo);
		waqRqstVrfDtls.setBizDtlCd(bizDtlDcd);
		
		//검증코드  검토상태코드  초기화
		//프로파일 MSTR 
		result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_MSTR");
		
		//컬럼분석 검토상태코드  초기화
		if(prfKndCd.equals("PC01")) {
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_COL_ANA");
		}
		//유효값분석 검토상태코드  초기화
		if(prfKndCd.equals("PC02")) {
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_EFVA_ANA");
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_EFVA_USER_DF");
		}
		//날짜형식분석 검토상태코드  초기화
		else if(prfKndCd.equals("PC03")) {
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_DTFRM_ANA");
		}
		//범위분석 검토상태코드  초기화
		else if(prfKndCd.equals("PC04")) {
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_RNG_ANA");
		}
		//유효패턴분석 검토상태코드  초기화
		else if(prfKndCd.equals("PC05")) {
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_PTR_USER_DF");
		}
		//관계분석 검토상태코드  초기화
		else if(prfKndCd.equals("PT01")) {
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_REL_TBL");
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_REL_COL");
		}
		//중복분석 검토상태코드  초기화
		else if(prfKndCd.equals("PT02")) {
			result += waqPrfMstrMapper.updateCheckInit(rqstNo, "WAQ_PRF_UNQ_COL");
		}
		
		//검증결과 상세테이블 삭제
		result += waqRqstVrfDtlsMapper.deleteSelective(waqRqstVrfDtls);
		//진단대상명 ID UPDATE
		result += waqPrfMstrMapper.updateDbConnTrgId(rqstNo);
		//스키마명 ID UPDATE
		result += waqPrfMstrMapper.updateDbSchId(rqstNo);
		
		//프로파일 정보 update
		result += waqPrfMstrMapper.updatePrfInfo(rqstNo);
		
		// 컬럼분석
		if(prfKndCd.equals("PC01")){
			// 상세정보 UPDATE
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_COL_ANA", rqstNo );
			
			//변경된 데이터 없음(PRF00)
			result += waqPc01Mapper.checkNoChg("WAQ_PRF_COL_ANA", rqstNo, bizDtlDcd, "PRF00");
			
			//요청서내 중복자료 검증(PRF01)
			result += waqPrfMstrMapper.checkDupPrf("WAQ_PRF_MSTR", rqstNo,  bizDtlDcd, "PRF01");
			
			//컬럼분석 정보 미등록
			result += waqPc01Mapper.checkPc01Info("WAQ_PRF_COL_ANA",rqstNo, bizDtlDcd, "PRF07");
		}
		// 유효값분석
		if(prfKndCd.equals("PC02")){
			// 상세정보 UPDATE
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_EFVA_ANA", rqstNo );
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_EFVA_USER_DF", rqstNo );
			
			//코드테이블 사용 일경우 코드테이블진단대상ID, 스키마ID UPDATE
			result += waqPc02Mapper.updateCdDbConnId(rqstNo);
			result += waqPc02Mapper.updateCdDbSchId(rqstNo);
			
			//코드테이블연계 일경우 코드테이블 정보 미입력 검증
			result += waqPc02Mapper.chkCdTblInfo("WAQ_PRF_EFVA_ANA", rqstNo, bizDtlDcd, "PRF16");
			
			//미존재 코드테이블진단대상명
			result += waqPc02Mapper.chkCdDbConnTrg("WAQ_PRF_EFVA_ANA", rqstNo, bizDtlDcd, "PRF17");
			
			//미존재 코드테이블스키마명
			result += waqPc02Mapper.chkCdDbSch("WAQ_PRF_EFVA_ANA", rqstNo, bizDtlDcd, "PRF18");
			
			//미존재 코드테이블명
			result += waqPc02Mapper.chkCdDbcTbl("WAQ_PRF_EFVA_ANA", rqstNo, bizDtlDcd, "PRF19");
			
			//미존재 코드값컬럼명
			result += waqPc02Mapper.chkCdDbcCol(rqstNo, bizDtlDcd, "PRF20");
			
			//미존재 코드ID컬럼명
			result += waqPc02Mapper.chkCdIdDbcCol(rqstNo, bizDtlDcd, "PRF21");
			
			// 코드테이블연계 일경우 코드ID컬럼명은 존재시 코드ID 작성여부 검증 
			result += waqPc02Mapper.chkCdIdNm("WAQ_PRF_EFVA_ANA", rqstNo, bizDtlDcd, "PRF22");
			
			// 코드테이블연계 일경우 코드ID 존재시  코드ID컬럼명 작성여부 검증 
			result += waqPc02Mapper.chkCdId("WAQ_PRF_EFVA_ANA", rqstNo, bizDtlDcd, "PRF23");
	
			//사용자정의유효값
			//사용자정의 유효값 미입력
			result += waqPc02UserDfMapper.chkUserDfReg(rqstNo,bizDtlDcd, "PRF24");
			
			//메타연계
			//메타도메인 미존재
			result += waqPc02Mapper.chkMetaDmn(rqstNo,bizDtlDcd, "PRF25");
			
			//변경된 데이터 없음(PRF00)
			result += waqPc02UserDfMapper.checkNoChg(rqstNo, bizDtlDcd, "PRF00");
			
			//요청서내 중복자료 검증(PRF01)
			result += waqPc02Mapper.checkDupPrf(rqstNo,  bizDtlDcd, "PRF01");
			
			//요청서내 중복자료 검증(PRF01)
			//사용자정의 유효값일 경우
			result += waqPc02UserDfMapper.checkDupPrfUser(rqstNo,  bizDtlDcd, "PRF01");
			
		}
		//날짜형식분석
		else if(prfKndCd.equals("PC03")) {
			// 상세정보 UPDATE
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_DTFRM_ANA", rqstNo );
			
			//변경된 데이터 없음(PRF00)
			result += waqPc03Mapper.checkNoChg("WAQ_PRF_DTFRM_ANA", rqstNo, bizDtlDcd, "PRF00");
			
			//요청서내 중복자료 검증(PRF01)
			result += waqPrfMstrMapper.checkDupPrf("WAQ_PRF_MSTR", rqstNo,  bizDtlDcd, "PRF01");
		}
		
		//범위분석
		else if(prfKndCd.equals("PC04")) {
			// 상세정보 UPDATE
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_RNG_ANA", rqstNo );
			
			//변경된 데이터 없음(PRF00)
			result += waqPc04Mapper.checkNoChg("WAQ_PRF_RNG_ANA", rqstNo, bizDtlDcd, "PRF00");
			
			//요청서내 중복자료 검증(PRF01)
			result += waqPrfMstrMapper.checkDupPrf("WAQ_PRF_MSTR", rqstNo,  bizDtlDcd, "PRF01");
			
			//연결자 존재할경우 범위연산자2, 범위유효값2 값 존재
			result += waqPc04Mapper.checkRngOpr2("WAQ_PRF_RNG_ANA", rqstNo, bizDtlDcd, "PRF08");
			
			//범위연산자2 존재할 경우 연결자, 범위유효값2 값 존재
			result += waqPc04Mapper.checkRngCnc("WAQ_PRF_RNG_ANA", rqstNo, bizDtlDcd, "PRF09");
			
			//범위연산자2, 범위유효값2 존재할 경우 연결자  존재
			result += waqPc04Mapper.checkRngCnc2("WAQ_PRF_RNG_ANA", rqstNo, bizDtlDcd, "PRF10");
			
		}
		
		//유효패턴분석
		else if(prfKndCd.equals("PC05")) {
			// 상세정보 UPDATE
			//result += waqPc05Mapper.updatePrfDtlInfo("WAQ_PRF_PTR_USER_DF", rqstNo );
			// 상세정보 UPDATE
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_PTR_USER_DF", rqstNo );
			
			//변경된 데이터 없음(PRF00)
			result += waqPc05Mapper.checkNoChg("WAQ_PRF_PTR_USER_DF", rqstNo, bizDtlDcd, "PRF00");
			
			//요청서내 중복자료 검증(PRF01)
			result += waqPc05Mapper.checkDupPrf("WAQ_PRF_PTR_USER_DF", rqstNo,  bizDtlDcd, "PRF01");
		}
		
		//관계분석
		else if(prfKndCd.equals("PT01")) {
			//부모진단대상ID UPDATE
			result += waqPt01TblMapper.updatePaDbConnTrgId(rqstNo);
			//부모스키마ID UPDAT
			result += waqPt01TblMapper.updatePaDbSchId(rqstNo);
			
			//부모 테이블 상세정보 UPDATE
			//result += waqPt01TblMapper.updatePrfDtlInfo("WAQ_PRF_REL_TBL", rqstNo );
			//관계컬럼 테이블 상세정보 UPDATE
			//result += waqPt01ColMapper.updatePrfDtlInfo("WAQ_PRF_REL_COL", rqstNo );
			
			//부모 테이블 상세정보 UPDATE
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_REL_TBL", rqstNo );
			//관계컬럼 테이블 상세정보 UPDATE
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_REL_COL", rqstNo );
			
			//변경된 데이터 없음(PRF00)
			result += waqPt01ColMapper.checkNoChg(rqstNo, bizDtlDcd, "PRF00");
			
			//요청서내 중복자료 검증(PRF01)
			result += waqPt01ColMapper.checkDupPrf(rqstNo,  bizDtlDcd, "PRF01");
			
			//미존재 부모진단대상명
			result += waqPt01TblMapper.chkPaDbConnTrg("WAQ_PRF_REL_TBL", rqstNo, bizDtlDcd, "PRF11");
			
			//미존재 부모스키마명
			result += waqPt01TblMapper.chkPaDbSch("WAQ_PRF_REL_TBL", rqstNo, bizDtlDcd, "PRF12");
			
			//미존재 부모테이블명
			result += waqPt01TblMapper.chkPaDbcTbl("WAQ_PRF_REL_TBL", rqstNo, bizDtlDcd, "PRF13");
			
			//미존재 부모컬럼명
			result += waqPt01ColMapper.chkPaDbcCol(rqstNo, bizDtlDcd, "PRF14");
			
			//미존재 자식컬럼명
			result += waqPt01ColMapper.chkChDbcCol(rqstNo, bizDtlDcd, "PRF15");
			
		}
		
		//중복분석분석
		else if(prfKndCd.equals("PT02")) {
			// 상세정보 UPDATE
			//result += waqPt02Mapper.updatePrfDtlInfo("WAQ_PRF_UNQ_COL", rqstNo );
			result += waqPrfMstrMapper.updatePrfDtlInfo("WAQ_PRF_UNQ_COL", rqstNo );
			
			//변경된 데이터 없음(PRF00)
			result += waqPt02Mapper.checkNoChg("WAQ_PRF_UNQ_COL", rqstNo, bizDtlDcd, "PRF00");
			
			//요청서내 중복자료 검증(PRF01)
			result += waqPt02Mapper.checkDupPrf("WAQ_PRF_UNQ_COL", rqstNo,  bizDtlDcd, "PRF01");
			
			//중복컬럼 검증
			result += waqPt02Mapper.chkDbcCol("WAQ_PRF_UNQ_COL", rqstNo,  bizDtlDcd, "PRF06");
			
			//중복컬럼 존재여부 검증
			result += waqPt02Mapper.chkDbcColYN("WAQ_PRF_UNQ_COL", rqstNo,  bizDtlDcd, "PRF06");
		}
		
		if(!prfKndCd.equals("PC01")) {
			
			int curLvl = 0, fullLvl = 0;
			
			//품질지표ID UPDATE
			result += waqPrfMstrMapper.updateDqiId(rqstNo);
			//데이터품질지표 미존재
			result += waqPrfMstrMapper.checkNotExistDqi(rqstNo, bizDtlDcd, "BRA04");
			
			//데이터품질지표 최하위 체크
			List<WaqPrfMstrVO> saveVo = waqPrfMstrMapper.selectWaqList(rqstNo);
			
			for(int i=0; i<saveVo.size(); i++) {
				result += waqPrfMstrMapper.checkLowRank(rqstNo, bizDtlDcd, "BRA17", saveVo.get(i).getDqiLnm(), saveVo.get(i).getRqstSno());
			}
		}

		//미존재 프로파일(삭제시)(PRF02)
		result += waqPrfMstrMapper.checkNotExistPrf("WAQ_PRF_MSTR", rqstNo, bizDtlDcd, "PRF02");
				
		//미존재 진단대상명(PRF03)
		result += waqPrfMstrMapper.chkDbConnTrg("WAQ_PRF_MSTR", rqstNo,  bizDtlDcd, "PRF03");
				
		//미존재 스키마명(PRF04)
		result += waqPrfMstrMapper.chkDbSch("WAQ_PRF_MSTR", rqstNo,  bizDtlDcd, "PRF04");
		
		//진단대상 테이블 검증(PRF05)
		result += waqPrfMstrMapper.chkDbcTbl("WAQ_PRF_MSTR", rqstNo,  bizDtlDcd, "PRF05");
		
		//테이블분석 일경우 제외
		if(!prfKndCd.equals("PT01") && !prfKndCd.equals("PT02")) {
			//진단대상컬럼 검증
			result += waqPrfMstrMapper.chkDbcCol("WAQ_PRF_MSTR", rqstNo,  bizDtlDcd, "PRF06");
		}
		
		
		//등록가능여부(검증코드) 업데이트
		// 컬럼분석
		if(prfKndCd.equals("PC01")){
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_COL_ANA", rqstNo, bizDtlDcd);
		}
		//유효값분석
		else if(prfKndCd.equals("PC02")){
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_EFVA_ANA", rqstNo, bizDtlDcd);
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_EFVA_USER_DF", rqstNo, bizDtlDcd);
		}
		//날짜형식
		else if(prfKndCd.equals("PC03")){
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_DTFRM_ANA", rqstNo, bizDtlDcd);
		}
		//범위분석
		else if(prfKndCd.equals("PC04")){
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_RNG_ANA", rqstNo, bizDtlDcd);
		}
		//유효패턴분석
		else if(prfKndCd.equals("PC05")) {
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_PTR_USER_DF", rqstNo, bizDtlDcd);
		}
		//관계분석
		else if(prfKndCd.equals("PT01")) {
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_REL_TBL", rqstNo, bizDtlDcd);
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_REL_COL", rqstNo, bizDtlDcd);
		}
		//중복분석
		else if(prfKndCd.equals("PT02")) {
			result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_UNQ_COL", rqstNo, bizDtlDcd);
		}
		
		//프로파일 마스터 등록가능여부(검증코드) 업데이트
		result += waqRqstVrfDtlsMapper.updateVrfCd("WAQ_PRF_MSTR", rqstNo, bizDtlDcd);
				
		//마스터 정보 업데이트...
		requestMstService.updateRqstPrcStep(mstVo);

		//요청서명 업데이트
		requestMstService.updateRequestMsterNm(mstVo);
		
		return result;
	}

	/** 결재 승인 처리 업데이트.... insomnia
	 * @throws Exception */
	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception {
		int result = 0;

		String rqstNo = mstVo.getRqstNo();
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		logger.debug("결재 승인 처리 시작-결재자:{},요청번호:{}",userid, rqstNo );

		String prfKndCd = mstVo.getBizDtlCd();
		WaqPrfMstrVO waqPrfMstrVo = new WaqPrfMstrVO();
		
		
		// 1.요청 테이블의 내용을 업데이트 한다. (검토상태와 검토내용 업데이트)
		if(prfKndCd.equals("PC01")){
			for (WaqPrfColAnaVO savevo : (ArrayList<WaqPrfColAnaVO>)reglist) {
				 waqPrfMstrVo = new WaqPrfMstrVO();
				 savevo.setAprvUserId(userid);
				 savevo.setRqstNo(rqstNo);				
				 waqPrfMstrVo = savevo;
				 result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_COL_ANA", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				 result  += waqPrfMstrMapper.updatervwStsCd(waqPrfMstrVo);
				
			}
		}
		
		//유효값분석
		else if(prfKndCd.equals("PC02")){
			//1 : N 인 프로파일 유효패턴, 유효값, 중복, 관계 일경우  WAQ_PRF_MSTR, 프로파일 상세정보테이블  정리
			//프로파일 마스터 그룹핑 후 MAX(RQST_SNO) 외 삭제
			waqPrfMstrMapper.deleteByPrfNmGroup(rqstNo);
			//프로파일 마스터 삭제된 ROW  - > 부모테이블  정보 에서도 삭제
			waqPrfMstrMapper.deleteByMstrRow("WAQ_PRF_EFVA_ANA", rqstNo);
			//프로파일 상세 RQST_SNO UPATE
			waqPrfMstrMapper.updateRqstSno("WAQ_PRF_EFVA_USER_DF", rqstNo);
			
			//프로파일 상세 리스트 삭제 시 1 : N 프로파일(관계, 중복, 패턴, 유효값) 일경우 Q영역프로파일상세 삭제 건수 와 M영역 건수가 다르면 REG_TYP_CD D -> U  UPDATE
			waqPrfMstrMapper.updateMstrDtoU("WAQ_PRF_MSTR", "WAM_PRF_EFVA_ANA", "WAQ_PRF_EFVA_ANA", rqstNo);
			//프로파일 상세 리스트 삭제 시 1 : N 프로파일(관계, 중복, 패턴, 유효값) 일경우 Q영역프로파일상세 삭제 건수 와 M영역 건수가 다르면 REG_TYP_CD D -> U  UPDATE
			waqPrfMstrMapper.updateMstrDtoU("WAQ_PRF_EFVA_ANA", "WAM_PRF_EFVA_USER_DF", "WAQ_PRF_EFVA_USER_DF", rqstNo);
			
			for (WaqPrfEfvaUserDfVO savevo : (ArrayList<WaqPrfEfvaUserDfVO>)reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				savevo.setAprvUserId(userid);
				savevo.setRqstNo(rqstNo);				
				waqPrfMstrVo = savevo;
				result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_EFVA_USER_DF", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_EFVA_ANA", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				result  += waqPrfMstrMapper.updatervwStsCd(waqPrfMstrVo);
			}
		}
		
		//날짜형식분석
		else if(prfKndCd.equals("PC03")){
			
			for (WaqPrfDtfrmAnaVO savevo : (ArrayList<WaqPrfDtfrmAnaVO>)reglist) {
				 waqPrfMstrVo = new WaqPrfMstrVO();
				 savevo.setAprvUserId(userid);
				 savevo.setRqstNo(rqstNo);
				 waqPrfMstrVo = savevo;
				 
			    result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_DTFRM_ANA", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				result  += waqPrfMstrMapper.updatervwStsCd(waqPrfMstrVo);
			}
		}
		
		//범위분석
		else if(prfKndCd.equals("PC04")){
			
			for (WaqPrfRngAnaVO savevo : (ArrayList<WaqPrfRngAnaVO>)reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				savevo.setAprvUserId(userid);
				savevo.setRqstNo(rqstNo);
				waqPrfMstrVo = savevo;
				
				result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_RNG_ANA", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				result  += waqPrfMstrMapper.updatervwStsCd(waqPrfMstrVo);
			}
		}
		
		//유효패턴분석
		else if(prfKndCd.equals("PC05")){
			
			//1 : N 인 프로파일 유효패턴, 유효값, 중복, 관계 일경우  WAQ_PRF_MSTR, 프로파일 상세정보테이블  정리
			//프로파일 마스터 그룹핑 후 MAX(RQST_SNO) 외 삭제
			waqPrfMstrMapper.deleteByPrfNmGroup(rqstNo);
			//프로파일 상세 RQST_SNO UPATE
			waqPrfMstrMapper.updateRqstSno("WAQ_PRF_PTR_USER_DF", rqstNo);
			
			//프로파일 상세 리스트 삭제 시 1 : N 프로파일(관계, 중복, 패턴, 유효값) 일경우 Q영역프로파일상세 삭제 건수 와 M영역 건수가 다르면 REG_TYP_CD D -> U  UPDATE
			waqPrfMstrMapper.updateMstrDtoU("WAQ_PRF_MSTR", "WAM_PRF_PTR_USER_DF", "WAQ_PRF_PTR_USER_DF", rqstNo);
			
			for (WaqPrfPtrAnaVO savevo : (ArrayList<WaqPrfPtrAnaVO>)reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				savevo.setAprvUserId(userid);
				savevo.setRqstNo(rqstNo);
				waqPrfMstrVo = savevo;
				
				result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_PTR_USER_DF", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				result  += waqPrfMstrMapper.updatervwStsCd(waqPrfMstrVo);
			}
		}
		
		//관계분석
		else if(prfKndCd.equals("PT01")){
			//1 : N 인 프로파일 유효패턴, 유효값, 중복, 관계 일경우  WAQ_PRF_MSTR, 프로파일 상세정보테이블  정리
			//프로파일 마스터 그룹핑 후 MAX(RQST_SNO) 외 삭제
			waqPrfMstrMapper.deleteByPrfNmGroup(rqstNo);
			//프로파일 마스터 삭제된 ROW  - > 부모테이블  정보 에서도 삭제
			waqPrfMstrMapper.deleteByMstrRow("WAQ_PRF_REL_TBL", rqstNo);
			//프로파일 상세 RQST_SNO UPATE
			waqPrfMstrMapper.updateRqstSno("WAQ_PRF_REL_COL", rqstNo);
			
			//프로파일 상세 리스트 삭제 시 1 : N 프로파일(관계, 중복, 패턴, 유효값) 일경우 Q영역프로파일상세 삭제 건수 와 M영역 건수가 다르면 REG_TYP_CD D -> U  UPDATE
			waqPrfMstrMapper.updateMstrDtoU("WAQ_PRF_MSTR", "WAM_PRF_REL_TBL", "WAQ_PRF_REL_TBL", rqstNo);
			//프로파일 상세 리스트 삭제 시 1 : N 프로파일(관계, 중복, 패턴, 유효값) 일경우 Q영역프로파일상세 삭제 건수 와 M영역 건수가 다르면 REG_TYP_CD D -> U  UPDATE
			waqPrfMstrMapper.updateMstrDtoU("WAQ_PRF_REL_TBL", "WAM_PRF_REL_COL", "WAQ_PRF_REL_COL", rqstNo);
			
			for (WaqPrfRelColVO savevo : (ArrayList<WaqPrfRelColVO>)reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				savevo.setAprvUserId(userid);
				savevo.setRqstNo(rqstNo);
				waqPrfMstrVo = savevo;
				
				result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_REL_COL", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_REL_TBL", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				result  += waqPrfMstrMapper.updatervwStsCd(waqPrfMstrVo);
			}
		}
		
		//중복분석
		else if(prfKndCd.equals("PT02")){
			//1 : N 인 프로파일 유효패턴, 유효값, 중복, 관계 일경우  WAQ_PRF_MSTR, 프로파일 상세정보테이블  정리
			//프로파일 마스터 그룹핑 후 MAX(RQST_SNO) 외 삭제
			waqPrfMstrMapper.deleteByPrfNmGroup(rqstNo);
			//프로파일 상세 RQST_SNO UPATE
			waqPrfMstrMapper.updateRqstSno("WAQ_PRF_UNQ_COL", rqstNo);
			
			//프로파일 상세 리스트 삭제 시 1 : N 프로파일(관계, 중복, 패턴, 유효값) 일경우 Q영역프로파일상세 삭제 건수 와 M영역 건수가 다르면 REG_TYP_CD D -> U  UPDATE
			waqPrfMstrMapper.updateMstrDtoU("WAQ_PRF_MSTR", "WAM_PRF_UNQ_COL", "WAQ_PRF_UNQ_COL", rqstNo);
			
			for (WaqPrfUnqColVO savevo : (ArrayList<WaqPrfUnqColVO>)reglist) {
				waqPrfMstrVo = new WaqPrfMstrVO();
				savevo.setAprvUserId(userid);
				savevo.setRqstNo(rqstNo);
				waqPrfMstrVo = savevo;
				
				result  += waqPrfMstrMapper.updateDtlrvwStsCd("WAQ_PRF_UNQ_COL", savevo.getRvwStsCd(), savevo.getRvwConts(), savevo.getAprvUserId(), rqstNo, savevo.getRqstSno());
				result  += waqPrfMstrMapper.updatervwStsCd(waqPrfMstrVo);
			}
		}
		
		
		//업데이트 내용이 없으면 오류 리턴한다.
		if (result <= 0 ) {
			logger.debug("결재 승인 실패 : 요청내용중 업데이트 대상이 없음...결재자:{},요청번호:{}",userid, rqstNo);
			throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : 요청내용중 업데이트 대상이 없음...");
		}

		//2. 결재 진행 테이블을 업데이트 한다. 최초의 결재라인을 업데이트 처리한다. (세션 유저정보와 결재진행의 userid가 동일해야 한다.
		//3.최종 승인인지 아닌지 확인한다. (이건 AOP 방식으로 처리할 수 있을까?....)
		boolean waq2wam = false;
		
		if(!UtilObject.isNull(mstVo.getAprLvl()) && mstVo.getAprLvl()  > 0){
			// S: 임시저장 Q: 결재요청 A : 승인 
			waq2wam = requestApproveService.setApproveProcess(mstVo, "WAQ_PRF_MSTR");
		}else{
			//요청 마스터를 업데이트 한다.
			mstVo.setRqstStepCd("A");
			//요청상태 승인으로 UPDATE
			result = requestMstService.updateRqstPrcStep(mstVo);
			//승인자, 승인시간 UPDATE
			result = requestMstService.updateApproveInfo(mstVo);
			waq2wam = true;
		}

		//4. 최종 결재가 완료이면 waq ==> wam, wah으로 저장처리한다.
		if(waq2wam) {
			//waq2wam을 처리하자...
			logger.debug("waq to wam and wah");

			result = 0;
			result = regWaq2Wam(mstVo);

			//업데이트 내용이 없으면 오류 리턴한다.
			if (result <= 0 ) {
				logger.debug("결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음..결재자:{},요청번호:{}",userid, rqstNo);
				throw new WiseBizException("ERR.APPROVE", "결재 승인 실패 : WAQ요청서를 WAM, WAH로 이관내용이 없음");
			}

		}
		
		return result;
	}
	
	
	/** @param mstVo insomnia
	 * @throws Exception */
	private int regWaq2Wam(WaqMstr mstVo) throws Exception {
		int result = 0;

		String rqstno = mstVo.getRqstNo();
		String prfKndCd = mstVo.getBizDtlCd();

		//신규 대상인 경우 ID 채번한다.
		List<WaqPrfMstrVO> waqclist =  waqPrfMstrMapper.selectWaqC(rqstno);

		for (WaqPrfMstrVO savevo : waqclist) {
			String id = objectIdGnrService.getNextStringId();
			savevo.setPrfId(id);
//			savevo.setIbsStatus("U");
			//신규 등록건에 대해 id 업데이트 처리한다....
			result += waqPrfMstrMapper.updateidByKey(savevo);
		}

		//프로파일 마스터 
		//result += waqPrfMstrMapper.updateWaqCUD(rqstno);
		result += waqPrfMstrMapper.deleteWAM(rqstno);
		result += waqPrfMstrMapper.insertWAM(rqstno);
		result += waqPrfMstrMapper.updateWAH(rqstno);
		result += waqPrfMstrMapper.insertWAH(rqstno);
		
		//컬럼분석 
		if(prfKndCd.equals("PC01")){
			//프로파일ID UPDATE
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_COL_ANA", rqstno);
			
			result += waqPc01Mapper.deleteWAM(rqstno);
			result += waqPc01Mapper.insertWAM(rqstno);
			result += waqPc01Mapper.updateWAH(rqstno);
			result += waqPc01Mapper.insertWAH(rqstno);
		}
		
		//유효값분석
		else if(prfKndCd.equals("PC02")){
			//프로파일ID UPDATE
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_EFVA_USER_DF", rqstno);
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_EFVA_ANA", rqstno);
			
			result += waqPc02Mapper.deleteWAM(rqstno);
			result += waqPc02Mapper.insertWAM(rqstno);
			result += waqPc02Mapper.updateWAH(rqstno);
			result += waqPc02Mapper.insertWAH(rqstno);
			
			//사용자정의유효값
			result += waqPc02UserDfMapper.deleteWAM(rqstno);
			result += waqPc02UserDfMapper.insertWAM(rqstno);
			result += waqPc02UserDfMapper.updateWAH(rqstno);
			result += waqPc02UserDfMapper.insertWAH(rqstno);
		}
		//날짜형식분석
		else if(prfKndCd.equals("PC03")){
			//프로파일ID UPDATE
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_DTFRM_ANA", rqstno);
			
			result += waqPc03Mapper.deleteWAM(rqstno);
			result += waqPc03Mapper.insertWAM(rqstno);
			result += waqPc03Mapper.updateWAH(rqstno);
			result += waqPc03Mapper.insertWAH(rqstno);
		}
		//범위분석
		else if(prfKndCd.equals("PC04")){
			//프로파일ID UPDATE
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_RNG_ANA", rqstno);
			
			result += waqPc04Mapper.deleteWAM(rqstno);
			result += waqPc04Mapper.insertWAM(rqstno);
			result += waqPc04Mapper.updateWAH(rqstno);
			result += waqPc04Mapper.insertWAH(rqstno);
		}
		//유효패턴분석
		else if(prfKndCd.equals("PC05")){
			//프로파일ID UPDATE
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_PTR_USER_DF", rqstno);
			
			result += waqPc05Mapper.deleteWAM(rqstno);
			result += waqPc05Mapper.insertWAM(rqstno);
			result += waqPc05Mapper.updateWAH(rqstno);
			result += waqPc05Mapper.insertWAH(rqstno);
		}
		//관계분석
		else if(prfKndCd.equals("PT01")){
			//프로파일ID UPDATE
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_REL_TBL", rqstno);
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_REL_COL", rqstno);
			
			//부모테이블
			result += waqPt01TblMapper.deleteWAM(rqstno);
			result += waqPt01TblMapper.insertWAM(rqstno);
			result += waqPt01TblMapper.updateWAH(rqstno);
			result += waqPt01TblMapper.insertWAH(rqstno);
			
			//관계컬럼
			result += waqPt01ColMapper.deleteWAM(rqstno);
			result += waqPt01ColMapper.insertWAM(rqstno);
			result += waqPt01ColMapper.updateWAH(rqstno);
			result += waqPt01ColMapper.insertWAH(rqstno);
		}
		//중복분석
		else if(prfKndCd.equals("PT02")){
			//프로파일ID UPDATE
			waqPrfMstrMapper.updateDtlIdByKey("WAQ_PRF_UNQ_COL", rqstno);
			
			result += waqPt02Mapper.deleteWAM(rqstno);
			result += waqPt02Mapper.insertWAM(rqstno);
			result += waqPt02Mapper.updateWAH(rqstno);
			result += waqPt02Mapper.insertWAH(rqstno);
		}
		return result;

	}
	
	
	/** 프로파일 요청 리스트 삭제  */
	public int delPrfExlLst(WaqMstr reqmst, String delkey) {
		int result = 0;
		
		String[] delkeys = delkey.split("[|]");

		int cnt = delkeys.length;
		Integer [] ids = new Integer[cnt];

		for (int i=0; i<cnt; i++) {
			String delk = delkeys[i];
			ids[i] = new Integer(delk);
			logger.debug("delkey:{}", delk);
		}

		logger.debug("ids:{}", ids);

		//프로파일 마스터 삭제
		result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_MSTR", ids, reqmst.getRqstNo());
		
		//컬럼분석
		if(reqmst.getBizDtlCd().equals("PC01")){
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_COL_ANA", ids, reqmst.getRqstNo());
		}
		//유효값분석
		if(reqmst.getBizDtlCd().equals("PC02")){
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_EFVA_ANA", ids, reqmst.getRqstNo());
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_EFVA_USER_DF", ids, reqmst.getRqstNo());
		}
		//날짜형식분석
		else if(reqmst.getBizDtlCd().equals("PC03")){
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_DTFRM_ANA", ids, reqmst.getRqstNo());
		}
		//범위분석
		else if(reqmst.getBizDtlCd().equals("PC04")){
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_RNG_ANA", ids, reqmst.getRqstNo());
		}
		//유효패턴분석
		else if(reqmst.getBizDtlCd().equals("PC05")){
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_PTR_USER_DF", ids, reqmst.getRqstNo());
		}
		//관계분석
		else if(reqmst.getBizDtlCd().equals("PT01")){
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_REL_TBL", ids, reqmst.getRqstNo());
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_REL_COL", ids, reqmst.getRqstNo());
		}
		//중복분석
		else if(reqmst.getBizDtlCd().equals("PT02")){
			result += waqPrfMstrMapper.deletebyRqstSno("WAQ_PRF_UNQ_COL", ids, reqmst.getRqstNo());
		}
		
		return result;
	}
	
	public int submit(WaqMstr mstVo) {
		// TODO Auto-generated method stub
		return 0;
	}


	//컬럼분석 검증 조회
	public List<WaqPrfColAnaVO> getPrfExlPc01Lst(WaqMstr search) {
		List<WaqPrfColAnaVO> list =  waqPc01Mapper.selectPrfExlPc01Lst(search);
		return list;
	}
	
	//유효값분석 검증 조회
	public List<WaqPrfEfvaUserDfVO> getPrfExlPc02Lst(WaqMstr search) {
		List<WaqPrfEfvaUserDfVO> list =  waqPc02UserDfMapper.selectPrfExlPc02Lst(search);
		return list;
	}
		
	//날짜형식분석 검증 조회
	public List<WaqPrfDtfrmAnaVO> getPrfExlPc03Lst(WaqMstr search) {
		
		List<WaqPrfDtfrmAnaVO> list =  waqPc03Mapper.selectPrfExlPc03Lst(search);
		return list;
	}
	
	//범위분석 검증 조회
	public List<WaqPrfRngAnaVO> getPrfExlPc04Lst(WaqMstr search) {
		List<WaqPrfRngAnaVO> list =  waqPc04Mapper.selectPrfExlPc04Lst(search);
		return list;
	}
	
	//유효패턴분석 검증 조회
	public List<WaqPrfPtrAnaVO> getPrfExlPc05Lst(WaqMstr search) {
		List<WaqPrfPtrAnaVO> list =  waqPc05Mapper.selectPrfExlPc05Lst(search);
		return list;
	}

	//관계분석 검증 조회
	public List<WaqPrfRelColVO> getPrfExlPt01Lst(WaqMstr search) {
		List<WaqPrfRelColVO> list =  waqPt01ColMapper.selectPrfExlPt01Lst(search);
		return list;
	}
	
	//중복분석 검증 조회
	public List<WaqPrfUnqColVO> getPrfExlPt02Lst(WaqMstr search) {
		List<WaqPrfUnqColVO> list =  waqPt02Mapper.selectPrfExlPt02Lst(search);
		return list;
	}


	@Override
	public String getPrfKndCd(String rqstNo) {
		WaqPrfMstrVO waqPrfMstrVO = waqPrfMstrMapper.selectPrfKndCd(rqstNo);
		String prfKndCd = waqPrfMstrVO.getPrfKndCd();
		return prfKndCd;
	}


	//************** 메타연동 프로파일 등록 대상 조회
	//범위분석 메타 연동 대상 조회
	public List<WaqPrfRngAnaVO> getPrfMetaSrchPc04Lst(WaqMstr search) {
		// TODO Auto-generated method stub
		return waqPc04Mapper.selectPrfFromMetaPc04Lst(search);
	}
	
	//날짜분석 메타 연동 대상 조회@Overrid
	public List<WaqPrfDtfrmAnaVO> getPrfMetaSrchPc03Lst(WaqMstr search) {
		// TODO Auto-generated method stub
		return waqPc03Mapper.selectPrfFromMetaPc03Lst(search);
	}
	
	//유효값분석 메타 연동 대상 조회
	public List<WaqPrfEfvaUserDfVO> getPrfMetaSrchPc02Lst(WaqMstr search) {
		// TODO Auto-generated method stub
		return waqPc02UserDfMapper.selectPrfFromMetaPc02Lst(search);
	}
	
	//관계분석 메타 연동 대상 조회
	public List<WaqPrfRelColVO> getPrfMetaSrchPt01Lst(WaqMstr search) {
		// TODO Auto-generated method stub
		return waqPt01ColMapper.selectPrfFromMetaPt01Lst(search);
	}
	//************** 메타연동 프로파일 등록 대상 조회


	@Override
	public List<WaqPrfEfvaUserDfVO> getPrfWatPc05Lst(String dbConnTrgId, String dbSchId, String dbcColNm, String paramVal) {
		// TODO Auto-generated method stub
		return waqPc05Mapper.selectPrfWatPc05Lst(dbConnTrgId, dbSchId, dbcColNm, paramVal);
	}


	@Override
	public List<WaqPrfEfvaUserDfVO> getPrfWatPc02Lst(String dbConnTrgId, String dbSchId, String dbcColNm, String paramVal) {
		// TODO Auto-generated method stub
		return waqPc02UserDfMapper.selectPrfWatPc02Lst(dbConnTrgId, dbSchId, dbcColNm, paramVal);
	}


	@Override
	public List<WaqPrfDtfrmAnaVO> getPrfWatPc03Lst(String dbConnTrgId, String dbSchId, String dbcColNm, String paramVal) {
		// TODO Auto-generated method stub
		return waqPc03Mapper.selectPrfWatPc03Lst(dbConnTrgId, dbSchId, dbcColNm, paramVal);
	}


	/** 컬럼분석 메타연동 대상 조회 insomnia */
//	public List<WaqPrfColAnaVO> getPrfMetaSrchPc01Lst(WaqMstr search) {
//		return waqPc01Mapper.selectPrfFromMetaPc01Lst(search);
//	}
	
	public int regWaqRejected(WaqMstr reqmst, String oldRqstNo) throws Exception {
		int result = 0;

		String prfKndCd = getPrfKndCd(oldRqstNo);
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();
		reqmst.setRqstUserId(userid);
		
		reqmst.getBizInfo().setBizDtlCd(prfKndCd);
		reqmst.setBizDtlCd(prfKndCd);
		
		if(prfKndCd.equals("PT01")) {
			
			result += waqPrfMstrMapper.insertWaqRejected(reqmst, oldRqstNo);
			result += waqPt01TblMapper.insertWaqRejected(reqmst, oldRqstNo);
			result += waqPt01ColMapper.insertWaqRejected(reqmst, oldRqstNo);
			
		} else if(prfKndCd.equals("PT02")) {
			
			result += waqPrfMstrMapper.insertWaqRejected(reqmst, oldRqstNo);
			result += waqPt02Mapper.insertWaqRejected(reqmst, oldRqstNo);
			
		} else if(prfKndCd.equals("PC01")) {
			
			result += waqPrfMstrMapper.insertWaqRejected(reqmst, oldRqstNo);
			result += waqPc01Mapper.insertWaqRejected(reqmst, oldRqstNo);
			
		} else if(prfKndCd.equals("PC02")) {
			
			result += waqPrfMstrMapper.insertWaqRejected(reqmst, oldRqstNo);
			//유효값분석정보
			result += waqPc02Mapper.insertWaqRejected(reqmst, oldRqstNo);
			
//			//사용자정의유효값
//			if(waqPrfEfvaAnaVO.getEfvaAnaKndCd().equals("USER")){
//				 
//				result += waqPc02UserDfMapper.insertSelective(saveVo);
//			}
			
			result += waqPc02UserDfMapper.insertWaqRejected(reqmst, oldRqstNo);
			
		} else if(prfKndCd.equals("PC03")) {
			
			result += waqPrfMstrMapper.insertWaqRejected(reqmst, oldRqstNo);
			result += waqPc03Mapper.insertWaqRejected(reqmst, oldRqstNo);
		
		} else if(prfKndCd.equals("PC04")) {
			
			result += waqPrfMstrMapper.insertWaqRejected(reqmst, oldRqstNo);
			result += waqPc04Mapper.insertWaqRejected(reqmst, oldRqstNo);
			
		} else {

			result += waqPrfMstrMapper.insertWaqRejected(reqmst, oldRqstNo);
			result += waqPc05Mapper.insertWaqRejected(reqmst, oldRqstNo);
			
		}
		
		//마스터 등록
		register(reqmst, null);
		//검증
		check(reqmst);
		
		return result;
	}
}
