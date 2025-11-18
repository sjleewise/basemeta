package kr.wise.sysinf.prism.service;

import java.util.List;

import kr.wise.rest.client.ResearchVo;

public interface PrismResearchMstMapper {
    int deleteByPrimaryKey(String researchId);

    int insert(PrismResearchMst record);
    
    int insertResearch(ResearchVo record);

    int insertSelective(PrismResearchMst record);

    PrismResearchMst selectByPrimaryKey(String researchId);
    List<PrismResearchMst> selectByOrgan(String researchId);

    int updateByPrimaryKeySelective(PrismResearchMst record);

    int updateByPrimaryKey(PrismResearchMst record);

	List<PrismResearchMst> selectResearchbyOrgan(String organName);

}