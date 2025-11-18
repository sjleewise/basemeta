/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MsgRqstServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 메시지 등록요청 서비스 구현체....
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 9. 24. 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    이상익 : 2015. 9. 25. :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.stnd.service.MsgService;
import kr.wise.meta.stnd.service.WamMsg;
import kr.wise.meta.stnd.service.WamMsgMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : MsgRqstServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : 이상익
 * 6. 작성일   : 2015. 9. 25. 
 * </PRE>
 */
@Service("MsgService")
public class MsgServiceImpl implements MsgService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamMsgMapper wammapper;

	
		/** 
		 * 메시지 리스트 호출
		 * lsi */
	public List<WamMsg> getMessageList(WamMsg data) {

		logger.debug("searchvo:{}", data);

		return wammapper.selectList(data);
	}
	
			/** 
			 * 메시지 상세정보 호출
			 * lsi */
    public WamMsg selectMessageDetail(String msgId){
    
    	logger.debug("msgId:{}", msgId);
    	
    	return wammapper.selectMessageDtl(msgId);
    }
    
    		/** 
		 * 메시지 변경이력 리스트 호출
		 * lsi */
	public List<WamMsg> selectMessageChangeList(String msgId) {

		logger.debug("msgId:{}", msgId);

		return wammapper.selectChangeList(msgId);
	}
	
}
