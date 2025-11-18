/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CommonRqstService.java
 * 2. Package : kr.wise.commons.rqstmst.service
 * 3. Comment :
 * 4. 작성자  : insomnia(장명수)
 * 5. 작성일  : 2013. 4. 23. 오후 6:29:18
 * 6. 변경이력 :
 *    이름		: 일자          	: 변경내용
 *    ------------------------------------------------------
 *    insomnia 	: 2013. 4. 23. 		: 신규 개발.
 */
package kr.wise.commons.rqstmst.service;

import java.util.List;

/**
 * <PRE>
 * 1. ClassName : CommonRqstService
 * 2. Package  : kr.wise.commons.rqstmst.service
 * 3. Comment  : 등록요청 공통 인터페이스 정의...
 * 	- 작성완료(저장) : register
 *  - 검증		: check
 *  - 등록요청		: submit
 * 	- 승인 		: approve
 *  -조회 (다건) 	: select + {세부메소드명} + List 예) getTblList
 *  -조회 (단건)  	: select + {세부메소드명} + Info 예) getTblInfo
 *  -체크 		: chk + {세부메소드명} 예) chkTblInfo
 *  -저장			: save + {세부메소드명} 예) saveTblInfo
 * 4. 작성자   : insomnia(장명수)
 * 5. 작성일   : 2013. 4. 23.
 * </PRE>
 */

public interface CommonRqstService {

	/**
	 * <PRE>
	 * 1. MethodName : register
	 * 2. Comment    : 요청서 임시저장 (작성 완료)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 23.
	 * </PRE>
	 *   @return void
	 *   @param vo
	 */
//	public Map<String, String> register(ArrayList<?> reglist, WaqMstr mstVo);

	public int register(WaqMstr mstVo, List<?> reglist) throws Exception;

	/**
	 * <PRE>
	 * 1. MethodName : check
	 * 2. Comment    : 요청서 검증
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 23.
	 * </PRE>
	 *   @return void
	 *   @param vo
	 * @throws Exception
	 */
//	public Map<String, String> check(WaqMstr mstVo);

	public int check(WaqMstr mstVo) throws Exception;

	/**
	 * <PRE>
	 * 1. MethodName : submit
	 * 2. Comment    : 요청서 등록 요청.. (라이트 버전에서는 제외)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 23.
	 * </PRE>
	 *   @return void
	 *   @param vo
	 */
//	public Map<String, String> submit(WaqMstr mstVo);
	public int submit(WaqMstr mstVo) throws Exception;


	/**
	 * <PRE>
	 * 1. MethodName : approve
	 * 2. Comment    : 요청서 승인... (WAQ -> WAM으로 저장)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 23.
	 * </PRE>
	 *   @return void
	 *   @param vo
	 */
//	public Map<String, String> approve(WaqMstr mstVo);

	public int approve(WaqMstr mstVo, List<?> reglist) throws Exception ;


}
