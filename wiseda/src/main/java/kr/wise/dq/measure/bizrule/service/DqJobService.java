package kr.wise.dq.measure.bizrule.service;

import java.util.List;
import java.util.Map;

public interface DqJobService {

	List<DqJobVO> selectDqJobList(DqJobVO search) throws Exception;

	List<DqJobVO> selectDbmsEnmCode() throws Exception;

	List<DqJobVO> selectErrStatusImpStatusCode() throws Exception;

	//int selectDqJobTotCnt(Map<String, Object> param) throws Exception;

}
