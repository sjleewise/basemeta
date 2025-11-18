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
package kr.wise.commons.code.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;



@Mapper
public interface CodeSyncSrcMapper {

	int insertSimpleCodeToMeta(SimpleCodeVo vo);	//타겟 공통코드테이블에서 단순코드 추출하여 메타에 입력
	
    int simpleCodeGapExec();  //추출해온 코드와 메타의 코드를 비교하여 갭분석
    
    
    int deleteWatGapSmpCd();  //단순코드 gap테이블 삭제
    int deleteWatSmpCd();     //단순코드 임시테이블 삭제
    
    List<SimpleCodeVo> getGapSimpleCodeList();
    
    int deleteWatGapCmpCd();  //복잡코드 gap테이블 삭제
    int deleteWatCmpCd();     //단순코드 임시테이블 삭제
    
    int insertComplexCodeToMeta(ComplexCodeVo vo);  //추출한 복잡코드를 메타에 입력
    int complexCodeGapExec();  //추출해온 코드와 메타의 코드를 비교하여 갭분석
    
    List<ComplexCodeVo> getGapComplexCodeList();
}
