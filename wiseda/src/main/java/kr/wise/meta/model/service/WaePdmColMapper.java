package kr.wise.meta.model.service;

import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface WaePdmColMapper extends CommonRqstMapper {
    int deleteByPrimaryKey(WaePdmCol record);

    int insert(WaePdmCol record);

    int insertSelective(WaePdmCol record);

    int updateByPrimaryKeySelective(WaePdmCol savevo); 
    
    int updateByPrimaryKey(WaePdmCol record);
    
    /** @param saveVo insomnia */
	int deleteByrqstSno(WaePdmCol saveVo);
	
	
	int insertWaqPdmTblForWae(WaePdmCol colVo);

	int insertWaqPdmColForWae(WaePdmCol colVo);  

	
	int deleteByrqstNo(String rqstNo);
	 
}