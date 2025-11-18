package kr.wise.meta.ddl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;
@Mapper
public interface WamDdlRelMapper {
    int deleteByPrimaryKey(@Param("ddlRelId") String ddlRelId, @Param("paEntyId") String paEntyId, @Param("paAttrId") String paAttrId, @Param("chEntyId") String chEntyId, @Param("chAttrId") String chAttrId);

    int insert(WamDdlRel record);

    int insertSelective(WamDdlRel record);

    WamDdlRel selectByPrimaryKey(@Param("ddlRelId") String ddlRelId, @Param("paEntyId") String paEntyId, @Param("paAttrId") String paAttrId, @Param("chEntyId") String chEntyId, @Param("chAttrId") String chAttrId);

    int updateByPrimaryKeySelective(WamDdlRel record);

    int updateByPrimaryKey(WamDdlRel record);

	/** @param searchVO
	/** @return meta */
	List<WamDdlRel> selectDdlRelList(WamDdlTbl searchVO);

	/** @param ddlTblId
	/** @return meta */
	List<WamDdlRel> selectDdlRelChangeList(String ddlTblId);

	/** @param searchVO
	/** @return meta */
	List<WamDdlRel> selectDdlRelHistList(WamDdlTbl searchVO);
}