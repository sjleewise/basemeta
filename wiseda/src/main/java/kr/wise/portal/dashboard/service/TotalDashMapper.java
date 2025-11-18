/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : TotalDashMapper.java
 * 2. Package : kr.wise.portal.dashboard.service
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.12.12.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.12.12. :            : 신규 개발.
 */
package kr.wise.portal.dashboard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;

@Mapper
public interface TotalDashMapper {

	/* dashboard 원천데이터요청현황 건수  */
	List<TotalCountVO> selectSrcDataRqstCnt(String userId);
	
	/* 보유현황 건수  */
	List<TotalCountVO> selectMtaTotalCnt();

	//시스템모니터링 (Collector)
	String selectCollectorStatus(List<CodeListVo> orgCdList);
	//시스템모니터링 (ESB)
	String selectEsbSvrStatus(List<CodeListVo> orgCdList);
	//시스템모니터링(행공센)
	String selectGpucFsvrStatus(List<CodeListVo> orgCdList);
	//시스템모니터링(HDD)

	//나의할일 쿼리
	List<TotalCountVO> selectMyListCnt(@Param("userId")String userId, @Param("orgCd")String orgCd);

	int mergeUsersTotMeta();

	int mergeUsersGapMeta();

	int mergeUsersSrcData();
	
	List<TotalCountVO> selectDbmsCnt();
	List<TotalCountVO> selectPrfCnt();
	List<TotalCountVO> selectChartCnt();

	List<DqMainChartVO> selectPrfCntByDbms(WaaDbConnTrgVO vo);
}
