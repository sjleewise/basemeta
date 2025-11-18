package kr.wise.commons.damgmt.db.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaTblSpacMapper {
    int deleteByPrimaryKey(String dbTblSpacId);

    int insert(WaaTblSpac record);

    int insertSelective(WaaTblSpac record);

    WaaTblSpac selectByPrimaryKey(String dbTblSpacId);

    int updateByPrimaryKeySelective(WaaTblSpac record);

    int updateByPrimaryKey(WaaTblSpac record);

	/** @param record yeonho */
	int updateExpDtm(WaaTblSpac record);

	/** @param search
	/** @return yeonho */
	List<WaaTblSpac> getDbTblSpacList(WaaTblSpac search);

	/** @param waaTblSpac
	/** @return yeonho */
	int deleteDbTblSpac(WaaTblSpac record);

	/** @param dbTblSpacPnm
	/** @return yeonho */
	WaaTblSpac selectByPnm(@Param("dbTblSpacPnm") String dbTblSpacPnm, @Param("dbConnTrgId") String dbConnTrgId);
}