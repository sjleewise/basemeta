package kr.wise.commons.sysmgmt.program.service;

import java.util.List;

import kr.wise.commons.cmm.SearchVO;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface ProgrmManageMapper {

	String selectProgrmUrl(String progrmNm);
	
	List selectProgrmList(ProgrmManageVO searchVO);

	int selectProgrmListTotCnt(SearchVO searchVO);

	ProgrmManageVO selectProgrmDetail(ProgrmManageVO searchVO);

	int insertProgram(ProgrmManageVO saveVO);

	int updateProgram(ProgrmManageVO saveVO);

	int deleteProgram(String[] saveVO);

	int deleteProgramVo(ProgrmManageVO vo);

}
