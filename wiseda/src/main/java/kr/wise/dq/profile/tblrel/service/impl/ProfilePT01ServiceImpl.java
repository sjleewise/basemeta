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
package kr.wise.dq.profile.tblrel.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;
import kr.wise.dq.profile.tblrel.service.ProfilePT01Service;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColMapper;
import kr.wise.dq.profile.tblrel.service.WamPrfRelColVO;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblMapper;
import kr.wise.dq.profile.tblrel.service.WamPrfRelTblVO;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import kr.wise.sysinf.erwin.service.R7MartMapper;

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
@Service("profilePT01Service")
public class ProfilePT01ServiceImpl implements ProfilePT01Service{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	WamPrfRelTblMapper wamRelTblMapper;

	@Inject
	WamPrfRelColMapper wamRelColMapper;

	@Inject
	private	R7MartMapper r7mapper;
	
	@Override
	public WamPrfRelTblVO getPrfPT01Dtl(String prfId) {
		return wamRelTblMapper.selectByPrimaryKey(prfId);
	}

	@Override
	public List<WamPrfRelColVO> getPrfPT01RelColLst(String prfId) {
		List<WamPrfRelColVO> result = wamRelColMapper.selectListByPrimaryKey(prfId);
		return result;
	}

	@Override
	public List<WamPrfMstrCommonVO> profileListRelAna(WamPrfMstrCommonVO search) {
		List<WamPrfMstrCommonVO> list = wamRelColMapper.profileListRelAna(search);
		return list;
	}
	
	
	/* 모델마트연계 관계정보 조회*/
	@Override
	public List<WaqPrfRelColVO> getErMart7RelLst(WaqPrfRelColVO search) {
		return  r7mapper.selectErMart7RelLst(search) ;
	}

}
