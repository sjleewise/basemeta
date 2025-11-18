package kr.wise.commons.damgmt.gov.service.impl;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.dmnginfo.service.WaaDmngMapper;
import kr.wise.commons.damgmt.dmnginfo.service.WaaInfoTypeMapper;
import kr.wise.commons.damgmt.gov.service.GovLstMapService;
import kr.wise.commons.damgmt.gov.service.GovLstMapper;
import kr.wise.commons.damgmt.gov.service.WaaGovCdVal;
import kr.wise.commons.damgmt.gov.service.WaaGovDmn;
import kr.wise.commons.damgmt.gov.service.WaaGovSditm;
import kr.wise.commons.damgmt.gov.service.WaaGovStwd;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("GovLstMapService")
public class GovLstMapServiceImpl implements GovLstMapService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private GovLstMapper mapper;
	
	@Inject
	private WaaDmngMapper dmngMapper;
	
	@Inject
	private WaaInfoTypeMapper infoMapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService;
	
	public List<WaaGovStwd> getList(WaaGovStwd search) {
		
		List<WaaGovStwd> list = mapper.selectList(search);
		
		return list;
	}
	
//	public WaaDmngInfotpMap findDmngInfotpMap(WaaDmngInfotpMap search) {
//		
//		return mapper.selectByPrimaryKey(search.getDmngId(), search.getInfotpId());
//	}
	
	public int regGovStwdLstMap(WaaGovStwd record) throws Exception {
		logger.debug("{}", record);

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;

//		
//		WaaGovStwd stwdVO = mapper.selectByPrimaryKey(record.getStwdLnm(), record.getStwdPnm());
//		
//		//단어 논리명이나 물리명이 있는경우 미등록, 리턴처리..(데이터 미입력)
//		if(stwdVO == null) {
//			if(!stwdVO.getStwdLnm().equals(record.getStwdLnm())) {
//				return 0;
//			}
//			if(!stwdVO.getStwdPnm().equals(record.getStwdPnm())) {
//				return 0;
//			}
//		}
		String tmpStatus ;

		//표준단어 논리명으로 기존 자료가 있는지 확인한다.
		WaaGovStwd comvo = mapper.selectByPrimaryKey(record);
		
		if (comvo != null && StringUtils.hasText(comvo.getStwdLnm())) {
//			record.setStwdId(comvo.getStwdId());
			record.setIbsStatus("U");
			
			result = mapper.updateByPrimaryKeySelective(record);
			return result;
		}
		tmpStatus = record.getIbsStatus();
		
//		//도메인그룹ID, 인포타입ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..(데이터 미입력)
//		if (comvo == null || !StringUtils.hasText(record.getStwdId())) { 
//			
//			record.setStwdId(objectIdGnrService.getNextStringId());
//		} else {
//			
//			mapper.updateExpDtm(record);
//		}
//		
		//도메인그룹ID, 인포타입ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..(데이터 미입력)
//		WaaDmngInfotpMap tmpVO = mapper.selectByPrimaryKey(record.getStwdLnm());
		if (comvo == null || !comvo.getStwdLnm().equals(record.getStwdLnm()) || !comvo.getStwdLnm().equals(record.getStwdLnm())) { 
			
			record.setStwdId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
		} else {
			if (UtilObject.isNull(comvo.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(comvo.getObjVers()+1);
			}
			result = mapper.updateByPrimaryKey(record);
			mapper.updateExpDtm(record);

			
		}
		result = mapper.insertSelective(record);

		logger.debug("{}", record);
		return result;
	}

	@Override
	public int regGovStwdLstList(ArrayList<WaaGovStwd> list) throws Exception {
		int result = 0;
		for (WaaGovStwd WaaGovStwd : list) {
			result += regGovStwdLstMap(WaaGovStwd);
		}
		return result;
		
	}
//
//
//	public int delgovStwdLstList(ArrayList<WaaGovStwd> list) {
//		int result = 0;
//		for (WaaGovStwd waaGovStwd : list) {
//			String stwdLnm = waaGovStwd.getStwdLnm();
//			String stwdPnm = waaGovStwd.getStwdPnm();
//			if (stwdLnm != null && !"".equals(stwdLnm) && !"".equals(stwdPnm)) {
//				//WaaDmng.setIbsStatus("D");
//				waaGovStwd.setExpDtm(null);
//				result += mapper.updateExpDtm(waaGovStwd);
//			}
//		}	
//		return result;
//	}

	public int delgovStwdLstList(ArrayList<WaaGovStwd> list) {
		int result = 0;
		for (WaaGovStwd waaGovStwd : list) {
			String stwdLnm = waaGovStwd.getStwdLnm();
			String stwdPnm = waaGovStwd.getStwdPnm();
			if (stwdLnm != null && !"".equals(stwdLnm) && !"".equals(stwdPnm)) {
				//WaaDmng.setIbsStatus("D");

				result += mapper.deleteSelective(waaGovStwd);
				
			}
		}	
		return result;
	}
	
	
//	================================표준단어=========================================================
	
	public List<WaaGovSditm> sditmgetList(WaaGovSditm search) {
		List<WaaGovSditm> list = mapper.selectListBySditm(search);
		return list;
	}
	
	public int delgovSditmLstList(ArrayList<WaaGovSditm> list) {
		int result = 0;
		for (WaaGovSditm waaGovSditm : list) {
			String sditmLnm = waaGovSditm.getSditmLnm();
			String sditmPnm = waaGovSditm.getSditmPnm();
			if (sditmLnm != null && !"".equals(sditmLnm) && !"".equals(sditmPnm)) {
				//WaaDmng.setIbsStatus("D");

				result += mapper.deleteSelectivebysditm(waaGovSditm);
				
			}
		}	
		return result;
	}
	
	public int regGovSditmLstMap(WaaGovSditm record) throws Exception {
		logger.debug("{}", record);

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;

		String tmpStatus ;

		//표준단어 논리명으로 기존 자료가 있는지 확인한다.
//		WaaGovSditm comvo = mapper.selectCheckLstsditm(record);
		
//		if (record != null && StringUtils.hasText(record.getSditmLnm())) {
////			record.setStwdId(comvo.getStwdId());
//			record.setIbsStatus("U");
//			
//			result = mapper.updateByPrimaryKeySelectiveSditm(record);
//			return result;
//		}
		tmpStatus = record.getIbsStatus();
		
		
		if (record == null || !record.getSditmLnm().equals(record.getSditmLnm()) || !record.getSditmLnm().equals(record.getSditmLnm())) { 

			record.setSditmId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
		} else {
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(record.getObjVers()+1);
			}
//			result = mapper.updateByPrimaryKey(record);
//			mapper.updateExpDtmSditm(record);

			
		}
		record.setSditmId(objectIdGnrService.getNextStringId());
		System.out.println("======"+objectIdGnrService.getNextStringId());
		result = mapper.insertSelectivebySditm(record);

		logger.debug("{}", record);
		return result;
	}

	@Override
	public int regGovSditmLstList(ArrayList<WaaGovSditm> list) throws Exception {
		int result = 0;
		for (WaaGovSditm WaaGovSditm : list) {
			result += regGovSditmLstMap(WaaGovSditm);
		}
		return result;
		
	}
	// ======================================== 표준 도메인 ================================== //
	
	public List<WaaGovDmn> dmngetList(WaaGovDmn search) {

		List<WaaGovDmn> list = mapper.selectList_DMN(search);
		
		return list;

	}
	
	public int regGovDmnLstMap(WaaGovDmn record) throws Exception {
		logger.debug("{}", record);

		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;

//		
//		WaaGovDmn stwdVO = mapper.selectByPrimaryKey(record.getStwdLnm(), record.getStwdPnm());
//		
//		//단어 논리명이나 물리명이 있는경우 미등록, 리턴처리..(데이터 미입력)
//		if(stwdVO == null) {
//			if(!stwdVO.getStwdLnm().equals(record.getStwdLnm())) {
//				return 0;
//			}
//			if(!stwdVO.getStwdPnm().equals(record.getStwdPnm())) {
//				return 0;
//			}
//		}
		String tmpStatus ;

		//도메인명을 조건으로 동일한 도메인이 존재하는지 확인 -> 파라미터 null아니면 기존데이터 업데이트
		WaaGovDmn comvo = mapper.selectByPrimaryKey_dmn2(record);
		
		if (comvo != null && StringUtils.hasText(comvo.getDmnLnm())) {
//			record.setStwdId(comvo.getStwdId());
			record.setIbsStatus("U");
			
			result = mapper.updateByPrimaryKeySelective_dmn(record);
			return result;
		}
		tmpStatus = record.getIbsStatus();
		
//		//도메인그룹ID, 인포타입ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..(데이터 미입력)
//		if (comvo == null || !StringUtils.hasText(record.getStwdId())) { 
//			
//			record.setStwdId(objectIdGnrService.getNextStringId());
//		} else {
//			
//			mapper.updateExpDtm(record);
//		}
//		
		//도메인 아이디, 논리명으로 데이터가 있는지 확인하여 없을경우 -> 신규처리..(데이터 미입력)
//		WaaDmngInfotpMap tmpVO = mapper.selectByPrimaryKey(record.getStwdLnm());
		if (comvo == null || !comvo.getDmnLnm().equals(record.getDmnLnm()) || !comvo.getDmnLnm().equals(record.getDmnLnm())) { 
			
			record.setDmnId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
		} else {
//			if (UtilObject.isNull(comvo.getObjVers())) {
//				record.setObjVers(1);
//			}
//			else { 
//				record.setObjVers(comvo.getObjVers()+1);
//			}
//			result = mapper.updateByPrimaryKey(record);
			mapper.updateExpDtm_dmn(record);

			
		}
		result = mapper.insertSelective_dmn(record);

		logger.debug("{}", record);
		return result;
	}

	@Override
	public int regGovdmnLstList(ArrayList<WaaGovDmn> list) throws Exception {
		int result = 0;
		for (WaaGovDmn WaaGovDmn : list) {
			result += regGovDmnLstMap(WaaGovDmn);
		}
		return result;
		
	}
	
	public int delgovdmnLstList(ArrayList<WaaGovDmn> list) {
		int result = 0;
		for (WaaGovDmn waaGovDmn : list) {
			String dmnLnm = waaGovDmn.getDmnLnm();
			String dmngLnm = waaGovDmn.getDmngLnm();
			if (dmnLnm != null && !"".equals(dmnLnm) && !"".equals(dmngLnm)) {
				//WaaDmng.setIbsStatus("D");

				result += mapper.deleteSelective_dmn(waaGovDmn);
			}
		}	
		return result;
	}
	
	// ======================================== 표준 유효값 ================================== //
	
		public List<WaaGovCdVal> cdValgetList(WaaGovCdVal search) {
			
			if(search.getCdVal() != null || search.getCdValNm() != null) {
				List<WaaGovCdVal> list = mapper.selectByPrimaryKey_cdVal(search);
			}
			List<WaaGovCdVal> list = mapper.selectList_cdVal(search);
			return list;

		}

		public int regGovCdValLstMap(WaaGovCdVal record) throws Exception {
			logger.debug("{}", record);

			LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
			int result = 0;

//			
//			WaaGovDmn stwdVO = mapper.selectByPrimaryKey(record.getStwdLnm(), record.getStwdPnm());
//			
//			//단어 논리명이나 물리명이 있는경우 미등록, 리턴처리..(데이터 미입력)
//			if(stwdVO == null) {
//				if(!stwdVO.getStwdLnm().equals(record.getStwdLnm())) {
//					return 0;
//				}
//				if(!stwdVO.getStwdPnm().equals(record.getStwdPnm())) {
//					return 0;
//				}
//			}
			String tmpStatus ;

			//도메인명을 조건으로 동일한 도메인이 존재하는지 확인 -> 파라미터 null아니면 기존데이터 업데이트
			WaaGovCdVal comvo = mapper.selectByPrimaryKey_cdVal2(record);
			
			if (comvo != null && StringUtils.hasText(comvo.getDmnLnm())) {
//				record.setStwdId(comvo.getStwdId());
				record.setIbsStatus("U");
				
				result = mapper.updateByPrimaryKeySelective_cdVal(record);
				return result;
			}
			tmpStatus = record.getIbsStatus();
			
//			//도메인그룹ID, 인포타입ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..(데이터 미입력)
//			if (comvo == null || !StringUtils.hasText(record.getStwdId())) { 
//				
//				record.setStwdId(objectIdGnrService.getNextStringId());
//			} else {
//				
//				mapper.updateExpDtm(record);
//			}
//			
			//도메인 아이디, 논리명으로 데이터가 있는지 확인하여 없을경우 -> 신규처리..(데이터 미입력)
//			WaaDmngInfotpMap tmpVO = mapper.selectByPrimaryKey(record.getStwdLnm());
			if (comvo == null || !comvo.getCdValNm().equals(record.getCdValNm()) || !comvo.getCdVal().equals(record.getCdVal())) { 
				
				record.setCdValId(objectIdGnrService.getNextStringId());
				record.setRegTypCd("C");
				record.setObjVers(1);
			} 
			result = mapper.insertSelective_cdVal(record);

			logger.debug("{}", record);
			return result;
		}

		@Override
		public int regGovCdValLstList(ArrayList<WaaGovCdVal> list) throws Exception {
			int result = 0;
			for (WaaGovCdVal WaaGovCdVal : list) {
				result += regGovCdValLstMap(WaaGovCdVal);
			}
			return result;
			
		}
		
		public int delgovCdValLstList(ArrayList<WaaGovCdVal> list) {
			int result = 0;
			for (WaaGovCdVal waaGovCdVal : list) {
				String cdVal = waaGovCdVal.getCdVal();
				String cdValNm = waaGovCdVal.getCdValNm();
				if (cdVal != null && !"".equals(cdVal) && !"".equals(cdValNm)) {
					//WaaDmng.setIbsStatus("D");

					result += mapper.deleteSelective_cdVal(waaGovCdVal);
				}
			}	
			return result;
		}
		
}