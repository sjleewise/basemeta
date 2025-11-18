package kr.wise.commons.damgmt.approve.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.AprgService;
import kr.wise.commons.damgmt.approve.service.WaaAprg;
import kr.wise.commons.damgmt.approve.service.WaaAprgMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("aprgService")
public class AprgServiceImpl implements AprgService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaAprgMapper mapper;
	
	@Inject
	private EgovIdGnrService objectIdGnrService; 
	
	public List<WaaAprg> getList(WaaAprg search) {
		List<WaaAprg> list = mapper.selectList(search);
		return list;

	}

	public WaaAprg findAprg(WaaAprg search) {
		// TODO Auto-generated method stub
		return null;
	}

	public int regAprg(WaaAprg record) throws Exception {
		logger.debug("AprgServiceImpl.Java saveAprg Method");
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
						
				
		
		//메뉴ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getAprgId())) {  // 신규...
			
			
			record.setAprgId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			
		} else if("U".equals(tmpStatus) || "D".equals(tmpStatus) || StringUtils.hasText(record.getAprgId())) {
			if ("I".equals(tmpStatus)) {
				record.setRegTypCd("U");
			} else {
				record.setRegTypCd(tmpStatus);
			}
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else { 
				record.setObjVers(record.getObjVers()+1);
			}
			//result = menuMapper.updateByPrimaryKeySelective(record);
		} 
		
		mapper.updateExpDtm(record);
		record.setWritUserId(user.getUniqId());
		result = mapper.insertSelective(record);
		return result;
	}

	public int regList(ArrayList<WaaAprg> list) throws Exception {
		int result = 0;

		for (WaaAprg record : list) {

			
			//그리드 상태가 있을 경우만 DB에 처리한다...
			if(!UtilString.isBlank(record.getIbsStatus())) {
				result += regAprg(record);
			}

		}

		return result;
	}

	public int delList(ArrayList<WaaAprg> list) throws Exception {
		int result = 0;
		for (WaaAprg WaaAprg : list) {
			String id = WaaAprg.getAprgId();
			if (id != null && !"".equals(id)) {
				WaaAprg.setIbsStatus("D");

				result += regAprg(WaaAprg);
			}
		}

		return result;
	}

	@Override
	public int getAprgCnt(WaaAprg waaAprg) {
		// TODO Auto-generated method stub
		return mapper.selectAprgCnt(waaAprg);
	}

}
