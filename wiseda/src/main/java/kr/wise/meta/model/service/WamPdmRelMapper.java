package kr.wise.meta.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPdmRelMapper {
    int deleteByPrimaryKey(@Param("pdmRelId") String pdmRelId, @Param("paEntyId") String paEntyId, @Param("paAttrId") String paAttrId, @Param("chEntyId") String chEntyId, @Param("chAttrId") String chAttrId);

    int insert(WamPdmRel record);

    int insertSelective(WamPdmRel record);

    WamPdmRel selectByPrimaryKey(@Param("pdmRelId") String pdmRelId, @Param("paEntyId") String paEntyId, @Param("paAttrId") String paAttrId, @Param("chEntyId") String chEntyId, @Param("chAttrId") String chAttrId);

    int updateByPrimaryKeySelective(WamPdmRel record);

    int updateByPrimaryKey(WamPdmRel record);

	/** @param search
	/** @return yeonho */
	List<WamPdmRel> getPdmRelList(WamPdmRel search);
	
	/** @param search
	/** @return yeonho */
	List<WamPdmRel> getPdmRelHistList(WamPdmRel search);

	/** @param search
	/** @return yeonho */
	List<WamPdmRel> getPdmRelListHistPage(WamPdmRel search);

	/** @param search
	/** @return yeonho */
	List<WamPdmRel> getPdmRelHistListHistPage(WamPdmRel search);
}