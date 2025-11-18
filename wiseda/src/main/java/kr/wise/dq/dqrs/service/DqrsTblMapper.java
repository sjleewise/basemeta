package kr.wise.dq.dqrs.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface DqrsTblMapper {
    int deleteByPrimaryKey(int menuNo);


	List<DqrsTbl> selectDqrsTblLst(DqrsTbl searchVO);

	DqrsTbl selectDqrsTblDtl(String menuId);

	int insertDqrsTbl(DqrsTbl saveVO);


//	int deleteDqrsTbl(String menuId);

	int updateExpDtm(DqrsTbl record);
	
	int checkVrfRmk(DqrsTbl saveVo);
	
	int checkDbms(DqrsTbl saveVo);
	
	int updateVrfRmk(DqrsTbl saveVo);
	
	int checkDupDqrsTbl(DqrsTbl saveVo);


	List<DqrsResult> selectDqrsMdlGapLst(DqrsResult search);

	List<DqrsResult> selectDqrsMdlColGapLst(DqrsResult search);

}