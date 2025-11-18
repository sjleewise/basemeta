package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

import kr.wise.meta.stnd.service.SlcGlossaryM;

@Mapper
public interface SlcGlossaryMMapper {
    int deleteByPrimaryKey(String glossaryClasNm);

    int insert(SlcGlossaryM record);

    int insertSelective(SlcGlossaryM record);

    SlcGlossaryM selectByPrimaryKey(String glossaryClasNm);

    int updateByPrimaryKeySelective(SlcGlossaryM record);

    int updateByPrimaryKey(SlcGlossaryM record);

	List<SlcGlossaryM> selectSlcGlossaryList(SlcGlossaryM data);

	int deleteList(ArrayList<SlcGlossaryM> list);

	List selectSlcGlossaryListXls(SlcGlossaryM data);
}