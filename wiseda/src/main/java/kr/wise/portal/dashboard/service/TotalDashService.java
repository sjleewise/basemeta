package kr.wise.portal.dashboard.service;

import java.util.List;

import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.dashboard.service.DbcAllErrChartVO;


public interface TotalDashService {
	
	/* 표준데이터 조회 */
    public List<TotalCountVO> selectTotCntWAMs(String userid) throws Exception;
    /* 데이터모델 조회 */
	public List<UpdateCntVO> selectUpdateCntStat(String userid) throws Exception;
	/* 표준차트 조회(모델 vs DB 일치율) */
	public DbcAllErrChartVO selectErrChart() throws Exception;
	/* 표준차트 조회(데이터 에러율) */
	public List<DbcAllErrChartVO> selectDqErrChartList() throws Exception;
	
	public List<StandardChartVO> selectstandardChart();
	
	public List<StandardChartVO> selectDeptStandardChart();
	
	//메인 추가
	public List<TotalCountVO> selectDbmsCnt();
	public List<TotalCountVO> selectPrfCnt();
	public List<TotalCountVO> selectChartCnt();
	public List<DqMainChartVO> selectPrfCntByDbms(WaaDbConnTrgVO vo);
}
