/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AppStndWordServiceImpl.java
 * 2. Package : kr.wise.meta.app.service.impl
 * 3. Comment : APP표준단어 조회 서비스 구현체
 * 4. 작성자  : mse
 * 5. 작성일   : 2016. 3. 16. 오전 10:55:20
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    mse : 2016. 3. 16. :            : 신규 개발.
 */
package kr.wise.meta.app.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.app.service.AppStndWordService;
import kr.wise.meta.app.service.WamAppStwd;
import kr.wise.meta.app.service.WamAppStwdMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AppStndWordServiceImpl.java
 * 3. Package  : kr.wise.meta.app.service.impl
 * 4. Comment  :
 * 5. 작성자   : mse
 * 6. 작성일   : 2016. 3. 16. 오전 10:55:20
 * </PRE>
 */
@Service("appStndWordService")
public class AppStndWordServiceImpl implements AppStndWordService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamAppStwdMapper mapper;

	public List<WamAppStwd> getStndWordList(WamAppStwd data) {

		logger.debug("searchvo:{}", data);

		return mapper.selectList(data);
	}

	public WamAppStwd selectStndWordDetail(String stwdId) {
		logger.debug("searchId:{}", stwdId);

		return mapper.selectWordDetail(stwdId);
	}

	public List<WamAppStwd> selectStndWordChangeList(String stwdId) {
		logger.debug("search Id:{}", stwdId);

		return mapper.selectWordChangeList(stwdId);
	}

}
