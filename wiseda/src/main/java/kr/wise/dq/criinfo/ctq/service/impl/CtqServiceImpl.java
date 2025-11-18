package kr.wise.dq.criinfo.ctq.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.criinfo.ctq.service.CtqService;
import kr.wise.dq.criinfo.ctq.service.WahCtqMapper;
import kr.wise.dq.criinfo.ctq.service.WahCtqVO;
import kr.wise.dq.criinfo.ctq.service.WamCtqMapper;
import kr.wise.dq.criinfo.ctq.service.WamCtqVO;
import kr.wise.dq.criinfo.ctq.service.WamCtqWhereUsed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : CtqServiceImpl.java
 * 2. Package : kr.wise.dq.criinfo.ctq.service.impl
 * 3. Comment : 
 * 4. 작성자  : wisePooh
 * 5. 작성일  : 2014. 3. 19. 오후 7:49:18
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    wisePooh : 2014. 3. 19. :            : 신규 개발.
 * </PRE>
 */
@Service("ctqService")
public class CtqServiceImpl implements CtqService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamCtqMapper mapper;
	
	@Inject
	private WahCtqMapper wahmapper;
	
	public List<WamCtqVO> getCtqList(WamCtqVO search) {
		List<WamCtqVO> list = mapper.selectCtqList(search);
		return list;
	}

	public WamCtqVO getCtqDetail(String sCtqId) {
		WamCtqVO result = mapper.selectByPrimaryKey(sCtqId);
		return result;
	}

	public List<WahCtqVO> getCtqHstLst(String sCtqId) {
		 List<WahCtqVO> list = wahmapper.selectHstByCtqId(sCtqId);
		 return list;
	}
	
	public List<WamCtqWhereUsed> getCtqWhereusedLst(String sCtqId) {
		 List<WamCtqWhereUsed> list = wahmapper.selectWhereusedByCtqId(sCtqId);
		 return list;
	}

	
}
