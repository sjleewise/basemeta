/*
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

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.WahPrfColAnaMapper;
import kr.wise.dq.profile.colana.service.WamPrfColAnaMapper;
import kr.wise.dq.profile.coldtfrmana.service.WahPrfDtfrmAnaMapper;
import kr.wise.dq.profile.coldtfrmana.service.WamPrfDtfrmAnaMapper;
import kr.wise.dq.profile.colefvaana.service.WahPrfEfvaAnaMapper;
import kr.wise.dq.profile.colefvaana.service.WahPrfEfvaUserDfMapper;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaMapper;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfMapper;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.WahPrfPtrAnaMapper;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaMapper;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WahPrfRngAnaMapper;
import kr.wise.dq.profile.colrngana.service.WamPrfRngAnaMapper;
import kr.wise.dq.profile.mstr.service.ProfileMstrService;
import kr.wise.dq.profile.mstr.service.WahPrfMstrMapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrMapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.mstr.service.WaqPrfDqiMapMapper;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;
import kr.wise.dq.profile.tblrel.service.WahPrfRelColMapper;
import kr.wise.dq.profile.tblrel.service.WahPrfRelTblMapper;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColMapper;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblMapper;
import kr.wise.dq.profile.tblunq.service.WahPrfUnqColMapper;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColMapper;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


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
@Service("profileMstrService")
public class ProfileMstrServiceImpl implements ProfileMstrService{

Logger logger = LoggerFactory.getLogger(getClass());

	/* PRF ID 채번*/
	@Inject
	private EgovIdGnrService objectIdGnrService;

	/* 프로파일 마스터 */
	@Inject
	private WahPrfMstrMapper wahMstMapper;
	@Inject
	private WamPrfMstrMapper wamMstMapper;

	//컬럼분석
	@Inject
	private WahPrfColAnaMapper wahPc01Mapper;
	@Inject
	private WamPrfColAnaMapper wamPc01Mapper;

	//유효값분석
	@Inject
	private WahPrfEfvaAnaMapper wahPc02Mapper;
	@Inject
	private WamPrfEfvaAnaMapper wamPc02Mapper;
	//유효값분석 사용자 정의
	@Inject
	private WahPrfEfvaUserDfMapper wahPc02UserDfMapper;
	@Inject
	private WamPrfEfvaUserDfMapper wamPc02UserDfMapper;

	//날짜형식분석
	@Inject
	private WahPrfDtfrmAnaMapper wahPc03Mapper;
	@Inject
	private WamPrfDtfrmAnaMapper wamPc03Mapper;

	//범위분석
	@Inject
	private WahPrfRngAnaMapper wahPc04Mapper;
	@Inject
	private WamPrfRngAnaMapper wamPc04Mapper;

	//패턴분석
	@Inject
	private WahPrfPtrAnaMapper wahPc05Mapper;
	@Inject
	private WamPrfPtrAnaMapper wamPc05Mapper;


	//관계분석테이블
	@Inject
	private WahPrfRelTblMapper wahPt01RelTblMapper;
	@Inject
	private WamPrfRelTblMapper wamPt01RelTblMapper;
	//관계컬럼
	@Inject
	private WahPrfRelColMapper wahPt01RelColMapper;
	@Inject
	private WamPrfRelColMapper wamPt01RelColMapper;

	//중복분석
	@Inject
	private WahPrfUnqColMapper wahPt02Mapper;
	@Inject
	private WamPrfUnqColMapper wamPt02Mapper;

	/* 프로파일 마스터 저장 */
	@Override
	public int register( ArrayList<?> reglist) throws Exception {
		//프로파일 종류
		String prfKndCd = null ;

		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//프로파일 요청 리턴
		int result = 0;
		for (WamPrfMstrVO saveVo : (ArrayList<WamPrfMstrVO>)reglist) {
			
			if(! UtilString.null2Blank( saveVo.getRegTypCd() ).equals("D")){
				//C, U 확인
				String prfId = wamMstMapper.selectProfileRegTypCd(saveVo);

				if(UtilString.null2Blank(prfId).equals("")){
					saveVo.setPrfId(objectIdGnrService.getNextStringId());
					saveVo.setRegTypCd("C");
				}else{
					saveVo.setPrfId(prfId);
					saveVo.setRegTypCd("U");
				}
			}

			prfKndCd = saveVo.getPrfKndCd();
			String prfNm = prfKndCd + "|" +saveVo.getDbConnTrgPnm() + "|" + saveVo.getDbSchPnm() + "|"+ saveVo.getDbcTblNm() + "|" + saveVo.getObjNm() ;
			//유효값 분석일 경우 유효값 유형 까지
			if(prfKndCd.equals("PC02")){
				prfNm = prfNm + "|" +saveVo.getWamPrfEfvaAnaVO().getEfvaAnaKndCd();
			}
			//프로파일명 셋팅
			saveVo.setPrfNm(prfNm);
			//요청자 셋팅...
			saveVo.setRqstUserId(userid);
			
			logger.debug("dqiId >>> " + saveVo.getDqiId());
			logger.debug("dqiLnm >>> " + saveVo.getDqiLnm());
			//waq_prf_mstr 등록
			result += saveWamPrfMst(saveVo);

			//컬럼분석(PC01)
			if(prfKndCd.equals("PC01")){
				result += saveWamPC01(saveVo);
			}
			//유효값분석(PC02)
			else if(prfKndCd.equals("PC02")){
				result += saveWamPC02(saveVo);
			}
			//날짜형식분석(PC03)
			else if(prfKndCd.equals("PC03")){
				result += saveWamPC03(saveVo);
			}
			//범위분석(PC04)
			else if(prfKndCd.equals("PC04")){
				result += saveWamPC04(saveVo);
			}
			//패턴분석(PC05)
			else if(prfKndCd.equals("PC05")){
				result += saveWamPC05UserDf(saveVo);
			}

			//관계분석
			else if(prfKndCd.equals("PT01")){
				result += saveWamPT01(saveVo);
			}
			//중복분석
			else if(prfKndCd.equals("PT02")){
				result += saveWamPT02(saveVo);
			}
		}
		
		return result;
	}

	//프로파일 마스터
	@Override
	public int saveWamPrfMst(WamPrfMstrVO record) throws Exception {
		int result = 0;
		String tmpStstus = record.getRegTypCd();
		logger.debug("record.getRqstNo() >>>> " + record.getRqstNo());

		//이력테이블 만료
		//result += wahMstMapper.updateWahPrf("WAH_PRF_MSTR" ,record.getPrfId());
		result += wahMstMapper.updateWahPrf("WAH_PRF_MSTR" ,record.getPrfId(), "", "");

		//이력테이블 등록
		result += wahMstMapper.insertSelective(record);

		if("C".equals(tmpStstus) || "U".equals(tmpStstus) ) {
			//WAM 테이블 삭제
			result += wamMstMapper.deleteByPrfId("WAM_PRF_MSTR", record.getPrfId());
			//WAM 테이블 등록
			result += wamMstMapper.insertWamPrfMstByPrfId(record.getPrfId());
			
//			result += wamMstMapper.insertSelective(record);
		}  else if ("D".equals(tmpStstus)){
			//WAM 테이블 삭제
			result += wamMstMapper.deleteByPrfId("WAM_PRF_MSTR", record.getPrfId());
//			result += wahMstMapper.updateWahPrf("WAH_PRF_MSTR" ,record.getPrfId(), "", "");
		}
		return result;
	}
	
	//컬럼분석
	@Override
	public int saveWamPC01(WamPrfMstrVO record) {
		int result = 0;
		String tmpStstus = record.getRegTypCd();
		String prfId = record.getPrfId();

		//이력테이블 만료
		//result += wahMstMapper.updateWahPrf("WAH_PRF_COL_ANA" ,record.getPrfId());
		result += wahMstMapper.updateWahPrf("WAH_PRF_COL_ANA" ,prfId, "", "");

		//이력테이블 등록
		result += wahPc01Mapper.insertSelective(record);

		if("C".equals(tmpStstus) || "U".equals(tmpStstus) ) {
			//WAM 테이블 삭제
			result += wamMstMapper.deleteByPrfId("WAM_PRF_COL_ANA", record.getPrfId());
			//result += wamPc01Mapper.deleteByPrimaryKey(prfId);
			//WAM 테이블 등록
			result += wamPc01Mapper.insertWamPrfPC01ByPrfId(record.getPrfId());
//			result += wamPc01Mapper.insertSelective(record);
		} else if ("D".equals(tmpStstus)){
			//WAM 테이블 삭제
			result += wamPc01Mapper.deleteByPrimaryKey(prfId);
		}

		return result;
	}

	//유효값 분석
	@Override
	public int saveWamPC02(WamPrfMstrVO record) {
		int result = 0;
		String tmpStstus = record.getRegTypCd();
		String prfId = record.getPrfId();

		//이력테이블 만료
		//result += wahMstMapper.updateWahPrf("WAH_PRF_EFVA_ANA" ,record.getPrfId());
		result += wahMstMapper.updateWahPrf("WAH_PRF_EFVA_ANA" ,prfId, "", "");
		//이력테이블 등록
		result += wahPc02Mapper.insertSelective(record);

		if("C".equals(tmpStstus) || "U".equals(tmpStstus) ) {
			//WAM 테이블 삭제
			result += wamMstMapper.deleteByPrfId("WAM_PRF_EFVA_ANA", record.getPrfId());
			//WAM 테이블 등록
			result += wamPc02Mapper.insertWamPrfPC02ByPrfId(prfId);
		} else if ("D".equals(tmpStstus)){
			//WAM 테이블 삭제
			result += wamMstMapper.deleteByPrfId("WAM_PRF_EFVA_ANA", record.getPrfId());
		}

		//사용자 정의 유효값 일경우
		if(UtilString.null2Blank( record.getWamPrfEfvaAnaVO().getEfvaAnaKndCd()).equals("USER")){
			result += saveWamPC02UserDf(record);
		}

		return result;
	}

	//사용자 정의 유효값
	@Override
	public int saveWamPC02UserDf(WamPrfMstrVO record) {
		int result = 0;
		String prfId = record.getPrfId();

		for (WamPrfEfvaUserDfVO saveVO : record.getWamPrfEfvaAnaVO().getWamPrfEfvaUserDfVO()) {

			String tmpStstus = UtilString.null2Blank( saveVO.getIbsStatus());

			saveVO.setPrfId(prfId);
			saveVO.setRqstUserId(record.getRqstUserId());

			//사용자 정의 유효값 셋팅
			//C, U, D 셋팅
			if(record.getRegTypCd().equals("D")){
				saveVO.setRegTypCd("D");
			}else{
				if("I".equals(tmpStstus)){
					saveVO.setRegTypCd("C");
				}else {
					saveVO.setRegTypCd(tmpStstus);
				}
			}

			if("I".equals(tmpStstus)   ) {
				//이력테이블 등록
				result += wahPc02UserDfMapper.insertSelective(saveVO);
				//WAM 테이블 등록
				result += wamPc02UserDfMapper.insertWamPrfPC02UserDfByPrfId(saveVO);
			}else if("U".equals(tmpStstus) ){
				//이력테이블 만료
				//result += wahPc02UserDfMapper.updateWahPrfEfvaUserDfExpDtm(saveVO);
				result += wahMstMapper.updateWahPrf("WAH_PRF_EFVA_USER_DF" ,prfId, record.getPrfKndCd(), saveVO.getUserDfEfva());
				//이력테이블 등록
				result += wahPc02UserDfMapper.insertSelective(saveVO);
				//WAM 테이블 등록
				result += wamPc02UserDfMapper.insertWamPrfPC02UserDfByPrfId(saveVO);
			}else if("D".equals(tmpStstus) )  {
				//이력테이블 만료
				result += wahMstMapper.updateWahPrf("WAH_PRF_EFVA_USER_DF" ,prfId, record.getPrfKndCd(), saveVO.getUserDfEfva());
				//WAM 테이블 삭제
				result += wamPc02UserDfMapper.deleteByPrimaryKey(saveVO);
			}
		}
	return result;
	}

	//날짜형식분석
	@Override
	public int saveWamPC03(WamPrfMstrVO record) {
		int result = 0;
		String tmpStstus = record.getRegTypCd();
		String prfId = record.getPrfId();

		//이력테이블 만료
		//result += wahMstMapper.updateWahPrf("WAH_PRF_DTFRM_ANA" ,record.getPrfId());
		result += wahMstMapper.updateWahPrf("WAH_PRF_DTFRM_ANA" ,prfId, "", "");
		//이력테이블 등록
		result += wahPc03Mapper.insertSelective(record);

		if("C".equals(tmpStstus) || "U".equals(tmpStstus) ) {
			//WAM 테이블 삭제
			result += wamMstMapper.deleteByPrfId("WAM_PRF_DTFRM_ANA", record.getPrfId());
			//WAM 테이블 등록
			result += wamPc03Mapper.insertWamPrfPC03ByPrfId(record.getPrfId());
		} else if ("D".equals(tmpStstus)){
			//WAM 테이블 삭제
			result += wamPc03Mapper.deleteByPrimaryKey(prfId);
		}

		return result;
	}

	//범위분석
	@Override
	public int saveWamPC04(WamPrfMstrVO record) {
		int result = 0;
		String tmpStstus = record.getRegTypCd();
		String prfId = record.getPrfId();

		//이력테이블 만료
		//result += wahMstMapper.updateWahPrf("WAH_PRF_RNG_ANA" ,record.getPrfId());
		result += wahMstMapper.updateWahPrf("WAH_PRF_RNG_ANA" ,prfId, "", "");
		//이력테이블 등록
		result += wahPc04Mapper.insertSelective(record);

		if("C".equals(tmpStstus) || "U".equals(tmpStstus) ) {
			//WAM 테이블 삭제
			result += wamMstMapper.deleteByPrfId("WAM_PRF_RNG_ANA", record.getPrfId());
			//WAM 테이블 등록
			result += wamPc04Mapper.insertWamPrfPC04ByPrfId(record.getPrfId());
		} else if ("D".equals(tmpStstus)){
			//WAM 테이블 삭제
			result += wamPc04Mapper.deleteByPrimaryKey(prfId);
		}

		return result;
	}


	//사용자 정의패턴
	@Override
	public int saveWamPC05UserDf(WamPrfMstrVO record) {
		int result = 0;
		String prfId = record.getPrfId();

		for (WamPrfPtrAnaVO saveVO : record.getWamPrfPtrAnaVO()) {
			String tmpStstus = UtilString.null2Blank( saveVO.getIbsStatus());

			saveVO.setPrfId(prfId);
			saveVO.setRqstUserId(record.getRqstUserId());

			//사용자 정의 유효값 셋팅
			//C, U, D 셋팅
			if(record.getRegTypCd().equals("D")){
				saveVO.setRegTypCd("D");
			}else{
				if("I".equals(tmpStstus)){
					saveVO.setRegTypCd("C");
				}else  {
					saveVO.setRegTypCd(tmpStstus);
				}
			}

			//이력테이블 만료
//			result += wahPc05Mapper.updateWahPrfPtrUserDfExpDtm(saveVO);
			result += wahMstMapper.updateWahPrf("WAH_PRF_PTR_USER_DF" ,prfId, record.getPrfKndCd(), saveVO.getUseYn());
			//이력테이블 등록
			result += wahPc05Mapper.insertSelective(saveVO);

			if("I".equals(tmpStstus)  || "U".equals(tmpStstus)  ) {
				//WAM 테이블 삭제
				result += wamPc05Mapper.deleteByPrimaryKey(saveVO);
				//WAM 테이블 등록
				result += wamPc05Mapper.insertWamPrfPC05UserDfByPrfId(saveVO);
			}
			else  {
				//WAM 테이블 삭제
				result += wamPc05Mapper.deleteByPrimaryKey(saveVO);
			}
		}
		return result;
	}

	//관계분석
	public int saveWamPT01(WamPrfMstrVO record) {
		int result = 0;
		String prfId = record.getPrfId();

		//관계분석 부모테이블 정보 등록
		//이력테이블 만료
		result += wahMstMapper.updateWahPrf("WAH_PRF_REL_TBL" ,prfId, record.getPrfKndCd(), "");
		//이력테이블 등록
		result += wahPt01RelTblMapper.insertSelective(record);

		//WAM 테이블 삭제
		result += wamMstMapper.deleteByPrfId("WAM_PRF_REL_TBL", record.getPrfId());
		//WAM 테이블 등록
		result += wamPt01RelTblMapper.insertWamPrfPT01ByPrfId(record.getPrfId());


		//관계컬럼 등록
		for (WamPrfRelColVO saveVO : record.getWamPrfRelColVO()) {
			String tmpStstus = UtilString.null2Blank( saveVO.getIbsStatus());

			//화면에서 그리드 객체 전체 submit
			//조회 (R) 이 아닐경우 중복컬럼 UPDATE, INSERT
			if(!tmpStstus.equals("R")){

				saveVO.setPrfId(prfId);
				saveVO.setRqstUserId(record.getRqstUserId());

				//사용자 정의 유효값 셋팅
				//C, U, D 셋팅
				if(record.getRegTypCd().equals("D")){
					saveVO.setRegTypCd("D");
					tmpStstus = "D";
				}else{
					if("I".equals(tmpStstus)){
						saveVO.setRegTypCd("C");
					}else  {
						saveVO.setRegTypCd(tmpStstus);
					}
				}

				//이력테이블 만료
//				result += wahMstMapper.updateWahPrf("WAH_PRF_REL_COL" ,prfId, record.getPrfKndCd(), saveVO.getPaTblDbcColNm());
				result += wahPt01RelColMapper.updateWahPrf(saveVO);
				//이력테이블 등록
				result += wahPt01RelColMapper.insertSelective(saveVO);

				if("I".equals(tmpStstus) || "U".equals(tmpStstus) ) {
					//WAM 테이블 삭제
					result += wamPt01RelColMapper.deleteByPrimaryKey(saveVO);
					//WAM 테이블 등록
					result += wamPt01RelColMapper.insertWamPrfPT01ByPrfId(saveVO);
				} else if ("D".equals(tmpStstus)){
					//WAM 테이블 삭제
					result += wamPt01RelColMapper.deleteByPrimaryKey(saveVO);
				}
			}
		}

		return result;
	}

	//중복분석
	@Override
	public int saveWamPT02(WamPrfMstrVO record) {
		int result = 0;
		String prfId = record.getPrfId();

		for (WamPrfUnqColVO saveVO : record.getWamPrfUnqColVO()) {
			String tmpStstus = UtilString.null2Blank( saveVO.getIbsStatus());

			//화면에서 그리드 객체 전체 submit
			//조회 (R) 이 아닐경우 중복컬럼 UPDATE, INSERT
			if(!tmpStstus.equals("R")){

				saveVO.setPrfId(prfId);
				saveVO.setRqstUserId(record.getRqstUserId());

				//사용자 정의 유효값 셋팅
				//C, U, D 셋팅
				if(record.getRegTypCd().equals("D")){
					saveVO.setRegTypCd("D");
				}else{
					if("I".equals(tmpStstus)){
						saveVO.setRegTypCd("C");
					}else  {
						saveVO.setRegTypCd(tmpStstus);
					}
				}

				//이력테이블 만료
				//중복컬럼별
				result += wahMstMapper.updateWahPrf("WAH_PRF_UNQ_COL" ,prfId, record.getPrfKndCd(), saveVO.getDbcColNm());
				//이력테이블 등록
				result += wahPt02Mapper.insertSelective(saveVO);

				if("I".equals(tmpStstus) || "U".equals(tmpStstus) ) {
					//WAM 테이블 삭제
					result += wamPt02Mapper.deleteByPrimaryKey(saveVO);
					//WAM 테이블 등록
					result += wamPt02Mapper.insertWamPrfPT02ByPrfId(saveVO);
				} else if ("D".equals(tmpStstus)){
					//WAM 테이블 삭제
					result += wamPt02Mapper.deleteByPrimaryKey(saveVO);
				}
			}
		}


		return result;
	}

	
	//프로파일 마스터 조회
	public WamPrfMstrCommonVO  getPrfMstrByPrfId(String prfId, String prfKndCd) {
		WamPrfMstrCommonVO  prfmstrVO = wamMstMapper.selectPrfMstrByPrfId(prfId, prfKndCd);
		return prfmstrVO;
	}
		
	@Override
	public WamPrfMstrVO getSqlGenDbmsInfoByPrfId(String prfId) {
		WamPrfMstrVO  prfmstrVO = wamMstMapper.selectSqlGenDbmsInfoByPrfId(prfId);
		return prfmstrVO;
	}

	@Override
	public int saveErrCntSql(WamPrfMstrVO saveVo) {
		// TODO Auto-generated method stub
		int result = 0;
		
//		for(WamPrfMstrVO saveVo : list) {
			result += wamMstMapper.updateErrCntSql(saveVo);
//		}
		
		return result;
	}


}
