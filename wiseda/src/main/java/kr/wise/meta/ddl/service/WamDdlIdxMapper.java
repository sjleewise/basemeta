package kr.wise.meta.ddl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamDdlIdxMapper {
    int deleteByPrimaryKey(String ddlIdxId);

    int insert(WamDdlIdx record);

    int insertSelective(WamDdlIdx record);

    WamDdlIdx selectByPrimaryKey(String ddlIdxId);

    int updateByPrimaryKeySelective(WamDdlIdx record);

    int updateByPrimaryKeyWithBLOBs(WamDdlIdx record);

    int updateByPrimaryKey(WamDdlIdx record);

	/** @param search
	/** @return meta */
	List<WamDdlIdx> selectList(WamDdlIdx search);

	/** @param ddlIdxId
	/** @return meta */
	WamDdlIdx selectByDdlIdxId(@Param("ddlIdxId")String ddlIdxId, @Param("rqstNo")String rqstNo);

	/** @param search
	/** @return meta */
	List<WamDdlIdx> selectChangeList(WamDdlIdx search);

	/** @param record
	/** @return meta */
	int updateDdlIdxRqstPrc(WamDdlTbl record);

	/** @param search
	/** @return meta */
	List<WamDdlIdx> selectDdlIdxHistList(WamDdlIdx search);

	/** @param search
	/** @return meta */
	WamDdlIdx selectDdlIdxHistInfo(WamDdlIdx search);

	/** @param search
	/** @return meta */
	List<WamDdlIdx> selectIdxChangeHistList(WamDdlIdx search);

}