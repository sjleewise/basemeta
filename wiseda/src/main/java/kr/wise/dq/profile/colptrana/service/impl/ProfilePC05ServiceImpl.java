/*
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
package kr.wise.dq.profile.colptrana.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.anares.service.WamPrfResultVO;
import kr.wise.dq.profile.colefvaana.service.ProfilePC02Service;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaMapper;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaAnaVO;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfMapper;
import kr.wise.dq.profile.colefvaana.service.WamPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.ProfilePC05Service;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaMapper;
import kr.wise.dq.profile.colptrana.service.WamPrfPtrAnaVO;
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
@Service("profilePC05Service")
public class ProfilePC05ServiceImpl implements ProfilePC05Service{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamPrfPtrAnaMapper wamMapper;

	@Override
	public WamPrfPtrAnaVO getPrfPC05Dtl(String prfId) {
		WamPrfPtrAnaVO result = wamMapper.selectByPrimaryKeyByPrfKndCd(prfId);
		return result;
	}
	
	@Override
	public List<WamPrfPtrAnaVO> getPrfPC05UserDfLst(String prfId) {
		return wamMapper.selectByPrimaryKey(prfId);
	}

	@Override
	public List<WamPrfMstrCommonVO> profileListPtrAna(WamPrfMstrCommonVO search) {
		return wamMapper.profileListPtrAna(search);
	}

}
