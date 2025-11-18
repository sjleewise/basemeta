package kr.wise.meta.stnd.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

@Mapper
public interface WaqSditmEngMapper {
    

	/** @param search
	/** @return insomnia */
	List<WaqSditm> selectItemEngRqstListbyMst(WaqMstr search);

	//인포타입검증 
	int checkEngInfoType(Map<String, Object> checkmap);
	
}