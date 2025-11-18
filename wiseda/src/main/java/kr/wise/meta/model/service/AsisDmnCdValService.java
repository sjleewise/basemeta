/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : AsisDmnCdValService.java
 * 2. Package : kr.wise.meta.model.service
 * 3. Comment :
 * 4. 작성자  :
 * 5. 작성일  : 
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    :            : 신규 개발.
 */
package kr.wise.meta.model.service;

import java.util.List;

import kr.wise.meta.model.service.WamAsisDmnCdVal;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : AsisDmnCdValService.java
 * 3. Package  : kr.wise.meta.model.service
 * 4. Comment  :
 * 5. 작성자   : 
 * 6. 작성일   : 
 * </PRE>
 */
public interface AsisDmnCdValService {
    List<WamAsisDmnCdVal> getAsisDmnCdValList(WamAsisDmnCdVal data);
    
    WamAsisDmnCdVal selectAsisDmnCdValDetail(String dmnId);
    
    //메시지 변경이력 조회
    List<WamAsisDmnCdVal> selectAsisDmnCdValChangeList(String dmnId);
}
