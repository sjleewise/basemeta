/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CodeSyncSrcMapper.java
 * 2. Package : 
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015.10.30
 * 6. 변경이력 : 메타 입력을 위한  매퍼

 */
package kr.wise.sysinf.messageSync.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;



@Mapper
public interface MessageSyncSrcMapper {

	int deleteWatMsgCd();// 메시지코드 임시테이블 삭제
	int insertMessageCodeToMeta(MessageCodeVo vo);	//타겟 메시지코드테이블에서 단순코드 추출하여 메타에 입력
	
	int deleteWatGapMsgCd();  //메시지코드 gap테이블 삭제
	int messageCodeGapExec();  //추출해온 메시지코드와 메타의 코드를 비교하여 갭분석
	
	List<MessageCodeVo> getGapMessageCodeList();
  
}
