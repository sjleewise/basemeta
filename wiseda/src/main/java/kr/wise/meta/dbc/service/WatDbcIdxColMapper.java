package kr.wise.meta.dbc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WatDbcIdxColMapper {
    int insert(WatDbcIdxCol record);

    int insertSelective(WatDbcIdxCol record);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcIdxCol> selectList(@Param("dbSchId") String dbSchId, @Param("dbcTblNm") String dbcTblNm);

	/** @param dbSchId
	/** @param dbcTblNm
	/** @return meta */
	List<WatDbcIdxCol> selectDbcIdxColChangeList(@Param("dbSchId")String dbSchId, @Param("dbcTblNm")String dbcTblNm);
}