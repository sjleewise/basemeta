package kr.wise.commons.damgmt.approve.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaaAprgMapper {

	int deleteAll();

	int deleteByPrimaryKey(String AprgId);

    int insert(WaaAprg record);

    int insertSelective(WaaAprg record);

    WaaAprg selectByPrimaryKey(String AprgId);

    int updateByPrimaryKeySelective(WaaAprg record);

    int updateByPrimaryKey(WaaAprg record);

    int deleteRegTypCd(WaaAprg record);

    List<WaaAprg> selectList(WaaAprg record);

	int updateExpDtm(WaaAprg record);

	int selectAprgCnt(WaaAprg waaAprg);

}