/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CodeSyncService.java
 * 2. Package : kr.wise.common.code.service
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015.10.30
 * 6. 변경이력 :

 */
package kr.wise.sysinf.messageSync.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.sysinf.messageSync.service.MessageSyncService;
import kr.wise.sysinf.messageSync.service.MessageSyncSrcMapper;
import kr.wise.sysinf.messageSync.service.MessageSyncTgtMapper;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.code.service.ComplexCodeVo;
import kr.wise.sysinf.messageSync.service.MessageCodeVo;
import kr.wise.commons.damgmt.approve.service.WaaRqstAprp;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service ("messageSyncService")
public class MessageSyncServiceImpl implements MessageSyncService {

	Logger logger = LoggerFactory.getLogger(getClass());

	//private List<CodeListVo> codeList;

	//@Inject
	//private CodeListMapper codeListMapper;

	/**타겟이 되는 DB의 MAPPER */
	@Inject
	private MessageSyncTgtMapper messageSyncTgtMapper;
	
	/** 메타 DB MAPPER*/
	@Inject
	private MessageSyncSrcMapper messageSyncSrcMapper;
	
	@Override
    public void syncMessage(){
		int result =0;
		try{
        
		//타겟에서 메시지코드 추출
		List<MessageCodeVo> MessageCodeVo =  messageSyncTgtMapper.getTgtMessageCodeList();
        
		//메시지코드 임시 테이블 삭제
		result += messageSyncSrcMapper.deleteWatMsgCd();
		//메타 메시지코드 임시 테이블에 입력
        for(MessageCodeVo vo : MessageCodeVo){
            result  += messageSyncSrcMapper.insertMessageCodeToMeta(vo);
       }
        
       // 메시지코드 갭테이블 삭제
        result += messageSyncSrcMapper.deleteWatGapMsgCd();
        //메시지코드 갭분석
        result += messageSyncSrcMapper.messageCodeGapExec();
        
        //메시지코드 SYNC
        logger.debug("메시지코드 sync시작");
        List<MessageCodeVo> messageCodeGapVo = messageSyncSrcMapper.getGapMessageCodeList();
        for(MessageCodeVo vo : messageCodeGapVo){
        	logger.debug("메시지코드 gap_status = "+ vo.getGapStatus());
            if(vo.getGapStatus().equals("I")){
               logger.debug("메시지코드 입력");
        	   result += messageSyncTgtMapper.insertMessageCode(vo);
            }else if(vo.getGapStatus().equals("U")){
               logger.debug("메시지코드 수정");
               result += messageSyncTgtMapper.updateMessageCode(vo);
            }else if(vo.getGapStatus().equals("D")){
               logger.debug("메시지코드 삭제");
               result += messageSyncTgtMapper.deleteMessageCode(vo);
            }
        }
        logger.debug("메시지코드 sync종료");
       } 
	   catch(Exception e){
			logger.debug("메시지코드 sync오류 발생");
	   }
		

	}
		
		
}
