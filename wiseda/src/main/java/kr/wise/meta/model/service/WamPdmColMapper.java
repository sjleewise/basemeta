package kr.wise.meta.model.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WamPdmColMapper {
    int deleteByPrimaryKey(String pdmColId);

    int insert(WamPdmCol record);

    int insertSelective(WamPdmCol record);

    WamPdmCol selectByPrimaryKey(String pdmColId);

    List<WamPdmCol> selectList(WamPdmCol search);
    
    List<WamPdmCol> selectListGap(WamPdmCol search);

    int updateByPrimaryKeySelective(WamPdmCol record);

    int updateByPrimaryKey(WamPdmCol record);

	List<WamPdmCol> seleccoldtltList(WamPdmCol search);

	List<WamPdmCol> seleccolhisttList(WamPdmTbl search);

	/** @param search
	/** @return yeonho */
	List<WamPdmCol> selectPdmColList(WamPdmCol search);
	
	/** @param search
	/** @return lsi */
	List<WamPdmCol> seleccolhisttListDtl(WamPdmCol search);
	
	List<WamPdmCol> selectPdmColChgList(WamPdmCol search);
	
	List<WamPdmCol> selectColNonStndList(WamPdmCol search);
	
	
}