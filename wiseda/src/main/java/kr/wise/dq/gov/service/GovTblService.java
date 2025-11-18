package kr.wise.dq.gov.service;

import java.util.ArrayList;
import java.util.List;

public interface GovTblService {

	List<GovTblVO> selectGovTblList(GovTblVO searchVO);

	GovTblVO selectGovTblDetail(String tblId);

	public int saveGovTbl(GovTblVO record) throws Exception ;
	
	public int regGovTbl(ArrayList<GovTblVO> saveVO) throws Exception;

	int deleteGovTbl(ArrayList<GovTblVO> record) throws Exception;

}
