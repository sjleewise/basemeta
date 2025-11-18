package kr.wise.sysinf.prism.service;

import kr.wise.sysinf.prism.service.PrismResearchDtl;

public interface PrismResearchDtlMapper {
    int deleteByPrimaryKey(String researchId);

    int insert(PrismResearchDtl record);

    int insertSelective(PrismResearchDtl record);

    PrismResearchDtl selectByPrimaryKey(String researchId);

    int updateByPrimaryKeySelective(PrismResearchDtl record);

    int updateByPrimaryKeyWithBLOBs(PrismResearchDtl record);

    int updateByPrimaryKey(PrismResearchDtl record);

	int insertResearchDetail(ResearchDetail vo);
}