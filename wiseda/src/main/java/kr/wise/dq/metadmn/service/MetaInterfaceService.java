/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : ColProfileService.java
 * 2. Package : kr.wise.dq.criinfo.profile.service
 * 3. Comment : 
 * 4. 작성자  : hwang
 * 5. 작성일  : 2014. 3. 24. 오후 1:31:01
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    hwang : 2014. 3. 24. :            : 신규 개발.
 */
package kr.wise.dq.metadmn.service;

import java.util.List;

public interface MetaInterfaceService {
	//도메인 조회
	MetaDmnItfVO getMetaDmnDtl(MetaDmnItfVO searchVO);
	
	//코드도메인 유효값 조회
	List<MetaDmnCdValItfVO> getMetaDmnCdValLst(String dmnId);
	
}
