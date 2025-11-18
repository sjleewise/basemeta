package kr.wise.commons.damgmt.db.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaDbSynonymMapper {
    int deleteByPrimaryKey(@Param("synonymId") String synonymId, @Param("expDtm") Date expDtm);

    int insert(WaaDbSynonym record);

    int insertSelective(WaaDbSynonym record);

    WaaDbSynonym selectByPrimaryKey(@Param("synonymId") String synonymId);

    int updateByPrimaryKeySelective(WaaDbSynonym record);

    int updateByPrimaryKey(WaaDbSynonym record);

	/** @param search
	/** @return yeonho */
	List<WaaDbSynonym> getDbSynonymList(WaaDbSynonym search);

	/** @param record yeonho */
	int updateExpDtm(WaaDbSynonym record);

	/** @param record
	/** @return yeonho */
	int deleteDbSynonymAuth(WaaDbSynonym record);
}