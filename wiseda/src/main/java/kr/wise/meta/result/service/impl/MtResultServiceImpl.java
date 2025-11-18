package kr.wise.meta.result.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import kr.wise.meta.result.service.MtResultDataVO;
import kr.wise.meta.result.service.MtResultMapper;
import kr.wise.meta.result.service.MtResultService;
import kr.wise.meta.result.service.MtResultVO;

import org.springframework.stereotype.Service;

@Service("MtResultService")
public class MtResultServiceImpl implements MtResultService {

	@Inject
	MtResultMapper mtResultMapper;
	

	@Override
	public List<MtResultVO> getResultList(MtResultVO search) {
		return mtResultMapper.selectResultList(search);
	}


	@Override
	public MtResultVO getResultTerm(MtResultVO search) {
		
		return mtResultMapper.selectDtm(search);
	}
	
	@Override
	public List<MtResultVO> getTblList(MtResultVO search) {
		return mtResultMapper.selectTblList(search);
	}
	
	@Override
	public List<MtResultVO> getDmnRule(MtResultVO search) {
		return mtResultMapper.selectDmnRule(search);
	}
	
	@Override
	public List<MtResultVO> getExecList(MtResultVO search) {
		return mtResultMapper.selectExecList(search);
	}
	
	@Override
	public List<MtResultVO> getErrList(MtResultVO search) {
		return mtResultMapper.selectErrList(search);
	}


	@Override
	public BigDecimal getTotCnt(MtResultVO search) {
		
		return mtResultMapper.selectTotCnt(search);
	}


	@Override
	public BigDecimal getRunCnt(MtResultVO search) {
		
		return mtResultMapper.selectRunCnt(search);
	}


	@Override
	public List<MtResultDataVO> getErrDataMaxColCnt(MtResultVO search) {
		
		return mtResultMapper.selectErrDataMaxColCnt(search);
	}


	@Override
	public List<MtResultDataVO> getErrListByDqi(MtResultVO search) {
		
		return mtResultMapper.selectErrListByDqi(search);
	}


	@Override
	public List<MtResultDataVO> getErrDataByDqi(MtResultVO search) {
		
		return mtResultMapper.selectErrDataByDqi(search);
	}


	@Override
	public String[] getDqiLnmLst() {
		
		return mtResultMapper.getDqiLnmLst();
	}


	@Override
	public MtResultVO getStndTerm(MtResultVO search) {
		return mtResultMapper.selectStndDtm(search);
	}
	
	@Override
	public MtResultVO getCnt(MtResultVO search) {
		return mtResultMapper.selectCnt(search);
	}
	
	@Override
	public MtResultVO getGovCnt(MtResultVO search) {
		return mtResultMapper.selectGovCnt(search);
	}


	@Override
	public MtResultVO getModelTerm(MtResultVO search) {
		return mtResultMapper.selectModelDtm(search);
	}
	
	@Override
	public List<MtResultVO> getSditmTblList(MtResultVO search) {
		return mtResultMapper.selectSditmTblList(search);
	}

	@Override
	public List<MtResultVO> getSditmList(MtResultVO search) {
		return mtResultMapper.selectSditmList(search);
	}
	
	@Override
	public List<MtResultVO> getDmnList(MtResultVO search) {
		return mtResultMapper.selectDmnList(search);
	}
	
	@Override
	public List<MtResultVO> getGovSditmTblList(MtResultVO search) {
		return mtResultMapper.selectGovSditmTblList(search);
	}

	@Override
	public List<MtResultVO> getGovSditmList(MtResultVO search) {
		return mtResultMapper.selectGovSditmList(search);
	}
	
	@Override
	public List<MtResultVO> getGovDmnList(MtResultVO search) {
		return mtResultMapper.selectGovDmnList(search);
	}
	
	@Override
	public List<MtResultVO> getModelStatus(MtResultVO search) {
		return mtResultMapper.selectModelStatus(search);
	}
	
	
	// poi(구조진단)
	// 물리모델
	@Override
	public List<MtResultVO> getStructResultList(MtResultVO search) {
		return mtResultMapper.selectStructResultList(search);
	}
	
	@Override
	public List<MtResultVO> getDbcTblList(MtResultVO search) {
		return mtResultMapper.selectDbcTblList(search);
	}
	
	@Override
	public List<MtResultVO> getDbcColList(MtResultVO search) {
		return mtResultMapper.selectDbcColList(search);
	}
	
	@Override
	public List<MtResultVO> getPdmTblList(MtResultVO search) {
		return mtResultMapper.selectPdmTblList(search);
	}
	
	@Override
	public List<MtResultVO> getPdmColList(MtResultVO search) {
		return mtResultMapper.selectPdmColList(search);
	}
		
	// poi(구조진단)
	// 개별모델
	@Override
	public List<MtResultVO> getGovStructResultList(MtResultVO search) {
		return mtResultMapper.selectGovStructResultList(search);
	}
	
	@Override
	public List<MtResultVO> getGovDbcTblList(MtResultVO search) {
		return mtResultMapper.selectGovDbcTblList(search);
	}
	
	@Override
	public List<MtResultVO> getGovDbcColList(MtResultVO search) {
		return mtResultMapper.selectGovDbcColList(search);
	}
	
	@Override
	public List<MtResultVO> getGovTblList(MtResultVO search) {
		return mtResultMapper.selectGovTblList(search);
	}
	
	@Override
	public List<MtResultVO> getGovColList(MtResultVO search) {
		return mtResultMapper.selectGovColList(search);
	}
	
	@Override
	public String getDbConnTrgURL(MtResultVO search) {
		return mtResultMapper.selectDbConnTrgURL(search);
	}
	
	@Override
	public List<MtResultVO> getTblDdList(MtResultVO search) {
		return mtResultMapper.selectTblDdList(search);
	}
	
	@Override
	public List<MtResultVO> getColDdList(MtResultVO search) {
		return mtResultMapper.selectColDdList(search);
	}
	
	@Override
	public List<MtResultVO> getGovTblDdList(MtResultVO search) {
		return mtResultMapper.selectGovTblDdList(search);
	}
	
	@Override
	public List<MtResultVO> getGovColDdList(MtResultVO search) {
		return mtResultMapper.selectGovColDdList(search);
	}
}
