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
package kr.wise.sysinf.codeSync.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.code.service.*;


@Mapper
public interface CodeSyncTgtMapper {

	List<SimpleCodeVo> getTgtSimpleCodeList();	//타겟 공통코드테이블에서 단순코드 추출
	
	int insertSimpleCode(SimpleCodeVo vo);  //단순코드 신규입력
    int updateSimpleCode(SimpleCodeVo vo);  //단순코드 업데이트
    int deleteSimpleCode(SimpleCodeVo vo);  //단순코드 삭제
    
    List<ComplexCodeVo> getTgtComplexCodeList();//타겟 복잡코드 추출
    
	int insertComplexCode(ComplexCodeVo vo);  //복잡코드 신규입력
    int updateComplexCode(ComplexCodeVo vo);  //복잡코드 업데이트
    int deleteComplexCode(ComplexCodeVo vo);  //복잡코드 삭제

}
