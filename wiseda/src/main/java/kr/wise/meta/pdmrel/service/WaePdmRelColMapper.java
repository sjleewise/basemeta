package kr.wise.meta.pdmrel.service;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

@Mapper
public interface WaePdmRelColMapper extends CommonRqstMapper{

	int insertSelective(WaePdmRelCol savevo);

	int updateByPrimaryKeySelective(WaePdmRelCol savevo);

	int deleteByPrimaryKey(WaePdmRelCol savevo);

	int insertWaqPdmRelForWae(WaePdmRelCol colVo);

	int insertWaqPdmRelColForWae(WaePdmRelCol colVo);

	int deleteByRqstNo(WaqMstr reqmst); 

	
	
}