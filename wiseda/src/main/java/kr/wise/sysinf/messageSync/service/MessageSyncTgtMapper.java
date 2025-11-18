/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CodeSyncTgtMapper.java
 * 2. Package : 
 * 3. Comment :
 * 4. 작성자  : 이상익
 * 5. 작성일  : 2015.10.30
 * 6. 변경이력 : 타겟이 되는 db의 매퍼

 */
package kr.wise.sysinf.messageSync.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;



@Mapper
public interface MessageSyncTgtMapper {

	List<MessageCodeVo> getTgtMessageCodeList();	//타겟 공통코드테이블에서 메시지코드 추출
	int insertMessageCode(MessageCodeVo vo);  //메시지코드 신규입력
	int updateMessageCode(MessageCodeVo vo);  //메시지코드 업데이트
    int deleteMessageCode(MessageCodeVo vo);  //메시지코드 삭제
    
}
