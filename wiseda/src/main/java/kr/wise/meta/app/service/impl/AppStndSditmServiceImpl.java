package kr.wise.meta.app.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.app.service.AppStndSditmService;
import kr.wise.meta.app.service.WamAppSditm;
import kr.wise.meta.app.service.WamAppSditmMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : AppStndSditmServiceImpl.java
 * 3. Package  : kr.wise.meta.app.service.impl
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */ 
@Service("appStndSditmService")
public class AppStndSditmServiceImpl implements AppStndSditmService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamAppSditmMapper mapper;
		
	
	@Override
	public List<WamAppSditm> getStndItemList(WamAppSditm data) {
		
		return mapper.selectSditmList(data);
		//SLC 전용입니다.
//		return mapper.selectSditmListSLC(data);
	}

	@Override
	public WamAppSditm selectSditmDetail(String sditmId) {
		return mapper.selectByPrimaryKey(sditmId);
	}

	@Override
	public List<WamAppSditm> selectSditmChangeList(String sditmId) {
			logger.debug("search Id:{}", sditmId);
		
		return mapper.selectSditmChangeList(sditmId);
	}

}
