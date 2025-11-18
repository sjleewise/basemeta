package kr.wise.commons.damgmt.dmnginfo.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaaDmngMapper {
    
	int insert(WaaDmng record);

	WaaDmng selectByPrimaryKey(String dmngId);

	int updateByPrimaryKeySelective(WaaDmng record);

	int updateByPrimaryKey(WaaDmng record);
	
	int deleteByPrimaryKey(String dmngId);

    int insertSelective(WaaDmng record);
  
    int deleteRegTypCd(WaaDmng record);
    
    List<WaaDmng> selectList(WaaDmng record); 
    
    int deleteExpDtm(WaaDmng record);

	int updateExpDtm(WaaDmng record);

	/** @param dmngId yeonho */
	int updateFullPath(String dmngId);
	
	int updtDmngLvl(String rqstNo);
	
	int updtUppDmngInfo(String rqstNo);

	/** @param dmngLnm
	/** @return yeonho */
	WaaDmng getDmngId(String dmngLnm);

	WaaDmng selectDetail(WaaDmng record);

	List<WaaDmng> selectChkChdDmngList(WaaDmng waaDmng); 
    
}