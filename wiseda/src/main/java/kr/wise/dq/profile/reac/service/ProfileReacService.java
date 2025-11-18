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
package kr.wise.dq.profile.reac.service;

import java.util.List;

import kr.wise.dq.profile.mstr.service.WamPrfMstrCommonVO;

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
public interface ProfileReacService {

	public WamPrfReacTblVO getPrfReacDtl(String prfId);

	List<WamPrfReacColVO> getPrfReacRelColLst(String prfId);

	//프로파일 조회 관계분석
	List<WamPrfMstrCommonVO> profileListReacAna(WamPrfMstrCommonVO search);
	
	//모델마트연계 관계정보 조회
	//List<WaqPrfReacColVO> getErMart7ReacLst(WaqPrfReacColVO search);


}
