package kr.wise.dq.result.service;

import java.math.BigDecimal;
import java.util.List;

public interface ResultService {

	List<ResultVO> getResultList(ResultVO search);

	ResultVO getResultTerm(ResultVO search);

	List<ResultVO> getTblList(ResultVO search);

	List<ResultVO> getDmnRule(ResultVO search);

	List<ResultVO> getExecList(ResultVO search);

	List<ResultVO> getErrList(ResultVO search);

	BigDecimal getTotCnt(ResultVO search);

	BigDecimal getRunCnt(ResultVO search);

	List<ResultDataVO> getErrDataMaxColCnt(ResultVO search);

	List<ResultDataVO> getErrListByDqi(ResultVO search);

	List<ResultDataVO> getErrDataByDqi(ResultVO search);

	String[] getDqiLnmLst();

	ResultVO getStndTerm(ResultVO search);

	ResultVO getModelTerm(ResultVO search);
	
	public List<ResultVO> getSditmTblList(ResultVO search);
	
	public List<ResultVO> getSditmList(ResultVO search);
	
	public List<ResultVO> getDmnList(ResultVO search);
	
	ResultVO getModelStatus(ResultVO search);

	List<MultiDimVO> getMultiDimLst(MultiDimVO search);

	List<MultiDimVO> getMultiDimLstTop5();

}
