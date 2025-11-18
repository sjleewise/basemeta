package kr.wise.dq.result.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.dq.result.service.MultiDimVO;
import kr.wise.dq.result.service.ResultDataVO;
import kr.wise.dq.result.service.ResultMapper;
import kr.wise.dq.result.service.ResultService;
import kr.wise.dq.result.service.ResultVO;

import org.springframework.stereotype.Service;

@Service("ResultService")
public class ResultServiceImpl implements ResultService {

	@Inject
	ResultMapper resultMapper;
	

	@Override
	public List<ResultVO> getResultList(ResultVO search) {
		return resultMapper.selectResultList(search);
	}


	@Override
	public ResultVO getResultTerm(ResultVO search) {
		
		return resultMapper.selectDtm(search);
	}
	
	@Override
	public List<ResultVO> getTblList(ResultVO search) {
		return resultMapper.selectTblList(search);
	}
	
	@Override
	public List<ResultVO> getDmnRule(ResultVO search) {
		return resultMapper.selectDmnRule(search);
	}
	
	@Override
	public List<ResultVO> getExecList(ResultVO search) {
		return resultMapper.selectExecList(search);
	}
	
	@Override
	public List<ResultVO> getErrList(ResultVO search) {
		return resultMapper.selectErrList(search);
	}


	@Override
	public BigDecimal getTotCnt(ResultVO search) {
		
		return resultMapper.selectTotCnt(search);
	}


	@Override
	public BigDecimal getRunCnt(ResultVO search) {
		
		return resultMapper.selectRunCnt(search);
	}


	@Override
	public List<ResultDataVO> getErrDataMaxColCnt(ResultVO search) {
		
		return resultMapper.selectErrDataMaxColCnt(search);
	}


	@Override
	public List<ResultDataVO> getErrListByDqi(ResultVO search) {
		
		return resultMapper.selectErrListByDqi(search);
	}


	@Override
	public List<ResultDataVO> getErrDataByDqi(ResultVO search) {
		
		return resultMapper.selectErrDataByDqi(search);
	}


	@Override
	public String[] getDqiLnmLst() {
		
		return resultMapper.getDqiLnmLst();
	}


	@Override
	public ResultVO getStndTerm(ResultVO search) {
		return resultMapper.selectStndDtm(search);
	}


	@Override
	public ResultVO getModelTerm(ResultVO search) {
		return resultMapper.selectModelDtm(search);
	}
	
	@Override
	public List<ResultVO> getSditmTblList(ResultVO search) {
		return resultMapper.selectSditmTblList(search);
	}

	@Override
	public List<ResultVO> getSditmList(ResultVO search) {
		return resultMapper.selectSditmList(search);
	}
	
	@Override
	public List<ResultVO> getDmnList(ResultVO search) {
		return resultMapper.selectDmnList(search);
	}
	
	@Override
	public ResultVO getModelStatus(ResultVO search) {
		return resultMapper.selectModelStatus(search);
	}

	
	@Override
	public List<MultiDimVO> getMultiDimLst(MultiDimVO search) {
		
		return resultMapper.selectMultiDimLst(search);
	}
	
	@Override
	public List<MultiDimVO> getMultiDimLstTop5() {
		
		return resultMapper.selectMultiDimLstTop5();
	}


}
