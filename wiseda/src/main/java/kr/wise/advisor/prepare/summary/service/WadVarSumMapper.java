package kr.wise.advisor.prepare.summary.service;

import kr.wise.advisor.prepare.summary.service.WadVarSum;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadVarSumMapper {
    int deleteByPrimaryKey(String anlVarId);

    int insert(WadVarSum record);

    int insertSelective(WadVarSum record);

    WadVarSum selectByPrimaryKey(String anlVarId);

    int updateByPrimaryKeySelective(WadVarSum record);

    int updateByPrimaryKey(WadVarSum record);
}