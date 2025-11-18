package kr.wise.meta.codecfcsys.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamCodeCfcSysMapper {
    int deleteByPrimaryKey(String codeCfcSysId);

    int insert(WamCodeCfcSys record);

    int insertSelective(WamCodeCfcSys record);

    WamCodeCfcSys selectByPrimaryKey(String codeCfcSysId);

    int updateByPrimaryKeySelective(WamCodeCfcSys record);

    int updateByPrimaryKey(WamCodeCfcSys record);

	/** @param search
	/** @return meta */
	List<WamCodeCfcSys> selectList(WamCodeCfcSys search);

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSys> selectHistList(WaqCodeCfcSys search);
}