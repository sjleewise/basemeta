package kr.wise.dq.result.vrfcrule.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.dq.result.service.ResultVO;

@Mapper
public interface VrfcruleResultMapper {
	//값진단종합현황 조회
	List<ResultVO> selectResultListWithRule(ResultVO search);
	
	VrfcruleResultVO selectAnaExecuteStatus(VrfcruleResultVO data);
	
	VrfcruleResultVO selectAnaTargetTableStatus(VrfcruleResultVO data);
	
	List<VrfcruleResultVO> selectValAnaResultList(VrfcruleResultVO data);
	
	List<VrfcruleResultVO> selectAnaTargetTableList(VrfcruleResultVO data);
	
	List<VrfcruleResultVO> selectDmnList(VrfcruleResultVO data);
	
	List<VrfcruleResultVO> selectRefItegrityList(VrfcruleResultVO data);
	
//	List<VrfcruleResultVO> selectReqValCmplList(VrfcruleResultVO data);
	
//	List<VrfcruleResultVO> selectDataRdndList(VrfcruleResultVO data);
	
	List<VrfcruleResultVO> selectBrList(VrfcruleResultVO data);
	
	List<VrfcruleResultVO> selectExcInfoList(VrfcruleResultVO data);
	
	List<VrfcruleResultVO> selectPrfInfo(VrfcruleResultVO data);


	
}
