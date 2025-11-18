package kr.wise.advisor.prepare.textcluster.service;

import java.util.List;

import kr.wise.advisor.prepare.textcluster.service.WadDataMtcTbl;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WadDataMtcTblMapper {
    int deleteByPrimaryKey(String mtcId);

    int insert(WadDataMtcTbl record);

    int insertSelective(WadDataMtcTbl record);

    WadDataMtcTbl selectByPrimaryKey(String mtcId);

    int updateByPrimaryKeySelective(WadDataMtcTbl record);

    int updateByPrimaryKey(WadDataMtcTbl record);

	/** @param search
	/** @return insomnia */
	List<WadDataMtcTbl> selectList(WadDataMtcTbl search);

	List<WadDataMtcTbl> tblList(WadDataMtcTbl search);

	List<WadDataMtcTbl> hiveTblList(WadDataMtcTbl search);
}