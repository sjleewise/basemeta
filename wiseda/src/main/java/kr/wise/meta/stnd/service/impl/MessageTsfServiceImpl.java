/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : MessageTsfServiceImpl.java
 * 2. Package : kr.wise.meta.stnd.service.impl
 * 3. Comment : 메시지 조회 서비스 구현체....
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015. 12. 02 
 * 6. 변경이력 :

 */
package kr.wise.meta.stnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.CommonVo;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.exception.WiseBizException;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.approve.service.RequestApproveService;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.meta.stnd.service.MessageTsfService;
import kr.wise.meta.stnd.service.WamMsgTsf;
import kr.wise.meta.stnd.service.WamMsgTsfMapper;

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
@Service("MessageTsfService")
public class MessageTsfServiceImpl implements MessageTsfService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WamMsgTsfMapper wammapper;

	
	
		/** 
		 * 메시지 리스트 호출
		 * lsi */
	public List<WamMsgTsf> getMessageTsfList(WamMsgTsf data) {

		logger.debug("searchvo:{}", data);

		return wammapper.selectList(data);
	}
	
			/** 
			 * 메시지 상세정보 호출
			 * lsi */
    public WamMsgTsf selectMessageTsfDetail(WamMsgTsf searchVo){
    
    	logger.debug("searchVo:{}", searchVo.getMsgId());
    	
    	return wammapper.selectMessageTsfDtl(searchVo);
    }
    
    		/** 
		 * 메시지 변경이력 리스트 호출
		 * lsi */
	public List<WamMsgTsf> selectMessageTsfChangeList(WamMsgTsf searchVo) {

		logger.debug("searchVo:{}", searchVo);

		return wammapper.selectChangeTsfList(searchVo);
	}


	
}
