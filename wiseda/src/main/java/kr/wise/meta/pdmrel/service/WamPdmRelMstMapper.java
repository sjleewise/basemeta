package kr.wise.meta.pdmrel.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

@Mapper
public interface WamPdmRelMstMapper {

	List<WamPdmRelMst> selectWamPdmRelList(WamPdmRelMst search);

	List<WamPdmRelMst> selectWamPdmRelColList(WamPdmRelMst search);    
    
	
	
}