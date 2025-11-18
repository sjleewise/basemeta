package kr.wise.dq.stnd.service.impl;

import java.util.List;

import javax.inject.Inject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.wise.dq.stnd.service.WdqStndDicService;
import kr.wise.dq.stnd.service.WdqmStwd;
import kr.wise.dq.stnd.service.WdqmStwdMapper;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDicServiceImpl.java
 * 3. Package  : kr.wise.dq.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 13. 오후 8:57:57
 * </PRE>
 */
@Service("wdqStndDicService")
public class WdqStndDicServiceImpl implements WdqStndDicService {

	private final Logger log = LoggerFactory.getLogger(getClass());


	@Inject
	private WdqmStwdMapper mapper;


	/** insomnia */
	@Override
	public List<WdqmStwd> getStndList(WdqmStwd data) {
		return mapper.selectStndList(data);
	}

}
