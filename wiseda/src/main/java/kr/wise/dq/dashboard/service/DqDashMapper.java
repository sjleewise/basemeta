package kr.wise.dq.dashboard.service;

import java.util.List;

import kr.wise.portal.dashboard.service.TotalCountVO;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface DqDashMapper {

	List<DqdashSystemVO> getDataDQList() throws Exception;

	List<DqdashSystemVO> getJobTeamDQList() throws Exception;
	
	/* 표준차트 조회(데이터 에러율) */
    List<DbcAllErrChartVO> selectDqErrChartList() throws Exception;
    
    
    /* 기준정보 */
    List<TotalCountVO> selectTotCntDqCri();
    
    /* 업무영역별 진단현황 */
    List<DqdashSystemVO> selectBizAareaImpvList() throws Exception;
    

}
