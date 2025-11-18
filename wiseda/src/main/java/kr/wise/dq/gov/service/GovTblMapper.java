package kr.wise.dq.gov.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface GovTblMapper {
    int deleteByPrimaryKey(int menuNo);


	List<GovTblVO> selectGovTblList(GovTblVO searchVO);

	GovTblVO selectGovTblDetail(String menuId);

	int insertGovTbl(GovTblVO saveVO);


//	int deleteGovTbl(String menuId);

	int updateExpDtm(GovTblVO record);
	

}