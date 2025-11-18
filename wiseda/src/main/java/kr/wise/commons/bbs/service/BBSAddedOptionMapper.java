package kr.wise.commons.bbs.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface BBSAddedOptionMapper {
	
	void insertAddedOptionsInf(BoardMaster boardMaster);
	
	BoardMasterVO  selectAddedOptionsInf(BoardMaster master);
	
	void updateAddedOptionsInf(BoardMaster master);
	
}