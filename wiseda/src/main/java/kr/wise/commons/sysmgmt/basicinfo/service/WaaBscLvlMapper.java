package kr.wise.commons.sysmgmt.basicinfo.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaBscLvlMapper {
    int deleteByPrimaryKey(@Param("bscId") String bscId, @Param("expDtm") Date expDtm);

    int insert(WaaBscLvl record);

    int insertSelective(WaaBscLvl record);

    WaaBscLvl selectByPrimaryKey(@Param("bscId") String bscId);

    int updateByPrimaryKeySelective(WaaBscLvl record);

    int updateByPrimaryKey(WaaBscLvl record);

	/** @param waaBscLvl
	/** @return yeonho */
	int updateExpDtm(WaaBscLvl waaBscLvl);

	/** @param search
	/** @return yeonho */
	List<WaaBscLvl> selectList(WaaBscLvl search);

	int selectBscId(WaaBscLvl waaBscLvl);
	
	int selectBscLvl(String bscId);
}