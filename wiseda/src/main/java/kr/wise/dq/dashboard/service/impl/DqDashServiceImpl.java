package kr.wise.dq.dashboard.service.impl;

import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.dashboard.service.DqDashMapper;
import kr.wise.dq.dashboard.service.DqDashService;
import kr.wise.dq.dashboard.service.DqdashSystemVO;
import kr.wise.dq.dashboard.service.PieChartMapper;
import kr.wise.dq.dashboard.service.PieChartVO;
import kr.wise.dq.dashboard.service.WidgetsChartVO;
import kr.wise.dq.measure.profile.service.WdqPrfMstMapper;
import kr.wise.portal.dashboard.service.TotalCountVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service ("dqdashService")
public class DqDashServiceImpl implements DqDashService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Inject
	private DqDashMapper dqDashMapper;
	
	@Inject
	private WdqPrfMstMapper wdqPrfMstMapper;
	
	@Inject
	private PieChartMapper pieChartMapper;
	
	
	//DQ DB 연결 파워차트호출
	public List<WidgetsChartVO> selectWidgetsChart() throws Exception {
		
		return wdqPrfMstMapper.selectWidgetsChart();
	}
	//DQ DB 연결 파이차트호출
	public List<PieChartVO> selectPieChart()throws Exception {
		
		return pieChartMapper.selectPieChart();
	}

	public List<DqdashSystemVO> getDataDQList() throws Exception{
		
		return dqDashMapper.getDataDQList();
	}

	public List<DqdashSystemVO> getJobTeamDQList() throws Exception{
		
		return dqDashMapper.getJobTeamDQList();
	}
	
	
	/* DQ 메인 기준정보 조회 */
	public List<TotalCountVO> getTotCntDqCri() throws Exception { 
		return 	 dqDashMapper.selectTotCntDqCri();
	}
	
	public List<DqdashSystemVO> getBizAareaImpvList() throws Exception{
		return 	 dqDashMapper.selectBizAareaImpvList();
	}
	
}
