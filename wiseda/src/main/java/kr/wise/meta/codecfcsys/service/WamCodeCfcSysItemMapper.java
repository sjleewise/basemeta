package kr.wise.meta.codecfcsys.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamCodeCfcSysItemMapper {
    int deleteByPrimaryKey(String codeCfcSysId);

    int insert(WamCodeCfcSysItem record);

    int insertSelective(WamCodeCfcSysItem record);

    WamCodeCfcSysItem selectByPrimaryKey(String codeCfcSysId);

    int updateByPrimaryKeySelective(WamCodeCfcSysItem record);

    int updateByPrimaryKey(WamCodeCfcSysItem record);

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSysItem> selectItemList(WaqCodeCfcSysItem search);

	/** @param search
	/** @return meta */
	List<WaqCodeCfcSysItem> selectHistList(WaqCodeCfcSysItem search);
}