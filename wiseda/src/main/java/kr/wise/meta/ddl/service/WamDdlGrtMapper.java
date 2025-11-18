package kr.wise.meta.ddl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamDdlGrtMapper {
    int deleteByPrimaryKey(String ddlGrtId);

    int insert(WamDdlGrt record);

    int insertSelective(WamDdlGrt record);

    WamDdlGrt selectByPrimaryKey(String ddlGrtId);

    int updateByPrimaryKeySelective(WamDdlGrt record);

    int updateByPrimaryKeyWithBLOBs(WamDdlGrt record);

    int updateByPrimaryKey(WamDdlGrt record);

	/** @param search
	/** @return yeonho */
	List<WamDdlGrt> selectList(WamDdlGrt search);

	/** @param ddlGrtId
	/** @return yeonho */
	WamDdlGrt selectByddlGrtId(@Param("ddlGrtId")String ddlGrtId, @Param("rqstNo")String rqstNo);

	/** @param search
	/** @return yeonho */
	List<WamDdlGrt> selectChangeList(WamDdlGrt search);

	/** @param record
	/** @return yeonho */
	int updateDdlGrtRqstPrc(WamDdlTbl record);

	/** @param search
	/** @return yeonho */
	List<WamDdlGrt> selectDdlObjHistList(WamDdlGrt search);

	/** @param search
	/** @return yeonho */
	WamDdlGrt selectDdlObjHistInfo(WamDdlGrt search);

	/** @param search
	/** @return yeonho */
	List<WamDdlGrt> selectObjChangeHistList(WamDdlGrt search);

	WamDdlGrt selectByPrimaryKeyRqstList(String ddlGrtId, String rqstNo);

	WamDdlGrt selectByRqstNo(WamDdlGrt grt);

}