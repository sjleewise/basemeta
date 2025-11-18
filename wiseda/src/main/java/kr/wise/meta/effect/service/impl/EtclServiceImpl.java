/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : EtclServiceImpl.java
 * 2. Package : kr.wise.meta.effect.service.impl
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 4. 17:48:00
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열    :  2014. 7. 4. :            : 신규 개발.
 */
package kr.wise.meta.effect.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.effect.service.EtclMapper;
import kr.wise.meta.effect.service.EtclService;
import kr.wise.meta.effect.service.EtclVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : EtclServiceImpl.java
 * 3. Package  : kr.wise.meta.effect.service.impl
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 4. 17:48:00
 * </PRE>
 */
@Service("EtclService")
public class EtclServiceImpl implements EtclService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private EtclMapper mapper;

	/** 유성열 */
	public List<EtclVO> selectEtclTaskList(EtclVO search) {

		return mapper.selectEtclTaskList(search);
	}
	
	/** 유성열 */
	public List<EtclVO> selectEtclTaskSource(EtclVO search) {

		return mapper.selectEtclTaskSource(search);
	}
	
	/** 유성열 */
	public List<EtclVO> selectEtclTaskTarget(EtclVO search) {

		return mapper.selectEtclTaskTarget(search);
	}
	
}
