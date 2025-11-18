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
package kr.wise.commons.code.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.WiseConfig;
import kr.wise.commons.WiseMetaConfig;
import kr.wise.commons.WiseMetaConfig.CodeListAction;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.code.service.CodeListMapper;
import kr.wise.commons.code.service.CodeListService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.CodeSyncService;
import kr.wise.commons.code.service.CodeSyncSrcMapper;
import kr.wise.sysinf.codeSync.service.CodeSyncTgtMapper;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.code.service.ComplexCodeVo;
import kr.wise.commons.code.service.SimpleCodeVo;
import kr.wise.commons.damgmt.approve.service.WaaRqstAprp;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service ("codeSyncService")
public class CodeSyncServiceImpl implements CodeSyncService {

	Logger logger = LoggerFactory.getLogger(getClass());

	//private List<CodeListVo> codeList;

	//@Inject
	//private CodeListMapper codeListMapper;

	/**타겟이 되는 DB의 MAPPER */
	@Inject
	private CodeSyncTgtMapper codeSyncTgtMapper;
	
	/** 메타 DB MAPPER*/
	@Inject
	private CodeSyncSrcMapper codeSyncSrcMapper;
	
	@Override
    public void syncCode(){
		int result =0;
		try{
        
		//타겟에서 단순코드 추출
		List<SimpleCodeVo> simpleCodeVo =  codeSyncTgtMapper.getTgtSimpleCodeList();
        
		//단순코드 임시 테이블 삭제
		result += codeSyncSrcMapper.deleteWatSmpCd();
		//메타 단순코드 임시 테이블에 입력
        for(SimpleCodeVo vo : simpleCodeVo){
             result  += codeSyncSrcMapper.insertSimpleCodeToMeta(vo);
        }
        
        //단순코드 갭테이블 삭제
        result += codeSyncSrcMapper.deleteWatGapSmpCd();
        //단순코드 갭분석
        result += codeSyncSrcMapper.simpleCodeGapExec();
        
        //단순코드 SYNC
        logger.debug("단순코드 sync시작");
        List<SimpleCodeVo> simpleCodeGapVo = codeSyncSrcMapper.getGapSimpleCodeList();
        for(SimpleCodeVo vo : simpleCodeGapVo){
        	logger.debug("단순코드 gap_status = "+ vo.getGapStatus());
            if(vo.getGapStatus().equals("I")){
               logger.debug("단순코드 입력");
        	   result += codeSyncTgtMapper.insertSimpleCode(vo);
            }else if(vo.getGapStatus().equals("U")){
               logger.debug("단순코드 수정");
               result += codeSyncTgtMapper.updateSimpleCode(vo);
            }else if(vo.getGapStatus().equals("D")){
               logger.debug("단순코드 삭제");
               result += codeSyncTgtMapper.deleteSimpleCode(vo);
            }
        }
        logger.debug("단순코드 sync종료");
       } 
	   catch(Exception e){
			logger.debug("단순코드 sync오류 발생");
	   }
		
		try{
			//타겟에서 복잡코드 추출
		    List<ComplexCodeVo> complexCodeVo =  codeSyncTgtMapper.getTgtComplexCodeList();
        
		//복잡코드 임시 테이블 삭제
		result += codeSyncSrcMapper.deleteWatCmpCd();
		//메타 복잡코드 임시 테이블에 입력
        for(ComplexCodeVo vo : complexCodeVo){
             result  += codeSyncSrcMapper.insertComplexCodeToMeta(vo);
        }
        
        //복잡코드 갭테이블 삭제
        result += codeSyncSrcMapper.deleteWatGapCmpCd();
        //복잡코드 갭분석
        result += codeSyncSrcMapper.complexCodeGapExec();
        
        //복잡코드 SYNC
        logger.debug("복잡코드 sync시작");
        List<ComplexCodeVo> complexCodeGapVo = codeSyncSrcMapper.getGapComplexCodeList();
        for(ComplexCodeVo vo : complexCodeGapVo){
        	logger.debug("복잡코드 gap_status = "+ vo.getGapStatus());
            if(vo.getGapStatus().equals("I")){
               logger.debug("복잡코드 입력");
        	   result += codeSyncTgtMapper.insertComplexCode(vo);
            }else if(vo.getGapStatus().equals("U")){
               logger.debug("복잡코드 수정");
               result += codeSyncTgtMapper.updateComplexCode(vo);
            }else if(vo.getGapStatus().equals("D")){
               logger.debug("복잡코드 삭제");
               result += codeSyncTgtMapper.deleteComplexCode(vo);
            }
        }
        logger.debug("복잡코드 sync종료");
			
			
		}catch(Exception e){
			logger.debug("복잡코드 sync오류 발생");
		}
	}
}
