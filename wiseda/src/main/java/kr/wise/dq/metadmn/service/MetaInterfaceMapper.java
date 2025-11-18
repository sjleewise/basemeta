package kr.wise.dq.metadmn.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface MetaInterfaceMapper {
	//도메인 조회
	MetaDmnItfVO selectMetaDmnDtl(MetaDmnItfVO searchVO);
	
	//코드도메인 유효값 조회
	List<MetaDmnCdValItfVO> selectMetaDmnCdValLst(String dmnId);
    
}