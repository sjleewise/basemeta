package kr.wise.meta.ldm.service;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;

@Mapper
public interface WaeLdmAttrMapper extends CommonRqstMapper {   
    int deleteByPrimaryKey(WaeLdmAttr record); 

    int insert(WaeLdmAttr record);

    int insertSelective(WaeLdmAttr record);

    int updateByPrimaryKeySelective(WaeLdmAttr savevo); 
    
    int updateByPrimaryKey(WaeLdmAttr record);
    
    /** @param saveVo insomnia */
	int deleteByrqstSno(WaeLdmAttr saveVo);
	
	
	int insertWaqLdmTblForWae(WaeLdmAttr colVo);

	int insertWaqLdmColForWae(WaeLdmAttr colVo);  

	

	 
}