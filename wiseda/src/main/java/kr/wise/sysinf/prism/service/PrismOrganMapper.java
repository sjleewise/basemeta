package kr.wise.sysinf.prism.service;

import java.util.List;

public interface PrismOrganMapper {
    int deleteByPrimaryKey(String organId);

    int insert(PrismOrgan record);

    int insertSelective(PrismOrgan record);

    PrismOrgan selectByPrimaryKey(String organId);
    
    List<PrismOrgan> selectList();
    List<PrismOrgan> selectOrganResList();

    int updateByPrimaryKeySelective(PrismOrgan record);

    int updateByPrimaryKey(PrismOrgan record);

	List<PrismOrgan> selectOrganListbyNew();
	List<PrismOrgan> selectOrganListbyNewReport();
}