/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MsgCodeServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 메시지 등록요청 서비스 구현체....
 * 4. 작성자  : 
 * 5. 작성일  :  
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    :            : 신규 개발.
 */
package kr.wise.meta.stnd.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.stnd.service.MsgCodeService;
import kr.wise.meta.stnd.service.WamActnMsg;
import kr.wise.meta.stnd.service.WamActnMsgMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : MsgCodeServiceImpl.java
 * 3. Package  : kr.wise.meta.stnd.service.impl
 * 4. Comment  :
 * 5. 작성자   : 
 * 6. 작성일   : 
 * </PRE>
 */
@Service("MsgCodeService")
public class MsgCodeServiceImpl implements MsgCodeService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamActnMsgMapper wammapper;

	/** 
	 * 메시지 리스트 호출
	 *  */
	public List<WamActnMsg> getCodeMessageList(WamActnMsg data) {
		
		logger.debug("searchvo:{}", data);
		
		return wammapper.selectList(data);
	}
	
	/** 
	 * 메시지 상세정보 호출
	 *  */
	public WamActnMsg selectCodeMessageDetail(String actnMsgId){
		
		logger.debug("actnMsgId:{}", actnMsgId);
		
		return wammapper.selectMessageDtl(actnMsgId);
	}
	
	/** 
	 * 메시지 변경이력 리스트 호출
	 *  */
	public List<WamActnMsg> selectCodeMessageChangeList(String actnMsgId) {
		
		logger.debug("actnMsgId:{}", actnMsgId);
		
		return wammapper.selectChangeList(actnMsgId);
	}

	
}
