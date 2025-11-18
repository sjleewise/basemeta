/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : OlapServiceImpl.java
 * 2. Package : kr.wise.meta.effect.service.impl
 * 3. Comment :
 * 4. 작성자  : 유성열
 * 5. 작성일  : 2014. 7. 8. 15:10
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    유성열    :  2014. 7. 8. :            : 신규 개발.
 */
package kr.wise.meta.effect.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.effect.service.OlapMapper;
import kr.wise.meta.effect.service.OlapService;
import kr.wise.meta.effect.service.OlapVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : OlapServiceImpl.java
 * 3. Package  : kr.wise.meta.effect.service.impl
 * 4. Comment  :
 * 5. 작성자   : 유성열
 * 6. 작성일   : 2014. 7. 8. 15:10
 * </PRE>
 */
@Service("OlapService")
public class OlapServiceImpl implements OlapService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private OlapMapper mapper;

	/** 유성열 */
	public List<OlapVO> selectOlapReportList(OlapVO search) {

		return mapper.selectOlapReportList(search);
	}
	
	/** 유성열 */
	public List<OlapVO> selectOlapReportDetailList(OlapVO search) {

		return mapper.selectOlapReportDetailList(search);
	}
	
}
