package kr.wise.meta.stnd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface AsisColItemMapMapper {
    int insert(AsisColItemMap record);

    int insertSelective(AsisColItemMap record);

	List<AsisColItemMap> selectAsisColvsItem(AsisColItemMap data);

	List<AsisColItemMap> selectAsisColvsItemUseModel(AsisColItemMap data);
}
