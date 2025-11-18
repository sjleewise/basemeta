package kr.wise.dq.dashboard.service;

import java.util.List;

import kr.wise.portal.dashboard.service.TotalCountVO;

public interface DqDashService {

	List<WidgetsChartVO> selectWidgetsChart() throws Exception;
	
	List<PieChartVO> selectPieChart() throws Exception;

	List<DqdashSystemVO> getDataDQList() throws Exception;

	List<DqdashSystemVO> getJobTeamDQList() throws Exception;

	/* 기준정보 조회 */
	List<TotalCountVO> getTotCntDqCri() throws Exception;
	
	/* 기준정보 조회 */
	List<DqdashSystemVO> getBizAareaImpvList() throws Exception;

}
