package kr.wise.meta.result.service;

import java.math.BigDecimal;
import java.util.List;

public interface MtResultService {

	List<MtResultVO> getResultList(MtResultVO search);

	MtResultVO getResultTerm(MtResultVO search);

	List<MtResultVO> getTblList(MtResultVO search);

	List<MtResultVO> getDmnRule(MtResultVO search);

	List<MtResultVO> getExecList(MtResultVO search);

	List<MtResultVO> getErrList(MtResultVO search);

	BigDecimal getTotCnt(MtResultVO search);

	BigDecimal getRunCnt(MtResultVO search);

	List<MtResultDataVO> getErrDataMaxColCnt(MtResultVO search);

	List<MtResultDataVO> getErrListByDqi(MtResultVO search);

	List<MtResultDataVO> getErrDataByDqi(MtResultVO search);

	String[] getDqiLnmLst();

	MtResultVO getStndTerm(MtResultVO search);
	
	MtResultVO getCnt(MtResultVO search);
	
	MtResultVO getGovCnt(MtResultVO search);

	MtResultVO getModelTerm(MtResultVO search);
	
	public List<MtResultVO> getSditmTblList(MtResultVO search);
	
	public List<MtResultVO> getSditmList(MtResultVO search);
	
	public List<MtResultVO> getDmnList(MtResultVO search);
	
	public List<MtResultVO> getGovSditmTblList(MtResultVO search);
	
	public List<MtResultVO> getGovSditmList(MtResultVO search);
	
	public List<MtResultVO> getGovDmnList(MtResultVO search);
	
	List<MtResultVO> getModelStatus(MtResultVO search);

//	List<MtResultVO> getGovModelStatus(MtResultVO search);

	List<MtResultVO> getStructResultList(MtResultVO search);
	
	List<MtResultVO> getDbcTblList(MtResultVO search);
	
	List<MtResultVO> getDbcColList(MtResultVO search);
	
	List<MtResultVO> getPdmTblList(MtResultVO search);
	
	List<MtResultVO> getPdmColList(MtResultVO search);
	
	List<MtResultVO> getGovStructResultList(MtResultVO search);
	
	List<MtResultVO> getGovDbcTblList(MtResultVO search);
	
	List<MtResultVO> getGovDbcColList(MtResultVO search);
	
	List<MtResultVO> getGovTblList(MtResultVO search);
	
	List<MtResultVO> getGovColList(MtResultVO search);
	
	String getDbConnTrgURL(MtResultVO search);
	
	List<MtResultVO> getTblDdList(MtResultVO search);
	
	List<MtResultVO> getColDdList(MtResultVO search);

	List<MtResultVO> getGovTblDdList(MtResultVO search);
	
	List<MtResultVO> getGovColDdList(MtResultVO search);
}
