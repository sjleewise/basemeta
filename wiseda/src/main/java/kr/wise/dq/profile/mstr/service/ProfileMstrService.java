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
import java.util.List;

import org.apache.ibatis.annotations.Param;



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
public interface ProfileMstrService {
	
	//프로파일 등록
	public int  register(ArrayList<?> reglist)  throws Exception ;
	
	//프로파일 마스터 등록
	public int saveWamPrfMst(WamPrfMstrVO record) throws Exception ;
	
	//프로파일 마스터 조회
	public WamPrfMstrCommonVO  getPrfMstrByPrfId(String prfId, String prfKndCd) ;
	
	//컬럼분석 등록
	public int saveWamPC01(WamPrfMstrVO record);
	
	//유효값분석 등록
	public int saveWamPC02(WamPrfMstrVO record);
	
	//사용자 정의 유효값
	public int saveWamPC02UserDf(WamPrfMstrVO record);
	
	//날짜형식분석 등록
	public int saveWamPC03(WamPrfMstrVO record);
	
	//범위분석 등록
	public int saveWamPC04(WamPrfMstrVO record);
	
	//패턴분석 등록
	public int saveWamPC05UserDf(WamPrfMstrVO record);
	
	//중복분석 등록
	public int saveWamPT02(WamPrfMstrVO record);
	
    //프로파일 SQL 생성기 DB타입, 프로파일종류 조회
    public WamPrfMstrVO getSqlGenDbmsInfoByPrfId(String prfId);

	public int saveErrCntSql(WamPrfMstrVO saveVo);
	
}
