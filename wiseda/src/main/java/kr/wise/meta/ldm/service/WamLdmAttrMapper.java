package kr.wise.meta.ldm.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamLdmAttrMapper {
    int deleteByPrimaryKey(String pdmColId);

    int insert(WamLdmAttr record);

    int insertSelective(WamLdmAttr record);

    WamLdmAttr selectByPrimaryKey(String pdmColId);

    List<WamLdmAttr> selectList(WamLdmAttr search);
    
    List<WamLdmAttr> selectListGap(WamLdmAttr search);

    int updateByPrimaryKeySelective(WamLdmAttr record);

    int updateByPrimaryKey(WamLdmAttr record);

	List<WamLdmAttr> seleccoldtltList(WamLdmAttr search);

	
	/** @param search
	/** @return meta */
	List<WamLdmAttr> selectPdmColList(WamLdmAttr search);
	
	/** @param search
	/** @return lsi */
	List<WamLdmAttr> seleccolhisttListDtl(WamLdmAttr search); 
	
	List<WamLdmAttr> selectPdmColChgList(WamLdmAttr search);
	
	List<WamLdmAttr> selectColNonStndList(WamLdmAttr search);

	List<WamLdmAttr> seleAttrHisttList(WamLdmEnty search); 
	
	
}