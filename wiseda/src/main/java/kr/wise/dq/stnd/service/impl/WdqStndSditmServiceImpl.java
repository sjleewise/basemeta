package kr.wise.dq.stnd.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.dq.stnd.service.WdqStndSditmService;
import kr.wise.dq.stnd.service.WdqmSditm;
import kr.wise.dq.stnd.service.WdqmSditmMapper;
import kr.wise.dq.stnd.service.WdqmStwdCnfg;
import kr.wise.dq.stnd.service.WdqmStwdCnfgMapper;
import kr.wise.dq.stnd.service.WdqmWhereUsed;
import kr.wise.dq.stnd.service.WdqmWhereUsedMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : StndSditmServiceImpl.java
 * 3. Package  : kr.wise.dq.stnd.service.impl
 * 4. Comment  : 
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 3. 오후 2:49:30
 * </PRE>
 */ 
@Service("wdqStndSditmService")
public class WdqStndSditmServiceImpl implements WdqStndSditmService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WdqmSditmMapper mapper;
		
	@Inject
	WdqmStwdCnfgMapper cnfgMapper;
	
	@Inject
	WdqmWhereUsedMapper whereUsedMapper;
	
	@Override
	public List<WdqmSditm> getStndItemList(WdqmSditm data) {
		
		return mapper.selectSditmList(data);
		//SLC 전용입니다.
//		return mapper.selectSditmListSLC(data);
	}

	@Override
	public WdqmSditm selectSditmDetail(String sditmId) {
		return mapper.selectByPrimaryKey(sditmId);
	}

	@Override
	public List<WdqmSditm> selectSditmChangeList(String sditmId) {
			logger.debug("search Id:{}", sditmId);
		
		return mapper.selectSditmChangeList(sditmId);
	}

	@Override
	public List<WdqmStwdCnfg> selectSditmInitList(String sditmId) {
		logger.debug("{}", sditmId);
		return cnfgMapper.selectSditmInitList(sditmId);
	}

	@Override
	public List<WdqmWhereUsed> selectSditmWhereUsedList(String sditmId) {
		return whereUsedMapper.selectSditmWhereUsedList(sditmId);
	}

	@Override
	public List<WdqmSditm> getSditmTransList(WdqmSditm data) {

		return mapper.getSditmTransList(data);
	}
	
	@Override
	public int saveSditmTransYnPrc(ArrayList<WdqmSditm> list){
		int result = 0;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		String userId = user.getUniqId();
		logger.debug("{}", list);
		
		for (WdqmSditm record : list) {
			result += mapper.saveSditmTransYnPrc(record);			
		}
		return result;	
	
	}

	@Override
	public List<WdqmSditm> selectSditmComparisonList(String sditmId) {
		
		return mapper.selectSditmComparisonList(sditmId);
	}
}
