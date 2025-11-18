package kr.wise.commons.code.service;

import java.util.List;

import kr.wise.commons.cmm.SearchVO;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface ComtccmmncodeMapper {
    int deleteByPrimaryKey(String codeId);

    int insert(Comtccmmncode record);

    int insertSelective(Comtccmmncode record);

    Comtccmmncode selectByPrimaryKey(String codeId);

    int updateByPrimaryKeySelective(Comtccmmncode record);

    int updateByPrimaryKey(Comtccmmncode record);

	List<Comtccmmncode> selectComCodeList(SearchVO searchVO);

	int deleteComCode(String[] ids);
}