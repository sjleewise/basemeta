package kr.wise.dq.criinfo.bizarea.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.criinfo.bizarea.service.BizAreaService;
import kr.wise.dq.criinfo.bizarea.service.WahBizAreaMapper;
import kr.wise.dq.criinfo.bizarea.service.WahBizAreaVO;
import kr.wise.dq.criinfo.bizarea.service.WamBizAreaMapper;
import kr.wise.dq.criinfo.bizarea.service.WamBizAreaVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : BizAreaServiceImpl.java
 * 2. Package : kr.wise.dq.criinfo.bizarea.service.impl
 * 3. Comment : 
 * 4. 작성자  : wisePooh
 * 5. 작성일  : 2014. 3. 19. 오후 7:49:18
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    wisePooh : 2014. 3. 19. :            : 신규 개발.
 * </PRE>
 */
@Service("bizAreaService")
public class BizAreaServiceImpl implements BizAreaService {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamBizAreaMapper mapper;
	
	@Inject
	private WahBizAreaMapper wahmapper;
	
	public List<WamBizAreaVO> getBizAreaList(WamBizAreaVO search) {
		List<WamBizAreaVO> list = mapper.selectBizAreaList(search);
		return list;
	}

	public WamBizAreaVO getBizAreaDetail(String sBizAreaId) {
		WamBizAreaVO result = mapper.selectByPrimaryKey(sBizAreaId);
		return result;
	}

	public List<WahBizAreaVO> getBizAreaHstLst(String sBizAreaId) {
		 List<WahBizAreaVO> list = wahmapper.selectHstByBizAreaId(sBizAreaId);
		 return list;
	}

	@Override
	public List<WamBizAreaVO> getBizAreaListPop(WamBizAreaVO search) {
		// TODO Auto-generated method stub
		List<WamBizAreaVO> list = mapper.selectBizAreaListPop(search);
		return list;
	}
	
	
}
