package kr.wise.meta.mta.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.helper.UserDetailHelper;
/*import kr.wise.meta.cenm.service.CenmService;*/
import kr.wise.meta.mta.service.MtaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("mtaService")
public class MtaServiceImpl implements MtaService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

//	@Inject
//	private WamStwdMapper mapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;

//	@Inject
//	private WamWhereUsedMapper whereUsedMapper;
//	
//	@Inject
//	private WamStwdAbrMapper abrMapper;
//	
//	@Inject
//	private StndWordAbrService stndWordAbrService;

	
//	public List<WamStwd> getStndWordList(WamStwd data) {
//
//		logger.debug("searchvo:{}", data);
//
//		return mapper.selectList(data);
//	}


}
