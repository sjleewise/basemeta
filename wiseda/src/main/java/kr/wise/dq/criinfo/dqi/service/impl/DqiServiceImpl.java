package kr.wise.dq.criinfo.dqi.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.criinfo.dqi.service.DqiService;
import kr.wise.dq.criinfo.dqi.service.WahDqiMapper;
import kr.wise.dq.criinfo.dqi.service.WahDqiVO;
import kr.wise.dq.criinfo.dqi.service.WamDqiMapper;
import kr.wise.dq.criinfo.dqi.service.WamDqiVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DqiServiceImpl.java
 * 2. Package : kr.wise.dq.criinfo.dqi.service.impl
 * 3. Comment : 
 * 4. 작성자  : wisePooh
 * 5. 작성일  : 2014. 3. 19. 오후 7:49:18
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    wisePooh : 2014. 3. 19. :            : 신규 개발.
 * </PRE>
 */
@Service("dqiService")
public class DqiServiceImpl implements DqiService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamDqiMapper mapper;
	
	@Inject
	private WahDqiMapper wahmapper;
	
	public List<WamDqiVO> getDqiList(WamDqiVO search) {
		List<WamDqiVO> list = mapper.selectDqiList(search);
		return list;
	}

	public WamDqiVO getDqiDetail(String sDqiId) {
		WamDqiVO result = mapper.selectByPrimaryKey(sDqiId);
		return result;
	}

	public List<WahDqiVO> getDqiHstLst(String sDqiId) {
		 List<WahDqiVO> list = wahmapper.selectHstByDqiId(sDqiId);
		 return list;
	}
	
	
}
