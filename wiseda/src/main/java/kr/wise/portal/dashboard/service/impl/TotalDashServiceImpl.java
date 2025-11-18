package kr.wise.portal.dashboard.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.dq.dashboard.service.DbcAllErrChartVO;
import kr.wise.dq.dashboard.service.DqDashMapper;
import kr.wise.meta.model.service.WamPdmTblMapper;
import kr.wise.portal.dashboard.service.DqMainChartVO;
import kr.wise.portal.dashboard.service.StandardChartVO;
import kr.wise.portal.dashboard.service.TotalCountVO;
import kr.wise.portal.dashboard.service.TotalDashMapper;
import kr.wise.portal.dashboard.service.TotalDashService;
import kr.wise.portal.dashboard.service.UpdateCntVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service ("totdashService")
public class TotalDashServiceImpl implements TotalDashService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private TotalDashMapper totalDashMapper;
	
	@Inject
	private WamPdmTblMapper totmapper;

	@Inject
	private DqDashMapper dqDashMapper;

	/* 표준데이터 조회 */
	public List<TotalCountVO> selectTotCntWAMs(String userid) throws Exception {
		return 	 totmapper.selectPdmTblCount(userid);
	}
	/* 데이터모델 조회 */
	public List<UpdateCntVO> selectUpdateCntStat(String userid) throws Exception {

		return totmapper.selectUpdateCountStat(userid);
	}
	/* 표준차트 조회(모델 vs DB 일치율) */
	public DbcAllErrChartVO selectErrChart() throws Exception{

		return totmapper.selectErrChart();
	}

	/* 표준차트 조회(데이터 에러율) */
	public List<DbcAllErrChartVO> selectDqErrChartList() throws Exception {
		return dqDashMapper.selectDqErrChartList();
	}


	
	/** yeonho */
	@Override
	public List<StandardChartVO> selectDeptStandardChart() {
		return totmapper.selectDeptStandardChart();
	}
	@Override
	public List<StandardChartVO> selectstandardChart() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<TotalCountVO> selectDbmsCnt(){
		return totalDashMapper.selectDbmsCnt();
	}
	
	@Override
	public List<TotalCountVO> selectPrfCnt(){
		return totalDashMapper.selectPrfCnt();
	}
	@Override
	public List<TotalCountVO> selectChartCnt(){
		return totalDashMapper.selectChartCnt();
	}

	@Override
	public List<DqMainChartVO> selectPrfCntByDbms(WaaDbConnTrgVO vo) {
		// TODO Auto-generated method stub
		return totalDashMapper.selectPrfCntByDbms(vo);
	}
}
