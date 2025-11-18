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
package kr.wise.dq.profile.colana.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.profile.colana.service.ProfileColAnaService;
import kr.wise.dq.profile.colana.service.WahPrfColAnaMapper;
import kr.wise.dq.profile.colana.service.WamPrfColAnaMapper;
import kr.wise.dq.profile.colana.service.WamPrfColAnaVO;
import kr.wise.dq.profile.mstr.service.ProfileMstrService;
import kr.wise.dq.profile.mstr.service.WahPrfMstrMapper;
import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.mstr.service.WamPrfMstrMapper;
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
@Service("profileColAnaService")
public class ProfileColAnaServiceImpl implements ProfileColAnaService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	WamPrfColAnaMapper wamMapper;

	@Override
	public WamPrfColAnaVO getColAnaDtl(String prfId) {
		WamPrfColAnaVO result = wamMapper.selectByPrimaryKey(prfId);
		return result;
	}
	
	/* 컬럼분석 프로파일 조회 */
	@Override
	public List<WamPrfMstrCommonVO> profileListColAna(WamPrfMstrCommonVO search) {
		return wamMapper.profileListColAna(search);
	}
}
