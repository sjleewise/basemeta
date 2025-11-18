package kr.wise.meta.stnd.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.stnd.service.StndDicService;
import kr.wise.meta.stnd.service.WamStwd;
import kr.wise.meta.stnd.service.WamStwdMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : StndDicServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 3. 13. 오후 8:57:57
 * </PRE>
 */
@Service("stndDicService")
public class StndDicServiceImpl implements StndDicService {

	private final Logger log = LoggerFactory.getLogger(getClass());


	@Inject
	private WamStwdMapper mapper;


	/** insomnia */
	@Override
	public List<WamStwd> getStndList(WamStwd data) {
		return mapper.selectStndList(data);
	}

}
