package kr.wise.meta.dbc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.meta.mta.service.WamMtaExl;

@Mapper
public interface WatDbcTblMapper {
    int deleteByPrimaryKey(@Param("dbSchId") String dbSchId, @Param("dbcTblNm") String dbcTblNm);

    int insert(WatDbcTbl record);

    int insertSelective(WatDbcTbl record);

    WatDbcTbl selectByPrimaryKey(@Param("dbSchId") String dbSchId, @Param("dbcTblNm") String dbcTblNm);

    int updateByPrimaryKeySelective(WatDbcTbl record);

    int updateByPrimaryKey(WatDbcTbl record);

	/** @param search
	/** @return meta */
	List<WatDbcTbl> selectList(WatDbcTbl search);

	/** @param search
	/** @return meta */
	List<WatDbcTbl> selectHistList(WatDbcTbl search);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcTbl> selectDbcTblChangeList(@Param("dbSchId")String dbSchId, @Param("dbcTblNm")String dbcTblNm);
	
	List<WatDbcTbl> selectDbcDpndList(@Param("dbSchId") String dbSchId, @Param("dbcTblNm") String dbcTblNm, @Param("dbConnTrgLnm") String dbConnTrgLnm , @Param("dbSchLnm") String dbSchLnm);

	List<WatDbcTbl> selectWatGapList(WatDbcTbl data);

	List<WamMtaExl> selectMtaExlLst(WamMtaExl data);

	List<WamMtaExl> selectMtaExlRqst(WamMtaExl data);

	void deleteMtaExl(String mtaDgr);

	int insertMtaExl(WamMtaExl record);

	int selectMtaDgr();
}