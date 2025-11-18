package kr.wise.commons.sysmgmt.program.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.cmm.SearchVO;

public interface ProgrmManageService {

	List selectProgrmList(ProgrmManageVO searchVO);

	int selectProgrmListTotCnt(SearchVO searchVO);

	ProgrmManageVO selectProgrmDetail(ProgrmManageVO searchVO);

	int saveProgram(ProgrmManageVO saveVO, String saction);

	int delProgram(ProgrmManageVO saveVO);

	List<ProgrmManageVO> selectProgrmRow(ProgrmManageVO searchVO);

	int regProgramList(ArrayList<ProgrmManageVO> list);

}
