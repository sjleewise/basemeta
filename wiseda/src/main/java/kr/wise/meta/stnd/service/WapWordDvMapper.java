package kr.wise.meta.stnd.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WapWordDvMapper {
    int insert(WapWordDv record);

	int insertSelective(WapWordDv record);

	List<WapWordDv> selectWordDivListList(WapWordDv search);

	int delWordAutoDiv(WapWordDv savevo);
}