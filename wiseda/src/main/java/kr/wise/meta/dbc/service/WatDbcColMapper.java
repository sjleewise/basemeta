package kr.wise.meta.dbc.service;

import java.util.List;

import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WatDbcColMapper {
    int deleteByPrimaryKey(@Param("dbSchId") String dbSchId, @Param("dbcTblNm") String dbcTblNm, @Param("dbcColNm") String dbcColNm);

    int insert(WatDbcCol record);

    int insertSelective(WatDbcCol record);

    WatDbcCol selectByPrimaryKey(@Param("dbSchId") String dbSchId, @Param("dbcTblNm") String dbcTblNm, @Param("dbcColNm") String dbcColNm);

    int updateByPrimaryKeySelective(WatDbcCol record);

    int updateByPrimaryKey(WatDbcCol record);

	/** @param dbSchId
	/** @param dbcTblNm meta 
	 * @return */
	List<WatDbcCol> selectList(@Param("dbSchId") String dbSchId, @Param("dbcTblNm") String dbcTblNm);

	List<WaqPrfColAnaVO> getDbcColList(WaqPrfColAnaVO record);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcCol> selectListNonColor(@Param("dbSchId")String dbSchId, @Param("dbcTblNm")String dbcTblNm);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcCol> selectDbcColChangeList(@Param("dbSchId")String dbSchId, @Param("dbcTblNm")String dbcTblNm);

	
}