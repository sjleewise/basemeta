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
package kr.wise.dq.profile.mstr.service;

import java.util.ArrayList;
import java.util.Map;

import kr.wise.commons.rqstmst.service.WaqMstr;

/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ColProfileService.java
 * 3. Package  : kr.wise.dq.criinfo.profile.service
 * 4. Comment  : 
 * 5. 작성자   : hwang
 * 6. 작성일   : 2014. 3. 24. 오후 1:31:01
 * </PRE>
 */
public interface ProfileRqstMstrService {
	//프로파일 요청 정보 저장
	public String  register(WaqMstr mstVo, ArrayList<?> reglist)  throws Exception ;
	
	//프로파일 마스터
	public int saveWaqPrfMst(WaqPrfMstrVO record);
	
	//컬럼분석
	public int saveWaqPC01(WaqPrfMstrVO record); 
	
	//유효값 분석
	public int saveWaqPC02(WaqPrfMstrVO record);
	
	//사용자정의 유효값
	public int saveWaqPC02UserDf(WaqPrfMstrVO record);
	
	//프로파일 검증 
	public int check(WaqMstr reqmst, String prfKndCd) ;
	
	//프로파일 저장
	public Map<String, String> approve(WaqMstr mstrVo, String prfKindCd) ;
		
}
