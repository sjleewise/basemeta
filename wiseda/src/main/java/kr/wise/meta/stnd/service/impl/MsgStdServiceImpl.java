/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MsgStdServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 메시지 등록요청 서비스 구현체....
 * 4. 작성자  : 
 * 5. 작성일  : . 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                     :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.stnd.service.MsgService;
import kr.wise.meta.stnd.service.MsgStdService;
import kr.wise.meta.stnd.service.WamMsg;
import kr.wise.meta.stnd.service.WamMsgMapper;
import kr.wise.meta.stnd.service.WamStdErrMsg;
import kr.wise.meta.stnd.service.WamStdErrMsgMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : MsgStdServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : 
 * 6. 작성일   : 
 * </PRE>
 */
@Service("MsgStdService")
public class MsgStdServiceImpl implements MsgStdService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamStdErrMsgMapper wammapper;

	/** 
	 * 메시지 리스트 호출
	 *  */
	public List<WamStdErrMsg> getStdMessageList(WamStdErrMsg data) {
		
		logger.debug("searchvo:{}", data);
		
		return wammapper.selectList(data);
	}
	
	/** 
	 * 메시지 상세정보 호출
	 *  */
	public WamStdErrMsg selectStdMessageDetail(String stdErrMsgId){
		
		logger.debug("stdErrMsgId:{}", stdErrMsgId);
		
		return wammapper.selectMessageDtl(stdErrMsgId);
	}
	
	/** 
	 * 메시지 변경이력 리스트 호출
	 *  */
	public List<WamStdErrMsg> selectStdMessageChangeList(String stdErrMsgId) {
		
		logger.debug("stdErrMsgId:{}", stdErrMsgId);
		
		return wammapper.selectChangeList(stdErrMsgId);
	}

	
}
