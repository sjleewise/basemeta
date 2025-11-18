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
package kr.wise.dq.metadmn.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.metadmn.service.MetaDmnCdValItfVO;
import kr.wise.dq.metadmn.service.MetaDmnItfVO;
import kr.wise.dq.metadmn.service.MetaInterfaceMapper;
import kr.wise.dq.metadmn.service.MetaInterfaceService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("MetaInterfaceService")
public class MetaInterfaceServiceImpl implements MetaInterfaceService{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	MetaInterfaceMapper metaItfMapper;
	
	/* 메타도메인 조회*/
	@Override
	public MetaDmnItfVO getMetaDmnDtl(MetaDmnItfVO searchVO) {
		return metaItfMapper.selectMetaDmnDtl(searchVO);
	}
	
	/* 코드도메인 유효값 조회*/
	@Override
	public List<MetaDmnCdValItfVO> getMetaDmnCdValLst(String dmnId) {
		return  metaItfMapper.selectMetaDmnCdValLst(dmnId) ;
	}


}
