package kr.wise.dq.criinfo.anatrg.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.damgmt.schedule.service.WamShdJob;
import kr.wise.dq.vrfcrule.service.VrfcruleVO;

@Mapper
public interface WaaColRuleRelMapper { 

	List<WaaColRuleRel> selectList(WaaColRuleRel search);
	
	List<WaaColRuleRel> selectconntrglist(WaaColRuleRel search); 

	List<WaaColRuleRel> selectListByFK(String dbConnTrgId);

	WaaColRuleRel selectByPrimaryKey(String dbSchId);

	int updateExpDtm(String dbSchId);

	int insertSelective(WaaColRuleRel record); 

	int updateByPrimaryKeySelective(WaaColRuleRel saveVo);

	List<WaaColRuleRel> selectTrgTbl(WaaColRuleRel vo); 

	List<WaaColRuleRel> selectCheckRuleTbl(WaaExpTbl vo);

	List<WamShdJob> selectDqliteJobPopList(WamShdJob search);

	int deleteByPrimaryKeySelective(WaaColRuleRel delVo);

	int deleteShdJobByPrimaryKeySelective(WaaColRuleRel delVo);

	int deleteVrfcNull();
	
}

