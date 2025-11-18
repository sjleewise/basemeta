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
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.helper.grid.IBSheetListVO;
import kr.wise.commons.rqstmst.service.CommonRqstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.WaqPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WaqPrfRngAnaVO;
import kr.wise.dq.profile.tblrel.service.WaqPrfRelColVO;
import kr.wise.dq.profile.tblunq.service.WaqPrfUnqColVO;

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
public interface ProfileExcelRqstMstrService extends CommonRqstService {
	
	String getPrfKndCd(String rqstNo);
	
	//컬럼분석 검증 조회
	List<WaqPrfColAnaVO> getPrfExlPc01Lst( WaqMstr search);
	
	//유효값분석 검증 조회
    List<WaqPrfEfvaUserDfVO> getPrfExlPc02Lst(WaqMstr search);
	
	//날짜분석 검증 조회
	List<WaqPrfDtfrmAnaVO> getPrfExlPc03Lst( WaqMstr search);
	
	//범위분석 검증 조회
	List<WaqPrfRngAnaVO> getPrfExlPc04Lst( WaqMstr search);
	
	//유효패턴분석 검증 조회
	List<WaqPrfPtrAnaVO> getPrfExlPc05Lst( WaqMstr search);
	
	//관계분석 검증 조회
	List<WaqPrfRelColVO> getPrfExlPt01Lst( WaqMstr search);
	
	//중복분석 검증 조회
	List<WaqPrfUnqColVO> getPrfExlPt02Lst( WaqMstr search);
	
	//프로파일 엑셀 리스트 삭제
	int delPrfExlLst(WaqMstr reqmst, String checkjoin);

	//메타연동 조회
	List<WaqPrfRngAnaVO> getPrfMetaSrchPc04Lst(WaqMstr search);

	List<WaqPrfDtfrmAnaVO> getPrfMetaSrchPc03Lst(WaqMstr search);

	List<WaqPrfEfvaUserDfVO> getPrfMetaSrchPc02Lst(WaqMstr search);

	List<WaqPrfRelColVO> getPrfMetaSrchPt01Lst(WaqMstr search);

	List<WaqPrfEfvaUserDfVO> getPrfWatPc05Lst(String dbConnTrgId, String dbSchId, String dbcColNm, String paramVal);

	List<WaqPrfEfvaUserDfVO> getPrfWatPc02Lst(String dbConnTrgId, String dbSchId, String dbcColNm, String paramVal);

	List<WaqPrfDtfrmAnaVO> getPrfWatPc03Lst(String dbConnTrgId, String dbSchId, String dbcColNm, String paramVal); 
	
	int regWaqRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo) throws Exception;

}
