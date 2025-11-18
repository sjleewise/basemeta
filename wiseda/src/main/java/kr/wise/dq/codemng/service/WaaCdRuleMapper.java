package kr.wise.dq.codemng.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.dq.bizrule.service.WamBrMstr;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

@Mapper
public interface WaaCdRuleMapper {

	WaaCdRule selectByPrimaryKey(WaaCdRule search);  

	int insertSelective(WaaCdRule record);

	int updateByPrimaryKeySelective(WaaCdRule record);

	int deleteByPrimaryKey(WaaCdRule record);
	
	List<WaaCdRule> selectCodeMngList(WaaCdRule search); 
}

