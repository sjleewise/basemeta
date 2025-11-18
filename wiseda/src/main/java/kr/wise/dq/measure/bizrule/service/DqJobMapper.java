package kr.wise.dq.measure.bizrule.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface DqJobMapper {

	List<DqJobVO> selectDqJobList(DqJobVO search) throws Exception;

	List<DqJobVO> selectDbmsEnmCode()throws Exception;

	List<DqJobVO> selectErrStatusImpStatusCode()throws Exception;

	

}
