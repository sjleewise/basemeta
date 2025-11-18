/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileServiceImpl.java
 * 2. Package : kr.wise.dq.criinfo.profile.service.impl
 * 3. Comment : 
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:35:18
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.profile.anares.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.anares.service.ProfileResultService;
import kr.wise.dq.profile.anares.service.WamPrfResultMapper;
import kr.wise.dq.profile.anares.service.WamPrfResultVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ColProfileServiceImpl.java
 * 3. Package  : kr.wise.dq.criinfo.profile.service.impl
 * 4. Comment  : 
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 3. 24. 오후 1:35:18
 * </PRE>
 */
@Service("ProfileResultService")
public class ProfileResultServiceImpl implements ProfileResultService{
	
	Logger logger = LoggerFactory.getLogger(getClass());


	/* 프로파일 결과 */
	@Inject
	private WamPrfResultMapper wamPrfMstrMapper;


	/* 프로파일 결과 리스트 조회 */
	@Override
	public List<WamPrfResultVO> getPrfLst(WamPrfResultVO search) {
		return wamPrfMstrMapper.selectPrfLst(search);
	}

	/* 컬럼프로파일 결과 조회 */
	@Override
	public WamPrfResultVO getColAnaResDtl(String prfId) {
		// TODO Auto-generated method stub
		return wamPrfMstrMapper.selectColAnaResDtl(prfId);
	}
	
	@Override
	public List<WamPrfResultVO> getColAnaTrsLst(String prfId) {
		// TODO Auto-generated method stub
		return wamPrfMstrMapper.selectColAnaTrsLst(prfId);
	}
}
