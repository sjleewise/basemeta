package kr.wise.commons.damgmt.gov.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.gov.service.GovStndMapMapper;
import kr.wise.commons.damgmt.gov.service.GovStndMapService;
import kr.wise.commons.damgmt.gov.service.WaaGovDmnMap;
import kr.wise.commons.damgmt.gov.service.WaaGovSditmMap;
import kr.wise.commons.damgmt.gov.service.WaaGovStwdMap;
import kr.wise.commons.helper.UserDetailHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("govStndMapServiceImpl")
public class GovStndMapServiceImpl implements GovStndMapService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private GovStndMapMapper mapper;

    @Inject
    private EgovIdGnrService objectIdGnrService;

    //공통표준 단어 매핑
    @Override
	public WaaGovStwdMap selectStwdMapDetail(String govStwdId) {
		// TODO Auto-generated method stub
		return mapper.selectStwdMapDtlById(govStwdId);
	}

	@Override
	public List<WaaGovStwdMap> selectGovStwdMapList(WaaGovStwdMap record) {
		// TODO Auto-generated method stub
		return mapper.selectStwdMapList(record);
	}

	@Override
	public int saveGovStwdMapRow(WaaGovStwdMap saveVO) throws Exception {
		// TODO Auto-generated method stub
		return registerGovStwdMap(saveVO);
	}

	@Override
	public int deleteGovStwdMapList(ArrayList<WaaGovStwdMap> list) {
		// TODO Auto-generated method stub
		int result = 0;
		
		//등록유형코드 및 만료일자 업데이트
		for(WaaGovStwdMap record : list) {
			result += mapper.delGovStwdMap(record.getGovStwdId());
		}
		
		return result;
	}

	@Override
	public int saveGovStwdMapList(ArrayList<WaaGovStwdMap> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		
		for(WaaGovStwdMap record : list) {
			result += registerGovStwdMap(record);
		}
		
		return result;
	}

	@Override
	public List<WaaGovStwdMap> selectGovStwdMapChangeList(String govStwdId) {
		// TODO Auto-generated method stub
		return mapper.selectStwdMapChgList(govStwdId);
	}
	
	private int registerGovStwdMap(WaaGovStwdMap record) throws Exception {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		//id가 있는지 확인
		//기존 논리명 존재하는지 확인
		String govStwdId = mapper.selectGovStwdMapId(record);
		
		WaaGovStwdMap stwdInfo = new WaaGovStwdMap();
		
		if(record.getStwdLnm() != null && !record.getStwdLnm().equals("") ){
			stwdInfo = mapper.selectStwdByLnm(record);
			
			//기관표준 단어만 입력된 경우 (id 미존재, 논리명 존재)
			if((record.getStwdId() == null || record.getStwdId().equals(""))
					&& (record.getStwdLnm() != null && !record.getStwdLnm().equals(""))) {
				
				if(stwdInfo != null && stwdInfo.getStwdId() != null && !stwdInfo.getStwdId().equals("")) {
					record.setStwdId(stwdInfo.getStwdId());
					record.setStwdLnm(stwdInfo.getStwdLnm());
				} else {
					if(!record.getStwdLnm().contains("단어 미존재")) {
						record.setStwdId(null);
						record.setStwdLnm("[" + record.getStwdLnm() + "] 입력 단어 미존재");
					}
				}
			} else if(stwdInfo != null && !record.getStwdId().equals(stwdInfo.getStwdId())) {
				record.setStwdId(stwdInfo.getStwdId());
				record.setStwdLnm(stwdInfo.getStwdLnm());
			} else if(record.getStwdLnm() == null || record.getStwdLnm().equals("")){
				record.setStwdId(null);
				record.setStwdLnm(null);
			}  else if(stwdInfo == null) {
				if(!record.getStwdLnm().contains("단어 미존재")) {
					record.setStwdId(null);
					record.setStwdLnm("[" + record.getStwdLnm() + "] 입력 단어 미존재");
				}
			}
		} else {
			record.setStwdId(null);
		}
		
		
		
		if(record.getDmnYn() != null && record.getDmnYn().equals("Y")) {
			if(record.getGovDmngLnm() == null || record.getGovDmngLnm().equals("") || record.getGovDmngLnm().equals("-"))
				record.setGovDmngLnm("[형식단어여부가 'Y'인 경우, 필수 입력 사항입니다.]");
		} else if(record.getDmnYn() != null && record.getDmnYn().equals("N") && record.getGovDmngLnm().contains("필수 입력 사항입니다")) {
			record.setGovDmngLnm("-");
		}
				
		//id 존재 또는 기존 논리명 존재시 업데이트
		if((govStwdId != null && !govStwdId.equals("")) 
				|| (record.getGovStwdId() != null && !record.getGovStwdId().equals(""))) {
			record.setRegTypCd("U");
			record.setRegUserId(user.getId());
			
			//공통표준 논리명 존재 및 아이디 미존재시, 아이디 값 세팅
			if(record.getGovStwdLnm() != null && !record.getGovStwdLnm().equals("") 
					&& (record.getGovStwdId() == null || record.getGovStwdId().equals(""))) {
				record.setGovStwdId(govStwdId);
			}
			
			mapper.updateGovStwdMapExpDtm(record.getGovStwdId());
			
			result = mapper.insertGovStwdMap(record);
		}
		//id 미존재, 기존 논리명 미존재시 신규 등록
		else {
			record.setGovStwdId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setRegUserId(user.getId());
			
			result = mapper.insertGovStwdMap(record);
		}
		
		return result;
	}
	
	//공통표준 도메인 매핑
	@Override
	public WaaGovDmnMap selectDmnMapDetail(String govDmnId) {
		// TODO Auto-generated method stub
		return mapper.selectDmnMapDtlById(govDmnId);
	}

	@Override
	public List<WaaGovDmnMap> selectGovDmnMapList(WaaGovDmnMap record) {
		// TODO Auto-generated method stub
		return mapper.selectDmnMapList(record);
	}

	@Override
	public int saveGovDmnMapRow(WaaGovDmnMap saveVO) throws Exception {
		// TODO Auto-generated method stub
		return registerGovDmnMap(saveVO);
	}

	@Override
	public int deleteGovDmnMapList(ArrayList<WaaGovDmnMap> list) {
		// TODO Auto-generated method stub
		int result = 0;
		
		//등록유형코드 및 만료일자 업데이트
		for(WaaGovDmnMap record : list) {
			result += mapper.delGovDmnMap(record.getGovDmnId());
		}
		
		return result;
	}

	@Override
	public int saveGovDmnMapList(ArrayList<WaaGovDmnMap> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		
		for(WaaGovDmnMap record : list) {
			result += registerGovDmnMap(record);
		}
		
		return result;
	}

	@Override
	public List<WaaGovDmnMap> selectGovDmnMapChangeList(String govDmnId) {
		// TODO Auto-generated method stub
		return mapper.selectDmnMapChgList(govDmnId);
	}
	
	private int registerGovDmnMap(WaaGovDmnMap record) throws Exception {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		//id가 있는지 확인
		//기존 논리명 존재하는지 확인
		String govDmnId = mapper.selectGovDmnMapId(record);
		
		WaaGovDmnMap dmnInfo = null;
		
		if(record.getDmnLnm() != null && !record.getDmnLnm().equals("") ){
			dmnInfo = mapper.selectDmnByLnm(record);
			
			//기관표준 도메인만 입력된 경우 (id 미존재, 논리명 존재)
			if((record.getDmnId() == null || record.getDmnId().equals(""))
					&& (record.getDmnLnm() != null && !record.getDmnLnm().equals(""))) {
				
				if(dmnInfo != null && dmnInfo.getDmnId() != null && !dmnInfo.getDmnId().equals("")) {
					record.setDmnId(dmnInfo.getDmnId());
					record.setDmnLnm(dmnInfo.getDmnLnm());
				} else {
					if(!record.getDmnLnm().contains("도메인 미존재")) {
						record.setDmnId(null);
						record.setDmnLnm("[" + record.getDmnLnm() + "] 입력 도메인 미존재");
					}
				}
			} else if(dmnInfo != null && !record.getDmnId().equals(dmnInfo.getDmnId())) {
				record.setDmnId(dmnInfo.getDmnId());
				record.setDmnLnm(dmnInfo.getDmnLnm());
			} else if(record.getDmnLnm() == null || record.getDmnLnm().equals("")){
				record.setDmnId(null);
				record.setDmnLnm(null);
			}  else if(dmnInfo == null) {
				if(!record.getDmnLnm().contains("도메인 미존재")) {
					record.setDmnId(null);
					record.setDmnLnm("[" + record.getDmnLnm() + "] 입력 도메인 미존재");
				}
			}
		} else {
			record.setDmnId(null);
		}
		
		
				
		//id 존재 또는 기존 논리명 존재시 업데이트
		if((govDmnId != null && !govDmnId.equals("")) 
				|| (record.getGovDmnId() != null && !record.getGovDmnId().equals(""))) {
			record.setRegTypCd("U");
			record.setRegUserId(user.getId());
			
			//공통표준 논리명 존재 및 아이디 미존재시, 아이디 값 세팅
			if(record.getGovDmnLnm() != null && !record.getGovDmnLnm().equals("") 
					&& (record.getGovDmnId() == null || record.getGovDmnId().equals(""))) {
				record.setGovDmnId(govDmnId);
			}
			
			mapper.updateGovDmnMapExpDtm(record.getGovDmnId());
			
			result = mapper.insertGovDmnMap(record);
		}
		//id 미존재, 기존 논리명 미존재시 신규 등록
		else {
			record.setGovDmnId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setRegUserId(user.getId());
			
			result = mapper.insertGovDmnMap(record);
		}
		
		return result;
	}
	
	//공통표준 용어 매핑
	@Override
	public WaaGovSditmMap selectSditmMapDetail(String govSditmId) {
		// TODO Auto-generated method stub
		return mapper.selectSditmMapDtlById(govSditmId);
	}

	@Override
	public List<WaaGovSditmMap> selectGovSditmMapList(WaaGovSditmMap record) {
		// TODO Auto-generated method stub
		return mapper.selectSditmMapList(record);
	}

	@Override
	public int saveGovSditmMapRow(WaaGovSditmMap saveVO) throws Exception {
		// TODO Auto-generated method stub
		return registerGovSditmMap(saveVO);
	}

	@Override
	public int deleteGovSditmMapList(ArrayList<WaaGovSditmMap> list) {
		// TODO Auto-generated method stub
		int result = 0;
		
		//등록유형코드 및 만료일자 업데이트
		for(WaaGovSditmMap record : list) {
			result += mapper.delGovSditmMap(record.getGovSditmId());
		}
		
		return result;
	}

	@Override
	public int saveGovSditmMapList(ArrayList<WaaGovSditmMap> list) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		
		for(WaaGovSditmMap record : list) {
			result += registerGovSditmMap(record);
		}
		
		return result;
	}

	@Override
	public List<WaaGovSditmMap> selectGovSditmMapChangeList(String govSditmId) {
		// TODO Auto-generated method stub
		return mapper.selectSditmMapChgList(govSditmId);
	}
	
	private int registerGovSditmMap(WaaGovSditmMap record) throws Exception {
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		
		//id가 있는지 확인
		//기존 논리명 존재하는지 확인
		String govSditmId = mapper.selectGovSditmMapId(record);
		
		WaaGovSditmMap sditmInfo = null;
		
		if(record.getSditmLnm() != null && !record.getSditmLnm().equals("") ){
			sditmInfo = mapper.selectSditmByLnm(record);
			
			String govDmnId = mapper.selectGovDmnMapIdInSditm(record);
			record.setGovDmnId(govDmnId);
			
			//기관표준 용어만 입력된 경우 (id 미존재, 논리명 존재)
			if((record.getSditmId() == null || record.getSditmId().equals(""))
					&& (record.getSditmLnm() != null && !record.getSditmLnm().equals(""))) {
				
				if(sditmInfo != null && sditmInfo.getSditmId() != null && !sditmInfo.getSditmId().equals("")) {
					record.setSditmId(sditmInfo.getSditmId());
					record.setSditmLnm(sditmInfo.getSditmLnm());
				} else {
					if(!record.getSditmLnm().contains("용어 미존재")) {
						record.setSditmId(null);
						record.setSditmLnm("[" + record.getSditmLnm() + "] 입력 용어 미존재");
					}
				}
			} else if(sditmInfo != null && !record.getSditmId().equals(sditmInfo.getSditmId())) {
				record.setSditmId(sditmInfo.getSditmId());
				record.setSditmLnm(sditmInfo.getSditmLnm());
			} else if(record.getSditmLnm() == null || record.getSditmLnm().equals("")){
				record.setSditmId(null);
				record.setSditmLnm(null);
			} else if(sditmInfo == null) {
				if(!record.getSditmLnm().contains("용어 미존재")) {
					record.setSditmId(null);
					record.setSditmLnm("[" + record.getSditmLnm() + "] 입력 용어 미존재");
				}
			}
		} else {
			record.setSditmId(null);
		}
		
		
				
		//id 존재 또는 기존 논리명 존재시 업데이트
		if((govSditmId != null && !govSditmId.equals("")) 
				|| (record.getGovSditmId() != null && !record.getGovSditmId().equals(""))) {
			record.setRegTypCd("U");
			record.setRegUserId(user.getId());
			
			//공통표준 논리명 존재 및 아이디 미존재시, 아이디 값 세팅
			if(record.getGovSditmLnm() != null && !record.getGovSditmLnm().equals("") 
					&& (record.getGovSditmId() == null || record.getGovSditmId().equals(""))) {
				record.setGovSditmId(govSditmId);
			}
			
			mapper.updateGovSditmMapExpDtm(record.getGovSditmId());
			
			result = mapper.insertGovSditmMap(record);
		}
		//id 미존재, 기존 논리명 미존재시 신규 등록
		else {
			record.setGovSditmId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setRegUserId(user.getId());
			
			result = mapper.insertGovSditmMap(record);
		}
		
		return result;
	}
}
