/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AsisDmnCdValServiceImpl.java
 * 2. Package : kr.wise.meta.model.service.impl
 * 3. Comment : 메시지 등록요청 서비스 구현체....
 * 4. 작성자  : 
 * 5. 작성일  :  
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    :            : 신규 개발.
 */
package kr.wise.meta.model.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.model.service.AsisDmnCdValService;
import kr.wise.meta.model.service.WamAsisDmnCdVal;
import kr.wise.meta.model.service.WamAsisDmnCdValMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AsisDmnCdValServiceImpl.java
 * 3. Package  : kr.wise.meta.model.service.impl
 * 4. Comment  :
 * 5. 작성자   : 
 * 6. 작성일   : 
 * </PRE>
 */
@Service("AsisDmnCdValService")
public class AsisDmnCdValServiceImpl implements AsisDmnCdValService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamAsisDmnCdValMapper wammapper;

    public List<WamAsisDmnCdVal> getAsisDmnCdValList(WamAsisDmnCdVal data) {
		
		logger.debug("searchvo:{}", data);
		
		return wammapper.selectList(data);
	}
	
    public WamAsisDmnCdVal selectAsisDmnCdValDetail(String dmnId){
		
		logger.debug("dmnId:{}", dmnId);
		
		return wammapper.selectDtl(dmnId);
	}
	
    public List<WamAsisDmnCdVal> selectAsisDmnCdValChangeList(String dmnId) {
		
		logger.debug("dmnId:{}", dmnId);
		
		return wammapper.selectChangeList(dmnId);
	}
}
