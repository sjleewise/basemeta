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
package kr.wise.dq.profile.tblunq.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.tblunq.service.ProfilePT02Service;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColMapper;
import kr.wise.dq.profile.tblunq.service.WamPrfUnqColVO;


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
@Service("profilePT02Service")
public class ProfilePT02ServiceImpl implements ProfilePT02Service{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamPrfUnqColMapper wamMapper;

	
	@Override
	public WamPrfUnqColVO getPrfPT02Dtl(String prfId) {
		return wamMapper.selectByPrimaryKey(prfId);
	}
	
	@Override
	public List<WamPrfUnqColVO> getPrfPT02UnqColLst(String prfId) {
		List<WamPrfUnqColVO> result = wamMapper.selectListByPrimaryKey(prfId);
		return result;
	}

	//프로파일조회 중복분석
	@Override
	public List<WamPrfMstrCommonVO> profileListUnqAna(WamPrfMstrCommonVO search) {
		List<WamPrfMstrCommonVO> list = wamMapper.profileListUnqAna(search);
		return list;
	}
	
	

}
