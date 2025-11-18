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
package kr.wise.dq.profile.colefvaana.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.colefvaana.service.ProfilePC02Service;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaMapper;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfMapper;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;


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
@Service("profilePC02Service")
public class ProfilePC02ServiceImpl implements ProfilePC02Service{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamPrfEfvaAnaMapper wamMapper;
	
	@Inject
	WamPrfEfvaUserDfMapper wamPC02UserDfMapper;

	@Override
	public WamPrfEfvaAnaVO getPrfPC02Dtl(String prfId) {
		WamPrfEfvaAnaVO result = wamMapper.selectByPrimaryKey(prfId);
		return result;
	}
	
	@Override
	public List<WamPrfEfvaUserDfVO> getPrfPC02UserDfLst(String prfId) {
		return wamPC02UserDfMapper.selectByPrimaryKey(prfId);
	}

	/* 유효값분석 프로파일 조회 */
	@Override
	public List<WamPrfMstrCommonVO> profileListCodeAna(WamPrfMstrCommonVO search) {
		return wamMapper.profileListCodeAna(search);
	}
	
}
